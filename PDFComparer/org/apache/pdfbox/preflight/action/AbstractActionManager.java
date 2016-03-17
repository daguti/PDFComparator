/*     */ package org.apache.pdfbox.preflight.action;
/*     */ 
/*     */ import java.util.List;
/*     */ import org.apache.pdfbox.cos.COSDictionary;
/*     */ import org.apache.pdfbox.preflight.PreflightContext;
/*     */ import org.apache.pdfbox.preflight.ValidationResult.ValidationError;
/*     */ import org.apache.pdfbox.preflight.exception.ValidationException;
/*     */ 
/*     */ public abstract class AbstractActionManager
/*     */ {
/*  38 */   protected ActionManagerFactory actionFact = null;
/*     */ 
/*  42 */   protected String aaKey = null;
/*     */ 
/*  46 */   protected COSDictionary actionDictionnary = null;
/*     */ 
/*  50 */   protected PreflightContext context = null;
/*     */ 
/*     */   AbstractActionManager(ActionManagerFactory amFact, COSDictionary adict, PreflightContext ctx, String aaKey)
/*     */   {
/*  65 */     this.actionFact = amFact;
/*  66 */     this.actionDictionnary = adict;
/*  67 */     this.aaKey = aaKey;
/*  68 */     this.context = ctx;
/*     */   }
/*     */ 
/*     */   public boolean isAdditionalAction()
/*     */   {
/*  76 */     return this.aaKey != null;
/*     */   }
/*     */ 
/*     */   public COSDictionary getActionDictionnary()
/*     */   {
/*  84 */     return this.actionDictionnary;
/*     */   }
/*     */ 
/*     */   public String getAdditionalActionKey()
/*     */   {
/*  92 */     return this.aaKey;
/*     */   }
/*     */ 
/*     */   protected boolean validNextActions()
/*     */     throws ValidationException
/*     */   {
/* 104 */     List lActions = this.actionFact.getNextActions(this.context, this.actionDictionnary);
/* 105 */     for (AbstractActionManager nAction : lActions)
/*     */     {
/* 107 */       if (!nAction.innerValid())
/*     */       {
/* 109 */         return false;
/*     */       }
/*     */     }
/* 112 */     return true;
/*     */   }
/*     */ 
/*     */   public boolean valid()
/*     */     throws ValidationException
/*     */   {
/* 125 */     return valid(false);
/*     */   }
/*     */ 
/*     */   public boolean valid(boolean additonalActionAuth)
/*     */     throws ValidationException
/*     */   {
/* 146 */     if ((isAdditionalAction()) && (!additonalActionAuth))
/*     */     {
/* 148 */       this.context.addValidationError(new ValidationResult.ValidationError("6.2.2", "Additional Action are forbidden"));
/*     */ 
/* 150 */       return false;
/*     */     }
/*     */ 
/* 153 */     if (innerValid())
/*     */     {
/* 155 */       return validNextActions();
/*     */     }
/*     */ 
/* 158 */     return true;
/*     */   }
/*     */ 
/*     */   protected abstract boolean innerValid();
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.preflight.action.AbstractActionManager
 * JD-Core Version:    0.6.2
 */