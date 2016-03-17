/*    */ package org.apache.pdfbox.pdmodel.fdf;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import org.apache.pdfbox.cos.COSDictionary;
/*    */ import org.apache.pdfbox.cos.COSName;
/*    */ import org.w3c.dom.Element;
/*    */ 
/*    */ public class FDFAnnotationSquiggly extends FDFAnnotation
/*    */ {
/*    */   public static final String SUBTYPE = "Squiggly";
/*    */ 
/*    */   public FDFAnnotationSquiggly()
/*    */   {
/* 44 */     this.annot.setName(COSName.SUBTYPE, "Squiggly");
/*    */   }
/*    */ 
/*    */   public FDFAnnotationSquiggly(COSDictionary a)
/*    */   {
/* 54 */     super(a);
/*    */   }
/*    */ 
/*    */   public FDFAnnotationSquiggly(Element element)
/*    */     throws IOException
/*    */   {
/* 66 */     super(element);
/* 67 */     this.annot.setName(COSName.SUBTYPE, "Squiggly");
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.pdmodel.fdf.FDFAnnotationSquiggly
 * JD-Core Version:    0.6.2
 */