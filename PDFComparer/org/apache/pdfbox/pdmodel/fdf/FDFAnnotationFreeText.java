/*    */ package org.apache.pdfbox.pdmodel.fdf;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import org.apache.pdfbox.cos.COSDictionary;
/*    */ import org.apache.pdfbox.cos.COSName;
/*    */ import org.w3c.dom.Element;
/*    */ 
/*    */ public class FDFAnnotationFreeText extends FDFAnnotation
/*    */ {
/*    */   public static final String SUBTYPE = "FreeText";
/*    */ 
/*    */   public FDFAnnotationFreeText()
/*    */   {
/* 44 */     this.annot.setName(COSName.SUBTYPE, "FreeText");
/*    */   }
/*    */ 
/*    */   public FDFAnnotationFreeText(COSDictionary a)
/*    */   {
/* 54 */     super(a);
/*    */   }
/*    */ 
/*    */   public FDFAnnotationFreeText(Element element)
/*    */     throws IOException
/*    */   {
/* 66 */     super(element);
/* 67 */     this.annot.setName(COSName.SUBTYPE, "FreeText");
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.pdmodel.fdf.FDFAnnotationFreeText
 * JD-Core Version:    0.6.2
 */