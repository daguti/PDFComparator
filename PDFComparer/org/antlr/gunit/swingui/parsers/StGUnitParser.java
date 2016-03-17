/*     */ package org.antlr.gunit.swingui.parsers;
/*     */ 
/*     */ import org.antlr.gunit.swingui.model.ITestCaseInput;
/*     */ import org.antlr.gunit.swingui.model.ITestCaseOutput;
/*     */ import org.antlr.gunit.swingui.runner.TestSuiteAdapter;
/*     */ import org.antlr.runtime.BitSet;
/*     */ import org.antlr.runtime.EarlyExitException;
/*     */ import org.antlr.runtime.MismatchedSetException;
/*     */ import org.antlr.runtime.NoViableAltException;
/*     */ import org.antlr.runtime.Parser;
/*     */ import org.antlr.runtime.ParserRuleReturnScope;
/*     */ import org.antlr.runtime.RecognitionException;
/*     */ import org.antlr.runtime.RecognizerSharedState;
/*     */ import org.antlr.runtime.Token;
/*     */ import org.antlr.runtime.TokenStream;
/*     */ 
/*     */ public class StGUnitParser extends Parser
/*     */ {
/*  14 */   public static final String[] tokenNames = { "<invalid>", "<EOR>", "<DOWN>", "<UP>", "OK", "FAIL", "DOC_COMMENT", "ACTION", "RULE_REF", "TOKEN_REF", "RETVAL", "AST", "STRING", "ML_STRING", "EXT", "SL_COMMENT", "ML_COMMENT", "ESC", "NESTED_RETVAL", "NESTED_AST", "NESTED_ACTION", "STRING_LITERAL", "CHAR_LITERAL", "XDIGIT", "WS", "'gunit'", "'walks'", "';'", "'@header'", "':'", "'returns'", "'->'" };
/*     */   public static final int EOF = -1;
/*     */   public static final int T__25 = 25;
/*     */   public static final int T__26 = 26;
/*     */   public static final int T__27 = 27;
/*     */   public static final int T__28 = 28;
/*     */   public static final int T__29 = 29;
/*     */   public static final int T__30 = 30;
/*     */   public static final int T__31 = 31;
/*     */   public static final int OK = 4;
/*     */   public static final int FAIL = 5;
/*     */   public static final int DOC_COMMENT = 6;
/*     */   public static final int ACTION = 7;
/*     */   public static final int RULE_REF = 8;
/*     */   public static final int TOKEN_REF = 9;
/*     */   public static final int RETVAL = 10;
/*     */   public static final int AST = 11;
/*     */   public static final int STRING = 12;
/*     */   public static final int ML_STRING = 13;
/*     */   public static final int EXT = 14;
/*     */   public static final int SL_COMMENT = 15;
/*     */   public static final int ML_COMMENT = 16;
/*     */   public static final int ESC = 17;
/*     */   public static final int NESTED_RETVAL = 18;
/*     */   public static final int NESTED_AST = 19;
/*     */   public static final int NESTED_ACTION = 20;
/*     */   public static final int STRING_LITERAL = 21;
/*     */   public static final int CHAR_LITERAL = 22;
/*     */   public static final int XDIGIT = 23;
/*     */   public static final int WS = 24;
/*     */   public TestSuiteAdapter adapter;
/* 744 */   public static final BitSet FOLLOW_25_in_gUnitDef68 = new BitSet(new long[] { 768L });
/* 745 */   public static final BitSet FOLLOW_id_in_gUnitDef72 = new BitSet(new long[] { 201326592L });
/* 746 */   public static final BitSet FOLLOW_26_in_gUnitDef82 = new BitSet(new long[] { 768L });
/* 747 */   public static final BitSet FOLLOW_id_in_gUnitDef84 = new BitSet(new long[] { 134217728L });
/* 748 */   public static final BitSet FOLLOW_27_in_gUnitDef88 = new BitSet(new long[] { 268436226L });
/* 749 */   public static final BitSet FOLLOW_header_in_gUnitDef93 = new BitSet(new long[] { 770L });
/* 750 */   public static final BitSet FOLLOW_suite_in_gUnitDef96 = new BitSet(new long[] { 770L });
/* 751 */   public static final BitSet FOLLOW_28_in_header108 = new BitSet(new long[] { 128L });
/* 752 */   public static final BitSet FOLLOW_ACTION_in_header110 = new BitSet(new long[] { 2L });
/* 753 */   public static final BitSet FOLLOW_RULE_REF_in_suite127 = new BitSet(new long[] { 603979776L });
/* 754 */   public static final BitSet FOLLOW_26_in_suite130 = new BitSet(new long[] { 256L });
/* 755 */   public static final BitSet FOLLOW_RULE_REF_in_suite132 = new BitSet(new long[] { 536870912L });
/* 756 */   public static final BitSet FOLLOW_TOKEN_REF_in_suite154 = new BitSet(new long[] { 536870912L });
/* 757 */   public static final BitSet FOLLOW_29_in_suite168 = new BitSet(new long[] { 13056L });
/* 758 */   public static final BitSet FOLLOW_test_in_suite172 = new BitSet(new long[] { 13058L });
/* 759 */   public static final BitSet FOLLOW_input_in_test188 = new BitSet(new long[] { 3221225520L });
/* 760 */   public static final BitSet FOLLOW_expect_in_test190 = new BitSet(new long[] { 2L });
/* 761 */   public static final BitSet FOLLOW_OK_in_expect210 = new BitSet(new long[] { 2L });
/* 762 */   public static final BitSet FOLLOW_FAIL_in_expect219 = new BitSet(new long[] { 2L });
/* 763 */   public static final BitSet FOLLOW_30_in_expect227 = new BitSet(new long[] { 1024L });
/* 764 */   public static final BitSet FOLLOW_RETVAL_in_expect229 = new BitSet(new long[] { 2L });
/* 765 */   public static final BitSet FOLLOW_31_in_expect236 = new BitSet(new long[] { 12416L });
/* 766 */   public static final BitSet FOLLOW_output_in_expect238 = new BitSet(new long[] { 2L });
/* 767 */   public static final BitSet FOLLOW_31_in_expect245 = new BitSet(new long[] { 2048L });
/* 768 */   public static final BitSet FOLLOW_AST_in_expect247 = new BitSet(new long[] { 2L });
/* 769 */   public static final BitSet FOLLOW_STRING_in_input264 = new BitSet(new long[] { 2L });
/* 770 */   public static final BitSet FOLLOW_ML_STRING_in_input273 = new BitSet(new long[] { 2L });
/* 771 */   public static final BitSet FOLLOW_fileInput_in_input280 = new BitSet(new long[] { 2L });
/* 772 */   public static final BitSet FOLLOW_set_in_output0 = new BitSet(new long[] { 2L });
/* 773 */   public static final BitSet FOLLOW_id_in_fileInput319 = new BitSet(new long[] { 16386L });
/* 774 */   public static final BitSet FOLLOW_EXT_in_fileInput324 = new BitSet(new long[] { 2L });
/* 775 */   public static final BitSet FOLLOW_set_in_id0 = new BitSet(new long[] { 2L });
/*     */ 
/*     */   public StGUnitParser(TokenStream input)
/*     */   {
/*  52 */     this(input, new RecognizerSharedState());
/*     */   }
/*     */   public StGUnitParser(TokenStream input, RecognizerSharedState state) {
/*  55 */     super(input, state);
/*     */   }
/*     */ 
/*     */   public String[] getTokenNames()
/*     */   {
/*  60 */     return tokenNames; } 
/*  61 */   public String getGrammarFileName() { return "org/antlr/gunit/swingui/parsers/StGUnit.g"; }
/*     */ 
/*     */ 
/*     */   public final void gUnitDef()
/*     */     throws RecognitionException
/*     */   {
/*  71 */     id_return name = null;
/*     */     try
/*     */     {
/*  78 */       match(this.input, 25, FOLLOW_25_in_gUnitDef68);
/*  79 */       pushFollow(FOLLOW_id_in_gUnitDef72);
/*  80 */       name = id();
/*     */ 
/*  82 */       this.state._fsp -= 1;
/*     */ 
/*  84 */       this.adapter.setGrammarName(name != null ? this.input.toString(name.start, name.stop) : null);
/*     */ 
/*  86 */       int alt1 = 2;
/*  87 */       switch (this.input.LA(1))
/*     */       {
/*     */       case 26:
/*  90 */         alt1 = 1;
/*     */       }
/*     */ 
/*  95 */       switch (alt1)
/*     */       {
/*     */       case 1:
/*  99 */         match(this.input, 26, FOLLOW_26_in_gUnitDef82);
/* 100 */         pushFollow(FOLLOW_id_in_gUnitDef84);
/* 101 */         id();
/*     */ 
/* 103 */         this.state._fsp -= 1;
/*     */       }
/*     */ 
/* 111 */       match(this.input, 27, FOLLOW_27_in_gUnitDef88);
/*     */ 
/* 113 */       int alt2 = 2;
/* 114 */       switch (this.input.LA(1))
/*     */       {
/*     */       case 28:
/* 117 */         alt2 = 1;
/*     */       }
/*     */ 
/* 122 */       switch (alt2)
/*     */       {
/*     */       case 1:
/* 126 */         pushFollow(FOLLOW_header_in_gUnitDef93);
/* 127 */         header();
/*     */ 
/* 129 */         this.state._fsp -= 1;
/*     */       }
/*     */ 
/*     */       while (true)
/*     */       {
/* 140 */         int alt3 = 2;
/* 141 */         switch (this.input.LA(1))
/*     */         {
/*     */         case 8:
/*     */         case 9:
/* 145 */           alt3 = 1;
/*     */         }
/*     */ 
/* 151 */         switch (alt3)
/*     */         {
/*     */         case 1:
/* 155 */           pushFollow(FOLLOW_suite_in_gUnitDef96);
/* 156 */           suite();
/*     */ 
/* 158 */           this.state._fsp -= 1;
/*     */ 
/* 162 */           break;
/*     */         default:
/* 165 */           return;
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/*     */     }
/*     */     catch (RecognitionException re)
/*     */     {
/* 173 */       re = 
/* 178 */         re;
/*     */ 
/* 174 */       reportError(re);
/* 175 */       recover(this.input, re);
/*     */     }
/*     */     finally
/*     */     {
/*     */     }
/*     */   }
/*     */ 
/*     */   public final void header()
/*     */     throws RecognitionException
/*     */   {
/*     */     try
/*     */     {
/* 191 */       match(this.input, 28, FOLLOW_28_in_header108);
/* 192 */       match(this.input, 7, FOLLOW_ACTION_in_header110);
/*     */     }
/*     */     catch (RecognitionException re)
/*     */     {
/* 197 */       re = 
/* 202 */         re;
/*     */ 
/* 198 */       reportError(re);
/* 199 */       recover(this.input, re);
/*     */     }
/*     */     finally
/*     */     {
/*     */     }
/*     */   }
/*     */ 
/*     */   public final void suite()
/*     */     throws RecognitionException
/*     */   {
/* 211 */     Token parserRule = null;
/* 212 */     Token lexerRule = null;
/*     */     try
/*     */     {
/* 219 */       int alt5 = 2;
/* 220 */       switch (this.input.LA(1))
/*     */       {
/*     */       case 8:
/* 223 */         alt5 = 1;
/*     */ 
/* 225 */         break;
/*     */       case 9:
/* 228 */         alt5 = 2;
/*     */ 
/* 230 */         break;
/*     */       default:
/* 232 */         NoViableAltException nvae = new NoViableAltException("", 5, 0, this.input);
/*     */ 
/* 235 */         throw nvae;
/*     */       }
/*     */ 
/* 238 */       switch (alt5)
/*     */       {
/*     */       case 1:
/* 242 */         parserRule = (Token)match(this.input, 8, FOLLOW_RULE_REF_in_suite127);
/*     */ 
/* 244 */         int alt4 = 2;
/* 245 */         switch (this.input.LA(1))
/*     */         {
/*     */         case 26:
/* 248 */           alt4 = 1;
/*     */         }
/*     */ 
/* 253 */         switch (alt4)
/*     */         {
/*     */         case 1:
/* 257 */           match(this.input, 26, FOLLOW_26_in_suite130);
/* 258 */           match(this.input, 8, FOLLOW_RULE_REF_in_suite132);
/*     */         }
/*     */ 
/* 265 */         this.adapter.startRule(parserRule != null ? parserRule.getText() : null);
/*     */ 
/* 268 */         break;
/*     */       case 2:
/* 272 */         lexerRule = (Token)match(this.input, 9, FOLLOW_TOKEN_REF_in_suite154);
/* 273 */         this.adapter.startRule(lexerRule != null ? lexerRule.getText() : null);
/*     */       }
/*     */ 
/* 280 */       match(this.input, 29, FOLLOW_29_in_suite168);
/*     */ 
/* 282 */       int cnt6 = 0;
/*     */       while (true)
/*     */       {
/* 285 */         int alt6 = 2;
/* 286 */         switch (this.input.LA(1))
/*     */         {
/*     */         case 8:
/* 289 */           switch (this.input.LA(2))
/*     */           {
/*     */           case 4:
/*     */           case 5:
/*     */           case 14:
/*     */           case 30:
/*     */           case 31:
/* 296 */             alt6 = 1;
/*     */           }
/*     */ 
/* 303 */           break;
/*     */         case 9:
/* 306 */           switch (this.input.LA(2))
/*     */           {
/*     */           case 4:
/*     */           case 5:
/*     */           case 14:
/*     */           case 30:
/*     */           case 31:
/* 313 */             alt6 = 1;
/*     */           }
/*     */ 
/* 320 */           break;
/*     */         case 12:
/*     */         case 13:
/* 324 */           alt6 = 1;
/*     */         case 10:
/*     */         case 11:
/*     */         }
/*     */ 
/* 330 */         switch (alt6)
/*     */         {
/*     */         case 1:
/* 334 */           pushFollow(FOLLOW_test_in_suite172);
/* 335 */           test();
/*     */ 
/* 337 */           this.state._fsp -= 1;
/*     */ 
/* 341 */           break;
/*     */         default:
/* 344 */           if (cnt6 >= 1) break label548;
/* 345 */           EarlyExitException eee = new EarlyExitException(6, this.input);
/*     */ 
/* 347 */           throw eee;
/*     */         }
/* 349 */         cnt6++;
/*     */       }
/*     */ 
/* 352 */       label548: this.adapter.endRule();
/*     */     }
/*     */     catch (RecognitionException re)
/*     */     {
/* 357 */       re = 
/* 362 */         re;
/*     */ 
/* 358 */       reportError(re);
/* 359 */       recover(this.input, re);
/*     */     }
/*     */     finally
/*     */     {
/*     */     }
/*     */   }
/*     */ 
/*     */   public final void test()
/*     */     throws RecognitionException
/*     */   {
/* 371 */     ITestCaseInput input1 = null;
/*     */ 
/* 373 */     ITestCaseOutput expect2 = null;
/*     */     try
/*     */     {
/* 380 */       pushFollow(FOLLOW_input_in_test188);
/* 381 */       input1 = input();
/*     */ 
/* 383 */       this.state._fsp -= 1;
/*     */ 
/* 385 */       pushFollow(FOLLOW_expect_in_test190);
/* 386 */       expect2 = expect();
/*     */ 
/* 388 */       this.state._fsp -= 1;
/*     */ 
/* 390 */       this.adapter.addTestCase(input1, expect2);
/*     */     }
/*     */     catch (RecognitionException re)
/*     */     {
/* 395 */       re = 
/* 400 */         re;
/*     */ 
/* 396 */       reportError(re);
/* 397 */       recover(this.input, re);
/*     */     }
/*     */     finally
/*     */     {
/*     */     }
/*     */   }
/*     */ 
/*     */   public final ITestCaseOutput expect()
/*     */     throws RecognitionException
/*     */   {
/* 409 */     ITestCaseOutput out = null;
/*     */ 
/* 411 */     Token RETVAL3 = null;
/* 412 */     Token AST5 = null;
/* 413 */     output_return output4 = null;
/*     */     try
/*     */     {
/* 418 */       int alt7 = 5;
/* 419 */       switch (this.input.LA(1))
/*     */       {
/*     */       case 4:
/* 422 */         alt7 = 1;
/*     */ 
/* 424 */         break;
/*     */       case 5:
/* 427 */         alt7 = 2;
/*     */ 
/* 429 */         break;
/*     */       case 30:
/* 432 */         alt7 = 3;
/*     */ 
/* 434 */         break;
/*     */       case 31:
/* 437 */         switch (this.input.LA(2))
/*     */         {
/*     */         case 11:
/* 440 */           alt7 = 5;
/*     */ 
/* 442 */           break;
/*     */         case 7:
/*     */         case 12:
/*     */         case 13:
/* 447 */           alt7 = 4;
/*     */ 
/* 449 */           break;
/*     */         case 8:
/*     */         case 9:
/*     */         case 10:
/*     */         default:
/* 451 */           NoViableAltException nvae = new NoViableAltException("", 7, 4, this.input);
/*     */ 
/* 454 */           throw nvae;
/*     */         }
/*     */ 
/* 458 */         break;
/*     */       default:
/* 460 */         NoViableAltException nvae = new NoViableAltException("", 7, 0, this.input);
/*     */ 
/* 463 */         throw nvae;
/*     */       }
/*     */ 
/* 466 */       switch (alt7)
/*     */       {
/*     */       case 1:
/* 470 */         match(this.input, 4, FOLLOW_OK_in_expect210);
/* 471 */         out = TestSuiteAdapter.createBoolOutput(true);
/*     */ 
/* 474 */         break;
/*     */       case 2:
/* 478 */         match(this.input, 5, FOLLOW_FAIL_in_expect219);
/* 479 */         out = TestSuiteAdapter.createBoolOutput(false);
/*     */ 
/* 482 */         break;
/*     */       case 3:
/* 486 */         match(this.input, 30, FOLLOW_30_in_expect227);
/* 487 */         RETVAL3 = (Token)match(this.input, 10, FOLLOW_RETVAL_in_expect229);
/* 488 */         out = TestSuiteAdapter.createReturnOutput(RETVAL3 != null ? RETVAL3.getText() : null);
/*     */ 
/* 491 */         break;
/*     */       case 4:
/* 495 */         match(this.input, 31, FOLLOW_31_in_expect236);
/* 496 */         pushFollow(FOLLOW_output_in_expect238);
/* 497 */         output4 = output();
/*     */ 
/* 499 */         this.state._fsp -= 1;
/*     */ 
/* 501 */         out = TestSuiteAdapter.createStdOutput(output4 != null ? this.input.toString(output4.start, output4.stop) : null);
/*     */ 
/* 504 */         break;
/*     */       case 5:
/* 508 */         match(this.input, 31, FOLLOW_31_in_expect245);
/* 509 */         AST5 = (Token)match(this.input, 11, FOLLOW_AST_in_expect247);
/* 510 */         out = TestSuiteAdapter.createAstOutput(AST5 != null ? AST5.getText() : null);
/*     */       }
/*     */ 
/*     */     }
/*     */     catch (RecognitionException re)
/*     */     {
/* 517 */       re = 
/* 522 */         re;
/*     */ 
/* 518 */       reportError(re);
/* 519 */       recover(this.input, re);
/*     */     }
/*     */     finally {
/*     */     }
/* 523 */     return out;
/*     */   }
/*     */ 
/*     */   public final ITestCaseInput input()
/*     */     throws RecognitionException
/*     */   {
/* 531 */     ITestCaseInput in = null;
/*     */ 
/* 533 */     Token STRING6 = null;
/* 534 */     Token ML_STRING7 = null;
/* 535 */     String fileInput8 = null;
/*     */     try
/*     */     {
/* 540 */       int alt8 = 3;
/* 541 */       switch (this.input.LA(1))
/*     */       {
/*     */       case 12:
/* 544 */         alt8 = 1;
/*     */ 
/* 546 */         break;
/*     */       case 13:
/* 549 */         alt8 = 2;
/*     */ 
/* 551 */         break;
/*     */       case 8:
/*     */       case 9:
/* 555 */         alt8 = 3;
/*     */ 
/* 557 */         break;
/*     */       case 10:
/*     */       case 11:
/*     */       default:
/* 559 */         NoViableAltException nvae = new NoViableAltException("", 8, 0, this.input);
/*     */ 
/* 562 */         throw nvae;
/*     */       }
/*     */ 
/* 565 */       switch (alt8)
/*     */       {
/*     */       case 1:
/* 569 */         STRING6 = (Token)match(this.input, 12, FOLLOW_STRING_in_input264);
/* 570 */         in = TestSuiteAdapter.createStringInput(STRING6 != null ? STRING6.getText() : null);
/*     */ 
/* 573 */         break;
/*     */       case 2:
/* 577 */         ML_STRING7 = (Token)match(this.input, 13, FOLLOW_ML_STRING_in_input273);
/* 578 */         in = TestSuiteAdapter.createMultiInput(ML_STRING7 != null ? ML_STRING7.getText() : null);
/*     */ 
/* 581 */         break;
/*     */       case 3:
/* 585 */         pushFollow(FOLLOW_fileInput_in_input280);
/* 586 */         fileInput8 = fileInput();
/*     */ 
/* 588 */         this.state._fsp -= 1;
/*     */ 
/* 590 */         in = TestSuiteAdapter.createFileInput(fileInput8);
/*     */       }
/*     */ 
/*     */     }
/*     */     catch (RecognitionException re)
/*     */     {
/* 597 */       re = 
/* 602 */         re;
/*     */ 
/* 598 */       reportError(re);
/* 599 */       recover(this.input, re);
/*     */     }
/*     */     finally {
/*     */     }
/* 603 */     return in;
/*     */   }
/*     */ 
/*     */   public final output_return output()
/*     */     throws RecognitionException
/*     */   {
/* 613 */     output_return retval = new output_return();
/* 614 */     retval.start = this.input.LT(1);
/*     */     try
/*     */     {
/* 620 */       if ((this.input.LA(1) == 7) || ((this.input.LA(1) >= 12) && (this.input.LA(1) <= 13))) {
/* 621 */         this.input.consume();
/* 622 */         this.state.errorRecovery = false;
/*     */       }
/*     */       else {
/* 625 */         MismatchedSetException mse = new MismatchedSetException(null, this.input);
/* 626 */         throw mse;
/*     */       }
/*     */ 
/* 632 */       retval.stop = this.input.LT(-1);
/*     */     }
/*     */     catch (RecognitionException re) {
/* 635 */       re = 
/* 640 */         re;
/*     */ 
/* 636 */       reportError(re);
/* 637 */       recover(this.input, re);
/*     */     }
/*     */     finally {
/*     */     }
/* 641 */     return retval;
/*     */   }
/*     */ 
/*     */   public final String fileInput()
/*     */     throws RecognitionException
/*     */   {
/* 649 */     String path = null;
/*     */ 
/* 651 */     Token EXT10 = null;
/* 652 */     id_return id9 = null;
/*     */     try
/*     */     {
/* 659 */       pushFollow(FOLLOW_id_in_fileInput319);
/* 660 */       id9 = id();
/*     */ 
/* 662 */       this.state._fsp -= 1;
/*     */ 
/* 664 */       path = id9 != null ? this.input.toString(id9.start, id9.stop) : null;
/*     */ 
/* 666 */       int alt9 = 2;
/* 667 */       switch (this.input.LA(1))
/*     */       {
/*     */       case 14:
/* 670 */         alt9 = 1;
/*     */       }
/*     */ 
/* 675 */       switch (alt9)
/*     */       {
/*     */       case 1:
/* 679 */         EXT10 = (Token)match(this.input, 14, FOLLOW_EXT_in_fileInput324);
/* 680 */         path = path + (EXT10 != null ? EXT10.getText() : null);
/*     */       }
/*     */ 
/*     */     }
/*     */     catch (RecognitionException re)
/*     */     {
/* 691 */       re = 
/* 696 */         re;
/*     */ 
/* 692 */       reportError(re);
/* 693 */       recover(this.input, re);
/*     */     }
/*     */     finally {
/*     */     }
/* 697 */     return path;
/*     */   }
/*     */ 
/*     */   public final id_return id()
/*     */     throws RecognitionException
/*     */   {
/* 707 */     id_return retval = new id_return();
/* 708 */     retval.start = this.input.LT(1);
/*     */     try
/*     */     {
/* 714 */       if ((this.input.LA(1) >= 8) && (this.input.LA(1) <= 9)) {
/* 715 */         this.input.consume();
/* 716 */         this.state.errorRecovery = false;
/*     */       }
/*     */       else {
/* 719 */         MismatchedSetException mse = new MismatchedSetException(null, this.input);
/* 720 */         throw mse;
/*     */       }
/*     */ 
/* 726 */       retval.stop = this.input.LT(-1);
/*     */     }
/*     */     catch (RecognitionException re) {
/* 729 */       re = 
/* 734 */         re;
/*     */ 
/* 730 */       reportError(re);
/* 731 */       recover(this.input, re);
/*     */     }
/*     */     finally {
/*     */     }
/* 735 */     return retval;
/*     */   }
/*     */ 
/*     */   public static class id_return extends ParserRuleReturnScope
/*     */   {
/*     */   }
/*     */ 
/*     */   public static class output_return extends ParserRuleReturnScope
/*     */   {
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.antlr.gunit.swingui.parsers.StGUnitParser
 * JD-Core Version:    0.6.2
 */