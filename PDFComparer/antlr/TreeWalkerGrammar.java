/*    */ package antlr;
/*    */ 
/*    */ import java.io.IOException;
/*    */ 
/*    */ class TreeWalkerGrammar extends Grammar
/*    */ {
/* 21 */   protected boolean transform = false;
/*    */ 
/*    */   TreeWalkerGrammar(String paramString1, Tool paramTool, String paramString2)
/*    */   {
/* 25 */     super(paramString1, paramTool, paramString2);
/*    */   }
/*    */ 
/*    */   public void generate() throws IOException
/*    */   {
/* 30 */     this.generator.gen(this);
/*    */   }
/*    */ 
/*    */   protected String getSuperClass()
/*    */   {
/* 35 */     return "TreeParser";
/*    */   }
/*    */ 
/*    */   public void processArguments(String[] paramArrayOfString)
/*    */   {
/* 44 */     for (int i = 0; i < paramArrayOfString.length; i++)
/* 45 */       if (paramArrayOfString[i].equals("-trace")) {
/* 46 */         this.traceRules = true;
/* 47 */         this.antlrTool.setArgOK(i);
/*    */       }
/* 49 */       else if (paramArrayOfString[i].equals("-traceTreeParser")) {
/* 50 */         this.traceRules = true;
/* 51 */         this.antlrTool.setArgOK(i);
/*    */       }
/*    */   }
/*    */ 
/*    */   public boolean setOption(String paramString, Token paramToken)
/*    */   {
/* 63 */     if (paramString.equals("buildAST")) {
/* 64 */       if (paramToken.getText().equals("true")) {
/* 65 */         this.buildAST = true;
/*    */       }
/* 67 */       else if (paramToken.getText().equals("false")) {
/* 68 */         this.buildAST = false;
/*    */       }
/*    */       else {
/* 71 */         this.antlrTool.error("buildAST option must be true or false", getFilename(), paramToken.getLine(), paramToken.getColumn());
/*    */       }
/* 73 */       return true;
/*    */     }
/* 75 */     if (paramString.equals("ASTLabelType")) {
/* 76 */       super.setOption(paramString, paramToken);
/* 77 */       return true;
/*    */     }
/* 79 */     if (paramString.equals("className")) {
/* 80 */       super.setOption(paramString, paramToken);
/* 81 */       return true;
/*    */     }
/* 83 */     if (super.setOption(paramString, paramToken)) {
/* 84 */       return true;
/*    */     }
/* 86 */     this.antlrTool.error("Invalid option: " + paramString, getFilename(), paramToken.getLine(), paramToken.getColumn());
/* 87 */     return false;
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     antlr.TreeWalkerGrammar
 * JD-Core Version:    0.6.2
 */