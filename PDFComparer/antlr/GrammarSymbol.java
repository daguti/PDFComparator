/*    */ package antlr;
/*    */ 
/*    */ abstract class GrammarSymbol
/*    */ {
/*    */   protected String id;
/*    */ 
/*    */   public GrammarSymbol()
/*    */   {
/*    */   }
/*    */ 
/*    */   public GrammarSymbol(String paramString)
/*    */   {
/* 20 */     this.id = paramString;
/*    */   }
/*    */ 
/*    */   public String getId() {
/* 24 */     return this.id;
/*    */   }
/*    */ 
/*    */   public void setId(String paramString) {
/* 28 */     this.id = paramString;
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     antlr.GrammarSymbol
 * JD-Core Version:    0.6.2
 */