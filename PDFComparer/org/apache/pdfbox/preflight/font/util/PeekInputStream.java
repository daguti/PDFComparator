/*    */ package org.apache.pdfbox.preflight.font.util;
/*    */ 
/*    */ import java.io.ByteArrayOutputStream;
/*    */ import java.io.IOException;
/*    */ import java.io.InputStream;
/*    */ import org.apache.commons.io.IOUtils;
/*    */ 
/*    */ public class PeekInputStream extends InputStream
/*    */ {
/* 32 */   private byte[] content = new byte[0];
/* 33 */   private int position = 0;
/*    */ 
/*    */   public PeekInputStream(InputStream source) throws IOException
/*    */   {
/* 37 */     ByteArrayOutputStream bos = new ByteArrayOutputStream();
/*    */     try
/*    */     {
/* 40 */       IOUtils.copyLarge(source, bos);
/* 41 */       this.content = bos.toByteArray();
/*    */     }
/*    */     finally
/*    */     {
/* 45 */       IOUtils.closeQuietly(source);
/* 46 */       IOUtils.closeQuietly(bos);
/*    */     }
/*    */   }
/*    */ 
/*    */   public int read()
/*    */     throws IOException
/*    */   {
/* 53 */     if (this.position >= this.content.length)
/*    */     {
/* 55 */       throw new IOException("No more content in this stream");
/*    */     }
/*    */ 
/* 58 */     int currentByte = this.content[this.position] & 0xFF;
/* 59 */     this.position += 1;
/* 60 */     return currentByte;
/*    */   }
/*    */ 
/*    */   public int peek() throws IOException
/*    */   {
/* 65 */     if (this.position >= this.content.length)
/*    */     {
/* 67 */       throw new IOException("No more content in this stream");
/*    */     }
/*    */ 
/* 70 */     return this.content[this.position] & 0xFF;
/*    */   }
/*    */ 
/*    */   public byte[] peek(int numberOfBytes) throws IOException
/*    */   {
/* 75 */     if ((numberOfBytes < 0) || (this.position + numberOfBytes >= this.content.length))
/*    */     {
/* 77 */       throw new IOException("No more content in this stream, can't return the next " + numberOfBytes + " bytes");
/*    */     }
/*    */ 
/* 80 */     byte[] nextBytes = new byte[numberOfBytes];
/* 81 */     System.arraycopy(this.content, this.position, nextBytes, 0, numberOfBytes);
/* 82 */     return nextBytes;
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.preflight.font.util.PeekInputStream
 * JD-Core Version:    0.6.2
 */