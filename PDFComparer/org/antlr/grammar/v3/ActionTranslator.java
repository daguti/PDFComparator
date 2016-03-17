/*      */ package org.antlr.grammar.v3;
/*      */ 
/*      */ import java.io.PrintStream;
/*      */ import java.util.ArrayList;
/*      */ import java.util.List;
/*      */ import org.antlr.codegen.CodeGenerator;
/*      */ import org.antlr.runtime.ANTLRStringStream;
/*      */ import org.antlr.runtime.BaseRecognizer;
/*      */ import org.antlr.runtime.CharStream;
/*      */ import org.antlr.runtime.DFA;
/*      */ import org.antlr.runtime.EarlyExitException;
/*      */ import org.antlr.runtime.FailedPredicateException;
/*      */ import org.antlr.runtime.IntStream;
/*      */ import org.antlr.runtime.Lexer;
/*      */ import org.antlr.runtime.MismatchedSetException;
/*      */ import org.antlr.runtime.NoViableAltException;
/*      */ import org.antlr.runtime.RecognitionException;
/*      */ import org.antlr.runtime.RecognizerSharedState;
/*      */ import org.antlr.stringtemplate.StringTemplate;
/*      */ import org.antlr.stringtemplate.StringTemplateGroup;
/*      */ import org.antlr.tool.Attribute;
/*      */ import org.antlr.tool.AttributeScope;
/*      */ import org.antlr.tool.ErrorManager;
/*      */ import org.antlr.tool.Grammar;
/*      */ import org.antlr.tool.Grammar.LabelElementPair;
/*      */ import org.antlr.tool.GrammarAST;
/*      */ import org.antlr.tool.Rule;
/*      */ 
/*      */ public class ActionTranslator extends Lexer
/*      */ {
/*      */   public static final int INDIRECT_TEMPLATE_INSTANCE = 28;
/*      */   public static final int RULE_SCOPE_ATTR = 12;
/*      */   public static final int ESC = 32;
/*      */   public static final int SET_RULE_SCOPE_ATTR = 11;
/*      */   public static final int DYNAMIC_NEGATIVE_INDEXED_SCOPE_ATTR = 22;
/*      */   public static final int SET_ATTRIBUTE = 30;
/*      */   public static final int ERROR_SCOPED_XY = 20;
/*      */   public static final int INT = 37;
/*      */   public static final int TEMPLATE_EXPR = 31;
/*      */   public static final int ERROR_XY = 33;
/*      */   public static final int LOCAL_ATTR = 17;
/*      */   public static final int TEXT = 36;
/*      */   public static final int ISOLATED_TOKEN_REF = 14;
/*      */   public static final int ID = 4;
/*      */   public static final int TOKEN_SCOPE_ATTR = 10;
/*      */   public static final int EOF = -1;
/*      */   public static final int SET_TOKEN_SCOPE_ATTR = 9;
/*      */   public static final int ACTION = 27;
/*      */   public static final int UNKNOWN_SYNTAX = 35;
/*      */   public static final int WS = 5;
/*      */   public static final int ARG = 25;
/*      */   public static final int TEMPLATE_INSTANCE = 26;
/*      */   public static final int ISOLATED_LEXER_RULE_REF = 15;
/*      */   public static final int SET_EXPR_ATTRIBUTE = 29;
/*      */   public static final int ATTR_VALUE_EXPR = 6;
/*      */   public static final int SET_ENCLOSING_RULE_SCOPE_ATTR = 7;
/*      */   public static final int SET_LOCAL_ATTR = 16;
/*      */   public static final int ENCLOSING_RULE_SCOPE_ATTR = 8;
/*      */   public static final int SET_DYNAMIC_SCOPE_ATTR = 18;
/*      */   public static final int SCOPE_INDEX_EXPR = 21;
/*      */   public static final int ISOLATED_DYNAMIC_SCOPE = 24;
/*      */   public static final int LABEL_REF = 13;
/*      */   public static final int DYNAMIC_SCOPE_ATTR = 19;
/*      */   public static final int ERROR_X = 34;
/*      */   public static final int DYNAMIC_ABSOLUTE_INDEXED_SCOPE_ATTR = 23;
/*   59 */   public List chunks = new ArrayList();
/*      */   Rule enclosingRule;
/*      */   int outerAltNum;
/*      */   Grammar grammar;
/*      */   CodeGenerator generator;
/*      */   antlr.Token actionToken;
/* 3512 */   protected DFA22 dfa22 = new DFA22(this);
/* 3513 */   protected DFA28 dfa28 = new DFA28(this);
/*      */   static final String DFA22_eotS = "\001\001\tèøø";
/*      */   static final String DFA22_eofS = "\nèøø";
/*      */   static final String DFA22_minS = "\001\"\tèøø";
/*      */   static final String DFA22_maxS = "\001}\tèøø";
/*      */   static final String DFA22_acceptS = "\001èøø\001\t\001\001\001\002\001\003\001\004\001\005\001\006\001\007\001\b";
/*      */   static final String DFA22_specialS = "\nèøø}>";
/* 3526 */   static final String[] DFA22_transitionS = { "\001\t\005èøø\001\004\001\005\002èøø\001\006\001èøø\001\003\022èøø\032\002\004èøø\001\002\001èøø\032\002\001\007\001èøø\001\b", "", "", "", "", "", "", "", "", "" };
/*      */ 
/* 3540 */   static final short[] DFA22_eot = DFA.unpackEncodedString("\001\001\tèøø");
/* 3541 */   static final short[] DFA22_eof = DFA.unpackEncodedString("\nèøø");
/* 3542 */   static final char[] DFA22_min = DFA.unpackEncodedStringToUnsignedChars("\001\"\tèøø");
/* 3543 */   static final char[] DFA22_max = DFA.unpackEncodedStringToUnsignedChars("\001}\tèøø");
/* 3544 */   static final short[] DFA22_accept = DFA.unpackEncodedString("\001èøø\001\t\001\001\001\002\001\003\001\004\001\005\001\006\001\007\001\b");
/* 3545 */   static final short[] DFA22_special = DFA.unpackEncodedString("\nèøø}>");
/*      */   static final short[][] DFA22_transition;
/*      */   static final String DFA28_eotS = "\036èøø";
/*      */   static final String DFA28_eofS = "\036èøø";
/*      */   static final String DFA28_minS = "";
/*      */   static final String DFA28_maxS = "";
/*      */   static final String DFA28_acceptS = "\002èøø\001\022\001\023\001\024\001\025\001\026\001\032\001èøø\001\001\001\002\001\003\001\004\001\005\001\006\001\007\001\b\001\t\001\n\001\013\001\f\001\r\001\016\001\017\001\020\001\021\001\030\001\031\001\027\001\033";
/*      */   static final String DFA28_specialS = "";
/*      */   static final String[] DFA28_transitionS;
/*      */   static final short[] DFA28_eot;
/*      */   static final short[] DFA28_eof;
/*      */   static final char[] DFA28_min;
/*      */   static final char[] DFA28_max;
/*      */   static final short[] DFA28_accept;
/*      */   static final short[] DFA28_special;
/*      */   static final short[][] DFA28_transition;
/*      */ 
/*      */   public ActionTranslator(CodeGenerator generator, String ruleName, GrammarAST actionAST)
/*      */   {
/*   70 */     this(new ANTLRStringStream(actionAST.token.getText()));
/*   71 */     this.generator = generator;
/*   72 */     this.grammar = generator.grammar;
/*   73 */     this.enclosingRule = this.grammar.getLocallyDefinedRule(ruleName);
/*   74 */     this.actionToken = actionAST.token;
/*   75 */     this.outerAltNum = actionAST.outerAltNum;
/*      */   }
/*      */ 
/*      */   public ActionTranslator(CodeGenerator generator, String ruleName, antlr.Token actionToken, int outerAltNum)
/*      */   {
/*   83 */     this(new ANTLRStringStream(actionToken.getText()));
/*   84 */     this.generator = generator;
/*   85 */     this.grammar = generator.grammar;
/*   86 */     this.enclosingRule = this.grammar.getRule(ruleName);
/*   87 */     this.actionToken = actionToken;
/*   88 */     this.outerAltNum = outerAltNum;
/*      */   }
/*      */ 
/*      */   public List translateToChunks()
/*      */   {
/*      */     org.antlr.runtime.Token t;
/*      */     do
/*   98 */       t = nextToken();
/*   99 */     while (t.getType() != -1);
/*  100 */     return this.chunks;
/*      */   }
/*      */ 
/*      */   public String translate() {
/*  104 */     List theChunks = translateToChunks();
/*      */ 
/*  106 */     StringBuffer buf = new StringBuffer();
/*  107 */     for (int i = 0; i < theChunks.size(); i++) {
/*  108 */       Object o = theChunks.get(i);
/*  109 */       buf.append(o);
/*      */     }
/*      */ 
/*  112 */     return buf.toString();
/*      */   }
/*      */ 
/*      */   public List translateAction(String action) {
/*  116 */     String rname = null;
/*  117 */     if (this.enclosingRule != null) {
/*  118 */       rname = this.enclosingRule.name;
/*      */     }
/*  120 */     ActionTranslator translator = new ActionTranslator(this.generator, rname, new antlr.CommonToken(40, action), this.outerAltNum);
/*      */ 
/*  124 */     return translator.translateToChunks();
/*      */   }
/*      */ 
/*      */   public boolean isTokenRefInAlt(String id) {
/*  128 */     return this.enclosingRule.getTokenRefsInAlt(id, this.outerAltNum) != null;
/*      */   }
/*      */   public boolean isRuleRefInAlt(String id) {
/*  131 */     return this.enclosingRule.getRuleRefsInAlt(id, this.outerAltNum) != null;
/*      */   }
/*      */   public Grammar.LabelElementPair getElementLabel(String id) {
/*  134 */     return this.enclosingRule.getLabel(id);
/*      */   }
/*      */ 
/*      */   public void checkElementRefUniqueness(String ref, boolean isToken) {
/*  138 */     List refs = null;
/*  139 */     if (isToken) {
/*  140 */       refs = this.enclosingRule.getTokenRefsInAlt(ref, this.outerAltNum);
/*      */     }
/*      */     else {
/*  143 */       refs = this.enclosingRule.getRuleRefsInAlt(ref, this.outerAltNum);
/*      */     }
/*  145 */     if ((refs != null) && (refs.size() > 1))
/*  146 */       ErrorManager.grammarError(127, this.grammar, this.actionToken, ref);
/*      */   }
/*      */ 
/*      */   public Attribute getRuleLabelAttribute(String ruleName, String attrName)
/*      */   {
/*  157 */     Rule r = this.grammar.getRule(ruleName);
/*  158 */     AttributeScope scope = r.getLocalAttributeScope(attrName);
/*  159 */     if ((scope != null) && (!scope.isParameterScope)) {
/*  160 */       return scope.getAttribute(attrName);
/*      */     }
/*  162 */     return null;
/*      */   }
/*      */ 
/*      */   AttributeScope resolveDynamicScope(String scopeName) {
/*  166 */     if (this.grammar.getGlobalScope(scopeName) != null) {
/*  167 */       return this.grammar.getGlobalScope(scopeName);
/*      */     }
/*  169 */     Rule scopeRule = this.grammar.getRule(scopeName);
/*  170 */     if (scopeRule != null) {
/*  171 */       return scopeRule.ruleScope;
/*      */     }
/*  173 */     return null;
/*      */   }
/*      */ 
/*      */   protected StringTemplate template(String name) {
/*  177 */     StringTemplate st = this.generator.getTemplates().getInstanceOf(name);
/*  178 */     this.chunks.add(st);
/*  179 */     return st;
/*      */   }
/*      */ 
/*      */   public ActionTranslator()
/*      */   {
/*      */   }
/*      */ 
/*      */   public ActionTranslator(CharStream input)
/*      */   {
/*  190 */     this(input, new RecognizerSharedState());
/*      */   }
/*      */   public ActionTranslator(CharStream input, RecognizerSharedState state) {
/*  193 */     super(input, state);
/*      */   }
/*      */   public String getGrammarFileName() {
/*  196 */     return "org/antlr/grammar/v3/ActionTranslator.g";
/*      */   }
/*      */   public org.antlr.runtime.Token nextToken() {
/*      */     while (true) {
/*  200 */       if (this.input.LA(1) == -1) {
/*  201 */         return org.antlr.runtime.Token.EOF_TOKEN;
/*      */       }
/*  203 */       this.state.token = null;
/*  204 */       this.state.channel = 0;
/*  205 */       this.state.tokenStartCharIndex = this.input.index();
/*  206 */       this.state.tokenStartCharPositionInLine = this.input.getCharPositionInLine();
/*  207 */       this.state.tokenStartLine = this.input.getLine();
/*  208 */       this.state.text = null;
/*      */       try {
/*  210 */         int m = this.input.mark();
/*  211 */         this.state.backtracking = 1;
/*  212 */         this.state.failed = false;
/*  213 */         mTokens();
/*  214 */         this.state.backtracking = 0;
/*      */ 
/*  216 */         if (this.state.failed) {
/*  217 */           this.input.rewind(m);
/*  218 */           this.input.consume();
/*      */         }
/*      */         else {
/*  221 */           emit();
/*  222 */           return this.state.token;
/*      */         }
/*      */       }
/*      */       catch (RecognitionException re)
/*      */       {
/*  227 */         reportError(re);
/*  228 */         recover(re);
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   public void memoize(IntStream input, int ruleIndex, int ruleStartIndex)
/*      */   {
/*  237 */     if (this.state.backtracking > 1) super.memoize(input, ruleIndex, ruleStartIndex); 
/*      */   }
/*      */ 
/*      */   public boolean alreadyParsedRule(IntStream input, int ruleIndex)
/*      */   {
/*  241 */     if (this.state.backtracking > 1) return super.alreadyParsedRule(input, ruleIndex);
/*  242 */     return false;
/*      */   }
/*      */   public final void mSET_ENCLOSING_RULE_SCOPE_ATTR() throws RecognitionException {
/*      */     try {
/*  246 */       int _type = 7;
/*  247 */       int _channel = 0;
/*  248 */       org.antlr.runtime.CommonToken x = null;
/*  249 */       org.antlr.runtime.CommonToken y = null;
/*  250 */       org.antlr.runtime.CommonToken expr = null;
/*      */ 
/*  255 */       match(36); if (this.state.failed) return;
/*  256 */       int xStart49 = getCharIndex();
/*  257 */       mID(); if (this.state.failed) return;
/*  258 */       x = new org.antlr.runtime.CommonToken(this.input, 0, 0, xStart49, getCharIndex() - 1);
/*  259 */       match(46); if (this.state.failed) return;
/*  260 */       int yStart55 = getCharIndex();
/*  261 */       mID(); if (this.state.failed) return;
/*  262 */       y = new org.antlr.runtime.CommonToken(this.input, 0, 0, yStart55, getCharIndex() - 1);
/*      */ 
/*  264 */       int alt1 = 2;
/*  265 */       switch (this.input.LA(1))
/*      */       {
/*      */       case 9:
/*      */       case 10:
/*      */       case 13:
/*      */       case 32:
/*  271 */         alt1 = 1;
/*      */       }
/*      */ 
/*  276 */       switch (alt1)
/*      */       {
/*      */       case 1:
/*  280 */         mWS(); if (this.state.failed)
/*      */         {
/*      */           return;
/*      */         }
/*      */         break;
/*      */       }
/*      */ 
/*  287 */       match(61); if (this.state.failed) return;
/*  288 */       int exprStart64 = getCharIndex();
/*  289 */       mATTR_VALUE_EXPR(); if (this.state.failed) return;
/*  290 */       expr = new org.antlr.runtime.CommonToken(this.input, 0, 0, exprStart64, getCharIndex() - 1);
/*  291 */       match(59); if (this.state.failed) return;
/*  292 */       if (this.enclosingRule != null) if ((x != null ? x.getText() : null).equals(this.enclosingRule.name)) if (this.enclosingRule.getLocalAttributeScope(y != null ? y.getText() : null) != null) {
/*      */             break label396;
/*      */           }
/*  295 */       if (this.state.backtracking > 0) { this.state.failed = true; return; }
/*  296 */       throw new FailedPredicateException(this.input, "SET_ENCLOSING_RULE_SCOPE_ATTR", "enclosingRule!=null &&\n\t                         $x.text.equals(enclosingRule.name) &&\n\t                         enclosingRule.getLocalAttributeScope($y.text)!=null");
/*      */ 
/*  298 */       label396: if (this.state.backtracking == 1)
/*      */       {
/*  300 */         StringTemplate st = null;
/*  301 */         AttributeScope scope = this.enclosingRule.getLocalAttributeScope(y != null ? y.getText() : null);
/*  302 */         if (scope.isPredefinedRuleScope) {
/*  303 */           if (!(y != null ? y.getText() : null).equals("st")) { if (!(y != null ? y.getText() : null).equals("tree")); } else {
/*  304 */             st = template("ruleSetPropertyRef_" + (y != null ? y.getText() : null));
/*  305 */             this.grammar.referenceRuleLabelPredefinedAttribute(x != null ? x.getText() : null);
/*  306 */             st.setAttribute("scope", x != null ? x.getText() : null);
/*  307 */             st.setAttribute("attr", y != null ? y.getText() : null);
/*  308 */             st.setAttribute("expr", translateAction(expr != null ? expr.getText() : null)); break label844;
/*      */           }
/*  310 */           ErrorManager.grammarError(151, this.grammar, this.actionToken, x != null ? x.getText() : null, y != null ? y.getText() : null);
/*      */         }
/*  317 */         else if (scope.isPredefinedLexerRuleScope)
/*      */         {
/*  319 */           ErrorManager.grammarError(151, this.grammar, this.actionToken, x != null ? x.getText() : null, y != null ? y.getText() : null);
/*      */         }
/*  325 */         else if (scope.isParameterScope) {
/*  326 */           st = template("parameterSetAttributeRef");
/*  327 */           st.setAttribute("attr", scope.getAttribute(y != null ? y.getText() : null));
/*  328 */           st.setAttribute("expr", translateAction(expr != null ? expr.getText() : null));
/*      */         }
/*      */         else {
/*  331 */           st = template("returnSetAttributeRef");
/*  332 */           st.setAttribute("ruleDescriptor", this.enclosingRule);
/*  333 */           st.setAttribute("attr", scope.getAttribute(y != null ? y.getText() : null));
/*  334 */           st.setAttribute("expr", translateAction(expr != null ? expr.getText() : null));
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/*  341 */       label844: this.state.type = _type;
/*  342 */       this.state.channel = _channel;
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public final void mENCLOSING_RULE_SCOPE_ATTR() throws RecognitionException
/*      */   {
/*      */     try {
/*  352 */       int _type = 8;
/*  353 */       int _channel = 0;
/*  354 */       org.antlr.runtime.CommonToken x = null;
/*  355 */       org.antlr.runtime.CommonToken y = null;
/*      */ 
/*  360 */       match(36); if (this.state.failed) return;
/*  361 */       int xStart96 = getCharIndex();
/*  362 */       mID(); if (this.state.failed) return;
/*  363 */       x = new org.antlr.runtime.CommonToken(this.input, 0, 0, xStart96, getCharIndex() - 1);
/*  364 */       match(46); if (this.state.failed) return;
/*  365 */       int yStart102 = getCharIndex();
/*  366 */       mID(); if (this.state.failed) return;
/*  367 */       y = new org.antlr.runtime.CommonToken(this.input, 0, 0, yStart102, getCharIndex() - 1);
/*  368 */       if (this.enclosingRule != null) if ((x != null ? x.getText() : null).equals(this.enclosingRule.name)) if (this.enclosingRule.getLocalAttributeScope(y != null ? y.getText() : null) != null) {
/*      */             break label222;
/*      */           }
/*  371 */       if (this.state.backtracking > 0) { this.state.failed = true; return; }
/*  372 */       throw new FailedPredicateException(this.input, "ENCLOSING_RULE_SCOPE_ATTR", "enclosingRule!=null &&\n\t                         $x.text.equals(enclosingRule.name) &&\n\t                         enclosingRule.getLocalAttributeScope($y.text)!=null");
/*      */ 
/*  374 */       label222: if (this.state.backtracking == 1)
/*      */       {
/*  376 */         if (isRuleRefInAlt(x != null ? x.getText() : null)) {
/*  377 */           ErrorManager.grammarError(132, this.grammar, this.actionToken, x != null ? x.getText() : null);
/*      */         }
/*      */ 
/*  382 */         StringTemplate st = null;
/*  383 */         AttributeScope scope = this.enclosingRule.getLocalAttributeScope(y != null ? y.getText() : null);
/*  384 */         if (scope.isPredefinedRuleScope) {
/*  385 */           st = template("rulePropertyRef_" + (y != null ? y.getText() : null));
/*  386 */           this.grammar.referenceRuleLabelPredefinedAttribute(x != null ? x.getText() : null);
/*  387 */           st.setAttribute("scope", x != null ? x.getText() : null);
/*  388 */           st.setAttribute("attr", y != null ? y.getText() : null);
/*      */         }
/*  390 */         else if (scope.isPredefinedLexerRuleScope)
/*      */         {
/*  392 */           ErrorManager.grammarError(130, this.grammar, this.actionToken, x != null ? x.getText() : null);
/*      */         }
/*  397 */         else if (scope.isParameterScope) {
/*  398 */           st = template("parameterAttributeRef");
/*  399 */           st.setAttribute("attr", scope.getAttribute(y != null ? y.getText() : null));
/*      */         }
/*      */         else {
/*  402 */           st = template("returnAttributeRef");
/*  403 */           st.setAttribute("ruleDescriptor", this.enclosingRule);
/*  404 */           st.setAttribute("attr", scope.getAttribute(y != null ? y.getText() : null));
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/*  411 */       this.state.type = _type;
/*  412 */       this.state.channel = _channel;
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public final void mSET_TOKEN_SCOPE_ATTR() throws RecognitionException
/*      */   {
/*      */     try {
/*  422 */       int _type = 9;
/*  423 */       int _channel = 0;
/*  424 */       org.antlr.runtime.CommonToken x = null;
/*  425 */       org.antlr.runtime.CommonToken y = null;
/*      */ 
/*  430 */       match(36); if (this.state.failed) return;
/*  431 */       int xStart128 = getCharIndex();
/*  432 */       mID(); if (this.state.failed) return;
/*  433 */       x = new org.antlr.runtime.CommonToken(this.input, 0, 0, xStart128, getCharIndex() - 1);
/*  434 */       match(46); if (this.state.failed) return;
/*  435 */       int yStart134 = getCharIndex();
/*  436 */       mID(); if (this.state.failed) return;
/*  437 */       y = new org.antlr.runtime.CommonToken(this.input, 0, 0, yStart134, getCharIndex() - 1);
/*      */ 
/*  439 */       int alt2 = 2;
/*  440 */       switch (this.input.LA(1))
/*      */       {
/*      */       case 9:
/*      */       case 10:
/*      */       case 13:
/*      */       case 32:
/*  446 */         alt2 = 1;
/*      */       }
/*      */ 
/*  451 */       switch (alt2)
/*      */       {
/*      */       case 1:
/*  455 */         mWS(); if (this.state.failed)
/*      */         {
/*      */           return;
/*      */         }
/*      */         break;
/*      */       }
/*      */ 
/*  462 */       match(61); if (this.state.failed) return;
/*  463 */       if ((this.enclosingRule != null) && (this.input.LA(1) != 61)) if (this.enclosingRule.getTokenLabel(x != null ? x.getText() : null) == null) { if (!isTokenRefInAlt(x != null ? x.getText() : null)); } else if (AttributeScope.tokenScope.getAttribute(y != null ? y.getText() : null) != null)
/*      */           {
/*      */             break label365;
/*      */           }
/*  467 */       if (this.state.backtracking > 0) { this.state.failed = true; return; }
/*  468 */       throw new FailedPredicateException(this.input, "SET_TOKEN_SCOPE_ATTR", "enclosingRule!=null && input.LA(1)!='=' &&\n\t                         (enclosingRule.getTokenLabel($x.text)!=null||\n\t                          isTokenRefInAlt($x.text)) &&\n\t                         AttributeScope.tokenScope.getAttribute($y.text)!=null");
/*      */ 
/*  470 */       label365: if (this.state.backtracking == 1)
/*      */       {
/*  472 */         ErrorManager.grammarError(151, this.grammar, this.actionToken, x != null ? x.getText() : null, y != null ? y.getText() : null);
/*      */       }
/*      */ 
/*  482 */       this.state.type = _type;
/*  483 */       this.state.channel = _channel;
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public final void mTOKEN_SCOPE_ATTR() throws RecognitionException
/*      */   {
/*      */     try {
/*  493 */       int _type = 10;
/*  494 */       int _channel = 0;
/*  495 */       org.antlr.runtime.CommonToken x = null;
/*  496 */       org.antlr.runtime.CommonToken y = null;
/*      */ 
/*  501 */       match(36); if (this.state.failed) return;
/*  502 */       int xStart173 = getCharIndex();
/*  503 */       mID(); if (this.state.failed) return;
/*  504 */       x = new org.antlr.runtime.CommonToken(this.input, 0, 0, xStart173, getCharIndex() - 1);
/*  505 */       match(46); if (this.state.failed) return;
/*  506 */       int yStart179 = getCharIndex();
/*  507 */       mID(); if (this.state.failed) return;
/*  508 */       y = new org.antlr.runtime.CommonToken(this.input, 0, 0, yStart179, getCharIndex() - 1);
/*  509 */       if (this.enclosingRule != null) if (this.enclosingRule.getTokenLabel(x != null ? x.getText() : null) == null) { if (!isTokenRefInAlt(x != null ? x.getText() : null)); } else if (AttributeScope.tokenScope.getAttribute(y != null ? y.getText() : null) != null) { if (this.grammar.type != 1) break label308; if (getElementLabel(x != null ? x.getText() : null).elementRef.token.getType() == 55) break label308; if (getElementLabel(x != null ? x.getText() : null).elementRef.token.getType() == 50)
/*      */           {
/*      */             break label308;
/*      */           }
/*      */         }
/*      */ 
/*      */ 
/*  516 */       if (this.state.backtracking > 0) { this.state.failed = true; return; }
/*  517 */       throw new FailedPredicateException(this.input, "TOKEN_SCOPE_ATTR", "enclosingRule!=null &&\n\t                         (enclosingRule.getTokenLabel($x.text)!=null||\n\t                          isTokenRefInAlt($x.text)) &&\n\t                         AttributeScope.tokenScope.getAttribute($y.text)!=null &&\n\t                         (grammar.type!=Grammar.LEXER ||\n\t                         getElementLabel($x.text).elementRef.token.getType()==ANTLRParser.TOKEN_REF ||\n\t                         getElementLabel($x.text).elementRef.token.getType()==ANTLRParser.STRING_LITERAL)");
/*      */ 
/*  519 */       label308: if (this.state.backtracking == 1)
/*      */       {
/*  521 */         String label = x != null ? x.getText() : null;
/*  522 */         if (this.enclosingRule.getTokenLabel(x != null ? x.getText() : null) == null)
/*      */         {
/*  524 */           checkElementRefUniqueness(x != null ? x.getText() : null, true);
/*  525 */           label = this.enclosingRule.getElementLabel(x != null ? x.getText() : null, this.outerAltNum, this.generator);
/*  526 */           if (label == null) {
/*  527 */             ErrorManager.grammarError(128, this.grammar, this.actionToken, "$" + (x != null ? x.getText() : null) + "." + (y != null ? y.getText() : null));
/*      */ 
/*  531 */             label = x != null ? x.getText() : null;
/*      */           }
/*      */         }
/*  534 */         StringTemplate st = template("tokenLabelPropertyRef_" + (y != null ? y.getText() : null));
/*  535 */         st.setAttribute("scope", label);
/*  536 */         st.setAttribute("attr", AttributeScope.tokenScope.getAttribute(y != null ? y.getText() : null));
/*      */       }
/*      */ 
/*  542 */       this.state.type = _type;
/*  543 */       this.state.channel = _channel;
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public final void mSET_RULE_SCOPE_ATTR() throws RecognitionException
/*      */   {
/*      */     try {
/*  553 */       int _type = 11;
/*  554 */       int _channel = 0;
/*  555 */       org.antlr.runtime.CommonToken x = null;
/*  556 */       org.antlr.runtime.CommonToken y = null;
/*      */ 
/*  559 */       Grammar.LabelElementPair pair = null;
/*  560 */       String refdRuleName = null;
/*      */ 
/*  565 */       match(36); if (this.state.failed) return;
/*  566 */       int xStart210 = getCharIndex();
/*  567 */       mID(); if (this.state.failed) return;
/*  568 */       x = new org.antlr.runtime.CommonToken(this.input, 0, 0, xStart210, getCharIndex() - 1);
/*  569 */       match(46); if (this.state.failed) return;
/*  570 */       int yStart216 = getCharIndex();
/*  571 */       mID(); if (this.state.failed) return;
/*  572 */       y = new org.antlr.runtime.CommonToken(this.input, 0, 0, yStart216, getCharIndex() - 1);
/*      */ 
/*  574 */       int alt3 = 2;
/*  575 */       switch (this.input.LA(1))
/*      */       {
/*      */       case 9:
/*      */       case 10:
/*      */       case 13:
/*      */       case 32:
/*  581 */         alt3 = 1;
/*      */       }
/*      */ 
/*  586 */       switch (alt3)
/*      */       {
/*      */       case 1:
/*  590 */         mWS(); if (this.state.failed)
/*      */         {
/*      */           return;
/*      */         }
/*      */         break;
/*      */       }
/*      */ 
/*  597 */       match(61); if (this.state.failed) return;
/*  598 */       if ((this.enclosingRule == null) || (this.input.LA(1) == 61)) {
/*  599 */         if (this.state.backtracking > 0) { this.state.failed = true; return; }
/*  600 */         throw new FailedPredicateException(this.input, "SET_RULE_SCOPE_ATTR", "enclosingRule!=null && input.LA(1)!='='");
/*      */       }
/*  602 */       if (this.state.backtracking == 1)
/*      */       {
/*  604 */         pair = this.enclosingRule.getRuleLabel(x != null ? x.getText() : null);
/*  605 */         refdRuleName = x != null ? x.getText() : null;
/*  606 */         if (pair != null) {
/*  607 */           refdRuleName = pair.referencedRuleName;
/*      */         }
/*      */       }
/*      */ 
/*  611 */       if (this.enclosingRule.getRuleLabel(x != null ? x.getText() : null) == null) { if (!isRuleRefInAlt(x != null ? x.getText() : null)); } else if (getRuleLabelAttribute(x != null ? x.getText() : this.enclosingRule.getRuleLabel(x != null ? x.getText() : null) != null ? this.enclosingRule.getRuleLabel(x != null ? x.getText() : null).referencedRuleName : null, y != null ? y.getText() : null) != null)
/*      */           break label519;
/*  613 */       if (this.state.backtracking > 0) { this.state.failed = true; return; }
/*  614 */       throw new FailedPredicateException(this.input, "SET_RULE_SCOPE_ATTR", "(enclosingRule.getRuleLabel($x.text)!=null || isRuleRefInAlt($x.text)) &&\n\t      getRuleLabelAttribute(enclosingRule.getRuleLabel($x.text)!=null?enclosingRule.getRuleLabel($x.text).referencedRuleName:$x.text,$y.text)!=null");
/*      */ 
/*  616 */       label519: if (this.state.backtracking == 1)
/*      */       {
/*  618 */         ErrorManager.grammarError(151, this.grammar, this.actionToken, x != null ? x.getText() : null, y != null ? y.getText() : null);
/*      */       }
/*      */ 
/*  628 */       this.state.type = _type;
/*  629 */       this.state.channel = _channel;
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public final void mRULE_SCOPE_ATTR() throws RecognitionException
/*      */   {
/*      */     try {
/*  639 */       int _type = 12;
/*  640 */       int _channel = 0;
/*  641 */       org.antlr.runtime.CommonToken x = null;
/*  642 */       org.antlr.runtime.CommonToken y = null;
/*      */ 
/*  645 */       Grammar.LabelElementPair pair = null;
/*  646 */       String refdRuleName = null;
/*      */ 
/*  651 */       match(36); if (this.state.failed) return;
/*  652 */       int xStart269 = getCharIndex();
/*  653 */       mID(); if (this.state.failed) return;
/*  654 */       x = new org.antlr.runtime.CommonToken(this.input, 0, 0, xStart269, getCharIndex() - 1);
/*  655 */       match(46); if (this.state.failed) return;
/*  656 */       int yStart275 = getCharIndex();
/*  657 */       mID(); if (this.state.failed) return;
/*  658 */       y = new org.antlr.runtime.CommonToken(this.input, 0, 0, yStart275, getCharIndex() - 1);
/*  659 */       if (this.enclosingRule == null) {
/*  660 */         if (this.state.backtracking > 0) { this.state.failed = true; return; }
/*  661 */         throw new FailedPredicateException(this.input, "RULE_SCOPE_ATTR", "enclosingRule!=null");
/*      */       }
/*  663 */       if (this.state.backtracking == 1)
/*      */       {
/*  665 */         pair = this.enclosingRule.getRuleLabel(x != null ? x.getText() : null);
/*  666 */         refdRuleName = x != null ? x.getText() : null;
/*  667 */         if (pair != null) {
/*  668 */           refdRuleName = pair.referencedRuleName;
/*      */         }
/*      */       }
/*      */ 
/*  672 */       if (this.enclosingRule.getRuleLabel(x != null ? x.getText() : null) == null) { if (!isRuleRefInAlt(x != null ? x.getText() : null)); } else if (getRuleLabelAttribute(x != null ? x.getText() : this.enclosingRule.getRuleLabel(x != null ? x.getText() : null) != null ? this.enclosingRule.getRuleLabel(x != null ? x.getText() : null).referencedRuleName : null, y != null ? y.getText() : null) != null)
/*      */           break label393;
/*  674 */       if (this.state.backtracking > 0) { this.state.failed = true; return; }
/*  675 */       throw new FailedPredicateException(this.input, "RULE_SCOPE_ATTR", "(enclosingRule.getRuleLabel($x.text)!=null || isRuleRefInAlt($x.text)) &&\n\t      getRuleLabelAttribute(enclosingRule.getRuleLabel($x.text)!=null?enclosingRule.getRuleLabel($x.text).referencedRuleName:$x.text,$y.text)!=null");
/*      */ 
/*  677 */       label393: if (this.state.backtracking == 1)
/*      */       {
/*  679 */         String label = x != null ? x.getText() : null;
/*  680 */         if (pair == null)
/*      */         {
/*  682 */           checkElementRefUniqueness(x != null ? x.getText() : null, false);
/*  683 */           label = this.enclosingRule.getElementLabel(x != null ? x.getText() : null, this.outerAltNum, this.generator);
/*  684 */           if (label == null) {
/*  685 */             ErrorManager.grammarError(128, this.grammar, this.actionToken, "$" + (x != null ? x.getText() : null) + "." + (y != null ? y.getText() : null));
/*      */ 
/*  689 */             label = x != null ? x.getText() : null;
/*      */           }
/*      */         }
/*      */ 
/*  693 */         Rule refdRule = this.grammar.getRule(refdRuleName);
/*  694 */         AttributeScope scope = refdRule.getLocalAttributeScope(y != null ? y.getText() : null);
/*  695 */         if (scope.isPredefinedRuleScope) {
/*  696 */           StringTemplate st = template("ruleLabelPropertyRef_" + (y != null ? y.getText() : null));
/*  697 */           this.grammar.referenceRuleLabelPredefinedAttribute(refdRuleName);
/*  698 */           st.setAttribute("scope", label);
/*  699 */           st.setAttribute("attr", y != null ? y.getText() : null);
/*      */         }
/*  701 */         else if (scope.isPredefinedLexerRuleScope) {
/*  702 */           StringTemplate st = template("lexerRuleLabelPropertyRef_" + (y != null ? y.getText() : null));
/*  703 */           this.grammar.referenceRuleLabelPredefinedAttribute(refdRuleName);
/*  704 */           st.setAttribute("scope", label);
/*  705 */           st.setAttribute("attr", y != null ? y.getText() : null);
/*      */         }
/*  707 */         else if (!scope.isParameterScope)
/*      */         {
/*  711 */           StringTemplate st = template("ruleLabelRef");
/*  712 */           st.setAttribute("referencedRule", refdRule);
/*  713 */           st.setAttribute("scope", label);
/*  714 */           st.setAttribute("attr", scope.getAttribute(y != null ? y.getText() : null));
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/*  721 */       this.state.type = _type;
/*  722 */       this.state.channel = _channel;
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public final void mLABEL_REF() throws RecognitionException
/*      */   {
/*      */     try {
/*  732 */       int _type = 13;
/*  733 */       int _channel = 0;
/*  734 */       org.antlr.runtime.CommonToken ID1 = null;
/*      */ 
/*  739 */       match(36); if (this.state.failed) return;
/*  740 */       int ID1Start317 = getCharIndex();
/*  741 */       mID(); if (this.state.failed) return;
/*  742 */       ID1 = new org.antlr.runtime.CommonToken(this.input, 0, 0, ID1Start317, getCharIndex() - 1);
/*  743 */       if (this.enclosingRule != null) if (getElementLabel(ID1 != null ? ID1.getText() : null) != null) if (this.enclosingRule.getRuleLabel(ID1 != null ? ID1.getText() : null) == null) {
/*      */             break label150;
/*      */           }
/*  746 */       if (this.state.backtracking > 0) { this.state.failed = true; return; }
/*  747 */       throw new FailedPredicateException(this.input, "LABEL_REF", "enclosingRule!=null &&\n\t            getElementLabel($ID.text)!=null &&\n\t\t        enclosingRule.getRuleLabel($ID.text)==null");
/*      */ 
/*  749 */       label150: if (this.state.backtracking == 1)
/*      */       {
/*  752 */         Grammar.LabelElementPair pair = getElementLabel(ID1 != null ? ID1.getText() : null);
/*      */         StringTemplate st;
/*      */         StringTemplate st;
/*  753 */         if ((pair.type == 3) || (pair.type == 4) || (pair.type == 7))
/*      */         {
/*  757 */           st = template("listLabelRef");
/*      */         }
/*      */         else {
/*  760 */           st = template("tokenLabelRef");
/*      */         }
/*  762 */         st.setAttribute("label", ID1 != null ? ID1.getText() : null);
/*      */       }
/*      */ 
/*  768 */       this.state.type = _type;
/*  769 */       this.state.channel = _channel;
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public final void mISOLATED_TOKEN_REF() throws RecognitionException
/*      */   {
/*      */     try {
/*  779 */       int _type = 14;
/*  780 */       int _channel = 0;
/*  781 */       org.antlr.runtime.CommonToken ID2 = null;
/*      */ 
/*  786 */       match(36); if (this.state.failed) return;
/*  787 */       int ID2Start341 = getCharIndex();
/*  788 */       mID(); if (this.state.failed) return;
/*  789 */       ID2 = new org.antlr.runtime.CommonToken(this.input, 0, 0, ID2Start341, getCharIndex() - 1);
/*  790 */       if ((this.grammar.type != 1) && (this.enclosingRule != null)) { if (isTokenRefInAlt(ID2 != null ? ID2.getText() : null)); } else {
/*  791 */         if (this.state.backtracking > 0) { this.state.failed = true; return; }
/*  792 */         throw new FailedPredicateException(this.input, "ISOLATED_TOKEN_REF", "grammar.type!=Grammar.LEXER && enclosingRule!=null && isTokenRefInAlt($ID.text)");
/*      */       }
/*  794 */       if (this.state.backtracking == 1)
/*      */       {
/*  796 */         String label = this.enclosingRule.getElementLabel(ID2 != null ? ID2.getText() : null, this.outerAltNum, this.generator);
/*  797 */         checkElementRefUniqueness(ID2 != null ? ID2.getText() : null, true);
/*  798 */         if (label == null) {
/*  799 */           ErrorManager.grammarError(128, this.grammar, this.actionToken, ID2 != null ? ID2.getText() : null);
/*      */         }
/*      */         else
/*      */         {
/*  805 */           StringTemplate st = template("tokenLabelRef");
/*  806 */           st.setAttribute("label", label);
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/*  813 */       this.state.type = _type;
/*  814 */       this.state.channel = _channel;
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public final void mISOLATED_LEXER_RULE_REF() throws RecognitionException
/*      */   {
/*      */     try {
/*  824 */       int _type = 15;
/*  825 */       int _channel = 0;
/*  826 */       org.antlr.runtime.CommonToken ID3 = null;
/*      */ 
/*  831 */       match(36); if (this.state.failed) return;
/*  832 */       int ID3Start365 = getCharIndex();
/*  833 */       mID(); if (this.state.failed) return;
/*  834 */       ID3 = new org.antlr.runtime.CommonToken(this.input, 0, 0, ID3Start365, getCharIndex() - 1);
/*  835 */       if ((this.grammar.type == 1) && (this.enclosingRule != null)) { if (isRuleRefInAlt(ID3 != null ? ID3.getText() : null));
/*      */       } else
/*      */       {
/*  838 */         if (this.state.backtracking > 0) { this.state.failed = true; return; }
/*  839 */         throw new FailedPredicateException(this.input, "ISOLATED_LEXER_RULE_REF", "grammar.type==Grammar.LEXER &&\n\t             enclosingRule!=null &&\n\t             isRuleRefInAlt($ID.text)");
/*      */       }
/*  841 */       if (this.state.backtracking == 1)
/*      */       {
/*  843 */         String label = this.enclosingRule.getElementLabel(ID3 != null ? ID3.getText() : null, this.outerAltNum, this.generator);
/*  844 */         checkElementRefUniqueness(ID3 != null ? ID3.getText() : null, false);
/*  845 */         if (label == null) {
/*  846 */           ErrorManager.grammarError(128, this.grammar, this.actionToken, ID3 != null ? ID3.getText() : null);
/*      */         }
/*      */         else
/*      */         {
/*  852 */           StringTemplate st = template("lexerRuleLabel");
/*  853 */           st.setAttribute("label", label);
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/*  860 */       this.state.type = _type;
/*  861 */       this.state.channel = _channel;
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public final void mSET_LOCAL_ATTR() throws RecognitionException
/*      */   {
/*      */     try {
/*  871 */       int _type = 16;
/*  872 */       int _channel = 0;
/*  873 */       org.antlr.runtime.CommonToken expr = null;
/*  874 */       org.antlr.runtime.CommonToken ID4 = null;
/*      */ 
/*  879 */       match(36); if (this.state.failed) return;
/*  880 */       int ID4Start389 = getCharIndex();
/*  881 */       mID(); if (this.state.failed) return;
/*  882 */       ID4 = new org.antlr.runtime.CommonToken(this.input, 0, 0, ID4Start389, getCharIndex() - 1);
/*      */ 
/*  884 */       int alt4 = 2;
/*  885 */       switch (this.input.LA(1))
/*      */       {
/*      */       case 9:
/*      */       case 10:
/*      */       case 13:
/*      */       case 32:
/*  891 */         alt4 = 1;
/*      */       }
/*      */ 
/*  896 */       switch (alt4)
/*      */       {
/*      */       case 1:
/*  900 */         mWS(); if (this.state.failed)
/*      */         {
/*      */           return;
/*      */         }
/*      */         break;
/*      */       }
/*      */ 
/*  907 */       match(61); if (this.state.failed) return;
/*  908 */       int exprStart398 = getCharIndex();
/*  909 */       mATTR_VALUE_EXPR(); if (this.state.failed) return;
/*  910 */       expr = new org.antlr.runtime.CommonToken(this.input, 0, 0, exprStart398, getCharIndex() - 1);
/*  911 */       match(59); if (this.state.failed) return;
/*  912 */       if (this.enclosingRule != null) if (this.enclosingRule.getLocalAttributeScope(ID4 != null ? ID4.getText() : null) != null) if (!this.enclosingRule.getLocalAttributeScope(ID4 != null ? ID4.getText() : null).isPredefinedLexerRuleScope) {
/*      */             break label337;
/*      */           }
/*  915 */       if (this.state.backtracking > 0) { this.state.failed = true; return; }
/*  916 */       throw new FailedPredicateException(this.input, "SET_LOCAL_ATTR", "enclosingRule!=null\n\t\t\t\t\t\t\t\t\t\t\t\t\t&& enclosingRule.getLocalAttributeScope($ID.text)!=null\n\t\t\t\t\t\t\t\t\t\t\t\t\t&& !enclosingRule.getLocalAttributeScope($ID.text).isPredefinedLexerRuleScope");
/*      */ 
/*  918 */       label337: if (this.state.backtracking == 1)
/*      */       {
/*  921 */         AttributeScope scope = this.enclosingRule.getLocalAttributeScope(ID4 != null ? ID4.getText() : null);
/*  922 */         if (scope.isPredefinedRuleScope) {
/*  923 */           if (!(ID4 != null ? ID4.getText() : null).equals("tree")) { if (!(ID4 != null ? ID4.getText() : null).equals("st")); } else {
/*  924 */             StringTemplate st = template("ruleSetPropertyRef_" + (ID4 != null ? ID4.getText() : null));
/*  925 */             this.grammar.referenceRuleLabelPredefinedAttribute(this.enclosingRule.name);
/*  926 */             st.setAttribute("scope", this.enclosingRule.name);
/*  927 */             st.setAttribute("attr", ID4 != null ? ID4.getText() : null);
/*  928 */             st.setAttribute("expr", translateAction(expr != null ? expr.getText() : null)); break label705;
/*      */           }
/*  930 */           ErrorManager.grammarError(151, this.grammar, this.actionToken, ID4 != null ? ID4.getText() : null, "");
/*      */         }
/*  937 */         else if (scope.isParameterScope) {
/*  938 */           StringTemplate st = template("parameterSetAttributeRef");
/*  939 */           st.setAttribute("attr", scope.getAttribute(ID4 != null ? ID4.getText() : null));
/*  940 */           st.setAttribute("expr", translateAction(expr != null ? expr.getText() : null));
/*      */         }
/*      */         else {
/*  943 */           StringTemplate st = template("returnSetAttributeRef");
/*  944 */           st.setAttribute("ruleDescriptor", this.enclosingRule);
/*  945 */           st.setAttribute("attr", scope.getAttribute(ID4 != null ? ID4.getText() : null));
/*  946 */           st.setAttribute("expr", translateAction(expr != null ? expr.getText() : null));
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/*  953 */       label705: this.state.type = _type;
/*  954 */       this.state.channel = _channel;
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public final void mLOCAL_ATTR() throws RecognitionException
/*      */   {
/*      */     try {
/*  964 */       int _type = 17;
/*  965 */       int _channel = 0;
/*  966 */       org.antlr.runtime.CommonToken ID5 = null;
/*      */ 
/*  971 */       match(36); if (this.state.failed) return;
/*  972 */       int ID5Start421 = getCharIndex();
/*  973 */       mID(); if (this.state.failed) return;
/*  974 */       ID5 = new org.antlr.runtime.CommonToken(this.input, 0, 0, ID5Start421, getCharIndex() - 1);
/*  975 */       if (this.enclosingRule != null) { if (this.enclosingRule.getLocalAttributeScope(ID5 != null ? ID5.getText() : null) != null); } else {
/*  976 */         if (this.state.backtracking > 0) { this.state.failed = true; return; }
/*  977 */         throw new FailedPredicateException(this.input, "LOCAL_ATTR", "enclosingRule!=null && enclosingRule.getLocalAttributeScope($ID.text)!=null");
/*      */       }
/*  979 */       if (this.state.backtracking == 1)
/*      */       {
/*  982 */         AttributeScope scope = this.enclosingRule.getLocalAttributeScope(ID5 != null ? ID5.getText() : null);
/*  983 */         if (scope.isPredefinedRuleScope) {
/*  984 */           StringTemplate st = template("rulePropertyRef_" + (ID5 != null ? ID5.getText() : null));
/*  985 */           this.grammar.referenceRuleLabelPredefinedAttribute(this.enclosingRule.name);
/*  986 */           st.setAttribute("scope", this.enclosingRule.name);
/*  987 */           st.setAttribute("attr", ID5 != null ? ID5.getText() : null);
/*      */         }
/*  989 */         else if (scope.isPredefinedLexerRuleScope) {
/*  990 */           StringTemplate st = template("lexerRulePropertyRef_" + (ID5 != null ? ID5.getText() : null));
/*  991 */           st.setAttribute("scope", this.enclosingRule.name);
/*  992 */           st.setAttribute("attr", ID5 != null ? ID5.getText() : null);
/*      */         }
/*  994 */         else if (scope.isParameterScope) {
/*  995 */           StringTemplate st = template("parameterAttributeRef");
/*  996 */           st.setAttribute("attr", scope.getAttribute(ID5 != null ? ID5.getText() : null));
/*      */         }
/*      */         else {
/*  999 */           StringTemplate st = template("returnAttributeRef");
/* 1000 */           st.setAttribute("ruleDescriptor", this.enclosingRule);
/* 1001 */           st.setAttribute("attr", scope.getAttribute(ID5 != null ? ID5.getText() : null));
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/* 1008 */       this.state.type = _type;
/* 1009 */       this.state.channel = _channel;
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public final void mSET_DYNAMIC_SCOPE_ATTR() throws RecognitionException
/*      */   {
/*      */     try {
/* 1019 */       int _type = 18;
/* 1020 */       int _channel = 0;
/* 1021 */       org.antlr.runtime.CommonToken x = null;
/* 1022 */       org.antlr.runtime.CommonToken y = null;
/* 1023 */       org.antlr.runtime.CommonToken expr = null;
/*      */ 
/* 1028 */       match(36); if (this.state.failed) return;
/* 1029 */       int xStart447 = getCharIndex();
/* 1030 */       mID(); if (this.state.failed) return;
/* 1031 */       x = new org.antlr.runtime.CommonToken(this.input, 0, 0, xStart447, getCharIndex() - 1);
/* 1032 */       match("::"); if (this.state.failed) return;
/*      */ 
/* 1034 */       int yStart453 = getCharIndex();
/* 1035 */       mID(); if (this.state.failed) return;
/* 1036 */       y = new org.antlr.runtime.CommonToken(this.input, 0, 0, yStart453, getCharIndex() - 1);
/*      */ 
/* 1038 */       int alt5 = 2;
/* 1039 */       switch (this.input.LA(1))
/*      */       {
/*      */       case 9:
/*      */       case 10:
/*      */       case 13:
/*      */       case 32:
/* 1045 */         alt5 = 1;
/*      */       }
/*      */ 
/* 1050 */       switch (alt5)
/*      */       {
/*      */       case 1:
/* 1054 */         mWS(); if (this.state.failed)
/*      */         {
/*      */           return;
/*      */         }
/*      */         break;
/*      */       }
/*      */ 
/* 1061 */       match(61); if (this.state.failed) return;
/* 1062 */       int exprStart462 = getCharIndex();
/* 1063 */       mATTR_VALUE_EXPR(); if (this.state.failed) return;
/* 1064 */       expr = new org.antlr.runtime.CommonToken(this.input, 0, 0, exprStart462, getCharIndex() - 1);
/* 1065 */       match(59); if (this.state.failed) return;
/* 1066 */       if (resolveDynamicScope(x != null ? x.getText() : null) != null) { if (resolveDynamicScope(x != null ? x.getText() : null).getAttribute(y != null ? y.getText() : null) != null);
/*      */       } else {
/* 1068 */         if (this.state.backtracking > 0) { this.state.failed = true; return; }
/* 1069 */         throw new FailedPredicateException(this.input, "SET_DYNAMIC_SCOPE_ATTR", "resolveDynamicScope($x.text)!=null &&\n\t\t\t\t\t\t     resolveDynamicScope($x.text).getAttribute($y.text)!=null");
/*      */       }
/* 1071 */       if (this.state.backtracking == 1)
/*      */       {
/* 1073 */         AttributeScope scope = resolveDynamicScope(x != null ? x.getText() : null);
/* 1074 */         if (scope != null) {
/* 1075 */           StringTemplate st = template("scopeSetAttributeRef");
/* 1076 */           st.setAttribute("scope", x != null ? x.getText() : null);
/* 1077 */           st.setAttribute("attr", scope.getAttribute(y != null ? y.getText() : null));
/* 1078 */           st.setAttribute("expr", translateAction(expr != null ? expr.getText() : null));
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/* 1088 */       this.state.type = _type;
/* 1089 */       this.state.channel = _channel;
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public final void mDYNAMIC_SCOPE_ATTR() throws RecognitionException
/*      */   {
/*      */     try {
/* 1099 */       int _type = 19;
/* 1100 */       int _channel = 0;
/* 1101 */       org.antlr.runtime.CommonToken x = null;
/* 1102 */       org.antlr.runtime.CommonToken y = null;
/*      */ 
/* 1107 */       match(36); if (this.state.failed) return;
/* 1108 */       int xStart497 = getCharIndex();
/* 1109 */       mID(); if (this.state.failed) return;
/* 1110 */       x = new org.antlr.runtime.CommonToken(this.input, 0, 0, xStart497, getCharIndex() - 1);
/* 1111 */       match("::"); if (this.state.failed) return;
/*      */ 
/* 1113 */       int yStart503 = getCharIndex();
/* 1114 */       mID(); if (this.state.failed) return;
/* 1115 */       y = new org.antlr.runtime.CommonToken(this.input, 0, 0, yStart503, getCharIndex() - 1);
/* 1116 */       if (resolveDynamicScope(x != null ? x.getText() : null) != null) { if (resolveDynamicScope(x != null ? x.getText() : null).getAttribute(y != null ? y.getText() : null) != null);
/*      */       } else {
/* 1118 */         if (this.state.backtracking > 0) { this.state.failed = true; return; }
/* 1119 */         throw new FailedPredicateException(this.input, "DYNAMIC_SCOPE_ATTR", "resolveDynamicScope($x.text)!=null &&\n\t\t\t\t\t\t     resolveDynamicScope($x.text).getAttribute($y.text)!=null");
/*      */       }
/* 1121 */       if (this.state.backtracking == 1)
/*      */       {
/* 1123 */         AttributeScope scope = resolveDynamicScope(x != null ? x.getText() : null);
/* 1124 */         if (scope != null) {
/* 1125 */           StringTemplate st = template("scopeAttributeRef");
/* 1126 */           st.setAttribute("scope", x != null ? x.getText() : null);
/* 1127 */           st.setAttribute("attr", scope.getAttribute(y != null ? y.getText() : null));
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/* 1137 */       this.state.type = _type;
/* 1138 */       this.state.channel = _channel;
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public final void mERROR_SCOPED_XY() throws RecognitionException
/*      */   {
/*      */     try {
/* 1148 */       int _type = 20;
/* 1149 */       int _channel = 0;
/* 1150 */       org.antlr.runtime.CommonToken x = null;
/* 1151 */       org.antlr.runtime.CommonToken y = null;
/*      */ 
/* 1156 */       match(36); if (this.state.failed) return;
/* 1157 */       int xStart537 = getCharIndex();
/* 1158 */       mID(); if (this.state.failed) return;
/* 1159 */       x = new org.antlr.runtime.CommonToken(this.input, 0, 0, xStart537, getCharIndex() - 1);
/* 1160 */       match("::"); if (this.state.failed) return;
/*      */ 
/* 1162 */       int yStart543 = getCharIndex();
/* 1163 */       mID(); if (this.state.failed) return;
/* 1164 */       y = new org.antlr.runtime.CommonToken(this.input, 0, 0, yStart543, getCharIndex() - 1);
/* 1165 */       if (this.state.backtracking == 1)
/*      */       {
/* 1167 */         this.chunks.add(getText());
/* 1168 */         this.generator.issueInvalidScopeError(x != null ? x.getText() : null, y != null ? y.getText() : null, this.enclosingRule, this.actionToken, this.outerAltNum);
/*      */       }
/*      */ 
/* 1176 */       this.state.type = _type;
/* 1177 */       this.state.channel = _channel;
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public final void mDYNAMIC_NEGATIVE_INDEXED_SCOPE_ATTR() throws RecognitionException
/*      */   {
/*      */     try {
/* 1187 */       int _type = 22;
/* 1188 */       int _channel = 0;
/* 1189 */       org.antlr.runtime.CommonToken x = null;
/* 1190 */       org.antlr.runtime.CommonToken expr = null;
/* 1191 */       org.antlr.runtime.CommonToken y = null;
/*      */ 
/* 1196 */       match(36); if (this.state.failed) return;
/* 1197 */       int xStart565 = getCharIndex();
/* 1198 */       mID(); if (this.state.failed) return;
/* 1199 */       x = new org.antlr.runtime.CommonToken(this.input, 0, 0, xStart565, getCharIndex() - 1);
/* 1200 */       match(91); if (this.state.failed) return;
/* 1201 */       match(45); if (this.state.failed) return;
/* 1202 */       int exprStart573 = getCharIndex();
/* 1203 */       mSCOPE_INDEX_EXPR(); if (this.state.failed) return;
/* 1204 */       expr = new org.antlr.runtime.CommonToken(this.input, 0, 0, exprStart573, getCharIndex() - 1);
/* 1205 */       match(93); if (this.state.failed) return;
/* 1206 */       match("::"); if (this.state.failed) return;
/*      */ 
/* 1208 */       int yStart581 = getCharIndex();
/* 1209 */       mID(); if (this.state.failed) return;
/* 1210 */       y = new org.antlr.runtime.CommonToken(this.input, 0, 0, yStart581, getCharIndex() - 1);
/* 1211 */       if (this.state.backtracking == 1)
/*      */       {
/* 1213 */         StringTemplate st = template("scopeAttributeRef");
/* 1214 */         st.setAttribute("scope", x != null ? x.getText() : null);
/* 1215 */         st.setAttribute("attr", resolveDynamicScope(x != null ? x.getText() : null).getAttribute(y != null ? y.getText() : null));
/* 1216 */         st.setAttribute("negIndex", expr != null ? expr.getText() : null);
/*      */       }
/*      */ 
/* 1222 */       this.state.type = _type;
/* 1223 */       this.state.channel = _channel;
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public final void mDYNAMIC_ABSOLUTE_INDEXED_SCOPE_ATTR() throws RecognitionException
/*      */   {
/*      */     try {
/* 1233 */       int _type = 23;
/* 1234 */       int _channel = 0;
/* 1235 */       org.antlr.runtime.CommonToken x = null;
/* 1236 */       org.antlr.runtime.CommonToken expr = null;
/* 1237 */       org.antlr.runtime.CommonToken y = null;
/*      */ 
/* 1242 */       match(36); if (this.state.failed) return;
/* 1243 */       int xStart605 = getCharIndex();
/* 1244 */       mID(); if (this.state.failed) return;
/* 1245 */       x = new org.antlr.runtime.CommonToken(this.input, 0, 0, xStart605, getCharIndex() - 1);
/* 1246 */       match(91); if (this.state.failed) return;
/* 1247 */       int exprStart611 = getCharIndex();
/* 1248 */       mSCOPE_INDEX_EXPR(); if (this.state.failed) return;
/* 1249 */       expr = new org.antlr.runtime.CommonToken(this.input, 0, 0, exprStart611, getCharIndex() - 1);
/* 1250 */       match(93); if (this.state.failed) return;
/* 1251 */       match("::"); if (this.state.failed) return;
/*      */ 
/* 1253 */       int yStart619 = getCharIndex();
/* 1254 */       mID(); if (this.state.failed) return;
/* 1255 */       y = new org.antlr.runtime.CommonToken(this.input, 0, 0, yStart619, getCharIndex() - 1);
/* 1256 */       if (this.state.backtracking == 1)
/*      */       {
/* 1258 */         StringTemplate st = template("scopeAttributeRef");
/* 1259 */         st.setAttribute("scope", x != null ? x.getText() : null);
/* 1260 */         st.setAttribute("attr", resolveDynamicScope(x != null ? x.getText() : null).getAttribute(y != null ? y.getText() : null));
/* 1261 */         st.setAttribute("index", expr != null ? expr.getText() : null);
/*      */       }
/*      */ 
/* 1267 */       this.state.type = _type;
/* 1268 */       this.state.channel = _channel;
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public final void mSCOPE_INDEX_EXPR()
/*      */     throws RecognitionException
/*      */   {
/*      */     try
/*      */     {
/* 1282 */       int cnt6 = 0;
/*      */       while (true)
/*      */       {
/* 1285 */         int alt6 = 2;
/* 1286 */         int LA6_0 = this.input.LA(1);
/*      */ 
/* 1288 */         if (((LA6_0 >= 0) && (LA6_0 <= 92)) || ((LA6_0 >= 94) && (LA6_0 <= 65535))) {
/* 1289 */           alt6 = 1;
/*      */         }
/*      */ 
/* 1293 */         switch (alt6)
/*      */         {
/*      */         case 1:
/* 1297 */           if (((this.input.LA(1) >= 0) && (this.input.LA(1) <= 92)) || ((this.input.LA(1) >= 94) && (this.input.LA(1) <= 65535))) {
/* 1298 */             this.input.consume();
/* 1299 */             this.state.failed = false;
/*      */           }
/*      */           else {
/* 1302 */             if (this.state.backtracking > 0) { this.state.failed = true; return; }
/* 1303 */             MismatchedSetException mse = new MismatchedSetException(null, this.input);
/* 1304 */             recover(mse);
/* 1305 */             throw mse;
/*      */           }
/*      */ 
/*      */           break;
/*      */         default:
/* 1312 */           if (cnt6 >= 1) return;
/* 1313 */           if (this.state.backtracking > 0) { this.state.failed = true; return; }
/* 1314 */           EarlyExitException eee = new EarlyExitException(6, this.input);
/*      */ 
/* 1316 */           throw eee;
/*      */         }
/* 1318 */         cnt6++;
/*      */       }
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public final void mISOLATED_DYNAMIC_SCOPE()
/*      */     throws RecognitionException
/*      */   {
/*      */     try
/*      */     {
/* 1333 */       int _type = 24;
/* 1334 */       int _channel = 0;
/* 1335 */       org.antlr.runtime.CommonToken ID6 = null;
/*      */ 
/* 1340 */       match(36); if (this.state.failed) return;
/* 1341 */       int ID6Start662 = getCharIndex();
/* 1342 */       mID(); if (this.state.failed) return;
/* 1343 */       ID6 = new org.antlr.runtime.CommonToken(this.input, 0, 0, ID6Start662, getCharIndex() - 1);
/* 1344 */       if (resolveDynamicScope(ID6 != null ? ID6.getText() : null) == null) {
/* 1345 */         if (this.state.backtracking > 0) { this.state.failed = true; return; }
/* 1346 */         throw new FailedPredicateException(this.input, "ISOLATED_DYNAMIC_SCOPE", "resolveDynamicScope($ID.text)!=null");
/*      */       }
/* 1348 */       if (this.state.backtracking == 1)
/*      */       {
/* 1350 */         StringTemplate st = template("isolatedDynamicScopeRef");
/* 1351 */         st.setAttribute("scope", ID6 != null ? ID6.getText() : null);
/*      */       }
/*      */ 
/* 1357 */       this.state.type = _type;
/* 1358 */       this.state.channel = _channel;
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public final void mTEMPLATE_INSTANCE() throws RecognitionException
/*      */   {
/*      */     try {
/* 1368 */       int _type = 26;
/* 1369 */       int _channel = 0;
/*      */ 
/* 1373 */       match(37); if (this.state.failed) return;
/* 1374 */       mID(); if (this.state.failed) return;
/* 1375 */       match(40); if (this.state.failed) return;
/*      */ 
/* 1377 */       int alt11 = 2;
/* 1378 */       switch (this.input.LA(1))
/*      */       {
/*      */       case 9:
/*      */       case 10:
/*      */       case 13:
/*      */       case 32:
/*      */       case 65:
/*      */       case 66:
/*      */       case 67:
/*      */       case 68:
/*      */       case 69:
/*      */       case 70:
/*      */       case 71:
/*      */       case 72:
/*      */       case 73:
/*      */       case 74:
/*      */       case 75:
/*      */       case 76:
/*      */       case 77:
/*      */       case 78:
/*      */       case 79:
/*      */       case 80:
/*      */       case 81:
/*      */       case 82:
/*      */       case 83:
/*      */       case 84:
/*      */       case 85:
/*      */       case 86:
/*      */       case 87:
/*      */       case 88:
/*      */       case 89:
/*      */       case 90:
/*      */       case 95:
/*      */       case 97:
/*      */       case 98:
/*      */       case 99:
/*      */       case 100:
/*      */       case 101:
/*      */       case 102:
/*      */       case 103:
/*      */       case 104:
/*      */       case 105:
/*      */       case 106:
/*      */       case 107:
/*      */       case 108:
/*      */       case 109:
/*      */       case 110:
/*      */       case 111:
/*      */       case 112:
/*      */       case 113:
/*      */       case 114:
/*      */       case 115:
/*      */       case 116:
/*      */       case 117:
/*      */       case 118:
/*      */       case 119:
/*      */       case 120:
/*      */       case 121:
/*      */       case 122:
/* 1437 */         alt11 = 1;
/*      */       case 11:
/*      */       case 12:
/*      */       case 14:
/*      */       case 15:
/*      */       case 16:
/*      */       case 17:
/*      */       case 18:
/*      */       case 19:
/*      */       case 20:
/*      */       case 21:
/*      */       case 22:
/*      */       case 23:
/*      */       case 24:
/*      */       case 25:
/*      */       case 26:
/*      */       case 27:
/*      */       case 28:
/*      */       case 29:
/*      */       case 30:
/*      */       case 31:
/*      */       case 33:
/*      */       case 34:
/*      */       case 35:
/*      */       case 36:
/*      */       case 37:
/*      */       case 38:
/*      */       case 39:
/*      */       case 40:
/*      */       case 41:
/*      */       case 42:
/*      */       case 43:
/*      */       case 44:
/*      */       case 45:
/*      */       case 46:
/*      */       case 47:
/*      */       case 48:
/*      */       case 49:
/*      */       case 50:
/*      */       case 51:
/*      */       case 52:
/*      */       case 53:
/*      */       case 54:
/*      */       case 55:
/*      */       case 56:
/*      */       case 57:
/*      */       case 58:
/*      */       case 59:
/*      */       case 60:
/*      */       case 61:
/*      */       case 62:
/*      */       case 63:
/*      */       case 64:
/*      */       case 91:
/*      */       case 92:
/*      */       case 93:
/*      */       case 94:
/* 1442 */       case 96: } switch (alt11)
/*      */       {
/*      */       case 1:
/* 1447 */         int alt7 = 2;
/* 1448 */         switch (this.input.LA(1))
/*      */         {
/*      */         case 9:
/*      */         case 10:
/*      */         case 13:
/*      */         case 32:
/* 1454 */           alt7 = 1;
/*      */         }
/*      */ 
/* 1459 */         switch (alt7)
/*      */         {
/*      */         case 1:
/* 1463 */           mWS(); if (this.state.failed)
/*      */           {
/*      */             return;
/*      */           }
/*      */           break;
/*      */         }
/*      */ 
/* 1470 */         mARG(); if (this.state.failed)
/*      */           return;
/*      */         while (true)
/*      */         {
/* 1474 */           int alt9 = 2;
/* 1475 */           switch (this.input.LA(1))
/*      */           {
/*      */           case 44:
/* 1478 */             alt9 = 1;
/*      */           }
/*      */ 
/* 1484 */           switch (alt9)
/*      */           {
/*      */           case 1:
/* 1488 */             match(44); if (this.state.failed) return;
/*      */ 
/* 1490 */             int alt8 = 2;
/* 1491 */             switch (this.input.LA(1))
/*      */             {
/*      */             case 9:
/*      */             case 10:
/*      */             case 13:
/*      */             case 32:
/* 1497 */               alt8 = 1;
/*      */             }
/*      */ 
/* 1502 */             switch (alt8)
/*      */             {
/*      */             case 1:
/* 1506 */               mWS(); if (this.state.failed)
/*      */               {
/*      */                 return;
/*      */               }
/*      */               break;
/*      */             }
/*      */ 
/* 1513 */             mARG(); if (this.state.failed) return;
/*      */ 
/* 1516 */             break;
/*      */           default:
/* 1519 */             break label855;
/*      */           }
/*      */ 
/*      */         }
/*      */ 
/* 1524 */         label855: int alt10 = 2;
/* 1525 */         switch (this.input.LA(1))
/*      */         {
/*      */         case 9:
/*      */         case 10:
/*      */         case 13:
/*      */         case 32:
/* 1531 */           alt10 = 1;
/*      */         }
/*      */ 
/* 1536 */         switch (alt10)
/*      */         {
/*      */         case 1:
/* 1540 */           mWS(); if (this.state.failed)
/*      */           {
/*      */             return;
/*      */           }
/*      */ 
/*      */           break;
/*      */         }
/*      */ 
/*      */         break;
/*      */       }
/*      */ 
/* 1553 */       match(41); if (this.state.failed) return;
/* 1554 */       if (this.state.backtracking == 1)
/*      */       {
/* 1556 */         String action = getText().substring(1, getText().length());
/* 1557 */         String ruleName = "<outside-of-rule>";
/* 1558 */         if (this.enclosingRule != null) {
/* 1559 */           ruleName = this.enclosingRule.name;
/*      */         }
/* 1561 */         StringTemplate st = this.generator.translateTemplateConstructor(ruleName, this.outerAltNum, this.actionToken, action);
/*      */ 
/* 1566 */         if (st != null) {
/* 1567 */           this.chunks.add(st);
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/* 1574 */       this.state.type = _type;
/* 1575 */       this.state.channel = _channel;
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public final void mINDIRECT_TEMPLATE_INSTANCE() throws RecognitionException
/*      */   {
/*      */     try {
/* 1585 */       int _type = 28;
/* 1586 */       int _channel = 0;
/*      */ 
/* 1590 */       match(37); if (this.state.failed) return;
/* 1591 */       match(40); if (this.state.failed) return;
/* 1592 */       mACTION(); if (this.state.failed) return;
/* 1593 */       match(41); if (this.state.failed) return;
/* 1594 */       match(40); if (this.state.failed) return;
/*      */ 
/* 1596 */       int alt16 = 2;
/* 1597 */       switch (this.input.LA(1))
/*      */       {
/*      */       case 9:
/*      */       case 10:
/*      */       case 13:
/*      */       case 32:
/*      */       case 65:
/*      */       case 66:
/*      */       case 67:
/*      */       case 68:
/*      */       case 69:
/*      */       case 70:
/*      */       case 71:
/*      */       case 72:
/*      */       case 73:
/*      */       case 74:
/*      */       case 75:
/*      */       case 76:
/*      */       case 77:
/*      */       case 78:
/*      */       case 79:
/*      */       case 80:
/*      */       case 81:
/*      */       case 82:
/*      */       case 83:
/*      */       case 84:
/*      */       case 85:
/*      */       case 86:
/*      */       case 87:
/*      */       case 88:
/*      */       case 89:
/*      */       case 90:
/*      */       case 95:
/*      */       case 97:
/*      */       case 98:
/*      */       case 99:
/*      */       case 100:
/*      */       case 101:
/*      */       case 102:
/*      */       case 103:
/*      */       case 104:
/*      */       case 105:
/*      */       case 106:
/*      */       case 107:
/*      */       case 108:
/*      */       case 109:
/*      */       case 110:
/*      */       case 111:
/*      */       case 112:
/*      */       case 113:
/*      */       case 114:
/*      */       case 115:
/*      */       case 116:
/*      */       case 117:
/*      */       case 118:
/*      */       case 119:
/*      */       case 120:
/*      */       case 121:
/*      */       case 122:
/* 1656 */         alt16 = 1;
/*      */       case 11:
/*      */       case 12:
/*      */       case 14:
/*      */       case 15:
/*      */       case 16:
/*      */       case 17:
/*      */       case 18:
/*      */       case 19:
/*      */       case 20:
/*      */       case 21:
/*      */       case 22:
/*      */       case 23:
/*      */       case 24:
/*      */       case 25:
/*      */       case 26:
/*      */       case 27:
/*      */       case 28:
/*      */       case 29:
/*      */       case 30:
/*      */       case 31:
/*      */       case 33:
/*      */       case 34:
/*      */       case 35:
/*      */       case 36:
/*      */       case 37:
/*      */       case 38:
/*      */       case 39:
/*      */       case 40:
/*      */       case 41:
/*      */       case 42:
/*      */       case 43:
/*      */       case 44:
/*      */       case 45:
/*      */       case 46:
/*      */       case 47:
/*      */       case 48:
/*      */       case 49:
/*      */       case 50:
/*      */       case 51:
/*      */       case 52:
/*      */       case 53:
/*      */       case 54:
/*      */       case 55:
/*      */       case 56:
/*      */       case 57:
/*      */       case 58:
/*      */       case 59:
/*      */       case 60:
/*      */       case 61:
/*      */       case 62:
/*      */       case 63:
/*      */       case 64:
/*      */       case 91:
/*      */       case 92:
/*      */       case 93:
/*      */       case 94:
/* 1661 */       case 96: } switch (alt16)
/*      */       {
/*      */       case 1:
/* 1666 */         int alt12 = 2;
/* 1667 */         switch (this.input.LA(1))
/*      */         {
/*      */         case 9:
/*      */         case 10:
/*      */         case 13:
/*      */         case 32:
/* 1673 */           alt12 = 1;
/*      */         }
/*      */ 
/* 1678 */         switch (alt12)
/*      */         {
/*      */         case 1:
/* 1682 */           mWS(); if (this.state.failed)
/*      */           {
/*      */             return;
/*      */           }
/*      */           break;
/*      */         }
/*      */ 
/* 1689 */         mARG(); if (this.state.failed)
/*      */           return;
/*      */         while (true)
/*      */         {
/* 1693 */           int alt14 = 2;
/* 1694 */           switch (this.input.LA(1))
/*      */           {
/*      */           case 44:
/* 1697 */             alt14 = 1;
/*      */           }
/*      */ 
/* 1703 */           switch (alt14)
/*      */           {
/*      */           case 1:
/* 1707 */             match(44); if (this.state.failed) return;
/*      */ 
/* 1709 */             int alt13 = 2;
/* 1710 */             switch (this.input.LA(1))
/*      */             {
/*      */             case 9:
/*      */             case 10:
/*      */             case 13:
/*      */             case 32:
/* 1716 */               alt13 = 1;
/*      */             }
/*      */ 
/* 1721 */             switch (alt13)
/*      */             {
/*      */             case 1:
/* 1725 */               mWS(); if (this.state.failed)
/*      */               {
/*      */                 return;
/*      */               }
/*      */               break;
/*      */             }
/*      */ 
/* 1732 */             mARG(); if (this.state.failed) return;
/*      */ 
/* 1735 */             break;
/*      */           default:
/* 1738 */             break label891;
/*      */           }
/*      */ 
/*      */         }
/*      */ 
/* 1743 */         label891: int alt15 = 2;
/* 1744 */         switch (this.input.LA(1))
/*      */         {
/*      */         case 9:
/*      */         case 10:
/*      */         case 13:
/*      */         case 32:
/* 1750 */           alt15 = 1;
/*      */         }
/*      */ 
/* 1755 */         switch (alt15)
/*      */         {
/*      */         case 1:
/* 1759 */           mWS(); if (this.state.failed)
/*      */           {
/*      */             return;
/*      */           }
/*      */ 
/*      */           break;
/*      */         }
/*      */ 
/*      */         break;
/*      */       }
/*      */ 
/* 1772 */       match(41); if (this.state.failed) return;
/* 1773 */       if (this.state.backtracking == 1)
/*      */       {
/* 1775 */         String action = getText().substring(1, getText().length());
/* 1776 */         StringTemplate st = this.generator.translateTemplateConstructor(this.enclosingRule.name, this.outerAltNum, this.actionToken, action);
/*      */ 
/* 1781 */         this.chunks.add(st);
/*      */       }
/*      */ 
/* 1787 */       this.state.type = _type;
/* 1788 */       this.state.channel = _channel;
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public final void mARG()
/*      */     throws RecognitionException
/*      */   {
/*      */     try
/*      */     {
/* 1801 */       mID(); if (this.state.failed) return;
/* 1802 */       match(61); if (this.state.failed) return;
/* 1803 */       mACTION(); if (this.state.failed) return;
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public final void mSET_EXPR_ATTRIBUTE()
/*      */     throws RecognitionException
/*      */   {
/*      */     try
/*      */     {
/* 1816 */       int _type = 29;
/* 1817 */       int _channel = 0;
/* 1818 */       org.antlr.runtime.CommonToken a = null;
/* 1819 */       org.antlr.runtime.CommonToken expr = null;
/* 1820 */       org.antlr.runtime.CommonToken ID7 = null;
/*      */ 
/* 1825 */       match(37); if (this.state.failed) return;
/* 1826 */       int aStart812 = getCharIndex();
/* 1827 */       mACTION(); if (this.state.failed) return;
/* 1828 */       a = new org.antlr.runtime.CommonToken(this.input, 0, 0, aStart812, getCharIndex() - 1);
/* 1829 */       match(46); if (this.state.failed) return;
/* 1830 */       int ID7Start816 = getCharIndex();
/* 1831 */       mID(); if (this.state.failed) return;
/* 1832 */       ID7 = new org.antlr.runtime.CommonToken(this.input, 0, 0, ID7Start816, getCharIndex() - 1);
/*      */ 
/* 1834 */       int alt17 = 2;
/* 1835 */       switch (this.input.LA(1))
/*      */       {
/*      */       case 9:
/*      */       case 10:
/*      */       case 13:
/*      */       case 32:
/* 1841 */         alt17 = 1;
/*      */       }
/*      */ 
/* 1846 */       switch (alt17)
/*      */       {
/*      */       case 1:
/* 1850 */         mWS(); if (this.state.failed)
/*      */         {
/*      */           return;
/*      */         }
/*      */         break;
/*      */       }
/*      */ 
/* 1857 */       match(61); if (this.state.failed) return;
/* 1858 */       int exprStart825 = getCharIndex();
/* 1859 */       mATTR_VALUE_EXPR(); if (this.state.failed) return;
/* 1860 */       expr = new org.antlr.runtime.CommonToken(this.input, 0, 0, exprStart825, getCharIndex() - 1);
/* 1861 */       match(59); if (this.state.failed) return;
/* 1862 */       if (this.state.backtracking == 1)
/*      */       {
/* 1864 */         StringTemplate st = template("actionSetAttribute");
/* 1865 */         String action = a != null ? a.getText() : null;
/* 1866 */         action = action.substring(1, action.length() - 1);
/* 1867 */         st.setAttribute("st", translateAction(action));
/* 1868 */         st.setAttribute("attrName", ID7 != null ? ID7.getText() : null);
/* 1869 */         st.setAttribute("expr", translateAction(expr != null ? expr.getText() : null));
/*      */       }
/*      */ 
/* 1875 */       this.state.type = _type;
/* 1876 */       this.state.channel = _channel;
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public final void mSET_ATTRIBUTE() throws RecognitionException
/*      */   {
/*      */     try {
/* 1886 */       int _type = 30;
/* 1887 */       int _channel = 0;
/* 1888 */       org.antlr.runtime.CommonToken x = null;
/* 1889 */       org.antlr.runtime.CommonToken y = null;
/* 1890 */       org.antlr.runtime.CommonToken expr = null;
/*      */ 
/* 1895 */       match(37); if (this.state.failed) return;
/* 1896 */       int xStart852 = getCharIndex();
/* 1897 */       mID(); if (this.state.failed) return;
/* 1898 */       x = new org.antlr.runtime.CommonToken(this.input, 0, 0, xStart852, getCharIndex() - 1);
/* 1899 */       match(46); if (this.state.failed) return;
/* 1900 */       int yStart858 = getCharIndex();
/* 1901 */       mID(); if (this.state.failed) return;
/* 1902 */       y = new org.antlr.runtime.CommonToken(this.input, 0, 0, yStart858, getCharIndex() - 1);
/*      */ 
/* 1904 */       int alt18 = 2;
/* 1905 */       switch (this.input.LA(1))
/*      */       {
/*      */       case 9:
/*      */       case 10:
/*      */       case 13:
/*      */       case 32:
/* 1911 */         alt18 = 1;
/*      */       }
/*      */ 
/* 1916 */       switch (alt18)
/*      */       {
/*      */       case 1:
/* 1920 */         mWS(); if (this.state.failed)
/*      */         {
/*      */           return;
/*      */         }
/*      */         break;
/*      */       }
/*      */ 
/* 1927 */       match(61); if (this.state.failed) return;
/* 1928 */       int exprStart867 = getCharIndex();
/* 1929 */       mATTR_VALUE_EXPR(); if (this.state.failed) return;
/* 1930 */       expr = new org.antlr.runtime.CommonToken(this.input, 0, 0, exprStart867, getCharIndex() - 1);
/* 1931 */       match(59); if (this.state.failed) return;
/* 1932 */       if (this.state.backtracking == 1)
/*      */       {
/* 1934 */         StringTemplate st = template("actionSetAttribute");
/* 1935 */         st.setAttribute("st", x != null ? x.getText() : null);
/* 1936 */         st.setAttribute("attrName", y != null ? y.getText() : null);
/* 1937 */         st.setAttribute("expr", translateAction(expr != null ? expr.getText() : null));
/*      */       }
/*      */ 
/* 1943 */       this.state.type = _type;
/* 1944 */       this.state.channel = _channel;
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public final void mATTR_VALUE_EXPR()
/*      */     throws RecognitionException
/*      */   {
/*      */     try
/*      */     {
/* 1957 */       if (((this.input.LA(1) >= 0) && (this.input.LA(1) <= 60)) || ((this.input.LA(1) >= 62) && (this.input.LA(1) <= 65535))) {
/* 1958 */         this.input.consume();
/* 1959 */         this.state.failed = false;
/*      */       }
/*      */       else {
/* 1962 */         if (this.state.backtracking > 0) { this.state.failed = true; return; }
/* 1963 */         MismatchedSetException mse = new MismatchedSetException(null, this.input);
/* 1964 */         recover(mse);
/* 1965 */         throw mse;
/*      */       }
/*      */ 
/*      */       while (true)
/*      */       {
/* 1970 */         int alt19 = 2;
/* 1971 */         int LA19_0 = this.input.LA(1);
/*      */ 
/* 1973 */         if (((LA19_0 >= 0) && (LA19_0 <= 58)) || ((LA19_0 >= 60) && (LA19_0 <= 65535))) {
/* 1974 */           alt19 = 1;
/*      */         }
/*      */ 
/* 1978 */         switch (alt19)
/*      */         {
/*      */         case 1:
/* 1982 */           if (((this.input.LA(1) >= 0) && (this.input.LA(1) <= 58)) || ((this.input.LA(1) >= 60) && (this.input.LA(1) <= 65535))) {
/* 1983 */             this.input.consume();
/* 1984 */             this.state.failed = false;
/*      */           }
/*      */           else {
/* 1987 */             if (this.state.backtracking > 0) { this.state.failed = true; return; }
/* 1988 */             MismatchedSetException mse = new MismatchedSetException(null, this.input);
/* 1989 */             recover(mse);
/* 1990 */             throw mse;
/*      */           }
/*      */ 
/*      */           break;
/*      */         default:
/* 1997 */           return;
/*      */         }
/*      */       }
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public final void mTEMPLATE_EXPR()
/*      */     throws RecognitionException
/*      */   {
/*      */     try
/*      */     {
/* 2013 */       int _type = 31;
/* 2014 */       int _channel = 0;
/* 2015 */       org.antlr.runtime.CommonToken a = null;
/*      */ 
/* 2020 */       match(37); if (this.state.failed) return;
/* 2021 */       int aStart916 = getCharIndex();
/* 2022 */       mACTION(); if (this.state.failed) return;
/* 2023 */       a = new org.antlr.runtime.CommonToken(this.input, 0, 0, aStart916, getCharIndex() - 1);
/* 2024 */       if (this.state.backtracking == 1)
/*      */       {
/* 2026 */         StringTemplate st = template("actionStringConstructor");
/* 2027 */         String action = a != null ? a.getText() : null;
/* 2028 */         action = action.substring(1, action.length() - 1);
/* 2029 */         st.setAttribute("stringExpr", translateAction(action));
/*      */       }
/*      */ 
/* 2035 */       this.state.type = _type;
/* 2036 */       this.state.channel = _channel;
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public final void mACTION()
/*      */     throws RecognitionException
/*      */   {
/*      */     try
/*      */     {
/* 2049 */       match(123); if (this.state.failed)
/*      */         return;
/*      */       while (true)
/*      */       {
/* 2053 */         int alt20 = 2;
/* 2054 */         int LA20_0 = this.input.LA(1);
/*      */ 
/* 2056 */         if (LA20_0 == 125) {
/* 2057 */           alt20 = 2;
/*      */         }
/* 2059 */         else if (((LA20_0 >= 0) && (LA20_0 <= 124)) || ((LA20_0 >= 126) && (LA20_0 <= 65535))) {
/* 2060 */           alt20 = 1;
/*      */         }
/*      */ 
/* 2064 */         switch (alt20)
/*      */         {
/*      */         case 1:
/* 2068 */           matchAny(); if (this.state.failed)
/*      */           {
/*      */             return;
/*      */           }
/*      */           break;
/*      */         default:
/* 2074 */           break label105;
/*      */         }
/*      */       }
/*      */ 
/* 2078 */       label105: match(125); if (this.state.failed) return;
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public final void mESC()
/*      */     throws RecognitionException
/*      */   {
/*      */     try
/*      */     {
/* 2091 */       int _type = 32;
/* 2092 */       int _channel = 0;
/*      */ 
/* 2094 */       int alt21 = 3;
/* 2095 */       switch (this.input.LA(1))
/*      */       {
/*      */       case 92:
/* 2098 */         int LA21_1 = this.input.LA(2);
/*      */ 
/* 2100 */         if (LA21_1 == 36) {
/* 2101 */           alt21 = 1;
/*      */         }
/* 2103 */         else if (LA21_1 == 37) {
/* 2104 */           alt21 = 2;
/*      */         }
/* 2106 */         else if (((LA21_1 >= 0) && (LA21_1 <= 35)) || ((LA21_1 >= 38) && (LA21_1 <= 65535))) {
/* 2107 */           alt21 = 3;
/*      */         }
/*      */         else {
/* 2110 */           if (this.state.backtracking > 0) { this.state.failed = true; return; }
/* 2111 */           NoViableAltException nvae = new NoViableAltException("", 21, 1, this.input);
/*      */ 
/* 2114 */           throw nvae;
/*      */         }
/*      */ 
/* 2117 */         break;
/*      */       default:
/* 2119 */         if (this.state.backtracking > 0) { this.state.failed = true; return; }
/* 2120 */         NoViableAltException nvae = new NoViableAltException("", 21, 0, this.input);
/*      */ 
/* 2123 */         throw nvae;
/*      */       }
/*      */ 
/* 2126 */       switch (alt21)
/*      */       {
/*      */       case 1:
/* 2130 */         match(92); if (this.state.failed) return;
/* 2131 */         match(36); if (this.state.failed) return;
/* 2132 */         if (this.state.backtracking == 1)
/* 2133 */           this.chunks.add("$"); break;
/*      */       case 2:
/* 2141 */         match(92); if (this.state.failed) return;
/* 2142 */         match(37); if (this.state.failed) return;
/* 2143 */         if (this.state.backtracking == 1)
/* 2144 */           this.chunks.add("%"); break;
/*      */       case 3:
/* 2152 */         match(92); if (this.state.failed) return;
/* 2153 */         if (((this.input.LA(1) >= 0) && (this.input.LA(1) <= 35)) || ((this.input.LA(1) >= 38) && (this.input.LA(1) <= 65535))) {
/* 2154 */           this.input.consume();
/* 2155 */           this.state.failed = false;
/*      */         }
/*      */         else {
/* 2158 */           if (this.state.backtracking > 0) { this.state.failed = true; return; }
/* 2159 */           MismatchedSetException mse = new MismatchedSetException(null, this.input);
/* 2160 */           recover(mse);
/* 2161 */           throw mse;
/*      */         }
/* 2163 */         if (this.state.backtracking == 1) {
/* 2164 */           this.chunks.add(getText());
/*      */         }
/*      */ 
/*      */         break;
/*      */       }
/*      */ 
/* 2171 */       this.state.type = _type;
/* 2172 */       this.state.channel = _channel;
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public final void mERROR_XY() throws RecognitionException
/*      */   {
/*      */     try {
/* 2182 */       int _type = 33;
/* 2183 */       int _channel = 0;
/* 2184 */       org.antlr.runtime.CommonToken x = null;
/* 2185 */       org.antlr.runtime.CommonToken y = null;
/*      */ 
/* 2190 */       match(36); if (this.state.failed) return;
/* 2191 */       int xStart1016 = getCharIndex();
/* 2192 */       mID(); if (this.state.failed) return;
/* 2193 */       x = new org.antlr.runtime.CommonToken(this.input, 0, 0, xStart1016, getCharIndex() - 1);
/* 2194 */       match(46); if (this.state.failed) return;
/* 2195 */       int yStart1022 = getCharIndex();
/* 2196 */       mID(); if (this.state.failed) return;
/* 2197 */       y = new org.antlr.runtime.CommonToken(this.input, 0, 0, yStart1022, getCharIndex() - 1);
/* 2198 */       if (this.state.backtracking == 1)
/*      */       {
/* 2200 */         this.chunks.add(getText());
/* 2201 */         this.generator.issueInvalidAttributeError(x != null ? x.getText() : null, y != null ? y.getText() : null, this.enclosingRule, this.actionToken, this.outerAltNum);
/*      */       }
/*      */ 
/* 2209 */       this.state.type = _type;
/* 2210 */       this.state.channel = _channel;
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public final void mERROR_X() throws RecognitionException
/*      */   {
/*      */     try {
/* 2220 */       int _type = 34;
/* 2221 */       int _channel = 0;
/* 2222 */       org.antlr.runtime.CommonToken x = null;
/*      */ 
/* 2227 */       match(36); if (this.state.failed) return;
/* 2228 */       int xStart1042 = getCharIndex();
/* 2229 */       mID(); if (this.state.failed) return;
/* 2230 */       x = new org.antlr.runtime.CommonToken(this.input, 0, 0, xStart1042, getCharIndex() - 1);
/* 2231 */       if (this.state.backtracking == 1)
/*      */       {
/* 2233 */         this.chunks.add(getText());
/* 2234 */         this.generator.issueInvalidAttributeError(x != null ? x.getText() : null, this.enclosingRule, this.actionToken, this.outerAltNum);
/*      */       }
/*      */ 
/* 2242 */       this.state.type = _type;
/* 2243 */       this.state.channel = _channel;
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public final void mUNKNOWN_SYNTAX() throws RecognitionException
/*      */   {
/*      */     try {
/* 2253 */       int _type = 35;
/* 2254 */       int _channel = 0;
/*      */ 
/* 2256 */       int alt23 = 2;
/* 2257 */       switch (this.input.LA(1))
/*      */       {
/*      */       case 36:
/* 2260 */         alt23 = 1;
/*      */ 
/* 2262 */         break;
/*      */       case 37:
/* 2265 */         alt23 = 2;
/*      */ 
/* 2267 */         break;
/*      */       default:
/* 2269 */         if (this.state.backtracking > 0) { this.state.failed = true; return; }
/* 2270 */         NoViableAltException nvae = new NoViableAltException("", 23, 0, this.input);
/*      */ 
/* 2273 */         throw nvae;
/*      */       }
/*      */ 
/* 2276 */       switch (alt23)
/*      */       {
/*      */       case 1:
/* 2280 */         match(36); if (this.state.failed) return;
/* 2281 */         if (this.state.backtracking == 1)
/*      */         {
/* 2283 */           this.chunks.add(getText()); } break;
/*      */       case 2:
/* 2293 */         match(37); if (this.state.failed)
/*      */           return;
/*      */         while (true)
/*      */         {
/* 2297 */           int alt22 = 9;
/* 2298 */           alt22 = this.dfa22.predict(this.input);
/* 2299 */           switch (alt22)
/*      */           {
/*      */           case 1:
/* 2303 */             mID(); if (this.state.failed)
/*      */             {
/*      */               return;
/*      */             }
/*      */ 
/*      */             break;
/*      */           case 2:
/* 2310 */             match(46); if (this.state.failed)
/*      */             {
/*      */               return;
/*      */             }
/*      */ 
/*      */             break;
/*      */           case 3:
/* 2317 */             match(40); if (this.state.failed)
/*      */             {
/*      */               return;
/*      */             }
/*      */ 
/*      */             break;
/*      */           case 4:
/* 2324 */             match(41); if (this.state.failed)
/*      */             {
/*      */               return;
/*      */             }
/*      */ 
/*      */             break;
/*      */           case 5:
/* 2331 */             match(44); if (this.state.failed)
/*      */             {
/*      */               return;
/*      */             }
/*      */ 
/*      */             break;
/*      */           case 6:
/* 2338 */             match(123); if (this.state.failed)
/*      */             {
/*      */               return;
/*      */             }
/*      */ 
/*      */             break;
/*      */           case 7:
/* 2345 */             match(125); if (this.state.failed)
/*      */             {
/*      */               return;
/*      */             }
/*      */ 
/*      */             break;
/*      */           case 8:
/* 2352 */             match(34); if (this.state.failed)
/*      */             {
/*      */               return;
/*      */             }
/*      */             break;
/*      */           default:
/* 2358 */             break label388;
/*      */           }
/*      */         }
/*      */ 
/* 2362 */         label388: if (this.state.backtracking == 1)
/*      */         {
/* 2364 */           this.chunks.add(getText());
/* 2365 */           ErrorManager.grammarError(146, this.grammar, this.actionToken, getText());
/*      */         }
/*      */ 
/*      */         break;
/*      */       }
/*      */ 
/* 2376 */       this.state.type = _type;
/* 2377 */       this.state.channel = _channel;
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public final void mTEXT() throws RecognitionException
/*      */   {
/*      */     try {
/* 2387 */       int _type = 36;
/* 2388 */       int _channel = 0;
/*      */ 
/* 2393 */       int cnt24 = 0;
/*      */       while (true)
/*      */       {
/* 2396 */         int alt24 = 2;
/* 2397 */         int LA24_0 = this.input.LA(1);
/*      */ 
/* 2399 */         if (((LA24_0 >= 0) && (LA24_0 <= 35)) || ((LA24_0 >= 38) && (LA24_0 <= 91)) || ((LA24_0 >= 93) && (LA24_0 <= 65535))) {
/* 2400 */           alt24 = 1;
/*      */         }
/*      */ 
/* 2404 */         switch (alt24)
/*      */         {
/*      */         case 1:
/* 2408 */           if (((this.input.LA(1) >= 0) && (this.input.LA(1) <= 35)) || ((this.input.LA(1) >= 38) && (this.input.LA(1) <= 91)) || ((this.input.LA(1) >= 93) && (this.input.LA(1) <= 65535))) {
/* 2409 */             this.input.consume();
/* 2410 */             this.state.failed = false;
/*      */           }
/*      */           else {
/* 2413 */             if (this.state.backtracking > 0) { this.state.failed = true; return; }
/* 2414 */             MismatchedSetException mse = new MismatchedSetException(null, this.input);
/* 2415 */             recover(mse);
/* 2416 */             throw mse;
/*      */           }
/*      */ 
/*      */           break;
/*      */         default:
/* 2423 */           if (cnt24 >= 1) break label285;
/* 2424 */           if (this.state.backtracking > 0) { this.state.failed = true; return; }
/* 2425 */           EarlyExitException eee = new EarlyExitException(24, this.input);
/*      */ 
/* 2427 */           throw eee;
/*      */         }
/* 2429 */         cnt24++;
/*      */       }
/*      */ 
/* 2432 */       label285: if (this.state.backtracking == 1) {
/* 2433 */         this.chunks.add(getText());
/*      */       }
/*      */ 
/* 2438 */       this.state.type = _type;
/* 2439 */       this.state.channel = _channel;
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public final void mID()
/*      */     throws RecognitionException
/*      */   {
/*      */     try
/*      */     {
/* 2452 */       if (((this.input.LA(1) >= 65) && (this.input.LA(1) <= 90)) || (this.input.LA(1) == 95) || ((this.input.LA(1) >= 97) && (this.input.LA(1) <= 122))) {
/* 2453 */         this.input.consume();
/* 2454 */         this.state.failed = false;
/*      */       }
/*      */       else {
/* 2457 */         if (this.state.backtracking > 0) { this.state.failed = true; return; }
/* 2458 */         MismatchedSetException mse = new MismatchedSetException(null, this.input);
/* 2459 */         recover(mse);
/* 2460 */         throw mse;
/*      */       }
/*      */ 
/*      */       while (true)
/*      */       {
/* 2465 */         int alt25 = 2;
/* 2466 */         switch (this.input.LA(1))
/*      */         {
/*      */         case 48:
/*      */         case 49:
/*      */         case 50:
/*      */         case 51:
/*      */         case 52:
/*      */         case 53:
/*      */         case 54:
/*      */         case 55:
/*      */         case 56:
/*      */         case 57:
/*      */         case 65:
/*      */         case 66:
/*      */         case 67:
/*      */         case 68:
/*      */         case 69:
/*      */         case 70:
/*      */         case 71:
/*      */         case 72:
/*      */         case 73:
/*      */         case 74:
/*      */         case 75:
/*      */         case 76:
/*      */         case 77:
/*      */         case 78:
/*      */         case 79:
/*      */         case 80:
/*      */         case 81:
/*      */         case 82:
/*      */         case 83:
/*      */         case 84:
/*      */         case 85:
/*      */         case 86:
/*      */         case 87:
/*      */         case 88:
/*      */         case 89:
/*      */         case 90:
/*      */         case 95:
/*      */         case 97:
/*      */         case 98:
/*      */         case 99:
/*      */         case 100:
/*      */         case 101:
/*      */         case 102:
/*      */         case 103:
/*      */         case 104:
/*      */         case 105:
/*      */         case 106:
/*      */         case 107:
/*      */         case 108:
/*      */         case 109:
/*      */         case 110:
/*      */         case 111:
/*      */         case 112:
/*      */         case 113:
/*      */         case 114:
/*      */         case 115:
/*      */         case 116:
/*      */         case 117:
/*      */         case 118:
/*      */         case 119:
/*      */         case 120:
/*      */         case 121:
/*      */         case 122:
/* 2531 */           alt25 = 1;
/*      */         case 58:
/*      */         case 59:
/*      */         case 60:
/*      */         case 61:
/*      */         case 62:
/*      */         case 63:
/*      */         case 64:
/*      */         case 91:
/*      */         case 92:
/*      */         case 93:
/*      */         case 94:
/* 2537 */         case 96: } switch (alt25)
/*      */         {
/*      */         case 1:
/* 2541 */           if (((this.input.LA(1) >= 48) && (this.input.LA(1) <= 57)) || ((this.input.LA(1) >= 65) && (this.input.LA(1) <= 90)) || (this.input.LA(1) == 95) || ((this.input.LA(1) >= 97) && (this.input.LA(1) <= 122))) {
/* 2542 */             this.input.consume();
/* 2543 */             this.state.failed = false;
/*      */           }
/*      */           else {
/* 2546 */             if (this.state.backtracking > 0) { this.state.failed = true; return; }
/* 2547 */             MismatchedSetException mse = new MismatchedSetException(null, this.input);
/* 2548 */             recover(mse);
/* 2549 */             throw mse;
/*      */           }
/*      */ 
/*      */           break;
/*      */         default:
/* 2556 */           return;
/*      */         }
/*      */       }
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public final void mINT()
/*      */     throws RecognitionException
/*      */   {
/*      */     try
/*      */     {
/* 2576 */       int cnt26 = 0;
/*      */       while (true)
/*      */       {
/* 2579 */         int alt26 = 2;
/* 2580 */         switch (this.input.LA(1))
/*      */         {
/*      */         case 48:
/*      */         case 49:
/*      */         case 50:
/*      */         case 51:
/*      */         case 52:
/*      */         case 53:
/*      */         case 54:
/*      */         case 55:
/*      */         case 56:
/*      */         case 57:
/* 2592 */           alt26 = 1;
/*      */         }
/*      */ 
/* 2598 */         switch (alt26)
/*      */         {
/*      */         case 1:
/* 2602 */           matchRange(48, 57); if (this.state.failed)
/*      */           {
/*      */             return;
/*      */           }
/*      */           break;
/*      */         default:
/* 2608 */           if (cnt26 >= 1) return;
/* 2609 */           if (this.state.backtracking > 0) { this.state.failed = true; return; }
/* 2610 */           EarlyExitException eee = new EarlyExitException(26, this.input);
/*      */ 
/* 2612 */           throw eee;
/*      */         }
/* 2614 */         cnt26++;
/*      */       }
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public final void mWS()
/*      */     throws RecognitionException
/*      */   {
/*      */     try
/*      */     {
/* 2633 */       int cnt27 = 0;
/*      */       while (true)
/*      */       {
/* 2636 */         int alt27 = 2;
/* 2637 */         switch (this.input.LA(1))
/*      */         {
/*      */         case 9:
/*      */         case 10:
/*      */         case 13:
/*      */         case 32:
/* 2643 */           alt27 = 1;
/*      */         }
/*      */ 
/* 2649 */         switch (alt27)
/*      */         {
/*      */         case 1:
/* 2653 */           if (((this.input.LA(1) >= 9) && (this.input.LA(1) <= 10)) || (this.input.LA(1) == 13) || (this.input.LA(1) == 32)) {
/* 2654 */             this.input.consume();
/* 2655 */             this.state.failed = false;
/*      */           }
/*      */           else {
/* 2658 */             if (this.state.backtracking > 0) { this.state.failed = true; return; }
/* 2659 */             MismatchedSetException mse = new MismatchedSetException(null, this.input);
/* 2660 */             recover(mse);
/* 2661 */             throw mse;
/*      */           }
/*      */ 
/*      */           break;
/*      */         default:
/* 2668 */           if (cnt27 >= 1) return;
/* 2669 */           if (this.state.backtracking > 0) { this.state.failed = true; return; }
/* 2670 */           EarlyExitException eee = new EarlyExitException(27, this.input);
/*      */ 
/* 2672 */           throw eee;
/*      */         }
/* 2674 */         cnt27++;
/*      */       }
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public void mTokens()
/*      */     throws RecognitionException
/*      */   {
/* 2688 */     int alt28 = 27;
/* 2689 */     alt28 = this.dfa28.predict(this.input);
/* 2690 */     switch (alt28)
/*      */     {
/*      */     case 1:
/* 2694 */       mSET_ENCLOSING_RULE_SCOPE_ATTR(); if (this.state.failed);
/*      */       break;
/*      */     case 2:
/* 2701 */       mENCLOSING_RULE_SCOPE_ATTR(); if (this.state.failed);
/*      */       break;
/*      */     case 3:
/* 2708 */       mSET_TOKEN_SCOPE_ATTR(); if (this.state.failed);
/*      */       break;
/*      */     case 4:
/* 2715 */       mTOKEN_SCOPE_ATTR(); if (this.state.failed);
/*      */       break;
/*      */     case 5:
/* 2722 */       mSET_RULE_SCOPE_ATTR(); if (this.state.failed);
/*      */       break;
/*      */     case 6:
/* 2729 */       mRULE_SCOPE_ATTR(); if (this.state.failed);
/*      */       break;
/*      */     case 7:
/* 2736 */       mLABEL_REF(); if (this.state.failed);
/*      */       break;
/*      */     case 8:
/* 2743 */       mISOLATED_TOKEN_REF(); if (this.state.failed);
/*      */       break;
/*      */     case 9:
/* 2750 */       mISOLATED_LEXER_RULE_REF(); if (this.state.failed);
/*      */       break;
/*      */     case 10:
/* 2757 */       mSET_LOCAL_ATTR(); if (this.state.failed);
/*      */       break;
/*      */     case 11:
/* 2764 */       mLOCAL_ATTR(); if (this.state.failed);
/*      */       break;
/*      */     case 12:
/* 2771 */       mSET_DYNAMIC_SCOPE_ATTR(); if (this.state.failed);
/*      */       break;
/*      */     case 13:
/* 2778 */       mDYNAMIC_SCOPE_ATTR(); if (this.state.failed);
/*      */       break;
/*      */     case 14:
/* 2785 */       mERROR_SCOPED_XY(); if (this.state.failed);
/*      */       break;
/*      */     case 15:
/* 2792 */       mDYNAMIC_NEGATIVE_INDEXED_SCOPE_ATTR(); if (this.state.failed);
/*      */       break;
/*      */     case 16:
/* 2799 */       mDYNAMIC_ABSOLUTE_INDEXED_SCOPE_ATTR(); if (this.state.failed);
/*      */       break;
/*      */     case 17:
/* 2806 */       mISOLATED_DYNAMIC_SCOPE(); if (this.state.failed);
/*      */       break;
/*      */     case 18:
/* 2813 */       mTEMPLATE_INSTANCE(); if (this.state.failed);
/*      */       break;
/*      */     case 19:
/* 2820 */       mINDIRECT_TEMPLATE_INSTANCE(); if (this.state.failed);
/*      */       break;
/*      */     case 20:
/* 2827 */       mSET_EXPR_ATTRIBUTE(); if (this.state.failed);
/*      */       break;
/*      */     case 21:
/* 2834 */       mSET_ATTRIBUTE(); if (this.state.failed);
/*      */       break;
/*      */     case 22:
/* 2841 */       mTEMPLATE_EXPR(); if (this.state.failed);
/*      */       break;
/*      */     case 23:
/* 2848 */       mESC(); if (this.state.failed);
/*      */       break;
/*      */     case 24:
/* 2855 */       mERROR_XY(); if (this.state.failed);
/*      */       break;
/*      */     case 25:
/* 2862 */       mERROR_X(); if (this.state.failed);
/*      */       break;
/*      */     case 26:
/* 2869 */       mUNKNOWN_SYNTAX(); if (this.state.failed);
/*      */       break;
/*      */     case 27:
/* 2876 */       mTEXT(); if (this.state.failed);
/*      */       break;
/*      */     }
/*      */   }
/*      */ 
/*      */   public final void synpred1_ActionTranslator_fragment()
/*      */     throws RecognitionException
/*      */   {
/* 2890 */     mSET_ENCLOSING_RULE_SCOPE_ATTR(); if (this.state.failed);
/*      */   }
/*      */ 
/*      */   public final void synpred2_ActionTranslator_fragment()
/*      */     throws RecognitionException
/*      */   {
/* 2901 */     mENCLOSING_RULE_SCOPE_ATTR(); if (this.state.failed);
/*      */   }
/*      */ 
/*      */   public final void synpred3_ActionTranslator_fragment()
/*      */     throws RecognitionException
/*      */   {
/* 2912 */     mSET_TOKEN_SCOPE_ATTR(); if (this.state.failed);
/*      */   }
/*      */ 
/*      */   public final void synpred4_ActionTranslator_fragment()
/*      */     throws RecognitionException
/*      */   {
/* 2923 */     mTOKEN_SCOPE_ATTR(); if (this.state.failed);
/*      */   }
/*      */ 
/*      */   public final void synpred5_ActionTranslator_fragment()
/*      */     throws RecognitionException
/*      */   {
/* 2934 */     mSET_RULE_SCOPE_ATTR(); if (this.state.failed);
/*      */   }
/*      */ 
/*      */   public final void synpred6_ActionTranslator_fragment()
/*      */     throws RecognitionException
/*      */   {
/* 2945 */     mRULE_SCOPE_ATTR(); if (this.state.failed);
/*      */   }
/*      */ 
/*      */   public final void synpred7_ActionTranslator_fragment()
/*      */     throws RecognitionException
/*      */   {
/* 2956 */     mLABEL_REF(); if (this.state.failed);
/*      */   }
/*      */ 
/*      */   public final void synpred8_ActionTranslator_fragment()
/*      */     throws RecognitionException
/*      */   {
/* 2967 */     mISOLATED_TOKEN_REF(); if (this.state.failed);
/*      */   }
/*      */ 
/*      */   public final void synpred9_ActionTranslator_fragment()
/*      */     throws RecognitionException
/*      */   {
/* 2978 */     mISOLATED_LEXER_RULE_REF(); if (this.state.failed);
/*      */   }
/*      */ 
/*      */   public final void synpred10_ActionTranslator_fragment()
/*      */     throws RecognitionException
/*      */   {
/* 2989 */     mSET_LOCAL_ATTR(); if (this.state.failed);
/*      */   }
/*      */ 
/*      */   public final void synpred11_ActionTranslator_fragment()
/*      */     throws RecognitionException
/*      */   {
/* 3000 */     mLOCAL_ATTR(); if (this.state.failed);
/*      */   }
/*      */ 
/*      */   public final void synpred12_ActionTranslator_fragment()
/*      */     throws RecognitionException
/*      */   {
/* 3011 */     mSET_DYNAMIC_SCOPE_ATTR(); if (this.state.failed);
/*      */   }
/*      */ 
/*      */   public final void synpred13_ActionTranslator_fragment()
/*      */     throws RecognitionException
/*      */   {
/* 3022 */     mDYNAMIC_SCOPE_ATTR(); if (this.state.failed);
/*      */   }
/*      */ 
/*      */   public final void synpred14_ActionTranslator_fragment()
/*      */     throws RecognitionException
/*      */   {
/* 3033 */     mERROR_SCOPED_XY(); if (this.state.failed);
/*      */   }
/*      */ 
/*      */   public final void synpred15_ActionTranslator_fragment()
/*      */     throws RecognitionException
/*      */   {
/* 3044 */     mDYNAMIC_NEGATIVE_INDEXED_SCOPE_ATTR(); if (this.state.failed);
/*      */   }
/*      */ 
/*      */   public final void synpred16_ActionTranslator_fragment()
/*      */     throws RecognitionException
/*      */   {
/* 3055 */     mDYNAMIC_ABSOLUTE_INDEXED_SCOPE_ATTR(); if (this.state.failed);
/*      */   }
/*      */ 
/*      */   public final void synpred17_ActionTranslator_fragment()
/*      */     throws RecognitionException
/*      */   {
/* 3066 */     mISOLATED_DYNAMIC_SCOPE(); if (this.state.failed);
/*      */   }
/*      */ 
/*      */   public final void synpred18_ActionTranslator_fragment()
/*      */     throws RecognitionException
/*      */   {
/* 3077 */     mTEMPLATE_INSTANCE(); if (this.state.failed);
/*      */   }
/*      */ 
/*      */   public final void synpred19_ActionTranslator_fragment()
/*      */     throws RecognitionException
/*      */   {
/* 3088 */     mINDIRECT_TEMPLATE_INSTANCE(); if (this.state.failed);
/*      */   }
/*      */ 
/*      */   public final void synpred20_ActionTranslator_fragment()
/*      */     throws RecognitionException
/*      */   {
/* 3099 */     mSET_EXPR_ATTRIBUTE(); if (this.state.failed);
/*      */   }
/*      */ 
/*      */   public final void synpred21_ActionTranslator_fragment()
/*      */     throws RecognitionException
/*      */   {
/* 3110 */     mSET_ATTRIBUTE(); if (this.state.failed);
/*      */   }
/*      */ 
/*      */   public final void synpred22_ActionTranslator_fragment()
/*      */     throws RecognitionException
/*      */   {
/* 3121 */     mTEMPLATE_EXPR(); if (this.state.failed);
/*      */   }
/*      */ 
/*      */   public final void synpred24_ActionTranslator_fragment()
/*      */     throws RecognitionException
/*      */   {
/* 3132 */     mERROR_XY(); if (this.state.failed);
/*      */   }
/*      */ 
/*      */   public final void synpred25_ActionTranslator_fragment()
/*      */     throws RecognitionException
/*      */   {
/* 3143 */     mERROR_X(); if (this.state.failed);
/*      */   }
/*      */ 
/*      */   public final void synpred26_ActionTranslator_fragment()
/*      */     throws RecognitionException
/*      */   {
/* 3154 */     mUNKNOWN_SYNTAX(); if (this.state.failed);
/*      */   }
/*      */ 
/*      */   public final boolean synpred18_ActionTranslator()
/*      */   {
/* 3161 */     this.state.backtracking += 1;
/* 3162 */     int start = this.input.mark();
/*      */     try {
/* 3164 */       synpred18_ActionTranslator_fragment();
/*      */     } catch (RecognitionException re) {
/* 3166 */       System.err.println("impossible: " + re);
/*      */     }
/* 3168 */     boolean success = !this.state.failed;
/* 3169 */     this.input.rewind(start);
/* 3170 */     this.state.backtracking -= 1;
/* 3171 */     this.state.failed = false;
/* 3172 */     return success;
/*      */   }
/*      */   public final boolean synpred19_ActionTranslator() {
/* 3175 */     this.state.backtracking += 1;
/* 3176 */     int start = this.input.mark();
/*      */     try {
/* 3178 */       synpred19_ActionTranslator_fragment();
/*      */     } catch (RecognitionException re) {
/* 3180 */       System.err.println("impossible: " + re);
/*      */     }
/* 3182 */     boolean success = !this.state.failed;
/* 3183 */     this.input.rewind(start);
/* 3184 */     this.state.backtracking -= 1;
/* 3185 */     this.state.failed = false;
/* 3186 */     return success;
/*      */   }
/*      */   public final boolean synpred16_ActionTranslator() {
/* 3189 */     this.state.backtracking += 1;
/* 3190 */     int start = this.input.mark();
/*      */     try {
/* 3192 */       synpred16_ActionTranslator_fragment();
/*      */     } catch (RecognitionException re) {
/* 3194 */       System.err.println("impossible: " + re);
/*      */     }
/* 3196 */     boolean success = !this.state.failed;
/* 3197 */     this.input.rewind(start);
/* 3198 */     this.state.backtracking -= 1;
/* 3199 */     this.state.failed = false;
/* 3200 */     return success;
/*      */   }
/*      */   public final boolean synpred11_ActionTranslator() {
/* 3203 */     this.state.backtracking += 1;
/* 3204 */     int start = this.input.mark();
/*      */     try {
/* 3206 */       synpred11_ActionTranslator_fragment();
/*      */     } catch (RecognitionException re) {
/* 3208 */       System.err.println("impossible: " + re);
/*      */     }
/* 3210 */     boolean success = !this.state.failed;
/* 3211 */     this.input.rewind(start);
/* 3212 */     this.state.backtracking -= 1;
/* 3213 */     this.state.failed = false;
/* 3214 */     return success;
/*      */   }
/*      */   public final boolean synpred24_ActionTranslator() {
/* 3217 */     this.state.backtracking += 1;
/* 3218 */     int start = this.input.mark();
/*      */     try {
/* 3220 */       synpred24_ActionTranslator_fragment();
/*      */     } catch (RecognitionException re) {
/* 3222 */       System.err.println("impossible: " + re);
/*      */     }
/* 3224 */     boolean success = !this.state.failed;
/* 3225 */     this.input.rewind(start);
/* 3226 */     this.state.backtracking -= 1;
/* 3227 */     this.state.failed = false;
/* 3228 */     return success;
/*      */   }
/*      */   public final boolean synpred12_ActionTranslator() {
/* 3231 */     this.state.backtracking += 1;
/* 3232 */     int start = this.input.mark();
/*      */     try {
/* 3234 */       synpred12_ActionTranslator_fragment();
/*      */     } catch (RecognitionException re) {
/* 3236 */       System.err.println("impossible: " + re);
/*      */     }
/* 3238 */     boolean success = !this.state.failed;
/* 3239 */     this.input.rewind(start);
/* 3240 */     this.state.backtracking -= 1;
/* 3241 */     this.state.failed = false;
/* 3242 */     return success;
/*      */   }
/*      */   public final boolean synpred9_ActionTranslator() {
/* 3245 */     this.state.backtracking += 1;
/* 3246 */     int start = this.input.mark();
/*      */     try {
/* 3248 */       synpred9_ActionTranslator_fragment();
/*      */     } catch (RecognitionException re) {
/* 3250 */       System.err.println("impossible: " + re);
/*      */     }
/* 3252 */     boolean success = !this.state.failed;
/* 3253 */     this.input.rewind(start);
/* 3254 */     this.state.backtracking -= 1;
/* 3255 */     this.state.failed = false;
/* 3256 */     return success;
/*      */   }
/*      */   public final boolean synpred17_ActionTranslator() {
/* 3259 */     this.state.backtracking += 1;
/* 3260 */     int start = this.input.mark();
/*      */     try {
/* 3262 */       synpred17_ActionTranslator_fragment();
/*      */     } catch (RecognitionException re) {
/* 3264 */       System.err.println("impossible: " + re);
/*      */     }
/* 3266 */     boolean success = !this.state.failed;
/* 3267 */     this.input.rewind(start);
/* 3268 */     this.state.backtracking -= 1;
/* 3269 */     this.state.failed = false;
/* 3270 */     return success;
/*      */   }
/*      */   public final boolean synpred4_ActionTranslator() {
/* 3273 */     this.state.backtracking += 1;
/* 3274 */     int start = this.input.mark();
/*      */     try {
/* 3276 */       synpred4_ActionTranslator_fragment();
/*      */     } catch (RecognitionException re) {
/* 3278 */       System.err.println("impossible: " + re);
/*      */     }
/* 3280 */     boolean success = !this.state.failed;
/* 3281 */     this.input.rewind(start);
/* 3282 */     this.state.backtracking -= 1;
/* 3283 */     this.state.failed = false;
/* 3284 */     return success;
/*      */   }
/*      */   public final boolean synpred13_ActionTranslator() {
/* 3287 */     this.state.backtracking += 1;
/* 3288 */     int start = this.input.mark();
/*      */     try {
/* 3290 */       synpred13_ActionTranslator_fragment();
/*      */     } catch (RecognitionException re) {
/* 3292 */       System.err.println("impossible: " + re);
/*      */     }
/* 3294 */     boolean success = !this.state.failed;
/* 3295 */     this.input.rewind(start);
/* 3296 */     this.state.backtracking -= 1;
/* 3297 */     this.state.failed = false;
/* 3298 */     return success;
/*      */   }
/*      */   public final boolean synpred21_ActionTranslator() {
/* 3301 */     this.state.backtracking += 1;
/* 3302 */     int start = this.input.mark();
/*      */     try {
/* 3304 */       synpred21_ActionTranslator_fragment();
/*      */     } catch (RecognitionException re) {
/* 3306 */       System.err.println("impossible: " + re);
/*      */     }
/* 3308 */     boolean success = !this.state.failed;
/* 3309 */     this.input.rewind(start);
/* 3310 */     this.state.backtracking -= 1;
/* 3311 */     this.state.failed = false;
/* 3312 */     return success;
/*      */   }
/*      */   public final boolean synpred20_ActionTranslator() {
/* 3315 */     this.state.backtracking += 1;
/* 3316 */     int start = this.input.mark();
/*      */     try {
/* 3318 */       synpred20_ActionTranslator_fragment();
/*      */     } catch (RecognitionException re) {
/* 3320 */       System.err.println("impossible: " + re);
/*      */     }
/* 3322 */     boolean success = !this.state.failed;
/* 3323 */     this.input.rewind(start);
/* 3324 */     this.state.backtracking -= 1;
/* 3325 */     this.state.failed = false;
/* 3326 */     return success;
/*      */   }
/*      */   public final boolean synpred6_ActionTranslator() {
/* 3329 */     this.state.backtracking += 1;
/* 3330 */     int start = this.input.mark();
/*      */     try {
/* 3332 */       synpred6_ActionTranslator_fragment();
/*      */     } catch (RecognitionException re) {
/* 3334 */       System.err.println("impossible: " + re);
/*      */     }
/* 3336 */     boolean success = !this.state.failed;
/* 3337 */     this.input.rewind(start);
/* 3338 */     this.state.backtracking -= 1;
/* 3339 */     this.state.failed = false;
/* 3340 */     return success;
/*      */   }
/*      */   public final boolean synpred2_ActionTranslator() {
/* 3343 */     this.state.backtracking += 1;
/* 3344 */     int start = this.input.mark();
/*      */     try {
/* 3346 */       synpred2_ActionTranslator_fragment();
/*      */     } catch (RecognitionException re) {
/* 3348 */       System.err.println("impossible: " + re);
/*      */     }
/* 3350 */     boolean success = !this.state.failed;
/* 3351 */     this.input.rewind(start);
/* 3352 */     this.state.backtracking -= 1;
/* 3353 */     this.state.failed = false;
/* 3354 */     return success;
/*      */   }
/*      */   public final boolean synpred3_ActionTranslator() {
/* 3357 */     this.state.backtracking += 1;
/* 3358 */     int start = this.input.mark();
/*      */     try {
/* 3360 */       synpred3_ActionTranslator_fragment();
/*      */     } catch (RecognitionException re) {
/* 3362 */       System.err.println("impossible: " + re);
/*      */     }
/* 3364 */     boolean success = !this.state.failed;
/* 3365 */     this.input.rewind(start);
/* 3366 */     this.state.backtracking -= 1;
/* 3367 */     this.state.failed = false;
/* 3368 */     return success;
/*      */   }
/*      */   public final boolean synpred10_ActionTranslator() {
/* 3371 */     this.state.backtracking += 1;
/* 3372 */     int start = this.input.mark();
/*      */     try {
/* 3374 */       synpred10_ActionTranslator_fragment();
/*      */     } catch (RecognitionException re) {
/* 3376 */       System.err.println("impossible: " + re);
/*      */     }
/* 3378 */     boolean success = !this.state.failed;
/* 3379 */     this.input.rewind(start);
/* 3380 */     this.state.backtracking -= 1;
/* 3381 */     this.state.failed = false;
/* 3382 */     return success;
/*      */   }
/*      */   public final boolean synpred5_ActionTranslator() {
/* 3385 */     this.state.backtracking += 1;
/* 3386 */     int start = this.input.mark();
/*      */     try {
/* 3388 */       synpred5_ActionTranslator_fragment();
/*      */     } catch (RecognitionException re) {
/* 3390 */       System.err.println("impossible: " + re);
/*      */     }
/* 3392 */     boolean success = !this.state.failed;
/* 3393 */     this.input.rewind(start);
/* 3394 */     this.state.backtracking -= 1;
/* 3395 */     this.state.failed = false;
/* 3396 */     return success;
/*      */   }
/*      */   public final boolean synpred14_ActionTranslator() {
/* 3399 */     this.state.backtracking += 1;
/* 3400 */     int start = this.input.mark();
/*      */     try {
/* 3402 */       synpred14_ActionTranslator_fragment();
/*      */     } catch (RecognitionException re) {
/* 3404 */       System.err.println("impossible: " + re);
/*      */     }
/* 3406 */     boolean success = !this.state.failed;
/* 3407 */     this.input.rewind(start);
/* 3408 */     this.state.backtracking -= 1;
/* 3409 */     this.state.failed = false;
/* 3410 */     return success;
/*      */   }
/*      */   public final boolean synpred25_ActionTranslator() {
/* 3413 */     this.state.backtracking += 1;
/* 3414 */     int start = this.input.mark();
/*      */     try {
/* 3416 */       synpred25_ActionTranslator_fragment();
/*      */     } catch (RecognitionException re) {
/* 3418 */       System.err.println("impossible: " + re);
/*      */     }
/* 3420 */     boolean success = !this.state.failed;
/* 3421 */     this.input.rewind(start);
/* 3422 */     this.state.backtracking -= 1;
/* 3423 */     this.state.failed = false;
/* 3424 */     return success;
/*      */   }
/*      */   public final boolean synpred26_ActionTranslator() {
/* 3427 */     this.state.backtracking += 1;
/* 3428 */     int start = this.input.mark();
/*      */     try {
/* 3430 */       synpred26_ActionTranslator_fragment();
/*      */     } catch (RecognitionException re) {
/* 3432 */       System.err.println("impossible: " + re);
/*      */     }
/* 3434 */     boolean success = !this.state.failed;
/* 3435 */     this.input.rewind(start);
/* 3436 */     this.state.backtracking -= 1;
/* 3437 */     this.state.failed = false;
/* 3438 */     return success;
/*      */   }
/*      */   public final boolean synpred7_ActionTranslator() {
/* 3441 */     this.state.backtracking += 1;
/* 3442 */     int start = this.input.mark();
/*      */     try {
/* 3444 */       synpred7_ActionTranslator_fragment();
/*      */     } catch (RecognitionException re) {
/* 3446 */       System.err.println("impossible: " + re);
/*      */     }
/* 3448 */     boolean success = !this.state.failed;
/* 3449 */     this.input.rewind(start);
/* 3450 */     this.state.backtracking -= 1;
/* 3451 */     this.state.failed = false;
/* 3452 */     return success;
/*      */   }
/*      */   public final boolean synpred1_ActionTranslator() {
/* 3455 */     this.state.backtracking += 1;
/* 3456 */     int start = this.input.mark();
/*      */     try {
/* 3458 */       synpred1_ActionTranslator_fragment();
/*      */     } catch (RecognitionException re) {
/* 3460 */       System.err.println("impossible: " + re);
/*      */     }
/* 3462 */     boolean success = !this.state.failed;
/* 3463 */     this.input.rewind(start);
/* 3464 */     this.state.backtracking -= 1;
/* 3465 */     this.state.failed = false;
/* 3466 */     return success;
/*      */   }
/*      */   public final boolean synpred22_ActionTranslator() {
/* 3469 */     this.state.backtracking += 1;
/* 3470 */     int start = this.input.mark();
/*      */     try {
/* 3472 */       synpred22_ActionTranslator_fragment();
/*      */     } catch (RecognitionException re) {
/* 3474 */       System.err.println("impossible: " + re);
/*      */     }
/* 3476 */     boolean success = !this.state.failed;
/* 3477 */     this.input.rewind(start);
/* 3478 */     this.state.backtracking -= 1;
/* 3479 */     this.state.failed = false;
/* 3480 */     return success;
/*      */   }
/*      */   public final boolean synpred8_ActionTranslator() {
/* 3483 */     this.state.backtracking += 1;
/* 3484 */     int start = this.input.mark();
/*      */     try {
/* 3486 */       synpred8_ActionTranslator_fragment();
/*      */     } catch (RecognitionException re) {
/* 3488 */       System.err.println("impossible: " + re);
/*      */     }
/* 3490 */     boolean success = !this.state.failed;
/* 3491 */     this.input.rewind(start);
/* 3492 */     this.state.backtracking -= 1;
/* 3493 */     this.state.failed = false;
/* 3494 */     return success;
/*      */   }
/*      */   public final boolean synpred15_ActionTranslator() {
/* 3497 */     this.state.backtracking += 1;
/* 3498 */     int start = this.input.mark();
/*      */     try {
/* 3500 */       synpred15_ActionTranslator_fragment();
/*      */     } catch (RecognitionException re) {
/* 3502 */       System.err.println("impossible: " + re);
/*      */     }
/* 3504 */     boolean success = !this.state.failed;
/* 3505 */     this.input.rewind(start);
/* 3506 */     this.state.backtracking -= 1;
/* 3507 */     this.state.failed = false;
/* 3508 */     return success;
/*      */   }
/*      */ 
/*      */   static
/*      */   {
/* 3549 */     int numStates = DFA22_transitionS.length;
/* 3550 */     DFA22_transition = new short[numStates][];
/* 3551 */     for (int i = 0; i < numStates; i++) {
/* 3552 */       DFA22_transition[i] = DFA.unpackEncodedString(DFA22_transitionS[i]);
/*      */     }
/*      */ 
/* 3587 */     DFA28_transitionS = new String[] { "$\035\001\b\001\0016\035\001\034Ôæ£\035", "\001èøø", "", "", "", "", "", "", "\001èøø", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "" };
/*      */ 
/* 3620 */     DFA28_eot = DFA.unpackEncodedString("\036èøø");
/* 3621 */     DFA28_eof = DFA.unpackEncodedString("\036èøø");
/* 3622 */     DFA28_min = DFA.unpackEncodedStringToUnsignedChars("");
/* 3623 */     DFA28_max = DFA.unpackEncodedStringToUnsignedChars("");
/* 3624 */     DFA28_accept = DFA.unpackEncodedString("\002èøø\001\022\001\023\001\024\001\025\001\026\001\032\001èøø\001\001\001\002\001\003\001\004\001\005\001\006\001\007\001\b\001\t\001\n\001\013\001\f\001\r\001\016\001\017\001\020\001\021\001\030\001\031\001\027\001\033");
/* 3625 */     DFA28_special = DFA.unpackEncodedString("");
/*      */ 
/* 3629 */     int numStates = DFA28_transitionS.length;
/* 3630 */     DFA28_transition = new short[numStates][];
/* 3631 */     for (int i = 0; i < numStates; i++)
/* 3632 */       DFA28_transition[i] = DFA.unpackEncodedString(DFA28_transitionS[i]);
/*      */   }
/*      */ 
/*      */   class DFA28 extends DFA
/*      */   {
/*      */     public DFA28(BaseRecognizer recognizer)
/*      */     {
/* 3639 */       this.recognizer = recognizer;
/* 3640 */       this.decisionNumber = 28;
/* 3641 */       this.eot = ActionTranslator.DFA28_eot;
/* 3642 */       this.eof = ActionTranslator.DFA28_eof;
/* 3643 */       this.min = ActionTranslator.DFA28_min;
/* 3644 */       this.max = ActionTranslator.DFA28_max;
/* 3645 */       this.accept = ActionTranslator.DFA28_accept;
/* 3646 */       this.special = ActionTranslator.DFA28_special;
/* 3647 */       this.transition = ActionTranslator.DFA28_transition;
/*      */     }
/*      */     public String getDescription() {
/* 3650 */       return "1:1: Tokens options {k=1; backtrack=true; } : ( SET_ENCLOSING_RULE_SCOPE_ATTR | ENCLOSING_RULE_SCOPE_ATTR | SET_TOKEN_SCOPE_ATTR | TOKEN_SCOPE_ATTR | SET_RULE_SCOPE_ATTR | RULE_SCOPE_ATTR | LABEL_REF | ISOLATED_TOKEN_REF | ISOLATED_LEXER_RULE_REF | SET_LOCAL_ATTR | LOCAL_ATTR | SET_DYNAMIC_SCOPE_ATTR | DYNAMIC_SCOPE_ATTR | ERROR_SCOPED_XY | DYNAMIC_NEGATIVE_INDEXED_SCOPE_ATTR | DYNAMIC_ABSOLUTE_INDEXED_SCOPE_ATTR | ISOLATED_DYNAMIC_SCOPE | TEMPLATE_INSTANCE | INDIRECT_TEMPLATE_INSTANCE | SET_EXPR_ATTRIBUTE | SET_ATTRIBUTE | TEMPLATE_EXPR | ESC | ERROR_XY | ERROR_X | UNKNOWN_SYNTAX | TEXT );";
/*      */     }
/*      */     public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
/* 3653 */       IntStream input = _input;
/* 3654 */       int _s = s;
/* 3655 */       switch (s) {
/*      */       case 0:
/* 3657 */         int LA28_0 = input.LA(1);
/*      */ 
/* 3659 */         s = -1;
/* 3660 */         if (LA28_0 == 37) s = 1;
/* 3662 */         else if (LA28_0 == 36) s = 8;
/* 3664 */         else if (LA28_0 == 92) s = 28;
/* 3666 */         else if (((LA28_0 >= 0) && (LA28_0 <= 35)) || ((LA28_0 >= 38) && (LA28_0 <= 91)) || ((LA28_0 >= 93) && (LA28_0 <= 65535))) s = 29;
/*      */ 
/* 3668 */         if (s >= 0) return s;
/*      */         break;
/*      */       case 1:
/* 3671 */         int LA28_1 = input.LA(1);
/*      */ 
/* 3674 */         int index28_1 = input.index();
/* 3675 */         input.rewind();
/* 3676 */         s = -1;
/* 3677 */         if (ActionTranslator.this.synpred18_ActionTranslator()) s = 2;
/* 3679 */         else if (ActionTranslator.this.synpred19_ActionTranslator()) s = 3;
/* 3681 */         else if (ActionTranslator.this.synpred20_ActionTranslator()) s = 4;
/* 3683 */         else if (ActionTranslator.this.synpred21_ActionTranslator()) s = 5;
/* 3685 */         else if (ActionTranslator.this.synpred22_ActionTranslator()) s = 6;
/* 3687 */         else if (ActionTranslator.this.synpred26_ActionTranslator()) s = 7;
/*      */ 
/* 3690 */         input.seek(index28_1);
/* 3691 */         if (s >= 0) return s;
/*      */         break;
/*      */       case 2:
/* 3694 */         int LA28_8 = input.LA(1);
/*      */ 
/* 3697 */         int index28_8 = input.index();
/* 3698 */         input.rewind();
/* 3699 */         s = -1;
/* 3700 */         if (ActionTranslator.this.synpred1_ActionTranslator()) s = 9;
/* 3702 */         else if (ActionTranslator.this.synpred2_ActionTranslator()) s = 10;
/* 3704 */         else if (ActionTranslator.this.synpred3_ActionTranslator()) s = 11;
/* 3706 */         else if (ActionTranslator.this.synpred4_ActionTranslator()) s = 12;
/* 3708 */         else if (ActionTranslator.this.synpred5_ActionTranslator()) s = 13;
/* 3710 */         else if (ActionTranslator.this.synpred6_ActionTranslator()) s = 14;
/* 3712 */         else if (ActionTranslator.this.synpred7_ActionTranslator()) s = 15;
/* 3714 */         else if (ActionTranslator.this.synpred8_ActionTranslator()) s = 16;
/* 3716 */         else if (ActionTranslator.this.synpred9_ActionTranslator()) s = 17;
/* 3718 */         else if (ActionTranslator.this.synpred10_ActionTranslator()) s = 18;
/* 3720 */         else if (ActionTranslator.this.synpred11_ActionTranslator()) s = 19;
/* 3722 */         else if (ActionTranslator.this.synpred12_ActionTranslator()) s = 20;
/* 3724 */         else if (ActionTranslator.this.synpred13_ActionTranslator()) s = 21;
/* 3726 */         else if (ActionTranslator.this.synpred14_ActionTranslator()) s = 22;
/* 3728 */         else if (ActionTranslator.this.synpred15_ActionTranslator()) s = 23;
/* 3730 */         else if (ActionTranslator.this.synpred16_ActionTranslator()) s = 24;
/* 3732 */         else if (ActionTranslator.this.synpred17_ActionTranslator()) s = 25;
/* 3734 */         else if (ActionTranslator.this.synpred24_ActionTranslator()) s = 26;
/* 3736 */         else if (ActionTranslator.this.synpred25_ActionTranslator()) s = 27;
/* 3738 */         else if (ActionTranslator.this.synpred26_ActionTranslator()) s = 7;
/*      */ 
/* 3741 */         input.seek(index28_8);
/* 3742 */         if (s >= 0) return s;
/*      */         break;
/*      */       }
/* 3745 */       if (ActionTranslator.this.state.backtracking > 0) { ActionTranslator.this.state.failed = true; return -1; }
/* 3746 */       NoViableAltException nvae = new NoViableAltException(getDescription(), 28, _s, input);
/*      */ 
/* 3748 */       error(nvae);
/* 3749 */       throw nvae;
/*      */     }
/*      */   }
/*      */ 
/*      */   class DFA22 extends DFA
/*      */   {
/*      */     public DFA22(BaseRecognizer recognizer)
/*      */     {
/* 3559 */       this.recognizer = recognizer;
/* 3560 */       this.decisionNumber = 22;
/* 3561 */       this.eot = ActionTranslator.DFA22_eot;
/* 3562 */       this.eof = ActionTranslator.DFA22_eof;
/* 3563 */       this.min = ActionTranslator.DFA22_min;
/* 3564 */       this.max = ActionTranslator.DFA22_max;
/* 3565 */       this.accept = ActionTranslator.DFA22_accept;
/* 3566 */       this.special = ActionTranslator.DFA22_special;
/* 3567 */       this.transition = ActionTranslator.DFA22_transition;
/*      */     }
/*      */     public String getDescription() {
/* 3570 */       return "()* loopback of 785:8: ( ID | '.' | '(' | ')' | ',' | '{' | '}' | '\"' )*";
/*      */     }
/*      */   }
/*      */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.antlr.grammar.v3.ActionTranslator
 * JD-Core Version:    0.6.2
 */