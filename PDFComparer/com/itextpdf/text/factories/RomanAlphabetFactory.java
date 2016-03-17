/*     */ package com.itextpdf.text.factories;
/*     */ 
/*     */ import com.itextpdf.text.error_messages.MessageLocalization;
/*     */ 
/*     */ public class RomanAlphabetFactory
/*     */ {
/*     */   public static final String getString(int index)
/*     */   {
/*  64 */     if (index < 1) throw new NumberFormatException(MessageLocalization.getComposedMessage("you.can.t.translate.a.negative.number.into.an.alphabetical.value", new Object[0]));
/*     */ 
/*  66 */     index--;
/*  67 */     int bytes = 1;
/*  68 */     int start = 0;
/*  69 */     int symbols = 26;
/*  70 */     while (index >= symbols + start) {
/*  71 */       bytes++;
/*  72 */       start += symbols;
/*  73 */       symbols *= 26;
/*     */     }
/*     */ 
/*  76 */     int c = index - start;
/*  77 */     char[] value = new char[bytes];
/*  78 */     while (bytes > 0) {
/*  79 */       value[(--bytes)] = ((char)(97 + c % 26));
/*  80 */       c /= 26;
/*     */     }
/*     */ 
/*  83 */     return new String(value);
/*     */   }
/*     */ 
/*     */   public static final String getLowerCaseString(int index)
/*     */   {
/*  94 */     return getString(index);
/*     */   }
/*     */ 
/*     */   public static final String getUpperCaseString(int index)
/*     */   {
/* 105 */     return getString(index).toUpperCase();
/*     */   }
/*     */ 
/*     */   public static final String getString(int index, boolean lowercase)
/*     */   {
/* 118 */     if (lowercase) {
/* 119 */       return getLowerCaseString(index);
/*     */     }
/*     */ 
/* 122 */     return getUpperCaseString(index);
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.factories.RomanAlphabetFactory
 * JD-Core Version:    0.6.2
 */