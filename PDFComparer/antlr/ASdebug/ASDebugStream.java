/*    */ package antlr.ASdebug;
/*    */ 
/*    */ import antlr.Token;
/*    */ import antlr.TokenStream;
/*    */ 
/*    */ public final class ASDebugStream
/*    */ {
/*    */   public static String getEntireText(TokenStream paramTokenStream)
/*    */   {
/* 15 */     if ((paramTokenStream instanceof IASDebugStream))
/*    */     {
/* 17 */       IASDebugStream localIASDebugStream = (IASDebugStream)paramTokenStream;
/* 18 */       return localIASDebugStream.getEntireText();
/*    */     }
/* 20 */     return null;
/*    */   }
/*    */ 
/*    */   public static TokenOffsetInfo getOffsetInfo(TokenStream paramTokenStream, Token paramToken)
/*    */   {
/* 25 */     if ((paramTokenStream instanceof IASDebugStream))
/*    */     {
/* 27 */       IASDebugStream localIASDebugStream = (IASDebugStream)paramTokenStream;
/* 28 */       return localIASDebugStream.getOffsetInfo(paramToken);
/*    */     }
/* 30 */     return null;
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     antlr.ASdebug.ASDebugStream
 * JD-Core Version:    0.6.2
 */