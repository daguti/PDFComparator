/*     */ package org.apache.pdfbox.pdmodel.graphics.color;
/*     */ 
/*     */ import java.awt.color.ColorSpace;
/*     */ import java.awt.image.ColorModel;
/*     */ import java.io.IOException;
/*     */ import org.apache.pdfbox.cos.COSArray;
/*     */ import org.apache.pdfbox.cos.COSBase;
/*     */ import org.apache.pdfbox.cos.COSName;
/*     */ import org.apache.pdfbox.pdmodel.common.COSObjectable;
/*     */ 
/*     */ public abstract class PDColorSpace
/*     */   implements COSObjectable
/*     */ {
/*     */   protected COSArray array;
/*  49 */   private ColorSpace colorSpace = null;
/*     */ 
/*     */   public abstract String getName();
/*     */ 
/*     */   public abstract int getNumberOfComponents()
/*     */     throws IOException;
/*     */ 
/*     */   public COSBase getCOSObject()
/*     */   {
/*  74 */     return COSName.getPDFName(getName());
/*     */   }
/*     */ 
/*     */   public ColorSpace getJavaColorSpace()
/*     */     throws IOException
/*     */   {
/*  84 */     if (this.colorSpace == null) {
/*  85 */       this.colorSpace = createColorSpace();
/*     */     }
/*  87 */     return this.colorSpace;
/*     */   }
/*     */ 
/*     */   protected abstract ColorSpace createColorSpace()
/*     */     throws IOException;
/*     */ 
/*     */   public abstract ColorModel createColorModel(int paramInt)
/*     */     throws IOException;
/*     */ 
/*     */   public String toString()
/*     */   {
/* 118 */     return getName() + "{ " + (this.array == null ? "" : this.array.toString()) + " }";
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.pdmodel.graphics.color.PDColorSpace
 * JD-Core Version:    0.6.2
 */