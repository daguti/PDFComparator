/*    */ package org.apache.pdfbox.pdmodel.fdf;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import org.apache.pdfbox.cos.COSDictionary;
/*    */ import org.apache.pdfbox.cos.COSName;
/*    */ import org.w3c.dom.Element;
/*    */ 
/*    */ public class FDFAnnotationLine extends FDFAnnotation
/*    */ {
/*    */   public static final String SUBTYPE = "Line";
/*    */ 
/*    */   public FDFAnnotationLine()
/*    */   {
/* 44 */     this.annot.setName(COSName.SUBTYPE, "Line");
/*    */   }
/*    */ 
/*    */   public FDFAnnotationLine(COSDictionary a)
/*    */   {
/* 54 */     super(a);
/*    */   }
/*    */ 
/*    */   public FDFAnnotationLine(Element element)
/*    */     throws IOException
/*    */   {
/* 66 */     super(element);
/* 67 */     this.annot.setName(COSName.SUBTYPE, "Line");
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.pdmodel.fdf.FDFAnnotationLine
 * JD-Core Version:    0.6.2
 */