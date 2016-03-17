/*    */ package org.apache.pdfbox.pdmodel.markedcontent;
/*    */ 
/*    */ import org.apache.pdfbox.cos.COSBase;
/*    */ import org.apache.pdfbox.cos.COSDictionary;
/*    */ import org.apache.pdfbox.cos.COSName;
/*    */ import org.apache.pdfbox.pdmodel.common.COSObjectable;
/*    */ import org.apache.pdfbox.pdmodel.graphics.optionalcontent.PDOptionalContentGroup;
/*    */ 
/*    */ public class PDPropertyList
/*    */   implements COSObjectable
/*    */ {
/*    */   private COSDictionary props;
/*    */ 
/*    */   public PDPropertyList()
/*    */   {
/* 42 */     this.props = new COSDictionary();
/*    */   }
/*    */ 
/*    */   public PDPropertyList(COSDictionary dict)
/*    */   {
/* 51 */     this.props = dict;
/*    */   }
/*    */ 
/*    */   public COSBase getCOSObject()
/*    */   {
/* 57 */     return this.props;
/*    */   }
/*    */ 
/*    */   public PDOptionalContentGroup getOptionalContentGroup(COSName name)
/*    */   {
/* 67 */     COSDictionary dict = (COSDictionary)this.props.getDictionaryObject(name);
/* 68 */     if (dict != null)
/*    */     {
/* 70 */       if (COSName.OCG.equals(dict.getItem(COSName.TYPE)))
/*    */       {
/* 72 */         return new PDOptionalContentGroup(dict);
/*    */       }
/*    */     }
/* 75 */     return null;
/*    */   }
/*    */ 
/*    */   public void putMapping(COSName name, PDOptionalContentGroup ocg)
/*    */   {
/* 85 */     putMapping(name, (COSDictionary)ocg.getCOSObject());
/*    */   }
/*    */ 
/*    */   private void putMapping(COSName name, COSDictionary dict)
/*    */   {
/* 90 */     this.props.setItem(name, dict);
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.pdmodel.markedcontent.PDPropertyList
 * JD-Core Version:    0.6.2
 */