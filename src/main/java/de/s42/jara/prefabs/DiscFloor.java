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
import de.s42.jara.core.Vector3;
import de.s42.jara.enitites.Scene;
import de.s42.jara.enitites.primitives.Triangle;
import de.s42.jara.loaders.objloader.ObjLoader;
import de.s42.jara.materials.PbrMaterial;
import java.io.File;
import java.nio.file.Path;
import java.util.List;

/**
 *
 * @author Benjamin.Schiller
 */
public final class DiscFloor
{
	private DiscFloor()
	{

	}

	public static void load(Scene scene, AssetManager assets, Vector3 translation, Vector3 scale)
	{
		assert scene != null;
		assert assets != null;
		assert translation != null;
		assert scale != null;

		PbrMaterial CobbleStone = assets.loadPbrMaterial(AssetManager.Materials.CobbleStone, new Vector3(3.0));
		assets.setMaterial("SteelBlack", CobbleStone);

		List<Triangle> discFloor = ObjLoader.loadTriangles(
			Path.of(Configuration.getBasePath() + File.separator + "meshes/disc-floor.obj"),
			assets, translation, scale);
		scene.add(discFloor);
	}
}