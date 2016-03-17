/*     */ package com.itextpdf.text.pdf;
/*     */ 
/*     */ public class PdfPHeaderCell extends PdfPCell
/*     */ {
/*     */   public static final int NONE = 0;
/*     */   public static final int ROW = 1;
/*     */   public static final int COLUMN = 2;
/*     */   public static final int BOTH = 3;
/*  71 */   protected int scope = 0;
/*     */ 
/*  85 */   protected String name = null;
/*     */ 
/*     */   public PdfPHeaderCell()
/*     */   {
/*  75 */     this.role = PdfName.TH;
/*     */   }
/*     */ 
/*     */   public PdfPHeaderCell(PdfPHeaderCell headerCell) {
/*  79 */     super(headerCell);
/*  80 */     this.role = headerCell.role;
/*  81 */     this.scope = headerCell.scope;
/*  82 */     this.name = headerCell.getName();
/*     */   }
/*     */ 
/*     */   public void setName(String name)
/*     */   {
/*  88 */     this.name = name;
/*     */   }
/*     */ 
/*     */   public String getName() {
/*  92 */     return this.name;
/*     */   }
/*     */ 
/*     */   public PdfName getRole() {
/*  96 */     return this.role;
/*     */   }
/*     */ 
/*     */   public void setRole(PdfName role) {
/* 100 */     this.role = role;
/*     */   }
/*     */ 
/*     */   public void setScope(int scope) {
/* 104 */     this.scope = scope;
/*     */   }
/*     */ 
/*     */   public int getScope() {
/* 108 */     return this.scope;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.pdf.PdfPHeaderCell
 * JD-Core Version:    0.6.2
 */