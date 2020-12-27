// Generated from src/de/s42/jara/loaders/objloader/ObjParser.g4 by ANTLR 4.8
package de.s42.jara.loaders.objloader.parser;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link ObjParserParser}.
 */
public interface ObjParserListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link ObjParserParser#meshes}.
	 * @param ctx the parse tree
	 */
	void enterMeshes(ObjParserParser.MeshesContext ctx);
	/**
	 * Exit a parse tree produced by {@link ObjParserParser#meshes}.
	 * @param ctx the parse tree
	 */
	void exitMeshes(ObjParserParser.MeshesContext ctx);
	/**
	 * Enter a parse tree produced by {@link ObjParserParser#command}.
	 * @param ctx the parse tree
	 */
	void enterCommand(ObjParserParser.CommandContext ctx);
	/**
	 * Exit a parse tree produced by {@link ObjParserParser#command}.
	 * @param ctx the parse tree
	 */
	void exitCommand(ObjParserParser.CommandContext ctx);
	/**
	 * Enter a parse tree produced by {@link ObjParserParser#mtllib}.
	 * @param ctx the parse tree
	 */
	void enterMtllib(ObjParserParser.MtllibContext ctx);
	/**
	 * Exit a parse tree produced by {@link ObjParserParser#mtllib}.
	 * @param ctx the parse tree
	 */
	void exitMtllib(ObjParserParser.MtllibContext ctx);
	/**
	 * Enter a parse tree produced by {@link ObjParserParser#object}.
	 * @param ctx the parse tree
	 */
	void enterObject(ObjParserParser.ObjectContext ctx);
	/**
	 * Exit a parse tree produced by {@link ObjParserParser#object}.
	 * @param ctx the parse tree
	 */
	void exitObject(ObjParserParser.ObjectContext ctx);
	/**
	 * Enter a parse tree produced by {@link ObjParserParser#position}.
	 * @param ctx the parse tree
	 */
	void enterPosition(ObjParserParser.PositionContext ctx);
	/**
	 * Exit a parse tree produced by {@link ObjParserParser#position}.
	 * @param ctx the parse tree
	 */
	void exitPosition(ObjParserParser.PositionContext ctx);
	/**
	 * Enter a parse tree produced by {@link ObjParserParser#normal}.
	 * @param ctx the parse tree
	 */
	void enterNormal(ObjParserParser.NormalContext ctx);
	/**
	 * Exit a parse tree produced by {@link ObjParserParser#normal}.
	 * @param ctx the parse tree
	 */
	void exitNormal(ObjParserParser.NormalContext ctx);
	/**
	 * Enter a parse tree produced by {@link ObjParserParser#textureposition}.
	 * @param ctx the parse tree
	 */
	void enterTextureposition(ObjParserParser.TexturepositionContext ctx);
	/**
	 * Exit a parse tree produced by {@link ObjParserParser#textureposition}.
	 * @param ctx the parse tree
	 */
	void exitTextureposition(ObjParserParser.TexturepositionContext ctx);
	/**
	 * Enter a parse tree produced by {@link ObjParserParser#group}.
	 * @param ctx the parse tree
	 */
	void enterGroup(ObjParserParser.GroupContext ctx);
	/**
	 * Exit a parse tree produced by {@link ObjParserParser#group}.
	 * @param ctx the parse tree
	 */
	void exitGroup(ObjParserParser.GroupContext ctx);
	/**
	 * Enter a parse tree produced by {@link ObjParserParser#usematerial}.
	 * @param ctx the parse tree
	 */
	void enterUsematerial(ObjParserParser.UsematerialContext ctx);
	/**
	 * Exit a parse tree produced by {@link ObjParserParser#usematerial}.
	 * @param ctx the parse tree
	 */
	void exitUsematerial(ObjParserParser.UsematerialContext ctx);
	/**
	 * Enter a parse tree produced by {@link ObjParserParser#smooth}.
	 * @param ctx the parse tree
	 */
	void enterSmooth(ObjParserParser.SmoothContext ctx);
	/**
	 * Exit a parse tree produced by {@link ObjParserParser#smooth}.
	 * @param ctx the parse tree
	 */
	void exitSmooth(ObjParserParser.SmoothContext ctx);
	/**
	 * Enter a parse tree produced by {@link ObjParserParser#face}.
	 * @param ctx the parse tree
	 */
	void enterFace(ObjParserParser.FaceContext ctx);
	/**
	 * Exit a parse tree produced by {@link ObjParserParser#face}.
	 * @param ctx the parse tree
	 */
	void exitFace(ObjParserParser.FaceContext ctx);
	/**
	 * Enter a parse tree produced by {@link ObjParserParser#vector2}.
	 * @param ctx the parse tree
	 */
	void enterVector2(ObjParserParser.Vector2Context ctx);
	/**
	 * Exit a parse tree produced by {@link ObjParserParser#vector2}.
	 * @param ctx the parse tree
	 */
	void exitVector2(ObjParserParser.Vector2Context ctx);
	/**
	 * Enter a parse tree produced by {@link ObjParserParser#vector3}.
	 * @param ctx the parse tree
	 */
	void enterVector3(ObjParserParser.Vector3Context ctx);
	/**
	 * Exit a parse tree produced by {@link ObjParserParser#vector3}.
	 * @param ctx the parse tree
	 */
	void exitVector3(ObjParserParser.Vector3Context ctx);
	/**
	 * Enter a parse tree produced by {@link ObjParserParser#vertex}.
	 * @param ctx the parse tree
	 */
	void enterVertex(ObjParserParser.VertexContext ctx);
	/**
	 * Exit a parse tree produced by {@link ObjParserParser#vertex}.
	 * @param ctx the parse tree
	 */
	void exitVertex(ObjParserParser.VertexContext ctx);
	/**
	 * Enter a parse tree produced by {@link ObjParserParser#positionIndex}.
	 * @param ctx the parse tree
	 */
	void enterPositionIndex(ObjParserParser.PositionIndexContext ctx);
	/**
	 * Exit a parse tree produced by {@link ObjParserParser#positionIndex}.
	 * @param ctx the parse tree
	 */
	void exitPositionIndex(ObjParserParser.PositionIndexContext ctx);
	/**
	 * Enter a parse tree produced by {@link ObjParserParser#textureIndex}.
	 * @param ctx the parse tree
	 */
	void enterTextureIndex(ObjParserParser.TextureIndexContext ctx);
	/**
	 * Exit a parse tree produced by {@link ObjParserParser#textureIndex}.
	 * @param ctx the parse tree
	 */
	void exitTextureIndex(ObjParserParser.TextureIndexContext ctx);
	/**
	 * Enter a parse tree produced by {@link ObjParserParser#normalIndex}.
	 * @param ctx the parse tree
	 */
	void enterNormalIndex(ObjParserParser.NormalIndexContext ctx);
	/**
	 * Exit a parse tree produced by {@link ObjParserParser#normalIndex}.
	 * @param ctx the parse tree
	 */
	void exitNormalIndex(ObjParserParser.NormalIndexContext ctx);
}