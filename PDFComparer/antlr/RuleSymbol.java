/*    */ package antlr;
/*    */ 
/*    */ import antlr.collections.impl.Vector;
/*    */ 
/*    */ class RuleSymbol extends GrammarSymbol
/*    */ {
/*    */   RuleBlock block;
/*    */   boolean defined;
/*    */   Vector references;
/*    */   String access;
/*    */   String comment;
/*    */ 
/*    */   public RuleSymbol(String paramString)
/*    */   {
/* 22 */     super(paramString);
/* 23 */     this.references = new Vector();
/*    */   }
/*    */ 
/*    */   public void addReference(RuleRefElement paramRuleRefElement) {
/* 27 */     this.references.appendElement(paramRuleRefElement);
/*    */   }
/*    */ 
/*    */   public RuleBlock getBlock() {
/* 31 */     return this.block;
/*    */   }
/*    */ 
/*    */   public RuleRefElement getReference(int paramInt) {
/* 35 */     return (RuleRefElement)this.references.elementAt(paramInt);
/*    */   }
/*    */ 
/*    */   public boolean isDefined() {
/* 39 */     return this.defined;
/*    */   }
/*    */ 
/*    */   public int numReferences() {
/* 43 */     return this.references.size();
/*    */   }
/*    */ 
/*    */   public void setBlock(RuleBlock paramRuleBlock) {
/* 47 */     this.block = paramRuleBlock;
/*    */   }
/*    */ 
/*    */   public void setDefined() {
/* 51 */     this.defined = true;
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     antlr.RuleSymbol
 * JD-Core Version:    0.6.2
 */