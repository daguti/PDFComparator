/*    */ package org.apache.pdfbox.pdmodel.fdf;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import org.apache.pdfbox.cos.COSDictionary;
/*    */ import org.apache.pdfbox.cos.COSName;
/*    */ import org.w3c.dom.Element;
/*    */ 
/*    */ public class FDFAnnotationInk extends FDFAnnotation
/*    */ {
/*    */   public static final String SUBTYPE = "Ink";
/*    */ 
/*    */   public FDFAnnotationInk()
/*    */   {
/* 44 */     this.annot.setName(COSName.SUBTYPE, "Ink");
/*    */   }
/*    */ 
/*    */   public FDFAnnotationInk(COSDictionary a)
/*    */   {
/* 54 */     super(a);
/*    */   }
/*    */ 
/*    */   public FDFAnnotationInk(Element element)
/*    */     throws IOException
/*    */   {
/* 66 */     super(element);
/* 67 */     this.annot.setName(COSName.SUBTYPE, "Ink");
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.pdmodel.fdf.FDFAnnotationInk
 * JD-Core Version:    0.6.2
 */