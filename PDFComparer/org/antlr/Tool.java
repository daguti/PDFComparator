/*      */ package org.antlr;
/*      */ 
/*      */ import antlr.ANTLRException;
/*      */ import java.io.BufferedReader;
/*      */ import java.io.BufferedWriter;
/*      */ import java.io.File;
/*      */ import java.io.FileNotFoundException;
/*      */ import java.io.FileReader;
/*      */ import java.io.FileWriter;
/*      */ import java.io.IOException;
/*      */ import java.io.InputStream;
/*      */ import java.io.PrintStream;
/*      */ import java.io.StringReader;
/*      */ import java.io.StringWriter;
/*      */ import java.io.Writer;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Collection;
/*      */ import java.util.GregorianCalendar;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import java.util.Properties;
/*      */ import org.antlr.analysis.DFA;
/*      */ import org.antlr.analysis.NFAContext;
/*      */ import org.antlr.codegen.CodeGenerator;
/*      */ import org.antlr.misc.Graph;
/*      */ import org.antlr.runtime.misc.Stats;
/*      */ import org.antlr.stringtemplate.StringTemplate;
/*      */ import org.antlr.tool.BuildDependencyGenerator;
/*      */ import org.antlr.tool.CompositeGrammar;
/*      */ import org.antlr.tool.DOTGenerator;
/*      */ import org.antlr.tool.ErrorManager;
/*      */ import org.antlr.tool.Grammar;
/*      */ import org.antlr.tool.GrammarReport;
/*      */ import org.antlr.tool.GrammarReport2;
/*      */ import org.antlr.tool.GrammarSpelunker;
/*      */ import org.antlr.tool.Rule;
/*      */ 
/*      */ public class Tool
/*      */ {
/*   43 */   public final Properties antlrSettings = new Properties();
/*   44 */   public String VERSION = "!Unknown version!";
/*      */   public static final String UNINITIALIZED_DIR = "<unset-dir>";
/*   47 */   private List<String> grammarFileNames = new ArrayList();
/*   48 */   private boolean generate_NFA_dot = false;
/*   49 */   private boolean generate_DFA_dot = false;
/*   50 */   private String outputDirectory = ".";
/*   51 */   private boolean haveOutputDir = false;
/*   52 */   private String inputDirectory = null;
/*      */   private String parentGrammarDirectory;
/*      */   private String grammarOutputDirectory;
/*   55 */   private boolean haveInputDir = false;
/*   56 */   private String libDirectory = ".";
/*   57 */   private boolean debug = false;
/*   58 */   private boolean trace = false;
/*   59 */   private boolean profile = false;
/*   60 */   private boolean report = false;
/*   61 */   private boolean printGrammar = false;
/*   62 */   private boolean depend = false;
/*   63 */   private boolean forceAllFilesToOutputDir = false;
/*   64 */   private boolean forceRelativeOutput = false;
/*   65 */   protected boolean deleteTempLexer = true;
/*   66 */   private boolean verbose = false;
/*      */ 
/*   68 */   private boolean make = false;
/*   69 */   private boolean showBanner = true;
/*   70 */   private static boolean exitNow = false;
/*      */ 
/*   74 */   public static boolean internalOption_PrintGrammarTree = false;
/*   75 */   public static boolean internalOption_PrintDFA = false;
/*   76 */   public static boolean internalOption_ShowNFAConfigsInDFA = false;
/*   77 */   public static boolean internalOption_watchNFAConversion = false;
/*      */ 
/*      */   public static void main(String[] args)
/*      */   {
/*   86 */     Tool antlr = new Tool(args);
/*      */ 
/*   88 */     if (!exitNow) {
/*   89 */       antlr.process();
/*   90 */       if (ErrorManager.getNumErrors() > 0) {
/*   91 */         System.exit(1);
/*      */       }
/*   93 */       System.exit(0);
/*      */     }
/*      */   }
/*      */ 
/*      */   private void loadResources()
/*      */   {
/*  102 */     InputStream in = null;
/*  103 */     in = getClass().getResourceAsStream("antlr.properties");
/*      */ 
/*  108 */     if (in != null)
/*      */     {
/*      */       try
/*      */       {
/*  112 */         this.antlrSettings.load(in);
/*      */ 
/*  116 */         this.VERSION = this.antlrSettings.getProperty("antlr.version");
/*      */       }
/*      */       catch (Exception e)
/*      */       {
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   public Tool() {
/*  125 */     loadResources();
/*      */   }
/*      */ 
/*      */   public Tool(String[] args)
/*      */   {
/*  130 */     loadResources();
/*      */ 
/*  134 */     processArgs(args);
/*      */   }
/*      */ 
/*      */   public void processArgs(String[] args)
/*      */   {
/*  141 */     if (isVerbose()) {
/*  142 */       ErrorManager.info("ANTLR Parser Generator  Version " + this.VERSION);
/*  143 */       this.showBanner = false;
/*      */     }
/*      */ 
/*  146 */     if ((args == null) || (args.length == 0)) {
/*  147 */       help();
/*  148 */       return;
/*      */     }
/*  150 */     for (int i = 0; i < args.length; i++)
/*  151 */       if ((args[i].equals("-o")) || (args[i].equals("-fo"))) {
/*  152 */         if (i + 1 >= args.length) {
/*  153 */           System.err.println("missing output directory with -fo/-o option; ignoring");
/*      */         }
/*      */         else {
/*  156 */           if (args[i].equals("-fo")) {
/*  157 */             setForceAllFilesToOutputDir(true);
/*      */           }
/*  159 */           i++;
/*  160 */           this.outputDirectory = args[i];
/*  161 */           if ((this.outputDirectory.endsWith("/")) || (this.outputDirectory.endsWith("\\")))
/*      */           {
/*  163 */             this.outputDirectory = this.outputDirectory.substring(0, getOutputDirectory().length() - 1);
/*      */           }
/*      */ 
/*  166 */           File outDir = new File(this.outputDirectory);
/*  167 */           this.haveOutputDir = true;
/*  168 */           if ((outDir.exists()) && (!outDir.isDirectory())) {
/*  169 */             ErrorManager.error(6, this.outputDirectory);
/*  170 */             setLibDirectory(".");
/*      */           }
/*      */         }
/*      */       }
/*  174 */       else if (args[i].equals("-lib")) {
/*  175 */         if (i + 1 >= args.length) {
/*  176 */           System.err.println("missing library directory with -lib option; ignoring");
/*      */         }
/*      */         else {
/*  179 */           i++;
/*  180 */           setLibDirectory(args[i]);
/*  181 */           if ((getLibraryDirectory().endsWith("/")) || (getLibraryDirectory().endsWith("\\")))
/*      */           {
/*  183 */             setLibDirectory(getLibraryDirectory().substring(0, getLibraryDirectory().length() - 1));
/*      */           }
/*  185 */           File outDir = new File(getLibraryDirectory());
/*  186 */           if (!outDir.exists()) {
/*  187 */             ErrorManager.error(5, getLibraryDirectory());
/*  188 */             setLibDirectory(".");
/*      */           }
/*      */         }
/*      */       }
/*  192 */       else if (args[i].equals("-nfa")) {
/*  193 */         setGenerate_NFA_dot(true);
/*      */       }
/*  195 */       else if (args[i].equals("-dfa")) {
/*  196 */         setGenerate_DFA_dot(true);
/*      */       }
/*  198 */       else if (args[i].equals("-debug")) {
/*  199 */         setDebug(true);
/*      */       }
/*  201 */       else if (args[i].equals("-trace")) {
/*  202 */         setTrace(true);
/*      */       }
/*  204 */       else if (args[i].equals("-report")) {
/*  205 */         setReport(true);
/*      */       }
/*  207 */       else if (args[i].equals("-profile")) {
/*  208 */         setProfile(true);
/*      */       }
/*  210 */       else if (args[i].equals("-print")) {
/*  211 */         setPrintGrammar(true);
/*      */       }
/*  213 */       else if (args[i].equals("-depend")) {
/*  214 */         setDepend(true);
/*      */       }
/*  216 */       else if (args[i].equals("-verbose")) {
/*  217 */         setVerbose(true);
/*      */       }
/*  219 */       else if (args[i].equals("-version")) {
/*  220 */         version();
/*  221 */         exitNow = true;
/*      */       }
/*  223 */       else if (args[i].equals("-make")) {
/*  224 */         setMake(true);
/*      */       }
/*  226 */       else if (args[i].equals("-message-format")) {
/*  227 */         if (i + 1 >= args.length) {
/*  228 */           System.err.println("missing output format with -message-format option; using default");
/*      */         }
/*      */         else {
/*  231 */           i++;
/*  232 */           ErrorManager.setFormat(args[i]);
/*      */         }
/*      */       }
/*  235 */       else if (args[i].equals("-Xgrtree")) {
/*  236 */         internalOption_PrintGrammarTree = true;
/*      */       }
/*  238 */       else if (args[i].equals("-Xdfa")) {
/*  239 */         internalOption_PrintDFA = true;
/*      */       }
/*  241 */       else if (args[i].equals("-Xnoprune")) {
/*  242 */         org.antlr.analysis.DFAOptimizer.PRUNE_EBNF_EXIT_BRANCHES = false;
/*      */       }
/*  244 */       else if (args[i].equals("-Xnocollapse")) {
/*  245 */         org.antlr.analysis.DFAOptimizer.COLLAPSE_ALL_PARALLEL_EDGES = false;
/*      */       }
/*  247 */       else if (args[i].equals("-Xdbgconversion")) {
/*  248 */         org.antlr.analysis.NFAToDFAConverter.debug = true;
/*      */       }
/*  250 */       else if (args[i].equals("-Xmultithreaded")) {
/*  251 */         org.antlr.analysis.NFAToDFAConverter.SINGLE_THREADED_NFA_CONVERSION = false;
/*      */       }
/*  253 */       else if (args[i].equals("-Xnomergestopstates")) {
/*  254 */         org.antlr.analysis.DFAOptimizer.MERGE_STOP_STATES = false;
/*      */       }
/*  256 */       else if (args[i].equals("-Xdfaverbose")) {
/*  257 */         internalOption_ShowNFAConfigsInDFA = true;
/*      */       }
/*  259 */       else if (args[i].equals("-Xwatchconversion")) {
/*  260 */         internalOption_watchNFAConversion = true;
/*      */       }
/*  262 */       else if (args[i].equals("-XdbgST")) {
/*  263 */         CodeGenerator.EMIT_TEMPLATE_DELIMITERS = true;
/*      */       }
/*  265 */       else if (args[i].equals("-Xmaxinlinedfastates")) {
/*  266 */         if (i + 1 >= args.length) {
/*  267 */           System.err.println("missing max inline dfa states -Xmaxinlinedfastates option; ignoring");
/*      */         }
/*      */         else {
/*  270 */           i++;
/*  271 */           CodeGenerator.MAX_ACYCLIC_DFA_STATES_INLINE = Integer.parseInt(args[i]);
/*      */         }
/*      */       }
/*  274 */       else if (args[i].equals("-Xmaxswitchcaselabels")) {
/*  275 */         if (i + 1 >= args.length) {
/*  276 */           System.err.println("missing max switch case labels -Xmaxswitchcaselabels option; ignoring");
/*      */         }
/*      */         else {
/*  279 */           i++;
/*  280 */           CodeGenerator.MAX_SWITCH_CASE_LABELS = Integer.parseInt(args[i]);
/*      */         }
/*      */       }
/*  283 */       else if (args[i].equals("-Xminswitchalts")) {
/*  284 */         if (i + 1 >= args.length) {
/*  285 */           System.err.println("missing min switch alternatives -Xminswitchalts option; ignoring");
/*      */         }
/*      */         else {
/*  288 */           i++;
/*  289 */           CodeGenerator.MIN_SWITCH_ALTS = Integer.parseInt(args[i]);
/*      */         }
/*      */       }
/*  292 */       else if (args[i].equals("-Xm")) {
/*  293 */         if (i + 1 >= args.length) {
/*  294 */           System.err.println("missing max recursion with -Xm option; ignoring");
/*      */         }
/*      */         else {
/*  297 */           i++;
/*  298 */           NFAContext.MAX_SAME_RULE_INVOCATIONS_PER_NFA_CONFIG_STACK = Integer.parseInt(args[i]);
/*      */         }
/*      */       }
/*  301 */       else if (args[i].equals("-Xmaxdfaedges")) {
/*  302 */         if (i + 1 >= args.length) {
/*  303 */           System.err.println("missing max number of edges with -Xmaxdfaedges option; ignoring");
/*      */         }
/*      */         else {
/*  306 */           i++;
/*  307 */           DFA.MAX_STATE_TRANSITIONS_FOR_TABLE = Integer.parseInt(args[i]);
/*      */         }
/*      */       }
/*  310 */       else if (args[i].equals("-Xconversiontimeout")) {
/*  311 */         if (i + 1 >= args.length) {
/*  312 */           System.err.println("missing max time in ms -Xconversiontimeout option; ignoring");
/*      */         }
/*      */         else {
/*  315 */           i++;
/*  316 */           DFA.MAX_TIME_PER_DFA_CREATION = Integer.parseInt(args[i]);
/*      */         }
/*      */       }
/*  319 */       else if (args[i].equals("-Xnfastates")) {
/*  320 */         org.antlr.analysis.DecisionProbe.verbose = true;
/*      */       }
/*  322 */       else if (args[i].equals("-Xsavelexer")) {
/*  323 */         this.deleteTempLexer = false;
/*      */       }
/*  325 */       else if (args[i].equals("-X")) {
/*  326 */         Xhelp();
/*      */       }
/*  329 */       else if (args[i].charAt(0) != '-')
/*      */       {
/*  331 */         addGrammarFile(args[i]);
/*      */       }
/*      */   }
/*      */ 
/*      */   public boolean buildRequired(String grammarFileName)
/*      */     throws IOException, ANTLRException
/*      */   {
/*  360 */     BuildDependencyGenerator bd = new BuildDependencyGenerator(this, grammarFileName);
/*      */ 
/*  363 */     List outputFiles = bd.getGeneratedFileList();
/*  364 */     List inputFiles = bd.getDependenciesFileList();
/*      */     File grammarFile;
/*      */     File grammarFile;
/*  367 */     if (this.haveInputDir) {
/*  368 */       grammarFile = new File(this.inputDirectory, grammarFileName);
/*      */     }
/*      */     else {
/*  371 */       grammarFile = new File(grammarFileName);
/*      */     }
/*  373 */     long grammarLastModified = grammarFile.lastModified();
/*  374 */     for (Iterator i$ = outputFiles.iterator(); i$.hasNext(); ) { outputFile = (File)i$.next();
/*  375 */       if ((!outputFile.exists()) || (grammarLastModified > outputFile.lastModified()))
/*      */       {
/*  377 */         return true;
/*      */       }
/*      */ 
/*  381 */       if (inputFiles != null)
/*  382 */         for (i$ = inputFiles.iterator(); i$.hasNext(); ) { File inputFile = (File)i$.next();
/*      */ 
/*  384 */           if (inputFile.lastModified() > outputFile.lastModified())
/*      */           {
/*  386 */             return true;
/*      */           }
/*      */         }
/*      */     }
/*      */     File outputFile;
/*      */     Iterator i$;
/*  391 */     if (isVerbose()) {
/*  392 */       System.out.println("Grammar " + grammarFile + " is up to date - build skipped");
/*      */     }
/*  394 */     return false;
/*      */   }
/*      */ 
/*      */   public void process() {
/*  398 */     boolean exceptionWhenWritingLexerFile = false;
/*  399 */     String lexerGrammarFileName = null;
/*      */ 
/*  403 */     if ((isVerbose()) && (this.showBanner)) {
/*  404 */       ErrorManager.info("ANTLR Parser Generator  Version " + this.VERSION);
/*  405 */       this.showBanner = false;
/*      */     }
/*      */     try
/*      */     {
/*  409 */       sortGrammarFiles();
/*      */     }
/*      */     catch (Exception e) {
/*  412 */       ErrorManager.error(10, e);
/*      */     }
/*      */     catch (Error e) {
/*  415 */       ErrorManager.error(10, e);
/*      */     }
/*      */ 
/*  418 */     for (Iterator i$ = this.grammarFileNames.iterator(); i$.hasNext(); ) { String grammarFileName = (String)i$.next();
/*      */ 
/*  422 */       if (this.make) {
/*      */         try {
/*  424 */           if (!buildRequired(grammarFileName)) continue; 
/*      */         }
/*      */         catch (Exception e)
/*      */         {
/*  427 */           ErrorManager.error(10, e);
/*      */         }
/*      */       }
/*      */       else {
/*  431 */         if ((isVerbose()) && (!isDepend()))
/*  432 */           System.out.println(grammarFileName);
/*      */         try
/*      */         {
/*  435 */           if (isDepend()) {
/*  436 */             BuildDependencyGenerator dep = new BuildDependencyGenerator(this, grammarFileName);
/*      */ 
/*  444 */             System.out.println(dep.getDependencies());
/*      */           }
/*      */           else
/*      */           {
/*  448 */             Grammar grammar = getRootGrammar(grammarFileName);
/*      */ 
/*  451 */             grammar.composite.assignTokenTypes();
/*  452 */             grammar.composite.defineGrammarSymbols();
/*  453 */             grammar.composite.createNFAs();
/*      */ 
/*  455 */             generateRecognizer(grammar);
/*      */ 
/*  457 */             if (isPrintGrammar()) {
/*  458 */               grammar.printGrammar(System.out);
/*      */             }
/*      */ 
/*  461 */             if (isReport()) {
/*  462 */               GrammarReport2 greport = new GrammarReport2(grammar);
/*  463 */               System.out.print(greport.toString());
/*      */             }
/*      */ 
/*  469 */             if (isProfile()) {
/*  470 */               GrammarReport greport = new GrammarReport(grammar);
/*  471 */               Stats.writeReport("grammar.stats", greport.toNotifyString());
/*      */             }
/*      */ 
/*  476 */             String lexerGrammarStr = grammar.getLexerGrammar();
/*      */ 
/*  478 */             if ((grammar.type == 4) && (lexerGrammarStr != null)) {
/*  479 */               lexerGrammarFileName = grammar.getImplicitlyGeneratedLexerFileName();
/*      */               try {
/*  481 */                 Writer w = getOutputFile(grammar, lexerGrammarFileName);
/*  482 */                 w.write(lexerGrammarStr);
/*  483 */                 w.close();
/*      */               }
/*      */               catch (IOException e)
/*      */               {
/*  488 */                 exceptionWhenWritingLexerFile = true;
/*  489 */                 throw e;
/*      */               }
/*      */               try {
/*  492 */                 StringReader sr = new StringReader(lexerGrammarStr);
/*  493 */                 Grammar lexerGrammar = new Grammar();
/*  494 */                 lexerGrammar.composite.watchNFAConversion = internalOption_watchNFAConversion;
/*  495 */                 lexerGrammar.implicitLexer = true;
/*  496 */                 lexerGrammar.setTool(this);
/*  497 */                 File lexerGrammarFullFile = new File(getFileDirectory(lexerGrammarFileName), lexerGrammarFileName);
/*      */ 
/*  499 */                 lexerGrammar.setFileName(lexerGrammarFullFile.toString());
/*      */ 
/*  501 */                 lexerGrammar.importTokenVocabulary(grammar);
/*  502 */                 lexerGrammar.parseAndBuildAST(sr);
/*      */ 
/*  504 */                 sr.close();
/*      */ 
/*  506 */                 lexerGrammar.composite.assignTokenTypes();
/*  507 */                 lexerGrammar.composite.defineGrammarSymbols();
/*  508 */                 lexerGrammar.composite.createNFAs();
/*      */ 
/*  510 */                 generateRecognizer(lexerGrammar);
/*      */               }
/*      */               finally
/*      */               {
/*      */                 File outputDir;
/*      */                 File outputFile;
/*  514 */                 if (this.deleteTempLexer) {
/*  515 */                   File outputDir = getOutputDirectory(lexerGrammarFileName);
/*  516 */                   File outputFile = new File(outputDir, lexerGrammarFileName);
/*  517 */                   outputFile.delete();
/*      */                 }
/*      */               }
/*      */             }
/*      */           }
/*      */         } catch (IOException e) {
/*  523 */           if (exceptionWhenWritingLexerFile) {
/*  524 */             ErrorManager.error(1, lexerGrammarFileName, e);
/*      */           }
/*      */           else
/*      */           {
/*  528 */             ErrorManager.error(7, grammarFileName);
/*      */           }
/*      */         }
/*      */         catch (Exception e)
/*      */         {
/*  533 */           ErrorManager.error(10, grammarFileName, e);
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   public void sortGrammarFiles()
/*      */     throws IOException
/*      */   {
/*  548 */     Graph g = new Graph();
/*  549 */     List missingFiles = new ArrayList();
/*  550 */     for (Iterator i$ = this.grammarFileNames.iterator(); i$.hasNext(); ) { String gfile = (String)i$.next();
/*      */       try {
/*  552 */         GrammarSpelunker grammar = new GrammarSpelunker(this.inputDirectory, gfile);
/*  553 */         grammar.parse();
/*  554 */         String vocabName = grammar.getTokenVocab();
/*  555 */         String grammarName = grammar.getGrammarName();
/*      */ 
/*  557 */         if (vocabName != null) g.addEdge(gfile, vocabName + ".tokens");
/*      */ 
/*  559 */         g.addEdge(grammarName + ".tokens", gfile);
/*      */       }
/*      */       catch (FileNotFoundException fnfe) {
/*  562 */         ErrorManager.error(7, gfile);
/*  563 */         missingFiles.add(gfile);
/*      */       }
/*      */     }
/*  566 */     List sorted = g.sort();
/*      */ 
/*  568 */     this.grammarFileNames.clear();
/*  569 */     for (int i = 0; i < sorted.size(); i++) {
/*  570 */       String f = (String)sorted.get(i);
/*  571 */       if ((!missingFiles.contains(f)) && (
/*  572 */         (f.endsWith(".g")) || (f.endsWith(".g3"))))
/*  573 */         this.grammarFileNames.add(f);
/*      */     }
/*      */   }
/*      */ 
/*      */   public Grammar getRootGrammar(String grammarFileName)
/*      */     throws IOException
/*      */   {
/*  586 */     CompositeGrammar composite = new CompositeGrammar();
/*  587 */     Grammar grammar = new Grammar(this, grammarFileName, composite);
/*  588 */     composite.setDelegationRoot(grammar);
/*  589 */     FileReader fr = null;
/*  590 */     File f = null;
/*      */ 
/*  592 */     if (this.haveInputDir) {
/*  593 */       f = new File(this.inputDirectory, grammarFileName);
/*      */     }
/*      */     else {
/*  596 */       f = new File(grammarFileName);
/*      */     }
/*      */ 
/*  603 */     this.parentGrammarDirectory = f.getParent();
/*      */ 
/*  605 */     if (grammarFileName.lastIndexOf(File.separatorChar) == -1) {
/*  606 */       this.grammarOutputDirectory = ".";
/*      */     }
/*      */     else {
/*  609 */       this.grammarOutputDirectory = grammarFileName.substring(0, grammarFileName.lastIndexOf(File.separatorChar));
/*      */     }
/*  611 */     fr = new FileReader(f);
/*  612 */     BufferedReader br = new BufferedReader(fr);
/*  613 */     grammar.parseAndBuildAST(br);
/*  614 */     composite.watchNFAConversion = internalOption_watchNFAConversion;
/*  615 */     br.close();
/*  616 */     fr.close();
/*  617 */     return grammar;
/*      */   }
/*      */ 
/*      */   protected void generateRecognizer(Grammar grammar)
/*      */   {
/*  628 */     String language = (String)grammar.getOption("language");
/*  629 */     if (language != null) {
/*  630 */       CodeGenerator generator = new CodeGenerator(this, grammar, language);
/*  631 */       grammar.setCodeGenerator(generator);
/*  632 */       generator.setDebug(isDebug());
/*  633 */       generator.setProfile(isProfile());
/*  634 */       generator.setTrace(isTrace());
/*      */ 
/*  637 */       if (isGenerate_NFA_dot()) {
/*  638 */         generateNFAs(grammar);
/*      */       }
/*      */ 
/*  642 */       generator.genRecognizer();
/*      */ 
/*  644 */       if (isGenerate_DFA_dot()) {
/*  645 */         generateDFAs(grammar);
/*      */       }
/*      */ 
/*  648 */       List delegates = grammar.getDirectDelegates();
/*  649 */       for (int i = 0; (delegates != null) && (i < delegates.size()); i++) {
/*  650 */         Grammar delegate = (Grammar)delegates.get(i);
/*  651 */         if (delegate != grammar)
/*  652 */           generateRecognizer(delegate);
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   public void generateDFAs(Grammar g)
/*      */   {
/*  659 */     for (int d = 1; d <= g.getNumberOfDecisions(); d++) {
/*  660 */       DFA dfa = g.getLookaheadDFA(d);
/*  661 */       if (dfa != null)
/*      */       {
/*  664 */         DOTGenerator dotGenerator = new DOTGenerator(g);
/*  665 */         String dot = dotGenerator.getDOT(dfa.startState);
/*  666 */         String dotFileName = g.name + "." + "dec-" + d;
/*  667 */         if (g.implicitLexer)
/*  668 */           dotFileName = g.name + Grammar.grammarTypeToFileNameSuffix[g.type] + "." + "dec-" + d;
/*      */         try
/*      */         {
/*  671 */           writeDOTFile(g, dotFileName, dot);
/*      */         } catch (IOException ioe) {
/*  673 */           ErrorManager.error(14, dotFileName, ioe);
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   protected void generateNFAs(Grammar g)
/*      */   {
/*  681 */     DOTGenerator dotGenerator = new DOTGenerator(g);
/*  682 */     Collection rules = g.getAllImportedRules();
/*  683 */     rules.addAll(g.getRules());
/*      */ 
/*  685 */     for (Iterator itr = rules.iterator(); itr.hasNext(); ) {
/*  686 */       Rule r = (Rule)itr.next();
/*      */       try {
/*  688 */         String dot = dotGenerator.getDOT(r.startState);
/*  689 */         if (dot != null)
/*  690 */           writeDOTFile(g, r, dot);
/*      */       }
/*      */       catch (IOException ioe) {
/*  693 */         ErrorManager.error(1, ioe);
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   protected void writeDOTFile(Grammar g, Rule r, String dot) throws IOException {
/*  699 */     writeDOTFile(g, r.grammar.name + "." + r.name, dot);
/*      */   }
/*      */ 
/*      */   protected void writeDOTFile(Grammar g, String name, String dot) throws IOException {
/*  703 */     Writer fw = getOutputFile(g, name + ".dot");
/*  704 */     fw.write(dot);
/*  705 */     fw.close();
/*      */   }
/*      */ 
/*      */   private static void version() {
/*  709 */     ErrorManager.info("ANTLR Parser Generator  Version " + new Tool().VERSION);
/*      */   }
/*      */ 
/*      */   private static void help() {
/*  713 */     ErrorManager.info("ANTLR Parser Generator  Version " + new Tool().VERSION);
/*  714 */     System.err.println("usage: java org.antlr.Tool [args] file.g [file2.g file3.g ...]");
/*  715 */     System.err.println("  -o outputDir          specify output directory where all output is generated");
/*  716 */     System.err.println("  -fo outputDir         same as -o but force even files with relative paths to dir");
/*  717 */     System.err.println("  -lib dir              specify location of token files");
/*  718 */     System.err.println("  -depend               generate file dependencies");
/*  719 */     System.err.println("  -report               print out a report about the grammar(s) processed");
/*  720 */     System.err.println("  -print                print out the grammar without actions");
/*  721 */     System.err.println("  -debug                generate a parser that emits debugging events");
/*  722 */     System.err.println("  -profile              generate a parser that computes profiling information");
/*  723 */     System.err.println("  -trace                generate a recognizer that traces rule entry/exit");
/*  724 */     System.err.println("  -nfa                  generate an NFA for each rule");
/*  725 */     System.err.println("  -dfa                  generate a DFA for each decision point");
/*  726 */     System.err.println("  -message-format name  specify output style for messages");
/*  727 */     System.err.println("  -verbose              generate ANTLR version and other information");
/*  728 */     System.err.println("  -make                 only build if generated files older than grammar");
/*  729 */     System.err.println("  -version              print the version of ANTLR and exit.");
/*  730 */     System.err.println("  -X                    display extended argument list");
/*      */   }
/*      */ 
/*      */   private static void Xhelp() {
/*  734 */     ErrorManager.info("ANTLR Parser Generator  Version " + new Tool().VERSION);
/*  735 */     System.err.println("  -Xgrtree                print the grammar AST");
/*  736 */     System.err.println("  -Xdfa                   print DFA as text ");
/*  737 */     System.err.println("  -Xnoprune               test lookahead against EBNF block exit branches");
/*  738 */     System.err.println("  -Xnocollapse            collapse incident edges into DFA states");
/*  739 */     System.err.println("  -Xdbgconversion         dump lots of info during NFA conversion");
/*  740 */     System.err.println("  -Xmultithreaded         run the analysis in 2 threads");
/*  741 */     System.err.println("  -Xnomergestopstates     do not merge stop states");
/*  742 */     System.err.println("  -Xdfaverbose            generate DFA states in DOT with NFA configs");
/*  743 */     System.err.println("  -Xwatchconversion       print a message for each NFA before converting");
/*  744 */     System.err.println("  -XdbgST                 put tags at start/stop of all templates in output");
/*  745 */     System.err.println("  -Xnfastates             for nondeterminisms, list NFA states for each path");
/*  746 */     System.err.println("  -Xm m                   max number of rule invocations during conversion           [" + NFAContext.MAX_SAME_RULE_INVOCATIONS_PER_NFA_CONFIG_STACK + "]");
/*  747 */     System.err.println("  -Xmaxdfaedges m         max \"comfortable\" number of edges for single DFA state     [" + DFA.MAX_STATE_TRANSITIONS_FOR_TABLE + "]");
/*      */ 
/*  749 */     System.err.println("  -Xmaxinlinedfastates m  max DFA states before table used rather than inlining      [10]");
/*  750 */     System.err.println("  -Xmaxswitchcaselabels m don't generate switch() statements for dfas bigger  than m [300]");
/*  751 */     System.err.println("  -Xminswitchalts m       don't generate switch() statements for dfas smaller than m [3]");
/*  752 */     System.err.println("  -Xsavelexer             don't delete temporary lexers generated from combined grammars");
/*      */   }
/*      */ 
/*      */   public void setMaxSwitchCaseLabels(int maxSwitchCaseLabels)
/*      */   {
/*  762 */     CodeGenerator.MAX_SWITCH_CASE_LABELS = maxSwitchCaseLabels;
/*      */   }
/*      */ 
/*      */   public void setMinSwitchAlts(int minSwitchAlts)
/*      */   {
/*  772 */     CodeGenerator.MIN_SWITCH_ALTS = minSwitchAlts;
/*      */   }
/*      */ 
/*      */   public void setOutputDirectory(String outputDirectory)
/*      */   {
/*  781 */     this.haveOutputDir = true;
/*  782 */     this.outputDirectory = outputDirectory;
/*      */   }
/*      */ 
/*      */   public void setForceRelativeOutput(boolean forceRelativeOutput)
/*      */   {
/*  795 */     this.forceRelativeOutput = forceRelativeOutput;
/*      */   }
/*      */ 
/*      */   public void setInputDirectory(String inputDirectory)
/*      */   {
/*  810 */     this.inputDirectory = inputDirectory;
/*  811 */     this.haveInputDir = true;
/*      */   }
/*      */ 
/*      */   public Writer getOutputFile(Grammar g, String fileName)
/*      */     throws IOException
/*      */   {
/*  832 */     if (getOutputDirectory() == null)
/*  833 */       return new StringWriter();
/*      */     File outputDir;
/*      */     File outputDir;
/*  841 */     if (fileName.endsWith(".tokens"))
/*      */     {
/*      */       File outputDir;
/*  842 */       if (this.haveOutputDir) {
/*  843 */         outputDir = new File(getOutputDirectory());
/*      */       }
/*      */       else
/*  846 */         outputDir = new File(".");
/*      */     }
/*      */     else
/*      */     {
/*  850 */       outputDir = getOutputDirectory(g.getFileName());
/*      */     }
/*  852 */     File outputFile = new File(outputDir, fileName);
/*      */ 
/*  854 */     if (!outputDir.exists()) {
/*  855 */       outputDir.mkdirs();
/*      */     }
/*  857 */     FileWriter fw = new FileWriter(outputFile);
/*  858 */     return new BufferedWriter(fw);
/*      */   }
/*      */ 
/*      */   public File getOutputDirectory(String fileNameWithPath)
/*      */   {
/*  872 */     File outputDir = new File(getOutputDirectory());
/*      */     String fileDirectory;
/*      */     String fileDirectory;
/*  882 */     if (fileNameWithPath.lastIndexOf(File.separatorChar) == -1)
/*      */     {
/*  888 */       fileDirectory = this.grammarOutputDirectory;
/*      */     }
/*      */     else
/*      */     {
/*  892 */       fileDirectory = fileNameWithPath.substring(0, fileNameWithPath.lastIndexOf(File.separatorChar));
/*      */     }
/*  894 */     if (this.haveOutputDir)
/*      */     {
/*  898 */       if (((fileDirectory != null) && (!this.forceRelativeOutput) && ((new File(fileDirectory).isAbsolute()) || (fileDirectory.startsWith("~")))) || (isForceAllFilesToOutputDir()))
/*      */       {
/*  903 */         outputDir = new File(getOutputDirectory());
/*      */       }
/*  907 */       else if (fileDirectory != null) {
/*  908 */         outputDir = new File(getOutputDirectory(), fileDirectory);
/*      */       }
/*      */       else {
/*  911 */         outputDir = new File(getOutputDirectory());
/*      */       }
/*      */ 
/*      */     }
/*      */     else
/*      */     {
/*  921 */       outputDir = new File(fileDirectory);
/*      */     }
/*  923 */     return outputDir;
/*      */   }
/*      */ 
/*      */   public String getLibraryFile(String fileName)
/*      */     throws IOException
/*      */   {
/*  941 */     File f = new File(getLibraryDirectory() + File.separator + fileName);
/*      */ 
/*  943 */     if (f.exists())
/*      */     {
/*  947 */       return f.getAbsolutePath();
/*      */     }
/*      */ 
/*  955 */     return this.parentGrammarDirectory + File.separator + fileName;
/*      */   }
/*      */ 
/*      */   public String getFileDirectory(String fileName)
/*      */   {
/*      */     File f;
/*      */     File f;
/*  970 */     if ((this.haveInputDir) && (!fileName.startsWith(File.separator))) {
/*  971 */       f = new File(this.inputDirectory, fileName);
/*      */     }
/*      */     else {
/*  974 */       f = new File(fileName);
/*      */     }
/*      */ 
/*  978 */     return f.getParent();
/*      */   }
/*      */ 
/*      */   public File getImportedVocabFile(String vocabName)
/*      */   {
/*  992 */     File f = new File(getLibraryDirectory(), File.separator + vocabName + ".tokens");
/*      */ 
/*  996 */     if (f.exists()) {
/*  997 */       return f;
/*      */     }
/*      */ 
/* 1005 */     if (this.haveOutputDir) {
/* 1006 */       f = new File(getOutputDirectory(), vocabName + ".tokens");
/*      */     }
/*      */     else {
/* 1009 */       f = new File(vocabName + ".tokens");
/*      */     }
/* 1011 */     return f;
/*      */   }
/*      */ 
/*      */   public void panic()
/*      */   {
/* 1017 */     throw new Error("ANTLR panic");
/*      */   }
/*      */ 
/*      */   public static String getCurrentTimeStamp()
/*      */   {
/* 1023 */     GregorianCalendar calendar = new GregorianCalendar();
/* 1024 */     int y = calendar.get(1);
/* 1025 */     int m = calendar.get(2) + 1;
/* 1026 */     int d = calendar.get(5);
/* 1027 */     int h = calendar.get(11);
/* 1028 */     int min = calendar.get(12);
/* 1029 */     int sec = calendar.get(13);
/* 1030 */     String sy = String.valueOf(y);
/* 1031 */     String sm = m < 10 ? "0" + m : String.valueOf(m);
/* 1032 */     String sd = d < 10 ? "0" + d : String.valueOf(d);
/* 1033 */     String sh = h < 10 ? "0" + h : String.valueOf(h);
/* 1034 */     String smin = min < 10 ? "0" + min : String.valueOf(min);
/* 1035 */     String ssec = sec < 10 ? "0" + sec : String.valueOf(sec);
/* 1036 */     return sy + "-" + sm + "-" + sd + " " + sh + ":" + smin + ":" + ssec;
/*      */   }
/*      */ 
/*      */   public List<String> getGrammarFileNames()
/*      */   {
/* 1046 */     return this.grammarFileNames;
/*      */   }
/*      */ 
/*      */   public boolean isGenerate_NFA_dot()
/*      */   {
/* 1056 */     return this.generate_NFA_dot;
/*      */   }
/*      */ 
/*      */   public boolean isGenerate_DFA_dot()
/*      */   {
/* 1066 */     return this.generate_DFA_dot;
/*      */   }
/*      */ 
/*      */   public String getOutputDirectory()
/*      */   {
/* 1077 */     return this.outputDirectory;
/*      */   }
/*      */ 
/*      */   public String getLibraryDirectory()
/*      */   {
/* 1087 */     return this.libDirectory;
/*      */   }
/*      */ 
/*      */   public boolean isDebug()
/*      */   {
/* 1099 */     return this.debug;
/*      */   }
/*      */ 
/*      */   public boolean isTrace()
/*      */   {
/* 1109 */     return this.trace;
/*      */   }
/*      */ 
/*      */   public boolean isProfile()
/*      */   {
/* 1120 */     return this.profile;
/*      */   }
/*      */ 
/*      */   public boolean isReport()
/*      */   {
/* 1131 */     return this.report;
/*      */   }
/*      */ 
/*      */   public boolean isPrintGrammar()
/*      */   {
/* 1141 */     return this.printGrammar;
/*      */   }
/*      */ 
/*      */   public boolean isDepend()
/*      */   {
/* 1152 */     return this.depend;
/*      */   }
/*      */ 
/*      */   public boolean isForceAllFilesToOutputDir()
/*      */   {
/* 1162 */     return this.forceAllFilesToOutputDir;
/*      */   }
/*      */ 
/*      */   public boolean isVerbose()
/*      */   {
/* 1172 */     return this.verbose;
/*      */   }
/*      */ 
/*      */   public int getConversionTimeout()
/*      */   {
/* 1181 */     return DFA.MAX_TIME_PER_DFA_CREATION;
/*      */   }
/*      */ 
/*      */   public String getMessageFormat()
/*      */   {
/* 1189 */     return ErrorManager.getMessageFormat().toString();
/*      */   }
/*      */ 
/*      */   public int getNumErrors()
/*      */   {
/* 1197 */     return ErrorManager.getNumErrors();
/*      */   }
/*      */ 
/*      */   public boolean getMake()
/*      */   {
/* 1216 */     return this.make;
/*      */   }
/*      */ 
/*      */   public void setMessageFormat(String format)
/*      */   {
/* 1225 */     ErrorManager.setFormat(format);
/*      */   }
/*      */ 
/*      */   public void setGrammarFileNames(List<String> grammarFileNames)
/*      */   {
/* 1233 */     this.grammarFileNames = grammarFileNames;
/*      */   }
/*      */ 
/*      */   public void addGrammarFile(String grammarFileName) {
/* 1237 */     if (!this.grammarFileNames.contains(grammarFileName))
/* 1238 */       this.grammarFileNames.add(grammarFileName);
/*      */   }
/*      */ 
/*      */   public void setGenerate_NFA_dot(boolean generate_NFA_dot)
/*      */   {
/* 1249 */     this.generate_NFA_dot = generate_NFA_dot;
/*      */   }
/*      */ 
/*      */   public void setGenerate_DFA_dot(boolean generate_DFA_dot)
/*      */   {
/* 1259 */     this.generate_DFA_dot = generate_DFA_dot;
/*      */   }
/*      */ 
/*      */   public void setLibDirectory(String libDirectory)
/*      */   {
/* 1269 */     this.libDirectory = libDirectory;
/*      */   }
/*      */ 
/*      */   public void setDebug(boolean debug)
/*      */   {
/* 1281 */     this.debug = debug;
/*      */   }
/*      */ 
/*      */   public void setTrace(boolean trace)
/*      */   {
/* 1291 */     this.trace = trace;
/*      */   }
/*      */ 
/*      */   public void setProfile(boolean profile)
/*      */   {
/* 1302 */     this.profile = profile;
/*      */   }
/*      */ 
/*      */   public void setReport(boolean report)
/*      */   {
/* 1313 */     this.report = report;
/*      */   }
/*      */ 
/*      */   public void setPrintGrammar(boolean printGrammar)
/*      */   {
/* 1323 */     this.printGrammar = printGrammar;
/*      */   }
/*      */ 
/*      */   public void setDepend(boolean depend)
/*      */   {
/* 1334 */     this.depend = depend;
/*      */   }
/*      */ 
/*      */   public void setForceAllFilesToOutputDir(boolean forceAllFilesToOutputDir)
/*      */   {
/* 1344 */     this.forceAllFilesToOutputDir = forceAllFilesToOutputDir;
/*      */   }
/*      */ 
/*      */   public void setVerbose(boolean verbose)
/*      */   {
/* 1354 */     this.verbose = verbose;
/*      */   }
/*      */ 
/*      */   public void setMake(boolean make)
/*      */   {
/* 1373 */     this.make = make;
/*      */   }
/*      */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.antlr.Tool
 * JD-Core Version:    0.6.2
 */