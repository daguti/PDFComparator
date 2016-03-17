/*     */ package com.itextpdf.text.pdf;
/*     */ 
/*     */ import com.itextpdf.text.error_messages.MessageLocalization;
/*     */ import com.itextpdf.text.exceptions.UnsupportedPdfException;
/*     */ import com.itextpdf.text.pdf.codec.TIFFFaxDecoder;
/*     */ import com.itextpdf.text.pdf.codec.TIFFFaxDecompressor;
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ 
/*     */ public final class FilterHandlers
/*     */ {
/*  93 */   private static final Map<PdfName, FilterHandler> defaults = Collections.unmodifiableMap(map);
/*     */ 
/*     */   public static Map<PdfName, FilterHandler> getDefaultFilterHandlers()
/*     */   {
/* 100 */     return defaults;
/*     */   }
/*     */ 
/*     */   static
/*     */   {
/*  80 */     HashMap map = new HashMap();
/*     */ 
/*  82 */     map.put(PdfName.FLATEDECODE, new Filter_FLATEDECODE(null));
/*  83 */     map.put(PdfName.FL, new Filter_FLATEDECODE(null));
/*  84 */     map.put(PdfName.ASCIIHEXDECODE, new Filter_ASCIIHEXDECODE(null));
/*  85 */     map.put(PdfName.AHX, new Filter_ASCIIHEXDECODE(null));
/*  86 */     map.put(PdfName.ASCII85DECODE, new Filter_ASCII85DECODE(null));
/*  87 */     map.put(PdfName.A85, new Filter_ASCII85DECODE(null));
/*  88 */     map.put(PdfName.LZWDECODE, new Filter_LZWDECODE(null));
/*  89 */     map.put(PdfName.CCITTFAXDECODE, new Filter_CCITTFAXDECODE(null));
/*  90 */     map.put(PdfName.CRYPT, new Filter_DoNothing(null));
/*  91 */     map.put(PdfName.RUNLENGTHDECODE, new Filter_RUNLENGTHDECODE(null));
/*     */   }
/*     */ 
/*     */   private static class Filter_RUNLENGTHDECODE
/*     */     implements FilterHandlers.FilterHandler
/*     */   {
/*     */     public byte[] decode(byte[] b, PdfName filterName, PdfObject decodeParams, PdfDictionary streamDictionary)
/*     */       throws IOException
/*     */     {
/* 221 */       ByteArrayOutputStream baos = new ByteArrayOutputStream();
/* 222 */       byte dupCount = -1;
/* 223 */       for (int i = 0; i < b.length; i++) {
/* 224 */         dupCount = b[i];
/* 225 */         if (dupCount == -128)
/*     */           break;
/* 227 */         if ((dupCount >= 0) && (dupCount <= 127)) {
/* 228 */           int bytesToCopy = dupCount + 1;
/* 229 */           baos.write(b, i, bytesToCopy);
/* 230 */           i += bytesToCopy;
/*     */         }
/*     */         else {
/* 233 */           i++;
/* 234 */           for (int j = 0; j < 1 - dupCount; j++) {
/* 235 */             baos.write(b[i]);
/*     */           }
/*     */         }
/*     */       }
/*     */ 
/* 240 */       return baos.toByteArray();
/*     */     }
/*     */   }
/*     */ 
/*     */   private static class Filter_DoNothing
/*     */     implements FilterHandlers.FilterHandler
/*     */   {
/*     */     public byte[] decode(byte[] b, PdfName filterName, PdfObject decodeParams, PdfDictionary streamDictionary)
/*     */       throws IOException
/*     */     {
/* 210 */       return b;
/*     */     }
/*     */   }
/*     */ 
/*     */   private static class Filter_CCITTFAXDECODE
/*     */     implements FilterHandlers.FilterHandler
/*     */   {
/*     */     public byte[] decode(byte[] b, PdfName filterName, PdfObject decodeParams, PdfDictionary streamDictionary)
/*     */       throws IOException
/*     */     {
/* 151 */       PdfNumber wn = (PdfNumber)PdfReader.getPdfObjectRelease(streamDictionary.get(PdfName.WIDTH));
/* 152 */       PdfNumber hn = (PdfNumber)PdfReader.getPdfObjectRelease(streamDictionary.get(PdfName.HEIGHT));
/* 153 */       if ((wn == null) || (hn == null))
/* 154 */         throw new UnsupportedPdfException(MessageLocalization.getComposedMessage("filter.ccittfaxdecode.is.only.supported.for.images", new Object[0]));
/* 155 */       int width = wn.intValue();
/* 156 */       int height = hn.intValue();
/*     */ 
/* 158 */       PdfDictionary param = (decodeParams instanceof PdfDictionary) ? (PdfDictionary)decodeParams : null;
/* 159 */       int k = 0;
/* 160 */       boolean blackIs1 = false;
/* 161 */       boolean byteAlign = false;
/* 162 */       if (param != null) {
/* 163 */         PdfNumber kn = param.getAsNumber(PdfName.K);
/* 164 */         if (kn != null)
/* 165 */           k = kn.intValue();
/* 166 */         PdfBoolean bo = param.getAsBoolean(PdfName.BLACKIS1);
/* 167 */         if (bo != null)
/* 168 */           blackIs1 = bo.booleanValue();
/* 169 */         bo = param.getAsBoolean(PdfName.ENCODEDBYTEALIGN);
/* 170 */         if (bo != null)
/* 171 */           byteAlign = bo.booleanValue();
/*     */       }
/* 173 */       byte[] outBuf = new byte[(width + 7) / 8 * height];
/* 174 */       TIFFFaxDecompressor decoder = new TIFFFaxDecompressor();
/* 175 */       if ((k == 0) || (k > 0)) {
/* 176 */         int tiffT4Options = k > 0 ? 1 : 0;
/* 177 */         tiffT4Options |= (byteAlign ? 4 : 0);
/* 178 */         decoder.SetOptions(1, 3, tiffT4Options, 0);
/* 179 */         decoder.decodeRaw(outBuf, b, width, height);
/* 180 */         if (decoder.fails > 0) {
/* 181 */           byte[] outBuf2 = new byte[(width + 7) / 8 * height];
/* 182 */           int oldFails = decoder.fails;
/* 183 */           decoder.SetOptions(1, 2, tiffT4Options, 0);
/* 184 */           decoder.decodeRaw(outBuf2, b, width, height);
/* 185 */           if (decoder.fails < oldFails)
/* 186 */             outBuf = outBuf2;
/*     */         }
/*     */       }
/*     */       else
/*     */       {
/* 191 */         TIFFFaxDecoder deca = new TIFFFaxDecoder(1, width, height);
/* 192 */         deca.decodeT6(outBuf, b, 0, height, 0L);
/*     */       }
/* 194 */       if (!blackIs1) {
/* 195 */         int len = outBuf.length;
/* 196 */         for (int t = 0; t < len; t++)
/*     */         {
/*     */           int tmp372_370 = t;
/*     */           byte[] tmp372_368 = outBuf; tmp372_368[tmp372_370] = ((byte)(tmp372_368[tmp372_370] ^ 0xFF));
/*     */         }
/*     */       }
/* 200 */       b = outBuf;
/* 201 */       return b;
/*     */     }
/*     */   }
/*     */ 
/*     */   private static class Filter_LZWDECODE
/*     */     implements FilterHandlers.FilterHandler
/*     */   {
/*     */     public byte[] decode(byte[] b, PdfName filterName, PdfObject decodeParams, PdfDictionary streamDictionary)
/*     */       throws IOException
/*     */     {
/* 139 */       b = PdfReader.LZWDecode(b);
/* 140 */       b = PdfReader.decodePredictor(b, decodeParams);
/* 141 */       return b;
/*     */     }
/*     */   }
/*     */ 
/*     */   private static class Filter_ASCII85DECODE
/*     */     implements FilterHandlers.FilterHandler
/*     */   {
/*     */     public byte[] decode(byte[] b, PdfName filterName, PdfObject decodeParams, PdfDictionary streamDictionary)
/*     */       throws IOException
/*     */     {
/* 129 */       b = PdfReader.ASCII85Decode(b);
/* 130 */       return b;
/*     */     }
/*     */   }
/*     */ 
/*     */   private static class Filter_ASCIIHEXDECODE
/*     */     implements FilterHandlers.FilterHandler
/*     */   {
/*     */     public byte[] decode(byte[] b, PdfName filterName, PdfObject decodeParams, PdfDictionary streamDictionary)
/*     */       throws IOException
/*     */     {
/* 119 */       b = PdfReader.ASCIIHexDecode(b);
/* 120 */       return b;
/*     */     }
/*     */   }
/*     */ 
/*     */   private static class Filter_FLATEDECODE
/*     */     implements FilterHandlers.FilterHandler
/*     */   {
/*     */     public byte[] decode(byte[] b, PdfName filterName, PdfObject decodeParams, PdfDictionary streamDictionary)
/*     */       throws IOException
/*     */     {
/* 108 */       b = PdfReader.FlateDecode(b);
/* 109 */       b = PdfReader.decodePredictor(b, decodeParams);
/* 110 */       return b;
/*     */     }
/*     */   }
/*     */ 
/*     */   public static abstract interface FilterHandler
/*     */   {
/*     */     public abstract byte[] decode(byte[] paramArrayOfByte, PdfName paramPdfName, PdfObject paramPdfObject, PdfDictionary paramPdfDictionary)
/*     */       throws IOException;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.pdf.FilterHandlers
 * JD-Core Version:    0.6.2
 */