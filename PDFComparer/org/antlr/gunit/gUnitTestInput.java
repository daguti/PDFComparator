/*    */ package org.antlr.gunit;
/*    */ 
/*    */ public class gUnitTestInput
/*    */ {
/*    */   public String input;
/*    */   public boolean isFile;
/*    */   public int line;
/*    */ 
/*    */   public gUnitTestInput(String input, boolean isFile, int line)
/*    */   {
/* 37 */     this.input = input;
/* 38 */     this.isFile = isFile;
/* 39 */     this.line = line;
/*    */   }
/*    */ 
/*    */   public String getInputEscaped() {
/* 43 */     return JUnitCodeGen.escapeForJava(this.input);
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.antlr.gunit.gUnitTestInput
 * JD-Core Version:    0.6.2
 */