/*      */ package antlr.actions.csharp;
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
/* 2432 */   public static final BitSet _tokenSet_0 = new BitSet(mk_tokenSet_0());
/*      */ 
/* 2439 */   public static final BitSet _tokenSet_1 = new BitSet(mk_tokenSet_1());
/*      */ 
/* 2446 */   public static final BitSet _tokenSet_2 = new BitSet(mk_tokenSet_2());
/*      */ 
/* 2451 */   public static final BitSet _tokenSet_3 = new BitSet(mk_tokenSet_3());
/*      */ 
/* 2456 */   public static final BitSet _tokenSet_4 = new BitSet(mk_tokenSet_4());
/*      */ 
/* 2461 */   public static final BitSet _tokenSet_5 = new BitSet(mk_tokenSet_5());
/*      */ 
/* 2466 */   public static final BitSet _tokenSet_6 = new BitSet(mk_tokenSet_6());
/*      */ 
/* 2474 */   public static final BitSet _tokenSet_7 = new BitSet(mk_tokenSet_7());
/*      */ 
/* 2482 */   public static final BitSet _tokenSet_8 = new BitSet(mk_tokenSet_8());
/*      */ 
/* 2487 */   public static final BitSet _tokenSet_9 = new BitSet(mk_tokenSet_9());
/*      */ 
/* 2492 */   public static final BitSet _tokenSet_10 = new BitSet(mk_tokenSet_10());
/*      */ 
/* 2497 */   public static final BitSet _tokenSet_11 = new BitSet(mk_tokenSet_11());
/*      */ 
/* 2502 */   public static final BitSet _tokenSet_12 = new BitSet(mk_tokenSet_12());
/*      */ 
/* 2507 */   public static final BitSet _tokenSet_13 = new BitSet(mk_tokenSet_13());
/*      */ 
/* 2512 */   public static final BitSet _tokenSet_14 = new BitSet(mk_tokenSet_14());
/*      */ 
/* 2517 */   public static final BitSet _tokenSet_15 = new BitSet(mk_tokenSet_15());
/*      */ 
/* 2522 */   public static final BitSet _tokenSet_16 = new BitSet(mk_tokenSet_16());
/*      */ 
/* 2527 */   public static final BitSet _tokenSet_17 = new BitSet(mk_tokenSet_17());
/*      */ 
/* 2532 */   public static final BitSet _tokenSet_18 = new BitSet(mk_tokenSet_18());
/*      */ 
/* 2537 */   public static final BitSet _tokenSet_19 = new BitSet(mk_tokenSet_19());
/*      */ 
/* 2542 */   public static final BitSet _tokenSet_20 = new BitSet(mk_tokenSet_20());
/*      */ 
/* 2547 */   public static final BitSet _tokenSet_21 = new BitSet(mk_tokenSet_21());
/*      */ 
/* 2552 */   public static final BitSet _tokenSet_22 = new BitSet(mk_tokenSet_22());
/*      */ 
/* 2557 */   public static final BitSet _tokenSet_23 = new BitSet(mk_tokenSet_23());
/*      */ 
/* 2562 */   public static final BitSet _tokenSet_24 = new BitSet(mk_tokenSet_24());
/*      */ 
/* 2567 */   public static final BitSet _tokenSet_25 = new BitSet(mk_tokenSet_25());
/*      */ 
/* 2572 */   public static final BitSet _tokenSet_26 = new BitSet(mk_tokenSet_26());
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
/*      */   public void setTool(Tool paramTool)
/*      */   {
/*   80 */     this.antlrTool = paramTool;
/*      */   }
/*      */ 
/*      */   public void reportError(RecognitionException paramRecognitionException)
/*      */   {
/*   85 */     this.antlrTool.error("Syntax error in action: " + paramRecognitionException, getFilename(), getLine(), getColumn());
/*      */   }
/*      */ 
/*      */   public void reportError(String paramString)
/*      */   {
/*   90 */     this.antlrTool.error(paramString, getFilename(), getLine(), getColumn());
/*      */   }
/*      */ 
/*      */   public void reportWarning(String paramString)
/*      */   {
/*   95 */     if (getFilename() == null)
/*   96 */       this.antlrTool.warning(paramString);
/*      */     else
/*   98 */       this.antlrTool.warning(paramString, getFilename(), getLine(), getColumn()); 
/*      */   }
/*      */ 
/*  101 */   public ActionLexer(InputStream paramInputStream) { this(new ByteBuffer(paramInputStream)); }
/*      */ 
/*      */   public ActionLexer(Reader paramReader) {
/*  104 */     this(new CharBuffer(paramReader));
/*      */   }
/*      */   public ActionLexer(InputBuffer paramInputBuffer) {
/*  107 */     this(new LexerSharedInputState(paramInputBuffer));
/*      */   }
/*      */   public ActionLexer(LexerSharedInputState paramLexerSharedInputState) {
/*  110 */     super(paramLexerSharedInputState);
/*  111 */     this.caseSensitiveLiterals = true;
/*  112 */     setCaseSensitive(true);
/*  113 */     this.literals = new Hashtable();
/*      */   }
/*      */ 
/*      */   public Token nextToken() throws TokenStreamException {
/*  117 */     Token localToken = null;
/*      */     while (true)
/*      */     {
/*  120 */       Object localObject = null;
/*  121 */       int i = 0;
/*  122 */       resetText();
/*      */       try
/*      */       {
/*  125 */         if ((LA(1) >= '\003') && (LA(1) <= 'ÿ')) {
/*  126 */           mACTION(true);
/*  127 */           localToken = this._returnToken;
/*      */         }
/*  130 */         else if (LA(1) == 65535) { uponEOF(); this._returnToken = makeToken(1); } else {
/*  131 */           throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */         }
/*      */ 
/*  134 */         if (this._returnToken == null) continue;
/*  135 */         i = this._returnToken.getType();
/*  136 */         this._returnToken.setType(i);
/*  137 */         return this._returnToken;
/*      */       }
/*      */       catch (RecognitionException localRecognitionException) {
/*  140 */         throw new TokenStreamRecognitionException(localRecognitionException);
/*      */       }
/*      */       catch (CharStreamException localCharStreamException)
/*      */       {
/*  144 */         if ((localCharStreamException instanceof CharStreamIOException)) {
/*  145 */           throw new TokenStreamIOException(((CharStreamIOException)localCharStreamException).io);
/*      */         }
/*      */ 
/*  148 */         throw new TokenStreamException(localCharStreamException.getMessage());
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   public final void mACTION(boolean paramBoolean) throws RecognitionException, CharStreamException, TokenStreamException
/*      */   {
/*  155 */     Token localToken = null; int j = this.text.length();
/*  156 */     int i = 4;
/*      */ 
/*  160 */     int k = 0;
/*      */     while (true)
/*      */     {
/*  163 */       switch (LA(1))
/*      */       {
/*      */       case '#':
/*  166 */         mAST_ITEM(false);
/*  167 */         break;
/*      */       case '$':
/*  171 */         mTEXT_ITEM(false);
/*  172 */         break;
/*      */       default:
/*  175 */         if (_tokenSet_0.member(LA(1))) {
/*  176 */           mSTUFF(false);
/*      */         }
/*      */         else {
/*  179 */           if (k >= 1) break label126; throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */         }break;
/*      */       }
/*  182 */       k++;
/*      */     }
/*      */ 
/*  185 */     label126: if ((paramBoolean) && (localToken == null) && (i != -1)) {
/*  186 */       localToken = makeToken(i);
/*  187 */       localToken.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/*      */     }
/*  189 */     this._returnToken = localToken;
/*      */   }
/*      */ 
/*      */   protected final void mSTUFF(boolean paramBoolean)
/*      */     throws RecognitionException, CharStreamException, TokenStreamException
/*      */   {
/*  196 */     Token localToken = null; int j = this.text.length();
/*  197 */     int i = 5;
/*      */ 
/*  200 */     switch (LA(1))
/*      */     {
/*      */     case '"':
/*  203 */       mSTRING(false);
/*  204 */       break;
/*      */     case '\'':
/*  208 */       mCHAR(false);
/*  209 */       break;
/*      */     case '\n':
/*  213 */       match('\n');
/*  214 */       newline();
/*  215 */       break;
/*      */     default:
/*  218 */       if ((LA(1) == '/') && ((LA(2) == '*') || (LA(2) == '/'))) {
/*  219 */         mCOMMENT(false);
/*      */       }
/*  221 */       else if ((LA(1) == '\r') && (LA(2) == '\n')) {
/*  222 */         match("\r\n");
/*  223 */         newline();
/*      */       }
/*  225 */       else if ((LA(1) == '\\') && (LA(2) == '#')) {
/*  226 */         match('\\');
/*  227 */         match('#');
/*  228 */         this.text.setLength(j); this.text.append("#");
/*      */       }
/*  230 */       else if ((LA(1) == '/') && (_tokenSet_1.member(LA(2)))) {
/*  231 */         match('/');
/*      */ 
/*  233 */         match(_tokenSet_1);
/*      */       }
/*  236 */       else if (LA(1) == '\r') {
/*  237 */         match('\r');
/*  238 */         newline();
/*      */       }
/*  240 */       else if (_tokenSet_2.member(LA(1)))
/*      */       {
/*  242 */         match(_tokenSet_2);
/*      */       }
/*      */       else
/*      */       {
/*  246 */         throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */       }break;
/*      */     }
/*  249 */     if ((paramBoolean) && (localToken == null) && (i != -1)) {
/*  250 */       localToken = makeToken(i);
/*  251 */       localToken.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/*      */     }
/*  253 */     this._returnToken = localToken;
/*      */   }
/*      */ 
/*      */   protected final void mAST_ITEM(boolean paramBoolean) throws RecognitionException, CharStreamException, TokenStreamException {
/*  257 */     Token localToken1 = null; int j = this.text.length();
/*  258 */     int i = 6;
/*      */ 
/*  260 */     Token localToken2 = null;
/*  261 */     Token localToken3 = null;
/*  262 */     Token localToken4 = null;
/*      */     int k;
/*  264 */     if ((LA(1) == '#') && (LA(2) == '(')) {
/*  265 */       k = this.text.length();
/*  266 */       match('#');
/*  267 */       this.text.setLength(k);
/*  268 */       mTREE(true);
/*  269 */       localToken2 = this._returnToken;
/*      */     }
/*      */     else
/*      */     {
/*      */       String str1;
/*  271 */       if ((LA(1) == '#') && (_tokenSet_3.member(LA(2)))) {
/*  272 */         k = this.text.length();
/*  273 */         match('#');
/*  274 */         this.text.setLength(k);
/*      */ 
/*  276 */         switch (LA(1)) { case '\t':
/*      */         case '\n':
/*      */         case '\r':
/*      */         case ' ':
/*  279 */           mWS(false);
/*  280 */           break;
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
/*  297 */           break;
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
/*      */         case '"':
/*      */         case '#':
/*      */         case '$':
/*      */         case '%':
/*      */         case '&':
/*      */         case '\'':
/*      */         case '(':
/*      */         case ')':
/*      */         case '*':
/*      */         case '+':
/*      */         case ',':
/*      */         case '-':
/*      */         case '.':
/*      */         case '/':
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
/*      */         case ':':
/*      */         case ';':
/*      */         case '<':
/*      */         case '=':
/*      */         case '>':
/*      */         case '?':
/*      */         case '@':
/*      */         case '[':
/*      */         case '\\':
/*      */         case ']':
/*      */         case '^':
/*      */         case '`':
/*      */         default:
/*  301 */           throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */         }
/*      */ 
/*  305 */         mID(true);
/*  306 */         localToken3 = this._returnToken;
/*      */ 
/*  308 */         str1 = localToken3.getText();
/*  309 */         String str2 = this.generator.mapTreeId(localToken3.getText(), this.transInfo);
/*      */ 
/*  312 */         if ((str2 != null) && (!str1.equals(str2)))
/*      */         {
/*  314 */           this.text.setLength(j); this.text.append(str2);
/*      */         }
/*  318 */         else if ((str1.equals("define")) || (str1.equals("undef")) || (str1.equals("if")) || (str1.equals("elif")) || (str1.equals("else")) || (str1.equals("endif")) || (str1.equals("line")) || (str1.equals("error")) || (str1.equals("warning")) || (str1.equals("region")) || (str1.equals("endregion")))
/*      */         {
/*  330 */           this.text.setLength(j); this.text.append("#" + str1);
/*      */         }
/*      */ 
/*  335 */         if (_tokenSet_4.member(LA(1))) {
/*  336 */           mWS(false);
/*      */         }
/*      */ 
/*  343 */         if (LA(1) == '=') {
/*  344 */           mVAR_ASSIGN(false);
/*      */         }
/*      */ 
/*      */       }
/*  351 */       else if ((LA(1) == '#') && (LA(2) == '[')) {
/*  352 */         k = this.text.length();
/*  353 */         match('#');
/*  354 */         this.text.setLength(k);
/*  355 */         mAST_CONSTRUCTOR(true);
/*  356 */         localToken4 = this._returnToken;
/*      */       }
/*  358 */       else if ((LA(1) == '#') && (LA(2) == '#')) {
/*  359 */         match("##");
/*      */ 
/*  361 */         if (this.currentRule != null)
/*      */         {
/*  363 */           str1 = this.currentRule.getRuleName() + "_AST";
/*  364 */           this.text.setLength(j); this.text.append(str1);
/*      */ 
/*  366 */           if (this.transInfo != null) {
/*  367 */             this.transInfo.refRuleRoot = str1;
/*      */           }
/*      */         }
/*      */         else
/*      */         {
/*  372 */           reportWarning("\"##\" not valid in this context");
/*  373 */           this.text.setLength(j); this.text.append("##");
/*      */         }
/*      */ 
/*  377 */         if (_tokenSet_4.member(LA(1))) {
/*  378 */           mWS(false);
/*      */         }
/*      */ 
/*  385 */         if (LA(1) == '=') {
/*  386 */           mVAR_ASSIGN(false);
/*      */         }
/*      */ 
/*      */       }
/*      */       else
/*      */       {
/*  394 */         throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */       }
/*      */     }
/*  397 */     if ((paramBoolean) && (localToken1 == null) && (i != -1)) {
/*  398 */       localToken1 = makeToken(i);
/*  399 */       localToken1.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/*      */     }
/*  401 */     this._returnToken = localToken1;
/*      */   }
/*      */ 
/*      */   protected final void mTEXT_ITEM(boolean paramBoolean) throws RecognitionException, CharStreamException, TokenStreamException {
/*  405 */     Token localToken1 = null; int j = this.text.length();
/*  406 */     int i = 7;
/*      */ 
/*  408 */     Token localToken2 = null;
/*  409 */     Token localToken3 = null;
/*  410 */     Token localToken4 = null;
/*  411 */     Token localToken5 = null;
/*  412 */     Token localToken6 = null;
/*  413 */     Token localToken7 = null;
/*      */     String str1;
/*      */     String str2;
/*  415 */     if ((LA(1) == '$') && (LA(2) == 'F') && (LA(3) == 'O')) {
/*  416 */       match("$FOLLOW");
/*      */ 
/*  418 */       if ((_tokenSet_5.member(LA(1))) && (_tokenSet_6.member(LA(2))) && (LA(3) >= '\003') && (LA(3) <= 'ÿ'))
/*      */       {
/*  420 */         switch (LA(1)) { case '\t':
/*      */         case '\n':
/*      */         case '\r':
/*      */         case ' ':
/*  423 */           mWS(false);
/*  424 */           break;
/*      */         case '(':
/*  428 */           break;
/*      */         default:
/*  432 */           throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */         }
/*      */ 
/*  436 */         match('(');
/*  437 */         mTEXT_ARG(true);
/*  438 */         localToken6 = this._returnToken;
/*  439 */         match(')');
/*      */       }
/*      */ 
/*  446 */       str1 = this.currentRule.getRuleName();
/*  447 */       if (localToken6 != null) {
/*  448 */         str1 = localToken6.getText();
/*      */       }
/*  450 */       str2 = this.generator.getFOLLOWBitSet(str1, 1);
/*      */ 
/*  452 */       if (str2 == null) {
/*  453 */         reportError("$FOLLOW(" + str1 + ")" + ": unknown rule or bad lookahead computation");
/*      */       }
/*      */       else
/*      */       {
/*  457 */         this.text.setLength(j); this.text.append(str2);
/*      */       }
/*      */ 
/*      */     }
/*  461 */     else if ((LA(1) == '$') && (LA(2) == 'F') && (LA(3) == 'I')) {
/*  462 */       match("$FIRST");
/*      */ 
/*  464 */       if ((_tokenSet_5.member(LA(1))) && (_tokenSet_6.member(LA(2))) && (LA(3) >= '\003') && (LA(3) <= 'ÿ'))
/*      */       {
/*  466 */         switch (LA(1)) { case '\t':
/*      */         case '\n':
/*      */         case '\r':
/*      */         case ' ':
/*  469 */           mWS(false);
/*  470 */           break;
/*      */         case '(':
/*  474 */           break;
/*      */         default:
/*  478 */           throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */         }
/*      */ 
/*  482 */         match('(');
/*  483 */         mTEXT_ARG(true);
/*  484 */         localToken7 = this._returnToken;
/*  485 */         match(')');
/*      */       }
/*      */ 
/*  492 */       str1 = this.currentRule.getRuleName();
/*  493 */       if (localToken7 != null) {
/*  494 */         str1 = localToken7.getText();
/*      */       }
/*  496 */       str2 = this.generator.getFIRSTBitSet(str1, 1);
/*      */ 
/*  498 */       if (str2 == null) {
/*  499 */         reportError("$FIRST(" + str1 + ")" + ": unknown rule or bad lookahead computation");
/*      */       }
/*      */       else
/*      */       {
/*  503 */         this.text.setLength(j); this.text.append(str2);
/*      */       }
/*      */ 
/*      */     }
/*  507 */     else if ((LA(1) == '$') && (LA(2) == 'a')) {
/*  508 */       match("$append");
/*      */ 
/*  510 */       switch (LA(1)) { case '\t':
/*      */       case '\n':
/*      */       case '\r':
/*      */       case ' ':
/*  513 */         mWS(false);
/*  514 */         break;
/*      */       case '(':
/*  518 */         break;
/*      */       default:
/*  522 */         throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */       }
/*      */ 
/*  526 */       match('(');
/*  527 */       mTEXT_ARG(true);
/*  528 */       localToken2 = this._returnToken;
/*  529 */       match(')');
/*      */ 
/*  531 */       str1 = "text.Append(" + localToken2.getText() + ")";
/*  532 */       this.text.setLength(j); this.text.append(str1);
/*      */     }
/*  535 */     else if ((LA(1) == '$') && (LA(2) == 's')) {
/*  536 */       match("$set");
/*      */ 
/*  538 */       if ((LA(1) == 'T') && (LA(2) == 'e')) {
/*  539 */         match("Text");
/*      */ 
/*  541 */         switch (LA(1)) { case '\t':
/*      */         case '\n':
/*      */         case '\r':
/*      */         case ' ':
/*  544 */           mWS(false);
/*  545 */           break;
/*      */         case '(':
/*  549 */           break;
/*      */         default:
/*  553 */           throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */         }
/*      */ 
/*  557 */         match('(');
/*  558 */         mTEXT_ARG(true);
/*  559 */         localToken3 = this._returnToken;
/*  560 */         match(')');
/*      */ 
/*  563 */         str1 = "text.Length = _begin; text.Append(" + localToken3.getText() + ")";
/*  564 */         this.text.setLength(j); this.text.append(str1);
/*      */       }
/*  567 */       else if ((LA(1) == 'T') && (LA(2) == 'o')) {
/*  568 */         match("Token");
/*      */ 
/*  570 */         switch (LA(1)) { case '\t':
/*      */         case '\n':
/*      */         case '\r':
/*      */         case ' ':
/*  573 */           mWS(false);
/*  574 */           break;
/*      */         case '(':
/*  578 */           break;
/*      */         default:
/*  582 */           throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */         }
/*      */ 
/*  586 */         match('(');
/*  587 */         mTEXT_ARG(true);
/*  588 */         localToken4 = this._returnToken;
/*  589 */         match(')');
/*      */ 
/*  591 */         str1 = "_token = " + localToken4.getText();
/*  592 */         this.text.setLength(j); this.text.append(str1);
/*      */       }
/*  595 */       else if ((LA(1) == 'T') && (LA(2) == 'y')) {
/*  596 */         match("Type");
/*      */ 
/*  598 */         switch (LA(1)) { case '\t':
/*      */         case '\n':
/*      */         case '\r':
/*      */         case ' ':
/*  601 */           mWS(false);
/*  602 */           break;
/*      */         case '(':
/*  606 */           break;
/*      */         default:
/*  610 */           throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */         }
/*      */ 
/*  614 */         match('(');
/*  615 */         mTEXT_ARG(true);
/*  616 */         localToken5 = this._returnToken;
/*  617 */         match(')');
/*      */ 
/*  619 */         str1 = "_ttype = " + localToken5.getText();
/*  620 */         this.text.setLength(j); this.text.append(str1);
/*      */       }
/*      */       else
/*      */       {
/*  624 */         throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */       }
/*      */ 
/*      */     }
/*  629 */     else if ((LA(1) == '$') && (LA(2) == 'g')) {
/*  630 */       match("$getText");
/*      */ 
/*  632 */       this.text.setLength(j); this.text.append("text.ToString(_begin, text.Length-_begin)");
/*      */     }
/*      */     else
/*      */     {
/*  636 */       throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */     }
/*      */ 
/*  639 */     if ((paramBoolean) && (localToken1 == null) && (i != -1)) {
/*  640 */       localToken1 = makeToken(i);
/*  641 */       localToken1.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/*      */     }
/*  643 */     this._returnToken = localToken1;
/*      */   }
/*      */ 
/*      */   protected final void mCOMMENT(boolean paramBoolean) throws RecognitionException, CharStreamException, TokenStreamException {
/*  647 */     Token localToken = null; int j = this.text.length();
/*  648 */     int i = 19;
/*      */ 
/*  651 */     if ((LA(1) == '/') && (LA(2) == '/')) {
/*  652 */       mSL_COMMENT(false);
/*      */     }
/*  654 */     else if ((LA(1) == '/') && (LA(2) == '*')) {
/*  655 */       mML_COMMENT(false);
/*      */     }
/*      */     else {
/*  658 */       throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */     }
/*      */ 
/*  661 */     if ((paramBoolean) && (localToken == null) && (i != -1)) {
/*  662 */       localToken = makeToken(i);
/*  663 */       localToken.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/*      */     }
/*  665 */     this._returnToken = localToken;
/*      */   }
/*      */ 
/*      */   protected final void mSTRING(boolean paramBoolean) throws RecognitionException, CharStreamException, TokenStreamException {
/*  669 */     Token localToken = null; int j = this.text.length();
/*  670 */     int i = 23;
/*      */ 
/*  673 */     match('"');
/*      */     while (true)
/*      */     {
/*  677 */       if (LA(1) == '\\') {
/*  678 */         mESC(false);
/*      */       } else {
/*  680 */         if (!_tokenSet_7.member(LA(1))) break;
/*  681 */         matchNot('"');
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/*  689 */     match('"');
/*  690 */     if ((paramBoolean) && (localToken == null) && (i != -1)) {
/*  691 */       localToken = makeToken(i);
/*  692 */       localToken.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/*      */     }
/*  694 */     this._returnToken = localToken;
/*      */   }
/*      */ 
/*      */   protected final void mCHAR(boolean paramBoolean) throws RecognitionException, CharStreamException, TokenStreamException {
/*  698 */     Token localToken = null; int j = this.text.length();
/*  699 */     int i = 22;
/*      */ 
/*  702 */     match('\'');
/*      */ 
/*  704 */     if (LA(1) == '\\') {
/*  705 */       mESC(false);
/*      */     }
/*  707 */     else if (_tokenSet_8.member(LA(1))) {
/*  708 */       matchNot('\'');
/*      */     }
/*      */     else {
/*  711 */       throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */     }
/*      */ 
/*  715 */     match('\'');
/*  716 */     if ((paramBoolean) && (localToken == null) && (i != -1)) {
/*  717 */       localToken = makeToken(i);
/*  718 */       localToken.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/*      */     }
/*  720 */     this._returnToken = localToken;
/*      */   }
/*      */ 
/*      */   protected final void mTREE(boolean paramBoolean) throws RecognitionException, CharStreamException, TokenStreamException {
/*  724 */     Token localToken1 = null; int j = this.text.length();
/*  725 */     int i = 8;
/*      */ 
/*  727 */     Token localToken2 = null;
/*  728 */     Token localToken3 = null;
/*      */ 
/*  730 */     StringBuffer localStringBuffer = new StringBuffer();
/*  731 */     int m = 0;
/*  732 */     Vector localVector = new Vector(10);
/*      */ 
/*  735 */     int k = this.text.length();
/*  736 */     match('(');
/*  737 */     this.text.setLength(k);
/*      */ 
/*  739 */     switch (LA(1)) { case '\t':
/*      */     case '\n':
/*      */     case '\r':
/*      */     case ' ':
/*  742 */       k = this.text.length();
/*  743 */       mWS(false);
/*  744 */       this.text.setLength(k);
/*  745 */       break;
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
/*  763 */       break;
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
/*  767 */       throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */     }
/*      */ 
/*  771 */     k = this.text.length();
/*  772 */     mTREE_ELEMENT(true);
/*  773 */     this.text.setLength(k);
/*  774 */     localToken2 = this._returnToken;
/*      */ 
/*  776 */     localVector.appendElement(this.generator.processStringForASTConstructor(localToken2.getText()));
/*      */ 
/*  781 */     switch (LA(1)) { case '\t':
/*      */     case '\n':
/*      */     case '\r':
/*      */     case ' ':
/*  784 */       k = this.text.length();
/*  785 */       mWS(false);
/*  786 */       this.text.setLength(k);
/*  787 */       break;
/*      */     case ')':
/*      */     case ',':
/*  791 */       break;
/*      */     default:
/*  795 */       throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */     }
/*      */ 
/*  802 */     while (LA(1) == ',') {
/*  803 */       k = this.text.length();
/*  804 */       match(',');
/*  805 */       this.text.setLength(k);
/*      */ 
/*  807 */       switch (LA(1)) { case '\t':
/*      */       case '\n':
/*      */       case '\r':
/*      */       case ' ':
/*  810 */         k = this.text.length();
/*  811 */         mWS(false);
/*  812 */         this.text.setLength(k);
/*  813 */         break;
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
/*  831 */         break;
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
/*  835 */         throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */       }
/*      */ 
/*  839 */       k = this.text.length();
/*  840 */       mTREE_ELEMENT(true);
/*  841 */       this.text.setLength(k);
/*  842 */       localToken3 = this._returnToken;
/*      */ 
/*  844 */       localVector.appendElement(this.generator.processStringForASTConstructor(localToken3.getText()));
/*      */ 
/*  849 */       switch (LA(1)) { case '\t':
/*      */       case '\n':
/*      */       case '\r':
/*      */       case ' ':
/*  852 */         k = this.text.length();
/*  853 */         mWS(false);
/*  854 */         this.text.setLength(k);
/*  855 */         break;
/*      */       case ')':
/*      */       case ',':
/*  859 */         break;
/*      */       default:
/*  863 */         throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/*  874 */     this.text.setLength(j); this.text.append(this.generator.getASTCreateString(localVector));
/*  875 */     k = this.text.length();
/*  876 */     match(')');
/*  877 */     this.text.setLength(k);
/*  878 */     if ((paramBoolean) && (localToken1 == null) && (i != -1)) {
/*  879 */       localToken1 = makeToken(i);
/*  880 */       localToken1.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/*      */     }
/*  882 */     this._returnToken = localToken1;
/*      */   }
/*      */ 
/*      */   protected final void mWS(boolean paramBoolean) throws RecognitionException, CharStreamException, TokenStreamException {
/*  886 */     Token localToken = null; int j = this.text.length();
/*  887 */     int i = 28;
/*      */ 
/*  891 */     int k = 0;
/*      */     while (true)
/*      */     {
/*  894 */       if ((LA(1) == '\r') && (LA(2) == '\n')) {
/*  895 */         match('\r');
/*  896 */         match('\n');
/*  897 */         newline();
/*      */       }
/*  899 */       else if (LA(1) == ' ') {
/*  900 */         match(' ');
/*      */       }
/*  902 */       else if (LA(1) == '\t') {
/*  903 */         match('\t');
/*      */       }
/*  905 */       else if (LA(1) == '\r') {
/*  906 */         match('\r');
/*  907 */         newline();
/*      */       }
/*  909 */       else if (LA(1) == '\n') {
/*  910 */         match('\n');
/*  911 */         newline();
/*      */       }
/*      */       else {
/*  914 */         if (k >= 1) break; throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */       }
/*      */ 
/*  917 */       k++;
/*      */     }
/*      */ 
/*  920 */     if ((paramBoolean) && (localToken == null) && (i != -1)) {
/*  921 */       localToken = makeToken(i);
/*  922 */       localToken.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/*      */     }
/*  924 */     this._returnToken = localToken;
/*      */   }
/*      */ 
/*      */   protected final void mID(boolean paramBoolean) throws RecognitionException, CharStreamException, TokenStreamException {
/*  928 */     Token localToken = null; int j = this.text.length();
/*  929 */     int i = 17;
/*      */ 
/*  933 */     switch (LA(1)) { case 'a':
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
/*  942 */       matchRange('a', 'z');
/*  943 */       break;
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
/*  953 */       matchRange('A', 'Z');
/*  954 */       break;
/*      */     case '_':
/*  958 */       match('_');
/*  959 */       break;
/*      */     case '[':
/*      */     case '\\':
/*      */     case ']':
/*      */     case '^':
/*      */     case '`':
/*      */     default:
/*  963 */       throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */     }
/*      */ 
/*  970 */     while (_tokenSet_9.member(LA(1)))
/*      */     {
/*  972 */       switch (LA(1)) { case 'a':
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
/*  981 */         matchRange('a', 'z');
/*  982 */         break;
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
/*  992 */         matchRange('A', 'Z');
/*  993 */         break;
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
/*  999 */         matchRange('0', '9');
/* 1000 */         break;
/*      */       case '_':
/* 1004 */         match('_');
/* 1005 */         break;
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
/* 1009 */         throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 1020 */     if ((paramBoolean) && (localToken == null) && (i != -1)) {
/* 1021 */       localToken = makeToken(i);
/* 1022 */       localToken.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/*      */     }
/* 1024 */     this._returnToken = localToken;
/*      */   }
/*      */ 
/*      */   protected final void mVAR_ASSIGN(boolean paramBoolean) throws RecognitionException, CharStreamException, TokenStreamException {
/* 1028 */     Token localToken = null; int j = this.text.length();
/* 1029 */     int i = 18;
/*      */ 
/* 1032 */     match('=');
/*      */ 
/* 1036 */     if ((LA(1) != '=') && (this.transInfo != null) && (this.transInfo.refRuleRoot != null)) {
/* 1037 */       this.transInfo.assignToRoot = true;
/*      */     }
/*      */ 
/* 1040 */     if ((paramBoolean) && (localToken == null) && (i != -1)) {
/* 1041 */       localToken = makeToken(i);
/* 1042 */       localToken.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/*      */     }
/* 1044 */     this._returnToken = localToken;
/*      */   }
/*      */ 
/*      */   protected final void mAST_CONSTRUCTOR(boolean paramBoolean) throws RecognitionException, CharStreamException, TokenStreamException {
/* 1048 */     Token localToken1 = null; int j = this.text.length();
/* 1049 */     int i = 10;
/*      */ 
/* 1051 */     Token localToken2 = null;
/* 1052 */     Token localToken3 = null;
/* 1053 */     Token localToken4 = null;
/*      */ 
/* 1055 */     int k = this.text.length();
/* 1056 */     match('[');
/* 1057 */     this.text.setLength(k);
/*      */ 
/* 1059 */     switch (LA(1)) { case '\t':
/*      */     case '\n':
/*      */     case '\r':
/*      */     case ' ':
/* 1062 */       k = this.text.length();
/* 1063 */       mWS(false);
/* 1064 */       this.text.setLength(k);
/* 1065 */       break;
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
/* 1085 */       break;
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
/* 1089 */       throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */     }
/*      */ 
/* 1093 */     k = this.text.length();
/* 1094 */     mAST_CTOR_ELEMENT(true);
/* 1095 */     this.text.setLength(k);
/* 1096 */     localToken2 = this._returnToken;
/*      */ 
/* 1098 */     switch (LA(1)) { case '\t':
/*      */     case '\n':
/*      */     case '\r':
/*      */     case ' ':
/* 1101 */       k = this.text.length();
/* 1102 */       mWS(false);
/* 1103 */       this.text.setLength(k);
/* 1104 */       break;
/*      */     case ',':
/*      */     case ']':
/* 1108 */       break;
/*      */     default:
/* 1112 */       throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */     }
/*      */ 
/* 1117 */     if ((LA(1) == ',') && (_tokenSet_10.member(LA(2))) && (LA(3) >= '\003') && (LA(3) <= 'ÿ')) {
/* 1118 */       k = this.text.length();
/* 1119 */       match(',');
/* 1120 */       this.text.setLength(k);
/*      */ 
/* 1122 */       switch (LA(1)) { case '\t':
/*      */       case '\n':
/*      */       case '\r':
/*      */       case ' ':
/* 1125 */         k = this.text.length();
/* 1126 */         mWS(false);
/* 1127 */         this.text.setLength(k);
/* 1128 */         break;
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
/* 1148 */         break;
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
/* 1152 */         throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */       }
/*      */ 
/* 1156 */       k = this.text.length();
/* 1157 */       mAST_CTOR_ELEMENT(true);
/* 1158 */       this.text.setLength(k);
/* 1159 */       localToken3 = this._returnToken;
/*      */     }
/* 1161 */     switch (LA(1)) { case '\t':
/*      */     case '\n':
/*      */     case '\r':
/*      */     case ' ':
/* 1164 */       k = this.text.length();
/* 1165 */       mWS(false);
/* 1166 */       this.text.setLength(k);
/* 1167 */       break;
/*      */     case ',':
/*      */     case ']':
/* 1171 */       break;
/*      */     default:
/* 1175 */       throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */ 
/* 1180 */       if ((LA(1) != ',') && (LA(1) != ']'))
/*      */       {
/* 1183 */         throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */       }
/*      */       break;
/*      */     }
/*      */ 
/* 1188 */     switch (LA(1))
/*      */     {
/*      */     case ',':
/* 1191 */       k = this.text.length();
/* 1192 */       match(',');
/* 1193 */       this.text.setLength(k);
/*      */ 
/* 1195 */       switch (LA(1)) { case '\t':
/*      */       case '\n':
/*      */       case '\r':
/*      */       case ' ':
/* 1198 */         k = this.text.length();
/* 1199 */         mWS(false);
/* 1200 */         this.text.setLength(k);
/* 1201 */         break;
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
/* 1221 */         break;
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
/* 1225 */         throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */       }
/*      */ 
/* 1229 */       k = this.text.length();
/* 1230 */       mAST_CTOR_ELEMENT(true);
/* 1231 */       this.text.setLength(k);
/* 1232 */       localToken4 = this._returnToken;
/*      */ 
/* 1234 */       switch (LA(1)) { case '\t':
/*      */       case '\n':
/*      */       case '\r':
/*      */       case ' ':
/* 1237 */         k = this.text.length();
/* 1238 */         mWS(false);
/* 1239 */         this.text.setLength(k);
/* 1240 */         break;
/*      */       case ']':
/* 1244 */         break;
/*      */       default:
/* 1248 */         throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */       }
/*      */ 
/*      */       break;
/*      */     case ']':
/* 1256 */       break;
/*      */     default:
/* 1260 */       throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */     }
/*      */ 
/* 1264 */     k = this.text.length();
/* 1265 */     match(']');
/* 1266 */     this.text.setLength(k);
/*      */ 
/* 1268 */     String str = this.generator.processStringForASTConstructor(localToken2.getText());
/*      */ 
/* 1272 */     if (localToken3 != null)
/* 1273 */       str = str + "," + localToken3.getText();
/* 1274 */     if (localToken4 != null) {
/* 1275 */       str = str + "," + localToken4.getText();
/*      */     }
/* 1277 */     this.text.setLength(j); this.text.append(this.generator.getASTCreateString(null, str));
/*      */ 
/* 1279 */     if ((paramBoolean) && (localToken1 == null) && (i != -1)) {
/* 1280 */       localToken1 = makeToken(i);
/* 1281 */       localToken1.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/*      */     }
/* 1283 */     this._returnToken = localToken1;
/*      */   }
/*      */ 
/*      */   protected final void mTEXT_ARG(boolean paramBoolean) throws RecognitionException, CharStreamException, TokenStreamException {
/* 1287 */     Token localToken = null; int j = this.text.length();
/* 1288 */     int i = 13;
/*      */ 
/* 1292 */     switch (LA(1)) { case '\t':
/*      */     case '\n':
/*      */     case '\r':
/*      */     case ' ':
/* 1295 */       mWS(false);
/* 1296 */       break;
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
/* 1316 */       break;
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
/* 1320 */       throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */     }
/*      */ 
/* 1325 */     int k = 0;
/*      */     while (true)
/*      */     {
/* 1328 */       if ((_tokenSet_11.member(LA(1))) && (LA(2) >= '\003') && (LA(2) <= 'ÿ')) {
/* 1329 */         mTEXT_ARG_ELEMENT(false);
/*      */ 
/* 1331 */         if ((_tokenSet_4.member(LA(1))) && (_tokenSet_12.member(LA(2)))) {
/* 1332 */           mWS(false);
/*      */         }
/* 1334 */         else if (!_tokenSet_12.member(LA(1)))
/*      */         {
/* 1337 */           throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */         }
/*      */ 
/*      */       }
/*      */       else
/*      */       {
/* 1343 */         if (k >= 1) break; throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */       }
/*      */ 
/* 1346 */       k++;
/*      */     }
/*      */ 
/* 1349 */     if ((paramBoolean) && (localToken == null) && (i != -1)) {
/* 1350 */       localToken = makeToken(i);
/* 1351 */       localToken.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/*      */     }
/* 1353 */     this._returnToken = localToken;
/*      */   }
/*      */ 
/*      */   protected final void mTREE_ELEMENT(boolean paramBoolean) throws RecognitionException, CharStreamException, TokenStreamException {
/* 1357 */     Token localToken1 = null; int j = this.text.length();
/* 1358 */     int i = 9;
/*      */ 
/* 1360 */     Token localToken2 = null;
/*      */ 
/* 1363 */     switch (LA(1))
/*      */     {
/*      */     case '(':
/* 1366 */       mTREE(false);
/* 1367 */       break;
/*      */     case '[':
/* 1371 */       mAST_CONSTRUCTOR(false);
/* 1372 */       break;
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
/* 1389 */       mID_ELEMENT(false);
/* 1390 */       break;
/*      */     case '"':
/* 1394 */       mSTRING(false);
/* 1395 */       break;
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
/* 1398 */       if ((LA(1) == '#') && (LA(2) == '(')) {
/* 1399 */         k = this.text.length();
/* 1400 */         match('#');
/* 1401 */         this.text.setLength(k);
/* 1402 */         mTREE(false);
/*      */       }
/* 1404 */       else if ((LA(1) == '#') && (LA(2) == '[')) {
/* 1405 */         k = this.text.length();
/* 1406 */         match('#');
/* 1407 */         this.text.setLength(k);
/* 1408 */         mAST_CONSTRUCTOR(false);
/*      */       }
/*      */       else
/*      */       {
/*      */         String str;
/* 1410 */         if ((LA(1) == '#') && (_tokenSet_13.member(LA(2)))) {
/* 1411 */           k = this.text.length();
/* 1412 */           match('#');
/* 1413 */           this.text.setLength(k);
/* 1414 */           boolean bool = mID_ELEMENT(true);
/* 1415 */           localToken2 = this._returnToken;
/*      */ 
/* 1417 */           if (!bool)
/*      */           {
/* 1419 */             str = this.generator.mapTreeId(localToken2.getText(), null);
/* 1420 */             if (str != null) {
/* 1421 */               this.text.setLength(j); this.text.append(str);
/*      */             }
/*      */           }
/*      */ 
/*      */         }
/* 1426 */         else if ((LA(1) == '#') && (LA(2) == '#')) {
/* 1427 */           match("##");
/*      */ 
/* 1429 */           if (this.currentRule != null)
/*      */           {
/* 1431 */             str = this.currentRule.getRuleName() + "_AST";
/* 1432 */             this.text.setLength(j); this.text.append(str);
/*      */           }
/*      */           else
/*      */           {
/* 1436 */             reportError("\"##\" not valid in this context");
/* 1437 */             this.text.setLength(j); this.text.append("##");
/*      */           }
/*      */         }
/*      */         else
/*      */         {
/* 1442 */           throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn()); } 
/*      */       }break;
/*      */     }
/* 1445 */     if ((paramBoolean) && (localToken1 == null) && (i != -1)) {
/* 1446 */       localToken1 = makeToken(i);
/* 1447 */       localToken1.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/*      */     }
/* 1449 */     this._returnToken = localToken1;
/*      */   }
/*      */ 
/*      */   protected final boolean mID_ELEMENT(boolean paramBoolean)
/*      */     throws RecognitionException, CharStreamException, TokenStreamException
/*      */   {
/* 1456 */     boolean bool = false;
/* 1457 */     Token localToken1 = null; int j = this.text.length();
/* 1458 */     int i = 12;
/*      */ 
/* 1460 */     Token localToken2 = null;
/*      */ 
/* 1462 */     mID(true);
/* 1463 */     localToken2 = this._returnToken;
/*      */     int k;
/* 1465 */     if ((_tokenSet_4.member(LA(1))) && (_tokenSet_14.member(LA(2)))) {
/* 1466 */       k = this.text.length();
/* 1467 */       mWS(false);
/* 1468 */       this.text.setLength(k);
/*      */     }
/* 1470 */     else if (!_tokenSet_14.member(LA(1)))
/*      */     {
/* 1473 */       throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */     }
/*      */ 
/* 1478 */     switch (LA(1))
/*      */     {
/*      */     case '(':
/* 1481 */       match('(');
/*      */ 
/* 1483 */       if ((_tokenSet_4.member(LA(1))) && (_tokenSet_15.member(LA(2))) && (LA(3) >= '\003') && (LA(3) <= 'ÿ')) {
/* 1484 */         k = this.text.length();
/* 1485 */         mWS(false);
/* 1486 */         this.text.setLength(k);
/*      */       }
/* 1488 */       else if ((!_tokenSet_15.member(LA(1))) || (LA(2) < '\003') || (LA(2) > 'ÿ'))
/*      */       {
/* 1491 */         throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */       }
/*      */ 
/* 1496 */       switch (LA(1)) { case '"':
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
/* 1515 */         mARG(false);
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
/* 1519 */       case '`': } while (LA(1) == ',') {
/* 1520 */         match(',');
/*      */ 
/* 1522 */         switch (LA(1)) { case '\t':
/*      */         case '\n':
/*      */         case '\r':
/*      */         case ' ':
/* 1525 */           k = this.text.length();
/* 1526 */           mWS(false);
/* 1527 */           this.text.setLength(k);
/* 1528 */           break;
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
/* 1548 */           break;
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
/* 1552 */           throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */         }
/*      */ 
/* 1556 */         mARG(false); continue;
/*      */ 
/* 1569 */         break;
/*      */ 
/* 1573 */         throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */       }
/*      */ 
/* 1578 */       switch (LA(1)) { case '\t':
/*      */       case '\n':
/*      */       case '\r':
/*      */       case ' ':
/* 1581 */         k = this.text.length();
/* 1582 */         mWS(false);
/* 1583 */         this.text.setLength(k);
/* 1584 */         break;
/*      */       case ')':
/* 1588 */         break;
/*      */       default:
/* 1592 */         throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */       }
/*      */ 
/* 1596 */       match(')');
/* 1597 */       break;
/*      */     case '[':
/* 1602 */       int m = 0;
/*      */       while (true)
/*      */       {
/* 1605 */         if (LA(1) == '[') {
/* 1606 */           match('[');
/*      */ 
/* 1608 */           switch (LA(1)) { case '\t':
/*      */           case '\n':
/*      */           case '\r':
/*      */           case ' ':
/* 1611 */             k = this.text.length();
/* 1612 */             mWS(false);
/* 1613 */             this.text.setLength(k);
/* 1614 */             break;
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
/* 1634 */             break;
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
/* 1638 */             throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */           }
/*      */ 
/* 1642 */           mARG(false);
/*      */ 
/* 1644 */           switch (LA(1)) { case '\t':
/*      */           case '\n':
/*      */           case '\r':
/*      */           case ' ':
/* 1647 */             k = this.text.length();
/* 1648 */             mWS(false);
/* 1649 */             this.text.setLength(k);
/* 1650 */             break;
/*      */           case ']':
/* 1654 */             break;
/*      */           default:
/* 1658 */             throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */           }
/*      */ 
/* 1662 */           match(']');
/*      */         }
/*      */         else {
/* 1665 */           if (m >= 1) break; throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */         }
/*      */ 
/* 1668 */         m++;
/*      */       }
/*      */ 
/*      */     case '.':
/* 1675 */       match('.');
/* 1676 */       mID_ELEMENT(false);
/* 1677 */       break;
/*      */     default:
/* 1680 */       if ((LA(1) == '-') && (LA(2) == '>') && (_tokenSet_13.member(LA(3)))) {
/* 1681 */         match("->");
/* 1682 */         mID_ELEMENT(false);
/*      */       }
/* 1684 */       else if (_tokenSet_16.member(LA(1)))
/*      */       {
/* 1686 */         bool = true;
/* 1687 */         String str = this.generator.mapTreeId(localToken2.getText(), this.transInfo);
/*      */ 
/* 1689 */         if (str != null) {
/* 1690 */           this.text.setLength(j); this.text.append(str);
/*      */         }
/*      */ 
/* 1694 */         if ((_tokenSet_17.member(LA(1))) && (_tokenSet_16.member(LA(2))) && (this.transInfo != null) && (this.transInfo.refRuleRoot != null))
/*      */         {
/* 1696 */           switch (LA(1)) { case '\t':
/*      */           case '\n':
/*      */           case '\r':
/*      */           case ' ':
/* 1699 */             mWS(false);
/* 1700 */             break;
/*      */           case '=':
/* 1704 */             break;
/*      */           default:
/* 1708 */             throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */           }
/*      */ 
/* 1712 */           mVAR_ASSIGN(false);
/*      */         }
/* 1714 */         else if (!_tokenSet_18.member(LA(1)))
/*      */         {
/* 1717 */           throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */         }
/*      */ 
/*      */       }
/*      */       else
/*      */       {
/* 1723 */         throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */       }
/*      */       break;
/*      */     }
/* 1727 */     if ((paramBoolean) && (localToken1 == null) && (i != -1)) {
/* 1728 */       localToken1 = makeToken(i);
/* 1729 */       localToken1.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/*      */     }
/* 1731 */     this._returnToken = localToken1;
/* 1732 */     return bool;
/*      */   }
/*      */ 
/*      */   protected final void mAST_CTOR_ELEMENT(boolean paramBoolean)
/*      */     throws RecognitionException, CharStreamException, TokenStreamException
/*      */   {
/* 1739 */     Token localToken = null; int j = this.text.length();
/* 1740 */     int i = 11;
/*      */ 
/* 1743 */     if ((LA(1) == '"') && (LA(2) >= '\003') && (LA(2) <= 'ÿ') && (LA(3) >= '\003') && (LA(3) <= 'ÿ')) {
/* 1744 */       mSTRING(false);
/*      */     }
/* 1746 */     else if ((_tokenSet_19.member(LA(1))) && (LA(2) >= '\003') && (LA(2) <= 'ÿ')) {
/* 1747 */       mTREE_ELEMENT(false);
/*      */     }
/* 1749 */     else if ((LA(1) >= '0') && (LA(1) <= '9')) {
/* 1750 */       mINT(false);
/*      */     }
/*      */     else {
/* 1753 */       throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */     }
/*      */ 
/* 1756 */     if ((paramBoolean) && (localToken == null) && (i != -1)) {
/* 1757 */       localToken = makeToken(i);
/* 1758 */       localToken.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/*      */     }
/* 1760 */     this._returnToken = localToken;
/*      */   }
/*      */ 
/*      */   protected final void mINT(boolean paramBoolean) throws RecognitionException, CharStreamException, TokenStreamException {
/* 1764 */     Token localToken = null; int j = this.text.length();
/* 1765 */     int i = 26;
/*      */ 
/* 1769 */     int k = 0;
/*      */     while (true)
/*      */     {
/* 1772 */       if ((LA(1) >= '0') && (LA(1) <= '9')) {
/* 1773 */         mDIGIT(false);
/*      */       }
/*      */       else {
/* 1776 */         if (k >= 1) break; throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */       }
/*      */ 
/* 1779 */       k++;
/*      */     }
/*      */ 
/* 1782 */     if ((paramBoolean) && (localToken == null) && (i != -1)) {
/* 1783 */       localToken = makeToken(i);
/* 1784 */       localToken.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/*      */     }
/* 1786 */     this._returnToken = localToken;
/*      */   }
/*      */ 
/*      */   protected final void mARG(boolean paramBoolean) throws RecognitionException, CharStreamException, TokenStreamException {
/* 1790 */     Token localToken = null; int j = this.text.length();
/* 1791 */     int i = 16;
/*      */ 
/* 1795 */     switch (LA(1))
/*      */     {
/*      */     case '\'':
/* 1798 */       mCHAR(false);
/* 1799 */       break;
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
/* 1805 */       mINT_OR_FLOAT(false);
/* 1806 */       break;
/*      */     case '(':
/*      */     case ')':
/*      */     case '*':
/*      */     case '+':
/*      */     case ',':
/*      */     case '-':
/*      */     case '.':
/*      */     case '/':
/*      */     default:
/* 1809 */       if ((_tokenSet_19.member(LA(1))) && (LA(2) >= '\003') && (LA(2) <= 'ÿ') && (LA(3) >= '\003') && (LA(3) <= 'ÿ')) {
/* 1810 */         mTREE_ELEMENT(false);
/*      */       }
/* 1812 */       else if ((LA(1) == '"') && (LA(2) >= '\003') && (LA(2) <= 'ÿ') && (LA(3) >= '\003') && (LA(3) <= 'ÿ')) {
/* 1813 */         mSTRING(false);
/*      */       }
/*      */       else {
/* 1816 */         throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */       }
/*      */ 
/*      */       break;
/*      */     }
/*      */ 
/* 1823 */     while ((_tokenSet_20.member(LA(1))) && (_tokenSet_21.member(LA(2))) && (LA(3) >= '\003') && (LA(3) <= 'ÿ'))
/*      */     {
/* 1825 */       switch (LA(1)) { case '\t':
/*      */       case '\n':
/*      */       case '\r':
/*      */       case ' ':
/* 1828 */         mWS(false);
/* 1829 */         break;
/*      */       case '*':
/*      */       case '+':
/*      */       case '-':
/*      */       case '/':
/* 1833 */         break;
/*      */       default:
/* 1837 */         throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */       }
/*      */ 
/* 1842 */       switch (LA(1))
/*      */       {
/*      */       case '+':
/* 1845 */         match('+');
/* 1846 */         break;
/*      */       case '-':
/* 1850 */         match('-');
/* 1851 */         break;
/*      */       case '*':
/* 1855 */         match('*');
/* 1856 */         break;
/*      */       case '/':
/* 1860 */         match('/');
/* 1861 */         break;
/*      */       case ',':
/*      */       case '.':
/*      */       default:
/* 1865 */         throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */       }
/*      */ 
/* 1870 */       switch (LA(1)) { case '\t':
/*      */       case '\n':
/*      */       case '\r':
/*      */       case ' ':
/* 1873 */         mWS(false);
/* 1874 */         break;
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
/* 1894 */         break;
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
/* 1898 */         throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */       }
/*      */ 
/* 1902 */       mARG(false);
/*      */     }
/*      */ 
/* 1910 */     if ((paramBoolean) && (localToken == null) && (i != -1)) {
/* 1911 */       localToken = makeToken(i);
/* 1912 */       localToken.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/*      */     }
/* 1914 */     this._returnToken = localToken;
/*      */   }
/*      */ 
/*      */   protected final void mTEXT_ARG_ELEMENT(boolean paramBoolean) throws RecognitionException, CharStreamException, TokenStreamException {
/* 1918 */     Token localToken = null; int j = this.text.length();
/* 1919 */     int i = 14;
/*      */ 
/* 1922 */     switch (LA(1)) { case 'A':
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
/* 1938 */       mTEXT_ARG_ID_ELEMENT(false);
/* 1939 */       break;
/*      */     case '"':
/* 1943 */       mSTRING(false);
/* 1944 */       break;
/*      */     case '\'':
/* 1948 */       mCHAR(false);
/* 1949 */       break;
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
/* 1955 */       mINT_OR_FLOAT(false);
/* 1956 */       break;
/*      */     case '$':
/* 1960 */       mTEXT_ITEM(false);
/* 1961 */       break;
/*      */     case '+':
/* 1965 */       match('+');
/* 1966 */       break;
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
/* 1970 */       throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */     }
/*      */ 
/* 1973 */     if ((paramBoolean) && (localToken == null) && (i != -1)) {
/* 1974 */       localToken = makeToken(i);
/* 1975 */       localToken.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/*      */     }
/* 1977 */     this._returnToken = localToken;
/*      */   }
/*      */ 
/*      */   protected final void mTEXT_ARG_ID_ELEMENT(boolean paramBoolean) throws RecognitionException, CharStreamException, TokenStreamException {
/* 1981 */     Token localToken1 = null; int j = this.text.length();
/* 1982 */     int i = 15;
/*      */ 
/* 1984 */     Token localToken2 = null;
/*      */ 
/* 1986 */     mID(true);
/* 1987 */     localToken2 = this._returnToken;
/*      */     int k;
/* 1989 */     if ((_tokenSet_4.member(LA(1))) && (_tokenSet_22.member(LA(2)))) {
/* 1990 */       k = this.text.length();
/* 1991 */       mWS(false);
/* 1992 */       this.text.setLength(k);
/*      */     }
/* 1994 */     else if (!_tokenSet_22.member(LA(1)))
/*      */     {
/* 1997 */       throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */     }
/*      */ 
/* 2002 */     switch (LA(1))
/*      */     {
/*      */     case '(':
/* 2005 */       match('(');
/*      */ 
/* 2007 */       if ((_tokenSet_4.member(LA(1))) && (_tokenSet_23.member(LA(2))) && (LA(3) >= '\003') && (LA(3) <= 'ÿ')) {
/* 2008 */         k = this.text.length();
/* 2009 */         mWS(false);
/* 2010 */         this.text.setLength(k);
/*      */       }
/* 2012 */       else if ((!_tokenSet_23.member(LA(1))) || (LA(2) < '\003') || (LA(2) > 'ÿ'))
/*      */       {
/* 2015 */         throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */       }
/*      */ 
/* 2022 */       if ((_tokenSet_24.member(LA(1))) && (LA(2) >= '\003') && (LA(2) <= 'ÿ') && (LA(3) >= '\003') && (LA(3) <= 'ÿ')) {
/* 2023 */         mTEXT_ARG(false);
/*      */ 
/* 2027 */         while (LA(1) == ',') {
/* 2028 */           match(',');
/* 2029 */           mTEXT_ARG(false);
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/* 2045 */       switch (LA(1)) { case '\t':
/*      */       case '\n':
/*      */       case '\r':
/*      */       case ' ':
/* 2048 */         k = this.text.length();
/* 2049 */         mWS(false);
/* 2050 */         this.text.setLength(k);
/* 2051 */         break;
/*      */       case ')':
/* 2055 */         break;
/*      */       default:
/* 2059 */         throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */       }
/*      */ 
/* 2063 */       match(')');
/* 2064 */       break;
/*      */     case '[':
/* 2069 */       int m = 0;
/*      */       while (true)
/*      */       {
/* 2072 */         if (LA(1) == '[') {
/* 2073 */           match('[');
/*      */ 
/* 2075 */           if ((_tokenSet_4.member(LA(1))) && (_tokenSet_24.member(LA(2))) && (LA(3) >= '\003') && (LA(3) <= 'ÿ')) {
/* 2076 */             k = this.text.length();
/* 2077 */             mWS(false);
/* 2078 */             this.text.setLength(k);
/*      */           }
/* 2080 */           else if ((!_tokenSet_24.member(LA(1))) || (LA(2) < '\003') || (LA(2) > 'ÿ') || (LA(3) < '\003') || (LA(3) > 'ÿ'))
/*      */           {
/* 2083 */             throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */           }
/*      */ 
/* 2087 */           mTEXT_ARG(false);
/*      */ 
/* 2089 */           switch (LA(1)) { case '\t':
/*      */           case '\n':
/*      */           case '\r':
/*      */           case ' ':
/* 2092 */             k = this.text.length();
/* 2093 */             mWS(false);
/* 2094 */             this.text.setLength(k);
/* 2095 */             break;
/*      */           case ']':
/* 2099 */             break;
/*      */           default:
/* 2103 */             throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */           }
/*      */ 
/* 2107 */           match(']');
/*      */         }
/*      */         else {
/* 2110 */           if (m >= 1) break; throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */         }
/*      */ 
/* 2113 */         m++;
/*      */       }
/*      */ 
/*      */     case '.':
/* 2120 */       match('.');
/* 2121 */       mTEXT_ARG_ID_ELEMENT(false);
/* 2122 */       break;
/*      */     case '-':
/* 2126 */       match("->");
/* 2127 */       mTEXT_ARG_ID_ELEMENT(false);
/* 2128 */       break;
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
/* 2150 */       break;
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
/* 2154 */       throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */     }
/*      */ 
/* 2158 */     if ((paramBoolean) && (localToken1 == null) && (i != -1)) {
/* 2159 */       localToken1 = makeToken(i);
/* 2160 */       localToken1.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/*      */     }
/* 2162 */     this._returnToken = localToken1;
/*      */   }
/*      */ 
/*      */   protected final void mINT_OR_FLOAT(boolean paramBoolean) throws RecognitionException, CharStreamException, TokenStreamException {
/* 2166 */     Token localToken = null; int j = this.text.length();
/* 2167 */     int i = 27;
/*      */ 
/* 2171 */     int k = 0;
/*      */     while (true)
/*      */     {
/* 2174 */       if ((LA(1) >= '0') && (LA(1) <= '9') && (_tokenSet_25.member(LA(2)))) {
/* 2175 */         mDIGIT(false);
/*      */       }
/*      */       else {
/* 2178 */         if (k >= 1) break; throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */       }
/*      */ 
/* 2181 */       k++;
/*      */     }
/*      */ 
/* 2185 */     if ((LA(1) == 'L') && (_tokenSet_26.member(LA(2)))) {
/* 2186 */       match('L');
/*      */     }
/* 2188 */     else if ((LA(1) == 'l') && (_tokenSet_26.member(LA(2)))) {
/* 2189 */       match('l');
/*      */     } else {
/* 2191 */       if (LA(1) == '.') {
/* 2192 */         match('.');
/*      */ 
/* 2196 */         while ((LA(1) >= '0') && (LA(1) <= '9') && (_tokenSet_26.member(LA(2)))) {
/* 2197 */           mDIGIT(false);
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/* 2206 */       if (!_tokenSet_26.member(LA(1)))
/*      */       {
/* 2209 */         throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */       }
/*      */     }
/*      */ 
/* 2213 */     if ((paramBoolean) && (localToken == null) && (i != -1)) {
/* 2214 */       localToken = makeToken(i);
/* 2215 */       localToken.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/*      */     }
/* 2217 */     this._returnToken = localToken;
/*      */   }
/*      */ 
/*      */   protected final void mSL_COMMENT(boolean paramBoolean) throws RecognitionException, CharStreamException, TokenStreamException {
/* 2221 */     Token localToken = null; int j = this.text.length();
/* 2222 */     int i = 20;
/*      */ 
/* 2225 */     match("//");
/*      */ 
/* 2230 */     while ((LA(1) != '\n') && (LA(1) != '\r') && 
/* 2231 */       (LA(1) >= '\003') && (LA(1) <= 'ÿ') && (LA(2) >= '\003') && (LA(2) <= 'ÿ')) {
/* 2232 */       matchNot(65535);
/*      */     }
/*      */ 
/* 2241 */     if ((LA(1) == '\r') && (LA(2) == '\n')) {
/* 2242 */       match("\r\n");
/*      */     }
/* 2244 */     else if (LA(1) == '\n') {
/* 2245 */       match('\n');
/*      */     }
/* 2247 */     else if (LA(1) == '\r') {
/* 2248 */       match('\r');
/*      */     }
/*      */     else {
/* 2251 */       throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */     }
/*      */ 
/* 2255 */     newline();
/* 2256 */     if ((paramBoolean) && (localToken == null) && (i != -1)) {
/* 2257 */       localToken = makeToken(i);
/* 2258 */       localToken.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/*      */     }
/* 2260 */     this._returnToken = localToken;
/*      */   }
/*      */ 
/*      */   protected final void mML_COMMENT(boolean paramBoolean) throws RecognitionException, CharStreamException, TokenStreamException {
/* 2264 */     Token localToken = null; int j = this.text.length();
/* 2265 */     int i = 21;
/*      */ 
/* 2268 */     match("/*");
/*      */ 
/* 2273 */     while ((LA(1) != '*') || (LA(2) != '/')) {
/* 2274 */       if ((LA(1) == '\r') && (LA(2) == '\n') && (LA(3) >= '\003') && (LA(3) <= 'ÿ')) {
/* 2275 */         match('\r');
/* 2276 */         match('\n');
/* 2277 */         newline();
/*      */       }
/* 2279 */       else if ((LA(1) == '\r') && (LA(2) >= '\003') && (LA(2) <= 'ÿ') && (LA(3) >= '\003') && (LA(3) <= 'ÿ')) {
/* 2280 */         match('\r');
/* 2281 */         newline();
/*      */       }
/* 2283 */       else if ((LA(1) == '\n') && (LA(2) >= '\003') && (LA(2) <= 'ÿ') && (LA(3) >= '\003') && (LA(3) <= 'ÿ')) {
/* 2284 */         match('\n');
/* 2285 */         newline();
/*      */       } else {
/* 2287 */         if ((LA(1) < '\003') || (LA(1) > 'ÿ') || (LA(2) < '\003') || (LA(2) > 'ÿ') || (LA(3) < '\003') || (LA(3) > 'ÿ')) break;
/* 2288 */         matchNot(65535);
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 2296 */     match("*/");
/* 2297 */     if ((paramBoolean) && (localToken == null) && (i != -1)) {
/* 2298 */       localToken = makeToken(i);
/* 2299 */       localToken.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/*      */     }
/* 2301 */     this._returnToken = localToken;
/*      */   }
/*      */ 
/*      */   protected final void mESC(boolean paramBoolean) throws RecognitionException, CharStreamException, TokenStreamException {
/* 2305 */     Token localToken = null; int j = this.text.length();
/* 2306 */     int i = 24;
/*      */ 
/* 2309 */     match('\\');
/*      */ 
/* 2311 */     switch (LA(1))
/*      */     {
/*      */     case 'n':
/* 2314 */       match('n');
/* 2315 */       break;
/*      */     case 'r':
/* 2319 */       match('r');
/* 2320 */       break;
/*      */     case 't':
/* 2324 */       match('t');
/* 2325 */       break;
/*      */     case 'b':
/* 2329 */       match('b');
/* 2330 */       break;
/*      */     case 'f':
/* 2334 */       match('f');
/* 2335 */       break;
/*      */     case '"':
/* 2339 */       match('"');
/* 2340 */       break;
/*      */     case '\'':
/* 2344 */       match('\'');
/* 2345 */       break;
/*      */     case '\\':
/* 2349 */       match('\\');
/* 2350 */       break;
/*      */     case '0':
/*      */     case '1':
/*      */     case '2':
/*      */     case '3':
/* 2355 */       matchRange('0', '3');
/*      */ 
/* 2358 */       if ((LA(1) >= '0') && (LA(1) <= '9') && (LA(2) >= '\003') && (LA(2) <= 'ÿ')) {
/* 2359 */         mDIGIT(false);
/*      */ 
/* 2361 */         if ((LA(1) >= '0') && (LA(1) <= '9') && (LA(2) >= '\003') && (LA(2) <= 'ÿ')) {
/* 2362 */           mDIGIT(false);
/*      */         }
/* 2364 */         else if ((LA(1) < '\003') || (LA(1) > 'ÿ'))
/*      */         {
/* 2367 */           throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */         }
/*      */ 
/*      */       }
/* 2372 */       else if ((LA(1) < '\003') || (LA(1) > 'ÿ'))
/*      */       {
/* 2375 */         throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */       }
/*      */ 
/*      */       break;
/*      */     case '4':
/*      */     case '5':
/*      */     case '6':
/*      */     case '7':
/* 2384 */       matchRange('4', '7');
/*      */ 
/* 2387 */       if ((LA(1) >= '0') && (LA(1) <= '9') && (LA(2) >= '\003') && (LA(2) <= 'ÿ')) {
/* 2388 */         mDIGIT(false);
/*      */       }
/* 2390 */       else if ((LA(1) < '\003') || (LA(1) > 'ÿ'))
/*      */       {
/* 2393 */         throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */       }
/*      */ 
/*      */       break;
/*      */     default:
/* 2401 */       throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */     }
/*      */ 
/* 2405 */     if ((paramBoolean) && (localToken == null) && (i != -1)) {
/* 2406 */       localToken = makeToken(i);
/* 2407 */       localToken.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/*      */     }
/* 2409 */     this._returnToken = localToken;
/*      */   }
/*      */ 
/*      */   protected final void mDIGIT(boolean paramBoolean) throws RecognitionException, CharStreamException, TokenStreamException {
/* 2413 */     Token localToken = null; int j = this.text.length();
/* 2414 */     int i = 25;
/*      */ 
/* 2417 */     matchRange('0', '9');
/* 2418 */     if ((paramBoolean) && (localToken == null) && (i != -1)) {
/* 2419 */       localToken = makeToken(i);
/* 2420 */       localToken.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/*      */     }
/* 2422 */     this._returnToken = localToken;
/*      */   }
/*      */ 
/*      */   private static final long[] mk_tokenSet_0()
/*      */   {
/* 2427 */     long[] arrayOfLong = new long[8];
/* 2428 */     arrayOfLong[0] = -103079215112L;
/* 2429 */     for (int i = 1; i <= 3; i++) arrayOfLong[i] = -1L;
/* 2430 */     return arrayOfLong;
/*      */   }
/*      */ 
/*      */   private static final long[] mk_tokenSet_1() {
/* 2434 */     long[] arrayOfLong = new long[8];
/* 2435 */     arrayOfLong[0] = -145135534866440L;
/* 2436 */     for (int i = 1; i <= 3; i++) arrayOfLong[i] = -1L;
/* 2437 */     return arrayOfLong;
/*      */   }
/*      */ 
/*      */   private static final long[] mk_tokenSet_2() {
/* 2441 */     long[] arrayOfLong = new long[8];
/* 2442 */     arrayOfLong[0] = -141407503262728L;
/* 2443 */     for (int i = 1; i <= 3; i++) arrayOfLong[i] = -1L;
/* 2444 */     return arrayOfLong;
/*      */   }
/*      */ 
/*      */   private static final long[] mk_tokenSet_3() {
/* 2448 */     long[] arrayOfLong = { 4294977024L, 576460745995190270L, 0L, 0L, 0L };
/* 2449 */     return arrayOfLong;
/*      */   }
/*      */ 
/*      */   private static final long[] mk_tokenSet_4() {
/* 2453 */     long[] arrayOfLong = { 4294977024L, 0L, 0L, 0L, 0L };
/* 2454 */     return arrayOfLong;
/*      */   }
/*      */ 
/*      */   private static final long[] mk_tokenSet_5() {
/* 2458 */     long[] arrayOfLong = { 1103806604800L, 0L, 0L, 0L, 0L };
/* 2459 */     return arrayOfLong;
/*      */   }
/*      */ 
/*      */   private static final long[] mk_tokenSet_6() {
/* 2463 */     long[] arrayOfLong = { 287959436729787904L, 576460745995190270L, 0L, 0L, 0L };
/* 2464 */     return arrayOfLong;
/*      */   }
/*      */ 
/*      */   private static final long[] mk_tokenSet_7() {
/* 2468 */     long[] arrayOfLong = new long[8];
/* 2469 */     arrayOfLong[0] = -17179869192L;
/* 2470 */     arrayOfLong[1] = -268435457L;
/* 2471 */     for (int i = 2; i <= 3; i++) arrayOfLong[i] = -1L;
/* 2472 */     return arrayOfLong;
/*      */   }
/*      */ 
/*      */   private static final long[] mk_tokenSet_8() {
/* 2476 */     long[] arrayOfLong = new long[8];
/* 2477 */     arrayOfLong[0] = -549755813896L;
/* 2478 */     arrayOfLong[1] = -268435457L;
/* 2479 */     for (int i = 2; i <= 3; i++) arrayOfLong[i] = -1L;
/* 2480 */     return arrayOfLong;
/*      */   }
/*      */ 
/*      */   private static final long[] mk_tokenSet_9() {
/* 2484 */     long[] arrayOfLong = { 287948901175001088L, 576460745995190270L, 0L, 0L, 0L };
/* 2485 */     return arrayOfLong;
/*      */   }
/*      */ 
/*      */   private static final long[] mk_tokenSet_10() {
/* 2489 */     long[] arrayOfLong = { 287950056521213440L, 576460746129407998L, 0L, 0L, 0L };
/* 2490 */     return arrayOfLong;
/*      */   }
/*      */ 
/*      */   private static final long[] mk_tokenSet_11() {
/* 2494 */     long[] arrayOfLong = { 287958332923183104L, 576460745995190270L, 0L, 0L, 0L };
/* 2495 */     return arrayOfLong;
/*      */   }
/*      */ 
/*      */   private static final long[] mk_tokenSet_12() {
/* 2499 */     long[] arrayOfLong = { 287978128427460096L, 576460746532061182L, 0L, 0L, 0L };
/* 2500 */     return arrayOfLong;
/*      */   }
/*      */ 
/*      */   private static final long[] mk_tokenSet_13() {
/* 2504 */     long[] arrayOfLong = { 0L, 576460745995190270L, 0L, 0L, 0L };
/* 2505 */     return arrayOfLong;
/*      */   }
/*      */ 
/*      */   private static final long[] mk_tokenSet_14() {
/* 2509 */     long[] arrayOfLong = { 2306123388973753856L, 671088640L, 0L, 0L, 0L };
/* 2510 */     return arrayOfLong;
/*      */   }
/*      */ 
/*      */   private static final long[] mk_tokenSet_15() {
/* 2514 */     long[] arrayOfLong = { 287952805300282880L, 576460746129407998L, 0L, 0L, 0L };
/* 2515 */     return arrayOfLong;
/*      */   }
/*      */ 
/*      */   private static final long[] mk_tokenSet_16() {
/* 2519 */     long[] arrayOfLong = { 2306051920717948416L, 536870912L, 0L, 0L, 0L };
/* 2520 */     return arrayOfLong;
/*      */   }
/*      */ 
/*      */   private static final long[] mk_tokenSet_17() {
/* 2524 */     long[] arrayOfLong = { 2305843013508670976L, 0L, 0L, 0L, 0L };
/* 2525 */     return arrayOfLong;
/*      */   }
/*      */ 
/*      */   private static final long[] mk_tokenSet_18() {
/* 2529 */     long[] arrayOfLong = { 208911504254464L, 536870912L, 0L, 0L, 0L };
/* 2530 */     return arrayOfLong;
/*      */   }
/*      */ 
/*      */   private static final long[] mk_tokenSet_19() {
/* 2534 */     long[] arrayOfLong = { 1151051235328L, 576460746129407998L, 0L, 0L, 0L };
/* 2535 */     return arrayOfLong;
/*      */   }
/*      */ 
/*      */   private static final long[] mk_tokenSet_20() {
/* 2539 */     long[] arrayOfLong = { 189120294954496L, 0L, 0L, 0L, 0L };
/* 2540 */     return arrayOfLong;
/*      */   }
/*      */ 
/*      */   private static final long[] mk_tokenSet_21() {
/* 2544 */     long[] arrayOfLong = { 288139722277004800L, 576460746129407998L, 0L, 0L, 0L };
/* 2545 */     return arrayOfLong;
/*      */   }
/*      */ 
/*      */   private static final long[] mk_tokenSet_22() {
/* 2549 */     long[] arrayOfLong = { 288084781055354368L, 576460746666278910L, 0L, 0L, 0L };
/* 2550 */     return arrayOfLong;
/*      */   }
/*      */ 
/*      */   private static final long[] mk_tokenSet_23() {
/* 2554 */     long[] arrayOfLong = { 287960536241415680L, 576460745995190270L, 0L, 0L, 0L };
/* 2555 */     return arrayOfLong;
/*      */   }
/*      */ 
/*      */   private static final long[] mk_tokenSet_24() {
/* 2559 */     long[] arrayOfLong = { 287958337218160128L, 576460745995190270L, 0L, 0L, 0L };
/* 2560 */     return arrayOfLong;
/*      */   }
/*      */ 
/*      */   private static final long[] mk_tokenSet_25() {
/* 2564 */     long[] arrayOfLong = { 288228817078593024L, 576460746532061182L, 0L, 0L, 0L };
/* 2565 */     return arrayOfLong;
/*      */   }
/*      */ 
/*      */   private static final long[] mk_tokenSet_26() {
/* 2569 */     long[] arrayOfLong = { 288158448334415360L, 576460746532061182L, 0L, 0L, 0L };
/* 2570 */     return arrayOfLong;
/*      */   }
/*      */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     antlr.actions.csharp.ActionLexer
 * JD-Core Version:    0.6.2
 */