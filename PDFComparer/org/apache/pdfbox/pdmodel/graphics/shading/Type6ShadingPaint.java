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
/*    */ public class Type6ShadingPaint
/*    */   implements Paint
/*    */ {
/* 38 */   private static final Log LOG = LogFactory.getLog(Type6ShadingPaint.class);
/*    */   private final PDShadingType6 shading;
/*    */   private final Matrix ctm;
/*    */   private final int pageHeight;
/*    */ 
/*    */   public Type6ShadingPaint(PDShadingType6 shading, Matrix ctm, int pageHeight)
/*    */   {
/* 53 */     this.shading = shading;
/* 54 */     this.ctm = ctm;
/* 55 */     this.pageHeight = pageHeight;
/*    */   }
/*    */ 
/*    */   public int getTransparency()
/*    */   {
/* 63 */     return 0;
/*    */   }
/*    */ 
/*    */   public PaintContext createContext(ColorModel cm, Rectangle deviceBounds, Rectangle2D userBounds, AffineTransform xform, RenderingHints hints)
/*    */   {
/*    */     try
/*    */     {
/* 74 */       return new Type6ShadingContext(this.shading, cm, xform, this.ctm, this.pageHeight, deviceBounds);
/*    */     }
/*    */     catch (IOException ex)
/*    */     {
/* 78 */       LOG.error(ex);
/* 79 */     }return null;
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.pdmodel.graphics.shading.Type6ShadingPaint
 * JD-Core Version:    0.6.2
 */