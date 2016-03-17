/*    */ package org.apache.pdfbox.preflight.exception;
/*    */ 
/*    */ public class MissingValidationProcessException extends ValidationException
/*    */ {
/*    */   private String processName;
/*    */ 
/*    */   public MissingValidationProcessException(String process)
/*    */   {
/* 34 */     super(process + " is missing, validation can't be done");
/* 35 */     this.processName = process;
/*    */   }
/*    */ 
/*    */   public String getProcessName()
/*    */   {
/* 40 */     return this.processName;
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.preflight.exception.MissingValidationProcessException
 * JD-Core Version:    0.6.2
 */