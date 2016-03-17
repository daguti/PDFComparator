/*    */ package org.apache.pdfbox.pdmodel.fdf;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import org.apache.pdfbox.cos.COSDictionary;
/*    */ import org.apache.pdfbox.cos.COSName;
/*    */ import org.w3c.dom.Element;
/*    */ 
/*    */ public class FDFAnnotationStamp extends FDFAnnotation
/*    */ {
/*    */   public static final String SUBTYPE = "Stamp";
/*    */ 
/*    */   public FDFAnnotationStamp()
/*    */   {
/* 44 */     this.annot.setName(COSName.SUBTYPE, "Stamp");
/*    */   }
/*    */ 
/*    */   public FDFAnnotationStamp(COSDictionary a)
/*    */   {
/* 54 */     super(a);
/*    */   }
/*    */ 
/*    */   public FDFAnnotationStamp(Element element)
/*    */     throws IOException
/*    */   {
/* 66 */     super(element);
/* 67 */     this.annot.setName(COSName.SUBTYPE, "Stamp");
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.pdmodel.fdf.FDFAnnotationStamp
 * JD-Core Version:    0.6.2
 */