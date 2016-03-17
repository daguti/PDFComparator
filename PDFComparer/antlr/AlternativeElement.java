/*    */ package antlr;
/*    */ 
/*    */ abstract class AlternativeElement extends GrammarElement
/*    */ {
/*    */   AlternativeElement next;
/* 12 */   protected int autoGenType = 1;
/*    */   protected String enclosingRuleName;
/*    */ 
/*    */   public AlternativeElement(Grammar paramGrammar)
/*    */   {
/* 17 */     super(paramGrammar);
/*    */   }
/*    */ 
/*    */   public AlternativeElement(Grammar paramGrammar, Token paramToken) {
/* 21 */     super(paramGrammar, paramToken);
/*    */   }
/*    */ 
/*    */   public AlternativeElement(Grammar paramGrammar, Token paramToken, int paramInt) {
/* 25 */     super(paramGrammar, paramToken);
/* 26 */     this.autoGenType = paramInt;
/*    */   }
/*    */ 
/*    */   public int getAutoGenType() {
/* 30 */     return this.autoGenType;
/*    */   }
/*    */ 
/*    */   public void setAutoGenType(int paramInt) {
/* 34 */     this.autoGenType = paramInt;
/*    */   }
/*    */ 
/*    */   public String getLabel() {
/* 38 */     return null;
/*    */   }
/*    */ 
/*    */   public void setLabel(String paramString)
/*    */   {
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     antlr.AlternativeElement
 * JD-Core Version:    0.6.2
 */