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
public class Vertex
{
	public final Vector3 position = new Vector3();
	public final Vector3 normal = new Vector3();
	public final Vector3 texture = new Vector3();

	public Vertex()
	{

	}

	public Vertex(Vector3 position, Vector3 normal, Vector3 texture)
	{
		assert position != null;
		assert normal != null;
		assert texture != null;

		this.position.copy(position);
		this.normal.copy(normal);
		this.texture.copy(texture);
	}

	public Vertex(Vertex toCopy)
	{
		assert toCopy != null;

		position.copy(toCopy.position);
		normal.copy(toCopy.normal);
		texture.copy(toCopy.texture);
	}

	public Vertex copy(Vertex toCopy)
	{
		assert toCopy != null;

		position.copy(toCopy.position);
		normal.copy(toCopy.normal);
		texture.copy(toCopy.texture);

		return this;
	}
}
