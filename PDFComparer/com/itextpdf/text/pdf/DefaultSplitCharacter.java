/*     */ package com.itextpdf.text.pdf;
/*     */ 
/*     */ import com.itextpdf.text.SplitCharacter;
/*     */ 
/*     */ public class DefaultSplitCharacter
/*     */   implements SplitCharacter
/*     */ {
/*  65 */   public static final SplitCharacter DEFAULT = new DefaultSplitCharacter();
/*     */   protected char[] characters;
/*     */ 
/*     */   public DefaultSplitCharacter()
/*     */   {
/*     */   }
/*     */ 
/*     */   public DefaultSplitCharacter(char character)
/*     */   {
/*  82 */     this(new char[] { character });
/*     */   }
/*     */ 
/*     */   public DefaultSplitCharacter(char[] characters)
/*     */   {
/*  91 */     this.characters = characters;
/*     */   }
/*     */ 
/*     */   public boolean isSplitCharacter(int start, int current, int end, char[] cc, PdfChunk[] ck)
/*     */   {
/* 116 */     char c = getCurrentCharacter(current, cc, ck);
/*     */ 
/* 118 */     if (this.characters != null) {
/* 119 */       for (int i = 0; i < this.characters.length; i++) {
/* 120 */         if (c == this.characters[i]) {
/* 121 */           return true;
/*     */         }
/*     */       }
/* 124 */       return false;
/*     */     }
/*     */ 
/* 127 */     if ((c <= ' ') || (c == '-') || (c == '‐')) {
/* 128 */       return true;
/*     */     }
/* 130 */     if (c < ' ')
/* 131 */       return false;
/* 132 */     return ((c >= ' ') && (c <= '​')) || ((c >= '⺀') && (c < 55200)) || ((c >= 63744) && (c < 64256)) || ((c >= 65072) && (c < 65104)) || ((c >= 65377) && (c < 65440));
/*     */   }
/*     */ 
/*     */   protected char getCurrentCharacter(int current, char[] cc, PdfChunk[] ck)
/*     */   {
/* 148 */     if (ck == null) {
/* 149 */       return cc[current];
/*     */     }
/* 151 */     return (char)ck[java.lang.Math.min(current, ck.length - 1)].getUnicodeEquivalent(cc[current]);
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.pdf.DefaultSplitCharacter
 * JD-Core Version:    0.6.2
 */