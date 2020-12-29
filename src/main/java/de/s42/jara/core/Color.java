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

import java.util.concurrent.ThreadLocalRandom;

/**
 *
 * @author Benjamin.Schiller
 */
public class Color
{
	public double r, g, b, a;

	public final static Color Black = new Color(0.0, 0.0, 0.0, 1.0);
	public final static Color White = new Color(1.0, 1.0, 1.0, 1.0);
	public final static Color TransparentBlack = new Color(0.0, 0.0, 0.0, 0.0);
	public final static Color TransparentWhite = new Color(1.0, 1.0, 1.0, 0.0);

	public final static double ONE_DIVIDED_BY_255 = 1.0 / 255.0;

	public static Color createRandomColorRGB()
	{
		ThreadLocalRandom random = ThreadLocalRandom.current();
		return new Color(
			random.nextDouble(),
			random.nextDouble(),
			random.nextDouble(),
			1.0
		);
	}

	public static Color createRandomColor()
	{
		ThreadLocalRandom random = ThreadLocalRandom.current();
		return new Color(
			random.nextDouble(),
			random.nextDouble(),
			random.nextDouble(),
			random.nextDouble()
		);
	}

	public Color randomColor()
	{
		ThreadLocalRandom random = ThreadLocalRandom.current();
		r = random.nextDouble();
		g = random.nextDouble();
		b = random.nextDouble();
		a = random.nextDouble();

		return this;
	}

	public static Vector3 convertToVector(Vector3 result, int argb)
	{
		assert result != null;

		result.z = ((ONE_DIVIDED_BY_255 * (double) (argb & 0xFF)) * 2.0 - 1.0);
		argb = argb >> 8;
		result.y = ((ONE_DIVIDED_BY_255 * (double) (argb & 0xFF)) * 2.0 - 1.0);
		argb = argb >> 8;
		result.x = (ONE_DIVIDED_BY_255 * (double) (argb & 0xFF)) * 2.0 - 1.0;

		return result;
	}

	public Color()
	{
		r = 0.0;
		g = 0.0;
		b = 0.0;
		a = 1.0;
	}

	public Color(double r, double g, double b)
	{
		this.r = r;
		this.g = g;
		this.b = b;
		this.a = 1.0;
	}

	public Color(double r, double g, double b, double a)
	{
		this.r = r;
		this.g = g;
		this.b = b;
		this.a = a;
	}

	public Color(Color toCopy)
	{
		assert toCopy != null;

		r = toCopy.r;
		g = toCopy.g;
		b = toCopy.b;
		a = toCopy.a;
	}

	public Color(int argb)
	{
		b = ONE_DIVIDED_BY_255 * (double) (argb & 0xFF);
		argb = argb >> 8;
		g = ONE_DIVIDED_BY_255 * (double) (argb & 0xFF);
		argb = argb >> 8;
		r = ONE_DIVIDED_BY_255 * (double) (argb & 0xFF);
		argb = argb >> 8;
		a = ONE_DIVIDED_BY_255 * (double) (argb & 0xFF);
	}

	public int getARGB8()
	{
		assert r >= 0.0 && r <= 1.0;
		assert g >= 0.0 && g <= 1.0;
		assert b >= 0.0 && b <= 1.0;
		assert a >= 0.0 && a <= 1.0;

		int ir = (int) (r * 255.0 + 0.5);
		int ig = (int) (g * 255.0 + 0.5);
		int ib = (int) (b * 255.0 + 0.5);
		int ia = (int) (a * 255.0 + 0.5);

		return (ia << 24) | (ir << 16) | (ig << 8) | ib;
	}

	public int getScaledARGB8(double scale)
	{
		double scale255 = scale * 255.0;
		int ir = Math.min(Math.max(0, (int) (r * scale255 + 0.5)), 255);
		int ig = Math.min(Math.max(0, (int) (g * scale255 + 0.5)), 255);
		int ib = Math.min(Math.max(0, (int) (b * scale255 + 0.5)), 255);
		int ia = Math.min(Math.max(0, (int) (a * scale255 + 0.5)), 255);

		return (ia << 24) | (ir << 16) | (ig << 8) | ib;
	}

	public int getScaledRGB8(double scale)
	{
		double scale255 = scale * 255.0;
		int ir = Math.min(Math.max(0, (int) (r * scale255 + 0.5)), 255);
		int ig = Math.min(Math.max(0, (int) (g * scale255 + 0.5)), 255);
		int ib = Math.min(Math.max(0, (int) (b * scale255 + 0.5)), 255);

		return (255 << 24) | (ir << 16) | (ig << 8) | ib;
	}

	public Color set(int argb)
	{
		b = ONE_DIVIDED_BY_255 * (double) (argb & 0xFF);
		argb = argb >> 8;
		g = ONE_DIVIDED_BY_255 * (double) (argb & 0xFF);
		argb = argb >> 8;
		r = ONE_DIVIDED_BY_255 * (double) (argb & 0xFF);
		argb = argb >> 8;
		a = ONE_DIVIDED_BY_255 * (double) (argb & 0xFF);
		return this;
	}

	public Color set(double r, double g, double b)
	{
		this.r = r;
		this.g = g;
		this.b = b;
		this.a = 1.0;

		return this;
	}

	public Color set(double r, double g, double b, double a)
	{
		this.r = r;
		this.g = g;
		this.b = b;
		this.a = a;

		return this;
	}

	public Color copy(Color other)
	{
		assert other != null;

		r = other.r;
		g = other.g;
		b = other.b;
		a = other.a;

		return this;
	}

	public Color copy(Vector3 other)
	{
		assert other != null;

		r = other.x;
		g = other.y;
		b = other.z;

		return this;
	}

	public Color copy()
	{
		return new Color(this);
	}

	public Color copyRGB(Color other)
	{
		assert other != null;

		r = other.r;
		g = other.g;
		b = other.b;

		return this;
	}

	public Color add(Color other)
	{
		assert other != null;

		r += other.r;
		g += other.g;
		b += other.b;
		a += other.a;

		return this;
	}

	public Color add(double value)
	{
		r += value;
		g += value;
		b += value;
		a += value;

		return this;
	}

	public Color addRGB(Color other)
	{
		assert other != null;

		r += other.r;
		g += other.g;
		b += other.b;

		return this;
	}

	public Color addRGB(double value)
	{
		r += value;
		g += value;
		b += value;

		return this;
	}

	public Color addSoftRGB(Color other)
	{
		assert other != null;

		r = Math.max(r, other.r);
		g = Math.max(g, other.g);
		b = Math.max(b, other.b);

		return this;
	}

	public Color subtract(Color other)
	{
		assert other != null;

		r -= other.r;
		g -= other.g;
		b -= other.b;
		a -= other.a;

		return this;
	}

	public Color subtract(double value)
	{
		r -= value;
		g -= value;
		b -= value;
		a -= value;

		return this;
	}

	public Color subtractRGB(Color other)
	{
		assert other != null;

		r -= other.r;
		g -= other.g;
		b -= other.b;

		return this;
	}

	public Color subtractRGB(double value)
	{
		r -= value;
		g -= value;
		b -= value;

		return this;
	}

	public Color multiplyRGB(double scalar)
	{
		r *= scalar;
		g *= scalar;
		b *= scalar;

		return this;
	}

	public Color multiply(double scalar)
	{
		r *= scalar;
		g *= scalar;
		b *= scalar;
		a *= scalar;

		return this;
	}

	public Color divideRGB(double scalar)
	{
		assert scalar != 0.0;

		r /= scalar;
		g /= scalar;
		b /= scalar;

		return this;
	}

	public Color divide(double scalar)
	{
		assert scalar != 0.0;

		r /= scalar;
		g /= scalar;
		b /= scalar;
		a /= scalar;

		return this;
	}

	public Color multiply(Color other)
	{
		assert other != null;

		r *= other.r;
		g *= other.g;
		b *= other.b;
		a *= other.a;

		return this;
	}

	public Color multiplyRGB(Color other)
	{
		assert other != null;

		r *= other.r;
		g *= other.g;
		b *= other.b;

		return this;
	}

	public Color powRGB(double exponent)
	{
		r = Math.pow(r, exponent);
		g = Math.pow(g, exponent);
		b = Math.pow(b, exponent);

		return this;
	}

	public Color clamp()
	{
		r = JaraMath.saturate(r);
		g = JaraMath.saturate(g);
		b = JaraMath.saturate(b);
		a = JaraMath.saturate(a);

		return this;
	}

	public Color min(double min)
	{
		r = Math.min(r, min);
		g = Math.min(g, min);
		b = Math.min(b, min);
		a = Math.min(a, min);

		return this;
	}

	public Color max(double max)
	{
		r = Math.max(r, max);
		g = Math.max(g, max);
		b = Math.max(b, max);
		a = Math.max(a, max);

		return this;
	}

	public Color blend(Color other, double blend)
	{
		assert other != null;
		assert blend >= 0.0 && blend <= 1.0;

		double iblend = 1.0 - blend;
		r = r * iblend + other.r * blend;
		g = g * iblend + other.g * blend;
		b = b * iblend + other.b * blend;
		a = a * iblend + other.a * blend;

		return this;
	}

	public Color blendRGB(Color other, double blend)
	{
		assert other != null;
		assert blend >= 0.0 && blend <= 1.0;

		double iblend = 1.0 - blend;
		r = r * iblend + other.r * blend;
		g = g * iblend + other.g * blend;
		b = b * iblend + other.b * blend;

		return this;
	}

	public double getBrightness()
	{
		return (r + g + b) * (1.0 / 3.0);
	}

	public double getR()
	{
		return r;
	}

	public double getRClamped()
	{
		return JaraMath.saturate(r);
	}

	public void setR(double r)
	{
		this.r = r;
	}

	public double getG()
	{
		return g;
	}

	public double getGClamped()
	{
		return JaraMath.saturate(g);
	}

	public void setG(double g)
	{
		this.g = g;
	}

	public double getB()
	{
		return b;
	}

	public double getBClamped()
	{
		return JaraMath.saturate(b);
	}

	public void setB(double b)
	{
		this.b = b;
	}

	public double getA()
	{
		return a;
	}

	public double getAClamped()
	{
		return JaraMath.saturate(a);
	}

	public void setA(double a)
	{
		this.a = a;
	}

	public Color setRGBZero()
	{
		r = 0.0;
		g = 0.0;
		b = 0.0;
		return this;
	}

	public Color setZero()
	{
		r = 0.0;
		g = 0.0;
		b = 0.0;
		a = 0.0;

		return this;
	}

	public java.awt.Color toAwtColor()
	{
		return new java.awt.Color((float) getRClamped(), (float) getGClamped(), (float) getBClamped(), (float) getAClamped());
	}

	@Override
	public String toString()
	{
		return "[ r: " + r + ", g: " + g + ", b: " + b + ", a: " + a + " ]";
	}
}
