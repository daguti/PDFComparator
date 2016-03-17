/*    */ package org.apache.pdfbox.preflight.xobject;
/*    */ 
/*    */ import org.apache.pdfbox.cos.COSStream;
/*    */ import org.apache.pdfbox.preflight.PreflightContext;
/*    */ import org.apache.pdfbox.preflight.exception.ValidationException;
/*    */ 
/*    */ public class XObjPostscriptValidator extends AbstractXObjValidator
/*    */ {
/*    */   public XObjPostscriptValidator(PreflightContext context, COSStream xobj)
/*    */   {
/* 33 */     super(context, xobj);
/*    */   }
/*    */ 
/*    */   public void validate()
/*    */     throws ValidationException
/*    */   {
/* 44 */     super.validate();
/*    */   }
/*    */ 
/*    */   protected void checkMandatoryFields()
/*    */   {
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.preflight.xobject.XObjPostscriptValidator
 * JD-Core Version:    0.6.2
 */