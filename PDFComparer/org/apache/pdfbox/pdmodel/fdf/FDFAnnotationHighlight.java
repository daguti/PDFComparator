/*    */ package org.apache.pdfbox.pdmodel.fdf;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import org.apache.pdfbox.cos.COSDictionary;
/*    */ import org.apache.pdfbox.cos.COSName;
/*    */ import org.w3c.dom.Element;
/*    */ 
/*    */ public class FDFAnnotationHighlight extends FDFAnnotation
/*    */ {
/*    */   public static final String SUBTYPE = "Highlight";
/*    */ 
/*    */   public FDFAnnotationHighlight()
/*    */   {
/* 44 */     this.annot.setName(COSName.SUBTYPE, "Highlight");
/*    */   }
/*    */ 
/*    */   public FDFAnnotationHighlight(COSDictionary a)
/*    */   {
/* 54 */     super(a);
/*    */   }
/*    */ 
/*    */   public FDFAnnotationHighlight(Element element)
/*    */     throws IOException
/*    */   {
/* 66 */     super(element);
/* 67 */     this.annot.setName(COSName.SUBTYPE, "Highlight");
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.pdmodel.fdf.FDFAnnotationHighlight
 * JD-Core Version:    0.6.2
 */