// Generated from src/de/s42/jara/loaders/objloader/ObjParser.g4 by ANTLR 4.8
package de.s42.jara.loaders.objloader.parser;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class ObjParserParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.8", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		KEYWORD_MTLLIB=1, KEYWORD_OBJECT=2, KEYWORD_GROUP=3, KEYWORD_POSITION=4, 
		KEYWORD_NORMAL=5, KEYWORD_TEXTUREPOSITION=6, KEYWORD_USEMATERIAL=7, KEYWORD_SMOOTH=8, 
		KEYWORD_FACE=9, KEYWORD_OFF=10, SLASH=11, INTEGER=12, FLOAT=13, SYMBOL=14, 
		COMMENT=15, NEWLINE=16, WS=17;
	public static final int
		RULE_meshes = 0, RULE_command = 1, RULE_mtllib = 2, RULE_object = 3, RULE_position = 4, 
		RULE_normal = 5, RULE_textureposition = 6, RULE_group = 7, RULE_usematerial = 8, 
		RULE_smooth = 9, RULE_face = 10, RULE_vector2 = 11, RULE_vector3 = 12, 
		RULE_vertex = 13, RULE_positionIndex = 14, RULE_textureIndex = 15, RULE_normalIndex = 16;
	private static String[] makeRuleNames() {
		return new String[] {
			"meshes", "command", "mtllib", "object", "position", "normal", "textureposition", 
			"group", "usematerial", "smooth", "face", "vector2", "vector3", "vertex", 
			"positionIndex", "textureIndex", "normalIndex"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'mtllib'", "'o'", "'g'", "'v'", "'vn'", "'vt'", "'usemtl'", "'s'", 
			"'f'", "'off'", "'/'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, "KEYWORD_MTLLIB", "KEYWORD_OBJECT", "KEYWORD_GROUP", "KEYWORD_POSITION", 
			"KEYWORD_NORMAL", "KEYWORD_TEXTUREPOSITION", "KEYWORD_USEMATERIAL", "KEYWORD_SMOOTH", 
			"KEYWORD_FACE", "KEYWORD_OFF", "SLASH", "INTEGER", "FLOAT", "SYMBOL", 
			"COMMENT", "NEWLINE", "WS"
		};
	}
	private static final String[] _SYMBOLIC_NAMES = makeSymbolicNames();
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}

	@Override
	public String getGrammarFileName() { return "ObjParser.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public ObjParserParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	public static class MeshesContext extends ParserRuleContext {
		public TerminalNode EOF() { return getToken(ObjParserParser.EOF, 0); }
		public List<CommandContext> command() {
			return getRuleContexts(CommandContext.class);
		}
		public CommandContext command(int i) {
			return getRuleContext(CommandContext.class,i);
		}
		public MeshesContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_meshes; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ObjParserListener ) ((ObjParserListener)listener).enterMeshes(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ObjParserListener ) ((ObjParserListener)listener).exitMeshes(this);
		}
	}

	public final MeshesContext meshes() throws RecognitionException {
		MeshesContext _localctx = new MeshesContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_meshes);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(37);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << KEYWORD_MTLLIB) | (1L << KEYWORD_OBJECT) | (1L << KEYWORD_GROUP) | (1L << KEYWORD_POSITION) | (1L << KEYWORD_NORMAL) | (1L << KEYWORD_TEXTUREPOSITION) | (1L << KEYWORD_USEMATERIAL) | (1L << KEYWORD_SMOOTH) | (1L << KEYWORD_FACE) | (1L << NEWLINE))) != 0)) {
				{
				{
				setState(34);
				command();
				}
				}
				setState(39);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(40);
			match(EOF);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class CommandContext extends ParserRuleContext {
		public TerminalNode NEWLINE() { return getToken(ObjParserParser.NEWLINE, 0); }
		public MtllibContext mtllib() {
			return getRuleContext(MtllibContext.class,0);
		}
		public ObjectContext object() {
			return getRuleContext(ObjectContext.class,0);
		}
		public PositionContext position() {
			return getRuleContext(PositionContext.class,0);
		}
		public NormalContext normal() {
			return getRuleContext(NormalContext.class,0);
		}
		public TexturepositionContext textureposition() {
			return getRuleContext(TexturepositionContext.class,0);
		}
		public GroupContext group() {
			return getRuleContext(GroupContext.class,0);
		}
		public UsematerialContext usematerial() {
			return getRuleContext(UsematerialContext.class,0);
		}
		public SmoothContext smooth() {
			return getRuleContext(SmoothContext.class,0);
		}
		public FaceContext face() {
			return getRuleContext(FaceContext.class,0);
		}
		public CommandContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_command; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ObjParserListener ) ((ObjParserListener)listener).enterCommand(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ObjParserListener ) ((ObjParserListener)listener).exitCommand(this);
		}
	}

	public final CommandContext command() throws RecognitionException {
		CommandContext _localctx = new CommandContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_command);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(51);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case KEYWORD_MTLLIB:
				{
				setState(42);
				mtllib();
				}
				break;
			case KEYWORD_OBJECT:
				{
				setState(43);
				object();
				}
				break;
			case KEYWORD_POSITION:
				{
				setState(44);
				position();
				}
				break;
			case KEYWORD_NORMAL:
				{
				setState(45);
				normal();
				}
				break;
			case KEYWORD_TEXTUREPOSITION:
				{
				setState(46);
				textureposition();
				}
				break;
			case KEYWORD_GROUP:
				{
				setState(47);
				group();
				}
				break;
			case KEYWORD_USEMATERIAL:
				{
				setState(48);
				usematerial();
				}
				break;
			case KEYWORD_SMOOTH:
				{
				setState(49);
				smooth();
				}
				break;
			case KEYWORD_FACE:
				{
				setState(50);
				face();
				}
				break;
			case NEWLINE:
				break;
			default:
				break;
			}
			setState(53);
			match(NEWLINE);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class MtllibContext extends ParserRuleContext {
		public TerminalNode KEYWORD_MTLLIB() { return getToken(ObjParserParser.KEYWORD_MTLLIB, 0); }
		public TerminalNode SYMBOL() { return getToken(ObjParserParser.SYMBOL, 0); }
		public MtllibContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_mtllib; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ObjParserListener ) ((ObjParserListener)listener).enterMtllib(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ObjParserListener ) ((ObjParserListener)listener).exitMtllib(this);
		}
	}

	public final MtllibContext mtllib() throws RecognitionException {
		MtllibContext _localctx = new MtllibContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_mtllib);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(55);
			match(KEYWORD_MTLLIB);
			setState(56);
			match(SYMBOL);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ObjectContext extends ParserRuleContext {
		public TerminalNode KEYWORD_OBJECT() { return getToken(ObjParserParser.KEYWORD_OBJECT, 0); }
		public TerminalNode SYMBOL() { return getToken(ObjParserParser.SYMBOL, 0); }
		public ObjectContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_object; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ObjParserListener ) ((ObjParserListener)listener).enterObject(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ObjParserListener ) ((ObjParserListener)listener).exitObject(this);
		}
	}

	public final ObjectContext object() throws RecognitionException {
		ObjectContext _localctx = new ObjectContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_object);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(58);
			match(KEYWORD_OBJECT);
			setState(59);
			match(SYMBOL);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class PositionContext extends ParserRuleContext {
		public TerminalNode KEYWORD_POSITION() { return getToken(ObjParserParser.KEYWORD_POSITION, 0); }
		public Vector3Context vector3() {
			return getRuleContext(Vector3Context.class,0);
		}
		public PositionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_position; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ObjParserListener ) ((ObjParserListener)listener).enterPosition(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ObjParserListener ) ((ObjParserListener)listener).exitPosition(this);
		}
	}

	public final PositionContext position() throws RecognitionException {
		PositionContext _localctx = new PositionContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_position);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(61);
			match(KEYWORD_POSITION);
			setState(62);
			vector3();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class NormalContext extends ParserRuleContext {
		public TerminalNode KEYWORD_NORMAL() { return getToken(ObjParserParser.KEYWORD_NORMAL, 0); }
		public Vector3Context vector3() {
			return getRuleContext(Vector3Context.class,0);
		}
		public NormalContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_normal; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ObjParserListener ) ((ObjParserListener)listener).enterNormal(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ObjParserListener ) ((ObjParserListener)listener).exitNormal(this);
		}
	}

	public final NormalContext normal() throws RecognitionException {
		NormalContext _localctx = new NormalContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_normal);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(64);
			match(KEYWORD_NORMAL);
			setState(65);
			vector3();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class TexturepositionContext extends ParserRuleContext {
		public TerminalNode KEYWORD_TEXTUREPOSITION() { return getToken(ObjParserParser.KEYWORD_TEXTUREPOSITION, 0); }
		public Vector2Context vector2() {
			return getRuleContext(Vector2Context.class,0);
		}
		public TexturepositionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_textureposition; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ObjParserListener ) ((ObjParserListener)listener).enterTextureposition(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ObjParserListener ) ((ObjParserListener)listener).exitTextureposition(this);
		}
	}

	public final TexturepositionContext textureposition() throws RecognitionException {
		TexturepositionContext _localctx = new TexturepositionContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_textureposition);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(67);
			match(KEYWORD_TEXTUREPOSITION);
			setState(68);
			vector2();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class GroupContext extends ParserRuleContext {
		public TerminalNode KEYWORD_GROUP() { return getToken(ObjParserParser.KEYWORD_GROUP, 0); }
		public TerminalNode SYMBOL() { return getToken(ObjParserParser.SYMBOL, 0); }
		public GroupContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_group; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ObjParserListener ) ((ObjParserListener)listener).enterGroup(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ObjParserListener ) ((ObjParserListener)listener).exitGroup(this);
		}
	}

	public final GroupContext group() throws RecognitionException {
		GroupContext _localctx = new GroupContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_group);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(70);
			match(KEYWORD_GROUP);
			setState(71);
			match(SYMBOL);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class UsematerialContext extends ParserRuleContext {
		public TerminalNode KEYWORD_USEMATERIAL() { return getToken(ObjParserParser.KEYWORD_USEMATERIAL, 0); }
		public TerminalNode SYMBOL() { return getToken(ObjParserParser.SYMBOL, 0); }
		public UsematerialContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_usematerial; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ObjParserListener ) ((ObjParserListener)listener).enterUsematerial(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ObjParserListener ) ((ObjParserListener)listener).exitUsematerial(this);
		}
	}

	public final UsematerialContext usematerial() throws RecognitionException {
		UsematerialContext _localctx = new UsematerialContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_usematerial);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(73);
			match(KEYWORD_USEMATERIAL);
			setState(74);
			match(SYMBOL);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class SmoothContext extends ParserRuleContext {
		public TerminalNode KEYWORD_SMOOTH() { return getToken(ObjParserParser.KEYWORD_SMOOTH, 0); }
		public TerminalNode KEYWORD_OFF() { return getToken(ObjParserParser.KEYWORD_OFF, 0); }
		public TerminalNode INTEGER() { return getToken(ObjParserParser.INTEGER, 0); }
		public TerminalNode FLOAT() { return getToken(ObjParserParser.FLOAT, 0); }
		public SmoothContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_smooth; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ObjParserListener ) ((ObjParserListener)listener).enterSmooth(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ObjParserListener ) ((ObjParserListener)listener).exitSmooth(this);
		}
	}

	public final SmoothContext smooth() throws RecognitionException {
		SmoothContext _localctx = new SmoothContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_smooth);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(76);
			match(KEYWORD_SMOOTH);
			setState(77);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << KEYWORD_OFF) | (1L << INTEGER) | (1L << FLOAT))) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class FaceContext extends ParserRuleContext {
		public TerminalNode KEYWORD_FACE() { return getToken(ObjParserParser.KEYWORD_FACE, 0); }
		public List<VertexContext> vertex() {
			return getRuleContexts(VertexContext.class);
		}
		public VertexContext vertex(int i) {
			return getRuleContext(VertexContext.class,i);
		}
		public FaceContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_face; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ObjParserListener ) ((ObjParserListener)listener).enterFace(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ObjParserListener ) ((ObjParserListener)listener).exitFace(this);
		}
	}

	public final FaceContext face() throws RecognitionException {
		FaceContext _localctx = new FaceContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_face);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(79);
			match(KEYWORD_FACE);
			setState(80);
			vertex();
			setState(81);
			vertex();
			setState(82);
			vertex();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Vector2Context extends ParserRuleContext {
		public List<TerminalNode> FLOAT() { return getTokens(ObjParserParser.FLOAT); }
		public TerminalNode FLOAT(int i) {
			return getToken(ObjParserParser.FLOAT, i);
		}
		public Vector2Context(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_vector2; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ObjParserListener ) ((ObjParserListener)listener).enterVector2(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ObjParserListener ) ((ObjParserListener)listener).exitVector2(this);
		}
	}

	public final Vector2Context vector2() throws RecognitionException {
		Vector2Context _localctx = new Vector2Context(_ctx, getState());
		enterRule(_localctx, 22, RULE_vector2);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(84);
			match(FLOAT);
			setState(85);
			match(FLOAT);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Vector3Context extends ParserRuleContext {
		public List<TerminalNode> FLOAT() { return getTokens(ObjParserParser.FLOAT); }
		public TerminalNode FLOAT(int i) {
			return getToken(ObjParserParser.FLOAT, i);
		}
		public Vector3Context(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_vector3; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ObjParserListener ) ((ObjParserListener)listener).enterVector3(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ObjParserListener ) ((ObjParserListener)listener).exitVector3(this);
		}
	}

	public final Vector3Context vector3() throws RecognitionException {
		Vector3Context _localctx = new Vector3Context(_ctx, getState());
		enterRule(_localctx, 24, RULE_vector3);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(87);
			match(FLOAT);
			setState(88);
			match(FLOAT);
			setState(89);
			match(FLOAT);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class VertexContext extends ParserRuleContext {
		public PositionIndexContext positionIndex() {
			return getRuleContext(PositionIndexContext.class,0);
		}
		public List<TerminalNode> SLASH() { return getTokens(ObjParserParser.SLASH); }
		public TerminalNode SLASH(int i) {
			return getToken(ObjParserParser.SLASH, i);
		}
		public TextureIndexContext textureIndex() {
			return getRuleContext(TextureIndexContext.class,0);
		}
		public NormalIndexContext normalIndex() {
			return getRuleContext(NormalIndexContext.class,0);
		}
		public VertexContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_vertex; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ObjParserListener ) ((ObjParserListener)listener).enterVertex(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ObjParserListener ) ((ObjParserListener)listener).exitVertex(this);
		}
	}

	public final VertexContext vertex() throws RecognitionException {
		VertexContext _localctx = new VertexContext(_ctx, getState());
		enterRule(_localctx, 26, RULE_vertex);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(91);
			positionIndex();
			setState(92);
			match(SLASH);
			setState(93);
			textureIndex();
			setState(94);
			match(SLASH);
			setState(95);
			normalIndex();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class PositionIndexContext extends ParserRuleContext {
		public TerminalNode INTEGER() { return getToken(ObjParserParser.INTEGER, 0); }
		public PositionIndexContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_positionIndex; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ObjParserListener ) ((ObjParserListener)listener).enterPositionIndex(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ObjParserListener ) ((ObjParserListener)listener).exitPositionIndex(this);
		}
	}

	public final PositionIndexContext positionIndex() throws RecognitionException {
		PositionIndexContext _localctx = new PositionIndexContext(_ctx, getState());
		enterRule(_localctx, 28, RULE_positionIndex);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(97);
			match(INTEGER);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class TextureIndexContext extends ParserRuleContext {
		public TerminalNode INTEGER() { return getToken(ObjParserParser.INTEGER, 0); }
		public TextureIndexContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_textureIndex; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ObjParserListener ) ((ObjParserListener)listener).enterTextureIndex(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ObjParserListener ) ((ObjParserListener)listener).exitTextureIndex(this);
		}
	}

	public final TextureIndexContext textureIndex() throws RecognitionException {
		TextureIndexContext _localctx = new TextureIndexContext(_ctx, getState());
		enterRule(_localctx, 30, RULE_textureIndex);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(99);
			match(INTEGER);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class NormalIndexContext extends ParserRuleContext {
		public TerminalNode INTEGER() { return getToken(ObjParserParser.INTEGER, 0); }
		public NormalIndexContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_normalIndex; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof ObjParserListener ) ((ObjParserListener)listener).enterNormalIndex(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof ObjParserListener ) ((ObjParserListener)listener).exitNormalIndex(this);
		}
	}

	public final NormalIndexContext normalIndex() throws RecognitionException {
		NormalIndexContext _localctx = new NormalIndexContext(_ctx, getState());
		enterRule(_localctx, 32, RULE_normalIndex);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(101);
			match(INTEGER);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\3\23j\4\2\t\2\4\3\t"+
		"\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t\13\4"+
		"\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22\3\2\7"+
		"\2&\n\2\f\2\16\2)\13\2\3\2\3\2\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\3\5\3"+
		"\66\n\3\3\3\3\3\3\4\3\4\3\4\3\5\3\5\3\5\3\6\3\6\3\6\3\7\3\7\3\7\3\b\3"+
		"\b\3\b\3\t\3\t\3\t\3\n\3\n\3\n\3\13\3\13\3\13\3\f\3\f\3\f\3\f\3\f\3\r"+
		"\3\r\3\r\3\16\3\16\3\16\3\16\3\17\3\17\3\17\3\17\3\17\3\17\3\20\3\20\3"+
		"\21\3\21\3\22\3\22\3\22\2\2\23\2\4\6\b\n\f\16\20\22\24\26\30\32\34\36"+
		" \"\2\3\4\2\f\f\16\17\2b\2\'\3\2\2\2\4\65\3\2\2\2\69\3\2\2\2\b<\3\2\2"+
		"\2\n?\3\2\2\2\fB\3\2\2\2\16E\3\2\2\2\20H\3\2\2\2\22K\3\2\2\2\24N\3\2\2"+
		"\2\26Q\3\2\2\2\30V\3\2\2\2\32Y\3\2\2\2\34]\3\2\2\2\36c\3\2\2\2 e\3\2\2"+
		"\2\"g\3\2\2\2$&\5\4\3\2%$\3\2\2\2&)\3\2\2\2\'%\3\2\2\2\'(\3\2\2\2(*\3"+
		"\2\2\2)\'\3\2\2\2*+\7\2\2\3+\3\3\2\2\2,\66\5\6\4\2-\66\5\b\5\2.\66\5\n"+
		"\6\2/\66\5\f\7\2\60\66\5\16\b\2\61\66\5\20\t\2\62\66\5\22\n\2\63\66\5"+
		"\24\13\2\64\66\5\26\f\2\65,\3\2\2\2\65-\3\2\2\2\65.\3\2\2\2\65/\3\2\2"+
		"\2\65\60\3\2\2\2\65\61\3\2\2\2\65\62\3\2\2\2\65\63\3\2\2\2\65\64\3\2\2"+
		"\2\65\66\3\2\2\2\66\67\3\2\2\2\678\7\22\2\28\5\3\2\2\29:\7\3\2\2:;\7\20"+
		"\2\2;\7\3\2\2\2<=\7\4\2\2=>\7\20\2\2>\t\3\2\2\2?@\7\6\2\2@A\5\32\16\2"+
		"A\13\3\2\2\2BC\7\7\2\2CD\5\32\16\2D\r\3\2\2\2EF\7\b\2\2FG\5\30\r\2G\17"+
		"\3\2\2\2HI\7\5\2\2IJ\7\20\2\2J\21\3\2\2\2KL\7\t\2\2LM\7\20\2\2M\23\3\2"+
		"\2\2NO\7\n\2\2OP\t\2\2\2P\25\3\2\2\2QR\7\13\2\2RS\5\34\17\2ST\5\34\17"+
		"\2TU\5\34\17\2U\27\3\2\2\2VW\7\17\2\2WX\7\17\2\2X\31\3\2\2\2YZ\7\17\2"+
		"\2Z[\7\17\2\2[\\\7\17\2\2\\\33\3\2\2\2]^\5\36\20\2^_\7\r\2\2_`\5 \21\2"+
		"`a\7\r\2\2ab\5\"\22\2b\35\3\2\2\2cd\7\16\2\2d\37\3\2\2\2ef\7\16\2\2f!"+
		"\3\2\2\2gh\7\16\2\2h#\3\2\2\2\4\'\65";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}