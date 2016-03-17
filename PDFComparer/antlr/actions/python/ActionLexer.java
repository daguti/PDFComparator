/*      */ package antlr.actions.python;
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
/*   63 */   protected int lineOffset = 0;
/*      */   private Tool antlrTool;
/*      */   ActionTransInfo transInfo;
/* 2431 */   public static final BitSet _tokenSet_0 = new BitSet(mk_tokenSet_0());
/*      */ 
/* 2438 */   public static final BitSet _tokenSet_1 = new BitSet(mk_tokenSet_1());
/*      */ 
/* 2445 */   public static final BitSet _tokenSet_2 = new BitSet(mk_tokenSet_2());
/*      */ 
/* 2450 */   public static final BitSet _tokenSet_3 = new BitSet(mk_tokenSet_3());
/*      */ 
/* 2455 */   public static final BitSet _tokenSet_4 = new BitSet(mk_tokenSet_4());
/*      */ 
/* 2460 */   public static final BitSet _tokenSet_5 = new BitSet(mk_tokenSet_5());
/*      */ 
/* 2465 */   public static final BitSet _tokenSet_6 = new BitSet(mk_tokenSet_6());
/*      */ 
/* 2473 */   public static final BitSet _tokenSet_7 = new BitSet(mk_tokenSet_7());
/*      */ 
/* 2481 */   public static final BitSet _tokenSet_8 = new BitSet(mk_tokenSet_8());
/*      */ 
/* 2486 */   public static final BitSet _tokenSet_9 = new BitSet(mk_tokenSet_9());
/*      */ 
/* 2491 */   public static final BitSet _tokenSet_10 = new BitSet(mk_tokenSet_10());
/*      */ 
/* 2496 */   public static final BitSet _tokenSet_11 = new BitSet(mk_tokenSet_11());
/*      */ 
/* 2501 */   public static final BitSet _tokenSet_12 = new BitSet(mk_tokenSet_12());
/*      */ 
/* 2506 */   public static final BitSet _tokenSet_13 = new BitSet(mk_tokenSet_13());
/*      */ 
/* 2511 */   public static final BitSet _tokenSet_14 = new BitSet(mk_tokenSet_14());
/*      */ 
/* 2516 */   public static final BitSet _tokenSet_15 = new BitSet(mk_tokenSet_15());
/*      */ 
/* 2521 */   public static final BitSet _tokenSet_16 = new BitSet(mk_tokenSet_16());
/*      */ 
/* 2526 */   public static final BitSet _tokenSet_17 = new BitSet(mk_tokenSet_17());
/*      */ 
/* 2531 */   public static final BitSet _tokenSet_18 = new BitSet(mk_tokenSet_18());
/*      */ 
/* 2536 */   public static final BitSet _tokenSet_19 = new BitSet(mk_tokenSet_19());
/*      */ 
/* 2541 */   public static final BitSet _tokenSet_20 = new BitSet(mk_tokenSet_20());
/*      */ 
/* 2546 */   public static final BitSet _tokenSet_21 = new BitSet(mk_tokenSet_21());
/*      */ 
/* 2551 */   public static final BitSet _tokenSet_22 = new BitSet(mk_tokenSet_22());
/*      */ 
/* 2556 */   public static final BitSet _tokenSet_23 = new BitSet(mk_tokenSet_23());
/*      */ 
/* 2561 */   public static final BitSet _tokenSet_24 = new BitSet(mk_tokenSet_24());
/*      */ 
/* 2566 */   public static final BitSet _tokenSet_25 = new BitSet(mk_tokenSet_25());
/*      */ 
/*      */   public ActionLexer(String paramString, RuleBlock paramRuleBlock, CodeGenerator paramCodeGenerator, ActionTransInfo paramActionTransInfo)
/*      */   {
/*   73 */     this(new StringReader(paramString));
/*   74 */     this.currentRule = paramRuleBlock;
/*   75 */     this.generator = paramCodeGenerator;
/*   76 */     this.transInfo = paramActionTransInfo;
/*      */   }
/*      */ 
/*      */   public void setLineOffset(int paramInt)
/*      */   {
/*   81 */     setLine(paramInt);
/*      */   }
/*      */ 
/*      */   public void setTool(Tool paramTool) {
/*   85 */     this.antlrTool = paramTool;
/*      */   }
/*      */ 
/*      */   public void reportError(RecognitionException paramRecognitionException)
/*      */   {
/*   90 */     this.antlrTool.error("Syntax error in action: " + paramRecognitionException, getFilename(), getLine(), getColumn());
/*      */   }
/*      */ 
/*      */   public void reportError(String paramString)
/*      */   {
/*   97 */     this.antlrTool.error(paramString, getFilename(), getLine(), getColumn());
/*      */   }
/*      */ 
/*      */   public void reportWarning(String paramString)
/*      */   {
/*  102 */     if (getFilename() == null) {
/*  103 */       this.antlrTool.warning(paramString);
/*      */     }
/*      */     else
/*  106 */       this.antlrTool.warning(paramString, getFilename(), getLine(), getColumn());
/*      */   }
/*      */ 
/*      */   public ActionLexer(InputStream paramInputStream) {
/*  110 */     this(new ByteBuffer(paramInputStream));
/*      */   }
/*      */   public ActionLexer(Reader paramReader) {
/*  113 */     this(new CharBuffer(paramReader));
/*      */   }
/*      */   public ActionLexer(InputBuffer paramInputBuffer) {
/*  116 */     this(new LexerSharedInputState(paramInputBuffer));
/*      */   }
/*      */   public ActionLexer(LexerSharedInputState paramLexerSharedInputState) {
/*  119 */     super(paramLexerSharedInputState);
/*  120 */     this.caseSensitiveLiterals = true;
/*  121 */     setCaseSensitive(true);
/*  122 */     this.literals = new Hashtable();
/*      */   }
/*      */ 
/*      */   public Token nextToken() throws TokenStreamException {
/*  126 */     Token localToken = null;
/*      */     while (true)
/*      */     {
/*  129 */       Object localObject = null;
/*  130 */       int i = 0;
/*  131 */       resetText();
/*      */       try
/*      */       {
/*  134 */         if ((LA(1) >= '\003') && (LA(1) <= 'ÿ')) {
/*  135 */           mACTION(true);
/*  136 */           localToken = this._returnToken;
/*      */         }
/*  139 */         else if (LA(1) == 65535) { uponEOF(); this._returnToken = makeToken(1); } else {
/*  140 */           throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */         }
/*      */ 
/*  143 */         if (this._returnToken == null) continue;
/*  144 */         i = this._returnToken.getType();
/*  145 */         this._returnToken.setType(i);
/*  146 */         return this._returnToken;
/*      */       }
/*      */       catch (RecognitionException localRecognitionException) {
/*  149 */         throw new TokenStreamRecognitionException(localRecognitionException);
/*      */       }
/*      */       catch (CharStreamException localCharStreamException)
/*      */       {
/*  153 */         if ((localCharStreamException instanceof CharStreamIOException)) {
/*  154 */           throw new TokenStreamIOException(((CharStreamIOException)localCharStreamException).io);
/*      */         }
/*      */ 
/*  157 */         throw new TokenStreamException(localCharStreamException.getMessage());
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   public final void mACTION(boolean paramBoolean) throws RecognitionException, CharStreamException, TokenStreamException
/*      */   {
/*  164 */     Token localToken = null; int j = this.text.length();
/*  165 */     int i = 4;
/*      */ 
/*  169 */     int k = 0;
/*      */     while (true)
/*      */     {
/*  172 */       switch (LA(1))
/*      */       {
/*      */       case '#':
/*  175 */         mAST_ITEM(false);
/*  176 */         break;
/*      */       case '$':
/*  180 */         mTEXT_ITEM(false);
/*  181 */         break;
/*      */       default:
/*  184 */         if (_tokenSet_0.member(LA(1))) {
/*  185 */           mSTUFF(false);
/*      */         }
/*      */         else {
/*  188 */           if (k >= 1) break label126; throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */         }break;
/*      */       }
/*  191 */       k++;
/*      */     }
/*      */ 
/*  194 */     label126: if ((paramBoolean) && (localToken == null) && (i != -1)) {
/*  195 */       localToken = makeToken(i);
/*  196 */       localToken.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/*      */     }
/*  198 */     this._returnToken = localToken;
/*      */   }
/*      */ 
/*      */   protected final void mSTUFF(boolean paramBoolean) throws RecognitionException, CharStreamException, TokenStreamException {
/*  202 */     Token localToken = null; int j = this.text.length();
/*  203 */     int i = 5;
/*      */ 
/*  206 */     switch (LA(1))
/*      */     {
/*      */     case '"':
/*  209 */       mSTRING(false);
/*  210 */       break;
/*      */     case '\'':
/*  214 */       mCHAR(false);
/*  215 */       break;
/*      */     case '\n':
/*  219 */       match('\n');
/*  220 */       newline();
/*  221 */       break;
/*      */     default:
/*  224 */       if ((LA(1) == '/') && ((LA(2) == '*') || (LA(2) == '/'))) {
/*  225 */         mCOMMENT(false);
/*      */       }
/*  227 */       else if ((LA(1) == '\r') && (LA(2) == '\n')) {
/*  228 */         match("\r\n");
/*  229 */         newline();
/*      */       }
/*  231 */       else if ((LA(1) == '/') && (_tokenSet_1.member(LA(2)))) {
/*  232 */         match('/');
/*      */ 
/*  234 */         match(_tokenSet_1);
/*      */       }
/*  237 */       else if (LA(1) == '\r') {
/*  238 */         match('\r');
/*  239 */         newline();
/*      */       }
/*  241 */       else if (_tokenSet_2.member(LA(1)))
/*      */       {
/*  243 */         match(_tokenSet_2);
/*      */       }
/*      */       else
/*      */       {
/*  247 */         throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */       }break;
/*      */     }
/*  250 */     if ((paramBoolean) && (localToken == null) && (i != -1)) {
/*  251 */       localToken = makeToken(i);
/*  252 */       localToken.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/*      */     }
/*  254 */     this._returnToken = localToken;
/*      */   }
/*      */ 
/*      */   protected final void mAST_ITEM(boolean paramBoolean) throws RecognitionException, CharStreamException, TokenStreamException {
/*  258 */     Token localToken1 = null; int j = this.text.length();
/*  259 */     int i = 6;
/*      */ 
/*  261 */     Token localToken2 = null;
/*  262 */     Token localToken3 = null;
/*  263 */     Token localToken4 = null;
/*      */     int k;
/*  265 */     if ((LA(1) == '#') && (LA(2) == '(')) {
/*  266 */       k = this.text.length();
/*  267 */       match('#');
/*  268 */       this.text.setLength(k);
/*  269 */       mTREE(true);
/*  270 */       localToken2 = this._returnToken;
/*      */     }
/*      */     else
/*      */     {
/*      */       String str1;
/*  272 */       if ((LA(1) == '#') && (_tokenSet_3.member(LA(2)))) {
/*  273 */         k = this.text.length();
/*  274 */         match('#');
/*  275 */         this.text.setLength(k);
/*  276 */         mID(true);
/*  277 */         localToken3 = this._returnToken;
/*      */ 
/*  279 */         str1 = localToken3.getText();
/*  280 */         String str2 = this.generator.mapTreeId(str1, this.transInfo);
/*  281 */         if (str2 != null) {
/*  282 */           this.text.setLength(j); this.text.append(str2);
/*      */         }
/*      */ 
/*  286 */         if (_tokenSet_4.member(LA(1))) {
/*  287 */           mWS(false);
/*      */         }
/*      */ 
/*  294 */         if (LA(1) == '=') {
/*  295 */           mVAR_ASSIGN(false);
/*      */         }
/*      */ 
/*      */       }
/*  302 */       else if ((LA(1) == '#') && (LA(2) == '[')) {
/*  303 */         k = this.text.length();
/*  304 */         match('#');
/*  305 */         this.text.setLength(k);
/*  306 */         mAST_CONSTRUCTOR(true);
/*  307 */         localToken4 = this._returnToken;
/*      */       }
/*  309 */       else if ((LA(1) == '#') && (LA(2) == '#')) {
/*  310 */         match("##");
/*      */ 
/*  312 */         str1 = this.currentRule.getRuleName() + "_AST"; this.text.setLength(j); this.text.append(str1);
/*  313 */         if (this.transInfo != null) {
/*  314 */           this.transInfo.refRuleRoot = str1;
/*      */         }
/*      */ 
/*  318 */         if (_tokenSet_4.member(LA(1))) {
/*  319 */           mWS(false);
/*      */         }
/*      */ 
/*  326 */         if (LA(1) == '=') {
/*  327 */           mVAR_ASSIGN(false);
/*      */         }
/*      */ 
/*      */       }
/*      */       else
/*      */       {
/*  335 */         throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */       }
/*      */     }
/*  338 */     if ((paramBoolean) && (localToken1 == null) && (i != -1)) {
/*  339 */       localToken1 = makeToken(i);
/*  340 */       localToken1.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/*      */     }
/*  342 */     this._returnToken = localToken1;
/*      */   }
/*      */ 
/*      */   protected final void mTEXT_ITEM(boolean paramBoolean) throws RecognitionException, CharStreamException, TokenStreamException {
/*  346 */     Token localToken1 = null; int j = this.text.length();
/*  347 */     int i = 7;
/*      */ 
/*  349 */     Token localToken2 = null;
/*  350 */     Token localToken3 = null;
/*  351 */     Token localToken4 = null;
/*  352 */     Token localToken5 = null;
/*  353 */     Token localToken6 = null;
/*  354 */     Token localToken7 = null;
/*      */     String str1;
/*  356 */     if ((LA(1) == '$') && (LA(2) == 's') && (LA(3) == 'e')) {
/*  357 */       match("$set");
/*      */ 
/*  359 */       if ((LA(1) == 'T') && (LA(2) == 'e')) {
/*  360 */         match("Text");
/*      */ 
/*  362 */         switch (LA(1)) { case '\t':
/*      */         case '\n':
/*      */         case '\r':
/*      */         case ' ':
/*  365 */           mWS(false);
/*  366 */           break;
/*      */         case '(':
/*  370 */           break;
/*      */         default:
/*  374 */           throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */         }
/*      */ 
/*  378 */         match('(');
/*  379 */         mTEXT_ARG(true);
/*  380 */         localToken3 = this._returnToken;
/*  381 */         match(')');
/*      */ 
/*  384 */         str1 = "self.text.setLength(_begin) ; self.text.append(" + localToken3.getText() + ")";
/*  385 */         this.text.setLength(j); this.text.append(str1);
/*      */       }
/*  388 */       else if ((LA(1) == 'T') && (LA(2) == 'o')) {
/*  389 */         match("Token");
/*      */ 
/*  391 */         switch (LA(1)) { case '\t':
/*      */         case '\n':
/*      */         case '\r':
/*      */         case ' ':
/*  394 */           mWS(false);
/*  395 */           break;
/*      */         case '(':
/*  399 */           break;
/*      */         default:
/*  403 */           throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */         }
/*      */ 
/*  407 */         match('(');
/*  408 */         mTEXT_ARG(true);
/*  409 */         localToken4 = this._returnToken;
/*  410 */         match(')');
/*      */ 
/*  412 */         str1 = "_token = " + localToken4.getText();
/*  413 */         this.text.setLength(j); this.text.append(str1);
/*      */       }
/*  416 */       else if ((LA(1) == 'T') && (LA(2) == 'y')) {
/*  417 */         match("Type");
/*      */ 
/*  419 */         switch (LA(1)) { case '\t':
/*      */         case '\n':
/*      */         case '\r':
/*      */         case ' ':
/*  422 */           mWS(false);
/*  423 */           break;
/*      */         case '(':
/*  427 */           break;
/*      */         default:
/*  431 */           throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */         }
/*      */ 
/*  435 */         match('(');
/*  436 */         mTEXT_ARG(true);
/*  437 */         localToken5 = this._returnToken;
/*  438 */         match(')');
/*      */ 
/*  440 */         str1 = "_ttype = " + localToken5.getText();
/*  441 */         this.text.setLength(j); this.text.append(str1);
/*      */       }
/*      */       else
/*      */       {
/*  445 */         throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */       }
/*      */     }
/*      */     else
/*      */     {
/*      */       String str2;
/*  450 */       if ((LA(1) == '$') && (LA(2) == 'F') && (LA(3) == 'O')) {
/*  451 */         match("$FOLLOW");
/*      */ 
/*  453 */         if ((_tokenSet_5.member(LA(1))) && (_tokenSet_6.member(LA(2))) && (LA(3) >= '\003') && (LA(3) <= 'ÿ'))
/*      */         {
/*  455 */           switch (LA(1)) { case '\t':
/*      */           case '\n':
/*      */           case '\r':
/*      */           case ' ':
/*  458 */             mWS(false);
/*  459 */             break;
/*      */           case '(':
/*  463 */             break;
/*      */           default:
/*  467 */             throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */           }
/*      */ 
/*  471 */           match('(');
/*  472 */           mTEXT_ARG(true);
/*  473 */           localToken6 = this._returnToken;
/*  474 */           match(')');
/*      */         }
/*      */ 
/*  481 */         str1 = this.currentRule.getRuleName();
/*  482 */         if (localToken6 != null) {
/*  483 */           str1 = localToken6.getText();
/*      */         }
/*  485 */         str2 = this.generator.getFOLLOWBitSet(str1, 1);
/*  486 */         if (str2 == null) {
/*  487 */           reportError("$FOLLOW(" + str1 + ")" + ": unknown rule or bad lookahead computation");
/*      */         }
/*      */         else
/*      */         {
/*  491 */           this.text.setLength(j); this.text.append(str2);
/*      */         }
/*      */ 
/*      */       }
/*  495 */       else if ((LA(1) == '$') && (LA(2) == 'F') && (LA(3) == 'I')) {
/*  496 */         match("$FIRST");
/*      */ 
/*  498 */         if ((_tokenSet_5.member(LA(1))) && (_tokenSet_6.member(LA(2))) && (LA(3) >= '\003') && (LA(3) <= 'ÿ'))
/*      */         {
/*  500 */           switch (LA(1)) { case '\t':
/*      */           case '\n':
/*      */           case '\r':
/*      */           case ' ':
/*  503 */             mWS(false);
/*  504 */             break;
/*      */           case '(':
/*  508 */             break;
/*      */           default:
/*  512 */             throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */           }
/*      */ 
/*  516 */           match('(');
/*  517 */           mTEXT_ARG(true);
/*  518 */           localToken7 = this._returnToken;
/*  519 */           match(')');
/*      */         }
/*      */ 
/*  526 */         str1 = this.currentRule.getRuleName();
/*  527 */         if (localToken7 != null) {
/*  528 */           str1 = localToken7.getText();
/*      */         }
/*  530 */         str2 = this.generator.getFIRSTBitSet(str1, 1);
/*  531 */         if (str2 == null) {
/*  532 */           reportError("$FIRST(" + str1 + ")" + ": unknown rule or bad lookahead computation");
/*      */         }
/*      */         else
/*      */         {
/*  536 */           this.text.setLength(j); this.text.append(str2);
/*      */         }
/*      */ 
/*      */       }
/*  540 */       else if ((LA(1) == '$') && (LA(2) == 's') && (LA(3) == 'k')) {
/*  541 */         match("$skip");
/*      */ 
/*  543 */         this.text.setLength(j); this.text.append("_ttype = SKIP");
/*      */       }
/*  546 */       else if ((LA(1) == '$') && (LA(2) == 'a')) {
/*  547 */         match("$append");
/*      */ 
/*  549 */         switch (LA(1)) { case '\t':
/*      */         case '\n':
/*      */         case '\r':
/*      */         case ' ':
/*  552 */           mWS(false);
/*  553 */           break;
/*      */         case '(':
/*  557 */           break;
/*      */         default:
/*  561 */           throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */         }
/*      */ 
/*  565 */         match('(');
/*  566 */         mTEXT_ARG(true);
/*  567 */         localToken2 = this._returnToken;
/*  568 */         match(')');
/*      */ 
/*  570 */         str1 = "self.text.append(" + localToken2.getText() + ")";
/*  571 */         this.text.setLength(j); this.text.append(str1);
/*      */       }
/*  574 */       else if ((LA(1) == '$') && (LA(2) == 'g')) {
/*  575 */         match("$getText");
/*      */ 
/*  577 */         this.text.setLength(j); this.text.append("self.text.getString(_begin)");
/*      */       }
/*  580 */       else if ((LA(1) == '$') && (LA(2) == 'n'))
/*      */       {
/*  582 */         if ((LA(1) == '$') && (LA(2) == 'n') && (LA(3) == 'l')) {
/*  583 */           match("$nl");
/*      */         }
/*  585 */         else if ((LA(1) == '$') && (LA(2) == 'n') && (LA(3) == 'e')) {
/*  586 */           match("$newline");
/*      */         }
/*      */         else {
/*  589 */           throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */         }
/*      */ 
/*  594 */         this.text.setLength(j); this.text.append("self.newline()");
/*      */       }
/*      */       else
/*      */       {
/*  598 */         throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */       }
/*      */     }
/*  601 */     if ((paramBoolean) && (localToken1 == null) && (i != -1)) {
/*  602 */       localToken1 = makeToken(i);
/*  603 */       localToken1.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/*      */     }
/*  605 */     this._returnToken = localToken1;
/*      */   }
/*      */ 
/*      */   protected final void mCOMMENT(boolean paramBoolean) throws RecognitionException, CharStreamException, TokenStreamException {
/*  609 */     Token localToken = null; int j = this.text.length();
/*  610 */     int i = 19;
/*      */ 
/*  614 */     if ((LA(1) == '/') && (LA(2) == '/')) {
/*  615 */       mSL_COMMENT(false);
/*      */     }
/*  617 */     else if ((LA(1) == '/') && (LA(2) == '*')) {
/*  618 */       mML_COMMENT(false);
/*      */     }
/*      */     else {
/*  621 */       throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */     }
/*      */ 
/*  627 */     if ((paramBoolean) && (localToken == null) && (i != -1)) {
/*  628 */       localToken = makeToken(i);
/*  629 */       localToken.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/*      */     }
/*  631 */     this._returnToken = localToken;
/*      */   }
/*      */ 
/*      */   protected final void mSTRING(boolean paramBoolean) throws RecognitionException, CharStreamException, TokenStreamException {
/*  635 */     Token localToken = null; int j = this.text.length();
/*  636 */     int i = 24;
/*      */ 
/*  639 */     match('"');
/*      */     while (true)
/*      */     {
/*  643 */       if (LA(1) == '\\') {
/*  644 */         mESC(false);
/*      */       } else {
/*  646 */         if (!_tokenSet_7.member(LA(1))) break;
/*  647 */         matchNot('"');
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/*  655 */     match('"');
/*  656 */     if ((paramBoolean) && (localToken == null) && (i != -1)) {
/*  657 */       localToken = makeToken(i);
/*  658 */       localToken.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/*      */     }
/*  660 */     this._returnToken = localToken;
/*      */   }
/*      */ 
/*      */   protected final void mCHAR(boolean paramBoolean) throws RecognitionException, CharStreamException, TokenStreamException {
/*  664 */     Token localToken = null; int j = this.text.length();
/*  665 */     int i = 23;
/*      */ 
/*  668 */     match('\'');
/*      */ 
/*  670 */     if (LA(1) == '\\') {
/*  671 */       mESC(false);
/*      */     }
/*  673 */     else if (_tokenSet_8.member(LA(1))) {
/*  674 */       matchNot('\'');
/*      */     }
/*      */     else {
/*  677 */       throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */     }
/*      */ 
/*  681 */     match('\'');
/*  682 */     if ((paramBoolean) && (localToken == null) && (i != -1)) {
/*  683 */       localToken = makeToken(i);
/*  684 */       localToken.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/*      */     }
/*  686 */     this._returnToken = localToken;
/*      */   }
/*      */ 
/*      */   protected final void mTREE(boolean paramBoolean) throws RecognitionException, CharStreamException, TokenStreamException {
/*  690 */     Token localToken1 = null; int j = this.text.length();
/*  691 */     int i = 8;
/*      */ 
/*  693 */     Token localToken2 = null;
/*  694 */     Token localToken3 = null;
/*      */ 
/*  696 */     StringBuffer localStringBuffer = new StringBuffer();
/*  697 */     int m = 0;
/*  698 */     Vector localVector = new Vector(10);
/*      */ 
/*  701 */     int k = this.text.length();
/*  702 */     match('(');
/*  703 */     this.text.setLength(k);
/*      */ 
/*  705 */     switch (LA(1)) { case '\t':
/*      */     case '\n':
/*      */     case '\r':
/*      */     case ' ':
/*  708 */       k = this.text.length();
/*  709 */       mWS(false);
/*  710 */       this.text.setLength(k);
/*  711 */       break;
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
/*  729 */       break;
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
/*  733 */       throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */     }
/*      */ 
/*  737 */     k = this.text.length();
/*  738 */     mTREE_ELEMENT(true);
/*  739 */     this.text.setLength(k);
/*  740 */     localToken2 = this._returnToken;
/*  741 */     localVector.appendElement(localToken2.getText());
/*      */ 
/*  743 */     switch (LA(1)) { case '\t':
/*      */     case '\n':
/*      */     case '\r':
/*      */     case ' ':
/*  746 */       k = this.text.length();
/*  747 */       mWS(false);
/*  748 */       this.text.setLength(k);
/*  749 */       break;
/*      */     case ')':
/*      */     case ',':
/*  753 */       break;
/*      */     default:
/*  757 */       throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */     }
/*      */ 
/*  764 */     while (LA(1) == ',') {
/*  765 */       k = this.text.length();
/*  766 */       match(',');
/*  767 */       this.text.setLength(k);
/*      */ 
/*  769 */       switch (LA(1)) { case '\t':
/*      */       case '\n':
/*      */       case '\r':
/*      */       case ' ':
/*  772 */         k = this.text.length();
/*  773 */         mWS(false);
/*  774 */         this.text.setLength(k);
/*  775 */         break;
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
/*  793 */         break;
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
/*  797 */         throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */       }
/*      */ 
/*  801 */       k = this.text.length();
/*  802 */       mTREE_ELEMENT(true);
/*  803 */       this.text.setLength(k);
/*  804 */       localToken3 = this._returnToken;
/*  805 */       localVector.appendElement(localToken3.getText());
/*      */ 
/*  807 */       switch (LA(1)) { case '\t':
/*      */       case '\n':
/*      */       case '\r':
/*      */       case ' ':
/*  810 */         k = this.text.length();
/*  811 */         mWS(false);
/*  812 */         this.text.setLength(k);
/*  813 */         break;
/*      */       case ')':
/*      */       case ',':
/*  817 */         break;
/*      */       default:
/*  821 */         throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/*  832 */     this.text.setLength(j); this.text.append(this.generator.getASTCreateString(localVector));
/*  833 */     k = this.text.length();
/*  834 */     match(')');
/*  835 */     this.text.setLength(k);
/*  836 */     if ((paramBoolean) && (localToken1 == null) && (i != -1)) {
/*  837 */       localToken1 = makeToken(i);
/*  838 */       localToken1.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/*      */     }
/*  840 */     this._returnToken = localToken1;
/*      */   }
/*      */ 
/*      */   protected final void mID(boolean paramBoolean) throws RecognitionException, CharStreamException, TokenStreamException {
/*  844 */     Token localToken = null; int j = this.text.length();
/*  845 */     int i = 17;
/*      */ 
/*  849 */     switch (LA(1)) { case 'a':
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
/*  858 */       matchRange('a', 'z');
/*  859 */       break;
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
/*  869 */       matchRange('A', 'Z');
/*  870 */       break;
/*      */     case '_':
/*  874 */       match('_');
/*  875 */       break;
/*      */     case '[':
/*      */     case '\\':
/*      */     case ']':
/*      */     case '^':
/*      */     case '`':
/*      */     default:
/*  879 */       throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */     }
/*      */ 
/*  886 */     while (_tokenSet_9.member(LA(1)))
/*      */     {
/*  888 */       switch (LA(1)) { case 'a':
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
/*  897 */         matchRange('a', 'z');
/*  898 */         break;
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
/*  908 */         matchRange('A', 'Z');
/*  909 */         break;
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
/*  915 */         matchRange('0', '9');
/*  916 */         break;
/*      */       case '_':
/*  920 */         match('_');
/*  921 */         break;
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
/*  925 */         throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/*  936 */     if ((paramBoolean) && (localToken == null) && (i != -1)) {
/*  937 */       localToken = makeToken(i);
/*  938 */       localToken.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/*      */     }
/*  940 */     this._returnToken = localToken;
/*      */   }
/*      */ 
/*      */   protected final void mWS(boolean paramBoolean) throws RecognitionException, CharStreamException, TokenStreamException {
/*  944 */     Token localToken = null; int j = this.text.length();
/*  945 */     int i = 29;
/*      */ 
/*  949 */     int k = 0;
/*      */     while (true)
/*      */     {
/*  952 */       if ((LA(1) == '\r') && (LA(2) == '\n')) {
/*  953 */         match('\r');
/*  954 */         match('\n');
/*  955 */         newline();
/*      */       }
/*  957 */       else if (LA(1) == ' ') {
/*  958 */         match(' ');
/*      */       }
/*  960 */       else if (LA(1) == '\t') {
/*  961 */         match('\t');
/*      */       }
/*  963 */       else if (LA(1) == '\r') {
/*  964 */         match('\r');
/*  965 */         newline();
/*      */       }
/*  967 */       else if (LA(1) == '\n') {
/*  968 */         match('\n');
/*  969 */         newline();
/*      */       }
/*      */       else {
/*  972 */         if (k >= 1) break; throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */       }
/*      */ 
/*  975 */       k++;
/*      */     }
/*      */ 
/*  978 */     if ((paramBoolean) && (localToken == null) && (i != -1)) {
/*  979 */       localToken = makeToken(i);
/*  980 */       localToken.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/*      */     }
/*  982 */     this._returnToken = localToken;
/*      */   }
/*      */ 
/*      */   protected final void mVAR_ASSIGN(boolean paramBoolean) throws RecognitionException, CharStreamException, TokenStreamException {
/*  986 */     Token localToken = null; int j = this.text.length();
/*  987 */     int i = 18;
/*      */ 
/*  990 */     match('=');
/*      */ 
/*  994 */     if ((LA(1) != '=') && (this.transInfo != null) && (this.transInfo.refRuleRoot != null)) {
/*  995 */       this.transInfo.assignToRoot = true;
/*      */     }
/*      */ 
/*  998 */     if ((paramBoolean) && (localToken == null) && (i != -1)) {
/*  999 */       localToken = makeToken(i);
/* 1000 */       localToken.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/*      */     }
/* 1002 */     this._returnToken = localToken;
/*      */   }
/*      */ 
/*      */   protected final void mAST_CONSTRUCTOR(boolean paramBoolean) throws RecognitionException, CharStreamException, TokenStreamException {
/* 1006 */     Token localToken1 = null; int j = this.text.length();
/* 1007 */     int i = 10;
/*      */ 
/* 1009 */     Token localToken2 = null;
/* 1010 */     Token localToken3 = null;
/* 1011 */     Token localToken4 = null;
/*      */ 
/* 1013 */     int k = this.text.length();
/* 1014 */     match('[');
/* 1015 */     this.text.setLength(k);
/*      */ 
/* 1017 */     switch (LA(1)) { case '\t':
/*      */     case '\n':
/*      */     case '\r':
/*      */     case ' ':
/* 1020 */       k = this.text.length();
/* 1021 */       mWS(false);
/* 1022 */       this.text.setLength(k);
/* 1023 */       break;
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
/* 1043 */       break;
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
/* 1047 */       throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */     }
/*      */ 
/* 1051 */     k = this.text.length();
/* 1052 */     mAST_CTOR_ELEMENT(true);
/* 1053 */     this.text.setLength(k);
/* 1054 */     localToken2 = this._returnToken;
/*      */ 
/* 1056 */     switch (LA(1)) { case '\t':
/*      */     case '\n':
/*      */     case '\r':
/*      */     case ' ':
/* 1059 */       k = this.text.length();
/* 1060 */       mWS(false);
/* 1061 */       this.text.setLength(k);
/* 1062 */       break;
/*      */     case ',':
/*      */     case ']':
/* 1066 */       break;
/*      */     default:
/* 1070 */       throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */     }
/*      */ 
/* 1075 */     if ((LA(1) == ',') && (_tokenSet_10.member(LA(2))) && (LA(3) >= '\003') && (LA(3) <= 'ÿ')) {
/* 1076 */       k = this.text.length();
/* 1077 */       match(',');
/* 1078 */       this.text.setLength(k);
/*      */ 
/* 1080 */       switch (LA(1)) { case '\t':
/*      */       case '\n':
/*      */       case '\r':
/*      */       case ' ':
/* 1083 */         k = this.text.length();
/* 1084 */         mWS(false);
/* 1085 */         this.text.setLength(k);
/* 1086 */         break;
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
/* 1106 */         break;
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
/* 1110 */         throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */       }
/*      */ 
/* 1114 */       k = this.text.length();
/* 1115 */       mAST_CTOR_ELEMENT(true);
/* 1116 */       this.text.setLength(k);
/* 1117 */       localToken3 = this._returnToken;
/*      */     }
/* 1119 */     switch (LA(1)) { case '\t':
/*      */     case '\n':
/*      */     case '\r':
/*      */     case ' ':
/* 1122 */       k = this.text.length();
/* 1123 */       mWS(false);
/* 1124 */       this.text.setLength(k);
/* 1125 */       break;
/*      */     case ',':
/*      */     case ']':
/* 1129 */       break;
/*      */     default:
/* 1133 */       throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */ 
/* 1138 */       if ((LA(1) != ',') && (LA(1) != ']'))
/*      */       {
/* 1141 */         throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */       }
/*      */       break;
/*      */     }
/*      */ 
/* 1146 */     switch (LA(1))
/*      */     {
/*      */     case ',':
/* 1149 */       k = this.text.length();
/* 1150 */       match(',');
/* 1151 */       this.text.setLength(k);
/*      */ 
/* 1153 */       switch (LA(1)) { case '\t':
/*      */       case '\n':
/*      */       case '\r':
/*      */       case ' ':
/* 1156 */         k = this.text.length();
/* 1157 */         mWS(false);
/* 1158 */         this.text.setLength(k);
/* 1159 */         break;
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
/* 1179 */         break;
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
/* 1183 */         throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */       }
/*      */ 
/* 1187 */       k = this.text.length();
/* 1188 */       mAST_CTOR_ELEMENT(true);
/* 1189 */       this.text.setLength(k);
/* 1190 */       localToken4 = this._returnToken;
/*      */ 
/* 1192 */       switch (LA(1)) { case '\t':
/*      */       case '\n':
/*      */       case '\r':
/*      */       case ' ':
/* 1195 */         k = this.text.length();
/* 1196 */         mWS(false);
/* 1197 */         this.text.setLength(k);
/* 1198 */         break;
/*      */       case ']':
/* 1202 */         break;
/*      */       default:
/* 1206 */         throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */       }
/*      */ 
/*      */       break;
/*      */     case ']':
/* 1214 */       break;
/*      */     default:
/* 1218 */       throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */     }
/*      */ 
/* 1222 */     k = this.text.length();
/* 1223 */     match(']');
/* 1224 */     this.text.setLength(k);
/*      */ 
/* 1226 */     String str = localToken2.getText();
/* 1227 */     if (localToken3 != null) {
/* 1228 */       str = str + "," + localToken3.getText();
/*      */     }
/* 1230 */     if (localToken4 != null) {
/* 1231 */       str = str + "," + localToken4.getText();
/*      */     }
/* 1233 */     this.text.setLength(j); this.text.append(this.generator.getASTCreateString(null, str));
/*      */ 
/* 1235 */     if ((paramBoolean) && (localToken1 == null) && (i != -1)) {
/* 1236 */       localToken1 = makeToken(i);
/* 1237 */       localToken1.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/*      */     }
/* 1239 */     this._returnToken = localToken1;
/*      */   }
/*      */ 
/*      */   protected final void mTEXT_ARG(boolean paramBoolean) throws RecognitionException, CharStreamException, TokenStreamException {
/* 1243 */     Token localToken = null; int j = this.text.length();
/* 1244 */     int i = 13;
/*      */ 
/* 1248 */     switch (LA(1)) { case '\t':
/*      */     case '\n':
/*      */     case '\r':
/*      */     case ' ':
/* 1251 */       mWS(false);
/* 1252 */       break;
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
/* 1272 */       break;
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
/* 1276 */       throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */     }
/*      */ 
/* 1281 */     int k = 0;
/*      */     while (true)
/*      */     {
/* 1284 */       if ((_tokenSet_11.member(LA(1))) && (LA(2) >= '\003') && (LA(2) <= 'ÿ')) {
/* 1285 */         mTEXT_ARG_ELEMENT(false);
/*      */ 
/* 1287 */         if ((_tokenSet_4.member(LA(1))) && (_tokenSet_12.member(LA(2)))) {
/* 1288 */           mWS(false);
/*      */         }
/* 1290 */         else if (!_tokenSet_12.member(LA(1)))
/*      */         {
/* 1293 */           throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */         }
/*      */ 
/*      */       }
/*      */       else
/*      */       {
/* 1299 */         if (k >= 1) break; throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */       }
/*      */ 
/* 1302 */       k++;
/*      */     }
/*      */ 
/* 1305 */     if ((paramBoolean) && (localToken == null) && (i != -1)) {
/* 1306 */       localToken = makeToken(i);
/* 1307 */       localToken.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/*      */     }
/* 1309 */     this._returnToken = localToken;
/*      */   }
/*      */ 
/*      */   protected final void mTREE_ELEMENT(boolean paramBoolean) throws RecognitionException, CharStreamException, TokenStreamException {
/* 1313 */     Token localToken1 = null; int j = this.text.length();
/* 1314 */     int i = 9;
/*      */ 
/* 1316 */     Token localToken2 = null;
/*      */ 
/* 1319 */     switch (LA(1))
/*      */     {
/*      */     case '(':
/* 1322 */       mTREE(false);
/* 1323 */       break;
/*      */     case '[':
/* 1327 */       mAST_CONSTRUCTOR(false);
/* 1328 */       break;
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
/* 1345 */       mID_ELEMENT(false);
/* 1346 */       break;
/*      */     case '"':
/* 1350 */       mSTRING(false);
/* 1351 */       break;
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
/* 1354 */       if ((LA(1) == '#') && (LA(2) == '(')) {
/* 1355 */         k = this.text.length();
/* 1356 */         match('#');
/* 1357 */         this.text.setLength(k);
/* 1358 */         mTREE(false);
/*      */       }
/* 1360 */       else if ((LA(1) == '#') && (LA(2) == '[')) {
/* 1361 */         k = this.text.length();
/* 1362 */         match('#');
/* 1363 */         this.text.setLength(k);
/* 1364 */         mAST_CONSTRUCTOR(false);
/*      */       }
/*      */       else
/*      */       {
/*      */         String str;
/* 1366 */         if ((LA(1) == '#') && (_tokenSet_3.member(LA(2)))) {
/* 1367 */           k = this.text.length();
/* 1368 */           match('#');
/* 1369 */           this.text.setLength(k);
/* 1370 */           boolean bool = mID_ELEMENT(true);
/* 1371 */           localToken2 = this._returnToken;
/*      */ 
/* 1373 */           if (!bool)
/*      */           {
/* 1375 */             str = this.generator.mapTreeId(localToken2.getText(), null);
/* 1376 */             this.text.setLength(j); this.text.append(str);
/*      */           }
/*      */ 
/*      */         }
/* 1380 */         else if ((LA(1) == '#') && (LA(2) == '#')) {
/* 1381 */           match("##");
/* 1382 */           str = this.currentRule.getRuleName() + "_AST"; this.text.setLength(j); this.text.append(str);
/*      */         }
/*      */         else {
/* 1385 */           throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn()); } 
/*      */       }break;
/*      */     }
/* 1388 */     if ((paramBoolean) && (localToken1 == null) && (i != -1)) {
/* 1389 */       localToken1 = makeToken(i);
/* 1390 */       localToken1.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/*      */     }
/* 1392 */     this._returnToken = localToken1;
/*      */   }
/*      */ 
/*      */   protected final boolean mID_ELEMENT(boolean paramBoolean)
/*      */     throws RecognitionException, CharStreamException, TokenStreamException
/*      */   {
/* 1399 */     boolean bool = false;
/* 1400 */     Token localToken1 = null; int j = this.text.length();
/* 1401 */     int i = 12;
/*      */ 
/* 1403 */     Token localToken2 = null;
/*      */ 
/* 1405 */     mID(true);
/* 1406 */     localToken2 = this._returnToken;
/*      */     int k;
/* 1408 */     if ((_tokenSet_4.member(LA(1))) && (_tokenSet_13.member(LA(2)))) {
/* 1409 */       k = this.text.length();
/* 1410 */       mWS(false);
/* 1411 */       this.text.setLength(k);
/*      */     }
/* 1413 */     else if (!_tokenSet_13.member(LA(1)))
/*      */     {
/* 1416 */       throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */     }
/*      */ 
/* 1421 */     switch (LA(1))
/*      */     {
/*      */     case '(':
/* 1424 */       match('(');
/*      */ 
/* 1426 */       if ((_tokenSet_4.member(LA(1))) && (_tokenSet_14.member(LA(2))) && (LA(3) >= '\003') && (LA(3) <= 'ÿ')) {
/* 1427 */         k = this.text.length();
/* 1428 */         mWS(false);
/* 1429 */         this.text.setLength(k);
/*      */       }
/* 1431 */       else if ((!_tokenSet_14.member(LA(1))) || (LA(2) < '\003') || (LA(2) > 'ÿ'))
/*      */       {
/* 1434 */         throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */       }
/*      */ 
/* 1439 */       switch (LA(1)) { case '"':
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
/* 1458 */         mARG(false);
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
/* 1462 */       case '`': } while (LA(1) == ',') {
/* 1463 */         match(',');
/*      */ 
/* 1465 */         switch (LA(1)) { case '\t':
/*      */         case '\n':
/*      */         case '\r':
/*      */         case ' ':
/* 1468 */           k = this.text.length();
/* 1469 */           mWS(false);
/* 1470 */           this.text.setLength(k);
/* 1471 */           break;
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
/* 1491 */           break;
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
/* 1495 */           throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */         }
/*      */ 
/* 1499 */         mARG(false); continue;
/*      */ 
/* 1512 */         break;
/*      */ 
/* 1516 */         throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */       }
/*      */ 
/* 1521 */       switch (LA(1)) { case '\t':
/*      */       case '\n':
/*      */       case '\r':
/*      */       case ' ':
/* 1524 */         k = this.text.length();
/* 1525 */         mWS(false);
/* 1526 */         this.text.setLength(k);
/* 1527 */         break;
/*      */       case ')':
/* 1531 */         break;
/*      */       default:
/* 1535 */         throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */       }
/*      */ 
/* 1539 */       match(')');
/* 1540 */       break;
/*      */     case '[':
/* 1545 */       int m = 0;
/*      */       while (true)
/*      */       {
/* 1548 */         if (LA(1) == '[') {
/* 1549 */           match('[');
/*      */ 
/* 1551 */           switch (LA(1)) { case '\t':
/*      */           case '\n':
/*      */           case '\r':
/*      */           case ' ':
/* 1554 */             k = this.text.length();
/* 1555 */             mWS(false);
/* 1556 */             this.text.setLength(k);
/* 1557 */             break;
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
/* 1577 */             break;
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
/* 1581 */             throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */           }
/*      */ 
/* 1585 */           mARG(false);
/*      */ 
/* 1587 */           switch (LA(1)) { case '\t':
/*      */           case '\n':
/*      */           case '\r':
/*      */           case ' ':
/* 1590 */             k = this.text.length();
/* 1591 */             mWS(false);
/* 1592 */             this.text.setLength(k);
/* 1593 */             break;
/*      */           case ']':
/* 1597 */             break;
/*      */           default:
/* 1601 */             throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */           }
/*      */ 
/* 1605 */           match(']');
/*      */         }
/*      */         else {
/* 1608 */           if (m >= 1) break; throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */         }
/*      */ 
/* 1611 */         m++;
/*      */       }
/*      */ 
/*      */     case '.':
/* 1618 */       match('.');
/* 1619 */       mID_ELEMENT(false);
/* 1620 */       break;
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
/* 1627 */       bool = true;
/* 1628 */       String str = this.generator.mapTreeId(localToken2.getText(), this.transInfo);
/* 1629 */       this.text.setLength(j); this.text.append(str);
/*      */ 
/* 1632 */       if ((_tokenSet_15.member(LA(1))) && (_tokenSet_16.member(LA(2))) && (this.transInfo != null) && (this.transInfo.refRuleRoot != null))
/*      */       {
/* 1634 */         switch (LA(1)) { case '\t':
/*      */         case '\n':
/*      */         case '\r':
/*      */         case ' ':
/* 1637 */           mWS(false);
/* 1638 */           break;
/*      */         case '=':
/* 1642 */           break;
/*      */         default:
/* 1646 */           throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */         }
/*      */ 
/* 1650 */         mVAR_ASSIGN(false);
/*      */       }
/* 1652 */       else if (!_tokenSet_17.member(LA(1)))
/*      */       {
/* 1655 */         throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */       }
/*      */ 
/*      */       break;
/*      */     default:
/* 1663 */       throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */     }
/*      */ 
/* 1667 */     if ((paramBoolean) && (localToken1 == null) && (i != -1)) {
/* 1668 */       localToken1 = makeToken(i);
/* 1669 */       localToken1.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/*      */     }
/* 1671 */     this._returnToken = localToken1;
/* 1672 */     return bool;
/*      */   }
/*      */ 
/*      */   protected final void mAST_CTOR_ELEMENT(boolean paramBoolean)
/*      */     throws RecognitionException, CharStreamException, TokenStreamException
/*      */   {
/* 1679 */     Token localToken = null; int j = this.text.length();
/* 1680 */     int i = 11;
/*      */ 
/* 1683 */     if ((LA(1) == '"') && (LA(2) >= '\003') && (LA(2) <= 'ÿ') && (LA(3) >= '\003') && (LA(3) <= 'ÿ')) {
/* 1684 */       mSTRING(false);
/*      */     }
/* 1686 */     else if ((_tokenSet_18.member(LA(1))) && (LA(2) >= '\003') && (LA(2) <= 'ÿ')) {
/* 1687 */       mTREE_ELEMENT(false);
/*      */     }
/* 1689 */     else if ((LA(1) >= '0') && (LA(1) <= '9')) {
/* 1690 */       mINT(false);
/*      */     }
/*      */     else {
/* 1693 */       throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */     }
/*      */ 
/* 1696 */     if ((paramBoolean) && (localToken == null) && (i != -1)) {
/* 1697 */       localToken = makeToken(i);
/* 1698 */       localToken.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/*      */     }
/* 1700 */     this._returnToken = localToken;
/*      */   }
/*      */ 
/*      */   protected final void mINT(boolean paramBoolean) throws RecognitionException, CharStreamException, TokenStreamException {
/* 1704 */     Token localToken = null; int j = this.text.length();
/* 1705 */     int i = 27;
/*      */ 
/* 1709 */     int k = 0;
/*      */     while (true)
/*      */     {
/* 1712 */       if ((LA(1) >= '0') && (LA(1) <= '9')) {
/* 1713 */         mDIGIT(false);
/*      */       }
/*      */       else {
/* 1716 */         if (k >= 1) break; throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */       }
/*      */ 
/* 1719 */       k++;
/*      */     }
/*      */ 
/* 1722 */     if ((paramBoolean) && (localToken == null) && (i != -1)) {
/* 1723 */       localToken = makeToken(i);
/* 1724 */       localToken.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/*      */     }
/* 1726 */     this._returnToken = localToken;
/*      */   }
/*      */ 
/*      */   protected final void mARG(boolean paramBoolean) throws RecognitionException, CharStreamException, TokenStreamException {
/* 1730 */     Token localToken = null; int j = this.text.length();
/* 1731 */     int i = 16;
/*      */ 
/* 1735 */     switch (LA(1))
/*      */     {
/*      */     case '\'':
/* 1738 */       mCHAR(false);
/* 1739 */       break;
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
/* 1745 */       mINT_OR_FLOAT(false);
/* 1746 */       break;
/*      */     case '(':
/*      */     case ')':
/*      */     case '*':
/*      */     case '+':
/*      */     case ',':
/*      */     case '-':
/*      */     case '.':
/*      */     case '/':
/*      */     default:
/* 1749 */       if ((_tokenSet_18.member(LA(1))) && (LA(2) >= '\003') && (LA(2) <= 'ÿ') && (LA(3) >= '\003') && (LA(3) <= 'ÿ')) {
/* 1750 */         mTREE_ELEMENT(false);
/*      */       }
/* 1752 */       else if ((LA(1) == '"') && (LA(2) >= '\003') && (LA(2) <= 'ÿ') && (LA(3) >= '\003') && (LA(3) <= 'ÿ')) {
/* 1753 */         mSTRING(false);
/*      */       }
/*      */       else {
/* 1756 */         throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */       }
/*      */ 
/*      */       break;
/*      */     }
/*      */ 
/* 1763 */     while ((_tokenSet_19.member(LA(1))) && (_tokenSet_20.member(LA(2))) && (LA(3) >= '\003') && (LA(3) <= 'ÿ'))
/*      */     {
/* 1765 */       switch (LA(1)) { case '\t':
/*      */       case '\n':
/*      */       case '\r':
/*      */       case ' ':
/* 1768 */         mWS(false);
/* 1769 */         break;
/*      */       case '*':
/*      */       case '+':
/*      */       case '-':
/*      */       case '/':
/* 1773 */         break;
/*      */       default:
/* 1777 */         throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */       }
/*      */ 
/* 1782 */       switch (LA(1))
/*      */       {
/*      */       case '+':
/* 1785 */         match('+');
/* 1786 */         break;
/*      */       case '-':
/* 1790 */         match('-');
/* 1791 */         break;
/*      */       case '*':
/* 1795 */         match('*');
/* 1796 */         break;
/*      */       case '/':
/* 1800 */         match('/');
/* 1801 */         break;
/*      */       case ',':
/*      */       case '.':
/*      */       default:
/* 1805 */         throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */       }
/*      */ 
/* 1810 */       switch (LA(1)) { case '\t':
/*      */       case '\n':
/*      */       case '\r':
/*      */       case ' ':
/* 1813 */         mWS(false);
/* 1814 */         break;
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
/* 1834 */         break;
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
/* 1838 */         throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */       }
/*      */ 
/* 1842 */       mARG(false);
/*      */     }
/*      */ 
/* 1850 */     if ((paramBoolean) && (localToken == null) && (i != -1)) {
/* 1851 */       localToken = makeToken(i);
/* 1852 */       localToken.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/*      */     }
/* 1854 */     this._returnToken = localToken;
/*      */   }
/*      */ 
/*      */   protected final void mTEXT_ARG_ELEMENT(boolean paramBoolean) throws RecognitionException, CharStreamException, TokenStreamException {
/* 1858 */     Token localToken = null; int j = this.text.length();
/* 1859 */     int i = 14;
/*      */ 
/* 1862 */     switch (LA(1)) { case 'A':
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
/* 1878 */       mTEXT_ARG_ID_ELEMENT(false);
/* 1879 */       break;
/*      */     case '"':
/* 1883 */       mSTRING(false);
/* 1884 */       break;
/*      */     case '\'':
/* 1888 */       mCHAR(false);
/* 1889 */       break;
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
/* 1895 */       mINT_OR_FLOAT(false);
/* 1896 */       break;
/*      */     case '$':
/* 1900 */       mTEXT_ITEM(false);
/* 1901 */       break;
/*      */     case '+':
/* 1905 */       match('+');
/* 1906 */       break;
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
/* 1910 */       throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */     }
/*      */ 
/* 1913 */     if ((paramBoolean) && (localToken == null) && (i != -1)) {
/* 1914 */       localToken = makeToken(i);
/* 1915 */       localToken.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/*      */     }
/* 1917 */     this._returnToken = localToken;
/*      */   }
/*      */ 
/*      */   protected final void mTEXT_ARG_ID_ELEMENT(boolean paramBoolean) throws RecognitionException, CharStreamException, TokenStreamException {
/* 1921 */     Token localToken1 = null; int j = this.text.length();
/* 1922 */     int i = 15;
/*      */ 
/* 1924 */     Token localToken2 = null;
/*      */ 
/* 1926 */     mID(true);
/* 1927 */     localToken2 = this._returnToken;
/*      */     int k;
/* 1929 */     if ((_tokenSet_4.member(LA(1))) && (_tokenSet_21.member(LA(2)))) {
/* 1930 */       k = this.text.length();
/* 1931 */       mWS(false);
/* 1932 */       this.text.setLength(k);
/*      */     }
/* 1934 */     else if (!_tokenSet_21.member(LA(1)))
/*      */     {
/* 1937 */       throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */     }
/*      */ 
/* 1942 */     switch (LA(1))
/*      */     {
/*      */     case '(':
/* 1945 */       match('(');
/*      */ 
/* 1947 */       if ((_tokenSet_4.member(LA(1))) && (_tokenSet_22.member(LA(2))) && (LA(3) >= '\003') && (LA(3) <= 'ÿ')) {
/* 1948 */         k = this.text.length();
/* 1949 */         mWS(false);
/* 1950 */         this.text.setLength(k);
/*      */       }
/* 1952 */       else if ((!_tokenSet_22.member(LA(1))) || (LA(2) < '\003') || (LA(2) > 'ÿ'))
/*      */       {
/* 1955 */         throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */       }
/*      */ 
/* 1962 */       if ((_tokenSet_23.member(LA(1))) && (LA(2) >= '\003') && (LA(2) <= 'ÿ') && (LA(3) >= '\003') && (LA(3) <= 'ÿ')) {
/* 1963 */         mTEXT_ARG(false);
/*      */ 
/* 1967 */         while (LA(1) == ',') {
/* 1968 */           match(',');
/* 1969 */           mTEXT_ARG(false);
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/* 1985 */       switch (LA(1)) { case '\t':
/*      */       case '\n':
/*      */       case '\r':
/*      */       case ' ':
/* 1988 */         k = this.text.length();
/* 1989 */         mWS(false);
/* 1990 */         this.text.setLength(k);
/* 1991 */         break;
/*      */       case ')':
/* 1995 */         break;
/*      */       default:
/* 1999 */         throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */       }
/*      */ 
/* 2003 */       match(')');
/* 2004 */       break;
/*      */     case '[':
/* 2009 */       int m = 0;
/*      */       while (true)
/*      */       {
/* 2012 */         if (LA(1) == '[') {
/* 2013 */           match('[');
/*      */ 
/* 2015 */           if ((_tokenSet_4.member(LA(1))) && (_tokenSet_23.member(LA(2))) && (LA(3) >= '\003') && (LA(3) <= 'ÿ')) {
/* 2016 */             k = this.text.length();
/* 2017 */             mWS(false);
/* 2018 */             this.text.setLength(k);
/*      */           }
/* 2020 */           else if ((!_tokenSet_23.member(LA(1))) || (LA(2) < '\003') || (LA(2) > 'ÿ') || (LA(3) < '\003') || (LA(3) > 'ÿ'))
/*      */           {
/* 2023 */             throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */           }
/*      */ 
/* 2027 */           mTEXT_ARG(false);
/*      */ 
/* 2029 */           switch (LA(1)) { case '\t':
/*      */           case '\n':
/*      */           case '\r':
/*      */           case ' ':
/* 2032 */             k = this.text.length();
/* 2033 */             mWS(false);
/* 2034 */             this.text.setLength(k);
/* 2035 */             break;
/*      */           case ']':
/* 2039 */             break;
/*      */           default:
/* 2043 */             throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */           }
/*      */ 
/* 2047 */           match(']');
/*      */         }
/*      */         else {
/* 2050 */           if (m >= 1) break; throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */         }
/*      */ 
/* 2053 */         m++;
/*      */       }
/*      */ 
/*      */     case '.':
/* 2060 */       match('.');
/* 2061 */       mTEXT_ARG_ID_ELEMENT(false);
/* 2062 */       break;
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
/* 2084 */       break;
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
/* 2088 */       throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */     }
/*      */ 
/* 2092 */     if ((paramBoolean) && (localToken1 == null) && (i != -1)) {
/* 2093 */       localToken1 = makeToken(i);
/* 2094 */       localToken1.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/*      */     }
/* 2096 */     this._returnToken = localToken1;
/*      */   }
/*      */ 
/*      */   protected final void mINT_OR_FLOAT(boolean paramBoolean) throws RecognitionException, CharStreamException, TokenStreamException {
/* 2100 */     Token localToken = null; int j = this.text.length();
/* 2101 */     int i = 28;
/*      */ 
/* 2105 */     int k = 0;
/*      */     while (true)
/*      */     {
/* 2108 */       if ((LA(1) >= '0') && (LA(1) <= '9') && (_tokenSet_24.member(LA(2)))) {
/* 2109 */         mDIGIT(false);
/*      */       }
/*      */       else {
/* 2112 */         if (k >= 1) break; throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */       }
/*      */ 
/* 2115 */       k++;
/*      */     }
/*      */ 
/* 2119 */     if ((LA(1) == 'L') && (_tokenSet_25.member(LA(2)))) {
/* 2120 */       match('L');
/*      */     }
/* 2122 */     else if ((LA(1) == 'l') && (_tokenSet_25.member(LA(2)))) {
/* 2123 */       match('l');
/*      */     } else {
/* 2125 */       if (LA(1) == '.') {
/* 2126 */         match('.');
/*      */ 
/* 2130 */         while ((LA(1) >= '0') && (LA(1) <= '9') && (_tokenSet_25.member(LA(2)))) {
/* 2131 */           mDIGIT(false);
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/* 2140 */       if (!_tokenSet_25.member(LA(1)))
/*      */       {
/* 2143 */         throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */       }
/*      */     }
/*      */ 
/* 2147 */     if ((paramBoolean) && (localToken == null) && (i != -1)) {
/* 2148 */       localToken = makeToken(i);
/* 2149 */       localToken.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/*      */     }
/* 2151 */     this._returnToken = localToken;
/*      */   }
/*      */ 
/*      */   protected final void mSL_COMMENT(boolean paramBoolean) throws RecognitionException, CharStreamException, TokenStreamException {
/* 2155 */     Token localToken = null; int j = this.text.length();
/* 2156 */     int i = 20;
/*      */ 
/* 2159 */     match("//");
/*      */ 
/* 2162 */     this.text.setLength(j); this.text.append("#");
/*      */ 
/* 2168 */     while ((LA(1) != '\n') && (LA(1) != '\r') && 
/* 2169 */       (LA(1) >= '\003') && (LA(1) <= 'ÿ') && (LA(2) >= '\003') && (LA(2) <= 'ÿ')) {
/* 2170 */       matchNot(65535);
/*      */     }
/*      */ 
/* 2182 */     if ((LA(1) == '\r') && (LA(2) == '\n')) {
/* 2183 */       match("\r\n");
/*      */     }
/* 2185 */     else if (LA(1) == '\n') {
/* 2186 */       match('\n');
/*      */     }
/* 2188 */     else if (LA(1) == '\r') {
/* 2189 */       match('\r');
/*      */     }
/*      */     else {
/* 2192 */       throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */     }
/*      */ 
/* 2197 */     newline();
/*      */ 
/* 2199 */     if ((paramBoolean) && (localToken == null) && (i != -1)) {
/* 2200 */       localToken = makeToken(i);
/* 2201 */       localToken.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/*      */     }
/* 2203 */     this._returnToken = localToken;
/*      */   }
/*      */ 
/*      */   protected final void mML_COMMENT(boolean paramBoolean) throws RecognitionException, CharStreamException, TokenStreamException {
/* 2207 */     Token localToken = null; int j = this.text.length();
/* 2208 */     int i = 22;
/*      */ 
/* 2211 */     match("/*");
/*      */ 
/* 2214 */     this.text.setLength(j); this.text.append("#");
/*      */ 
/* 2220 */     while ((LA(1) != '*') || (LA(2) != '/')) {
/* 2221 */       if ((LA(1) == '\r') && (LA(2) == '\n') && (LA(3) >= '\003') && (LA(3) <= 'ÿ')) {
/* 2222 */         match('\r');
/* 2223 */         match('\n');
/* 2224 */         k = this.text.length();
/* 2225 */         mIGNWS(false);
/* 2226 */         this.text.setLength(k);
/*      */ 
/* 2228 */         newline();
/* 2229 */         this.text.append("# ");
/*      */       }
/* 2232 */       else if ((LA(1) == '\r') && (LA(2) >= '\003') && (LA(2) <= 'ÿ') && (LA(3) >= '\003') && (LA(3) <= 'ÿ')) {
/* 2233 */         match('\r');
/* 2234 */         k = this.text.length();
/* 2235 */         mIGNWS(false);
/* 2236 */         this.text.setLength(k);
/*      */ 
/* 2238 */         newline();
/* 2239 */         this.text.append("# ");
/*      */       }
/* 2242 */       else if ((LA(1) == '\n') && (LA(2) >= '\003') && (LA(2) <= 'ÿ') && (LA(3) >= '\003') && (LA(3) <= 'ÿ')) {
/* 2243 */         match('\n');
/* 2244 */         k = this.text.length();
/* 2245 */         mIGNWS(false);
/* 2246 */         this.text.setLength(k);
/*      */ 
/* 2248 */         newline();
/* 2249 */         this.text.append("# ");
/*      */       }
/*      */       else {
/* 2252 */         if ((LA(1) < '\003') || (LA(1) > 'ÿ') || (LA(2) < '\003') || (LA(2) > 'ÿ') || (LA(3) < '\003') || (LA(3) > 'ÿ')) break;
/* 2253 */         matchNot(65535);
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 2264 */     this.text.append("\n");
/*      */ 
/* 2266 */     int k = this.text.length();
/* 2267 */     match("*/");
/* 2268 */     this.text.setLength(k);
/* 2269 */     if ((paramBoolean) && (localToken == null) && (i != -1)) {
/* 2270 */       localToken = makeToken(i);
/* 2271 */       localToken.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/*      */     }
/* 2273 */     this._returnToken = localToken;
/*      */   }
/*      */ 
/*      */   protected final void mIGNWS(boolean paramBoolean) throws RecognitionException, CharStreamException, TokenStreamException {
/* 2277 */     Token localToken = null; int j = this.text.length();
/* 2278 */     int i = 21;
/*      */     while (true)
/*      */     {
/* 2284 */       if ((LA(1) == ' ') && (LA(2) >= '\003') && (LA(2) <= 'ÿ') && (LA(3) >= '\003') && (LA(3) <= 'ÿ')) {
/* 2285 */         match(' ');
/*      */       } else {
/* 2287 */         if ((LA(1) != '\t') || (LA(2) < '\003') || (LA(2) > 'ÿ') || (LA(3) < '\003') || (LA(3) > 'ÿ')) break;
/* 2288 */         match('\t');
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 2296 */     if ((paramBoolean) && (localToken == null) && (i != -1)) {
/* 2297 */       localToken = makeToken(i);
/* 2298 */       localToken.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/*      */     }
/* 2300 */     this._returnToken = localToken;
/*      */   }
/*      */ 
/*      */   protected final void mESC(boolean paramBoolean) throws RecognitionException, CharStreamException, TokenStreamException {
/* 2304 */     Token localToken = null; int j = this.text.length();
/* 2305 */     int i = 25;
/*      */ 
/* 2308 */     match('\\');
/*      */ 
/* 2310 */     switch (LA(1))
/*      */     {
/*      */     case 'n':
/* 2313 */       match('n');
/* 2314 */       break;
/*      */     case 'r':
/* 2318 */       match('r');
/* 2319 */       break;
/*      */     case 't':
/* 2323 */       match('t');
/* 2324 */       break;
/*      */     case 'b':
/* 2328 */       match('b');
/* 2329 */       break;
/*      */     case 'f':
/* 2333 */       match('f');
/* 2334 */       break;
/*      */     case '"':
/* 2338 */       match('"');
/* 2339 */       break;
/*      */     case '\'':
/* 2343 */       match('\'');
/* 2344 */       break;
/*      */     case '\\':
/* 2348 */       match('\\');
/* 2349 */       break;
/*      */     case '0':
/*      */     case '1':
/*      */     case '2':
/*      */     case '3':
/* 2354 */       matchRange('0', '3');
/*      */ 
/* 2357 */       if ((LA(1) >= '0') && (LA(1) <= '9') && (LA(2) >= '\003') && (LA(2) <= 'ÿ')) {
/* 2358 */         mDIGIT(false);
/*      */ 
/* 2360 */         if ((LA(1) >= '0') && (LA(1) <= '9') && (LA(2) >= '\003') && (LA(2) <= 'ÿ')) {
/* 2361 */           mDIGIT(false);
/*      */         }
/* 2363 */         else if ((LA(1) < '\003') || (LA(1) > 'ÿ'))
/*      */         {
/* 2366 */           throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */         }
/*      */ 
/*      */       }
/* 2371 */       else if ((LA(1) < '\003') || (LA(1) > 'ÿ'))
/*      */       {
/* 2374 */         throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */       }
/*      */ 
/*      */       break;
/*      */     case '4':
/*      */     case '5':
/*      */     case '6':
/*      */     case '7':
/* 2383 */       matchRange('4', '7');
/*      */ 
/* 2386 */       if ((LA(1) >= '0') && (LA(1) <= '9') && (LA(2) >= '\003') && (LA(2) <= 'ÿ')) {
/* 2387 */         mDIGIT(false);
/*      */       }
/* 2389 */       else if ((LA(1) < '\003') || (LA(1) > 'ÿ'))
/*      */       {
/* 2392 */         throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */       }
/*      */ 
/*      */       break;
/*      */     default:
/* 2400 */       throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */     }
/*      */ 
/* 2404 */     if ((paramBoolean) && (localToken == null) && (i != -1)) {
/* 2405 */       localToken = makeToken(i);
/* 2406 */       localToken.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/*      */     }
/* 2408 */     this._returnToken = localToken;
/*      */   }
/*      */ 
/*      */   protected final void mDIGIT(boolean paramBoolean) throws RecognitionException, CharStreamException, TokenStreamException {
/* 2412 */     Token localToken = null; int j = this.text.length();
/* 2413 */     int i = 26;
/*      */ 
/* 2416 */     matchRange('0', '9');
/* 2417 */     if ((paramBoolean) && (localToken == null) && (i != -1)) {
/* 2418 */       localToken = makeToken(i);
/* 2419 */       localToken.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/*      */     }
/* 2421 */     this._returnToken = localToken;
/*      */   }
/*      */ 
/*      */   private static final long[] mk_tokenSet_0()
/*      */   {
/* 2426 */     long[] arrayOfLong = new long[8];
/* 2427 */     arrayOfLong[0] = -103079215112L;
/* 2428 */     for (int i = 1; i <= 3; i++) arrayOfLong[i] = -1L;
/* 2429 */     return arrayOfLong;
/*      */   }
/*      */ 
/*      */   private static final long[] mk_tokenSet_1() {
/* 2433 */     long[] arrayOfLong = new long[8];
/* 2434 */     arrayOfLong[0] = -145135534866440L;
/* 2435 */     for (int i = 1; i <= 3; i++) arrayOfLong[i] = -1L;
/* 2436 */     return arrayOfLong;
/*      */   }
/*      */ 
/*      */   private static final long[] mk_tokenSet_2() {
/* 2440 */     long[] arrayOfLong = new long[8];
/* 2441 */     arrayOfLong[0] = -141407503262728L;
/* 2442 */     for (int i = 1; i <= 3; i++) arrayOfLong[i] = -1L;
/* 2443 */     return arrayOfLong;
/*      */   }
/*      */ 
/*      */   private static final long[] mk_tokenSet_3() {
/* 2447 */     long[] arrayOfLong = { 0L, 576460745995190270L, 0L, 0L, 0L };
/* 2448 */     return arrayOfLong;
/*      */   }
/*      */ 
/*      */   private static final long[] mk_tokenSet_4() {
/* 2452 */     long[] arrayOfLong = { 4294977024L, 0L, 0L, 0L, 0L };
/* 2453 */     return arrayOfLong;
/*      */   }
/*      */ 
/*      */   private static final long[] mk_tokenSet_5() {
/* 2457 */     long[] arrayOfLong = { 1103806604800L, 0L, 0L, 0L, 0L };
/* 2458 */     return arrayOfLong;
/*      */   }
/*      */ 
/*      */   private static final long[] mk_tokenSet_6() {
/* 2462 */     long[] arrayOfLong = { 287959436729787904L, 576460745995190270L, 0L, 0L, 0L };
/* 2463 */     return arrayOfLong;
/*      */   }
/*      */ 
/*      */   private static final long[] mk_tokenSet_7() {
/* 2467 */     long[] arrayOfLong = new long[8];
/* 2468 */     arrayOfLong[0] = -17179869192L;
/* 2469 */     arrayOfLong[1] = -268435457L;
/* 2470 */     for (int i = 2; i <= 3; i++) arrayOfLong[i] = -1L;
/* 2471 */     return arrayOfLong;
/*      */   }
/*      */ 
/*      */   private static final long[] mk_tokenSet_8() {
/* 2475 */     long[] arrayOfLong = new long[8];
/* 2476 */     arrayOfLong[0] = -549755813896L;
/* 2477 */     arrayOfLong[1] = -268435457L;
/* 2478 */     for (int i = 2; i <= 3; i++) arrayOfLong[i] = -1L;
/* 2479 */     return arrayOfLong;
/*      */   }
/*      */ 
/*      */   private static final long[] mk_tokenSet_9() {
/* 2483 */     long[] arrayOfLong = { 287948901175001088L, 576460745995190270L, 0L, 0L, 0L };
/* 2484 */     return arrayOfLong;
/*      */   }
/*      */ 
/*      */   private static final long[] mk_tokenSet_10() {
/* 2488 */     long[] arrayOfLong = { 287950056521213440L, 576460746129407998L, 0L, 0L, 0L };
/* 2489 */     return arrayOfLong;
/*      */   }
/*      */ 
/*      */   private static final long[] mk_tokenSet_11() {
/* 2493 */     long[] arrayOfLong = { 287958332923183104L, 576460745995190270L, 0L, 0L, 0L };
/* 2494 */     return arrayOfLong;
/*      */   }
/*      */ 
/*      */   private static final long[] mk_tokenSet_12() {
/* 2498 */     long[] arrayOfLong = { 287978128427460096L, 576460746532061182L, 0L, 0L, 0L };
/* 2499 */     return arrayOfLong;
/*      */   }
/*      */ 
/*      */   private static final long[] mk_tokenSet_13() {
/* 2503 */     long[] arrayOfLong = { 2306123388973753856L, 671088640L, 0L, 0L, 0L };
/* 2504 */     return arrayOfLong;
/*      */   }
/*      */ 
/*      */   private static final long[] mk_tokenSet_14() {
/* 2508 */     long[] arrayOfLong = { 287952805300282880L, 576460746129407998L, 0L, 0L, 0L };
/* 2509 */     return arrayOfLong;
/*      */   }
/*      */ 
/*      */   private static final long[] mk_tokenSet_15() {
/* 2513 */     long[] arrayOfLong = { 2305843013508670976L, 0L, 0L, 0L, 0L };
/* 2514 */     return arrayOfLong;
/*      */   }
/*      */ 
/*      */   private static final long[] mk_tokenSet_16() {
/* 2518 */     long[] arrayOfLong = { 2306051920717948416L, 536870912L, 0L, 0L, 0L };
/* 2519 */     return arrayOfLong;
/*      */   }
/*      */ 
/*      */   private static final long[] mk_tokenSet_17() {
/* 2523 */     long[] arrayOfLong = { 208911504254464L, 536870912L, 0L, 0L, 0L };
/* 2524 */     return arrayOfLong;
/*      */   }
/*      */ 
/*      */   private static final long[] mk_tokenSet_18() {
/* 2528 */     long[] arrayOfLong = { 1151051235328L, 576460746129407998L, 0L, 0L, 0L };
/* 2529 */     return arrayOfLong;
/*      */   }
/*      */ 
/*      */   private static final long[] mk_tokenSet_19() {
/* 2533 */     long[] arrayOfLong = { 189120294954496L, 0L, 0L, 0L, 0L };
/* 2534 */     return arrayOfLong;
/*      */   }
/*      */ 
/*      */   private static final long[] mk_tokenSet_20() {
/* 2538 */     long[] arrayOfLong = { 288139722277004800L, 576460746129407998L, 0L, 0L, 0L };
/* 2539 */     return arrayOfLong;
/*      */   }
/*      */ 
/*      */   private static final long[] mk_tokenSet_21() {
/* 2543 */     long[] arrayOfLong = { 288049596683265536L, 576460746666278910L, 0L, 0L, 0L };
/* 2544 */     return arrayOfLong;
/*      */   }
/*      */ 
/*      */   private static final long[] mk_tokenSet_22() {
/* 2548 */     long[] arrayOfLong = { 287960536241415680L, 576460745995190270L, 0L, 0L, 0L };
/* 2549 */     return arrayOfLong;
/*      */   }
/*      */ 
/*      */   private static final long[] mk_tokenSet_23() {
/* 2553 */     long[] arrayOfLong = { 287958337218160128L, 576460745995190270L, 0L, 0L, 0L };
/* 2554 */     return arrayOfLong;
/*      */   }
/*      */ 
/*      */   private static final long[] mk_tokenSet_24() {
/* 2558 */     long[] arrayOfLong = { 288228817078593024L, 576460746532061182L, 0L, 0L, 0L };
/* 2559 */     return arrayOfLong;
/*      */   }
/*      */ 
/*      */   private static final long[] mk_tokenSet_25() {
/* 2563 */     long[] arrayOfLong = { 288158448334415360L, 576460746532061182L, 0L, 0L, 0L };
/* 2564 */     return arrayOfLong;
/*      */   }
/*      */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     antlr.actions.python.ActionLexer
 * JD-Core Version:    0.6.2
 */