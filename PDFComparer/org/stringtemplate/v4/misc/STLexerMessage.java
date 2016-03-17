/*    */ package org.stringtemplate.v4.misc;
/*    */ 
/*    */ import org.antlr.runtime.RecognitionException;
/*    */ import org.antlr.runtime.Token;
/*    */ 
/*    */ public class STLexerMessage extends STMessage
/*    */ {
/*    */   String msg;
/*    */   Token templateToken;
/*    */   String srcName;
/*    */ 
/*    */   public STLexerMessage(String srcName, String msg, Token templateToken, Throwable cause)
/*    */   {
/* 42 */     super(ErrorType.LEXER_ERROR, null, cause, null);
/* 43 */     this.msg = msg;
/* 44 */     this.templateToken = templateToken;
/* 45 */     this.srcName = srcName;
/*    */   }
/*    */ 
/*    */   public String toString() {
/* 49 */     RecognitionException re = (RecognitionException)this.cause;
/* 50 */     int line = re.line;
/* 51 */     int charPos = re.charPositionInLine;
/* 52 */     if (this.templateToken != null) {
/* 53 */       int templateDelimiterSize = 1;
/* 54 */       if (this.templateToken.getType() == 5) {
/* 55 */         templateDelimiterSize = 2;
/*    */       }
/* 57 */       line += this.templateToken.getLine() - 1;
/* 58 */       charPos += this.templateToken.getCharPositionInLine() + templateDelimiterSize;
/*    */     }
/* 60 */     String filepos = line + ":" + charPos;
/* 61 */     if (this.srcName != null) {
/* 62 */       return this.srcName + " " + filepos + ": " + String.format(this.error.message, new Object[] { this.msg });
/*    */     }
/* 64 */     return filepos + ": " + String.format(this.error.message, new Object[] { this.msg });
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.stringtemplate.v4.misc.STLexerMessage
 * JD-Core Version:    0.6.2
 */