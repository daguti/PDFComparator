/*     */ package org.apache.pdfbox.util.operator.pagedrawer;
/*     */ 
/*     */ import java.awt.Dimension;
/*     */ import java.awt.geom.AffineTransform;
/*     */ import java.awt.image.BufferedImage;
/*     */ import java.io.IOException;
/*     */ import java.util.List;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.commons.logging.LogFactory;
/*     */ import org.apache.pdfbox.cos.COSBase;
/*     */ import org.apache.pdfbox.pdfviewer.PageDrawer;
/*     */ import org.apache.pdfbox.pdmodel.PDPage;
/*     */ import org.apache.pdfbox.pdmodel.graphics.PDGraphicsState;
/*     */ import org.apache.pdfbox.pdmodel.graphics.xobject.PDInlinedImage;
/*     */ import org.apache.pdfbox.util.ImageParameters;
/*     */ import org.apache.pdfbox.util.Matrix;
/*     */ import org.apache.pdfbox.util.PDFOperator;
/*     */ import org.apache.pdfbox.util.PDFStreamEngine;
/*     */ import org.apache.pdfbox.util.operator.OperatorProcessor;
/*     */ 
/*     */ public class BeginInlineImage extends OperatorProcessor
/*     */ {
/*  47 */   private static final Log LOG = LogFactory.getLog(BeginInlineImage.class);
/*     */ 
/*     */   public void process(PDFOperator operator, List<COSBase> arguments)
/*     */     throws IOException
/*     */   {
/*  57 */     PageDrawer drawer = (PageDrawer)this.context;
/*  58 */     PDPage page = drawer.getPage();
/*     */ 
/*  60 */     ImageParameters params = operator.getImageParameters();
/*  61 */     PDInlinedImage image = new PDInlinedImage();
/*  62 */     image.setImageParameters(params);
/*  63 */     image.setImageData(operator.getImageData());
/*  64 */     if (params.isStencil())
/*     */     {
/*  67 */       LOG.warn("Stencil masks are not implemented, background may be incorrect");
/*     */     }
/*  69 */     BufferedImage awtImage = image.createImage(this.context.getColorSpaces());
/*     */ 
/*  71 */     if (awtImage == null)
/*     */     {
/*  73 */       LOG.warn("BeginInlineImage.process(): createImage returned NULL");
/*  74 */       return;
/*     */     }
/*  76 */     int imageWidth = awtImage.getWidth();
/*  77 */     int imageHeight = awtImage.getHeight();
/*  78 */     double pageHeight = drawer.getPageSize().getHeight();
/*     */ 
/*  80 */     Matrix ctm = drawer.getGraphicsState().getCurrentTransformationMatrix();
/*  81 */     int pageRotation = page.findRotation();
/*     */ 
/*  83 */     AffineTransform ctmAT = ctm.createAffineTransform();
/*  84 */     ctmAT.scale(1.0F / imageWidth, 1.0F / imageHeight);
/*  85 */     Matrix rotationMatrix = new Matrix();
/*  86 */     rotationMatrix.setFromAffineTransform(ctmAT);
/*     */ 
/*  91 */     double angle = Math.atan(ctmAT.getShearX() / ctmAT.getScaleX());
/*  92 */     Matrix translationMatrix = null;
/*  93 */     if ((pageRotation == 0) || (pageRotation == 180))
/*     */     {
/*  95 */       translationMatrix = Matrix.getTranslatingInstance((float)(Math.sin(angle) * ctm.getXScale()), (float)(pageHeight - 2.0F * ctm.getYPosition() - Math.cos(angle) * ctm.getYScale()));
/*     */     }
/*  97 */     else if ((pageRotation == 90) || (pageRotation == 270))
/*     */     {
/*  99 */       translationMatrix = Matrix.getTranslatingInstance((float)(Math.sin(angle) * ctm.getYScale()), (float)(pageHeight - 2.0F * ctm.getYPosition()));
/*     */     }
/* 101 */     rotationMatrix = rotationMatrix.multiply(translationMatrix);
/* 102 */     rotationMatrix.setValue(0, 1, -1.0F * rotationMatrix.getValue(0, 1));
/* 103 */     rotationMatrix.setValue(1, 0, -1.0F * rotationMatrix.getValue(1, 0));
/* 104 */     AffineTransform at = new AffineTransform(rotationMatrix.getValue(0, 0), rotationMatrix.getValue(0, 1), rotationMatrix.getValue(1, 0), rotationMatrix.getValue(1, 1), rotationMatrix.getValue(2, 0), rotationMatrix.getValue(2, 1));
/*     */ 
/* 109 */     drawer.drawImage(awtImage, at);
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.util.operator.pagedrawer.BeginInlineImage
 * JD-Core Version:    0.6.2
 */