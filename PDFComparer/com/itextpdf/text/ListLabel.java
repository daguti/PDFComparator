/*     */ package com.itextpdf.text;
/*     */ 
/*     */ import com.itextpdf.text.pdf.PdfName;
/*     */ 
/*     */ public class ListLabel extends ListBody
/*     */ {
/*  51 */   protected PdfName role = PdfName.LBL;
/*  52 */   protected float indentation = 0.0F;
/*     */ 
/*     */   protected ListLabel(ListItem parentItem) {
/*  55 */     super(parentItem);
/*     */   }
/*     */ 
/*     */   public PdfName getRole() {
/*  59 */     return this.role;
/*     */   }
/*     */ 
/*     */   public void setRole(PdfName role) {
/*  63 */     this.role = role;
/*     */   }
/*     */ 
/*     */   public float getIndentation() {
/*  67 */     return this.indentation;
/*     */   }
/*     */ 
/*     */   public void setIndentation(float indentation) {
/*  71 */     this.indentation = indentation;
/*     */   }
/*     */ 
/*     */   @Deprecated
/*     */   public boolean getTagLabelContent()
/*     */   {
/*  94 */     return false;
/*     */   }
/*     */ 
/*     */   @Deprecated
/*     */   public void setTagLabelContent(boolean tagLabelContent) {
/*     */   }
/*     */ 
/*     */   public boolean isInline() {
/* 102 */     return true;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.ListLabel
 * JD-Core Version:    0.6.2
 */