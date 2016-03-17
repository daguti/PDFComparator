/*    */ package org.apache.pdfbox.pdmodel.fdf;
/*    */ 
/*    */ import org.apache.pdfbox.cos.COSBase;
/*    */ import org.apache.pdfbox.cos.COSDictionary;
/*    */ import org.apache.pdfbox.pdmodel.common.COSObjectable;
/*    */ 
/*    */ public class FDFPageInfo
/*    */   implements COSObjectable
/*    */ {
/*    */   private COSDictionary pageInfo;
/*    */ 
/*    */   public FDFPageInfo()
/*    */   {
/* 39 */     this.pageInfo = new COSDictionary();
/*    */   }
/*    */ 
/*    */   public FDFPageInfo(COSDictionary p)
/*    */   {
/* 49 */     this.pageInfo = p;
/*    */   }
/*    */ 
/*    */   public COSBase getCOSObject()
/*    */   {
/* 59 */     return this.pageInfo;
/*    */   }
/*    */ 
/*    */   public COSDictionary getCOSDictionary()
/*    */   {
/* 69 */     return this.pageInfo;
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.pdmodel.fdf.FDFPageInfo
 * JD-Core Version:    0.6.2
 */