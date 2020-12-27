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
package de.s42.jara.enitites.lights;

import de.s42.jara.core.Color;
import de.s42.jara.core.Vector3;

/**
 *
 * @author Benjamin.Schiller
 */
public class DirectionalLight extends Light
{
	public final Vector3 direction;
	public final double discSize;
	public final double rampUpBrightness;
	public final double rampUpExponent;
	public final double rampUpScale;
	public final double attenuationExponent;

	public DirectionalLight(
		final Vector3 direction,
		final Color color,
		final double discSize,
		final double rampUpBrightness,
		final double rampUpExponent,
		final double rampUpScale,
		final double attenuationExponent
	)
	{
		super(color);

		assert direction != null;

		this.direction = direction.copy().normalize();
		this.discSize = discSize;
		this.rampUpBrightness = rampUpBrightness;
		this.rampUpExponent = rampUpExponent;
		this.rampUpScale = rampUpScale;
		this.attenuationExponent = attenuationExponent;
	}
}
