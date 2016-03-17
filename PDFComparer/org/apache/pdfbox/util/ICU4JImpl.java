/*     */ package org.apache.pdfbox.util;
/*     */ 
/*     */ import com.ibm.icu.text.Bidi;
/*     */ import com.ibm.icu.text.Normalizer;
/*     */ 
/*     */ /** @deprecated */
/*     */ public class ICU4JImpl
/*     */ {
/*     */   Bidi bidi;
/*     */ 
/*     */   public ICU4JImpl()
/*     */   {
/*  39 */     this.bidi = new Bidi();
/*     */ 
/*  49 */     this.bidi.setReorderingMode(5);
/*     */   }
/*     */ 
/*     */   public String makeLineLogicalOrder(String str, boolean isRtlDominant)
/*     */   {
/*  62 */     this.bidi.setPara(str, (byte)(isRtlDominant ? 1 : 0), null);
/*     */ 
/*  69 */     return this.bidi.writeReordered(2);
/*     */   }
/*     */ 
/*     */   public String normalizePres(String str)
/*     */   {
/*  81 */     StringBuilder builder = null;
/*  82 */     int p = 0;
/*  83 */     int q = 0;
/*  84 */     int strLength = str.length();
/*  85 */     for (; q < strLength; q++)
/*     */     {
/*  92 */       char c = str.charAt(q);
/*  93 */       if (((64256 <= c) && (c <= 65023)) || ((65136 <= c) && (c <= 65279)))
/*     */       {
/*  95 */         if (builder == null)
/*     */         {
/*  97 */           builder = new StringBuilder(strLength * 2);
/*     */         }
/*  99 */         builder.append(str.substring(p, q));
/*     */ 
/* 103 */         if ((c == 65010) && (q > 0) && ((str.charAt(q - 1) == 'ا') || (str.charAt(q - 1) == 65165)))
/*     */         {
/* 105 */           builder.append("لله");
/*     */         }
/*     */         else
/*     */         {
/* 111 */           builder.append(Normalizer.normalize(c, Normalizer.NFKC).trim());
/*     */         }
/*     */ 
/* 114 */         p = q + 1;
/*     */       }
/*     */     }
/* 117 */     if (builder == null)
/*     */     {
/* 119 */       return str;
/*     */     }
/*     */ 
/* 123 */     builder.append(str.substring(p, q));
/* 124 */     return builder.toString();
/*     */   }
/*     */ 
/*     */   public String normalizeDiac(String str)
/*     */   {
/* 136 */     StringBuilder retStr = new StringBuilder();
/* 137 */     int strLength = str.length();
/* 138 */     for (int i = 0; i < strLength; i++)
/*     */     {
/* 140 */       char c = str.charAt(i);
/* 141 */       int type = Character.getType(c);
/* 142 */       if ((type == 6) || (type == 27) || (type == 4))
/*     */       {
/* 150 */         retStr.append(Normalizer.normalize(c, Normalizer.NFKC).trim());
/*     */       }
/*     */       else
/*     */       {
/* 154 */         retStr.append(str.charAt(i));
/*     */       }
/*     */     }
/* 157 */     return retStr.toString();
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.util.ICU4JImpl
 * JD-Core Version:    0.6.2
 */