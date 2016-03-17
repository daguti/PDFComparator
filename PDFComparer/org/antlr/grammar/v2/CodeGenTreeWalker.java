/*      */ package org.antlr.grammar.v2;
/*      */ 
/*      */ import antlr.CommonToken;
/*      */ import antlr.MismatchedTokenException;
/*      */ import antlr.NoViableAltException;
/*      */ import antlr.RecognitionException;
/*      */ import antlr.Token;
/*      */ import antlr.TokenWithIndex;
/*      */ import antlr.TreeParser;
/*      */ import antlr.collections.AST;
/*      */ import java.io.PrintStream;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Collection;
/*      */ import java.util.HashSet;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.Set;
/*      */ import org.antlr.analysis.DFA;
/*      */ import org.antlr.analysis.LookaheadSet;
/*      */ import org.antlr.analysis.NFAState;
/*      */ import org.antlr.analysis.Transition;
/*      */ import org.antlr.codegen.CodeGenerator;
/*      */ import org.antlr.codegen.Target;
/*      */ import org.antlr.misc.IntSet;
/*      */ import org.antlr.misc.Utils;
/*      */ import org.antlr.stringtemplate.StringTemplate;
/*      */ import org.antlr.stringtemplate.StringTemplateGroup;
/*      */ import org.antlr.tool.CompositeGrammar;
/*      */ import org.antlr.tool.CompositeGrammarTree;
/*      */ import org.antlr.tool.ErrorManager;
/*      */ import org.antlr.tool.Grammar;
/*      */ import org.antlr.tool.Grammar.LabelElementPair;
/*      */ import org.antlr.tool.GrammarAST;
/*      */ import org.antlr.tool.Rule;
/*      */ 
/*      */ public class CodeGenTreeWalker extends TreeParser
/*      */   implements CodeGenTreeWalkerTokenTypes
/*      */ {
/*      */   protected static final int RULE_BLOCK_NESTING_LEVEL = 0;
/*      */   protected static final int OUTER_REWRITE_NESTING_LEVEL = 0;
/*   82 */   protected String currentRuleName = null;
/*   83 */   protected int blockNestingLevel = 0;
/*   84 */   protected int rewriteBlockNestingLevel = 0;
/*   85 */   protected int outerAltNum = 0;
/*   86 */   protected StringTemplate currentBlockST = null;
/*   87 */   protected boolean currentAltHasASTRewrite = false;
/*   88 */   protected int rewriteTreeNestingLevel = 0;
/*   89 */   protected Set rewriteRuleRefs = null;
/*      */   protected CodeGenerator generator;
/*      */   protected Grammar grammar;
/*      */   protected StringTemplateGroup templates;
/*      */   protected StringTemplate recognizerST;
/*      */   protected StringTemplate outputFileST;
/*      */   protected StringTemplate headerFileST;
/*  123 */   protected String outputOption = "";
/*      */ 
/* 3241 */   public static final String[] _tokenNames = { "<0>", "EOF", "<2>", "NULL_TREE_LOOKAHEAD", "\"options\"", "\"tokens\"", "\"parser\"", "LEXER", "RULE", "BLOCK", "OPTIONAL", "CLOSURE", "POSITIVE_CLOSURE", "SYNPRED", "RANGE", "CHAR_RANGE", "EPSILON", "ALT", "EOR", "EOB", "EOA", "ID", "ARG", "ARGLIST", "RET", "LEXER_GRAMMAR", "PARSER_GRAMMAR", "TREE_GRAMMAR", "COMBINED_GRAMMAR", "INITACTION", "FORCED_ACTION", "LABEL", "TEMPLATE", "\"scope\"", "\"import\"", "GATED_SEMPRED", "SYN_SEMPRED", "BACKTRACK_SEMPRED", "\"fragment\"", "DOT", "ACTION", "DOC_COMMENT", "SEMI", "\"lexer\"", "\"tree\"", "\"grammar\"", "AMPERSAND", "COLON", "RCURLY", "ASSIGN", "STRING_LITERAL", "CHAR_LITERAL", "INT", "STAR", "COMMA", "TOKEN_REF", "\"protected\"", "\"public\"", "\"private\"", "BANG", "ARG_ACTION", "\"returns\"", "\"throws\"", "LPAREN", "OR", "RPAREN", "\"catch\"", "\"finally\"", "PLUS_ASSIGN", "SEMPRED", "IMPLIES", "ROOT", "WILDCARD", "RULE_REF", "NOT", "TREE_BEGIN", "QUESTION", "PLUS", "OPEN_ELEMENT_OPTION", "CLOSE_ELEMENT_OPTION", "REWRITE", "ETC", "DOLLAR", "DOUBLE_QUOTE_STRING_LITERAL", "DOUBLE_ANGLE_STRING_LITERAL", "WS", "COMMENT", "SL_COMMENT", "ML_COMMENT", "STRAY_BRACKET", "ESC", "DIGIT", "XDIGIT", "NESTED_ARG_ACTION", "NESTED_ACTION", "ACTION_CHAR_LITERAL", "ACTION_STRING_LITERAL", "ACTION_ESC", "WS_LOOP", "INTERNAL_RULE_REF", "WS_OPT", "SRC" };
/*      */ 
/*      */   public String getCurrentRuleName()
/*      */   {
/*   67 */     return this.currentRuleName;
/*      */   }
/*      */ 
/*      */   public void setCurrentRuleName(String currentRuleName) {
/*   71 */     this.currentRuleName = currentRuleName;
/*      */   }
/*      */ 
/*      */   public int getOuterAltNum() {
/*   75 */     return this.outerAltNum;
/*      */   }
/*      */ 
/*      */   public void setOuterAltNum(int outerAltNum) {
/*   79 */     this.outerAltNum = outerAltNum;
/*      */   }
/*      */ 
/*      */   public void reportError(RecognitionException ex)
/*      */   {
/*   92 */     Token token = null;
/*   93 */     if ((ex instanceof MismatchedTokenException)) {
/*   94 */       token = ((MismatchedTokenException)ex).token;
/*      */     }
/*   96 */     else if ((ex instanceof NoViableAltException)) {
/*   97 */       token = ((NoViableAltException)ex).token;
/*      */     }
/*   99 */     ErrorManager.syntaxError(100, this.grammar, token, "codegen: " + ex.toString(), ex);
/*      */   }
/*      */ 
/*      */   public void reportError(String s)
/*      */   {
/*  108 */     System.out.println("codegen: error: " + s);
/*      */   }
/*      */ 
/*      */   protected StringTemplate getWildcardST(GrammarAST elementAST, GrammarAST ast_suffix, String label)
/*      */   {
/*  126 */     String name = "wildcard";
/*  127 */     if (this.grammar.type == 1) {
/*  128 */       name = "wildcardChar";
/*      */     }
/*  130 */     return getTokenElementST(name, name, elementAST, ast_suffix, label);
/*      */   }
/*      */ 
/*      */   protected StringTemplate getRuleElementST(String name, String ruleTargetName, GrammarAST elementAST, GrammarAST ast_suffix, String label)
/*      */   {
/*  139 */     String suffix = getSTSuffix(elementAST, ast_suffix, label);
/*  140 */     name = name + suffix;
/*      */ 
/*  143 */     Rule r = this.grammar.getRule(this.currentRuleName);
/*  144 */     if (((this.grammar.buildAST()) || (suffix.length() > 0)) && (label == null) && ((r == null) || (!r.isSynPred)))
/*      */     {
/*  148 */       label = this.generator.createUniqueLabel(ruleTargetName);
/*  149 */       CommonToken labelTok = new CommonToken(21, label);
/*  150 */       this.grammar.defineRuleRefLabel(this.currentRuleName, labelTok, elementAST);
/*      */     }
/*  152 */     StringTemplate elementST = this.templates.getInstanceOf(name);
/*  153 */     if (label != null) {
/*  154 */       elementST.setAttribute("label", label);
/*      */     }
/*  156 */     return elementST;
/*      */   }
/*      */ 
/*      */   protected StringTemplate getTokenElementST(String name, String elementName, GrammarAST elementAST, GrammarAST ast_suffix, String label)
/*      */   {
/*  165 */     String suffix = getSTSuffix(elementAST, ast_suffix, label);
/*  166 */     name = name + suffix;
/*      */ 
/*  169 */     Rule r = this.grammar.getRule(this.currentRuleName);
/*  170 */     if (((this.grammar.buildAST()) || (suffix.length() > 0)) && (label == null) && ((r == null) || (!r.isSynPred)))
/*      */     {
/*  173 */       label = this.generator.createUniqueLabel(elementName);
/*  174 */       CommonToken labelTok = new CommonToken(21, label);
/*  175 */       this.grammar.defineTokenRefLabel(this.currentRuleName, labelTok, elementAST);
/*      */     }
/*  177 */     StringTemplate elementST = this.templates.getInstanceOf(name);
/*  178 */     if (label != null) {
/*  179 */       elementST.setAttribute("label", label);
/*      */     }
/*  181 */     return elementST;
/*      */   }
/*      */ 
/*      */   public boolean isListLabel(String label) {
/*  185 */     boolean hasListLabel = false;
/*  186 */     if (label != null) {
/*  187 */       Rule r = this.grammar.getRule(this.currentRuleName);
/*  188 */       String stName = null;
/*  189 */       if (r != null) {
/*  190 */         Grammar.LabelElementPair pair = r.getLabel(label);
/*  191 */         if ((pair != null) && ((pair.type == 4) || (pair.type == 3) || (pair.type == 7)))
/*      */         {
/*  196 */           hasListLabel = true;
/*      */         }
/*      */       }
/*      */     }
/*  200 */     return hasListLabel;
/*      */   }
/*      */ 
/*      */   protected String getSTSuffix(GrammarAST elementAST, GrammarAST ast_suffix, String label)
/*      */   {
/*  207 */     if (this.grammar.type == 1) {
/*  208 */       return "";
/*      */     }
/*      */ 
/*  212 */     String operatorPart = "";
/*  213 */     String rewritePart = "";
/*  214 */     String listLabelPart = "";
/*  215 */     Rule ruleDescr = this.grammar.getRule(this.currentRuleName);
/*  216 */     if ((ast_suffix != null) && (!ruleDescr.isSynPred)) {
/*  217 */       if (ast_suffix.getType() == 71) {
/*  218 */         operatorPart = "RuleRoot";
/*      */       }
/*  220 */       else if (ast_suffix.getType() == 59) {
/*  221 */         operatorPart = "Bang";
/*      */       }
/*      */     }
/*  224 */     if ((this.currentAltHasASTRewrite) && (elementAST.getType() != 72)) {
/*  225 */       rewritePart = "Track";
/*      */     }
/*  227 */     if (isListLabel(label)) {
/*  228 */       listLabelPart = "AndListLabel";
/*      */     }
/*  230 */     String STsuffix = operatorPart + rewritePart + listLabelPart;
/*      */ 
/*  233 */     return STsuffix;
/*      */   }
/*      */ 
/*      */   protected List<String> getTokenTypesAsTargetLabels(Set<GrammarAST> refs)
/*      */   {
/*  238 */     if ((refs == null) || (refs.size() == 0)) {
/*  239 */       return null;
/*      */     }
/*  241 */     List labels = new ArrayList(refs.size());
/*  242 */     for (Iterator i$ = refs.iterator(); i$.hasNext(); ) { GrammarAST t = (GrammarAST)i$.next();
/*      */       String label;
/*      */       String label;
/*  244 */       if (t.getType() == 73) {
/*  245 */         label = t.getText();
/*      */       }
/*      */       else
/*      */       {
/*      */         String label;
/*  247 */         if (t.getType() == 31) {
/*  248 */           label = t.getText();
/*      */         }
/*      */         else
/*      */         {
/*  252 */           label = this.generator.getTokenTypeAsTargetLabel(this.grammar.getTokenType(t.getText()));
/*      */         }
/*      */       }
/*  255 */       labels.add(label);
/*      */     }
/*  257 */     return labels;
/*      */   }
/*      */ 
/*      */   public void init(Grammar g) {
/*  261 */     this.grammar = g;
/*  262 */     this.generator = this.grammar.getCodeGenerator();
/*  263 */     this.templates = this.generator.getTemplates();
/*      */   }
/*      */   public CodeGenTreeWalker() {
/*  266 */     this.tokenNames = _tokenNames;
/*      */   }
/*      */ 
/*      */   public final void grammar(AST _t, Grammar g, StringTemplate recognizerST, StringTemplate outputFileST, StringTemplate headerFileST)
/*      */     throws RecognitionException
/*      */   {
/*  276 */     GrammarAST grammar_AST_in = _t == ASTNULL ? null : (GrammarAST)_t;
/*      */ 
/*  278 */     init(g);
/*  279 */     this.recognizerST = recognizerST;
/*  280 */     this.outputFileST = outputFileST;
/*  281 */     this.headerFileST = headerFileST;
/*  282 */     String superClass = (String)g.getOption("superClass");
/*  283 */     this.outputOption = ((String)g.getOption("output"));
/*  284 */     recognizerST.setAttribute("superClass", superClass);
/*  285 */     if (g.type != 1) {
/*  286 */       recognizerST.setAttribute("ASTLabelType", g.getOption("ASTLabelType"));
/*      */     }
/*  288 */     if ((g.type == 3) && (g.getOption("ASTLabelType") == null)) {
/*  289 */       ErrorManager.grammarWarning(152, g, null, g.name);
/*      */     }
/*      */ 
/*  294 */     if (g.type != 3) {
/*  295 */       recognizerST.setAttribute("labelType", g.getOption("TokenLabelType"));
/*      */     }
/*  297 */     recognizerST.setAttribute("numRules", this.grammar.getRules().size());
/*  298 */     outputFileST.setAttribute("numRules", this.grammar.getRules().size());
/*  299 */     headerFileST.setAttribute("numRules", this.grammar.getRules().size());
/*      */     try
/*      */     {
/*  304 */       if (_t == null) _t = ASTNULL;
/*  305 */       switch (_t.getType())
/*      */       {
/*      */       case 25:
/*  308 */         AST __t3 = _t;
/*  309 */         GrammarAST tmp1_AST_in = (GrammarAST)_t;
/*  310 */         match(_t, 25);
/*  311 */         _t = _t.getFirstChild();
/*  312 */         grammarSpec(_t);
/*  313 */         _t = this._retTree;
/*  314 */         _t = __t3;
/*  315 */         _t = _t.getNextSibling();
/*  316 */         break;
/*      */       case 26:
/*  320 */         AST __t4 = _t;
/*  321 */         GrammarAST tmp2_AST_in = (GrammarAST)_t;
/*  322 */         match(_t, 26);
/*  323 */         _t = _t.getFirstChild();
/*  324 */         grammarSpec(_t);
/*  325 */         _t = this._retTree;
/*  326 */         _t = __t4;
/*  327 */         _t = _t.getNextSibling();
/*  328 */         break;
/*      */       case 27:
/*  332 */         AST __t5 = _t;
/*  333 */         GrammarAST tmp3_AST_in = (GrammarAST)_t;
/*  334 */         match(_t, 27);
/*  335 */         _t = _t.getFirstChild();
/*  336 */         grammarSpec(_t);
/*  337 */         _t = this._retTree;
/*  338 */         _t = __t5;
/*  339 */         _t = _t.getNextSibling();
/*  340 */         break;
/*      */       case 28:
/*  344 */         AST __t6 = _t;
/*  345 */         GrammarAST tmp4_AST_in = (GrammarAST)_t;
/*  346 */         match(_t, 28);
/*  347 */         _t = _t.getFirstChild();
/*  348 */         grammarSpec(_t);
/*  349 */         _t = this._retTree;
/*  350 */         _t = __t6;
/*  351 */         _t = _t.getNextSibling();
/*  352 */         break;
/*      */       default:
/*  356 */         throw new NoViableAltException(_t);
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (RecognitionException ex)
/*      */     {
/*  362 */       reportError(ex);
/*  363 */       if (_t != null) _t = _t.getNextSibling();
/*      */     }
/*  365 */     this._retTree = _t;
/*      */   }
/*      */ 
/*      */   public final void grammarSpec(AST _t) throws RecognitionException
/*      */   {
/*  370 */     GrammarAST grammarSpec_AST_in = _t == ASTNULL ? null : (GrammarAST)_t;
/*  371 */     GrammarAST name = null;
/*  372 */     GrammarAST cmt = null;
/*      */     try
/*      */     {
/*  375 */       name = (GrammarAST)_t;
/*  376 */       match(_t, 21);
/*  377 */       _t = _t.getNextSibling();
/*      */ 
/*  379 */       if (_t == null) _t = ASTNULL;
/*  380 */       switch (_t.getType())
/*      */       {
/*      */       case 41:
/*  383 */         cmt = (GrammarAST)_t;
/*  384 */         match(_t, 41);
/*  385 */         _t = _t.getNextSibling();
/*      */ 
/*  387 */         this.outputFileST.setAttribute("docComment", cmt.getText());
/*  388 */         this.headerFileST.setAttribute("docComment", cmt.getText());
/*      */ 
/*  390 */         break;
/*      */       case 4:
/*      */       case 5:
/*      */       case 8:
/*      */       case 33:
/*      */       case 34:
/*      */       case 46:
/*  399 */         break;
/*      */       default:
/*  403 */         throw new NoViableAltException(_t);
/*      */       }
/*      */ 
/*  408 */       this.recognizerST.setAttribute("name", this.grammar.getRecognizerName());
/*  409 */       this.outputFileST.setAttribute("name", this.grammar.getRecognizerName());
/*  410 */       this.headerFileST.setAttribute("name", this.grammar.getRecognizerName());
/*  411 */       this.recognizerST.setAttribute("scopes", this.grammar.getGlobalScopes());
/*  412 */       this.headerFileST.setAttribute("scopes", this.grammar.getGlobalScopes());
/*      */ 
/*  415 */       if (_t == null) _t = ASTNULL;
/*  416 */       switch (_t.getType())
/*      */       {
/*      */       case 4:
/*  419 */         AST __t12 = _t;
/*  420 */         GrammarAST tmp5_AST_in = (GrammarAST)_t;
/*  421 */         match(_t, 4);
/*  422 */         _t = _t.getFirstChild();
/*  423 */         GrammarAST tmp6_AST_in = (GrammarAST)_t;
/*  424 */         if (_t == null) throw new MismatchedTokenException();
/*  425 */         _t = _t.getNextSibling();
/*  426 */         _t = __t12;
/*  427 */         _t = _t.getNextSibling();
/*  428 */         break;
/*      */       case 5:
/*      */       case 8:
/*      */       case 33:
/*      */       case 34:
/*      */       case 46:
/*  436 */         break;
/*      */       default:
/*  440 */         throw new NoViableAltException(_t);
/*      */       }
/*      */ 
/*  445 */       if (_t == null) _t = ASTNULL;
/*  446 */       switch (_t.getType())
/*      */       {
/*      */       case 34:
/*  449 */         AST __t14 = _t;
/*  450 */         GrammarAST tmp7_AST_in = (GrammarAST)_t;
/*  451 */         match(_t, 34);
/*  452 */         _t = _t.getFirstChild();
/*  453 */         GrammarAST tmp8_AST_in = (GrammarAST)_t;
/*  454 */         if (_t == null) throw new MismatchedTokenException();
/*  455 */         _t = _t.getNextSibling();
/*  456 */         _t = __t14;
/*  457 */         _t = _t.getNextSibling();
/*  458 */         break;
/*      */       case 5:
/*      */       case 8:
/*      */       case 33:
/*      */       case 46:
/*  465 */         break;
/*      */       default:
/*  469 */         throw new NoViableAltException(_t);
/*      */       }
/*      */ 
/*  474 */       if (_t == null) _t = ASTNULL;
/*  475 */       switch (_t.getType())
/*      */       {
/*      */       case 5:
/*  478 */         AST __t16 = _t;
/*  479 */         GrammarAST tmp9_AST_in = (GrammarAST)_t;
/*  480 */         match(_t, 5);
/*  481 */         _t = _t.getFirstChild();
/*  482 */         GrammarAST tmp10_AST_in = (GrammarAST)_t;
/*  483 */         if (_t == null) throw new MismatchedTokenException();
/*  484 */         _t = _t.getNextSibling();
/*  485 */         _t = __t16;
/*  486 */         _t = _t.getNextSibling();
/*  487 */         break;
/*      */       case 8:
/*      */       case 33:
/*      */       case 46:
/*  493 */         break;
/*      */       default:
/*  497 */         throw new NoViableAltException(_t);
/*      */       }
/*      */ 
/*      */       while (true)
/*      */       {
/*  504 */         if (_t == null) _t = ASTNULL;
/*  505 */         if (_t.getType() != 33) break;
/*  506 */         attrScope(_t);
/*  507 */         _t = this._retTree;
/*      */       }
/*      */ 
/*      */       while (true)
/*      */       {
/*  518 */         if (_t == null) _t = ASTNULL;
/*  519 */         if (_t.getType() != 46) break;
/*  520 */         GrammarAST tmp11_AST_in = (GrammarAST)_t;
/*  521 */         match(_t, 46);
/*  522 */         _t = _t.getNextSibling();
/*      */       }
/*      */ 
/*  530 */       rules(_t, this.recognizerST);
/*  531 */       _t = this._retTree;
/*      */     }
/*      */     catch (RecognitionException ex) {
/*  534 */       reportError(ex);
/*  535 */       if (_t != null) _t = _t.getNextSibling();
/*      */     }
/*  537 */     this._retTree = _t;
/*      */   }
/*      */ 
/*      */   public final void attrScope(AST _t) throws RecognitionException
/*      */   {
/*  542 */     GrammarAST attrScope_AST_in = _t == ASTNULL ? null : (GrammarAST)_t;
/*      */     try
/*      */     {
/*  545 */       AST __t8 = _t;
/*  546 */       GrammarAST tmp12_AST_in = (GrammarAST)_t;
/*  547 */       match(_t, 33);
/*  548 */       _t = _t.getFirstChild();
/*  549 */       GrammarAST tmp13_AST_in = (GrammarAST)_t;
/*  550 */       match(_t, 21);
/*  551 */       _t = _t.getNextSibling();
/*  552 */       GrammarAST tmp14_AST_in = (GrammarAST)_t;
/*  553 */       match(_t, 40);
/*  554 */       _t = _t.getNextSibling();
/*  555 */       _t = __t8;
/*  556 */       _t = _t.getNextSibling();
/*      */     }
/*      */     catch (RecognitionException ex) {
/*  559 */       reportError(ex);
/*  560 */       if (_t != null) _t = _t.getNextSibling();
/*      */     }
/*  562 */     this._retTree = _t;
/*      */   }
/*      */ 
/*      */   public final void rules(AST _t, StringTemplate recognizerST)
/*      */     throws RecognitionException
/*      */   {
/*  569 */     GrammarAST rules_AST_in = _t == ASTNULL ? null : (GrammarAST)_t;
/*      */     try
/*      */     {
/*  576 */       int _cnt24 = 0;
/*      */       while (true)
/*      */       {
/*  579 */         if (_t == null) _t = ASTNULL;
/*  580 */         if (_t.getType() == 8)
/*      */         {
/*  583 */           String ruleName = _t.getFirstChild().getText();
/*  584 */           Rule r = this.grammar.getRule(ruleName);
/*      */ 
/*  586 */           if (_t == null) _t = ASTNULL;
/*  587 */           if ((_t.getType() == 8) && (this.grammar.generateMethodForRule(ruleName))) {
/*  588 */             StringTemplate rST = rule(_t);
/*  589 */             _t = this._retTree;
/*      */ 
/*  591 */             if (rST != null) {
/*  592 */               recognizerST.setAttribute("rules", rST);
/*  593 */               this.outputFileST.setAttribute("rules", rST);
/*  594 */               this.headerFileST.setAttribute("rules", rST);
/*      */             }
/*      */ 
/*      */           }
/*  598 */           else if (_t.getType() == 8) {
/*  599 */             GrammarAST tmp15_AST_in = (GrammarAST)_t;
/*  600 */             match(_t, 8);
/*  601 */             _t = _t.getNextSibling();
/*      */           }
/*      */           else {
/*  604 */             throw new NoViableAltException(_t);
/*      */           }
/*      */ 
/*      */         }
/*      */         else
/*      */         {
/*  610 */           if (_cnt24 >= 1) break; throw new NoViableAltException(_t);
/*      */         }
/*      */ 
/*  613 */         _cnt24++;
/*      */       }
/*      */     }
/*      */     catch (RecognitionException ex)
/*      */     {
/*  618 */       reportError(ex);
/*  619 */       if (_t != null) _t = _t.getNextSibling();
/*      */     }
/*  621 */     this._retTree = _t;
/*      */   }
/*      */ 
/*      */   public final StringTemplate rule(AST _t) throws RecognitionException {
/*  625 */     StringTemplate code = null;
/*      */ 
/*  627 */     GrammarAST rule_AST_in = _t == ASTNULL ? null : (GrammarAST)_t;
/*  628 */     GrammarAST id = null;
/*  629 */     GrammarAST mod = null;
/*      */ 
/*  632 */     String initAction = null;
/*      */ 
/*  635 */     GrammarAST block = rule_AST_in.getFirstChildWithType(9);
/*  636 */     DFA dfa = block.getLookaheadDFA();
/*      */ 
/*  639 */     this.blockNestingLevel = -1;
/*  640 */     Rule ruleDescr = this.grammar.getRule(rule_AST_in.getFirstChild().getText());
/*      */ 
/*  644 */     StringTemplateGroup saveGroup = this.templates;
/*  645 */     if (ruleDescr.isSynPred) {
/*  646 */       this.templates = this.generator.getBaseTemplates();
/*      */     }
/*      */ 
/*      */     try
/*      */     {
/*  651 */       AST __t26 = _t;
/*  652 */       GrammarAST tmp16_AST_in = (GrammarAST)_t;
/*  653 */       match(_t, 8);
/*  654 */       _t = _t.getFirstChild();
/*  655 */       id = (GrammarAST)_t;
/*  656 */       match(_t, 21);
/*  657 */       _t = _t.getNextSibling();
/*  658 */       String r = id.getText(); this.currentRuleName = r;
/*      */ 
/*  660 */       if (_t == null) _t = ASTNULL;
/*  661 */       switch (_t.getType())
/*      */       {
/*      */       case 38:
/*      */       case 56:
/*      */       case 57:
/*      */       case 58:
/*  667 */         mod = _t == ASTNULL ? null : (GrammarAST)_t;
/*  668 */         modifier(_t);
/*  669 */         _t = this._retTree;
/*  670 */         break;
/*      */       case 22:
/*  674 */         break;
/*      */       default:
/*  678 */         throw new NoViableAltException(_t);
/*      */       }
/*      */ 
/*  682 */       AST __t28 = _t;
/*  683 */       GrammarAST tmp17_AST_in = (GrammarAST)_t;
/*  684 */       match(_t, 22);
/*  685 */       _t = _t.getFirstChild();
/*      */ 
/*  687 */       if (_t == null) _t = ASTNULL;
/*  688 */       switch (_t.getType())
/*      */       {
/*      */       case 60:
/*  691 */         GrammarAST tmp18_AST_in = (GrammarAST)_t;
/*  692 */         match(_t, 60);
/*  693 */         _t = _t.getNextSibling();
/*  694 */         break;
/*      */       case 3:
/*  698 */         break;
/*      */       default:
/*  702 */         throw new NoViableAltException(_t);
/*      */       }
/*      */ 
/*  706 */       _t = __t28;
/*  707 */       _t = _t.getNextSibling();
/*  708 */       AST __t30 = _t;
/*  709 */       GrammarAST tmp19_AST_in = (GrammarAST)_t;
/*  710 */       match(_t, 24);
/*  711 */       _t = _t.getFirstChild();
/*      */ 
/*  713 */       if (_t == null) _t = ASTNULL;
/*  714 */       switch (_t.getType())
/*      */       {
/*      */       case 60:
/*  717 */         GrammarAST tmp20_AST_in = (GrammarAST)_t;
/*  718 */         match(_t, 60);
/*  719 */         _t = _t.getNextSibling();
/*  720 */         break;
/*      */       case 3:
/*  724 */         break;
/*      */       default:
/*  728 */         throw new NoViableAltException(_t);
/*      */       }
/*      */ 
/*  732 */       _t = __t30;
/*  733 */       _t = _t.getNextSibling();
/*      */ 
/*  735 */       if (_t == null) _t = ASTNULL;
/*  736 */       switch (_t.getType())
/*      */       {
/*      */       case 4:
/*  739 */         AST __t33 = _t;
/*  740 */         GrammarAST tmp21_AST_in = (GrammarAST)_t;
/*  741 */         match(_t, 4);
/*  742 */         _t = _t.getFirstChild();
/*  743 */         GrammarAST tmp22_AST_in = (GrammarAST)_t;
/*  744 */         if (_t == null) throw new MismatchedTokenException();
/*  745 */         _t = _t.getNextSibling();
/*  746 */         _t = __t33;
/*  747 */         _t = _t.getNextSibling();
/*  748 */         break;
/*      */       case 9:
/*      */       case 33:
/*      */       case 46:
/*  754 */         break;
/*      */       default:
/*  758 */         throw new NoViableAltException(_t);
/*      */       }
/*      */ 
/*  763 */       if (_t == null) _t = ASTNULL;
/*  764 */       switch (_t.getType())
/*      */       {
/*      */       case 33:
/*  767 */         ruleScopeSpec(_t);
/*  768 */         _t = this._retTree;
/*  769 */         break;
/*      */       case 9:
/*      */       case 46:
/*  774 */         break;
/*      */       default:
/*  778 */         throw new NoViableAltException(_t);
/*      */       }
/*      */ 
/*      */       while (true)
/*      */       {
/*  785 */         if (_t == null) _t = ASTNULL;
/*  786 */         if (_t.getType() != 46) break;
/*  787 */         GrammarAST tmp23_AST_in = (GrammarAST)_t;
/*  788 */         match(_t, 46);
/*  789 */         _t = _t.getNextSibling();
/*      */       }
/*      */ 
/*  797 */       StringTemplate b = block(_t, "ruleBlock", dfa);
/*  798 */       _t = this._retTree;
/*      */ 
/*  800 */       String description = this.grammar.grammarTreeToString(rule_AST_in.getFirstChildWithType(9), false);
/*      */ 
/*  803 */       description = this.generator.target.getTargetStringLiteralFromString(description);
/*      */ 
/*  805 */       b.setAttribute("description", description);
/*      */ 
/*  807 */       String stName = null;
/*  808 */       if (ruleDescr.isSynPred) {
/*  809 */         stName = "synpredRule";
/*      */       }
/*  811 */       else if (this.grammar.type == 1) {
/*  812 */         if (r.equals("Tokens")) {
/*  813 */           stName = "tokensRule";
/*      */         }
/*      */         else {
/*  816 */           stName = "lexerRule";
/*      */         }
/*      */ 
/*      */       }
/*  820 */       else if ((this.grammar.type != 4) || (!Character.isUpperCase(r.charAt(0))))
/*      */       {
/*  823 */         stName = "rule";
/*      */       }
/*      */ 
/*  826 */       code = this.templates.getInstanceOf(stName);
/*  827 */       if (code.getName().equals("rule")) {
/*  828 */         code.setAttribute("emptyRule", Boolean.valueOf(this.grammar.isEmptyRule(block)));
/*      */       }
/*      */ 
/*  831 */       code.setAttribute("ruleDescriptor", ruleDescr);
/*  832 */       String memo = (String)this.grammar.getBlockOption(rule_AST_in, "memoize");
/*  833 */       if (memo == null) {
/*  834 */         memo = (String)this.grammar.getOption("memoize");
/*      */       }
/*  836 */       if ((memo != null) && (memo.equals("true")) && ((stName.equals("rule")) || (stName.equals("lexerRule"))))
/*      */       {
/*  839 */         code.setAttribute("memoize", Boolean.valueOf((memo != null) && (memo.equals("true"))));
/*      */       }
/*      */ 
/*  844 */       if (_t == null) _t = ASTNULL;
/*  845 */       switch (_t.getType())
/*      */       {
/*      */       case 66:
/*      */       case 67:
/*  849 */         exceptionGroup(_t, code);
/*  850 */         _t = this._retTree;
/*  851 */         break;
/*      */       case 18:
/*  855 */         break;
/*      */       default:
/*  859 */         throw new NoViableAltException(_t);
/*      */       }
/*      */ 
/*  863 */       GrammarAST tmp24_AST_in = (GrammarAST)_t;
/*  864 */       match(_t, 18);
/*  865 */       _t = _t.getNextSibling();
/*  866 */       _t = __t26;
/*  867 */       _t = _t.getNextSibling();
/*      */ 
/*  869 */       if (code != null) {
/*  870 */         if (this.grammar.type == 1) {
/*  871 */           boolean naked = (r.equals("Tokens")) || ((mod != null) && (mod.getText().equals("fragment")));
/*      */ 
/*  874 */           code.setAttribute("nakedBlock", Boolean.valueOf(naked));
/*      */         }
/*      */         else {
/*  877 */           description = this.grammar.grammarTreeToString(rule_AST_in, false);
/*      */ 
/*  879 */           description = this.generator.target.getTargetStringLiteralFromString(description);
/*      */ 
/*  881 */           code.setAttribute("description", description);
/*      */         }
/*  883 */         Rule theRule = this.grammar.getRule(r);
/*  884 */         this.generator.translateActionAttributeReferencesForSingleScope(theRule, theRule.getActions());
/*      */ 
/*  888 */         code.setAttribute("ruleName", r);
/*  889 */         code.setAttribute("block", b);
/*  890 */         if (initAction != null) {
/*  891 */           code.setAttribute("initAction", initAction);
/*      */         }
/*      */       }
/*  894 */       this.templates = saveGroup;
/*      */     }
/*      */     catch (RecognitionException ex)
/*      */     {
/*  898 */       reportError(ex);
/*  899 */       if (_t != null) _t = _t.getNextSibling();
/*      */     }
/*  901 */     this._retTree = _t;
/*  902 */     return code;
/*      */   }
/*      */ 
/*      */   public final void modifier(AST _t) throws RecognitionException
/*      */   {
/*  907 */     GrammarAST modifier_AST_in = _t == ASTNULL ? null : (GrammarAST)_t;
/*      */     try
/*      */     {
/*  910 */       if (_t == null) _t = ASTNULL;
/*  911 */       switch (_t.getType())
/*      */       {
/*      */       case 56:
/*  914 */         GrammarAST tmp25_AST_in = (GrammarAST)_t;
/*  915 */         match(_t, 56);
/*  916 */         _t = _t.getNextSibling();
/*  917 */         break;
/*      */       case 57:
/*  921 */         GrammarAST tmp26_AST_in = (GrammarAST)_t;
/*  922 */         match(_t, 57);
/*  923 */         _t = _t.getNextSibling();
/*  924 */         break;
/*      */       case 58:
/*  928 */         GrammarAST tmp27_AST_in = (GrammarAST)_t;
/*  929 */         match(_t, 58);
/*  930 */         _t = _t.getNextSibling();
/*  931 */         break;
/*      */       case 38:
/*  935 */         GrammarAST tmp28_AST_in = (GrammarAST)_t;
/*  936 */         match(_t, 38);
/*  937 */         _t = _t.getNextSibling();
/*  938 */         break;
/*      */       default:
/*  942 */         throw new NoViableAltException(_t);
/*      */       }
/*      */     }
/*      */     catch (RecognitionException ex)
/*      */     {
/*  947 */       reportError(ex);
/*  948 */       if (_t != null) _t = _t.getNextSibling();
/*      */     }
/*  950 */     this._retTree = _t;
/*      */   }
/*      */ 
/*      */   public final void ruleScopeSpec(AST _t) throws RecognitionException
/*      */   {
/*  955 */     GrammarAST ruleScopeSpec_AST_in = _t == ASTNULL ? null : (GrammarAST)_t;
/*      */     try
/*      */     {
/*  958 */       AST __t40 = _t;
/*  959 */       GrammarAST tmp29_AST_in = (GrammarAST)_t;
/*  960 */       match(_t, 33);
/*  961 */       _t = _t.getFirstChild();
/*      */ 
/*  963 */       if (_t == null) _t = ASTNULL;
/*  964 */       switch (_t.getType())
/*      */       {
/*      */       case 40:
/*  967 */         GrammarAST tmp30_AST_in = (GrammarAST)_t;
/*  968 */         match(_t, 40);
/*  969 */         _t = _t.getNextSibling();
/*  970 */         break;
/*      */       case 3:
/*      */       case 21:
/*  975 */         break;
/*      */       default:
/*  979 */         throw new NoViableAltException(_t);
/*      */       }
/*      */ 
/*      */       while (true)
/*      */       {
/*  986 */         if (_t == null) _t = ASTNULL;
/*  987 */         if (_t.getType() != 21) break;
/*  988 */         GrammarAST tmp31_AST_in = (GrammarAST)_t;
/*  989 */         match(_t, 21);
/*  990 */         _t = _t.getNextSibling();
/*      */       }
/*      */ 
/*  998 */       _t = __t40;
/*  999 */       _t = _t.getNextSibling();
/*      */     }
/*      */     catch (RecognitionException ex) {
/* 1002 */       reportError(ex);
/* 1003 */       if (_t != null) _t = _t.getNextSibling();
/*      */     }
/* 1005 */     this._retTree = _t;
/*      */   }
/*      */ 
/*      */   public final StringTemplate block(AST _t, String blockTemplateName, DFA dfa)
/*      */     throws RecognitionException
/*      */   {
/* 1011 */     StringTemplate code = null;
/*      */ 
/* 1013 */     GrammarAST block_AST_in = _t == ASTNULL ? null : (GrammarAST)_t;
/*      */ 
/* 1015 */     StringTemplate decision = null;
/* 1016 */     if (dfa != null) {
/* 1017 */       code = this.templates.getInstanceOf(blockTemplateName);
/* 1018 */       decision = this.generator.genLookaheadDecision(this.recognizerST, dfa);
/* 1019 */       code.setAttribute("decision", decision);
/* 1020 */       code.setAttribute("decisionNumber", dfa.getDecisionNumber());
/* 1021 */       code.setAttribute("maxK", dfa.getMaxLookaheadDepth());
/* 1022 */       code.setAttribute("maxAlt", dfa.getNumberOfAlts());
/*      */     }
/*      */     else {
/* 1025 */       code = this.templates.getInstanceOf(blockTemplateName + "SingleAlt");
/*      */     }
/* 1027 */     this.blockNestingLevel += 1;
/* 1028 */     code.setAttribute("blockLevel", this.blockNestingLevel);
/* 1029 */     code.setAttribute("enclosingBlockLevel", this.blockNestingLevel - 1);
/* 1030 */     StringTemplate alt = null;
/* 1031 */     StringTemplate rew = null;
/* 1032 */     StringTemplate sb = null;
/* 1033 */     GrammarAST r = null;
/* 1034 */     int altNum = 1;
/* 1035 */     if (this.blockNestingLevel == 0) {
/* 1036 */       this.outerAltNum = 1;
/*      */     }
/*      */ 
/*      */     try
/*      */     {
/* 1041 */       if (_t == null) _t = ASTNULL;
/* 1042 */       if ((_t.getType() == 9) && (block_AST_in.getSetValue() != null)) {
/* 1043 */         sb = setBlock(_t);
/* 1044 */         _t = this._retTree;
/*      */ 
/* 1046 */         code.setAttribute("alts", sb);
/* 1047 */         this.blockNestingLevel -= 1;
/*      */       }
/* 1050 */       else if (_t.getType() == 9) {
/* 1051 */         AST __t45 = _t;
/* 1052 */         GrammarAST tmp32_AST_in = (GrammarAST)_t;
/* 1053 */         match(_t, 9);
/* 1054 */         _t = _t.getFirstChild();
/*      */ 
/* 1056 */         if (_t == null) _t = ASTNULL;
/* 1057 */         switch (_t.getType())
/*      */         {
/*      */         case 4:
/* 1060 */           GrammarAST tmp33_AST_in = (GrammarAST)_t;
/* 1061 */           match(_t, 4);
/* 1062 */           _t = _t.getNextSibling();
/* 1063 */           break;
/*      */         case 17:
/* 1067 */           break;
/*      */         default:
/* 1071 */           throw new NoViableAltException(_t);
/*      */         }
/*      */ 
/* 1076 */         int _cnt48 = 0;
/*      */         while (true)
/*      */         {
/* 1079 */           if (_t == null) _t = ASTNULL;
/* 1080 */           if (_t.getType() == 17) {
/* 1081 */             alt = alternative(_t);
/* 1082 */             _t = this._retTree;
/* 1083 */             r = (GrammarAST)_t;
/* 1084 */             rew = rewrite(_t);
/* 1085 */             _t = this._retTree;
/*      */ 
/* 1087 */             if (this.blockNestingLevel == 0) {
/* 1088 */               this.outerAltNum += 1;
/*      */             }
/*      */ 
/* 1093 */             boolean etc = (r.getType() == 80) && (r.getFirstChild() != null) && (r.getFirstChild().getType() == 81);
/*      */ 
/* 1097 */             if ((rew != null) && (!etc)) alt.setAttribute("rew", rew);
/*      */ 
/* 1099 */             code.setAttribute("alts", alt);
/* 1100 */             alt.setAttribute("altNum", Utils.integer(altNum));
/* 1101 */             alt.setAttribute("outerAlt", Boolean.valueOf(this.blockNestingLevel == 0));
/*      */ 
/* 1103 */             altNum++;
/*      */           }
/*      */           else
/*      */           {
/* 1107 */             if (_cnt48 >= 1) break; throw new NoViableAltException(_t);
/*      */           }
/*      */ 
/* 1110 */           _cnt48++;
/*      */         }
/*      */ 
/* 1113 */         GrammarAST tmp34_AST_in = (GrammarAST)_t;
/* 1114 */         match(_t, 19);
/* 1115 */         _t = _t.getNextSibling();
/* 1116 */         _t = __t45;
/* 1117 */         _t = _t.getNextSibling();
/* 1118 */         this.blockNestingLevel -= 1;
/*      */       }
/*      */       else {
/* 1121 */         throw new NoViableAltException(_t);
/*      */       }
/*      */     }
/*      */     catch (RecognitionException ex)
/*      */     {
/* 1126 */       reportError(ex);
/* 1127 */       if (_t != null) _t = _t.getNextSibling();
/*      */     }
/* 1129 */     this._retTree = _t;
/* 1130 */     return code;
/*      */   }
/*      */ 
/*      */   public final void exceptionGroup(AST _t, StringTemplate ruleST)
/*      */     throws RecognitionException
/*      */   {
/* 1137 */     GrammarAST exceptionGroup_AST_in = _t == ASTNULL ? null : (GrammarAST)_t;
/*      */     try
/*      */     {
/* 1140 */       if (_t == null) _t = ASTNULL;
/* 1141 */       switch (_t.getType())
/*      */       {
/*      */       case 66:
/* 1145 */         int _cnt52 = 0;
/*      */         while (true)
/*      */         {
/* 1148 */           if (_t == null) _t = ASTNULL;
/* 1149 */           if (_t.getType() == 66) {
/* 1150 */             exceptionHandler(_t, ruleST);
/* 1151 */             _t = this._retTree;
/*      */           }
/*      */           else {
/* 1154 */             if (_cnt52 >= 1) break; throw new NoViableAltException(_t);
/*      */           }
/*      */ 
/* 1157 */           _cnt52++;
/*      */         }
/*      */ 
/* 1161 */         if (_t == null) _t = ASTNULL;
/* 1162 */         switch (_t.getType())
/*      */         {
/*      */         case 67:
/* 1165 */           finallyClause(_t, ruleST);
/* 1166 */           _t = this._retTree;
/* 1167 */           break;
/*      */         case 18:
/* 1171 */           break;
/*      */         default:
/* 1175 */           throw new NoViableAltException(_t);
/*      */         }
/*      */ 
/*      */         break;
/*      */       case 67:
/* 1183 */         finallyClause(_t, ruleST);
/* 1184 */         _t = this._retTree;
/* 1185 */         break;
/*      */       default:
/* 1189 */         throw new NoViableAltException(_t);
/*      */       }
/*      */     }
/*      */     catch (RecognitionException ex)
/*      */     {
/* 1194 */       reportError(ex);
/* 1195 */       if (_t != null) _t = _t.getNextSibling();
/*      */     }
/* 1197 */     this._retTree = _t;
/*      */   }
/*      */ 
/*      */   public final StringTemplate setBlock(AST _t) throws RecognitionException {
/* 1201 */     StringTemplate code = null;
/*      */ 
/* 1203 */     GrammarAST setBlock_AST_in = _t == ASTNULL ? null : (GrammarAST)_t;
/* 1204 */     GrammarAST s = null;
/*      */ 
/* 1206 */     StringTemplate setcode = null;
/* 1207 */     if ((this.blockNestingLevel == 0) && (this.grammar.buildAST())) {
/* 1208 */       Rule r = this.grammar.getRule(this.currentRuleName);
/* 1209 */       this.currentAltHasASTRewrite = r.hasRewrite(this.outerAltNum);
/* 1210 */       if (this.currentAltHasASTRewrite) {
/* 1211 */         r.trackTokenReferenceInAlt(setBlock_AST_in, this.outerAltNum);
/*      */       }
/*      */     }
/*      */ 
/*      */     try
/*      */     {
/* 1217 */       s = (GrammarAST)_t;
/* 1218 */       match(_t, 9);
/* 1219 */       _t = _t.getNextSibling();
/*      */ 
/* 1221 */       int i = ((TokenWithIndex)s.getToken()).getIndex();
/* 1222 */       if (this.blockNestingLevel == 0) {
/* 1223 */         setcode = getTokenElementST("matchRuleBlockSet", "set", s, null, null);
/*      */       }
/*      */       else {
/* 1226 */         setcode = getTokenElementST("matchSet", "set", s, null, null);
/*      */       }
/* 1228 */       setcode.setAttribute("elementIndex", i);
/* 1229 */       if (this.grammar.type != 1) {
/* 1230 */         this.generator.generateLocalFOLLOW(s, "set", this.currentRuleName, i);
/*      */       }
/* 1232 */       setcode.setAttribute("s", this.generator.genSetExpr(this.templates, s.getSetValue(), 1, false));
/*      */ 
/* 1234 */       StringTemplate altcode = this.templates.getInstanceOf("alt");
/* 1235 */       altcode.setAttribute("elements.{el,line,pos}", setcode, Utils.integer(s.getLine()), Utils.integer(s.getColumn()));
/*      */ 
/* 1240 */       altcode.setAttribute("altNum", Utils.integer(1));
/* 1241 */       altcode.setAttribute("outerAlt", Boolean.valueOf(this.blockNestingLevel == 0));
/*      */ 
/* 1243 */       if ((!this.currentAltHasASTRewrite) && (this.grammar.buildAST())) {
/* 1244 */         altcode.setAttribute("autoAST", Boolean.valueOf(true));
/*      */       }
/* 1246 */       altcode.setAttribute("treeLevel", this.rewriteTreeNestingLevel);
/* 1247 */       code = altcode;
/*      */     }
/*      */     catch (RecognitionException ex)
/*      */     {
/* 1251 */       reportError(ex);
/* 1252 */       if (_t != null) _t = _t.getNextSibling();
/*      */     }
/* 1254 */     this._retTree = _t;
/* 1255 */     return code;
/*      */   }
/*      */ 
/*      */   public final StringTemplate alternative(AST _t) throws RecognitionException {
/* 1259 */     StringTemplate code = this.templates.getInstanceOf("alt");
/*      */ 
/* 1261 */     GrammarAST alternative_AST_in = _t == ASTNULL ? null : (GrammarAST)_t;
/* 1262 */     GrammarAST a = null;
/*      */ 
/* 1280 */     if ((this.blockNestingLevel == 0) && (this.grammar.buildAST())) {
/* 1281 */       Rule r = this.grammar.getRule(this.currentRuleName);
/* 1282 */       this.currentAltHasASTRewrite = r.hasRewrite(this.outerAltNum);
/*      */     }
/* 1284 */     String description = this.grammar.grammarTreeToString(alternative_AST_in, false);
/* 1285 */     description = this.generator.target.getTargetStringLiteralFromString(description);
/* 1286 */     code.setAttribute("description", description);
/* 1287 */     code.setAttribute("treeLevel", this.rewriteTreeNestingLevel);
/* 1288 */     if ((!this.currentAltHasASTRewrite) && (this.grammar.buildAST())) {
/* 1289 */       code.setAttribute("autoAST", Boolean.valueOf(true));
/*      */     }
/*      */ 
/*      */     try
/*      */     {
/* 1295 */       AST __t59 = _t;
/* 1296 */       a = _t == ASTNULL ? null : (GrammarAST)_t;
/* 1297 */       match(_t, 17);
/* 1298 */       _t = _t.getFirstChild();
/*      */ 
/* 1300 */       int _cnt61 = 0;
/*      */       while (true)
/*      */       {
/* 1303 */         if (_t == null) _t = ASTNULL;
/* 1304 */         if ((_t.getType() == 9) || (_t.getType() == 10) || (_t.getType() == 11) || (_t.getType() == 12) || (_t.getType() == 15) || (_t.getType() == 16) || (_t.getType() == 30) || (_t.getType() == 35) || (_t.getType() == 36) || (_t.getType() == 37) || (_t.getType() == 39) || (_t.getType() == 40) || (_t.getType() == 49) || (_t.getType() == 50) || (_t.getType() == 51) || (_t.getType() == 55) || (_t.getType() == 59) || (_t.getType() == 68) || (_t.getType() == 69) || (_t.getType() == 71) || (_t.getType() == 72) || (_t.getType() == 73) || (_t.getType() == 74) || (_t.getType() == 75)) {
/* 1305 */           GrammarAST elAST = (GrammarAST)_t;
/* 1306 */           StringTemplate e = element(_t, null, null);
/* 1307 */           _t = this._retTree;
/*      */ 
/* 1309 */           if (e != null) {
/* 1310 */             code.setAttribute("elements.{el,line,pos}", e, Utils.integer(elAST.getLine()), Utils.integer(elAST.getColumn()));
/*      */           }
/*      */ 
/*      */         }
/*      */         else
/*      */         {
/* 1319 */           if (_cnt61 >= 1) break; throw new NoViableAltException(_t);
/*      */         }
/*      */         StringTemplate e;
/* 1322 */         _cnt61++;
/*      */       }
/*      */ 
/* 1325 */       GrammarAST tmp35_AST_in = (GrammarAST)_t;
/* 1326 */       match(_t, 20);
/* 1327 */       _t = _t.getNextSibling();
/* 1328 */       _t = __t59;
/* 1329 */       _t = _t.getNextSibling();
/*      */     }
/*      */     catch (RecognitionException ex) {
/* 1332 */       reportError(ex);
/* 1333 */       if (_t != null) _t = _t.getNextSibling();
/*      */     }
/* 1335 */     this._retTree = _t;
/* 1336 */     return code;
/*      */   }
/*      */ 
/*      */   public final StringTemplate rewrite(AST _t) throws RecognitionException {
/* 1340 */     StringTemplate code = null;
/*      */ 
/* 1342 */     GrammarAST rewrite_AST_in = _t == ASTNULL ? null : (GrammarAST)_t;
/* 1343 */     GrammarAST r = null;
/* 1344 */     GrammarAST pred = null;
/*      */ 
/* 1347 */     if (rewrite_AST_in.getType() == 80) {
/* 1348 */       if (this.generator.grammar.buildTemplate()) {
/* 1349 */         code = this.templates.getInstanceOf("rewriteTemplate");
/*      */       }
/*      */       else {
/* 1352 */         code = this.templates.getInstanceOf("rewriteCode");
/* 1353 */         code.setAttribute("treeLevel", Utils.integer(0));
/* 1354 */         code.setAttribute("rewriteBlockLevel", Utils.integer(0));
/* 1355 */         code.setAttribute("referencedElementsDeep", getTokenTypesAsTargetLabels(rewrite_AST_in.rewriteRefsDeep));
/*      */ 
/* 1357 */         Set tokenLabels = this.grammar.getLabels(rewrite_AST_in.rewriteRefsDeep, 2);
/*      */ 
/* 1359 */         Set tokenListLabels = this.grammar.getLabels(rewrite_AST_in.rewriteRefsDeep, 4);
/*      */ 
/* 1361 */         Set ruleLabels = this.grammar.getLabels(rewrite_AST_in.rewriteRefsDeep, 1);
/*      */ 
/* 1363 */         Set ruleListLabels = this.grammar.getLabels(rewrite_AST_in.rewriteRefsDeep, 3);
/*      */ 
/* 1365 */         Set wildcardLabels = this.grammar.getLabels(rewrite_AST_in.rewriteRefsDeep, 6);
/*      */ 
/* 1367 */         Set wildcardListLabels = this.grammar.getLabels(rewrite_AST_in.rewriteRefsDeep, 7);
/*      */ 
/* 1371 */         StringTemplate retvalST = this.templates.getInstanceOf("prevRuleRootRef");
/* 1372 */         ruleLabels.add(retvalST.toString());
/* 1373 */         code.setAttribute("referencedTokenLabels", tokenLabels);
/* 1374 */         code.setAttribute("referencedTokenListLabels", tokenListLabels);
/* 1375 */         code.setAttribute("referencedRuleLabels", ruleLabels);
/* 1376 */         code.setAttribute("referencedRuleListLabels", ruleListLabels);
/* 1377 */         code.setAttribute("referencedWildcardLabels", wildcardLabels);
/* 1378 */         code.setAttribute("referencedWildcardListLabels", wildcardListLabels);
/*      */       }
/*      */     }
/*      */     else {
/* 1382 */       code = this.templates.getInstanceOf("noRewrite");
/* 1383 */       code.setAttribute("treeLevel", Utils.integer(0));
/* 1384 */       code.setAttribute("rewriteBlockLevel", Utils.integer(0));
/*      */     }
/*      */ 
/*      */     try
/*      */     {
/*      */       while (true)
/*      */       {
/* 1392 */         if (_t == null) _t = ASTNULL;
/* 1393 */         if (_t.getType() != 80) break;
/* 1394 */         this.rewriteRuleRefs = new HashSet();
/* 1395 */         AST __t96 = _t;
/* 1396 */         r = _t == ASTNULL ? null : (GrammarAST)_t;
/* 1397 */         match(_t, 80);
/* 1398 */         _t = _t.getFirstChild();
/*      */ 
/* 1400 */         if (_t == null) _t = ASTNULL;
/* 1401 */         switch (_t.getType())
/*      */         {
/*      */         case 69:
/* 1404 */           pred = (GrammarAST)_t;
/* 1405 */           match(_t, 69);
/* 1406 */           _t = _t.getNextSibling();
/* 1407 */           break;
/*      */         case 17:
/*      */         case 32:
/*      */         case 40:
/*      */         case 81:
/* 1414 */           break;
/*      */         default:
/* 1418 */           throw new NoViableAltException(_t);
/*      */         }
/*      */ 
/* 1422 */         StringTemplate alt = rewrite_alternative(_t);
/* 1423 */         _t = this._retTree;
/* 1424 */         _t = __t96;
/* 1425 */         _t = _t.getNextSibling();
/*      */ 
/* 1427 */         this.rewriteBlockNestingLevel = 0;
/* 1428 */         List predChunks = null;
/* 1429 */         if (pred != null)
/*      */         {
/* 1431 */           predChunks = this.generator.translateAction(this.currentRuleName, pred);
/*      */         }
/* 1433 */         String description = this.grammar.grammarTreeToString(r, false);
/*      */ 
/* 1435 */         description = this.generator.target.getTargetStringLiteralFromString(description);
/* 1436 */         code.setAttribute("alts.{pred,alt,description}", predChunks, alt, description);
/*      */ 
/* 1440 */         pred = null;
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (RecognitionException ex)
/*      */     {
/* 1451 */       reportError(ex);
/* 1452 */       if (_t != null) _t = _t.getNextSibling();
/*      */     }
/* 1454 */     this._retTree = _t;
/* 1455 */     return code;
/*      */   }
/*      */ 
/*      */   public final void exceptionHandler(AST _t, StringTemplate ruleST)
/*      */     throws RecognitionException
/*      */   {
/* 1462 */     GrammarAST exceptionHandler_AST_in = _t == ASTNULL ? null : (GrammarAST)_t;
/*      */     try
/*      */     {
/* 1465 */       AST __t55 = _t;
/* 1466 */       GrammarAST tmp36_AST_in = (GrammarAST)_t;
/* 1467 */       match(_t, 66);
/* 1468 */       _t = _t.getFirstChild();
/* 1469 */       GrammarAST tmp37_AST_in = (GrammarAST)_t;
/* 1470 */       match(_t, 60);
/* 1471 */       _t = _t.getNextSibling();
/* 1472 */       GrammarAST tmp38_AST_in = (GrammarAST)_t;
/* 1473 */       match(_t, 40);
/* 1474 */       _t = _t.getNextSibling();
/* 1475 */       _t = __t55;
/* 1476 */       _t = _t.getNextSibling();
/*      */ 
/* 1478 */       List chunks = this.generator.translateAction(this.currentRuleName, tmp38_AST_in);
/* 1479 */       ruleST.setAttribute("exceptions.{decl,action}", tmp37_AST_in.getText(), chunks);
/*      */     }
/*      */     catch (RecognitionException ex)
/*      */     {
/* 1483 */       reportError(ex);
/* 1484 */       if (_t != null) _t = _t.getNextSibling();
/*      */     }
/* 1486 */     this._retTree = _t;
/*      */   }
/*      */ 
/*      */   public final void finallyClause(AST _t, StringTemplate ruleST)
/*      */     throws RecognitionException
/*      */   {
/* 1493 */     GrammarAST finallyClause_AST_in = _t == ASTNULL ? null : (GrammarAST)_t;
/*      */     try
/*      */     {
/* 1496 */       AST __t57 = _t;
/* 1497 */       GrammarAST tmp39_AST_in = (GrammarAST)_t;
/* 1498 */       match(_t, 67);
/* 1499 */       _t = _t.getFirstChild();
/* 1500 */       GrammarAST tmp40_AST_in = (GrammarAST)_t;
/* 1501 */       match(_t, 40);
/* 1502 */       _t = _t.getNextSibling();
/* 1503 */       _t = __t57;
/* 1504 */       _t = _t.getNextSibling();
/*      */ 
/* 1506 */       List chunks = this.generator.translateAction(this.currentRuleName, tmp40_AST_in);
/* 1507 */       ruleST.setAttribute("finally", chunks);
/*      */     }
/*      */     catch (RecognitionException ex)
/*      */     {
/* 1511 */       reportError(ex);
/* 1512 */       if (_t != null) _t = _t.getNextSibling();
/*      */     }
/* 1514 */     this._retTree = _t;
/*      */   }
/*      */ 
/*      */   public final StringTemplate element(AST _t, GrammarAST label, GrammarAST astSuffix)
/*      */     throws RecognitionException
/*      */   {
/* 1520 */     StringTemplate code = null;
/*      */ 
/* 1522 */     GrammarAST element_AST_in = _t == ASTNULL ? null : (GrammarAST)_t;
/* 1523 */     GrammarAST n = null;
/* 1524 */     GrammarAST alabel = null;
/* 1525 */     GrammarAST label2 = null;
/* 1526 */     GrammarAST a = null;
/* 1527 */     GrammarAST b = null;
/* 1528 */     GrammarAST sp = null;
/* 1529 */     GrammarAST gsp = null;
/*      */ 
/* 1531 */     IntSet elements = null;
/* 1532 */     GrammarAST ast = null;
/*      */     try
/*      */     {
/* 1536 */       if (_t == null) _t = ASTNULL;
/* 1537 */       switch (_t.getType())
/*      */       {
/*      */       case 71:
/* 1540 */         AST __t63 = _t;
/* 1541 */         GrammarAST tmp41_AST_in = (GrammarAST)_t;
/* 1542 */         match(_t, 71);
/* 1543 */         _t = _t.getFirstChild();
/* 1544 */         code = element(_t, label, tmp41_AST_in);
/* 1545 */         _t = this._retTree;
/* 1546 */         _t = __t63;
/* 1547 */         _t = _t.getNextSibling();
/* 1548 */         break;
/*      */       case 59:
/* 1552 */         AST __t64 = _t;
/* 1553 */         GrammarAST tmp42_AST_in = (GrammarAST)_t;
/* 1554 */         match(_t, 59);
/* 1555 */         _t = _t.getFirstChild();
/* 1556 */         code = element(_t, label, tmp42_AST_in);
/* 1557 */         _t = this._retTree;
/* 1558 */         _t = __t64;
/* 1559 */         _t = _t.getNextSibling();
/* 1560 */         break;
/*      */       case 74:
/* 1564 */         AST __t65 = _t;
/* 1565 */         n = _t == ASTNULL ? null : (GrammarAST)_t;
/* 1566 */         match(_t, 74);
/* 1567 */         _t = _t.getFirstChild();
/* 1568 */         code = notElement(_t, n, label, astSuffix);
/* 1569 */         _t = this._retTree;
/* 1570 */         _t = __t65;
/* 1571 */         _t = _t.getNextSibling();
/* 1572 */         break;
/*      */       case 49:
/* 1576 */         AST __t66 = _t;
/* 1577 */         GrammarAST tmp43_AST_in = (GrammarAST)_t;
/* 1578 */         match(_t, 49);
/* 1579 */         _t = _t.getFirstChild();
/* 1580 */         alabel = (GrammarAST)_t;
/* 1581 */         match(_t, 21);
/* 1582 */         _t = _t.getNextSibling();
/* 1583 */         code = element(_t, alabel, astSuffix);
/* 1584 */         _t = this._retTree;
/* 1585 */         _t = __t66;
/* 1586 */         _t = _t.getNextSibling();
/* 1587 */         break;
/*      */       case 68:
/* 1591 */         AST __t67 = _t;
/* 1592 */         GrammarAST tmp44_AST_in = (GrammarAST)_t;
/* 1593 */         match(_t, 68);
/* 1594 */         _t = _t.getFirstChild();
/* 1595 */         label2 = (GrammarAST)_t;
/* 1596 */         match(_t, 21);
/* 1597 */         _t = _t.getNextSibling();
/* 1598 */         code = element(_t, label2, astSuffix);
/* 1599 */         _t = this._retTree;
/* 1600 */         _t = __t67;
/* 1601 */         _t = _t.getNextSibling();
/* 1602 */         break;
/*      */       case 15:
/* 1606 */         AST __t68 = _t;
/* 1607 */         GrammarAST tmp45_AST_in = (GrammarAST)_t;
/* 1608 */         match(_t, 15);
/* 1609 */         _t = _t.getFirstChild();
/* 1610 */         a = (GrammarAST)_t;
/* 1611 */         match(_t, 51);
/* 1612 */         _t = _t.getNextSibling();
/* 1613 */         b = (GrammarAST)_t;
/* 1614 */         match(_t, 51);
/* 1615 */         _t = _t.getNextSibling();
/* 1616 */         _t = __t68;
/* 1617 */         _t = _t.getNextSibling();
/* 1618 */         code = this.templates.getInstanceOf("charRangeRef");
/* 1619 */         String low = this.generator.target.getTargetCharLiteralFromANTLRCharLiteral(this.generator, a.getText());
/*      */ 
/* 1621 */         String high = this.generator.target.getTargetCharLiteralFromANTLRCharLiteral(this.generator, b.getText());
/*      */ 
/* 1623 */         code.setAttribute("a", low);
/* 1624 */         code.setAttribute("b", high);
/* 1625 */         if (label != null)
/* 1626 */           code.setAttribute("label", label.getText()); break;
/*      */       case 75:
/* 1633 */         code = tree(_t);
/* 1634 */         _t = this._retTree;
/* 1635 */         break;
/*      */       case 30:
/*      */       case 40:
/* 1640 */         code = element_action(_t);
/* 1641 */         _t = this._retTree;
/* 1642 */         break;
/*      */       case 35:
/*      */       case 69:
/* 1648 */         if (_t == null) _t = ASTNULL;
/* 1649 */         switch (_t.getType())
/*      */         {
/*      */         case 69:
/* 1652 */           sp = (GrammarAST)_t;
/* 1653 */           match(_t, 69);
/* 1654 */           _t = _t.getNextSibling();
/* 1655 */           break;
/*      */         case 35:
/* 1659 */           gsp = (GrammarAST)_t;
/* 1660 */           match(_t, 35);
/* 1661 */           _t = _t.getNextSibling();
/* 1662 */           sp = gsp;
/* 1663 */           break;
/*      */         default:
/* 1667 */           throw new NoViableAltException(_t);
/*      */         }
/*      */ 
/* 1672 */         code = this.templates.getInstanceOf("validateSemanticPredicate");
/* 1673 */         code.setAttribute("pred", this.generator.translateAction(this.currentRuleName, sp));
/* 1674 */         String description = this.generator.target.getTargetStringLiteralFromString(sp.getText());
/*      */ 
/* 1676 */         code.setAttribute("description", description);
/*      */ 
/* 1678 */         break;
/*      */       case 36:
/* 1682 */         GrammarAST tmp46_AST_in = (GrammarAST)_t;
/* 1683 */         match(_t, 36);
/* 1684 */         _t = _t.getNextSibling();
/* 1685 */         break;
/*      */       case 37:
/* 1689 */         GrammarAST tmp47_AST_in = (GrammarAST)_t;
/* 1690 */         match(_t, 37);
/* 1691 */         _t = _t.getNextSibling();
/* 1692 */         break;
/*      */       case 16:
/* 1696 */         GrammarAST tmp48_AST_in = (GrammarAST)_t;
/* 1697 */         match(_t, 16);
/* 1698 */         _t = _t.getNextSibling();
/* 1699 */         break;
/*      */       default:
/* 1702 */         if (_t == null) _t = ASTNULL;
/* 1703 */         if ((_t.getType() >= 9) && (_t.getType() <= 12) && (element_AST_in.getSetValue() == null)) {
/* 1704 */           code = ebnf(_t);
/* 1705 */           _t = this._retTree;
/*      */         }
/* 1707 */         else if ((_t.getType() == 9) || (_t.getType() == 39) || (_t.getType() == 50) || (_t.getType() == 51) || (_t.getType() == 55) || (_t.getType() == 72) || (_t.getType() == 73)) {
/* 1708 */           code = atom(_t, null, label, astSuffix);
/* 1709 */           _t = this._retTree;
/*      */         }
/*      */         else {
/* 1712 */           throw new NoViableAltException(_t);
/*      */         }
/*      */         break;
/*      */       }
/*      */     } catch (RecognitionException ex) {
/* 1717 */       reportError(ex);
/* 1718 */       if (_t != null) _t = _t.getNextSibling();
/*      */     }
/* 1720 */     this._retTree = _t;
/* 1721 */     return code;
/*      */   }
/*      */ 
/*      */   public final StringTemplate notElement(AST _t, GrammarAST n, GrammarAST label, GrammarAST astSuffix)
/*      */     throws RecognitionException
/*      */   {
/* 1727 */     StringTemplate code = null;
/*      */ 
/* 1729 */     GrammarAST notElement_AST_in = _t == ASTNULL ? null : (GrammarAST)_t;
/* 1730 */     GrammarAST assign_c = null;
/* 1731 */     GrammarAST assign_s = null;
/* 1732 */     GrammarAST assign_t = null;
/* 1733 */     GrammarAST assign_st = null;
/*      */ 
/* 1735 */     IntSet elements = null;
/* 1736 */     String labelText = null;
/* 1737 */     if (label != null) {
/* 1738 */       labelText = label.getText();
/*      */     }
/*      */ 
/*      */     try
/*      */     {
/* 1744 */       if (_t == null) _t = ASTNULL;
/* 1745 */       switch (_t.getType())
/*      */       {
/*      */       case 51:
/* 1748 */         assign_c = (GrammarAST)_t;
/* 1749 */         match(_t, 51);
/* 1750 */         _t = _t.getNextSibling();
/*      */ 
/* 1752 */         int ttype = 0;
/* 1753 */         if (this.grammar.type == 1) {
/* 1754 */           ttype = Grammar.getCharValueFromGrammarCharLiteral(assign_c.getText());
/*      */         }
/*      */         else {
/* 1757 */           ttype = this.grammar.getTokenType(assign_c.getText());
/*      */         }
/* 1759 */         elements = this.grammar.complement(ttype);
/*      */ 
/* 1761 */         break;
/*      */       case 50:
/* 1765 */         assign_s = (GrammarAST)_t;
/* 1766 */         match(_t, 50);
/* 1767 */         _t = _t.getNextSibling();
/*      */ 
/* 1769 */         int ttype = 0;
/* 1770 */         if (this.grammar.type != 1)
/*      */         {
/* 1774 */           ttype = this.grammar.getTokenType(assign_s.getText());
/*      */         }
/* 1776 */         elements = this.grammar.complement(ttype);
/*      */ 
/* 1778 */         break;
/*      */       case 55:
/* 1782 */         assign_t = (GrammarAST)_t;
/* 1783 */         match(_t, 55);
/* 1784 */         _t = _t.getNextSibling();
/*      */ 
/* 1786 */         int ttype = this.grammar.getTokenType(assign_t.getText());
/* 1787 */         elements = this.grammar.complement(ttype);
/*      */ 
/* 1789 */         break;
/*      */       case 9:
/* 1793 */         assign_st = (GrammarAST)_t;
/* 1794 */         match(_t, 9);
/* 1795 */         _t = _t.getNextSibling();
/*      */ 
/* 1797 */         elements = assign_st.getSetValue();
/* 1798 */         elements = this.grammar.complement(elements);
/*      */ 
/* 1800 */         break;
/*      */       default:
/* 1804 */         throw new NoViableAltException(_t);
/*      */       }
/*      */ 
/* 1809 */       code = getTokenElementST("matchSet", "set", (GrammarAST)n.getFirstChild(), astSuffix, labelText);
/*      */ 
/* 1814 */       code.setAttribute("s", this.generator.genSetExpr(this.templates, elements, 1, false));
/* 1815 */       int i = ((TokenWithIndex)n.getToken()).getIndex();
/* 1816 */       code.setAttribute("elementIndex", i);
/* 1817 */       if (this.grammar.type != 1) {
/* 1818 */         this.generator.generateLocalFOLLOW(n, "set", this.currentRuleName, i);
/*      */       }
/*      */     }
/*      */     catch (RecognitionException ex)
/*      */     {
/* 1823 */       reportError(ex);
/* 1824 */       if (_t != null) _t = _t.getNextSibling();
/*      */     }
/* 1826 */     this._retTree = _t;
/* 1827 */     return code;
/*      */   }
/*      */ 
/*      */   public final StringTemplate ebnf(AST _t) throws RecognitionException {
/* 1831 */     StringTemplate code = null;
/*      */ 
/* 1833 */     GrammarAST ebnf_AST_in = _t == ASTNULL ? null : (GrammarAST)_t;
/*      */ 
/* 1835 */     DFA dfa = null;
/* 1836 */     GrammarAST b = (GrammarAST)ebnf_AST_in.getFirstChild();
/* 1837 */     GrammarAST eob = b.getLastChild();
/*      */     try
/*      */     {
/* 1842 */       if (_t == null) _t = ASTNULL;
/* 1843 */       switch (_t.getType())
/*      */       {
/*      */       case 9:
/* 1846 */         dfa = ebnf_AST_in.getLookaheadDFA();
/* 1847 */         code = block(_t, "block", dfa);
/* 1848 */         _t = this._retTree;
/* 1849 */         break;
/*      */       case 10:
/* 1853 */         dfa = ebnf_AST_in.getLookaheadDFA();
/* 1854 */         AST __t75 = _t;
/* 1855 */         GrammarAST tmp49_AST_in = (GrammarAST)_t;
/* 1856 */         match(_t, 10);
/* 1857 */         _t = _t.getFirstChild();
/* 1858 */         code = block(_t, "optionalBlock", dfa);
/* 1859 */         _t = this._retTree;
/* 1860 */         _t = __t75;
/* 1861 */         _t = _t.getNextSibling();
/* 1862 */         break;
/*      */       case 11:
/* 1866 */         dfa = eob.getLookaheadDFA();
/* 1867 */         AST __t76 = _t;
/* 1868 */         GrammarAST tmp50_AST_in = (GrammarAST)_t;
/* 1869 */         match(_t, 11);
/* 1870 */         _t = _t.getFirstChild();
/* 1871 */         code = block(_t, "closureBlock", dfa);
/* 1872 */         _t = this._retTree;
/* 1873 */         _t = __t76;
/* 1874 */         _t = _t.getNextSibling();
/* 1875 */         break;
/*      */       case 12:
/* 1879 */         dfa = eob.getLookaheadDFA();
/* 1880 */         AST __t77 = _t;
/* 1881 */         GrammarAST tmp51_AST_in = (GrammarAST)_t;
/* 1882 */         match(_t, 12);
/* 1883 */         _t = _t.getFirstChild();
/* 1884 */         code = block(_t, "positiveClosureBlock", dfa);
/* 1885 */         _t = this._retTree;
/* 1886 */         _t = __t77;
/* 1887 */         _t = _t.getNextSibling();
/* 1888 */         break;
/*      */       default:
/* 1892 */         throw new NoViableAltException(_t);
/*      */       }
/*      */ 
/* 1897 */       String description = this.grammar.grammarTreeToString(ebnf_AST_in, false);
/* 1898 */       description = this.generator.target.getTargetStringLiteralFromString(description);
/* 1899 */       code.setAttribute("description", description);
/*      */     }
/*      */     catch (RecognitionException ex)
/*      */     {
/* 1903 */       reportError(ex);
/* 1904 */       if (_t != null) _t = _t.getNextSibling();
/*      */     }
/* 1906 */     this._retTree = _t;
/* 1907 */     return code;
/*      */   }
/*      */ 
/*      */   public final StringTemplate atom(AST _t, GrammarAST scope, GrammarAST label, GrammarAST astSuffix)
/*      */     throws RecognitionException
/*      */   {
/* 1913 */     StringTemplate code = null;
/*      */ 
/* 1915 */     GrammarAST atom_AST_in = _t == ASTNULL ? null : (GrammarAST)_t;
/* 1916 */     GrammarAST r = null;
/* 1917 */     GrammarAST rarg = null;
/* 1918 */     GrammarAST t = null;
/* 1919 */     GrammarAST targ = null;
/* 1920 */     GrammarAST c = null;
/* 1921 */     GrammarAST s = null;
/* 1922 */     GrammarAST w = null;
/*      */ 
/* 1924 */     String labelText = null;
/* 1925 */     if (label != null) {
/* 1926 */       labelText = label.getText();
/*      */     }
/* 1928 */     if ((this.grammar.type != 1) && ((atom_AST_in.getType() == 73) || (atom_AST_in.getType() == 55) || (atom_AST_in.getType() == 51) || (atom_AST_in.getType() == 50)))
/*      */     {
/* 1932 */       Rule encRule = this.grammar.getRule(atom_AST_in.enclosingRuleName);
/* 1933 */       if ((encRule != null) && (encRule.hasRewrite(this.outerAltNum)) && (astSuffix != null)) {
/* 1934 */         ErrorManager.grammarError(165, this.grammar, atom_AST_in.getToken(), atom_AST_in.enclosingRuleName, new Integer(this.outerAltNum));
/*      */ 
/* 1939 */         astSuffix = null;
/*      */       }
/*      */     }
/*      */ 
/*      */     try
/*      */     {
/* 1945 */       if (_t == null) _t = ASTNULL;
/* 1946 */       switch (_t.getType())
/*      */       {
/*      */       case 73:
/* 1949 */         AST __t85 = _t;
/* 1950 */         r = _t == ASTNULL ? null : (GrammarAST)_t;
/* 1951 */         match(_t, 73);
/* 1952 */         _t = _t.getFirstChild();
/*      */ 
/* 1954 */         if (_t == null) _t = ASTNULL;
/* 1955 */         switch (_t.getType())
/*      */         {
/*      */         case 60:
/* 1958 */           rarg = (GrammarAST)_t;
/* 1959 */           match(_t, 60);
/* 1960 */           _t = _t.getNextSibling();
/* 1961 */           break;
/*      */         case 3:
/* 1965 */           break;
/*      */         default:
/* 1969 */           throw new NoViableAltException(_t);
/*      */         }
/*      */ 
/* 1973 */         _t = __t85;
/* 1974 */         _t = _t.getNextSibling();
/*      */ 
/* 1976 */         this.grammar.checkRuleReference(scope, r, rarg, this.currentRuleName);
/* 1977 */         String scopeName = null;
/* 1978 */         if (scope != null) {
/* 1979 */           scopeName = scope.getText();
/*      */         }
/* 1981 */         Rule rdef = this.grammar.getRule(scopeName, r.getText());
/*      */ 
/* 1983 */         if (!rdef.getHasReturnValue()) {
/* 1984 */           labelText = null;
/*      */         }
/* 1986 */         code = getRuleElementST("ruleRef", r.getText(), r, astSuffix, labelText);
/* 1987 */         code.setAttribute("rule", rdef);
/* 1988 */         if (scope != null) {
/* 1989 */           Grammar scopeG = this.grammar.composite.getGrammar(scope.getText());
/* 1990 */           code.setAttribute("scope", scopeG);
/*      */         }
/* 1992 */         else if (rdef.grammar != this.grammar)
/*      */         {
/* 1994 */           List rdefDelegates = rdef.grammar.getDelegates();
/* 1995 */           if (rdefDelegates.contains(this.grammar)) {
/* 1996 */             code.setAttribute("scope", rdef.grammar);
/*      */           }
/* 2002 */           else if (this.grammar != rdef.grammar.composite.delegateGrammarTreeRoot.grammar) {
/* 2003 */             code.setAttribute("scope", rdef.grammar.composite.delegateGrammarTreeRoot.grammar);
/*      */           }
/*      */ 
/*      */         }
/*      */ 
/* 2009 */         if (rarg != null) {
/* 2010 */           List args = this.generator.translateAction(this.currentRuleName, rarg);
/* 2011 */           code.setAttribute("args", args);
/*      */         }
/* 2013 */         int i = ((TokenWithIndex)r.getToken()).getIndex();
/* 2014 */         code.setAttribute("elementIndex", i);
/* 2015 */         this.generator.generateLocalFOLLOW(r, r.getText(), this.currentRuleName, i);
/* 2016 */         r.code = code;
/*      */ 
/* 2018 */         break;
/*      */       case 55:
/* 2022 */         AST __t87 = _t;
/* 2023 */         t = _t == ASTNULL ? null : (GrammarAST)_t;
/* 2024 */         match(_t, 55);
/* 2025 */         _t = _t.getFirstChild();
/*      */ 
/* 2027 */         if (_t == null) _t = ASTNULL;
/* 2028 */         switch (_t.getType())
/*      */         {
/*      */         case 60:
/* 2031 */           targ = (GrammarAST)_t;
/* 2032 */           match(_t, 60);
/* 2033 */           _t = _t.getNextSibling();
/* 2034 */           break;
/*      */         case 3:
/* 2038 */           break;
/*      */         default:
/* 2042 */           throw new NoViableAltException(_t);
/*      */         }
/*      */ 
/* 2046 */         _t = __t87;
/* 2047 */         _t = _t.getNextSibling();
/*      */ 
/* 2049 */         if ((this.currentAltHasASTRewrite) && (t.terminalOptions != null) && (t.terminalOptions.get("node") != null))
/*      */         {
/* 2051 */           ErrorManager.grammarError(155, this.grammar, t.getToken(), t.getText());
/*      */         }
/*      */ 
/* 2056 */         this.grammar.checkRuleReference(scope, t, targ, this.currentRuleName);
/* 2057 */         if (this.grammar.type == 1) {
/* 2058 */           if (this.grammar.getTokenType(t.getText()) == -1) {
/* 2059 */             code = this.templates.getInstanceOf("lexerMatchEOF");
/*      */           }
/*      */           else {
/* 2062 */             code = this.templates.getInstanceOf("lexerRuleRef");
/* 2063 */             if (isListLabel(labelText)) {
/* 2064 */               code = this.templates.getInstanceOf("lexerRuleRefAndListLabel");
/*      */             }
/* 2066 */             String scopeName = null;
/* 2067 */             if (scope != null) {
/* 2068 */               scopeName = scope.getText();
/*      */             }
/* 2070 */             Rule rdef2 = this.grammar.getRule(scopeName, t.getText());
/* 2071 */             code.setAttribute("rule", rdef2);
/* 2072 */             if (scope != null) {
/* 2073 */               Grammar scopeG = this.grammar.composite.getGrammar(scope.getText());
/* 2074 */               code.setAttribute("scope", scopeG);
/*      */             }
/* 2076 */             else if (rdef2.grammar != this.grammar)
/*      */             {
/* 2078 */               code.setAttribute("scope", rdef2.grammar);
/*      */             }
/* 2080 */             if (targ != null) {
/* 2081 */               List args = this.generator.translateAction(this.currentRuleName, targ);
/* 2082 */               code.setAttribute("args", args);
/*      */             }
/*      */           }
/* 2085 */           int i = ((TokenWithIndex)t.getToken()).getIndex();
/* 2086 */           code.setAttribute("elementIndex", i);
/* 2087 */           if (label != null) code.setAttribute("label", labelText); 
/*      */         }
/*      */         else
/*      */         {
/* 2090 */           code = getTokenElementST("tokenRef", t.getText(), t, astSuffix, labelText);
/* 2091 */           String tokenLabel = this.generator.getTokenTypeAsTargetLabel(this.grammar.getTokenType(t.getText()));
/*      */ 
/* 2093 */           code.setAttribute("token", tokenLabel);
/* 2094 */           if ((!this.currentAltHasASTRewrite) && (t.terminalOptions != null)) {
/* 2095 */             code.setAttribute("hetero", t.terminalOptions.get("node"));
/*      */           }
/* 2097 */           int i = ((TokenWithIndex)t.getToken()).getIndex();
/* 2098 */           code.setAttribute("elementIndex", i);
/* 2099 */           this.generator.generateLocalFOLLOW(t, tokenLabel, this.currentRuleName, i);
/*      */         }
/* 2101 */         t.code = code;
/*      */ 
/* 2103 */         break;
/*      */       case 51:
/* 2107 */         c = (GrammarAST)_t;
/* 2108 */         match(_t, 51);
/* 2109 */         _t = _t.getNextSibling();
/*      */ 
/* 2111 */         if (this.grammar.type == 1) {
/* 2112 */           code = this.templates.getInstanceOf("charRef");
/* 2113 */           code.setAttribute("char", this.generator.target.getTargetCharLiteralFromANTLRCharLiteral(this.generator, c.getText()));
/*      */ 
/* 2115 */           if (label != null)
/* 2116 */             code.setAttribute("label", labelText);
/*      */         }
/*      */         else
/*      */         {
/* 2120 */           code = getTokenElementST("tokenRef", "char_literal", c, astSuffix, labelText);
/* 2121 */           String tokenLabel = this.generator.getTokenTypeAsTargetLabel(this.grammar.getTokenType(c.getText()));
/* 2122 */           code.setAttribute("token", tokenLabel);
/* 2123 */           if (c.terminalOptions != null) {
/* 2124 */             code.setAttribute("hetero", c.terminalOptions.get("node"));
/*      */           }
/* 2126 */           int i = ((TokenWithIndex)c.getToken()).getIndex();
/* 2127 */           code.setAttribute("elementIndex", i);
/* 2128 */           this.generator.generateLocalFOLLOW(c, tokenLabel, this.currentRuleName, i);
/*      */         }
/*      */ 
/* 2131 */         break;
/*      */       case 50:
/* 2135 */         s = (GrammarAST)_t;
/* 2136 */         match(_t, 50);
/* 2137 */         _t = _t.getNextSibling();
/*      */ 
/* 2139 */         int i = ((TokenWithIndex)s.getToken()).getIndex();
/* 2140 */         if (this.grammar.type == 1) {
/* 2141 */           code = this.templates.getInstanceOf("lexerStringRef");
/* 2142 */           code.setAttribute("string", this.generator.target.getTargetStringLiteralFromANTLRStringLiteral(this.generator, s.getText()));
/*      */ 
/* 2144 */           code.setAttribute("elementIndex", i);
/* 2145 */           if (label != null)
/* 2146 */             code.setAttribute("label", labelText);
/*      */         }
/*      */         else
/*      */         {
/* 2150 */           code = getTokenElementST("tokenRef", "string_literal", s, astSuffix, labelText);
/* 2151 */           String tokenLabel = this.generator.getTokenTypeAsTargetLabel(this.grammar.getTokenType(s.getText()));
/*      */ 
/* 2153 */           code.setAttribute("token", tokenLabel);
/* 2154 */           if (s.terminalOptions != null) {
/* 2155 */             code.setAttribute("hetero", s.terminalOptions.get("node"));
/*      */           }
/* 2157 */           code.setAttribute("elementIndex", i);
/* 2158 */           this.generator.generateLocalFOLLOW(s, tokenLabel, this.currentRuleName, i);
/*      */         }
/*      */ 
/* 2161 */         break;
/*      */       case 72:
/* 2165 */         w = (GrammarAST)_t;
/* 2166 */         match(_t, 72);
/* 2167 */         _t = _t.getNextSibling();
/*      */ 
/* 2169 */         code = getWildcardST(w, astSuffix, labelText);
/* 2170 */         code.setAttribute("elementIndex", ((TokenWithIndex)w.getToken()).getIndex());
/*      */ 
/* 2172 */         break;
/*      */       case 39:
/* 2176 */         AST __t89 = _t;
/* 2177 */         GrammarAST tmp52_AST_in = (GrammarAST)_t;
/* 2178 */         match(_t, 39);
/* 2179 */         _t = _t.getFirstChild();
/* 2180 */         GrammarAST tmp53_AST_in = (GrammarAST)_t;
/* 2181 */         match(_t, 21);
/* 2182 */         _t = _t.getNextSibling();
/* 2183 */         code = atom(_t, tmp53_AST_in, label, astSuffix);
/* 2184 */         _t = this._retTree;
/* 2185 */         _t = __t89;
/* 2186 */         _t = _t.getNextSibling();
/* 2187 */         break;
/*      */       case 9:
/* 2191 */         code = set(_t, label, astSuffix);
/* 2192 */         _t = this._retTree;
/* 2193 */         break;
/*      */       default:
/* 2197 */         throw new NoViableAltException(_t);
/*      */       }
/*      */     }
/*      */     catch (RecognitionException ex)
/*      */     {
/* 2202 */       reportError(ex);
/* 2203 */       if (_t != null) _t = _t.getNextSibling();
/*      */     }
/* 2205 */     this._retTree = _t;
/* 2206 */     return code;
/*      */   }
/*      */ 
/*      */   public final StringTemplate tree(AST _t) throws RecognitionException {
/* 2210 */     StringTemplate code = this.templates.getInstanceOf("tree");
/*      */ 
/* 2212 */     GrammarAST tree_AST_in = _t == ASTNULL ? null : (GrammarAST)_t;
/*      */ 
/* 2214 */     StringTemplate el = null; StringTemplate act = null;
/* 2215 */     GrammarAST elAST = null; GrammarAST actAST = null;
/* 2216 */     NFAState afterDOWN = (NFAState)tree_AST_in.NFATreeDownState.transition(0).target;
/* 2217 */     LookaheadSet s = this.grammar.LOOK(afterDOWN);
/* 2218 */     if (s.member(3))
/*      */     {
/* 2222 */       code.setAttribute("nullableChildList", "true");
/*      */     }
/* 2224 */     this.rewriteTreeNestingLevel += 1;
/* 2225 */     code.setAttribute("enclosingTreeLevel", this.rewriteTreeNestingLevel - 1);
/* 2226 */     code.setAttribute("treeLevel", this.rewriteTreeNestingLevel);
/* 2227 */     Rule r = this.grammar.getRule(this.currentRuleName);
/* 2228 */     GrammarAST rootSuffix = null;
/* 2229 */     if ((this.grammar.buildAST()) && (!r.hasRewrite(this.outerAltNum))) {
/* 2230 */       rootSuffix = new GrammarAST(71, "ROOT");
/*      */     }
/*      */ 
/*      */     try
/*      */     {
/* 2235 */       AST __t79 = _t;
/* 2236 */       GrammarAST tmp54_AST_in = (GrammarAST)_t;
/* 2237 */       match(_t, 75);
/* 2238 */       _t = _t.getFirstChild();
/* 2239 */       elAST = (GrammarAST)_t;
/* 2240 */       el = element(_t, null, rootSuffix);
/* 2241 */       _t = this._retTree;
/*      */ 
/* 2243 */       code.setAttribute("root.{el,line,pos}", el, Utils.integer(elAST.getLine()), Utils.integer(elAST.getColumn()));
/*      */       while (true)
/*      */       {
/* 2252 */         if (_t == null) _t = ASTNULL;
/* 2253 */         if ((_t.getType() != 30) && (_t.getType() != 40)) break;
/* 2254 */         actAST = (GrammarAST)_t;
/* 2255 */         act = element_action(_t);
/* 2256 */         _t = this._retTree;
/*      */ 
/* 2258 */         code.setAttribute("actionsAfterRoot.{el,line,pos}", act, Utils.integer(actAST.getLine()), Utils.integer(actAST.getColumn()));
/*      */       }
/*      */ 
/*      */       while (true)
/*      */       {
/* 2274 */         if (_t == null) _t = ASTNULL;
/* 2275 */         if ((_t.getType() != 9) && (_t.getType() != 10) && (_t.getType() != 11) && (_t.getType() != 12) && (_t.getType() != 15) && (_t.getType() != 16) && (_t.getType() != 30) && (_t.getType() != 35) && (_t.getType() != 36) && (_t.getType() != 37) && (_t.getType() != 39) && (_t.getType() != 40) && (_t.getType() != 49) && (_t.getType() != 50) && (_t.getType() != 51) && (_t.getType() != 55) && (_t.getType() != 59) && (_t.getType() != 68) && (_t.getType() != 69) && (_t.getType() != 71) && (_t.getType() != 72) && (_t.getType() != 73) && (_t.getType() != 74) && (_t.getType() != 75)) break;
/* 2276 */         elAST = (GrammarAST)_t;
/* 2277 */         el = element(_t, null, null);
/* 2278 */         _t = this._retTree;
/*      */ 
/* 2280 */         code.setAttribute("children.{el,line,pos}", el, Utils.integer(elAST.getLine()), Utils.integer(elAST.getColumn()));
/*      */       }
/*      */ 
/* 2293 */       _t = __t79;
/* 2294 */       _t = _t.getNextSibling();
/* 2295 */       this.rewriteTreeNestingLevel -= 1;
/*      */     }
/*      */     catch (RecognitionException ex) {
/* 2298 */       reportError(ex);
/* 2299 */       if (_t != null) _t = _t.getNextSibling();
/*      */     }
/* 2301 */     this._retTree = _t;
/* 2302 */     return code;
/*      */   }
/*      */ 
/*      */   public final StringTemplate element_action(AST _t) throws RecognitionException {
/* 2306 */     StringTemplate code = null;
/*      */ 
/* 2308 */     GrammarAST element_action_AST_in = _t == ASTNULL ? null : (GrammarAST)_t;
/* 2309 */     GrammarAST act = null;
/* 2310 */     GrammarAST act2 = null;
/*      */     try
/*      */     {
/* 2313 */       if (_t == null) _t = ASTNULL;
/* 2314 */       switch (_t.getType())
/*      */       {
/*      */       case 40:
/* 2317 */         act = (GrammarAST)_t;
/* 2318 */         match(_t, 40);
/* 2319 */         _t = _t.getNextSibling();
/*      */ 
/* 2321 */         code = this.templates.getInstanceOf("execAction");
/* 2322 */         code.setAttribute("action", this.generator.translateAction(this.currentRuleName, act));
/*      */ 
/* 2324 */         break;
/*      */       case 30:
/* 2328 */         act2 = (GrammarAST)_t;
/* 2329 */         match(_t, 30);
/* 2330 */         _t = _t.getNextSibling();
/*      */ 
/* 2332 */         code = this.templates.getInstanceOf("execForcedAction");
/* 2333 */         code.setAttribute("action", this.generator.translateAction(this.currentRuleName, act2));
/*      */ 
/* 2335 */         break;
/*      */       default:
/* 2339 */         throw new NoViableAltException(_t);
/*      */       }
/*      */     }
/*      */     catch (RecognitionException ex)
/*      */     {
/* 2344 */       reportError(ex);
/* 2345 */       if (_t != null) _t = _t.getNextSibling();
/*      */     }
/* 2347 */     this._retTree = _t;
/* 2348 */     return code;
/*      */   }
/*      */ 
/*      */   public final StringTemplate set(AST _t, GrammarAST label, GrammarAST astSuffix)
/*      */     throws RecognitionException
/*      */   {
/* 2354 */     StringTemplate code = null;
/*      */ 
/* 2356 */     GrammarAST set_AST_in = _t == ASTNULL ? null : (GrammarAST)_t;
/* 2357 */     GrammarAST s = null;
/*      */ 
/* 2359 */     String labelText = null;
/* 2360 */     if (label != null) {
/* 2361 */       labelText = label.getText();
/*      */     }
/*      */ 
/*      */     try
/*      */     {
/* 2366 */       s = (GrammarAST)_t;
/* 2367 */       match(_t, 9);
/* 2368 */       _t = _t.getNextSibling();
/*      */ 
/* 2370 */       code = getTokenElementST("matchSet", "set", s, astSuffix, labelText);
/* 2371 */       int i = ((TokenWithIndex)s.getToken()).getIndex();
/* 2372 */       code.setAttribute("elementIndex", i);
/* 2373 */       if (this.grammar.type != 1) {
/* 2374 */         this.generator.generateLocalFOLLOW(s, "set", this.currentRuleName, i);
/*      */       }
/* 2376 */       code.setAttribute("s", this.generator.genSetExpr(this.templates, s.getSetValue(), 1, false));
/*      */     }
/*      */     catch (RecognitionException ex)
/*      */     {
/* 2380 */       reportError(ex);
/* 2381 */       if (_t != null) _t = _t.getNextSibling();
/*      */     }
/* 2383 */     this._retTree = _t;
/* 2384 */     return code;
/*      */   }
/*      */ 
/*      */   public final void ast_suffix(AST _t) throws RecognitionException
/*      */   {
/* 2389 */     GrammarAST ast_suffix_AST_in = _t == ASTNULL ? null : (GrammarAST)_t;
/*      */     try
/*      */     {
/* 2392 */       if (_t == null) _t = ASTNULL;
/* 2393 */       switch (_t.getType())
/*      */       {
/*      */       case 71:
/* 2396 */         GrammarAST tmp55_AST_in = (GrammarAST)_t;
/* 2397 */         match(_t, 71);
/* 2398 */         _t = _t.getNextSibling();
/* 2399 */         break;
/*      */       case 59:
/* 2403 */         GrammarAST tmp56_AST_in = (GrammarAST)_t;
/* 2404 */         match(_t, 59);
/* 2405 */         _t = _t.getNextSibling();
/* 2406 */         break;
/*      */       default:
/* 2410 */         throw new NoViableAltException(_t);
/*      */       }
/*      */     }
/*      */     catch (RecognitionException ex)
/*      */     {
/* 2415 */       reportError(ex);
/* 2416 */       if (_t != null) _t = _t.getNextSibling();
/*      */     }
/* 2418 */     this._retTree = _t;
/*      */   }
/*      */ 
/*      */   public final void setElement(AST _t) throws RecognitionException
/*      */   {
/* 2423 */     GrammarAST setElement_AST_in = _t == ASTNULL ? null : (GrammarAST)_t;
/* 2424 */     GrammarAST c = null;
/* 2425 */     GrammarAST t = null;
/* 2426 */     GrammarAST s = null;
/* 2427 */     GrammarAST c1 = null;
/* 2428 */     GrammarAST c2 = null;
/*      */     try
/*      */     {
/* 2431 */       if (_t == null) _t = ASTNULL;
/* 2432 */       switch (_t.getType())
/*      */       {
/*      */       case 51:
/* 2435 */         c = (GrammarAST)_t;
/* 2436 */         match(_t, 51);
/* 2437 */         _t = _t.getNextSibling();
/* 2438 */         break;
/*      */       case 55:
/* 2442 */         t = (GrammarAST)_t;
/* 2443 */         match(_t, 55);
/* 2444 */         _t = _t.getNextSibling();
/* 2445 */         break;
/*      */       case 50:
/* 2449 */         s = (GrammarAST)_t;
/* 2450 */         match(_t, 50);
/* 2451 */         _t = _t.getNextSibling();
/* 2452 */         break;
/*      */       case 15:
/* 2456 */         AST __t93 = _t;
/* 2457 */         GrammarAST tmp57_AST_in = (GrammarAST)_t;
/* 2458 */         match(_t, 15);
/* 2459 */         _t = _t.getFirstChild();
/* 2460 */         c1 = (GrammarAST)_t;
/* 2461 */         match(_t, 51);
/* 2462 */         _t = _t.getNextSibling();
/* 2463 */         c2 = (GrammarAST)_t;
/* 2464 */         match(_t, 51);
/* 2465 */         _t = _t.getNextSibling();
/* 2466 */         _t = __t93;
/* 2467 */         _t = _t.getNextSibling();
/* 2468 */         break;
/*      */       default:
/* 2472 */         throw new NoViableAltException(_t);
/*      */       }
/*      */     }
/*      */     catch (RecognitionException ex)
/*      */     {
/* 2477 */       reportError(ex);
/* 2478 */       if (_t != null) _t = _t.getNextSibling();
/*      */     }
/* 2480 */     this._retTree = _t;
/*      */   }
/*      */ 
/*      */   public final StringTemplate rewrite_alternative(AST _t) throws RecognitionException {
/* 2484 */     StringTemplate code = null;
/*      */ 
/* 2486 */     GrammarAST rewrite_alternative_AST_in = _t == ASTNULL ? null : (GrammarAST)_t;
/* 2487 */     GrammarAST a = null;
/*      */     try
/*      */     {
/* 2493 */       if (_t == null) _t = ASTNULL;
/* 2494 */       if ((_t.getType() == 17) && (this.generator.grammar.buildAST())) {
/* 2495 */         AST __t102 = _t;
/* 2496 */         a = _t == ASTNULL ? null : (GrammarAST)_t;
/* 2497 */         match(_t, 17);
/* 2498 */         _t = _t.getFirstChild();
/* 2499 */         code = this.templates.getInstanceOf("rewriteElementList");
/*      */ 
/* 2501 */         if (_t == null) _t = ASTNULL;
/* 2502 */         switch (_t.getType())
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
/* 2515 */           int _cnt105 = 0;
/*      */           while (true)
/*      */           {
/* 2518 */             if (_t == null) _t = ASTNULL;
/* 2519 */             if ((_t.getType() == 10) || (_t.getType() == 11) || (_t.getType() == 12) || (_t.getType() == 31) || (_t.getType() == 40) || (_t.getType() == 50) || (_t.getType() == 51) || (_t.getType() == 55) || (_t.getType() == 73) || (_t.getType() == 75)) {
/* 2520 */               GrammarAST elAST = (GrammarAST)_t;
/* 2521 */               StringTemplate el = rewrite_element(_t);
/* 2522 */               _t = this._retTree;
/* 2523 */               code.setAttribute("elements.{el,line,pos}", el, Utils.integer(elAST.getLine()), Utils.integer(elAST.getColumn()));
/*      */             }
/*      */             else
/*      */             {
/* 2531 */               if (_cnt105 >= 1) break; throw new NoViableAltException(_t);
/*      */             }
/*      */             StringTemplate el;
/* 2534 */             _cnt105++;
/*      */           }
/*      */ 
/* 2537 */           break;
/*      */         case 16:
/* 2541 */           GrammarAST tmp58_AST_in = (GrammarAST)_t;
/* 2542 */           match(_t, 16);
/* 2543 */           _t = _t.getNextSibling();
/* 2544 */           code.setAttribute("elements.{el,line,pos}", this.templates.getInstanceOf("rewriteEmptyAlt"), Utils.integer(a.getLine()), Utils.integer(a.getColumn()));
/*      */ 
/* 2550 */           break;
/*      */         default:
/* 2554 */           throw new NoViableAltException(_t);
/*      */         }
/*      */ 
/* 2558 */         GrammarAST tmp59_AST_in = (GrammarAST)_t;
/* 2559 */         match(_t, 20);
/* 2560 */         _t = _t.getNextSibling();
/* 2561 */         _t = __t102;
/* 2562 */         _t = _t.getNextSibling();
/*      */       }
/* 2564 */       else if (((_t.getType() == 17) || (_t.getType() == 32) || (_t.getType() == 40)) && (this.generator.grammar.buildTemplate())) {
/* 2565 */         code = rewrite_template(_t);
/* 2566 */         _t = this._retTree;
/*      */       }
/* 2568 */       else if (_t.getType() == 81) {
/* 2569 */         GrammarAST tmp60_AST_in = (GrammarAST)_t;
/* 2570 */         match(_t, 81);
/* 2571 */         _t = _t.getNextSibling();
/*      */       }
/*      */       else {
/* 2574 */         throw new NoViableAltException(_t);
/*      */       }
/*      */     }
/*      */     catch (RecognitionException ex)
/*      */     {
/* 2579 */       reportError(ex);
/* 2580 */       if (_t != null) _t = _t.getNextSibling();
/*      */     }
/* 2582 */     this._retTree = _t;
/* 2583 */     return code;
/*      */   }
/*      */ 
/*      */   public final StringTemplate rewrite_block(AST _t, String blockTemplateName)
/*      */     throws RecognitionException
/*      */   {
/* 2589 */     StringTemplate code = null;
/*      */ 
/* 2591 */     GrammarAST rewrite_block_AST_in = _t == ASTNULL ? null : (GrammarAST)_t;
/*      */ 
/* 2593 */     this.rewriteBlockNestingLevel += 1;
/* 2594 */     code = this.templates.getInstanceOf(blockTemplateName);
/* 2595 */     StringTemplate save_currentBlockST = this.currentBlockST;
/* 2596 */     this.currentBlockST = code;
/* 2597 */     code.setAttribute("rewriteBlockLevel", this.rewriteBlockNestingLevel);
/* 2598 */     StringTemplate alt = null;
/*      */     try
/*      */     {
/* 2602 */       AST __t100 = _t;
/* 2603 */       GrammarAST tmp61_AST_in = (GrammarAST)_t;
/* 2604 */       match(_t, 9);
/* 2605 */       _t = _t.getFirstChild();
/*      */ 
/* 2607 */       this.currentBlockST.setAttribute("referencedElementsDeep", getTokenTypesAsTargetLabels(tmp61_AST_in.rewriteRefsDeep));
/*      */ 
/* 2609 */       this.currentBlockST.setAttribute("referencedElements", getTokenTypesAsTargetLabels(tmp61_AST_in.rewriteRefsShallow));
/*      */ 
/* 2612 */       alt = rewrite_alternative(_t);
/* 2613 */       _t = this._retTree;
/* 2614 */       GrammarAST tmp62_AST_in = (GrammarAST)_t;
/* 2615 */       match(_t, 19);
/* 2616 */       _t = _t.getNextSibling();
/* 2617 */       _t = __t100;
/* 2618 */       _t = _t.getNextSibling();
/*      */ 
/* 2620 */       code.setAttribute("alt", alt);
/* 2621 */       this.rewriteBlockNestingLevel -= 1;
/* 2622 */       this.currentBlockST = save_currentBlockST;
/*      */     }
/*      */     catch (RecognitionException ex)
/*      */     {
/* 2626 */       reportError(ex);
/* 2627 */       if (_t != null) _t = _t.getNextSibling();
/*      */     }
/* 2629 */     this._retTree = _t;
/* 2630 */     return code;
/*      */   }
/*      */ 
/*      */   public final StringTemplate rewrite_element(AST _t) throws RecognitionException {
/* 2634 */     StringTemplate code = null;
/*      */ 
/* 2636 */     GrammarAST rewrite_element_AST_in = _t == ASTNULL ? null : (GrammarAST)_t;
/*      */ 
/* 2638 */     IntSet elements = null;
/* 2639 */     GrammarAST ast = null;
/*      */     try
/*      */     {
/* 2643 */       if (_t == null) _t = ASTNULL;
/* 2644 */       switch (_t.getType())
/*      */       {
/*      */       case 31:
/*      */       case 40:
/*      */       case 50:
/*      */       case 51:
/*      */       case 55:
/*      */       case 73:
/* 2652 */         code = rewrite_atom(_t, false);
/* 2653 */         _t = this._retTree;
/* 2654 */         break;
/*      */       case 10:
/*      */       case 11:
/*      */       case 12:
/* 2660 */         code = rewrite_ebnf(_t);
/* 2661 */         _t = this._retTree;
/* 2662 */         break;
/*      */       case 75:
/* 2666 */         code = rewrite_tree(_t);
/* 2667 */         _t = this._retTree;
/* 2668 */         break;
/*      */       default:
/* 2672 */         throw new NoViableAltException(_t);
/*      */       }
/*      */     }
/*      */     catch (RecognitionException ex)
/*      */     {
/* 2677 */       reportError(ex);
/* 2678 */       if (_t != null) _t = _t.getNextSibling();
/*      */     }
/* 2680 */     this._retTree = _t;
/* 2681 */     return code;
/*      */   }
/*      */ 
/*      */   public final StringTemplate rewrite_template(AST _t) throws RecognitionException {
/* 2685 */     StringTemplate code = null;
/*      */ 
/* 2687 */     GrammarAST rewrite_template_AST_in = _t == ASTNULL ? null : (GrammarAST)_t;
/* 2688 */     GrammarAST id = null;
/* 2689 */     GrammarAST ind = null;
/* 2690 */     GrammarAST arg = null;
/* 2691 */     GrammarAST a = null;
/* 2692 */     GrammarAST act = null;
/*      */     try
/*      */     {
/* 2695 */       if (_t == null) _t = ASTNULL;
/* 2696 */       switch (_t.getType())
/*      */       {
/*      */       case 17:
/* 2699 */         AST __t120 = _t;
/* 2700 */         GrammarAST tmp63_AST_in = (GrammarAST)_t;
/* 2701 */         match(_t, 17);
/* 2702 */         _t = _t.getFirstChild();
/* 2703 */         GrammarAST tmp64_AST_in = (GrammarAST)_t;
/* 2704 */         match(_t, 16);
/* 2705 */         _t = _t.getNextSibling();
/* 2706 */         GrammarAST tmp65_AST_in = (GrammarAST)_t;
/* 2707 */         match(_t, 20);
/* 2708 */         _t = _t.getNextSibling();
/* 2709 */         _t = __t120;
/* 2710 */         _t = _t.getNextSibling();
/* 2711 */         code = this.templates.getInstanceOf("rewriteEmptyTemplate");
/* 2712 */         break;
/*      */       case 32:
/* 2716 */         AST __t121 = _t;
/* 2717 */         GrammarAST tmp66_AST_in = (GrammarAST)_t;
/* 2718 */         match(_t, 32);
/* 2719 */         _t = _t.getFirstChild();
/*      */ 
/* 2721 */         if (_t == null) _t = ASTNULL;
/* 2722 */         switch (_t.getType())
/*      */         {
/*      */         case 21:
/* 2725 */           id = (GrammarAST)_t;
/* 2726 */           match(_t, 21);
/* 2727 */           _t = _t.getNextSibling();
/* 2728 */           break;
/*      */         case 40:
/* 2732 */           ind = (GrammarAST)_t;
/* 2733 */           match(_t, 40);
/* 2734 */           _t = _t.getNextSibling();
/* 2735 */           break;
/*      */         default:
/* 2739 */           throw new NoViableAltException(_t);
/*      */         }
/*      */ 
/* 2744 */         if ((id != null) && (id.getText().equals("template"))) {
/* 2745 */           code = this.templates.getInstanceOf("rewriteInlineTemplate");
/*      */         }
/* 2747 */         else if (id != null) {
/* 2748 */           code = this.templates.getInstanceOf("rewriteExternalTemplate");
/* 2749 */           code.setAttribute("name", id.getText());
/*      */         }
/* 2751 */         else if (ind != null) {
/* 2752 */           code = this.templates.getInstanceOf("rewriteIndirectTemplate");
/* 2753 */           List chunks = this.generator.translateAction(this.currentRuleName, ind);
/* 2754 */           code.setAttribute("expr", chunks);
/*      */         }
/*      */ 
/* 2757 */         AST __t123 = _t;
/* 2758 */         GrammarAST tmp67_AST_in = (GrammarAST)_t;
/* 2759 */         match(_t, 23);
/* 2760 */         _t = _t.getFirstChild();
/*      */         while (true)
/*      */         {
/* 2764 */           if (_t == null) _t = ASTNULL;
/* 2765 */           if (_t.getType() != 22) break;
/* 2766 */           AST __t125 = _t;
/* 2767 */           GrammarAST tmp68_AST_in = (GrammarAST)_t;
/* 2768 */           match(_t, 22);
/* 2769 */           _t = _t.getFirstChild();
/* 2770 */           arg = (GrammarAST)_t;
/* 2771 */           match(_t, 21);
/* 2772 */           _t = _t.getNextSibling();
/* 2773 */           a = (GrammarAST)_t;
/* 2774 */           match(_t, 40);
/* 2775 */           _t = _t.getNextSibling();
/*      */ 
/* 2780 */           a.outerAltNum = this.outerAltNum;
/* 2781 */           List chunks = this.generator.translateAction(this.currentRuleName, a);
/* 2782 */           code.setAttribute("args.{name,value}", arg.getText(), chunks);
/*      */ 
/* 2784 */           _t = __t125;
/* 2785 */           _t = _t.getNextSibling();
/*      */         }
/*      */ 
/* 2793 */         _t = __t123;
/* 2794 */         _t = _t.getNextSibling();
/*      */ 
/* 2796 */         if (_t == null) _t = ASTNULL;
/* 2797 */         switch (_t.getType())
/*      */         {
/*      */         case 83:
/* 2800 */           GrammarAST tmp69_AST_in = (GrammarAST)_t;
/* 2801 */           match(_t, 83);
/* 2802 */           _t = _t.getNextSibling();
/*      */ 
/* 2804 */           String sl = tmp69_AST_in.getText();
/* 2805 */           String t = sl.substring(1, sl.length() - 1);
/* 2806 */           t = this.generator.target.getTargetStringLiteralFromString(t);
/* 2807 */           code.setAttribute("template", t);
/*      */ 
/* 2809 */           break;
/*      */         case 84:
/* 2813 */           GrammarAST tmp70_AST_in = (GrammarAST)_t;
/* 2814 */           match(_t, 84);
/* 2815 */           _t = _t.getNextSibling();
/*      */ 
/* 2817 */           String sl = tmp70_AST_in.getText();
/* 2818 */           String t = sl.substring(2, sl.length() - 2);
/* 2819 */           t = this.generator.target.getTargetStringLiteralFromString(t);
/* 2820 */           code.setAttribute("template", t);
/*      */ 
/* 2822 */           break;
/*      */         case 3:
/* 2826 */           break;
/*      */         default:
/* 2830 */           throw new NoViableAltException(_t);
/*      */         }
/*      */ 
/* 2834 */         _t = __t121;
/* 2835 */         _t = _t.getNextSibling();
/* 2836 */         break;
/*      */       case 40:
/* 2840 */         act = (GrammarAST)_t;
/* 2841 */         match(_t, 40);
/* 2842 */         _t = _t.getNextSibling();
/*      */ 
/* 2845 */         act.outerAltNum = this.outerAltNum;
/* 2846 */         code = this.templates.getInstanceOf("rewriteAction");
/* 2847 */         code.setAttribute("action", this.generator.translateAction(this.currentRuleName, act));
/*      */ 
/* 2850 */         break;
/*      */       default:
/* 2854 */         throw new NoViableAltException(_t);
/*      */       }
/*      */     }
/*      */     catch (RecognitionException ex)
/*      */     {
/* 2859 */       reportError(ex);
/* 2860 */       if (_t != null) _t = _t.getNextSibling();
/*      */     }
/* 2862 */     this._retTree = _t;
/* 2863 */     return code;
/*      */   }
/*      */ 
/*      */   public final StringTemplate rewrite_atom(AST _t, boolean isRoot)
/*      */     throws RecognitionException
/*      */   {
/* 2869 */     StringTemplate code = null;
/*      */ 
/* 2871 */     GrammarAST rewrite_atom_AST_in = _t == ASTNULL ? null : (GrammarAST)_t;
/* 2872 */     GrammarAST r = null;
/* 2873 */     GrammarAST tk = null;
/* 2874 */     GrammarAST arg = null;
/* 2875 */     GrammarAST cl = null;
/* 2876 */     GrammarAST sl = null;
/*      */     try
/*      */     {
/* 2879 */       if (_t == null) _t = ASTNULL;
/* 2880 */       switch (_t.getType())
/*      */       {
/*      */       case 73:
/* 2883 */         r = (GrammarAST)_t;
/* 2884 */         match(_t, 73);
/* 2885 */         _t = _t.getNextSibling();
/*      */ 
/* 2887 */         String ruleRefName = r.getText();
/* 2888 */         String stName = "rewriteRuleRef";
/* 2889 */         if (isRoot) {
/* 2890 */           stName = stName + "Root";
/*      */         }
/* 2892 */         code = this.templates.getInstanceOf(stName);
/* 2893 */         code.setAttribute("rule", ruleRefName);
/* 2894 */         if (this.grammar.getRule(ruleRefName) == null) {
/* 2895 */           ErrorManager.grammarError(106, this.grammar, r.getToken(), ruleRefName);
/*      */ 
/* 2899 */           code = new StringTemplate();
/*      */         }
/* 2901 */         else if (this.grammar.getRule(this.currentRuleName).getRuleRefsInAlt(ruleRefName, this.outerAltNum) == null)
/*      */         {
/* 2904 */           ErrorManager.grammarError(136, this.grammar, r.getToken(), ruleRefName);
/*      */ 
/* 2908 */           code = new StringTemplate();
/*      */         }
/* 2912 */         else if (!this.rewriteRuleRefs.contains(ruleRefName)) {
/* 2913 */           this.rewriteRuleRefs.add(ruleRefName); } break;
/*      */       case 50:
/*      */       case 51:
/*      */       case 55:
/* 2923 */         GrammarAST term = (GrammarAST)_t;
/*      */ 
/* 2925 */         if (_t == null) _t = ASTNULL;
/* 2926 */         switch (_t.getType())
/*      */         {
/*      */         case 55:
/* 2929 */           AST __t117 = _t;
/* 2930 */           tk = _t == ASTNULL ? null : (GrammarAST)_t;
/* 2931 */           match(_t, 55);
/* 2932 */           _t = _t.getFirstChild();
/*      */ 
/* 2934 */           if (_t == null) _t = ASTNULL;
/* 2935 */           switch (_t.getType())
/*      */           {
/*      */           case 60:
/* 2938 */             arg = (GrammarAST)_t;
/* 2939 */             match(_t, 60);
/* 2940 */             _t = _t.getNextSibling();
/* 2941 */             break;
/*      */           case 3:
/* 2945 */             break;
/*      */           default:
/* 2949 */             throw new NoViableAltException(_t);
/*      */           }
/*      */ 
/* 2953 */           _t = __t117;
/* 2954 */           _t = _t.getNextSibling();
/* 2955 */           break;
/*      */         case 51:
/* 2959 */           cl = (GrammarAST)_t;
/* 2960 */           match(_t, 51);
/* 2961 */           _t = _t.getNextSibling();
/* 2962 */           break;
/*      */         case 50:
/* 2966 */           sl = (GrammarAST)_t;
/* 2967 */           match(_t, 50);
/* 2968 */           _t = _t.getNextSibling();
/* 2969 */           break;
/*      */         default:
/* 2973 */           throw new NoViableAltException(_t);
/*      */         }
/*      */ 
/* 2978 */         String tokenName = rewrite_atom_AST_in.getText();
/* 2979 */         String stName = "rewriteTokenRef";
/* 2980 */         Rule rule = this.grammar.getRule(this.currentRuleName);
/* 2981 */         Set tokenRefsInAlt = rule.getTokenRefsInAlt(this.outerAltNum);
/* 2982 */         boolean createNewNode = (!tokenRefsInAlt.contains(tokenName)) || (arg != null);
/* 2983 */         Object hetero = null;
/* 2984 */         if (term.terminalOptions != null) {
/* 2985 */           hetero = term.terminalOptions.get("node");
/*      */         }
/* 2987 */         if (createNewNode) {
/* 2988 */           stName = "rewriteImaginaryTokenRef";
/*      */         }
/* 2990 */         if (isRoot) {
/* 2991 */           stName = stName + "Root";
/*      */         }
/* 2993 */         code = this.templates.getInstanceOf(stName);
/* 2994 */         code.setAttribute("hetero", hetero);
/* 2995 */         if (arg != null) {
/* 2996 */           List args = this.generator.translateAction(this.currentRuleName, arg);
/* 2997 */           code.setAttribute("args", args);
/*      */         }
/* 2999 */         code.setAttribute("elementIndex", ((TokenWithIndex)rewrite_atom_AST_in.getToken()).getIndex());
/* 3000 */         int ttype = this.grammar.getTokenType(tokenName);
/* 3001 */         String tok = this.generator.getTokenTypeAsTargetLabel(ttype);
/* 3002 */         code.setAttribute("token", tok);
/* 3003 */         if (this.grammar.getTokenType(tokenName) == -7) {
/* 3004 */           ErrorManager.grammarError(135, this.grammar, rewrite_atom_AST_in.getToken(), tokenName);
/*      */ 
/* 3008 */           code = new StringTemplate(); } break;
/*      */       case 31:
/* 3015 */         GrammarAST tmp71_AST_in = (GrammarAST)_t;
/* 3016 */         match(_t, 31);
/* 3017 */         _t = _t.getNextSibling();
/*      */ 
/* 3019 */         String labelName = tmp71_AST_in.getText();
/* 3020 */         Rule rule = this.grammar.getRule(this.currentRuleName);
/* 3021 */         Grammar.LabelElementPair pair = rule.getLabel(labelName);
/* 3022 */         if (labelName.equals(this.currentRuleName))
/*      */         {
/* 3024 */           if ((rule.hasRewrite(this.outerAltNum)) && (rule.getRuleRefsInAlt(this.outerAltNum).contains(labelName)))
/*      */           {
/* 3027 */             ErrorManager.grammarError(132, this.grammar, tmp71_AST_in.getToken(), labelName);
/*      */           }
/*      */ 
/* 3032 */           StringTemplate labelST = this.templates.getInstanceOf("prevRuleRootRef");
/* 3033 */           code = this.templates.getInstanceOf("rewriteRuleLabelRef" + (isRoot ? "Root" : ""));
/* 3034 */           code.setAttribute("label", labelST);
/*      */         }
/* 3036 */         else if (pair == null) {
/* 3037 */           ErrorManager.grammarError(137, this.grammar, tmp71_AST_in.getToken(), labelName);
/*      */ 
/* 3041 */           code = new StringTemplate();
/*      */         }
/*      */         else {
/* 3044 */           String stName = null;
/* 3045 */           switch (pair.type) {
/*      */           case 2:
/* 3047 */             stName = "rewriteTokenLabelRef";
/* 3048 */             break;
/*      */           case 6:
/* 3050 */             stName = "rewriteWildcardLabelRef";
/* 3051 */             break;
/*      */           case 7:
/* 3053 */             stName = "rewriteRuleListLabelRef";
/* 3054 */             break;
/*      */           case 1:
/* 3056 */             stName = "rewriteRuleLabelRef";
/* 3057 */             break;
/*      */           case 4:
/* 3059 */             stName = "rewriteTokenListLabelRef";
/* 3060 */             break;
/*      */           case 3:
/* 3062 */             stName = "rewriteRuleListLabelRef";
/*      */           case 5:
/*      */           }
/* 3065 */           if (isRoot) {
/* 3066 */             stName = stName + "Root";
/*      */           }
/* 3068 */           code = this.templates.getInstanceOf(stName);
/* 3069 */           code.setAttribute("label", labelName);
/*      */         }
/*      */ 
/* 3072 */         break;
/*      */       case 40:
/* 3076 */         GrammarAST tmp72_AST_in = (GrammarAST)_t;
/* 3077 */         match(_t, 40);
/* 3078 */         _t = _t.getNextSibling();
/*      */ 
/* 3081 */         String actText = tmp72_AST_in.getText();
/* 3082 */         List chunks = this.generator.translateAction(this.currentRuleName, tmp72_AST_in);
/* 3083 */         code = this.templates.getInstanceOf("rewriteNodeAction" + (isRoot ? "Root" : ""));
/* 3084 */         code.setAttribute("action", chunks);
/*      */ 
/* 3086 */         break;
/*      */       default:
/* 3090 */         throw new NoViableAltException(_t);
/*      */       }
/*      */     }
/*      */     catch (RecognitionException ex)
/*      */     {
/* 3095 */       reportError(ex);
/* 3096 */       if (_t != null) _t = _t.getNextSibling();
/*      */     }
/* 3098 */     this._retTree = _t;
/* 3099 */     return code;
/*      */   }
/*      */ 
/*      */   public final StringTemplate rewrite_ebnf(AST _t) throws RecognitionException {
/* 3103 */     StringTemplate code = null;
/*      */ 
/* 3105 */     GrammarAST rewrite_ebnf_AST_in = _t == ASTNULL ? null : (GrammarAST)_t;
/*      */     try
/*      */     {
/* 3108 */       if (_t == null) _t = ASTNULL;
/* 3109 */       switch (_t.getType())
/*      */       {
/*      */       case 10:
/* 3112 */         AST __t108 = _t;
/* 3113 */         GrammarAST tmp73_AST_in = (GrammarAST)_t;
/* 3114 */         match(_t, 10);
/* 3115 */         _t = _t.getFirstChild();
/* 3116 */         code = rewrite_block(_t, "rewriteOptionalBlock");
/* 3117 */         _t = this._retTree;
/* 3118 */         _t = __t108;
/* 3119 */         _t = _t.getNextSibling();
/*      */ 
/* 3121 */         String description = this.grammar.grammarTreeToString(rewrite_ebnf_AST_in, false);
/* 3122 */         description = this.generator.target.getTargetStringLiteralFromString(description);
/* 3123 */         code.setAttribute("description", description);
/*      */ 
/* 3125 */         break;
/*      */       case 11:
/* 3129 */         AST __t109 = _t;
/* 3130 */         GrammarAST tmp74_AST_in = (GrammarAST)_t;
/* 3131 */         match(_t, 11);
/* 3132 */         _t = _t.getFirstChild();
/* 3133 */         code = rewrite_block(_t, "rewriteClosureBlock");
/* 3134 */         _t = this._retTree;
/* 3135 */         _t = __t109;
/* 3136 */         _t = _t.getNextSibling();
/*      */ 
/* 3138 */         String description = this.grammar.grammarTreeToString(rewrite_ebnf_AST_in, false);
/* 3139 */         description = this.generator.target.getTargetStringLiteralFromString(description);
/* 3140 */         code.setAttribute("description", description);
/*      */ 
/* 3142 */         break;
/*      */       case 12:
/* 3146 */         AST __t110 = _t;
/* 3147 */         GrammarAST tmp75_AST_in = (GrammarAST)_t;
/* 3148 */         match(_t, 12);
/* 3149 */         _t = _t.getFirstChild();
/* 3150 */         code = rewrite_block(_t, "rewritePositiveClosureBlock");
/* 3151 */         _t = this._retTree;
/* 3152 */         _t = __t110;
/* 3153 */         _t = _t.getNextSibling();
/*      */ 
/* 3155 */         String description = this.grammar.grammarTreeToString(rewrite_ebnf_AST_in, false);
/* 3156 */         description = this.generator.target.getTargetStringLiteralFromString(description);
/* 3157 */         code.setAttribute("description", description);
/*      */ 
/* 3159 */         break;
/*      */       default:
/* 3163 */         throw new NoViableAltException(_t);
/*      */       }
/*      */     }
/*      */     catch (RecognitionException ex)
/*      */     {
/* 3168 */       reportError(ex);
/* 3169 */       if (_t != null) _t = _t.getNextSibling();
/*      */     }
/* 3171 */     this._retTree = _t;
/* 3172 */     return code;
/*      */   }
/*      */ 
/*      */   public final StringTemplate rewrite_tree(AST _t) throws RecognitionException {
/* 3176 */     StringTemplate code = this.templates.getInstanceOf("rewriteTree");
/*      */ 
/* 3178 */     GrammarAST rewrite_tree_AST_in = _t == ASTNULL ? null : (GrammarAST)_t;
/*      */ 
/* 3180 */     this.rewriteTreeNestingLevel += 1;
/* 3181 */     code.setAttribute("treeLevel", this.rewriteTreeNestingLevel);
/* 3182 */     code.setAttribute("enclosingTreeLevel", this.rewriteTreeNestingLevel - 1);
/*      */ 
/* 3184 */     GrammarAST elAST = null;
/*      */     try
/*      */     {
/* 3188 */       AST __t112 = _t;
/* 3189 */       GrammarAST tmp76_AST_in = (GrammarAST)_t;
/* 3190 */       match(_t, 75);
/* 3191 */       _t = _t.getFirstChild();
/* 3192 */       elAST = (GrammarAST)_t;
/* 3193 */       StringTemplate r = rewrite_atom(_t, true);
/* 3194 */       _t = this._retTree;
/* 3195 */       code.setAttribute("root.{el,line,pos}", r, Utils.integer(elAST.getLine()), Utils.integer(elAST.getColumn()));
/*      */       while (true)
/*      */       {
/* 3204 */         if (_t == null) _t = ASTNULL;
/* 3205 */         if ((_t.getType() != 10) && (_t.getType() != 11) && (_t.getType() != 12) && (_t.getType() != 31) && (_t.getType() != 40) && (_t.getType() != 50) && (_t.getType() != 51) && (_t.getType() != 55) && (_t.getType() != 73) && (_t.getType() != 75)) break;
/* 3206 */         elAST = (GrammarAST)_t;
/* 3207 */         StringTemplate el = rewrite_element(_t);
/* 3208 */         _t = this._retTree;
/*      */ 
/* 3210 */         code.setAttribute("children.{el,line,pos}", el, Utils.integer(elAST.getLine()), Utils.integer(elAST.getColumn()));
/*      */       }
/*      */ 
/* 3223 */       _t = __t112;
/* 3224 */       _t = _t.getNextSibling();
/*      */ 
/* 3226 */       String description = this.grammar.grammarTreeToString(rewrite_tree_AST_in, false);
/* 3227 */       description = this.generator.target.getTargetStringLiteralFromString(description);
/* 3228 */       code.setAttribute("description", description);
/* 3229 */       this.rewriteTreeNestingLevel -= 1;
/*      */     }
/*      */     catch (RecognitionException ex)
/*      */     {
/* 3233 */       reportError(ex);
/* 3234 */       if (_t != null) _t = _t.getNextSibling();
/*      */     }
/* 3236 */     this._retTree = _t;
/* 3237 */     return code;
/*      */   }
/*      */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.antlr.grammar.v2.CodeGenTreeWalker
 * JD-Core Version:    0.6.2
 */