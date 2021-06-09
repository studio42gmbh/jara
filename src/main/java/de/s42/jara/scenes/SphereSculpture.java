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
import de.s42.jara.core.Vector3;
import de.s42.jara.enitites.Camera;
import de.s42.jara.enitites.Scene;
import de.s42.jara.enitites.lights.AmbientLight;
import de.s42.jara.enitites.lights.DirectionalLight;
import de.s42.jara.enitites.primitives.Sphere;
import de.s42.jara.materials.Material;

public class SphereSculpture implements SceneLoader
{
	@Override
	public Scene loadScene(AssetManager assets)
	{
		assert assets != null;

		Scene scene = new Scene("Workshop Scene");

		DirectionalLight studioLight1 = new DirectionalLight(
			new Vector3(-2.0, 1.0, 1.0),
			new Color(1.5, 1.3, 0.95),
			0.995, //discSize
			0.9, //rampUpBrightness			
			15.0, //rampUpExponent 
			10.0, //rampUpScale 
			6.0 //attenuationExponent
		);
		scene.setDirectionalLight(studioLight1);

		//@todo JARA stil have to add 2nd light to fully emulate workshop scene
		/*
		DirectionalLight studioLight2 = new DirectionalLight(
			new Vector3(1.0, 1.0, 0.0),
			new Color(1.5, 1.3, 0.95),
			0.995, //discSize
			0.9, //rampUpBrightness			
			15.0, //rampUpExponent 
			10.0, //rampUpScale 
			6.0 //attenuationExponent
		);
		scene.setDirectionalLight(studioLight2);
		 */
		AmbientLight ambient = new AmbientLight(
			new Color(0.05, 0.05, 0.04)
		);
		scene.setAmbientLight(ambient);

		Camera camera = new Camera(
			new Vector3(16.0, 5.0, 14.0),
			new Vector3(3.5, 3.0, -10.0),
			Vector3.UP,
			Math.toRadians(75.0),
			(double) Configuration.getWidth() / (double) Configuration.getHeight()
		);
		scene.setCamera(camera);

		for (int c = 0; c < 500; ++c) {

			double i = c / 5.0;

			Material material = new Material(
				Color.Black,
				new Color(
					0.7,
					Math.sin(i / 30.0) * 0.3 + 0.5,
					0.7),
				Math.sin(i / 5.0) * 0.2 + 0.5,
				-Math.sin(i / 5.0) * 0.2 + 0.5,
				Material.IOR_PLASTIC
			);

			double cr = 100.0 / (i + 100);
			cr = Math.pow(cr, 2.0);
			cr *= 1.0;

			double r = 115.0 / (i + 115);
			r = Math.pow(r, 5.0);
			r *= 2.0;

			Sphere sphere = new Sphere(
				new Vector3(
					3.56 + Math.sin(i / 3.0) * cr,
					i / 10.0,
					-10 + Math.cos(i / 3.0) * cr
				),
				material,
				r);

			scene.add(sphere);
		}

		Material stone = new Material(
			Color.Black,
			new Color(0.45, 0.45, 0.45),
			0.8, //-> pop rock! FB 2020
			0.4,
			Material.IOR_STONE
		);

		Sphere floor = new Sphere(new Vector3(0.0, -3000002.0, 0.0), stone, 3000000.0);
		scene.add(floor);

		return scene;
	}
}
