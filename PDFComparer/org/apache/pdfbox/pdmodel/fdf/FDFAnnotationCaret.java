/*    */ package org.apache.pdfbox.pdmodel.fdf;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import org.apache.pdfbox.cos.COSDictionary;
/*    */ import org.apache.pdfbox.cos.COSName;
/*    */ import org.w3c.dom.Element;
/*    */ 
/*    */ public class FDFAnnotationCaret extends FDFAnnotation
/*    */ {
/*    */   public static final String SUBTYPE = "Caret";
/*    */ 
/*    */   public FDFAnnotationCaret()
/*    */   {
/* 44 */     this.annot.setName(COSName.SUBTYPE, "Caret");
/*    */   }
/*    */ 
/*    */   public FDFAnnotationCaret(COSDictionary a)
/*    */   {
/* 54 */     super(a);
/*    */   }
/*    */ 
/*    */   public FDFAnnotationCaret(Element element)
/*    */     throws IOException
/*    */   {
/* 66 */     super(element);
/* 67 */     this.annot.setName(COSName.SUBTYPE, "Caret");
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.pdmodel.fdf.FDFAnnotationCaret
 * JD-Core Version:    0.6.2
 */