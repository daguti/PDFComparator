/*    */ package org.antlr.gunit;
/*    */ 
/*    */ public class gUnitTestResult
/*    */ {
/*    */   private boolean success;
/*    */   private String output;
/*    */   private String error;
/*    */   private String returned;
/*    */   private boolean isLexerTest;
/*    */ 
/*    */   public gUnitTestResult(boolean success, String output)
/*    */   {
/* 39 */     this.success = success;
/* 40 */     this.output = output;
/*    */   }
/*    */ 
/*    */   public gUnitTestResult(boolean success, String output, boolean isLexerTest) {
/* 44 */     this(success, output);
/* 45 */     this.isLexerTest = isLexerTest;
/*    */   }
/*    */ 
/*    */   public gUnitTestResult(boolean success, String output, String returned) {
/* 49 */     this(success, output);
/* 50 */     this.returned = returned;
/*    */   }
/*    */ 
/*    */   public boolean isSuccess() {
/* 54 */     return this.success;
/*    */   }
/*    */ 
/*    */   public String getOutput() {
/* 58 */     return this.output;
/*    */   }
/*    */ 
/*    */   public String getError() {
/* 62 */     return this.error;
/*    */   }
/*    */ 
/*    */   public String getReturned() {
/* 66 */     return this.returned;
/*    */   }
/*    */ 
/*    */   public boolean isLexerTest() {
/* 70 */     return this.isLexerTest;
/*    */   }
/*    */ 
/*    */   public void setError(String error) {
/* 74 */     this.error = error;
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.antlr.gunit.gUnitTestResult
 * JD-Core Version:    0.6.2
 */