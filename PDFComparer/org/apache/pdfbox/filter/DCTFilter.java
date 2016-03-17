/*    */ package org.apache.pdfbox.filter;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.io.InputStream;
/*    */ import java.io.OutputStream;
/*    */ import org.apache.commons.logging.Log;
/*    */ import org.apache.commons.logging.LogFactory;
/*    */ import org.apache.pdfbox.cos.COSDictionary;
/*    */ 
/*    */ public class DCTFilter
/*    */   implements Filter
/*    */ {
/* 39 */   private static final Log log = LogFactory.getLog(DCTFilter.class);
/*    */ 
/*    */   public void decode(InputStream compressedData, OutputStream result, COSDictionary options, int filterIndex)
/*    */     throws IOException
/*    */   {
/* 47 */     log.warn("DCTFilter.decode is not implemented yet, skipping this stream.");
/*    */   }
/*    */ 
/*    */   public void encode(InputStream rawData, OutputStream result, COSDictionary options, int filterIndex)
/*    */     throws IOException
/*    */   {
/* 56 */     log.warn("DCTFilter.encode is not implemented yet, skipping this stream.");
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.filter.DCTFilter
 * JD-Core Version:    0.6.2
 */