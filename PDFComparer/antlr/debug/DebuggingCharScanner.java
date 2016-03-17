/*     */ package antlr.debug;
/*     */ 
/*     */ import antlr.ANTLRStringBuffer;
/*     */ import antlr.CharScanner;
/*     */ import antlr.CharStreamException;
/*     */ import antlr.InputBuffer;
/*     */ import antlr.LexerSharedInputState;
/*     */ import antlr.MismatchedCharException;
/*     */ import antlr.Token;
/*     */ import antlr.collections.impl.BitSet;
/*     */ 
/*     */ public abstract class DebuggingCharScanner extends CharScanner
/*     */   implements DebuggingParser
/*     */ {
/*   9 */   private ParserEventSupport parserEventSupport = new ParserEventSupport(this);
/*  10 */   private boolean _notDebugMode = false;
/*     */   protected String[] ruleNames;
/*     */   protected String[] semPredNames;
/*     */ 
/*     */   public DebuggingCharScanner(InputBuffer paramInputBuffer)
/*     */   {
/*  16 */     super(paramInputBuffer);
/*     */   }
/*     */   public DebuggingCharScanner(LexerSharedInputState paramLexerSharedInputState) {
/*  19 */     super(paramLexerSharedInputState);
/*     */   }
/*     */   public void addMessageListener(MessageListener paramMessageListener) {
/*  22 */     this.parserEventSupport.addMessageListener(paramMessageListener);
/*     */   }
/*     */   public void addNewLineListener(NewLineListener paramNewLineListener) {
/*  25 */     this.parserEventSupport.addNewLineListener(paramNewLineListener);
/*     */   }
/*     */   public void addParserListener(ParserListener paramParserListener) {
/*  28 */     this.parserEventSupport.addParserListener(paramParserListener);
/*     */   }
/*     */   public void addParserMatchListener(ParserMatchListener paramParserMatchListener) {
/*  31 */     this.parserEventSupport.addParserMatchListener(paramParserMatchListener);
/*     */   }
/*     */   public void addParserTokenListener(ParserTokenListener paramParserTokenListener) {
/*  34 */     this.parserEventSupport.addParserTokenListener(paramParserTokenListener);
/*     */   }
/*     */   public void addSemanticPredicateListener(SemanticPredicateListener paramSemanticPredicateListener) {
/*  37 */     this.parserEventSupport.addSemanticPredicateListener(paramSemanticPredicateListener);
/*     */   }
/*     */   public void addSyntacticPredicateListener(SyntacticPredicateListener paramSyntacticPredicateListener) {
/*  40 */     this.parserEventSupport.addSyntacticPredicateListener(paramSyntacticPredicateListener);
/*     */   }
/*     */   public void addTraceListener(TraceListener paramTraceListener) {
/*  43 */     this.parserEventSupport.addTraceListener(paramTraceListener);
/*     */   }
/*     */   public void consume() throws CharStreamException {
/*  46 */     int i = -99;
/*     */     try { i = LA(1); } catch (CharStreamException localCharStreamException) {
/*     */     }
/*  49 */     super.consume();
/*  50 */     this.parserEventSupport.fireConsume(i);
/*     */   }
/*     */   protected void fireEnterRule(int paramInt1, int paramInt2) {
/*  53 */     if (isDebugMode())
/*  54 */       this.parserEventSupport.fireEnterRule(paramInt1, this.inputState.guessing, paramInt2); 
/*     */   }
/*     */ 
/*  57 */   protected void fireExitRule(int paramInt1, int paramInt2) { if (isDebugMode())
/*  58 */       this.parserEventSupport.fireExitRule(paramInt1, this.inputState.guessing, paramInt2); }
/*     */ 
/*     */   protected boolean fireSemanticPredicateEvaluated(int paramInt1, int paramInt2, boolean paramBoolean) {
/*  61 */     if (isDebugMode()) {
/*  62 */       return this.parserEventSupport.fireSemanticPredicateEvaluated(paramInt1, paramInt2, paramBoolean, this.inputState.guessing);
/*     */     }
/*  64 */     return paramBoolean;
/*     */   }
/*     */   protected void fireSyntacticPredicateFailed() {
/*  67 */     if (isDebugMode())
/*  68 */       this.parserEventSupport.fireSyntacticPredicateFailed(this.inputState.guessing); 
/*     */   }
/*     */ 
/*  71 */   protected void fireSyntacticPredicateStarted() { if (isDebugMode())
/*  72 */       this.parserEventSupport.fireSyntacticPredicateStarted(this.inputState.guessing); }
/*     */ 
/*     */   protected void fireSyntacticPredicateSucceeded() {
/*  75 */     if (isDebugMode())
/*  76 */       this.parserEventSupport.fireSyntacticPredicateSucceeded(this.inputState.guessing); 
/*     */   }
/*     */ 
/*  79 */   public String getRuleName(int paramInt) { return this.ruleNames[paramInt]; }
/*     */ 
/*     */   public String getSemPredName(int paramInt) {
/*  82 */     return this.semPredNames[paramInt];
/*     */   }
/*     */   public synchronized void goToSleep() {
/*     */     try { wait(); } catch (InterruptedException localInterruptedException) {
/*     */     }
/*     */   }
/*     */ 
/*  89 */   public boolean isDebugMode() { return !this._notDebugMode; }
/*     */ 
/*     */   public char LA(int paramInt) throws CharStreamException {
/*  92 */     char c = super.LA(paramInt);
/*  93 */     this.parserEventSupport.fireLA(paramInt, c);
/*  94 */     return c;
/*     */   }
/*     */ 
/*     */   protected Token makeToken(int paramInt)
/*     */   {
/* 111 */     return super.makeToken(paramInt);
/*     */   }
/*     */   public void match(char paramChar) throws MismatchedCharException, CharStreamException {
/* 114 */     char c = LA(1);
/*     */     try {
/* 116 */       super.match(paramChar);
/* 117 */       this.parserEventSupport.fireMatch(paramChar, this.inputState.guessing);
/*     */     }
/*     */     catch (MismatchedCharException localMismatchedCharException) {
/* 120 */       if (this.inputState.guessing == 0)
/* 121 */         this.parserEventSupport.fireMismatch(c, paramChar, this.inputState.guessing);
/* 122 */       throw localMismatchedCharException;
/*     */     }
/*     */   }
/*     */ 
/* 126 */   public void match(BitSet paramBitSet) throws MismatchedCharException, CharStreamException { String str = this.text.toString();
/* 127 */     int i = LA(1);
/*     */     try {
/* 129 */       super.match(paramBitSet);
/* 130 */       this.parserEventSupport.fireMatch(i, paramBitSet, str, this.inputState.guessing);
/*     */     }
/*     */     catch (MismatchedCharException localMismatchedCharException) {
/* 133 */       if (this.inputState.guessing == 0)
/* 134 */         this.parserEventSupport.fireMismatch(i, paramBitSet, str, this.inputState.guessing);
/* 135 */       throw localMismatchedCharException;
/*     */     } }
/*     */ 
/*     */   public void match(String paramString) throws MismatchedCharException, CharStreamException {
/* 139 */     StringBuffer localStringBuffer = new StringBuffer("");
/* 140 */     int i = paramString.length();
/*     */     try
/*     */     {
/* 143 */       for (int j = 1; j <= i; j++)
/* 144 */         localStringBuffer.append(super.LA(j));
/*     */     }
/*     */     catch (Exception localException)
/*     */     {
/*     */     }
/*     */     try {
/* 150 */       super.match(paramString);
/* 151 */       this.parserEventSupport.fireMatch(paramString, this.inputState.guessing);
/*     */     }
/*     */     catch (MismatchedCharException localMismatchedCharException) {
/* 154 */       if (this.inputState.guessing == 0)
/* 155 */         this.parserEventSupport.fireMismatch(localStringBuffer.toString(), paramString, this.inputState.guessing);
/* 156 */       throw localMismatchedCharException;
/*     */     }
/*     */   }
/*     */ 
/*     */   public void matchNot(char paramChar) throws MismatchedCharException, CharStreamException {
/* 161 */     char c = LA(1);
/*     */     try {
/* 163 */       super.matchNot(paramChar);
/* 164 */       this.parserEventSupport.fireMatchNot(c, paramChar, this.inputState.guessing);
/*     */     }
/*     */     catch (MismatchedCharException localMismatchedCharException) {
/* 167 */       if (this.inputState.guessing == 0)
/* 168 */         this.parserEventSupport.fireMismatchNot(c, paramChar, this.inputState.guessing);
/* 169 */       throw localMismatchedCharException;
/*     */     }
/*     */   }
/*     */ 
/*     */   public void matchRange(char paramChar1, char paramChar2) throws MismatchedCharException, CharStreamException {
/* 174 */     char c = LA(1);
/*     */     try {
/* 176 */       super.matchRange(paramChar1, paramChar2);
/* 177 */       this.parserEventSupport.fireMatch(c, "" + paramChar1 + paramChar2, this.inputState.guessing);
/*     */     }
/*     */     catch (MismatchedCharException localMismatchedCharException) {
/* 180 */       if (this.inputState.guessing == 0)
/* 181 */         this.parserEventSupport.fireMismatch(c, "" + paramChar1 + paramChar2, this.inputState.guessing);
/* 182 */       throw localMismatchedCharException;
/*     */     }
/*     */   }
/*     */ 
/*     */   public void newline() {
/* 187 */     super.newline();
/* 188 */     this.parserEventSupport.fireNewLine(getLine());
/*     */   }
/*     */   public void removeMessageListener(MessageListener paramMessageListener) {
/* 191 */     this.parserEventSupport.removeMessageListener(paramMessageListener);
/*     */   }
/*     */   public void removeNewLineListener(NewLineListener paramNewLineListener) {
/* 194 */     this.parserEventSupport.removeNewLineListener(paramNewLineListener);
/*     */   }
/*     */   public void removeParserListener(ParserListener paramParserListener) {
/* 197 */     this.parserEventSupport.removeParserListener(paramParserListener);
/*     */   }
/*     */   public void removeParserMatchListener(ParserMatchListener paramParserMatchListener) {
/* 200 */     this.parserEventSupport.removeParserMatchListener(paramParserMatchListener);
/*     */   }
/*     */   public void removeParserTokenListener(ParserTokenListener paramParserTokenListener) {
/* 203 */     this.parserEventSupport.removeParserTokenListener(paramParserTokenListener);
/*     */   }
/*     */   public void removeSemanticPredicateListener(SemanticPredicateListener paramSemanticPredicateListener) {
/* 206 */     this.parserEventSupport.removeSemanticPredicateListener(paramSemanticPredicateListener);
/*     */   }
/*     */   public void removeSyntacticPredicateListener(SyntacticPredicateListener paramSyntacticPredicateListener) {
/* 209 */     this.parserEventSupport.removeSyntacticPredicateListener(paramSyntacticPredicateListener);
/*     */   }
/*     */   public void removeTraceListener(TraceListener paramTraceListener) {
/* 212 */     this.parserEventSupport.removeTraceListener(paramTraceListener);
/*     */   }
/*     */ 
/*     */   public void reportError(MismatchedCharException paramMismatchedCharException) {
/* 216 */     this.parserEventSupport.fireReportError(paramMismatchedCharException);
/* 217 */     super.reportError(paramMismatchedCharException);
/*     */   }
/*     */ 
/*     */   public void reportError(String paramString) {
/* 221 */     this.parserEventSupport.fireReportError(paramString);
/* 222 */     super.reportError(paramString);
/*     */   }
/*     */ 
/*     */   public void reportWarning(String paramString) {
/* 226 */     this.parserEventSupport.fireReportWarning(paramString);
/* 227 */     super.reportWarning(paramString);
/*     */   }
/*     */   public void setDebugMode(boolean paramBoolean) {
/* 230 */     this._notDebugMode = (!paramBoolean);
/*     */   }
/*     */   public void setupDebugging() {
/*     */   }
/*     */   public synchronized void wakeUp() {
/* 235 */     notify();
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     antlr.debug.DebuggingCharScanner
 * JD-Core Version:    0.6.2
 */