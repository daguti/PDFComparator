/*      */ package org.antlr.stringtemplate.language;
/*      */ 
/*      */ import antlr.ANTLRHashString;
/*      */ import antlr.ANTLRStringBuffer;
/*      */ import antlr.ByteBuffer;
/*      */ import antlr.CharBuffer;
/*      */ import antlr.CharScanner;
/*      */ import antlr.CharStreamException;
/*      */ import antlr.CharStreamIOException;
/*      */ import antlr.InputBuffer;
/*      */ import antlr.LexerSharedInputState;
/*      */ import antlr.NoViableAltForCharException;
/*      */ import antlr.RecognitionException;
/*      */ import antlr.Token;
/*      */ import antlr.TokenStream;
/*      */ import antlr.TokenStreamException;
/*      */ import antlr.TokenStreamIOException;
/*      */ import antlr.TokenStreamRecognitionException;
/*      */ import antlr.collections.impl.BitSet;
/*      */ import java.io.InputStream;
/*      */ import java.io.Reader;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Hashtable;
/*      */ import java.util.List;
/*      */ 
/*      */ public class ActionLexer extends CharScanner
/*      */   implements ActionParserTokenTypes, TokenStream
/*      */ {
/* 1009 */   public static final BitSet _tokenSet_0 = new BitSet(mk_tokenSet_0());
/*      */ 
/* 1016 */   public static final BitSet _tokenSet_1 = new BitSet(mk_tokenSet_1());
/*      */ 
/* 1023 */   public static final BitSet _tokenSet_2 = new BitSet(mk_tokenSet_2());
/*      */ 
/* 1029 */   public static final BitSet _tokenSet_3 = new BitSet(mk_tokenSet_3());
/*      */ 
/* 1038 */   public static final BitSet _tokenSet_4 = new BitSet(mk_tokenSet_4());
/*      */ 
/* 1044 */   public static final BitSet _tokenSet_5 = new BitSet(mk_tokenSet_5());
/*      */ 
/* 1051 */   public static final BitSet _tokenSet_6 = new BitSet(mk_tokenSet_6());
/*      */ 
/*      */   public ActionLexer(InputStream in)
/*      */   {
/*   61 */     this(new ByteBuffer(in));
/*      */   }
/*      */   public ActionLexer(Reader in) {
/*   64 */     this(new CharBuffer(in));
/*      */   }
/*      */   public ActionLexer(InputBuffer ib) {
/*   67 */     this(new LexerSharedInputState(ib));
/*      */   }
/*      */   public ActionLexer(LexerSharedInputState state) {
/*   70 */     super(state);
/*   71 */     this.caseSensitiveLiterals = true;
/*   72 */     setCaseSensitive(true);
/*   73 */     this.literals = new Hashtable();
/*   74 */     this.literals.put(new ANTLRHashString("super", this), new Integer(32));
/*   75 */     this.literals.put(new ANTLRHashString("if", this), new Integer(8));
/*   76 */     this.literals.put(new ANTLRHashString("first", this), new Integer(26));
/*   77 */     this.literals.put(new ANTLRHashString("last", this), new Integer(28));
/*   78 */     this.literals.put(new ANTLRHashString("rest", this), new Integer(27));
/*   79 */     this.literals.put(new ANTLRHashString("trunc", this), new Integer(31));
/*   80 */     this.literals.put(new ANTLRHashString("strip", this), new Integer(30));
/*   81 */     this.literals.put(new ANTLRHashString("length", this), new Integer(29));
/*   82 */     this.literals.put(new ANTLRHashString("elseif", this), new Integer(18));
/*      */   }
/*      */ 
/*      */   public Token nextToken() throws TokenStreamException {
/*   86 */     Token theRetToken = null;
/*      */     while (true)
/*      */     {
/*   89 */       Token _token = null;
/*   90 */       int _ttype = 0;
/*   91 */       resetText();
/*      */       try
/*      */       {
/*   94 */         switch (LA(1)) { case 'A':
/*      */         case 'B':
/*      */         case 'C':
/*      */         case 'D':
/*      */         case 'E':
/*      */         case 'F':
/*      */         case 'G':
/*      */         case 'H':
/*      */         case 'I':
/*      */         case 'J':
/*      */         case 'K':
/*      */         case 'L':
/*      */         case 'M':
/*      */         case 'N':
/*      */         case 'O':
/*      */         case 'P':
/*      */         case 'Q':
/*      */         case 'R':
/*      */         case 'S':
/*      */         case 'T':
/*      */         case 'U':
/*      */         case 'V':
/*      */         case 'W':
/*      */         case 'X':
/*      */         case 'Y':
/*      */         case 'Z':
/*      */         case '_':
/*      */         case 'a':
/*      */         case 'b':
/*      */         case 'c':
/*      */         case 'd':
/*      */         case 'e':
/*      */         case 'f':
/*      */         case 'g':
/*      */         case 'h':
/*      */         case 'i':
/*      */         case 'j':
/*      */         case 'k':
/*      */         case 'l':
/*      */         case 'm':
/*      */         case 'n':
/*      */         case 'o':
/*      */         case 'p':
/*      */         case 'q':
/*      */         case 'r':
/*      */         case 's':
/*      */         case 't':
/*      */         case 'u':
/*      */         case 'v':
/*      */         case 'w':
/*      */         case 'x':
/*      */         case 'y':
/*      */         case 'z':
/*  110 */           mID(true);
/*  111 */           theRetToken = this._returnToken;
/*  112 */           break;
/*      */         case '0':
/*      */         case '1':
/*      */         case '2':
/*      */         case '3':
/*      */         case '4':
/*      */         case '5':
/*      */         case '6':
/*      */         case '7':
/*      */         case '8':
/*      */         case '9':
/*  118 */           mINT(true);
/*  119 */           theRetToken = this._returnToken;
/*  120 */           break;
/*      */         case '"':
/*  124 */           mSTRING(true);
/*  125 */           theRetToken = this._returnToken;
/*  126 */           break;
/*      */         case '{':
/*  130 */           mANONYMOUS_TEMPLATE(true);
/*  131 */           theRetToken = this._returnToken;
/*  132 */           break;
/*      */         case '[':
/*  136 */           mLBRACK(true);
/*  137 */           theRetToken = this._returnToken;
/*  138 */           break;
/*      */         case ']':
/*  142 */           mRBRACK(true);
/*  143 */           theRetToken = this._returnToken;
/*  144 */           break;
/*      */         case '(':
/*  148 */           mLPAREN(true);
/*  149 */           theRetToken = this._returnToken;
/*  150 */           break;
/*      */         case ')':
/*  154 */           mRPAREN(true);
/*  155 */           theRetToken = this._returnToken;
/*  156 */           break;
/*      */         case ',':
/*  160 */           mCOMMA(true);
/*  161 */           theRetToken = this._returnToken;
/*  162 */           break;
/*      */         case '=':
/*  166 */           mASSIGN(true);
/*  167 */           theRetToken = this._returnToken;
/*  168 */           break;
/*      */         case ':':
/*  172 */           mCOLON(true);
/*  173 */           theRetToken = this._returnToken;
/*  174 */           break;
/*      */         case '+':
/*  178 */           mPLUS(true);
/*  179 */           theRetToken = this._returnToken;
/*  180 */           break;
/*      */         case ';':
/*  184 */           mSEMI(true);
/*  185 */           theRetToken = this._returnToken;
/*  186 */           break;
/*      */         case '!':
/*  190 */           mNOT(true);
/*  191 */           theRetToken = this._returnToken;
/*  192 */           break;
/*      */         case '\t':
/*      */         case '\n':
/*      */         case '\r':
/*      */         case ' ':
/*  196 */           mWS(true);
/*  197 */           theRetToken = this._returnToken;
/*  198 */           break;
/*      */         case '\013':
/*      */         case '\f':
/*      */         case '\016':
/*      */         case '\017':
/*      */         case '\020':
/*      */         case '\021':
/*      */         case '\022':
/*      */         case '\023':
/*      */         case '\024':
/*      */         case '\025':
/*      */         case '\026':
/*      */         case '\027':
/*      */         case '\030':
/*      */         case '\031':
/*      */         case '\032':
/*      */         case '\033':
/*      */         case '\034':
/*      */         case '\035':
/*      */         case '\036':
/*      */         case '\037':
/*      */         case '#':
/*      */         case '$':
/*      */         case '%':
/*      */         case '&':
/*      */         case '\'':
/*      */         case '*':
/*      */         case '-':
/*      */         case '.':
/*      */         case '/':
/*      */         case '<':
/*      */         case '>':
/*      */         case '?':
/*      */         case '@':
/*      */         case '\\':
/*      */         case '^':
/*      */         case '`':
/*      */         default:
/*  201 */           if ((LA(1) == '.') && (LA(2) == '.')) {
/*  202 */             mDOTDOTDOT(true);
/*  203 */             theRetToken = this._returnToken;
/*      */           }
/*  205 */           else if (LA(1) == '.') {
/*  206 */             mDOT(true);
/*  207 */             theRetToken = this._returnToken;
/*      */           }
/*  210 */           else if (LA(1) == 65535) { uponEOF(); this._returnToken = makeToken(1); } else {
/*  211 */             throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */           }
/*      */           break; }
/*  214 */         if (this._returnToken == null) continue;
/*  215 */         _ttype = this._returnToken.getType();
/*  216 */         this._returnToken.setType(_ttype);
/*  217 */         return this._returnToken;
/*      */       }
/*      */       catch (RecognitionException e) {
/*  220 */         throw new TokenStreamRecognitionException(e);
/*      */       }
/*      */       catch (CharStreamException cse)
/*      */       {
/*  224 */         if ((cse instanceof CharStreamIOException)) {
/*  225 */           throw new TokenStreamIOException(((CharStreamIOException)cse).io);
/*      */         }
/*      */ 
/*  228 */         throw new TokenStreamException(cse.getMessage());
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   public final void mID(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException
/*      */   {
/*  235 */     Token _token = null; int _begin = this.text.length();
/*  236 */     int _ttype = 20;
/*      */ 
/*  240 */     switch (LA(1)) { case 'a':
/*      */     case 'b':
/*      */     case 'c':
/*      */     case 'd':
/*      */     case 'e':
/*      */     case 'f':
/*      */     case 'g':
/*      */     case 'h':
/*      */     case 'i':
/*      */     case 'j':
/*      */     case 'k':
/*      */     case 'l':
/*      */     case 'm':
/*      */     case 'n':
/*      */     case 'o':
/*      */     case 'p':
/*      */     case 'q':
/*      */     case 'r':
/*      */     case 's':
/*      */     case 't':
/*      */     case 'u':
/*      */     case 'v':
/*      */     case 'w':
/*      */     case 'x':
/*      */     case 'y':
/*      */     case 'z':
/*  249 */       matchRange('a', 'z');
/*  250 */       break;
/*      */     case 'A':
/*      */     case 'B':
/*      */     case 'C':
/*      */     case 'D':
/*      */     case 'E':
/*      */     case 'F':
/*      */     case 'G':
/*      */     case 'H':
/*      */     case 'I':
/*      */     case 'J':
/*      */     case 'K':
/*      */     case 'L':
/*      */     case 'M':
/*      */     case 'N':
/*      */     case 'O':
/*      */     case 'P':
/*      */     case 'Q':
/*      */     case 'R':
/*      */     case 'S':
/*      */     case 'T':
/*      */     case 'U':
/*      */     case 'V':
/*      */     case 'W':
/*      */     case 'X':
/*      */     case 'Y':
/*      */     case 'Z':
/*  260 */       matchRange('A', 'Z');
/*  261 */       break;
/*      */     case '_':
/*  265 */       match('_');
/*  266 */       break;
/*      */     case '[':
/*      */     case '\\':
/*      */     case ']':
/*      */     case '^':
/*      */     case '`':
/*      */     default:
/*  270 */       throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */     }
/*      */ 
/*      */     while (true)
/*      */     {
/*  277 */       switch (LA(1)) { case 'a':
/*      */       case 'b':
/*      */       case 'c':
/*      */       case 'd':
/*      */       case 'e':
/*      */       case 'f':
/*      */       case 'g':
/*      */       case 'h':
/*      */       case 'i':
/*      */       case 'j':
/*      */       case 'k':
/*      */       case 'l':
/*      */       case 'm':
/*      */       case 'n':
/*      */       case 'o':
/*      */       case 'p':
/*      */       case 'q':
/*      */       case 'r':
/*      */       case 's':
/*      */       case 't':
/*      */       case 'u':
/*      */       case 'v':
/*      */       case 'w':
/*      */       case 'x':
/*      */       case 'y':
/*      */       case 'z':
/*  286 */         matchRange('a', 'z');
/*  287 */         break;
/*      */       case 'A':
/*      */       case 'B':
/*      */       case 'C':
/*      */       case 'D':
/*      */       case 'E':
/*      */       case 'F':
/*      */       case 'G':
/*      */       case 'H':
/*      */       case 'I':
/*      */       case 'J':
/*      */       case 'K':
/*      */       case 'L':
/*      */       case 'M':
/*      */       case 'N':
/*      */       case 'O':
/*      */       case 'P':
/*      */       case 'Q':
/*      */       case 'R':
/*      */       case 'S':
/*      */       case 'T':
/*      */       case 'U':
/*      */       case 'V':
/*      */       case 'W':
/*      */       case 'X':
/*      */       case 'Y':
/*      */       case 'Z':
/*  297 */         matchRange('A', 'Z');
/*  298 */         break;
/*      */       case '0':
/*      */       case '1':
/*      */       case '2':
/*      */       case '3':
/*      */       case '4':
/*      */       case '5':
/*      */       case '6':
/*      */       case '7':
/*      */       case '8':
/*      */       case '9':
/*  304 */         matchRange('0', '9');
/*  305 */         break;
/*      */       case '_':
/*  309 */         match('_');
/*  310 */         break;
/*      */       case '/':
/*  314 */         match('/');
/*      */       case ':':
/*      */       case ';':
/*      */       case '<':
/*      */       case '=':
/*      */       case '>':
/*      */       case '?':
/*      */       case '@':
/*      */       case '[':
/*      */       case '\\':
/*      */       case ']':
/*      */       case '^':
/*  324 */       case '`': }  } _ttype = testLiteralsTable(_ttype);
/*  325 */     if ((_createToken) && (_token == null) && (_ttype != -1)) {
/*  326 */       _token = makeToken(_ttype);
/*  327 */       _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
/*      */     }
/*  329 */     this._returnToken = _token;
/*      */   }
/*      */ 
/*      */   public final void mINT(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
/*  333 */     Token _token = null; int _begin = this.text.length();
/*  334 */     int _ttype = 35;
/*      */ 
/*  338 */     int _cnt63 = 0;
/*      */     while (true)
/*      */     {
/*  341 */       if ((LA(1) >= '0') && (LA(1) <= '9')) {
/*  342 */         matchRange('0', '9');
/*      */       }
/*      */       else {
/*  345 */         if (_cnt63 >= 1) break; throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */       }
/*      */ 
/*  348 */       _cnt63++;
/*      */     }
/*      */ 
/*  351 */     if ((_createToken) && (_token == null) && (_ttype != -1)) {
/*  352 */       _token = makeToken(_ttype);
/*  353 */       _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
/*      */     }
/*  355 */     this._returnToken = _token;
/*      */   }
/*      */ 
/*      */   public final void mSTRING(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
/*  359 */     Token _token = null; int _begin = this.text.length();
/*  360 */     int _ttype = 34;
/*      */ 
/*  363 */     int _saveIndex = this.text.length();
/*  364 */     match('"');
/*  365 */     this.text.setLength(_saveIndex);
/*      */     while (true)
/*      */     {
/*  369 */       if (LA(1) == '\\') {
/*  370 */         mESC_CHAR(false, true);
/*      */       } else {
/*  372 */         if (!_tokenSet_0.member(LA(1))) break;
/*  373 */         matchNot('"');
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/*  381 */     _saveIndex = this.text.length();
/*  382 */     match('"');
/*  383 */     this.text.setLength(_saveIndex);
/*  384 */     if ((_createToken) && (_token == null) && (_ttype != -1)) {
/*  385 */       _token = makeToken(_ttype);
/*  386 */       _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
/*      */     }
/*  388 */     this._returnToken = _token;
/*      */   }
/*      */ 
/*      */   protected final void mESC_CHAR(boolean _createToken, boolean doEscape)
/*      */     throws RecognitionException, CharStreamException, TokenStreamException
/*      */   {
/*  397 */     Token _token = null; int _begin = this.text.length();
/*  398 */     int _ttype = 41;
/*      */ 
/*  400 */     char c = '\000';
/*      */ 
/*  402 */     match('\\');
/*      */ 
/*  404 */     if ((LA(1) == 'n') && (LA(2) >= '\003') && (LA(2) <= 65534)) {
/*  405 */       match('n');
/*  406 */       if ((this.inputState.guessing == 0) && 
/*  407 */         (doEscape)) { this.text.setLength(_begin); this.text.append("\n");
/*      */       }
/*      */     }
/*  410 */     else if ((LA(1) == 'r') && (LA(2) >= '\003') && (LA(2) <= 65534)) {
/*  411 */       match('r');
/*  412 */       if ((this.inputState.guessing == 0) && 
/*  413 */         (doEscape)) { this.text.setLength(_begin); this.text.append("\r");
/*      */       }
/*      */     }
/*  416 */     else if ((LA(1) == 't') && (LA(2) >= '\003') && (LA(2) <= 65534)) {
/*  417 */       match('t');
/*  418 */       if ((this.inputState.guessing == 0) && 
/*  419 */         (doEscape)) { this.text.setLength(_begin); this.text.append("\t");
/*      */       }
/*      */     }
/*  422 */     else if ((LA(1) == 'b') && (LA(2) >= '\003') && (LA(2) <= 65534)) {
/*  423 */       match('b');
/*  424 */       if ((this.inputState.guessing == 0) && 
/*  425 */         (doEscape)) { this.text.setLength(_begin); this.text.append("\b");
/*      */       }
/*      */     }
/*  428 */     else if ((LA(1) == 'f') && (LA(2) >= '\003') && (LA(2) <= 65534)) {
/*  429 */       match('f');
/*  430 */       if ((this.inputState.guessing == 0) && 
/*  431 */         (doEscape)) { this.text.setLength(_begin); this.text.append("\f");
/*      */       }
/*      */     }
/*  434 */     else if ((LA(1) >= '\003') && (LA(1) <= 65534) && (LA(2) >= '\003') && (LA(2) <= 65534)) {
/*  435 */       c = LA(1);
/*  436 */       matchNot(65535);
/*  437 */       if ((this.inputState.guessing == 0) && 
/*  438 */         (doEscape)) { this.text.setLength(_begin); this.text.append(String.valueOf(c)); }
/*      */     }
/*      */     else
/*      */     {
/*  442 */       throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */     }
/*      */ 
/*  446 */     if ((_createToken) && (_token == null) && (_ttype != -1)) {
/*  447 */       _token = makeToken(_ttype);
/*  448 */       _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
/*      */     }
/*  450 */     this._returnToken = _token;
/*      */   }
/*      */ 
/*      */   public final void mANONYMOUS_TEMPLATE(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
/*  454 */     Token _token = null; int _begin = this.text.length();
/*  455 */     int _ttype = 33;
/*      */ 
/*  458 */     List args = null;
/*  459 */     StringTemplateToken t = null;
/*      */ 
/*  462 */     int _saveIndex = this.text.length();
/*  463 */     match('{');
/*  464 */     this.text.setLength(_saveIndex);
/*      */ 
/*  466 */     boolean synPredMatched70 = false;
/*  467 */     if ((_tokenSet_1.member(LA(1))) && (_tokenSet_2.member(LA(2)))) {
/*  468 */       int _m70 = mark();
/*  469 */       synPredMatched70 = true;
/*  470 */       this.inputState.guessing += 1;
/*      */       try
/*      */       {
/*  473 */         mTEMPLATE_ARGS(false);
/*      */       }
/*      */       catch (RecognitionException pe)
/*      */       {
/*  477 */         synPredMatched70 = false;
/*      */       }
/*  479 */       rewind(_m70);
/*  480 */       this.inputState.guessing -= 1;
/*      */     }
/*  482 */     if (synPredMatched70) {
/*  483 */       args = mTEMPLATE_ARGS(false);
/*      */ 
/*  485 */       if ((_tokenSet_3.member(LA(1))) && (LA(2) >= '\003') && (LA(2) <= 65534)) {
/*  486 */         _saveIndex = this.text.length();
/*  487 */         mWS_CHAR(false);
/*  488 */         this.text.setLength(_saveIndex);
/*      */       }
/*  490 */       else if ((LA(1) < '\003') || (LA(1) > 65534))
/*      */       {
/*  493 */         throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */       }
/*      */ 
/*  497 */       if (this.inputState.guessing == 0)
/*      */       {
/*  500 */         t = new StringTemplateToken(33, new String(this.text.getBuffer(), _begin, this.text.length() - _begin), args);
/*  501 */         _token = t;
/*      */       }
/*      */ 
/*      */     }
/*  505 */     else if ((LA(1) < '\003') || (LA(1) > 65534))
/*      */     {
/*  508 */       throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */     }
/*      */ 
/*      */     while (true)
/*      */     {
/*  515 */       if ((LA(1) == '\\') && (LA(2) == '{')) {
/*  516 */         _saveIndex = this.text.length();
/*  517 */         match('\\');
/*  518 */         this.text.setLength(_saveIndex);
/*  519 */         match('{');
/*      */       }
/*  521 */       else if ((LA(1) == '\\') && (LA(2) == '}')) {
/*  522 */         _saveIndex = this.text.length();
/*  523 */         match('\\');
/*  524 */         this.text.setLength(_saveIndex);
/*  525 */         match('}');
/*      */       }
/*  527 */       else if ((LA(1) == '\\') && (LA(2) >= '\003') && (LA(2) <= 65534)) {
/*  528 */         mESC_CHAR(false, false);
/*      */       }
/*  530 */       else if (LA(1) == '{') {
/*  531 */         mNESTED_ANONYMOUS_TEMPLATE(false);
/*      */       } else {
/*  533 */         if (!_tokenSet_4.member(LA(1))) break;
/*  534 */         matchNot('}');
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/*  542 */     if (this.inputState.guessing == 0)
/*      */     {
/*  544 */       if (t != null) {
/*  545 */         t.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
/*      */       }
/*      */     }
/*      */ 
/*  549 */     _saveIndex = this.text.length();
/*  550 */     match('}');
/*  551 */     this.text.setLength(_saveIndex);
/*  552 */     if ((_createToken) && (_token == null) && (_ttype != -1)) {
/*  553 */       _token = makeToken(_ttype);
/*  554 */       _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
/*      */     }
/*  556 */     this._returnToken = _token;
/*      */   }
/*      */ 
/*      */   protected final List mTEMPLATE_ARGS(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
/*  560 */     List args = new ArrayList();
/*  561 */     Token _token = null; int _begin = this.text.length();
/*  562 */     int _ttype = 39;
/*      */ 
/*  564 */     Token a = null;
/*  565 */     Token a2 = null;
/*      */ 
/*  568 */     switch (LA(1)) { case '\t':
/*      */     case '\n':
/*      */     case '\r':
/*      */     case ' ':
/*  571 */       _saveIndex = this.text.length();
/*  572 */       mWS_CHAR(false);
/*  573 */       this.text.setLength(_saveIndex);
/*  574 */       break;
/*      */     case 'A':
/*      */     case 'B':
/*      */     case 'C':
/*      */     case 'D':
/*      */     case 'E':
/*      */     case 'F':
/*      */     case 'G':
/*      */     case 'H':
/*      */     case 'I':
/*      */     case 'J':
/*      */     case 'K':
/*      */     case 'L':
/*      */     case 'M':
/*      */     case 'N':
/*      */     case 'O':
/*      */     case 'P':
/*      */     case 'Q':
/*      */     case 'R':
/*      */     case 'S':
/*      */     case 'T':
/*      */     case 'U':
/*      */     case 'V':
/*      */     case 'W':
/*      */     case 'X':
/*      */     case 'Y':
/*      */     case 'Z':
/*      */     case '_':
/*      */     case 'a':
/*      */     case 'b':
/*      */     case 'c':
/*      */     case 'd':
/*      */     case 'e':
/*      */     case 'f':
/*      */     case 'g':
/*      */     case 'h':
/*      */     case 'i':
/*      */     case 'j':
/*      */     case 'k':
/*      */     case 'l':
/*      */     case 'm':
/*      */     case 'n':
/*      */     case 'o':
/*      */     case 'p':
/*      */     case 'q':
/*      */     case 'r':
/*      */     case 's':
/*      */     case 't':
/*      */     case 'u':
/*      */     case 'v':
/*      */     case 'w':
/*      */     case 'x':
/*      */     case 'y':
/*      */     case 'z':
/*  591 */       break;
/*      */     case '\013':
/*      */     case '\f':
/*      */     case '\016':
/*      */     case '\017':
/*      */     case '\020':
/*      */     case '\021':
/*      */     case '\022':
/*      */     case '\023':
/*      */     case '\024':
/*      */     case '\025':
/*      */     case '\026':
/*      */     case '\027':
/*      */     case '\030':
/*      */     case '\031':
/*      */     case '\032':
/*      */     case '\033':
/*      */     case '\034':
/*      */     case '\035':
/*      */     case '\036':
/*      */     case '\037':
/*      */     case '!':
/*      */     case '"':
/*      */     case '#':
/*      */     case '$':
/*      */     case '%':
/*      */     case '&':
/*      */     case '\'':
/*      */     case '(':
/*      */     case ')':
/*      */     case '*':
/*      */     case '+':
/*      */     case ',':
/*      */     case '-':
/*      */     case '.':
/*      */     case '/':
/*      */     case '0':
/*      */     case '1':
/*      */     case '2':
/*      */     case '3':
/*      */     case '4':
/*      */     case '5':
/*      */     case '6':
/*      */     case '7':
/*      */     case '8':
/*      */     case '9':
/*      */     case ':':
/*      */     case ';':
/*      */     case '<':
/*      */     case '=':
/*      */     case '>':
/*      */     case '?':
/*      */     case '@':
/*      */     case '[':
/*      */     case '\\':
/*      */     case ']':
/*      */     case '^':
/*      */     case '`':
/*      */     default:
/*  595 */       throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */     }
/*      */ 
/*  599 */     int _saveIndex = this.text.length();
/*  600 */     mID(true);
/*  601 */     this.text.setLength(_saveIndex);
/*  602 */     a = this._returnToken;
/*  603 */     if (this.inputState.guessing == 0) {
/*  604 */       args.add(a.getText());
/*      */     }
/*      */ 
/*  609 */     while ((_tokenSet_5.member(LA(1))) && (_tokenSet_6.member(LA(2))))
/*      */     {
/*  611 */       switch (LA(1)) { case '\t':
/*      */       case '\n':
/*      */       case '\r':
/*      */       case ' ':
/*  614 */         _saveIndex = this.text.length();
/*  615 */         mWS_CHAR(false);
/*  616 */         this.text.setLength(_saveIndex);
/*  617 */         break;
/*      */       case ',':
/*  621 */         break;
/*      */       default:
/*  625 */         throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */       }
/*      */ 
/*  629 */       _saveIndex = this.text.length();
/*  630 */       match(',');
/*  631 */       this.text.setLength(_saveIndex);
/*      */ 
/*  633 */       switch (LA(1)) { case '\t':
/*      */       case '\n':
/*      */       case '\r':
/*      */       case ' ':
/*  636 */         _saveIndex = this.text.length();
/*  637 */         mWS_CHAR(false);
/*  638 */         this.text.setLength(_saveIndex);
/*  639 */         break;
/*      */       case 'A':
/*      */       case 'B':
/*      */       case 'C':
/*      */       case 'D':
/*      */       case 'E':
/*      */       case 'F':
/*      */       case 'G':
/*      */       case 'H':
/*      */       case 'I':
/*      */       case 'J':
/*      */       case 'K':
/*      */       case 'L':
/*      */       case 'M':
/*      */       case 'N':
/*      */       case 'O':
/*      */       case 'P':
/*      */       case 'Q':
/*      */       case 'R':
/*      */       case 'S':
/*      */       case 'T':
/*      */       case 'U':
/*      */       case 'V':
/*      */       case 'W':
/*      */       case 'X':
/*      */       case 'Y':
/*      */       case 'Z':
/*      */       case '_':
/*      */       case 'a':
/*      */       case 'b':
/*      */       case 'c':
/*      */       case 'd':
/*      */       case 'e':
/*      */       case 'f':
/*      */       case 'g':
/*      */       case 'h':
/*      */       case 'i':
/*      */       case 'j':
/*      */       case 'k':
/*      */       case 'l':
/*      */       case 'm':
/*      */       case 'n':
/*      */       case 'o':
/*      */       case 'p':
/*      */       case 'q':
/*      */       case 'r':
/*      */       case 's':
/*      */       case 't':
/*      */       case 'u':
/*      */       case 'v':
/*      */       case 'w':
/*      */       case 'x':
/*      */       case 'y':
/*      */       case 'z':
/*  656 */         break;
/*      */       case '\013':
/*      */       case '\f':
/*      */       case '\016':
/*      */       case '\017':
/*      */       case '\020':
/*      */       case '\021':
/*      */       case '\022':
/*      */       case '\023':
/*      */       case '\024':
/*      */       case '\025':
/*      */       case '\026':
/*      */       case '\027':
/*      */       case '\030':
/*      */       case '\031':
/*      */       case '\032':
/*      */       case '\033':
/*      */       case '\034':
/*      */       case '\035':
/*      */       case '\036':
/*      */       case '\037':
/*      */       case '!':
/*      */       case '"':
/*      */       case '#':
/*      */       case '$':
/*      */       case '%':
/*      */       case '&':
/*      */       case '\'':
/*      */       case '(':
/*      */       case ')':
/*      */       case '*':
/*      */       case '+':
/*      */       case ',':
/*      */       case '-':
/*      */       case '.':
/*      */       case '/':
/*      */       case '0':
/*      */       case '1':
/*      */       case '2':
/*      */       case '3':
/*      */       case '4':
/*      */       case '5':
/*      */       case '6':
/*      */       case '7':
/*      */       case '8':
/*      */       case '9':
/*      */       case ':':
/*      */       case ';':
/*      */       case '<':
/*      */       case '=':
/*      */       case '>':
/*      */       case '?':
/*      */       case '@':
/*      */       case '[':
/*      */       case '\\':
/*      */       case ']':
/*      */       case '^':
/*      */       case '`':
/*      */       default:
/*  660 */         throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */       }
/*      */ 
/*  664 */       _saveIndex = this.text.length();
/*  665 */       mID(true);
/*  666 */       this.text.setLength(_saveIndex);
/*  667 */       a2 = this._returnToken;
/*  668 */       if (this.inputState.guessing == 0) {
/*  669 */         args.add(a2.getText());
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/*  679 */     switch (LA(1)) { case '\t':
/*      */     case '\n':
/*      */     case '\r':
/*      */     case ' ':
/*  682 */       _saveIndex = this.text.length();
/*  683 */       mWS_CHAR(false);
/*  684 */       this.text.setLength(_saveIndex);
/*  685 */       break;
/*      */     case '|':
/*  689 */       break;
/*      */     default:
/*  693 */       throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */     }
/*      */ 
/*  697 */     _saveIndex = this.text.length();
/*  698 */     match('|');
/*  699 */     this.text.setLength(_saveIndex);
/*  700 */     if ((_createToken) && (_token == null) && (_ttype != -1)) {
/*  701 */       _token = makeToken(_ttype);
/*  702 */       _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
/*      */     }
/*  704 */     this._returnToken = _token;
/*  705 */     return args;
/*      */   }
/*      */ 
/*      */   protected final void mWS_CHAR(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
/*  709 */     Token _token = null; int _begin = this.text.length();
/*  710 */     int _ttype = 43;
/*      */ 
/*  713 */     switch (LA(1))
/*      */     {
/*      */     case ' ':
/*  716 */       match(' ');
/*  717 */       break;
/*      */     case '\t':
/*  721 */       match('\t');
/*  722 */       break;
/*      */     case '\r':
/*  726 */       match('\r');
/*  727 */       break;
/*      */     case '\n':
/*  731 */       match('\n');
/*  732 */       if (this.inputState.guessing == 0)
/*  733 */         newline(); break;
/*      */     default:
/*  739 */       throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */     }
/*      */ 
/*  742 */     if ((_createToken) && (_token == null) && (_ttype != -1)) {
/*  743 */       _token = makeToken(_ttype);
/*  744 */       _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
/*      */     }
/*  746 */     this._returnToken = _token;
/*      */   }
/*      */ 
/*      */   protected final void mNESTED_ANONYMOUS_TEMPLATE(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
/*  750 */     Token _token = null; int _begin = this.text.length();
/*  751 */     int _ttype = 40;
/*      */ 
/*  754 */     match('{');
/*      */     while (true)
/*      */     {
/*  758 */       if ((LA(1) == '\\') && (LA(2) == '{')) {
/*  759 */         int _saveIndex = this.text.length();
/*  760 */         match('\\');
/*  761 */         this.text.setLength(_saveIndex);
/*  762 */         match('{');
/*      */       }
/*  764 */       else if ((LA(1) == '\\') && (LA(2) == '}')) {
/*  765 */         int _saveIndex = this.text.length();
/*  766 */         match('\\');
/*  767 */         this.text.setLength(_saveIndex);
/*  768 */         match('}');
/*      */       }
/*  770 */       else if ((LA(1) == '\\') && (LA(2) >= '\003') && (LA(2) <= 65534)) {
/*  771 */         mESC_CHAR(false, false);
/*      */       }
/*  773 */       else if (LA(1) == '{') {
/*  774 */         mNESTED_ANONYMOUS_TEMPLATE(false);
/*      */       } else {
/*  776 */         if (!_tokenSet_4.member(LA(1))) break;
/*  777 */         matchNot('}');
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/*  785 */     match('}');
/*  786 */     if ((_createToken) && (_token == null) && (_ttype != -1)) {
/*  787 */       _token = makeToken(_ttype);
/*  788 */       _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
/*      */     }
/*  790 */     this._returnToken = _token;
/*      */   }
/*      */ 
/*      */   public final void mLBRACK(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
/*  794 */     Token _token = null; int _begin = this.text.length();
/*  795 */     int _ttype = 36;
/*      */ 
/*  798 */     match('[');
/*  799 */     if ((_createToken) && (_token == null) && (_ttype != -1)) {
/*  800 */       _token = makeToken(_ttype);
/*  801 */       _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
/*      */     }
/*  803 */     this._returnToken = _token;
/*      */   }
/*      */ 
/*      */   public final void mRBRACK(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
/*  807 */     Token _token = null; int _begin = this.text.length();
/*  808 */     int _ttype = 37;
/*      */ 
/*  811 */     match(']');
/*  812 */     if ((_createToken) && (_token == null) && (_ttype != -1)) {
/*  813 */       _token = makeToken(_ttype);
/*  814 */       _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
/*      */     }
/*  816 */     this._returnToken = _token;
/*      */   }
/*      */ 
/*      */   public final void mLPAREN(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
/*  820 */     Token _token = null; int _begin = this.text.length();
/*  821 */     int _ttype = 16;
/*      */ 
/*  824 */     match('(');
/*  825 */     if ((_createToken) && (_token == null) && (_ttype != -1)) {
/*  826 */       _token = makeToken(_ttype);
/*  827 */       _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
/*      */     }
/*  829 */     this._returnToken = _token;
/*      */   }
/*      */ 
/*      */   public final void mRPAREN(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
/*  833 */     Token _token = null; int _begin = this.text.length();
/*  834 */     int _ttype = 17;
/*      */ 
/*  837 */     match(')');
/*  838 */     if ((_createToken) && (_token == null) && (_ttype != -1)) {
/*  839 */       _token = makeToken(_ttype);
/*  840 */       _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
/*      */     }
/*  842 */     this._returnToken = _token;
/*      */   }
/*      */ 
/*      */   public final void mCOMMA(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
/*  846 */     Token _token = null; int _begin = this.text.length();
/*  847 */     int _ttype = 19;
/*      */ 
/*  850 */     match(',');
/*  851 */     if ((_createToken) && (_token == null) && (_ttype != -1)) {
/*  852 */       _token = makeToken(_ttype);
/*  853 */       _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
/*      */     }
/*  855 */     this._returnToken = _token;
/*      */   }
/*      */ 
/*      */   public final void mDOT(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
/*  859 */     Token _token = null; int _begin = this.text.length();
/*  860 */     int _ttype = 25;
/*      */ 
/*  863 */     match('.');
/*  864 */     if ((_createToken) && (_token == null) && (_ttype != -1)) {
/*  865 */       _token = makeToken(_ttype);
/*  866 */       _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
/*      */     }
/*  868 */     this._returnToken = _token;
/*      */   }
/*      */ 
/*      */   public final void mASSIGN(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
/*  872 */     Token _token = null; int _begin = this.text.length();
/*  873 */     int _ttype = 21;
/*      */ 
/*  876 */     match('=');
/*  877 */     if ((_createToken) && (_token == null) && (_ttype != -1)) {
/*  878 */       _token = makeToken(_ttype);
/*  879 */       _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
/*      */     }
/*  881 */     this._returnToken = _token;
/*      */   }
/*      */ 
/*      */   public final void mCOLON(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
/*  885 */     Token _token = null; int _begin = this.text.length();
/*  886 */     int _ttype = 22;
/*      */ 
/*  889 */     match(':');
/*  890 */     if ((_createToken) && (_token == null) && (_ttype != -1)) {
/*  891 */       _token = makeToken(_ttype);
/*  892 */       _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
/*      */     }
/*  894 */     this._returnToken = _token;
/*      */   }
/*      */ 
/*      */   public final void mPLUS(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
/*  898 */     Token _token = null; int _begin = this.text.length();
/*  899 */     int _ttype = 24;
/*      */ 
/*  902 */     match('+');
/*  903 */     if ((_createToken) && (_token == null) && (_ttype != -1)) {
/*  904 */       _token = makeToken(_ttype);
/*  905 */       _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
/*      */     }
/*  907 */     this._returnToken = _token;
/*      */   }
/*      */ 
/*      */   public final void mSEMI(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
/*  911 */     Token _token = null; int _begin = this.text.length();
/*  912 */     int _ttype = 15;
/*      */ 
/*  915 */     match(';');
/*  916 */     if ((_createToken) && (_token == null) && (_ttype != -1)) {
/*  917 */       _token = makeToken(_ttype);
/*  918 */       _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
/*      */     }
/*  920 */     this._returnToken = _token;
/*      */   }
/*      */ 
/*      */   public final void mNOT(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
/*  924 */     Token _token = null; int _begin = this.text.length();
/*  925 */     int _ttype = 23;
/*      */ 
/*  928 */     match('!');
/*  929 */     if ((_createToken) && (_token == null) && (_ttype != -1)) {
/*  930 */       _token = makeToken(_ttype);
/*  931 */       _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
/*      */     }
/*  933 */     this._returnToken = _token;
/*      */   }
/*      */ 
/*      */   public final void mDOTDOTDOT(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
/*  937 */     Token _token = null; int _begin = this.text.length();
/*  938 */     int _ttype = 38;
/*      */ 
/*  941 */     match("...");
/*  942 */     if ((_createToken) && (_token == null) && (_ttype != -1)) {
/*  943 */       _token = makeToken(_ttype);
/*  944 */       _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
/*      */     }
/*  946 */     this._returnToken = _token;
/*      */   }
/*      */ 
/*      */   public final void mWS(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
/*  950 */     Token _token = null; int _begin = this.text.length();
/*  951 */     int _ttype = 42;
/*      */ 
/*  955 */     int _cnt100 = 0;
/*      */     while (true)
/*      */     {
/*  958 */       switch (LA(1))
/*      */       {
/*      */       case ' ':
/*  961 */         match(' ');
/*  962 */         break;
/*      */       case '\t':
/*  966 */         match('\t');
/*  967 */         break;
/*      */       case '\r':
/*  971 */         match('\r');
/*  972 */         break;
/*      */       case '\n':
/*  976 */         match('\n');
/*  977 */         if (this.inputState.guessing == 0)
/*  978 */           newline(); break;
/*      */       default:
/*  984 */         if (_cnt100 >= 1) break label154; throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */       }
/*      */ 
/*  987 */       _cnt100++;
/*      */     }
/*      */ 
/*  990 */     label154: if (this.inputState.guessing == 0) {
/*  991 */       _ttype = -1;
/*      */     }
/*  993 */     if ((_createToken) && (_token == null) && (_ttype != -1)) {
/*  994 */       _token = makeToken(_ttype);
/*  995 */       _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
/*      */     }
/*  997 */     this._returnToken = _token;
/*      */   }
/*      */ 
/*      */   private static final long[] mk_tokenSet_0()
/*      */   {
/* 1002 */     long[] data = new long[2048];
/* 1003 */     data[0] = -17179869192L;
/* 1004 */     data[1] = -268435457L;
/* 1005 */     for (int i = 2; i <= 1022; i++) data[i] = -1L;
/* 1006 */     data[1023] = 9223372036854775807L;
/* 1007 */     return data;
/*      */   }
/*      */ 
/*      */   private static final long[] mk_tokenSet_1() {
/* 1011 */     long[] data = new long[1025];
/* 1012 */     data[0] = 4294977024L;
/* 1013 */     data[1] = 576460745995190270L;
/* 1014 */     return data;
/*      */   }
/*      */ 
/*      */   private static final long[] mk_tokenSet_2() {
/* 1018 */     long[] data = new long[1025];
/* 1019 */     data[0] = 288107235144377856L;
/* 1020 */     data[1] = 1729382250602037246L;
/* 1021 */     return data;
/*      */   }
/*      */ 
/*      */   private static final long[] mk_tokenSet_3() {
/* 1025 */     long[] data = new long[1025];
/* 1026 */     data[0] = 4294977024L;
/* 1027 */     return data;
/*      */   }
/*      */ 
/*      */   private static final long[] mk_tokenSet_4() {
/* 1031 */     long[] data = new long[2048];
/* 1032 */     data[0] = -8L;
/* 1033 */     data[1] = -2882303761785552897L;
/* 1034 */     for (int i = 2; i <= 1022; i++) data[i] = -1L;
/* 1035 */     data[1023] = 9223372036854775807L;
/* 1036 */     return data;
/*      */   }
/*      */ 
/*      */   private static final long[] mk_tokenSet_5() {
/* 1040 */     long[] data = new long[1025];
/* 1041 */     data[0] = 17596481021440L;
/* 1042 */     return data;
/*      */   }
/*      */ 
/*      */   private static final long[] mk_tokenSet_6() {
/* 1046 */     long[] data = new long[1025];
/* 1047 */     data[0] = 17596481021440L;
/* 1048 */     data[1] = 576460745995190270L;
/* 1049 */     return data;
/*      */   }
/*      */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.antlr.stringtemplate.language.ActionLexer
 * JD-Core Version:    0.6.2
 */