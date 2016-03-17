/*     */ package com.itextpdf.text.pdf.parser;
/*     */ 
/*     */ import com.itextpdf.text.pdf.PRTokeniser;
/*     */ import com.itextpdf.text.pdf.PdfArray;
/*     */ import com.itextpdf.text.pdf.PdfContentParser;
/*     */ import com.itextpdf.text.pdf.PdfDictionary;
/*     */ import com.itextpdf.text.pdf.PdfName;
/*     */ import com.itextpdf.text.pdf.PdfNumber;
/*     */ import com.itextpdf.text.pdf.PdfObject;
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ 
/*     */ public final class InlineImageUtils
/*     */ {
/*  88 */   private static final Map<PdfName, PdfName> inlineImageEntryAbbreviationMap = new HashMap();
/*     */   private static final Map<PdfName, PdfName> inlineImageColorSpaceAbbreviationMap;
/*     */   private static final Map<PdfName, PdfName> inlineImageFilterAbbreviationMap;
/*     */ 
/*     */   public static InlineImageInfo parseInlineImage(PdfContentParser ps, PdfDictionary colorSpaceDic)
/*     */     throws IOException
/*     */   {
/* 153 */     PdfDictionary inlineImageDictionary = parseInlineImageDictionary(ps);
/* 154 */     byte[] samples = parseInlineImageSamples(inlineImageDictionary, colorSpaceDic, ps);
/* 155 */     return new InlineImageInfo(samples, inlineImageDictionary);
/*     */   }
/*     */ 
/*     */   private static PdfDictionary parseInlineImageDictionary(PdfContentParser ps)
/*     */     throws IOException
/*     */   {
/* 167 */     PdfDictionary dictionary = new PdfDictionary();
/*     */ 
/* 169 */     for (PdfObject key = ps.readPRObject(); (key != null) && (!"ID".equals(key.toString())); key = ps.readPRObject()) {
/* 170 */       PdfObject value = ps.readPRObject();
/*     */ 
/* 172 */       PdfName resolvedKey = (PdfName)inlineImageEntryAbbreviationMap.get(key);
/* 173 */       if (resolvedKey == null) {
/* 174 */         resolvedKey = (PdfName)key;
/*     */       }
/* 176 */       dictionary.put(resolvedKey, getAlternateValue(resolvedKey, value));
/*     */     }
/*     */ 
/* 179 */     int ch = ps.getTokeniser().read();
/* 180 */     if (!PRTokeniser.isWhitespace(ch)) {
/* 181 */       throw new IOException("Unexpected character " + ch + " found after ID in inline image");
/*     */     }
/* 183 */     return dictionary;
/*     */   }
/*     */ 
/*     */   private static PdfObject getAlternateValue(PdfName key, PdfObject value)
/*     */   {
/* 193 */     if (key == PdfName.FILTER) {
/* 194 */       if ((value instanceof PdfName)) {
/* 195 */         PdfName altValue = (PdfName)inlineImageFilterAbbreviationMap.get(value);
/* 196 */         if (altValue != null)
/* 197 */           return altValue;
/* 198 */       } else if ((value instanceof PdfArray)) {
/* 199 */         PdfArray array = (PdfArray)value;
/* 200 */         PdfArray altArray = new PdfArray();
/* 201 */         int count = array.size();
/* 202 */         for (int i = 0; i < count; i++) {
/* 203 */           altArray.add(getAlternateValue(key, array.getPdfObject(i)));
/*     */         }
/* 205 */         return altArray;
/*     */       }
/* 207 */     } else if (key == PdfName.COLORSPACE) {
/* 208 */       PdfName altValue = (PdfName)inlineImageColorSpaceAbbreviationMap.get(value);
/* 209 */       if (altValue != null) {
/* 210 */         return altValue;
/*     */       }
/*     */     }
/* 213 */     return value;
/*     */   }
/*     */ 
/*     */   private static int getComponentsPerPixel(PdfName colorSpaceName, PdfDictionary colorSpaceDic)
/*     */   {
/* 221 */     if (colorSpaceName == null)
/* 222 */       return 1;
/* 223 */     if (colorSpaceName.equals(PdfName.DEVICEGRAY))
/* 224 */       return 1;
/* 225 */     if (colorSpaceName.equals(PdfName.DEVICERGB))
/* 226 */       return 3;
/* 227 */     if (colorSpaceName.equals(PdfName.DEVICECMYK)) {
/* 228 */       return 4;
/*     */     }
/* 230 */     if (colorSpaceDic != null) {
/* 231 */       PdfArray colorSpace = colorSpaceDic.getAsArray(colorSpaceName);
/* 232 */       if (colorSpace != null) {
/* 233 */         if (PdfName.INDEXED.equals(colorSpace.getAsName(0)))
/* 234 */           return 1;
/*     */       }
/*     */       else
/*     */       {
/* 238 */         PdfName tempName = colorSpaceDic.getAsName(colorSpaceName);
/* 239 */         if (tempName != null) {
/* 240 */           return getComponentsPerPixel(tempName, colorSpaceDic);
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/* 245 */     throw new IllegalArgumentException("Unexpected color space " + colorSpaceName);
/*     */   }
/*     */ 
/*     */   private static int computeBytesPerRow(PdfDictionary imageDictionary, PdfDictionary colorSpaceDic)
/*     */   {
/* 256 */     PdfNumber wObj = imageDictionary.getAsNumber(PdfName.WIDTH);
/* 257 */     PdfNumber bpcObj = imageDictionary.getAsNumber(PdfName.BITSPERCOMPONENT);
/* 258 */     int cpp = getComponentsPerPixel(imageDictionary.getAsName(PdfName.COLORSPACE), colorSpaceDic);
/*     */ 
/* 260 */     int w = wObj.intValue();
/* 261 */     int bpc = bpcObj != null ? bpcObj.intValue() : 1;
/*     */ 
/* 264 */     int bytesPerRow = (w * bpc * cpp + 7) / 8;
/*     */ 
/* 266 */     return bytesPerRow;
/*     */   }
/*     */ 
/*     */   private static byte[] parseUnfilteredSamples(PdfDictionary imageDictionary, PdfDictionary colorSpaceDic, PdfContentParser ps)
/*     */     throws IOException
/*     */   {
/* 282 */     if (imageDictionary.contains(PdfName.FILTER)) {
/* 283 */       throw new IllegalArgumentException("Dictionary contains filters");
/*     */     }
/* 285 */     PdfNumber h = imageDictionary.getAsNumber(PdfName.HEIGHT);
/*     */ 
/* 287 */     int bytesToRead = computeBytesPerRow(imageDictionary, colorSpaceDic) * h.intValue();
/* 288 */     byte[] bytes = new byte[bytesToRead];
/* 289 */     PRTokeniser tokeniser = ps.getTokeniser();
/*     */ 
/* 291 */     int shouldBeWhiteSpace = tokeniser.read();
/*     */ 
/* 294 */     int startIndex = 0;
/* 295 */     if ((!PRTokeniser.isWhitespace(shouldBeWhiteSpace)) || (shouldBeWhiteSpace == 0)) {
/* 296 */       bytes[0] = ((byte)shouldBeWhiteSpace);
/* 297 */       startIndex++;
/*     */     }
/* 299 */     for (int i = startIndex; i < bytesToRead; i++) {
/* 300 */       int ch = tokeniser.read();
/* 301 */       if (ch == -1) {
/* 302 */         throw new InlineImageParseException("End of content stream reached before end of image data");
/*     */       }
/* 304 */       bytes[i] = ((byte)ch);
/*     */     }
/* 306 */     PdfObject ei = ps.readPRObject();
/* 307 */     if (!ei.toString().equals("EI"))
/*     */     {
/* 310 */       PdfObject ei2 = ps.readPRObject();
/* 311 */       if (!ei2.toString().equals("EI"))
/* 312 */         throw new InlineImageParseException("EI not found after end of image data");
/*     */     }
/* 314 */     return bytes;
/*     */   }
/*     */ 
/*     */   private static byte[] parseInlineImageSamples(PdfDictionary imageDictionary, PdfDictionary colorSpaceDic, PdfContentParser ps)
/*     */     throws IOException
/*     */   {
/* 330 */     if (!imageDictionary.contains(PdfName.FILTER)) {
/* 331 */       return parseUnfilteredSamples(imageDictionary, colorSpaceDic, ps);
/*     */     }
/*     */ 
/* 341 */     ByteArrayOutputStream baos = new ByteArrayOutputStream();
/* 342 */     ByteArrayOutputStream accumulated = new ByteArrayOutputStream();
/*     */ 
/* 344 */     int found = 0;
/* 345 */     PRTokeniser tokeniser = ps.getTokeniser();
/*     */     int ch;
/* 347 */     while ((ch = tokeniser.read()) != -1) {
/* 348 */       if ((found == 0) && (PRTokeniser.isWhitespace(ch))) {
/* 349 */         found++;
/* 350 */         accumulated.write(ch);
/* 351 */       } else if ((found == 1) && (ch == 69)) {
/* 352 */         found++;
/* 353 */         accumulated.write(ch);
/* 354 */       } else if ((found == 1) && (PRTokeniser.isWhitespace(ch)))
/*     */       {
/* 359 */         baos.write(accumulated.toByteArray());
/* 360 */         accumulated.reset();
/* 361 */         accumulated.write(ch);
/* 362 */       } else if ((found == 2) && (ch == 73)) {
/* 363 */         found++;
/* 364 */         accumulated.write(ch);
/* 365 */       } else if ((found == 3) && (PRTokeniser.isWhitespace(ch))) {
/* 366 */         byte[] tmp = baos.toByteArray();
/*     */         try {
/* 368 */           new PdfImageObject(imageDictionary, tmp, colorSpaceDic);
/* 369 */           return tmp;
/*     */         }
/*     */         catch (Exception e) {
/* 372 */           baos.write(accumulated.toByteArray());
/* 373 */           accumulated.reset();
/*     */ 
/* 375 */           baos.write(ch);
/* 376 */           found = 0;
/*     */         }
/*     */       } else {
/* 379 */         baos.write(accumulated.toByteArray());
/* 380 */         accumulated.reset();
/*     */ 
/* 382 */         baos.write(ch);
/* 383 */         found = 0;
/*     */       }
/*     */     }
/* 386 */     throw new InlineImageParseException("Could not find image data or EI");
/*     */   }
/*     */ 
/*     */   static
/*     */   {
/*  91 */     inlineImageEntryAbbreviationMap.put(PdfName.BITSPERCOMPONENT, PdfName.BITSPERCOMPONENT);
/*  92 */     inlineImageEntryAbbreviationMap.put(PdfName.COLORSPACE, PdfName.COLORSPACE);
/*  93 */     inlineImageEntryAbbreviationMap.put(PdfName.DECODE, PdfName.DECODE);
/*  94 */     inlineImageEntryAbbreviationMap.put(PdfName.DECODEPARMS, PdfName.DECODEPARMS);
/*  95 */     inlineImageEntryAbbreviationMap.put(PdfName.FILTER, PdfName.FILTER);
/*  96 */     inlineImageEntryAbbreviationMap.put(PdfName.HEIGHT, PdfName.HEIGHT);
/*  97 */     inlineImageEntryAbbreviationMap.put(PdfName.IMAGEMASK, PdfName.IMAGEMASK);
/*  98 */     inlineImageEntryAbbreviationMap.put(PdfName.INTENT, PdfName.INTENT);
/*  99 */     inlineImageEntryAbbreviationMap.put(PdfName.INTERPOLATE, PdfName.INTERPOLATE);
/* 100 */     inlineImageEntryAbbreviationMap.put(PdfName.WIDTH, PdfName.WIDTH);
/*     */ 
/* 103 */     inlineImageEntryAbbreviationMap.put(new PdfName("BPC"), PdfName.BITSPERCOMPONENT);
/* 104 */     inlineImageEntryAbbreviationMap.put(new PdfName("CS"), PdfName.COLORSPACE);
/* 105 */     inlineImageEntryAbbreviationMap.put(new PdfName("D"), PdfName.DECODE);
/* 106 */     inlineImageEntryAbbreviationMap.put(new PdfName("DP"), PdfName.DECODEPARMS);
/* 107 */     inlineImageEntryAbbreviationMap.put(new PdfName("F"), PdfName.FILTER);
/* 108 */     inlineImageEntryAbbreviationMap.put(new PdfName("H"), PdfName.HEIGHT);
/* 109 */     inlineImageEntryAbbreviationMap.put(new PdfName("IM"), PdfName.IMAGEMASK);
/* 110 */     inlineImageEntryAbbreviationMap.put(new PdfName("I"), PdfName.INTERPOLATE);
/* 111 */     inlineImageEntryAbbreviationMap.put(new PdfName("W"), PdfName.WIDTH);
/*     */ 
/* 119 */     inlineImageColorSpaceAbbreviationMap = new HashMap();
/*     */ 
/* 121 */     inlineImageColorSpaceAbbreviationMap.put(new PdfName("G"), PdfName.DEVICEGRAY);
/* 122 */     inlineImageColorSpaceAbbreviationMap.put(new PdfName("RGB"), PdfName.DEVICERGB);
/* 123 */     inlineImageColorSpaceAbbreviationMap.put(new PdfName("CMYK"), PdfName.DEVICECMYK);
/* 124 */     inlineImageColorSpaceAbbreviationMap.put(new PdfName("I"), PdfName.INDEXED);
/*     */ 
/* 132 */     inlineImageFilterAbbreviationMap = new HashMap();
/*     */ 
/* 134 */     inlineImageFilterAbbreviationMap.put(new PdfName("AHx"), PdfName.ASCIIHEXDECODE);
/* 135 */     inlineImageFilterAbbreviationMap.put(new PdfName("A85"), PdfName.ASCII85DECODE);
/* 136 */     inlineImageFilterAbbreviationMap.put(new PdfName("LZW"), PdfName.LZWDECODE);
/* 137 */     inlineImageFilterAbbreviationMap.put(new PdfName("Fl"), PdfName.FLATEDECODE);
/* 138 */     inlineImageFilterAbbreviationMap.put(new PdfName("RL"), PdfName.RUNLENGTHDECODE);
/* 139 */     inlineImageFilterAbbreviationMap.put(new PdfName("CCF"), PdfName.CCITTFAXDECODE);
/* 140 */     inlineImageFilterAbbreviationMap.put(new PdfName("DCT"), PdfName.DCTDECODE);
/*     */   }
/*     */ 
/*     */   public static class InlineImageParseException extends IOException
/*     */   {
/*     */     private static final long serialVersionUID = 233760879000268548L;
/*     */ 
/*     */     public InlineImageParseException(String message)
/*     */     {
/*  77 */       super();
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.pdf.parser.InlineImageUtils
 * JD-Core Version:    0.6.2
 */