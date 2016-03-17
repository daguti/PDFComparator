/*     */ package com.itextpdf.text.pdf.fonts.cmaps;
/*     */ 
/*     */ import com.itextpdf.text.ExceptionConverter;
/*     */ import com.itextpdf.text.error_messages.MessageLocalization;
/*     */ import com.itextpdf.text.pdf.PdfObject;
/*     */ import com.itextpdf.text.pdf.PdfString;
/*     */ import java.io.IOException;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import java.util.Map.Entry;
/*     */ 
/*     */ public class CMapToUnicode extends AbstractCMap
/*     */ {
/*  50 */   private Map<Integer, String> singleByteMappings = new HashMap();
/*  51 */   private Map<Integer, String> doubleByteMappings = new HashMap();
/*     */ 
/*     */   public boolean hasOneByteMappings()
/*     */   {
/*  66 */     return !this.singleByteMappings.isEmpty();
/*     */   }
/*     */ 
/*     */   public boolean hasTwoByteMappings()
/*     */   {
/*  75 */     return !this.doubleByteMappings.isEmpty();
/*     */   }
/*     */ 
/*     */   public String lookup(byte[] code, int offset, int length)
/*     */   {
/*  89 */     String result = null;
/*  90 */     Integer key = null;
/*  91 */     if (length == 1)
/*     */     {
/*  93 */       key = Integer.valueOf(code[offset] & 0xFF);
/*  94 */       result = (String)this.singleByteMappings.get(key);
/*  95 */     } else if (length == 2) {
/*  96 */       int intKey = code[offset] & 0xFF;
/*  97 */       intKey <<= 8;
/*  98 */       intKey += (code[(offset + 1)] & 0xFF);
/*  99 */       key = Integer.valueOf(intKey);
/*     */ 
/* 101 */       result = (String)this.doubleByteMappings.get(key);
/*     */     }
/*     */ 
/* 104 */     return result;
/*     */   }
/*     */ 
/*     */   public Map<Integer, Integer> createReverseMapping() throws IOException {
/* 108 */     Map result = new HashMap();
/* 109 */     for (Map.Entry entry : this.singleByteMappings.entrySet()) {
/* 110 */       result.put(Integer.valueOf(convertToInt((String)entry.getValue())), entry.getKey());
/*     */     }
/* 112 */     for (Map.Entry entry : this.doubleByteMappings.entrySet()) {
/* 113 */       result.put(Integer.valueOf(convertToInt((String)entry.getValue())), entry.getKey());
/*     */     }
/* 115 */     return result;
/*     */   }
/*     */ 
/*     */   public Map<Integer, Integer> createDirectMapping() throws IOException {
/* 119 */     Map result = new HashMap();
/* 120 */     for (Map.Entry entry : this.singleByteMappings.entrySet()) {
/* 121 */       result.put(entry.getKey(), Integer.valueOf(convertToInt((String)entry.getValue())));
/*     */     }
/* 123 */     for (Map.Entry entry : this.doubleByteMappings.entrySet()) {
/* 124 */       result.put(entry.getKey(), Integer.valueOf(convertToInt((String)entry.getValue())));
/*     */     }
/* 126 */     return result;
/*     */   }
/*     */ 
/*     */   private int convertToInt(String s) throws IOException {
/* 130 */     byte[] b = s.getBytes("UTF-16BE");
/* 131 */     int value = 0;
/* 132 */     for (int i = 0; i < b.length - 1; i++) {
/* 133 */       value += (b[i] & 0xFF);
/* 134 */       value <<= 8;
/*     */     }
/* 136 */     value += (b[(b.length - 1)] & 0xFF);
/* 137 */     return value;
/*     */   }
/*     */ 
/*     */   void addChar(int cid, String uni) {
/* 141 */     this.doubleByteMappings.put(Integer.valueOf(cid), uni);
/*     */   }
/*     */ 
/*     */   void addChar(PdfString mark, PdfObject code)
/*     */   {
/*     */     try {
/* 147 */       byte[] src = mark.getBytes();
/* 148 */       String dest = createStringFromBytes(code.getBytes());
/* 149 */       if (src.length == 1) {
/* 150 */         this.singleByteMappings.put(Integer.valueOf(src[0] & 0xFF), dest);
/* 151 */       } else if (src.length == 2) {
/* 152 */         int intSrc = src[0] & 0xFF;
/* 153 */         intSrc <<= 8;
/* 154 */         intSrc |= src[1] & 0xFF;
/* 155 */         this.doubleByteMappings.put(Integer.valueOf(intSrc), dest);
/*     */       } else {
/* 157 */         throw new IOException(MessageLocalization.getComposedMessage("mapping.code.should.be.1.or.two.bytes.and.not.1", src.length));
/*     */       }
/*     */     }
/*     */     catch (Exception ex) {
/* 161 */       throw new ExceptionConverter(ex);
/*     */     }
/*     */   }
/*     */ 
/*     */   private String createStringFromBytes(byte[] bytes) throws IOException {
/* 166 */     String retval = null;
/* 167 */     if (bytes.length == 1)
/* 168 */       retval = new String(bytes);
/*     */     else {
/* 170 */       retval = new String(bytes, "UTF-16BE");
/*     */     }
/* 172 */     return retval;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.pdf.fonts.cmaps.CMapToUnicode
 * JD-Core Version:    0.6.2
 */