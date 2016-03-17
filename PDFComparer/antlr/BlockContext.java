/*    */ package antlr;
/*    */ 
/*    */ import antlr.collections.impl.Vector;
/*    */ 
/*    */ class BlockContext
/*    */ {
/*    */   AlternativeBlock block;
/*    */   int altNum;
/*    */   BlockEndElement blockEnd;
/*    */ 
/*    */   public void addAlternativeElement(AlternativeElement paramAlternativeElement)
/*    */   {
/* 22 */     currentAlt().addElement(paramAlternativeElement);
/*    */   }
/*    */ 
/*    */   public Alternative currentAlt() {
/* 26 */     return (Alternative)this.block.alternatives.elementAt(this.altNum);
/*    */   }
/*    */ 
/*    */   public AlternativeElement currentElement() {
/* 30 */     return currentAlt().tail;
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     antlr.BlockContext
 * JD-Core Version:    0.6.2
 */