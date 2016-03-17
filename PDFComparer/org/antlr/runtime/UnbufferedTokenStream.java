/*    */ package org.antlr.runtime;
/*    */ 
/*    */ import org.antlr.runtime.misc.LookaheadStream;
/*    */ 
/*    */ public class UnbufferedTokenStream extends LookaheadStream<Token>
/*    */   implements TokenStream
/*    */ {
/*    */   protected TokenSource tokenSource;
/* 52 */   protected int tokenIndex = 0;
/*    */ 
/* 55 */   protected int channel = 0;
/*    */ 
/*    */   public UnbufferedTokenStream(TokenSource tokenSource) {
/* 58 */     this.tokenSource = tokenSource;
/*    */   }
/*    */ 
/*    */   public Token nextElement() {
/* 62 */     Token t = this.tokenSource.nextToken();
/* 63 */     t.setTokenIndex(this.tokenIndex++);
/* 64 */     return t;
/*    */   }
/*    */   public boolean isEOF(Token o) {
/* 67 */     return o.getType() == -1;
/*    */   }
/* 69 */   public TokenSource getTokenSource() { return this.tokenSource; } 
/*    */   public String toString(int start, int stop) {
/* 71 */     return "n/a";
/*    */   }
/* 73 */   public String toString(Token start, Token stop) { return "n/a"; } 
/*    */   public int LA(int i) {
/* 75 */     return ((Token)LT(i)).getType();
/*    */   }
/*    */   public Token get(int i) {
/* 78 */     throw new UnsupportedOperationException("Absolute token indexes are meaningless in an unbuffered stream");
/*    */   }
/*    */   public String getSourceName() {
/* 81 */     return this.tokenSource.getSourceName();
/*    */   }
/*    */ 
/*    */   public boolean isEOF(Object x0)
/*    */   {
/* 50 */     return isEOF((Token)x0); } 
/* 50 */   public Object nextElement() { return nextElement(); } 
/* 50 */   public Token LT(int x0) { return (Token)super.LT(x0); }
/*    */ 
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.antlr.runtime.UnbufferedTokenStream
 * JD-Core Version:    0.6.2
 */