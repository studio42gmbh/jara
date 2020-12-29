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
public class Matrix3
{
	public final double[][] m = new double[3][3];

	public Matrix3()
	{
	}

	public Matrix3 textureUp()
	{
		//x
		m[0][0] = 1.0;
		m[0][1] = 0.0;
		m[0][2] = 0.0;

		//-z
		m[1][0] = 0.0;
		m[1][1] = 0.0;
		m[1][2] = -1.0;

		//y
		m[2][0] = 0.0;
		m[2][1] = 1.0;
		m[2][2] = 0.0;

		return this;
	}

	public Matrix3 textureDown()
	{
		//-x
		m[0][0] = -1.0;
		m[0][1] = 0.0;
		m[0][2] = 0.0;

		//z
		m[1][0] = 0.0;
		m[1][1] = 0.0;
		m[1][2] = 1.0;

		//-y
		m[2][0] = 0.0;
		m[2][1] = -1.0;
		m[2][2] = 0.0;

		return this;
	}

	public Matrix3 identity()
	{
		m[0][1] = m[0][2]
			= m[1][0] = m[1][2]
			= m[2][0] = m[2][1] = 0.0;
		m[0][0] = m[1][1] = m[2][2] = 1.0;

		return this;
	}

	public Matrix3 zero()
	{
		m[0][0] = m[0][1] = m[0][2]
			= m[1][0] = m[1][2] = m[1][1]
			= m[2][0] = m[2][1] = m[2][2] = 0.0;

		return this;
	}

	public Matrix3 transpose()
	{

		double tmp;

		tmp = m[1][0];
		m[1][0] = m[0][1];
		m[0][1] = tmp;
		tmp = m[2][0];
		m[2][0] = m[0][2];
		m[0][2] = tmp;
		tmp = m[1][2];
		m[1][2] = m[2][1];
		m[2][1] = tmp;

		return this;
	}

	public Matrix3 copy(Matrix3 other)
	{
		assert other != null;

		/*
		System.arraycopy(other.m[0], 0, m[0], 0, 3);
		System.arraycopy(other.m[1], 0, m[1], 0, 3);
		System.arraycopy(other.m[2], 0, m[2], 0, 3);
		 */
		m[0][0] = other.m[0][0];
		m[0][1] = other.m[0][1];
		m[0][2] = other.m[0][2];
		m[1][0] = other.m[1][0];
		m[1][1] = other.m[1][1];
		m[1][2] = other.m[1][2];
		m[2][0] = other.m[2][0];
		m[2][1] = other.m[2][1];
		m[2][2] = other.m[2][2];

		return this;
	}

	public void computeNormalMatrixFromDirection(Vector3 normal)
	{
		assert normal != null;

		//if the normal is to close to UP or BOTTOM -> we have to avoid the collapsing matrix
		double dot = normal.dot(Vector3.UP);
		if (dot > JaraMath.ONE_MINUS_EPSILON) {

			textureUp();
		}
		//BOTTOM
		else if (dot < -JaraMath.ONE_MINUS_EPSILON) {

			textureDown();
		}
		//create a orthogonal system from normal. tangent and bitangent
		else {

			Vector3 tangent
				= normal.copy()
					.cross(Vector3.UP)
					.normalize();

			Vector3 bitangent
				= normal.copy()
					.cross(tangent)
					.normalize().invert();

			setColumn(0, tangent);
			setColumn(1, bitangent);
			setColumn(2, normal);
		}
	}

	public Matrix3 setAll(
		final double m00, double m01, double m02,
		final double m10, double m11, double m12,
		final double m20, double m21, double m22
	)
	{
		m[0][0] = m00;
		m[0][1] = m01;
		m[0][2] = m02;
		m[1][0] = m10;
		m[1][1] = m11;
		m[1][2] = m12;
		m[2][0] = m20;
		m[2][1] = m21;
		m[2][2] = m22;

		return this;
	}

	public Matrix3 set(int x, int y, double value)
	{
		assert x >= 0 && x <= 2;
		assert y >= 0 && y <= 2;

		m[x][y] = value;

		return this;
	}

	public Matrix3 setColumn(int x, Vector3 column)
	{
		assert x >= 0 && x <= 2;
		assert column != null;

		m[x][0] = column.x;
		m[x][1] = column.y;
		m[x][2] = column.z;

		return this;
	}

	public Matrix3 setRow(int y, Vector3 row)
	{
		assert y >= 0 && y <= 2;
		assert row != null;

		m[0][y] = row.x;
		m[1][y] = row.y;
		m[2][y] = row.z;

		return this;
	}

	public Vector3 multiply(Vector3 vector)
	{
		assert vector != null;

		double tx = vector.x * m[0][0] + vector.y * m[1][0] + vector.z * m[2][0];
		double ty = vector.x * m[0][1] + vector.y * m[1][1] + vector.z * m[2][1];
		double tz = vector.x * m[0][2] + vector.y * m[1][2] + vector.z * m[2][2];

		vector.x = tx;
		vector.y = ty;
		vector.z = tz;

		return vector;
	}
}
