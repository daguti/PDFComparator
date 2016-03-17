/*     */ package org.apache.pdfbox.filter;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.commons.logging.LogFactory;
/*     */ import org.apache.pdfbox.cos.COSDictionary;
/*     */ 
/*     */ public class RunLengthDecodeFilter
/*     */   implements Filter
/*     */ {
/*  54 */   private static final Log log = LogFactory.getLog(RunLengthDecodeFilter.class);
/*     */   private static final int RUN_LENGTH_EOD = 128;
/*     */ 
/*     */   public void decode(InputStream compressedData, OutputStream result, COSDictionary options, int filterIndex)
/*     */     throws IOException
/*     */   {
/*  72 */     int dupAmount = -1;
/*  73 */     byte[] buffer = new byte[''];
/*  74 */     while (((dupAmount = compressedData.read()) != -1) && (dupAmount != 128))
/*     */     {
/*  76 */       if (dupAmount <= 127)
/*     */       {
/*  78 */         int amountToCopy = dupAmount + 1;
/*  79 */         int compressedRead = 0;
/*  80 */         while (amountToCopy > 0)
/*     */         {
/*  82 */           compressedRead = compressedData.read(buffer, 0, amountToCopy);
/*  83 */           result.write(buffer, 0, compressedRead);
/*  84 */           amountToCopy -= compressedRead;
/*     */         }
/*     */       }
/*     */       else
/*     */       {
/*  89 */         int dupByte = compressedData.read();
/*  90 */         for (int i = 0; i < 257 - dupAmount; i++)
/*     */         {
/*  92 */           result.write(dupByte);
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public void encode(InputStream rawData, OutputStream result, COSDictionary options, int filterIndex)
/*     */     throws IOException
/*     */   {
/* 104 */     log.warn("RunLengthDecodeFilter.encode is not implemented yet, skipping this stream.");
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.filter.RunLengthDecodeFilter
 * JD-Core Version:    0.6.2
 */