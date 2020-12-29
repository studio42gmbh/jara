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
package de.s42.jara.enitites;

import de.s42.jara.core.BoundingBox;
import de.s42.jara.materials.Material;
import de.s42.jara.core.Vector3;
import de.s42.jara.tracer.RayContext;

/**
 *
 * @author Benjamin.Schiller
 */
public abstract class PhysicalEntity extends Entity
{
	public Material material;
	public final BoundingBox bounds = new BoundingBox();

	public PhysicalEntity(Vector3 position, Material material)
	{
		super(position);

		assert material != null;

		this.material = material;
	}

	public abstract boolean intersect(RayContext context);

	public void compute(RayContext context)
	{
		assert context != null;

		material.compute(context);
	}

	public Material getMaterial()
	{
		return material;
	}

	public void setMaterial(Material material)
	{
		assert material != null;

		this.material = material;
	}
}
