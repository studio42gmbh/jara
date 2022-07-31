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
import de.s42.jara.enitites.lights.DirectionalLight;
import de.s42.jara.enitites.primitives.Plane;
import de.s42.jara.enitites.primitives.Sphere;
import de.s42.jara.materials.Material;
import de.s42.jara.materials.Texture;

/**
 *
 * @author Benjamin Schiller
 */
public class Transparent implements SceneLoader
{
	private final static AssetManager.Materials FLOOR_MATERIAL = AssetManager.Materials.IronScratched;

	@Override
	public Scene loadScene(AssetManager assets)
	{
		assert assets != null;

		Scene scene = new Scene("Transparent");

		Texture background = assets.loadBackground(
			AssetManager.Backgrounds.Sunset,
			0.8, //rampUpBrightness
			5.0, //rampUpExponent
			2.0 //rampUpScale
		);
		scene.setBackgroundTextureSmoothing(200.0);
		scene.setBackgroundTextureSmoothingRender(200.0);		
		scene.setBackgroundTexture(background);
/*
		DirectionalLight light = new DirectionalLight(
			new Vector3(-5.0, 1.0, 1.0),
			new Color(1.5, 1.0, 0.7, 1.0),
			0.97, //discSize
			0.2, //rampUpBrightness			
			5.0, //rampUpExponent 		
			1.0, //rampUpScale 
			8.0 //attenuationExponent			
		);
		scene.setDirectionalLight(light);
*/
		Camera camera = new Camera(
			new Vector3(1.0, 1.5, 2.0),
			new Vector3(0.0, -0.0, 0.0),
			Vector3.UP,
			Math.toRadians(90),
			(double) Configuration.getWidth() / (double) Configuration.getHeight()
		);
		scene.setCamera(camera);

		Material sphereMaterial = new Material(
			Color.Black,
			new Color(0.0, 0.0, 0.1, 1.0),
			0.5,
			0.1,
			Material.IOR_GLASS
		);
		sphereMaterial.transparency = 0.9;

		Material sphereShinyMaterial = new Material(
			Color.Black,
			
			new Color(30.0 / 255.0, 135.0 / 255.0, 188.0 / 255.0, 1.0),
			0.0,
			0.0,
			Material.IOR_PLASTIC
		);

		Material sphereShiny2Material = new Material(
			Color.Black,
			
			new Color(232.0 / 255.0, 174.0 / 255.0, 0.0 / 255.0, 1.0),
			0.0,
			0.0,
			Material.IOR_PLASTIC
		);

		
		Sphere sphere1 = new Sphere(
			new Vector3(-0.2, 0.0, 0.0),
			sphereMaterial,
			0.8
		);
		scene.add(sphere1);

		Sphere sphere2 = new Sphere(
			new Vector3(1.3, -0.5, -0.5),
			sphereMaterial,
			0.3
		);
		scene.add(sphere2);

		Sphere sphere3 = new Sphere(
			new Vector3(-4.2, -0.4, -2.0),
			sphereMaterial,
			0.4
		);
		scene.add(sphere3);

		Sphere sphere4 = new Sphere(
			new Vector3(1.0, -0.5, -4.0),
			sphereShinyMaterial,
			0.3
		);
		scene.add(sphere4);

		Sphere sphere5 = new Sphere(
			new Vector3(-1.1, -0.7, 0.8),
			sphereShiny2Material,
			0.1
		);
		scene.add(sphere5);

		
		Material floorMaterial = assets.loadPbrMaterial(FLOOR_MATERIAL, new Vector3(0.25));
		
		Plane plane = new Plane(
			new Vector3(0.0, -0.8, 0.0),
			floorMaterial,
			new Vector3(0.0, 1.0, 0.0)
		);
		scene.add(plane);

		return scene;
	}
}
