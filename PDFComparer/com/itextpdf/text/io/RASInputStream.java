/*    */ package com.itextpdf.text.io;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.io.InputStream;
/*    */ 
/*    */ public class RASInputStream extends InputStream
/*    */ {
/*    */   private final RandomAccessSource source;
/* 61 */   private long position = 0L;
/*    */ 
/*    */   public RASInputStream(RandomAccessSource source)
/*    */   {
/* 68 */     this.source = source;
/*    */   }
/*    */ 
/*    */   public int read(byte[] b, int off, int len)
/*    */     throws IOException
/*    */   {
/* 76 */     int count = this.source.get(this.position, b, off, len);
/* 77 */     this.position += count;
/* 78 */     return count;
/*    */   }
/*    */ 
/*    */   public int read()
/*    */     throws IOException
/*    */   {
/* 86 */     return this.source.get(this.position++);
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.io.RASInputStream
 * JD-Core Version:    0.6.2
 */