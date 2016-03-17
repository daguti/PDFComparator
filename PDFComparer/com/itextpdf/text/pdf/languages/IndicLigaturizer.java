/*     */ package com.itextpdf.text.pdf.languages;
/*     */ 
/*     */ public abstract class IndicLigaturizer
/*     */   implements LanguageProcessor
/*     */ {
/*     */   public static final int MATRA_AA = 0;
/*     */   public static final int MATRA_I = 1;
/*     */   public static final int MATRA_E = 2;
/*     */   public static final int MATRA_AI = 3;
/*     */   public static final int MATRA_HLR = 4;
/*     */   public static final int MATRA_HLRR = 5;
/*     */   public static final int LETTER_A = 6;
/*     */   public static final int LETTER_AU = 7;
/*     */   public static final int LETTER_KA = 8;
/*     */   public static final int LETTER_HA = 9;
/*     */   public static final int HALANTA = 10;
/*     */   protected char[] langTable;
/*     */ 
/*     */   public String process(String s)
/*     */   {
/*  81 */     if ((s == null) || (s.length() == 0))
/*  82 */       return "";
/*  83 */     StringBuilder res = new StringBuilder();
/*     */ 
/*  85 */     for (int i = 0; i < s.length(); i++) {
/*  86 */       char letter = s.charAt(i);
/*     */ 
/*  88 */       if ((IsVyanjana(letter)) || (IsSwaraLetter(letter))) {
/*  89 */         res.append(letter);
/*  90 */       } else if (IsSwaraMatra(letter)) {
/*  91 */         int prevCharIndex = res.length() - 1;
/*     */ 
/*  93 */         if (prevCharIndex >= 0)
/*     */         {
/*  96 */           if (res.charAt(prevCharIndex) == this.langTable[10]) {
/*  97 */             res.deleteCharAt(prevCharIndex);
/*     */           }
/*  99 */           res.append(letter);
/* 100 */           int prevPrevCharIndex = res.length() - 2;
/*     */ 
/* 102 */           if ((letter == this.langTable[1]) && (prevPrevCharIndex >= 0))
/* 103 */             swap(res, prevPrevCharIndex, res.length() - 1);
/*     */         } else {
/* 105 */           res.append(letter);
/*     */         }
/*     */       } else {
/* 108 */         res.append(letter);
/*     */       }
/*     */     }
/*     */ 
/* 112 */     return res.toString();
/*     */   }
/*     */ 
/*     */   public boolean isRTL()
/*     */   {
/* 122 */     return false;
/*     */   }
/*     */ 
/*     */   protected boolean IsSwaraLetter(char ch)
/*     */   {
/* 133 */     return (ch >= this.langTable[6]) && (ch <= this.langTable[7]);
/*     */   }
/*     */ 
/*     */   protected boolean IsSwaraMatra(char ch)
/*     */   {
/* 144 */     return ((ch >= this.langTable[0]) && (ch <= this.langTable[3])) || (ch == this.langTable[4]) || (ch == this.langTable[5]);
/*     */   }
/*     */ 
/*     */   protected boolean IsVyanjana(char ch)
/*     */   {
/* 156 */     return (ch >= this.langTable[8]) && (ch <= this.langTable[9]);
/*     */   }
/*     */ 
/*     */   private static void swap(StringBuilder s, int i, int j)
/*     */   {
/* 170 */     char temp = s.charAt(i);
/* 171 */     s.setCharAt(i, s.charAt(j));
/* 172 */     s.setCharAt(j, temp);
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.pdf.languages.IndicLigaturizer
 * JD-Core Version:    0.6.2
 */