/*    */ package org.apache.pdfbox.filter;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.io.InputStream;
/*    */ import java.io.OutputStream;
/*    */ import org.apache.pdfbox.cos.COSDictionary;
/*    */ import org.apache.pdfbox.io.ASCII85InputStream;
/*    */ import org.apache.pdfbox.io.ASCII85OutputStream;
/*    */ 
/*    */ public class ASCII85Filter
/*    */   implements Filter
/*    */ {
/*    */   public void decode(InputStream compressedData, OutputStream result, COSDictionary options, int filterIndex)
/*    */     throws IOException
/*    */   {
/* 42 */     ASCII85InputStream is = null;
/*    */     try
/*    */     {
/* 45 */       is = new ASCII85InputStream(compressedData);
/* 46 */       byte[] buffer = new byte[1024];
/* 47 */       int amountRead = 0;
/* 48 */       while ((amountRead = is.read(buffer, 0, 1024)) != -1)
/*    */       {
/* 50 */         result.write(buffer, 0, amountRead);
/*    */       }
/* 52 */       result.flush();
/*    */     }
/*    */     finally
/*    */     {
/* 56 */       if (is != null)
/*    */       {
/* 58 */         is.close();
/*    */       }
/*    */     }
/*    */   }
/*    */ 
/*    */   public void encode(InputStream rawData, OutputStream result, COSDictionary options, int filterIndex)
/*    */     throws IOException
/*    */   {
/* 69 */     ASCII85OutputStream os = new ASCII85OutputStream(result);
/* 70 */     byte[] buffer = new byte[1024];
/* 71 */     int amountRead = 0;
/* 72 */     while ((amountRead = rawData.read(buffer, 0, 1024)) != -1)
/*    */     {
/* 74 */       os.write(buffer, 0, amountRead);
/*    */     }
/* 76 */     os.close();
/* 77 */     result.flush();
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.filter.ASCII85Filter
 * JD-Core Version:    0.6.2
 */