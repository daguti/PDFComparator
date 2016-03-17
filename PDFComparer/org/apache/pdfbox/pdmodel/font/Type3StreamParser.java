/*     */ package org.apache.pdfbox.pdmodel.font;
/*     */ 
/*     */ import java.awt.Image;
/*     */ import java.io.IOException;
/*     */ import java.util.List;
/*     */ import org.apache.fontbox.util.BoundingBox;
/*     */ import org.apache.pdfbox.cos.COSNumber;
/*     */ import org.apache.pdfbox.cos.COSStream;
/*     */ import org.apache.pdfbox.pdmodel.graphics.xobject.PDInlinedImage;
/*     */ import org.apache.pdfbox.util.ImageParameters;
/*     */ import org.apache.pdfbox.util.PDFOperator;
/*     */ import org.apache.pdfbox.util.PDFStreamEngine;
/*     */ 
/*     */ public class Type3StreamParser extends PDFStreamEngine
/*     */ {
/*  44 */   private PDInlinedImage image = null;
/*  45 */   private BoundingBox box = null;
/*     */ 
/*     */   public Image createImage(COSStream type3Stream)
/*     */     throws IOException
/*     */   {
/*  59 */     processStream(null, null, type3Stream);
/*  60 */     return this.image.createImage();
/*     */   }
/*     */ 
/*     */   protected void processOperator(PDFOperator operator, List arguments)
/*     */     throws IOException
/*     */   {
/*  73 */     super.processOperator(operator, arguments);
/*  74 */     String operation = operator.getOperation();
/*     */ 
/*  96 */     if (operation.equals("BI"))
/*     */     {
/*  98 */       ImageParameters params = operator.getImageParameters();
/*  99 */       this.image = new PDInlinedImage();
/* 100 */       this.image.setImageParameters(params);
/* 101 */       this.image.setImageData(operator.getImageData());
/*     */     }
/*     */ 
/* 135 */     if (!operation.equals("d0"))
/*     */     {
/* 143 */       if (operation.equals("d1"))
/*     */       {
/* 148 */         COSNumber llx = (COSNumber)arguments.get(2);
/* 149 */         COSNumber lly = (COSNumber)arguments.get(3);
/* 150 */         COSNumber urx = (COSNumber)arguments.get(4);
/* 151 */         COSNumber ury = (COSNumber)arguments.get(5);
/*     */ 
/* 155 */         this.box = new BoundingBox();
/* 156 */         this.box.setLowerLeftX(llx.floatValue());
/* 157 */         this.box.setLowerLeftY(lly.floatValue());
/* 158 */         this.box.setUpperRightX(urx.floatValue());
/* 159 */         this.box.setUpperRightY(ury.floatValue());
/*     */       }
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.pdmodel.font.Type3StreamParser
 * JD-Core Version:    0.6.2
 */