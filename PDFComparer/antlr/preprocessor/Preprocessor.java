/*     */ package antlr.preprocessor;
/*     */ 
/*     */ import antlr.LLkParser;
/*     */ import antlr.NoViableAltException;
/*     */ import antlr.ParserSharedInputState;
/*     */ import antlr.RecognitionException;
/*     */ import antlr.SemanticException;
/*     */ import antlr.Token;
/*     */ import antlr.TokenBuffer;
/*     */ import antlr.TokenStream;
/*     */ import antlr.TokenStreamException;
/*     */ import antlr.Tool;
/*     */ import antlr.collections.impl.BitSet;
/*     */ import antlr.collections.impl.IndexedVector;
/*     */ 
/*     */ public class Preprocessor extends LLkParser
/*     */   implements PreprocessorTokenTypes
/*     */ {
/*     */   private Tool antlrTool;
/* 775 */   public static final String[] _tokenNames = { "<0>", "EOF", "<2>", "NULL_TREE_LOOKAHEAD", "\"tokens\"", "HEADER_ACTION", "SUBRULE_BLOCK", "ACTION", "\"class\"", "ID", "\"extends\"", "SEMI", "TOKENS_SPEC", "OPTIONS_START", "ASSIGN_RHS", "RCURLY", "\"protected\"", "\"private\"", "\"public\"", "BANG", "ARG_ACTION", "\"returns\"", "RULE_BLOCK", "\"throws\"", "COMMA", "\"exception\"", "\"catch\"", "ALT", "ELEMENT", "LPAREN", "RPAREN", "ID_OR_KEYWORD", "CURLY_BLOCK_SCARF", "WS", "NEWLINE", "COMMENT", "SL_COMMENT", "ML_COMMENT", "CHAR_LITERAL", "STRING_LITERAL", "ESC", "DIGIT", "XDIGIT" };
/*     */ 
/* 825 */   public static final BitSet _tokenSet_0 = new BitSet(mk_tokenSet_0());
/*     */ 
/* 830 */   public static final BitSet _tokenSet_1 = new BitSet(mk_tokenSet_1());
/*     */ 
/* 835 */   public static final BitSet _tokenSet_2 = new BitSet(mk_tokenSet_2());
/*     */ 
/* 840 */   public static final BitSet _tokenSet_3 = new BitSet(mk_tokenSet_3());
/*     */ 
/* 845 */   public static final BitSet _tokenSet_4 = new BitSet(mk_tokenSet_4());
/*     */ 
/* 850 */   public static final BitSet _tokenSet_5 = new BitSet(mk_tokenSet_5());
/*     */ 
/* 855 */   public static final BitSet _tokenSet_6 = new BitSet(mk_tokenSet_6());
/*     */ 
/* 860 */   public static final BitSet _tokenSet_7 = new BitSet(mk_tokenSet_7());
/*     */ 
/* 865 */   public static final BitSet _tokenSet_8 = new BitSet(mk_tokenSet_8());
/*     */ 
/*     */   public void setTool(Tool paramTool)
/*     */   {
/*  37 */     if (this.antlrTool == null) {
/*  38 */       this.antlrTool = paramTool;
/*     */     }
/*     */     else
/*  41 */       throw new IllegalStateException("antlr.Tool already registered");
/*     */   }
/*     */ 
/*     */   protected Tool getTool()
/*     */   {
/*  47 */     return this.antlrTool;
/*     */   }
/*     */ 
/*     */   public void reportError(String paramString)
/*     */   {
/*  55 */     if (getTool() != null) {
/*  56 */       getTool().error(paramString, getFilename(), -1, -1);
/*     */     }
/*     */     else
/*  59 */       super.reportError(paramString);
/*     */   }
/*     */ 
/*     */   public void reportError(RecognitionException paramRecognitionException)
/*     */   {
/*  68 */     if (getTool() != null) {
/*  69 */       getTool().error(paramRecognitionException.getErrorMessage(), paramRecognitionException.getFilename(), paramRecognitionException.getLine(), paramRecognitionException.getColumn());
/*     */     }
/*     */     else
/*  72 */       super.reportError(paramRecognitionException);
/*     */   }
/*     */ 
/*     */   public void reportWarning(String paramString)
/*     */   {
/*  81 */     if (getTool() != null) {
/*  82 */       getTool().warning(paramString, getFilename(), -1, -1);
/*     */     }
/*     */     else
/*  85 */       super.reportWarning(paramString);
/*     */   }
/*     */ 
/*     */   protected Preprocessor(TokenBuffer paramTokenBuffer, int paramInt)
/*     */   {
/*  90 */     super(paramTokenBuffer, paramInt);
/*  91 */     this.tokenNames = _tokenNames;
/*     */   }
/*     */ 
/*     */   public Preprocessor(TokenBuffer paramTokenBuffer) {
/*  95 */     this(paramTokenBuffer, 1);
/*     */   }
/*     */ 
/*     */   protected Preprocessor(TokenStream paramTokenStream, int paramInt) {
/*  99 */     super(paramTokenStream, paramInt);
/* 100 */     this.tokenNames = _tokenNames;
/*     */   }
/*     */ 
/*     */   public Preprocessor(TokenStream paramTokenStream) {
/* 104 */     this(paramTokenStream, 1);
/*     */   }
/*     */ 
/*     */   public Preprocessor(ParserSharedInputState paramParserSharedInputState) {
/* 108 */     super(paramParserSharedInputState, 1);
/* 109 */     this.tokenNames = _tokenNames;
/*     */   }
/*     */ 
/*     */   public final void grammarFile(Hierarchy paramHierarchy, String paramString)
/*     */     throws RecognitionException, TokenStreamException
/*     */   {
/* 116 */     Token localToken = null;
/*     */ 
/* 119 */     IndexedVector localIndexedVector = null;
/*     */     try
/*     */     {
/* 126 */       while (LA(1) == 5) {
/* 127 */         localToken = LT(1);
/* 128 */         match(5);
/* 129 */         paramHierarchy.getFile(paramString).addHeaderAction(localToken.getText());
/*     */       }
/*     */ 
/* 138 */       switch (LA(1))
/*     */       {
/*     */       case 13:
/* 141 */         localIndexedVector = optionSpec(null);
/* 142 */         break;
/*     */       case 1:
/*     */       case 7:
/*     */       case 8:
/* 148 */         break;
/*     */       default:
/* 152 */         throw new NoViableAltException(LT(1), getFilename());
/*     */       }
/*     */ 
/* 159 */       while ((LA(1) == 7) || (LA(1) == 8)) {
/* 160 */         Grammar localGrammar = class_def(paramString, paramHierarchy);
/*     */ 
/* 162 */         if ((localGrammar != null) && (localIndexedVector != null)) {
/* 163 */           paramHierarchy.getFile(paramString).setOptions(localIndexedVector);
/*     */         }
/* 165 */         if (localGrammar != null) {
/* 166 */           localGrammar.setFileName(paramString);
/* 167 */           paramHierarchy.addGrammar(localGrammar);
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/* 177 */       match(1);
/*     */     }
/*     */     catch (RecognitionException localRecognitionException) {
/* 180 */       reportError(localRecognitionException);
/* 181 */       consume();
/* 182 */       consumeUntil(_tokenSet_0);
/*     */     }
/*     */   }
/*     */ 
/*     */   public final IndexedVector optionSpec(Grammar paramGrammar)
/*     */     throws RecognitionException, TokenStreamException
/*     */   {
/* 191 */     Token localToken1 = null;
/* 192 */     Token localToken2 = null;
/*     */ 
/* 194 */     IndexedVector localIndexedVector = new IndexedVector();
/*     */     try
/*     */     {
/* 198 */       match(13);
/*     */ 
/* 202 */       while (LA(1) == 9) {
/* 203 */         localToken1 = LT(1);
/* 204 */         match(9);
/* 205 */         localToken2 = LT(1);
/* 206 */         match(14);
/*     */ 
/* 208 */         Option localOption = new Option(localToken1.getText(), localToken2.getText(), paramGrammar);
/* 209 */         localIndexedVector.appendElement(localOption.getName(), localOption);
/* 210 */         if ((paramGrammar != null) && (localToken1.getText().equals("importVocab"))) {
/* 211 */           paramGrammar.specifiedVocabulary = true;
/* 212 */           paramGrammar.importVocab = localToken2.getText();
/*     */         }
/* 214 */         else if ((paramGrammar != null) && (localToken1.getText().equals("exportVocab")))
/*     */         {
/* 217 */           paramGrammar.exportVocab = localToken2.getText().substring(0, localToken2.getText().length() - 1);
/* 218 */           paramGrammar.exportVocab = paramGrammar.exportVocab.trim();
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/* 228 */       match(15);
/*     */     }
/*     */     catch (RecognitionException localRecognitionException) {
/* 231 */       reportError(localRecognitionException);
/* 232 */       consume();
/* 233 */       consumeUntil(_tokenSet_1);
/*     */     }
/* 235 */     return localIndexedVector;
/*     */   }
/*     */ 
/*     */   public final Grammar class_def(String paramString, Hierarchy paramHierarchy)
/*     */     throws RecognitionException, TokenStreamException
/*     */   {
/* 243 */     Token localToken1 = null;
/* 244 */     Token localToken2 = null;
/* 245 */     Token localToken3 = null;
/* 246 */     Token localToken4 = null;
/* 247 */     Token localToken5 = null;
/*     */ 
/* 249 */     Grammar localGrammar = null;
/* 250 */     IndexedVector localIndexedVector1 = new IndexedVector(100);
/* 251 */     IndexedVector localIndexedVector2 = null;
/* 252 */     String str = null;
/*     */     try
/*     */     {
/* 257 */       switch (LA(1))
/*     */       {
/*     */       case 7:
/* 260 */         localToken1 = LT(1);
/* 261 */         match(7);
/* 262 */         break;
/*     */       case 8:
/* 266 */         break;
/*     */       default:
/* 270 */         throw new NoViableAltException(LT(1), getFilename());
/*     */       }
/*     */ 
/* 274 */       match(8);
/* 275 */       localToken2 = LT(1);
/* 276 */       match(9);
/* 277 */       match(10);
/* 278 */       localToken3 = LT(1);
/* 279 */       match(9);
/*     */ 
/* 281 */       switch (LA(1))
/*     */       {
/*     */       case 6:
/* 284 */         str = superClass();
/* 285 */         break;
/*     */       case 11:
/* 289 */         break;
/*     */       default:
/* 293 */         throw new NoViableAltException(LT(1), getFilename());
/*     */       }
/*     */ 
/* 297 */       match(11);
/*     */ 
/* 299 */       localGrammar = paramHierarchy.getGrammar(localToken2.getText());
/* 300 */       if (localGrammar != null)
/*     */       {
/* 302 */         localGrammar = null;
/* 303 */         throw new SemanticException("redefinition of grammar " + localToken2.getText(), paramString, localToken2.getLine(), localToken2.getColumn());
/*     */       }
/*     */ 
/* 306 */       localGrammar = new Grammar(paramHierarchy.getTool(), localToken2.getText(), localToken3.getText(), localIndexedVector1);
/* 307 */       localGrammar.superClass = str;
/* 308 */       if (localToken1 != null) {
/* 309 */         localGrammar.setPreambleAction(localToken1.getText());
/*     */       }
/*     */ 
/* 314 */       switch (LA(1))
/*     */       {
/*     */       case 13:
/* 317 */         localIndexedVector2 = optionSpec(localGrammar);
/* 318 */         break;
/*     */       case 7:
/*     */       case 9:
/*     */       case 12:
/*     */       case 16:
/*     */       case 17:
/*     */       case 18:
/* 327 */         break;
/*     */       case 8:
/*     */       case 10:
/*     */       case 11:
/*     */       case 14:
/*     */       case 15:
/*     */       default:
/* 331 */         throw new NoViableAltException(LT(1), getFilename());
/*     */       }
/*     */ 
/* 336 */       if (localGrammar != null) {
/* 337 */         localGrammar.setOptions(localIndexedVector2);
/*     */       }
/*     */ 
/* 341 */       switch (LA(1))
/*     */       {
/*     */       case 12:
/* 344 */         localToken4 = LT(1);
/* 345 */         match(12);
/* 346 */         localGrammar.setTokenSection(localToken4.getText());
/* 347 */         break;
/*     */       case 7:
/*     */       case 9:
/*     */       case 16:
/*     */       case 17:
/*     */       case 18:
/* 355 */         break;
/*     */       case 8:
/*     */       case 10:
/*     */       case 11:
/*     */       case 13:
/*     */       case 14:
/*     */       case 15:
/*     */       default:
/* 359 */         throw new NoViableAltException(LT(1), getFilename());
/*     */       }
/*     */ 
/* 364 */       switch (LA(1))
/*     */       {
/*     */       case 7:
/* 367 */         localToken5 = LT(1);
/* 368 */         match(7);
/* 369 */         localGrammar.setMemberAction(localToken5.getText());
/* 370 */         break;
/*     */       case 9:
/*     */       case 16:
/*     */       case 17:
/*     */       case 18:
/* 377 */         break;
/*     */       case 8:
/*     */       case 10:
/*     */       case 11:
/*     */       case 12:
/*     */       case 13:
/*     */       case 14:
/*     */       case 15:
/*     */       default:
/* 381 */         throw new NoViableAltException(LT(1), getFilename());
/*     */       }
/*     */ 
/* 386 */       int i = 0;
/*     */       while (true)
/*     */       {
/* 389 */         if (_tokenSet_2.member(LA(1))) {
/* 390 */           rule(localGrammar);
/*     */         }
/*     */         else {
/* 393 */           if (i >= 1) break; throw new NoViableAltException(LT(1), getFilename());
/*     */         }
/*     */ 
/* 396 */         i++;
/*     */       }
/*     */     }
/*     */     catch (RecognitionException localRecognitionException)
/*     */     {
/* 401 */       reportError(localRecognitionException);
/* 402 */       consume();
/* 403 */       consumeUntil(_tokenSet_3);
/*     */     }
/* 405 */     return localGrammar;
/*     */   }
/*     */ 
/*     */   public final String superClass()
/*     */     throws RecognitionException, TokenStreamException
/*     */   {
/* 411 */     String str = LT(1).getText();
/*     */     try
/*     */     {
/* 414 */       match(6);
/*     */     }
/*     */     catch (RecognitionException localRecognitionException) {
/* 417 */       reportError(localRecognitionException);
/* 418 */       consume();
/* 419 */       consumeUntil(_tokenSet_4);
/*     */     }
/* 421 */     return str;
/*     */   }
/*     */ 
/*     */   public final void rule(Grammar paramGrammar)
/*     */     throws RecognitionException, TokenStreamException
/*     */   {
/* 428 */     Token localToken1 = null;
/* 429 */     Token localToken2 = null;
/* 430 */     Token localToken3 = null;
/* 431 */     Token localToken4 = null;
/* 432 */     Token localToken5 = null;
/*     */ 
/* 434 */     IndexedVector localIndexedVector = null;
/* 435 */     String str1 = null;
/* 436 */     int i = 0;
/* 437 */     String str2 = null; String str3 = "";
/*     */     try
/*     */     {
/* 442 */       switch (LA(1))
/*     */       {
/*     */       case 16:
/* 445 */         match(16);
/* 446 */         str1 = "protected";
/* 447 */         break;
/*     */       case 17:
/* 451 */         match(17);
/* 452 */         str1 = "private";
/* 453 */         break;
/*     */       case 18:
/* 457 */         match(18);
/* 458 */         str1 = "public";
/* 459 */         break;
/*     */       case 9:
/* 463 */         break;
/*     */       case 10:
/*     */       case 11:
/*     */       case 12:
/*     */       case 13:
/*     */       case 14:
/*     */       case 15:
/*     */       default:
/* 467 */         throw new NoViableAltException(LT(1), getFilename());
/*     */       }
/*     */ 
/* 471 */       localToken1 = LT(1);
/* 472 */       match(9);
/*     */ 
/* 474 */       switch (LA(1))
/*     */       {
/*     */       case 19:
/* 477 */         match(19);
/* 478 */         i = 1;
/* 479 */         break;
/*     */       case 7:
/*     */       case 13:
/*     */       case 20:
/*     */       case 21:
/*     */       case 22:
/*     */       case 23:
/* 488 */         break;
/*     */       case 8:
/*     */       case 9:
/*     */       case 10:
/*     */       case 11:
/*     */       case 12:
/*     */       case 14:
/*     */       case 15:
/*     */       case 16:
/*     */       case 17:
/*     */       case 18:
/*     */       default:
/* 492 */         throw new NoViableAltException(LT(1), getFilename());
/*     */       }
/*     */ 
/* 497 */       switch (LA(1))
/*     */       {
/*     */       case 20:
/* 500 */         localToken2 = LT(1);
/* 501 */         match(20);
/* 502 */         break;
/*     */       case 7:
/*     */       case 13:
/*     */       case 21:
/*     */       case 22:
/*     */       case 23:
/* 510 */         break;
/*     */       case 8:
/*     */       case 9:
/*     */       case 10:
/*     */       case 11:
/*     */       case 12:
/*     */       case 14:
/*     */       case 15:
/*     */       case 16:
/*     */       case 17:
/*     */       case 18:
/*     */       case 19:
/*     */       default:
/* 514 */         throw new NoViableAltException(LT(1), getFilename());
/*     */       }
/*     */ 
/* 519 */       switch (LA(1))
/*     */       {
/*     */       case 21:
/* 522 */         match(21);
/* 523 */         localToken3 = LT(1);
/* 524 */         match(20);
/* 525 */         break;
/*     */       case 7:
/*     */       case 13:
/*     */       case 22:
/*     */       case 23:
/* 532 */         break;
/*     */       default:
/* 536 */         throw new NoViableAltException(LT(1), getFilename());
/*     */       }
/*     */ 
/* 541 */       switch (LA(1))
/*     */       {
/*     */       case 23:
/* 544 */         str3 = throwsSpec();
/* 545 */         break;
/*     */       case 7:
/*     */       case 13:
/*     */       case 22:
/* 551 */         break;
/*     */       default:
/* 555 */         throw new NoViableAltException(LT(1), getFilename());
/*     */       }
/*     */ 
/* 560 */       switch (LA(1))
/*     */       {
/*     */       case 13:
/* 563 */         localIndexedVector = optionSpec(null);
/* 564 */         break;
/*     */       case 7:
/*     */       case 22:
/* 569 */         break;
/*     */       default:
/* 573 */         throw new NoViableAltException(LT(1), getFilename());
/*     */       }
/*     */ 
/* 578 */       switch (LA(1))
/*     */       {
/*     */       case 7:
/* 581 */         localToken4 = LT(1);
/* 582 */         match(7);
/* 583 */         break;
/*     */       case 22:
/* 587 */         break;
/*     */       default:
/* 591 */         throw new NoViableAltException(LT(1), getFilename());
/*     */       }
/*     */ 
/* 595 */       localToken5 = LT(1);
/* 596 */       match(22);
/* 597 */       str2 = exceptionGroup();
/*     */ 
/* 599 */       String str4 = localToken5.getText() + str2;
/* 600 */       Rule localRule = new Rule(localToken1.getText(), str4, localIndexedVector, paramGrammar);
/* 601 */       localRule.setThrowsSpec(str3);
/* 602 */       if (localToken2 != null) {
/* 603 */         localRule.setArgs(localToken2.getText());
/*     */       }
/* 605 */       if (localToken3 != null) {
/* 606 */         localRule.setReturnValue(localToken3.getText());
/*     */       }
/* 608 */       if (localToken4 != null) {
/* 609 */         localRule.setInitAction(localToken4.getText());
/*     */       }
/* 611 */       if (i != 0) {
/* 612 */         localRule.setBang();
/*     */       }
/* 614 */       localRule.setVisibility(str1);
/* 615 */       if (paramGrammar != null) {
/* 616 */         paramGrammar.addRule(localRule);
/*     */       }
/*     */     }
/*     */     catch (RecognitionException localRecognitionException)
/*     */     {
/* 621 */       reportError(localRecognitionException);
/* 622 */       consume();
/* 623 */       consumeUntil(_tokenSet_5);
/*     */     }
/*     */   }
/*     */ 
/*     */   public final String throwsSpec()
/*     */     throws RecognitionException, TokenStreamException
/*     */   {
/* 630 */     Token localToken1 = null;
/* 631 */     Token localToken2 = null;
/* 632 */     String str = "throws ";
/*     */     try
/*     */     {
/* 635 */       match(23);
/* 636 */       localToken1 = LT(1);
/* 637 */       match(9);
/* 638 */       str = str + localToken1.getText();
/*     */ 
/* 642 */       while (LA(1) == 24) {
/* 643 */         match(24);
/* 644 */         localToken2 = LT(1);
/* 645 */         match(9);
/* 646 */         str = str + "," + localToken2.getText();
/*     */       }
/*     */ 
/*     */     }
/*     */     catch (RecognitionException localRecognitionException)
/*     */     {
/* 656 */       reportError(localRecognitionException);
/* 657 */       consume();
/* 658 */       consumeUntil(_tokenSet_6);
/*     */     }
/* 660 */     return str;
/*     */   }
/*     */ 
/*     */   public final String exceptionGroup()
/*     */     throws RecognitionException, TokenStreamException
/*     */   {
/* 666 */     String str2 = null; String str1 = "";
/*     */     try
/*     */     {
/* 672 */       while (LA(1) == 25) {
/* 673 */         str2 = exceptionSpec();
/* 674 */         str1 = str1 + str2;
/*     */       }
/*     */ 
/*     */     }
/*     */     catch (RecognitionException localRecognitionException)
/*     */     {
/* 684 */       reportError(localRecognitionException);
/* 685 */       consume();
/* 686 */       consumeUntil(_tokenSet_5);
/*     */     }
/* 688 */     return str1;
/*     */   }
/*     */ 
/*     */   public final String exceptionSpec()
/*     */     throws RecognitionException, TokenStreamException
/*     */   {
/* 694 */     Token localToken = null;
/* 695 */     String str2 = null;
/* 696 */     String str1 = System.getProperty("line.separator") + "exception ";
/*     */     try
/*     */     {
/* 700 */       match(25);
/*     */ 
/* 702 */       switch (LA(1))
/*     */       {
/*     */       case 20:
/* 705 */         localToken = LT(1);
/* 706 */         match(20);
/* 707 */         str1 = str1 + localToken.getText();
/* 708 */         break;
/*     */       case 1:
/*     */       case 7:
/*     */       case 8:
/*     */       case 9:
/*     */       case 16:
/*     */       case 17:
/*     */       case 18:
/*     */       case 25:
/*     */       case 26:
/* 720 */         break;
/*     */       case 2:
/*     */       case 3:
/*     */       case 4:
/*     */       case 5:
/*     */       case 6:
/*     */       case 10:
/*     */       case 11:
/*     */       case 12:
/*     */       case 13:
/*     */       case 14:
/*     */       case 15:
/*     */       case 19:
/*     */       case 21:
/*     */       case 22:
/*     */       case 23:
/*     */       case 24:
/*     */       default:
/* 724 */         throw new NoViableAltException(LT(1), getFilename());
/*     */       }
/*     */ 
/* 731 */       while (LA(1) == 26) {
/* 732 */         str2 = exceptionHandler();
/* 733 */         str1 = str1 + str2;
/*     */       }
/*     */ 
/*     */     }
/*     */     catch (RecognitionException localRecognitionException)
/*     */     {
/* 743 */       reportError(localRecognitionException);
/* 744 */       consume();
/* 745 */       consumeUntil(_tokenSet_7);
/*     */     }
/* 747 */     return str1;
/*     */   }
/*     */ 
/*     */   public final String exceptionHandler()
/*     */     throws RecognitionException, TokenStreamException
/*     */   {
/* 753 */     Token localToken1 = null;
/* 754 */     Token localToken2 = null;
/* 755 */     String str = null;
/*     */     try
/*     */     {
/* 758 */       match(26);
/* 759 */       localToken1 = LT(1);
/* 760 */       match(20);
/* 761 */       localToken2 = LT(1);
/* 762 */       match(7);
/* 763 */       str = System.getProperty("line.separator") + "catch " + localToken1.getText() + " " + localToken2.getText();
/*     */     }
/*     */     catch (RecognitionException localRecognitionException)
/*     */     {
/* 767 */       reportError(localRecognitionException);
/* 768 */       consume();
/* 769 */       consumeUntil(_tokenSet_8);
/*     */     }
/* 771 */     return str;
/*     */   }
/*     */ 
/*     */   private static final long[] mk_tokenSet_0()
/*     */   {
/* 822 */     long[] arrayOfLong = { 2L, 0L };
/* 823 */     return arrayOfLong;
/*     */   }
/*     */ 
/*     */   private static final long[] mk_tokenSet_1() {
/* 827 */     long[] arrayOfLong = { 4658050L, 0L };
/* 828 */     return arrayOfLong;
/*     */   }
/*     */ 
/*     */   private static final long[] mk_tokenSet_2() {
/* 832 */     long[] arrayOfLong = { 459264L, 0L };
/* 833 */     return arrayOfLong;
/*     */   }
/*     */ 
/*     */   private static final long[] mk_tokenSet_3() {
/* 837 */     long[] arrayOfLong = { 386L, 0L };
/* 838 */     return arrayOfLong;
/*     */   }
/*     */ 
/*     */   private static final long[] mk_tokenSet_4() {
/* 842 */     long[] arrayOfLong = { 2048L, 0L };
/* 843 */     return arrayOfLong;
/*     */   }
/*     */ 
/*     */   private static final long[] mk_tokenSet_5() {
/* 847 */     long[] arrayOfLong = { 459650L, 0L };
/* 848 */     return arrayOfLong;
/*     */   }
/*     */ 
/*     */   private static final long[] mk_tokenSet_6() {
/* 852 */     long[] arrayOfLong = { 4202624L, 0L };
/* 853 */     return arrayOfLong;
/*     */   }
/*     */ 
/*     */   private static final long[] mk_tokenSet_7() {
/* 857 */     long[] arrayOfLong = { 34014082L, 0L };
/* 858 */     return arrayOfLong;
/*     */   }
/*     */ 
/*     */   private static final long[] mk_tokenSet_8() {
/* 862 */     long[] arrayOfLong = { 101122946L, 0L };
/* 863 */     return arrayOfLong;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     antlr.preprocessor.Preprocessor
 * JD-Core Version:    0.6.2
 */