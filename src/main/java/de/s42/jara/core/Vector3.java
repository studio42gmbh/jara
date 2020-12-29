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
public class Vector3
{
	public double x, y, z;

	public final static Vector3 ZERO = new Vector3(0.0);
	public final static Vector3 ONE = new Vector3(1.0);

	public final static Vector3 ORIGIN = new Vector3(0.0);

	public final static Vector3 UP = new Vector3(0.0, 1.0, 0.0);
	public final static Vector3 DOWN = new Vector3(0.0, -1.0, 0.0);

	public final static Vector3 RIGHT = new Vector3(1.0, 0.0, 0.0);
	public final static Vector3 LEFT = new Vector3(-1.0, 0.0, 0.0);

	public final static Vector3 FRONT = new Vector3(0.0, 0.0, -1.0);
	public final static Vector3 BACK = new Vector3(0.0, 0.0, 1.0);

	public Vector3()
	{
	}

	public Vector3(double x, double y, double z)
	{
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public Vector3(double scalar)
	{
		this.x = scalar;
		this.y = scalar;
		this.z = scalar;
	}

	public Vector3(Vector3 toCopy)
	{
		assert toCopy != null;

		x = toCopy.x;
		y = toCopy.y;
		z = toCopy.z;
	}

	public static Vector3 createNormalizedRandomVector()
	{
		ThreadLocalRandom random = ThreadLocalRandom.current();
		return new Vector3(
			random.nextDouble(-1.0, 1.0),
			random.nextDouble(-1.0, 1.0),
			random.nextDouble(-1.0, 1.0)
		).normalize();
	}

	public Vector3 normalizedRandomVector()
	{
		ThreadLocalRandom random = ThreadLocalRandom.current();
		x = random.nextDouble(-1.0, 1.0);
		y = random.nextDouble(-1.0, 1.0);
		z = random.nextDouble(-1.0, 1.0);

		return this;
	}

	/**
	 * Create uniformly distributed random vector on sphere slice
	 *
	 * @param normal
	 * @param spreadAngle has to be between 0.0 and 1.0
	 * @return
	 */
	public static Vector3 createSphereRandomVector(Vector3 normal, double spreadAngle)
	{
		assert normal != null;
		assert normal.isUnitVector();
		assert spreadAngle >= 0.0;
		assert spreadAngle <= Math.PI;

		//no spread -> return vector itself
		if (spreadAngle < JaraMath.EPSILON) {
			return normal.copy();
		}

		ThreadLocalRandom random = ThreadLocalRandom.current();

		Vector3 different = Math.abs(normal.x) < 0.5 ? new Vector3(1.0, 0.0, 0.0) : new Vector3(1.0, 1.0, 0.0);

		Vector3 b1 = different.cross(normal).normalize();

		Vector3 b2 = b1.copy().cross(normal);

		double z = random.nextDouble(Math.cos(spreadAngle), 1.0);

		double r = Math.sqrt(1.0 - z * z);

		double theta = random.nextDouble(-Math.PI, +Math.PI);

		double x = r * Math.cos(theta);

		double y = r * Math.sin(theta);

		b1.multiply(x);
		b2.multiply(y);
		return normal
			.copy()
			.multiply(z)
			.add(b2)
			.add(b1)
			.normalize();
	}

	/**
	 * Creates a ring around a given value where 1.0 = normal 0.5 = orthogonal 0.0 = opposite
	 *
	 * @param normal
	 * @param spread has to be between 0.0 and 1.0
	 * @return
	 */
	public static Vector3 createSphereRingRandomVector(Vector3 normal, double spread)
	{
		assert normal != null;
		assert normal.isUnitVector();
		assert spread >= 0.0;
		assert spread <= 1.0;

		//spread ~ 1.0 -> return vector itself
		if (spread > JaraMath.ONE_MINUS_EPSILON) {
			return normal.copy();
		}

		//spread ~ 0.0 -> return inverted vector
		if (spread < JaraMath.EPSILON) {
			return normal.copy().invert();
		}

		ThreadLocalRandom random = ThreadLocalRandom.current();

		Vector3 different = Math.abs(normal.x) < 0.5 ? new Vector3(1.0, 0.0, 0.0) : new Vector3(1.0, 1.0, 0.0);

		Vector3 b1 = different.cross(normal).normalize();

		Vector3 b2 = b1.copy().cross(normal);

		double z = spread;

		double r = Math.sqrt(1.0 - z * z);

		double theta = random.nextDouble(-Math.PI, +Math.PI);

		double x = r * Math.cos(theta);

		double y = r * Math.sin(theta);

		b1.multiply(x);
		b2.multiply(y);
		return normal
			.copy()
			.multiply(z)
			.add(b2)
			.add(b1)
			.normalize();
	}

	/**
	 * Create uniformly distributed random vector on sphere slice
	 *
	 * @param normal
	 * @param spreadAngle has to be between 0.0 and 1.0
	 * @return
	 */
	public static Vector3 createSphereGaussianVector(Vector3 normal, double spreadAngle)
	{
		assert normal != null;
		assert normal.isUnitVector();
		assert spreadAngle >= 0.0;
		assert spreadAngle <= Math.PI;

		//no spread -> return vector itself
		if (spreadAngle < JaraMath.EPSILON) {
			return normal.copy();
		}

		ThreadLocalRandom random = ThreadLocalRandom.current();

		Vector3 different = Math.abs(normal.x) < 0.5 ? new Vector3(1.0, 0.0, 0.0) : new Vector3(1.0, 1.0, 0.0);

		Vector3 b1 = different.cross(normal).normalize();

		Vector3 b2 = b1.copy().cross(normal);

		//double z = random.nextDouble(Math.cos(spreadAngle), 1.0);
		double g = Math.min(0.9999999, Math.abs(random.nextGaussian()));
		double z = random.nextDouble(Math.cos(spreadAngle * g) - 0.0000001, 1.0);

		double r = Math.sqrt(1.0 - z * z);

		double theta = random.nextDouble(-Math.PI, +Math.PI);

		double x = r * Math.cos(theta);

		double y = r * Math.sin(theta);

		b1.multiply(x);
		b2.multiply(y);
		return normal
			.copy()
			.multiply(z)
			.add(b2)
			.add(b1)
			.normalize();
	}

	/**
	 * Simple randombased implementation of creating a randomly distributed hemisphere vector is a bit faster than more
	 * generic createSphereRandomVector(normal, Math.PI * 0.5)
	 *
	 * @param normal
	 * @return
	 */
	public static Vector3 createHemisphereRandomVector(Vector3 normal)
	{
		assert normal != null;
		assert normal.isUnitVector();

		ThreadLocalRandom random = ThreadLocalRandom.current();
		Vector3 result = new Vector3(
			random.nextDouble(-1.0, 1.0),
			random.nextDouble(-1.0, 1.0),
			random.nextDouble(-1.0, 1.0)
		);

		while (true) {

			if (result.dot(normal) > 0.0) {
				return result.normalize();
			}

			result.set(
				random.nextDouble(-1.0, 1.0),
				random.nextDouble(-1.0, 1.0),
				random.nextDouble(-1.0, 1.0)
			);
		}
	}

	public Vector3 set(double x, double y, double z)
	{
		this.x = x;
		this.y = y;
		this.z = z;

		return this;
	}

	public boolean isUnitVector()
	{
		double l = squaredLength();
		return (l > JaraMath.ONE_MINUS_EPSILON && l < JaraMath.ONE_PLUS_EPSILON);
	}

	public Vector3 copy()
	{
		return new Vector3(this);
	}

	public Vector3 copy(Vector3 toCopy)
	{
		assert toCopy != null;

		x = toCopy.x;
		y = toCopy.y;
		z = toCopy.z;

		return this;
	}

	public Vector3 subtract(Vector3 other)
	{
		assert other != null;

		x -= other.x;
		y -= other.y;
		z -= other.z;

		return this;
	}

	public Vector3 subtract(double sub)
	{
		x -= sub;
		y -= sub;
		z -= sub;

		return this;
	}

	public Vector3 add(Vector3 other)
	{
		assert other != null;

		x += other.x;
		y += other.y;
		z += other.z;

		return this;
	}

	public Vector3 add(double add)
	{
		x += add;
		y += add;
		z += add;

		return this;
	}

	public Vector3 add(double x, double y, double z)
	{
		this.x += x;
		this.y += y;
		this.z += z;

		return this;
	}

	public Vector3 normalize()
	{
		double l = length();

		assert (l > JaraMath.ONE_MINUS_EPSILON && l < JaraMath.ONE_PLUS_EPSILON);

		double il = 1.0 / l;

		x *= il;
		y *= il;
		z *= il;

		return this;
	}

	public Vector3 fixNullLengthVector(double x, double y, double z)
	{
		//if the vector is null length -> assign the given vector
		if (squaredLength() < JaraMath.EPSILON) {
			this.x = x;
			this.y = y;
			this.z = z;
		}

		return this;
	}

	public Vector3 invert()
	{
		x = -x;
		y = -y;
		z = -z;

		return this;
	}

	public Vector3 lerp(Vector3 other, double blend)
	{
		assert other != null;
		assert blend >= 0.0 && blend <= 1.0;

		double iblend = 1.0 - blend;
		x = x * iblend + other.x * blend;
		y = y * iblend + other.y * blend;
		z = z * iblend + other.z * blend;

		return this;
	}

	public Vector3 reflect(Vector3 normal)
	{
		assert normal != null;

		return subtract(normal.copy().multiply(2.0 * dot(normal)));
	}

	public Vector3 refract(Vector3 normal, double ior)
	{
		assert normal != null;

		double dotNormal = dot(normal);

		double k = 1.0 - ior * ior * (1.0 - dotNormal * dotNormal);

		if (k < 0.0) {
			set(0.0, 0.0, 0.0);
		}
		else {
			multiply(ior).subtract(normal.copy().multiply(ior * dotNormal + Math.sqrt(k)));
		}

		return this;
	}

	public double length()
	{
		return Math.sqrt(x * x + y * y + z * z);
	}

	public double squaredLength()
	{
		return x * x + y * y + z * z;
	}

	public Vector3 reciproc()
	{
		x = 1.0 / x;
		y = 1.0 / y;
		z = 1.0 / z;

		return this;
	}

	public double dot(Vector3 other)
	{
		assert other != null;

		return x * other.x + y * other.y + z * other.z;
	}

	public Vector3 cross(Vector3 other)
	{
		assert other != null;

		double tx = y * other.z - z * other.y;
		double ty = z * other.x - x * other.z;
		double tz = x * other.y - y * other.x;

		x = tx;
		y = ty;
		z = tz;

		return this;
	}

	public double distanceSquared(Vector3 other)
	{
		assert other != null;

		double dx = x - other.x;
		double dy = y - other.y;
		double dz = z - other.z;

		return dx * dx + dy * dy * dz * dz;
	}

	public double distance(Vector3 other)
	{
		assert other != null;

		return Math.sqrt(distanceSquared(other));
	}

	public Vector3 multiply(double scalar)
	{
		x *= scalar;
		y *= scalar;
		z *= scalar;

		return this;
	}

	public Vector3 multiply(Vector3 scalar)
	{
		x *= scalar.x;
		y *= scalar.y;
		z *= scalar.z;

		return this;
	}

	public Vector3 divide(double scalar)
	{
		assert scalar != 0.0;

		x /= scalar;
		y /= scalar;
		z /= scalar;

		return this;
	}

	public Vector3 divide(Vector3 other)
	{
		assert other != null;
		assert other.x != 0.0;
		assert other.y != 0.0;
		assert other.z != 0.0;

		x /= other.x;
		y /= other.y;
		z /= other.z;

		return this;
	}

	public Vector3 min(Vector3 other)
	{
		assert other != null;

		x = Math.min(x, other.x);
		y = Math.min(y, other.y);
		z = Math.min(z, other.z);

		return this;
	}

	public Vector3 max(Vector3 other)
	{
		assert other != null;

		x = Math.max(x, other.x);
		y = Math.max(y, other.y);
		z = Math.max(z, other.z);

		return this;
	}

	public boolean smaller(Vector3 other)
	{
		assert other != null;

		return x < other.x
			&& y < other.y
			&& z < other.z;
	}

	public boolean bigger(Vector3 other)
	{
		assert other != null;

		return x > other.x
			&& y > other.y
			&& z > other.z;
	}

	public double getX()
	{
		return x;
	}

	public void setX(double x)
	{
		this.x = x;
	}

	public double getY()
	{
		return y;
	}

	public void setY(double y)
	{
		this.y = y;
	}

	public double getZ()
	{
		return z;
	}

	public void setZ(double z)
	{
		this.z = z;
	}

	public double get(int index)
	{
		assert index >= 0 && index < 3;

		switch (index) {
			case 0:
				return x;
			case 1:
				return y;
			case 2:
				return z;
			default:
				throw new RuntimeException("Unknown index " + index);
		}
	}

	public void set(int index, double value)
	{
		assert index >= 0 && index < 3;

		switch (index) {
			case 0:
				x = value;
				return;
			case 1:
				y = value;
				return;
			case 2:
				z = value;
				return;
			default:
				throw new RuntimeException("Unknown index " + index);
		}
	}

	@Override
	public String toString()
	{
		return "[ x: " + x + ", y: " + y + ", z: " + y + " ]";
	}
}
