/*    */ package org.apache.pdfbox.pdmodel.common.filespecification;
/*    */ 
/*    */ import org.apache.pdfbox.cos.COSBase;
/*    */ import org.apache.pdfbox.cos.COSString;
/*    */ 
/*    */ public class PDSimpleFileSpecification extends PDFileSpecification
/*    */ {
/*    */   private COSString file;
/*    */ 
/*    */   public PDSimpleFileSpecification()
/*    */   {
/* 38 */     this.file = new COSString("");
/*    */   }
/*    */ 
/*    */   public PDSimpleFileSpecification(COSString fileName)
/*    */   {
/* 48 */     this.file = fileName;
/*    */   }
/*    */ 
/*    */   public String getFile()
/*    */   {
/* 58 */     return this.file.getString();
/*    */   }
/*    */ 
/*    */   public void setFile(String fileName)
/*    */   {
/* 68 */     this.file = new COSString(fileName);
/*    */   }
/*    */ 
/*    */   public COSBase getCOSObject()
/*    */   {
/* 78 */     return this.file;
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.pdmodel.common.filespecification.PDSimpleFileSpecification
 * JD-Core Version:    0.6.2
 */