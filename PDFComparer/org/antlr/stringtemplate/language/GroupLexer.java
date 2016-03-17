/*     */ package org.antlr.stringtemplate.language;
/*     */ 
/*     */ import antlr.ANTLRHashString;
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
/*     */ import antlr.collections.impl.BitSet;
/*     */ import java.io.InputStream;
/*     */ import java.io.Reader;
/*     */ import java.util.Hashtable;
/*     */ import java.util.List;
/*     */ 
/*     */ public class GroupLexer extends CharScanner
/*     */   implements GroupParserTokenTypes, TokenStream
/*     */ {
/* 912 */   public static final BitSet _tokenSet_0 = new BitSet(mk_tokenSet_0());
/*     */ 
/* 921 */   public static final BitSet _tokenSet_1 = new BitSet(mk_tokenSet_1());
/*     */ 
/* 929 */   public static final BitSet _tokenSet_2 = new BitSet(mk_tokenSet_2());
/*     */ 
/*     */   public GroupLexer(InputStream in)
/*     */   {
/*  61 */     this(new ByteBuffer(in));
/*     */   }
/*     */   public GroupLexer(Reader in) {
/*  64 */     this(new CharBuffer(in));
/*     */   }
/*     */   public GroupLexer(InputBuffer ib) {
/*  67 */     this(new LexerSharedInputState(ib));
/*     */   }
/*     */   public GroupLexer(LexerSharedInputState state) {
/*  70 */     super(state);
/*  71 */     this.caseSensitiveLiterals = true;
/*  72 */     setCaseSensitive(true);
/*  73 */     this.literals = new Hashtable();
/*  74 */     this.literals.put(new ANTLRHashString("default", this), new Integer(21));
/*  75 */     this.literals.put(new ANTLRHashString("group", this), new Integer(4));
/*  76 */     this.literals.put(new ANTLRHashString("implements", this), new Integer(7));
/*     */   }
/*     */ 
/*     */   public Token nextToken() throws TokenStreamException {
/*  80 */     Token theRetToken = null;
/*     */     while (true)
/*     */     {
/*  83 */       Token _token = null;
/*  84 */       int _ttype = 0;
/*  85 */       resetText();
/*     */       try
/*     */       {
/*  88 */         switch (LA(1)) { case 'A':
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
/*     */         case '_':
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
/* 104 */           mID(true);
/* 105 */           theRetToken = this._returnToken;
/* 106 */           break;
/*     */         case '"':
/* 110 */           mSTRING(true);
/* 111 */           theRetToken = this._returnToken;
/* 112 */           break;
/*     */         case '<':
/* 116 */           mBIGSTRING(true);
/* 117 */           theRetToken = this._returnToken;
/* 118 */           break;
/*     */         case '{':
/* 122 */           mANONYMOUS_TEMPLATE(true);
/* 123 */           theRetToken = this._returnToken;
/* 124 */           break;
/*     */         case '@':
/* 128 */           mAT(true);
/* 129 */           theRetToken = this._returnToken;
/* 130 */           break;
/*     */         case '(':
/* 134 */           mLPAREN(true);
/* 135 */           theRetToken = this._returnToken;
/* 136 */           break;
/*     */         case ')':
/* 140 */           mRPAREN(true);
/* 141 */           theRetToken = this._returnToken;
/* 142 */           break;
/*     */         case '[':
/* 146 */           mLBRACK(true);
/* 147 */           theRetToken = this._returnToken;
/* 148 */           break;
/*     */         case ']':
/* 152 */           mRBRACK(true);
/* 153 */           theRetToken = this._returnToken;
/* 154 */           break;
/*     */         case ',':
/* 158 */           mCOMMA(true);
/* 159 */           theRetToken = this._returnToken;
/* 160 */           break;
/*     */         case '.':
/* 164 */           mDOT(true);
/* 165 */           theRetToken = this._returnToken;
/* 166 */           break;
/*     */         case ';':
/* 170 */           mSEMI(true);
/* 171 */           theRetToken = this._returnToken;
/* 172 */           break;
/*     */         case '*':
/* 176 */           mSTAR(true);
/* 177 */           theRetToken = this._returnToken;
/* 178 */           break;
/*     */         case '+':
/* 182 */           mPLUS(true);
/* 183 */           theRetToken = this._returnToken;
/* 184 */           break;
/*     */         case '=':
/* 188 */           mASSIGN(true);
/* 189 */           theRetToken = this._returnToken;
/* 190 */           break;
/*     */         case '?':
/* 194 */           mOPTIONAL(true);
/* 195 */           theRetToken = this._returnToken;
/* 196 */           break;
/*     */         case '\t':
/*     */         case '\n':
/*     */         case '\f':
/*     */         case '\r':
/*     */         case ' ':
/* 201 */           mWS(true);
/* 202 */           theRetToken = this._returnToken;
/* 203 */           break;
/*     */         case '\013':
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
/*     */         case '-':
/*     */         case '/':
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
/*     */         case ':':
/*     */         case '>':
/*     */         case '\\':
/*     */         case '^':
/*     */         case '`':
/*     */         default:
/* 206 */           if ((LA(1) == ':') && (LA(2) == ':')) {
/* 207 */             mDEFINED_TO_BE(true);
/* 208 */             theRetToken = this._returnToken;
/*     */           }
/* 210 */           else if ((LA(1) == '/') && (LA(2) == '/')) {
/* 211 */             mSL_COMMENT(true);
/* 212 */             theRetToken = this._returnToken;
/*     */           }
/* 214 */           else if ((LA(1) == '/') && (LA(2) == '*')) {
/* 215 */             mML_COMMENT(true);
/* 216 */             theRetToken = this._returnToken;
/*     */           }
/* 218 */           else if (LA(1) == ':') {
/* 219 */             mCOLON(true);
/* 220 */             theRetToken = this._returnToken;
/*     */           }
/* 223 */           else if (LA(1) == 65535) { uponEOF(); this._returnToken = makeToken(1); } else {
/* 224 */             throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*     */           }
/*     */           break; }
/* 227 */         if (this._returnToken == null) continue;
/* 228 */         _ttype = this._returnToken.getType();
/* 229 */         this._returnToken.setType(_ttype);
/* 230 */         return this._returnToken;
/*     */       }
/*     */       catch (RecognitionException e) {
/* 233 */         throw new TokenStreamRecognitionException(e);
/*     */       }
/*     */       catch (CharStreamException cse)
/*     */       {
/* 237 */         if ((cse instanceof CharStreamIOException)) {
/* 238 */           throw new TokenStreamIOException(((CharStreamIOException)cse).io);
/*     */         }
/*     */ 
/* 241 */         throw new TokenStreamException(cse.getMessage());
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public final void mID(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException
/*     */   {
/* 248 */     Token _token = null; int _begin = this.text.length();
/* 249 */     int _ttype = 5;
/*     */ 
/* 253 */     switch (LA(1)) { case 'a':
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
/* 262 */       matchRange('a', 'z');
/* 263 */       break;
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
/* 273 */       matchRange('A', 'Z');
/* 274 */       break;
/*     */     case '_':
/* 278 */       match('_');
/* 279 */       break;
/*     */     case '[':
/*     */     case '\\':
/*     */     case ']':
/*     */     case '^':
/*     */     case '`':
/*     */     default:
/* 283 */       throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*     */     }
/*     */ 
/*     */     while (true)
/*     */     {
/* 290 */       switch (LA(1)) { case 'a':
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
/* 299 */         matchRange('a', 'z');
/* 300 */         break;
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
/* 310 */         matchRange('A', 'Z');
/* 311 */         break;
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
/* 317 */         matchRange('0', '9');
/* 318 */         break;
/*     */       case '-':
/* 322 */         match('-');
/* 323 */         break;
/*     */       case '_':
/* 327 */         match('_');
/*     */       case '.':
/*     */       case '/':
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
/* 337 */       case '`': }  } _ttype = testLiteralsTable(_ttype);
/* 338 */     if ((_createToken) && (_token == null) && (_ttype != -1)) {
/* 339 */       _token = makeToken(_ttype);
/* 340 */       _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
/*     */     }
/* 342 */     this._returnToken = _token;
/*     */   }
/*     */ 
/*     */   public final void mSTRING(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
/* 346 */     Token _token = null; int _begin = this.text.length();
/* 347 */     int _ttype = 15;
/*     */ 
/* 350 */     int _saveIndex = this.text.length();
/* 351 */     match('"');
/* 352 */     this.text.setLength(_saveIndex);
/*     */     while (true)
/*     */     {
/* 356 */       if ((LA(1) == '\\') && (LA(2) == '"')) {
/* 357 */         _saveIndex = this.text.length();
/* 358 */         match('\\');
/* 359 */         this.text.setLength(_saveIndex);
/* 360 */         match('"');
/*     */       }
/* 362 */       else if ((LA(1) == '\\') && (_tokenSet_0.member(LA(2)))) {
/* 363 */         match('\\');
/* 364 */         matchNot('"');
/*     */       } else {
/* 366 */         if (!_tokenSet_1.member(LA(1))) break;
/* 367 */         matchNot('"');
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 375 */     _saveIndex = this.text.length();
/* 376 */     match('"');
/* 377 */     this.text.setLength(_saveIndex);
/* 378 */     if ((_createToken) && (_token == null) && (_ttype != -1)) {
/* 379 */       _token = makeToken(_ttype);
/* 380 */       _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
/*     */     }
/* 382 */     this._returnToken = _token;
/*     */   }
/*     */ 
/*     */   public final void mBIGSTRING(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
/* 386 */     Token _token = null; int _begin = this.text.length();
/* 387 */     int _ttype = 16;
/*     */ 
/* 390 */     int _saveIndex = this.text.length();
/* 391 */     match("<<");
/* 392 */     this.text.setLength(_saveIndex);
/*     */ 
/* 394 */     if (((LA(1) == '\n') || (LA(1) == '\r')) && (LA(2) >= 0) && (LA(2) <= 65534))
/*     */     {
/* 396 */       switch (LA(1))
/*     */       {
/*     */       case '\r':
/* 399 */         _saveIndex = this.text.length();
/* 400 */         match('\r');
/* 401 */         this.text.setLength(_saveIndex);
/* 402 */         break;
/*     */       case '\n':
/* 406 */         break;
/*     */       default:
/* 410 */         throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*     */       }
/*     */ 
/* 414 */       _saveIndex = this.text.length();
/* 415 */       match('\n');
/* 416 */       this.text.setLength(_saveIndex);
/* 417 */       newline();
/*     */     }
/* 419 */     else if ((LA(1) < 0) || (LA(1) > 65534) || (LA(2) < 0) || (LA(2) > 65534))
/*     */     {
/* 422 */       throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*     */     }
/*     */ 
/* 430 */     while ((LA(1) != '>') || (LA(2) != '>')) {
/* 431 */       if ((LA(1) == '\r') && (LA(2) == '\n') && (LA(3) == '>') && (LA(4) == '>')) {
/* 432 */         _saveIndex = this.text.length();
/* 433 */         match('\r');
/* 434 */         this.text.setLength(_saveIndex);
/* 435 */         _saveIndex = this.text.length();
/* 436 */         match('\n');
/* 437 */         this.text.setLength(_saveIndex);
/* 438 */         newline();
/*     */       }
/* 440 */       else if ((LA(1) == '\n') && (LA(2) >= 0) && (LA(2) <= 65534) && (LA(2) == '>') && (LA(3) == '>')) {
/* 441 */         _saveIndex = this.text.length();
/* 442 */         match('\n');
/* 443 */         this.text.setLength(_saveIndex);
/* 444 */         newline();
/*     */       }
/* 446 */       else if (((LA(1) == '\n') || (LA(1) == '\r')) && (LA(2) >= 0) && (LA(2) <= 65534))
/*     */       {
/* 448 */         switch (LA(1))
/*     */         {
/*     */         case '\r':
/* 451 */           match('\r');
/* 452 */           break;
/*     */         case '\n':
/* 456 */           break;
/*     */         default:
/* 460 */           throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*     */         }
/*     */ 
/* 464 */         match('\n');
/* 465 */         newline();
/*     */       }
/* 467 */       else if ((LA(1) == '\\') && (LA(2) == '>')) {
/* 468 */         _saveIndex = this.text.length();
/* 469 */         match('\\');
/* 470 */         this.text.setLength(_saveIndex);
/* 471 */         match('>');
/*     */       } else {
/* 473 */         if ((LA(1) < 0) || (LA(1) > 65534) || (LA(2) < 0) || (LA(2) > 65534)) break;
/* 474 */         matchNot(65535);
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 482 */     _saveIndex = this.text.length();
/* 483 */     match(">>");
/* 484 */     this.text.setLength(_saveIndex);
/* 485 */     if ((_createToken) && (_token == null) && (_ttype != -1)) {
/* 486 */       _token = makeToken(_ttype);
/* 487 */       _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
/*     */     }
/* 489 */     this._returnToken = _token;
/*     */   }
/*     */ 
/*     */   public final void mANONYMOUS_TEMPLATE(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
/* 493 */     Token _token = null; int _begin = this.text.length();
/* 494 */     int _ttype = 18;
/*     */ 
/* 497 */     List args = null;
/* 498 */     StringTemplateToken t = null;
/*     */ 
/* 501 */     int _saveIndex = this.text.length();
/* 502 */     match('{');
/* 503 */     this.text.setLength(_saveIndex);
/*     */ 
/* 508 */     while (LA(1) != '}') {
/* 509 */       if (((LA(1) == '\n') || (LA(1) == '\r')) && (LA(2) >= 0) && (LA(2) <= 65534))
/*     */       {
/* 511 */         switch (LA(1))
/*     */         {
/*     */         case '\r':
/* 514 */           match('\r');
/* 515 */           break;
/*     */         case '\n':
/* 519 */           break;
/*     */         default:
/* 523 */           throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*     */         }
/*     */ 
/* 527 */         match('\n');
/* 528 */         newline();
/*     */       }
/* 530 */       else if ((LA(1) == '\\') && (LA(2) == '}')) {
/* 531 */         _saveIndex = this.text.length();
/* 532 */         match('\\');
/* 533 */         this.text.setLength(_saveIndex);
/* 534 */         match('}');
/*     */       } else {
/* 536 */         if ((LA(1) < 0) || (LA(1) > 65534) || (LA(2) < 0) || (LA(2) > 65534)) break;
/* 537 */         matchNot(65535);
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 545 */     _saveIndex = this.text.length();
/* 546 */     match('}');
/* 547 */     this.text.setLength(_saveIndex);
/* 548 */     if ((_createToken) && (_token == null) && (_ttype != -1)) {
/* 549 */       _token = makeToken(_ttype);
/* 550 */       _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
/*     */     }
/* 552 */     this._returnToken = _token;
/*     */   }
/*     */ 
/*     */   public final void mAT(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
/* 556 */     Token _token = null; int _begin = this.text.length();
/* 557 */     int _ttype = 10;
/*     */ 
/* 560 */     match('@');
/* 561 */     if ((_createToken) && (_token == null) && (_ttype != -1)) {
/* 562 */       _token = makeToken(_ttype);
/* 563 */       _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
/*     */     }
/* 565 */     this._returnToken = _token;
/*     */   }
/*     */ 
/*     */   public final void mLPAREN(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
/* 569 */     Token _token = null; int _begin = this.text.length();
/* 570 */     int _ttype = 12;
/*     */ 
/* 573 */     match('(');
/* 574 */     if ((_createToken) && (_token == null) && (_ttype != -1)) {
/* 575 */       _token = makeToken(_ttype);
/* 576 */       _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
/*     */     }
/* 578 */     this._returnToken = _token;
/*     */   }
/*     */ 
/*     */   public final void mRPAREN(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
/* 582 */     Token _token = null; int _begin = this.text.length();
/* 583 */     int _ttype = 13;
/*     */ 
/* 586 */     match(')');
/* 587 */     if ((_createToken) && (_token == null) && (_ttype != -1)) {
/* 588 */       _token = makeToken(_ttype);
/* 589 */       _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
/*     */     }
/* 591 */     this._returnToken = _token;
/*     */   }
/*     */ 
/*     */   public final void mLBRACK(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
/* 595 */     Token _token = null; int _begin = this.text.length();
/* 596 */     int _ttype = 19;
/*     */ 
/* 599 */     match('[');
/* 600 */     if ((_createToken) && (_token == null) && (_ttype != -1)) {
/* 601 */       _token = makeToken(_ttype);
/* 602 */       _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
/*     */     }
/* 604 */     this._returnToken = _token;
/*     */   }
/*     */ 
/*     */   public final void mRBRACK(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
/* 608 */     Token _token = null; int _begin = this.text.length();
/* 609 */     int _ttype = 20;
/*     */ 
/* 612 */     match(']');
/* 613 */     if ((_createToken) && (_token == null) && (_ttype != -1)) {
/* 614 */       _token = makeToken(_ttype);
/* 615 */       _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
/*     */     }
/* 617 */     this._returnToken = _token;
/*     */   }
/*     */ 
/*     */   public final void mCOMMA(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
/* 621 */     Token _token = null; int _begin = this.text.length();
/* 622 */     int _ttype = 8;
/*     */ 
/* 625 */     match(',');
/* 626 */     if ((_createToken) && (_token == null) && (_ttype != -1)) {
/* 627 */       _token = makeToken(_ttype);
/* 628 */       _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
/*     */     }
/* 630 */     this._returnToken = _token;
/*     */   }
/*     */ 
/*     */   public final void mDOT(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
/* 634 */     Token _token = null; int _begin = this.text.length();
/* 635 */     int _ttype = 11;
/*     */ 
/* 638 */     match('.');
/* 639 */     if ((_createToken) && (_token == null) && (_ttype != -1)) {
/* 640 */       _token = makeToken(_ttype);
/* 641 */       _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
/*     */     }
/* 643 */     this._returnToken = _token;
/*     */   }
/*     */ 
/*     */   public final void mDEFINED_TO_BE(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
/* 647 */     Token _token = null; int _begin = this.text.length();
/* 648 */     int _ttype = 14;
/*     */ 
/* 651 */     match("::=");
/* 652 */     if ((_createToken) && (_token == null) && (_ttype != -1)) {
/* 653 */       _token = makeToken(_ttype);
/* 654 */       _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
/*     */     }
/* 656 */     this._returnToken = _token;
/*     */   }
/*     */ 
/*     */   public final void mSEMI(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
/* 660 */     Token _token = null; int _begin = this.text.length();
/* 661 */     int _ttype = 9;
/*     */ 
/* 664 */     match(';');
/* 665 */     if ((_createToken) && (_token == null) && (_ttype != -1)) {
/* 666 */       _token = makeToken(_ttype);
/* 667 */       _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
/*     */     }
/* 669 */     this._returnToken = _token;
/*     */   }
/*     */ 
/*     */   public final void mCOLON(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
/* 673 */     Token _token = null; int _begin = this.text.length();
/* 674 */     int _ttype = 6;
/*     */ 
/* 677 */     match(':');
/* 678 */     if ((_createToken) && (_token == null) && (_ttype != -1)) {
/* 679 */       _token = makeToken(_ttype);
/* 680 */       _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
/*     */     }
/* 682 */     this._returnToken = _token;
/*     */   }
/*     */ 
/*     */   public final void mSTAR(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
/* 686 */     Token _token = null; int _begin = this.text.length();
/* 687 */     int _ttype = 22;
/*     */ 
/* 690 */     match('*');
/* 691 */     if ((_createToken) && (_token == null) && (_ttype != -1)) {
/* 692 */       _token = makeToken(_ttype);
/* 693 */       _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
/*     */     }
/* 695 */     this._returnToken = _token;
/*     */   }
/*     */ 
/*     */   public final void mPLUS(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
/* 699 */     Token _token = null; int _begin = this.text.length();
/* 700 */     int _ttype = 23;
/*     */ 
/* 703 */     match('+');
/* 704 */     if ((_createToken) && (_token == null) && (_ttype != -1)) {
/* 705 */       _token = makeToken(_ttype);
/* 706 */       _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
/*     */     }
/* 708 */     this._returnToken = _token;
/*     */   }
/*     */ 
/*     */   public final void mASSIGN(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
/* 712 */     Token _token = null; int _begin = this.text.length();
/* 713 */     int _ttype = 17;
/*     */ 
/* 716 */     match('=');
/* 717 */     if ((_createToken) && (_token == null) && (_ttype != -1)) {
/* 718 */       _token = makeToken(_ttype);
/* 719 */       _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
/*     */     }
/* 721 */     this._returnToken = _token;
/*     */   }
/*     */ 
/*     */   public final void mOPTIONAL(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
/* 725 */     Token _token = null; int _begin = this.text.length();
/* 726 */     int _ttype = 24;
/*     */ 
/* 729 */     match('?');
/* 730 */     if ((_createToken) && (_token == null) && (_ttype != -1)) {
/* 731 */       _token = makeToken(_ttype);
/* 732 */       _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
/*     */     }
/* 734 */     this._returnToken = _token;
/*     */   }
/*     */ 
/*     */   public final void mSL_COMMENT(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
/* 738 */     Token _token = null; int _begin = this.text.length();
/* 739 */     int _ttype = 25;
/*     */ 
/* 742 */     match("//");
/*     */ 
/* 746 */     while (_tokenSet_2.member(LA(1)))
/*     */     {
/* 748 */       match(_tokenSet_2);
/*     */     }
/*     */ 
/* 758 */     if ((LA(1) == '\n') || (LA(1) == '\r'))
/*     */     {
/* 760 */       switch (LA(1))
/*     */       {
/*     */       case '\r':
/* 763 */         match('\r');
/* 764 */         break;
/*     */       case '\n':
/* 768 */         break;
/*     */       default:
/* 772 */         throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*     */       }
/*     */ 
/* 776 */       match('\n');
/*     */     }
/*     */ 
/* 782 */     _ttype = -1; newline();
/* 783 */     if ((_createToken) && (_token == null) && (_ttype != -1)) {
/* 784 */       _token = makeToken(_ttype);
/* 785 */       _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
/*     */     }
/* 787 */     this._returnToken = _token;
/*     */   }
/*     */ 
/*     */   public final void mML_COMMENT(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
/* 791 */     Token _token = null; int _begin = this.text.length();
/* 792 */     int _ttype = 26;
/*     */ 
/* 795 */     match("/*");
/*     */ 
/* 800 */     while ((LA(1) != '*') || (LA(2) != '/')) {
/* 801 */       if (((LA(1) == '\n') || (LA(1) == '\r')) && (LA(2) >= 0) && (LA(2) <= 65534))
/*     */       {
/* 803 */         switch (LA(1))
/*     */         {
/*     */         case '\r':
/* 806 */           match('\r');
/* 807 */           break;
/*     */         case '\n':
/* 811 */           break;
/*     */         default:
/* 815 */           throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*     */         }
/*     */ 
/* 819 */         match('\n');
/* 820 */         newline();
/*     */       } else {
/* 822 */         if ((LA(1) < 0) || (LA(1) > 65534) || (LA(2) < 0) || (LA(2) > 65534)) break;
/* 823 */         matchNot(65535);
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 831 */     match("*/");
/* 832 */     _ttype = -1;
/* 833 */     if ((_createToken) && (_token == null) && (_ttype != -1)) {
/* 834 */       _token = makeToken(_ttype);
/* 835 */       _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
/*     */     }
/* 837 */     this._returnToken = _token;
/*     */   }
/*     */ 
/*     */   public final void mWS(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
/* 841 */     Token _token = null; int _begin = this.text.length();
/* 842 */     int _ttype = 27;
/*     */ 
/* 846 */     int _cnt70 = 0;
/*     */     while (true)
/*     */     {
/* 849 */       switch (LA(1))
/*     */       {
/*     */       case ' ':
/* 852 */         match(' ');
/* 853 */         break;
/*     */       case '\t':
/* 857 */         match('\t');
/* 858 */         break;
/*     */       case '\f':
/* 862 */         match('\f');
/* 863 */         break;
/*     */       case '\n':
/*     */       case '\r':
/* 868 */         switch (LA(1))
/*     */         {
/*     */         case '\r':
/* 871 */           match('\r');
/* 872 */           break;
/*     */         case '\n':
/* 876 */           break;
/*     */         default:
/* 880 */           throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*     */         }
/*     */ 
/* 884 */         match('\n');
/* 885 */         newline();
/* 886 */         break;
/*     */       default:
/* 890 */         if (_cnt70 >= 1) break label222; throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*     */       }
/*     */ 
/* 893 */       _cnt70++;
/*     */     }
/*     */ 
/* 896 */     label222: _ttype = -1;
/* 897 */     if ((_createToken) && (_token == null) && (_ttype != -1)) {
/* 898 */       _token = makeToken(_ttype);
/* 899 */       _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
/*     */     }
/* 901 */     this._returnToken = _token;
/*     */   }
/*     */ 
/*     */   private static final long[] mk_tokenSet_0()
/*     */   {
/* 906 */     long[] data = new long[2048];
/* 907 */     data[0] = -17179869185L;
/* 908 */     for (int i = 1; i <= 1022; i++) data[i] = -1L;
/* 909 */     data[1023] = 9223372036854775807L;
/* 910 */     return data;
/*     */   }
/*     */ 
/*     */   private static final long[] mk_tokenSet_1() {
/* 914 */     long[] data = new long[2048];
/* 915 */     data[0] = -17179869185L;
/* 916 */     data[1] = -268435457L;
/* 917 */     for (int i = 2; i <= 1022; i++) data[i] = -1L;
/* 918 */     data[1023] = 9223372036854775807L;
/* 919 */     return data;
/*     */   }
/*     */ 
/*     */   private static final long[] mk_tokenSet_2() {
/* 923 */     long[] data = new long[2048];
/* 924 */     data[0] = -9217L;
/* 925 */     for (int i = 1; i <= 1022; i++) data[i] = -1L;
/* 926 */     data[1023] = 9223372036854775807L;
/* 927 */     return data;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.antlr.stringtemplate.language.GroupLexer
 * JD-Core Version:    0.6.2
 */