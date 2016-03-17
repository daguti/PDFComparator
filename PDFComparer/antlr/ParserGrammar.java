/*     */ package antlr;
/*     */ 
/*     */ import java.io.IOException;
/*     */ 
/*     */ class ParserGrammar extends Grammar
/*     */ {
/*     */   ParserGrammar(String paramString1, Tool paramTool, String paramString2)
/*     */   {
/*  23 */     super(paramString1, paramTool, paramString2);
/*     */   }
/*     */ 
/*     */   public void generate() throws IOException
/*     */   {
/*  28 */     this.generator.gen(this);
/*     */   }
/*     */ 
/*     */   protected String getSuperClass()
/*     */   {
/*  34 */     if (this.debuggingOutput)
/*  35 */       return "debug.LLkDebuggingParser";
/*  36 */     return "LLkParser";
/*     */   }
/*     */ 
/*     */   public void processArguments(String[] paramArrayOfString)
/*     */   {
/*  45 */     for (int i = 0; i < paramArrayOfString.length; i++)
/*  46 */       if (paramArrayOfString[i].equals("-trace")) {
/*  47 */         this.traceRules = true;
/*  48 */         this.antlrTool.setArgOK(i);
/*     */       }
/*  50 */       else if (paramArrayOfString[i].equals("-traceParser")) {
/*  51 */         this.traceRules = true;
/*  52 */         this.antlrTool.setArgOK(i);
/*     */       }
/*  54 */       else if (paramArrayOfString[i].equals("-debug")) {
/*  55 */         this.debuggingOutput = true;
/*  56 */         this.antlrTool.setArgOK(i);
/*     */       }
/*     */   }
/*     */ 
/*     */   public boolean setOption(String paramString, Token paramToken)
/*     */   {
/*  64 */     String str = paramToken.getText();
/*  65 */     if (paramString.equals("buildAST")) {
/*  66 */       if (str.equals("true")) {
/*  67 */         this.buildAST = true;
/*     */       }
/*  69 */       else if (str.equals("false")) {
/*  70 */         this.buildAST = false;
/*     */       }
/*     */       else {
/*  73 */         this.antlrTool.error("buildAST option must be true or false", getFilename(), paramToken.getLine(), paramToken.getColumn());
/*     */       }
/*  75 */       return true;
/*     */     }
/*  77 */     if (paramString.equals("interactive")) {
/*  78 */       if (str.equals("true")) {
/*  79 */         this.interactive = true;
/*     */       }
/*  81 */       else if (str.equals("false")) {
/*  82 */         this.interactive = false;
/*     */       }
/*     */       else {
/*  85 */         this.antlrTool.error("interactive option must be true or false", getFilename(), paramToken.getLine(), paramToken.getColumn());
/*     */       }
/*  87 */       return true;
/*     */     }
/*  89 */     if (paramString.equals("ASTLabelType")) {
/*  90 */       super.setOption(paramString, paramToken);
/*  91 */       return true;
/*     */     }
/*  93 */     if (paramString.equals("className")) {
/*  94 */       super.setOption(paramString, paramToken);
/*  95 */       return true;
/*     */     }
/*  97 */     if (super.setOption(paramString, paramToken)) {
/*  98 */       return true;
/*     */     }
/* 100 */     this.antlrTool.error("Invalid option: " + paramString, getFilename(), paramToken.getLine(), paramToken.getColumn());
/* 101 */     return false;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     antlr.ParserGrammar
 * JD-Core Version:    0.6.2
 */