/*     */ package antlr.actions.python;
/*     */ 
/*     */ import antlr.ANTLRStringBuffer;
/*     */ import antlr.ByteBuffer;
/*     */ import antlr.CharBuffer;
/*     */ import antlr.CharScanner;
/*     */ import antlr.CharStreamException;
/*     */ import antlr.CharStreamIOException;
/*     */ import antlr.InputBuffer;
/*     */ import antlr.LexerSharedInputState;
/*     */ import antlr.NoViableAltForCharException;
/*     */ import antlr.RecognitionException;
/*     */ import antlr.Token;
/*     */ import antlr.TokenStream;
/*     */ import antlr.TokenStreamException;
/*     */ import antlr.TokenStreamIOException;
/*     */ import antlr.TokenStreamRecognitionException;
/*     */ import antlr.Tool;
/*     */ import antlr.collections.impl.BitSet;
/*     */ import java.io.InputStream;
/*     */ import java.io.Reader;
/*     */ import java.io.StringReader;
/*     */ import java.util.Hashtable;
/*     */ 
/*     */ public class CodeLexer extends CharScanner
/*     */   implements CodeLexerTokenTypes, TokenStream
/*     */ {
/*  36 */   protected int lineOffset = 0;
/*     */   private Tool antlrTool;
/* 374 */   public static final BitSet _tokenSet_0 = new BitSet(mk_tokenSet_0());
/*     */ 
/* 381 */   public static final BitSet _tokenSet_1 = new BitSet(mk_tokenSet_1());
/*     */ 
/*     */   public CodeLexer(String paramString1, String paramString2, int paramInt, Tool paramTool)
/*     */   {
/*  46 */     this(new StringReader(paramString1));
/*  47 */     setLine(paramInt);
/*  48 */     setFilename(paramString2);
/*  49 */     this.antlrTool = paramTool;
/*     */   }
/*     */ 
/*     */   public void setLineOffset(int paramInt) {
/*  53 */     setLine(paramInt);
/*     */   }
/*     */ 
/*     */   public void reportError(RecognitionException paramRecognitionException)
/*     */   {
/*  58 */     this.antlrTool.error("Syntax error in action: " + paramRecognitionException, getFilename(), getLine(), getColumn());
/*     */   }
/*     */ 
/*     */   public void reportError(String paramString)
/*     */   {
/*  65 */     this.antlrTool.error(paramString, getFilename(), getLine(), getColumn());
/*     */   }
/*     */ 
/*     */   public void reportWarning(String paramString)
/*     */   {
/*  70 */     if (getFilename() == null) {
/*  71 */       this.antlrTool.warning(paramString);
/*     */     }
/*     */     else
/*  74 */       this.antlrTool.warning(paramString, getFilename(), getLine(), getColumn());
/*     */   }
/*     */ 
/*     */   public CodeLexer(InputStream paramInputStream) {
/*  78 */     this(new ByteBuffer(paramInputStream));
/*     */   }
/*     */   public CodeLexer(Reader paramReader) {
/*  81 */     this(new CharBuffer(paramReader));
/*     */   }
/*     */   public CodeLexer(InputBuffer paramInputBuffer) {
/*  84 */     this(new LexerSharedInputState(paramInputBuffer));
/*     */   }
/*     */   public CodeLexer(LexerSharedInputState paramLexerSharedInputState) {
/*  87 */     super(paramLexerSharedInputState);
/*  88 */     this.caseSensitiveLiterals = true;
/*  89 */     setCaseSensitive(true);
/*  90 */     this.literals = new Hashtable();
/*     */   }
/*     */ 
/*     */   public Token nextToken() throws TokenStreamException {
/*  94 */     Token localToken = null;
/*     */     while (true)
/*     */     {
/*  97 */       Object localObject = null;
/*  98 */       int i = 0;
/*  99 */       resetText();
/*     */       try
/*     */       {
/* 103 */         mACTION(true);
/* 104 */         localToken = this._returnToken;
/*     */ 
/* 107 */         if (this._returnToken == null) continue;
/* 108 */         i = this._returnToken.getType();
/* 109 */         this._returnToken.setType(i);
/* 110 */         return this._returnToken;
/*     */       }
/*     */       catch (RecognitionException localRecognitionException) {
/* 113 */         throw new TokenStreamRecognitionException(localRecognitionException);
/*     */       }
/*     */       catch (CharStreamException localCharStreamException)
/*     */       {
/* 117 */         if ((localCharStreamException instanceof CharStreamIOException)) {
/* 118 */           throw new TokenStreamIOException(((CharStreamIOException)localCharStreamException).io);
/*     */         }
/*     */ 
/* 121 */         throw new TokenStreamException(localCharStreamException.getMessage());
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public final void mACTION(boolean paramBoolean) throws RecognitionException, CharStreamException, TokenStreamException
/*     */   {
/* 128 */     Token localToken = null; int j = this.text.length();
/* 129 */     int i = 4;
/*     */ 
/* 135 */     while ((LA(1) >= '\003') && (LA(1) <= 'ÿ')) {
/* 136 */       mSTUFF(false);
/*     */     }
/*     */ 
/* 144 */     if ((paramBoolean) && (localToken == null) && (i != -1)) {
/* 145 */       localToken = makeToken(i);
/* 146 */       localToken.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/*     */     }
/* 148 */     this._returnToken = localToken;
/*     */   }
/*     */ 
/*     */   protected final void mSTUFF(boolean paramBoolean) throws RecognitionException, CharStreamException, TokenStreamException {
/* 152 */     Token localToken = null; int j = this.text.length();
/* 153 */     int i = 5;
/*     */ 
/* 156 */     if ((LA(1) == '/') && ((LA(2) == '*') || (LA(2) == '/'))) {
/* 157 */       mCOMMENT(false);
/*     */     }
/* 159 */     else if ((LA(1) == '\r') && (LA(2) == '\n')) {
/* 160 */       match("\r\n");
/* 161 */       newline();
/*     */     }
/* 163 */     else if ((LA(1) == '/') && (_tokenSet_0.member(LA(2)))) {
/* 164 */       match('/');
/*     */ 
/* 166 */       match(_tokenSet_0);
/*     */     }
/* 169 */     else if (LA(1) == '\r') {
/* 170 */       match('\r');
/* 171 */       newline();
/*     */     }
/* 173 */     else if (LA(1) == '\n') {
/* 174 */       match('\n');
/* 175 */       newline();
/*     */     }
/* 177 */     else if (_tokenSet_1.member(LA(1)))
/*     */     {
/* 179 */       match(_tokenSet_1);
/*     */     }
/*     */     else
/*     */     {
/* 183 */       throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*     */     }
/*     */ 
/* 186 */     if ((paramBoolean) && (localToken == null) && (i != -1)) {
/* 187 */       localToken = makeToken(i);
/* 188 */       localToken.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/*     */     }
/* 190 */     this._returnToken = localToken;
/*     */   }
/*     */ 
/*     */   protected final void mCOMMENT(boolean paramBoolean) throws RecognitionException, CharStreamException, TokenStreamException {
/* 194 */     Token localToken = null; int j = this.text.length();
/* 195 */     int i = 6;
/*     */ 
/* 198 */     if ((LA(1) == '/') && (LA(2) == '/')) {
/* 199 */       mSL_COMMENT(false);
/*     */     }
/* 201 */     else if ((LA(1) == '/') && (LA(2) == '*')) {
/* 202 */       mML_COMMENT(false);
/*     */     }
/*     */     else {
/* 205 */       throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*     */     }
/*     */ 
/* 208 */     if ((paramBoolean) && (localToken == null) && (i != -1)) {
/* 209 */       localToken = makeToken(i);
/* 210 */       localToken.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/*     */     }
/* 212 */     this._returnToken = localToken;
/*     */   }
/*     */ 
/*     */   protected final void mSL_COMMENT(boolean paramBoolean) throws RecognitionException, CharStreamException, TokenStreamException {
/* 216 */     Token localToken = null; int j = this.text.length();
/* 217 */     int i = 7;
/*     */ 
/* 220 */     int k = this.text.length();
/* 221 */     match("//");
/* 222 */     this.text.setLength(k);
/*     */ 
/* 225 */     this.text.append("#");
/*     */ 
/* 231 */     while ((LA(1) != '\n') && (LA(1) != '\r') && 
/* 232 */       (LA(1) >= '\003') && (LA(1) <= 'ÿ') && (LA(2) >= '\003') && (LA(2) <= 'ÿ')) {
/* 233 */       matchNot(65535);
/*     */     }
/*     */ 
/* 242 */     if ((LA(1) == '\r') && (LA(2) == '\n')) {
/* 243 */       match("\r\n");
/*     */     }
/* 245 */     else if (LA(1) == '\n') {
/* 246 */       match('\n');
/*     */     }
/* 248 */     else if (LA(1) == '\r') {
/* 249 */       match('\r');
/*     */     }
/*     */     else {
/* 252 */       throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*     */     }
/*     */ 
/* 257 */     newline();
/*     */ 
/* 259 */     if ((paramBoolean) && (localToken == null) && (i != -1)) {
/* 260 */       localToken = makeToken(i);
/* 261 */       localToken.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/*     */     }
/* 263 */     this._returnToken = localToken;
/*     */   }
/*     */ 
/*     */   protected final void mML_COMMENT(boolean paramBoolean) throws RecognitionException, CharStreamException, TokenStreamException {
/* 267 */     Token localToken = null; int j = this.text.length();
/* 268 */     int i = 9;
/*     */ 
/* 271 */     int m = 0;
/*     */ 
/* 274 */     int k = this.text.length();
/* 275 */     match("/*");
/* 276 */     this.text.setLength(k);
/*     */ 
/* 279 */     this.text.append("#");
/*     */ 
/* 285 */     while ((LA(1) != '*') || (LA(2) != '/')) {
/* 286 */       if ((LA(1) == '\r') && (LA(2) == '\n')) {
/* 287 */         match('\r');
/* 288 */         match('\n');
/* 289 */         k = this.text.length();
/* 290 */         mIGNWS(false);
/* 291 */         this.text.setLength(k);
/*     */ 
/* 293 */         newline();
/* 294 */         this.text.append("# ");
/*     */       }
/* 297 */       else if ((LA(1) == '\r') && (LA(2) >= '\003') && (LA(2) <= 'ÿ')) {
/* 298 */         match('\r');
/* 299 */         k = this.text.length();
/* 300 */         mIGNWS(false);
/* 301 */         this.text.setLength(k);
/*     */ 
/* 303 */         newline();
/* 304 */         this.text.append("# ");
/*     */       }
/* 307 */       else if ((LA(1) == '\n') && (LA(2) >= '\003') && (LA(2) <= 'ÿ')) {
/* 308 */         match('\n');
/* 309 */         k = this.text.length();
/* 310 */         mIGNWS(false);
/* 311 */         this.text.setLength(k);
/*     */ 
/* 313 */         newline();
/* 314 */         this.text.append("# ");
/*     */       }
/*     */       else {
/* 317 */         if ((LA(1) < '\003') || (LA(1) > 'ÿ') || (LA(2) < '\003') || (LA(2) > 'ÿ')) break;
/* 318 */         matchNot(65535);
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 328 */     this.text.append("\n");
/*     */ 
/* 330 */     k = this.text.length();
/* 331 */     match("*/");
/* 332 */     this.text.setLength(k);
/* 333 */     if ((paramBoolean) && (localToken == null) && (i != -1)) {
/* 334 */       localToken = makeToken(i);
/* 335 */       localToken.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/*     */     }
/* 337 */     this._returnToken = localToken;
/*     */   }
/*     */ 
/*     */   protected final void mIGNWS(boolean paramBoolean) throws RecognitionException, CharStreamException, TokenStreamException {
/* 341 */     Token localToken = null; int j = this.text.length();
/* 342 */     int i = 8;
/*     */     while (true)
/*     */     {
/* 348 */       if ((LA(1) == ' ') && (LA(2) >= '\003') && (LA(2) <= 'ÿ')) {
/* 349 */         match(' ');
/*     */       } else {
/* 351 */         if ((LA(1) != '\t') || (LA(2) < '\003') || (LA(2) > 'ÿ')) break;
/* 352 */         match('\t');
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 360 */     if ((paramBoolean) && (localToken == null) && (i != -1)) {
/* 361 */       localToken = makeToken(i);
/* 362 */       localToken.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/*     */     }
/* 364 */     this._returnToken = localToken;
/*     */   }
/*     */ 
/*     */   private static final long[] mk_tokenSet_0()
/*     */   {
/* 369 */     long[] arrayOfLong = new long[8];
/* 370 */     arrayOfLong[0] = -145135534866440L;
/* 371 */     for (int i = 1; i <= 3; i++) arrayOfLong[i] = -1L;
/* 372 */     return arrayOfLong;
/*     */   }
/*     */ 
/*     */   private static final long[] mk_tokenSet_1() {
/* 376 */     long[] arrayOfLong = new long[8];
/* 377 */     arrayOfLong[0] = -140737488364552L;
/* 378 */     for (int i = 1; i <= 3; i++) arrayOfLong[i] = -1L;
/* 379 */     return arrayOfLong;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     antlr.actions.python.CodeLexer
 * JD-Core Version:    0.6.2
 */