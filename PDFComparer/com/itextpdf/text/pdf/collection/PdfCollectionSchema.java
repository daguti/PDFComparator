/*    */ package com.itextpdf.text.pdf.collection;
/*    */ 
/*    */ import com.itextpdf.text.pdf.PdfDictionary;
/*    */ import com.itextpdf.text.pdf.PdfName;
/*    */ 
/*    */ public class PdfCollectionSchema extends PdfDictionary
/*    */ {
/*    */   public PdfCollectionSchema()
/*    */   {
/* 55 */     super(PdfName.COLLECTIONSCHEMA);
/*    */   }
/*    */ 
/*    */   public void addField(String name, PdfCollectionField field)
/*    */   {
/* 64 */     put(new PdfName(name), field);
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.pdf.collection.PdfCollectionSchema
 * JD-Core Version:    0.6.2
 */