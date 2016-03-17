/*      */ package antlr;
/*      */ 
/*      */ import antlr.collections.impl.BitSet;
/*      */ import java.io.InputStream;
/*      */ import java.io.Reader;
/*      */ import java.util.Hashtable;
/*      */ 
/*      */ public class ANTLRLexer extends CharScanner
/*      */   implements ANTLRTokenTypes, TokenStream
/*      */ {
/* 1430 */   public static final BitSet _tokenSet_0 = new BitSet(mk_tokenSet_0());
/*      */ 
/* 1438 */   public static final BitSet _tokenSet_1 = new BitSet(mk_tokenSet_1());
/*      */ 
/* 1446 */   public static final BitSet _tokenSet_2 = new BitSet(mk_tokenSet_2());
/*      */ 
/* 1454 */   public static final BitSet _tokenSet_3 = new BitSet(mk_tokenSet_3());
/*      */ 
/* 1461 */   public static final BitSet _tokenSet_4 = new BitSet(mk_tokenSet_4());
/*      */ 
/* 1466 */   public static final BitSet _tokenSet_5 = new BitSet(mk_tokenSet_5());
/*      */ 
/*      */   public static int escapeCharValue(String paramString)
/*      */   {
/*   35 */     if (paramString.charAt(1) != '\\') return 0;
/*   36 */     switch (paramString.charAt(2)) { case 'b':
/*   37 */       return 8;
/*      */     case 'r':
/*   38 */       return 13;
/*      */     case 't':
/*   39 */       return 9;
/*      */     case 'n':
/*   40 */       return 10;
/*      */     case 'f':
/*   41 */       return 12;
/*      */     case '"':
/*   42 */       return 34;
/*      */     case '\'':
/*   43 */       return 39;
/*      */     case '\\':
/*   44 */       return 92;
/*      */     case 'u':
/*   48 */       if (paramString.length() != 8) {
/*   49 */         return 0;
/*      */       }
/*      */ 
/*   52 */       return Character.digit(paramString.charAt(3), 16) * 16 * 16 * 16 + Character.digit(paramString.charAt(4), 16) * 16 * 16 + Character.digit(paramString.charAt(5), 16) * 16 + Character.digit(paramString.charAt(6), 16);
/*      */     case '0':
/*      */     case '1':
/*      */     case '2':
/*      */     case '3':
/*   63 */       if ((paramString.length() > 5) && (Character.isDigit(paramString.charAt(4)))) {
/*   64 */         return (paramString.charAt(2) - '0') * 8 * 8 + (paramString.charAt(3) - '0') * 8 + (paramString.charAt(4) - '0');
/*      */       }
/*   66 */       if ((paramString.length() > 4) && (Character.isDigit(paramString.charAt(3)))) {
/*   67 */         return (paramString.charAt(2) - '0') * 8 + (paramString.charAt(3) - '0');
/*      */       }
/*   69 */       return paramString.charAt(2) - '0';
/*      */     case '4':
/*      */     case '5':
/*      */     case '6':
/*      */     case '7':
/*   75 */       if ((paramString.length() > 4) && (Character.isDigit(paramString.charAt(3)))) {
/*   76 */         return (paramString.charAt(2) - '0') * 8 + (paramString.charAt(3) - '0');
/*      */       }
/*   78 */       return paramString.charAt(2) - '0';
/*      */     }
/*      */ 
/*   81 */     return 0;
/*      */   }
/*      */ 
/*      */   public static int tokenTypeForCharLiteral(String paramString)
/*      */   {
/*   86 */     if (paramString.length() > 3) {
/*   87 */       return escapeCharValue(paramString);
/*      */     }
/*      */ 
/*   90 */     return paramString.charAt(1);
/*      */   }
/*      */ 
/*      */   public ANTLRLexer(InputStream paramInputStream) {
/*   94 */     this(new ByteBuffer(paramInputStream));
/*      */   }
/*      */   public ANTLRLexer(Reader paramReader) {
/*   97 */     this(new CharBuffer(paramReader));
/*      */   }
/*      */   public ANTLRLexer(InputBuffer paramInputBuffer) {
/*  100 */     this(new LexerSharedInputState(paramInputBuffer));
/*      */   }
/*      */   public ANTLRLexer(LexerSharedInputState paramLexerSharedInputState) {
/*  103 */     super(paramLexerSharedInputState);
/*  104 */     this.caseSensitiveLiterals = true;
/*  105 */     setCaseSensitive(true);
/*  106 */     this.literals = new Hashtable();
/*  107 */     this.literals.put(new ANTLRHashString("public", this), new Integer(31));
/*  108 */     this.literals.put(new ANTLRHashString("class", this), new Integer(10));
/*  109 */     this.literals.put(new ANTLRHashString("header", this), new Integer(5));
/*  110 */     this.literals.put(new ANTLRHashString("throws", this), new Integer(37));
/*  111 */     this.literals.put(new ANTLRHashString("lexclass", this), new Integer(9));
/*  112 */     this.literals.put(new ANTLRHashString("catch", this), new Integer(40));
/*  113 */     this.literals.put(new ANTLRHashString("private", this), new Integer(32));
/*  114 */     this.literals.put(new ANTLRHashString("options", this), new Integer(51));
/*  115 */     this.literals.put(new ANTLRHashString("extends", this), new Integer(11));
/*  116 */     this.literals.put(new ANTLRHashString("protected", this), new Integer(30));
/*  117 */     this.literals.put(new ANTLRHashString("TreeParser", this), new Integer(13));
/*  118 */     this.literals.put(new ANTLRHashString("Parser", this), new Integer(29));
/*  119 */     this.literals.put(new ANTLRHashString("Lexer", this), new Integer(12));
/*  120 */     this.literals.put(new ANTLRHashString("returns", this), new Integer(35));
/*  121 */     this.literals.put(new ANTLRHashString("charVocabulary", this), new Integer(18));
/*  122 */     this.literals.put(new ANTLRHashString("tokens", this), new Integer(4));
/*  123 */     this.literals.put(new ANTLRHashString("exception", this), new Integer(39));
/*      */   }
/*      */ 
/*      */   public Token nextToken() throws TokenStreamException {
/*  127 */     Token localToken = null;
/*      */     while (true)
/*      */     {
/*  130 */       Object localObject = null;
/*  131 */       int i = 0;
/*  132 */       resetText();
/*      */       try
/*      */       {
/*  135 */         switch (LA(1)) { case '\t':
/*      */         case '\n':
/*      */         case '\r':
/*      */         case ' ':
/*  138 */           mWS(true);
/*  139 */           localToken = this._returnToken;
/*  140 */           break;
/*      */         case '/':
/*  144 */           mCOMMENT(true);
/*  145 */           localToken = this._returnToken;
/*  146 */           break;
/*      */         case '<':
/*  150 */           mOPEN_ELEMENT_OPTION(true);
/*  151 */           localToken = this._returnToken;
/*  152 */           break;
/*      */         case '>':
/*  156 */           mCLOSE_ELEMENT_OPTION(true);
/*  157 */           localToken = this._returnToken;
/*  158 */           break;
/*      */         case ',':
/*  162 */           mCOMMA(true);
/*  163 */           localToken = this._returnToken;
/*  164 */           break;
/*      */         case '?':
/*  168 */           mQUESTION(true);
/*  169 */           localToken = this._returnToken;
/*  170 */           break;
/*      */         case '#':
/*  174 */           mTREE_BEGIN(true);
/*  175 */           localToken = this._returnToken;
/*  176 */           break;
/*      */         case '(':
/*  180 */           mLPAREN(true);
/*  181 */           localToken = this._returnToken;
/*  182 */           break;
/*      */         case ')':
/*  186 */           mRPAREN(true);
/*  187 */           localToken = this._returnToken;
/*  188 */           break;
/*      */         case ':':
/*  192 */           mCOLON(true);
/*  193 */           localToken = this._returnToken;
/*  194 */           break;
/*      */         case '*':
/*  198 */           mSTAR(true);
/*  199 */           localToken = this._returnToken;
/*  200 */           break;
/*      */         case '+':
/*  204 */           mPLUS(true);
/*  205 */           localToken = this._returnToken;
/*  206 */           break;
/*      */         case ';':
/*  210 */           mSEMI(true);
/*  211 */           localToken = this._returnToken;
/*  212 */           break;
/*      */         case '^':
/*  216 */           mCARET(true);
/*  217 */           localToken = this._returnToken;
/*  218 */           break;
/*      */         case '!':
/*  222 */           mBANG(true);
/*  223 */           localToken = this._returnToken;
/*  224 */           break;
/*      */         case '|':
/*  228 */           mOR(true);
/*  229 */           localToken = this._returnToken;
/*  230 */           break;
/*      */         case '~':
/*  234 */           mNOT_OP(true);
/*  235 */           localToken = this._returnToken;
/*  236 */           break;
/*      */         case '}':
/*  240 */           mRCURLY(true);
/*  241 */           localToken = this._returnToken;
/*  242 */           break;
/*      */         case '\'':
/*  246 */           mCHAR_LITERAL(true);
/*  247 */           localToken = this._returnToken;
/*  248 */           break;
/*      */         case '"':
/*  252 */           mSTRING_LITERAL(true);
/*  253 */           localToken = this._returnToken;
/*  254 */           break;
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
/*  260 */           mINT(true);
/*  261 */           localToken = this._returnToken;
/*  262 */           break;
/*      */         case '[':
/*  266 */           mARG_ACTION(true);
/*  267 */           localToken = this._returnToken;
/*  268 */           break;
/*      */         case '{':
/*  272 */           mACTION(true);
/*  273 */           localToken = this._returnToken;
/*  274 */           break;
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
/*  284 */           mTOKEN_REF(true);
/*  285 */           localToken = this._returnToken;
/*  286 */           break;
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
/*  296 */           mRULE_REF(true);
/*  297 */           localToken = this._returnToken;
/*  298 */           break;
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
/*      */         case '$':
/*      */         case '%':
/*      */         case '&':
/*      */         case '-':
/*      */         case '.':
/*      */         case '=':
/*      */         case '@':
/*      */         case '\\':
/*      */         case ']':
/*      */         case '_':
/*      */         case '`':
/*      */         default:
/*  301 */           if ((LA(1) == '=') && (LA(2) == '>')) {
/*  302 */             mIMPLIES(true);
/*  303 */             localToken = this._returnToken;
/*      */           }
/*  305 */           else if ((LA(1) == '.') && (LA(2) == '.')) {
/*  306 */             mRANGE(true);
/*  307 */             localToken = this._returnToken;
/*      */           }
/*  309 */           else if (LA(1) == '=') {
/*  310 */             mASSIGN(true);
/*  311 */             localToken = this._returnToken;
/*      */           }
/*  313 */           else if (LA(1) == '.') {
/*  314 */             mWILDCARD(true);
/*  315 */             localToken = this._returnToken;
/*      */           }
/*  318 */           else if (LA(1) == 65535) { uponEOF(); this._returnToken = makeToken(1); } else {
/*  319 */             throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */           }
/*      */           break; }
/*  322 */         if (this._returnToken == null) continue;
/*  323 */         i = this._returnToken.getType();
/*  324 */         this._returnToken.setType(i);
/*  325 */         return this._returnToken;
/*      */       }
/*      */       catch (RecognitionException localRecognitionException) {
/*  328 */         throw new TokenStreamRecognitionException(localRecognitionException);
/*      */       }
/*      */       catch (CharStreamException localCharStreamException)
/*      */       {
/*  332 */         if ((localCharStreamException instanceof CharStreamIOException)) {
/*  333 */           throw new TokenStreamIOException(((CharStreamIOException)localCharStreamException).io);
/*      */         }
/*      */ 
/*  336 */         throw new TokenStreamException(localCharStreamException.getMessage());
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   public final void mWS(boolean paramBoolean) throws RecognitionException, CharStreamException, TokenStreamException
/*      */   {
/*  343 */     Token localToken = null; int j = this.text.length();
/*  344 */     int i = 52;
/*      */ 
/*  348 */     switch (LA(1))
/*      */     {
/*      */     case ' ':
/*  351 */       match(' ');
/*  352 */       break;
/*      */     case '\t':
/*  356 */       match('\t');
/*  357 */       break;
/*      */     case '\n':
/*  361 */       match('\n');
/*  362 */       newline();
/*  363 */       break;
/*      */     default:
/*  366 */       if ((LA(1) == '\r') && (LA(2) == '\n')) {
/*  367 */         match('\r');
/*  368 */         match('\n');
/*  369 */         newline();
/*      */       }
/*  371 */       else if (LA(1) == '\r') {
/*  372 */         match('\r');
/*  373 */         newline();
/*      */       }
/*      */       else {
/*  376 */         throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */       }
/*      */       break;
/*      */     }
/*  380 */     i = -1;
/*  381 */     if ((paramBoolean) && (localToken == null) && (i != -1)) {
/*  382 */       localToken = makeToken(i);
/*  383 */       localToken.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/*      */     }
/*  385 */     this._returnToken = localToken;
/*      */   }
/*      */ 
/*      */   public final void mCOMMENT(boolean paramBoolean) throws RecognitionException, CharStreamException, TokenStreamException {
/*  389 */     Token localToken1 = null; int j = this.text.length();
/*  390 */     int i = 53;
/*      */ 
/*  392 */     Token localToken2 = null;
/*      */ 
/*  395 */     if ((LA(1) == '/') && (LA(2) == '/')) {
/*  396 */       mSL_COMMENT(false);
/*      */     }
/*  398 */     else if ((LA(1) == '/') && (LA(2) == '*')) {
/*  399 */       mML_COMMENT(true);
/*  400 */       localToken2 = this._returnToken;
/*  401 */       i = localToken2.getType();
/*      */     }
/*      */     else {
/*  404 */       throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */     }
/*      */ 
/*  408 */     if (i != 8) i = -1;
/*  409 */     if ((paramBoolean) && (localToken1 == null) && (i != -1)) {
/*  410 */       localToken1 = makeToken(i);
/*  411 */       localToken1.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/*      */     }
/*  413 */     this._returnToken = localToken1;
/*      */   }
/*      */ 
/*      */   protected final void mSL_COMMENT(boolean paramBoolean) throws RecognitionException, CharStreamException, TokenStreamException {
/*  417 */     Token localToken = null; int j = this.text.length();
/*  418 */     int i = 54;
/*      */ 
/*  421 */     match("//");
/*      */ 
/*  425 */     while (_tokenSet_0.member(LA(1)))
/*      */     {
/*  427 */       match(_tokenSet_0);
/*      */     }
/*      */ 
/*  437 */     if ((LA(1) == '\r') && (LA(2) == '\n')) {
/*  438 */       match('\r');
/*  439 */       match('\n');
/*      */     }
/*  441 */     else if (LA(1) == '\r') {
/*  442 */       match('\r');
/*      */     }
/*  444 */     else if (LA(1) == '\n') {
/*  445 */       match('\n');
/*      */     }
/*      */     else {
/*  448 */       throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */     }
/*      */ 
/*  452 */     newline();
/*  453 */     if ((paramBoolean) && (localToken == null) && (i != -1)) {
/*  454 */       localToken = makeToken(i);
/*  455 */       localToken.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/*      */     }
/*  457 */     this._returnToken = localToken;
/*      */   }
/*      */ 
/*      */   protected final void mML_COMMENT(boolean paramBoolean) throws RecognitionException, CharStreamException, TokenStreamException {
/*  461 */     Token localToken = null; int j = this.text.length();
/*  462 */     int i = 55;
/*      */ 
/*  465 */     match("/*");
/*      */ 
/*  467 */     if ((LA(1) == '*') && (LA(2) >= '\003') && (LA(2) <= 'ÿ') && (LA(2) != '/')) {
/*  468 */       match('*');
/*  469 */       i = 8;
/*      */     }
/*  471 */     else if ((LA(1) < '\003') || (LA(1) > 'ÿ') || (LA(2) < '\003') || (LA(2) > 'ÿ'))
/*      */     {
/*  474 */       throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */     }
/*      */ 
/*  482 */     while ((LA(1) != '*') || (LA(2) != '/')) {
/*  483 */       if ((LA(1) == '\r') && (LA(2) == '\n')) {
/*  484 */         match('\r');
/*  485 */         match('\n');
/*  486 */         newline();
/*      */       }
/*  488 */       else if ((LA(1) == '\r') && (LA(2) >= '\003') && (LA(2) <= 'ÿ')) {
/*  489 */         match('\r');
/*  490 */         newline();
/*      */       }
/*  492 */       else if ((_tokenSet_0.member(LA(1))) && (LA(2) >= '\003') && (LA(2) <= 'ÿ'))
/*      */       {
/*  494 */         match(_tokenSet_0);
/*      */       }
/*      */       else {
/*  497 */         if (LA(1) != '\n') break;
/*  498 */         match('\n');
/*  499 */         newline();
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/*  507 */     match("*/");
/*  508 */     if ((paramBoolean) && (localToken == null) && (i != -1)) {
/*  509 */       localToken = makeToken(i);
/*  510 */       localToken.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/*      */     }
/*  512 */     this._returnToken = localToken;
/*      */   }
/*      */ 
/*      */   public final void mOPEN_ELEMENT_OPTION(boolean paramBoolean) throws RecognitionException, CharStreamException, TokenStreamException {
/*  516 */     Token localToken = null; int j = this.text.length();
/*  517 */     int i = 25;
/*      */ 
/*  520 */     match('<');
/*  521 */     if ((paramBoolean) && (localToken == null) && (i != -1)) {
/*  522 */       localToken = makeToken(i);
/*  523 */       localToken.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/*      */     }
/*  525 */     this._returnToken = localToken;
/*      */   }
/*      */ 
/*      */   public final void mCLOSE_ELEMENT_OPTION(boolean paramBoolean) throws RecognitionException, CharStreamException, TokenStreamException {
/*  529 */     Token localToken = null; int j = this.text.length();
/*  530 */     int i = 26;
/*      */ 
/*  533 */     match('>');
/*  534 */     if ((paramBoolean) && (localToken == null) && (i != -1)) {
/*  535 */       localToken = makeToken(i);
/*  536 */       localToken.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/*      */     }
/*  538 */     this._returnToken = localToken;
/*      */   }
/*      */ 
/*      */   public final void mCOMMA(boolean paramBoolean) throws RecognitionException, CharStreamException, TokenStreamException {
/*  542 */     Token localToken = null; int j = this.text.length();
/*  543 */     int i = 38;
/*      */ 
/*  546 */     match(',');
/*  547 */     if ((paramBoolean) && (localToken == null) && (i != -1)) {
/*  548 */       localToken = makeToken(i);
/*  549 */       localToken.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/*      */     }
/*  551 */     this._returnToken = localToken;
/*      */   }
/*      */ 
/*      */   public final void mQUESTION(boolean paramBoolean) throws RecognitionException, CharStreamException, TokenStreamException {
/*  555 */     Token localToken = null; int j = this.text.length();
/*  556 */     int i = 45;
/*      */ 
/*  559 */     match('?');
/*  560 */     if ((paramBoolean) && (localToken == null) && (i != -1)) {
/*  561 */       localToken = makeToken(i);
/*  562 */       localToken.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/*      */     }
/*  564 */     this._returnToken = localToken;
/*      */   }
/*      */ 
/*      */   public final void mTREE_BEGIN(boolean paramBoolean) throws RecognitionException, CharStreamException, TokenStreamException {
/*  568 */     Token localToken = null; int j = this.text.length();
/*  569 */     int i = 44;
/*      */ 
/*  572 */     match("#(");
/*  573 */     if ((paramBoolean) && (localToken == null) && (i != -1)) {
/*  574 */       localToken = makeToken(i);
/*  575 */       localToken.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/*      */     }
/*  577 */     this._returnToken = localToken;
/*      */   }
/*      */ 
/*      */   public final void mLPAREN(boolean paramBoolean) throws RecognitionException, CharStreamException, TokenStreamException {
/*  581 */     Token localToken = null; int j = this.text.length();
/*  582 */     int i = 27;
/*      */ 
/*  585 */     match('(');
/*  586 */     if ((paramBoolean) && (localToken == null) && (i != -1)) {
/*  587 */       localToken = makeToken(i);
/*  588 */       localToken.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/*      */     }
/*  590 */     this._returnToken = localToken;
/*      */   }
/*      */ 
/*      */   public final void mRPAREN(boolean paramBoolean) throws RecognitionException, CharStreamException, TokenStreamException {
/*  594 */     Token localToken = null; int j = this.text.length();
/*  595 */     int i = 28;
/*      */ 
/*  598 */     match(')');
/*  599 */     if ((paramBoolean) && (localToken == null) && (i != -1)) {
/*  600 */       localToken = makeToken(i);
/*  601 */       localToken.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/*      */     }
/*  603 */     this._returnToken = localToken;
/*      */   }
/*      */ 
/*      */   public final void mCOLON(boolean paramBoolean) throws RecognitionException, CharStreamException, TokenStreamException {
/*  607 */     Token localToken = null; int j = this.text.length();
/*  608 */     int i = 36;
/*      */ 
/*  611 */     match(':');
/*  612 */     if ((paramBoolean) && (localToken == null) && (i != -1)) {
/*  613 */       localToken = makeToken(i);
/*  614 */       localToken.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/*      */     }
/*  616 */     this._returnToken = localToken;
/*      */   }
/*      */ 
/*      */   public final void mSTAR(boolean paramBoolean) throws RecognitionException, CharStreamException, TokenStreamException {
/*  620 */     Token localToken = null; int j = this.text.length();
/*  621 */     int i = 46;
/*      */ 
/*  624 */     match('*');
/*  625 */     if ((paramBoolean) && (localToken == null) && (i != -1)) {
/*  626 */       localToken = makeToken(i);
/*  627 */       localToken.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/*      */     }
/*  629 */     this._returnToken = localToken;
/*      */   }
/*      */ 
/*      */   public final void mPLUS(boolean paramBoolean) throws RecognitionException, CharStreamException, TokenStreamException {
/*  633 */     Token localToken = null; int j = this.text.length();
/*  634 */     int i = 47;
/*      */ 
/*  637 */     match('+');
/*  638 */     if ((paramBoolean) && (localToken == null) && (i != -1)) {
/*  639 */       localToken = makeToken(i);
/*  640 */       localToken.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/*      */     }
/*  642 */     this._returnToken = localToken;
/*      */   }
/*      */ 
/*      */   public final void mASSIGN(boolean paramBoolean) throws RecognitionException, CharStreamException, TokenStreamException {
/*  646 */     Token localToken = null; int j = this.text.length();
/*  647 */     int i = 15;
/*      */ 
/*  650 */     match('=');
/*  651 */     if ((paramBoolean) && (localToken == null) && (i != -1)) {
/*  652 */       localToken = makeToken(i);
/*  653 */       localToken.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/*      */     }
/*  655 */     this._returnToken = localToken;
/*      */   }
/*      */ 
/*      */   public final void mIMPLIES(boolean paramBoolean) throws RecognitionException, CharStreamException, TokenStreamException {
/*  659 */     Token localToken = null; int j = this.text.length();
/*  660 */     int i = 48;
/*      */ 
/*  663 */     match("=>");
/*  664 */     if ((paramBoolean) && (localToken == null) && (i != -1)) {
/*  665 */       localToken = makeToken(i);
/*  666 */       localToken.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/*      */     }
/*  668 */     this._returnToken = localToken;
/*      */   }
/*      */ 
/*      */   public final void mSEMI(boolean paramBoolean) throws RecognitionException, CharStreamException, TokenStreamException {
/*  672 */     Token localToken = null; int j = this.text.length();
/*  673 */     int i = 16;
/*      */ 
/*  676 */     match(';');
/*  677 */     if ((paramBoolean) && (localToken == null) && (i != -1)) {
/*  678 */       localToken = makeToken(i);
/*  679 */       localToken.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/*      */     }
/*  681 */     this._returnToken = localToken;
/*      */   }
/*      */ 
/*      */   public final void mCARET(boolean paramBoolean) throws RecognitionException, CharStreamException, TokenStreamException {
/*  685 */     Token localToken = null; int j = this.text.length();
/*  686 */     int i = 49;
/*      */ 
/*  689 */     match('^');
/*  690 */     if ((paramBoolean) && (localToken == null) && (i != -1)) {
/*  691 */       localToken = makeToken(i);
/*  692 */       localToken.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/*      */     }
/*  694 */     this._returnToken = localToken;
/*      */   }
/*      */ 
/*      */   public final void mBANG(boolean paramBoolean) throws RecognitionException, CharStreamException, TokenStreamException {
/*  698 */     Token localToken = null; int j = this.text.length();
/*  699 */     int i = 33;
/*      */ 
/*  702 */     match('!');
/*  703 */     if ((paramBoolean) && (localToken == null) && (i != -1)) {
/*  704 */       localToken = makeToken(i);
/*  705 */       localToken.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/*      */     }
/*  707 */     this._returnToken = localToken;
/*      */   }
/*      */ 
/*      */   public final void mOR(boolean paramBoolean) throws RecognitionException, CharStreamException, TokenStreamException {
/*  711 */     Token localToken = null; int j = this.text.length();
/*  712 */     int i = 21;
/*      */ 
/*  715 */     match('|');
/*  716 */     if ((paramBoolean) && (localToken == null) && (i != -1)) {
/*  717 */       localToken = makeToken(i);
/*  718 */       localToken.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/*      */     }
/*  720 */     this._returnToken = localToken;
/*      */   }
/*      */ 
/*      */   public final void mWILDCARD(boolean paramBoolean) throws RecognitionException, CharStreamException, TokenStreamException {
/*  724 */     Token localToken = null; int j = this.text.length();
/*  725 */     int i = 50;
/*      */ 
/*  728 */     match('.');
/*  729 */     if ((paramBoolean) && (localToken == null) && (i != -1)) {
/*  730 */       localToken = makeToken(i);
/*  731 */       localToken.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/*      */     }
/*  733 */     this._returnToken = localToken;
/*      */   }
/*      */ 
/*      */   public final void mRANGE(boolean paramBoolean) throws RecognitionException, CharStreamException, TokenStreamException {
/*  737 */     Token localToken = null; int j = this.text.length();
/*  738 */     int i = 22;
/*      */ 
/*  741 */     match("..");
/*  742 */     if ((paramBoolean) && (localToken == null) && (i != -1)) {
/*  743 */       localToken = makeToken(i);
/*  744 */       localToken.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/*      */     }
/*  746 */     this._returnToken = localToken;
/*      */   }
/*      */ 
/*      */   public final void mNOT_OP(boolean paramBoolean) throws RecognitionException, CharStreamException, TokenStreamException {
/*  750 */     Token localToken = null; int j = this.text.length();
/*  751 */     int i = 42;
/*      */ 
/*  754 */     match('~');
/*  755 */     if ((paramBoolean) && (localToken == null) && (i != -1)) {
/*  756 */       localToken = makeToken(i);
/*  757 */       localToken.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/*      */     }
/*  759 */     this._returnToken = localToken;
/*      */   }
/*      */ 
/*      */   public final void mRCURLY(boolean paramBoolean) throws RecognitionException, CharStreamException, TokenStreamException {
/*  763 */     Token localToken = null; int j = this.text.length();
/*  764 */     int i = 17;
/*      */ 
/*  767 */     match('}');
/*  768 */     if ((paramBoolean) && (localToken == null) && (i != -1)) {
/*  769 */       localToken = makeToken(i);
/*  770 */       localToken.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/*      */     }
/*  772 */     this._returnToken = localToken;
/*      */   }
/*      */ 
/*      */   public final void mCHAR_LITERAL(boolean paramBoolean) throws RecognitionException, CharStreamException, TokenStreamException {
/*  776 */     Token localToken = null; int j = this.text.length();
/*  777 */     int i = 19;
/*      */ 
/*  780 */     match('\'');
/*      */ 
/*  782 */     if (LA(1) == '\\') {
/*  783 */       mESC(false);
/*      */     }
/*  785 */     else if (_tokenSet_1.member(LA(1))) {
/*  786 */       matchNot('\'');
/*      */     }
/*      */     else {
/*  789 */       throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */     }
/*      */ 
/*  793 */     match('\'');
/*  794 */     if ((paramBoolean) && (localToken == null) && (i != -1)) {
/*  795 */       localToken = makeToken(i);
/*  796 */       localToken.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/*      */     }
/*  798 */     this._returnToken = localToken;
/*      */   }
/*      */ 
/*      */   protected final void mESC(boolean paramBoolean) throws RecognitionException, CharStreamException, TokenStreamException {
/*  802 */     Token localToken = null; int j = this.text.length();
/*  803 */     int i = 56;
/*      */ 
/*  806 */     match('\\');
/*      */ 
/*  808 */     switch (LA(1))
/*      */     {
/*      */     case 'n':
/*  811 */       match('n');
/*  812 */       break;
/*      */     case 'r':
/*  816 */       match('r');
/*  817 */       break;
/*      */     case 't':
/*  821 */       match('t');
/*  822 */       break;
/*      */     case 'b':
/*  826 */       match('b');
/*  827 */       break;
/*      */     case 'f':
/*  831 */       match('f');
/*  832 */       break;
/*      */     case 'w':
/*  836 */       match('w');
/*  837 */       break;
/*      */     case 'a':
/*  841 */       match('a');
/*  842 */       break;
/*      */     case '"':
/*  846 */       match('"');
/*  847 */       break;
/*      */     case '\'':
/*  851 */       match('\'');
/*  852 */       break;
/*      */     case '\\':
/*  856 */       match('\\');
/*  857 */       break;
/*      */     case '0':
/*      */     case '1':
/*      */     case '2':
/*      */     case '3':
/*  862 */       matchRange('0', '3');
/*      */ 
/*  865 */       if ((LA(1) >= '0') && (LA(1) <= '7') && (LA(2) >= '\003') && (LA(2) <= 'ÿ')) {
/*  866 */         matchRange('0', '7');
/*      */ 
/*  868 */         if ((LA(1) >= '0') && (LA(1) <= '7') && (LA(2) >= '\003') && (LA(2) <= 'ÿ')) {
/*  869 */           matchRange('0', '7');
/*      */         }
/*  871 */         else if ((LA(1) < '\003') || (LA(1) > 'ÿ'))
/*      */         {
/*  874 */           throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */         }
/*      */ 
/*      */       }
/*  879 */       else if ((LA(1) < '\003') || (LA(1) > 'ÿ'))
/*      */       {
/*  882 */         throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */       }
/*      */ 
/*      */       break;
/*      */     case '4':
/*      */     case '5':
/*      */     case '6':
/*      */     case '7':
/*  891 */       matchRange('4', '7');
/*      */ 
/*  894 */       if ((LA(1) >= '0') && (LA(1) <= '7') && (LA(2) >= '\003') && (LA(2) <= 'ÿ')) {
/*  895 */         matchRange('0', '7');
/*      */       }
/*  897 */       else if ((LA(1) < '\003') || (LA(1) > 'ÿ'))
/*      */       {
/*  900 */         throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */       }
/*      */ 
/*      */       break;
/*      */     case 'u':
/*  908 */       match('u');
/*  909 */       mXDIGIT(false);
/*  910 */       mXDIGIT(false);
/*  911 */       mXDIGIT(false);
/*  912 */       mXDIGIT(false);
/*  913 */       break;
/*      */     default:
/*  917 */       throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */     }
/*      */ 
/*  921 */     if ((paramBoolean) && (localToken == null) && (i != -1)) {
/*  922 */       localToken = makeToken(i);
/*  923 */       localToken.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/*      */     }
/*  925 */     this._returnToken = localToken;
/*      */   }
/*      */ 
/*      */   public final void mSTRING_LITERAL(boolean paramBoolean) throws RecognitionException, CharStreamException, TokenStreamException {
/*  929 */     Token localToken = null; int j = this.text.length();
/*  930 */     int i = 6;
/*      */ 
/*  933 */     match('"');
/*      */     while (true)
/*      */     {
/*  937 */       if (LA(1) == '\\') {
/*  938 */         mESC(false);
/*      */       } else {
/*  940 */         if (!_tokenSet_2.member(LA(1))) break;
/*  941 */         matchNot('"');
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/*  949 */     match('"');
/*  950 */     if ((paramBoolean) && (localToken == null) && (i != -1)) {
/*  951 */       localToken = makeToken(i);
/*  952 */       localToken.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/*      */     }
/*  954 */     this._returnToken = localToken;
/*      */   }
/*      */ 
/*      */   protected final void mXDIGIT(boolean paramBoolean) throws RecognitionException, CharStreamException, TokenStreamException {
/*  958 */     Token localToken = null; int j = this.text.length();
/*  959 */     int i = 58;
/*      */ 
/*  962 */     switch (LA(1)) { case '0':
/*      */     case '1':
/*      */     case '2':
/*      */     case '3':
/*      */     case '4':
/*      */     case '5':
/*      */     case '6':
/*      */     case '7':
/*      */     case '8':
/*      */     case '9':
/*  967 */       matchRange('0', '9');
/*  968 */       break;
/*      */     case 'a':
/*      */     case 'b':
/*      */     case 'c':
/*      */     case 'd':
/*      */     case 'e':
/*      */     case 'f':
/*  973 */       matchRange('a', 'f');
/*  974 */       break;
/*      */     case 'A':
/*      */     case 'B':
/*      */     case 'C':
/*      */     case 'D':
/*      */     case 'E':
/*      */     case 'F':
/*  979 */       matchRange('A', 'F');
/*  980 */       break;
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
/*  984 */       throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */     }
/*      */ 
/*  987 */     if ((paramBoolean) && (localToken == null) && (i != -1)) {
/*  988 */       localToken = makeToken(i);
/*  989 */       localToken.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/*      */     }
/*  991 */     this._returnToken = localToken;
/*      */   }
/*      */ 
/*      */   protected final void mDIGIT(boolean paramBoolean) throws RecognitionException, CharStreamException, TokenStreamException {
/*  995 */     Token localToken = null; int j = this.text.length();
/*  996 */     int i = 57;
/*      */ 
/*  999 */     matchRange('0', '9');
/* 1000 */     if ((paramBoolean) && (localToken == null) && (i != -1)) {
/* 1001 */       localToken = makeToken(i);
/* 1002 */       localToken.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/*      */     }
/* 1004 */     this._returnToken = localToken;
/*      */   }
/*      */ 
/*      */   public final void mINT(boolean paramBoolean) throws RecognitionException, CharStreamException, TokenStreamException {
/* 1008 */     Token localToken = null; int j = this.text.length();
/* 1009 */     int i = 20;
/*      */ 
/* 1013 */     int k = 0;
/*      */     while (true)
/*      */     {
/* 1016 */       if ((LA(1) >= '0') && (LA(1) <= '9')) {
/* 1017 */         matchRange('0', '9');
/*      */       }
/*      */       else {
/* 1020 */         if (k >= 1) break; throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */       }
/*      */ 
/* 1023 */       k++;
/*      */     }
/*      */ 
/* 1026 */     if ((paramBoolean) && (localToken == null) && (i != -1)) {
/* 1027 */       localToken = makeToken(i);
/* 1028 */       localToken.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/*      */     }
/* 1030 */     this._returnToken = localToken;
/*      */   }
/*      */ 
/*      */   public final void mARG_ACTION(boolean paramBoolean) throws RecognitionException, CharStreamException, TokenStreamException {
/* 1034 */     Token localToken = null; int j = this.text.length();
/* 1035 */     int i = 34;
/*      */ 
/* 1038 */     mNESTED_ARG_ACTION(false);
/* 1039 */     setText(StringUtils.stripFrontBack(getText(), "[", "]"));
/* 1040 */     if ((paramBoolean) && (localToken == null) && (i != -1)) {
/* 1041 */       localToken = makeToken(i);
/* 1042 */       localToken.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/*      */     }
/* 1044 */     this._returnToken = localToken;
/*      */   }
/*      */ 
/*      */   protected final void mNESTED_ARG_ACTION(boolean paramBoolean) throws RecognitionException, CharStreamException, TokenStreamException {
/* 1048 */     Token localToken = null; int j = this.text.length();
/* 1049 */     int i = 59;
/*      */ 
/* 1052 */     match('[');
/*      */     while (true)
/*      */     {
/* 1056 */       switch (LA(1))
/*      */       {
/*      */       case '[':
/* 1059 */         mNESTED_ARG_ACTION(false);
/* 1060 */         break;
/*      */       case '\n':
/* 1064 */         match('\n');
/* 1065 */         newline();
/* 1066 */         break;
/*      */       case '\'':
/* 1070 */         mCHAR_LITERAL(false);
/* 1071 */         break;
/*      */       case '"':
/* 1075 */         mSTRING_LITERAL(false);
/* 1076 */         break;
/*      */       default:
/* 1079 */         if ((LA(1) == '\r') && (LA(2) == '\n')) {
/* 1080 */           match('\r');
/* 1081 */           match('\n');
/* 1082 */           newline();
/*      */         }
/* 1084 */         else if ((LA(1) == '\r') && (LA(2) >= '\003') && (LA(2) <= 'ÿ')) {
/* 1085 */           match('\r');
/* 1086 */           newline();
/*      */         } else {
/* 1088 */           if (!_tokenSet_3.member(LA(1))) break label210;
/* 1089 */           matchNot(']');
/*      */         }
/*      */ 
/*      */         break;
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 1097 */     label210: match(']');
/* 1098 */     if ((paramBoolean) && (localToken == null) && (i != -1)) {
/* 1099 */       localToken = makeToken(i);
/* 1100 */       localToken.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/*      */     }
/* 1102 */     this._returnToken = localToken;
/*      */   }
/*      */ 
/*      */   public final void mACTION(boolean paramBoolean) throws RecognitionException, CharStreamException, TokenStreamException {
/* 1106 */     Object localObject = null; int j = this.text.length();
/* 1107 */     int i = 7;
/*      */ 
/* 1109 */     int k = getLine(); int m = getColumn();
/*      */ 
/* 1111 */     mNESTED_ACTION(false);
/*      */ 
/* 1113 */     if (LA(1) == '?') {
/* 1114 */       match('?');
/* 1115 */       i = 43;
/*      */     }
/*      */ 
/* 1122 */     if (i == 7) {
/* 1123 */       setText(StringUtils.stripFrontBack(getText(), "{", "}"));
/*      */     }
/*      */     else {
/* 1126 */       setText(StringUtils.stripFrontBack(getText(), "{", "}?"));
/*      */     }
/* 1128 */     CommonToken localCommonToken = new CommonToken(i, new String(this.text.getBuffer(), j, this.text.length() - j));
/* 1129 */     localCommonToken.setLine(k);
/* 1130 */     localCommonToken.setColumn(m);
/* 1131 */     localObject = localCommonToken;
/*      */ 
/* 1133 */     if ((paramBoolean) && (localObject == null) && (i != -1)) {
/* 1134 */       localObject = makeToken(i);
/* 1135 */       ((Token)localObject).setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/*      */     }
/* 1137 */     this._returnToken = ((Token)localObject);
/*      */   }
/*      */ 
/*      */   protected final void mNESTED_ACTION(boolean paramBoolean) throws RecognitionException, CharStreamException, TokenStreamException {
/* 1141 */     Token localToken = null; int j = this.text.length();
/* 1142 */     int i = 60;
/*      */ 
/* 1145 */     match('{');
/*      */ 
/* 1150 */     while (LA(1) != '}') {
/* 1151 */       if (((LA(1) == '\n') || (LA(1) == '\r')) && (LA(2) >= '\003') && (LA(2) <= 'ÿ'))
/*      */       {
/* 1153 */         if ((LA(1) == '\r') && (LA(2) == '\n')) {
/* 1154 */           match('\r');
/* 1155 */           match('\n');
/* 1156 */           newline();
/*      */         }
/* 1158 */         else if ((LA(1) == '\r') && (LA(2) >= '\003') && (LA(2) <= 'ÿ')) {
/* 1159 */           match('\r');
/* 1160 */           newline();
/*      */         }
/* 1162 */         else if (LA(1) == '\n') {
/* 1163 */           match('\n');
/* 1164 */           newline();
/*      */         }
/*      */         else {
/* 1167 */           throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */         }
/*      */ 
/*      */       }
/* 1172 */       else if ((LA(1) == '{') && (LA(2) >= '\003') && (LA(2) <= 'ÿ')) {
/* 1173 */         mNESTED_ACTION(false);
/*      */       }
/* 1175 */       else if ((LA(1) == '\'') && (_tokenSet_4.member(LA(2)))) {
/* 1176 */         mCHAR_LITERAL(false);
/*      */       }
/* 1178 */       else if ((LA(1) == '/') && ((LA(2) == '*') || (LA(2) == '/'))) {
/* 1179 */         mCOMMENT(false);
/*      */       }
/* 1181 */       else if ((LA(1) == '"') && (LA(2) >= '\003') && (LA(2) <= 'ÿ')) {
/* 1182 */         mSTRING_LITERAL(false);
/*      */       } else {
/* 1184 */         if ((LA(1) < '\003') || (LA(1) > 'ÿ') || (LA(2) < '\003') || (LA(2) > 'ÿ')) break;
/* 1185 */         matchNot(65535);
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 1193 */     match('}');
/* 1194 */     if ((paramBoolean) && (localToken == null) && (i != -1)) {
/* 1195 */       localToken = makeToken(i);
/* 1196 */       localToken.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/*      */     }
/* 1198 */     this._returnToken = localToken;
/*      */   }
/*      */ 
/*      */   public final void mTOKEN_REF(boolean paramBoolean) throws RecognitionException, CharStreamException, TokenStreamException {
/* 1202 */     Token localToken = null; int j = this.text.length();
/* 1203 */     int i = 24;
/*      */ 
/* 1206 */     matchRange('A', 'Z');
/*      */     while (true)
/*      */     {
/* 1210 */       switch (LA(1)) { case 'a':
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
/* 1219 */         matchRange('a', 'z');
/* 1220 */         break;
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
/* 1230 */         matchRange('A', 'Z');
/* 1231 */         break;
/*      */       case '_':
/* 1235 */         match('_');
/* 1236 */         break;
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
/* 1242 */         matchRange('0', '9');
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
/* 1252 */       case '`': }  } i = testLiteralsTable(i);
/* 1253 */     if ((paramBoolean) && (localToken == null) && (i != -1)) {
/* 1254 */       localToken = makeToken(i);
/* 1255 */       localToken.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/*      */     }
/* 1257 */     this._returnToken = localToken;
/*      */   }
/*      */ 
/*      */   public final void mRULE_REF(boolean paramBoolean) throws RecognitionException, CharStreamException, TokenStreamException {
/* 1261 */     Token localToken = null; int j = this.text.length();
/* 1262 */     int i = 41;
/*      */ 
/* 1265 */     int k = 0;
/*      */ 
/* 1268 */     k = mINTERNAL_RULE_REF(false);
/* 1269 */     i = k;
/*      */ 
/* 1271 */     if (k == 51) {
/* 1272 */       mWS_LOOP(false);
/*      */ 
/* 1274 */       if (LA(1) == '{') {
/* 1275 */         match('{');
/* 1276 */         i = 14;
/*      */       }
/*      */ 
/*      */     }
/* 1283 */     else if (k == 4) {
/* 1284 */       mWS_LOOP(false);
/*      */ 
/* 1286 */       if (LA(1) == '{') {
/* 1287 */         match('{');
/* 1288 */         i = 23;
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 1299 */     if ((paramBoolean) && (localToken == null) && (i != -1)) {
/* 1300 */       localToken = makeToken(i);
/* 1301 */       localToken.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/*      */     }
/* 1303 */     this._returnToken = localToken;
/*      */   }
/*      */ 
/*      */   protected final int mINTERNAL_RULE_REF(boolean paramBoolean) throws RecognitionException, CharStreamException, TokenStreamException
/*      */   {
/* 1308 */     Token localToken = null; int k = this.text.length();
/* 1309 */     int j = 62;
/*      */ 
/* 1312 */     int i = 41;
/*      */ 
/* 1315 */     matchRange('a', 'z');
/*      */     while (true)
/*      */     {
/* 1319 */       switch (LA(1)) { case 'a':
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
/* 1328 */         matchRange('a', 'z');
/* 1329 */         break;
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
/* 1339 */         matchRange('A', 'Z');
/* 1340 */         break;
/*      */       case '_':
/* 1344 */         match('_');
/* 1345 */         break;
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
/* 1351 */         matchRange('0', '9');
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
/* 1361 */       case '`': }  } i = testLiteralsTable(i);
/* 1362 */     if ((paramBoolean) && (localToken == null) && (j != -1)) {
/* 1363 */       localToken = makeToken(j);
/* 1364 */       localToken.setText(new String(this.text.getBuffer(), k, this.text.length() - k));
/*      */     }
/* 1366 */     this._returnToken = localToken;
/* 1367 */     return i;
/*      */   }
/*      */ 
/*      */   protected final void mWS_LOOP(boolean paramBoolean) throws RecognitionException, CharStreamException, TokenStreamException {
/* 1371 */     Token localToken = null; int j = this.text.length();
/* 1372 */     int i = 61;
/*      */     while (true)
/*      */     {
/* 1378 */       switch (LA(1)) { case '\t':
/*      */       case '\n':
/*      */       case '\r':
/*      */       case ' ':
/* 1381 */         mWS(false);
/* 1382 */         break;
/*      */       case '/':
/* 1386 */         mCOMMENT(false);
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 1396 */     if ((paramBoolean) && (localToken == null) && (i != -1)) {
/* 1397 */       localToken = makeToken(i);
/* 1398 */       localToken.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/*      */     }
/* 1400 */     this._returnToken = localToken;
/*      */   }
/*      */ 
/*      */   protected final void mWS_OPT(boolean paramBoolean) throws RecognitionException, CharStreamException, TokenStreamException {
/* 1404 */     Token localToken = null; int j = this.text.length();
/* 1405 */     int i = 63;
/*      */ 
/* 1409 */     if (_tokenSet_5.member(LA(1))) {
/* 1410 */       mWS(false);
/*      */     }
/*      */ 
/* 1416 */     if ((paramBoolean) && (localToken == null) && (i != -1)) {
/* 1417 */       localToken = makeToken(i);
/* 1418 */       localToken.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/*      */     }
/* 1420 */     this._returnToken = localToken;
/*      */   }
/*      */ 
/*      */   private static final long[] mk_tokenSet_0()
/*      */   {
/* 1425 */     long[] arrayOfLong = new long[8];
/* 1426 */     arrayOfLong[0] = -9224L;
/* 1427 */     for (int i = 1; i <= 3; i++) arrayOfLong[i] = -1L;
/* 1428 */     return arrayOfLong;
/*      */   }
/*      */ 
/*      */   private static final long[] mk_tokenSet_1() {
/* 1432 */     long[] arrayOfLong = new long[8];
/* 1433 */     arrayOfLong[0] = -549755813896L;
/* 1434 */     arrayOfLong[1] = -268435457L;
/* 1435 */     for (int i = 2; i <= 3; i++) arrayOfLong[i] = -1L;
/* 1436 */     return arrayOfLong;
/*      */   }
/*      */ 
/*      */   private static final long[] mk_tokenSet_2() {
/* 1440 */     long[] arrayOfLong = new long[8];
/* 1441 */     arrayOfLong[0] = -17179869192L;
/* 1442 */     arrayOfLong[1] = -268435457L;
/* 1443 */     for (int i = 2; i <= 3; i++) arrayOfLong[i] = -1L;
/* 1444 */     return arrayOfLong;
/*      */   }
/*      */ 
/*      */   private static final long[] mk_tokenSet_3() {
/* 1448 */     long[] arrayOfLong = new long[8];
/* 1449 */     arrayOfLong[0] = -566935692296L;
/* 1450 */     arrayOfLong[1] = -671088641L;
/* 1451 */     for (int i = 2; i <= 3; i++) arrayOfLong[i] = -1L;
/* 1452 */     return arrayOfLong;
/*      */   }
/*      */ 
/*      */   private static final long[] mk_tokenSet_4() {
/* 1456 */     long[] arrayOfLong = new long[8];
/* 1457 */     arrayOfLong[0] = -549755813896L;
/* 1458 */     for (int i = 1; i <= 3; i++) arrayOfLong[i] = -1L;
/* 1459 */     return arrayOfLong;
/*      */   }
/*      */ 
/*      */   private static final long[] mk_tokenSet_5() {
/* 1463 */     long[] arrayOfLong = { 4294977024L, 0L, 0L, 0L, 0L };
/* 1464 */     return arrayOfLong;
/*      */   }
/*      */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     antlr.ANTLRLexer
 * JD-Core Version:    0.6.2
 */