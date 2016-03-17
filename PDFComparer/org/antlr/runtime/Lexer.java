/*     */ package org.antlr.runtime;
/*     */ 
/*     */ public abstract class Lexer extends BaseRecognizer
/*     */   implements TokenSource
/*     */ {
/*     */   protected CharStream input;
/*     */ 
/*     */   public Lexer()
/*     */   {
/*     */   }
/*     */ 
/*     */   public Lexer(CharStream input)
/*     */   {
/*  43 */     this.input = input;
/*     */   }
/*     */ 
/*     */   public Lexer(CharStream input, RecognizerSharedState state) {
/*  47 */     super(state);
/*  48 */     this.input = input;
/*     */   }
/*     */ 
/*     */   public void reset() {
/*  52 */     super.reset();
/*     */ 
/*  54 */     if (this.input != null) {
/*  55 */       this.input.seek(0);
/*     */     }
/*  57 */     if (this.state == null) {
/*  58 */       return;
/*     */     }
/*  60 */     this.state.token = null;
/*  61 */     this.state.type = 0;
/*  62 */     this.state.channel = 0;
/*  63 */     this.state.tokenStartCharIndex = -1;
/*  64 */     this.state.tokenStartCharPositionInLine = -1;
/*  65 */     this.state.tokenStartLine = -1;
/*  66 */     this.state.text = null;
/*     */   }
/*     */ 
/*     */   public Token nextToken()
/*     */   {
/*     */     while (true)
/*     */     {
/*  74 */       this.state.token = null;
/*  75 */       this.state.channel = 0;
/*  76 */       this.state.tokenStartCharIndex = this.input.index();
/*  77 */       this.state.tokenStartCharPositionInLine = this.input.getCharPositionInLine();
/*  78 */       this.state.tokenStartLine = this.input.getLine();
/*  79 */       this.state.text = null;
/*  80 */       if (this.input.LA(1) == -1) {
/*  81 */         Token eof = new CommonToken(this.input, -1, 0, this.input.index(), this.input.index());
/*     */ 
/*  84 */         eof.setLine(getLine());
/*  85 */         eof.setCharPositionInLine(getCharPositionInLine());
/*  86 */         return eof;
/*     */       }
/*     */       try {
/*  89 */         mTokens();
/*  90 */         if (this.state.token == null)
/*  91 */           emit();
/*     */         else {
/*  93 */           if (this.state.token == Token.SKIP_TOKEN)
/*     */             continue;
/*     */         }
/*  96 */         return this.state.token;
/*     */       }
/*     */       catch (NoViableAltException nva) {
/*  99 */         reportError(nva);
/* 100 */         recover(nva);
/*     */       }
/*     */       catch (RecognitionException re) {
/* 103 */         reportError(re);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public void skip()
/*     */   {
/* 116 */     this.state.token = Token.SKIP_TOKEN;
/*     */   }
/*     */ 
/*     */   public abstract void mTokens()
/*     */     throws RecognitionException;
/*     */ 
/*     */   public void setCharStream(CharStream input)
/*     */   {
/* 124 */     this.input = null;
/* 125 */     reset();
/* 126 */     this.input = input;
/*     */   }
/*     */ 
/*     */   public CharStream getCharStream() {
/* 130 */     return this.input;
/*     */   }
/*     */ 
/*     */   public String getSourceName() {
/* 134 */     return this.input.getSourceName();
/*     */   }
/*     */ 
/*     */   public void emit(Token token)
/*     */   {
/* 143 */     this.state.token = token;
/*     */   }
/*     */ 
/*     */   public Token emit()
/*     */   {
/* 156 */     Token t = new CommonToken(this.input, this.state.type, this.state.channel, this.state.tokenStartCharIndex, getCharIndex() - 1);
/* 157 */     t.setLine(this.state.tokenStartLine);
/* 158 */     t.setText(this.state.text);
/* 159 */     t.setCharPositionInLine(this.state.tokenStartCharPositionInLine);
/* 160 */     emit(t);
/* 161 */     return t;
/*     */   }
/*     */ 
/*     */   public void match(String s) throws MismatchedTokenException {
/* 165 */     int i = 0;
/* 166 */     while (i < s.length()) {
/* 167 */       if (this.input.LA(1) != s.charAt(i)) {
/* 168 */         if (this.state.backtracking > 0) {
/* 169 */           this.state.failed = true;
/* 170 */           return;
/*     */         }
/* 172 */         MismatchedTokenException mte = new MismatchedTokenException(s.charAt(i), this.input);
/*     */ 
/* 174 */         recover(mte);
/* 175 */         throw mte;
/*     */       }
/* 177 */       i++;
/* 178 */       this.input.consume();
/* 179 */       this.state.failed = false;
/*     */     }
/*     */   }
/*     */ 
/*     */   public void matchAny() {
/* 184 */     this.input.consume();
/*     */   }
/*     */ 
/*     */   public void match(int c) throws MismatchedTokenException {
/* 188 */     if (this.input.LA(1) != c) {
/* 189 */       if (this.state.backtracking > 0) {
/* 190 */         this.state.failed = true;
/* 191 */         return;
/*     */       }
/* 193 */       MismatchedTokenException mte = new MismatchedTokenException(c, this.input);
/*     */ 
/* 195 */       recover(mte);
/* 196 */       throw mte;
/*     */     }
/* 198 */     this.input.consume();
/* 199 */     this.state.failed = false;
/*     */   }
/*     */ 
/*     */   public void matchRange(int a, int b)
/*     */     throws MismatchedRangeException
/*     */   {
/* 205 */     if ((this.input.LA(1) < a) || (this.input.LA(1) > b)) {
/* 206 */       if (this.state.backtracking > 0) {
/* 207 */         this.state.failed = true;
/* 208 */         return;
/*     */       }
/* 210 */       MismatchedRangeException mre = new MismatchedRangeException(a, b, this.input);
/*     */ 
/* 212 */       recover(mre);
/* 213 */       throw mre;
/*     */     }
/* 215 */     this.input.consume();
/* 216 */     this.state.failed = false;
/*     */   }
/*     */ 
/*     */   public int getLine() {
/* 220 */     return this.input.getLine();
/*     */   }
/*     */ 
/*     */   public int getCharPositionInLine() {
/* 224 */     return this.input.getCharPositionInLine();
/*     */   }
/*     */ 
/*     */   public int getCharIndex()
/*     */   {
/* 229 */     return this.input.index();
/*     */   }
/*     */ 
/*     */   public String getText()
/*     */   {
/* 236 */     if (this.state.text != null) {
/* 237 */       return this.state.text;
/*     */     }
/* 239 */     return this.input.substring(this.state.tokenStartCharIndex, getCharIndex() - 1);
/*     */   }
/*     */ 
/*     */   public void setText(String text)
/*     */   {
/* 246 */     this.state.text = text;
/*     */   }
/*     */ 
/*     */   public void reportError(RecognitionException e)
/*     */   {
/* 261 */     displayRecognitionError(getTokenNames(), e);
/*     */   }
/*     */ 
/*     */   public String getErrorMessage(RecognitionException e, String[] tokenNames) {
/* 265 */     String msg = null;
/* 266 */     if ((e instanceof MismatchedTokenException)) {
/* 267 */       MismatchedTokenException mte = (MismatchedTokenException)e;
/* 268 */       msg = "mismatched character " + getCharErrorDisplay(e.c) + " expecting " + getCharErrorDisplay(mte.expecting);
/*     */     }
/* 270 */     else if ((e instanceof NoViableAltException)) {
/* 271 */       NoViableAltException nvae = (NoViableAltException)e;
/*     */ 
/* 275 */       msg = "no viable alternative at character " + getCharErrorDisplay(e.c);
/*     */     }
/* 277 */     else if ((e instanceof EarlyExitException)) {
/* 278 */       EarlyExitException eee = (EarlyExitException)e;
/*     */ 
/* 280 */       msg = "required (...)+ loop did not match anything at character " + getCharErrorDisplay(e.c);
/*     */     }
/* 282 */     else if ((e instanceof MismatchedNotSetException)) {
/* 283 */       MismatchedNotSetException mse = (MismatchedNotSetException)e;
/* 284 */       msg = "mismatched character " + getCharErrorDisplay(e.c) + " expecting set " + mse.expecting;
/*     */     }
/* 286 */     else if ((e instanceof MismatchedSetException)) {
/* 287 */       MismatchedSetException mse = (MismatchedSetException)e;
/* 288 */       msg = "mismatched character " + getCharErrorDisplay(e.c) + " expecting set " + mse.expecting;
/*     */     }
/* 290 */     else if ((e instanceof MismatchedRangeException)) {
/* 291 */       MismatchedRangeException mre = (MismatchedRangeException)e;
/* 292 */       msg = "mismatched character " + getCharErrorDisplay(e.c) + " expecting set " + getCharErrorDisplay(mre.a) + ".." + getCharErrorDisplay(mre.b);
/*     */     }
/*     */     else
/*     */     {
/* 296 */       msg = super.getErrorMessage(e, tokenNames);
/*     */     }
/* 298 */     return msg;
/*     */   }
/*     */ 
/*     */   public String getCharErrorDisplay(int c) {
/* 302 */     String s = String.valueOf((char)c);
/* 303 */     switch (c) {
/*     */     case -1:
/* 305 */       s = "<EOF>";
/* 306 */       break;
/*     */     case 10:
/* 308 */       s = "\\n";
/* 309 */       break;
/*     */     case 9:
/* 311 */       s = "\\t";
/* 312 */       break;
/*     */     case 13:
/* 314 */       s = "\\r";
/*     */     }
/*     */ 
/* 317 */     return "'" + s + "'";
/*     */   }
/*     */ 
/*     */   public void recover(RecognitionException re)
/*     */   {
/* 328 */     this.input.consume();
/*     */   }
/*     */ 
/*     */   public void traceIn(String ruleName, int ruleIndex) {
/* 332 */     String inputSymbol = (char)this.input.LT(1) + " line=" + getLine() + ":" + getCharPositionInLine();
/* 333 */     super.traceIn(ruleName, ruleIndex, inputSymbol);
/*     */   }
/*     */ 
/*     */   public void traceOut(String ruleName, int ruleIndex) {
/* 337 */     String inputSymbol = (char)this.input.LT(1) + " line=" + getLine() + ":" + getCharPositionInLine();
/* 338 */     super.traceOut(ruleName, ruleIndex, inputSymbol);
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.antlr.runtime.Lexer
 * JD-Core Version:    0.6.2
 */