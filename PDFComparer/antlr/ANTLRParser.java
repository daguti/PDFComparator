/*      */ package antlr;
/*      */ 
/*      */ import antlr.collections.impl.BitSet;
/*      */ 
/*      */ public class ANTLRParser extends LLkParser
/*      */   implements ANTLRTokenTypes
/*      */ {
/*      */   private static final boolean DEBUG_PARSER = false;
/*      */   ANTLRGrammarParseBehavior behavior;
/*      */   Tool antlrTool;
/*   32 */   protected int blockNesting = -1;
/*      */ 
/* 2850 */   public static final String[] _tokenNames = { "<0>", "EOF", "<2>", "NULL_TREE_LOOKAHEAD", "\"tokens\"", "\"header\"", "STRING_LITERAL", "ACTION", "DOC_COMMENT", "\"lexclass\"", "\"class\"", "\"extends\"", "\"Lexer\"", "\"TreeParser\"", "OPTIONS", "ASSIGN", "SEMI", "RCURLY", "\"charVocabulary\"", "CHAR_LITERAL", "INT", "OR", "RANGE", "TOKENS", "TOKEN_REF", "OPEN_ELEMENT_OPTION", "CLOSE_ELEMENT_OPTION", "LPAREN", "RPAREN", "\"Parser\"", "\"protected\"", "\"public\"", "\"private\"", "BANG", "ARG_ACTION", "\"returns\"", "COLON", "\"throws\"", "COMMA", "\"exception\"", "\"catch\"", "RULE_REF", "NOT_OP", "SEMPRED", "TREE_BEGIN", "QUESTION", "STAR", "PLUS", "IMPLIES", "CARET", "WILDCARD", "\"options\"", "WS", "COMMENT", "SL_COMMENT", "ML_COMMENT", "ESC", "DIGIT", "XDIGIT", "NESTED_ARG_ACTION", "NESTED_ACTION", "WS_LOOP", "INTERNAL_RULE_REF", "WS_OPT" };
/*      */ 
/* 2921 */   public static final BitSet _tokenSet_0 = new BitSet(mk_tokenSet_0());
/*      */ 
/* 2926 */   public static final BitSet _tokenSet_1 = new BitSet(mk_tokenSet_1());
/*      */ 
/* 2931 */   public static final BitSet _tokenSet_2 = new BitSet(mk_tokenSet_2());
/*      */ 
/* 2936 */   public static final BitSet _tokenSet_3 = new BitSet(mk_tokenSet_3());
/*      */ 
/* 2941 */   public static final BitSet _tokenSet_4 = new BitSet(mk_tokenSet_4());
/*      */ 
/* 2946 */   public static final BitSet _tokenSet_5 = new BitSet(mk_tokenSet_5());
/*      */ 
/* 2951 */   public static final BitSet _tokenSet_6 = new BitSet(mk_tokenSet_6());
/*      */ 
/* 2956 */   public static final BitSet _tokenSet_7 = new BitSet(mk_tokenSet_7());
/*      */ 
/* 2961 */   public static final BitSet _tokenSet_8 = new BitSet(mk_tokenSet_8());
/*      */ 
/* 2966 */   public static final BitSet _tokenSet_9 = new BitSet(mk_tokenSet_9());
/*      */ 
/* 2971 */   public static final BitSet _tokenSet_10 = new BitSet(mk_tokenSet_10());
/*      */ 
/* 2976 */   public static final BitSet _tokenSet_11 = new BitSet(mk_tokenSet_11());
/*      */ 
/*      */   public ANTLRParser(TokenBuffer paramTokenBuffer, ANTLRGrammarParseBehavior paramANTLRGrammarParseBehavior, Tool paramTool)
/*      */   {
/*   39 */     super(paramTokenBuffer, 1);
/*   40 */     this.tokenNames = _tokenNames;
/*   41 */     this.behavior = paramANTLRGrammarParseBehavior;
/*   42 */     this.antlrTool = paramTool;
/*      */   }
/*      */ 
/*      */   public void reportError(String paramString) {
/*   46 */     this.antlrTool.error(paramString, getFilename(), -1, -1);
/*      */   }
/*      */ 
/*      */   public void reportError(RecognitionException paramRecognitionException) {
/*   50 */     reportError(paramRecognitionException, paramRecognitionException.getErrorMessage());
/*      */   }
/*      */ 
/*      */   public void reportError(RecognitionException paramRecognitionException, String paramString) {
/*   54 */     this.antlrTool.error(paramString, paramRecognitionException.getFilename(), paramRecognitionException.getLine(), paramRecognitionException.getColumn());
/*      */   }
/*      */ 
/*      */   public void reportWarning(String paramString) {
/*   58 */     this.antlrTool.warning(paramString, getFilename(), -1, -1);
/*      */   }
/*      */ 
/*      */   private boolean lastInRule() throws TokenStreamException {
/*   62 */     if ((this.blockNesting == 0) && ((LA(1) == 16) || (LA(1) == 39) || (LA(1) == 21))) {
/*   63 */       return true;
/*      */     }
/*   65 */     return false;
/*      */   }
/*      */ 
/*      */   private void checkForMissingEndRule(Token paramToken) {
/*   69 */     if (paramToken.getColumn() == 1)
/*   70 */       this.antlrTool.warning("did you forget to terminate previous rule?", getFilename(), paramToken.getLine(), paramToken.getColumn());
/*      */   }
/*      */ 
/*      */   protected ANTLRParser(TokenBuffer paramTokenBuffer, int paramInt)
/*      */   {
/*   75 */     super(paramTokenBuffer, paramInt);
/*   76 */     this.tokenNames = _tokenNames;
/*      */   }
/*      */ 
/*      */   public ANTLRParser(TokenBuffer paramTokenBuffer) {
/*   80 */     this(paramTokenBuffer, 2);
/*      */   }
/*      */ 
/*      */   protected ANTLRParser(TokenStream paramTokenStream, int paramInt) {
/*   84 */     super(paramTokenStream, paramInt);
/*   85 */     this.tokenNames = _tokenNames;
/*      */   }
/*      */ 
/*      */   public ANTLRParser(TokenStream paramTokenStream) {
/*   89 */     this(paramTokenStream, 2);
/*      */   }
/*      */ 
/*      */   public ANTLRParser(ParserSharedInputState paramParserSharedInputState) {
/*   93 */     super(paramParserSharedInputState, 2);
/*   94 */     this.tokenNames = _tokenNames;
/*      */   }
/*      */ 
/*      */   public final void grammar() throws RecognitionException, TokenStreamException
/*      */   {
/*   99 */     Token localToken1 = null;
/*  100 */     Token localToken2 = null;
/*      */     try
/*      */     {
/*  106 */       while (LA(1) == 5) {
/*  107 */         if (this.inputState.guessing == 0)
/*      */         {
/*  109 */           localToken1 = null;
/*      */         }
/*      */ 
/*  113 */         match(5);
/*      */ 
/*  115 */         switch (LA(1))
/*      */         {
/*      */         case 6:
/*  118 */           localToken1 = LT(1);
/*  119 */           match(6);
/*  120 */           break;
/*      */         case 7:
/*  124 */           break;
/*      */         default:
/*  128 */           throw new NoViableAltException(LT(1), getFilename());
/*      */         }
/*      */ 
/*  132 */         localToken2 = LT(1);
/*  133 */         match(7);
/*  134 */         if (this.inputState.guessing == 0)
/*      */         {
/*  138 */           this.behavior.refHeaderAction(localToken1, localToken2);
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/*  149 */       switch (LA(1))
/*      */       {
/*      */       case 14:
/*  152 */         fileOptionsSpec();
/*  153 */         break;
/*      */       case 1:
/*      */       case 7:
/*      */       case 8:
/*      */       case 9:
/*      */       case 10:
/*  161 */         break;
/*      */       case 2:
/*      */       case 3:
/*      */       case 4:
/*      */       case 5:
/*      */       case 6:
/*      */       case 11:
/*      */       case 12:
/*      */       case 13:
/*      */       default:
/*  165 */         throw new NoViableAltException(LT(1), getFilename());
/*      */       }
/*      */ 
/*  172 */       while ((LA(1) >= 7) && (LA(1) <= 10)) {
/*  173 */         classDef();
/*      */       }
/*      */ 
/*  181 */       match(1);
/*      */     }
/*      */     catch (RecognitionException localRecognitionException) {
/*  184 */       if (this.inputState.guessing == 0)
/*      */       {
/*  186 */         reportError(localRecognitionException, "rule grammar trapped:\n" + localRecognitionException.toString());
/*  187 */         consumeUntil(1);
/*      */       }
/*      */       else {
/*  190 */         throw localRecognitionException;
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   public final void fileOptionsSpec()
/*      */     throws RecognitionException, TokenStreamException
/*      */   {
/*  199 */     match(14);
/*      */ 
/*  203 */     while ((LA(1) == 24) || (LA(1) == 41)) {
/*  204 */       Token localToken1 = id();
/*  205 */       match(15);
/*  206 */       Token localToken2 = optionValue();
/*  207 */       if (this.inputState.guessing == 0) {
/*  208 */         this.behavior.setFileOption(localToken1, localToken2, getInputState().filename);
/*      */       }
/*  210 */       match(16);
/*      */     }
/*      */ 
/*  218 */     match(17);
/*      */   }
/*      */ 
/*      */   public final void classDef() throws RecognitionException, TokenStreamException
/*      */   {
/*  223 */     Token localToken1 = null;
/*  224 */     Token localToken2 = null;
/*  225 */     String str = null;
/*      */     try
/*      */     {
/*  229 */       switch (LA(1))
/*      */       {
/*      */       case 7:
/*  232 */         localToken1 = LT(1);
/*  233 */         match(7);
/*  234 */         if (this.inputState.guessing == 0)
/*  235 */           this.behavior.refPreambleAction(localToken1); break;
/*      */       case 8:
/*      */       case 9:
/*      */       case 10:
/*  243 */         break;
/*      */       default:
/*  247 */         throw new NoViableAltException(LT(1), getFilename());
/*      */       }
/*      */ 
/*  252 */       switch (LA(1))
/*      */       {
/*      */       case 8:
/*  255 */         localToken2 = LT(1);
/*  256 */         match(8);
/*  257 */         if (this.inputState.guessing == 0)
/*  258 */           str = localToken2.getText(); break;
/*      */       case 9:
/*      */       case 10:
/*  265 */         break;
/*      */       default:
/*  269 */         throw new NoViableAltException(LT(1), getFilename());
/*      */       }
/*      */ 
/*  274 */       int i = 0;
/*      */       int j;
/*  275 */       if (((LA(1) == 9) || (LA(1) == 10)) && ((LA(2) == 24) || (LA(2) == 41))) {
/*  276 */         j = mark();
/*  277 */         i = 1;
/*  278 */         this.inputState.guessing += 1;
/*      */         try
/*      */         {
/*  281 */           switch (LA(1))
/*      */           {
/*      */           case 9:
/*  284 */             match(9);
/*  285 */             break;
/*      */           case 10:
/*  289 */             match(10);
/*  290 */             id();
/*  291 */             match(11);
/*  292 */             match(12);
/*  293 */             break;
/*      */           default:
/*  297 */             throw new NoViableAltException(LT(1), getFilename());
/*      */           }
/*      */ 
/*      */         }
/*      */         catch (RecognitionException localRecognitionException2)
/*      */         {
/*  303 */           i = 0;
/*      */         }
/*  305 */         rewind(j);
/*  306 */         this.inputState.guessing -= 1;
/*      */       }
/*  308 */       if (i != 0) {
/*  309 */         lexerSpec(str);
/*      */       }
/*      */       else {
/*  312 */         j = 0;
/*  313 */         if ((LA(1) == 10) && ((LA(2) == 24) || (LA(2) == 41))) {
/*  314 */           int m = mark();
/*  315 */           j = 1;
/*  316 */           this.inputState.guessing += 1;
/*      */           try
/*      */           {
/*  319 */             match(10);
/*  320 */             id();
/*  321 */             match(11);
/*  322 */             match(13);
/*      */           }
/*      */           catch (RecognitionException localRecognitionException3)
/*      */           {
/*  326 */             j = 0;
/*      */           }
/*  328 */           rewind(m);
/*  329 */           this.inputState.guessing -= 1;
/*      */         }
/*  331 */         if (j != 0) {
/*  332 */           treeParserSpec(str);
/*      */         }
/*  334 */         else if ((LA(1) == 10) && ((LA(2) == 24) || (LA(2) == 41))) {
/*  335 */           parserSpec(str);
/*      */         }
/*      */         else {
/*  338 */           throw new NoViableAltException(LT(1), getFilename());
/*      */         }
/*      */       }
/*      */ 
/*  342 */       rules();
/*  343 */       if (this.inputState.guessing == 0)
/*  344 */         this.behavior.endGrammar();
/*      */     }
/*      */     catch (RecognitionException localRecognitionException1)
/*      */     {
/*  348 */       if (this.inputState.guessing == 0)
/*      */       {
/*  350 */         if ((localRecognitionException1 instanceof NoViableAltException)) {
/*  351 */           NoViableAltException localNoViableAltException = (NoViableAltException)localRecognitionException1;
/*      */ 
/*  354 */           if (localNoViableAltException.token.getType() == 8) {
/*  355 */             reportError(localRecognitionException1, "JAVADOC comments may only prefix rules and grammars");
/*      */           }
/*      */           else
/*  358 */             reportError(localRecognitionException1, "rule classDef trapped:\n" + localRecognitionException1.toString());
/*      */         }
/*      */         else
/*      */         {
/*  362 */           reportError(localRecognitionException1, "rule classDef trapped:\n" + localRecognitionException1.toString());
/*      */         }
/*  364 */         this.behavior.abortGrammar();
/*  365 */         int k = 1;
/*      */ 
/*  367 */         while (k != 0) {
/*  368 */           consume();
/*  369 */           switch (LA(1)) {
/*      */           case 1:
/*      */           case 9:
/*      */           case 10:
/*  373 */             k = 0;
/*      */           }
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/*  379 */       throw localRecognitionException1;
/*      */     }
/*      */   }
/*      */ 
/*      */   public final Token id()
/*      */     throws RecognitionException, TokenStreamException
/*      */   {
/*  387 */     Token localToken2 = null;
/*  388 */     Token localToken3 = null;
/*  389 */     Token localToken1 = null;
/*      */ 
/*  391 */     switch (LA(1))
/*      */     {
/*      */     case 24:
/*  394 */       localToken2 = LT(1);
/*  395 */       match(24);
/*  396 */       if (this.inputState.guessing == 0)
/*  397 */         localToken1 = localToken2; break;
/*      */     case 41:
/*  403 */       localToken3 = LT(1);
/*  404 */       match(41);
/*  405 */       if (this.inputState.guessing == 0)
/*  406 */         localToken1 = localToken3; break;
/*      */     default:
/*  412 */       throw new NoViableAltException(LT(1), getFilename());
/*      */     }
/*      */ 
/*  415 */     return localToken1;
/*      */   }
/*      */ 
/*      */   public final void lexerSpec(String paramString)
/*      */     throws RecognitionException, TokenStreamException
/*      */   {
/*  422 */     Token localToken1 = null;
/*  423 */     Token localToken2 = null;
/*      */ 
/*  426 */     String str = null;
/*      */     Token localToken3;
/*  430 */     switch (LA(1))
/*      */     {
/*      */     case 9:
/*  433 */       localToken1 = LT(1);
/*  434 */       match(9);
/*  435 */       localToken3 = id();
/*  436 */       if (this.inputState.guessing == 0)
/*      */       {
/*  438 */         this.antlrTool.warning("lexclass' is deprecated; use 'class X extends Lexer'", getFilename(), localToken1.getLine(), localToken1.getColumn()); } break;
/*      */     case 10:
/*  447 */       match(10);
/*  448 */       localToken3 = id();
/*  449 */       match(11);
/*  450 */       match(12);
/*      */ 
/*  452 */       switch (LA(1))
/*      */       {
/*      */       case 27:
/*  455 */         str = superClass();
/*  456 */         break;
/*      */       case 16:
/*  460 */         break;
/*      */       default:
/*  464 */         throw new NoViableAltException(LT(1), getFilename());
/*      */       }
/*      */ 
/*      */       break;
/*      */     default:
/*  472 */       throw new NoViableAltException(LT(1), getFilename());
/*      */     }
/*      */ 
/*  476 */     if (this.inputState.guessing == 0) {
/*  477 */       this.behavior.startLexer(getFilename(), localToken3, str, paramString);
/*      */     }
/*  479 */     match(16);
/*      */ 
/*  481 */     switch (LA(1))
/*      */     {
/*      */     case 14:
/*  484 */       lexerOptionsSpec();
/*  485 */       break;
/*      */     case 7:
/*      */     case 8:
/*      */     case 23:
/*      */     case 24:
/*      */     case 30:
/*      */     case 31:
/*      */     case 32:
/*      */     case 41:
/*  496 */       break;
/*      */     case 9:
/*      */     case 10:
/*      */     case 11:
/*      */     case 12:
/*      */     case 13:
/*      */     case 15:
/*      */     case 16:
/*      */     case 17:
/*      */     case 18:
/*      */     case 19:
/*      */     case 20:
/*      */     case 21:
/*      */     case 22:
/*      */     case 25:
/*      */     case 26:
/*      */     case 27:
/*      */     case 28:
/*      */     case 29:
/*      */     case 33:
/*      */     case 34:
/*      */     case 35:
/*      */     case 36:
/*      */     case 37:
/*      */     case 38:
/*      */     case 39:
/*      */     case 40:
/*      */     default:
/*  500 */       throw new NoViableAltException(LT(1), getFilename());
/*      */     }
/*      */ 
/*  504 */     if (this.inputState.guessing == 0) {
/*  505 */       this.behavior.endOptions();
/*      */     }
/*      */ 
/*  508 */     switch (LA(1))
/*      */     {
/*      */     case 23:
/*  511 */       tokensSpec();
/*  512 */       break;
/*      */     case 7:
/*      */     case 8:
/*      */     case 24:
/*      */     case 30:
/*      */     case 31:
/*      */     case 32:
/*      */     case 41:
/*  522 */       break;
/*      */     default:
/*  526 */       throw new NoViableAltException(LT(1), getFilename());
/*      */     }
/*      */ 
/*  531 */     switch (LA(1))
/*      */     {
/*      */     case 7:
/*  534 */       localToken2 = LT(1);
/*  535 */       match(7);
/*  536 */       if (this.inputState.guessing == 0)
/*  537 */         this.behavior.refMemberAction(localToken2); break;
/*      */     case 8:
/*      */     case 24:
/*      */     case 30:
/*      */     case 31:
/*      */     case 32:
/*      */     case 41:
/*  548 */       break;
/*      */     default:
/*  552 */       throw new NoViableAltException(LT(1), getFilename());
/*      */     }
/*      */   }
/*      */ 
/*      */   public final void treeParserSpec(String paramString)
/*      */     throws RecognitionException, TokenStreamException
/*      */   {
/*  562 */     Token localToken1 = null;
/*      */ 
/*  565 */     String str = null;
/*      */ 
/*  568 */     match(10);
/*  569 */     Token localToken2 = id();
/*  570 */     match(11);
/*  571 */     match(13);
/*      */ 
/*  573 */     switch (LA(1))
/*      */     {
/*      */     case 27:
/*  576 */       str = superClass();
/*  577 */       break;
/*      */     case 16:
/*  581 */       break;
/*      */     default:
/*  585 */       throw new NoViableAltException(LT(1), getFilename());
/*      */     }
/*      */ 
/*  589 */     if (this.inputState.guessing == 0) {
/*  590 */       this.behavior.startTreeWalker(getFilename(), localToken2, str, paramString);
/*      */     }
/*  592 */     match(16);
/*      */ 
/*  594 */     switch (LA(1))
/*      */     {
/*      */     case 14:
/*  597 */       treeParserOptionsSpec();
/*  598 */       break;
/*      */     case 7:
/*      */     case 8:
/*      */     case 23:
/*      */     case 24:
/*      */     case 30:
/*      */     case 31:
/*      */     case 32:
/*      */     case 41:
/*  609 */       break;
/*      */     case 9:
/*      */     case 10:
/*      */     case 11:
/*      */     case 12:
/*      */     case 13:
/*      */     case 15:
/*      */     case 16:
/*      */     case 17:
/*      */     case 18:
/*      */     case 19:
/*      */     case 20:
/*      */     case 21:
/*      */     case 22:
/*      */     case 25:
/*      */     case 26:
/*      */     case 27:
/*      */     case 28:
/*      */     case 29:
/*      */     case 33:
/*      */     case 34:
/*      */     case 35:
/*      */     case 36:
/*      */     case 37:
/*      */     case 38:
/*      */     case 39:
/*      */     case 40:
/*      */     default:
/*  613 */       throw new NoViableAltException(LT(1), getFilename());
/*      */     }
/*      */ 
/*  617 */     if (this.inputState.guessing == 0) {
/*  618 */       this.behavior.endOptions();
/*      */     }
/*      */ 
/*  621 */     switch (LA(1))
/*      */     {
/*      */     case 23:
/*  624 */       tokensSpec();
/*  625 */       break;
/*      */     case 7:
/*      */     case 8:
/*      */     case 24:
/*      */     case 30:
/*      */     case 31:
/*      */     case 32:
/*      */     case 41:
/*  635 */       break;
/*      */     default:
/*  639 */       throw new NoViableAltException(LT(1), getFilename());
/*      */     }
/*      */ 
/*  644 */     switch (LA(1))
/*      */     {
/*      */     case 7:
/*  647 */       localToken1 = LT(1);
/*  648 */       match(7);
/*  649 */       if (this.inputState.guessing == 0)
/*  650 */         this.behavior.refMemberAction(localToken1); break;
/*      */     case 8:
/*      */     case 24:
/*      */     case 30:
/*      */     case 31:
/*      */     case 32:
/*      */     case 41:
/*  661 */       break;
/*      */     default:
/*  665 */       throw new NoViableAltException(LT(1), getFilename());
/*      */     }
/*      */   }
/*      */ 
/*      */   public final void parserSpec(String paramString)
/*      */     throws RecognitionException, TokenStreamException
/*      */   {
/*  675 */     Token localToken1 = null;
/*      */ 
/*  678 */     String str = null;
/*      */ 
/*  681 */     match(10);
/*  682 */     Token localToken2 = id();
/*      */ 
/*  684 */     switch (LA(1))
/*      */     {
/*      */     case 11:
/*  687 */       match(11);
/*  688 */       match(29);
/*      */ 
/*  690 */       switch (LA(1))
/*      */       {
/*      */       case 27:
/*  693 */         str = superClass();
/*  694 */         break;
/*      */       case 16:
/*  698 */         break;
/*      */       default:
/*  702 */         throw new NoViableAltException(LT(1), getFilename());
/*      */       }
/*      */ 
/*      */       break;
/*      */     case 16:
/*  710 */       if (this.inputState.guessing == 0)
/*      */       {
/*  712 */         this.antlrTool.warning("use 'class X extends Parser'", getFilename(), localToken2.getLine(), localToken2.getColumn()); } break;
/*      */     default:
/*  721 */       throw new NoViableAltException(LT(1), getFilename());
/*      */     }
/*      */ 
/*  725 */     if (this.inputState.guessing == 0) {
/*  726 */       this.behavior.startParser(getFilename(), localToken2, str, paramString);
/*      */     }
/*  728 */     match(16);
/*      */ 
/*  730 */     switch (LA(1))
/*      */     {
/*      */     case 14:
/*  733 */       parserOptionsSpec();
/*  734 */       break;
/*      */     case 7:
/*      */     case 8:
/*      */     case 23:
/*      */     case 24:
/*      */     case 30:
/*      */     case 31:
/*      */     case 32:
/*      */     case 41:
/*  745 */       break;
/*      */     case 9:
/*      */     case 10:
/*      */     case 11:
/*      */     case 12:
/*      */     case 13:
/*      */     case 15:
/*      */     case 16:
/*      */     case 17:
/*      */     case 18:
/*      */     case 19:
/*      */     case 20:
/*      */     case 21:
/*      */     case 22:
/*      */     case 25:
/*      */     case 26:
/*      */     case 27:
/*      */     case 28:
/*      */     case 29:
/*      */     case 33:
/*      */     case 34:
/*      */     case 35:
/*      */     case 36:
/*      */     case 37:
/*      */     case 38:
/*      */     case 39:
/*      */     case 40:
/*      */     default:
/*  749 */       throw new NoViableAltException(LT(1), getFilename());
/*      */     }
/*      */ 
/*  753 */     if (this.inputState.guessing == 0) {
/*  754 */       this.behavior.endOptions();
/*      */     }
/*      */ 
/*  757 */     switch (LA(1))
/*      */     {
/*      */     case 23:
/*  760 */       tokensSpec();
/*  761 */       break;
/*      */     case 7:
/*      */     case 8:
/*      */     case 24:
/*      */     case 30:
/*      */     case 31:
/*      */     case 32:
/*      */     case 41:
/*  771 */       break;
/*      */     default:
/*  775 */       throw new NoViableAltException(LT(1), getFilename());
/*      */     }
/*      */ 
/*  780 */     switch (LA(1))
/*      */     {
/*      */     case 7:
/*  783 */       localToken1 = LT(1);
/*  784 */       match(7);
/*  785 */       if (this.inputState.guessing == 0)
/*  786 */         this.behavior.refMemberAction(localToken1); break;
/*      */     case 8:
/*      */     case 24:
/*      */     case 30:
/*      */     case 31:
/*      */     case 32:
/*      */     case 41:
/*  797 */       break;
/*      */     default:
/*  801 */       throw new NoViableAltException(LT(1), getFilename());
/*      */     }
/*      */   }
/*      */ 
/*      */   public final void rules()
/*      */     throws RecognitionException, TokenStreamException
/*      */   {
/*  811 */     int i = 0;
/*      */     while (true)
/*      */     {
/*  814 */       if ((_tokenSet_0.member(LA(1))) && (_tokenSet_1.member(LA(2)))) {
/*  815 */         rule();
/*      */       }
/*      */       else {
/*  818 */         if (i >= 1) break; throw new NoViableAltException(LT(1), getFilename());
/*      */       }
/*      */ 
/*  821 */       i++;
/*      */     }
/*      */   }
/*      */ 
/*      */   public final Token optionValue()
/*      */     throws RecognitionException, TokenStreamException
/*      */   {
/*  829 */     Token localToken2 = null;
/*  830 */     Token localToken3 = null;
/*  831 */     Token localToken4 = null;
/*  832 */     Token localToken1 = null;
/*      */ 
/*  834 */     switch (LA(1))
/*      */     {
/*      */     case 24:
/*      */     case 41:
/*  838 */       localToken1 = qualifiedID();
/*  839 */       break;
/*      */     case 6:
/*  843 */       localToken2 = LT(1);
/*  844 */       match(6);
/*  845 */       if (this.inputState.guessing == 0)
/*  846 */         localToken1 = localToken2; break;
/*      */     case 19:
/*  852 */       localToken3 = LT(1);
/*  853 */       match(19);
/*  854 */       if (this.inputState.guessing == 0)
/*  855 */         localToken1 = localToken3; break;
/*      */     case 20:
/*  861 */       localToken4 = LT(1);
/*  862 */       match(20);
/*  863 */       if (this.inputState.guessing == 0)
/*  864 */         localToken1 = localToken4; break;
/*      */     default:
/*  870 */       throw new NoViableAltException(LT(1), getFilename());
/*      */     }
/*      */ 
/*  873 */     return localToken1;
/*      */   }
/*      */ 
/*      */   public final void parserOptionsSpec()
/*      */     throws RecognitionException, TokenStreamException
/*      */   {
/*  880 */     match(14);
/*      */ 
/*  884 */     while ((LA(1) == 24) || (LA(1) == 41)) {
/*  885 */       Token localToken1 = id();
/*  886 */       match(15);
/*  887 */       Token localToken2 = optionValue();
/*  888 */       if (this.inputState.guessing == 0) {
/*  889 */         this.behavior.setGrammarOption(localToken1, localToken2);
/*      */       }
/*  891 */       match(16);
/*      */     }
/*      */ 
/*  899 */     match(17);
/*      */   }
/*      */ 
/*      */   public final void treeParserOptionsSpec()
/*      */     throws RecognitionException, TokenStreamException
/*      */   {
/*  906 */     match(14);
/*      */ 
/*  910 */     while ((LA(1) == 24) || (LA(1) == 41)) {
/*  911 */       Token localToken1 = id();
/*  912 */       match(15);
/*  913 */       Token localToken2 = optionValue();
/*  914 */       if (this.inputState.guessing == 0) {
/*  915 */         this.behavior.setGrammarOption(localToken1, localToken2);
/*      */       }
/*  917 */       match(16);
/*      */     }
/*      */ 
/*  925 */     match(17);
/*      */   }
/*      */ 
/*      */   public final void lexerOptionsSpec()
/*      */     throws RecognitionException, TokenStreamException
/*      */   {
/*  932 */     match(14);
/*      */     while (true)
/*      */     {
/*  936 */       switch (LA(1))
/*      */       {
/*      */       case 18:
/*  939 */         match(18);
/*  940 */         match(15);
/*  941 */         BitSet localBitSet = charSet();
/*  942 */         match(16);
/*  943 */         if (this.inputState.guessing == 0)
/*  944 */           this.behavior.setCharVocabulary(localBitSet); break;
/*      */       case 24:
/*      */       case 41:
/*  951 */         Token localToken1 = id();
/*  952 */         match(15);
/*  953 */         Token localToken2 = optionValue();
/*  954 */         if (this.inputState.guessing == 0) {
/*  955 */           this.behavior.setGrammarOption(localToken1, localToken2);
/*      */         }
/*  957 */         match(16);
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/*  967 */     match(17);
/*      */   }
/*      */ 
/*      */   public final BitSet charSet()
/*      */     throws RecognitionException, TokenStreamException
/*      */   {
/*  974 */     BitSet localBitSet1 = null;
/*  975 */     BitSet localBitSet2 = null;
/*      */ 
/*  978 */     localBitSet1 = setBlockElement();
/*      */ 
/*  982 */     while (LA(1) == 21) {
/*  983 */       match(21);
/*  984 */       localBitSet2 = setBlockElement();
/*  985 */       if (this.inputState.guessing == 0) {
/*  986 */         localBitSet1.orInPlace(localBitSet2);
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/*  995 */     return localBitSet1;
/*      */   }
/*      */ 
/*      */   public final void subruleOptionsSpec()
/*      */     throws RecognitionException, TokenStreamException
/*      */   {
/* 1002 */     match(14);
/*      */ 
/* 1006 */     while ((LA(1) == 24) || (LA(1) == 41)) {
/* 1007 */       Token localToken1 = id();
/* 1008 */       match(15);
/* 1009 */       Token localToken2 = optionValue();
/* 1010 */       if (this.inputState.guessing == 0) {
/* 1011 */         this.behavior.setSubruleOption(localToken1, localToken2);
/*      */       }
/* 1013 */       match(16);
/*      */     }
/*      */ 
/* 1021 */     match(17);
/*      */   }
/*      */ 
/*      */   public final Token qualifiedID()
/*      */     throws RecognitionException, TokenStreamException
/*      */   {
/* 1028 */     CommonToken localCommonToken = null;
/*      */ 
/* 1031 */     StringBuffer localStringBuffer = new StringBuffer(30);
/*      */ 
/* 1035 */     Token localToken = id();
/* 1036 */     if (this.inputState.guessing == 0) {
/* 1037 */       localStringBuffer.append(localToken.getText());
/*      */     }
/*      */ 
/* 1042 */     while (LA(1) == 50) {
/* 1043 */       match(50);
/* 1044 */       localToken = id();
/* 1045 */       if (this.inputState.guessing == 0) {
/* 1046 */         localStringBuffer.append('.'); localStringBuffer.append(localToken.getText());
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 1055 */     if (this.inputState.guessing == 0)
/*      */     {
/* 1059 */       localCommonToken = new CommonToken(24, localStringBuffer.toString());
/* 1060 */       localCommonToken.setLine(localToken.getLine());
/*      */     }
/*      */ 
/* 1063 */     return localCommonToken;
/*      */   }
/*      */ 
/*      */   public final BitSet setBlockElement()
/*      */     throws RecognitionException, TokenStreamException
/*      */   {
/* 1069 */     Token localToken1 = null;
/* 1070 */     Token localToken2 = null;
/*      */ 
/* 1072 */     BitSet localBitSet = null;
/* 1073 */     int i = 0;
/*      */ 
/* 1076 */     localToken1 = LT(1);
/* 1077 */     match(19);
/* 1078 */     if (this.inputState.guessing == 0)
/*      */     {
/* 1080 */       i = ANTLRLexer.tokenTypeForCharLiteral(localToken1.getText());
/* 1081 */       localBitSet = BitSet.of(i);
/*      */     }
/*      */     int k;
/* 1085 */     switch (LA(1))
/*      */     {
/*      */     case 22:
/* 1088 */       match(22);
/* 1089 */       localToken2 = LT(1);
/* 1090 */       match(19);
/* 1091 */       if (this.inputState.guessing == 0)
/*      */       {
/* 1093 */         int j = ANTLRLexer.tokenTypeForCharLiteral(localToken2.getText());
/* 1094 */         if (j < i) {
/* 1095 */           this.antlrTool.error("Malformed range line ", getFilename(), localToken1.getLine(), localToken1.getColumn());
/*      */         }
/* 1097 */         for (k = i + 1; k <= j; ) {
/* 1098 */           localBitSet.add(k);
/*      */ 
/* 1097 */           k++; continue;
/*      */ 
/* 1107 */           break;
/*      */ 
/* 1111 */           throw new NoViableAltException(LT(1), getFilename()); } 
/*      */       }break;
/*      */     case 16:
/*      */     case 21:
/* 1115 */     }return localBitSet;
/*      */   }
/*      */ 
/*      */   public final void tokensSpec() throws RecognitionException, TokenStreamException
/*      */   {
/* 1120 */     Token localToken1 = null;
/* 1121 */     Token localToken2 = null;
/* 1122 */     Token localToken3 = null;
/*      */ 
/* 1124 */     match(23);
/*      */ 
/* 1126 */     int i = 0;
/*      */     while (true)
/*      */     {
/* 1129 */       if ((LA(1) == 6) || (LA(1) == 24))
/*      */       {
/* 1131 */         switch (LA(1))
/*      */         {
/*      */         case 24:
/* 1134 */           if (this.inputState.guessing == 0) {
/* 1135 */             localToken2 = null;
/*      */           }
/* 1137 */           localToken1 = LT(1);
/* 1138 */           match(24);
/*      */ 
/* 1140 */           switch (LA(1))
/*      */           {
/*      */           case 15:
/* 1143 */             match(15);
/* 1144 */             localToken2 = LT(1);
/* 1145 */             match(6);
/* 1146 */             break;
/*      */           case 16:
/*      */           case 25:
/* 1151 */             break;
/*      */           default:
/* 1155 */             throw new NoViableAltException(LT(1), getFilename());
/*      */           }
/*      */ 
/* 1159 */           if (this.inputState.guessing == 0) {
/* 1160 */             this.behavior.defineToken(localToken1, localToken2);
/*      */           }
/*      */ 
/* 1163 */           switch (LA(1))
/*      */           {
/*      */           case 25:
/* 1166 */             tokensSpecOptions(localToken1);
/* 1167 */             break;
/*      */           case 16:
/* 1171 */             break;
/*      */           default:
/* 1175 */             throw new NoViableAltException(LT(1), getFilename());
/*      */           }
/*      */ 
/*      */           break;
/*      */         case 6:
/* 1183 */           localToken3 = LT(1);
/* 1184 */           match(6);
/* 1185 */           if (this.inputState.guessing == 0) {
/* 1186 */             this.behavior.defineToken(null, localToken3);
/*      */           }
/*      */ 
/* 1189 */           switch (LA(1))
/*      */           {
/*      */           case 25:
/* 1192 */             tokensSpecOptions(localToken3);
/* 1193 */             break;
/*      */           case 16:
/* 1197 */             break;
/*      */           default:
/* 1201 */             throw new NoViableAltException(LT(1), getFilename());
/*      */           }
/*      */ 
/*      */           break;
/*      */         default:
/* 1209 */           throw new NoViableAltException(LT(1), getFilename());
/*      */         }
/*      */ 
/* 1213 */         match(16);
/*      */       }
/*      */       else {
/* 1216 */         if (i >= 1) break; throw new NoViableAltException(LT(1), getFilename());
/*      */       }
/*      */ 
/* 1219 */       i++;
/*      */     }
/*      */ 
/* 1222 */     match(17);
/*      */   }
/*      */ 
/*      */   public final void tokensSpecOptions(Token paramToken)
/*      */     throws RecognitionException, TokenStreamException
/*      */   {
/* 1230 */     Token localToken1 = null; Token localToken2 = null;
/*      */ 
/* 1233 */     match(25);
/* 1234 */     localToken1 = id();
/* 1235 */     match(15);
/* 1236 */     localToken2 = optionValue();
/* 1237 */     if (this.inputState.guessing == 0) {
/* 1238 */       this.behavior.refTokensSpecElementOption(paramToken, localToken1, localToken2);
/*      */     }
/*      */ 
/* 1243 */     while (LA(1) == 16) {
/* 1244 */       match(16);
/* 1245 */       localToken1 = id();
/* 1246 */       match(15);
/* 1247 */       localToken2 = optionValue();
/* 1248 */       if (this.inputState.guessing == 0) {
/* 1249 */         this.behavior.refTokensSpecElementOption(paramToken, localToken1, localToken2);
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 1258 */     match(26);
/*      */   }
/*      */ 
/*      */   public final String superClass()
/*      */     throws RecognitionException, TokenStreamException
/*      */   {
/* 1264 */     String str = null;
/*      */ 
/* 1266 */     match(27);
/* 1267 */     if (this.inputState.guessing == 0)
/*      */     {
/* 1269 */       str = LT(1).getText();
/* 1270 */       str = StringUtils.stripFrontBack(str, "\"", "\"");
/*      */     }
/*      */ 
/* 1274 */     match(6);
/*      */ 
/* 1276 */     match(28);
/* 1277 */     return str;
/*      */   }
/*      */ 
/*      */   public final void rule() throws RecognitionException, TokenStreamException
/*      */   {
/* 1282 */     Token localToken1 = null;
/* 1283 */     Token localToken2 = null;
/* 1284 */     Token localToken3 = null;
/* 1285 */     Token localToken4 = null;
/* 1286 */     Token localToken5 = null;
/* 1287 */     Token localToken6 = null;
/* 1288 */     Token localToken7 = null;
/*      */ 
/* 1290 */     String str1 = "public";
/*      */ 
/* 1292 */     String str2 = null;
/* 1293 */     boolean bool = true;
/* 1294 */     this.blockNesting = -1;
/*      */ 
/* 1298 */     switch (LA(1))
/*      */     {
/*      */     case 8:
/* 1301 */       localToken1 = LT(1);
/* 1302 */       match(8);
/* 1303 */       if (this.inputState.guessing == 0)
/* 1304 */         str2 = localToken1.getText(); break;
/*      */     case 24:
/*      */     case 30:
/*      */     case 31:
/*      */     case 32:
/*      */     case 41:
/* 1314 */       break;
/*      */     default:
/* 1318 */       throw new NoViableAltException(LT(1), getFilename());
/*      */     }
/*      */ 
/* 1323 */     switch (LA(1))
/*      */     {
/*      */     case 30:
/* 1326 */       localToken2 = LT(1);
/* 1327 */       match(30);
/* 1328 */       if (this.inputState.guessing == 0)
/* 1329 */         str1 = localToken2.getText(); break;
/*      */     case 31:
/* 1335 */       localToken3 = LT(1);
/* 1336 */       match(31);
/* 1337 */       if (this.inputState.guessing == 0)
/* 1338 */         str1 = localToken3.getText(); break;
/*      */     case 32:
/* 1344 */       localToken4 = LT(1);
/* 1345 */       match(32);
/* 1346 */       if (this.inputState.guessing == 0)
/* 1347 */         str1 = localToken4.getText(); break;
/*      */     case 24:
/*      */     case 41:
/* 1354 */       break;
/*      */     default:
/* 1358 */       throw new NoViableAltException(LT(1), getFilename());
/*      */     }
/*      */ 
/* 1362 */     Token localToken8 = id();
/*      */ 
/* 1364 */     switch (LA(1))
/*      */     {
/*      */     case 33:
/* 1367 */       match(33);
/* 1368 */       if (this.inputState.guessing == 0)
/* 1369 */         bool = false; break;
/*      */     case 7:
/*      */     case 14:
/*      */     case 34:
/*      */     case 35:
/*      */     case 36:
/*      */     case 37:
/* 1380 */       break;
/*      */     default:
/* 1384 */       throw new NoViableAltException(LT(1), getFilename());
/*      */     }
/*      */ 
/* 1388 */     if (this.inputState.guessing == 0)
/*      */     {
/* 1390 */       this.behavior.defineRuleName(localToken8, str1, bool, str2);
/*      */     }
/*      */ 
/* 1394 */     switch (LA(1))
/*      */     {
/*      */     case 34:
/* 1397 */       localToken5 = LT(1);
/* 1398 */       match(34);
/* 1399 */       if (this.inputState.guessing == 0)
/* 1400 */         this.behavior.refArgAction(localToken5); break;
/*      */     case 7:
/*      */     case 14:
/*      */     case 35:
/*      */     case 36:
/*      */     case 37:
/* 1410 */       break;
/*      */     default:
/* 1414 */       throw new NoViableAltException(LT(1), getFilename());
/*      */     }
/*      */ 
/* 1419 */     switch (LA(1))
/*      */     {
/*      */     case 35:
/* 1422 */       match(35);
/* 1423 */       localToken6 = LT(1);
/* 1424 */       match(34);
/* 1425 */       if (this.inputState.guessing == 0)
/* 1426 */         this.behavior.refReturnAction(localToken6); break;
/*      */     case 7:
/*      */     case 14:
/*      */     case 36:
/*      */     case 37:
/* 1435 */       break;
/*      */     default:
/* 1439 */       throw new NoViableAltException(LT(1), getFilename());
/*      */     }
/*      */ 
/* 1444 */     switch (LA(1))
/*      */     {
/*      */     case 37:
/* 1447 */       throwsSpec();
/* 1448 */       break;
/*      */     case 7:
/*      */     case 14:
/*      */     case 36:
/* 1454 */       break;
/*      */     default:
/* 1458 */       throw new NoViableAltException(LT(1), getFilename());
/*      */     }
/*      */ 
/* 1463 */     switch (LA(1))
/*      */     {
/*      */     case 14:
/* 1466 */       ruleOptionsSpec();
/* 1467 */       break;
/*      */     case 7:
/*      */     case 36:
/* 1472 */       break;
/*      */     default:
/* 1476 */       throw new NoViableAltException(LT(1), getFilename());
/*      */     }
/*      */ 
/* 1481 */     switch (LA(1))
/*      */     {
/*      */     case 7:
/* 1484 */       localToken7 = LT(1);
/* 1485 */       match(7);
/* 1486 */       if (this.inputState.guessing == 0)
/* 1487 */         this.behavior.refInitAction(localToken7); break;
/*      */     case 36:
/* 1493 */       break;
/*      */     default:
/* 1497 */       throw new NoViableAltException(LT(1), getFilename());
/*      */     }
/*      */ 
/* 1501 */     match(36);
/* 1502 */     block();
/* 1503 */     match(16);
/*      */ 
/* 1505 */     switch (LA(1))
/*      */     {
/*      */     case 39:
/* 1508 */       exceptionGroup();
/* 1509 */       break;
/*      */     case 1:
/*      */     case 7:
/*      */     case 8:
/*      */     case 9:
/*      */     case 10:
/*      */     case 24:
/*      */     case 30:
/*      */     case 31:
/*      */     case 32:
/*      */     case 41:
/* 1522 */       break;
/*      */     case 2:
/*      */     case 3:
/*      */     case 4:
/*      */     case 5:
/*      */     case 6:
/*      */     case 11:
/*      */     case 12:
/*      */     case 13:
/*      */     case 14:
/*      */     case 15:
/*      */     case 16:
/*      */     case 17:
/*      */     case 18:
/*      */     case 19:
/*      */     case 20:
/*      */     case 21:
/*      */     case 22:
/*      */     case 23:
/*      */     case 25:
/*      */     case 26:
/*      */     case 27:
/*      */     case 28:
/*      */     case 29:
/*      */     case 33:
/*      */     case 34:
/*      */     case 35:
/*      */     case 36:
/*      */     case 37:
/*      */     case 38:
/*      */     case 40:
/*      */     default:
/* 1526 */       throw new NoViableAltException(LT(1), getFilename());
/*      */     }
/*      */ 
/* 1530 */     if (this.inputState.guessing == 0)
/* 1531 */       this.behavior.endRule(localToken8.getText());
/*      */   }
/*      */ 
/*      */   public final void throwsSpec()
/*      */     throws RecognitionException, TokenStreamException
/*      */   {
/* 1538 */     String str = null;
/*      */ 
/* 1542 */     match(37);
/* 1543 */     Token localToken1 = id();
/* 1544 */     if (this.inputState.guessing == 0) {
/* 1545 */       str = localToken1.getText();
/*      */     }
/*      */ 
/* 1550 */     while (LA(1) == 38) {
/* 1551 */       match(38);
/* 1552 */       Token localToken2 = id();
/* 1553 */       if (this.inputState.guessing == 0) {
/* 1554 */         str = str + "," + localToken2.getText();
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 1563 */     if (this.inputState.guessing == 0)
/* 1564 */       this.behavior.setUserExceptions(str);
/*      */   }
/*      */ 
/*      */   public final void ruleOptionsSpec()
/*      */     throws RecognitionException, TokenStreamException
/*      */   {
/* 1572 */     match(14);
/*      */ 
/* 1576 */     while ((LA(1) == 24) || (LA(1) == 41)) {
/* 1577 */       Token localToken1 = id();
/* 1578 */       match(15);
/* 1579 */       Token localToken2 = optionValue();
/* 1580 */       if (this.inputState.guessing == 0) {
/* 1581 */         this.behavior.setRuleOption(localToken1, localToken2);
/*      */       }
/* 1583 */       match(16);
/*      */     }
/*      */ 
/* 1591 */     match(17);
/*      */   }
/*      */ 
/*      */   public final void block()
/*      */     throws RecognitionException, TokenStreamException
/*      */   {
/* 1597 */     if (this.inputState.guessing == 0) {
/* 1598 */       this.blockNesting += 1;
/*      */     }
/* 1600 */     alternative();
/*      */ 
/* 1604 */     while (LA(1) == 21) {
/* 1605 */       match(21);
/* 1606 */       alternative();
/*      */     }
/*      */ 
/* 1614 */     if (this.inputState.guessing == 0)
/* 1615 */       this.blockNesting -= 1;
/*      */   }
/*      */ 
/*      */   public final void exceptionGroup()
/*      */     throws RecognitionException, TokenStreamException
/*      */   {
/* 1622 */     if (this.inputState.guessing == 0) {
/* 1623 */       this.behavior.beginExceptionGroup();
/*      */     }
/*      */ 
/* 1626 */     int i = 0;
/*      */     while (true)
/*      */     {
/* 1629 */       if (LA(1) == 39) {
/* 1630 */         exceptionSpec();
/*      */       }
/*      */       else {
/* 1633 */         if (i >= 1) break; throw new NoViableAltException(LT(1), getFilename());
/*      */       }
/*      */ 
/* 1636 */       i++;
/*      */     }
/*      */ 
/* 1639 */     if (this.inputState.guessing == 0)
/* 1640 */       this.behavior.endExceptionGroup();
/*      */   }
/*      */ 
/*      */   public final void alternative()
/*      */     throws RecognitionException, TokenStreamException
/*      */   {
/* 1646 */     boolean bool = true;
/*      */ 
/* 1649 */     switch (LA(1))
/*      */     {
/*      */     case 33:
/* 1652 */       match(33);
/* 1653 */       if (this.inputState.guessing == 0)
/* 1654 */         bool = false; break;
/*      */     case 6:
/*      */     case 7:
/*      */     case 16:
/*      */     case 19:
/*      */     case 21:
/*      */     case 24:
/*      */     case 27:
/*      */     case 28:
/*      */     case 39:
/*      */     case 41:
/*      */     case 42:
/*      */     case 43:
/*      */     case 44:
/*      */     case 50:
/* 1673 */       break;
/*      */     case 8:
/*      */     case 9:
/*      */     case 10:
/*      */     case 11:
/*      */     case 12:
/*      */     case 13:
/*      */     case 14:
/*      */     case 15:
/*      */     case 17:
/*      */     case 18:
/*      */     case 20:
/*      */     case 22:
/*      */     case 23:
/*      */     case 25:
/*      */     case 26:
/*      */     case 29:
/*      */     case 30:
/*      */     case 31:
/*      */     case 32:
/*      */     case 34:
/*      */     case 35:
/*      */     case 36:
/*      */     case 37:
/*      */     case 38:
/*      */     case 40:
/*      */     case 45:
/*      */     case 46:
/*      */     case 47:
/*      */     case 48:
/*      */     case 49:
/*      */     default:
/* 1677 */       throw new NoViableAltException(LT(1), getFilename());
/*      */     }
/*      */ 
/* 1681 */     if (this.inputState.guessing == 0) {
/* 1682 */       this.behavior.beginAlt(bool);
/*      */     }
/*      */ 
/* 1687 */     while (_tokenSet_2.member(LA(1))) {
/* 1688 */       element();
/*      */     }
/*      */ 
/* 1697 */     switch (LA(1))
/*      */     {
/*      */     case 39:
/* 1700 */       exceptionSpecNoLabel();
/* 1701 */       break;
/*      */     case 16:
/*      */     case 21:
/*      */     case 28:
/* 1707 */       break;
/*      */     default:
/* 1711 */       throw new NoViableAltException(LT(1), getFilename());
/*      */     }
/*      */ 
/* 1715 */     if (this.inputState.guessing == 0)
/* 1716 */       this.behavior.endAlt();
/*      */   }
/*      */ 
/*      */   public final void element()
/*      */     throws RecognitionException, TokenStreamException
/*      */   {
/* 1723 */     elementNoOptionSpec();
/*      */ 
/* 1725 */     switch (LA(1))
/*      */     {
/*      */     case 25:
/* 1728 */       elementOptionSpec();
/* 1729 */       break;
/*      */     case 6:
/*      */     case 7:
/*      */     case 16:
/*      */     case 19:
/*      */     case 21:
/*      */     case 24:
/*      */     case 27:
/*      */     case 28:
/*      */     case 39:
/*      */     case 41:
/*      */     case 42:
/*      */     case 43:
/*      */     case 44:
/*      */     case 50:
/* 1746 */       break;
/*      */     case 8:
/*      */     case 9:
/*      */     case 10:
/*      */     case 11:
/*      */     case 12:
/*      */     case 13:
/*      */     case 14:
/*      */     case 15:
/*      */     case 17:
/*      */     case 18:
/*      */     case 20:
/*      */     case 22:
/*      */     case 23:
/*      */     case 26:
/*      */     case 29:
/*      */     case 30:
/*      */     case 31:
/*      */     case 32:
/*      */     case 33:
/*      */     case 34:
/*      */     case 35:
/*      */     case 36:
/*      */     case 37:
/*      */     case 38:
/*      */     case 40:
/*      */     case 45:
/*      */     case 46:
/*      */     case 47:
/*      */     case 48:
/*      */     case 49:
/*      */     default:
/* 1750 */       throw new NoViableAltException(LT(1), getFilename());
/*      */     }
/*      */   }
/*      */ 
/*      */   public final void exceptionSpecNoLabel()
/*      */     throws RecognitionException, TokenStreamException
/*      */   {
/* 1759 */     match(39);
/* 1760 */     if (this.inputState.guessing == 0) {
/* 1761 */       this.behavior.beginExceptionSpec(null);
/*      */     }
/*      */ 
/* 1766 */     while (LA(1) == 40) {
/* 1767 */       exceptionHandler();
/*      */     }
/*      */ 
/* 1775 */     if (this.inputState.guessing == 0)
/* 1776 */       this.behavior.endExceptionSpec();
/*      */   }
/*      */ 
/*      */   public final void exceptionSpec()
/*      */     throws RecognitionException, TokenStreamException
/*      */   {
/* 1782 */     Token localToken1 = null;
/* 1783 */     Token localToken2 = null;
/*      */ 
/* 1785 */     match(39);
/*      */ 
/* 1787 */     switch (LA(1))
/*      */     {
/*      */     case 34:
/* 1790 */       localToken1 = LT(1);
/* 1791 */       match(34);
/* 1792 */       if (this.inputState.guessing == 0)
/* 1793 */         localToken2 = localToken1; break;
/*      */     case 1:
/*      */     case 7:
/*      */     case 8:
/*      */     case 9:
/*      */     case 10:
/*      */     case 24:
/*      */     case 30:
/*      */     case 31:
/*      */     case 32:
/*      */     case 39:
/*      */     case 40:
/*      */     case 41:
/* 1810 */       break;
/*      */     case 2:
/*      */     case 3:
/*      */     case 4:
/*      */     case 5:
/*      */     case 6:
/*      */     case 11:
/*      */     case 12:
/*      */     case 13:
/*      */     case 14:
/*      */     case 15:
/*      */     case 16:
/*      */     case 17:
/*      */     case 18:
/*      */     case 19:
/*      */     case 20:
/*      */     case 21:
/*      */     case 22:
/*      */     case 23:
/*      */     case 25:
/*      */     case 26:
/*      */     case 27:
/*      */     case 28:
/*      */     case 29:
/*      */     case 33:
/*      */     case 35:
/*      */     case 36:
/*      */     case 37:
/*      */     case 38:
/*      */     default:
/* 1814 */       throw new NoViableAltException(LT(1), getFilename());
/*      */     }
/*      */ 
/* 1818 */     if (this.inputState.guessing == 0) {
/* 1819 */       this.behavior.beginExceptionSpec(localToken2);
/*      */     }
/*      */ 
/* 1824 */     while (LA(1) == 40) {
/* 1825 */       exceptionHandler();
/*      */     }
/*      */ 
/* 1833 */     if (this.inputState.guessing == 0)
/* 1834 */       this.behavior.endExceptionSpec();
/*      */   }
/*      */ 
/*      */   public final void exceptionHandler()
/*      */     throws RecognitionException, TokenStreamException
/*      */   {
/* 1840 */     Token localToken1 = null;
/* 1841 */     Token localToken2 = null;
/*      */ 
/* 1844 */     match(40);
/* 1845 */     localToken1 = LT(1);
/* 1846 */     match(34);
/* 1847 */     localToken2 = LT(1);
/* 1848 */     match(7);
/* 1849 */     if (this.inputState.guessing == 0)
/* 1850 */       this.behavior.refExceptionHandler(localToken1, localToken2);
/*      */   }
/*      */ 
/*      */   public final void elementNoOptionSpec()
/*      */     throws RecognitionException, TokenStreamException
/*      */   {
/* 1856 */     Token localToken1 = null;
/* 1857 */     Token localToken2 = null;
/* 1858 */     Token localToken3 = null;
/* 1859 */     Token localToken4 = null;
/* 1860 */     Token localToken5 = null;
/* 1861 */     Token localToken6 = null;
/* 1862 */     Token localToken7 = null;
/* 1863 */     Token localToken8 = null;
/*      */ 
/* 1865 */     Token localToken9 = null;
/* 1866 */     Token localToken10 = null;
/* 1867 */     Token localToken11 = null;
/* 1868 */     int i = 1;
/*      */ 
/* 1871 */     switch (LA(1))
/*      */     {
/*      */     case 7:
/* 1874 */       localToken7 = LT(1);
/* 1875 */       match(7);
/* 1876 */       if (this.inputState.guessing == 0)
/* 1877 */         this.behavior.refAction(localToken7); break;
/*      */     case 43:
/* 1883 */       localToken8 = LT(1);
/* 1884 */       match(43);
/* 1885 */       if (this.inputState.guessing == 0)
/* 1886 */         this.behavior.refSemPred(localToken8); break;
/*      */     case 44:
/* 1892 */       tree();
/* 1893 */       break;
/*      */     default:
/* 1896 */       if (((LA(1) == 24) || (LA(1) == 41)) && (LA(2) == 15)) {
/* 1897 */         localToken10 = id();
/* 1898 */         match(15);
/*      */ 
/* 1900 */         if (((LA(1) == 24) || (LA(1) == 41)) && (LA(2) == 36)) {
/* 1901 */           localToken9 = id();
/* 1902 */           match(36);
/* 1903 */           if (this.inputState.guessing == 0) {
/* 1904 */             checkForMissingEndRule(localToken9);
/*      */           }
/*      */         }
/* 1907 */         else if (((LA(1) != 24) && (LA(1) != 41)) || (!_tokenSet_3.member(LA(2))))
/*      */         {
/* 1910 */           throw new NoViableAltException(LT(1), getFilename());
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/* 1915 */       switch (LA(1))
/*      */       {
/*      */       case 41:
/* 1918 */         localToken1 = LT(1);
/* 1919 */         match(41);
/*      */ 
/* 1921 */         switch (LA(1))
/*      */         {
/*      */         case 34:
/* 1924 */           localToken2 = LT(1);
/* 1925 */           match(34);
/* 1926 */           if (this.inputState.guessing == 0)
/* 1927 */             localToken11 = localToken2; break;
/*      */         case 6:
/*      */         case 7:
/*      */         case 16:
/*      */         case 19:
/*      */         case 21:
/*      */         case 24:
/*      */         case 25:
/*      */         case 27:
/*      */         case 28:
/*      */         case 33:
/*      */         case 39:
/*      */         case 41:
/*      */         case 42:
/*      */         case 43:
/*      */         case 44:
/*      */         case 50:
/* 1948 */           break;
/*      */         case 8:
/*      */         case 9:
/*      */         case 10:
/*      */         case 11:
/*      */         case 12:
/*      */         case 13:
/*      */         case 14:
/*      */         case 15:
/*      */         case 17:
/*      */         case 18:
/*      */         case 20:
/*      */         case 22:
/*      */         case 23:
/*      */         case 26:
/*      */         case 29:
/*      */         case 30:
/*      */         case 31:
/*      */         case 32:
/*      */         case 35:
/*      */         case 36:
/*      */         case 37:
/*      */         case 38:
/*      */         case 40:
/*      */         case 45:
/*      */         case 46:
/*      */         case 47:
/*      */         case 48:
/*      */         case 49:
/*      */         default:
/* 1952 */           throw new NoViableAltException(LT(1), getFilename());
/*      */         }
/*      */ 
/* 1957 */         switch (LA(1))
/*      */         {
/*      */         case 33:
/* 1960 */           match(33);
/* 1961 */           if (this.inputState.guessing == 0)
/* 1962 */             i = 3; break;
/*      */         case 6:
/*      */         case 7:
/*      */         case 16:
/*      */         case 19:
/*      */         case 21:
/*      */         case 24:
/*      */         case 25:
/*      */         case 27:
/*      */         case 28:
/*      */         case 39:
/*      */         case 41:
/*      */         case 42:
/*      */         case 43:
/*      */         case 44:
/*      */         case 50:
/* 1982 */           break;
/*      */         case 8:
/*      */         case 9:
/*      */         case 10:
/*      */         case 11:
/*      */         case 12:
/*      */         case 13:
/*      */         case 14:
/*      */         case 15:
/*      */         case 17:
/*      */         case 18:
/*      */         case 20:
/*      */         case 22:
/*      */         case 23:
/*      */         case 26:
/*      */         case 29:
/*      */         case 30:
/*      */         case 31:
/*      */         case 32:
/*      */         case 34:
/*      */         case 35:
/*      */         case 36:
/*      */         case 37:
/*      */         case 38:
/*      */         case 40:
/*      */         case 45:
/*      */         case 46:
/*      */         case 47:
/*      */         case 48:
/*      */         case 49:
/*      */         default:
/* 1986 */           throw new NoViableAltException(LT(1), getFilename());
/*      */         }
/*      */ 
/* 1990 */         if (this.inputState.guessing == 0)
/* 1991 */           this.behavior.refRule(localToken10, localToken1, localToken9, localToken11, i); break;
/*      */       case 24:
/* 1997 */         localToken3 = LT(1);
/* 1998 */         match(24);
/*      */ 
/* 2000 */         switch (LA(1))
/*      */         {
/*      */         case 34:
/* 2003 */           localToken4 = LT(1);
/* 2004 */           match(34);
/* 2005 */           if (this.inputState.guessing == 0)
/* 2006 */             localToken11 = localToken4; break;
/*      */         case 6:
/*      */         case 7:
/*      */         case 16:
/*      */         case 19:
/*      */         case 21:
/*      */         case 24:
/*      */         case 25:
/*      */         case 27:
/*      */         case 28:
/*      */         case 39:
/*      */         case 41:
/*      */         case 42:
/*      */         case 43:
/*      */         case 44:
/*      */         case 50:
/* 2026 */           break;
/*      */         case 8:
/*      */         case 9:
/*      */         case 10:
/*      */         case 11:
/*      */         case 12:
/*      */         case 13:
/*      */         case 14:
/*      */         case 15:
/*      */         case 17:
/*      */         case 18:
/*      */         case 20:
/*      */         case 22:
/*      */         case 23:
/*      */         case 26:
/*      */         case 29:
/*      */         case 30:
/*      */         case 31:
/*      */         case 32:
/*      */         case 33:
/*      */         case 35:
/*      */         case 36:
/*      */         case 37:
/*      */         case 38:
/*      */         case 40:
/*      */         case 45:
/*      */         case 46:
/*      */         case 47:
/*      */         case 48:
/*      */         case 49:
/*      */         default:
/* 2030 */           throw new NoViableAltException(LT(1), getFilename());
/*      */         }
/*      */ 
/* 2034 */         if (this.inputState.guessing == 0)
/* 2035 */           this.behavior.refToken(localToken10, localToken3, localToken9, localToken11, false, i, lastInRule()); break;
/*      */       default:
/* 2041 */         throw new NoViableAltException(LT(1), getFilename());
/*      */ 
/* 2046 */         if ((_tokenSet_4.member(LA(1))) && (_tokenSet_5.member(LA(2))))
/*      */         {
/* 2048 */           if (((LA(1) == 24) || (LA(1) == 41)) && (LA(2) == 36)) {
/* 2049 */             localToken9 = id();
/* 2050 */             match(36);
/* 2051 */             if (this.inputState.guessing == 0) {
/* 2052 */               checkForMissingEndRule(localToken9);
/*      */             }
/*      */           }
/* 2055 */           else if ((!_tokenSet_4.member(LA(1))) || (!_tokenSet_6.member(LA(2))))
/*      */           {
/* 2058 */             throw new NoViableAltException(LT(1), getFilename());
/*      */           }
/*      */ 
/*      */         }
/*      */ 
/* 2063 */         switch (LA(1))
/*      */         {
/*      */         case 41:
/* 2066 */           localToken5 = LT(1);
/* 2067 */           match(41);
/*      */ 
/* 2069 */           switch (LA(1))
/*      */           {
/*      */           case 34:
/* 2072 */             localToken6 = LT(1);
/* 2073 */             match(34);
/* 2074 */             if (this.inputState.guessing == 0)
/* 2075 */               localToken11 = localToken6; break;
/*      */           case 6:
/*      */           case 7:
/*      */           case 16:
/*      */           case 19:
/*      */           case 21:
/*      */           case 24:
/*      */           case 25:
/*      */           case 27:
/*      */           case 28:
/*      */           case 33:
/*      */           case 39:
/*      */           case 41:
/*      */           case 42:
/*      */           case 43:
/*      */           case 44:
/*      */           case 50:
/* 2096 */             break;
/*      */           case 8:
/*      */           case 9:
/*      */           case 10:
/*      */           case 11:
/*      */           case 12:
/*      */           case 13:
/*      */           case 14:
/*      */           case 15:
/*      */           case 17:
/*      */           case 18:
/*      */           case 20:
/*      */           case 22:
/*      */           case 23:
/*      */           case 26:
/*      */           case 29:
/*      */           case 30:
/*      */           case 31:
/*      */           case 32:
/*      */           case 35:
/*      */           case 36:
/*      */           case 37:
/*      */           case 38:
/*      */           case 40:
/*      */           case 45:
/*      */           case 46:
/*      */           case 47:
/*      */           case 48:
/*      */           case 49:
/*      */           default:
/* 2100 */             throw new NoViableAltException(LT(1), getFilename());
/*      */           }
/*      */ 
/* 2105 */           switch (LA(1))
/*      */           {
/*      */           case 33:
/* 2108 */             match(33);
/* 2109 */             if (this.inputState.guessing == 0)
/* 2110 */               i = 3; break;
/*      */           case 6:
/*      */           case 7:
/*      */           case 16:
/*      */           case 19:
/*      */           case 21:
/*      */           case 24:
/*      */           case 25:
/*      */           case 27:
/*      */           case 28:
/*      */           case 39:
/*      */           case 41:
/*      */           case 42:
/*      */           case 43:
/*      */           case 44:
/*      */           case 50:
/* 2130 */             break;
/*      */           case 8:
/*      */           case 9:
/*      */           case 10:
/*      */           case 11:
/*      */           case 12:
/*      */           case 13:
/*      */           case 14:
/*      */           case 15:
/*      */           case 17:
/*      */           case 18:
/*      */           case 20:
/*      */           case 22:
/*      */           case 23:
/*      */           case 26:
/*      */           case 29:
/*      */           case 30:
/*      */           case 31:
/*      */           case 32:
/*      */           case 34:
/*      */           case 35:
/*      */           case 36:
/*      */           case 37:
/*      */           case 38:
/*      */           case 40:
/*      */           case 45:
/*      */           case 46:
/*      */           case 47:
/*      */           case 48:
/*      */           case 49:
/*      */           default:
/* 2134 */             throw new NoViableAltException(LT(1), getFilename());
/*      */           }
/*      */ 
/* 2138 */           if (this.inputState.guessing == 0)
/* 2139 */             this.behavior.refRule(localToken10, localToken5, localToken9, localToken11, i); break;
/*      */         case 42:
/* 2145 */           match(42);
/*      */ 
/* 2147 */           switch (LA(1))
/*      */           {
/*      */           case 19:
/*      */           case 24:
/* 2151 */             notTerminal(localToken9);
/* 2152 */             break;
/*      */           case 27:
/* 2156 */             ebnf(localToken9, true);
/* 2157 */             break;
/*      */           default:
/* 2161 */             throw new NoViableAltException(LT(1), getFilename());
/*      */           }
/*      */ 
/*      */           break;
/*      */         case 27:
/* 2169 */           ebnf(localToken9, false);
/* 2170 */           break;
/*      */         default:
/* 2173 */           if (((LA(1) == 6) || (LA(1) == 19) || (LA(1) == 24)) && (LA(2) == 22)) {
/* 2174 */             range(localToken9);
/*      */           }
/* 2176 */           else if ((_tokenSet_7.member(LA(1))) && (_tokenSet_8.member(LA(2)))) {
/* 2177 */             terminal(localToken9);
/*      */           }
/*      */           else {
/* 2180 */             throw new NoViableAltException(LT(1), getFilename());
/*      */ 
/* 2186 */             throw new NoViableAltException(LT(1), getFilename());
/*      */           }break;
/*      */         }break;
/*      */       }break;
/*      */     }
/*      */   }
/*      */ 
/*      */   public final void elementOptionSpec() throws RecognitionException, TokenStreamException {
/* 2194 */     Token localToken1 = null; Token localToken2 = null;
/*      */ 
/* 2197 */     match(25);
/* 2198 */     localToken1 = id();
/* 2199 */     match(15);
/* 2200 */     localToken2 = optionValue();
/* 2201 */     if (this.inputState.guessing == 0) {
/* 2202 */       this.behavior.refElementOption(localToken1, localToken2);
/*      */     }
/*      */ 
/* 2207 */     while (LA(1) == 16) {
/* 2208 */       match(16);
/* 2209 */       localToken1 = id();
/* 2210 */       match(15);
/* 2211 */       localToken2 = optionValue();
/* 2212 */       if (this.inputState.guessing == 0) {
/* 2213 */         this.behavior.refElementOption(localToken1, localToken2);
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 2222 */     match(26);
/*      */   }
/*      */ 
/*      */   public final void range(Token paramToken)
/*      */     throws RecognitionException, TokenStreamException
/*      */   {
/* 2229 */     Token localToken1 = null;
/* 2230 */     Token localToken2 = null;
/* 2231 */     Token localToken3 = null;
/* 2232 */     Token localToken4 = null;
/* 2233 */     Token localToken5 = null;
/* 2234 */     Token localToken6 = null;
/*      */ 
/* 2236 */     Token localToken7 = null;
/* 2237 */     Token localToken8 = null;
/* 2238 */     int i = 1;
/*      */ 
/* 2241 */     switch (LA(1))
/*      */     {
/*      */     case 19:
/* 2244 */       localToken1 = LT(1);
/* 2245 */       match(19);
/* 2246 */       match(22);
/* 2247 */       localToken2 = LT(1);
/* 2248 */       match(19);
/*      */ 
/* 2250 */       switch (LA(1))
/*      */       {
/*      */       case 33:
/* 2253 */         match(33);
/* 2254 */         if (this.inputState.guessing == 0)
/* 2255 */           i = 3; break;
/*      */       case 6:
/*      */       case 7:
/*      */       case 16:
/*      */       case 19:
/*      */       case 21:
/*      */       case 24:
/*      */       case 25:
/*      */       case 27:
/*      */       case 28:
/*      */       case 39:
/*      */       case 41:
/*      */       case 42:
/*      */       case 43:
/*      */       case 44:
/*      */       case 50:
/* 2275 */         break;
/*      */       case 8:
/*      */       case 9:
/*      */       case 10:
/*      */       case 11:
/*      */       case 12:
/*      */       case 13:
/*      */       case 14:
/*      */       case 15:
/*      */       case 17:
/*      */       case 18:
/*      */       case 20:
/*      */       case 22:
/*      */       case 23:
/*      */       case 26:
/*      */       case 29:
/*      */       case 30:
/*      */       case 31:
/*      */       case 32:
/*      */       case 34:
/*      */       case 35:
/*      */       case 36:
/*      */       case 37:
/*      */       case 38:
/*      */       case 40:
/*      */       case 45:
/*      */       case 46:
/*      */       case 47:
/*      */       case 48:
/*      */       case 49:
/*      */       default:
/* 2279 */         throw new NoViableAltException(LT(1), getFilename());
/*      */       }
/*      */ 
/* 2283 */       if (this.inputState.guessing == 0)
/* 2284 */         this.behavior.refCharRange(localToken1, localToken2, paramToken, i, lastInRule()); break;
/*      */     case 6:
/*      */     case 24:
/* 2292 */       switch (LA(1))
/*      */       {
/*      */       case 24:
/* 2295 */         localToken3 = LT(1);
/* 2296 */         match(24);
/* 2297 */         if (this.inputState.guessing == 0)
/* 2298 */           localToken7 = localToken3; break;
/*      */       case 6:
/* 2304 */         localToken4 = LT(1);
/* 2305 */         match(6);
/* 2306 */         if (this.inputState.guessing == 0)
/* 2307 */           localToken7 = localToken4; break;
/*      */       default:
/* 2313 */         throw new NoViableAltException(LT(1), getFilename());
/*      */       }
/*      */ 
/* 2317 */       match(22);
/*      */ 
/* 2319 */       switch (LA(1))
/*      */       {
/*      */       case 24:
/* 2322 */         localToken5 = LT(1);
/* 2323 */         match(24);
/* 2324 */         if (this.inputState.guessing == 0)
/* 2325 */           localToken8 = localToken5; break;
/*      */       case 6:
/* 2331 */         localToken6 = LT(1);
/* 2332 */         match(6);
/* 2333 */         if (this.inputState.guessing == 0)
/* 2334 */           localToken8 = localToken6; break;
/*      */       default:
/* 2340 */         throw new NoViableAltException(LT(1), getFilename());
/*      */       }
/*      */ 
/* 2344 */       i = ast_type_spec();
/* 2345 */       if (this.inputState.guessing == 0)
/* 2346 */         this.behavior.refTokenRange(localToken7, localToken8, paramToken, i, lastInRule()); break;
/*      */     default:
/* 2352 */       throw new NoViableAltException(LT(1), getFilename());
/*      */     }
/*      */   }
/*      */ 
/*      */   public final void terminal(Token paramToken)
/*      */     throws RecognitionException, TokenStreamException
/*      */   {
/* 2361 */     Token localToken1 = null;
/* 2362 */     Token localToken2 = null;
/* 2363 */     Token localToken3 = null;
/* 2364 */     Token localToken4 = null;
/* 2365 */     Token localToken5 = null;
/*      */ 
/* 2367 */     int i = 1;
/* 2368 */     Token localToken6 = null;
/*      */ 
/* 2371 */     switch (LA(1))
/*      */     {
/*      */     case 19:
/* 2374 */       localToken1 = LT(1);
/* 2375 */       match(19);
/*      */ 
/* 2377 */       switch (LA(1))
/*      */       {
/*      */       case 33:
/* 2380 */         match(33);
/* 2381 */         if (this.inputState.guessing == 0)
/* 2382 */           i = 3; break;
/*      */       case 6:
/*      */       case 7:
/*      */       case 16:
/*      */       case 19:
/*      */       case 21:
/*      */       case 24:
/*      */       case 25:
/*      */       case 27:
/*      */       case 28:
/*      */       case 39:
/*      */       case 41:
/*      */       case 42:
/*      */       case 43:
/*      */       case 44:
/*      */       case 50:
/* 2402 */         break;
/*      */       case 8:
/*      */       case 9:
/*      */       case 10:
/*      */       case 11:
/*      */       case 12:
/*      */       case 13:
/*      */       case 14:
/*      */       case 15:
/*      */       case 17:
/*      */       case 18:
/*      */       case 20:
/*      */       case 22:
/*      */       case 23:
/*      */       case 26:
/*      */       case 29:
/*      */       case 30:
/*      */       case 31:
/*      */       case 32:
/*      */       case 34:
/*      */       case 35:
/*      */       case 36:
/*      */       case 37:
/*      */       case 38:
/*      */       case 40:
/*      */       case 45:
/*      */       case 46:
/*      */       case 47:
/*      */       case 48:
/*      */       case 49:
/*      */       default:
/* 2406 */         throw new NoViableAltException(LT(1), getFilename());
/*      */       }
/*      */ 
/* 2410 */       if (this.inputState.guessing == 0)
/* 2411 */         this.behavior.refCharLiteral(localToken1, paramToken, false, i, lastInRule()); break;
/*      */     case 24:
/* 2417 */       localToken2 = LT(1);
/* 2418 */       match(24);
/* 2419 */       i = ast_type_spec();
/*      */ 
/* 2421 */       switch (LA(1))
/*      */       {
/*      */       case 34:
/* 2424 */         localToken3 = LT(1);
/* 2425 */         match(34);
/* 2426 */         if (this.inputState.guessing == 0)
/* 2427 */           localToken6 = localToken3; break;
/*      */       case 6:
/*      */       case 7:
/*      */       case 16:
/*      */       case 19:
/*      */       case 21:
/*      */       case 24:
/*      */       case 25:
/*      */       case 27:
/*      */       case 28:
/*      */       case 39:
/*      */       case 41:
/*      */       case 42:
/*      */       case 43:
/*      */       case 44:
/*      */       case 50:
/* 2447 */         break;
/*      */       case 8:
/*      */       case 9:
/*      */       case 10:
/*      */       case 11:
/*      */       case 12:
/*      */       case 13:
/*      */       case 14:
/*      */       case 15:
/*      */       case 17:
/*      */       case 18:
/*      */       case 20:
/*      */       case 22:
/*      */       case 23:
/*      */       case 26:
/*      */       case 29:
/*      */       case 30:
/*      */       case 31:
/*      */       case 32:
/*      */       case 33:
/*      */       case 35:
/*      */       case 36:
/*      */       case 37:
/*      */       case 38:
/*      */       case 40:
/*      */       case 45:
/*      */       case 46:
/*      */       case 47:
/*      */       case 48:
/*      */       case 49:
/*      */       default:
/* 2451 */         throw new NoViableAltException(LT(1), getFilename());
/*      */       }
/*      */ 
/* 2455 */       if (this.inputState.guessing == 0)
/* 2456 */         this.behavior.refToken(null, localToken2, paramToken, localToken6, false, i, lastInRule()); break;
/*      */     case 6:
/* 2462 */       localToken4 = LT(1);
/* 2463 */       match(6);
/* 2464 */       i = ast_type_spec();
/* 2465 */       if (this.inputState.guessing == 0)
/* 2466 */         this.behavior.refStringLiteral(localToken4, paramToken, i, lastInRule()); break;
/*      */     case 50:
/* 2472 */       localToken5 = LT(1);
/* 2473 */       match(50);
/* 2474 */       i = ast_type_spec();
/* 2475 */       if (this.inputState.guessing == 0)
/* 2476 */         this.behavior.refWildcard(localToken5, paramToken, i); break;
/*      */     default:
/* 2482 */       throw new NoViableAltException(LT(1), getFilename());
/*      */     }
/*      */   }
/*      */ 
/*      */   public final void notTerminal(Token paramToken)
/*      */     throws RecognitionException, TokenStreamException
/*      */   {
/* 2491 */     Token localToken1 = null;
/* 2492 */     Token localToken2 = null;
/* 2493 */     int i = 1;
/*      */ 
/* 2495 */     switch (LA(1))
/*      */     {
/*      */     case 19:
/* 2498 */       localToken1 = LT(1);
/* 2499 */       match(19);
/*      */ 
/* 2501 */       switch (LA(1))
/*      */       {
/*      */       case 33:
/* 2504 */         match(33);
/* 2505 */         if (this.inputState.guessing == 0)
/* 2506 */           i = 3; break;
/*      */       case 6:
/*      */       case 7:
/*      */       case 16:
/*      */       case 19:
/*      */       case 21:
/*      */       case 24:
/*      */       case 25:
/*      */       case 27:
/*      */       case 28:
/*      */       case 39:
/*      */       case 41:
/*      */       case 42:
/*      */       case 43:
/*      */       case 44:
/*      */       case 50:
/* 2526 */         break;
/*      */       case 8:
/*      */       case 9:
/*      */       case 10:
/*      */       case 11:
/*      */       case 12:
/*      */       case 13:
/*      */       case 14:
/*      */       case 15:
/*      */       case 17:
/*      */       case 18:
/*      */       case 20:
/*      */       case 22:
/*      */       case 23:
/*      */       case 26:
/*      */       case 29:
/*      */       case 30:
/*      */       case 31:
/*      */       case 32:
/*      */       case 34:
/*      */       case 35:
/*      */       case 36:
/*      */       case 37:
/*      */       case 38:
/*      */       case 40:
/*      */       case 45:
/*      */       case 46:
/*      */       case 47:
/*      */       case 48:
/*      */       case 49:
/*      */       default:
/* 2530 */         throw new NoViableAltException(LT(1), getFilename());
/*      */       }
/*      */ 
/* 2534 */       if (this.inputState.guessing == 0)
/* 2535 */         this.behavior.refCharLiteral(localToken1, paramToken, true, i, lastInRule()); break;
/*      */     case 24:
/* 2541 */       localToken2 = LT(1);
/* 2542 */       match(24);
/* 2543 */       i = ast_type_spec();
/* 2544 */       if (this.inputState.guessing == 0)
/* 2545 */         this.behavior.refToken(null, localToken2, paramToken, null, true, i, lastInRule()); break;
/*      */     default:
/* 2551 */       throw new NoViableAltException(LT(1), getFilename());
/*      */     }
/*      */   }
/*      */ 
/*      */   public final void ebnf(Token paramToken, boolean paramBoolean)
/*      */     throws RecognitionException, TokenStreamException
/*      */   {
/* 2560 */     Token localToken1 = null;
/* 2561 */     Token localToken2 = null;
/* 2562 */     Token localToken3 = null;
/*      */ 
/* 2564 */     localToken1 = LT(1);
/* 2565 */     match(27);
/* 2566 */     if (this.inputState.guessing == 0) {
/* 2567 */       this.behavior.beginSubRule(paramToken, localToken1, paramBoolean);
/*      */     }
/*      */ 
/* 2570 */     if (LA(1) == 14) {
/* 2571 */       subruleOptionsSpec();
/*      */ 
/* 2573 */       switch (LA(1))
/*      */       {
/*      */       case 7:
/* 2576 */         localToken2 = LT(1);
/* 2577 */         match(7);
/* 2578 */         if (this.inputState.guessing == 0)
/* 2579 */           this.behavior.refInitAction(localToken2); break;
/*      */       case 36:
/* 2585 */         break;
/*      */       default:
/* 2589 */         throw new NoViableAltException(LT(1), getFilename());
/*      */       }
/*      */ 
/* 2593 */       match(36);
/*      */     }
/* 2595 */     else if ((LA(1) == 7) && (LA(2) == 36)) {
/* 2596 */       localToken3 = LT(1);
/* 2597 */       match(7);
/* 2598 */       if (this.inputState.guessing == 0) {
/* 2599 */         this.behavior.refInitAction(localToken3);
/*      */       }
/* 2601 */       match(36);
/*      */     }
/* 2603 */     else if ((!_tokenSet_9.member(LA(1))) || (!_tokenSet_10.member(LA(2))))
/*      */     {
/* 2606 */       throw new NoViableAltException(LT(1), getFilename());
/*      */     }
/*      */ 
/* 2610 */     block();
/* 2611 */     match(28);
/*      */ 
/* 2613 */     switch (LA(1))
/*      */     {
/*      */     case 6:
/*      */     case 7:
/*      */     case 16:
/*      */     case 19:
/*      */     case 21:
/*      */     case 24:
/*      */     case 25:
/*      */     case 27:
/*      */     case 28:
/*      */     case 33:
/*      */     case 39:
/*      */     case 41:
/*      */     case 42:
/*      */     case 43:
/*      */     case 44:
/*      */     case 45:
/*      */     case 46:
/*      */     case 47:
/*      */     case 50:
/* 2635 */       switch (LA(1))
/*      */       {
/*      */       case 45:
/* 2638 */         match(45);
/* 2639 */         if (this.inputState.guessing == 0)
/* 2640 */           this.behavior.optionalSubRule(); break;
/*      */       case 46:
/* 2646 */         match(46);
/* 2647 */         if (this.inputState.guessing == 0)
/* 2648 */           this.behavior.zeroOrMoreSubRule(); break;
/*      */       case 47:
/* 2654 */         match(47);
/* 2655 */         if (this.inputState.guessing == 0)
/* 2656 */           this.behavior.oneOrMoreSubRule(); break;
/*      */       case 6:
/*      */       case 7:
/*      */       case 16:
/*      */       case 19:
/*      */       case 21:
/*      */       case 24:
/*      */       case 25:
/*      */       case 27:
/*      */       case 28:
/*      */       case 33:
/*      */       case 39:
/*      */       case 41:
/*      */       case 42:
/*      */       case 43:
/*      */       case 44:
/*      */       case 50:
/* 2677 */         break;
/*      */       case 8:
/*      */       case 9:
/*      */       case 10:
/*      */       case 11:
/*      */       case 12:
/*      */       case 13:
/*      */       case 14:
/*      */       case 15:
/*      */       case 17:
/*      */       case 18:
/*      */       case 20:
/*      */       case 22:
/*      */       case 23:
/*      */       case 26:
/*      */       case 29:
/*      */       case 30:
/*      */       case 31:
/*      */       case 32:
/*      */       case 34:
/*      */       case 35:
/*      */       case 36:
/*      */       case 37:
/*      */       case 38:
/*      */       case 40:
/*      */       case 48:
/*      */       case 49:
/*      */       default:
/* 2681 */         throw new NoViableAltException(LT(1), getFilename());
/*      */       }
/*      */ 
/* 2686 */       switch (LA(1))
/*      */       {
/*      */       case 33:
/* 2689 */         match(33);
/* 2690 */         if (this.inputState.guessing == 0)
/* 2691 */           this.behavior.noASTSubRule(); break;
/*      */       case 6:
/*      */       case 7:
/*      */       case 16:
/*      */       case 19:
/*      */       case 21:
/*      */       case 24:
/*      */       case 25:
/*      */       case 27:
/*      */       case 28:
/*      */       case 39:
/*      */       case 41:
/*      */       case 42:
/*      */       case 43:
/*      */       case 44:
/*      */       case 50:
/* 2711 */         break;
/*      */       case 8:
/*      */       case 9:
/*      */       case 10:
/*      */       case 11:
/*      */       case 12:
/*      */       case 13:
/*      */       case 14:
/*      */       case 15:
/*      */       case 17:
/*      */       case 18:
/*      */       case 20:
/*      */       case 22:
/*      */       case 23:
/*      */       case 26:
/*      */       case 29:
/*      */       case 30:
/*      */       case 31:
/*      */       case 32:
/*      */       case 34:
/*      */       case 35:
/*      */       case 36:
/*      */       case 37:
/*      */       case 38:
/*      */       case 40:
/*      */       case 45:
/*      */       case 46:
/*      */       case 47:
/*      */       case 48:
/*      */       case 49:
/*      */       default:
/* 2715 */         throw new NoViableAltException(LT(1), getFilename());
/*      */       }
/*      */ 
/*      */       break;
/*      */     case 48:
/* 2723 */       match(48);
/* 2724 */       if (this.inputState.guessing == 0)
/* 2725 */         this.behavior.synPred(); break;
/*      */     case 8:
/*      */     case 9:
/*      */     case 10:
/*      */     case 11:
/*      */     case 12:
/*      */     case 13:
/*      */     case 14:
/*      */     case 15:
/*      */     case 17:
/*      */     case 18:
/*      */     case 20:
/*      */     case 22:
/*      */     case 23:
/*      */     case 26:
/*      */     case 29:
/*      */     case 30:
/*      */     case 31:
/*      */     case 32:
/*      */     case 34:
/*      */     case 35:
/*      */     case 36:
/*      */     case 37:
/*      */     case 38:
/*      */     case 40:
/*      */     case 49:
/*      */     default:
/* 2731 */       throw new NoViableAltException(LT(1), getFilename());
/*      */     }
/*      */ 
/* 2735 */     if (this.inputState.guessing == 0)
/* 2736 */       this.behavior.endSubRule();
/*      */   }
/*      */ 
/*      */   public final void tree()
/*      */     throws RecognitionException, TokenStreamException
/*      */   {
/* 2742 */     Token localToken = null;
/*      */ 
/* 2744 */     localToken = LT(1);
/* 2745 */     match(44);
/* 2746 */     if (this.inputState.guessing == 0) {
/* 2747 */       this.behavior.beginTree(localToken);
/*      */     }
/* 2749 */     rootNode();
/* 2750 */     if (this.inputState.guessing == 0) {
/* 2751 */       this.behavior.beginChildList();
/*      */     }
/*      */ 
/* 2754 */     int i = 0;
/*      */     while (true)
/*      */     {
/* 2757 */       if (_tokenSet_2.member(LA(1))) {
/* 2758 */         element();
/*      */       }
/*      */       else {
/* 2761 */         if (i >= 1) break; throw new NoViableAltException(LT(1), getFilename());
/*      */       }
/*      */ 
/* 2764 */       i++;
/*      */     }
/*      */ 
/* 2767 */     if (this.inputState.guessing == 0) {
/* 2768 */       this.behavior.endChildList();
/*      */     }
/* 2770 */     match(28);
/* 2771 */     if (this.inputState.guessing == 0)
/* 2772 */       this.behavior.endTree();
/*      */   }
/*      */ 
/*      */   public final void rootNode()
/*      */     throws RecognitionException, TokenStreamException
/*      */   {
/* 2778 */     Token localToken = null;
/*      */ 
/* 2781 */     if (((LA(1) == 24) || (LA(1) == 41)) && (LA(2) == 36)) {
/* 2782 */       localToken = id();
/* 2783 */       match(36);
/* 2784 */       if (this.inputState.guessing == 0) {
/* 2785 */         checkForMissingEndRule(localToken);
/*      */       }
/*      */     }
/* 2788 */     else if ((!_tokenSet_7.member(LA(1))) || (!_tokenSet_11.member(LA(2))))
/*      */     {
/* 2791 */       throw new NoViableAltException(LT(1), getFilename());
/*      */     }
/*      */ 
/* 2795 */     terminal(localToken);
/*      */   }
/*      */ 
/*      */   public final int ast_type_spec()
/*      */     throws RecognitionException, TokenStreamException
/*      */   {
/* 2801 */     int i = 1;
/*      */ 
/* 2804 */     switch (LA(1))
/*      */     {
/*      */     case 49:
/* 2807 */       match(49);
/* 2808 */       if (this.inputState.guessing == 0)
/* 2809 */         i = 2; break;
/*      */     case 33:
/* 2815 */       match(33);
/* 2816 */       if (this.inputState.guessing == 0)
/* 2817 */         i = 3; break;
/*      */     case 6:
/*      */     case 7:
/*      */     case 16:
/*      */     case 19:
/*      */     case 21:
/*      */     case 24:
/*      */     case 25:
/*      */     case 27:
/*      */     case 28:
/*      */     case 34:
/*      */     case 39:
/*      */     case 41:
/*      */     case 42:
/*      */     case 43:
/*      */     case 44:
/*      */     case 50:
/* 2838 */       break;
/*      */     case 8:
/*      */     case 9:
/*      */     case 10:
/*      */     case 11:
/*      */     case 12:
/*      */     case 13:
/*      */     case 14:
/*      */     case 15:
/*      */     case 17:
/*      */     case 18:
/*      */     case 20:
/*      */     case 22:
/*      */     case 23:
/*      */     case 26:
/*      */     case 29:
/*      */     case 30:
/*      */     case 31:
/*      */     case 32:
/*      */     case 35:
/*      */     case 36:
/*      */     case 37:
/*      */     case 38:
/*      */     case 40:
/*      */     case 45:
/*      */     case 46:
/*      */     case 47:
/*      */     case 48:
/*      */     default:
/* 2842 */       throw new NoViableAltException(LT(1), getFilename());
/*      */     }
/*      */ 
/* 2846 */     return i;
/*      */   }
/*      */ 
/*      */   private static final long[] mk_tokenSet_0()
/*      */   {
/* 2918 */     long[] arrayOfLong = { 2206556225792L, 0L };
/* 2919 */     return arrayOfLong;
/*      */   }
/*      */ 
/*      */   private static final long[] mk_tokenSet_1() {
/* 2923 */     long[] arrayOfLong = { 2472844214400L, 0L };
/* 2924 */     return arrayOfLong;
/*      */   }
/*      */ 
/*      */   private static final long[] mk_tokenSet_2() {
/* 2928 */     long[] arrayOfLong = { 1158885407195328L, 0L };
/* 2929 */     return arrayOfLong;
/*      */   }
/*      */ 
/*      */   private static final long[] mk_tokenSet_3() {
/* 2933 */     long[] arrayOfLong = { 1159461236965568L, 0L };
/* 2934 */     return arrayOfLong;
/*      */   }
/*      */ 
/*      */   private static final long[] mk_tokenSet_4() {
/* 2938 */     long[] arrayOfLong = { 1132497128128576L, 0L };
/* 2939 */     return arrayOfLong;
/*      */   }
/*      */ 
/*      */   private static final long[] mk_tokenSet_5() {
/* 2943 */     long[] arrayOfLong = { 1722479914074304L, 0L };
/* 2944 */     return arrayOfLong;
/*      */   }
/*      */ 
/*      */   private static final long[] mk_tokenSet_6() {
/* 2948 */     long[] arrayOfLong = { 1722411194597568L, 0L };
/* 2949 */     return arrayOfLong;
/*      */   }
/*      */ 
/*      */   private static final long[] mk_tokenSet_7() {
/* 2953 */     long[] arrayOfLong = { 1125899924144192L, 0L };
/* 2954 */     return arrayOfLong;
/*      */   }
/*      */ 
/*      */   private static final long[] mk_tokenSet_8() {
/* 2958 */     long[] arrayOfLong = { 1722411190386880L, 0L };
/* 2959 */     return arrayOfLong;
/*      */   }
/*      */ 
/*      */   private static final long[] mk_tokenSet_9() {
/* 2963 */     long[] arrayOfLong = { 1159444023476416L, 0L };
/* 2964 */     return arrayOfLong;
/*      */   }
/*      */ 
/*      */   private static final long[] mk_tokenSet_10() {
/* 2968 */     long[] arrayOfLong = { 2251345007067328L, 0L };
/* 2969 */     return arrayOfLong;
/*      */   }
/*      */ 
/*      */   private static final long[] mk_tokenSet_11() {
/* 2973 */     long[] arrayOfLong = { 1721861130420416L, 0L };
/* 2974 */     return arrayOfLong;
/*      */   }
/*      */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     antlr.ANTLRParser
 * JD-Core Version:    0.6.2
 */