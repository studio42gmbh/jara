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
import de.s42.jara.materials.Texture;
import de.s42.jara.prefabs.House;

/**
 *
 * @author Benjamin Schiller
 */
public class HouseStill implements SceneLoader
{

	@Override
	public Scene loadScene(AssetManager assets)
	{
		assert assets != null;

		Scene scene = new Scene("Chair Still");

		Texture background = assets.loadBackground(
			AssetManager.Backgrounds.Sunset,
			0.9, //rampUpBrightness
			5.0, //rampUpExponent
			2.0 //rampUpScale
		);
		scene.setBackgroundTextureSmoothing(100.0);
		scene.setBackgroundTextureSmoothingRender(1.0);
		scene.setBackgroundTexture(background);
		scene.setShowDirectBackground(true);
		scene.setBackgroundTextureOffsetX(0.36);
		scene.setBackgroundColor(new Color(0.05, 0.05, 0.05, 0.0));

		Camera camera = new Camera(
			new Vector3(30.0, 14.0, -23.0),
			new Vector3(1.0, 3.45, 0.0),
			Vector3.UP,
			Math.toRadians(30),
			(double) Configuration.getWidth() / (double) Configuration.getHeight()
		);
		scene.setCamera(camera);

		House.load(scene, assets, Vector3.ORIGIN, new Vector3(0.55f));

		return scene;
	}
}
