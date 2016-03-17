/*    */ package org.apache.pdfbox.util.operator.pagedrawer;
/*    */ 
/*    */ import java.awt.Dimension;
/*    */ import java.awt.geom.AffineTransform;
/*    */ import java.awt.geom.Area;
/*    */ import java.awt.geom.GeneralPath;
/*    */ import java.awt.geom.Point2D;
/*    */ import java.awt.image.BufferedImage;
/*    */ import java.io.IOException;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ import java.util.Stack;
/*    */ import org.apache.commons.logging.Log;
/*    */ import org.apache.commons.logging.LogFactory;
/*    */ import org.apache.pdfbox.cos.COSBase;
/*    */ import org.apache.pdfbox.cos.COSName;
/*    */ import org.apache.pdfbox.cos.COSStream;
/*    */ import org.apache.pdfbox.pdfviewer.PageDrawer;
/*    */ import org.apache.pdfbox.pdmodel.PDPage;
/*    */ import org.apache.pdfbox.pdmodel.PDResources;
/*    */ import org.apache.pdfbox.pdmodel.common.PDRectangle;
/*    */ import org.apache.pdfbox.pdmodel.graphics.PDGraphicsState;
/*    */ import org.apache.pdfbox.pdmodel.graphics.xobject.PDXObject;
/*    */ import org.apache.pdfbox.pdmodel.graphics.xobject.PDXObjectForm;
/*    */ import org.apache.pdfbox.pdmodel.graphics.xobject.PDXObjectImage;
/*    */ import org.apache.pdfbox.util.Matrix;
/*    */ import org.apache.pdfbox.util.PDFOperator;
/*    */ import org.apache.pdfbox.util.PDFStreamEngine;
/*    */ import org.apache.pdfbox.util.operator.OperatorProcessor;
/*    */ 
/*    */ public class Invoke extends OperatorProcessor
/*    */ {
/* 57 */   private static final Log LOG = LogFactory.getLog(Invoke.class);
/*    */ 
/*    */   public void process(PDFOperator operator, List<COSBase> arguments)
/*    */     throws IOException
/*    */   {
/* 67 */     PageDrawer drawer = (PageDrawer)this.context;
/* 68 */     PDPage page = drawer.getPage();
/* 69 */     COSName objectName = (COSName)arguments.get(0);
/* 70 */     Map xobjects = drawer.getResources().getXObjects();
/* 71 */     PDXObject xobject = (PDXObject)xobjects.get(objectName.getName());
/* 72 */     if (xobject == null)
/*    */     {
/* 74 */       LOG.warn("Can't find the XObject for '" + objectName.getName() + "'");
/*    */     }
/* 76 */     else if ((xobject instanceof PDXObjectImage))
/*    */     {
/* 78 */       PDXObjectImage image = (PDXObjectImage)xobject;
/*    */       try
/*    */       {
/* 81 */         if (image.getImageMask())
/*    */         {
/* 85 */           image.setStencilColor(drawer.getGraphicsState().getNonStrokingColor());
/*    */         }
/* 87 */         BufferedImage awtImage = image.getRGBImage();
/* 88 */         if (awtImage == null)
/*    */         {
/* 90 */           LOG.warn("getRGBImage returned NULL");
/* 91 */           return;
/*    */         }
/* 93 */         int imageWidth = awtImage.getWidth();
/* 94 */         int imageHeight = awtImage.getHeight();
/* 95 */         double pageHeight = drawer.getPageSize().getHeight();
/*    */ 
/* 97 */         LOG.debug("imageWidth: " + imageWidth + "\t\timageHeight: " + imageHeight);
/*    */ 
/* 99 */         Matrix ctm = drawer.getGraphicsState().getCurrentTransformationMatrix();
/* 100 */         float yScaling = ctm.getYScale();
/* 101 */         float angle = (float)Math.acos(ctm.getValue(0, 0) / ctm.getXScale());
/* 102 */         if ((ctm.getValue(0, 1) < 0.0F) && (ctm.getValue(1, 0) > 0.0F))
/*    */         {
/* 104 */           angle = -1.0F * angle;
/*    */         }
/* 106 */         ctm.setValue(2, 1, (float)(pageHeight - ctm.getYPosition() - Math.cos(angle) * yScaling));
/* 107 */         ctm.setValue(2, 0, (float)(ctm.getXPosition() - Math.sin(angle) * yScaling));
/*    */ 
/* 109 */         ctm.setValue(0, 1, -1.0F * ctm.getValue(0, 1));
/* 110 */         ctm.setValue(1, 0, -1.0F * ctm.getValue(1, 0));
/* 111 */         AffineTransform ctmAT = ctm.createAffineTransform();
/* 112 */         ctmAT.scale(1.0F / imageWidth, 1.0F / imageHeight);
/* 113 */         drawer.drawImage(awtImage, ctmAT);
/*    */       }
/*    */       catch (Exception e)
/*    */       {
/* 117 */         LOG.error(e, e);
/*    */       }
/*    */     }
/* 120 */     else if ((xobject instanceof PDXObjectForm))
/*    */     {
/* 123 */       this.context.getGraphicsStack().push((PDGraphicsState)this.context.getGraphicsState().clone());
/*    */ 
/* 125 */       PDXObjectForm form = (PDXObjectForm)xobject;
/* 126 */       COSStream formContentstream = form.getCOSStream();
/*    */ 
/* 128 */       PDResources pdResources = form.getResources();
/*    */ 
/* 130 */       Matrix matrix = form.getMatrix();
/* 131 */       if (matrix != null)
/*    */       {
/* 133 */         Matrix xobjectCTM = matrix.multiply(this.context.getGraphicsState().getCurrentTransformationMatrix());
/* 134 */         this.context.getGraphicsState().setCurrentTransformationMatrix(xobjectCTM);
/*    */       }
/* 136 */       if (form.getBBox() != null)
/*    */       {
/* 138 */         PDGraphicsState graphicsState = this.context.getGraphicsState();
/* 139 */         PDRectangle bBox = form.getBBox();
/*    */ 
/* 141 */         float x1 = bBox.getLowerLeftX();
/* 142 */         float y1 = bBox.getLowerLeftY();
/* 143 */         float x2 = bBox.getUpperRightX();
/* 144 */         float y2 = bBox.getUpperRightY();
/*    */ 
/* 146 */         Point2D p0 = drawer.transformedPoint(x1, y1);
/* 147 */         Point2D p1 = drawer.transformedPoint(x2, y1);
/* 148 */         Point2D p2 = drawer.transformedPoint(x2, y2);
/* 149 */         Point2D p3 = drawer.transformedPoint(x1, y2);
/*    */ 
/* 151 */         GeneralPath bboxPath = new GeneralPath();
/* 152 */         bboxPath.moveTo((float)p0.getX(), (float)p0.getY());
/* 153 */         bboxPath.lineTo((float)p1.getX(), (float)p1.getY());
/* 154 */         bboxPath.lineTo((float)p2.getX(), (float)p2.getY());
/* 155 */         bboxPath.lineTo((float)p3.getX(), (float)p3.getY());
/* 156 */         bboxPath.closePath();
/*    */ 
/* 158 */         Area resultClippingArea = new Area(graphicsState.getCurrentClippingPath());
/* 159 */         Area newArea = new Area(bboxPath);
/* 160 */         resultClippingArea.intersect(newArea);
/*    */ 
/* 162 */         graphicsState.setCurrentClippingPath(resultClippingArea);
/*    */       }
/* 164 */       getContext().processSubStream(page, pdResources, formContentstream);
/*    */ 
/* 167 */       this.context.setGraphicsState((PDGraphicsState)this.context.getGraphicsStack().pop());
/*    */     }
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.util.operator.pagedrawer.Invoke
 * JD-Core Version:    0.6.2
 */