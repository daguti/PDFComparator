/*      */ package org.antlr.grammar.v2;
/*      */ 
/*      */ import antlr.ASTFactory;
/*      */ import antlr.MismatchedTokenException;
/*      */ import antlr.NoViableAltException;
/*      */ import antlr.RecognitionException;
/*      */ import antlr.Token;
/*      */ import antlr.TreeParser;
/*      */ import antlr.collections.AST;
/*      */ import antlr.collections.impl.ASTArray;
/*      */ import java.util.HashMap;
/*      */ import java.util.Map;
/*      */ import org.antlr.tool.ErrorManager;
/*      */ import org.antlr.tool.Grammar;
/*      */ import org.antlr.tool.GrammarAST;
/*      */ 
/*      */ public class AssignTokenTypesWalker extends TreeParser
/*      */   implements AssignTokenTypesWalkerTokenTypes
/*      */ {
/*      */   protected Grammar grammar;
/*      */   protected String currentRuleName;
/*      */   protected static GrammarAST stringAlias;
/*      */   protected static GrammarAST charAlias;
/*      */   protected static GrammarAST stringAlias2;
/*      */   protected static GrammarAST charAlias2;
/* 1851 */   public static final String[] _tokenNames = { "<0>", "EOF", "<2>", "NULL_TREE_LOOKAHEAD", "\"options\"", "\"tokens\"", "\"parser\"", "LEXER", "RULE", "BLOCK", "OPTIONAL", "CLOSURE", "POSITIVE_CLOSURE", "SYNPRED", "RANGE", "CHAR_RANGE", "EPSILON", "ALT", "EOR", "EOB", "EOA", "ID", "ARG", "ARGLIST", "RET", "LEXER_GRAMMAR", "PARSER_GRAMMAR", "TREE_GRAMMAR", "COMBINED_GRAMMAR", "INITACTION", "FORCED_ACTION", "LABEL", "TEMPLATE", "\"scope\"", "\"import\"", "GATED_SEMPRED", "SYN_SEMPRED", "BACKTRACK_SEMPRED", "\"fragment\"", "DOT", "ACTION", "DOC_COMMENT", "SEMI", "\"lexer\"", "\"tree\"", "\"grammar\"", "AMPERSAND", "COLON", "RCURLY", "ASSIGN", "STRING_LITERAL", "CHAR_LITERAL", "INT", "STAR", "COMMA", "TOKEN_REF", "\"protected\"", "\"public\"", "\"private\"", "BANG", "ARG_ACTION", "\"returns\"", "\"throws\"", "LPAREN", "OR", "RPAREN", "\"catch\"", "\"finally\"", "PLUS_ASSIGN", "SEMPRED", "IMPLIES", "ROOT", "WILDCARD", "RULE_REF", "NOT", "TREE_BEGIN", "QUESTION", "PLUS", "OPEN_ELEMENT_OPTION", "CLOSE_ELEMENT_OPTION", "REWRITE", "ETC", "DOLLAR", "DOUBLE_QUOTE_STRING_LITERAL", "DOUBLE_ANGLE_STRING_LITERAL", "WS", "COMMENT", "SL_COMMENT", "ML_COMMENT", "STRAY_BRACKET", "ESC", "DIGIT", "XDIGIT", "NESTED_ARG_ACTION", "NESTED_ACTION", "ACTION_CHAR_LITERAL", "ACTION_STRING_LITERAL", "ACTION_ESC", "WS_LOOP", "INTERNAL_RULE_REF", "WS_OPT", "SRC", "CHARSET" };
/*      */ 
/*      */   public void reportError(RecognitionException ex)
/*      */   {
/*  103 */     Token token = null;
/*  104 */     if ((ex instanceof MismatchedTokenException)) {
/*  105 */       token = ((MismatchedTokenException)ex).token;
/*      */     }
/*  107 */     else if ((ex instanceof NoViableAltException)) {
/*  108 */       token = ((NoViableAltException)ex).token;
/*      */     }
/*  110 */     ErrorManager.syntaxError(100, this.grammar, token, "assign.types: " + ex.toString(), ex);
/*      */   }
/*      */ 
/*      */   protected void initASTPatterns()
/*      */   {
/*  128 */     stringAlias = (GrammarAST)this.astFactory.make(new ASTArray(3).add((GrammarAST)this.astFactory.create(9)).add((GrammarAST)this.astFactory.make(new ASTArray(3).add((GrammarAST)this.astFactory.create(17)).add((GrammarAST)this.astFactory.create(50)).add((GrammarAST)this.astFactory.create(20)))).add((GrammarAST)this.astFactory.create(19)));
/*      */ 
/*  130 */     charAlias = (GrammarAST)this.astFactory.make(new ASTArray(3).add((GrammarAST)this.astFactory.create(9)).add((GrammarAST)this.astFactory.make(new ASTArray(3).add((GrammarAST)this.astFactory.create(17)).add((GrammarAST)this.astFactory.create(51)).add((GrammarAST)this.astFactory.create(20)))).add((GrammarAST)this.astFactory.create(19)));
/*      */ 
/*  132 */     stringAlias2 = (GrammarAST)this.astFactory.make(new ASTArray(3).add((GrammarAST)this.astFactory.create(9)).add((GrammarAST)this.astFactory.make(new ASTArray(4).add((GrammarAST)this.astFactory.create(17)).add((GrammarAST)this.astFactory.create(50)).add((GrammarAST)this.astFactory.create(40)).add((GrammarAST)this.astFactory.create(20)))).add((GrammarAST)this.astFactory.create(19)));
/*      */ 
/*  134 */     charAlias2 = (GrammarAST)this.astFactory.make(new ASTArray(3).add((GrammarAST)this.astFactory.create(9)).add((GrammarAST)this.astFactory.make(new ASTArray(4).add((GrammarAST)this.astFactory.create(17)).add((GrammarAST)this.astFactory.create(51)).add((GrammarAST)this.astFactory.create(40)).add((GrammarAST)this.astFactory.create(20)))).add((GrammarAST)this.astFactory.create(19)));
/*      */   }
/*      */   protected void trackString(GrammarAST t) {
/*      */   }
/*      */   protected void trackToken(GrammarAST t) {
/*      */   }
/*      */   protected void trackTokenRule(GrammarAST t, GrammarAST modifier, GrammarAST block) {
/*      */   }
/*      */   protected void alias(GrammarAST t, GrammarAST s) {
/*      */   }
/*      */   public void defineTokens(Grammar root) {  } 
/*      */   protected void defineStringLiteralsFromDelegates() {  } 
/*      */   protected void assignStringTypes(Grammar root) {  } 
/*      */   protected void aliasTokenIDsAndLiterals(Grammar root) {  } 
/*      */   protected void assignTokenIDTypes(Grammar root) {  } 
/*      */   protected void defineTokenNamesAndLiteralsInGrammar(Grammar root) {  } 
/*      */   protected void init(Grammar root) {  } 
/*  151 */   public AssignTokenTypesWalker() { this.tokenNames = _tokenNames; }
/*      */ 
/*      */ 
/*      */   public final void grammar(AST _t, Grammar g)
/*      */     throws RecognitionException
/*      */   {
/*  158 */     GrammarAST grammar_AST_in = _t == ASTNULL ? null : (GrammarAST)_t;
/*      */ 
/*  160 */     init(g);
/*      */     try
/*      */     {
/*  165 */       if (_t == null) _t = ASTNULL;
/*  166 */       switch (_t.getType())
/*      */       {
/*      */       case 25:
/*  169 */         AST __t3 = _t;
/*  170 */         GrammarAST tmp1_AST_in = (GrammarAST)_t;
/*  171 */         match(_t, 25);
/*  172 */         _t = _t.getFirstChild();
/*  173 */         grammarSpec(_t);
/*  174 */         _t = this._retTree;
/*  175 */         _t = __t3;
/*  176 */         _t = _t.getNextSibling();
/*  177 */         break;
/*      */       case 26:
/*  181 */         AST __t4 = _t;
/*  182 */         GrammarAST tmp2_AST_in = (GrammarAST)_t;
/*  183 */         match(_t, 26);
/*  184 */         _t = _t.getFirstChild();
/*  185 */         grammarSpec(_t);
/*  186 */         _t = this._retTree;
/*  187 */         _t = __t4;
/*  188 */         _t = _t.getNextSibling();
/*  189 */         break;
/*      */       case 27:
/*  193 */         AST __t5 = _t;
/*  194 */         GrammarAST tmp3_AST_in = (GrammarAST)_t;
/*  195 */         match(_t, 27);
/*  196 */         _t = _t.getFirstChild();
/*  197 */         grammarSpec(_t);
/*  198 */         _t = this._retTree;
/*  199 */         _t = __t5;
/*  200 */         _t = _t.getNextSibling();
/*  201 */         break;
/*      */       case 28:
/*  205 */         AST __t6 = _t;
/*  206 */         GrammarAST tmp4_AST_in = (GrammarAST)_t;
/*  207 */         match(_t, 28);
/*  208 */         _t = _t.getFirstChild();
/*  209 */         grammarSpec(_t);
/*  210 */         _t = this._retTree;
/*  211 */         _t = __t6;
/*  212 */         _t = _t.getNextSibling();
/*  213 */         break;
/*      */       default:
/*  217 */         throw new NoViableAltException(_t);
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (RecognitionException ex)
/*      */     {
/*  223 */       reportError(ex);
/*  224 */       if (_t != null) _t = _t.getNextSibling();
/*      */     }
/*  226 */     this._retTree = _t;
/*      */   }
/*      */ 
/*      */   public final void grammarSpec(AST _t) throws RecognitionException
/*      */   {
/*  231 */     GrammarAST grammarSpec_AST_in = _t == ASTNULL ? null : (GrammarAST)_t;
/*  232 */     GrammarAST id = null;
/*  233 */     GrammarAST cmt = null;
/*  234 */     Map opts = null;
/*      */     try
/*      */     {
/*  237 */       id = (GrammarAST)_t;
/*  238 */       match(_t, 21);
/*  239 */       _t = _t.getNextSibling();
/*      */ 
/*  241 */       if (_t == null) _t = ASTNULL;
/*  242 */       switch (_t.getType())
/*      */       {
/*      */       case 41:
/*  245 */         cmt = (GrammarAST)_t;
/*  246 */         match(_t, 41);
/*  247 */         _t = _t.getNextSibling();
/*  248 */         break;
/*      */       case 4:
/*      */       case 5:
/*      */       case 8:
/*      */       case 33:
/*      */       case 34:
/*      */       case 46:
/*  257 */         break;
/*      */       default:
/*  261 */         throw new NoViableAltException(_t);
/*      */       }
/*      */ 
/*  266 */       if (_t == null) _t = ASTNULL;
/*  267 */       switch (_t.getType())
/*      */       {
/*      */       case 4:
/*  270 */         optionsSpec(_t);
/*  271 */         _t = this._retTree;
/*  272 */         break;
/*      */       case 5:
/*      */       case 8:
/*      */       case 33:
/*      */       case 34:
/*      */       case 46:
/*  280 */         break;
/*      */       default:
/*  284 */         throw new NoViableAltException(_t);
/*      */       }
/*      */ 
/*  289 */       if (_t == null) _t = ASTNULL;
/*  290 */       switch (_t.getType())
/*      */       {
/*      */       case 34:
/*  293 */         delegateGrammars(_t);
/*  294 */         _t = this._retTree;
/*  295 */         break;
/*      */       case 5:
/*      */       case 8:
/*      */       case 33:
/*      */       case 46:
/*  302 */         break;
/*      */       default:
/*  306 */         throw new NoViableAltException(_t);
/*      */       }
/*      */ 
/*  311 */       if (_t == null) _t = ASTNULL;
/*  312 */       switch (_t.getType())
/*      */       {
/*      */       case 5:
/*  315 */         tokensSpec(_t);
/*  316 */         _t = this._retTree;
/*  317 */         break;
/*      */       case 8:
/*      */       case 33:
/*      */       case 46:
/*  323 */         break;
/*      */       default:
/*  327 */         throw new NoViableAltException(_t);
/*      */       }
/*      */ 
/*      */       while (true)
/*      */       {
/*  334 */         if (_t == null) _t = ASTNULL;
/*  335 */         if (_t.getType() != 33) break;
/*  336 */         attrScope(_t);
/*  337 */         _t = this._retTree;
/*      */       }
/*      */ 
/*      */       while (true)
/*      */       {
/*  348 */         if (_t == null) _t = ASTNULL;
/*  349 */         if (_t.getType() != 46) break;
/*  350 */         GrammarAST tmp5_AST_in = (GrammarAST)_t;
/*  351 */         match(_t, 46);
/*  352 */         _t = _t.getNextSibling();
/*      */       }
/*      */ 
/*  360 */       rules(_t);
/*  361 */       _t = this._retTree;
/*      */     }
/*      */     catch (RecognitionException ex) {
/*  364 */       reportError(ex);
/*  365 */       if (_t != null) _t = _t.getNextSibling();
/*      */     }
/*  367 */     this._retTree = _t;
/*      */   }
/*      */ 
/*      */   public final Map optionsSpec(AST _t) throws RecognitionException {
/*  371 */     Map opts = new HashMap();
/*      */ 
/*  373 */     GrammarAST optionsSpec_AST_in = _t == ASTNULL ? null : (GrammarAST)_t;
/*      */     try
/*      */     {
/*  376 */       AST __t19 = _t;
/*  377 */       GrammarAST tmp6_AST_in = (GrammarAST)_t;
/*  378 */       match(_t, 4);
/*  379 */       _t = _t.getFirstChild();
/*      */ 
/*  381 */       int _cnt21 = 0;
/*      */       while (true)
/*      */       {
/*  384 */         if (_t == null) _t = ASTNULL;
/*  385 */         if (_t.getType() == 49) {
/*  386 */           option(_t, opts);
/*  387 */           _t = this._retTree;
/*      */         }
/*      */         else {
/*  390 */           if (_cnt21 >= 1) break; throw new NoViableAltException(_t);
/*      */         }
/*      */ 
/*  393 */         _cnt21++;
/*      */       }
/*      */ 
/*  396 */       _t = __t19;
/*  397 */       _t = _t.getNextSibling();
/*      */     }
/*      */     catch (RecognitionException ex) {
/*  400 */       reportError(ex);
/*  401 */       if (_t != null) _t = _t.getNextSibling();
/*      */     }
/*  403 */     this._retTree = _t;
/*  404 */     return opts;
/*      */   }
/*      */ 
/*      */   public final void delegateGrammars(AST _t) throws RecognitionException
/*      */   {
/*  409 */     GrammarAST delegateGrammars_AST_in = _t == ASTNULL ? null : (GrammarAST)_t;
/*      */     try
/*      */     {
/*  412 */       AST __t31 = _t;
/*  413 */       GrammarAST tmp7_AST_in = (GrammarAST)_t;
/*  414 */       match(_t, 34);
/*  415 */       _t = _t.getFirstChild();
/*      */ 
/*  417 */       int _cnt34 = 0;
/*      */       while (true)
/*      */       {
/*  420 */         if (_t == null) _t = ASTNULL;
/*  421 */         switch (_t.getType())
/*      */         {
/*      */         case 49:
/*  424 */           AST __t33 = _t;
/*  425 */           GrammarAST tmp8_AST_in = (GrammarAST)_t;
/*  426 */           match(_t, 49);
/*  427 */           _t = _t.getFirstChild();
/*  428 */           GrammarAST tmp9_AST_in = (GrammarAST)_t;
/*  429 */           match(_t, 21);
/*  430 */           _t = _t.getNextSibling();
/*  431 */           GrammarAST tmp10_AST_in = (GrammarAST)_t;
/*  432 */           match(_t, 21);
/*  433 */           _t = _t.getNextSibling();
/*  434 */           _t = __t33;
/*  435 */           _t = _t.getNextSibling();
/*  436 */           break;
/*      */         case 21:
/*  440 */           GrammarAST tmp11_AST_in = (GrammarAST)_t;
/*  441 */           match(_t, 21);
/*  442 */           _t = _t.getNextSibling();
/*  443 */           break;
/*      */         default:
/*  447 */           if (_cnt34 >= 1) break label203; throw new NoViableAltException(_t);
/*      */         }
/*      */ 
/*  450 */         _cnt34++;
/*      */       }
/*      */ 
/*  453 */       label203: _t = __t31;
/*  454 */       _t = _t.getNextSibling();
/*      */     }
/*      */     catch (RecognitionException ex) {
/*  457 */       reportError(ex);
/*  458 */       if (_t != null) _t = _t.getNextSibling();
/*      */     }
/*  460 */     this._retTree = _t;
/*      */   }
/*      */ 
/*      */   public final void tokensSpec(AST _t) throws RecognitionException
/*      */   {
/*  465 */     GrammarAST tokensSpec_AST_in = _t == ASTNULL ? null : (GrammarAST)_t;
/*      */     try
/*      */     {
/*  468 */       AST __t36 = _t;
/*  469 */       GrammarAST tmp12_AST_in = (GrammarAST)_t;
/*  470 */       match(_t, 5);
/*  471 */       _t = _t.getFirstChild();
/*      */ 
/*  473 */       int _cnt38 = 0;
/*      */       while (true)
/*      */       {
/*  476 */         if (_t == null) _t = ASTNULL;
/*  477 */         if ((_t.getType() == 49) || (_t.getType() == 55)) {
/*  478 */           tokenSpec(_t);
/*  479 */           _t = this._retTree;
/*      */         }
/*      */         else {
/*  482 */           if (_cnt38 >= 1) break; throw new NoViableAltException(_t);
/*      */         }
/*      */ 
/*  485 */         _cnt38++;
/*      */       }
/*      */ 
/*  488 */       _t = __t36;
/*  489 */       _t = _t.getNextSibling();
/*      */     }
/*      */     catch (RecognitionException ex) {
/*  492 */       reportError(ex);
/*  493 */       if (_t != null) _t = _t.getNextSibling();
/*      */     }
/*  495 */     this._retTree = _t;
/*      */   }
/*      */ 
/*      */   public final void attrScope(AST _t) throws RecognitionException
/*      */   {
/*  500 */     GrammarAST attrScope_AST_in = _t == ASTNULL ? null : (GrammarAST)_t;
/*      */     try
/*      */     {
/*  503 */       AST __t17 = _t;
/*  504 */       GrammarAST tmp13_AST_in = (GrammarAST)_t;
/*  505 */       match(_t, 33);
/*  506 */       _t = _t.getFirstChild();
/*  507 */       GrammarAST tmp14_AST_in = (GrammarAST)_t;
/*  508 */       match(_t, 21);
/*  509 */       _t = _t.getNextSibling();
/*  510 */       GrammarAST tmp15_AST_in = (GrammarAST)_t;
/*  511 */       match(_t, 40);
/*  512 */       _t = _t.getNextSibling();
/*  513 */       _t = __t17;
/*  514 */       _t = _t.getNextSibling();
/*      */     }
/*      */     catch (RecognitionException ex) {
/*  517 */       reportError(ex);
/*  518 */       if (_t != null) _t = _t.getNextSibling();
/*      */     }
/*  520 */     this._retTree = _t;
/*      */   }
/*      */ 
/*      */   public final void rules(AST _t) throws RecognitionException
/*      */   {
/*  525 */     GrammarAST rules_AST_in = _t == ASTNULL ? null : (GrammarAST)_t;
/*      */     try
/*      */     {
/*  529 */       int _cnt44 = 0;
/*      */       while (true)
/*      */       {
/*  532 */         if (_t == null) _t = ASTNULL;
/*  533 */         if (_t.getType() == 8) {
/*  534 */           rule(_t);
/*  535 */           _t = this._retTree;
/*      */         }
/*      */         else {
/*  538 */           if (_cnt44 >= 1) break; throw new NoViableAltException(_t);
/*      */         }
/*      */ 
/*  541 */         _cnt44++;
/*      */       }
/*      */     }
/*      */     catch (RecognitionException ex)
/*      */     {
/*  546 */       reportError(ex);
/*  547 */       if (_t != null) _t = _t.getNextSibling();
/*      */     }
/*  549 */     this._retTree = _t;
/*      */   }
/*      */ 
/*      */   public final void option(AST _t, Map opts)
/*      */     throws RecognitionException
/*      */   {
/*  556 */     GrammarAST option_AST_in = _t == ASTNULL ? null : (GrammarAST)_t;
/*  557 */     GrammarAST id = null;
/*      */ 
/*  559 */     String key = null;
/*  560 */     Object value = null;
/*      */     try
/*      */     {
/*  564 */       AST __t23 = _t;
/*  565 */       GrammarAST tmp16_AST_in = (GrammarAST)_t;
/*  566 */       match(_t, 49);
/*  567 */       _t = _t.getFirstChild();
/*  568 */       id = (GrammarAST)_t;
/*  569 */       match(_t, 21);
/*  570 */       _t = _t.getNextSibling();
/*  571 */       key = id.getText();
/*  572 */       value = optionValue(_t);
/*  573 */       _t = this._retTree;
/*  574 */       _t = __t23;
/*  575 */       _t = _t.getNextSibling();
/*      */ 
/*  577 */       opts.put(key, value);
/*      */ 
/*  579 */       if ((this.currentRuleName == null) && (key.equals("tokenVocab"))) {
/*  580 */         this.grammar.importTokenVocabulary(id, (String)value);
/*      */       }
/*      */     }
/*      */     catch (RecognitionException ex)
/*      */     {
/*  585 */       reportError(ex);
/*  586 */       if (_t != null) _t = _t.getNextSibling();
/*      */     }
/*  588 */     this._retTree = _t;
/*      */   }
/*      */ 
/*      */   public final Object optionValue(AST _t) throws RecognitionException {
/*  592 */     Object value = null;
/*      */ 
/*  594 */     GrammarAST optionValue_AST_in = _t == ASTNULL ? null : (GrammarAST)_t;
/*  595 */     GrammarAST id = null;
/*  596 */     GrammarAST s = null;
/*  597 */     GrammarAST c = null;
/*  598 */     GrammarAST i = null;
/*      */     try
/*      */     {
/*  601 */       if (_t == null) _t = ASTNULL;
/*  602 */       switch (_t.getType())
/*      */       {
/*      */       case 21:
/*  605 */         id = (GrammarAST)_t;
/*  606 */         match(_t, 21);
/*  607 */         _t = _t.getNextSibling();
/*  608 */         value = id.getText();
/*  609 */         break;
/*      */       case 50:
/*  613 */         s = (GrammarAST)_t;
/*  614 */         match(_t, 50);
/*  615 */         _t = _t.getNextSibling();
/*  616 */         value = s.getText();
/*  617 */         break;
/*      */       case 51:
/*  621 */         c = (GrammarAST)_t;
/*  622 */         match(_t, 51);
/*  623 */         _t = _t.getNextSibling();
/*  624 */         value = c.getText();
/*  625 */         break;
/*      */       case 52:
/*  629 */         i = (GrammarAST)_t;
/*  630 */         match(_t, 52);
/*  631 */         _t = _t.getNextSibling();
/*  632 */         value = new Integer(i.getText());
/*  633 */         break;
/*      */       default:
/*  637 */         throw new NoViableAltException(_t);
/*      */       }
/*      */     }
/*      */     catch (RecognitionException ex)
/*      */     {
/*  642 */       reportError(ex);
/*  643 */       if (_t != null) _t = _t.getNextSibling();
/*      */     }
/*  645 */     this._retTree = _t;
/*  646 */     return value;
/*      */   }
/*      */ 
/*      */   public final void charSet(AST _t) throws RecognitionException
/*      */   {
/*  651 */     GrammarAST charSet_AST_in = _t == ASTNULL ? null : (GrammarAST)_t;
/*      */     try
/*      */     {
/*  654 */       AST __t26 = _t;
/*  655 */       GrammarAST tmp17_AST_in = (GrammarAST)_t;
/*  656 */       match(_t, 102);
/*  657 */       _t = _t.getFirstChild();
/*  658 */       charSetElement(_t);
/*  659 */       _t = this._retTree;
/*  660 */       _t = __t26;
/*  661 */       _t = _t.getNextSibling();
/*      */     }
/*      */     catch (RecognitionException ex) {
/*  664 */       reportError(ex);
/*  665 */       if (_t != null) _t = _t.getNextSibling();
/*      */     }
/*  667 */     this._retTree = _t;
/*      */   }
/*      */ 
/*      */   public final void charSetElement(AST _t) throws RecognitionException
/*      */   {
/*  672 */     GrammarAST charSetElement_AST_in = _t == ASTNULL ? null : (GrammarAST)_t;
/*  673 */     GrammarAST c = null;
/*  674 */     GrammarAST c1 = null;
/*  675 */     GrammarAST c2 = null;
/*  676 */     GrammarAST c3 = null;
/*  677 */     GrammarAST c4 = null;
/*      */     try
/*      */     {
/*  680 */       if (_t == null) _t = ASTNULL;
/*  681 */       switch (_t.getType())
/*      */       {
/*      */       case 51:
/*  684 */         c = (GrammarAST)_t;
/*  685 */         match(_t, 51);
/*  686 */         _t = _t.getNextSibling();
/*  687 */         break;
/*      */       case 64:
/*  691 */         AST __t28 = _t;
/*  692 */         GrammarAST tmp18_AST_in = (GrammarAST)_t;
/*  693 */         match(_t, 64);
/*  694 */         _t = _t.getFirstChild();
/*  695 */         c1 = (GrammarAST)_t;
/*  696 */         match(_t, 51);
/*  697 */         _t = _t.getNextSibling();
/*  698 */         c2 = (GrammarAST)_t;
/*  699 */         match(_t, 51);
/*  700 */         _t = _t.getNextSibling();
/*  701 */         _t = __t28;
/*  702 */         _t = _t.getNextSibling();
/*  703 */         break;
/*      */       case 14:
/*  707 */         AST __t29 = _t;
/*  708 */         GrammarAST tmp19_AST_in = (GrammarAST)_t;
/*  709 */         match(_t, 14);
/*  710 */         _t = _t.getFirstChild();
/*  711 */         c3 = (GrammarAST)_t;
/*  712 */         match(_t, 51);
/*  713 */         _t = _t.getNextSibling();
/*  714 */         c4 = (GrammarAST)_t;
/*  715 */         match(_t, 51);
/*  716 */         _t = _t.getNextSibling();
/*  717 */         _t = __t29;
/*  718 */         _t = _t.getNextSibling();
/*  719 */         break;
/*      */       default:
/*  723 */         throw new NoViableAltException(_t);
/*      */       }
/*      */     }
/*      */     catch (RecognitionException ex)
/*      */     {
/*  728 */       reportError(ex);
/*  729 */       if (_t != null) _t = _t.getNextSibling();
/*      */     }
/*  731 */     this._retTree = _t;
/*      */   }
/*      */ 
/*      */   public final void tokenSpec(AST _t) throws RecognitionException
/*      */   {
/*  736 */     GrammarAST tokenSpec_AST_in = _t == ASTNULL ? null : (GrammarAST)_t;
/*  737 */     GrammarAST t = null;
/*  738 */     GrammarAST t2 = null;
/*  739 */     GrammarAST s = null;
/*  740 */     GrammarAST c = null;
/*      */     try
/*      */     {
/*  743 */       if (_t == null) _t = ASTNULL;
/*  744 */       switch (_t.getType())
/*      */       {
/*      */       case 55:
/*  747 */         t = (GrammarAST)_t;
/*  748 */         match(_t, 55);
/*  749 */         _t = _t.getNextSibling();
/*  750 */         trackToken(t);
/*  751 */         break;
/*      */       case 49:
/*  755 */         AST __t40 = _t;
/*  756 */         GrammarAST tmp20_AST_in = (GrammarAST)_t;
/*  757 */         match(_t, 49);
/*  758 */         _t = _t.getFirstChild();
/*  759 */         t2 = (GrammarAST)_t;
/*  760 */         match(_t, 55);
/*  761 */         _t = _t.getNextSibling();
/*  762 */         trackToken(t2);
/*      */ 
/*  764 */         if (_t == null) _t = ASTNULL;
/*  765 */         switch (_t.getType())
/*      */         {
/*      */         case 50:
/*  768 */           s = (GrammarAST)_t;
/*  769 */           match(_t, 50);
/*  770 */           _t = _t.getNextSibling();
/*  771 */           trackString(s); alias(t2, s);
/*  772 */           break;
/*      */         case 51:
/*  776 */           c = (GrammarAST)_t;
/*  777 */           match(_t, 51);
/*  778 */           _t = _t.getNextSibling();
/*  779 */           trackString(c); alias(t2, c);
/*  780 */           break;
/*      */         default:
/*  784 */           throw new NoViableAltException(_t);
/*      */         }
/*      */ 
/*  788 */         _t = __t40;
/*  789 */         _t = _t.getNextSibling();
/*  790 */         break;
/*      */       default:
/*  794 */         throw new NoViableAltException(_t);
/*      */       }
/*      */     }
/*      */     catch (RecognitionException ex)
/*      */     {
/*  799 */       reportError(ex);
/*  800 */       if (_t != null) _t = _t.getNextSibling();
/*      */     }
/*  802 */     this._retTree = _t;
/*      */   }
/*      */ 
/*      */   public final void rule(AST _t) throws RecognitionException
/*      */   {
/*  807 */     GrammarAST rule_AST_in = _t == ASTNULL ? null : (GrammarAST)_t;
/*  808 */     GrammarAST id = null;
/*  809 */     GrammarAST m = null;
/*  810 */     GrammarAST b = null;
/*      */     try
/*      */     {
/*  813 */       AST __t46 = _t;
/*  814 */       GrammarAST tmp21_AST_in = (GrammarAST)_t;
/*  815 */       match(_t, 8);
/*  816 */       _t = _t.getFirstChild();
/*  817 */       id = (GrammarAST)_t;
/*  818 */       match(_t, 21);
/*  819 */       _t = _t.getNextSibling();
/*  820 */       this.currentRuleName = id.getText();
/*      */ 
/*  822 */       if (_t == null) _t = ASTNULL;
/*  823 */       switch (_t.getType())
/*      */       {
/*      */       case 38:
/*      */       case 56:
/*      */       case 57:
/*      */       case 58:
/*  829 */         m = _t == ASTNULL ? null : (GrammarAST)_t;
/*  830 */         modifier(_t);
/*  831 */         _t = this._retTree;
/*  832 */         break;
/*      */       case 22:
/*  836 */         break;
/*      */       default:
/*  840 */         throw new NoViableAltException(_t);
/*      */       }
/*      */ 
/*  845 */       GrammarAST tmp22_AST_in = (GrammarAST)_t;
/*  846 */       match(_t, 22);
/*  847 */       _t = _t.getNextSibling();
/*      */ 
/*  849 */       if (_t == null) _t = ASTNULL;
/*  850 */       switch (_t.getType())
/*      */       {
/*      */       case 60:
/*  853 */         GrammarAST tmp23_AST_in = (GrammarAST)_t;
/*  854 */         match(_t, 60);
/*  855 */         _t = _t.getNextSibling();
/*  856 */         break;
/*      */       case 24:
/*  860 */         break;
/*      */       default:
/*  864 */         throw new NoViableAltException(_t);
/*      */       }
/*      */ 
/*  870 */       GrammarAST tmp24_AST_in = (GrammarAST)_t;
/*  871 */       match(_t, 24);
/*  872 */       _t = _t.getNextSibling();
/*      */ 
/*  874 */       if (_t == null) _t = ASTNULL;
/*  875 */       switch (_t.getType())
/*      */       {
/*      */       case 60:
/*  878 */         GrammarAST tmp25_AST_in = (GrammarAST)_t;
/*  879 */         match(_t, 60);
/*  880 */         _t = _t.getNextSibling();
/*  881 */         break;
/*      */       case 4:
/*      */       case 9:
/*      */       case 33:
/*      */       case 46:
/*  888 */         break;
/*      */       default:
/*  892 */         throw new NoViableAltException(_t);
/*      */       }
/*      */ 
/*  898 */       if (_t == null) _t = ASTNULL;
/*  899 */       switch (_t.getType())
/*      */       {
/*      */       case 4:
/*  902 */         optionsSpec(_t);
/*  903 */         _t = this._retTree;
/*  904 */         break;
/*      */       case 9:
/*      */       case 33:
/*      */       case 46:
/*  910 */         break;
/*      */       default:
/*  914 */         throw new NoViableAltException(_t);
/*      */       }
/*      */ 
/*  919 */       if (_t == null) _t = ASTNULL;
/*  920 */       switch (_t.getType())
/*      */       {
/*      */       case 33:
/*  923 */         ruleScopeSpec(_t);
/*  924 */         _t = this._retTree;
/*  925 */         break;
/*      */       case 9:
/*      */       case 46:
/*  930 */         break;
/*      */       default:
/*  934 */         throw new NoViableAltException(_t);
/*      */       }
/*      */ 
/*      */       while (true)
/*      */       {
/*  941 */         if (_t == null) _t = ASTNULL;
/*  942 */         if (_t.getType() != 46) break;
/*  943 */         GrammarAST tmp26_AST_in = (GrammarAST)_t;
/*  944 */         match(_t, 46);
/*  945 */         _t = _t.getNextSibling();
/*      */       }
/*      */ 
/*  953 */       b = _t == ASTNULL ? null : (GrammarAST)_t;
/*  954 */       block(_t);
/*  955 */       _t = this._retTree;
/*      */ 
/*  957 */       if (_t == null) _t = ASTNULL;
/*  958 */       switch (_t.getType())
/*      */       {
/*      */       case 66:
/*      */       case 67:
/*  962 */         exceptionGroup(_t);
/*  963 */         _t = this._retTree;
/*  964 */         break;
/*      */       case 18:
/*  968 */         break;
/*      */       default:
/*  972 */         throw new NoViableAltException(_t);
/*      */       }
/*      */ 
/*  976 */       GrammarAST tmp27_AST_in = (GrammarAST)_t;
/*  977 */       match(_t, 18);
/*  978 */       _t = _t.getNextSibling();
/*  979 */       trackTokenRule(id, m, b);
/*  980 */       _t = __t46;
/*  981 */       _t = _t.getNextSibling();
/*      */     }
/*      */     catch (RecognitionException ex) {
/*  984 */       reportError(ex);
/*  985 */       if (_t != null) _t = _t.getNextSibling();
/*      */     }
/*  987 */     this._retTree = _t;
/*      */   }
/*      */ 
/*      */   public final void modifier(AST _t) throws RecognitionException
/*      */   {
/*  992 */     GrammarAST modifier_AST_in = _t == ASTNULL ? null : (GrammarAST)_t;
/*      */     try
/*      */     {
/*  995 */       if (_t == null) _t = ASTNULL;
/*  996 */       switch (_t.getType())
/*      */       {
/*      */       case 56:
/*  999 */         GrammarAST tmp28_AST_in = (GrammarAST)_t;
/* 1000 */         match(_t, 56);
/* 1001 */         _t = _t.getNextSibling();
/* 1002 */         break;
/*      */       case 57:
/* 1006 */         GrammarAST tmp29_AST_in = (GrammarAST)_t;
/* 1007 */         match(_t, 57);
/* 1008 */         _t = _t.getNextSibling();
/* 1009 */         break;
/*      */       case 58:
/* 1013 */         GrammarAST tmp30_AST_in = (GrammarAST)_t;
/* 1014 */         match(_t, 58);
/* 1015 */         _t = _t.getNextSibling();
/* 1016 */         break;
/*      */       case 38:
/* 1020 */         GrammarAST tmp31_AST_in = (GrammarAST)_t;
/* 1021 */         match(_t, 38);
/* 1022 */         _t = _t.getNextSibling();
/* 1023 */         break;
/*      */       default:
/* 1027 */         throw new NoViableAltException(_t);
/*      */       }
/*      */     }
/*      */     catch (RecognitionException ex)
/*      */     {
/* 1032 */       reportError(ex);
/* 1033 */       if (_t != null) _t = _t.getNextSibling();
/*      */     }
/* 1035 */     this._retTree = _t;
/*      */   }
/*      */ 
/*      */   public final void ruleScopeSpec(AST _t) throws RecognitionException
/*      */   {
/* 1040 */     GrammarAST ruleScopeSpec_AST_in = _t == ASTNULL ? null : (GrammarAST)_t;
/*      */     try
/*      */     {
/* 1043 */       AST __t59 = _t;
/* 1044 */       GrammarAST tmp32_AST_in = (GrammarAST)_t;
/* 1045 */       match(_t, 33);
/* 1046 */       _t = _t.getFirstChild();
/*      */ 
/* 1048 */       if (_t == null) _t = ASTNULL;
/* 1049 */       switch (_t.getType())
/*      */       {
/*      */       case 40:
/* 1052 */         GrammarAST tmp33_AST_in = (GrammarAST)_t;
/* 1053 */         match(_t, 40);
/* 1054 */         _t = _t.getNextSibling();
/* 1055 */         break;
/*      */       case 3:
/*      */       case 21:
/* 1060 */         break;
/*      */       default:
/* 1064 */         throw new NoViableAltException(_t);
/*      */       }
/*      */ 
/*      */       while (true)
/*      */       {
/* 1071 */         if (_t == null) _t = ASTNULL;
/* 1072 */         if (_t.getType() != 21) break;
/* 1073 */         GrammarAST tmp34_AST_in = (GrammarAST)_t;
/* 1074 */         match(_t, 21);
/* 1075 */         _t = _t.getNextSibling();
/*      */       }
/*      */ 
/* 1083 */       _t = __t59;
/* 1084 */       _t = _t.getNextSibling();
/*      */     }
/*      */     catch (RecognitionException ex) {
/* 1087 */       reportError(ex);
/* 1088 */       if (_t != null) _t = _t.getNextSibling();
/*      */     }
/* 1090 */     this._retTree = _t;
/*      */   }
/*      */ 
/*      */   public final void block(AST _t) throws RecognitionException
/*      */   {
/* 1095 */     GrammarAST block_AST_in = _t == ASTNULL ? null : (GrammarAST)_t;
/*      */     try
/*      */     {
/* 1098 */       AST __t64 = _t;
/* 1099 */       GrammarAST tmp35_AST_in = (GrammarAST)_t;
/* 1100 */       match(_t, 9);
/* 1101 */       _t = _t.getFirstChild();
/*      */ 
/* 1103 */       if (_t == null) _t = ASTNULL;
/* 1104 */       switch (_t.getType())
/*      */       {
/*      */       case 4:
/* 1107 */         optionsSpec(_t);
/* 1108 */         _t = this._retTree;
/* 1109 */         break;
/*      */       case 17:
/* 1113 */         break;
/*      */       default:
/* 1117 */         throw new NoViableAltException(_t);
/*      */       }
/*      */ 
/* 1122 */       int _cnt67 = 0;
/*      */       while (true)
/*      */       {
/* 1125 */         if (_t == null) _t = ASTNULL;
/* 1126 */         if (_t.getType() == 17) {
/* 1127 */           alternative(_t);
/* 1128 */           _t = this._retTree;
/* 1129 */           rewrite(_t);
/* 1130 */           _t = this._retTree;
/*      */         }
/*      */         else {
/* 1133 */           if (_cnt67 >= 1) break; throw new NoViableAltException(_t);
/*      */         }
/*      */ 
/* 1136 */         _cnt67++;
/*      */       }
/*      */ 
/* 1139 */       GrammarAST tmp36_AST_in = (GrammarAST)_t;
/* 1140 */       match(_t, 19);
/* 1141 */       _t = _t.getNextSibling();
/* 1142 */       _t = __t64;
/* 1143 */       _t = _t.getNextSibling();
/*      */     }
/*      */     catch (RecognitionException ex) {
/* 1146 */       reportError(ex);
/* 1147 */       if (_t != null) _t = _t.getNextSibling();
/*      */     }
/* 1149 */     this._retTree = _t;
/*      */   }
/*      */ 
/*      */   public final void exceptionGroup(AST _t) throws RecognitionException
/*      */   {
/* 1154 */     GrammarAST exceptionGroup_AST_in = _t == ASTNULL ? null : (GrammarAST)_t;
/*      */     try
/*      */     {
/* 1157 */       if (_t == null) _t = ASTNULL;
/* 1158 */       switch (_t.getType())
/*      */       {
/*      */       case 66:
/* 1162 */         int _cnt74 = 0;
/*      */         while (true)
/*      */         {
/* 1165 */           if (_t == null) _t = ASTNULL;
/* 1166 */           if (_t.getType() == 66) {
/* 1167 */             exceptionHandler(_t);
/* 1168 */             _t = this._retTree;
/*      */           }
/*      */           else {
/* 1171 */             if (_cnt74 >= 1) break; throw new NoViableAltException(_t);
/*      */           }
/*      */ 
/* 1174 */           _cnt74++;
/*      */         }
/*      */ 
/* 1178 */         if (_t == null) _t = ASTNULL;
/* 1179 */         switch (_t.getType())
/*      */         {
/*      */         case 67:
/* 1182 */           finallyClause(_t);
/* 1183 */           _t = this._retTree;
/* 1184 */           break;
/*      */         case 18:
/* 1188 */           break;
/*      */         default:
/* 1192 */           throw new NoViableAltException(_t);
/*      */         }
/*      */ 
/*      */         break;
/*      */       case 67:
/* 1200 */         finallyClause(_t);
/* 1201 */         _t = this._retTree;
/* 1202 */         break;
/*      */       default:
/* 1206 */         throw new NoViableAltException(_t);
/*      */       }
/*      */     }
/*      */     catch (RecognitionException ex)
/*      */     {
/* 1211 */       reportError(ex);
/* 1212 */       if (_t != null) _t = _t.getNextSibling();
/*      */     }
/* 1214 */     this._retTree = _t;
/*      */   }
/*      */ 
/*      */   public final void alternative(AST _t) throws RecognitionException
/*      */   {
/* 1219 */     GrammarAST alternative_AST_in = _t == ASTNULL ? null : (GrammarAST)_t;
/*      */     try
/*      */     {
/* 1222 */       AST __t69 = _t;
/* 1223 */       GrammarAST tmp37_AST_in = (GrammarAST)_t;
/* 1224 */       match(_t, 17);
/* 1225 */       _t = _t.getFirstChild();
/*      */ 
/* 1227 */       int _cnt71 = 0;
/*      */       while (true)
/*      */       {
/* 1230 */         if (_t == null) _t = ASTNULL;
/* 1231 */         if ((_t.getType() == 9) || (_t.getType() == 10) || (_t.getType() == 11) || (_t.getType() == 12) || (_t.getType() == 13) || (_t.getType() == 14) || (_t.getType() == 15) || (_t.getType() == 16) || (_t.getType() == 30) || (_t.getType() == 35) || (_t.getType() == 36) || (_t.getType() == 37) || (_t.getType() == 39) || (_t.getType() == 40) || (_t.getType() == 49) || (_t.getType() == 50) || (_t.getType() == 51) || (_t.getType() == 55) || (_t.getType() == 59) || (_t.getType() == 68) || (_t.getType() == 69) || (_t.getType() == 71) || (_t.getType() == 72) || (_t.getType() == 73) || (_t.getType() == 74) || (_t.getType() == 75)) {
/* 1232 */           element(_t);
/* 1233 */           _t = this._retTree;
/*      */         }
/*      */         else {
/* 1236 */           if (_cnt71 >= 1) break; throw new NoViableAltException(_t);
/*      */         }
/*      */ 
/* 1239 */         _cnt71++;
/*      */       }
/*      */ 
/* 1242 */       GrammarAST tmp38_AST_in = (GrammarAST)_t;
/* 1243 */       match(_t, 20);
/* 1244 */       _t = _t.getNextSibling();
/* 1245 */       _t = __t69;
/* 1246 */       _t = _t.getNextSibling();
/*      */     }
/*      */     catch (RecognitionException ex) {
/* 1249 */       reportError(ex);
/* 1250 */       if (_t != null) _t = _t.getNextSibling();
/*      */     }
/* 1252 */     this._retTree = _t;
/*      */   }
/*      */ 
/*      */   public final void rewrite(AST _t) throws RecognitionException
/*      */   {
/* 1257 */     GrammarAST rewrite_AST_in = _t == ASTNULL ? null : (GrammarAST)_t;
/*      */     try
/*      */     {
/*      */       while (true)
/*      */       {
/* 1263 */         if (_t == null) _t = ASTNULL;
/* 1264 */         if (_t.getType() != 80) break;
/* 1265 */         AST __t82 = _t;
/* 1266 */         GrammarAST tmp39_AST_in = (GrammarAST)_t;
/* 1267 */         match(_t, 80);
/* 1268 */         _t = _t.getFirstChild();
/*      */ 
/* 1270 */         if (_t == null) _t = ASTNULL;
/* 1271 */         switch (_t.getType())
/*      */         {
/*      */         case 69:
/* 1274 */           GrammarAST tmp40_AST_in = (GrammarAST)_t;
/* 1275 */           match(_t, 69);
/* 1276 */           _t = _t.getNextSibling();
/* 1277 */           break;
/*      */         case 17:
/*      */         case 32:
/*      */         case 40:
/*      */         case 81:
/* 1284 */           break;
/*      */         default:
/* 1288 */           throw new NoViableAltException(_t);
/*      */         }
/*      */ 
/* 1293 */         if (_t == null) _t = ASTNULL;
/* 1294 */         switch (_t.getType())
/*      */         {
/*      */         case 17:
/* 1297 */           GrammarAST tmp41_AST_in = (GrammarAST)_t;
/* 1298 */           match(_t, 17);
/* 1299 */           _t = _t.getNextSibling();
/* 1300 */           break;
/*      */         case 32:
/* 1304 */           GrammarAST tmp42_AST_in = (GrammarAST)_t;
/* 1305 */           match(_t, 32);
/* 1306 */           _t = _t.getNextSibling();
/* 1307 */           break;
/*      */         case 40:
/* 1311 */           GrammarAST tmp43_AST_in = (GrammarAST)_t;
/* 1312 */           match(_t, 40);
/* 1313 */           _t = _t.getNextSibling();
/* 1314 */           break;
/*      */         case 81:
/* 1318 */           GrammarAST tmp44_AST_in = (GrammarAST)_t;
/* 1319 */           match(_t, 81);
/* 1320 */           _t = _t.getNextSibling();
/* 1321 */           break;
/*      */         default:
/* 1325 */           throw new NoViableAltException(_t);
/*      */         }
/*      */ 
/* 1329 */         _t = __t82;
/* 1330 */         _t = _t.getNextSibling();
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (RecognitionException ex)
/*      */     {
/* 1340 */       reportError(ex);
/* 1341 */       if (_t != null) _t = _t.getNextSibling();
/*      */     }
/* 1343 */     this._retTree = _t;
/*      */   }
/*      */ 
/*      */   public final void element(AST _t) throws RecognitionException
/*      */   {
/* 1348 */     GrammarAST element_AST_in = _t == ASTNULL ? null : (GrammarAST)_t;
/*      */     try
/*      */     {
/* 1351 */       if (_t == null) _t = ASTNULL;
/* 1352 */       switch (_t.getType())
/*      */       {
/*      */       case 71:
/* 1355 */         AST __t87 = _t;
/* 1356 */         GrammarAST tmp45_AST_in = (GrammarAST)_t;
/* 1357 */         match(_t, 71);
/* 1358 */         _t = _t.getFirstChild();
/* 1359 */         element(_t);
/* 1360 */         _t = this._retTree;
/* 1361 */         _t = __t87;
/* 1362 */         _t = _t.getNextSibling();
/* 1363 */         break;
/*      */       case 59:
/* 1367 */         AST __t88 = _t;
/* 1368 */         GrammarAST tmp46_AST_in = (GrammarAST)_t;
/* 1369 */         match(_t, 59);
/* 1370 */         _t = _t.getFirstChild();
/* 1371 */         element(_t);
/* 1372 */         _t = this._retTree;
/* 1373 */         _t = __t88;
/* 1374 */         _t = _t.getNextSibling();
/* 1375 */         break;
/*      */       case 39:
/*      */       case 50:
/*      */       case 51:
/*      */       case 55:
/*      */       case 72:
/*      */       case 73:
/* 1384 */         atom(_t);
/* 1385 */         _t = this._retTree;
/* 1386 */         break;
/*      */       case 74:
/* 1390 */         AST __t89 = _t;
/* 1391 */         GrammarAST tmp47_AST_in = (GrammarAST)_t;
/* 1392 */         match(_t, 74);
/* 1393 */         _t = _t.getFirstChild();
/* 1394 */         element(_t);
/* 1395 */         _t = this._retTree;
/* 1396 */         _t = __t89;
/* 1397 */         _t = _t.getNextSibling();
/* 1398 */         break;
/*      */       case 14:
/* 1402 */         AST __t90 = _t;
/* 1403 */         GrammarAST tmp48_AST_in = (GrammarAST)_t;
/* 1404 */         match(_t, 14);
/* 1405 */         _t = _t.getFirstChild();
/* 1406 */         atom(_t);
/* 1407 */         _t = this._retTree;
/* 1408 */         atom(_t);
/* 1409 */         _t = this._retTree;
/* 1410 */         _t = __t90;
/* 1411 */         _t = _t.getNextSibling();
/* 1412 */         break;
/*      */       case 15:
/* 1416 */         AST __t91 = _t;
/* 1417 */         GrammarAST tmp49_AST_in = (GrammarAST)_t;
/* 1418 */         match(_t, 15);
/* 1419 */         _t = _t.getFirstChild();
/* 1420 */         atom(_t);
/* 1421 */         _t = this._retTree;
/* 1422 */         atom(_t);
/* 1423 */         _t = this._retTree;
/* 1424 */         _t = __t91;
/* 1425 */         _t = _t.getNextSibling();
/* 1426 */         break;
/*      */       case 49:
/* 1430 */         AST __t92 = _t;
/* 1431 */         GrammarAST tmp50_AST_in = (GrammarAST)_t;
/* 1432 */         match(_t, 49);
/* 1433 */         _t = _t.getFirstChild();
/* 1434 */         GrammarAST tmp51_AST_in = (GrammarAST)_t;
/* 1435 */         match(_t, 21);
/* 1436 */         _t = _t.getNextSibling();
/* 1437 */         element(_t);
/* 1438 */         _t = this._retTree;
/* 1439 */         _t = __t92;
/* 1440 */         _t = _t.getNextSibling();
/* 1441 */         break;
/*      */       case 68:
/* 1445 */         AST __t93 = _t;
/* 1446 */         GrammarAST tmp52_AST_in = (GrammarAST)_t;
/* 1447 */         match(_t, 68);
/* 1448 */         _t = _t.getFirstChild();
/* 1449 */         GrammarAST tmp53_AST_in = (GrammarAST)_t;
/* 1450 */         match(_t, 21);
/* 1451 */         _t = _t.getNextSibling();
/* 1452 */         element(_t);
/* 1453 */         _t = this._retTree;
/* 1454 */         _t = __t93;
/* 1455 */         _t = _t.getNextSibling();
/* 1456 */         break;
/*      */       case 9:
/*      */       case 10:
/*      */       case 11:
/*      */       case 12:
/* 1463 */         ebnf(_t);
/* 1464 */         _t = this._retTree;
/* 1465 */         break;
/*      */       case 75:
/* 1469 */         tree(_t);
/* 1470 */         _t = this._retTree;
/* 1471 */         break;
/*      */       case 13:
/* 1475 */         AST __t94 = _t;
/* 1476 */         GrammarAST tmp54_AST_in = (GrammarAST)_t;
/* 1477 */         match(_t, 13);
/* 1478 */         _t = _t.getFirstChild();
/* 1479 */         block(_t);
/* 1480 */         _t = this._retTree;
/* 1481 */         _t = __t94;
/* 1482 */         _t = _t.getNextSibling();
/* 1483 */         break;
/*      */       case 30:
/* 1487 */         GrammarAST tmp55_AST_in = (GrammarAST)_t;
/* 1488 */         match(_t, 30);
/* 1489 */         _t = _t.getNextSibling();
/* 1490 */         break;
/*      */       case 40:
/* 1494 */         GrammarAST tmp56_AST_in = (GrammarAST)_t;
/* 1495 */         match(_t, 40);
/* 1496 */         _t = _t.getNextSibling();
/* 1497 */         break;
/*      */       case 69:
/* 1501 */         GrammarAST tmp57_AST_in = (GrammarAST)_t;
/* 1502 */         match(_t, 69);
/* 1503 */         _t = _t.getNextSibling();
/* 1504 */         break;
/*      */       case 36:
/* 1508 */         GrammarAST tmp58_AST_in = (GrammarAST)_t;
/* 1509 */         match(_t, 36);
/* 1510 */         _t = _t.getNextSibling();
/* 1511 */         break;
/*      */       case 37:
/* 1515 */         GrammarAST tmp59_AST_in = (GrammarAST)_t;
/* 1516 */         match(_t, 37);
/* 1517 */         _t = _t.getNextSibling();
/* 1518 */         break;
/*      */       case 35:
/* 1522 */         GrammarAST tmp60_AST_in = (GrammarAST)_t;
/* 1523 */         match(_t, 35);
/* 1524 */         _t = _t.getNextSibling();
/* 1525 */         break;
/*      */       case 16:
/* 1529 */         GrammarAST tmp61_AST_in = (GrammarAST)_t;
/* 1530 */         match(_t, 16);
/* 1531 */         _t = _t.getNextSibling();
/* 1532 */         break;
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
/*      */       case 31:
/*      */       case 32:
/*      */       case 33:
/*      */       case 34:
/*      */       case 38:
/*      */       case 41:
/*      */       case 42:
/*      */       case 43:
/*      */       case 44:
/*      */       case 45:
/*      */       case 46:
/*      */       case 47:
/*      */       case 48:
/*      */       case 52:
/*      */       case 53:
/*      */       case 54:
/*      */       case 56:
/*      */       case 57:
/*      */       case 58:
/*      */       case 60:
/*      */       case 61:
/*      */       case 62:
/*      */       case 63:
/*      */       case 64:
/*      */       case 65:
/*      */       case 66:
/*      */       case 67:
/*      */       case 70:
/*      */       default:
/* 1536 */         throw new NoViableAltException(_t);
/*      */       }
/*      */     }
/*      */     catch (RecognitionException ex)
/*      */     {
/* 1541 */       reportError(ex);
/* 1542 */       if (_t != null) _t = _t.getNextSibling();
/*      */     }
/* 1544 */     this._retTree = _t;
/*      */   }
/*      */ 
/*      */   public final void exceptionHandler(AST _t) throws RecognitionException
/*      */   {
/* 1549 */     GrammarAST exceptionHandler_AST_in = _t == ASTNULL ? null : (GrammarAST)_t;
/*      */     try
/*      */     {
/* 1552 */       AST __t77 = _t;
/* 1553 */       GrammarAST tmp62_AST_in = (GrammarAST)_t;
/* 1554 */       match(_t, 66);
/* 1555 */       _t = _t.getFirstChild();
/* 1556 */       GrammarAST tmp63_AST_in = (GrammarAST)_t;
/* 1557 */       match(_t, 60);
/* 1558 */       _t = _t.getNextSibling();
/* 1559 */       GrammarAST tmp64_AST_in = (GrammarAST)_t;
/* 1560 */       match(_t, 40);
/* 1561 */       _t = _t.getNextSibling();
/* 1562 */       _t = __t77;
/* 1563 */       _t = _t.getNextSibling();
/*      */     }
/*      */     catch (RecognitionException ex) {
/* 1566 */       reportError(ex);
/* 1567 */       if (_t != null) _t = _t.getNextSibling();
/*      */     }
/* 1569 */     this._retTree = _t;
/*      */   }
/*      */ 
/*      */   public final void finallyClause(AST _t) throws RecognitionException
/*      */   {
/* 1574 */     GrammarAST finallyClause_AST_in = _t == ASTNULL ? null : (GrammarAST)_t;
/*      */     try
/*      */     {
/* 1577 */       AST __t79 = _t;
/* 1578 */       GrammarAST tmp65_AST_in = (GrammarAST)_t;
/* 1579 */       match(_t, 67);
/* 1580 */       _t = _t.getFirstChild();
/* 1581 */       GrammarAST tmp66_AST_in = (GrammarAST)_t;
/* 1582 */       match(_t, 40);
/* 1583 */       _t = _t.getNextSibling();
/* 1584 */       _t = __t79;
/* 1585 */       _t = _t.getNextSibling();
/*      */     }
/*      */     catch (RecognitionException ex) {
/* 1588 */       reportError(ex);
/* 1589 */       if (_t != null) _t = _t.getNextSibling();
/*      */     }
/* 1591 */     this._retTree = _t;
/*      */   }
/*      */ 
/*      */   public final void atom(AST _t) throws RecognitionException
/*      */   {
/* 1596 */     GrammarAST atom_AST_in = _t == ASTNULL ? null : (GrammarAST)_t;
/* 1597 */     GrammarAST rr = null;
/* 1598 */     GrammarAST rarg = null;
/* 1599 */     GrammarAST t = null;
/* 1600 */     GrammarAST targ = null;
/* 1601 */     GrammarAST c = null;
/* 1602 */     GrammarAST s = null;
/*      */     try
/*      */     {
/* 1605 */       if (_t == null) _t = ASTNULL;
/* 1606 */       switch (_t.getType())
/*      */       {
/*      */       case 73:
/* 1609 */         AST __t104 = _t;
/* 1610 */         rr = _t == ASTNULL ? null : (GrammarAST)_t;
/* 1611 */         match(_t, 73);
/* 1612 */         _t = _t.getFirstChild();
/*      */ 
/* 1614 */         if (_t == null) _t = ASTNULL;
/* 1615 */         switch (_t.getType())
/*      */         {
/*      */         case 60:
/* 1618 */           rarg = (GrammarAST)_t;
/* 1619 */           match(_t, 60);
/* 1620 */           _t = _t.getNextSibling();
/* 1621 */           break;
/*      */         case 3:
/* 1625 */           break;
/*      */         default:
/* 1629 */           throw new NoViableAltException(_t);
/*      */         }
/*      */ 
/* 1633 */         _t = __t104;
/* 1634 */         _t = _t.getNextSibling();
/* 1635 */         break;
/*      */       case 55:
/* 1639 */         AST __t106 = _t;
/* 1640 */         t = _t == ASTNULL ? null : (GrammarAST)_t;
/* 1641 */         match(_t, 55);
/* 1642 */         _t = _t.getFirstChild();
/*      */ 
/* 1644 */         if (_t == null) _t = ASTNULL;
/* 1645 */         switch (_t.getType())
/*      */         {
/*      */         case 60:
/* 1648 */           targ = (GrammarAST)_t;
/* 1649 */           match(_t, 60);
/* 1650 */           _t = _t.getNextSibling();
/* 1651 */           break;
/*      */         case 3:
/* 1655 */           break;
/*      */         default:
/* 1659 */           throw new NoViableAltException(_t);
/*      */         }
/*      */ 
/* 1663 */         _t = __t106;
/* 1664 */         _t = _t.getNextSibling();
/* 1665 */         trackToken(t);
/* 1666 */         break;
/*      */       case 51:
/* 1670 */         c = (GrammarAST)_t;
/* 1671 */         match(_t, 51);
/* 1672 */         _t = _t.getNextSibling();
/* 1673 */         trackString(c);
/* 1674 */         break;
/*      */       case 50:
/* 1678 */         s = (GrammarAST)_t;
/* 1679 */         match(_t, 50);
/* 1680 */         _t = _t.getNextSibling();
/* 1681 */         trackString(s);
/* 1682 */         break;
/*      */       case 72:
/* 1686 */         GrammarAST tmp67_AST_in = (GrammarAST)_t;
/* 1687 */         match(_t, 72);
/* 1688 */         _t = _t.getNextSibling();
/* 1689 */         break;
/*      */       case 39:
/* 1693 */         AST __t108 = _t;
/* 1694 */         GrammarAST tmp68_AST_in = (GrammarAST)_t;
/* 1695 */         match(_t, 39);
/* 1696 */         _t = _t.getFirstChild();
/* 1697 */         GrammarAST tmp69_AST_in = (GrammarAST)_t;
/* 1698 */         match(_t, 21);
/* 1699 */         _t = _t.getNextSibling();
/* 1700 */         atom(_t);
/* 1701 */         _t = this._retTree;
/* 1702 */         _t = __t108;
/* 1703 */         _t = _t.getNextSibling();
/* 1704 */         break;
/*      */       default:
/* 1708 */         throw new NoViableAltException(_t);
/*      */       }
/*      */     }
/*      */     catch (RecognitionException ex)
/*      */     {
/* 1713 */       reportError(ex);
/* 1714 */       if (_t != null) _t = _t.getNextSibling();
/*      */     }
/* 1716 */     this._retTree = _t;
/*      */   }
/*      */ 
/*      */   public final void ebnf(AST _t) throws RecognitionException
/*      */   {
/* 1721 */     GrammarAST ebnf_AST_in = _t == ASTNULL ? null : (GrammarAST)_t;
/*      */     try
/*      */     {
/* 1724 */       if (_t == null) _t = ASTNULL;
/* 1725 */       switch (_t.getType())
/*      */       {
/*      */       case 9:
/* 1728 */         block(_t);
/* 1729 */         _t = this._retTree;
/* 1730 */         break;
/*      */       case 10:
/* 1734 */         AST __t96 = _t;
/* 1735 */         GrammarAST tmp70_AST_in = (GrammarAST)_t;
/* 1736 */         match(_t, 10);
/* 1737 */         _t = _t.getFirstChild();
/* 1738 */         block(_t);
/* 1739 */         _t = this._retTree;
/* 1740 */         _t = __t96;
/* 1741 */         _t = _t.getNextSibling();
/* 1742 */         break;
/*      */       case 11:
/* 1746 */         AST __t97 = _t;
/* 1747 */         GrammarAST tmp71_AST_in = (GrammarAST)_t;
/* 1748 */         match(_t, 11);
/* 1749 */         _t = _t.getFirstChild();
/* 1750 */         block(_t);
/* 1751 */         _t = this._retTree;
/* 1752 */         _t = __t97;
/* 1753 */         _t = _t.getNextSibling();
/* 1754 */         break;
/*      */       case 12:
/* 1758 */         AST __t98 = _t;
/* 1759 */         GrammarAST tmp72_AST_in = (GrammarAST)_t;
/* 1760 */         match(_t, 12);
/* 1761 */         _t = _t.getFirstChild();
/* 1762 */         block(_t);
/* 1763 */         _t = this._retTree;
/* 1764 */         _t = __t98;
/* 1765 */         _t = _t.getNextSibling();
/* 1766 */         break;
/*      */       default:
/* 1770 */         throw new NoViableAltException(_t);
/*      */       }
/*      */     }
/*      */     catch (RecognitionException ex)
/*      */     {
/* 1775 */       reportError(ex);
/* 1776 */       if (_t != null) _t = _t.getNextSibling();
/*      */     }
/* 1778 */     this._retTree = _t;
/*      */   }
/*      */ 
/*      */   public final void tree(AST _t) throws RecognitionException
/*      */   {
/* 1783 */     GrammarAST tree_AST_in = _t == ASTNULL ? null : (GrammarAST)_t;
/*      */     try
/*      */     {
/* 1786 */       AST __t100 = _t;
/* 1787 */       GrammarAST tmp73_AST_in = (GrammarAST)_t;
/* 1788 */       match(_t, 75);
/* 1789 */       _t = _t.getFirstChild();
/* 1790 */       element(_t);
/* 1791 */       _t = this._retTree;
/*      */       while (true)
/*      */       {
/* 1795 */         if (_t == null) _t = ASTNULL;
/* 1796 */         if ((_t.getType() != 9) && (_t.getType() != 10) && (_t.getType() != 11) && (_t.getType() != 12) && (_t.getType() != 13) && (_t.getType() != 14) && (_t.getType() != 15) && (_t.getType() != 16) && (_t.getType() != 30) && (_t.getType() != 35) && (_t.getType() != 36) && (_t.getType() != 37) && (_t.getType() != 39) && (_t.getType() != 40) && (_t.getType() != 49) && (_t.getType() != 50) && (_t.getType() != 51) && (_t.getType() != 55) && (_t.getType() != 59) && (_t.getType() != 68) && (_t.getType() != 69) && (_t.getType() != 71) && (_t.getType() != 72) && (_t.getType() != 73) && (_t.getType() != 74) && (_t.getType() != 75)) break;
/* 1797 */         element(_t);
/* 1798 */         _t = this._retTree;
/*      */       }
/*      */ 
/* 1806 */       _t = __t100;
/* 1807 */       _t = _t.getNextSibling();
/*      */     }
/*      */     catch (RecognitionException ex) {
/* 1810 */       reportError(ex);
/* 1811 */       if (_t != null) _t = _t.getNextSibling();
/*      */     }
/* 1813 */     this._retTree = _t;
/*      */   }
/*      */ 
/*      */   public final void ast_suffix(AST _t) throws RecognitionException
/*      */   {
/* 1818 */     GrammarAST ast_suffix_AST_in = _t == ASTNULL ? null : (GrammarAST)_t;
/*      */     try
/*      */     {
/* 1821 */       if (_t == null) _t = ASTNULL;
/* 1822 */       switch (_t.getType())
/*      */       {
/*      */       case 71:
/* 1825 */         GrammarAST tmp74_AST_in = (GrammarAST)_t;
/* 1826 */         match(_t, 71);
/* 1827 */         _t = _t.getNextSibling();
/* 1828 */         break;
/*      */       case 59:
/* 1832 */         GrammarAST tmp75_AST_in = (GrammarAST)_t;
/* 1833 */         match(_t, 59);
/* 1834 */         _t = _t.getNextSibling();
/* 1835 */         break;
/*      */       default:
/* 1839 */         throw new NoViableAltException(_t);
/*      */       }
/*      */     }
/*      */     catch (RecognitionException ex)
/*      */     {
/* 1844 */       reportError(ex);
/* 1845 */       if (_t != null) _t = _t.getNextSibling();
/*      */     }
/* 1847 */     this._retTree = _t;
/*      */   }
/*      */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.antlr.grammar.v2.AssignTokenTypesWalker
 * JD-Core Version:    0.6.2
 */