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
public final class CarShelby
{
	private CarShelby()
	{
	}

	public static void load(Scene scene, AssetManager assets, Vector3 translation, Vector3 scale)
	{
		assert scene != null;
		assert assets != null;
		assert translation != null;
		assert scale != null;

		Material Default = new Material(
			Color.Black,
			new Color(0.5, 0.5, 0.5),
			0.0,
			1.0,
			Material.IOR_PLASTIC
		);

		/**/
 /*Material Carroserie = new Material(
			Color.Black,
			new Color(0.85, 0.0, 0.0),
			0.7,
			0.15,
			Material.IOR_STEEL
		);
		Carroserie.clearCoatRoughness = 0.1;
		Carroserie.clearCoat = 1.0;		*/
		//Carroserie.doubleSided = false;
		/**/
		Material Carroserie = new Material(
			Color.Black,
			new Color(75.0 / 256.0, 83.0 / 256.0, 32.0 / 256.0),
			0.7,
			0.2,
			Material.IOR_STEEL
		);
		Carroserie.clearCoatRoughness = 0.1;
		Carroserie.clearCoat = 0.5;
		assets.setMaterial("Carroserie", Carroserie);

		Material Chrome = new Material(
			Color.Black,
			new Color(0.95, 0.95, 0.95),
			1.0,
			0.05,
			Material.IOR_STEEL
		);
		//Chrome.doubleSided = false;
		assets.setMaterial("Chrome", Chrome);

		Material Cuire = new Material(
			Color.Black,
			new Color(0.9, 0.85, 0.7),
			0.0,
			0.3,
			Material.IOR_PLASTIC
		);
		Cuire.doubleSided = false;
		assets.setMaterial("Cuire", Cuire);

		Material Laniere = new Material(
			Color.Black,
			new Color(0.5, 0.35, 0.1),
			0.0,
			0.3,
			Material.IOR_PLASTIC
		);
		Laniere.doubleSided = false;
		assets.setMaterial("Laniere", Laniere);

		Material Pneu = new Material(
			Color.Black,
			new Color(0.15, 0.15, 0.15),
			0.0,
			0.4,
			Material.IOR_RUBBER
		);
		assets.setMaterial("Pneu", Pneu);
		assets.setMaterial("Pneu2", Pneu);

		Material Verre = new Material(
			Color.Black,
			new Color(0.90, 0.95, 0.98),
			1.0,
			0.0,
			Material.IOR_AIR + 0.01
		);
		//Verre.doubleSided = false;
		Verre.transparency = 0.80;
		assets.setMaterial("Verre", Verre);

		Material Lamp = new Material(
			Color.Black,
			new Color(0.90, 0.95, 0.98),
			1.0,
			0.3,
			Material.IOR_AIR + 0.1
		);
		Lamp.doubleSided = false;
		Lamp.transparency = 0.30;
		assets.setMaterial("Lamp", Lamp);

		Material Mirror = new Material(
			Color.Black,
			new Color(0.95, 0.95, 0.95),
			1.0,
			0.0,
			Material.IOR_STEEL
		);
		//Mirror.doubleSided = false;
		assets.setMaterial("Mirror", Mirror);

		Material Wood = new Material(
			Color.Black,
			new Color(0.1, 0.1, 0.1),
			0.0,
			0.2,
			Material.IOR_WOOD
		);
		assets.setMaterial("Wood", Wood);
		assets.setMaterial("Leather_volant", Wood);

		assets.setMaterial("Aiguille", Default);
		assets.setMaterial("Tableau_de_bord", Default);
		assets.setMaterial("Tapis_sol", Default);
		assets.setMaterial("Cuire_levier_de_vitesse_", Default);
		assets.setMaterial("Headlights__bottom", Default);
		assets.setMaterial("Material", Default);
		assets.setMaterial("Material.002", Default);
		assets.setMaterial("Compteur", Default);

		List<Triangle> triangles = ObjLoader.loadTriangles(
			Path.of(Configuration.getBasePath() + File.separator + "meshes/car-shelby.obj"),
			assets, translation, scale);
		scene.add(triangles);
	}
}
