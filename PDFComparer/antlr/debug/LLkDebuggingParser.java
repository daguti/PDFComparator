/*     */ package antlr.debug;
/*     */ 
/*     */ import antlr.LLkParser;
/*     */ import antlr.MismatchedTokenException;
/*     */ import antlr.ParserSharedInputState;
/*     */ import antlr.RecognitionException;
/*     */ import antlr.Token;
/*     */ import antlr.TokenBuffer;
/*     */ import antlr.TokenStream;
/*     */ import antlr.TokenStreamException;
/*     */ import antlr.Utils;
/*     */ import antlr.collections.impl.BitSet;
/*     */ import java.io.PrintStream;
/*     */ import java.lang.reflect.Constructor;
/*     */ 
/*     */ public class LLkDebuggingParser extends LLkParser
/*     */   implements DebuggingParser
/*     */ {
/*  13 */   protected ParserEventSupport parserEventSupport = new ParserEventSupport(this);
/*     */ 
/*  15 */   private boolean _notDebugMode = false;
/*     */   protected String[] ruleNames;
/*     */   protected String[] semPredNames;
/*     */ 
/*     */   public LLkDebuggingParser(int paramInt)
/*     */   {
/*  21 */     super(paramInt);
/*     */   }
/*     */   public LLkDebuggingParser(ParserSharedInputState paramParserSharedInputState, int paramInt) {
/*  24 */     super(paramParserSharedInputState, paramInt);
/*     */   }
/*     */   public LLkDebuggingParser(TokenBuffer paramTokenBuffer, int paramInt) {
/*  27 */     super(paramTokenBuffer, paramInt);
/*     */   }
/*     */   public LLkDebuggingParser(TokenStream paramTokenStream, int paramInt) {
/*  30 */     super(paramTokenStream, paramInt);
/*     */   }
/*     */   public void addMessageListener(MessageListener paramMessageListener) {
/*  33 */     this.parserEventSupport.addMessageListener(paramMessageListener);
/*     */   }
/*     */   public void addParserListener(ParserListener paramParserListener) {
/*  36 */     this.parserEventSupport.addParserListener(paramParserListener);
/*     */   }
/*     */   public void addParserMatchListener(ParserMatchListener paramParserMatchListener) {
/*  39 */     this.parserEventSupport.addParserMatchListener(paramParserMatchListener);
/*     */   }
/*     */   public void addParserTokenListener(ParserTokenListener paramParserTokenListener) {
/*  42 */     this.parserEventSupport.addParserTokenListener(paramParserTokenListener);
/*     */   }
/*     */   public void addSemanticPredicateListener(SemanticPredicateListener paramSemanticPredicateListener) {
/*  45 */     this.parserEventSupport.addSemanticPredicateListener(paramSemanticPredicateListener);
/*     */   }
/*     */   public void addSyntacticPredicateListener(SyntacticPredicateListener paramSyntacticPredicateListener) {
/*  48 */     this.parserEventSupport.addSyntacticPredicateListener(paramSyntacticPredicateListener);
/*     */   }
/*     */   public void addTraceListener(TraceListener paramTraceListener) {
/*  51 */     this.parserEventSupport.addTraceListener(paramTraceListener);
/*     */   }
/*     */ 
/*     */   public void consume() throws TokenStreamException {
/*  55 */     int i = -99;
/*  56 */     i = LA(1);
/*  57 */     super.consume();
/*  58 */     this.parserEventSupport.fireConsume(i);
/*     */   }
/*     */   protected void fireEnterRule(int paramInt1, int paramInt2) {
/*  61 */     if (isDebugMode())
/*  62 */       this.parserEventSupport.fireEnterRule(paramInt1, this.inputState.guessing, paramInt2); 
/*     */   }
/*     */ 
/*  65 */   protected void fireExitRule(int paramInt1, int paramInt2) { if (isDebugMode())
/*  66 */       this.parserEventSupport.fireExitRule(paramInt1, this.inputState.guessing, paramInt2); }
/*     */ 
/*     */   protected boolean fireSemanticPredicateEvaluated(int paramInt1, int paramInt2, boolean paramBoolean) {
/*  69 */     if (isDebugMode()) {
/*  70 */       return this.parserEventSupport.fireSemanticPredicateEvaluated(paramInt1, paramInt2, paramBoolean, this.inputState.guessing);
/*     */     }
/*  72 */     return paramBoolean;
/*     */   }
/*     */   protected void fireSyntacticPredicateFailed() {
/*  75 */     if (isDebugMode())
/*  76 */       this.parserEventSupport.fireSyntacticPredicateFailed(this.inputState.guessing); 
/*     */   }
/*     */ 
/*  79 */   protected void fireSyntacticPredicateStarted() { if (isDebugMode())
/*  80 */       this.parserEventSupport.fireSyntacticPredicateStarted(this.inputState.guessing); }
/*     */ 
/*     */   protected void fireSyntacticPredicateSucceeded() {
/*  83 */     if (isDebugMode())
/*  84 */       this.parserEventSupport.fireSyntacticPredicateSucceeded(this.inputState.guessing); 
/*     */   }
/*     */ 
/*  87 */   public String getRuleName(int paramInt) { return this.ruleNames[paramInt]; }
/*     */ 
/*     */   public String getSemPredName(int paramInt) {
/*  90 */     return this.semPredNames[paramInt];
/*     */   }
/*     */   public synchronized void goToSleep() {
/*     */     try { wait(); } catch (InterruptedException localInterruptedException) {
/*     */     }
/*     */   }
/*     */ 
/*  97 */   public boolean isDebugMode() { return !this._notDebugMode; }
/*     */ 
/*     */   public boolean isGuessing() {
/* 100 */     return this.inputState.guessing > 0;
/*     */   }
/*     */ 
/*     */   public int LA(int paramInt)
/*     */     throws TokenStreamException
/*     */   {
/* 107 */     int i = super.LA(paramInt);
/* 108 */     this.parserEventSupport.fireLA(paramInt, i);
/* 109 */     return i;
/*     */   }
/*     */ 
/*     */   public void match(int paramInt)
/*     */     throws MismatchedTokenException, TokenStreamException
/*     */   {
/* 116 */     String str = LT(1).getText();
/* 117 */     int i = LA(1);
/*     */     try {
/* 119 */       super.match(paramInt);
/* 120 */       this.parserEventSupport.fireMatch(paramInt, str, this.inputState.guessing);
/*     */     }
/*     */     catch (MismatchedTokenException localMismatchedTokenException) {
/* 123 */       if (this.inputState.guessing == 0)
/* 124 */         this.parserEventSupport.fireMismatch(i, paramInt, str, this.inputState.guessing);
/* 125 */       throw localMismatchedTokenException;
/*     */     }
/*     */   }
/*     */ 
/*     */   public void match(BitSet paramBitSet)
/*     */     throws MismatchedTokenException, TokenStreamException
/*     */   {
/* 133 */     String str = LT(1).getText();
/* 134 */     int i = LA(1);
/*     */     try {
/* 136 */       super.match(paramBitSet);
/* 137 */       this.parserEventSupport.fireMatch(i, paramBitSet, str, this.inputState.guessing);
/*     */     }
/*     */     catch (MismatchedTokenException localMismatchedTokenException) {
/* 140 */       if (this.inputState.guessing == 0)
/* 141 */         this.parserEventSupport.fireMismatch(i, paramBitSet, str, this.inputState.guessing);
/* 142 */       throw localMismatchedTokenException;
/*     */     }
/*     */   }
/*     */ 
/* 146 */   public void matchNot(int paramInt) throws MismatchedTokenException, TokenStreamException { String str = LT(1).getText();
/* 147 */     int i = LA(1);
/*     */     try {
/* 149 */       super.matchNot(paramInt);
/* 150 */       this.parserEventSupport.fireMatchNot(i, paramInt, str, this.inputState.guessing);
/*     */     }
/*     */     catch (MismatchedTokenException localMismatchedTokenException) {
/* 153 */       if (this.inputState.guessing == 0)
/* 154 */         this.parserEventSupport.fireMismatchNot(i, paramInt, str, this.inputState.guessing);
/* 155 */       throw localMismatchedTokenException;
/*     */     } }
/*     */ 
/*     */   public void removeMessageListener(MessageListener paramMessageListener) {
/* 159 */     this.parserEventSupport.removeMessageListener(paramMessageListener);
/*     */   }
/*     */   public void removeParserListener(ParserListener paramParserListener) {
/* 162 */     this.parserEventSupport.removeParserListener(paramParserListener);
/*     */   }
/*     */   public void removeParserMatchListener(ParserMatchListener paramParserMatchListener) {
/* 165 */     this.parserEventSupport.removeParserMatchListener(paramParserMatchListener);
/*     */   }
/*     */   public void removeParserTokenListener(ParserTokenListener paramParserTokenListener) {
/* 168 */     this.parserEventSupport.removeParserTokenListener(paramParserTokenListener);
/*     */   }
/*     */   public void removeSemanticPredicateListener(SemanticPredicateListener paramSemanticPredicateListener) {
/* 171 */     this.parserEventSupport.removeSemanticPredicateListener(paramSemanticPredicateListener);
/*     */   }
/*     */   public void removeSyntacticPredicateListener(SyntacticPredicateListener paramSyntacticPredicateListener) {
/* 174 */     this.parserEventSupport.removeSyntacticPredicateListener(paramSyntacticPredicateListener);
/*     */   }
/*     */   public void removeTraceListener(TraceListener paramTraceListener) {
/* 177 */     this.parserEventSupport.removeTraceListener(paramTraceListener);
/*     */   }
/*     */ 
/*     */   public void reportError(RecognitionException paramRecognitionException) {
/* 181 */     this.parserEventSupport.fireReportError(paramRecognitionException);
/* 182 */     super.reportError(paramRecognitionException);
/*     */   }
/*     */ 
/*     */   public void reportError(String paramString) {
/* 186 */     this.parserEventSupport.fireReportError(paramString);
/* 187 */     super.reportError(paramString);
/*     */   }
/*     */ 
/*     */   public void reportWarning(String paramString) {
/* 191 */     this.parserEventSupport.fireReportWarning(paramString);
/* 192 */     super.reportWarning(paramString);
/*     */   }
/*     */   public void setDebugMode(boolean paramBoolean) {
/* 195 */     this._notDebugMode = (!paramBoolean);
/*     */   }
/*     */   public void setupDebugging(TokenBuffer paramTokenBuffer) {
/* 198 */     setupDebugging(null, paramTokenBuffer);
/*     */   }
/*     */   public void setupDebugging(TokenStream paramTokenStream) {
/* 201 */     setupDebugging(paramTokenStream, null);
/*     */   }
/*     */ 
/*     */   protected void setupDebugging(TokenStream paramTokenStream, TokenBuffer paramTokenBuffer) {
/* 205 */     setDebugMode(true);
/*     */     try
/*     */     {
/*     */       try {
/* 209 */         Utils.loadClass("javax.swing.JButton");
/*     */       }
/*     */       catch (ClassNotFoundException localClassNotFoundException) {
/* 212 */         System.err.println("Swing is required to use ParseView, but is not present in your CLASSPATH");
/* 213 */         System.exit(1);
/*     */       }
/* 215 */       Class localClass = Utils.loadClass("antlr.parseview.ParseView");
/* 216 */       Constructor localConstructor = localClass.getConstructor(new Class[] { LLkDebuggingParser.class, TokenStream.class, TokenBuffer.class });
/* 217 */       localConstructor.newInstance(new Object[] { this, paramTokenStream, paramTokenBuffer });
/*     */     }
/*     */     catch (Exception localException) {
/* 220 */       System.err.println("Error initializing ParseView: " + localException);
/* 221 */       System.err.println("Please report this to Scott Stanchfield, thetick@magelang.com");
/* 222 */       System.exit(1);
/*     */     }
/*     */   }
/*     */ 
/* 226 */   public synchronized void wakeUp() { notify(); }
/*     */ 
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     antlr.debug.LLkDebuggingParser
 * JD-Core Version:    0.6.2
 */