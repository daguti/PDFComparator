/*    */ package antlr;
/*    */ 
/*    */ class JavaBlockFinishingInfo
/*    */ {
/*    */   String postscript;
/*    */   boolean generatedSwitch;
/*    */   boolean generatedAnIf;
/*    */   boolean needAnErrorClause;
/*    */ 
/*    */   public JavaBlockFinishingInfo()
/*    */   {
/* 23 */     this.postscript = null;
/* 24 */     this.generatedSwitch = (this.generatedSwitch = 0);
/* 25 */     this.needAnErrorClause = true;
/*    */   }
/*    */ 
/*    */   public JavaBlockFinishingInfo(String paramString, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3) {
/* 29 */     this.postscript = paramString;
/* 30 */     this.generatedSwitch = paramBoolean1;
/* 31 */     this.generatedAnIf = paramBoolean2;
/* 32 */     this.needAnErrorClause = paramBoolean3;
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     antlr.JavaBlockFinishingInfo
 * JD-Core Version:    0.6.2
 */