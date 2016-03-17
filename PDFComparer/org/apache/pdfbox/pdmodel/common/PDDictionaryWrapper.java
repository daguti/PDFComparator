/*    */ package org.apache.pdfbox.pdmodel.common;
/*    */ 
/*    */ import org.apache.pdfbox.cos.COSBase;
/*    */ import org.apache.pdfbox.cos.COSDictionary;
/*    */ 
/*    */ public class PDDictionaryWrapper
/*    */   implements COSObjectable
/*    */ {
/*    */   private final COSDictionary dictionary;
/*    */ 
/*    */   public PDDictionaryWrapper()
/*    */   {
/* 39 */     this.dictionary = new COSDictionary();
/*    */   }
/*    */ 
/*    */   public PDDictionaryWrapper(COSDictionary dictionary)
/*    */   {
/* 49 */     this.dictionary = dictionary;
/*    */   }
/*    */ 
/*    */   public COSBase getCOSObject()
/*    */   {
/* 58 */     return this.dictionary;
/*    */   }
/*    */ 
/*    */   protected COSDictionary getCOSDictionary()
/*    */   {
/* 68 */     return this.dictionary;
/*    */   }
/*    */ 
/*    */   public boolean equals(Object obj)
/*    */   {
/* 75 */     if (this == obj)
/*    */     {
/* 77 */       return true;
/*    */     }
/* 79 */     if ((obj instanceof PDDictionaryWrapper))
/*    */     {
/* 81 */       return this.dictionary.equals(((PDDictionaryWrapper)obj).dictionary);
/*    */     }
/* 83 */     return false;
/*    */   }
/*    */ 
/*    */   public int hashCode()
/*    */   {
/* 89 */     return this.dictionary.hashCode();
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.pdmodel.common.PDDictionaryWrapper
 * JD-Core Version:    0.6.2
 */