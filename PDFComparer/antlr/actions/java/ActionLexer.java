/*      */ package antlr.actions.java;
/*      */ 
/*      */ import antlr.ANTLRStringBuffer;
/*      */ import antlr.ActionTransInfo;
/*      */ import antlr.ByteBuffer;
/*      */ import antlr.CharBuffer;
/*      */ import antlr.CharScanner;
/*      */ import antlr.CharStreamException;
/*      */ import antlr.CharStreamIOException;
/*      */ import antlr.CodeGenerator;
/*      */ import antlr.InputBuffer;
/*      */ import antlr.LexerSharedInputState;
/*      */ import antlr.NoViableAltForCharException;
/*      */ import antlr.RecognitionException;
/*      */ import antlr.RuleBlock;
/*      */ import antlr.Token;
/*      */ import antlr.TokenStream;
/*      */ import antlr.TokenStreamException;
/*      */ import antlr.TokenStreamIOException;
/*      */ import antlr.TokenStreamRecognitionException;
/*      */ import antlr.Tool;
/*      */ import antlr.collections.impl.BitSet;
/*      */ import antlr.collections.impl.Vector;
/*      */ import java.io.InputStream;
/*      */ import java.io.Reader;
/*      */ import java.io.StringReader;
/*      */ import java.util.Hashtable;
/*      */ 
/*      */ public class ActionLexer extends CharScanner
/*      */   implements ActionLexerTokenTypes, TokenStream
/*      */ {
/*      */   protected RuleBlock currentRule;
/*      */   protected CodeGenerator generator;
/*   59 */   protected int lineOffset = 0;
/*      */   private Tool antlrTool;
/*      */   ActionTransInfo transInfo;
/* 2333 */   public static final BitSet _tokenSet_0 = new BitSet(mk_tokenSet_0());
/*      */ 
/* 2340 */   public static final BitSet _tokenSet_1 = new BitSet(mk_tokenSet_1());
/*      */ 
/* 2347 */   public static final BitSet _tokenSet_2 = new BitSet(mk_tokenSet_2());
/*      */ 
/* 2352 */   public static final BitSet _tokenSet_3 = new BitSet(mk_tokenSet_3());
/*      */ 
/* 2357 */   public static final BitSet _tokenSet_4 = new BitSet(mk_tokenSet_4());
/*      */ 
/* 2362 */   public static final BitSet _tokenSet_5 = new BitSet(mk_tokenSet_5());
/*      */ 
/* 2367 */   public static final BitSet _tokenSet_6 = new BitSet(mk_tokenSet_6());
/*      */ 
/* 2375 */   public static final BitSet _tokenSet_7 = new BitSet(mk_tokenSet_7());
/*      */ 
/* 2383 */   public static final BitSet _tokenSet_8 = new BitSet(mk_tokenSet_8());
/*      */ 
/* 2388 */   public static final BitSet _tokenSet_9 = new BitSet(mk_tokenSet_9());
/*      */ 
/* 2393 */   public static final BitSet _tokenSet_10 = new BitSet(mk_tokenSet_10());
/*      */ 
/* 2398 */   public static final BitSet _tokenSet_11 = new BitSet(mk_tokenSet_11());
/*      */ 
/* 2403 */   public static final BitSet _tokenSet_12 = new BitSet(mk_tokenSet_12());
/*      */ 
/* 2408 */   public static final BitSet _tokenSet_13 = new BitSet(mk_tokenSet_13());
/*      */ 
/* 2413 */   public static final BitSet _tokenSet_14 = new BitSet(mk_tokenSet_14());
/*      */ 
/* 2418 */   public static final BitSet _tokenSet_15 = new BitSet(mk_tokenSet_15());
/*      */ 
/* 2423 */   public static final BitSet _tokenSet_16 = new BitSet(mk_tokenSet_16());
/*      */ 
/* 2428 */   public static final BitSet _tokenSet_17 = new BitSet(mk_tokenSet_17());
/*      */ 
/* 2433 */   public static final BitSet _tokenSet_18 = new BitSet(mk_tokenSet_18());
/*      */ 
/* 2438 */   public static final BitSet _tokenSet_19 = new BitSet(mk_tokenSet_19());
/*      */ 
/* 2443 */   public static final BitSet _tokenSet_20 = new BitSet(mk_tokenSet_20());
/*      */ 
/* 2448 */   public static final BitSet _tokenSet_21 = new BitSet(mk_tokenSet_21());
/*      */ 
/* 2453 */   public static final BitSet _tokenSet_22 = new BitSet(mk_tokenSet_22());
/*      */ 
/* 2458 */   public static final BitSet _tokenSet_23 = new BitSet(mk_tokenSet_23());
/*      */ 
/* 2463 */   public static final BitSet _tokenSet_24 = new BitSet(mk_tokenSet_24());
/*      */ 
/* 2468 */   public static final BitSet _tokenSet_25 = new BitSet(mk_tokenSet_25());
/*      */ 
/*      */   public ActionLexer(String paramString, RuleBlock paramRuleBlock, CodeGenerator paramCodeGenerator, ActionTransInfo paramActionTransInfo)
/*      */   {
/*   67 */     this(new StringReader(paramString));
/*   68 */     this.currentRule = paramRuleBlock;
/*   69 */     this.generator = paramCodeGenerator;
/*   70 */     this.transInfo = paramActionTransInfo;
/*      */   }
/*      */ 
/*      */   public void setLineOffset(int paramInt)
/*      */   {
/*   75 */     setLine(paramInt);
/*      */   }
/*      */ 
/*      */   public void setTool(Tool paramTool) {
/*   79 */     this.antlrTool = paramTool;
/*      */   }
/*      */ 
/*      */   public void reportError(RecognitionException paramRecognitionException)
/*      */   {
/*   84 */     this.antlrTool.error("Syntax error in action: " + paramRecognitionException, getFilename(), getLine(), getColumn());
/*      */   }
/*      */ 
/*      */   public void reportError(String paramString)
/*      */   {
/*   89 */     this.antlrTool.error(paramString, getFilename(), getLine(), getColumn());
/*      */   }
/*      */ 
/*      */   public void reportWarning(String paramString)
/*      */   {
/*   94 */     if (getFilename() == null) {
/*   95 */       this.antlrTool.warning(paramString);
/*      */     }
/*      */     else
/*   98 */       this.antlrTool.warning(paramString, getFilename(), getLine(), getColumn());
/*      */   }
/*      */ 
/*      */   public ActionLexer(InputStream paramInputStream) {
/*  102 */     this(new ByteBuffer(paramInputStream));
/*      */   }
/*      */   public ActionLexer(Reader paramReader) {
/*  105 */     this(new CharBuffer(paramReader));
/*      */   }
/*      */   public ActionLexer(InputBuffer paramInputBuffer) {
/*  108 */     this(new LexerSharedInputState(paramInputBuffer));
/*      */   }
/*      */   public ActionLexer(LexerSharedInputState paramLexerSharedInputState) {
/*  111 */     super(paramLexerSharedInputState);
/*  112 */     this.caseSensitiveLiterals = true;
/*  113 */     setCaseSensitive(true);
/*  114 */     this.literals = new Hashtable();
/*      */   }
/*      */ 
/*      */   public Token nextToken() throws TokenStreamException {
/*  118 */     Token localToken = null;
/*      */     while (true)
/*      */     {
/*  121 */       Object localObject = null;
/*  122 */       int i = 0;
/*  123 */       resetText();
/*      */       try
/*      */       {
/*  126 */         if ((LA(1) >= '\003') && (LA(1) <= 'ÿ')) {
/*  127 */           mACTION(true);
/*  128 */           localToken = this._returnToken;
/*      */         }
/*  131 */         else if (LA(1) == 65535) { uponEOF(); this._returnToken = makeToken(1); } else {
/*  132 */           throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */         }
/*      */ 
/*  135 */         if (this._returnToken == null) continue;
/*  136 */         i = this._returnToken.getType();
/*  137 */         this._returnToken.setType(i);
/*  138 */         return this._returnToken;
/*      */       }
/*      */       catch (RecognitionException localRecognitionException) {
/*  141 */         throw new TokenStreamRecognitionException(localRecognitionException);
/*      */       }
/*      */       catch (CharStreamException localCharStreamException)
/*      */       {
/*  145 */         if ((localCharStreamException instanceof CharStreamIOException)) {
/*  146 */           throw new TokenStreamIOException(((CharStreamIOException)localCharStreamException).io);
/*      */         }
/*      */ 
/*  149 */         throw new TokenStreamException(localCharStreamException.getMessage());
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   public final void mACTION(boolean paramBoolean) throws RecognitionException, CharStreamException, TokenStreamException
/*      */   {
/*  156 */     Token localToken = null; int j = this.text.length();
/*  157 */     int i = 4;
/*      */ 
/*  161 */     int k = 0;
/*      */     while (true)
/*      */     {
/*  164 */       switch (LA(1))
/*      */       {
/*      */       case '#':
/*  167 */         mAST_ITEM(false);
/*  168 */         break;
/*      */       case '$':
/*  172 */         mTEXT_ITEM(false);
/*  173 */         break;
/*      */       default:
/*  176 */         if (_tokenSet_0.member(LA(1))) {
/*  177 */           mSTUFF(false);
/*      */         }
/*      */         else {
/*  180 */           if (k >= 1) break label126; throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */         }break;
/*      */       }
/*  183 */       k++;
/*      */     }
/*      */ 
/*  186 */     label126: if ((paramBoolean) && (localToken == null) && (i != -1)) {
/*  187 */       localToken = makeToken(i);
/*  188 */       localToken.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/*      */     }
/*  190 */     this._returnToken = localToken;
/*      */   }
/*      */ 
/*      */   protected final void mSTUFF(boolean paramBoolean) throws RecognitionException, CharStreamException, TokenStreamException {
/*  194 */     Token localToken = null; int j = this.text.length();
/*  195 */     int i = 5;
/*      */ 
/*  198 */     switch (LA(1))
/*      */     {
/*      */     case '"':
/*  201 */       mSTRING(false);
/*  202 */       break;
/*      */     case '\'':
/*  206 */       mCHAR(false);
/*  207 */       break;
/*      */     case '\n':
/*  211 */       match('\n');
/*  212 */       newline();
/*  213 */       break;
/*      */     default:
/*  216 */       if ((LA(1) == '/') && ((LA(2) == '*') || (LA(2) == '/'))) {
/*  217 */         mCOMMENT(false);
/*      */       }
/*  219 */       else if ((LA(1) == '\r') && (LA(2) == '\n')) {
/*  220 */         match("\r\n");
/*  221 */         newline();
/*      */       }
/*  223 */       else if ((LA(1) == '/') && (_tokenSet_1.member(LA(2)))) {
/*  224 */         match('/');
/*      */ 
/*  226 */         match(_tokenSet_1);
/*      */       }
/*  229 */       else if (LA(1) == '\r') {
/*  230 */         match('\r');
/*  231 */         newline();
/*      */       }
/*  233 */       else if (_tokenSet_2.member(LA(1)))
/*      */       {
/*  235 */         match(_tokenSet_2);
/*      */       }
/*      */       else
/*      */       {
/*  239 */         throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */       }break;
/*      */     }
/*  242 */     if ((paramBoolean) && (localToken == null) && (i != -1)) {
/*  243 */       localToken = makeToken(i);
/*  244 */       localToken.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/*      */     }
/*  246 */     this._returnToken = localToken;
/*      */   }
/*      */ 
/*      */   protected final void mAST_ITEM(boolean paramBoolean) throws RecognitionException, CharStreamException, TokenStreamException {
/*  250 */     Token localToken1 = null; int j = this.text.length();
/*  251 */     int i = 6;
/*      */ 
/*  253 */     Token localToken2 = null;
/*  254 */     Token localToken3 = null;
/*  255 */     Token localToken4 = null;
/*      */     int k;
/*  257 */     if ((LA(1) == '#') && (LA(2) == '(')) {
/*  258 */       k = this.text.length();
/*  259 */       match('#');
/*  260 */       this.text.setLength(k);
/*  261 */       mTREE(true);
/*  262 */       localToken2 = this._returnToken;
/*      */     }
/*      */     else
/*      */     {
/*      */       String str1;
/*  264 */       if ((LA(1) == '#') && (_tokenSet_3.member(LA(2)))) {
/*  265 */         k = this.text.length();
/*  266 */         match('#');
/*  267 */         this.text.setLength(k);
/*  268 */         mID(true);
/*  269 */         localToken3 = this._returnToken;
/*      */ 
/*  271 */         str1 = localToken3.getText();
/*  272 */         String str2 = this.generator.mapTreeId(str1, this.transInfo);
/*  273 */         if (str2 != null) {
/*  274 */           this.text.setLength(j); this.text.append(str2);
/*      */         }
/*      */ 
/*  278 */         if (_tokenSet_4.member(LA(1))) {
/*  279 */           mWS(false);
/*      */         }
/*      */ 
/*  286 */         if (LA(1) == '=') {
/*  287 */           mVAR_ASSIGN(false);
/*      */         }
/*      */ 
/*      */       }
/*  294 */       else if ((LA(1) == '#') && (LA(2) == '[')) {
/*  295 */         k = this.text.length();
/*  296 */         match('#');
/*  297 */         this.text.setLength(k);
/*  298 */         mAST_CONSTRUCTOR(true);
/*  299 */         localToken4 = this._returnToken;
/*      */       }
/*  301 */       else if ((LA(1) == '#') && (LA(2) == '#')) {
/*  302 */         match("##");
/*      */ 
/*  304 */         str1 = this.currentRule.getRuleName() + "_AST"; this.text.setLength(j); this.text.append(str1);
/*  305 */         if (this.transInfo != null) {
/*  306 */           this.transInfo.refRuleRoot = str1;
/*      */         }
/*      */ 
/*  310 */         if (_tokenSet_4.member(LA(1))) {
/*  311 */           mWS(false);
/*      */         }
/*      */ 
/*  318 */         if (LA(1) == '=') {
/*  319 */           mVAR_ASSIGN(false);
/*      */         }
/*      */ 
/*      */       }
/*      */       else
/*      */       {
/*  327 */         throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */       }
/*      */     }
/*  330 */     if ((paramBoolean) && (localToken1 == null) && (i != -1)) {
/*  331 */       localToken1 = makeToken(i);
/*  332 */       localToken1.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/*      */     }
/*  334 */     this._returnToken = localToken1;
/*      */   }
/*      */ 
/*      */   protected final void mTEXT_ITEM(boolean paramBoolean) throws RecognitionException, CharStreamException, TokenStreamException {
/*  338 */     Token localToken1 = null; int j = this.text.length();
/*  339 */     int i = 7;
/*      */ 
/*  341 */     Token localToken2 = null;
/*  342 */     Token localToken3 = null;
/*  343 */     Token localToken4 = null;
/*  344 */     Token localToken5 = null;
/*  345 */     Token localToken6 = null;
/*  346 */     Token localToken7 = null;
/*      */     String str1;
/*      */     String str2;
/*  348 */     if ((LA(1) == '$') && (LA(2) == 'F') && (LA(3) == 'O')) {
/*  349 */       match("$FOLLOW");
/*      */ 
/*  351 */       if ((_tokenSet_5.member(LA(1))) && (_tokenSet_6.member(LA(2))) && (LA(3) >= '\003') && (LA(3) <= 'ÿ'))
/*      */       {
/*  353 */         switch (LA(1)) { case '\t':
/*      */         case '\n':
/*      */         case '\r':
/*      */         case ' ':
/*  356 */           mWS(false);
/*  357 */           break;
/*      */         case '(':
/*  361 */           break;
/*      */         default:
/*  365 */           throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */         }
/*      */ 
/*  369 */         match('(');
/*  370 */         mTEXT_ARG(true);
/*  371 */         localToken6 = this._returnToken;
/*  372 */         match(')');
/*      */       }
/*      */ 
/*  379 */       str1 = this.currentRule.getRuleName();
/*  380 */       if (localToken6 != null) {
/*  381 */         str1 = localToken6.getText();
/*      */       }
/*  383 */       str2 = this.generator.getFOLLOWBitSet(str1, 1);
/*      */ 
/*  385 */       if (str2 == null) {
/*  386 */         reportError("$FOLLOW(" + str1 + ")" + ": unknown rule or bad lookahead computation");
/*      */       }
/*      */       else
/*      */       {
/*  390 */         this.text.setLength(j); this.text.append(str2);
/*      */       }
/*      */ 
/*      */     }
/*  394 */     else if ((LA(1) == '$') && (LA(2) == 'F') && (LA(3) == 'I')) {
/*  395 */       match("$FIRST");
/*      */ 
/*  397 */       if ((_tokenSet_5.member(LA(1))) && (_tokenSet_6.member(LA(2))) && (LA(3) >= '\003') && (LA(3) <= 'ÿ'))
/*      */       {
/*  399 */         switch (LA(1)) { case '\t':
/*      */         case '\n':
/*      */         case '\r':
/*      */         case ' ':
/*  402 */           mWS(false);
/*  403 */           break;
/*      */         case '(':
/*  407 */           break;
/*      */         default:
/*  411 */           throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */         }
/*      */ 
/*  415 */         match('(');
/*  416 */         mTEXT_ARG(true);
/*  417 */         localToken7 = this._returnToken;
/*  418 */         match(')');
/*      */       }
/*      */ 
/*  425 */       str1 = this.currentRule.getRuleName();
/*  426 */       if (localToken7 != null) {
/*  427 */         str1 = localToken7.getText();
/*      */       }
/*  429 */       str2 = this.generator.getFIRSTBitSet(str1, 1);
/*      */ 
/*  431 */       if (str2 == null) {
/*  432 */         reportError("$FIRST(" + str1 + ")" + ": unknown rule or bad lookahead computation");
/*      */       }
/*      */       else
/*      */       {
/*  436 */         this.text.setLength(j); this.text.append(str2);
/*      */       }
/*      */ 
/*      */     }
/*  440 */     else if ((LA(1) == '$') && (LA(2) == 'a')) {
/*  441 */       match("$append");
/*      */ 
/*  443 */       switch (LA(1)) { case '\t':
/*      */       case '\n':
/*      */       case '\r':
/*      */       case ' ':
/*  446 */         mWS(false);
/*  447 */         break;
/*      */       case '(':
/*  451 */         break;
/*      */       default:
/*  455 */         throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */       }
/*      */ 
/*  459 */       match('(');
/*  460 */       mTEXT_ARG(true);
/*  461 */       localToken2 = this._returnToken;
/*  462 */       match(')');
/*      */ 
/*  464 */       str1 = "text.append(" + localToken2.getText() + ")";
/*  465 */       this.text.setLength(j); this.text.append(str1);
/*      */     }
/*  468 */     else if ((LA(1) == '$') && (LA(2) == 's')) {
/*  469 */       match("$set");
/*      */ 
/*  471 */       if ((LA(1) == 'T') && (LA(2) == 'e')) {
/*  472 */         match("Text");
/*      */ 
/*  474 */         switch (LA(1)) { case '\t':
/*      */         case '\n':
/*      */         case '\r':
/*      */         case ' ':
/*  477 */           mWS(false);
/*  478 */           break;
/*      */         case '(':
/*  482 */           break;
/*      */         default:
/*  486 */           throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */         }
/*      */ 
/*  490 */         match('(');
/*  491 */         mTEXT_ARG(true);
/*  492 */         localToken3 = this._returnToken;
/*  493 */         match(')');
/*      */ 
/*  496 */         str1 = "text.setLength(_begin); text.append(" + localToken3.getText() + ")";
/*  497 */         this.text.setLength(j); this.text.append(str1);
/*      */       }
/*  500 */       else if ((LA(1) == 'T') && (LA(2) == 'o')) {
/*  501 */         match("Token");
/*      */ 
/*  503 */         switch (LA(1)) { case '\t':
/*      */         case '\n':
/*      */         case '\r':
/*      */         case ' ':
/*  506 */           mWS(false);
/*  507 */           break;
/*      */         case '(':
/*  511 */           break;
/*      */         default:
/*  515 */           throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */         }
/*      */ 
/*  519 */         match('(');
/*  520 */         mTEXT_ARG(true);
/*  521 */         localToken4 = this._returnToken;
/*  522 */         match(')');
/*      */ 
/*  524 */         str1 = "_token = " + localToken4.getText();
/*  525 */         this.text.setLength(j); this.text.append(str1);
/*      */       }
/*  528 */       else if ((LA(1) == 'T') && (LA(2) == 'y')) {
/*  529 */         match("Type");
/*      */ 
/*  531 */         switch (LA(1)) { case '\t':
/*      */         case '\n':
/*      */         case '\r':
/*      */         case ' ':
/*  534 */           mWS(false);
/*  535 */           break;
/*      */         case '(':
/*  539 */           break;
/*      */         default:
/*  543 */           throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */         }
/*      */ 
/*  547 */         match('(');
/*  548 */         mTEXT_ARG(true);
/*  549 */         localToken5 = this._returnToken;
/*  550 */         match(')');
/*      */ 
/*  552 */         str1 = "_ttype = " + localToken5.getText();
/*  553 */         this.text.setLength(j); this.text.append(str1);
/*      */       }
/*      */       else
/*      */       {
/*  557 */         throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */       }
/*      */ 
/*      */     }
/*  562 */     else if ((LA(1) == '$') && (LA(2) == 'g')) {
/*  563 */       match("$getText");
/*      */ 
/*  565 */       this.text.setLength(j); this.text.append("new String(text.getBuffer(),_begin,text.length()-_begin)");
/*      */     }
/*      */     else
/*      */     {
/*  569 */       throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */     }
/*      */ 
/*  572 */     if ((paramBoolean) && (localToken1 == null) && (i != -1)) {
/*  573 */       localToken1 = makeToken(i);
/*  574 */       localToken1.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/*      */     }
/*  576 */     this._returnToken = localToken1;
/*      */   }
/*      */ 
/*      */   protected final void mCOMMENT(boolean paramBoolean) throws RecognitionException, CharStreamException, TokenStreamException {
/*  580 */     Token localToken = null; int j = this.text.length();
/*  581 */     int i = 19;
/*      */ 
/*  584 */     if ((LA(1) == '/') && (LA(2) == '/')) {
/*  585 */       mSL_COMMENT(false);
/*      */     }
/*  587 */     else if ((LA(1) == '/') && (LA(2) == '*')) {
/*  588 */       mML_COMMENT(false);
/*      */     }
/*      */     else {
/*  591 */       throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */     }
/*      */ 
/*  594 */     if ((paramBoolean) && (localToken == null) && (i != -1)) {
/*  595 */       localToken = makeToken(i);
/*  596 */       localToken.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/*      */     }
/*  598 */     this._returnToken = localToken;
/*      */   }
/*      */ 
/*      */   protected final void mSTRING(boolean paramBoolean) throws RecognitionException, CharStreamException, TokenStreamException {
/*  602 */     Token localToken = null; int j = this.text.length();
/*  603 */     int i = 23;
/*      */ 
/*  606 */     match('"');
/*      */     while (true)
/*      */     {
/*  610 */       if (LA(1) == '\\') {
/*  611 */         mESC(false);
/*      */       } else {
/*  613 */         if (!_tokenSet_7.member(LA(1))) break;
/*  614 */         matchNot('"');
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/*  622 */     match('"');
/*  623 */     if ((paramBoolean) && (localToken == null) && (i != -1)) {
/*  624 */       localToken = makeToken(i);
/*  625 */       localToken.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/*      */     }
/*  627 */     this._returnToken = localToken;
/*      */   }
/*      */ 
/*      */   protected final void mCHAR(boolean paramBoolean) throws RecognitionException, CharStreamException, TokenStreamException {
/*  631 */     Token localToken = null; int j = this.text.length();
/*  632 */     int i = 22;
/*      */ 
/*  635 */     match('\'');
/*      */ 
/*  637 */     if (LA(1) == '\\') {
/*  638 */       mESC(false);
/*      */     }
/*  640 */     else if (_tokenSet_8.member(LA(1))) {
/*  641 */       matchNot('\'');
/*      */     }
/*      */     else {
/*  644 */       throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */     }
/*      */ 
/*  648 */     match('\'');
/*  649 */     if ((paramBoolean) && (localToken == null) && (i != -1)) {
/*  650 */       localToken = makeToken(i);
/*  651 */       localToken.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/*      */     }
/*  653 */     this._returnToken = localToken;
/*      */   }
/*      */ 
/*      */   protected final void mTREE(boolean paramBoolean) throws RecognitionException, CharStreamException, TokenStreamException {
/*  657 */     Token localToken1 = null; int j = this.text.length();
/*  658 */     int i = 8;
/*      */ 
/*  660 */     Token localToken2 = null;
/*  661 */     Token localToken3 = null;
/*      */ 
/*  663 */     StringBuffer localStringBuffer = new StringBuffer();
/*  664 */     int m = 0;
/*  665 */     Vector localVector = new Vector(10);
/*      */ 
/*  668 */     int k = this.text.length();
/*  669 */     match('(');
/*  670 */     this.text.setLength(k);
/*      */ 
/*  672 */     switch (LA(1)) { case '\t':
/*      */     case '\n':
/*      */     case '\r':
/*      */     case ' ':
/*  675 */       k = this.text.length();
/*  676 */       mWS(false);
/*  677 */       this.text.setLength(k);
/*  678 */       break;
/*      */     case '"':
/*      */     case '#':
/*      */     case '(':
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
/*      */     case '[':
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
/*  696 */       break;
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
/*      */     case '$':
/*      */     case '%':
/*      */     case '&':
/*      */     case '\'':
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
/*      */     case '\\':
/*      */     case ']':
/*      */     case '^':
/*      */     case '`':
/*      */     default:
/*  700 */       throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */     }
/*      */ 
/*  704 */     k = this.text.length();
/*  705 */     mTREE_ELEMENT(true);
/*  706 */     this.text.setLength(k);
/*  707 */     localToken2 = this._returnToken;
/*  708 */     localVector.appendElement(localToken2.getText());
/*      */ 
/*  710 */     switch (LA(1)) { case '\t':
/*      */     case '\n':
/*      */     case '\r':
/*      */     case ' ':
/*  713 */       k = this.text.length();
/*  714 */       mWS(false);
/*  715 */       this.text.setLength(k);
/*  716 */       break;
/*      */     case ')':
/*      */     case ',':
/*  720 */       break;
/*      */     default:
/*  724 */       throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */     }
/*      */ 
/*  731 */     while (LA(1) == ',') {
/*  732 */       k = this.text.length();
/*  733 */       match(',');
/*  734 */       this.text.setLength(k);
/*      */ 
/*  736 */       switch (LA(1)) { case '\t':
/*      */       case '\n':
/*      */       case '\r':
/*      */       case ' ':
/*  739 */         k = this.text.length();
/*  740 */         mWS(false);
/*  741 */         this.text.setLength(k);
/*  742 */         break;
/*      */       case '"':
/*      */       case '#':
/*      */       case '(':
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
/*      */       case '[':
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
/*  760 */         break;
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
/*      */       case '$':
/*      */       case '%':
/*      */       case '&':
/*      */       case '\'':
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
/*      */       case '\\':
/*      */       case ']':
/*      */       case '^':
/*      */       case '`':
/*      */       default:
/*  764 */         throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */       }
/*      */ 
/*  768 */       k = this.text.length();
/*  769 */       mTREE_ELEMENT(true);
/*  770 */       this.text.setLength(k);
/*  771 */       localToken3 = this._returnToken;
/*  772 */       localVector.appendElement(localToken3.getText());
/*      */ 
/*  774 */       switch (LA(1)) { case '\t':
/*      */       case '\n':
/*      */       case '\r':
/*      */       case ' ':
/*  777 */         k = this.text.length();
/*  778 */         mWS(false);
/*  779 */         this.text.setLength(k);
/*  780 */         break;
/*      */       case ')':
/*      */       case ',':
/*  784 */         break;
/*      */       default:
/*  788 */         throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/*  799 */     this.text.setLength(j); this.text.append(this.generator.getASTCreateString(localVector));
/*  800 */     k = this.text.length();
/*  801 */     match(')');
/*  802 */     this.text.setLength(k);
/*  803 */     if ((paramBoolean) && (localToken1 == null) && (i != -1)) {
/*  804 */       localToken1 = makeToken(i);
/*  805 */       localToken1.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/*      */     }
/*  807 */     this._returnToken = localToken1;
/*      */   }
/*      */ 
/*      */   protected final void mID(boolean paramBoolean) throws RecognitionException, CharStreamException, TokenStreamException {
/*  811 */     Token localToken = null; int j = this.text.length();
/*  812 */     int i = 17;
/*      */ 
/*  816 */     switch (LA(1)) { case 'a':
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
/*  825 */       matchRange('a', 'z');
/*  826 */       break;
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
/*  836 */       matchRange('A', 'Z');
/*  837 */       break;
/*      */     case '_':
/*  841 */       match('_');
/*  842 */       break;
/*      */     case '[':
/*      */     case '\\':
/*      */     case ']':
/*      */     case '^':
/*      */     case '`':
/*      */     default:
/*  846 */       throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */     }
/*      */ 
/*  853 */     while (_tokenSet_9.member(LA(1)))
/*      */     {
/*  855 */       switch (LA(1)) { case 'a':
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
/*  864 */         matchRange('a', 'z');
/*  865 */         break;
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
/*  875 */         matchRange('A', 'Z');
/*  876 */         break;
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
/*  882 */         matchRange('0', '9');
/*  883 */         break;
/*      */       case '_':
/*  887 */         match('_');
/*  888 */         break;
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
/*  892 */         throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/*  903 */     if ((paramBoolean) && (localToken == null) && (i != -1)) {
/*  904 */       localToken = makeToken(i);
/*  905 */       localToken.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/*      */     }
/*  907 */     this._returnToken = localToken;
/*      */   }
/*      */ 
/*      */   protected final void mWS(boolean paramBoolean) throws RecognitionException, CharStreamException, TokenStreamException {
/*  911 */     Token localToken = null; int j = this.text.length();
/*  912 */     int i = 28;
/*      */ 
/*  916 */     int k = 0;
/*      */     while (true)
/*      */     {
/*  919 */       if ((LA(1) == '\r') && (LA(2) == '\n')) {
/*  920 */         match('\r');
/*  921 */         match('\n');
/*  922 */         newline();
/*      */       }
/*  924 */       else if (LA(1) == ' ') {
/*  925 */         match(' ');
/*      */       }
/*  927 */       else if (LA(1) == '\t') {
/*  928 */         match('\t');
/*      */       }
/*  930 */       else if (LA(1) == '\r') {
/*  931 */         match('\r');
/*  932 */         newline();
/*      */       }
/*  934 */       else if (LA(1) == '\n') {
/*  935 */         match('\n');
/*  936 */         newline();
/*      */       }
/*      */       else {
/*  939 */         if (k >= 1) break; throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */       }
/*      */ 
/*  942 */       k++;
/*      */     }
/*      */ 
/*  945 */     if ((paramBoolean) && (localToken == null) && (i != -1)) {
/*  946 */       localToken = makeToken(i);
/*  947 */       localToken.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/*      */     }
/*  949 */     this._returnToken = localToken;
/*      */   }
/*      */ 
/*      */   protected final void mVAR_ASSIGN(boolean paramBoolean) throws RecognitionException, CharStreamException, TokenStreamException {
/*  953 */     Token localToken = null; int j = this.text.length();
/*  954 */     int i = 18;
/*      */ 
/*  957 */     match('=');
/*      */ 
/*  961 */     if ((LA(1) != '=') && (this.transInfo != null) && (this.transInfo.refRuleRoot != null)) {
/*  962 */       this.transInfo.assignToRoot = true;
/*      */     }
/*      */ 
/*  965 */     if ((paramBoolean) && (localToken == null) && (i != -1)) {
/*  966 */       localToken = makeToken(i);
/*  967 */       localToken.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/*      */     }
/*  969 */     this._returnToken = localToken;
/*      */   }
/*      */ 
/*      */   protected final void mAST_CONSTRUCTOR(boolean paramBoolean) throws RecognitionException, CharStreamException, TokenStreamException {
/*  973 */     Token localToken1 = null; int j = this.text.length();
/*  974 */     int i = 10;
/*      */ 
/*  976 */     Token localToken2 = null;
/*  977 */     Token localToken3 = null;
/*  978 */     Token localToken4 = null;
/*      */ 
/*  980 */     int k = this.text.length();
/*  981 */     match('[');
/*  982 */     this.text.setLength(k);
/*      */ 
/*  984 */     switch (LA(1)) { case '\t':
/*      */     case '\n':
/*      */     case '\r':
/*      */     case ' ':
/*  987 */       k = this.text.length();
/*  988 */       mWS(false);
/*  989 */       this.text.setLength(k);
/*  990 */       break;
/*      */     case '"':
/*      */     case '#':
/*      */     case '(':
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
/*      */     case '[':
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
/* 1010 */       break;
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
/*      */     case '$':
/*      */     case '%':
/*      */     case '&':
/*      */     case '\'':
/*      */     case ')':
/*      */     case '*':
/*      */     case '+':
/*      */     case ',':
/*      */     case '-':
/*      */     case '.':
/*      */     case '/':
/*      */     case ':':
/*      */     case ';':
/*      */     case '<':
/*      */     case '=':
/*      */     case '>':
/*      */     case '?':
/*      */     case '@':
/*      */     case '\\':
/*      */     case ']':
/*      */     case '^':
/*      */     case '`':
/*      */     default:
/* 1014 */       throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */     }
/*      */ 
/* 1018 */     k = this.text.length();
/* 1019 */     mAST_CTOR_ELEMENT(true);
/* 1020 */     this.text.setLength(k);
/* 1021 */     localToken2 = this._returnToken;
/*      */ 
/* 1023 */     switch (LA(1)) { case '\t':
/*      */     case '\n':
/*      */     case '\r':
/*      */     case ' ':
/* 1026 */       k = this.text.length();
/* 1027 */       mWS(false);
/* 1028 */       this.text.setLength(k);
/* 1029 */       break;
/*      */     case ',':
/*      */     case ']':
/* 1033 */       break;
/*      */     default:
/* 1037 */       throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */     }
/*      */ 
/* 1042 */     if ((LA(1) == ',') && (_tokenSet_10.member(LA(2))) && (LA(3) >= '\003') && (LA(3) <= 'ÿ')) {
/* 1043 */       k = this.text.length();
/* 1044 */       match(',');
/* 1045 */       this.text.setLength(k);
/*      */ 
/* 1047 */       switch (LA(1)) { case '\t':
/*      */       case '\n':
/*      */       case '\r':
/*      */       case ' ':
/* 1050 */         k = this.text.length();
/* 1051 */         mWS(false);
/* 1052 */         this.text.setLength(k);
/* 1053 */         break;
/*      */       case '"':
/*      */       case '#':
/*      */       case '(':
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
/*      */       case '[':
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
/* 1073 */         break;
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
/*      */       case '$':
/*      */       case '%':
/*      */       case '&':
/*      */       case '\'':
/*      */       case ')':
/*      */       case '*':
/*      */       case '+':
/*      */       case ',':
/*      */       case '-':
/*      */       case '.':
/*      */       case '/':
/*      */       case ':':
/*      */       case ';':
/*      */       case '<':
/*      */       case '=':
/*      */       case '>':
/*      */       case '?':
/*      */       case '@':
/*      */       case '\\':
/*      */       case ']':
/*      */       case '^':
/*      */       case '`':
/*      */       default:
/* 1077 */         throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */       }
/*      */ 
/* 1081 */       k = this.text.length();
/* 1082 */       mAST_CTOR_ELEMENT(true);
/* 1083 */       this.text.setLength(k);
/* 1084 */       localToken3 = this._returnToken;
/*      */     }
/* 1086 */     switch (LA(1)) { case '\t':
/*      */     case '\n':
/*      */     case '\r':
/*      */     case ' ':
/* 1089 */       k = this.text.length();
/* 1090 */       mWS(false);
/* 1091 */       this.text.setLength(k);
/* 1092 */       break;
/*      */     case ',':
/*      */     case ']':
/* 1096 */       break;
/*      */     default:
/* 1100 */       throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */ 
/* 1105 */       if ((LA(1) != ',') && (LA(1) != ']'))
/*      */       {
/* 1108 */         throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */       }
/*      */       break;
/*      */     }
/*      */ 
/* 1113 */     switch (LA(1))
/*      */     {
/*      */     case ',':
/* 1116 */       k = this.text.length();
/* 1117 */       match(',');
/* 1118 */       this.text.setLength(k);
/*      */ 
/* 1120 */       switch (LA(1)) { case '\t':
/*      */       case '\n':
/*      */       case '\r':
/*      */       case ' ':
/* 1123 */         k = this.text.length();
/* 1124 */         mWS(false);
/* 1125 */         this.text.setLength(k);
/* 1126 */         break;
/*      */       case '"':
/*      */       case '#':
/*      */       case '(':
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
/*      */       case '[':
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
/* 1146 */         break;
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
/*      */       case '$':
/*      */       case '%':
/*      */       case '&':
/*      */       case '\'':
/*      */       case ')':
/*      */       case '*':
/*      */       case '+':
/*      */       case ',':
/*      */       case '-':
/*      */       case '.':
/*      */       case '/':
/*      */       case ':':
/*      */       case ';':
/*      */       case '<':
/*      */       case '=':
/*      */       case '>':
/*      */       case '?':
/*      */       case '@':
/*      */       case '\\':
/*      */       case ']':
/*      */       case '^':
/*      */       case '`':
/*      */       default:
/* 1150 */         throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */       }
/*      */ 
/* 1154 */       k = this.text.length();
/* 1155 */       mAST_CTOR_ELEMENT(true);
/* 1156 */       this.text.setLength(k);
/* 1157 */       localToken4 = this._returnToken;
/*      */ 
/* 1159 */       switch (LA(1)) { case '\t':
/*      */       case '\n':
/*      */       case '\r':
/*      */       case ' ':
/* 1162 */         k = this.text.length();
/* 1163 */         mWS(false);
/* 1164 */         this.text.setLength(k);
/* 1165 */         break;
/*      */       case ']':
/* 1169 */         break;
/*      */       default:
/* 1173 */         throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */       }
/*      */ 
/*      */       break;
/*      */     case ']':
/* 1181 */       break;
/*      */     default:
/* 1185 */       throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */     }
/*      */ 
/* 1189 */     k = this.text.length();
/* 1190 */     match(']');
/* 1191 */     this.text.setLength(k);
/*      */ 
/* 1193 */     String str = localToken2.getText();
/* 1194 */     if (localToken3 != null) {
/* 1195 */       str = str + "," + localToken3.getText();
/*      */     }
/* 1197 */     if (localToken4 != null) {
/* 1198 */       str = str + "," + localToken4.getText();
/*      */     }
/* 1200 */     this.text.setLength(j); this.text.append(this.generator.getASTCreateString(null, str));
/*      */ 
/* 1202 */     if ((paramBoolean) && (localToken1 == null) && (i != -1)) {
/* 1203 */       localToken1 = makeToken(i);
/* 1204 */       localToken1.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/*      */     }
/* 1206 */     this._returnToken = localToken1;
/*      */   }
/*      */ 
/*      */   protected final void mTEXT_ARG(boolean paramBoolean) throws RecognitionException, CharStreamException, TokenStreamException {
/* 1210 */     Token localToken = null; int j = this.text.length();
/* 1211 */     int i = 13;
/*      */ 
/* 1215 */     switch (LA(1)) { case '\t':
/*      */     case '\n':
/*      */     case '\r':
/*      */     case ' ':
/* 1218 */       mWS(false);
/* 1219 */       break;
/*      */     case '"':
/*      */     case '$':
/*      */     case '\'':
/*      */     case '+':
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
/* 1239 */       break;
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
/*      */     case '#':
/*      */     case '%':
/*      */     case '&':
/*      */     case '(':
/*      */     case ')':
/*      */     case '*':
/*      */     case ',':
/*      */     case '-':
/*      */     case '.':
/*      */     case '/':
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
/* 1243 */       throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */     }
/*      */ 
/* 1248 */     int k = 0;
/*      */     while (true)
/*      */     {
/* 1251 */       if ((_tokenSet_11.member(LA(1))) && (LA(2) >= '\003') && (LA(2) <= 'ÿ')) {
/* 1252 */         mTEXT_ARG_ELEMENT(false);
/*      */ 
/* 1254 */         if ((_tokenSet_4.member(LA(1))) && (_tokenSet_12.member(LA(2)))) {
/* 1255 */           mWS(false);
/*      */         }
/* 1257 */         else if (!_tokenSet_12.member(LA(1)))
/*      */         {
/* 1260 */           throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */         }
/*      */ 
/*      */       }
/*      */       else
/*      */       {
/* 1266 */         if (k >= 1) break; throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */       }
/*      */ 
/* 1269 */       k++;
/*      */     }
/*      */ 
/* 1272 */     if ((paramBoolean) && (localToken == null) && (i != -1)) {
/* 1273 */       localToken = makeToken(i);
/* 1274 */       localToken.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/*      */     }
/* 1276 */     this._returnToken = localToken;
/*      */   }
/*      */ 
/*      */   protected final void mTREE_ELEMENT(boolean paramBoolean) throws RecognitionException, CharStreamException, TokenStreamException {
/* 1280 */     Token localToken1 = null; int j = this.text.length();
/* 1281 */     int i = 9;
/*      */ 
/* 1283 */     Token localToken2 = null;
/*      */ 
/* 1286 */     switch (LA(1))
/*      */     {
/*      */     case '(':
/* 1289 */       mTREE(false);
/* 1290 */       break;
/*      */     case '[':
/* 1294 */       mAST_CONSTRUCTOR(false);
/* 1295 */       break;
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
/* 1312 */       mID_ELEMENT(false);
/* 1313 */       break;
/*      */     case '"':
/* 1317 */       mSTRING(false);
/* 1318 */       break;
/*      */     case '#':
/*      */     case '$':
/*      */     case '%':
/*      */     case '&':
/*      */     case '\'':
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
/*      */     case '\\':
/*      */     case ']':
/*      */     case '^':
/*      */     case '`':
/*      */     default:
/*      */       int k;
/* 1321 */       if ((LA(1) == '#') && (LA(2) == '(')) {
/* 1322 */         k = this.text.length();
/* 1323 */         match('#');
/* 1324 */         this.text.setLength(k);
/* 1325 */         mTREE(false);
/*      */       }
/* 1327 */       else if ((LA(1) == '#') && (LA(2) == '[')) {
/* 1328 */         k = this.text.length();
/* 1329 */         match('#');
/* 1330 */         this.text.setLength(k);
/* 1331 */         mAST_CONSTRUCTOR(false);
/*      */       }
/*      */       else
/*      */       {
/*      */         String str;
/* 1333 */         if ((LA(1) == '#') && (_tokenSet_3.member(LA(2)))) {
/* 1334 */           k = this.text.length();
/* 1335 */           match('#');
/* 1336 */           this.text.setLength(k);
/* 1337 */           boolean bool = mID_ELEMENT(true);
/* 1338 */           localToken2 = this._returnToken;
/*      */ 
/* 1340 */           if (!bool)
/*      */           {
/* 1342 */             str = this.generator.mapTreeId(localToken2.getText(), null);
/* 1343 */             this.text.setLength(j); this.text.append(str);
/*      */           }
/*      */ 
/*      */         }
/* 1347 */         else if ((LA(1) == '#') && (LA(2) == '#')) {
/* 1348 */           match("##");
/* 1349 */           str = this.currentRule.getRuleName() + "_AST"; this.text.setLength(j); this.text.append(str);
/*      */         }
/*      */         else {
/* 1352 */           throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn()); } 
/*      */       }break;
/*      */     }
/* 1355 */     if ((paramBoolean) && (localToken1 == null) && (i != -1)) {
/* 1356 */       localToken1 = makeToken(i);
/* 1357 */       localToken1.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/*      */     }
/* 1359 */     this._returnToken = localToken1;
/*      */   }
/*      */ 
/*      */   protected final boolean mID_ELEMENT(boolean paramBoolean)
/*      */     throws RecognitionException, CharStreamException, TokenStreamException
/*      */   {
/* 1366 */     boolean bool = false;
/* 1367 */     Token localToken1 = null; int j = this.text.length();
/* 1368 */     int i = 12;
/*      */ 
/* 1370 */     Token localToken2 = null;
/*      */ 
/* 1372 */     mID(true);
/* 1373 */     localToken2 = this._returnToken;
/*      */     int k;
/* 1375 */     if ((_tokenSet_4.member(LA(1))) && (_tokenSet_13.member(LA(2)))) {
/* 1376 */       k = this.text.length();
/* 1377 */       mWS(false);
/* 1378 */       this.text.setLength(k);
/*      */     }
/* 1380 */     else if (!_tokenSet_13.member(LA(1)))
/*      */     {
/* 1383 */       throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */     }
/*      */ 
/* 1388 */     switch (LA(1))
/*      */     {
/*      */     case '(':
/* 1391 */       match('(');
/*      */ 
/* 1393 */       if ((_tokenSet_4.member(LA(1))) && (_tokenSet_14.member(LA(2))) && (LA(3) >= '\003') && (LA(3) <= 'ÿ')) {
/* 1394 */         k = this.text.length();
/* 1395 */         mWS(false);
/* 1396 */         this.text.setLength(k);
/*      */       }
/* 1398 */       else if ((!_tokenSet_14.member(LA(1))) || (LA(2) < '\003') || (LA(2) > 'ÿ'))
/*      */       {
/* 1401 */         throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */       }
/*      */ 
/* 1406 */       switch (LA(1)) { case '"':
/*      */       case '#':
/*      */       case '\'':
/*      */       case '(':
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
/*      */       case '[':
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
/* 1425 */         mARG(false);
/*      */       case '\t':
/*      */       case '\n':
/*      */       case '\r':
/*      */       case ' ':
/*      */       case ')':
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
/*      */       case '$':
/*      */       case '%':
/*      */       case '&':
/*      */       case '*':
/*      */       case '+':
/*      */       case ',':
/*      */       case '-':
/*      */       case '.':
/*      */       case '/':
/*      */       case ':':
/*      */       case ';':
/*      */       case '<':
/*      */       case '=':
/*      */       case '>':
/*      */       case '?':
/*      */       case '@':
/*      */       case '\\':
/*      */       case ']':
/*      */       case '^':
/* 1429 */       case '`': } while (LA(1) == ',') {
/* 1430 */         match(',');
/*      */ 
/* 1432 */         switch (LA(1)) { case '\t':
/*      */         case '\n':
/*      */         case '\r':
/*      */         case ' ':
/* 1435 */           k = this.text.length();
/* 1436 */           mWS(false);
/* 1437 */           this.text.setLength(k);
/* 1438 */           break;
/*      */         case '"':
/*      */         case '#':
/*      */         case '\'':
/*      */         case '(':
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
/*      */         case '[':
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
/* 1458 */           break;
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
/*      */         case '!':
/*      */         case '$':
/*      */         case '%':
/*      */         case '&':
/*      */         case ')':
/*      */         case '*':
/*      */         case '+':
/*      */         case ',':
/*      */         case '-':
/*      */         case '.':
/*      */         case '/':
/*      */         case ':':
/*      */         case ';':
/*      */         case '<':
/*      */         case '=':
/*      */         case '>':
/*      */         case '?':
/*      */         case '@':
/*      */         case '\\':
/*      */         case ']':
/*      */         case '^':
/*      */         case '`':
/*      */         default:
/* 1462 */           throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */         }
/*      */ 
/* 1466 */         mARG(false); continue;
/*      */ 
/* 1479 */         break;
/*      */ 
/* 1483 */         throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */       }
/*      */ 
/* 1488 */       switch (LA(1)) { case '\t':
/*      */       case '\n':
/*      */       case '\r':
/*      */       case ' ':
/* 1491 */         k = this.text.length();
/* 1492 */         mWS(false);
/* 1493 */         this.text.setLength(k);
/* 1494 */         break;
/*      */       case ')':
/* 1498 */         break;
/*      */       default:
/* 1502 */         throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */       }
/*      */ 
/* 1506 */       match(')');
/* 1507 */       break;
/*      */     case '[':
/* 1512 */       int m = 0;
/*      */       while (true)
/*      */       {
/* 1515 */         if (LA(1) == '[') {
/* 1516 */           match('[');
/*      */ 
/* 1518 */           switch (LA(1)) { case '\t':
/*      */           case '\n':
/*      */           case '\r':
/*      */           case ' ':
/* 1521 */             k = this.text.length();
/* 1522 */             mWS(false);
/* 1523 */             this.text.setLength(k);
/* 1524 */             break;
/*      */           case '"':
/*      */           case '#':
/*      */           case '\'':
/*      */           case '(':
/*      */           case '0':
/*      */           case '1':
/*      */           case '2':
/*      */           case '3':
/*      */           case '4':
/*      */           case '5':
/*      */           case '6':
/*      */           case '7':
/*      */           case '8':
/*      */           case '9':
/*      */           case 'A':
/*      */           case 'B':
/*      */           case 'C':
/*      */           case 'D':
/*      */           case 'E':
/*      */           case 'F':
/*      */           case 'G':
/*      */           case 'H':
/*      */           case 'I':
/*      */           case 'J':
/*      */           case 'K':
/*      */           case 'L':
/*      */           case 'M':
/*      */           case 'N':
/*      */           case 'O':
/*      */           case 'P':
/*      */           case 'Q':
/*      */           case 'R':
/*      */           case 'S':
/*      */           case 'T':
/*      */           case 'U':
/*      */           case 'V':
/*      */           case 'W':
/*      */           case 'X':
/*      */           case 'Y':
/*      */           case 'Z':
/*      */           case '[':
/*      */           case '_':
/*      */           case 'a':
/*      */           case 'b':
/*      */           case 'c':
/*      */           case 'd':
/*      */           case 'e':
/*      */           case 'f':
/*      */           case 'g':
/*      */           case 'h':
/*      */           case 'i':
/*      */           case 'j':
/*      */           case 'k':
/*      */           case 'l':
/*      */           case 'm':
/*      */           case 'n':
/*      */           case 'o':
/*      */           case 'p':
/*      */           case 'q':
/*      */           case 'r':
/*      */           case 's':
/*      */           case 't':
/*      */           case 'u':
/*      */           case 'v':
/*      */           case 'w':
/*      */           case 'x':
/*      */           case 'y':
/*      */           case 'z':
/* 1544 */             break;
/*      */           case '\013':
/*      */           case '\f':
/*      */           case '\016':
/*      */           case '\017':
/*      */           case '\020':
/*      */           case '\021':
/*      */           case '\022':
/*      */           case '\023':
/*      */           case '\024':
/*      */           case '\025':
/*      */           case '\026':
/*      */           case '\027':
/*      */           case '\030':
/*      */           case '\031':
/*      */           case '\032':
/*      */           case '\033':
/*      */           case '\034':
/*      */           case '\035':
/*      */           case '\036':
/*      */           case '\037':
/*      */           case '!':
/*      */           case '$':
/*      */           case '%':
/*      */           case '&':
/*      */           case ')':
/*      */           case '*':
/*      */           case '+':
/*      */           case ',':
/*      */           case '-':
/*      */           case '.':
/*      */           case '/':
/*      */           case ':':
/*      */           case ';':
/*      */           case '<':
/*      */           case '=':
/*      */           case '>':
/*      */           case '?':
/*      */           case '@':
/*      */           case '\\':
/*      */           case ']':
/*      */           case '^':
/*      */           case '`':
/*      */           default:
/* 1548 */             throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */           }
/*      */ 
/* 1552 */           mARG(false);
/*      */ 
/* 1554 */           switch (LA(1)) { case '\t':
/*      */           case '\n':
/*      */           case '\r':
/*      */           case ' ':
/* 1557 */             k = this.text.length();
/* 1558 */             mWS(false);
/* 1559 */             this.text.setLength(k);
/* 1560 */             break;
/*      */           case ']':
/* 1564 */             break;
/*      */           default:
/* 1568 */             throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */           }
/*      */ 
/* 1572 */           match(']');
/*      */         }
/*      */         else {
/* 1575 */           if (m >= 1) break; throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */         }
/*      */ 
/* 1578 */         m++;
/*      */       }
/*      */ 
/*      */     case '.':
/* 1585 */       match('.');
/* 1586 */       mID_ELEMENT(false);
/* 1587 */       break;
/*      */     case '\t':
/*      */     case '\n':
/*      */     case '\r':
/*      */     case ' ':
/*      */     case ')':
/*      */     case '*':
/*      */     case '+':
/*      */     case ',':
/*      */     case '-':
/*      */     case '/':
/*      */     case '=':
/*      */     case ']':
/* 1594 */       bool = true;
/* 1595 */       String str = this.generator.mapTreeId(localToken2.getText(), this.transInfo);
/* 1596 */       this.text.setLength(j); this.text.append(str);
/*      */ 
/* 1599 */       if ((_tokenSet_15.member(LA(1))) && (_tokenSet_16.member(LA(2))) && (this.transInfo != null) && (this.transInfo.refRuleRoot != null))
/*      */       {
/* 1601 */         switch (LA(1)) { case '\t':
/*      */         case '\n':
/*      */         case '\r':
/*      */         case ' ':
/* 1604 */           mWS(false);
/* 1605 */           break;
/*      */         case '=':
/* 1609 */           break;
/*      */         default:
/* 1613 */           throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */         }
/*      */ 
/* 1617 */         mVAR_ASSIGN(false);
/*      */       }
/* 1619 */       else if (!_tokenSet_17.member(LA(1)))
/*      */       {
/* 1622 */         throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */       }
/*      */ 
/*      */       break;
/*      */     default:
/* 1630 */       throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */     }
/*      */ 
/* 1634 */     if ((paramBoolean) && (localToken1 == null) && (i != -1)) {
/* 1635 */       localToken1 = makeToken(i);
/* 1636 */       localToken1.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/*      */     }
/* 1638 */     this._returnToken = localToken1;
/* 1639 */     return bool;
/*      */   }
/*      */ 
/*      */   protected final void mAST_CTOR_ELEMENT(boolean paramBoolean)
/*      */     throws RecognitionException, CharStreamException, TokenStreamException
/*      */   {
/* 1646 */     Token localToken = null; int j = this.text.length();
/* 1647 */     int i = 11;
/*      */ 
/* 1650 */     if ((LA(1) == '"') && (LA(2) >= '\003') && (LA(2) <= 'ÿ') && (LA(3) >= '\003') && (LA(3) <= 'ÿ')) {
/* 1651 */       mSTRING(false);
/*      */     }
/* 1653 */     else if ((_tokenSet_18.member(LA(1))) && (LA(2) >= '\003') && (LA(2) <= 'ÿ')) {
/* 1654 */       mTREE_ELEMENT(false);
/*      */     }
/* 1656 */     else if ((LA(1) >= '0') && (LA(1) <= '9')) {
/* 1657 */       mINT(false);
/*      */     }
/*      */     else {
/* 1660 */       throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */     }
/*      */ 
/* 1663 */     if ((paramBoolean) && (localToken == null) && (i != -1)) {
/* 1664 */       localToken = makeToken(i);
/* 1665 */       localToken.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/*      */     }
/* 1667 */     this._returnToken = localToken;
/*      */   }
/*      */ 
/*      */   protected final void mINT(boolean paramBoolean) throws RecognitionException, CharStreamException, TokenStreamException {
/* 1671 */     Token localToken = null; int j = this.text.length();
/* 1672 */     int i = 26;
/*      */ 
/* 1676 */     int k = 0;
/*      */     while (true)
/*      */     {
/* 1679 */       if ((LA(1) >= '0') && (LA(1) <= '9')) {
/* 1680 */         mDIGIT(false);
/*      */       }
/*      */       else {
/* 1683 */         if (k >= 1) break; throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */       }
/*      */ 
/* 1686 */       k++;
/*      */     }
/*      */ 
/* 1689 */     if ((paramBoolean) && (localToken == null) && (i != -1)) {
/* 1690 */       localToken = makeToken(i);
/* 1691 */       localToken.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/*      */     }
/* 1693 */     this._returnToken = localToken;
/*      */   }
/*      */ 
/*      */   protected final void mARG(boolean paramBoolean) throws RecognitionException, CharStreamException, TokenStreamException {
/* 1697 */     Token localToken = null; int j = this.text.length();
/* 1698 */     int i = 16;
/*      */ 
/* 1702 */     switch (LA(1))
/*      */     {
/*      */     case '\'':
/* 1705 */       mCHAR(false);
/* 1706 */       break;
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
/* 1712 */       mINT_OR_FLOAT(false);
/* 1713 */       break;
/*      */     case '(':
/*      */     case ')':
/*      */     case '*':
/*      */     case '+':
/*      */     case ',':
/*      */     case '-':
/*      */     case '.':
/*      */     case '/':
/*      */     default:
/* 1716 */       if ((_tokenSet_18.member(LA(1))) && (LA(2) >= '\003') && (LA(2) <= 'ÿ') && (LA(3) >= '\003') && (LA(3) <= 'ÿ')) {
/* 1717 */         mTREE_ELEMENT(false);
/*      */       }
/* 1719 */       else if ((LA(1) == '"') && (LA(2) >= '\003') && (LA(2) <= 'ÿ') && (LA(3) >= '\003') && (LA(3) <= 'ÿ')) {
/* 1720 */         mSTRING(false);
/*      */       }
/*      */       else {
/* 1723 */         throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */       }
/*      */ 
/*      */       break;
/*      */     }
/*      */ 
/* 1730 */     while ((_tokenSet_19.member(LA(1))) && (_tokenSet_20.member(LA(2))) && (LA(3) >= '\003') && (LA(3) <= 'ÿ'))
/*      */     {
/* 1732 */       switch (LA(1)) { case '\t':
/*      */       case '\n':
/*      */       case '\r':
/*      */       case ' ':
/* 1735 */         mWS(false);
/* 1736 */         break;
/*      */       case '*':
/*      */       case '+':
/*      */       case '-':
/*      */       case '/':
/* 1740 */         break;
/*      */       default:
/* 1744 */         throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */       }
/*      */ 
/* 1749 */       switch (LA(1))
/*      */       {
/*      */       case '+':
/* 1752 */         match('+');
/* 1753 */         break;
/*      */       case '-':
/* 1757 */         match('-');
/* 1758 */         break;
/*      */       case '*':
/* 1762 */         match('*');
/* 1763 */         break;
/*      */       case '/':
/* 1767 */         match('/');
/* 1768 */         break;
/*      */       case ',':
/*      */       case '.':
/*      */       default:
/* 1772 */         throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */       }
/*      */ 
/* 1777 */       switch (LA(1)) { case '\t':
/*      */       case '\n':
/*      */       case '\r':
/*      */       case ' ':
/* 1780 */         mWS(false);
/* 1781 */         break;
/*      */       case '"':
/*      */       case '#':
/*      */       case '\'':
/*      */       case '(':
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
/*      */       case '[':
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
/* 1801 */         break;
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
/*      */       case '$':
/*      */       case '%':
/*      */       case '&':
/*      */       case ')':
/*      */       case '*':
/*      */       case '+':
/*      */       case ',':
/*      */       case '-':
/*      */       case '.':
/*      */       case '/':
/*      */       case ':':
/*      */       case ';':
/*      */       case '<':
/*      */       case '=':
/*      */       case '>':
/*      */       case '?':
/*      */       case '@':
/*      */       case '\\':
/*      */       case ']':
/*      */       case '^':
/*      */       case '`':
/*      */       default:
/* 1805 */         throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */       }
/*      */ 
/* 1809 */       mARG(false);
/*      */     }
/*      */ 
/* 1817 */     if ((paramBoolean) && (localToken == null) && (i != -1)) {
/* 1818 */       localToken = makeToken(i);
/* 1819 */       localToken.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/*      */     }
/* 1821 */     this._returnToken = localToken;
/*      */   }
/*      */ 
/*      */   protected final void mTEXT_ARG_ELEMENT(boolean paramBoolean) throws RecognitionException, CharStreamException, TokenStreamException {
/* 1825 */     Token localToken = null; int j = this.text.length();
/* 1826 */     int i = 14;
/*      */ 
/* 1829 */     switch (LA(1)) { case 'A':
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
/* 1845 */       mTEXT_ARG_ID_ELEMENT(false);
/* 1846 */       break;
/*      */     case '"':
/* 1850 */       mSTRING(false);
/* 1851 */       break;
/*      */     case '\'':
/* 1855 */       mCHAR(false);
/* 1856 */       break;
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
/* 1862 */       mINT_OR_FLOAT(false);
/* 1863 */       break;
/*      */     case '$':
/* 1867 */       mTEXT_ITEM(false);
/* 1868 */       break;
/*      */     case '+':
/* 1872 */       match('+');
/* 1873 */       break;
/*      */     case '#':
/*      */     case '%':
/*      */     case '&':
/*      */     case '(':
/*      */     case ')':
/*      */     case '*':
/*      */     case ',':
/*      */     case '-':
/*      */     case '.':
/*      */     case '/':
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
/* 1877 */       throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */     }
/*      */ 
/* 1880 */     if ((paramBoolean) && (localToken == null) && (i != -1)) {
/* 1881 */       localToken = makeToken(i);
/* 1882 */       localToken.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/*      */     }
/* 1884 */     this._returnToken = localToken;
/*      */   }
/*      */ 
/*      */   protected final void mTEXT_ARG_ID_ELEMENT(boolean paramBoolean) throws RecognitionException, CharStreamException, TokenStreamException {
/* 1888 */     Token localToken1 = null; int j = this.text.length();
/* 1889 */     int i = 15;
/*      */ 
/* 1891 */     Token localToken2 = null;
/*      */ 
/* 1893 */     mID(true);
/* 1894 */     localToken2 = this._returnToken;
/*      */     int k;
/* 1896 */     if ((_tokenSet_4.member(LA(1))) && (_tokenSet_21.member(LA(2)))) {
/* 1897 */       k = this.text.length();
/* 1898 */       mWS(false);
/* 1899 */       this.text.setLength(k);
/*      */     }
/* 1901 */     else if (!_tokenSet_21.member(LA(1)))
/*      */     {
/* 1904 */       throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */     }
/*      */ 
/* 1909 */     switch (LA(1))
/*      */     {
/*      */     case '(':
/* 1912 */       match('(');
/*      */ 
/* 1914 */       if ((_tokenSet_4.member(LA(1))) && (_tokenSet_22.member(LA(2))) && (LA(3) >= '\003') && (LA(3) <= 'ÿ')) {
/* 1915 */         k = this.text.length();
/* 1916 */         mWS(false);
/* 1917 */         this.text.setLength(k);
/*      */       }
/* 1919 */       else if ((!_tokenSet_22.member(LA(1))) || (LA(2) < '\003') || (LA(2) > 'ÿ'))
/*      */       {
/* 1922 */         throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */       }
/*      */ 
/* 1929 */       if ((_tokenSet_23.member(LA(1))) && (LA(2) >= '\003') && (LA(2) <= 'ÿ') && (LA(3) >= '\003') && (LA(3) <= 'ÿ')) {
/* 1930 */         mTEXT_ARG(false);
/*      */ 
/* 1934 */         while (LA(1) == ',') {
/* 1935 */           match(',');
/* 1936 */           mTEXT_ARG(false);
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/* 1952 */       switch (LA(1)) { case '\t':
/*      */       case '\n':
/*      */       case '\r':
/*      */       case ' ':
/* 1955 */         k = this.text.length();
/* 1956 */         mWS(false);
/* 1957 */         this.text.setLength(k);
/* 1958 */         break;
/*      */       case ')':
/* 1962 */         break;
/*      */       default:
/* 1966 */         throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */       }
/*      */ 
/* 1970 */       match(')');
/* 1971 */       break;
/*      */     case '[':
/* 1976 */       int m = 0;
/*      */       while (true)
/*      */       {
/* 1979 */         if (LA(1) == '[') {
/* 1980 */           match('[');
/*      */ 
/* 1982 */           if ((_tokenSet_4.member(LA(1))) && (_tokenSet_23.member(LA(2))) && (LA(3) >= '\003') && (LA(3) <= 'ÿ')) {
/* 1983 */             k = this.text.length();
/* 1984 */             mWS(false);
/* 1985 */             this.text.setLength(k);
/*      */           }
/* 1987 */           else if ((!_tokenSet_23.member(LA(1))) || (LA(2) < '\003') || (LA(2) > 'ÿ') || (LA(3) < '\003') || (LA(3) > 'ÿ'))
/*      */           {
/* 1990 */             throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */           }
/*      */ 
/* 1994 */           mTEXT_ARG(false);
/*      */ 
/* 1996 */           switch (LA(1)) { case '\t':
/*      */           case '\n':
/*      */           case '\r':
/*      */           case ' ':
/* 1999 */             k = this.text.length();
/* 2000 */             mWS(false);
/* 2001 */             this.text.setLength(k);
/* 2002 */             break;
/*      */           case ']':
/* 2006 */             break;
/*      */           default:
/* 2010 */             throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */           }
/*      */ 
/* 2014 */           match(']');
/*      */         }
/*      */         else {
/* 2017 */           if (m >= 1) break; throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */         }
/*      */ 
/* 2020 */         m++;
/*      */       }
/*      */ 
/*      */     case '.':
/* 2027 */       match('.');
/* 2028 */       mTEXT_ARG_ID_ELEMENT(false);
/* 2029 */       break;
/*      */     case '\t':
/*      */     case '\n':
/*      */     case '\r':
/*      */     case ' ':
/*      */     case '"':
/*      */     case '$':
/*      */     case '\'':
/*      */     case ')':
/*      */     case '+':
/*      */     case ',':
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
/*      */     case ']':
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
/* 2051 */       break;
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
/*      */     case '#':
/*      */     case '%':
/*      */     case '&':
/*      */     case '*':
/*      */     case '-':
/*      */     case '/':
/*      */     case ':':
/*      */     case ';':
/*      */     case '<':
/*      */     case '=':
/*      */     case '>':
/*      */     case '?':
/*      */     case '@':
/*      */     case '\\':
/*      */     case '^':
/*      */     case '`':
/*      */     default:
/* 2055 */       throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */     }
/*      */ 
/* 2059 */     if ((paramBoolean) && (localToken1 == null) && (i != -1)) {
/* 2060 */       localToken1 = makeToken(i);
/* 2061 */       localToken1.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/*      */     }
/* 2063 */     this._returnToken = localToken1;
/*      */   }
/*      */ 
/*      */   protected final void mINT_OR_FLOAT(boolean paramBoolean) throws RecognitionException, CharStreamException, TokenStreamException {
/* 2067 */     Token localToken = null; int j = this.text.length();
/* 2068 */     int i = 27;
/*      */ 
/* 2072 */     int k = 0;
/*      */     while (true)
/*      */     {
/* 2075 */       if ((LA(1) >= '0') && (LA(1) <= '9') && (_tokenSet_24.member(LA(2)))) {
/* 2076 */         mDIGIT(false);
/*      */       }
/*      */       else {
/* 2079 */         if (k >= 1) break; throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */       }
/*      */ 
/* 2082 */       k++;
/*      */     }
/*      */ 
/* 2086 */     if ((LA(1) == 'L') && (_tokenSet_25.member(LA(2)))) {
/* 2087 */       match('L');
/*      */     }
/* 2089 */     else if ((LA(1) == 'l') && (_tokenSet_25.member(LA(2)))) {
/* 2090 */       match('l');
/*      */     } else {
/* 2092 */       if (LA(1) == '.') {
/* 2093 */         match('.');
/*      */ 
/* 2097 */         while ((LA(1) >= '0') && (LA(1) <= '9') && (_tokenSet_25.member(LA(2)))) {
/* 2098 */           mDIGIT(false);
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/* 2107 */       if (!_tokenSet_25.member(LA(1)))
/*      */       {
/* 2110 */         throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */       }
/*      */     }
/*      */ 
/* 2114 */     if ((paramBoolean) && (localToken == null) && (i != -1)) {
/* 2115 */       localToken = makeToken(i);
/* 2116 */       localToken.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/*      */     }
/* 2118 */     this._returnToken = localToken;
/*      */   }
/*      */ 
/*      */   protected final void mSL_COMMENT(boolean paramBoolean) throws RecognitionException, CharStreamException, TokenStreamException {
/* 2122 */     Token localToken = null; int j = this.text.length();
/* 2123 */     int i = 20;
/*      */ 
/* 2126 */     match("//");
/*      */ 
/* 2131 */     while ((LA(1) != '\n') && (LA(1) != '\r') && 
/* 2132 */       (LA(1) >= '\003') && (LA(1) <= 'ÿ') && (LA(2) >= '\003') && (LA(2) <= 'ÿ')) {
/* 2133 */       matchNot(65535);
/*      */     }
/*      */ 
/* 2142 */     if ((LA(1) == '\r') && (LA(2) == '\n')) {
/* 2143 */       match("\r\n");
/*      */     }
/* 2145 */     else if (LA(1) == '\n') {
/* 2146 */       match('\n');
/*      */     }
/* 2148 */     else if (LA(1) == '\r') {
/* 2149 */       match('\r');
/*      */     }
/*      */     else {
/* 2152 */       throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */     }
/*      */ 
/* 2156 */     newline();
/* 2157 */     if ((paramBoolean) && (localToken == null) && (i != -1)) {
/* 2158 */       localToken = makeToken(i);
/* 2159 */       localToken.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/*      */     }
/* 2161 */     this._returnToken = localToken;
/*      */   }
/*      */ 
/*      */   protected final void mML_COMMENT(boolean paramBoolean) throws RecognitionException, CharStreamException, TokenStreamException {
/* 2165 */     Token localToken = null; int j = this.text.length();
/* 2166 */     int i = 21;
/*      */ 
/* 2169 */     match("/*");
/*      */ 
/* 2174 */     while ((LA(1) != '*') || (LA(2) != '/')) {
/* 2175 */       if ((LA(1) == '\r') && (LA(2) == '\n') && (LA(3) >= '\003') && (LA(3) <= 'ÿ')) {
/* 2176 */         match('\r');
/* 2177 */         match('\n');
/* 2178 */         newline();
/*      */       }
/* 2180 */       else if ((LA(1) == '\r') && (LA(2) >= '\003') && (LA(2) <= 'ÿ') && (LA(3) >= '\003') && (LA(3) <= 'ÿ')) {
/* 2181 */         match('\r');
/* 2182 */         newline();
/*      */       }
/* 2184 */       else if ((LA(1) == '\n') && (LA(2) >= '\003') && (LA(2) <= 'ÿ') && (LA(3) >= '\003') && (LA(3) <= 'ÿ')) {
/* 2185 */         match('\n');
/* 2186 */         newline();
/*      */       } else {
/* 2188 */         if ((LA(1) < '\003') || (LA(1) > 'ÿ') || (LA(2) < '\003') || (LA(2) > 'ÿ') || (LA(3) < '\003') || (LA(3) > 'ÿ')) break;
/* 2189 */         matchNot(65535);
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 2197 */     match("*/");
/* 2198 */     if ((paramBoolean) && (localToken == null) && (i != -1)) {
/* 2199 */       localToken = makeToken(i);
/* 2200 */       localToken.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/*      */     }
/* 2202 */     this._returnToken = localToken;
/*      */   }
/*      */ 
/*      */   protected final void mESC(boolean paramBoolean) throws RecognitionException, CharStreamException, TokenStreamException {
/* 2206 */     Token localToken = null; int j = this.text.length();
/* 2207 */     int i = 24;
/*      */ 
/* 2210 */     match('\\');
/*      */ 
/* 2212 */     switch (LA(1))
/*      */     {
/*      */     case 'n':
/* 2215 */       match('n');
/* 2216 */       break;
/*      */     case 'r':
/* 2220 */       match('r');
/* 2221 */       break;
/*      */     case 't':
/* 2225 */       match('t');
/* 2226 */       break;
/*      */     case 'b':
/* 2230 */       match('b');
/* 2231 */       break;
/*      */     case 'f':
/* 2235 */       match('f');
/* 2236 */       break;
/*      */     case '"':
/* 2240 */       match('"');
/* 2241 */       break;
/*      */     case '\'':
/* 2245 */       match('\'');
/* 2246 */       break;
/*      */     case '\\':
/* 2250 */       match('\\');
/* 2251 */       break;
/*      */     case '0':
/*      */     case '1':
/*      */     case '2':
/*      */     case '3':
/* 2256 */       matchRange('0', '3');
/*      */ 
/* 2259 */       if ((LA(1) >= '0') && (LA(1) <= '9') && (LA(2) >= '\003') && (LA(2) <= 'ÿ')) {
/* 2260 */         mDIGIT(false);
/*      */ 
/* 2262 */         if ((LA(1) >= '0') && (LA(1) <= '9') && (LA(2) >= '\003') && (LA(2) <= 'ÿ')) {
/* 2263 */           mDIGIT(false);
/*      */         }
/* 2265 */         else if ((LA(1) < '\003') || (LA(1) > 'ÿ'))
/*      */         {
/* 2268 */           throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */         }
/*      */ 
/*      */       }
/* 2273 */       else if ((LA(1) < '\003') || (LA(1) > 'ÿ'))
/*      */       {
/* 2276 */         throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */       }
/*      */ 
/*      */       break;
/*      */     case '4':
/*      */     case '5':
/*      */     case '6':
/*      */     case '7':
/* 2285 */       matchRange('4', '7');
/*      */ 
/* 2288 */       if ((LA(1) >= '0') && (LA(1) <= '9') && (LA(2) >= '\003') && (LA(2) <= 'ÿ')) {
/* 2289 */         mDIGIT(false);
/*      */       }
/* 2291 */       else if ((LA(1) < '\003') || (LA(1) > 'ÿ'))
/*      */       {
/* 2294 */         throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */       }
/*      */ 
/*      */       break;
/*      */     default:
/* 2302 */       throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */     }
/*      */ 
/* 2306 */     if ((paramBoolean) && (localToken == null) && (i != -1)) {
/* 2307 */       localToken = makeToken(i);
/* 2308 */       localToken.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/*      */     }
/* 2310 */     this._returnToken = localToken;
/*      */   }
/*      */ 
/*      */   protected final void mDIGIT(boolean paramBoolean) throws RecognitionException, CharStreamException, TokenStreamException {
/* 2314 */     Token localToken = null; int j = this.text.length();
/* 2315 */     int i = 25;
/*      */ 
/* 2318 */     matchRange('0', '9');
/* 2319 */     if ((paramBoolean) && (localToken == null) && (i != -1)) {
/* 2320 */       localToken = makeToken(i);
/* 2321 */       localToken.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/*      */     }
/* 2323 */     this._returnToken = localToken;
/*      */   }
/*      */ 
/*      */   private static final long[] mk_tokenSet_0()
/*      */   {
/* 2328 */     long[] arrayOfLong = new long[8];
/* 2329 */     arrayOfLong[0] = -103079215112L;
/* 2330 */     for (int i = 1; i <= 3; i++) arrayOfLong[i] = -1L;
/* 2331 */     return arrayOfLong;
/*      */   }
/*      */ 
/*      */   private static final long[] mk_tokenSet_1() {
/* 2335 */     long[] arrayOfLong = new long[8];
/* 2336 */     arrayOfLong[0] = -145135534866440L;
/* 2337 */     for (int i = 1; i <= 3; i++) arrayOfLong[i] = -1L;
/* 2338 */     return arrayOfLong;
/*      */   }
/*      */ 
/*      */   private static final long[] mk_tokenSet_2() {
/* 2342 */     long[] arrayOfLong = new long[8];
/* 2343 */     arrayOfLong[0] = -141407503262728L;
/* 2344 */     for (int i = 1; i <= 3; i++) arrayOfLong[i] = -1L;
/* 2345 */     return arrayOfLong;
/*      */   }
/*      */ 
/*      */   private static final long[] mk_tokenSet_3() {
/* 2349 */     long[] arrayOfLong = { 0L, 576460745995190270L, 0L, 0L, 0L };
/* 2350 */     return arrayOfLong;
/*      */   }
/*      */ 
/*      */   private static final long[] mk_tokenSet_4() {
/* 2354 */     long[] arrayOfLong = { 4294977024L, 0L, 0L, 0L, 0L };
/* 2355 */     return arrayOfLong;
/*      */   }
/*      */ 
/*      */   private static final long[] mk_tokenSet_5() {
/* 2359 */     long[] arrayOfLong = { 1103806604800L, 0L, 0L, 0L, 0L };
/* 2360 */     return arrayOfLong;
/*      */   }
/*      */ 
/*      */   private static final long[] mk_tokenSet_6() {
/* 2364 */     long[] arrayOfLong = { 287959436729787904L, 576460745995190270L, 0L, 0L, 0L };
/* 2365 */     return arrayOfLong;
/*      */   }
/*      */ 
/*      */   private static final long[] mk_tokenSet_7() {
/* 2369 */     long[] arrayOfLong = new long[8];
/* 2370 */     arrayOfLong[0] = -17179869192L;
/* 2371 */     arrayOfLong[1] = -268435457L;
/* 2372 */     for (int i = 2; i <= 3; i++) arrayOfLong[i] = -1L;
/* 2373 */     return arrayOfLong;
/*      */   }
/*      */ 
/*      */   private static final long[] mk_tokenSet_8() {
/* 2377 */     long[] arrayOfLong = new long[8];
/* 2378 */     arrayOfLong[0] = -549755813896L;
/* 2379 */     arrayOfLong[1] = -268435457L;
/* 2380 */     for (int i = 2; i <= 3; i++) arrayOfLong[i] = -1L;
/* 2381 */     return arrayOfLong;
/*      */   }
/*      */ 
/*      */   private static final long[] mk_tokenSet_9() {
/* 2385 */     long[] arrayOfLong = { 287948901175001088L, 576460745995190270L, 0L, 0L, 0L };
/* 2386 */     return arrayOfLong;
/*      */   }
/*      */ 
/*      */   private static final long[] mk_tokenSet_10() {
/* 2390 */     long[] arrayOfLong = { 287950056521213440L, 576460746129407998L, 0L, 0L, 0L };
/* 2391 */     return arrayOfLong;
/*      */   }
/*      */ 
/*      */   private static final long[] mk_tokenSet_11() {
/* 2395 */     long[] arrayOfLong = { 287958332923183104L, 576460745995190270L, 0L, 0L, 0L };
/* 2396 */     return arrayOfLong;
/*      */   }
/*      */ 
/*      */   private static final long[] mk_tokenSet_12() {
/* 2400 */     long[] arrayOfLong = { 287978128427460096L, 576460746532061182L, 0L, 0L, 0L };
/* 2401 */     return arrayOfLong;
/*      */   }
/*      */ 
/*      */   private static final long[] mk_tokenSet_13() {
/* 2405 */     long[] arrayOfLong = { 2306123388973753856L, 671088640L, 0L, 0L, 0L };
/* 2406 */     return arrayOfLong;
/*      */   }
/*      */ 
/*      */   private static final long[] mk_tokenSet_14() {
/* 2410 */     long[] arrayOfLong = { 287952805300282880L, 576460746129407998L, 0L, 0L, 0L };
/* 2411 */     return arrayOfLong;
/*      */   }
/*      */ 
/*      */   private static final long[] mk_tokenSet_15() {
/* 2415 */     long[] arrayOfLong = { 2305843013508670976L, 0L, 0L, 0L, 0L };
/* 2416 */     return arrayOfLong;
/*      */   }
/*      */ 
/*      */   private static final long[] mk_tokenSet_16() {
/* 2420 */     long[] arrayOfLong = { 2306051920717948416L, 536870912L, 0L, 0L, 0L };
/* 2421 */     return arrayOfLong;
/*      */   }
/*      */ 
/*      */   private static final long[] mk_tokenSet_17() {
/* 2425 */     long[] arrayOfLong = { 208911504254464L, 536870912L, 0L, 0L, 0L };
/* 2426 */     return arrayOfLong;
/*      */   }
/*      */ 
/*      */   private static final long[] mk_tokenSet_18() {
/* 2430 */     long[] arrayOfLong = { 1151051235328L, 576460746129407998L, 0L, 0L, 0L };
/* 2431 */     return arrayOfLong;
/*      */   }
/*      */ 
/*      */   private static final long[] mk_tokenSet_19() {
/* 2435 */     long[] arrayOfLong = { 189120294954496L, 0L, 0L, 0L, 0L };
/* 2436 */     return arrayOfLong;
/*      */   }
/*      */ 
/*      */   private static final long[] mk_tokenSet_20() {
/* 2440 */     long[] arrayOfLong = { 288139722277004800L, 576460746129407998L, 0L, 0L, 0L };
/* 2441 */     return arrayOfLong;
/*      */   }
/*      */ 
/*      */   private static final long[] mk_tokenSet_21() {
/* 2445 */     long[] arrayOfLong = { 288049596683265536L, 576460746666278910L, 0L, 0L, 0L };
/* 2446 */     return arrayOfLong;
/*      */   }
/*      */ 
/*      */   private static final long[] mk_tokenSet_22() {
/* 2450 */     long[] arrayOfLong = { 287960536241415680L, 576460745995190270L, 0L, 0L, 0L };
/* 2451 */     return arrayOfLong;
/*      */   }
/*      */ 
/*      */   private static final long[] mk_tokenSet_23() {
/* 2455 */     long[] arrayOfLong = { 287958337218160128L, 576460745995190270L, 0L, 0L, 0L };
/* 2456 */     return arrayOfLong;
/*      */   }
/*      */ 
/*      */   private static final long[] mk_tokenSet_24() {
/* 2460 */     long[] arrayOfLong = { 288228817078593024L, 576460746532061182L, 0L, 0L, 0L };
/* 2461 */     return arrayOfLong;
/*      */   }
/*      */ 
/*      */   private static final long[] mk_tokenSet_25() {
/* 2465 */     long[] arrayOfLong = { 288158448334415360L, 576460746532061182L, 0L, 0L, 0L };
/* 2466 */     return arrayOfLong;
/*      */   }
/*      */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     antlr.actions.java.ActionLexer
 * JD-Core Version:    0.6.2
 */