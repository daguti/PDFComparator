/*      */ package org.antlr.tool;
/*      */ 
/*      */ import antlr.RecognitionException;
/*      */ import antlr.Token;
/*      */ import antlr.TokenStreamException;
/*      */ import antlr.TokenStreamRewriteEngine;
/*      */ import antlr.TokenWithIndex;
/*      */ import antlr.collections.AST;
/*      */ import java.io.BufferedReader;
/*      */ import java.io.File;
/*      */ import java.io.FileNotFoundException;
/*      */ import java.io.FileReader;
/*      */ import java.io.IOException;
/*      */ import java.io.PrintStream;
/*      */ import java.io.Reader;
/*      */ import java.io.StreamTokenizer;
/*      */ import java.io.StringReader;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Collection;
/*      */ import java.util.HashMap;
/*      */ import java.util.HashSet;
/*      */ import java.util.Iterator;
/*      */ import java.util.LinkedHashMap;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.Set;
/*      */ import java.util.Vector;
/*      */ import org.antlr.Tool;
/*      */ import org.antlr.analysis.DFA;
/*      */ import org.antlr.analysis.DFAState;
/*      */ import org.antlr.analysis.DecisionProbe;
/*      */ import org.antlr.analysis.LL1Analyzer;
/*      */ import org.antlr.analysis.LL1DFA;
/*      */ import org.antlr.analysis.LookaheadSet;
/*      */ import org.antlr.analysis.NFA;
/*      */ import org.antlr.analysis.NFAConversionThread;
/*      */ import org.antlr.analysis.NFAState;
/*      */ import org.antlr.analysis.NFAToDFAConverter;
/*      */ import org.antlr.analysis.SemanticContext;
/*      */ import org.antlr.analysis.Transition;
/*      */ import org.antlr.codegen.CodeGenerator;
/*      */ import org.antlr.codegen.Target;
/*      */ import org.antlr.grammar.v2.ANTLRLexer;
/*      */ import org.antlr.grammar.v2.ANTLRParser;
/*      */ import org.antlr.grammar.v2.ANTLRTreePrinter;
/*      */ import org.antlr.grammar.v2.DefineGrammarItemsWalker;
/*      */ import org.antlr.grammar.v2.TreeToNFAConverter;
/*      */ import org.antlr.grammar.v3.ActionAnalysis;
/*      */ import org.antlr.misc.Barrier;
/*      */ import org.antlr.misc.IntSet;
/*      */ import org.antlr.misc.IntervalSet;
/*      */ import org.antlr.misc.MultiMap;
/*      */ import org.antlr.misc.OrderedHashSet;
/*      */ import org.antlr.misc.Utils;
/*      */ import org.antlr.stringtemplate.StringTemplate;
/*      */ import org.antlr.stringtemplate.language.AngleBracketTemplateLexer;
/*      */ 
/*      */ public class Grammar
/*      */ {
/*      */   public static final String SYNPRED_RULE_PREFIX = "synpred";
/*      */   public static final String GRAMMAR_FILE_EXTENSION = ".g";
/*      */   public static final String LEXER_GRAMMAR_FILE_EXTENSION = ".g";
/*      */   public static final int INITIAL_DECISION_LIST_SIZE = 300;
/*      */   public static final int INVALID_RULE_INDEX = -1;
/*      */   public static final int RULE_LABEL = 1;
/*      */   public static final int TOKEN_LABEL = 2;
/*      */   public static final int RULE_LIST_LABEL = 3;
/*      */   public static final int TOKEN_LIST_LABEL = 4;
/*      */   public static final int CHAR_LABEL = 5;
/*      */   public static final int WILDCARD_TREE_LABEL = 6;
/*      */   public static final int WILDCARD_TREE_LIST_LABEL = 7;
/*   70 */   public static String[] LabelTypeToString = { "<invalid>", "rule", "token", "rule-list", "token-list", "wildcard-tree", "wildcard-tree-list" };
/*      */   public static final String ARTIFICIAL_TOKENS_RULENAME = "Tokens";
/*      */   public static final String FRAGMENT_RULE_MODIFIER = "fragment";
/*      */   public static final String SYNPREDGATE_ACTION_NAME = "synpredgate";
/*   81 */   public static int[] ANTLRLiteralEscapedCharValue = new int['ÿ'];
/*      */ 
/*   85 */   public static String[] ANTLRLiteralCharValueEscape = new String['ÿ'];
/*      */   public static final int LEXER = 1;
/*      */   public static final int PARSER = 2;
/*      */   public static final int TREE_PARSER = 3;
/*      */   public static final int COMBINED = 4;
/*  109 */   public static final String[] grammarTypeToString = { "<invalid>", "lexer", "parser", "tree", "combined" };
/*      */ 
/*  117 */   public static final String[] grammarTypeToFileNameSuffix = { "<invalid>", "Lexer", "Parser", "", "Parser" };
/*      */ 
/*  130 */   public static MultiMap<Integer, Integer> validDelegations = new MultiMap() { } ;
/*      */   protected TokenStreamRewriteEngine tokenBuffer;
/*      */   public static final String IGNORE_STRING_IN_GRAMMAR_FILE_NAME = "__";
/*      */   public static final String AUTO_GENERATED_TOKEN_NAME_PREFIX = "T__";
/*      */   public String name;
/*      */   public int type;
/*      */   protected Map options;
/*  199 */   public static final Set legalLexerOptions = new HashSet() { } ;
/*      */ 
/*  212 */   public static final Set legalParserOptions = new HashSet() { } ;
/*      */ 
/*  225 */   public static final Set legalTreeParserOptions = new HashSet() { } ;
/*      */ 
/*  239 */   public static final Set doNotCopyOptionsToLexer = new HashSet() { } ;
/*      */ 
/*  247 */   public static final Map defaultOptions = new HashMap() { } ;
/*      */ 
/*  254 */   public static final Set legalBlockOptions = new HashSet() { } ;
/*      */ 
/*  258 */   public static final Map defaultBlockOptions = new HashMap() { } ;
/*      */ 
/*  261 */   public static final Map defaultLexerBlockOptions = new HashMap() { } ;
/*      */ 
/*  267 */   public static final Set legalTokenOptions = new HashSet() { } ;
/*      */   public static final String defaultTokenOption = "node";
/*  281 */   protected int global_k = -1;
/*      */ 
/*  289 */   protected Map<String, Map<String, GrammarAST>> actions = new HashMap();
/*      */   public NFA nfa;
/*      */   protected NFAFactory factory;
/*      */   public CompositeGrammar composite;
/*      */   public CompositeGrammarTree compositeTreeNode;
/*      */   public String label;
/*  315 */   protected IntSet charVocabulary = null;
/*      */ 
/*  320 */   Map lineColumnToLookaheadDFAMap = new HashMap();
/*      */   public Tool tool;
/*  327 */   protected Set<GrammarAST> ruleRefs = new HashSet();
/*      */ 
/*  329 */   protected Set<GrammarAST> scopedRuleRefs = new HashSet();
/*      */ 
/*  332 */   protected Set<Token> tokenIDRefs = new HashSet();
/*      */ 
/*  337 */   protected int decisionCount = 0;
/*      */   protected Set<Rule> leftRecursiveRules;
/*      */   protected boolean externalAnalysisAbort;
/*  351 */   public int numNonLLStar = 0;
/*      */   protected LinkedHashMap nameToSynpredASTMap;
/*      */   public boolean atLeastOneRuleMemoizes;
/*      */   public boolean atLeastOneBacktrackOption;
/*      */   public boolean implicitLexer;
/*  370 */   protected LinkedHashMap<String, Rule> nameToRuleMap = new LinkedHashMap();
/*      */ 
/*  375 */   public Set<String> overriddenRules = new HashSet();
/*      */ 
/*  384 */   protected Set<Rule> delegatedRuleReferences = new HashSet();
/*      */ 
/*  389 */   public List<String> lexerRuleNamesInCombined = new ArrayList();
/*      */ 
/*  394 */   protected Map scopes = new HashMap();
/*      */ 
/*  400 */   protected GrammarAST grammarTree = null;
/*      */ 
/*  406 */   protected Vector<Decision> indexToDecision = new Vector(300);
/*      */   protected CodeGenerator generator;
/*  414 */   public NameSpaceChecker nameSpaceChecker = new NameSpaceChecker(this);
/*      */ 
/*  416 */   public LL1Analyzer ll1Analyzer = new LL1Analyzer(this);
/*      */ 
/*  429 */   protected StringTemplate lexerGrammarST = new StringTemplate("lexer grammar <name>;\n<if(options)>options {\n  <options:{<it.name>=<it.value>;<\\n>}>\n}<\\n>\n<endif>\n<if(imports)>import <imports; separator=\", \">;<endif>\n<actionNames,actions:{n,a|@<n> {<a>}\n}>\n<literals:{<it.ruleName> : <it.literal> ;\n}>\n<rules>", AngleBracketTemplateLexer.class);
/*      */   protected String fileName;
/*      */   public long DFACreationWallClockTimeInMS;
/*  454 */   public int numberOfSemanticPredicates = 0;
/*  455 */   public int numberOfManualLookaheadOptions = 0;
/*  456 */   public Set<Integer> setOfNondeterministicDecisionNumbers = new HashSet();
/*  457 */   public Set<Integer> setOfNondeterministicDecisionNumbersResolvedWithPredicates = new HashSet();
/*      */ 
/*  463 */   public Set<GrammarAST> blocksWithSynPreds = new HashSet();
/*      */ 
/*  468 */   public Set<DFA> decisionsWhoseDFAsUsesSynPreds = new HashSet();
/*      */ 
/*  476 */   public Set<String> synPredNamesUsedInDFA = new HashSet();
/*      */ 
/*  481 */   public Set<GrammarAST> blocksWithSemPreds = new HashSet();
/*      */ 
/*  484 */   public Set<DFA> decisionsWhoseDFAsUsesSemPreds = new HashSet();
/*      */ 
/*  486 */   protected boolean allDecisionDFACreated = false;
/*      */ 
/*  493 */   protected boolean builtFromString = false;
/*      */ 
/*  496 */   GrammarSanity sanity = new GrammarSanity(this);
/*      */   Target target;
/*      */ 
/*      */   public Grammar(Tool tool, String fileName, CompositeGrammar composite)
/*      */   {
/*  503 */     this.composite = composite;
/*  504 */     setTool(tool);
/*  505 */     setFileName(fileName);
/*      */ 
/*  507 */     if (composite.delegateGrammarTreeRoot == null) {
/*  508 */       composite.setDelegationRoot(this);
/*      */     }
/*  510 */     this.target = CodeGenerator.loadLanguageTarget((String)getOption("language"));
/*      */   }
/*      */ 
/*      */   public Grammar()
/*      */   {
/*  517 */     this.builtFromString = true;
/*  518 */     this.composite = new CompositeGrammar(this);
/*  519 */     this.target = CodeGenerator.loadLanguageTarget((String)getOption("language"));
/*      */   }
/*      */ 
/*      */   public Grammar(String grammarString)
/*      */     throws RecognitionException, TokenStreamException
/*      */   {
/*  526 */     this(null, grammarString);
/*      */   }
/*      */ 
/*      */   public Grammar(Tool tool, String grammarString)
/*      */     throws RecognitionException
/*      */   {
/*  535 */     this();
/*  536 */     setTool(tool);
/*  537 */     setFileName("<string>");
/*  538 */     StringReader r = new StringReader(grammarString);
/*  539 */     parseAndBuildAST(r);
/*  540 */     this.composite.assignTokenTypes();
/*  541 */     defineGrammarSymbols();
/*  542 */     checkNameSpaceAndActions();
/*      */   }
/*      */ 
/*      */   public void setFileName(String fileName) {
/*  546 */     this.fileName = fileName;
/*      */   }
/*      */ 
/*      */   public String getFileName() {
/*  550 */     return this.fileName;
/*      */   }
/*      */ 
/*      */   public void setName(String name) {
/*  554 */     if (name == null) {
/*  555 */       return;
/*      */     }
/*      */ 
/*  558 */     String saneFile = this.fileName.replace('\\', '/');
/*  559 */     int lastSlash = saneFile.lastIndexOf('/');
/*  560 */     String onlyFileName = saneFile.substring(lastSlash + 1, this.fileName.length());
/*  561 */     if (!this.builtFromString) {
/*  562 */       int lastDot = onlyFileName.lastIndexOf('.');
/*  563 */       String onlyFileNameNoSuffix = null;
/*  564 */       if (lastDot < 0) {
/*  565 */         ErrorManager.error(9, this.fileName);
/*  566 */         onlyFileNameNoSuffix = onlyFileName + ".g";
/*      */       }
/*      */       else {
/*  569 */         onlyFileNameNoSuffix = onlyFileName.substring(0, lastDot);
/*      */       }
/*  571 */       if (!name.equals(onlyFileNameNoSuffix)) {
/*  572 */         ErrorManager.error(8, name, this.fileName);
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/*  577 */     this.name = name;
/*      */   }
/*      */ 
/*      */   public void setGrammarContent(String grammarString) throws RecognitionException {
/*  581 */     StringReader r = new StringReader(grammarString);
/*  582 */     parseAndBuildAST(r);
/*  583 */     this.composite.assignTokenTypes();
/*  584 */     this.composite.defineGrammarSymbols();
/*      */   }
/*      */ 
/*      */   public void parseAndBuildAST()
/*      */     throws IOException
/*      */   {
/*  590 */     FileReader fr = null;
/*  591 */     BufferedReader br = null;
/*      */     try {
/*  593 */       fr = new FileReader(this.fileName);
/*  594 */       br = new BufferedReader(fr);
/*  595 */       parseAndBuildAST(br);
/*  596 */       br.close();
/*  597 */       br = null;
/*      */     }
/*      */     finally {
/*  600 */       if (br != null)
/*  601 */         br.close();
/*      */     }
/*      */   }
/*      */ 
/*      */   public void parseAndBuildAST(Reader r)
/*      */   {
/*  608 */     ANTLRLexer lexer = new ANTLRLexer(r);
/*  609 */     lexer.setFilename(getFileName());
/*      */ 
/*  613 */     lexer.setTokenObjectClass("antlr.TokenWithIndex");
/*  614 */     this.tokenBuffer = new TokenStreamRewriteEngine(lexer);
/*  615 */     this.tokenBuffer.discard(85);
/*  616 */     this.tokenBuffer.discard(88);
/*  617 */     this.tokenBuffer.discard(86);
/*  618 */     this.tokenBuffer.discard(87);
/*  619 */     ANTLRParser parser = new ANTLRParser(this.tokenBuffer);
/*  620 */     parser.setFilename(getFileName());
/*      */     try {
/*  622 */       parser.grammar(this);
/*      */     }
/*      */     catch (TokenStreamException tse) {
/*  625 */       ErrorManager.internalError("unexpected stream error from parsing " + this.fileName, tse);
/*      */     }
/*      */     catch (RecognitionException re) {
/*  628 */       ErrorManager.internalError("unexpected parser recognition error from " + this.fileName, re);
/*      */     }
/*      */ 
/*  631 */     dealWithTreeFilterMode();
/*      */ 
/*  633 */     if ((lexer.hasASTOperator) && (!buildAST())) {
/*  634 */       Object value = getOption("output");
/*  635 */       if (value == null) {
/*  636 */         ErrorManager.grammarWarning(149, this, null);
/*      */ 
/*  638 */         setOption("output", "AST", null);
/*      */       }
/*      */       else {
/*  641 */         ErrorManager.grammarError(164, this, null, value);
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/*  646 */     this.grammarTree = ((GrammarAST)parser.getAST());
/*  647 */     setFileName(lexer.getFilename());
/*  648 */     if ((this.grammarTree == null) || (this.grammarTree.findFirstType(8) == null)) {
/*  649 */       ErrorManager.error(150, getFileName());
/*  650 */       return;
/*      */     }
/*      */ 
/*  654 */     List synpredRules = getArtificialRulesForSyntacticPredicates(parser, this.nameToSynpredASTMap);
/*      */ 
/*  657 */     for (int i = 0; i < synpredRules.size(); i++) {
/*  658 */       GrammarAST rAST = (GrammarAST)synpredRules.get(i);
/*  659 */       this.grammarTree.addChild(rAST);
/*      */     }
/*      */   }
/*      */ 
/*      */   protected void dealWithTreeFilterMode() {
/*  664 */     Object filterMode = (String)getOption("filter");
/*  665 */     if ((this.type == 3) && (filterMode != null) && (filterMode.toString().equals("true")))
/*      */     {
/*  671 */       Object backtrack = (String)getOption("backtrack");
/*  672 */       Object output = getOption("output");
/*  673 */       Object rewrite = getOption("rewrite");
/*  674 */       if ((backtrack != null) && (!backtrack.toString().equals("true"))) {
/*  675 */         ErrorManager.error(167, "backtrack", backtrack);
/*      */       }
/*      */ 
/*  678 */       if ((output != null) && (!output.toString().equals("AST"))) {
/*  679 */         ErrorManager.error(167, "output", output);
/*      */ 
/*  681 */         setOption("output", "", null);
/*      */       }
/*  683 */       if ((rewrite != null) && (!rewrite.toString().equals("true"))) {
/*  684 */         ErrorManager.error(167, "rewrite", rewrite);
/*      */       }
/*      */ 
/*  688 */       setOption("backtrack", "true", null);
/*  689 */       if ((output != null) && (output.toString().equals("AST")))
/*  690 */         setOption("rewrite", "true", null);
/*      */     }
/*      */   }
/*      */ 
/*      */   public void defineGrammarSymbols()
/*      */   {
/*  698 */     if (Tool.internalOption_PrintGrammarTree) {
/*  699 */       System.out.println(this.grammarTree.toStringList());
/*      */     }
/*      */ 
/*  704 */     DefineGrammarItemsWalker defineItemsWalker = new DefineGrammarItemsWalker();
/*  705 */     defineItemsWalker.setASTNodeClass("org.antlr.tool.GrammarAST");
/*      */     try {
/*  707 */       defineItemsWalker.grammar(this.grammarTree, this);
/*      */     }
/*      */     catch (RecognitionException re) {
/*  710 */       ErrorManager.error(15, re);
/*      */     }
/*      */   }
/*      */ 
/*      */   public void checkNameSpaceAndActions()
/*      */   {
/*  717 */     examineAllExecutableActions();
/*  718 */     checkAllRulesForUselessLabels();
/*      */ 
/*  720 */     this.nameSpaceChecker.checkConflicts();
/*      */   }
/*      */ 
/*      */   public boolean validImport(Grammar delegate)
/*      */   {
/*  725 */     List validDelegators = (List)validDelegations.get(new Integer(delegate.type));
/*  726 */     return (validDelegators != null) && (validDelegators.contains(new Integer(this.type)));
/*      */   }
/*      */ 
/*      */   public String getLexerGrammar()
/*      */   {
/*  733 */     if ((this.lexerGrammarST.getAttribute("literals") == null) && (this.lexerGrammarST.getAttribute("rules") == null))
/*      */     {
/*  737 */       return null;
/*      */     }
/*  739 */     this.lexerGrammarST.setAttribute("name", this.name);
/*      */ 
/*  741 */     if (this.actions.get("lexer") != null) {
/*  742 */       this.lexerGrammarST.setAttribute("actionNames", ((Map)this.actions.get("lexer")).keySet());
/*      */ 
/*  744 */       this.lexerGrammarST.setAttribute("actions", ((Map)this.actions.get("lexer")).values());
/*      */     }
/*      */ 
/*  748 */     if (this.options != null) {
/*  749 */       Iterator optionNames = this.options.keySet().iterator();
/*  750 */       while (optionNames.hasNext()) {
/*  751 */         String optionName = (String)optionNames.next();
/*  752 */         if (!doNotCopyOptionsToLexer.contains(optionName)) {
/*  753 */           Object value = this.options.get(optionName);
/*  754 */           this.lexerGrammarST.setAttribute("options.{name,value}", optionName, value);
/*      */         }
/*      */       }
/*      */     }
/*  758 */     return this.lexerGrammarST.toString();
/*      */   }
/*      */ 
/*      */   public String getImplicitlyGeneratedLexerFileName() {
/*  762 */     return this.name + "__" + ".g";
/*      */   }
/*      */ 
/*      */   public String getRecognizerName()
/*      */   {
/*  773 */     String suffix = "";
/*  774 */     List grammarsFromRootToMe = this.composite.getDelegators(this);
/*      */ 
/*  776 */     String qualifiedName = this.name;
/*  777 */     if (grammarsFromRootToMe != null) {
/*  778 */       StringBuffer buf = new StringBuffer();
/*  779 */       for (Iterator i$ = grammarsFromRootToMe.iterator(); i$.hasNext(); ) { Grammar g = (Grammar)i$.next();
/*  780 */         buf.append(g.name);
/*  781 */         buf.append('_');
/*      */       }
/*  783 */       buf.append(this.name);
/*  784 */       qualifiedName = buf.toString();
/*      */     }
/*  786 */     if ((this.type == 4) || ((this.type == 1) && (this.implicitLexer)))
/*      */     {
/*  789 */       suffix = grammarTypeToFileNameSuffix[this.type];
/*      */     }
/*  791 */     return qualifiedName + suffix;
/*      */   }
/*      */ 
/*      */   public GrammarAST addArtificialMatchTokensRule(GrammarAST grammarAST, List<String> ruleNames, List<String> delegateNames, boolean filterMode)
/*      */   {
/*  813 */     StringTemplate matchTokenRuleST = null;
/*  814 */     if (filterMode) {
/*  815 */       matchTokenRuleST = new StringTemplate("Tokens options {k=1; backtrack=true;} : <rules; separator=\"|\">;", AngleBracketTemplateLexer.class);
/*      */     }
/*      */     else
/*      */     {
/*  821 */       matchTokenRuleST = new StringTemplate("Tokens : <rules; separator=\"|\">;", AngleBracketTemplateLexer.class);
/*      */     }
/*      */ 
/*  827 */     for (int i = 0; i < ruleNames.size(); i++) {
/*  828 */       String rname = (String)ruleNames.get(i);
/*  829 */       matchTokenRuleST.setAttribute("rules", rname);
/*      */     }
/*  831 */     for (int i = 0; i < delegateNames.size(); i++) {
/*  832 */       String dname = (String)delegateNames.get(i);
/*  833 */       matchTokenRuleST.setAttribute("rules", dname + ".Tokens");
/*      */     }
/*      */ 
/*  837 */     ANTLRLexer lexer = new ANTLRLexer(new StringReader(matchTokenRuleST.toString()));
/*  838 */     lexer.setTokenObjectClass("antlr.TokenWithIndex");
/*  839 */     TokenStreamRewriteEngine tokbuf = new TokenStreamRewriteEngine(lexer);
/*      */ 
/*  841 */     tokbuf.discard(85);
/*  842 */     tokbuf.discard(88);
/*  843 */     tokbuf.discard(86);
/*  844 */     tokbuf.discard(87);
/*  845 */     ANTLRParser parser = new ANTLRParser(tokbuf);
/*  846 */     parser.setGrammar(this);
/*  847 */     parser.setGtype(25);
/*  848 */     parser.setASTNodeClass("org.antlr.tool.GrammarAST");
/*      */     try {
/*  850 */       parser.rule();
/*  851 */       if (Tool.internalOption_PrintGrammarTree) {
/*  852 */         System.out.println("Tokens rule: " + parser.getAST().toStringTree());
/*      */       }
/*  854 */       GrammarAST p = grammarAST;
/*  855 */       while (p.getType() != 25) {
/*  856 */         p = (GrammarAST)p.getNextSibling();
/*      */       }
/*  858 */       p.addChild(parser.getAST());
/*      */     }
/*      */     catch (Exception e) {
/*  861 */       ErrorManager.error(12, e);
/*      */     }
/*      */ 
/*  864 */     return (GrammarAST)parser.getAST();
/*      */   }
/*      */ 
/*      */   protected List getArtificialRulesForSyntacticPredicates(ANTLRParser parser, LinkedHashMap nameToSynpredASTMap)
/*      */   {
/*  873 */     List rules = new ArrayList();
/*  874 */     if (nameToSynpredASTMap == null) {
/*  875 */       return rules;
/*      */     }
/*  877 */     Set predNames = nameToSynpredASTMap.keySet();
/*  878 */     boolean isLexer = this.grammarTree.getType() == 25;
/*  879 */     for (Iterator it = predNames.iterator(); it.hasNext(); ) {
/*  880 */       String synpredName = (String)it.next();
/*  881 */       GrammarAST fragmentAST = (GrammarAST)nameToSynpredASTMap.get(synpredName);
/*      */ 
/*  883 */       GrammarAST ruleAST = parser.createSimpleRuleAST(synpredName, fragmentAST, isLexer);
/*      */ 
/*  887 */       rules.add(ruleAST);
/*      */     }
/*  889 */     return rules;
/*      */   }
/*      */ 
/*      */   public void createRuleStartAndStopNFAStates()
/*      */   {
/*  917 */     if (this.nfa != null) {
/*  918 */       return;
/*      */     }
/*  920 */     this.nfa = new NFA(this);
/*  921 */     this.factory = new NFAFactory(this.nfa);
/*      */ 
/*  923 */     Collection rules = getRules();
/*  924 */     for (Iterator itr = rules.iterator(); itr.hasNext(); ) {
/*  925 */       Rule r = (Rule)itr.next();
/*  926 */       String ruleName = r.name;
/*  927 */       NFAState ruleBeginState = this.factory.newState();
/*  928 */       ruleBeginState.setDescription("rule " + ruleName + " start");
/*  929 */       ruleBeginState.enclosingRule = r;
/*  930 */       r.startState = ruleBeginState;
/*  931 */       NFAState ruleEndState = this.factory.newState();
/*  932 */       ruleEndState.setDescription("rule " + ruleName + " end");
/*  933 */       ruleEndState.setAcceptState(true);
/*  934 */       ruleEndState.enclosingRule = r;
/*  935 */       r.stopState = ruleEndState;
/*      */     }
/*      */   }
/*      */ 
/*      */   public void buildNFA() {
/*  940 */     if (this.nfa == null) {
/*  941 */       createRuleStartAndStopNFAStates();
/*      */     }
/*  943 */     if (this.nfa.complete)
/*      */     {
/*  945 */       return;
/*      */     }
/*      */ 
/*  948 */     if (getRules().size() == 0) {
/*  949 */       return;
/*      */     }
/*      */ 
/*  952 */     TreeToNFAConverter nfaBuilder = new TreeToNFAConverter(this, this.nfa, this.factory);
/*      */     try {
/*  954 */       nfaBuilder.grammar(this.grammarTree);
/*      */     }
/*      */     catch (RecognitionException re) {
/*  957 */       ErrorManager.error(15, this.name, re);
/*      */     }
/*      */ 
/*  961 */     this.nfa.complete = true;
/*      */   }
/*      */ 
/*      */   public void createLookaheadDFAs()
/*      */   {
/*  976 */     createLookaheadDFAs(true);
/*      */   }
/*      */ 
/*      */   public void createLookaheadDFAs(boolean wackTempStructures) {
/*  980 */     if (this.nfa == null) {
/*  981 */       buildNFA();
/*      */     }
/*      */ 
/*  985 */     checkAllRulesForLeftRecursion();
/*      */ 
/*  994 */     long start = System.currentTimeMillis();
/*      */ 
/*  997 */     int numDecisions = getNumberOfDecisions();
/*  998 */     if (NFAToDFAConverter.SINGLE_THREADED_NFA_CONVERSION) {
/*  999 */       for (int decision = 1; decision <= numDecisions; decision++) {
/* 1000 */         NFAState decisionStartState = getDecisionNFAStartState(decision);
/* 1001 */         if (this.leftRecursiveRules.contains(decisionStartState.enclosingRule))
/*      */         {
/* 1003 */           if (this.composite.watchNFAConversion) {
/* 1004 */             System.out.println("ignoring decision " + decision + " within left-recursive rule " + decisionStartState.enclosingRule.name);
/*      */           }
/*      */ 
/*      */         }
/* 1009 */         else if ((!this.externalAnalysisAbort) && (decisionStartState.getNumberOfTransitions() > 1)) {
/* 1010 */           Rule r = decisionStartState.enclosingRule;
/* 1011 */           if ((!r.isSynPred) || (this.synPredNamesUsedInDFA.contains(r.name)))
/*      */           {
/* 1014 */             DFA dfa = null;
/*      */ 
/* 1016 */             if ((getUserMaxLookahead(decision) == 0) || (getUserMaxLookahead(decision) == 1))
/*      */             {
/* 1019 */               dfa = createLL_1_LookaheadDFA(decision);
/*      */             }
/* 1021 */             if (dfa == null) {
/* 1022 */               if (this.composite.watchNFAConversion) {
/* 1023 */                 System.out.println("decision " + decision + " not suitable for LL(1)-optimized DFA analysis");
/*      */               }
/*      */ 
/* 1026 */               dfa = createLookaheadDFA(decision, wackTempStructures);
/*      */             }
/* 1028 */             if (dfa.startState == null)
/*      */             {
/* 1030 */               setLookaheadDFA(decision, null);
/*      */             }
/* 1032 */             if (Tool.internalOption_PrintDFA) {
/* 1033 */               System.out.println("DFA d=" + decision);
/* 1034 */               FASerializer serializer = new FASerializer(this.nfa.grammar);
/* 1035 */               String result = serializer.serialize(dfa.startState);
/* 1036 */               System.out.println(result);
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*      */     } else {
/* 1042 */       ErrorManager.info("two-threaded DFA conversion");
/*      */ 
/* 1044 */       Barrier barrier = new Barrier(3);
/*      */ 
/* 1046 */       int midpoint = numDecisions / 2;
/* 1047 */       NFAConversionThread t1 = new NFAConversionThread(this, barrier, 1, midpoint);
/*      */ 
/* 1049 */       new Thread(t1).start();
/* 1050 */       if (midpoint == numDecisions / 2) {
/* 1051 */         midpoint++;
/*      */       }
/* 1053 */       NFAConversionThread t2 = new NFAConversionThread(this, barrier, midpoint, numDecisions);
/*      */ 
/* 1055 */       new Thread(t2).start();
/*      */       try
/*      */       {
/* 1058 */         barrier.waitForRelease();
/*      */       }
/*      */       catch (InterruptedException e) {
/* 1061 */         ErrorManager.internalError("what the hell? DFA interruptus", e);
/*      */       }
/*      */     }
/*      */ 
/* 1065 */     long stop = System.currentTimeMillis();
/* 1066 */     this.DFACreationWallClockTimeInMS = (stop - start);
/*      */ 
/* 1069 */     this.allDecisionDFACreated = true;
/*      */   }
/*      */ 
/*      */   public DFA createLL_1_LookaheadDFA(int decision) {
/* 1073 */     Decision d = getDecision(decision);
/* 1074 */     String enclosingRule = d.startState.enclosingRule.name;
/* 1075 */     Rule r = d.startState.enclosingRule;
/* 1076 */     NFAState decisionStartState = getDecisionNFAStartState(decision);
/*      */ 
/* 1078 */     if (this.composite.watchNFAConversion) {
/* 1079 */       System.out.println("--------------------\nattempting LL(1) DFA (d=" + decisionStartState.getDecisionNumber() + ") for " + decisionStartState.getDescription());
/*      */     }
/*      */ 
/* 1084 */     if ((r.isSynPred) && (!this.synPredNamesUsedInDFA.contains(enclosingRule))) {
/* 1085 */       return null;
/*      */     }
/*      */ 
/* 1089 */     int numAlts = getNumberOfAltsForDecisionNFA(decisionStartState);
/* 1090 */     LookaheadSet[] altLook = new LookaheadSet[numAlts + 1];
/* 1091 */     for (int alt = 1; alt <= numAlts; alt++) {
/* 1092 */       int walkAlt = decisionStartState.translateDisplayAltToWalkAlt(alt);
/*      */ 
/* 1094 */       NFAState altLeftEdge = getNFAStateForAltOfDecision(decisionStartState, walkAlt);
/* 1095 */       NFAState altStartState = (NFAState)altLeftEdge.transition[0].target;
/*      */ 
/* 1097 */       altLook[alt] = this.ll1Analyzer.LOOK(altStartState);
/*      */     }
/*      */ 
/* 1102 */     boolean decisionIsLL_1 = true;
/*      */ 
/* 1104 */     for (int i = 1; i <= numAlts; i++) {
/* 1105 */       for (int j = i + 1; j <= numAlts; j++)
/*      */       {
/* 1111 */         LookaheadSet collision = altLook[i].intersection(altLook[j]);
/* 1112 */         if (!collision.isNil())
/*      */         {
/* 1114 */           decisionIsLL_1 = false;
/* 1115 */           break label258;
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/* 1120 */     label258: boolean foundConfoundingPredicate = this.ll1Analyzer.detectConfoundingPredicates(decisionStartState);
/*      */ 
/* 1122 */     if ((decisionIsLL_1) && (!foundConfoundingPredicate))
/*      */     {
/* 1124 */       if (NFAToDFAConverter.debug) {
/* 1125 */         System.out.println("decision " + decision + " is simple LL(1)");
/*      */       }
/* 1127 */       DFA lookaheadDFA = new LL1DFA(decision, decisionStartState, altLook);
/* 1128 */       setLookaheadDFA(decision, lookaheadDFA);
/* 1129 */       updateLineColumnToLookaheadDFAMap(lookaheadDFA);
/* 1130 */       return lookaheadDFA;
/*      */     }
/*      */ 
/* 1146 */     if ((getUserMaxLookahead(decision) != 1) || (!getAutoBacktrackMode(decision)) || (foundConfoundingPredicate))
/*      */     {
/* 1151 */       return null;
/*      */     }
/*      */ 
/* 1154 */     List edges = new ArrayList();
/* 1155 */     for (int i = 1; i < altLook.length; i++) {
/* 1156 */       LookaheadSet s = altLook[i];
/* 1157 */       edges.add(s.tokenTypeSet);
/*      */     }
/* 1159 */     List disjoint = makeEdgeSetsDisjoint(edges);
/*      */ 
/* 1162 */     MultiMap edgeMap = new MultiMap();
/* 1163 */     for (int i = 0; i < disjoint.size(); i++) {
/* 1164 */       IntervalSet ds = (IntervalSet)disjoint.get(i);
/* 1165 */       for (int alt = 1; alt < altLook.length; alt++) {
/* 1166 */         LookaheadSet look = altLook[alt];
/* 1167 */         if (!ds.and(look.tokenTypeSet).isNil()) {
/* 1168 */           edgeMap.map(ds, new Integer(alt));
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 1177 */     DFA lookaheadDFA = new LL1DFA(decision, decisionStartState, edgeMap);
/* 1178 */     setLookaheadDFA(decision, lookaheadDFA);
/*      */ 
/* 1181 */     updateLineColumnToLookaheadDFAMap(lookaheadDFA);
/*      */ 
/* 1183 */     return lookaheadDFA;
/*      */   }
/*      */ 
/*      */   private void updateLineColumnToLookaheadDFAMap(DFA lookaheadDFA) {
/* 1187 */     GrammarAST decisionAST = this.nfa.grammar.getDecisionBlockAST(lookaheadDFA.decisionNumber);
/* 1188 */     int line = decisionAST.getLine();
/* 1189 */     int col = decisionAST.getColumn();
/* 1190 */     this.lineColumnToLookaheadDFAMap.put(new StringBuffer().append(line).append(":").toString() + col, lookaheadDFA);
/*      */   }
/*      */ 
/*      */   protected List<IntervalSet> makeEdgeSetsDisjoint(List<IntervalSet> edges)
/*      */   {
/* 1195 */     OrderedHashSet disjointSets = new OrderedHashSet();
/*      */ 
/* 1197 */     int numEdges = edges.size();
/* 1198 */     for (int e = 0; e < numEdges; e++) {
/* 1199 */       IntervalSet t = (IntervalSet)edges.get(e);
/* 1200 */       if (!disjointSets.contains(t))
/*      */       {
/* 1205 */         IntervalSet remainder = t;
/* 1206 */         int numDisjointElements = disjointSets.size();
/* 1207 */         for (int i = 0; i < numDisjointElements; i++) {
/* 1208 */           IntervalSet s_i = (IntervalSet)disjointSets.get(i);
/*      */ 
/* 1210 */           if (!t.and(s_i).isNil())
/*      */           {
/* 1220 */             IntervalSet intersection = (IntervalSet)s_i.and(t);
/* 1221 */             disjointSets.set(i, intersection);
/*      */ 
/* 1224 */             IntSet existingMinusNewElements = s_i.subtract(t);
/*      */ 
/* 1226 */             if (!existingMinusNewElements.isNil())
/*      */             {
/* 1229 */               disjointSets.add(existingMinusNewElements);
/*      */             }
/*      */ 
/* 1233 */             remainder = (IntervalSet)t.subtract(s_i);
/* 1234 */             if (remainder.isNil())
/*      */             {
/*      */               break;
/*      */             }
/* 1238 */             t = remainder;
/*      */           }
/*      */         }
/* 1240 */         if (!remainder.isNil())
/* 1241 */           disjointSets.add(remainder);
/*      */       }
/*      */     }
/* 1244 */     return disjointSets.elements();
/*      */   }
/*      */ 
/*      */   public DFA createLookaheadDFA(int decision, boolean wackTempStructures) {
/* 1248 */     Decision d = getDecision(decision);
/* 1249 */     String enclosingRule = d.startState.enclosingRule.name;
/* 1250 */     Rule r = d.startState.enclosingRule;
/*      */ 
/* 1253 */     NFAState decisionStartState = getDecisionNFAStartState(decision);
/* 1254 */     long startDFA = 0L; long stopDFA = 0L;
/* 1255 */     if (this.composite.watchNFAConversion) {
/* 1256 */       System.out.println("--------------------\nbuilding lookahead DFA (d=" + decisionStartState.getDecisionNumber() + ") for " + decisionStartState.getDescription());
/*      */ 
/* 1259 */       startDFA = System.currentTimeMillis();
/*      */     }
/*      */ 
/* 1262 */     DFA lookaheadDFA = new DFA(decision, decisionStartState);
/*      */ 
/* 1265 */     boolean failed = (lookaheadDFA.probe.isNonLLStarDecision()) || (lookaheadDFA.probe.analysisOverflowed());
/*      */ 
/* 1268 */     if ((failed) && (lookaheadDFA.okToRetryDFAWithK1()))
/*      */     {
/* 1271 */       this.decisionsWhoseDFAsUsesSynPreds.remove(lookaheadDFA);
/*      */ 
/* 1273 */       d.blockAST.setBlockOption(this, "k", Utils.integer(1));
/* 1274 */       if (this.composite.watchNFAConversion) {
/* 1275 */         System.out.print("trying decision " + decision + " again with k=1; reason: " + lookaheadDFA.getReasonForFailure());
/*      */       }
/*      */ 
/* 1279 */       lookaheadDFA = null;
/* 1280 */       lookaheadDFA = new DFA(decision, decisionStartState);
/*      */     }
/*      */ 
/* 1283 */     setLookaheadDFA(decision, lookaheadDFA);
/*      */     Iterator i$;
/* 1285 */     if (wackTempStructures) {
/* 1286 */       for (i$ = lookaheadDFA.getUniqueStates().values().iterator(); i$.hasNext(); ) { DFAState s = (DFAState)i$.next();
/* 1287 */         s.reset();
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 1292 */     updateLineColumnToLookaheadDFAMap(lookaheadDFA);
/*      */ 
/* 1294 */     if (this.composite.watchNFAConversion) {
/* 1295 */       stopDFA = System.currentTimeMillis();
/* 1296 */       System.out.println("cost: " + lookaheadDFA.getNumberOfStates() + " states, " + (int)(stopDFA - startDFA) + " ms");
/*      */     }
/*      */ 
/* 1300 */     return lookaheadDFA;
/*      */   }
/*      */ 
/*      */   public void externallyAbortNFAToDFAConversion()
/*      */   {
/* 1306 */     this.externalAnalysisAbort = true;
/*      */   }
/*      */ 
/*      */   public boolean NFAToDFAConversionExternallyAborted() {
/* 1310 */     return this.externalAnalysisAbort;
/*      */   }
/*      */ 
/*      */   public int getNewTokenType()
/*      */   {
/* 1315 */     this.composite.maxTokenType += 1;
/* 1316 */     return this.composite.maxTokenType;
/*      */   }
/*      */ 
/*      */   public void defineToken(String text, int tokenType)
/*      */   {
/* 1325 */     if (this.composite.tokenIDToTypeMap.get(text) != null)
/*      */     {
/* 1328 */       return;
/*      */     }
/*      */ 
/* 1332 */     if (text.charAt(0) == '\'') {
/* 1333 */       this.composite.stringLiteralToTypeMap.put(text, Utils.integer(tokenType));
/*      */ 
/* 1335 */       if (tokenType >= this.composite.typeToStringLiteralList.size()) {
/* 1336 */         this.composite.typeToStringLiteralList.setSize(tokenType + 1);
/*      */       }
/* 1338 */       this.composite.typeToStringLiteralList.set(tokenType, text);
/*      */     }
/*      */     else {
/* 1341 */       this.composite.tokenIDToTypeMap.put(text, Utils.integer(tokenType));
/*      */     }
/* 1343 */     int index = 7 + tokenType - 1;
/*      */ 
/* 1345 */     this.composite.maxTokenType = Math.max(this.composite.maxTokenType, tokenType);
/* 1346 */     if (index >= this.composite.typeToTokenList.size()) {
/* 1347 */       this.composite.typeToTokenList.setSize(index + 1);
/*      */     }
/* 1349 */     String prevToken = (String)this.composite.typeToTokenList.get(index);
/* 1350 */     if ((prevToken == null) || (prevToken.charAt(0) == '\''))
/*      */     {
/* 1352 */       this.composite.typeToTokenList.set(index, text);
/*      */     }
/*      */   }
/*      */ 
/*      */   public void defineRule(Token ruleToken, String modifier, Map options, GrammarAST tree, GrammarAST argActionAST, int numAlts)
/*      */   {
/* 1366 */     String ruleName = ruleToken.getText();
/* 1367 */     if (getLocallyDefinedRule(ruleName) != null) {
/* 1368 */       ErrorManager.grammarError(101, this, ruleToken, ruleName);
/*      */ 
/* 1370 */       return;
/*      */     }
/*      */ 
/* 1373 */     if (((this.type == 2) || (this.type == 3)) && (Character.isUpperCase(ruleName.charAt(0))))
/*      */     {
/* 1376 */       ErrorManager.grammarError(102, this, ruleToken, ruleName);
/*      */ 
/* 1378 */       return;
/*      */     }
/*      */ 
/* 1381 */     Rule r = new Rule(this, ruleName, this.composite.ruleIndex, numAlts);
/*      */ 
/* 1386 */     r.modifier = modifier;
/* 1387 */     this.nameToRuleMap.put(ruleName, r);
/* 1388 */     setRuleAST(ruleName, tree);
/* 1389 */     r.setOptions(options, ruleToken);
/* 1390 */     r.argActionAST = argActionAST;
/* 1391 */     this.composite.ruleIndexToRuleList.setSize(this.composite.ruleIndex + 1);
/* 1392 */     this.composite.ruleIndexToRuleList.set(this.composite.ruleIndex, r);
/* 1393 */     this.composite.ruleIndex += 1;
/* 1394 */     if (ruleName.startsWith("synpred"))
/* 1395 */       r.isSynPred = true;
/*      */   }
/*      */ 
/*      */   public String defineSyntacticPredicate(GrammarAST blockAST, String currentRuleName)
/*      */   {
/* 1405 */     if (this.nameToSynpredASTMap == null) {
/* 1406 */       this.nameToSynpredASTMap = new LinkedHashMap();
/*      */     }
/* 1408 */     String predName = "synpred" + (this.nameToSynpredASTMap.size() + 1) + "_" + this.name;
/*      */ 
/* 1410 */     blockAST.setTreeEnclosingRuleNameDeeply(predName);
/* 1411 */     this.nameToSynpredASTMap.put(predName, blockAST);
/* 1412 */     return predName;
/*      */   }
/*      */ 
/*      */   public LinkedHashMap getSyntacticPredicates() {
/* 1416 */     return this.nameToSynpredASTMap;
/*      */   }
/*      */ 
/*      */   public GrammarAST getSyntacticPredicate(String name) {
/* 1420 */     if (this.nameToSynpredASTMap == null) {
/* 1421 */       return null;
/*      */     }
/* 1423 */     return (GrammarAST)this.nameToSynpredASTMap.get(name);
/*      */   }
/*      */ 
/*      */   public void synPredUsedInDFA(DFA dfa, SemanticContext semCtx) {
/* 1427 */     this.decisionsWhoseDFAsUsesSynPreds.add(dfa);
/* 1428 */     semCtx.trackUseOfSyntacticPredicates(this);
/*      */   }
/*      */ 
/*      */   public void defineNamedAction(GrammarAST ampersandAST, String scope, GrammarAST nameAST, GrammarAST actionAST)
/*      */   {
/* 1446 */     if (scope == null) {
/* 1447 */       scope = getDefaultActionScope(this.type);
/*      */     }
/*      */ 
/* 1450 */     String actionName = nameAST.getText();
/* 1451 */     Map scopeActions = (Map)this.actions.get(scope);
/* 1452 */     if (scopeActions == null) {
/* 1453 */       scopeActions = new HashMap();
/* 1454 */       this.actions.put(scope, scopeActions);
/*      */     }
/* 1456 */     GrammarAST a = (GrammarAST)scopeActions.get(actionName);
/* 1457 */     if (a != null) {
/* 1458 */       ErrorManager.grammarError(144, this, nameAST.getToken(), nameAST.getText());
/*      */     }
/*      */     else
/*      */     {
/* 1463 */       scopeActions.put(actionName, actionAST);
/*      */     }
/*      */     Iterator i$;
/* 1466 */     if ((this == this.composite.getRootGrammar()) && (actionName.equals("header"))) {
/* 1467 */       List allgrammars = this.composite.getRootGrammar().getDelegates();
/* 1468 */       for (i$ = allgrammars.iterator(); i$.hasNext(); ) { Grammar delegate = (Grammar)i$.next();
/* 1469 */         if (this.target.isValidActionScope(delegate.type, scope))
/*      */         {
/* 1471 */           delegate.defineNamedAction(ampersandAST, scope, nameAST, actionAST);
/*      */         } }
/*      */     }
/*      */   }
/*      */ 
/*      */   public void setSynPredGateIfNotAlready(StringTemplate gateST)
/*      */   {
/* 1478 */     String scope = getDefaultActionScope(this.type);
/* 1479 */     Map actionsForGrammarScope = (Map)this.actions.get(scope);
/*      */ 
/* 1481 */     if ((actionsForGrammarScope == null) || (!actionsForGrammarScope.containsKey("synpredgate")))
/*      */     {
/* 1484 */       if (actionsForGrammarScope == null) {
/* 1485 */         actionsForGrammarScope = new HashMap();
/* 1486 */         this.actions.put(scope, actionsForGrammarScope);
/*      */       }
/* 1488 */       actionsForGrammarScope.put("synpredgate", gateST);
/*      */     }
/*      */   }
/*      */ 
/*      */   public Map getActions()
/*      */   {
/* 1494 */     return this.actions;
/*      */   }
/*      */ 
/*      */   public String getDefaultActionScope(int grammarType)
/*      */   {
/* 1502 */     switch (grammarType) {
/*      */     case 1:
/* 1504 */       return "lexer";
/*      */     case 2:
/*      */     case 4:
/* 1507 */       return "parser";
/*      */     case 3:
/* 1509 */       return "treeparser";
/*      */     }
/* 1511 */     return null;
/*      */   }
/*      */ 
/*      */   public void defineLexerRuleFoundInParser(Token ruleToken, GrammarAST ruleAST)
/*      */   {
/* 1523 */     StringBuffer buf = new StringBuffer();
/* 1524 */     buf.append("// $ANTLR src \"");
/* 1525 */     buf.append(getFileName());
/* 1526 */     buf.append("\" ");
/* 1527 */     buf.append(ruleAST.getLine());
/* 1528 */     buf.append("\n");
/* 1529 */     for (int i = ruleAST.ruleStartTokenIndex; 
/* 1530 */       (i <= ruleAST.ruleStopTokenIndex) && (i < this.tokenBuffer.size()); 
/* 1531 */       i++)
/*      */     {
/* 1533 */       TokenWithIndex t = this.tokenBuffer.getToken(i);
/*      */ 
/* 1535 */       if (t.getType() == 9) {
/* 1536 */         buf.append("(");
/*      */       }
/* 1538 */       else if (t.getType() == 40) {
/* 1539 */         buf.append("{");
/* 1540 */         buf.append(t.getText());
/* 1541 */         buf.append("}");
/*      */       }
/* 1543 */       else if ((t.getType() == 69) || (t.getType() == 36) || (t.getType() == 35) || (t.getType() == 37))
/*      */       {
/* 1548 */         buf.append("{");
/* 1549 */         buf.append(t.getText());
/* 1550 */         buf.append("}?");
/*      */       }
/* 1552 */       else if (t.getType() == 60) {
/* 1553 */         buf.append("[");
/* 1554 */         buf.append(t.getText());
/* 1555 */         buf.append("]");
/*      */       }
/*      */       else {
/* 1558 */         buf.append(t.getText());
/*      */       }
/*      */     }
/* 1561 */     String ruleText = buf.toString();
/*      */ 
/* 1564 */     if (getGrammarIsRoot()) {
/* 1565 */       this.lexerGrammarST.setAttribute("rules", ruleText);
/*      */     }
/*      */ 
/* 1568 */     this.composite.lexerRules.add(ruleToken.getText());
/*      */   }
/*      */ 
/*      */   public void defineLexerRuleForAliasedStringLiteral(String tokenID, String literal, int tokenType)
/*      */   {
/* 1578 */     if (getGrammarIsRoot())
/*      */     {
/* 1580 */       this.lexerGrammarST.setAttribute("literals.{ruleName,type,literal}", tokenID, Utils.integer(tokenType), literal);
/*      */     }
/*      */ 
/* 1586 */     this.composite.lexerRules.add(tokenID);
/*      */   }
/*      */ 
/*      */   public void defineLexerRuleForStringLiteral(String literal, int tokenType)
/*      */   {
/* 1592 */     String tokenID = computeTokenNameFromLiteral(tokenType, literal);
/* 1593 */     defineToken(tokenID, tokenType);
/*      */ 
/* 1595 */     if (getGrammarIsRoot())
/* 1596 */       this.lexerGrammarST.setAttribute("literals.{ruleName,type,literal}", tokenID, Utils.integer(tokenType), literal);
/*      */   }
/*      */ 
/*      */   public Rule getLocallyDefinedRule(String ruleName)
/*      */   {
/* 1604 */     Rule r = (Rule)this.nameToRuleMap.get(ruleName);
/* 1605 */     return r;
/*      */   }
/*      */ 
/*      */   public Rule getRule(String ruleName) {
/* 1609 */     Rule r = this.composite.getRule(ruleName);
/*      */ 
/* 1615 */     return r;
/*      */   }
/*      */ 
/*      */   public Rule getRule(String scopeName, String ruleName) {
/* 1619 */     if (scopeName != null) {
/* 1620 */       Grammar scope = this.composite.getGrammar(scopeName);
/* 1621 */       if (scope == null) {
/* 1622 */         return null;
/*      */       }
/* 1624 */       return scope.getLocallyDefinedRule(ruleName);
/*      */     }
/* 1626 */     return getRule(ruleName);
/*      */   }
/*      */ 
/*      */   public int getRuleIndex(String scopeName, String ruleName) {
/* 1630 */     Rule r = getRule(scopeName, ruleName);
/* 1631 */     if (r != null) {
/* 1632 */       return r.index;
/*      */     }
/* 1634 */     return -1;
/*      */   }
/*      */ 
/*      */   public int getRuleIndex(String ruleName) {
/* 1638 */     return getRuleIndex(null, ruleName);
/*      */   }
/*      */ 
/*      */   public String getRuleName(int ruleIndex) {
/* 1642 */     Rule r = (Rule)this.composite.ruleIndexToRuleList.get(ruleIndex);
/* 1643 */     if (r != null) {
/* 1644 */       return r.name;
/*      */     }
/* 1646 */     return null;
/*      */   }
/*      */ 
/*      */   public boolean generateMethodForRule(String ruleName)
/*      */   {
/* 1655 */     if (ruleName.equals("Tokens"))
/*      */     {
/* 1658 */       return true;
/*      */     }
/* 1660 */     if (this.overriddenRules.contains(ruleName))
/*      */     {
/* 1662 */       return false;
/*      */     }
/*      */ 
/* 1665 */     Rule r = getLocallyDefinedRule(ruleName);
/* 1666 */     return (!r.isSynPred) || ((r.isSynPred) && (this.synPredNamesUsedInDFA.contains(ruleName)));
/*      */   }
/*      */ 
/*      */   public AttributeScope defineGlobalScope(String name, Token scopeAction)
/*      */   {
/* 1671 */     AttributeScope scope = new AttributeScope(this, name, scopeAction);
/* 1672 */     this.scopes.put(name, scope);
/* 1673 */     return scope;
/*      */   }
/*      */ 
/*      */   public AttributeScope createReturnScope(String ruleName, Token retAction) {
/* 1677 */     AttributeScope scope = new AttributeScope(this, ruleName, retAction);
/* 1678 */     scope.isReturnScope = true;
/* 1679 */     return scope;
/*      */   }
/*      */ 
/*      */   public AttributeScope createRuleScope(String ruleName, Token scopeAction) {
/* 1683 */     AttributeScope scope = new AttributeScope(this, ruleName, scopeAction);
/* 1684 */     scope.isDynamicRuleScope = true;
/* 1685 */     return scope;
/*      */   }
/*      */ 
/*      */   public AttributeScope createParameterScope(String ruleName, Token argAction) {
/* 1689 */     AttributeScope scope = new AttributeScope(this, ruleName, argAction);
/* 1690 */     scope.isParameterScope = true;
/* 1691 */     return scope;
/*      */   }
/*      */ 
/*      */   public AttributeScope getGlobalScope(String name)
/*      */   {
/* 1696 */     return (AttributeScope)this.scopes.get(name);
/*      */   }
/*      */ 
/*      */   public Map getGlobalScopes() {
/* 1700 */     return this.scopes;
/*      */   }
/*      */ 
/*      */   protected void defineLabel(Rule r, Token label, GrammarAST element, int type)
/*      */   {
/* 1707 */     boolean err = this.nameSpaceChecker.checkForLabelTypeMismatch(r, label, type);
/* 1708 */     if (err) {
/* 1709 */       return;
/*      */     }
/* 1711 */     r.defineLabel(label, element, type);
/*      */   }
/*      */ 
/*      */   public void defineTokenRefLabel(String ruleName, Token label, GrammarAST tokenRef)
/*      */   {
/* 1718 */     Rule r = getLocallyDefinedRule(ruleName);
/* 1719 */     if (r != null)
/* 1720 */       if ((this.type == 1) && ((tokenRef.getType() == 51) || (tokenRef.getType() == 9) || (tokenRef.getType() == 74) || (tokenRef.getType() == 15) || (tokenRef.getType() == 72)))
/*      */       {
/* 1727 */         defineLabel(r, label, tokenRef, 5);
/*      */       }
/*      */       else
/* 1730 */         defineLabel(r, label, tokenRef, 2);
/*      */   }
/*      */ 
/*      */   public void defineWildcardTreeLabel(String ruleName, Token label, GrammarAST tokenRef)
/*      */   {
/* 1739 */     Rule r = getLocallyDefinedRule(ruleName);
/* 1740 */     if (r != null)
/* 1741 */       defineLabel(r, label, tokenRef, 6);
/*      */   }
/*      */ 
/*      */   public void defineWildcardTreeListLabel(String ruleName, Token label, GrammarAST tokenRef)
/*      */   {
/* 1749 */     Rule r = getLocallyDefinedRule(ruleName);
/* 1750 */     if (r != null)
/* 1751 */       defineLabel(r, label, tokenRef, 7);
/*      */   }
/*      */ 
/*      */   public void defineRuleRefLabel(String ruleName, Token label, GrammarAST ruleRef)
/*      */   {
/* 1759 */     Rule r = getLocallyDefinedRule(ruleName);
/* 1760 */     if (r != null)
/* 1761 */       defineLabel(r, label, ruleRef, 1);
/*      */   }
/*      */ 
/*      */   public void defineTokenListLabel(String ruleName, Token label, GrammarAST element)
/*      */   {
/* 1769 */     Rule r = getLocallyDefinedRule(ruleName);
/* 1770 */     if (r != null)
/* 1771 */       defineLabel(r, label, element, 4);
/*      */   }
/*      */ 
/*      */   public void defineRuleListLabel(String ruleName, Token label, GrammarAST element)
/*      */   {
/* 1779 */     Rule r = getLocallyDefinedRule(ruleName);
/* 1780 */     if (r != null) {
/* 1781 */       if (!r.getHasMultipleReturnValues()) {
/* 1782 */         ErrorManager.grammarError(134, this, label, label.getText());
/*      */       }
/*      */ 
/* 1786 */       defineLabel(r, label, element, 3);
/*      */     }
/*      */   }
/*      */ 
/*      */   public Set<String> getLabels(Set<GrammarAST> rewriteElements, int labelType)
/*      */   {
/* 1795 */     Set labels = new HashSet();
/* 1796 */     for (Iterator it = rewriteElements.iterator(); it.hasNext(); ) {
/* 1797 */       GrammarAST el = (GrammarAST)it.next();
/* 1798 */       if (el.getType() == 31) {
/* 1799 */         String labelName = el.getText();
/* 1800 */         Rule enclosingRule = getLocallyDefinedRule(el.enclosingRuleName);
/* 1801 */         LabelElementPair pair = enclosingRule.getLabel(labelName);
/*      */ 
/* 1818 */         if ((pair != null) && (pair.type == labelType) && (!labelName.equals(el.enclosingRuleName)))
/*      */         {
/* 1821 */           labels.add(labelName);
/*      */         }
/*      */       }
/*      */     }
/* 1825 */     return labels;
/*      */   }
/*      */ 
/*      */   protected void examineAllExecutableActions()
/*      */   {
/* 1834 */     Collection rules = getRules();
/* 1835 */     for (Iterator it = rules.iterator(); it.hasNext(); ) {
/* 1836 */       r = (Rule)it.next();
/*      */ 
/* 1838 */       List actions = r.getInlineActions();
/* 1839 */       for (int i = 0; i < actions.size(); i++) {
/* 1840 */         GrammarAST actionAST = (GrammarAST)actions.get(i);
/* 1841 */         ActionAnalysis sniffer = new ActionAnalysis(this, r.name, actionAST);
/*      */ 
/* 1843 */         sniffer.analyze();
/*      */       }
/*      */ 
/* 1846 */       Collection namedActions = r.getActions().values();
/* 1847 */       for (it2 = namedActions.iterator(); it2.hasNext(); ) {
/* 1848 */         GrammarAST actionAST = (GrammarAST)it2.next();
/* 1849 */         ActionAnalysis sniffer = new ActionAnalysis(this, r.name, actionAST);
/*      */ 
/* 1851 */         sniffer.analyze();
/*      */       }
/*      */     }
/*      */     Rule r;
/*      */     Iterator it2;
/*      */   }
/*      */ 
/*      */   public void checkAllRulesForUselessLabels() {
/* 1860 */     if (this.type == 1) {
/* 1861 */       return;
/*      */     }
/* 1863 */     Set rules = this.nameToRuleMap.keySet();
/* 1864 */     for (Iterator it = rules.iterator(); it.hasNext(); ) {
/* 1865 */       String ruleName = (String)it.next();
/* 1866 */       Rule r = getRule(ruleName);
/* 1867 */       removeUselessLabels(r.getRuleLabels());
/* 1868 */       removeUselessLabels(r.getRuleListLabels());
/*      */     }
/*      */   }
/*      */ 
/*      */   protected void removeUselessLabels(Map ruleToElementLabelPairMap)
/*      */   {
/* 1876 */     if (ruleToElementLabelPairMap == null) {
/* 1877 */       return;
/*      */     }
/* 1879 */     Collection labels = ruleToElementLabelPairMap.values();
/* 1880 */     List kill = new ArrayList();
/* 1881 */     for (Iterator labelit = labels.iterator(); labelit.hasNext(); ) {
/* 1882 */       LabelElementPair pair = (LabelElementPair)labelit.next();
/* 1883 */       Rule refdRule = getRule(pair.elementRef.getText());
/* 1884 */       if ((refdRule != null) && (!refdRule.getHasReturnValue()) && (!pair.actionReferencesLabel))
/*      */       {
/* 1886 */         kill.add(pair.label.getText());
/*      */       }
/*      */     }
/* 1889 */     for (int i = 0; i < kill.size(); i++) {
/* 1890 */       String labelToKill = (String)kill.get(i);
/*      */ 
/* 1892 */       ruleToElementLabelPairMap.remove(labelToKill);
/*      */     }
/*      */   }
/*      */ 
/*      */   public void altReferencesRule(String enclosingRuleName, GrammarAST refScopeAST, GrammarAST refAST, int outerAltNum)
/*      */   {
/* 1917 */     Rule r = getRule(enclosingRuleName);
/* 1918 */     if (r == null) {
/* 1919 */       return;
/*      */     }
/* 1921 */     r.trackRuleReferenceInAlt(refAST, outerAltNum);
/* 1922 */     Token refToken = refAST.getToken();
/* 1923 */     if (!this.ruleRefs.contains(refAST))
/* 1924 */       this.ruleRefs.add(refAST);
/*      */   }
/*      */ 
/*      */   public void altReferencesTokenID(String ruleName, GrammarAST refAST, int outerAltNum)
/*      */   {
/* 1935 */     Rule r = getLocallyDefinedRule(ruleName);
/* 1936 */     if (r == null) {
/* 1937 */       return;
/*      */     }
/* 1939 */     r.trackTokenReferenceInAlt(refAST, outerAltNum);
/* 1940 */     if (!this.tokenIDRefs.contains(refAST.getToken()))
/* 1941 */       this.tokenIDRefs.add(refAST.getToken());
/*      */   }
/*      */ 
/*      */   public void referenceRuleLabelPredefinedAttribute(String ruleName)
/*      */   {
/* 1952 */     Rule r = getRule(ruleName);
/* 1953 */     if ((r != null) && (this.type != 1))
/*      */     {
/* 1957 */       r.referencedPredefinedRuleAttributes = true;
/*      */     }
/*      */   }
/*      */ 
/*      */   public List checkAllRulesForLeftRecursion() {
/* 1962 */     return this.sanity.checkAllRulesForLeftRecursion();
/*      */   }
/*      */ 
/*      */   public Set<Rule> getLeftRecursiveRules()
/*      */   {
/* 1970 */     if (this.nfa == null) {
/* 1971 */       buildNFA();
/*      */     }
/* 1973 */     if (this.leftRecursiveRules != null) {
/* 1974 */       return this.leftRecursiveRules;
/*      */     }
/* 1976 */     this.sanity.checkAllRulesForLeftRecursion();
/* 1977 */     return this.leftRecursiveRules;
/*      */   }
/*      */ 
/*      */   public void checkRuleReference(GrammarAST scopeAST, GrammarAST refAST, GrammarAST argsAST, String currentRuleName)
/*      */   {
/* 1985 */     this.sanity.checkRuleReference(scopeAST, refAST, argsAST, currentRuleName);
/*      */   }
/*      */ 
/*      */   public boolean isEmptyRule(GrammarAST block)
/*      */   {
/* 1995 */     GrammarAST aTokenRefNode = block.findFirstType(55);
/*      */ 
/* 1997 */     GrammarAST aStringLiteralRefNode = block.findFirstType(50);
/*      */ 
/* 1999 */     GrammarAST aCharLiteralRefNode = block.findFirstType(51);
/*      */ 
/* 2001 */     GrammarAST aWildcardRefNode = block.findFirstType(72);
/*      */ 
/* 2003 */     GrammarAST aRuleRefNode = block.findFirstType(73);
/*      */ 
/* 2005 */     if ((aTokenRefNode == null) && (aStringLiteralRefNode == null) && (aCharLiteralRefNode == null) && (aWildcardRefNode == null) && (aRuleRefNode == null))
/*      */     {
/* 2011 */       return true;
/*      */     }
/* 2013 */     return false;
/*      */   }
/*      */ 
/*      */   public boolean isAtomTokenType(int ttype) {
/* 2017 */     return (ttype == 72) || (ttype == 51) || (ttype == 15) || (ttype == 50) || (ttype == 74) || ((this.type != 1) && (ttype == 55));
/*      */   }
/*      */ 
/*      */   public int getTokenType(String tokenName)
/*      */   {
/* 2026 */     Integer I = null;
/* 2027 */     if (tokenName.charAt(0) == '\'') {
/* 2028 */       I = (Integer)this.composite.stringLiteralToTypeMap.get(tokenName);
/*      */     }
/*      */     else {
/* 2031 */       I = (Integer)this.composite.tokenIDToTypeMap.get(tokenName);
/*      */     }
/* 2033 */     int i = I != null ? I.intValue() : -7;
/*      */ 
/* 2035 */     return i;
/*      */   }
/*      */ 
/*      */   public Set getTokenIDs()
/*      */   {
/* 2040 */     return this.composite.tokenIDToTypeMap.keySet();
/*      */   }
/*      */ 
/*      */   public Collection getTokenTypesWithoutID()
/*      */   {
/* 2048 */     List types = new ArrayList();
/* 2049 */     for (int t = 4; t <= getMaxTokenType(); t++) {
/* 2050 */       String name = getTokenDisplayName(t);
/* 2051 */       if (name.charAt(0) == '\'') {
/* 2052 */         types.add(Utils.integer(t));
/*      */       }
/*      */     }
/* 2055 */     return types;
/*      */   }
/*      */ 
/*      */   public Set<String> getTokenDisplayNames()
/*      */   {
/* 2062 */     Set names = new HashSet();
/* 2063 */     for (int t = 4; t <= getMaxTokenType(); t++) {
/* 2064 */       names.add(getTokenDisplayName(t));
/*      */     }
/* 2066 */     return names;
/*      */   }
/*      */ 
/*      */   public static int getCharValueFromGrammarCharLiteral(String literal)
/*      */   {
/* 2077 */     switch (literal.length())
/*      */     {
/*      */     case 3:
/* 2080 */       return literal.charAt(1);
/*      */     case 4:
/* 2083 */       if (Character.isDigit(literal.charAt(2))) {
/* 2084 */         ErrorManager.error(100, "invalid char literal: " + literal);
/*      */ 
/* 2086 */         return -1;
/*      */       }
/* 2088 */       int escChar = literal.charAt(2);
/* 2089 */       int charVal = ANTLRLiteralEscapedCharValue[escChar];
/* 2090 */       if (charVal == 0)
/*      */       {
/* 2092 */         return escChar;
/*      */       }
/* 2094 */       return charVal;
/*      */     case 8:
/* 2097 */       String unicodeChars = literal.substring(3, literal.length() - 1);
/* 2098 */       return Integer.parseInt(unicodeChars, 16);
/*      */     }
/* 2100 */     ErrorManager.error(100, "invalid char literal: " + literal);
/*      */ 
/* 2102 */     return -1;
/*      */   }
/*      */ 
/*      */   public static StringBuffer getUnescapedStringFromGrammarStringLiteral(String literal)
/*      */   {
/* 2121 */     StringBuffer buf = new StringBuffer();
/* 2122 */     int last = literal.length() - 1;
/* 2123 */     for (int i = 1; i < last; i++) {
/* 2124 */       char c = literal.charAt(i);
/* 2125 */       if (c == '\\') {
/* 2126 */         i++;
/* 2127 */         c = literal.charAt(i);
/* 2128 */         if (Character.toUpperCase(c) == 'U')
/*      */         {
/* 2130 */           i++;
/* 2131 */           String unicodeChars = literal.substring(i, i + 4);
/*      */ 
/* 2133 */           int val = Integer.parseInt(unicodeChars, 16);
/* 2134 */           i += 3;
/* 2135 */           buf.append((char)val);
/*      */         }
/* 2137 */         else if (Character.isDigit(c)) {
/* 2138 */           ErrorManager.error(100, "invalid char literal: " + literal);
/*      */ 
/* 2140 */           buf.append("\\" + c);
/*      */         }
/*      */         else {
/* 2143 */           buf.append((char)ANTLRLiteralEscapedCharValue[c]);
/*      */         }
/*      */       }
/*      */       else {
/* 2147 */         buf.append(c);
/*      */       }
/*      */     }
/*      */ 
/* 2151 */     return buf;
/*      */   }
/*      */ 
/*      */   public int importTokenVocabulary(Grammar importFromGr)
/*      */   {
/* 2164 */     Set importedTokenIDs = importFromGr.getTokenIDs();
/* 2165 */     for (Iterator it = importedTokenIDs.iterator(); it.hasNext(); ) {
/* 2166 */       String tokenID = (String)it.next();
/* 2167 */       int tokenType = importFromGr.getTokenType(tokenID);
/* 2168 */       this.composite.maxTokenType = Math.max(this.composite.maxTokenType, tokenType);
/* 2169 */       if (tokenType >= 4)
/*      */       {
/* 2171 */         defineToken(tokenID, tokenType);
/*      */       }
/*      */     }
/* 2174 */     return this.composite.maxTokenType;
/*      */   }
/*      */ 
/*      */   public void importGrammar(GrammarAST grammarNameAST, String label)
/*      */   {
/* 2184 */     String grammarName = grammarNameAST.getText();
/*      */ 
/* 2186 */     String gname = grammarName + ".g";
/* 2187 */     BufferedReader br = null;
/*      */     try {
/* 2189 */       String fullName = this.tool.getLibraryFile(gname);
/* 2190 */       FileReader fr = new FileReader(fullName);
/* 2191 */       br = new BufferedReader(fr);
/* 2192 */       Grammar delegateGrammar = null;
/* 2193 */       delegateGrammar = new Grammar(this.tool, gname, this.composite);
/* 2194 */       delegateGrammar.label = label;
/*      */ 
/* 2196 */       addDelegateGrammar(delegateGrammar);
/*      */ 
/* 2198 */       delegateGrammar.parseAndBuildAST(br);
/* 2199 */       if (!validImport(delegateGrammar)) {
/* 2200 */         ErrorManager.grammarError(161, this, grammarNameAST.token, this, delegateGrammar);
/*      */       }
/* 2207 */       else if ((this.type == 4) && ((delegateGrammar.name.equals(this.name + grammarTypeToFileNameSuffix[1])) || (delegateGrammar.name.equals(this.name + grammarTypeToFileNameSuffix[2]))))
/*      */       {
/* 2211 */         ErrorManager.grammarError(163, this, grammarNameAST.token, this, delegateGrammar);
/*      */       }
/* 2218 */       else if (delegateGrammar.grammarTree != null)
/*      */       {
/* 2221 */         if ((delegateGrammar.type == 1) && (this.type == 4))
/*      */         {
/* 2224 */           this.lexerGrammarST.setAttribute("imports", grammarName);
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (IOException ioe)
/*      */     {
/* 2232 */       ErrorManager.error(7, gname, ioe);
/*      */     }
/*      */     finally
/*      */     {
/* 2237 */       if (br != null)
/*      */         try {
/* 2239 */           br.close();
/*      */         }
/*      */         catch (IOException ioe) {
/* 2242 */           ErrorManager.error(2, gname, ioe);
/*      */         }
/*      */     }
/*      */   }
/*      */ 
/*      */   protected void addDelegateGrammar(Grammar delegateGrammar)
/*      */   {
/* 2252 */     CompositeGrammarTree t = this.composite.delegateGrammarTreeRoot.findNode(this);
/* 2253 */     t.addChild(new CompositeGrammarTree(delegateGrammar));
/*      */ 
/* 2255 */     delegateGrammar.composite = this.composite;
/*      */   }
/*      */ 
/*      */   public int importTokenVocabulary(GrammarAST tokenVocabOptionAST, String vocabName)
/*      */   {
/* 2262 */     if (!getGrammarIsRoot()) {
/* 2263 */       ErrorManager.grammarWarning(160, this, tokenVocabOptionAST.token, this.name);
/*      */ 
/* 2267 */       return this.composite.maxTokenType;
/*      */     }
/*      */ 
/* 2270 */     File fullFile = this.tool.getImportedVocabFile(vocabName);
/*      */     try {
/* 2272 */       FileReader fr = new FileReader(fullFile);
/* 2273 */       BufferedReader br = new BufferedReader(fr);
/* 2274 */       StreamTokenizer tokenizer = new StreamTokenizer(br);
/* 2275 */       tokenizer.parseNumbers();
/* 2276 */       tokenizer.wordChars(95, 95);
/* 2277 */       tokenizer.eolIsSignificant(true);
/* 2278 */       tokenizer.slashSlashComments(true);
/* 2279 */       tokenizer.slashStarComments(true);
/* 2280 */       tokenizer.ordinaryChar(61);
/* 2281 */       tokenizer.quoteChar(39);
/* 2282 */       tokenizer.whitespaceChars(32, 32);
/* 2283 */       tokenizer.whitespaceChars(9, 9);
/* 2284 */       int lineNum = 1;
/* 2285 */       int token = tokenizer.nextToken();
/* 2286 */       while (token != -1)
/*      */       {
/*      */         String tokenID;
/* 2288 */         if (token == -3) {
/* 2289 */           tokenID = tokenizer.sval;
/*      */         }
/*      */         else
/*      */         {
/*      */           String tokenID;
/* 2291 */           if (token == 39) {
/* 2292 */             tokenID = "'" + tokenizer.sval + "'";
/*      */           }
/*      */           else {
/* 2295 */             ErrorManager.error(13, vocabName + ".tokens", Utils.integer(lineNum));
/*      */ 
/* 2298 */             while (tokenizer.nextToken() != 10);
/* 2299 */             token = tokenizer.nextToken();
/* 2300 */             continue;
/*      */           }
/*      */         }
/*      */         String tokenID;
/* 2302 */         token = tokenizer.nextToken();
/* 2303 */         if (token != 61)
/*      */         {
/* 2307 */           while (tokenizer.nextToken() != 10);
/* 2308 */           token = tokenizer.nextToken();
/*      */         }
/*      */         else {
/* 2311 */           token = tokenizer.nextToken();
/* 2312 */           if (token != -2) {
/* 2313 */             ErrorManager.error(13, vocabName + ".tokens", Utils.integer(lineNum));
/*      */ 
/* 2316 */             while (tokenizer.nextToken() != 10);
/* 2317 */             token = tokenizer.nextToken();
/*      */           }
/*      */           else {
/* 2320 */             int tokenType = (int)tokenizer.nval;
/* 2321 */             token = tokenizer.nextToken();
/*      */ 
/* 2323 */             this.composite.maxTokenType = Math.max(this.composite.maxTokenType, tokenType);
/* 2324 */             defineToken(tokenID, tokenType);
/* 2325 */             lineNum++;
/* 2326 */             if (token != 10) {
/* 2327 */               ErrorManager.error(13, vocabName + ".tokens", Utils.integer(lineNum));
/*      */ 
/* 2330 */               while (tokenizer.nextToken() != 10);
/* 2331 */               token = tokenizer.nextToken();
/*      */             }
/*      */             else {
/* 2334 */               token = tokenizer.nextToken();
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/* 2336 */       br.close();
/*      */     }
/*      */     catch (FileNotFoundException fnfe) {
/* 2339 */       ErrorManager.error(3, fullFile);
/*      */     }
/*      */     catch (IOException ioe)
/*      */     {
/* 2343 */       ErrorManager.error(4, fullFile, ioe);
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 2348 */       ErrorManager.error(4, fullFile, e);
/*      */     }
/*      */ 
/* 2352 */     return this.composite.maxTokenType;
/*      */   }
/*      */ 
/*      */   public String getTokenDisplayName(int ttype)
/*      */   {
/* 2360 */     String tokenName = null;
/* 2361 */     int index = 0;
/*      */ 
/* 2363 */     if ((this.type == 1) && (ttype >= 0) && (ttype <= 65535))
/*      */     {
/* 2366 */       return getANTLRCharLiteralForChar(ttype);
/*      */     }
/*      */ 
/* 2369 */     if (ttype < 0) {
/* 2370 */       tokenName = (String)this.composite.typeToTokenList.get(7 + ttype);
/*      */     }
/*      */     else
/*      */     {
/* 2374 */       index = ttype - 1;
/* 2375 */       index += 7;
/*      */ 
/* 2377 */       if (index < this.composite.typeToTokenList.size()) {
/* 2378 */         tokenName = (String)this.composite.typeToTokenList.get(index);
/* 2379 */         if ((tokenName != null) && (tokenName.startsWith("T__")))
/*      */         {
/* 2382 */           tokenName = (String)this.composite.typeToStringLiteralList.get(ttype);
/*      */         }
/*      */       }
/*      */       else {
/* 2386 */         tokenName = String.valueOf(ttype);
/*      */       }
/*      */     }
/*      */ 
/* 2390 */     return tokenName;
/*      */   }
/*      */ 
/*      */   public Set<String> getStringLiterals()
/*      */   {
/* 2395 */     return this.composite.stringLiteralToTypeMap.keySet();
/*      */   }
/*      */ 
/*      */   public String getGrammarTypeString() {
/* 2399 */     return grammarTypeToString[this.type];
/*      */   }
/*      */ 
/*      */   public int getGrammarMaxLookahead() {
/* 2403 */     if (this.global_k >= 0) {
/* 2404 */       return this.global_k;
/*      */     }
/* 2406 */     Object k = getOption("k");
/* 2407 */     if (k == null) {
/* 2408 */       this.global_k = 0;
/*      */     }
/* 2410 */     else if ((k instanceof Integer)) {
/* 2411 */       Integer kI = (Integer)k;
/* 2412 */       this.global_k = kI.intValue();
/*      */     }
/* 2416 */     else if (k.equals("*")) {
/* 2417 */       this.global_k = 0;
/*      */     }
/*      */ 
/* 2420 */     return this.global_k;
/*      */   }
/*      */ 
/*      */   public String setOption(String key, Object value, Token optionsStartToken)
/*      */   {
/* 2427 */     if (legalOption(key)) {
/* 2428 */       ErrorManager.grammarError(133, this, optionsStartToken, key);
/*      */ 
/* 2432 */       return null;
/*      */     }
/* 2434 */     if (!optionIsValid(key, value)) {
/* 2435 */       return null;
/*      */     }
/* 2437 */     if ((key.equals("backtrack")) && (value.toString().equals("true"))) {
/* 2438 */       this.composite.getRootGrammar().atLeastOneBacktrackOption = true;
/*      */     }
/* 2440 */     if (this.options == null) {
/* 2441 */       this.options = new HashMap();
/*      */     }
/* 2443 */     this.options.put(key, value);
/* 2444 */     return key;
/*      */   }
/*      */ 
/*      */   public boolean legalOption(String key) {
/* 2448 */     switch (this.type) {
/*      */     case 1:
/* 2450 */       return !legalLexerOptions.contains(key);
/*      */     case 2:
/* 2452 */       return !legalParserOptions.contains(key);
/*      */     case 3:
/* 2454 */       return !legalTreeParserOptions.contains(key);
/*      */     }
/* 2456 */     return !legalParserOptions.contains(key);
/*      */   }
/*      */ 
/*      */   public void setOptions(Map options, Token optionsStartToken)
/*      */   {
/* 2461 */     if (options == null) {
/* 2462 */       this.options = null;
/* 2463 */       return;
/*      */     }
/* 2465 */     Set keys = options.keySet();
/* 2466 */     for (Iterator it = keys.iterator(); it.hasNext(); ) {
/* 2467 */       String optionName = (String)it.next();
/* 2468 */       Object optionValue = options.get(optionName);
/* 2469 */       String stored = setOption(optionName, optionValue, optionsStartToken);
/* 2470 */       if (stored == null)
/* 2471 */         it.remove();
/*      */     }
/*      */   }
/*      */ 
/*      */   public Object getOption(String key)
/*      */   {
/* 2477 */     return this.composite.getOption(key);
/*      */   }
/*      */ 
/*      */   public Object getLocallyDefinedOption(String key) {
/* 2481 */     Object value = null;
/* 2482 */     if (this.options != null) {
/* 2483 */       value = this.options.get(key);
/*      */     }
/* 2485 */     if (value == null) {
/* 2486 */       value = defaultOptions.get(key);
/*      */     }
/* 2488 */     return value;
/*      */   }
/*      */ 
/*      */   public Object getBlockOption(GrammarAST blockAST, String key) {
/* 2492 */     String v = (String)blockAST.getBlockOption(key);
/* 2493 */     if (v != null) {
/* 2494 */       return v;
/*      */     }
/* 2496 */     if (this.type == 1) {
/* 2497 */       return defaultLexerBlockOptions.get(key);
/*      */     }
/* 2499 */     return defaultBlockOptions.get(key);
/*      */   }
/*      */ 
/*      */   public int getUserMaxLookahead(int decision) {
/* 2503 */     int user_k = 0;
/* 2504 */     GrammarAST blockAST = this.nfa.grammar.getDecisionBlockAST(decision);
/* 2505 */     Object k = blockAST.getBlockOption("k");
/* 2506 */     if (k == null) {
/* 2507 */       user_k = this.nfa.grammar.getGrammarMaxLookahead();
/* 2508 */       return user_k;
/*      */     }
/* 2510 */     if ((k instanceof Integer)) {
/* 2511 */       Integer kI = (Integer)k;
/* 2512 */       user_k = kI.intValue();
/*      */     }
/* 2516 */     else if (k.equals("*")) {
/* 2517 */       user_k = 0;
/*      */     }
/*      */ 
/* 2520 */     return user_k;
/*      */   }
/*      */ 
/*      */   public boolean getAutoBacktrackMode(int decision) {
/* 2524 */     NFAState decisionNFAStartState = getDecisionNFAStartState(decision);
/* 2525 */     String autoBacktrack = (String)getBlockOption(decisionNFAStartState.associatedASTNode, "backtrack");
/*      */ 
/* 2528 */     if (autoBacktrack == null) {
/* 2529 */       autoBacktrack = (String)this.nfa.grammar.getOption("backtrack");
/*      */     }
/* 2531 */     return (autoBacktrack != null) && (autoBacktrack.equals("true"));
/*      */   }
/*      */ 
/*      */   public boolean optionIsValid(String key, Object value) {
/* 2535 */     return true;
/*      */   }
/*      */ 
/*      */   public boolean buildAST() {
/* 2539 */     String outputType = (String)getOption("output");
/* 2540 */     if (outputType != null) {
/* 2541 */       return outputType.toString().equals("AST");
/*      */     }
/* 2543 */     return false;
/*      */   }
/*      */ 
/*      */   public boolean rewriteMode() {
/* 2547 */     Object outputType = getOption("rewrite");
/* 2548 */     if (outputType != null) {
/* 2549 */       return outputType.toString().equals("true");
/*      */     }
/* 2551 */     return false;
/*      */   }
/*      */ 
/*      */   public boolean isBuiltFromString() {
/* 2555 */     return this.builtFromString;
/*      */   }
/*      */ 
/*      */   public boolean buildTemplate() {
/* 2559 */     String outputType = (String)getOption("output");
/* 2560 */     if (outputType != null) {
/* 2561 */       return outputType.toString().equals("template");
/*      */     }
/* 2563 */     return false;
/*      */   }
/*      */ 
/*      */   public Collection<Rule> getRules() {
/* 2567 */     return this.nameToRuleMap.values();
/*      */   }
/*      */ 
/*      */   public Set<Rule> getDelegatedRules()
/*      */   {
/* 2586 */     return this.composite.getDelegatedRules(this);
/*      */   }
/*      */ 
/*      */   public Set<Rule> getAllImportedRules()
/*      */   {
/* 2593 */     return this.composite.getAllImportedRules(this);
/*      */   }
/*      */ 
/*      */   public List<Grammar> getDelegates()
/*      */   {
/* 2600 */     return this.composite.getDelegates(this);
/*      */   }
/*      */ 
/*      */   public boolean getHasDelegates() {
/* 2604 */     return !getDelegates().isEmpty();
/*      */   }
/*      */ 
/*      */   public List<String> getDelegateNames()
/*      */   {
/* 2609 */     List names = new ArrayList();
/* 2610 */     List delegates = this.composite.getDelegates(this);
/*      */     Iterator i$;
/* 2611 */     if (delegates != null) {
/* 2612 */       for (i$ = delegates.iterator(); i$.hasNext(); ) { Grammar g = (Grammar)i$.next();
/* 2613 */         names.add(g.name);
/*      */       }
/*      */     }
/* 2616 */     return names;
/*      */   }
/*      */ 
/*      */   public List<Grammar> getDirectDelegates() {
/* 2620 */     return this.composite.getDirectDelegates(this);
/*      */   }
/*      */ 
/*      */   public List<Grammar> getIndirectDelegates()
/*      */   {
/* 2625 */     return this.composite.getIndirectDelegates(this);
/*      */   }
/*      */ 
/*      */   public List<Grammar> getDelegators()
/*      */   {
/* 2632 */     return this.composite.getDelegators(this);
/*      */   }
/*      */ 
/*      */   public Grammar getDelegator()
/*      */   {
/* 2637 */     return this.composite.getDelegator(this);
/*      */   }
/*      */ 
/*      */   public Set<Rule> getDelegatedRuleReferences() {
/* 2641 */     return this.delegatedRuleReferences;
/*      */   }
/*      */ 
/*      */   public boolean getGrammarIsRoot() {
/* 2645 */     return this.composite.delegateGrammarTreeRoot.grammar == this;
/*      */   }
/*      */ 
/*      */   public void setRuleAST(String ruleName, GrammarAST t) {
/* 2649 */     Rule r = getLocallyDefinedRule(ruleName);
/* 2650 */     if (r != null) {
/* 2651 */       r.tree = t;
/* 2652 */       r.EORNode = t.getLastChild();
/*      */     }
/*      */   }
/*      */ 
/*      */   public NFAState getRuleStartState(String ruleName) {
/* 2657 */     return getRuleStartState(null, ruleName);
/*      */   }
/*      */ 
/*      */   public NFAState getRuleStartState(String scopeName, String ruleName) {
/* 2661 */     Rule r = getRule(scopeName, ruleName);
/* 2662 */     if (r != null)
/*      */     {
/* 2664 */       return r.startState;
/*      */     }
/*      */ 
/* 2667 */     return null;
/*      */   }
/*      */ 
/*      */   public String getRuleModifier(String ruleName) {
/* 2671 */     Rule r = getRule(ruleName);
/* 2672 */     if (r != null) {
/* 2673 */       return r.modifier;
/*      */     }
/* 2675 */     return null;
/*      */   }
/*      */ 
/*      */   public NFAState getRuleStopState(String ruleName) {
/* 2679 */     Rule r = getRule(ruleName);
/* 2680 */     if (r != null) {
/* 2681 */       return r.stopState;
/*      */     }
/* 2683 */     return null;
/*      */   }
/*      */ 
/*      */   public int assignDecisionNumber(NFAState state) {
/* 2687 */     this.decisionCount += 1;
/* 2688 */     state.setDecisionNumber(this.decisionCount);
/* 2689 */     return this.decisionCount;
/*      */   }
/*      */ 
/*      */   protected Decision getDecision(int decision) {
/* 2693 */     int index = decision - 1;
/* 2694 */     if (index >= this.indexToDecision.size()) {
/* 2695 */       return null;
/*      */     }
/* 2697 */     Decision d = (Decision)this.indexToDecision.get(index);
/* 2698 */     return d;
/*      */   }
/*      */ 
/*      */   public List<Decision> getDecisions() {
/* 2702 */     return this.indexToDecision;
/*      */   }
/*      */ 
/*      */   protected Decision createDecision(int decision) {
/* 2706 */     int index = decision - 1;
/* 2707 */     if (index < this.indexToDecision.size()) {
/* 2708 */       return getDecision(decision);
/*      */     }
/* 2710 */     Decision d = new Decision();
/* 2711 */     d.decision = decision;
/* 2712 */     d.grammar = this;
/* 2713 */     this.indexToDecision.setSize(getNumberOfDecisions());
/* 2714 */     this.indexToDecision.set(index, d);
/* 2715 */     return d;
/*      */   }
/*      */ 
/*      */   public List getDecisionNFAStartStateList() {
/* 2719 */     List states = new ArrayList(100);
/* 2720 */     for (int d = 0; d < this.indexToDecision.size(); d++) {
/* 2721 */       Decision dec = (Decision)this.indexToDecision.get(d);
/* 2722 */       states.add(dec.startState);
/*      */     }
/* 2724 */     return states;
/*      */   }
/*      */ 
/*      */   public NFAState getDecisionNFAStartState(int decision) {
/* 2728 */     Decision d = getDecision(decision);
/* 2729 */     if (d == null) {
/* 2730 */       return null;
/*      */     }
/* 2732 */     return d.startState;
/*      */   }
/*      */ 
/*      */   public DFA getLookaheadDFA(int decision) {
/* 2736 */     Decision d = getDecision(decision);
/* 2737 */     if (d == null) {
/* 2738 */       return null;
/*      */     }
/* 2740 */     return d.dfa;
/*      */   }
/*      */ 
/*      */   public GrammarAST getDecisionBlockAST(int decision) {
/* 2744 */     Decision d = getDecision(decision);
/* 2745 */     if (d == null) {
/* 2746 */       return null;
/*      */     }
/* 2748 */     return d.blockAST;
/*      */   }
/*      */ 
/*      */   public List getLookaheadDFAColumnsForLineInFile(int line)
/*      */   {
/* 2761 */     String prefix = line + ":";
/* 2762 */     List columns = new ArrayList();
/* 2763 */     Iterator iter = this.lineColumnToLookaheadDFAMap.keySet().iterator();
/* 2764 */     while (iter.hasNext()) {
/* 2765 */       String key = (String)iter.next();
/* 2766 */       if (key.startsWith(prefix)) {
/* 2767 */         columns.add(Integer.valueOf(key.substring(prefix.length())));
/*      */       }
/*      */     }
/* 2770 */     return columns;
/*      */   }
/*      */ 
/*      */   public DFA getLookaheadDFAFromPositionInFile(int line, int col)
/*      */   {
/* 2775 */     return (DFA)this.lineColumnToLookaheadDFAMap.get(new StringBuffer().append(line).append(":").toString() + col);
/*      */   }
/*      */ 
/*      */   public Map getLineColumnToLookaheadDFAMap()
/*      */   {
/* 2780 */     return this.lineColumnToLookaheadDFAMap;
/*      */   }
/*      */ 
/*      */   public int getNumberOfDecisions()
/*      */   {
/* 2809 */     return this.decisionCount;
/*      */   }
/*      */ 
/*      */   public int getNumberOfCyclicDecisions() {
/* 2813 */     int n = 0;
/* 2814 */     for (int i = 1; i <= getNumberOfDecisions(); i++) {
/* 2815 */       Decision d = getDecision(i);
/* 2816 */       if ((d.dfa != null) && (d.dfa.isCyclic())) {
/* 2817 */         n++;
/*      */       }
/*      */     }
/* 2820 */     return n;
/*      */   }
/*      */ 
/*      */   public void setLookaheadDFA(int decision, DFA lookaheadDFA)
/*      */   {
/* 2834 */     Decision d = createDecision(decision);
/* 2835 */     d.dfa = lookaheadDFA;
/* 2836 */     GrammarAST ast = d.startState.associatedASTNode;
/* 2837 */     ast.setLookaheadDFA(lookaheadDFA);
/*      */   }
/*      */ 
/*      */   public void setDecisionNFA(int decision, NFAState state) {
/* 2841 */     Decision d = createDecision(decision);
/* 2842 */     d.startState = state;
/*      */   }
/*      */ 
/*      */   public void setDecisionBlockAST(int decision, GrammarAST blockAST)
/*      */   {
/* 2847 */     Decision d = createDecision(decision);
/* 2848 */     d.blockAST = blockAST;
/*      */   }
/*      */ 
/*      */   public boolean allDecisionDFAHaveBeenCreated() {
/* 2852 */     return this.allDecisionDFACreated;
/*      */   }
/*      */ 
/*      */   public int getMaxTokenType()
/*      */   {
/* 2857 */     return this.composite.maxTokenType;
/*      */   }
/*      */ 
/*      */   public int getMaxCharValue()
/*      */   {
/* 2864 */     if (this.generator != null) {
/* 2865 */       return this.generator.target.getMaxCharValue(this.generator);
/*      */     }
/*      */ 
/* 2868 */     return 65535;
/*      */   }
/*      */ 
/*      */   public IntSet getTokenTypes()
/*      */   {
/* 2874 */     if (this.type == 1) {
/* 2875 */       return getAllCharValues();
/*      */     }
/* 2877 */     return IntervalSet.of(4, getMaxTokenType());
/*      */   }
/*      */ 
/*      */   public IntSet getAllCharValues()
/*      */   {
/* 2884 */     if (this.charVocabulary != null) {
/* 2885 */       return this.charVocabulary;
/*      */     }
/* 2887 */     IntSet allChar = IntervalSet.of(0, getMaxCharValue());
/* 2888 */     return allChar;
/*      */   }
/*      */ 
/*      */   public static String getANTLRCharLiteralForChar(int c)
/*      */   {
/* 2901 */     if (c < 0) {
/* 2902 */       ErrorManager.internalError("invalid char value " + c);
/* 2903 */       return "'<INVALID>'";
/*      */     }
/* 2905 */     if ((c < ANTLRLiteralCharValueEscape.length) && (ANTLRLiteralCharValueEscape[c] != null)) {
/* 2906 */       return '\'' + ANTLRLiteralCharValueEscape[c] + '\'';
/*      */     }
/* 2908 */     if ((Character.UnicodeBlock.of((char)c) == Character.UnicodeBlock.BASIC_LATIN) && (!Character.isISOControl((char)c)))
/*      */     {
/* 2910 */       if (c == 92) {
/* 2911 */         return "'\\\\'";
/*      */       }
/* 2913 */       if (c == 39) {
/* 2914 */         return "'\\''";
/*      */       }
/* 2916 */       return '\'' + Character.toString((char)c) + '\'';
/*      */     }
/*      */ 
/* 2920 */     String hex = Integer.toHexString(c | 0x10000).toUpperCase().substring(1, 5);
/* 2921 */     String unicodeStr = "'\\u" + hex + "'";
/* 2922 */     return unicodeStr;
/*      */   }
/*      */ 
/*      */   public IntSet complement(IntSet set)
/*      */   {
/* 2932 */     IntSet c = set.complement(getTokenTypes());
/*      */ 
/* 2934 */     return c;
/*      */   }
/*      */ 
/*      */   public IntSet complement(int atom) {
/* 2938 */     return complement(IntervalSet.of(atom));
/*      */   }
/*      */ 
/*      */   public boolean isValidSet(TreeToNFAConverter nfabuilder, GrammarAST t)
/*      */   {
/* 2945 */     boolean valid = true;
/*      */     try
/*      */     {
/* 2948 */       nfabuilder.testBlockAsSet(t);
/*      */     }
/*      */     catch (RecognitionException re)
/*      */     {
/* 2952 */       valid = false;
/*      */     }
/*      */ 
/* 2955 */     return valid;
/*      */   }
/*      */ 
/*      */   public IntSet getSetFromRule(TreeToNFAConverter nfabuilder, String ruleName)
/*      */     throws RecognitionException
/*      */   {
/* 2970 */     Rule r = getRule(ruleName);
/* 2971 */     if (r == null) {
/* 2972 */       return null;
/*      */     }
/* 2974 */     IntSet elements = null;
/*      */ 
/* 2976 */     elements = nfabuilder.setRule(r.tree);
/*      */ 
/* 2978 */     return elements;
/*      */   }
/*      */ 
/*      */   public int getNumberOfAltsForDecisionNFA(NFAState decisionState)
/*      */   {
/* 2986 */     if (decisionState == null) {
/* 2987 */       return 0;
/*      */     }
/* 2989 */     int n = 1;
/* 2990 */     NFAState p = decisionState;
/* 2991 */     while (p.transition[1] != null) {
/* 2992 */       n++;
/* 2993 */       p = (NFAState)p.transition[1].target;
/*      */     }
/* 2995 */     return n;
/*      */   }
/*      */ 
/*      */   public NFAState getNFAStateForAltOfDecision(NFAState decisionState, int alt)
/*      */   {
/* 3010 */     if ((decisionState == null) || (alt <= 0)) {
/* 3011 */       return null;
/*      */     }
/* 3013 */     int n = 1;
/* 3014 */     NFAState p = decisionState;
/* 3015 */     while (p != null) {
/* 3016 */       if (n == alt) {
/* 3017 */         return p;
/*      */       }
/* 3019 */       n++;
/* 3020 */       Transition next = p.transition[1];
/* 3021 */       p = null;
/* 3022 */       if (next != null) {
/* 3023 */         p = (NFAState)next.target;
/*      */       }
/*      */     }
/* 3026 */     return null;
/*      */   }
/*      */ 
/*      */   public LookaheadSet FIRST(NFAState s)
/*      */   {
/* 3046 */     return this.ll1Analyzer.FIRST(s);
/*      */   }
/*      */ 
/*      */   public LookaheadSet LOOK(NFAState s) {
/* 3050 */     return this.ll1Analyzer.LOOK(s);
/*      */   }
/*      */ 
/*      */   public void setCodeGenerator(CodeGenerator generator) {
/* 3054 */     this.generator = generator;
/*      */   }
/*      */ 
/*      */   public CodeGenerator getCodeGenerator() {
/* 3058 */     return this.generator;
/*      */   }
/*      */ 
/*      */   public GrammarAST getGrammarTree() {
/* 3062 */     return this.grammarTree;
/*      */   }
/*      */ 
/*      */   public Tool getTool() {
/* 3066 */     return this.tool;
/*      */   }
/*      */ 
/*      */   public void setTool(Tool tool) {
/* 3070 */     this.tool = tool;
/*      */   }
/*      */ 
/*      */   public String computeTokenNameFromLiteral(int tokenType, String literal)
/*      */   {
/* 3078 */     return "T__" + tokenType;
/*      */   }
/*      */ 
/*      */   public String toString() {
/* 3082 */     return grammarTreeToString(this.grammarTree);
/*      */   }
/*      */ 
/*      */   public String grammarTreeToString(GrammarAST t) {
/* 3086 */     return grammarTreeToString(t, true);
/*      */   }
/*      */ 
/*      */   public String grammarTreeToString(GrammarAST t, boolean showActions) {
/* 3090 */     String s = null;
/*      */     try {
/* 3092 */       s = t.getLine() + ":" + t.getColumn() + ": ";
/* 3093 */       s = s + new ANTLRTreePrinter().toString(t, this, showActions);
/*      */     }
/*      */     catch (Exception e) {
/* 3096 */       s = "<invalid or missing tree structure>";
/*      */     }
/* 3098 */     return s;
/*      */   }
/*      */ 
/*      */   public void printGrammar(PrintStream output) {
/* 3102 */     ANTLRTreePrinter printer = new ANTLRTreePrinter();
/* 3103 */     printer.setASTNodeClass("org.antlr.tool.GrammarAST");
/*      */     try {
/* 3105 */       String g = printer.toString(this.grammarTree, this, false);
/* 3106 */       output.println(g);
/*      */     }
/*      */     catch (RecognitionException re) {
/* 3109 */       ErrorManager.error(100, re);
/*      */     }
/*      */   }
/*      */ 
/*      */   static
/*      */   {
/*   88 */     ANTLRLiteralEscapedCharValue[110] = 10;
/*   89 */     ANTLRLiteralEscapedCharValue[114] = 13;
/*   90 */     ANTLRLiteralEscapedCharValue[116] = 9;
/*   91 */     ANTLRLiteralEscapedCharValue[98] = 8;
/*   92 */     ANTLRLiteralEscapedCharValue[102] = 12;
/*   93 */     ANTLRLiteralEscapedCharValue[92] = 92;
/*   94 */     ANTLRLiteralEscapedCharValue[39] = 39;
/*   95 */     ANTLRLiteralEscapedCharValue[34] = 34;
/*   96 */     ANTLRLiteralCharValueEscape[10] = "\\n";
/*   97 */     ANTLRLiteralCharValueEscape[13] = "\\r";
/*   98 */     ANTLRLiteralCharValueEscape[9] = "\\t";
/*   99 */     ANTLRLiteralCharValueEscape[8] = "\\b";
/*  100 */     ANTLRLiteralCharValueEscape[12] = "\\f";
/*  101 */     ANTLRLiteralCharValueEscape[92] = "\\\\";
/*  102 */     ANTLRLiteralCharValueEscape[39] = "\\'";
/*      */   }
/*      */ 
/*      */   public class LabelElementPair
/*      */   {
/*      */     public Token label;
/*      */     public GrammarAST elementRef;
/*      */     public String referencedRuleName;
/*      */     public boolean actionReferencesLabel;
/*      */     public int type;
/*      */ 
/*      */     public LabelElementPair(Token label, GrammarAST elementRef)
/*      */     {
/*  173 */       this.label = label;
/*  174 */       this.elementRef = elementRef;
/*  175 */       this.referencedRuleName = elementRef.getText();
/*      */     }
/*      */     public Rule getReferencedRule() {
/*  178 */       return Grammar.this.getRule(this.referencedRuleName);
/*      */     }
/*      */     public String toString() {
/*  181 */       return this.elementRef.toString();
/*      */     }
/*      */   }
/*      */ 
/*      */   public static class Decision
/*      */   {
/*      */     public Grammar grammar;
/*      */     public int decision;
/*      */     public NFAState startState;
/*      */     public GrammarAST blockAST;
/*      */     public DFA dfa;
/*      */   }
/*      */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.antlr.tool.Grammar
 * JD-Core Version:    0.6.2
 */