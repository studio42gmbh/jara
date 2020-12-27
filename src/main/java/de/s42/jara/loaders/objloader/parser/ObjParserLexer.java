// Generated from src/de/s42/jara/loaders/objloader/ObjParser.g4 by ANTLR 4.8
package de.s42.jara.loaders.objloader.parser;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class ObjParserLexer extends Lexer {
	static { RuntimeMetaData.checkVersion("4.8", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		KEYWORD_MTLLIB=1, KEYWORD_OBJECT=2, KEYWORD_GROUP=3, KEYWORD_POSITION=4, 
		KEYWORD_NORMAL=5, KEYWORD_TEXTUREPOSITION=6, KEYWORD_USEMATERIAL=7, KEYWORD_SMOOTH=8, 
		KEYWORD_FACE=9, KEYWORD_OFF=10, SLASH=11, INTEGER=12, FLOAT=13, SYMBOL=14, 
		COMMENT=15, NEWLINE=16, WS=17;
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	private static String[] makeRuleNames() {
		return new String[] {
			"KEYWORD_MTLLIB", "KEYWORD_OBJECT", "KEYWORD_GROUP", "KEYWORD_POSITION", 
			"KEYWORD_NORMAL", "KEYWORD_TEXTUREPOSITION", "KEYWORD_USEMATERIAL", "KEYWORD_SMOOTH", 
			"KEYWORD_FACE", "KEYWORD_OFF", "SLASH", "INTEGER", "FLOAT", "SYMBOL", 
			"COMMENT", "NEWLINE", "WS"
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


	public ObjParserLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "ObjParser.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public String[] getChannelNames() { return channelNames; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\2\23y\b\1\4\2\t\2\4"+
		"\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t"+
		"\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\3\3\3\3\4\3\4\3\5\3\5\3\6\3\6\3\6\3\7\3"+
		"\7\3\7\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\t\3\t\3\n\3\n\3\13\3\13\3\13\3\13"+
		"\3\f\3\f\3\r\5\rK\n\r\3\r\6\rN\n\r\r\r\16\rO\3\16\5\16S\n\16\3\16\6\16"+
		"V\n\16\r\16\16\16W\3\16\3\16\6\16\\\n\16\r\16\16\16]\3\17\3\17\7\17b\n"+
		"\17\f\17\16\17e\13\17\3\20\3\20\6\20i\n\20\r\20\16\20j\3\20\3\20\3\20"+
		"\3\20\3\21\3\21\3\22\6\22t\n\22\r\22\16\22u\3\22\3\22\2\2\23\3\3\5\4\7"+
		"\5\t\6\13\7\r\b\17\t\21\n\23\13\25\f\27\r\31\16\33\17\35\20\37\21!\22"+
		"#\23\3\2\b\3\2//\3\2\62;\5\2\62;C\\c|\7\2/\60\62;C\\aac|\3\2\f\f\5\2\13"+
		"\13\17\17\"\"\2\u0080\2\3\3\2\2\2\2\5\3\2\2\2\2\7\3\2\2\2\2\t\3\2\2\2"+
		"\2\13\3\2\2\2\2\r\3\2\2\2\2\17\3\2\2\2\2\21\3\2\2\2\2\23\3\2\2\2\2\25"+
		"\3\2\2\2\2\27\3\2\2\2\2\31\3\2\2\2\2\33\3\2\2\2\2\35\3\2\2\2\2\37\3\2"+
		"\2\2\2!\3\2\2\2\2#\3\2\2\2\3%\3\2\2\2\5,\3\2\2\2\7.\3\2\2\2\t\60\3\2\2"+
		"\2\13\62\3\2\2\2\r\65\3\2\2\2\178\3\2\2\2\21?\3\2\2\2\23A\3\2\2\2\25C"+
		"\3\2\2\2\27G\3\2\2\2\31J\3\2\2\2\33R\3\2\2\2\35_\3\2\2\2\37f\3\2\2\2!"+
		"p\3\2\2\2#s\3\2\2\2%&\7o\2\2&\'\7v\2\2\'(\7n\2\2()\7n\2\2)*\7k\2\2*+\7"+
		"d\2\2+\4\3\2\2\2,-\7q\2\2-\6\3\2\2\2./\7i\2\2/\b\3\2\2\2\60\61\7x\2\2"+
		"\61\n\3\2\2\2\62\63\7x\2\2\63\64\7p\2\2\64\f\3\2\2\2\65\66\7x\2\2\66\67"+
		"\7v\2\2\67\16\3\2\2\289\7w\2\29:\7u\2\2:;\7g\2\2;<\7o\2\2<=\7v\2\2=>\7"+
		"n\2\2>\20\3\2\2\2?@\7u\2\2@\22\3\2\2\2AB\7h\2\2B\24\3\2\2\2CD\7q\2\2D"+
		"E\7h\2\2EF\7h\2\2F\26\3\2\2\2GH\7\61\2\2H\30\3\2\2\2IK\t\2\2\2JI\3\2\2"+
		"\2JK\3\2\2\2KM\3\2\2\2LN\t\3\2\2ML\3\2\2\2NO\3\2\2\2OM\3\2\2\2OP\3\2\2"+
		"\2P\32\3\2\2\2QS\t\2\2\2RQ\3\2\2\2RS\3\2\2\2SU\3\2\2\2TV\t\3\2\2UT\3\2"+
		"\2\2VW\3\2\2\2WU\3\2\2\2WX\3\2\2\2XY\3\2\2\2Y[\7\60\2\2Z\\\t\3\2\2[Z\3"+
		"\2\2\2\\]\3\2\2\2][\3\2\2\2]^\3\2\2\2^\34\3\2\2\2_c\t\4\2\2`b\t\5\2\2"+
		"a`\3\2\2\2be\3\2\2\2ca\3\2\2\2cd\3\2\2\2d\36\3\2\2\2ec\3\2\2\2fh\7%\2"+
		"\2gi\n\6\2\2hg\3\2\2\2ij\3\2\2\2jh\3\2\2\2jk\3\2\2\2kl\3\2\2\2lm\5!\21"+
		"\2mn\3\2\2\2no\b\20\2\2o \3\2\2\2pq\t\6\2\2q\"\3\2\2\2rt\t\7\2\2sr\3\2"+
		"\2\2tu\3\2\2\2us\3\2\2\2uv\3\2\2\2vw\3\2\2\2wx\b\22\2\2x$\3\2\2\2\13\2"+
		"JORW]cju\3\b\2\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}