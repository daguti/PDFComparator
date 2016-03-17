/*     */ package com.itextpdf.text.factories;
/*     */ 
/*     */ import com.itextpdf.text.SpecialSymbol;
/*     */ 
/*     */ public class GreekAlphabetFactory
/*     */ {
/*     */   public static final String getString(int index)
/*     */   {
/*  66 */     return getString(index, true);
/*     */   }
/*     */ 
/*     */   public static final String getLowerCaseString(int index)
/*     */   {
/*  75 */     return getString(index);
/*     */   }
/*     */ 
/*     */   public static final String getUpperCaseString(int index)
/*     */   {
/*  84 */     return getString(index).toUpperCase();
/*     */   }
/*     */ 
/*     */   public static final String getString(int index, boolean lowercase)
/*     */   {
/*  94 */     if (index < 1) return "";
/*  95 */     index--;
/*     */ 
/*  97 */     int bytes = 1;
/*  98 */     int start = 0;
/*  99 */     int symbols = 24;
/* 100 */     while (index >= symbols + start) {
/* 101 */       bytes++;
/* 102 */       start += symbols;
/* 103 */       symbols *= 24;
/*     */     }
/*     */ 
/* 106 */     int c = index - start;
/* 107 */     char[] value = new char[bytes];
/* 108 */     while (bytes > 0) {
/* 109 */       bytes--;
/* 110 */       value[bytes] = ((char)(c % 24));
/* 111 */       if (value[bytes] > '\020')
/*     */       {
/*     */         int tmp84_83 = bytes;
/*     */         char[] tmp84_81 = value; tmp84_81[tmp84_83] = ((char)(tmp84_81[tmp84_83] + '\001'));
/*     */       }
/*     */       int tmp93_92 = bytes;
/*     */       char[] tmp93_90 = value; tmp93_90[tmp93_92] = ((char)(tmp93_90[tmp93_92] + (lowercase ? 945 : 'Α')));
/* 113 */       value[bytes] = SpecialSymbol.getCorrespondingSymbol(value[bytes]);
/* 114 */       c /= 24;
/*     */     }
/*     */ 
/* 117 */     return String.valueOf(value);
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.factories.GreekAlphabetFactory
 * JD-Core Version:    0.6.2
 */