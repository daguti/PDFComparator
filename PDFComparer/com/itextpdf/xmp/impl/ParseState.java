/*     */ package com.itextpdf.xmp.impl;
/*     */ 
/*     */ import com.itextpdf.xmp.XMPException;
/*     */ 
/*     */ class ParseState
/*     */ {
/*     */   private String str;
/* 416 */   private int pos = 0;
/*     */ 
/*     */   public ParseState(String str)
/*     */   {
/* 424 */     this.str = str;
/*     */   }
/*     */ 
/*     */   public int length()
/*     */   {
/* 433 */     return this.str.length();
/*     */   }
/*     */ 
/*     */   public boolean hasNext()
/*     */   {
/* 442 */     return this.pos < this.str.length();
/*     */   }
/*     */ 
/*     */   public char ch(int index)
/*     */   {
/* 452 */     return index < this.str.length() ? this.str.charAt(index) : '\000';
/*     */   }
/*     */ 
/*     */   public char ch()
/*     */   {
/* 463 */     return this.pos < this.str.length() ? this.str.charAt(this.pos) : '\000';
/*     */   }
/*     */ 
/*     */   public void skip()
/*     */   {
/* 474 */     this.pos += 1;
/*     */   }
/*     */ 
/*     */   public int pos()
/*     */   {
/* 483 */     return this.pos;
/*     */   }
/*     */ 
/*     */   public int gatherInt(String errorMsg, int maxValue)
/*     */     throws XMPException
/*     */   {
/* 496 */     int value = 0;
/* 497 */     boolean success = false;
/* 498 */     char ch = ch(this.pos);
/* 499 */     while (('0' <= ch) && (ch <= '9'))
/*     */     {
/* 501 */       value = value * 10 + (ch - '0');
/* 502 */       success = true;
/* 503 */       this.pos += 1;
/* 504 */       ch = ch(this.pos);
/*     */     }
/*     */ 
/* 507 */     if (success)
/*     */     {
/* 509 */       if (value > maxValue)
/*     */       {
/* 511 */         return maxValue;
/*     */       }
/* 513 */       if (value < 0)
/*     */       {
/* 515 */         return 0;
/*     */       }
/*     */ 
/* 519 */       return value;
/*     */     }
/*     */ 
/* 524 */     throw new XMPException(errorMsg, 5);
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.xmp.impl.ParseState
 * JD-Core Version:    0.6.2
 */