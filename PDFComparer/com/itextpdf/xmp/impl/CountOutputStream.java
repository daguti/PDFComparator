/*    */ package com.itextpdf.xmp.impl;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.io.OutputStream;
/*    */ 
/*    */ public final class CountOutputStream extends OutputStream
/*    */ {
/*    */   private final OutputStream out;
/* 47 */   private int bytesWritten = 0;
/*    */ 
/*    */   CountOutputStream(OutputStream out)
/*    */   {
/* 56 */     this.out = out;
/*    */   }
/*    */ 
/*    */   public void write(byte[] buf, int off, int len)
/*    */     throws IOException
/*    */   {
/* 66 */     this.out.write(buf, off, len);
/* 67 */     this.bytesWritten += len;
/*    */   }
/*    */ 
/*    */   public void write(byte[] buf)
/*    */     throws IOException
/*    */   {
/* 77 */     this.out.write(buf);
/* 78 */     this.bytesWritten += buf.length;
/*    */   }
/*    */ 
/*    */   public void write(int b)
/*    */     throws IOException
/*    */   {
/* 88 */     this.out.write(b);
/* 89 */     this.bytesWritten += 1;
/*    */   }
/*    */ 
/*    */   public int getBytesWritten()
/*    */   {
/* 98 */     return this.bytesWritten;
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.xmp.impl.CountOutputStream
 * JD-Core Version:    0.6.2
 */