/*    */ package org.apache.pdfbox.pdmodel.fdf;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import org.apache.pdfbox.cos.COSDictionary;
/*    */ import org.apache.pdfbox.cos.COSName;
/*    */ import org.w3c.dom.Element;
/*    */ 
/*    */ public class FDFAnnotationText extends FDFAnnotation
/*    */ {
/*    */   public static final String SUBTYPE = "Text";
/*    */ 
/*    */   public FDFAnnotationText()
/*    */   {
/* 44 */     this.annot.setName(COSName.SUBTYPE, "Text");
/*    */   }
/*    */ 
/*    */   public FDFAnnotationText(COSDictionary a)
/*    */   {
/* 54 */     super(a);
/*    */   }
/*    */ 
/*    */   public FDFAnnotationText(Element element)
/*    */     throws IOException
/*    */   {
/* 66 */     super(element);
/* 67 */     this.annot.setName(COSName.SUBTYPE, "Text");
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.pdmodel.fdf.FDFAnnotationText
 * JD-Core Version:    0.6.2
 */