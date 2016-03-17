/*    */ package antlr;
/*    */ 
/*    */ class StringLiteralSymbol extends TokenSymbol
/*    */ {
/*    */   protected String label;
/*    */ 
/*    */   public StringLiteralSymbol(String paramString)
/*    */   {
/* 15 */     super(paramString);
/*    */   }
/*    */ 
/*    */   public String getLabel() {
/* 19 */     return this.label;
/*    */   }
/*    */ 
/*    */   public void setLabel(String paramString) {
/* 23 */     this.label = paramString;
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     antlr.StringLiteralSymbol
 * JD-Core Version:    0.6.2
 */