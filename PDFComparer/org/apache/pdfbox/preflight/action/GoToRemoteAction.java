/*    */ package org.apache.pdfbox.preflight.action;
/*    */ 
/*    */ import org.apache.pdfbox.cos.COSBase;
/*    */ import org.apache.pdfbox.cos.COSDictionary;
/*    */ import org.apache.pdfbox.cos.COSName;
/*    */ import org.apache.pdfbox.preflight.PreflightContext;
/*    */ import org.apache.pdfbox.preflight.ValidationResult.ValidationError;
/*    */ 
/*    */ public class GoToRemoteAction extends GoToAction
/*    */ {
/*    */   public GoToRemoteAction(ActionManagerFactory amFact, COSDictionary adict, PreflightContext ctx, String aaKey)
/*    */   {
/* 51 */     super(amFact, adict, ctx, aaKey);
/*    */   }
/*    */ 
/*    */   protected boolean innerValid()
/*    */   {
/* 62 */     if (super.innerValid())
/*    */     {
/* 64 */       COSBase f = this.actionDictionnary.getItem(COSName.F);
/* 65 */       if (f == null)
/*    */       {
/* 67 */         this.context.addValidationError(new ValidationResult.ValidationError("6.1.1", "F entry is mandatory for the GoToRemoteActions"));
/*    */ 
/* 69 */         return false;
/*    */       }
/*    */     }
/* 72 */     return true;
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.preflight.action.GoToRemoteAction
 * JD-Core Version:    0.6.2
 */