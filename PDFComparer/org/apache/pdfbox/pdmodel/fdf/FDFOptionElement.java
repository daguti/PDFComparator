/*     */ package org.apache.pdfbox.pdmodel.fdf;
/*     */ 
/*     */ import org.apache.pdfbox.cos.COSArray;
/*     */ import org.apache.pdfbox.cos.COSBase;
/*     */ import org.apache.pdfbox.cos.COSString;
/*     */ import org.apache.pdfbox.pdmodel.common.COSObjectable;
/*     */ 
/*     */ public class FDFOptionElement
/*     */   implements COSObjectable
/*     */ {
/*     */   private COSArray option;
/*     */ 
/*     */   public FDFOptionElement()
/*     */   {
/*  41 */     this.option = new COSArray();
/*  42 */     this.option.add(new COSString(""));
/*  43 */     this.option.add(new COSString(""));
/*     */   }
/*     */ 
/*     */   public FDFOptionElement(COSArray o)
/*     */   {
/*  53 */     this.option = o;
/*     */   }
/*     */ 
/*     */   public COSBase getCOSObject()
/*     */   {
/*  63 */     return this.option;
/*     */   }
/*     */ 
/*     */   public COSArray getCOSArray()
/*     */   {
/*  73 */     return this.option;
/*     */   }
/*     */ 
/*     */   public String getOption()
/*     */   {
/*  83 */     return ((COSString)this.option.getObject(0)).getString();
/*     */   }
/*     */ 
/*     */   public void setOption(String opt)
/*     */   {
/*  93 */     this.option.set(0, new COSString(opt));
/*     */   }
/*     */ 
/*     */   public String getDefaultAppearanceString()
/*     */   {
/* 103 */     return ((COSString)this.option.getObject(1)).getString();
/*     */   }
/*     */ 
/*     */   public void setDefaultAppearanceString(String da)
/*     */   {
/* 113 */     this.option.set(1, new COSString(da));
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.pdmodel.fdf.FDFOptionElement
 * JD-Core Version:    0.6.2
 */