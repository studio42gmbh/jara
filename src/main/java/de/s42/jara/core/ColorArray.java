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
package de.s42.jara.core;

import static de.s42.jara.core.Color.ONE_DIVIDED_BY_255;

/**
 *
 * @author Benjamin Schiller
 */
public class ColorArray
{
	//contains r,g,b,a tupels
	public final double[] data;

	public ColorArray(int size)
	{
		assert size > 0;

		data = new double[size << 2];
	}

	public void setARGB8(int position, int argb)
	{
		assert position >= 0 && position < data.length >> 2;

		int dataPos = position << 2;

		data[dataPos] = ONE_DIVIDED_BY_255 * (double) (argb & 0xFF);
		argb = argb >> 8;
		data[dataPos + 1] = ONE_DIVIDED_BY_255 * (double) (argb & 0xFF);
		argb = argb >> 8;
		data[dataPos + 2] = ONE_DIVIDED_BY_255 * (double) (argb & 0xFF);
		argb = argb >> 8;
		data[dataPos + 3] = ONE_DIVIDED_BY_255 * (double) (argb & 0xFF);
	}

	public void setARGB8(int startPosition, int length, int[] argbArray)
	{
		assert startPosition >= 0 && startPosition < data.length >> 2;
		assert startPosition + length >= 0 && startPosition + length <= data.length >> 2;
		assert argbArray != null;

		int startPos = startPosition << 2;
		int endPos = (startPosition + length) << 2;

		for (int i = startPos; i < endPos; i += 4) {

			int argb = argbArray[i >> 2];

			data[i] = ONE_DIVIDED_BY_255 * (double) (argb & 0xFF);
			argb = argb >> 8;
			data[i + 1] = ONE_DIVIDED_BY_255 * (double) (argb & 0xFF);
			argb = argb >> 8;
			data[i + 2] = ONE_DIVIDED_BY_255 * (double) (argb & 0xFF);
			argb = argb >> 8;
			data[i + 3] = ONE_DIVIDED_BY_255 * (double) (argb & 0xFF);
		}
	}

	public ColorArray set(int position, double scalar)
	{
		assert position >= 0 && position < data.length >> 2;

		int dataPos = position << 2;

		data[dataPos] = scalar;
		data[dataPos + 1] = scalar;
		data[dataPos + 2] = scalar;
		data[dataPos + 3] = scalar;

		return this;
	}

	public ColorArray set(int position, double r, double g, double b, double a)
	{
		assert position >= 0 && position < data.length >> 2;

		int dataPos = position << 2;

		data[dataPos] = b;
		data[dataPos + 1] = g;
		data[dataPos + 2] = r;
		data[dataPos + 3] = a;

		return this;
	}

	public ColorArray set(int position, Color color)
	{
		assert position >= 0 && position < data.length >> 2;
		assert color != null;

		int dataPos = position << 2;

		data[dataPos] = color.b;
		data[dataPos + 1] = color.g;
		data[dataPos + 2] = color.r;
		data[dataPos + 3] = color.a;

		return this;
	}

	public Color get(int position, Color result)
	{
		assert position >= 0 && position < data.length >> 2;
		assert result != null;

		int dataPos = position << 2;

		result.b = data[dataPos];
		result.g = data[dataPos + 1];
		result.r = data[dataPos + 2];
		result.a = data[dataPos + 3];

		return result;
	}

	public Vector3 get(int position, Vector3 result)
	{
		assert position >= 0 && position < data.length >> 2;
		assert result != null;

		int dataPos = position << 2;

		result.z = data[dataPos];
		result.y = data[dataPos + 1];
		result.x = data[dataPos + 2];

		return result;
	}

	public int getScaledRGB8(int position, double scale)
	{
		assert position >= 0 && position < data.length >> 2;

		int dataPos = position << 2;

		double scale255 = scale * 255.0;
		int ir = JaraMath.clamp((int) (data[dataPos + 2] * scale255 + 0.5), 0, 255);
		int ig = JaraMath.clamp((int) (data[dataPos + 1] * scale255 + 0.5), 0, 255);
		int ib = JaraMath.clamp((int) (data[dataPos] * scale255 + 0.5), 0, 255);

		return (255 << 24) | (ir << 16) | (ig << 8) | ib;
	}

	public int getScaledARGB8(int position, double scale)
	{
		assert position >= 0 && position < data.length >> 2;

		int dataPos = position << 2;

		double scale255 = scale * 255.0;
		int ia = JaraMath.clamp((int) (data[dataPos + 3] * scale255 + 0.5), 0, 255);
		int ir = JaraMath.clamp((int) (data[dataPos + 2] * scale255 + 0.5), 0, 255);
		int ig = JaraMath.clamp((int) (data[dataPos + 1] * scale255 + 0.5), 0, 255);
		int ib = JaraMath.clamp((int) (data[dataPos] * scale255 + 0.5), 0, 255);

		return (ia << 24) | (ir << 16) | (ig << 8) | ib;
	}

	public double getColorVectorLength(int position)
	{
		assert position >= 0 && position < data.length >> 2;

		int dataPos = position << 2;

		double r = data[dataPos + 2];
		double g = data[dataPos + 1];
		double b = data[dataPos];

		return Math.sqrt(r * r + g * g + b * b);
	}

	public ColorArray multiply(int position, double scalar)
	{
		assert position >= 0 && position < data.length >> 2;

		int dataPos = position << 2;

		data[dataPos + 3] *= scalar;
		data[dataPos + 2] *= scalar;
		data[dataPos + 1] *= scalar;
		data[dataPos] *= scalar;

		return this;
	}

	public ColorArray multiply(double scalar)
	{
		for (int i = 0; i < data.length; ++i) {
			data[i] *= scalar;
		}

		return this;
	}

	public ColorArray divide(double scalar)
	{
		for (int i = 0; i < data.length; ++i) {
			data[i] /= scalar;
		}

		return this;
	}

	public ColorArray subtract(double scalar)
	{
		for (int i = 0; i < data.length; ++i) {
			data[i] -= scalar;
		}

		return this;
	}

	public ColorArray add(double scalar)
	{
		for (int i = 0; i < data.length; ++i) {
			data[i] += scalar;
		}

		return this;
	}

	public ColorArray normalize()
	{
		for (int i = 0; i < data.length >> 2; ++i) {
			multiply(i, getColorVectorLength(i));
		}

		return this;
	}

	public ColorArray add(int position, Color color)
	{
		assert position >= 0 && position < data.length >> 2;
		assert color != null;

		int dataPos = position << 2;

		data[dataPos] += color.b;
		data[dataPos + 1] += color.g;
		data[dataPos + 2] += color.r;
		data[dataPos + 3] += color.a;

		return this;
	}
}
