/*    */ package org.apache.pdfbox.pdmodel.fdf;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import org.apache.pdfbox.cos.COSDictionary;
/*    */ import org.apache.pdfbox.cos.COSName;
/*    */ import org.w3c.dom.Element;
/*    */ 
/*    */ public class FDFAnnotationCircle extends FDFAnnotation
/*    */ {
/*    */   public static final String SUBTYPE = "Circle";
/*    */ 
/*    */   public FDFAnnotationCircle()
/*    */   {
/* 44 */     this.annot.setName(COSName.SUBTYPE, "Circle");
/*    */   }
/*    */ 
/*    */   public FDFAnnotationCircle(COSDictionary a)
/*    */   {
/* 54 */     super(a);
/*    */   }
/*    */ 
/*    */   public FDFAnnotationCircle(Element element)
/*    */     throws IOException
/*    */   {
/* 66 */     super(element);
/* 67 */     this.annot.setName(COSName.SUBTYPE, "Circle");
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.pdmodel.fdf.FDFAnnotationCircle
 * JD-Core Version:    0.6.2
 */