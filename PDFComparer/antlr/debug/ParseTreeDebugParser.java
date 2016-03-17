/*     */ package antlr.debug;
/*     */ 
/*     */ import antlr.CommonToken;
/*     */ import antlr.LLkParser;
/*     */ import antlr.MismatchedTokenException;
/*     */ import antlr.ParseTree;
/*     */ import antlr.ParseTreeRule;
/*     */ import antlr.ParseTreeToken;
/*     */ import antlr.ParserSharedInputState;
/*     */ import antlr.TokenBuffer;
/*     */ import antlr.TokenStream;
/*     */ import antlr.TokenStreamException;
/*     */ import antlr.collections.impl.BitSet;
/*     */ import java.util.Stack;
/*     */ 
/*     */ public class ParseTreeDebugParser extends LLkParser
/*     */ {
/*  23 */   protected Stack currentParseTreeRoot = new Stack();
/*     */ 
/*  28 */   protected ParseTreeRule mostRecentParseTreeRoot = null;
/*     */ 
/*  31 */   protected int numberOfDerivationSteps = 1;
/*     */ 
/*     */   public ParseTreeDebugParser(int paramInt) {
/*  34 */     super(paramInt);
/*     */   }
/*     */ 
/*     */   public ParseTreeDebugParser(ParserSharedInputState paramParserSharedInputState, int paramInt) {
/*  38 */     super(paramParserSharedInputState, paramInt);
/*     */   }
/*     */ 
/*     */   public ParseTreeDebugParser(TokenBuffer paramTokenBuffer, int paramInt) {
/*  42 */     super(paramTokenBuffer, paramInt);
/*     */   }
/*     */ 
/*     */   public ParseTreeDebugParser(TokenStream paramTokenStream, int paramInt) {
/*  46 */     super(paramTokenStream, paramInt);
/*     */   }
/*     */ 
/*     */   public ParseTree getParseTree() {
/*  50 */     return this.mostRecentParseTreeRoot;
/*     */   }
/*     */ 
/*     */   public int getNumberOfDerivationSteps() {
/*  54 */     return this.numberOfDerivationSteps;
/*     */   }
/*     */ 
/*     */   public void match(int paramInt) throws MismatchedTokenException, TokenStreamException {
/*  58 */     addCurrentTokenToParseTree();
/*  59 */     super.match(paramInt);
/*     */   }
/*     */ 
/*     */   public void match(BitSet paramBitSet) throws MismatchedTokenException, TokenStreamException {
/*  63 */     addCurrentTokenToParseTree();
/*  64 */     super.match(paramBitSet);
/*     */   }
/*     */ 
/*     */   public void matchNot(int paramInt) throws MismatchedTokenException, TokenStreamException {
/*  68 */     addCurrentTokenToParseTree();
/*  69 */     super.matchNot(paramInt);
/*     */   }
/*     */ 
/*     */   protected void addCurrentTokenToParseTree()
/*     */     throws TokenStreamException
/*     */   {
/*  80 */     if (this.inputState.guessing > 0) {
/*  81 */       return;
/*     */     }
/*  83 */     ParseTreeRule localParseTreeRule = (ParseTreeRule)this.currentParseTreeRoot.peek();
/*  84 */     ParseTreeToken localParseTreeToken = null;
/*  85 */     if (LA(1) == 1) {
/*  86 */       localParseTreeToken = new ParseTreeToken(new CommonToken("EOF"));
/*     */     }
/*     */     else {
/*  89 */       localParseTreeToken = new ParseTreeToken(LT(1));
/*     */     }
/*  91 */     localParseTreeRule.addChild(localParseTreeToken);
/*     */   }
/*     */ 
/*     */   public void traceIn(String paramString) throws TokenStreamException
/*     */   {
/*  96 */     if (this.inputState.guessing > 0) {
/*  97 */       return;
/*     */     }
/*  99 */     ParseTreeRule localParseTreeRule1 = new ParseTreeRule(paramString);
/* 100 */     if (this.currentParseTreeRoot.size() > 0) {
/* 101 */       ParseTreeRule localParseTreeRule2 = (ParseTreeRule)this.currentParseTreeRoot.peek();
/* 102 */       localParseTreeRule2.addChild(localParseTreeRule1);
/*     */     }
/* 104 */     this.currentParseTreeRoot.push(localParseTreeRule1);
/* 105 */     this.numberOfDerivationSteps += 1;
/*     */   }
/*     */ 
/*     */   public void traceOut(String paramString) throws TokenStreamException
/*     */   {
/* 110 */     if (this.inputState.guessing > 0) {
/* 111 */       return;
/*     */     }
/* 113 */     this.mostRecentParseTreeRoot = ((ParseTreeRule)this.currentParseTreeRoot.pop());
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     antlr.debug.ParseTreeDebugParser
 * JD-Core Version:    0.6.2
 */