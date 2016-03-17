/*      */ package org.antlr.grammar.v2;
/*      */ 
/*      */ import antlr.MismatchedTokenException;
/*      */ import antlr.NoViableAltException;
/*      */ import antlr.RecognitionException;
/*      */ import antlr.SemanticException;
/*      */ import antlr.Token;
/*      */ import antlr.TreeParser;
/*      */ import antlr.TreeParserSharedInputState;
/*      */ import antlr.collections.AST;
/*      */ import java.util.ArrayList;
/*      */ import java.util.HashMap;
/*      */ import java.util.HashSet;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.Set;
/*      */ import org.antlr.misc.Utils;
/*      */ import org.antlr.tool.AttributeScope;
/*      */ import org.antlr.tool.ErrorManager;
/*      */ import org.antlr.tool.Grammar;
/*      */ import org.antlr.tool.GrammarAST;
/*      */ import org.antlr.tool.Rule;
/*      */ 
/*      */ public class DefineGrammarItemsWalker extends TreeParser
/*      */   implements DefineGrammarItemsWalkerTokenTypes
/*      */ {
/*      */   protected Grammar grammar;
/*      */   protected GrammarAST root;
/*      */   protected String currentRuleName;
/*      */   protected GrammarAST currentRewriteBlock;
/*      */   protected GrammarAST currentRewriteRule;
/*   57 */   protected int outerAltNum = 0;
/*   58 */   protected int blockLevel = 0;
/*      */ 
/* 3031 */   public static final String[] _tokenNames = { "<0>", "EOF", "<2>", "NULL_TREE_LOOKAHEAD", "\"options\"", "\"tokens\"", "\"parser\"", "LEXER", "RULE", "BLOCK", "OPTIONAL", "CLOSURE", "POSITIVE_CLOSURE", "SYNPRED", "RANGE", "CHAR_RANGE", "EPSILON", "ALT", "EOR", "EOB", "EOA", "ID", "ARG", "ARGLIST", "RET", "LEXER_GRAMMAR", "PARSER_GRAMMAR", "TREE_GRAMMAR", "COMBINED_GRAMMAR", "INITACTION", "FORCED_ACTION", "LABEL", "TEMPLATE", "\"scope\"", "\"import\"", "GATED_SEMPRED", "SYN_SEMPRED", "BACKTRACK_SEMPRED", "\"fragment\"", "DOT", "ACTION", "DOC_COMMENT", "SEMI", "\"lexer\"", "\"tree\"", "\"grammar\"", "AMPERSAND", "COLON", "RCURLY", "ASSIGN", "STRING_LITERAL", "CHAR_LITERAL", "INT", "STAR", "COMMA", "TOKEN_REF", "\"protected\"", "\"public\"", "\"private\"", "BANG", "ARG_ACTION", "\"returns\"", "\"throws\"", "LPAREN", "OR", "RPAREN", "\"catch\"", "\"finally\"", "PLUS_ASSIGN", "SEMPRED", "IMPLIES", "ROOT", "WILDCARD", "RULE_REF", "NOT", "TREE_BEGIN", "QUESTION", "PLUS", "OPEN_ELEMENT_OPTION", "CLOSE_ELEMENT_OPTION", "REWRITE", "ETC", "DOLLAR", "DOUBLE_QUOTE_STRING_LITERAL", "DOUBLE_ANGLE_STRING_LITERAL", "WS", "COMMENT", "SL_COMMENT", "ML_COMMENT", "STRAY_BRACKET", "ESC", "DIGIT", "XDIGIT", "NESTED_ARG_ACTION", "NESTED_ACTION", "ACTION_CHAR_LITERAL", "ACTION_STRING_LITERAL", "ACTION_ESC", "WS_LOOP", "INTERNAL_RULE_REF", "WS_OPT", "SRC" };
/*      */ 
/*      */   public void reportError(RecognitionException ex)
/*      */   {
/*   61 */     Token token = null;
/*   62 */     if ((ex instanceof MismatchedTokenException)) {
/*   63 */       token = ((MismatchedTokenException)ex).token;
/*      */     }
/*   65 */     else if ((ex instanceof NoViableAltException)) {
/*   66 */       token = ((NoViableAltException)ex).token;
/*      */     }
/*   68 */     ErrorManager.syntaxError(100, this.grammar, token, "define: " + ex.toString(), ex);
/*      */   }
/*      */ 
/*      */   protected void finish()
/*      */   {
/*   77 */     trimGrammar();
/*      */   }
/*      */ 
/*      */   protected void trimGrammar()
/*      */   {
/*   82 */     if (this.grammar.type != 4) {
/*   83 */       return;
/*      */     }
/*      */ 
/*   86 */     GrammarAST p = this.root;
/*      */ 
/*   88 */     while (!p.getText().equals("grammar")) {
/*   89 */       p = (GrammarAST)p.getNextSibling();
/*      */     }
/*   91 */     p = (GrammarAST)p.getFirstChild();
/*      */ 
/*   93 */     GrammarAST prev = p;
/*   94 */     while (p.getType() != 8) {
/*   95 */       prev = p;
/*   96 */       p = (GrammarAST)p.getNextSibling();
/*      */     }
/*      */ 
/*   99 */     while (p != null) {
/*  100 */       String ruleName = p.getFirstChild().getText();
/*      */ 
/*  102 */       if (Character.isUpperCase(ruleName.charAt(0)))
/*      */       {
/*  104 */         prev.setNextSibling(p.getNextSibling());
/*      */       }
/*      */       else {
/*  107 */         prev = p;
/*      */       }
/*  109 */       p = (GrammarAST)p.getNextSibling();
/*      */     }
/*      */   }
/*      */ 
/*      */   protected void trackInlineAction(GrammarAST actionAST)
/*      */   {
/*  115 */     Rule r = this.grammar.getRule(this.currentRuleName);
/*  116 */     if (r != null)
/*  117 */       r.trackInlineAction(actionAST);
/*      */   }
/*      */ 
/*      */   public DefineGrammarItemsWalker()
/*      */   {
/*  122 */     this.tokenNames = _tokenNames;
/*      */   }
/*      */ 
/*      */   public final void grammar(AST _t, Grammar g)
/*      */     throws RecognitionException
/*      */   {
/*  129 */     GrammarAST grammar_AST_in = _t == ASTNULL ? null : (GrammarAST)_t;
/*      */ 
/*  131 */     this.grammar = g;
/*  132 */     this.root = grammar_AST_in;
/*      */     try
/*      */     {
/*  137 */       if (_t == null) _t = ASTNULL;
/*  138 */       switch (_t.getType())
/*      */       {
/*      */       case 25:
/*  141 */         AST __t3 = _t;
/*  142 */         GrammarAST tmp1_AST_in = (GrammarAST)_t;
/*  143 */         match(_t, 25);
/*  144 */         _t = _t.getFirstChild();
/*  145 */         if (this.inputState.guessing == 0) {
/*  146 */           this.grammar.type = 1;
/*      */         }
/*  148 */         grammarSpec(_t);
/*  149 */         _t = this._retTree;
/*  150 */         _t = __t3;
/*  151 */         _t = _t.getNextSibling();
/*  152 */         break;
/*      */       case 26:
/*  156 */         AST __t4 = _t;
/*  157 */         GrammarAST tmp2_AST_in = (GrammarAST)_t;
/*  158 */         match(_t, 26);
/*  159 */         _t = _t.getFirstChild();
/*  160 */         if (this.inputState.guessing == 0) {
/*  161 */           this.grammar.type = 2;
/*      */         }
/*  163 */         grammarSpec(_t);
/*  164 */         _t = this._retTree;
/*  165 */         _t = __t4;
/*  166 */         _t = _t.getNextSibling();
/*  167 */         break;
/*      */       case 27:
/*  171 */         AST __t5 = _t;
/*  172 */         GrammarAST tmp3_AST_in = (GrammarAST)_t;
/*  173 */         match(_t, 27);
/*  174 */         _t = _t.getFirstChild();
/*  175 */         if (this.inputState.guessing == 0) {
/*  176 */           this.grammar.type = 3;
/*      */         }
/*  178 */         grammarSpec(_t);
/*  179 */         _t = this._retTree;
/*  180 */         _t = __t5;
/*  181 */         _t = _t.getNextSibling();
/*  182 */         break;
/*      */       case 28:
/*  186 */         AST __t6 = _t;
/*  187 */         GrammarAST tmp4_AST_in = (GrammarAST)_t;
/*  188 */         match(_t, 28);
/*  189 */         _t = _t.getFirstChild();
/*  190 */         if (this.inputState.guessing == 0) {
/*  191 */           this.grammar.type = 4;
/*      */         }
/*  193 */         grammarSpec(_t);
/*  194 */         _t = this._retTree;
/*  195 */         _t = __t6;
/*  196 */         _t = _t.getNextSibling();
/*  197 */         break;
/*      */       default:
/*  201 */         throw new NoViableAltException(_t);
/*      */       }
/*      */ 
/*  205 */       if (this.inputState.guessing == 0)
/*  206 */         finish();
/*      */     }
/*      */     catch (RecognitionException ex)
/*      */     {
/*  210 */       if (this.inputState.guessing == 0) {
/*  211 */         reportError(ex);
/*  212 */         if (_t != null) _t = _t.getNextSibling(); 
/*      */       }
/*  214 */       else { throw ex; }
/*      */ 
/*      */     }
/*  217 */     this._retTree = _t;
/*      */   }
/*      */ 
/*      */   public final void grammarSpec(AST _t) throws RecognitionException
/*      */   {
/*  222 */     GrammarAST grammarSpec_AST_in = _t == ASTNULL ? null : (GrammarAST)_t;
/*  223 */     GrammarAST id = null;
/*  224 */     GrammarAST cmt = null;
/*      */ 
/*  226 */     Map opts = null;
/*  227 */     Token optionsStartToken = null;
/*      */     try
/*      */     {
/*  231 */       id = (GrammarAST)_t;
/*  232 */       match(_t, 21);
/*  233 */       _t = _t.getNextSibling();
/*      */ 
/*  235 */       if (_t == null) _t = ASTNULL;
/*  236 */       switch (_t.getType())
/*      */       {
/*      */       case 41:
/*  239 */         cmt = (GrammarAST)_t;
/*  240 */         match(_t, 41);
/*  241 */         _t = _t.getNextSibling();
/*  242 */         break;
/*      */       case 4:
/*      */       case 5:
/*      */       case 8:
/*      */       case 33:
/*      */       case 34:
/*      */       case 46:
/*  251 */         break;
/*      */       default:
/*  255 */         throw new NoViableAltException(_t);
/*      */       }
/*      */ 
/*  260 */       if (_t == null) _t = ASTNULL;
/*  261 */       switch (_t.getType())
/*      */       {
/*      */       case 4:
/*  264 */         if (this.inputState.guessing == 0) {
/*  265 */           optionsStartToken = ((GrammarAST)_t).getToken();
/*      */         }
/*  267 */         optionsSpec(_t);
/*  268 */         _t = this._retTree;
/*  269 */         break;
/*      */       case 5:
/*      */       case 8:
/*      */       case 33:
/*      */       case 34:
/*      */       case 46:
/*  277 */         break;
/*      */       default:
/*  281 */         throw new NoViableAltException(_t);
/*      */       }
/*      */ 
/*  286 */       if (_t == null) _t = ASTNULL;
/*  287 */       switch (_t.getType())
/*      */       {
/*      */       case 34:
/*  290 */         delegateGrammars(_t);
/*  291 */         _t = this._retTree;
/*  292 */         break;
/*      */       case 5:
/*      */       case 8:
/*      */       case 33:
/*      */       case 46:
/*  299 */         break;
/*      */       default:
/*  303 */         throw new NoViableAltException(_t);
/*      */       }
/*      */ 
/*  308 */       if (_t == null) _t = ASTNULL;
/*  309 */       switch (_t.getType())
/*      */       {
/*      */       case 5:
/*  312 */         tokensSpec(_t);
/*  313 */         _t = this._retTree;
/*  314 */         break;
/*      */       case 8:
/*      */       case 33:
/*      */       case 46:
/*  320 */         break;
/*      */       default:
/*  324 */         throw new NoViableAltException(_t);
/*      */       }
/*      */ 
/*      */       while (true)
/*      */       {
/*  331 */         if (_t == null) _t = ASTNULL;
/*  332 */         if (_t.getType() != 33) break;
/*  333 */         attrScope(_t);
/*  334 */         _t = this._retTree;
/*      */       }
/*      */ 
/*  343 */       if (_t == null) _t = ASTNULL;
/*  344 */       switch (_t.getType())
/*      */       {
/*      */       case 46:
/*  347 */         actions(_t);
/*  348 */         _t = this._retTree;
/*  349 */         break;
/*      */       case 8:
/*  353 */         break;
/*      */       default:
/*  357 */         throw new NoViableAltException(_t);
/*      */       }
/*      */ 
/*  361 */       rules(_t);
/*  362 */       _t = this._retTree;
/*      */     }
/*      */     catch (RecognitionException ex) {
/*  365 */       if (this.inputState.guessing == 0) {
/*  366 */         reportError(ex);
/*  367 */         if (_t != null) _t = _t.getNextSibling(); 
/*      */       }
/*  369 */       else { throw ex; }
/*      */ 
/*      */     }
/*  372 */     this._retTree = _t;
/*      */   }
/*      */ 
/*      */   public final void attrScope(AST _t) throws RecognitionException
/*      */   {
/*  377 */     GrammarAST attrScope_AST_in = _t == ASTNULL ? null : (GrammarAST)_t;
/*  378 */     GrammarAST name = null;
/*  379 */     GrammarAST attrs = null;
/*      */     try
/*      */     {
/*  382 */       AST __t8 = _t;
/*  383 */       GrammarAST tmp5_AST_in = (GrammarAST)_t;
/*  384 */       match(_t, 33);
/*  385 */       _t = _t.getFirstChild();
/*  386 */       name = (GrammarAST)_t;
/*  387 */       match(_t, 21);
/*  388 */       _t = _t.getNextSibling();
/*  389 */       attrs = (GrammarAST)_t;
/*  390 */       match(_t, 40);
/*  391 */       _t = _t.getNextSibling();
/*  392 */       _t = __t8;
/*  393 */       _t = _t.getNextSibling();
/*  394 */       if (this.inputState.guessing == 0)
/*      */       {
/*  396 */         AttributeScope scope = this.grammar.defineGlobalScope(name.getText(), attrs.token);
/*  397 */         scope.isDynamicGlobalScope = true;
/*  398 */         scope.addAttributes(attrs.getText(), 59);
/*      */       }
/*      */     }
/*      */     catch (RecognitionException ex)
/*      */     {
/*  403 */       if (this.inputState.guessing == 0) {
/*  404 */         reportError(ex);
/*  405 */         if (_t != null) _t = _t.getNextSibling(); 
/*      */       }
/*  407 */       else { throw ex; }
/*      */ 
/*      */     }
/*  410 */     this._retTree = _t;
/*      */   }
/*      */ 
/*      */   public final void optionsSpec(AST _t) throws RecognitionException
/*      */   {
/*  415 */     GrammarAST optionsSpec_AST_in = _t == ASTNULL ? null : (GrammarAST)_t;
/*      */     try
/*      */     {
/*  418 */       GrammarAST tmp6_AST_in = (GrammarAST)_t;
/*  419 */       match(_t, 4);
/*  420 */       _t = _t.getNextSibling();
/*      */     }
/*      */     catch (RecognitionException ex) {
/*  423 */       if (this.inputState.guessing == 0) {
/*  424 */         reportError(ex);
/*  425 */         if (_t != null) _t = _t.getNextSibling(); 
/*      */       }
/*  427 */       else { throw ex; }
/*      */ 
/*      */     }
/*  430 */     this._retTree = _t;
/*      */   }
/*      */ 
/*      */   public final void delegateGrammars(AST _t) throws RecognitionException
/*      */   {
/*  435 */     GrammarAST delegateGrammars_AST_in = _t == ASTNULL ? null : (GrammarAST)_t;
/*      */     try
/*      */     {
/*  438 */       AST __t25 = _t;
/*  439 */       GrammarAST tmp7_AST_in = (GrammarAST)_t;
/*  440 */       match(_t, 34);
/*  441 */       _t = _t.getFirstChild();
/*      */ 
/*  443 */       int _cnt28 = 0;
/*      */       while (true)
/*      */       {
/*  446 */         if (_t == null) _t = ASTNULL;
/*  447 */         switch (_t.getType())
/*      */         {
/*      */         case 49:
/*  450 */           AST __t27 = _t;
/*  451 */           GrammarAST tmp8_AST_in = (GrammarAST)_t;
/*  452 */           match(_t, 49);
/*  453 */           _t = _t.getFirstChild();
/*  454 */           GrammarAST tmp9_AST_in = (GrammarAST)_t;
/*  455 */           match(_t, 21);
/*  456 */           _t = _t.getNextSibling();
/*  457 */           GrammarAST tmp10_AST_in = (GrammarAST)_t;
/*  458 */           match(_t, 21);
/*  459 */           _t = _t.getNextSibling();
/*  460 */           _t = __t27;
/*  461 */           _t = _t.getNextSibling();
/*  462 */           break;
/*      */         case 21:
/*  466 */           GrammarAST tmp11_AST_in = (GrammarAST)_t;
/*  467 */           match(_t, 21);
/*  468 */           _t = _t.getNextSibling();
/*  469 */           break;
/*      */         default:
/*  473 */           if (_cnt28 >= 1) break label203; throw new NoViableAltException(_t);
/*      */         }
/*      */ 
/*  476 */         _cnt28++;
/*      */       }
/*      */ 
/*  479 */       label203: _t = __t25;
/*  480 */       _t = _t.getNextSibling();
/*      */     }
/*      */     catch (RecognitionException ex) {
/*  483 */       if (this.inputState.guessing == 0) {
/*  484 */         reportError(ex);
/*  485 */         if (_t != null) _t = _t.getNextSibling(); 
/*      */       }
/*  487 */       else { throw ex; }
/*      */ 
/*      */     }
/*  490 */     this._retTree = _t;
/*      */   }
/*      */ 
/*      */   public final void tokensSpec(AST _t) throws RecognitionException
/*      */   {
/*  495 */     GrammarAST tokensSpec_AST_in = _t == ASTNULL ? null : (GrammarAST)_t;
/*      */     try
/*      */     {
/*  498 */       AST __t30 = _t;
/*  499 */       GrammarAST tmp12_AST_in = (GrammarAST)_t;
/*  500 */       match(_t, 5);
/*  501 */       _t = _t.getFirstChild();
/*      */ 
/*  503 */       int _cnt32 = 0;
/*      */       while (true)
/*      */       {
/*  506 */         if (_t == null) _t = ASTNULL;
/*  507 */         if ((_t.getType() == 49) || (_t.getType() == 55)) {
/*  508 */           tokenSpec(_t);
/*  509 */           _t = this._retTree;
/*      */         }
/*      */         else {
/*  512 */           if (_cnt32 >= 1) break; throw new NoViableAltException(_t);
/*      */         }
/*      */ 
/*  515 */         _cnt32++;
/*      */       }
/*      */ 
/*  518 */       _t = __t30;
/*  519 */       _t = _t.getNextSibling();
/*      */     }
/*      */     catch (RecognitionException ex) {
/*  522 */       if (this.inputState.guessing == 0) {
/*  523 */         reportError(ex);
/*  524 */         if (_t != null) _t = _t.getNextSibling(); 
/*      */       }
/*  526 */       else { throw ex; }
/*      */ 
/*      */     }
/*  529 */     this._retTree = _t;
/*      */   }
/*      */ 
/*      */   public final void actions(AST _t) throws RecognitionException
/*      */   {
/*  534 */     GrammarAST actions_AST_in = _t == ASTNULL ? null : (GrammarAST)_t;
/*      */     try
/*      */     {
/*  538 */       int _cnt19 = 0;
/*      */       while (true)
/*      */       {
/*  541 */         if (_t == null) _t = ASTNULL;
/*  542 */         if (_t.getType() == 46) {
/*  543 */           action(_t);
/*  544 */           _t = this._retTree;
/*      */         }
/*      */         else {
/*  547 */           if (_cnt19 >= 1) break; throw new NoViableAltException(_t);
/*      */         }
/*      */ 
/*  550 */         _cnt19++;
/*      */       }
/*      */     }
/*      */     catch (RecognitionException ex)
/*      */     {
/*  555 */       if (this.inputState.guessing == 0) {
/*  556 */         reportError(ex);
/*  557 */         if (_t != null) _t = _t.getNextSibling(); 
/*      */       }
/*  559 */       else { throw ex; }
/*      */ 
/*      */     }
/*  562 */     this._retTree = _t;
/*      */   }
/*      */ 
/*      */   public final void rules(AST _t) throws RecognitionException
/*      */   {
/*  567 */     GrammarAST rules_AST_in = _t == ASTNULL ? null : (GrammarAST)_t;
/*      */     try
/*      */     {
/*  571 */       int _cnt38 = 0;
/*      */       while (true)
/*      */       {
/*  574 */         if (_t == null) _t = ASTNULL;
/*  575 */         if (_t.getType() == 8) {
/*  576 */           rule(_t);
/*  577 */           _t = this._retTree;
/*      */         }
/*      */         else {
/*  580 */           if (_cnt38 >= 1) break; throw new NoViableAltException(_t);
/*      */         }
/*      */ 
/*  583 */         _cnt38++;
/*      */       }
/*      */     }
/*      */     catch (RecognitionException ex)
/*      */     {
/*  588 */       if (this.inputState.guessing == 0) {
/*  589 */         reportError(ex);
/*  590 */         if (_t != null) _t = _t.getNextSibling(); 
/*      */       }
/*  592 */       else { throw ex; }
/*      */ 
/*      */     }
/*  595 */     this._retTree = _t;
/*      */   }
/*      */ 
/*      */   public final void action(AST _t) throws RecognitionException
/*      */   {
/*  600 */     GrammarAST action_AST_in = _t == ASTNULL ? null : (GrammarAST)_t;
/*  601 */     GrammarAST amp = null;
/*  602 */     GrammarAST id1 = null;
/*  603 */     GrammarAST id2 = null;
/*  604 */     GrammarAST a1 = null;
/*  605 */     GrammarAST a2 = null;
/*      */ 
/*  607 */     String scope = null;
/*  608 */     GrammarAST nameAST = null; GrammarAST actionAST = null;
/*      */     try
/*      */     {
/*  612 */       AST __t21 = _t;
/*  613 */       amp = _t == ASTNULL ? null : (GrammarAST)_t;
/*  614 */       match(_t, 46);
/*  615 */       _t = _t.getFirstChild();
/*  616 */       id1 = (GrammarAST)_t;
/*  617 */       match(_t, 21);
/*  618 */       _t = _t.getNextSibling();
/*      */ 
/*  620 */       if (_t == null) _t = ASTNULL;
/*  621 */       switch (_t.getType())
/*      */       {
/*      */       case 21:
/*  624 */         id2 = (GrammarAST)_t;
/*  625 */         match(_t, 21);
/*  626 */         _t = _t.getNextSibling();
/*  627 */         a1 = (GrammarAST)_t;
/*  628 */         match(_t, 40);
/*  629 */         _t = _t.getNextSibling();
/*  630 */         if (this.inputState.guessing == 0) {
/*  631 */           scope = id1.getText(); nameAST = id2; actionAST = a1; } break;
/*      */       case 40:
/*  637 */         a2 = (GrammarAST)_t;
/*  638 */         match(_t, 40);
/*  639 */         _t = _t.getNextSibling();
/*  640 */         if (this.inputState.guessing == 0) {
/*  641 */           scope = null; nameAST = id1; actionAST = a2; } break;
/*      */       default:
/*  647 */         throw new NoViableAltException(_t);
/*      */       }
/*      */ 
/*  651 */       _t = __t21;
/*  652 */       _t = _t.getNextSibling();
/*  653 */       if (this.inputState.guessing == 0)
/*      */       {
/*  655 */         this.grammar.defineNamedAction(amp, scope, nameAST, actionAST);
/*      */       }
/*      */     }
/*      */     catch (RecognitionException ex)
/*      */     {
/*  660 */       if (this.inputState.guessing == 0) {
/*  661 */         reportError(ex);
/*  662 */         if (_t != null) _t = _t.getNextSibling(); 
/*      */       }
/*  664 */       else { throw ex; }
/*      */ 
/*      */     }
/*  667 */     this._retTree = _t;
/*      */   }
/*      */ 
/*      */   public final void tokenSpec(AST _t) throws RecognitionException
/*      */   {
/*  672 */     GrammarAST tokenSpec_AST_in = _t == ASTNULL ? null : (GrammarAST)_t;
/*  673 */     GrammarAST t = null;
/*  674 */     GrammarAST t2 = null;
/*  675 */     GrammarAST s = null;
/*  676 */     GrammarAST c = null;
/*      */     try
/*      */     {
/*  679 */       if (_t == null) _t = ASTNULL;
/*  680 */       switch (_t.getType())
/*      */       {
/*      */       case 55:
/*  683 */         t = (GrammarAST)_t;
/*  684 */         match(_t, 55);
/*  685 */         _t = _t.getNextSibling();
/*  686 */         break;
/*      */       case 49:
/*  690 */         AST __t34 = _t;
/*  691 */         GrammarAST tmp13_AST_in = (GrammarAST)_t;
/*  692 */         match(_t, 49);
/*  693 */         _t = _t.getFirstChild();
/*  694 */         t2 = (GrammarAST)_t;
/*  695 */         match(_t, 55);
/*  696 */         _t = _t.getNextSibling();
/*      */ 
/*  698 */         if (_t == null) _t = ASTNULL;
/*  699 */         switch (_t.getType())
/*      */         {
/*      */         case 50:
/*  702 */           s = (GrammarAST)_t;
/*  703 */           match(_t, 50);
/*  704 */           _t = _t.getNextSibling();
/*  705 */           break;
/*      */         case 51:
/*  709 */           c = (GrammarAST)_t;
/*  710 */           match(_t, 51);
/*  711 */           _t = _t.getNextSibling();
/*  712 */           break;
/*      */         default:
/*  716 */           throw new NoViableAltException(_t);
/*      */         }
/*      */ 
/*  720 */         _t = __t34;
/*  721 */         _t = _t.getNextSibling();
/*  722 */         break;
/*      */       default:
/*  726 */         throw new NoViableAltException(_t);
/*      */       }
/*      */     }
/*      */     catch (RecognitionException ex)
/*      */     {
/*  731 */       if (this.inputState.guessing == 0) {
/*  732 */         reportError(ex);
/*  733 */         if (_t != null) _t = _t.getNextSibling(); 
/*      */       }
/*  735 */       else { throw ex; }
/*      */ 
/*      */     }
/*  738 */     this._retTree = _t;
/*      */   }
/*      */ 
/*      */   public final void rule(AST _t) throws RecognitionException
/*      */   {
/*  743 */     GrammarAST rule_AST_in = _t == ASTNULL ? null : (GrammarAST)_t;
/*  744 */     GrammarAST id = null;
/*  745 */     GrammarAST args = null;
/*  746 */     GrammarAST ret = null;
/*  747 */     GrammarAST b = null;
/*      */ 
/*  749 */     String mod = null;
/*  750 */     String name = null;
/*  751 */     Map opts = null;
/*  752 */     Rule r = null;
/*      */     try
/*      */     {
/*  756 */       AST __t40 = _t;
/*  757 */       GrammarAST tmp14_AST_in = (GrammarAST)_t;
/*  758 */       match(_t, 8);
/*  759 */       _t = _t.getFirstChild();
/*  760 */       id = (GrammarAST)_t;
/*  761 */       match(_t, 21);
/*  762 */       _t = _t.getNextSibling();
/*  763 */       if (this.inputState.guessing == 0) {
/*  764 */         opts = tmp14_AST_in.getBlockOptions();
/*      */       }
/*      */ 
/*  767 */       if (_t == null) _t = ASTNULL;
/*  768 */       switch (_t.getType())
/*      */       {
/*      */       case 38:
/*      */       case 56:
/*      */       case 57:
/*      */       case 58:
/*  774 */         mod = modifier(_t);
/*  775 */         _t = this._retTree;
/*  776 */         break;
/*      */       case 22:
/*  780 */         break;
/*      */       default:
/*  784 */         throw new NoViableAltException(_t);
/*      */       }
/*      */ 
/*  788 */       AST __t42 = _t;
/*  789 */       GrammarAST tmp15_AST_in = (GrammarAST)_t;
/*  790 */       match(_t, 22);
/*  791 */       _t = _t.getFirstChild();
/*      */ 
/*  793 */       if (_t == null) _t = ASTNULL;
/*  794 */       switch (_t.getType())
/*      */       {
/*      */       case 60:
/*  797 */         args = (GrammarAST)_t;
/*  798 */         match(_t, 60);
/*  799 */         _t = _t.getNextSibling();
/*  800 */         break;
/*      */       case 3:
/*  804 */         break;
/*      */       default:
/*  808 */         throw new NoViableAltException(_t);
/*      */       }
/*      */ 
/*  812 */       _t = __t42;
/*  813 */       _t = _t.getNextSibling();
/*  814 */       AST __t44 = _t;
/*  815 */       GrammarAST tmp16_AST_in = (GrammarAST)_t;
/*  816 */       match(_t, 24);
/*  817 */       _t = _t.getFirstChild();
/*      */ 
/*  819 */       if (_t == null) _t = ASTNULL;
/*  820 */       switch (_t.getType())
/*      */       {
/*      */       case 60:
/*  823 */         ret = (GrammarAST)_t;
/*  824 */         match(_t, 60);
/*  825 */         _t = _t.getNextSibling();
/*  826 */         break;
/*      */       case 3:
/*  830 */         break;
/*      */       default:
/*  834 */         throw new NoViableAltException(_t);
/*      */       }
/*      */ 
/*  838 */       _t = __t44;
/*  839 */       _t = _t.getNextSibling();
/*      */ 
/*  841 */       if (_t == null) _t = ASTNULL;
/*  842 */       switch (_t.getType())
/*      */       {
/*      */       case 4:
/*  845 */         optionsSpec(_t);
/*  846 */         _t = this._retTree;
/*  847 */         break;
/*      */       case 9:
/*      */       case 33:
/*      */       case 46:
/*  853 */         break;
/*      */       default:
/*  857 */         throw new NoViableAltException(_t);
/*      */       }
/*      */ 
/*  861 */       if (this.inputState.guessing == 0)
/*      */       {
/*  863 */         name = id.getText();
/*  864 */         this.currentRuleName = name;
/*  865 */         if ((Character.isUpperCase(name.charAt(0))) && (this.grammar.type == 4))
/*      */         {
/*  869 */           this.grammar.defineLexerRuleFoundInParser(id.getToken(), rule_AST_in);
/*      */         }
/*      */         else {
/*  872 */           int numAlts = countAltsForRule(rule_AST_in);
/*  873 */           this.grammar.defineRule(id.getToken(), mod, opts, rule_AST_in, args, numAlts);
/*  874 */           r = this.grammar.getRule(name);
/*  875 */           if (args != null) {
/*  876 */             r.parameterScope = this.grammar.createParameterScope(name, args.token);
/*  877 */             r.parameterScope.addAttributes(args.getText(), 44);
/*      */           }
/*  879 */           if (ret != null) {
/*  880 */             r.returnScope = this.grammar.createReturnScope(name, ret.token);
/*  881 */             r.returnScope.addAttributes(ret.getText(), 44);
/*      */           }
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/*  887 */       if (_t == null) _t = ASTNULL;
/*  888 */       switch (_t.getType())
/*      */       {
/*      */       case 33:
/*  891 */         ruleScopeSpec(_t, r);
/*  892 */         _t = this._retTree;
/*  893 */         break;
/*      */       case 9:
/*      */       case 46:
/*  898 */         break;
/*      */       default:
/*  902 */         throw new NoViableAltException(_t);
/*      */       }
/*      */ 
/*      */       while (true)
/*      */       {
/*  909 */         if (_t == null) _t = ASTNULL;
/*  910 */         if (_t.getType() != 46) break;
/*  911 */         ruleAction(_t, r);
/*  912 */         _t = this._retTree;
/*      */       }
/*      */ 
/*  920 */       if (this.inputState.guessing == 0) {
/*  921 */         this.blockLevel = 0;
/*      */       }
/*  923 */       b = _t == ASTNULL ? null : (GrammarAST)_t;
/*  924 */       block(_t);
/*  925 */       _t = this._retTree;
/*      */ 
/*  927 */       if (_t == null) _t = ASTNULL;
/*  928 */       switch (_t.getType())
/*      */       {
/*      */       case 66:
/*      */       case 67:
/*  932 */         exceptionGroup(_t);
/*  933 */         _t = this._retTree;
/*  934 */         break;
/*      */       case 18:
/*  938 */         break;
/*      */       default:
/*  942 */         throw new NoViableAltException(_t);
/*      */       }
/*      */ 
/*  946 */       GrammarAST tmp17_AST_in = (GrammarAST)_t;
/*  947 */       match(_t, 18);
/*  948 */       _t = _t.getNextSibling();
/*  949 */       if (this.inputState.guessing == 0)
/*      */       {
/*  953 */         b.setBlockOptions(opts);
/*      */       }
/*      */ 
/*  956 */       _t = __t40;
/*  957 */       _t = _t.getNextSibling();
/*      */     }
/*      */     catch (RecognitionException ex) {
/*  960 */       if (this.inputState.guessing == 0) {
/*  961 */         reportError(ex);
/*  962 */         if (_t != null) _t = _t.getNextSibling(); 
/*      */       }
/*  964 */       else { throw ex; }
/*      */ 
/*      */     }
/*  967 */     this._retTree = _t;
/*      */   }
/*      */ 
/*      */   public final String modifier(AST _t)
/*      */     throws RecognitionException
/*      */   {
/*  973 */     GrammarAST modifier_AST_in = _t == ASTNULL ? null : (GrammarAST)_t;
/*      */ 
/*  975 */     String mod = modifier_AST_in.getText();
/*      */     try
/*      */     {
/*  979 */       if (_t == null) _t = ASTNULL;
/*  980 */       switch (_t.getType())
/*      */       {
/*      */       case 56:
/*  983 */         GrammarAST tmp18_AST_in = (GrammarAST)_t;
/*  984 */         match(_t, 56);
/*  985 */         _t = _t.getNextSibling();
/*  986 */         break;
/*      */       case 57:
/*  990 */         GrammarAST tmp19_AST_in = (GrammarAST)_t;
/*  991 */         match(_t, 57);
/*  992 */         _t = _t.getNextSibling();
/*  993 */         break;
/*      */       case 58:
/*  997 */         GrammarAST tmp20_AST_in = (GrammarAST)_t;
/*  998 */         match(_t, 58);
/*  999 */         _t = _t.getNextSibling();
/* 1000 */         break;
/*      */       case 38:
/* 1004 */         GrammarAST tmp21_AST_in = (GrammarAST)_t;
/* 1005 */         match(_t, 38);
/* 1006 */         _t = _t.getNextSibling();
/* 1007 */         break;
/*      */       default:
/* 1011 */         throw new NoViableAltException(_t);
/*      */       }
/*      */     }
/*      */     catch (RecognitionException ex)
/*      */     {
/* 1016 */       if (this.inputState.guessing == 0) {
/* 1017 */         reportError(ex);
/* 1018 */         if (_t != null) _t = _t.getNextSibling(); 
/*      */       }
/* 1020 */       else { throw ex; }
/*      */ 
/*      */     }
/* 1023 */     this._retTree = _t;
/* 1024 */     return mod;
/*      */   }
/*      */ 
/*      */   public final void ruleScopeSpec(AST _t, Rule r)
/*      */     throws RecognitionException
/*      */   {
/* 1031 */     GrammarAST ruleScopeSpec_AST_in = _t == ASTNULL ? null : (GrammarAST)_t;
/* 1032 */     GrammarAST attrs = null;
/* 1033 */     GrammarAST uses = null;
/*      */     try
/*      */     {
/* 1036 */       AST __t69 = _t;
/* 1037 */       GrammarAST tmp22_AST_in = (GrammarAST)_t;
/* 1038 */       match(_t, 33);
/* 1039 */       _t = _t.getFirstChild();
/*      */ 
/* 1041 */       if (_t == null) _t = ASTNULL;
/* 1042 */       switch (_t.getType())
/*      */       {
/*      */       case 40:
/* 1045 */         attrs = (GrammarAST)_t;
/* 1046 */         match(_t, 40);
/* 1047 */         _t = _t.getNextSibling();
/* 1048 */         if (this.inputState.guessing == 0)
/*      */         {
/* 1050 */           r.ruleScope = this.grammar.createRuleScope(r.name, attrs.token);
/* 1051 */           r.ruleScope.isDynamicRuleScope = true;
/* 1052 */           r.ruleScope.addAttributes(attrs.getText(), 59); } break;
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
/* 1073 */         uses = (GrammarAST)_t;
/* 1074 */         match(_t, 21);
/* 1075 */         _t = _t.getNextSibling();
/* 1076 */         if (this.inputState.guessing == 0)
/*      */         {
/* 1078 */           if (this.grammar.getGlobalScope(uses.getText()) == null) {
/* 1079 */             ErrorManager.grammarError(140, this.grammar, uses.token, uses.getText());
/*      */           }
/*      */           else
/*      */           {
/* 1085 */             if (r.useScopes == null) r.useScopes = new ArrayList();
/* 1086 */             r.useScopes.add(uses.getText());
/*      */           }
/*      */ 
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/* 1097 */       _t = __t69;
/* 1098 */       _t = _t.getNextSibling();
/*      */     }
/*      */     catch (RecognitionException ex) {
/* 1101 */       if (this.inputState.guessing == 0) {
/* 1102 */         reportError(ex);
/* 1103 */         if (_t != null) _t = _t.getNextSibling(); 
/*      */       }
/* 1105 */       else { throw ex; }
/*      */ 
/*      */     }
/* 1108 */     this._retTree = _t;
/*      */   }
/*      */ 
/*      */   public final void ruleAction(AST _t, Rule r)
/*      */     throws RecognitionException
/*      */   {
/* 1115 */     GrammarAST ruleAction_AST_in = _t == ASTNULL ? null : (GrammarAST)_t;
/* 1116 */     GrammarAST amp = null;
/* 1117 */     GrammarAST id = null;
/* 1118 */     GrammarAST a = null;
/*      */     try
/*      */     {
/* 1121 */       AST __t66 = _t;
/* 1122 */       amp = _t == ASTNULL ? null : (GrammarAST)_t;
/* 1123 */       match(_t, 46);
/* 1124 */       _t = _t.getFirstChild();
/* 1125 */       id = (GrammarAST)_t;
/* 1126 */       match(_t, 21);
/* 1127 */       _t = _t.getNextSibling();
/* 1128 */       a = (GrammarAST)_t;
/* 1129 */       match(_t, 40);
/* 1130 */       _t = _t.getNextSibling();
/* 1131 */       _t = __t66;
/* 1132 */       _t = _t.getNextSibling();
/* 1133 */       if ((this.inputState.guessing == 0) && 
/* 1134 */         (r != null)) r.defineNamedAction(amp, id, a);
/*      */     }
/*      */     catch (RecognitionException ex)
/*      */     {
/* 1138 */       if (this.inputState.guessing == 0) {
/* 1139 */         reportError(ex);
/* 1140 */         if (_t != null) _t = _t.getNextSibling(); 
/*      */       }
/* 1142 */       else { throw ex; }
/*      */ 
/*      */     }
/* 1145 */     this._retTree = _t;
/*      */   }
/*      */ 
/*      */   public final void block(AST _t) throws RecognitionException
/*      */   {
/* 1150 */     GrammarAST block_AST_in = _t == ASTNULL ? null : (GrammarAST)_t;
/*      */ 
/* 1152 */     this.blockLevel += 1;
/* 1153 */     if (this.blockLevel == 1) this.outerAltNum = 1;
/*      */ 
/*      */     try
/*      */     {
/* 1157 */       AST __t74 = _t;
/* 1158 */       GrammarAST tmp23_AST_in = (GrammarAST)_t;
/* 1159 */       match(_t, 9);
/* 1160 */       _t = _t.getFirstChild();
/*      */ 
/* 1162 */       if (_t == null) _t = ASTNULL;
/* 1163 */       switch (_t.getType())
/*      */       {
/*      */       case 4:
/* 1166 */         optionsSpec(_t);
/* 1167 */         _t = this._retTree;
/* 1168 */         break;
/*      */       case 17:
/*      */       case 46:
/* 1173 */         break;
/*      */       default:
/* 1177 */         throw new NoViableAltException(_t);
/*      */       }
/*      */ 
/*      */       while (true)
/*      */       {
/* 1184 */         if (_t == null) _t = ASTNULL;
/* 1185 */         if (_t.getType() != 46) break;
/* 1186 */         blockAction(_t);
/* 1187 */         _t = this._retTree;
/*      */       }
/*      */ 
/* 1196 */       int _cnt79 = 0;
/*      */       while (true)
/*      */       {
/* 1199 */         if (_t == null) _t = ASTNULL;
/* 1200 */         if (_t.getType() == 17) {
/* 1201 */           alternative(_t);
/* 1202 */           _t = this._retTree;
/* 1203 */           rewrite(_t);
/* 1204 */           _t = this._retTree;
/* 1205 */           if ((this.inputState.guessing == 0) && 
/* 1206 */             (this.blockLevel == 1)) this.outerAltNum += 1;
/*      */         }
/*      */         else
/*      */         {
/* 1210 */           if (_cnt79 >= 1) break; throw new NoViableAltException(_t);
/*      */         }
/*      */ 
/* 1213 */         _cnt79++;
/*      */       }
/*      */ 
/* 1216 */       GrammarAST tmp24_AST_in = (GrammarAST)_t;
/* 1217 */       match(_t, 19);
/* 1218 */       _t = _t.getNextSibling();
/* 1219 */       _t = __t74;
/* 1220 */       _t = _t.getNextSibling();
/* 1221 */       if (this.inputState.guessing == 0)
/* 1222 */         this.blockLevel -= 1;
/*      */     }
/*      */     catch (RecognitionException ex)
/*      */     {
/* 1226 */       if (this.inputState.guessing == 0) {
/* 1227 */         reportError(ex);
/* 1228 */         if (_t != null) _t = _t.getNextSibling(); 
/*      */       }
/* 1230 */       else { throw ex; }
/*      */ 
/*      */     }
/* 1233 */     this._retTree = _t;
/*      */   }
/*      */ 
/*      */   public final void exceptionGroup(AST _t) throws RecognitionException
/*      */   {
/* 1238 */     GrammarAST exceptionGroup_AST_in = _t == ASTNULL ? null : (GrammarAST)_t;
/*      */     try
/*      */     {
/* 1241 */       if (_t == null) _t = ASTNULL;
/* 1242 */       switch (_t.getType())
/*      */       {
/*      */       case 66:
/* 1246 */         int _cnt88 = 0;
/*      */         while (true)
/*      */         {
/* 1249 */           if (_t == null) _t = ASTNULL;
/* 1250 */           if (_t.getType() == 66) {
/* 1251 */             exceptionHandler(_t);
/* 1252 */             _t = this._retTree;
/*      */           }
/*      */           else {
/* 1255 */             if (_cnt88 >= 1) break; throw new NoViableAltException(_t);
/*      */           }
/*      */ 
/* 1258 */           _cnt88++;
/*      */         }
/*      */ 
/* 1262 */         if (_t == null) _t = ASTNULL;
/* 1263 */         switch (_t.getType())
/*      */         {
/*      */         case 67:
/* 1266 */           finallyClause(_t);
/* 1267 */           _t = this._retTree;
/* 1268 */           break;
/*      */         case 18:
/* 1272 */           break;
/*      */         default:
/* 1276 */           throw new NoViableAltException(_t);
/*      */         }
/*      */ 
/*      */         break;
/*      */       case 67:
/* 1284 */         finallyClause(_t);
/* 1285 */         _t = this._retTree;
/* 1286 */         break;
/*      */       default:
/* 1290 */         throw new NoViableAltException(_t);
/*      */       }
/*      */     }
/*      */     catch (RecognitionException ex)
/*      */     {
/* 1295 */       if (this.inputState.guessing == 0) {
/* 1296 */         reportError(ex);
/* 1297 */         if (_t != null) _t = _t.getNextSibling(); 
/*      */       }
/* 1299 */       else { throw ex; }
/*      */ 
/*      */     }
/* 1302 */     this._retTree = _t;
/*      */   }
/*      */ 
/*      */   public final int countAltsForRule(AST _t) throws RecognitionException {
/* 1306 */     int n = 0;
/*      */ 
/* 1308 */     GrammarAST countAltsForRule_AST_in = _t == ASTNULL ? null : (GrammarAST)_t;
/* 1309 */     GrammarAST id = null;
/*      */     try
/*      */     {
/* 1312 */       AST __t52 = _t;
/* 1313 */       GrammarAST tmp25_AST_in = (GrammarAST)_t;
/* 1314 */       match(_t, 8);
/* 1315 */       _t = _t.getFirstChild();
/* 1316 */       id = (GrammarAST)_t;
/* 1317 */       match(_t, 21);
/* 1318 */       _t = _t.getNextSibling();
/*      */ 
/* 1320 */       if (_t == null) _t = ASTNULL;
/* 1321 */       switch (_t.getType())
/*      */       {
/*      */       case 38:
/*      */       case 56:
/*      */       case 57:
/*      */       case 58:
/* 1327 */         modifier(_t);
/* 1328 */         _t = this._retTree;
/* 1329 */         break;
/*      */       case 22:
/* 1333 */         break;
/*      */       default:
/* 1337 */         throw new NoViableAltException(_t);
/*      */       }
/*      */ 
/* 1341 */       GrammarAST tmp26_AST_in = (GrammarAST)_t;
/* 1342 */       match(_t, 22);
/* 1343 */       _t = _t.getNextSibling();
/* 1344 */       GrammarAST tmp27_AST_in = (GrammarAST)_t;
/* 1345 */       match(_t, 24);
/* 1346 */       _t = _t.getNextSibling();
/*      */ 
/* 1348 */       if (_t == null) _t = ASTNULL;
/* 1349 */       switch (_t.getType())
/*      */       {
/*      */       case 4:
/* 1352 */         GrammarAST tmp28_AST_in = (GrammarAST)_t;
/* 1353 */         match(_t, 4);
/* 1354 */         _t = _t.getNextSibling();
/* 1355 */         break;
/*      */       case 9:
/*      */       case 33:
/*      */       case 46:
/* 1361 */         break;
/*      */       default:
/* 1365 */         throw new NoViableAltException(_t);
/*      */       }
/*      */ 
/* 1370 */       if (_t == null) _t = ASTNULL;
/* 1371 */       switch (_t.getType())
/*      */       {
/*      */       case 33:
/* 1374 */         GrammarAST tmp29_AST_in = (GrammarAST)_t;
/* 1375 */         match(_t, 33);
/* 1376 */         _t = _t.getNextSibling();
/* 1377 */         break;
/*      */       case 9:
/*      */       case 46:
/* 1382 */         break;
/*      */       default:
/* 1386 */         throw new NoViableAltException(_t);
/*      */       }
/*      */ 
/*      */       while (true)
/*      */       {
/* 1393 */         if (_t == null) _t = ASTNULL;
/* 1394 */         if (_t.getType() != 46) break;
/* 1395 */         GrammarAST tmp30_AST_in = (GrammarAST)_t;
/* 1396 */         match(_t, 46);
/* 1397 */         _t = _t.getNextSibling();
/*      */       }
/*      */ 
/* 1405 */       AST __t58 = _t;
/* 1406 */       GrammarAST tmp31_AST_in = (GrammarAST)_t;
/* 1407 */       match(_t, 9);
/* 1408 */       _t = _t.getFirstChild();
/*      */ 
/* 1410 */       if (_t == null) _t = ASTNULL;
/* 1411 */       switch (_t.getType())
/*      */       {
/*      */       case 4:
/* 1414 */         GrammarAST tmp32_AST_in = (GrammarAST)_t;
/* 1415 */         match(_t, 4);
/* 1416 */         _t = _t.getNextSibling();
/* 1417 */         break;
/*      */       case 17:
/* 1421 */         break;
/*      */       default:
/* 1425 */         throw new NoViableAltException(_t);
/*      */       }
/*      */ 
/* 1430 */       int _cnt63 = 0;
/*      */       while (true)
/*      */       {
/* 1433 */         if (_t == null) _t = ASTNULL;
/* 1434 */         if (_t.getType() == 17) {
/* 1435 */           GrammarAST tmp33_AST_in = (GrammarAST)_t;
/* 1436 */           match(_t, 17);
/* 1437 */           _t = _t.getNextSibling();
/*      */           while (true)
/*      */           {
/* 1441 */             if (_t == null) _t = ASTNULL;
/* 1442 */             if (_t.getType() != 80) break;
/* 1443 */             GrammarAST tmp34_AST_in = (GrammarAST)_t;
/* 1444 */             match(_t, 80);
/* 1445 */             _t = _t.getNextSibling();
/*      */           }
/*      */ 
/* 1453 */           if (this.inputState.guessing == 0)
/* 1454 */             n++;
/*      */         }
/*      */         else
/*      */         {
/* 1458 */           if (_cnt63 >= 1) break; throw new NoViableAltException(_t);
/*      */         }
/*      */ 
/* 1461 */         _cnt63++;
/*      */       }
/*      */ 
/* 1464 */       GrammarAST tmp35_AST_in = (GrammarAST)_t;
/* 1465 */       match(_t, 19);
/* 1466 */       _t = _t.getNextSibling();
/* 1467 */       _t = __t58;
/* 1468 */       _t = _t.getNextSibling();
/*      */ 
/* 1470 */       if (_t == null) _t = ASTNULL;
/* 1471 */       switch (_t.getType())
/*      */       {
/*      */       case 66:
/*      */       case 67:
/* 1475 */         exceptionGroup(_t);
/* 1476 */         _t = this._retTree;
/* 1477 */         break;
/*      */       case 18:
/* 1481 */         break;
/*      */       default:
/* 1485 */         throw new NoViableAltException(_t);
/*      */       }
/*      */ 
/* 1489 */       GrammarAST tmp36_AST_in = (GrammarAST)_t;
/* 1490 */       match(_t, 18);
/* 1491 */       _t = _t.getNextSibling();
/* 1492 */       _t = __t52;
/* 1493 */       _t = _t.getNextSibling();
/*      */     }
/*      */     catch (RecognitionException ex) {
/* 1496 */       if (this.inputState.guessing == 0) {
/* 1497 */         reportError(ex);
/* 1498 */         if (_t != null) _t = _t.getNextSibling(); 
/*      */       }
/* 1500 */       else { throw ex; }
/*      */ 
/*      */     }
/* 1503 */     this._retTree = _t;
/* 1504 */     return n;
/*      */   }
/*      */ 
/*      */   public final void blockAction(AST _t) throws RecognitionException
/*      */   {
/* 1509 */     GrammarAST blockAction_AST_in = _t == ASTNULL ? null : (GrammarAST)_t;
/* 1510 */     GrammarAST amp = null;
/* 1511 */     GrammarAST id = null;
/* 1512 */     GrammarAST a = null;
/*      */     try
/*      */     {
/* 1515 */       AST __t81 = _t;
/* 1516 */       amp = _t == ASTNULL ? null : (GrammarAST)_t;
/* 1517 */       match(_t, 46);
/* 1518 */       _t = _t.getFirstChild();
/* 1519 */       id = (GrammarAST)_t;
/* 1520 */       match(_t, 21);
/* 1521 */       _t = _t.getNextSibling();
/* 1522 */       a = (GrammarAST)_t;
/* 1523 */       match(_t, 40);
/* 1524 */       _t = _t.getNextSibling();
/* 1525 */       _t = __t81;
/* 1526 */       _t = _t.getNextSibling();
/*      */     }
/*      */     catch (RecognitionException ex) {
/* 1529 */       if (this.inputState.guessing == 0) {
/* 1530 */         reportError(ex);
/* 1531 */         if (_t != null) _t = _t.getNextSibling(); 
/*      */       }
/* 1533 */       else { throw ex; }
/*      */ 
/*      */     }
/* 1536 */     this._retTree = _t;
/*      */   }
/*      */ 
/*      */   public final void alternative(AST _t) throws RecognitionException
/*      */   {
/* 1541 */     GrammarAST alternative_AST_in = _t == ASTNULL ? null : (GrammarAST)_t;
/*      */ 
/* 1543 */     if ((this.grammar.type != 1) && (this.grammar.getOption("output") != null) && (this.blockLevel == 1)) {
/* 1544 */       GrammarAST aRewriteNode = alternative_AST_in.findFirstType(80);
/* 1545 */       GrammarAST rewriteAST = (GrammarAST)alternative_AST_in.getNextSibling();
/*      */ 
/* 1548 */       if ((aRewriteNode != null) || ((rewriteAST != null) && (rewriteAST.getType() == 80) && (rewriteAST.getFirstChild() != null) && (rewriteAST.getFirstChild().getType() != 81)))
/*      */       {
/* 1554 */         Rule r = this.grammar.getRule(this.currentRuleName);
/* 1555 */         r.trackAltsWithRewrites(alternative_AST_in, this.outerAltNum);
/*      */       }
/*      */     }
/*      */ 
/*      */     try
/*      */     {
/* 1561 */       AST __t83 = _t;
/* 1562 */       GrammarAST tmp37_AST_in = (GrammarAST)_t;
/* 1563 */       match(_t, 17);
/* 1564 */       _t = _t.getFirstChild();
/*      */ 
/* 1566 */       int _cnt85 = 0;
/*      */       while (true)
/*      */       {
/* 1569 */         if (_t == null) _t = ASTNULL;
/* 1570 */         if ((_t.getType() == 9) || (_t.getType() == 10) || (_t.getType() == 11) || (_t.getType() == 12) || (_t.getType() == 13) || (_t.getType() == 14) || (_t.getType() == 15) || (_t.getType() == 16) || (_t.getType() == 30) || (_t.getType() == 35) || (_t.getType() == 36) || (_t.getType() == 37) || (_t.getType() == 39) || (_t.getType() == 40) || (_t.getType() == 49) || (_t.getType() == 50) || (_t.getType() == 51) || (_t.getType() == 55) || (_t.getType() == 59) || (_t.getType() == 68) || (_t.getType() == 69) || (_t.getType() == 71) || (_t.getType() == 72) || (_t.getType() == 73) || (_t.getType() == 74) || (_t.getType() == 75)) {
/* 1571 */           element(_t);
/* 1572 */           _t = this._retTree;
/*      */         }
/*      */         else {
/* 1575 */           if (_cnt85 >= 1) break; throw new NoViableAltException(_t);
/*      */         }
/*      */ 
/* 1578 */         _cnt85++;
/*      */       }
/*      */ 
/* 1581 */       GrammarAST tmp38_AST_in = (GrammarAST)_t;
/* 1582 */       match(_t, 20);
/* 1583 */       _t = _t.getNextSibling();
/* 1584 */       _t = __t83;
/* 1585 */       _t = _t.getNextSibling();
/*      */     }
/*      */     catch (RecognitionException ex) {
/* 1588 */       if (this.inputState.guessing == 0) {
/* 1589 */         reportError(ex);
/* 1590 */         if (_t != null) _t = _t.getNextSibling(); 
/*      */       }
/* 1592 */       else { throw ex; }
/*      */ 
/*      */     }
/* 1595 */     this._retTree = _t;
/*      */   }
/*      */ 
/*      */   public final void rewrite(AST _t) throws RecognitionException
/*      */   {
/* 1600 */     GrammarAST rewrite_AST_in = _t == ASTNULL ? null : (GrammarAST)_t;
/* 1601 */     GrammarAST pred = null;
/*      */ 
/* 1603 */     this.currentRewriteRule = rewrite_AST_in;
/* 1604 */     if (this.grammar.buildAST()) {
/* 1605 */       rewrite_AST_in.rewriteRefsDeep = new HashSet();
/*      */     }
/*      */ 
/*      */     try
/*      */     {
/*      */       while (true)
/*      */       {
/* 1613 */         if (_t == null) _t = ASTNULL;
/* 1614 */         if (_t.getType() != 80) break;
/* 1615 */         AST __t129 = _t;
/* 1616 */         GrammarAST tmp39_AST_in = (GrammarAST)_t;
/* 1617 */         match(_t, 80);
/* 1618 */         _t = _t.getFirstChild();
/*      */ 
/* 1620 */         if (_t == null) _t = ASTNULL;
/* 1621 */         switch (_t.getType())
/*      */         {
/*      */         case 69:
/* 1624 */           pred = (GrammarAST)_t;
/* 1625 */           match(_t, 69);
/* 1626 */           _t = _t.getNextSibling();
/* 1627 */           break;
/*      */         case 17:
/*      */         case 32:
/*      */         case 40:
/*      */         case 81:
/* 1634 */           break;
/*      */         default:
/* 1638 */           throw new NoViableAltException(_t);
/*      */         }
/*      */ 
/* 1642 */         rewrite_alternative(_t);
/* 1643 */         _t = this._retTree;
/* 1644 */         _t = __t129;
/* 1645 */         _t = _t.getNextSibling();
/* 1646 */         if (this.inputState.guessing == 0)
/*      */         {
/* 1648 */           if (pred != null) {
/* 1649 */             pred.outerAltNum = this.outerAltNum;
/* 1650 */             trackInlineAction(pred);
/*      */           }
/*      */ 
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (RecognitionException ex)
/*      */     {
/* 1663 */       if (this.inputState.guessing == 0) {
/* 1664 */         reportError(ex);
/* 1665 */         if (_t != null) _t = _t.getNextSibling(); 
/*      */       }
/* 1667 */       else { throw ex; }
/*      */ 
/*      */     }
/* 1670 */     this._retTree = _t;
/*      */   }
/*      */ 
/*      */   public final void element(AST _t) throws RecognitionException
/*      */   {
/* 1675 */     GrammarAST element_AST_in = _t == ASTNULL ? null : (GrammarAST)_t;
/* 1676 */     GrammarAST id = null;
/* 1677 */     GrammarAST el = null;
/* 1678 */     GrammarAST id2 = null;
/* 1679 */     GrammarAST a2 = null;
/* 1680 */     GrammarAST act = null;
/* 1681 */     GrammarAST act2 = null;
/*      */     try
/*      */     {
/* 1684 */       if (_t == null) _t = ASTNULL;
/* 1685 */       switch (_t.getType())
/*      */       {
/*      */       case 71:
/* 1688 */         AST __t95 = _t;
/* 1689 */         GrammarAST tmp40_AST_in = (GrammarAST)_t;
/* 1690 */         match(_t, 71);
/* 1691 */         _t = _t.getFirstChild();
/* 1692 */         element(_t);
/* 1693 */         _t = this._retTree;
/* 1694 */         _t = __t95;
/* 1695 */         _t = _t.getNextSibling();
/* 1696 */         break;
/*      */       case 59:
/* 1700 */         AST __t96 = _t;
/* 1701 */         GrammarAST tmp41_AST_in = (GrammarAST)_t;
/* 1702 */         match(_t, 59);
/* 1703 */         _t = _t.getFirstChild();
/* 1704 */         element(_t);
/* 1705 */         _t = this._retTree;
/* 1706 */         _t = __t96;
/* 1707 */         _t = _t.getNextSibling();
/* 1708 */         break;
/*      */       case 39:
/*      */       case 50:
/*      */       case 51:
/*      */       case 55:
/*      */       case 72:
/*      */       case 73:
/* 1717 */         atom(_t, null);
/* 1718 */         _t = this._retTree;
/* 1719 */         break;
/*      */       case 74:
/* 1723 */         AST __t97 = _t;
/* 1724 */         GrammarAST tmp42_AST_in = (GrammarAST)_t;
/* 1725 */         match(_t, 74);
/* 1726 */         _t = _t.getFirstChild();
/* 1727 */         element(_t);
/* 1728 */         _t = this._retTree;
/* 1729 */         _t = __t97;
/* 1730 */         _t = _t.getNextSibling();
/* 1731 */         break;
/*      */       case 14:
/* 1735 */         AST __t98 = _t;
/* 1736 */         GrammarAST tmp43_AST_in = (GrammarAST)_t;
/* 1737 */         match(_t, 14);
/* 1738 */         _t = _t.getFirstChild();
/* 1739 */         atom(_t, null);
/* 1740 */         _t = this._retTree;
/* 1741 */         atom(_t, null);
/* 1742 */         _t = this._retTree;
/* 1743 */         _t = __t98;
/* 1744 */         _t = _t.getNextSibling();
/* 1745 */         break;
/*      */       case 15:
/* 1749 */         AST __t99 = _t;
/* 1750 */         GrammarAST tmp44_AST_in = (GrammarAST)_t;
/* 1751 */         match(_t, 15);
/* 1752 */         _t = _t.getFirstChild();
/* 1753 */         atom(_t, null);
/* 1754 */         _t = this._retTree;
/* 1755 */         atom(_t, null);
/* 1756 */         _t = this._retTree;
/* 1757 */         _t = __t99;
/* 1758 */         _t = _t.getNextSibling();
/* 1759 */         break;
/*      */       case 49:
/* 1763 */         AST __t100 = _t;
/* 1764 */         GrammarAST tmp45_AST_in = (GrammarAST)_t;
/* 1765 */         match(_t, 49);
/* 1766 */         _t = _t.getFirstChild();
/* 1767 */         id = (GrammarAST)_t;
/* 1768 */         match(_t, 21);
/* 1769 */         _t = _t.getNextSibling();
/* 1770 */         el = _t == ASTNULL ? null : (GrammarAST)_t;
/* 1771 */         element(_t);
/* 1772 */         _t = this._retTree;
/* 1773 */         _t = __t100;
/* 1774 */         _t = _t.getNextSibling();
/* 1775 */         if (this.inputState.guessing == 0)
/*      */         {
/* 1777 */           if ((el.getType() == 71) || (el.getType() == 59))
/*      */           {
/* 1780 */             el = (GrammarAST)el.getFirstChild();
/*      */           }
/* 1782 */           if (el.getType() == 73) {
/* 1783 */             this.grammar.defineRuleRefLabel(this.currentRuleName, id.getToken(), el);
/*      */           }
/* 1785 */           else if ((el.getType() == 72) && (this.grammar.type == 3)) {
/* 1786 */             this.grammar.defineWildcardTreeLabel(this.currentRuleName, id.getToken(), el);
/*      */           }
/*      */           else
/* 1789 */             this.grammar.defineTokenRefLabel(this.currentRuleName, id.getToken(), el);  } break;
/*      */       case 68:
/* 1797 */         AST __t101 = _t;
/* 1798 */         GrammarAST tmp46_AST_in = (GrammarAST)_t;
/* 1799 */         match(_t, 68);
/* 1800 */         _t = _t.getFirstChild();
/* 1801 */         id2 = (GrammarAST)_t;
/* 1802 */         match(_t, 21);
/* 1803 */         _t = _t.getNextSibling();
/* 1804 */         a2 = _t == ASTNULL ? null : (GrammarAST)_t;
/* 1805 */         element(_t);
/* 1806 */         _t = this._retTree;
/* 1807 */         if (this.inputState.guessing == 0)
/*      */         {
/* 1809 */           if ((a2.getType() == 71) || (a2.getType() == 59))
/*      */           {
/* 1812 */             a2 = (GrammarAST)a2.getFirstChild();
/*      */           }
/* 1814 */           if (a2.getType() == 73) {
/* 1815 */             this.grammar.defineRuleListLabel(this.currentRuleName, id2.getToken(), a2);
/*      */           }
/* 1817 */           else if ((a2.getType() == 72) && (this.grammar.type == 3)) {
/* 1818 */             this.grammar.defineWildcardTreeListLabel(this.currentRuleName, id2.getToken(), a2);
/*      */           }
/*      */           else {
/* 1821 */             this.grammar.defineTokenListLabel(this.currentRuleName, id2.getToken(), a2);
/*      */           }
/*      */         }
/*      */ 
/* 1825 */         _t = __t101;
/* 1826 */         _t = _t.getNextSibling();
/* 1827 */         break;
/*      */       case 9:
/*      */       case 10:
/*      */       case 11:
/*      */       case 12:
/* 1834 */         ebnf(_t);
/* 1835 */         _t = this._retTree;
/* 1836 */         break;
/*      */       case 75:
/* 1840 */         tree(_t);
/* 1841 */         _t = this._retTree;
/* 1842 */         break;
/*      */       case 13:
/* 1846 */         AST __t102 = _t;
/* 1847 */         GrammarAST tmp47_AST_in = (GrammarAST)_t;
/* 1848 */         match(_t, 13);
/* 1849 */         _t = _t.getFirstChild();
/* 1850 */         block(_t);
/* 1851 */         _t = this._retTree;
/* 1852 */         _t = __t102;
/* 1853 */         _t = _t.getNextSibling();
/* 1854 */         break;
/*      */       case 40:
/* 1858 */         act = (GrammarAST)_t;
/* 1859 */         match(_t, 40);
/* 1860 */         _t = _t.getNextSibling();
/* 1861 */         if (this.inputState.guessing == 0)
/*      */         {
/* 1863 */           act.outerAltNum = this.outerAltNum;
/* 1864 */           trackInlineAction(act); } break;
/*      */       case 30:
/* 1871 */         act2 = (GrammarAST)_t;
/* 1872 */         match(_t, 30);
/* 1873 */         _t = _t.getNextSibling();
/* 1874 */         if (this.inputState.guessing == 0)
/*      */         {
/* 1876 */           act2.outerAltNum = this.outerAltNum;
/* 1877 */           trackInlineAction(act2); } break;
/*      */       case 69:
/* 1884 */         GrammarAST tmp48_AST_in = (GrammarAST)_t;
/* 1885 */         match(_t, 69);
/* 1886 */         _t = _t.getNextSibling();
/* 1887 */         if (this.inputState.guessing == 0)
/*      */         {
/* 1889 */           tmp48_AST_in.outerAltNum = this.outerAltNum;
/* 1890 */           trackInlineAction(tmp48_AST_in); } break;
/*      */       case 36:
/* 1897 */         GrammarAST tmp49_AST_in = (GrammarAST)_t;
/* 1898 */         match(_t, 36);
/* 1899 */         _t = _t.getNextSibling();
/* 1900 */         break;
/*      */       case 37:
/* 1904 */         GrammarAST tmp50_AST_in = (GrammarAST)_t;
/* 1905 */         match(_t, 37);
/* 1906 */         _t = _t.getNextSibling();
/* 1907 */         break;
/*      */       case 35:
/* 1911 */         GrammarAST tmp51_AST_in = (GrammarAST)_t;
/* 1912 */         match(_t, 35);
/* 1913 */         _t = _t.getNextSibling();
/* 1914 */         if (this.inputState.guessing == 0)
/*      */         {
/* 1916 */           tmp51_AST_in.outerAltNum = this.outerAltNum;
/* 1917 */           trackInlineAction(tmp51_AST_in); } break;
/*      */       case 16:
/* 1924 */         GrammarAST tmp52_AST_in = (GrammarAST)_t;
/* 1925 */         match(_t, 16);
/* 1926 */         _t = _t.getNextSibling();
/* 1927 */         break;
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
/* 1931 */         throw new NoViableAltException(_t);
/*      */       }
/*      */     }
/*      */     catch (RecognitionException ex)
/*      */     {
/* 1936 */       if (this.inputState.guessing == 0) {
/* 1937 */         reportError(ex);
/* 1938 */         if (_t != null) _t = _t.getNextSibling(); 
/*      */       }
/* 1940 */       else { throw ex; }
/*      */ 
/*      */     }
/* 1943 */     this._retTree = _t;
/*      */   }
/*      */ 
/*      */   public final void exceptionHandler(AST _t) throws RecognitionException
/*      */   {
/* 1948 */     GrammarAST exceptionHandler_AST_in = _t == ASTNULL ? null : (GrammarAST)_t;
/*      */     try
/*      */     {
/* 1951 */       AST __t91 = _t;
/* 1952 */       GrammarAST tmp53_AST_in = (GrammarAST)_t;
/* 1953 */       match(_t, 66);
/* 1954 */       _t = _t.getFirstChild();
/* 1955 */       GrammarAST tmp54_AST_in = (GrammarAST)_t;
/* 1956 */       match(_t, 60);
/* 1957 */       _t = _t.getNextSibling();
/* 1958 */       GrammarAST tmp55_AST_in = (GrammarAST)_t;
/* 1959 */       match(_t, 40);
/* 1960 */       _t = _t.getNextSibling();
/* 1961 */       _t = __t91;
/* 1962 */       _t = _t.getNextSibling();
/* 1963 */       if (this.inputState.guessing == 0)
/* 1964 */         trackInlineAction(tmp55_AST_in);
/*      */     }
/*      */     catch (RecognitionException ex)
/*      */     {
/* 1968 */       if (this.inputState.guessing == 0) {
/* 1969 */         reportError(ex);
/* 1970 */         if (_t != null) _t = _t.getNextSibling(); 
/*      */       }
/* 1972 */       else { throw ex; }
/*      */ 
/*      */     }
/* 1975 */     this._retTree = _t;
/*      */   }
/*      */ 
/*      */   public final void finallyClause(AST _t) throws RecognitionException
/*      */   {
/* 1980 */     GrammarAST finallyClause_AST_in = _t == ASTNULL ? null : (GrammarAST)_t;
/*      */     try
/*      */     {
/* 1983 */       AST __t93 = _t;
/* 1984 */       GrammarAST tmp56_AST_in = (GrammarAST)_t;
/* 1985 */       match(_t, 67);
/* 1986 */       _t = _t.getFirstChild();
/* 1987 */       GrammarAST tmp57_AST_in = (GrammarAST)_t;
/* 1988 */       match(_t, 40);
/* 1989 */       _t = _t.getNextSibling();
/* 1990 */       _t = __t93;
/* 1991 */       _t = _t.getNextSibling();
/* 1992 */       if (this.inputState.guessing == 0)
/* 1993 */         trackInlineAction(tmp57_AST_in);
/*      */     }
/*      */     catch (RecognitionException ex)
/*      */     {
/* 1997 */       if (this.inputState.guessing == 0) {
/* 1998 */         reportError(ex);
/* 1999 */         if (_t != null) _t = _t.getNextSibling(); 
/*      */       }
/* 2001 */       else { throw ex; }
/*      */ 
/*      */     }
/* 2004 */     this._retTree = _t;
/*      */   }
/*      */ 
/*      */   public final void atom(AST _t, GrammarAST scope)
/*      */     throws RecognitionException
/*      */   {
/* 2011 */     GrammarAST atom_AST_in = _t == ASTNULL ? null : (GrammarAST)_t;
/* 2012 */     GrammarAST rr = null;
/* 2013 */     GrammarAST rarg = null;
/* 2014 */     GrammarAST t = null;
/* 2015 */     GrammarAST targ = null;
/* 2016 */     GrammarAST c = null;
/* 2017 */     GrammarAST s = null;
/*      */     try
/*      */     {
/* 2020 */       if (_t == null) _t = ASTNULL;
/* 2021 */       switch (_t.getType())
/*      */       {
/*      */       case 73:
/* 2024 */         AST __t121 = _t;
/* 2025 */         rr = _t == ASTNULL ? null : (GrammarAST)_t;
/* 2026 */         match(_t, 73);
/* 2027 */         _t = _t.getFirstChild();
/*      */ 
/* 2029 */         if (_t == null) _t = ASTNULL;
/* 2030 */         switch (_t.getType())
/*      */         {
/*      */         case 60:
/* 2033 */           rarg = (GrammarAST)_t;
/* 2034 */           match(_t, 60);
/* 2035 */           _t = _t.getNextSibling();
/* 2036 */           break;
/*      */         case 3:
/* 2040 */           break;
/*      */         default:
/* 2044 */           throw new NoViableAltException(_t);
/*      */         }
/*      */ 
/* 2048 */         _t = __t121;
/* 2049 */         _t = _t.getNextSibling();
/* 2050 */         if (this.inputState.guessing == 0)
/*      */         {
/* 2052 */           this.grammar.altReferencesRule(this.currentRuleName, scope, rr, this.outerAltNum);
/* 2053 */           if (rarg != null) {
/* 2054 */             rarg.outerAltNum = this.outerAltNum;
/* 2055 */             trackInlineAction(rarg); }  } break;
/*      */       case 55:
/* 2063 */         AST __t123 = _t;
/* 2064 */         t = _t == ASTNULL ? null : (GrammarAST)_t;
/* 2065 */         match(_t, 55);
/* 2066 */         _t = _t.getFirstChild();
/*      */ 
/* 2068 */         if (_t == null) _t = ASTNULL;
/* 2069 */         switch (_t.getType())
/*      */         {
/*      */         case 60:
/* 2072 */           targ = (GrammarAST)_t;
/* 2073 */           match(_t, 60);
/* 2074 */           _t = _t.getNextSibling();
/* 2075 */           break;
/*      */         case 3:
/* 2079 */           break;
/*      */         default:
/* 2083 */           throw new NoViableAltException(_t);
/*      */         }
/*      */ 
/* 2087 */         _t = __t123;
/* 2088 */         _t = _t.getNextSibling();
/* 2089 */         if (this.inputState.guessing == 0)
/*      */         {
/* 2091 */           if (targ != null) {
/* 2092 */             targ.outerAltNum = this.outerAltNum;
/* 2093 */             trackInlineAction(targ);
/*      */           }
/* 2095 */           if (this.grammar.type == 1) {
/* 2096 */             this.grammar.altReferencesRule(this.currentRuleName, scope, t, this.outerAltNum);
/*      */           }
/*      */           else
/* 2099 */             this.grammar.altReferencesTokenID(this.currentRuleName, t, this.outerAltNum);  } break;
/*      */       case 51:
/* 2107 */         c = (GrammarAST)_t;
/* 2108 */         match(_t, 51);
/* 2109 */         _t = _t.getNextSibling();
/* 2110 */         if (this.inputState.guessing == 0)
/*      */         {
/* 2112 */           if (this.grammar.type != 1) {
/* 2113 */             Rule rule = this.grammar.getRule(this.currentRuleName);
/* 2114 */             if (rule != null)
/* 2115 */               rule.trackTokenReferenceInAlt(c, this.outerAltNum); 
/*      */           }
/*      */         }
/* 2117 */         break;
/*      */       case 50:
/* 2124 */         s = (GrammarAST)_t;
/* 2125 */         match(_t, 50);
/* 2126 */         _t = _t.getNextSibling();
/* 2127 */         if (this.inputState.guessing == 0)
/*      */         {
/* 2129 */           if (this.grammar.type != 1) {
/* 2130 */             Rule rule = this.grammar.getRule(this.currentRuleName);
/* 2131 */             if (rule != null)
/* 2132 */               rule.trackTokenReferenceInAlt(s, this.outerAltNum); 
/*      */           }
/*      */         }
/* 2134 */         break;
/*      */       case 72:
/* 2141 */         GrammarAST tmp58_AST_in = (GrammarAST)_t;
/* 2142 */         match(_t, 72);
/* 2143 */         _t = _t.getNextSibling();
/* 2144 */         break;
/*      */       case 39:
/* 2148 */         AST __t125 = _t;
/* 2149 */         GrammarAST tmp59_AST_in = (GrammarAST)_t;
/* 2150 */         match(_t, 39);
/* 2151 */         _t = _t.getFirstChild();
/* 2152 */         GrammarAST tmp60_AST_in = (GrammarAST)_t;
/* 2153 */         match(_t, 21);
/* 2154 */         _t = _t.getNextSibling();
/* 2155 */         atom(_t, tmp60_AST_in);
/* 2156 */         _t = this._retTree;
/* 2157 */         _t = __t125;
/* 2158 */         _t = _t.getNextSibling();
/* 2159 */         break;
/*      */       default:
/* 2163 */         throw new NoViableAltException(_t);
/*      */       }
/*      */     }
/*      */     catch (RecognitionException ex)
/*      */     {
/* 2168 */       if (this.inputState.guessing == 0) {
/* 2169 */         reportError(ex);
/* 2170 */         if (_t != null) _t = _t.getNextSibling(); 
/*      */       }
/* 2172 */       else { throw ex; }
/*      */ 
/*      */     }
/* 2175 */     this._retTree = _t;
/*      */   }
/*      */ 
/*      */   public final void ebnf(AST _t) throws RecognitionException
/*      */   {
/* 2180 */     GrammarAST ebnf_AST_in = _t == ASTNULL ? null : (GrammarAST)_t;
/*      */     try
/*      */     {
/* 2183 */       if (_t == null) _t = ASTNULL;
/* 2184 */       switch (_t.getType())
/*      */       {
/*      */       case 9:
/* 2187 */         block(_t);
/* 2188 */         _t = this._retTree;
/* 2189 */         break;
/*      */       case 10:
/* 2193 */         AST __t106 = _t;
/* 2194 */         GrammarAST tmp61_AST_in = (GrammarAST)_t;
/* 2195 */         match(_t, 10);
/* 2196 */         _t = _t.getFirstChild();
/* 2197 */         block(_t);
/* 2198 */         _t = this._retTree;
/* 2199 */         _t = __t106;
/* 2200 */         _t = _t.getNextSibling();
/* 2201 */         break;
/*      */       default:
/* 2204 */         boolean synPredMatched105 = false;
/* 2205 */         if (_t == null) _t = ASTNULL;
/* 2206 */         if ((_t.getType() == 11) || (_t.getType() == 12)) {
/* 2207 */           AST __t105 = _t;
/* 2208 */           synPredMatched105 = true;
/* 2209 */           this.inputState.guessing += 1;
/*      */           try
/*      */           {
/* 2212 */             dotLoop(_t);
/* 2213 */             _t = this._retTree;
/*      */           }
/*      */           catch (RecognitionException pe)
/*      */           {
/* 2217 */             synPredMatched105 = false;
/*      */           }
/* 2219 */           _t = __t105;
/* 2220 */           this.inputState.guessing -= 1;
/*      */         }
/* 2222 */         if (synPredMatched105) {
/* 2223 */           dotLoop(_t);
/* 2224 */           _t = this._retTree;
/*      */         }
/* 2226 */         else if (_t.getType() == 11) {
/* 2227 */           AST __t107 = _t;
/* 2228 */           GrammarAST tmp62_AST_in = (GrammarAST)_t;
/* 2229 */           match(_t, 11);
/* 2230 */           _t = _t.getFirstChild();
/* 2231 */           block(_t);
/* 2232 */           _t = this._retTree;
/* 2233 */           _t = __t107;
/* 2234 */           _t = _t.getNextSibling();
/*      */         }
/* 2236 */         else if (_t.getType() == 12) {
/* 2237 */           AST __t108 = _t;
/* 2238 */           GrammarAST tmp63_AST_in = (GrammarAST)_t;
/* 2239 */           match(_t, 12);
/* 2240 */           _t = _t.getFirstChild();
/* 2241 */           block(_t);
/* 2242 */           _t = this._retTree;
/* 2243 */           _t = __t108;
/* 2244 */           _t = _t.getNextSibling();
/*      */         }
/*      */         else {
/* 2247 */           throw new NoViableAltException(_t);
/*      */         }
/*      */         break;
/*      */       }
/*      */     } catch (RecognitionException ex) {
/* 2252 */       if (this.inputState.guessing == 0) {
/* 2253 */         reportError(ex);
/* 2254 */         if (_t != null) _t = _t.getNextSibling(); 
/*      */       }
/* 2256 */       else { throw ex; }
/*      */ 
/*      */     }
/* 2259 */     this._retTree = _t;
/*      */   }
/*      */ 
/*      */   public final void tree(AST _t) throws RecognitionException
/*      */   {
/* 2264 */     GrammarAST tree_AST_in = _t == ASTNULL ? null : (GrammarAST)_t;
/*      */     try
/*      */     {
/* 2267 */       AST __t117 = _t;
/* 2268 */       GrammarAST tmp64_AST_in = (GrammarAST)_t;
/* 2269 */       match(_t, 75);
/* 2270 */       _t = _t.getFirstChild();
/* 2271 */       element(_t);
/* 2272 */       _t = this._retTree;
/*      */       while (true)
/*      */       {
/* 2276 */         if (_t == null) _t = ASTNULL;
/* 2277 */         if ((_t.getType() != 9) && (_t.getType() != 10) && (_t.getType() != 11) && (_t.getType() != 12) && (_t.getType() != 13) && (_t.getType() != 14) && (_t.getType() != 15) && (_t.getType() != 16) && (_t.getType() != 30) && (_t.getType() != 35) && (_t.getType() != 36) && (_t.getType() != 37) && (_t.getType() != 39) && (_t.getType() != 40) && (_t.getType() != 49) && (_t.getType() != 50) && (_t.getType() != 51) && (_t.getType() != 55) && (_t.getType() != 59) && (_t.getType() != 68) && (_t.getType() != 69) && (_t.getType() != 71) && (_t.getType() != 72) && (_t.getType() != 73) && (_t.getType() != 74) && (_t.getType() != 75)) break;
/* 2278 */         element(_t);
/* 2279 */         _t = this._retTree;
/*      */       }
/*      */ 
/* 2287 */       _t = __t117;
/* 2288 */       _t = _t.getNextSibling();
/*      */     }
/*      */     catch (RecognitionException ex) {
/* 2291 */       if (this.inputState.guessing == 0) {
/* 2292 */         reportError(ex);
/* 2293 */         if (_t != null) _t = _t.getNextSibling(); 
/*      */       }
/* 2295 */       else { throw ex; }
/*      */ 
/*      */     }
/* 2298 */     this._retTree = _t;
/*      */   }
/*      */ 
/*      */   public final void dotLoop(AST _t)
/*      */     throws RecognitionException
/*      */   {
/* 2305 */     GrammarAST dotLoop_AST_in = _t == ASTNULL ? null : (GrammarAST)_t;
/*      */ 
/* 2307 */     GrammarAST block = (GrammarAST)dotLoop_AST_in.getFirstChild();
/*      */     try
/*      */     {
/* 2312 */       if (_t == null) _t = ASTNULL;
/* 2313 */       switch (_t.getType())
/*      */       {
/*      */       case 11:
/* 2316 */         AST __t111 = _t;
/* 2317 */         GrammarAST tmp65_AST_in = (GrammarAST)_t;
/* 2318 */         match(_t, 11);
/* 2319 */         _t = _t.getFirstChild();
/* 2320 */         dotBlock(_t);
/* 2321 */         _t = this._retTree;
/* 2322 */         _t = __t111;
/* 2323 */         _t = _t.getNextSibling();
/* 2324 */         break;
/*      */       case 12:
/* 2328 */         AST __t112 = _t;
/* 2329 */         GrammarAST tmp66_AST_in = (GrammarAST)_t;
/* 2330 */         match(_t, 12);
/* 2331 */         _t = _t.getFirstChild();
/* 2332 */         dotBlock(_t);
/* 2333 */         _t = this._retTree;
/* 2334 */         _t = __t112;
/* 2335 */         _t = _t.getNextSibling();
/* 2336 */         break;
/*      */       default:
/* 2340 */         throw new NoViableAltException(_t);
/*      */       }
/*      */ 
/* 2344 */       if (this.inputState.guessing == 0)
/*      */       {
/* 2346 */         Map opts = new HashMap();
/* 2347 */         opts.put("greedy", "false");
/* 2348 */         if (this.grammar.type != 1)
/*      */         {
/* 2351 */           opts.put("k", Utils.integer(1));
/*      */         }
/* 2353 */         block.setOptions(this.grammar, opts);
/*      */       }
/*      */     }
/*      */     catch (RecognitionException ex)
/*      */     {
/* 2358 */       if (this.inputState.guessing == 0) {
/* 2359 */         reportError(ex);
/* 2360 */         if (_t != null) _t = _t.getNextSibling(); 
/*      */       }
/* 2362 */       else { throw ex; }
/*      */ 
/*      */     }
/* 2365 */     this._retTree = _t;
/*      */   }
/*      */ 
/*      */   public final void dotBlock(AST _t) throws RecognitionException
/*      */   {
/* 2370 */     GrammarAST dotBlock_AST_in = _t == ASTNULL ? null : (GrammarAST)_t;
/*      */     try
/*      */     {
/* 2373 */       AST __t114 = _t;
/* 2374 */       GrammarAST tmp67_AST_in = (GrammarAST)_t;
/* 2375 */       match(_t, 9);
/* 2376 */       _t = _t.getFirstChild();
/* 2377 */       AST __t115 = _t;
/* 2378 */       GrammarAST tmp68_AST_in = (GrammarAST)_t;
/* 2379 */       match(_t, 17);
/* 2380 */       _t = _t.getFirstChild();
/* 2381 */       GrammarAST tmp69_AST_in = (GrammarAST)_t;
/* 2382 */       match(_t, 72);
/* 2383 */       _t = _t.getNextSibling();
/* 2384 */       GrammarAST tmp70_AST_in = (GrammarAST)_t;
/* 2385 */       match(_t, 20);
/* 2386 */       _t = _t.getNextSibling();
/* 2387 */       _t = __t115;
/* 2388 */       _t = _t.getNextSibling();
/* 2389 */       GrammarAST tmp71_AST_in = (GrammarAST)_t;
/* 2390 */       match(_t, 19);
/* 2391 */       _t = _t.getNextSibling();
/* 2392 */       _t = __t114;
/* 2393 */       _t = _t.getNextSibling();
/*      */     }
/*      */     catch (RecognitionException ex) {
/* 2396 */       if (this.inputState.guessing == 0) {
/* 2397 */         reportError(ex);
/* 2398 */         if (_t != null) _t = _t.getNextSibling(); 
/*      */       }
/* 2400 */       else { throw ex; }
/*      */ 
/*      */     }
/* 2403 */     this._retTree = _t;
/*      */   }
/*      */ 
/*      */   public final void ast_suffix(AST _t) throws RecognitionException
/*      */   {
/* 2408 */     GrammarAST ast_suffix_AST_in = _t == ASTNULL ? null : (GrammarAST)_t;
/*      */     try
/*      */     {
/* 2411 */       if (_t == null) _t = ASTNULL;
/* 2412 */       switch (_t.getType())
/*      */       {
/*      */       case 71:
/* 2415 */         GrammarAST tmp72_AST_in = (GrammarAST)_t;
/* 2416 */         match(_t, 71);
/* 2417 */         _t = _t.getNextSibling();
/* 2418 */         break;
/*      */       case 59:
/* 2422 */         GrammarAST tmp73_AST_in = (GrammarAST)_t;
/* 2423 */         match(_t, 59);
/* 2424 */         _t = _t.getNextSibling();
/* 2425 */         break;
/*      */       default:
/* 2429 */         throw new NoViableAltException(_t);
/*      */       }
/*      */     }
/*      */     catch (RecognitionException ex)
/*      */     {
/* 2434 */       if (this.inputState.guessing == 0) {
/* 2435 */         reportError(ex);
/* 2436 */         if (_t != null) _t = _t.getNextSibling(); 
/*      */       }
/* 2438 */       else { throw ex; }
/*      */ 
/*      */     }
/* 2441 */     this._retTree = _t;
/*      */   }
/*      */ 
/*      */   public final void rewrite_alternative(AST _t) throws RecognitionException
/*      */   {
/* 2446 */     GrammarAST rewrite_alternative_AST_in = _t == ASTNULL ? null : (GrammarAST)_t;
/* 2447 */     GrammarAST a = null;
/*      */     try
/*      */     {
/* 2450 */       if (_t == null) _t = ASTNULL;
/* 2451 */       if ((_t.getType() == 17) && (this.grammar.buildAST())) {
/* 2452 */         AST __t135 = _t;
/* 2453 */         a = _t == ASTNULL ? null : (GrammarAST)_t;
/* 2454 */         match(_t, 17);
/* 2455 */         _t = _t.getFirstChild();
/*      */ 
/* 2457 */         if (_t == null) _t = ASTNULL;
/* 2458 */         switch (_t.getType())
/*      */         {
/*      */         case 10:
/*      */         case 11:
/*      */         case 12:
/*      */         case 31:
/*      */         case 40:
/*      */         case 50:
/*      */         case 51:
/*      */         case 55:
/*      */         case 73:
/*      */         case 75:
/* 2471 */           int _cnt138 = 0;
/*      */           while (true)
/*      */           {
/* 2474 */             if (_t == null) _t = ASTNULL;
/* 2475 */             if ((_t.getType() == 10) || (_t.getType() == 11) || (_t.getType() == 12) || (_t.getType() == 31) || (_t.getType() == 40) || (_t.getType() == 50) || (_t.getType() == 51) || (_t.getType() == 55) || (_t.getType() == 73) || (_t.getType() == 75)) {
/* 2476 */               rewrite_element(_t);
/* 2477 */               _t = this._retTree;
/*      */             }
/*      */             else {
/* 2480 */               if (_cnt138 >= 1) break; throw new NoViableAltException(_t);
/*      */             }
/*      */ 
/* 2483 */             _cnt138++;
/*      */           }
/*      */ 
/* 2486 */           break;
/*      */         case 16:
/* 2490 */           GrammarAST tmp74_AST_in = (GrammarAST)_t;
/* 2491 */           match(_t, 16);
/* 2492 */           _t = _t.getNextSibling();
/* 2493 */           break;
/*      */         default:
/* 2497 */           throw new NoViableAltException(_t);
/*      */         }
/*      */ 
/* 2501 */         GrammarAST tmp75_AST_in = (GrammarAST)_t;
/* 2502 */         match(_t, 20);
/* 2503 */         _t = _t.getNextSibling();
/* 2504 */         _t = __t135;
/* 2505 */         _t = _t.getNextSibling();
/*      */       }
/* 2507 */       else if (((_t.getType() == 17) || (_t.getType() == 32) || (_t.getType() == 40)) && (this.grammar.buildTemplate())) {
/* 2508 */         rewrite_template(_t);
/* 2509 */         _t = this._retTree;
/*      */       }
/* 2511 */       else if (_t.getType() == 81) {
/* 2512 */         GrammarAST tmp76_AST_in = (GrammarAST)_t;
/* 2513 */         match(_t, 81);
/* 2514 */         _t = _t.getNextSibling();
/* 2515 */         if (this.blockLevel != 1)
/* 2516 */           throw new SemanticException("this.blockLevel==1");
/*      */       }
/*      */       else {
/* 2519 */         throw new NoViableAltException(_t);
/*      */       }
/*      */     }
/*      */     catch (RecognitionException ex)
/*      */     {
/* 2524 */       if (this.inputState.guessing == 0) {
/* 2525 */         reportError(ex);
/* 2526 */         if (_t != null) _t = _t.getNextSibling(); 
/*      */       }
/* 2528 */       else { throw ex; }
/*      */ 
/*      */     }
/* 2531 */     this._retTree = _t;
/*      */   }
/*      */ 
/*      */   public final void rewrite_block(AST _t) throws RecognitionException
/*      */   {
/* 2536 */     GrammarAST rewrite_block_AST_in = _t == ASTNULL ? null : (GrammarAST)_t;
/*      */ 
/* 2538 */     GrammarAST enclosingBlock = this.currentRewriteBlock;
/* 2539 */     if (this.inputState.guessing == 0) {
/* 2540 */       this.currentRewriteBlock = rewrite_block_AST_in;
/* 2541 */       this.currentRewriteBlock.rewriteRefsShallow = new HashSet();
/* 2542 */       this.currentRewriteBlock.rewriteRefsDeep = new HashSet();
/*      */     }
/*      */ 
/*      */     try
/*      */     {
/* 2547 */       AST __t133 = _t;
/* 2548 */       GrammarAST tmp77_AST_in = (GrammarAST)_t;
/* 2549 */       match(_t, 9);
/* 2550 */       _t = _t.getFirstChild();
/* 2551 */       rewrite_alternative(_t);
/* 2552 */       _t = this._retTree;
/* 2553 */       GrammarAST tmp78_AST_in = (GrammarAST)_t;
/* 2554 */       match(_t, 19);
/* 2555 */       _t = _t.getNextSibling();
/* 2556 */       _t = __t133;
/* 2557 */       _t = _t.getNextSibling();
/* 2558 */       if (this.inputState.guessing == 0)
/*      */       {
/* 2561 */         if (enclosingBlock != null) {
/* 2562 */           enclosingBlock.rewriteRefsDeep.addAll(this.currentRewriteBlock.rewriteRefsShallow);
/*      */         }
/*      */ 
/* 2565 */         this.currentRewriteBlock = enclosingBlock;
/*      */       }
/*      */     }
/*      */     catch (RecognitionException ex)
/*      */     {
/* 2570 */       if (this.inputState.guessing == 0) {
/* 2571 */         reportError(ex);
/* 2572 */         if (_t != null) _t = _t.getNextSibling(); 
/*      */       }
/* 2574 */       else { throw ex; }
/*      */ 
/*      */     }
/* 2577 */     this._retTree = _t;
/*      */   }
/*      */ 
/*      */   public final void rewrite_element(AST _t) throws RecognitionException
/*      */   {
/* 2582 */     GrammarAST rewrite_element_AST_in = _t == ASTNULL ? null : (GrammarAST)_t;
/*      */     try
/*      */     {
/* 2585 */       if (_t == null) _t = ASTNULL;
/* 2586 */       switch (_t.getType())
/*      */       {
/*      */       case 31:
/*      */       case 40:
/*      */       case 50:
/*      */       case 51:
/*      */       case 55:
/*      */       case 73:
/* 2594 */         rewrite_atom(_t);
/* 2595 */         _t = this._retTree;
/* 2596 */         break;
/*      */       case 10:
/*      */       case 11:
/*      */       case 12:
/* 2602 */         rewrite_ebnf(_t);
/* 2603 */         _t = this._retTree;
/* 2604 */         break;
/*      */       case 75:
/* 2608 */         rewrite_tree(_t);
/* 2609 */         _t = this._retTree;
/* 2610 */         break;
/*      */       default:
/* 2614 */         throw new NoViableAltException(_t);
/*      */       }
/*      */     }
/*      */     catch (RecognitionException ex)
/*      */     {
/* 2619 */       if (this.inputState.guessing == 0) {
/* 2620 */         reportError(ex);
/* 2621 */         if (_t != null) _t = _t.getNextSibling(); 
/*      */       }
/* 2623 */       else { throw ex; }
/*      */ 
/*      */     }
/* 2626 */     this._retTree = _t;
/*      */   }
/*      */ 
/*      */   public final void rewrite_template(AST _t) throws RecognitionException
/*      */   {
/* 2631 */     GrammarAST rewrite_template_AST_in = _t == ASTNULL ? null : (GrammarAST)_t;
/* 2632 */     GrammarAST id = null;
/* 2633 */     GrammarAST ind = null;
/* 2634 */     GrammarAST arg = null;
/* 2635 */     GrammarAST a = null;
/* 2636 */     GrammarAST act = null;
/*      */     try
/*      */     {
/* 2639 */       if (_t == null) _t = ASTNULL;
/* 2640 */       switch (_t.getType())
/*      */       {
/*      */       case 17:
/* 2643 */         AST __t153 = _t;
/* 2644 */         GrammarAST tmp79_AST_in = (GrammarAST)_t;
/* 2645 */         match(_t, 17);
/* 2646 */         _t = _t.getFirstChild();
/* 2647 */         GrammarAST tmp80_AST_in = (GrammarAST)_t;
/* 2648 */         match(_t, 16);
/* 2649 */         _t = _t.getNextSibling();
/* 2650 */         GrammarAST tmp81_AST_in = (GrammarAST)_t;
/* 2651 */         match(_t, 20);
/* 2652 */         _t = _t.getNextSibling();
/* 2653 */         _t = __t153;
/* 2654 */         _t = _t.getNextSibling();
/* 2655 */         break;
/*      */       case 32:
/* 2659 */         AST __t154 = _t;
/* 2660 */         GrammarAST tmp82_AST_in = (GrammarAST)_t;
/* 2661 */         match(_t, 32);
/* 2662 */         _t = _t.getFirstChild();
/*      */ 
/* 2664 */         if (_t == null) _t = ASTNULL;
/* 2665 */         switch (_t.getType())
/*      */         {
/*      */         case 21:
/* 2668 */           id = (GrammarAST)_t;
/* 2669 */           match(_t, 21);
/* 2670 */           _t = _t.getNextSibling();
/* 2671 */           break;
/*      */         case 40:
/* 2675 */           ind = (GrammarAST)_t;
/* 2676 */           match(_t, 40);
/* 2677 */           _t = _t.getNextSibling();
/* 2678 */           break;
/*      */         default:
/* 2682 */           throw new NoViableAltException(_t);
/*      */         }
/*      */ 
/* 2686 */         AST __t156 = _t;
/* 2687 */         GrammarAST tmp83_AST_in = (GrammarAST)_t;
/* 2688 */         match(_t, 23);
/* 2689 */         _t = _t.getFirstChild();
/*      */         while (true)
/*      */         {
/* 2693 */           if (_t == null) _t = ASTNULL;
/* 2694 */           if (_t.getType() != 22) break;
/* 2695 */           AST __t158 = _t;
/* 2696 */           GrammarAST tmp84_AST_in = (GrammarAST)_t;
/* 2697 */           match(_t, 22);
/* 2698 */           _t = _t.getFirstChild();
/* 2699 */           arg = (GrammarAST)_t;
/* 2700 */           match(_t, 21);
/* 2701 */           _t = _t.getNextSibling();
/* 2702 */           a = (GrammarAST)_t;
/* 2703 */           match(_t, 40);
/* 2704 */           _t = _t.getNextSibling();
/* 2705 */           _t = __t158;
/* 2706 */           _t = _t.getNextSibling();
/* 2707 */           if (this.inputState.guessing == 0)
/*      */           {
/* 2709 */             a.outerAltNum = this.outerAltNum;
/* 2710 */             trackInlineAction(a);
/*      */           }
/*      */ 
/*      */         }
/*      */ 
/* 2720 */         _t = __t156;
/* 2721 */         _t = _t.getNextSibling();
/* 2722 */         if (this.inputState.guessing == 0)
/*      */         {
/* 2724 */           if (ind != null) {
/* 2725 */             ind.outerAltNum = this.outerAltNum;
/* 2726 */             trackInlineAction(ind);
/*      */           }
/*      */ 
/*      */         }
/*      */ 
/* 2731 */         if (_t == null) _t = ASTNULL;
/* 2732 */         switch (_t.getType())
/*      */         {
/*      */         case 83:
/* 2735 */           GrammarAST tmp85_AST_in = (GrammarAST)_t;
/* 2736 */           match(_t, 83);
/* 2737 */           _t = _t.getNextSibling();
/* 2738 */           break;
/*      */         case 84:
/* 2742 */           GrammarAST tmp86_AST_in = (GrammarAST)_t;
/* 2743 */           match(_t, 84);
/* 2744 */           _t = _t.getNextSibling();
/* 2745 */           break;
/*      */         case 3:
/* 2749 */           break;
/*      */         default:
/* 2753 */           throw new NoViableAltException(_t);
/*      */         }
/*      */ 
/* 2757 */         _t = __t154;
/* 2758 */         _t = _t.getNextSibling();
/* 2759 */         break;
/*      */       case 40:
/* 2763 */         act = (GrammarAST)_t;
/* 2764 */         match(_t, 40);
/* 2765 */         _t = _t.getNextSibling();
/* 2766 */         if (this.inputState.guessing == 0)
/*      */         {
/* 2768 */           act.outerAltNum = this.outerAltNum;
/* 2769 */           trackInlineAction(act); } break;
/*      */       default:
/* 2776 */         throw new NoViableAltException(_t);
/*      */       }
/*      */     }
/*      */     catch (RecognitionException ex)
/*      */     {
/* 2781 */       if (this.inputState.guessing == 0) {
/* 2782 */         reportError(ex);
/* 2783 */         if (_t != null) _t = _t.getNextSibling(); 
/*      */       }
/* 2785 */       else { throw ex; }
/*      */ 
/*      */     }
/* 2788 */     this._retTree = _t;
/*      */   }
/*      */ 
/*      */   public final void rewrite_atom(AST _t) throws RecognitionException
/*      */   {
/* 2793 */     GrammarAST rewrite_atom_AST_in = _t == ASTNULL ? null : (GrammarAST)_t;
/* 2794 */     GrammarAST arg = null;
/*      */ 
/* 2796 */     Rule r = this.grammar.getRule(this.currentRuleName);
/* 2797 */     Set tokenRefsInAlt = r.getTokenRefsInAlt(this.outerAltNum);
/* 2798 */     boolean imaginary = (rewrite_atom_AST_in.getType() == 55) && (!tokenRefsInAlt.contains(rewrite_atom_AST_in.getText()));
/*      */ 
/* 2801 */     if ((!imaginary) && (this.grammar.buildAST()) && ((rewrite_atom_AST_in.getType() == 73) || (rewrite_atom_AST_in.getType() == 31) || (rewrite_atom_AST_in.getType() == 55) || (rewrite_atom_AST_in.getType() == 51) || (rewrite_atom_AST_in.getType() == 50)))
/*      */     {
/* 2809 */       if (this.currentRewriteBlock != null) {
/* 2810 */         this.currentRewriteBlock.rewriteRefsShallow.add(rewrite_atom_AST_in);
/* 2811 */         this.currentRewriteBlock.rewriteRefsDeep.add(rewrite_atom_AST_in);
/*      */       }
/* 2813 */       this.currentRewriteRule.rewriteRefsDeep.add(rewrite_atom_AST_in);
/*      */     }
/*      */ 
/*      */     try
/*      */     {
/* 2818 */       if (_t == null) _t = ASTNULL;
/* 2819 */       switch (_t.getType())
/*      */       {
/*      */       case 73:
/* 2822 */         GrammarAST tmp87_AST_in = (GrammarAST)_t;
/* 2823 */         match(_t, 73);
/* 2824 */         _t = _t.getNextSibling();
/* 2825 */         break;
/*      */       case 50:
/*      */       case 51:
/*      */       case 55:
/* 2832 */         if (_t == null) _t = ASTNULL;
/* 2833 */         switch (_t.getType())
/*      */         {
/*      */         case 55:
/* 2836 */           AST __t150 = _t;
/* 2837 */           GrammarAST tmp88_AST_in = (GrammarAST)_t;
/* 2838 */           match(_t, 55);
/* 2839 */           _t = _t.getFirstChild();
/*      */ 
/* 2841 */           if (_t == null) _t = ASTNULL;
/* 2842 */           switch (_t.getType())
/*      */           {
/*      */           case 60:
/* 2845 */             arg = (GrammarAST)_t;
/* 2846 */             match(_t, 60);
/* 2847 */             _t = _t.getNextSibling();
/* 2848 */             break;
/*      */           case 3:
/* 2852 */             break;
/*      */           default:
/* 2856 */             throw new NoViableAltException(_t);
/*      */           }
/*      */ 
/* 2860 */           _t = __t150;
/* 2861 */           _t = _t.getNextSibling();
/* 2862 */           break;
/*      */         case 51:
/* 2866 */           GrammarAST tmp89_AST_in = (GrammarAST)_t;
/* 2867 */           match(_t, 51);
/* 2868 */           _t = _t.getNextSibling();
/* 2869 */           break;
/*      */         case 50:
/* 2873 */           GrammarAST tmp90_AST_in = (GrammarAST)_t;
/* 2874 */           match(_t, 50);
/* 2875 */           _t = _t.getNextSibling();
/* 2876 */           break;
/*      */         default:
/* 2880 */           throw new NoViableAltException(_t);
/*      */         }
/*      */ 
/* 2884 */         if (this.inputState.guessing == 0)
/*      */         {
/* 2886 */           if (arg != null) {
/* 2887 */             arg.outerAltNum = this.outerAltNum;
/* 2888 */             trackInlineAction(arg); }  } break;
/*      */       case 31:
/* 2896 */         GrammarAST tmp91_AST_in = (GrammarAST)_t;
/* 2897 */         match(_t, 31);
/* 2898 */         _t = _t.getNextSibling();
/* 2899 */         break;
/*      */       case 40:
/* 2903 */         GrammarAST tmp92_AST_in = (GrammarAST)_t;
/* 2904 */         match(_t, 40);
/* 2905 */         _t = _t.getNextSibling();
/* 2906 */         if (this.inputState.guessing == 0)
/*      */         {
/* 2908 */           tmp92_AST_in.outerAltNum = this.outerAltNum;
/* 2909 */           trackInlineAction(tmp92_AST_in); } break;
/*      */       default:
/* 2916 */         throw new NoViableAltException(_t);
/*      */       }
/*      */     }
/*      */     catch (RecognitionException ex)
/*      */     {
/* 2921 */       if (this.inputState.guessing == 0) {
/* 2922 */         reportError(ex);
/* 2923 */         if (_t != null) _t = _t.getNextSibling(); 
/*      */       }
/* 2925 */       else { throw ex; }
/*      */ 
/*      */     }
/* 2928 */     this._retTree = _t;
/*      */   }
/*      */ 
/*      */   public final void rewrite_ebnf(AST _t) throws RecognitionException
/*      */   {
/* 2933 */     GrammarAST rewrite_ebnf_AST_in = _t == ASTNULL ? null : (GrammarAST)_t;
/*      */     try
/*      */     {
/* 2936 */       if (_t == null) _t = ASTNULL;
/* 2937 */       switch (_t.getType())
/*      */       {
/*      */       case 10:
/* 2940 */         AST __t141 = _t;
/* 2941 */         GrammarAST tmp93_AST_in = (GrammarAST)_t;
/* 2942 */         match(_t, 10);
/* 2943 */         _t = _t.getFirstChild();
/* 2944 */         rewrite_block(_t);
/* 2945 */         _t = this._retTree;
/* 2946 */         _t = __t141;
/* 2947 */         _t = _t.getNextSibling();
/* 2948 */         break;
/*      */       case 11:
/* 2952 */         AST __t142 = _t;
/* 2953 */         GrammarAST tmp94_AST_in = (GrammarAST)_t;
/* 2954 */         match(_t, 11);
/* 2955 */         _t = _t.getFirstChild();
/* 2956 */         rewrite_block(_t);
/* 2957 */         _t = this._retTree;
/* 2958 */         _t = __t142;
/* 2959 */         _t = _t.getNextSibling();
/* 2960 */         break;
/*      */       case 12:
/* 2964 */         AST __t143 = _t;
/* 2965 */         GrammarAST tmp95_AST_in = (GrammarAST)_t;
/* 2966 */         match(_t, 12);
/* 2967 */         _t = _t.getFirstChild();
/* 2968 */         rewrite_block(_t);
/* 2969 */         _t = this._retTree;
/* 2970 */         _t = __t143;
/* 2971 */         _t = _t.getNextSibling();
/* 2972 */         break;
/*      */       default:
/* 2976 */         throw new NoViableAltException(_t);
/*      */       }
/*      */     }
/*      */     catch (RecognitionException ex)
/*      */     {
/* 2981 */       if (this.inputState.guessing == 0) {
/* 2982 */         reportError(ex);
/* 2983 */         if (_t != null) _t = _t.getNextSibling(); 
/*      */       }
/* 2985 */       else { throw ex; }
/*      */ 
/*      */     }
/* 2988 */     this._retTree = _t;
/*      */   }
/*      */ 
/*      */   public final void rewrite_tree(AST _t) throws RecognitionException
/*      */   {
/* 2993 */     GrammarAST rewrite_tree_AST_in = _t == ASTNULL ? null : (GrammarAST)_t;
/*      */     try
/*      */     {
/* 2996 */       AST __t145 = _t;
/* 2997 */       GrammarAST tmp96_AST_in = (GrammarAST)_t;
/* 2998 */       match(_t, 75);
/* 2999 */       _t = _t.getFirstChild();
/* 3000 */       rewrite_atom(_t);
/* 3001 */       _t = this._retTree;
/*      */       while (true)
/*      */       {
/* 3005 */         if (_t == null) _t = ASTNULL;
/* 3006 */         if ((_t.getType() != 10) && (_t.getType() != 11) && (_t.getType() != 12) && (_t.getType() != 31) && (_t.getType() != 40) && (_t.getType() != 50) && (_t.getType() != 51) && (_t.getType() != 55) && (_t.getType() != 73) && (_t.getType() != 75)) break;
/* 3007 */         rewrite_element(_t);
/* 3008 */         _t = this._retTree;
/*      */       }
/*      */ 
/* 3016 */       _t = __t145;
/* 3017 */       _t = _t.getNextSibling();
/*      */     }
/*      */     catch (RecognitionException ex) {
/* 3020 */       if (this.inputState.guessing == 0) {
/* 3021 */         reportError(ex);
/* 3022 */         if (_t != null) _t = _t.getNextSibling(); 
/*      */       }
/* 3024 */       else { throw ex; }
/*      */ 
/*      */     }
/* 3027 */     this._retTree = _t;
/*      */   }
/*      */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.antlr.grammar.v2.DefineGrammarItemsWalker
 * JD-Core Version:    0.6.2
 */