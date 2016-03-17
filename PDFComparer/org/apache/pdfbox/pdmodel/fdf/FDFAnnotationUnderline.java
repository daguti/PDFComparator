/*    */ package org.apache.pdfbox.pdmodel.fdf;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import org.apache.pdfbox.cos.COSDictionary;
/*    */ import org.apache.pdfbox.cos.COSName;
/*    */ import org.w3c.dom.Element;
/*    */ 
/*    */ public class FDFAnnotationUnderline extends FDFAnnotation
/*    */ {
/*    */   public static final String SUBTYPE = "Underline";
/*    */ 
/*    */   public FDFAnnotationUnderline()
/*    */   {
/* 44 */     this.annot.setName(COSName.SUBTYPE, "Underline");
/*    */   }
/*    */ 
/*    */   public FDFAnnotationUnderline(COSDictionary a)
/*    */   {
/* 54 */     super(a);
/*    */   }
/*    */ 
/*    */   public FDFAnnotationUnderline(Element element)
/*    */     throws IOException
/*    */   {
/* 66 */     super(element);
/* 67 */     this.annot.setName(COSName.SUBTYPE, "Underline");
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.pdmodel.fdf.FDFAnnotationUnderline
 * JD-Core Version:    0.6.2
 */