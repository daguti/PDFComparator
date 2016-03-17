/*    */ package org.stringtemplate.v4.misc;
/*    */ 
/*    */ import java.io.PrintWriter;
/*    */ import java.io.StringWriter;
/*    */ import org.antlr.runtime.Token;
/*    */ import org.stringtemplate.v4.ST;
/*    */ 
/*    */ public class STMessage
/*    */ {
/*    */   public ST self;
/*    */   public ErrorType error;
/*    */   public Object arg;
/*    */   public Object arg2;
/*    */   public Object arg3;
/*    */   public Throwable cause;
/*    */ 
/*    */   public STMessage(ErrorType error)
/*    */   {
/* 54 */     this.error = error;
/*    */   }
/*    */   public STMessage(ErrorType error, ST self) {
/* 57 */     this(error);
/* 58 */     this.self = self;
/*    */   }
/*    */   public STMessage(ErrorType error, ST self, Throwable cause) {
/* 61 */     this(error, self);
/* 62 */     this.cause = cause;
/*    */   }
/*    */   public STMessage(ErrorType error, ST self, Throwable cause, Object arg) {
/* 65 */     this(error, self, cause);
/* 66 */     this.arg = arg;
/*    */   }
/*    */   public STMessage(ErrorType error, ST self, Throwable cause, Token where, Object arg) {
/* 69 */     this(error, self, cause, where);
/* 70 */     this.arg = arg;
/*    */   }
/*    */   public STMessage(ErrorType error, ST self, Throwable cause, Object arg, Object arg2) {
/* 73 */     this(error, self, cause, arg);
/* 74 */     this.arg2 = arg2;
/*    */   }
/*    */   public STMessage(ErrorType error, ST self, Throwable cause, Object arg, Object arg2, Object arg3) {
/* 77 */     this(error, self, cause, arg, arg2);
/* 78 */     this.arg3 = arg3;
/*    */   }
/*    */ 
/*    */   public String toString()
/*    */   {
/* 83 */     StringWriter sw = new StringWriter();
/* 84 */     PrintWriter pw = new PrintWriter(sw);
/* 85 */     String msg = String.format(this.error.message, new Object[] { this.arg, this.arg2, this.arg3 });
/* 86 */     pw.print(msg);
/* 87 */     if (this.cause != null) {
/* 88 */       pw.print("\nCaused by: ");
/* 89 */       this.cause.printStackTrace(pw);
/*    */     }
/* 91 */     return sw.toString();
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.stringtemplate.v4.misc.STMessage
 * JD-Core Version:    0.6.2
 */