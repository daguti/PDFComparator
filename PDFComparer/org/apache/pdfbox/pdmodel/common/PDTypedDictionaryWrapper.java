/*    */ package org.apache.pdfbox.pdmodel.common;
/*    */ 
/*    */ import org.apache.pdfbox.cos.COSDictionary;
/*    */ import org.apache.pdfbox.cos.COSName;
/*    */ 
/*    */ public class PDTypedDictionaryWrapper extends PDDictionaryWrapper
/*    */ {
/*    */   public PDTypedDictionaryWrapper(String type)
/*    */   {
/* 40 */     getCOSDictionary().setName(COSName.TYPE, type);
/*    */   }
/*    */ 
/*    */   public PDTypedDictionaryWrapper(COSDictionary dictionary)
/*    */   {
/* 50 */     super(dictionary);
/*    */   }
/*    */ 
/*    */   public String getType()
/*    */   {
/* 61 */     return getCOSDictionary().getNameAsString(COSName.TYPE);
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.pdmodel.common.PDTypedDictionaryWrapper
 * JD-Core Version:    0.6.2
 */