/*      */ package org.antlr.codegen;
/*      */ 
/*      */ import antlr.CommonToken;
/*      */ import antlr.RecognitionException;
/*      */ import antlr.Token;
/*      */ import antlr.TokenStreamRewriteEngine;
/*      */ import java.io.IOException;
/*      */ import java.io.StringReader;
/*      */ import java.io.Writer;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Collection;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.Set;
/*      */ import org.antlr.Tool;
/*      */ import org.antlr.analysis.DFA;
/*      */ import org.antlr.analysis.DFAOptimizer;
/*      */ import org.antlr.analysis.DFAState;
/*      */ import org.antlr.analysis.Label;
/*      */ import org.antlr.analysis.LookaheadSet;
/*      */ import org.antlr.analysis.NFAState;
/*      */ import org.antlr.analysis.SemanticContext;
/*      */ import org.antlr.analysis.State;
/*      */ import org.antlr.analysis.Transition;
/*      */ import org.antlr.grammar.v2.ANTLRLexer;
/*      */ import org.antlr.grammar.v2.ANTLRParser;
/*      */ import org.antlr.grammar.v2.CodeGenTreeWalker;
/*      */ import org.antlr.grammar.v3.ActionTranslator;
/*      */ import org.antlr.misc.BitSet;
/*      */ import org.antlr.misc.IntSet;
/*      */ import org.antlr.misc.Interval;
/*      */ import org.antlr.misc.IntervalSet;
/*      */ import org.antlr.misc.Utils;
/*      */ import org.antlr.stringtemplate.CommonGroupLoader;
/*      */ import org.antlr.stringtemplate.StringTemplate;
/*      */ import org.antlr.stringtemplate.StringTemplateGroup;
/*      */ import org.antlr.stringtemplate.StringTemplateGroupLoader;
/*      */ import org.antlr.stringtemplate.StringTemplateWriter;
/*      */ import org.antlr.stringtemplate.language.AngleBracketTemplateLexer;
/*      */ import org.antlr.tool.AttributeScope;
/*      */ import org.antlr.tool.CompositeGrammar;
/*      */ import org.antlr.tool.ErrorManager;
/*      */ import org.antlr.tool.Grammar;
/*      */ import org.antlr.tool.Grammar.LabelElementPair;
/*      */ import org.antlr.tool.GrammarAST;
/*      */ import org.antlr.tool.Rule;
/*      */ 
/*      */ public class CodeGenerator
/*      */ {
/*      */   public static final int MSCL_DEFAULT = 300;
/*   81 */   public static int MAX_SWITCH_CASE_LABELS = 300;
/*      */   public static final int MSA_DEFAULT = 3;
/*   83 */   public static int MIN_SWITCH_ALTS = 3;
/*   84 */   public boolean GENERATE_SWITCHES_WHEN_POSSIBLE = true;
/*      */ 
/*   86 */   public static boolean EMIT_TEMPLATE_DELIMITERS = false;
/*      */   public static final int MADSI_DEFAULT = 10;
/*   88 */   public static int MAX_ACYCLIC_DFA_STATES_INLINE = 10;
/*      */ 
/*   90 */   public String classpathTemplateRootDirectoryName = "org/antlr/codegen/templates";
/*      */   public Grammar grammar;
/*      */   protected String language;
/*  104 */   public Target target = null;
/*      */   protected StringTemplateGroup templates;
/*      */   protected StringTemplateGroup baseTemplates;
/*      */   protected StringTemplate recognizerST;
/*      */   protected StringTemplate outputFileST;
/*      */   protected StringTemplate headerFileST;
/*  120 */   protected int uniqueLabelNumber = 1;
/*      */   protected Tool tool;
/*      */   protected boolean debug;
/*      */   protected boolean trace;
/*      */   protected boolean profile;
/*  138 */   protected int lineWidth = 72;
/*      */ 
/*  141 */   public ACyclicDFACodeGenerator acyclicDFAGenerator = new ACyclicDFACodeGenerator(this);
/*      */   public static final String VOCAB_FILE_EXTENSION = ".tokens";
/*      */   protected static final String vocabFilePattern = "<tokens:{<attr.name>=<attr.type>\n}><literals:{<attr.name>=<attr.type>\n}>";
/*      */ 
/*      */   public CodeGenerator(Tool tool, Grammar grammar, String language)
/*      */   {
/*  156 */     this.tool = tool;
/*  157 */     this.grammar = grammar;
/*  158 */     this.language = language;
/*  159 */     this.target = loadLanguageTarget(language);
/*      */   }
/*      */ 
/*      */   public static Target loadLanguageTarget(String language) {
/*  163 */     Target target = null;
/*  164 */     String targetName = "org.antlr.codegen." + language + "Target";
/*      */     try {
/*  166 */       Class c = Class.forName(targetName);
/*  167 */       target = (Target)c.newInstance();
/*      */     }
/*      */     catch (ClassNotFoundException cnfe) {
/*  170 */       target = new Target();
/*      */     }
/*      */     catch (InstantiationException ie) {
/*  173 */       ErrorManager.error(23, targetName, ie);
/*      */     }
/*      */     catch (IllegalAccessException cnfe)
/*      */     {
/*  178 */       ErrorManager.error(23, targetName, cnfe);
/*      */     }
/*      */ 
/*  182 */     return target;
/*      */   }
/*      */ 
/*      */   public void loadTemplates(String language)
/*      */   {
/*  188 */     String templateDirs = this.classpathTemplateRootDirectoryName + ":" + this.classpathTemplateRootDirectoryName + "/" + language;
/*      */ 
/*  192 */     StringTemplateGroupLoader loader = new CommonGroupLoader(templateDirs, ErrorManager.getStringTemplateErrorListener());
/*      */ 
/*  195 */     StringTemplateGroup.registerGroupLoader(loader);
/*  196 */     StringTemplateGroup.registerDefaultLexer(AngleBracketTemplateLexer.class);
/*      */ 
/*  199 */     StringTemplateGroup coreTemplates = StringTemplateGroup.loadGroup(language);
/*      */ 
/*  201 */     this.baseTemplates = coreTemplates;
/*  202 */     if (coreTemplates == null) {
/*  203 */       ErrorManager.error(20, language);
/*      */ 
/*  205 */       return;
/*      */     }
/*      */ 
/*  210 */     String outputOption = (String)this.grammar.getOption("output");
/*  211 */     if ((outputOption != null) && (outputOption.equals("AST"))) {
/*  212 */       if ((this.debug) && (this.grammar.type != 1)) {
/*  213 */         StringTemplateGroup dbgTemplates = StringTemplateGroup.loadGroup("Dbg", coreTemplates);
/*      */ 
/*  215 */         this.baseTemplates = dbgTemplates;
/*  216 */         StringTemplateGroup astTemplates = StringTemplateGroup.loadGroup("AST", dbgTemplates);
/*      */ 
/*  218 */         StringTemplateGroup astParserTemplates = astTemplates;
/*      */ 
/*  220 */         if (this.grammar.type == 3) {
/*  221 */           astParserTemplates = StringTemplateGroup.loadGroup("ASTTreeParser", astTemplates);
/*      */         }
/*      */         else
/*      */         {
/*  225 */           astParserTemplates = StringTemplateGroup.loadGroup("ASTParser", astTemplates);
/*      */         }
/*      */ 
/*  229 */         StringTemplateGroup astDbgTemplates = StringTemplateGroup.loadGroup("ASTDbg", astParserTemplates);
/*      */ 
/*  231 */         this.templates = astDbgTemplates;
/*      */       }
/*      */       else {
/*  234 */         StringTemplateGroup astTemplates = StringTemplateGroup.loadGroup("AST", coreTemplates);
/*      */ 
/*  236 */         StringTemplateGroup astParserTemplates = astTemplates;
/*      */ 
/*  238 */         if (this.grammar.type == 3) {
/*  239 */           astParserTemplates = StringTemplateGroup.loadGroup("ASTTreeParser", astTemplates);
/*      */         }
/*      */         else
/*      */         {
/*  243 */           astParserTemplates = StringTemplateGroup.loadGroup("ASTParser", astTemplates);
/*      */         }
/*      */ 
/*  247 */         this.templates = astParserTemplates;
/*      */       }
/*      */     }
/*  250 */     else if ((outputOption != null) && (outputOption.equals("template"))) {
/*  251 */       if ((this.debug) && (this.grammar.type != 1)) {
/*  252 */         StringTemplateGroup dbgTemplates = StringTemplateGroup.loadGroup("Dbg", coreTemplates);
/*      */ 
/*  254 */         this.baseTemplates = dbgTemplates;
/*  255 */         StringTemplateGroup stTemplates = StringTemplateGroup.loadGroup("ST", dbgTemplates);
/*      */ 
/*  257 */         this.templates = stTemplates;
/*      */       }
/*      */       else {
/*  260 */         this.templates = StringTemplateGroup.loadGroup("ST", coreTemplates);
/*      */       }
/*      */     }
/*  263 */     else if ((this.debug) && (this.grammar.type != 1)) {
/*  264 */       this.templates = StringTemplateGroup.loadGroup("Dbg", coreTemplates);
/*  265 */       this.baseTemplates = this.templates;
/*      */     }
/*      */     else {
/*  268 */       this.templates = coreTemplates;
/*      */     }
/*      */ 
/*  271 */     if (EMIT_TEMPLATE_DELIMITERS) {
/*  272 */       this.templates.emitDebugStartStopStrings(true);
/*  273 */       this.templates.doNotEmitDebugStringsForTemplate("codeFileExtension");
/*  274 */       this.templates.doNotEmitDebugStringsForTemplate("headerFileExtension");
/*      */     }
/*      */   }
/*      */ 
/*      */   public StringTemplate genRecognizer()
/*      */   {
/*  293 */     loadTemplates(this.language);
/*  294 */     if (this.templates == null) {
/*  295 */       return null;
/*      */     }
/*      */ 
/*  299 */     if (ErrorManager.doNotAttemptAnalysis()) {
/*  300 */       return null;
/*      */     }
/*  302 */     this.target.performGrammarAnalysis(this, this.grammar);
/*      */ 
/*  306 */     if (ErrorManager.doNotAttemptCodeGen()) {
/*  307 */       return null;
/*      */     }
/*      */ 
/*  311 */     DFAOptimizer optimizer = new DFAOptimizer(this.grammar);
/*  312 */     optimizer.optimize();
/*      */ 
/*  315 */     this.outputFileST = this.templates.getInstanceOf("outputFile");
/*      */ 
/*  318 */     if (this.templates.isDefined("headerFile")) {
/*  319 */       this.headerFileST = this.templates.getInstanceOf("headerFile");
/*      */     }
/*      */     else
/*      */     {
/*  323 */       this.headerFileST = new StringTemplate(this.templates, "");
/*  324 */       this.headerFileST.setName("dummy-header-file");
/*      */     }
/*      */ 
/*  327 */     boolean filterMode = (this.grammar.getOption("filter") != null) && (this.grammar.getOption("filter").equals("true"));
/*      */ 
/*  329 */     boolean canBacktrack = (this.grammar.getSyntacticPredicates() != null) || (this.grammar.composite.getRootGrammar().atLeastOneBacktrackOption) || (filterMode);
/*      */ 
/*  340 */     Map actions = this.grammar.getActions();
/*  341 */     verifyActionScopesOkForTarget(actions);
/*      */ 
/*  343 */     translateActionAttributeReferences(actions);
/*      */ 
/*  345 */     StringTemplate gateST = this.templates.getInstanceOf("actionGate");
/*  346 */     if (filterMode)
/*      */     {
/*  349 */       gateST = this.templates.getInstanceOf("filteringActionGate");
/*      */     }
/*  351 */     this.grammar.setSynPredGateIfNotAlready(gateST);
/*      */ 
/*  353 */     this.headerFileST.setAttribute("actions", actions);
/*  354 */     this.outputFileST.setAttribute("actions", actions);
/*      */ 
/*  356 */     this.headerFileST.setAttribute("buildTemplate", new Boolean(this.grammar.buildTemplate()));
/*  357 */     this.outputFileST.setAttribute("buildTemplate", new Boolean(this.grammar.buildTemplate()));
/*  358 */     this.headerFileST.setAttribute("buildAST", new Boolean(this.grammar.buildAST()));
/*  359 */     this.outputFileST.setAttribute("buildAST", new Boolean(this.grammar.buildAST()));
/*      */ 
/*  361 */     this.outputFileST.setAttribute("rewriteMode", Boolean.valueOf(this.grammar.rewriteMode()));
/*  362 */     this.headerFileST.setAttribute("rewriteMode", Boolean.valueOf(this.grammar.rewriteMode()));
/*      */ 
/*  364 */     this.outputFileST.setAttribute("backtracking", Boolean.valueOf(canBacktrack));
/*  365 */     this.headerFileST.setAttribute("backtracking", Boolean.valueOf(canBacktrack));
/*      */ 
/*  369 */     String memoize = (String)this.grammar.getOption("memoize");
/*  370 */     if (!this.grammar.atLeastOneRuleMemoizes);
/*  370 */     this.outputFileST.setAttribute("memoize", new Boolean((Boolean.valueOf((memoize != null) && (memoize.equals("true"))).booleanValue()) && (canBacktrack)));
/*      */ 
/*  374 */     if (!this.grammar.atLeastOneRuleMemoizes);
/*  374 */     this.headerFileST.setAttribute("memoize", new Boolean((Boolean.valueOf((memoize != null) && (memoize.equals("true"))).booleanValue()) && (canBacktrack)));
/*      */ 
/*  380 */     this.outputFileST.setAttribute("trace", Boolean.valueOf(this.trace));
/*  381 */     this.headerFileST.setAttribute("trace", Boolean.valueOf(this.trace));
/*      */ 
/*  383 */     this.outputFileST.setAttribute("profile", Boolean.valueOf(this.profile));
/*  384 */     this.headerFileST.setAttribute("profile", Boolean.valueOf(this.profile));
/*      */ 
/*  387 */     if (this.grammar.type == 1) {
/*  388 */       this.recognizerST = this.templates.getInstanceOf("lexer");
/*  389 */       this.outputFileST.setAttribute("LEXER", Boolean.valueOf(true));
/*  390 */       this.headerFileST.setAttribute("LEXER", Boolean.valueOf(true));
/*  391 */       this.recognizerST.setAttribute("filterMode", Boolean.valueOf(filterMode));
/*      */     }
/*  394 */     else if ((this.grammar.type == 2) || (this.grammar.type == 4))
/*      */     {
/*  397 */       this.recognizerST = this.templates.getInstanceOf("parser");
/*  398 */       this.outputFileST.setAttribute("PARSER", Boolean.valueOf(true));
/*  399 */       this.headerFileST.setAttribute("PARSER", Boolean.valueOf(true));
/*      */     }
/*      */     else {
/*  402 */       this.recognizerST = this.templates.getInstanceOf("treeParser");
/*  403 */       this.outputFileST.setAttribute("TREE_PARSER", Boolean.valueOf(true));
/*  404 */       this.headerFileST.setAttribute("TREE_PARSER", Boolean.valueOf(true));
/*  405 */       this.recognizerST.setAttribute("filterMode", Boolean.valueOf(filterMode));
/*      */     }
/*      */ 
/*  408 */     this.outputFileST.setAttribute("recognizer", this.recognizerST);
/*  409 */     this.headerFileST.setAttribute("recognizer", this.recognizerST);
/*  410 */     this.outputFileST.setAttribute("actionScope", this.grammar.getDefaultActionScope(this.grammar.type));
/*      */ 
/*  412 */     this.headerFileST.setAttribute("actionScope", this.grammar.getDefaultActionScope(this.grammar.type));
/*      */ 
/*  415 */     String targetAppropriateFileNameString = this.target.getTargetStringLiteralFromString(this.grammar.getFileName());
/*      */ 
/*  417 */     this.outputFileST.setAttribute("fileName", targetAppropriateFileNameString);
/*  418 */     this.headerFileST.setAttribute("fileName", targetAppropriateFileNameString);
/*  419 */     this.outputFileST.setAttribute("ANTLRVersion", this.tool.VERSION);
/*  420 */     this.headerFileST.setAttribute("ANTLRVersion", this.tool.VERSION);
/*  421 */     this.outputFileST.setAttribute("generatedTimestamp", Tool.getCurrentTimeStamp());
/*  422 */     this.headerFileST.setAttribute("generatedTimestamp", Tool.getCurrentTimeStamp());
/*      */ 
/*  428 */     CodeGenTreeWalker gen = new CodeGenTreeWalker();
/*      */     try {
/*  430 */       gen.grammar(this.grammar.getGrammarTree(), this.grammar, this.recognizerST, this.outputFileST, this.headerFileST);
/*      */     }
/*      */     catch (RecognitionException re)
/*      */     {
/*  437 */       ErrorManager.error(15, re);
/*      */     }
/*      */ 
/*  441 */     genTokenTypeConstants(this.recognizerST);
/*  442 */     genTokenTypeConstants(this.outputFileST);
/*  443 */     genTokenTypeConstants(this.headerFileST);
/*      */ 
/*  445 */     if (this.grammar.type != 1) {
/*  446 */       genTokenTypeNames(this.recognizerST);
/*  447 */       genTokenTypeNames(this.outputFileST);
/*  448 */       genTokenTypeNames(this.headerFileST);
/*      */     }
/*      */ 
/*  452 */     Set synpredNames = null;
/*  453 */     if (this.grammar.synPredNamesUsedInDFA.size() > 0) {
/*  454 */       synpredNames = this.grammar.synPredNamesUsedInDFA;
/*      */     }
/*  456 */     this.outputFileST.setAttribute("synpreds", synpredNames);
/*  457 */     this.headerFileST.setAttribute("synpreds", synpredNames);
/*      */ 
/*  460 */     this.recognizerST.setAttribute("grammar", this.grammar);
/*      */     try
/*      */     {
/*  464 */       this.target.genRecognizerFile(this.tool, this, this.grammar, this.outputFileST);
/*  465 */       if (this.templates.isDefined("headerFile")) {
/*  466 */         StringTemplate extST = this.templates.getInstanceOf("headerFileExtension");
/*  467 */         this.target.genRecognizerHeaderFile(this.tool, this, this.grammar, this.headerFileST, extST.toString());
/*      */       }
/*      */ 
/*  471 */       StringTemplate tokenVocabSerialization = genTokenVocabOutput();
/*  472 */       String vocabFileName = getVocabFileName();
/*  473 */       if (vocabFileName != null) {
/*  474 */         write(tokenVocabSerialization, vocabFileName);
/*      */       }
/*      */     }
/*      */     catch (IOException ioe)
/*      */     {
/*  479 */       ErrorManager.error(1, getVocabFileName(), ioe);
/*      */     }
/*      */ 
/*  488 */     return this.outputFileST;
/*      */   }
/*      */ 
/*      */   protected void verifyActionScopesOkForTarget(Map actions)
/*      */   {
/*  496 */     Set actionScopeKeySet = actions.keySet();
/*  497 */     for (Iterator it = actionScopeKeySet.iterator(); it.hasNext(); ) {
/*  498 */       String scope = (String)it.next();
/*  499 */       if (!this.target.isValidActionScope(this.grammar.type, scope))
/*      */       {
/*  501 */         Map scopeActions = (Map)actions.get(scope);
/*  502 */         GrammarAST actionAST = (GrammarAST)scopeActions.values().iterator().next();
/*      */ 
/*  504 */         ErrorManager.grammarError(143, this.grammar, actionAST.getToken(), scope, this.grammar.getGrammarTypeString());
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   protected void translateActionAttributeReferences(Map actions)
/*      */   {
/*  516 */     Set actionScopeKeySet = actions.keySet();
/*  517 */     for (Iterator it = actionScopeKeySet.iterator(); it.hasNext(); ) {
/*  518 */       String scope = (String)it.next();
/*  519 */       Map scopeActions = (Map)actions.get(scope);
/*  520 */       translateActionAttributeReferencesForSingleScope(null, scopeActions);
/*      */     }
/*      */   }
/*      */ 
/*      */   public void translateActionAttributeReferencesForSingleScope(Rule r, Map scopeActions)
/*      */   {
/*  529 */     String ruleName = null;
/*  530 */     if (r != null) {
/*  531 */       ruleName = r.name;
/*      */     }
/*  533 */     Set actionNameSet = scopeActions.keySet();
/*  534 */     for (Iterator nameIT = actionNameSet.iterator(); nameIT.hasNext(); ) {
/*  535 */       String name = (String)nameIT.next();
/*  536 */       GrammarAST actionAST = (GrammarAST)scopeActions.get(name);
/*  537 */       List chunks = translateAction(ruleName, actionAST);
/*  538 */       scopeActions.put(name, chunks);
/*      */     }
/*      */   }
/*      */ 
/*      */   public void generateLocalFOLLOW(GrammarAST referencedElementNode, String referencedElementName, String enclosingRuleName, int elementIndex)
/*      */   {
/*  571 */     NFAState followingNFAState = referencedElementNode.followingNFAState;
/*  572 */     LookaheadSet follow = null;
/*  573 */     if (followingNFAState != null)
/*      */     {
/*  576 */       follow = this.grammar.FIRST(followingNFAState);
/*      */     }
/*      */ 
/*  579 */     if (follow == null) {
/*  580 */       ErrorManager.internalError("no follow state or cannot compute follow");
/*  581 */       follow = new LookaheadSet();
/*      */     }
/*  583 */     if (follow.member(-1))
/*      */     {
/*  587 */       follow.remove(-1);
/*      */     }
/*      */ 
/*  591 */     List tokenTypeList = null;
/*  592 */     long[] words = null;
/*  593 */     if (follow.tokenTypeSet == null) {
/*  594 */       words = new long[1];
/*  595 */       tokenTypeList = new ArrayList();
/*      */     }
/*      */     else {
/*  598 */       BitSet bits = BitSet.of(follow.tokenTypeSet);
/*  599 */       words = bits.toPackedArray();
/*  600 */       tokenTypeList = follow.tokenTypeSet.toList();
/*      */     }
/*      */ 
/*  603 */     String[] wordStrings = new String[words.length];
/*  604 */     for (int j = 0; j < words.length; j++) {
/*  605 */       long w = words[j];
/*  606 */       wordStrings[j] = this.target.getTarget64BitStringFromValue(w);
/*      */     }
/*  608 */     this.recognizerST.setAttribute("bitsets.{name,inName,bits,tokenTypes,tokenIndex}", referencedElementName, enclosingRuleName, wordStrings, tokenTypeList, Utils.integer(elementIndex));
/*      */ 
/*  614 */     this.outputFileST.setAttribute("bitsets.{name,inName,bits,tokenTypes,tokenIndex}", referencedElementName, enclosingRuleName, wordStrings, tokenTypeList, Utils.integer(elementIndex));
/*      */ 
/*  620 */     this.headerFileST.setAttribute("bitsets.{name,inName,bits,tokenTypes,tokenIndex}", referencedElementName, enclosingRuleName, wordStrings, tokenTypeList, Utils.integer(elementIndex));
/*      */   }
/*      */ 
/*      */   public StringTemplate genLookaheadDecision(StringTemplate recognizerST, DFA dfa)
/*      */   {
/*      */     StringTemplate decisionST;
/*      */     StringTemplate decisionST;
/*  645 */     if (dfa.canInlineDecision()) {
/*  646 */       decisionST = this.acyclicDFAGenerator.genFixedLookaheadDecision(getTemplates(), dfa);
/*      */     }
/*      */     else
/*      */     {
/*  651 */       dfa.createStateTables(this);
/*  652 */       this.outputFileST.setAttribute("cyclicDFAs", dfa);
/*  653 */       this.headerFileST.setAttribute("cyclicDFAs", dfa);
/*  654 */       decisionST = this.templates.getInstanceOf("dfaDecision");
/*  655 */       String description = dfa.getNFADecisionStartState().getDescription();
/*  656 */       description = this.target.getTargetStringLiteralFromString(description);
/*  657 */       if (description != null) {
/*  658 */         decisionST.setAttribute("description", description);
/*      */       }
/*  660 */       decisionST.setAttribute("decisionNumber", Utils.integer(dfa.getDecisionNumber()));
/*      */     }
/*      */ 
/*  663 */     return decisionST;
/*      */   }
/*      */ 
/*      */   public StringTemplate generateSpecialState(DFAState s)
/*      */   {
/*  674 */     StringTemplate stateST = this.templates.getInstanceOf("cyclicDFAState");
/*  675 */     stateST.setAttribute("needErrorClause", Boolean.valueOf(true));
/*  676 */     stateST.setAttribute("semPredState", Boolean.valueOf(s.isResolvedWithPredicates()));
/*      */ 
/*  678 */     stateST.setAttribute("stateNumber", s.stateNumber);
/*  679 */     stateST.setAttribute("decisionNumber", s.dfa.decisionNumber);
/*      */ 
/*  681 */     boolean foundGatedPred = false;
/*  682 */     StringTemplate eotST = null;
/*  683 */     for (int i = 0; i < s.getNumberOfTransitions(); i++) {
/*  684 */       Transition edge = s.transition(i);
/*      */       StringTemplate edgeST;
/*  686 */       if (edge.label.getAtom() == -2)
/*      */       {
/*  688 */         StringTemplate edgeST = this.templates.getInstanceOf("eotDFAEdge");
/*  689 */         stateST.removeAttribute("needErrorClause");
/*  690 */         eotST = edgeST;
/*      */       }
/*      */       else {
/*  693 */         edgeST = this.templates.getInstanceOf("cyclicDFAEdge");
/*  694 */         StringTemplate exprST = genLabelExpr(this.templates, edge, 1);
/*      */ 
/*  696 */         edgeST.setAttribute("labelExpr", exprST);
/*      */       }
/*  698 */       edgeST.setAttribute("edgeNumber", Utils.integer(i + 1));
/*  699 */       edgeST.setAttribute("targetStateNumber", Utils.integer(edge.target.stateNumber));
/*      */ 
/*  702 */       if (!edge.label.isSemanticPredicate()) {
/*  703 */         DFAState t = (DFAState)edge.target;
/*  704 */         SemanticContext preds = t.getGatedPredicatesInNFAConfigurations();
/*  705 */         if (preds != null) {
/*  706 */           foundGatedPred = true;
/*  707 */           StringTemplate predST = preds.genExpr(this, getTemplates(), t.dfa);
/*      */ 
/*  710 */           edgeST.setAttribute("predicates", predST.toString());
/*      */         }
/*      */       }
/*  713 */       if (edge.label.getAtom() != -2) {
/*  714 */         stateST.setAttribute("edges", edgeST);
/*      */       }
/*      */     }
/*  717 */     if (foundGatedPred)
/*      */     {
/*  720 */       stateST.setAttribute("semPredState", new Boolean(foundGatedPred));
/*      */     }
/*  722 */     if (eotST != null) {
/*  723 */       stateST.setAttribute("edges", eotST);
/*      */     }
/*  725 */     return stateST;
/*      */   }
/*      */ 
/*      */   protected StringTemplate genLabelExpr(StringTemplateGroup templates, Transition edge, int k)
/*      */   {
/*  733 */     Label label = edge.label;
/*  734 */     if (label.isSemanticPredicate()) {
/*  735 */       return genSemanticPredicateExpr(templates, edge);
/*      */     }
/*  737 */     if (label.isSet()) {
/*  738 */       return genSetExpr(templates, label.getSet(), k, true);
/*      */     }
/*      */ 
/*  741 */     StringTemplate eST = templates.getInstanceOf("lookaheadTest");
/*  742 */     eST.setAttribute("atom", getTokenTypeAsTargetLabel(label.getAtom()));
/*  743 */     eST.setAttribute("atomAsInt", Utils.integer(label.getAtom()));
/*  744 */     eST.setAttribute("k", Utils.integer(k));
/*  745 */     return eST;
/*      */   }
/*      */ 
/*      */   protected StringTemplate genSemanticPredicateExpr(StringTemplateGroup templates, Transition edge)
/*      */   {
/*  751 */     DFA dfa = ((DFAState)edge.target).dfa;
/*  752 */     Label label = edge.label;
/*  753 */     SemanticContext semCtx = label.getSemanticContext();
/*  754 */     return semCtx.genExpr(this, templates, dfa);
/*      */   }
/*      */ 
/*      */   public StringTemplate genSetExpr(StringTemplateGroup templates, IntSet set, int k, boolean partOfDFA)
/*      */   {
/*  765 */     if (!(set instanceof IntervalSet)) {
/*  766 */       throw new IllegalArgumentException("unable to generate expressions for non IntervalSet objects");
/*      */     }
/*  768 */     IntervalSet iset = (IntervalSet)set;
/*  769 */     if ((iset.getIntervals() == null) || (iset.getIntervals().size() == 0)) {
/*  770 */       StringTemplate emptyST = new StringTemplate(templates, "");
/*  771 */       emptyST.setName("empty-set-expr");
/*  772 */       return emptyST;
/*      */     }
/*  774 */     String testSTName = "lookaheadTest";
/*  775 */     String testRangeSTName = "lookaheadRangeTest";
/*  776 */     if (!partOfDFA) {
/*  777 */       testSTName = "isolatedLookaheadTest";
/*  778 */       testRangeSTName = "isolatedLookaheadRangeTest";
/*      */     }
/*  780 */     StringTemplate setST = templates.getInstanceOf("setTest");
/*  781 */     Iterator iter = iset.getIntervals().iterator();
/*  782 */     int rangeNumber = 1;
/*  783 */     while (iter.hasNext()) {
/*  784 */       Interval I = (Interval)iter.next();
/*  785 */       int a = I.a;
/*  786 */       int b = I.b;
/*      */       StringTemplate eST;
/*  788 */       if (a == b) {
/*  789 */         StringTemplate eST = templates.getInstanceOf(testSTName);
/*  790 */         eST.setAttribute("atom", getTokenTypeAsTargetLabel(a));
/*  791 */         eST.setAttribute("atomAsInt", Utils.integer(a));
/*      */       }
/*      */       else
/*      */       {
/*  795 */         eST = templates.getInstanceOf(testRangeSTName);
/*  796 */         eST.setAttribute("lower", getTokenTypeAsTargetLabel(a));
/*  797 */         eST.setAttribute("lowerAsInt", Utils.integer(a));
/*  798 */         eST.setAttribute("upper", getTokenTypeAsTargetLabel(b));
/*  799 */         eST.setAttribute("upperAsInt", Utils.integer(b));
/*  800 */         eST.setAttribute("rangeNumber", Utils.integer(rangeNumber));
/*      */       }
/*  802 */       eST.setAttribute("k", Utils.integer(k));
/*  803 */       setST.setAttribute("ranges", eST);
/*  804 */       rangeNumber++;
/*      */     }
/*  806 */     return setST;
/*      */   }
/*      */ 
/*      */   protected void genTokenTypeConstants(StringTemplate code)
/*      */   {
/*  817 */     Iterator tokenIDs = this.grammar.getTokenIDs().iterator();
/*  818 */     while (tokenIDs.hasNext()) {
/*  819 */       String tokenID = (String)tokenIDs.next();
/*  820 */       int tokenType = this.grammar.getTokenType(tokenID);
/*  821 */       if ((tokenType == -1) || (tokenType >= 4))
/*      */       {
/*  825 */         code.setAttribute("tokens.{name,type}", tokenID, Utils.integer(tokenType));
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   protected void genTokenTypeNames(StringTemplate code)
/*      */   {
/*  834 */     for (int t = 4; t <= this.grammar.getMaxTokenType(); t++) {
/*  835 */       String tokenName = this.grammar.getTokenDisplayName(t);
/*  836 */       if (tokenName != null) {
/*  837 */         tokenName = this.target.getTargetStringLiteralFromString(tokenName, true);
/*  838 */         code.setAttribute("tokenNames", tokenName);
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   public String getTokenTypeAsTargetLabel(int ttype)
/*      */   {
/*  854 */     if (this.grammar.type == 1) {
/*  855 */       String name = this.grammar.getTokenDisplayName(ttype);
/*  856 */       return this.target.getTargetCharLiteralFromANTLRCharLiteral(this, name);
/*      */     }
/*  858 */     return this.target.getTokenTypeAsTargetLabel(this, ttype);
/*      */   }
/*      */ 
/*      */   protected StringTemplate genTokenVocabOutput()
/*      */   {
/*  869 */     StringTemplate vocabFileST = new StringTemplate("<tokens:{<attr.name>=<attr.type>\n}><literals:{<attr.name>=<attr.type>\n}>", AngleBracketTemplateLexer.class);
/*      */ 
/*  872 */     vocabFileST.setName("vocab-file");
/*      */ 
/*  874 */     Iterator tokenIDs = this.grammar.getTokenIDs().iterator();
/*  875 */     while (tokenIDs.hasNext()) {
/*  876 */       String tokenID = (String)tokenIDs.next();
/*  877 */       int tokenType = this.grammar.getTokenType(tokenID);
/*  878 */       if (tokenType >= 4) {
/*  879 */         vocabFileST.setAttribute("tokens.{name,type}", tokenID, Utils.integer(tokenType));
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/*  884 */     Iterator literals = this.grammar.getStringLiterals().iterator();
/*  885 */     while (literals.hasNext()) {
/*  886 */       String literal = (String)literals.next();
/*  887 */       int tokenType = this.grammar.getTokenType(literal);
/*  888 */       if (tokenType >= 4) {
/*  889 */         vocabFileST.setAttribute("tokens.{name,type}", literal, Utils.integer(tokenType));
/*      */       }
/*      */     }
/*      */ 
/*  893 */     return vocabFileST;
/*      */   }
/*      */ 
/*      */   public List translateAction(String ruleName, GrammarAST actionTree)
/*      */   {
/*  899 */     if (actionTree.getType() == 60) {
/*  900 */       return translateArgAction(ruleName, actionTree);
/*      */     }
/*  902 */     ActionTranslator translator = new ActionTranslator(this, ruleName, actionTree);
/*  903 */     List chunks = translator.translateToChunks();
/*  904 */     chunks = this.target.postProcessAction(chunks, actionTree.token);
/*  905 */     return chunks;
/*      */   }
/*      */ 
/*      */   public List<StringTemplate> translateArgAction(String ruleName, GrammarAST actionTree)
/*      */   {
/*  916 */     String actionText = actionTree.token.getText();
/*  917 */     List args = getListOfArgumentsFromAction(actionText, 44);
/*  918 */     List translatedArgs = new ArrayList();
/*  919 */     for (Iterator i$ = args.iterator(); i$.hasNext(); ) { String arg = (String)i$.next();
/*  920 */       if (arg != null) {
/*  921 */         Token actionToken = new CommonToken(40, arg);
/*      */ 
/*  923 */         ActionTranslator translator = new ActionTranslator(this, ruleName, actionToken, actionTree.outerAltNum);
/*      */ 
/*  927 */         List chunks = translator.translateToChunks();
/*  928 */         chunks = this.target.postProcessAction(chunks, actionToken);
/*  929 */         StringTemplate catST = new StringTemplate(this.templates, "<chunks>");
/*  930 */         catST.setAttribute("chunks", chunks);
/*  931 */         this.templates.createStringTemplate();
/*  932 */         translatedArgs.add(catST);
/*      */       }
/*      */     }
/*  935 */     if (translatedArgs.size() == 0) {
/*  936 */       return null;
/*      */     }
/*  938 */     return translatedArgs;
/*      */   }
/*      */ 
/*      */   public static List<String> getListOfArgumentsFromAction(String actionText, int separatorChar)
/*      */   {
/*  944 */     List args = new ArrayList();
/*  945 */     getListOfArgumentsFromAction(actionText, 0, -1, separatorChar, args);
/*  946 */     return args;
/*      */   }
/*      */ 
/*      */   public static int getListOfArgumentsFromAction(String actionText, int start, int targetChar, int separatorChar, List<String> args)
/*      */   {
/*  963 */     if (actionText == null) {
/*  964 */       return -1;
/*      */     }
/*  966 */     actionText = actionText.replaceAll("//.*\n", "");
/*  967 */     int n = actionText.length();
/*      */ 
/*  969 */     int p = start;
/*  970 */     int last = p;
/*  971 */     while ((p < n) && (actionText.charAt(p) != targetChar)) {
/*  972 */       int c = actionText.charAt(p);
/*  973 */       switch (c) {
/*      */       case 39:
/*  975 */         p++;
/*  976 */         while ((p < n) && (actionText.charAt(p) != '\'')) {
/*  977 */           if ((actionText.charAt(p) == '\\') && (p + 1 < n) && (actionText.charAt(p + 1) == '\''))
/*      */           {
/*  980 */             p++;
/*      */           }
/*  982 */           p++;
/*      */         }
/*  984 */         p++;
/*  985 */         break;
/*      */       case 34:
/*  987 */         p++;
/*  988 */         while ((p < n) && (actionText.charAt(p) != '"')) {
/*  989 */           if ((actionText.charAt(p) == '\\') && (p + 1 < n) && (actionText.charAt(p + 1) == '"'))
/*      */           {
/*  992 */             p++;
/*      */           }
/*  994 */           p++;
/*      */         }
/*  996 */         p++;
/*  997 */         break;
/*      */       case 40:
/*  999 */         p = getListOfArgumentsFromAction(actionText, p + 1, 41, separatorChar, args);
/* 1000 */         break;
/*      */       case 123:
/* 1002 */         p = getListOfArgumentsFromAction(actionText, p + 1, 125, separatorChar, args);
/* 1003 */         break;
/*      */       case 60:
/* 1005 */         if (actionText.indexOf('>', p + 1) >= p)
/*      */         {
/* 1008 */           p = getListOfArgumentsFromAction(actionText, p + 1, 62, separatorChar, args);
/*      */         }
/*      */         else {
/* 1011 */           p++;
/*      */         }
/* 1013 */         break;
/*      */       case 91:
/* 1015 */         p = getListOfArgumentsFromAction(actionText, p + 1, 93, separatorChar, args);
/* 1016 */         break;
/*      */       default:
/* 1018 */         if ((c == separatorChar) && (targetChar == -1)) {
/* 1019 */           String arg = actionText.substring(last, p);
/*      */ 
/* 1021 */           args.add(arg.trim());
/* 1022 */           last = p + 1;
/*      */         }
/* 1024 */         p++;
/*      */       }
/*      */     }
/*      */ 
/* 1028 */     if ((targetChar == -1) && (p <= n)) {
/* 1029 */       String arg = actionText.substring(last, p).trim();
/*      */ 
/* 1031 */       if (arg.length() > 0) {
/* 1032 */         args.add(arg.trim());
/*      */       }
/*      */     }
/* 1035 */     p++;
/* 1036 */     return p;
/*      */   }
/*      */ 
/*      */   public StringTemplate translateTemplateConstructor(String ruleName, int outerAltNum, Token actionToken, String templateActionText)
/*      */   {
/* 1050 */     ANTLRLexer lexer = new ANTLRLexer(new StringReader(templateActionText));
/* 1051 */     lexer.setFilename(this.grammar.getFileName());
/* 1052 */     lexer.setTokenObjectClass("antlr.TokenWithIndex");
/* 1053 */     TokenStreamRewriteEngine tokenBuffer = new TokenStreamRewriteEngine(lexer);
/* 1054 */     tokenBuffer.discard(85);
/* 1055 */     tokenBuffer.discard(88);
/* 1056 */     tokenBuffer.discard(86);
/* 1057 */     tokenBuffer.discard(87);
/* 1058 */     ANTLRParser parser = new ANTLRParser(tokenBuffer);
/* 1059 */     parser.setFilename(this.grammar.getFileName());
/* 1060 */     parser.setASTNodeClass("org.antlr.tool.GrammarAST");
/*      */     try {
/* 1062 */       parser.rewrite_template();
/*      */     }
/*      */     catch (RecognitionException re) {
/* 1065 */       ErrorManager.grammarError(146, this.grammar, actionToken, templateActionText);
/*      */     }
/*      */     catch (Exception tse)
/*      */     {
/* 1071 */       ErrorManager.internalError("can't parse template action", tse);
/*      */     }
/* 1073 */     GrammarAST rewriteTree = (GrammarAST)parser.getAST();
/*      */ 
/* 1076 */     CodeGenTreeWalker gen = new CodeGenTreeWalker();
/* 1077 */     gen.init(this.grammar);
/* 1078 */     gen.setCurrentRuleName(ruleName);
/* 1079 */     gen.setOuterAltNum(outerAltNum);
/* 1080 */     StringTemplate st = null;
/*      */     try {
/* 1082 */       st = gen.rewrite_template(rewriteTree);
/*      */     }
/*      */     catch (RecognitionException re) {
/* 1085 */       ErrorManager.error(15, re);
/*      */     }
/*      */ 
/* 1088 */     return st;
/*      */   }
/*      */ 
/*      */   public void issueInvalidScopeError(String x, String y, Rule enclosingRule, Token actionToken, int outerAltNum)
/*      */   {
/* 1099 */     Rule r = this.grammar.getRule(x);
/* 1100 */     AttributeScope scope = this.grammar.getGlobalScope(x);
/* 1101 */     if ((scope == null) && 
/* 1102 */       (r != null)) {
/* 1103 */       scope = r.ruleScope;
/*      */     }
/*      */ 
/* 1106 */     if (scope == null) {
/* 1107 */       ErrorManager.grammarError(140, this.grammar, actionToken, x);
/*      */     }
/* 1112 */     else if (scope.getAttribute(y) == null)
/* 1113 */       ErrorManager.grammarError(141, this.grammar, actionToken, x, y);
/*      */   }
/*      */ 
/*      */   public void issueInvalidAttributeError(String x, String y, Rule enclosingRule, Token actionToken, int outerAltNum)
/*      */   {
/* 1128 */     if (enclosingRule == null)
/*      */     {
/* 1130 */       ErrorManager.grammarError(111, this.grammar, actionToken, x, y);
/*      */ 
/* 1135 */       return;
/*      */     }
/*      */ 
/* 1139 */     Grammar.LabelElementPair label = enclosingRule.getRuleLabel(x);
/*      */ 
/* 1141 */     if ((label != null) || (enclosingRule.getRuleRefsInAlt(x, outerAltNum) != null))
/*      */     {
/* 1143 */       String refdRuleName = x;
/* 1144 */       if (label != null) {
/* 1145 */         refdRuleName = enclosingRule.getRuleLabel(x).referencedRuleName;
/*      */       }
/* 1147 */       Rule refdRule = this.grammar.getRule(refdRuleName);
/* 1148 */       AttributeScope scope = refdRule.getAttributeScope(y);
/* 1149 */       if (scope == null) {
/* 1150 */         ErrorManager.grammarError(116, this.grammar, actionToken, refdRuleName, y);
/*      */       }
/* 1156 */       else if (scope.isParameterScope) {
/* 1157 */         ErrorManager.grammarError(115, this.grammar, actionToken, refdRuleName, y);
/*      */       }
/* 1163 */       else if (scope.isDynamicRuleScope)
/* 1164 */         ErrorManager.grammarError(112, this.grammar, actionToken, refdRuleName, y);
/*      */     }
/*      */   }
/*      */ 
/*      */   public void issueInvalidAttributeError(String x, Rule enclosingRule, Token actionToken, int outerAltNum)
/*      */   {
/* 1180 */     if (enclosingRule == null)
/*      */     {
/* 1182 */       ErrorManager.grammarError(111, this.grammar, actionToken, x);
/*      */ 
/* 1186 */       return;
/*      */     }
/*      */ 
/* 1190 */     Grammar.LabelElementPair label = enclosingRule.getRuleLabel(x);
/* 1191 */     AttributeScope scope = enclosingRule.getAttributeScope(x);
/*      */ 
/* 1193 */     if ((label != null) || (enclosingRule.getRuleRefsInAlt(x, outerAltNum) != null) || (enclosingRule.name.equals(x)))
/*      */     {
/* 1197 */       ErrorManager.grammarError(117, this.grammar, actionToken, x);
/*      */     }
/* 1202 */     else if ((scope != null) && (scope.isDynamicRuleScope)) {
/* 1203 */       ErrorManager.grammarError(142, this.grammar, actionToken, x);
/*      */     }
/*      */     else
/*      */     {
/* 1209 */       ErrorManager.grammarError(114, this.grammar, actionToken, x);
/*      */     }
/*      */   }
/*      */ 
/*      */   public StringTemplateGroup getTemplates()
/*      */   {
/* 1219 */     return this.templates;
/*      */   }
/*      */ 
/*      */   public StringTemplateGroup getBaseTemplates() {
/* 1223 */     return this.baseTemplates;
/*      */   }
/*      */ 
/*      */   public void setDebug(boolean debug) {
/* 1227 */     this.debug = debug;
/*      */   }
/*      */ 
/*      */   public void setTrace(boolean trace) {
/* 1231 */     this.trace = trace;
/*      */   }
/*      */ 
/*      */   public void setProfile(boolean profile) {
/* 1235 */     this.profile = profile;
/* 1236 */     if (profile)
/* 1237 */       setDebug(true);
/*      */   }
/*      */ 
/*      */   public StringTemplate getRecognizerST()
/*      */   {
/* 1242 */     return this.outputFileST;
/*      */   }
/*      */ 
/*      */   public String getRecognizerFileName(String name, int type)
/*      */   {
/* 1249 */     StringTemplate extST = this.templates.getInstanceOf("codeFileExtension");
/* 1250 */     String recognizerName = this.grammar.getRecognizerName();
/* 1251 */     return recognizerName + extST.toString();
/*      */   }
/*      */ 
/*      */   public String getVocabFileName()
/*      */   {
/* 1267 */     if (this.grammar.isBuiltFromString()) {
/* 1268 */       return null;
/*      */     }
/* 1270 */     return this.grammar.name + ".tokens";
/*      */   }
/*      */ 
/*      */   public void write(StringTemplate code, String fileName) throws IOException {
/* 1274 */     long start = System.currentTimeMillis();
/* 1275 */     Writer w = this.tool.getOutputFile(this.grammar, fileName);
/*      */ 
/* 1277 */     StringTemplateWriter wr = this.templates.getStringTemplateWriter(w);
/* 1278 */     wr.setLineWidth(this.lineWidth);
/* 1279 */     code.write(wr);
/* 1280 */     w.close();
/* 1281 */     long stop = System.currentTimeMillis();
/*      */   }
/*      */ 
/*      */   protected boolean canGenerateSwitch(DFAState s)
/*      */   {
/* 1292 */     if (!this.GENERATE_SWITCHES_WHEN_POSSIBLE) {
/* 1293 */       return false;
/*      */     }
/* 1295 */     int size = 0;
/* 1296 */     for (int i = 0; i < s.getNumberOfTransitions(); i++) {
/* 1297 */       Transition edge = s.transition(i);
/* 1298 */       if (edge.label.isSemanticPredicate()) {
/* 1299 */         return false;
/*      */       }
/*      */ 
/* 1302 */       if (edge.label.getAtom() == -2) {
/* 1303 */         int EOTPredicts = ((DFAState)edge.target).getUniquelyPredictedAlt();
/* 1304 */         if (EOTPredicts == -1)
/*      */         {
/* 1306 */           return false;
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/* 1311 */       if (((DFAState)edge.target).getGatedPredicatesInNFAConfigurations() != null) {
/* 1312 */         return false;
/*      */       }
/* 1314 */       size += edge.label.getSet().size();
/*      */     }
/* 1316 */     if ((s.getNumberOfTransitions() < MIN_SWITCH_ALTS) || (size > MAX_SWITCH_CASE_LABELS))
/*      */     {
/* 1318 */       return false;
/*      */     }
/* 1320 */     return true;
/*      */   }
/*      */ 
/*      */   public String createUniqueLabel(String name)
/*      */   {
/* 1330 */     return name + this.uniqueLabelNumber++;
/*      */   }
/*      */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.antlr.codegen.CodeGenerator
 * JD-Core Version:    0.6.2
 */