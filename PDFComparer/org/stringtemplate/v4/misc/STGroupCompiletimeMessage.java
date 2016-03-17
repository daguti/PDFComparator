/*    */ package org.stringtemplate.v4.misc;
/*    */ 
/*    */ import org.antlr.runtime.RecognitionException;
/*    */ import org.antlr.runtime.Token;
/*    */ 
/*    */ public class STGroupCompiletimeMessage extends STMessage
/*    */ {
/*    */   Token token;
/*    */   String srcName;
/*    */ 
/*    */   public STGroupCompiletimeMessage(ErrorType error, String srcName, Token t, Throwable cause)
/*    */   {
/* 40 */     this(error, srcName, t, cause, null);
/*    */   }
/*    */ 
/*    */   public STGroupCompiletimeMessage(ErrorType error, String srcName, Token t, Throwable cause, Object arg)
/*    */   {
/* 45 */     this(error, srcName, t, cause, arg, null);
/*    */   }
/*    */ 
/*    */   public STGroupCompiletimeMessage(ErrorType error, String srcName, Token t, Throwable cause, Object arg, Object arg2)
/*    */   {
/* 50 */     super(error, null, cause, arg, arg2);
/* 51 */     this.token = t;
/* 52 */     this.srcName = srcName;
/*    */   }
/*    */ 
/*    */   public String toString() {
/* 56 */     RecognitionException re = (RecognitionException)this.cause;
/* 57 */     int line = 0;
/* 58 */     int charPos = -1;
/* 59 */     if (this.token != null) {
/* 60 */       line = this.token.getLine();
/* 61 */       charPos = this.token.getCharPositionInLine();
/*    */     }
/* 63 */     else if (re != null) {
/* 64 */       line = re.line;
/* 65 */       charPos = re.charPositionInLine;
/*    */     }
/* 67 */     String filepos = line + ":" + charPos;
/* 68 */     if (this.srcName != null) {
/* 69 */       return this.srcName + " " + filepos + ": " + String.format(this.error.message, new Object[] { this.arg, this.arg2 });
/*    */     }
/* 71 */     return filepos + ": " + String.format(this.error.message, new Object[] { this.arg, this.arg2 });
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.stringtemplate.v4.misc.STGroupCompiletimeMessage
 * JD-Core Version:    0.6.2
 */