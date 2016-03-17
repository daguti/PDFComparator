/*    */ package org.apache.pdfbox.pdmodel.graphics.optionalcontent;
/*    */ 
/*    */ import org.apache.pdfbox.cos.COSBase;
/*    */ import org.apache.pdfbox.cos.COSDictionary;
/*    */ import org.apache.pdfbox.cos.COSName;
/*    */ import org.apache.pdfbox.pdmodel.common.COSObjectable;
/*    */ 
/*    */ public class PDOptionalContentGroup
/*    */   implements COSObjectable
/*    */ {
/*    */   private COSDictionary ocg;
/*    */ 
/*    */   public PDOptionalContentGroup(String name)
/*    */   {
/* 41 */     this.ocg = new COSDictionary();
/* 42 */     this.ocg.setItem(COSName.TYPE, COSName.OCG);
/* 43 */     setName(name);
/*    */   }
/*    */ 
/*    */   public PDOptionalContentGroup(COSDictionary dict)
/*    */   {
/* 52 */     if (!dict.getItem(COSName.TYPE).equals(COSName.OCG))
/*    */     {
/* 54 */       throw new IllegalArgumentException("Provided dictionary is not of type '" + COSName.OCG + "'");
/*    */     }
/*    */ 
/* 57 */     this.ocg = dict;
/*    */   }
/*    */ 
/*    */   public COSBase getCOSObject()
/*    */   {
/* 63 */     return this.ocg;
/*    */   }
/*    */ 
/*    */   public String getName()
/*    */   {
/* 72 */     return this.ocg.getString(COSName.NAME);
/*    */   }
/*    */ 
/*    */   public void setName(String name)
/*    */   {
/* 81 */     this.ocg.setString(COSName.NAME, name);
/*    */   }
/*    */ 
/*    */   public String toString()
/*    */   {
/* 90 */     return super.toString() + " (" + getName() + ")";
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.pdmodel.graphics.optionalcontent.PDOptionalContentGroup
 * JD-Core Version:    0.6.2
 */