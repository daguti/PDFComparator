/*      */ package org.antlr.grammar.v2;
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
/*      */ import org.antlr.tool.ErrorManager;
/*      */ import org.antlr.tool.Grammar;
/*      */ 
/*      */ public class ANTLRLexer extends CharScanner
/*      */   implements ANTLRTokenTypes, TokenStream
/*      */ {
/*   83 */   public boolean hasASTOperator = false;
/*      */ 
/* 1787 */   public static final BitSet _tokenSet_0 = new BitSet(mk_tokenSet_0());
/*      */ 
/* 1795 */   public static final BitSet _tokenSet_1 = new BitSet(mk_tokenSet_1());
/*      */ 
/* 1800 */   public static final BitSet _tokenSet_2 = new BitSet(mk_tokenSet_2());
/*      */ 
/* 1807 */   public static final BitSet _tokenSet_3 = new BitSet(mk_tokenSet_3());
/*      */ 
/* 1815 */   public static final BitSet _tokenSet_4 = new BitSet(mk_tokenSet_4());
/*      */ 
/* 1823 */   public static final BitSet _tokenSet_5 = new BitSet(mk_tokenSet_5());
/*      */ 
/* 1831 */   public static final BitSet _tokenSet_6 = new BitSet(mk_tokenSet_6());
/*      */ 
/* 1838 */   public static final BitSet _tokenSet_7 = new BitSet(mk_tokenSet_7());
/*      */ 
/* 1843 */   public static final BitSet _tokenSet_8 = new BitSet(mk_tokenSet_8());
/*      */ 
/*      */   public void tab()
/*      */   {
/*   81 */     setColumn(getColumn() + 1);
/*      */   }
/*      */ 
/*      */   public ANTLRLexer(InputStream in) {
/*   85 */     this(new ByteBuffer(in));
/*      */   }
/*      */   public ANTLRLexer(Reader in) {
/*   88 */     this(new CharBuffer(in));
/*      */   }
/*      */   public ANTLRLexer(InputBuffer ib) {
/*   91 */     this(new LexerSharedInputState(ib));
/*      */   }
/*      */   public ANTLRLexer(LexerSharedInputState state) {
/*   94 */     super(state);
/*   95 */     this.caseSensitiveLiterals = true;
/*   96 */     setCaseSensitive(true);
/*   97 */     this.literals = new Hashtable();
/*   98 */     this.literals.put(new ANTLRHashString("lexer", this), new Integer(43));
/*   99 */     this.literals.put(new ANTLRHashString("scope", this), new Integer(33));
/*  100 */     this.literals.put(new ANTLRHashString("finally", this), new Integer(67));
/*  101 */     this.literals.put(new ANTLRHashString("throws", this), new Integer(62));
/*  102 */     this.literals.put(new ANTLRHashString("import", this), new Integer(34));
/*  103 */     this.literals.put(new ANTLRHashString("fragment", this), new Integer(38));
/*  104 */     this.literals.put(new ANTLRHashString("private", this), new Integer(58));
/*  105 */     this.literals.put(new ANTLRHashString("grammar", this), new Integer(45));
/*  106 */     this.literals.put(new ANTLRHashString("tokens", this), new Integer(5));
/*  107 */     this.literals.put(new ANTLRHashString("options", this), new Integer(4));
/*  108 */     this.literals.put(new ANTLRHashString("parser", this), new Integer(6));
/*  109 */     this.literals.put(new ANTLRHashString("tree", this), new Integer(44));
/*  110 */     this.literals.put(new ANTLRHashString("protected", this), new Integer(56));
/*  111 */     this.literals.put(new ANTLRHashString("returns", this), new Integer(61));
/*  112 */     this.literals.put(new ANTLRHashString("public", this), new Integer(57));
/*  113 */     this.literals.put(new ANTLRHashString("catch", this), new Integer(66));
/*      */   }
/*      */ 
/*      */   public Token nextToken() throws TokenStreamException {
/*  117 */     Token theRetToken = null;
/*      */     while (true)
/*      */     {
/*  120 */       Token _token = null;
/*  121 */       int _ttype = 0;
/*  122 */       resetText();
/*      */       try
/*      */       {
/*  125 */         switch (LA(1)) { case '\t':
/*      */         case '\n':
/*      */         case '\r':
/*      */         case ' ':
/*  128 */           mWS(true);
/*  129 */           theRetToken = this._returnToken;
/*  130 */           break;
/*      */         case '/':
/*  134 */           mCOMMENT(true);
/*  135 */           theRetToken = this._returnToken;
/*  136 */           break;
/*      */         case '>':
/*  140 */           mCLOSE_ELEMENT_OPTION(true);
/*  141 */           theRetToken = this._returnToken;
/*  142 */           break;
/*      */         case '@':
/*  146 */           mAMPERSAND(true);
/*  147 */           theRetToken = this._returnToken;
/*  148 */           break;
/*      */         case ',':
/*  152 */           mCOMMA(true);
/*  153 */           theRetToken = this._returnToken;
/*  154 */           break;
/*      */         case '?':
/*  158 */           mQUESTION(true);
/*  159 */           theRetToken = this._returnToken;
/*  160 */           break;
/*      */         case '(':
/*  164 */           mLPAREN(true);
/*  165 */           theRetToken = this._returnToken;
/*  166 */           break;
/*      */         case ')':
/*  170 */           mRPAREN(true);
/*  171 */           theRetToken = this._returnToken;
/*  172 */           break;
/*      */         case ':':
/*  176 */           mCOLON(true);
/*  177 */           theRetToken = this._returnToken;
/*  178 */           break;
/*      */         case '*':
/*  182 */           mSTAR(true);
/*  183 */           theRetToken = this._returnToken;
/*  184 */           break;
/*      */         case '-':
/*  188 */           mREWRITE(true);
/*  189 */           theRetToken = this._returnToken;
/*  190 */           break;
/*      */         case ';':
/*  194 */           mSEMI(true);
/*  195 */           theRetToken = this._returnToken;
/*  196 */           break;
/*      */         case '!':
/*  200 */           mBANG(true);
/*  201 */           theRetToken = this._returnToken;
/*  202 */           break;
/*      */         case '|':
/*  206 */           mOR(true);
/*  207 */           theRetToken = this._returnToken;
/*  208 */           break;
/*      */         case '~':
/*  212 */           mNOT(true);
/*  213 */           theRetToken = this._returnToken;
/*  214 */           break;
/*      */         case '}':
/*  218 */           mRCURLY(true);
/*  219 */           theRetToken = this._returnToken;
/*  220 */           break;
/*      */         case '$':
/*  224 */           mDOLLAR(true);
/*  225 */           theRetToken = this._returnToken;
/*  226 */           break;
/*      */         case ']':
/*  230 */           mSTRAY_BRACKET(true);
/*  231 */           theRetToken = this._returnToken;
/*  232 */           break;
/*      */         case '\'':
/*  236 */           mCHAR_LITERAL(true);
/*  237 */           theRetToken = this._returnToken;
/*  238 */           break;
/*      */         case '"':
/*  242 */           mDOUBLE_QUOTE_STRING_LITERAL(true);
/*  243 */           theRetToken = this._returnToken;
/*  244 */           break;
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
/*  250 */           mINT(true);
/*  251 */           theRetToken = this._returnToken;
/*  252 */           break;
/*      */         case '[':
/*  256 */           mARG_ACTION(true);
/*  257 */           theRetToken = this._returnToken;
/*  258 */           break;
/*      */         case '{':
/*  262 */           mACTION(true);
/*  263 */           theRetToken = this._returnToken;
/*  264 */           break;
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
/*  274 */           mTOKEN_REF(true);
/*  275 */           theRetToken = this._returnToken;
/*  276 */           break;
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
/*  286 */           mRULE_REF(true);
/*  287 */           theRetToken = this._returnToken;
/*  288 */           break;
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
/*      */         case '%':
/*      */         case '&':
/*      */         case '+':
/*      */         case '.':
/*      */         case '<':
/*      */         case '=':
/*      */         case '\\':
/*      */         case '^':
/*      */         case '_':
/*      */         case '`':
/*      */         default:
/*  291 */           if ((LA(1) == '.') && (LA(2) == '.') && (LA(3) == '.')) {
/*  292 */             mETC(true);
/*  293 */             theRetToken = this._returnToken;
/*      */           }
/*  295 */           else if ((LA(1) == '^') && (LA(2) == '(')) {
/*  296 */             mTREE_BEGIN(true);
/*  297 */             theRetToken = this._returnToken;
/*      */           }
/*  299 */           else if ((LA(1) == '+') && (LA(2) == '=')) {
/*  300 */             mPLUS_ASSIGN(true);
/*  301 */             theRetToken = this._returnToken;
/*      */           }
/*  303 */           else if ((LA(1) == '=') && (LA(2) == '>')) {
/*  304 */             mIMPLIES(true);
/*  305 */             theRetToken = this._returnToken;
/*      */           }
/*  307 */           else if ((LA(1) == '.') && (LA(2) == '.')) {
/*  308 */             mRANGE(true);
/*  309 */             theRetToken = this._returnToken;
/*      */           }
/*  311 */           else if ((LA(1) == '<') && (LA(2) == '<')) {
/*  312 */             mDOUBLE_ANGLE_STRING_LITERAL(true);
/*  313 */             theRetToken = this._returnToken;
/*      */           }
/*  315 */           else if (LA(1) == '<') {
/*  316 */             mOPEN_ELEMENT_OPTION(true);
/*  317 */             theRetToken = this._returnToken;
/*      */           }
/*  319 */           else if (LA(1) == '+') {
/*  320 */             mPLUS(true);
/*  321 */             theRetToken = this._returnToken;
/*      */           }
/*  323 */           else if (LA(1) == '=') {
/*  324 */             mASSIGN(true);
/*  325 */             theRetToken = this._returnToken;
/*      */           }
/*  327 */           else if (LA(1) == '^') {
/*  328 */             mROOT(true);
/*  329 */             theRetToken = this._returnToken;
/*      */           }
/*  331 */           else if (LA(1) == '.') {
/*  332 */             mWILDCARD(true);
/*  333 */             theRetToken = this._returnToken;
/*      */           }
/*  336 */           else if (LA(1) == 65535) { uponEOF(); this._returnToken = makeToken(1); } else {
/*  337 */             throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */           }
/*      */           break; }
/*  340 */         if (this._returnToken == null) continue;
/*  341 */         _ttype = this._returnToken.getType();
/*  342 */         this._returnToken.setType(_ttype);
/*  343 */         return this._returnToken;
/*      */       }
/*      */       catch (RecognitionException e) {
/*  346 */         throw new TokenStreamRecognitionException(e);
/*      */       }
/*      */       catch (CharStreamException cse)
/*      */       {
/*  350 */         if ((cse instanceof CharStreamIOException)) {
/*  351 */           throw new TokenStreamIOException(((CharStreamIOException)cse).io);
/*      */         }
/*      */ 
/*  354 */         throw new TokenStreamException(cse.getMessage());
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   public final void mWS(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException
/*      */   {
/*  361 */     Token _token = null; int _begin = this.text.length();
/*  362 */     int _ttype = 85;
/*      */ 
/*  366 */     switch (LA(1))
/*      */     {
/*      */     case ' ':
/*  369 */       match(' ');
/*  370 */       break;
/*      */     case '\t':
/*  374 */       match('\t');
/*  375 */       break;
/*      */     case '\n':
/*      */     case '\r':
/*  380 */       switch (LA(1))
/*      */       {
/*      */       case '\r':
/*  383 */         match('\r');
/*  384 */         break;
/*      */       case '\n':
/*  388 */         break;
/*      */       default:
/*  392 */         throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */       }
/*      */ 
/*  396 */       match('\n');
/*  397 */       if (this.inputState.guessing == 0)
/*  398 */         newline(); break;
/*      */     default:
/*  404 */       throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */     }
/*      */ 
/*  408 */     if ((_createToken) && (_token == null) && (_ttype != -1)) {
/*  409 */       _token = makeToken(_ttype);
/*  410 */       _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
/*      */     }
/*  412 */     this._returnToken = _token;
/*      */   }
/*      */ 
/*      */   public final void mCOMMENT(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
/*  416 */     Token _token = null; int _begin = this.text.length();
/*  417 */     int _ttype = 86;
/*      */ 
/*  419 */     Token t = null;
/*      */ 
/*  422 */     if ((LA(1) == '/') && (LA(2) == '/')) {
/*  423 */       mSL_COMMENT(false);
/*      */     }
/*  425 */     else if ((LA(1) == '/') && (LA(2) == '*')) {
/*  426 */       mML_COMMENT(true);
/*  427 */       t = this._returnToken;
/*  428 */       if (this.inputState.guessing == 0)
/*  429 */         _ttype = t.getType();
/*      */     }
/*      */     else
/*      */     {
/*  433 */       throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */     }
/*      */ 
/*  437 */     if ((_createToken) && (_token == null) && (_ttype != -1)) {
/*  438 */       _token = makeToken(_ttype);
/*  439 */       _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
/*      */     }
/*  441 */     this._returnToken = _token;
/*      */   }
/*      */ 
/*      */   protected final void mSL_COMMENT(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
/*  445 */     Token _token = null; int _begin = this.text.length();
/*  446 */     int _ttype = 87;
/*      */ 
/*  449 */     match("//");
/*      */ 
/*  451 */     boolean synPredMatched165 = false;
/*  452 */     if ((LA(1) == ' ') && (LA(2) == '$') && (LA(3) == 'A')) {
/*  453 */       int _m165 = mark();
/*  454 */       synPredMatched165 = true;
/*  455 */       this.inputState.guessing += 1;
/*      */       try
/*      */       {
/*  458 */         match(" $ANTLR");
/*      */       }
/*      */       catch (RecognitionException pe)
/*      */       {
/*  462 */         synPredMatched165 = false;
/*      */       }
/*  464 */       rewind(_m165);
/*  465 */       this.inputState.guessing -= 1;
/*      */     }
/*  467 */     if (synPredMatched165) {
/*  468 */       match(" $ANTLR ");
/*  469 */       mSRC(false);
/*      */ 
/*  471 */       switch (LA(1))
/*      */       {
/*      */       case '\r':
/*  474 */         match('\r');
/*  475 */         break;
/*      */       case '\n':
/*  479 */         break;
/*      */       default:
/*  483 */         throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */       }
/*      */ 
/*  487 */       match('\n');
/*      */     }
/*  489 */     else if ((LA(1) >= '\003') && (LA(1) <= 'ÿ'))
/*      */     {
/*  494 */       while ((LA(1) != '\n') && (LA(1) != '\r') && 
/*  495 */         (LA(1) >= '\003') && (LA(1) <= 'ÿ') && (LA(2) >= '\003') && (LA(2) <= 'ÿ')) {
/*  496 */         matchNot(65535);
/*      */       }
/*      */ 
/*  505 */       switch (LA(1))
/*      */       {
/*      */       case '\r':
/*  508 */         match('\r');
/*  509 */         break;
/*      */       case '\n':
/*  513 */         break;
/*      */       default:
/*  517 */         throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */       }
/*      */ 
/*  521 */       match('\n');
/*      */     }
/*      */     else {
/*  524 */       throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */     }
/*      */ 
/*  528 */     if (this.inputState.guessing == 0) {
/*  529 */       newline();
/*      */     }
/*  531 */     if ((_createToken) && (_token == null) && (_ttype != -1)) {
/*  532 */       _token = makeToken(_ttype);
/*  533 */       _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
/*      */     }
/*  535 */     this._returnToken = _token;
/*      */   }
/*      */ 
/*      */   protected final void mML_COMMENT(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
/*  539 */     Token _token = null; int _begin = this.text.length();
/*  540 */     int _ttype = 88;
/*      */ 
/*  543 */     match("/*");
/*      */ 
/*  545 */     if ((LA(1) == '*') && (LA(2) >= '\003') && (LA(2) <= 'ÿ') && (LA(3) >= '\003') && (LA(3) <= 'ÿ') && (LA(2) != '/')) {
/*  546 */       match('*');
/*  547 */       if (this.inputState.guessing == 0) {
/*  548 */         _ttype = 41;
/*      */       }
/*      */     }
/*  551 */     else if ((LA(1) < '\003') || (LA(1) > 'ÿ') || (LA(2) < '\003') || (LA(2) > 'ÿ'))
/*      */     {
/*  554 */       throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */     }
/*      */ 
/*  562 */     while ((LA(1) != '*') || (LA(2) != '/')) {
/*  563 */       switch (LA(1))
/*      */       {
/*      */       case '\r':
/*  566 */         match('\r');
/*  567 */         match('\n');
/*  568 */         if (this.inputState.guessing == 0)
/*  569 */           newline(); break;
/*      */       case '\n':
/*  575 */         match('\n');
/*  576 */         if (this.inputState.guessing == 0)
/*  577 */           newline(); break;
/*      */       default:
/*  582 */         if ((!_tokenSet_0.member(LA(1))) || (LA(2) < '\003') || (LA(2) > 'ÿ') || (LA(3) < '\003') || (LA(3) > 'ÿ'))
/*      */           break label340;
/*  584 */         match(_tokenSet_0);
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/*  593 */     label340: match("*/");
/*  594 */     if ((_createToken) && (_token == null) && (_ttype != -1)) {
/*  595 */       _token = makeToken(_ttype);
/*  596 */       _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
/*      */     }
/*  598 */     this._returnToken = _token;
/*      */   }
/*      */ 
/*      */   protected final void mSRC(boolean _createToken)
/*      */     throws RecognitionException, CharStreamException, TokenStreamException
/*      */   {
/*  606 */     Token _token = null; int _begin = this.text.length();
/*  607 */     int _ttype = 101;
/*      */ 
/*  609 */     Token file = null;
/*  610 */     Token line = null;
/*      */ 
/*  612 */     match("src");
/*  613 */     match(' ');
/*  614 */     mACTION_STRING_LITERAL(true);
/*  615 */     file = this._returnToken;
/*  616 */     match(' ');
/*  617 */     mINT(true);
/*  618 */     line = this._returnToken;
/*  619 */     if (this.inputState.guessing == 0)
/*      */     {
/*  621 */       newline();
/*  622 */       setFilename(file.getText().substring(1, file.getText().length() - 1));
/*  623 */       setLine(Integer.parseInt(line.getText()) - 1);
/*  624 */       _ttype = -1;
/*      */     }
/*      */ 
/*  627 */     if ((_createToken) && (_token == null) && (_ttype != -1)) {
/*  628 */       _token = makeToken(_ttype);
/*  629 */       _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
/*      */     }
/*  631 */     this._returnToken = _token;
/*      */   }
/*      */ 
/*      */   public final void mOPEN_ELEMENT_OPTION(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
/*  635 */     Token _token = null; int _begin = this.text.length();
/*  636 */     int _ttype = 78;
/*      */ 
/*  639 */     match('<');
/*  640 */     if ((_createToken) && (_token == null) && (_ttype != -1)) {
/*  641 */       _token = makeToken(_ttype);
/*  642 */       _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
/*      */     }
/*  644 */     this._returnToken = _token;
/*      */   }
/*      */ 
/*      */   public final void mCLOSE_ELEMENT_OPTION(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
/*  648 */     Token _token = null; int _begin = this.text.length();
/*  649 */     int _ttype = 79;
/*      */ 
/*  652 */     match('>');
/*  653 */     if ((_createToken) && (_token == null) && (_ttype != -1)) {
/*  654 */       _token = makeToken(_ttype);
/*  655 */       _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
/*      */     }
/*  657 */     this._returnToken = _token;
/*      */   }
/*      */ 
/*      */   public final void mAMPERSAND(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
/*  661 */     Token _token = null; int _begin = this.text.length();
/*  662 */     int _ttype = 46;
/*      */ 
/*  665 */     match('@');
/*  666 */     if ((_createToken) && (_token == null) && (_ttype != -1)) {
/*  667 */       _token = makeToken(_ttype);
/*  668 */       _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
/*      */     }
/*  670 */     this._returnToken = _token;
/*      */   }
/*      */ 
/*      */   public final void mCOMMA(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
/*  674 */     Token _token = null; int _begin = this.text.length();
/*  675 */     int _ttype = 54;
/*      */ 
/*  678 */     match(',');
/*  679 */     if ((_createToken) && (_token == null) && (_ttype != -1)) {
/*  680 */       _token = makeToken(_ttype);
/*  681 */       _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
/*      */     }
/*  683 */     this._returnToken = _token;
/*      */   }
/*      */ 
/*      */   public final void mQUESTION(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
/*  687 */     Token _token = null; int _begin = this.text.length();
/*  688 */     int _ttype = 76;
/*      */ 
/*  691 */     match('?');
/*  692 */     if ((_createToken) && (_token == null) && (_ttype != -1)) {
/*  693 */       _token = makeToken(_ttype);
/*  694 */       _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
/*      */     }
/*  696 */     this._returnToken = _token;
/*      */   }
/*      */ 
/*      */   public final void mTREE_BEGIN(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
/*  700 */     Token _token = null; int _begin = this.text.length();
/*  701 */     int _ttype = 75;
/*      */ 
/*  704 */     match("^(");
/*  705 */     if ((_createToken) && (_token == null) && (_ttype != -1)) {
/*  706 */       _token = makeToken(_ttype);
/*  707 */       _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
/*      */     }
/*  709 */     this._returnToken = _token;
/*      */   }
/*      */ 
/*      */   public final void mLPAREN(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
/*  713 */     Token _token = null; int _begin = this.text.length();
/*  714 */     int _ttype = 63;
/*      */ 
/*  717 */     match('(');
/*  718 */     if ((_createToken) && (_token == null) && (_ttype != -1)) {
/*  719 */       _token = makeToken(_ttype);
/*  720 */       _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
/*      */     }
/*  722 */     this._returnToken = _token;
/*      */   }
/*      */ 
/*      */   public final void mRPAREN(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
/*  726 */     Token _token = null; int _begin = this.text.length();
/*  727 */     int _ttype = 65;
/*      */ 
/*  730 */     match(')');
/*  731 */     if ((_createToken) && (_token == null) && (_ttype != -1)) {
/*  732 */       _token = makeToken(_ttype);
/*  733 */       _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
/*      */     }
/*  735 */     this._returnToken = _token;
/*      */   }
/*      */ 
/*      */   public final void mCOLON(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
/*  739 */     Token _token = null; int _begin = this.text.length();
/*  740 */     int _ttype = 47;
/*      */ 
/*  743 */     match(':');
/*  744 */     if ((_createToken) && (_token == null) && (_ttype != -1)) {
/*  745 */       _token = makeToken(_ttype);
/*  746 */       _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
/*      */     }
/*  748 */     this._returnToken = _token;
/*      */   }
/*      */ 
/*      */   public final void mSTAR(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
/*  752 */     Token _token = null; int _begin = this.text.length();
/*  753 */     int _ttype = 53;
/*      */ 
/*  756 */     match('*');
/*  757 */     if ((_createToken) && (_token == null) && (_ttype != -1)) {
/*  758 */       _token = makeToken(_ttype);
/*  759 */       _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
/*      */     }
/*  761 */     this._returnToken = _token;
/*      */   }
/*      */ 
/*      */   public final void mPLUS(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
/*  765 */     Token _token = null; int _begin = this.text.length();
/*  766 */     int _ttype = 77;
/*      */ 
/*  769 */     match('+');
/*  770 */     if ((_createToken) && (_token == null) && (_ttype != -1)) {
/*  771 */       _token = makeToken(_ttype);
/*  772 */       _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
/*      */     }
/*  774 */     this._returnToken = _token;
/*      */   }
/*      */ 
/*      */   public final void mASSIGN(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
/*  778 */     Token _token = null; int _begin = this.text.length();
/*  779 */     int _ttype = 49;
/*      */ 
/*  782 */     match('=');
/*  783 */     if ((_createToken) && (_token == null) && (_ttype != -1)) {
/*  784 */       _token = makeToken(_ttype);
/*  785 */       _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
/*      */     }
/*  787 */     this._returnToken = _token;
/*      */   }
/*      */ 
/*      */   public final void mPLUS_ASSIGN(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
/*  791 */     Token _token = null; int _begin = this.text.length();
/*  792 */     int _ttype = 68;
/*      */ 
/*  795 */     match("+=");
/*  796 */     if ((_createToken) && (_token == null) && (_ttype != -1)) {
/*  797 */       _token = makeToken(_ttype);
/*  798 */       _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
/*      */     }
/*  800 */     this._returnToken = _token;
/*      */   }
/*      */ 
/*      */   public final void mIMPLIES(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
/*  804 */     Token _token = null; int _begin = this.text.length();
/*  805 */     int _ttype = 70;
/*      */ 
/*  808 */     match("=>");
/*  809 */     if ((_createToken) && (_token == null) && (_ttype != -1)) {
/*  810 */       _token = makeToken(_ttype);
/*  811 */       _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
/*      */     }
/*  813 */     this._returnToken = _token;
/*      */   }
/*      */ 
/*      */   public final void mREWRITE(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
/*  817 */     Token _token = null; int _begin = this.text.length();
/*  818 */     int _ttype = 80;
/*      */ 
/*  821 */     match("->");
/*  822 */     if ((_createToken) && (_token == null) && (_ttype != -1)) {
/*  823 */       _token = makeToken(_ttype);
/*  824 */       _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
/*      */     }
/*  826 */     this._returnToken = _token;
/*      */   }
/*      */ 
/*      */   public final void mSEMI(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
/*  830 */     Token _token = null; int _begin = this.text.length();
/*  831 */     int _ttype = 42;
/*      */ 
/*  834 */     match(';');
/*  835 */     if ((_createToken) && (_token == null) && (_ttype != -1)) {
/*  836 */       _token = makeToken(_ttype);
/*  837 */       _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
/*      */     }
/*  839 */     this._returnToken = _token;
/*      */   }
/*      */ 
/*      */   public final void mROOT(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
/*  843 */     Token _token = null; int _begin = this.text.length();
/*  844 */     int _ttype = 71;
/*      */ 
/*  847 */     match('^');
/*  848 */     if (this.inputState.guessing == 0) {
/*  849 */       this.hasASTOperator = true;
/*      */     }
/*  851 */     if ((_createToken) && (_token == null) && (_ttype != -1)) {
/*  852 */       _token = makeToken(_ttype);
/*  853 */       _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
/*      */     }
/*  855 */     this._returnToken = _token;
/*      */   }
/*      */ 
/*      */   public final void mBANG(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
/*  859 */     Token _token = null; int _begin = this.text.length();
/*  860 */     int _ttype = 59;
/*      */ 
/*  863 */     match('!');
/*  864 */     if (this.inputState.guessing == 0) {
/*  865 */       this.hasASTOperator = true;
/*      */     }
/*  867 */     if ((_createToken) && (_token == null) && (_ttype != -1)) {
/*  868 */       _token = makeToken(_ttype);
/*  869 */       _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
/*      */     }
/*  871 */     this._returnToken = _token;
/*      */   }
/*      */ 
/*      */   public final void mOR(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
/*  875 */     Token _token = null; int _begin = this.text.length();
/*  876 */     int _ttype = 64;
/*      */ 
/*  879 */     match('|');
/*  880 */     if ((_createToken) && (_token == null) && (_ttype != -1)) {
/*  881 */       _token = makeToken(_ttype);
/*  882 */       _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
/*      */     }
/*  884 */     this._returnToken = _token;
/*      */   }
/*      */ 
/*      */   public final void mWILDCARD(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
/*  888 */     Token _token = null; int _begin = this.text.length();
/*  889 */     int _ttype = 72;
/*      */ 
/*  892 */     match('.');
/*  893 */     if ((_createToken) && (_token == null) && (_ttype != -1)) {
/*  894 */       _token = makeToken(_ttype);
/*  895 */       _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
/*      */     }
/*  897 */     this._returnToken = _token;
/*      */   }
/*      */ 
/*      */   public final void mETC(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
/*  901 */     Token _token = null; int _begin = this.text.length();
/*  902 */     int _ttype = 81;
/*      */ 
/*  905 */     match("...");
/*  906 */     if ((_createToken) && (_token == null) && (_ttype != -1)) {
/*  907 */       _token = makeToken(_ttype);
/*  908 */       _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
/*      */     }
/*  910 */     this._returnToken = _token;
/*      */   }
/*      */ 
/*      */   public final void mRANGE(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
/*  914 */     Token _token = null; int _begin = this.text.length();
/*  915 */     int _ttype = 14;
/*      */ 
/*  918 */     match("..");
/*  919 */     if ((_createToken) && (_token == null) && (_ttype != -1)) {
/*  920 */       _token = makeToken(_ttype);
/*  921 */       _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
/*      */     }
/*  923 */     this._returnToken = _token;
/*      */   }
/*      */ 
/*      */   public final void mNOT(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
/*  927 */     Token _token = null; int _begin = this.text.length();
/*  928 */     int _ttype = 74;
/*      */ 
/*  931 */     match('~');
/*  932 */     if ((_createToken) && (_token == null) && (_ttype != -1)) {
/*  933 */       _token = makeToken(_ttype);
/*  934 */       _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
/*      */     }
/*  936 */     this._returnToken = _token;
/*      */   }
/*      */ 
/*      */   public final void mRCURLY(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
/*  940 */     Token _token = null; int _begin = this.text.length();
/*  941 */     int _ttype = 48;
/*      */ 
/*  944 */     match('}');
/*  945 */     if ((_createToken) && (_token == null) && (_ttype != -1)) {
/*  946 */       _token = makeToken(_ttype);
/*  947 */       _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
/*      */     }
/*  949 */     this._returnToken = _token;
/*      */   }
/*      */ 
/*      */   public final void mDOLLAR(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
/*  953 */     Token _token = null; int _begin = this.text.length();
/*  954 */     int _ttype = 82;
/*      */ 
/*  957 */     match('$');
/*  958 */     if ((_createToken) && (_token == null) && (_ttype != -1)) {
/*  959 */       _token = makeToken(_ttype);
/*  960 */       _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
/*      */     }
/*  962 */     this._returnToken = _token;
/*      */   }
/*      */ 
/*      */   public final void mSTRAY_BRACKET(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
/*  966 */     Token _token = null; int _begin = this.text.length();
/*  967 */     int _ttype = 89;
/*      */ 
/*  970 */     match(']');
/*  971 */     if (this.inputState.guessing == 0)
/*      */     {
/*  973 */       ErrorManager.syntaxError(100, null, _token, "antlr: dangling ']'? make sure to escape with \\]", null);
/*      */     }
/*      */ 
/*  981 */     if ((_createToken) && (_token == null) && (_ttype != -1)) {
/*  982 */       _token = makeToken(_ttype);
/*  983 */       _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
/*      */     }
/*  985 */     this._returnToken = _token;
/*      */   }
/*      */ 
/*      */   public final void mCHAR_LITERAL(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
/*  989 */     Token _token = null; int _begin = this.text.length();
/*  990 */     int _ttype = 51;
/*      */ 
/*  993 */     match('\'');
/*      */     while (true)
/*      */     {
/*  997 */       switch (LA(1))
/*      */       {
/*      */       case '\\':
/* 1000 */         mESC(false);
/* 1001 */         break;
/*      */       case '\n':
/* 1005 */         match('\n');
/* 1006 */         if (this.inputState.guessing == 0)
/* 1007 */           newline(); break;
/*      */       default:
/* 1012 */         if (!_tokenSet_1.member(LA(1))) break label106;
/* 1013 */         matchNot('\'');
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 1021 */     label106: match('\'');
/* 1022 */     if (this.inputState.guessing == 0)
/*      */     {
/* 1024 */       StringBuffer s = Grammar.getUnescapedStringFromGrammarStringLiteral(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
/* 1025 */       if (s.length() > 1) {
/* 1026 */         _ttype = 50;
/*      */       }
/*      */     }
/*      */ 
/* 1030 */     if ((_createToken) && (_token == null) && (_ttype != -1)) {
/* 1031 */       _token = makeToken(_ttype);
/* 1032 */       _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
/*      */     }
/* 1034 */     this._returnToken = _token;
/*      */   }
/*      */ 
/*      */   protected final void mESC(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
/* 1038 */     Token _token = null; int _begin = this.text.length();
/* 1039 */     int _ttype = 90;
/*      */ 
/* 1042 */     match('\\');
/*      */ 
/* 1044 */     if ((LA(1) == 'u') && (_tokenSet_2.member(LA(2))) && (_tokenSet_2.member(LA(3)))) {
/* 1045 */       match('u');
/* 1046 */       mXDIGIT(false);
/* 1047 */       mXDIGIT(false);
/* 1048 */       mXDIGIT(false);
/* 1049 */       mXDIGIT(false);
/*      */     }
/* 1051 */     else if ((LA(1) == 'n') && (LA(2) >= '\003') && (LA(2) <= 'ÿ')) {
/* 1052 */       match('n');
/*      */     }
/* 1054 */     else if ((LA(1) == 'r') && (LA(2) >= '\003') && (LA(2) <= 'ÿ')) {
/* 1055 */       match('r');
/*      */     }
/* 1057 */     else if ((LA(1) == 't') && (LA(2) >= '\003') && (LA(2) <= 'ÿ')) {
/* 1058 */       match('t');
/*      */     }
/* 1060 */     else if ((LA(1) == 'b') && (LA(2) >= '\003') && (LA(2) <= 'ÿ')) {
/* 1061 */       match('b');
/*      */     }
/* 1063 */     else if ((LA(1) == 'f') && (LA(2) >= '\003') && (LA(2) <= 'ÿ')) {
/* 1064 */       match('f');
/*      */     }
/* 1066 */     else if ((LA(1) == '"') && (LA(2) >= '\003') && (LA(2) <= 'ÿ')) {
/* 1067 */       match('"');
/*      */     }
/* 1069 */     else if ((LA(1) == '\'') && (LA(2) >= '\003') && (LA(2) <= 'ÿ')) {
/* 1070 */       match('\'');
/*      */     }
/* 1072 */     else if ((LA(1) == '\\') && (LA(2) >= '\003') && (LA(2) <= 'ÿ')) {
/* 1073 */       match('\\');
/*      */     }
/* 1075 */     else if ((LA(1) == '>') && (LA(2) >= '\003') && (LA(2) <= 'ÿ')) {
/* 1076 */       match('>');
/*      */     }
/* 1078 */     else if ((LA(1) >= '\003') && (LA(1) <= 'ÿ') && (LA(2) >= '\003') && (LA(2) <= 'ÿ')) {
/* 1079 */       matchNot(65535);
/*      */     }
/*      */     else {
/* 1082 */       throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */     }
/*      */ 
/* 1086 */     if ((_createToken) && (_token == null) && (_ttype != -1)) {
/* 1087 */       _token = makeToken(_ttype);
/* 1088 */       _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
/*      */     }
/* 1090 */     this._returnToken = _token;
/*      */   }
/*      */ 
/*      */   public final void mDOUBLE_QUOTE_STRING_LITERAL(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
/* 1094 */     Token _token = null; int _begin = this.text.length();
/* 1095 */     int _ttype = 83;
/*      */ 
/* 1098 */     match('"');
/*      */     while (true)
/*      */     {
/* 1102 */       if ((LA(1) == '\\') && (LA(2) == '"')) {
/* 1103 */         int _saveIndex = this.text.length();
/* 1104 */         match('\\');
/* 1105 */         this.text.setLength(_saveIndex);
/* 1106 */         match('"');
/*      */       }
/* 1108 */       else if ((LA(1) == '\\') && (_tokenSet_3.member(LA(2)))) {
/* 1109 */         match('\\');
/* 1110 */         matchNot('"');
/*      */       }
/* 1112 */       else if (LA(1) == '\n') {
/* 1113 */         match('\n');
/* 1114 */         if (this.inputState.guessing == 0)
/* 1115 */           newline();
/*      */       }
/*      */       else {
/* 1118 */         if (!_tokenSet_4.member(LA(1))) break;
/* 1119 */         matchNot('"');
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 1127 */     match('"');
/* 1128 */     if ((_createToken) && (_token == null) && (_ttype != -1)) {
/* 1129 */       _token = makeToken(_ttype);
/* 1130 */       _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
/*      */     }
/* 1132 */     this._returnToken = _token;
/*      */   }
/*      */ 
/*      */   public final void mDOUBLE_ANGLE_STRING_LITERAL(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
/* 1136 */     Token _token = null; int _begin = this.text.length();
/* 1137 */     int _ttype = 84;
/*      */ 
/* 1140 */     match("<<");
/*      */ 
/* 1145 */     while ((LA(1) != '>') || (LA(2) != '>')) {
/* 1146 */       if ((LA(1) == '\n') && (LA(2) >= '\003') && (LA(2) <= 'ÿ') && (LA(3) >= '\003') && (LA(3) <= 'ÿ')) {
/* 1147 */         match('\n');
/* 1148 */         if (this.inputState.guessing == 0)
/* 1149 */           newline();
/*      */       }
/*      */       else {
/* 1152 */         if ((LA(1) < '\003') || (LA(1) > 'ÿ') || (LA(2) < '\003') || (LA(2) > 'ÿ') || (LA(3) < '\003') || (LA(3) > 'ÿ')) break;
/* 1153 */         matchNot(65535);
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 1161 */     match(">>");
/* 1162 */     if ((_createToken) && (_token == null) && (_ttype != -1)) {
/* 1163 */       _token = makeToken(_ttype);
/* 1164 */       _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
/*      */     }
/* 1166 */     this._returnToken = _token;
/*      */   }
/*      */ 
/*      */   protected final void mXDIGIT(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
/* 1170 */     Token _token = null; int _begin = this.text.length();
/* 1171 */     int _ttype = 92;
/*      */ 
/* 1174 */     switch (LA(1)) { case '0':
/*      */     case '1':
/*      */     case '2':
/*      */     case '3':
/*      */     case '4':
/*      */     case '5':
/*      */     case '6':
/*      */     case '7':
/*      */     case '8':
/*      */     case '9':
/* 1179 */       matchRange('0', '9');
/* 1180 */       break;
/*      */     case 'a':
/*      */     case 'b':
/*      */     case 'c':
/*      */     case 'd':
/*      */     case 'e':
/*      */     case 'f':
/* 1185 */       matchRange('a', 'f');
/* 1186 */       break;
/*      */     case 'A':
/*      */     case 'B':
/*      */     case 'C':
/*      */     case 'D':
/*      */     case 'E':
/*      */     case 'F':
/* 1191 */       matchRange('A', 'F');
/* 1192 */       break;
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
/* 1196 */       throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */     }
/*      */ 
/* 1199 */     if ((_createToken) && (_token == null) && (_ttype != -1)) {
/* 1200 */       _token = makeToken(_ttype);
/* 1201 */       _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
/*      */     }
/* 1203 */     this._returnToken = _token;
/*      */   }
/*      */ 
/*      */   protected final void mDIGIT(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
/* 1207 */     Token _token = null; int _begin = this.text.length();
/* 1208 */     int _ttype = 91;
/*      */ 
/* 1211 */     matchRange('0', '9');
/* 1212 */     if ((_createToken) && (_token == null) && (_ttype != -1)) {
/* 1213 */       _token = makeToken(_ttype);
/* 1214 */       _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
/*      */     }
/* 1216 */     this._returnToken = _token;
/*      */   }
/*      */ 
/*      */   public final void mINT(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
/* 1220 */     Token _token = null; int _begin = this.text.length();
/* 1221 */     int _ttype = 52;
/*      */ 
/* 1225 */     int _cnt216 = 0;
/*      */     while (true)
/*      */     {
/* 1228 */       if ((LA(1) >= '0') && (LA(1) <= '9')) {
/* 1229 */         matchRange('0', '9');
/*      */       }
/*      */       else {
/* 1232 */         if (_cnt216 >= 1) break; throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */       }
/*      */ 
/* 1235 */       _cnt216++;
/*      */     }
/*      */ 
/* 1238 */     if ((_createToken) && (_token == null) && (_ttype != -1)) {
/* 1239 */       _token = makeToken(_ttype);
/* 1240 */       _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
/*      */     }
/* 1242 */     this._returnToken = _token;
/*      */   }
/*      */ 
/*      */   public final void mARG_ACTION(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
/* 1246 */     Token _token = null; int _begin = this.text.length();
/* 1247 */     int _ttype = 60;
/*      */ 
/* 1250 */     int _saveIndex = this.text.length();
/* 1251 */     match('[');
/* 1252 */     this.text.setLength(_saveIndex);
/* 1253 */     mNESTED_ARG_ACTION(false);
/* 1254 */     _saveIndex = this.text.length();
/* 1255 */     match(']');
/* 1256 */     this.text.setLength(_saveIndex);
/* 1257 */     if ((_createToken) && (_token == null) && (_ttype != -1)) {
/* 1258 */       _token = makeToken(_ttype);
/* 1259 */       _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
/*      */     }
/* 1261 */     this._returnToken = _token;
/*      */   }
/*      */ 
/*      */   protected final void mNESTED_ARG_ACTION(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
/* 1265 */     Token _token = null; int _begin = this.text.length();
/* 1266 */     int _ttype = 93;
/*      */     while (true)
/*      */     {
/* 1272 */       switch (LA(1))
/*      */       {
/*      */       case '\r':
/* 1275 */         match('\r');
/* 1276 */         match('\n');
/* 1277 */         if (this.inputState.guessing == 0)
/* 1278 */           newline(); break;
/*      */       case '\n':
/* 1284 */         match('\n');
/* 1285 */         if (this.inputState.guessing == 0)
/* 1286 */           newline(); break;
/*      */       case '"':
/* 1292 */         mACTION_STRING_LITERAL(false);
/* 1293 */         break;
/*      */       case '\'':
/* 1297 */         mACTION_CHAR_LITERAL(false);
/* 1298 */         break;
/*      */       default:
/* 1301 */         if ((LA(1) == '\\') && (LA(2) == ']')) {
/* 1302 */           int _saveIndex = this.text.length();
/* 1303 */           match('\\');
/* 1304 */           this.text.setLength(_saveIndex);
/* 1305 */           match(']');
/*      */         }
/* 1307 */         else if ((LA(1) == '\\') && (_tokenSet_5.member(LA(2)))) {
/* 1308 */           match('\\');
/* 1309 */           matchNot(']');
/*      */         } else {
/* 1311 */           if (!_tokenSet_6.member(LA(1))) break label243;
/* 1312 */           matchNot(']');
/*      */         }
/*      */ 
/*      */         break;
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 1320 */     label243: if ((_createToken) && (_token == null) && (_ttype != -1)) {
/* 1321 */       _token = makeToken(_ttype);
/* 1322 */       _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
/*      */     }
/* 1324 */     this._returnToken = _token;
/*      */   }
/*      */ 
/*      */   protected final void mACTION_STRING_LITERAL(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
/* 1328 */     Token _token = null; int _begin = this.text.length();
/* 1329 */     int _ttype = 96;
/*      */ 
/* 1332 */     match('"');
/*      */     while (true)
/*      */     {
/* 1336 */       switch (LA(1))
/*      */       {
/*      */       case '\\':
/* 1339 */         mACTION_ESC(false);
/* 1340 */         break;
/*      */       case '\n':
/* 1344 */         match('\n');
/* 1345 */         if (this.inputState.guessing == 0)
/* 1346 */           newline(); break;
/*      */       default:
/* 1351 */         if (!_tokenSet_4.member(LA(1))) break label106;
/* 1352 */         matchNot('"');
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 1360 */     label106: match('"');
/* 1361 */     if ((_createToken) && (_token == null) && (_ttype != -1)) {
/* 1362 */       _token = makeToken(_ttype);
/* 1363 */       _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
/*      */     }
/* 1365 */     this._returnToken = _token;
/*      */   }
/*      */ 
/*      */   protected final void mACTION_CHAR_LITERAL(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
/* 1369 */     Token _token = null; int _begin = this.text.length();
/* 1370 */     int _ttype = 95;
/*      */ 
/* 1373 */     match('\'');
/*      */     while (true)
/*      */     {
/* 1377 */       switch (LA(1))
/*      */       {
/*      */       case '\\':
/* 1380 */         mACTION_ESC(false);
/* 1381 */         break;
/*      */       case '\n':
/* 1385 */         match('\n');
/* 1386 */         if (this.inputState.guessing == 0)
/* 1387 */           newline(); break;
/*      */       default:
/* 1392 */         if (!_tokenSet_1.member(LA(1))) break label106;
/* 1393 */         matchNot('\'');
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 1401 */     label106: match('\'');
/* 1402 */     if ((_createToken) && (_token == null) && (_ttype != -1)) {
/* 1403 */       _token = makeToken(_ttype);
/* 1404 */       _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
/*      */     }
/* 1406 */     this._returnToken = _token;
/*      */   }
/*      */ 
/*      */   public final void mACTION(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
/* 1410 */     Token _token = null; int _begin = this.text.length();
/* 1411 */     int _ttype = 40;
/*      */ 
/* 1413 */     int actionLine = getLine(); int actionColumn = getColumn();
/*      */ 
/* 1415 */     mNESTED_ACTION(false);
/*      */ 
/* 1417 */     if (LA(1) == '?') {
/* 1418 */       int _saveIndex = this.text.length();
/* 1419 */       match('?');
/* 1420 */       this.text.setLength(_saveIndex);
/* 1421 */       if (this.inputState.guessing == 0) {
/* 1422 */         _ttype = 69;
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 1429 */     if (this.inputState.guessing == 0)
/*      */     {
/* 1431 */       Token t = makeToken(_ttype);
/* 1432 */       String action = new String(this.text.getBuffer(), _begin, this.text.length() - _begin);
/* 1433 */       int n = 1;
/* 1434 */       if ((action.startsWith("{{")) && (action.endsWith("}}"))) {
/* 1435 */         t.setType(30);
/* 1436 */         n = 2;
/*      */       }
/* 1438 */       action = action.substring(n, action.length() - n);
/* 1439 */       t.setText(action);
/* 1440 */       t.setLine(actionLine);
/* 1441 */       t.setColumn(actionColumn);
/* 1442 */       _token = t;
/*      */     }
/*      */ 
/* 1445 */     if ((_createToken) && (_token == null) && (_ttype != -1)) {
/* 1446 */       _token = makeToken(_ttype);
/* 1447 */       _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
/*      */     }
/* 1449 */     this._returnToken = _token;
/*      */   }
/*      */ 
/*      */   protected final void mNESTED_ACTION(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
/* 1453 */     Token _token = null; int _begin = this.text.length();
/* 1454 */     int _ttype = 94;
/*      */ 
/* 1457 */     match('{');
/*      */ 
/* 1462 */     while (LA(1) != '}') {
/* 1463 */       if ((LA(1) == '{') && (LA(2) >= '\003') && (LA(2) <= 'ÿ') && (LA(3) >= '\003') && (LA(3) <= 'ÿ')) {
/* 1464 */         mNESTED_ACTION(false);
/*      */       }
/* 1466 */       else if ((LA(1) == '\'') && (LA(2) >= '\003') && (LA(2) <= 'ÿ') && (LA(3) >= '\003') && (LA(3) <= 'ÿ')) {
/* 1467 */         mACTION_CHAR_LITERAL(false);
/*      */       }
/* 1469 */       else if ((LA(1) == '/') && ((LA(2) == '*') || (LA(2) == '/')) && (LA(3) >= '\003') && (LA(3) <= 'ÿ')) {
/* 1470 */         mCOMMENT(false);
/*      */       }
/* 1472 */       else if ((LA(1) == '"') && (LA(2) >= '\003') && (LA(2) <= 'ÿ') && (LA(3) >= '\003') && (LA(3) <= 'ÿ')) {
/* 1473 */         mACTION_STRING_LITERAL(false);
/*      */       }
/* 1475 */       else if ((LA(1) == '\\') && (LA(2) >= '\003') && (LA(2) <= 'ÿ') && (LA(3) >= '\003') && (LA(3) <= 'ÿ')) {
/* 1476 */         mACTION_ESC(false);
/*      */       } else {
/* 1478 */         if (((LA(1) == '\n') || (LA(1) == '\r')) && (LA(2) >= '\003') && (LA(2) <= 'ÿ'));
/* 1480 */         switch (LA(1))
/*      */         {
/*      */         case '\r':
/* 1483 */           match('\r');
/* 1484 */           match('\n');
/* 1485 */           if (this.inputState.guessing == 0)
/* 1486 */             newline(); break;
/*      */         case '\n':
/* 1492 */           match('\n');
/* 1493 */           if (this.inputState.guessing == 0)
/* 1494 */             newline(); break;
/*      */         default:
/* 1500 */           throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */ 
/* 1505 */           if ((LA(1) < '\003') || (LA(1) > 'ÿ') || (LA(2) < '\003') || (LA(2) > 'ÿ')) break label522;
/* 1506 */           matchNot(65535);
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 1514 */     label522: match('}');
/* 1515 */     if ((_createToken) && (_token == null) && (_ttype != -1)) {
/* 1516 */       _token = makeToken(_ttype);
/* 1517 */       _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
/*      */     }
/* 1519 */     this._returnToken = _token;
/*      */   }
/*      */ 
/*      */   protected final void mACTION_ESC(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
/* 1523 */     Token _token = null; int _begin = this.text.length();
/* 1524 */     int _ttype = 97;
/*      */ 
/* 1527 */     if ((LA(1) == '\\') && (LA(2) == '\'')) {
/* 1528 */       match("\\'");
/*      */     }
/* 1530 */     else if ((LA(1) == '\\') && (LA(2) == '"')) {
/* 1531 */       match("\\\"");
/*      */     }
/* 1533 */     else if ((LA(1) == '\\') && (_tokenSet_7.member(LA(2)))) {
/* 1534 */       match('\\');
/*      */ 
/* 1536 */       match(_tokenSet_7);
/*      */     }
/*      */     else
/*      */     {
/* 1540 */       throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */     }
/*      */ 
/* 1543 */     if ((_createToken) && (_token == null) && (_ttype != -1)) {
/* 1544 */       _token = makeToken(_ttype);
/* 1545 */       _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
/*      */     }
/* 1547 */     this._returnToken = _token;
/*      */   }
/*      */ 
/*      */   public final void mTOKEN_REF(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
/* 1551 */     Token _token = null; int _begin = this.text.length();
/* 1552 */     int _ttype = 55;
/*      */ 
/* 1555 */     matchRange('A', 'Z');
/*      */     while (true)
/*      */     {
/* 1559 */       switch (LA(1)) { case 'a':
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
/* 1568 */         matchRange('a', 'z');
/* 1569 */         break;
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
/* 1579 */         matchRange('A', 'Z');
/* 1580 */         break;
/*      */       case '_':
/* 1584 */         match('_');
/* 1585 */         break;
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
/* 1591 */         matchRange('0', '9');
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
/* 1601 */       case '`': }  } _ttype = testLiteralsTable(_ttype);
/* 1602 */     if ((_createToken) && (_token == null) && (_ttype != -1)) {
/* 1603 */       _token = makeToken(_ttype);
/* 1604 */       _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
/*      */     }
/* 1606 */     this._returnToken = _token;
/*      */   }
/*      */ 
/*      */   public final void mRULE_REF(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
/* 1610 */     Token _token = null; int _begin = this.text.length();
/* 1611 */     int _ttype = 73;
/*      */ 
/* 1614 */     int t = 0;
/*      */ 
/* 1617 */     t = mINTERNAL_RULE_REF(false);
/* 1618 */     if (this.inputState.guessing == 0) {
/* 1619 */       _ttype = t;
/*      */     }
/*      */ 
/* 1622 */     if (t == 4) {
/* 1623 */       mWS_LOOP(false);
/*      */ 
/* 1625 */       if (LA(1) == '{') {
/* 1626 */         match('{');
/* 1627 */         if (this.inputState.guessing == 0) {
/* 1628 */           _ttype = 4;
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/*      */     }
/* 1636 */     else if (t == 5) {
/* 1637 */       mWS_LOOP(false);
/*      */ 
/* 1639 */       if (LA(1) == '{') {
/* 1640 */         match('{');
/* 1641 */         if (this.inputState.guessing == 0) {
/* 1642 */           _ttype = 5;
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 1654 */     if ((_createToken) && (_token == null) && (_ttype != -1)) {
/* 1655 */       _token = makeToken(_ttype);
/* 1656 */       _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
/*      */     }
/* 1658 */     this._returnToken = _token;
/*      */   }
/*      */ 
/*      */   protected final int mINTERNAL_RULE_REF(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException
/*      */   {
/* 1663 */     Token _token = null; int _begin = this.text.length();
/* 1664 */     int _ttype = 99;
/*      */ 
/* 1667 */     int t = 73;
/*      */ 
/* 1670 */     matchRange('a', 'z');
/*      */     while (true)
/*      */     {
/* 1674 */       switch (LA(1)) { case 'a':
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
/* 1683 */         matchRange('a', 'z');
/* 1684 */         break;
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
/* 1694 */         matchRange('A', 'Z');
/* 1695 */         break;
/*      */       case '_':
/* 1699 */         match('_');
/* 1700 */         break;
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
/* 1706 */         matchRange('0', '9');
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
/* 1716 */       case '`': }  } if (this.inputState.guessing == 0) {
/* 1717 */       t = testLiteralsTable(t);
/*      */     }
/* 1719 */     if ((_createToken) && (_token == null) && (_ttype != -1)) {
/* 1720 */       _token = makeToken(_ttype);
/* 1721 */       _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
/*      */     }
/* 1723 */     this._returnToken = _token;
/* 1724 */     return t;
/*      */   }
/*      */ 
/*      */   protected final void mWS_LOOP(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
/* 1728 */     Token _token = null; int _begin = this.text.length();
/* 1729 */     int _ttype = 98;
/*      */     while (true)
/*      */     {
/* 1735 */       switch (LA(1)) { case '\t':
/*      */       case '\n':
/*      */       case '\r':
/*      */       case ' ':
/* 1738 */         mWS(false);
/* 1739 */         break;
/*      */       case '/':
/* 1743 */         mCOMMENT(false);
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 1753 */     if ((_createToken) && (_token == null) && (_ttype != -1)) {
/* 1754 */       _token = makeToken(_ttype);
/* 1755 */       _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
/*      */     }
/* 1757 */     this._returnToken = _token;
/*      */   }
/*      */ 
/*      */   protected final void mWS_OPT(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
/* 1761 */     Token _token = null; int _begin = this.text.length();
/* 1762 */     int _ttype = 100;
/*      */ 
/* 1766 */     if (_tokenSet_8.member(LA(1))) {
/* 1767 */       mWS(false);
/*      */     }
/*      */ 
/* 1773 */     if ((_createToken) && (_token == null) && (_ttype != -1)) {
/* 1774 */       _token = makeToken(_ttype);
/* 1775 */       _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
/*      */     }
/* 1777 */     this._returnToken = _token;
/*      */   }
/*      */ 
/*      */   private static final long[] mk_tokenSet_0()
/*      */   {
/* 1782 */     long[] data = new long[8];
/* 1783 */     data[0] = -9224L;
/* 1784 */     for (int i = 1; i <= 3; i++) data[i] = -1L;
/* 1785 */     return data;
/*      */   }
/*      */ 
/*      */   private static final long[] mk_tokenSet_1() {
/* 1789 */     long[] data = new long[8];
/* 1790 */     data[0] = -549755814920L;
/* 1791 */     data[1] = -268435457L;
/* 1792 */     for (int i = 2; i <= 3; i++) data[i] = -1L;
/* 1793 */     return data;
/*      */   }
/*      */ 
/*      */   private static final long[] mk_tokenSet_2() {
/* 1797 */     long[] data = { 287948901175001088L, 541165879422L, 0L, 0L, 0L };
/* 1798 */     return data;
/*      */   }
/*      */ 
/*      */   private static final long[] mk_tokenSet_3() {
/* 1802 */     long[] data = new long[8];
/* 1803 */     data[0] = -17179869192L;
/* 1804 */     for (int i = 1; i <= 3; i++) data[i] = -1L;
/* 1805 */     return data;
/*      */   }
/*      */ 
/*      */   private static final long[] mk_tokenSet_4() {
/* 1809 */     long[] data = new long[8];
/* 1810 */     data[0] = -17179870216L;
/* 1811 */     data[1] = -268435457L;
/* 1812 */     for (int i = 2; i <= 3; i++) data[i] = -1L;
/* 1813 */     return data;
/*      */   }
/*      */ 
/*      */   private static final long[] mk_tokenSet_5() {
/* 1817 */     long[] data = new long[8];
/* 1818 */     data[0] = -8L;
/* 1819 */     data[1] = -536870913L;
/* 1820 */     for (int i = 2; i <= 3; i++) data[i] = -1L;
/* 1821 */     return data;
/*      */   }
/*      */ 
/*      */   private static final long[] mk_tokenSet_6() {
/* 1825 */     long[] data = new long[8];
/* 1826 */     data[0] = -566935692296L;
/* 1827 */     data[1] = -805306369L;
/* 1828 */     for (int i = 2; i <= 3; i++) data[i] = -1L;
/* 1829 */     return data;
/*      */   }
/*      */ 
/*      */   private static final long[] mk_tokenSet_7() {
/* 1833 */     long[] data = new long[8];
/* 1834 */     data[0] = -566935683080L;
/* 1835 */     for (int i = 1; i <= 3; i++) data[i] = -1L;
/* 1836 */     return data;
/*      */   }
/*      */ 
/*      */   private static final long[] mk_tokenSet_8() {
/* 1840 */     long[] data = { 4294977024L, 0L, 0L, 0L, 0L };
/* 1841 */     return data;
/*      */   }
/*      */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.antlr.grammar.v2.ANTLRLexer
 * JD-Core Version:    0.6.2
 */