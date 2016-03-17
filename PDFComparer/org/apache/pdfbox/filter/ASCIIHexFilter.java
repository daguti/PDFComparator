/*     */ package org.apache.pdfbox.filter;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.commons.logging.LogFactory;
/*     */ import org.apache.pdfbox.cos.COSDictionary;
/*     */ 
/*     */ public class ASCIIHexFilter
/*     */   implements Filter
/*     */ {
/*  41 */   private static final Log log = LogFactory.getLog(ASCIIHexFilter.class);
/*     */ 
/* 109 */   private static final int[] REVERSE_HEX = { -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9, -1, -1, -1, -1, -1, -1, -1, 10, 11, 12, 13, 14, 15, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, 10, 11, 12, 13, 14, 15 };
/*     */ 
/*     */   private boolean isWhitespace(int c)
/*     */   {
/*  54 */     return (c == 0) || (c == 9) || (c == 10) || (c == 12) || (c == 13) || (c == 32);
/*     */   }
/*     */ 
/*     */   private boolean isEOD(int c)
/*     */   {
/*  59 */     return c == 62;
/*     */   }
/*     */ 
/*     */   public void decode(InputStream compressedData, OutputStream result, COSDictionary options, int filterIndex)
/*     */     throws IOException
/*     */   {
/*  68 */     int value = 0;
/*  69 */     int firstByte = 0;
/*  70 */     int secondByte = 0;
/*  71 */     while ((firstByte = compressedData.read()) != -1)
/*     */     {
/*  74 */       while (isWhitespace(firstByte))
/*     */       {
/*  76 */         firstByte = compressedData.read();
/*     */       }
/*  78 */       if ((firstByte == -1) || (isEOD(firstByte)))
/*     */       {
/*     */         break;
/*     */       }
/*     */ 
/*  83 */       if (REVERSE_HEX[firstByte] == -1)
/*     */       {
/*  85 */         log.error("Invalid Hex Code; int: " + firstByte + " char: " + (char)firstByte);
/*     */       }
/*  87 */       value = REVERSE_HEX[firstByte] * 16;
/*  88 */       secondByte = compressedData.read();
/*     */ 
/*  90 */       if ((secondByte == -1) || (isEOD(secondByte)))
/*     */       {
/*  93 */         result.write(value);
/*  94 */         break;
/*     */       }
/*  96 */       if (secondByte >= 0)
/*     */       {
/*  98 */         if (REVERSE_HEX[secondByte] == -1)
/*     */         {
/* 100 */           log.error("Invalid Hex Code; int: " + secondByte + " char: " + (char)secondByte);
/*     */         }
/* 102 */         value += REVERSE_HEX[secondByte];
/*     */       }
/* 104 */       result.write(value);
/*     */     }
/* 106 */     result.flush();
/*     */   }
/*     */ 
/*     */   public void encode(InputStream rawData, OutputStream result, COSDictionary options, int filterIndex)
/*     */     throws IOException
/*     */   {
/* 222 */     int byteRead = 0;
/* 223 */     while ((byteRead = rawData.read()) != -1)
/*     */     {
/* 225 */       int value = (byteRead + 256) % 256;
/* 226 */       result.write(org.apache.pdfbox.persistence.util.COSHEXTable.TABLE[value]);
/*     */     }
/* 228 */     result.flush();
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.filter.ASCIIHexFilter
 * JD-Core Version:    0.6.2
 */