/*    */ package org.apache.pdfbox.preflight.process.reflect;
/*    */ 
/*    */ import java.util.List;
/*    */ import org.apache.pdfbox.cos.COSDictionary;
/*    */ import org.apache.pdfbox.pdmodel.PDPage;
/*    */ import org.apache.pdfbox.preflight.PreflightConfiguration;
/*    */ import org.apache.pdfbox.preflight.PreflightContext;
/*    */ import org.apache.pdfbox.preflight.PreflightPath;
/*    */ import org.apache.pdfbox.preflight.ValidationResult.ValidationError;
/*    */ import org.apache.pdfbox.preflight.action.AbstractActionManager;
/*    */ import org.apache.pdfbox.preflight.action.ActionManagerFactory;
/*    */ import org.apache.pdfbox.preflight.exception.ValidationException;
/*    */ import org.apache.pdfbox.preflight.process.AbstractProcess;
/*    */ 
/*    */ public class ActionsValidationProcess extends AbstractProcess
/*    */ {
/*    */   public void validate(PreflightContext context)
/*    */     throws ValidationException
/*    */   {
/* 43 */     PreflightPath vPath = context.getValidationPath();
/* 44 */     if (vPath.isEmpty())
/*    */       return;
/*    */     boolean aaEntryAuth;
/* 47 */     if (!vPath.isExpectedType(COSDictionary.class))
/*    */     {
/* 49 */       context.addValidationError(new ValidationResult.ValidationError("6.1.3", "Action validation process needs at least one COSDictionary object"));
/*    */     }
/*    */     else
/*    */     {
/* 53 */       COSDictionary actionsDict = (COSDictionary)vPath.peek();
/*    */ 
/* 55 */       aaEntryAuth = vPath.size() - vPath.getClosestTypePosition(PDPage.class) == 2;
/*    */ 
/* 57 */       PreflightConfiguration config = context.getConfig();
/* 58 */       ActionManagerFactory factory = config.getActionFact();
/* 59 */       List la = factory.getActionManagers(context, actionsDict);
/* 60 */       for (AbstractActionManager aMng : la)
/*    */       {
/* 62 */         aMng.valid(aaEntryAuth);
/*    */       }
/*    */     }
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.preflight.process.reflect.ActionsValidationProcess
 * JD-Core Version:    0.6.2
 */