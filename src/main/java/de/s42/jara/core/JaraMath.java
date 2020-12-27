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

/**
 *
 * @author Benjamin.Schiller
 */
public final class JaraMath
{
	public static final double EPSILON = 0.000000001;
	public static final double ONE_MINUS_EPSILON = 1.0 - EPSILON;
	public static final double ONE_PLUS_EPSILON = 1.0 + EPSILON;
	public static final double PI2 = Math.PI * 2.0;
	public static final double PIHALF = Math.PI * 0.5;

	private JaraMath()
	{
	}

	public static int clamp(int value, int min, int max)
	{
		assert min < max;

		return (value < min) ? min : (value > max) ? max : value;
	}

	public static double clamp(double value, double min, double max)
	{
		assert min < max;

		return (value < min) ? min : (value > max) ? max : value;
	}

	public static double saturate(double value)
	{
		return clamp(value, 0.0, 1.0);
	}
}
