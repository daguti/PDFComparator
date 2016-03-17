/*    */ package org.antlr.tool;
/*    */ 
/*    */ import antlr.Token;
/*    */ import org.antlr.stringtemplate.StringTemplate;
/*    */ 
/*    */ public class GrammarSemanticsMessage extends Message
/*    */ {
/*    */   public Grammar g;
/*    */   public Token offendingToken;
/*    */ 
/*    */   public GrammarSemanticsMessage(int msgID, Grammar g, Token offendingToken)
/*    */   {
/* 47 */     this(msgID, g, offendingToken, null, null);
/*    */   }
/*    */ 
/*    */   public GrammarSemanticsMessage(int msgID, Grammar g, Token offendingToken, Object arg)
/*    */   {
/* 55 */     this(msgID, g, offendingToken, arg, null);
/*    */   }
/*    */ 
/*    */   public GrammarSemanticsMessage(int msgID, Grammar g, Token offendingToken, Object arg, Object arg2)
/*    */   {
/* 64 */     super(msgID, arg, arg2);
/* 65 */     this.g = g;
/* 66 */     this.offendingToken = offendingToken;
/*    */   }
/*    */ 
/*    */   public String toString() {
/* 70 */     this.line = 0;
/* 71 */     this.column = 0;
/* 72 */     if (this.offendingToken != null) {
/* 73 */       this.line = this.offendingToken.getLine();
/* 74 */       this.column = this.offendingToken.getColumn();
/*    */     }
/* 76 */     if (this.g != null) {
/* 77 */       this.file = this.g.getFileName();
/*    */     }
/* 79 */     StringTemplate st = getMessageTemplate();
/* 80 */     if (this.arg != null) {
/* 81 */       st.setAttribute("arg", this.arg);
/*    */     }
/* 83 */     if (this.arg2 != null) {
/* 84 */       st.setAttribute("arg2", this.arg2);
/*    */     }
/* 86 */     return super.toString(st);
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.antlr.tool.GrammarSemanticsMessage
 * JD-Core Version:    0.6.2
 */