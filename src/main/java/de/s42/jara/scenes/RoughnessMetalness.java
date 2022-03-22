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
package de.s42.jara.scenes;

import de.s42.jara.Configuration;
import de.s42.jara.SceneLoader;
import de.s42.jara.assets.AssetManager;
import de.s42.jara.core.Color;
import de.s42.jara.enitites.Scene;
import de.s42.jara.core.Vector3;
import de.s42.jara.enitites.Camera;
import de.s42.jara.enitites.primitives.Sphere;
import de.s42.jara.materials.Material;
import de.s42.jara.materials.Texture;

/**
 *
 * @author Benjamin Schiller
 */
public class RoughnessMetalness implements SceneLoader
{
	public final static int PREFERRED_WIDTH = 1000;
	public final static int PREFERRED_HEIGHT = 1000;
	public final static int GRID_SIZE_X = 6;
	public final static int GRID_SIZE_Y = 6;
	public final static double SPHERE_DISTANCE = 2.2;
	public final static double SPHERE_RADIUS = 1.0;
	public final static double CAMERA_FOV = 45.0;

	@Override
	public Scene loadScene(AssetManager assets)
	{
		assert assets != null;

		Scene scene = new Scene("Material Test");

		Texture background = assets.loadBackground(
			AssetManager.Backgrounds.Sunset,
			0.9, //rampUpBrightness
			5.0, //rampUpExponent
			10.0 //rampUpScale
		);

		scene.setBackgroundTexture(background);
		scene.setShowDirectBackground(false);

		//calculate optimal camera distance from width and height of sphere grid and the camera fov
		double cameraDist
			= 1.1
			* (((double) Math.max(GRID_SIZE_X, GRID_SIZE_Y) * SPHERE_DISTANCE + SPHERE_RADIUS) * 0.5)
			/ Math.tan(Math.toRadians(CAMERA_FOV) * 0.5);

		Camera camera = new Camera(
			new Vector3(0.0, 0.0, cameraDist),
			new Vector3(0.0, 0.0, 0.0),
			Vector3.UP,
			Math.toRadians(CAMERA_FOV),
			(double) Configuration.getWidth() / (double) Configuration.getHeight()
		);
		scene.setCamera(camera);

		for (int x = 0; x < GRID_SIZE_X; ++x) {

			for (int y = 0; y < GRID_SIZE_Y; ++y) {

				Material material = new Material(
					new Color(0.0, 0.0, 0.0, 1.0), //emissive
					//new Color(1.0, 0.78, 0.74, 1.0), //albedo
					new Color(1.0, 1.0, 1.0, 1.0), //albedo
					(GRID_SIZE_Y > 1 ? (double) y / (double) (GRID_SIZE_Y - 1) : 0.0), //metalness
					(GRID_SIZE_X > 1 ? (double) x / (double) (GRID_SIZE_X - 1) : 1.0), //roughness
					Material.IOR_PLASTIC
				);

				Sphere sphere = new Sphere(new Vector3(
					x * SPHERE_DISTANCE - ((double) (GRID_SIZE_X - 1) * SPHERE_DISTANCE * 0.5),
					y * SPHERE_DISTANCE - ((double) (GRID_SIZE_Y - 1) * SPHERE_DISTANCE * 0.5),
					0.0),
					material,
					SPHERE_RADIUS);
				scene.add(sphere);
			}
		}

		return scene;
	}

	@Override
	public int getPreferredWidth()
	{
		return PREFERRED_WIDTH;
	}

	@Override
	public int getPreferredHeight()
	{
		return PREFERRED_HEIGHT;
	}
}
