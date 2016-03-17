/*    */ package antlr;
/*    */ 
/*    */ public class SemanticException extends RecognitionException
/*    */ {
/*    */   public SemanticException(String paramString)
/*    */   {
/* 12 */     super(paramString);
/*    */   }
/*    */ 
/*    */   /** @deprecated */
/*    */   public SemanticException(String paramString1, String paramString2, int paramInt) {
/* 17 */     this(paramString1, paramString2, paramInt, -1);
/*    */   }
/*    */ 
/*    */   public SemanticException(String paramString1, String paramString2, int paramInt1, int paramInt2) {
/* 21 */     super(paramString1, paramString2, paramInt1, paramInt2);
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     antlr.SemanticException
 * JD-Core Version:    0.6.2
 */