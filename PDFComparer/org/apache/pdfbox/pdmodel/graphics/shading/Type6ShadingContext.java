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
/*    */ class Type6ShadingContext extends PatchMeshesShadingContext
/*    */ {
/*    */   public Type6ShadingContext(PDShadingType6 shading, ColorModel colorModel, AffineTransform xform, Matrix ctm, int pageHeight, Rectangle dBounds)
/*    */     throws IOException
/*    */   {
/* 53 */     super(shading, colorModel, xform, ctm, pageHeight, dBounds);
/*    */ 
/* 56 */     xform.scale(1.0D, -1.0D);
/* 57 */     xform.translate(0.0D, -pageHeight);
/*    */ 
/* 59 */     this.patchList = getCoonsPatchList(xform, ctm);
/* 60 */     this.pixelTable = calcPixelTable();
/*    */   }
/*    */ 
/*    */   private ArrayList<Patch> getCoonsPatchList(AffineTransform xform, Matrix ctm)
/*    */     throws IOException
/*    */   {
/* 66 */     PDShadingType6 coonsShadingType = (PDShadingType6)this.patchMeshesShadingType;
/* 67 */     COSDictionary cosDictionary = coonsShadingType.getCOSDictionary();
/* 68 */     PDRange rangeX = coonsShadingType.getDecodeForParameter(0);
/* 69 */     PDRange rangeY = coonsShadingType.getDecodeForParameter(1);
/*    */ 
/* 71 */     PDRange[] colRange = new PDRange[this.numberOfColorComponents];
/* 72 */     for (int i = 0; i < this.numberOfColorComponents; i++)
/*    */     {
/* 74 */       colRange[i] = coonsShadingType.getDecodeForParameter(2 + i);
/*    */     }
/* 76 */     return getPatchList(xform, ctm, cosDictionary, rangeX, rangeY, colRange, 12);
/*    */   }
/*    */ 
/*    */   protected Patch generatePatch(Point2D[] points, float[][] color)
/*    */   {
/* 82 */     return new CoonsPatch(points, color);
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.pdmodel.graphics.shading.Type6ShadingContext
 * JD-Core Version:    0.6.2
 */