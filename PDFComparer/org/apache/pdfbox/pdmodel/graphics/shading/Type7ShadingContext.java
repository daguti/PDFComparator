/*    */ package org.apache.pdfbox.pdmodel.graphics.shading;
/*    */ 
/*    */ import java.awt.Rectangle;
/*    */ import java.awt.geom.AffineTransform;
/*    */ import java.awt.geom.Point2D;
/*    */ import java.awt.image.ColorModel;
/*    */ import java.io.IOException;
/*    */ import java.util.ArrayList;
/*    */ import org.apache.pdfbox.cos.COSDictionary;
/*    */ import org.apache.pdfbox.pdmodel.common.PDRange;
/*    */ import org.apache.pdfbox.util.Matrix;
/*    */ 
/*    */ class Type7ShadingContext extends PatchMeshesShadingContext
/*    */ {
/*    */   public Type7ShadingContext(PDShadingType7 shading, ColorModel colorModel, AffineTransform xform, Matrix ctm, int pageHeight, Rectangle dBounds)
/*    */     throws IOException
/*    */   {
/* 52 */     super(shading, colorModel, xform, ctm, pageHeight, dBounds);
/*    */ 
/* 55 */     xform.scale(1.0D, -1.0D);
/* 56 */     xform.translate(0.0D, -pageHeight);
/*    */ 
/* 58 */     this.patchList = getTensorPatchList(xform, ctm);
/* 59 */     this.pixelTable = calcPixelTable();
/*    */   }
/*    */ 
/*    */   private ArrayList<Patch> getTensorPatchList(AffineTransform xform, Matrix ctm)
/*    */     throws IOException
/*    */   {
/* 65 */     PDShadingType7 tensorShadingType = (PDShadingType7)this.patchMeshesShadingType;
/* 66 */     COSDictionary cosDictionary = tensorShadingType.getCOSDictionary();
/* 67 */     PDRange rangeX = tensorShadingType.getDecodeForParameter(0);
/* 68 */     PDRange rangeY = tensorShadingType.getDecodeForParameter(1);
/* 69 */     PDRange[] colRange = new PDRange[this.numberOfColorComponents];
/* 70 */     for (int i = 0; i < this.numberOfColorComponents; i++)
/*    */     {
/* 72 */       colRange[i] = tensorShadingType.getDecodeForParameter(2 + i);
/*    */     }
/* 74 */     return getPatchList(xform, ctm, cosDictionary, rangeX, rangeY, colRange, 16);
/*    */   }
/*    */ 
/*    */   protected Patch generatePatch(Point2D[] points, float[][] color)
/*    */   {
/* 80 */     return new TensorPatch(points, color);
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.pdmodel.graphics.shading.Type7ShadingContext
 * JD-Core Version:    0.6.2
 */