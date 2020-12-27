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
public class Spheres implements SceneLoader
{
	@Override
	public Scene loadScene( AssetManager assets)
	{
		assert assets != null;
		
		Scene scene = new Scene("Spheres");

		DirectionalLight sun = new DirectionalLight(
			new Vector3(0.5, 1.0, 0.0),
			new Color(1.1, 1.1, 1.05),
			0.98, //discSize
			0.98, //rampUpBrightness			
			4.0, //rampUpExponent 
			4.0, //rampUpScale 
			26.0 //attenuationExponent
		);
		scene.setDirectionalLight(sun);

		AmbientLight ambient = new AmbientLight(
			new Color(0.05, 0.20, 0.45)
		);
		scene.setAmbientLight(ambient);

		Camera camera = new Camera(
			new Vector3(16.0, 7.0, 7.0),
			new Vector3(3.5, 2.0, -10.0),
			Vector3.UP,
			Math.toRadians(75.0),
			(double) Configuration.getWidth() / (double) Configuration.getHeight()
		);
		scene.setCamera(camera);

		Material stone = new Material(
			Color.Black,
			new Color(0.7, 0.7, 0.65, 1.0),
			0.0,
			1.0,
			Material.IOR_STONE
		);

		Material plasticRed = new Material(
			Color.Black,
			new Color(0.9, 0.1, 0.1, 1.0),
			0.5,
			0.4,
			Material.IOR_STEEL
		);

		Material plasticYellow = new Material(
			Color.Black,
			new Color(0.9, 0.9, 0.1, 1.0),
			0.0,
			0.2,
			Material.IOR_PLASTIC
		);

		Material mirror = new Material(
			Color.Black,
			Color.White,
			0.9,
			0.0,
			Material.IOR_STEEL
		);

		Material glowy = new Material(
			new Color(2.95, 2.95, 2.93, 1.0),
			Color.White,
			0.0,
			0.0,
			Material.IOR_AIR
		);

		Material rainbow = new RainbowMaterial(
			new Color(0.1, 0.1, 0.1, 1.0),
			0.0,
			0.0,
			Material.IOR_GLASS,
			0.3, //scale
			1.0 //amplitude
		);

		Sphere sphere1 = new Sphere(new Vector3(-8.0, 0.0, -9.0), rainbow, 6.0);
		scene.add(sphere1);

		Sphere sphere2 = new Sphere(new Vector3(4.0, 0.0, 4.0), plasticYellow, 3.0);
		scene.add(sphere2);

		Sphere sphere3 = new Sphere(new Vector3(0.0, 5.0, -10.0), stone, 3.0);
		scene.add(sphere3);

		Sphere sphere4 = new Sphere(new Vector3(6.0, 0.0, -13.0), plasticRed, 5.0);
		scene.add(sphere4);

		Sphere sphere5 = new Sphere(new Vector3(4.0, 0.0, -2.0), glowy, 1.0);
		scene.add(sphere5);

		Sphere sphere6 = new Sphere(new Vector3(-5.0, -8.0, -20.0), mirror, 9.0);
		scene.add(sphere6);

		Sphere sphere7 = new Sphere(new Vector3(-35.0, 12.0, -150.0), plasticYellow, 29.0);
		scene.add(sphere7);

		Sphere sphere8 = new Sphere(new Vector3(3.0, 7.0, -9.0), rainbow, 1.0);
		scene.add(sphere8);

		Sphere sphere9 = new Sphere(new Vector3(-8.0, 9.0, -6.0), stone, 3.0);
		scene.add(sphere9);

		Plane plane = new Plane(
			new Vector3(0.0, -7.0, 0.0),
			stone,
			new Vector3(0.0, 1.0, 0.0)
		);
		scene.add(plane);

		return scene;
	}

	@Override
	public int getPreferredWidth()
	{
		return Configuration.getDefaultWidth();
	}

	@Override
	public int getPreferredHeight()
	{
		return Configuration.getDefaultHeight();
	}
}
