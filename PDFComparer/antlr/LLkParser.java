/*    */ package antlr;
/*    */ 
/*    */ import java.io.PrintStream;
/*    */ 
/*    */ public class LLkParser extends Parser
/*    */ {
/*    */   int k;
/*    */ 
/*    */   public LLkParser(int paramInt)
/*    */   {
/* 21 */     this.k = paramInt;
/*    */   }
/*    */ 
/*    */   public LLkParser(ParserSharedInputState paramParserSharedInputState, int paramInt) {
/* 25 */     super(paramParserSharedInputState);
/* 26 */     this.k = paramInt;
/*    */   }
/*    */ 
/*    */   public LLkParser(TokenBuffer paramTokenBuffer, int paramInt) {
/* 30 */     this.k = paramInt;
/* 31 */     setTokenBuffer(paramTokenBuffer);
/*    */   }
/*    */ 
/*    */   public LLkParser(TokenStream paramTokenStream, int paramInt) {
/* 35 */     this.k = paramInt;
/* 36 */     TokenBuffer localTokenBuffer = new TokenBuffer(paramTokenStream);
/* 37 */     setTokenBuffer(localTokenBuffer);
/*    */   }
/*    */ 
/*    */   public void consume()
/*    */     throws TokenStreamException
/*    */   {
/* 48 */     this.inputState.input.consume();
/*    */   }
/*    */ 
/*    */   public int LA(int paramInt) throws TokenStreamException {
/* 52 */     return this.inputState.input.LA(paramInt);
/*    */   }
/*    */ 
/*    */   public Token LT(int paramInt) throws TokenStreamException {
/* 56 */     return this.inputState.input.LT(paramInt);
/*    */   }
/*    */ 
/*    */   private void trace(String paramString1, String paramString2) throws TokenStreamException {
/* 60 */     traceIndent();
/* 61 */     System.out.print(paramString1 + paramString2 + (this.inputState.guessing > 0 ? "; [guessing]" : "; "));
/* 62 */     for (int i = 1; i <= this.k; i++) {
/* 63 */       if (i != 1) {
/* 64 */         System.out.print(", ");
/*    */       }
/* 66 */       if (LT(i) != null) {
/* 67 */         System.out.print("LA(" + i + ")==" + LT(i).getText());
/*    */       }
/*    */       else {
/* 70 */         System.out.print("LA(" + i + ")==null");
/*    */       }
/*    */     }
/* 73 */     System.out.println("");
/*    */   }
/*    */ 
/*    */   public void traceIn(String paramString) throws TokenStreamException {
/* 77 */     this.traceDepth += 1;
/* 78 */     trace("> ", paramString);
/*    */   }
/*    */ 
/*    */   public void traceOut(String paramString) throws TokenStreamException {
/* 82 */     trace("< ", paramString);
/* 83 */     this.traceDepth -= 1;
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     antlr.LLkParser
 * JD-Core Version:    0.6.2
 */