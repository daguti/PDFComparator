/*    */ package antlr;
/*    */ 
/*    */ import java.io.IOException;
/*    */ 
/*    */ public class CharStreamIOException extends CharStreamException
/*    */ {
/*    */   public IOException io;
/*    */ 
/*    */   public CharStreamIOException(IOException paramIOException)
/*    */   {
/* 19 */     super(paramIOException.getMessage());
/* 20 */     this.io = paramIOException;
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     antlr.CharStreamIOException
 * JD-Core Version:    0.6.2
 */