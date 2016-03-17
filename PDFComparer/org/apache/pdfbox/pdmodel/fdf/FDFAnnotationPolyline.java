/*    */ package org.apache.pdfbox.pdmodel.fdf;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import org.apache.pdfbox.cos.COSDictionary;
/*    */ import org.apache.pdfbox.cos.COSName;
/*    */ import org.w3c.dom.Element;
/*    */ 
/*    */ public class FDFAnnotationPolyline extends FDFAnnotation
/*    */ {
/*    */   public static final String SUBTYPE = "Polyline";
/*    */ 
/*    */   public FDFAnnotationPolyline()
/*    */   {
/* 44 */     this.annot.setName(COSName.SUBTYPE, "Polyline");
/*    */   }
/*    */ 
/*    */   public FDFAnnotationPolyline(COSDictionary a)
/*    */   {
/* 54 */     super(a);
/*    */   }
/*    */ 
/*    */   public FDFAnnotationPolyline(Element element)
/*    */     throws IOException
/*    */   {
/* 66 */     super(element);
/* 67 */     this.annot.setName(COSName.SUBTYPE, "Polyline");
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.pdmodel.fdf.FDFAnnotationPolyline
 * JD-Core Version:    0.6.2
 */