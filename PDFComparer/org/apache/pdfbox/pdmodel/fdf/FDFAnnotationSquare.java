/*    */ package org.apache.pdfbox.pdmodel.fdf;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import org.apache.pdfbox.cos.COSDictionary;
/*    */ import org.apache.pdfbox.cos.COSName;
/*    */ import org.w3c.dom.Element;
/*    */ 
/*    */ public class FDFAnnotationSquare extends FDFAnnotation
/*    */ {
/*    */   public static final String SUBTYPE = "Square";
/*    */ 
/*    */   public FDFAnnotationSquare()
/*    */   {
/* 44 */     this.annot.setName(COSName.SUBTYPE, "Square");
/*    */   }
/*    */ 
/*    */   public FDFAnnotationSquare(COSDictionary a)
/*    */   {
/* 54 */     super(a);
/*    */   }
/*    */ 
/*    */   public FDFAnnotationSquare(Element element)
/*    */     throws IOException
/*    */   {
/* 66 */     super(element);
/* 67 */     this.annot.setName(COSName.SUBTYPE, "Square");
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.pdmodel.fdf.FDFAnnotationSquare
 * JD-Core Version:    0.6.2
 */