/*     */ package org.apache.pdfbox.preflight.action;
/*     */ 
/*     */ import org.apache.pdfbox.cos.COSBase;
/*     */ import org.apache.pdfbox.cos.COSDictionary;
/*     */ import org.apache.pdfbox.cos.COSDocument;
/*     */ import org.apache.pdfbox.cos.COSName;
/*     */ import org.apache.pdfbox.preflight.PreflightContext;
/*     */ import org.apache.pdfbox.preflight.PreflightDocument;
/*     */ import org.apache.pdfbox.preflight.ValidationResult.ValidationError;
/*     */ import org.apache.pdfbox.preflight.utils.COSUtils;
/*     */ 
/*     */ public class HideAction extends AbstractActionManager
/*     */ {
/*     */   public HideAction(ActionManagerFactory amFact, COSDictionary adict, PreflightContext ctx, String aaKey)
/*     */   {
/*  55 */     super(amFact, adict, ctx, aaKey);
/*     */   }
/*     */ 
/*     */   protected boolean innerValid()
/*     */   {
/*  66 */     COSBase t = this.actionDictionnary.getItem(COSName.T);
/*     */ 
/*  68 */     if (t == null)
/*     */     {
/*  70 */       this.context.addValidationError(new ValidationResult.ValidationError("6.1.1", "T entry is mandatory for the NamedActions"));
/*     */ 
/*  72 */       return false;
/*     */     }
/*     */ 
/*  75 */     COSDocument cosDocument = this.context.getDocument().getDocument();
/*  76 */     if ((!COSUtils.isDictionary(t, cosDocument)) && (!COSUtils.isArray(t, cosDocument)) && (!COSUtils.isString(t, cosDocument)))
/*     */     {
/*  79 */       this.context.addValidationError(new ValidationResult.ValidationError("6.1.3", "T entry type is invalid"));
/*  80 */       return false;
/*     */     }
/*     */ 
/*  95 */     boolean h = this.actionDictionnary.getBoolean(COSName.H, true);
/*  96 */     if (h)
/*     */     {
/*  98 */       this.context.addValidationError(new ValidationResult.ValidationError("6.1.4", "H entry is \"true\""));
/*  99 */       return false;
/*     */     }
/*     */ 
/* 102 */     return true;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.preflight.action.HideAction
 * JD-Core Version:    0.6.2
 */