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
import de.s42.jara.enitites.primitives.Disc;
import de.s42.jara.enitites.primitives.Sphere;
import de.s42.jara.materials.Material;
import de.s42.jara.materials.RainbowMaterial;
import de.s42.jara.materials.Texture;

/**
 *
 * @author Benjamin Schiller
 */
public class Spheres implements SceneLoader
{
	@Override
	public Scene loadScene(AssetManager assets)
	{
		assert assets != null;

		Scene scene = new Scene("Spheres");

		Texture background = assets.loadBackground(
			AssetManager.Backgrounds.Amsterdam,
			0.8, //rampUpBrightness
			5.0, //rampUpExponent
			2.0 //rampUpScale
		);
		scene.setBackgroundTextureSmoothing(300.0);
		scene.setBackgroundTextureSmoothingRender(300.0);
		scene.setBackgroundTexture(background);
		scene.setShowDirectBackground(true);
		scene.setBackgroundTextureOffsetX(0.65);
		//scene.setBackgroundColor(new Color(0.12 * 0.3, 0.11 * 0.3, 0.13 * 0.3, 1.0));

		Camera camera = new Camera(
			new Vector3(17.0, 7.0, 8.0),
			new Vector3(3.5, 3.0, -10.0),
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
			0.3,
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
			new Color(0.85, 0.83, 0.75, 1.0),
			1.0,
			0.0,
			Material.IOR_STEEL
		);

		Material steel = new Material(
			Color.Black,
			new Color(0.85, 0.83, 0.75, 1.0),
			1.0,
			0.2,
			Material.IOR_STEEL
		);

		Material rainbow = new RainbowMaterial(
			Color.Black,
			0.0,
			0.0,
			Material.IOR_GLASS,
			0.3, //scale
			1.0 //amplitude
		);
		
		Material rainbowGlow = new RainbowMaterial(
			new Color(1.0, 1.0, 1.0, 1.0),
			0.0,
			0.0,
			Material.IOR_GLASS,
			0.16, //scale
			0.8 //amplitude
		);
		

		Material transparentMaterial = new Material(
			Color.Black,
			new Color(0.0, 0.0, 0.1, 1.0),
			0.0,
			0.0,
			Material.IOR_GLASS
		);
		transparentMaterial.transparency = 0.95;
		
		Material transparentRoughMaterial = new Material(
			Color.Black,
			new Color(0.0, 0.0, 0.1, 1.0),
			0.5,
			0.2,
			Material.IOR_GLASS
		);
		transparentRoughMaterial.transparency = 0.80;
		

		Material floorMaterial = assets.loadPbrMaterial(AssetManager.Materials.CobbleStone, new Vector3(0.025));
		
		Material pbrMaterial = assets.loadPbrMaterial(AssetManager.Materials.GoldOre, new Vector3(1.0));
		
		Material pbrMaterial2 = assets.loadPbrMaterial(AssetManager.Materials.TerazzoBrickWork, new Vector3(1.0));

		Material pbrMaterial3 = assets.loadPbrMaterial(AssetManager.Materials.CastIronRusted, new Vector3(1.0));
		
		Sphere sphere1 = new Sphere(new Vector3(-8.5, -1.0, -9.0), rainbow, 6.0);
		scene.add(sphere1);

		Sphere sphere2 = new Sphere(new Vector3(4.0, 0.0, 4.0), plasticYellow, 3.0);
		//scene.add(sphere2);

		Sphere sphere3 = new Sphere(new Vector3(0.0, 5.0, -10.0), transparentMaterial, 3.0);
		scene.add(sphere3);

		Sphere sphere4 = new Sphere(new Vector3(4.0, -4.0, -14.0), plasticRed, 3.0);
		scene.add(sphere4);

		Sphere sphere5 = new Sphere(new Vector3(-20.0, -5.0, -50.0), rainbowGlow, 10.0);
		//scene.add(sphere5);

		Sphere sphere6 = new Sphere(new Vector3(-6.0, -2.0, -22.0), pbrMaterial3, 5.0);
		scene.add(sphere6);

		Sphere sphere7 = new Sphere(new Vector3(-35.0, 12.0, -150.0), pbrMaterial2, 19.0);
		scene.add(sphere7);

		Sphere sphere8 = new Sphere(new Vector3(-5.0, 8.0, -6.0), rainbow, 1.0);
		scene.add(sphere8);

		Sphere sphere9 = new Sphere(new Vector3(-8.0, 9.0, -6.0), stone, 3.0);
		scene.add(sphere9);
		
		Sphere sphere10 = new Sphere(new Vector3(5.0, -2.0, -55.0), pbrMaterial, 5.0);
		scene.add(sphere10);
		
		Sphere sphere11 = new Sphere(new Vector3(9.0, -4.0, -25.0), transparentRoughMaterial, 3.0);
		scene.add(sphere11);
		
		Sphere sphere12 = new Sphere(new Vector3(-50.0, 3.0, -125.0), mirror, 10.0);
		scene.add(sphere12);		
				
		/*Plane plane = new Plane(
			new Vector3(0.0, -7.0, 0.0),
			floorMaterial,
			new Vector3(0.0, 1.0, 0.0)
		);
		scene.add(plane);
		*/
		Disc disc = new Disc(
			new Vector3(0.0, -7.0, -50.0),
			floorMaterial,
			new Vector3(0.0, 1.0, 0.0),
			150
		);
		scene.add(disc);
		
		return scene;
	}
}
