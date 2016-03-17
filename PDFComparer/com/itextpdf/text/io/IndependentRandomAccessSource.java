/*    */ package com.itextpdf.text.io;
/*    */ 
/*    */ import java.io.IOException;
/*    */ 
/*    */ public class IndependentRandomAccessSource
/*    */   implements RandomAccessSource
/*    */ {
/*    */   private final RandomAccessSource source;
/*    */ 
/*    */   public IndependentRandomAccessSource(RandomAccessSource source)
/*    */   {
/* 64 */     this.source = source;
/*    */   }
/*    */ 
/*    */   public int get(long position)
/*    */     throws IOException
/*    */   {
/* 71 */     return this.source.get(position);
/*    */   }
/*    */ 
/*    */   public int get(long position, byte[] bytes, int off, int len)
/*    */     throws IOException
/*    */   {
/* 78 */     return this.source.get(position, bytes, off, len);
/*    */   }
/*    */ 
/*    */   public long length()
/*    */   {
/* 85 */     return this.source.length();
/*    */   }
/*    */ 
/*    */   public void close()
/*    */     throws IOException
/*    */   {
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.io.IndependentRandomAccessSource
 * JD-Core Version:    0.6.2
 */