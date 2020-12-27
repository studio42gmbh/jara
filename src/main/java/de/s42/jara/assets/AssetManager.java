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
package de.s42.jara.assets;

import de.s42.jara.Configuration;
import de.s42.jara.core.Color;
import de.s42.jara.core.Vector3;
import de.s42.jara.materials.Material;
import de.s42.jara.materials.PbrMaterial;
import de.s42.jara.materials.Texture;
import de.s42.jara.util.FileHelper;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Benjamin.Schiller
 */
public final class AssetManager
{
	public enum Materials
	{
		CobbleStone("materials/cobblestone", Material.IOR_STONE),
		GoldOre("materials/goldore", Material.IOR_STONE),
		TerazzoBrickWork("materials/terazzobrickwork", Material.IOR_STONE);

		public final String materialBasePath;
		public final double ior;

		private Materials(String materialBasePath, double ior)
		{
			this.materialBasePath = materialBasePath;
			this.ior = ior;
		}
	}

	public enum Backgrounds
	{
		Studio1("backgrounds/studio1.jpg"),
		Studio2("backgrounds/studio2.jpg"),
		Studio3("backgrounds/studio3.jpg"),
		SkyCloudy("backgrounds/sky-cloudy.jpg"),
		Amsterdam("backgrounds/amsterdam.jpg"),
		Path("backgrounds/path.png"),
		Sunny("backgrounds/sunny.jpg"),
		Sunset("backgrounds/sunset.jpg");

		public final String texturePath;

		private Backgrounds(String texturePath)
		{
			this.texturePath = texturePath;
		}
	}

	private final Map<String, Material> materials = Collections.synchronizedMap(new HashMap());

	public AssetManager()
	{
	}

	public void setMaterial(String id, Material material)
	{
		assert id != null;
		assert material != null;

		materials.put(id, material);
	}

	public Material getMaterial(String id)
	{
		assert id != null;

		Material material = materials.get(id);

		if (material == null) {
			throw new RuntimeException("Material " + id + " not mapped");
		}

		return material;
	}

	public Texture loadBackground(
		final Backgrounds backgroundId,
		final double rampUpBrightness,
		final double rampUpExponent,
		final double rampUpScale
	)
	{
		assert backgroundId != null;

		Texture background = loadTexture(backgroundId.texturePath);

		int size = background.data.data.length >> 2;
		Color temp = new Color();

		for (int i = 0; i < size; ++i) {

			temp = background.data.get(i, temp);

			double scale = Math.max(0.0, temp.getBrightness() - rampUpBrightness) * (1.0 / (1.0 - rampUpBrightness));
			scale = Math.pow(scale, rampUpExponent);
			scale *= rampUpScale;
			temp.addRGB(temp.copy().multiplyRGB(scale));

			background.data.set(i, temp);
		}

		return background;
	}

	public PbrMaterial loadPbrMaterial(Materials materialId, Vector3 textureScale)
	{
		assert materialId != null;

		return loadPbrMaterial(materialId.materialBasePath, materialId.ior, textureScale);
	}

	public PbrMaterial loadPbrMaterial(String materialBasePath, double ior, Vector3 textureScale)
	{
		assert materialBasePath != null;
		assert textureScale != null;

		Texture albedo = loadMaterialTexture(materialBasePath, "base");
		Texture roughness = loadMaterialTexture(materialBasePath, "roughness");
		Texture metalness = loadMaterialTexture(materialBasePath, "metalness");
		Texture emissive = loadMaterialTexture(materialBasePath, "emissive");
		Texture normal = loadMaterialTextureAsNormalMap(materialBasePath, "normal");

		return new PbrMaterial(
			albedo,
			roughness,
			metalness,
			normal,
			emissive,
			ior,
			textureScale
		);
	}

	private Texture loadMaterialTextureAsNormalMap( String materialBasePath,  String textureType)
	{
		assert materialBasePath != null;
		assert textureType != null;

		Texture normal = loadMaterialTexture(materialBasePath, textureType);

		if (normal != null) {
			//prepare normal map to contain proper normal vectors
			normal.data.multiply(2.0).subtract(1.0).normalize();
		}

		return normal;
	}

	private Texture loadMaterialTexture( String materialBasePath,  String textureType)
	{
		assert materialBasePath != null;
		assert textureType != null;

		//try load png
		if (canLoadTexture(materialBasePath + File.separator + textureType + ".png")) {
			return loadTexture(materialBasePath + File.separator + textureType + ".png");
		}

		//try load png
		if (canLoadTexture(materialBasePath + File.separator + textureType + ".jpg")) {
			return loadTexture(materialBasePath + File.separator + textureType + ".jpg");
		}

		return null;
	}

	public boolean canLoadTexture( String textureFile)
	{
		assert textureFile != null;

		return FileHelper.fileExists(Configuration.getBasePath() + File.separator + textureFile);
	}

	public Texture loadTextureAsNormalMap( String textureFile)
	{
		assert textureFile != null;

		Texture normal = loadTexture(textureFile);

		//prepare normal map to contain proper normal vectors
		normal.data.multiply(2.0).subtract(1.0);

		return normal;
	}

	public Texture loadTexture( String textureFile)
	{
		assert textureFile != null;

		BufferedImage image = FileHelper.getFileAsImage(Configuration.getBasePath() + File.separator + textureFile);
		Texture tex = new Texture(image);
		image.flush();
		return tex;
	}
}
