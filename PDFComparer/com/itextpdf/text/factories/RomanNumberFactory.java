/*     */ package com.itextpdf.text.factories;
/*     */ 
/*     */ public class RomanNumberFactory
/*     */ {
/*  80 */   private static final RomanDigit[] roman = { new RomanDigit('m', 1000, false), new RomanDigit('d', 500, false), new RomanDigit('c', 100, true), new RomanDigit('l', 50, false), new RomanDigit('x', 10, true), new RomanDigit('v', 5, false), new RomanDigit('i', 1, true) };
/*     */ 
/*     */   public static final String getString(int index)
/*     */   {
/*  96 */     StringBuffer buf = new StringBuffer();
/*     */ 
/*  99 */     if (index < 0) {
/* 100 */       buf.append('-');
/* 101 */       index = -index;
/*     */     }
/*     */ 
/* 105 */     if (index > 3000) {
/* 106 */       buf.append('|');
/* 107 */       buf.append(getString(index / 1000));
/* 108 */       buf.append('|');
/*     */ 
/* 110 */       index -= index / 1000 * 1000;
/*     */     }
/*     */ 
/* 114 */     int pos = 0;
/*     */     while (true)
/*     */     {
/* 117 */       RomanDigit dig = roman[pos];
/*     */ 
/* 119 */       while (index >= dig.value) {
/* 120 */         buf.append(dig.digit);
/* 121 */         index -= dig.value;
/*     */       }
/*     */ 
/* 124 */       if (index <= 0)
/*     */       {
/*     */         break;
/*     */       }
/* 128 */       int j = pos;
/* 129 */       while (!roman[(++j)].pre);
/* 132 */       if (index + roman[j].value >= dig.value) {
/* 133 */         buf.append(roman[j].digit).append(dig.digit);
/* 134 */         index -= dig.value - roman[j].value;
/*     */       }
/* 136 */       pos++;
/*     */     }
/* 138 */     return buf.toString();
/*     */   }
/*     */ 
/*     */   public static final String getLowerCaseString(int index)
/*     */   {
/* 147 */     return getString(index);
/*     */   }
/*     */ 
/*     */   public static final String getUpperCaseString(int index)
/*     */   {
/* 156 */     return getString(index).toUpperCase();
/*     */   }
/*     */ 
/*     */   public static final String getString(int index, boolean lowercase)
/*     */   {
/* 166 */     if (lowercase) {
/* 167 */       return getLowerCaseString(index);
/*     */     }
/*     */ 
/* 170 */     return getUpperCaseString(index);
/*     */   }
/*     */ 
/*     */   private static class RomanDigit
/*     */   {
/*     */     public char digit;
/*     */     public int value;
/*     */     public boolean pre;
/*     */ 
/*     */     RomanDigit(char digit, int value, boolean pre)
/*     */     {
/*  71 */       this.digit = digit;
/*  72 */       this.value = value;
/*  73 */       this.pre = pre;
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.factories.RomanNumberFactory
 * JD-Core Version:    0.6.2
 */