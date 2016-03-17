/*    */ package org.apache.pdfbox.pdmodel.interactive.annotation;
/*    */ 
/*    */ import org.apache.pdfbox.cos.COSBase;
/*    */ import org.apache.pdfbox.cos.COSDictionary;
/*    */ import org.apache.pdfbox.cos.COSName;
/*    */ import org.apache.pdfbox.pdmodel.common.COSObjectable;
/*    */ 
/*    */ public class PDExternalDataDictionary
/*    */   implements COSObjectable
/*    */ {
/*    */   private COSDictionary dataDictionary;
/*    */ 
/*    */   public PDExternalDataDictionary()
/*    */   {
/* 40 */     this.dataDictionary = new COSDictionary();
/* 41 */     this.dataDictionary.setName(COSName.TYPE, "ExData");
/*    */   }
/*    */ 
/*    */   public PDExternalDataDictionary(COSDictionary dictionary)
/*    */   {
/* 51 */     this.dataDictionary = dictionary;
/*    */   }
/*    */ 
/*    */   public COSBase getCOSObject()
/*    */   {
/* 59 */     return this.dataDictionary;
/*    */   }
/*    */ 
/*    */   public COSDictionary getDictionary()
/*    */   {
/* 69 */     return this.dataDictionary;
/*    */   }
/*    */ 
/*    */   public String getType()
/*    */   {
/* 79 */     return getDictionary().getNameAsString(COSName.TYPE, "ExData");
/*    */   }
/*    */ 
/*    */   public String getSubtype()
/*    */   {
/* 88 */     return getDictionary().getNameAsString(COSName.SUBTYPE);
/*    */   }
/*    */ 
/*    */   public void setSubtype(String subtype)
/*    */   {
/* 97 */     getDictionary().setName(COSName.SUBTYPE, subtype);
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.pdmodel.interactive.annotation.PDExternalDataDictionary
 * JD-Core Version:    0.6.2
 */