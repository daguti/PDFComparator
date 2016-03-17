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
/*    */ public class RadialShadingPaint
/*    */   implements Paint
/*    */ {
/* 39 */   private static final Log LOG = LogFactory.getLog(RadialShadingPaint.class);
/*    */   private PDShadingType3 shading;
/*    */   private Matrix ctm;
/*    */   private int pageHeight;
/*    */ 
/*    */   public RadialShadingPaint(PDShadingType3 shading, Matrix ctm, int pageHeight)
/*    */   {
/* 54 */     this.shading = shading;
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
/* 75 */       return new RadialShadingContext(this.shading, cm, xform, this.ctm, this.pageHeight, deviceBounds);
/*    */     }
/*    */     catch (IOException ex)
/*    */     {
/* 79 */       LOG.error(ex);
/* 80 */     }return null;
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.pdmodel.graphics.shading.RadialShadingPaint
 * JD-Core Version:    0.6.2
 */