/*    */ package antlr;
/*    */ 
/*    */ class CSharpBlockFinishingInfo
/*    */ {
/*    */   String postscript;
/*    */   boolean generatedSwitch;
/*    */   boolean generatedAnIf;
/*    */   boolean needAnErrorClause;
/*    */ 
/*    */   public CSharpBlockFinishingInfo()
/*    */   {
/* 29 */     this.postscript = null;
/* 30 */     this.generatedSwitch = (this.generatedSwitch = 0);
/* 31 */     this.needAnErrorClause = true;
/*    */   }
/*    */ 
/*    */   public CSharpBlockFinishingInfo(String paramString, boolean paramBoolean1, boolean paramBoolean2, boolean paramBoolean3)
/*    */   {
/* 36 */     this.postscript = paramString;
/* 37 */     this.generatedSwitch = paramBoolean1;
/* 38 */     this.generatedAnIf = paramBoolean2;
/* 39 */     this.needAnErrorClause = paramBoolean3;
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     antlr.CSharpBlockFinishingInfo
 * JD-Core Version:    0.6.2
 */