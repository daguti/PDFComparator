/*    */ package org.apache.pdfbox.io;
/*    */ 
/*    */ import java.io.ByteArrayOutputStream;
/*    */ 
/*    */ public class FastByteArrayOutputStream extends ByteArrayOutputStream
/*    */ {
/*    */   public FastByteArrayOutputStream(int size)
/*    */   {
/* 36 */     super(size);
/*    */   }
/*    */ 
/*    */   public byte[] getByteArray()
/*    */   {
/* 46 */     return this.buf;
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.io.FastByteArrayOutputStream
 * JD-Core Version:    0.6.2
 */