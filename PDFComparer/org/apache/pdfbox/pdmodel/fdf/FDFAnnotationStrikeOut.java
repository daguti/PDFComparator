/*    */ package org.apache.pdfbox.pdmodel.fdf;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import org.apache.pdfbox.cos.COSDictionary;
/*    */ import org.apache.pdfbox.cos.COSName;
/*    */ import org.w3c.dom.Element;
/*    */ 
/*    */ public class FDFAnnotationStrikeOut extends FDFAnnotation
/*    */ {
/*    */   public static final String SUBTYPE = "StrikeOut";
/*    */ 
/*    */   public FDFAnnotationStrikeOut()
/*    */   {
/* 44 */     this.annot.setName(COSName.SUBTYPE, "StrikeOut");
/*    */   }
/*    */ 
/*    */   public FDFAnnotationStrikeOut(COSDictionary a)
/*    */   {
/* 54 */     super(a);
/*    */   }
/*    */ 
/*    */   public FDFAnnotationStrikeOut(Element element)
/*    */     throws IOException
/*    */   {
/* 66 */     super(element);
/* 67 */     this.annot.setName(COSName.SUBTYPE, "StrikeOut");
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.pdmodel.fdf.FDFAnnotationStrikeOut
 * JD-Core Version:    0.6.2
 */