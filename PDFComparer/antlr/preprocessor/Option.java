/*    */ package antlr.preprocessor;
/*    */ 
/*    */ class Option
/*    */ {
/*    */   protected String name;
/*    */   protected String rhs;
/*    */   protected Grammar enclosingGrammar;
/*    */ 
/*    */   public Option(String paramString1, String paramString2, Grammar paramGrammar)
/*    */   {
/* 18 */     this.name = paramString1;
/* 19 */     this.rhs = paramString2;
/* 20 */     setEnclosingGrammar(paramGrammar);
/*    */   }
/*    */ 
/*    */   public Grammar getEnclosingGrammar() {
/* 24 */     return this.enclosingGrammar;
/*    */   }
/*    */ 
/*    */   public String getName() {
/* 28 */     return this.name;
/*    */   }
/*    */ 
/*    */   public String getRHS() {
/* 32 */     return this.rhs;
/*    */   }
/*    */ 
/*    */   public void setEnclosingGrammar(Grammar paramGrammar) {
/* 36 */     this.enclosingGrammar = paramGrammar;
/*    */   }
/*    */ 
/*    */   public void setName(String paramString) {
/* 40 */     this.name = paramString;
/*    */   }
/*    */ 
/*    */   public void setRHS(String paramString) {
/* 44 */     this.rhs = paramString;
/*    */   }
/*    */ 
/*    */   public String toString() {
/* 48 */     return "\t" + this.name + "=" + this.rhs;
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     antlr.preprocessor.Option
 * JD-Core Version:    0.6.2
 */