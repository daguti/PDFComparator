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
/*    */ public class UriAction extends AbstractActionManager
/*    */ {
/*    */   public UriAction(ActionManagerFactory amFact, COSDictionary adict, PreflightContext ctx, String aaKey)
/*    */   {
/* 53 */     super(amFact, adict, ctx, aaKey);
/*    */   }
/*    */ 
/*    */   protected boolean innerValid()
/*    */   {
/* 64 */     COSBase uri = this.actionDictionnary.getItem(COSName.URI);
/* 65 */     if (uri == null)
/*    */     {
/* 67 */       this.context.addValidationError(new ValidationResult.ValidationError("6.1.1", "URI entry is mandatory for the UriAction"));
/*    */ 
/* 69 */       return false;
/*    */     }
/*    */ 
/* 72 */     COSDocument cosDocument = this.context.getDocument().getDocument();
/* 73 */     if (!COSUtils.isString(uri, cosDocument))
/*    */     {
/* 75 */       this.context.addValidationError(new ValidationResult.ValidationError("6.1.3", "URI entry should be a string"));
/* 76 */       return false;
/*    */     }
/*    */ 
/* 79 */     return true;
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.preflight.action.UriAction
 * JD-Core Version:    0.6.2
 */