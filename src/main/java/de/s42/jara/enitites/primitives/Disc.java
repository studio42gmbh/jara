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
package de.s42.jara.enitites.primitives;

import de.s42.jara.core.JaraMath;
import de.s42.jara.materials.Material;
import de.s42.jara.core.Vector3;
import de.s42.jara.enitites.PhysicalEntity;
import de.s42.jara.tracer.RayContext;

/**
 *
 * @author Benjamin.Schiller
 */
public class Disc extends PhysicalEntity
{
	public final Vector3 normal;
	public final double radius;
	public final double radiusSquared;

	public Disc(Vector3 position, Material material, Vector3 normal, double radius)
	{
		super(position, material);

		assert normal != null;
		assert radius > 0.0;

		this.normal = normal.copy().normalize();

		this.radius = radius;
		radiusSquared = radius * radius;

		bounds.min.copy(position).subtract(radius);
		bounds.max.copy(position).add(radius);
		bounds.set(bounds.min, bounds.max);
	}

	@Override
	public boolean intersect(RayContext context)
	{
		assert context != null;

		double dotDirectionNormal = context.direction.dot(normal);

		//just allow hits from "top" of plane
		if (dotDirectionNormal > 0) {
			return false;
		}

		double closestApproach
			= position.copy()
				.subtract(context.origin)
				.dot(normal) / dotDirectionNormal;

		if (closestApproach > JaraMath.EPSILON) {

			Vector3 newOrigin = context.direction.copy()
				.multiply(closestApproach)
				.add(context.origin);

			if (newOrigin.copy().subtract(position).squaredLength() > radiusSquared) {
				return false;
			}

			context.origin.copy(newOrigin);
			context.direction.copy(normal);
			context.inbound = true;
			context.normalMatrix.computeNormalMatrixFromDirection(normal);
			context.texturePosition.set(context.origin.x, context.origin.z, 0.0);

			return true;
		}
		else {
			return false;
		}
	}
}
