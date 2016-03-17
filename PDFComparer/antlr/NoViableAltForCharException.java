/*    */ package antlr;
/*    */ 
/*    */ public class NoViableAltForCharException extends RecognitionException
/*    */ {
/*    */   public char foundChar;
/*    */ 
/*    */   public NoViableAltForCharException(char paramChar, CharScanner paramCharScanner)
/*    */   {
/* 14 */     super("NoViableAlt", paramCharScanner.getFilename(), paramCharScanner.getLine(), paramCharScanner.getColumn());
/*    */ 
/* 16 */     this.foundChar = paramChar;
/*    */   }
/*    */ 
/*    */   /** @deprecated */
/*    */   public NoViableAltForCharException(char paramChar, String paramString, int paramInt) {
/* 21 */     this(paramChar, paramString, paramInt, -1);
/*    */   }
/*    */ 
/*    */   public NoViableAltForCharException(char paramChar, String paramString, int paramInt1, int paramInt2) {
/* 25 */     super("NoViableAlt", paramString, paramInt1, paramInt2);
/* 26 */     this.foundChar = paramChar;
/*    */   }
/*    */ 
/*    */   public String getMessage()
/*    */   {
/* 33 */     String str = "unexpected char: ";
/*    */ 
/* 41 */     if ((this.foundChar >= ' ') && (this.foundChar <= '~')) {
/* 42 */       str = str + '\'';
/* 43 */       str = str + this.foundChar;
/* 44 */       str = str + '\'';
/*    */     }
/*    */     else {
/* 47 */       str = str + "0x" + Integer.toHexString(this.foundChar).toUpperCase();
/*    */     }
/* 49 */     return str;
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     antlr.NoViableAltForCharException
 * JD-Core Version:    0.6.2
 */