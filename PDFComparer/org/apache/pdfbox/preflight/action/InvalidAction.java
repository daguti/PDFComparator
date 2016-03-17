/*    */ package org.apache.pdfbox.preflight.action;
/*    */ 
/*    */ import org.apache.pdfbox.cos.COSDictionary;
/*    */ import org.apache.pdfbox.preflight.PreflightContext;
/*    */ import org.apache.pdfbox.preflight.ValidationResult.ValidationError;
/*    */ 
/*    */ public class InvalidAction extends AbstractActionManager
/*    */ {
/* 36 */   private String actionName = null;
/*    */ 
/*    */   public InvalidAction(ActionManagerFactory amFact, COSDictionary adict, PreflightContext ctx, String aaKey, String name)
/*    */   {
/* 54 */     super(amFact, adict, ctx, aaKey);
/* 55 */     this.actionName = name;
/*    */   }
/*    */ 
/*    */   protected boolean innerValid()
/*    */   {
/* 66 */     this.context.addValidationError(new ValidationResult.ValidationError("6.2.5", "The action " + this.actionName + " is forbidden"));
/*    */ 
/* 68 */     return false;
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.preflight.action.InvalidAction
 * JD-Core Version:    0.6.2
 */