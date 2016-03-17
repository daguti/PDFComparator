/*    */ package org.apache.pdfbox.filter;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.io.InputStream;
/*    */ import java.io.OutputStream;
/*    */ import org.apache.pdfbox.cos.COSDictionary;
/*    */ 
/*    */ public class IdentityFilter
/*    */   implements Filter
/*    */ {
/*    */   private static final int BUFFER_SIZE = 1024;
/*    */ 
/*    */   public void decode(InputStream compressedData, OutputStream result, COSDictionary options, int filterIndex)
/*    */     throws IOException
/*    */   {
/* 41 */     byte[] buffer = new byte[1024];
/* 42 */     int amountRead = 0;
/* 43 */     while ((amountRead = compressedData.read(buffer, 0, 1024)) != -1)
/*    */     {
/* 45 */       result.write(buffer, 0, amountRead);
/*    */     }
/* 47 */     result.flush();
/*    */   }
/*    */ 
/*    */   public void encode(InputStream rawData, OutputStream result, COSDictionary options, int filterIndex)
/*    */     throws IOException
/*    */   {
/* 56 */     byte[] buffer = new byte[1024];
/* 57 */     int amountRead = 0;
/* 58 */     while ((amountRead = rawData.read(buffer, 0, 1024)) != -1)
/*    */     {
/* 60 */       result.write(buffer, 0, amountRead);
/*    */     }
/* 62 */     result.flush();
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.filter.IdentityFilter
 * JD-Core Version:    0.6.2
 */