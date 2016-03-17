/*     */ package com.itextpdf.text.pdf.collection;
/*     */ 
/*     */ import com.itextpdf.text.pdf.PdfDictionary;
/*     */ import com.itextpdf.text.pdf.PdfName;
/*     */ import com.itextpdf.text.pdf.PdfNumber;
/*     */ import com.itextpdf.text.pdf.PdfString;
/*     */ 
/*     */ public class PdfTargetDictionary extends PdfDictionary
/*     */ {
/*     */   public PdfTargetDictionary(PdfTargetDictionary nested)
/*     */   {
/*  61 */     put(PdfName.R, PdfName.P);
/*  62 */     if (nested != null)
/*  63 */       setAdditionalPath(nested);
/*     */   }
/*     */ 
/*     */   public PdfTargetDictionary(boolean child)
/*     */   {
/*  72 */     if (child) {
/*  73 */       put(PdfName.R, PdfName.C);
/*     */     }
/*     */     else
/*  76 */       put(PdfName.R, PdfName.P);
/*     */   }
/*     */ 
/*     */   public void setEmbeddedFileName(String target)
/*     */   {
/*  86 */     put(PdfName.N, new PdfString(target, null));
/*     */   }
/*     */ 
/*     */   public void setFileAttachmentPagename(String name)
/*     */   {
/*  96 */     put(PdfName.P, new PdfString(name, null));
/*     */   }
/*     */ 
/*     */   public void setFileAttachmentPage(int page)
/*     */   {
/* 106 */     put(PdfName.P, new PdfNumber(page));
/*     */   }
/*     */ 
/*     */   public void setFileAttachmentName(String name)
/*     */   {
/* 116 */     put(PdfName.A, new PdfString(name, "UnicodeBig"));
/*     */   }
/*     */ 
/*     */   public void setFileAttachmentIndex(int annotation)
/*     */   {
/* 126 */     put(PdfName.A, new PdfNumber(annotation));
/*     */   }
/*     */ 
/*     */   public void setAdditionalPath(PdfTargetDictionary nested)
/*     */   {
/* 135 */     put(PdfName.T, nested);
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.pdf.collection.PdfTargetDictionary
 * JD-Core Version:    0.6.2
 */