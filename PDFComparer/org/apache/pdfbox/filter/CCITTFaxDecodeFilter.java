/*     */ package org.apache.pdfbox.filter;
/*     */ 
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.commons.logging.LogFactory;
/*     */ import org.apache.pdfbox.cos.COSArray;
/*     */ import org.apache.pdfbox.cos.COSBase;
/*     */ import org.apache.pdfbox.cos.COSDictionary;
/*     */ import org.apache.pdfbox.cos.COSName;
/*     */ import org.apache.pdfbox.io.IOUtils;
/*     */ import org.apache.pdfbox.io.ccitt.CCITTFaxG31DDecodeInputStream;
/*     */ import org.apache.pdfbox.io.ccitt.FillOrderChangeInputStream;
/*     */ 
/*     */ public class CCITTFaxDecodeFilter
/*     */   implements Filter
/*     */ {
/*  47 */   private static final Log log = LogFactory.getLog(CCITTFaxDecodeFilter.class);
/*     */ 
/*     */   public void decode(InputStream compressedData, OutputStream result, COSDictionary options, int filterIndex)
/*     */     throws IOException
/*     */   {
/*  63 */     COSBase decodeP = options.getDictionaryObject(COSName.DECODE_PARMS, COSName.DP);
/*  64 */     COSDictionary decodeParms = null;
/*  65 */     if ((decodeP instanceof COSDictionary))
/*     */     {
/*  67 */       decodeParms = (COSDictionary)decodeP;
/*     */     }
/*  69 */     else if ((decodeP instanceof COSArray))
/*     */     {
/*  71 */       decodeParms = (COSDictionary)((COSArray)decodeP).getObject(filterIndex);
/*     */     }
/*  73 */     int cols = decodeParms.getInt(COSName.COLUMNS, 1728);
/*  74 */     int rows = decodeParms.getInt(COSName.ROWS, 0);
/*  75 */     int height = options.getInt(COSName.HEIGHT, COSName.H, 0);
/*  76 */     if ((rows > 0) && (height > 0))
/*     */     {
/*  79 */       rows = Math.min(rows, height);
/*     */     }
/*     */     else
/*     */     {
/*  84 */       rows = Math.max(rows, height);
/*     */     }
/*  86 */     int k = decodeParms.getInt(COSName.K, 0);
/*  87 */     boolean encodedByteAlign = decodeParms.getBoolean(COSName.ENCODED_BYTE_ALIGN, false);
/*  88 */     int arraySize = (cols + 7) / 8 * rows;
/*  89 */     TIFFFaxDecoder faxDecoder = new TIFFFaxDecoder(1, cols, rows);
/*     */ 
/*  91 */     long tiffOptions = 0L;
/*  92 */     byte[] compressed = IOUtils.toByteArray(compressedData);
/*  93 */     byte[] decompressed = null;
/*  94 */     if (k == 0)
/*     */     {
/*  96 */       InputStream in = new CCITTFaxG31DDecodeInputStream(new ByteArrayInputStream(compressed), cols, encodedByteAlign);
/*     */ 
/*  98 */       in = new FillOrderChangeInputStream(in);
/*  99 */       decompressed = IOUtils.toByteArray(in);
/* 100 */       in.close();
/*     */     }
/* 102 */     else if (k > 0)
/*     */     {
/* 104 */       decompressed = new byte[arraySize];
/* 105 */       faxDecoder.decode2D(decompressed, compressed, 0, rows, tiffOptions);
/*     */     }
/* 107 */     else if (k < 0)
/*     */     {
/* 109 */       decompressed = new byte[arraySize];
/* 110 */       faxDecoder.decodeT6(decompressed, compressed, 0, rows, tiffOptions, encodedByteAlign);
/*     */     }
/*     */ 
/* 114 */     boolean blackIsOne = decodeParms.getBoolean(COSName.BLACK_IS_1, false);
/* 115 */     if (!blackIsOne)
/*     */     {
/* 121 */       invertBitmap(decompressed);
/*     */     }
/*     */ 
/* 125 */     if (!options.containsKey(COSName.COLORSPACE))
/*     */     {
/* 127 */       options.setName(COSName.COLORSPACE, COSName.DEVICEGRAY.getName());
/*     */     }
/*     */ 
/* 130 */     result.write(decompressed);
/*     */   }
/*     */ 
/*     */   public void encode(InputStream rawData, OutputStream result, COSDictionary options, int filterIndex)
/*     */     throws IOException
/*     */   {
/* 139 */     log.warn("CCITTFaxDecode.encode is not implemented yet, skipping this stream.");
/*     */   }
/*     */ 
/*     */   private void invertBitmap(byte[] bufferData)
/*     */   {
/* 144 */     int i = 0; for (int c = bufferData.length; i < c; i++)
/*     */     {
/* 146 */       bufferData[i] = ((byte)((bufferData[i] ^ 0xFFFFFFFF) & 0xFF));
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.filter.CCITTFaxDecodeFilter
 * JD-Core Version:    0.6.2
 */