/*    */ package org.apache.pdfbox.preflight.action;
/*    */ 
/*    */ import org.apache.pdfbox.cos.COSDictionary;
/*    */ import org.apache.pdfbox.cos.COSName;
/*    */ import org.apache.pdfbox.preflight.PreflightContext;
/*    */ import org.apache.pdfbox.preflight.ValidationResult.ValidationError;
/*    */ 
/*    */ public class NamedAction extends AbstractActionManager
/*    */ {
/*    */   public NamedAction(ActionManagerFactory amFact, COSDictionary adict, PreflightContext ctx, String aaKey)
/*    */   {
/* 60 */     super(amFact, adict, ctx, aaKey);
/*    */   }
/*    */ 
/*    */   protected boolean innerValid()
/*    */   {
/* 71 */     String n = this.actionDictionnary.getNameAsString(COSName.N);
/*    */ 
/* 74 */     if ((n == null) || ("".equals(n)))
/*    */     {
/* 76 */       this.context.addValidationError(new ValidationResult.ValidationError("6.1.1", "N entry is mandatory for the NamedActions"));
/*    */ 
/* 78 */       return false;
/*    */     }
/*    */ 
/* 82 */     if ((!"FirstPage".equals(n)) && (!"LastPage".equals(n)) && (!"NextPage".equals(n)) && (!"PrevPage".equals(n)))
/*    */     {
/* 86 */       this.context.addValidationError(new ValidationResult.ValidationError("6.2.1", n + " isn't authorized as named action"));
/*    */ 
/* 88 */       return false;
/*    */     }
/*    */ 
/* 91 */     return true;
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.preflight.action.NamedAction
 * JD-Core Version:    0.6.2
 */