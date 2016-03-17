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
/*    */ public class ThreadAction extends AbstractActionManager
/*    */ {
/*    */   public ThreadAction(ActionManagerFactory amFact, COSDictionary adict, PreflightContext ctx, String aaKey)
/*    */   {
/* 53 */     super(amFact, adict, ctx, aaKey);
/*    */   }
/*    */ 
/*    */   protected boolean innerValid()
/*    */   {
/* 64 */     COSBase d = this.actionDictionnary.getItem(COSName.D);
/*    */ 
/* 67 */     if (d == null)
/*    */     {
/* 69 */       this.context.addValidationError(new ValidationResult.ValidationError("6.1.1", "D entry is mandatory for the ThreadAction"));
/*    */ 
/* 71 */       return false;
/*    */     }
/*    */ 
/* 74 */     COSDocument cosDocument = this.context.getDocument().getDocument();
/* 75 */     if ((!COSUtils.isInteger(d, cosDocument)) && (!COSUtils.isString(d, cosDocument)) && (!COSUtils.isDictionary(d, cosDocument)))
/*    */     {
/* 78 */       this.context.addValidationError(new ValidationResult.ValidationError("6.1.3", "D entry type is invalid"));
/* 79 */       return false;
/*    */     }
/*    */ 
/* 82 */     return true;
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.preflight.action.ThreadAction
 * JD-Core Version:    0.6.2
 */