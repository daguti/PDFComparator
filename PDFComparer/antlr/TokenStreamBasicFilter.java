/*    */ package antlr;
/*    */ 
/*    */ import antlr.ASdebug.ASDebugStream;
/*    */ import antlr.ASdebug.IASDebugStream;
/*    */ import antlr.ASdebug.TokenOffsetInfo;
/*    */ import antlr.collections.impl.BitSet;
/*    */ 
/*    */ public class TokenStreamBasicFilter
/*    */   implements TokenStream, IASDebugStream
/*    */ {
/*    */   protected BitSet discardMask;
/*    */   protected TokenStream input;
/*    */ 
/*    */   public TokenStreamBasicFilter(TokenStream paramTokenStream)
/*    */   {
/* 27 */     this.input = paramTokenStream;
/* 28 */     this.discardMask = new BitSet();
/*    */   }
/*    */ 
/*    */   public void discard(int paramInt) {
/* 32 */     this.discardMask.add(paramInt);
/*    */   }
/*    */ 
/*    */   public void discard(BitSet paramBitSet) {
/* 36 */     this.discardMask = paramBitSet;
/*    */   }
/*    */ 
/*    */   public Token nextToken() throws TokenStreamException {
/* 40 */     Token localToken = this.input.nextToken();
/* 41 */     while ((localToken != null) && (this.discardMask.member(localToken.getType()))) {
/* 42 */       localToken = this.input.nextToken();
/*    */     }
/* 44 */     return localToken;
/*    */   }
/*    */ 
/*    */   public String getEntireText()
/*    */   {
/* 49 */     return ASDebugStream.getEntireText(this.input);
/*    */   }
/*    */ 
/*    */   public TokenOffsetInfo getOffsetInfo(Token paramToken)
/*    */   {
/* 54 */     return ASDebugStream.getOffsetInfo(this.input, paramToken);
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     antlr.TokenStreamBasicFilter
 * JD-Core Version:    0.6.2
 */