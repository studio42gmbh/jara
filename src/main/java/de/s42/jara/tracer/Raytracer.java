/*
 * The MIT License
 *
 * Copyright 2020 Studio 42 GmbH (https://www.s42m.de).
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package de.s42.jara.tracer;

import de.s42.jara.Configuration;
import de.s42.jara.enitites.Camera;
import de.s42.jara.enitites.Scene;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author Benjamin Schiller
 */
public class Raytracer
{
	private final static Logger log = LogManager.getLogger(Raytracer.class.getName());

	private static final int TILE_COUNT = Configuration.getWidth() * Configuration.getHeight()
		/ Configuration.getTilesize() / Configuration.getTilesize();

	public final RenderBuffer buffer;
	public final Scene scene;
	public long passesRendered;
	private double hdrScale = 1.0;
	@SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
	private final List<RenderWorker> threads = new ArrayList(Configuration.getThreads());
	final AtomicInteger tilesToRenderCount = new AtomicInteger(0);

	//create queue that can handle the max amount of tiles
	final BlockingQueue<Rectangle> tilesToRender = new ArrayBlockingQueue(TILE_COUNT);
	Rectangle[] tiles = new Rectangle[TILE_COUNT];

	public Raytracer(RenderBuffer buffer, Scene scene)
	{
		assert buffer != null : "buffer may not be null";
		assert scene != null : "scene may not be null";

		this.buffer = buffer;
		this.scene = scene;
		passesRendered = 0;

		init();
	}

	private void init()
	{
		//prepare this scene for rendering
		scene.prepareForRendering();

		//init tile infos
		Arrays.setAll(tiles, index -> new Rectangle());

		//init threads
		for (int t = 0; t < Configuration.getThreads(); ++t) {
			RenderWorker renderWorker = new RenderWorker(this);
			renderWorker.setPriority(Thread.MIN_PRIORITY);
			threads.add(renderWorker);
			renderWorker.start();
		}

		//@workaround for multithread gc undeterministic behavior in openjdk 14 
		//massive parallel usage of cold trace code seems to produce "shadow" garbage of i.e. Color instances
		renderTile(new RayContext(this), ThreadLocalRandom.current(), new Rectangle(0, 0, 5, 5));
		buffer.reset();
		synchronized (this) {
			try {
				this.wait(10);
			}
			catch (InterruptedException ex) {
				log.error(ex.getMessage(), ex);
			}
		}
	}

	public void render()
	{
		//raytrace
		int tileSize = Configuration.getTilesize();
		int w = buffer.getWidth() / tileSize;
		int h = buffer.getHeight() / tileSize;
		for (int y = 0; y < h; ++y) {
			for (int x = 0; x < w; ++x) {
				Rectangle tile = tiles[y * w + x];
				tile.setBounds(x * tileSize, y * tileSize, tileSize, tileSize);
				scheduleRenderTile(tile);
			}
		}

		waitForTasksDone();

		passesRendered++;

		blit();
	}

	public void renderTile(RayContext context, ThreadLocalRandom random, Rectangle tile)
	{
		assert context != null;
		assert random != null;
		assert tile != null;

		//raytrace
		double width = buffer.getWidth();
		double height = buffer.getHeight();
		double jitterX = 2.0 / width; // 2.0 because the cam space goes from -1.0 to 1.0 -> 1 pixel range is 2.0 / w
		double jitterY = 2.0 / height; // 2.0 because the cam space goes from -1.0 to 1.0 -> 1 pixel range is 2.0 / h
		int rayDepth = Configuration.getRayDepth();
		Camera camera = scene.getCamera();

		for (double yc = tile.y; yc < tile.y + tile.height; ++yc) {
			for (double xc = tile.x; xc < tile.x + tile.width; ++xc) {

				context.reset(rayDepth);

				double xs = (xc * 2.0 - width) / width;
				double ys = -((yc * 2.0 - height) / height);

				//@todo JARA might want to try to create a real cirular jitter
				/*
				double theta = random.nextDouble(-Math.PI, +Math.PI);
				double r = random.nextDouble();
				double randX = r * Math.cos(theta);
				double randY = r * Math.sin(theta);
				 */
				double randX = random.nextDouble();
				double randY = random.nextDouble();
				double offsetX = randX * jitterX * 2.0 - jitterX;
				double offsetY = randY * jitterY * 2.0 - jitterY;

				//give it color stability on the first passes - later do temporal anitaliasing
				if (passesRendered < 10) {
					offsetX = 0.0;
					offsetY = 0.0;
				}

				camera.getRayFromScreenCoords(context, xs + offsetX, ys + offsetY);

				raytrace(context);

				//tent weighting - summing to 1 in average
				double dX = Math.abs(0.5 - randX) * 2.0;
				double dY = Math.abs(0.5 - randY) * 2.0;

				double weight = 2.0 - (dX + dY);

				//give it color stability on the first passes - later do temporal anitaliasing
				if (passesRendered < 10) {
					weight = 1.0;
				}

				buffer.addColor((int) xc, (int) yc, context.color.multiplyRGB(weight));
			}
		}
	}

	public boolean raytrace(RayContext context)
	{
		assert context != null;

		if (context.depth <= 0) {
			context.color.setRGBZero();
			return true;
		}

		context.lastDirection.copy(context.direction);
		context.lastOrigin.copy(context.origin);

		//does hit an entity
		if (scene.intersect(context)) {

			context.entity.compute(context);
			return true;
		}

		//return background
		scene.background(context);
		return true;
	}

	public void blit()
	{
		buffer.blitColorsToBuffer(hdrScale / (double) passesRendered);
	}

	protected void waitForTasksDone()
	{
		synchronized (tilesToRenderCount) {
			while (tilesToRenderCount.get() > 0) {
				try {
					tilesToRenderCount.wait();
				}
				catch (InterruptedException ex) {
					log.error("Error waiting for tasks", ex);
				}
			}
		}
	}

	protected void scheduleRenderTile(Rectangle tile)
	{
		assert tile != null;

		try {

			tilesToRenderCount.incrementAndGet();
			tilesToRender.put(tile);
		}
		catch (InterruptedException ex) {
			log.error("Error filling queue", ex);
		}
	}

	public List<RenderWorker> getThreads()
	{
		return Collections.unmodifiableList(threads);
	}

	public double getHdrScale()
	{
		return hdrScale;
	}

	public void setHdrScale(double hdrScale)
	{
		this.hdrScale = hdrScale;
	}
}
