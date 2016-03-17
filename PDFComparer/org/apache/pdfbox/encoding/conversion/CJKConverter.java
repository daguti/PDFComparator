/*     */ package org.apache.pdfbox.encoding.conversion;
/*     */ 
/*     */ import java.io.UnsupportedEncodingException;
/*     */ import org.apache.fontbox.cmap.CMap;
/*     */ 
/*     */ public class CJKConverter
/*     */   implements EncodingConverter
/*     */ {
/*  33 */   private String encodingName = null;
/*     */ 
/*  35 */   private String charsetName = null;
/*     */ 
/*     */   public CJKConverter(String encoding)
/*     */   {
/*  45 */     this.encodingName = encoding;
/*  46 */     this.charsetName = CJKEncodings.getCharset(encoding);
/*     */   }
/*     */ 
/*     */   public String convertString(String s)
/*     */   {
/*  62 */     if (s.length() == 1)
/*     */     {
/*  64 */       return s;
/*     */     }
/*     */ 
/*  67 */     if (this.charsetName.equalsIgnoreCase("UTF-16BE"))
/*     */     {
/*  69 */       return s;
/*     */     }
/*     */ 
/*     */     try
/*     */     {
/*  74 */       return new String(s.getBytes("UTF-16BE"), this.charsetName);
/*     */     }
/*     */     catch (UnsupportedEncodingException uee) {
/*     */     }
/*  78 */     return s;
/*     */   }
/*     */ 
/*     */   public String convertBytes(byte[] c, int offset, int length, CMap cmap)
/*     */   {
/*  90 */     if (cmap != null)
/*     */     {
/*     */       try
/*     */       {
/*  94 */         if (cmap.isInCodeSpaceRanges(c, offset, length))
/*     */         {
/*  96 */           return new String(c, offset, length, this.charsetName);
/*     */         }
/*     */ 
/* 100 */         return null;
/*     */       }
/*     */       catch (UnsupportedEncodingException uee)
/*     */       {
/* 106 */         return new String(c, offset, length);
/*     */       }
/*     */     }
/*     */ 
/* 110 */     return null;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.encoding.conversion.CJKConverter
 * JD-Core Version:    0.6.2
 */