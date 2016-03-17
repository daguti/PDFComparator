/*    */ package org.apache.pdfbox.pdmodel.interactive.action;
/*    */ 
/*    */ import org.apache.pdfbox.cos.COSBase;
/*    */ import org.apache.pdfbox.cos.COSDictionary;
/*    */ import org.apache.pdfbox.pdmodel.common.COSObjectable;
/*    */ import org.apache.pdfbox.pdmodel.interactive.action.type.PDAction;
/*    */ 
/*    */ public class PDAdditionalActions
/*    */   implements COSObjectable
/*    */ {
/*    */   private COSDictionary actions;
/*    */ 
/*    */   public PDAdditionalActions()
/*    */   {
/* 40 */     this.actions = new COSDictionary();
/*    */   }
/*    */ 
/*    */   public PDAdditionalActions(COSDictionary a)
/*    */   {
/* 50 */     this.actions = a;
/*    */   }
/*    */ 
/*    */   public COSBase getCOSObject()
/*    */   {
/* 60 */     return this.actions;
/*    */   }
/*    */ 
/*    */   public COSDictionary getCOSDictionary()
/*    */   {
/* 70 */     return this.actions;
/*    */   }
/*    */ 
/*    */   public PDAction getF()
/*    */   {
/* 80 */     return PDActionFactory.createAction((COSDictionary)this.actions.getDictionaryObject("F"));
/*    */   }
/*    */ 
/*    */   public void setF(PDAction action)
/*    */   {
/* 90 */     this.actions.setItem("F", action);
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.pdmodel.interactive.action.PDAdditionalActions
 * JD-Core Version:    0.6.2
 */