/*    */ package org.antlr.runtime.tree;
/*    */ 
/*    */ public class RewriteCardinalityException extends RuntimeException
/*    */ {
/*    */   public String elementDescription;
/*    */ 
/*    */   public RewriteCardinalityException(String elementDescription)
/*    */   {
/* 38 */     this.elementDescription = elementDescription;
/*    */   }
/*    */ 
/*    */   public String getMessage() {
/* 42 */     if (this.elementDescription != null) {
/* 43 */       return this.elementDescription;
/*    */     }
/* 45 */     return null;
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.antlr.runtime.tree.RewriteCardinalityException
 * JD-Core Version:    0.6.2
 */