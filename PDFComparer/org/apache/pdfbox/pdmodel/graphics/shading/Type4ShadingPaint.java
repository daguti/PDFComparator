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
/*    */ public class Type4ShadingPaint
/*    */   implements Paint
/*    */ {
/* 37 */   private static final Log LOG = LogFactory.getLog(Type4ShadingPaint.class);
/*    */   private PDShadingType4 shading;
/*    */   private Matrix ctm;
/*    */   private int pageHeight;
/*    */ 
/*    */   public Type4ShadingPaint(PDShadingType4 shading, Matrix ctm, int pageHeight)
/*    */   {
/* 52 */     this.shading = shading;
/* 53 */     this.ctm = ctm;
/* 54 */     this.pageHeight = pageHeight;
/*    */   }
/*    */ 
/*    */   public int getTransparency()
/*    */   {
/* 59 */     return 0;
/*    */   }
/*    */ 
/*    */   public PaintContext createContext(ColorModel cm, Rectangle deviceBounds, Rectangle2D userBounds, AffineTransform xform, RenderingHints hints)
/*    */   {
/*    */     try
/*    */     {
/* 67 */       return new Type4ShadingContext(this.shading, cm, xform, this.ctm, this.pageHeight, deviceBounds);
/*    */     }
/*    */     catch (IOException ex)
/*    */     {
/* 71 */       LOG.error(ex);
/* 72 */     }return null;
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.pdmodel.graphics.shading.Type4ShadingPaint
 * JD-Core Version:    0.6.2
 */