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
package de.s42.jara.tracer;

import de.s42.jara.core.Color;
import de.s42.jara.core.ColorArray;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

/**
 *
 * @author Benjamin Schiller
 */
public class RenderBuffer
{
	private final int width, height;
	private final BufferedImage buffer;
	private final ColorArray bufferedColors;

	public RenderBuffer(int width, int height)
	{
		assert width > 0;
		assert height > 0;

		this.width = width;
		this.height = height;

		buffer = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		bufferedColors = new ColorArray(width * height);

		init();
	}

	private void init()
	{
		Graphics g2D = buffer.createGraphics();

		g2D.setColor(java.awt.Color.BLACK);
		g2D.fillRect(0, 0, width, height);
		g2D.dispose();
	}

	public void reset()
	{
		int[] imagePixelData;

		imagePixelData = ((DataBufferInt) buffer.getRaster().getDataBuffer()).getData();

		int size = width * height;

		for (int i = 0; i < size; ++i) {

			imagePixelData[i] = 0x0;
			bufferedColors.set(i, 0.0);
		}
	}

	public void setColor(int x, int y, Color color)
	{
		assert x >= 0 && x < width;
		assert y >= 0 && y < height;
		assert color != null;

		bufferedColors.set(x + width * y, color);
	}

	public Color getColor(int x, int y, Color result)
	{
		assert x >= 0 && x < width;
		assert y >= 0 && y < height;

		return bufferedColors.get(x + width * y, result);
	}

	public void addColor(int x, int y, Color color)
	{
		assert x >= 0 && x < width;
		assert y >= 0 && y < height;
		assert color != null;

		bufferedColors.add(x + width * y, color);
	}

	public synchronized void blitColorsToBuffer(double scale)
	{
		blitColorsToBuffer(scale, 0, 0, width, height);
	}

	public synchronized void blitColorsToBuffer(double scale, int x, int y, int w, int h)
	{
		assert x >= 0 && x < width;
		assert y >= 0 && y < height;
		assert x + w >= 0 && x + w <= width;
		assert y + y >= 0 && y + y <= height;

		int[] imagePixelData;

		imagePixelData = ((DataBufferInt) buffer.getRaster().getDataBuffer()).getData();

		for (int yc = y; yc < y + h; ++yc) {
			for (int xc = x; xc < x + w; ++xc) {

				int index = xc + width * yc;
				imagePixelData[index] = bufferedColors.getScaledARGB8(index, scale);
			}
		}
	}

	public int getWidth()
	{
		return width;
	}

	public int getHeight()
	{
		return height;
	}

	public BufferedImage getBuffer()
	{
		return buffer;
	}
}
