/*      */ package org.antlr.stringtemplate.language;
/*      */ 
/*      */ import antlr.ASTFactory;
/*      */ import antlr.ASTPair;
/*      */ import antlr.LLkParser;
/*      */ import antlr.NoViableAltException;
/*      */ import antlr.ParserSharedInputState;
/*      */ import antlr.RecognitionException;
/*      */ import antlr.Token;
/*      */ import antlr.TokenBuffer;
/*      */ import antlr.TokenStream;
/*      */ import antlr.TokenStreamException;
/*      */ import antlr.collections.impl.ASTArray;
/*      */ import antlr.collections.impl.BitSet;
/*      */ import java.util.HashMap;
/*      */ import java.util.Map;
/*      */ import org.antlr.stringtemplate.StringTemplate;
/*      */ import org.antlr.stringtemplate.StringTemplateGroup;
/*      */ 
/*      */ public class ActionParser extends LLkParser
/*      */   implements ActionParserTokenTypes
/*      */ {
/*   57 */   protected StringTemplate self = null;
/*      */ 
/* 1451 */   public static final String[] _tokenNames = { "<0>", "EOF", "<2>", "NULL_TREE_LOOKAHEAD", "APPLY", "MULTI_APPLY", "ARGS", "INCLUDE", "\"if\"", "VALUE", "TEMPLATE", "FUNCTION", "SINGLEVALUEARG", "LIST", "NOTHING", "SEMI", "LPAREN", "RPAREN", "\"elseif\"", "COMMA", "ID", "ASSIGN", "COLON", "NOT", "PLUS", "DOT", "\"first\"", "\"rest\"", "\"last\"", "\"length\"", "\"strip\"", "\"trunc\"", "\"super\"", "ANONYMOUS_TEMPLATE", "STRING", "INT", "LBRACK", "RBRACK", "DOTDOTDOT", "TEMPLATE_ARGS", "NESTED_ANONYMOUS_TEMPLATE", "ESC_CHAR", "WS", "WS_CHAR" };
/*      */ 
/* 1506 */   public static final BitSet _tokenSet_0 = new BitSet(mk_tokenSet_0());
/*      */ 
/* 1511 */   public static final BitSet _tokenSet_1 = new BitSet(mk_tokenSet_1());
/*      */ 
/* 1516 */   public static final BitSet _tokenSet_2 = new BitSet(mk_tokenSet_2());
/*      */ 
/* 1521 */   public static final BitSet _tokenSet_3 = new BitSet(mk_tokenSet_3());
/*      */ 
/* 1526 */   public static final BitSet _tokenSet_4 = new BitSet(mk_tokenSet_4());
/*      */ 
/* 1531 */   public static final BitSet _tokenSet_5 = new BitSet(mk_tokenSet_5());
/*      */ 
/* 1536 */   public static final BitSet _tokenSet_6 = new BitSet(mk_tokenSet_6());
/*      */ 
/* 1541 */   public static final BitSet _tokenSet_7 = new BitSet(mk_tokenSet_7());
/*      */ 
/* 1546 */   public static final BitSet _tokenSet_8 = new BitSet(mk_tokenSet_8());
/*      */ 
/* 1551 */   public static final BitSet _tokenSet_9 = new BitSet(mk_tokenSet_9());
/*      */ 
/* 1556 */   public static final BitSet _tokenSet_10 = new BitSet(mk_tokenSet_10());
/*      */ 
/* 1561 */   public static final BitSet _tokenSet_11 = new BitSet(mk_tokenSet_11());
/*      */ 
/* 1566 */   public static final BitSet _tokenSet_12 = new BitSet(mk_tokenSet_12());
/*      */ 
/* 1571 */   public static final BitSet _tokenSet_13 = new BitSet(mk_tokenSet_13());
/*      */ 
/* 1576 */   public static final BitSet _tokenSet_14 = new BitSet(mk_tokenSet_14());
/*      */ 
/*      */   public ActionParser(TokenStream lexer, StringTemplate self)
/*      */   {
/*   60 */     this(lexer, 2);
/*   61 */     this.self = self;
/*      */   }
/*      */ 
/*      */   public void reportError(RecognitionException e) {
/*   65 */     StringTemplateGroup group = this.self.getGroup();
/*   66 */     if (group == StringTemplate.defaultGroup) {
/*   67 */       this.self.error("action parse error; template context is " + this.self.getEnclosingInstanceStackString(), e);
/*      */     }
/*      */     else
/*   70 */       this.self.error("action parse error in group " + this.self.getGroup().getName() + " line " + this.self.getGroupFileLine() + "; template context is " + this.self.getEnclosingInstanceStackString(), e);
/*      */   }
/*      */ 
/*      */   protected ActionParser(TokenBuffer tokenBuf, int k)
/*      */   {
/*   75 */     super(tokenBuf, k);
/*   76 */     this.tokenNames = _tokenNames;
/*   77 */     buildTokenTypeASTClassMap();
/*   78 */     this.astFactory = new ASTFactory(getTokenTypeToASTClassMap());
/*      */   }
/*      */ 
/*      */   public ActionParser(TokenBuffer tokenBuf) {
/*   82 */     this(tokenBuf, 2);
/*      */   }
/*      */ 
/*      */   protected ActionParser(TokenStream lexer, int k) {
/*   86 */     super(lexer, k);
/*   87 */     this.tokenNames = _tokenNames;
/*   88 */     buildTokenTypeASTClassMap();
/*   89 */     this.astFactory = new ASTFactory(getTokenTypeToASTClassMap());
/*      */   }
/*      */ 
/*      */   public ActionParser(TokenStream lexer) {
/*   93 */     this(lexer, 2);
/*      */   }
/*      */ 
/*      */   public ActionParser(ParserSharedInputState state) {
/*   97 */     super(state, 2);
/*   98 */     this.tokenNames = _tokenNames;
/*   99 */     buildTokenTypeASTClassMap();
/*  100 */     this.astFactory = new ASTFactory(getTokenTypeToASTClassMap());
/*      */   }
/*      */ 
/*      */   public final Map action() throws RecognitionException, TokenStreamException {
/*  104 */     Map opts = null;
/*      */ 
/*  106 */     this.returnAST = null;
/*  107 */     ASTPair currentAST = new ASTPair();
/*  108 */     StringTemplateAST action_AST = null;
/*      */     try
/*      */     {
/*  111 */       switch (LA(1))
/*      */       {
/*      */       case 16:
/*      */       case 20:
/*      */       case 26:
/*      */       case 27:
/*      */       case 28:
/*      */       case 29:
/*      */       case 30:
/*      */       case 31:
/*      */       case 32:
/*      */       case 33:
/*      */       case 34:
/*      */       case 35:
/*      */       case 36:
/*  126 */         templatesExpr();
/*  127 */         this.astFactory.addASTChild(currentAST, this.returnAST);
/*      */ 
/*  129 */         switch (LA(1))
/*      */         {
/*      */         case 15:
/*  132 */           match(15);
/*  133 */           opts = optionList();
/*  134 */           this.astFactory.addASTChild(currentAST, this.returnAST);
/*  135 */           break;
/*      */         case 1:
/*  139 */           break;
/*      */         default:
/*  143 */           throw new NoViableAltException(LT(1), getFilename());
/*      */         }
/*      */ 
/*  147 */         action_AST = (StringTemplateAST)currentAST.root;
/*  148 */         break;
/*      */       case 8:
/*  152 */         StringTemplateAST tmp2_AST = null;
/*  153 */         tmp2_AST = (StringTemplateAST)this.astFactory.create(LT(1));
/*  154 */         this.astFactory.makeASTRoot(currentAST, tmp2_AST);
/*  155 */         match(8);
/*  156 */         match(16);
/*  157 */         ifCondition();
/*  158 */         this.astFactory.addASTChild(currentAST, this.returnAST);
/*  159 */         match(17);
/*  160 */         action_AST = (StringTemplateAST)currentAST.root;
/*  161 */         break;
/*      */       case 18:
/*  165 */         match(18);
/*  166 */         match(16);
/*  167 */         ifCondition();
/*  168 */         this.astFactory.addASTChild(currentAST, this.returnAST);
/*  169 */         match(17);
/*  170 */         action_AST = (StringTemplateAST)currentAST.root;
/*  171 */         break;
/*      */       case 9:
/*      */       case 10:
/*      */       case 11:
/*      */       case 12:
/*      */       case 13:
/*      */       case 14:
/*      */       case 15:
/*      */       case 17:
/*      */       case 19:
/*      */       case 21:
/*      */       case 22:
/*      */       case 23:
/*      */       case 24:
/*      */       case 25:
/*      */       default:
/*  175 */         throw new NoViableAltException(LT(1), getFilename());
/*      */       }
/*      */     }
/*      */     catch (RecognitionException ex)
/*      */     {
/*  180 */       if (this.inputState.guessing == 0) {
/*  181 */         reportError(ex);
/*  182 */         recover(ex, _tokenSet_0);
/*      */       } else {
/*  184 */         throw ex;
/*      */       }
/*      */     }
/*  187 */     this.returnAST = action_AST;
/*  188 */     return opts;
/*      */   }
/*      */ 
/*      */   public final void templatesExpr() throws RecognitionException, TokenStreamException
/*      */   {
/*  193 */     this.returnAST = null;
/*  194 */     ASTPair currentAST = new ASTPair();
/*  195 */     StringTemplateAST templatesExpr_AST = null;
/*  196 */     Token c = null;
/*  197 */     StringTemplateAST c_AST = null;
/*      */     try
/*      */     {
/*  200 */       boolean synPredMatched10 = false;
/*  201 */       if ((_tokenSet_1.member(LA(1))) && (_tokenSet_2.member(LA(2)))) {
/*  202 */         int _m10 = mark();
/*  203 */         synPredMatched10 = true;
/*  204 */         this.inputState.guessing += 1;
/*      */         try
/*      */         {
/*  207 */           parallelArrayTemplateApplication();
/*      */         }
/*      */         catch (RecognitionException pe)
/*      */         {
/*  211 */           synPredMatched10 = false;
/*      */         }
/*  213 */         rewind(_m10);
/*  214 */         this.inputState.guessing -= 1;
/*      */       }
/*  216 */       if (synPredMatched10) {
/*  217 */         parallelArrayTemplateApplication();
/*  218 */         this.astFactory.addASTChild(currentAST, this.returnAST);
/*  219 */         templatesExpr_AST = (StringTemplateAST)currentAST.root;
/*      */       }
/*  221 */       else if ((_tokenSet_1.member(LA(1))) && (_tokenSet_3.member(LA(2)))) {
/*  222 */         expr();
/*  223 */         this.astFactory.addASTChild(currentAST, this.returnAST);
/*      */ 
/*  227 */         while (LA(1) == 22) {
/*  228 */           c = LT(1);
/*  229 */           c_AST = (StringTemplateAST)this.astFactory.create(c);
/*  230 */           this.astFactory.makeASTRoot(currentAST, c_AST);
/*  231 */           match(22);
/*  232 */           if (this.inputState.guessing == 0) {
/*  233 */             c_AST.setType(4);
/*      */           }
/*  235 */           template();
/*  236 */           this.astFactory.addASTChild(currentAST, this.returnAST);
/*      */ 
/*  240 */           while (LA(1) == 19) {
/*  241 */             match(19);
/*  242 */             template();
/*  243 */             this.astFactory.addASTChild(currentAST, this.returnAST);
/*      */           }
/*      */ 
/*      */         }
/*      */ 
/*  258 */         templatesExpr_AST = (StringTemplateAST)currentAST.root;
/*      */       }
/*      */       else {
/*  261 */         throw new NoViableAltException(LT(1), getFilename());
/*      */       }
/*      */     }
/*      */     catch (RecognitionException ex)
/*      */     {
/*  266 */       if (this.inputState.guessing == 0) {
/*  267 */         reportError(ex);
/*  268 */         recover(ex, _tokenSet_4);
/*      */       } else {
/*  270 */         throw ex;
/*      */       }
/*      */     }
/*  273 */     this.returnAST = templatesExpr_AST;
/*      */   }
/*      */ 
/*      */   public final Map optionList() throws RecognitionException, TokenStreamException {
/*  277 */     Map opts = new HashMap();
/*      */ 
/*  279 */     this.returnAST = null;
/*  280 */     ASTPair currentAST = new ASTPair();
/*  281 */     StringTemplateAST optionList_AST = null;
/*      */     try
/*      */     {
/*  284 */       option(opts);
/*      */ 
/*  288 */       while (LA(1) == 19) {
/*  289 */         StringTemplateAST tmp9_AST = null;
/*  290 */         tmp9_AST = (StringTemplateAST)this.astFactory.create(LT(1));
/*  291 */         match(19);
/*  292 */         option(opts);
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (RecognitionException ex)
/*      */     {
/*  302 */       if (this.inputState.guessing == 0) {
/*  303 */         reportError(ex);
/*  304 */         recover(ex, _tokenSet_0);
/*      */       } else {
/*  306 */         throw ex;
/*      */       }
/*      */     }
/*  309 */     this.returnAST = optionList_AST;
/*  310 */     return opts;
/*      */   }
/*      */ 
/*      */   public final void ifCondition() throws RecognitionException, TokenStreamException
/*      */   {
/*  315 */     this.returnAST = null;
/*  316 */     ASTPair currentAST = new ASTPair();
/*  317 */     StringTemplateAST ifCondition_AST = null;
/*      */     try
/*      */     {
/*  320 */       switch (LA(1))
/*      */       {
/*      */       case 16:
/*      */       case 20:
/*      */       case 26:
/*      */       case 27:
/*      */       case 28:
/*      */       case 29:
/*      */       case 30:
/*      */       case 31:
/*      */       case 32:
/*      */       case 33:
/*      */       case 34:
/*      */       case 35:
/*      */       case 36:
/*  335 */         ifAtom();
/*  336 */         this.astFactory.addASTChild(currentAST, this.returnAST);
/*  337 */         ifCondition_AST = (StringTemplateAST)currentAST.root;
/*  338 */         break;
/*      */       case 23:
/*  342 */         StringTemplateAST tmp10_AST = null;
/*  343 */         tmp10_AST = (StringTemplateAST)this.astFactory.create(LT(1));
/*  344 */         this.astFactory.makeASTRoot(currentAST, tmp10_AST);
/*  345 */         match(23);
/*  346 */         ifAtom();
/*  347 */         this.astFactory.addASTChild(currentAST, this.returnAST);
/*  348 */         ifCondition_AST = (StringTemplateAST)currentAST.root;
/*  349 */         break;
/*      */       case 17:
/*      */       case 18:
/*      */       case 19:
/*      */       case 21:
/*      */       case 22:
/*      */       case 24:
/*      */       case 25:
/*      */       default:
/*  353 */         throw new NoViableAltException(LT(1), getFilename());
/*      */       }
/*      */     }
/*      */     catch (RecognitionException ex)
/*      */     {
/*  358 */       if (this.inputState.guessing == 0) {
/*  359 */         reportError(ex);
/*  360 */         recover(ex, _tokenSet_5);
/*      */       } else {
/*  362 */         throw ex;
/*      */       }
/*      */     }
/*  365 */     this.returnAST = ifCondition_AST;
/*      */   }
/*      */ 
/*      */   public final void option(Map opts)
/*      */     throws RecognitionException, TokenStreamException
/*      */   {
/*  372 */     this.returnAST = null;
/*  373 */     ASTPair currentAST = new ASTPair();
/*  374 */     StringTemplateAST option_AST = null;
/*  375 */     Token i = null;
/*  376 */     StringTemplateAST i_AST = null;
/*  377 */     StringTemplateAST e_AST = null;
/*      */ 
/*  379 */     Object v = null;
/*      */     try
/*      */     {
/*  383 */       i = LT(1);
/*  384 */       i_AST = (StringTemplateAST)this.astFactory.create(i);
/*  385 */       this.astFactory.addASTChild(currentAST, i_AST);
/*  386 */       match(20);
/*      */ 
/*  388 */       switch (LA(1))
/*      */       {
/*      */       case 21:
/*  391 */         StringTemplateAST tmp11_AST = null;
/*  392 */         tmp11_AST = (StringTemplateAST)this.astFactory.create(LT(1));
/*  393 */         this.astFactory.addASTChild(currentAST, tmp11_AST);
/*  394 */         match(21);
/*  395 */         nonAlternatingTemplateExpr();
/*  396 */         e_AST = (StringTemplateAST)this.returnAST;
/*  397 */         this.astFactory.addASTChild(currentAST, this.returnAST);
/*  398 */         if (this.inputState.guessing == 0)
/*  399 */           v = e_AST; break;
/*      */       case 1:
/*      */       case 19:
/*  406 */         if (this.inputState.guessing == 0)
/*  407 */           v = "empty expr option"; break;
/*      */       default:
/*  413 */         throw new NoViableAltException(LT(1), getFilename());
/*      */       }
/*      */ 
/*  417 */       if (this.inputState.guessing == 0) {
/*  418 */         opts.put(i_AST.getText(), v);
/*      */       }
/*  420 */       option_AST = (StringTemplateAST)currentAST.root;
/*      */     }
/*      */     catch (RecognitionException ex) {
/*  423 */       if (this.inputState.guessing == 0) {
/*  424 */         reportError(ex);
/*  425 */         recover(ex, _tokenSet_6);
/*      */       } else {
/*  427 */         throw ex;
/*      */       }
/*      */     }
/*  430 */     this.returnAST = option_AST;
/*      */   }
/*      */ 
/*      */   public final void nonAlternatingTemplateExpr() throws RecognitionException, TokenStreamException
/*      */   {
/*  435 */     this.returnAST = null;
/*  436 */     ASTPair currentAST = new ASTPair();
/*  437 */     StringTemplateAST nonAlternatingTemplateExpr_AST = null;
/*  438 */     Token c = null;
/*  439 */     StringTemplateAST c_AST = null;
/*      */     try
/*      */     {
/*  442 */       expr();
/*  443 */       this.astFactory.addASTChild(currentAST, this.returnAST);
/*      */ 
/*  447 */       while (LA(1) == 22) {
/*  448 */         c = LT(1);
/*  449 */         c_AST = (StringTemplateAST)this.astFactory.create(c);
/*  450 */         this.astFactory.makeASTRoot(currentAST, c_AST);
/*  451 */         match(22);
/*  452 */         if (this.inputState.guessing == 0) {
/*  453 */           c_AST.setType(4);
/*      */         }
/*  455 */         template();
/*  456 */         this.astFactory.addASTChild(currentAST, this.returnAST);
/*      */       }
/*      */ 
/*  464 */       nonAlternatingTemplateExpr_AST = (StringTemplateAST)currentAST.root;
/*      */     }
/*      */     catch (RecognitionException ex) {
/*  467 */       if (this.inputState.guessing == 0) {
/*  468 */         reportError(ex);
/*  469 */         recover(ex, _tokenSet_7);
/*      */       } else {
/*  471 */         throw ex;
/*      */       }
/*      */     }
/*  474 */     this.returnAST = nonAlternatingTemplateExpr_AST;
/*      */   }
/*      */ 
/*      */   public final void parallelArrayTemplateApplication() throws RecognitionException, TokenStreamException
/*      */   {
/*  479 */     this.returnAST = null;
/*  480 */     ASTPair currentAST = new ASTPair();
/*  481 */     StringTemplateAST parallelArrayTemplateApplication_AST = null;
/*  482 */     Token c = null;
/*  483 */     StringTemplateAST c_AST = null;
/*      */     try
/*      */     {
/*  486 */       expr();
/*  487 */       this.astFactory.addASTChild(currentAST, this.returnAST);
/*      */ 
/*  489 */       int _cnt17 = 0;
/*      */       while (true)
/*      */       {
/*  492 */         if (LA(1) == 19) {
/*  493 */           match(19);
/*  494 */           expr();
/*  495 */           this.astFactory.addASTChild(currentAST, this.returnAST);
/*      */         }
/*      */         else {
/*  498 */           if (_cnt17 >= 1) break; throw new NoViableAltException(LT(1), getFilename());
/*      */         }
/*      */ 
/*  501 */         _cnt17++;
/*      */       }
/*      */ 
/*  504 */       c = LT(1);
/*  505 */       c_AST = (StringTemplateAST)this.astFactory.create(c);
/*  506 */       this.astFactory.addASTChild(currentAST, c_AST);
/*  507 */       match(22);
/*  508 */       anonymousTemplate();
/*  509 */       this.astFactory.addASTChild(currentAST, this.returnAST);
/*  510 */       if (this.inputState.guessing == 0) {
/*  511 */         parallelArrayTemplateApplication_AST = (StringTemplateAST)currentAST.root;
/*  512 */         parallelArrayTemplateApplication_AST = (StringTemplateAST)this.astFactory.make(new ASTArray(2).add((StringTemplateAST)this.astFactory.create(5, "MULTI_APPLY")).add(parallelArrayTemplateApplication_AST));
/*      */ 
/*  514 */         currentAST.root = parallelArrayTemplateApplication_AST;
/*  515 */         currentAST.child = ((parallelArrayTemplateApplication_AST != null) && (parallelArrayTemplateApplication_AST.getFirstChild() != null) ? parallelArrayTemplateApplication_AST.getFirstChild() : parallelArrayTemplateApplication_AST);
/*      */ 
/*  517 */         currentAST.advanceChildToEnd();
/*      */       }
/*  519 */       parallelArrayTemplateApplication_AST = (StringTemplateAST)currentAST.root;
/*      */     }
/*      */     catch (RecognitionException ex) {
/*  522 */       if (this.inputState.guessing == 0) {
/*  523 */         reportError(ex);
/*  524 */         recover(ex, _tokenSet_4);
/*      */       } else {
/*  526 */         throw ex;
/*      */       }
/*      */     }
/*  529 */     this.returnAST = parallelArrayTemplateApplication_AST;
/*      */   }
/*      */ 
/*      */   public final void expr() throws RecognitionException, TokenStreamException
/*      */   {
/*  534 */     this.returnAST = null;
/*  535 */     ASTPair currentAST = new ASTPair();
/*  536 */     StringTemplateAST expr_AST = null;
/*      */     try
/*      */     {
/*  539 */       primaryExpr();
/*  540 */       this.astFactory.addASTChild(currentAST, this.returnAST);
/*      */ 
/*  544 */       while (LA(1) == 24) {
/*  545 */         StringTemplateAST tmp13_AST = null;
/*  546 */         tmp13_AST = (StringTemplateAST)this.astFactory.create(LT(1));
/*  547 */         this.astFactory.makeASTRoot(currentAST, tmp13_AST);
/*  548 */         match(24);
/*  549 */         primaryExpr();
/*  550 */         this.astFactory.addASTChild(currentAST, this.returnAST);
/*      */       }
/*      */ 
/*  558 */       expr_AST = (StringTemplateAST)currentAST.root;
/*      */     }
/*      */     catch (RecognitionException ex) {
/*  561 */       if (this.inputState.guessing == 0) {
/*  562 */         reportError(ex);
/*  563 */         recover(ex, _tokenSet_8);
/*      */       } else {
/*  565 */         throw ex;
/*      */       }
/*      */     }
/*  568 */     this.returnAST = expr_AST;
/*      */   }
/*      */ 
/*      */   public final void template() throws RecognitionException, TokenStreamException
/*      */   {
/*  573 */     this.returnAST = null;
/*  574 */     ASTPair currentAST = new ASTPair();
/*  575 */     StringTemplateAST template_AST = null;
/*      */     try
/*      */     {
/*  579 */       switch (LA(1))
/*      */       {
/*      */       case 16:
/*      */       case 20:
/*      */       case 32:
/*  584 */         namedTemplate();
/*  585 */         this.astFactory.addASTChild(currentAST, this.returnAST);
/*  586 */         break;
/*      */       case 33:
/*  590 */         anonymousTemplate();
/*  591 */         this.astFactory.addASTChild(currentAST, this.returnAST);
/*  592 */         break;
/*      */       default:
/*  596 */         throw new NoViableAltException(LT(1), getFilename());
/*      */       }
/*      */ 
/*  600 */       if (this.inputState.guessing == 0) {
/*  601 */         template_AST = (StringTemplateAST)currentAST.root;
/*  602 */         template_AST = (StringTemplateAST)this.astFactory.make(new ASTArray(2).add((StringTemplateAST)this.astFactory.create(10)).add(template_AST));
/*  603 */         currentAST.root = template_AST;
/*  604 */         currentAST.child = ((template_AST != null) && (template_AST.getFirstChild() != null) ? template_AST.getFirstChild() : template_AST);
/*      */ 
/*  606 */         currentAST.advanceChildToEnd();
/*      */       }
/*  608 */       template_AST = (StringTemplateAST)currentAST.root;
/*      */     }
/*      */     catch (RecognitionException ex) {
/*  611 */       if (this.inputState.guessing == 0) {
/*  612 */         reportError(ex);
/*  613 */         recover(ex, _tokenSet_8);
/*      */       } else {
/*  615 */         throw ex;
/*      */       }
/*      */     }
/*  618 */     this.returnAST = template_AST;
/*      */   }
/*      */ 
/*      */   public final void anonymousTemplate() throws RecognitionException, TokenStreamException
/*      */   {
/*  623 */     this.returnAST = null;
/*  624 */     ASTPair currentAST = new ASTPair();
/*  625 */     StringTemplateAST anonymousTemplate_AST = null;
/*  626 */     Token t = null;
/*  627 */     StringTemplateAST t_AST = null;
/*      */     try
/*      */     {
/*  630 */       t = LT(1);
/*  631 */       t_AST = (StringTemplateAST)this.astFactory.create(t);
/*  632 */       this.astFactory.addASTChild(currentAST, t_AST);
/*  633 */       match(33);
/*  634 */       if (this.inputState.guessing == 0)
/*      */       {
/*  636 */         StringTemplate anonymous = new StringTemplate();
/*  637 */         anonymous.setGroup(this.self.getGroup());
/*  638 */         anonymous.setEnclosingInstance(this.self);
/*  639 */         anonymous.setTemplate(t.getText());
/*  640 */         anonymous.defineFormalArguments(((StringTemplateToken)t).args);
/*  641 */         t_AST.setStringTemplate(anonymous);
/*      */       }
/*      */ 
/*  644 */       anonymousTemplate_AST = (StringTemplateAST)currentAST.root;
/*      */     }
/*      */     catch (RecognitionException ex) {
/*  647 */       if (this.inputState.guessing == 0) {
/*  648 */         reportError(ex);
/*  649 */         recover(ex, _tokenSet_8);
/*      */       } else {
/*  651 */         throw ex;
/*      */       }
/*      */     }
/*  654 */     this.returnAST = anonymousTemplate_AST;
/*      */   }
/*      */ 
/*      */   public final void ifAtom() throws RecognitionException, TokenStreamException
/*      */   {
/*  659 */     this.returnAST = null;
/*  660 */     ASTPair currentAST = new ASTPair();
/*  661 */     StringTemplateAST ifAtom_AST = null;
/*      */     try
/*      */     {
/*  664 */       templatesExpr();
/*  665 */       this.astFactory.addASTChild(currentAST, this.returnAST);
/*  666 */       ifAtom_AST = (StringTemplateAST)currentAST.root;
/*      */     }
/*      */     catch (RecognitionException ex) {
/*  669 */       if (this.inputState.guessing == 0) {
/*  670 */         reportError(ex);
/*  671 */         recover(ex, _tokenSet_5);
/*      */       } else {
/*  673 */         throw ex;
/*      */       }
/*      */     }
/*  676 */     this.returnAST = ifAtom_AST;
/*      */   }
/*      */ 
/*      */   public final void primaryExpr() throws RecognitionException, TokenStreamException
/*      */   {
/*  681 */     this.returnAST = null;
/*  682 */     ASTPair currentAST = new ASTPair();
/*  683 */     StringTemplateAST primaryExpr_AST = null;
/*      */     try
/*      */     {
/*  686 */       switch (LA(1))
/*      */       {
/*      */       case 26:
/*      */       case 27:
/*      */       case 28:
/*      */       case 29:
/*      */       case 30:
/*      */       case 31:
/*  694 */         function();
/*  695 */         this.astFactory.addASTChild(currentAST, this.returnAST);
/*      */ 
/*  699 */         while (LA(1) == 25) {
/*  700 */           StringTemplateAST tmp14_AST = null;
/*  701 */           tmp14_AST = (StringTemplateAST)this.astFactory.create(LT(1));
/*  702 */           this.astFactory.makeASTRoot(currentAST, tmp14_AST);
/*  703 */           match(25);
/*      */ 
/*  705 */           switch (LA(1))
/*      */           {
/*      */           case 20:
/*  708 */             StringTemplateAST tmp15_AST = null;
/*  709 */             tmp15_AST = (StringTemplateAST)this.astFactory.create(LT(1));
/*  710 */             this.astFactory.addASTChild(currentAST, tmp15_AST);
/*  711 */             match(20);
/*  712 */             break;
/*      */           case 16:
/*  716 */             valueExpr();
/*  717 */             this.astFactory.addASTChild(currentAST, this.returnAST);
/*  718 */             break;
/*      */           default:
/*  722 */             throw new NoViableAltException(LT(1), getFilename());
/*      */           }
/*      */ 
/*      */         }
/*      */ 
/*  733 */         primaryExpr_AST = (StringTemplateAST)currentAST.root;
/*  734 */         break;
/*      */       case 36:
/*  738 */         list();
/*  739 */         this.astFactory.addASTChild(currentAST, this.returnAST);
/*  740 */         primaryExpr_AST = (StringTemplateAST)currentAST.root;
/*  741 */         break;
/*      */       case 32:
/*      */       case 33:
/*      */       case 34:
/*      */       case 35:
/*      */       default:
/*  744 */         boolean synPredMatched25 = false;
/*  745 */         if (((LA(1) == 16) || (LA(1) == 20) || (LA(1) == 32)) && (_tokenSet_9.member(LA(2)))) {
/*  746 */           int _m25 = mark();
/*  747 */           synPredMatched25 = true;
/*  748 */           this.inputState.guessing += 1;
/*      */           try
/*      */           {
/*  751 */             templateInclude();
/*      */           }
/*      */           catch (RecognitionException pe)
/*      */           {
/*  755 */             synPredMatched25 = false;
/*      */           }
/*  757 */           rewind(_m25);
/*  758 */           this.inputState.guessing -= 1;
/*      */         }
/*  760 */         if (synPredMatched25) {
/*  761 */           templateInclude();
/*  762 */           this.astFactory.addASTChild(currentAST, this.returnAST);
/*  763 */           primaryExpr_AST = (StringTemplateAST)currentAST.root;
/*      */         }
/*  765 */         else if ((_tokenSet_10.member(LA(1))) && (_tokenSet_11.member(LA(2)))) {
/*  766 */           atom();
/*  767 */           this.astFactory.addASTChild(currentAST, this.returnAST);
/*      */ 
/*  771 */           while (LA(1) == 25) {
/*  772 */             StringTemplateAST tmp16_AST = null;
/*  773 */             tmp16_AST = (StringTemplateAST)this.astFactory.create(LT(1));
/*  774 */             this.astFactory.makeASTRoot(currentAST, tmp16_AST);
/*  775 */             match(25);
/*      */ 
/*  777 */             switch (LA(1))
/*      */             {
/*      */             case 20:
/*  780 */               StringTemplateAST tmp17_AST = null;
/*  781 */               tmp17_AST = (StringTemplateAST)this.astFactory.create(LT(1));
/*  782 */               this.astFactory.addASTChild(currentAST, tmp17_AST);
/*  783 */               match(20);
/*  784 */               break;
/*      */             case 16:
/*  788 */               valueExpr();
/*  789 */               this.astFactory.addASTChild(currentAST, this.returnAST);
/*  790 */               break;
/*      */             default:
/*  794 */               throw new NoViableAltException(LT(1), getFilename());
/*      */             }
/*      */ 
/*      */           }
/*      */ 
/*  805 */           primaryExpr_AST = (StringTemplateAST)currentAST.root;
/*      */         }
/*  807 */         else if ((LA(1) == 16) && (_tokenSet_1.member(LA(2)))) {
/*  808 */           valueExpr();
/*  809 */           this.astFactory.addASTChild(currentAST, this.returnAST);
/*  810 */           primaryExpr_AST = (StringTemplateAST)currentAST.root;
/*      */         }
/*      */         else {
/*  813 */           throw new NoViableAltException(LT(1), getFilename());
/*      */         }
/*      */         break;
/*      */       }
/*      */     } catch (RecognitionException ex) {
/*  818 */       if (this.inputState.guessing == 0) {
/*  819 */         reportError(ex);
/*  820 */         recover(ex, _tokenSet_12);
/*      */       } else {
/*  822 */         throw ex;
/*      */       }
/*      */     }
/*  825 */     this.returnAST = primaryExpr_AST;
/*      */   }
/*      */ 
/*      */   public final void templateInclude() throws RecognitionException, TokenStreamException
/*      */   {
/*  830 */     this.returnAST = null;
/*  831 */     ASTPair currentAST = new ASTPair();
/*  832 */     StringTemplateAST templateInclude_AST = null;
/*  833 */     Token id = null;
/*  834 */     StringTemplateAST id_AST = null;
/*  835 */     Token qid = null;
/*  836 */     StringTemplateAST qid_AST = null;
/*      */     try
/*      */     {
/*  840 */       switch (LA(1))
/*      */       {
/*      */       case 20:
/*  843 */         id = LT(1);
/*  844 */         id_AST = (StringTemplateAST)this.astFactory.create(id);
/*  845 */         this.astFactory.addASTChild(currentAST, id_AST);
/*  846 */         match(20);
/*  847 */         argList();
/*  848 */         this.astFactory.addASTChild(currentAST, this.returnAST);
/*  849 */         break;
/*      */       case 32:
/*  853 */         match(32);
/*  854 */         match(25);
/*  855 */         qid = LT(1);
/*  856 */         qid_AST = (StringTemplateAST)this.astFactory.create(qid);
/*  857 */         this.astFactory.addASTChild(currentAST, qid_AST);
/*  858 */         match(20);
/*  859 */         if (this.inputState.guessing == 0) {
/*  860 */           qid_AST.setText("super." + qid_AST.getText());
/*      */         }
/*  862 */         argList();
/*  863 */         this.astFactory.addASTChild(currentAST, this.returnAST);
/*  864 */         break;
/*      */       case 16:
/*  868 */         indirectTemplate();
/*  869 */         this.astFactory.addASTChild(currentAST, this.returnAST);
/*  870 */         break;
/*      */       default:
/*  874 */         throw new NoViableAltException(LT(1), getFilename());
/*      */       }
/*      */ 
/*  878 */       if (this.inputState.guessing == 0) {
/*  879 */         templateInclude_AST = (StringTemplateAST)currentAST.root;
/*  880 */         templateInclude_AST = (StringTemplateAST)this.astFactory.make(new ASTArray(2).add((StringTemplateAST)this.astFactory.create(7, "include")).add(templateInclude_AST));
/*  881 */         currentAST.root = templateInclude_AST;
/*  882 */         currentAST.child = ((templateInclude_AST != null) && (templateInclude_AST.getFirstChild() != null) ? templateInclude_AST.getFirstChild() : templateInclude_AST);
/*      */ 
/*  884 */         currentAST.advanceChildToEnd();
/*      */       }
/*  886 */       templateInclude_AST = (StringTemplateAST)currentAST.root;
/*      */     }
/*      */     catch (RecognitionException ex) {
/*  889 */       if (this.inputState.guessing == 0) {
/*  890 */         reportError(ex);
/*  891 */         recover(ex, _tokenSet_12);
/*      */       } else {
/*  893 */         throw ex;
/*      */       }
/*      */     }
/*  896 */     this.returnAST = templateInclude_AST;
/*      */   }
/*      */ 
/*      */   public final void atom() throws RecognitionException, TokenStreamException
/*      */   {
/*  901 */     this.returnAST = null;
/*  902 */     ASTPair currentAST = new ASTPair();
/*  903 */     StringTemplateAST atom_AST = null;
/*      */     try
/*      */     {
/*  906 */       switch (LA(1))
/*      */       {
/*      */       case 20:
/*  909 */         StringTemplateAST tmp20_AST = null;
/*  910 */         tmp20_AST = (StringTemplateAST)this.astFactory.create(LT(1));
/*  911 */         this.astFactory.addASTChild(currentAST, tmp20_AST);
/*  912 */         match(20);
/*  913 */         atom_AST = (StringTemplateAST)currentAST.root;
/*  914 */         break;
/*      */       case 34:
/*  918 */         StringTemplateAST tmp21_AST = null;
/*  919 */         tmp21_AST = (StringTemplateAST)this.astFactory.create(LT(1));
/*  920 */         this.astFactory.addASTChild(currentAST, tmp21_AST);
/*  921 */         match(34);
/*  922 */         atom_AST = (StringTemplateAST)currentAST.root;
/*  923 */         break;
/*      */       case 35:
/*  927 */         StringTemplateAST tmp22_AST = null;
/*  928 */         tmp22_AST = (StringTemplateAST)this.astFactory.create(LT(1));
/*  929 */         this.astFactory.addASTChild(currentAST, tmp22_AST);
/*  930 */         match(35);
/*  931 */         atom_AST = (StringTemplateAST)currentAST.root;
/*  932 */         break;
/*      */       case 33:
/*  936 */         StringTemplateAST tmp23_AST = null;
/*  937 */         tmp23_AST = (StringTemplateAST)this.astFactory.create(LT(1));
/*  938 */         this.astFactory.addASTChild(currentAST, tmp23_AST);
/*  939 */         match(33);
/*  940 */         atom_AST = (StringTemplateAST)currentAST.root;
/*  941 */         break;
/*      */       default:
/*  945 */         throw new NoViableAltException(LT(1), getFilename());
/*      */       }
/*      */     }
/*      */     catch (RecognitionException ex)
/*      */     {
/*  950 */       if (this.inputState.guessing == 0) {
/*  951 */         reportError(ex);
/*  952 */         recover(ex, _tokenSet_11);
/*      */       } else {
/*  954 */         throw ex;
/*      */       }
/*      */     }
/*  957 */     this.returnAST = atom_AST;
/*      */   }
/*      */ 
/*      */   public final void valueExpr() throws RecognitionException, TokenStreamException
/*      */   {
/*  962 */     this.returnAST = null;
/*  963 */     ASTPair currentAST = new ASTPair();
/*  964 */     StringTemplateAST valueExpr_AST = null;
/*  965 */     Token eval = null;
/*  966 */     StringTemplateAST eval_AST = null;
/*      */     try
/*      */     {
/*  969 */       eval = LT(1);
/*  970 */       eval_AST = (StringTemplateAST)this.astFactory.create(eval);
/*  971 */       this.astFactory.makeASTRoot(currentAST, eval_AST);
/*  972 */       match(16);
/*  973 */       templatesExpr();
/*  974 */       this.astFactory.addASTChild(currentAST, this.returnAST);
/*  975 */       match(17);
/*  976 */       if (this.inputState.guessing == 0) {
/*  977 */         eval_AST.setType(9); eval_AST.setText("value");
/*      */       }
/*  979 */       valueExpr_AST = (StringTemplateAST)currentAST.root;
/*      */     }
/*      */     catch (RecognitionException ex) {
/*  982 */       if (this.inputState.guessing == 0) {
/*  983 */         reportError(ex);
/*  984 */         recover(ex, _tokenSet_11);
/*      */       } else {
/*  986 */         throw ex;
/*      */       }
/*      */     }
/*  989 */     this.returnAST = valueExpr_AST;
/*      */   }
/*      */ 
/*      */   public final void function() throws RecognitionException, TokenStreamException
/*      */   {
/*  994 */     this.returnAST = null;
/*  995 */     ASTPair currentAST = new ASTPair();
/*  996 */     StringTemplateAST function_AST = null;
/*      */     try
/*      */     {
/* 1000 */       switch (LA(1))
/*      */       {
/*      */       case 26:
/* 1003 */         StringTemplateAST tmp25_AST = null;
/* 1004 */         tmp25_AST = (StringTemplateAST)this.astFactory.create(LT(1));
/* 1005 */         this.astFactory.addASTChild(currentAST, tmp25_AST);
/* 1006 */         match(26);
/* 1007 */         break;
/*      */       case 27:
/* 1011 */         StringTemplateAST tmp26_AST = null;
/* 1012 */         tmp26_AST = (StringTemplateAST)this.astFactory.create(LT(1));
/* 1013 */         this.astFactory.addASTChild(currentAST, tmp26_AST);
/* 1014 */         match(27);
/* 1015 */         break;
/*      */       case 28:
/* 1019 */         StringTemplateAST tmp27_AST = null;
/* 1020 */         tmp27_AST = (StringTemplateAST)this.astFactory.create(LT(1));
/* 1021 */         this.astFactory.addASTChild(currentAST, tmp27_AST);
/* 1022 */         match(28);
/* 1023 */         break;
/*      */       case 29:
/* 1027 */         StringTemplateAST tmp28_AST = null;
/* 1028 */         tmp28_AST = (StringTemplateAST)this.astFactory.create(LT(1));
/* 1029 */         this.astFactory.addASTChild(currentAST, tmp28_AST);
/* 1030 */         match(29);
/* 1031 */         break;
/*      */       case 30:
/* 1035 */         StringTemplateAST tmp29_AST = null;
/* 1036 */         tmp29_AST = (StringTemplateAST)this.astFactory.create(LT(1));
/* 1037 */         this.astFactory.addASTChild(currentAST, tmp29_AST);
/* 1038 */         match(30);
/* 1039 */         break;
/*      */       case 31:
/* 1043 */         StringTemplateAST tmp30_AST = null;
/* 1044 */         tmp30_AST = (StringTemplateAST)this.astFactory.create(LT(1));
/* 1045 */         this.astFactory.addASTChild(currentAST, tmp30_AST);
/* 1046 */         match(31);
/* 1047 */         break;
/*      */       default:
/* 1051 */         throw new NoViableAltException(LT(1), getFilename());
/*      */       }
/*      */ 
/* 1055 */       singleArg();
/* 1056 */       this.astFactory.addASTChild(currentAST, this.returnAST);
/* 1057 */       if (this.inputState.guessing == 0) {
/* 1058 */         function_AST = (StringTemplateAST)currentAST.root;
/* 1059 */         function_AST = (StringTemplateAST)this.astFactory.make(new ASTArray(2).add((StringTemplateAST)this.astFactory.create(11)).add(function_AST));
/* 1060 */         currentAST.root = function_AST;
/* 1061 */         currentAST.child = ((function_AST != null) && (function_AST.getFirstChild() != null) ? function_AST.getFirstChild() : function_AST);
/*      */ 
/* 1063 */         currentAST.advanceChildToEnd();
/*      */       }
/* 1065 */       function_AST = (StringTemplateAST)currentAST.root;
/*      */     }
/*      */     catch (RecognitionException ex) {
/* 1068 */       if (this.inputState.guessing == 0) {
/* 1069 */         reportError(ex);
/* 1070 */         recover(ex, _tokenSet_11);
/*      */       } else {
/* 1072 */         throw ex;
/*      */       }
/*      */     }
/* 1075 */     this.returnAST = function_AST;
/*      */   }
/*      */ 
/*      */   public final void list() throws RecognitionException, TokenStreamException
/*      */   {
/* 1080 */     this.returnAST = null;
/* 1081 */     ASTPair currentAST = new ASTPair();
/* 1082 */     StringTemplateAST list_AST = null;
/* 1083 */     Token lb = null;
/* 1084 */     StringTemplateAST lb_AST = null;
/*      */     try
/*      */     {
/* 1087 */       lb = LT(1);
/* 1088 */       lb_AST = (StringTemplateAST)this.astFactory.create(lb);
/* 1089 */       this.astFactory.makeASTRoot(currentAST, lb_AST);
/* 1090 */       match(36);
/* 1091 */       if (this.inputState.guessing == 0) {
/* 1092 */         lb_AST.setType(13); lb_AST.setText("value");
/*      */       }
/* 1094 */       listElement();
/* 1095 */       this.astFactory.addASTChild(currentAST, this.returnAST);
/*      */ 
/* 1099 */       while (LA(1) == 19) {
/* 1100 */         match(19);
/* 1101 */         listElement();
/* 1102 */         this.astFactory.addASTChild(currentAST, this.returnAST);
/*      */       }
/*      */ 
/* 1110 */       match(37);
/* 1111 */       list_AST = (StringTemplateAST)currentAST.root;
/*      */     }
/*      */     catch (RecognitionException ex) {
/* 1114 */       if (this.inputState.guessing == 0) {
/* 1115 */         reportError(ex);
/* 1116 */         recover(ex, _tokenSet_12);
/*      */       } else {
/* 1118 */         throw ex;
/*      */       }
/*      */     }
/* 1121 */     this.returnAST = list_AST;
/*      */   }
/*      */ 
/*      */   public final void singleArg() throws RecognitionException, TokenStreamException
/*      */   {
/* 1126 */     this.returnAST = null;
/* 1127 */     ASTPair currentAST = new ASTPair();
/* 1128 */     StringTemplateAST singleArg_AST = null;
/*      */     try
/*      */     {
/* 1131 */       match(16);
/* 1132 */       nonAlternatingTemplateExpr();
/* 1133 */       this.astFactory.addASTChild(currentAST, this.returnAST);
/* 1134 */       match(17);
/* 1135 */       if (this.inputState.guessing == 0) {
/* 1136 */         singleArg_AST = (StringTemplateAST)currentAST.root;
/* 1137 */         singleArg_AST = (StringTemplateAST)this.astFactory.make(new ASTArray(2).add((StringTemplateAST)this.astFactory.create(12, "SINGLEVALUEARG")).add(singleArg_AST));
/* 1138 */         currentAST.root = singleArg_AST;
/* 1139 */         currentAST.child = ((singleArg_AST != null) && (singleArg_AST.getFirstChild() != null) ? singleArg_AST.getFirstChild() : singleArg_AST);
/*      */ 
/* 1141 */         currentAST.advanceChildToEnd();
/*      */       }
/* 1143 */       singleArg_AST = (StringTemplateAST)currentAST.root;
/*      */     }
/*      */     catch (RecognitionException ex) {
/* 1146 */       if (this.inputState.guessing == 0) {
/* 1147 */         reportError(ex);
/* 1148 */         recover(ex, _tokenSet_11);
/*      */       } else {
/* 1150 */         throw ex;
/*      */       }
/*      */     }
/* 1153 */     this.returnAST = singleArg_AST;
/*      */   }
/*      */ 
/*      */   public final void namedTemplate() throws RecognitionException, TokenStreamException
/*      */   {
/* 1158 */     this.returnAST = null;
/* 1159 */     ASTPair currentAST = new ASTPair();
/* 1160 */     StringTemplateAST namedTemplate_AST = null;
/* 1161 */     Token qid = null;
/* 1162 */     StringTemplateAST qid_AST = null;
/*      */     try
/*      */     {
/* 1165 */       switch (LA(1))
/*      */       {
/*      */       case 20:
/* 1168 */         StringTemplateAST tmp35_AST = null;
/* 1169 */         tmp35_AST = (StringTemplateAST)this.astFactory.create(LT(1));
/* 1170 */         this.astFactory.addASTChild(currentAST, tmp35_AST);
/* 1171 */         match(20);
/* 1172 */         argList();
/* 1173 */         this.astFactory.addASTChild(currentAST, this.returnAST);
/* 1174 */         namedTemplate_AST = (StringTemplateAST)currentAST.root;
/* 1175 */         break;
/*      */       case 32:
/* 1179 */         match(32);
/* 1180 */         match(25);
/* 1181 */         qid = LT(1);
/* 1182 */         qid_AST = (StringTemplateAST)this.astFactory.create(qid);
/* 1183 */         this.astFactory.addASTChild(currentAST, qid_AST);
/* 1184 */         match(20);
/* 1185 */         if (this.inputState.guessing == 0) {
/* 1186 */           qid_AST.setText("super." + qid_AST.getText());
/*      */         }
/* 1188 */         argList();
/* 1189 */         this.astFactory.addASTChild(currentAST, this.returnAST);
/* 1190 */         namedTemplate_AST = (StringTemplateAST)currentAST.root;
/* 1191 */         break;
/*      */       case 16:
/* 1195 */         indirectTemplate();
/* 1196 */         this.astFactory.addASTChild(currentAST, this.returnAST);
/* 1197 */         namedTemplate_AST = (StringTemplateAST)currentAST.root;
/* 1198 */         break;
/*      */       default:
/* 1202 */         throw new NoViableAltException(LT(1), getFilename());
/*      */       }
/*      */     }
/*      */     catch (RecognitionException ex)
/*      */     {
/* 1207 */       if (this.inputState.guessing == 0) {
/* 1208 */         reportError(ex);
/* 1209 */         recover(ex, _tokenSet_8);
/*      */       } else {
/* 1211 */         throw ex;
/*      */       }
/*      */     }
/* 1214 */     this.returnAST = namedTemplate_AST;
/*      */   }
/*      */ 
/*      */   public final void argList() throws RecognitionException, TokenStreamException
/*      */   {
/* 1219 */     this.returnAST = null;
/* 1220 */     ASTPair currentAST = new ASTPair();
/* 1221 */     StringTemplateAST argList_AST = null;
/*      */     try
/*      */     {
/* 1224 */       if ((LA(1) == 16) && (LA(2) == 17)) {
/* 1225 */         match(16);
/* 1226 */         match(17);
/* 1227 */         if (this.inputState.guessing == 0) {
/* 1228 */           argList_AST = (StringTemplateAST)currentAST.root;
/* 1229 */           argList_AST = (StringTemplateAST)this.astFactory.create(6, "ARGS");
/* 1230 */           currentAST.root = argList_AST;
/* 1231 */           currentAST.child = ((argList_AST != null) && (argList_AST.getFirstChild() != null) ? argList_AST.getFirstChild() : argList_AST);
/*      */ 
/* 1233 */           currentAST.advanceChildToEnd();
/*      */         }
/*      */       }
/*      */       else {
/* 1237 */         boolean synPredMatched52 = false;
/* 1238 */         if ((LA(1) == 16) && (_tokenSet_1.member(LA(2)))) {
/* 1239 */           int _m52 = mark();
/* 1240 */           synPredMatched52 = true;
/* 1241 */           this.inputState.guessing += 1;
/*      */           try
/*      */           {
/* 1244 */             singleArg();
/*      */           }
/*      */           catch (RecognitionException pe)
/*      */           {
/* 1248 */             synPredMatched52 = false;
/*      */           }
/* 1250 */           rewind(_m52);
/* 1251 */           this.inputState.guessing -= 1;
/*      */         }
/* 1253 */         if (synPredMatched52) {
/* 1254 */           singleArg();
/* 1255 */           this.astFactory.addASTChild(currentAST, this.returnAST);
/* 1256 */           argList_AST = (StringTemplateAST)currentAST.root;
/*      */         }
/* 1258 */         else if ((LA(1) == 16) && ((LA(2) == 20) || (LA(2) == 38))) {
/* 1259 */           match(16);
/* 1260 */           argumentAssignment();
/* 1261 */           this.astFactory.addASTChild(currentAST, this.returnAST);
/*      */ 
/* 1265 */           while (LA(1) == 19) {
/* 1266 */             match(19);
/* 1267 */             argumentAssignment();
/* 1268 */             this.astFactory.addASTChild(currentAST, this.returnAST);
/*      */           }
/*      */ 
/* 1276 */           match(17);
/* 1277 */           if (this.inputState.guessing == 0) {
/* 1278 */             argList_AST = (StringTemplateAST)currentAST.root;
/* 1279 */             argList_AST = (StringTemplateAST)this.astFactory.make(new ASTArray(2).add((StringTemplateAST)this.astFactory.create(6, "ARGS")).add(argList_AST));
/* 1280 */             currentAST.root = argList_AST;
/* 1281 */             currentAST.child = ((argList_AST != null) && (argList_AST.getFirstChild() != null) ? argList_AST.getFirstChild() : argList_AST);
/*      */ 
/* 1283 */             currentAST.advanceChildToEnd();
/*      */           }
/* 1285 */           argList_AST = (StringTemplateAST)currentAST.root;
/*      */         }
/*      */         else {
/* 1288 */           throw new NoViableAltException(LT(1), getFilename());
/*      */         }
/*      */       }
/*      */     }
/*      */     catch (RecognitionException ex) {
/* 1293 */       if (this.inputState.guessing == 0) {
/* 1294 */         reportError(ex);
/* 1295 */         recover(ex, _tokenSet_12);
/*      */       } else {
/* 1297 */         throw ex;
/*      */       }
/*      */     }
/* 1300 */     this.returnAST = argList_AST;
/*      */   }
/*      */ 
/*      */   public final void indirectTemplate()
/*      */     throws RecognitionException, TokenStreamException
/*      */   {
/* 1306 */     this.returnAST = null;
/* 1307 */     ASTPair currentAST = new ASTPair();
/* 1308 */     StringTemplateAST indirectTemplate_AST = null;
/* 1309 */     StringTemplateAST e_AST = null;
/* 1310 */     StringTemplateAST args_AST = null;
/*      */     try
/*      */     {
/* 1313 */       StringTemplateAST tmp43_AST = null;
/* 1314 */       tmp43_AST = (StringTemplateAST)this.astFactory.create(LT(1));
/* 1315 */       match(16);
/* 1316 */       templatesExpr();
/* 1317 */       e_AST = (StringTemplateAST)this.returnAST;
/* 1318 */       StringTemplateAST tmp44_AST = null;
/* 1319 */       tmp44_AST = (StringTemplateAST)this.astFactory.create(LT(1));
/* 1320 */       match(17);
/* 1321 */       argList();
/* 1322 */       args_AST = (StringTemplateAST)this.returnAST;
/* 1323 */       if (this.inputState.guessing == 0) {
/* 1324 */         indirectTemplate_AST = (StringTemplateAST)currentAST.root;
/* 1325 */         indirectTemplate_AST = (StringTemplateAST)this.astFactory.make(new ASTArray(3).add((StringTemplateAST)this.astFactory.create(9, "value")).add(e_AST).add(args_AST));
/* 1326 */         currentAST.root = indirectTemplate_AST;
/* 1327 */         currentAST.child = ((indirectTemplate_AST != null) && (indirectTemplate_AST.getFirstChild() != null) ? indirectTemplate_AST.getFirstChild() : indirectTemplate_AST);
/*      */ 
/* 1329 */         currentAST.advanceChildToEnd();
/*      */       }
/*      */     }
/*      */     catch (RecognitionException ex) {
/* 1333 */       if (this.inputState.guessing == 0) {
/* 1334 */         reportError(ex);
/* 1335 */         recover(ex, _tokenSet_12);
/*      */       } else {
/* 1337 */         throw ex;
/*      */       }
/*      */     }
/* 1340 */     this.returnAST = indirectTemplate_AST;
/*      */   }
/*      */ 
/*      */   public final void listElement() throws RecognitionException, TokenStreamException
/*      */   {
/* 1345 */     this.returnAST = null;
/* 1346 */     ASTPair currentAST = new ASTPair();
/* 1347 */     StringTemplateAST listElement_AST = null;
/*      */     try
/*      */     {
/* 1350 */       switch (LA(1))
/*      */       {
/*      */       case 16:
/*      */       case 20:
/*      */       case 26:
/*      */       case 27:
/*      */       case 28:
/*      */       case 29:
/*      */       case 30:
/*      */       case 31:
/*      */       case 32:
/*      */       case 33:
/*      */       case 34:
/*      */       case 35:
/*      */       case 36:
/* 1365 */         nonAlternatingTemplateExpr();
/* 1366 */         this.astFactory.addASTChild(currentAST, this.returnAST);
/* 1367 */         listElement_AST = (StringTemplateAST)currentAST.root;
/* 1368 */         break;
/*      */       case 19:
/*      */       case 37:
/* 1373 */         if (this.inputState.guessing == 0) {
/* 1374 */           listElement_AST = (StringTemplateAST)currentAST.root;
/* 1375 */           listElement_AST = (StringTemplateAST)this.astFactory.create(14, "NOTHING");
/* 1376 */           currentAST.root = listElement_AST;
/* 1377 */           currentAST.child = ((listElement_AST != null) && (listElement_AST.getFirstChild() != null) ? listElement_AST.getFirstChild() : listElement_AST);
/*      */ 
/* 1379 */           currentAST.advanceChildToEnd();
/*      */         }
/* 1381 */         listElement_AST = (StringTemplateAST)currentAST.root;
/* 1382 */         break;
/*      */       case 17:
/*      */       case 18:
/*      */       case 21:
/*      */       case 22:
/*      */       case 23:
/*      */       case 24:
/*      */       case 25:
/*      */       default:
/* 1386 */         throw new NoViableAltException(LT(1), getFilename());
/*      */       }
/*      */     }
/*      */     catch (RecognitionException ex)
/*      */     {
/* 1391 */       if (this.inputState.guessing == 0) {
/* 1392 */         reportError(ex);
/* 1393 */         recover(ex, _tokenSet_13);
/*      */       } else {
/* 1395 */         throw ex;
/*      */       }
/*      */     }
/* 1398 */     this.returnAST = listElement_AST;
/*      */   }
/*      */ 
/*      */   public final void argumentAssignment() throws RecognitionException, TokenStreamException
/*      */   {
/* 1403 */     this.returnAST = null;
/* 1404 */     ASTPair currentAST = new ASTPair();
/* 1405 */     StringTemplateAST argumentAssignment_AST = null;
/*      */     try
/*      */     {
/* 1408 */       switch (LA(1))
/*      */       {
/*      */       case 20:
/* 1411 */         StringTemplateAST tmp45_AST = null;
/* 1412 */         tmp45_AST = (StringTemplateAST)this.astFactory.create(LT(1));
/* 1413 */         this.astFactory.addASTChild(currentAST, tmp45_AST);
/* 1414 */         match(20);
/* 1415 */         StringTemplateAST tmp46_AST = null;
/* 1416 */         tmp46_AST = (StringTemplateAST)this.astFactory.create(LT(1));
/* 1417 */         this.astFactory.makeASTRoot(currentAST, tmp46_AST);
/* 1418 */         match(21);
/* 1419 */         nonAlternatingTemplateExpr();
/* 1420 */         this.astFactory.addASTChild(currentAST, this.returnAST);
/* 1421 */         argumentAssignment_AST = (StringTemplateAST)currentAST.root;
/* 1422 */         break;
/*      */       case 38:
/* 1426 */         StringTemplateAST tmp47_AST = null;
/* 1427 */         tmp47_AST = (StringTemplateAST)this.astFactory.create(LT(1));
/* 1428 */         this.astFactory.addASTChild(currentAST, tmp47_AST);
/* 1429 */         match(38);
/* 1430 */         argumentAssignment_AST = (StringTemplateAST)currentAST.root;
/* 1431 */         break;
/*      */       default:
/* 1435 */         throw new NoViableAltException(LT(1), getFilename());
/*      */       }
/*      */     }
/*      */     catch (RecognitionException ex)
/*      */     {
/* 1440 */       if (this.inputState.guessing == 0) {
/* 1441 */         reportError(ex);
/* 1442 */         recover(ex, _tokenSet_14);
/*      */       } else {
/* 1444 */         throw ex;
/*      */       }
/*      */     }
/* 1447 */     this.returnAST = argumentAssignment_AST;
/*      */   }
/*      */ 
/*      */   protected void buildTokenTypeASTClassMap()
/*      */   {
/* 1499 */     this.tokenTypeToASTClassMap = null;
/*      */   }
/*      */ 
/*      */   private static final long[] mk_tokenSet_0() {
/* 1503 */     long[] data = { 2L, 0L };
/* 1504 */     return data;
/*      */   }
/*      */ 
/*      */   private static final long[] mk_tokenSet_1() {
/* 1508 */     long[] data = { 137372958720L, 0L };
/* 1509 */     return data;
/*      */   }
/*      */ 
/*      */   private static final long[] mk_tokenSet_2() {
/* 1513 */     long[] data = { 274862768128L, 0L };
/* 1514 */     return data;
/*      */   }
/*      */ 
/*      */   private static final long[] mk_tokenSet_3() {
/* 1518 */     long[] data = { 274867126274L, 0L };
/* 1519 */     return data;
/*      */   }
/*      */ 
/*      */   private static final long[] mk_tokenSet_4() {
/* 1523 */     long[] data = { 163842L, 0L };
/* 1524 */     return data;
/*      */   }
/*      */ 
/*      */   private static final long[] mk_tokenSet_5() {
/* 1528 */     long[] data = { 131072L, 0L };
/* 1529 */     return data;
/*      */   }
/*      */ 
/*      */   private static final long[] mk_tokenSet_6() {
/* 1533 */     long[] data = { 524290L, 0L };
/* 1534 */     return data;
/*      */   }
/*      */ 
/*      */   private static final long[] mk_tokenSet_7() {
/* 1538 */     long[] data = { 137439608834L, 0L };
/* 1539 */     return data;
/*      */   }
/*      */ 
/*      */   private static final long[] mk_tokenSet_8() {
/* 1543 */     long[] data = { 137443835906L, 0L };
/* 1544 */     return data;
/*      */   }
/*      */ 
/*      */   private static final long[] mk_tokenSet_9() {
/* 1548 */     long[] data = { 137406513152L, 0L };
/* 1549 */     return data;
/*      */   }
/*      */ 
/*      */   private static final long[] mk_tokenSet_10() {
/* 1553 */     long[] data = { 60130590720L, 0L };
/* 1554 */     return data;
/*      */   }
/*      */ 
/*      */   private static final long[] mk_tokenSet_11() {
/* 1558 */     long[] data = { 137494167554L, 0L };
/* 1559 */     return data;
/*      */   }
/*      */ 
/*      */   private static final long[] mk_tokenSet_12() {
/* 1563 */     long[] data = { 137460613122L, 0L };
/* 1564 */     return data;
/*      */   }
/*      */ 
/*      */   private static final long[] mk_tokenSet_13() {
/* 1568 */     long[] data = { 137439477760L, 0L };
/* 1569 */     return data;
/*      */   }
/*      */ 
/*      */   private static final long[] mk_tokenSet_14() {
/* 1573 */     long[] data = { 655360L, 0L };
/* 1574 */     return data;
/*      */   }
/*      */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.antlr.stringtemplate.language.ActionParser
 * JD-Core Version:    0.6.2
 */