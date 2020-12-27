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
public class Sphere extends PhysicalEntity
{
	public final double radius;
	public final double radiusSquared;

	public Sphere( Vector3 position,  Material material,  double radius)
	{
		super(position, material);

		assert radius > 0.0;

		this.radius = radius;
		radiusSquared = radius * radius;

		bounds.min.copy(position).subtract(radius);
		bounds.max.copy(position).add(radius);
		bounds.set(bounds.min, bounds.max);
	}

	@Override
	public boolean intersect( RayContext context)
	{
		assert context != null;

		Vector3 deltaCenterToRayOrigin = context.origin.copy().subtract(position);

		double dotDeltaCenterToRayOriginToRayDirection = deltaCenterToRayOrigin.dot(context.direction);

		//looking away from sphere -> no hit
		if (dotDeltaCenterToRayOriginToRayDirection >= 0.0) {
			//throw new RuntimeException("DD");
			return false;
		}

		Vector3 closestApproach = deltaCenterToRayOrigin.copy()
			.subtract(context.direction.copy()
				.multiply(dotDeltaCenterToRayOriginToRayDirection));

		double closestApproachDistanceSquared = closestApproach.squaredLength();

		//no intersection - approach to far away
		if (closestApproachDistanceSquared > radiusSquared) {
			return false;
		}

		double deltaDistances = Math.sqrt(radiusSquared - closestApproachDistanceSquared);

		//if inside the spphere -> find 2nd hit point 
		//@todo BS JARA optimize check inside sphere removing JaraMath.ONE_PLUS_EPSILON
		if (deltaCenterToRayOrigin.squaredLength() <= radiusSquared * JaraMath.ONE_PLUS_EPSILON) {
			deltaDistances = -deltaDistances;
			context.inbound = false;
		}
		else {
			context.inbound = true;
		}

		Vector3 intersectionPoint = closestApproach.copy()
			.subtract(
				context.direction.copy()
					.multiply(deltaDistances)
			);

		context.direction.copy(intersectionPoint).normalize();
		context.origin.copy(intersectionPoint).add(position);

		//Spherical mapping where UP is 0.0 and BOTTOM is 1.0
		double u = Math.atan2(context.direction.z, context.direction.x) / Math.PI / 2.0 + 0.5;
		double v = 1.0 - (Math.asin(context.direction.y) / Math.PI + 0.5);

		context.texturePosition.set(u * 2.0, v, 0.0);
		context.normalMatrix.computeNormalMatrixFromDirection(context.direction);

		return true;
	}
}
