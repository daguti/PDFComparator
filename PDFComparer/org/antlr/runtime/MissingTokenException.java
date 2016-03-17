/*    */ package org.antlr.runtime;
/*    */ 
/*    */ public class MissingTokenException extends MismatchedTokenException
/*    */ {
/*    */   public Object inserted;
/*    */ 
/*    */   public MissingTokenException()
/*    */   {
/*    */   }
/*    */ 
/*    */   public MissingTokenException(int expecting, IntStream input, Object inserted)
/*    */   {
/* 39 */     super(expecting, input);
/* 40 */     this.inserted = inserted;
/*    */   }
/*    */ 
/*    */   public int getMissingType() {
/* 44 */     return this.expecting;
/*    */   }
/*    */ 
/*    */   public String toString() {
/* 48 */     if ((this.inserted != null) && (this.token != null)) {
/* 49 */       return "MissingTokenException(inserted " + this.inserted + " at " + this.token.getText() + ")";
/*    */     }
/* 51 */     if (this.token != null) {
/* 52 */       return "MissingTokenException(at " + this.token.getText() + ")";
/*    */     }
/* 54 */     return "MissingTokenException";
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.antlr.runtime.MissingTokenException
 * JD-Core Version:    0.6.2
 */