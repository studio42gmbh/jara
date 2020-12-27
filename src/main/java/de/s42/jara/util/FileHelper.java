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
package de.s42.jara.util;

import de.s42.jara.Configuration;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageTypeSpecifier;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.FileImageOutputStream;
import javax.imageio.stream.ImageOutputStream;
import javax.swing.filechooser.FileSystemView;

/**
 *
 * @author Benjamin.Schiller
 */
public final class FileHelper
{

	private FileHelper()
	{
		//do nothing
	}

	public final static boolean fileExists( String path)
	{
		assert path != null;

		return fileExists(Path.of(path));
	}

	public final static boolean fileExists( Path path)
	{
		assert path != null;

		return Files.isRegularFile(path);
	}

	public static Path getUserHome()
	{
		return FileSystemView.getFileSystemView().getHomeDirectory().toPath();
	}

	public final static boolean hasResource( Class moduleClass,  String relativeResourceName)
	{
		assert moduleClass != null;
		assert relativeResourceName != null;

		return (moduleClass.getResource(relativeResourceName) != null);
	}

	public final static InputStream getResourceAsStream( Class moduleClass,  String relativeResourceName)
	{
		assert moduleClass != null;
		assert relativeResourceName != null;

		InputStream in = moduleClass.getResourceAsStream(relativeResourceName);

		if (in == null) {
			throw new RuntimeException("Mssing resource '" + relativeResourceName + "' in module " + moduleClass.getName());
		}

		return in;
	}

	public static String getFileAsString( Path path) throws IOException
	{
		assert path != null;

		byte[] encoded = Files.readAllBytes(path);
		return StandardCharsets.UTF_8.decode(ByteBuffer.wrap(encoded)).toString();
	}

	public final static InputStream getFileAsStream( String fileName)
	{
		assert fileName != null;

		return getFileAsStream(Path.of(fileName));
	}

	public final static InputStream getFileAsStream( Path file)
	{
		assert file != null;

		try {
			return Files.newInputStream(file);
		}
		catch (IOException ex) {
			throw new RuntimeException("Error loading file '" + file + " - " + ex.getMessage(), ex);
		}
	}

	public final static BufferedImage getResourceAsImage( Class moduleClass,  String relativeResourceName)
	{
		assert moduleClass != null;
		assert relativeResourceName != null;

		try {
			BufferedImage image;
			try ( InputStream in = FileHelper.getResourceAsStream(moduleClass, relativeResourceName)) {
				image = ImageIO.read(in);
				in.close();
			}
			return image;
		}
		catch (IOException ex) {
			throw new RuntimeException("Unable to read module resource " + relativeResourceName + " - " + ex.getMessage(), ex);
		}
	}

	public final static BufferedImage getFileAsImage( String fileName)
	{
		assert fileName != null;

		return getFileAsImage(Path.of(fileName));
	}

	public final static BufferedImage getFileAsImage( Path file)
	{
		assert file != null;

		try {
			BufferedImage image;
			try ( InputStream in = FileHelper.getFileAsStream(file)) {
				image = ImageIO.read(in);
				in.close();
			}
			return image;
		}
		catch (IOException ex) {
			throw new RuntimeException("Unable to read file " + file.toAbsolutePath().toString() + " - " + ex.getMessage(), ex);
		}
	}

	public final static String getResource( Class moduleClass,  String relativeResourceName)
	{
		assert moduleClass != null;
		assert relativeResourceName != null;

		InputStream in = null;
		try {
			in = FileHelper.getResourceAsStream(moduleClass, relativeResourceName);

			ByteArrayOutputStream result = new ByteArrayOutputStream();
			byte[] buffer = new byte[1024];
			int length;
			while ((length = in.read(buffer)) != -1) {
				result.write(buffer, 0, length);
			}

			in.close();

			// StandardCharsets.UTF_8.name() > JDK 7
			return result.toString("UTF-8");
		}
		catch (IOException ex) {

			if (in != null) {
				try {
					in.close();
				}
				catch (IOException ex1) {
					throw new RuntimeException(ex1.getMessage(), ex1);
				}
			}

			throw new RuntimeException("Unable to read module resource " + relativeResourceName + " - " + ex.getMessage(), ex);
		}
	}

	public final static String getModuleVersion( Class moduleClass)
	{
		assert moduleClass != null;

		return getResource(moduleClass, moduleClass.getSimpleName() + ".version");
	}

	public static void saveImageAsFilePng( BufferedImage image,  String outputFile) throws IOException
	{
		assert outputFile != null;

		saveImageAsFilePng(image, Path.of(outputFile));
	}

	public static void saveImageAsFilePng( BufferedImage image,  Path outputFile) throws IOException
	{
		assert image != null;
		assert outputFile != null;

		ImageTypeSpecifier type = ImageTypeSpecifier.createFromRenderedImage(image);
		ImageWriter writer = ImageIO.getImageWriters(type, "png").next();

		ImageWriteParam param = writer.getDefaultWriteParam();
		param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
		param.setCompressionQuality(1.0f);

		try ( ImageOutputStream out = new FileImageOutputStream(outputFile.toFile())) {
			writer.setOutput(out);
			writer.write(null, new IIOImage(image, null, null), param);
			writer.dispose();
		}
	}

	public static void saveImageAsFileJpg( BufferedImage image,  String outputFile) throws IOException
	{
		assert outputFile != null;

		saveImageAsFileJpg(image, Path.of(outputFile), Configuration.getJpgQuality());
	}

	public static void saveImageAsFileJpg( BufferedImage image,  String outputFile,  float quality) throws IOException
	{
		assert outputFile != null;

		saveImageAsFileJpg(image, Path.of(outputFile), quality);
	}

	public static void saveImageAsFileJpg( BufferedImage image,  Path outputFile,  float quality) throws IOException
	{
		assert image != null;
		assert outputFile != null;
		assert quality >= 0.0f && quality <= 1.0f;

		ImageTypeSpecifier type = ImageTypeSpecifier.createFromRenderedImage(image);
		ImageWriter writer = ImageIO.getImageWriters(type, "jpg").next();

		ImageWriteParam param = writer.getDefaultWriteParam();
		param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
		param.setCompressionQuality(quality);

		try ( ImageOutputStream out = new FileImageOutputStream(outputFile.toFile())) {
			writer.setOutput(out);
			writer.write(null, new IIOImage(image, null, null), param);
			writer.dispose();
		}
	}
}
