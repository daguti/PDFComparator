/*    */ package org.apache.pdfbox.util.operator;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ import org.apache.pdfbox.cos.COSBase;
/*    */ import org.apache.pdfbox.cos.COSName;
/*    */ import org.apache.pdfbox.cos.COSStream;
/*    */ import org.apache.pdfbox.pdmodel.PDResources;
/*    */ import org.apache.pdfbox.pdmodel.graphics.PDGraphicsState;
/*    */ import org.apache.pdfbox.pdmodel.graphics.xobject.PDXObject;
/*    */ import org.apache.pdfbox.pdmodel.graphics.xobject.PDXObjectForm;
/*    */ import org.apache.pdfbox.util.Matrix;
/*    */ import org.apache.pdfbox.util.PDFMarkedContentExtractor;
/*    */ import org.apache.pdfbox.util.PDFOperator;
/*    */ import org.apache.pdfbox.util.PDFStreamEngine;
/*    */ 
/*    */ public class Invoke extends OperatorProcessor
/*    */ {
/*    */   public void process(PDFOperator operator, List<COSBase> arguments)
/*    */     throws IOException
/*    */   {
/* 53 */     COSName name = (COSName)arguments.get(0);
/*    */ 
/* 55 */     Map xobjects = this.context.getXObjects();
/* 56 */     PDXObject xobject = (PDXObject)xobjects.get(name.getName());
/* 57 */     if ((this.context instanceof PDFMarkedContentExtractor))
/*    */     {
/* 59 */       ((PDFMarkedContentExtractor)this.context).xobject(xobject);
/*    */     }
/*    */ 
/* 62 */     if ((xobject instanceof PDXObjectForm))
/*    */     {
/* 64 */       PDXObjectForm form = (PDXObjectForm)xobject;
/* 65 */       COSStream formContentstream = form.getCOSStream();
/*    */ 
/* 67 */       Matrix matrix = form.getMatrix();
/* 68 */       if (matrix != null)
/*    */       {
/* 70 */         Matrix xobjectCTM = matrix.multiply(this.context.getGraphicsState().getCurrentTransformationMatrix());
/* 71 */         this.context.getGraphicsState().setCurrentTransformationMatrix(xobjectCTM);
/*    */       }
/*    */ 
/* 74 */       PDResources pdResources = form.getResources();
/* 75 */       this.context.processSubStream(this.context.getCurrentPage(), pdResources, formContentstream);
/*    */     }
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.util.operator.Invoke
 * JD-Core Version:    0.6.2
 */