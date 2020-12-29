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
import de.s42.jara.core.Vertex;
import de.s42.jara.enitites.PhysicalEntity;
import de.s42.jara.tracer.RayContext;

/**
 *
 * @author Benjamin.Schiller
 */
public class Triangle extends PhysicalEntity
{
	//tuples of position, normal, texture
	public final Vertex vertex1 = new Vertex();
	public final Vertex vertex2 = new Vertex();
	public final Vertex vertex3 = new Vertex();

	private final Vector3 v1v2 = new Vector3();
	private final Vector3 v1v3 = new Vector3();
	private final Vector3 faceNormal = new Vector3();
	private final boolean smoothNormal;
	private final boolean doubleSided;

	private final Vector3 deltaUV1 = new Vector3();
	private final Vector3 deltaUV2 = new Vector3();

	private final Vector3 tangent = new Vector3();
	private final Vector3 bitangent = new Vector3();

	public Triangle(Vector3 position, Material material, Vertex vertex1, Vertex vertex2, Vertex vertex3, boolean smoothNormal, boolean doubleSided)
	{
		super(position, material);

		assert vertex1 != null;
		assert vertex2 != null;
		assert vertex3 != null;

		this.smoothNormal = smoothNormal;
		this.doubleSided = doubleSided;

		this.vertex1.copy(vertex1);
		this.vertex2.copy(vertex2);
		this.vertex3.copy(vertex3);

		v1v2.copy(vertex2.position).subtract(vertex1.position);
		v1v3.copy(vertex3.position).subtract(vertex1.position);

		deltaUV1.copy(vertex2.texture).subtract(vertex1.texture);
		deltaUV2.copy(vertex3.texture).subtract(vertex1.texture);

		double f = 1.0 / (deltaUV1.x * deltaUV2.y - deltaUV2.x * deltaUV1.y);

		tangent.x = f * (deltaUV2.y * v1v2.x - deltaUV1.y * v1v3.x);
		tangent.y = f * (deltaUV2.y * v1v2.y - deltaUV1.y * v1v3.y);
		tangent.z = f * (deltaUV2.y * v1v2.z - deltaUV1.y * v1v3.z);
		tangent.normalize();

		bitangent.x = f * (-deltaUV2.x * v1v2.x + deltaUV1.x * v1v3.x);
		bitangent.y = f * (-deltaUV2.x * v1v2.y + deltaUV1.x * v1v3.y);
		bitangent.z = f * (-deltaUV2.x * v1v2.z + deltaUV1.x * v1v3.z);
		bitangent.invert().normalize();

		faceNormal.copy(v1v2).cross(v1v3).normalize();

		bounds.min.copy(vertex1.position).min(vertex2.position).min(vertex3.position);
		bounds.max.copy(vertex1.position).max(vertex2.position).max(vertex3.position);
		bounds.set(bounds.min, bounds.max);
	}

	@Override
	public boolean intersect(RayContext context)
	{
		assert context != null;

		Vector3 pvec = context.direction.copy().cross(v1v3);

		double det = v1v2.dot(pvec);

		// if the determinant is close to 0, the ray misses the triangle
		if (doubleSided) {
			if (Math.abs(det) < JaraMath.EPSILON) {
				return false;
			}
		}
		// if the determinant is negative the triangle is backfacing
		else {
			if (det < JaraMath.EPSILON) {
				return false;
			}
		}

		double invDet = 1.0 / det;

		Vector3 tvec = context.origin.copy().subtract(vertex1.position);
		double u = tvec.dot(pvec) * invDet;

		if (u < 0 || u > 1) {
			return false;
		}

		Vector3 qvec = tvec.copy().cross(v1v2);

		double v = context.direction.dot(qvec) * invDet;

		if (v < 0 || u + v > 1) {
			return false;
		}

		double t = v1v3.dot(qvec) * invDet;

		if (t > JaraMath.EPSILON) {

			Vector3 tex = vertex1.texture.copy().multiply(1.0 - u - v)
				.add(vertex2.texture.copy().multiply(u))
				.add(vertex3.texture.copy().multiply(v));

			Vector3 tan;
			Vector3 bit;
			Vector3 n;

			if (smoothNormal) {
				n = vertex1.normal.copy().multiply(1.0 - u - v)
					.add(vertex2.normal.copy().multiply(u))
					.add(vertex3.normal.copy().multiply(v)).normalize();

				//t = normalize(t - n * dot(n, t));
				tan = n.copy().multiply(tangent.dot(n)).invert().add(tangent).normalize();

				//b = cross(t, n);
				bit = tan.copy().cross(n);
			}
			else {
				n = faceNormal;
				tan = tangent;
				bit = bitangent;
			}

			context.normalMatrix.setColumn(0, tan);
			context.normalMatrix.setColumn(1, bit);
			context.normalMatrix.setColumn(2, n);

			context.texturePosition.copy(tex);
			context.origin.add(context.direction.copy().multiply(t));
			context.direction.copy(n);
			context.inbound = det >= 0.0;

			return true;
		}

		return false;
	}
}
