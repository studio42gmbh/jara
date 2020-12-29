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
import de.s42.jara.tracer.RayContext;

/**
 *
 * @author Benjamin.Schiller
 */
public class RainbowMaterial extends Material
{
	public final double scale;
	public final double amplitude;

	public RainbowMaterial(Color emissive, double metalness, double roughness, double ior, double scale, double amplitude)
	{
		super(emissive, Color.Black, metalness, roughness, ior);

		this.scale = scale;
		this.amplitude = amplitude;
	}

	@Override
	public Color computeEmissive(RayContext context)
	{
		assert context != null;

		return computeAlbedo(context).multiplyRGB(emissive);
	}

	@Override
	public Color computeAlbedo(RayContext context)
	{
		assert context != null;

		return new Color(
			Math.abs(Math.sin(context.origin.x * scale) * amplitude) + (1.0 - amplitude),
			Math.abs(Math.cos(context.origin.y * scale) * amplitude) + (1.0 - amplitude),
			Math.abs(Math.cos(context.origin.z * scale) * amplitude) + (1.0 - amplitude)
		);
	}
}
