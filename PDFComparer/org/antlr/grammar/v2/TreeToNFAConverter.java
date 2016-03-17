/*      */ package org.antlr.grammar.v2;
/*      */ 
/*      */ import antlr.MismatchedTokenException;
/*      */ import antlr.NoViableAltException;
/*      */ import antlr.RecognitionException;
/*      */ import antlr.SemanticException;
/*      */ import antlr.Token;
/*      */ import antlr.TreeParser;
/*      */ import antlr.collections.AST;
/*      */ import antlr.collections.impl.BitSet;
/*      */ import java.util.LinkedList;
/*      */ import java.util.List;
/*      */ import org.antlr.analysis.Label;
/*      */ import org.antlr.analysis.NFA;
/*      */ import org.antlr.analysis.NFAState;
/*      */ import org.antlr.analysis.RuleClosureTransition;
/*      */ import org.antlr.analysis.StateCluster;
/*      */ import org.antlr.analysis.Transition;
/*      */ import org.antlr.misc.IntSet;
/*      */ import org.antlr.misc.IntervalSet;
/*      */ import org.antlr.tool.ErrorManager;
/*      */ import org.antlr.tool.Grammar;
/*      */ import org.antlr.tool.GrammarAST;
/*      */ import org.antlr.tool.NFAFactory;
/*      */ import org.antlr.tool.Rule;
/*      */ 
/*      */ public class TreeToNFAConverter extends TreeParser
/*      */   implements TreeToNFAConverterTokenTypes
/*      */ {
/*   55 */   protected NFAFactory factory = null;
/*      */ 
/*   58 */   protected NFA nfa = null;
/*      */ 
/*   61 */   protected Grammar grammar = null;
/*      */ 
/*   63 */   protected String currentRuleName = null;
/*      */ 
/*   65 */   protected int outerAltNum = 0;
/*   66 */   protected int blockLevel = 0;
/*      */ 
/* 2847 */   public static final String[] _tokenNames = { "<0>", "EOF", "<2>", "NULL_TREE_LOOKAHEAD", "\"options\"", "\"tokens\"", "\"parser\"", "LEXER", "RULE", "BLOCK", "OPTIONAL", "CLOSURE", "POSITIVE_CLOSURE", "SYNPRED", "RANGE", "CHAR_RANGE", "EPSILON", "ALT", "EOR", "EOB", "EOA", "ID", "ARG", "ARGLIST", "RET", "LEXER_GRAMMAR", "PARSER_GRAMMAR", "TREE_GRAMMAR", "COMBINED_GRAMMAR", "INITACTION", "FORCED_ACTION", "LABEL", "TEMPLATE", "\"scope\"", "\"import\"", "GATED_SEMPRED", "SYN_SEMPRED", "BACKTRACK_SEMPRED", "\"fragment\"", "DOT", "ACTION", "DOC_COMMENT", "SEMI", "\"lexer\"", "\"tree\"", "\"grammar\"", "AMPERSAND", "COLON", "RCURLY", "ASSIGN", "STRING_LITERAL", "CHAR_LITERAL", "INT", "STAR", "COMMA", "TOKEN_REF", "\"protected\"", "\"public\"", "\"private\"", "BANG", "ARG_ACTION", "\"returns\"", "\"throws\"", "LPAREN", "OR", "RPAREN", "\"catch\"", "\"finally\"", "PLUS_ASSIGN", "SEMPRED", "IMPLIES", "ROOT", "WILDCARD", "RULE_REF", "NOT", "TREE_BEGIN", "QUESTION", "PLUS", "OPEN_ELEMENT_OPTION", "CLOSE_ELEMENT_OPTION", "REWRITE", "ETC", "DOLLAR", "DOUBLE_QUOTE_STRING_LITERAL", "DOUBLE_ANGLE_STRING_LITERAL", "WS", "COMMENT", "SL_COMMENT", "ML_COMMENT", "STRAY_BRACKET", "ESC", "DIGIT", "XDIGIT", "NESTED_ARG_ACTION", "NESTED_ACTION", "ACTION_CHAR_LITERAL", "ACTION_STRING_LITERAL", "ACTION_ESC", "WS_LOOP", "INTERNAL_RULE_REF", "WS_OPT", "SRC" };
/*      */ 
/* 2956 */   public static final BitSet _tokenSet_0 = new BitSet(mk_tokenSet_0());
/*      */ 
/*      */   public TreeToNFAConverter(Grammar g, NFA nfa, NFAFactory factory)
/*      */   {
/*   69 */     this();
/*   70 */     this.grammar = g;
/*   71 */     this.nfa = nfa;
/*   72 */     this.factory = factory;
/*      */   }
/*      */ 
/*      */   protected void addFollowTransition(String ruleName, NFAState following)
/*      */   {
/*   98 */     Rule r = this.grammar.getRule(ruleName);
/*   99 */     NFAState end = r.stopState;
/*  100 */     while (end.transition(1) != null) {
/*  101 */       end = (NFAState)end.transition(1).target;
/*      */     }
/*  103 */     if (end.transition(0) != null)
/*      */     {
/*  106 */       NFAState n = this.factory.newState();
/*  107 */       Transition e = new Transition(-5, n);
/*  108 */       end.addTransition(e);
/*  109 */       end = n;
/*      */     }
/*  111 */     Transition followEdge = new Transition(-5, following);
/*  112 */     end.addTransition(followEdge);
/*      */   }
/*      */ 
/*      */   protected void finish() {
/*  116 */     List rules = new LinkedList();
/*  117 */     rules.addAll(this.grammar.getRules());
/*  118 */     int numEntryPoints = this.factory.build_EOFStates(rules);
/*  119 */     if (numEntryPoints == 0)
/*  120 */       ErrorManager.grammarWarning(138, this.grammar, null, this.grammar.name);
/*      */   }
/*      */ 
/*      */   public void reportError(RecognitionException ex)
/*      */   {
/*  128 */     Token token = null;
/*  129 */     if ((ex instanceof MismatchedTokenException)) {
/*  130 */       token = ((MismatchedTokenException)ex).token;
/*      */     }
/*  132 */     else if ((ex instanceof NoViableAltException)) {
/*  133 */       token = ((NoViableAltException)ex).token;
/*      */     }
/*  135 */     ErrorManager.syntaxError(100, this.grammar, token, "buildnfa: " + ex.toString(), ex);
/*      */   }
/*      */ 
/*      */   public TreeToNFAConverter()
/*      */   {
/*  143 */     this.tokenNames = _tokenNames;
/*      */   }
/*      */ 
/*      */   public final void grammar(AST _t) throws RecognitionException
/*      */   {
/*  148 */     GrammarAST grammar_AST_in = _t == ASTNULL ? null : (GrammarAST)_t;
/*      */     try
/*      */     {
/*  152 */       if (_t == null) _t = ASTNULL;
/*  153 */       switch (_t.getType())
/*      */       {
/*      */       case 25:
/*  156 */         AST __t3 = _t;
/*  157 */         GrammarAST tmp1_AST_in = (GrammarAST)_t;
/*  158 */         match(_t, 25);
/*  159 */         _t = _t.getFirstChild();
/*  160 */         grammarSpec(_t);
/*  161 */         _t = this._retTree;
/*  162 */         _t = __t3;
/*  163 */         _t = _t.getNextSibling();
/*  164 */         break;
/*      */       case 26:
/*  168 */         AST __t4 = _t;
/*  169 */         GrammarAST tmp2_AST_in = (GrammarAST)_t;
/*  170 */         match(_t, 26);
/*  171 */         _t = _t.getFirstChild();
/*  172 */         grammarSpec(_t);
/*  173 */         _t = this._retTree;
/*  174 */         _t = __t4;
/*  175 */         _t = _t.getNextSibling();
/*  176 */         break;
/*      */       case 27:
/*  180 */         AST __t5 = _t;
/*  181 */         GrammarAST tmp3_AST_in = (GrammarAST)_t;
/*  182 */         match(_t, 27);
/*  183 */         _t = _t.getFirstChild();
/*  184 */         grammarSpec(_t);
/*  185 */         _t = this._retTree;
/*  186 */         _t = __t5;
/*  187 */         _t = _t.getNextSibling();
/*  188 */         break;
/*      */       case 28:
/*  192 */         AST __t6 = _t;
/*  193 */         GrammarAST tmp4_AST_in = (GrammarAST)_t;
/*  194 */         match(_t, 28);
/*  195 */         _t = _t.getFirstChild();
/*  196 */         grammarSpec(_t);
/*  197 */         _t = this._retTree;
/*  198 */         _t = __t6;
/*  199 */         _t = _t.getNextSibling();
/*  200 */         break;
/*      */       default:
/*  204 */         throw new NoViableAltException(_t);
/*      */       }
/*      */ 
/*  208 */       finish();
/*      */     }
/*      */     catch (RecognitionException ex) {
/*  211 */       reportError(ex);
/*  212 */       if (_t != null) _t = _t.getNextSibling();
/*      */     }
/*  214 */     this._retTree = _t;
/*      */   }
/*      */ 
/*      */   public final void grammarSpec(AST _t) throws RecognitionException
/*      */   {
/*  219 */     GrammarAST grammarSpec_AST_in = _t == ASTNULL ? null : (GrammarAST)_t;
/*  220 */     GrammarAST cmt = null;
/*      */     try
/*      */     {
/*  223 */       GrammarAST tmp5_AST_in = (GrammarAST)_t;
/*  224 */       match(_t, 21);
/*  225 */       _t = _t.getNextSibling();
/*      */ 
/*  227 */       if (_t == null) _t = ASTNULL;
/*  228 */       switch (_t.getType())
/*      */       {
/*      */       case 41:
/*  231 */         cmt = (GrammarAST)_t;
/*  232 */         match(_t, 41);
/*  233 */         _t = _t.getNextSibling();
/*  234 */         break;
/*      */       case 4:
/*      */       case 5:
/*      */       case 8:
/*      */       case 33:
/*      */       case 34:
/*      */       case 46:
/*  243 */         break;
/*      */       default:
/*  247 */         throw new NoViableAltException(_t);
/*      */       }
/*      */ 
/*  252 */       if (_t == null) _t = ASTNULL;
/*  253 */       switch (_t.getType())
/*      */       {
/*      */       case 4:
/*  256 */         AST __t12 = _t;
/*  257 */         GrammarAST tmp6_AST_in = (GrammarAST)_t;
/*  258 */         match(_t, 4);
/*  259 */         _t = _t.getFirstChild();
/*  260 */         GrammarAST tmp7_AST_in = (GrammarAST)_t;
/*  261 */         if (_t == null) throw new MismatchedTokenException();
/*  262 */         _t = _t.getNextSibling();
/*  263 */         _t = __t12;
/*  264 */         _t = _t.getNextSibling();
/*  265 */         break;
/*      */       case 5:
/*      */       case 8:
/*      */       case 33:
/*      */       case 34:
/*      */       case 46:
/*  273 */         break;
/*      */       default:
/*  277 */         throw new NoViableAltException(_t);
/*      */       }
/*      */ 
/*  282 */       if (_t == null) _t = ASTNULL;
/*  283 */       switch (_t.getType())
/*      */       {
/*      */       case 34:
/*  286 */         AST __t14 = _t;
/*  287 */         GrammarAST tmp8_AST_in = (GrammarAST)_t;
/*  288 */         match(_t, 34);
/*  289 */         _t = _t.getFirstChild();
/*  290 */         GrammarAST tmp9_AST_in = (GrammarAST)_t;
/*  291 */         if (_t == null) throw new MismatchedTokenException();
/*  292 */         _t = _t.getNextSibling();
/*  293 */         _t = __t14;
/*  294 */         _t = _t.getNextSibling();
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
/*  315 */         AST __t16 = _t;
/*  316 */         GrammarAST tmp10_AST_in = (GrammarAST)_t;
/*  317 */         match(_t, 5);
/*  318 */         _t = _t.getFirstChild();
/*  319 */         GrammarAST tmp11_AST_in = (GrammarAST)_t;
/*  320 */         if (_t == null) throw new MismatchedTokenException();
/*  321 */         _t = _t.getNextSibling();
/*  322 */         _t = __t16;
/*  323 */         _t = _t.getNextSibling();
/*  324 */         break;
/*      */       case 8:
/*      */       case 33:
/*      */       case 46:
/*  330 */         break;
/*      */       default:
/*  334 */         throw new NoViableAltException(_t);
/*      */       }
/*      */ 
/*      */       while (true)
/*      */       {
/*  341 */         if (_t == null) _t = ASTNULL;
/*  342 */         if (_t.getType() != 33) break;
/*  343 */         attrScope(_t);
/*  344 */         _t = this._retTree;
/*      */       }
/*      */ 
/*      */       while (true)
/*      */       {
/*  355 */         if (_t == null) _t = ASTNULL;
/*  356 */         if (_t.getType() != 46) break;
/*  357 */         GrammarAST tmp12_AST_in = (GrammarAST)_t;
/*  358 */         match(_t, 46);
/*  359 */         _t = _t.getNextSibling();
/*      */       }
/*      */ 
/*  367 */       rules(_t);
/*  368 */       _t = this._retTree;
/*      */     }
/*      */     catch (RecognitionException ex) {
/*  371 */       reportError(ex);
/*  372 */       if (_t != null) _t = _t.getNextSibling();
/*      */     }
/*  374 */     this._retTree = _t;
/*      */   }
/*      */ 
/*      */   public final void attrScope(AST _t) throws RecognitionException
/*      */   {
/*  379 */     GrammarAST attrScope_AST_in = _t == ASTNULL ? null : (GrammarAST)_t;
/*      */     try
/*      */     {
/*  382 */       AST __t8 = _t;
/*  383 */       GrammarAST tmp13_AST_in = (GrammarAST)_t;
/*  384 */       match(_t, 33);
/*  385 */       _t = _t.getFirstChild();
/*  386 */       GrammarAST tmp14_AST_in = (GrammarAST)_t;
/*  387 */       match(_t, 21);
/*  388 */       _t = _t.getNextSibling();
/*  389 */       GrammarAST tmp15_AST_in = (GrammarAST)_t;
/*  390 */       match(_t, 40);
/*  391 */       _t = _t.getNextSibling();
/*  392 */       _t = __t8;
/*  393 */       _t = _t.getNextSibling();
/*      */     }
/*      */     catch (RecognitionException ex) {
/*  396 */       reportError(ex);
/*  397 */       if (_t != null) _t = _t.getNextSibling();
/*      */     }
/*  399 */     this._retTree = _t;
/*      */   }
/*      */ 
/*      */   public final void rules(AST _t) throws RecognitionException
/*      */   {
/*  404 */     GrammarAST rules_AST_in = _t == ASTNULL ? null : (GrammarAST)_t;
/*      */     try
/*      */     {
/*  408 */       int _cnt23 = 0;
/*      */       while (true)
/*      */       {
/*  411 */         if (_t == null) _t = ASTNULL;
/*  412 */         if (_t.getType() == 8) {
/*  413 */           rule(_t);
/*  414 */           _t = this._retTree;
/*      */         }
/*      */         else {
/*  417 */           if (_cnt23 >= 1) break; throw new NoViableAltException(_t);
/*      */         }
/*      */ 
/*  420 */         _cnt23++;
/*      */       }
/*      */     }
/*      */     catch (RecognitionException ex)
/*      */     {
/*  425 */       reportError(ex);
/*  426 */       if (_t != null) _t = _t.getNextSibling();
/*      */     }
/*  428 */     this._retTree = _t;
/*      */   }
/*      */ 
/*      */   public final void rule(AST _t) throws RecognitionException
/*      */   {
/*  433 */     GrammarAST rule_AST_in = _t == ASTNULL ? null : (GrammarAST)_t;
/*  434 */     GrammarAST id = null;
/*      */ 
/*  436 */     StateCluster g = null;
/*  437 */     StateCluster b = null;
/*  438 */     String r = null;
/*      */     try
/*      */     {
/*  442 */       AST __t25 = _t;
/*  443 */       GrammarAST tmp16_AST_in = (GrammarAST)_t;
/*  444 */       match(_t, 8);
/*  445 */       _t = _t.getFirstChild();
/*  446 */       id = (GrammarAST)_t;
/*  447 */       match(_t, 21);
/*  448 */       _t = _t.getNextSibling();
/*  449 */       r = id.getText();
/*      */ 
/*  451 */       this.currentRuleName = r;
/*  452 */       this.factory.setCurrentRule(this.grammar.getLocallyDefinedRule(r));
/*      */ 
/*  455 */       if (_t == null) _t = ASTNULL;
/*  456 */       switch (_t.getType())
/*      */       {
/*      */       case 38:
/*      */       case 56:
/*      */       case 57:
/*      */       case 58:
/*  462 */         modifier(_t);
/*  463 */         _t = this._retTree;
/*  464 */         break;
/*      */       case 22:
/*  468 */         break;
/*      */       default:
/*  472 */         throw new NoViableAltException(_t);
/*      */       }
/*      */ 
/*  477 */       GrammarAST tmp17_AST_in = (GrammarAST)_t;
/*  478 */       match(_t, 22);
/*  479 */       _t = _t.getNextSibling();
/*      */ 
/*  481 */       if (_t == null) _t = ASTNULL;
/*  482 */       switch (_t.getType())
/*      */       {
/*      */       case 60:
/*  485 */         GrammarAST tmp18_AST_in = (GrammarAST)_t;
/*  486 */         match(_t, 60);
/*  487 */         _t = _t.getNextSibling();
/*  488 */         break;
/*      */       case 24:
/*  492 */         break;
/*      */       default:
/*  496 */         throw new NoViableAltException(_t);
/*      */       }
/*      */ 
/*  502 */       GrammarAST tmp19_AST_in = (GrammarAST)_t;
/*  503 */       match(_t, 24);
/*  504 */       _t = _t.getNextSibling();
/*      */ 
/*  506 */       if (_t == null) _t = ASTNULL;
/*  507 */       switch (_t.getType())
/*      */       {
/*      */       case 60:
/*  510 */         GrammarAST tmp20_AST_in = (GrammarAST)_t;
/*  511 */         match(_t, 60);
/*  512 */         _t = _t.getNextSibling();
/*  513 */         break;
/*      */       case 4:
/*      */       case 9:
/*      */       case 33:
/*      */       case 46:
/*  520 */         break;
/*      */       default:
/*  524 */         throw new NoViableAltException(_t);
/*      */       }
/*      */ 
/*  530 */       if (_t == null) _t = ASTNULL;
/*  531 */       switch (_t.getType())
/*      */       {
/*      */       case 4:
/*  534 */         GrammarAST tmp21_AST_in = (GrammarAST)_t;
/*  535 */         match(_t, 4);
/*  536 */         _t = _t.getNextSibling();
/*  537 */         break;
/*      */       case 9:
/*      */       case 33:
/*      */       case 46:
/*  543 */         break;
/*      */       default:
/*  547 */         throw new NoViableAltException(_t);
/*      */       }
/*      */ 
/*  552 */       if (_t == null) _t = ASTNULL;
/*  553 */       switch (_t.getType())
/*      */       {
/*      */       case 33:
/*  556 */         ruleScopeSpec(_t);
/*  557 */         _t = this._retTree;
/*  558 */         break;
/*      */       case 9:
/*      */       case 46:
/*  563 */         break;
/*      */       default:
/*  567 */         throw new NoViableAltException(_t);
/*      */       }
/*      */ 
/*      */       while (true)
/*      */       {
/*  574 */         if (_t == null) _t = ASTNULL;
/*  575 */         if (_t.getType() != 46) break;
/*  576 */         GrammarAST tmp22_AST_in = (GrammarAST)_t;
/*  577 */         match(_t, 46);
/*  578 */         _t = _t.getNextSibling();
/*      */       }
/*      */ 
/*  586 */       GrammarAST blk = (GrammarAST)_t;
/*  587 */       b = block(_t);
/*  588 */       _t = this._retTree;
/*      */ 
/*  590 */       if (_t == null) _t = ASTNULL;
/*  591 */       switch (_t.getType())
/*      */       {
/*      */       case 66:
/*      */       case 67:
/*  595 */         exceptionGroup(_t);
/*  596 */         _t = this._retTree;
/*  597 */         break;
/*      */       case 18:
/*  601 */         break;
/*      */       default:
/*  605 */         throw new NoViableAltException(_t);
/*      */       }
/*      */ 
/*  609 */       GrammarAST tmp23_AST_in = (GrammarAST)_t;
/*  610 */       match(_t, 18);
/*  611 */       _t = _t.getNextSibling();
/*      */ 
/*  613 */       if (blk.getSetValue() != null)
/*      */       {
/*  616 */         b = this.factory.build_AlternativeBlockFromSet(b);
/*      */       }
/*  618 */       if ((Character.isLowerCase(r.charAt(0))) || (this.grammar.type == 1))
/*      */       {
/*  622 */         Rule thisR = this.grammar.getLocallyDefinedRule(r);
/*  623 */         NFAState start = thisR.startState;
/*  624 */         start.associatedASTNode = id;
/*  625 */         start.addTransition(new Transition(-5, b.left));
/*      */ 
/*  628 */         if (this.grammar.getNumberOfAltsForDecisionNFA(b.left) > 1) {
/*  629 */           b.left.setDescription(this.grammar.grammarTreeToString(rule_AST_in, false));
/*  630 */           b.left.setDecisionASTNode(blk);
/*  631 */           int d = this.grammar.assignDecisionNumber(b.left);
/*  632 */           this.grammar.setDecisionNFA(d, b.left);
/*  633 */           this.grammar.setDecisionBlockAST(d, blk);
/*      */         }
/*      */ 
/*  637 */         NFAState end = thisR.stopState;
/*  638 */         b.right.addTransition(new Transition(-5, end));
/*      */       }
/*      */ 
/*  641 */       _t = __t25;
/*  642 */       _t = _t.getNextSibling();
/*      */     }
/*      */     catch (RecognitionException ex) {
/*  645 */       reportError(ex);
/*  646 */       if (_t != null) _t = _t.getNextSibling();
/*      */     }
/*  648 */     this._retTree = _t;
/*      */   }
/*      */ 
/*      */   public final void modifier(AST _t) throws RecognitionException
/*      */   {
/*  653 */     GrammarAST modifier_AST_in = _t == ASTNULL ? null : (GrammarAST)_t;
/*      */     try
/*      */     {
/*  656 */       if (_t == null) _t = ASTNULL;
/*  657 */       switch (_t.getType())
/*      */       {
/*      */       case 56:
/*  660 */         GrammarAST tmp24_AST_in = (GrammarAST)_t;
/*  661 */         match(_t, 56);
/*  662 */         _t = _t.getNextSibling();
/*  663 */         break;
/*      */       case 57:
/*  667 */         GrammarAST tmp25_AST_in = (GrammarAST)_t;
/*  668 */         match(_t, 57);
/*  669 */         _t = _t.getNextSibling();
/*  670 */         break;
/*      */       case 58:
/*  674 */         GrammarAST tmp26_AST_in = (GrammarAST)_t;
/*  675 */         match(_t, 58);
/*  676 */         _t = _t.getNextSibling();
/*  677 */         break;
/*      */       case 38:
/*  681 */         GrammarAST tmp27_AST_in = (GrammarAST)_t;
/*  682 */         match(_t, 38);
/*  683 */         _t = _t.getNextSibling();
/*  684 */         break;
/*      */       default:
/*  688 */         throw new NoViableAltException(_t);
/*      */       }
/*      */     }
/*      */     catch (RecognitionException ex)
/*      */     {
/*  693 */       reportError(ex);
/*  694 */       if (_t != null) _t = _t.getNextSibling();
/*      */     }
/*  696 */     this._retTree = _t;
/*      */   }
/*      */ 
/*      */   public final void ruleScopeSpec(AST _t) throws RecognitionException
/*      */   {
/*  701 */     GrammarAST ruleScopeSpec_AST_in = _t == ASTNULL ? null : (GrammarAST)_t;
/*      */     try
/*      */     {
/*  704 */       AST __t38 = _t;
/*  705 */       GrammarAST tmp28_AST_in = (GrammarAST)_t;
/*  706 */       match(_t, 33);
/*  707 */       _t = _t.getFirstChild();
/*      */ 
/*  709 */       if (_t == null) _t = ASTNULL;
/*  710 */       switch (_t.getType())
/*      */       {
/*      */       case 40:
/*  713 */         GrammarAST tmp29_AST_in = (GrammarAST)_t;
/*  714 */         match(_t, 40);
/*  715 */         _t = _t.getNextSibling();
/*  716 */         break;
/*      */       case 3:
/*      */       case 21:
/*  721 */         break;
/*      */       default:
/*  725 */         throw new NoViableAltException(_t);
/*      */       }
/*      */ 
/*      */       while (true)
/*      */       {
/*  732 */         if (_t == null) _t = ASTNULL;
/*  733 */         if (_t.getType() != 21) break;
/*  734 */         GrammarAST tmp30_AST_in = (GrammarAST)_t;
/*  735 */         match(_t, 21);
/*  736 */         _t = _t.getNextSibling();
/*      */       }
/*      */ 
/*  744 */       _t = __t38;
/*  745 */       _t = _t.getNextSibling();
/*      */     }
/*      */     catch (RecognitionException ex) {
/*  748 */       reportError(ex);
/*  749 */       if (_t != null) _t = _t.getNextSibling();
/*      */     }
/*  751 */     this._retTree = _t;
/*      */   }
/*      */ 
/*      */   public final StateCluster block(AST _t) throws RecognitionException {
/*  755 */     StateCluster g = null;
/*      */ 
/*  757 */     GrammarAST block_AST_in = _t == ASTNULL ? null : (GrammarAST)_t;
/*      */ 
/*  759 */     StateCluster a = null;
/*  760 */     List alts = new LinkedList();
/*  761 */     this.blockLevel += 1;
/*  762 */     if (this.blockLevel == 1) this.outerAltNum = 1;
/*      */ 
/*      */     try
/*      */     {
/*  766 */       if (_t == null) _t = ASTNULL;
/*  767 */       if ((_t.getType() == 9) && (this.grammar.isValidSet(this, block_AST_in)) && (!this.currentRuleName.equals("Tokens")))
/*      */       {
/*  769 */         g = set(_t);
/*  770 */         _t = this._retTree;
/*  771 */         this.blockLevel -= 1;
/*      */       }
/*  773 */       else if (_t.getType() == 9) {
/*  774 */         AST __t43 = _t;
/*  775 */         GrammarAST tmp31_AST_in = (GrammarAST)_t;
/*  776 */         match(_t, 9);
/*  777 */         _t = _t.getFirstChild();
/*      */ 
/*  779 */         if (_t == null) _t = ASTNULL;
/*  780 */         switch (_t.getType())
/*      */         {
/*      */         case 4:
/*  783 */           GrammarAST tmp32_AST_in = (GrammarAST)_t;
/*  784 */           match(_t, 4);
/*  785 */           _t = _t.getNextSibling();
/*  786 */           break;
/*      */         case 17:
/*  790 */           break;
/*      */         default:
/*  794 */           throw new NoViableAltException(_t);
/*      */         }
/*      */ 
/*  799 */         int _cnt46 = 0;
/*      */         while (true)
/*      */         {
/*  802 */           if (_t == null) _t = ASTNULL;
/*  803 */           if (_t.getType() == 17) {
/*  804 */             a = alternative(_t);
/*  805 */             _t = this._retTree;
/*  806 */             rewrite(_t);
/*  807 */             _t = this._retTree;
/*      */ 
/*  809 */             alts.add(a);
/*  810 */             if (this.blockLevel == 1) this.outerAltNum += 1;
/*      */           }
/*      */           else
/*      */           {
/*  814 */             if (_cnt46 >= 1) break; throw new NoViableAltException(_t);
/*      */           }
/*      */ 
/*  817 */           _cnt46++;
/*      */         }
/*      */ 
/*  820 */         GrammarAST tmp33_AST_in = (GrammarAST)_t;
/*  821 */         match(_t, 19);
/*  822 */         _t = _t.getNextSibling();
/*  823 */         _t = __t43;
/*  824 */         _t = _t.getNextSibling();
/*  825 */         g = this.factory.build_AlternativeBlock(alts);
/*  826 */         this.blockLevel -= 1;
/*      */       }
/*      */       else {
/*  829 */         throw new NoViableAltException(_t);
/*      */       }
/*      */     }
/*      */     catch (RecognitionException ex)
/*      */     {
/*  834 */       reportError(ex);
/*  835 */       if (_t != null) _t = _t.getNextSibling();
/*      */     }
/*  837 */     this._retTree = _t;
/*  838 */     return g;
/*      */   }
/*      */ 
/*      */   public final void exceptionGroup(AST _t) throws RecognitionException
/*      */   {
/*  843 */     GrammarAST exceptionGroup_AST_in = _t == ASTNULL ? null : (GrammarAST)_t;
/*      */     try
/*      */     {
/*  846 */       if (_t == null) _t = ASTNULL;
/*  847 */       switch (_t.getType())
/*      */       {
/*      */       case 66:
/*  851 */         int _cnt53 = 0;
/*      */         while (true)
/*      */         {
/*  854 */           if (_t == null) _t = ASTNULL;
/*  855 */           if (_t.getType() == 66) {
/*  856 */             exceptionHandler(_t);
/*  857 */             _t = this._retTree;
/*      */           }
/*      */           else {
/*  860 */             if (_cnt53 >= 1) break; throw new NoViableAltException(_t);
/*      */           }
/*      */ 
/*  863 */           _cnt53++;
/*      */         }
/*      */ 
/*  867 */         if (_t == null) _t = ASTNULL;
/*  868 */         switch (_t.getType())
/*      */         {
/*      */         case 67:
/*  871 */           finallyClause(_t);
/*  872 */           _t = this._retTree;
/*  873 */           break;
/*      */         case 18:
/*  877 */           break;
/*      */         default:
/*  881 */           throw new NoViableAltException(_t);
/*      */         }
/*      */ 
/*      */         break;
/*      */       case 67:
/*  889 */         finallyClause(_t);
/*  890 */         _t = this._retTree;
/*  891 */         break;
/*      */       default:
/*  895 */         throw new NoViableAltException(_t);
/*      */       }
/*      */     }
/*      */     catch (RecognitionException ex)
/*      */     {
/*  900 */       reportError(ex);
/*  901 */       if (_t != null) _t = _t.getNextSibling();
/*      */     }
/*  903 */     this._retTree = _t;
/*      */   }
/*      */ 
/*      */   public final StateCluster set(AST _t) throws RecognitionException {
/*  907 */     StateCluster g = null;
/*      */ 
/*  909 */     GrammarAST set_AST_in = _t == ASTNULL ? null : (GrammarAST)_t;
/*  910 */     GrammarAST b = null;
/*      */ 
/*  912 */     IntSet elements = new IntervalSet();
/*  913 */     set_AST_in.setSetValue(elements);
/*      */     try
/*      */     {
/*  917 */       AST __t102 = _t;
/*  918 */       b = _t == ASTNULL ? null : (GrammarAST)_t;
/*  919 */       match(_t, 9);
/*  920 */       _t = _t.getFirstChild();
/*      */ 
/*  922 */       int _cnt106 = 0;
/*      */       while (true)
/*      */       {
/*  925 */         if (_t == null) _t = ASTNULL;
/*  926 */         if (_t.getType() == 17) {
/*  927 */           AST __t104 = _t;
/*  928 */           GrammarAST tmp34_AST_in = (GrammarAST)_t;
/*  929 */           match(_t, 17);
/*  930 */           _t = _t.getFirstChild();
/*      */ 
/*  932 */           if (_t == null) _t = ASTNULL;
/*  933 */           switch (_t.getType())
/*      */           {
/*      */           case 37:
/*  936 */             GrammarAST tmp35_AST_in = (GrammarAST)_t;
/*  937 */             match(_t, 37);
/*  938 */             _t = _t.getNextSibling();
/*  939 */             break;
/*      */           case 9:
/*      */           case 15:
/*      */           case 50:
/*      */           case 51:
/*      */           case 55:
/*      */           case 74:
/*  948 */             break;
/*      */           default:
/*  952 */             throw new NoViableAltException(_t);
/*      */           }
/*      */ 
/*  956 */           setElement(_t, elements);
/*  957 */           _t = this._retTree;
/*  958 */           GrammarAST tmp36_AST_in = (GrammarAST)_t;
/*  959 */           match(_t, 20);
/*  960 */           _t = _t.getNextSibling();
/*  961 */           _t = __t104;
/*  962 */           _t = _t.getNextSibling();
/*      */         }
/*      */         else {
/*  965 */           if (_cnt106 >= 1) break; throw new NoViableAltException(_t);
/*      */         }
/*      */ 
/*  968 */         _cnt106++;
/*      */       }
/*      */ 
/*  971 */       GrammarAST tmp37_AST_in = (GrammarAST)_t;
/*  972 */       match(_t, 19);
/*  973 */       _t = _t.getNextSibling();
/*  974 */       _t = __t102;
/*  975 */       _t = _t.getNextSibling();
/*      */ 
/*  977 */       g = this.factory.build_Set(elements, b);
/*  978 */       b.followingNFAState = g.right;
/*  979 */       b.setSetValue(elements);
/*      */     }
/*      */     catch (RecognitionException ex)
/*      */     {
/*  983 */       reportError(ex);
/*  984 */       if (_t != null) _t = _t.getNextSibling();
/*      */     }
/*  986 */     this._retTree = _t;
/*  987 */     return g;
/*      */   }
/*      */ 
/*      */   public final StateCluster alternative(AST _t) throws RecognitionException {
/*  991 */     StateCluster g = null;
/*      */ 
/*  993 */     GrammarAST alternative_AST_in = _t == ASTNULL ? null : (GrammarAST)_t;
/*      */ 
/*  995 */     StateCluster e = null;
/*      */     try
/*      */     {
/*  999 */       AST __t48 = _t;
/* 1000 */       GrammarAST tmp38_AST_in = (GrammarAST)_t;
/* 1001 */       match(_t, 17);
/* 1002 */       _t = _t.getFirstChild();
/*      */ 
/* 1004 */       int _cnt50 = 0;
/*      */       while (true)
/*      */       {
/* 1007 */         if (_t == null) _t = ASTNULL;
/* 1008 */         if (_tokenSet_0.member(_t.getType())) {
/* 1009 */           e = element(_t);
/* 1010 */           _t = this._retTree;
/* 1011 */           g = this.factory.build_AB(g, e);
/*      */         }
/*      */         else {
/* 1014 */           if (_cnt50 >= 1) break; throw new NoViableAltException(_t);
/*      */         }
/*      */ 
/* 1017 */         _cnt50++;
/*      */       }
/*      */ 
/* 1020 */       _t = __t48;
/* 1021 */       _t = _t.getNextSibling();
/*      */ 
/* 1023 */       if (g == null) {
/* 1024 */         g = this.factory.build_Epsilon();
/*      */       }
/*      */       else {
/* 1027 */         this.factory.optimizeAlternative(g);
/*      */       }
/*      */     }
/*      */     catch (RecognitionException ex)
/*      */     {
/* 1032 */       reportError(ex);
/* 1033 */       if (_t != null) _t = _t.getNextSibling();
/*      */     }
/* 1035 */     this._retTree = _t;
/* 1036 */     return g;
/*      */   }
/*      */ 
/*      */   public final void rewrite(AST _t) throws RecognitionException
/*      */   {
/* 1041 */     GrammarAST rewrite_AST_in = _t == ASTNULL ? null : (GrammarAST)_t;
/*      */     try
/*      */     {
/*      */       while (true)
/*      */       {
/* 1047 */         if (_t == null) _t = ASTNULL;
/* 1048 */         if (_t.getType() != 80)
/*      */           break;
/* 1050 */         if (this.grammar.getOption("output") == null) {
/* 1051 */           ErrorManager.grammarError(149, this.grammar, rewrite_AST_in.token, this.currentRuleName);
/*      */         }
/*      */ 
/* 1055 */         AST __t61 = _t;
/* 1056 */         GrammarAST tmp39_AST_in = (GrammarAST)_t;
/* 1057 */         match(_t, 80);
/* 1058 */         _t = _t.getFirstChild();
/*      */ 
/* 1060 */         if (_t == null) _t = ASTNULL;
/* 1061 */         switch (_t.getType())
/*      */         {
/*      */         case 69:
/* 1064 */           GrammarAST tmp40_AST_in = (GrammarAST)_t;
/* 1065 */           match(_t, 69);
/* 1066 */           _t = _t.getNextSibling();
/* 1067 */           break;
/*      */         case 17:
/*      */         case 32:
/*      */         case 40:
/*      */         case 81:
/* 1074 */           break;
/*      */         default:
/* 1078 */           throw new NoViableAltException(_t);
/*      */         }
/*      */ 
/* 1083 */         if (_t == null) _t = ASTNULL;
/* 1084 */         switch (_t.getType())
/*      */         {
/*      */         case 17:
/* 1087 */           GrammarAST tmp41_AST_in = (GrammarAST)_t;
/* 1088 */           match(_t, 17);
/* 1089 */           _t = _t.getNextSibling();
/* 1090 */           break;
/*      */         case 32:
/* 1094 */           GrammarAST tmp42_AST_in = (GrammarAST)_t;
/* 1095 */           match(_t, 32);
/* 1096 */           _t = _t.getNextSibling();
/* 1097 */           break;
/*      */         case 40:
/* 1101 */           GrammarAST tmp43_AST_in = (GrammarAST)_t;
/* 1102 */           match(_t, 40);
/* 1103 */           _t = _t.getNextSibling();
/* 1104 */           break;
/*      */         case 81:
/* 1108 */           GrammarAST tmp44_AST_in = (GrammarAST)_t;
/* 1109 */           match(_t, 81);
/* 1110 */           _t = _t.getNextSibling();
/* 1111 */           break;
/*      */         default:
/* 1115 */           throw new NoViableAltException(_t);
/*      */         }
/*      */ 
/* 1119 */         _t = __t61;
/* 1120 */         _t = _t.getNextSibling();
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (RecognitionException ex)
/*      */     {
/* 1130 */       reportError(ex);
/* 1131 */       if (_t != null) _t = _t.getNextSibling();
/*      */     }
/* 1133 */     this._retTree = _t;
/*      */   }
/*      */ 
/*      */   public final StateCluster element(AST _t) throws RecognitionException {
/* 1137 */     StateCluster g = null;
/*      */ 
/* 1139 */     GrammarAST element_AST_in = _t == ASTNULL ? null : (GrammarAST)_t;
/* 1140 */     GrammarAST a = null;
/* 1141 */     GrammarAST b = null;
/* 1142 */     GrammarAST c1 = null;
/* 1143 */     GrammarAST c2 = null;
/* 1144 */     GrammarAST pred = null;
/* 1145 */     GrammarAST spred = null;
/* 1146 */     GrammarAST bpred = null;
/* 1147 */     GrammarAST gpred = null;
/*      */     try
/*      */     {
/* 1150 */       if (_t == null) _t = ASTNULL;
/* 1151 */       switch (_t.getType())
/*      */       {
/*      */       case 71:
/* 1154 */         AST __t66 = _t;
/* 1155 */         GrammarAST tmp45_AST_in = (GrammarAST)_t;
/* 1156 */         match(_t, 71);
/* 1157 */         _t = _t.getFirstChild();
/* 1158 */         g = element(_t);
/* 1159 */         _t = this._retTree;
/* 1160 */         _t = __t66;
/* 1161 */         _t = _t.getNextSibling();
/* 1162 */         break;
/*      */       case 59:
/* 1166 */         AST __t67 = _t;
/* 1167 */         GrammarAST tmp46_AST_in = (GrammarAST)_t;
/* 1168 */         match(_t, 59);
/* 1169 */         _t = _t.getFirstChild();
/* 1170 */         g = element(_t);
/* 1171 */         _t = this._retTree;
/* 1172 */         _t = __t67;
/* 1173 */         _t = _t.getNextSibling();
/* 1174 */         break;
/*      */       case 49:
/* 1178 */         AST __t68 = _t;
/* 1179 */         GrammarAST tmp47_AST_in = (GrammarAST)_t;
/* 1180 */         match(_t, 49);
/* 1181 */         _t = _t.getFirstChild();
/* 1182 */         GrammarAST tmp48_AST_in = (GrammarAST)_t;
/* 1183 */         match(_t, 21);
/* 1184 */         _t = _t.getNextSibling();
/* 1185 */         g = element(_t);
/* 1186 */         _t = this._retTree;
/* 1187 */         _t = __t68;
/* 1188 */         _t = _t.getNextSibling();
/* 1189 */         break;
/*      */       case 68:
/* 1193 */         AST __t69 = _t;
/* 1194 */         GrammarAST tmp49_AST_in = (GrammarAST)_t;
/* 1195 */         match(_t, 68);
/* 1196 */         _t = _t.getFirstChild();
/* 1197 */         GrammarAST tmp50_AST_in = (GrammarAST)_t;
/* 1198 */         match(_t, 21);
/* 1199 */         _t = _t.getNextSibling();
/* 1200 */         g = element(_t);
/* 1201 */         _t = this._retTree;
/* 1202 */         _t = __t69;
/* 1203 */         _t = _t.getNextSibling();
/* 1204 */         break;
/*      */       case 14:
/* 1208 */         AST __t70 = _t;
/* 1209 */         GrammarAST tmp51_AST_in = (GrammarAST)_t;
/* 1210 */         match(_t, 14);
/* 1211 */         _t = _t.getFirstChild();
/* 1212 */         a = _t == ASTNULL ? null : (GrammarAST)_t;
/* 1213 */         atom(_t, null);
/* 1214 */         _t = this._retTree;
/* 1215 */         b = _t == ASTNULL ? null : (GrammarAST)_t;
/* 1216 */         atom(_t, null);
/* 1217 */         _t = this._retTree;
/* 1218 */         _t = __t70;
/* 1219 */         _t = _t.getNextSibling();
/* 1220 */         g = this.factory.build_Range(this.grammar.getTokenType(a.getText()), this.grammar.getTokenType(b.getText()));
/*      */ 
/* 1222 */         break;
/*      */       case 15:
/* 1226 */         AST __t71 = _t;
/* 1227 */         GrammarAST tmp52_AST_in = (GrammarAST)_t;
/* 1228 */         match(_t, 15);
/* 1229 */         _t = _t.getFirstChild();
/* 1230 */         c1 = (GrammarAST)_t;
/* 1231 */         match(_t, 51);
/* 1232 */         _t = _t.getNextSibling();
/* 1233 */         c2 = (GrammarAST)_t;
/* 1234 */         match(_t, 51);
/* 1235 */         _t = _t.getNextSibling();
/* 1236 */         _t = __t71;
/* 1237 */         _t = _t.getNextSibling();
/*      */ 
/* 1239 */         if (this.grammar.type == 1)
/* 1240 */           g = this.factory.build_CharRange(c1.getText(), c2.getText()); break;
/*      */       case 39:
/*      */       case 50:
/*      */       case 51:
/*      */       case 55:
/*      */       case 72:
/*      */       case 73:
/*      */       case 74:
/* 1253 */         g = atom_or_notatom(_t);
/* 1254 */         _t = this._retTree;
/* 1255 */         break;
/*      */       case 9:
/*      */       case 10:
/*      */       case 11:
/*      */       case 12:
/* 1262 */         g = ebnf(_t);
/* 1263 */         _t = this._retTree;
/* 1264 */         break;
/*      */       case 75:
/* 1268 */         g = tree(_t);
/* 1269 */         _t = this._retTree;
/* 1270 */         break;
/*      */       case 13:
/* 1274 */         AST __t72 = _t;
/* 1275 */         GrammarAST tmp53_AST_in = (GrammarAST)_t;
/* 1276 */         match(_t, 13);
/* 1277 */         _t = _t.getFirstChild();
/* 1278 */         block(_t);
/* 1279 */         _t = this._retTree;
/* 1280 */         _t = __t72;
/* 1281 */         _t = _t.getNextSibling();
/* 1282 */         break;
/*      */       case 40:
/* 1286 */         GrammarAST tmp54_AST_in = (GrammarAST)_t;
/* 1287 */         match(_t, 40);
/* 1288 */         _t = _t.getNextSibling();
/* 1289 */         g = this.factory.build_Action(tmp54_AST_in);
/* 1290 */         break;
/*      */       case 30:
/* 1294 */         GrammarAST tmp55_AST_in = (GrammarAST)_t;
/* 1295 */         match(_t, 30);
/* 1296 */         _t = _t.getNextSibling();
/* 1297 */         g = this.factory.build_Action(tmp55_AST_in);
/* 1298 */         break;
/*      */       case 69:
/* 1302 */         pred = (GrammarAST)_t;
/* 1303 */         match(_t, 69);
/* 1304 */         _t = _t.getNextSibling();
/* 1305 */         g = this.factory.build_SemanticPredicate(pred);
/* 1306 */         break;
/*      */       case 36:
/* 1310 */         spred = (GrammarAST)_t;
/* 1311 */         match(_t, 36);
/* 1312 */         _t = _t.getNextSibling();
/* 1313 */         g = this.factory.build_SemanticPredicate(spred);
/* 1314 */         break;
/*      */       case 37:
/* 1318 */         bpred = (GrammarAST)_t;
/* 1319 */         match(_t, 37);
/* 1320 */         _t = _t.getNextSibling();
/* 1321 */         g = this.factory.build_SemanticPredicate(bpred);
/* 1322 */         break;
/*      */       case 35:
/* 1326 */         gpred = (GrammarAST)_t;
/* 1327 */         match(_t, 35);
/* 1328 */         _t = _t.getNextSibling();
/* 1329 */         g = this.factory.build_SemanticPredicate(gpred);
/* 1330 */         break;
/*      */       case 16:
/* 1334 */         GrammarAST tmp56_AST_in = (GrammarAST)_t;
/* 1335 */         match(_t, 16);
/* 1336 */         _t = _t.getNextSibling();
/* 1337 */         g = this.factory.build_Epsilon();
/* 1338 */         break;
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
/* 1342 */         throw new NoViableAltException(_t);
/*      */       }
/*      */     }
/*      */     catch (RecognitionException ex)
/*      */     {
/* 1347 */       reportError(ex);
/* 1348 */       if (_t != null) _t = _t.getNextSibling();
/*      */     }
/* 1350 */     this._retTree = _t;
/* 1351 */     return g;
/*      */   }
/*      */ 
/*      */   public final void exceptionHandler(AST _t) throws RecognitionException
/*      */   {
/* 1356 */     GrammarAST exceptionHandler_AST_in = _t == ASTNULL ? null : (GrammarAST)_t;
/*      */     try
/*      */     {
/* 1359 */       AST __t56 = _t;
/* 1360 */       GrammarAST tmp57_AST_in = (GrammarAST)_t;
/* 1361 */       match(_t, 66);
/* 1362 */       _t = _t.getFirstChild();
/* 1363 */       GrammarAST tmp58_AST_in = (GrammarAST)_t;
/* 1364 */       match(_t, 60);
/* 1365 */       _t = _t.getNextSibling();
/* 1366 */       GrammarAST tmp59_AST_in = (GrammarAST)_t;
/* 1367 */       match(_t, 40);
/* 1368 */       _t = _t.getNextSibling();
/* 1369 */       _t = __t56;
/* 1370 */       _t = _t.getNextSibling();
/*      */     }
/*      */     catch (RecognitionException ex) {
/* 1373 */       reportError(ex);
/* 1374 */       if (_t != null) _t = _t.getNextSibling();
/*      */     }
/* 1376 */     this._retTree = _t;
/*      */   }
/*      */ 
/*      */   public final void finallyClause(AST _t) throws RecognitionException
/*      */   {
/* 1381 */     GrammarAST finallyClause_AST_in = _t == ASTNULL ? null : (GrammarAST)_t;
/*      */     try
/*      */     {
/* 1384 */       AST __t58 = _t;
/* 1385 */       GrammarAST tmp60_AST_in = (GrammarAST)_t;
/* 1386 */       match(_t, 67);
/* 1387 */       _t = _t.getFirstChild();
/* 1388 */       GrammarAST tmp61_AST_in = (GrammarAST)_t;
/* 1389 */       match(_t, 40);
/* 1390 */       _t = _t.getNextSibling();
/* 1391 */       _t = __t58;
/* 1392 */       _t = _t.getNextSibling();
/*      */     }
/*      */     catch (RecognitionException ex) {
/* 1395 */       reportError(ex);
/* 1396 */       if (_t != null) _t = _t.getNextSibling();
/*      */     }
/* 1398 */     this._retTree = _t;
/*      */   }
/*      */ 
/*      */   public final StateCluster atom(AST _t, String scopeName)
/*      */     throws RecognitionException
/*      */   {
/* 1404 */     StateCluster g = null;
/*      */ 
/* 1406 */     GrammarAST atom_AST_in = _t == ASTNULL ? null : (GrammarAST)_t;
/* 1407 */     GrammarAST r = null;
/* 1408 */     GrammarAST rarg = null;
/* 1409 */     GrammarAST as1 = null;
/* 1410 */     GrammarAST t = null;
/* 1411 */     GrammarAST targ = null;
/* 1412 */     GrammarAST as2 = null;
/* 1413 */     GrammarAST c = null;
/* 1414 */     GrammarAST as3 = null;
/* 1415 */     GrammarAST s = null;
/* 1416 */     GrammarAST as4 = null;
/* 1417 */     GrammarAST w = null;
/* 1418 */     GrammarAST as5 = null;
/* 1419 */     GrammarAST scope = null;
/*      */     try
/*      */     {
/* 1422 */       if (_t == null) _t = ASTNULL;
/* 1423 */       switch (_t.getType())
/*      */       {
/*      */       case 73:
/* 1426 */         AST __t87 = _t;
/* 1427 */         r = _t == ASTNULL ? null : (GrammarAST)_t;
/* 1428 */         match(_t, 73);
/* 1429 */         _t = _t.getFirstChild();
/*      */ 
/* 1431 */         if (_t == null) _t = ASTNULL;
/* 1432 */         switch (_t.getType())
/*      */         {
/*      */         case 60:
/* 1435 */           rarg = (GrammarAST)_t;
/* 1436 */           match(_t, 60);
/* 1437 */           _t = _t.getNextSibling();
/* 1438 */           break;
/*      */         case 3:
/*      */         case 59:
/*      */         case 71:
/* 1444 */           break;
/*      */         default:
/* 1448 */           throw new NoViableAltException(_t);
/*      */         }
/*      */ 
/* 1453 */         if (_t == null) _t = ASTNULL;
/* 1454 */         switch (_t.getType())
/*      */         {
/*      */         case 59:
/*      */         case 71:
/* 1458 */           as1 = _t == ASTNULL ? null : (GrammarAST)_t;
/* 1459 */           ast_suffix(_t);
/* 1460 */           _t = this._retTree;
/* 1461 */           break;
/*      */         case 3:
/* 1465 */           break;
/*      */         default:
/* 1469 */           throw new NoViableAltException(_t);
/*      */         }
/*      */ 
/* 1473 */         _t = __t87;
/* 1474 */         _t = _t.getNextSibling();
/*      */ 
/* 1476 */         NFAState start = this.grammar.getRuleStartState(scopeName, r.getText());
/* 1477 */         if (start != null) {
/* 1478 */           Rule rr = this.grammar.getRule(scopeName, r.getText());
/* 1479 */           g = this.factory.build_RuleRef(rr, start);
/* 1480 */           r.followingNFAState = g.right;
/* 1481 */           r.NFAStartState = g.left;
/* 1482 */           if (((g.left.transition(0) instanceof RuleClosureTransition)) && (this.grammar.type != 1))
/*      */           {
/* 1485 */             addFollowTransition(r.getText(), g.right);
/*      */           }
/*      */         }
/* 1488 */         break;
/*      */       case 55:
/* 1494 */         AST __t90 = _t;
/* 1495 */         t = _t == ASTNULL ? null : (GrammarAST)_t;
/* 1496 */         match(_t, 55);
/* 1497 */         _t = _t.getFirstChild();
/*      */ 
/* 1499 */         if (_t == null) _t = ASTNULL;
/* 1500 */         switch (_t.getType())
/*      */         {
/*      */         case 60:
/* 1503 */           targ = (GrammarAST)_t;
/* 1504 */           match(_t, 60);
/* 1505 */           _t = _t.getNextSibling();
/* 1506 */           break;
/*      */         case 3:
/*      */         case 59:
/*      */         case 71:
/* 1512 */           break;
/*      */         default:
/* 1516 */           throw new NoViableAltException(_t);
/*      */         }
/*      */ 
/* 1521 */         if (_t == null) _t = ASTNULL;
/* 1522 */         switch (_t.getType())
/*      */         {
/*      */         case 59:
/*      */         case 71:
/* 1526 */           as2 = _t == ASTNULL ? null : (GrammarAST)_t;
/* 1527 */           ast_suffix(_t);
/* 1528 */           _t = this._retTree;
/* 1529 */           break;
/*      */         case 3:
/* 1533 */           break;
/*      */         default:
/* 1537 */           throw new NoViableAltException(_t);
/*      */         }
/*      */ 
/* 1541 */         _t = __t90;
/* 1542 */         _t = _t.getNextSibling();
/*      */ 
/* 1544 */         if (this.grammar.type == 1) {
/* 1545 */           NFAState start = this.grammar.getRuleStartState(scopeName, t.getText());
/* 1546 */           if (start != null) {
/* 1547 */             Rule rr = this.grammar.getRule(scopeName, t.getText());
/* 1548 */             g = this.factory.build_RuleRef(rr, start);
/* 1549 */             t.NFAStartState = g.left;
/*      */           }
/*      */ 
/*      */         }
/*      */         else
/*      */         {
/* 1555 */           g = this.factory.build_Atom(t);
/* 1556 */           t.followingNFAState = g.right;
/*      */         }
/*      */ 
/* 1559 */         break;
/*      */       case 51:
/* 1563 */         AST __t93 = _t;
/* 1564 */         c = _t == ASTNULL ? null : (GrammarAST)_t;
/* 1565 */         match(_t, 51);
/* 1566 */         _t = _t.getFirstChild();
/*      */ 
/* 1568 */         if (_t == null) _t = ASTNULL;
/* 1569 */         switch (_t.getType())
/*      */         {
/*      */         case 59:
/*      */         case 71:
/* 1573 */           as3 = _t == ASTNULL ? null : (GrammarAST)_t;
/* 1574 */           ast_suffix(_t);
/* 1575 */           _t = this._retTree;
/* 1576 */           break;
/*      */         case 3:
/* 1580 */           break;
/*      */         default:
/* 1584 */           throw new NoViableAltException(_t);
/*      */         }
/*      */ 
/* 1588 */         _t = __t93;
/* 1589 */         _t = _t.getNextSibling();
/*      */ 
/* 1591 */         if (this.grammar.type == 1) {
/* 1592 */           g = this.factory.build_CharLiteralAtom(c);
/*      */         }
/*      */         else {
/* 1595 */           g = this.factory.build_Atom(c);
/* 1596 */           c.followingNFAState = g.right;
/*      */         }
/*      */ 
/* 1599 */         break;
/*      */       case 50:
/* 1603 */         AST __t95 = _t;
/* 1604 */         s = _t == ASTNULL ? null : (GrammarAST)_t;
/* 1605 */         match(_t, 50);
/* 1606 */         _t = _t.getFirstChild();
/*      */ 
/* 1608 */         if (_t == null) _t = ASTNULL;
/* 1609 */         switch (_t.getType())
/*      */         {
/*      */         case 59:
/*      */         case 71:
/* 1613 */           as4 = _t == ASTNULL ? null : (GrammarAST)_t;
/* 1614 */           ast_suffix(_t);
/* 1615 */           _t = this._retTree;
/* 1616 */           break;
/*      */         case 3:
/* 1620 */           break;
/*      */         default:
/* 1624 */           throw new NoViableAltException(_t);
/*      */         }
/*      */ 
/* 1628 */         _t = __t95;
/* 1629 */         _t = _t.getNextSibling();
/*      */ 
/* 1631 */         if (this.grammar.type == 1) {
/* 1632 */           g = this.factory.build_StringLiteralAtom(s);
/*      */         }
/*      */         else {
/* 1635 */           g = this.factory.build_Atom(s);
/* 1636 */           s.followingNFAState = g.right;
/*      */         }
/*      */ 
/* 1639 */         break;
/*      */       case 72:
/* 1643 */         AST __t97 = _t;
/* 1644 */         w = _t == ASTNULL ? null : (GrammarAST)_t;
/* 1645 */         match(_t, 72);
/* 1646 */         _t = _t.getFirstChild();
/*      */ 
/* 1648 */         if (_t == null) _t = ASTNULL;
/* 1649 */         switch (_t.getType())
/*      */         {
/*      */         case 59:
/*      */         case 71:
/* 1653 */           as5 = _t == ASTNULL ? null : (GrammarAST)_t;
/* 1654 */           ast_suffix(_t);
/* 1655 */           _t = this._retTree;
/* 1656 */           break;
/*      */         case 3:
/* 1660 */           break;
/*      */         default:
/* 1664 */           throw new NoViableAltException(_t);
/*      */         }
/*      */ 
/* 1668 */         _t = __t97;
/* 1669 */         _t = _t.getNextSibling();
/*      */ 
/* 1671 */         if (this.nfa.grammar.type == 3) {
/* 1672 */           g = this.factory.build_WildcardTree(w);
/*      */         }
/*      */         else {
/* 1675 */           g = this.factory.build_Wildcard(w);
/*      */         }
/*      */ 
/* 1678 */         break;
/*      */       case 39:
/* 1682 */         AST __t99 = _t;
/* 1683 */         GrammarAST tmp62_AST_in = (GrammarAST)_t;
/* 1684 */         match(_t, 39);
/* 1685 */         _t = _t.getFirstChild();
/* 1686 */         scope = (GrammarAST)_t;
/* 1687 */         match(_t, 21);
/* 1688 */         _t = _t.getNextSibling();
/* 1689 */         g = atom(_t, scope.getText());
/* 1690 */         _t = this._retTree;
/* 1691 */         _t = __t99;
/* 1692 */         _t = _t.getNextSibling();
/* 1693 */         break;
/*      */       default:
/* 1697 */         throw new NoViableAltException(_t);
/*      */       }
/*      */     }
/*      */     catch (RecognitionException ex)
/*      */     {
/* 1702 */       reportError(ex);
/* 1703 */       if (_t != null) _t = _t.getNextSibling();
/*      */     }
/* 1705 */     this._retTree = _t;
/* 1706 */     return g;
/*      */   }
/*      */ 
/*      */   public final StateCluster atom_or_notatom(AST _t) throws RecognitionException {
/* 1710 */     StateCluster g = null;
/*      */ 
/* 1712 */     GrammarAST atom_or_notatom_AST_in = _t == ASTNULL ? null : (GrammarAST)_t;
/* 1713 */     GrammarAST n = null;
/* 1714 */     GrammarAST c = null;
/* 1715 */     GrammarAST ast1 = null;
/* 1716 */     GrammarAST t = null;
/* 1717 */     GrammarAST ast3 = null;
/*      */     try
/*      */     {
/* 1720 */       if (_t == null) _t = ASTNULL;
/* 1721 */       switch (_t.getType())
/*      */       {
/*      */       case 39:
/*      */       case 50:
/*      */       case 51:
/*      */       case 55:
/*      */       case 72:
/*      */       case 73:
/* 1729 */         g = atom(_t, null);
/* 1730 */         _t = this._retTree;
/* 1731 */         break;
/*      */       case 74:
/* 1735 */         AST __t82 = _t;
/* 1736 */         n = _t == ASTNULL ? null : (GrammarAST)_t;
/* 1737 */         match(_t, 74);
/* 1738 */         _t = _t.getFirstChild();
/*      */ 
/* 1740 */         if (_t == null) _t = ASTNULL;
/* 1741 */         switch (_t.getType())
/*      */         {
/*      */         case 51:
/* 1744 */           c = (GrammarAST)_t;
/* 1745 */           match(_t, 51);
/* 1746 */           _t = _t.getNextSibling();
/*      */ 
/* 1748 */           if (_t == null) _t = ASTNULL;
/* 1749 */           switch (_t.getType())
/*      */           {
/*      */           case 59:
/*      */           case 71:
/* 1753 */             ast1 = _t == ASTNULL ? null : (GrammarAST)_t;
/* 1754 */             ast_suffix(_t);
/* 1755 */             _t = this._retTree;
/* 1756 */             break;
/*      */           case 3:
/* 1760 */             break;
/*      */           default:
/* 1764 */             throw new NoViableAltException(_t);
/*      */           }
/*      */ 
/* 1769 */           int ttype = 0;
/* 1770 */           if (this.grammar.type == 1) {
/* 1771 */             ttype = Grammar.getCharValueFromGrammarCharLiteral(c.getText());
/*      */           }
/*      */           else {
/* 1774 */             ttype = this.grammar.getTokenType(c.getText());
/*      */           }
/* 1776 */           IntSet notAtom = this.grammar.complement(ttype);
/* 1777 */           if (notAtom.isNil()) {
/* 1778 */             ErrorManager.grammarError(139, this.grammar, c.token, c.getText());
/*      */           }
/*      */ 
/* 1783 */           g = this.factory.build_Set(notAtom, n);
/*      */ 
/* 1785 */           break;
/*      */         case 55:
/* 1789 */           t = (GrammarAST)_t;
/* 1790 */           match(_t, 55);
/* 1791 */           _t = _t.getNextSibling();
/*      */ 
/* 1793 */           if (_t == null) _t = ASTNULL;
/* 1794 */           switch (_t.getType())
/*      */           {
/*      */           case 59:
/*      */           case 71:
/* 1798 */             ast3 = _t == ASTNULL ? null : (GrammarAST)_t;
/* 1799 */             ast_suffix(_t);
/* 1800 */             _t = this._retTree;
/* 1801 */             break;
/*      */           case 3:
/* 1805 */             break;
/*      */           default:
/* 1809 */             throw new NoViableAltException(_t);
/*      */           }
/*      */ 
/* 1814 */           int ttype = 0;
/* 1815 */           IntSet notAtom = null;
/* 1816 */           if (this.grammar.type == 1) {
/* 1817 */             notAtom = this.grammar.getSetFromRule(this, t.getText());
/* 1818 */             if (notAtom == null) {
/* 1819 */               ErrorManager.grammarError(154, this.grammar, t.token, t.getText());
/*      */             }
/*      */             else
/*      */             {
/* 1825 */               notAtom = this.grammar.complement(notAtom);
/*      */             }
/*      */           }
/*      */           else {
/* 1829 */             ttype = this.grammar.getTokenType(t.getText());
/* 1830 */             notAtom = this.grammar.complement(ttype);
/*      */           }
/* 1832 */           if ((notAtom == null) || (notAtom.isNil())) {
/* 1833 */             ErrorManager.grammarError(139, this.grammar, t.token, t.getText());
/*      */           }
/*      */ 
/* 1838 */           g = this.factory.build_Set(notAtom, n);
/*      */ 
/* 1840 */           break;
/*      */         case 9:
/* 1844 */           g = set(_t);
/* 1845 */           _t = this._retTree;
/*      */ 
/* 1847 */           GrammarAST stNode = (GrammarAST)n.getFirstChild();
/*      */ 
/* 1850 */           IntSet s = stNode.getSetValue();
/* 1851 */           stNode.setSetValue(s);
/*      */ 
/* 1854 */           s = this.grammar.complement(s);
/* 1855 */           if (s.isNil()) {
/* 1856 */             ErrorManager.grammarError(139, this.grammar, n.token);
/*      */           }
/*      */ 
/* 1860 */           g = this.factory.build_Set(s, n);
/*      */ 
/* 1862 */           break;
/*      */         default:
/* 1866 */           throw new NoViableAltException(_t);
/*      */         }
/*      */ 
/* 1870 */         n.followingNFAState = g.right;
/* 1871 */         _t = __t82;
/* 1872 */         _t = _t.getNextSibling();
/* 1873 */         break;
/*      */       default:
/* 1877 */         throw new NoViableAltException(_t);
/*      */       }
/*      */     }
/*      */     catch (RecognitionException ex)
/*      */     {
/* 1882 */       reportError(ex);
/* 1883 */       if (_t != null) _t = _t.getNextSibling();
/*      */     }
/* 1885 */     this._retTree = _t;
/* 1886 */     return g;
/*      */   }
/*      */ 
/*      */   public final StateCluster ebnf(AST _t) throws RecognitionException {
/* 1890 */     StateCluster g = null;
/*      */ 
/* 1892 */     GrammarAST ebnf_AST_in = _t == ASTNULL ? null : (GrammarAST)_t;
/*      */ 
/* 1894 */     StateCluster b = null;
/* 1895 */     GrammarAST blk = ebnf_AST_in;
/* 1896 */     if (blk.getType() != 9) {
/* 1897 */       blk = (GrammarAST)blk.getFirstChild();
/*      */     }
/* 1899 */     GrammarAST eob = blk.getLastChild();
/*      */     try
/*      */     {
/* 1903 */       if (_t == null) _t = ASTNULL;
/* 1904 */       switch (_t.getType())
/*      */       {
/*      */       case 10:
/* 1907 */         AST __t74 = _t;
/* 1908 */         GrammarAST tmp63_AST_in = (GrammarAST)_t;
/* 1909 */         match(_t, 10);
/* 1910 */         _t = _t.getFirstChild();
/* 1911 */         b = block(_t);
/* 1912 */         _t = this._retTree;
/* 1913 */         _t = __t74;
/* 1914 */         _t = _t.getNextSibling();
/*      */ 
/* 1916 */         if (blk.getSetValue() != null)
/*      */         {
/* 1919 */           b = this.factory.build_AlternativeBlockFromSet(b);
/*      */         }
/* 1921 */         g = this.factory.build_Aoptional(b);
/* 1922 */         g.left.setDescription(this.grammar.grammarTreeToString(ebnf_AST_in, false));
/*      */ 
/* 1924 */         int d = this.grammar.assignDecisionNumber(g.left);
/* 1925 */         this.grammar.setDecisionNFA(d, g.left);
/* 1926 */         this.grammar.setDecisionBlockAST(d, blk);
/* 1927 */         g.left.setDecisionASTNode(ebnf_AST_in);
/*      */ 
/* 1929 */         break;
/*      */       case 11:
/* 1933 */         AST __t75 = _t;
/* 1934 */         GrammarAST tmp64_AST_in = (GrammarAST)_t;
/* 1935 */         match(_t, 11);
/* 1936 */         _t = _t.getFirstChild();
/* 1937 */         b = block(_t);
/* 1938 */         _t = this._retTree;
/* 1939 */         _t = __t75;
/* 1940 */         _t = _t.getNextSibling();
/*      */ 
/* 1942 */         if (blk.getSetValue() != null) {
/* 1943 */           b = this.factory.build_AlternativeBlockFromSet(b);
/*      */         }
/* 1945 */         g = this.factory.build_Astar(b);
/*      */ 
/* 1947 */         b.right.setDescription("()* loopback of " + this.grammar.grammarTreeToString(ebnf_AST_in, false));
/* 1948 */         int d = this.grammar.assignDecisionNumber(b.right);
/* 1949 */         this.grammar.setDecisionNFA(d, b.right);
/* 1950 */         this.grammar.setDecisionBlockAST(d, blk);
/* 1951 */         b.right.setDecisionASTNode(eob);
/*      */ 
/* 1953 */         NFAState altBlockState = (NFAState)g.left.transition(0).target;
/* 1954 */         altBlockState.setDecisionASTNode(ebnf_AST_in);
/* 1955 */         altBlockState.setDecisionNumber(d);
/* 1956 */         g.left.setDecisionNumber(d);
/* 1957 */         g.left.setDecisionASTNode(ebnf_AST_in);
/*      */ 
/* 1959 */         break;
/*      */       case 12:
/* 1963 */         AST __t76 = _t;
/* 1964 */         GrammarAST tmp65_AST_in = (GrammarAST)_t;
/* 1965 */         match(_t, 12);
/* 1966 */         _t = _t.getFirstChild();
/* 1967 */         b = block(_t);
/* 1968 */         _t = this._retTree;
/* 1969 */         _t = __t76;
/* 1970 */         _t = _t.getNextSibling();
/*      */ 
/* 1972 */         if (blk.getSetValue() != null) {
/* 1973 */           b = this.factory.build_AlternativeBlockFromSet(b);
/*      */         }
/* 1975 */         g = this.factory.build_Aplus(b);
/*      */ 
/* 1978 */         b.right.setDescription("()+ loopback of " + this.grammar.grammarTreeToString(ebnf_AST_in, false));
/* 1979 */         int d = this.grammar.assignDecisionNumber(b.right);
/* 1980 */         this.grammar.setDecisionNFA(d, b.right);
/* 1981 */         this.grammar.setDecisionBlockAST(d, blk);
/* 1982 */         b.right.setDecisionASTNode(eob);
/*      */ 
/* 1984 */         NFAState altBlockState = (NFAState)g.left.transition(0).target;
/* 1985 */         altBlockState.setDecisionASTNode(ebnf_AST_in);
/* 1986 */         altBlockState.setDecisionNumber(d);
/*      */ 
/* 1988 */         break;
/*      */       default:
/* 1991 */         if (_t == null) _t = ASTNULL;
/* 1992 */         if ((_t.getType() == 9) && (this.grammar.isValidSet(this, ebnf_AST_in))) {
/* 1993 */           g = set(_t);
/* 1994 */           _t = this._retTree;
/*      */         }
/* 1996 */         else if (_t.getType() == 9) {
/* 1997 */           b = block(_t);
/* 1998 */           _t = this._retTree;
/*      */ 
/* 2001 */           if (this.grammar.getNumberOfAltsForDecisionNFA(b.left) > 1) {
/* 2002 */             b.left.setDescription(this.grammar.grammarTreeToString(blk, false));
/* 2003 */             b.left.setDecisionASTNode(blk);
/* 2004 */             int d = this.grammar.assignDecisionNumber(b.left);
/* 2005 */             this.grammar.setDecisionNFA(d, b.left);
/* 2006 */             this.grammar.setDecisionBlockAST(d, blk);
/*      */           }
/* 2008 */           g = b;
/*      */         }
/*      */         else
/*      */         {
/* 2012 */           throw new NoViableAltException(_t);
/*      */         }
/*      */         break;
/*      */       }
/*      */     } catch (RecognitionException ex) {
/* 2017 */       reportError(ex);
/* 2018 */       if (_t != null) _t = _t.getNextSibling();
/*      */     }
/* 2020 */     this._retTree = _t;
/* 2021 */     return g;
/*      */   }
/*      */ 
/*      */   public final StateCluster tree(AST _t) throws RecognitionException {
/* 2025 */     StateCluster g = null;
/*      */ 
/* 2027 */     GrammarAST tree_AST_in = _t == ASTNULL ? null : (GrammarAST)_t;
/*      */ 
/* 2029 */     StateCluster e = null;
/* 2030 */     GrammarAST el = null;
/* 2031 */     StateCluster down = null; StateCluster up = null;
/*      */     try
/*      */     {
/* 2035 */       AST __t78 = _t;
/* 2036 */       GrammarAST tmp66_AST_in = (GrammarAST)_t;
/* 2037 */       match(_t, 75);
/* 2038 */       _t = _t.getFirstChild();
/* 2039 */       el = (GrammarAST)_t;
/* 2040 */       g = element(_t);
/* 2041 */       _t = this._retTree;
/*      */ 
/* 2043 */       down = this.factory.build_Atom(2, el);
/*      */ 
/* 2046 */       g = this.factory.build_AB(g, down);
/*      */       while (true)
/*      */       {
/* 2051 */         if (_t == null) _t = ASTNULL;
/* 2052 */         if (!_tokenSet_0.member(_t.getType())) break;
/* 2053 */         el = (GrammarAST)_t;
/* 2054 */         e = element(_t);
/* 2055 */         _t = this._retTree;
/* 2056 */         g = this.factory.build_AB(g, e);
/*      */       }
/*      */ 
/* 2065 */       up = this.factory.build_Atom(3, el);
/*      */ 
/* 2067 */       g = this.factory.build_AB(g, up);
/*      */ 
/* 2069 */       tree_AST_in.NFATreeDownState = down.left;
/*      */ 
/* 2071 */       _t = __t78;
/* 2072 */       _t = _t.getNextSibling();
/*      */     }
/*      */     catch (RecognitionException ex) {
/* 2075 */       reportError(ex);
/* 2076 */       if (_t != null) _t = _t.getNextSibling();
/*      */     }
/* 2078 */     this._retTree = _t;
/* 2079 */     return g;
/*      */   }
/*      */ 
/*      */   public final void ast_suffix(AST _t) throws RecognitionException
/*      */   {
/* 2084 */     GrammarAST ast_suffix_AST_in = _t == ASTNULL ? null : (GrammarAST)_t;
/*      */     try
/*      */     {
/* 2087 */       if (_t == null) _t = ASTNULL;
/* 2088 */       switch (_t.getType())
/*      */       {
/*      */       case 71:
/* 2091 */         GrammarAST tmp67_AST_in = (GrammarAST)_t;
/* 2092 */         match(_t, 71);
/* 2093 */         _t = _t.getNextSibling();
/* 2094 */         break;
/*      */       case 59:
/* 2098 */         GrammarAST tmp68_AST_in = (GrammarAST)_t;
/* 2099 */         match(_t, 59);
/* 2100 */         _t = _t.getNextSibling();
/* 2101 */         break;
/*      */       default:
/* 2105 */         throw new NoViableAltException(_t);
/*      */       }
/*      */     }
/*      */     catch (RecognitionException ex)
/*      */     {
/* 2110 */       reportError(ex);
/* 2111 */       if (_t != null) _t = _t.getNextSibling();
/*      */     }
/* 2113 */     this._retTree = _t;
/*      */   }
/*      */ 
/*      */   public final void setElement(AST _t, IntSet elements)
/*      */     throws RecognitionException
/*      */   {
/* 2120 */     GrammarAST setElement_AST_in = _t == ASTNULL ? null : (GrammarAST)_t;
/* 2121 */     GrammarAST c = null;
/* 2122 */     GrammarAST t = null;
/* 2123 */     GrammarAST s = null;
/* 2124 */     GrammarAST c1 = null;
/* 2125 */     GrammarAST c2 = null;
/*      */ 
/* 2128 */     IntSet ns = null;
/*      */     try
/*      */     {
/* 2133 */       if (_t == null) _t = ASTNULL;
/*      */       int ttype;
/* 2134 */       switch (_t.getType())
/*      */       {
/*      */       case 51:
/* 2137 */         c = (GrammarAST)_t;
/* 2138 */         match(_t, 51);
/* 2139 */         _t = _t.getNextSibling();
/*      */         int ttype;
/* 2141 */         if (this.grammar.type == 1) {
/* 2142 */           ttype = Grammar.getCharValueFromGrammarCharLiteral(c.getText());
/*      */         }
/*      */         else {
/* 2145 */           ttype = this.grammar.getTokenType(c.getText());
/*      */         }
/* 2147 */         if (elements.member(ttype)) {
/* 2148 */           ErrorManager.grammarError(204, this.grammar, c.token, c.getText());
/*      */         }
/*      */ 
/* 2153 */         elements.add(ttype);
/*      */ 
/* 2155 */         break;
/*      */       case 55:
/* 2159 */         t = (GrammarAST)_t;
/* 2160 */         match(_t, 55);
/* 2161 */         _t = _t.getNextSibling();
/*      */ 
/* 2163 */         if (this.grammar.type == 1)
/*      */         {
/* 2165 */           IntSet ruleSet = this.grammar.getSetFromRule(this, t.getText());
/* 2166 */           if (ruleSet == null) {
/* 2167 */             ErrorManager.grammarError(154, this.grammar, t.token, t.getText());
/*      */           }
/*      */           else
/*      */           {
/* 2173 */             elements.addAll(ruleSet);
/*      */           }
/*      */         }
/*      */         else {
/* 2177 */           ttype = this.grammar.getTokenType(t.getText());
/* 2178 */           if (elements.member(ttype)) {
/* 2179 */             ErrorManager.grammarError(204, this.grammar, t.token, t.getText());
/*      */           }
/*      */ 
/* 2184 */           elements.add(ttype);
/*      */         }
/*      */ 
/* 2187 */         break;
/*      */       case 50:
/* 2191 */         s = (GrammarAST)_t;
/* 2192 */         match(_t, 50);
/* 2193 */         _t = _t.getNextSibling();
/*      */ 
/* 2195 */         ttype = this.grammar.getTokenType(s.getText());
/* 2196 */         if (elements.member(ttype)) {
/* 2197 */           ErrorManager.grammarError(204, this.grammar, s.token, s.getText());
/*      */         }
/*      */ 
/* 2202 */         elements.add(ttype);
/*      */ 
/* 2204 */         break;
/*      */       case 15:
/* 2208 */         AST __t122 = _t;
/* 2209 */         GrammarAST tmp69_AST_in = (GrammarAST)_t;
/* 2210 */         match(_t, 15);
/* 2211 */         _t = _t.getFirstChild();
/* 2212 */         c1 = (GrammarAST)_t;
/* 2213 */         match(_t, 51);
/* 2214 */         _t = _t.getNextSibling();
/* 2215 */         c2 = (GrammarAST)_t;
/* 2216 */         match(_t, 51);
/* 2217 */         _t = _t.getNextSibling();
/* 2218 */         _t = __t122;
/* 2219 */         _t = _t.getNextSibling();
/*      */ 
/* 2221 */         if (this.grammar.type == 1) {
/* 2222 */           int a = Grammar.getCharValueFromGrammarCharLiteral(c1.getText());
/* 2223 */           int b = Grammar.getCharValueFromGrammarCharLiteral(c2.getText());
/* 2224 */           elements.addAll(IntervalSet.of(a, b));
/* 2225 */         }break;
/*      */       case 9:
/* 2231 */         StateCluster gset = set(_t);
/* 2232 */         _t = this._retTree;
/*      */ 
/* 2234 */         Transition setTrans = gset.left.transition(0);
/* 2235 */         elements.addAll(setTrans.label.getSet());
/*      */ 
/* 2237 */         break;
/*      */       case 74:
/* 2241 */         AST __t123 = _t;
/* 2242 */         GrammarAST tmp70_AST_in = (GrammarAST)_t;
/* 2243 */         match(_t, 74);
/* 2244 */         _t = _t.getFirstChild();
/* 2245 */         ns = new IntervalSet();
/* 2246 */         setElement(_t, ns);
/* 2247 */         _t = this._retTree;
/*      */ 
/* 2249 */         IntSet not = this.grammar.complement(ns);
/* 2250 */         elements.addAll(not);
/*      */ 
/* 2252 */         _t = __t123;
/* 2253 */         _t = _t.getNextSibling();
/* 2254 */         break;
/*      */       default:
/* 2258 */         throw new NoViableAltException(_t);
/*      */       }
/*      */     }
/*      */     catch (RecognitionException ex)
/*      */     {
/* 2263 */       reportError(ex);
/* 2264 */       if (_t != null) _t = _t.getNextSibling();
/*      */     }
/* 2266 */     this._retTree = _t;
/*      */   }
/*      */ 
/*      */   public final IntSet setRule(AST _t) throws RecognitionException {
/* 2270 */     IntSet elements = new IntervalSet();
/*      */ 
/* 2272 */     GrammarAST setRule_AST_in = _t == ASTNULL ? null : (GrammarAST)_t;
/* 2273 */     GrammarAST id = null;
/* 2274 */     IntSet s = null;
/*      */     try
/*      */     {
/* 2277 */       AST __t108 = _t;
/* 2278 */       GrammarAST tmp71_AST_in = (GrammarAST)_t;
/* 2279 */       match(_t, 8);
/* 2280 */       _t = _t.getFirstChild();
/* 2281 */       id = (GrammarAST)_t;
/* 2282 */       match(_t, 21);
/* 2283 */       _t = _t.getNextSibling();
/*      */ 
/* 2285 */       if (_t == null) _t = ASTNULL;
/* 2286 */       switch (_t.getType())
/*      */       {
/*      */       case 38:
/*      */       case 56:
/*      */       case 57:
/*      */       case 58:
/* 2292 */         modifier(_t);
/* 2293 */         _t = this._retTree;
/* 2294 */         break;
/*      */       case 22:
/* 2298 */         break;
/*      */       default:
/* 2302 */         throw new NoViableAltException(_t);
/*      */       }
/*      */ 
/* 2306 */       GrammarAST tmp72_AST_in = (GrammarAST)_t;
/* 2307 */       match(_t, 22);
/* 2308 */       _t = _t.getNextSibling();
/* 2309 */       GrammarAST tmp73_AST_in = (GrammarAST)_t;
/* 2310 */       match(_t, 24);
/* 2311 */       _t = _t.getNextSibling();
/*      */ 
/* 2313 */       if (_t == null) _t = ASTNULL;
/* 2314 */       switch (_t.getType())
/*      */       {
/*      */       case 4:
/* 2317 */         GrammarAST tmp74_AST_in = (GrammarAST)_t;
/* 2318 */         match(_t, 4);
/* 2319 */         _t = _t.getNextSibling();
/* 2320 */         break;
/*      */       case 9:
/*      */       case 33:
/*      */       case 46:
/* 2326 */         break;
/*      */       default:
/* 2330 */         throw new NoViableAltException(_t);
/*      */       }
/*      */ 
/* 2335 */       if (_t == null) _t = ASTNULL;
/* 2336 */       switch (_t.getType())
/*      */       {
/*      */       case 33:
/* 2339 */         ruleScopeSpec(_t);
/* 2340 */         _t = this._retTree;
/* 2341 */         break;
/*      */       case 9:
/*      */       case 46:
/* 2346 */         break;
/*      */       default:
/* 2350 */         throw new NoViableAltException(_t);
/*      */       }
/*      */ 
/*      */       while (true)
/*      */       {
/* 2357 */         if (_t == null) _t = ASTNULL;
/* 2358 */         if (_t.getType() != 46) break;
/* 2359 */         GrammarAST tmp75_AST_in = (GrammarAST)_t;
/* 2360 */         match(_t, 46);
/* 2361 */         _t = _t.getNextSibling();
/*      */       }
/*      */ 
/* 2369 */       AST __t114 = _t;
/* 2370 */       GrammarAST tmp76_AST_in = (GrammarAST)_t;
/* 2371 */       match(_t, 9);
/* 2372 */       _t = _t.getFirstChild();
/*      */ 
/* 2374 */       if (_t == null) _t = ASTNULL;
/* 2375 */       switch (_t.getType())
/*      */       {
/*      */       case 4:
/* 2378 */         GrammarAST tmp77_AST_in = (GrammarAST)_t;
/* 2379 */         match(_t, 4);
/* 2380 */         _t = _t.getNextSibling();
/* 2381 */         break;
/*      */       case 17:
/* 2385 */         break;
/*      */       default:
/* 2389 */         throw new NoViableAltException(_t);
/*      */       }
/*      */ 
/* 2394 */       int _cnt119 = 0;
/*      */       while (true)
/*      */       {
/* 2397 */         if (_t == null) _t = ASTNULL;
/* 2398 */         if (_t.getType() == 17) {
/* 2399 */           AST __t117 = _t;
/* 2400 */           GrammarAST tmp78_AST_in = (GrammarAST)_t;
/* 2401 */           match(_t, 17);
/* 2402 */           _t = _t.getFirstChild();
/*      */ 
/* 2404 */           if (_t == null) _t = ASTNULL;
/* 2405 */           switch (_t.getType())
/*      */           {
/*      */           case 37:
/* 2408 */             GrammarAST tmp79_AST_in = (GrammarAST)_t;
/* 2409 */             match(_t, 37);
/* 2410 */             _t = _t.getNextSibling();
/* 2411 */             break;
/*      */           case 9:
/*      */           case 15:
/*      */           case 50:
/*      */           case 51:
/*      */           case 55:
/*      */           case 74:
/* 2420 */             break;
/*      */           default:
/* 2424 */             throw new NoViableAltException(_t);
/*      */           }
/*      */ 
/* 2428 */           setElement(_t, elements);
/* 2429 */           _t = this._retTree;
/* 2430 */           GrammarAST tmp80_AST_in = (GrammarAST)_t;
/* 2431 */           match(_t, 20);
/* 2432 */           _t = _t.getNextSibling();
/* 2433 */           _t = __t117;
/* 2434 */           _t = _t.getNextSibling();
/*      */         }
/*      */         else {
/* 2437 */           if (_cnt119 >= 1) break; throw new NoViableAltException(_t);
/*      */         }
/*      */ 
/* 2440 */         _cnt119++;
/*      */       }
/*      */ 
/* 2443 */       GrammarAST tmp81_AST_in = (GrammarAST)_t;
/* 2444 */       match(_t, 19);
/* 2445 */       _t = _t.getNextSibling();
/* 2446 */       _t = __t114;
/* 2447 */       _t = _t.getNextSibling();
/*      */ 
/* 2449 */       if (_t == null) _t = ASTNULL;
/* 2450 */       switch (_t.getType())
/*      */       {
/*      */       case 66:
/*      */       case 67:
/* 2454 */         exceptionGroup(_t);
/* 2455 */         _t = this._retTree;
/* 2456 */         break;
/*      */       case 18:
/* 2460 */         break;
/*      */       default:
/* 2464 */         throw new NoViableAltException(_t);
/*      */       }
/*      */ 
/* 2468 */       GrammarAST tmp82_AST_in = (GrammarAST)_t;
/* 2469 */       match(_t, 18);
/* 2470 */       _t = _t.getNextSibling();
/* 2471 */       _t = __t108;
/* 2472 */       _t = _t.getNextSibling();
/*      */     }
/*      */     catch (RecognitionException re) {
/* 2475 */       throw re;
/*      */     }
/* 2477 */     this._retTree = _t;
/* 2478 */     return elements;
/*      */   }
/*      */ 
/*      */   public final void testBlockAsSet(AST _t)
/*      */     throws RecognitionException
/*      */   {
/* 2487 */     GrammarAST testBlockAsSet_AST_in = _t == ASTNULL ? null : (GrammarAST)_t;
/*      */ 
/* 2489 */     int nAlts = 0;
/* 2490 */     Rule r = this.grammar.getLocallyDefinedRule(this.currentRuleName);
/*      */     try
/*      */     {
/* 2494 */       AST __t125 = _t;
/* 2495 */       GrammarAST tmp83_AST_in = (GrammarAST)_t;
/* 2496 */       match(_t, 9);
/* 2497 */       _t = _t.getFirstChild();
/*      */ 
/* 2499 */       int _cnt129 = 0;
/*      */       while (true)
/*      */       {
/* 2502 */         if (_t == null) _t = ASTNULL;
/* 2503 */         if (_t.getType() == 17) {
/* 2504 */           AST __t127 = _t;
/* 2505 */           GrammarAST tmp84_AST_in = (GrammarAST)_t;
/* 2506 */           match(_t, 17);
/* 2507 */           _t = _t.getFirstChild();
/*      */ 
/* 2509 */           if (_t == null) _t = ASTNULL;
/* 2510 */           switch (_t.getType())
/*      */           {
/*      */           case 37:
/* 2513 */             GrammarAST tmp85_AST_in = (GrammarAST)_t;
/* 2514 */             match(_t, 37);
/* 2515 */             _t = _t.getNextSibling();
/* 2516 */             break;
/*      */           case 9:
/*      */           case 15:
/*      */           case 50:
/*      */           case 51:
/*      */           case 55:
/*      */           case 74:
/* 2525 */             break;
/*      */           default:
/* 2529 */             throw new NoViableAltException(_t);
/*      */           }
/*      */ 
/* 2533 */           testSetElement(_t);
/* 2534 */           _t = this._retTree;
/* 2535 */           nAlts++;
/* 2536 */           GrammarAST tmp86_AST_in = (GrammarAST)_t;
/* 2537 */           match(_t, 20);
/* 2538 */           _t = _t.getNextSibling();
/* 2539 */           _t = __t127;
/* 2540 */           _t = _t.getNextSibling();
/* 2541 */           if (r.hasRewrite(this.outerAltNum))
/* 2542 */             throw new SemanticException("!r.hasRewrite(outerAltNum)");
/*      */         }
/*      */         else {
/* 2545 */           if (_cnt129 >= 1) break; throw new NoViableAltException(_t);
/*      */         }
/*      */ 
/* 2548 */         _cnt129++;
/*      */       }
/*      */ 
/* 2551 */       GrammarAST tmp87_AST_in = (GrammarAST)_t;
/* 2552 */       match(_t, 19);
/* 2553 */       _t = _t.getNextSibling();
/* 2554 */       _t = __t125;
/* 2555 */       _t = _t.getNextSibling();
/* 2556 */       if (nAlts <= 1)
/* 2557 */         throw new SemanticException("nAlts>1");
/*      */     }
/*      */     catch (RecognitionException re) {
/* 2560 */       throw re;
/*      */     }
/* 2562 */     this._retTree = _t;
/*      */   }
/*      */ 
/*      */   public final void testSetElement(AST _t)
/*      */     throws RecognitionException
/*      */   {
/* 2568 */     GrammarAST testSetElement_AST_in = _t == ASTNULL ? null : (GrammarAST)_t;
/* 2569 */     GrammarAST c = null;
/* 2570 */     GrammarAST t = null;
/* 2571 */     GrammarAST s = null;
/* 2572 */     GrammarAST c1 = null;
/* 2573 */     GrammarAST c2 = null;
/*      */ 
/* 2575 */     AST r = _t;
/*      */     try
/*      */     {
/* 2579 */       if (_t == null) _t = ASTNULL;
/* 2580 */       switch (_t.getType())
/*      */       {
/*      */       case 51:
/* 2583 */         c = (GrammarAST)_t;
/* 2584 */         match(_t, 51);
/* 2585 */         _t = _t.getNextSibling();
/* 2586 */         break;
/*      */       case 55:
/* 2590 */         t = (GrammarAST)_t;
/* 2591 */         match(_t, 55);
/* 2592 */         _t = _t.getNextSibling();
/*      */ 
/* 2594 */         if (this.grammar.type == 1) {
/* 2595 */           Rule rule = this.grammar.getRule(t.getText());
/* 2596 */           if (rule == null) {
/* 2597 */             throw new RecognitionException("invalid rule");
/*      */           }
/*      */ 
/* 2600 */           testSetRule(rule.tree);
/* 2601 */         }break;
/*      */       case 15:
/* 2607 */         AST __t144 = _t;
/* 2608 */         GrammarAST tmp88_AST_in = (GrammarAST)_t;
/* 2609 */         match(_t, 15);
/* 2610 */         _t = _t.getFirstChild();
/* 2611 */         c1 = (GrammarAST)_t;
/* 2612 */         match(_t, 51);
/* 2613 */         _t = _t.getNextSibling();
/* 2614 */         c2 = (GrammarAST)_t;
/* 2615 */         match(_t, 51);
/* 2616 */         _t = _t.getNextSibling();
/* 2617 */         _t = __t144;
/* 2618 */         _t = _t.getNextSibling();
/* 2619 */         break;
/*      */       case 9:
/* 2623 */         testBlockAsSet(_t);
/* 2624 */         _t = this._retTree;
/* 2625 */         break;
/*      */       case 74:
/* 2629 */         AST __t145 = _t;
/* 2630 */         GrammarAST tmp89_AST_in = (GrammarAST)_t;
/* 2631 */         match(_t, 74);
/* 2632 */         _t = _t.getFirstChild();
/* 2633 */         testSetElement(_t);
/* 2634 */         _t = this._retTree;
/* 2635 */         _t = __t145;
/* 2636 */         _t = _t.getNextSibling();
/* 2637 */         break;
/*      */       default:
/* 2640 */         if (_t == null) _t = ASTNULL;
/* 2641 */         if ((_t.getType() == 50) && (this.grammar.type != 1)) {
/* 2642 */           s = (GrammarAST)_t;
/* 2643 */           match(_t, 50);
/* 2644 */           _t = _t.getNextSibling();
/*      */         }
/*      */         else {
/* 2647 */           throw new NoViableAltException(_t);
/*      */         }
/*      */         break;
/*      */       }
/*      */     } catch (RecognitionException re) {
/* 2652 */       throw re;
/*      */     }
/* 2654 */     this._retTree = _t;
/*      */   }
/*      */ 
/*      */   public final void testSetRule(AST _t) throws RecognitionException
/*      */   {
/* 2659 */     GrammarAST testSetRule_AST_in = _t == ASTNULL ? null : (GrammarAST)_t;
/* 2660 */     GrammarAST id = null;
/*      */     try
/*      */     {
/* 2663 */       AST __t131 = _t;
/* 2664 */       GrammarAST tmp90_AST_in = (GrammarAST)_t;
/* 2665 */       match(_t, 8);
/* 2666 */       _t = _t.getFirstChild();
/* 2667 */       id = (GrammarAST)_t;
/* 2668 */       match(_t, 21);
/* 2669 */       _t = _t.getNextSibling();
/*      */ 
/* 2671 */       if (_t == null) _t = ASTNULL;
/* 2672 */       switch (_t.getType())
/*      */       {
/*      */       case 38:
/*      */       case 56:
/*      */       case 57:
/*      */       case 58:
/* 2678 */         modifier(_t);
/* 2679 */         _t = this._retTree;
/* 2680 */         break;
/*      */       case 22:
/* 2684 */         break;
/*      */       default:
/* 2688 */         throw new NoViableAltException(_t);
/*      */       }
/*      */ 
/* 2692 */       GrammarAST tmp91_AST_in = (GrammarAST)_t;
/* 2693 */       match(_t, 22);
/* 2694 */       _t = _t.getNextSibling();
/* 2695 */       GrammarAST tmp92_AST_in = (GrammarAST)_t;
/* 2696 */       match(_t, 24);
/* 2697 */       _t = _t.getNextSibling();
/*      */ 
/* 2699 */       if (_t == null) _t = ASTNULL;
/* 2700 */       switch (_t.getType())
/*      */       {
/*      */       case 4:
/* 2703 */         GrammarAST tmp93_AST_in = (GrammarAST)_t;
/* 2704 */         match(_t, 4);
/* 2705 */         _t = _t.getNextSibling();
/* 2706 */         break;
/*      */       case 9:
/*      */       case 33:
/*      */       case 46:
/* 2712 */         break;
/*      */       default:
/* 2716 */         throw new NoViableAltException(_t);
/*      */       }
/*      */ 
/* 2721 */       if (_t == null) _t = ASTNULL;
/* 2722 */       switch (_t.getType())
/*      */       {
/*      */       case 33:
/* 2725 */         ruleScopeSpec(_t);
/* 2726 */         _t = this._retTree;
/* 2727 */         break;
/*      */       case 9:
/*      */       case 46:
/* 2732 */         break;
/*      */       default:
/* 2736 */         throw new NoViableAltException(_t);
/*      */       }
/*      */ 
/*      */       while (true)
/*      */       {
/* 2743 */         if (_t == null) _t = ASTNULL;
/* 2744 */         if (_t.getType() != 46) break;
/* 2745 */         GrammarAST tmp94_AST_in = (GrammarAST)_t;
/* 2746 */         match(_t, 46);
/* 2747 */         _t = _t.getNextSibling();
/*      */       }
/*      */ 
/* 2755 */       AST __t137 = _t;
/* 2756 */       GrammarAST tmp95_AST_in = (GrammarAST)_t;
/* 2757 */       match(_t, 9);
/* 2758 */       _t = _t.getFirstChild();
/*      */ 
/* 2760 */       int _cnt141 = 0;
/*      */       while (true)
/*      */       {
/* 2763 */         if (_t == null) _t = ASTNULL;
/* 2764 */         if (_t.getType() == 17) {
/* 2765 */           AST __t139 = _t;
/* 2766 */           GrammarAST tmp96_AST_in = (GrammarAST)_t;
/* 2767 */           match(_t, 17);
/* 2768 */           _t = _t.getFirstChild();
/*      */ 
/* 2770 */           if (_t == null) _t = ASTNULL;
/* 2771 */           switch (_t.getType())
/*      */           {
/*      */           case 37:
/* 2774 */             GrammarAST tmp97_AST_in = (GrammarAST)_t;
/* 2775 */             match(_t, 37);
/* 2776 */             _t = _t.getNextSibling();
/* 2777 */             break;
/*      */           case 9:
/*      */           case 15:
/*      */           case 50:
/*      */           case 51:
/*      */           case 55:
/*      */           case 74:
/* 2786 */             break;
/*      */           default:
/* 2790 */             throw new NoViableAltException(_t);
/*      */           }
/*      */ 
/* 2794 */           testSetElement(_t);
/* 2795 */           _t = this._retTree;
/* 2796 */           GrammarAST tmp98_AST_in = (GrammarAST)_t;
/* 2797 */           match(_t, 20);
/* 2798 */           _t = _t.getNextSibling();
/* 2799 */           _t = __t139;
/* 2800 */           _t = _t.getNextSibling();
/*      */         }
/*      */         else {
/* 2803 */           if (_cnt141 >= 1) break; throw new NoViableAltException(_t);
/*      */         }
/*      */ 
/* 2806 */         _cnt141++;
/*      */       }
/*      */ 
/* 2809 */       GrammarAST tmp99_AST_in = (GrammarAST)_t;
/* 2810 */       match(_t, 19);
/* 2811 */       _t = _t.getNextSibling();
/* 2812 */       _t = __t137;
/* 2813 */       _t = _t.getNextSibling();
/*      */ 
/* 2815 */       if (_t == null) _t = ASTNULL;
/* 2816 */       switch (_t.getType())
/*      */       {
/*      */       case 66:
/*      */       case 67:
/* 2820 */         exceptionGroup(_t);
/* 2821 */         _t = this._retTree;
/* 2822 */         break;
/*      */       case 18:
/* 2826 */         break;
/*      */       default:
/* 2830 */         throw new NoViableAltException(_t);
/*      */       }
/*      */ 
/* 2834 */       GrammarAST tmp100_AST_in = (GrammarAST)_t;
/* 2835 */       match(_t, 18);
/* 2836 */       _t = _t.getNextSibling();
/* 2837 */       _t = __t131;
/* 2838 */       _t = _t.getNextSibling();
/*      */     }
/*      */     catch (RecognitionException re) {
/* 2841 */       throw re;
/*      */     }
/* 2843 */     this._retTree = _t;
/*      */   }
/*      */ 
/*      */   private static final long[] mk_tokenSet_0()
/*      */   {
/* 2953 */     long[] data = { 616432089855819264L, 4016L, 0L, 0L };
/* 2954 */     return data;
/*      */   }
/*      */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.antlr.grammar.v2.TreeToNFAConverter
 * JD-Core Version:    0.6.2
 */