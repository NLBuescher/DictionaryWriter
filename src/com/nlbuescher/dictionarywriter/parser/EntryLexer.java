// Generated from Entry.g4 by ANTLR 4.5.3
package com.nlbuescher.dictionarywriter.parser;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class EntryLexer extends Lexer {
	static { RuntimeMetaData.checkVersion("4.5.3", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		WhiteSpace=1, End=2, Indent=3, Newline=4, Number=5, DefinitionLabel=6, 
		Heading=7, Pronunciation=8, Grammar=9, Form=10, DefinitionGroupHeading=11, 
		Definition=12, SubDefinition=13, SubEntryLabel=14, SubEntryText=15, SubEntryListItem=16, 
		Note=17, ExampleText=18, ExampleTranslation=19;
	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	public static final String[] ruleNames = {
		"WhiteSpace", "End", "Indent", "Newline", "Number", "DefinitionLabel", 
		"Heading", "Pronunciation", "Grammar", "Form", "DefinitionGroupHeading", 
		"Definition", "SubDefinition", "SubEntryLabel", "SubEntryText", "SubEntryListItem", 
		"Note", "ExampleText", "ExampleTranslation"
	};

	private static final String[] _LITERAL_NAMES = {
	};
	private static final String[] _SYMBOLIC_NAMES = {
		null, "WhiteSpace", "End", "Indent", "Newline", "Number", "DefinitionLabel", 
		"Heading", "Pronunciation", "Grammar", "Form", "DefinitionGroupHeading", 
		"Definition", "SubDefinition", "SubEntryLabel", "SubEntryText", "SubEntryListItem", 
		"Note", "ExampleText", "ExampleTranslation"
	};
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


	public EntryLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "Entry.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	public static final String _serializedATN =
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\2\25\u0104\b\1\4\2"+
		"\t\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4"+
		"\13\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22"+
		"\t\22\4\23\t\23\4\24\t\24\3\2\3\2\5\2,\n\2\3\2\3\2\3\3\3\3\6\3\62\n\3"+
		"\r\3\16\3\63\3\3\3\3\3\3\3\3\5\3:\n\3\3\3\3\3\3\4\3\4\3\4\3\4\3\4\5\4"+
		"C\n\4\3\5\3\5\3\6\6\6H\n\6\r\6\16\6I\3\7\3\7\3\7\3\7\3\7\3\7\5\7R\n\7"+
		"\3\b\3\b\3\b\3\b\6\bX\n\b\r\b\16\bY\3\b\3\b\3\b\3\t\3\t\6\ta\n\t\r\t\16"+
		"\tb\3\t\3\t\3\t\6\th\n\t\r\t\16\ti\3\t\3\t\3\n\3\n\3\n\3\n\3\n\7\ns\n"+
		"\n\f\n\16\nv\13\n\3\n\3\n\3\n\3\n\3\13\3\13\6\13~\n\13\r\13\16\13\177"+
		"\3\13\3\13\3\13\6\13\u0085\n\13\r\13\16\13\u0086\3\13\3\13\3\f\3\f\3\f"+
		"\3\f\3\f\3\f\7\f\u0091\n\f\f\f\16\f\u0094\13\f\3\f\3\f\3\f\3\f\3\f\3\r"+
		"\3\r\7\r\u009d\n\r\f\r\16\r\u00a0\13\r\3\r\3\r\3\16\3\16\3\16\3\16\3\16"+
		"\7\16\u00a9\n\16\f\16\16\16\u00ac\13\16\3\16\3\16\3\17\6\17\u00b1\n\17"+
		"\r\17\16\17\u00b2\3\17\3\17\6\17\u00b7\n\17\r\17\16\17\u00b8\3\17\3\17"+
		"\3\20\3\20\3\20\3\20\3\20\7\20\u00c2\n\20\f\20\16\20\u00c5\13\20\3\20"+
		"\3\20\3\20\3\20\3\21\3\21\3\21\3\21\3\21\7\21\u00d0\n\21\f\21\16\21\u00d3"+
		"\13\21\3\21\3\21\3\22\3\22\3\22\3\22\3\22\7\22\u00dc\n\22\f\22\16\22\u00df"+
		"\13\22\3\22\3\22\3\22\3\22\3\23\5\23\u00e6\n\23\3\23\3\23\7\23\u00ea\n"+
		"\23\f\23\16\23\u00ed\13\23\3\23\3\23\5\23\u00f1\n\23\3\24\5\24\u00f4\n"+
		"\24\3\24\3\24\3\24\3\24\3\24\3\24\7\24\u00fc\n\24\f\24\16\24\u00ff\13"+
		"\24\3\24\3\24\5\24\u0103\n\24\21Ybit\177\u0086\u0092\u009e\u00aa\u00b2"+
		"\u00c3\u00d1\u00dd\u00eb\u00fd\2\25\3\3\5\4\7\5\t\6\13\7\r\b\17\t\21\n"+
		"\23\13\25\f\27\r\31\16\33\17\35\20\37\21!\22#\23%\24\'\25\3\2\7\4\2\13"+
		"\13\"\"\4\2\f\f\17\17\3\2\62;\6\2\f\f\17\17**~~\6\2\f\f\17\17**}}\u011e"+
		"\2\3\3\2\2\2\2\5\3\2\2\2\2\7\3\2\2\2\2\t\3\2\2\2\2\13\3\2\2\2\2\r\3\2"+
		"\2\2\2\17\3\2\2\2\2\21\3\2\2\2\2\23\3\2\2\2\2\25\3\2\2\2\2\27\3\2\2\2"+
		"\2\31\3\2\2\2\2\33\3\2\2\2\2\35\3\2\2\2\2\37\3\2\2\2\2!\3\2\2\2\2#\3\2"+
		"\2\2\2%\3\2\2\2\2\'\3\2\2\2\3+\3\2\2\2\59\3\2\2\2\7B\3\2\2\2\tD\3\2\2"+
		"\2\13G\3\2\2\2\rQ\3\2\2\2\17S\3\2\2\2\21^\3\2\2\2\23m\3\2\2\2\25{\3\2"+
		"\2\2\27\u008a\3\2\2\2\31\u009a\3\2\2\2\33\u00a3\3\2\2\2\35\u00b0\3\2\2"+
		"\2\37\u00bc\3\2\2\2!\u00ca\3\2\2\2#\u00d6\3\2\2\2%\u00e5\3\2\2\2\'\u00f3"+
		"\3\2\2\2),\5\t\5\2*,\t\2\2\2+)\3\2\2\2+*\3\2\2\2,-\3\2\2\2-.\b\2\2\2."+
		"\4\3\2\2\2/\61\5\t\5\2\60\62\5\t\5\2\61\60\3\2\2\2\62\63\3\2\2\2\63\61"+
		"\3\2\2\2\63\64\3\2\2\2\64:\3\2\2\2\65\66\5\t\5\2\66\67\7\2\2\3\67:\3\2"+
		"\2\28:\7\2\2\39/\3\2\2\29\65\3\2\2\298\3\2\2\2:;\3\2\2\2;<\b\3\2\2<\6"+
		"\3\2\2\2=>\7\"\2\2>?\7\"\2\2?@\7\"\2\2@C\7\"\2\2AC\7\13\2\2B=\3\2\2\2"+
		"BA\3\2\2\2C\b\3\2\2\2DE\t\3\2\2E\n\3\2\2\2FH\t\4\2\2GF\3\2\2\2HI\3\2\2"+
		"\2IG\3\2\2\2IJ\3\2\2\2J\f\3\2\2\2KL\7/\2\2LR\7\"\2\2MN\5\13\6\2NO\7\60"+
		"\2\2OP\7\"\2\2PR\3\2\2\2QK\3\2\2\2QM\3\2\2\2R\16\3\2\2\2ST\7%\2\2TU\7"+
		"\"\2\2UW\3\2\2\2VX\n\3\2\2WV\3\2\2\2XY\3\2\2\2YZ\3\2\2\2YW\3\2\2\2Z[\3"+
		"\2\2\2[\\\7\"\2\2\\]\7%\2\2]\20\3\2\2\2^`\7~\2\2_a\n\5\2\2`_\3\2\2\2a"+
		"b\3\2\2\2bc\3\2\2\2b`\3\2\2\2cd\3\2\2\2de\7~\2\2eg\7*\2\2fh\n\5\2\2gf"+
		"\3\2\2\2hi\3\2\2\2ij\3\2\2\2ig\3\2\2\2jk\3\2\2\2kl\7+\2\2l\22\3\2\2\2"+
		"mn\7%\2\2no\7%\2\2op\7\"\2\2pt\3\2\2\2qs\n\3\2\2rq\3\2\2\2sv\3\2\2\2t"+
		"u\3\2\2\2tr\3\2\2\2uw\3\2\2\2vt\3\2\2\2wx\7\"\2\2xy\7%\2\2yz\7%\2\2z\24"+
		"\3\2\2\2{}\7}\2\2|~\n\6\2\2}|\3\2\2\2~\177\3\2\2\2\177\u0080\3\2\2\2\177"+
		"}\3\2\2\2\u0080\u0081\3\2\2\2\u0081\u0082\7\177\2\2\u0082\u0084\7*\2\2"+
		"\u0083\u0085\n\6\2\2\u0084\u0083\3\2\2\2\u0085\u0086\3\2\2\2\u0086\u0087"+
		"\3\2\2\2\u0086\u0084\3\2\2\2\u0087\u0088\3\2\2\2\u0088\u0089\7+\2\2\u0089"+
		"\26\3\2\2\2\u008a\u008b\7%\2\2\u008b\u008c\7%\2\2\u008c\u008d\7%\2\2\u008d"+
		"\u008e\7\"\2\2\u008e\u0092\3\2\2\2\u008f\u0091\n\3\2\2\u0090\u008f\3\2"+
		"\2\2\u0091\u0094\3\2\2\2\u0092\u0093\3\2\2\2\u0092\u0090\3\2\2\2\u0093"+
		"\u0095\3\2\2\2\u0094\u0092\3\2\2\2\u0095\u0096\7\"\2\2\u0096\u0097\7%"+
		"\2\2\u0097\u0098\7%\2\2\u0098\u0099\7%\2\2\u0099\30\3\2\2\2\u009a\u009e"+
		"\5\r\7\2\u009b\u009d\n\3\2\2\u009c\u009b\3\2\2\2\u009d\u00a0\3\2\2\2\u009e"+
		"\u009f\3\2\2\2\u009e\u009c\3\2\2\2\u009f\u00a1\3\2\2\2\u00a0\u009e\3\2"+
		"\2\2\u00a1\u00a2\5\t\5\2\u00a2\32\3\2\2\2\u00a3\u00a4\5\7\4\2\u00a4\u00a5"+
		"\7/\2\2\u00a5\u00a6\7\"\2\2\u00a6\u00aa\3\2\2\2\u00a7\u00a9\n\3\2\2\u00a8"+
		"\u00a7\3\2\2\2\u00a9\u00ac\3\2\2\2\u00aa\u00ab\3\2\2\2\u00aa\u00a8\3\2"+
		"\2\2\u00ab\u00ad\3\2\2\2\u00ac\u00aa\3\2\2\2\u00ad\u00ae\5\t\5\2\u00ae"+
		"\34\3\2\2\2\u00af\u00b1\n\3\2\2\u00b0\u00af\3\2\2\2\u00b1\u00b2\3\2\2"+
		"\2\u00b2\u00b3\3\2\2\2\u00b2\u00b0\3\2\2\2\u00b3\u00b4\3\2\2\2\u00b4\u00b6"+
		"\5\t\5\2\u00b5\u00b7\7/\2\2\u00b6\u00b5\3\2\2\2\u00b7\u00b8\3\2\2\2\u00b8"+
		"\u00b6\3\2\2\2\u00b8\u00b9\3\2\2\2\u00b9\u00ba\3\2\2\2\u00ba\u00bb\5\t"+
		"\5\2\u00bb\36\3\2\2\2\u00bc\u00bd\7)\2\2\u00bd\u00be\7)\2\2\u00be\u00bf"+
		"\7)\2\2\u00bf\u00c3\3\2\2\2\u00c0\u00c2\13\2\2\2\u00c1\u00c0\3\2\2\2\u00c2"+
		"\u00c5\3\2\2\2\u00c3\u00c4\3\2\2\2\u00c3\u00c1\3\2\2\2\u00c4\u00c6\3\2"+
		"\2\2\u00c5\u00c3\3\2\2\2\u00c6\u00c7\7)\2\2\u00c7\u00c8\7)\2\2\u00c8\u00c9"+
		"\7)\2\2\u00c9 \3\2\2\2\u00ca\u00cb\7/\2\2\u00cb\u00cc\7/\2\2\u00cc\u00cd"+
		"\7\"\2\2\u00cd\u00d1\3\2\2\2\u00ce\u00d0\n\3\2\2\u00cf\u00ce\3\2\2\2\u00d0"+
		"\u00d3\3\2\2\2\u00d1\u00d2\3\2\2\2\u00d1\u00cf\3\2\2\2\u00d2\u00d4\3\2"+
		"\2\2\u00d3\u00d1\3\2\2\2\u00d4\u00d5\5\t\5\2\u00d5\"\3\2\2\2\u00d6\u00d7"+
		"\7$\2\2\u00d7\u00d8\7$\2\2\u00d8\u00d9\7$\2\2\u00d9\u00dd\3\2\2\2\u00da"+
		"\u00dc\13\2\2\2\u00db\u00da\3\2\2\2\u00dc\u00df\3\2\2\2\u00dd\u00de\3"+
		"\2\2\2\u00dd\u00db\3\2\2\2\u00de\u00e0\3\2\2\2\u00df\u00dd\3\2\2\2\u00e0"+
		"\u00e1\7$\2\2\u00e1\u00e2\7$\2\2\u00e2\u00e3\7$\2\2\u00e3$\3\2\2\2\u00e4"+
		"\u00e6\5\7\4\2\u00e5\u00e4\3\2\2\2\u00e5\u00e6\3\2\2\2\u00e6\u00e7\3\2"+
		"\2\2\u00e7\u00eb\7<\2\2\u00e8\u00ea\n\3\2\2\u00e9\u00e8\3\2\2\2\u00ea"+
		"\u00ed\3\2\2\2\u00eb\u00ec\3\2\2\2\u00eb\u00e9\3\2\2\2\u00ec\u00f0\3\2"+
		"\2\2\u00ed\u00eb\3\2\2\2\u00ee\u00f1\5\t\5\2\u00ef\u00f1\7\2\2\3\u00f0"+
		"\u00ee\3\2\2\2\u00f0\u00ef\3\2\2\2\u00f1&\3\2\2\2\u00f2\u00f4\5\7\4\2"+
		"\u00f3\u00f2\3\2\2\2\u00f3\u00f4\3\2\2\2\u00f4\u00f5\3\2\2\2\u00f5\u00f6"+
		"\7/\2\2\u00f6\u00f7\7/\2\2\u00f7\u00f8\7/\2\2\u00f8\u00f9\7\"\2\2\u00f9"+
		"\u00fd\3\2\2\2\u00fa\u00fc\n\3\2\2\u00fb\u00fa\3\2\2\2\u00fc\u00ff\3\2"+
		"\2\2\u00fd\u00fe\3\2\2\2\u00fd\u00fb\3\2\2\2\u00fe\u0102\3\2\2\2\u00ff"+
		"\u00fd\3\2\2\2\u0100\u0103\5\t\5\2\u0101\u0103\7\2\2\3\u0102\u0100\3\2"+
		"\2\2\u0102\u0101\3\2\2\2\u0103(\3\2\2\2\35\2+\639BIQYbit\177\u0086\u0092"+
		"\u009e\u00aa\u00b2\u00b8\u00c3\u00d1\u00dd\u00e5\u00eb\u00f0\u00f3\u00fd"+
		"\u0102\3\b\2\2";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}