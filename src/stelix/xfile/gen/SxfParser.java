// Generated from /home/slowcheetah/work_dir/sxf/src/Sxf.g4 by ANTLR 4.9.2
package stelix.xfile.gen;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class SxfParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.9.2", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, INTEGER=7, SET=8, SET_LONG=9, 
		BLOCK_START=10, BLOCK_END=11, SEPARATOR=12, SP_START=13, SP_END=14, IDENTIFIER=15, 
		StringLiteral=16, WS=17, UnterminatedStringLiteral=18, BlockComment=19;
	public static final int
		RULE_file = 0, RULE_block = 1, RULE_variable = 2, RULE_named_basicvar = 3, 
		RULE_unnamed_object = 4, RULE_block_elements = 5, RULE_variable_types = 6, 
		RULE_number = 7, RULE_struct = 8, RULE_var_integer = 9, RULE_var_double = 10, 
		RULE_var_false = 11, RULE_var_true = 12, RULE_var_null = 13, RULE_bool = 14, 
		RULE_string_literal = 15;
	private static String[] makeRuleNames() {
		return new String[] {
			"file", "block", "variable", "named_basicvar", "unnamed_object", "block_elements", 
			"variable_types", "number", "struct", "var_integer", "var_double", "var_false", 
			"var_true", "var_null", "bool", "string_literal"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'.'", "'false'", "'FALSE'", "'true'", "'TRUE'", "'null'", null, 
			"':'", "'=>'", "'{'", "'}'", "','", "'['", "']'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, null, null, null, null, null, null, "INTEGER", "SET", "SET_LONG", 
			"BLOCK_START", "BLOCK_END", "SEPARATOR", "SP_START", "SP_END", "IDENTIFIER", 
			"StringLiteral", "WS", "UnterminatedStringLiteral", "BlockComment"
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
	public String getGrammarFileName() { return "Sxf.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public SxfParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	public static class FileContext extends ParserRuleContext {
		public BlockContext block() {
			return getRuleContext(BlockContext.class,0);
		}
		public FileContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_file; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SxfListener ) ((SxfListener)listener).enterFile(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SxfListener ) ((SxfListener)listener).exitFile(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SxfVisitor ) return ((SxfVisitor<? extends T>)visitor).visitFile(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FileContext file() throws RecognitionException {
		FileContext _localctx = new FileContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_file);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(32);
			block();
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

	public static class BlockContext extends ParserRuleContext {
		public TerminalNode BLOCK_START() { return getToken(SxfParser.BLOCK_START, 0); }
		public TerminalNode BLOCK_END() { return getToken(SxfParser.BLOCK_END, 0); }
		public Block_elementsContext block_elements() {
			return getRuleContext(Block_elementsContext.class,0);
		}
		public BlockContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_block; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SxfListener ) ((SxfListener)listener).enterBlock(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SxfListener ) ((SxfListener)listener).exitBlock(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SxfVisitor ) return ((SxfVisitor<? extends T>)visitor).visitBlock(this);
			else return visitor.visitChildren(this);
		}
	}

	public final BlockContext block() throws RecognitionException {
		BlockContext _localctx = new BlockContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_block);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(34);
			match(BLOCK_START);
			setState(36);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << BLOCK_START) | (1L << SP_START) | (1L << IDENTIFIER) | (1L << StringLiteral))) != 0)) {
				{
				setState(35);
				block_elements();
				}
			}

			setState(38);
			match(BLOCK_END);
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

	public static class VariableContext extends ParserRuleContext {
		public Named_basicvarContext named_basicvar() {
			return getRuleContext(Named_basicvarContext.class,0);
		}
		public Unnamed_objectContext unnamed_object() {
			return getRuleContext(Unnamed_objectContext.class,0);
		}
		public VariableContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_variable; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SxfListener ) ((SxfListener)listener).enterVariable(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SxfListener ) ((SxfListener)listener).exitVariable(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SxfVisitor ) return ((SxfVisitor<? extends T>)visitor).visitVariable(this);
			else return visitor.visitChildren(this);
		}
	}

	public final VariableContext variable() throws RecognitionException {
		VariableContext _localctx = new VariableContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_variable);
		try {
			setState(42);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case IDENTIFIER:
			case StringLiteral:
				enterOuterAlt(_localctx, 1);
				{
				setState(40);
				named_basicvar();
				}
				break;
			case BLOCK_START:
			case SP_START:
				enterOuterAlt(_localctx, 2);
				{
				setState(41);
				unnamed_object();
				}
				break;
			default:
				throw new NoViableAltException(this);
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

	public static class Named_basicvarContext extends ParserRuleContext {
		public TerminalNode IDENTIFIER() { return getToken(SxfParser.IDENTIFIER, 0); }
		public String_literalContext string_literal() {
			return getRuleContext(String_literalContext.class,0);
		}
		public TerminalNode SET() { return getToken(SxfParser.SET, 0); }
		public Variable_typesContext variable_types() {
			return getRuleContext(Variable_typesContext.class,0);
		}
		public TerminalNode SET_LONG() { return getToken(SxfParser.SET_LONG, 0); }
		public BlockContext block() {
			return getRuleContext(BlockContext.class,0);
		}
		public Named_basicvarContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_named_basicvar; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SxfListener ) ((SxfListener)listener).enterNamed_basicvar(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SxfListener ) ((SxfListener)listener).exitNamed_basicvar(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SxfVisitor ) return ((SxfVisitor<? extends T>)visitor).visitNamed_basicvar(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Named_basicvarContext named_basicvar() throws RecognitionException {
		Named_basicvarContext _localctx = new Named_basicvarContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_named_basicvar);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(46);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case IDENTIFIER:
				{
				setState(44);
				match(IDENTIFIER);
				}
				break;
			case StringLiteral:
				{
				setState(45);
				string_literal();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			setState(52);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case SET:
				{
				{
				setState(48);
				match(SET);
				setState(49);
				variable_types();
				}
				}
				break;
			case SET_LONG:
				{
				{
				setState(50);
				match(SET_LONG);
				setState(51);
				block();
				}
				}
				break;
			default:
				throw new NoViableAltException(this);
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

	public static class Unnamed_objectContext extends ParserRuleContext {
		public BlockContext block() {
			return getRuleContext(BlockContext.class,0);
		}
		public StructContext struct() {
			return getRuleContext(StructContext.class,0);
		}
		public Unnamed_objectContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_unnamed_object; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SxfListener ) ((SxfListener)listener).enterUnnamed_object(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SxfListener ) ((SxfListener)listener).exitUnnamed_object(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SxfVisitor ) return ((SxfVisitor<? extends T>)visitor).visitUnnamed_object(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Unnamed_objectContext unnamed_object() throws RecognitionException {
		Unnamed_objectContext _localctx = new Unnamed_objectContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_unnamed_object);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(56);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case BLOCK_START:
				{
				setState(54);
				block();
				}
				break;
			case SP_START:
				{
				setState(55);
				struct();
				}
				break;
			default:
				throw new NoViableAltException(this);
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

	public static class Block_elementsContext extends ParserRuleContext {
		public List<VariableContext> variable() {
			return getRuleContexts(VariableContext.class);
		}
		public VariableContext variable(int i) {
			return getRuleContext(VariableContext.class,i);
		}
		public List<TerminalNode> SEPARATOR() { return getTokens(SxfParser.SEPARATOR); }
		public TerminalNode SEPARATOR(int i) {
			return getToken(SxfParser.SEPARATOR, i);
		}
		public Block_elementsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_block_elements; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SxfListener ) ((SxfListener)listener).enterBlock_elements(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SxfListener ) ((SxfListener)listener).exitBlock_elements(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SxfVisitor ) return ((SxfVisitor<? extends T>)visitor).visitBlock_elements(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Block_elementsContext block_elements() throws RecognitionException {
		Block_elementsContext _localctx = new Block_elementsContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_block_elements);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(58);
			variable();
			setState(63);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==SEPARATOR) {
				{
				{
				setState(59);
				match(SEPARATOR);
				setState(60);
				variable();
				}
				}
				setState(65);
				_errHandler.sync(this);
				_la = _input.LA(1);
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

	public static class Variable_typesContext extends ParserRuleContext {
		public String_literalContext string_literal() {
			return getRuleContext(String_literalContext.class,0);
		}
		public NumberContext number() {
			return getRuleContext(NumberContext.class,0);
		}
		public BoolContext bool() {
			return getRuleContext(BoolContext.class,0);
		}
		public StructContext struct() {
			return getRuleContext(StructContext.class,0);
		}
		public BlockContext block() {
			return getRuleContext(BlockContext.class,0);
		}
		public Var_nullContext var_null() {
			return getRuleContext(Var_nullContext.class,0);
		}
		public Variable_typesContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_variable_types; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SxfListener ) ((SxfListener)listener).enterVariable_types(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SxfListener ) ((SxfListener)listener).exitVariable_types(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SxfVisitor ) return ((SxfVisitor<? extends T>)visitor).visitVariable_types(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Variable_typesContext variable_types() throws RecognitionException {
		Variable_typesContext _localctx = new Variable_typesContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_variable_types);
		try {
			setState(72);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case StringLiteral:
				enterOuterAlt(_localctx, 1);
				{
				setState(66);
				string_literal();
				}
				break;
			case INTEGER:
				enterOuterAlt(_localctx, 2);
				{
				setState(67);
				number();
				}
				break;
			case T__1:
			case T__2:
			case T__3:
			case T__4:
				enterOuterAlt(_localctx, 3);
				{
				setState(68);
				bool();
				}
				break;
			case SP_START:
				enterOuterAlt(_localctx, 4);
				{
				setState(69);
				struct();
				}
				break;
			case BLOCK_START:
				enterOuterAlt(_localctx, 5);
				{
				setState(70);
				block();
				}
				break;
			case T__5:
				enterOuterAlt(_localctx, 6);
				{
				setState(71);
				var_null();
				}
				break;
			default:
				throw new NoViableAltException(this);
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

	public static class NumberContext extends ParserRuleContext {
		public Var_integerContext var_integer() {
			return getRuleContext(Var_integerContext.class,0);
		}
		public Var_doubleContext var_double() {
			return getRuleContext(Var_doubleContext.class,0);
		}
		public NumberContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_number; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SxfListener ) ((SxfListener)listener).enterNumber(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SxfListener ) ((SxfListener)listener).exitNumber(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SxfVisitor ) return ((SxfVisitor<? extends T>)visitor).visitNumber(this);
			else return visitor.visitChildren(this);
		}
	}

	public final NumberContext number() throws RecognitionException {
		NumberContext _localctx = new NumberContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_number);
		try {
			setState(76);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,7,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(74);
				var_integer();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(75);
				var_double();
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

	public static class StructContext extends ParserRuleContext {
		public TerminalNode SP_START() { return getToken(SxfParser.SP_START, 0); }
		public TerminalNode SP_END() { return getToken(SxfParser.SP_END, 0); }
		public List<Variable_typesContext> variable_types() {
			return getRuleContexts(Variable_typesContext.class);
		}
		public Variable_typesContext variable_types(int i) {
			return getRuleContext(Variable_typesContext.class,i);
		}
		public List<TerminalNode> SEPARATOR() { return getTokens(SxfParser.SEPARATOR); }
		public TerminalNode SEPARATOR(int i) {
			return getToken(SxfParser.SEPARATOR, i);
		}
		public StructContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_struct; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SxfListener ) ((SxfListener)listener).enterStruct(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SxfListener ) ((SxfListener)listener).exitStruct(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SxfVisitor ) return ((SxfVisitor<? extends T>)visitor).visitStruct(this);
			else return visitor.visitChildren(this);
		}
	}

	public final StructContext struct() throws RecognitionException {
		StructContext _localctx = new StructContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_struct);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(78);
			match(SP_START);
			setState(87);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__1) | (1L << T__2) | (1L << T__3) | (1L << T__4) | (1L << T__5) | (1L << INTEGER) | (1L << BLOCK_START) | (1L << SP_START) | (1L << StringLiteral))) != 0)) {
				{
				setState(79);
				variable_types();
				setState(84);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==SEPARATOR) {
					{
					{
					setState(80);
					match(SEPARATOR);
					setState(81);
					variable_types();
					}
					}
					setState(86);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				}
			}

			setState(89);
			match(SP_END);
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

	public static class Var_integerContext extends ParserRuleContext {
		public TerminalNode INTEGER() { return getToken(SxfParser.INTEGER, 0); }
		public Var_integerContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_var_integer; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SxfListener ) ((SxfListener)listener).enterVar_integer(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SxfListener ) ((SxfListener)listener).exitVar_integer(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SxfVisitor ) return ((SxfVisitor<? extends T>)visitor).visitVar_integer(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Var_integerContext var_integer() throws RecognitionException {
		Var_integerContext _localctx = new Var_integerContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_var_integer);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(91);
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

	public static class Var_doubleContext extends ParserRuleContext {
		public List<TerminalNode> INTEGER() { return getTokens(SxfParser.INTEGER); }
		public TerminalNode INTEGER(int i) {
			return getToken(SxfParser.INTEGER, i);
		}
		public Var_doubleContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_var_double; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SxfListener ) ((SxfListener)listener).enterVar_double(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SxfListener ) ((SxfListener)listener).exitVar_double(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SxfVisitor ) return ((SxfVisitor<? extends T>)visitor).visitVar_double(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Var_doubleContext var_double() throws RecognitionException {
		Var_doubleContext _localctx = new Var_doubleContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_var_double);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(93);
			match(INTEGER);
			setState(94);
			match(T__0);
			setState(95);
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

	public static class Var_falseContext extends ParserRuleContext {
		public Var_falseContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_var_false; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SxfListener ) ((SxfListener)listener).enterVar_false(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SxfListener ) ((SxfListener)listener).exitVar_false(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SxfVisitor ) return ((SxfVisitor<? extends T>)visitor).visitVar_false(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Var_falseContext var_false() throws RecognitionException {
		Var_falseContext _localctx = new Var_falseContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_var_false);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(97);
			_la = _input.LA(1);
			if ( !(_la==T__1 || _la==T__2) ) {
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

	public static class Var_trueContext extends ParserRuleContext {
		public Var_trueContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_var_true; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SxfListener ) ((SxfListener)listener).enterVar_true(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SxfListener ) ((SxfListener)listener).exitVar_true(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SxfVisitor ) return ((SxfVisitor<? extends T>)visitor).visitVar_true(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Var_trueContext var_true() throws RecognitionException {
		Var_trueContext _localctx = new Var_trueContext(_ctx, getState());
		enterRule(_localctx, 24, RULE_var_true);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(99);
			_la = _input.LA(1);
			if ( !(_la==T__3 || _la==T__4) ) {
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

	public static class Var_nullContext extends ParserRuleContext {
		public Var_nullContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_var_null; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SxfListener ) ((SxfListener)listener).enterVar_null(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SxfListener ) ((SxfListener)listener).exitVar_null(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SxfVisitor ) return ((SxfVisitor<? extends T>)visitor).visitVar_null(this);
			else return visitor.visitChildren(this);
		}
	}

	public final Var_nullContext var_null() throws RecognitionException {
		Var_nullContext _localctx = new Var_nullContext(_ctx, getState());
		enterRule(_localctx, 26, RULE_var_null);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(101);
			match(T__5);
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

	public static class BoolContext extends ParserRuleContext {
		public Var_trueContext var_true() {
			return getRuleContext(Var_trueContext.class,0);
		}
		public Var_falseContext var_false() {
			return getRuleContext(Var_falseContext.class,0);
		}
		public BoolContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_bool; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SxfListener ) ((SxfListener)listener).enterBool(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SxfListener ) ((SxfListener)listener).exitBool(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SxfVisitor ) return ((SxfVisitor<? extends T>)visitor).visitBool(this);
			else return visitor.visitChildren(this);
		}
	}

	public final BoolContext bool() throws RecognitionException {
		BoolContext _localctx = new BoolContext(_ctx, getState());
		enterRule(_localctx, 28, RULE_bool);
		try {
			setState(105);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__3:
			case T__4:
				enterOuterAlt(_localctx, 1);
				{
				setState(103);
				var_true();
				}
				break;
			case T__1:
			case T__2:
				enterOuterAlt(_localctx, 2);
				{
				setState(104);
				var_false();
				}
				break;
			default:
				throw new NoViableAltException(this);
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

	public static class String_literalContext extends ParserRuleContext {
		public TerminalNode StringLiteral() { return getToken(SxfParser.StringLiteral, 0); }
		public String_literalContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_string_literal; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof SxfListener ) ((SxfListener)listener).enterString_literal(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof SxfListener ) ((SxfListener)listener).exitString_literal(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof SxfVisitor ) return ((SxfVisitor<? extends T>)visitor).visitString_literal(this);
			else return visitor.visitChildren(this);
		}
	}

	public final String_literalContext string_literal() throws RecognitionException {
		String_literalContext _localctx = new String_literalContext(_ctx, getState());
		enterRule(_localctx, 30, RULE_string_literal);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(107);
			match(StringLiteral);
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
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\3\25p\4\2\t\2\4\3\t"+
		"\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t\13\4"+
		"\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\3\2\3\2\3\3\3\3"+
		"\5\3\'\n\3\3\3\3\3\3\4\3\4\5\4-\n\4\3\5\3\5\5\5\61\n\5\3\5\3\5\3\5\3\5"+
		"\5\5\67\n\5\3\6\3\6\5\6;\n\6\3\7\3\7\3\7\7\7@\n\7\f\7\16\7C\13\7\3\b\3"+
		"\b\3\b\3\b\3\b\3\b\5\bK\n\b\3\t\3\t\5\tO\n\t\3\n\3\n\3\n\3\n\7\nU\n\n"+
		"\f\n\16\nX\13\n\5\nZ\n\n\3\n\3\n\3\13\3\13\3\f\3\f\3\f\3\f\3\r\3\r\3\16"+
		"\3\16\3\17\3\17\3\20\3\20\5\20l\n\20\3\21\3\21\3\21\2\2\22\2\4\6\b\n\f"+
		"\16\20\22\24\26\30\32\34\36 \2\4\3\2\4\5\3\2\6\7\2n\2\"\3\2\2\2\4$\3\2"+
		"\2\2\6,\3\2\2\2\b\60\3\2\2\2\n:\3\2\2\2\f<\3\2\2\2\16J\3\2\2\2\20N\3\2"+
		"\2\2\22P\3\2\2\2\24]\3\2\2\2\26_\3\2\2\2\30c\3\2\2\2\32e\3\2\2\2\34g\3"+
		"\2\2\2\36k\3\2\2\2 m\3\2\2\2\"#\5\4\3\2#\3\3\2\2\2$&\7\f\2\2%\'\5\f\7"+
		"\2&%\3\2\2\2&\'\3\2\2\2\'(\3\2\2\2()\7\r\2\2)\5\3\2\2\2*-\5\b\5\2+-\5"+
		"\n\6\2,*\3\2\2\2,+\3\2\2\2-\7\3\2\2\2.\61\7\21\2\2/\61\5 \21\2\60.\3\2"+
		"\2\2\60/\3\2\2\2\61\66\3\2\2\2\62\63\7\n\2\2\63\67\5\16\b\2\64\65\7\13"+
		"\2\2\65\67\5\4\3\2\66\62\3\2\2\2\66\64\3\2\2\2\67\t\3\2\2\28;\5\4\3\2"+
		"9;\5\22\n\2:8\3\2\2\2:9\3\2\2\2;\13\3\2\2\2<A\5\6\4\2=>\7\16\2\2>@\5\6"+
		"\4\2?=\3\2\2\2@C\3\2\2\2A?\3\2\2\2AB\3\2\2\2B\r\3\2\2\2CA\3\2\2\2DK\5"+
		" \21\2EK\5\20\t\2FK\5\36\20\2GK\5\22\n\2HK\5\4\3\2IK\5\34\17\2JD\3\2\2"+
		"\2JE\3\2\2\2JF\3\2\2\2JG\3\2\2\2JH\3\2\2\2JI\3\2\2\2K\17\3\2\2\2LO\5\24"+
		"\13\2MO\5\26\f\2NL\3\2\2\2NM\3\2\2\2O\21\3\2\2\2PY\7\17\2\2QV\5\16\b\2"+
		"RS\7\16\2\2SU\5\16\b\2TR\3\2\2\2UX\3\2\2\2VT\3\2\2\2VW\3\2\2\2WZ\3\2\2"+
		"\2XV\3\2\2\2YQ\3\2\2\2YZ\3\2\2\2Z[\3\2\2\2[\\\7\20\2\2\\\23\3\2\2\2]^"+
		"\7\t\2\2^\25\3\2\2\2_`\7\t\2\2`a\7\3\2\2ab\7\t\2\2b\27\3\2\2\2cd\t\2\2"+
		"\2d\31\3\2\2\2ef\t\3\2\2f\33\3\2\2\2gh\7\b\2\2h\35\3\2\2\2il\5\32\16\2"+
		"jl\5\30\r\2ki\3\2\2\2kj\3\2\2\2l\37\3\2\2\2mn\7\22\2\2n!\3\2\2\2\r&,\60"+
		"\66:AJNVYk";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}