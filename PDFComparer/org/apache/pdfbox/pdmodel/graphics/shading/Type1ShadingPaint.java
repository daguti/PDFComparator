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
/*    */ public class Type1ShadingPaint
/*    */   implements Paint
/*    */ {
/* 37 */   private static final Log LOG = LogFactory.getLog(Type1ShadingPaint.class);
/*    */   private PDShadingType1 shading;
/*    */   private Matrix ctm;
/*    */   private int pageHeight;
/*    */ 
/*    */   public Type1ShadingPaint(PDShadingType1 shading, Matrix ctm, int pageHeight)
/*    */   {
/* 52 */     this.shading = shading;
/* 53 */     this.ctm = ctm;
/* 54 */     this.pageHeight = pageHeight;
/*    */   }
/*    */ 
/*    */   public int getTransparency()
/*    */   {
/* 62 */     return 0;
/*    */   }
/*    */ 
/*    */   public PaintContext createContext(ColorModel cm, Rectangle deviceBounds, Rectangle2D userBounds, AffineTransform xform, RenderingHints hints)
/*    */   {
/*    */     try
/*    */     {
/* 73 */       return new Type1ShadingContext(this.shading, cm, xform, this.ctm, this.pageHeight, deviceBounds);
/*    */     }
/*    */     catch (IOException ex)
/*    */     {
/* 77 */       LOG.error(ex);
/* 78 */     }return null;
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.pdmodel.graphics.shading.Type1ShadingPaint
 * JD-Core Version:    0.6.2
 */