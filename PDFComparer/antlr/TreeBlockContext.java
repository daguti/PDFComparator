/*    */ package antlr;
/*    */ 
/*    */ class TreeBlockContext extends BlockContext
/*    */ {
/* 22 */   protected boolean nextElementIsRoot = true;
/*    */ 
/*    */   public void addAlternativeElement(AlternativeElement paramAlternativeElement)
/*    */   {
/* 26 */     TreeElement localTreeElement = (TreeElement)this.block;
/* 27 */     if (this.nextElementIsRoot) {
/* 28 */       localTreeElement.root = ((GrammarAtom)paramAlternativeElement);
/* 29 */       this.nextElementIsRoot = false;
/*    */     }
/*    */     else {
/* 32 */       super.addAlternativeElement(paramAlternativeElement);
/*    */     }
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     antlr.TreeBlockContext
 * JD-Core Version:    0.6.2
 */