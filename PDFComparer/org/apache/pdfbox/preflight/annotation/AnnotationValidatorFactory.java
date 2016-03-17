/*    */ package org.apache.pdfbox.preflight.annotation;
/*    */ 
/*    */ import java.lang.reflect.Constructor;
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ import org.apache.pdfbox.cos.COSDictionary;
/*    */ import org.apache.pdfbox.cos.COSName;
/*    */ import org.apache.pdfbox.preflight.PreflightContext;
/*    */ import org.apache.pdfbox.preflight.ValidationResult.ValidationError;
/*    */ import org.apache.pdfbox.preflight.action.ActionManagerFactory;
/*    */ import org.apache.pdfbox.preflight.exception.ValidationException;
/*    */ 
/*    */ public abstract class AnnotationValidatorFactory
/*    */ {
/* 39 */   protected ActionManagerFactory actionFact = null;
/*    */ 
/* 41 */   protected Map<String, Class<? extends AnnotationValidator>> validatorClasses = new HashMap();
/*    */ 
/*    */   public AnnotationValidatorFactory()
/*    */   {
/* 45 */     initializeClasses();
/*    */   }
/*    */ 
/*    */   public AnnotationValidatorFactory(ActionManagerFactory actionFact)
/*    */   {
/* 51 */     this.actionFact = actionFact;
/*    */   }
/*    */ 
/*    */   public final void setActionFact(ActionManagerFactory _actionFact)
/*    */   {
/* 56 */     this.actionFact = _actionFact;
/*    */   }
/*    */ 
/*    */   protected abstract void initializeClasses();
/*    */ 
/*    */   public final AnnotationValidator getAnnotationValidator(PreflightContext ctx, COSDictionary annotDic)
/*    */     throws ValidationException
/*    */   {
/* 75 */     AnnotationValidator result = null;
/* 76 */     String subtype = annotDic.getNameAsString(COSName.SUBTYPE);
/* 77 */     Class clazz = (Class)this.validatorClasses.get(subtype);
/*    */ 
/* 79 */     if (clazz == null)
/*    */     {
/* 81 */       ctx.addValidationError(new ValidationResult.ValidationError("5.2.1", "The subtype isn't authorized : " + subtype));
/*    */     }
/*    */     else
/*    */     {
/*    */       try
/*    */       {
/* 88 */         Constructor constructor = clazz.getConstructor(new Class[] { PreflightContext.class, COSDictionary.class });
/*    */ 
/* 90 */         result = (AnnotationValidator)constructor.newInstance(new Object[] { ctx, annotDic });
/* 91 */         result.setFactory(this);
/*    */       }
/*    */       catch (Exception e)
/*    */       {
/* 95 */         throw new ValidationException(e.getMessage());
/*    */       }
/*    */     }
/* 98 */     return result;
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.preflight.annotation.AnnotationValidatorFactory
 * JD-Core Version:    0.6.2
 */