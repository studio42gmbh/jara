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
public class BoundingBox
{
	public final Vector3 min = new Vector3(-Double.MAX_VALUE);
	public final Vector3 max = new Vector3(Double.MAX_VALUE);
	public final Vector3 delta = new Vector3();
	public final Vector3 center = new Vector3();
	public double volume;

	public BoundingBox copy(BoundingBox other)
	{
		assert other != null;

		return set(other.min, other.max);
	}

	public BoundingBox set(Vector3 min, Vector3 max)
	{
		assert min != null;
		assert max != null;

		this.min.copy(min);
		this.max.copy(max);
		delta.copy(max).subtract(min);
		center.copy(delta).multiply(0.5).add(min);
		volume = delta.x * delta.y * delta.z;

		return this;
	}

	public BoundingBox join(BoundingBox other)
	{
		assert other != null;

		return set(min.min(other.min), max.max(other.max));
	}

	public boolean contains(Vector3 point)
	{
		assert point != null;

		return min.smaller(point) && max.bigger(point);
	}

	public boolean intersects(Vector3 origin, Vector3 direction)
	{
		return intersects(origin, direction, 0.0, Double.MAX_VALUE);
	}

	/**
	 * Implementation of a rather fast AABB test. See
	 * https://medium.com/@bromanz/another-view-on-the-classic-ray-aabb-intersection-algorithm-for-bvh-traversal-41125138b525
	 *
	 * @param origin
	 * @param direction
	 * @param tmin
	 * @param tmax
	 * @return
	 */
	public boolean intersects(Vector3 origin, Vector3 direction, double tmin, double tmax)
	{
		assert origin != null;
		assert direction != null;
		assert tmin < tmax;

		Vector3 invD = direction.copy().reciproc();

		Vector3 t0s = min.copy().subtract(origin).multiply(invD);
		Vector3 t1s = max.copy().subtract(origin).multiply(invD);

		Vector3 tsmaller = t0s.copy().min(t1s);
		Vector3 tbigger = t0s.max(t1s); //spares copy of t0s as it will not be used anymore

		tmin = Math.max(tmin, Math.max(tsmaller.x, Math.max(tsmaller.y, tsmaller.z)));
		tmax = Math.min(tmax, Math.min(tbigger.x, Math.min(tbigger.y, tbigger.z)));

		return tmin <= tmax;
	}

	@Override
	public String toString()
	{
		return "[ min: " + min + ", max: " + max + " ]";
	}
}
