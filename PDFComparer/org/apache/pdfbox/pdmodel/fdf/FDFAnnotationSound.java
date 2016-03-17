/*    */ package org.apache.pdfbox.pdmodel.fdf;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import org.apache.pdfbox.cos.COSDictionary;
/*    */ import org.apache.pdfbox.cos.COSName;
/*    */ import org.w3c.dom.Element;
/*    */ 
/*    */ public class FDFAnnotationSound extends FDFAnnotation
/*    */ {
/*    */   public static final String SUBTYPE = "Sound";
/*    */ 
/*    */   public FDFAnnotationSound()
/*    */   {
/* 44 */     this.annot.setName(COSName.SUBTYPE, "Sound");
/*    */   }
/*    */ 
/*    */   public FDFAnnotationSound(COSDictionary a)
/*    */   {
/* 54 */     super(a);
/*    */   }
/*    */ 
/*    */   public FDFAnnotationSound(Element element)
/*    */     throws IOException
/*    */   {
/* 66 */     super(element);
/* 67 */     this.annot.setName(COSName.SUBTYPE, "Sound");
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.pdmodel.fdf.FDFAnnotationSound
 * JD-Core Version:    0.6.2
 */