/*      */ package org.antlr.stringtemplate.language;
/*      */ 
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
/*      */ import antlr.SemanticException;
/*      */ import antlr.Token;
/*      */ import antlr.TokenStream;
/*      */ import antlr.TokenStreamException;
/*      */ import antlr.TokenStreamIOException;
/*      */ import antlr.TokenStreamRecognitionException;
/*      */ import antlr.collections.impl.BitSet;
/*      */ import java.io.InputStream;
/*      */ import java.io.Reader;
/*      */ import java.util.Hashtable;
/*      */ import org.antlr.stringtemplate.StringTemplate;
/*      */ 
/*      */ public class AngleBracketTemplateLexer extends CharScanner
/*      */   implements AngleBracketTemplateLexerTokenTypes, TokenStream
/*      */ {
/*   65 */   protected String currentIndent = null;
/*      */   protected StringTemplate self;
/* 1433 */   public static final BitSet _tokenSet_0 = new BitSet(mk_tokenSet_0());
/*      */ 
/* 1441 */   public static final BitSet _tokenSet_1 = new BitSet(mk_tokenSet_1());
/*      */ 
/* 1447 */   public static final BitSet _tokenSet_2 = new BitSet(mk_tokenSet_2());
/*      */ 
/* 1454 */   public static final BitSet _tokenSet_3 = new BitSet(mk_tokenSet_3());
/*      */ 
/* 1461 */   public static final BitSet _tokenSet_4 = new BitSet(mk_tokenSet_4());
/*      */ 
/* 1469 */   public static final BitSet _tokenSet_5 = new BitSet(mk_tokenSet_5());
/*      */ 
/* 1477 */   public static final BitSet _tokenSet_6 = new BitSet(mk_tokenSet_6());
/*      */ 
/* 1485 */   public static final BitSet _tokenSet_7 = new BitSet(mk_tokenSet_7());
/*      */ 
/* 1494 */   public static final BitSet _tokenSet_8 = new BitSet(mk_tokenSet_8());
/*      */ 
/* 1503 */   public static final BitSet _tokenSet_9 = new BitSet(mk_tokenSet_9());
/*      */ 
/* 1512 */   public static final BitSet _tokenSet_10 = new BitSet(mk_tokenSet_10());
/*      */ 
/* 1521 */   public static final BitSet _tokenSet_11 = new BitSet(mk_tokenSet_11());
/*      */ 
/* 1530 */   public static final BitSet _tokenSet_12 = new BitSet(mk_tokenSet_12());
/*      */ 
/* 1539 */   public static final BitSet _tokenSet_13 = new BitSet(mk_tokenSet_13());
/*      */ 
/*      */   public AngleBracketTemplateLexer(StringTemplate self, Reader r)
/*      */   {
/*   69 */     this(r);
/*   70 */     this.self = self;
/*      */   }
/*      */ 
/*      */   public void reportError(RecognitionException e) {
/*   74 */     this.self.error("<...> chunk lexer error", e);
/*      */   }
/*      */ 
/*      */   protected boolean upcomingELSE(int i) throws CharStreamException {
/*   78 */     return (LA(i) == '<') && (LA(i + 1) == 'e') && (LA(i + 2) == 'l') && (LA(i + 3) == 's') && (LA(i + 4) == 'e') && (LA(i + 5) == '>');
/*      */   }
/*      */ 
/*      */   protected boolean upcomingENDIF(int i) throws CharStreamException
/*      */   {
/*   83 */     return (LA(i) == '<') && (LA(i + 1) == 'e') && (LA(i + 2) == 'n') && (LA(i + 3) == 'd') && (LA(i + 4) == 'i') && (LA(i + 5) == 'f') && (LA(i + 6) == '>');
/*      */   }
/*      */ 
/*      */   protected boolean upcomingAtEND(int i) throws CharStreamException
/*      */   {
/*   88 */     return (LA(i) == '<') && (LA(i + 1) == '@') && (LA(i + 2) == 'e') && (LA(i + 3) == 'n') && (LA(i + 4) == 'd') && (LA(i + 5) == '>');
/*      */   }
/*      */ 
/*      */   protected boolean upcomingNewline(int i) throws CharStreamException {
/*   92 */     return ((LA(i) == '\r') && (LA(i + 1) == '\n')) || (LA(i) == '\n');
/*      */   }
/*      */   public AngleBracketTemplateLexer(InputStream in) {
/*   95 */     this(new ByteBuffer(in));
/*      */   }
/*      */   public AngleBracketTemplateLexer(Reader in) {
/*   98 */     this(new CharBuffer(in));
/*      */   }
/*      */   public AngleBracketTemplateLexer(InputBuffer ib) {
/*  101 */     this(new LexerSharedInputState(ib));
/*      */   }
/*      */   public AngleBracketTemplateLexer(LexerSharedInputState state) {
/*  104 */     super(state);
/*  105 */     this.caseSensitiveLiterals = true;
/*  106 */     setCaseSensitive(true);
/*  107 */     this.literals = new Hashtable();
/*      */   }
/*      */ 
/*      */   public Token nextToken() throws TokenStreamException {
/*  111 */     Token theRetToken = null;
/*      */     while (true)
/*      */     {
/*  114 */       Token _token = null;
/*  115 */       int _ttype = 0;
/*  116 */       resetText();
/*      */       try
/*      */       {
/*  119 */         switch (LA(1)) {
/*      */         case '\n':
/*      */         case '\r':
/*  122 */           mNEWLINE(true);
/*  123 */           theRetToken = this._returnToken;
/*  124 */           break;
/*      */         case '<':
/*  128 */           mACTION(true);
/*  129 */           theRetToken = this._returnToken;
/*  130 */           break;
/*      */         default:
/*  133 */           if ((_tokenSet_0.member(LA(1))) && (LA(1) != '\r') && (LA(1) != '\n')) {
/*  134 */             mLITERAL(true);
/*  135 */             theRetToken = this._returnToken;
/*      */           }
/*  138 */           else if (LA(1) == 65535) { uponEOF(); this._returnToken = makeToken(1); } else {
/*  139 */             throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */           }break;
/*      */         }
/*  142 */         if (this._returnToken == null) continue;
/*  143 */         _ttype = this._returnToken.getType();
/*  144 */         _ttype = testLiteralsTable(_ttype);
/*  145 */         this._returnToken.setType(_ttype);
/*  146 */         return this._returnToken;
/*      */       }
/*      */       catch (RecognitionException e) {
/*  149 */         throw new TokenStreamRecognitionException(e);
/*      */       }
/*      */       catch (CharStreamException cse)
/*      */       {
/*  153 */         if ((cse instanceof CharStreamIOException)) {
/*  154 */           throw new TokenStreamIOException(((CharStreamIOException)cse).io);
/*      */         }
/*      */ 
/*  157 */         throw new TokenStreamException(cse.getMessage());
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   public final void mLITERAL(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException
/*      */   {
/*  164 */     Token _token = null; int _begin = this.text.length();
/*  165 */     int _ttype = 4;
/*      */ 
/*  167 */     Token ind = null;
/*      */ 
/*  169 */     if ((LA(1) == '\r') || (LA(1) == '\n')) {
/*  170 */       throw new SemanticException("LA(1)!='\\r'&&LA(1)!='\\n'");
/*      */     }
/*  172 */     int _cnt5 = 0;
/*      */     while (true)
/*      */     {
/*  176 */       int loopStartIndex = this.text.length();
/*  177 */       int col = getColumn();
/*      */ 
/*  179 */       if ((LA(1) == '\\') && (LA(2) == '<')) {
/*  180 */         int _saveIndex = this.text.length();
/*  181 */         match('\\');
/*  182 */         this.text.setLength(_saveIndex);
/*  183 */         match('<');
/*      */       }
/*  185 */       else if ((LA(1) == '\\') && (LA(2) == '>')) {
/*  186 */         int _saveIndex = this.text.length();
/*  187 */         match('\\');
/*  188 */         this.text.setLength(_saveIndex);
/*  189 */         match('>');
/*      */       }
/*  191 */       else if ((LA(1) == '\\') && (LA(2) == '\\')) {
/*  192 */         int _saveIndex = this.text.length();
/*  193 */         match('\\');
/*  194 */         this.text.setLength(_saveIndex);
/*  195 */         match('\\');
/*      */       }
/*  197 */       else if ((LA(1) == '\\') && (_tokenSet_1.member(LA(2)))) {
/*  198 */         match('\\');
/*      */ 
/*  200 */         match(_tokenSet_1);
/*      */       }
/*  203 */       else if ((LA(1) == '\t') || (LA(1) == ' ')) {
/*  204 */         mINDENT(true);
/*  205 */         ind = this._returnToken;
/*      */ 
/*  207 */         if ((col == 1) && (LA(1) == '<'))
/*      */         {
/*  209 */           this.currentIndent = ind.getText();
/*  210 */           this.text.setLength(loopStartIndex);
/*      */         } else {
/*  212 */           this.currentIndent = null;
/*      */         }
/*      */       }
/*  215 */       else if (_tokenSet_0.member(LA(1)))
/*      */       {
/*  217 */         match(_tokenSet_0);
/*      */       }
/*      */       else
/*      */       {
/*  221 */         if (_cnt5 >= 1) break; throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */       }
/*      */ 
/*  224 */       _cnt5++;
/*      */     }
/*      */ 
/*  227 */     if (new String(this.text.getBuffer(), _begin, this.text.length() - _begin).length() == 0) _ttype = -1;
/*  228 */     if ((_createToken) && (_token == null) && (_ttype != -1)) {
/*  229 */       _token = makeToken(_ttype);
/*  230 */       _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
/*      */     }
/*  232 */     this._returnToken = _token;
/*      */   }
/*      */ 
/*      */   protected final void mINDENT(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
/*  236 */     Token _token = null; int _begin = this.text.length();
/*  237 */     int _ttype = 21;
/*      */ 
/*  241 */     int _cnt8 = 0;
/*      */     while (true)
/*      */     {
/*  244 */       if (LA(1) == ' ') {
/*  245 */         match(' ');
/*      */       }
/*  247 */       else if (LA(1) == '\t') {
/*  248 */         match('\t');
/*      */       }
/*      */       else {
/*  251 */         if (_cnt8 >= 1) break; throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */       }
/*      */ 
/*  254 */       _cnt8++;
/*      */     }
/*      */ 
/*  257 */     if ((_createToken) && (_token == null) && (_ttype != -1)) {
/*  258 */       _token = makeToken(_ttype);
/*  259 */       _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
/*      */     }
/*  261 */     this._returnToken = _token;
/*      */   }
/*      */ 
/*      */   public final void mNEWLINE(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
/*  265 */     Token _token = null; int _begin = this.text.length();
/*  266 */     int _ttype = 5;
/*      */ 
/*  270 */     switch (LA(1))
/*      */     {
/*      */     case '\r':
/*  273 */       match('\r');
/*  274 */       break;
/*      */     case '\n':
/*  278 */       break;
/*      */     default:
/*  282 */       throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */     }
/*      */ 
/*  286 */     match('\n');
/*  287 */     newline(); this.currentIndent = null;
/*  288 */     if ((_createToken) && (_token == null) && (_ttype != -1)) {
/*  289 */       _token = makeToken(_ttype);
/*  290 */       _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
/*      */     }
/*  292 */     this._returnToken = _token;
/*      */   }
/*      */ 
/*      */   public final void mACTION(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
/*  296 */     Token _token = null; int _begin = this.text.length();
/*  297 */     int _ttype = 6;
/*      */ 
/*  300 */     int startCol = getColumn();
/*      */ 
/*  303 */     if ((LA(1) == '<') && (LA(2) == '\\') && (LA(3) == '\\') && (LA(4) == '>') && (_tokenSet_2.member(LA(5)))) {
/*  304 */       mLINE_BREAK(false);
/*  305 */       _ttype = -1;
/*      */     }
/*  307 */     else if ((LA(1) == '<') && (LA(2) == '\\') && (_tokenSet_3.member(LA(3))) && (_tokenSet_4.member(LA(4)))) {
/*  308 */       StringBuffer buf = new StringBuffer(); char uc = '\000';
/*  309 */       int _saveIndex = this.text.length();
/*  310 */       match('<');
/*  311 */       this.text.setLength(_saveIndex);
/*      */ 
/*  313 */       int _cnt13 = 0;
/*      */       while (true)
/*      */       {
/*  316 */         if (LA(1) == '\\') {
/*  317 */           uc = mESC_CHAR(false);
/*  318 */           buf.append(uc);
/*      */         }
/*      */         else {
/*  321 */           if (_cnt13 >= 1) break; throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */         }
/*      */ 
/*  324 */         _cnt13++;
/*      */       }
/*      */ 
/*  327 */       _saveIndex = this.text.length();
/*  328 */       match('>');
/*  329 */       this.text.setLength(_saveIndex);
/*  330 */       this.text.setLength(_begin); this.text.append(buf.toString()); _ttype = 4;
/*      */     }
/*  332 */     else if ((LA(1) == '<') && (LA(2) == '!') && (LA(3) >= '\001') && (LA(3) <= 65534) && (LA(4) >= '\001') && (LA(4) <= 65534)) {
/*  333 */       mCOMMENT(false);
/*  334 */       _ttype = -1;
/*      */     }
/*  336 */     else if ((LA(1) == '<') && (_tokenSet_5.member(LA(2))) && (LA(3) >= '\001') && (LA(3) <= 65534))
/*      */     {
/*  338 */       if ((LA(1) == '<') && (LA(2) == 'i') && (LA(3) == 'f') && ((LA(4) == ' ') || (LA(4) == '(')) && (_tokenSet_6.member(LA(5))) && (LA(6) >= '\001') && (LA(6) <= 65534) && (LA(7) >= '\001') && (LA(7) <= 65534)) {
/*  339 */         int _saveIndex = this.text.length();
/*  340 */         match('<');
/*  341 */         this.text.setLength(_saveIndex);
/*  342 */         match("if");
/*      */ 
/*  346 */         while (LA(1) == ' ') {
/*  347 */           _saveIndex = this.text.length();
/*  348 */           match(' ');
/*  349 */           this.text.setLength(_saveIndex);
/*      */         }
/*      */ 
/*  357 */         match("(");
/*  358 */         mIF_EXPR(false);
/*  359 */         match(")");
/*  360 */         _saveIndex = this.text.length();
/*  361 */         match('>');
/*  362 */         this.text.setLength(_saveIndex);
/*  363 */         _ttype = 7;
/*      */ 
/*  365 */         if ((LA(1) == '\n') || (LA(1) == '\r'))
/*      */         {
/*  367 */           switch (LA(1))
/*      */           {
/*      */           case '\r':
/*  370 */             _saveIndex = this.text.length();
/*  371 */             match('\r');
/*  372 */             this.text.setLength(_saveIndex);
/*  373 */             break;
/*      */           case '\n':
/*  377 */             break;
/*      */           default:
/*  381 */             throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */           }
/*      */ 
/*  385 */           _saveIndex = this.text.length();
/*  386 */           match('\n');
/*  387 */           this.text.setLength(_saveIndex);
/*  388 */           newline();
/*      */         }
/*      */ 
/*      */       }
/*  395 */       else if ((LA(1) == '<') && (LA(2) == 'e') && (LA(3) == 'l') && (LA(4) == 's') && (LA(5) == 'e') && (LA(6) == 'i') && (LA(7) == 'f')) {
/*  396 */         int _saveIndex = this.text.length();
/*  397 */         match('<');
/*  398 */         this.text.setLength(_saveIndex);
/*  399 */         match("elseif");
/*      */ 
/*  403 */         while (LA(1) == ' ') {
/*  404 */           _saveIndex = this.text.length();
/*  405 */           match(' ');
/*  406 */           this.text.setLength(_saveIndex);
/*      */         }
/*      */ 
/*  414 */         match("(");
/*  415 */         mIF_EXPR(false);
/*  416 */         match(")");
/*  417 */         _saveIndex = this.text.length();
/*  418 */         match('>');
/*  419 */         this.text.setLength(_saveIndex);
/*  420 */         _ttype = 8;
/*      */ 
/*  422 */         if ((LA(1) == '\n') || (LA(1) == '\r'))
/*      */         {
/*  424 */           switch (LA(1))
/*      */           {
/*      */           case '\r':
/*  427 */             _saveIndex = this.text.length();
/*  428 */             match('\r');
/*  429 */             this.text.setLength(_saveIndex);
/*  430 */             break;
/*      */           case '\n':
/*  434 */             break;
/*      */           default:
/*  438 */             throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */           }
/*      */ 
/*  442 */           _saveIndex = this.text.length();
/*  443 */           match('\n');
/*  444 */           this.text.setLength(_saveIndex);
/*  445 */           newline();
/*      */         }
/*      */ 
/*      */       }
/*  452 */       else if ((LA(1) == '<') && (LA(2) == 'e') && (LA(3) == 'n') && (LA(4) == 'd') && (LA(5) == 'i') && (LA(6) == 'f') && (LA(7) == '>')) {
/*  453 */         int _saveIndex = this.text.length();
/*  454 */         match('<');
/*  455 */         this.text.setLength(_saveIndex);
/*  456 */         match("endif");
/*  457 */         _saveIndex = this.text.length();
/*  458 */         match('>');
/*  459 */         this.text.setLength(_saveIndex);
/*  460 */         _ttype = 10;
/*      */ 
/*  462 */         if (((LA(1) == '\n') || (LA(1) == '\r')) && (startCol == 1))
/*      */         {
/*  464 */           switch (LA(1))
/*      */           {
/*      */           case '\r':
/*  467 */             _saveIndex = this.text.length();
/*  468 */             match('\r');
/*  469 */             this.text.setLength(_saveIndex);
/*  470 */             break;
/*      */           case '\n':
/*  474 */             break;
/*      */           default:
/*  478 */             throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */           }
/*      */ 
/*  482 */           _saveIndex = this.text.length();
/*  483 */           match('\n');
/*  484 */           this.text.setLength(_saveIndex);
/*  485 */           newline();
/*      */         }
/*      */ 
/*      */       }
/*  492 */       else if ((LA(1) == '<') && (LA(2) == 'e') && (LA(3) == 'l') && (LA(4) == 's') && (LA(5) == 'e') && (LA(6) == '>')) {
/*  493 */         int _saveIndex = this.text.length();
/*  494 */         match('<');
/*  495 */         this.text.setLength(_saveIndex);
/*  496 */         match("else");
/*  497 */         _saveIndex = this.text.length();
/*  498 */         match('>');
/*  499 */         this.text.setLength(_saveIndex);
/*  500 */         _ttype = 9;
/*      */ 
/*  502 */         if ((LA(1) == '\n') || (LA(1) == '\r'))
/*      */         {
/*  504 */           switch (LA(1))
/*      */           {
/*      */           case '\r':
/*  507 */             _saveIndex = this.text.length();
/*  508 */             match('\r');
/*  509 */             this.text.setLength(_saveIndex);
/*  510 */             break;
/*      */           case '\n':
/*  514 */             break;
/*      */           default:
/*  518 */             throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */           }
/*      */ 
/*  522 */           _saveIndex = this.text.length();
/*  523 */           match('\n');
/*  524 */           this.text.setLength(_saveIndex);
/*  525 */           newline();
/*      */         }
/*      */       }
/*      */       else
/*      */       {
/*      */         int _saveIndex;
/*  532 */         if ((LA(1) == '<') && (LA(2) == '@') && (_tokenSet_7.member(LA(3))) && (LA(4) >= '\001') && (LA(4) <= 65534) && (LA(5) >= '\001') && (LA(5) <= 65534) && (LA(6) >= '\001') && (LA(6) <= 65534)) {
/*  533 */           _saveIndex = this.text.length();
/*  534 */           match('<');
/*  535 */           this.text.setLength(_saveIndex);
/*  536 */           _saveIndex = this.text.length();
/*  537 */           match('@');
/*  538 */           this.text.setLength(_saveIndex);
/*      */ 
/*  540 */           int _cnt29 = 0;
/*      */           while (true)
/*      */           {
/*  543 */             if (_tokenSet_7.member(LA(1)))
/*      */             {
/*  545 */               match(_tokenSet_7);
/*      */             }
/*      */             else
/*      */             {
/*  549 */               if (_cnt29 >= 1) break; throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */             }
/*      */ 
/*  552 */             _cnt29++;
/*      */           }
/*      */         }
/*      */ 
/*  556 */         switch (LA(1))
/*      */         {
/*      */         case '(':
/*  559 */           _saveIndex = this.text.length();
/*  560 */           match("()");
/*  561 */           this.text.setLength(_saveIndex);
/*  562 */           _saveIndex = this.text.length();
/*  563 */           match('>');
/*  564 */           this.text.setLength(_saveIndex);
/*  565 */           _ttype = 11;
/*  566 */           break;
/*      */         case '>':
/*  570 */           _saveIndex = this.text.length();
/*  571 */           match('>');
/*  572 */           this.text.setLength(_saveIndex);
/*      */ 
/*  574 */           _ttype = 12;
/*  575 */           String t = new String(this.text.getBuffer(), _begin, this.text.length() - _begin);
/*  576 */           this.text.setLength(_begin); this.text.append(t + "::=");
/*      */ 
/*  579 */           if (((LA(1) == '\n') || (LA(1) == '\r')) && (LA(2) >= '\001') && (LA(2) <= 65534) && (LA(3) >= '\001') && (LA(3) <= 65534))
/*      */           {
/*  581 */             switch (LA(1))
/*      */             {
/*      */             case '\r':
/*  584 */               _saveIndex = this.text.length();
/*  585 */               match('\r');
/*  586 */               this.text.setLength(_saveIndex);
/*  587 */               break;
/*      */             case '\n':
/*  591 */               break;
/*      */             default:
/*  595 */               throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */             }
/*      */ 
/*  599 */             _saveIndex = this.text.length();
/*  600 */             match('\n');
/*  601 */             this.text.setLength(_saveIndex);
/*  602 */             newline();
/*      */           }
/*  604 */           else if ((LA(1) < '\001') || (LA(1) > 65534) || (LA(2) < '\001') || (LA(2) > 65534))
/*      */           {
/*  607 */             throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */           }
/*      */ 
/*  611 */           boolean atLeft = false;
/*      */ 
/*  613 */           int _cnt36 = 0;
/*      */           while (true)
/*      */           {
/*  616 */             if ((LA(1) >= '\001') && (LA(1) <= 65534) && (LA(2) >= '\001') && (LA(2) <= 65534) && (!upcomingAtEND(1)) && ((!upcomingNewline(1)) || (!upcomingAtEND(2))))
/*      */             {
/*  618 */               if (((LA(1) == '\n') || (LA(1) == '\r')) && (LA(2) >= '\001') && (LA(2) <= 65534))
/*      */               {
/*  620 */                 switch (LA(1))
/*      */                 {
/*      */                 case '\r':
/*  623 */                   match('\r');
/*  624 */                   break;
/*      */                 case '\n':
/*  628 */                   break;
/*      */                 default:
/*  632 */                   throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */                 }
/*      */ 
/*  636 */                 match('\n');
/*  637 */                 newline(); atLeft = true;
/*      */               }
/*  639 */               else if ((LA(1) >= '\001') && (LA(1) <= 65534) && (LA(2) >= '\001') && (LA(2) <= 65534)) {
/*  640 */                 matchNot(65535);
/*  641 */                 atLeft = false;
/*      */               }
/*      */               else {
/*  644 */                 throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */               }
/*      */ 
/*      */             }
/*      */             else
/*      */             {
/*  650 */               if (_cnt36 >= 1) break; throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */             }
/*      */ 
/*  653 */             _cnt36++;
/*      */           }
/*      */ 
/*  657 */           if (((LA(1) == '\n') || (LA(1) == '\r')) && (LA(2) >= '\001') && (LA(2) <= 65534))
/*      */           {
/*  659 */             switch (LA(1))
/*      */             {
/*      */             case '\r':
/*  662 */               _saveIndex = this.text.length();
/*  663 */               match('\r');
/*  664 */               this.text.setLength(_saveIndex);
/*  665 */               break;
/*      */             case '\n':
/*  669 */               break;
/*      */             default:
/*  673 */               throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */             }
/*      */ 
/*  677 */             _saveIndex = this.text.length();
/*  678 */             match('\n');
/*  679 */             this.text.setLength(_saveIndex);
/*  680 */             newline(); atLeft = true;
/*      */           }
/*  682 */           else if ((LA(1) < '\001') || (LA(1) > 65534))
/*      */           {
/*  685 */             throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */           }
/*      */ 
/*  690 */           if ((LA(1) == '<') && (LA(2) == '@')) {
/*  691 */             _saveIndex = this.text.length();
/*  692 */             match("<@end>");
/*  693 */             this.text.setLength(_saveIndex);
/*      */           }
/*  695 */           else if ((LA(1) >= '\001') && (LA(1) <= 65534)) {
/*  696 */             matchNot(65535);
/*  697 */             this.self.error("missing region " + t + " <@end> tag");
/*      */           }
/*      */           else {
/*  700 */             throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */           }
/*      */ 
/*  705 */           if (((LA(1) == '\n') || (LA(1) == '\r')) && (atLeft))
/*      */           {
/*  707 */             switch (LA(1))
/*      */             {
/*      */             case '\r':
/*  710 */               _saveIndex = this.text.length();
/*  711 */               match('\r');
/*  712 */               this.text.setLength(_saveIndex);
/*  713 */               break;
/*      */             case '\n':
/*  717 */               break;
/*      */             default:
/*  721 */               throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */             }
/*      */ 
/*  725 */             _saveIndex = this.text.length();
/*  726 */             match('\n');
/*  727 */             this.text.setLength(_saveIndex);
/*  728 */             newline(); } break;
/*      */         default:
/*  738 */           throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */ 
/*  743 */           if ((LA(1) == '<') && (_tokenSet_5.member(LA(2))) && (LA(3) >= '\001') && (LA(3) <= 65534)) {
/*  744 */             int _saveIndex = this.text.length();
/*  745 */             match('<');
/*  746 */             this.text.setLength(_saveIndex);
/*  747 */             mEXPR(false);
/*  748 */             _saveIndex = this.text.length();
/*  749 */             match('>');
/*  750 */             this.text.setLength(_saveIndex);
/*      */           }
/*      */           else {
/*  753 */             throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */           }
/*      */           break;
/*      */         }
/*      */       }
/*      */       int _saveIndex;
/*  758 */       ChunkToken t = new ChunkToken(_ttype, new String(this.text.getBuffer(), _begin, this.text.length() - _begin), this.currentIndent);
/*  759 */       _token = t;
/*      */     }
/*      */     else
/*      */     {
/*  763 */       throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */     }
/*      */ 
/*  766 */     if ((_createToken) && (_token == null) && (_ttype != -1)) {
/*  767 */       _token = makeToken(_ttype);
/*  768 */       _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
/*      */     }
/*  770 */     this._returnToken = _token;
/*      */   }
/*      */ 
/*      */   protected final void mLINE_BREAK(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
/*  774 */     Token _token = null; int _begin = this.text.length();
/*  775 */     int _ttype = 23;
/*      */ 
/*  778 */     match("<\\\\>");
/*      */ 
/*  780 */     switch (LA(1)) {
/*      */     case '\t':
/*      */     case ' ':
/*  783 */       mINDENT(false);
/*  784 */       break;
/*      */     case '\n':
/*      */     case '\r':
/*  788 */       break;
/*      */     default:
/*  792 */       throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */     }
/*      */ 
/*  797 */     switch (LA(1))
/*      */     {
/*      */     case '\r':
/*  800 */       match('\r');
/*  801 */       break;
/*      */     case '\n':
/*  805 */       break;
/*      */     default:
/*  809 */       throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */     }
/*      */ 
/*  813 */     match('\n');
/*  814 */     newline();
/*      */ 
/*  816 */     if ((LA(1) == '\t') || (LA(1) == ' ')) {
/*  817 */       mINDENT(false);
/*      */     }
/*      */ 
/*  823 */     if ((_createToken) && (_token == null) && (_ttype != -1)) {
/*  824 */       _token = makeToken(_ttype);
/*  825 */       _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
/*      */     }
/*  827 */     this._returnToken = _token;
/*      */   }
/*      */ 
/*      */   protected final char mESC_CHAR(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
/*  831 */     char uc = '\000';
/*  832 */     Token _token = null; int _begin = this.text.length();
/*  833 */     int _ttype = 16;
/*      */ 
/*  835 */     Token a = null;
/*  836 */     Token b = null;
/*  837 */     Token c = null;
/*  838 */     Token d = null;
/*      */ 
/*  840 */     if ((LA(1) == '\\') && (LA(2) == 'n')) {
/*  841 */       int _saveIndex = this.text.length();
/*  842 */       match("\\n");
/*  843 */       this.text.setLength(_saveIndex);
/*  844 */       uc = '\n';
/*      */     }
/*  846 */     else if ((LA(1) == '\\') && (LA(2) == 'r')) {
/*  847 */       int _saveIndex = this.text.length();
/*  848 */       match("\\r");
/*  849 */       this.text.setLength(_saveIndex);
/*  850 */       uc = '\r';
/*      */     }
/*  852 */     else if ((LA(1) == '\\') && (LA(2) == 't')) {
/*  853 */       int _saveIndex = this.text.length();
/*  854 */       match("\\t");
/*  855 */       this.text.setLength(_saveIndex);
/*  856 */       uc = '\t';
/*      */     }
/*  858 */     else if ((LA(1) == '\\') && (LA(2) == ' ')) {
/*  859 */       int _saveIndex = this.text.length();
/*  860 */       match("\\ ");
/*  861 */       this.text.setLength(_saveIndex);
/*  862 */       uc = ' ';
/*      */     }
/*  864 */     else if ((LA(1) == '\\') && (LA(2) == 'u')) {
/*  865 */       int _saveIndex = this.text.length();
/*  866 */       match("\\u");
/*  867 */       this.text.setLength(_saveIndex);
/*  868 */       _saveIndex = this.text.length();
/*  869 */       mHEX(true);
/*  870 */       this.text.setLength(_saveIndex);
/*  871 */       a = this._returnToken;
/*  872 */       _saveIndex = this.text.length();
/*  873 */       mHEX(true);
/*  874 */       this.text.setLength(_saveIndex);
/*  875 */       b = this._returnToken;
/*  876 */       _saveIndex = this.text.length();
/*  877 */       mHEX(true);
/*  878 */       this.text.setLength(_saveIndex);
/*  879 */       c = this._returnToken;
/*  880 */       _saveIndex = this.text.length();
/*  881 */       mHEX(true);
/*  882 */       this.text.setLength(_saveIndex);
/*  883 */       d = this._returnToken;
/*  884 */       uc = (char)Integer.parseInt(a.getText() + b.getText() + c.getText() + d.getText(), 16);
/*      */     }
/*      */     else {
/*  887 */       throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */     }
/*      */     int _saveIndex;
/*  890 */     if ((_createToken) && (_token == null) && (_ttype != -1)) {
/*  891 */       _token = makeToken(_ttype);
/*  892 */       _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
/*      */     }
/*  894 */     this._returnToken = _token;
/*  895 */     return uc;
/*      */   }
/*      */ 
/*      */   protected final void mCOMMENT(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
/*  899 */     Token _token = null; int _begin = this.text.length();
/*  900 */     int _ttype = 22;
/*      */ 
/*  903 */     int startCol = getColumn();
/*      */ 
/*  906 */     match("<!");
/*      */ 
/*  911 */     while ((LA(1) != '!') || (LA(2) != '>')) {
/*  912 */       if (((LA(1) == '\n') || (LA(1) == '\r')) && (LA(2) >= '\001') && (LA(2) <= 65534) && (LA(3) >= '\001') && (LA(3) <= 65534))
/*      */       {
/*  914 */         switch (LA(1))
/*      */         {
/*      */         case '\r':
/*  917 */           match('\r');
/*  918 */           break;
/*      */         case '\n':
/*  922 */           break;
/*      */         default:
/*  926 */           throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */         }
/*      */ 
/*  930 */         match('\n');
/*  931 */         newline();
/*      */       } else {
/*  933 */         if ((LA(1) < '\001') || (LA(1) > 65534) || (LA(2) < '\001') || (LA(2) > 65534) || (LA(3) < '\001') || (LA(3) > 65534)) break;
/*  934 */         matchNot(65535);
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/*  942 */     match("!>");
/*      */ 
/*  944 */     if (((LA(1) == '\n') || (LA(1) == '\r')) && (startCol == 1))
/*      */     {
/*  946 */       switch (LA(1))
/*      */       {
/*      */       case '\r':
/*  949 */         match('\r');
/*  950 */         break;
/*      */       case '\n':
/*  954 */         break;
/*      */       default:
/*  958 */         throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */       }
/*      */ 
/*  962 */       match('\n');
/*  963 */       newline();
/*      */     }
/*      */ 
/*  969 */     if ((_createToken) && (_token == null) && (_ttype != -1)) {
/*  970 */       _token = makeToken(_ttype);
/*  971 */       _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
/*      */     }
/*  973 */     this._returnToken = _token;
/*      */   }
/*      */ 
/*      */   protected final void mIF_EXPR(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
/*  977 */     Token _token = null; int _begin = this.text.length();
/*  978 */     int _ttype = 15;
/*      */ 
/*  982 */     int _cnt61 = 0;
/*      */     while (true)
/*      */     {
/*  985 */       switch (LA(1))
/*      */       {
/*      */       case '\\':
/*  988 */         mESC(false);
/*  989 */         break;
/*      */       case '\n':
/*      */       case '\r':
/*  994 */         switch (LA(1))
/*      */         {
/*      */         case '\r':
/*  997 */           match('\r');
/*  998 */           break;
/*      */         case '\n':
/* 1002 */           break;
/*      */         default:
/* 1006 */           throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */         }
/*      */ 
/* 1010 */         match('\n');
/* 1011 */         newline();
/* 1012 */         break;
/*      */       case '{':
/* 1016 */         mSUBTEMPLATE(false);
/* 1017 */         break;
/*      */       case '(':
/* 1021 */         mNESTED_PARENS(false);
/* 1022 */         break;
/*      */       default:
/* 1025 */         if (_tokenSet_8.member(LA(1))) {
/* 1026 */           matchNot(')');
/*      */         }
/*      */         else {
/* 1029 */           if (_cnt61 >= 1) break label241; throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */         }break;
/*      */       }
/* 1032 */       _cnt61++;
/*      */     }
/*      */ 
/* 1035 */     label241: if ((_createToken) && (_token == null) && (_ttype != -1)) {
/* 1036 */       _token = makeToken(_ttype);
/* 1037 */       _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
/*      */     }
/* 1039 */     this._returnToken = _token;
/*      */   }
/*      */ 
/*      */   protected final void mEXPR(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
/* 1043 */     Token _token = null; int _begin = this.text.length();
/* 1044 */     int _ttype = 13;
/*      */ 
/* 1048 */     int _cnt49 = 0;
/*      */     while (true)
/*      */     {
/* 1051 */       switch (LA(1))
/*      */       {
/*      */       case '\\':
/* 1054 */         mESC(false);
/* 1055 */         break;
/*      */       case '\n':
/*      */       case '\r':
/* 1060 */         switch (LA(1))
/*      */         {
/*      */         case '\r':
/* 1063 */           match('\r');
/* 1064 */           break;
/*      */         case '\n':
/* 1068 */           break;
/*      */         default:
/* 1072 */           throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */         }
/*      */ 
/* 1076 */         match('\n');
/* 1077 */         newline();
/* 1078 */         break;
/*      */       case '{':
/* 1082 */         mSUBTEMPLATE(false);
/* 1083 */         break;
/*      */       default:
/* 1086 */         if (((LA(1) == '+') || (LA(1) == '=')) && ((LA(2) == '"') || (LA(2) == '<')))
/*      */         {
/* 1088 */           switch (LA(1))
/*      */           {
/*      */           case '=':
/* 1091 */             match('=');
/* 1092 */             break;
/*      */           case '+':
/* 1096 */             match('+');
/* 1097 */             break;
/*      */           default:
/* 1101 */             throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */           }
/*      */ 
/* 1105 */           mTEMPLATE(false);
/*      */         }
/* 1107 */         else if (((LA(1) == '+') || (LA(1) == '=')) && (LA(2) == '{'))
/*      */         {
/* 1109 */           switch (LA(1))
/*      */           {
/*      */           case '=':
/* 1112 */             match('=');
/* 1113 */             break;
/*      */           case '+':
/* 1117 */             match('+');
/* 1118 */             break;
/*      */           default:
/* 1122 */             throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */           }
/*      */ 
/* 1126 */           mSUBTEMPLATE(false);
/*      */         }
/* 1128 */         else if (((LA(1) == '+') || (LA(1) == '=')) && (_tokenSet_9.member(LA(2))))
/*      */         {
/* 1130 */           switch (LA(1))
/*      */           {
/*      */           case '=':
/* 1133 */             match('=');
/* 1134 */             break;
/*      */           case '+':
/* 1138 */             match('+');
/* 1139 */             break;
/*      */           default:
/* 1143 */             throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */           }
/*      */ 
/* 1148 */           match(_tokenSet_9);
/*      */         }
/* 1151 */         else if (_tokenSet_10.member(LA(1))) {
/* 1152 */           matchNot('>');
/*      */         }
/*      */         else {
/* 1155 */           if (_cnt49 >= 1) break label576; throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */         }break;
/*      */       }
/* 1158 */       _cnt49++;
/*      */     }
/*      */ 
/* 1161 */     label576: if ((_createToken) && (_token == null) && (_ttype != -1)) {
/* 1162 */       _token = makeToken(_ttype);
/* 1163 */       _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
/*      */     }
/* 1165 */     this._returnToken = _token;
/*      */   }
/*      */ 
/*      */   protected final void mESC(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
/* 1169 */     Token _token = null; int _begin = this.text.length();
/* 1170 */     int _ttype = 17;
/*      */ 
/* 1173 */     match('\\');
/* 1174 */     matchNot(65535);
/* 1175 */     if ((_createToken) && (_token == null) && (_ttype != -1)) {
/* 1176 */       _token = makeToken(_ttype);
/* 1177 */       _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
/*      */     }
/* 1179 */     this._returnToken = _token;
/*      */   }
/*      */ 
/*      */   protected final void mSUBTEMPLATE(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
/* 1183 */     Token _token = null; int _begin = this.text.length();
/* 1184 */     int _ttype = 19;
/*      */ 
/* 1187 */     match('{');
/*      */     while (true)
/*      */     {
/* 1191 */       switch (LA(1))
/*      */       {
/*      */       case '{':
/* 1194 */         mSUBTEMPLATE(false);
/* 1195 */         break;
/*      */       case '\\':
/* 1199 */         mESC(false);
/* 1200 */         break;
/*      */       default:
/* 1203 */         if (!_tokenSet_11.member(LA(1))) break label91;
/* 1204 */         matchNot('}');
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 1212 */     label91: match('}');
/* 1213 */     if ((_createToken) && (_token == null) && (_ttype != -1)) {
/* 1214 */       _token = makeToken(_ttype);
/* 1215 */       _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
/*      */     }
/* 1217 */     this._returnToken = _token;
/*      */   }
/*      */ 
/*      */   protected final void mTEMPLATE(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
/* 1221 */     Token _token = null; int _begin = this.text.length();
/* 1222 */     int _ttype = 14;
/*      */ 
/* 1225 */     switch (LA(1))
/*      */     {
/*      */     case '"':
/* 1228 */       match('"');
/*      */       while (true)
/*      */       {
/* 1232 */         if (LA(1) == '\\') {
/* 1233 */           mESC(false);
/*      */         } else {
/* 1235 */           if (!_tokenSet_12.member(LA(1))) break;
/* 1236 */           matchNot('"');
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/* 1244 */       match('"');
/* 1245 */       break;
/*      */     case '<':
/* 1249 */       match("<<");
/*      */ 
/* 1251 */       if (((LA(1) == '\n') || (LA(1) == '\r')) && (LA(2) >= '\001') && (LA(2) <= 65534) && (LA(3) >= '\001') && (LA(3) <= 65534) && (LA(4) >= '\001') && (LA(4) <= 65534))
/*      */       {
/* 1253 */         switch (LA(1))
/*      */         {
/*      */         case '\r':
/* 1256 */           _saveIndex = this.text.length();
/* 1257 */           match('\r');
/* 1258 */           this.text.setLength(_saveIndex);
/* 1259 */           break;
/*      */         case '\n':
/* 1263 */           break;
/*      */         default:
/* 1267 */           throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */         }
/*      */ 
/* 1271 */         int _saveIndex = this.text.length();
/* 1272 */         match('\n');
/* 1273 */         this.text.setLength(_saveIndex);
/* 1274 */         newline();
/*      */       }
/* 1276 */       else if ((LA(1) < '\001') || (LA(1) > 65534) || (LA(2) < '\001') || (LA(2) > 65534) || (LA(3) < '\001') || (LA(3) > 65534))
/*      */       {
/* 1279 */         throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */       }
/*      */ 
/* 1287 */       while ((LA(1) != '>') || (LA(2) != '>') || (LA(3) < '\001') || (LA(3) > 65534)) {
/* 1288 */         if ((LA(1) == '\r') && (LA(2) == '\n') && (LA(3) >= '\001') && (LA(3) <= 65534) && (LA(4) >= '\001') && (LA(4) <= 65534) && (LA(5) >= '\001') && (LA(5) <= 65534) && (LA(3) == '>') && (LA(4) == '>')) {
/* 1289 */           int _saveIndex = this.text.length();
/* 1290 */           match('\r');
/* 1291 */           this.text.setLength(_saveIndex);
/* 1292 */           _saveIndex = this.text.length();
/* 1293 */           match('\n');
/* 1294 */           this.text.setLength(_saveIndex);
/* 1295 */           newline();
/*      */         }
/* 1297 */         else if ((LA(1) == '\n') && (LA(2) >= '\001') && (LA(2) <= 65534) && (LA(3) >= '\001') && (LA(3) <= 65534) && (LA(4) >= '\001') && (LA(4) <= 65534) && (LA(2) == '>') && (LA(3) == '>')) {
/* 1298 */           int _saveIndex = this.text.length();
/* 1299 */           match('\n');
/* 1300 */           this.text.setLength(_saveIndex);
/* 1301 */           newline();
/*      */         }
/* 1303 */         else if (((LA(1) == '\n') || (LA(1) == '\r')) && (LA(2) >= '\001') && (LA(2) <= 65534) && (LA(3) >= '\001') && (LA(3) <= 65534) && (LA(4) >= '\001') && (LA(4) <= 65534))
/*      */         {
/* 1305 */           switch (LA(1))
/*      */           {
/*      */           case '\r':
/* 1308 */             match('\r');
/* 1309 */             break;
/*      */           case '\n':
/* 1313 */             break;
/*      */           default:
/* 1317 */             throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */           }
/*      */ 
/* 1321 */           match('\n');
/* 1322 */           newline();
/*      */         } else {
/* 1324 */           if ((LA(1) < '\001') || (LA(1) > 65534) || (LA(2) < '\001') || (LA(2) > 65534) || (LA(3) < '\001') || (LA(3) > 65534) || (LA(4) < '\001') || (LA(4) > 65534)) break;
/* 1325 */           matchNot(65535);
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/* 1333 */       match(">>");
/* 1334 */       break;
/*      */     default:
/* 1338 */       throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */     }
/*      */ 
/* 1341 */     if ((_createToken) && (_token == null) && (_ttype != -1)) {
/* 1342 */       _token = makeToken(_ttype);
/* 1343 */       _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
/*      */     }
/* 1345 */     this._returnToken = _token;
/*      */   }
/*      */ 
/*      */   protected final void mNESTED_PARENS(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
/* 1349 */     Token _token = null; int _begin = this.text.length();
/* 1350 */     int _ttype = 20;
/*      */ 
/* 1353 */     match('(');
/*      */ 
/* 1355 */     int _cnt70 = 0;
/*      */     while (true)
/*      */     {
/* 1358 */       switch (LA(1))
/*      */       {
/*      */       case '(':
/* 1361 */         mNESTED_PARENS(false);
/* 1362 */         break;
/*      */       case '\\':
/* 1366 */         mESC(false);
/* 1367 */         break;
/*      */       default:
/* 1370 */         if (_tokenSet_13.member(LA(1))) {
/* 1371 */           matchNot(')');
/*      */         }
/*      */         else {
/* 1374 */           if (_cnt70 >= 1) break label135; throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */         }break;
/*      */       }
/* 1377 */       _cnt70++;
/*      */     }
/*      */ 
/* 1380 */     label135: match(')');
/* 1381 */     if ((_createToken) && (_token == null) && (_ttype != -1)) {
/* 1382 */       _token = makeToken(_ttype);
/* 1383 */       _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
/*      */     }
/* 1385 */     this._returnToken = _token;
/*      */   }
/*      */ 
/*      */   protected final void mHEX(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
/* 1389 */     Token _token = null; int _begin = this.text.length();
/* 1390 */     int _ttype = 18;
/*      */ 
/* 1393 */     switch (LA(1)) { case '0':
/*      */     case '1':
/*      */     case '2':
/*      */     case '3':
/*      */     case '4':
/*      */     case '5':
/*      */     case '6':
/*      */     case '7':
/*      */     case '8':
/*      */     case '9':
/* 1398 */       matchRange('0', '9');
/* 1399 */       break;
/*      */     case 'A':
/*      */     case 'B':
/*      */     case 'C':
/*      */     case 'D':
/*      */     case 'E':
/*      */     case 'F':
/* 1404 */       matchRange('A', 'F');
/* 1405 */       break;
/*      */     case 'a':
/*      */     case 'b':
/*      */     case 'c':
/*      */     case 'd':
/*      */     case 'e':
/*      */     case 'f':
/* 1410 */       matchRange('a', 'f');
/* 1411 */       break;
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
/* 1415 */       throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */     }
/*      */ 
/* 1418 */     if ((_createToken) && (_token == null) && (_ttype != -1)) {
/* 1419 */       _token = makeToken(_ttype);
/* 1420 */       _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
/*      */     }
/* 1422 */     this._returnToken = _token;
/*      */   }
/*      */ 
/*      */   private static final long[] mk_tokenSet_0()
/*      */   {
/* 1427 */     long[] data = new long[2048];
/* 1428 */     data[0] = -1152921504606856194L;
/* 1429 */     for (int i = 1; i <= 1022; i++) data[i] = -1L;
/* 1430 */     data[1023] = 9223372036854775807L;
/* 1431 */     return data;
/*      */   }
/*      */ 
/*      */   private static final long[] mk_tokenSet_1() {
/* 1435 */     long[] data = new long[2048];
/* 1436 */     data[0] = -5764607523034234882L;
/* 1437 */     for (int i = 1; i <= 1022; i++) data[i] = -1L;
/* 1438 */     data[1023] = 9223372036854775807L;
/* 1439 */     return data;
/*      */   }
/*      */ 
/*      */   private static final long[] mk_tokenSet_2() {
/* 1443 */     long[] data = new long[1025];
/* 1444 */     data[0] = 4294977024L;
/* 1445 */     return data;
/*      */   }
/*      */ 
/*      */   private static final long[] mk_tokenSet_3() {
/* 1449 */     long[] data = new long[1025];
/* 1450 */     data[0] = 4294967296L;
/* 1451 */     data[1] = 14707067533131776L;
/* 1452 */     return data;
/*      */   }
/*      */ 
/*      */   private static final long[] mk_tokenSet_4() {
/* 1456 */     long[] data = new long[1025];
/* 1457 */     data[0] = 4899634919602388992L;
/* 1458 */     data[1] = 541434314878L;
/* 1459 */     return data;
/*      */   }
/*      */ 
/*      */   private static final long[] mk_tokenSet_5() {
/* 1463 */     long[] data = new long[2048];
/* 1464 */     data[0] = -4611686018427387906L;
/* 1465 */     for (int i = 1; i <= 1022; i++) data[i] = -1L;
/* 1466 */     data[1023] = 9223372036854775807L;
/* 1467 */     return data;
/*      */   }
/*      */ 
/*      */   private static final long[] mk_tokenSet_6() {
/* 1471 */     long[] data = new long[2048];
/* 1472 */     data[0] = -2199023255554L;
/* 1473 */     for (int i = 1; i <= 1022; i++) data[i] = -1L;
/* 1474 */     data[1023] = 9223372036854775807L;
/* 1475 */     return data;
/*      */   }
/*      */ 
/*      */   private static final long[] mk_tokenSet_7() {
/* 1479 */     long[] data = new long[2048];
/* 1480 */     data[0] = -4611687117939015682L;
/* 1481 */     for (int i = 1; i <= 1022; i++) data[i] = -1L;
/* 1482 */     data[1023] = 9223372036854775807L;
/* 1483 */     return data;
/*      */   }
/*      */ 
/*      */   private static final long[] mk_tokenSet_8() {
/* 1487 */     long[] data = new long[2048];
/* 1488 */     data[0] = -3298534892546L;
/* 1489 */     data[1] = -576460752571858945L;
/* 1490 */     for (int i = 2; i <= 1022; i++) data[i] = -1L;
/* 1491 */     data[1023] = 9223372036854775807L;
/* 1492 */     return data;
/*      */   }
/*      */ 
/*      */   private static final long[] mk_tokenSet_9() {
/* 1496 */     long[] data = new long[2048];
/* 1497 */     data[0] = -1152921521786716162L;
/* 1498 */     data[1] = -576460752303423489L;
/* 1499 */     for (int i = 2; i <= 1022; i++) data[i] = -1L;
/* 1500 */     data[1023] = 9223372036854775807L;
/* 1501 */     return data;
/*      */   }
/*      */ 
/*      */   private static final long[] mk_tokenSet_10() {
/* 1505 */     long[] data = new long[2048];
/* 1506 */     data[0] = -6917537823734113282L;
/* 1507 */     data[1] = -576460752571858945L;
/* 1508 */     for (int i = 2; i <= 1022; i++) data[i] = -1L;
/* 1509 */     data[1023] = 9223372036854775807L;
/* 1510 */     return data;
/*      */   }
/*      */ 
/*      */   private static final long[] mk_tokenSet_11() {
/* 1514 */     long[] data = new long[2048];
/* 1515 */     data[0] = -2L;
/* 1516 */     data[1] = -2882303761785552897L;
/* 1517 */     for (int i = 2; i <= 1022; i++) data[i] = -1L;
/* 1518 */     data[1023] = 9223372036854775807L;
/* 1519 */     return data;
/*      */   }
/*      */ 
/*      */   private static final long[] mk_tokenSet_12() {
/* 1523 */     long[] data = new long[2048];
/* 1524 */     data[0] = -17179869186L;
/* 1525 */     data[1] = -268435457L;
/* 1526 */     for (int i = 2; i <= 1022; i++) data[i] = -1L;
/* 1527 */     data[1023] = 9223372036854775807L;
/* 1528 */     return data;
/*      */   }
/*      */ 
/*      */   private static final long[] mk_tokenSet_13() {
/* 1532 */     long[] data = new long[2048];
/* 1533 */     data[0] = -3298534883330L;
/* 1534 */     data[1] = -268435457L;
/* 1535 */     for (int i = 2; i <= 1022; i++) data[i] = -1L;
/* 1536 */     data[1023] = 9223372036854775807L;
/* 1537 */     return data;
/*      */   }
/*      */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.antlr.stringtemplate.language.AngleBracketTemplateLexer
 * JD-Core Version:    0.6.2
 */