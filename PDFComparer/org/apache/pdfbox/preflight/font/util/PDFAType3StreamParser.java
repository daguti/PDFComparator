/*     */ package org.apache.pdfbox.preflight.font.util;
/*     */ 
/*     */ import java.awt.Image;
/*     */ import java.io.IOException;
/*     */ import java.util.List;
/*     */ import org.apache.fontbox.util.BoundingBox;
/*     */ import org.apache.pdfbox.cos.COSFloat;
/*     */ import org.apache.pdfbox.cos.COSInteger;
/*     */ import org.apache.pdfbox.cos.COSNumber;
/*     */ import org.apache.pdfbox.cos.COSStream;
/*     */ import org.apache.pdfbox.pdmodel.PDPage;
/*     */ import org.apache.pdfbox.pdmodel.graphics.xobject.PDInlinedImage;
/*     */ import org.apache.pdfbox.preflight.PreflightContext;
/*     */ import org.apache.pdfbox.preflight.content.ContentStreamEngine;
/*     */ import org.apache.pdfbox.util.ImageParameters;
/*     */ import org.apache.pdfbox.util.PDFOperator;
/*     */ 
/*     */ public class PDFAType3StreamParser extends ContentStreamEngine
/*     */ {
/*  46 */   private boolean firstOperator = true;
/*  47 */   private float width = 0.0F;
/*     */ 
/*  49 */   private PDInlinedImage image = null;
/*  50 */   private BoundingBox box = null;
/*     */ 
/*     */   public PDFAType3StreamParser(PreflightContext context, PDPage page)
/*     */   {
/*  54 */     super(context, page);
/*     */   }
/*     */ 
/*     */   public Image createImage(COSStream type3Stream)
/*     */     throws IOException
/*     */   {
/*  70 */     resetEngine();
/*  71 */     processSubStream(null, null, type3Stream);
/*  72 */     return this.image.createImage();
/*     */   }
/*     */ 
/*     */   protected void processOperator(PDFOperator operator, List arguments)
/*     */     throws IOException
/*     */   {
/*  88 */     super.processOperator(operator, arguments);
/*  89 */     String operation = operator.getOperation();
/*     */ 
/*  91 */     if (operation.equals("BI"))
/*     */     {
/*  93 */       ImageParameters params = operator.getImageParameters();
/*  94 */       this.image = new PDInlinedImage();
/*  95 */       this.image.setImageParameters(params);
/*  96 */       this.image.setImageData(operator.getImageData());
/*     */ 
/*  98 */       validImageFilter(operator);
/*  99 */       validImageColorSpace(operator);
/*     */     }
/*     */ 
/* 102 */     if (operation.equals("d0"))
/*     */     {
/* 110 */       checkType3FirstOperator(arguments);
/*     */     }
/* 113 */     else if (operation.equals("d1"))
/*     */     {
/* 118 */       COSNumber llx = (COSNumber)arguments.get(2);
/* 119 */       COSNumber lly = (COSNumber)arguments.get(3);
/* 120 */       COSNumber urx = (COSNumber)arguments.get(4);
/* 121 */       COSNumber ury = (COSNumber)arguments.get(5);
/*     */ 
/* 125 */       this.box = new BoundingBox();
/* 126 */       this.box.setLowerLeftX(llx.floatValue());
/* 127 */       this.box.setLowerLeftY(lly.floatValue());
/* 128 */       this.box.setUpperRightX(urx.floatValue());
/* 129 */       this.box.setUpperRightY(ury.floatValue());
/*     */ 
/* 131 */       checkType3FirstOperator(arguments);
/*     */     }
/*     */ 
/* 134 */     checkColorOperators(operation);
/* 135 */     validRenderingIntent(operator, arguments);
/* 136 */     checkSetColorSpaceOperators(operator, arguments);
/* 137 */     validNumberOfGraphicStates(operator);
/* 138 */     this.firstOperator = false;
/*     */   }
/*     */ 
/*     */   private void checkType3FirstOperator(List arguments)
/*     */     throws IOException
/*     */   {
/* 150 */     if (!this.firstOperator)
/*     */     {
/* 152 */       throw new IOException("Type3 CharProc : First operator must be d0 or d1");
/*     */     }
/*     */ 
/* 155 */     Object obj = arguments.get(0);
/* 156 */     if ((obj instanceof Number))
/*     */     {
/* 158 */       this.width = ((Number)obj).intValue();
/*     */     }
/* 160 */     else if ((obj instanceof COSInteger))
/*     */     {
/* 162 */       this.width = ((COSInteger)obj).floatValue();
/*     */     }
/* 164 */     else if ((obj instanceof COSFloat))
/*     */     {
/* 166 */       this.width = ((COSFloat)obj).floatValue();
/*     */     }
/*     */     else
/*     */     {
/* 170 */       throw new IOException("Unexpected argument type. Expected : COSInteger or Number / Received : " + obj.getClass().getName());
/*     */     }
/*     */   }
/*     */ 
/*     */   public float getWidth()
/*     */   {
/* 180 */     return this.width;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.preflight.font.util.PDFAType3StreamParser
 * JD-Core Version:    0.6.2
 */