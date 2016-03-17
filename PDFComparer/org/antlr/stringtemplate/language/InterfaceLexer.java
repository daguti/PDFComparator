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
/*     */ 
/*     */ public class InterfaceLexer extends CharScanner
/*     */   implements InterfaceParserTokenTypes, TokenStream
/*     */ {
/* 516 */   public static final BitSet _tokenSet_0 = new BitSet(mk_tokenSet_0());
/*     */ 
/*     */   public InterfaceLexer(InputStream in)
/*     */   {
/*  61 */     this(new ByteBuffer(in));
/*     */   }
/*     */   public InterfaceLexer(Reader in) {
/*  64 */     this(new CharBuffer(in));
/*     */   }
/*     */   public InterfaceLexer(InputBuffer ib) {
/*  67 */     this(new LexerSharedInputState(ib));
/*     */   }
/*     */   public InterfaceLexer(LexerSharedInputState state) {
/*  70 */     super(state);
/*  71 */     this.caseSensitiveLiterals = true;
/*  72 */     setCaseSensitive(true);
/*  73 */     this.literals = new Hashtable();
/*  74 */     this.literals.put(new ANTLRHashString("interface", this), new Integer(4));
/*  75 */     this.literals.put(new ANTLRHashString("optional", this), new Integer(7));
/*     */   }
/*     */ 
/*     */   public Token nextToken() throws TokenStreamException {
/*  79 */     Token theRetToken = null;
/*     */     while (true)
/*     */     {
/*  82 */       Token _token = null;
/*  83 */       int _ttype = 0;
/*  84 */       resetText();
/*     */       try
/*     */       {
/*  87 */         switch (LA(1)) { case 'A':
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
/* 103 */           mID(true);
/* 104 */           theRetToken = this._returnToken;
/* 105 */           break;
/*     */         case '(':
/* 109 */           mLPAREN(true);
/* 110 */           theRetToken = this._returnToken;
/* 111 */           break;
/*     */         case ')':
/* 115 */           mRPAREN(true);
/* 116 */           theRetToken = this._returnToken;
/* 117 */           break;
/*     */         case ',':
/* 121 */           mCOMMA(true);
/* 122 */           theRetToken = this._returnToken;
/* 123 */           break;
/*     */         case ';':
/* 127 */           mSEMI(true);
/* 128 */           theRetToken = this._returnToken;
/* 129 */           break;
/*     */         case ':':
/* 133 */           mCOLON(true);
/* 134 */           theRetToken = this._returnToken;
/* 135 */           break;
/*     */         case '\t':
/*     */         case '\n':
/*     */         case '\f':
/*     */         case '\r':
/*     */         case ' ':
/* 140 */           mWS(true);
/* 141 */           theRetToken = this._returnToken;
/* 142 */           break;
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
/*     */         case '"':
/*     */         case '#':
/*     */         case '$':
/*     */         case '%':
/*     */         case '&':
/*     */         case '\'':
/*     */         case '*':
/*     */         case '+':
/*     */         case '-':
/*     */         case '.':
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
/*     */         case '<':
/*     */         case '=':
/*     */         case '>':
/*     */         case '?':
/*     */         case '@':
/*     */         case '[':
/*     */         case '\\':
/*     */         case ']':
/*     */         case '^':
/*     */         case '`':
/*     */         default:
/* 145 */           if ((LA(1) == '/') && (LA(2) == '/')) {
/* 146 */             mSL_COMMENT(true);
/* 147 */             theRetToken = this._returnToken;
/*     */           }
/* 149 */           else if ((LA(1) == '/') && (LA(2) == '*')) {
/* 150 */             mML_COMMENT(true);
/* 151 */             theRetToken = this._returnToken;
/*     */           }
/* 154 */           else if (LA(1) == 65535) { uponEOF(); this._returnToken = makeToken(1); } else {
/* 155 */             throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*     */           }
/*     */           break; }
/* 158 */         if (this._returnToken == null) continue;
/* 159 */         _ttype = this._returnToken.getType();
/* 160 */         _ttype = testLiteralsTable(_ttype);
/* 161 */         this._returnToken.setType(_ttype);
/* 162 */         return this._returnToken;
/*     */       }
/*     */       catch (RecognitionException e) {
/* 165 */         throw new TokenStreamRecognitionException(e);
/*     */       }
/*     */       catch (CharStreamException cse)
/*     */       {
/* 169 */         if ((cse instanceof CharStreamIOException)) {
/* 170 */           throw new TokenStreamIOException(((CharStreamIOException)cse).io);
/*     */         }
/*     */ 
/* 173 */         throw new TokenStreamException(cse.getMessage());
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public final void mID(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException
/*     */   {
/* 180 */     Token _token = null; int _begin = this.text.length();
/* 181 */     int _ttype = 5;
/*     */ 
/* 185 */     switch (LA(1)) { case 'a':
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
/* 194 */       matchRange('a', 'z');
/* 195 */       break;
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
/* 205 */       matchRange('A', 'Z');
/* 206 */       break;
/*     */     case '_':
/* 210 */       match('_');
/* 211 */       break;
/*     */     case '[':
/*     */     case '\\':
/*     */     case ']':
/*     */     case '^':
/*     */     case '`':
/*     */     default:
/* 215 */       throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*     */     }
/*     */ 
/*     */     while (true)
/*     */     {
/* 222 */       switch (LA(1)) { case 'a':
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
/* 231 */         matchRange('a', 'z');
/* 232 */         break;
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
/* 242 */         matchRange('A', 'Z');
/* 243 */         break;
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
/* 249 */         matchRange('0', '9');
/* 250 */         break;
/*     */       case '-':
/* 254 */         match('-');
/* 255 */         break;
/*     */       case '_':
/* 259 */         match('_');
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
/* 269 */       case '`': }  } if ((_createToken) && (_token == null) && (_ttype != -1)) {
/* 270 */       _token = makeToken(_ttype);
/* 271 */       _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
/*     */     }
/* 273 */     this._returnToken = _token;
/*     */   }
/*     */ 
/*     */   public final void mLPAREN(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
/* 277 */     Token _token = null; int _begin = this.text.length();
/* 278 */     int _ttype = 8;
/*     */ 
/* 281 */     match('(');
/* 282 */     if ((_createToken) && (_token == null) && (_ttype != -1)) {
/* 283 */       _token = makeToken(_ttype);
/* 284 */       _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
/*     */     }
/* 286 */     this._returnToken = _token;
/*     */   }
/*     */ 
/*     */   public final void mRPAREN(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
/* 290 */     Token _token = null; int _begin = this.text.length();
/* 291 */     int _ttype = 9;
/*     */ 
/* 294 */     match(')');
/* 295 */     if ((_createToken) && (_token == null) && (_ttype != -1)) {
/* 296 */       _token = makeToken(_ttype);
/* 297 */       _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
/*     */     }
/* 299 */     this._returnToken = _token;
/*     */   }
/*     */ 
/*     */   public final void mCOMMA(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
/* 303 */     Token _token = null; int _begin = this.text.length();
/* 304 */     int _ttype = 10;
/*     */ 
/* 307 */     match(',');
/* 308 */     if ((_createToken) && (_token == null) && (_ttype != -1)) {
/* 309 */       _token = makeToken(_ttype);
/* 310 */       _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
/*     */     }
/* 312 */     this._returnToken = _token;
/*     */   }
/*     */ 
/*     */   public final void mSEMI(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
/* 316 */     Token _token = null; int _begin = this.text.length();
/* 317 */     int _ttype = 6;
/*     */ 
/* 320 */     match(';');
/* 321 */     if ((_createToken) && (_token == null) && (_ttype != -1)) {
/* 322 */       _token = makeToken(_ttype);
/* 323 */       _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
/*     */     }
/* 325 */     this._returnToken = _token;
/*     */   }
/*     */ 
/*     */   public final void mCOLON(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
/* 329 */     Token _token = null; int _begin = this.text.length();
/* 330 */     int _ttype = 11;
/*     */ 
/* 333 */     match(':');
/* 334 */     if ((_createToken) && (_token == null) && (_ttype != -1)) {
/* 335 */       _token = makeToken(_ttype);
/* 336 */       _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
/*     */     }
/* 338 */     this._returnToken = _token;
/*     */   }
/*     */ 
/*     */   public final void mSL_COMMENT(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
/* 342 */     Token _token = null; int _begin = this.text.length();
/* 343 */     int _ttype = 12;
/*     */ 
/* 346 */     match("//");
/*     */ 
/* 350 */     while (_tokenSet_0.member(LA(1)))
/*     */     {
/* 352 */       match(_tokenSet_0);
/*     */     }
/*     */ 
/* 362 */     if ((LA(1) == '\n') || (LA(1) == '\r'))
/*     */     {
/* 364 */       switch (LA(1))
/*     */       {
/*     */       case '\r':
/* 367 */         match('\r');
/* 368 */         break;
/*     */       case '\n':
/* 372 */         break;
/*     */       default:
/* 376 */         throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*     */       }
/*     */ 
/* 380 */       match('\n');
/*     */     }
/*     */ 
/* 386 */     _ttype = -1; newline();
/* 387 */     if ((_createToken) && (_token == null) && (_ttype != -1)) {
/* 388 */       _token = makeToken(_ttype);
/* 389 */       _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
/*     */     }
/* 391 */     this._returnToken = _token;
/*     */   }
/*     */ 
/*     */   public final void mML_COMMENT(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
/* 395 */     Token _token = null; int _begin = this.text.length();
/* 396 */     int _ttype = 13;
/*     */ 
/* 399 */     match("/*");
/*     */ 
/* 404 */     while ((LA(1) != '*') || (LA(2) != '/')) {
/* 405 */       if (((LA(1) == '\n') || (LA(1) == '\r')) && (LA(2) >= 0) && (LA(2) <= 65534))
/*     */       {
/* 407 */         switch (LA(1))
/*     */         {
/*     */         case '\r':
/* 410 */           match('\r');
/* 411 */           break;
/*     */         case '\n':
/* 415 */           break;
/*     */         default:
/* 419 */           throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*     */         }
/*     */ 
/* 423 */         match('\n');
/* 424 */         newline();
/*     */       } else {
/* 426 */         if ((LA(1) < 0) || (LA(1) > 65534) || (LA(2) < 0) || (LA(2) > 65534)) break;
/* 427 */         matchNot(65535);
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 435 */     match("*/");
/* 436 */     _ttype = -1;
/* 437 */     if ((_createToken) && (_token == null) && (_ttype != -1)) {
/* 438 */       _token = makeToken(_ttype);
/* 439 */       _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
/*     */     }
/* 441 */     this._returnToken = _token;
/*     */   }
/*     */ 
/*     */   public final void mWS(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
/* 445 */     Token _token = null; int _begin = this.text.length();
/* 446 */     int _ttype = 14;
/*     */ 
/* 450 */     int _cnt32 = 0;
/*     */     while (true)
/*     */     {
/* 453 */       switch (LA(1))
/*     */       {
/*     */       case ' ':
/* 456 */         match(' ');
/* 457 */         break;
/*     */       case '\t':
/* 461 */         match('\t');
/* 462 */         break;
/*     */       case '\f':
/* 466 */         match('\f');
/* 467 */         break;
/*     */       case '\n':
/*     */       case '\r':
/* 472 */         switch (LA(1))
/*     */         {
/*     */         case '\r':
/* 475 */           match('\r');
/* 476 */           break;
/*     */         case '\n':
/* 480 */           break;
/*     */         default:
/* 484 */           throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*     */         }
/*     */ 
/* 488 */         match('\n');
/* 489 */         newline();
/* 490 */         break;
/*     */       default:
/* 494 */         if (_cnt32 >= 1) break label222; throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*     */       }
/*     */ 
/* 497 */       _cnt32++;
/*     */     }
/*     */ 
/* 500 */     label222: _ttype = -1;
/* 501 */     if ((_createToken) && (_token == null) && (_ttype != -1)) {
/* 502 */       _token = makeToken(_ttype);
/* 503 */       _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
/*     */     }
/* 505 */     this._returnToken = _token;
/*     */   }
/*     */ 
/*     */   private static final long[] mk_tokenSet_0()
/*     */   {
/* 510 */     long[] data = new long[2048];
/* 511 */     data[0] = -9217L;
/* 512 */     for (int i = 1; i <= 1022; i++) data[i] = -1L;
/* 513 */     data[1023] = 9223372036854775807L;
/* 514 */     return data;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.antlr.stringtemplate.language.InterfaceLexer
 * JD-Core Version:    0.6.2
 */