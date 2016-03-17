/*    */ package antlr;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.io.InputStream;
/*    */ 
/*    */ public class ByteBuffer extends InputBuffer
/*    */ {
/*    */   public transient InputStream input;
/*    */ 
/*    */   public ByteBuffer(InputStream paramInputStream)
/*    */   {
/* 36 */     this.input = paramInputStream;
/*    */   }
/*    */ 
/*    */   public void fill(int paramInt) throws CharStreamException
/*    */   {
/*    */     try {
/* 42 */       syncConsume();
/*    */ 
/* 44 */       while (this.queue.nbrEntries < paramInt + this.markerOffset)
/*    */       {
/* 46 */         this.queue.append((char)this.input.read());
/*    */       }
/*    */     }
/*    */     catch (IOException localIOException) {
/* 50 */       throw new CharStreamIOException(localIOException);
/*    */     }
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     antlr.ByteBuffer
 * JD-Core Version:    0.6.2
 */