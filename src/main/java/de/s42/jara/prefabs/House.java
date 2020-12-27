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
 * @author Benjamin.Schiller
 */
public final class House
{
	private House()
	{

	}

	public static void load( Scene scene,  AssetManager assets,  Vector3 translation,  Vector3 scale)
	{
		assert scene != null;
		assert assets != null;
		assert translation != null;
		assert scale != null;

		Material foundationGreyBrick = new Material(
			Color.Black,
			new Color(0.65, 0.65, 0.65),
			0.0,
			0.7,
			Material.IOR_STONE
		);
		assets.setMaterial("foundation_grey_brick", foundationGreyBrick);

		Material curb = new Material(
			Color.Black,
			new Color(0.65, 0.65, 0.65),
			0.0,
			0.8,
			Material.IOR_STONE
		);
		assets.setMaterial("curb", curb);

		Material metalGrey = new Material(
			Color.Black,
			new Color(0.65, 0.65, 0.65),
			0.9,
			0.6,
			Material.IOR_STEEL
		);
		assets.setMaterial("metal_grey", metalGrey);

		Material plasterGrey = new Material(
			Color.Black,
			new Color(0.65, 0.65, 0.65),
			0.0,
			0.8,
			Material.IOR_STONE
		);
		assets.setMaterial("plaster_grey", plasterGrey);

		Material plasterWhite = new Material(
			Color.Black,
			new Color(0.85, 0.85, 0.85),
			0.0,
			0.8,
			Material.IOR_STONE
		);
		assets.setMaterial("plaster_white", plasterWhite);

		Material woodBrown = new Material(
			Color.Black,
			new Color(133 / 255.0, 94 / 255.0, 66 / 255.0),
			0.0,
			0.6,
			Material.IOR_WOOD
		);
		assets.setMaterial("wood_balls_brown", woodBrown);
		assets.setMaterial("wood_brown", woodBrown);

		Material windowGlass = new Material(
			Color.Black,
			new Color(0.90, 0.95, 0.98),
			1.0,
			0.0,
			Material.IOR_AIR + 0.01
		);
		windowGlass.transparency = 0.80;
		assets.setMaterial("window_glass", windowGlass);

		Material plateWhite = new Material(
			Color.Black,
			new Color(0.85, 0.85, 0.85),
			0.0,
			0.8,
			Material.IOR_STONE
		);
		assets.setMaterial("plate_white", plateWhite);

		Material parapet = new Material(
			Color.Black,
			new Color(0.85, 0.85, 0.85),
			0.0,
			0.8,
			Material.IOR_STONE
		);
		assets.setMaterial("parapet", parapet);

		List<Triangle> discFloor = ObjLoader.loadTriangles(
			Path.of(Configuration.getBasePath() + File.separator + "meshes/house.obj"),
			assets, translation, scale);
		scene.add(discFloor);
	}
}
