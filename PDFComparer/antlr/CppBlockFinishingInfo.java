/*    */ package antlr;
/*    */ 
/*    */ class CppBlockFinishingInfo
/*    */ {
/*    */   String postscript;
/*    */   boolean generatedSwitch;
/*    */   boolean generatedAnIf;
/*    */   boolean needAnErrorClause;
/*    */ 
/*    */   public CppBlockFinishingInfo()
/*    */   {
/* 25 */     this.postscript = null;
/* 26 */     this.generatedSwitch = false;
/* 27 */     this.needAnErrorClause = true;
/*    */   }
/*    */   public CppBlockFinishingInfo(String paramString, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3) {
/* 30 */     this.postscript = paramString;
/* 31 */     this.generatedSwitch = paramBoolean1;
/* 32 */     this.generatedAnIf = paramBoolean2;
/* 33 */     this.needAnErrorClause = paramBoolean3;
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     antlr.CppBlockFinishingInfo
 * JD-Core Version:    0.6.2
 */