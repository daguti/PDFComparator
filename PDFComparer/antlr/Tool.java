/*     */ package antlr;
/*     */ 
/*     */ import antlr.collections.impl.BitSet;
/*     */ import antlr.collections.impl.Vector;
/*     */ import java.io.BufferedReader;
/*     */ import java.io.BufferedWriter;
/*     */ import java.io.DataInputStream;
/*     */ import java.io.File;
/*     */ import java.io.FileReader;
/*     */ import java.io.FileWriter;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStreamReader;
/*     */ import java.io.PrintStream;
/*     */ import java.io.PrintWriter;
/*     */ import java.io.Reader;
/*     */ import java.io.Writer;
/*     */ import java.util.StringTokenizer;
/*     */ 
/*     */ public class Tool
/*     */ {
/*  26 */   public static String version = "";
/*     */   ToolErrorHandler errorHandler;
/*  32 */   protected boolean hasError = false;
/*     */ 
/*  35 */   boolean genDiagnostics = false;
/*     */ 
/*  38 */   boolean genDocBook = false;
/*     */ 
/*  41 */   boolean genHTML = false;
/*     */ 
/*  44 */   protected String outputDir = ".";
/*     */   protected String grammarFile;
/*  48 */   transient Reader f = new InputStreamReader(System.in);
/*     */ 
/*  52 */   protected String literalsPrefix = "LITERAL_";
/*  53 */   protected boolean upperCaseMangledLiterals = false;
/*     */ 
/*  56 */   protected NameSpace nameSpace = null;
/*  57 */   protected String namespaceAntlr = null;
/*  58 */   protected String namespaceStd = null;
/*  59 */   protected boolean genHashLines = true;
/*  60 */   protected boolean noConstructors = false;
/*     */ 
/*  62 */   private BitSet cmdLineArgValid = new BitSet();
/*     */ 
/*     */   public Tool()
/*     */   {
/*  66 */     this.errorHandler = new DefaultToolErrorHandler(this);
/*     */   }
/*     */ 
/*     */   public String getGrammarFile() {
/*  70 */     return this.grammarFile;
/*     */   }
/*     */ 
/*     */   public boolean hasError() {
/*  74 */     return this.hasError;
/*     */   }
/*     */ 
/*     */   public NameSpace getNameSpace() {
/*  78 */     return this.nameSpace;
/*     */   }
/*     */ 
/*     */   public String getNamespaceStd() {
/*  82 */     return this.namespaceStd;
/*     */   }
/*     */ 
/*     */   public String getNamespaceAntlr() {
/*  86 */     return this.namespaceAntlr;
/*     */   }
/*     */ 
/*     */   public boolean getGenHashLines() {
/*  90 */     return this.genHashLines;
/*     */   }
/*     */ 
/*     */   public String getLiteralsPrefix() {
/*  94 */     return this.literalsPrefix;
/*     */   }
/*     */ 
/*     */   public boolean getUpperCaseMangledLiterals() {
/*  98 */     return this.upperCaseMangledLiterals;
/*     */   }
/*     */ 
/*     */   public void setFileLineFormatter(FileLineFormatter paramFileLineFormatter) {
/* 102 */     FileLineFormatter.setFormatter(paramFileLineFormatter);
/*     */   }
/*     */ 
/*     */   protected void checkForInvalidArguments(String[] paramArrayOfString, BitSet paramBitSet)
/*     */   {
/* 107 */     for (int i = 0; i < paramArrayOfString.length; i++)
/* 108 */       if (!paramBitSet.member(i))
/* 109 */         warning("invalid command-line argument: " + paramArrayOfString[i] + "; ignored");
/*     */   }
/*     */ 
/*     */   public void copyFile(String paramString1, String paramString2)
/*     */     throws IOException
/*     */   {
/* 121 */     File localFile1 = new File(paramString1);
/* 122 */     File localFile2 = new File(paramString2);
/* 123 */     BufferedReader localBufferedReader = null;
/* 124 */     BufferedWriter localBufferedWriter = null;
/*     */     try
/*     */     {
/* 131 */       if ((!localFile1.exists()) || (!localFile1.isFile())) {
/* 132 */         throw new FileCopyException("FileCopy: no such source file: " + paramString1);
/*     */       }
/* 134 */       if (!localFile1.canRead())
/* 135 */         throw new FileCopyException("FileCopy: source file is unreadable: " + paramString1);
/*     */       Object localObject1;
/* 141 */       if (localFile2.exists()) {
/* 142 */         if (localFile2.isFile()) {
/* 143 */           localObject1 = new DataInputStream(System.in);
/*     */ 
/* 146 */           if (!localFile2.canWrite()) {
/* 147 */             throw new FileCopyException("FileCopy: destination file is unwriteable: " + paramString2);
/*     */           }
/*     */ 
/*     */         }
/*     */         else
/*     */         {
/* 159 */           throw new FileCopyException("FileCopy: destination is not a file: " + paramString2);
/*     */         }
/*     */       }
/*     */       else
/*     */       {
/* 164 */         localObject1 = parent(localFile2);
/* 165 */         if (!((File)localObject1).exists()) {
/* 166 */           throw new FileCopyException("FileCopy: destination directory doesn't exist: " + paramString2);
/*     */         }
/* 168 */         if (!((File)localObject1).canWrite()) {
/* 169 */           throw new FileCopyException("FileCopy: destination directory is unwriteable: " + paramString2);
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/* 175 */       localBufferedReader = new BufferedReader(new FileReader(localFile1));
/* 176 */       localBufferedWriter = new BufferedWriter(new FileWriter(localFile2));
/*     */ 
/* 178 */       char[] arrayOfChar = new char[1024];
/*     */       while (true) {
/* 180 */         int i = localBufferedReader.read(arrayOfChar, 0, 1024);
/* 181 */         if (i == -1) break;
/* 182 */         localBufferedWriter.write(arrayOfChar, 0, i);
/*     */       }
/*     */     }
/*     */     finally
/*     */     {
/* 187 */       if (localBufferedReader != null) {
/*     */         try {
/* 189 */           localBufferedReader.close();
/*     */         }
/*     */         catch (IOException localIOException1)
/*     */         {
/*     */         }
/*     */       }
/* 195 */       if (localBufferedWriter != null)
/*     */         try {
/* 197 */           localBufferedWriter.close();
/*     */         }
/*     */         catch (IOException localIOException2)
/*     */         {
/*     */         }
/*     */     }
/*     */   }
/*     */ 
/*     */   public void doEverythingWrapper(String[] paramArrayOfString)
/*     */   {
/* 211 */     int i = doEverything(paramArrayOfString);
/* 212 */     System.exit(i);
/*     */   }
/*     */ 
/*     */   public int doEverything(String[] paramArrayOfString)
/*     */   {
/* 223 */     antlr.preprocessor.Tool localTool = new antlr.preprocessor.Tool(this, paramArrayOfString);
/*     */ 
/* 225 */     boolean bool = localTool.preprocess();
/* 226 */     String[] arrayOfString = localTool.preprocessedArgList();
/*     */ 
/* 229 */     processArguments(arrayOfString);
/* 230 */     if (!bool) {
/* 231 */       return 1;
/*     */     }
/*     */ 
/* 234 */     this.f = getGrammarReader();
/*     */ 
/* 236 */     ANTLRLexer localANTLRLexer = new ANTLRLexer(this.f);
/* 237 */     TokenBuffer localTokenBuffer = new TokenBuffer(localANTLRLexer);
/* 238 */     LLkAnalyzer localLLkAnalyzer = new LLkAnalyzer(this);
/* 239 */     MakeGrammar localMakeGrammar = new MakeGrammar(this, paramArrayOfString, localLLkAnalyzer);
/*     */     try
/*     */     {
/* 242 */       ANTLRParser localANTLRParser = new ANTLRParser(localTokenBuffer, localMakeGrammar, this);
/* 243 */       localANTLRParser.setFilename(this.grammarFile);
/* 244 */       localANTLRParser.grammar();
/* 245 */       if (hasError()) {
/* 246 */         fatalError("Exiting due to errors.");
/*     */       }
/* 248 */       checkForInvalidArguments(arrayOfString, this.cmdLineArgValid);
/*     */ 
/* 255 */       String str = "antlr." + getLanguage(localMakeGrammar) + "CodeGenerator";
/*     */       try {
/* 257 */         CodeGenerator localCodeGenerator = (CodeGenerator)Utils.createInstanceOf(str);
/* 258 */         localCodeGenerator.setBehavior(localMakeGrammar);
/* 259 */         localCodeGenerator.setAnalyzer(localLLkAnalyzer);
/* 260 */         localCodeGenerator.setTool(this);
/* 261 */         localCodeGenerator.gen();
/*     */       }
/*     */       catch (ClassNotFoundException localClassNotFoundException) {
/* 264 */         panic("Cannot instantiate code-generator: " + str);
/*     */       }
/*     */       catch (InstantiationException localInstantiationException) {
/* 267 */         panic("Cannot instantiate code-generator: " + str);
/*     */       }
/*     */       catch (IllegalArgumentException localIllegalArgumentException) {
/* 270 */         panic("Cannot instantiate code-generator: " + str);
/*     */       }
/*     */       catch (IllegalAccessException localIllegalAccessException) {
/* 273 */         panic("code-generator class '" + str + "' is not accessible");
/*     */       }
/*     */     }
/*     */     catch (RecognitionException localRecognitionException) {
/* 277 */       fatalError("Unhandled parser error: " + localRecognitionException.getMessage());
/*     */     }
/*     */     catch (TokenStreamException localTokenStreamException) {
/* 280 */       fatalError("TokenStreamException: " + localTokenStreamException.getMessage());
/*     */     }
/* 282 */     return 0;
/*     */   }
/*     */ 
/*     */   public void error(String paramString)
/*     */   {
/* 289 */     this.hasError = true;
/* 290 */     System.err.println("error: " + paramString);
/*     */   }
/*     */ 
/*     */   public void error(String paramString1, String paramString2, int paramInt1, int paramInt2)
/*     */   {
/* 300 */     this.hasError = true;
/* 301 */     System.err.println(FileLineFormatter.getFormatter().getFormatString(paramString2, paramInt1, paramInt2) + paramString1);
/*     */   }
/*     */ 
/*     */   public String fileMinusPath(String paramString)
/*     */   {
/* 306 */     String str = System.getProperty("file.separator");
/* 307 */     int i = paramString.lastIndexOf(str);
/* 308 */     if (i == -1) {
/* 309 */       return paramString;
/*     */     }
/* 311 */     return paramString.substring(i + 1);
/*     */   }
/*     */ 
/*     */   public String getLanguage(MakeGrammar paramMakeGrammar)
/*     */   {
/* 318 */     if (this.genDiagnostics) {
/* 319 */       return "Diagnostic";
/*     */     }
/* 321 */     if (this.genHTML) {
/* 322 */       return "HTML";
/*     */     }
/* 324 */     if (this.genDocBook) {
/* 325 */       return "DocBook";
/*     */     }
/* 327 */     return paramMakeGrammar.language;
/*     */   }
/*     */ 
/*     */   public String getOutputDirectory() {
/* 331 */     return this.outputDir;
/*     */   }
/*     */ 
/*     */   private static void help() {
/* 335 */     System.err.println("usage: java antlr.Tool [args] file.g");
/* 336 */     System.err.println("  -o outputDir       specify output directory where all output generated.");
/* 337 */     System.err.println("  -glib superGrammar specify location of supergrammar file.");
/* 338 */     System.err.println("  -debug             launch the ParseView debugger upon parser invocation.");
/* 339 */     System.err.println("  -html              generate a html file from your grammar.");
/* 340 */     System.err.println("  -docbook           generate a docbook sgml file from your grammar.");
/* 341 */     System.err.println("  -diagnostic        generate a textfile with diagnostics.");
/* 342 */     System.err.println("  -trace             have all rules call traceIn/traceOut.");
/* 343 */     System.err.println("  -traceLexer        have lexer rules call traceIn/traceOut.");
/* 344 */     System.err.println("  -traceParser       have parser rules call traceIn/traceOut.");
/* 345 */     System.err.println("  -traceTreeParser   have tree parser rules call traceIn/traceOut.");
/* 346 */     System.err.println("  -h|-help|--help    this message");
/*     */   }
/*     */ 
/*     */   public static void main(String[] paramArrayOfString) {
/* 350 */     System.err.println("ANTLR Parser Generator   Version 2.7.7 (20060906)   1989-2005");
/*     */ 
/* 352 */     version = "2.7.7 (20060906)";
/*     */     try
/*     */     {
/* 355 */       int i = 0;
/*     */ 
/* 357 */       if (paramArrayOfString.length == 0) {
/* 358 */         i = 1;
/*     */       }
/*     */       else {
/* 361 */         for (int j = 0; j < paramArrayOfString.length; j++) {
/* 362 */           if ((paramArrayOfString[j].equals("-h")) || (paramArrayOfString[j].equals("-help")) || (paramArrayOfString[j].equals("--help")))
/*     */           {
/* 366 */             i = 1;
/* 367 */             break;
/*     */           }
/*     */         }
/*     */       }
/*     */ 
/* 372 */       if (i != 0) {
/* 373 */         help();
/*     */       }
/*     */       else {
/* 376 */         Tool localTool = new Tool();
/* 377 */         localTool.doEverything(paramArrayOfString);
/* 378 */         localTool = null;
/*     */       }
/*     */     }
/*     */     catch (Exception localException) {
/* 382 */       System.err.println(System.getProperty("line.separator") + System.getProperty("line.separator"));
/*     */ 
/* 384 */       System.err.println("#$%%*&@# internal error: " + localException.toString());
/* 385 */       System.err.println("[complain to nearest government official");
/* 386 */       System.err.println(" or send hate-mail to parrt@antlr.org;");
/* 387 */       System.err.println(" please send stack trace with report.]" + System.getProperty("line.separator"));
/*     */ 
/* 389 */       localException.printStackTrace();
/*     */     }
/*     */   }
/*     */ 
/*     */   public PrintWriter openOutputFile(String paramString)
/*     */     throws IOException
/*     */   {
/* 397 */     if (this.outputDir != ".") {
/* 398 */       File localFile = new File(this.outputDir);
/* 399 */       if (!localFile.exists())
/* 400 */         localFile.mkdirs();
/*     */     }
/* 402 */     return new PrintWriter(new PreservingFileWriter(this.outputDir + System.getProperty("file.separator") + paramString));
/*     */   }
/*     */ 
/*     */   public Reader getGrammarReader() {
/* 406 */     BufferedReader localBufferedReader = null;
/*     */     try {
/* 408 */       if (this.grammarFile != null)
/* 409 */         localBufferedReader = new BufferedReader(new FileReader(this.grammarFile));
/*     */     }
/*     */     catch (IOException localIOException)
/*     */     {
/* 413 */       fatalError("cannot open grammar file " + this.grammarFile);
/*     */     }
/* 415 */     return localBufferedReader;
/*     */   }
/*     */ 
/*     */   public void reportException(Exception paramException, String paramString)
/*     */   {
/* 421 */     System.err.println(paramString + ": " + paramException.getMessage());
/*     */   }
/*     */ 
/*     */   public void reportProgress(String paramString)
/*     */   {
/* 428 */     System.out.println(paramString);
/*     */   }
/*     */ 
/*     */   public void fatalError(String paramString)
/*     */   {
/* 444 */     System.err.println(paramString);
/* 445 */     Utils.error(paramString);
/*     */   }
/*     */ 
/*     */   /** @deprecated */
/*     */   public void panic()
/*     */   {
/* 455 */     fatalError("panic");
/*     */   }
/*     */ 
/*     */   /** @deprecated */
/*     */   public void panic(String paramString)
/*     */   {
/* 466 */     fatalError("panic: " + paramString);
/*     */   }
/*     */ 
/*     */   public File parent(File paramFile)
/*     */   {
/* 473 */     String str = paramFile.getParent();
/* 474 */     if (str == null) {
/* 475 */       if (paramFile.isAbsolute()) {
/* 476 */         return new File(File.separator);
/*     */       }
/* 478 */       return new File(System.getProperty("user.dir"));
/*     */     }
/* 480 */     return new File(str);
/*     */   }
/*     */ 
/*     */   public static Vector parseSeparatedList(String paramString, char paramChar)
/*     */   {
/* 487 */     StringTokenizer localStringTokenizer = new StringTokenizer(paramString, String.valueOf(paramChar));
/*     */ 
/* 489 */     Vector localVector = new Vector(10);
/* 490 */     while (localStringTokenizer.hasMoreTokens()) {
/* 491 */       localVector.appendElement(localStringTokenizer.nextToken());
/*     */     }
/* 493 */     if (localVector.size() == 0) return null;
/* 494 */     return localVector;
/*     */   }
/*     */ 
/*     */   public String pathToFile(String paramString)
/*     */   {
/* 501 */     String str = System.getProperty("file.separator");
/* 502 */     int i = paramString.lastIndexOf(str);
/* 503 */     if (i == -1)
/*     */     {
/* 505 */       return "." + System.getProperty("file.separator");
/*     */     }
/* 507 */     return paramString.substring(0, i + 1);
/*     */   }
/*     */ 
/*     */   protected void processArguments(String[] paramArrayOfString)
/*     */   {
/* 516 */     for (int i = 0; i < paramArrayOfString.length; i++)
/* 517 */       if (paramArrayOfString[i].equals("-diagnostic")) {
/* 518 */         this.genDiagnostics = true;
/* 519 */         this.genHTML = false;
/* 520 */         setArgOK(i);
/*     */       }
/* 522 */       else if (paramArrayOfString[i].equals("-o")) {
/* 523 */         setArgOK(i);
/* 524 */         if (i + 1 >= paramArrayOfString.length) {
/* 525 */           error("missing output directory with -o option; ignoring");
/*     */         }
/*     */         else {
/* 528 */           i++;
/* 529 */           setOutputDirectory(paramArrayOfString[i]);
/* 530 */           setArgOK(i);
/*     */         }
/*     */       }
/* 533 */       else if (paramArrayOfString[i].equals("-html")) {
/* 534 */         this.genHTML = true;
/* 535 */         this.genDiagnostics = false;
/* 536 */         setArgOK(i);
/*     */       }
/* 538 */       else if (paramArrayOfString[i].equals("-docbook")) {
/* 539 */         this.genDocBook = true;
/* 540 */         this.genDiagnostics = false;
/* 541 */         setArgOK(i);
/*     */       }
/* 544 */       else if (paramArrayOfString[i].charAt(0) != '-')
/*     */       {
/* 546 */         this.grammarFile = paramArrayOfString[i];
/* 547 */         setArgOK(i);
/*     */       }
/*     */   }
/*     */ 
/*     */   public void setArgOK(int paramInt)
/*     */   {
/* 554 */     this.cmdLineArgValid.add(paramInt);
/*     */   }
/*     */ 
/*     */   public void setOutputDirectory(String paramString) {
/* 558 */     this.outputDir = paramString;
/*     */   }
/*     */ 
/*     */   public void toolError(String paramString)
/*     */   {
/* 565 */     System.err.println("error: " + paramString);
/*     */   }
/*     */ 
/*     */   public void warning(String paramString)
/*     */   {
/* 572 */     System.err.println("warning: " + paramString);
/*     */   }
/*     */ 
/*     */   public void warning(String paramString1, String paramString2, int paramInt1, int paramInt2)
/*     */   {
/* 582 */     System.err.println(FileLineFormatter.getFormatter().getFormatString(paramString2, paramInt1, paramInt2) + "warning:" + paramString1);
/*     */   }
/*     */ 
/*     */   public void warning(String[] paramArrayOfString, String paramString, int paramInt1, int paramInt2)
/*     */   {
/* 592 */     if ((paramArrayOfString == null) || (paramArrayOfString.length == 0)) {
/* 593 */       panic("bad multi-line message to Tool.warning");
/*     */     }
/* 595 */     System.err.println(FileLineFormatter.getFormatter().getFormatString(paramString, paramInt1, paramInt2) + "warning:" + paramArrayOfString[0]);
/*     */ 
/* 597 */     for (int i = 1; i < paramArrayOfString.length; i++)
/* 598 */       System.err.println(FileLineFormatter.getFormatter().getFormatString(paramString, paramInt1, paramInt2) + "    " + paramArrayOfString[i]);
/*     */   }
/*     */ 
/*     */   public void setNameSpace(String paramString)
/*     */   {
/* 610 */     if (null == this.nameSpace)
/* 611 */       this.nameSpace = new NameSpace(StringUtils.stripFrontBack(paramString, "\"", "\""));
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     antlr.Tool
 * JD-Core Version:    0.6.2
 */