/*     */ package antlr;
/*     */ 
/*     */ import antlr.collections.impl.BitSet;
/*     */ import java.io.IOException;
/*     */ 
/*     */ class LexerGrammar extends Grammar
/*     */ {
/*     */   protected BitSet charVocabulary;
/*  22 */   protected boolean testLiterals = true;
/*     */ 
/*  24 */   protected boolean caseSensitiveLiterals = true;
/*     */ 
/*  26 */   protected boolean caseSensitive = true;
/*     */ 
/*  28 */   protected boolean filterMode = false;
/*     */ 
/*  34 */   protected String filterRule = null;
/*     */ 
/*     */   LexerGrammar(String paramString1, Tool paramTool, String paramString2) {
/*  37 */     super(paramString1, paramTool, paramString2);
/*     */ 
/*  39 */     BitSet localBitSet = new BitSet();
/*  40 */     for (int i = 0; i <= 127; i++) {
/*  41 */       localBitSet.add(i);
/*     */     }
/*  43 */     setCharVocabulary(localBitSet);
/*     */ 
/*  46 */     this.defaultErrorHandler = false;
/*     */   }
/*     */ 
/*     */   public void generate() throws IOException
/*     */   {
/*  51 */     this.generator.gen(this);
/*     */   }
/*     */ 
/*     */   public String getSuperClass()
/*     */   {
/*  56 */     if (this.debuggingOutput)
/*  57 */       return "debug.DebuggingCharScanner";
/*  58 */     return "CharScanner";
/*     */   }
/*     */ 
/*     */   public boolean getTestLiterals()
/*     */   {
/*  63 */     return this.testLiterals;
/*     */   }
/*     */ 
/*     */   public void processArguments(String[] paramArrayOfString)
/*     */   {
/*  72 */     for (int i = 0; i < paramArrayOfString.length; i++)
/*  73 */       if (paramArrayOfString[i].equals("-trace")) {
/*  74 */         this.traceRules = true;
/*  75 */         this.antlrTool.setArgOK(i);
/*     */       }
/*  77 */       else if (paramArrayOfString[i].equals("-traceLexer")) {
/*  78 */         this.traceRules = true;
/*  79 */         this.antlrTool.setArgOK(i);
/*     */       }
/*  81 */       else if (paramArrayOfString[i].equals("-debug")) {
/*  82 */         this.debuggingOutput = true;
/*  83 */         this.antlrTool.setArgOK(i);
/*     */       }
/*     */   }
/*     */ 
/*     */   public void setCharVocabulary(BitSet paramBitSet)
/*     */   {
/*  90 */     this.charVocabulary = paramBitSet;
/*     */   }
/*     */ 
/*     */   public boolean setOption(String paramString, Token paramToken)
/*     */   {
/*  95 */     String str = paramToken.getText();
/*  96 */     if (paramString.equals("buildAST")) {
/*  97 */       this.antlrTool.warning("buildAST option is not valid for lexer", getFilename(), paramToken.getLine(), paramToken.getColumn());
/*  98 */       return true;
/*     */     }
/* 100 */     if (paramString.equals("testLiterals")) {
/* 101 */       if (str.equals("true")) {
/* 102 */         this.testLiterals = true;
/*     */       }
/* 104 */       else if (str.equals("false")) {
/* 105 */         this.testLiterals = false;
/*     */       }
/*     */       else {
/* 108 */         this.antlrTool.warning("testLiterals option must be true or false", getFilename(), paramToken.getLine(), paramToken.getColumn());
/*     */       }
/* 110 */       return true;
/*     */     }
/* 112 */     if (paramString.equals("interactive")) {
/* 113 */       if (str.equals("true")) {
/* 114 */         this.interactive = true;
/*     */       }
/* 116 */       else if (str.equals("false")) {
/* 117 */         this.interactive = false;
/*     */       }
/*     */       else {
/* 120 */         this.antlrTool.error("interactive option must be true or false", getFilename(), paramToken.getLine(), paramToken.getColumn());
/*     */       }
/* 122 */       return true;
/*     */     }
/* 124 */     if (paramString.equals("caseSensitive")) {
/* 125 */       if (str.equals("true")) {
/* 126 */         this.caseSensitive = true;
/*     */       }
/* 128 */       else if (str.equals("false")) {
/* 129 */         this.caseSensitive = false;
/*     */       }
/*     */       else {
/* 132 */         this.antlrTool.warning("caseSensitive option must be true or false", getFilename(), paramToken.getLine(), paramToken.getColumn());
/*     */       }
/* 134 */       return true;
/*     */     }
/* 136 */     if (paramString.equals("caseSensitiveLiterals")) {
/* 137 */       if (str.equals("true")) {
/* 138 */         this.caseSensitiveLiterals = true;
/*     */       }
/* 140 */       else if (str.equals("false")) {
/* 141 */         this.caseSensitiveLiterals = false;
/*     */       }
/*     */       else {
/* 144 */         this.antlrTool.warning("caseSensitiveLiterals option must be true or false", getFilename(), paramToken.getLine(), paramToken.getColumn());
/*     */       }
/* 146 */       return true;
/*     */     }
/* 148 */     if (paramString.equals("filter")) {
/* 149 */       if (str.equals("true")) {
/* 150 */         this.filterMode = true;
/*     */       }
/* 152 */       else if (str.equals("false")) {
/* 153 */         this.filterMode = false;
/*     */       }
/* 155 */       else if (paramToken.getType() == 24) {
/* 156 */         this.filterMode = true;
/* 157 */         this.filterRule = str;
/*     */       }
/*     */       else {
/* 160 */         this.antlrTool.warning("filter option must be true, false, or a lexer rule name", getFilename(), paramToken.getLine(), paramToken.getColumn());
/*     */       }
/* 162 */       return true;
/*     */     }
/* 164 */     if (paramString.equals("longestPossible")) {
/* 165 */       this.antlrTool.warning("longestPossible option has been deprecated; ignoring it...", getFilename(), paramToken.getLine(), paramToken.getColumn());
/* 166 */       return true;
/*     */     }
/* 168 */     if (paramString.equals("className")) {
/* 169 */       super.setOption(paramString, paramToken);
/* 170 */       return true;
/*     */     }
/* 172 */     if (super.setOption(paramString, paramToken)) {
/* 173 */       return true;
/*     */     }
/* 175 */     this.antlrTool.error("Invalid option: " + paramString, getFilename(), paramToken.getLine(), paramToken.getColumn());
/* 176 */     return false;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     antlr.LexerGrammar
 * JD-Core Version:    0.6.2
 */