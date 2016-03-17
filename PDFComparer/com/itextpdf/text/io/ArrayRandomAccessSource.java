/*    */ package com.itextpdf.text.io;
/*    */ 
/*    */ import java.io.IOException;
/*    */ 
/*    */ class ArrayRandomAccessSource
/*    */   implements RandomAccessSource
/*    */ {
/*    */   private byte[] array;
/*    */ 
/*    */   public ArrayRandomAccessSource(byte[] array)
/*    */   {
/* 55 */     if (array == null) throw new NullPointerException();
/* 56 */     this.array = array;
/*    */   }
/*    */ 
/*    */   public int get(long offset) {
/* 60 */     if (offset >= this.array.length) return -1;
/* 61 */     return 0xFF & this.array[((int)offset)];
/*    */   }
/*    */ 
/*    */   public int get(long offset, byte[] bytes, int off, int len) {
/* 65 */     if (this.array == null) throw new IllegalStateException("Already closed");
/*    */ 
/* 67 */     if (offset >= this.array.length) {
/* 68 */       return -1;
/*    */     }
/* 70 */     if (offset + len > this.array.length) {
/* 71 */       len = (int)(this.array.length - offset);
/*    */     }
/* 73 */     System.arraycopy(this.array, (int)offset, bytes, off, len);
/*    */ 
/* 75 */     return len;
/*    */   }
/*    */ 
/*    */   public long length()
/*    */   {
/* 80 */     return this.array.length;
/*    */   }
/*    */ 
/*    */   public void close() throws IOException {
/* 84 */     this.array = null;
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.io.ArrayRandomAccessSource
 * JD-Core Version:    0.6.2
 */