/*     */ package antlr;
/*     */ 
/*     */ import antlr.collections.impl.BitSet;
/*     */ import java.io.InputStream;
/*     */ import java.io.Reader;
/*     */ import java.util.Hashtable;
/*     */ 
/*     */ public class ANTLRTokdefLexer extends CharScanner
/*     */   implements ANTLRTokdefParserTokenTypes, TokenStream
/*     */ {
/* 640 */   public static final BitSet _tokenSet_0 = new BitSet(mk_tokenSet_0());
/*     */ 
/* 647 */   public static final BitSet _tokenSet_1 = new BitSet(mk_tokenSet_1());
/*     */ 
/* 654 */   public static final BitSet _tokenSet_2 = new BitSet(mk_tokenSet_2());
/*     */ 
/* 662 */   public static final BitSet _tokenSet_3 = new BitSet(mk_tokenSet_3());
/*     */ 
/*     */   public ANTLRTokdefLexer(InputStream paramInputStream)
/*     */   {
/*  30 */     this(new ByteBuffer(paramInputStream));
/*     */   }
/*     */   public ANTLRTokdefLexer(Reader paramReader) {
/*  33 */     this(new CharBuffer(paramReader));
/*     */   }
/*     */   public ANTLRTokdefLexer(InputBuffer paramInputBuffer) {
/*  36 */     this(new LexerSharedInputState(paramInputBuffer));
/*     */   }
/*     */   public ANTLRTokdefLexer(LexerSharedInputState paramLexerSharedInputState) {
/*  39 */     super(paramLexerSharedInputState);
/*  40 */     this.caseSensitiveLiterals = true;
/*  41 */     setCaseSensitive(true);
/*  42 */     this.literals = new Hashtable();
/*     */   }
/*     */ 
/*     */   public Token nextToken() throws TokenStreamException {
/*  46 */     Token localToken = null;
/*     */     while (true)
/*     */     {
/*  49 */       Object localObject = null;
/*  50 */       int i = 0;
/*  51 */       resetText();
/*     */       try
/*     */       {
/*  54 */         switch (LA(1)) { case '\t':
/*     */         case '\n':
/*     */         case '\r':
/*     */         case ' ':
/*  57 */           mWS(true);
/*  58 */           localToken = this._returnToken;
/*  59 */           break;
/*     */         case '(':
/*  63 */           mLPAREN(true);
/*  64 */           localToken = this._returnToken;
/*  65 */           break;
/*     */         case ')':
/*  69 */           mRPAREN(true);
/*  70 */           localToken = this._returnToken;
/*  71 */           break;
/*     */         case '=':
/*  75 */           mASSIGN(true);
/*  76 */           localToken = this._returnToken;
/*  77 */           break;
/*     */         case '"':
/*  81 */           mSTRING(true);
/*  82 */           localToken = this._returnToken;
/*  83 */           break;
/*     */         case 'A':
/*     */         case 'B':
/*     */         case 'C':
/*     */         case 'D':
/*     */         case 'E':
/*     */         case 'F':
/*     */         case 'G':
/*     */         case 'H':
/*     */         case 'I':
/*     */         case 'J':
/*     */         case 'K':
/*     */         case 'L':
/*     */         case 'M':
/*     */         case 'N':
/*     */         case 'O':
/*     */         case 'P':
/*     */         case 'Q':
/*     */         case 'R':
/*     */         case 'S':
/*     */         case 'T':
/*     */         case 'U':
/*     */         case 'V':
/*     */         case 'W':
/*     */         case 'X':
/*     */         case 'Y':
/*     */         case 'Z':
/*     */         case 'a':
/*     */         case 'b':
/*     */         case 'c':
/*     */         case 'd':
/*     */         case 'e':
/*     */         case 'f':
/*     */         case 'g':
/*     */         case 'h':
/*     */         case 'i':
/*     */         case 'j':
/*     */         case 'k':
/*     */         case 'l':
/*     */         case 'm':
/*     */         case 'n':
/*     */         case 'o':
/*     */         case 'p':
/*     */         case 'q':
/*     */         case 'r':
/*     */         case 's':
/*     */         case 't':
/*     */         case 'u':
/*     */         case 'v':
/*     */         case 'w':
/*     */         case 'x':
/*     */         case 'y':
/*     */         case 'z':
/*  99 */           mID(true);
/* 100 */           localToken = this._returnToken;
/* 101 */           break;
/*     */         case '0':
/*     */         case '1':
/*     */         case '2':
/*     */         case '3':
/*     */         case '4':
/*     */         case '5':
/*     */         case '6':
/*     */         case '7':
/*     */         case '8':
/*     */         case '9':
/* 107 */           mINT(true);
/* 108 */           localToken = this._returnToken;
/* 109 */           break;
/*     */         case '\013':
/*     */         case '\f':
/*     */         case '\016':
/*     */         case '\017':
/*     */         case '\020':
/*     */         case '\021':
/*     */         case '\022':
/*     */         case '\023':
/*     */         case '\024':
/*     */         case '\025':
/*     */         case '\026':
/*     */         case '\027':
/*     */         case '\030':
/*     */         case '\031':
/*     */         case '\032':
/*     */         case '\033':
/*     */         case '\034':
/*     */         case '\035':
/*     */         case '\036':
/*     */         case '\037':
/*     */         case '!':
/*     */         case '#':
/*     */         case '$':
/*     */         case '%':
/*     */         case '&':
/*     */         case '\'':
/*     */         case '*':
/*     */         case '+':
/*     */         case ',':
/*     */         case '-':
/*     */         case '.':
/*     */         case '/':
/*     */         case ':':
/*     */         case ';':
/*     */         case '<':
/*     */         case '>':
/*     */         case '?':
/*     */         case '@':
/*     */         case '[':
/*     */         case '\\':
/*     */         case ']':
/*     */         case '^':
/*     */         case '_':
/*     */         case '`':
/*     */         default:
/* 112 */           if ((LA(1) == '/') && (LA(2) == '/')) {
/* 113 */             mSL_COMMENT(true);
/* 114 */             localToken = this._returnToken;
/*     */           }
/* 116 */           else if ((LA(1) == '/') && (LA(2) == '*')) {
/* 117 */             mML_COMMENT(true);
/* 118 */             localToken = this._returnToken;
/*     */           }
/* 121 */           else if (LA(1) == 65535) { uponEOF(); this._returnToken = makeToken(1); } else {
/* 122 */             throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*     */           }
/*     */           break; }
/* 125 */         if (this._returnToken == null) continue;
/* 126 */         i = this._returnToken.getType();
/* 127 */         this._returnToken.setType(i);
/* 128 */         return this._returnToken;
/*     */       }
/*     */       catch (RecognitionException localRecognitionException) {
/* 131 */         throw new TokenStreamRecognitionException(localRecognitionException);
/*     */       }
/*     */       catch (CharStreamException localCharStreamException)
/*     */       {
/* 135 */         if ((localCharStreamException instanceof CharStreamIOException)) {
/* 136 */           throw new TokenStreamIOException(((CharStreamIOException)localCharStreamException).io);
/*     */         }
/*     */ 
/* 139 */         throw new TokenStreamException(localCharStreamException.getMessage());
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public final void mWS(boolean paramBoolean) throws RecognitionException, CharStreamException, TokenStreamException
/*     */   {
/* 146 */     Token localToken = null; int j = this.text.length();
/* 147 */     int i = 10;
/*     */ 
/* 151 */     switch (LA(1))
/*     */     {
/*     */     case ' ':
/* 154 */       match(' ');
/* 155 */       break;
/*     */     case '\t':
/* 159 */       match('\t');
/* 160 */       break;
/*     */     case '\r':
/* 164 */       match('\r');
/*     */ 
/* 166 */       if (LA(1) == '\n') {
/* 167 */         match('\n');
/*     */       }
/*     */ 
/* 173 */       newline();
/* 174 */       break;
/*     */     case '\n':
/* 178 */       match('\n');
/* 179 */       newline();
/* 180 */       break;
/*     */     default:
/* 184 */       throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*     */     }
/*     */ 
/* 188 */     i = -1;
/* 189 */     if ((paramBoolean) && (localToken == null) && (i != -1)) {
/* 190 */       localToken = makeToken(i);
/* 191 */       localToken.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/*     */     }
/* 193 */     this._returnToken = localToken;
/*     */   }
/*     */ 
/*     */   public final void mSL_COMMENT(boolean paramBoolean) throws RecognitionException, CharStreamException, TokenStreamException {
/* 197 */     Token localToken = null; int j = this.text.length();
/* 198 */     int i = 11;
/*     */ 
/* 201 */     match("//");
/*     */ 
/* 205 */     while (_tokenSet_0.member(LA(1)))
/*     */     {
/* 207 */       match(_tokenSet_0);
/*     */     }
/*     */ 
/* 217 */     switch (LA(1))
/*     */     {
/*     */     case '\n':
/* 220 */       match('\n');
/* 221 */       break;
/*     */     case '\r':
/* 225 */       match('\r');
/*     */ 
/* 227 */       if (LA(1) == '\n')
/* 228 */         match('\n'); break;
/*     */     default:
/* 238 */       throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*     */     }
/*     */ 
/* 242 */     i = -1; newline();
/* 243 */     if ((paramBoolean) && (localToken == null) && (i != -1)) {
/* 244 */       localToken = makeToken(i);
/* 245 */       localToken.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/*     */     }
/* 247 */     this._returnToken = localToken;
/*     */   }
/*     */ 
/*     */   public final void mML_COMMENT(boolean paramBoolean) throws RecognitionException, CharStreamException, TokenStreamException {
/* 251 */     Token localToken = null; int j = this.text.length();
/* 252 */     int i = 12;
/*     */ 
/* 255 */     match("/*");
/*     */     while (true)
/*     */     {
/* 259 */       if ((LA(1) == '*') && (_tokenSet_1.member(LA(2)))) {
/* 260 */         match('*');
/* 261 */         matchNot('/');
/*     */       }
/* 263 */       else if (LA(1) == '\n') {
/* 264 */         match('\n');
/* 265 */         newline();
/*     */       } else {
/* 267 */         if (!_tokenSet_2.member(LA(1))) break;
/* 268 */         matchNot('*');
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 276 */     match("*/");
/* 277 */     i = -1;
/* 278 */     if ((paramBoolean) && (localToken == null) && (i != -1)) {
/* 279 */       localToken = makeToken(i);
/* 280 */       localToken.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/*     */     }
/* 282 */     this._returnToken = localToken;
/*     */   }
/*     */ 
/*     */   public final void mLPAREN(boolean paramBoolean) throws RecognitionException, CharStreamException, TokenStreamException {
/* 286 */     Token localToken = null; int j = this.text.length();
/* 287 */     int i = 7;
/*     */ 
/* 290 */     match('(');
/* 291 */     if ((paramBoolean) && (localToken == null) && (i != -1)) {
/* 292 */       localToken = makeToken(i);
/* 293 */       localToken.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/*     */     }
/* 295 */     this._returnToken = localToken;
/*     */   }
/*     */ 
/*     */   public final void mRPAREN(boolean paramBoolean) throws RecognitionException, CharStreamException, TokenStreamException {
/* 299 */     Token localToken = null; int j = this.text.length();
/* 300 */     int i = 8;
/*     */ 
/* 303 */     match(')');
/* 304 */     if ((paramBoolean) && (localToken == null) && (i != -1)) {
/* 305 */       localToken = makeToken(i);
/* 306 */       localToken.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/*     */     }
/* 308 */     this._returnToken = localToken;
/*     */   }
/*     */ 
/*     */   public final void mASSIGN(boolean paramBoolean) throws RecognitionException, CharStreamException, TokenStreamException {
/* 312 */     Token localToken = null; int j = this.text.length();
/* 313 */     int i = 6;
/*     */ 
/* 316 */     match('=');
/* 317 */     if ((paramBoolean) && (localToken == null) && (i != -1)) {
/* 318 */       localToken = makeToken(i);
/* 319 */       localToken.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/*     */     }
/* 321 */     this._returnToken = localToken;
/*     */   }
/*     */ 
/*     */   public final void mSTRING(boolean paramBoolean) throws RecognitionException, CharStreamException, TokenStreamException {
/* 325 */     Token localToken = null; int j = this.text.length();
/* 326 */     int i = 5;
/*     */ 
/* 329 */     match('"');
/*     */     while (true)
/*     */     {
/* 333 */       if (LA(1) == '\\') {
/* 334 */         mESC(false);
/*     */       } else {
/* 336 */         if (!_tokenSet_3.member(LA(1))) break;
/* 337 */         matchNot('"');
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 345 */     match('"');
/* 346 */     if ((paramBoolean) && (localToken == null) && (i != -1)) {
/* 347 */       localToken = makeToken(i);
/* 348 */       localToken.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/*     */     }
/* 350 */     this._returnToken = localToken;
/*     */   }
/*     */ 
/*     */   protected final void mESC(boolean paramBoolean) throws RecognitionException, CharStreamException, TokenStreamException {
/* 354 */     Token localToken = null; int j = this.text.length();
/* 355 */     int i = 13;
/*     */ 
/* 358 */     match('\\');
/*     */ 
/* 360 */     switch (LA(1))
/*     */     {
/*     */     case 'n':
/* 363 */       match('n');
/* 364 */       break;
/*     */     case 'r':
/* 368 */       match('r');
/* 369 */       break;
/*     */     case 't':
/* 373 */       match('t');
/* 374 */       break;
/*     */     case 'b':
/* 378 */       match('b');
/* 379 */       break;
/*     */     case 'f':
/* 383 */       match('f');
/* 384 */       break;
/*     */     case '"':
/* 388 */       match('"');
/* 389 */       break;
/*     */     case '\'':
/* 393 */       match('\'');
/* 394 */       break;
/*     */     case '\\':
/* 398 */       match('\\');
/* 399 */       break;
/*     */     case '0':
/*     */     case '1':
/*     */     case '2':
/*     */     case '3':
/* 404 */       matchRange('0', '3');
/*     */ 
/* 407 */       if ((LA(1) >= '0') && (LA(1) <= '9') && (LA(2) >= '\003') && (LA(2) <= 'ÿ')) {
/* 408 */         mDIGIT(false);
/*     */ 
/* 410 */         if ((LA(1) >= '0') && (LA(1) <= '9') && (LA(2) >= '\003') && (LA(2) <= 'ÿ')) {
/* 411 */           mDIGIT(false);
/*     */         }
/* 413 */         else if ((LA(1) < '\003') || (LA(1) > 'ÿ'))
/*     */         {
/* 416 */           throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*     */         }
/*     */ 
/*     */       }
/* 421 */       else if ((LA(1) < '\003') || (LA(1) > 'ÿ'))
/*     */       {
/* 424 */         throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*     */       }
/*     */ 
/*     */       break;
/*     */     case '4':
/*     */     case '5':
/*     */     case '6':
/*     */     case '7':
/* 433 */       matchRange('4', '7');
/*     */ 
/* 436 */       if ((LA(1) >= '0') && (LA(1) <= '9') && (LA(2) >= '\003') && (LA(2) <= 'ÿ')) {
/* 437 */         mDIGIT(false);
/*     */       }
/* 439 */       else if ((LA(1) < '\003') || (LA(1) > 'ÿ'))
/*     */       {
/* 442 */         throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*     */       }
/*     */ 
/*     */       break;
/*     */     case 'u':
/* 450 */       match('u');
/* 451 */       mXDIGIT(false);
/* 452 */       mXDIGIT(false);
/* 453 */       mXDIGIT(false);
/* 454 */       mXDIGIT(false);
/* 455 */       break;
/*     */     default:
/* 459 */       throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*     */     }
/*     */ 
/* 463 */     if ((paramBoolean) && (localToken == null) && (i != -1)) {
/* 464 */       localToken = makeToken(i);
/* 465 */       localToken.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/*     */     }
/* 467 */     this._returnToken = localToken;
/*     */   }
/*     */ 
/*     */   protected final void mDIGIT(boolean paramBoolean) throws RecognitionException, CharStreamException, TokenStreamException {
/* 471 */     Token localToken = null; int j = this.text.length();
/* 472 */     int i = 14;
/*     */ 
/* 475 */     matchRange('0', '9');
/* 476 */     if ((paramBoolean) && (localToken == null) && (i != -1)) {
/* 477 */       localToken = makeToken(i);
/* 478 */       localToken.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/*     */     }
/* 480 */     this._returnToken = localToken;
/*     */   }
/*     */ 
/*     */   protected final void mXDIGIT(boolean paramBoolean) throws RecognitionException, CharStreamException, TokenStreamException {
/* 484 */     Token localToken = null; int j = this.text.length();
/* 485 */     int i = 15;
/*     */ 
/* 488 */     switch (LA(1)) { case '0':
/*     */     case '1':
/*     */     case '2':
/*     */     case '3':
/*     */     case '4':
/*     */     case '5':
/*     */     case '6':
/*     */     case '7':
/*     */     case '8':
/*     */     case '9':
/* 493 */       matchRange('0', '9');
/* 494 */       break;
/*     */     case 'a':
/*     */     case 'b':
/*     */     case 'c':
/*     */     case 'd':
/*     */     case 'e':
/*     */     case 'f':
/* 499 */       matchRange('a', 'f');
/* 500 */       break;
/*     */     case 'A':
/*     */     case 'B':
/*     */     case 'C':
/*     */     case 'D':
/*     */     case 'E':
/*     */     case 'F':
/* 505 */       matchRange('A', 'F');
/* 506 */       break;
/*     */     case ':':
/*     */     case ';':
/*     */     case '<':
/*     */     case '=':
/*     */     case '>':
/*     */     case '?':
/*     */     case '@':
/*     */     case 'G':
/*     */     case 'H':
/*     */     case 'I':
/*     */     case 'J':
/*     */     case 'K':
/*     */     case 'L':
/*     */     case 'M':
/*     */     case 'N':
/*     */     case 'O':
/*     */     case 'P':
/*     */     case 'Q':
/*     */     case 'R':
/*     */     case 'S':
/*     */     case 'T':
/*     */     case 'U':
/*     */     case 'V':
/*     */     case 'W':
/*     */     case 'X':
/*     */     case 'Y':
/*     */     case 'Z':
/*     */     case '[':
/*     */     case '\\':
/*     */     case ']':
/*     */     case '^':
/*     */     case '_':
/*     */     case '`':
/*     */     default:
/* 510 */       throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*     */     }
/*     */ 
/* 513 */     if ((paramBoolean) && (localToken == null) && (i != -1)) {
/* 514 */       localToken = makeToken(i);
/* 515 */       localToken.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/*     */     }
/* 517 */     this._returnToken = localToken;
/*     */   }
/*     */ 
/*     */   public final void mID(boolean paramBoolean) throws RecognitionException, CharStreamException, TokenStreamException {
/* 521 */     Token localToken = null; int j = this.text.length();
/* 522 */     int i = 4;
/*     */ 
/* 526 */     switch (LA(1)) { case 'a':
/*     */     case 'b':
/*     */     case 'c':
/*     */     case 'd':
/*     */     case 'e':
/*     */     case 'f':
/*     */     case 'g':
/*     */     case 'h':
/*     */     case 'i':
/*     */     case 'j':
/*     */     case 'k':
/*     */     case 'l':
/*     */     case 'm':
/*     */     case 'n':
/*     */     case 'o':
/*     */     case 'p':
/*     */     case 'q':
/*     */     case 'r':
/*     */     case 's':
/*     */     case 't':
/*     */     case 'u':
/*     */     case 'v':
/*     */     case 'w':
/*     */     case 'x':
/*     */     case 'y':
/*     */     case 'z':
/* 535 */       matchRange('a', 'z');
/* 536 */       break;
/*     */     case 'A':
/*     */     case 'B':
/*     */     case 'C':
/*     */     case 'D':
/*     */     case 'E':
/*     */     case 'F':
/*     */     case 'G':
/*     */     case 'H':
/*     */     case 'I':
/*     */     case 'J':
/*     */     case 'K':
/*     */     case 'L':
/*     */     case 'M':
/*     */     case 'N':
/*     */     case 'O':
/*     */     case 'P':
/*     */     case 'Q':
/*     */     case 'R':
/*     */     case 'S':
/*     */     case 'T':
/*     */     case 'U':
/*     */     case 'V':
/*     */     case 'W':
/*     */     case 'X':
/*     */     case 'Y':
/*     */     case 'Z':
/* 546 */       matchRange('A', 'Z');
/* 547 */       break;
/*     */     case '[':
/*     */     case '\\':
/*     */     case ']':
/*     */     case '^':
/*     */     case '_':
/*     */     case '`':
/*     */     default:
/* 551 */       throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*     */     }
/*     */ 
/*     */     while (true)
/*     */     {
/* 558 */       switch (LA(1)) { case 'a':
/*     */       case 'b':
/*     */       case 'c':
/*     */       case 'd':
/*     */       case 'e':
/*     */       case 'f':
/*     */       case 'g':
/*     */       case 'h':
/*     */       case 'i':
/*     */       case 'j':
/*     */       case 'k':
/*     */       case 'l':
/*     */       case 'm':
/*     */       case 'n':
/*     */       case 'o':
/*     */       case 'p':
/*     */       case 'q':
/*     */       case 'r':
/*     */       case 's':
/*     */       case 't':
/*     */       case 'u':
/*     */       case 'v':
/*     */       case 'w':
/*     */       case 'x':
/*     */       case 'y':
/*     */       case 'z':
/* 567 */         matchRange('a', 'z');
/* 568 */         break;
/*     */       case 'A':
/*     */       case 'B':
/*     */       case 'C':
/*     */       case 'D':
/*     */       case 'E':
/*     */       case 'F':
/*     */       case 'G':
/*     */       case 'H':
/*     */       case 'I':
/*     */       case 'J':
/*     */       case 'K':
/*     */       case 'L':
/*     */       case 'M':
/*     */       case 'N':
/*     */       case 'O':
/*     */       case 'P':
/*     */       case 'Q':
/*     */       case 'R':
/*     */       case 'S':
/*     */       case 'T':
/*     */       case 'U':
/*     */       case 'V':
/*     */       case 'W':
/*     */       case 'X':
/*     */       case 'Y':
/*     */       case 'Z':
/* 578 */         matchRange('A', 'Z');
/* 579 */         break;
/*     */       case '_':
/* 583 */         match('_');
/* 584 */         break;
/*     */       case '0':
/*     */       case '1':
/*     */       case '2':
/*     */       case '3':
/*     */       case '4':
/*     */       case '5':
/*     */       case '6':
/*     */       case '7':
/*     */       case '8':
/*     */       case '9':
/* 590 */         matchRange('0', '9');
/*     */       case ':':
/*     */       case ';':
/*     */       case '<':
/*     */       case '=':
/*     */       case '>':
/*     */       case '?':
/*     */       case '@':
/*     */       case '[':
/*     */       case '\\':
/*     */       case ']':
/*     */       case '^':
/* 600 */       case '`': }  } if ((paramBoolean) && (localToken == null) && (i != -1)) {
/* 601 */       localToken = makeToken(i);
/* 602 */       localToken.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/*     */     }
/* 604 */     this._returnToken = localToken;
/*     */   }
/*     */ 
/*     */   public final void mINT(boolean paramBoolean) throws RecognitionException, CharStreamException, TokenStreamException {
/* 608 */     Token localToken = null; int j = this.text.length();
/* 609 */     int i = 9;
/*     */ 
/* 613 */     int k = 0;
/*     */     while (true)
/*     */     {
/* 616 */       if ((LA(1) >= '0') && (LA(1) <= '9')) {
/* 617 */         mDIGIT(false);
/*     */       }
/*     */       else {
/* 620 */         if (k >= 1) break; throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*     */       }
/*     */ 
/* 623 */       k++;
/*     */     }
/*     */ 
/* 626 */     if ((paramBoolean) && (localToken == null) && (i != -1)) {
/* 627 */       localToken = makeToken(i);
/* 628 */       localToken.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/*     */     }
/* 630 */     this._returnToken = localToken;
/*     */   }
/*     */ 
/*     */   private static final long[] mk_tokenSet_0()
/*     */   {
/* 635 */     long[] arrayOfLong = new long[8];
/* 636 */     arrayOfLong[0] = -9224L;
/* 637 */     for (int i = 1; i <= 3; i++) arrayOfLong[i] = -1L;
/* 638 */     return arrayOfLong;
/*     */   }
/*     */ 
/*     */   private static final long[] mk_tokenSet_1() {
/* 642 */     long[] arrayOfLong = new long[8];
/* 643 */     arrayOfLong[0] = -140737488355336L;
/* 644 */     for (int i = 1; i <= 3; i++) arrayOfLong[i] = -1L;
/* 645 */     return arrayOfLong;
/*     */   }
/*     */ 
/*     */   private static final long[] mk_tokenSet_2() {
/* 649 */     long[] arrayOfLong = new long[8];
/* 650 */     arrayOfLong[0] = -4398046512136L;
/* 651 */     for (int i = 1; i <= 3; i++) arrayOfLong[i] = -1L;
/* 652 */     return arrayOfLong;
/*     */   }
/*     */ 
/*     */   private static final long[] mk_tokenSet_3() {
/* 656 */     long[] arrayOfLong = new long[8];
/* 657 */     arrayOfLong[0] = -17179869192L;
/* 658 */     arrayOfLong[1] = -268435457L;
/* 659 */     for (int i = 2; i <= 3; i++) arrayOfLong[i] = -1L;
/* 660 */     return arrayOfLong;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     antlr.ANTLRTokdefLexer
 * JD-Core Version:    0.6.2
 */