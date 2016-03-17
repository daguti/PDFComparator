/*     */ package antlr;
/*     */ 
/*     */ import antlr.collections.AST;
/*     */ import antlr.collections.impl.BitSet;
/*     */ import antlr.debug.MessageListener;
/*     */ import antlr.debug.ParserListener;
/*     */ import antlr.debug.ParserMatchListener;
/*     */ import antlr.debug.ParserTokenListener;
/*     */ import antlr.debug.SemanticPredicateListener;
/*     */ import antlr.debug.SyntacticPredicateListener;
/*     */ import antlr.debug.TraceListener;
/*     */ import java.io.PrintStream;
/*     */ import java.util.Hashtable;
/*     */ 
/*     */ public abstract class Parser
/*     */ {
/*     */   protected ParserSharedInputState inputState;
/*     */   protected String[] tokenNames;
/*     */   protected AST returnAST;
/*  74 */   protected ASTFactory astFactory = null;
/*     */ 
/*  79 */   protected Hashtable tokenTypeToASTClassMap = null;
/*     */ 
/*  81 */   private boolean ignoreInvalidDebugCalls = false;
/*     */ 
/*  84 */   protected int traceDepth = 0;
/*     */ 
/*     */   public Parser() {
/*  87 */     this(new ParserSharedInputState());
/*     */   }
/*     */ 
/*     */   public Parser(ParserSharedInputState paramParserSharedInputState) {
/*  91 */     this.inputState = paramParserSharedInputState;
/*     */   }
/*     */ 
/*     */   public Hashtable getTokenTypeToASTClassMap()
/*     */   {
/*  99 */     return this.tokenTypeToASTClassMap;
/*     */   }
/*     */ 
/*     */   public void addMessageListener(MessageListener paramMessageListener) {
/* 103 */     if (!this.ignoreInvalidDebugCalls)
/* 104 */       throw new IllegalArgumentException("addMessageListener() is only valid if parser built for debugging");
/*     */   }
/*     */ 
/*     */   public void addParserListener(ParserListener paramParserListener) {
/* 108 */     if (!this.ignoreInvalidDebugCalls)
/* 109 */       throw new IllegalArgumentException("addParserListener() is only valid if parser built for debugging");
/*     */   }
/*     */ 
/*     */   public void addParserMatchListener(ParserMatchListener paramParserMatchListener) {
/* 113 */     if (!this.ignoreInvalidDebugCalls)
/* 114 */       throw new IllegalArgumentException("addParserMatchListener() is only valid if parser built for debugging");
/*     */   }
/*     */ 
/*     */   public void addParserTokenListener(ParserTokenListener paramParserTokenListener) {
/* 118 */     if (!this.ignoreInvalidDebugCalls)
/* 119 */       throw new IllegalArgumentException("addParserTokenListener() is only valid if parser built for debugging");
/*     */   }
/*     */ 
/*     */   public void addSemanticPredicateListener(SemanticPredicateListener paramSemanticPredicateListener) {
/* 123 */     if (!this.ignoreInvalidDebugCalls)
/* 124 */       throw new IllegalArgumentException("addSemanticPredicateListener() is only valid if parser built for debugging");
/*     */   }
/*     */ 
/*     */   public void addSyntacticPredicateListener(SyntacticPredicateListener paramSyntacticPredicateListener) {
/* 128 */     if (!this.ignoreInvalidDebugCalls)
/* 129 */       throw new IllegalArgumentException("addSyntacticPredicateListener() is only valid if parser built for debugging");
/*     */   }
/*     */ 
/*     */   public void addTraceListener(TraceListener paramTraceListener) {
/* 133 */     if (!this.ignoreInvalidDebugCalls)
/* 134 */       throw new IllegalArgumentException("addTraceListener() is only valid if parser built for debugging");
/*     */   }
/*     */ 
/*     */   public abstract void consume()
/*     */     throws TokenStreamException;
/*     */ 
/*     */   public void consumeUntil(int paramInt) throws TokenStreamException
/*     */   {
/* 142 */     while ((LA(1) != 1) && (LA(1) != paramInt))
/* 143 */       consume();
/*     */   }
/*     */ 
/*     */   public void consumeUntil(BitSet paramBitSet)
/*     */     throws TokenStreamException
/*     */   {
/* 149 */     while ((LA(1) != 1) && (!paramBitSet.member(LA(1))))
/* 150 */       consume();
/*     */   }
/*     */ 
/*     */   protected void defaultDebuggingSetup(TokenStream paramTokenStream, TokenBuffer paramTokenBuffer)
/*     */   {
/*     */   }
/*     */ 
/*     */   public AST getAST()
/*     */   {
/* 160 */     return this.returnAST;
/*     */   }
/*     */ 
/*     */   public ASTFactory getASTFactory() {
/* 164 */     return this.astFactory;
/*     */   }
/*     */ 
/*     */   public String getFilename() {
/* 168 */     return this.inputState.filename;
/*     */   }
/*     */ 
/*     */   public ParserSharedInputState getInputState() {
/* 172 */     return this.inputState;
/*     */   }
/*     */ 
/*     */   public void setInputState(ParserSharedInputState paramParserSharedInputState) {
/* 176 */     this.inputState = paramParserSharedInputState;
/*     */   }
/*     */ 
/*     */   public String getTokenName(int paramInt) {
/* 180 */     return this.tokenNames[paramInt];
/*     */   }
/*     */ 
/*     */   public String[] getTokenNames() {
/* 184 */     return this.tokenNames;
/*     */   }
/*     */ 
/*     */   public boolean isDebugMode() {
/* 188 */     return false;
/*     */   }
/*     */ 
/*     */   public abstract int LA(int paramInt)
/*     */     throws TokenStreamException;
/*     */ 
/*     */   public abstract Token LT(int paramInt)
/*     */     throws TokenStreamException;
/*     */ 
/*     */   public int mark()
/*     */   {
/* 202 */     return this.inputState.input.mark();
/*     */   }
/*     */ 
/*     */   public void match(int paramInt)
/*     */     throws MismatchedTokenException, TokenStreamException
/*     */   {
/* 210 */     if (LA(1) != paramInt) {
/* 211 */       throw new MismatchedTokenException(this.tokenNames, LT(1), paramInt, false, getFilename());
/*     */     }
/*     */ 
/* 214 */     consume();
/*     */   }
/*     */ 
/*     */   public void match(BitSet paramBitSet)
/*     */     throws MismatchedTokenException, TokenStreamException
/*     */   {
/* 222 */     if (!paramBitSet.member(LA(1))) {
/* 223 */       throw new MismatchedTokenException(this.tokenNames, LT(1), paramBitSet, false, getFilename());
/*     */     }
/*     */ 
/* 226 */     consume();
/*     */   }
/*     */ 
/*     */   public void matchNot(int paramInt) throws MismatchedTokenException, TokenStreamException {
/* 230 */     if (LA(1) == paramInt)
/*     */     {
/* 232 */       throw new MismatchedTokenException(this.tokenNames, LT(1), paramInt, true, getFilename());
/*     */     }
/*     */ 
/* 235 */     consume();
/*     */   }
/*     */ 
/*     */   /** @deprecated */
/*     */   public static void panic()
/*     */   {
/* 245 */     System.err.println("Parser: panic");
/* 246 */     System.exit(1);
/*     */   }
/*     */ 
/*     */   public void removeMessageListener(MessageListener paramMessageListener) {
/* 250 */     if (!this.ignoreInvalidDebugCalls)
/* 251 */       throw new RuntimeException("removeMessageListener() is only valid if parser built for debugging");
/*     */   }
/*     */ 
/*     */   public void removeParserListener(ParserListener paramParserListener) {
/* 255 */     if (!this.ignoreInvalidDebugCalls)
/* 256 */       throw new RuntimeException("removeParserListener() is only valid if parser built for debugging");
/*     */   }
/*     */ 
/*     */   public void removeParserMatchListener(ParserMatchListener paramParserMatchListener) {
/* 260 */     if (!this.ignoreInvalidDebugCalls)
/* 261 */       throw new RuntimeException("removeParserMatchListener() is only valid if parser built for debugging");
/*     */   }
/*     */ 
/*     */   public void removeParserTokenListener(ParserTokenListener paramParserTokenListener) {
/* 265 */     if (!this.ignoreInvalidDebugCalls)
/* 266 */       throw new RuntimeException("removeParserTokenListener() is only valid if parser built for debugging");
/*     */   }
/*     */ 
/*     */   public void removeSemanticPredicateListener(SemanticPredicateListener paramSemanticPredicateListener) {
/* 270 */     if (!this.ignoreInvalidDebugCalls)
/* 271 */       throw new IllegalArgumentException("removeSemanticPredicateListener() is only valid if parser built for debugging");
/*     */   }
/*     */ 
/*     */   public void removeSyntacticPredicateListener(SyntacticPredicateListener paramSyntacticPredicateListener) {
/* 275 */     if (!this.ignoreInvalidDebugCalls)
/* 276 */       throw new IllegalArgumentException("removeSyntacticPredicateListener() is only valid if parser built for debugging");
/*     */   }
/*     */ 
/*     */   public void removeTraceListener(TraceListener paramTraceListener) {
/* 280 */     if (!this.ignoreInvalidDebugCalls)
/* 281 */       throw new RuntimeException("removeTraceListener() is only valid if parser built for debugging");
/*     */   }
/*     */ 
/*     */   public void reportError(RecognitionException paramRecognitionException)
/*     */   {
/* 286 */     System.err.println(paramRecognitionException);
/*     */   }
/*     */ 
/*     */   public void reportError(String paramString)
/*     */   {
/* 291 */     if (getFilename() == null) {
/* 292 */       System.err.println("error: " + paramString);
/*     */     }
/*     */     else
/* 295 */       System.err.println(getFilename() + ": error: " + paramString);
/*     */   }
/*     */ 
/*     */   public void reportWarning(String paramString)
/*     */   {
/* 301 */     if (getFilename() == null) {
/* 302 */       System.err.println("warning: " + paramString);
/*     */     }
/*     */     else
/* 305 */       System.err.println(getFilename() + ": warning: " + paramString);
/*     */   }
/*     */ 
/*     */   public void recover(RecognitionException paramRecognitionException, BitSet paramBitSet)
/*     */     throws TokenStreamException
/*     */   {
/* 311 */     consume();
/* 312 */     consumeUntil(paramBitSet);
/*     */   }
/*     */ 
/*     */   public void rewind(int paramInt) {
/* 316 */     this.inputState.input.rewind(paramInt);
/*     */   }
/*     */ 
/*     */   public void setASTFactory(ASTFactory paramASTFactory)
/*     */   {
/* 324 */     this.astFactory = paramASTFactory;
/*     */   }
/*     */ 
/*     */   public void setASTNodeClass(String paramString) {
/* 328 */     this.astFactory.setASTNodeType(paramString);
/*     */   }
/*     */ 
/*     */   /** @deprecated */
/*     */   public void setASTNodeType(String paramString)
/*     */   {
/* 336 */     setASTNodeClass(paramString);
/*     */   }
/*     */ 
/*     */   public void setDebugMode(boolean paramBoolean) {
/* 340 */     if (!this.ignoreInvalidDebugCalls)
/* 341 */       throw new RuntimeException("setDebugMode() only valid if parser built for debugging");
/*     */   }
/*     */ 
/*     */   public void setFilename(String paramString) {
/* 345 */     this.inputState.filename = paramString;
/*     */   }
/*     */ 
/*     */   public void setIgnoreInvalidDebugCalls(boolean paramBoolean) {
/* 349 */     this.ignoreInvalidDebugCalls = paramBoolean;
/*     */   }
/*     */ 
/*     */   public void setTokenBuffer(TokenBuffer paramTokenBuffer)
/*     */   {
/* 354 */     this.inputState.input = paramTokenBuffer;
/*     */   }
/*     */ 
/*     */   public void traceIndent() {
/* 358 */     for (int i = 0; i < this.traceDepth; i++)
/* 359 */       System.out.print(" ");
/*     */   }
/*     */ 
/*     */   public void traceIn(String paramString) throws TokenStreamException {
/* 363 */     this.traceDepth += 1;
/* 364 */     traceIndent();
/* 365 */     System.out.println("> " + paramString + "; LA(1)==" + LT(1).getText() + (this.inputState.guessing > 0 ? " [guessing]" : ""));
/*     */   }
/*     */ 
/*     */   public void traceOut(String paramString) throws TokenStreamException
/*     */   {
/* 370 */     traceIndent();
/* 371 */     System.out.println("< " + paramString + "; LA(1)==" + LT(1).getText() + (this.inputState.guessing > 0 ? " [guessing]" : ""));
/*     */ 
/* 373 */     this.traceDepth -= 1;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     antlr.Parser
 * JD-Core Version:    0.6.2
 */