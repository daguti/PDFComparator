/*      */ package antlr.actions.cpp;
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
/* 2401 */   public static final BitSet _tokenSet_0 = new BitSet(mk_tokenSet_0());
/*      */ 
/* 2408 */   public static final BitSet _tokenSet_1 = new BitSet(mk_tokenSet_1());
/*      */ 
/* 2415 */   public static final BitSet _tokenSet_2 = new BitSet(mk_tokenSet_2());
/*      */ 
/* 2420 */   public static final BitSet _tokenSet_3 = new BitSet(mk_tokenSet_3());
/*      */ 
/* 2425 */   public static final BitSet _tokenSet_4 = new BitSet(mk_tokenSet_4());
/*      */ 
/* 2430 */   public static final BitSet _tokenSet_5 = new BitSet(mk_tokenSet_5());
/*      */ 
/* 2435 */   public static final BitSet _tokenSet_6 = new BitSet(mk_tokenSet_6());
/*      */ 
/* 2443 */   public static final BitSet _tokenSet_7 = new BitSet(mk_tokenSet_7());
/*      */ 
/* 2451 */   public static final BitSet _tokenSet_8 = new BitSet(mk_tokenSet_8());
/*      */ 
/* 2456 */   public static final BitSet _tokenSet_9 = new BitSet(mk_tokenSet_9());
/*      */ 
/* 2461 */   public static final BitSet _tokenSet_10 = new BitSet(mk_tokenSet_10());
/*      */ 
/* 2466 */   public static final BitSet _tokenSet_11 = new BitSet(mk_tokenSet_11());
/*      */ 
/* 2471 */   public static final BitSet _tokenSet_12 = new BitSet(mk_tokenSet_12());
/*      */ 
/* 2476 */   public static final BitSet _tokenSet_13 = new BitSet(mk_tokenSet_13());
/*      */ 
/* 2483 */   public static final BitSet _tokenSet_14 = new BitSet(mk_tokenSet_14());
/*      */ 
/* 2488 */   public static final BitSet _tokenSet_15 = new BitSet(mk_tokenSet_15());
/*      */ 
/* 2493 */   public static final BitSet _tokenSet_16 = new BitSet(mk_tokenSet_16());
/*      */ 
/* 2498 */   public static final BitSet _tokenSet_17 = new BitSet(mk_tokenSet_17());
/*      */ 
/* 2503 */   public static final BitSet _tokenSet_18 = new BitSet(mk_tokenSet_18());
/*      */ 
/* 2508 */   public static final BitSet _tokenSet_19 = new BitSet(mk_tokenSet_19());
/*      */ 
/* 2513 */   public static final BitSet _tokenSet_20 = new BitSet(mk_tokenSet_20());
/*      */ 
/* 2518 */   public static final BitSet _tokenSet_21 = new BitSet(mk_tokenSet_21());
/*      */ 
/* 2523 */   public static final BitSet _tokenSet_22 = new BitSet(mk_tokenSet_22());
/*      */ 
/* 2528 */   public static final BitSet _tokenSet_23 = new BitSet(mk_tokenSet_23());
/*      */ 
/* 2533 */   public static final BitSet _tokenSet_24 = new BitSet(mk_tokenSet_24());
/*      */ 
/* 2538 */   public static final BitSet _tokenSet_25 = new BitSet(mk_tokenSet_25());
/*      */ 
/* 2543 */   public static final BitSet _tokenSet_26 = new BitSet(mk_tokenSet_26());
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
/*      */         case ':':
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
/*  318 */         else if ((str1.equals("if")) || (str1.equals("define")) || (str1.equals("ifdef")) || (str1.equals("ifndef")) || (str1.equals("else")) || (str1.equals("elif")) || (str1.equals("endif")) || (str1.equals("warning")) || (str1.equals("error")) || (str1.equals("ident")) || (str1.equals("pragma")) || (str1.equals("include")))
/*      */         {
/*  331 */           this.text.setLength(j); this.text.append("#" + str1);
/*      */         }
/*      */ 
/*  336 */         if (_tokenSet_4.member(LA(1))) {
/*  337 */           mWS(false);
/*      */         }
/*      */ 
/*  344 */         if (LA(1) == '=') {
/*  345 */           mVAR_ASSIGN(false);
/*      */         }
/*      */ 
/*      */       }
/*  352 */       else if ((LA(1) == '#') && (LA(2) == '[')) {
/*  353 */         k = this.text.length();
/*  354 */         match('#');
/*  355 */         this.text.setLength(k);
/*  356 */         mAST_CONSTRUCTOR(true);
/*  357 */         localToken4 = this._returnToken;
/*      */       }
/*  359 */       else if ((LA(1) == '#') && (LA(2) == '#')) {
/*  360 */         match("##");
/*      */ 
/*  362 */         if (this.currentRule != null)
/*      */         {
/*  364 */           str1 = this.currentRule.getRuleName() + "_AST";
/*  365 */           this.text.setLength(j); this.text.append(str1);
/*      */ 
/*  367 */           if (this.transInfo != null) {
/*  368 */             this.transInfo.refRuleRoot = str1;
/*      */           }
/*      */         }
/*      */         else
/*      */         {
/*  373 */           reportWarning("\"##\" not valid in this context");
/*  374 */           this.text.setLength(j); this.text.append("##");
/*      */         }
/*      */ 
/*  378 */         if (_tokenSet_4.member(LA(1))) {
/*  379 */           mWS(false);
/*      */         }
/*      */ 
/*  386 */         if (LA(1) == '=') {
/*  387 */           mVAR_ASSIGN(false);
/*      */         }
/*      */ 
/*      */       }
/*      */       else
/*      */       {
/*  395 */         throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */       }
/*      */     }
/*  398 */     if ((paramBoolean) && (localToken1 == null) && (i != -1)) {
/*  399 */       localToken1 = makeToken(i);
/*  400 */       localToken1.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/*      */     }
/*  402 */     this._returnToken = localToken1;
/*      */   }
/*      */ 
/*      */   protected final void mTEXT_ITEM(boolean paramBoolean) throws RecognitionException, CharStreamException, TokenStreamException {
/*  406 */     Token localToken1 = null; int j = this.text.length();
/*  407 */     int i = 7;
/*      */ 
/*  409 */     Token localToken2 = null;
/*  410 */     Token localToken3 = null;
/*  411 */     Token localToken4 = null;
/*  412 */     Token localToken5 = null;
/*  413 */     Token localToken6 = null;
/*  414 */     Token localToken7 = null;
/*      */     String str1;
/*      */     String str2;
/*  416 */     if ((LA(1) == '$') && (LA(2) == 'F') && (LA(3) == 'O')) {
/*  417 */       match("$FOLLOW");
/*      */ 
/*  419 */       if ((_tokenSet_5.member(LA(1))) && (_tokenSet_6.member(LA(2))) && (LA(3) >= '\003') && (LA(3) <= 'ÿ'))
/*      */       {
/*  421 */         switch (LA(1)) { case '\t':
/*      */         case '\n':
/*      */         case '\r':
/*      */         case ' ':
/*  424 */           mWS(false);
/*  425 */           break;
/*      */         case '(':
/*  429 */           break;
/*      */         default:
/*  433 */           throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */         }
/*      */ 
/*  437 */         match('(');
/*  438 */         mTEXT_ARG(true);
/*  439 */         localToken6 = this._returnToken;
/*  440 */         match(')');
/*      */       }
/*      */ 
/*  447 */       str1 = this.currentRule.getRuleName();
/*  448 */       if (localToken6 != null) {
/*  449 */         str1 = localToken6.getText();
/*      */       }
/*  451 */       str2 = this.generator.getFOLLOWBitSet(str1, 1);
/*      */ 
/*  453 */       if (str2 == null) {
/*  454 */         reportError("$FOLLOW(" + str1 + ")" + ": unknown rule or bad lookahead computation");
/*      */       }
/*      */       else
/*      */       {
/*  458 */         this.text.setLength(j); this.text.append(str2);
/*      */       }
/*      */ 
/*      */     }
/*  462 */     else if ((LA(1) == '$') && (LA(2) == 'F') && (LA(3) == 'I')) {
/*  463 */       match("$FIRST");
/*      */ 
/*  465 */       if ((_tokenSet_5.member(LA(1))) && (_tokenSet_6.member(LA(2))) && (LA(3) >= '\003') && (LA(3) <= 'ÿ'))
/*      */       {
/*  467 */         switch (LA(1)) { case '\t':
/*      */         case '\n':
/*      */         case '\r':
/*      */         case ' ':
/*  470 */           mWS(false);
/*  471 */           break;
/*      */         case '(':
/*  475 */           break;
/*      */         default:
/*  479 */           throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */         }
/*      */ 
/*  483 */         match('(');
/*  484 */         mTEXT_ARG(true);
/*  485 */         localToken7 = this._returnToken;
/*  486 */         match(')');
/*      */       }
/*      */ 
/*  493 */       str1 = this.currentRule.getRuleName();
/*  494 */       if (localToken7 != null) {
/*  495 */         str1 = localToken7.getText();
/*      */       }
/*  497 */       str2 = this.generator.getFIRSTBitSet(str1, 1);
/*      */ 
/*  499 */       if (str2 == null) {
/*  500 */         reportError("$FIRST(" + str1 + ")" + ": unknown rule or bad lookahead computation");
/*      */       }
/*      */       else
/*      */       {
/*  504 */         this.text.setLength(j); this.text.append(str2);
/*      */       }
/*      */ 
/*      */     }
/*  508 */     else if ((LA(1) == '$') && (LA(2) == 'a')) {
/*  509 */       match("$append");
/*      */ 
/*  511 */       switch (LA(1)) { case '\t':
/*      */       case '\n':
/*      */       case '\r':
/*      */       case ' ':
/*  514 */         mWS(false);
/*  515 */         break;
/*      */       case '(':
/*  519 */         break;
/*      */       default:
/*  523 */         throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */       }
/*      */ 
/*  527 */       match('(');
/*  528 */       mTEXT_ARG(true);
/*  529 */       localToken2 = this._returnToken;
/*  530 */       match(')');
/*      */ 
/*  532 */       str1 = "text += " + localToken2.getText();
/*  533 */       this.text.setLength(j); this.text.append(str1);
/*      */     }
/*  536 */     else if ((LA(1) == '$') && (LA(2) == 's')) {
/*  537 */       match("$set");
/*      */ 
/*  539 */       if ((LA(1) == 'T') && (LA(2) == 'e')) {
/*  540 */         match("Text");
/*      */ 
/*  542 */         switch (LA(1)) { case '\t':
/*      */         case '\n':
/*      */         case '\r':
/*      */         case ' ':
/*  545 */           mWS(false);
/*  546 */           break;
/*      */         case '(':
/*  550 */           break;
/*      */         default:
/*  554 */           throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */         }
/*      */ 
/*  558 */         match('(');
/*  559 */         mTEXT_ARG(true);
/*  560 */         localToken3 = this._returnToken;
/*  561 */         match(')');
/*      */ 
/*  564 */         str1 = "{ text.erase(_begin); text += " + localToken3.getText() + "; }";
/*  565 */         this.text.setLength(j); this.text.append(str1);
/*      */       }
/*  568 */       else if ((LA(1) == 'T') && (LA(2) == 'o')) {
/*  569 */         match("Token");
/*      */ 
/*  571 */         switch (LA(1)) { case '\t':
/*      */         case '\n':
/*      */         case '\r':
/*      */         case ' ':
/*  574 */           mWS(false);
/*  575 */           break;
/*      */         case '(':
/*  579 */           break;
/*      */         default:
/*  583 */           throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */         }
/*      */ 
/*  587 */         match('(');
/*  588 */         mTEXT_ARG(true);
/*  589 */         localToken4 = this._returnToken;
/*  590 */         match(')');
/*      */ 
/*  592 */         str1 = "_token = " + localToken4.getText();
/*  593 */         this.text.setLength(j); this.text.append(str1);
/*      */       }
/*  596 */       else if ((LA(1) == 'T') && (LA(2) == 'y')) {
/*  597 */         match("Type");
/*      */ 
/*  599 */         switch (LA(1)) { case '\t':
/*      */         case '\n':
/*      */         case '\r':
/*      */         case ' ':
/*  602 */           mWS(false);
/*  603 */           break;
/*      */         case '(':
/*  607 */           break;
/*      */         default:
/*  611 */           throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */         }
/*      */ 
/*  615 */         match('(');
/*  616 */         mTEXT_ARG(true);
/*  617 */         localToken5 = this._returnToken;
/*  618 */         match(')');
/*      */ 
/*  620 */         str1 = "_ttype = " + localToken5.getText();
/*  621 */         this.text.setLength(j); this.text.append(str1);
/*      */       }
/*      */       else
/*      */       {
/*  625 */         throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */       }
/*      */ 
/*      */     }
/*  630 */     else if ((LA(1) == '$') && (LA(2) == 'g')) {
/*  631 */       match("$getText");
/*      */ 
/*  633 */       this.text.setLength(j); this.text.append("text.substr(_begin,text.length()-_begin)");
/*      */     }
/*      */     else
/*      */     {
/*  637 */       throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */     }
/*      */ 
/*  640 */     if ((paramBoolean) && (localToken1 == null) && (i != -1)) {
/*  641 */       localToken1 = makeToken(i);
/*  642 */       localToken1.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/*      */     }
/*  644 */     this._returnToken = localToken1;
/*      */   }
/*      */ 
/*      */   protected final void mCOMMENT(boolean paramBoolean) throws RecognitionException, CharStreamException, TokenStreamException {
/*  648 */     Token localToken = null; int j = this.text.length();
/*  649 */     int i = 19;
/*      */ 
/*  652 */     if ((LA(1) == '/') && (LA(2) == '/')) {
/*  653 */       mSL_COMMENT(false);
/*      */     }
/*  655 */     else if ((LA(1) == '/') && (LA(2) == '*')) {
/*  656 */       mML_COMMENT(false);
/*      */     }
/*      */     else {
/*  659 */       throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */     }
/*      */ 
/*  662 */     if ((paramBoolean) && (localToken == null) && (i != -1)) {
/*  663 */       localToken = makeToken(i);
/*  664 */       localToken.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/*      */     }
/*  666 */     this._returnToken = localToken;
/*      */   }
/*      */ 
/*      */   protected final void mSTRING(boolean paramBoolean) throws RecognitionException, CharStreamException, TokenStreamException {
/*  670 */     Token localToken = null; int j = this.text.length();
/*  671 */     int i = 23;
/*      */ 
/*  674 */     match('"');
/*      */     while (true)
/*      */     {
/*  678 */       if (LA(1) == '\\') {
/*  679 */         mESC(false);
/*      */       } else {
/*  681 */         if (!_tokenSet_7.member(LA(1))) break;
/*  682 */         matchNot('"');
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/*  690 */     match('"');
/*  691 */     if ((paramBoolean) && (localToken == null) && (i != -1)) {
/*  692 */       localToken = makeToken(i);
/*  693 */       localToken.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/*      */     }
/*  695 */     this._returnToken = localToken;
/*      */   }
/*      */ 
/*      */   protected final void mCHAR(boolean paramBoolean) throws RecognitionException, CharStreamException, TokenStreamException {
/*  699 */     Token localToken = null; int j = this.text.length();
/*  700 */     int i = 22;
/*      */ 
/*  703 */     match('\'');
/*      */ 
/*  705 */     if (LA(1) == '\\') {
/*  706 */       mESC(false);
/*      */     }
/*  708 */     else if (_tokenSet_8.member(LA(1))) {
/*  709 */       matchNot('\'');
/*      */     }
/*      */     else {
/*  712 */       throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */     }
/*      */ 
/*  716 */     match('\'');
/*  717 */     if ((paramBoolean) && (localToken == null) && (i != -1)) {
/*  718 */       localToken = makeToken(i);
/*  719 */       localToken.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/*      */     }
/*  721 */     this._returnToken = localToken;
/*      */   }
/*      */ 
/*      */   protected final void mTREE(boolean paramBoolean) throws RecognitionException, CharStreamException, TokenStreamException {
/*  725 */     Token localToken1 = null; int j = this.text.length();
/*  726 */     int i = 8;
/*      */ 
/*  728 */     Token localToken2 = null;
/*  729 */     Token localToken3 = null;
/*      */ 
/*  731 */     StringBuffer localStringBuffer = new StringBuffer();
/*  732 */     int m = 0;
/*  733 */     Vector localVector = new Vector(10);
/*      */ 
/*  736 */     int k = this.text.length();
/*  737 */     match('(');
/*  738 */     this.text.setLength(k);
/*      */ 
/*  740 */     switch (LA(1)) { case '\t':
/*      */     case '\n':
/*      */     case '\r':
/*      */     case ' ':
/*  743 */       k = this.text.length();
/*  744 */       mWS(false);
/*  745 */       this.text.setLength(k);
/*  746 */       break;
/*      */     case '"':
/*      */     case '#':
/*      */     case '(':
/*      */     case ':':
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
/*  764 */       break;
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
/*  768 */       throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */     }
/*      */ 
/*  772 */     k = this.text.length();
/*  773 */     mTREE_ELEMENT(true);
/*  774 */     this.text.setLength(k);
/*  775 */     localToken2 = this._returnToken;
/*      */ 
/*  777 */     localVector.appendElement(this.generator.processStringForASTConstructor(localToken2.getText()));
/*      */ 
/*  782 */     switch (LA(1)) { case '\t':
/*      */     case '\n':
/*      */     case '\r':
/*      */     case ' ':
/*  785 */       k = this.text.length();
/*  786 */       mWS(false);
/*  787 */       this.text.setLength(k);
/*  788 */       break;
/*      */     case ')':
/*      */     case ',':
/*  792 */       break;
/*      */     default:
/*  796 */       throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */     }
/*      */ 
/*  803 */     while (LA(1) == ',') {
/*  804 */       k = this.text.length();
/*  805 */       match(',');
/*  806 */       this.text.setLength(k);
/*      */ 
/*  808 */       switch (LA(1)) { case '\t':
/*      */       case '\n':
/*      */       case '\r':
/*      */       case ' ':
/*  811 */         k = this.text.length();
/*  812 */         mWS(false);
/*  813 */         this.text.setLength(k);
/*  814 */         break;
/*      */       case '"':
/*      */       case '#':
/*      */       case '(':
/*      */       case ':':
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
/*  832 */         break;
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
/*  836 */         throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */       }
/*      */ 
/*  840 */       k = this.text.length();
/*  841 */       mTREE_ELEMENT(true);
/*  842 */       this.text.setLength(k);
/*  843 */       localToken3 = this._returnToken;
/*      */ 
/*  845 */       localVector.appendElement(this.generator.processStringForASTConstructor(localToken3.getText()));
/*      */ 
/*  850 */       switch (LA(1)) { case '\t':
/*      */       case '\n':
/*      */       case '\r':
/*      */       case ' ':
/*  853 */         k = this.text.length();
/*  854 */         mWS(false);
/*  855 */         this.text.setLength(k);
/*  856 */         break;
/*      */       case ')':
/*      */       case ',':
/*  860 */         break;
/*      */       default:
/*  864 */         throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/*  875 */     this.text.setLength(j); this.text.append(this.generator.getASTCreateString(localVector));
/*  876 */     k = this.text.length();
/*  877 */     match(')');
/*  878 */     this.text.setLength(k);
/*  879 */     if ((paramBoolean) && (localToken1 == null) && (i != -1)) {
/*  880 */       localToken1 = makeToken(i);
/*  881 */       localToken1.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/*      */     }
/*  883 */     this._returnToken = localToken1;
/*      */   }
/*      */ 
/*      */   protected final void mWS(boolean paramBoolean) throws RecognitionException, CharStreamException, TokenStreamException {
/*  887 */     Token localToken = null; int j = this.text.length();
/*  888 */     int i = 28;
/*      */ 
/*  892 */     int k = 0;
/*      */     while (true)
/*      */     {
/*  895 */       if ((LA(1) == '\r') && (LA(2) == '\n')) {
/*  896 */         match('\r');
/*  897 */         match('\n');
/*  898 */         newline();
/*      */       }
/*  900 */       else if (LA(1) == ' ') {
/*  901 */         match(' ');
/*      */       }
/*  903 */       else if (LA(1) == '\t') {
/*  904 */         match('\t');
/*      */       }
/*  906 */       else if (LA(1) == '\r') {
/*  907 */         match('\r');
/*  908 */         newline();
/*      */       }
/*  910 */       else if (LA(1) == '\n') {
/*  911 */         match('\n');
/*  912 */         newline();
/*      */       }
/*      */       else {
/*  915 */         if (k >= 1) break; throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */       }
/*      */ 
/*  918 */       k++;
/*      */     }
/*      */ 
/*  921 */     if ((paramBoolean) && (localToken == null) && (i != -1)) {
/*  922 */       localToken = makeToken(i);
/*  923 */       localToken.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/*      */     }
/*  925 */     this._returnToken = localToken;
/*      */   }
/*      */ 
/*      */   protected final void mID(boolean paramBoolean) throws RecognitionException, CharStreamException, TokenStreamException {
/*  929 */     Token localToken = null; int j = this.text.length();
/*  930 */     int i = 17;
/*      */ 
/*  934 */     switch (LA(1)) { case 'a':
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
/*  943 */       matchRange('a', 'z');
/*  944 */       break;
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
/*  954 */       matchRange('A', 'Z');
/*  955 */       break;
/*      */     case '_':
/*  959 */       match('_');
/*  960 */       break;
/*      */     case ':':
/*  964 */       match("::");
/*  965 */       break;
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
/*  969 */       throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */     }
/*      */ 
/*  976 */     while (_tokenSet_9.member(LA(1)))
/*      */     {
/*  978 */       switch (LA(1)) { case 'a':
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
/*  987 */         matchRange('a', 'z');
/*  988 */         break;
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
/*  998 */         matchRange('A', 'Z');
/*  999 */         break;
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
/* 1005 */         matchRange('0', '9');
/* 1006 */         break;
/*      */       case '_':
/* 1010 */         match('_');
/* 1011 */         break;
/*      */       case ':':
/* 1015 */         match("::");
/* 1016 */         break;
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
/* 1020 */         throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 1031 */     if ((paramBoolean) && (localToken == null) && (i != -1)) {
/* 1032 */       localToken = makeToken(i);
/* 1033 */       localToken.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/*      */     }
/* 1035 */     this._returnToken = localToken;
/*      */   }
/*      */ 
/*      */   protected final void mVAR_ASSIGN(boolean paramBoolean) throws RecognitionException, CharStreamException, TokenStreamException {
/* 1039 */     Token localToken = null; int j = this.text.length();
/* 1040 */     int i = 18;
/*      */ 
/* 1043 */     match('=');
/*      */ 
/* 1047 */     if ((LA(1) != '=') && (this.transInfo != null) && (this.transInfo.refRuleRoot != null)) {
/* 1048 */       this.transInfo.assignToRoot = true;
/*      */     }
/*      */ 
/* 1051 */     if ((paramBoolean) && (localToken == null) && (i != -1)) {
/* 1052 */       localToken = makeToken(i);
/* 1053 */       localToken.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/*      */     }
/* 1055 */     this._returnToken = localToken;
/*      */   }
/*      */ 
/*      */   protected final void mAST_CONSTRUCTOR(boolean paramBoolean) throws RecognitionException, CharStreamException, TokenStreamException {
/* 1059 */     Token localToken1 = null; int j = this.text.length();
/* 1060 */     int i = 10;
/*      */ 
/* 1062 */     Token localToken2 = null;
/* 1063 */     Token localToken3 = null;
/*      */ 
/* 1065 */     int k = this.text.length();
/* 1066 */     match('[');
/* 1067 */     this.text.setLength(k);
/*      */ 
/* 1069 */     switch (LA(1)) { case '\t':
/*      */     case '\n':
/*      */     case '\r':
/*      */     case ' ':
/* 1072 */       k = this.text.length();
/* 1073 */       mWS(false);
/* 1074 */       this.text.setLength(k);
/* 1075 */       break;
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
/*      */     case ':':
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
/* 1095 */       break;
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
/* 1099 */       throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */     }
/*      */ 
/* 1103 */     k = this.text.length();
/* 1104 */     mAST_CTOR_ELEMENT(true);
/* 1105 */     this.text.setLength(k);
/* 1106 */     localToken2 = this._returnToken;
/*      */ 
/* 1108 */     switch (LA(1)) { case '\t':
/*      */     case '\n':
/*      */     case '\r':
/*      */     case ' ':
/* 1111 */       k = this.text.length();
/* 1112 */       mWS(false);
/* 1113 */       this.text.setLength(k);
/* 1114 */       break;
/*      */     case ',':
/*      */     case ']':
/* 1118 */       break;
/*      */     default:
/* 1122 */       throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */     }
/*      */ 
/* 1127 */     switch (LA(1))
/*      */     {
/*      */     case ',':
/* 1130 */       k = this.text.length();
/* 1131 */       match(',');
/* 1132 */       this.text.setLength(k);
/*      */ 
/* 1134 */       switch (LA(1)) { case '\t':
/*      */       case '\n':
/*      */       case '\r':
/*      */       case ' ':
/* 1137 */         k = this.text.length();
/* 1138 */         mWS(false);
/* 1139 */         this.text.setLength(k);
/* 1140 */         break;
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
/*      */       case ':':
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
/* 1160 */         break;
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
/* 1164 */         throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */       }
/*      */ 
/* 1168 */       k = this.text.length();
/* 1169 */       mAST_CTOR_ELEMENT(true);
/* 1170 */       this.text.setLength(k);
/* 1171 */       localToken3 = this._returnToken;
/*      */ 
/* 1173 */       switch (LA(1)) { case '\t':
/*      */       case '\n':
/*      */       case '\r':
/*      */       case ' ':
/* 1176 */         k = this.text.length();
/* 1177 */         mWS(false);
/* 1178 */         this.text.setLength(k);
/* 1179 */         break;
/*      */       case ']':
/* 1183 */         break;
/*      */       default:
/* 1187 */         throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */       }
/*      */ 
/*      */       break;
/*      */     case ']':
/* 1195 */       break;
/*      */     default:
/* 1199 */       throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */     }
/*      */ 
/* 1203 */     k = this.text.length();
/* 1204 */     match(']');
/* 1205 */     this.text.setLength(k);
/*      */ 
/* 1209 */     String str = this.generator.processStringForASTConstructor(localToken2.getText());
/*      */ 
/* 1213 */     if (localToken3 != null) {
/* 1214 */       str = str + "," + localToken3.getText();
/*      */     }
/* 1216 */     this.text.setLength(j); this.text.append(this.generator.getASTCreateString(null, str));
/*      */ 
/* 1218 */     if ((paramBoolean) && (localToken1 == null) && (i != -1)) {
/* 1219 */       localToken1 = makeToken(i);
/* 1220 */       localToken1.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/*      */     }
/* 1222 */     this._returnToken = localToken1;
/*      */   }
/*      */ 
/*      */   protected final void mTEXT_ARG(boolean paramBoolean) throws RecognitionException, CharStreamException, TokenStreamException {
/* 1226 */     Token localToken = null; int j = this.text.length();
/* 1227 */     int i = 13;
/*      */ 
/* 1231 */     switch (LA(1)) { case '\t':
/*      */     case '\n':
/*      */     case '\r':
/*      */     case ' ':
/* 1234 */       mWS(false);
/* 1235 */       break;
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
/*      */     case ':':
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
/* 1255 */       break;
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
/* 1259 */       throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */     }
/*      */ 
/* 1264 */     int k = 0;
/*      */     while (true)
/*      */     {
/* 1267 */       if ((_tokenSet_10.member(LA(1))) && (LA(2) >= '\003') && (LA(2) <= 'ÿ')) {
/* 1268 */         mTEXT_ARG_ELEMENT(false);
/*      */ 
/* 1270 */         if ((_tokenSet_4.member(LA(1))) && (_tokenSet_11.member(LA(2)))) {
/* 1271 */           mWS(false);
/*      */         }
/* 1273 */         else if (!_tokenSet_11.member(LA(1)))
/*      */         {
/* 1276 */           throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */         }
/*      */ 
/*      */       }
/*      */       else
/*      */       {
/* 1282 */         if (k >= 1) break; throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */       }
/*      */ 
/* 1285 */       k++;
/*      */     }
/*      */ 
/* 1288 */     if ((paramBoolean) && (localToken == null) && (i != -1)) {
/* 1289 */       localToken = makeToken(i);
/* 1290 */       localToken.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/*      */     }
/* 1292 */     this._returnToken = localToken;
/*      */   }
/*      */ 
/*      */   protected final void mTREE_ELEMENT(boolean paramBoolean) throws RecognitionException, CharStreamException, TokenStreamException {
/* 1296 */     Token localToken1 = null; int j = this.text.length();
/* 1297 */     int i = 9;
/*      */ 
/* 1299 */     Token localToken2 = null;
/*      */ 
/* 1302 */     switch (LA(1))
/*      */     {
/*      */     case '(':
/* 1305 */       mTREE(false);
/* 1306 */       break;
/*      */     case '[':
/* 1310 */       mAST_CONSTRUCTOR(false);
/* 1311 */       break;
/*      */     case ':':
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
/* 1328 */       mID_ELEMENT(false);
/* 1329 */       break;
/*      */     case '"':
/* 1333 */       mSTRING(false);
/* 1334 */       break;
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
/* 1337 */       if ((LA(1) == '#') && (LA(2) == '(')) {
/* 1338 */         k = this.text.length();
/* 1339 */         match('#');
/* 1340 */         this.text.setLength(k);
/* 1341 */         mTREE(false);
/*      */       }
/* 1343 */       else if ((LA(1) == '#') && (LA(2) == '[')) {
/* 1344 */         k = this.text.length();
/* 1345 */         match('#');
/* 1346 */         this.text.setLength(k);
/* 1347 */         mAST_CONSTRUCTOR(false);
/*      */       }
/*      */       else
/*      */       {
/*      */         String str;
/* 1349 */         if ((LA(1) == '#') && (_tokenSet_12.member(LA(2)))) {
/* 1350 */           k = this.text.length();
/* 1351 */           match('#');
/* 1352 */           this.text.setLength(k);
/* 1353 */           boolean bool = mID_ELEMENT(true);
/* 1354 */           localToken2 = this._returnToken;
/*      */ 
/* 1356 */           if (!bool)
/*      */           {
/* 1358 */             str = this.generator.mapTreeId(localToken2.getText(), null);
/*      */ 
/* 1360 */             if (str != null) {
/* 1361 */               this.text.setLength(j); this.text.append(str);
/*      */             }
/*      */           }
/*      */ 
/*      */         }
/* 1366 */         else if ((LA(1) == '#') && (LA(2) == '#')) {
/* 1367 */           match("##");
/*      */ 
/* 1369 */           if (this.currentRule != null)
/*      */           {
/* 1371 */             str = this.currentRule.getRuleName() + "_AST";
/* 1372 */             this.text.setLength(j); this.text.append(str);
/*      */           }
/*      */           else
/*      */           {
/* 1376 */             reportError("\"##\" not valid in this context");
/* 1377 */             this.text.setLength(j); this.text.append("##");
/*      */           }
/*      */         }
/*      */         else
/*      */         {
/* 1382 */           throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn()); } 
/*      */       }break;
/*      */     }
/* 1385 */     if ((paramBoolean) && (localToken1 == null) && (i != -1)) {
/* 1386 */       localToken1 = makeToken(i);
/* 1387 */       localToken1.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/*      */     }
/* 1389 */     this._returnToken = localToken1;
/*      */   }
/*      */ 
/*      */   protected final boolean mID_ELEMENT(boolean paramBoolean)
/*      */     throws RecognitionException, CharStreamException, TokenStreamException
/*      */   {
/* 1396 */     boolean bool = false;
/* 1397 */     Token localToken1 = null; int j = this.text.length();
/* 1398 */     int i = 12;
/*      */ 
/* 1400 */     Token localToken2 = null;
/*      */ 
/* 1402 */     mID(true);
/* 1403 */     localToken2 = this._returnToken;
/*      */     int k;
/* 1405 */     if ((_tokenSet_4.member(LA(1))) && (_tokenSet_13.member(LA(2)))) {
/* 1406 */       k = this.text.length();
/* 1407 */       mWS(false);
/* 1408 */       this.text.setLength(k);
/*      */     }
/* 1410 */     else if (!_tokenSet_13.member(LA(1)))
/*      */     {
/* 1413 */       throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */     }
/*      */ 
/* 1418 */     switch (LA(1))
/*      */     {
/*      */     case '(':
/*      */     case '<':
/* 1422 */       switch (LA(1))
/*      */       {
/*      */       case '<':
/* 1425 */         match('<');
/*      */ 
/* 1429 */         while (_tokenSet_14.member(LA(1))) {
/* 1430 */           matchNot('>');
/*      */         }
/*      */ 
/* 1438 */         match('>');
/* 1439 */         break;
/*      */       case '(':
/* 1443 */         break;
/*      */       default:
/* 1447 */         throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */       }
/*      */ 
/* 1451 */       match('(');
/*      */ 
/* 1453 */       if ((_tokenSet_4.member(LA(1))) && (_tokenSet_15.member(LA(2))) && (LA(3) >= '\003') && (LA(3) <= 'ÿ')) {
/* 1454 */         k = this.text.length();
/* 1455 */         mWS(false);
/* 1456 */         this.text.setLength(k);
/*      */       }
/* 1458 */       else if ((!_tokenSet_15.member(LA(1))) || (LA(2) < '\003') || (LA(2) > 'ÿ'))
/*      */       {
/* 1461 */         throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */       }
/*      */ 
/* 1466 */       switch (LA(1)) { case '"':
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
/*      */       case ':':
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
/* 1486 */         mARG(false);
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
/*      */       case ';':
/*      */       case '<':
/*      */       case '=':
/*      */       case '>':
/*      */       case '?':
/*      */       case '@':
/*      */       case '\\':
/*      */       case ']':
/*      */       case '^':
/* 1490 */       case '`': } while (LA(1) == ',') {
/* 1491 */         match(',');
/*      */ 
/* 1493 */         switch (LA(1)) { case '\t':
/*      */         case '\n':
/*      */         case '\r':
/*      */         case ' ':
/* 1496 */           k = this.text.length();
/* 1497 */           mWS(false);
/* 1498 */           this.text.setLength(k);
/* 1499 */           break;
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
/*      */         case ':':
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
/* 1520 */           break;
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
/* 1524 */           throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */         }
/*      */ 
/* 1528 */         mARG(false); continue;
/*      */ 
/* 1541 */         break;
/*      */ 
/* 1545 */         throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */       }
/*      */ 
/* 1550 */       switch (LA(1)) { case '\t':
/*      */       case '\n':
/*      */       case '\r':
/*      */       case ' ':
/* 1553 */         k = this.text.length();
/* 1554 */         mWS(false);
/* 1555 */         this.text.setLength(k);
/* 1556 */         break;
/*      */       case ')':
/* 1560 */         break;
/*      */       default:
/* 1564 */         throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */       }
/*      */ 
/* 1568 */       match(')');
/* 1569 */       break;
/*      */     case '[':
/* 1574 */       int m = 0;
/*      */       while (true)
/*      */       {
/* 1577 */         if (LA(1) == '[') {
/* 1578 */           match('[');
/*      */ 
/* 1580 */           switch (LA(1)) { case '\t':
/*      */           case '\n':
/*      */           case '\r':
/*      */           case ' ':
/* 1583 */             k = this.text.length();
/* 1584 */             mWS(false);
/* 1585 */             this.text.setLength(k);
/* 1586 */             break;
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
/*      */           case ':':
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
/* 1607 */             break;
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
/* 1611 */             throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */           }
/*      */ 
/* 1615 */           mARG(false);
/*      */ 
/* 1617 */           switch (LA(1)) { case '\t':
/*      */           case '\n':
/*      */           case '\r':
/*      */           case ' ':
/* 1620 */             k = this.text.length();
/* 1621 */             mWS(false);
/* 1622 */             this.text.setLength(k);
/* 1623 */             break;
/*      */           case ']':
/* 1627 */             break;
/*      */           default:
/* 1631 */             throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */           }
/*      */ 
/* 1635 */           match(']');
/*      */         }
/*      */         else {
/* 1638 */           if (m >= 1) break; throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */         }
/*      */ 
/* 1641 */         m++;
/*      */       }
/*      */ 
/*      */     case '.':
/* 1648 */       match('.');
/* 1649 */       mID_ELEMENT(false);
/* 1650 */       break;
/*      */     case ':':
/* 1654 */       match("::");
/* 1655 */       mID_ELEMENT(false);
/* 1656 */       break;
/*      */     default:
/* 1659 */       if ((LA(1) == '-') && (LA(2) == '>') && (_tokenSet_12.member(LA(3)))) {
/* 1660 */         match("->");
/* 1661 */         mID_ELEMENT(false);
/*      */       }
/* 1663 */       else if (_tokenSet_16.member(LA(1)))
/*      */       {
/* 1665 */         bool = true;
/* 1666 */         String str = this.generator.mapTreeId(localToken2.getText(), this.transInfo);
/*      */ 
/* 1668 */         if (str != null) {
/* 1669 */           this.text.setLength(j); this.text.append(str);
/*      */         }
/*      */ 
/* 1673 */         if ((_tokenSet_17.member(LA(1))) && (_tokenSet_16.member(LA(2))) && (this.transInfo != null) && (this.transInfo.refRuleRoot != null))
/*      */         {
/* 1675 */           switch (LA(1)) { case '\t':
/*      */           case '\n':
/*      */           case '\r':
/*      */           case ' ':
/* 1678 */             mWS(false);
/* 1679 */             break;
/*      */           case '=':
/* 1683 */             break;
/*      */           default:
/* 1687 */             throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */           }
/*      */ 
/* 1691 */           mVAR_ASSIGN(false);
/*      */         }
/* 1693 */         else if (!_tokenSet_18.member(LA(1)))
/*      */         {
/* 1696 */           throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */         }
/*      */ 
/*      */       }
/*      */       else
/*      */       {
/* 1702 */         throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */       }
/*      */       break;
/*      */     }
/* 1706 */     if ((paramBoolean) && (localToken1 == null) && (i != -1)) {
/* 1707 */       localToken1 = makeToken(i);
/* 1708 */       localToken1.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/*      */     }
/* 1710 */     this._returnToken = localToken1;
/* 1711 */     return bool;
/*      */   }
/*      */ 
/*      */   protected final void mAST_CTOR_ELEMENT(boolean paramBoolean)
/*      */     throws RecognitionException, CharStreamException, TokenStreamException
/*      */   {
/* 1718 */     Token localToken = null; int j = this.text.length();
/* 1719 */     int i = 11;
/*      */ 
/* 1722 */     if ((LA(1) == '"') && (LA(2) >= '\003') && (LA(2) <= 'ÿ') && (LA(3) >= '\003') && (LA(3) <= 'ÿ')) {
/* 1723 */       mSTRING(false);
/*      */     }
/* 1725 */     else if ((_tokenSet_19.member(LA(1))) && (LA(2) >= '\003') && (LA(2) <= 'ÿ')) {
/* 1726 */       mTREE_ELEMENT(false);
/*      */     }
/* 1728 */     else if ((LA(1) >= '0') && (LA(1) <= '9')) {
/* 1729 */       mINT(false);
/*      */     }
/*      */     else {
/* 1732 */       throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */     }
/*      */ 
/* 1735 */     if ((paramBoolean) && (localToken == null) && (i != -1)) {
/* 1736 */       localToken = makeToken(i);
/* 1737 */       localToken.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/*      */     }
/* 1739 */     this._returnToken = localToken;
/*      */   }
/*      */ 
/*      */   protected final void mINT(boolean paramBoolean) throws RecognitionException, CharStreamException, TokenStreamException {
/* 1743 */     Token localToken = null; int j = this.text.length();
/* 1744 */     int i = 26;
/*      */ 
/* 1748 */     int k = 0;
/*      */     while (true)
/*      */     {
/* 1751 */       if ((LA(1) >= '0') && (LA(1) <= '9')) {
/* 1752 */         mDIGIT(false);
/*      */       }
/*      */       else {
/* 1755 */         if (k >= 1) break; throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */       }
/*      */ 
/* 1758 */       k++;
/*      */     }
/*      */ 
/* 1761 */     if ((paramBoolean) && (localToken == null) && (i != -1)) {
/* 1762 */       localToken = makeToken(i);
/* 1763 */       localToken.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/*      */     }
/* 1765 */     this._returnToken = localToken;
/*      */   }
/*      */ 
/*      */   protected final void mARG(boolean paramBoolean) throws RecognitionException, CharStreamException, TokenStreamException {
/* 1769 */     Token localToken = null; int j = this.text.length();
/* 1770 */     int i = 16;
/*      */ 
/* 1774 */     switch (LA(1))
/*      */     {
/*      */     case '\'':
/* 1777 */       mCHAR(false);
/* 1778 */       break;
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
/* 1784 */       mINT_OR_FLOAT(false);
/* 1785 */       break;
/*      */     case '(':
/*      */     case ')':
/*      */     case '*':
/*      */     case '+':
/*      */     case ',':
/*      */     case '-':
/*      */     case '.':
/*      */     case '/':
/*      */     default:
/* 1788 */       if ((_tokenSet_19.member(LA(1))) && (LA(2) >= '\003') && (LA(2) <= 'ÿ') && (LA(3) >= '\003') && (LA(3) <= 'ÿ')) {
/* 1789 */         mTREE_ELEMENT(false);
/*      */       }
/* 1791 */       else if ((LA(1) == '"') && (LA(2) >= '\003') && (LA(2) <= 'ÿ') && (LA(3) >= '\003') && (LA(3) <= 'ÿ')) {
/* 1792 */         mSTRING(false);
/*      */       }
/*      */       else {
/* 1795 */         throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */       }
/*      */ 
/*      */       break;
/*      */     }
/*      */ 
/* 1802 */     while ((_tokenSet_20.member(LA(1))) && (_tokenSet_21.member(LA(2))) && (LA(3) >= '\003') && (LA(3) <= 'ÿ'))
/*      */     {
/* 1804 */       switch (LA(1)) { case '\t':
/*      */       case '\n':
/*      */       case '\r':
/*      */       case ' ':
/* 1807 */         mWS(false);
/* 1808 */         break;
/*      */       case '*':
/*      */       case '+':
/*      */       case '-':
/*      */       case '/':
/* 1812 */         break;
/*      */       default:
/* 1816 */         throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */       }
/*      */ 
/* 1821 */       switch (LA(1))
/*      */       {
/*      */       case '+':
/* 1824 */         match('+');
/* 1825 */         break;
/*      */       case '-':
/* 1829 */         match('-');
/* 1830 */         break;
/*      */       case '*':
/* 1834 */         match('*');
/* 1835 */         break;
/*      */       case '/':
/* 1839 */         match('/');
/* 1840 */         break;
/*      */       case ',':
/*      */       case '.':
/*      */       default:
/* 1844 */         throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */       }
/*      */ 
/* 1849 */       switch (LA(1)) { case '\t':
/*      */       case '\n':
/*      */       case '\r':
/*      */       case ' ':
/* 1852 */         mWS(false);
/* 1853 */         break;
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
/*      */       case ':':
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
/* 1874 */         break;
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
/* 1878 */         throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */       }
/*      */ 
/* 1882 */       mARG(false);
/*      */     }
/*      */ 
/* 1890 */     if ((paramBoolean) && (localToken == null) && (i != -1)) {
/* 1891 */       localToken = makeToken(i);
/* 1892 */       localToken.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/*      */     }
/* 1894 */     this._returnToken = localToken;
/*      */   }
/*      */ 
/*      */   protected final void mTEXT_ARG_ELEMENT(boolean paramBoolean) throws RecognitionException, CharStreamException, TokenStreamException {
/* 1898 */     Token localToken = null; int j = this.text.length();
/* 1899 */     int i = 14;
/*      */ 
/* 1902 */     switch (LA(1)) { case ':':
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
/* 1918 */       mTEXT_ARG_ID_ELEMENT(false);
/* 1919 */       break;
/*      */     case '"':
/* 1923 */       mSTRING(false);
/* 1924 */       break;
/*      */     case '\'':
/* 1928 */       mCHAR(false);
/* 1929 */       break;
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
/* 1935 */       mINT_OR_FLOAT(false);
/* 1936 */       break;
/*      */     case '$':
/* 1940 */       mTEXT_ITEM(false);
/* 1941 */       break;
/*      */     case '+':
/* 1945 */       match('+');
/* 1946 */       break;
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
/* 1950 */       throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */     }
/*      */ 
/* 1953 */     if ((paramBoolean) && (localToken == null) && (i != -1)) {
/* 1954 */       localToken = makeToken(i);
/* 1955 */       localToken.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/*      */     }
/* 1957 */     this._returnToken = localToken;
/*      */   }
/*      */ 
/*      */   protected final void mTEXT_ARG_ID_ELEMENT(boolean paramBoolean) throws RecognitionException, CharStreamException, TokenStreamException {
/* 1961 */     Token localToken1 = null; int j = this.text.length();
/* 1962 */     int i = 15;
/*      */ 
/* 1964 */     Token localToken2 = null;
/*      */ 
/* 1966 */     mID(true);
/* 1967 */     localToken2 = this._returnToken;
/*      */     int k;
/* 1969 */     if ((_tokenSet_4.member(LA(1))) && (_tokenSet_22.member(LA(2)))) {
/* 1970 */       k = this.text.length();
/* 1971 */       mWS(false);
/* 1972 */       this.text.setLength(k);
/*      */     }
/* 1974 */     else if (!_tokenSet_22.member(LA(1)))
/*      */     {
/* 1977 */       throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */     }
/*      */ 
/* 1982 */     switch (LA(1))
/*      */     {
/*      */     case '(':
/* 1985 */       match('(');
/*      */ 
/* 1987 */       if ((_tokenSet_4.member(LA(1))) && (_tokenSet_23.member(LA(2))) && (LA(3) >= '\003') && (LA(3) <= 'ÿ')) {
/* 1988 */         k = this.text.length();
/* 1989 */         mWS(false);
/* 1990 */         this.text.setLength(k);
/*      */       }
/* 1992 */       else if ((!_tokenSet_23.member(LA(1))) || (LA(2) < '\003') || (LA(2) > 'ÿ'))
/*      */       {
/* 1995 */         throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */       }
/*      */ 
/* 2002 */       if ((_tokenSet_24.member(LA(1))) && (LA(2) >= '\003') && (LA(2) <= 'ÿ') && (LA(3) >= '\003') && (LA(3) <= 'ÿ')) {
/* 2003 */         mTEXT_ARG(false);
/*      */ 
/* 2007 */         while (LA(1) == ',') {
/* 2008 */           match(',');
/* 2009 */           mTEXT_ARG(false);
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/* 2025 */       switch (LA(1)) { case '\t':
/*      */       case '\n':
/*      */       case '\r':
/*      */       case ' ':
/* 2028 */         k = this.text.length();
/* 2029 */         mWS(false);
/* 2030 */         this.text.setLength(k);
/* 2031 */         break;
/*      */       case ')':
/* 2035 */         break;
/*      */       default:
/* 2039 */         throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */       }
/*      */ 
/* 2043 */       match(')');
/* 2044 */       break;
/*      */     case '[':
/* 2049 */       int m = 0;
/*      */       while (true)
/*      */       {
/* 2052 */         if (LA(1) == '[') {
/* 2053 */           match('[');
/*      */ 
/* 2055 */           if ((_tokenSet_4.member(LA(1))) && (_tokenSet_24.member(LA(2))) && (LA(3) >= '\003') && (LA(3) <= 'ÿ')) {
/* 2056 */             k = this.text.length();
/* 2057 */             mWS(false);
/* 2058 */             this.text.setLength(k);
/*      */           }
/* 2060 */           else if ((!_tokenSet_24.member(LA(1))) || (LA(2) < '\003') || (LA(2) > 'ÿ') || (LA(3) < '\003') || (LA(3) > 'ÿ'))
/*      */           {
/* 2063 */             throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */           }
/*      */ 
/* 2067 */           mTEXT_ARG(false);
/*      */ 
/* 2069 */           switch (LA(1)) { case '\t':
/*      */           case '\n':
/*      */           case '\r':
/*      */           case ' ':
/* 2072 */             k = this.text.length();
/* 2073 */             mWS(false);
/* 2074 */             this.text.setLength(k);
/* 2075 */             break;
/*      */           case ']':
/* 2079 */             break;
/*      */           default:
/* 2083 */             throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */           }
/*      */ 
/* 2087 */           match(']');
/*      */         }
/*      */         else {
/* 2090 */           if (m >= 1) break; throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */         }
/*      */ 
/* 2093 */         m++;
/*      */       }
/*      */ 
/*      */     case '.':
/* 2100 */       match('.');
/* 2101 */       mTEXT_ARG_ID_ELEMENT(false);
/* 2102 */       break;
/*      */     case '-':
/* 2106 */       match("->");
/* 2107 */       mTEXT_ARG_ID_ELEMENT(false);
/* 2108 */       break;
/*      */     default:
/* 2111 */       if ((LA(1) == ':') && (LA(2) == ':') && (_tokenSet_12.member(LA(3)))) {
/* 2112 */         match("::");
/* 2113 */         mTEXT_ARG_ID_ELEMENT(false);
/*      */       }
/* 2115 */       else if (!_tokenSet_11.member(LA(1)))
/*      */       {
/* 2118 */         throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */       }
/*      */       break;
/*      */     }
/* 2122 */     if ((paramBoolean) && (localToken1 == null) && (i != -1)) {
/* 2123 */       localToken1 = makeToken(i);
/* 2124 */       localToken1.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/*      */     }
/* 2126 */     this._returnToken = localToken1;
/*      */   }
/*      */ 
/*      */   protected final void mINT_OR_FLOAT(boolean paramBoolean) throws RecognitionException, CharStreamException, TokenStreamException {
/* 2130 */     Token localToken = null; int j = this.text.length();
/* 2131 */     int i = 27;
/*      */ 
/* 2135 */     int k = 0;
/*      */     while (true)
/*      */     {
/* 2138 */       if ((LA(1) >= '0') && (LA(1) <= '9') && (_tokenSet_25.member(LA(2)))) {
/* 2139 */         mDIGIT(false);
/*      */       }
/*      */       else {
/* 2142 */         if (k >= 1) break; throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */       }
/*      */ 
/* 2145 */       k++;
/*      */     }
/*      */ 
/* 2149 */     if ((LA(1) == 'L') && (_tokenSet_26.member(LA(2)))) {
/* 2150 */       match('L');
/*      */     }
/* 2152 */     else if ((LA(1) == 'l') && (_tokenSet_26.member(LA(2)))) {
/* 2153 */       match('l');
/*      */     } else {
/* 2155 */       if (LA(1) == '.') {
/* 2156 */         match('.');
/*      */ 
/* 2160 */         while ((LA(1) >= '0') && (LA(1) <= '9') && (_tokenSet_26.member(LA(2)))) {
/* 2161 */           mDIGIT(false);
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/* 2170 */       if (!_tokenSet_26.member(LA(1)))
/*      */       {
/* 2173 */         throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */       }
/*      */     }
/*      */ 
/* 2177 */     if ((paramBoolean) && (localToken == null) && (i != -1)) {
/* 2178 */       localToken = makeToken(i);
/* 2179 */       localToken.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/*      */     }
/* 2181 */     this._returnToken = localToken;
/*      */   }
/*      */ 
/*      */   protected final void mSL_COMMENT(boolean paramBoolean) throws RecognitionException, CharStreamException, TokenStreamException {
/* 2185 */     Token localToken = null; int j = this.text.length();
/* 2186 */     int i = 20;
/*      */ 
/* 2189 */     match("//");
/*      */ 
/* 2194 */     while ((LA(1) != '\n') && (LA(1) != '\r') && 
/* 2195 */       (LA(1) >= '\003') && (LA(1) <= 'ÿ') && (LA(2) >= '\003') && (LA(2) <= 'ÿ')) {
/* 2196 */       matchNot(65535);
/*      */     }
/*      */ 
/* 2205 */     if ((LA(1) == '\r') && (LA(2) == '\n')) {
/* 2206 */       match("\r\n");
/*      */     }
/* 2208 */     else if (LA(1) == '\n') {
/* 2209 */       match('\n');
/*      */     }
/* 2211 */     else if (LA(1) == '\r') {
/* 2212 */       match('\r');
/*      */     }
/*      */     else {
/* 2215 */       throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */     }
/*      */ 
/* 2219 */     newline();
/* 2220 */     if ((paramBoolean) && (localToken == null) && (i != -1)) {
/* 2221 */       localToken = makeToken(i);
/* 2222 */       localToken.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/*      */     }
/* 2224 */     this._returnToken = localToken;
/*      */   }
/*      */ 
/*      */   protected final void mML_COMMENT(boolean paramBoolean) throws RecognitionException, CharStreamException, TokenStreamException {
/* 2228 */     Token localToken = null; int j = this.text.length();
/* 2229 */     int i = 21;
/*      */ 
/* 2232 */     match("/*");
/*      */ 
/* 2237 */     while ((LA(1) != '*') || (LA(2) != '/')) {
/* 2238 */       if ((LA(1) == '\r') && (LA(2) == '\n') && (LA(3) >= '\003') && (LA(3) <= 'ÿ')) {
/* 2239 */         match('\r');
/* 2240 */         match('\n');
/* 2241 */         newline();
/*      */       }
/* 2243 */       else if ((LA(1) == '\r') && (LA(2) >= '\003') && (LA(2) <= 'ÿ') && (LA(3) >= '\003') && (LA(3) <= 'ÿ')) {
/* 2244 */         match('\r');
/* 2245 */         newline();
/*      */       }
/* 2247 */       else if ((LA(1) == '\n') && (LA(2) >= '\003') && (LA(2) <= 'ÿ') && (LA(3) >= '\003') && (LA(3) <= 'ÿ')) {
/* 2248 */         match('\n');
/* 2249 */         newline();
/*      */       } else {
/* 2251 */         if ((LA(1) < '\003') || (LA(1) > 'ÿ') || (LA(2) < '\003') || (LA(2) > 'ÿ') || (LA(3) < '\003') || (LA(3) > 'ÿ')) break;
/* 2252 */         matchNot(65535);
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 2260 */     match("*/");
/* 2261 */     if ((paramBoolean) && (localToken == null) && (i != -1)) {
/* 2262 */       localToken = makeToken(i);
/* 2263 */       localToken.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/*      */     }
/* 2265 */     this._returnToken = localToken;
/*      */   }
/*      */ 
/*      */   protected final void mESC(boolean paramBoolean) throws RecognitionException, CharStreamException, TokenStreamException {
/* 2269 */     Token localToken = null; int j = this.text.length();
/* 2270 */     int i = 24;
/*      */ 
/* 2273 */     match('\\');
/*      */ 
/* 2275 */     switch (LA(1))
/*      */     {
/*      */     case 'n':
/* 2278 */       match('n');
/* 2279 */       break;
/*      */     case 'r':
/* 2283 */       match('r');
/* 2284 */       break;
/*      */     case 't':
/* 2288 */       match('t');
/* 2289 */       break;
/*      */     case 'v':
/* 2293 */       match('v');
/* 2294 */       break;
/*      */     case 'b':
/* 2298 */       match('b');
/* 2299 */       break;
/*      */     case 'f':
/* 2303 */       match('f');
/* 2304 */       break;
/*      */     case '"':
/* 2308 */       match('"');
/* 2309 */       break;
/*      */     case '\'':
/* 2313 */       match('\'');
/* 2314 */       break;
/*      */     case '\\':
/* 2318 */       match('\\');
/* 2319 */       break;
/*      */     case '0':
/*      */     case '1':
/*      */     case '2':
/*      */     case '3':
/* 2324 */       matchRange('0', '3');
/*      */ 
/* 2327 */       if ((LA(1) >= '0') && (LA(1) <= '9') && (LA(2) >= '\003') && (LA(2) <= 'ÿ')) {
/* 2328 */         mDIGIT(false);
/*      */ 
/* 2330 */         if ((LA(1) >= '0') && (LA(1) <= '9') && (LA(2) >= '\003') && (LA(2) <= 'ÿ')) {
/* 2331 */           mDIGIT(false);
/*      */         }
/* 2333 */         else if ((LA(1) < '\003') || (LA(1) > 'ÿ'))
/*      */         {
/* 2336 */           throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */         }
/*      */ 
/*      */       }
/* 2341 */       else if ((LA(1) < '\003') || (LA(1) > 'ÿ'))
/*      */       {
/* 2344 */         throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */       }
/*      */ 
/*      */       break;
/*      */     case '4':
/*      */     case '5':
/*      */     case '6':
/*      */     case '7':
/* 2353 */       matchRange('4', '7');
/*      */ 
/* 2356 */       if ((LA(1) >= '0') && (LA(1) <= '9') && (LA(2) >= '\003') && (LA(2) <= 'ÿ')) {
/* 2357 */         mDIGIT(false);
/*      */       }
/* 2359 */       else if ((LA(1) < '\003') || (LA(1) > 'ÿ'))
/*      */       {
/* 2362 */         throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */       }
/*      */ 
/*      */       break;
/*      */     default:
/* 2370 */       throw new NoViableAltForCharException(LA(1), getFilename(), getLine(), getColumn());
/*      */     }
/*      */ 
/* 2374 */     if ((paramBoolean) && (localToken == null) && (i != -1)) {
/* 2375 */       localToken = makeToken(i);
/* 2376 */       localToken.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/*      */     }
/* 2378 */     this._returnToken = localToken;
/*      */   }
/*      */ 
/*      */   protected final void mDIGIT(boolean paramBoolean) throws RecognitionException, CharStreamException, TokenStreamException {
/* 2382 */     Token localToken = null; int j = this.text.length();
/* 2383 */     int i = 25;
/*      */ 
/* 2386 */     matchRange('0', '9');
/* 2387 */     if ((paramBoolean) && (localToken == null) && (i != -1)) {
/* 2388 */       localToken = makeToken(i);
/* 2389 */       localToken.setText(new String(this.text.getBuffer(), j, this.text.length() - j));
/*      */     }
/* 2391 */     this._returnToken = localToken;
/*      */   }
/*      */ 
/*      */   private static final long[] mk_tokenSet_0()
/*      */   {
/* 2396 */     long[] arrayOfLong = new long[8];
/* 2397 */     arrayOfLong[0] = -103079215112L;
/* 2398 */     for (int i = 1; i <= 3; i++) arrayOfLong[i] = -1L;
/* 2399 */     return arrayOfLong;
/*      */   }
/*      */ 
/*      */   private static final long[] mk_tokenSet_1() {
/* 2403 */     long[] arrayOfLong = new long[8];
/* 2404 */     arrayOfLong[0] = -145135534866440L;
/* 2405 */     for (int i = 1; i <= 3; i++) arrayOfLong[i] = -1L;
/* 2406 */     return arrayOfLong;
/*      */   }
/*      */ 
/*      */   private static final long[] mk_tokenSet_2() {
/* 2410 */     long[] arrayOfLong = new long[8];
/* 2411 */     arrayOfLong[0] = -141407503262728L;
/* 2412 */     for (int i = 1; i <= 3; i++) arrayOfLong[i] = -1L;
/* 2413 */     return arrayOfLong;
/*      */   }
/*      */ 
/*      */   private static final long[] mk_tokenSet_3() {
/* 2417 */     long[] arrayOfLong = { 288230380446688768L, 576460745995190270L, 0L, 0L, 0L };
/* 2418 */     return arrayOfLong;
/*      */   }
/*      */ 
/*      */   private static final long[] mk_tokenSet_4() {
/* 2422 */     long[] arrayOfLong = { 4294977024L, 0L, 0L, 0L, 0L };
/* 2423 */     return arrayOfLong;
/*      */   }
/*      */ 
/*      */   private static final long[] mk_tokenSet_5() {
/* 2427 */     long[] arrayOfLong = { 1103806604800L, 0L, 0L, 0L, 0L };
/* 2428 */     return arrayOfLong;
/*      */   }
/*      */ 
/*      */   private static final long[] mk_tokenSet_6() {
/* 2432 */     long[] arrayOfLong = { 576189812881499648L, 576460745995190270L, 0L, 0L, 0L };
/* 2433 */     return arrayOfLong;
/*      */   }
/*      */ 
/*      */   private static final long[] mk_tokenSet_7() {
/* 2437 */     long[] arrayOfLong = new long[8];
/* 2438 */     arrayOfLong[0] = -17179869192L;
/* 2439 */     arrayOfLong[1] = -268435457L;
/* 2440 */     for (int i = 2; i <= 3; i++) arrayOfLong[i] = -1L;
/* 2441 */     return arrayOfLong;
/*      */   }
/*      */ 
/*      */   private static final long[] mk_tokenSet_8() {
/* 2445 */     long[] arrayOfLong = new long[8];
/* 2446 */     arrayOfLong[0] = -549755813896L;
/* 2447 */     arrayOfLong[1] = -268435457L;
/* 2448 */     for (int i = 2; i <= 3; i++) arrayOfLong[i] = -1L;
/* 2449 */     return arrayOfLong;
/*      */   }
/*      */ 
/*      */   private static final long[] mk_tokenSet_9() {
/* 2453 */     long[] arrayOfLong = { 576179277326712832L, 576460745995190270L, 0L, 0L, 0L };
/* 2454 */     return arrayOfLong;
/*      */   }
/*      */ 
/*      */   private static final long[] mk_tokenSet_10() {
/* 2458 */     long[] arrayOfLong = { 576188709074894848L, 576460745995190270L, 0L, 0L, 0L };
/* 2459 */     return arrayOfLong;
/*      */   }
/*      */ 
/*      */   private static final long[] mk_tokenSet_11() {
/* 2463 */     long[] arrayOfLong = { 576208504579171840L, 576460746532061182L, 0L, 0L, 0L };
/* 2464 */     return arrayOfLong;
/*      */   }
/*      */ 
/*      */   private static final long[] mk_tokenSet_12() {
/* 2468 */     long[] arrayOfLong = { 288230376151711744L, 576460745995190270L, 0L, 0L, 0L };
/* 2469 */     return arrayOfLong;
/*      */   }
/*      */ 
/*      */   private static final long[] mk_tokenSet_13() {
/* 2473 */     long[] arrayOfLong = { 3747275269732312576L, 671088640L, 0L, 0L, 0L };
/* 2474 */     return arrayOfLong;
/*      */   }
/*      */ 
/*      */   private static final long[] mk_tokenSet_14() {
/* 2478 */     long[] arrayOfLong = new long[8];
/* 2479 */     arrayOfLong[0] = -4611686018427387912L;
/* 2480 */     for (int i = 1; i <= 3; i++) arrayOfLong[i] = -1L;
/* 2481 */     return arrayOfLong;
/*      */   }
/*      */ 
/*      */   private static final long[] mk_tokenSet_15() {
/* 2485 */     long[] arrayOfLong = { 576183181451994624L, 576460746129407998L, 0L, 0L, 0L };
/* 2486 */     return arrayOfLong;
/*      */   }
/*      */ 
/*      */   private static final long[] mk_tokenSet_16() {
/* 2490 */     long[] arrayOfLong = { 2306051920717948416L, 536870912L, 0L, 0L, 0L };
/* 2491 */     return arrayOfLong;
/*      */   }
/*      */ 
/*      */   private static final long[] mk_tokenSet_17() {
/* 2495 */     long[] arrayOfLong = { 2305843013508670976L, 0L, 0L, 0L, 0L };
/* 2496 */     return arrayOfLong;
/*      */   }
/*      */ 
/*      */   private static final long[] mk_tokenSet_18() {
/* 2500 */     long[] arrayOfLong = { 208911504254464L, 536870912L, 0L, 0L, 0L };
/* 2501 */     return arrayOfLong;
/*      */   }
/*      */ 
/*      */   private static final long[] mk_tokenSet_19() {
/* 2505 */     long[] arrayOfLong = { 288231527202947072L, 576460746129407998L, 0L, 0L, 0L };
/* 2506 */     return arrayOfLong;
/*      */   }
/*      */ 
/*      */   private static final long[] mk_tokenSet_20() {
/* 2510 */     long[] arrayOfLong = { 189120294954496L, 0L, 0L, 0L, 0L };
/* 2511 */     return arrayOfLong;
/*      */   }
/*      */ 
/*      */   private static final long[] mk_tokenSet_21() {
/* 2515 */     long[] arrayOfLong = { 576370098428716544L, 576460746129407998L, 0L, 0L, 0L };
/* 2516 */     return arrayOfLong;
/*      */   }
/*      */ 
/*      */   private static final long[] mk_tokenSet_22() {
/* 2520 */     long[] arrayOfLong = { 576315157207066112L, 576460746666278910L, 0L, 0L, 0L };
/* 2521 */     return arrayOfLong;
/*      */   }
/*      */ 
/*      */   private static final long[] mk_tokenSet_23() {
/* 2525 */     long[] arrayOfLong = { 576190912393127424L, 576460745995190270L, 0L, 0L, 0L };
/* 2526 */     return arrayOfLong;
/*      */   }
/*      */ 
/*      */   private static final long[] mk_tokenSet_24() {
/* 2530 */     long[] arrayOfLong = { 576188713369871872L, 576460745995190270L, 0L, 0L, 0L };
/* 2531 */     return arrayOfLong;
/*      */   }
/*      */ 
/*      */   private static final long[] mk_tokenSet_25() {
/* 2535 */     long[] arrayOfLong = { 576459193230304768L, 576460746532061182L, 0L, 0L, 0L };
/* 2536 */     return arrayOfLong;
/*      */   }
/*      */ 
/*      */   private static final long[] mk_tokenSet_26() {
/* 2540 */     long[] arrayOfLong = { 576388824486127104L, 576460746532061182L, 0L, 0L, 0L };
/* 2541 */     return arrayOfLong;
/*      */   }
/*      */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     antlr.actions.cpp.ActionLexer
 * JD-Core Version:    0.6.2
 */