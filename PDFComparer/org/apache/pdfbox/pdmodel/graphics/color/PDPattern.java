/*     */ package org.apache.pdfbox.pdmodel.graphics.color;
/*     */ 
/*     */ import java.awt.color.ColorSpace;
/*     */ import java.awt.image.ColorModel;
/*     */ import java.io.IOException;
/*     */ import org.apache.pdfbox.cos.COSArray;
/*     */ import org.apache.pdfbox.cos.COSName;
/*     */ 
/*     */ public class PDPattern extends PDColorSpace
/*     */ {
/*     */   private COSArray array;
/*     */   public static final String NAME = "Pattern";
/*     */ 
/*     */   public PDPattern()
/*     */   {
/*  47 */     this.array = new COSArray();
/*  48 */     this.array.add(COSName.PATTERN);
/*     */   }
/*     */ 
/*     */   public PDPattern(COSArray pattern)
/*     */   {
/*  58 */     this.array = pattern;
/*     */   }
/*     */ 
/*     */   public String getName()
/*     */   {
/*  68 */     return "Pattern";
/*     */   }
/*     */ 
/*     */   public int getNumberOfComponents()
/*     */     throws IOException
/*     */   {
/*  80 */     return -1;
/*     */   }
/*     */ 
/*     */   protected ColorSpace createColorSpace()
/*     */     throws IOException
/*     */   {
/*  92 */     throw new IOException("Not implemented");
/*     */   }
/*     */ 
/*     */   public ColorModel createColorModel(int bpc)
/*     */     throws IOException
/*     */   {
/* 106 */     throw new IOException("Not implemented");
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.pdmodel.graphics.color.PDPattern
 * JD-Core Version:    0.6.2
 */