/*      */ package antlr.preprocessor;
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
/*      */ import java.util.Hashtable;
/*      */ 
/*      */ public class PreprocessorLexer extends CharScanner
/*      */   implements PreprocessorTokenTypes, TokenStream
/*      */ {
/* 1321 */   public static final BitSet _tokenSet_0 = new BitSet(mk_tokenSet_0());
/*      */ 
/* 1326 */   public static final BitSet _tokenSet_1 = new BitSet(mk_tokenSet_1());
/*      */ 
/* 1333 */   public static final BitSet _tokenSet_2 = new BitSet(mk_tokenSet_2());
/*      */ 
/* 1340 */   public static final BitSet _tokenSet_3 = new BitSet(mk_tokenSet_3());
/*      */ 
/* 1345 */   public static final BitSet _tokenSet_4 = new BitSet(mk_tokenSet_4());
/*      */ 
/* 1353 */   public static final BitSet _tokenSet_5 = new BitSet(mk_tokenSet_5());
/*      */ 
/* 1360 */   public static final BitSet _tokenSet_6 = new BitSet(mk_tokenSet_6());
/*      */ 
/* 1368 */   public static final BitSet _tokenSet_7 = new BitSet(mk_tokenSet_7());
/*      */ 
/* 1376 */   public static final BitSet _tokenSet_8 = new BitSet(mk_tokenSet_8());
/*      */ 
/* 1381 */   public static final BitSet _tokenSet_9 = new BitSet(mk_tokenSet_9());
/*      */ 
/* 1386 */   public static final BitSet _tokenSet_10 = new BitSet(mk_tokenSet_10());
/*      */ 
/*      */   public PreprocessorLexer(InputStream paramInputStream)
/*      */   {
/*   32 */     this(new ByteBuffer(paramInputStream));
/*      */   }
/*      */   public PreprocessorLexer(Reader paramReader) {
/*   35 */     this(new CharBuffer(paramReader));
/*      */   }
/*      */   public PreprocessorLexer(InputBuffer paramInputBuffer) {
/*   38 */     this(new LexerSharedInputState(paramInputBuffer));
/*      */   }
/*      */   public PreprocessorLexer(LexerSharedInputState paramLexerSharedInputState) {
/*   41 */     super(paramLexerSharedInputState);
/*   42 */     this.caseSensitiveLiterals = true;
/*   43 */     setCaseSensitive(true);
/*   44 */     this.literals = new Hashtable();
/*   45 */     this.literals.put(new ANTLRHashString("public", this), new Integer(18));
/*   46 */     this.literals.put(new ANTLRHashString("class", this), new Integer(8));
/*   47 */     this.literals.put(new ANTLRHashString("throws", this), new Integer(23));
/*   48 */     this.literals.put(new ANTLRHashString("catch", this), new Integer(26));
/*   49 */     this.literals.put(new ANTLRHashString("private", this), new Integer(17));
/*   50 */     this.literals.put(new ANTLRHashString("extends", this), new Integer(10));
/*   51 */     this.literals.put(new ANTLRHashString("protected", this), new Integer(16));
/*   52 */     this.literals.put(new ANTLRHashString("returns", this), new Integer(21));
/*   53 */     this.literals.put(new ANTLRHashString("tokens", this), new Integer(4));
/*   54 */     this.literals.put(new ANTLRHashString("exception", this), new Integer(25));
/*      */   }
/*      */ 
/*      */   public Token nextToken() throws TokenStreamException {
/*   58 */     Token localToken = null;
/*      */     while (true)
/*      */     {
/*   61 */       Object localObject = null;
/*   62 */       int i = 0;
/*   63 */       resetText();
/*      */       try
/*      */       {
/*   66 */         switch (LA(1))
/*      */         {
/*      */         case ':':
/*   69 */           mRULE_BLOCK(true);
/*   70 */           localToken = this._returnToken;
/*   71 */           break;
/*      */         case '\t':
/*      */         case '\n':
/*      */         case '\r':
/*      */         case ' ':
/*   75 */           mWS(true);
/*   76 */           localToken = this._returnToken;
/*   77 */           break;
/*      */         case '/':
/*   81 */           mCOMMENT(true);
/*   82 */           localToken = this._returnToken;
/*   83 */           break;
/*      */         case '{':
/*   87 */           mACTION(true);
/*   88 */           localToken = this._returnToken;
/*   89 */           break;
/*      */         case '"':
/*   93 */           mSTRING_LITERAL(true);
/*   94 */           localToken = this._returnToken;
/*   95 */           break;
/*      */         case '\'':
/*   99 */           mCHAR_LITERAL(true);
/*  100 */           localToken = this._returnToken;
/*  101 */           break;
/*      */         case '!':
/*  105 */           mBANG(true);
/*  106 */           localToken = this._returnToken;
/*  107 */           break;
/*      */         case ';':
/*  111 */           mSEMI(true);
/*  112 */           localToken = this._returnToken;
/*  113 */           break;
/*      */         case ',':
/*  117 */           mCOMMA(true);
/*  118 */           localToken = this._returnToken;
/*  119 */           break;
/*      */         case '}':
/*  123 */           mRCURLY(true);
/*  124 */           localToken = this._returnToken;
/*  125 */           break;
/*      */         case ')':
/*  129 */           mRPAREN(true);
/*  130 */           localToken = this._returnToken;
/*  131 */           break;
/*      */         case 'A':
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
/*  148 */           mID_OR_KEYWORD(true);
/*  149 */           localToken = this._returnToken;
/*  150 */           break;
/*      */         case '=':
/*  154 */           mASSIGN_RHS(true);
/*  155 */           localToken = this._returnToken;
/*  156 */           break;
/*      */         case '[':
/*  160 */           mARG_ACTION(true);
/*  161 */           localToken = this._returnToken;
/*  162 */           break;
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
/*      */         case '(':
/*      */         case '*':
/*      */         case '+':
/*      */         case '-':
/*      */         case '.':
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
/*      */         case '<':
/*      */         case '>':
/*      */         case '?':
/*      */         case '@':
/*      */         case '\\':
/*      */         case ']':
/*      */         case '^':
/*      */         case '`':
/*      */         case '|':
/*      */         default:
/*  165 */           if ((LA(1) == '(') && (_tokenSet_0.member(LA(2)))) {
/*  166 */             mSUBRULE_BLOCK(true);
/*  167 */             localToken = this._returnToken;
/*      */           }
/*  169 */           else if (LA(1) == '(') {
/*  170 */             mLPAREN(true);
/*  171 */             localToken = this._returnToken;
/*      */           }
/*  174 */           else if (LA(1) == 65535) { uponEOF(); this._returnToken = makeToken(1); } else {
/*  175 */             throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */           }break;
/*      */         }
/*  178 */         if (this._returnToken == null) continue;
/*  179 */         i = this._returnToken.getType();
/*  180 */         i = testLiteralsTable(i);
/*  181 */         this._returnToken.setType(i);
/*  182 */         return this._returnToken;
/*      */       }
/*      */       catch (RecognitionException localRecognitionException) {
/*  185 */         throw new TokenStreamRecognitionException(localRecognitionException);
/*      */       }
/*      */       catch (CharStreamException localCharStreamException)
/*      */       {
/*  189 */         if ((localCharStreamException instanceof CharStreamIOException)) {
/*  190 */           throw new TokenStreamIOException(((CharStreamIOException)localCharStreamException).io);
/*      */         }
/*      */ 
/*  193 */         throw new TokenStreamException(localCharStreamException.getMessage());
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   public final void mRULE_BLOCK(boolean paramBoolean) throws RecognitionException, CharStreamException, TokenStreamException
/*      */   {
/*  200 */     Token localToken = null; int j = this.text.length();
/*  201 */     int i = 22;
/*      */ 
/*  204 */     match(':');
/*      */     int k;
/*  206 */     if ((_tokenSet_1.member(LA(1))) && (_tokenSet_2.member(LA(2)))) {
/*  207 */       k = this.text.length();
/*  208 */       mWS(false);
/*  209 */       this.text.setLength(k);
/*      */     }
/*  211 */     else if (!_tokenSet_2.member(LA(1)))
/*      */     {
/*  214 */       throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */     }
/*      */ 
/*  218 */     mALT(false);
/*      */ 
/*  220 */     switch (LA(1)) { case '\t':
/*      */     case '\n':
/*      */     case '\r':
/*      */     case ' ':
/*  223 */       k = this.text.length();
/*  224 */       mWS(false);
/*  225 */       this.text.setLength(k);
/*  226 */       break;
/*      */     case ';':
/*      */     case '|':
/*  230 */       break;
/*      */     default:
/*  234 */       throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */     }
/*      */ 
/*  241 */     while (LA(1) == '|') {
/*  242 */       match('|');
/*      */ 
/*  244 */       if ((_tokenSet_1.member(LA(1))) && (_tokenSet_2.member(LA(2)))) {
/*  245 */         k = this.text.length();
/*  246 */         mWS(false);
/*  247 */         this.text.setLength(k);
/*      */       }
/*  249 */       else if (!_tokenSet_2.member(LA(1)))
/*      */       {
/*  252 */         throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */       }
/*      */ 
/*  256 */       mALT(false);
/*      */ 
/*  258 */       switch (LA(1)) { case '\t':
/*      */       case '\n':
/*      */       case '\r':
/*      */       case ' ':
/*  261 */         k = this.text.length();
/*  262 */         mWS(false);
/*  263 */         this.text.setLength(k);
/*  264 */         break;
/*      */       case ';':
/*      */       case '|':
/*  268 */         break;
/*      */       default:
/*  272 */         throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/*  283 */     match(';');
/*  284 */     if ((paramBoolean) && (localToken == null) && (i != -1)) {
/*  285 */       localToken = makeToken(i);
/*  286 */       localToken.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/*      */     }
/*  288 */     this._returnToken = localToken;
/*      */   }
/*      */ 
/*      */   public final void mWS(boolean paramBoolean) throws RecognitionException, CharStreamException, TokenStreamException {
/*  292 */     Token localToken = null; int j = this.text.length();
/*  293 */     int i = 33;
/*      */ 
/*  297 */     int k = 0;
/*      */     while (true)
/*      */     {
/*  300 */       if (LA(1) == ' ') {
/*  301 */         match(' ');
/*      */       }
/*  303 */       else if (LA(1) == '\t') {
/*  304 */         match('\t');
/*      */       }
/*  306 */       else if ((LA(1) == '\n') || (LA(1) == '\r')) {
/*  307 */         mNEWLINE(false);
/*      */       }
/*      */       else {
/*  310 */         if (k >= 1) break; throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */       }
/*      */ 
/*  313 */       k++;
/*      */     }
/*      */ 
/*  316 */     i = -1;
/*  317 */     if ((paramBoolean) && (localToken == null) && (i != -1)) {
/*  318 */       localToken = makeToken(i);
/*  319 */       localToken.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/*      */     }
/*  321 */     this._returnToken = localToken;
/*      */   }
/*      */ 
/*      */   protected final void mALT(boolean paramBoolean) throws RecognitionException, CharStreamException, TokenStreamException {
/*  325 */     Token localToken = null; int j = this.text.length();
/*  326 */     int i = 27;
/*      */ 
/*  332 */     while ((_tokenSet_3.member(LA(1))) && (LA(2) >= '\003') && (LA(2) <= 'ÿ')) {
/*  333 */       mELEMENT(false);
/*      */     }
/*      */ 
/*  341 */     if ((paramBoolean) && (localToken == null) && (i != -1)) {
/*  342 */       localToken = makeToken(i);
/*  343 */       localToken.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/*      */     }
/*  345 */     this._returnToken = localToken;
/*      */   }
/*      */ 
/*      */   public final void mSUBRULE_BLOCK(boolean paramBoolean) throws RecognitionException, CharStreamException, TokenStreamException {
/*  349 */     Token localToken = null; int j = this.text.length();
/*  350 */     int i = 6;
/*      */ 
/*  353 */     match('(');
/*      */ 
/*  355 */     if ((_tokenSet_1.member(LA(1))) && (_tokenSet_0.member(LA(2)))) {
/*  356 */       mWS(false);
/*      */     }
/*  358 */     else if (!_tokenSet_0.member(LA(1)))
/*      */     {
/*  361 */       throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */     }
/*      */ 
/*  365 */     mALT(false);
/*      */ 
/*  369 */     while ((_tokenSet_4.member(LA(1))) && (_tokenSet_0.member(LA(2))))
/*      */     {
/*  371 */       switch (LA(1)) { case '\t':
/*      */       case '\n':
/*      */       case '\r':
/*      */       case ' ':
/*  374 */         mWS(false);
/*  375 */         break;
/*      */       case '|':
/*  379 */         break;
/*      */       default:
/*  383 */         throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */       }
/*      */ 
/*  387 */       match('|');
/*      */ 
/*  389 */       if ((_tokenSet_1.member(LA(1))) && (_tokenSet_0.member(LA(2)))) {
/*  390 */         mWS(false);
/*      */       }
/*  392 */       else if (!_tokenSet_0.member(LA(1)))
/*      */       {
/*  395 */         throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */       }
/*      */ 
/*  399 */       mALT(false);
/*      */     }
/*      */ 
/*  408 */     switch (LA(1)) { case '\t':
/*      */     case '\n':
/*      */     case '\r':
/*      */     case ' ':
/*  411 */       mWS(false);
/*  412 */       break;
/*      */     case ')':
/*  416 */       break;
/*      */     default:
/*  420 */       throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */     }
/*      */ 
/*  424 */     match(')');
/*      */ 
/*  426 */     if ((LA(1) == '=') && (LA(2) == '>')) {
/*  427 */       match("=>");
/*      */     }
/*  429 */     else if (LA(1) == '*') {
/*  430 */       match('*');
/*      */     }
/*  432 */     else if (LA(1) == '+') {
/*  433 */       match('+');
/*      */     }
/*  435 */     else if (LA(1) == '?') {
/*  436 */       match('?');
/*      */     }
/*      */ 
/*  442 */     if ((paramBoolean) && (localToken == null) && (i != -1)) {
/*  443 */       localToken = makeToken(i);
/*  444 */       localToken.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/*      */     }
/*  446 */     this._returnToken = localToken;
/*      */   }
/*      */ 
/*      */   protected final void mELEMENT(boolean paramBoolean) throws RecognitionException, CharStreamException, TokenStreamException {
/*  450 */     Token localToken = null; int j = this.text.length();
/*  451 */     int i = 28;
/*      */ 
/*  454 */     switch (LA(1))
/*      */     {
/*      */     case '/':
/*  457 */       mCOMMENT(false);
/*  458 */       break;
/*      */     case '{':
/*  462 */       mACTION(false);
/*  463 */       break;
/*      */     case '"':
/*  467 */       mSTRING_LITERAL(false);
/*  468 */       break;
/*      */     case '\'':
/*  472 */       mCHAR_LITERAL(false);
/*  473 */       break;
/*      */     case '(':
/*  477 */       mSUBRULE_BLOCK(false);
/*  478 */       break;
/*      */     case '\n':
/*      */     case '\r':
/*  482 */       mNEWLINE(false);
/*  483 */       break;
/*      */     default:
/*  486 */       if (_tokenSet_5.member(LA(1)))
/*      */       {
/*  488 */         match(_tokenSet_5);
/*      */       }
/*      */       else
/*      */       {
/*  492 */         throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */       }break;
/*      */     }
/*  495 */     if ((paramBoolean) && (localToken == null) && (i != -1)) {
/*  496 */       localToken = makeToken(i);
/*  497 */       localToken.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/*      */     }
/*  499 */     this._returnToken = localToken;
/*      */   }
/*      */ 
/*      */   public final void mCOMMENT(boolean paramBoolean) throws RecognitionException, CharStreamException, TokenStreamException {
/*  503 */     Token localToken = null; int j = this.text.length();
/*  504 */     int i = 35;
/*      */ 
/*  508 */     if ((LA(1) == '/') && (LA(2) == '/')) {
/*  509 */       mSL_COMMENT(false);
/*      */     }
/*  511 */     else if ((LA(1) == '/') && (LA(2) == '*')) {
/*  512 */       mML_COMMENT(false);
/*      */     }
/*      */     else {
/*  515 */       throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */     }
/*      */ 
/*  519 */     i = -1;
/*  520 */     if ((paramBoolean) && (localToken == null) && (i != -1)) {
/*  521 */       localToken = makeToken(i);
/*  522 */       localToken.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/*      */     }
/*  524 */     this._returnToken = localToken;
/*      */   }
/*      */ 
/*      */   public final void mACTION(boolean paramBoolean) throws RecognitionException, CharStreamException, TokenStreamException {
/*  528 */     Token localToken = null; int j = this.text.length();
/*  529 */     int i = 7;
/*      */ 
/*  532 */     match('{');
/*      */ 
/*  537 */     while (LA(1) != '}') {
/*  538 */       if (((LA(1) == '\n') || (LA(1) == '\r')) && (LA(2) >= '\003') && (LA(2) <= 'ÿ')) {
/*  539 */         mNEWLINE(false);
/*      */       }
/*  541 */       else if ((LA(1) == '{') && (LA(2) >= '\003') && (LA(2) <= 'ÿ')) {
/*  542 */         mACTION(false);
/*      */       }
/*  544 */       else if ((LA(1) == '\'') && (_tokenSet_6.member(LA(2)))) {
/*  545 */         mCHAR_LITERAL(false);
/*      */       }
/*  547 */       else if ((LA(1) == '/') && ((LA(2) == '*') || (LA(2) == '/'))) {
/*  548 */         mCOMMENT(false);
/*      */       }
/*  550 */       else if ((LA(1) == '"') && (LA(2) >= '\003') && (LA(2) <= 'ÿ')) {
/*  551 */         mSTRING_LITERAL(false);
/*      */       } else {
/*  553 */         if ((LA(1) < '\003') || (LA(1) > 'ÿ') || (LA(2) < '\003') || (LA(2) > 'ÿ')) break;
/*  554 */         matchNot(65535);
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/*  562 */     match('}');
/*  563 */     if ((paramBoolean) && (localToken == null) && (i != -1)) {
/*  564 */       localToken = makeToken(i);
/*  565 */       localToken.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/*      */     }
/*  567 */     this._returnToken = localToken;
/*      */   }
/*      */ 
/*      */   public final void mSTRING_LITERAL(boolean paramBoolean) throws RecognitionException, CharStreamException, TokenStreamException {
/*  571 */     Token localToken = null; int j = this.text.length();
/*  572 */     int i = 39;
/*      */ 
/*  575 */     match('"');
/*      */     while (true)
/*      */     {
/*  579 */       if (LA(1) == '\\') {
/*  580 */         mESC(false);
/*      */       } else {
/*  582 */         if (!_tokenSet_7.member(LA(1))) break;
/*  583 */         matchNot('"');
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/*  591 */     match('"');
/*  592 */     if ((paramBoolean) && (localToken == null) && (i != -1)) {
/*  593 */       localToken = makeToken(i);
/*  594 */       localToken.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/*      */     }
/*  596 */     this._returnToken = localToken;
/*      */   }
/*      */ 
/*      */   public final void mCHAR_LITERAL(boolean paramBoolean) throws RecognitionException, CharStreamException, TokenStreamException {
/*  600 */     Token localToken = null; int j = this.text.length();
/*  601 */     int i = 38;
/*      */ 
/*  604 */     match('\'');
/*      */ 
/*  606 */     if (LA(1) == '\\') {
/*  607 */       mESC(false);
/*      */     }
/*  609 */     else if (_tokenSet_8.member(LA(1))) {
/*  610 */       matchNot('\'');
/*      */     }
/*      */     else {
/*  613 */       throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */     }
/*      */ 
/*  617 */     match('\'');
/*  618 */     if ((paramBoolean) && (localToken == null) && (i != -1)) {
/*  619 */       localToken = makeToken(i);
/*  620 */       localToken.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/*      */     }
/*  622 */     this._returnToken = localToken;
/*      */   }
/*      */ 
/*      */   protected final void mNEWLINE(boolean paramBoolean) throws RecognitionException, CharStreamException, TokenStreamException {
/*  626 */     Token localToken = null; int j = this.text.length();
/*  627 */     int i = 34;
/*      */ 
/*  631 */     if ((LA(1) == '\r') && (LA(2) == '\n')) {
/*  632 */       match('\r');
/*  633 */       match('\n');
/*  634 */       newline();
/*      */     }
/*  636 */     else if (LA(1) == '\r') {
/*  637 */       match('\r');
/*  638 */       newline();
/*      */     }
/*  640 */     else if (LA(1) == '\n') {
/*  641 */       match('\n');
/*  642 */       newline();
/*      */     }
/*      */     else {
/*  645 */       throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */     }
/*      */ 
/*  649 */     if ((paramBoolean) && (localToken == null) && (i != -1)) {
/*  650 */       localToken = makeToken(i);
/*  651 */       localToken.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/*      */     }
/*  653 */     this._returnToken = localToken;
/*      */   }
/*      */ 
/*      */   public final void mBANG(boolean paramBoolean) throws RecognitionException, CharStreamException, TokenStreamException {
/*  657 */     Token localToken = null; int j = this.text.length();
/*  658 */     int i = 19;
/*      */ 
/*  661 */     match('!');
/*  662 */     if ((paramBoolean) && (localToken == null) && (i != -1)) {
/*  663 */       localToken = makeToken(i);
/*  664 */       localToken.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/*      */     }
/*  666 */     this._returnToken = localToken;
/*      */   }
/*      */ 
/*      */   public final void mSEMI(boolean paramBoolean) throws RecognitionException, CharStreamException, TokenStreamException {
/*  670 */     Token localToken = null; int j = this.text.length();
/*  671 */     int i = 11;
/*      */ 
/*  674 */     match(';');
/*  675 */     if ((paramBoolean) && (localToken == null) && (i != -1)) {
/*  676 */       localToken = makeToken(i);
/*  677 */       localToken.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/*      */     }
/*  679 */     this._returnToken = localToken;
/*      */   }
/*      */ 
/*      */   public final void mCOMMA(boolean paramBoolean) throws RecognitionException, CharStreamException, TokenStreamException {
/*  683 */     Token localToken = null; int j = this.text.length();
/*  684 */     int i = 24;
/*      */ 
/*  687 */     match(',');
/*  688 */     if ((paramBoolean) && (localToken == null) && (i != -1)) {
/*  689 */       localToken = makeToken(i);
/*  690 */       localToken.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/*      */     }
/*  692 */     this._returnToken = localToken;
/*      */   }
/*      */ 
/*      */   public final void mRCURLY(boolean paramBoolean) throws RecognitionException, CharStreamException, TokenStreamException {
/*  696 */     Token localToken = null; int j = this.text.length();
/*  697 */     int i = 15;
/*      */ 
/*  700 */     match('}');
/*  701 */     if ((paramBoolean) && (localToken == null) && (i != -1)) {
/*  702 */       localToken = makeToken(i);
/*  703 */       localToken.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/*      */     }
/*  705 */     this._returnToken = localToken;
/*      */   }
/*      */ 
/*      */   public final void mLPAREN(boolean paramBoolean) throws RecognitionException, CharStreamException, TokenStreamException {
/*  709 */     Token localToken = null; int j = this.text.length();
/*  710 */     int i = 29;
/*      */ 
/*  713 */     match('(');
/*  714 */     if ((paramBoolean) && (localToken == null) && (i != -1)) {
/*  715 */       localToken = makeToken(i);
/*  716 */       localToken.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/*      */     }
/*  718 */     this._returnToken = localToken;
/*      */   }
/*      */ 
/*      */   public final void mRPAREN(boolean paramBoolean) throws RecognitionException, CharStreamException, TokenStreamException {
/*  722 */     Token localToken = null; int j = this.text.length();
/*  723 */     int i = 30;
/*      */ 
/*  726 */     match(')');
/*  727 */     if ((paramBoolean) && (localToken == null) && (i != -1)) {
/*  728 */       localToken = makeToken(i);
/*  729 */       localToken.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/*      */     }
/*  731 */     this._returnToken = localToken;
/*      */   }
/*      */ 
/*      */   public final void mID_OR_KEYWORD(boolean paramBoolean)
/*      */     throws RecognitionException, CharStreamException, TokenStreamException
/*      */   {
/*  742 */     Token localToken1 = null; int j = this.text.length();
/*  743 */     int i = 31;
/*      */ 
/*  745 */     Token localToken2 = null;
/*      */ 
/*  747 */     mID(true);
/*  748 */     localToken2 = this._returnToken;
/*  749 */     i = localToken2.getType();
/*      */ 
/*  751 */     if ((_tokenSet_9.member(LA(1))) && (LA(2) >= '\003') && (LA(2) <= 'ÿ') && (localToken2.getText().equals("header")))
/*      */     {
/*  753 */       if ((_tokenSet_1.member(LA(1))) && (_tokenSet_9.member(LA(2)))) {
/*  754 */         mWS(false);
/*      */       }
/*  756 */       else if ((!_tokenSet_9.member(LA(1))) || (LA(2) < '\003') || (LA(2) > 'ÿ'))
/*      */       {
/*  759 */         throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */       }
/*      */ 
/*  764 */       switch (LA(1))
/*      */       {
/*      */       case '"':
/*  767 */         mSTRING_LITERAL(false);
/*  768 */         break;
/*      */       case '\t':
/*      */       case '\n':
/*      */       case '\r':
/*      */       case ' ':
/*      */       case '/':
/*      */       case '{':
/*  773 */         break;
/*      */       default:
/*  777 */         throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */       }
/*      */ 
/*      */       while (true)
/*      */       {
/*  784 */         switch (LA(1)) { case '\t':
/*      */         case '\n':
/*      */         case '\r':
/*      */         case ' ':
/*  787 */           mWS(false);
/*  788 */           break;
/*      */         case '/':
/*  792 */           mCOMMENT(false);
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/*  802 */       mACTION(false);
/*  803 */       i = 5;
/*      */     }
/*  805 */     else if ((_tokenSet_10.member(LA(1))) && (LA(2) >= '\003') && (LA(2) <= 'ÿ') && (localToken2.getText().equals("tokens")))
/*      */     {
/*      */       while (true)
/*      */       {
/*  809 */         switch (LA(1)) { case '\t':
/*      */         case '\n':
/*      */         case '\r':
/*      */         case ' ':
/*  812 */           mWS(false);
/*  813 */           break;
/*      */         case '/':
/*  817 */           mCOMMENT(false);
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/*  827 */       mCURLY_BLOCK_SCARF(false);
/*  828 */       i = 12;
/*      */     }
/*  830 */     else if ((_tokenSet_10.member(LA(1))) && (localToken2.getText().equals("options")))
/*      */     {
/*      */       while (true)
/*      */       {
/*  834 */         switch (LA(1)) { case '\t':
/*      */         case '\n':
/*      */         case '\r':
/*      */         case ' ':
/*  837 */           mWS(false);
/*  838 */           break;
/*      */         case '/':
/*  842 */           mCOMMENT(false);
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/*  852 */       match('{');
/*  853 */       i = 13;
/*      */     }
/*      */ 
/*  859 */     if ((paramBoolean) && (localToken1 == null) && (i != -1)) {
/*  860 */       localToken1 = makeToken(i);
/*  861 */       localToken1.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/*      */     }
/*  863 */     this._returnToken = localToken1;
/*      */   }
/*      */ 
/*      */   protected final void mID(boolean paramBoolean) throws RecognitionException, CharStreamException, TokenStreamException {
/*  867 */     Token localToken = null; int j = this.text.length();
/*  868 */     int i = 9;
/*      */ 
/*  872 */     switch (LA(1)) { case 'a':
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
/*  881 */       matchRange('a', 'z');
/*  882 */       break;
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
/*  892 */       matchRange('A', 'Z');
/*  893 */       break;
/*      */     case '_':
/*  897 */       match('_');
/*  898 */       break;
/*      */     case '[':
/*      */     case '\\':
/*      */     case ']':
/*      */     case '^':
/*      */     case '`':
/*      */     default:
/*  902 */       throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */     }
/*      */ 
/*      */     while (true)
/*      */     {
/*  909 */       switch (LA(1)) { case 'a':
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
/*  918 */         matchRange('a', 'z');
/*  919 */         break;
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
/*  929 */         matchRange('A', 'Z');
/*  930 */         break;
/*      */       case '_':
/*  934 */         match('_');
/*  935 */         break;
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
/*  941 */         matchRange('0', '9');
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
/*  951 */       case '`': }  } i = testLiteralsTable(new String(this.text.getBuffer(), j, this.text.length() - j), i);
/*  952 */     if ((paramBoolean) && (localToken == null) && (i != -1)) {
/*  953 */       localToken = makeToken(i);
/*  954 */       localToken.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/*      */     }
/*  956 */     this._returnToken = localToken;
/*      */   }
/*      */ 
/*      */   protected final void mCURLY_BLOCK_SCARF(boolean paramBoolean) throws RecognitionException, CharStreamException, TokenStreamException {
/*  960 */     Token localToken = null; int j = this.text.length();
/*  961 */     int i = 32;
/*      */ 
/*  964 */     match('{');
/*      */ 
/*  969 */     while (LA(1) != '}') {
/*  970 */       if (((LA(1) == '\n') || (LA(1) == '\r')) && (LA(2) >= '\003') && (LA(2) <= 'ÿ')) {
/*  971 */         mNEWLINE(false);
/*      */       }
/*  973 */       else if ((LA(1) == '"') && (LA(2) >= '\003') && (LA(2) <= 'ÿ')) {
/*  974 */         mSTRING_LITERAL(false);
/*      */       }
/*  976 */       else if ((LA(1) == '\'') && (_tokenSet_6.member(LA(2)))) {
/*  977 */         mCHAR_LITERAL(false);
/*      */       }
/*  979 */       else if ((LA(1) == '/') && ((LA(2) == '*') || (LA(2) == '/'))) {
/*  980 */         mCOMMENT(false);
/*      */       } else {
/*  982 */         if ((LA(1) < '\003') || (LA(1) > 'ÿ') || (LA(2) < '\003') || (LA(2) > 'ÿ')) break;
/*  983 */         matchNot(65535);
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/*  991 */     match('}');
/*  992 */     if ((paramBoolean) && (localToken == null) && (i != -1)) {
/*  993 */       localToken = makeToken(i);
/*  994 */       localToken.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/*      */     }
/*  996 */     this._returnToken = localToken;
/*      */   }
/*      */ 
/*      */   public final void mASSIGN_RHS(boolean paramBoolean) throws RecognitionException, CharStreamException, TokenStreamException {
/* 1000 */     Token localToken = null; int j = this.text.length();
/* 1001 */     int i = 14;
/*      */ 
/* 1004 */     int k = this.text.length();
/* 1005 */     match('=');
/* 1006 */     this.text.setLength(k);
/*      */ 
/* 1011 */     while (LA(1) != ';') {
/* 1012 */       if ((LA(1) == '"') && (LA(2) >= '\003') && (LA(2) <= 'ÿ')) {
/* 1013 */         mSTRING_LITERAL(false);
/*      */       }
/* 1015 */       else if ((LA(1) == '\'') && (_tokenSet_6.member(LA(2)))) {
/* 1016 */         mCHAR_LITERAL(false);
/*      */       }
/* 1018 */       else if (((LA(1) == '\n') || (LA(1) == '\r')) && (LA(2) >= '\003') && (LA(2) <= 'ÿ')) {
/* 1019 */         mNEWLINE(false);
/*      */       } else {
/* 1021 */         if ((LA(1) < '\003') || (LA(1) > 'ÿ') || (LA(2) < '\003') || (LA(2) > 'ÿ')) break;
/* 1022 */         matchNot(65535);
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 1030 */     match(';');
/* 1031 */     if ((paramBoolean) && (localToken == null) && (i != -1)) {
/* 1032 */       localToken = makeToken(i);
/* 1033 */       localToken.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/*      */     }
/* 1035 */     this._returnToken = localToken;
/*      */   }
/*      */ 
/*      */   protected final void mSL_COMMENT(boolean paramBoolean) throws RecognitionException, CharStreamException, TokenStreamException {
/* 1039 */     Token localToken = null; int j = this.text.length();
/* 1040 */     int i = 36;
/*      */ 
/* 1043 */     match("//");
/*      */ 
/* 1048 */     while ((LA(1) != '\n') && (LA(1) != '\r') && 
/* 1049 */       (LA(1) >= '\003') && (LA(1) <= 'ÿ') && (LA(2) >= '\003') && (LA(2) <= 'ÿ')) {
/* 1050 */       matchNot(65535);
/*      */     }
/*      */ 
/* 1058 */     mNEWLINE(false);
/* 1059 */     if ((paramBoolean) && (localToken == null) && (i != -1)) {
/* 1060 */       localToken = makeToken(i);
/* 1061 */       localToken.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/*      */     }
/* 1063 */     this._returnToken = localToken;
/*      */   }
/*      */ 
/*      */   protected final void mML_COMMENT(boolean paramBoolean) throws RecognitionException, CharStreamException, TokenStreamException {
/* 1067 */     Token localToken = null; int j = this.text.length();
/* 1068 */     int i = 37;
/*      */ 
/* 1071 */     match("/*");
/*      */ 
/* 1076 */     while ((LA(1) != '*') || (LA(2) != '/')) {
/* 1077 */       if (((LA(1) == '\n') || (LA(1) == '\r')) && (LA(2) >= '\003') && (LA(2) <= 'ÿ')) {
/* 1078 */         mNEWLINE(false);
/*      */       } else {
/* 1080 */         if ((LA(1) < '\003') || (LA(1) > 'ÿ') || (LA(2) < '\003') || (LA(2) > 'ÿ')) break;
/* 1081 */         matchNot(65535);
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 1089 */     match("*/");
/* 1090 */     if ((paramBoolean) && (localToken == null) && (i != -1)) {
/* 1091 */       localToken = makeToken(i);
/* 1092 */       localToken.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/*      */     }
/* 1094 */     this._returnToken = localToken;
/*      */   }
/*      */ 
/*      */   protected final void mESC(boolean paramBoolean) throws RecognitionException, CharStreamException, TokenStreamException {
/* 1098 */     Token localToken = null; int j = this.text.length();
/* 1099 */     int i = 40;
/*      */ 
/* 1102 */     match('\\');
/*      */ 
/* 1104 */     switch (LA(1))
/*      */     {
/*      */     case 'n':
/* 1107 */       match('n');
/* 1108 */       break;
/*      */     case 'r':
/* 1112 */       match('r');
/* 1113 */       break;
/*      */     case 't':
/* 1117 */       match('t');
/* 1118 */       break;
/*      */     case 'b':
/* 1122 */       match('b');
/* 1123 */       break;
/*      */     case 'f':
/* 1127 */       match('f');
/* 1128 */       break;
/*      */     case 'w':
/* 1132 */       match('w');
/* 1133 */       break;
/*      */     case 'a':
/* 1137 */       match('a');
/* 1138 */       break;
/*      */     case '"':
/* 1142 */       match('"');
/* 1143 */       break;
/*      */     case '\'':
/* 1147 */       match('\'');
/* 1148 */       break;
/*      */     case '\\':
/* 1152 */       match('\\');
/* 1153 */       break;
/*      */     case '0':
/*      */     case '1':
/*      */     case '2':
/*      */     case '3':
/* 1158 */       matchRange('0', '3');
/*      */ 
/* 1161 */       if ((LA(1) >= '0') && (LA(1) <= '9') && (LA(2) >= '\003') && (LA(2) <= 'ÿ')) {
/* 1162 */         mDIGIT(false);
/*      */ 
/* 1164 */         if ((LA(1) >= '0') && (LA(1) <= '9') && (LA(2) >= '\003') && (LA(2) <= 'ÿ')) {
/* 1165 */           mDIGIT(false);
/*      */         }
/* 1167 */         else if ((LA(1) < '\003') || (LA(1) > 'ÿ'))
/*      */         {
/* 1170 */           throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */         }
/*      */ 
/*      */       }
/* 1175 */       else if ((LA(1) < '\003') || (LA(1) > 'ÿ'))
/*      */       {
/* 1178 */         throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */       }
/*      */ 
/*      */       break;
/*      */     case '4':
/*      */     case '5':
/*      */     case '6':
/*      */     case '7':
/* 1187 */       matchRange('4', '7');
/*      */ 
/* 1190 */       if ((LA(1) >= '0') && (LA(1) <= '9') && (LA(2) >= '\003') && (LA(2) <= 'ÿ')) {
/* 1191 */         mDIGIT(false);
/*      */       }
/* 1193 */       else if ((LA(1) < '\003') || (LA(1) > 'ÿ'))
/*      */       {
/* 1196 */         throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */       }
/*      */ 
/*      */       break;
/*      */     case 'u':
/* 1204 */       match('u');
/* 1205 */       mXDIGIT(false);
/* 1206 */       mXDIGIT(false);
/* 1207 */       mXDIGIT(false);
/* 1208 */       mXDIGIT(false);
/* 1209 */       break;
/*      */     default:
/* 1213 */       throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */     }
/*      */ 
/* 1217 */     if ((paramBoolean) && (localToken == null) && (i != -1)) {
/* 1218 */       localToken = makeToken(i);
/* 1219 */       localToken.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/*      */     }
/* 1221 */     this._returnToken = localToken;
/*      */   }
/*      */ 
/*      */   protected final void mDIGIT(boolean paramBoolean) throws RecognitionException, CharStreamException, TokenStreamException {
/* 1225 */     Token localToken = null; int j = this.text.length();
/* 1226 */     int i = 41;
/*      */ 
/* 1229 */     matchRange('0', '9');
/* 1230 */     if ((paramBoolean) && (localToken == null) && (i != -1)) {
/* 1231 */       localToken = makeToken(i);
/* 1232 */       localToken.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/*      */     }
/* 1234 */     this._returnToken = localToken;
/*      */   }
/*      */ 
/*      */   protected final void mXDIGIT(boolean paramBoolean) throws RecognitionException, CharStreamException, TokenStreamException {
/* 1238 */     Token localToken = null; int j = this.text.length();
/* 1239 */     int i = 42;
/*      */ 
/* 1242 */     switch (LA(1)) { case '0':
/*      */     case '1':
/*      */     case '2':
/*      */     case '3':
/*      */     case '4':
/*      */     case '5':
/*      */     case '6':
/*      */     case '7':
/*      */     case '8':
/*      */     case '9':
/* 1247 */       matchRange('0', '9');
/* 1248 */       break;
/*      */     case 'a':
/*      */     case 'b':
/*      */     case 'c':
/*      */     case 'd':
/*      */     case 'e':
/*      */     case 'f':
/* 1253 */       matchRange('a', 'f');
/* 1254 */       break;
/*      */     case 'A':
/*      */     case 'B':
/*      */     case 'C':
/*      */     case 'D':
/*      */     case 'E':
/*      */     case 'F':
/* 1259 */       matchRange('A', 'F');
/* 1260 */       break;
/*      */     case ':':
/*      */     case ';':
/*      */     case '<':
/*      */     case '=':
/*      */     case '>':
/*      */     case '?':
/*      */     case '@':
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
/*      */     case '[':
/*      */     case '\\':
/*      */     case ']':
/*      */     case '^':
/*      */     case '_':
/*      */     case '`':
/*      */     default:
/* 1264 */       throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */     }
/*      */ 
/* 1267 */     if ((paramBoolean) && (localToken == null) && (i != -1)) {
/* 1268 */       localToken = makeToken(i);
/* 1269 */       localToken.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/*      */     }
/* 1271 */     this._returnToken = localToken;
/*      */   }
/*      */ 
/*      */   public final void mARG_ACTION(boolean paramBoolean) throws RecognitionException, CharStreamException, TokenStreamException {
/* 1275 */     Token localToken = null; int j = this.text.length();
/* 1276 */     int i = 20;
/*      */ 
/* 1279 */     match('[');
/*      */ 
/* 1284 */     while (LA(1) != ']') {
/* 1285 */       if ((LA(1) == '[') && (LA(2) >= '\003') && (LA(2) <= 'ÿ')) {
/* 1286 */         mARG_ACTION(false);
/*      */       }
/* 1288 */       else if (((LA(1) == '\n') || (LA(1) == '\r')) && (LA(2) >= '\003') && (LA(2) <= 'ÿ')) {
/* 1289 */         mNEWLINE(false);
/*      */       }
/* 1291 */       else if ((LA(1) == '\'') && (_tokenSet_6.member(LA(2)))) {
/* 1292 */         mCHAR_LITERAL(false);
/*      */       }
/* 1294 */       else if ((LA(1) == '"') && (LA(2) >= '\003') && (LA(2) <= 'ÿ')) {
/* 1295 */         mSTRING_LITERAL(false);
/*      */       } else {
/* 1297 */         if ((LA(1) < '\003') || (LA(1) > 'ÿ') || (LA(2) < '\003') || (LA(2) > 'ÿ')) break;
/* 1298 */         matchNot(65535);
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 1306 */     match(']');
/* 1307 */     if ((paramBoolean) && (localToken == null) && (i != -1)) {
/* 1308 */       localToken = makeToken(i);
/* 1309 */       localToken.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/*      */     }
/* 1311 */     this._returnToken = localToken;
/*      */   }
/*      */ 
/*      */   private static final long[] mk_tokenSet_0()
/*      */   {
/* 1316 */     long[] arrayOfLong = new long[8];
/* 1317 */     arrayOfLong[0] = -576460752303423496L;
/* 1318 */     for (int i = 1; i <= 3; i++) arrayOfLong[i] = -1L;
/* 1319 */     return arrayOfLong;
/*      */   }
/*      */ 
/*      */   private static final long[] mk_tokenSet_1() {
/* 1323 */     long[] arrayOfLong = { 4294977024L, 0L, 0L, 0L, 0L };
/* 1324 */     return arrayOfLong;
/*      */   }
/*      */ 
/*      */   private static final long[] mk_tokenSet_2() {
/* 1328 */     long[] arrayOfLong = new long[8];
/* 1329 */     arrayOfLong[0] = -2199023255560L;
/* 1330 */     for (int i = 1; i <= 3; i++) arrayOfLong[i] = -1L;
/* 1331 */     return arrayOfLong;
/*      */   }
/*      */ 
/*      */   private static final long[] mk_tokenSet_3() {
/* 1335 */     long[] arrayOfLong = new long[8];
/* 1336 */     arrayOfLong[0] = -576462951326679048L;
/* 1337 */     for (int i = 1; i <= 3; i++) arrayOfLong[i] = -1L;
/* 1338 */     return arrayOfLong;
/*      */   }
/*      */ 
/*      */   private static final long[] mk_tokenSet_4() {
/* 1342 */     long[] arrayOfLong = { 4294977024L, 1152921504606846976L, 0L, 0L, 0L };
/* 1343 */     return arrayOfLong;
/*      */   }
/*      */ 
/*      */   private static final long[] mk_tokenSet_5() {
/* 1347 */     long[] arrayOfLong = new long[8];
/* 1348 */     arrayOfLong[0] = -576605355262354440L;
/* 1349 */     arrayOfLong[1] = -576460752303423489L;
/* 1350 */     for (int i = 2; i <= 3; i++) arrayOfLong[i] = -1L;
/* 1351 */     return arrayOfLong;
/*      */   }
/*      */ 
/*      */   private static final long[] mk_tokenSet_6() {
/* 1355 */     long[] arrayOfLong = new long[8];
/* 1356 */     arrayOfLong[0] = -549755813896L;
/* 1357 */     for (int i = 1; i <= 3; i++) arrayOfLong[i] = -1L;
/* 1358 */     return arrayOfLong;
/*      */   }
/*      */ 
/*      */   private static final long[] mk_tokenSet_7() {
/* 1362 */     long[] arrayOfLong = new long[8];
/* 1363 */     arrayOfLong[0] = -17179869192L;
/* 1364 */     arrayOfLong[1] = -268435457L;
/* 1365 */     for (int i = 2; i <= 3; i++) arrayOfLong[i] = -1L;
/* 1366 */     return arrayOfLong;
/*      */   }
/*      */ 
/*      */   private static final long[] mk_tokenSet_8() {
/* 1370 */     long[] arrayOfLong = new long[8];
/* 1371 */     arrayOfLong[0] = -549755813896L;
/* 1372 */     arrayOfLong[1] = -268435457L;
/* 1373 */     for (int i = 2; i <= 3; i++) arrayOfLong[i] = -1L;
/* 1374 */     return arrayOfLong;
/*      */   }
/*      */ 
/*      */   private static final long[] mk_tokenSet_9() {
/* 1378 */     long[] arrayOfLong = { 140758963201536L, 576460752303423488L, 0L, 0L, 0L };
/* 1379 */     return arrayOfLong;
/*      */   }
/*      */ 
/*      */   private static final long[] mk_tokenSet_10() {
/* 1383 */     long[] arrayOfLong = { 140741783332352L, 576460752303423488L, 0L, 0L, 0L };
/* 1384 */     return arrayOfLong;
/*      */   }
/*      */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     antlr.preprocessor.PreprocessorLexer
 * JD-Core Version:    0.6.2
 */