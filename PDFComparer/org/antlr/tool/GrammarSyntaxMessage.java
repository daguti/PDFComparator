/*    */ package org.antlr.tool;
/*    */ 
/*    */ import antlr.RecognitionException;
/*    */ import antlr.Token;
/*    */ import org.antlr.stringtemplate.StringTemplate;
/*    */ 
/*    */ public class GrammarSyntaxMessage extends Message
/*    */ {
/*    */   public Grammar g;
/*    */   public Token offendingToken;
/*    */   public RecognitionException exception;
/*    */ 
/*    */   public GrammarSyntaxMessage(int msgID, Grammar grammar, Token offendingToken, RecognitionException exception)
/*    */   {
/* 47 */     this(msgID, grammar, offendingToken, null, exception);
/*    */   }
/*    */ 
/*    */   public GrammarSyntaxMessage(int msgID, Grammar grammar, Token offendingToken, Object arg, RecognitionException exception)
/*    */   {
/* 56 */     super(msgID, arg, null);
/* 57 */     this.offendingToken = offendingToken;
/* 58 */     this.exception = exception;
/* 59 */     this.g = grammar;
/*    */   }
/*    */ 
/*    */   public String toString() {
/* 63 */     this.line = 0;
/* 64 */     this.column = 0;
/* 65 */     if (this.offendingToken != null) {
/* 66 */       this.line = this.offendingToken.getLine();
/* 67 */       this.column = this.offendingToken.getColumn();
/*    */     }
/*    */ 
/* 71 */     if (this.g != null) {
/* 72 */       this.file = this.g.getFileName();
/*    */     }
/* 74 */     StringTemplate st = getMessageTemplate();
/* 75 */     if (this.arg != null) {
/* 76 */       st.setAttribute("arg", this.arg);
/*    */     }
/* 78 */     return super.toString(st);
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.antlr.tool.GrammarSyntaxMessage
 * JD-Core Version:    0.6.2
 */