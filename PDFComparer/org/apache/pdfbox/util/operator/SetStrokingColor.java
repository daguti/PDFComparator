/*    */ package org.apache.pdfbox.util.operator;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.util.List;
/*    */ import org.apache.commons.logging.Log;
/*    */ import org.apache.commons.logging.LogFactory;
/*    */ import org.apache.pdfbox.cos.COSBase;
/*    */ import org.apache.pdfbox.pdmodel.graphics.PDGraphicsState;
/*    */ import org.apache.pdfbox.pdmodel.graphics.color.PDCalRGB;
/*    */ import org.apache.pdfbox.pdmodel.graphics.color.PDColorSpace;
/*    */ import org.apache.pdfbox.pdmodel.graphics.color.PDColorState;
/*    */ import org.apache.pdfbox.pdmodel.graphics.color.PDDeviceCMYK;
/*    */ import org.apache.pdfbox.pdmodel.graphics.color.PDDeviceGray;
/*    */ import org.apache.pdfbox.pdmodel.graphics.color.PDDeviceN;
/*    */ import org.apache.pdfbox.pdmodel.graphics.color.PDDeviceRGB;
/*    */ import org.apache.pdfbox.pdmodel.graphics.color.PDICCBased;
/*    */ import org.apache.pdfbox.pdmodel.graphics.color.PDIndexed;
/*    */ import org.apache.pdfbox.pdmodel.graphics.color.PDLab;
/*    */ import org.apache.pdfbox.pdmodel.graphics.color.PDPattern;
/*    */ import org.apache.pdfbox.pdmodel.graphics.color.PDSeparation;
/*    */ import org.apache.pdfbox.util.PDFOperator;
/*    */ import org.apache.pdfbox.util.PDFStreamEngine;
/*    */ 
/*    */ public class SetStrokingColor extends OperatorProcessor
/*    */ {
/* 49 */   private static final Log LOG = LogFactory.getLog(SetStrokingColor.class);
/*    */ 
/*    */   public void process(PDFOperator operator, List<COSBase> arguments)
/*    */     throws IOException
/*    */   {
/* 59 */     PDColorSpace colorSpace = this.context.getGraphicsState().getStrokingColor().getColorSpace();
/* 60 */     if (colorSpace != null)
/*    */     {
/* 62 */       OperatorProcessor newOperator = null;
/* 63 */       if ((colorSpace instanceof PDDeviceGray))
/*    */       {
/* 65 */         newOperator = new SetStrokingGrayColor();
/*    */       }
/* 67 */       else if ((colorSpace instanceof PDDeviceRGB))
/*    */       {
/* 69 */         newOperator = new SetStrokingRGBColor();
/*    */       }
/* 71 */       else if ((colorSpace instanceof PDDeviceCMYK))
/*    */       {
/* 73 */         newOperator = new SetStrokingCMYKColor();
/*    */       }
/* 75 */       else if ((colorSpace instanceof PDICCBased))
/*    */       {
/* 77 */         newOperator = new SetStrokingICCBasedColor();
/*    */       }
/* 79 */       else if ((colorSpace instanceof PDCalRGB))
/*    */       {
/* 81 */         newOperator = new SetStrokingCalRGBColor();
/*    */       }
/* 83 */       else if ((colorSpace instanceof PDSeparation))
/*    */       {
/* 85 */         newOperator = new SetStrokingSeparation();
/*    */       }
/* 87 */       else if ((colorSpace instanceof PDDeviceN))
/*    */       {
/* 89 */         newOperator = new SetStrokingDeviceN();
/*    */       }
/* 91 */       else if ((colorSpace instanceof PDPattern))
/*    */       {
/* 93 */         newOperator = new SetStrokingPattern();
/*    */       }
/* 95 */       else if ((colorSpace instanceof PDIndexed))
/*    */       {
/* 97 */         newOperator = new SetStrokingIndexed();
/*    */       }
/* 99 */       else if ((colorSpace instanceof PDLab))
/*    */       {
/* 101 */         newOperator = new SetStrokingLabColor();
/*    */       }
/*    */ 
/* 104 */       if (newOperator != null)
/*    */       {
/* 106 */         newOperator.setContext(getContext());
/* 107 */         newOperator.process(operator, arguments);
/*    */       }
/*    */       else
/*    */       {
/* 111 */         LOG.info("Not supported colorspace " + colorSpace.getName() + " within operator " + operator.getOperation());
/*    */       }
/*    */ 
/*    */     }
/*    */     else
/*    */     {
/* 117 */       LOG.warn("Colorspace not found in " + getClass().getName() + ".process!!");
/*    */     }
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.util.operator.SetStrokingColor
 * JD-Core Version:    0.6.2
 */