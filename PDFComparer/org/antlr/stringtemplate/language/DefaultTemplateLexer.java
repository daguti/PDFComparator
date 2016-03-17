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
/*      */ public class DefaultTemplateLexer extends CharScanner
/*      */   implements TemplateParserTokenTypes, TokenStream
/*      */ {
/*   65 */   protected String currentIndent = null;
/*      */   protected StringTemplate self;
/* 1424 */   public static final BitSet _tokenSet_0 = new BitSet(mk_tokenSet_0());
/*      */ 
/* 1432 */   public static final BitSet _tokenSet_1 = new BitSet(mk_tokenSet_1());
/*      */ 
/* 1438 */   public static final BitSet _tokenSet_2 = new BitSet(mk_tokenSet_2());
/*      */ 
/* 1445 */   public static final BitSet _tokenSet_3 = new BitSet(mk_tokenSet_3());
/*      */ 
/* 1452 */   public static final BitSet _tokenSet_4 = new BitSet(mk_tokenSet_4());
/*      */ 
/* 1460 */   public static final BitSet _tokenSet_5 = new BitSet(mk_tokenSet_5());
/*      */ 
/* 1468 */   public static final BitSet _tokenSet_6 = new BitSet(mk_tokenSet_6());
/*      */ 
/* 1477 */   public static final BitSet _tokenSet_7 = new BitSet(mk_tokenSet_7());
/*      */ 
/* 1486 */   public static final BitSet _tokenSet_8 = new BitSet(mk_tokenSet_8());
/*      */ 
/* 1495 */   public static final BitSet _tokenSet_9 = new BitSet(mk_tokenSet_9());
/*      */ 
/* 1504 */   public static final BitSet _tokenSet_10 = new BitSet(mk_tokenSet_10());
/*      */ 
/* 1513 */   public static final BitSet _tokenSet_11 = new BitSet(mk_tokenSet_11());
/*      */ 
/* 1522 */   public static final BitSet _tokenSet_12 = new BitSet(mk_tokenSet_12());
/*      */ 
/*      */   public DefaultTemplateLexer(StringTemplate self, Reader r)
/*      */   {
/*   69 */     this(r);
/*   70 */     this.self = self;
/*      */   }
/*      */ 
/*      */   public void reportError(RecognitionException e) {
/*   74 */     this.self.error("$...$ chunk lexer error", e);
/*      */   }
/*      */ 
/*      */   protected boolean upcomingELSE(int i) throws CharStreamException {
/*   78 */     return (LA(i) == '$') && (LA(i + 1) == 'e') && (LA(i + 2) == 'l') && (LA(i + 3) == 's') && (LA(i + 4) == 'e') && (LA(i + 5) == '$');
/*      */   }
/*      */ 
/*      */   protected boolean upcomingENDIF(int i) throws CharStreamException
/*      */   {
/*   83 */     return (LA(i) == '$') && (LA(i + 1) == 'e') && (LA(i + 2) == 'n') && (LA(i + 3) == 'd') && (LA(i + 4) == 'i') && (LA(i + 5) == 'f') && (LA(i + 6) == '$');
/*      */   }
/*      */ 
/*      */   protected boolean upcomingAtEND(int i) throws CharStreamException
/*      */   {
/*   88 */     return (LA(i) == '$') && (LA(i + 1) == '@') && (LA(i + 2) == 'e') && (LA(i + 3) == 'n') && (LA(i + 4) == 'd') && (LA(i + 5) == '$');
/*      */   }
/*      */ 
/*      */   protected boolean upcomingNewline(int i) throws CharStreamException {
/*   92 */     return ((LA(i) == '\r') && (LA(i + 1) == '\n')) || (LA(i) == '\n');
/*      */   }
/*      */   public DefaultTemplateLexer(InputStream in) {
/*   95 */     this(new ByteBuffer(in));
/*      */   }
/*      */   public DefaultTemplateLexer(Reader in) {
/*   98 */     this(new CharBuffer(in));
/*      */   }
/*      */   public DefaultTemplateLexer(InputBuffer ib) {
/*  101 */     this(new LexerSharedInputState(ib));
/*      */   }
/*      */   public DefaultTemplateLexer(LexerSharedInputState state) {
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
/*      */         case '$':
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
/*  172 */     int _cnt11 = 0;
/*      */     while (true)
/*      */     {
/*  176 */       int loopStartIndex = this.text.length();
/*  177 */       int col = getColumn();
/*      */ 
/*  179 */       if ((LA(1) == '\\') && (LA(2) == '$')) {
/*  180 */         int _saveIndex = this.text.length();
/*  181 */         match('\\');
/*  182 */         this.text.setLength(_saveIndex);
/*  183 */         match('$');
/*      */       }
/*  185 */       else if ((LA(1) == '\\') && (LA(2) == '\\')) {
/*  186 */         int _saveIndex = this.text.length();
/*  187 */         match('\\');
/*  188 */         this.text.setLength(_saveIndex);
/*  189 */         match('\\');
/*      */       }
/*  191 */       else if ((LA(1) == '\\') && (_tokenSet_1.member(LA(2)))) {
/*  192 */         match('\\');
/*  193 */         matchNot('$');
/*      */       }
/*  195 */       else if ((LA(1) == '\t') || (LA(1) == ' ')) {
/*  196 */         mINDENT(true);
/*  197 */         ind = this._returnToken;
/*      */ 
/*  199 */         if ((col == 1) && (LA(1) == '$'))
/*      */         {
/*  201 */           this.currentIndent = ind.getText();
/*  202 */           this.text.setLength(loopStartIndex);
/*      */         } else {
/*  204 */           this.currentIndent = null;
/*      */         }
/*      */       }
/*  207 */       else if (_tokenSet_0.member(LA(1)))
/*      */       {
/*  209 */         match(_tokenSet_0);
/*      */       }
/*      */       else
/*      */       {
/*  213 */         if (_cnt11 >= 1) break; throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */       }
/*      */ 
/*  216 */       _cnt11++;
/*      */     }
/*      */ 
/*  219 */     if (new String(this.text.getBuffer(), _begin, this.text.length() - _begin).length() == 0) _ttype = -1;
/*  220 */     if ((_createToken) && (_token == null) && (_ttype != -1)) {
/*  221 */       _token = makeToken(_ttype);
/*  222 */       _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
/*      */     }
/*  224 */     this._returnToken = _token;
/*      */   }
/*      */ 
/*      */   protected final void mINDENT(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
/*  228 */     Token _token = null; int _begin = this.text.length();
/*  229 */     int _ttype = 21;
/*      */ 
/*  233 */     int _cnt76 = 0;
/*      */     while (true)
/*      */     {
/*  236 */       if (LA(1) == ' ') {
/*  237 */         match(' ');
/*      */       }
/*  239 */       else if (LA(1) == '\t') {
/*  240 */         match('\t');
/*      */       }
/*      */       else {
/*  243 */         if (_cnt76 >= 1) break; throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */       }
/*      */ 
/*  246 */       _cnt76++;
/*      */     }
/*      */ 
/*  249 */     if ((_createToken) && (_token == null) && (_ttype != -1)) {
/*  250 */       _token = makeToken(_ttype);
/*  251 */       _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
/*      */     }
/*  253 */     this._returnToken = _token;
/*      */   }
/*      */ 
/*      */   public final void mNEWLINE(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
/*  257 */     Token _token = null; int _begin = this.text.length();
/*  258 */     int _ttype = 5;
/*      */ 
/*  262 */     switch (LA(1))
/*      */     {
/*      */     case '\r':
/*  265 */       match('\r');
/*  266 */       break;
/*      */     case '\n':
/*  270 */       break;
/*      */     default:
/*  274 */       throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */     }
/*      */ 
/*  278 */     match('\n');
/*  279 */     newline(); this.currentIndent = null;
/*  280 */     if ((_createToken) && (_token == null) && (_ttype != -1)) {
/*  281 */       _token = makeToken(_ttype);
/*  282 */       _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
/*      */     }
/*  284 */     this._returnToken = _token;
/*      */   }
/*      */ 
/*      */   public final void mACTION(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
/*  288 */     Token _token = null; int _begin = this.text.length();
/*  289 */     int _ttype = 6;
/*      */ 
/*  292 */     int startCol = getColumn();
/*      */ 
/*  295 */     if ((LA(1) == '$') && (LA(2) == '\\') && (LA(3) == '\\') && (LA(4) == '$') && (_tokenSet_2.member(LA(5)))) {
/*  296 */       mLINE_BREAK(false);
/*  297 */       _ttype = -1;
/*      */     }
/*  299 */     else if ((LA(1) == '$') && (LA(2) == '\\') && (_tokenSet_3.member(LA(3))) && (_tokenSet_4.member(LA(4)))) {
/*  300 */       StringBuffer buf = new StringBuffer(); char uc = '\000';
/*  301 */       int _saveIndex = this.text.length();
/*  302 */       match('$');
/*  303 */       this.text.setLength(_saveIndex);
/*      */ 
/*  305 */       int _cnt16 = 0;
/*      */       while (true)
/*      */       {
/*  308 */         if (LA(1) == '\\') {
/*  309 */           uc = mESC_CHAR(false);
/*  310 */           buf.append(uc);
/*      */         }
/*      */         else {
/*  313 */           if (_cnt16 >= 1) break; throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */         }
/*      */ 
/*  316 */         _cnt16++;
/*      */       }
/*      */ 
/*  319 */       _saveIndex = this.text.length();
/*  320 */       match('$');
/*  321 */       this.text.setLength(_saveIndex);
/*  322 */       this.text.setLength(_begin); this.text.append(buf.toString()); _ttype = 4;
/*      */     }
/*  324 */     else if ((LA(1) == '$') && (LA(2) == '!') && (LA(3) >= '\001') && (LA(3) <= 65534) && (LA(4) >= '\001') && (LA(4) <= 65534)) {
/*  325 */       mCOMMENT(false);
/*  326 */       _ttype = -1;
/*      */     }
/*  328 */     else if ((LA(1) == '$') && (_tokenSet_1.member(LA(2))) && (LA(3) >= '\001') && (LA(3) <= 65534))
/*      */     {
/*  330 */       if ((LA(1) == '$') && (LA(2) == 'i') && (LA(3) == 'f') && ((LA(4) == ' ') || (LA(4) == '(')) && (_tokenSet_5.member(LA(5))) && (LA(6) >= '\001') && (LA(6) <= 65534) && (LA(7) >= '\001') && (LA(7) <= 65534)) {
/*  331 */         int _saveIndex = this.text.length();
/*  332 */         match('$');
/*  333 */         this.text.setLength(_saveIndex);
/*  334 */         match("if");
/*      */ 
/*  338 */         while (LA(1) == ' ') {
/*  339 */           _saveIndex = this.text.length();
/*  340 */           match(' ');
/*  341 */           this.text.setLength(_saveIndex);
/*      */         }
/*      */ 
/*  349 */         match("(");
/*  350 */         mIF_EXPR(false);
/*  351 */         match(")");
/*  352 */         _saveIndex = this.text.length();
/*  353 */         match('$');
/*  354 */         this.text.setLength(_saveIndex);
/*  355 */         _ttype = 7;
/*      */ 
/*  357 */         if ((LA(1) == '\n') || (LA(1) == '\r'))
/*      */         {
/*  359 */           switch (LA(1))
/*      */           {
/*      */           case '\r':
/*  362 */             _saveIndex = this.text.length();
/*  363 */             match('\r');
/*  364 */             this.text.setLength(_saveIndex);
/*  365 */             break;
/*      */           case '\n':
/*  369 */             break;
/*      */           default:
/*  373 */             throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */           }
/*      */ 
/*  377 */           _saveIndex = this.text.length();
/*  378 */           match('\n');
/*  379 */           this.text.setLength(_saveIndex);
/*  380 */           newline();
/*      */         }
/*      */ 
/*      */       }
/*  387 */       else if ((LA(1) == '$') && (LA(2) == 'e') && (LA(3) == 'l') && (LA(4) == 's') && (LA(5) == 'e') && (LA(6) == 'i') && (LA(7) == 'f')) {
/*  388 */         int _saveIndex = this.text.length();
/*  389 */         match('$');
/*  390 */         this.text.setLength(_saveIndex);
/*  391 */         match("elseif");
/*      */ 
/*  395 */         while (LA(1) == ' ') {
/*  396 */           _saveIndex = this.text.length();
/*  397 */           match(' ');
/*  398 */           this.text.setLength(_saveIndex);
/*      */         }
/*      */ 
/*  406 */         match("(");
/*  407 */         mIF_EXPR(false);
/*  408 */         match(")");
/*  409 */         _saveIndex = this.text.length();
/*  410 */         match('$');
/*  411 */         this.text.setLength(_saveIndex);
/*  412 */         _ttype = 8;
/*      */ 
/*  414 */         if ((LA(1) == '\n') || (LA(1) == '\r'))
/*      */         {
/*  416 */           switch (LA(1))
/*      */           {
/*      */           case '\r':
/*  419 */             _saveIndex = this.text.length();
/*  420 */             match('\r');
/*  421 */             this.text.setLength(_saveIndex);
/*  422 */             break;
/*      */           case '\n':
/*  426 */             break;
/*      */           default:
/*  430 */             throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */           }
/*      */ 
/*  434 */           _saveIndex = this.text.length();
/*  435 */           match('\n');
/*  436 */           this.text.setLength(_saveIndex);
/*  437 */           newline();
/*      */         }
/*      */ 
/*      */       }
/*  444 */       else if ((LA(1) == '$') && (LA(2) == 'e') && (LA(3) == 'n') && (LA(4) == 'd') && (LA(5) == 'i') && (LA(6) == 'f') && (LA(7) == '$')) {
/*  445 */         int _saveIndex = this.text.length();
/*  446 */         match('$');
/*  447 */         this.text.setLength(_saveIndex);
/*  448 */         match("endif");
/*  449 */         _saveIndex = this.text.length();
/*  450 */         match('$');
/*  451 */         this.text.setLength(_saveIndex);
/*  452 */         _ttype = 10;
/*      */ 
/*  454 */         if (((LA(1) == '\n') || (LA(1) == '\r')) && (startCol == 1))
/*      */         {
/*  456 */           switch (LA(1))
/*      */           {
/*      */           case '\r':
/*  459 */             _saveIndex = this.text.length();
/*  460 */             match('\r');
/*  461 */             this.text.setLength(_saveIndex);
/*  462 */             break;
/*      */           case '\n':
/*  466 */             break;
/*      */           default:
/*  470 */             throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */           }
/*      */ 
/*  474 */           _saveIndex = this.text.length();
/*  475 */           match('\n');
/*  476 */           this.text.setLength(_saveIndex);
/*  477 */           newline();
/*      */         }
/*      */ 
/*      */       }
/*  484 */       else if ((LA(1) == '$') && (LA(2) == 'e') && (LA(3) == 'l') && (LA(4) == 's') && (LA(5) == 'e') && (LA(6) == '$')) {
/*  485 */         int _saveIndex = this.text.length();
/*  486 */         match('$');
/*  487 */         this.text.setLength(_saveIndex);
/*  488 */         match("else");
/*  489 */         _saveIndex = this.text.length();
/*  490 */         match('$');
/*  491 */         this.text.setLength(_saveIndex);
/*  492 */         _ttype = 9;
/*      */ 
/*  494 */         if ((LA(1) == '\n') || (LA(1) == '\r'))
/*      */         {
/*  496 */           switch (LA(1))
/*      */           {
/*      */           case '\r':
/*  499 */             _saveIndex = this.text.length();
/*  500 */             match('\r');
/*  501 */             this.text.setLength(_saveIndex);
/*  502 */             break;
/*      */           case '\n':
/*  506 */             break;
/*      */           default:
/*  510 */             throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */           }
/*      */ 
/*  514 */           _saveIndex = this.text.length();
/*  515 */           match('\n');
/*  516 */           this.text.setLength(_saveIndex);
/*  517 */           newline();
/*      */         }
/*      */       }
/*      */       else
/*      */       {
/*      */         int _saveIndex;
/*  524 */         if ((LA(1) == '$') && (LA(2) == '@') && (_tokenSet_6.member(LA(3))) && (LA(4) >= '\001') && (LA(4) <= 65534) && (LA(5) >= '\001') && (LA(5) <= 65534) && (LA(6) >= '\001') && (LA(6) <= 65534)) {
/*  525 */           _saveIndex = this.text.length();
/*  526 */           match('$');
/*  527 */           this.text.setLength(_saveIndex);
/*  528 */           _saveIndex = this.text.length();
/*  529 */           match('@');
/*  530 */           this.text.setLength(_saveIndex);
/*      */ 
/*  532 */           int _cnt32 = 0;
/*      */           while (true)
/*      */           {
/*  535 */             if (_tokenSet_6.member(LA(1)))
/*      */             {
/*  537 */               match(_tokenSet_6);
/*      */             }
/*      */             else
/*      */             {
/*  541 */               if (_cnt32 >= 1) break; throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */             }
/*      */ 
/*  544 */             _cnt32++;
/*      */           }
/*      */         }
/*      */ 
/*  548 */         switch (LA(1))
/*      */         {
/*      */         case '(':
/*  551 */           _saveIndex = this.text.length();
/*  552 */           match("()");
/*  553 */           this.text.setLength(_saveIndex);
/*  554 */           _saveIndex = this.text.length();
/*  555 */           match('$');
/*  556 */           this.text.setLength(_saveIndex);
/*  557 */           _ttype = 11;
/*  558 */           break;
/*      */         case '$':
/*  562 */           _saveIndex = this.text.length();
/*  563 */           match('$');
/*  564 */           this.text.setLength(_saveIndex);
/*  565 */           _ttype = 12;
/*  566 */           String t = new String(this.text.getBuffer(), _begin, this.text.length() - _begin);
/*  567 */           this.text.setLength(_begin); this.text.append(t + "::=");
/*      */ 
/*  570 */           if (((LA(1) == '\n') || (LA(1) == '\r')) && (LA(2) >= '\001') && (LA(2) <= 65534) && (LA(3) >= '\001') && (LA(3) <= 65534))
/*      */           {
/*  572 */             switch (LA(1))
/*      */             {
/*      */             case '\r':
/*  575 */               _saveIndex = this.text.length();
/*  576 */               match('\r');
/*  577 */               this.text.setLength(_saveIndex);
/*  578 */               break;
/*      */             case '\n':
/*  582 */               break;
/*      */             default:
/*  586 */               throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */             }
/*      */ 
/*  590 */             _saveIndex = this.text.length();
/*  591 */             match('\n');
/*  592 */             this.text.setLength(_saveIndex);
/*  593 */             newline();
/*      */           }
/*  595 */           else if ((LA(1) < '\001') || (LA(1) > 65534) || (LA(2) < '\001') || (LA(2) > 65534))
/*      */           {
/*  598 */             throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */           }
/*      */ 
/*  602 */           boolean atLeft = false;
/*      */ 
/*  604 */           int _cnt39 = 0;
/*      */           while (true)
/*      */           {
/*  607 */             if ((LA(1) >= '\001') && (LA(1) <= 65534) && (LA(2) >= '\001') && (LA(2) <= 65534) && (!upcomingAtEND(1)) && ((!upcomingNewline(1)) || (!upcomingAtEND(2))))
/*      */             {
/*  609 */               if (((LA(1) == '\n') || (LA(1) == '\r')) && (LA(2) >= '\001') && (LA(2) <= 65534))
/*      */               {
/*  611 */                 switch (LA(1))
/*      */                 {
/*      */                 case '\r':
/*  614 */                   match('\r');
/*  615 */                   break;
/*      */                 case '\n':
/*  619 */                   break;
/*      */                 default:
/*  623 */                   throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */                 }
/*      */ 
/*  627 */                 match('\n');
/*  628 */                 newline(); atLeft = true;
/*      */               }
/*  630 */               else if ((LA(1) >= '\001') && (LA(1) <= 65534) && (LA(2) >= '\001') && (LA(2) <= 65534)) {
/*  631 */                 matchNot(65535);
/*  632 */                 atLeft = false;
/*      */               }
/*      */               else {
/*  635 */                 throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */               }
/*      */ 
/*      */             }
/*      */             else
/*      */             {
/*  641 */               if (_cnt39 >= 1) break; throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */             }
/*      */ 
/*  644 */             _cnt39++;
/*      */           }
/*      */ 
/*  648 */           if (((LA(1) == '\n') || (LA(1) == '\r')) && (LA(2) >= '\001') && (LA(2) <= 65534))
/*      */           {
/*  650 */             switch (LA(1))
/*      */             {
/*      */             case '\r':
/*  653 */               _saveIndex = this.text.length();
/*  654 */               match('\r');
/*  655 */               this.text.setLength(_saveIndex);
/*  656 */               break;
/*      */             case '\n':
/*  660 */               break;
/*      */             default:
/*  664 */               throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */             }
/*      */ 
/*  668 */             _saveIndex = this.text.length();
/*  669 */             match('\n');
/*  670 */             this.text.setLength(_saveIndex);
/*  671 */             newline(); atLeft = true;
/*      */           }
/*  673 */           else if ((LA(1) < '\001') || (LA(1) > 65534))
/*      */           {
/*  676 */             throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */           }
/*      */ 
/*  681 */           if ((LA(1) == '$') && (LA(2) == '@')) {
/*  682 */             _saveIndex = this.text.length();
/*  683 */             match("$@end$");
/*  684 */             this.text.setLength(_saveIndex);
/*      */           }
/*  686 */           else if ((LA(1) >= '\001') && (LA(1) <= 65534)) {
/*  687 */             matchNot(65535);
/*  688 */             this.self.error("missing region " + t + " $@end$ tag");
/*      */           }
/*      */           else {
/*  691 */             throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */           }
/*      */ 
/*  696 */           if (((LA(1) == '\n') || (LA(1) == '\r')) && (atLeft))
/*      */           {
/*  698 */             switch (LA(1))
/*      */             {
/*      */             case '\r':
/*  701 */               _saveIndex = this.text.length();
/*  702 */               match('\r');
/*  703 */               this.text.setLength(_saveIndex);
/*  704 */               break;
/*      */             case '\n':
/*  708 */               break;
/*      */             default:
/*  712 */               throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */             }
/*      */ 
/*  716 */             _saveIndex = this.text.length();
/*  717 */             match('\n');
/*  718 */             this.text.setLength(_saveIndex);
/*  719 */             newline(); } break;
/*      */         default:
/*  729 */           throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */ 
/*  734 */           if ((LA(1) == '$') && (_tokenSet_1.member(LA(2))) && (LA(3) >= '\001') && (LA(3) <= 65534)) {
/*  735 */             int _saveIndex = this.text.length();
/*  736 */             match('$');
/*  737 */             this.text.setLength(_saveIndex);
/*  738 */             mEXPR(false);
/*  739 */             _saveIndex = this.text.length();
/*  740 */             match('$');
/*  741 */             this.text.setLength(_saveIndex);
/*      */           }
/*      */           else {
/*  744 */             throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */           }
/*      */           break;
/*      */         }
/*      */       }
/*      */       int _saveIndex;
/*  749 */       ChunkToken t = new ChunkToken(_ttype, new String(this.text.getBuffer(), _begin, this.text.length() - _begin), this.currentIndent);
/*  750 */       _token = t;
/*      */     }
/*      */     else
/*      */     {
/*  754 */       throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */     }
/*      */ 
/*  757 */     if ((_createToken) && (_token == null) && (_ttype != -1)) {
/*  758 */       _token = makeToken(_ttype);
/*  759 */       _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
/*      */     }
/*  761 */     this._returnToken = _token;
/*      */   }
/*      */ 
/*      */   protected final void mLINE_BREAK(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
/*  765 */     Token _token = null; int _begin = this.text.length();
/*  766 */     int _ttype = 23;
/*      */ 
/*  769 */     match("$\\\\$");
/*      */ 
/*  771 */     switch (LA(1)) {
/*      */     case '\t':
/*      */     case ' ':
/*  774 */       mINDENT(false);
/*  775 */       break;
/*      */     case '\n':
/*      */     case '\r':
/*  779 */       break;
/*      */     default:
/*  783 */       throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */     }
/*      */ 
/*  788 */     switch (LA(1))
/*      */     {
/*      */     case '\r':
/*  791 */       match('\r');
/*  792 */       break;
/*      */     case '\n':
/*  796 */       break;
/*      */     default:
/*  800 */       throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */     }
/*      */ 
/*  804 */     match('\n');
/*  805 */     newline();
/*      */ 
/*  807 */     if ((LA(1) == '\t') || (LA(1) == ' ')) {
/*  808 */       mINDENT(false);
/*      */     }
/*      */ 
/*  814 */     if ((_createToken) && (_token == null) && (_ttype != -1)) {
/*  815 */       _token = makeToken(_ttype);
/*  816 */       _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
/*      */     }
/*  818 */     this._returnToken = _token;
/*      */   }
/*      */ 
/*      */   protected final char mESC_CHAR(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
/*  822 */     char uc = '\000';
/*  823 */     Token _token = null; int _begin = this.text.length();
/*  824 */     int _ttype = 16;
/*      */ 
/*  826 */     Token a = null;
/*  827 */     Token b = null;
/*  828 */     Token c = null;
/*  829 */     Token d = null;
/*      */ 
/*  831 */     if ((LA(1) == '\\') && (LA(2) == 'n')) {
/*  832 */       int _saveIndex = this.text.length();
/*  833 */       match("\\n");
/*  834 */       this.text.setLength(_saveIndex);
/*  835 */       uc = '\n';
/*      */     }
/*  837 */     else if ((LA(1) == '\\') && (LA(2) == 'r')) {
/*  838 */       int _saveIndex = this.text.length();
/*  839 */       match("\\r");
/*  840 */       this.text.setLength(_saveIndex);
/*  841 */       uc = '\r';
/*      */     }
/*  843 */     else if ((LA(1) == '\\') && (LA(2) == 't')) {
/*  844 */       int _saveIndex = this.text.length();
/*  845 */       match("\\t");
/*  846 */       this.text.setLength(_saveIndex);
/*  847 */       uc = '\t';
/*      */     }
/*  849 */     else if ((LA(1) == '\\') && (LA(2) == ' ')) {
/*  850 */       int _saveIndex = this.text.length();
/*  851 */       match("\\ ");
/*  852 */       this.text.setLength(_saveIndex);
/*  853 */       uc = ' ';
/*      */     }
/*  855 */     else if ((LA(1) == '\\') && (LA(2) == 'u')) {
/*  856 */       int _saveIndex = this.text.length();
/*  857 */       match("\\u");
/*  858 */       this.text.setLength(_saveIndex);
/*  859 */       _saveIndex = this.text.length();
/*  860 */       mHEX(true);
/*  861 */       this.text.setLength(_saveIndex);
/*  862 */       a = this._returnToken;
/*  863 */       _saveIndex = this.text.length();
/*  864 */       mHEX(true);
/*  865 */       this.text.setLength(_saveIndex);
/*  866 */       b = this._returnToken;
/*  867 */       _saveIndex = this.text.length();
/*  868 */       mHEX(true);
/*  869 */       this.text.setLength(_saveIndex);
/*  870 */       c = this._returnToken;
/*  871 */       _saveIndex = this.text.length();
/*  872 */       mHEX(true);
/*  873 */       this.text.setLength(_saveIndex);
/*  874 */       d = this._returnToken;
/*  875 */       uc = (char)Integer.parseInt(a.getText() + b.getText() + c.getText() + d.getText(), 16);
/*      */     }
/*      */     else {
/*  878 */       throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */     }
/*      */     int _saveIndex;
/*  881 */     if ((_createToken) && (_token == null) && (_ttype != -1)) {
/*  882 */       _token = makeToken(_ttype);
/*  883 */       _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
/*      */     }
/*  885 */     this._returnToken = _token;
/*  886 */     return uc;
/*      */   }
/*      */ 
/*      */   protected final void mCOMMENT(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
/*  890 */     Token _token = null; int _begin = this.text.length();
/*  891 */     int _ttype = 22;
/*      */ 
/*  894 */     int startCol = getColumn();
/*      */ 
/*  897 */     match("$!");
/*      */ 
/*  902 */     while ((LA(1) != '!') || (LA(2) != '$')) {
/*  903 */       if (((LA(1) == '\n') || (LA(1) == '\r')) && (LA(2) >= '\001') && (LA(2) <= 65534) && (LA(3) >= '\001') && (LA(3) <= 65534))
/*      */       {
/*  905 */         switch (LA(1))
/*      */         {
/*      */         case '\r':
/*  908 */           match('\r');
/*  909 */           break;
/*      */         case '\n':
/*  913 */           break;
/*      */         default:
/*  917 */           throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */         }
/*      */ 
/*  921 */         match('\n');
/*  922 */         newline();
/*      */       } else {
/*  924 */         if ((LA(1) < '\001') || (LA(1) > 65534) || (LA(2) < '\001') || (LA(2) > 65534) || (LA(3) < '\001') || (LA(3) > 65534)) break;
/*  925 */         matchNot(65535);
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/*  933 */     match("!$");
/*      */ 
/*  935 */     if (((LA(1) == '\n') || (LA(1) == '\r')) && (startCol == 1))
/*      */     {
/*  937 */       switch (LA(1))
/*      */       {
/*      */       case '\r':
/*  940 */         match('\r');
/*  941 */         break;
/*      */       case '\n':
/*  945 */         break;
/*      */       default:
/*  949 */         throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */       }
/*      */ 
/*  953 */       match('\n');
/*  954 */       newline();
/*      */     }
/*      */ 
/*  960 */     if ((_createToken) && (_token == null) && (_ttype != -1)) {
/*  961 */       _token = makeToken(_ttype);
/*  962 */       _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
/*      */     }
/*  964 */     this._returnToken = _token;
/*      */   }
/*      */ 
/*      */   protected final void mIF_EXPR(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
/*  968 */     Token _token = null; int _begin = this.text.length();
/*  969 */     int _ttype = 15;
/*      */ 
/*  973 */     int _cnt64 = 0;
/*      */     while (true)
/*      */     {
/*  976 */       switch (LA(1))
/*      */       {
/*      */       case '\\':
/*  979 */         mESC(false);
/*  980 */         break;
/*      */       case '\n':
/*      */       case '\r':
/*  985 */         switch (LA(1))
/*      */         {
/*      */         case '\r':
/*  988 */           match('\r');
/*  989 */           break;
/*      */         case '\n':
/*  993 */           break;
/*      */         default:
/*  997 */           throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */         }
/*      */ 
/* 1001 */         match('\n');
/* 1002 */         newline();
/* 1003 */         break;
/*      */       case '{':
/* 1007 */         mSUBTEMPLATE(false);
/* 1008 */         break;
/*      */       case '(':
/* 1012 */         mNESTED_PARENS(false);
/* 1013 */         break;
/*      */       default:
/* 1016 */         if (_tokenSet_7.member(LA(1))) {
/* 1017 */           matchNot(')');
/*      */         }
/*      */         else {
/* 1020 */           if (_cnt64 >= 1) break label241; throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */         }break;
/*      */       }
/* 1023 */       _cnt64++;
/*      */     }
/*      */ 
/* 1026 */     label241: if ((_createToken) && (_token == null) && (_ttype != -1)) {
/* 1027 */       _token = makeToken(_ttype);
/* 1028 */       _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
/*      */     }
/* 1030 */     this._returnToken = _token;
/*      */   }
/*      */ 
/*      */   protected final void mEXPR(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
/* 1034 */     Token _token = null; int _begin = this.text.length();
/* 1035 */     int _ttype = 13;
/*      */ 
/* 1039 */     int _cnt52 = 0;
/*      */     while (true)
/*      */     {
/* 1042 */       switch (LA(1))
/*      */       {
/*      */       case '\\':
/* 1045 */         mESC(false);
/* 1046 */         break;
/*      */       case '\n':
/*      */       case '\r':
/* 1051 */         switch (LA(1))
/*      */         {
/*      */         case '\r':
/* 1054 */           match('\r');
/* 1055 */           break;
/*      */         case '\n':
/* 1059 */           break;
/*      */         default:
/* 1063 */           throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */         }
/*      */ 
/* 1067 */         match('\n');
/* 1068 */         newline();
/* 1069 */         break;
/*      */       case '{':
/* 1073 */         mSUBTEMPLATE(false);
/* 1074 */         break;
/*      */       default:
/* 1077 */         if (((LA(1) == '+') || (LA(1) == '=')) && ((LA(2) == '"') || (LA(2) == '<')))
/*      */         {
/* 1079 */           switch (LA(1))
/*      */           {
/*      */           case '=':
/* 1082 */             match('=');
/* 1083 */             break;
/*      */           case '+':
/* 1087 */             match('+');
/* 1088 */             break;
/*      */           default:
/* 1092 */             throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */           }
/*      */ 
/* 1096 */           mTEMPLATE(false);
/*      */         }
/* 1098 */         else if (((LA(1) == '+') || (LA(1) == '=')) && (LA(2) == '{'))
/*      */         {
/* 1100 */           switch (LA(1))
/*      */           {
/*      */           case '=':
/* 1103 */             match('=');
/* 1104 */             break;
/*      */           case '+':
/* 1108 */             match('+');
/* 1109 */             break;
/*      */           default:
/* 1113 */             throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */           }
/*      */ 
/* 1117 */           mSUBTEMPLATE(false);
/*      */         }
/* 1119 */         else if (((LA(1) == '+') || (LA(1) == '=')) && (_tokenSet_8.member(LA(2))))
/*      */         {
/* 1121 */           switch (LA(1))
/*      */           {
/*      */           case '=':
/* 1124 */             match('=');
/* 1125 */             break;
/*      */           case '+':
/* 1129 */             match('+');
/* 1130 */             break;
/*      */           default:
/* 1134 */             throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */           }
/*      */ 
/* 1139 */           match(_tokenSet_8);
/*      */         }
/* 1142 */         else if (_tokenSet_9.member(LA(1))) {
/* 1143 */           matchNot('$');
/*      */         }
/*      */         else {
/* 1146 */           if (_cnt52 >= 1) break label576; throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */         }break;
/*      */       }
/* 1149 */       _cnt52++;
/*      */     }
/*      */ 
/* 1152 */     label576: if ((_createToken) && (_token == null) && (_ttype != -1)) {
/* 1153 */       _token = makeToken(_ttype);
/* 1154 */       _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
/*      */     }
/* 1156 */     this._returnToken = _token;
/*      */   }
/*      */ 
/*      */   protected final void mESC(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
/* 1160 */     Token _token = null; int _begin = this.text.length();
/* 1161 */     int _ttype = 17;
/*      */ 
/* 1164 */     match('\\');
/* 1165 */     matchNot(65535);
/* 1166 */     if ((_createToken) && (_token == null) && (_ttype != -1)) {
/* 1167 */       _token = makeToken(_ttype);
/* 1168 */       _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
/*      */     }
/* 1170 */     this._returnToken = _token;
/*      */   }
/*      */ 
/*      */   protected final void mSUBTEMPLATE(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
/* 1174 */     Token _token = null; int _begin = this.text.length();
/* 1175 */     int _ttype = 19;
/*      */ 
/* 1178 */     match('{');
/*      */     while (true)
/*      */     {
/* 1182 */       switch (LA(1))
/*      */       {
/*      */       case '{':
/* 1185 */         mSUBTEMPLATE(false);
/* 1186 */         break;
/*      */       case '\\':
/* 1190 */         mESC(false);
/* 1191 */         break;
/*      */       default:
/* 1194 */         if (!_tokenSet_10.member(LA(1))) break label91;
/* 1195 */         matchNot('}');
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 1203 */     label91: match('}');
/* 1204 */     if ((_createToken) && (_token == null) && (_ttype != -1)) {
/* 1205 */       _token = makeToken(_ttype);
/* 1206 */       _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
/*      */     }
/* 1208 */     this._returnToken = _token;
/*      */   }
/*      */ 
/*      */   protected final void mTEMPLATE(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
/* 1212 */     Token _token = null; int _begin = this.text.length();
/* 1213 */     int _ttype = 14;
/*      */ 
/* 1216 */     switch (LA(1))
/*      */     {
/*      */     case '"':
/* 1219 */       match('"');
/*      */       while (true)
/*      */       {
/* 1223 */         if (LA(1) == '\\') {
/* 1224 */           mESC(false);
/*      */         } else {
/* 1226 */           if (!_tokenSet_11.member(LA(1))) break;
/* 1227 */           matchNot('"');
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/* 1235 */       match('"');
/* 1236 */       break;
/*      */     case '<':
/* 1240 */       match("<<");
/*      */ 
/* 1242 */       if (((LA(1) == '\n') || (LA(1) == '\r')) && (LA(2) >= '\001') && (LA(2) <= 65534) && (LA(3) >= '\001') && (LA(3) <= 65534) && (LA(4) >= '\001') && (LA(4) <= 65534))
/*      */       {
/* 1244 */         switch (LA(1))
/*      */         {
/*      */         case '\r':
/* 1247 */           _saveIndex = this.text.length();
/* 1248 */           match('\r');
/* 1249 */           this.text.setLength(_saveIndex);
/* 1250 */           break;
/*      */         case '\n':
/* 1254 */           break;
/*      */         default:
/* 1258 */           throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */         }
/*      */ 
/* 1262 */         int _saveIndex = this.text.length();
/* 1263 */         match('\n');
/* 1264 */         this.text.setLength(_saveIndex);
/* 1265 */         newline();
/*      */       }
/* 1267 */       else if ((LA(1) < '\001') || (LA(1) > 65534) || (LA(2) < '\001') || (LA(2) > 65534) || (LA(3) < '\001') || (LA(3) > 65534))
/*      */       {
/* 1270 */         throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */       }
/*      */ 
/* 1278 */       while ((LA(1) != '>') || (LA(2) != '>') || (LA(3) < '\001') || (LA(3) > 65534)) {
/* 1279 */         if ((LA(1) == '\r') && (LA(2) == '\n') && (LA(3) >= '\001') && (LA(3) <= 65534) && (LA(4) >= '\001') && (LA(4) <= 65534) && (LA(5) >= '\001') && (LA(5) <= 65534) && (LA(3) == '>') && (LA(4) == '>')) {
/* 1280 */           int _saveIndex = this.text.length();
/* 1281 */           match('\r');
/* 1282 */           this.text.setLength(_saveIndex);
/* 1283 */           _saveIndex = this.text.length();
/* 1284 */           match('\n');
/* 1285 */           this.text.setLength(_saveIndex);
/* 1286 */           newline();
/*      */         }
/* 1288 */         else if ((LA(1) == '\n') && (LA(2) >= '\001') && (LA(2) <= 65534) && (LA(3) >= '\001') && (LA(3) <= 65534) && (LA(4) >= '\001') && (LA(4) <= 65534) && (LA(2) == '>') && (LA(3) == '>')) {
/* 1289 */           int _saveIndex = this.text.length();
/* 1290 */           match('\n');
/* 1291 */           this.text.setLength(_saveIndex);
/* 1292 */           newline();
/*      */         }
/* 1294 */         else if (((LA(1) == '\n') || (LA(1) == '\r')) && (LA(2) >= '\001') && (LA(2) <= 65534) && (LA(3) >= '\001') && (LA(3) <= 65534) && (LA(4) >= '\001') && (LA(4) <= 65534))
/*      */         {
/* 1296 */           switch (LA(1))
/*      */           {
/*      */           case '\r':
/* 1299 */             match('\r');
/* 1300 */             break;
/*      */           case '\n':
/* 1304 */             break;
/*      */           default:
/* 1308 */             throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */           }
/*      */ 
/* 1312 */           match('\n');
/* 1313 */           newline();
/*      */         } else {
/* 1315 */           if ((LA(1) < '\001') || (LA(1) > 65534) || (LA(2) < '\001') || (LA(2) > 65534) || (LA(3) < '\001') || (LA(3) > 65534) || (LA(4) < '\001') || (LA(4) > 65534)) break;
/* 1316 */           matchNot(65535);
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/* 1324 */       match(">>");
/* 1325 */       break;
/*      */     default:
/* 1329 */       throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */     }
/*      */ 
/* 1332 */     if ((_createToken) && (_token == null) && (_ttype != -1)) {
/* 1333 */       _token = makeToken(_ttype);
/* 1334 */       _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
/*      */     }
/* 1336 */     this._returnToken = _token;
/*      */   }
/*      */ 
/*      */   protected final void mNESTED_PARENS(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
/* 1340 */     Token _token = null; int _begin = this.text.length();
/* 1341 */     int _ttype = 20;
/*      */ 
/* 1344 */     match('(');
/*      */ 
/* 1346 */     int _cnt73 = 0;
/*      */     while (true)
/*      */     {
/* 1349 */       switch (LA(1))
/*      */       {
/*      */       case '(':
/* 1352 */         mNESTED_PARENS(false);
/* 1353 */         break;
/*      */       case '\\':
/* 1357 */         mESC(false);
/* 1358 */         break;
/*      */       default:
/* 1361 */         if (_tokenSet_12.member(LA(1))) {
/* 1362 */           matchNot(')');
/*      */         }
/*      */         else {
/* 1365 */           if (_cnt73 >= 1) break label135; throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */         }break;
/*      */       }
/* 1368 */       _cnt73++;
/*      */     }
/*      */ 
/* 1371 */     label135: match(')');
/* 1372 */     if ((_createToken) && (_token == null) && (_ttype != -1)) {
/* 1373 */       _token = makeToken(_ttype);
/* 1374 */       _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
/*      */     }
/* 1376 */     this._returnToken = _token;
/*      */   }
/*      */ 
/*      */   protected final void mHEX(boolean _createToken) throws RecognitionException, CharStreamException, TokenStreamException {
/* 1380 */     Token _token = null; int _begin = this.text.length();
/* 1381 */     int _ttype = 18;
/*      */ 
/* 1384 */     switch (LA(1)) { case '0':
/*      */     case '1':
/*      */     case '2':
/*      */     case '3':
/*      */     case '4':
/*      */     case '5':
/*      */     case '6':
/*      */     case '7':
/*      */     case '8':
/*      */     case '9':
/* 1389 */       matchRange('0', '9');
/* 1390 */       break;
/*      */     case 'A':
/*      */     case 'B':
/*      */     case 'C':
/*      */     case 'D':
/*      */     case 'E':
/*      */     case 'F':
/* 1395 */       matchRange('A', 'F');
/* 1396 */       break;
/*      */     case 'a':
/*      */     case 'b':
/*      */     case 'c':
/*      */     case 'd':
/*      */     case 'e':
/*      */     case 'f':
/* 1401 */       matchRange('a', 'f');
/* 1402 */       break;
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
/* 1406 */       throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */     }
/*      */ 
/* 1409 */     if ((_createToken) && (_token == null) && (_ttype != -1)) {
/* 1410 */       _token = makeToken(_ttype);
/* 1411 */       _token.setText(new String(this.text.getBuffer(), _begin, this.text.length() - _begin));
/*      */     }
/* 1413 */     this._returnToken = _token;
/*      */   }
/*      */ 
/*      */   private static final long[] mk_tokenSet_0()
/*      */   {
/* 1418 */     long[] data = new long[2048];
/* 1419 */     data[0] = -68719485954L;
/* 1420 */     for (int i = 1; i <= 1022; i++) data[i] = -1L;
/* 1421 */     data[1023] = 9223372036854775807L;
/* 1422 */     return data;
/*      */   }
/*      */ 
/*      */   private static final long[] mk_tokenSet_1() {
/* 1426 */     long[] data = new long[2048];
/* 1427 */     data[0] = -68719476738L;
/* 1428 */     for (int i = 1; i <= 1022; i++) data[i] = -1L;
/* 1429 */     data[1023] = 9223372036854775807L;
/* 1430 */     return data;
/*      */   }
/*      */ 
/*      */   private static final long[] mk_tokenSet_2() {
/* 1434 */     long[] data = new long[1025];
/* 1435 */     data[0] = 4294977024L;
/* 1436 */     return data;
/*      */   }
/*      */ 
/*      */   private static final long[] mk_tokenSet_3() {
/* 1440 */     long[] data = new long[1025];
/* 1441 */     data[0] = 4294967296L;
/* 1442 */     data[1] = 14707067533131776L;
/* 1443 */     return data;
/*      */   }
/*      */ 
/*      */   private static final long[] mk_tokenSet_4() {
/* 1447 */     long[] data = new long[1025];
/* 1448 */     data[0] = 287948969894477824L;
/* 1449 */     data[1] = 541434314878L;
/* 1450 */     return data;
/*      */   }
/*      */ 
/*      */   private static final long[] mk_tokenSet_5() {
/* 1454 */     long[] data = new long[2048];
/* 1455 */     data[0] = -2199023255554L;
/* 1456 */     for (int i = 1; i <= 1022; i++) data[i] = -1L;
/* 1457 */     data[1023] = 9223372036854775807L;
/* 1458 */     return data;
/*      */   }
/*      */ 
/*      */   private static final long[] mk_tokenSet_6() {
/* 1462 */     long[] data = new long[2048];
/* 1463 */     data[0] = -1168231104514L;
/* 1464 */     for (int i = 1; i <= 1022; i++) data[i] = -1L;
/* 1465 */     data[1023] = 9223372036854775807L;
/* 1466 */     return data;
/*      */   }
/*      */ 
/*      */   private static final long[] mk_tokenSet_7() {
/* 1470 */     long[] data = new long[2048];
/* 1471 */     data[0] = -3298534892546L;
/* 1472 */     data[1] = -576460752571858945L;
/* 1473 */     for (int i = 2; i <= 1022; i++) data[i] = -1L;
/* 1474 */     data[1023] = 9223372036854775807L;
/* 1475 */     return data;
/*      */   }
/*      */ 
/*      */   private static final long[] mk_tokenSet_8() {
/* 1479 */     long[] data = new long[2048];
/* 1480 */     data[0] = -1152921521786716162L;
/* 1481 */     data[1] = -576460752303423489L;
/* 1482 */     for (int i = 2; i <= 1022; i++) data[i] = -1L;
/* 1483 */     data[1023] = 9223372036854775807L;
/* 1484 */     return data;
/*      */   }
/*      */ 
/*      */   private static final long[] mk_tokenSet_9() {
/* 1488 */     long[] data = new long[2048];
/* 1489 */     data[0] = -2305851874026202114L;
/* 1490 */     data[1] = -576460752571858945L;
/* 1491 */     for (int i = 2; i <= 1022; i++) data[i] = -1L;
/* 1492 */     data[1023] = 9223372036854775807L;
/* 1493 */     return data;
/*      */   }
/*      */ 
/*      */   private static final long[] mk_tokenSet_10() {
/* 1497 */     long[] data = new long[2048];
/* 1498 */     data[0] = -2L;
/* 1499 */     data[1] = -2882303761785552897L;
/* 1500 */     for (int i = 2; i <= 1022; i++) data[i] = -1L;
/* 1501 */     data[1023] = 9223372036854775807L;
/* 1502 */     return data;
/*      */   }
/*      */ 
/*      */   private static final long[] mk_tokenSet_11() {
/* 1506 */     long[] data = new long[2048];
/* 1507 */     data[0] = -17179869186L;
/* 1508 */     data[1] = -268435457L;
/* 1509 */     for (int i = 2; i <= 1022; i++) data[i] = -1L;
/* 1510 */     data[1023] = 9223372036854775807L;
/* 1511 */     return data;
/*      */   }
/*      */ 
/*      */   private static final long[] mk_tokenSet_12() {
/* 1515 */     long[] data = new long[2048];
/* 1516 */     data[0] = -3298534883330L;
/* 1517 */     data[1] = -268435457L;
/* 1518 */     for (int i = 2; i <= 1022; i++) data[i] = -1L;
/* 1519 */     data[1023] = 9223372036854775807L;
/* 1520 */     return data;
/*      */   }
/*      */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.antlr.stringtemplate.language.DefaultTemplateLexer
 * JD-Core Version:    0.6.2
 */