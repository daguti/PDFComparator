/*    */ package org.apache.pdfbox.pdmodel.interactive.action.type;
/*    */ 
/*    */ import org.apache.pdfbox.cos.COSBase;
/*    */ import org.apache.pdfbox.cos.COSDictionary;
/*    */ import org.apache.pdfbox.pdmodel.common.COSObjectable;
/*    */ 
/*    */ public class PDURIDictionary
/*    */   implements COSObjectable
/*    */ {
/*    */   private COSDictionary uriDictionary;
/*    */ 
/*    */   public PDURIDictionary()
/*    */   {
/* 40 */     this.uriDictionary = new COSDictionary();
/*    */   }
/*    */ 
/*    */   public PDURIDictionary(COSDictionary dictionary)
/*    */   {
/* 50 */     this.uriDictionary = dictionary;
/*    */   }
/*    */ 
/*    */   public COSBase getCOSObject()
/*    */   {
/* 58 */     return this.uriDictionary;
/*    */   }
/*    */ 
/*    */   public COSDictionary getDictionary()
/*    */   {
/* 67 */     return this.uriDictionary;
/*    */   }
/*    */ 
/*    */   public String getBase()
/*    */   {
/* 82 */     return getDictionary().getString("Base");
/*    */   }
/*    */ 
/*    */   public void setBase(String base)
/*    */   {
/* 97 */     getDictionary().setString("Base", base);
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.pdmodel.interactive.action.type.PDURIDictionary
 * JD-Core Version:    0.6.2
 */