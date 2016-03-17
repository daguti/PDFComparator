/*      */ package org.antlr.gunit;
/*      */ 
/*      */ import java.io.PrintStream;
/*      */ import java.util.Stack;
/*      */ import org.antlr.runtime.BitSet;
/*      */ import org.antlr.runtime.EarlyExitException;
/*      */ import org.antlr.runtime.NoViableAltException;
/*      */ import org.antlr.runtime.Parser;
/*      */ import org.antlr.runtime.ParserRuleReturnScope;
/*      */ import org.antlr.runtime.RecognitionException;
/*      */ import org.antlr.runtime.RecognizerSharedState;
/*      */ import org.antlr.runtime.Token;
/*      */ import org.antlr.runtime.TokenStream;
/*      */ 
/*      */ public class gUnitParser extends Parser
/*      */ {
/*   10 */   public static final String[] tokenNames = { "<invalid>", "<EOR>", "<DOWN>", "<UP>", "OK", "FAIL", "DOC_COMMENT", "OPTIONS", "EXT", "ACTION", "RULE_REF", "TOKEN_REF", "STRING", "ML_STRING", "RETVAL", "AST", "SL_COMMENT", "ML_COMMENT", "ESC", "NESTED_RETVAL", "NESTED_AST", "STRING_LITERAL", "WS", "NESTED_ACTION", "CHAR_LITERAL", "XDIGIT", "'gunit'", "'walks'", "';'", "'}'", "'='", "'@header'", "':'", "'returns'", "'->'" };
/*      */   public static final int EOF = -1;
/*      */   public static final int T__26 = 26;
/*      */   public static final int T__27 = 27;
/*      */   public static final int T__28 = 28;
/*      */   public static final int T__29 = 29;
/*      */   public static final int T__30 = 30;
/*      */   public static final int T__31 = 31;
/*      */   public static final int T__32 = 32;
/*      */   public static final int T__33 = 33;
/*      */   public static final int T__34 = 34;
/*      */   public static final int OK = 4;
/*      */   public static final int FAIL = 5;
/*      */   public static final int DOC_COMMENT = 6;
/*      */   public static final int OPTIONS = 7;
/*      */   public static final int EXT = 8;
/*      */   public static final int ACTION = 9;
/*      */   public static final int RULE_REF = 10;
/*      */   public static final int TOKEN_REF = 11;
/*      */   public static final int STRING = 12;
/*      */   public static final int ML_STRING = 13;
/*      */   public static final int RETVAL = 14;
/*      */   public static final int AST = 15;
/*      */   public static final int SL_COMMENT = 16;
/*      */   public static final int ML_COMMENT = 17;
/*      */   public static final int ESC = 18;
/*      */   public static final int NESTED_RETVAL = 19;
/*      */   public static final int NESTED_AST = 20;
/*      */   public static final int STRING_LITERAL = 21;
/*      */   public static final int WS = 22;
/*      */   public static final int NESTED_ACTION = 23;
/*      */   public static final int CHAR_LITERAL = 24;
/*      */   public static final int XDIGIT = 25;
/*      */   public GrammarInfo grammarInfo;
/*  434 */   protected Stack testsuite_stack = new Stack();
/*      */ 
/* 1063 */   public static final BitSet FOLLOW_26_in_gUnitDef60 = new BitSet(new long[] { 3072L });
/* 1064 */   public static final BitSet FOLLOW_id_in_gUnitDef64 = new BitSet(new long[] { 402653184L });
/* 1065 */   public static final BitSet FOLLOW_27_in_gUnitDef67 = new BitSet(new long[] { 3072L });
/* 1066 */   public static final BitSet FOLLOW_id_in_gUnitDef71 = new BitSet(new long[] { 268435456L });
/* 1067 */   public static final BitSet FOLLOW_28_in_gUnitDef75 = new BitSet(new long[] { 2147486850L });
/* 1068 */   public static final BitSet FOLLOW_optionsSpec_in_gUnitDef84 = new BitSet(new long[] { 2147486722L });
/* 1069 */   public static final BitSet FOLLOW_header_in_gUnitDef87 = new BitSet(new long[] { 3074L });
/* 1070 */   public static final BitSet FOLLOW_testsuite_in_gUnitDef90 = new BitSet(new long[] { 3074L });
/* 1071 */   public static final BitSet FOLLOW_OPTIONS_in_optionsSpec104 = new BitSet(new long[] { 3072L });
/* 1072 */   public static final BitSet FOLLOW_option_in_optionsSpec107 = new BitSet(new long[] { 268435456L });
/* 1073 */   public static final BitSet FOLLOW_28_in_optionsSpec109 = new BitSet(new long[] { 536873984L });
/* 1074 */   public static final BitSet FOLLOW_29_in_optionsSpec113 = new BitSet(new long[] { 2L });
/* 1075 */   public static final BitSet FOLLOW_id_in_option124 = new BitSet(new long[] { 1073741824L });
/* 1076 */   public static final BitSet FOLLOW_30_in_option126 = new BitSet(new long[] { 3072L });
/* 1077 */   public static final BitSet FOLLOW_treeAdaptor_in_option128 = new BitSet(new long[] { 2L });
/* 1078 */   public static final BitSet FOLLOW_id_in_treeAdaptor144 = new BitSet(new long[] { 258L });
/* 1079 */   public static final BitSet FOLLOW_EXT_in_treeAdaptor146 = new BitSet(new long[] { 258L });
/* 1080 */   public static final BitSet FOLLOW_31_in_header157 = new BitSet(new long[] { 512L });
/* 1081 */   public static final BitSet FOLLOW_ACTION_in_header159 = new BitSet(new long[] { 2L });
/* 1082 */   public static final BitSet FOLLOW_RULE_REF_in_testsuite190 = new BitSet(new long[] { 4429185024L });
/* 1083 */   public static final BitSet FOLLOW_27_in_testsuite193 = new BitSet(new long[] { 1024L });
/* 1084 */   public static final BitSet FOLLOW_RULE_REF_in_testsuite197 = new BitSet(new long[] { 4294967296L });
/* 1085 */   public static final BitSet FOLLOW_TOKEN_REF_in_testsuite213 = new BitSet(new long[] { 4294967296L });
/* 1086 */   public static final BitSet FOLLOW_32_in_testsuite227 = new BitSet(new long[] { 15360L });
/* 1087 */   public static final BitSet FOLLOW_testcase_in_testsuite231 = new BitSet(new long[] { 15362L });
/* 1088 */   public static final BitSet FOLLOW_input_in_testcase249 = new BitSet(new long[] { 25769803824L });
/* 1089 */   public static final BitSet FOLLOW_expect_in_testcase251 = new BitSet(new long[] { 2L });
/* 1090 */   public static final BitSet FOLLOW_STRING_in_input278 = new BitSet(new long[] { 2L });
/* 1091 */   public static final BitSet FOLLOW_ML_STRING_in_input288 = new BitSet(new long[] { 2L });
/* 1092 */   public static final BitSet FOLLOW_file_in_input297 = new BitSet(new long[] { 2L });
/* 1093 */   public static final BitSet FOLLOW_OK_in_expect317 = new BitSet(new long[] { 2L });
/* 1094 */   public static final BitSet FOLLOW_FAIL_in_expect324 = new BitSet(new long[] { 2L });
/* 1095 */   public static final BitSet FOLLOW_33_in_expect331 = new BitSet(new long[] { 16384L });
/* 1096 */   public static final BitSet FOLLOW_RETVAL_in_expect333 = new BitSet(new long[] { 2L });
/* 1097 */   public static final BitSet FOLLOW_34_in_expect340 = new BitSet(new long[] { 45568L });
/* 1098 */   public static final BitSet FOLLOW_output_in_expect342 = new BitSet(new long[] { 2L });
/* 1099 */   public static final BitSet FOLLOW_STRING_in_output359 = new BitSet(new long[] { 2L });
/* 1100 */   public static final BitSet FOLLOW_ML_STRING_in_output369 = new BitSet(new long[] { 2L });
/* 1101 */   public static final BitSet FOLLOW_AST_in_output376 = new BitSet(new long[] { 2L });
/* 1102 */   public static final BitSet FOLLOW_ACTION_in_output383 = new BitSet(new long[] { 2L });
/* 1103 */   public static final BitSet FOLLOW_id_in_file401 = new BitSet(new long[] { 258L });
/* 1104 */   public static final BitSet FOLLOW_EXT_in_file403 = new BitSet(new long[] { 2L });
/* 1105 */   public static final BitSet FOLLOW_TOKEN_REF_in_id422 = new BitSet(new long[] { 2L });
/* 1106 */   public static final BitSet FOLLOW_RULE_REF_in_id429 = new BitSet(new long[] { 2L });
/*      */ 
/*      */   public gUnitParser(TokenStream input)
/*      */   {
/*   51 */     this(input, new RecognizerSharedState());
/*      */   }
/*      */   public gUnitParser(TokenStream input, RecognizerSharedState state) {
/*   54 */     super(input, state);
/*      */   }
/*      */ 
/*      */   public String[] getTokenNames()
/*      */   {
/*   59 */     return tokenNames; } 
/*   60 */   public String getGrammarFileName() { return "org/antlr/gunit/gUnit.g"; }
/*      */ 
/*      */ 
/*      */   public gUnitParser(TokenStream input, GrammarInfo grammarInfo)
/*      */   {
/*   65 */     super(input);
/*   66 */     this.grammarInfo = grammarInfo;
/*      */   }
/*      */ 
/*      */   public final void gUnitDef()
/*      */     throws RecognitionException
/*      */   {
/*   74 */     id_return g1 = null;
/*      */ 
/*   76 */     id_return g2 = null;
/*      */     try
/*      */     {
/*   83 */       match(this.input, 26, FOLLOW_26_in_gUnitDef60);
/*   84 */       pushFollow(FOLLOW_id_in_gUnitDef64);
/*   85 */       g1 = id();
/*      */ 
/*   87 */       this.state._fsp -= 1;
/*      */ 
/*   90 */       int alt1 = 2;
/*   91 */       switch (this.input.LA(1))
/*      */       {
/*      */       case 27:
/*   94 */         alt1 = 1;
/*      */       }
/*      */ 
/*   99 */       switch (alt1)
/*      */       {
/*      */       case 1:
/*  103 */         match(this.input, 27, FOLLOW_27_in_gUnitDef67);
/*  104 */         pushFollow(FOLLOW_id_in_gUnitDef71);
/*  105 */         g2 = id();
/*      */ 
/*  107 */         this.state._fsp -= 1;
/*      */       }
/*      */ 
/*  115 */       match(this.input, 28, FOLLOW_28_in_gUnitDef75);
/*      */ 
/*  117 */       if ((g2 != null ? this.input.toString(g2.start, g2.stop) : null) != null) {
/*  118 */         this.grammarInfo.setGrammarName(g2 != null ? this.input.toString(g2.start, g2.stop) : null);
/*  119 */         this.grammarInfo.setTreeGrammarName(g1 != null ? this.input.toString(g1.start, g1.stop) : null);
/*      */       }
/*      */       else {
/*  122 */         this.grammarInfo.setGrammarName(g1 != null ? this.input.toString(g1.start, g1.stop) : null);
/*      */       }
/*      */ 
/*  126 */       int alt2 = 2;
/*  127 */       switch (this.input.LA(1))
/*      */       {
/*      */       case 7:
/*  130 */         alt2 = 1;
/*      */       }
/*      */ 
/*  135 */       switch (alt2)
/*      */       {
/*      */       case 1:
/*  139 */         pushFollow(FOLLOW_optionsSpec_in_gUnitDef84);
/*  140 */         optionsSpec();
/*      */ 
/*  142 */         this.state._fsp -= 1;
/*      */       }
/*      */ 
/*  151 */       int alt3 = 2;
/*  152 */       switch (this.input.LA(1))
/*      */       {
/*      */       case 31:
/*  155 */         alt3 = 1;
/*      */       }
/*      */ 
/*  160 */       switch (alt3)
/*      */       {
/*      */       case 1:
/*  164 */         pushFollow(FOLLOW_header_in_gUnitDef87);
/*  165 */         header();
/*      */ 
/*  167 */         this.state._fsp -= 1;
/*      */       }
/*      */ 
/*      */       while (true)
/*      */       {
/*  178 */         int alt4 = 2;
/*  179 */         switch (this.input.LA(1))
/*      */         {
/*      */         case 10:
/*      */         case 11:
/*  183 */           alt4 = 1;
/*      */         }
/*      */ 
/*  189 */         switch (alt4)
/*      */         {
/*      */         case 1:
/*  193 */           pushFollow(FOLLOW_testsuite_in_gUnitDef90);
/*  194 */           testsuite();
/*      */ 
/*  196 */           this.state._fsp -= 1;
/*      */ 
/*  200 */           break;
/*      */         default:
/*  203 */           return;
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (RecognitionException re)
/*      */     {
/*  211 */       re = 
/*  216 */         re;
/*      */ 
/*  212 */       reportError(re);
/*  213 */       recover(this.input, re);
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public final void optionsSpec()
/*      */     throws RecognitionException
/*      */   {
/*      */     try
/*      */     {
/*  229 */       match(this.input, 7, FOLLOW_OPTIONS_in_optionsSpec104);
/*      */ 
/*  231 */       int cnt5 = 0;
/*      */       while (true)
/*      */       {
/*  234 */         int alt5 = 2;
/*  235 */         switch (this.input.LA(1))
/*      */         {
/*      */         case 10:
/*      */         case 11:
/*  239 */           alt5 = 1;
/*      */         }
/*      */ 
/*  245 */         switch (alt5)
/*      */         {
/*      */         case 1:
/*  249 */           pushFollow(FOLLOW_option_in_optionsSpec107);
/*  250 */           option();
/*      */ 
/*  252 */           this.state._fsp -= 1;
/*      */ 
/*  254 */           match(this.input, 28, FOLLOW_28_in_optionsSpec109);
/*      */ 
/*  257 */           break;
/*      */         default:
/*  260 */           if (cnt5 >= 1) break label147;
/*  261 */           EarlyExitException eee = new EarlyExitException(5, this.input);
/*      */ 
/*  263 */           throw eee;
/*      */         }
/*  265 */         cnt5++;
/*      */       }
/*      */ 
/*  268 */       label147: match(this.input, 29, FOLLOW_29_in_optionsSpec113);
/*      */     }
/*      */     catch (RecognitionException re)
/*      */     {
/*  273 */       re = 
/*  278 */         re;
/*      */ 
/*  274 */       reportError(re);
/*  275 */       recover(this.input, re);
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public final option_return option()
/*      */     throws RecognitionException
/*      */   {
/*  289 */     option_return retval = new option_return();
/*  290 */     retval.start = this.input.LT(1);
/*      */ 
/*  292 */     id_return id1 = null;
/*      */ 
/*  294 */     treeAdaptor_return treeAdaptor2 = null;
/*      */     try
/*      */     {
/*  301 */       pushFollow(FOLLOW_id_in_option124);
/*  302 */       id1 = id();
/*      */ 
/*  304 */       this.state._fsp -= 1;
/*      */ 
/*  306 */       match(this.input, 30, FOLLOW_30_in_option126);
/*  307 */       pushFollow(FOLLOW_treeAdaptor_in_option128);
/*  308 */       treeAdaptor2 = treeAdaptor();
/*      */ 
/*  310 */       this.state._fsp -= 1;
/*      */ 
/*  313 */       if ((id1 != null ? this.input.toString(id1.start, id1.stop) : null).equals("TreeAdaptor")) {
/*  314 */         this.grammarInfo.setAdaptor(treeAdaptor2 != null ? this.input.toString(treeAdaptor2.start, treeAdaptor2.stop) : null);
/*      */       }
/*      */       else {
/*  317 */         System.err.println("Invalid option detected: " + this.input.toString(retval.start, this.input.LT(-1)));
/*      */       }
/*      */ 
/*  322 */       retval.stop = this.input.LT(-1);
/*      */     }
/*      */     catch (RecognitionException re) {
/*  325 */       re = 
/*  330 */         re;
/*      */ 
/*  326 */       reportError(re);
/*  327 */       recover(this.input, re);
/*      */     }
/*      */     finally {
/*      */     }
/*  331 */     return retval;
/*      */   }
/*      */ 
/*      */   public final treeAdaptor_return treeAdaptor()
/*      */     throws RecognitionException
/*      */   {
/*  341 */     treeAdaptor_return retval = new treeAdaptor_return();
/*  342 */     retval.start = this.input.LT(1);
/*      */     try
/*      */     {
/*  348 */       pushFollow(FOLLOW_id_in_treeAdaptor144);
/*  349 */       id();
/*      */ 
/*  351 */       this.state._fsp -= 1;
/*      */       while (true)
/*      */       {
/*  356 */         int alt6 = 2;
/*  357 */         switch (this.input.LA(1))
/*      */         {
/*      */         case 8:
/*  360 */           alt6 = 1;
/*      */         }
/*      */ 
/*  366 */         switch (alt6)
/*      */         {
/*      */         case 1:
/*  370 */           match(this.input, 8, FOLLOW_EXT_in_treeAdaptor146);
/*      */ 
/*  373 */           break;
/*      */         default:
/*  376 */           break label119;
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/*  383 */       label119: retval.stop = this.input.LT(-1);
/*      */     }
/*      */     catch (RecognitionException re) {
/*  386 */       re = 
/*  391 */         re;
/*      */ 
/*  387 */       reportError(re);
/*  388 */       recover(this.input, re);
/*      */     }
/*      */     finally {
/*      */     }
/*  392 */     return retval;
/*      */   }
/*      */ 
/*      */   public final void header()
/*      */     throws RecognitionException
/*      */   {
/*  400 */     Token ACTION3 = null;
/*      */     try
/*      */     {
/*  406 */       match(this.input, 31, FOLLOW_31_in_header157);
/*  407 */       ACTION3 = (Token)match(this.input, 9, FOLLOW_ACTION_in_header159);
/*      */       int pos1;
/*  410 */       if ((pos1 = (ACTION3 != null ? ACTION3.getText() : null).indexOf("package")) != -1)
/*      */       {
/*      */         int pos2;
/*  410 */         if ((pos2 = (ACTION3 != null ? ACTION3.getText() : null).indexOf(';')) != -1) {
/*  411 */           this.grammarInfo.setGrammarPackage((ACTION3 != null ? ACTION3.getText() : null).substring(pos1 + 8, pos2).trim()); return;
/*      */         }
/*      */       }
/*  414 */       System.err.println("error(line " + ACTION3.getLine() + "): invalid header");
/*      */     }
/*      */     catch (RecognitionException re)
/*      */     {
/*  421 */       re = 
/*  426 */         re;
/*      */ 
/*  422 */       reportError(re);
/*  423 */       recover(this.input, re);
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public final void testsuite()
/*      */     throws RecognitionException
/*      */   {
/*  440 */     this.testsuite_stack.push(new testsuite_scope());
/*  441 */     Token r1 = null;
/*  442 */     Token r2 = null;
/*  443 */     Token t = null;
/*      */ 
/*  446 */     gUnitTestSuite ts = null;
/*  447 */     ((testsuite_scope)this.testsuite_stack.peek()).isLexicalRule = false;
/*      */     try
/*      */     {
/*  454 */       int alt8 = 2;
/*  455 */       switch (this.input.LA(1))
/*      */       {
/*      */       case 10:
/*  458 */         alt8 = 1;
/*      */ 
/*  460 */         break;
/*      */       case 11:
/*  463 */         alt8 = 2;
/*      */ 
/*  465 */         break;
/*      */       default:
/*  467 */         NoViableAltException nvae = new NoViableAltException("", 8, 0, this.input);
/*      */ 
/*  470 */         throw nvae;
/*      */       }
/*      */ 
/*  473 */       switch (alt8)
/*      */       {
/*      */       case 1:
/*  477 */         r1 = (Token)match(this.input, 10, FOLLOW_RULE_REF_in_testsuite190);
/*      */ 
/*  479 */         int alt7 = 2;
/*  480 */         switch (this.input.LA(1))
/*      */         {
/*      */         case 27:
/*  483 */           alt7 = 1;
/*      */         }
/*      */ 
/*  488 */         switch (alt7)
/*      */         {
/*      */         case 1:
/*  492 */           match(this.input, 27, FOLLOW_27_in_testsuite193);
/*  493 */           r2 = (Token)match(this.input, 10, FOLLOW_RULE_REF_in_testsuite197);
/*      */         }
/*      */ 
/*  501 */         if (r2 == null) ts = new gUnitTestSuite(r1 != null ? r1.getText() : null); else {
/*  502 */           ts = new gUnitTestSuite(r1 != null ? r1.getText() : null, r2 != null ? r2.getText() : null);
/*      */         }
/*      */ 
/*  506 */         break;
/*      */       case 2:
/*  510 */         t = (Token)match(this.input, 11, FOLLOW_TOKEN_REF_in_testsuite213);
/*      */ 
/*  512 */         ts = new gUnitTestSuite();
/*  513 */         ts.setLexicalRuleName(t != null ? t.getText() : null);
/*  514 */         ((testsuite_scope)this.testsuite_stack.peek()).isLexicalRule = true;
/*      */       }
/*      */ 
/*  522 */       match(this.input, 32, FOLLOW_32_in_testsuite227);
/*      */ 
/*  524 */       int cnt9 = 0;
/*      */       while (true)
/*      */       {
/*  527 */         int alt9 = 2;
/*  528 */         switch (this.input.LA(1))
/*      */         {
/*      */         case 10:
/*  531 */           switch (this.input.LA(2))
/*      */           {
/*      */           case 4:
/*      */           case 5:
/*      */           case 8:
/*      */           case 33:
/*      */           case 34:
/*  538 */             alt9 = 1;
/*      */           }
/*      */ 
/*  545 */           break;
/*      */         case 11:
/*  548 */           switch (this.input.LA(2))
/*      */           {
/*      */           case 4:
/*      */           case 5:
/*      */           case 8:
/*      */           case 33:
/*      */           case 34:
/*  555 */             alt9 = 1;
/*      */           }
/*      */ 
/*  562 */           break;
/*      */         case 12:
/*      */         case 13:
/*  566 */           alt9 = 1;
/*      */         }
/*      */ 
/*  572 */         switch (alt9)
/*      */         {
/*      */         case 1:
/*  576 */           pushFollow(FOLLOW_testcase_in_testsuite231);
/*  577 */           testcase(ts);
/*      */ 
/*  579 */           this.state._fsp -= 1;
/*      */ 
/*  583 */           break;
/*      */         default:
/*  586 */           if (cnt9 >= 1) break label646;
/*  587 */           EarlyExitException eee = new EarlyExitException(9, this.input);
/*      */ 
/*  589 */           throw eee;
/*      */         }
/*  591 */         cnt9++;
/*      */       }
/*      */ 
/*  594 */       label646: this.grammarInfo.addRuleTestSuite(ts);
/*      */     }
/*      */     catch (RecognitionException re)
/*      */     {
/*  600 */       reportError(re);
/*  601 */       recover(this.input, re);
/*      */     }
/*      */     finally {
/*  604 */       this.testsuite_stack.pop();
/*      */     }
/*      */   }
/*      */ 
/*      */   public final void testcase(gUnitTestSuite ts)
/*      */     throws RecognitionException
/*      */   {
/*  614 */     gUnitTestInput input4 = null;
/*      */ 
/*  616 */     AbstractTest expect5 = null;
/*      */     try
/*      */     {
/*  623 */       pushFollow(FOLLOW_input_in_testcase249);
/*  624 */       input4 = input();
/*      */ 
/*  626 */       this.state._fsp -= 1;
/*      */ 
/*  628 */       pushFollow(FOLLOW_expect_in_testcase251);
/*  629 */       expect5 = expect();
/*      */ 
/*  631 */       this.state._fsp -= 1;
/*      */ 
/*  633 */       ts.addTestCase(input4, expect5);
/*      */     }
/*      */     catch (RecognitionException re)
/*      */     {
/*  638 */       re = 
/*  643 */         re;
/*      */ 
/*  639 */       reportError(re);
/*  640 */       recover(this.input, re);
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public final gUnitTestInput input()
/*      */     throws RecognitionException
/*      */   {
/*  652 */     gUnitTestInput in = null;
/*      */ 
/*  654 */     Token STRING6 = null;
/*  655 */     Token ML_STRING7 = null;
/*  656 */     file_return file8 = null;
/*      */ 
/*  660 */     String testInput = null;
/*  661 */     boolean inputIsFile = false;
/*  662 */     int line = -1;
/*      */     try
/*      */     {
/*  666 */       int alt10 = 3;
/*  667 */       switch (this.input.LA(1))
/*      */       {
/*      */       case 12:
/*  670 */         alt10 = 1;
/*      */ 
/*  672 */         break;
/*      */       case 13:
/*  675 */         alt10 = 2;
/*      */ 
/*  677 */         break;
/*      */       case 10:
/*      */       case 11:
/*  681 */         alt10 = 3;
/*      */ 
/*  683 */         break;
/*      */       default:
/*  685 */         NoViableAltException nvae = new NoViableAltException("", 10, 0, this.input);
/*      */ 
/*  688 */         throw nvae;
/*      */       }
/*      */ 
/*  691 */       switch (alt10)
/*      */       {
/*      */       case 1:
/*  695 */         STRING6 = (Token)match(this.input, 12, FOLLOW_STRING_in_input278);
/*      */ 
/*  697 */         testInput = (STRING6 != null ? STRING6.getText() : null).replace("\\n", "\n").replace("\\r", "\r").replace("\\t", "\t").replace("\\b", "\b").replace("\\f", "\f").replace("\\\"", "\"").replace("\\'", "'").replace("\\\\", "\\");
/*      */ 
/*  699 */         line = STRING6 != null ? STRING6.getLine() : 0;
/*      */ 
/*  703 */         break;
/*      */       case 2:
/*  707 */         ML_STRING7 = (Token)match(this.input, 13, FOLLOW_ML_STRING_in_input288);
/*      */ 
/*  709 */         testInput = ML_STRING7 != null ? ML_STRING7.getText() : null;
/*  710 */         line = ML_STRING7 != null ? ML_STRING7.getLine() : 0;
/*      */ 
/*  714 */         break;
/*      */       case 3:
/*  718 */         pushFollow(FOLLOW_file_in_input297);
/*  719 */         file8 = file();
/*      */ 
/*  721 */         this.state._fsp -= 1;
/*      */ 
/*  724 */         testInput = file8 != null ? this.input.toString(file8.start, file8.stop) : null;
/*  725 */         inputIsFile = true;
/*  726 */         line = file8 != null ? file8.line : 0;
/*      */       }
/*      */ 
/*  734 */       in = new gUnitTestInput(testInput, inputIsFile, line);
/*      */     }
/*      */     catch (RecognitionException re) {
/*  737 */       re = 
/*  742 */         re;
/*      */ 
/*  738 */       reportError(re);
/*  739 */       recover(this.input, re);
/*      */     }
/*      */     finally {
/*      */     }
/*  743 */     return in;
/*      */   }
/*      */ 
/*      */   public final AbstractTest expect()
/*      */     throws RecognitionException
/*      */   {
/*  751 */     AbstractTest out = null;
/*      */ 
/*  753 */     Token RETVAL9 = null;
/*  754 */     Token output10 = null;
/*      */     try
/*      */     {
/*  759 */       int alt11 = 4;
/*  760 */       switch (this.input.LA(1))
/*      */       {
/*      */       case 4:
/*  763 */         alt11 = 1;
/*      */ 
/*  765 */         break;
/*      */       case 5:
/*  768 */         alt11 = 2;
/*      */ 
/*  770 */         break;
/*      */       case 33:
/*  773 */         alt11 = 3;
/*      */ 
/*  775 */         break;
/*      */       case 34:
/*  778 */         alt11 = 4;
/*      */ 
/*  780 */         break;
/*      */       default:
/*  782 */         NoViableAltException nvae = new NoViableAltException("", 11, 0, this.input);
/*      */ 
/*  785 */         throw nvae;
/*      */       }
/*      */ 
/*  788 */       switch (alt11)
/*      */       {
/*      */       case 1:
/*  792 */         match(this.input, 4, FOLLOW_OK_in_expect317);
/*  793 */         out = new BooleanTest(true);
/*      */ 
/*  796 */         break;
/*      */       case 2:
/*  800 */         match(this.input, 5, FOLLOW_FAIL_in_expect324);
/*  801 */         out = new BooleanTest(false);
/*      */ 
/*  804 */         break;
/*      */       case 3:
/*  808 */         match(this.input, 33, FOLLOW_33_in_expect331);
/*  809 */         RETVAL9 = (Token)match(this.input, 14, FOLLOW_RETVAL_in_expect333);
/*  810 */         if (!((testsuite_scope)this.testsuite_stack.peek()).isLexicalRule) out = new ReturnTest(RETVAL9); break;
/*      */       case 4:
/*  817 */         match(this.input, 34, FOLLOW_34_in_expect340);
/*  818 */         pushFollow(FOLLOW_output_in_expect342);
/*  819 */         output10 = output();
/*      */ 
/*  821 */         this.state._fsp -= 1;
/*      */ 
/*  823 */         if (!((testsuite_scope)this.testsuite_stack.peek()).isLexicalRule) out = new OutputTest(output10);
/*      */         break;
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (RecognitionException re)
/*      */     {
/*  830 */       re = 
/*  835 */         re;
/*      */ 
/*  831 */       reportError(re);
/*  832 */       recover(this.input, re);
/*      */     }
/*      */     finally {
/*      */     }
/*  836 */     return out;
/*      */   }
/*      */ 
/*      */   public final Token output()
/*      */     throws RecognitionException
/*      */   {
/*  844 */     Token token = null;
/*      */ 
/*  846 */     Token STRING11 = null;
/*  847 */     Token ML_STRING12 = null;
/*  848 */     Token AST13 = null;
/*  849 */     Token ACTION14 = null;
/*      */     try
/*      */     {
/*  853 */       int alt12 = 4;
/*  854 */       switch (this.input.LA(1))
/*      */       {
/*      */       case 12:
/*  857 */         alt12 = 1;
/*      */ 
/*  859 */         break;
/*      */       case 13:
/*  862 */         alt12 = 2;
/*      */ 
/*  864 */         break;
/*      */       case 15:
/*  867 */         alt12 = 3;
/*      */ 
/*  869 */         break;
/*      */       case 9:
/*  872 */         alt12 = 4;
/*      */ 
/*  874 */         break;
/*      */       case 10:
/*      */       case 11:
/*      */       case 14:
/*      */       default:
/*  876 */         NoViableAltException nvae = new NoViableAltException("", 12, 0, this.input);
/*      */ 
/*  879 */         throw nvae;
/*      */       }
/*      */ 
/*  882 */       switch (alt12)
/*      */       {
/*      */       case 1:
/*  886 */         STRING11 = (Token)match(this.input, 12, FOLLOW_STRING_in_output359);
/*      */ 
/*  888 */         STRING11.setText((STRING11 != null ? STRING11.getText() : null).replace("\\n", "\n").replace("\\r", "\r").replace("\\t", "\t").replace("\\b", "\b").replace("\\f", "\f").replace("\\\"", "\"").replace("\\'", "'").replace("\\\\", "\\"));
/*      */ 
/*  890 */         token = STRING11;
/*      */ 
/*  894 */         break;
/*      */       case 2:
/*  898 */         ML_STRING12 = (Token)match(this.input, 13, FOLLOW_ML_STRING_in_output369);
/*  899 */         token = ML_STRING12;
/*      */ 
/*  902 */         break;
/*      */       case 3:
/*  906 */         AST13 = (Token)match(this.input, 15, FOLLOW_AST_in_output376);
/*  907 */         token = AST13;
/*      */ 
/*  910 */         break;
/*      */       case 4:
/*  914 */         ACTION14 = (Token)match(this.input, 9, FOLLOW_ACTION_in_output383);
/*  915 */         token = ACTION14;
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (RecognitionException re)
/*      */     {
/*  922 */       re = 
/*  927 */         re;
/*      */ 
/*  923 */       reportError(re);
/*  924 */       recover(this.input, re);
/*      */     }
/*      */     finally {
/*      */     }
/*  928 */     return token;
/*      */   }
/*      */ 
/*      */   public final file_return file()
/*      */     throws RecognitionException
/*      */   {
/*  939 */     file_return retval = new file_return();
/*  940 */     retval.start = this.input.LT(1);
/*      */ 
/*  942 */     id_return id15 = null;
/*      */     try
/*      */     {
/*  949 */       pushFollow(FOLLOW_id_in_file401);
/*  950 */       id15 = id();
/*      */ 
/*  952 */       this.state._fsp -= 1;
/*      */ 
/*  955 */       int alt13 = 2;
/*  956 */       switch (this.input.LA(1))
/*      */       {
/*      */       case 8:
/*  959 */         alt13 = 1;
/*      */       }
/*      */ 
/*  964 */       switch (alt13)
/*      */       {
/*      */       case 1:
/*  968 */         match(this.input, 8, FOLLOW_EXT_in_file403);
/*      */       }
/*      */ 
/*  975 */       retval.line = (id15 != null ? id15.line : 0);
/*      */ 
/*  979 */       retval.stop = this.input.LT(-1);
/*      */     }
/*      */     catch (RecognitionException re) {
/*  982 */       re = 
/*  987 */         re;
/*      */ 
/*  983 */       reportError(re);
/*  984 */       recover(this.input, re);
/*      */     }
/*      */     finally {
/*      */     }
/*  988 */     return retval;
/*      */   }
/*      */ 
/*      */   public final id_return id()
/*      */     throws RecognitionException
/*      */   {
/*  999 */     id_return retval = new id_return();
/* 1000 */     retval.start = this.input.LT(1);
/*      */ 
/* 1002 */     Token TOKEN_REF16 = null;
/* 1003 */     Token RULE_REF17 = null;
/*      */     try
/*      */     {
/* 1007 */       int alt14 = 2;
/* 1008 */       switch (this.input.LA(1))
/*      */       {
/*      */       case 11:
/* 1011 */         alt14 = 1;
/*      */ 
/* 1013 */         break;
/*      */       case 10:
/* 1016 */         alt14 = 2;
/*      */ 
/* 1018 */         break;
/*      */       default:
/* 1020 */         NoViableAltException nvae = new NoViableAltException("", 14, 0, this.input);
/*      */ 
/* 1023 */         throw nvae;
/*      */       }
/*      */ 
/* 1026 */       switch (alt14)
/*      */       {
/*      */       case 1:
/* 1030 */         TOKEN_REF16 = (Token)match(this.input, 11, FOLLOW_TOKEN_REF_in_id422);
/* 1031 */         retval.line = (TOKEN_REF16 != null ? TOKEN_REF16.getLine() : 0);
/*      */ 
/* 1034 */         break;
/*      */       case 2:
/* 1038 */         RULE_REF17 = (Token)match(this.input, 10, FOLLOW_RULE_REF_in_id429);
/* 1039 */         retval.line = (RULE_REF17 != null ? RULE_REF17.getLine() : 0);
/*      */       }
/*      */ 
/* 1045 */       retval.stop = this.input.LT(-1);
/*      */     }
/*      */     catch (RecognitionException re) {
/* 1048 */       re = 
/* 1053 */         re;
/*      */ 
/* 1049 */       reportError(re);
/* 1050 */       recover(this.input, re);
/*      */     }
/*      */     finally {
/*      */     }
/* 1054 */     return retval;
/*      */   }
/*      */ 
/*      */   public static class id_return extends ParserRuleReturnScope
/*      */   {
/*      */     public int line;
/*      */   }
/*      */ 
/*      */   public static class file_return extends ParserRuleReturnScope
/*      */   {
/*      */     public int line;
/*      */   }
/*      */ 
/*      */   protected static class testsuite_scope
/*      */   {
/*      */     boolean isLexicalRule;
/*      */   }
/*      */ 
/*      */   public static class treeAdaptor_return extends ParserRuleReturnScope
/*      */   {
/*      */   }
/*      */ 
/*      */   public static class option_return extends ParserRuleReturnScope
/*      */   {
/*      */   }
/*      */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.antlr.gunit.gUnitParser
 * JD-Core Version:    0.6.2
 */