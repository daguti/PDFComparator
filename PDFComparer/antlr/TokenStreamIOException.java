/*    */ package antlr;
/*    */ 
/*    */ import java.io.IOException;
/*    */ 
/*    */ public class TokenStreamIOException extends TokenStreamException
/*    */ {
/*    */   public IOException io;
/*    */ 
/*    */   public TokenStreamIOException(IOException paramIOException)
/*    */   {
/* 23 */     super(paramIOException.getMessage());
/* 24 */     this.io = paramIOException;
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     antlr.TokenStreamIOException
 * JD-Core Version:    0.6.2
 */