/*     */ package antlr.preprocessor;
/*     */ 
/*     */ import antlr.ANTLRException;
/*     */ import antlr.TokenStreamException;
/*     */ import antlr.Tool;
/*     */ import antlr.collections.impl.IndexedVector;
/*     */ import java.io.BufferedReader;
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.FileReader;
/*     */ import java.util.Enumeration;
/*     */ import java.util.Hashtable;
/*     */ 
/*     */ public class Hierarchy
/*     */ {
/*  20 */   protected Grammar LexerRoot = null;
/*  21 */   protected Grammar ParserRoot = null;
/*  22 */   protected Grammar TreeParserRoot = null;
/*     */   protected Hashtable symbols;
/*     */   protected Hashtable files;
/*     */   protected Tool antlrTool;
/*     */ 
/*     */   public Hierarchy(Tool paramTool)
/*     */   {
/*  28 */     this.antlrTool = paramTool;
/*  29 */     this.LexerRoot = new Grammar(paramTool, "Lexer", null, null);
/*  30 */     this.ParserRoot = new Grammar(paramTool, "Parser", null, null);
/*  31 */     this.TreeParserRoot = new Grammar(paramTool, "TreeParser", null, null);
/*  32 */     this.symbols = new Hashtable(10);
/*  33 */     this.files = new Hashtable(10);
/*     */ 
/*  35 */     this.LexerRoot.setPredefined(true);
/*  36 */     this.ParserRoot.setPredefined(true);
/*  37 */     this.TreeParserRoot.setPredefined(true);
/*     */ 
/*  39 */     this.symbols.put(this.LexerRoot.getName(), this.LexerRoot);
/*  40 */     this.symbols.put(this.ParserRoot.getName(), this.ParserRoot);
/*  41 */     this.symbols.put(this.TreeParserRoot.getName(), this.TreeParserRoot);
/*     */   }
/*     */ 
/*     */   public void addGrammar(Grammar paramGrammar) {
/*  45 */     paramGrammar.setHierarchy(this);
/*     */ 
/*  47 */     this.symbols.put(paramGrammar.getName(), paramGrammar);
/*     */ 
/*  49 */     GrammarFile localGrammarFile = getFile(paramGrammar.getFileName());
/*  50 */     localGrammarFile.addGrammar(paramGrammar);
/*     */   }
/*     */ 
/*     */   public void addGrammarFile(GrammarFile paramGrammarFile) {
/*  54 */     this.files.put(paramGrammarFile.getName(), paramGrammarFile);
/*     */   }
/*     */ 
/*     */   public void expandGrammarsInFile(String paramString) {
/*  58 */     GrammarFile localGrammarFile = getFile(paramString);
/*  59 */     for (Enumeration localEnumeration = localGrammarFile.getGrammars().elements(); localEnumeration.hasMoreElements(); ) {
/*  60 */       Grammar localGrammar = (Grammar)localEnumeration.nextElement();
/*  61 */       localGrammar.expandInPlace();
/*     */     }
/*     */   }
/*     */ 
/*     */   public Grammar findRoot(Grammar paramGrammar) {
/*  66 */     if (paramGrammar.getSuperGrammarName() == null) {
/*  67 */       return paramGrammar;
/*     */     }
/*     */ 
/*  70 */     Grammar localGrammar = paramGrammar.getSuperGrammar();
/*  71 */     if (localGrammar == null) return paramGrammar;
/*  72 */     return findRoot(localGrammar);
/*     */   }
/*     */ 
/*     */   public GrammarFile getFile(String paramString) {
/*  76 */     return (GrammarFile)this.files.get(paramString);
/*     */   }
/*     */ 
/*     */   public Grammar getGrammar(String paramString) {
/*  80 */     return (Grammar)this.symbols.get(paramString);
/*     */   }
/*     */ 
/*     */   public static String optionsToString(IndexedVector paramIndexedVector) {
/*  84 */     String str = "options {" + System.getProperty("line.separator");
/*  85 */     for (Enumeration localEnumeration = paramIndexedVector.elements(); localEnumeration.hasMoreElements(); ) {
/*  86 */       str = str + (Option)localEnumeration.nextElement() + System.getProperty("line.separator");
/*     */     }
/*  88 */     str = str + "}" + System.getProperty("line.separator") + System.getProperty("line.separator");
/*     */ 
/*  91 */     return str;
/*     */   }
/*     */ 
/*     */   public void readGrammarFile(String paramString) throws FileNotFoundException {
/*  95 */     BufferedReader localBufferedReader = new BufferedReader(new FileReader(paramString));
/*  96 */     addGrammarFile(new GrammarFile(this.antlrTool, paramString));
/*     */ 
/*  99 */     PreprocessorLexer localPreprocessorLexer = new PreprocessorLexer(localBufferedReader);
/* 100 */     localPreprocessorLexer.setFilename(paramString);
/* 101 */     Preprocessor localPreprocessor = new Preprocessor(localPreprocessorLexer);
/* 102 */     localPreprocessor.setTool(this.antlrTool);
/* 103 */     localPreprocessor.setFilename(paramString);
/*     */     try
/*     */     {
/* 107 */       localPreprocessor.grammarFile(this, paramString);
/*     */     }
/*     */     catch (TokenStreamException localTokenStreamException) {
/* 110 */       this.antlrTool.toolError("Token stream error reading grammar(s):\n" + localTokenStreamException);
/*     */     }
/*     */     catch (ANTLRException localANTLRException) {
/* 113 */       this.antlrTool.toolError("error reading grammar(s):\n" + localANTLRException);
/*     */     }
/*     */   }
/*     */ 
/*     */   public boolean verifyThatHierarchyIsComplete()
/*     */   {
/* 119 */     int i = 1;
/*     */ 
/* 121 */     for (Enumeration localEnumeration = this.symbols.elements(); localEnumeration.hasMoreElements(); ) {
/* 122 */       localGrammar1 = (Grammar)localEnumeration.nextElement();
/* 123 */       if (localGrammar1.getSuperGrammarName() != null)
/*     */       {
/* 126 */         Grammar localGrammar2 = localGrammar1.getSuperGrammar();
/* 127 */         if (localGrammar2 == null) {
/* 128 */           this.antlrTool.toolError("grammar " + localGrammar1.getSuperGrammarName() + " not defined");
/* 129 */           i = 0;
/* 130 */           this.symbols.remove(localGrammar1.getName());
/*     */         }
/*     */       }
/*     */     }
/* 134 */     Grammar localGrammar1;
/* 134 */     if (i == 0) return false;
/*     */ 
/* 139 */     for (localEnumeration = this.symbols.elements(); localEnumeration.hasMoreElements(); ) {
/* 140 */       localGrammar1 = (Grammar)localEnumeration.nextElement();
/* 141 */       if (localGrammar1.getSuperGrammarName() != null)
/*     */       {
/* 144 */         localGrammar1.setType(findRoot(localGrammar1).getName());
/*     */       }
/*     */     }
/* 147 */     return true;
/*     */   }
/*     */ 
/*     */   public Tool getTool() {
/* 151 */     return this.antlrTool;
/*     */   }
/*     */ 
/*     */   public void setTool(Tool paramTool) {
/* 155 */     this.antlrTool = paramTool;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     antlr.preprocessor.Hierarchy
 * JD-Core Version:    0.6.2
 */