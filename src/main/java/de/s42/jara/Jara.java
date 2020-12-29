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
package de.s42.jara;

import de.s42.jara.tracer.RenderWorker;
import java.util.Arrays;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author Benjamin.Schiller
 */
public class Jara
{
	private final static Logger log = LogManager.getLogger(Jara.class.getName());

	public static void main(String[] args)
	{
		log.info("Jara");
		log.info("---");
		log.info("WIDTH: " + Configuration.getWidth());
		log.info("HEIGHT: " + Configuration.getHeight());
		log.info("THREADS: " + Configuration.getThreads());
		log.info("MAX_PASSES: " + Configuration.getMaxPasses());
		log.info("TILESIZE: " + Configuration.getTilesize());
		log.info("RAY_DEPTH: " + Configuration.getRayDepth());
		log.info("DIFFUSE_SUBSAMPLES: " + Arrays.toString(Configuration.getDiffuseSubsamples()));
		log.info("SPECULAR_SUBSAMPLES: " + Arrays.toString(Configuration.getSpecularSubsamples()));
		log.info("REFRACTION_SUBSAMPLES: " + Arrays.toString(Configuration.getRefractionSubsamples()));
		log.info("SCENE_LOADER: " + Configuration.getSceneLoader().getClass().getSimpleName());
		log.info("CAMERA_DOF: " + Configuration.getCameraDofSize());
		log.info("SAVE_FORMAT: " + Configuration.getSaveFormat());
		log.info("SPATIAL_TREE_MAX_DEPTH: " + Configuration.getSpatialTreeMaxDepth());
		log.info("SPATIAL_TREE_SPLIT_NODE_SIZE: " + Configuration.getSpatialTreeSplitNodeSize());
		log.info("---");

		App app = new App();

		long startTime = System.nanoTime();
		long lastLogTime = System.nanoTime();

		while ((long) app.raytracer.passesRendered < Configuration.getMaxPasses() && app.isRendering()) {

			app.render();

			//at most print one lap per second
			//@todo JARA might want to provide that debouncing to Log
			if (System.nanoTime() - lastLogTime > 1000000000L) {
				lastLogTime = System.nanoTime();

				double deltaMs = (System.nanoTime() - startTime) / 1000000.0;

				log.info("" + app.raytracer.passesRendered + " passes in " + deltaMs + " ms (" + (deltaMs / app.raytracer.passesRendered) + " ms per pass)");
			}
		}

		double deltaMs = (System.nanoTime() - startTime) / 1000000.0;
		log.info("Completed " + app.raytracer.passesRendered + " passes in " + deltaMs + " ms");
		log.info("---");
		for (RenderWorker worker : app.raytracer.getThreads()) {
			log.info("WORKER: " + worker.getId() + ", " + worker.renderedTileCount);
		}
		log.info("---");
		log.info("Done Rendering");
	}
}
