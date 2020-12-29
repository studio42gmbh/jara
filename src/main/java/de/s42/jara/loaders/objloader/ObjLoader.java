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
package de.s42.jara.loaders.objloader;

import de.s42.jara.assets.AssetManager;
import de.s42.jara.core.Vector3;
import de.s42.jara.core.Vertex;
import de.s42.jara.enitites.primitives.Triangle;
import de.s42.jara.loaders.objloader.parser.ObjParserBaseListener;
import de.s42.jara.loaders.objloader.parser.ObjParserLexer;
import de.s42.jara.loaders.objloader.parser.ObjParserParser;
import de.s42.jara.materials.Material;
import de.s42.jara.util.FileHelper;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import org.antlr.v4.runtime.BaseErrorListener;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * https://en.wikipedia.org/wiki/Wavefront_.obj_file
 *
 * @author Benjamin.Schiller
 */
public class ObjLoader extends ObjParserBaseListener
{
	private final static Logger log = LogManager.getLogger(ObjLoader.class.getName());

	private static class ErrorHandler extends BaseErrorListener
	{

		@Override
		public void syntaxError(Recognizer<?, ?> rcgnzr, Object o, int line, int position, String message, RecognitionException re)
		{
			message = message + " at " + line + ", " + position;

			throw new RuntimeException(message, re);
		}
	}

	private final List<Vector3> positions = new ArrayList();
	private final List<Vector3> normals = new ArrayList();
	private final List<Vector3> texturePositions = new ArrayList();
	private final List<int[]> faces = new ArrayList();
	private final List<String> faceMaterial = new ArrayList();
	private final List<Boolean> faceSmooth = new ArrayList();
	private String currentMaterial = "Default";
	private boolean smoothNormals = false;

	@Override
	public void enterSmooth(ObjParserParser.SmoothContext ctx)
	{
		smoothNormals = ctx.KEYWORD_OFF() == null;
	}

	@Override
	public void enterUsematerial(ObjParserParser.UsematerialContext ctx)
	{
		currentMaterial = ctx.SYMBOL().getText();
	}

	@Override
	public void enterPosition(ObjParserParser.PositionContext ctx)
	{
		double x = Double.parseDouble(ctx.vector3().FLOAT(0).getText());
		double y = Double.parseDouble(ctx.vector3().FLOAT(1).getText());
		double z = Double.parseDouble(ctx.vector3().FLOAT(2).getText());

		//log.trace("POS", x, y, z);
		positions.add(new Vector3(x, y, z));
	}

	@Override
	public void enterNormal(ObjParserParser.NormalContext ctx)
	{
		double x = Double.parseDouble(ctx.vector3().FLOAT(0).getText());
		double y = Double.parseDouble(ctx.vector3().FLOAT(1).getText());
		double z = Double.parseDouble(ctx.vector3().FLOAT(2).getText());

		//log.trace("NORMAL", x, y, z);
		normals.add(new Vector3(x, y, z));
	}

	@Override
	public void enterTextureposition(ObjParserParser.TexturepositionContext ctx)
	{
		double u = Double.parseDouble(ctx.vector2().FLOAT(0).getText());
		double v = Double.parseDouble(ctx.vector2().FLOAT(1).getText());

		//log.trace("TEX", u, v);
		texturePositions.add(new Vector3(u, v, 0.0));
	}

	@Override
	public void enterFace(ObjParserParser.FaceContext ctx)
	{
		int p1 = Integer.parseInt(ctx.vertex(0).positionIndex().getText()) - 1;
		int t1 = Integer.parseInt(ctx.vertex(0).textureIndex().getText()) - 1;
		int n1 = Integer.parseInt(ctx.vertex(0).normalIndex().getText()) - 1;
		int p2 = Integer.parseInt(ctx.vertex(1).positionIndex().getText()) - 1;
		int t2 = Integer.parseInt(ctx.vertex(1).textureIndex().getText()) - 1;
		int n2 = Integer.parseInt(ctx.vertex(1).normalIndex().getText()) - 1;
		int p3 = Integer.parseInt(ctx.vertex(2).positionIndex().getText()) - 1;
		int t3 = Integer.parseInt(ctx.vertex(2).textureIndex().getText()) - 1;
		int n3 = Integer.parseInt(ctx.vertex(2).normalIndex().getText()) - 1;

		//log.trace("FACE", p1, t1, n1, p2, t2, n2, p3, t3, n3);		
		faces.add(new int[]{p1, t1, n1, p2, t2, n2, p3, t3, n3});
		faceMaterial.add(currentMaterial);
		faceSmooth.add(smoothNormals);
	}

	public static List<Triangle> loadTriangles(Path file, AssetManager assets)
	{
		return loadTriangles(file, assets, Vector3.ORIGIN, new Vector3(1.0));
	}

	public static List<Triangle> loadTriangles(Path file, AssetManager assets, Vector3 translation, Vector3 scale)
	{
		assert file != null;
		assert assets != null;
		assert translation != null;
		assert scale != null;

		try {
			String fileContent = FileHelper.getFileAsString(file);

			//log.debug(fileContent);
			ObjLoader loader = new ObjLoader();

			ObjParserLexer lexer = new ObjParserLexer(CharStreams.fromString(fileContent));
			lexer.removeErrorListeners();
			lexer.addErrorListener(new ErrorHandler());
			TokenStream tokens = new CommonTokenStream(lexer);

			ObjParserParser parser = new ObjParserParser(tokens);
			parser.removeErrorListeners();
			parser.addErrorListener(new ErrorHandler());
			ObjParserParser.MeshesContext context = parser.meshes();
			ParseTreeWalker walker = new ParseTreeWalker();
			walker.walk(loader, context);

			log.info("Loaded from "
				+ file.toAbsolutePath().toString()
				+ " with "
				+ loader.faces.size() + " triangles "
				+ loader.positions.size() + " positions "
				+ loader.normals.size() + " normals "
				+ loader.texturePositions.size() + " texturePositions"
			);

			List triangles = new ArrayList();

			for (int i = 0; i < loader.faces.size(); ++i) {

				int[] face = loader.faces.get(i);
				String materialId = loader.faceMaterial.get(i);
				Boolean smooth = loader.faceSmooth.get(i);
				Material material = assets.getMaterial(materialId);
				boolean doubleSided = material.doubleSided;

				Vertex vertex1 = new Vertex(
					loader.positions.get(face[0]).copy().multiply(scale).add(translation), //pos
					loader.normals.get(face[2]), //norm
					loader.texturePositions.get(face[1]) //tex
				);
				Vertex vertex2 = new Vertex(
					loader.positions.get(face[3]).copy().multiply(scale).add(translation), //pos
					loader.normals.get(face[5]), //norm
					loader.texturePositions.get(face[4]) //tex
				);
				Vertex vertex3 = new Vertex(
					loader.positions.get(face[6]).copy().multiply(scale).add(translation), //pos
					loader.normals.get(face[8]), //norm
					loader.texturePositions.get(face[7]) //tex
				);

				Triangle tri = new Triangle(Vector3.ORIGIN, material, vertex1, vertex2, vertex3, smooth, doubleSided);

				triangles.add(tri);
			}

			return triangles;
		}
		catch (IOException ex) {
			throw new RuntimeException("File not found " + file.toAbsolutePath().toString() + " - " + ex.getMessage(), ex);
		}
	}
}
