/*     */ package org.apache.pdfbox.filter;
/*     */ 
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.util.zip.DataFormatException;
/*     */ import java.util.zip.DeflaterOutputStream;
/*     */ import java.util.zip.Inflater;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.commons.logging.LogFactory;
/*     */ import org.apache.pdfbox.cos.COSArray;
/*     */ import org.apache.pdfbox.cos.COSBase;
/*     */ import org.apache.pdfbox.cos.COSDictionary;
/*     */ import org.apache.pdfbox.cos.COSName;
/*     */ 
/*     */ public class FlateFilter
/*     */   implements Filter
/*     */ {
/*  48 */   private static final Log LOG = LogFactory.getLog(FlateFilter.class);
/*     */   private static final int BUFFER_SIZE = 16348;
/*     */ 
/*     */   public void decode(InputStream compressedData, OutputStream result, COSDictionary options, int filterIndex)
/*     */     throws IOException
/*     */   {
/*  58 */     COSBase baseObj = options.getDictionaryObject(COSName.DECODE_PARMS, COSName.DP);
/*  59 */     COSDictionary dict = null;
/*  60 */     if ((baseObj instanceof COSDictionary))
/*     */     {
/*  62 */       dict = (COSDictionary)baseObj;
/*     */     }
/*  64 */     else if ((baseObj instanceof COSArray))
/*     */     {
/*  66 */       COSArray paramArray = (COSArray)baseObj;
/*  67 */       if (filterIndex < paramArray.size())
/*     */       {
/*  69 */         dict = (COSDictionary)paramArray.getObject(filterIndex);
/*     */       }
/*     */     }
/*  72 */     else if (baseObj != null)
/*     */     {
/*  74 */       throw new IOException("Error: Expected COSArray or COSDictionary and not " + baseObj.getClass().getName());
/*     */     }
/*     */ 
/*  78 */     int predictor = -1;
/*  79 */     if (dict != null)
/*     */     {
/*  81 */       predictor = dict.getInt(COSName.PREDICTOR);
/*     */     }
/*     */ 
/*     */     try
/*     */     {
/*  86 */       if (predictor > 1)
/*     */       {
/*  88 */         int colors = Math.min(dict.getInt(COSName.COLORS, 1), 32);
/*  89 */         int bitsPerPixel = dict.getInt(COSName.BITS_PER_COMPONENT, 8);
/*  90 */         int columns = dict.getInt(COSName.COLUMNS, 1);
/*  91 */         ByteArrayOutputStream baos = new ByteArrayOutputStream();
/*  92 */         decompress(compressedData, baos);
/*  93 */         ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
/*  94 */         Predictor.decodePredictor(predictor, colors, bitsPerPixel, columns, bais, result);
/*  95 */         result.flush();
/*  96 */         baos.reset();
/*  97 */         bais.reset();
/*     */       }
/*     */       else
/*     */       {
/* 101 */         decompress(compressedData, result);
/*     */       }
/*     */ 
/*     */     }
/*     */     catch (DataFormatException exception)
/*     */     {
/* 107 */       LOG.error("FlateFilter: stop reading corrupt stream due to a DataFormatException");
/*     */ 
/* 109 */       IOException io = new IOException();
/* 110 */       io.initCause(exception);
/* 111 */       throw io;
/*     */     }
/*     */   }
/*     */ 
/*     */   private void decompress(InputStream in, OutputStream out)
/*     */     throws IOException, DataFormatException
/*     */   {
/* 119 */     byte[] buf = new byte[2048];
/* 120 */     int read = in.read(buf);
/* 121 */     if (read > 0)
/*     */     {
/* 123 */       Inflater inflater = new Inflater();
/* 124 */       inflater.setInput(buf, 0, read);
/* 125 */       byte[] res = new byte[2048];
/*     */       while (true)
/*     */       {
/* 128 */         int resRead = inflater.inflate(res);
/* 129 */         if (resRead != 0)
/*     */         {
/* 131 */           out.write(res, 0, resRead);
/*     */         }
/*     */         else {
/* 134 */           if ((inflater.finished()) || (inflater.needsDictionary()) || (in.available() == 0))
/*     */           {
/*     */             break;
/*     */           }
/* 138 */           read = in.read(buf);
/* 139 */           inflater.setInput(buf, 0, read);
/*     */         }
/*     */       }
/*     */     }
/* 142 */     out.close();
/*     */   }
/*     */ 
/*     */   public void encode(InputStream rawData, OutputStream result, COSDictionary options, int filterIndex)
/*     */     throws IOException
/*     */   {
/* 151 */     DeflaterOutputStream out = new DeflaterOutputStream(result);
/* 152 */     int amountRead = 0;
/* 153 */     int mayRead = rawData.available();
/* 154 */     if (mayRead > 0)
/*     */     {
/* 156 */       byte[] buffer = new byte[Math.min(mayRead, 16348)];
/* 157 */       while ((amountRead = rawData.read(buffer, 0, Math.min(mayRead, 16348))) != -1)
/*     */       {
/* 159 */         out.write(buffer, 0, amountRead);
/*     */       }
/*     */     }
/* 162 */     out.close();
/* 163 */     result.flush();
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.filter.FlateFilter
 * JD-Core Version:    0.6.2
 */