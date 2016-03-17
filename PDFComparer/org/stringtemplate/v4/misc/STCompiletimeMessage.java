/*    */ package org.stringtemplate.v4.misc;
/*    */ 
/*    */ import org.antlr.runtime.RecognitionException;
/*    */ import org.antlr.runtime.Token;
/*    */ 
/*    */ public class STCompiletimeMessage extends STMessage
/*    */ {
/*    */   Token templateToken;
/*    */   Token token;
/*    */   String srcName;
/*    */ 
/*    */   public STCompiletimeMessage(ErrorType error, String srcName, Token templateToken, Token t)
/*    */   {
/* 43 */     this(error, srcName, templateToken, t, null);
/*    */   }
/*    */   public STCompiletimeMessage(ErrorType error, String srcName, Token templateToken, Token t, Throwable cause) {
/* 46 */     this(error, srcName, templateToken, t, cause, null);
/*    */   }
/*    */ 
/*    */   public STCompiletimeMessage(ErrorType error, String srcName, Token templateToken, Token t, Throwable cause, Object arg)
/*    */   {
/* 51 */     this(error, srcName, templateToken, t, cause, arg, null);
/*    */   }
/*    */ 
/*    */   public STCompiletimeMessage(ErrorType error, String srcName, Token templateToken, Token t, Throwable cause, Object arg, Object arg2)
/*    */   {
/* 56 */     super(error, null, cause, arg, arg2);
/* 57 */     this.templateToken = templateToken;
/* 58 */     this.token = t;
/* 59 */     this.srcName = srcName;
/*    */   }
/*    */ 
/*    */   public String toString() {
/* 63 */     RecognitionException re = (RecognitionException)this.cause;
/* 64 */     int line = 0;
/* 65 */     int charPos = -1;
/* 66 */     if (this.token != null) {
/* 67 */       line = this.token.getLine();
/* 68 */       charPos = this.token.getCharPositionInLine();
/* 69 */       if (this.templateToken != null) {
/* 70 */         int templateDelimiterSize = 1;
/* 71 */         if (this.templateToken.getType() == 5) {
/* 72 */           templateDelimiterSize = 2;
/*    */         }
/* 74 */         line += this.templateToken.getLine() - 1;
/* 75 */         charPos += this.templateToken.getCharPositionInLine() + templateDelimiterSize;
/*    */       }
/*    */     }
/* 78 */     String filepos = line + ":" + charPos;
/* 79 */     if (this.srcName != null) {
/* 80 */       return this.srcName + " " + filepos + ": " + String.format(this.error.message, new Object[] { this.arg, this.arg2 });
/*    */     }
/* 82 */     return filepos + ": " + String.format(this.error.message, new Object[] { this.arg, this.arg2 });
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.stringtemplate.v4.misc.STCompiletimeMessage
 * JD-Core Version:    0.6.2
 */