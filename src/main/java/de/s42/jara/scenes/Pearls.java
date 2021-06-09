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
import de.s42.jara.enitites.lights.AmbientLight;
import de.s42.jara.enitites.lights.DirectionalLight;
import de.s42.jara.enitites.primitives.Plane;
import de.s42.jara.enitites.primitives.Sphere;
import de.s42.jara.materials.Material;
import de.s42.jara.materials.RainbowMaterial;

/**
 *
 * @author Benjamin.Schiller
 */
public class Pearls implements SceneLoader
{
	private final static AssetManager.Materials FLOOR_MATERIAL = AssetManager.Materials.GoldOre;

	@Override
	public Scene loadScene(AssetManager assets)
	{
		assert assets != null;

		Scene scene = new Scene("Pearls");

		DirectionalLight light = new DirectionalLight(
			new Vector3(-5.0, 1.0, 0.0),
			new Color(1.5, 1.0, 0.7, 1.0),
			0.97, //discSize
			0.9, //rampUpBrightness			
			5.0, //rampUpExponent 		
			2.0, //rampUpScale 
			7.0 //attenuationExponent			
		);
		scene.setDirectionalLight(light);

		AmbientLight ambient = new AmbientLight(
			new Color(0.30, 0.30, 0.35)
		);
		scene.setAmbientLight(ambient);

		Camera camera = new Camera(
			new Vector3(0.5, 0.4, 0.5),
			new Vector3(0.0, 0.025, 0.0),
			Vector3.UP,
			Math.toRadians(90),
			(double) Configuration.getWidth() / (double) Configuration.getHeight()
		);
		scene.setCamera(camera);

		Material floorMaterial = assets.loadPbrMaterial(FLOOR_MATERIAL, new Vector3(1.0));

		RainbowMaterial sphereMaterial = new RainbowMaterial(
			new Color(0.0, 0.0, 0.0, 1.0),
			0.5,
			0.4,
			Material.IOR_IRON,
			6.5, //scale
			0.5 //amplitude
		);

		Sphere sphere = new Sphere(
			new Vector3(0.0, 0.1, 0.0),
			sphereMaterial,
			0.1
		);
		scene.add(sphere);

		Sphere sphere2 = new Sphere(
			new Vector3(-0.3, 0.1, 0.2),
			sphereMaterial,
			0.1
		);
		scene.add(sphere2);

		Sphere sphere3 = new Sphere(
			new Vector3(0.4, 0.1, 0.1),
			sphereMaterial,
			0.1
		);
		scene.add(sphere3);

		Plane plane = new Plane(
			new Vector3(0.0, 0.0, 0.0),
			floorMaterial,
			new Vector3(0.0, 1.0, 0.0)
		);
		scene.add(plane);

		return scene;
	}
}
