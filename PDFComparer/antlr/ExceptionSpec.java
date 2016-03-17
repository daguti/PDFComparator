/*    */ package antlr;
/*    */ 
/*    */ import antlr.collections.impl.Vector;
/*    */ 
/*    */ class ExceptionSpec
/*    */ {
/*    */   protected Token label;
/*    */   protected Vector handlers;
/*    */ 
/*    */   public ExceptionSpec(Token paramToken)
/*    */   {
/* 22 */     this.label = paramToken;
/* 23 */     this.handlers = new Vector();
/*    */   }
/*    */ 
/*    */   public void addHandler(ExceptionHandler paramExceptionHandler) {
/* 27 */     this.handlers.appendElement(paramExceptionHandler);
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     antlr.ExceptionSpec
 * JD-Core Version:    0.6.2
 */