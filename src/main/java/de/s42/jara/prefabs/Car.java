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
package de.s42.jara.prefabs;

import de.s42.jara.Configuration;
import de.s42.jara.assets.AssetManager;
import de.s42.jara.core.Color;
import de.s42.jara.core.Vector3;
import de.s42.jara.enitites.Scene;
import de.s42.jara.enitites.primitives.Triangle;
import de.s42.jara.loaders.objloader.ObjLoader;
import de.s42.jara.materials.Material;
import java.io.File;
import java.nio.file.Path;
import java.util.List;

/**
 *
 * @author Benjamin Schiller
 */
public final class Car
{
	private Car()
	{
	}

	public static void load(Scene scene, AssetManager assets, Vector3 translation, Vector3 scale)
	{
		assert scene != null;
		assert assets != null;
		assert translation != null;
		assert scale != null;

		Material paint = new Material(
			Color.Black,
			new Color(0.0, 0.25, 0.1),
			0.7,
			0.3,
			Material.IOR_CAR_PAINT
		);
		paint.clearCoatRoughness = 0.05;
		paint.clearCoat = 1.0;
		assets.setMaterial("paint", paint);

		Material back_light = new Material(
			new Color(1.0 * 1, 0.0 * 1, 0.0 * 1),
			Color.Black,
			0.0,
			1.0,
			Material.IOR_PLASTIC
		);
		back_light.doubleSided = false;
		assets.setMaterial("back_light", back_light);

		Material side_light = new Material(
			new Color(1.0 * 5, 0.075 * 5, 0.0 * 5),
			Color.Black,
			0.0,
			1.0,
			Material.IOR_PLASTIC
		);
		side_light.doubleSided = false;
		assets.setMaterial("side_light", side_light);

		Material front_light = new Material(
			new Color(1.0 * 5, 1.0 * 5, 1.0 * 5),
			Color.Black,
			0.0,
			1.0,
			Material.IOR_PLASTIC
		);
		front_light.doubleSided = false;
		assets.setMaterial("front_light", front_light);

		Material in_car_light = new Material(
			new Color(0.1 * 1, 0.1 * 1, 0.8 * 1),
			Color.Black,
			0.0,
			1.0,
			Material.IOR_PLASTIC
		);
		in_car_light.doubleSided = false;
		assets.setMaterial("in_car_light", in_car_light);

		Material light_brown = new Material(
			Color.Black,
			new Color(0.9, 0.85, 0.7),
			0.0,
			0.8,
			Material.IOR_PLASTIC
		);
		light_brown.doubleSided = false;
		assets.setMaterial("light_brown", light_brown);

		Material black = new Material(
			Color.Black,
			new Color(0.1, 0.1, 0.1),
			0.0,
			0.5,
			Material.IOR_RUBBER
		);
		assets.setMaterial("black", black);

		Material window = new Material(
			Color.Black,
			new Color(0.90, 0.95, 0.98),
			1.0,
			0.0,
			Material.IOR_AIR
		);
		//window.doubleSided = false;
		window.transparency = 0.80;
		assets.setMaterial("window", window);

		Material grill = new Material(
			Color.Black,
			new Color(0.95, 0.95, 0.95),
			1.0,
			0.2,
			Material.IOR_STEEL
		);
		grill.doubleSided = false;
		assets.setMaterial("grill", grill);

		Material mirror = new Material(
			Color.Black,
			new Color(0.95, 0.95, 0.95),
			1.0,
			0.0,
			Material.IOR_STEEL
		);
		mirror.doubleSided = false;
		assets.setMaterial("mirror", mirror);

		List<Triangle> triangles = ObjLoader.loadTriangles(
			Path.of(Configuration.getBasePath() + File.separator + "meshes/car.obj"),
			assets, translation, scale);
		scene.add(triangles);
	}
}
