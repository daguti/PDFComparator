/*    */ package antlr;
/*    */ 
/*    */ class Alternative
/*    */ {
/*    */   AlternativeElement head;
/*    */   AlternativeElement tail;
/*    */   protected SynPredBlock synPred;
/*    */   protected String semPred;
/*    */   protected ExceptionSpec exceptionSpec;
/*    */   protected Lookahead[] cache;
/*    */   protected int lookaheadDepth;
/* 34 */   protected Token treeSpecifier = null;
/*    */   private boolean doAutoGen;
/*    */ 
/*    */   public Alternative()
/*    */   {
/*    */   }
/*    */ 
/*    */   public Alternative(AlternativeElement paramAlternativeElement)
/*    */   {
/* 43 */     addElement(paramAlternativeElement);
/*    */   }
/*    */ 
/*    */   public void addElement(AlternativeElement paramAlternativeElement)
/*    */   {
/* 48 */     if (this.head == null) {
/* 49 */       this.head = (this.tail = paramAlternativeElement);
/*    */     }
/*    */     else {
/* 52 */       this.tail.next = paramAlternativeElement;
/* 53 */       this.tail = paramAlternativeElement;
/*    */     }
/*    */   }
/*    */ 
/*    */   public boolean atStart() {
/* 58 */     return this.head == null;
/*    */   }
/*    */ 
/*    */   public boolean getAutoGen()
/*    */   {
/* 63 */     return (this.doAutoGen) && (this.treeSpecifier == null);
/*    */   }
/*    */ 
/*    */   public Token getTreeSpecifier() {
/* 67 */     return this.treeSpecifier;
/*    */   }
/*    */ 
/*    */   public void setAutoGen(boolean paramBoolean) {
/* 71 */     this.doAutoGen = paramBoolean;
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     antlr.Alternative
 * JD-Core Version:    0.6.2
 */