/*      */ package org.stringtemplate.v4.compiler;
/*      */ 
/*      */ import java.io.File;
/*      */ import java.util.ArrayList;
/*      */ import java.util.HashMap;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.Stack;
/*      */ import org.antlr.runtime.BitSet;
/*      */ import org.antlr.runtime.EarlyExitException;
/*      */ import org.antlr.runtime.FailedPredicateException;
/*      */ import org.antlr.runtime.MismatchedSetException;
/*      */ import org.antlr.runtime.MismatchedTokenException;
/*      */ import org.antlr.runtime.NoViableAltException;
/*      */ import org.antlr.runtime.Parser;
/*      */ import org.antlr.runtime.RecognitionException;
/*      */ import org.antlr.runtime.RecognizerSharedState;
/*      */ import org.antlr.runtime.Token;
/*      */ import org.antlr.runtime.TokenStream;
/*      */ import org.stringtemplate.v4.STGroup;
/*      */ import org.stringtemplate.v4.misc.ErrorManager;
/*      */ import org.stringtemplate.v4.misc.ErrorType;
/*      */ import org.stringtemplate.v4.misc.Misc;
/*      */ 
/*      */ public class GroupParser extends Parser
/*      */ {
/*   47 */   public static final String[] tokenNames = { "<invalid>", "<EOR>", "<DOWN>", "<UP>", "ANONYMOUS_TEMPLATE", "BIGSTRING", "BIGSTRING_NO_NL", "COMMENT", "FALSE", "ID", "LINE_COMMENT", "STRING", "TRUE", "WS", "'('", "')'", "','", "'.'", "':'", "'::='", "';'", "'='", "'@'", "'['", "']'", "'default'", "'delimiters'", "'group'", "'implements'", "'import'" };
/*      */   public static final int EOF = -1;
/*      */   public static final int T__14 = 14;
/*      */   public static final int T__15 = 15;
/*      */   public static final int T__16 = 16;
/*      */   public static final int T__17 = 17;
/*      */   public static final int T__18 = 18;
/*      */   public static final int T__19 = 19;
/*      */   public static final int T__20 = 20;
/*      */   public static final int T__21 = 21;
/*      */   public static final int T__22 = 22;
/*      */   public static final int T__23 = 23;
/*      */   public static final int T__24 = 24;
/*      */   public static final int T__25 = 25;
/*      */   public static final int T__26 = 26;
/*      */   public static final int T__27 = 27;
/*      */   public static final int T__28 = 28;
/*      */   public static final int T__29 = 29;
/*      */   public static final int ANONYMOUS_TEMPLATE = 4;
/*      */   public static final int BIGSTRING = 5;
/*      */   public static final int BIGSTRING_NO_NL = 6;
/*      */   public static final int COMMENT = 7;
/*      */   public static final int FALSE = 8;
/*      */   public static final int ID = 9;
/*      */   public static final int LINE_COMMENT = 10;
/*      */   public static final int STRING = 11;
/*      */   public static final int TRUE = 12;
/*      */   public static final int WS = 13;
/*      */   public STGroup group;
/*  841 */   protected Stack formalArgs_stack = new Stack();
/*      */ 
/* 1449 */   public static final BitSet FOLLOW_oldStyleHeader_in_group76 = new BitSet(new long[] { 608174592L });
/* 1450 */   public static final BitSet FOLLOW_delimiters_in_group81 = new BitSet(new long[] { 541065728L });
/* 1451 */   public static final BitSet FOLLOW_29_in_group91 = new BitSet(new long[] { 2048L });
/* 1452 */   public static final BitSet FOLLOW_STRING_in_group93 = new BitSet(new long[] { 541065728L });
/* 1453 */   public static final BitSet FOLLOW_29_in_group101 = new BitSet(new long[] { 512L });
/* 1454 */   public static final BitSet FOLLOW_ID_in_group112 = new BitSet(new long[] { 541196800L });
/* 1455 */   public static final BitSet FOLLOW_17_in_group115 = new BitSet(new long[] { 512L });
/* 1456 */   public static final BitSet FOLLOW_ID_in_group117 = new BitSet(new long[] { 541196800L });
/* 1457 */   public static final BitSet FOLLOW_def_in_group135 = new BitSet(new long[] { 4194818L });
/* 1458 */   public static final BitSet FOLLOW_27_in_oldStyleHeader157 = new BitSet(new long[] { 512L });
/* 1459 */   public static final BitSet FOLLOW_ID_in_oldStyleHeader159 = new BitSet(new long[] { 269746176L });
/* 1460 */   public static final BitSet FOLLOW_18_in_oldStyleHeader163 = new BitSet(new long[] { 512L });
/* 1461 */   public static final BitSet FOLLOW_ID_in_oldStyleHeader165 = new BitSet(new long[] { 269484032L });
/* 1462 */   public static final BitSet FOLLOW_28_in_oldStyleHeader177 = new BitSet(new long[] { 512L });
/* 1463 */   public static final BitSet FOLLOW_ID_in_oldStyleHeader179 = new BitSet(new long[] { 1114112L });
/* 1464 */   public static final BitSet FOLLOW_16_in_oldStyleHeader182 = new BitSet(new long[] { 512L });
/* 1465 */   public static final BitSet FOLLOW_ID_in_oldStyleHeader184 = new BitSet(new long[] { 1114112L });
/* 1466 */   public static final BitSet FOLLOW_20_in_oldStyleHeader196 = new BitSet(new long[] { 2L });
/* 1467 */   public static final BitSet FOLLOW_ID_in_groupName218 = new BitSet(new long[] { 131074L });
/* 1468 */   public static final BitSet FOLLOW_17_in_groupName223 = new BitSet(new long[] { 512L });
/* 1469 */   public static final BitSet FOLLOW_ID_in_groupName227 = new BitSet(new long[] { 131074L });
/* 1470 */   public static final BitSet FOLLOW_26_in_delimiters245 = new BitSet(new long[] { 2048L });
/* 1471 */   public static final BitSet FOLLOW_STRING_in_delimiters249 = new BitSet(new long[] { 65536L });
/* 1472 */   public static final BitSet FOLLOW_16_in_delimiters251 = new BitSet(new long[] { 2048L });
/* 1473 */   public static final BitSet FOLLOW_STRING_in_delimiters255 = new BitSet(new long[] { 2L });
/* 1474 */   public static final BitSet FOLLOW_templateDef_in_def279 = new BitSet(new long[] { 2L });
/* 1475 */   public static final BitSet FOLLOW_dictDef_in_def284 = new BitSet(new long[] { 2L });
/* 1476 */   public static final BitSet FOLLOW_22_in_templateDef308 = new BitSet(new long[] { 512L });
/* 1477 */   public static final BitSet FOLLOW_ID_in_templateDef312 = new BitSet(new long[] { 131072L });
/* 1478 */   public static final BitSet FOLLOW_17_in_templateDef314 = new BitSet(new long[] { 512L });
/* 1479 */   public static final BitSet FOLLOW_ID_in_templateDef318 = new BitSet(new long[] { 16384L });
/* 1480 */   public static final BitSet FOLLOW_14_in_templateDef320 = new BitSet(new long[] { 32768L });
/* 1481 */   public static final BitSet FOLLOW_15_in_templateDef322 = new BitSet(new long[] { 524288L });
/* 1482 */   public static final BitSet FOLLOW_ID_in_templateDef330 = new BitSet(new long[] { 16384L });
/* 1483 */   public static final BitSet FOLLOW_14_in_templateDef332 = new BitSet(new long[] { 33280L });
/* 1484 */   public static final BitSet FOLLOW_formalArgs_in_templateDef334 = new BitSet(new long[] { 32768L });
/* 1485 */   public static final BitSet FOLLOW_15_in_templateDef336 = new BitSet(new long[] { 524288L });
/* 1486 */   public static final BitSet FOLLOW_19_in_templateDef347 = new BitSet(new long[] { 2146L });
/* 1487 */   public static final BitSet FOLLOW_STRING_in_templateDef363 = new BitSet(new long[] { 2L });
/* 1488 */   public static final BitSet FOLLOW_BIGSTRING_in_templateDef378 = new BitSet(new long[] { 2L });
/* 1489 */   public static final BitSet FOLLOW_BIGSTRING_NO_NL_in_templateDef390 = new BitSet(new long[] { 2L });
/* 1490 */   public static final BitSet FOLLOW_ID_in_templateDef425 = new BitSet(new long[] { 524288L });
/* 1491 */   public static final BitSet FOLLOW_19_in_templateDef427 = new BitSet(new long[] { 512L });
/* 1492 */   public static final BitSet FOLLOW_ID_in_templateDef431 = new BitSet(new long[] { 2L });
/* 1493 */   public static final BitSet FOLLOW_formalArg_in_formalArgs457 = new BitSet(new long[] { 65538L });
/* 1494 */   public static final BitSet FOLLOW_16_in_formalArgs461 = new BitSet(new long[] { 512L });
/* 1495 */   public static final BitSet FOLLOW_formalArg_in_formalArgs463 = new BitSet(new long[] { 65538L });
/* 1496 */   public static final BitSet FOLLOW_ID_in_formalArg481 = new BitSet(new long[] { 2097154L });
/* 1497 */   public static final BitSet FOLLOW_21_in_formalArg487 = new BitSet(new long[] { 6416L });
/* 1498 */   public static final BitSet FOLLOW_set_in_formalArg491 = new BitSet(new long[] { 2L });
/* 1499 */   public static final BitSet FOLLOW_ID_in_dictDef532 = new BitSet(new long[] { 524288L });
/* 1500 */   public static final BitSet FOLLOW_19_in_dictDef534 = new BitSet(new long[] { 8388608L });
/* 1501 */   public static final BitSet FOLLOW_dict_in_dictDef536 = new BitSet(new long[] { 2L });
/* 1502 */   public static final BitSet FOLLOW_23_in_dict568 = new BitSet(new long[] { 33556480L });
/* 1503 */   public static final BitSet FOLLOW_dictPairs_in_dict570 = new BitSet(new long[] { 16777216L });
/* 1504 */   public static final BitSet FOLLOW_24_in_dict573 = new BitSet(new long[] { 2L });
/* 1505 */   public static final BitSet FOLLOW_keyValuePair_in_dictPairs588 = new BitSet(new long[] { 65538L });
/* 1506 */   public static final BitSet FOLLOW_16_in_dictPairs597 = new BitSet(new long[] { 2048L });
/* 1507 */   public static final BitSet FOLLOW_keyValuePair_in_dictPairs599 = new BitSet(new long[] { 65538L });
/* 1508 */   public static final BitSet FOLLOW_16_in_dictPairs605 = new BitSet(new long[] { 33554432L });
/* 1509 */   public static final BitSet FOLLOW_defaultValuePair_in_dictPairs607 = new BitSet(new long[] { 2L });
/* 1510 */   public static final BitSet FOLLOW_defaultValuePair_in_dictPairs618 = new BitSet(new long[] { 2L });
/* 1511 */   public static final BitSet FOLLOW_25_in_defaultValuePair641 = new BitSet(new long[] { 262144L });
/* 1512 */   public static final BitSet FOLLOW_18_in_defaultValuePair643 = new BitSet(new long[] { 7024L });
/* 1513 */   public static final BitSet FOLLOW_keyValue_in_defaultValuePair645 = new BitSet(new long[] { 2L });
/* 1514 */   public static final BitSet FOLLOW_STRING_in_keyValuePair659 = new BitSet(new long[] { 262144L });
/* 1515 */   public static final BitSet FOLLOW_18_in_keyValuePair661 = new BitSet(new long[] { 7024L });
/* 1516 */   public static final BitSet FOLLOW_keyValue_in_keyValuePair663 = new BitSet(new long[] { 2L });
/* 1517 */   public static final BitSet FOLLOW_BIGSTRING_in_keyValue680 = new BitSet(new long[] { 2L });
/* 1518 */   public static final BitSet FOLLOW_BIGSTRING_NO_NL_in_keyValue689 = new BitSet(new long[] { 2L });
/* 1519 */   public static final BitSet FOLLOW_ANONYMOUS_TEMPLATE_in_keyValue697 = new BitSet(new long[] { 2L });
/* 1520 */   public static final BitSet FOLLOW_STRING_in_keyValue704 = new BitSet(new long[] { 2L });
/* 1521 */   public static final BitSet FOLLOW_TRUE_in_keyValue714 = new BitSet(new long[] { 2L });
/* 1522 */   public static final BitSet FOLLOW_FALSE_in_keyValue724 = new BitSet(new long[] { 2L });
/* 1523 */   public static final BitSet FOLLOW_ID_in_keyValue737 = new BitSet(new long[] { 2L });
/*      */ 
/*      */   public Parser[] getDelegates()
/*      */   {
/*   81 */     return new Parser[0];
/*      */   }
/*      */ 
/*      */   public GroupParser(TokenStream input)
/*      */   {
/*   88 */     this(input, new RecognizerSharedState());
/*      */   }
/*      */   public GroupParser(TokenStream input, RecognizerSharedState state) {
/*   91 */     super(input, state);
/*      */   }
/*      */   public String[] getTokenNames() {
/*   94 */     return tokenNames; } 
/*   95 */   public String getGrammarFileName() { return "/usr/local/website/st/depot/stringtemplate4/src/org/stringtemplate/v4/compiler/Group.g"; }
/*      */ 
/*      */ 
/*      */   public void displayRecognitionError(String[] tokenNames, RecognitionException e)
/*      */   {
/*  103 */     String msg = getErrorMessage(e, tokenNames);
/*  104 */     this.group.errMgr.groupSyntaxError(ErrorType.SYNTAX_ERROR, getSourceName(), e, msg);
/*      */   }
/*      */   public String getSourceName() {
/*  107 */     String fullFileName = super.getSourceName();
/*  108 */     File f = new File(fullFileName);
/*  109 */     return f.getName();
/*      */   }
/*      */   public void error(String msg) {
/*  112 */     NoViableAltException e = new NoViableAltException("", 0, 0, this.input);
/*  113 */     this.group.errMgr.groupSyntaxError(ErrorType.SYNTAX_ERROR, getSourceName(), e, msg);
/*  114 */     recover(this.input, null);
/*      */   }
/*      */ 
/*      */   public final void group(STGroup group, String prefix)
/*      */     throws RecognitionException
/*      */   {
/*  122 */     Token STRING1 = null;
/*      */ 
/*  125 */     GroupLexer lexer = (GroupLexer)this.input.getTokenSource();
/*  126 */     this.group = (lexer.group = group);
/*      */     try
/*      */     {
/*  133 */       int alt1 = 2;
/*  134 */       int LA1_0 = this.input.LA(1);
/*      */ 
/*  136 */       if (LA1_0 == 27) {
/*  137 */         alt1 = 1;
/*      */       }
/*  139 */       switch (alt1)
/*      */       {
/*      */       case 1:
/*  143 */         pushFollow(FOLLOW_oldStyleHeader_in_group76);
/*  144 */         oldStyleHeader();
/*      */ 
/*  146 */         this.state._fsp -= 1;
/*      */       }
/*      */ 
/*  156 */       int alt2 = 2;
/*  157 */       int LA2_0 = this.input.LA(1);
/*      */ 
/*  159 */       if (LA2_0 == 26) {
/*  160 */         alt2 = 1;
/*      */       }
/*  162 */       switch (alt2)
/*      */       {
/*      */       case 1:
/*  166 */         pushFollow(FOLLOW_delimiters_in_group81);
/*  167 */         delimiters();
/*      */ 
/*  169 */         this.state._fsp -= 1;
/*      */       }
/*      */ 
/*      */       while (true)
/*      */       {
/*  181 */         int alt4 = 3;
/*  182 */         int LA4_0 = this.input.LA(1);
/*      */ 
/*  184 */         if (LA4_0 == 29) {
/*  185 */           int LA4_2 = this.input.LA(2);
/*      */ 
/*  187 */           if (LA4_2 == 11) {
/*  188 */             alt4 = 1;
/*      */           }
/*  190 */           else if (LA4_2 == 9) {
/*  191 */             alt4 = 2;
/*      */           }
/*      */ 
/*      */         }
/*      */ 
/*  198 */         switch (alt4)
/*      */         {
/*      */         case 1:
/*  202 */           match(this.input, 29, FOLLOW_29_in_group91);
/*      */ 
/*  204 */           STRING1 = (Token)match(this.input, 11, FOLLOW_STRING_in_group93);
/*      */ 
/*  206 */           group.importTemplates(STRING1);
/*      */ 
/*  209 */           break;
/*      */         case 2:
/*  213 */           match(this.input, 29, FOLLOW_29_in_group101);
/*      */ 
/*  216 */           MismatchedTokenException e = new MismatchedTokenException(11, this.input);
/*  217 */           reportError(e);
/*      */ 
/*  220 */           match(this.input, 9, FOLLOW_ID_in_group112);
/*      */           while (true)
/*      */           {
/*  225 */             int alt3 = 2;
/*  226 */             int LA3_0 = this.input.LA(1);
/*      */ 
/*  228 */             if (LA3_0 == 17) {
/*  229 */               alt3 = 1;
/*      */             }
/*      */ 
/*  233 */             switch (alt3)
/*      */             {
/*      */             case 1:
/*  237 */               match(this.input, 17, FOLLOW_17_in_group115);
/*      */ 
/*  239 */               match(this.input, 9, FOLLOW_ID_in_group117);
/*      */ 
/*  242 */               break;
/*      */             default:
/*  245 */               break label417;
/*      */             }
/*      */ 
/*      */           }
/*      */ 
/*  251 */           break;
/*      */         default:
/*  254 */           label417: break label426;
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/*  260 */       label426: int cnt5 = 0;
/*      */       while (true)
/*      */       {
/*  263 */         int alt5 = 2;
/*  264 */         int LA5_0 = this.input.LA(1);
/*      */ 
/*  266 */         if ((LA5_0 == 9) || (LA5_0 == 22)) {
/*  267 */           alt5 = 1;
/*      */         }
/*      */ 
/*  271 */         switch (alt5)
/*      */         {
/*      */         case 1:
/*  275 */           pushFollow(FOLLOW_def_in_group135);
/*  276 */           def(prefix);
/*      */ 
/*  278 */           this.state._fsp -= 1;
/*      */ 
/*  282 */           break;
/*      */         default:
/*  285 */           if (cnt5 >= 1) return;
/*  286 */           EarlyExitException eee = new EarlyExitException(5, this.input);
/*      */ 
/*  288 */           throw eee;
/*      */         }
/*  290 */         cnt5++;
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (RecognitionException re)
/*      */     {
/*  297 */       re = 
/*  304 */         re;
/*      */ 
/*  298 */       reportError(re);
/*  299 */       recover(this.input, re);
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public final void oldStyleHeader()
/*      */     throws RecognitionException
/*      */   {
/*      */     try
/*      */     {
/*  318 */       match(this.input, 27, FOLLOW_27_in_oldStyleHeader157);
/*      */ 
/*  320 */       match(this.input, 9, FOLLOW_ID_in_oldStyleHeader159);
/*      */ 
/*  323 */       int alt6 = 2;
/*  324 */       int LA6_0 = this.input.LA(1);
/*      */ 
/*  326 */       if (LA6_0 == 18) {
/*  327 */         alt6 = 1;
/*      */       }
/*  329 */       switch (alt6)
/*      */       {
/*      */       case 1:
/*  333 */         match(this.input, 18, FOLLOW_18_in_oldStyleHeader163);
/*      */ 
/*  335 */         match(this.input, 9, FOLLOW_ID_in_oldStyleHeader165);
/*      */       }
/*      */ 
/*  344 */       int alt8 = 2;
/*  345 */       int LA8_0 = this.input.LA(1);
/*      */ 
/*  347 */       if (LA8_0 == 28) {
/*  348 */         alt8 = 1;
/*      */       }
/*  350 */       switch (alt8)
/*      */       {
/*      */       case 1:
/*  354 */         match(this.input, 28, FOLLOW_28_in_oldStyleHeader177);
/*      */ 
/*  356 */         match(this.input, 9, FOLLOW_ID_in_oldStyleHeader179);
/*      */         while (true)
/*      */         {
/*  361 */           int alt7 = 2;
/*  362 */           int LA7_0 = this.input.LA(1);
/*      */ 
/*  364 */           if (LA7_0 == 16) {
/*  365 */             alt7 = 1;
/*      */           }
/*      */ 
/*  369 */           switch (alt7)
/*      */           {
/*      */           case 1:
/*  373 */             match(this.input, 16, FOLLOW_16_in_oldStyleHeader182);
/*      */ 
/*  375 */             match(this.input, 9, FOLLOW_ID_in_oldStyleHeader184);
/*      */ 
/*  378 */             break;
/*      */           default:
/*  381 */             break label249;
/*      */           }
/*      */ 
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/*  392 */       label249: match(this.input, 20, FOLLOW_20_in_oldStyleHeader196);
/*      */     }
/*      */     catch (RecognitionException re)
/*      */     {
/*  397 */       re = 
/*  404 */         re;
/*      */ 
/*  398 */       reportError(re);
/*  399 */       recover(this.input, re);
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public final String groupName()
/*      */     throws RecognitionException
/*      */   {
/*  414 */     String name = null;
/*      */ 
/*  417 */     Token a = null;
/*      */ 
/*  419 */     StringBuilder buf = new StringBuilder();
/*      */     try
/*      */     {
/*  424 */       a = (Token)match(this.input, 9, FOLLOW_ID_in_groupName218);
/*      */ 
/*  426 */       buf.append(a != null ? a.getText() : null);
/*      */       while (true)
/*      */       {
/*  431 */         int alt9 = 2;
/*  432 */         int LA9_0 = this.input.LA(1);
/*      */ 
/*  434 */         if (LA9_0 == 17) {
/*  435 */           alt9 = 1;
/*      */         }
/*      */ 
/*  439 */         switch (alt9)
/*      */         {
/*      */         case 1:
/*  443 */           match(this.input, 17, FOLLOW_17_in_groupName223);
/*      */ 
/*  445 */           a = (Token)match(this.input, 9, FOLLOW_ID_in_groupName227);
/*      */ 
/*  447 */           buf.append(a != null ? a.getText() : null);
/*      */ 
/*  450 */           break;
/*      */         default:
/*  453 */           break label151;
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (RecognitionException re)
/*      */     {
/*  461 */       label151: re = 
/*  468 */         re;
/*      */ 
/*  462 */       reportError(re);
/*  463 */       recover(this.input, re);
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*      */ 
/*  469 */     return name;
/*      */   }
/*      */ 
/*      */   public final void delimiters()
/*      */     throws RecognitionException
/*      */   {
/*  478 */     Token a = null;
/*  479 */     Token b = null;
/*      */     try
/*      */     {
/*  485 */       match(this.input, 26, FOLLOW_26_in_delimiters245);
/*      */ 
/*  487 */       a = (Token)match(this.input, 11, FOLLOW_STRING_in_delimiters249);
/*      */ 
/*  489 */       match(this.input, 16, FOLLOW_16_in_delimiters251);
/*      */ 
/*  491 */       b = (Token)match(this.input, 11, FOLLOW_STRING_in_delimiters255);
/*      */ 
/*  494 */       this.group.delimiterStartChar = a.getText().charAt(1);
/*  495 */       this.group.delimiterStopChar = b.getText().charAt(1);
/*      */     }
/*      */     catch (RecognitionException re)
/*      */     {
/*  501 */       re = 
/*  508 */         re;
/*      */ 
/*  502 */       reportError(re);
/*  503 */       recover(this.input, re);
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public final void def(String prefix)
/*      */     throws RecognitionException
/*      */   {
/*      */     try
/*      */     {
/*  520 */       int alt10 = 2;
/*  521 */       int LA10_0 = this.input.LA(1);
/*      */ 
/*  523 */       if (LA10_0 == 22) {
/*  524 */         alt10 = 1;
/*      */       }
/*  526 */       else if (LA10_0 == 9) {
/*  527 */         int LA10_2 = this.input.LA(2);
/*      */ 
/*  529 */         if (LA10_2 == 14) {
/*  530 */           alt10 = 1;
/*      */         }
/*  532 */         else if (LA10_2 == 19) {
/*  533 */           int LA10_3 = this.input.LA(3);
/*      */ 
/*  535 */           if (LA10_3 == 9) {
/*  536 */             alt10 = 1;
/*      */           }
/*  538 */           else if (LA10_3 == 23) {
/*  539 */             alt10 = 2;
/*      */           }
/*      */           else {
/*  542 */             NoViableAltException nvae = new NoViableAltException("", 10, 3, this.input);
/*      */ 
/*  545 */             throw nvae;
/*      */           }
/*      */         }
/*      */         else
/*      */         {
/*  550 */           NoViableAltException nvae = new NoViableAltException("", 10, 2, this.input);
/*      */ 
/*  553 */           throw nvae;
/*      */         }
/*      */       }
/*      */       else
/*      */       {
/*  558 */         NoViableAltException nvae = new NoViableAltException("", 10, 0, this.input);
/*      */ 
/*  561 */         throw nvae;
/*      */       }
/*      */ 
/*  564 */       switch (alt10)
/*      */       {
/*      */       case 1:
/*  568 */         pushFollow(FOLLOW_templateDef_in_def279);
/*  569 */         templateDef(prefix);
/*      */ 
/*  571 */         this.state._fsp -= 1;
/*      */ 
/*  575 */         break;
/*      */       case 2:
/*  579 */         pushFollow(FOLLOW_dictDef_in_def284);
/*  580 */         dictDef();
/*      */ 
/*  582 */         this.state._fsp -= 1;
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (RecognitionException re)
/*      */     {
/*  590 */       re = 
/*  600 */         re;
/*      */ 
/*  593 */       this.state.lastErrorIndex = this.input.index();
/*  594 */       error("garbled template definition starting at '" + this.input.LT(1).getText() + "'");
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public final void templateDef(String prefix)
/*      */     throws RecognitionException
/*      */   {
/*  610 */     Token enclosing = null;
/*  611 */     Token name = null;
/*  612 */     Token alias = null;
/*  613 */     Token target = null;
/*  614 */     Token STRING2 = null;
/*  615 */     Token BIGSTRING3 = null;
/*  616 */     Token BIGSTRING_NO_NL4 = null;
/*  617 */     List formalArgs5 = null;
/*      */ 
/*  621 */     String template = null;
/*  622 */     int n = 0;
/*      */     try
/*      */     {
/*  626 */       int alt13 = 2;
/*  627 */       int LA13_0 = this.input.LA(1);
/*      */ 
/*  629 */       if (LA13_0 == 22) {
/*  630 */         alt13 = 1;
/*      */       }
/*  632 */       else if (LA13_0 == 9) {
/*  633 */         int LA13_2 = this.input.LA(2);
/*      */ 
/*  635 */         if (LA13_2 == 14) {
/*  636 */           alt13 = 1;
/*      */         }
/*  638 */         else if (LA13_2 == 19) {
/*  639 */           alt13 = 2;
/*      */         }
/*      */         else {
/*  642 */           NoViableAltException nvae = new NoViableAltException("", 13, 2, this.input);
/*      */ 
/*  645 */           throw nvae;
/*      */         }
/*      */       }
/*      */       else
/*      */       {
/*  650 */         NoViableAltException nvae = new NoViableAltException("", 13, 0, this.input);
/*      */ 
/*  653 */         throw nvae;
/*      */       }
/*      */ 
/*  656 */       switch (alt13)
/*      */       {
/*      */       case 1:
/*  661 */         int alt11 = 2;
/*  662 */         int LA11_0 = this.input.LA(1);
/*      */ 
/*  664 */         if (LA11_0 == 22) {
/*  665 */           alt11 = 1;
/*      */         }
/*  667 */         else if (LA11_0 == 9) {
/*  668 */           alt11 = 2;
/*      */         }
/*      */         else {
/*  671 */           NoViableAltException nvae = new NoViableAltException("", 11, 0, this.input);
/*      */ 
/*  674 */           throw nvae;
/*      */         }
/*      */ 
/*  677 */         switch (alt11)
/*      */         {
/*      */         case 1:
/*  681 */           match(this.input, 22, FOLLOW_22_in_templateDef308);
/*      */ 
/*  683 */           enclosing = (Token)match(this.input, 9, FOLLOW_ID_in_templateDef312);
/*      */ 
/*  685 */           match(this.input, 17, FOLLOW_17_in_templateDef314);
/*      */ 
/*  687 */           name = (Token)match(this.input, 9, FOLLOW_ID_in_templateDef318);
/*      */ 
/*  689 */           match(this.input, 14, FOLLOW_14_in_templateDef320);
/*      */ 
/*  691 */           match(this.input, 15, FOLLOW_15_in_templateDef322);
/*      */ 
/*  694 */           break;
/*      */         case 2:
/*  698 */           name = (Token)match(this.input, 9, FOLLOW_ID_in_templateDef330);
/*      */ 
/*  700 */           match(this.input, 14, FOLLOW_14_in_templateDef332);
/*      */ 
/*  702 */           pushFollow(FOLLOW_formalArgs_in_templateDef334);
/*  703 */           formalArgs5 = formalArgs();
/*      */ 
/*  705 */           this.state._fsp -= 1;
/*      */ 
/*  708 */           match(this.input, 15, FOLLOW_15_in_templateDef336);
/*      */         }
/*      */ 
/*  716 */         match(this.input, 19, FOLLOW_19_in_templateDef347);
/*      */ 
/*  718 */         Token templateToken = this.input.LT(1);
/*      */ 
/*  721 */         int alt12 = 4;
/*  722 */         switch (this.input.LA(1))
/*      */         {
/*      */         case 11:
/*  725 */           alt12 = 1;
/*      */ 
/*  727 */           break;
/*      */         case 5:
/*  730 */           alt12 = 2;
/*      */ 
/*  732 */           break;
/*      */         case 6:
/*  735 */           alt12 = 3;
/*      */ 
/*  737 */           break;
/*      */         case -1:
/*      */         case 9:
/*      */         case 22:
/*  742 */           alt12 = 4;
/*      */ 
/*  744 */           break;
/*      */         default:
/*  746 */           NoViableAltException nvae = new NoViableAltException("", 12, 0, this.input);
/*      */ 
/*  749 */           throw nvae;
/*      */         }
/*      */ 
/*  753 */         switch (alt12)
/*      */         {
/*      */         case 1:
/*  757 */           STRING2 = (Token)match(this.input, 11, FOLLOW_STRING_in_templateDef363);
/*      */ 
/*  759 */           template = STRING2 != null ? STRING2.getText() : null; n = 1;
/*      */ 
/*  762 */           break;
/*      */         case 2:
/*  766 */           BIGSTRING3 = (Token)match(this.input, 5, FOLLOW_BIGSTRING_in_templateDef378);
/*      */ 
/*  768 */           template = BIGSTRING3 != null ? BIGSTRING3.getText() : null; n = 2;
/*      */ 
/*  771 */           break;
/*      */         case 3:
/*  775 */           BIGSTRING_NO_NL4 = (Token)match(this.input, 6, FOLLOW_BIGSTRING_NO_NL_in_templateDef390);
/*      */ 
/*  777 */           template = BIGSTRING_NO_NL4 != null ? BIGSTRING_NO_NL4.getText() : null; n = 2;
/*      */ 
/*  780 */           break;
/*      */         case 4:
/*  785 */           template = "";
/*  786 */           String msg = "missing template at '" + this.input.LT(1).getText() + "'";
/*  787 */           NoViableAltException e = new NoViableAltException("", 0, 0, this.input);
/*  788 */           this.group.errMgr.groupSyntaxError(ErrorType.SYNTAX_ERROR, getSourceName(), e, msg);
/*      */         }
/*      */ 
/*  798 */         if ((name != null ? name.getTokenIndex() : 0) >= 0) {
/*  799 */           template = Misc.strip(template, n);
/*  800 */           String templateName = name != null ? name.getText() : null;
/*  801 */           if (prefix.length() > 0) templateName = prefix + (name != null ? name.getText() : null);
/*  802 */           this.group.defineTemplateOrRegion(templateName, enclosing != null ? enclosing.getText() : null, templateToken, template, name, formalArgs5);
/*      */         }
/*      */ 
/*  808 */         break;
/*      */       case 2:
/*  812 */         alias = (Token)match(this.input, 9, FOLLOW_ID_in_templateDef425);
/*      */ 
/*  814 */         match(this.input, 19, FOLLOW_19_in_templateDef427);
/*      */ 
/*  816 */         target = (Token)match(this.input, 9, FOLLOW_ID_in_templateDef431);
/*      */ 
/*  818 */         this.group.defineTemplateAlias(alias, target);
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (RecognitionException re)
/*      */     {
/*  825 */       re = 
/*  832 */         re;
/*      */ 
/*  826 */       reportError(re);
/*  827 */       recover(this.input, re);
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public final List<FormalArgument> formalArgs()
/*      */     throws RecognitionException
/*      */   {
/*  848 */     this.formalArgs_stack.push(new formalArgs_scope());
/*  849 */     List args = new ArrayList();
/*      */ 
/*  852 */     ((formalArgs_scope)this.formalArgs_stack.peek()).hasOptionalParameter = false;
/*      */     try
/*      */     {
/*  855 */       int alt15 = 2;
/*  856 */       int LA15_0 = this.input.LA(1);
/*      */ 
/*  858 */       if (LA15_0 == 9) {
/*  859 */         alt15 = 1;
/*      */       }
/*  861 */       else if (LA15_0 == 15) {
/*  862 */         alt15 = 2;
/*      */       }
/*      */       else {
/*  865 */         NoViableAltException nvae = new NoViableAltException("", 15, 0, this.input);
/*      */ 
/*  868 */         throw nvae;
/*      */       }
/*      */ 
/*  871 */       switch (alt15)
/*      */       {
/*      */       case 1:
/*  875 */         pushFollow(FOLLOW_formalArg_in_formalArgs457);
/*  876 */         formalArg(args);
/*      */ 
/*  878 */         this.state._fsp -= 1;
/*      */         while (true)
/*      */         {
/*  884 */           int alt14 = 2;
/*  885 */           int LA14_0 = this.input.LA(1);
/*      */ 
/*  887 */           if (LA14_0 == 16) {
/*  888 */             alt14 = 1;
/*      */           }
/*      */ 
/*  892 */           switch (alt14)
/*      */           {
/*      */           case 1:
/*  896 */             match(this.input, 16, FOLLOW_16_in_formalArgs461);
/*      */ 
/*  898 */             pushFollow(FOLLOW_formalArg_in_formalArgs463);
/*  899 */             formalArg(args);
/*      */ 
/*  901 */             this.state._fsp -= 1;
/*      */ 
/*  905 */             break;
/*      */           default:
/*  908 */             break label240;
/*      */           }
/*      */ 
/*      */         }
/*      */ 
/*      */       case 2:
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (RecognitionException re)
/*      */     {
/*  924 */       label240: reportError(re);
/*  925 */       recover(this.input, re);
/*      */     }
/*      */     finally
/*      */     {
/*  930 */       this.formalArgs_stack.pop();
/*      */     }
/*  932 */     return args;
/*      */   }
/*      */ 
/*      */   public final void formalArg(List<FormalArgument> args)
/*      */     throws RecognitionException
/*      */   {
/*  941 */     Token a = null;
/*  942 */     Token ID6 = null;
/*      */     try
/*      */     {
/*  948 */       ID6 = (Token)match(this.input, 9, FOLLOW_ID_in_formalArg481);
/*      */ 
/*  951 */       int alt16 = 2;
/*  952 */       int LA16_0 = this.input.LA(1);
/*      */ 
/*  954 */       if (LA16_0 == 21) {
/*  955 */         alt16 = 1;
/*      */       }
/*  957 */       else if ((LA16_0 >= 15) && (LA16_0 <= 16)) {
/*  958 */         alt16 = 2;
/*      */       }
/*      */       else {
/*  961 */         NoViableAltException nvae = new NoViableAltException("", 16, 0, this.input);
/*      */ 
/*  964 */         throw nvae;
/*      */       }
/*      */ 
/*  967 */       switch (alt16)
/*      */       {
/*      */       case 1:
/*  971 */         match(this.input, 21, FOLLOW_21_in_formalArg487);
/*      */ 
/*  973 */         a = this.input.LT(1);
/*      */ 
/*  975 */         if ((this.input.LA(1) == 4) || (this.input.LA(1) == 8) || ((this.input.LA(1) >= 11) && (this.input.LA(1) <= 12))) {
/*  976 */           this.input.consume();
/*  977 */           this.state.errorRecovery = false;
/*      */         }
/*      */         else {
/*  980 */           MismatchedSetException mse = new MismatchedSetException(null, this.input);
/*  981 */           throw mse;
/*      */         }
/*      */ 
/*  985 */         ((formalArgs_scope)this.formalArgs_stack.peek()).hasOptionalParameter = true;
/*      */ 
/*  988 */         break;
/*      */       case 2:
/*  993 */         if (((formalArgs_scope)this.formalArgs_stack.peek()).hasOptionalParameter) {
/*  994 */           this.group.errMgr.compileTimeError(ErrorType.REQUIRED_PARAMETER_AFTER_OPTIONAL, null, ID6);
/*      */         }
/*      */ 
/*      */         break;
/*      */       }
/*      */ 
/* 1005 */       args.add(new FormalArgument(ID6 != null ? ID6.getText() : null, a));
/*      */     }
/*      */     catch (RecognitionException re)
/*      */     {
/* 1010 */       re = 
/* 1017 */         re;
/*      */ 
/* 1011 */       reportError(re);
/* 1012 */       recover(this.input, re);
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public final void dictDef()
/*      */     throws RecognitionException
/*      */   {
/* 1027 */     Token ID7 = null;
/* 1028 */     Map dict8 = null;
/*      */     try
/*      */     {
/* 1035 */       ID7 = (Token)match(this.input, 9, FOLLOW_ID_in_dictDef532);
/*      */ 
/* 1037 */       match(this.input, 19, FOLLOW_19_in_dictDef534);
/*      */ 
/* 1039 */       pushFollow(FOLLOW_dict_in_dictDef536);
/* 1040 */       dict8 = dict();
/*      */ 
/* 1042 */       this.state._fsp -= 1;
/*      */ 
/* 1046 */       if (this.group.rawGetDictionary(ID7 != null ? ID7.getText() : null) != null) {
/* 1047 */         this.group.errMgr.compileTimeError(ErrorType.MAP_REDEFINITION, null, ID7);
/*      */       }
/* 1049 */       else if (this.group.rawGetTemplate(ID7 != null ? ID7.getText() : null) != null) {
/* 1050 */         this.group.errMgr.compileTimeError(ErrorType.TEMPLATE_REDEFINITION_AS_MAP, null, ID7);
/*      */       }
/*      */       else {
/* 1053 */         this.group.defineDictionary(ID7 != null ? ID7.getText() : null, dict8);
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (RecognitionException re)
/*      */     {
/* 1060 */       re = 
/* 1067 */         re;
/*      */ 
/* 1061 */       reportError(re);
/* 1062 */       recover(this.input, re);
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public final Map<String, Object> dict()
/*      */     throws RecognitionException
/*      */   {
/* 1077 */     Map mapping = null;
/*      */ 
/* 1080 */     mapping = new HashMap();
/*      */     try
/*      */     {
/* 1085 */       match(this.input, 23, FOLLOW_23_in_dict568);
/*      */ 
/* 1087 */       pushFollow(FOLLOW_dictPairs_in_dict570);
/* 1088 */       dictPairs(mapping);
/*      */ 
/* 1090 */       this.state._fsp -= 1;
/*      */ 
/* 1093 */       match(this.input, 24, FOLLOW_24_in_dict573);
/*      */     }
/*      */     catch (RecognitionException re)
/*      */     {
/* 1098 */       re = 
/* 1105 */         re;
/*      */ 
/* 1099 */       reportError(re);
/* 1100 */       recover(this.input, re);
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*      */ 
/* 1106 */     return mapping;
/*      */   }
/*      */ 
/*      */   public final void dictPairs(Map<String, Object> mapping)
/*      */     throws RecognitionException
/*      */   {
/*      */     try
/*      */     {
/* 1117 */       int alt19 = 2;
/* 1118 */       int LA19_0 = this.input.LA(1);
/*      */ 
/* 1120 */       if (LA19_0 == 11) {
/* 1121 */         alt19 = 1;
/*      */       }
/* 1123 */       else if (LA19_0 == 25) {
/* 1124 */         alt19 = 2;
/*      */       }
/*      */       else {
/* 1127 */         NoViableAltException nvae = new NoViableAltException("", 19, 0, this.input);
/*      */ 
/* 1130 */         throw nvae;
/*      */       }
/*      */ 
/* 1133 */       switch (alt19)
/*      */       {
/*      */       case 1:
/* 1137 */         pushFollow(FOLLOW_keyValuePair_in_dictPairs588);
/* 1138 */         keyValuePair(mapping);
/*      */ 
/* 1140 */         this.state._fsp -= 1;
/*      */         while (true)
/*      */         {
/* 1146 */           int alt17 = 2;
/* 1147 */           int LA17_0 = this.input.LA(1);
/*      */ 
/* 1149 */           if (LA17_0 == 16) {
/* 1150 */             int LA17_1 = this.input.LA(2);
/*      */ 
/* 1152 */             if (LA17_1 == 11) {
/* 1153 */               alt17 = 1;
/*      */             }
/*      */ 
/*      */           }
/*      */ 
/* 1160 */           switch (alt17)
/*      */           {
/*      */           case 1:
/* 1164 */             match(this.input, 16, FOLLOW_16_in_dictPairs597);
/*      */ 
/* 1166 */             pushFollow(FOLLOW_keyValuePair_in_dictPairs599);
/* 1167 */             keyValuePair(mapping);
/*      */ 
/* 1169 */             this.state._fsp -= 1;
/*      */ 
/* 1173 */             break;
/*      */           default:
/* 1176 */             break label220;
/*      */           }
/*      */ 
/*      */         }
/*      */ 
/* 1182 */         int alt18 = 2;
/* 1183 */         int LA18_0 = this.input.LA(1);
/*      */ 
/* 1185 */         if (LA18_0 == 16) {
/* 1186 */           alt18 = 1;
/*      */         }
/* 1188 */         switch (alt18)
/*      */         {
/*      */         case 1:
/* 1192 */           match(this.input, 16, FOLLOW_16_in_dictPairs605);
/*      */ 
/* 1194 */           pushFollow(FOLLOW_defaultValuePair_in_dictPairs607);
/* 1195 */           defaultValuePair(mapping);
/*      */ 
/* 1197 */           this.state._fsp -= 1;
/*      */         }
/*      */ 
/* 1207 */         break;
/*      */       case 2:
/* 1211 */         label220: pushFollow(FOLLOW_defaultValuePair_in_dictPairs618);
/* 1212 */         defaultValuePair(mapping);
/*      */ 
/* 1214 */         this.state._fsp -= 1;
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (RecognitionException re)
/*      */     {
/* 1222 */       re = 
/* 1230 */         re;
/*      */ 
/* 1224 */       error("missing dictionary entry at '" + this.input.LT(1).getText() + "'");
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public final void defaultValuePair(Map<String, Object> mapping)
/*      */     throws RecognitionException
/*      */   {
/* 1240 */     Object keyValue9 = null;
/*      */     try
/*      */     {
/* 1247 */       match(this.input, 25, FOLLOW_25_in_defaultValuePair641);
/*      */ 
/* 1249 */       match(this.input, 18, FOLLOW_18_in_defaultValuePair643);
/*      */ 
/* 1251 */       pushFollow(FOLLOW_keyValue_in_defaultValuePair645);
/* 1252 */       keyValue9 = keyValue();
/*      */ 
/* 1254 */       this.state._fsp -= 1;
/*      */ 
/* 1257 */       mapping.put("default", keyValue9);
/*      */     }
/*      */     catch (RecognitionException re)
/*      */     {
/* 1262 */       re = 
/* 1269 */         re;
/*      */ 
/* 1263 */       reportError(re);
/* 1264 */       recover(this.input, re);
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public final void keyValuePair(Map<String, Object> mapping)
/*      */     throws RecognitionException
/*      */   {
/* 1279 */     Token STRING10 = null;
/* 1280 */     Object keyValue11 = null;
/*      */     try
/*      */     {
/* 1287 */       STRING10 = (Token)match(this.input, 11, FOLLOW_STRING_in_keyValuePair659);
/*      */ 
/* 1289 */       match(this.input, 18, FOLLOW_18_in_keyValuePair661);
/*      */ 
/* 1291 */       pushFollow(FOLLOW_keyValue_in_keyValuePair663);
/* 1292 */       keyValue11 = keyValue();
/*      */ 
/* 1294 */       this.state._fsp -= 1;
/*      */ 
/* 1297 */       mapping.put(Misc.replaceEscapes(Misc.strip(STRING10 != null ? STRING10.getText() : null, 1)), keyValue11);
/*      */     }
/*      */     catch (RecognitionException re)
/*      */     {
/* 1302 */       re = 
/* 1309 */         re;
/*      */ 
/* 1303 */       reportError(re);
/* 1304 */       recover(this.input, re);
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public final Object keyValue()
/*      */     throws RecognitionException
/*      */   {
/* 1319 */     Object value = null;
/*      */ 
/* 1322 */     Token BIGSTRING12 = null;
/* 1323 */     Token BIGSTRING_NO_NL13 = null;
/* 1324 */     Token ANONYMOUS_TEMPLATE14 = null;
/* 1325 */     Token STRING15 = null;
/*      */     try
/*      */     {
/* 1329 */       int alt20 = 7;
/* 1330 */       int LA20_0 = this.input.LA(1);
/*      */ 
/* 1332 */       if (LA20_0 == 5) {
/* 1333 */         alt20 = 1;
/*      */       }
/* 1335 */       else if (LA20_0 == 6) {
/* 1336 */         alt20 = 2;
/*      */       }
/* 1338 */       else if (LA20_0 == 4) {
/* 1339 */         alt20 = 3;
/*      */       }
/* 1341 */       else if (LA20_0 == 11) {
/* 1342 */         alt20 = 4;
/*      */       }
/* 1344 */       else if (LA20_0 == 12) {
/* 1345 */         alt20 = 5;
/*      */       }
/* 1347 */       else if (LA20_0 == 8) {
/* 1348 */         alt20 = 6;
/*      */       }
/* 1350 */       else if ((LA20_0 == 9) && (this.input.LT(1).getText().equals("key"))) {
/* 1351 */         alt20 = 7;
/*      */       }
/*      */       else {
/* 1354 */         NoViableAltException nvae = new NoViableAltException("", 20, 0, this.input);
/*      */ 
/* 1357 */         throw nvae;
/*      */       }
/*      */ 
/* 1360 */       switch (alt20)
/*      */       {
/*      */       case 1:
/* 1364 */         BIGSTRING12 = (Token)match(this.input, 5, FOLLOW_BIGSTRING_in_keyValue680);
/*      */ 
/* 1366 */         value = this.group.createSingleton(BIGSTRING12);
/*      */ 
/* 1369 */         break;
/*      */       case 2:
/* 1373 */         BIGSTRING_NO_NL13 = (Token)match(this.input, 6, FOLLOW_BIGSTRING_NO_NL_in_keyValue689);
/*      */ 
/* 1375 */         value = this.group.createSingleton(BIGSTRING_NO_NL13);
/*      */ 
/* 1378 */         break;
/*      */       case 3:
/* 1382 */         ANONYMOUS_TEMPLATE14 = (Token)match(this.input, 4, FOLLOW_ANONYMOUS_TEMPLATE_in_keyValue697);
/*      */ 
/* 1384 */         value = this.group.createSingleton(ANONYMOUS_TEMPLATE14);
/*      */ 
/* 1387 */         break;
/*      */       case 4:
/* 1391 */         STRING15 = (Token)match(this.input, 11, FOLLOW_STRING_in_keyValue704);
/*      */ 
/* 1393 */         value = Misc.replaceEscapes(Misc.strip(STRING15 != null ? STRING15.getText() : null, 1));
/*      */ 
/* 1396 */         break;
/*      */       case 5:
/* 1400 */         match(this.input, 12, FOLLOW_TRUE_in_keyValue714);
/*      */ 
/* 1402 */         value = Boolean.valueOf(true);
/*      */ 
/* 1405 */         break;
/*      */       case 6:
/* 1409 */         match(this.input, 8, FOLLOW_FALSE_in_keyValue724);
/*      */ 
/* 1411 */         value = Boolean.valueOf(false);
/*      */ 
/* 1414 */         break;
/*      */       case 7:
/* 1418 */         if (!this.input.LT(1).getText().equals("key")) {
/* 1419 */           throw new FailedPredicateException(this.input, "keyValue", "input.LT(1).getText().equals(\"key\")");
/*      */         }
/*      */ 
/* 1422 */         match(this.input, 9, FOLLOW_ID_in_keyValue737);
/*      */ 
/* 1424 */         value = "key";
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (RecognitionException re)
/*      */     {
/* 1431 */       re = 
/* 1439 */         re;
/*      */ 
/* 1433 */       error("missing value for key at '" + this.input.LT(1).getText() + "'");
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*      */ 
/* 1440 */     return value;
/*      */   }
/*      */ 
/*      */   protected static class formalArgs_scope
/*      */   {
/*      */     boolean hasOptionalParameter;
/*      */   }
/*      */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.stringtemplate.v4.compiler.GroupParser
 * JD-Core Version:    0.6.2
 */