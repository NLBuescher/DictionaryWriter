// Generated from Entry.g4 by ANTLR 4.5.3
package com.nlbuescher.dictionarywriter.parser;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class EntryParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.5.3", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		WhiteSpace=1, End=2, Indent=3, Newline=4, Number=5, DefinitionLabel=6, 
		Heading=7, Pronunciation=8, Grammar=9, Form=10, DefinitionGroupHeading=11, 
		Definition=12, SubDefinition=13, SubEntryLabel=14, SubEntryText=15, SubEntryListItem=16, 
		Note=17, ExampleText=18, ExampleTranslation=19;
	public static final int
		RULE_d_entry = 0, RULE_headGroup = 1, RULE_heading = 2, RULE_pronunciationGroup = 3, 
		RULE_pronunciation = 4, RULE_entryGroup = 5, RULE_entry = 6, RULE_grammarGroup = 7, 
		RULE_grammer = 8, RULE_formGroup = 9, RULE_form = 10, RULE_definitionGroup = 11, 
		RULE_definitionGroupHeading = 12, RULE_definition = 13, RULE_exampleGroup = 14, 
		RULE_example = 15, RULE_exampleText = 16, RULE_exampleTranslation = 17, 
		RULE_subDefinitionGroup = 18, RULE_subDefinition = 19, RULE_subEntryGroup = 20, 
		RULE_subEntry = 21, RULE_subEntryList = 22, RULE_subEntryListItem = 23, 
		RULE_noteGroup = 24, RULE_note = 25;
	public static final String[] ruleNames = {
		"d_entry", "headGroup", "heading", "pronunciationGroup", "pronunciation", 
		"entryGroup", "entry", "grammarGroup", "grammer", "formGroup", "form", 
		"definitionGroup", "definitionGroupHeading", "definition", "exampleGroup", 
		"example", "exampleText", "exampleTranslation", "subDefinitionGroup", 
		"subDefinition", "subEntryGroup", "subEntry", "subEntryList", "subEntryListItem", 
		"noteGroup", "note"
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

	@Override
	public String getGrammarFileName() { return "Entry.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public EntryParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}
	public static class D_entryContext extends ParserRuleContext {
		public HeadGroupContext headGroup() {
			return getRuleContext(HeadGroupContext.class,0);
		}
		public EntryGroupContext entryGroup() {
			return getRuleContext(EntryGroupContext.class,0);
		}
		public D_entryContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_d_entry; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof EntryListener ) ((EntryListener)listener).enterD_entry(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof EntryListener ) ((EntryListener)listener).exitD_entry(this);
		}
	}

	public final D_entryContext d_entry() throws RecognitionException {
		D_entryContext _localctx = new D_entryContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_d_entry);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(53);
			_la = _input.LA(1);
			if (_la==Heading) {
				{
				setState(52);
				headGroup();
				}
			}

			setState(56);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << Grammar) | (1L << DefinitionGroupHeading) | (1L << Definition))) != 0)) {
				{
				setState(55);
				entryGroup();
				}
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

	public static class HeadGroupContext extends ParserRuleContext {
		public HeadingContext heading() {
			return getRuleContext(HeadingContext.class,0);
		}
		public PronunciationGroupContext pronunciationGroup() {
			return getRuleContext(PronunciationGroupContext.class,0);
		}
		public HeadGroupContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_headGroup; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof EntryListener ) ((EntryListener)listener).enterHeadGroup(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof EntryListener ) ((EntryListener)listener).exitHeadGroup(this);
		}
	}

	public final HeadGroupContext headGroup() throws RecognitionException {
		HeadGroupContext _localctx = new HeadGroupContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_headGroup);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(58);
			heading();
			setState(60);
			_la = _input.LA(1);
			if (_la==Indent || _la==Pronunciation) {
				{
				setState(59);
				pronunciationGroup();
				}
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

	public static class HeadingContext extends ParserRuleContext {
		public TerminalNode Heading() { return getToken(EntryParser.Heading, 0); }
		public HeadingContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_heading; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof EntryListener ) ((EntryListener)listener).enterHeading(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof EntryListener ) ((EntryListener)listener).exitHeading(this);
		}
	}

	public final HeadingContext heading() throws RecognitionException {
		HeadingContext _localctx = new HeadingContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_heading);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(62);
			match(Heading);
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

	public static class PronunciationGroupContext extends ParserRuleContext {
		public List<PronunciationContext> pronunciation() {
			return getRuleContexts(PronunciationContext.class);
		}
		public PronunciationContext pronunciation(int i) {
			return getRuleContext(PronunciationContext.class,i);
		}
		public List<TerminalNode> Indent() { return getTokens(EntryParser.Indent); }
		public TerminalNode Indent(int i) {
			return getToken(EntryParser.Indent, i);
		}
		public PronunciationGroupContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_pronunciationGroup; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof EntryListener ) ((EntryListener)listener).enterPronunciationGroup(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof EntryListener ) ((EntryListener)listener).exitPronunciationGroup(this);
		}
	}

	public final PronunciationGroupContext pronunciationGroup() throws RecognitionException {
		PronunciationGroupContext _localctx = new PronunciationGroupContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_pronunciationGroup);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(68); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(65);
				_la = _input.LA(1);
				if (_la==Indent) {
					{
					setState(64);
					match(Indent);
					}
				}

				setState(67);
				pronunciation();
				}
				}
				setState(70); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( _la==Indent || _la==Pronunciation );
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

	public static class PronunciationContext extends ParserRuleContext {
		public TerminalNode Pronunciation() { return getToken(EntryParser.Pronunciation, 0); }
		public PronunciationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_pronunciation; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof EntryListener ) ((EntryListener)listener).enterPronunciation(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof EntryListener ) ((EntryListener)listener).exitPronunciation(this);
		}
	}

	public final PronunciationContext pronunciation() throws RecognitionException {
		PronunciationContext _localctx = new PronunciationContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_pronunciation);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(72);
			match(Pronunciation);
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

	public static class EntryGroupContext extends ParserRuleContext {
		public List<EntryContext> entry() {
			return getRuleContexts(EntryContext.class);
		}
		public EntryContext entry(int i) {
			return getRuleContext(EntryContext.class,i);
		}
		public EntryGroupContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_entryGroup; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof EntryListener ) ((EntryListener)listener).enterEntryGroup(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof EntryListener ) ((EntryListener)listener).exitEntryGroup(this);
		}
	}

	public final EntryGroupContext entryGroup() throws RecognitionException {
		EntryGroupContext _localctx = new EntryGroupContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_entryGroup);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(75); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(74);
				entry();
				}
				}
				setState(77); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( (((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << Grammar) | (1L << DefinitionGroupHeading) | (1L << Definition))) != 0) );
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

	public static class EntryContext extends ParserRuleContext {
		public GrammarGroupContext grammarGroup() {
			return getRuleContext(GrammarGroupContext.class,0);
		}
		public List<DefinitionGroupContext> definitionGroup() {
			return getRuleContexts(DefinitionGroupContext.class);
		}
		public DefinitionGroupContext definitionGroup(int i) {
			return getRuleContext(DefinitionGroupContext.class,i);
		}
		public SubEntryGroupContext subEntryGroup() {
			return getRuleContext(SubEntryGroupContext.class,0);
		}
		public EntryContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_entry; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof EntryListener ) ((EntryListener)listener).enterEntry(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof EntryListener ) ((EntryListener)listener).exitEntry(this);
		}
	}

	public final EntryContext entry() throws RecognitionException {
		EntryContext _localctx = new EntryContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_entry);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(80);
			_la = _input.LA(1);
			if (_la==Grammar) {
				{
				setState(79);
				grammarGroup();
				}
			}

			setState(83); 
			_errHandler.sync(this);
			_alt = 1;
			do {
				switch (_alt) {
				case 1:
					{
					{
					setState(82);
					definitionGroup();
					}
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				setState(85); 
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,7,_ctx);
			} while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER );
			setState(88);
			_la = _input.LA(1);
			if (_la==SubEntryLabel) {
				{
				setState(87);
				subEntryGroup();
				}
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

	public static class GrammarGroupContext extends ParserRuleContext {
		public GrammerContext grammer() {
			return getRuleContext(GrammerContext.class,0);
		}
		public FormGroupContext formGroup() {
			return getRuleContext(FormGroupContext.class,0);
		}
		public GrammarGroupContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_grammarGroup; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof EntryListener ) ((EntryListener)listener).enterGrammarGroup(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof EntryListener ) ((EntryListener)listener).exitGrammarGroup(this);
		}
	}

	public final GrammarGroupContext grammarGroup() throws RecognitionException {
		GrammarGroupContext _localctx = new GrammarGroupContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_grammarGroup);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(90);
			grammer();
			setState(92);
			_la = _input.LA(1);
			if (_la==Form) {
				{
				setState(91);
				formGroup();
				}
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

	public static class GrammerContext extends ParserRuleContext {
		public TerminalNode Grammar() { return getToken(EntryParser.Grammar, 0); }
		public GrammerContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_grammer; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof EntryListener ) ((EntryListener)listener).enterGrammer(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof EntryListener ) ((EntryListener)listener).exitGrammer(this);
		}
	}

	public final GrammerContext grammer() throws RecognitionException {
		GrammerContext _localctx = new GrammerContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_grammer);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(94);
			match(Grammar);
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

	public static class FormGroupContext extends ParserRuleContext {
		public List<FormContext> form() {
			return getRuleContexts(FormContext.class);
		}
		public FormContext form(int i) {
			return getRuleContext(FormContext.class,i);
		}
		public FormGroupContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_formGroup; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof EntryListener ) ((EntryListener)listener).enterFormGroup(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof EntryListener ) ((EntryListener)listener).exitFormGroup(this);
		}
	}

	public final FormGroupContext formGroup() throws RecognitionException {
		FormGroupContext _localctx = new FormGroupContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_formGroup);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(97); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(96);
				form();
				}
				}
				setState(99); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( _la==Form );
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

	public static class FormContext extends ParserRuleContext {
		public TerminalNode Form() { return getToken(EntryParser.Form, 0); }
		public PronunciationGroupContext pronunciationGroup() {
			return getRuleContext(PronunciationGroupContext.class,0);
		}
		public FormContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_form; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof EntryListener ) ((EntryListener)listener).enterForm(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof EntryListener ) ((EntryListener)listener).exitForm(this);
		}
	}

	public final FormContext form() throws RecognitionException {
		FormContext _localctx = new FormContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_form);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(101);
			match(Form);
			setState(103);
			_la = _input.LA(1);
			if (_la==Indent || _la==Pronunciation) {
				{
				setState(102);
				pronunciationGroup();
				}
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

	public static class DefinitionGroupContext extends ParserRuleContext {
		public DefinitionGroupHeadingContext definitionGroupHeading() {
			return getRuleContext(DefinitionGroupHeadingContext.class,0);
		}
		public List<DefinitionContext> definition() {
			return getRuleContexts(DefinitionContext.class);
		}
		public DefinitionContext definition(int i) {
			return getRuleContext(DefinitionContext.class,i);
		}
		public DefinitionGroupContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_definitionGroup; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof EntryListener ) ((EntryListener)listener).enterDefinitionGroup(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof EntryListener ) ((EntryListener)listener).exitDefinitionGroup(this);
		}
	}

	public final DefinitionGroupContext definitionGroup() throws RecognitionException {
		DefinitionGroupContext _localctx = new DefinitionGroupContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_definitionGroup);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(106);
			_la = _input.LA(1);
			if (_la==DefinitionGroupHeading) {
				{
				setState(105);
				definitionGroupHeading();
				}
			}

			setState(109); 
			_errHandler.sync(this);
			_alt = 1;
			do {
				switch (_alt) {
				case 1:
					{
					{
					setState(108);
					definition();
					}
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				setState(111); 
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,13,_ctx);
			} while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER );
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

	public static class DefinitionGroupHeadingContext extends ParserRuleContext {
		public TerminalNode DefinitionGroupHeading() { return getToken(EntryParser.DefinitionGroupHeading, 0); }
		public DefinitionGroupHeadingContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_definitionGroupHeading; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof EntryListener ) ((EntryListener)listener).enterDefinitionGroupHeading(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof EntryListener ) ((EntryListener)listener).exitDefinitionGroupHeading(this);
		}
	}

	public final DefinitionGroupHeadingContext definitionGroupHeading() throws RecognitionException {
		DefinitionGroupHeadingContext _localctx = new DefinitionGroupHeadingContext(_ctx, getState());
		enterRule(_localctx, 24, RULE_definitionGroupHeading);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(113);
			match(DefinitionGroupHeading);
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

	public static class DefinitionContext extends ParserRuleContext {
		public TerminalNode Definition() { return getToken(EntryParser.Definition, 0); }
		public ExampleGroupContext exampleGroup() {
			return getRuleContext(ExampleGroupContext.class,0);
		}
		public SubDefinitionGroupContext subDefinitionGroup() {
			return getRuleContext(SubDefinitionGroupContext.class,0);
		}
		public DefinitionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_definition; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof EntryListener ) ((EntryListener)listener).enterDefinition(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof EntryListener ) ((EntryListener)listener).exitDefinition(this);
		}
	}

	public final DefinitionContext definition() throws RecognitionException {
		DefinitionContext _localctx = new DefinitionContext(_ctx, getState());
		enterRule(_localctx, 26, RULE_definition);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(115);
			match(Definition);
			setState(117);
			_la = _input.LA(1);
			if (_la==Indent || _la==ExampleText) {
				{
				setState(116);
				exampleGroup();
				}
			}

			setState(120);
			_la = _input.LA(1);
			if (_la==SubDefinition) {
				{
				setState(119);
				subDefinitionGroup();
				}
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

	public static class ExampleGroupContext extends ParserRuleContext {
		public List<ExampleContext> example() {
			return getRuleContexts(ExampleContext.class);
		}
		public ExampleContext example(int i) {
			return getRuleContext(ExampleContext.class,i);
		}
		public ExampleGroupContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_exampleGroup; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof EntryListener ) ((EntryListener)listener).enterExampleGroup(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof EntryListener ) ((EntryListener)listener).exitExampleGroup(this);
		}
	}

	public final ExampleGroupContext exampleGroup() throws RecognitionException {
		ExampleGroupContext _localctx = new ExampleGroupContext(_ctx, getState());
		enterRule(_localctx, 28, RULE_exampleGroup);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(123); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(122);
				example();
				}
				}
				setState(125); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( _la==Indent || _la==ExampleText );
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

	public static class ExampleContext extends ParserRuleContext {
		public ExampleTextContext exampleText() {
			return getRuleContext(ExampleTextContext.class,0);
		}
		public ExampleTranslationContext exampleTranslation() {
			return getRuleContext(ExampleTranslationContext.class,0);
		}
		public ExampleContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_example; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof EntryListener ) ((EntryListener)listener).enterExample(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof EntryListener ) ((EntryListener)listener).exitExample(this);
		}
	}

	public final ExampleContext example() throws RecognitionException {
		ExampleContext _localctx = new ExampleContext(_ctx, getState());
		enterRule(_localctx, 30, RULE_example);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(127);
			exampleText();
			setState(129);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,17,_ctx) ) {
			case 1:
				{
				setState(128);
				exampleTranslation();
				}
				break;
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

	public static class ExampleTextContext extends ParserRuleContext {
		public TerminalNode ExampleText() { return getToken(EntryParser.ExampleText, 0); }
		public TerminalNode Indent() { return getToken(EntryParser.Indent, 0); }
		public ExampleTextContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_exampleText; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof EntryListener ) ((EntryListener)listener).enterExampleText(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof EntryListener ) ((EntryListener)listener).exitExampleText(this);
		}
	}

	public final ExampleTextContext exampleText() throws RecognitionException {
		ExampleTextContext _localctx = new ExampleTextContext(_ctx, getState());
		enterRule(_localctx, 32, RULE_exampleText);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(132);
			_la = _input.LA(1);
			if (_la==Indent) {
				{
				setState(131);
				match(Indent);
				}
			}

			setState(134);
			match(ExampleText);
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

	public static class ExampleTranslationContext extends ParserRuleContext {
		public TerminalNode ExampleTranslation() { return getToken(EntryParser.ExampleTranslation, 0); }
		public TerminalNode Indent() { return getToken(EntryParser.Indent, 0); }
		public ExampleTranslationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_exampleTranslation; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof EntryListener ) ((EntryListener)listener).enterExampleTranslation(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof EntryListener ) ((EntryListener)listener).exitExampleTranslation(this);
		}
	}

	public final ExampleTranslationContext exampleTranslation() throws RecognitionException {
		ExampleTranslationContext _localctx = new ExampleTranslationContext(_ctx, getState());
		enterRule(_localctx, 34, RULE_exampleTranslation);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(137);
			_la = _input.LA(1);
			if (_la==Indent) {
				{
				setState(136);
				match(Indent);
				}
			}

			setState(139);
			match(ExampleTranslation);
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

	public static class SubDefinitionGroupContext extends ParserRuleContext {
		public List<SubDefinitionContext> subDefinition() {
			return getRuleContexts(SubDefinitionContext.class);
		}
		public SubDefinitionContext subDefinition(int i) {
			return getRuleContext(SubDefinitionContext.class,i);
		}
		public SubDefinitionGroupContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_subDefinitionGroup; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof EntryListener ) ((EntryListener)listener).enterSubDefinitionGroup(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof EntryListener ) ((EntryListener)listener).exitSubDefinitionGroup(this);
		}
	}

	public final SubDefinitionGroupContext subDefinitionGroup() throws RecognitionException {
		SubDefinitionGroupContext _localctx = new SubDefinitionGroupContext(_ctx, getState());
		enterRule(_localctx, 36, RULE_subDefinitionGroup);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(142); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(141);
				subDefinition();
				}
				}
				setState(144); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( _la==SubDefinition );
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

	public static class SubDefinitionContext extends ParserRuleContext {
		public TerminalNode SubDefinition() { return getToken(EntryParser.SubDefinition, 0); }
		public ExampleGroupContext exampleGroup() {
			return getRuleContext(ExampleGroupContext.class,0);
		}
		public SubDefinitionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_subDefinition; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof EntryListener ) ((EntryListener)listener).enterSubDefinition(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof EntryListener ) ((EntryListener)listener).exitSubDefinition(this);
		}
	}

	public final SubDefinitionContext subDefinition() throws RecognitionException {
		SubDefinitionContext _localctx = new SubDefinitionContext(_ctx, getState());
		enterRule(_localctx, 38, RULE_subDefinition);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(146);
			match(SubDefinition);
			setState(148);
			_la = _input.LA(1);
			if (_la==Indent || _la==ExampleText) {
				{
				setState(147);
				exampleGroup();
				}
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

	public static class SubEntryGroupContext extends ParserRuleContext {
		public List<SubEntryContext> subEntry() {
			return getRuleContexts(SubEntryContext.class);
		}
		public SubEntryContext subEntry(int i) {
			return getRuleContext(SubEntryContext.class,i);
		}
		public SubEntryGroupContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_subEntryGroup; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof EntryListener ) ((EntryListener)listener).enterSubEntryGroup(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof EntryListener ) ((EntryListener)listener).exitSubEntryGroup(this);
		}
	}

	public final SubEntryGroupContext subEntryGroup() throws RecognitionException {
		SubEntryGroupContext _localctx = new SubEntryGroupContext(_ctx, getState());
		enterRule(_localctx, 40, RULE_subEntryGroup);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(151); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(150);
				subEntry();
				}
				}
				setState(153); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( _la==SubEntryLabel );
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

	public static class SubEntryContext extends ParserRuleContext {
		public TerminalNode SubEntryLabel() { return getToken(EntryParser.SubEntryLabel, 0); }
		public TerminalNode SubEntryText() { return getToken(EntryParser.SubEntryText, 0); }
		public NoteGroupContext noteGroup() {
			return getRuleContext(NoteGroupContext.class,0);
		}
		public SubEntryListContext subEntryList() {
			return getRuleContext(SubEntryListContext.class,0);
		}
		public SubEntryContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_subEntry; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof EntryListener ) ((EntryListener)listener).enterSubEntry(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof EntryListener ) ((EntryListener)listener).exitSubEntry(this);
		}
	}

	public final SubEntryContext subEntry() throws RecognitionException {
		SubEntryContext _localctx = new SubEntryContext(_ctx, getState());
		enterRule(_localctx, 42, RULE_subEntry);
		int _la;
		try {
			setState(165);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,25,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(155);
				match(SubEntryLabel);
				setState(156);
				match(SubEntryText);
				setState(158);
				_la = _input.LA(1);
				if (_la==Note) {
					{
					setState(157);
					noteGroup();
					}
				}

				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(160);
				match(SubEntryLabel);
				setState(161);
				subEntryList();
				setState(163);
				_la = _input.LA(1);
				if (_la==Note) {
					{
					setState(162);
					noteGroup();
					}
				}

				}
				break;
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

	public static class SubEntryListContext extends ParserRuleContext {
		public List<SubEntryListItemContext> subEntryListItem() {
			return getRuleContexts(SubEntryListItemContext.class);
		}
		public SubEntryListItemContext subEntryListItem(int i) {
			return getRuleContext(SubEntryListItemContext.class,i);
		}
		public SubEntryListContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_subEntryList; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof EntryListener ) ((EntryListener)listener).enterSubEntryList(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof EntryListener ) ((EntryListener)listener).exitSubEntryList(this);
		}
	}

	public final SubEntryListContext subEntryList() throws RecognitionException {
		SubEntryListContext _localctx = new SubEntryListContext(_ctx, getState());
		enterRule(_localctx, 44, RULE_subEntryList);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(168); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(167);
				subEntryListItem();
				}
				}
				setState(170); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( _la==SubEntryListItem );
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

	public static class SubEntryListItemContext extends ParserRuleContext {
		public TerminalNode SubEntryListItem() { return getToken(EntryParser.SubEntryListItem, 0); }
		public SubEntryListItemContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_subEntryListItem; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof EntryListener ) ((EntryListener)listener).enterSubEntryListItem(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof EntryListener ) ((EntryListener)listener).exitSubEntryListItem(this);
		}
	}

	public final SubEntryListItemContext subEntryListItem() throws RecognitionException {
		SubEntryListItemContext _localctx = new SubEntryListItemContext(_ctx, getState());
		enterRule(_localctx, 46, RULE_subEntryListItem);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(172);
			match(SubEntryListItem);
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

	public static class NoteGroupContext extends ParserRuleContext {
		public List<NoteContext> note() {
			return getRuleContexts(NoteContext.class);
		}
		public NoteContext note(int i) {
			return getRuleContext(NoteContext.class,i);
		}
		public NoteGroupContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_noteGroup; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof EntryListener ) ((EntryListener)listener).enterNoteGroup(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof EntryListener ) ((EntryListener)listener).exitNoteGroup(this);
		}
	}

	public final NoteGroupContext noteGroup() throws RecognitionException {
		NoteGroupContext _localctx = new NoteGroupContext(_ctx, getState());
		enterRule(_localctx, 48, RULE_noteGroup);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(175); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(174);
				note();
				}
				}
				setState(177); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( _la==Note );
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

	public static class NoteContext extends ParserRuleContext {
		public TerminalNode Note() { return getToken(EntryParser.Note, 0); }
		public NoteContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_note; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof EntryListener ) ((EntryListener)listener).enterNote(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof EntryListener ) ((EntryListener)listener).exitNote(this);
		}
	}

	public final NoteContext note() throws RecognitionException {
		NoteContext _localctx = new NoteContext(_ctx, getState());
		enterRule(_localctx, 50, RULE_note);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(179);
			match(Note);
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
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\3\25\u00b8\4\2\t\2"+
		"\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13"+
		"\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31\t\31"+
		"\4\32\t\32\4\33\t\33\3\2\5\28\n\2\3\2\5\2;\n\2\3\3\3\3\5\3?\n\3\3\4\3"+
		"\4\3\5\5\5D\n\5\3\5\6\5G\n\5\r\5\16\5H\3\6\3\6\3\7\6\7N\n\7\r\7\16\7O"+
		"\3\b\5\bS\n\b\3\b\6\bV\n\b\r\b\16\bW\3\b\5\b[\n\b\3\t\3\t\5\t_\n\t\3\n"+
		"\3\n\3\13\6\13d\n\13\r\13\16\13e\3\f\3\f\5\fj\n\f\3\r\5\rm\n\r\3\r\6\r"+
		"p\n\r\r\r\16\rq\3\16\3\16\3\17\3\17\5\17x\n\17\3\17\5\17{\n\17\3\20\6"+
		"\20~\n\20\r\20\16\20\177\3\21\3\21\5\21\u0084\n\21\3\22\5\22\u0087\n\22"+
		"\3\22\3\22\3\23\5\23\u008c\n\23\3\23\3\23\3\24\6\24\u0091\n\24\r\24\16"+
		"\24\u0092\3\25\3\25\5\25\u0097\n\25\3\26\6\26\u009a\n\26\r\26\16\26\u009b"+
		"\3\27\3\27\3\27\5\27\u00a1\n\27\3\27\3\27\3\27\5\27\u00a6\n\27\5\27\u00a8"+
		"\n\27\3\30\6\30\u00ab\n\30\r\30\16\30\u00ac\3\31\3\31\3\32\6\32\u00b2"+
		"\n\32\r\32\16\32\u00b3\3\33\3\33\3\33\2\2\34\2\4\6\b\n\f\16\20\22\24\26"+
		"\30\32\34\36 \"$&(*,.\60\62\64\2\2\u00b9\2\67\3\2\2\2\4<\3\2\2\2\6@\3"+
		"\2\2\2\bF\3\2\2\2\nJ\3\2\2\2\fM\3\2\2\2\16R\3\2\2\2\20\\\3\2\2\2\22`\3"+
		"\2\2\2\24c\3\2\2\2\26g\3\2\2\2\30l\3\2\2\2\32s\3\2\2\2\34u\3\2\2\2\36"+
		"}\3\2\2\2 \u0081\3\2\2\2\"\u0086\3\2\2\2$\u008b\3\2\2\2&\u0090\3\2\2\2"+
		"(\u0094\3\2\2\2*\u0099\3\2\2\2,\u00a7\3\2\2\2.\u00aa\3\2\2\2\60\u00ae"+
		"\3\2\2\2\62\u00b1\3\2\2\2\64\u00b5\3\2\2\2\668\5\4\3\2\67\66\3\2\2\2\67"+
		"8\3\2\2\28:\3\2\2\29;\5\f\7\2:9\3\2\2\2:;\3\2\2\2;\3\3\2\2\2<>\5\6\4\2"+
		"=?\5\b\5\2>=\3\2\2\2>?\3\2\2\2?\5\3\2\2\2@A\7\t\2\2A\7\3\2\2\2BD\7\5\2"+
		"\2CB\3\2\2\2CD\3\2\2\2DE\3\2\2\2EG\5\n\6\2FC\3\2\2\2GH\3\2\2\2HF\3\2\2"+
		"\2HI\3\2\2\2I\t\3\2\2\2JK\7\n\2\2K\13\3\2\2\2LN\5\16\b\2ML\3\2\2\2NO\3"+
		"\2\2\2OM\3\2\2\2OP\3\2\2\2P\r\3\2\2\2QS\5\20\t\2RQ\3\2\2\2RS\3\2\2\2S"+
		"U\3\2\2\2TV\5\30\r\2UT\3\2\2\2VW\3\2\2\2WU\3\2\2\2WX\3\2\2\2XZ\3\2\2\2"+
		"Y[\5*\26\2ZY\3\2\2\2Z[\3\2\2\2[\17\3\2\2\2\\^\5\22\n\2]_\5\24\13\2^]\3"+
		"\2\2\2^_\3\2\2\2_\21\3\2\2\2`a\7\13\2\2a\23\3\2\2\2bd\5\26\f\2cb\3\2\2"+
		"\2de\3\2\2\2ec\3\2\2\2ef\3\2\2\2f\25\3\2\2\2gi\7\f\2\2hj\5\b\5\2ih\3\2"+
		"\2\2ij\3\2\2\2j\27\3\2\2\2km\5\32\16\2lk\3\2\2\2lm\3\2\2\2mo\3\2\2\2n"+
		"p\5\34\17\2on\3\2\2\2pq\3\2\2\2qo\3\2\2\2qr\3\2\2\2r\31\3\2\2\2st\7\r"+
		"\2\2t\33\3\2\2\2uw\7\16\2\2vx\5\36\20\2wv\3\2\2\2wx\3\2\2\2xz\3\2\2\2"+
		"y{\5&\24\2zy\3\2\2\2z{\3\2\2\2{\35\3\2\2\2|~\5 \21\2}|\3\2\2\2~\177\3"+
		"\2\2\2\177}\3\2\2\2\177\u0080\3\2\2\2\u0080\37\3\2\2\2\u0081\u0083\5\""+
		"\22\2\u0082\u0084\5$\23\2\u0083\u0082\3\2\2\2\u0083\u0084\3\2\2\2\u0084"+
		"!\3\2\2\2\u0085\u0087\7\5\2\2\u0086\u0085\3\2\2\2\u0086\u0087\3\2\2\2"+
		"\u0087\u0088\3\2\2\2\u0088\u0089\7\24\2\2\u0089#\3\2\2\2\u008a\u008c\7"+
		"\5\2\2\u008b\u008a\3\2\2\2\u008b\u008c\3\2\2\2\u008c\u008d\3\2\2\2\u008d"+
		"\u008e\7\25\2\2\u008e%\3\2\2\2\u008f\u0091\5(\25\2\u0090\u008f\3\2\2\2"+
		"\u0091\u0092\3\2\2\2\u0092\u0090\3\2\2\2\u0092\u0093\3\2\2\2\u0093\'\3"+
		"\2\2\2\u0094\u0096\7\17\2\2\u0095\u0097\5\36\20\2\u0096\u0095\3\2\2\2"+
		"\u0096\u0097\3\2\2\2\u0097)\3\2\2\2\u0098\u009a\5,\27\2\u0099\u0098\3"+
		"\2\2\2\u009a\u009b\3\2\2\2\u009b\u0099\3\2\2\2\u009b\u009c\3\2\2\2\u009c"+
		"+\3\2\2\2\u009d\u009e\7\20\2\2\u009e\u00a0\7\21\2\2\u009f\u00a1\5\62\32"+
		"\2\u00a0\u009f\3\2\2\2\u00a0\u00a1\3\2\2\2\u00a1\u00a8\3\2\2\2\u00a2\u00a3"+
		"\7\20\2\2\u00a3\u00a5\5.\30\2\u00a4\u00a6\5\62\32\2\u00a5\u00a4\3\2\2"+
		"\2\u00a5\u00a6\3\2\2\2\u00a6\u00a8\3\2\2\2\u00a7\u009d\3\2\2\2\u00a7\u00a2"+
		"\3\2\2\2\u00a8-\3\2\2\2\u00a9\u00ab\5\60\31\2\u00aa\u00a9\3\2\2\2\u00ab"+
		"\u00ac\3\2\2\2\u00ac\u00aa\3\2\2\2\u00ac\u00ad\3\2\2\2\u00ad/\3\2\2\2"+
		"\u00ae\u00af\7\22\2\2\u00af\61\3\2\2\2\u00b0\u00b2\5\64\33\2\u00b1\u00b0"+
		"\3\2\2\2\u00b2\u00b3\3\2\2\2\u00b3\u00b1\3\2\2\2\u00b3\u00b4\3\2\2\2\u00b4"+
		"\63\3\2\2\2\u00b5\u00b6\7\23\2\2\u00b6\65\3\2\2\2\36\67:>CHORWZ^eilqw"+
		"z\177\u0083\u0086\u008b\u0092\u0096\u009b\u00a0\u00a5\u00a7\u00ac\u00b3";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}