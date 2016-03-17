/*    */ package org.apache.pdfbox.pdmodel.graphics.shading;
/*    */ 
/*    */ import java.awt.Paint;
/*    */ import java.awt.PaintContext;
/*    */ import java.awt.Rectangle;
/*    */ import java.awt.RenderingHints;
/*    */ import java.awt.geom.AffineTransform;
/*    */ import java.awt.geom.Rectangle2D;
/*    */ import java.awt.image.ColorModel;
/*    */ import java.io.IOException;
/*    */ import org.apache.commons.logging.Log;
/*    */ import org.apache.commons.logging.LogFactory;
/*    */ import org.apache.pdfbox.util.Matrix;
/*    */ 
/*    */ public class AxialShadingPaint
/*    */   implements Paint
/*    */ {
/* 39 */   private static final Log LOG = LogFactory.getLog(AxialShadingPaint.class);
/*    */   private PDShadingType2 shading;
/*    */   private Matrix ctm;
/*    */   private int pageHeight;
/*    */ 
/*    */   public AxialShadingPaint(PDShadingType2 shadingType2, Matrix ctm, int pageHeight)
/*    */   {
/* 54 */     this.shading = shadingType2;
/* 55 */     this.ctm = ctm;
/* 56 */     this.pageHeight = pageHeight;
/*    */   }
/*    */ 
/*    */   public int getTransparency()
/*    */   {
/* 64 */     return 0;
/*    */   }
/*    */ 
/*    */   public PaintContext createContext(ColorModel cm, Rectangle deviceBounds, Rectangle2D userBounds, AffineTransform xform, RenderingHints hints)
/*    */   {
/*    */     try
/*    */     {
/* 75 */       return new AxialShadingContext(this.shading, cm, xform, this.ctm, this.pageHeight, deviceBounds);
/*    */     }
/*    */     catch (IOException ex)
/*    */     {
/* 79 */       LOG.error(ex);
/* 80 */     }return null;
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.pdmodel.graphics.shading.AxialShadingPaint
 * JD-Core Version:    0.6.2
 */