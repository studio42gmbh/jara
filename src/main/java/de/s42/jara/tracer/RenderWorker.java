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

import java.awt.Rectangle;
import java.util.concurrent.ThreadLocalRandom;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author Benjamin Schiller
 */
public class RenderWorker extends Thread
{
	private final static Logger log = LogManager.getLogger(RenderWorker.class.getName());

	public long renderedTileCount = 0L;

	private final Raytracer raytracer;
	private final RayContext context;
	private final ThreadLocalRandom random;

	public RenderWorker(Raytracer raytracer)
	{
		assert raytracer != null;

		this.raytracer = raytracer;
		context = new RayContext(this.raytracer);
		random = ThreadLocalRandom.current();
	}

	@Override
	public void run()
	{

		while (true) {

			try {

				Rectangle tile = raytracer.tilesToRender.take();

				raytracer.renderTile(context, random, tile);

				renderedTileCount++;

				int c = raytracer.tilesToRenderCount.decrementAndGet();
				if (c == 0) {
					synchronized (raytracer.tilesToRenderCount) {
						raytracer.tilesToRenderCount.notifyAll();
					}
				}
			}
			catch (InterruptedException ex) {
				log.error("Error in queue", ex);
			}
		}
	}
}
