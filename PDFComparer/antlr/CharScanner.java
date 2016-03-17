/*     */ package antlr;
/*     */ 
/*     */ import antlr.collections.impl.BitSet;
/*     */ import java.io.PrintStream;
/*     */ import java.util.Hashtable;
/*     */ 
/*     */ public abstract class CharScanner
/*     */   implements TokenStream
/*     */ {
/*     */   static final char NO_CHAR = '\000';
/*     */   public static final char EOF_CHAR = 'èøø';
/*     */   protected ANTLRStringBuffer text;
/*  19 */   protected boolean saveConsumedInput = true;
/*     */   protected Class tokenObjectClass;
/*  21 */   protected boolean caseSensitive = true;
/*  22 */   protected boolean caseSensitiveLiterals = true;
/*     */   protected Hashtable literals;
/*  28 */   protected int tabsize = 8;
/*     */ 
/*  30 */   protected Token _returnToken = null;
/*     */   protected ANTLRHashString hashString;
/*     */   protected LexerSharedInputState inputState;
/*  41 */   protected boolean commitToPath = false;
/*     */ 
/*  44 */   protected int traceDepth = 0;
/*     */ 
/*     */   public CharScanner() {
/*  47 */     this.text = new ANTLRStringBuffer();
/*  48 */     this.hashString = new ANTLRHashString(this);
/*  49 */     setTokenObjectClass("antlr.CommonToken");
/*     */   }
/*     */ 
/*     */   public CharScanner(InputBuffer paramInputBuffer) {
/*  53 */     this();
/*  54 */     this.inputState = new LexerSharedInputState(paramInputBuffer);
/*     */   }
/*     */ 
/*     */   public CharScanner(LexerSharedInputState paramLexerSharedInputState) {
/*  58 */     this();
/*  59 */     this.inputState = paramLexerSharedInputState;
/*     */   }
/*     */ 
/*     */   public void append(char paramChar) {
/*  63 */     if (this.saveConsumedInput)
/*  64 */       this.text.append(paramChar);
/*     */   }
/*     */ 
/*     */   public void append(String paramString)
/*     */   {
/*  69 */     if (this.saveConsumedInput)
/*  70 */       this.text.append(paramString);
/*     */   }
/*     */ 
/*     */   public void commit()
/*     */   {
/*  75 */     this.inputState.input.commit();
/*     */   }
/*     */ 
/*     */   public void consume() throws CharStreamException {
/*  79 */     if (this.inputState.guessing == 0) {
/*  80 */       char c = LA(1);
/*  81 */       if (this.caseSensitive) {
/*  82 */         append(c);
/*     */       }
/*     */       else
/*     */       {
/*  87 */         append(this.inputState.input.LA(1));
/*     */       }
/*  89 */       if (c == '\t') {
/*  90 */         tab();
/*     */       }
/*     */       else {
/*  93 */         this.inputState.column += 1;
/*     */       }
/*     */     }
/*  96 */     this.inputState.input.consume();
/*     */   }
/*     */ 
/*     */   public void consumeUntil(int paramInt) throws CharStreamException
/*     */   {
/* 101 */     while ((LA(1) != 65535) && (LA(1) != paramInt))
/* 102 */       consume();
/*     */   }
/*     */ 
/*     */   public void consumeUntil(BitSet paramBitSet)
/*     */     throws CharStreamException
/*     */   {
/* 108 */     while ((LA(1) != 65535) && (!paramBitSet.member(LA(1))))
/* 109 */       consume();
/*     */   }
/*     */ 
/*     */   public boolean getCaseSensitive()
/*     */   {
/* 114 */     return this.caseSensitive;
/*     */   }
/*     */ 
/*     */   public final boolean getCaseSensitiveLiterals() {
/* 118 */     return this.caseSensitiveLiterals;
/*     */   }
/*     */ 
/*     */   public int getColumn() {
/* 122 */     return this.inputState.column;
/*     */   }
/*     */ 
/*     */   public void setColumn(int paramInt) {
/* 126 */     this.inputState.column = paramInt;
/*     */   }
/*     */ 
/*     */   public boolean getCommitToPath() {
/* 130 */     return this.commitToPath;
/*     */   }
/*     */ 
/*     */   public String getFilename() {
/* 134 */     return this.inputState.filename;
/*     */   }
/*     */ 
/*     */   public InputBuffer getInputBuffer() {
/* 138 */     return this.inputState.input;
/*     */   }
/*     */ 
/*     */   public LexerSharedInputState getInputState() {
/* 142 */     return this.inputState;
/*     */   }
/*     */ 
/*     */   public void setInputState(LexerSharedInputState paramLexerSharedInputState) {
/* 146 */     this.inputState = paramLexerSharedInputState;
/*     */   }
/*     */ 
/*     */   public int getLine() {
/* 150 */     return this.inputState.line;
/*     */   }
/*     */ 
/*     */   public String getText()
/*     */   {
/* 155 */     return this.text.toString();
/*     */   }
/*     */ 
/*     */   public Token getTokenObject() {
/* 159 */     return this._returnToken;
/*     */   }
/*     */ 
/*     */   public char LA(int paramInt) throws CharStreamException {
/* 163 */     if (this.caseSensitive) {
/* 164 */       return this.inputState.input.LA(paramInt);
/*     */     }
/*     */ 
/* 167 */     return toLower(this.inputState.input.LA(paramInt));
/*     */   }
/*     */ 
/*     */   protected Token makeToken(int paramInt)
/*     */   {
/*     */     try {
/* 173 */       Token localToken = (Token)this.tokenObjectClass.newInstance();
/* 174 */       localToken.setType(paramInt);
/* 175 */       localToken.setColumn(this.inputState.tokenStartColumn);
/* 176 */       localToken.setLine(this.inputState.tokenStartLine);
/*     */ 
/* 178 */       return localToken;
/*     */     }
/*     */     catch (InstantiationException localInstantiationException) {
/* 181 */       panic("can't instantiate token: " + this.tokenObjectClass);
/*     */     }
/*     */     catch (IllegalAccessException localIllegalAccessException) {
/* 184 */       panic("Token class is not accessible" + this.tokenObjectClass);
/*     */     }
/* 186 */     return Token.badToken;
/*     */   }
/*     */ 
/*     */   public int mark() {
/* 190 */     return this.inputState.input.mark();
/*     */   }
/*     */ 
/*     */   public void match(char paramChar) throws MismatchedCharException, CharStreamException {
/* 194 */     if (LA(1) != paramChar) {
/* 195 */       throw new MismatchedCharException(LA(1), paramChar, false, this);
/*     */     }
/* 197 */     consume();
/*     */   }
/*     */ 
/*     */   public void match(BitSet paramBitSet) throws MismatchedCharException, CharStreamException {
/* 201 */     if (!paramBitSet.member(LA(1))) {
/* 202 */       throw new MismatchedCharException(LA(1), paramBitSet, false, this);
/*     */     }
/*     */ 
/* 205 */     consume();
/*     */   }
/*     */ 
/*     */   public void match(String paramString) throws MismatchedCharException, CharStreamException
/*     */   {
/* 210 */     int i = paramString.length();
/* 211 */     for (int j = 0; j < i; j++) {
/* 212 */       if (LA(1) != paramString.charAt(j)) {
/* 213 */         throw new MismatchedCharException(LA(1), paramString.charAt(j), false, this);
/*     */       }
/* 215 */       consume();
/*     */     }
/*     */   }
/*     */ 
/*     */   public void matchNot(char paramChar) throws MismatchedCharException, CharStreamException {
/* 220 */     if (LA(1) == paramChar) {
/* 221 */       throw new MismatchedCharException(LA(1), paramChar, true, this);
/*     */     }
/* 223 */     consume();
/*     */   }
/*     */ 
/*     */   public void matchRange(char paramChar1, char paramChar2) throws MismatchedCharException, CharStreamException {
/* 227 */     if ((LA(1) < paramChar1) || (LA(1) > paramChar2)) throw new MismatchedCharException(LA(1), paramChar1, paramChar2, false, this);
/* 228 */     consume();
/*     */   }
/*     */ 
/*     */   public void newline() {
/* 232 */     this.inputState.line += 1;
/* 233 */     this.inputState.column = 1;
/*     */   }
/*     */ 
/*     */   public void tab()
/*     */   {
/* 240 */     int i = getColumn();
/* 241 */     int j = ((i - 1) / this.tabsize + 1) * this.tabsize + 1;
/* 242 */     setColumn(j);
/*     */   }
/*     */ 
/*     */   public void setTabSize(int paramInt) {
/* 246 */     this.tabsize = paramInt;
/*     */   }
/*     */ 
/*     */   public int getTabSize() {
/* 250 */     return this.tabsize;
/*     */   }
/*     */ 
/*     */   public void panic()
/*     */   {
/* 256 */     System.err.println("CharScanner: panic");
/* 257 */     Utils.error("");
/*     */   }
/*     */ 
/*     */   public void panic(String paramString)
/*     */   {
/* 271 */     System.err.println("CharScanner; panic: " + paramString);
/* 272 */     Utils.error(paramString);
/*     */   }
/*     */ 
/*     */   public void reportError(RecognitionException paramRecognitionException)
/*     */   {
/* 277 */     System.err.println(paramRecognitionException);
/*     */   }
/*     */ 
/*     */   public void reportError(String paramString)
/*     */   {
/* 282 */     if (getFilename() == null) {
/* 283 */       System.err.println("error: " + paramString);
/*     */     }
/*     */     else
/* 286 */       System.err.println(getFilename() + ": error: " + paramString);
/*     */   }
/*     */ 
/*     */   public void reportWarning(String paramString)
/*     */   {
/* 292 */     if (getFilename() == null) {
/* 293 */       System.err.println("warning: " + paramString);
/*     */     }
/*     */     else
/* 296 */       System.err.println(getFilename() + ": warning: " + paramString);
/*     */   }
/*     */ 
/*     */   public void resetText()
/*     */   {
/* 301 */     this.text.setLength(0);
/* 302 */     this.inputState.tokenStartColumn = this.inputState.column;
/* 303 */     this.inputState.tokenStartLine = this.inputState.line;
/*     */   }
/*     */ 
/*     */   public void rewind(int paramInt) {
/* 307 */     this.inputState.input.rewind(paramInt);
/*     */   }
/*     */ 
/*     */   public void setCaseSensitive(boolean paramBoolean)
/*     */   {
/* 313 */     this.caseSensitive = paramBoolean;
/*     */   }
/*     */ 
/*     */   public void setCommitToPath(boolean paramBoolean) {
/* 317 */     this.commitToPath = paramBoolean;
/*     */   }
/*     */ 
/*     */   public void setFilename(String paramString) {
/* 321 */     this.inputState.filename = paramString;
/*     */   }
/*     */ 
/*     */   public void setLine(int paramInt) {
/* 325 */     this.inputState.line = paramInt;
/*     */   }
/*     */ 
/*     */   public void setText(String paramString) {
/* 329 */     resetText();
/* 330 */     this.text.append(paramString);
/*     */   }
/*     */ 
/*     */   public void setTokenObjectClass(String paramString) {
/*     */     try {
/* 335 */       this.tokenObjectClass = Utils.loadClass(paramString);
/*     */     }
/*     */     catch (ClassNotFoundException localClassNotFoundException) {
/* 338 */       panic("ClassNotFoundException: " + paramString);
/*     */     }
/*     */   }
/*     */ 
/*     */   public int testLiteralsTable(int paramInt)
/*     */   {
/* 345 */     this.hashString.setBuffer(this.text.getBuffer(), this.text.length());
/* 346 */     Integer localInteger = (Integer)this.literals.get(this.hashString);
/* 347 */     if (localInteger != null) {
/* 348 */       paramInt = localInteger.intValue();
/*     */     }
/* 350 */     return paramInt;
/*     */   }
/*     */ 
/*     */   public int testLiteralsTable(String paramString, int paramInt)
/*     */   {
/* 359 */     ANTLRHashString localANTLRHashString = new ANTLRHashString(paramString, this);
/* 360 */     Integer localInteger = (Integer)this.literals.get(localANTLRHashString);
/* 361 */     if (localInteger != null) {
/* 362 */       paramInt = localInteger.intValue();
/*     */     }
/* 364 */     return paramInt;
/*     */   }
/*     */ 
/*     */   public char toLower(char paramChar)
/*     */   {
/* 369 */     return Character.toLowerCase(paramChar);
/*     */   }
/*     */ 
/*     */   public void traceIndent() {
/* 373 */     for (int i = 0; i < this.traceDepth; i++)
/* 374 */       System.out.print(" ");
/*     */   }
/*     */ 
/*     */   public void traceIn(String paramString) throws CharStreamException {
/* 378 */     this.traceDepth += 1;
/* 379 */     traceIndent();
/* 380 */     System.out.println("> lexer " + paramString + "; c==" + LA(1));
/*     */   }
/*     */ 
/*     */   public void traceOut(String paramString) throws CharStreamException {
/* 384 */     traceIndent();
/* 385 */     System.out.println("< lexer " + paramString + "; c==" + LA(1));
/* 386 */     this.traceDepth -= 1;
/*     */   }
/*     */ 
/*     */   public void uponEOF()
/*     */     throws TokenStreamException, CharStreamException
/*     */   {
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     antlr.CharScanner
 * JD-Core Version:    0.6.2
 */