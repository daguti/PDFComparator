/*     */ package com.itextpdf.text;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ 
/*     */ public class Chapter extends Section
/*     */ {
/*     */   private static final long serialVersionUID = 1791000695779357361L;
/*     */ 
/*     */   public Chapter(int number)
/*     */   {
/*  83 */     super(null, 1);
/*  84 */     this.numbers = new ArrayList();
/*  85 */     this.numbers.add(Integer.valueOf(number));
/*  86 */     this.triggerNewPage = true;
/*     */   }
/*     */ 
/*     */   public Chapter(Paragraph title, int number)
/*     */   {
/*  97 */     super(title, 1);
/*  98 */     this.numbers = new ArrayList();
/*  99 */     this.numbers.add(Integer.valueOf(number));
/* 100 */     this.triggerNewPage = true;
/*     */   }
/*     */ 
/*     */   public Chapter(String title, int number)
/*     */   {
/* 110 */     this(new Paragraph(title), number);
/*     */   }
/*     */ 
/*     */   public int type()
/*     */   {
/* 122 */     return 16;
/*     */   }
/*     */ 
/*     */   public boolean isNestable()
/*     */   {
/* 131 */     return false;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.Chapter
 * JD-Core Version:    0.6.2
 */