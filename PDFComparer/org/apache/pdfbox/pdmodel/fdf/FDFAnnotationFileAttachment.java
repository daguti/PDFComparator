/*    */ package org.apache.pdfbox.pdmodel.fdf;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import org.apache.pdfbox.cos.COSDictionary;
/*    */ import org.apache.pdfbox.cos.COSName;
/*    */ import org.w3c.dom.Element;
/*    */ 
/*    */ public class FDFAnnotationFileAttachment extends FDFAnnotation
/*    */ {
/*    */   public static final String SUBTYPE = "FileAttachment";
/*    */ 
/*    */   public FDFAnnotationFileAttachment()
/*    */   {
/* 44 */     this.annot.setName(COSName.SUBTYPE, "FileAttachment");
/*    */   }
/*    */ 
/*    */   public FDFAnnotationFileAttachment(COSDictionary a)
/*    */   {
/* 54 */     super(a);
/*    */   }
/*    */ 
/*    */   public FDFAnnotationFileAttachment(Element element)
/*    */     throws IOException
/*    */   {
/* 66 */     super(element);
/* 67 */     this.annot.setName(COSName.SUBTYPE, "FileAttachment");
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.pdmodel.fdf.FDFAnnotationFileAttachment
 * JD-Core Version:    0.6.2
 */