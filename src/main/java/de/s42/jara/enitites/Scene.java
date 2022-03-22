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
package de.s42.jara.enitites;

import de.s42.jara.Configuration;
import de.s42.jara.core.Color;
import de.s42.jara.core.JaraMath;
import de.s42.jara.core.spatial.SpatialPartitioning;
import de.s42.jara.core.Vector3;
import de.s42.jara.enitites.lights.DirectionalLight;
import de.s42.jara.enitites.lights.AmbientLight;
import de.s42.jara.materials.Texture;
import de.s42.jara.tracer.RayContext;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 *
 * @author Benjamin Schiller
 */
public class Scene extends Entity
{
	private Camera camera;
	private DirectionalLight directionalLight;
	private AmbientLight ambientLight;
	public final String name;
	private Texture backgroundTexture;
	private double backgroundTextureOffsetX;
	private double backgroundTextureOffsetY;
	private double backgroundTextureSmoothing;
	private double backgroundTextureSmoothingRender;
	public boolean showDirectBackground = true;
	private final Color backgroundColor = new Color();
	private final SpatialPartitioning spatial = new SpatialPartitioning();

	public Scene()
	{
		super(Vector3.ORIGIN);
		this.name = "Unnamed";
	}

	public Scene(String name)
	{
		super(Vector3.ORIGIN);

		assert name != null;

		this.name = name;
	}

	public void add(PhysicalEntity entity)
	{
		assert entity != null;

		spatial.add(entity);
	}

	public void add(List<? extends PhysicalEntity> entities)
	{
		assert entities != null;

		spatial.add(entities);
	}

	public void prepareForRendering()
	{
		spatial.prepareForRendering();

		//has to be done after spatial preparation
		if (Configuration.getCameraAutoFocus()) {
			getCamera().autoFocus(this);
		}
	}

	public boolean intersect(RayContext context)
	{
		return spatial.intersect(context);
	}

	public void background(RayContext context)
	{
		assert context != null;

		context.color.copy(backgroundColor);

		double backgroundJitterX = 0.0;
		double backgroundJitterY = 0.0;

		if (context.subSampleIndex == 0) {

			if (!showDirectBackground) {
				return;
			}

			if (backgroundTexture != null) {

				ThreadLocalRandom random = ThreadLocalRandom.current();

				//circular jitter
				double theta = random.nextDouble(-Math.PI, +Math.PI);
				double r = backgroundTextureSmoothing * random.nextDouble();
				backgroundJitterX = r * Math.cos(theta) / backgroundTexture.width;
				backgroundJitterY = r * Math.sin(theta) / backgroundTexture.height;
			}
		}
		else {
			if (backgroundTexture != null) {

				ThreadLocalRandom random = ThreadLocalRandom.current();

				//circular jitter
				double theta = random.nextDouble(-Math.PI, +Math.PI);
				double r = backgroundTextureSmoothingRender * random.nextDouble();
				backgroundJitterX = r * Math.cos(theta) / backgroundTexture.width;
				backgroundJitterY = r * Math.sin(theta) / backgroundTexture.height;
			}
		}

		if (backgroundTexture != null) {

			Vector3 texturePosition = new Vector3(
				Math.atan2(context.direction.z, context.direction.x) / JaraMath.PI2 + 0.5 + backgroundTextureOffsetX + backgroundJitterX,
				1.0 - (Math.asin(context.direction.y) / Math.PI + 0.5) + backgroundTextureOffsetY + backgroundJitterY,
				0.0
			);

			backgroundTexture.retrieveColor(context.color, texturePosition);
		}

		if (directionalLight != null) {

			double dot = (context.direction.dot(directionalLight.direction) + 1.0) * 0.5;

			//create a light disc
			if (dot > directionalLight.discSize) {
				dot = 1.0;
			}

			//ramp up colors according to light settings
			double scale = Math.max(0.0, dot - directionalLight.rampUpBrightness) * (1.0 / (1.0 - directionalLight.rampUpBrightness));
			scale = Math.pow(scale, directionalLight.rampUpExponent);
			scale *= directionalLight.rampUpScale;
			scale += Math.pow(dot, directionalLight.attenuationExponent);
			context.color.addRGB(directionalLight.color.copy().multiplyRGB(scale));
		}

		if (ambientLight != null) {

			context.color.add(ambientLight.color);
		}
	}

	public Camera getCamera()
	{
		return camera;
	}

	public void setCamera(Camera camera)
	{
		assert camera != null;

		this.camera = camera;
	}

	public DirectionalLight getDirectionalLight()
	{
		return directionalLight;
	}

	public void setDirectionalLight(DirectionalLight directionalLight)
	{
		this.directionalLight = directionalLight;
	}

	public AmbientLight getAmbientLight()
	{
		return ambientLight;
	}

	public void setAmbientLight(AmbientLight ambientLight)
	{
		this.ambientLight = ambientLight;
	}

	public boolean isShowDirectBackground()
	{
		return showDirectBackground;
	}

	public void setShowDirectBackground(boolean showDirectBackground)
	{
		this.showDirectBackground = showDirectBackground;
	}

	public Texture getBackgroundTexture()
	{
		return backgroundTexture;
	}

	public void setBackgroundTexture(Texture backgroundTexture)
	{
		this.backgroundTexture = backgroundTexture;
	}

	public double getBackgroundTextureOffsetX()
	{
		return backgroundTextureOffsetX;
	}

	public void setBackgroundTextureOffsetX(double backgroundTextureOffsetX)
	{
		this.backgroundTextureOffsetX = backgroundTextureOffsetX;
	}

	public Color getBackgroundColor()
	{
		return backgroundColor;
	}

	public void setBackgroundColor(Color backgroundColor)
	{
		assert backgroundColor != null;

		this.backgroundColor.copy(backgroundColor);
	}

	public double getBackgroundTextureSmoothing()
	{
		return backgroundTextureSmoothing;
	}

	public void setBackgroundTextureSmoothing(double backgroundTextureSmoothing)
	{
		this.backgroundTextureSmoothing = backgroundTextureSmoothing;
	}

	public double getBackgroundTextureSmoothingRender()
	{
		return backgroundTextureSmoothingRender;
	}

	public void setBackgroundTextureSmoothingRender(double backgroundTextureSmoothingRender)
	{
		this.backgroundTextureSmoothingRender = backgroundTextureSmoothingRender;
	}
}
