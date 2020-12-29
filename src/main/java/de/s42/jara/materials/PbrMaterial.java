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
package de.s42.jara.materials;

import de.s42.jara.core.Color;
import de.s42.jara.core.Vector3;
import de.s42.jara.tracer.RayContext;

/**
 *
 * @author Benjamin.Schiller
 */
public class PbrMaterial extends Material
{
	public Texture albedoTexture;
	public Texture roughnessTexture;
	public Texture metalnessTexture;
	public Texture normalTexture;
	public Texture emissiveTexture;

	private final Vector3 textureScale;

	public PbrMaterial(Texture albedo, Texture roughness, Texture metalness, Texture normal, Texture emissive, double ior, Vector3 textureScale)
	{
		super(Color.Black, Color.Black, 0.0, 0.0, ior);

		this.albedoTexture = albedo;
		this.roughnessTexture = roughness;
		this.metalnessTexture = metalness;
		this.normalTexture = normal;
		this.emissiveTexture = emissive;
		this.textureScale = textureScale.copy();
	}

	@Override
	public Color computeAlbedo(RayContext context)
	{
		assert context != null;

		if (albedoTexture == null) {
			return albedo;
		}

		return albedoTexture.retrieveColor(new Color(), context.texturePosition.copy().multiply(this.textureScale));
	}

	@Override
	public Color computeEmissive(RayContext context)
	{
		assert context != null;

		if (emissiveTexture == null) {
			return emissive;
		}

		return emissiveTexture.retrieveColor(new Color(), context.texturePosition.copy().multiply(this.textureScale));
	}

	@Override
	public double computeRoughness(RayContext context)
	{
		assert context != null;

		if (roughnessTexture == null) {
			return roughness;
		}

		return roughnessTexture.retrieveColor(new Color(), context.texturePosition.copy().multiply(this.textureScale)).getR();
	}

	@Override
	public double computeMetalness(RayContext context)
	{
		assert context != null;

		if (metalnessTexture == null) {
			return metalness;
		}

		return metalnessTexture.retrieveColor(new Color(), context.texturePosition.copy().multiply(this.textureScale)).getG();
	}

	@Override
	public Vector3 computeNormal(RayContext context)
	{
		assert context != null;

		if (normalTexture == null) {
			return context.direction.copy();
		}

		Vector3 textureNormal = /*new Vector3(0,0,1);//*/ normalTexture.retrieveVector3(new Vector3(), context.texturePosition.copy().multiply(this.textureScale));

		return context.normalMatrix.multiply(textureNormal);
	}
}
