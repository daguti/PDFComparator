/*    */ package org.apache.pdfbox.preflight.action;
/*    */ 
/*    */ import org.apache.pdfbox.cos.COSBase;
/*    */ import org.apache.pdfbox.cos.COSDictionary;
/*    */ import org.apache.pdfbox.cos.COSName;
/*    */ import org.apache.pdfbox.preflight.PreflightContext;
/*    */ import org.apache.pdfbox.preflight.ValidationResult.ValidationError;
/*    */ 
/*    */ public class SubmitAction extends AbstractActionManager
/*    */ {
/*    */   public SubmitAction(ActionManagerFactory amFact, COSDictionary adict, PreflightContext ctx, String aaKey)
/*    */   {
/* 50 */     super(amFact, adict, ctx, aaKey);
/*    */   }
/*    */ 
/*    */   protected boolean innerValid()
/*    */   {
/* 61 */     COSBase f = this.actionDictionnary.getItem(COSName.F);
/* 62 */     if (f == null)
/*    */     {
/* 64 */       this.context.addValidationError(new ValidationResult.ValidationError("6.1.1", "F entry is mandatory for the SubmitActions"));
/*    */ 
/* 66 */       return false;
/*    */     }
/* 68 */     return true;
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.preflight.action.SubmitAction
 * JD-Core Version:    0.6.2
 */