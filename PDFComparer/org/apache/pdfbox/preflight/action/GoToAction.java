/*    */ package org.apache.pdfbox.preflight.action;
/*    */ 
/*    */ import org.apache.pdfbox.cos.COSBase;
/*    */ import org.apache.pdfbox.cos.COSDictionary;
/*    */ import org.apache.pdfbox.cos.COSDocument;
/*    */ import org.apache.pdfbox.cos.COSName;
/*    */ import org.apache.pdfbox.preflight.PreflightContext;
/*    */ import org.apache.pdfbox.preflight.PreflightDocument;
/*    */ import org.apache.pdfbox.preflight.ValidationResult.ValidationError;
/*    */ import org.apache.pdfbox.preflight.utils.COSUtils;
/*    */ 
/*    */ public class GoToAction extends AbstractActionManager
/*    */ {
/*    */   public GoToAction(ActionManagerFactory amFact, COSDictionary adict, PreflightContext ctx, String aa)
/*    */   {
/* 54 */     super(amFact, adict, ctx, aa);
/*    */   }
/*    */ 
/*    */   protected boolean innerValid()
/*    */   {
/* 65 */     COSBase d = this.actionDictionnary.getItem(COSName.D);
/*    */ 
/* 68 */     if (d == null)
/*    */     {
/* 70 */       this.context.addValidationError(new ValidationResult.ValidationError("6.1.1", "D entry is mandatory for the GoToActions"));
/*    */ 
/* 72 */       return false;
/*    */     }
/*    */ 
/* 75 */     COSDocument cosDocument = this.context.getDocument().getDocument();
/* 76 */     if ((!(d instanceof COSName)) && (!COSUtils.isString(d, cosDocument)) && (!COSUtils.isArray(d, cosDocument)))
/*    */     {
/* 78 */       this.context.addValidationError(new ValidationResult.ValidationError("6.1.3", "Type of D entry is invalid"));
/* 79 */       return false;
/*    */     }
/*    */ 
/* 82 */     return true;
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.preflight.action.GoToAction
 * JD-Core Version:    0.6.2
 */