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
package de.s42.jara.materials;

import de.s42.jara.core.Color;
import de.s42.jara.core.ColorArray;
import de.s42.jara.core.Vector3;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

/**
 *
 * @author Benjamin Schiller
 */
public class Texture
{
	public final int width;
	public final int height;
	public final ColorArray data;

	public Texture(Image image)
	{
		assert image != null;

		//create TYPE_INT_ARGB copy of given image
		BufferedImage img = new BufferedImage(image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2D = (Graphics2D) img.getGraphics();
		g2D.drawImage(image, 0, 0, null);
		g2D.dispose();

		width = img.getWidth();
		height = img.getHeight();
		int[] imagePixelData = ((DataBufferInt) img.getRaster().getDataBuffer()).getData();

		data = new ColorArray(width * height);
		data.setARGB8(0, width * height, imagePixelData);

		img.flush();
	}

	public Vector3 retrieveVector3(Vector3 result, Vector3 position)
	{
		assert result != null;
		assert position != null;

		int u = (int) Math.floor((position.x - Math.floor(position.x)) * (width - 1));
		int v = (int) Math.floor((position.y - Math.floor(position.y)) * (height - 1));

		return data.get(u + v * width, result);
	}

	public Color retrieveColor(Color result, Vector3 position)
	{
		assert result != null;
		assert position != null;

		int u = (int) Math.floor((position.x - Math.floor(position.x)) * (width - 1));
		int v = (int) Math.floor((position.y - Math.floor(position.y)) * (height - 1));

		return data.get(u + v * width, result);
	}
}
