/*      */ package org.antlr.grammar.v2;
/*      */ 
/*      */ import antlr.MismatchedTokenException;
/*      */ import antlr.NoViableAltException;
/*      */ import antlr.RecognitionException;
/*      */ import antlr.Token;
/*      */ import antlr.TreeParser;
/*      */ import antlr.collections.AST;
/*      */ import java.util.StringTokenizer;
/*      */ import org.antlr.tool.ErrorManager;
/*      */ import org.antlr.tool.Grammar;
/*      */ import org.antlr.tool.GrammarAST;
/*      */ 
/*      */ public class ANTLRTreePrinter extends TreeParser
/*      */   implements ANTLRTreePrinterTokenTypes
/*      */ {
/*      */   protected Grammar grammar;
/*      */   protected boolean showActions;
/*   59 */   protected StringBuffer buf = new StringBuffer(300);
/*      */ 
/* 2313 */   public static final String[] _tokenNames = { "<0>", "EOF", "<2>", "NULL_TREE_LOOKAHEAD", "\"options\"", "\"tokens\"", "\"parser\"", "LEXER", "RULE", "BLOCK", "OPTIONAL", "CLOSURE", "POSITIVE_CLOSURE", "SYNPRED", "RANGE", "CHAR_RANGE", "EPSILON", "ALT", "EOR", "EOB", "EOA", "ID", "ARG", "ARGLIST", "RET", "LEXER_GRAMMAR", "PARSER_GRAMMAR", "TREE_GRAMMAR", "COMBINED_GRAMMAR", "INITACTION", "FORCED_ACTION", "LABEL", "TEMPLATE", "\"scope\"", "\"import\"", "GATED_SEMPRED", "SYN_SEMPRED", "BACKTRACK_SEMPRED", "\"fragment\"", "DOT", "ACTION", "DOC_COMMENT", "SEMI", "\"lexer\"", "\"tree\"", "\"grammar\"", "AMPERSAND", "COLON", "RCURLY", "ASSIGN", "STRING_LITERAL", "CHAR_LITERAL", "INT", "STAR", "COMMA", "TOKEN_REF", "\"protected\"", "\"public\"", "\"private\"", "BANG", "ARG_ACTION", "\"returns\"", "\"throws\"", "LPAREN", "OR", "RPAREN", "\"catch\"", "\"finally\"", "PLUS_ASSIGN", "SEMPRED", "IMPLIES", "ROOT", "WILDCARD", "RULE_REF", "NOT", "TREE_BEGIN", "QUESTION", "PLUS", "OPEN_ELEMENT_OPTION", "CLOSE_ELEMENT_OPTION", "REWRITE", "ETC", "DOLLAR", "DOUBLE_QUOTE_STRING_LITERAL", "DOUBLE_ANGLE_STRING_LITERAL", "WS", "COMMENT", "SL_COMMENT", "ML_COMMENT", "STRAY_BRACKET", "ESC", "DIGIT", "XDIGIT", "NESTED_ARG_ACTION", "NESTED_ACTION", "ACTION_CHAR_LITERAL", "ACTION_STRING_LITERAL", "ACTION_ESC", "WS_LOOP", "INTERNAL_RULE_REF", "WS_OPT", "SRC" };
/*      */ 
/*      */   public void out(String s)
/*      */   {
/*   62 */     this.buf.append(s);
/*      */   }
/*      */ 
/*      */   public void reportError(RecognitionException ex) {
/*   66 */     Token token = null;
/*   67 */     if ((ex instanceof MismatchedTokenException)) {
/*   68 */       token = ((MismatchedTokenException)ex).token;
/*      */     }
/*   70 */     else if ((ex instanceof NoViableAltException)) {
/*   71 */       token = ((NoViableAltException)ex).token;
/*      */     }
/*   73 */     ErrorManager.syntaxError(100, this.grammar, token, "antlr.print: " + ex.toString(), ex);
/*      */   }
/*      */ 
/*      */   public static String normalize(String g)
/*      */   {
/*   91 */     StringTokenizer st = new StringTokenizer(g, " ", false);
/*   92 */     StringBuffer buf = new StringBuffer();
/*   93 */     while (st.hasMoreTokens()) {
/*   94 */       String w = st.nextToken();
/*   95 */       buf.append(w);
/*   96 */       buf.append(" ");
/*      */     }
/*   98 */     return buf.toString().trim();
/*      */   }
/*      */   public ANTLRTreePrinter() {
/*  101 */     this.tokenNames = _tokenNames;
/*      */   }
/*      */ 
/*      */   public final String toString(AST _t, Grammar g, boolean showActions)
/*      */     throws RecognitionException
/*      */   {
/*  108 */     String s = null;
/*      */ 
/*  110 */     GrammarAST toString_AST_in = _t == ASTNULL ? null : (GrammarAST)_t;
/*      */ 
/*  112 */     this.grammar = g;
/*  113 */     this.showActions = showActions;
/*      */     try
/*      */     {
/*  118 */       if (_t == null) _t = ASTNULL;
/*  119 */       switch (_t.getType())
/*      */       {
/*      */       case 25:
/*      */       case 26:
/*      */       case 27:
/*      */       case 28:
/*  125 */         grammar(_t);
/*  126 */         _t = this._retTree;
/*  127 */         break;
/*      */       case 8:
/*  131 */         rule(_t);
/*  132 */         _t = this._retTree;
/*  133 */         break;
/*      */       case 17:
/*  137 */         alternative(_t);
/*  138 */         _t = this._retTree;
/*  139 */         break;
/*      */       case 9:
/*      */       case 10:
/*      */       case 11:
/*      */       case 12:
/*      */       case 13:
/*      */       case 14:
/*      */       case 15:
/*      */       case 16:
/*      */       case 30:
/*      */       case 31:
/*      */       case 35:
/*      */       case 36:
/*      */       case 37:
/*      */       case 39:
/*      */       case 40:
/*      */       case 49:
/*      */       case 50:
/*      */       case 51:
/*      */       case 55:
/*      */       case 59:
/*      */       case 68:
/*      */       case 69:
/*      */       case 71:
/*      */       case 72:
/*      */       case 73:
/*      */       case 74:
/*      */       case 75:
/*  169 */         element(_t);
/*  170 */         _t = this._retTree;
/*  171 */         break;
/*      */       case 80:
/*  175 */         single_rewrite(_t);
/*  176 */         _t = this._retTree;
/*  177 */         break;
/*      */       case 18:
/*  181 */         GrammarAST tmp1_AST_in = (GrammarAST)_t;
/*  182 */         match(_t, 18);
/*  183 */         _t = _t.getNextSibling();
/*  184 */         s = "EOR";
/*  185 */         break;
/*      */       case 19:
/*      */       case 20:
/*      */       case 21:
/*      */       case 22:
/*      */       case 23:
/*      */       case 24:
/*      */       case 29:
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
/*      */       case 76:
/*      */       case 77:
/*      */       case 78:
/*      */       case 79:
/*      */       default:
/*  189 */         throw new NoViableAltException(_t);
/*      */       }
/*      */ 
/*  193 */       return normalize(this.buf.toString());
/*      */     }
/*      */     catch (RecognitionException ex) {
/*  196 */       reportError(ex);
/*  197 */       if (_t != null) _t = _t.getNextSibling();
/*      */ 
/*  199 */       this._retTree = _t;
/*  200 */     }return s;
/*      */   }
/*      */ 
/*      */   public final void grammar(AST _t) throws RecognitionException
/*      */   {
/*  205 */     GrammarAST grammar_AST_in = _t == ASTNULL ? null : (GrammarAST)_t;
/*      */     try
/*      */     {
/*  209 */       if (_t == null) _t = ASTNULL;
/*  210 */       switch (_t.getType())
/*      */       {
/*      */       case 25:
/*  213 */         AST __t5 = _t;
/*  214 */         GrammarAST tmp2_AST_in = (GrammarAST)_t;
/*  215 */         match(_t, 25);
/*  216 */         _t = _t.getFirstChild();
/*  217 */         grammarSpec(_t, "lexer ");
/*  218 */         _t = this._retTree;
/*  219 */         _t = __t5;
/*  220 */         _t = _t.getNextSibling();
/*  221 */         break;
/*      */       case 26:
/*  225 */         AST __t6 = _t;
/*  226 */         GrammarAST tmp3_AST_in = (GrammarAST)_t;
/*  227 */         match(_t, 26);
/*  228 */         _t = _t.getFirstChild();
/*  229 */         grammarSpec(_t, "parser ");
/*  230 */         _t = this._retTree;
/*  231 */         _t = __t6;
/*  232 */         _t = _t.getNextSibling();
/*  233 */         break;
/*      */       case 27:
/*  237 */         AST __t7 = _t;
/*  238 */         GrammarAST tmp4_AST_in = (GrammarAST)_t;
/*  239 */         match(_t, 27);
/*  240 */         _t = _t.getFirstChild();
/*  241 */         grammarSpec(_t, "tree ");
/*  242 */         _t = this._retTree;
/*  243 */         _t = __t7;
/*  244 */         _t = _t.getNextSibling();
/*  245 */         break;
/*      */       case 28:
/*  249 */         AST __t8 = _t;
/*  250 */         GrammarAST tmp5_AST_in = (GrammarAST)_t;
/*  251 */         match(_t, 28);
/*  252 */         _t = _t.getFirstChild();
/*  253 */         grammarSpec(_t, "");
/*  254 */         _t = this._retTree;
/*  255 */         _t = __t8;
/*  256 */         _t = _t.getNextSibling();
/*  257 */         break;
/*      */       default:
/*  261 */         throw new NoViableAltException(_t);
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (RecognitionException ex)
/*      */     {
/*  267 */       reportError(ex);
/*  268 */       if (_t != null) _t = _t.getNextSibling();
/*      */     }
/*  270 */     this._retTree = _t;
/*      */   }
/*      */ 
/*      */   public final void rule(AST _t) throws RecognitionException
/*      */   {
/*  275 */     GrammarAST rule_AST_in = _t == ASTNULL ? null : (GrammarAST)_t;
/*  276 */     GrammarAST id = null;
/*  277 */     GrammarAST arg = null;
/*  278 */     GrammarAST ret = null;
/*  279 */     GrammarAST b = null;
/*      */     try
/*      */     {
/*  282 */       AST __t48 = _t;
/*  283 */       GrammarAST tmp6_AST_in = (GrammarAST)_t;
/*  284 */       match(_t, 8);
/*  285 */       _t = _t.getFirstChild();
/*  286 */       id = (GrammarAST)_t;
/*  287 */       match(_t, 21);
/*  288 */       _t = _t.getNextSibling();
/*      */ 
/*  290 */       if (_t == null) _t = ASTNULL;
/*  291 */       switch (_t.getType())
/*      */       {
/*      */       case 38:
/*      */       case 56:
/*      */       case 57:
/*      */       case 58:
/*  297 */         modifier(_t);
/*  298 */         _t = this._retTree;
/*  299 */         break;
/*      */       case 22:
/*  303 */         break;
/*      */       default:
/*  307 */         throw new NoViableAltException(_t);
/*      */       }
/*      */ 
/*  311 */       out(id.getText());
/*  312 */       AST __t50 = _t;
/*  313 */       GrammarAST tmp7_AST_in = (GrammarAST)_t;
/*  314 */       match(_t, 22);
/*  315 */       _t = _t.getFirstChild();
/*      */ 
/*  317 */       if (_t == null) _t = ASTNULL;
/*  318 */       switch (_t.getType())
/*      */       {
/*      */       case 60:
/*  321 */         arg = (GrammarAST)_t;
/*  322 */         match(_t, 60);
/*  323 */         _t = _t.getNextSibling();
/*  324 */         out("[" + arg.getText() + "]");
/*  325 */         break;
/*      */       case 3:
/*  329 */         break;
/*      */       default:
/*  333 */         throw new NoViableAltException(_t);
/*      */       }
/*      */ 
/*  337 */       _t = __t50;
/*  338 */       _t = _t.getNextSibling();
/*  339 */       AST __t52 = _t;
/*  340 */       GrammarAST tmp8_AST_in = (GrammarAST)_t;
/*  341 */       match(_t, 24);
/*  342 */       _t = _t.getFirstChild();
/*      */ 
/*  344 */       if (_t == null) _t = ASTNULL;
/*  345 */       switch (_t.getType())
/*      */       {
/*      */       case 60:
/*  348 */         ret = (GrammarAST)_t;
/*  349 */         match(_t, 60);
/*  350 */         _t = _t.getNextSibling();
/*  351 */         out(" returns [" + ret.getText() + "]");
/*  352 */         break;
/*      */       case 3:
/*  356 */         break;
/*      */       default:
/*  360 */         throw new NoViableAltException(_t);
/*      */       }
/*      */ 
/*  364 */       _t = __t52;
/*  365 */       _t = _t.getNextSibling();
/*      */ 
/*  367 */       if (_t == null) _t = ASTNULL;
/*  368 */       switch (_t.getType())
/*      */       {
/*      */       case 4:
/*  371 */         optionsSpec(_t);
/*  372 */         _t = this._retTree;
/*  373 */         break;
/*      */       case 9:
/*      */       case 33:
/*      */       case 46:
/*  379 */         break;
/*      */       default:
/*  383 */         throw new NoViableAltException(_t);
/*      */       }
/*      */ 
/*  388 */       if (_t == null) _t = ASTNULL;
/*  389 */       switch (_t.getType())
/*      */       {
/*      */       case 33:
/*  392 */         ruleScopeSpec(_t);
/*  393 */         _t = this._retTree;
/*  394 */         break;
/*      */       case 9:
/*      */       case 46:
/*  399 */         break;
/*      */       default:
/*  403 */         throw new NoViableAltException(_t);
/*      */       }
/*      */ 
/*      */       while (true)
/*      */       {
/*  410 */         if (_t == null) _t = ASTNULL;
/*  411 */         if (_t.getType() != 46) break;
/*  412 */         ruleAction(_t);
/*  413 */         _t = this._retTree;
/*      */       }
/*      */ 
/*  421 */       out(" : ");
/*  422 */       b = _t == ASTNULL ? null : (GrammarAST)_t;
/*  423 */       block(_t, false);
/*  424 */       _t = this._retTree;
/*      */ 
/*  426 */       if (_t == null) _t = ASTNULL;
/*  427 */       switch (_t.getType())
/*      */       {
/*      */       case 66:
/*      */       case 67:
/*  431 */         exceptionGroup(_t);
/*  432 */         _t = this._retTree;
/*  433 */         break;
/*      */       case 18:
/*  437 */         break;
/*      */       default:
/*  441 */         throw new NoViableAltException(_t);
/*      */       }
/*      */ 
/*  445 */       GrammarAST tmp9_AST_in = (GrammarAST)_t;
/*  446 */       match(_t, 18);
/*  447 */       _t = _t.getNextSibling();
/*  448 */       out(";\n");
/*  449 */       _t = __t48;
/*  450 */       _t = _t.getNextSibling();
/*      */     }
/*      */     catch (RecognitionException ex) {
/*  453 */       reportError(ex);
/*  454 */       if (_t != null) _t = _t.getNextSibling();
/*      */     }
/*  456 */     this._retTree = _t;
/*      */   }
/*      */ 
/*      */   public final void alternative(AST _t) throws RecognitionException
/*      */   {
/*  461 */     GrammarAST alternative_AST_in = _t == ASTNULL ? null : (GrammarAST)_t;
/*      */     try
/*      */     {
/*  464 */       AST __t80 = _t;
/*  465 */       GrammarAST tmp10_AST_in = (GrammarAST)_t;
/*  466 */       match(_t, 17);
/*  467 */       _t = _t.getFirstChild();
/*      */ 
/*  469 */       int _cnt82 = 0;
/*      */       while (true)
/*      */       {
/*  472 */         if (_t == null) _t = ASTNULL;
/*  473 */         if ((_t.getType() == 9) || (_t.getType() == 10) || (_t.getType() == 11) || (_t.getType() == 12) || (_t.getType() == 13) || (_t.getType() == 14) || (_t.getType() == 15) || (_t.getType() == 16) || (_t.getType() == 30) || (_t.getType() == 31) || (_t.getType() == 35) || (_t.getType() == 36) || (_t.getType() == 37) || (_t.getType() == 39) || (_t.getType() == 40) || (_t.getType() == 49) || (_t.getType() == 50) || (_t.getType() == 51) || (_t.getType() == 55) || (_t.getType() == 59) || (_t.getType() == 68) || (_t.getType() == 69) || (_t.getType() == 71) || (_t.getType() == 72) || (_t.getType() == 73) || (_t.getType() == 74) || (_t.getType() == 75)) {
/*  474 */           element(_t);
/*  475 */           _t = this._retTree;
/*      */         }
/*      */         else {
/*  478 */           if (_cnt82 >= 1) break; throw new NoViableAltException(_t);
/*      */         }
/*      */ 
/*  481 */         _cnt82++;
/*      */       }
/*      */ 
/*  484 */       GrammarAST tmp11_AST_in = (GrammarAST)_t;
/*  485 */       match(_t, 20);
/*  486 */       _t = _t.getNextSibling();
/*  487 */       _t = __t80;
/*  488 */       _t = _t.getNextSibling();
/*      */     }
/*      */     catch (RecognitionException ex) {
/*  491 */       reportError(ex);
/*  492 */       if (_t != null) _t = _t.getNextSibling();
/*      */     }
/*  494 */     this._retTree = _t;
/*      */   }
/*      */ 
/*      */   public final void element(AST _t) throws RecognitionException
/*      */   {
/*  499 */     GrammarAST element_AST_in = _t == ASTNULL ? null : (GrammarAST)_t;
/*  500 */     GrammarAST id = null;
/*  501 */     GrammarAST id2 = null;
/*  502 */     GrammarAST a = null;
/*  503 */     GrammarAST a2 = null;
/*  504 */     GrammarAST pred = null;
/*  505 */     GrammarAST spred = null;
/*  506 */     GrammarAST gpred = null;
/*      */     try
/*      */     {
/*  509 */       if (_t == null) _t = ASTNULL;
/*  510 */       switch (_t.getType())
/*      */       {
/*      */       case 71:
/*  513 */         AST __t107 = _t;
/*  514 */         GrammarAST tmp12_AST_in = (GrammarAST)_t;
/*  515 */         match(_t, 71);
/*  516 */         _t = _t.getFirstChild();
/*  517 */         element(_t);
/*  518 */         _t = this._retTree;
/*  519 */         _t = __t107;
/*  520 */         _t = _t.getNextSibling();
/*  521 */         break;
/*      */       case 59:
/*  525 */         AST __t108 = _t;
/*  526 */         GrammarAST tmp13_AST_in = (GrammarAST)_t;
/*  527 */         match(_t, 59);
/*  528 */         _t = _t.getFirstChild();
/*  529 */         element(_t);
/*  530 */         _t = this._retTree;
/*  531 */         _t = __t108;
/*  532 */         _t = _t.getNextSibling();
/*  533 */         break;
/*      */       case 31:
/*      */       case 39:
/*      */       case 50:
/*      */       case 51:
/*      */       case 55:
/*      */       case 72:
/*      */       case 73:
/*  543 */         atom(_t);
/*  544 */         _t = this._retTree;
/*  545 */         break;
/*      */       case 74:
/*  549 */         AST __t109 = _t;
/*  550 */         GrammarAST tmp14_AST_in = (GrammarAST)_t;
/*  551 */         match(_t, 74);
/*  552 */         _t = _t.getFirstChild();
/*  553 */         out("~");
/*  554 */         element(_t);
/*  555 */         _t = this._retTree;
/*  556 */         _t = __t109;
/*  557 */         _t = _t.getNextSibling();
/*  558 */         break;
/*      */       case 14:
/*  562 */         AST __t110 = _t;
/*  563 */         GrammarAST tmp15_AST_in = (GrammarAST)_t;
/*  564 */         match(_t, 14);
/*  565 */         _t = _t.getFirstChild();
/*  566 */         atom(_t);
/*  567 */         _t = this._retTree;
/*  568 */         out("..");
/*  569 */         atom(_t);
/*  570 */         _t = this._retTree;
/*  571 */         _t = __t110;
/*  572 */         _t = _t.getNextSibling();
/*  573 */         break;
/*      */       case 15:
/*  577 */         AST __t111 = _t;
/*  578 */         GrammarAST tmp16_AST_in = (GrammarAST)_t;
/*  579 */         match(_t, 15);
/*  580 */         _t = _t.getFirstChild();
/*  581 */         atom(_t);
/*  582 */         _t = this._retTree;
/*  583 */         out("..");
/*  584 */         atom(_t);
/*  585 */         _t = this._retTree;
/*  586 */         _t = __t111;
/*  587 */         _t = _t.getNextSibling();
/*  588 */         break;
/*      */       case 49:
/*  592 */         AST __t112 = _t;
/*  593 */         GrammarAST tmp17_AST_in = (GrammarAST)_t;
/*  594 */         match(_t, 49);
/*  595 */         _t = _t.getFirstChild();
/*  596 */         id = (GrammarAST)_t;
/*  597 */         match(_t, 21);
/*  598 */         _t = _t.getNextSibling();
/*  599 */         out(id.getText() + "=");
/*  600 */         element(_t);
/*  601 */         _t = this._retTree;
/*  602 */         _t = __t112;
/*  603 */         _t = _t.getNextSibling();
/*  604 */         break;
/*      */       case 68:
/*  608 */         AST __t113 = _t;
/*  609 */         GrammarAST tmp18_AST_in = (GrammarAST)_t;
/*  610 */         match(_t, 68);
/*  611 */         _t = _t.getFirstChild();
/*  612 */         id2 = (GrammarAST)_t;
/*  613 */         match(_t, 21);
/*  614 */         _t = _t.getNextSibling();
/*  615 */         out(id2.getText() + "+=");
/*  616 */         element(_t);
/*  617 */         _t = this._retTree;
/*  618 */         _t = __t113;
/*  619 */         _t = _t.getNextSibling();
/*  620 */         break;
/*      */       case 9:
/*      */       case 10:
/*      */       case 11:
/*      */       case 12:
/*  627 */         ebnf(_t);
/*  628 */         _t = this._retTree;
/*  629 */         break;
/*      */       case 75:
/*  633 */         tree(_t);
/*  634 */         _t = this._retTree;
/*  635 */         break;
/*      */       case 13:
/*  639 */         AST __t114 = _t;
/*  640 */         GrammarAST tmp19_AST_in = (GrammarAST)_t;
/*  641 */         match(_t, 13);
/*  642 */         _t = _t.getFirstChild();
/*  643 */         block(_t, true);
/*  644 */         _t = this._retTree;
/*  645 */         _t = __t114;
/*  646 */         _t = _t.getNextSibling();
/*  647 */         out("=>");
/*  648 */         break;
/*      */       case 40:
/*  652 */         a = (GrammarAST)_t;
/*  653 */         match(_t, 40);
/*  654 */         _t = _t.getNextSibling();
/*  655 */         if (this.showActions) { out("{"); out(a.getText()); out("}"); } break;
/*      */       case 30:
/*  660 */         a2 = (GrammarAST)_t;
/*  661 */         match(_t, 30);
/*  662 */         _t = _t.getNextSibling();
/*  663 */         if (this.showActions) { out("{{"); out(a2.getText()); out("}}"); } break;
/*      */       case 69:
/*  668 */         pred = (GrammarAST)_t;
/*  669 */         match(_t, 69);
/*  670 */         _t = _t.getNextSibling();
/*      */ 
/*  672 */         if (this.showActions) { out("{"); out(pred.getText()); out("}?"); } else {
/*  673 */           out("{...}?");
/*      */         }
/*  675 */         break;
/*      */       case 36:
/*  679 */         spred = (GrammarAST)_t;
/*  680 */         match(_t, 36);
/*  681 */         _t = _t.getNextSibling();
/*      */ 
/*  683 */         String name = spred.getText();
/*  684 */         GrammarAST predAST = this.grammar.getSyntacticPredicate(name);
/*  685 */         block(predAST, true);
/*  686 */         out("=>");
/*      */ 
/*  688 */         break;
/*      */       case 37:
/*  692 */         GrammarAST tmp20_AST_in = (GrammarAST)_t;
/*  693 */         match(_t, 37);
/*  694 */         _t = _t.getNextSibling();
/*  695 */         break;
/*      */       case 35:
/*  699 */         gpred = (GrammarAST)_t;
/*  700 */         match(_t, 35);
/*  701 */         _t = _t.getNextSibling();
/*      */ 
/*  703 */         if (this.showActions) { out("{"); out(gpred.getText()); out("}? =>"); } else {
/*  704 */           out("{...}? =>");
/*      */         }
/*  706 */         break;
/*      */       case 16:
/*  710 */         GrammarAST tmp21_AST_in = (GrammarAST)_t;
/*  711 */         match(_t, 16);
/*  712 */         _t = _t.getNextSibling();
/*  713 */         break;
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
/*  717 */         throw new NoViableAltException(_t);
/*      */       }
/*      */     }
/*      */     catch (RecognitionException ex)
/*      */     {
/*  722 */       reportError(ex);
/*  723 */       if (_t != null) _t = _t.getNextSibling();
/*      */     }
/*  725 */     this._retTree = _t;
/*      */   }
/*      */ 
/*      */   public final void single_rewrite(AST _t) throws RecognitionException
/*      */   {
/*  730 */     GrammarAST single_rewrite_AST_in = _t == ASTNULL ? null : (GrammarAST)_t;
/*      */     try
/*      */     {
/*  733 */       AST __t92 = _t;
/*  734 */       GrammarAST tmp22_AST_in = (GrammarAST)_t;
/*  735 */       match(_t, 80);
/*  736 */       _t = _t.getFirstChild();
/*  737 */       out(" ->");
/*      */ 
/*  739 */       if (_t == null) _t = ASTNULL;
/*  740 */       switch (_t.getType())
/*      */       {
/*      */       case 69:
/*  743 */         GrammarAST tmp23_AST_in = (GrammarAST)_t;
/*  744 */         match(_t, 69);
/*  745 */         _t = _t.getNextSibling();
/*  746 */         out(" {" + tmp23_AST_in.getText() + "}?");
/*  747 */         break;
/*      */       case 17:
/*      */       case 32:
/*      */       case 40:
/*      */       case 81:
/*  754 */         break;
/*      */       default:
/*  758 */         throw new NoViableAltException(_t);
/*      */       }
/*      */ 
/*  763 */       if (_t == null) _t = ASTNULL;
/*  764 */       switch (_t.getType())
/*      */       {
/*      */       case 17:
/*  767 */         alternative(_t);
/*  768 */         _t = this._retTree;
/*  769 */         break;
/*      */       case 32:
/*  773 */         rewrite_template(_t);
/*  774 */         _t = this._retTree;
/*  775 */         break;
/*      */       case 81:
/*  779 */         GrammarAST tmp24_AST_in = (GrammarAST)_t;
/*  780 */         match(_t, 81);
/*  781 */         _t = _t.getNextSibling();
/*  782 */         out("...");
/*  783 */         break;
/*      */       case 40:
/*  787 */         GrammarAST tmp25_AST_in = (GrammarAST)_t;
/*  788 */         match(_t, 40);
/*  789 */         _t = _t.getNextSibling();
/*  790 */         out(" {" + tmp25_AST_in.getText() + "}");
/*  791 */         break;
/*      */       default:
/*  795 */         throw new NoViableAltException(_t);
/*      */       }
/*      */ 
/*  799 */       _t = __t92;
/*  800 */       _t = _t.getNextSibling();
/*      */     }
/*      */     catch (RecognitionException ex) {
/*  803 */       reportError(ex);
/*  804 */       if (_t != null) _t = _t.getNextSibling();
/*      */     }
/*  806 */     this._retTree = _t;
/*      */   }
/*      */ 
/*      */   public final void grammarSpec(AST _t, String gtype)
/*      */     throws RecognitionException
/*      */   {
/*  813 */     GrammarAST grammarSpec_AST_in = _t == ASTNULL ? null : (GrammarAST)_t;
/*  814 */     GrammarAST id = null;
/*  815 */     GrammarAST cmt = null;
/*      */     try
/*      */     {
/*  818 */       id = (GrammarAST)_t;
/*  819 */       match(_t, 21);
/*  820 */       _t = _t.getNextSibling();
/*  821 */       out(gtype + "grammar " + id.getText());
/*      */ 
/*  823 */       if (_t == null) _t = ASTNULL;
/*  824 */       switch (_t.getType())
/*      */       {
/*      */       case 41:
/*  827 */         cmt = (GrammarAST)_t;
/*  828 */         match(_t, 41);
/*  829 */         _t = _t.getNextSibling();
/*  830 */         out(cmt.getText() + "\n");
/*  831 */         break;
/*      */       case 4:
/*      */       case 5:
/*      */       case 8:
/*      */       case 33:
/*      */       case 34:
/*      */       case 46:
/*  840 */         break;
/*      */       default:
/*  844 */         throw new NoViableAltException(_t);
/*      */       }
/*      */ 
/*  849 */       if (_t == null) _t = ASTNULL;
/*  850 */       switch (_t.getType())
/*      */       {
/*      */       case 4:
/*  853 */         optionsSpec(_t);
/*  854 */         _t = this._retTree;
/*  855 */         break;
/*      */       case 5:
/*      */       case 8:
/*      */       case 33:
/*      */       case 34:
/*      */       case 46:
/*  863 */         break;
/*      */       default:
/*  867 */         throw new NoViableAltException(_t);
/*      */       }
/*      */ 
/*  871 */       out(";\n");
/*      */ 
/*  873 */       if (_t == null) _t = ASTNULL;
/*  874 */       switch (_t.getType())
/*      */       {
/*      */       case 34:
/*  877 */         delegateGrammars(_t);
/*  878 */         _t = this._retTree;
/*  879 */         break;
/*      */       case 5:
/*      */       case 8:
/*      */       case 33:
/*      */       case 46:
/*  886 */         break;
/*      */       default:
/*  890 */         throw new NoViableAltException(_t);
/*      */       }
/*      */ 
/*  895 */       if (_t == null) _t = ASTNULL;
/*  896 */       switch (_t.getType())
/*      */       {
/*      */       case 5:
/*  899 */         tokensSpec(_t);
/*  900 */         _t = this._retTree;
/*  901 */         break;
/*      */       case 8:
/*      */       case 33:
/*      */       case 46:
/*  907 */         break;
/*      */       default:
/*  911 */         throw new NoViableAltException(_t);
/*      */       }
/*      */ 
/*      */       while (true)
/*      */       {
/*  918 */         if (_t == null) _t = ASTNULL;
/*  919 */         if (_t.getType() != 33) break;
/*  920 */         attrScope(_t);
/*  921 */         _t = this._retTree;
/*      */       }
/*      */ 
/*  930 */       if (_t == null) _t = ASTNULL;
/*  931 */       switch (_t.getType())
/*      */       {
/*      */       case 46:
/*  934 */         actions(_t);
/*  935 */         _t = this._retTree;
/*  936 */         break;
/*      */       case 8:
/*  940 */         break;
/*      */       default:
/*  944 */         throw new NoViableAltException(_t);
/*      */       }
/*      */ 
/*  948 */       rules(_t);
/*  949 */       _t = this._retTree;
/*      */     }
/*      */     catch (RecognitionException ex) {
/*  952 */       reportError(ex);
/*  953 */       if (_t != null) _t = _t.getNextSibling();
/*      */     }
/*  955 */     this._retTree = _t;
/*      */   }
/*      */ 
/*      */   public final void attrScope(AST _t) throws RecognitionException
/*      */   {
/*  960 */     GrammarAST attrScope_AST_in = _t == ASTNULL ? null : (GrammarAST)_t;
/*      */     try
/*      */     {
/*  963 */       AST __t10 = _t;
/*  964 */       GrammarAST tmp26_AST_in = (GrammarAST)_t;
/*  965 */       match(_t, 33);
/*  966 */       _t = _t.getFirstChild();
/*  967 */       GrammarAST tmp27_AST_in = (GrammarAST)_t;
/*  968 */       match(_t, 21);
/*  969 */       _t = _t.getNextSibling();
/*  970 */       GrammarAST tmp28_AST_in = (GrammarAST)_t;
/*  971 */       match(_t, 40);
/*  972 */       _t = _t.getNextSibling();
/*  973 */       _t = __t10;
/*  974 */       _t = _t.getNextSibling();
/*      */     }
/*      */     catch (RecognitionException ex) {
/*  977 */       reportError(ex);
/*  978 */       if (_t != null) _t = _t.getNextSibling();
/*      */     }
/*  980 */     this._retTree = _t;
/*      */   }
/*      */ 
/*      */   public final void optionsSpec(AST _t) throws RecognitionException
/*      */   {
/*  985 */     GrammarAST optionsSpec_AST_in = _t == ASTNULL ? null : (GrammarAST)_t;
/*      */     try
/*      */     {
/*  988 */       AST __t26 = _t;
/*  989 */       GrammarAST tmp29_AST_in = (GrammarAST)_t;
/*  990 */       match(_t, 4);
/*  991 */       _t = _t.getFirstChild();
/*  992 */       out(" options {");
/*      */ 
/*  994 */       int _cnt28 = 0;
/*      */       while (true)
/*      */       {
/*  997 */         if (_t == null) _t = ASTNULL;
/*  998 */         if (_t.getType() == 49) {
/*  999 */           option(_t);
/* 1000 */           _t = this._retTree;
/* 1001 */           out("; ");
/*      */         }
/*      */         else {
/* 1004 */           if (_cnt28 >= 1) break; throw new NoViableAltException(_t);
/*      */         }
/*      */ 
/* 1007 */         _cnt28++;
/*      */       }
/*      */ 
/* 1010 */       out("} ");
/* 1011 */       _t = __t26;
/* 1012 */       _t = _t.getNextSibling();
/*      */     }
/*      */     catch (RecognitionException ex) {
/* 1015 */       reportError(ex);
/* 1016 */       if (_t != null) _t = _t.getNextSibling();
/*      */     }
/* 1018 */     this._retTree = _t;
/*      */   }
/*      */ 
/*      */   public final void delegateGrammars(AST _t) throws RecognitionException
/*      */   {
/* 1023 */     GrammarAST delegateGrammars_AST_in = _t == ASTNULL ? null : (GrammarAST)_t;
/*      */     try
/*      */     {
/* 1026 */       AST __t33 = _t;
/* 1027 */       GrammarAST tmp30_AST_in = (GrammarAST)_t;
/* 1028 */       match(_t, 34);
/* 1029 */       _t = _t.getFirstChild();
/*      */ 
/* 1031 */       int _cnt36 = 0;
/*      */       while (true)
/*      */       {
/* 1034 */         if (_t == null) _t = ASTNULL;
/* 1035 */         switch (_t.getType())
/*      */         {
/*      */         case 49:
/* 1038 */           AST __t35 = _t;
/* 1039 */           GrammarAST tmp31_AST_in = (GrammarAST)_t;
/* 1040 */           match(_t, 49);
/* 1041 */           _t = _t.getFirstChild();
/* 1042 */           GrammarAST tmp32_AST_in = (GrammarAST)_t;
/* 1043 */           match(_t, 21);
/* 1044 */           _t = _t.getNextSibling();
/* 1045 */           GrammarAST tmp33_AST_in = (GrammarAST)_t;
/* 1046 */           match(_t, 21);
/* 1047 */           _t = _t.getNextSibling();
/* 1048 */           _t = __t35;
/* 1049 */           _t = _t.getNextSibling();
/* 1050 */           break;
/*      */         case 21:
/* 1054 */           GrammarAST tmp34_AST_in = (GrammarAST)_t;
/* 1055 */           match(_t, 21);
/* 1056 */           _t = _t.getNextSibling();
/* 1057 */           break;
/*      */         default:
/* 1061 */           if (_cnt36 >= 1) break label203; throw new NoViableAltException(_t);
/*      */         }
/*      */ 
/* 1064 */         _cnt36++;
/*      */       }
/*      */ 
/* 1067 */       label203: _t = __t33;
/* 1068 */       _t = _t.getNextSibling();
/*      */     }
/*      */     catch (RecognitionException ex) {
/* 1071 */       reportError(ex);
/* 1072 */       if (_t != null) _t = _t.getNextSibling();
/*      */     }
/* 1074 */     this._retTree = _t;
/*      */   }
/*      */ 
/*      */   public final void tokensSpec(AST _t) throws RecognitionException
/*      */   {
/* 1079 */     GrammarAST tokensSpec_AST_in = _t == ASTNULL ? null : (GrammarAST)_t;
/*      */     try
/*      */     {
/* 1082 */       AST __t38 = _t;
/* 1083 */       GrammarAST tmp35_AST_in = (GrammarAST)_t;
/* 1084 */       match(_t, 5);
/* 1085 */       _t = _t.getFirstChild();
/*      */ 
/* 1087 */       int _cnt40 = 0;
/*      */       while (true)
/*      */       {
/* 1090 */         if (_t == null) _t = ASTNULL;
/* 1091 */         if ((_t.getType() == 49) || (_t.getType() == 55)) {
/* 1092 */           tokenSpec(_t);
/* 1093 */           _t = this._retTree;
/*      */         }
/*      */         else {
/* 1096 */           if (_cnt40 >= 1) break; throw new NoViableAltException(_t);
/*      */         }
/*      */ 
/* 1099 */         _cnt40++;
/*      */       }
/*      */ 
/* 1102 */       _t = __t38;
/* 1103 */       _t = _t.getNextSibling();
/*      */     }
/*      */     catch (RecognitionException ex) {
/* 1106 */       reportError(ex);
/* 1107 */       if (_t != null) _t = _t.getNextSibling();
/*      */     }
/* 1109 */     this._retTree = _t;
/*      */   }
/*      */ 
/*      */   public final void actions(AST _t) throws RecognitionException
/*      */   {
/* 1114 */     GrammarAST actions_AST_in = _t == ASTNULL ? null : (GrammarAST)_t;
/*      */     try
/*      */     {
/* 1118 */       int _cnt21 = 0;
/*      */       while (true)
/*      */       {
/* 1121 */         if (_t == null) _t = ASTNULL;
/* 1122 */         if (_t.getType() == 46) {
/* 1123 */           action(_t);
/* 1124 */           _t = this._retTree;
/*      */         }
/*      */         else {
/* 1127 */           if (_cnt21 >= 1) break; throw new NoViableAltException(_t);
/*      */         }
/*      */ 
/* 1130 */         _cnt21++;
/*      */       }
/*      */     }
/*      */     catch (RecognitionException ex)
/*      */     {
/* 1135 */       reportError(ex);
/* 1136 */       if (_t != null) _t = _t.getNextSibling();
/*      */     }
/* 1138 */     this._retTree = _t;
/*      */   }
/*      */ 
/*      */   public final void rules(AST _t) throws RecognitionException
/*      */   {
/* 1143 */     GrammarAST rules_AST_in = _t == ASTNULL ? null : (GrammarAST)_t;
/*      */     try
/*      */     {
/* 1147 */       int _cnt46 = 0;
/*      */       while (true)
/*      */       {
/* 1150 */         if (_t == null) _t = ASTNULL;
/* 1151 */         if (_t.getType() == 8) {
/* 1152 */           rule(_t);
/* 1153 */           _t = this._retTree;
/*      */         }
/*      */         else {
/* 1156 */           if (_cnt46 >= 1) break; throw new NoViableAltException(_t);
/*      */         }
/*      */ 
/* 1159 */         _cnt46++;
/*      */       }
/*      */     }
/*      */     catch (RecognitionException ex)
/*      */     {
/* 1164 */       reportError(ex);
/* 1165 */       if (_t != null) _t = _t.getNextSibling();
/*      */     }
/* 1167 */     this._retTree = _t;
/*      */   }
/*      */ 
/*      */   public final void action(AST _t) throws RecognitionException
/*      */   {
/* 1172 */     GrammarAST action_AST_in = _t == ASTNULL ? null : (GrammarAST)_t;
/* 1173 */     GrammarAST id1 = null;
/* 1174 */     GrammarAST id2 = null;
/* 1175 */     GrammarAST a1 = null;
/* 1176 */     GrammarAST a2 = null;
/*      */ 
/* 1178 */     String scope = null; String name = null;
/* 1179 */     String action = null;
/*      */     try
/*      */     {
/* 1183 */       AST __t23 = _t;
/* 1184 */       GrammarAST tmp36_AST_in = (GrammarAST)_t;
/* 1185 */       match(_t, 46);
/* 1186 */       _t = _t.getFirstChild();
/* 1187 */       id1 = (GrammarAST)_t;
/* 1188 */       match(_t, 21);
/* 1189 */       _t = _t.getNextSibling();
/*      */ 
/* 1191 */       if (_t == null) _t = ASTNULL;
/* 1192 */       switch (_t.getType())
/*      */       {
/*      */       case 21:
/* 1195 */         id2 = (GrammarAST)_t;
/* 1196 */         match(_t, 21);
/* 1197 */         _t = _t.getNextSibling();
/* 1198 */         a1 = (GrammarAST)_t;
/* 1199 */         match(_t, 40);
/* 1200 */         _t = _t.getNextSibling();
/* 1201 */         scope = id1.getText(); name = a1.getText(); action = a1.getText();
/* 1202 */         break;
/*      */       case 40:
/* 1206 */         a2 = (GrammarAST)_t;
/* 1207 */         match(_t, 40);
/* 1208 */         _t = _t.getNextSibling();
/* 1209 */         scope = null; name = id1.getText(); action = a2.getText();
/* 1210 */         break;
/*      */       default:
/* 1214 */         throw new NoViableAltException(_t);
/*      */       }
/*      */ 
/* 1218 */       _t = __t23;
/* 1219 */       _t = _t.getNextSibling();
/*      */ 
/* 1221 */       if (this.showActions) {
/* 1222 */         out("@" + (scope != null ? scope + "::" : "") + name + action);
/*      */       }
/*      */     }
/*      */     catch (RecognitionException ex)
/*      */     {
/* 1227 */       reportError(ex);
/* 1228 */       if (_t != null) _t = _t.getNextSibling();
/*      */     }
/* 1230 */     this._retTree = _t;
/*      */   }
/*      */ 
/*      */   public final void option(AST _t) throws RecognitionException
/*      */   {
/* 1235 */     GrammarAST option_AST_in = _t == ASTNULL ? null : (GrammarAST)_t;
/* 1236 */     GrammarAST id = null;
/*      */     try
/*      */     {
/* 1239 */       AST __t30 = _t;
/* 1240 */       GrammarAST tmp37_AST_in = (GrammarAST)_t;
/* 1241 */       match(_t, 49);
/* 1242 */       _t = _t.getFirstChild();
/* 1243 */       id = (GrammarAST)_t;
/* 1244 */       match(_t, 21);
/* 1245 */       _t = _t.getNextSibling();
/* 1246 */       out(id.getText() + "=");
/* 1247 */       optionValue(_t);
/* 1248 */       _t = this._retTree;
/* 1249 */       _t = __t30;
/* 1250 */       _t = _t.getNextSibling();
/*      */     }
/*      */     catch (RecognitionException ex) {
/* 1253 */       reportError(ex);
/* 1254 */       if (_t != null) _t = _t.getNextSibling();
/*      */     }
/* 1256 */     this._retTree = _t;
/*      */   }
/*      */ 
/*      */   public final void optionValue(AST _t) throws RecognitionException
/*      */   {
/* 1261 */     GrammarAST optionValue_AST_in = _t == ASTNULL ? null : (GrammarAST)_t;
/* 1262 */     GrammarAST id = null;
/* 1263 */     GrammarAST s = null;
/* 1264 */     GrammarAST c = null;
/* 1265 */     GrammarAST i = null;
/*      */     try
/*      */     {
/* 1268 */       if (_t == null) _t = ASTNULL;
/* 1269 */       switch (_t.getType())
/*      */       {
/*      */       case 21:
/* 1272 */         id = (GrammarAST)_t;
/* 1273 */         match(_t, 21);
/* 1274 */         _t = _t.getNextSibling();
/* 1275 */         out(id.getText());
/* 1276 */         break;
/*      */       case 50:
/* 1280 */         s = (GrammarAST)_t;
/* 1281 */         match(_t, 50);
/* 1282 */         _t = _t.getNextSibling();
/* 1283 */         out(s.getText());
/* 1284 */         break;
/*      */       case 51:
/* 1288 */         c = (GrammarAST)_t;
/* 1289 */         match(_t, 51);
/* 1290 */         _t = _t.getNextSibling();
/* 1291 */         out(c.getText());
/* 1292 */         break;
/*      */       case 52:
/* 1296 */         i = (GrammarAST)_t;
/* 1297 */         match(_t, 52);
/* 1298 */         _t = _t.getNextSibling();
/* 1299 */         out(i.getText());
/* 1300 */         break;
/*      */       default:
/* 1304 */         throw new NoViableAltException(_t);
/*      */       }
/*      */     }
/*      */     catch (RecognitionException ex)
/*      */     {
/* 1309 */       reportError(ex);
/* 1310 */       if (_t != null) _t = _t.getNextSibling();
/*      */     }
/* 1312 */     this._retTree = _t;
/*      */   }
/*      */ 
/*      */   public final void tokenSpec(AST _t) throws RecognitionException
/*      */   {
/* 1317 */     GrammarAST tokenSpec_AST_in = _t == ASTNULL ? null : (GrammarAST)_t;
/*      */     try
/*      */     {
/* 1320 */       if (_t == null) _t = ASTNULL;
/* 1321 */       switch (_t.getType())
/*      */       {
/*      */       case 55:
/* 1324 */         GrammarAST tmp38_AST_in = (GrammarAST)_t;
/* 1325 */         match(_t, 55);
/* 1326 */         _t = _t.getNextSibling();
/* 1327 */         break;
/*      */       case 49:
/* 1331 */         AST __t42 = _t;
/* 1332 */         GrammarAST tmp39_AST_in = (GrammarAST)_t;
/* 1333 */         match(_t, 49);
/* 1334 */         _t = _t.getFirstChild();
/* 1335 */         GrammarAST tmp40_AST_in = (GrammarAST)_t;
/* 1336 */         match(_t, 55);
/* 1337 */         _t = _t.getNextSibling();
/*      */ 
/* 1339 */         if (_t == null) _t = ASTNULL;
/* 1340 */         switch (_t.getType())
/*      */         {
/*      */         case 50:
/* 1343 */           GrammarAST tmp41_AST_in = (GrammarAST)_t;
/* 1344 */           match(_t, 50);
/* 1345 */           _t = _t.getNextSibling();
/* 1346 */           break;
/*      */         case 51:
/* 1350 */           GrammarAST tmp42_AST_in = (GrammarAST)_t;
/* 1351 */           match(_t, 51);
/* 1352 */           _t = _t.getNextSibling();
/* 1353 */           break;
/*      */         default:
/* 1357 */           throw new NoViableAltException(_t);
/*      */         }
/*      */ 
/* 1361 */         _t = __t42;
/* 1362 */         _t = _t.getNextSibling();
/* 1363 */         break;
/*      */       default:
/* 1367 */         throw new NoViableAltException(_t);
/*      */       }
/*      */     }
/*      */     catch (RecognitionException ex)
/*      */     {
/* 1372 */       reportError(ex);
/* 1373 */       if (_t != null) _t = _t.getNextSibling();
/*      */     }
/* 1375 */     this._retTree = _t;
/*      */   }
/*      */ 
/*      */   public final void modifier(AST _t) throws RecognitionException
/*      */   {
/* 1380 */     GrammarAST modifier_AST_in = _t == ASTNULL ? null : (GrammarAST)_t;
/* 1381 */     out(modifier_AST_in.getText()); out(" ");
/*      */     try
/*      */     {
/* 1384 */       if (_t == null) _t = ASTNULL;
/* 1385 */       switch (_t.getType())
/*      */       {
/*      */       case 56:
/* 1388 */         GrammarAST tmp43_AST_in = (GrammarAST)_t;
/* 1389 */         match(_t, 56);
/* 1390 */         _t = _t.getNextSibling();
/* 1391 */         break;
/*      */       case 57:
/* 1395 */         GrammarAST tmp44_AST_in = (GrammarAST)_t;
/* 1396 */         match(_t, 57);
/* 1397 */         _t = _t.getNextSibling();
/* 1398 */         break;
/*      */       case 58:
/* 1402 */         GrammarAST tmp45_AST_in = (GrammarAST)_t;
/* 1403 */         match(_t, 58);
/* 1404 */         _t = _t.getNextSibling();
/* 1405 */         break;
/*      */       case 38:
/* 1409 */         GrammarAST tmp46_AST_in = (GrammarAST)_t;
/* 1410 */         match(_t, 38);
/* 1411 */         _t = _t.getNextSibling();
/* 1412 */         break;
/*      */       default:
/* 1416 */         throw new NoViableAltException(_t);
/*      */       }
/*      */     }
/*      */     catch (RecognitionException ex)
/*      */     {
/* 1421 */       reportError(ex);
/* 1422 */       if (_t != null) _t = _t.getNextSibling();
/*      */     }
/* 1424 */     this._retTree = _t;
/*      */   }
/*      */ 
/*      */   public final void ruleScopeSpec(AST _t) throws RecognitionException
/*      */   {
/* 1429 */     GrammarAST ruleScopeSpec_AST_in = _t == ASTNULL ? null : (GrammarAST)_t;
/*      */     try
/*      */     {
/* 1432 */       AST __t63 = _t;
/* 1433 */       GrammarAST tmp47_AST_in = (GrammarAST)_t;
/* 1434 */       match(_t, 33);
/* 1435 */       _t = _t.getFirstChild();
/*      */ 
/* 1437 */       if (_t == null) _t = ASTNULL;
/* 1438 */       switch (_t.getType())
/*      */       {
/*      */       case 40:
/* 1441 */         GrammarAST tmp48_AST_in = (GrammarAST)_t;
/* 1442 */         match(_t, 40);
/* 1443 */         _t = _t.getNextSibling();
/* 1444 */         break;
/*      */       case 3:
/*      */       case 21:
/* 1449 */         break;
/*      */       default:
/* 1453 */         throw new NoViableAltException(_t);
/*      */       }
/*      */ 
/*      */       while (true)
/*      */       {
/* 1460 */         if (_t == null) _t = ASTNULL;
/* 1461 */         if (_t.getType() != 21) break;
/* 1462 */         GrammarAST tmp49_AST_in = (GrammarAST)_t;
/* 1463 */         match(_t, 21);
/* 1464 */         _t = _t.getNextSibling();
/*      */       }
/*      */ 
/* 1472 */       _t = __t63;
/* 1473 */       _t = _t.getNextSibling();
/*      */     }
/*      */     catch (RecognitionException ex) {
/* 1476 */       reportError(ex);
/* 1477 */       if (_t != null) _t = _t.getNextSibling();
/*      */     }
/* 1479 */     this._retTree = _t;
/*      */   }
/*      */ 
/*      */   public final void ruleAction(AST _t) throws RecognitionException
/*      */   {
/* 1484 */     GrammarAST ruleAction_AST_in = _t == ASTNULL ? null : (GrammarAST)_t;
/* 1485 */     GrammarAST id = null;
/* 1486 */     GrammarAST a = null;
/*      */     try
/*      */     {
/* 1489 */       AST __t60 = _t;
/* 1490 */       GrammarAST tmp50_AST_in = (GrammarAST)_t;
/* 1491 */       match(_t, 46);
/* 1492 */       _t = _t.getFirstChild();
/* 1493 */       id = (GrammarAST)_t;
/* 1494 */       match(_t, 21);
/* 1495 */       _t = _t.getNextSibling();
/* 1496 */       a = (GrammarAST)_t;
/* 1497 */       match(_t, 40);
/* 1498 */       _t = _t.getNextSibling();
/* 1499 */       _t = __t60;
/* 1500 */       _t = _t.getNextSibling();
/* 1501 */       if (this.showActions) out("@" + id.getText() + "{" + a.getText() + "}"); 
/*      */     }
/*      */     catch (RecognitionException ex)
/*      */     {
/* 1504 */       reportError(ex);
/* 1505 */       if (_t != null) _t = _t.getNextSibling();
/*      */     }
/* 1507 */     this._retTree = _t;
/*      */   }
/*      */ 
/*      */   public final void block(AST _t, boolean forceParens)
/*      */     throws RecognitionException
/*      */   {
/* 1514 */     GrammarAST block_AST_in = _t == ASTNULL ? null : (GrammarAST)_t;
/*      */ 
/* 1516 */     int numAlts = countAltsForBlock(block_AST_in);
/*      */     try
/*      */     {
/* 1520 */       AST __t68 = _t;
/* 1521 */       GrammarAST tmp51_AST_in = (GrammarAST)_t;
/* 1522 */       match(_t, 9);
/* 1523 */       _t = _t.getFirstChild();
/* 1524 */       if ((forceParens) || (numAlts > 1)) out(" (");
/*      */ 
/* 1526 */       if (_t == null) _t = ASTNULL;
/* 1527 */       switch (_t.getType())
/*      */       {
/*      */       case 4:
/* 1530 */         optionsSpec(_t);
/* 1531 */         _t = this._retTree;
/* 1532 */         out(" : ");
/* 1533 */         break;
/*      */       case 17:
/* 1537 */         break;
/*      */       default:
/* 1541 */         throw new NoViableAltException(_t);
/*      */       }
/*      */ 
/* 1545 */       alternative(_t);
/* 1546 */       _t = this._retTree;
/* 1547 */       rewrite(_t);
/* 1548 */       _t = this._retTree;
/*      */       while (true)
/*      */       {
/* 1552 */         if (_t == null) _t = ASTNULL;
/* 1553 */         if (_t.getType() != 17) break;
/* 1554 */         out(" | ");
/* 1555 */         alternative(_t);
/* 1556 */         _t = this._retTree;
/* 1557 */         rewrite(_t);
/* 1558 */         _t = this._retTree;
/*      */       }
/*      */ 
/* 1566 */       GrammarAST tmp52_AST_in = (GrammarAST)_t;
/* 1567 */       match(_t, 19);
/* 1568 */       _t = _t.getNextSibling();
/* 1569 */       if ((forceParens) || (numAlts > 1)) out(")");
/* 1570 */       _t = __t68;
/* 1571 */       _t = _t.getNextSibling();
/*      */     }
/*      */     catch (RecognitionException ex) {
/* 1574 */       reportError(ex);
/* 1575 */       if (_t != null) _t = _t.getNextSibling();
/*      */     }
/* 1577 */     this._retTree = _t;
/*      */   }
/*      */ 
/*      */   public final void exceptionGroup(AST _t) throws RecognitionException
/*      */   {
/* 1582 */     GrammarAST exceptionGroup_AST_in = _t == ASTNULL ? null : (GrammarAST)_t;
/*      */     try
/*      */     {
/* 1585 */       if (_t == null) _t = ASTNULL;
/* 1586 */       switch (_t.getType())
/*      */       {
/*      */       case 66:
/* 1590 */         int _cnt85 = 0;
/*      */         while (true)
/*      */         {
/* 1593 */           if (_t == null) _t = ASTNULL;
/* 1594 */           if (_t.getType() == 66) {
/* 1595 */             exceptionHandler(_t);
/* 1596 */             _t = this._retTree;
/*      */           }
/*      */           else {
/* 1599 */             if (_cnt85 >= 1) break; throw new NoViableAltException(_t);
/*      */           }
/*      */ 
/* 1602 */           _cnt85++;
/*      */         }
/*      */ 
/* 1606 */         if (_t == null) _t = ASTNULL;
/* 1607 */         switch (_t.getType())
/*      */         {
/*      */         case 67:
/* 1610 */           finallyClause(_t);
/* 1611 */           _t = this._retTree;
/* 1612 */           break;
/*      */         case 18:
/* 1616 */           break;
/*      */         default:
/* 1620 */           throw new NoViableAltException(_t);
/*      */         }
/*      */ 
/*      */         break;
/*      */       case 67:
/* 1628 */         finallyClause(_t);
/* 1629 */         _t = this._retTree;
/* 1630 */         break;
/*      */       default:
/* 1634 */         throw new NoViableAltException(_t);
/*      */       }
/*      */     }
/*      */     catch (RecognitionException ex)
/*      */     {
/* 1639 */       reportError(ex);
/* 1640 */       if (_t != null) _t = _t.getNextSibling();
/*      */     }
/* 1642 */     this._retTree = _t;
/*      */   }
/*      */ 
/*      */   public final void rewrite(AST _t) throws RecognitionException
/*      */   {
/* 1647 */     GrammarAST rewrite_AST_in = _t == ASTNULL ? null : (GrammarAST)_t;
/*      */     try
/*      */     {
/*      */       while (true)
/*      */       {
/* 1653 */         if (_t == null) _t = ASTNULL;
/* 1654 */         if (_t.getType() != 80) break;
/* 1655 */         single_rewrite(_t);
/* 1656 */         _t = this._retTree;
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (RecognitionException ex)
/*      */     {
/* 1666 */       reportError(ex);
/* 1667 */       if (_t != null) _t = _t.getNextSibling();
/*      */     }
/* 1669 */     this._retTree = _t;
/*      */   }
/*      */ 
/*      */   public final int countAltsForBlock(AST _t) throws RecognitionException {
/* 1673 */     int n = 0;
/*      */ 
/* 1675 */     GrammarAST countAltsForBlock_AST_in = _t == ASTNULL ? null : (GrammarAST)_t;
/*      */     try
/*      */     {
/* 1678 */       AST __t73 = _t;
/* 1679 */       GrammarAST tmp53_AST_in = (GrammarAST)_t;
/* 1680 */       match(_t, 9);
/* 1681 */       _t = _t.getFirstChild();
/*      */ 
/* 1683 */       if (_t == null) _t = ASTNULL;
/* 1684 */       switch (_t.getType())
/*      */       {
/*      */       case 4:
/* 1687 */         GrammarAST tmp54_AST_in = (GrammarAST)_t;
/* 1688 */         match(_t, 4);
/* 1689 */         _t = _t.getNextSibling();
/* 1690 */         break;
/*      */       case 17:
/* 1694 */         break;
/*      */       default:
/* 1698 */         throw new NoViableAltException(_t);
/*      */       }
/*      */ 
/* 1703 */       int _cnt78 = 0;
/*      */       while (true)
/*      */       {
/* 1706 */         if (_t == null) _t = ASTNULL;
/* 1707 */         if (_t.getType() == 17) {
/* 1708 */           GrammarAST tmp55_AST_in = (GrammarAST)_t;
/* 1709 */           match(_t, 17);
/* 1710 */           _t = _t.getNextSibling();
/*      */           while (true)
/*      */           {
/* 1714 */             if (_t == null) _t = ASTNULL;
/* 1715 */             if (_t.getType() != 80) break;
/* 1716 */             GrammarAST tmp56_AST_in = (GrammarAST)_t;
/* 1717 */             match(_t, 80);
/* 1718 */             _t = _t.getNextSibling();
/*      */           }
/*      */ 
/* 1726 */           n++;
/*      */         }
/*      */         else {
/* 1729 */           if (_cnt78 >= 1) break; throw new NoViableAltException(_t);
/*      */         }
/*      */ 
/* 1732 */         _cnt78++;
/*      */       }
/*      */ 
/* 1735 */       GrammarAST tmp57_AST_in = (GrammarAST)_t;
/* 1736 */       match(_t, 19);
/* 1737 */       _t = _t.getNextSibling();
/* 1738 */       _t = __t73;
/* 1739 */       _t = _t.getNextSibling();
/*      */     }
/*      */     catch (RecognitionException ex) {
/* 1742 */       reportError(ex);
/* 1743 */       if (_t != null) _t = _t.getNextSibling();
/*      */     }
/* 1745 */     this._retTree = _t;
/* 1746 */     return n;
/*      */   }
/*      */ 
/*      */   public final void exceptionHandler(AST _t) throws RecognitionException
/*      */   {
/* 1751 */     GrammarAST exceptionHandler_AST_in = _t == ASTNULL ? null : (GrammarAST)_t;
/*      */     try
/*      */     {
/* 1754 */       AST __t88 = _t;
/* 1755 */       GrammarAST tmp58_AST_in = (GrammarAST)_t;
/* 1756 */       match(_t, 66);
/* 1757 */       _t = _t.getFirstChild();
/* 1758 */       GrammarAST tmp59_AST_in = (GrammarAST)_t;
/* 1759 */       match(_t, 60);
/* 1760 */       _t = _t.getNextSibling();
/* 1761 */       GrammarAST tmp60_AST_in = (GrammarAST)_t;
/* 1762 */       match(_t, 40);
/* 1763 */       _t = _t.getNextSibling();
/* 1764 */       _t = __t88;
/* 1765 */       _t = _t.getNextSibling();
/*      */     }
/*      */     catch (RecognitionException ex) {
/* 1768 */       reportError(ex);
/* 1769 */       if (_t != null) _t = _t.getNextSibling();
/*      */     }
/* 1771 */     this._retTree = _t;
/*      */   }
/*      */ 
/*      */   public final void finallyClause(AST _t) throws RecognitionException
/*      */   {
/* 1776 */     GrammarAST finallyClause_AST_in = _t == ASTNULL ? null : (GrammarAST)_t;
/*      */     try
/*      */     {
/* 1779 */       AST __t90 = _t;
/* 1780 */       GrammarAST tmp61_AST_in = (GrammarAST)_t;
/* 1781 */       match(_t, 67);
/* 1782 */       _t = _t.getFirstChild();
/* 1783 */       GrammarAST tmp62_AST_in = (GrammarAST)_t;
/* 1784 */       match(_t, 40);
/* 1785 */       _t = _t.getNextSibling();
/* 1786 */       _t = __t90;
/* 1787 */       _t = _t.getNextSibling();
/*      */     }
/*      */     catch (RecognitionException ex) {
/* 1790 */       reportError(ex);
/* 1791 */       if (_t != null) _t = _t.getNextSibling();
/*      */     }
/* 1793 */     this._retTree = _t;
/*      */   }
/*      */ 
/*      */   public final void rewrite_template(AST _t) throws RecognitionException
/*      */   {
/* 1798 */     GrammarAST rewrite_template_AST_in = _t == ASTNULL ? null : (GrammarAST)_t;
/* 1799 */     GrammarAST id = null;
/* 1800 */     GrammarAST ind = null;
/* 1801 */     GrammarAST arg = null;
/* 1802 */     GrammarAST a = null;
/*      */     try
/*      */     {
/* 1805 */       AST __t96 = _t;
/* 1806 */       GrammarAST tmp63_AST_in = (GrammarAST)_t;
/* 1807 */       match(_t, 32);
/* 1808 */       _t = _t.getFirstChild();
/*      */ 
/* 1810 */       if (_t == null) _t = ASTNULL;
/* 1811 */       switch (_t.getType())
/*      */       {
/*      */       case 21:
/* 1814 */         id = (GrammarAST)_t;
/* 1815 */         match(_t, 21);
/* 1816 */         _t = _t.getNextSibling();
/* 1817 */         out(" " + id.getText());
/* 1818 */         break;
/*      */       case 40:
/* 1822 */         ind = (GrammarAST)_t;
/* 1823 */         match(_t, 40);
/* 1824 */         _t = _t.getNextSibling();
/* 1825 */         out(" ({" + ind.getText() + "})");
/* 1826 */         break;
/*      */       default:
/* 1830 */         throw new NoViableAltException(_t);
/*      */       }
/*      */ 
/* 1834 */       AST __t98 = _t;
/* 1835 */       GrammarAST tmp64_AST_in = (GrammarAST)_t;
/* 1836 */       match(_t, 23);
/* 1837 */       _t = _t.getFirstChild();
/* 1838 */       out("(");
/*      */       while (true)
/*      */       {
/* 1842 */         if (_t == null) _t = ASTNULL;
/* 1843 */         if (_t.getType() != 22) break;
/* 1844 */         AST __t100 = _t;
/* 1845 */         GrammarAST tmp65_AST_in = (GrammarAST)_t;
/* 1846 */         match(_t, 22);
/* 1847 */         _t = _t.getFirstChild();
/* 1848 */         arg = (GrammarAST)_t;
/* 1849 */         match(_t, 21);
/* 1850 */         _t = _t.getNextSibling();
/* 1851 */         out(arg.getText() + "=");
/* 1852 */         a = (GrammarAST)_t;
/* 1853 */         match(_t, 40);
/* 1854 */         _t = _t.getNextSibling();
/* 1855 */         out(a.getText());
/* 1856 */         _t = __t100;
/* 1857 */         _t = _t.getNextSibling();
/*      */       }
/*      */ 
/* 1865 */       out(")");
/* 1866 */       _t = __t98;
/* 1867 */       _t = _t.getNextSibling();
/*      */ 
/* 1869 */       if (_t == null) _t = ASTNULL;
/* 1870 */       switch (_t.getType())
/*      */       {
/*      */       case 83:
/* 1873 */         GrammarAST tmp66_AST_in = (GrammarAST)_t;
/* 1874 */         match(_t, 83);
/* 1875 */         _t = _t.getNextSibling();
/* 1876 */         out(" " + tmp66_AST_in.getText());
/* 1877 */         break;
/*      */       case 84:
/* 1881 */         GrammarAST tmp67_AST_in = (GrammarAST)_t;
/* 1882 */         match(_t, 84);
/* 1883 */         _t = _t.getNextSibling();
/* 1884 */         out(" " + tmp67_AST_in.getText());
/* 1885 */         break;
/*      */       case 3:
/* 1889 */         break;
/*      */       default:
/* 1893 */         throw new NoViableAltException(_t);
/*      */       }
/*      */ 
/* 1897 */       _t = __t96;
/* 1898 */       _t = _t.getNextSibling();
/*      */     }
/*      */     catch (RecognitionException ex) {
/* 1901 */       reportError(ex);
/* 1902 */       if (_t != null) _t = _t.getNextSibling();
/*      */     }
/* 1904 */     this._retTree = _t;
/*      */   }
/*      */ 
/*      */   public final void atom(AST _t) throws RecognitionException
/*      */   {
/* 1909 */     GrammarAST atom_AST_in = _t == ASTNULL ? null : (GrammarAST)_t;
/* 1910 */     GrammarAST rarg = null;
/* 1911 */     GrammarAST targ = null;
/* 1912 */     out(" ");
/*      */     try
/*      */     {
/* 1915 */       if (_t == null) _t = ASTNULL;
/* 1916 */       switch (_t.getType())
/*      */       {
/*      */       case 50:
/*      */       case 51:
/*      */       case 55:
/*      */       case 72:
/*      */       case 73:
/* 1924 */         if (_t == null) _t = ASTNULL;
/* 1925 */         switch (_t.getType())
/*      */         {
/*      */         case 73:
/* 1928 */           AST __t125 = _t;
/* 1929 */           GrammarAST tmp68_AST_in = (GrammarAST)_t;
/* 1930 */           match(_t, 73);
/* 1931 */           _t = _t.getFirstChild();
/* 1932 */           out(atom_AST_in.toString());
/*      */ 
/* 1934 */           if (_t == null) _t = ASTNULL;
/* 1935 */           switch (_t.getType())
/*      */           {
/*      */           case 60:
/* 1938 */             rarg = (GrammarAST)_t;
/* 1939 */             match(_t, 60);
/* 1940 */             _t = _t.getNextSibling();
/* 1941 */             out("[" + rarg.toString() + "]");
/* 1942 */             break;
/*      */           case 3:
/*      */           case 59:
/*      */           case 71:
/* 1948 */             break;
/*      */           default:
/* 1952 */             throw new NoViableAltException(_t);
/*      */           }
/*      */ 
/* 1957 */           if (_t == null) _t = ASTNULL;
/* 1958 */           switch (_t.getType())
/*      */           {
/*      */           case 59:
/*      */           case 71:
/* 1962 */             ast_suffix(_t);
/* 1963 */             _t = this._retTree;
/* 1964 */             break;
/*      */           case 3:
/* 1968 */             break;
/*      */           default:
/* 1972 */             throw new NoViableAltException(_t);
/*      */           }
/*      */ 
/* 1976 */           _t = __t125;
/* 1977 */           _t = _t.getNextSibling();
/* 1978 */           break;
/*      */         case 55:
/* 1982 */           AST __t128 = _t;
/* 1983 */           GrammarAST tmp69_AST_in = (GrammarAST)_t;
/* 1984 */           match(_t, 55);
/* 1985 */           _t = _t.getFirstChild();
/* 1986 */           out(atom_AST_in.toString());
/*      */ 
/* 1988 */           if (_t == null) _t = ASTNULL;
/* 1989 */           switch (_t.getType())
/*      */           {
/*      */           case 60:
/* 1992 */             targ = (GrammarAST)_t;
/* 1993 */             match(_t, 60);
/* 1994 */             _t = _t.getNextSibling();
/* 1995 */             out("[" + targ.toString() + "]");
/* 1996 */             break;
/*      */           case 3:
/*      */           case 59:
/*      */           case 71:
/* 2002 */             break;
/*      */           default:
/* 2006 */             throw new NoViableAltException(_t);
/*      */           }
/*      */ 
/* 2011 */           if (_t == null) _t = ASTNULL;
/* 2012 */           switch (_t.getType())
/*      */           {
/*      */           case 59:
/*      */           case 71:
/* 2016 */             ast_suffix(_t);
/* 2017 */             _t = this._retTree;
/* 2018 */             break;
/*      */           case 3:
/* 2022 */             break;
/*      */           default:
/* 2026 */             throw new NoViableAltException(_t);
/*      */           }
/*      */ 
/* 2030 */           _t = __t128;
/* 2031 */           _t = _t.getNextSibling();
/* 2032 */           break;
/*      */         case 51:
/* 2036 */           AST __t131 = _t;
/* 2037 */           GrammarAST tmp70_AST_in = (GrammarAST)_t;
/* 2038 */           match(_t, 51);
/* 2039 */           _t = _t.getFirstChild();
/* 2040 */           out(atom_AST_in.toString());
/*      */ 
/* 2042 */           if (_t == null) _t = ASTNULL;
/* 2043 */           switch (_t.getType())
/*      */           {
/*      */           case 59:
/*      */           case 71:
/* 2047 */             ast_suffix(_t);
/* 2048 */             _t = this._retTree;
/* 2049 */             break;
/*      */           case 3:
/* 2053 */             break;
/*      */           default:
/* 2057 */             throw new NoViableAltException(_t);
/*      */           }
/*      */ 
/* 2061 */           _t = __t131;
/* 2062 */           _t = _t.getNextSibling();
/* 2063 */           break;
/*      */         case 50:
/* 2067 */           AST __t133 = _t;
/* 2068 */           GrammarAST tmp71_AST_in = (GrammarAST)_t;
/* 2069 */           match(_t, 50);
/* 2070 */           _t = _t.getFirstChild();
/* 2071 */           out(atom_AST_in.toString());
/*      */ 
/* 2073 */           if (_t == null) _t = ASTNULL;
/* 2074 */           switch (_t.getType())
/*      */           {
/*      */           case 59:
/*      */           case 71:
/* 2078 */             ast_suffix(_t);
/* 2079 */             _t = this._retTree;
/* 2080 */             break;
/*      */           case 3:
/* 2084 */             break;
/*      */           default:
/* 2088 */             throw new NoViableAltException(_t);
/*      */           }
/*      */ 
/* 2092 */           _t = __t133;
/* 2093 */           _t = _t.getNextSibling();
/* 2094 */           break;
/*      */         case 72:
/* 2098 */           AST __t135 = _t;
/* 2099 */           GrammarAST tmp72_AST_in = (GrammarAST)_t;
/* 2100 */           match(_t, 72);
/* 2101 */           _t = _t.getFirstChild();
/* 2102 */           out(atom_AST_in.toString());
/*      */ 
/* 2104 */           if (_t == null) _t = ASTNULL;
/* 2105 */           switch (_t.getType())
/*      */           {
/*      */           case 59:
/*      */           case 71:
/* 2109 */             ast_suffix(_t);
/* 2110 */             _t = this._retTree;
/* 2111 */             break;
/*      */           case 3:
/* 2115 */             break;
/*      */           default:
/* 2119 */             throw new NoViableAltException(_t);
/*      */           }
/*      */ 
/* 2123 */           _t = __t135;
/* 2124 */           _t = _t.getNextSibling();
/* 2125 */           break;
/*      */         default:
/* 2129 */           throw new NoViableAltException(_t);
/*      */         }
/*      */ 
/* 2133 */         out(" ");
/* 2134 */         break;
/*      */       case 31:
/* 2138 */         GrammarAST tmp73_AST_in = (GrammarAST)_t;
/* 2139 */         match(_t, 31);
/* 2140 */         _t = _t.getNextSibling();
/* 2141 */         out(" $" + tmp73_AST_in.getText());
/* 2142 */         break;
/*      */       case 39:
/* 2146 */         AST __t137 = _t;
/* 2147 */         GrammarAST tmp74_AST_in = (GrammarAST)_t;
/* 2148 */         match(_t, 39);
/* 2149 */         _t = _t.getFirstChild();
/* 2150 */         GrammarAST tmp75_AST_in = (GrammarAST)_t;
/* 2151 */         match(_t, 21);
/* 2152 */         _t = _t.getNextSibling();
/* 2153 */         out(tmp75_AST_in.getText() + ".");
/* 2154 */         atom(_t);
/* 2155 */         _t = this._retTree;
/* 2156 */         _t = __t137;
/* 2157 */         _t = _t.getNextSibling();
/* 2158 */         break;
/*      */       default:
/* 2162 */         throw new NoViableAltException(_t);
/*      */       }
/*      */     }
/*      */     catch (RecognitionException ex)
/*      */     {
/* 2167 */       reportError(ex);
/* 2168 */       if (_t != null) _t = _t.getNextSibling();
/*      */     }
/* 2170 */     this._retTree = _t;
/*      */   }
/*      */ 
/*      */   public final void ebnf(AST _t) throws RecognitionException
/*      */   {
/* 2175 */     GrammarAST ebnf_AST_in = _t == ASTNULL ? null : (GrammarAST)_t;
/*      */     try
/*      */     {
/* 2178 */       if (_t == null) _t = ASTNULL;
/* 2179 */       switch (_t.getType())
/*      */       {
/*      */       case 9:
/* 2182 */         block(_t, true);
/* 2183 */         _t = this._retTree;
/* 2184 */         out(" ");
/* 2185 */         break;
/*      */       case 10:
/* 2189 */         AST __t116 = _t;
/* 2190 */         GrammarAST tmp76_AST_in = (GrammarAST)_t;
/* 2191 */         match(_t, 10);
/* 2192 */         _t = _t.getFirstChild();
/* 2193 */         block(_t, true);
/* 2194 */         _t = this._retTree;
/* 2195 */         _t = __t116;
/* 2196 */         _t = _t.getNextSibling();
/* 2197 */         out("? ");
/* 2198 */         break;
/*      */       case 11:
/* 2202 */         AST __t117 = _t;
/* 2203 */         GrammarAST tmp77_AST_in = (GrammarAST)_t;
/* 2204 */         match(_t, 11);
/* 2205 */         _t = _t.getFirstChild();
/* 2206 */         block(_t, true);
/* 2207 */         _t = this._retTree;
/* 2208 */         _t = __t117;
/* 2209 */         _t = _t.getNextSibling();
/* 2210 */         out("* ");
/* 2211 */         break;
/*      */       case 12:
/* 2215 */         AST __t118 = _t;
/* 2216 */         GrammarAST tmp78_AST_in = (GrammarAST)_t;
/* 2217 */         match(_t, 12);
/* 2218 */         _t = _t.getFirstChild();
/* 2219 */         block(_t, true);
/* 2220 */         _t = this._retTree;
/* 2221 */         _t = __t118;
/* 2222 */         _t = _t.getNextSibling();
/* 2223 */         out("+ ");
/* 2224 */         break;
/*      */       default:
/* 2228 */         throw new NoViableAltException(_t);
/*      */       }
/*      */     }
/*      */     catch (RecognitionException ex)
/*      */     {
/* 2233 */       reportError(ex);
/* 2234 */       if (_t != null) _t = _t.getNextSibling();
/*      */     }
/* 2236 */     this._retTree = _t;
/*      */   }
/*      */ 
/*      */   public final void tree(AST _t) throws RecognitionException
/*      */   {
/* 2241 */     GrammarAST tree_AST_in = _t == ASTNULL ? null : (GrammarAST)_t;
/*      */     try
/*      */     {
/* 2244 */       AST __t120 = _t;
/* 2245 */       GrammarAST tmp79_AST_in = (GrammarAST)_t;
/* 2246 */       match(_t, 75);
/* 2247 */       _t = _t.getFirstChild();
/* 2248 */       out(" ^(");
/* 2249 */       element(_t);
/* 2250 */       _t = this._retTree;
/*      */       while (true)
/*      */       {
/* 2254 */         if (_t == null) _t = ASTNULL;
/* 2255 */         if ((_t.getType() != 9) && (_t.getType() != 10) && (_t.getType() != 11) && (_t.getType() != 12) && (_t.getType() != 13) && (_t.getType() != 14) && (_t.getType() != 15) && (_t.getType() != 16) && (_t.getType() != 30) && (_t.getType() != 31) && (_t.getType() != 35) && (_t.getType() != 36) && (_t.getType() != 37) && (_t.getType() != 39) && (_t.getType() != 40) && (_t.getType() != 49) && (_t.getType() != 50) && (_t.getType() != 51) && (_t.getType() != 55) && (_t.getType() != 59) && (_t.getType() != 68) && (_t.getType() != 69) && (_t.getType() != 71) && (_t.getType() != 72) && (_t.getType() != 73) && (_t.getType() != 74) && (_t.getType() != 75)) break;
/* 2256 */         element(_t);
/* 2257 */         _t = this._retTree;
/*      */       }
/*      */ 
/* 2265 */       out(") ");
/* 2266 */       _t = __t120;
/* 2267 */       _t = _t.getNextSibling();
/*      */     }
/*      */     catch (RecognitionException ex) {
/* 2270 */       reportError(ex);
/* 2271 */       if (_t != null) _t = _t.getNextSibling();
/*      */     }
/* 2273 */     this._retTree = _t;
/*      */   }
/*      */ 
/*      */   public final void ast_suffix(AST _t) throws RecognitionException
/*      */   {
/* 2278 */     GrammarAST ast_suffix_AST_in = _t == ASTNULL ? null : (GrammarAST)_t;
/*      */     try
/*      */     {
/* 2281 */       if (_t == null) _t = ASTNULL;
/* 2282 */       switch (_t.getType())
/*      */       {
/*      */       case 71:
/* 2285 */         GrammarAST tmp80_AST_in = (GrammarAST)_t;
/* 2286 */         match(_t, 71);
/* 2287 */         _t = _t.getNextSibling();
/* 2288 */         out("^");
/* 2289 */         break;
/*      */       case 59:
/* 2293 */         GrammarAST tmp81_AST_in = (GrammarAST)_t;
/* 2294 */         match(_t, 59);
/* 2295 */         _t = _t.getNextSibling();
/* 2296 */         out("!");
/* 2297 */         break;
/*      */       default:
/* 2301 */         throw new NoViableAltException(_t);
/*      */       }
/*      */     }
/*      */     catch (RecognitionException ex)
/*      */     {
/* 2306 */       reportError(ex);
/* 2307 */       if (_t != null) _t = _t.getNextSibling();
/*      */     }
/* 2309 */     this._retTree = _t;
/*      */   }
/*      */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.antlr.grammar.v2.ANTLRTreePrinter
 * JD-Core Version:    0.6.2
 */