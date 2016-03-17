/*    */ package org.apache.pdfbox.pdmodel.fdf;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import org.apache.pdfbox.cos.COSDictionary;
/*    */ import org.apache.pdfbox.cos.COSName;
/*    */ import org.w3c.dom.Element;
/*    */ 
/*    */ public class FDFAnnotationPolygon extends FDFAnnotation
/*    */ {
/*    */   public static final String SUBTYPE = "Polygon";
/*    */ 
/*    */   public FDFAnnotationPolygon()
/*    */   {
/* 44 */     this.annot.setName(COSName.SUBTYPE, "Polygon");
/*    */   }
/*    */ 
/*    */   public FDFAnnotationPolygon(COSDictionary a)
/*    */   {
/* 54 */     super(a);
/*    */   }
/*    */ 
/*    */   public FDFAnnotationPolygon(Element element)
/*    */     throws IOException
/*    */   {
/* 66 */     super(element);
/* 67 */     this.annot.setName(COSName.SUBTYPE, "Polygon");
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.pdmodel.fdf.FDFAnnotationPolygon
 * JD-Core Version:    0.6.2
 */