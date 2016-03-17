/*     */ package org.apache.pdfbox.pdmodel.interactive.annotation;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import org.apache.pdfbox.cos.COSDictionary;
/*     */ import org.apache.pdfbox.cos.COSName;
/*     */ 
/*     */ public class PDAnnotationPopup extends PDAnnotation
/*     */ {
/*     */   public static final String SUB_TYPE = "Popup";
/*     */ 
/*     */   public PDAnnotationPopup()
/*     */   {
/*  45 */     getDictionary().setItem(COSName.SUBTYPE, COSName.getPDFName("Popup"));
/*     */   }
/*     */ 
/*     */   public PDAnnotationPopup(COSDictionary field)
/*     */   {
/*  58 */     super(field);
/*     */   }
/*     */ 
/*     */   public void setOpen(boolean open)
/*     */   {
/*  69 */     getDictionary().setBoolean("Open", open);
/*     */   }
/*     */ 
/*     */   public boolean getOpen()
/*     */   {
/*  80 */     return getDictionary().getBoolean("Open", false);
/*     */   }
/*     */ 
/*     */   public void setParent(PDAnnotationMarkup annot)
/*     */   {
/*  91 */     getDictionary().setItem(COSName.PARENT, annot.getDictionary());
/*     */   }
/*     */ 
/*     */   public PDAnnotationMarkup getParent()
/*     */   {
/* 101 */     PDAnnotationMarkup am = null;
/*     */     try
/*     */     {
/* 104 */       am = (PDAnnotationMarkup)PDAnnotation.createAnnotation(getDictionary().getDictionaryObject("Parent", "P"));
/*     */     }
/*     */     catch (IOException ioe)
/*     */     {
/*     */     }
/*     */ 
/* 111 */     return am;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.pdmodel.interactive.annotation.PDAnnotationPopup
 * JD-Core Version:    0.6.2
 */