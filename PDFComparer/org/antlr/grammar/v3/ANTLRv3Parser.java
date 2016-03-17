/*      */ package org.antlr.grammar.v3;
/*      */ 
/*      */ import java.io.PrintStream;
/*      */ import java.util.ArrayList;
/*      */ import java.util.List;
/*      */ import java.util.Stack;
/*      */ import org.antlr.runtime.BaseRecognizer;
/*      */ import org.antlr.runtime.BitSet;
/*      */ import org.antlr.runtime.DFA;
/*      */ import org.antlr.runtime.EarlyExitException;
/*      */ import org.antlr.runtime.IntStream;
/*      */ import org.antlr.runtime.MismatchedSetException;
/*      */ import org.antlr.runtime.NoViableAltException;
/*      */ import org.antlr.runtime.Parser;
/*      */ import org.antlr.runtime.ParserRuleReturnScope;
/*      */ import org.antlr.runtime.RecognitionException;
/*      */ import org.antlr.runtime.RecognizerSharedState;
/*      */ import org.antlr.runtime.RuleReturnScope;
/*      */ import org.antlr.runtime.Token;
/*      */ import org.antlr.runtime.TokenStream;
/*      */ import org.antlr.runtime.tree.CommonTree;
/*      */ import org.antlr.runtime.tree.CommonTreeAdaptor;
/*      */ import org.antlr.runtime.tree.RewriteEarlyExitException;
/*      */ import org.antlr.runtime.tree.RewriteRuleSubtreeStream;
/*      */ import org.antlr.runtime.tree.RewriteRuleTokenStream;
/*      */ import org.antlr.runtime.tree.TreeAdaptor;
/*      */ 
/*      */ public class ANTLRv3Parser extends Parser
/*      */ {
/*   17 */   public static final String[] tokenNames = { "<invalid>", "<EOR>", "<DOWN>", "<UP>", "DOC_COMMENT", "PARSER", "LEXER", "RULE", "BLOCK", "OPTIONAL", "CLOSURE", "POSITIVE_CLOSURE", "SYNPRED", "RANGE", "CHAR_RANGE", "EPSILON", "ALT", "EOR", "EOB", "EOA", "ID", "ARG", "ARGLIST", "RET", "LEXER_GRAMMAR", "PARSER_GRAMMAR", "TREE_GRAMMAR", "COMBINED_GRAMMAR", "LABEL", "TEMPLATE", "SCOPE", "SEMPRED", "GATED_SEMPRED", "SYN_SEMPRED", "BACKTRACK_SEMPRED", "FRAGMENT", "TREE_BEGIN", "ROOT", "BANG", "REWRITE", "AT", "LABEL_ASSIGN", "LIST_LABEL_ASSIGN", "TOKENS", "TOKEN_REF", "STRING_LITERAL", "CHAR_LITERAL", "ACTION", "OPTIONS", "INT", "ARG_ACTION", "RULE_REF", "DOUBLE_QUOTE_STRING_LITERAL", "DOUBLE_ANGLE_STRING_LITERAL", "SRC", "SL_COMMENT", "ML_COMMENT", "LITERAL_CHAR", "ESC", "XDIGIT", "NESTED_ARG_ACTION", "ACTION_STRING_LITERAL", "ACTION_CHAR_LITERAL", "NESTED_ACTION", "ACTION_ESC", "WS_LOOP", "WS", "'lexer'", "'parser'", "'tree'", "'grammar'", "';'", "'}'", "'::'", "'*'", "'protected'", "'public'", "'private'", "':'", "'throws'", "','", "'('", "'|'", "')'", "'catch'", "'finally'", "'=>'", "'~'", "'<'", "'>'", "'.'", "'?'", "'+'", "'$'" };
/*      */   public static final int BACKTRACK_SEMPRED = 34;
/*      */   public static final int DOUBLE_ANGLE_STRING_LITERAL = 53;
/*      */   public static final int LEXER_GRAMMAR = 24;
/*      */   public static final int EOA = 19;
/*      */   public static final int ARGLIST = 22;
/*      */   public static final int EOF = -1;
/*      */   public static final int SEMPRED = 31;
/*      */   public static final int ACTION = 47;
/*      */   public static final int EOB = 18;
/*      */   public static final int TOKEN_REF = 44;
/*      */   public static final int T__93 = 93;
/*      */   public static final int T__91 = 91;
/*      */   public static final int RET = 23;
/*      */   public static final int T__92 = 92;
/*      */   public static final int STRING_LITERAL = 45;
/*      */   public static final int T__90 = 90;
/*      */   public static final int ARG = 21;
/*      */   public static final int EOR = 17;
/*      */   public static final int ARG_ACTION = 50;
/*      */   public static final int DOUBLE_QUOTE_STRING_LITERAL = 52;
/*      */   public static final int NESTED_ARG_ACTION = 60;
/*      */   public static final int ACTION_CHAR_LITERAL = 62;
/*      */   public static final int T__80 = 80;
/*      */   public static final int T__81 = 81;
/*      */   public static final int RULE = 7;
/*      */   public static final int T__82 = 82;
/*      */   public static final int T__83 = 83;
/*      */   public static final int ACTION_ESC = 64;
/*      */   public static final int PARSER_GRAMMAR = 25;
/*      */   public static final int SRC = 54;
/*      */   public static final int CHAR_RANGE = 14;
/*      */   public static final int INT = 49;
/*      */   public static final int EPSILON = 15;
/*      */   public static final int T__85 = 85;
/*      */   public static final int T__84 = 84;
/*      */   public static final int T__87 = 87;
/*      */   public static final int T__86 = 86;
/*      */   public static final int T__89 = 89;
/*      */   public static final int REWRITE = 39;
/*      */   public static final int T__88 = 88;
/*      */   public static final int WS = 66;
/*      */   public static final int T__71 = 71;
/*      */   public static final int T__72 = 72;
/*      */   public static final int COMBINED_GRAMMAR = 27;
/*      */   public static final int T__70 = 70;
/*      */   public static final int LEXER = 6;
/*      */   public static final int SL_COMMENT = 55;
/*      */   public static final int TREE_GRAMMAR = 26;
/*      */   public static final int T__76 = 76;
/*      */   public static final int CLOSURE = 10;
/*      */   public static final int T__75 = 75;
/*      */   public static final int PARSER = 5;
/*      */   public static final int T__74 = 74;
/*      */   public static final int T__73 = 73;
/*      */   public static final int T__79 = 79;
/*      */   public static final int T__78 = 78;
/*      */   public static final int T__77 = 77;
/*      */   public static final int T__68 = 68;
/*      */   public static final int T__69 = 69;
/*      */   public static final int T__67 = 67;
/*      */   public static final int NESTED_ACTION = 63;
/*      */   public static final int ESC = 58;
/*      */   public static final int FRAGMENT = 35;
/*      */   public static final int ID = 20;
/*      */   public static final int TREE_BEGIN = 36;
/*      */   public static final int AT = 40;
/*      */   public static final int ML_COMMENT = 56;
/*      */   public static final int ALT = 16;
/*      */   public static final int SCOPE = 30;
/*      */   public static final int LABEL_ASSIGN = 41;
/*      */   public static final int DOC_COMMENT = 4;
/*      */   public static final int WS_LOOP = 65;
/*      */   public static final int RANGE = 13;
/*      */   public static final int TOKENS = 43;
/*      */   public static final int GATED_SEMPRED = 32;
/*      */   public static final int LITERAL_CHAR = 57;
/*      */   public static final int BANG = 38;
/*      */   public static final int LIST_LABEL_ASSIGN = 42;
/*      */   public static final int ACTION_STRING_LITERAL = 61;
/*      */   public static final int ROOT = 37;
/*      */   public static final int RULE_REF = 51;
/*      */   public static final int SYNPRED = 12;
/*      */   public static final int OPTIONAL = 9;
/*      */   public static final int CHAR_LITERAL = 46;
/*      */   public static final int LABEL = 28;
/*      */   public static final int TEMPLATE = 29;
/*      */   public static final int SYN_SEMPRED = 33;
/*      */   public static final int XDIGIT = 59;
/*      */   public static final int BLOCK = 8;
/*      */   public static final int POSITIVE_CLOSURE = 11;
/*      */   public static final int OPTIONS = 48;
/*  124 */   protected TreeAdaptor adaptor = new CommonTreeAdaptor();
/*      */   int gtype;
/* 1682 */   protected Stack rule_stack = new Stack();
/*      */ 
/* 9371 */   protected DFA46 dfa46 = new DFA46(this);
/* 9372 */   protected DFA73 dfa73 = new DFA73(this);
/* 9373 */   protected DFA76 dfa76 = new DFA76(this);
/* 9374 */   protected DFA81 dfa81 = new DFA81(this);
/*      */   static final String DFA46_eotS = "\fð¿¿";
/*      */   static final String DFA46_eofS = "\fð¿¿";
/*      */   static final String DFA46_minS = "\003\037\005ð¿¿\002,\002ð¿¿";
/*      */   static final String DFA46_maxS = "\001Z\002\\\005ð¿¿\002Z\002ð¿¿";
/*      */   static final String DFA46_acceptS = "\003ð¿¿\001\003\001\004\001\005\001\006\001\007\002ð¿¿\001\002\001\001";
/*      */   static final String DFA46_specialS = "\fð¿¿}>";
/* 9387 */   static final String[] DFA46_transitionS = { "\001\006\004ð¿¿\001\007\007ð¿¿\001\001\002\003\001\005\003ð¿¿\001\002\035ð¿¿\001\004\005ð¿¿\001\003\002ð¿¿\001\003", "\001\003\004ð¿¿\004\003\001ð¿¿\001\b\001\t\001ð¿¿\004\003\002ð¿¿\002\003\023ð¿¿\001\003\002ð¿¿\001\003\006ð¿¿\003\003\003ð¿¿\002\003\001ð¿¿\003\003", "\001\003\004ð¿¿\004\003\001ð¿¿\001\b\001\t\001ð¿¿\004\003\002ð¿¿\002\003\023ð¿¿\001\003\002ð¿¿\001\003\006ð¿¿\003\003\003ð¿¿\001\003\002ð¿¿\003\003", "", "", "", "", "", "\003\013\004ð¿¿\001\013\035ð¿¿\001\n\005ð¿¿\001\013\002ð¿¿\001\013", "\003\013\004ð¿¿\001\013\035ð¿¿\001\n\005ð¿¿\001\013\002ð¿¿\001\013", "", "" };
/*      */ 
/* 9407 */   static final short[] DFA46_eot = DFA.unpackEncodedString("\fð¿¿");
/* 9408 */   static final short[] DFA46_eof = DFA.unpackEncodedString("\fð¿¿");
/* 9409 */   static final char[] DFA46_min = DFA.unpackEncodedStringToUnsignedChars("\003\037\005ð¿¿\002,\002ð¿¿");
/* 9410 */   static final char[] DFA46_max = DFA.unpackEncodedStringToUnsignedChars("\001Z\002\\\005ð¿¿\002Z\002ð¿¿");
/* 9411 */   static final short[] DFA46_accept = DFA.unpackEncodedString("\003ð¿¿\001\003\001\004\001\005\001\006\001\007\002ð¿¿\001\002\001\001");
/* 9412 */   static final short[] DFA46_special = DFA.unpackEncodedString("\fð¿¿}>");
/*      */   static final short[][] DFA46_transition;
/*      */   static final String DFA73_eotS = "\rð¿¿";
/*      */   static final String DFA73_eofS = "\rð¿¿";
/*      */   static final String DFA73_minS = "";
/*      */   static final String DFA73_maxS = "";
/*      */   static final String DFA73_acceptS = "\005ð¿¿\001\002\001\003\002ð¿¿\001\001\003ð¿¿";
/*      */   static final String DFA73_specialS = "";
/*      */   static final String[] DFA73_transitionS;
/*      */   static final short[] DFA73_eot;
/*      */   static final short[] DFA73_eof;
/*      */   static final char[] DFA73_min;
/*      */   static final char[] DFA73_max;
/*      */   static final short[] DFA73_accept;
/*      */   static final short[] DFA73_special;
/*      */   static final short[][] DFA73_transition;
/*      */   static final String DFA76_eotS = "\016ð¿¿";
/*      */   static final String DFA76_eofS = "\001ð¿¿\004\n\001ð¿¿\001\n\004ð¿¿\003\n";
/*      */   static final String DFA76_minS = "\005$\001,\001$\004ð¿¿\003$";
/*      */   static final String DFA76_maxS = "\005]\0013\001]\004ð¿¿\003]";
/*      */   static final String DFA76_acceptS = "\007ð¿¿\001\003\001\004\001\002\001\001\003ð¿¿";
/*      */   static final String DFA76_specialS = "\016ð¿¿}>";
/*      */   static final String[] DFA76_transitionS;
/*      */   static final short[] DFA76_eot;
/*      */   static final short[] DFA76_eof;
/*      */   static final char[] DFA76_min;
/*      */   static final char[] DFA76_max;
/*      */   static final short[] DFA76_accept;
/*      */   static final short[] DFA76_special;
/*      */   static final short[][] DFA76_transition;
/*      */   static final String DFA81_eotS = "\022ð¿¿";
/*      */   static final String DFA81_eofS = "\bð¿¿\001\n\tð¿¿";
/*      */   static final String DFA81_minS = "\001,\002Q\002ð¿¿\001,\002)\001'\001/\002ð¿¿\001P\001,\002)\001/\001P";
/*      */   static final String DFA81_maxS = "\003Q\002ð¿¿\001S\002)\001S\001/\002ð¿¿\001S\0013\002)\001/\001S";
/*      */   static final String DFA81_acceptS = "\003ð¿¿\001\003\001\004\005ð¿¿\001\002\001\001\006ð¿¿";
/*      */   static final String DFA81_specialS = "\022ð¿¿}>";
/*      */   static final String[] DFA81_transitionS;
/*      */   static final short[] DFA81_eot;
/*      */   static final short[] DFA81_eof;
/*      */   static final char[] DFA81_min;
/*      */   static final char[] DFA81_max;
/*      */   static final short[] DFA81_accept;
/*      */   static final short[] DFA81_special;
/*      */   static final short[][] DFA81_transition;
/* 9675 */   public static final BitSet FOLLOW_DOC_COMMENT_in_grammarDef367 = new BitSet(new long[] { 0L, 120L });
/* 9676 */   public static final BitSet FOLLOW_67_in_grammarDef377 = new BitSet(new long[] { 0L, 64L });
/* 9677 */   public static final BitSet FOLLOW_68_in_grammarDef395 = new BitSet(new long[] { 0L, 64L });
/* 9678 */   public static final BitSet FOLLOW_69_in_grammarDef411 = new BitSet(new long[] { 0L, 64L });
/* 9679 */   public static final BitSet FOLLOW_70_in_grammarDef452 = new BitSet(new long[] { 2269391999729664L });
/* 9680 */   public static final BitSet FOLLOW_id_in_grammarDef454 = new BitSet(new long[] { 0L, 128L });
/* 9681 */   public static final BitSet FOLLOW_71_in_grammarDef456 = new BitSet(new long[] { 2560798014570512L, 14336L });
/* 9682 */   public static final BitSet FOLLOW_optionsSpec_in_grammarDef458 = new BitSet(new long[] { 2560798014570512L, 14336L });
/* 9683 */   public static final BitSet FOLLOW_tokensSpec_in_grammarDef461 = new BitSet(new long[] { 2560798014570512L, 14336L });
/* 9684 */   public static final BitSet FOLLOW_attrScope_in_grammarDef464 = new BitSet(new long[] { 2560798014570512L, 14336L });
/* 9685 */   public static final BitSet FOLLOW_action_in_grammarDef467 = new BitSet(new long[] { 2560798014570512L, 14336L });
/* 9686 */   public static final BitSet FOLLOW_rule_in_grammarDef475 = new BitSet(new long[] { 2560798014570512L, 14336L });
/* 9687 */   public static final BitSet FOLLOW_EOF_in_grammarDef483 = new BitSet(new long[] { 2L });
/* 9688 */   public static final BitSet FOLLOW_TOKENS_in_tokensSpec544 = new BitSet(new long[] { 17592186044416L });
/* 9689 */   public static final BitSet FOLLOW_tokenSpec_in_tokensSpec546 = new BitSet(new long[] { 17592186044416L, 256L });
/* 9690 */   public static final BitSet FOLLOW_72_in_tokensSpec549 = new BitSet(new long[] { 2L });
/* 9691 */   public static final BitSet FOLLOW_TOKEN_REF_in_tokenSpec569 = new BitSet(new long[] { 2199023255552L, 128L });
/* 9692 */   public static final BitSet FOLLOW_LABEL_ASSIGN_in_tokenSpec575 = new BitSet(new long[] { 105553116266496L });
/* 9693 */   public static final BitSet FOLLOW_STRING_LITERAL_in_tokenSpec580 = new BitSet(new long[] { 0L, 128L });
/* 9694 */   public static final BitSet FOLLOW_CHAR_LITERAL_in_tokenSpec584 = new BitSet(new long[] { 0L, 128L });
/* 9695 */   public static final BitSet FOLLOW_71_in_tokenSpec623 = new BitSet(new long[] { 2L });
/* 9696 */   public static final BitSet FOLLOW_SCOPE_in_attrScope634 = new BitSet(new long[] { 2269391999729664L });
/* 9697 */   public static final BitSet FOLLOW_id_in_attrScope636 = new BitSet(new long[] { 140737488355328L });
/* 9698 */   public static final BitSet FOLLOW_ACTION_in_attrScope638 = new BitSet(new long[] { 2L });
/* 9699 */   public static final BitSet FOLLOW_AT_in_action661 = new BitSet(new long[] { 2269391999729664L, 24L });
/* 9700 */   public static final BitSet FOLLOW_actionScopeName_in_action664 = new BitSet(new long[] { 0L, 512L });
/* 9701 */   public static final BitSet FOLLOW_73_in_action666 = new BitSet(new long[] { 2269391999729664L });
/* 9702 */   public static final BitSet FOLLOW_id_in_action670 = new BitSet(new long[] { 140737488355328L });
/* 9703 */   public static final BitSet FOLLOW_ACTION_in_action672 = new BitSet(new long[] { 2L });
/* 9704 */   public static final BitSet FOLLOW_id_in_actionScopeName698 = new BitSet(new long[] { 2L });
/* 9705 */   public static final BitSet FOLLOW_67_in_actionScopeName705 = new BitSet(new long[] { 2L });
/* 9706 */   public static final BitSet FOLLOW_68_in_actionScopeName722 = new BitSet(new long[] { 2L });
/* 9707 */   public static final BitSet FOLLOW_OPTIONS_in_optionsSpec738 = new BitSet(new long[] { 2269391999729664L });
/* 9708 */   public static final BitSet FOLLOW_option_in_optionsSpec741 = new BitSet(new long[] { 0L, 128L });
/* 9709 */   public static final BitSet FOLLOW_71_in_optionsSpec743 = new BitSet(new long[] { 2269391999729664L, 256L });
/* 9710 */   public static final BitSet FOLLOW_72_in_optionsSpec747 = new BitSet(new long[] { 2L });
/* 9711 */   public static final BitSet FOLLOW_id_in_option772 = new BitSet(new long[] { 2199023255552L });
/* 9712 */   public static final BitSet FOLLOW_LABEL_ASSIGN_in_option774 = new BitSet(new long[] { 2937895069417472L, 1024L });
/* 9713 */   public static final BitSet FOLLOW_optionValue_in_option776 = new BitSet(new long[] { 2L });
/* 9714 */   public static final BitSet FOLLOW_qid_in_optionValue805 = new BitSet(new long[] { 2L });
/* 9715 */   public static final BitSet FOLLOW_STRING_LITERAL_in_optionValue815 = new BitSet(new long[] { 2L });
/* 9716 */   public static final BitSet FOLLOW_CHAR_LITERAL_in_optionValue825 = new BitSet(new long[] { 2L });
/* 9717 */   public static final BitSet FOLLOW_INT_in_optionValue835 = new BitSet(new long[] { 2L });
/* 9718 */   public static final BitSet FOLLOW_74_in_optionValue845 = new BitSet(new long[] { 2L });
/* 9719 */   public static final BitSet FOLLOW_DOC_COMMENT_in_rule870 = new BitSet(new long[] { 2269426359468032L, 14336L });
/* 9720 */   public static final BitSet FOLLOW_75_in_rule880 = new BitSet(new long[] { 2269391999729664L });
/* 9721 */   public static final BitSet FOLLOW_76_in_rule882 = new BitSet(new long[] { 2269391999729664L });
/* 9722 */   public static final BitSet FOLLOW_77_in_rule884 = new BitSet(new long[] { 2269391999729664L });
/* 9723 */   public static final BitSet FOLLOW_FRAGMENT_in_rule886 = new BitSet(new long[] { 2269391999729664L });
/* 9724 */   public static final BitSet FOLLOW_id_in_rule894 = new BitSet(new long[] { 1408750355218432L, 49152L });
/* 9725 */   public static final BitSet FOLLOW_BANG_in_rule900 = new BitSet(new long[] { 1408475477311488L, 49152L });
/* 9726 */   public static final BitSet FOLLOW_ARG_ACTION_in_rule909 = new BitSet(new long[] { 282575570468864L, 49152L });
/* 9727 */   public static final BitSet FOLLOW_RET_in_rule918 = new BitSet(new long[] { 1125899906842624L });
/* 9728 */   public static final BitSet FOLLOW_ARG_ACTION_in_rule922 = new BitSet(new long[] { 282575562080256L, 49152L });
/* 9729 */   public static final BitSet FOLLOW_throwsSpec_in_rule930 = new BitSet(new long[] { 282575562080256L, 16384L });
/* 9730 */   public static final BitSet FOLLOW_optionsSpec_in_rule933 = new BitSet(new long[] { 1100585369600L, 16384L });
/* 9731 */   public static final BitSet FOLLOW_ruleScopeSpec_in_rule936 = new BitSet(new long[] { 1099511627776L, 16384L });
/* 9732 */   public static final BitSet FOLLOW_ruleAction_in_rule939 = new BitSet(new long[] { 1099511627776L, 16384L });
/* 9733 */   public static final BitSet FOLLOW_78_in_rule944 = new BitSet(new long[] { 2516303227125760L, 75628544L });
/* 9734 */   public static final BitSet FOLLOW_altList_in_rule946 = new BitSet(new long[] { 0L, 128L });
/* 9735 */   public static final BitSet FOLLOW_71_in_rule948 = new BitSet(new long[] { 2L, 3145728L });
/* 9736 */   public static final BitSet FOLLOW_exceptionGroup_in_rule952 = new BitSet(new long[] { 2L });
/* 9737 */   public static final BitSet FOLLOW_AT_in_ruleAction1058 = new BitSet(new long[] { 2269391999729664L });
/* 9738 */   public static final BitSet FOLLOW_id_in_ruleAction1060 = new BitSet(new long[] { 140737488355328L });
/* 9739 */   public static final BitSet FOLLOW_ACTION_in_ruleAction1062 = new BitSet(new long[] { 2L });
/* 9740 */   public static final BitSet FOLLOW_79_in_throwsSpec1083 = new BitSet(new long[] { 2269391999729664L });
/* 9741 */   public static final BitSet FOLLOW_id_in_throwsSpec1085 = new BitSet(new long[] { 2L, 65536L });
/* 9742 */   public static final BitSet FOLLOW_80_in_throwsSpec1089 = new BitSet(new long[] { 2269391999729664L });
/* 9743 */   public static final BitSet FOLLOW_id_in_throwsSpec1091 = new BitSet(new long[] { 2L, 65536L });
/* 9744 */   public static final BitSet FOLLOW_SCOPE_in_ruleScopeSpec1114 = new BitSet(new long[] { 140737488355328L });
/* 9745 */   public static final BitSet FOLLOW_ACTION_in_ruleScopeSpec1116 = new BitSet(new long[] { 2L });
/* 9746 */   public static final BitSet FOLLOW_SCOPE_in_ruleScopeSpec1129 = new BitSet(new long[] { 2269391999729664L });
/* 9747 */   public static final BitSet FOLLOW_id_in_ruleScopeSpec1131 = new BitSet(new long[] { 0L, 65664L });
/* 9748 */   public static final BitSet FOLLOW_80_in_ruleScopeSpec1134 = new BitSet(new long[] { 2269391999729664L });
/* 9749 */   public static final BitSet FOLLOW_id_in_ruleScopeSpec1136 = new BitSet(new long[] { 0L, 65664L });
/* 9750 */   public static final BitSet FOLLOW_71_in_ruleScopeSpec1140 = new BitSet(new long[] { 2L });
/* 9751 */   public static final BitSet FOLLOW_SCOPE_in_ruleScopeSpec1154 = new BitSet(new long[] { 140737488355328L });
/* 9752 */   public static final BitSet FOLLOW_ACTION_in_ruleScopeSpec1156 = new BitSet(new long[] { 1073741824L });
/* 9753 */   public static final BitSet FOLLOW_SCOPE_in_ruleScopeSpec1160 = new BitSet(new long[] { 2269391999729664L });
/* 9754 */   public static final BitSet FOLLOW_id_in_ruleScopeSpec1162 = new BitSet(new long[] { 0L, 65664L });
/* 9755 */   public static final BitSet FOLLOW_80_in_ruleScopeSpec1165 = new BitSet(new long[] { 2269391999729664L });
/* 9756 */   public static final BitSet FOLLOW_id_in_ruleScopeSpec1167 = new BitSet(new long[] { 0L, 65664L });
/* 9757 */   public static final BitSet FOLLOW_71_in_ruleScopeSpec1171 = new BitSet(new long[] { 2L });
/* 9758 */   public static final BitSet FOLLOW_81_in_block1203 = new BitSet(new long[] { 2797778203836416L, 75644928L });
/* 9759 */   public static final BitSet FOLLOW_optionsSpec_in_block1212 = new BitSet(new long[] { 0L, 16384L });
/* 9760 */   public static final BitSet FOLLOW_78_in_block1216 = new BitSet(new long[] { 2516303227125760L, 75628544L });
/* 9761 */   public static final BitSet FOLLOW_altpair_in_block1223 = new BitSet(new long[] { 0L, 786432L });
/* 9762 */   public static final BitSet FOLLOW_82_in_block1227 = new BitSet(new long[] { 2516303227125760L, 75628544L });
/* 9763 */   public static final BitSet FOLLOW_altpair_in_block1229 = new BitSet(new long[] { 0L, 786432L });
/* 9764 */   public static final BitSet FOLLOW_83_in_block1244 = new BitSet(new long[] { 2L });
/* 9765 */   public static final BitSet FOLLOW_alternative_in_altpair1283 = new BitSet(new long[] { 549755813888L });
/* 9766 */   public static final BitSet FOLLOW_rewrite_in_altpair1285 = new BitSet(new long[] { 2L });
/* 9767 */   public static final BitSet FOLLOW_altpair_in_altList1305 = new BitSet(new long[] { 2L, 262144L });
/* 9768 */   public static final BitSet FOLLOW_82_in_altList1309 = new BitSet(new long[] { 2516303227125760L, 75628544L });
/* 9769 */   public static final BitSet FOLLOW_altpair_in_altList1311 = new BitSet(new long[] { 2L, 262144L });
/* 9770 */   public static final BitSet FOLLOW_element_in_alternative1352 = new BitSet(new long[] { 2515753471311874L, 75628544L });
/* 9771 */   public static final BitSet FOLLOW_exceptionHandler_in_exceptionGroup1403 = new BitSet(new long[] { 2L, 3145728L });
/* 9772 */   public static final BitSet FOLLOW_finallyClause_in_exceptionGroup1410 = new BitSet(new long[] { 2L });
/* 9773 */   public static final BitSet FOLLOW_finallyClause_in_exceptionGroup1418 = new BitSet(new long[] { 2L });
/* 9774 */   public static final BitSet FOLLOW_84_in_exceptionHandler1438 = new BitSet(new long[] { 1125899906842624L });
/* 9775 */   public static final BitSet FOLLOW_ARG_ACTION_in_exceptionHandler1440 = new BitSet(new long[] { 140737488355328L });
/* 9776 */   public static final BitSet FOLLOW_ACTION_in_exceptionHandler1442 = new BitSet(new long[] { 2L });
/* 9777 */   public static final BitSet FOLLOW_85_in_finallyClause1472 = new BitSet(new long[] { 140737488355328L });
/* 9778 */   public static final BitSet FOLLOW_ACTION_in_finallyClause1474 = new BitSet(new long[] { 2L });
/* 9779 */   public static final BitSet FOLLOW_id_in_element1496 = new BitSet(new long[] { 6597069766656L });
/* 9780 */   public static final BitSet FOLLOW_LABEL_ASSIGN_in_element1501 = new BitSet(new long[] { 2374945115996160L, 75497472L });
/* 9781 */   public static final BitSet FOLLOW_LIST_LABEL_ASSIGN_in_element1505 = new BitSet(new long[] { 2374945115996160L, 75497472L });
/* 9782 */   public static final BitSet FOLLOW_atom_in_element1508 = new BitSet(new long[] { 2L, 402654208L });
/* 9783 */   public static final BitSet FOLLOW_ebnfSuffix_in_element1514 = new BitSet(new long[] { 2L });
/* 9784 */   public static final BitSet FOLLOW_id_in_element1573 = new BitSet(new long[] { 6597069766656L });
/* 9785 */   public static final BitSet FOLLOW_LABEL_ASSIGN_in_element1578 = new BitSet(new long[] { 0L, 131072L });
/* 9786 */   public static final BitSet FOLLOW_LIST_LABEL_ASSIGN_in_element1582 = new BitSet(new long[] { 0L, 131072L });
/* 9787 */   public static final BitSet FOLLOW_block_in_element1585 = new BitSet(new long[] { 2L, 402654208L });
/* 9788 */   public static final BitSet FOLLOW_ebnfSuffix_in_element1591 = new BitSet(new long[] { 2L });
/* 9789 */   public static final BitSet FOLLOW_atom_in_element1650 = new BitSet(new long[] { 2L, 402654208L });
/* 9790 */   public static final BitSet FOLLOW_ebnfSuffix_in_element1656 = new BitSet(new long[] { 2L });
/* 9791 */   public static final BitSet FOLLOW_ebnf_in_element1702 = new BitSet(new long[] { 2L });
/* 9792 */   public static final BitSet FOLLOW_ACTION_in_element1709 = new BitSet(new long[] { 2L });
/* 9793 */   public static final BitSet FOLLOW_SEMPRED_in_element1716 = new BitSet(new long[] { 2L, 4194304L });
/* 9794 */   public static final BitSet FOLLOW_86_in_element1722 = new BitSet(new long[] { 2L });
/* 9795 */   public static final BitSet FOLLOW_treeSpec_in_element1742 = new BitSet(new long[] { 2L, 402654208L });
/* 9796 */   public static final BitSet FOLLOW_ebnfSuffix_in_element1748 = new BitSet(new long[] { 2L });
/* 9797 */   public static final BitSet FOLLOW_terminal_in_atom1800 = new BitSet(new long[] { 2L });
/* 9798 */   public static final BitSet FOLLOW_range_in_atom1805 = new BitSet(new long[] { 412316860418L });
/* 9799 */   public static final BitSet FOLLOW_ROOT_in_atom1815 = new BitSet(new long[] { 2L });
/* 9800 */   public static final BitSet FOLLOW_BANG_in_atom1819 = new BitSet(new long[] { 2L });
/* 9801 */   public static final BitSet FOLLOW_notSet_in_atom1853 = new BitSet(new long[] { 412316860418L });
/* 9802 */   public static final BitSet FOLLOW_ROOT_in_atom1862 = new BitSet(new long[] { 2L });
/* 9803 */   public static final BitSet FOLLOW_BANG_in_atom1866 = new BitSet(new long[] { 2L });
/* 9804 */   public static final BitSet FOLLOW_RULE_REF_in_atom1902 = new BitSet(new long[] { 1126312223703042L });
/* 9805 */   public static final BitSet FOLLOW_ARG_ACTION_in_atom1904 = new BitSet(new long[] { 412316860418L });
/* 9806 */   public static final BitSet FOLLOW_ROOT_in_atom1914 = new BitSet(new long[] { 2L });
/* 9807 */   public static final BitSet FOLLOW_BANG_in_atom1918 = new BitSet(new long[] { 2L });
/* 9808 */   public static final BitSet FOLLOW_87_in_notSet1966 = new BitSet(new long[] { 123145302310912L, 131072L });
/* 9809 */   public static final BitSet FOLLOW_notTerminal_in_notSet1972 = new BitSet(new long[] { 2L, 16777216L });
/* 9810 */   public static final BitSet FOLLOW_elementOptions_in_notSet1974 = new BitSet(new long[] { 2L });
/* 9811 */   public static final BitSet FOLLOW_block_in_notSet1992 = new BitSet(new long[] { 2L, 16777216L });
/* 9812 */   public static final BitSet FOLLOW_elementOptions_in_notSet1994 = new BitSet(new long[] { 2L });
/* 9813 */   public static final BitSet FOLLOW_set_in_notTerminal0 = new BitSet(new long[] { 2L });
/* 9814 */   public static final BitSet FOLLOW_88_in_elementOptions2046 = new BitSet(new long[] { 2269391999729664L });
/* 9815 */   public static final BitSet FOLLOW_qid_in_elementOptions2048 = new BitSet(new long[] { 0L, 33554432L });
/* 9816 */   public static final BitSet FOLLOW_89_in_elementOptions2050 = new BitSet(new long[] { 2L });
/* 9817 */   public static final BitSet FOLLOW_88_in_elementOptions2068 = new BitSet(new long[] { 2269391999729664L });
/* 9818 */   public static final BitSet FOLLOW_option_in_elementOptions2070 = new BitSet(new long[] { 0L, 33554560L });
/* 9819 */   public static final BitSet FOLLOW_71_in_elementOptions2073 = new BitSet(new long[] { 2269391999729664L });
/* 9820 */   public static final BitSet FOLLOW_option_in_elementOptions2075 = new BitSet(new long[] { 0L, 33554560L });
/* 9821 */   public static final BitSet FOLLOW_89_in_elementOptions2079 = new BitSet(new long[] { 2L });
/* 9822 */   public static final BitSet FOLLOW_id_in_elementOption2099 = new BitSet(new long[] { 2199023255552L });
/* 9823 */   public static final BitSet FOLLOW_LABEL_ASSIGN_in_elementOption2101 = new BitSet(new long[] { 2937895069417472L, 1024L });
/* 9824 */   public static final BitSet FOLLOW_optionValue_in_elementOption2103 = new BitSet(new long[] { 2L });
/* 9825 */   public static final BitSet FOLLOW_TREE_BEGIN_in_treeSpec2125 = new BitSet(new long[] { 2515753471311872L, 75628544L });
/* 9826 */   public static final BitSet FOLLOW_element_in_treeSpec2127 = new BitSet(new long[] { 2515753471311872L, 75628544L });
/* 9827 */   public static final BitSet FOLLOW_element_in_treeSpec2131 = new BitSet(new long[] { 2515753471311872L, 76152832L });
/* 9828 */   public static final BitSet FOLLOW_83_in_treeSpec2136 = new BitSet(new long[] { 2L });
/* 9829 */   public static final BitSet FOLLOW_CHAR_LITERAL_in_range2159 = new BitSet(new long[] { 8192L });
/* 9830 */   public static final BitSet FOLLOW_RANGE_in_range2161 = new BitSet(new long[] { 70368744177664L });
/* 9831 */   public static final BitSet FOLLOW_CHAR_LITERAL_in_range2165 = new BitSet(new long[] { 2L, 16777216L });
/* 9832 */   public static final BitSet FOLLOW_elementOptions_in_range2167 = new BitSet(new long[] { 2L });
/* 9833 */   public static final BitSet FOLLOW_CHAR_LITERAL_in_terminal2204 = new BitSet(new long[] { 412316860418L, 16777216L });
/* 9834 */   public static final BitSet FOLLOW_elementOptions_in_terminal2206 = new BitSet(new long[] { 412316860418L });
/* 9835 */   public static final BitSet FOLLOW_TOKEN_REF_in_terminal2237 = new BitSet(new long[] { 1126312223703042L, 16777216L });
/* 9836 */   public static final BitSet FOLLOW_ARG_ACTION_in_terminal2239 = new BitSet(new long[] { 412316860418L, 16777216L });
/* 9837 */   public static final BitSet FOLLOW_elementOptions_in_terminal2242 = new BitSet(new long[] { 412316860418L });
/* 9838 */   public static final BitSet FOLLOW_STRING_LITERAL_in_terminal2263 = new BitSet(new long[] { 412316860418L, 16777216L });
/* 9839 */   public static final BitSet FOLLOW_elementOptions_in_terminal2265 = new BitSet(new long[] { 412316860418L });
/* 9840 */   public static final BitSet FOLLOW_90_in_terminal2286 = new BitSet(new long[] { 412316860418L, 16777216L });
/* 9841 */   public static final BitSet FOLLOW_elementOptions_in_terminal2288 = new BitSet(new long[] { 412316860418L });
/* 9842 */   public static final BitSet FOLLOW_ROOT_in_terminal2315 = new BitSet(new long[] { 2L });
/* 9843 */   public static final BitSet FOLLOW_BANG_in_terminal2336 = new BitSet(new long[] { 2L });
/* 9844 */   public static final BitSet FOLLOW_block_in_ebnf2379 = new BitSet(new long[] { 2L, 406848512L });
/* 9845 */   public static final BitSet FOLLOW_91_in_ebnf2387 = new BitSet(new long[] { 2L });
/* 9846 */   public static final BitSet FOLLOW_74_in_ebnf2404 = new BitSet(new long[] { 2L });
/* 9847 */   public static final BitSet FOLLOW_92_in_ebnf2421 = new BitSet(new long[] { 2L });
/* 9848 */   public static final BitSet FOLLOW_86_in_ebnf2438 = new BitSet(new long[] { 2L });
/* 9849 */   public static final BitSet FOLLOW_91_in_ebnfSuffix2523 = new BitSet(new long[] { 2L });
/* 9850 */   public static final BitSet FOLLOW_74_in_ebnfSuffix2535 = new BitSet(new long[] { 2L });
/* 9851 */   public static final BitSet FOLLOW_92_in_ebnfSuffix2548 = new BitSet(new long[] { 2L });
/* 9852 */   public static final BitSet FOLLOW_REWRITE_in_rewrite2577 = new BitSet(new long[] { 2147483648L });
/* 9853 */   public static final BitSet FOLLOW_SEMPRED_in_rewrite2581 = new BitSet(new long[] { 2516301079642112L, 537001984L });
/* 9854 */   public static final BitSet FOLLOW_rewrite_alternative_in_rewrite2585 = new BitSet(new long[] { 549755813888L });
/* 9855 */   public static final BitSet FOLLOW_REWRITE_in_rewrite2593 = new BitSet(new long[] { 2515751323828224L, 537001984L });
/* 9856 */   public static final BitSet FOLLOW_rewrite_alternative_in_rewrite2597 = new BitSet(new long[] { 2L });
/* 9857 */   public static final BitSet FOLLOW_rewrite_template_in_rewrite_alternative2648 = new BitSet(new long[] { 2L });
/* 9858 */   public static final BitSet FOLLOW_rewrite_tree_alternative_in_rewrite_alternative2653 = new BitSet(new long[] { 2L });
/* 9859 */   public static final BitSet FOLLOW_81_in_rewrite_tree_block2695 = new BitSet(new long[] { 2515751323828224L, 537001984L });
/* 9860 */   public static final BitSet FOLLOW_rewrite_tree_alternative_in_rewrite_tree_block2697 = new BitSet(new long[] { 0L, 524288L });
/* 9861 */   public static final BitSet FOLLOW_83_in_rewrite_tree_block2699 = new BitSet(new long[] { 2L });
/* 9862 */   public static final BitSet FOLLOW_rewrite_tree_element_in_rewrite_tree_alternative2733 = new BitSet(new long[] { 2515751323828226L, 537001984L });
/* 9863 */   public static final BitSet FOLLOW_rewrite_tree_atom_in_rewrite_tree_element2761 = new BitSet(new long[] { 2L });
/* 9864 */   public static final BitSet FOLLOW_rewrite_tree_atom_in_rewrite_tree_element2766 = new BitSet(new long[] { 0L, 402654208L });
/* 9865 */   public static final BitSet FOLLOW_ebnfSuffix_in_rewrite_tree_element2768 = new BitSet(new long[] { 2L });
/* 9866 */   public static final BitSet FOLLOW_rewrite_tree_in_rewrite_tree_element2802 = new BitSet(new long[] { 2L, 402654208L });
/* 9867 */   public static final BitSet FOLLOW_ebnfSuffix_in_rewrite_tree_element2808 = new BitSet(new long[] { 2L });
/* 9868 */   public static final BitSet FOLLOW_rewrite_tree_ebnf_in_rewrite_tree_element2854 = new BitSet(new long[] { 2L });
/* 9869 */   public static final BitSet FOLLOW_CHAR_LITERAL_in_rewrite_tree_atom2870 = new BitSet(new long[] { 2L });
/* 9870 */   public static final BitSet FOLLOW_TOKEN_REF_in_rewrite_tree_atom2877 = new BitSet(new long[] { 1125899906842626L });
/* 9871 */   public static final BitSet FOLLOW_ARG_ACTION_in_rewrite_tree_atom2879 = new BitSet(new long[] { 2L });
/* 9872 */   public static final BitSet FOLLOW_RULE_REF_in_rewrite_tree_atom2900 = new BitSet(new long[] { 2L });
/* 9873 */   public static final BitSet FOLLOW_STRING_LITERAL_in_rewrite_tree_atom2907 = new BitSet(new long[] { 2L });
/* 9874 */   public static final BitSet FOLLOW_93_in_rewrite_tree_atom2916 = new BitSet(new long[] { 2269391999729664L });
/* 9875 */   public static final BitSet FOLLOW_id_in_rewrite_tree_atom2918 = new BitSet(new long[] { 2L });
/* 9876 */   public static final BitSet FOLLOW_ACTION_in_rewrite_tree_atom2929 = new BitSet(new long[] { 2L });
/* 9877 */   public static final BitSet FOLLOW_rewrite_tree_block_in_rewrite_tree_ebnf2950 = new BitSet(new long[] { 0L, 402654208L });
/* 9878 */   public static final BitSet FOLLOW_ebnfSuffix_in_rewrite_tree_ebnf2952 = new BitSet(new long[] { 2L });
/* 9879 */   public static final BitSet FOLLOW_TREE_BEGIN_in_rewrite_tree2972 = new BitSet(new long[] { 2515682604351488L, 536870912L });
/* 9880 */   public static final BitSet FOLLOW_rewrite_tree_atom_in_rewrite_tree2974 = new BitSet(new long[] { 2515751323828224L, 537526272L });
/* 9881 */   public static final BitSet FOLLOW_rewrite_tree_element_in_rewrite_tree2976 = new BitSet(new long[] { 2515751323828224L, 537526272L });
/* 9882 */   public static final BitSet FOLLOW_83_in_rewrite_tree2979 = new BitSet(new long[] { 2L });
/* 9883 */   public static final BitSet FOLLOW_id_in_rewrite_template3011 = new BitSet(new long[] { 0L, 131072L });
/* 9884 */   public static final BitSet FOLLOW_81_in_rewrite_template3015 = new BitSet(new long[] { 2269391999729664L, 524288L });
/* 9885 */   public static final BitSet FOLLOW_rewrite_template_args_in_rewrite_template3017 = new BitSet(new long[] { 0L, 524288L });
/* 9886 */   public static final BitSet FOLLOW_83_in_rewrite_template3019 = new BitSet(new long[] { 13510798882111488L });
/* 9887 */   public static final BitSet FOLLOW_DOUBLE_QUOTE_STRING_LITERAL_in_rewrite_template3027 = new BitSet(new long[] { 2L });
/* 9888 */   public static final BitSet FOLLOW_DOUBLE_ANGLE_STRING_LITERAL_in_rewrite_template3033 = new BitSet(new long[] { 2L });
/* 9889 */   public static final BitSet FOLLOW_rewrite_template_ref_in_rewrite_template3060 = new BitSet(new long[] { 2L });
/* 9890 */   public static final BitSet FOLLOW_rewrite_indirect_template_head_in_rewrite_template3069 = new BitSet(new long[] { 2L });
/* 9891 */   public static final BitSet FOLLOW_ACTION_in_rewrite_template3078 = new BitSet(new long[] { 2L });
/* 9892 */   public static final BitSet FOLLOW_id_in_rewrite_template_ref3091 = new BitSet(new long[] { 0L, 131072L });
/* 9893 */   public static final BitSet FOLLOW_81_in_rewrite_template_ref3095 = new BitSet(new long[] { 2269391999729664L, 524288L });
/* 9894 */   public static final BitSet FOLLOW_rewrite_template_args_in_rewrite_template_ref3097 = new BitSet(new long[] { 0L, 524288L });
/* 9895 */   public static final BitSet FOLLOW_83_in_rewrite_template_ref3099 = new BitSet(new long[] { 2L });
/* 9896 */   public static final BitSet FOLLOW_81_in_rewrite_indirect_template_head3127 = new BitSet(new long[] { 140737488355328L });
/* 9897 */   public static final BitSet FOLLOW_ACTION_in_rewrite_indirect_template_head3129 = new BitSet(new long[] { 0L, 524288L });
/* 9898 */   public static final BitSet FOLLOW_83_in_rewrite_indirect_template_head3131 = new BitSet(new long[] { 0L, 131072L });
/* 9899 */   public static final BitSet FOLLOW_81_in_rewrite_indirect_template_head3133 = new BitSet(new long[] { 2269391999729664L, 524288L });
/* 9900 */   public static final BitSet FOLLOW_rewrite_template_args_in_rewrite_indirect_template_head3135 = new BitSet(new long[] { 0L, 524288L });
/* 9901 */   public static final BitSet FOLLOW_83_in_rewrite_indirect_template_head3137 = new BitSet(new long[] { 2L });
/* 9902 */   public static final BitSet FOLLOW_rewrite_template_arg_in_rewrite_template_args3161 = new BitSet(new long[] { 2L, 65536L });
/* 9903 */   public static final BitSet FOLLOW_80_in_rewrite_template_args3164 = new BitSet(new long[] { 2269391999729664L });
/* 9904 */   public static final BitSet FOLLOW_rewrite_template_arg_in_rewrite_template_args3166 = new BitSet(new long[] { 2L, 65536L });
/* 9905 */   public static final BitSet FOLLOW_id_in_rewrite_template_arg3199 = new BitSet(new long[] { 2199023255552L });
/* 9906 */   public static final BitSet FOLLOW_LABEL_ASSIGN_in_rewrite_template_arg3201 = new BitSet(new long[] { 140737488355328L });
/* 9907 */   public static final BitSet FOLLOW_ACTION_in_rewrite_template_arg3203 = new BitSet(new long[] { 2L });
/* 9908 */   public static final BitSet FOLLOW_id_in_qid3224 = new BitSet(new long[] { 2L, 67108864L });
/* 9909 */   public static final BitSet FOLLOW_90_in_qid3227 = new BitSet(new long[] { 2269391999729664L });
/* 9910 */   public static final BitSet FOLLOW_id_in_qid3229 = new BitSet(new long[] { 2L, 67108864L });
/* 9911 */   public static final BitSet FOLLOW_TOKEN_REF_in_id3241 = new BitSet(new long[] { 2L });
/* 9912 */   public static final BitSet FOLLOW_RULE_REF_in_id3251 = new BitSet(new long[] { 2L });
/* 9913 */   public static final BitSet FOLLOW_rewrite_template_in_synpred1_ANTLRv32648 = new BitSet(new long[] { 2L });
/* 9914 */   public static final BitSet FOLLOW_rewrite_tree_alternative_in_synpred2_ANTLRv32653 = new BitSet(new long[] { 2L });
/*      */ 
/*      */   public ANTLRv3Parser(TokenStream input)
/*      */   {
/*  117 */     this(input, new RecognizerSharedState());
/*      */   }
/*      */   public ANTLRv3Parser(TokenStream input, RecognizerSharedState state) {
/*  120 */     super(input, state);
/*      */   }
/*      */ 
/*      */   public void setTreeAdaptor(TreeAdaptor adaptor)
/*      */   {
/*  127 */     this.adaptor = adaptor;
/*      */   }
/*      */   public TreeAdaptor getTreeAdaptor() {
/*  130 */     return this.adaptor;
/*      */   }
/*      */   public String[] getTokenNames() {
/*  133 */     return tokenNames; } 
/*  134 */   public String getGrammarFileName() { return "org/antlr/grammar/v3/ANTLRv3.g"; }
/*      */ 
/*      */ 
/*      */   public final grammarDef_return grammarDef()
/*      */     throws RecognitionException
/*      */   {
/*  148 */     grammarDef_return retval = new grammarDef_return();
/*  149 */     retval.start = this.input.LT(1);
/*      */ 
/*  151 */     CommonTree root_0 = null;
/*      */ 
/*  153 */     Token g = null;
/*  154 */     Token DOC_COMMENT1 = null;
/*  155 */     Token string_literal2 = null;
/*  156 */     Token string_literal3 = null;
/*  157 */     Token string_literal4 = null;
/*  158 */     Token char_literal6 = null;
/*  159 */     Token EOF12 = null;
/*  160 */     id_return id5 = null;
/*      */ 
/*  162 */     optionsSpec_return optionsSpec7 = null;
/*      */ 
/*  164 */     tokensSpec_return tokensSpec8 = null;
/*      */ 
/*  166 */     attrScope_return attrScope9 = null;
/*      */ 
/*  168 */     action_return action10 = null;
/*      */ 
/*  170 */     rule_return rule11 = null;
/*      */ 
/*  173 */     CommonTree g_tree = null;
/*  174 */     CommonTree DOC_COMMENT1_tree = null;
/*  175 */     CommonTree string_literal2_tree = null;
/*  176 */     CommonTree string_literal3_tree = null;
/*  177 */     CommonTree string_literal4_tree = null;
/*  178 */     CommonTree char_literal6_tree = null;
/*  179 */     CommonTree EOF12_tree = null;
/*  180 */     RewriteRuleTokenStream stream_67 = new RewriteRuleTokenStream(this.adaptor, "token 67");
/*  181 */     RewriteRuleTokenStream stream_DOC_COMMENT = new RewriteRuleTokenStream(this.adaptor, "token DOC_COMMENT");
/*  182 */     RewriteRuleTokenStream stream_69 = new RewriteRuleTokenStream(this.adaptor, "token 69");
/*  183 */     RewriteRuleTokenStream stream_68 = new RewriteRuleTokenStream(this.adaptor, "token 68");
/*  184 */     RewriteRuleTokenStream stream_EOF = new RewriteRuleTokenStream(this.adaptor, "token EOF");
/*  185 */     RewriteRuleTokenStream stream_70 = new RewriteRuleTokenStream(this.adaptor, "token 70");
/*  186 */     RewriteRuleTokenStream stream_71 = new RewriteRuleTokenStream(this.adaptor, "token 71");
/*  187 */     RewriteRuleSubtreeStream stream_id = new RewriteRuleSubtreeStream(this.adaptor, "rule id");
/*  188 */     RewriteRuleSubtreeStream stream_tokensSpec = new RewriteRuleSubtreeStream(this.adaptor, "rule tokensSpec");
/*  189 */     RewriteRuleSubtreeStream stream_attrScope = new RewriteRuleSubtreeStream(this.adaptor, "rule attrScope");
/*  190 */     RewriteRuleSubtreeStream stream_rule = new RewriteRuleSubtreeStream(this.adaptor, "rule rule");
/*  191 */     RewriteRuleSubtreeStream stream_action = new RewriteRuleSubtreeStream(this.adaptor, "rule action");
/*  192 */     RewriteRuleSubtreeStream stream_optionsSpec = new RewriteRuleSubtreeStream(this.adaptor, "rule optionsSpec");
/*      */     try
/*      */     {
/*  198 */       int alt1 = 2;
/*  199 */       switch (this.input.LA(1))
/*      */       {
/*      */       case 4:
/*  202 */         alt1 = 1;
/*      */       }
/*      */ 
/*  207 */       switch (alt1)
/*      */       {
/*      */       case 1:
/*  211 */         DOC_COMMENT1 = (Token)match(this.input, 4, FOLLOW_DOC_COMMENT_in_grammarDef367); if (this.state.failed) { grammarDef_return localgrammarDef_return1 = retval; return localgrammarDef_return1; }
/*  212 */         if (this.state.backtracking == 0) stream_DOC_COMMENT.add(DOC_COMMENT1);
/*      */ 
/*      */         break;
/*      */       }
/*      */ 
/*  221 */       int alt2 = 4;
/*      */       Object nvae;
/*  222 */       switch (this.input.LA(1))
/*      */       {
/*      */       case 67:
/*  225 */         alt2 = 1;
/*      */ 
/*  227 */         break;
/*      */       case 68:
/*  230 */         alt2 = 2;
/*      */ 
/*  232 */         break;
/*      */       case 69:
/*  235 */         alt2 = 3;
/*      */ 
/*  237 */         break;
/*      */       case 70:
/*  240 */         alt2 = 4;
/*      */ 
/*  242 */         break;
/*      */       default:
/*  244 */         if (this.state.backtracking > 0) { this.state.failed = true; grammarDef_return localgrammarDef_return2 = retval; return localgrammarDef_return2; }
/*  245 */         nvae = new NoViableAltException("", 2, 0, this.input);
/*      */ 
/*  248 */         throw ((Throwable)nvae);
/*      */       }
/*      */ 
/*  251 */       switch (alt2)
/*      */       {
/*      */       case 1:
/*  255 */         string_literal2 = (Token)match(this.input, 67, FOLLOW_67_in_grammarDef377); if (this.state.failed) { nvae = retval; return nvae; }
/*  256 */         if (this.state.backtracking == 0) stream_67.add(string_literal2);
/*      */ 
/*  258 */         if (this.state.backtracking == 0)
/*  259 */           this.gtype = 24; break;
/*      */       case 2:
/*  267 */         string_literal3 = (Token)match(this.input, 68, FOLLOW_68_in_grammarDef395); if (this.state.failed) { nvae = retval; return nvae; }
/*  268 */         if (this.state.backtracking == 0) stream_68.add(string_literal3);
/*      */ 
/*  270 */         if (this.state.backtracking == 0)
/*  271 */           this.gtype = 25; break;
/*      */       case 3:
/*  279 */         string_literal4 = (Token)match(this.input, 69, FOLLOW_69_in_grammarDef411); if (this.state.failed) { nvae = retval; return nvae; }
/*  280 */         if (this.state.backtracking == 0) stream_69.add(string_literal4);
/*      */ 
/*  282 */         if (this.state.backtracking == 0)
/*  283 */           this.gtype = 26; break;
/*      */       case 4:
/*  291 */         if (this.state.backtracking == 0) {
/*  292 */           this.gtype = 27;
/*      */         }
/*      */ 
/*      */         break;
/*      */       }
/*      */ 
/*  300 */       g = (Token)match(this.input, 70, FOLLOW_70_in_grammarDef452); if (this.state.failed) { nvae = retval; return nvae; }
/*  301 */       if (this.state.backtracking == 0) stream_70.add(g);
/*      */ 
/*  303 */       pushFollow(FOLLOW_id_in_grammarDef454);
/*  304 */       id5 = id();
/*      */ 
/*  306 */       this.state._fsp -= 1;
/*  307 */       if (this.state.failed) { nvae = retval; return nvae; }
/*  308 */       if (this.state.backtracking == 0) stream_id.add(id5.getTree());
/*  309 */       char_literal6 = (Token)match(this.input, 71, FOLLOW_71_in_grammarDef456); if (this.state.failed) { nvae = retval; return nvae; }
/*  310 */       if (this.state.backtracking == 0) stream_71.add(char_literal6);
/*      */ 
/*  313 */       int alt3 = 2;
/*  314 */       switch (this.input.LA(1))
/*      */       {
/*      */       case 48:
/*  317 */         alt3 = 1;
/*      */       }
/*      */ 
/*  322 */       switch (alt3)
/*      */       {
/*      */       case 1:
/*  326 */         pushFollow(FOLLOW_optionsSpec_in_grammarDef458);
/*  327 */         optionsSpec7 = optionsSpec();
/*      */ 
/*  329 */         this.state._fsp -= 1;
/*  330 */         if (this.state.failed) { grammarDef_return localgrammarDef_return3 = retval; return localgrammarDef_return3; }
/*  331 */         if (this.state.backtracking == 0) stream_optionsSpec.add(optionsSpec7.getTree());
/*      */ 
/*      */         break;
/*      */       }
/*      */ 
/*  339 */       int alt4 = 2;
/*  340 */       switch (this.input.LA(1))
/*      */       {
/*      */       case 43:
/*  343 */         alt4 = 1;
/*      */       }
/*      */ 
/*  348 */       switch (alt4)
/*      */       {
/*      */       case 1:
/*  352 */         pushFollow(FOLLOW_tokensSpec_in_grammarDef461);
/*  353 */         tokensSpec8 = tokensSpec();
/*      */ 
/*  355 */         this.state._fsp -= 1;
/*  356 */         if (this.state.failed) { grammarDef_return localgrammarDef_return4 = retval; return localgrammarDef_return4; }
/*  357 */         if (this.state.backtracking == 0) stream_tokensSpec.add(tokensSpec8.getTree());
/*      */ 
/*      */         break;
/*      */       }
/*      */ 
/*      */       grammarDef_return localgrammarDef_return5;
/*      */       while (true)
/*      */       {
/*  367 */         int alt5 = 2;
/*  368 */         switch (this.input.LA(1))
/*      */         {
/*      */         case 30:
/*  371 */           alt5 = 1;
/*      */         }
/*      */ 
/*  377 */         switch (alt5)
/*      */         {
/*      */         case 1:
/*  381 */           pushFollow(FOLLOW_attrScope_in_grammarDef464);
/*  382 */           attrScope9 = attrScope();
/*      */ 
/*  384 */           this.state._fsp -= 1;
/*  385 */           if (this.state.failed) { localgrammarDef_return5 = retval; return localgrammarDef_return5; }
/*  386 */           if (this.state.backtracking == 0) stream_attrScope.add(attrScope9.getTree()); break;
/*      */         default:
/*  392 */           break label1271;
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/*      */       while (true)
/*      */       {
/*  399 */         label1271: int alt6 = 2;
/*  400 */         switch (this.input.LA(1))
/*      */         {
/*      */         case 40:
/*  403 */           alt6 = 1;
/*      */         }
/*      */ 
/*  409 */         switch (alt6)
/*      */         {
/*      */         case 1:
/*  413 */           pushFollow(FOLLOW_action_in_grammarDef467);
/*  414 */           action10 = action();
/*      */ 
/*  416 */           this.state._fsp -= 1;
/*  417 */           if (this.state.failed) { localgrammarDef_return5 = retval; return localgrammarDef_return5; }
/*  418 */           if (this.state.backtracking == 0) stream_action.add(action10.getTree()); break;
/*      */         default:
/*  424 */           break label1399;
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/*  429 */       label1399: int cnt7 = 0;
/*      */       int alt7;
/*      */       while (true) { alt7 = 2;
/*  433 */         switch (this.input.LA(1))
/*      */         {
/*      */         case 4:
/*      */         case 35:
/*      */         case 44:
/*      */         case 51:
/*      */         case 75:
/*      */         case 76:
/*      */         case 77:
/*  442 */           alt7 = 1;
/*      */         }
/*      */         grammarDef_return localgrammarDef_return7;
/*  448 */         switch (alt7)
/*      */         {
/*      */         case 1:
/*  452 */           pushFollow(FOLLOW_rule_in_grammarDef475);
/*  453 */           rule11 = rule();
/*      */ 
/*  455 */           this.state._fsp -= 1;
/*  456 */           if (this.state.failed) { localgrammarDef_return7 = retval; return localgrammarDef_return7; }
/*  457 */           if (this.state.backtracking == 0) stream_rule.add(rule11.getTree()); break;
/*      */         default:
/*  463 */           if (cnt7 >= 1) break label1626;
/*  464 */           if (this.state.backtracking > 0) { this.state.failed = true; localgrammarDef_return7 = retval; return localgrammarDef_return7; }
/*  465 */           EarlyExitException eee = new EarlyExitException(7, this.input);
/*      */ 
/*  467 */           throw eee;
/*      */         }
/*  469 */         cnt7++;
/*      */       }
/*      */ 
/*  472 */       label1626: EOF12 = (Token)match(this.input, -1, FOLLOW_EOF_in_grammarDef483); if (this.state.failed) { grammarDef_return localgrammarDef_return6 = retval; return localgrammarDef_return6; }
/*  473 */       if (this.state.backtracking == 0) stream_EOF.add(EOF12);
/*      */ 
/*  484 */       if (this.state.backtracking == 0) {
/*  485 */         retval.tree = root_0;
/*  486 */         RewriteRuleSubtreeStream stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.tree : null);
/*      */ 
/*  488 */         root_0 = (CommonTree)this.adaptor.nil();
/*      */ 
/*  493 */         CommonTree root_1 = (CommonTree)this.adaptor.nil();
/*  494 */         root_1 = (CommonTree)this.adaptor.becomeRoot(this.adaptor.create(this.gtype, g), root_1);
/*      */ 
/*  496 */         this.adaptor.addChild(root_1, stream_id.nextTree());
/*      */ 
/*  498 */         if (stream_DOC_COMMENT.hasNext()) {
/*  499 */           this.adaptor.addChild(root_1, stream_DOC_COMMENT.nextNode());
/*      */         }
/*      */ 
/*  502 */         stream_DOC_COMMENT.reset();
/*      */ 
/*  504 */         if (stream_optionsSpec.hasNext()) {
/*  505 */           this.adaptor.addChild(root_1, stream_optionsSpec.nextTree());
/*      */         }
/*      */ 
/*  508 */         stream_optionsSpec.reset();
/*      */ 
/*  510 */         if (stream_tokensSpec.hasNext()) {
/*  511 */           this.adaptor.addChild(root_1, stream_tokensSpec.nextTree());
/*      */         }
/*      */ 
/*  514 */         stream_tokensSpec.reset();
/*      */ 
/*  516 */         while (stream_attrScope.hasNext()) {
/*  517 */           this.adaptor.addChild(root_1, stream_attrScope.nextTree());
/*      */         }
/*      */ 
/*  520 */         stream_attrScope.reset();
/*      */ 
/*  522 */         while (stream_action.hasNext()) {
/*  523 */           this.adaptor.addChild(root_1, stream_action.nextTree());
/*      */         }
/*      */ 
/*  526 */         stream_action.reset();
/*  527 */         if (!stream_rule.hasNext()) {
/*  528 */           throw new RewriteEarlyExitException();
/*      */         }
/*  530 */         while (stream_rule.hasNext()) {
/*  531 */           this.adaptor.addChild(root_1, stream_rule.nextTree());
/*      */         }
/*      */ 
/*  534 */         stream_rule.reset();
/*      */ 
/*  536 */         this.adaptor.addChild(root_0, root_1);
/*      */ 
/*  541 */         retval.tree = root_0;
/*      */       }
/*      */ 
/*  544 */       retval.stop = this.input.LT(-1);
/*      */ 
/*  546 */       if (this.state.backtracking == 0)
/*      */       {
/*  548 */         retval.tree = ((CommonTree)this.adaptor.rulePostProcessing(root_0));
/*  549 */         this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
/*      */       }
/*      */     } catch (RecognitionException re) {
/*  552 */       re = 
/*  559 */         re;
/*      */ 
/*  553 */       reportError(re);
/*  554 */       recover(this.input, re);
/*  555 */       retval.tree = ((CommonTree)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), re));
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*  560 */     return retval;
/*      */   }
/*      */ 
/*      */   public final tokensSpec_return tokensSpec()
/*      */     throws RecognitionException
/*      */   {
/*  572 */     tokensSpec_return retval = new tokensSpec_return();
/*  573 */     retval.start = this.input.LT(1);
/*      */ 
/*  575 */     CommonTree root_0 = null;
/*      */ 
/*  577 */     Token TOKENS13 = null;
/*  578 */     Token char_literal15 = null;
/*  579 */     tokenSpec_return tokenSpec14 = null;
/*      */ 
/*  582 */     CommonTree TOKENS13_tree = null;
/*  583 */     CommonTree char_literal15_tree = null;
/*  584 */     RewriteRuleTokenStream stream_TOKENS = new RewriteRuleTokenStream(this.adaptor, "token TOKENS");
/*  585 */     RewriteRuleTokenStream stream_72 = new RewriteRuleTokenStream(this.adaptor, "token 72");
/*  586 */     RewriteRuleSubtreeStream stream_tokenSpec = new RewriteRuleSubtreeStream(this.adaptor, "rule tokenSpec");
/*      */     try
/*      */     {
/*  591 */       TOKENS13 = (Token)match(this.input, 43, FOLLOW_TOKENS_in_tokensSpec544); if (this.state.failed) { tokensSpec_return localtokensSpec_return1 = retval; return localtokensSpec_return1; }
/*  592 */       if (this.state.backtracking == 0) stream_TOKENS.add(TOKENS13); 
/*      */ int cnt8 = 0;
/*      */       int alt8;
/*      */       while (true)
/*      */       {
/*  598 */         alt8 = 2;
/*  599 */         switch (this.input.LA(1))
/*      */         {
/*      */         case 44:
/*  602 */           alt8 = 1;
/*      */         }
/*      */         tokensSpec_return localtokensSpec_return2;
/*  608 */         switch (alt8)
/*      */         {
/*      */         case 1:
/*  612 */           pushFollow(FOLLOW_tokenSpec_in_tokensSpec546);
/*  613 */           tokenSpec14 = tokenSpec();
/*      */ 
/*  615 */           this.state._fsp -= 1;
/*  616 */           if (this.state.failed) { localtokensSpec_return2 = retval; return localtokensSpec_return2; }
/*  617 */           if (this.state.backtracking == 0) stream_tokenSpec.add(tokenSpec14.getTree()); break;
/*      */         default:
/*  623 */           if (cnt8 >= 1) break label314;
/*  624 */           if (this.state.backtracking > 0) { this.state.failed = true; localtokensSpec_return2 = retval; return localtokensSpec_return2; }
/*  625 */           EarlyExitException eee = new EarlyExitException(8, this.input);
/*      */ 
/*  627 */           throw eee;
/*      */         }
/*  629 */         cnt8++;
/*      */       }
/*      */ 
/*  632 */       label314: char_literal15 = (Token)match(this.input, 72, FOLLOW_72_in_tokensSpec549); if (this.state.failed) { alt8 = retval; return alt8; }
/*  633 */       if (this.state.backtracking == 0) stream_72.add(char_literal15);
/*      */ 
/*  644 */       if (this.state.backtracking == 0) {
/*  645 */         retval.tree = root_0;
/*  646 */         RewriteRuleSubtreeStream stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.tree : null);
/*      */ 
/*  648 */         root_0 = (CommonTree)this.adaptor.nil();
/*      */ 
/*  653 */         CommonTree root_1 = (CommonTree)this.adaptor.nil();
/*  654 */         root_1 = (CommonTree)this.adaptor.becomeRoot(stream_TOKENS.nextNode(), root_1);
/*      */ 
/*  656 */         if (!stream_tokenSpec.hasNext()) {
/*  657 */           throw new RewriteEarlyExitException();
/*      */         }
/*  659 */         while (stream_tokenSpec.hasNext()) {
/*  660 */           this.adaptor.addChild(root_1, stream_tokenSpec.nextTree());
/*      */         }
/*      */ 
/*  663 */         stream_tokenSpec.reset();
/*      */ 
/*  665 */         this.adaptor.addChild(root_0, root_1);
/*      */ 
/*  670 */         retval.tree = root_0;
/*      */       }
/*      */ 
/*  673 */       retval.stop = this.input.LT(-1);
/*      */ 
/*  675 */       if (this.state.backtracking == 0)
/*      */       {
/*  677 */         retval.tree = ((CommonTree)this.adaptor.rulePostProcessing(root_0));
/*  678 */         this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
/*      */       }
/*      */     } catch (RecognitionException re) {
/*  681 */       re = 
/*  688 */         re;
/*      */ 
/*  682 */       reportError(re);
/*  683 */       recover(this.input, re);
/*  684 */       retval.tree = ((CommonTree)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), re));
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*  689 */     return retval;
/*      */   }
/*      */ 
/*      */   public final tokenSpec_return tokenSpec()
/*      */     throws RecognitionException
/*      */   {
/*  701 */     tokenSpec_return retval = new tokenSpec_return();
/*  702 */     retval.start = this.input.LT(1);
/*      */ 
/*  704 */     CommonTree root_0 = null;
/*      */ 
/*  706 */     Token lit = null;
/*  707 */     Token TOKEN_REF16 = null;
/*  708 */     Token char_literal17 = null;
/*  709 */     Token char_literal18 = null;
/*      */ 
/*  711 */     CommonTree lit_tree = null;
/*  712 */     CommonTree TOKEN_REF16_tree = null;
/*  713 */     CommonTree char_literal17_tree = null;
/*  714 */     CommonTree char_literal18_tree = null;
/*  715 */     RewriteRuleTokenStream stream_STRING_LITERAL = new RewriteRuleTokenStream(this.adaptor, "token STRING_LITERAL");
/*  716 */     RewriteRuleTokenStream stream_CHAR_LITERAL = new RewriteRuleTokenStream(this.adaptor, "token CHAR_LITERAL");
/*  717 */     RewriteRuleTokenStream stream_LABEL_ASSIGN = new RewriteRuleTokenStream(this.adaptor, "token LABEL_ASSIGN");
/*  718 */     RewriteRuleTokenStream stream_71 = new RewriteRuleTokenStream(this.adaptor, "token 71");
/*  719 */     RewriteRuleTokenStream stream_TOKEN_REF = new RewriteRuleTokenStream(this.adaptor, "token TOKEN_REF");
/*      */     try
/*      */     {
/*  725 */       TOKEN_REF16 = (Token)match(this.input, 44, FOLLOW_TOKEN_REF_in_tokenSpec569); if (this.state.failed) { tokenSpec_return localtokenSpec_return1 = retval; return localtokenSpec_return1; }
/*  726 */       if (this.state.backtracking == 0) stream_TOKEN_REF.add(TOKEN_REF16);
/*      */ 
/*  729 */       int alt10 = 2;
/*      */       Object nvae;
/*  730 */       switch (this.input.LA(1))
/*      */       {
/*      */       case 41:
/*  733 */         alt10 = 1;
/*      */ 
/*  735 */         break;
/*      */       case 71:
/*  738 */         alt10 = 2;
/*      */ 
/*  740 */         break;
/*      */       default:
/*  742 */         if (this.state.backtracking > 0) { this.state.failed = true; tokenSpec_return localtokenSpec_return2 = retval; return localtokenSpec_return2; }
/*  743 */         nvae = new NoViableAltException("", 10, 0, this.input);
/*      */ 
/*  746 */         throw ((Throwable)nvae);
/*      */       }
/*      */       Object stream_retval;
/*  749 */       switch (alt10)
/*      */       {
/*      */       case 1:
/*  753 */         char_literal17 = (Token)match(this.input, 41, FOLLOW_LABEL_ASSIGN_in_tokenSpec575); if (this.state.failed) { nvae = retval; return nvae; }
/*  754 */         if (this.state.backtracking == 0) stream_LABEL_ASSIGN.add(char_literal17);
/*      */ 
/*  757 */         int alt9 = 2;
/*      */         Object nvae;
/*  758 */         switch (this.input.LA(1))
/*      */         {
/*      */         case 45:
/*  761 */           alt9 = 1;
/*      */ 
/*  763 */           break;
/*      */         case 46:
/*  766 */           alt9 = 2;
/*      */ 
/*  768 */           break;
/*      */         default:
/*  770 */           if (this.state.backtracking > 0) { this.state.failed = true; tokenSpec_return localtokenSpec_return3 = retval; return localtokenSpec_return3; }
/*  771 */           nvae = new NoViableAltException("", 9, 0, this.input);
/*      */ 
/*  774 */           throw ((Throwable)nvae);
/*      */         }
/*      */ 
/*  777 */         switch (alt9)
/*      */         {
/*      */         case 1:
/*  781 */           lit = (Token)match(this.input, 45, FOLLOW_STRING_LITERAL_in_tokenSpec580); if (this.state.failed) { nvae = retval; return nvae; }
/*  782 */           if (this.state.backtracking == 0) stream_STRING_LITERAL.add(lit); break;
/*      */         case 2:
/*  790 */           lit = (Token)match(this.input, 46, FOLLOW_CHAR_LITERAL_in_tokenSpec584); if (this.state.failed) { nvae = retval; return nvae; }
/*  791 */           if (this.state.backtracking == 0) stream_CHAR_LITERAL.add(lit);
/*      */ 
/*      */           break;
/*      */         }
/*      */ 
/*  808 */         if (this.state.backtracking == 0) {
/*  809 */           retval.tree = root_0;
/*  810 */           RewriteRuleTokenStream stream_lit = new RewriteRuleTokenStream(this.adaptor, "token lit", lit);
/*  811 */           RewriteRuleSubtreeStream stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.tree : null);
/*      */ 
/*  813 */           root_0 = (CommonTree)this.adaptor.nil();
/*      */ 
/*  818 */           CommonTree root_1 = (CommonTree)this.adaptor.nil();
/*  819 */           root_1 = (CommonTree)this.adaptor.becomeRoot(stream_LABEL_ASSIGN.nextNode(), root_1);
/*      */ 
/*  821 */           this.adaptor.addChild(root_1, stream_TOKEN_REF.nextNode());
/*  822 */           this.adaptor.addChild(root_1, stream_lit.nextNode());
/*      */ 
/*  824 */           this.adaptor.addChild(root_0, root_1);
/*      */ 
/*  829 */           retval.tree = root_0;
/*      */         }
/*  831 */         break;
/*      */       case 2:
/*  843 */         if (this.state.backtracking == 0) {
/*  844 */           retval.tree = root_0;
/*  845 */           stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.tree : null);
/*      */ 
/*  847 */           root_0 = (CommonTree)this.adaptor.nil();
/*      */ 
/*  850 */           this.adaptor.addChild(root_0, stream_TOKEN_REF.nextNode());
/*      */ 
/*  854 */           retval.tree = root_0;
/*      */         }
/*      */ 
/*      */         break;
/*      */       }
/*      */ 
/*  860 */       char_literal18 = (Token)match(this.input, 71, FOLLOW_71_in_tokenSpec623); if (this.state.failed) { stream_retval = retval; return stream_retval; }
/*  861 */       if (this.state.backtracking == 0) stream_71.add(char_literal18);
/*      */ 
/*  866 */       retval.stop = this.input.LT(-1);
/*      */ 
/*  868 */       if (this.state.backtracking == 0)
/*      */       {
/*  870 */         retval.tree = ((CommonTree)this.adaptor.rulePostProcessing(root_0));
/*  871 */         this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
/*      */       }
/*      */     } catch (RecognitionException re) {
/*  874 */       re = 
/*  881 */         re;
/*      */ 
/*  875 */       reportError(re);
/*  876 */       recover(this.input, re);
/*  877 */       retval.tree = ((CommonTree)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), re));
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*  882 */     return retval;
/*      */   }
/*      */ 
/*      */   public final attrScope_return attrScope()
/*      */     throws RecognitionException
/*      */   {
/*  894 */     attrScope_return retval = new attrScope_return();
/*  895 */     retval.start = this.input.LT(1);
/*      */ 
/*  897 */     CommonTree root_0 = null;
/*      */ 
/*  899 */     Token string_literal19 = null;
/*  900 */     Token ACTION21 = null;
/*  901 */     id_return id20 = null;
/*      */ 
/*  904 */     CommonTree string_literal19_tree = null;
/*  905 */     CommonTree ACTION21_tree = null;
/*  906 */     RewriteRuleTokenStream stream_SCOPE = new RewriteRuleTokenStream(this.adaptor, "token SCOPE");
/*  907 */     RewriteRuleTokenStream stream_ACTION = new RewriteRuleTokenStream(this.adaptor, "token ACTION");
/*  908 */     RewriteRuleSubtreeStream stream_id = new RewriteRuleSubtreeStream(this.adaptor, "rule id");
/*      */     try
/*      */     {
/*  913 */       string_literal19 = (Token)match(this.input, 30, FOLLOW_SCOPE_in_attrScope634);
/*      */       attrScope_return localattrScope_return1;
/*  913 */       if (this.state.failed) { localattrScope_return1 = retval; return localattrScope_return1; }
/*  914 */       if (this.state.backtracking == 0) stream_SCOPE.add(string_literal19);
/*      */ 
/*  916 */       pushFollow(FOLLOW_id_in_attrScope636);
/*  917 */       id20 = id();
/*      */ 
/*  919 */       this.state._fsp -= 1;
/*  920 */       if (this.state.failed) { localattrScope_return1 = retval; return localattrScope_return1; }
/*  921 */       if (this.state.backtracking == 0) stream_id.add(id20.getTree());
/*  922 */       ACTION21 = (Token)match(this.input, 47, FOLLOW_ACTION_in_attrScope638); if (this.state.failed) { localattrScope_return1 = retval; return localattrScope_return1; }
/*  923 */       if (this.state.backtracking == 0) stream_ACTION.add(ACTION21);
/*      */ 
/*  934 */       if (this.state.backtracking == 0) {
/*  935 */         retval.tree = root_0;
/*  936 */         RewriteRuleSubtreeStream stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.tree : null);
/*      */ 
/*  938 */         root_0 = (CommonTree)this.adaptor.nil();
/*      */ 
/*  943 */         CommonTree root_1 = (CommonTree)this.adaptor.nil();
/*  944 */         root_1 = (CommonTree)this.adaptor.becomeRoot(stream_SCOPE.nextNode(), root_1);
/*      */ 
/*  946 */         this.adaptor.addChild(root_1, stream_id.nextTree());
/*  947 */         this.adaptor.addChild(root_1, stream_ACTION.nextNode());
/*      */ 
/*  949 */         this.adaptor.addChild(root_0, root_1);
/*      */ 
/*  954 */         retval.tree = root_0;
/*      */       }
/*      */ 
/*  957 */       retval.stop = this.input.LT(-1);
/*      */ 
/*  959 */       if (this.state.backtracking == 0)
/*      */       {
/*  961 */         retval.tree = ((CommonTree)this.adaptor.rulePostProcessing(root_0));
/*  962 */         this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
/*      */       }
/*      */     } catch (RecognitionException re) {
/*  965 */       re = 
/*  972 */         re;
/*      */ 
/*  966 */       reportError(re);
/*  967 */       recover(this.input, re);
/*  968 */       retval.tree = ((CommonTree)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), re));
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*  973 */     return retval;
/*      */   }
/*      */ 
/*      */   public final action_return action()
/*      */     throws RecognitionException
/*      */   {
/*  985 */     action_return retval = new action_return();
/*  986 */     retval.start = this.input.LT(1);
/*      */ 
/*  988 */     CommonTree root_0 = null;
/*      */ 
/*  990 */     Token char_literal22 = null;
/*  991 */     Token string_literal24 = null;
/*  992 */     Token ACTION26 = null;
/*  993 */     actionScopeName_return actionScopeName23 = null;
/*      */ 
/*  995 */     id_return id25 = null;
/*      */ 
/*  998 */     CommonTree char_literal22_tree = null;
/*  999 */     CommonTree string_literal24_tree = null;
/* 1000 */     CommonTree ACTION26_tree = null;
/* 1001 */     RewriteRuleTokenStream stream_AT = new RewriteRuleTokenStream(this.adaptor, "token AT");
/* 1002 */     RewriteRuleTokenStream stream_ACTION = new RewriteRuleTokenStream(this.adaptor, "token ACTION");
/* 1003 */     RewriteRuleTokenStream stream_73 = new RewriteRuleTokenStream(this.adaptor, "token 73");
/* 1004 */     RewriteRuleSubtreeStream stream_id = new RewriteRuleSubtreeStream(this.adaptor, "rule id");
/* 1005 */     RewriteRuleSubtreeStream stream_actionScopeName = new RewriteRuleSubtreeStream(this.adaptor, "rule actionScopeName");
/*      */     try
/*      */     {
/* 1010 */       char_literal22 = (Token)match(this.input, 40, FOLLOW_AT_in_action661); if (this.state.failed) { action_return localaction_return1 = retval; return localaction_return1; }
/* 1011 */       if (this.state.backtracking == 0) stream_AT.add(char_literal22);
/*      */ 
/* 1014 */       int alt11 = 2;
/* 1015 */       switch (this.input.LA(1))
/*      */       {
/*      */       case 44:
/* 1018 */         switch (this.input.LA(2))
/*      */         {
/*      */         case 73:
/* 1021 */           alt11 = 1;
/*      */         }
/*      */ 
/* 1027 */         break;
/*      */       case 51:
/* 1030 */         switch (this.input.LA(2))
/*      */         {
/*      */         case 73:
/* 1033 */           alt11 = 1;
/*      */         }
/*      */ 
/* 1039 */         break;
/*      */       case 67:
/*      */       case 68:
/* 1043 */         alt11 = 1;
/*      */       }
/*      */       action_return localaction_return2;
/* 1048 */       switch (alt11)
/*      */       {
/*      */       case 1:
/* 1052 */         pushFollow(FOLLOW_actionScopeName_in_action664);
/* 1053 */         actionScopeName23 = actionScopeName();
/*      */ 
/* 1055 */         this.state._fsp -= 1;
/* 1056 */         if (this.state.failed) { localaction_return2 = retval; return localaction_return2; }
/* 1057 */         if (this.state.backtracking == 0) stream_actionScopeName.add(actionScopeName23.getTree());
/* 1058 */         string_literal24 = (Token)match(this.input, 73, FOLLOW_73_in_action666); if (this.state.failed) { localaction_return2 = retval; return localaction_return2; }
/* 1059 */         if (this.state.backtracking == 0) stream_73.add(string_literal24);
/*      */ 
/*      */         break;
/*      */       }
/*      */ 
/* 1067 */       pushFollow(FOLLOW_id_in_action670);
/* 1068 */       id25 = id();
/*      */ 
/* 1070 */       this.state._fsp -= 1;
/* 1071 */       if (this.state.failed) { localaction_return2 = retval; return localaction_return2; }
/* 1072 */       if (this.state.backtracking == 0) stream_id.add(id25.getTree());
/* 1073 */       ACTION26 = (Token)match(this.input, 47, FOLLOW_ACTION_in_action672); if (this.state.failed) { localaction_return2 = retval; return localaction_return2; }
/* 1074 */       if (this.state.backtracking == 0) stream_ACTION.add(ACTION26);
/*      */ 
/* 1085 */       if (this.state.backtracking == 0) {
/* 1086 */         retval.tree = root_0;
/* 1087 */         RewriteRuleSubtreeStream stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.tree : null);
/*      */ 
/* 1089 */         root_0 = (CommonTree)this.adaptor.nil();
/*      */ 
/* 1094 */         CommonTree root_1 = (CommonTree)this.adaptor.nil();
/* 1095 */         root_1 = (CommonTree)this.adaptor.becomeRoot(stream_AT.nextNode(), root_1);
/*      */ 
/* 1098 */         if (stream_actionScopeName.hasNext()) {
/* 1099 */           this.adaptor.addChild(root_1, stream_actionScopeName.nextTree());
/*      */         }
/*      */ 
/* 1102 */         stream_actionScopeName.reset();
/* 1103 */         this.adaptor.addChild(root_1, stream_id.nextTree());
/* 1104 */         this.adaptor.addChild(root_1, stream_ACTION.nextNode());
/*      */ 
/* 1106 */         this.adaptor.addChild(root_0, root_1);
/*      */ 
/* 1111 */         retval.tree = root_0;
/*      */       }
/*      */ 
/* 1114 */       retval.stop = this.input.LT(-1);
/*      */ 
/* 1116 */       if (this.state.backtracking == 0)
/*      */       {
/* 1118 */         retval.tree = ((CommonTree)this.adaptor.rulePostProcessing(root_0));
/* 1119 */         this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
/*      */       }
/*      */     } catch (RecognitionException re) {
/* 1122 */       re = 
/* 1129 */         re;
/*      */ 
/* 1123 */       reportError(re);
/* 1124 */       recover(this.input, re);
/* 1125 */       retval.tree = ((CommonTree)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), re));
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/* 1130 */     return retval;
/*      */   }
/*      */ 
/*      */   public final actionScopeName_return actionScopeName()
/*      */     throws RecognitionException
/*      */   {
/* 1142 */     actionScopeName_return retval = new actionScopeName_return();
/* 1143 */     retval.start = this.input.LT(1);
/*      */ 
/* 1145 */     CommonTree root_0 = null;
/*      */ 
/* 1147 */     Token l = null;
/* 1148 */     Token p = null;
/* 1149 */     id_return id27 = null;
/*      */ 
/* 1152 */     CommonTree l_tree = null;
/* 1153 */     CommonTree p_tree = null;
/* 1154 */     RewriteRuleTokenStream stream_67 = new RewriteRuleTokenStream(this.adaptor, "token 67");
/* 1155 */     RewriteRuleTokenStream stream_68 = new RewriteRuleTokenStream(this.adaptor, "token 68");
/*      */     try
/*      */     {
/* 1159 */       int alt12 = 3;
/*      */       Object nvae;
/* 1160 */       switch (this.input.LA(1))
/*      */       {
/*      */       case 44:
/*      */       case 51:
/* 1164 */         alt12 = 1;
/*      */ 
/* 1166 */         break;
/*      */       case 67:
/* 1169 */         alt12 = 2;
/*      */ 
/* 1171 */         break;
/*      */       case 68:
/* 1174 */         alt12 = 3;
/*      */ 
/* 1176 */         break;
/*      */       default:
/* 1178 */         if (this.state.backtracking > 0) { this.state.failed = true; actionScopeName_return localactionScopeName_return1 = retval; return localactionScopeName_return1; }
/* 1179 */         nvae = new NoViableAltException("", 12, 0, this.input);
/*      */ 
/* 1182 */         throw ((Throwable)nvae);
/*      */       }
/*      */       Object stream_retval;
/* 1185 */       switch (alt12)
/*      */       {
/*      */       case 1:
/* 1189 */         root_0 = (CommonTree)this.adaptor.nil();
/*      */ 
/* 1191 */         pushFollow(FOLLOW_id_in_actionScopeName698);
/* 1192 */         id27 = id();
/*      */ 
/* 1194 */         this.state._fsp -= 1;
/* 1195 */         if (this.state.failed) { nvae = retval; return nvae; }
/* 1196 */         if (this.state.backtracking == 0) this.adaptor.addChild(root_0, id27.getTree()); break;
/*      */       case 2:
/* 1203 */         l = (Token)match(this.input, 67, FOLLOW_67_in_actionScopeName705); if (this.state.failed) { nvae = retval; return nvae; }
/* 1204 */         if (this.state.backtracking == 0) stream_67.add(l);
/*      */ 
/* 1215 */         if (this.state.backtracking == 0) {
/* 1216 */           retval.tree = root_0;
/* 1217 */           stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.tree : null);
/*      */ 
/* 1219 */           root_0 = (CommonTree)this.adaptor.nil();
/*      */ 
/* 1222 */           this.adaptor.addChild(root_0, (CommonTree)this.adaptor.create(20, l));
/*      */ 
/* 1226 */           retval.tree = root_0; } break;
/*      */       case 3:
/* 1232 */         p = (Token)match(this.input, 68, FOLLOW_68_in_actionScopeName722); if (this.state.failed) { stream_retval = retval; return stream_retval; }
/* 1233 */         if (this.state.backtracking == 0) stream_68.add(p);
/*      */ 
/* 1244 */         if (this.state.backtracking == 0) {
/* 1245 */           retval.tree = root_0;
/* 1246 */           RewriteRuleSubtreeStream stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.tree : null);
/*      */ 
/* 1248 */           root_0 = (CommonTree)this.adaptor.nil();
/*      */ 
/* 1251 */           this.adaptor.addChild(root_0, (CommonTree)this.adaptor.create(20, p));
/*      */ 
/* 1255 */           retval.tree = root_0;
/*      */         }
/*      */         break;
/*      */       }
/*      */ 
/* 1260 */       retval.stop = this.input.LT(-1);
/*      */ 
/* 1262 */       if (this.state.backtracking == 0)
/*      */       {
/* 1264 */         retval.tree = ((CommonTree)this.adaptor.rulePostProcessing(root_0));
/* 1265 */         this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
/*      */       }
/*      */     } catch (RecognitionException re) {
/* 1268 */       re = 
/* 1275 */         re;
/*      */ 
/* 1269 */       reportError(re);
/* 1270 */       recover(this.input, re);
/* 1271 */       retval.tree = ((CommonTree)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), re));
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/* 1276 */     return retval;
/*      */   }
/*      */ 
/*      */   public final optionsSpec_return optionsSpec()
/*      */     throws RecognitionException
/*      */   {
/* 1288 */     optionsSpec_return retval = new optionsSpec_return();
/* 1289 */     retval.start = this.input.LT(1);
/*      */ 
/* 1291 */     CommonTree root_0 = null;
/*      */ 
/* 1293 */     Token OPTIONS28 = null;
/* 1294 */     Token char_literal30 = null;
/* 1295 */     Token char_literal31 = null;
/* 1296 */     option_return option29 = null;
/*      */ 
/* 1299 */     CommonTree OPTIONS28_tree = null;
/* 1300 */     CommonTree char_literal30_tree = null;
/* 1301 */     CommonTree char_literal31_tree = null;
/* 1302 */     RewriteRuleTokenStream stream_71 = new RewriteRuleTokenStream(this.adaptor, "token 71");
/* 1303 */     RewriteRuleTokenStream stream_72 = new RewriteRuleTokenStream(this.adaptor, "token 72");
/* 1304 */     RewriteRuleTokenStream stream_OPTIONS = new RewriteRuleTokenStream(this.adaptor, "token OPTIONS");
/* 1305 */     RewriteRuleSubtreeStream stream_option = new RewriteRuleSubtreeStream(this.adaptor, "rule option");
/*      */     try
/*      */     {
/* 1310 */       OPTIONS28 = (Token)match(this.input, 48, FOLLOW_OPTIONS_in_optionsSpec738); if (this.state.failed) { optionsSpec_return localoptionsSpec_return1 = retval; return localoptionsSpec_return1; }
/* 1311 */       if (this.state.backtracking == 0) stream_OPTIONS.add(OPTIONS28); 
/*      */ int cnt13 = 0;
/*      */       int alt13;
/*      */       while (true)
/*      */       {
/* 1317 */         alt13 = 2;
/* 1318 */         switch (this.input.LA(1))
/*      */         {
/*      */         case 44:
/*      */         case 51:
/* 1322 */           alt13 = 1;
/*      */         }
/*      */         optionsSpec_return localoptionsSpec_return2;
/* 1328 */         switch (alt13)
/*      */         {
/*      */         case 1:
/* 1332 */           pushFollow(FOLLOW_option_in_optionsSpec741);
/* 1333 */           option29 = option();
/*      */ 
/* 1335 */           this.state._fsp -= 1;
/* 1336 */           if (this.state.failed) { localoptionsSpec_return2 = retval; return localoptionsSpec_return2; }
/* 1337 */           if (this.state.backtracking == 0) stream_option.add(option29.getTree());
/* 1338 */           char_literal30 = (Token)match(this.input, 71, FOLLOW_71_in_optionsSpec743); if (this.state.failed) { localoptionsSpec_return2 = retval; return localoptionsSpec_return2; }
/* 1339 */           if (this.state.backtracking == 0) stream_71.add(char_literal30); break;
/*      */         default:
/* 1346 */           if (cnt13 >= 1) break label393;
/* 1347 */           if (this.state.backtracking > 0) { this.state.failed = true; localoptionsSpec_return2 = retval; return localoptionsSpec_return2; }
/* 1348 */           EarlyExitException eee = new EarlyExitException(13, this.input);
/*      */ 
/* 1350 */           throw eee;
/*      */         }
/* 1352 */         cnt13++;
/*      */       }
/*      */ 
/* 1355 */       label393: char_literal31 = (Token)match(this.input, 72, FOLLOW_72_in_optionsSpec747); if (this.state.failed) { alt13 = retval; return alt13; }
/* 1356 */       if (this.state.backtracking == 0) stream_72.add(char_literal31);
/*      */ 
/* 1367 */       if (this.state.backtracking == 0) {
/* 1368 */         retval.tree = root_0;
/* 1369 */         RewriteRuleSubtreeStream stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.tree : null);
/*      */ 
/* 1371 */         root_0 = (CommonTree)this.adaptor.nil();
/*      */ 
/* 1376 */         CommonTree root_1 = (CommonTree)this.adaptor.nil();
/* 1377 */         root_1 = (CommonTree)this.adaptor.becomeRoot(stream_OPTIONS.nextNode(), root_1);
/*      */ 
/* 1379 */         if (!stream_option.hasNext()) {
/* 1380 */           throw new RewriteEarlyExitException();
/*      */         }
/* 1382 */         while (stream_option.hasNext()) {
/* 1383 */           this.adaptor.addChild(root_1, stream_option.nextTree());
/*      */         }
/*      */ 
/* 1386 */         stream_option.reset();
/*      */ 
/* 1388 */         this.adaptor.addChild(root_0, root_1);
/*      */ 
/* 1393 */         retval.tree = root_0;
/*      */       }
/*      */ 
/* 1396 */       retval.stop = this.input.LT(-1);
/*      */ 
/* 1398 */       if (this.state.backtracking == 0)
/*      */       {
/* 1400 */         retval.tree = ((CommonTree)this.adaptor.rulePostProcessing(root_0));
/* 1401 */         this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
/*      */       }
/*      */     } catch (RecognitionException re) {
/* 1404 */       re = 
/* 1411 */         re;
/*      */ 
/* 1405 */       reportError(re);
/* 1406 */       recover(this.input, re);
/* 1407 */       retval.tree = ((CommonTree)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), re));
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/* 1412 */     return retval;
/*      */   }
/*      */ 
/*      */   public final option_return option()
/*      */     throws RecognitionException
/*      */   {
/* 1424 */     option_return retval = new option_return();
/* 1425 */     retval.start = this.input.LT(1);
/*      */ 
/* 1427 */     CommonTree root_0 = null;
/*      */ 
/* 1429 */     Token char_literal33 = null;
/* 1430 */     id_return id32 = null;
/*      */ 
/* 1432 */     optionValue_return optionValue34 = null;
/*      */ 
/* 1435 */     CommonTree char_literal33_tree = null;
/* 1436 */     RewriteRuleTokenStream stream_LABEL_ASSIGN = new RewriteRuleTokenStream(this.adaptor, "token LABEL_ASSIGN");
/* 1437 */     RewriteRuleSubtreeStream stream_id = new RewriteRuleSubtreeStream(this.adaptor, "rule id");
/* 1438 */     RewriteRuleSubtreeStream stream_optionValue = new RewriteRuleSubtreeStream(this.adaptor, "rule optionValue");
/*      */     try
/*      */     {
/* 1443 */       pushFollow(FOLLOW_id_in_option772);
/* 1444 */       id32 = id();
/*      */ 
/* 1446 */       this.state._fsp -= 1;
/*      */       option_return localoption_return1;
/* 1447 */       if (this.state.failed) { localoption_return1 = retval; return localoption_return1; }
/* 1448 */       if (this.state.backtracking == 0) stream_id.add(id32.getTree());
/* 1449 */       char_literal33 = (Token)match(this.input, 41, FOLLOW_LABEL_ASSIGN_in_option774); if (this.state.failed) { localoption_return1 = retval; return localoption_return1; }
/* 1450 */       if (this.state.backtracking == 0) stream_LABEL_ASSIGN.add(char_literal33);
/*      */ 
/* 1452 */       pushFollow(FOLLOW_optionValue_in_option776);
/* 1453 */       optionValue34 = optionValue();
/*      */ 
/* 1455 */       this.state._fsp -= 1;
/* 1456 */       if (this.state.failed) { localoption_return1 = retval; return localoption_return1; }
/* 1457 */       if (this.state.backtracking == 0) stream_optionValue.add(optionValue34.getTree());
/*      */ 
/* 1467 */       if (this.state.backtracking == 0) {
/* 1468 */         retval.tree = root_0;
/* 1469 */         RewriteRuleSubtreeStream stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.tree : null);
/*      */ 
/* 1471 */         root_0 = (CommonTree)this.adaptor.nil();
/*      */ 
/* 1476 */         CommonTree root_1 = (CommonTree)this.adaptor.nil();
/* 1477 */         root_1 = (CommonTree)this.adaptor.becomeRoot(stream_LABEL_ASSIGN.nextNode(), root_1);
/*      */ 
/* 1479 */         this.adaptor.addChild(root_1, stream_id.nextTree());
/* 1480 */         this.adaptor.addChild(root_1, stream_optionValue.nextTree());
/*      */ 
/* 1482 */         this.adaptor.addChild(root_0, root_1);
/*      */ 
/* 1487 */         retval.tree = root_0;
/*      */       }
/*      */ 
/* 1490 */       retval.stop = this.input.LT(-1);
/*      */ 
/* 1492 */       if (this.state.backtracking == 0)
/*      */       {
/* 1494 */         retval.tree = ((CommonTree)this.adaptor.rulePostProcessing(root_0));
/* 1495 */         this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
/*      */       }
/*      */     } catch (RecognitionException re) {
/* 1498 */       re = 
/* 1505 */         re;
/*      */ 
/* 1499 */       reportError(re);
/* 1500 */       recover(this.input, re);
/* 1501 */       retval.tree = ((CommonTree)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), re));
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/* 1506 */     return retval;
/*      */   }
/*      */ 
/*      */   public final optionValue_return optionValue()
/*      */     throws RecognitionException
/*      */   {
/* 1518 */     optionValue_return retval = new optionValue_return();
/* 1519 */     retval.start = this.input.LT(1);
/*      */ 
/* 1521 */     CommonTree root_0 = null;
/*      */ 
/* 1523 */     Token s = null;
/* 1524 */     Token STRING_LITERAL36 = null;
/* 1525 */     Token CHAR_LITERAL37 = null;
/* 1526 */     Token INT38 = null;
/* 1527 */     qid_return qid35 = null;
/*      */ 
/* 1530 */     CommonTree s_tree = null;
/* 1531 */     CommonTree STRING_LITERAL36_tree = null;
/* 1532 */     CommonTree CHAR_LITERAL37_tree = null;
/* 1533 */     CommonTree INT38_tree = null;
/* 1534 */     RewriteRuleTokenStream stream_74 = new RewriteRuleTokenStream(this.adaptor, "token 74");
/*      */     try
/*      */     {
/* 1538 */       int alt14 = 5;
/*      */       Object nvae;
/* 1539 */       switch (this.input.LA(1))
/*      */       {
/*      */       case 44:
/*      */       case 51:
/* 1543 */         alt14 = 1;
/*      */ 
/* 1545 */         break;
/*      */       case 45:
/* 1548 */         alt14 = 2;
/*      */ 
/* 1550 */         break;
/*      */       case 46:
/* 1553 */         alt14 = 3;
/*      */ 
/* 1555 */         break;
/*      */       case 49:
/* 1558 */         alt14 = 4;
/*      */ 
/* 1560 */         break;
/*      */       case 74:
/* 1563 */         alt14 = 5;
/*      */ 
/* 1565 */         break;
/*      */       default:
/* 1567 */         if (this.state.backtracking > 0) { this.state.failed = true; optionValue_return localoptionValue_return1 = retval; return localoptionValue_return1; }
/* 1568 */         nvae = new NoViableAltException("", 14, 0, this.input);
/*      */ 
/* 1571 */         throw ((Throwable)nvae);
/*      */       }
/*      */ 
/* 1574 */       switch (alt14)
/*      */       {
/*      */       case 1:
/* 1578 */         root_0 = (CommonTree)this.adaptor.nil();
/*      */ 
/* 1580 */         pushFollow(FOLLOW_qid_in_optionValue805);
/* 1581 */         qid35 = qid();
/*      */ 
/* 1583 */         this.state._fsp -= 1;
/* 1584 */         if (this.state.failed) { nvae = retval; return nvae; }
/* 1585 */         if (this.state.backtracking == 0) this.adaptor.addChild(root_0, qid35.getTree()); break;
/*      */       case 2:
/* 1592 */         root_0 = (CommonTree)this.adaptor.nil();
/*      */ 
/* 1594 */         STRING_LITERAL36 = (Token)match(this.input, 45, FOLLOW_STRING_LITERAL_in_optionValue815); if (this.state.failed) { nvae = retval; return nvae; }
/* 1595 */         if (this.state.backtracking == 0) {
/* 1596 */           STRING_LITERAL36_tree = (CommonTree)this.adaptor.create(STRING_LITERAL36);
/* 1597 */           this.adaptor.addChild(root_0, STRING_LITERAL36_tree); } break;
/*      */       case 3:
/* 1605 */         root_0 = (CommonTree)this.adaptor.nil();
/*      */ 
/* 1607 */         CHAR_LITERAL37 = (Token)match(this.input, 46, FOLLOW_CHAR_LITERAL_in_optionValue825); if (this.state.failed) { nvae = retval; return nvae; }
/* 1608 */         if (this.state.backtracking == 0) {
/* 1609 */           CHAR_LITERAL37_tree = (CommonTree)this.adaptor.create(CHAR_LITERAL37);
/* 1610 */           this.adaptor.addChild(root_0, CHAR_LITERAL37_tree); } break;
/*      */       case 4:
/* 1618 */         root_0 = (CommonTree)this.adaptor.nil();
/*      */ 
/* 1620 */         INT38 = (Token)match(this.input, 49, FOLLOW_INT_in_optionValue835); if (this.state.failed) { nvae = retval; return nvae; }
/* 1621 */         if (this.state.backtracking == 0) {
/* 1622 */           INT38_tree = (CommonTree)this.adaptor.create(INT38);
/* 1623 */           this.adaptor.addChild(root_0, INT38_tree); } break;
/*      */       case 5:
/* 1631 */         s = (Token)match(this.input, 74, FOLLOW_74_in_optionValue845); if (this.state.failed) { nvae = retval; return nvae; }
/* 1632 */         if (this.state.backtracking == 0) stream_74.add(s);
/*      */ 
/* 1643 */         if (this.state.backtracking == 0) {
/* 1644 */           retval.tree = root_0;
/* 1645 */           RewriteRuleSubtreeStream stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.tree : null);
/*      */ 
/* 1647 */           root_0 = (CommonTree)this.adaptor.nil();
/*      */ 
/* 1650 */           this.adaptor.addChild(root_0, (CommonTree)this.adaptor.create(45, s));
/*      */ 
/* 1654 */           retval.tree = root_0;
/*      */         }
/*      */         break;
/*      */       }
/*      */ 
/* 1659 */       retval.stop = this.input.LT(-1);
/*      */ 
/* 1661 */       if (this.state.backtracking == 0)
/*      */       {
/* 1663 */         retval.tree = ((CommonTree)this.adaptor.rulePostProcessing(root_0));
/* 1664 */         this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
/*      */       }
/*      */     } catch (RecognitionException re) {
/* 1667 */       re = 
/* 1674 */         re;
/*      */ 
/* 1668 */       reportError(re);
/* 1669 */       recover(this.input, re);
/* 1670 */       retval.tree = ((CommonTree)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), re));
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/* 1675 */     return retval;
/*      */   }
/*      */ 
/*      */   public final rule_return rule()
/*      */     throws RecognitionException
/*      */   {
/* 1692 */     this.rule_stack.push(new rule_scope());
/* 1693 */     rule_return retval = new rule_return();
/* 1694 */     retval.start = this.input.LT(1);
/*      */ 
/* 1696 */     CommonTree root_0 = null;
/*      */ 
/* 1698 */     Token modifier = null;
/* 1699 */     Token arg = null;
/* 1700 */     Token rt = null;
/* 1701 */     Token DOC_COMMENT39 = null;
/* 1702 */     Token string_literal40 = null;
/* 1703 */     Token string_literal41 = null;
/* 1704 */     Token string_literal42 = null;
/* 1705 */     Token string_literal43 = null;
/* 1706 */     Token char_literal45 = null;
/* 1707 */     Token string_literal46 = null;
/* 1708 */     Token char_literal51 = null;
/* 1709 */     Token char_literal53 = null;
/* 1710 */     id_return id44 = null;
/*      */ 
/* 1712 */     throwsSpec_return throwsSpec47 = null;
/*      */ 
/* 1714 */     optionsSpec_return optionsSpec48 = null;
/*      */ 
/* 1716 */     ruleScopeSpec_return ruleScopeSpec49 = null;
/*      */ 
/* 1718 */     ruleAction_return ruleAction50 = null;
/*      */ 
/* 1720 */     altList_return altList52 = null;
/*      */ 
/* 1722 */     exceptionGroup_return exceptionGroup54 = null;
/*      */ 
/* 1725 */     CommonTree modifier_tree = null;
/* 1726 */     CommonTree arg_tree = null;
/* 1727 */     CommonTree rt_tree = null;
/* 1728 */     CommonTree DOC_COMMENT39_tree = null;
/* 1729 */     CommonTree string_literal40_tree = null;
/* 1730 */     CommonTree string_literal41_tree = null;
/* 1731 */     CommonTree string_literal42_tree = null;
/* 1732 */     CommonTree string_literal43_tree = null;
/* 1733 */     CommonTree char_literal45_tree = null;
/* 1734 */     CommonTree string_literal46_tree = null;
/* 1735 */     CommonTree char_literal51_tree = null;
/* 1736 */     CommonTree char_literal53_tree = null;
/* 1737 */     RewriteRuleTokenStream stream_DOC_COMMENT = new RewriteRuleTokenStream(this.adaptor, "token DOC_COMMENT");
/* 1738 */     RewriteRuleTokenStream stream_78 = new RewriteRuleTokenStream(this.adaptor, "token 78");
/* 1739 */     RewriteRuleTokenStream stream_RET = new RewriteRuleTokenStream(this.adaptor, "token RET");
/* 1740 */     RewriteRuleTokenStream stream_77 = new RewriteRuleTokenStream(this.adaptor, "token 77");
/* 1741 */     RewriteRuleTokenStream stream_BANG = new RewriteRuleTokenStream(this.adaptor, "token BANG");
/* 1742 */     RewriteRuleTokenStream stream_71 = new RewriteRuleTokenStream(this.adaptor, "token 71");
/* 1743 */     RewriteRuleTokenStream stream_FRAGMENT = new RewriteRuleTokenStream(this.adaptor, "token FRAGMENT");
/* 1744 */     RewriteRuleTokenStream stream_75 = new RewriteRuleTokenStream(this.adaptor, "token 75");
/* 1745 */     RewriteRuleTokenStream stream_ARG_ACTION = new RewriteRuleTokenStream(this.adaptor, "token ARG_ACTION");
/* 1746 */     RewriteRuleTokenStream stream_76 = new RewriteRuleTokenStream(this.adaptor, "token 76");
/* 1747 */     RewriteRuleSubtreeStream stream_id = new RewriteRuleSubtreeStream(this.adaptor, "rule id");
/* 1748 */     RewriteRuleSubtreeStream stream_exceptionGroup = new RewriteRuleSubtreeStream(this.adaptor, "rule exceptionGroup");
/* 1749 */     RewriteRuleSubtreeStream stream_throwsSpec = new RewriteRuleSubtreeStream(this.adaptor, "rule throwsSpec");
/* 1750 */     RewriteRuleSubtreeStream stream_ruleScopeSpec = new RewriteRuleSubtreeStream(this.adaptor, "rule ruleScopeSpec");
/* 1751 */     RewriteRuleSubtreeStream stream_optionsSpec = new RewriteRuleSubtreeStream(this.adaptor, "rule optionsSpec");
/* 1752 */     RewriteRuleSubtreeStream stream_altList = new RewriteRuleSubtreeStream(this.adaptor, "rule altList");
/* 1753 */     RewriteRuleSubtreeStream stream_ruleAction = new RewriteRuleSubtreeStream(this.adaptor, "rule ruleAction");
/*      */     try
/*      */     {
/* 1759 */       int alt15 = 2;
/* 1760 */       switch (this.input.LA(1))
/*      */       {
/*      */       case 4:
/* 1763 */         alt15 = 1;
/*      */       }
/*      */ 
/* 1768 */       switch (alt15)
/*      */       {
/*      */       case 1:
/* 1772 */         DOC_COMMENT39 = (Token)match(this.input, 4, FOLLOW_DOC_COMMENT_in_rule870); if (this.state.failed) return retval;
/* 1773 */         if (this.state.backtracking == 0) stream_DOC_COMMENT.add(DOC_COMMENT39);
/*      */ 
/*      */         break;
/*      */       }
/*      */ 
/* 1782 */       int alt17 = 2;
/* 1783 */       switch (this.input.LA(1))
/*      */       {
/*      */       case 35:
/*      */       case 75:
/*      */       case 76:
/*      */       case 77:
/* 1789 */         alt17 = 1;
/*      */       }
/*      */       int alt16;
/*      */       Object nvae;
/* 1794 */       switch (alt17)
/*      */       {
/*      */       case 1:
/* 1799 */         alt16 = 4;
/* 1800 */         switch (this.input.LA(1))
/*      */         {
/*      */         case 75:
/* 1803 */           alt16 = 1;
/*      */ 
/* 1805 */           break;
/*      */         case 76:
/* 1808 */           alt16 = 2;
/*      */ 
/* 1810 */           break;
/*      */         case 77:
/* 1813 */           alt16 = 3;
/*      */ 
/* 1815 */           break;
/*      */         case 35:
/* 1818 */           alt16 = 4;
/*      */ 
/* 1820 */           break;
/*      */         default:
/* 1822 */           if (this.state.backtracking > 0) { this.state.failed = true; return retval; }
/* 1823 */           nvae = new NoViableAltException("", 16, 0, this.input);
/*      */ 
/* 1826 */           throw ((Throwable)nvae);
/*      */         }
/*      */ 
/* 1829 */         switch (alt16)
/*      */         {
/*      */         case 1:
/* 1833 */           string_literal40 = (Token)match(this.input, 75, FOLLOW_75_in_rule880); if (this.state.failed) return retval;
/* 1834 */           if (this.state.backtracking == 0) stream_75.add(string_literal40); break;
/*      */         case 2:
/* 1842 */           string_literal41 = (Token)match(this.input, 76, FOLLOW_76_in_rule882); if (this.state.failed) return retval;
/* 1843 */           if (this.state.backtracking == 0) stream_76.add(string_literal41); break;
/*      */         case 3:
/* 1851 */           string_literal42 = (Token)match(this.input, 77, FOLLOW_77_in_rule884); if (this.state.failed) return retval;
/* 1852 */           if (this.state.backtracking == 0) stream_77.add(string_literal42); break;
/*      */         case 4:
/* 1860 */           string_literal43 = (Token)match(this.input, 35, FOLLOW_FRAGMENT_in_rule886); if (this.state.failed) return retval;
/* 1861 */           if (this.state.backtracking == 0) stream_FRAGMENT.add(string_literal43);
/*      */ 
/*      */           break;
/*      */         }
/*      */ 
/*      */         break;
/*      */       }
/*      */ 
/* 1875 */       pushFollow(FOLLOW_id_in_rule894);
/* 1876 */       id44 = id();
/*      */ 
/* 1878 */       this.state._fsp -= 1;
/* 1879 */       if (this.state.failed) return retval;
/* 1880 */       if (this.state.backtracking == 0) stream_id.add(id44.getTree());
/* 1881 */       if (this.state.backtracking == 0) {
/* 1882 */         ((rule_scope)this.rule_stack.peek()).name = (id44 != null ? this.input.toString(id44.start, id44.stop) : null);
/*      */       }
/*      */ 
/* 1885 */       int alt18 = 2;
/* 1886 */       switch (this.input.LA(1))
/*      */       {
/*      */       case 38:
/* 1889 */         alt18 = 1;
/*      */       }
/*      */ 
/* 1894 */       switch (alt18)
/*      */       {
/*      */       case 1:
/* 1898 */         char_literal45 = (Token)match(this.input, 38, FOLLOW_BANG_in_rule900); if (this.state.failed) return retval;
/* 1899 */         if (this.state.backtracking == 0) stream_BANG.add(char_literal45);
/*      */ 
/*      */         break;
/*      */       }
/*      */ 
/* 1908 */       int alt19 = 2;
/* 1909 */       switch (this.input.LA(1))
/*      */       {
/*      */       case 50:
/* 1912 */         alt19 = 1;
/*      */       }
/*      */ 
/* 1917 */       switch (alt19)
/*      */       {
/*      */       case 1:
/* 1921 */         arg = (Token)match(this.input, 50, FOLLOW_ARG_ACTION_in_rule909); if (this.state.failed) return retval;
/* 1922 */         if (this.state.backtracking == 0) stream_ARG_ACTION.add(arg);
/*      */ 
/*      */         break;
/*      */       }
/*      */ 
/* 1931 */       int alt20 = 2;
/* 1932 */       switch (this.input.LA(1))
/*      */       {
/*      */       case 23:
/* 1935 */         alt20 = 1;
/*      */       }
/*      */ 
/* 1940 */       switch (alt20)
/*      */       {
/*      */       case 1:
/* 1944 */         string_literal46 = (Token)match(this.input, 23, FOLLOW_RET_in_rule918);
/*      */         rule_return localrule_return4;
/* 1944 */         if (this.state.failed) return retval;
/* 1945 */         if (this.state.backtracking == 0) stream_RET.add(string_literal46);
/*      */ 
/* 1947 */         rt = (Token)match(this.input, 50, FOLLOW_ARG_ACTION_in_rule922); if (this.state.failed) return retval;
/* 1948 */         if (this.state.backtracking == 0) stream_ARG_ACTION.add(rt);
/*      */ 
/*      */         break;
/*      */       }
/*      */ 
/* 1957 */       int alt21 = 2;
/* 1958 */       switch (this.input.LA(1))
/*      */       {
/*      */       case 79:
/* 1961 */         alt21 = 1;
/*      */       }
/*      */ 
/* 1966 */       switch (alt21)
/*      */       {
/*      */       case 1:
/* 1970 */         pushFollow(FOLLOW_throwsSpec_in_rule930);
/* 1971 */         throwsSpec47 = throwsSpec();
/*      */ 
/* 1973 */         this.state._fsp -= 1;
/* 1974 */         if (this.state.failed) return retval;
/* 1975 */         if (this.state.backtracking == 0) stream_throwsSpec.add(throwsSpec47.getTree());
/*      */ 
/*      */         break;
/*      */       }
/*      */ 
/* 1983 */       int alt22 = 2;
/* 1984 */       switch (this.input.LA(1))
/*      */       {
/*      */       case 48:
/* 1987 */         alt22 = 1;
/*      */       }
/*      */ 
/* 1992 */       switch (alt22)
/*      */       {
/*      */       case 1:
/* 1996 */         pushFollow(FOLLOW_optionsSpec_in_rule933);
/* 1997 */         optionsSpec48 = optionsSpec();
/*      */ 
/* 1999 */         this.state._fsp -= 1;
/* 2000 */         if (this.state.failed) return retval;
/* 2001 */         if (this.state.backtracking == 0) stream_optionsSpec.add(optionsSpec48.getTree());
/*      */ 
/*      */         break;
/*      */       }
/*      */ 
/* 2009 */       int alt23 = 2;
/* 2010 */       switch (this.input.LA(1))
/*      */       {
/*      */       case 30:
/* 2013 */         alt23 = 1;
/*      */       }
/*      */ 
/* 2018 */       switch (alt23)
/*      */       {
/*      */       case 1:
/* 2022 */         pushFollow(FOLLOW_ruleScopeSpec_in_rule936);
/* 2023 */         ruleScopeSpec49 = ruleScopeSpec();
/*      */ 
/* 2025 */         this.state._fsp -= 1;
/* 2026 */         if (this.state.failed) return retval;
/* 2027 */         if (this.state.backtracking == 0) stream_ruleScopeSpec.add(ruleScopeSpec49.getTree());
/*      */ 
/*      */         break;
/*      */       }
/*      */ 
/*      */       int alt24;
/*      */       rule_return localrule_return9;
/*      */       while (true)
/*      */       {
/* 2037 */         alt24 = 2;
/* 2038 */         switch (this.input.LA(1))
/*      */         {
/*      */         case 40:
/* 2041 */           alt24 = 1;
/*      */         }
/*      */ 
/* 2047 */         switch (alt24)
/*      */         {
/*      */         case 1:
/* 2051 */           pushFollow(FOLLOW_ruleAction_in_rule939);
/* 2052 */           ruleAction50 = ruleAction();
/*      */ 
/* 2054 */           this.state._fsp -= 1;
/* 2055 */           if (this.state.failed) return retval;
/* 2056 */           if (this.state.backtracking == 0) stream_ruleAction.add(ruleAction50.getTree()); break;
/*      */         default:
/* 2062 */           break label2015;
/*      */         }
/*      */       }
/*      */ 
/* 2066 */       label2015: char_literal51 = (Token)match(this.input, 78, FOLLOW_78_in_rule944);
/*      */       rule_return localrule_return8;
/* 2066 */       if (this.state.failed) return retval;
/* 2067 */       if (this.state.backtracking == 0) stream_78.add(char_literal51);
/*      */ 
/* 2069 */       pushFollow(FOLLOW_altList_in_rule946);
/* 2070 */       altList52 = altList();
/*      */ 
/* 2072 */       this.state._fsp -= 1;
/* 2073 */       if (this.state.failed) return retval;
/* 2074 */       if (this.state.backtracking == 0) stream_altList.add(altList52.getTree());
/* 2075 */       char_literal53 = (Token)match(this.input, 71, FOLLOW_71_in_rule948); if (this.state.failed) return retval;
/* 2076 */       if (this.state.backtracking == 0) stream_71.add(char_literal53);
/*      */ 
/* 2079 */       int alt25 = 2;
/* 2080 */       switch (this.input.LA(1))
/*      */       {
/*      */       case 84:
/*      */       case 85:
/* 2084 */         alt25 = 1;
/*      */       }
/*      */ 
/* 2089 */       switch (alt25)
/*      */       {
/*      */       case 1:
/* 2093 */         pushFollow(FOLLOW_exceptionGroup_in_rule952);
/* 2094 */         exceptionGroup54 = exceptionGroup();
/*      */ 
/* 2096 */         this.state._fsp -= 1;
/* 2097 */         if (this.state.failed) return retval;
/* 2098 */         if (this.state.backtracking == 0) stream_exceptionGroup.add(exceptionGroup54.getTree());
/*      */ 
/*      */         break;
/*      */       }
/*      */ 
/* 2114 */       if (this.state.backtracking == 0) {
/* 2115 */         retval.tree = root_0;
/* 2116 */         RewriteRuleTokenStream stream_arg = new RewriteRuleTokenStream(this.adaptor, "token arg", arg);
/* 2117 */         RewriteRuleTokenStream stream_rt = new RewriteRuleTokenStream(this.adaptor, "token rt", rt);
/* 2118 */         RewriteRuleSubtreeStream stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.tree : null);
/*      */ 
/* 2120 */         root_0 = (CommonTree)this.adaptor.nil();
/*      */ 
/* 2125 */         CommonTree root_1 = (CommonTree)this.adaptor.nil();
/* 2126 */         root_1 = (CommonTree)this.adaptor.becomeRoot((CommonTree)this.adaptor.create(7, "RULE"), root_1);
/*      */ 
/* 2128 */         this.adaptor.addChild(root_1, stream_id.nextTree());
/* 2129 */         this.adaptor.addChild(root_1, modifier != null ? this.adaptor.create(modifier) : null);
/*      */ 
/* 2131 */         if (stream_arg.hasNext())
/*      */         {
/* 2134 */           CommonTree root_2 = (CommonTree)this.adaptor.nil();
/* 2135 */           root_2 = (CommonTree)this.adaptor.becomeRoot((CommonTree)this.adaptor.create(21, arg), root_2);
/*      */ 
/* 2137 */           this.adaptor.addChild(root_2, stream_arg.nextNode());
/*      */ 
/* 2139 */           this.adaptor.addChild(root_1, root_2);
/*      */         }
/*      */ 
/* 2143 */         stream_arg.reset();
/*      */ 
/* 2145 */         if ((stream_RET.hasNext()) || (stream_rt.hasNext()))
/*      */         {
/* 2148 */           CommonTree root_2 = (CommonTree)this.adaptor.nil();
/* 2149 */           root_2 = (CommonTree)this.adaptor.becomeRoot(stream_RET.nextNode(), root_2);
/*      */ 
/* 2151 */           this.adaptor.addChild(root_2, stream_rt.nextNode());
/*      */ 
/* 2153 */           this.adaptor.addChild(root_1, root_2);
/*      */         }
/*      */ 
/* 2157 */         stream_RET.reset();
/* 2158 */         stream_rt.reset();
/*      */ 
/* 2160 */         if (stream_throwsSpec.hasNext()) {
/* 2161 */           this.adaptor.addChild(root_1, stream_throwsSpec.nextTree());
/*      */         }
/*      */ 
/* 2164 */         stream_throwsSpec.reset();
/*      */ 
/* 2166 */         if (stream_optionsSpec.hasNext()) {
/* 2167 */           this.adaptor.addChild(root_1, stream_optionsSpec.nextTree());
/*      */         }
/*      */ 
/* 2170 */         stream_optionsSpec.reset();
/*      */ 
/* 2172 */         if (stream_ruleScopeSpec.hasNext()) {
/* 2173 */           this.adaptor.addChild(root_1, stream_ruleScopeSpec.nextTree());
/*      */         }
/*      */ 
/* 2176 */         stream_ruleScopeSpec.reset();
/*      */ 
/* 2178 */         while (stream_ruleAction.hasNext()) {
/* 2179 */           this.adaptor.addChild(root_1, stream_ruleAction.nextTree());
/*      */         }
/*      */ 
/* 2182 */         stream_ruleAction.reset();
/* 2183 */         this.adaptor.addChild(root_1, stream_altList.nextTree());
/*      */ 
/* 2185 */         if (stream_exceptionGroup.hasNext()) {
/* 2186 */           this.adaptor.addChild(root_1, stream_exceptionGroup.nextTree());
/*      */         }
/*      */ 
/* 2189 */         stream_exceptionGroup.reset();
/* 2190 */         this.adaptor.addChild(root_1, (CommonTree)this.adaptor.create(17, "EOR"));
/*      */ 
/* 2192 */         this.adaptor.addChild(root_0, root_1);
/*      */ 
/* 2197 */         retval.tree = root_0;
/*      */       }
/*      */ 
/* 2200 */       retval.stop = this.input.LT(-1);
/*      */ 
/* 2202 */       if (this.state.backtracking == 0)
/*      */       {
/* 2204 */         retval.tree = ((CommonTree)this.adaptor.rulePostProcessing(root_0));
/* 2205 */         this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
/*      */       }
/*      */     }
/*      */     catch (RecognitionException re) {
/* 2209 */       reportError(re);
/* 2210 */       recover(this.input, re);
/* 2211 */       retval.tree = ((CommonTree)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), re));
/*      */     }
/*      */     finally
/*      */     {
/* 2215 */       this.rule_stack.pop();
/*      */     }
/* 2217 */     return retval;
/*      */   }
/*      */ 
/*      */   public final ruleAction_return ruleAction()
/*      */     throws RecognitionException
/*      */   {
/* 2229 */     ruleAction_return retval = new ruleAction_return();
/* 2230 */     retval.start = this.input.LT(1);
/*      */ 
/* 2232 */     CommonTree root_0 = null;
/*      */ 
/* 2234 */     Token char_literal55 = null;
/* 2235 */     Token ACTION57 = null;
/* 2236 */     id_return id56 = null;
/*      */ 
/* 2239 */     CommonTree char_literal55_tree = null;
/* 2240 */     CommonTree ACTION57_tree = null;
/* 2241 */     RewriteRuleTokenStream stream_AT = new RewriteRuleTokenStream(this.adaptor, "token AT");
/* 2242 */     RewriteRuleTokenStream stream_ACTION = new RewriteRuleTokenStream(this.adaptor, "token ACTION");
/* 2243 */     RewriteRuleSubtreeStream stream_id = new RewriteRuleSubtreeStream(this.adaptor, "rule id");
/*      */     try
/*      */     {
/* 2248 */       char_literal55 = (Token)match(this.input, 40, FOLLOW_AT_in_ruleAction1058);
/*      */       ruleAction_return localruleAction_return1;
/* 2248 */       if (this.state.failed) { localruleAction_return1 = retval; return localruleAction_return1; }
/* 2249 */       if (this.state.backtracking == 0) stream_AT.add(char_literal55);
/*      */ 
/* 2251 */       pushFollow(FOLLOW_id_in_ruleAction1060);
/* 2252 */       id56 = id();
/*      */ 
/* 2254 */       this.state._fsp -= 1;
/* 2255 */       if (this.state.failed) { localruleAction_return1 = retval; return localruleAction_return1; }
/* 2256 */       if (this.state.backtracking == 0) stream_id.add(id56.getTree());
/* 2257 */       ACTION57 = (Token)match(this.input, 47, FOLLOW_ACTION_in_ruleAction1062); if (this.state.failed) { localruleAction_return1 = retval; return localruleAction_return1; }
/* 2258 */       if (this.state.backtracking == 0) stream_ACTION.add(ACTION57);
/*      */ 
/* 2269 */       if (this.state.backtracking == 0) {
/* 2270 */         retval.tree = root_0;
/* 2271 */         RewriteRuleSubtreeStream stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.tree : null);
/*      */ 
/* 2273 */         root_0 = (CommonTree)this.adaptor.nil();
/*      */ 
/* 2278 */         CommonTree root_1 = (CommonTree)this.adaptor.nil();
/* 2279 */         root_1 = (CommonTree)this.adaptor.becomeRoot(stream_AT.nextNode(), root_1);
/*      */ 
/* 2281 */         this.adaptor.addChild(root_1, stream_id.nextTree());
/* 2282 */         this.adaptor.addChild(root_1, stream_ACTION.nextNode());
/*      */ 
/* 2284 */         this.adaptor.addChild(root_0, root_1);
/*      */ 
/* 2289 */         retval.tree = root_0;
/*      */       }
/*      */ 
/* 2292 */       retval.stop = this.input.LT(-1);
/*      */ 
/* 2294 */       if (this.state.backtracking == 0)
/*      */       {
/* 2296 */         retval.tree = ((CommonTree)this.adaptor.rulePostProcessing(root_0));
/* 2297 */         this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
/*      */       }
/*      */     } catch (RecognitionException re) {
/* 2300 */       re = 
/* 2307 */         re;
/*      */ 
/* 2301 */       reportError(re);
/* 2302 */       recover(this.input, re);
/* 2303 */       retval.tree = ((CommonTree)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), re));
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/* 2308 */     return retval;
/*      */   }
/*      */ 
/*      */   public final throwsSpec_return throwsSpec()
/*      */     throws RecognitionException
/*      */   {
/* 2320 */     throwsSpec_return retval = new throwsSpec_return();
/* 2321 */     retval.start = this.input.LT(1);
/*      */ 
/* 2323 */     CommonTree root_0 = null;
/*      */ 
/* 2325 */     Token string_literal58 = null;
/* 2326 */     Token char_literal60 = null;
/* 2327 */     id_return id59 = null;
/*      */ 
/* 2329 */     id_return id61 = null;
/*      */ 
/* 2332 */     CommonTree string_literal58_tree = null;
/* 2333 */     CommonTree char_literal60_tree = null;
/* 2334 */     RewriteRuleTokenStream stream_79 = new RewriteRuleTokenStream(this.adaptor, "token 79");
/* 2335 */     RewriteRuleTokenStream stream_80 = new RewriteRuleTokenStream(this.adaptor, "token 80");
/* 2336 */     RewriteRuleSubtreeStream stream_id = new RewriteRuleSubtreeStream(this.adaptor, "rule id");
/*      */     try
/*      */     {
/* 2341 */       string_literal58 = (Token)match(this.input, 79, FOLLOW_79_in_throwsSpec1083);
/*      */       throwsSpec_return localthrowsSpec_return1;
/* 2341 */       if (this.state.failed) { localthrowsSpec_return1 = retval; return localthrowsSpec_return1; }
/* 2342 */       if (this.state.backtracking == 0) stream_79.add(string_literal58);
/*      */ 
/* 2344 */       pushFollow(FOLLOW_id_in_throwsSpec1085);
/* 2345 */       id59 = id();
/*      */ 
/* 2347 */       this.state._fsp -= 1;
/* 2348 */       if (this.state.failed) { localthrowsSpec_return1 = retval; return localthrowsSpec_return1; }
/* 2349 */       if (this.state.backtracking == 0) stream_id.add(id59.getTree());
/*      */ 
/*      */       while (true)
/*      */       {
/* 2353 */         int alt26 = 2;
/* 2354 */         switch (this.input.LA(1))
/*      */         {
/*      */         case 80:
/* 2357 */           alt26 = 1;
/*      */         }
/*      */ 
/* 2363 */         switch (alt26)
/*      */         {
/*      */         case 1:
/* 2367 */           char_literal60 = (Token)match(this.input, 80, FOLLOW_80_in_throwsSpec1089);
/*      */           throwsSpec_return localthrowsSpec_return2;
/* 2367 */           if (this.state.failed) { localthrowsSpec_return2 = retval; return localthrowsSpec_return2; }
/* 2368 */           if (this.state.backtracking == 0) stream_80.add(char_literal60);
/*      */ 
/* 2370 */           pushFollow(FOLLOW_id_in_throwsSpec1091);
/* 2371 */           id61 = id();
/*      */ 
/* 2373 */           this.state._fsp -= 1;
/* 2374 */           if (this.state.failed) { localthrowsSpec_return2 = retval; return localthrowsSpec_return2; }
/* 2375 */           if (this.state.backtracking == 0) stream_id.add(id61.getTree()); break;
/*      */         default:
/* 2381 */           break label378;
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/* 2394 */       label378: if (this.state.backtracking == 0) {
/* 2395 */         retval.tree = root_0;
/* 2396 */         RewriteRuleSubtreeStream stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.tree : null);
/*      */ 
/* 2398 */         root_0 = (CommonTree)this.adaptor.nil();
/*      */ 
/* 2403 */         CommonTree root_1 = (CommonTree)this.adaptor.nil();
/* 2404 */         root_1 = (CommonTree)this.adaptor.becomeRoot(stream_79.nextNode(), root_1);
/*      */ 
/* 2406 */         if (!stream_id.hasNext()) {
/* 2407 */           throw new RewriteEarlyExitException();
/*      */         }
/* 2409 */         while (stream_id.hasNext()) {
/* 2410 */           this.adaptor.addChild(root_1, stream_id.nextTree());
/*      */         }
/*      */ 
/* 2413 */         stream_id.reset();
/*      */ 
/* 2415 */         this.adaptor.addChild(root_0, root_1);
/*      */ 
/* 2420 */         retval.tree = root_0;
/*      */       }
/*      */ 
/* 2423 */       retval.stop = this.input.LT(-1);
/*      */ 
/* 2425 */       if (this.state.backtracking == 0)
/*      */       {
/* 2427 */         retval.tree = ((CommonTree)this.adaptor.rulePostProcessing(root_0));
/* 2428 */         this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
/*      */       }
/*      */     } catch (RecognitionException re) {
/* 2431 */       re = 
/* 2438 */         re;
/*      */ 
/* 2432 */       reportError(re);
/* 2433 */       recover(this.input, re);
/* 2434 */       retval.tree = ((CommonTree)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), re));
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/* 2439 */     return retval;
/*      */   }
/*      */ 
/*      */   public final ruleScopeSpec_return ruleScopeSpec()
/*      */     throws RecognitionException
/*      */   {
/* 2451 */     ruleScopeSpec_return retval = new ruleScopeSpec_return();
/* 2452 */     retval.start = this.input.LT(1);
/*      */ 
/* 2454 */     CommonTree root_0 = null;
/*      */ 
/* 2456 */     Token string_literal62 = null;
/* 2457 */     Token ACTION63 = null;
/* 2458 */     Token string_literal64 = null;
/* 2459 */     Token char_literal66 = null;
/* 2460 */     Token char_literal68 = null;
/* 2461 */     Token string_literal69 = null;
/* 2462 */     Token ACTION70 = null;
/* 2463 */     Token string_literal71 = null;
/* 2464 */     Token char_literal73 = null;
/* 2465 */     Token char_literal75 = null;
/* 2466 */     id_return id65 = null;
/*      */ 
/* 2468 */     id_return id67 = null;
/*      */ 
/* 2470 */     id_return id72 = null;
/*      */ 
/* 2472 */     id_return id74 = null;
/*      */ 
/* 2475 */     CommonTree string_literal62_tree = null;
/* 2476 */     CommonTree ACTION63_tree = null;
/* 2477 */     CommonTree string_literal64_tree = null;
/* 2478 */     CommonTree char_literal66_tree = null;
/* 2479 */     CommonTree char_literal68_tree = null;
/* 2480 */     CommonTree string_literal69_tree = null;
/* 2481 */     CommonTree ACTION70_tree = null;
/* 2482 */     CommonTree string_literal71_tree = null;
/* 2483 */     CommonTree char_literal73_tree = null;
/* 2484 */     CommonTree char_literal75_tree = null;
/* 2485 */     RewriteRuleTokenStream stream_SCOPE = new RewriteRuleTokenStream(this.adaptor, "token SCOPE");
/* 2486 */     RewriteRuleTokenStream stream_80 = new RewriteRuleTokenStream(this.adaptor, "token 80");
/* 2487 */     RewriteRuleTokenStream stream_71 = new RewriteRuleTokenStream(this.adaptor, "token 71");
/* 2488 */     RewriteRuleTokenStream stream_ACTION = new RewriteRuleTokenStream(this.adaptor, "token ACTION");
/* 2489 */     RewriteRuleSubtreeStream stream_id = new RewriteRuleSubtreeStream(this.adaptor, "rule id");
/*      */     try
/*      */     {
/* 2492 */       int alt29 = 3;
/*      */       Object nvae;
/*      */       Object nvae;
/* 2493 */       switch (this.input.LA(1))
/*      */       {
/*      */       case 30:
/*      */         Object nvae;
/* 2496 */         switch (this.input.LA(2))
/*      */         {
/*      */         case 47:
/* 2499 */           switch (this.input.LA(3))
/*      */           {
/*      */           case 30:
/* 2502 */             alt29 = 3;
/*      */ 
/* 2504 */             break;
/*      */           case 40:
/*      */           case 78:
/* 2508 */             alt29 = 1;
/*      */ 
/* 2510 */             break;
/*      */           default:
/* 2512 */             if (this.state.backtracking > 0) { this.state.failed = true; ruleScopeSpec_return localruleScopeSpec_return1 = retval; return localruleScopeSpec_return1; }
/* 2513 */             nvae = new NoViableAltException("", 29, 2, this.input);
/*      */ 
/* 2516 */             throw ((Throwable)nvae);
/*      */           }
/*      */ 
/* 2520 */           break;
/*      */         case 44:
/*      */         case 51:
/* 2524 */           alt29 = 2;
/*      */ 
/* 2526 */           break;
/*      */         default:
/* 2528 */           if (this.state.backtracking > 0) { this.state.failed = true; nvae = retval; return nvae; }
/* 2529 */           nvae = new NoViableAltException("", 29, 1, this.input);
/*      */ 
/* 2532 */           throw ((Throwable)nvae);
/*      */         }
/*      */ 
/* 2536 */         break;
/*      */       default:
/* 2538 */         if (this.state.backtracking > 0) { this.state.failed = true; nvae = retval; return nvae; }
/* 2539 */         nvae = new NoViableAltException("", 29, 0, this.input);
/*      */ 
/* 2542 */         throw ((Throwable)nvae);
/*      */       }
/*      */       Object stream_retval;
/*      */       CommonTree root_1;
/*      */       label998: Object stream_retval;
/*      */       CommonTree root_1;
/* 2545 */       switch (alt29)
/*      */       {
/*      */       case 1:
/* 2549 */         string_literal62 = (Token)match(this.input, 30, FOLLOW_SCOPE_in_ruleScopeSpec1114); if (this.state.failed) { nvae = retval; return nvae; }
/* 2550 */         if (this.state.backtracking == 0) stream_SCOPE.add(string_literal62);
/*      */ 
/* 2552 */         ACTION63 = (Token)match(this.input, 47, FOLLOW_ACTION_in_ruleScopeSpec1116); if (this.state.failed) { nvae = retval; return nvae; }
/* 2553 */         if (this.state.backtracking == 0) stream_ACTION.add(ACTION63);
/*      */ 
/* 2564 */         if (this.state.backtracking == 0) {
/* 2565 */           retval.tree = root_0;
/* 2566 */           stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.tree : null);
/*      */ 
/* 2568 */           root_0 = (CommonTree)this.adaptor.nil();
/*      */ 
/* 2573 */           root_1 = (CommonTree)this.adaptor.nil();
/* 2574 */           root_1 = (CommonTree)this.adaptor.becomeRoot(stream_SCOPE.nextNode(), root_1);
/*      */ 
/* 2576 */           this.adaptor.addChild(root_1, stream_ACTION.nextNode());
/*      */ 
/* 2578 */           this.adaptor.addChild(root_0, root_1);
/*      */ 
/* 2583 */           retval.tree = root_0; } break;
/*      */       case 2:
/* 2589 */         string_literal64 = (Token)match(this.input, 30, FOLLOW_SCOPE_in_ruleScopeSpec1129); if (this.state.failed) { stream_retval = retval; return stream_retval; }
/* 2590 */         if (this.state.backtracking == 0) stream_SCOPE.add(string_literal64);
/*      */ 
/* 2592 */         pushFollow(FOLLOW_id_in_ruleScopeSpec1131);
/* 2593 */         id65 = id();
/*      */ 
/* 2595 */         this.state._fsp -= 1;
/* 2596 */         if (this.state.failed) { stream_retval = retval; return stream_retval; }
/* 2597 */         if (this.state.backtracking == 0) stream_id.add(id65.getTree());
/*      */         int alt27;
/*      */         while (true)
/*      */         {
/* 2601 */           alt27 = 2;
/* 2602 */           switch (this.input.LA(1))
/*      */           {
/*      */           case 80:
/* 2605 */             alt27 = 1;
/*      */           }
/*      */ 
/* 2611 */           switch (alt27)
/*      */           {
/*      */           case 1:
/* 2615 */             char_literal66 = (Token)match(this.input, 80, FOLLOW_80_in_ruleScopeSpec1134); if (this.state.failed) { root_1 = retval; return root_1; }
/* 2616 */             if (this.state.backtracking == 0) stream_80.add(char_literal66);
/*      */ 
/* 2618 */             pushFollow(FOLLOW_id_in_ruleScopeSpec1136);
/* 2619 */             id67 = id();
/*      */ 
/* 2621 */             this.state._fsp -= 1;
/* 2622 */             if (this.state.failed) { root_1 = retval; return root_1; }
/* 2623 */             if (this.state.backtracking == 0) stream_id.add(id67.getTree()); break;
/*      */           default:
/* 2629 */             break label998;
/*      */           }
/*      */         }
/*      */ 
/* 2633 */         char_literal68 = (Token)match(this.input, 71, FOLLOW_71_in_ruleScopeSpec1140); if (this.state.failed) { ruleScopeSpec_return localruleScopeSpec_return2 = retval; return localruleScopeSpec_return2; }
/* 2634 */         if (this.state.backtracking == 0) stream_71.add(char_literal68);
/*      */ 
/* 2645 */         if (this.state.backtracking == 0) {
/* 2646 */           retval.tree = root_0;
/* 2647 */           stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.tree : null);
/*      */ 
/* 2649 */           root_0 = (CommonTree)this.adaptor.nil();
/*      */ 
/* 2654 */           root_1 = (CommonTree)this.adaptor.nil();
/* 2655 */           root_1 = (CommonTree)this.adaptor.becomeRoot(stream_SCOPE.nextNode(), root_1);
/*      */ 
/* 2657 */           if (!stream_id.hasNext()) {
/* 2658 */             throw new RewriteEarlyExitException();
/*      */           }
/* 2660 */           while (stream_id.hasNext()) {
/* 2661 */             this.adaptor.addChild(root_1, stream_id.nextTree());
/*      */           }
/*      */ 
/* 2664 */           stream_id.reset();
/*      */ 
/* 2666 */           this.adaptor.addChild(root_0, root_1);
/*      */ 
/* 2671 */           retval.tree = root_0; } break;
/*      */       case 3:
/* 2677 */         string_literal69 = (Token)match(this.input, 30, FOLLOW_SCOPE_in_ruleScopeSpec1154); if (this.state.failed) { stream_retval = retval; return stream_retval; }
/* 2678 */         if (this.state.backtracking == 0) stream_SCOPE.add(string_literal69);
/*      */ 
/* 2680 */         ACTION70 = (Token)match(this.input, 47, FOLLOW_ACTION_in_ruleScopeSpec1156); if (this.state.failed) { stream_retval = retval; return stream_retval; }
/* 2681 */         if (this.state.backtracking == 0) stream_ACTION.add(ACTION70);
/*      */ 
/* 2683 */         string_literal71 = (Token)match(this.input, 30, FOLLOW_SCOPE_in_ruleScopeSpec1160); if (this.state.failed) { stream_retval = retval; return stream_retval; }
/* 2684 */         if (this.state.backtracking == 0) stream_SCOPE.add(string_literal71);
/*      */ 
/* 2686 */         pushFollow(FOLLOW_id_in_ruleScopeSpec1162);
/* 2687 */         id72 = id();
/*      */ 
/* 2689 */         this.state._fsp -= 1;
/* 2690 */         if (this.state.failed) { stream_retval = retval; return stream_retval; }
/* 2691 */         if (this.state.backtracking == 0) stream_id.add(id72.getTree());
/*      */         int alt28;
/*      */         while (true)
/*      */         {
/* 2695 */           alt28 = 2;
/* 2696 */           switch (this.input.LA(1))
/*      */           {
/*      */           case 80:
/* 2699 */             alt28 = 1;
/*      */           }
/*      */ 
/* 2705 */           switch (alt28)
/*      */           {
/*      */           case 1:
/* 2709 */             char_literal73 = (Token)match(this.input, 80, FOLLOW_80_in_ruleScopeSpec1165); if (this.state.failed) { root_1 = retval; return root_1; }
/* 2710 */             if (this.state.backtracking == 0) stream_80.add(char_literal73);
/*      */ 
/* 2712 */             pushFollow(FOLLOW_id_in_ruleScopeSpec1167);
/* 2713 */             id74 = id();
/*      */ 
/* 2715 */             this.state._fsp -= 1;
/* 2716 */             if (this.state.failed) { root_1 = retval; return root_1; }
/* 2717 */             if (this.state.backtracking == 0) stream_id.add(id74.getTree()); break;
/*      */           default:
/* 2723 */             break label1598;
/*      */           }
/*      */         }
/*      */ 
/* 2727 */         label1598: char_literal75 = (Token)match(this.input, 71, FOLLOW_71_in_ruleScopeSpec1171); if (this.state.failed) { ruleScopeSpec_return localruleScopeSpec_return3 = retval; return localruleScopeSpec_return3; }
/* 2728 */         if (this.state.backtracking == 0) stream_71.add(char_literal75);
/*      */ 
/* 2739 */         if (this.state.backtracking == 0) {
/* 2740 */           retval.tree = root_0;
/* 2741 */           RewriteRuleSubtreeStream stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.tree : null);
/*      */ 
/* 2743 */           root_0 = (CommonTree)this.adaptor.nil();
/*      */ 
/* 2748 */           CommonTree root_1 = (CommonTree)this.adaptor.nil();
/* 2749 */           root_1 = (CommonTree)this.adaptor.becomeRoot(stream_SCOPE.nextNode(), root_1);
/*      */ 
/* 2751 */           this.adaptor.addChild(root_1, stream_ACTION.nextNode());
/* 2752 */           if (!stream_id.hasNext()) {
/* 2753 */             throw new RewriteEarlyExitException();
/*      */           }
/* 2755 */           while (stream_id.hasNext()) {
/* 2756 */             this.adaptor.addChild(root_1, stream_id.nextTree());
/*      */           }
/*      */ 
/* 2759 */           stream_id.reset();
/*      */ 
/* 2761 */           this.adaptor.addChild(root_0, root_1);
/*      */ 
/* 2766 */           retval.tree = root_0;
/*      */         }
/*      */         break;
/*      */       }
/*      */ 
/* 2771 */       retval.stop = this.input.LT(-1);
/*      */ 
/* 2773 */       if (this.state.backtracking == 0)
/*      */       {
/* 2775 */         retval.tree = ((CommonTree)this.adaptor.rulePostProcessing(root_0));
/* 2776 */         this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
/*      */       }
/*      */     } catch (RecognitionException re) {
/* 2779 */       re = 
/* 2786 */         re;
/*      */ 
/* 2780 */       reportError(re);
/* 2781 */       recover(this.input, re);
/* 2782 */       retval.tree = ((CommonTree)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), re));
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/* 2787 */     return retval;
/*      */   }
/*      */ 
/*      */   public final block_return block()
/*      */     throws RecognitionException
/*      */   {
/* 2799 */     block_return retval = new block_return();
/* 2800 */     retval.start = this.input.LT(1);
/*      */ 
/* 2802 */     CommonTree root_0 = null;
/*      */ 
/* 2804 */     Token lp = null;
/* 2805 */     Token rp = null;
/* 2806 */     Token char_literal76 = null;
/* 2807 */     Token char_literal78 = null;
/* 2808 */     optionsSpec_return opts = null;
/*      */ 
/* 2810 */     altpair_return altpair77 = null;
/*      */ 
/* 2812 */     altpair_return altpair79 = null;
/*      */ 
/* 2815 */     CommonTree lp_tree = null;
/* 2816 */     CommonTree rp_tree = null;
/* 2817 */     CommonTree char_literal76_tree = null;
/* 2818 */     CommonTree char_literal78_tree = null;
/* 2819 */     RewriteRuleTokenStream stream_78 = new RewriteRuleTokenStream(this.adaptor, "token 78");
/* 2820 */     RewriteRuleTokenStream stream_82 = new RewriteRuleTokenStream(this.adaptor, "token 82");
/* 2821 */     RewriteRuleTokenStream stream_83 = new RewriteRuleTokenStream(this.adaptor, "token 83");
/* 2822 */     RewriteRuleTokenStream stream_81 = new RewriteRuleTokenStream(this.adaptor, "token 81");
/* 2823 */     RewriteRuleSubtreeStream stream_altpair = new RewriteRuleSubtreeStream(this.adaptor, "rule altpair");
/* 2824 */     RewriteRuleSubtreeStream stream_optionsSpec = new RewriteRuleSubtreeStream(this.adaptor, "rule optionsSpec");
/*      */     try
/*      */     {
/* 2829 */       lp = (Token)match(this.input, 81, FOLLOW_81_in_block1203); if (this.state.failed) { block_return localblock_return1 = retval; return localblock_return1; }
/* 2830 */       if (this.state.backtracking == 0) stream_81.add(lp);
/*      */ 
/* 2833 */       int alt31 = 2;
/* 2834 */       switch (this.input.LA(1))
/*      */       {
/*      */       case 48:
/*      */       case 78:
/* 2838 */         alt31 = 1;
/*      */       }
/*      */       int alt30;
/*      */       block_return localblock_return2;
/* 2843 */       switch (alt31)
/*      */       {
/*      */       case 1:
/* 2848 */         alt30 = 2;
/* 2849 */         switch (this.input.LA(1))
/*      */         {
/*      */         case 48:
/* 2852 */           alt30 = 1;
/*      */         }
/*      */ 
/* 2857 */         switch (alt30)
/*      */         {
/*      */         case 1:
/* 2861 */           pushFollow(FOLLOW_optionsSpec_in_block1212);
/* 2862 */           opts = optionsSpec();
/*      */ 
/* 2864 */           this.state._fsp -= 1;
/* 2865 */           if (this.state.failed) { localblock_return2 = retval; return localblock_return2; }
/* 2866 */           if (this.state.backtracking == 0) stream_optionsSpec.add(opts.getTree());
/*      */ 
/*      */           break;
/*      */         }
/*      */ 
/* 2873 */         char_literal76 = (Token)match(this.input, 78, FOLLOW_78_in_block1216); if (this.state.failed) { localblock_return2 = retval; return localblock_return2; }
/* 2874 */         if (this.state.backtracking == 0) stream_78.add(char_literal76);
/*      */ 
/*      */         break;
/*      */       }
/*      */ 
/* 2882 */       pushFollow(FOLLOW_altpair_in_block1223);
/* 2883 */       altpair77 = altpair();
/*      */ 
/* 2885 */       this.state._fsp -= 1;
/* 2886 */       if (this.state.failed) { alt30 = retval; return alt30; }
/* 2887 */       if (this.state.backtracking == 0) stream_altpair.add(altpair77.getTree());
/*      */       int alt32;
/*      */       while (true)
/*      */       {
/* 2891 */         alt32 = 2;
/* 2892 */         switch (this.input.LA(1))
/*      */         {
/*      */         case 82:
/* 2895 */           alt32 = 1;
/*      */         }
/*      */ 
/* 2901 */         switch (alt32)
/*      */         {
/*      */         case 1:
/* 2905 */           char_literal78 = (Token)match(this.input, 82, FOLLOW_82_in_block1227); if (this.state.failed) { localblock_return2 = retval; return localblock_return2; }
/* 2906 */           if (this.state.backtracking == 0) stream_82.add(char_literal78);
/*      */ 
/* 2908 */           pushFollow(FOLLOW_altpair_in_block1229);
/* 2909 */           altpair79 = altpair();
/*      */ 
/* 2911 */           this.state._fsp -= 1;
/* 2912 */           if (this.state.failed) { localblock_return2 = retval; return localblock_return2; }
/* 2913 */           if (this.state.backtracking == 0) stream_altpair.add(altpair79.getTree()); break;
/*      */         default:
/* 2919 */           break label674;
/*      */         }
/*      */       }
/*      */ 
/* 2923 */       label674: rp = (Token)match(this.input, 83, FOLLOW_83_in_block1244); if (this.state.failed) { alt32 = retval; return alt32; }
/* 2924 */       if (this.state.backtracking == 0) stream_83.add(rp);
/*      */ 
/* 2935 */       if (this.state.backtracking == 0) {
/* 2936 */         retval.tree = root_0;
/* 2937 */         RewriteRuleSubtreeStream stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.tree : null);
/*      */ 
/* 2939 */         root_0 = (CommonTree)this.adaptor.nil();
/*      */ 
/* 2944 */         CommonTree root_1 = (CommonTree)this.adaptor.nil();
/* 2945 */         root_1 = (CommonTree)this.adaptor.becomeRoot((CommonTree)this.adaptor.create(8, lp, "BLOCK"), root_1);
/*      */ 
/* 2948 */         if (stream_optionsSpec.hasNext()) {
/* 2949 */           this.adaptor.addChild(root_1, stream_optionsSpec.nextTree());
/*      */         }
/*      */ 
/* 2952 */         stream_optionsSpec.reset();
/* 2953 */         if (!stream_altpair.hasNext()) {
/* 2954 */           throw new RewriteEarlyExitException();
/*      */         }
/* 2956 */         while (stream_altpair.hasNext()) {
/* 2957 */           this.adaptor.addChild(root_1, stream_altpair.nextTree());
/*      */         }
/*      */ 
/* 2960 */         stream_altpair.reset();
/* 2961 */         this.adaptor.addChild(root_1, (CommonTree)this.adaptor.create(18, rp, "EOB"));
/*      */ 
/* 2963 */         this.adaptor.addChild(root_0, root_1);
/*      */ 
/* 2968 */         retval.tree = root_0;
/*      */       }
/*      */ 
/* 2971 */       retval.stop = this.input.LT(-1);
/*      */ 
/* 2973 */       if (this.state.backtracking == 0)
/*      */       {
/* 2975 */         retval.tree = ((CommonTree)this.adaptor.rulePostProcessing(root_0));
/* 2976 */         this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
/*      */       }
/*      */     } catch (RecognitionException re) {
/* 2979 */       re = 
/* 2986 */         re;
/*      */ 
/* 2980 */       reportError(re);
/* 2981 */       recover(this.input, re);
/* 2982 */       retval.tree = ((CommonTree)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), re));
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/* 2987 */     return retval;
/*      */   }
/*      */ 
/*      */   public final altpair_return altpair()
/*      */     throws RecognitionException
/*      */   {
/* 2999 */     altpair_return retval = new altpair_return();
/* 3000 */     retval.start = this.input.LT(1);
/*      */ 
/* 3002 */     CommonTree root_0 = null;
/*      */ 
/* 3004 */     alternative_return alternative80 = null;
/*      */ 
/* 3006 */     rewrite_return rewrite81 = null;
/*      */     try
/*      */     {
/* 3014 */       root_0 = (CommonTree)this.adaptor.nil();
/*      */ 
/* 3016 */       pushFollow(FOLLOW_alternative_in_altpair1283);
/* 3017 */       alternative80 = alternative();
/*      */ 
/* 3019 */       this.state._fsp -= 1;
/*      */       altpair_return localaltpair_return1;
/* 3020 */       if (this.state.failed) { localaltpair_return1 = retval; return localaltpair_return1; }
/* 3021 */       if (this.state.backtracking == 0) this.adaptor.addChild(root_0, alternative80.getTree());
/* 3022 */       pushFollow(FOLLOW_rewrite_in_altpair1285);
/* 3023 */       rewrite81 = rewrite();
/*      */ 
/* 3025 */       this.state._fsp -= 1;
/* 3026 */       if (this.state.failed) { localaltpair_return1 = retval; return localaltpair_return1; }
/* 3027 */       if (this.state.backtracking == 0) this.adaptor.addChild(root_0, rewrite81.getTree());
/*      */ 
/* 3031 */       retval.stop = this.input.LT(-1);
/*      */ 
/* 3033 */       if (this.state.backtracking == 0)
/*      */       {
/* 3035 */         retval.tree = ((CommonTree)this.adaptor.rulePostProcessing(root_0));
/* 3036 */         this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
/*      */       }
/*      */     } catch (RecognitionException re) {
/* 3039 */       re = 
/* 3046 */         re;
/*      */ 
/* 3040 */       reportError(re);
/* 3041 */       recover(this.input, re);
/* 3042 */       retval.tree = ((CommonTree)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), re));
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/* 3047 */     return retval;
/*      */   }
/*      */ 
/*      */   public final altList_return altList()
/*      */     throws RecognitionException
/*      */   {
/* 3059 */     altList_return retval = new altList_return();
/* 3060 */     retval.start = this.input.LT(1);
/*      */ 
/* 3062 */     CommonTree root_0 = null;
/*      */ 
/* 3064 */     Token char_literal83 = null;
/* 3065 */     altpair_return altpair82 = null;
/*      */ 
/* 3067 */     altpair_return altpair84 = null;
/*      */ 
/* 3070 */     CommonTree char_literal83_tree = null;
/* 3071 */     RewriteRuleTokenStream stream_82 = new RewriteRuleTokenStream(this.adaptor, "token 82");
/* 3072 */     RewriteRuleSubtreeStream stream_altpair = new RewriteRuleSubtreeStream(this.adaptor, "rule altpair");
/*      */ 
/* 3077 */     CommonTree blkRoot = (CommonTree)this.adaptor.create(8, this.input.LT(-1), "BLOCK");
/*      */     try
/*      */     {
/* 3083 */       pushFollow(FOLLOW_altpair_in_altList1305);
/* 3084 */       altpair82 = altpair();
/*      */ 
/* 3086 */       this.state._fsp -= 1;
/* 3087 */       if (this.state.failed) { altList_return localaltList_return1 = retval; return localaltList_return1; }
/* 3088 */       if (this.state.backtracking == 0) stream_altpair.add(altpair82.getTree());
/*      */ 
/*      */       while (true)
/*      */       {
/* 3092 */         int alt33 = 2;
/* 3093 */         switch (this.input.LA(1))
/*      */         {
/*      */         case 82:
/* 3096 */           alt33 = 1;
/*      */         }
/*      */ 
/* 3102 */         switch (alt33)
/*      */         {
/*      */         case 1:
/* 3106 */           char_literal83 = (Token)match(this.input, 82, FOLLOW_82_in_altList1309);
/*      */           altList_return localaltList_return2;
/* 3106 */           if (this.state.failed) { localaltList_return2 = retval; return localaltList_return2; }
/* 3107 */           if (this.state.backtracking == 0) stream_82.add(char_literal83);
/*      */ 
/* 3109 */           pushFollow(FOLLOW_altpair_in_altList1311);
/* 3110 */           altpair84 = altpair();
/*      */ 
/* 3112 */           this.state._fsp -= 1;
/* 3113 */           if (this.state.failed) { localaltList_return2 = retval; return localaltList_return2; }
/* 3114 */           if (this.state.backtracking == 0) stream_altpair.add(altpair84.getTree()); break;
/*      */         default:
/* 3120 */           break label332;
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/* 3133 */       label332: if (this.state.backtracking == 0) {
/* 3134 */         retval.tree = root_0;
/* 3135 */         RewriteRuleSubtreeStream stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.tree : null);
/*      */ 
/* 3137 */         root_0 = (CommonTree)this.adaptor.nil();
/*      */ 
/* 3142 */         CommonTree root_1 = (CommonTree)this.adaptor.nil();
/* 3143 */         root_1 = (CommonTree)this.adaptor.becomeRoot(blkRoot, root_1);
/*      */ 
/* 3145 */         if (!stream_altpair.hasNext()) {
/* 3146 */           throw new RewriteEarlyExitException();
/*      */         }
/* 3148 */         while (stream_altpair.hasNext()) {
/* 3149 */           this.adaptor.addChild(root_1, stream_altpair.nextTree());
/*      */         }
/*      */ 
/* 3152 */         stream_altpair.reset();
/* 3153 */         this.adaptor.addChild(root_1, (CommonTree)this.adaptor.create(18, "EOB"));
/*      */ 
/* 3155 */         this.adaptor.addChild(root_0, root_1);
/*      */ 
/* 3160 */         retval.tree = root_0;
/*      */       }
/*      */ 
/* 3163 */       retval.stop = this.input.LT(-1);
/*      */ 
/* 3165 */       if (this.state.backtracking == 0)
/*      */       {
/* 3167 */         retval.tree = ((CommonTree)this.adaptor.rulePostProcessing(root_0));
/* 3168 */         this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
/*      */       }
/*      */     } catch (RecognitionException re) {
/* 3171 */       re = 
/* 3178 */         re;
/*      */ 
/* 3172 */       reportError(re);
/* 3173 */       recover(this.input, re);
/* 3174 */       retval.tree = ((CommonTree)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), re));
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/* 3179 */     return retval;
/*      */   }
/*      */ 
/*      */   public final alternative_return alternative()
/*      */     throws RecognitionException
/*      */   {
/* 3191 */     alternative_return retval = new alternative_return();
/* 3192 */     retval.start = this.input.LT(1);
/*      */ 
/* 3194 */     CommonTree root_0 = null;
/*      */ 
/* 3196 */     element_return element85 = null;
/*      */ 
/* 3199 */     RewriteRuleSubtreeStream stream_element = new RewriteRuleSubtreeStream(this.adaptor, "rule element");
/*      */ 
/* 3201 */     Token firstToken = this.input.LT(1);
/* 3202 */     Token prevToken = this.input.LT(-1);
/*      */     try
/*      */     {
/* 3206 */       int alt35 = 2;
/* 3207 */       switch (this.input.LA(1))
/*      */       {
/*      */       case 31:
/*      */       case 36:
/*      */       case 44:
/*      */       case 45:
/*      */       case 46:
/*      */       case 47:
/*      */       case 51:
/*      */       case 81:
/*      */       case 87:
/*      */       case 90:
/* 3219 */         alt35 = 1;
/*      */ 
/* 3221 */         break;
/*      */       case 39:
/*      */       case 71:
/*      */       case 82:
/*      */       case 83:
/* 3227 */         alt35 = 2;
/*      */ 
/* 3229 */         break;
/*      */       case 32:
/*      */       case 33:
/*      */       case 34:
/*      */       case 35:
/*      */       case 37:
/*      */       case 38:
/*      */       case 40:
/*      */       case 41:
/*      */       case 42:
/*      */       case 43:
/*      */       case 48:
/*      */       case 49:
/*      */       case 50:
/*      */       case 52:
/*      */       case 53:
/*      */       case 54:
/*      */       case 55:
/*      */       case 56:
/*      */       case 57:
/*      */       case 58:
/*      */       case 59:
/*      */       case 60:
/*      */       case 61:
/*      */       case 62:
/*      */       case 63:
/*      */       case 64:
/*      */       case 65:
/*      */       case 66:
/*      */       case 67:
/*      */       case 68:
/*      */       case 69:
/*      */       case 70:
/*      */       case 72:
/*      */       case 73:
/*      */       case 74:
/*      */       case 75:
/*      */       case 76:
/*      */       case 77:
/*      */       case 78:
/*      */       case 79:
/*      */       case 80:
/*      */       case 84:
/*      */       case 85:
/*      */       case 86:
/*      */       case 88:
/*      */       case 89:
/*      */       default:
/* 3231 */         if (this.state.backtracking > 0) { this.state.failed = true; alternative_return localalternative_return1 = retval; return localalternative_return1; }
/* 3232 */         NoViableAltException nvae = new NoViableAltException("", 35, 0, this.input);
/*      */ 
/* 3235 */         throw nvae;
/*      */       }
/*      */ 
/* 3238 */       switch (alt35)
/*      */       {
/*      */       case 1:
/* 3243 */         int cnt34 = 0;
/*      */         while (true)
/*      */         {
/* 3246 */           int alt34 = 2;
/* 3247 */           switch (this.input.LA(1))
/*      */           {
/*      */           case 31:
/*      */           case 36:
/*      */           case 44:
/*      */           case 45:
/*      */           case 46:
/*      */           case 47:
/*      */           case 51:
/*      */           case 81:
/*      */           case 87:
/*      */           case 90:
/* 3259 */             alt34 = 1;
/*      */           }
/*      */           alternative_return localalternative_return2;
/* 3265 */           switch (alt34)
/*      */           {
/*      */           case 1:
/* 3269 */             pushFollow(FOLLOW_element_in_alternative1352);
/* 3270 */             element85 = element();
/*      */ 
/* 3272 */             this.state._fsp -= 1;
/* 3273 */             if (this.state.failed) { localalternative_return2 = retval; return localalternative_return2; }
/* 3274 */             if (this.state.backtracking == 0) stream_element.add(element85.getTree()); break;
/*      */           default:
/* 3280 */             if (cnt34 >= 1) break label668;
/* 3281 */             if (this.state.backtracking > 0) { this.state.failed = true; localalternative_return2 = retval; return localalternative_return2; }
/* 3282 */             EarlyExitException eee = new EarlyExitException(34, this.input);
/*      */ 
/* 3284 */             throw eee;
/*      */           }
/* 3286 */           cnt34++;
/*      */         }
/*      */ 
/* 3298 */         if (this.state.backtracking == 0) {
/* 3299 */           retval.tree = root_0;
/* 3300 */           RewriteRuleSubtreeStream stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.tree : null);
/*      */ 
/* 3302 */           root_0 = (CommonTree)this.adaptor.nil();
/*      */ 
/* 3307 */           CommonTree root_1 = (CommonTree)this.adaptor.nil();
/* 3308 */           root_1 = (CommonTree)this.adaptor.becomeRoot((CommonTree)this.adaptor.create(16, firstToken, "ALT"), root_1);
/*      */ 
/* 3310 */           if (!stream_element.hasNext()) {
/* 3311 */             throw new RewriteEarlyExitException();
/*      */           }
/* 3313 */           while (stream_element.hasNext()) {
/* 3314 */             this.adaptor.addChild(root_1, stream_element.nextTree());
/*      */           }
/*      */ 
/* 3317 */           stream_element.reset();
/* 3318 */           this.adaptor.addChild(root_1, (CommonTree)this.adaptor.create(19, "EOA"));
/*      */ 
/* 3320 */           this.adaptor.addChild(root_0, root_1);
/*      */ 
/* 3325 */           retval.tree = root_0;
/*      */         }
/* 3327 */         break;
/*      */       case 2:
/* 3339 */         label668: if (this.state.backtracking == 0) {
/* 3340 */           retval.tree = root_0;
/* 3341 */           RewriteRuleSubtreeStream stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.tree : null);
/*      */ 
/* 3343 */           root_0 = (CommonTree)this.adaptor.nil();
/*      */ 
/* 3348 */           CommonTree root_1 = (CommonTree)this.adaptor.nil();
/* 3349 */           root_1 = (CommonTree)this.adaptor.becomeRoot((CommonTree)this.adaptor.create(16, prevToken, "ALT"), root_1);
/*      */ 
/* 3351 */           this.adaptor.addChild(root_1, (CommonTree)this.adaptor.create(15, prevToken, "EPSILON"));
/* 3352 */           this.adaptor.addChild(root_1, (CommonTree)this.adaptor.create(19, "EOA"));
/*      */ 
/* 3354 */           this.adaptor.addChild(root_0, root_1);
/*      */ 
/* 3359 */           retval.tree = root_0;
/*      */         }
/*      */         break;
/*      */       }
/*      */ 
/* 3364 */       retval.stop = this.input.LT(-1);
/*      */ 
/* 3366 */       if (this.state.backtracking == 0)
/*      */       {
/* 3368 */         retval.tree = ((CommonTree)this.adaptor.rulePostProcessing(root_0));
/* 3369 */         this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
/*      */       }
/*      */     } catch (RecognitionException re) {
/* 3372 */       re = 
/* 3379 */         re;
/*      */ 
/* 3373 */       reportError(re);
/* 3374 */       recover(this.input, re);
/* 3375 */       retval.tree = ((CommonTree)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), re));
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/* 3380 */     return retval;
/*      */   }
/*      */ 
/*      */   public final exceptionGroup_return exceptionGroup()
/*      */     throws RecognitionException
/*      */   {
/* 3392 */     exceptionGroup_return retval = new exceptionGroup_return();
/* 3393 */     retval.start = this.input.LT(1);
/*      */ 
/* 3395 */     CommonTree root_0 = null;
/*      */ 
/* 3397 */     exceptionHandler_return exceptionHandler86 = null;
/*      */ 
/* 3399 */     finallyClause_return finallyClause87 = null;
/*      */ 
/* 3401 */     finallyClause_return finallyClause88 = null;
/*      */     try
/*      */     {
/* 3407 */       int alt38 = 2;
/* 3408 */       switch (this.input.LA(1))
/*      */       {
/*      */       case 84:
/* 3411 */         alt38 = 1;
/*      */ 
/* 3413 */         break;
/*      */       case 85:
/* 3416 */         alt38 = 2;
/*      */ 
/* 3418 */         break;
/*      */       default:
/* 3420 */         if (this.state.backtracking > 0) { this.state.failed = true; exceptionGroup_return localexceptionGroup_return1 = retval; return localexceptionGroup_return1; }
/* 3421 */         NoViableAltException nvae = new NoViableAltException("", 38, 0, this.input);
/*      */ 
/* 3424 */         throw nvae;
/*      */       }
/*      */       int cnt36;
/* 3427 */       switch (alt38)
/*      */       {
/*      */       case 1:
/* 3431 */         root_0 = (CommonTree)this.adaptor.nil();
/*      */ 
/* 3434 */         cnt36 = 0;
/*      */         Object eee;
/*      */         while (true) {
/* 3437 */           int alt36 = 2;
/* 3438 */           switch (this.input.LA(1))
/*      */           {
/*      */           case 84:
/* 3441 */             alt36 = 1;
/*      */           }
/*      */           exceptionGroup_return localexceptionGroup_return3;
/* 3447 */           switch (alt36)
/*      */           {
/*      */           case 1:
/* 3451 */             pushFollow(FOLLOW_exceptionHandler_in_exceptionGroup1403);
/* 3452 */             exceptionHandler86 = exceptionHandler();
/*      */ 
/* 3454 */             this.state._fsp -= 1;
/* 3455 */             if (this.state.failed) { localexceptionGroup_return3 = retval; return localexceptionGroup_return3; }
/* 3456 */             if (this.state.backtracking == 0) this.adaptor.addChild(root_0, exceptionHandler86.getTree()); break;
/*      */           default:
/* 3462 */             if (cnt36 >= 1) break label353;
/* 3463 */             if (this.state.backtracking > 0) { this.state.failed = true; localexceptionGroup_return3 = retval; return localexceptionGroup_return3; }
/* 3464 */             eee = new EarlyExitException(36, this.input);
/*      */ 
/* 3466 */             throw ((Throwable)eee);
/*      */           }
/* 3468 */           cnt36++;
/*      */         }
/*      */ 
/* 3472 */         int alt37 = 2;
/* 3473 */         switch (this.input.LA(1))
/*      */         {
/*      */         case 85:
/* 3476 */           alt37 = 1;
/*      */         }
/*      */ 
/* 3481 */         switch (alt37)
/*      */         {
/*      */         case 1:
/* 3485 */           pushFollow(FOLLOW_finallyClause_in_exceptionGroup1410);
/* 3486 */           finallyClause87 = finallyClause();
/*      */ 
/* 3488 */           this.state._fsp -= 1;
/* 3489 */           if (this.state.failed) { eee = retval; return eee; }
/* 3490 */           if (this.state.backtracking == 0) this.adaptor.addChild(root_0, finallyClause87.getTree());
/*      */ 
/*      */           break;
/*      */         }
/*      */ 
/* 3499 */         break;
/*      */       case 2:
/* 3503 */         label353: root_0 = (CommonTree)this.adaptor.nil();
/*      */ 
/* 3505 */         pushFollow(FOLLOW_finallyClause_in_exceptionGroup1418);
/* 3506 */         finallyClause88 = finallyClause();
/*      */ 
/* 3508 */         this.state._fsp -= 1;
/* 3509 */         if (this.state.failed) { exceptionGroup_return localexceptionGroup_return2 = retval; return localexceptionGroup_return2; }
/* 3510 */         if (this.state.backtracking == 0) this.adaptor.addChild(root_0, finallyClause88.getTree());
/*      */ 
/*      */         break;
/*      */       }
/*      */ 
/* 3516 */       retval.stop = this.input.LT(-1);
/*      */ 
/* 3518 */       if (this.state.backtracking == 0)
/*      */       {
/* 3520 */         retval.tree = ((CommonTree)this.adaptor.rulePostProcessing(root_0));
/* 3521 */         this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
/*      */       }
/*      */     } catch (RecognitionException re) {
/* 3524 */       re = 
/* 3531 */         re;
/*      */ 
/* 3525 */       reportError(re);
/* 3526 */       recover(this.input, re);
/* 3527 */       retval.tree = ((CommonTree)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), re));
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/* 3532 */     return retval;
/*      */   }
/*      */ 
/*      */   public final exceptionHandler_return exceptionHandler()
/*      */     throws RecognitionException
/*      */   {
/* 3544 */     exceptionHandler_return retval = new exceptionHandler_return();
/* 3545 */     retval.start = this.input.LT(1);
/*      */ 
/* 3547 */     CommonTree root_0 = null;
/*      */ 
/* 3549 */     Token string_literal89 = null;
/* 3550 */     Token ARG_ACTION90 = null;
/* 3551 */     Token ACTION91 = null;
/*      */ 
/* 3553 */     CommonTree string_literal89_tree = null;
/* 3554 */     CommonTree ARG_ACTION90_tree = null;
/* 3555 */     CommonTree ACTION91_tree = null;
/* 3556 */     RewriteRuleTokenStream stream_ACTION = new RewriteRuleTokenStream(this.adaptor, "token ACTION");
/* 3557 */     RewriteRuleTokenStream stream_84 = new RewriteRuleTokenStream(this.adaptor, "token 84");
/* 3558 */     RewriteRuleTokenStream stream_ARG_ACTION = new RewriteRuleTokenStream(this.adaptor, "token ARG_ACTION");
/*      */     try
/*      */     {
/* 3564 */       string_literal89 = (Token)match(this.input, 84, FOLLOW_84_in_exceptionHandler1438);
/*      */       exceptionHandler_return localexceptionHandler_return1;
/* 3564 */       if (this.state.failed) { localexceptionHandler_return1 = retval; return localexceptionHandler_return1; }
/* 3565 */       if (this.state.backtracking == 0) stream_84.add(string_literal89);
/*      */ 
/* 3567 */       ARG_ACTION90 = (Token)match(this.input, 50, FOLLOW_ARG_ACTION_in_exceptionHandler1440); if (this.state.failed) { localexceptionHandler_return1 = retval; return localexceptionHandler_return1; }
/* 3568 */       if (this.state.backtracking == 0) stream_ARG_ACTION.add(ARG_ACTION90);
/*      */ 
/* 3570 */       ACTION91 = (Token)match(this.input, 47, FOLLOW_ACTION_in_exceptionHandler1442); if (this.state.failed) { localexceptionHandler_return1 = retval; return localexceptionHandler_return1; }
/* 3571 */       if (this.state.backtracking == 0) stream_ACTION.add(ACTION91);
/*      */ 
/* 3582 */       if (this.state.backtracking == 0) {
/* 3583 */         retval.tree = root_0;
/* 3584 */         RewriteRuleSubtreeStream stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.tree : null);
/*      */ 
/* 3586 */         root_0 = (CommonTree)this.adaptor.nil();
/*      */ 
/* 3591 */         CommonTree root_1 = (CommonTree)this.adaptor.nil();
/* 3592 */         root_1 = (CommonTree)this.adaptor.becomeRoot(stream_84.nextNode(), root_1);
/*      */ 
/* 3594 */         this.adaptor.addChild(root_1, stream_ARG_ACTION.nextNode());
/* 3595 */         this.adaptor.addChild(root_1, stream_ACTION.nextNode());
/*      */ 
/* 3597 */         this.adaptor.addChild(root_0, root_1);
/*      */ 
/* 3602 */         retval.tree = root_0;
/*      */       }
/*      */ 
/* 3605 */       retval.stop = this.input.LT(-1);
/*      */ 
/* 3607 */       if (this.state.backtracking == 0)
/*      */       {
/* 3609 */         retval.tree = ((CommonTree)this.adaptor.rulePostProcessing(root_0));
/* 3610 */         this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
/*      */       }
/*      */     } catch (RecognitionException re) {
/* 3613 */       re = 
/* 3620 */         re;
/*      */ 
/* 3614 */       reportError(re);
/* 3615 */       recover(this.input, re);
/* 3616 */       retval.tree = ((CommonTree)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), re));
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/* 3621 */     return retval;
/*      */   }
/*      */ 
/*      */   public final finallyClause_return finallyClause()
/*      */     throws RecognitionException
/*      */   {
/* 3633 */     finallyClause_return retval = new finallyClause_return();
/* 3634 */     retval.start = this.input.LT(1);
/*      */ 
/* 3636 */     CommonTree root_0 = null;
/*      */ 
/* 3638 */     Token string_literal92 = null;
/* 3639 */     Token ACTION93 = null;
/*      */ 
/* 3641 */     CommonTree string_literal92_tree = null;
/* 3642 */     CommonTree ACTION93_tree = null;
/* 3643 */     RewriteRuleTokenStream stream_ACTION = new RewriteRuleTokenStream(this.adaptor, "token ACTION");
/* 3644 */     RewriteRuleTokenStream stream_85 = new RewriteRuleTokenStream(this.adaptor, "token 85");
/*      */     try
/*      */     {
/* 3650 */       string_literal92 = (Token)match(this.input, 85, FOLLOW_85_in_finallyClause1472);
/*      */       finallyClause_return localfinallyClause_return1;
/* 3650 */       if (this.state.failed) { localfinallyClause_return1 = retval; return localfinallyClause_return1; }
/* 3651 */       if (this.state.backtracking == 0) stream_85.add(string_literal92);
/*      */ 
/* 3653 */       ACTION93 = (Token)match(this.input, 47, FOLLOW_ACTION_in_finallyClause1474); if (this.state.failed) { localfinallyClause_return1 = retval; return localfinallyClause_return1; }
/* 3654 */       if (this.state.backtracking == 0) stream_ACTION.add(ACTION93);
/*      */ 
/* 3665 */       if (this.state.backtracking == 0) {
/* 3666 */         retval.tree = root_0;
/* 3667 */         RewriteRuleSubtreeStream stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.tree : null);
/*      */ 
/* 3669 */         root_0 = (CommonTree)this.adaptor.nil();
/*      */ 
/* 3674 */         CommonTree root_1 = (CommonTree)this.adaptor.nil();
/* 3675 */         root_1 = (CommonTree)this.adaptor.becomeRoot(stream_85.nextNode(), root_1);
/*      */ 
/* 3677 */         this.adaptor.addChild(root_1, stream_ACTION.nextNode());
/*      */ 
/* 3679 */         this.adaptor.addChild(root_0, root_1);
/*      */ 
/* 3684 */         retval.tree = root_0;
/*      */       }
/*      */ 
/* 3687 */       retval.stop = this.input.LT(-1);
/*      */ 
/* 3689 */       if (this.state.backtracking == 0)
/*      */       {
/* 3691 */         retval.tree = ((CommonTree)this.adaptor.rulePostProcessing(root_0));
/* 3692 */         this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
/*      */       }
/*      */     } catch (RecognitionException re) {
/* 3695 */       re = 
/* 3702 */         re;
/*      */ 
/* 3696 */       reportError(re);
/* 3697 */       recover(this.input, re);
/* 3698 */       retval.tree = ((CommonTree)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), re));
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/* 3703 */     return retval;
/*      */   }
/*      */ 
/*      */   public final element_return element()
/*      */     throws RecognitionException
/*      */   {
/* 3715 */     element_return retval = new element_return();
/* 3716 */     retval.start = this.input.LT(1);
/*      */ 
/* 3718 */     CommonTree root_0 = null;
/*      */ 
/* 3720 */     Token labelOp = null;
/* 3721 */     Token g = null;
/* 3722 */     Token ACTION103 = null;
/* 3723 */     Token SEMPRED104 = null;
/* 3724 */     id_return id94 = null;
/*      */ 
/* 3726 */     atom_return atom95 = null;
/*      */ 
/* 3728 */     ebnfSuffix_return ebnfSuffix96 = null;
/*      */ 
/* 3730 */     id_return id97 = null;
/*      */ 
/* 3732 */     block_return block98 = null;
/*      */ 
/* 3734 */     ebnfSuffix_return ebnfSuffix99 = null;
/*      */ 
/* 3736 */     atom_return atom100 = null;
/*      */ 
/* 3738 */     ebnfSuffix_return ebnfSuffix101 = null;
/*      */ 
/* 3740 */     ebnf_return ebnf102 = null;
/*      */ 
/* 3742 */     treeSpec_return treeSpec105 = null;
/*      */ 
/* 3744 */     ebnfSuffix_return ebnfSuffix106 = null;
/*      */ 
/* 3747 */     CommonTree labelOp_tree = null;
/* 3748 */     CommonTree g_tree = null;
/* 3749 */     CommonTree ACTION103_tree = null;
/* 3750 */     CommonTree SEMPRED104_tree = null;
/* 3751 */     RewriteRuleTokenStream stream_LIST_LABEL_ASSIGN = new RewriteRuleTokenStream(this.adaptor, "token LIST_LABEL_ASSIGN");
/* 3752 */     RewriteRuleTokenStream stream_LABEL_ASSIGN = new RewriteRuleTokenStream(this.adaptor, "token LABEL_ASSIGN");
/* 3753 */     RewriteRuleTokenStream stream_SEMPRED = new RewriteRuleTokenStream(this.adaptor, "token SEMPRED");
/* 3754 */     RewriteRuleTokenStream stream_86 = new RewriteRuleTokenStream(this.adaptor, "token 86");
/* 3755 */     RewriteRuleSubtreeStream stream_id = new RewriteRuleSubtreeStream(this.adaptor, "rule id");
/* 3756 */     RewriteRuleSubtreeStream stream_atom = new RewriteRuleSubtreeStream(this.adaptor, "rule atom");
/* 3757 */     RewriteRuleSubtreeStream stream_ebnfSuffix = new RewriteRuleSubtreeStream(this.adaptor, "rule ebnfSuffix");
/* 3758 */     RewriteRuleSubtreeStream stream_block = new RewriteRuleSubtreeStream(this.adaptor, "rule block");
/* 3759 */     RewriteRuleSubtreeStream stream_treeSpec = new RewriteRuleSubtreeStream(this.adaptor, "rule treeSpec");
/*      */     try
/*      */     {
/* 3762 */       int alt46 = 7;
/* 3763 */       alt46 = this.dfa46.predict(this.input);
/*      */       int alt39;
/*      */       int alt40;
/*      */       Object stream_labelOp;
/*      */       int alt41;
/*      */       int alt42;
/*      */       int alt43;
/*      */       Object stream_retval;
/*      */       element_return localelement_return4;
/*      */       int alt44;
/*      */       Object stream_retval;
/* 3764 */       switch (alt46)
/*      */       {
/*      */       case 1:
/* 3768 */         pushFollow(FOLLOW_id_in_element1496);
/* 3769 */         id94 = id();
/*      */ 
/* 3771 */         this.state._fsp -= 1;
/* 3772 */         if (this.state.failed) { element_return localelement_return1 = retval; return localelement_return1; }
/* 3773 */         if (this.state.backtracking == 0) stream_id.add(id94.getTree());
/*      */ 
/* 3775 */         alt39 = 2;
/*      */         Object nvae;
/* 3776 */         switch (this.input.LA(1))
/*      */         {
/*      */         case 41:
/* 3779 */           alt39 = 1;
/*      */ 
/* 3781 */           break;
/*      */         case 42:
/* 3784 */           alt39 = 2;
/*      */ 
/* 3786 */           break;
/*      */         default:
/* 3788 */           if (this.state.backtracking > 0) { this.state.failed = true; element_return localelement_return6 = retval; return localelement_return6; }
/* 3789 */           nvae = new NoViableAltException("", 39, 0, this.input);
/*      */ 
/* 3792 */           throw ((Throwable)nvae);
/*      */         }
/*      */ 
/* 3795 */         switch (alt39)
/*      */         {
/*      */         case 1:
/* 3799 */           labelOp = (Token)match(this.input, 41, FOLLOW_LABEL_ASSIGN_in_element1501); if (this.state.failed) { nvae = retval; return nvae; }
/* 3800 */           if (this.state.backtracking == 0) stream_LABEL_ASSIGN.add(labelOp); break;
/*      */         case 2:
/* 3808 */           labelOp = (Token)match(this.input, 42, FOLLOW_LIST_LABEL_ASSIGN_in_element1505); if (this.state.failed) { nvae = retval; return nvae; }
/* 3809 */           if (this.state.backtracking == 0) stream_LIST_LABEL_ASSIGN.add(labelOp);
/*      */ 
/*      */           break;
/*      */         }
/*      */ 
/* 3817 */         pushFollow(FOLLOW_atom_in_element1508);
/* 3818 */         atom95 = atom();
/*      */ 
/* 3820 */         this.state._fsp -= 1;
/* 3821 */         if (this.state.failed) { nvae = retval; return nvae; }
/* 3822 */         if (this.state.backtracking == 0) stream_atom.add(atom95.getTree());
/*      */ 
/* 3824 */         alt40 = 2;
/*      */         Object nvae;
/* 3825 */         switch (this.input.LA(1))
/*      */         {
/*      */         case 74:
/*      */         case 91:
/*      */         case 92:
/* 3830 */           alt40 = 1;
/*      */ 
/* 3832 */           break;
/*      */         case 31:
/*      */         case 36:
/*      */         case 39:
/*      */         case 44:
/*      */         case 45:
/*      */         case 46:
/*      */         case 47:
/*      */         case 51:
/*      */         case 71:
/*      */         case 81:
/*      */         case 82:
/*      */         case 83:
/*      */         case 87:
/*      */         case 90:
/* 3848 */           alt40 = 2;
/*      */ 
/* 3850 */           break;
/*      */         case 32:
/*      */         case 33:
/*      */         case 34:
/*      */         case 35:
/*      */         case 37:
/*      */         case 38:
/*      */         case 40:
/*      */         case 41:
/*      */         case 42:
/*      */         case 43:
/*      */         case 48:
/*      */         case 49:
/*      */         case 50:
/*      */         case 52:
/*      */         case 53:
/*      */         case 54:
/*      */         case 55:
/*      */         case 56:
/*      */         case 57:
/*      */         case 58:
/*      */         case 59:
/*      */         case 60:
/*      */         case 61:
/*      */         case 62:
/*      */         case 63:
/*      */         case 64:
/*      */         case 65:
/*      */         case 66:
/*      */         case 67:
/*      */         case 68:
/*      */         case 69:
/*      */         case 70:
/*      */         case 72:
/*      */         case 73:
/*      */         case 75:
/*      */         case 76:
/*      */         case 77:
/*      */         case 78:
/*      */         case 79:
/*      */         case 80:
/*      */         case 84:
/*      */         case 85:
/*      */         case 86:
/*      */         case 88:
/*      */         case 89:
/*      */         default:
/* 3852 */           if (this.state.backtracking > 0) { this.state.failed = true; element_return localelement_return9 = retval; return localelement_return9; }
/* 3853 */           nvae = new NoViableAltException("", 40, 0, this.input);
/*      */ 
/* 3856 */           throw ((Throwable)nvae);
/*      */         }
/*      */ 
/* 3859 */         switch (alt40)
/*      */         {
/*      */         case 1:
/* 3863 */           pushFollow(FOLLOW_ebnfSuffix_in_element1514);
/* 3864 */           ebnfSuffix96 = ebnfSuffix();
/*      */ 
/* 3866 */           this.state._fsp -= 1;
/* 3867 */           if (this.state.failed) { nvae = retval; return nvae; }
/* 3868 */           if (this.state.backtracking == 0) stream_ebnfSuffix.add(ebnfSuffix96.getTree());
/*      */ 
/* 3878 */           if (this.state.backtracking == 0) {
/* 3879 */             retval.tree = root_0;
/* 3880 */             RewriteRuleTokenStream stream_labelOp = new RewriteRuleTokenStream(this.adaptor, "token labelOp", labelOp);
/* 3881 */             RewriteRuleSubtreeStream stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.tree : null);
/*      */ 
/* 3883 */             root_0 = (CommonTree)this.adaptor.nil();
/*      */ 
/* 3888 */             CommonTree root_1 = (CommonTree)this.adaptor.nil();
/* 3889 */             root_1 = (CommonTree)this.adaptor.becomeRoot(stream_ebnfSuffix.nextNode(), root_1);
/*      */ 
/* 3893 */             CommonTree root_2 = (CommonTree)this.adaptor.nil();
/* 3894 */             root_2 = (CommonTree)this.adaptor.becomeRoot((CommonTree)this.adaptor.create(8, "BLOCK"), root_2);
/*      */ 
/* 3898 */             CommonTree root_3 = (CommonTree)this.adaptor.nil();
/* 3899 */             root_3 = (CommonTree)this.adaptor.becomeRoot((CommonTree)this.adaptor.create(16, "ALT"), root_3);
/*      */ 
/* 3903 */             CommonTree root_4 = (CommonTree)this.adaptor.nil();
/* 3904 */             root_4 = (CommonTree)this.adaptor.becomeRoot(stream_labelOp.nextNode(), root_4);
/*      */ 
/* 3906 */             this.adaptor.addChild(root_4, stream_id.nextTree());
/* 3907 */             this.adaptor.addChild(root_4, stream_atom.nextTree());
/*      */ 
/* 3909 */             this.adaptor.addChild(root_3, root_4);
/*      */ 
/* 3911 */             this.adaptor.addChild(root_3, (CommonTree)this.adaptor.create(19, "EOA"));
/*      */ 
/* 3913 */             this.adaptor.addChild(root_2, root_3);
/*      */ 
/* 3915 */             this.adaptor.addChild(root_2, (CommonTree)this.adaptor.create(18, "EOB"));
/*      */ 
/* 3917 */             this.adaptor.addChild(root_1, root_2);
/*      */ 
/* 3920 */             this.adaptor.addChild(root_0, root_1);
/*      */ 
/* 3925 */             retval.tree = root_0; } break;
/*      */         case 2:
/* 3939 */           if (this.state.backtracking == 0) {
/* 3940 */             retval.tree = root_0;
/* 3941 */             stream_labelOp = new RewriteRuleTokenStream(this.adaptor, "token labelOp", labelOp);
/* 3942 */             RewriteRuleSubtreeStream stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.tree : null);
/*      */ 
/* 3944 */             root_0 = (CommonTree)this.adaptor.nil();
/*      */ 
/* 3949 */             CommonTree root_1 = (CommonTree)this.adaptor.nil();
/* 3950 */             root_1 = (CommonTree)this.adaptor.becomeRoot(((RewriteRuleTokenStream)stream_labelOp).nextNode(), root_1);
/*      */ 
/* 3952 */             this.adaptor.addChild(root_1, stream_id.nextTree());
/* 3953 */             this.adaptor.addChild(root_1, stream_atom.nextTree());
/*      */ 
/* 3955 */             this.adaptor.addChild(root_0, root_1);
/*      */ 
/* 3960 */             retval.tree = root_0;
/*      */           }
/*      */ 
/*      */           break;
/*      */         }
/*      */ 
/* 3968 */         break;
/*      */       case 2:
/* 3972 */         pushFollow(FOLLOW_id_in_element1573);
/* 3973 */         id97 = id();
/*      */ 
/* 3975 */         this.state._fsp -= 1;
/* 3976 */         if (this.state.failed) { element_return localelement_return2 = retval; return localelement_return2; }
/* 3977 */         if (this.state.backtracking == 0) stream_id.add(id97.getTree());
/*      */ 
/* 3979 */         alt41 = 2;
/*      */         Object nvae;
/* 3980 */         switch (this.input.LA(1))
/*      */         {
/*      */         case 41:
/* 3983 */           alt41 = 1;
/*      */ 
/* 3985 */           break;
/*      */         case 42:
/* 3988 */           alt41 = 2;
/*      */ 
/* 3990 */           break;
/*      */         default:
/* 3992 */           if (this.state.backtracking > 0) { this.state.failed = true; element_return localelement_return7 = retval; return localelement_return7; }
/* 3993 */           nvae = new NoViableAltException("", 41, 0, this.input);
/*      */ 
/* 3996 */           throw ((Throwable)nvae);
/*      */         }
/*      */ 
/* 3999 */         switch (alt41)
/*      */         {
/*      */         case 1:
/* 4003 */           labelOp = (Token)match(this.input, 41, FOLLOW_LABEL_ASSIGN_in_element1578); if (this.state.failed) { nvae = retval; return nvae; }
/* 4004 */           if (this.state.backtracking == 0) stream_LABEL_ASSIGN.add(labelOp); break;
/*      */         case 2:
/* 4012 */           labelOp = (Token)match(this.input, 42, FOLLOW_LIST_LABEL_ASSIGN_in_element1582); if (this.state.failed) { nvae = retval; return nvae; }
/* 4013 */           if (this.state.backtracking == 0) stream_LIST_LABEL_ASSIGN.add(labelOp);
/*      */ 
/*      */           break;
/*      */         }
/*      */ 
/* 4021 */         pushFollow(FOLLOW_block_in_element1585);
/* 4022 */         block98 = block();
/*      */ 
/* 4024 */         this.state._fsp -= 1;
/* 4025 */         if (this.state.failed) { nvae = retval; return nvae; }
/* 4026 */         if (this.state.backtracking == 0) stream_block.add(block98.getTree());
/*      */ 
/* 4028 */         alt42 = 2;
/*      */         Object nvae;
/* 4029 */         switch (this.input.LA(1))
/*      */         {
/*      */         case 74:
/*      */         case 91:
/*      */         case 92:
/* 4034 */           alt42 = 1;
/*      */ 
/* 4036 */           break;
/*      */         case 31:
/*      */         case 36:
/*      */         case 39:
/*      */         case 44:
/*      */         case 45:
/*      */         case 46:
/*      */         case 47:
/*      */         case 51:
/*      */         case 71:
/*      */         case 81:
/*      */         case 82:
/*      */         case 83:
/*      */         case 87:
/*      */         case 90:
/* 4052 */           alt42 = 2;
/*      */ 
/* 4054 */           break;
/*      */         case 32:
/*      */         case 33:
/*      */         case 34:
/*      */         case 35:
/*      */         case 37:
/*      */         case 38:
/*      */         case 40:
/*      */         case 41:
/*      */         case 42:
/*      */         case 43:
/*      */         case 48:
/*      */         case 49:
/*      */         case 50:
/*      */         case 52:
/*      */         case 53:
/*      */         case 54:
/*      */         case 55:
/*      */         case 56:
/*      */         case 57:
/*      */         case 58:
/*      */         case 59:
/*      */         case 60:
/*      */         case 61:
/*      */         case 62:
/*      */         case 63:
/*      */         case 64:
/*      */         case 65:
/*      */         case 66:
/*      */         case 67:
/*      */         case 68:
/*      */         case 69:
/*      */         case 70:
/*      */         case 72:
/*      */         case 73:
/*      */         case 75:
/*      */         case 76:
/*      */         case 77:
/*      */         case 78:
/*      */         case 79:
/*      */         case 80:
/*      */         case 84:
/*      */         case 85:
/*      */         case 86:
/*      */         case 88:
/*      */         case 89:
/*      */         default:
/* 4056 */           if (this.state.backtracking > 0) { this.state.failed = true; stream_labelOp = retval; return stream_labelOp; }
/* 4057 */           nvae = new NoViableAltException("", 42, 0, this.input);
/*      */ 
/* 4060 */           throw ((Throwable)nvae);
/*      */         }
/*      */ 
/* 4063 */         switch (alt42)
/*      */         {
/*      */         case 1:
/* 4067 */           pushFollow(FOLLOW_ebnfSuffix_in_element1591);
/* 4068 */           ebnfSuffix99 = ebnfSuffix();
/*      */ 
/* 4070 */           this.state._fsp -= 1;
/* 4071 */           if (this.state.failed) { nvae = retval; return nvae; }
/* 4072 */           if (this.state.backtracking == 0) stream_ebnfSuffix.add(ebnfSuffix99.getTree());
/*      */ 
/* 4082 */           if (this.state.backtracking == 0) {
/* 4083 */             retval.tree = root_0;
/* 4084 */             RewriteRuleTokenStream stream_labelOp = new RewriteRuleTokenStream(this.adaptor, "token labelOp", labelOp);
/* 4085 */             RewriteRuleSubtreeStream stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.tree : null);
/*      */ 
/* 4087 */             root_0 = (CommonTree)this.adaptor.nil();
/*      */ 
/* 4092 */             CommonTree root_1 = (CommonTree)this.adaptor.nil();
/* 4093 */             root_1 = (CommonTree)this.adaptor.becomeRoot(stream_ebnfSuffix.nextNode(), root_1);
/*      */ 
/* 4097 */             CommonTree root_2 = (CommonTree)this.adaptor.nil();
/* 4098 */             root_2 = (CommonTree)this.adaptor.becomeRoot((CommonTree)this.adaptor.create(8, "BLOCK"), root_2);
/*      */ 
/* 4102 */             CommonTree root_3 = (CommonTree)this.adaptor.nil();
/* 4103 */             root_3 = (CommonTree)this.adaptor.becomeRoot((CommonTree)this.adaptor.create(16, "ALT"), root_3);
/*      */ 
/* 4107 */             CommonTree root_4 = (CommonTree)this.adaptor.nil();
/* 4108 */             root_4 = (CommonTree)this.adaptor.becomeRoot(stream_labelOp.nextNode(), root_4);
/*      */ 
/* 4110 */             this.adaptor.addChild(root_4, stream_id.nextTree());
/* 4111 */             this.adaptor.addChild(root_4, stream_block.nextTree());
/*      */ 
/* 4113 */             this.adaptor.addChild(root_3, root_4);
/*      */ 
/* 4115 */             this.adaptor.addChild(root_3, (CommonTree)this.adaptor.create(19, "EOA"));
/*      */ 
/* 4117 */             this.adaptor.addChild(root_2, root_3);
/*      */ 
/* 4119 */             this.adaptor.addChild(root_2, (CommonTree)this.adaptor.create(18, "EOB"));
/*      */ 
/* 4121 */             this.adaptor.addChild(root_1, root_2);
/*      */ 
/* 4124 */             this.adaptor.addChild(root_0, root_1);
/*      */ 
/* 4129 */             retval.tree = root_0; } break;
/*      */         case 2:
/* 4143 */           if (this.state.backtracking == 0) {
/* 4144 */             retval.tree = root_0;
/* 4145 */             RewriteRuleTokenStream stream_labelOp = new RewriteRuleTokenStream(this.adaptor, "token labelOp", labelOp);
/* 4146 */             RewriteRuleSubtreeStream stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.tree : null);
/*      */ 
/* 4148 */             root_0 = (CommonTree)this.adaptor.nil();
/*      */ 
/* 4153 */             CommonTree root_1 = (CommonTree)this.adaptor.nil();
/* 4154 */             root_1 = (CommonTree)this.adaptor.becomeRoot(stream_labelOp.nextNode(), root_1);
/*      */ 
/* 4156 */             this.adaptor.addChild(root_1, stream_id.nextTree());
/* 4157 */             this.adaptor.addChild(root_1, stream_block.nextTree());
/*      */ 
/* 4159 */             this.adaptor.addChild(root_0, root_1);
/*      */ 
/* 4164 */             retval.tree = root_0;
/*      */           }
/*      */ 
/*      */           break;
/*      */         }
/*      */ 
/* 4172 */         break;
/*      */       case 3:
/* 4176 */         pushFollow(FOLLOW_atom_in_element1650);
/* 4177 */         atom100 = atom();
/*      */ 
/* 4179 */         this.state._fsp -= 1;
/* 4180 */         if (this.state.failed) { element_return localelement_return3 = retval; return localelement_return3; }
/* 4181 */         if (this.state.backtracking == 0) stream_atom.add(atom100.getTree());
/*      */ 
/* 4183 */         alt43 = 2;
/*      */         Object nvae;
/* 4184 */         switch (this.input.LA(1))
/*      */         {
/*      */         case 74:
/*      */         case 91:
/*      */         case 92:
/* 4189 */           alt43 = 1;
/*      */ 
/* 4191 */           break;
/*      */         case 31:
/*      */         case 36:
/*      */         case 39:
/*      */         case 44:
/*      */         case 45:
/*      */         case 46:
/*      */         case 47:
/*      */         case 51:
/*      */         case 71:
/*      */         case 81:
/*      */         case 82:
/*      */         case 83:
/*      */         case 87:
/*      */         case 90:
/* 4207 */           alt43 = 2;
/*      */ 
/* 4209 */           break;
/*      */         case 32:
/*      */         case 33:
/*      */         case 34:
/*      */         case 35:
/*      */         case 37:
/*      */         case 38:
/*      */         case 40:
/*      */         case 41:
/*      */         case 42:
/*      */         case 43:
/*      */         case 48:
/*      */         case 49:
/*      */         case 50:
/*      */         case 52:
/*      */         case 53:
/*      */         case 54:
/*      */         case 55:
/*      */         case 56:
/*      */         case 57:
/*      */         case 58:
/*      */         case 59:
/*      */         case 60:
/*      */         case 61:
/*      */         case 62:
/*      */         case 63:
/*      */         case 64:
/*      */         case 65:
/*      */         case 66:
/*      */         case 67:
/*      */         case 68:
/*      */         case 69:
/*      */         case 70:
/*      */         case 72:
/*      */         case 73:
/*      */         case 75:
/*      */         case 76:
/*      */         case 77:
/*      */         case 78:
/*      */         case 79:
/*      */         case 80:
/*      */         case 84:
/*      */         case 85:
/*      */         case 86:
/*      */         case 88:
/*      */         case 89:
/*      */         default:
/* 4211 */           if (this.state.backtracking > 0) { this.state.failed = true; element_return localelement_return8 = retval; return localelement_return8; }
/* 4212 */           nvae = new NoViableAltException("", 43, 0, this.input);
/*      */ 
/* 4215 */           throw ((Throwable)nvae);
/*      */         }
/*      */ 
/* 4218 */         switch (alt43)
/*      */         {
/*      */         case 1:
/* 4222 */           pushFollow(FOLLOW_ebnfSuffix_in_element1656);
/* 4223 */           ebnfSuffix101 = ebnfSuffix();
/*      */ 
/* 4225 */           this.state._fsp -= 1;
/* 4226 */           if (this.state.failed) { nvae = retval; return nvae; }
/* 4227 */           if (this.state.backtracking == 0) stream_ebnfSuffix.add(ebnfSuffix101.getTree());
/*      */ 
/* 4237 */           if (this.state.backtracking == 0) {
/* 4238 */             retval.tree = root_0;
/* 4239 */             RewriteRuleSubtreeStream stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.tree : null);
/*      */ 
/* 4241 */             root_0 = (CommonTree)this.adaptor.nil();
/*      */ 
/* 4246 */             CommonTree root_1 = (CommonTree)this.adaptor.nil();
/* 4247 */             root_1 = (CommonTree)this.adaptor.becomeRoot(stream_ebnfSuffix.nextNode(), root_1);
/*      */ 
/* 4251 */             CommonTree root_2 = (CommonTree)this.adaptor.nil();
/* 4252 */             root_2 = (CommonTree)this.adaptor.becomeRoot((CommonTree)this.adaptor.create(8, "BLOCK"), root_2);
/*      */ 
/* 4256 */             CommonTree root_3 = (CommonTree)this.adaptor.nil();
/* 4257 */             root_3 = (CommonTree)this.adaptor.becomeRoot((CommonTree)this.adaptor.create(16, "ALT"), root_3);
/*      */ 
/* 4259 */             this.adaptor.addChild(root_3, stream_atom.nextTree());
/* 4260 */             this.adaptor.addChild(root_3, (CommonTree)this.adaptor.create(19, "EOA"));
/*      */ 
/* 4262 */             this.adaptor.addChild(root_2, root_3);
/*      */ 
/* 4264 */             this.adaptor.addChild(root_2, (CommonTree)this.adaptor.create(18, "EOB"));
/*      */ 
/* 4266 */             this.adaptor.addChild(root_1, root_2);
/*      */ 
/* 4269 */             this.adaptor.addChild(root_0, root_1);
/*      */ 
/* 4274 */             retval.tree = root_0; } break;
/*      */         case 2:
/* 4288 */           if (this.state.backtracking == 0) {
/* 4289 */             retval.tree = root_0;
/* 4290 */             stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.tree : null);
/*      */ 
/* 4292 */             root_0 = (CommonTree)this.adaptor.nil();
/*      */ 
/* 4295 */             this.adaptor.addChild(root_0, stream_atom.nextTree());
/*      */ 
/* 4299 */             retval.tree = root_0;
/*      */           }
/*      */ 
/*      */           break;
/*      */         }
/*      */ 
/* 4307 */         break;
/*      */       case 4:
/* 4311 */         root_0 = (CommonTree)this.adaptor.nil();
/*      */ 
/* 4313 */         pushFollow(FOLLOW_ebnf_in_element1702);
/* 4314 */         ebnf102 = ebnf();
/*      */ 
/* 4316 */         this.state._fsp -= 1;
/* 4317 */         if (this.state.failed) { localelement_return4 = retval; return localelement_return4; }
/* 4318 */         if (this.state.backtracking == 0) this.adaptor.addChild(root_0, ebnf102.getTree()); break;
/*      */       case 5:
/* 4325 */         root_0 = (CommonTree)this.adaptor.nil();
/*      */ 
/* 4327 */         ACTION103 = (Token)match(this.input, 47, FOLLOW_ACTION_in_element1709); if (this.state.failed) { localelement_return4 = retval; return localelement_return4; }
/* 4328 */         if (this.state.backtracking == 0) {
/* 4329 */           ACTION103_tree = (CommonTree)this.adaptor.create(ACTION103);
/* 4330 */           this.adaptor.addChild(root_0, ACTION103_tree); } break;
/*      */       case 6:
/* 4338 */         SEMPRED104 = (Token)match(this.input, 31, FOLLOW_SEMPRED_in_element1716); if (this.state.failed) { localelement_return4 = retval; return localelement_return4; }
/* 4339 */         if (this.state.backtracking == 0) stream_SEMPRED.add(SEMPRED104);
/*      */ 
/* 4342 */         alt44 = 2;
/*      */         Object nvae;
/* 4343 */         switch (this.input.LA(1))
/*      */         {
/*      */         case 86:
/* 4346 */           alt44 = 1;
/*      */ 
/* 4348 */           break;
/*      */         case 31:
/*      */         case 36:
/*      */         case 39:
/*      */         case 44:
/*      */         case 45:
/*      */         case 46:
/*      */         case 47:
/*      */         case 51:
/*      */         case 71:
/*      */         case 81:
/*      */         case 82:
/*      */         case 83:
/*      */         case 87:
/*      */         case 90:
/* 4364 */           alt44 = 2;
/*      */ 
/* 4366 */           break;
/*      */         case 32:
/*      */         case 33:
/*      */         case 34:
/*      */         case 35:
/*      */         case 37:
/*      */         case 38:
/*      */         case 40:
/*      */         case 41:
/*      */         case 42:
/*      */         case 43:
/*      */         case 48:
/*      */         case 49:
/*      */         case 50:
/*      */         case 52:
/*      */         case 53:
/*      */         case 54:
/*      */         case 55:
/*      */         case 56:
/*      */         case 57:
/*      */         case 58:
/*      */         case 59:
/*      */         case 60:
/*      */         case 61:
/*      */         case 62:
/*      */         case 63:
/*      */         case 64:
/*      */         case 65:
/*      */         case 66:
/*      */         case 67:
/*      */         case 68:
/*      */         case 69:
/*      */         case 70:
/*      */         case 72:
/*      */         case 73:
/*      */         case 74:
/*      */         case 75:
/*      */         case 76:
/*      */         case 77:
/*      */         case 78:
/*      */         case 79:
/*      */         case 80:
/*      */         case 84:
/*      */         case 85:
/*      */         case 88:
/*      */         case 89:
/*      */         default:
/* 4368 */           if (this.state.backtracking > 0) { this.state.failed = true; stream_retval = retval; return stream_retval; }
/* 4369 */           nvae = new NoViableAltException("", 44, 0, this.input);
/*      */ 
/* 4372 */           throw ((Throwable)nvae);
/*      */         }
/*      */ 
/* 4375 */         switch (alt44)
/*      */         {
/*      */         case 1:
/* 4379 */           g = (Token)match(this.input, 86, FOLLOW_86_in_element1722); if (this.state.failed) { nvae = retval; return nvae; }
/* 4380 */           if (this.state.backtracking == 0) stream_86.add(g);
/*      */ 
/* 4391 */           if (this.state.backtracking == 0) {
/* 4392 */             retval.tree = root_0;
/* 4393 */             RewriteRuleSubtreeStream stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.tree : null);
/*      */ 
/* 4395 */             root_0 = (CommonTree)this.adaptor.nil();
/*      */ 
/* 4398 */             this.adaptor.addChild(root_0, (CommonTree)this.adaptor.create(32, g));
/*      */ 
/* 4402 */             retval.tree = root_0; } break;
/*      */         case 2:
/* 4416 */           if (this.state.backtracking == 0) {
/* 4417 */             retval.tree = root_0;
/* 4418 */             stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.tree : null);
/*      */ 
/* 4420 */             root_0 = (CommonTree)this.adaptor.nil();
/*      */ 
/* 4423 */             this.adaptor.addChild(root_0, stream_SEMPRED.nextNode());
/*      */ 
/* 4427 */             retval.tree = root_0;
/*      */           }
/*      */ 
/*      */           break;
/*      */         }
/*      */ 
/* 4435 */         break;
/*      */       case 7:
/* 4439 */         pushFollow(FOLLOW_treeSpec_in_element1742);
/* 4440 */         treeSpec105 = treeSpec();
/*      */ 
/* 4442 */         this.state._fsp -= 1;
/* 4443 */         if (this.state.failed) { element_return localelement_return5 = retval; return localelement_return5; }
/* 4444 */         if (this.state.backtracking == 0) stream_treeSpec.add(treeSpec105.getTree());
/*      */ 
/* 4446 */         int alt45 = 2;
/*      */         Object nvae;
/* 4447 */         switch (this.input.LA(1))
/*      */         {
/*      */         case 74:
/*      */         case 91:
/*      */         case 92:
/* 4452 */           alt45 = 1;
/*      */ 
/* 4454 */           break;
/*      */         case 31:
/*      */         case 36:
/*      */         case 39:
/*      */         case 44:
/*      */         case 45:
/*      */         case 46:
/*      */         case 47:
/*      */         case 51:
/*      */         case 71:
/*      */         case 81:
/*      */         case 82:
/*      */         case 83:
/*      */         case 87:
/*      */         case 90:
/* 4470 */           alt45 = 2;
/*      */ 
/* 4472 */           break;
/*      */         case 32:
/*      */         case 33:
/*      */         case 34:
/*      */         case 35:
/*      */         case 37:
/*      */         case 38:
/*      */         case 40:
/*      */         case 41:
/*      */         case 42:
/*      */         case 43:
/*      */         case 48:
/*      */         case 49:
/*      */         case 50:
/*      */         case 52:
/*      */         case 53:
/*      */         case 54:
/*      */         case 55:
/*      */         case 56:
/*      */         case 57:
/*      */         case 58:
/*      */         case 59:
/*      */         case 60:
/*      */         case 61:
/*      */         case 62:
/*      */         case 63:
/*      */         case 64:
/*      */         case 65:
/*      */         case 66:
/*      */         case 67:
/*      */         case 68:
/*      */         case 69:
/*      */         case 70:
/*      */         case 72:
/*      */         case 73:
/*      */         case 75:
/*      */         case 76:
/*      */         case 77:
/*      */         case 78:
/*      */         case 79:
/*      */         case 80:
/*      */         case 84:
/*      */         case 85:
/*      */         case 86:
/*      */         case 88:
/*      */         case 89:
/*      */         default:
/* 4474 */           if (this.state.backtracking > 0) { this.state.failed = true; stream_retval = retval; return stream_retval; }
/* 4475 */           nvae = new NoViableAltException("", 45, 0, this.input);
/*      */ 
/* 4478 */           throw ((Throwable)nvae);
/*      */         }
/*      */ 
/* 4481 */         switch (alt45)
/*      */         {
/*      */         case 1:
/* 4485 */           pushFollow(FOLLOW_ebnfSuffix_in_element1748);
/* 4486 */           ebnfSuffix106 = ebnfSuffix();
/*      */ 
/* 4488 */           this.state._fsp -= 1;
/* 4489 */           if (this.state.failed) { nvae = retval; return nvae; }
/* 4490 */           if (this.state.backtracking == 0) stream_ebnfSuffix.add(ebnfSuffix106.getTree());
/*      */ 
/* 4500 */           if (this.state.backtracking == 0) {
/* 4501 */             retval.tree = root_0;
/* 4502 */             RewriteRuleSubtreeStream stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.tree : null);
/*      */ 
/* 4504 */             root_0 = (CommonTree)this.adaptor.nil();
/*      */ 
/* 4509 */             CommonTree root_1 = (CommonTree)this.adaptor.nil();
/* 4510 */             root_1 = (CommonTree)this.adaptor.becomeRoot(stream_ebnfSuffix.nextNode(), root_1);
/*      */ 
/* 4514 */             CommonTree root_2 = (CommonTree)this.adaptor.nil();
/* 4515 */             root_2 = (CommonTree)this.adaptor.becomeRoot((CommonTree)this.adaptor.create(8, "BLOCK"), root_2);
/*      */ 
/* 4519 */             CommonTree root_3 = (CommonTree)this.adaptor.nil();
/* 4520 */             root_3 = (CommonTree)this.adaptor.becomeRoot((CommonTree)this.adaptor.create(16, "ALT"), root_3);
/*      */ 
/* 4522 */             this.adaptor.addChild(root_3, stream_treeSpec.nextTree());
/* 4523 */             this.adaptor.addChild(root_3, (CommonTree)this.adaptor.create(19, "EOA"));
/*      */ 
/* 4525 */             this.adaptor.addChild(root_2, root_3);
/*      */ 
/* 4527 */             this.adaptor.addChild(root_2, (CommonTree)this.adaptor.create(18, "EOB"));
/*      */ 
/* 4529 */             this.adaptor.addChild(root_1, root_2);
/*      */ 
/* 4532 */             this.adaptor.addChild(root_0, root_1);
/*      */ 
/* 4537 */             retval.tree = root_0; } break;
/*      */         case 2:
/* 4551 */           if (this.state.backtracking == 0) {
/* 4552 */             retval.tree = root_0;
/* 4553 */             RewriteRuleSubtreeStream stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.tree : null);
/*      */ 
/* 4555 */             root_0 = (CommonTree)this.adaptor.nil();
/*      */ 
/* 4558 */             this.adaptor.addChild(root_0, stream_treeSpec.nextTree());
/*      */ 
/* 4562 */             retval.tree = root_0;
/*      */           }
/*      */ 
/*      */           break;
/*      */         }
/*      */ 
/*      */         break;
/*      */       }
/*      */ 
/* 4573 */       retval.stop = this.input.LT(-1);
/*      */ 
/* 4575 */       if (this.state.backtracking == 0)
/*      */       {
/* 4577 */         retval.tree = ((CommonTree)this.adaptor.rulePostProcessing(root_0));
/* 4578 */         this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
/*      */       }
/*      */     } catch (RecognitionException re) {
/* 4581 */       re = 
/* 4588 */         re;
/*      */ 
/* 4582 */       reportError(re);
/* 4583 */       recover(this.input, re);
/* 4584 */       retval.tree = ((CommonTree)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), re));
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/* 4589 */     return retval;
/*      */   }
/*      */ 
/*      */   public final atom_return atom()
/*      */     throws RecognitionException
/*      */   {
/* 4601 */     atom_return retval = new atom_return();
/* 4602 */     retval.start = this.input.LT(1);
/*      */ 
/* 4604 */     CommonTree root_0 = null;
/*      */ 
/* 4606 */     Token op = null;
/* 4607 */     Token RULE_REF110 = null;
/* 4608 */     Token ARG_ACTION111 = null;
/* 4609 */     terminal_return terminal107 = null;
/*      */ 
/* 4611 */     range_return range108 = null;
/*      */ 
/* 4613 */     notSet_return notSet109 = null;
/*      */ 
/* 4616 */     CommonTree op_tree = null;
/* 4617 */     CommonTree RULE_REF110_tree = null;
/* 4618 */     CommonTree ARG_ACTION111_tree = null;
/* 4619 */     RewriteRuleTokenStream stream_BANG = new RewriteRuleTokenStream(this.adaptor, "token BANG");
/* 4620 */     RewriteRuleTokenStream stream_ROOT = new RewriteRuleTokenStream(this.adaptor, "token ROOT");
/* 4621 */     RewriteRuleTokenStream stream_RULE_REF = new RewriteRuleTokenStream(this.adaptor, "token RULE_REF");
/* 4622 */     RewriteRuleTokenStream stream_ARG_ACTION = new RewriteRuleTokenStream(this.adaptor, "token ARG_ACTION");
/* 4623 */     RewriteRuleSubtreeStream stream_range = new RewriteRuleSubtreeStream(this.adaptor, "rule range");
/* 4624 */     RewriteRuleSubtreeStream stream_notSet = new RewriteRuleSubtreeStream(this.adaptor, "rule notSet");
/*      */     try
/*      */     {
/* 4627 */       int alt54 = 4;
/*      */       Object nvae;
/*      */       Object nvae;
/* 4628 */       switch (this.input.LA(1))
/*      */       {
/*      */       case 46:
/* 4631 */         switch (this.input.LA(2))
/*      */         {
/*      */         case 13:
/* 4634 */           alt54 = 2;
/*      */ 
/* 4636 */           break;
/*      */         case 31:
/*      */         case 36:
/*      */         case 37:
/*      */         case 38:
/*      */         case 39:
/*      */         case 44:
/*      */         case 45:
/*      */         case 46:
/*      */         case 47:
/*      */         case 51:
/*      */         case 71:
/*      */         case 74:
/*      */         case 81:
/*      */         case 82:
/*      */         case 83:
/*      */         case 87:
/*      */         case 88:
/*      */         case 90:
/*      */         case 91:
/*      */         case 92:
/* 4658 */           alt54 = 1;
/*      */ 
/* 4660 */           break;
/*      */         case 14:
/*      */         case 15:
/*      */         case 16:
/*      */         case 17:
/*      */         case 18:
/*      */         case 19:
/*      */         case 20:
/*      */         case 21:
/*      */         case 22:
/*      */         case 23:
/*      */         case 24:
/*      */         case 25:
/*      */         case 26:
/*      */         case 27:
/*      */         case 28:
/*      */         case 29:
/*      */         case 30:
/*      */         case 32:
/*      */         case 33:
/*      */         case 34:
/*      */         case 35:
/*      */         case 40:
/*      */         case 41:
/*      */         case 42:
/*      */         case 43:
/*      */         case 48:
/*      */         case 49:
/*      */         case 50:
/*      */         case 52:
/*      */         case 53:
/*      */         case 54:
/*      */         case 55:
/*      */         case 56:
/*      */         case 57:
/*      */         case 58:
/*      */         case 59:
/*      */         case 60:
/*      */         case 61:
/*      */         case 62:
/*      */         case 63:
/*      */         case 64:
/*      */         case 65:
/*      */         case 66:
/*      */         case 67:
/*      */         case 68:
/*      */         case 69:
/*      */         case 70:
/*      */         case 72:
/*      */         case 73:
/*      */         case 75:
/*      */         case 76:
/*      */         case 77:
/*      */         case 78:
/*      */         case 79:
/*      */         case 80:
/*      */         case 84:
/*      */         case 85:
/*      */         case 86:
/*      */         case 89:
/*      */         default:
/* 4662 */           if (this.state.backtracking > 0) { this.state.failed = true; atom_return localatom_return1 = retval; return localatom_return1; }
/* 4663 */           nvae = new NoViableAltException("", 54, 1, this.input);
/*      */ 
/* 4666 */           throw ((Throwable)nvae);
/*      */         }
/*      */ 
/* 4670 */         break;
/*      */       case 44:
/*      */       case 45:
/*      */       case 90:
/* 4675 */         alt54 = 1;
/*      */ 
/* 4677 */         break;
/*      */       case 87:
/* 4680 */         alt54 = 3;
/*      */ 
/* 4682 */         break;
/*      */       case 51:
/* 4685 */         alt54 = 4;
/*      */ 
/* 4687 */         break;
/*      */       default:
/* 4689 */         if (this.state.backtracking > 0) { this.state.failed = true; nvae = retval; return nvae; }
/* 4690 */         nvae = new NoViableAltException("", 54, 0, this.input);
/*      */ 
/* 4693 */         throw ((Throwable)nvae);
/*      */       }
/*      */       int alt48;
/*      */       Object stream_op;
/*      */       Object stream_retval;
/*      */       int alt50;
/*      */       Object stream_op;
/*      */       RewriteRuleSubtreeStream stream_retval;
/*      */       Object stream_retval;
/* 4696 */       switch (alt54)
/*      */       {
/*      */       case 1:
/* 4700 */         root_0 = (CommonTree)this.adaptor.nil();
/*      */ 
/* 4702 */         pushFollow(FOLLOW_terminal_in_atom1800);
/* 4703 */         terminal107 = terminal();
/*      */ 
/* 4705 */         this.state._fsp -= 1;
/* 4706 */         if (this.state.failed) { nvae = retval; return nvae; }
/* 4707 */         if (this.state.backtracking == 0) this.adaptor.addChild(root_0, terminal107.getTree()); break;
/*      */       case 2:
/* 4714 */         pushFollow(FOLLOW_range_in_atom1805);
/* 4715 */         range108 = range();
/*      */ 
/* 4717 */         this.state._fsp -= 1;
/* 4718 */         if (this.state.failed) { nvae = retval; return nvae; }
/* 4719 */         if (this.state.backtracking == 0) stream_range.add(range108.getTree());
/*      */ 
/* 4721 */         alt48 = 2;
/* 4722 */         switch (this.input.LA(1))
/*      */         {
/*      */         case 37:
/*      */         case 38:
/* 4726 */           alt48 = 1;
/*      */ 
/* 4728 */           break;
/*      */         case 31:
/*      */         case 36:
/*      */         case 39:
/*      */         case 44:
/*      */         case 45:
/*      */         case 46:
/*      */         case 47:
/*      */         case 51:
/*      */         case 71:
/*      */         case 74:
/*      */         case 81:
/*      */         case 82:
/*      */         case 83:
/*      */         case 87:
/*      */         case 90:
/*      */         case 91:
/*      */         case 92:
/* 4747 */           alt48 = 2;
/*      */ 
/* 4749 */           break;
/*      */         case 32:
/*      */         case 33:
/*      */         case 34:
/*      */         case 35:
/*      */         case 40:
/*      */         case 41:
/*      */         case 42:
/*      */         case 43:
/*      */         case 48:
/*      */         case 49:
/*      */         case 50:
/*      */         case 52:
/*      */         case 53:
/*      */         case 54:
/*      */         case 55:
/*      */         case 56:
/*      */         case 57:
/*      */         case 58:
/*      */         case 59:
/*      */         case 60:
/*      */         case 61:
/*      */         case 62:
/*      */         case 63:
/*      */         case 64:
/*      */         case 65:
/*      */         case 66:
/*      */         case 67:
/*      */         case 68:
/*      */         case 69:
/*      */         case 70:
/*      */         case 72:
/*      */         case 73:
/*      */         case 75:
/*      */         case 76:
/*      */         case 77:
/*      */         case 78:
/*      */         case 79:
/*      */         case 80:
/*      */         case 84:
/*      */         case 85:
/*      */         case 86:
/*      */         case 88:
/*      */         case 89:
/*      */         default:
/* 4751 */           if (this.state.backtracking > 0) { this.state.failed = true; atom_return localatom_return4 = retval; return localatom_return4; }
/* 4752 */           NoViableAltException nvae = new NoViableAltException("", 48, 0, this.input);
/*      */ 
/* 4755 */           throw nvae;
/*      */         }
/*      */ 
/* 4758 */         switch (alt48)
/*      */         {
/*      */         case 1:
/* 4763 */           int alt47 = 2;
/*      */           Object nvae;
/* 4764 */           switch (this.input.LA(1))
/*      */           {
/*      */           case 37:
/* 4767 */             alt47 = 1;
/*      */ 
/* 4769 */             break;
/*      */           case 38:
/* 4772 */             alt47 = 2;
/*      */ 
/* 4774 */             break;
/*      */           default:
/* 4776 */             if (this.state.backtracking > 0) { this.state.failed = true; atom_return localatom_return5 = retval; return localatom_return5; }
/* 4777 */             nvae = new NoViableAltException("", 47, 0, this.input);
/*      */ 
/* 4780 */             throw ((Throwable)nvae);
/*      */           }
/*      */ 
/* 4783 */           switch (alt47)
/*      */           {
/*      */           case 1:
/* 4787 */             op = (Token)match(this.input, 37, FOLLOW_ROOT_in_atom1815); if (this.state.failed) { nvae = retval; return nvae; }
/* 4788 */             if (this.state.backtracking == 0) stream_ROOT.add(op); break;
/*      */           case 2:
/* 4796 */             op = (Token)match(this.input, 38, FOLLOW_BANG_in_atom1819); if (this.state.failed) { nvae = retval; return nvae; }
/* 4797 */             if (this.state.backtracking == 0) stream_BANG.add(op);
/*      */ 
/*      */             break;
/*      */           }
/*      */ 
/* 4814 */           if (this.state.backtracking == 0) {
/* 4815 */             retval.tree = root_0;
/* 4816 */             stream_op = new RewriteRuleTokenStream(this.adaptor, "token op", op);
/* 4817 */             RewriteRuleSubtreeStream stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.tree : null);
/*      */ 
/* 4819 */             root_0 = (CommonTree)this.adaptor.nil();
/*      */ 
/* 4824 */             CommonTree root_1 = (CommonTree)this.adaptor.nil();
/* 4825 */             root_1 = (CommonTree)this.adaptor.becomeRoot(((RewriteRuleTokenStream)stream_op).nextNode(), root_1);
/*      */ 
/* 4827 */             this.adaptor.addChild(root_1, stream_range.nextTree());
/*      */ 
/* 4829 */             this.adaptor.addChild(root_0, root_1);
/*      */ 
/* 4834 */             retval.tree = root_0;
/*      */           }
/* 4836 */           break;
/*      */         case 2:
/* 4848 */           if (this.state.backtracking == 0) {
/* 4849 */             retval.tree = root_0;
/* 4850 */             stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.tree : null);
/*      */ 
/* 4852 */             root_0 = (CommonTree)this.adaptor.nil();
/*      */ 
/* 4855 */             this.adaptor.addChild(root_0, stream_range.nextTree());
/*      */ 
/* 4859 */             retval.tree = root_0;
/*      */           }
/*      */ 
/*      */           break;
/*      */         }
/*      */ 
/* 4867 */         break;
/*      */       case 3:
/* 4871 */         pushFollow(FOLLOW_notSet_in_atom1853);
/* 4872 */         notSet109 = notSet();
/*      */ 
/* 4874 */         this.state._fsp -= 1;
/* 4875 */         if (this.state.failed) { atom_return localatom_return2 = retval; return localatom_return2; }
/* 4876 */         if (this.state.backtracking == 0) stream_notSet.add(notSet109.getTree());
/*      */ 
/* 4878 */         alt50 = 2;
/* 4879 */         switch (this.input.LA(1))
/*      */         {
/*      */         case 37:
/*      */         case 38:
/* 4883 */           alt50 = 1;
/*      */ 
/* 4885 */           break;
/*      */         case 31:
/*      */         case 36:
/*      */         case 39:
/*      */         case 44:
/*      */         case 45:
/*      */         case 46:
/*      */         case 47:
/*      */         case 51:
/*      */         case 71:
/*      */         case 74:
/*      */         case 81:
/*      */         case 82:
/*      */         case 83:
/*      */         case 87:
/*      */         case 90:
/*      */         case 91:
/*      */         case 92:
/* 4904 */           alt50 = 2;
/*      */ 
/* 4906 */           break;
/*      */         case 32:
/*      */         case 33:
/*      */         case 34:
/*      */         case 35:
/*      */         case 40:
/*      */         case 41:
/*      */         case 42:
/*      */         case 43:
/*      */         case 48:
/*      */         case 49:
/*      */         case 50:
/*      */         case 52:
/*      */         case 53:
/*      */         case 54:
/*      */         case 55:
/*      */         case 56:
/*      */         case 57:
/*      */         case 58:
/*      */         case 59:
/*      */         case 60:
/*      */         case 61:
/*      */         case 62:
/*      */         case 63:
/*      */         case 64:
/*      */         case 65:
/*      */         case 66:
/*      */         case 67:
/*      */         case 68:
/*      */         case 69:
/*      */         case 70:
/*      */         case 72:
/*      */         case 73:
/*      */         case 75:
/*      */         case 76:
/*      */         case 77:
/*      */         case 78:
/*      */         case 79:
/*      */         case 80:
/*      */         case 84:
/*      */         case 85:
/*      */         case 86:
/*      */         case 88:
/*      */         case 89:
/*      */         default:
/* 4908 */           if (this.state.backtracking > 0) { this.state.failed = true; stream_retval = retval; return stream_retval; }
/* 4909 */           NoViableAltException nvae = new NoViableAltException("", 50, 0, this.input);
/*      */ 
/* 4912 */           throw nvae;
/*      */         }
/*      */ 
/* 4915 */         switch (alt50)
/*      */         {
/*      */         case 1:
/* 4920 */           int alt49 = 2;
/*      */           Object nvae;
/* 4921 */           switch (this.input.LA(1))
/*      */           {
/*      */           case 37:
/* 4924 */             alt49 = 1;
/*      */ 
/* 4926 */             break;
/*      */           case 38:
/* 4929 */             alt49 = 2;
/*      */ 
/* 4931 */             break;
/*      */           default:
/* 4933 */             if (this.state.backtracking > 0) { this.state.failed = true; stream_op = retval; return stream_op; }
/* 4934 */             nvae = new NoViableAltException("", 49, 0, this.input);
/*      */ 
/* 4937 */             throw ((Throwable)nvae);
/*      */           }
/*      */ 
/* 4940 */           switch (alt49)
/*      */           {
/*      */           case 1:
/* 4944 */             op = (Token)match(this.input, 37, FOLLOW_ROOT_in_atom1862); if (this.state.failed) { nvae = retval; return nvae; }
/* 4945 */             if (this.state.backtracking == 0) stream_ROOT.add(op); break;
/*      */           case 2:
/* 4953 */             op = (Token)match(this.input, 38, FOLLOW_BANG_in_atom1866); if (this.state.failed) { nvae = retval; return nvae; }
/* 4954 */             if (this.state.backtracking == 0) stream_BANG.add(op);
/*      */ 
/*      */             break;
/*      */           }
/*      */ 
/* 4971 */           if (this.state.backtracking == 0) {
/* 4972 */             retval.tree = root_0;
/* 4973 */             stream_op = new RewriteRuleTokenStream(this.adaptor, "token op", op);
/* 4974 */             stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.tree : null);
/*      */ 
/* 4976 */             root_0 = (CommonTree)this.adaptor.nil();
/*      */ 
/* 4981 */             CommonTree root_1 = (CommonTree)this.adaptor.nil();
/* 4982 */             root_1 = (CommonTree)this.adaptor.becomeRoot(((RewriteRuleTokenStream)stream_op).nextNode(), root_1);
/*      */ 
/* 4984 */             this.adaptor.addChild(root_1, stream_notSet.nextTree());
/*      */ 
/* 4986 */             this.adaptor.addChild(root_0, root_1);
/*      */ 
/* 4991 */             retval.tree = root_0;
/*      */           }
/* 4993 */           break;
/*      */         case 2:
/* 5005 */           if (this.state.backtracking == 0) {
/* 5006 */             retval.tree = root_0;
/* 5007 */             stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.tree : null);
/*      */ 
/* 5009 */             root_0 = (CommonTree)this.adaptor.nil();
/*      */ 
/* 5012 */             this.adaptor.addChild(root_0, stream_notSet.nextTree());
/*      */ 
/* 5016 */             retval.tree = root_0;
/*      */           }
/*      */ 
/*      */           break;
/*      */         }
/*      */ 
/* 5024 */         break;
/*      */       case 4:
/* 5028 */         RULE_REF110 = (Token)match(this.input, 51, FOLLOW_RULE_REF_in_atom1902); if (this.state.failed) { atom_return localatom_return3 = retval; return localatom_return3; }
/* 5029 */         if (this.state.backtracking == 0) stream_RULE_REF.add(RULE_REF110);
/*      */ 
/* 5032 */         int alt51 = 2;
/* 5033 */         switch (this.input.LA(1))
/*      */         {
/*      */         case 50:
/* 5036 */           alt51 = 1;
/*      */         }
/*      */ 
/* 5041 */         switch (alt51)
/*      */         {
/*      */         case 1:
/* 5045 */           ARG_ACTION111 = (Token)match(this.input, 50, FOLLOW_ARG_ACTION_in_atom1904); if (this.state.failed) { stream_retval = retval; return stream_retval; }
/* 5046 */           if (this.state.backtracking == 0) stream_ARG_ACTION.add(ARG_ACTION111);
/*      */ 
/*      */           break;
/*      */         }
/*      */ 
/* 5055 */         int alt53 = 2;
/* 5056 */         switch (this.input.LA(1))
/*      */         {
/*      */         case 37:
/*      */         case 38:
/* 5060 */           alt53 = 1;
/*      */ 
/* 5062 */           break;
/*      */         case 31:
/*      */         case 36:
/*      */         case 39:
/*      */         case 44:
/*      */         case 45:
/*      */         case 46:
/*      */         case 47:
/*      */         case 51:
/*      */         case 71:
/*      */         case 74:
/*      */         case 81:
/*      */         case 82:
/*      */         case 83:
/*      */         case 87:
/*      */         case 90:
/*      */         case 91:
/*      */         case 92:
/* 5081 */           alt53 = 2;
/*      */ 
/* 5083 */           break;
/*      */         case 32:
/*      */         case 33:
/*      */         case 34:
/*      */         case 35:
/*      */         case 40:
/*      */         case 41:
/*      */         case 42:
/*      */         case 43:
/*      */         case 48:
/*      */         case 49:
/*      */         case 50:
/*      */         case 52:
/*      */         case 53:
/*      */         case 54:
/*      */         case 55:
/*      */         case 56:
/*      */         case 57:
/*      */         case 58:
/*      */         case 59:
/*      */         case 60:
/*      */         case 61:
/*      */         case 62:
/*      */         case 63:
/*      */         case 64:
/*      */         case 65:
/*      */         case 66:
/*      */         case 67:
/*      */         case 68:
/*      */         case 69:
/*      */         case 70:
/*      */         case 72:
/*      */         case 73:
/*      */         case 75:
/*      */         case 76:
/*      */         case 77:
/*      */         case 78:
/*      */         case 79:
/*      */         case 80:
/*      */         case 84:
/*      */         case 85:
/*      */         case 86:
/*      */         case 88:
/*      */         case 89:
/*      */         default:
/* 5085 */           if (this.state.backtracking > 0) { this.state.failed = true; stream_op = retval; return stream_op; }
/* 5086 */           NoViableAltException nvae = new NoViableAltException("", 53, 0, this.input);
/*      */ 
/* 5089 */           throw nvae;
/*      */         }
/*      */ 
/* 5092 */         switch (alt53)
/*      */         {
/*      */         case 1:
/* 5097 */           int alt52 = 2;
/*      */           NoViableAltException nvae;
/* 5098 */           switch (this.input.LA(1))
/*      */           {
/*      */           case 37:
/* 5101 */             alt52 = 1;
/*      */ 
/* 5103 */             break;
/*      */           case 38:
/* 5106 */             alt52 = 2;
/*      */ 
/* 5108 */             break;
/*      */           default:
/* 5110 */             if (this.state.backtracking > 0) { this.state.failed = true; stream_retval = retval; return stream_retval; }
/* 5111 */             nvae = new NoViableAltException("", 52, 0, this.input);
/*      */ 
/* 5114 */             throw nvae;
/*      */           }
/*      */ 
/* 5117 */           switch (alt52)
/*      */           {
/*      */           case 1:
/* 5121 */             op = (Token)match(this.input, 37, FOLLOW_ROOT_in_atom1914); if (this.state.failed) { nvae = retval; return nvae; }
/* 5122 */             if (this.state.backtracking == 0) stream_ROOT.add(op); break;
/*      */           case 2:
/* 5130 */             op = (Token)match(this.input, 38, FOLLOW_BANG_in_atom1918); if (this.state.failed) { nvae = retval; return nvae; }
/* 5131 */             if (this.state.backtracking == 0) stream_BANG.add(op);
/*      */ 
/*      */             break;
/*      */           }
/*      */ 
/* 5148 */           if (this.state.backtracking == 0) {
/* 5149 */             retval.tree = root_0;
/* 5150 */             RewriteRuleTokenStream stream_op = new RewriteRuleTokenStream(this.adaptor, "token op", op);
/* 5151 */             RewriteRuleSubtreeStream stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.tree : null);
/*      */ 
/* 5153 */             root_0 = (CommonTree)this.adaptor.nil();
/*      */ 
/* 5158 */             CommonTree root_1 = (CommonTree)this.adaptor.nil();
/* 5159 */             root_1 = (CommonTree)this.adaptor.becomeRoot(stream_op.nextNode(), root_1);
/*      */ 
/* 5161 */             this.adaptor.addChild(root_1, stream_RULE_REF.nextNode());
/*      */ 
/* 5163 */             if (stream_ARG_ACTION.hasNext()) {
/* 5164 */               this.adaptor.addChild(root_1, stream_ARG_ACTION.nextNode());
/*      */             }
/*      */ 
/* 5167 */             stream_ARG_ACTION.reset();
/*      */ 
/* 5169 */             this.adaptor.addChild(root_0, root_1);
/*      */ 
/* 5174 */             retval.tree = root_0;
/*      */           }
/* 5176 */           break;
/*      */         case 2:
/* 5188 */           if (this.state.backtracking == 0) {
/* 5189 */             retval.tree = root_0;
/* 5190 */             RewriteRuleSubtreeStream stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.tree : null);
/*      */ 
/* 5192 */             root_0 = (CommonTree)this.adaptor.nil();
/*      */ 
/* 5197 */             CommonTree root_1 = (CommonTree)this.adaptor.nil();
/* 5198 */             root_1 = (CommonTree)this.adaptor.becomeRoot(stream_RULE_REF.nextNode(), root_1);
/*      */ 
/* 5201 */             if (stream_ARG_ACTION.hasNext()) {
/* 5202 */               this.adaptor.addChild(root_1, stream_ARG_ACTION.nextNode());
/*      */             }
/*      */ 
/* 5205 */             stream_ARG_ACTION.reset();
/*      */ 
/* 5207 */             this.adaptor.addChild(root_0, root_1);
/*      */ 
/* 5212 */             retval.tree = root_0;
/*      */           }
/*      */ 
/*      */           break;
/*      */         }
/*      */ 
/*      */         break;
/*      */       }
/*      */ 
/* 5223 */       retval.stop = this.input.LT(-1);
/*      */ 
/* 5225 */       if (this.state.backtracking == 0)
/*      */       {
/* 5227 */         retval.tree = ((CommonTree)this.adaptor.rulePostProcessing(root_0));
/* 5228 */         this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
/*      */       }
/*      */     } catch (RecognitionException re) {
/* 5231 */       re = 
/* 5238 */         re;
/*      */ 
/* 5232 */       reportError(re);
/* 5233 */       recover(this.input, re);
/* 5234 */       retval.tree = ((CommonTree)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), re));
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/* 5239 */     return retval;
/*      */   }
/*      */ 
/*      */   public final notSet_return notSet()
/*      */     throws RecognitionException
/*      */   {
/* 5251 */     notSet_return retval = new notSet_return();
/* 5252 */     retval.start = this.input.LT(1);
/*      */ 
/* 5254 */     CommonTree root_0 = null;
/*      */ 
/* 5256 */     Token char_literal112 = null;
/* 5257 */     notTerminal_return notTerminal113 = null;
/*      */ 
/* 5259 */     elementOptions_return elementOptions114 = null;
/*      */ 
/* 5261 */     block_return block115 = null;
/*      */ 
/* 5263 */     elementOptions_return elementOptions116 = null;
/*      */ 
/* 5266 */     CommonTree char_literal112_tree = null;
/* 5267 */     RewriteRuleTokenStream stream_87 = new RewriteRuleTokenStream(this.adaptor, "token 87");
/* 5268 */     RewriteRuleSubtreeStream stream_notTerminal = new RewriteRuleSubtreeStream(this.adaptor, "rule notTerminal");
/* 5269 */     RewriteRuleSubtreeStream stream_elementOptions = new RewriteRuleSubtreeStream(this.adaptor, "rule elementOptions");
/* 5270 */     RewriteRuleSubtreeStream stream_block = new RewriteRuleSubtreeStream(this.adaptor, "rule block");
/*      */     try
/*      */     {
/* 5275 */       char_literal112 = (Token)match(this.input, 87, FOLLOW_87_in_notSet1966); if (this.state.failed) { notSet_return localnotSet_return1 = retval; return localnotSet_return1; }
/* 5276 */       if (this.state.backtracking == 0) stream_87.add(char_literal112);
/*      */ 
/* 5279 */       int alt57 = 2;
/*      */       Object nvae;
/* 5280 */       switch (this.input.LA(1))
/*      */       {
/*      */       case 44:
/*      */       case 45:
/*      */       case 46:
/* 5285 */         alt57 = 1;
/*      */ 
/* 5287 */         break;
/*      */       case 81:
/* 5290 */         alt57 = 2;
/*      */ 
/* 5292 */         break;
/*      */       default:
/* 5294 */         if (this.state.backtracking > 0) { this.state.failed = true; notSet_return localnotSet_return2 = retval; return localnotSet_return2; }
/* 5295 */         nvae = new NoViableAltException("", 57, 0, this.input);
/*      */ 
/* 5298 */         throw ((Throwable)nvae);
/*      */       }
/*      */       int alt55;
/*      */       Object stream_retval;
/* 5301 */       switch (alt57)
/*      */       {
/*      */       case 1:
/* 5305 */         pushFollow(FOLLOW_notTerminal_in_notSet1972);
/* 5306 */         notTerminal113 = notTerminal();
/*      */ 
/* 5308 */         this.state._fsp -= 1;
/* 5309 */         if (this.state.failed) { nvae = retval; return nvae; }
/* 5310 */         if (this.state.backtracking == 0) stream_notTerminal.add(notTerminal113.getTree());
/*      */ 
/* 5312 */         alt55 = 2;
/* 5313 */         switch (this.input.LA(1))
/*      */         {
/*      */         case 88:
/* 5316 */           alt55 = 1;
/*      */         }
/*      */ 
/* 5321 */         switch (alt55)
/*      */         {
/*      */         case 1:
/* 5325 */           pushFollow(FOLLOW_elementOptions_in_notSet1974);
/* 5326 */           elementOptions114 = elementOptions();
/*      */ 
/* 5328 */           this.state._fsp -= 1;
/* 5329 */           if (this.state.failed) { notSet_return localnotSet_return4 = retval; return localnotSet_return4; }
/* 5330 */           if (this.state.backtracking == 0) stream_elementOptions.add(elementOptions114.getTree());
/*      */ 
/*      */           break;
/*      */         }
/*      */ 
/* 5346 */         if (this.state.backtracking == 0) {
/* 5347 */           retval.tree = root_0;
/* 5348 */           stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.tree : null);
/*      */ 
/* 5350 */           root_0 = (CommonTree)this.adaptor.nil();
/*      */ 
/* 5355 */           CommonTree root_1 = (CommonTree)this.adaptor.nil();
/* 5356 */           root_1 = (CommonTree)this.adaptor.becomeRoot(stream_87.nextNode(), root_1);
/*      */ 
/* 5358 */           this.adaptor.addChild(root_1, stream_notTerminal.nextTree());
/*      */ 
/* 5360 */           if (stream_elementOptions.hasNext()) {
/* 5361 */             this.adaptor.addChild(root_1, stream_elementOptions.nextTree());
/*      */           }
/*      */ 
/* 5364 */           stream_elementOptions.reset();
/*      */ 
/* 5366 */           this.adaptor.addChild(root_0, root_1);
/*      */ 
/* 5371 */           retval.tree = root_0;
/*      */         }
/* 5373 */         break;
/*      */       case 2:
/* 5377 */         pushFollow(FOLLOW_block_in_notSet1992);
/* 5378 */         block115 = block();
/*      */ 
/* 5380 */         this.state._fsp -= 1;
/* 5381 */         if (this.state.failed) { notSet_return localnotSet_return3 = retval; return localnotSet_return3; }
/* 5382 */         if (this.state.backtracking == 0) stream_block.add(block115.getTree());
/*      */ 
/* 5384 */         int alt56 = 2;
/* 5385 */         switch (this.input.LA(1))
/*      */         {
/*      */         case 88:
/* 5388 */           alt56 = 1;
/*      */         }
/*      */ 
/* 5393 */         switch (alt56)
/*      */         {
/*      */         case 1:
/* 5397 */           pushFollow(FOLLOW_elementOptions_in_notSet1994);
/* 5398 */           elementOptions116 = elementOptions();
/*      */ 
/* 5400 */           this.state._fsp -= 1;
/* 5401 */           if (this.state.failed) { stream_retval = retval; return stream_retval; }
/* 5402 */           if (this.state.backtracking == 0) stream_elementOptions.add(elementOptions116.getTree());
/*      */ 
/*      */           break;
/*      */         }
/*      */ 
/* 5418 */         if (this.state.backtracking == 0) {
/* 5419 */           retval.tree = root_0;
/* 5420 */           RewriteRuleSubtreeStream stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.tree : null);
/*      */ 
/* 5422 */           root_0 = (CommonTree)this.adaptor.nil();
/*      */ 
/* 5427 */           CommonTree root_1 = (CommonTree)this.adaptor.nil();
/* 5428 */           root_1 = (CommonTree)this.adaptor.becomeRoot(stream_87.nextNode(), root_1);
/*      */ 
/* 5430 */           this.adaptor.addChild(root_1, stream_block.nextTree());
/*      */ 
/* 5432 */           if (stream_elementOptions.hasNext()) {
/* 5433 */             this.adaptor.addChild(root_1, stream_elementOptions.nextTree());
/*      */           }
/*      */ 
/* 5436 */           stream_elementOptions.reset();
/*      */ 
/* 5438 */           this.adaptor.addChild(root_0, root_1);
/*      */ 
/* 5443 */           retval.tree = root_0;
/*      */         }
/*      */ 
/*      */         break;
/*      */       }
/*      */ 
/* 5452 */       retval.stop = this.input.LT(-1);
/*      */ 
/* 5454 */       if (this.state.backtracking == 0)
/*      */       {
/* 5456 */         retval.tree = ((CommonTree)this.adaptor.rulePostProcessing(root_0));
/* 5457 */         this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
/*      */       }
/*      */     } catch (RecognitionException re) {
/* 5460 */       re = 
/* 5467 */         re;
/*      */ 
/* 5461 */       reportError(re);
/* 5462 */       recover(this.input, re);
/* 5463 */       retval.tree = ((CommonTree)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), re));
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/* 5468 */     return retval;
/*      */   }
/*      */ 
/*      */   public final notTerminal_return notTerminal()
/*      */     throws RecognitionException
/*      */   {
/* 5480 */     notTerminal_return retval = new notTerminal_return();
/* 5481 */     retval.start = this.input.LT(1);
/*      */ 
/* 5483 */     CommonTree root_0 = null;
/*      */ 
/* 5485 */     Token set117 = null;
/*      */ 
/* 5487 */     CommonTree set117_tree = null;
/*      */     try
/*      */     {
/* 5493 */       root_0 = (CommonTree)this.adaptor.nil();
/*      */ 
/* 5495 */       set117 = this.input.LT(1);
/* 5496 */       if ((this.input.LA(1) >= 44) && (this.input.LA(1) <= 46)) {
/* 5497 */         this.input.consume();
/* 5498 */         if (this.state.backtracking == 0) this.adaptor.addChild(root_0, (CommonTree)this.adaptor.create(set117));
/* 5499 */         this.state.errorRecovery = false; this.state.failed = false;
/*      */       }
/*      */       else {
/* 5502 */         if (this.state.backtracking > 0) { this.state.failed = true; notTerminal_return localnotTerminal_return1 = retval; return localnotTerminal_return1; }
/* 5503 */         MismatchedSetException mse = new MismatchedSetException(null, this.input);
/* 5504 */         throw mse;
/*      */       }
/*      */ 
/* 5510 */       retval.stop = this.input.LT(-1);
/*      */ 
/* 5512 */       if (this.state.backtracking == 0)
/*      */       {
/* 5514 */         retval.tree = ((CommonTree)this.adaptor.rulePostProcessing(root_0));
/* 5515 */         this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
/*      */       }
/*      */     } catch (RecognitionException re) {
/* 5518 */       re = 
/* 5525 */         re;
/*      */ 
/* 5519 */       reportError(re);
/* 5520 */       recover(this.input, re);
/* 5521 */       retval.tree = ((CommonTree)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), re));
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/* 5526 */     return retval;
/*      */   }
/*      */ 
/*      */   public final elementOptions_return elementOptions()
/*      */     throws RecognitionException
/*      */   {
/* 5538 */     elementOptions_return retval = new elementOptions_return();
/* 5539 */     retval.start = this.input.LT(1);
/*      */ 
/* 5541 */     CommonTree root_0 = null;
/*      */ 
/* 5543 */     Token char_literal118 = null;
/* 5544 */     Token char_literal120 = null;
/* 5545 */     Token char_literal121 = null;
/* 5546 */     Token char_literal123 = null;
/* 5547 */     Token char_literal125 = null;
/* 5548 */     qid_return qid119 = null;
/*      */ 
/* 5550 */     option_return option122 = null;
/*      */ 
/* 5552 */     option_return option124 = null;
/*      */ 
/* 5555 */     CommonTree char_literal118_tree = null;
/* 5556 */     CommonTree char_literal120_tree = null;
/* 5557 */     CommonTree char_literal121_tree = null;
/* 5558 */     CommonTree char_literal123_tree = null;
/* 5559 */     CommonTree char_literal125_tree = null;
/* 5560 */     RewriteRuleTokenStream stream_71 = new RewriteRuleTokenStream(this.adaptor, "token 71");
/* 5561 */     RewriteRuleTokenStream stream_88 = new RewriteRuleTokenStream(this.adaptor, "token 88");
/* 5562 */     RewriteRuleTokenStream stream_89 = new RewriteRuleTokenStream(this.adaptor, "token 89");
/* 5563 */     RewriteRuleSubtreeStream stream_qid = new RewriteRuleSubtreeStream(this.adaptor, "rule qid");
/* 5564 */     RewriteRuleSubtreeStream stream_option = new RewriteRuleSubtreeStream(this.adaptor, "rule option");
/*      */     try
/*      */     {
/* 5567 */       int alt59 = 2;
/*      */       Object nvae;
/*      */       Object nvae;
/* 5568 */       switch (this.input.LA(1))
/*      */       {
/*      */       case 88:
/*      */         Object nvae;
/*      */         Object nvae;
/* 5571 */         switch (this.input.LA(2))
/*      */         {
/*      */         case 44:
/* 5574 */           switch (this.input.LA(3))
/*      */           {
/*      */           case 89:
/*      */           case 90:
/* 5578 */             alt59 = 1;
/*      */ 
/* 5580 */             break;
/*      */           case 41:
/* 5583 */             alt59 = 2;
/*      */ 
/* 5585 */             break;
/*      */           default:
/* 5587 */             if (this.state.backtracking > 0) { this.state.failed = true; elementOptions_return localelementOptions_return1 = retval; return localelementOptions_return1; }
/* 5588 */             nvae = new NoViableAltException("", 59, 2, this.input);
/*      */ 
/* 5591 */             throw ((Throwable)nvae);
/*      */           }
/*      */ 
/* 5595 */           break;
/*      */         case 51:
/* 5598 */           switch (this.input.LA(3))
/*      */           {
/*      */           case 89:
/*      */           case 90:
/* 5602 */             alt59 = 1;
/*      */ 
/* 5604 */             break;
/*      */           case 41:
/* 5607 */             alt59 = 2;
/*      */ 
/* 5609 */             break;
/*      */           default:
/* 5611 */             if (this.state.backtracking > 0) { this.state.failed = true; nvae = retval; return nvae; }
/* 5612 */             nvae = new NoViableAltException("", 59, 3, this.input);
/*      */ 
/* 5615 */             throw ((Throwable)nvae);
/*      */           }
/*      */ 
/* 5619 */           break;
/*      */         default:
/* 5621 */           if (this.state.backtracking > 0) { this.state.failed = true; nvae = retval; return nvae; }
/* 5622 */           nvae = new NoViableAltException("", 59, 1, this.input);
/*      */ 
/* 5625 */           throw ((Throwable)nvae);
/*      */         }
/*      */ 
/* 5629 */         break;
/*      */       default:
/* 5631 */         if (this.state.backtracking > 0) { this.state.failed = true; nvae = retval; return nvae; }
/* 5632 */         nvae = new NoViableAltException("", 59, 0, this.input);
/*      */ 
/* 5635 */         throw ((Throwable)nvae);
/*      */       }
/*      */       Object stream_retval;
/*      */       CommonTree root_1;
/* 5638 */       switch (alt59)
/*      */       {
/*      */       case 1:
/* 5642 */         char_literal118 = (Token)match(this.input, 88, FOLLOW_88_in_elementOptions2046); if (this.state.failed) { nvae = retval; return nvae; }
/* 5643 */         if (this.state.backtracking == 0) stream_88.add(char_literal118);
/*      */ 
/* 5645 */         pushFollow(FOLLOW_qid_in_elementOptions2048);
/* 5646 */         qid119 = qid();
/*      */ 
/* 5648 */         this.state._fsp -= 1;
/* 5649 */         if (this.state.failed) { nvae = retval; return nvae; }
/* 5650 */         if (this.state.backtracking == 0) stream_qid.add(qid119.getTree());
/* 5651 */         char_literal120 = (Token)match(this.input, 89, FOLLOW_89_in_elementOptions2050); if (this.state.failed) { nvae = retval; return nvae; }
/* 5652 */         if (this.state.backtracking == 0) stream_89.add(char_literal120);
/*      */ 
/* 5663 */         if (this.state.backtracking == 0) {
/* 5664 */           retval.tree = root_0;
/* 5665 */           stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.tree : null);
/*      */ 
/* 5667 */           root_0 = (CommonTree)this.adaptor.nil();
/*      */ 
/* 5672 */           root_1 = (CommonTree)this.adaptor.nil();
/* 5673 */           root_1 = (CommonTree)this.adaptor.becomeRoot((CommonTree)this.adaptor.create(48, "OPTIONS"), root_1);
/*      */ 
/* 5675 */           this.adaptor.addChild(root_1, stream_qid.nextTree());
/*      */ 
/* 5677 */           this.adaptor.addChild(root_0, root_1);
/*      */ 
/* 5682 */           retval.tree = root_0; } break;
/*      */       case 2:
/* 5688 */         char_literal121 = (Token)match(this.input, 88, FOLLOW_88_in_elementOptions2068); if (this.state.failed) { stream_retval = retval; return stream_retval; }
/* 5689 */         if (this.state.backtracking == 0) stream_88.add(char_literal121);
/*      */ 
/* 5691 */         pushFollow(FOLLOW_option_in_elementOptions2070);
/* 5692 */         option122 = option();
/*      */ 
/* 5694 */         this.state._fsp -= 1;
/* 5695 */         if (this.state.failed) { stream_retval = retval; return stream_retval; }
/* 5696 */         if (this.state.backtracking == 0) stream_option.add(option122.getTree());
/*      */         int alt58;
/*      */         while (true)
/*      */         {
/* 5700 */           alt58 = 2;
/* 5701 */           switch (this.input.LA(1))
/*      */           {
/*      */           case 71:
/* 5704 */             alt58 = 1;
/*      */           }
/*      */ 
/* 5710 */           switch (alt58)
/*      */           {
/*      */           case 1:
/* 5714 */             char_literal123 = (Token)match(this.input, 71, FOLLOW_71_in_elementOptions2073); if (this.state.failed) { root_1 = retval; return root_1; }
/* 5715 */             if (this.state.backtracking == 0) stream_71.add(char_literal123);
/*      */ 
/* 5717 */             pushFollow(FOLLOW_option_in_elementOptions2075);
/* 5718 */             option124 = option();
/*      */ 
/* 5720 */             this.state._fsp -= 1;
/* 5721 */             if (this.state.failed) { root_1 = retval; return root_1; }
/* 5722 */             if (this.state.backtracking == 0) stream_option.add(option124.getTree()); break;
/*      */           default:
/* 5728 */             break label1126;
/*      */           }
/*      */         }
/*      */ 
/* 5732 */         label1126: char_literal125 = (Token)match(this.input, 89, FOLLOW_89_in_elementOptions2079); if (this.state.failed) { elementOptions_return localelementOptions_return2 = retval; return localelementOptions_return2; }
/* 5733 */         if (this.state.backtracking == 0) stream_89.add(char_literal125);
/*      */ 
/* 5744 */         if (this.state.backtracking == 0) {
/* 5745 */           retval.tree = root_0;
/* 5746 */           RewriteRuleSubtreeStream stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.tree : null);
/*      */ 
/* 5748 */           root_0 = (CommonTree)this.adaptor.nil();
/*      */ 
/* 5753 */           CommonTree root_1 = (CommonTree)this.adaptor.nil();
/* 5754 */           root_1 = (CommonTree)this.adaptor.becomeRoot((CommonTree)this.adaptor.create(48, "OPTIONS"), root_1);
/*      */ 
/* 5756 */           if (!stream_option.hasNext()) {
/* 5757 */             throw new RewriteEarlyExitException();
/*      */           }
/* 5759 */           while (stream_option.hasNext()) {
/* 5760 */             this.adaptor.addChild(root_1, stream_option.nextTree());
/*      */           }
/*      */ 
/* 5763 */           stream_option.reset();
/*      */ 
/* 5765 */           this.adaptor.addChild(root_0, root_1);
/*      */ 
/* 5770 */           retval.tree = root_0;
/*      */         }
/*      */         break;
/*      */       }
/*      */ 
/* 5775 */       retval.stop = this.input.LT(-1);
/*      */ 
/* 5777 */       if (this.state.backtracking == 0)
/*      */       {
/* 5779 */         retval.tree = ((CommonTree)this.adaptor.rulePostProcessing(root_0));
/* 5780 */         this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
/*      */       }
/*      */     } catch (RecognitionException re) {
/* 5783 */       re = 
/* 5790 */         re;
/*      */ 
/* 5784 */       reportError(re);
/* 5785 */       recover(this.input, re);
/* 5786 */       retval.tree = ((CommonTree)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), re));
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/* 5791 */     return retval;
/*      */   }
/*      */ 
/*      */   public final elementOption_return elementOption()
/*      */     throws RecognitionException
/*      */   {
/* 5803 */     elementOption_return retval = new elementOption_return();
/* 5804 */     retval.start = this.input.LT(1);
/*      */ 
/* 5806 */     CommonTree root_0 = null;
/*      */ 
/* 5808 */     Token char_literal127 = null;
/* 5809 */     id_return id126 = null;
/*      */ 
/* 5811 */     optionValue_return optionValue128 = null;
/*      */ 
/* 5814 */     CommonTree char_literal127_tree = null;
/* 5815 */     RewriteRuleTokenStream stream_LABEL_ASSIGN = new RewriteRuleTokenStream(this.adaptor, "token LABEL_ASSIGN");
/* 5816 */     RewriteRuleSubtreeStream stream_id = new RewriteRuleSubtreeStream(this.adaptor, "rule id");
/* 5817 */     RewriteRuleSubtreeStream stream_optionValue = new RewriteRuleSubtreeStream(this.adaptor, "rule optionValue");
/*      */     try
/*      */     {
/* 5822 */       pushFollow(FOLLOW_id_in_elementOption2099);
/* 5823 */       id126 = id();
/*      */ 
/* 5825 */       this.state._fsp -= 1;
/*      */       elementOption_return localelementOption_return1;
/* 5826 */       if (this.state.failed) { localelementOption_return1 = retval; return localelementOption_return1; }
/* 5827 */       if (this.state.backtracking == 0) stream_id.add(id126.getTree());
/* 5828 */       char_literal127 = (Token)match(this.input, 41, FOLLOW_LABEL_ASSIGN_in_elementOption2101); if (this.state.failed) { localelementOption_return1 = retval; return localelementOption_return1; }
/* 5829 */       if (this.state.backtracking == 0) stream_LABEL_ASSIGN.add(char_literal127);
/*      */ 
/* 5831 */       pushFollow(FOLLOW_optionValue_in_elementOption2103);
/* 5832 */       optionValue128 = optionValue();
/*      */ 
/* 5834 */       this.state._fsp -= 1;
/* 5835 */       if (this.state.failed) { localelementOption_return1 = retval; return localelementOption_return1; }
/* 5836 */       if (this.state.backtracking == 0) stream_optionValue.add(optionValue128.getTree());
/*      */ 
/* 5846 */       if (this.state.backtracking == 0) {
/* 5847 */         retval.tree = root_0;
/* 5848 */         RewriteRuleSubtreeStream stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.tree : null);
/*      */ 
/* 5850 */         root_0 = (CommonTree)this.adaptor.nil();
/*      */ 
/* 5855 */         CommonTree root_1 = (CommonTree)this.adaptor.nil();
/* 5856 */         root_1 = (CommonTree)this.adaptor.becomeRoot(stream_LABEL_ASSIGN.nextNode(), root_1);
/*      */ 
/* 5858 */         this.adaptor.addChild(root_1, stream_id.nextTree());
/* 5859 */         this.adaptor.addChild(root_1, stream_optionValue.nextTree());
/*      */ 
/* 5861 */         this.adaptor.addChild(root_0, root_1);
/*      */ 
/* 5866 */         retval.tree = root_0;
/*      */       }
/*      */ 
/* 5869 */       retval.stop = this.input.LT(-1);
/*      */ 
/* 5871 */       if (this.state.backtracking == 0)
/*      */       {
/* 5873 */         retval.tree = ((CommonTree)this.adaptor.rulePostProcessing(root_0));
/* 5874 */         this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
/*      */       }
/*      */     } catch (RecognitionException re) {
/* 5877 */       re = 
/* 5884 */         re;
/*      */ 
/* 5878 */       reportError(re);
/* 5879 */       recover(this.input, re);
/* 5880 */       retval.tree = ((CommonTree)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), re));
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/* 5885 */     return retval;
/*      */   }
/*      */ 
/*      */   public final treeSpec_return treeSpec()
/*      */     throws RecognitionException
/*      */   {
/* 5897 */     treeSpec_return retval = new treeSpec_return();
/* 5898 */     retval.start = this.input.LT(1);
/*      */ 
/* 5900 */     CommonTree root_0 = null;
/*      */ 
/* 5902 */     Token string_literal129 = null;
/* 5903 */     Token char_literal132 = null;
/* 5904 */     element_return element130 = null;
/*      */ 
/* 5906 */     element_return element131 = null;
/*      */ 
/* 5909 */     CommonTree string_literal129_tree = null;
/* 5910 */     CommonTree char_literal132_tree = null;
/* 5911 */     RewriteRuleTokenStream stream_83 = new RewriteRuleTokenStream(this.adaptor, "token 83");
/* 5912 */     RewriteRuleTokenStream stream_TREE_BEGIN = new RewriteRuleTokenStream(this.adaptor, "token TREE_BEGIN");
/* 5913 */     RewriteRuleSubtreeStream stream_element = new RewriteRuleSubtreeStream(this.adaptor, "rule element");
/*      */     try
/*      */     {
/* 5918 */       string_literal129 = (Token)match(this.input, 36, FOLLOW_TREE_BEGIN_in_treeSpec2125);
/*      */       treeSpec_return localtreeSpec_return1;
/* 5918 */       if (this.state.failed) { localtreeSpec_return1 = retval; return localtreeSpec_return1; }
/* 5919 */       if (this.state.backtracking == 0) stream_TREE_BEGIN.add(string_literal129);
/*      */ 
/* 5921 */       pushFollow(FOLLOW_element_in_treeSpec2127);
/* 5922 */       element130 = element();
/*      */ 
/* 5924 */       this.state._fsp -= 1;
/* 5925 */       if (this.state.failed) { localtreeSpec_return1 = retval; return localtreeSpec_return1; }
/* 5926 */       if (this.state.backtracking == 0) stream_element.add(element130.getTree()); 
/*      */ int cnt60 = 0;
/*      */       int alt60;
/*      */       while (true) {
/* 5931 */         alt60 = 2;
/* 5932 */         switch (this.input.LA(1))
/*      */         {
/*      */         case 31:
/*      */         case 36:
/*      */         case 44:
/*      */         case 45:
/*      */         case 46:
/*      */         case 47:
/*      */         case 51:
/*      */         case 81:
/*      */         case 87:
/*      */         case 90:
/* 5944 */           alt60 = 1;
/*      */         }
/*      */         treeSpec_return localtreeSpec_return2;
/* 5950 */         switch (alt60)
/*      */         {
/*      */         case 1:
/* 5954 */           pushFollow(FOLLOW_element_in_treeSpec2131);
/* 5955 */           element131 = element();
/*      */ 
/* 5957 */           this.state._fsp -= 1;
/* 5958 */           if (this.state.failed) { localtreeSpec_return2 = retval; return localtreeSpec_return2; }
/* 5959 */           if (this.state.backtracking == 0) stream_element.add(element131.getTree()); break;
/*      */         default:
/* 5965 */           if (cnt60 >= 1) break label454;
/* 5966 */           if (this.state.backtracking > 0) { this.state.failed = true; localtreeSpec_return2 = retval; return localtreeSpec_return2; }
/* 5967 */           EarlyExitException eee = new EarlyExitException(60, this.input);
/*      */ 
/* 5969 */           throw eee;
/*      */         }
/* 5971 */         cnt60++;
/*      */       }
/*      */ 
/* 5974 */       label454: char_literal132 = (Token)match(this.input, 83, FOLLOW_83_in_treeSpec2136); if (this.state.failed) { alt60 = retval; return alt60; }
/* 5975 */       if (this.state.backtracking == 0) stream_83.add(char_literal132);
/*      */ 
/* 5986 */       if (this.state.backtracking == 0) {
/* 5987 */         retval.tree = root_0;
/* 5988 */         RewriteRuleSubtreeStream stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.tree : null);
/*      */ 
/* 5990 */         root_0 = (CommonTree)this.adaptor.nil();
/*      */ 
/* 5995 */         CommonTree root_1 = (CommonTree)this.adaptor.nil();
/* 5996 */         root_1 = (CommonTree)this.adaptor.becomeRoot((CommonTree)this.adaptor.create(36, "TREE_BEGIN"), root_1);
/*      */ 
/* 5998 */         if (!stream_element.hasNext()) {
/* 5999 */           throw new RewriteEarlyExitException();
/*      */         }
/* 6001 */         while (stream_element.hasNext()) {
/* 6002 */           this.adaptor.addChild(root_1, stream_element.nextTree());
/*      */         }
/*      */ 
/* 6005 */         stream_element.reset();
/*      */ 
/* 6007 */         this.adaptor.addChild(root_0, root_1);
/*      */ 
/* 6012 */         retval.tree = root_0;
/*      */       }
/*      */ 
/* 6015 */       retval.stop = this.input.LT(-1);
/*      */ 
/* 6017 */       if (this.state.backtracking == 0)
/*      */       {
/* 6019 */         retval.tree = ((CommonTree)this.adaptor.rulePostProcessing(root_0));
/* 6020 */         this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
/*      */       }
/*      */     } catch (RecognitionException re) {
/* 6023 */       re = 
/* 6030 */         re;
/*      */ 
/* 6024 */       reportError(re);
/* 6025 */       recover(this.input, re);
/* 6026 */       retval.tree = ((CommonTree)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), re));
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/* 6031 */     return retval;
/*      */   }
/*      */ 
/*      */   public final range_return range()
/*      */     throws RecognitionException
/*      */   {
/* 6043 */     range_return retval = new range_return();
/* 6044 */     retval.start = this.input.LT(1);
/*      */ 
/* 6046 */     CommonTree root_0 = null;
/*      */ 
/* 6048 */     Token c1 = null;
/* 6049 */     Token c2 = null;
/* 6050 */     Token RANGE133 = null;
/* 6051 */     elementOptions_return elementOptions134 = null;
/*      */ 
/* 6054 */     CommonTree c1_tree = null;
/* 6055 */     CommonTree c2_tree = null;
/* 6056 */     CommonTree RANGE133_tree = null;
/* 6057 */     RewriteRuleTokenStream stream_RANGE = new RewriteRuleTokenStream(this.adaptor, "token RANGE");
/* 6058 */     RewriteRuleTokenStream stream_CHAR_LITERAL = new RewriteRuleTokenStream(this.adaptor, "token CHAR_LITERAL");
/* 6059 */     RewriteRuleSubtreeStream stream_elementOptions = new RewriteRuleSubtreeStream(this.adaptor, "rule elementOptions");
/*      */     try
/*      */     {
/* 6064 */       c1 = (Token)match(this.input, 46, FOLLOW_CHAR_LITERAL_in_range2159);
/*      */       range_return localrange_return1;
/* 6064 */       if (this.state.failed) { localrange_return1 = retval; return localrange_return1; }
/* 6065 */       if (this.state.backtracking == 0) stream_CHAR_LITERAL.add(c1);
/*      */ 
/* 6067 */       RANGE133 = (Token)match(this.input, 13, FOLLOW_RANGE_in_range2161); if (this.state.failed) { localrange_return1 = retval; return localrange_return1; }
/* 6068 */       if (this.state.backtracking == 0) stream_RANGE.add(RANGE133);
/*      */ 
/* 6070 */       c2 = (Token)match(this.input, 46, FOLLOW_CHAR_LITERAL_in_range2165); if (this.state.failed) { localrange_return1 = retval; return localrange_return1; }
/* 6071 */       if (this.state.backtracking == 0) stream_CHAR_LITERAL.add(c2);
/*      */ 
/* 6074 */       int alt61 = 2;
/* 6075 */       switch (this.input.LA(1))
/*      */       {
/*      */       case 88:
/* 6078 */         alt61 = 1;
/*      */       }
/*      */ 
/* 6083 */       switch (alt61)
/*      */       {
/*      */       case 1:
/* 6087 */         pushFollow(FOLLOW_elementOptions_in_range2167);
/* 6088 */         elementOptions134 = elementOptions();
/*      */ 
/* 6090 */         this.state._fsp -= 1;
/* 6091 */         if (this.state.failed) { range_return localrange_return2 = retval; return localrange_return2; }
/* 6092 */         if (this.state.backtracking == 0) stream_elementOptions.add(elementOptions134.getTree());
/*      */ 
/*      */         break;
/*      */       }
/*      */ 
/* 6108 */       if (this.state.backtracking == 0) {
/* 6109 */         retval.tree = root_0;
/* 6110 */         RewriteRuleTokenStream stream_c1 = new RewriteRuleTokenStream(this.adaptor, "token c1", c1);
/* 6111 */         RewriteRuleTokenStream stream_c2 = new RewriteRuleTokenStream(this.adaptor, "token c2", c2);
/* 6112 */         RewriteRuleSubtreeStream stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.tree : null);
/*      */ 
/* 6114 */         root_0 = (CommonTree)this.adaptor.nil();
/*      */ 
/* 6119 */         CommonTree root_1 = (CommonTree)this.adaptor.nil();
/* 6120 */         root_1 = (CommonTree)this.adaptor.becomeRoot((CommonTree)this.adaptor.create(14, c1, ".."), root_1);
/*      */ 
/* 6122 */         this.adaptor.addChild(root_1, stream_c1.nextNode());
/* 6123 */         this.adaptor.addChild(root_1, stream_c2.nextNode());
/*      */ 
/* 6125 */         if (stream_elementOptions.hasNext()) {
/* 6126 */           this.adaptor.addChild(root_1, stream_elementOptions.nextTree());
/*      */         }
/*      */ 
/* 6129 */         stream_elementOptions.reset();
/*      */ 
/* 6131 */         this.adaptor.addChild(root_0, root_1);
/*      */ 
/* 6136 */         retval.tree = root_0;
/*      */       }
/*      */ 
/* 6139 */       retval.stop = this.input.LT(-1);
/*      */ 
/* 6141 */       if (this.state.backtracking == 0)
/*      */       {
/* 6143 */         retval.tree = ((CommonTree)this.adaptor.rulePostProcessing(root_0));
/* 6144 */         this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
/*      */       }
/*      */     } catch (RecognitionException re) {
/* 6147 */       re = 
/* 6154 */         re;
/*      */ 
/* 6148 */       reportError(re);
/* 6149 */       recover(this.input, re);
/* 6150 */       retval.tree = ((CommonTree)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), re));
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/* 6155 */     return retval;
/*      */   }
/*      */ 
/*      */   public final terminal_return terminal()
/*      */     throws RecognitionException
/*      */   {
/* 6167 */     terminal_return retval = new terminal_return();
/* 6168 */     retval.start = this.input.LT(1);
/*      */ 
/* 6170 */     CommonTree root_0 = null;
/*      */ 
/* 6172 */     Token CHAR_LITERAL135 = null;
/* 6173 */     Token TOKEN_REF137 = null;
/* 6174 */     Token ARG_ACTION138 = null;
/* 6175 */     Token STRING_LITERAL140 = null;
/* 6176 */     Token char_literal142 = null;
/* 6177 */     Token char_literal144 = null;
/* 6178 */     Token char_literal145 = null;
/* 6179 */     elementOptions_return elementOptions136 = null;
/*      */ 
/* 6181 */     elementOptions_return elementOptions139 = null;
/*      */ 
/* 6183 */     elementOptions_return elementOptions141 = null;
/*      */ 
/* 6185 */     elementOptions_return elementOptions143 = null;
/*      */ 
/* 6188 */     CommonTree CHAR_LITERAL135_tree = null;
/* 6189 */     CommonTree TOKEN_REF137_tree = null;
/* 6190 */     CommonTree ARG_ACTION138_tree = null;
/* 6191 */     CommonTree STRING_LITERAL140_tree = null;
/* 6192 */     CommonTree char_literal142_tree = null;
/* 6193 */     CommonTree char_literal144_tree = null;
/* 6194 */     CommonTree char_literal145_tree = null;
/* 6195 */     RewriteRuleTokenStream stream_STRING_LITERAL = new RewriteRuleTokenStream(this.adaptor, "token STRING_LITERAL");
/* 6196 */     RewriteRuleTokenStream stream_BANG = new RewriteRuleTokenStream(this.adaptor, "token BANG");
/* 6197 */     RewriteRuleTokenStream stream_CHAR_LITERAL = new RewriteRuleTokenStream(this.adaptor, "token CHAR_LITERAL");
/* 6198 */     RewriteRuleTokenStream stream_90 = new RewriteRuleTokenStream(this.adaptor, "token 90");
/* 6199 */     RewriteRuleTokenStream stream_ROOT = new RewriteRuleTokenStream(this.adaptor, "token ROOT");
/* 6200 */     RewriteRuleTokenStream stream_TOKEN_REF = new RewriteRuleTokenStream(this.adaptor, "token TOKEN_REF");
/* 6201 */     RewriteRuleTokenStream stream_ARG_ACTION = new RewriteRuleTokenStream(this.adaptor, "token ARG_ACTION");
/* 6202 */     RewriteRuleSubtreeStream stream_elementOptions = new RewriteRuleSubtreeStream(this.adaptor, "rule elementOptions");
/*      */     try
/*      */     {
/* 6208 */       int alt67 = 4;
/*      */       Object nvae;
/* 6209 */       switch (this.input.LA(1))
/*      */       {
/*      */       case 46:
/* 6212 */         alt67 = 1;
/*      */ 
/* 6214 */         break;
/*      */       case 44:
/* 6217 */         alt67 = 2;
/*      */ 
/* 6219 */         break;
/*      */       case 45:
/* 6222 */         alt67 = 3;
/*      */ 
/* 6224 */         break;
/*      */       case 90:
/* 6227 */         alt67 = 4;
/*      */ 
/* 6229 */         break;
/*      */       default:
/* 6231 */         if (this.state.backtracking > 0) { this.state.failed = true; terminal_return localterminal_return1 = retval; return localterminal_return1; }
/* 6232 */         nvae = new NoViableAltException("", 67, 0, this.input);
/*      */ 
/* 6235 */         throw ((Throwable)nvae);
/*      */       }
/*      */       int alt62;
/*      */       Object stream_retval;
/*      */       CommonTree root_1;
/*      */       int alt63;
/*      */       int alt64;
/*      */       int alt65;
/*      */       Object stream_retval;
/*      */       Object stream_retval;
/* 6238 */       switch (alt67)
/*      */       {
/*      */       case 1:
/* 6242 */         CHAR_LITERAL135 = (Token)match(this.input, 46, FOLLOW_CHAR_LITERAL_in_terminal2204); if (this.state.failed) { nvae = retval; return nvae; }
/* 6243 */         if (this.state.backtracking == 0) stream_CHAR_LITERAL.add(CHAR_LITERAL135);
/*      */ 
/* 6246 */         alt62 = 2;
/* 6247 */         switch (this.input.LA(1))
/*      */         {
/*      */         case 88:
/* 6250 */           alt62 = 1;
/*      */         }
/*      */ 
/* 6255 */         switch (alt62)
/*      */         {
/*      */         case 1:
/* 6259 */           pushFollow(FOLLOW_elementOptions_in_terminal2206);
/* 6260 */           elementOptions136 = elementOptions();
/*      */ 
/* 6262 */           this.state._fsp -= 1;
/* 6263 */           if (this.state.failed) { terminal_return localterminal_return5 = retval; return localterminal_return5; }
/* 6264 */           if (this.state.backtracking == 0) stream_elementOptions.add(elementOptions136.getTree());
/*      */ 
/*      */           break;
/*      */         }
/*      */ 
/* 6280 */         if (this.state.backtracking == 0) {
/* 6281 */           retval.tree = root_0;
/* 6282 */           stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.tree : null);
/*      */ 
/* 6284 */           root_0 = (CommonTree)this.adaptor.nil();
/*      */ 
/* 6289 */           root_1 = (CommonTree)this.adaptor.nil();
/* 6290 */           root_1 = (CommonTree)this.adaptor.becomeRoot(stream_CHAR_LITERAL.nextNode(), root_1);
/*      */ 
/* 6293 */           if (stream_elementOptions.hasNext()) {
/* 6294 */             this.adaptor.addChild(root_1, stream_elementOptions.nextTree());
/*      */           }
/*      */ 
/* 6297 */           stream_elementOptions.reset();
/*      */ 
/* 6299 */           this.adaptor.addChild(root_0, root_1);
/*      */ 
/* 6304 */           retval.tree = root_0;
/*      */         }
/* 6306 */         break;
/*      */       case 2:
/* 6310 */         TOKEN_REF137 = (Token)match(this.input, 44, FOLLOW_TOKEN_REF_in_terminal2237); if (this.state.failed) { terminal_return localterminal_return2 = retval; return localterminal_return2; }
/* 6311 */         if (this.state.backtracking == 0) stream_TOKEN_REF.add(TOKEN_REF137);
/*      */ 
/* 6314 */         alt63 = 2;
/* 6315 */         switch (this.input.LA(1))
/*      */         {
/*      */         case 50:
/* 6318 */           alt63 = 1;
/*      */         }
/*      */ 
/* 6323 */         switch (alt63)
/*      */         {
/*      */         case 1:
/* 6327 */           ARG_ACTION138 = (Token)match(this.input, 50, FOLLOW_ARG_ACTION_in_terminal2239); if (this.state.failed) { stream_retval = retval; return stream_retval; }
/* 6328 */           if (this.state.backtracking == 0) stream_ARG_ACTION.add(ARG_ACTION138);
/*      */ 
/*      */           break;
/*      */         }
/*      */ 
/* 6337 */         alt64 = 2;
/* 6338 */         switch (this.input.LA(1))
/*      */         {
/*      */         case 88:
/* 6341 */           alt64 = 1;
/*      */         }
/*      */ 
/* 6346 */         switch (alt64)
/*      */         {
/*      */         case 1:
/* 6350 */           pushFollow(FOLLOW_elementOptions_in_terminal2242);
/* 6351 */           elementOptions139 = elementOptions();
/*      */ 
/* 6353 */           this.state._fsp -= 1;
/* 6354 */           if (this.state.failed) { root_1 = retval; return root_1; }
/* 6355 */           if (this.state.backtracking == 0) stream_elementOptions.add(elementOptions139.getTree());
/*      */ 
/*      */           break;
/*      */         }
/*      */ 
/* 6371 */         if (this.state.backtracking == 0) {
/* 6372 */           retval.tree = root_0;
/* 6373 */           RewriteRuleSubtreeStream stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.tree : null);
/*      */ 
/* 6375 */           root_0 = (CommonTree)this.adaptor.nil();
/*      */ 
/* 6380 */           CommonTree root_1 = (CommonTree)this.adaptor.nil();
/* 6381 */           root_1 = (CommonTree)this.adaptor.becomeRoot(stream_TOKEN_REF.nextNode(), root_1);
/*      */ 
/* 6384 */           if (stream_ARG_ACTION.hasNext()) {
/* 6385 */             this.adaptor.addChild(root_1, stream_ARG_ACTION.nextNode());
/*      */           }
/*      */ 
/* 6388 */           stream_ARG_ACTION.reset();
/*      */ 
/* 6390 */           if (stream_elementOptions.hasNext()) {
/* 6391 */             this.adaptor.addChild(root_1, stream_elementOptions.nextTree());
/*      */           }
/*      */ 
/* 6394 */           stream_elementOptions.reset();
/*      */ 
/* 6396 */           this.adaptor.addChild(root_0, root_1);
/*      */ 
/* 6401 */           retval.tree = root_0;
/*      */         }
/* 6403 */         break;
/*      */       case 3:
/* 6407 */         STRING_LITERAL140 = (Token)match(this.input, 45, FOLLOW_STRING_LITERAL_in_terminal2263); if (this.state.failed) { terminal_return localterminal_return3 = retval; return localterminal_return3; }
/* 6408 */         if (this.state.backtracking == 0) stream_STRING_LITERAL.add(STRING_LITERAL140);
/*      */ 
/* 6411 */         alt65 = 2;
/* 6412 */         switch (this.input.LA(1))
/*      */         {
/*      */         case 88:
/* 6415 */           alt65 = 1;
/*      */         }
/*      */ 
/* 6420 */         switch (alt65)
/*      */         {
/*      */         case 1:
/* 6424 */           pushFollow(FOLLOW_elementOptions_in_terminal2265);
/* 6425 */           elementOptions141 = elementOptions();
/*      */ 
/* 6427 */           this.state._fsp -= 1;
/* 6428 */           if (this.state.failed) { terminal_return localterminal_return6 = retval; return localterminal_return6; }
/* 6429 */           if (this.state.backtracking == 0) stream_elementOptions.add(elementOptions141.getTree());
/*      */ 
/*      */           break;
/*      */         }
/*      */ 
/* 6445 */         if (this.state.backtracking == 0) {
/* 6446 */           retval.tree = root_0;
/* 6447 */           stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.tree : null);
/*      */ 
/* 6449 */           root_0 = (CommonTree)this.adaptor.nil();
/*      */ 
/* 6454 */           CommonTree root_1 = (CommonTree)this.adaptor.nil();
/* 6455 */           root_1 = (CommonTree)this.adaptor.becomeRoot(stream_STRING_LITERAL.nextNode(), root_1);
/*      */ 
/* 6458 */           if (stream_elementOptions.hasNext()) {
/* 6459 */             this.adaptor.addChild(root_1, stream_elementOptions.nextTree());
/*      */           }
/*      */ 
/* 6462 */           stream_elementOptions.reset();
/*      */ 
/* 6464 */           this.adaptor.addChild(root_0, root_1);
/*      */ 
/* 6469 */           retval.tree = root_0;
/*      */         }
/* 6471 */         break;
/*      */       case 4:
/* 6475 */         char_literal142 = (Token)match(this.input, 90, FOLLOW_90_in_terminal2286); if (this.state.failed) { terminal_return localterminal_return4 = retval; return localterminal_return4; }
/* 6476 */         if (this.state.backtracking == 0) stream_90.add(char_literal142);
/*      */ 
/* 6479 */         int alt66 = 2;
/* 6480 */         switch (this.input.LA(1))
/*      */         {
/*      */         case 88:
/* 6483 */           alt66 = 1;
/*      */         }
/*      */ 
/* 6488 */         switch (alt66)
/*      */         {
/*      */         case 1:
/* 6492 */           pushFollow(FOLLOW_elementOptions_in_terminal2288);
/* 6493 */           elementOptions143 = elementOptions();
/*      */ 
/* 6495 */           this.state._fsp -= 1;
/* 6496 */           if (this.state.failed) { stream_retval = retval; return stream_retval; }
/* 6497 */           if (this.state.backtracking == 0) stream_elementOptions.add(elementOptions143.getTree());
/*      */ 
/*      */           break;
/*      */         }
/*      */ 
/* 6513 */         if (this.state.backtracking == 0) {
/* 6514 */           retval.tree = root_0;
/* 6515 */           stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.tree : null);
/*      */ 
/* 6517 */           root_0 = (CommonTree)this.adaptor.nil();
/*      */ 
/* 6522 */           CommonTree root_1 = (CommonTree)this.adaptor.nil();
/* 6523 */           root_1 = (CommonTree)this.adaptor.becomeRoot(stream_90.nextNode(), root_1);
/*      */ 
/* 6526 */           if (stream_elementOptions.hasNext()) {
/* 6527 */             this.adaptor.addChild(root_1, stream_elementOptions.nextTree());
/*      */           }
/*      */ 
/* 6530 */           stream_elementOptions.reset();
/*      */ 
/* 6532 */           this.adaptor.addChild(root_0, root_1);
/*      */ 
/* 6537 */           retval.tree = root_0;
/*      */         }
/*      */ 
/*      */         break;
/*      */       }
/*      */ 
/* 6544 */       int alt68 = 3;
/* 6545 */       switch (this.input.LA(1))
/*      */       {
/*      */       case 37:
/* 6548 */         alt68 = 1;
/*      */ 
/* 6550 */         break;
/*      */       case 38:
/* 6553 */         alt68 = 2;
/*      */       }
/*      */       Object stream_retval;
/* 6558 */       switch (alt68)
/*      */       {
/*      */       case 1:
/* 6562 */         char_literal144 = (Token)match(this.input, 37, FOLLOW_ROOT_in_terminal2315); if (this.state.failed) { stream_retval = retval; return stream_retval; }
/* 6563 */         if (this.state.backtracking == 0) stream_ROOT.add(char_literal144);
/*      */ 
/* 6574 */         if (this.state.backtracking == 0) {
/* 6575 */           retval.tree = root_0;
/* 6576 */           stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.tree : null);
/*      */ 
/* 6578 */           root_0 = (CommonTree)this.adaptor.nil();
/*      */ 
/* 6583 */           CommonTree root_1 = (CommonTree)this.adaptor.nil();
/* 6584 */           root_1 = (CommonTree)this.adaptor.becomeRoot(stream_ROOT.nextNode(), root_1);
/*      */ 
/* 6586 */           this.adaptor.addChild(root_1, ((RewriteRuleSubtreeStream)stream_retval).nextTree());
/*      */ 
/* 6588 */           this.adaptor.addChild(root_0, root_1);
/*      */ 
/* 6593 */           retval.tree = root_0; } break;
/*      */       case 2:
/* 6599 */         char_literal145 = (Token)match(this.input, 38, FOLLOW_BANG_in_terminal2336); if (this.state.failed) { stream_retval = retval; return stream_retval; }
/* 6600 */         if (this.state.backtracking == 0) stream_BANG.add(char_literal145);
/*      */ 
/* 6611 */         if (this.state.backtracking == 0) {
/* 6612 */           retval.tree = root_0;
/* 6613 */           RewriteRuleSubtreeStream stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.tree : null);
/*      */ 
/* 6615 */           root_0 = (CommonTree)this.adaptor.nil();
/*      */ 
/* 6620 */           CommonTree root_1 = (CommonTree)this.adaptor.nil();
/* 6621 */           root_1 = (CommonTree)this.adaptor.becomeRoot(stream_BANG.nextNode(), root_1);
/*      */ 
/* 6623 */           this.adaptor.addChild(root_1, stream_retval.nextTree());
/*      */ 
/* 6625 */           this.adaptor.addChild(root_0, root_1);
/*      */ 
/* 6630 */           retval.tree = root_0;
/*      */         }
/*      */ 
/*      */         break;
/*      */       }
/*      */ 
/* 6639 */       retval.stop = this.input.LT(-1);
/*      */ 
/* 6641 */       if (this.state.backtracking == 0)
/*      */       {
/* 6643 */         retval.tree = ((CommonTree)this.adaptor.rulePostProcessing(root_0));
/* 6644 */         this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
/*      */       }
/*      */     } catch (RecognitionException re) {
/* 6647 */       re = 
/* 6654 */         re;
/*      */ 
/* 6648 */       reportError(re);
/* 6649 */       recover(this.input, re);
/* 6650 */       retval.tree = ((CommonTree)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), re));
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/* 6655 */     return retval;
/*      */   }
/*      */ 
/*      */   public final ebnf_return ebnf()
/*      */     throws RecognitionException
/*      */   {
/* 6667 */     ebnf_return retval = new ebnf_return();
/* 6668 */     retval.start = this.input.LT(1);
/*      */ 
/* 6670 */     CommonTree root_0 = null;
/*      */ 
/* 6672 */     Token op = null;
/* 6673 */     Token string_literal147 = null;
/* 6674 */     block_return block146 = null;
/*      */ 
/* 6677 */     CommonTree op_tree = null;
/* 6678 */     CommonTree string_literal147_tree = null;
/* 6679 */     RewriteRuleTokenStream stream_92 = new RewriteRuleTokenStream(this.adaptor, "token 92");
/* 6680 */     RewriteRuleTokenStream stream_91 = new RewriteRuleTokenStream(this.adaptor, "token 91");
/* 6681 */     RewriteRuleTokenStream stream_86 = new RewriteRuleTokenStream(this.adaptor, "token 86");
/* 6682 */     RewriteRuleTokenStream stream_74 = new RewriteRuleTokenStream(this.adaptor, "token 74");
/* 6683 */     RewriteRuleSubtreeStream stream_block = new RewriteRuleSubtreeStream(this.adaptor, "rule block");
/*      */ 
/* 6685 */     Token firstToken = this.input.LT(1);
/*      */     try
/*      */     {
/* 6691 */       pushFollow(FOLLOW_block_in_ebnf2379);
/* 6692 */       block146 = block();
/*      */ 
/* 6694 */       this.state._fsp -= 1;
/* 6695 */       if (this.state.failed) { ebnf_return localebnf_return1 = retval; return localebnf_return1; }
/* 6696 */       if (this.state.backtracking == 0) stream_block.add(block146.getTree());
/*      */ 
/* 6698 */       int alt69 = 5;
/*      */       Object nvae;
/* 6699 */       switch (this.input.LA(1))
/*      */       {
/*      */       case 91:
/* 6702 */         alt69 = 1;
/*      */ 
/* 6704 */         break;
/*      */       case 74:
/* 6707 */         alt69 = 2;
/*      */ 
/* 6709 */         break;
/*      */       case 92:
/* 6712 */         alt69 = 3;
/*      */ 
/* 6714 */         break;
/*      */       case 86:
/* 6717 */         alt69 = 4;
/*      */ 
/* 6719 */         break;
/*      */       case 31:
/*      */       case 36:
/*      */       case 39:
/*      */       case 44:
/*      */       case 45:
/*      */       case 46:
/*      */       case 47:
/*      */       case 51:
/*      */       case 71:
/*      */       case 81:
/*      */       case 82:
/*      */       case 83:
/*      */       case 87:
/*      */       case 90:
/* 6735 */         alt69 = 5;
/*      */ 
/* 6737 */         break;
/*      */       case 32:
/*      */       case 33:
/*      */       case 34:
/*      */       case 35:
/*      */       case 37:
/*      */       case 38:
/*      */       case 40:
/*      */       case 41:
/*      */       case 42:
/*      */       case 43:
/*      */       case 48:
/*      */       case 49:
/*      */       case 50:
/*      */       case 52:
/*      */       case 53:
/*      */       case 54:
/*      */       case 55:
/*      */       case 56:
/*      */       case 57:
/*      */       case 58:
/*      */       case 59:
/*      */       case 60:
/*      */       case 61:
/*      */       case 62:
/*      */       case 63:
/*      */       case 64:
/*      */       case 65:
/*      */       case 66:
/*      */       case 67:
/*      */       case 68:
/*      */       case 69:
/*      */       case 70:
/*      */       case 72:
/*      */       case 73:
/*      */       case 75:
/*      */       case 76:
/*      */       case 77:
/*      */       case 78:
/*      */       case 79:
/*      */       case 80:
/*      */       case 84:
/*      */       case 85:
/*      */       case 88:
/*      */       case 89:
/*      */       default:
/* 6739 */         if (this.state.backtracking > 0) { this.state.failed = true; ebnf_return localebnf_return2 = retval; return localebnf_return2; }
/* 6740 */         nvae = new NoViableAltException("", 69, 0, this.input);
/*      */ 
/* 6743 */         throw ((Throwable)nvae);
/*      */       }
/*      */       Object stream_retval;
/*      */       Object stream_retval;
/*      */       Object stream_retval;
/* 6746 */       switch (alt69)
/*      */       {
/*      */       case 1:
/* 6750 */         op = (Token)match(this.input, 91, FOLLOW_91_in_ebnf2387); if (this.state.failed) { nvae = retval; return nvae; }
/* 6751 */         if (this.state.backtracking == 0) stream_91.add(op);
/*      */ 
/* 6762 */         if (this.state.backtracking == 0) {
/* 6763 */           retval.tree = root_0;
/* 6764 */           stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.tree : null);
/*      */ 
/* 6766 */           root_0 = (CommonTree)this.adaptor.nil();
/*      */ 
/* 6771 */           CommonTree root_1 = (CommonTree)this.adaptor.nil();
/* 6772 */           root_1 = (CommonTree)this.adaptor.becomeRoot((CommonTree)this.adaptor.create(9, op), root_1);
/*      */ 
/* 6774 */           this.adaptor.addChild(root_1, stream_block.nextTree());
/*      */ 
/* 6776 */           this.adaptor.addChild(root_0, root_1);
/*      */ 
/* 6781 */           retval.tree = root_0; } break;
/*      */       case 2:
/* 6787 */         op = (Token)match(this.input, 74, FOLLOW_74_in_ebnf2404); if (this.state.failed) { stream_retval = retval; return stream_retval; }
/* 6788 */         if (this.state.backtracking == 0) stream_74.add(op);
/*      */ 
/* 6799 */         if (this.state.backtracking == 0) {
/* 6800 */           retval.tree = root_0;
/* 6801 */           stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.tree : null);
/*      */ 
/* 6803 */           root_0 = (CommonTree)this.adaptor.nil();
/*      */ 
/* 6808 */           CommonTree root_1 = (CommonTree)this.adaptor.nil();
/* 6809 */           root_1 = (CommonTree)this.adaptor.becomeRoot((CommonTree)this.adaptor.create(10, op), root_1);
/*      */ 
/* 6811 */           this.adaptor.addChild(root_1, stream_block.nextTree());
/*      */ 
/* 6813 */           this.adaptor.addChild(root_0, root_1);
/*      */ 
/* 6818 */           retval.tree = root_0; } break;
/*      */       case 3:
/* 6824 */         op = (Token)match(this.input, 92, FOLLOW_92_in_ebnf2421); if (this.state.failed) { stream_retval = retval; return stream_retval; }
/* 6825 */         if (this.state.backtracking == 0) stream_92.add(op);
/*      */ 
/* 6836 */         if (this.state.backtracking == 0) {
/* 6837 */           retval.tree = root_0;
/* 6838 */           stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.tree : null);
/*      */ 
/* 6840 */           root_0 = (CommonTree)this.adaptor.nil();
/*      */ 
/* 6845 */           CommonTree root_1 = (CommonTree)this.adaptor.nil();
/* 6846 */           root_1 = (CommonTree)this.adaptor.becomeRoot((CommonTree)this.adaptor.create(11, op), root_1);
/*      */ 
/* 6848 */           this.adaptor.addChild(root_1, stream_block.nextTree());
/*      */ 
/* 6850 */           this.adaptor.addChild(root_0, root_1);
/*      */ 
/* 6855 */           retval.tree = root_0; } break;
/*      */       case 4:
/* 6861 */         string_literal147 = (Token)match(this.input, 86, FOLLOW_86_in_ebnf2438); if (this.state.failed) { stream_retval = retval; return stream_retval; }
/* 6862 */         if (this.state.backtracking == 0) stream_86.add(string_literal147);
/*      */ 
/* 6873 */         if (this.state.backtracking == 0) {
/* 6874 */           retval.tree = root_0;
/* 6875 */           RewriteRuleSubtreeStream stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.tree : null);
/*      */ 
/* 6877 */           root_0 = (CommonTree)this.adaptor.nil();
/*      */ 
/* 6879 */           if ((this.gtype == 27) && (Character.isUpperCase(((rule_scope)this.rule_stack.peek()).name.charAt(0))))
/*      */           {
/* 6883 */             CommonTree root_1 = (CommonTree)this.adaptor.nil();
/* 6884 */             root_1 = (CommonTree)this.adaptor.becomeRoot((CommonTree)this.adaptor.create(12, "=>"), root_1);
/*      */ 
/* 6886 */             this.adaptor.addChild(root_1, stream_block.nextTree());
/*      */ 
/* 6888 */             this.adaptor.addChild(root_0, root_1);
/*      */           }
/*      */           else
/*      */           {
/* 6894 */             this.adaptor.addChild(root_0, (CommonTree)this.adaptor.create(33, "SYN_SEMPRED"));
/*      */           }
/*      */ 
/* 6898 */           retval.tree = root_0; } break;
/*      */       case 5:
/* 6912 */         if (this.state.backtracking == 0) {
/* 6913 */           retval.tree = root_0;
/* 6914 */           RewriteRuleSubtreeStream stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.tree : null);
/*      */ 
/* 6916 */           root_0 = (CommonTree)this.adaptor.nil();
/*      */ 
/* 6919 */           this.adaptor.addChild(root_0, stream_block.nextTree());
/*      */ 
/* 6923 */           retval.tree = root_0;
/*      */         }
/*      */ 
/*      */         break;
/*      */       }
/*      */ 
/* 6932 */       retval.stop = this.input.LT(-1);
/*      */ 
/* 6934 */       if (this.state.backtracking == 0)
/*      */       {
/* 6936 */         retval.tree = ((CommonTree)this.adaptor.rulePostProcessing(root_0));
/* 6937 */         this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
/*      */       }
/* 6939 */       if (this.state.backtracking == 0)
/*      */       {
/* 6941 */         retval.tree.getToken().setLine(firstToken.getLine());
/* 6942 */         retval.tree.getToken().setCharPositionInLine(firstToken.getCharPositionInLine());
/*      */       }
/*      */     }
/*      */     catch (RecognitionException re) {
/* 6946 */       re = 
/* 6953 */         re;
/*      */ 
/* 6947 */       reportError(re);
/* 6948 */       recover(this.input, re);
/* 6949 */       retval.tree = ((CommonTree)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), re));
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/* 6954 */     return retval;
/*      */   }
/*      */ 
/*      */   public final ebnfSuffix_return ebnfSuffix()
/*      */     throws RecognitionException
/*      */   {
/* 6966 */     ebnfSuffix_return retval = new ebnfSuffix_return();
/* 6967 */     retval.start = this.input.LT(1);
/*      */ 
/* 6969 */     CommonTree root_0 = null;
/*      */ 
/* 6971 */     Token char_literal148 = null;
/* 6972 */     Token char_literal149 = null;
/* 6973 */     Token char_literal150 = null;
/*      */ 
/* 6975 */     CommonTree char_literal148_tree = null;
/* 6976 */     CommonTree char_literal149_tree = null;
/* 6977 */     CommonTree char_literal150_tree = null;
/* 6978 */     RewriteRuleTokenStream stream_92 = new RewriteRuleTokenStream(this.adaptor, "token 92");
/* 6979 */     RewriteRuleTokenStream stream_91 = new RewriteRuleTokenStream(this.adaptor, "token 91");
/* 6980 */     RewriteRuleTokenStream stream_74 = new RewriteRuleTokenStream(this.adaptor, "token 74");
/*      */ 
/* 6983 */     Token op = this.input.LT(1);
/*      */     try
/*      */     {
/* 6987 */       int alt70 = 3;
/*      */       Object nvae;
/* 6988 */       switch (this.input.LA(1))
/*      */       {
/*      */       case 91:
/* 6991 */         alt70 = 1;
/*      */ 
/* 6993 */         break;
/*      */       case 74:
/* 6996 */         alt70 = 2;
/*      */ 
/* 6998 */         break;
/*      */       case 92:
/* 7001 */         alt70 = 3;
/*      */ 
/* 7003 */         break;
/*      */       default:
/* 7005 */         if (this.state.backtracking > 0) { this.state.failed = true; ebnfSuffix_return localebnfSuffix_return1 = retval; return localebnfSuffix_return1; }
/* 7006 */         nvae = new NoViableAltException("", 70, 0, this.input);
/*      */ 
/* 7009 */         throw ((Throwable)nvae);
/*      */       }
/*      */       Object stream_retval;
/*      */       Object stream_retval;
/* 7012 */       switch (alt70)
/*      */       {
/*      */       case 1:
/* 7016 */         char_literal148 = (Token)match(this.input, 91, FOLLOW_91_in_ebnfSuffix2523); if (this.state.failed) { nvae = retval; return nvae; }
/* 7017 */         if (this.state.backtracking == 0) stream_91.add(char_literal148);
/*      */ 
/* 7028 */         if (this.state.backtracking == 0) {
/* 7029 */           retval.tree = root_0;
/* 7030 */           stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.tree : null);
/*      */ 
/* 7032 */           root_0 = (CommonTree)this.adaptor.nil();
/*      */ 
/* 7035 */           this.adaptor.addChild(root_0, (CommonTree)this.adaptor.create(9, op));
/*      */ 
/* 7039 */           retval.tree = root_0; } break;
/*      */       case 2:
/* 7045 */         char_literal149 = (Token)match(this.input, 74, FOLLOW_74_in_ebnfSuffix2535); if (this.state.failed) { stream_retval = retval; return stream_retval; }
/* 7046 */         if (this.state.backtracking == 0) stream_74.add(char_literal149);
/*      */ 
/* 7057 */         if (this.state.backtracking == 0) {
/* 7058 */           retval.tree = root_0;
/* 7059 */           stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.tree : null);
/*      */ 
/* 7061 */           root_0 = (CommonTree)this.adaptor.nil();
/*      */ 
/* 7064 */           this.adaptor.addChild(root_0, (CommonTree)this.adaptor.create(10, op));
/*      */ 
/* 7068 */           retval.tree = root_0; } break;
/*      */       case 3:
/* 7074 */         char_literal150 = (Token)match(this.input, 92, FOLLOW_92_in_ebnfSuffix2548); if (this.state.failed) { stream_retval = retval; return stream_retval; }
/* 7075 */         if (this.state.backtracking == 0) stream_92.add(char_literal150);
/*      */ 
/* 7086 */         if (this.state.backtracking == 0) {
/* 7087 */           retval.tree = root_0;
/* 7088 */           RewriteRuleSubtreeStream stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.tree : null);
/*      */ 
/* 7090 */           root_0 = (CommonTree)this.adaptor.nil();
/*      */ 
/* 7093 */           this.adaptor.addChild(root_0, (CommonTree)this.adaptor.create(11, op));
/*      */ 
/* 7097 */           retval.tree = root_0;
/*      */         }
/*      */         break;
/*      */       }
/*      */ 
/* 7102 */       retval.stop = this.input.LT(-1);
/*      */ 
/* 7104 */       if (this.state.backtracking == 0)
/*      */       {
/* 7106 */         retval.tree = ((CommonTree)this.adaptor.rulePostProcessing(root_0));
/* 7107 */         this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
/*      */       }
/*      */     } catch (RecognitionException re) {
/* 7110 */       re = 
/* 7117 */         re;
/*      */ 
/* 7111 */       reportError(re);
/* 7112 */       recover(this.input, re);
/* 7113 */       retval.tree = ((CommonTree)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), re));
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/* 7118 */     return retval;
/*      */   }
/*      */ 
/*      */   public final rewrite_return rewrite()
/*      */     throws RecognitionException
/*      */   {
/* 7130 */     rewrite_return retval = new rewrite_return();
/* 7131 */     retval.start = this.input.LT(1);
/*      */ 
/* 7133 */     CommonTree root_0 = null;
/*      */ 
/* 7135 */     Token rew2 = null;
/* 7136 */     Token rew = null;
/* 7137 */     Token preds = null;
/* 7138 */     List list_rew = null;
/* 7139 */     List list_preds = null;
/* 7140 */     List list_predicated = null;
/* 7141 */     rewrite_alternative_return last = null;
/*      */ 
/* 7143 */     RuleReturnScope predicated = null;
/* 7144 */     CommonTree rew2_tree = null;
/* 7145 */     CommonTree rew_tree = null;
/* 7146 */     CommonTree preds_tree = null;
/* 7147 */     RewriteRuleTokenStream stream_SEMPRED = new RewriteRuleTokenStream(this.adaptor, "token SEMPRED");
/* 7148 */     RewriteRuleTokenStream stream_REWRITE = new RewriteRuleTokenStream(this.adaptor, "token REWRITE");
/* 7149 */     RewriteRuleSubtreeStream stream_rewrite_alternative = new RewriteRuleSubtreeStream(this.adaptor, "rule rewrite_alternative");
/*      */ 
/* 7151 */     Token firstToken = this.input.LT(1);
/*      */     try
/*      */     {
/* 7155 */       int alt72 = 2;
/* 7156 */       switch (this.input.LA(1))
/*      */       {
/*      */       case 39:
/* 7159 */         alt72 = 1;
/*      */ 
/* 7161 */         break;
/*      */       case 71:
/*      */       case 82:
/*      */       case 83:
/* 7166 */         alt72 = 2;
/*      */ 
/* 7168 */         break;
/*      */       default:
/* 7170 */         if (this.state.backtracking > 0) { this.state.failed = true; rewrite_return localrewrite_return1 = retval; return localrewrite_return1; }
/* 7171 */         NoViableAltException nvae = new NoViableAltException("", 72, 0, this.input);
/*      */ 
/* 7174 */         throw nvae;
/*      */       }
/*      */ 
/* 7177 */       switch (alt72)
/*      */       {
/*      */       case 1:
/*      */         int alt71;
/*      */         while (true)
/*      */         {
/* 7184 */           alt71 = 2;
/* 7185 */           switch (this.input.LA(1))
/*      */           {
/*      */           case 39:
/* 7188 */             switch (this.input.LA(2))
/*      */             {
/*      */             case 31:
/* 7191 */               alt71 = 1;
/*      */             }
/*      */ 
/*      */             break;
/*      */           }
/*      */ 
/* 7202 */           switch (alt71)
/*      */           {
/*      */           case 1:
/* 7206 */             rew = (Token)match(this.input, 39, FOLLOW_REWRITE_in_rewrite2577);
/*      */             rewrite_return localrewrite_return3;
/* 7206 */             if (this.state.failed) { localrewrite_return3 = retval; return localrewrite_return3; }
/* 7207 */             if (this.state.backtracking == 0) stream_REWRITE.add(rew);
/*      */ 
/* 7209 */             if (list_rew == null) list_rew = new ArrayList();
/* 7210 */             list_rew.add(rew);
/*      */ 
/* 7212 */             preds = (Token)match(this.input, 31, FOLLOW_SEMPRED_in_rewrite2581); if (this.state.failed) { localrewrite_return3 = retval; return localrewrite_return3; }
/* 7213 */             if (this.state.backtracking == 0) stream_SEMPRED.add(preds);
/*      */ 
/* 7215 */             if (list_preds == null) list_preds = new ArrayList();
/* 7216 */             list_preds.add(preds);
/*      */ 
/* 7218 */             pushFollow(FOLLOW_rewrite_alternative_in_rewrite2585);
/* 7219 */             predicated = rewrite_alternative();
/*      */ 
/* 7221 */             this.state._fsp -= 1;
/* 7222 */             if (this.state.failed) { localrewrite_return3 = retval; return localrewrite_return3; }
/* 7223 */             if (this.state.backtracking == 0) stream_rewrite_alternative.add(predicated.getTree());
/* 7224 */             if (list_predicated == null) list_predicated = new ArrayList();
/* 7225 */             list_predicated.add(predicated.getTree());
/*      */ 
/* 7229 */             break;
/*      */           default:
/* 7232 */             break label588;
/*      */           }
/*      */         }
/*      */ 
/* 7236 */         rew2 = (Token)match(this.input, 39, FOLLOW_REWRITE_in_rewrite2593);
/*      */         rewrite_return localrewrite_return2;
/* 7236 */         if (this.state.failed) { localrewrite_return2 = retval; return localrewrite_return2; }
/* 7237 */         if (this.state.backtracking == 0) stream_REWRITE.add(rew2);
/*      */ 
/* 7239 */         pushFollow(FOLLOW_rewrite_alternative_in_rewrite2597);
/* 7240 */         last = rewrite_alternative();
/*      */ 
/* 7242 */         this.state._fsp -= 1;
/* 7243 */         if (this.state.failed) { localrewrite_return2 = retval; return localrewrite_return2; }
/* 7244 */         if (this.state.backtracking == 0) stream_rewrite_alternative.add(last.getTree());
/*      */ 
/* 7254 */         if (this.state.backtracking == 0) {
/* 7255 */           retval.tree = root_0;
/* 7256 */           RewriteRuleTokenStream stream_rew2 = new RewriteRuleTokenStream(this.adaptor, "token rew2", rew2);
/* 7257 */           RewriteRuleTokenStream stream_rew = new RewriteRuleTokenStream(this.adaptor, "token rew", list_rew);
/* 7258 */           RewriteRuleTokenStream stream_preds = new RewriteRuleTokenStream(this.adaptor, "token preds", list_preds);
/* 7259 */           RewriteRuleSubtreeStream stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.tree : null);
/* 7260 */           RewriteRuleSubtreeStream stream_last = new RewriteRuleSubtreeStream(this.adaptor, "rule last", last != null ? last.tree : null);
/* 7261 */           RewriteRuleSubtreeStream stream_predicated = new RewriteRuleSubtreeStream(this.adaptor, "token predicated", list_predicated);
/* 7262 */           root_0 = (CommonTree)this.adaptor.nil();
/*      */ 
/* 7266 */           while ((stream_preds.hasNext()) || (stream_predicated.hasNext()) || (stream_rew.hasNext()))
/*      */           {
/* 7269 */             CommonTree root_1 = (CommonTree)this.adaptor.nil();
/* 7270 */             root_1 = (CommonTree)this.adaptor.becomeRoot(stream_rew.nextNode(), root_1);
/*      */ 
/* 7272 */             this.adaptor.addChild(root_1, stream_preds.nextNode());
/* 7273 */             this.adaptor.addChild(root_1, stream_predicated.nextTree());
/*      */ 
/* 7275 */             this.adaptor.addChild(root_0, root_1);
/*      */           }
/*      */ 
/* 7279 */           stream_preds.reset();
/* 7280 */           stream_predicated.reset();
/* 7281 */           stream_rew.reset();
/*      */ 
/* 7284 */           CommonTree root_1 = (CommonTree)this.adaptor.nil();
/* 7285 */           root_1 = (CommonTree)this.adaptor.becomeRoot(stream_rew2.nextNode(), root_1);
/*      */ 
/* 7287 */           this.adaptor.addChild(root_1, stream_last.nextTree());
/*      */ 
/* 7289 */           this.adaptor.addChild(root_0, root_1);
/*      */ 
/* 7294 */           retval.tree = root_0; } break;
/*      */       case 2:
/* 7300 */         label588: root_0 = (CommonTree)this.adaptor.nil();
/*      */       }
/*      */ 
/* 7306 */       retval.stop = this.input.LT(-1);
/*      */ 
/* 7308 */       if (this.state.backtracking == 0)
/*      */       {
/* 7310 */         retval.tree = ((CommonTree)this.adaptor.rulePostProcessing(root_0));
/* 7311 */         this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
/*      */       }
/*      */     } catch (RecognitionException re) {
/* 7314 */       re = 
/* 7321 */         re;
/*      */ 
/* 7315 */       reportError(re);
/* 7316 */       recover(this.input, re);
/* 7317 */       retval.tree = ((CommonTree)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), re));
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/* 7322 */     return retval;
/*      */   }
/*      */ 
/*      */   public final rewrite_alternative_return rewrite_alternative()
/*      */     throws RecognitionException
/*      */   {
/* 7334 */     rewrite_alternative_return retval = new rewrite_alternative_return();
/* 7335 */     retval.start = this.input.LT(1);
/*      */ 
/* 7337 */     CommonTree root_0 = null;
/*      */ 
/* 7339 */     rewrite_template_return rewrite_template151 = null;
/*      */ 
/* 7341 */     rewrite_tree_alternative_return rewrite_tree_alternative152 = null;
/*      */     try
/*      */     {
/* 7347 */       int alt73 = 3;
/* 7348 */       alt73 = this.dfa73.predict(this.input);
/*      */       rewrite_alternative_return localrewrite_alternative_return1;
/* 7349 */       switch (alt73)
/*      */       {
/*      */       case 1:
/* 7353 */         root_0 = (CommonTree)this.adaptor.nil();
/*      */ 
/* 7355 */         pushFollow(FOLLOW_rewrite_template_in_rewrite_alternative2648);
/* 7356 */         rewrite_template151 = rewrite_template();
/*      */ 
/* 7358 */         this.state._fsp -= 1;
/* 7359 */         if (this.state.failed) { localrewrite_alternative_return1 = retval; return localrewrite_alternative_return1; }
/* 7360 */         if (this.state.backtracking == 0) this.adaptor.addChild(root_0, rewrite_template151.getTree()); break;
/*      */       case 2:
/* 7367 */         root_0 = (CommonTree)this.adaptor.nil();
/*      */ 
/* 7369 */         pushFollow(FOLLOW_rewrite_tree_alternative_in_rewrite_alternative2653);
/* 7370 */         rewrite_tree_alternative152 = rewrite_tree_alternative();
/*      */ 
/* 7372 */         this.state._fsp -= 1;
/* 7373 */         if (this.state.failed) { localrewrite_alternative_return1 = retval; return localrewrite_alternative_return1; }
/* 7374 */         if (this.state.backtracking == 0) this.adaptor.addChild(root_0, rewrite_tree_alternative152.getTree()); break;
/*      */       case 3:
/* 7389 */         if (this.state.backtracking == 0) {
/* 7390 */           retval.tree = root_0;
/* 7391 */           RewriteRuleSubtreeStream stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.tree : null);
/*      */ 
/* 7393 */           root_0 = (CommonTree)this.adaptor.nil();
/*      */ 
/* 7398 */           CommonTree root_1 = (CommonTree)this.adaptor.nil();
/* 7399 */           root_1 = (CommonTree)this.adaptor.becomeRoot((CommonTree)this.adaptor.create(16, "ALT"), root_1);
/*      */ 
/* 7401 */           this.adaptor.addChild(root_1, (CommonTree)this.adaptor.create(15, "EPSILON"));
/* 7402 */           this.adaptor.addChild(root_1, (CommonTree)this.adaptor.create(19, "EOA"));
/*      */ 
/* 7404 */           this.adaptor.addChild(root_0, root_1);
/*      */ 
/* 7409 */           retval.tree = root_0;
/*      */         }
/*      */         break;
/*      */       }
/*      */ 
/* 7414 */       retval.stop = this.input.LT(-1);
/*      */ 
/* 7416 */       if (this.state.backtracking == 0)
/*      */       {
/* 7418 */         retval.tree = ((CommonTree)this.adaptor.rulePostProcessing(root_0));
/* 7419 */         this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
/*      */       }
/*      */     } catch (RecognitionException re) {
/* 7422 */       re = 
/* 7429 */         re;
/*      */ 
/* 7423 */       reportError(re);
/* 7424 */       recover(this.input, re);
/* 7425 */       retval.tree = ((CommonTree)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), re));
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/* 7430 */     return retval;
/*      */   }
/*      */ 
/*      */   public final rewrite_tree_block_return rewrite_tree_block()
/*      */     throws RecognitionException
/*      */   {
/* 7442 */     rewrite_tree_block_return retval = new rewrite_tree_block_return();
/* 7443 */     retval.start = this.input.LT(1);
/*      */ 
/* 7445 */     CommonTree root_0 = null;
/*      */ 
/* 7447 */     Token lp = null;
/* 7448 */     Token char_literal154 = null;
/* 7449 */     rewrite_tree_alternative_return rewrite_tree_alternative153 = null;
/*      */ 
/* 7452 */     CommonTree lp_tree = null;
/* 7453 */     CommonTree char_literal154_tree = null;
/* 7454 */     RewriteRuleTokenStream stream_83 = new RewriteRuleTokenStream(this.adaptor, "token 83");
/* 7455 */     RewriteRuleTokenStream stream_81 = new RewriteRuleTokenStream(this.adaptor, "token 81");
/* 7456 */     RewriteRuleSubtreeStream stream_rewrite_tree_alternative = new RewriteRuleSubtreeStream(this.adaptor, "rule rewrite_tree_alternative");
/*      */     try
/*      */     {
/* 7461 */       lp = (Token)match(this.input, 81, FOLLOW_81_in_rewrite_tree_block2695);
/*      */       rewrite_tree_block_return localrewrite_tree_block_return1;
/* 7461 */       if (this.state.failed) { localrewrite_tree_block_return1 = retval; return localrewrite_tree_block_return1; }
/* 7462 */       if (this.state.backtracking == 0) stream_81.add(lp);
/*      */ 
/* 7464 */       pushFollow(FOLLOW_rewrite_tree_alternative_in_rewrite_tree_block2697);
/* 7465 */       rewrite_tree_alternative153 = rewrite_tree_alternative();
/*      */ 
/* 7467 */       this.state._fsp -= 1;
/* 7468 */       if (this.state.failed) { localrewrite_tree_block_return1 = retval; return localrewrite_tree_block_return1; }
/* 7469 */       if (this.state.backtracking == 0) stream_rewrite_tree_alternative.add(rewrite_tree_alternative153.getTree());
/* 7470 */       char_literal154 = (Token)match(this.input, 83, FOLLOW_83_in_rewrite_tree_block2699); if (this.state.failed) { localrewrite_tree_block_return1 = retval; return localrewrite_tree_block_return1; }
/* 7471 */       if (this.state.backtracking == 0) stream_83.add(char_literal154);
/*      */ 
/* 7482 */       if (this.state.backtracking == 0) {
/* 7483 */         retval.tree = root_0;
/* 7484 */         RewriteRuleSubtreeStream stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.tree : null);
/*      */ 
/* 7486 */         root_0 = (CommonTree)this.adaptor.nil();
/*      */ 
/* 7491 */         CommonTree root_1 = (CommonTree)this.adaptor.nil();
/* 7492 */         root_1 = (CommonTree)this.adaptor.becomeRoot((CommonTree)this.adaptor.create(8, lp, "BLOCK"), root_1);
/*      */ 
/* 7494 */         this.adaptor.addChild(root_1, stream_rewrite_tree_alternative.nextTree());
/* 7495 */         this.adaptor.addChild(root_1, (CommonTree)this.adaptor.create(18, lp, "EOB"));
/*      */ 
/* 7497 */         this.adaptor.addChild(root_0, root_1);
/*      */ 
/* 7502 */         retval.tree = root_0;
/*      */       }
/*      */ 
/* 7505 */       retval.stop = this.input.LT(-1);
/*      */ 
/* 7507 */       if (this.state.backtracking == 0)
/*      */       {
/* 7509 */         retval.tree = ((CommonTree)this.adaptor.rulePostProcessing(root_0));
/* 7510 */         this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
/*      */       }
/*      */     } catch (RecognitionException re) {
/* 7513 */       re = 
/* 7520 */         re;
/*      */ 
/* 7514 */       reportError(re);
/* 7515 */       recover(this.input, re);
/* 7516 */       retval.tree = ((CommonTree)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), re));
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/* 7521 */     return retval;
/*      */   }
/*      */ 
/*      */   public final rewrite_tree_alternative_return rewrite_tree_alternative()
/*      */     throws RecognitionException
/*      */   {
/* 7533 */     rewrite_tree_alternative_return retval = new rewrite_tree_alternative_return();
/* 7534 */     retval.start = this.input.LT(1);
/*      */ 
/* 7536 */     CommonTree root_0 = null;
/*      */ 
/* 7538 */     rewrite_tree_element_return rewrite_tree_element155 = null;
/*      */ 
/* 7541 */     RewriteRuleSubtreeStream stream_rewrite_tree_element = new RewriteRuleSubtreeStream(this.adaptor, "rule rewrite_tree_element");
/*      */     try
/*      */     {
/* 7547 */       int cnt74 = 0;
/*      */       while (true)
/*      */       {
/* 7550 */         int alt74 = 2;
/* 7551 */         switch (this.input.LA(1))
/*      */         {
/*      */         case 36:
/*      */         case 44:
/*      */         case 45:
/*      */         case 46:
/*      */         case 47:
/*      */         case 51:
/*      */         case 81:
/*      */         case 93:
/* 7561 */           alt74 = 1;
/*      */         }
/*      */         rewrite_tree_alternative_return localrewrite_tree_alternative_return1;
/* 7567 */         switch (alt74)
/*      */         {
/*      */         case 1:
/* 7571 */           pushFollow(FOLLOW_rewrite_tree_element_in_rewrite_tree_alternative2733);
/* 7572 */           rewrite_tree_element155 = rewrite_tree_element();
/*      */ 
/* 7574 */           this.state._fsp -= 1;
/* 7575 */           if (this.state.failed) { localrewrite_tree_alternative_return1 = retval; return localrewrite_tree_alternative_return1; }
/* 7576 */           if (this.state.backtracking == 0) stream_rewrite_tree_element.add(rewrite_tree_element155.getTree()); break;
/*      */         default:
/* 7582 */           if (cnt74 >= 1) break label276;
/* 7583 */           if (this.state.backtracking > 0) { this.state.failed = true; localrewrite_tree_alternative_return1 = retval; return localrewrite_tree_alternative_return1; }
/* 7584 */           EarlyExitException eee = new EarlyExitException(74, this.input);
/*      */ 
/* 7586 */           throw eee;
/*      */         }
/* 7588 */         cnt74++;
/*      */       }
/*      */ 
/* 7600 */       label276: if (this.state.backtracking == 0) {
/* 7601 */         retval.tree = root_0;
/* 7602 */         RewriteRuleSubtreeStream stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.tree : null);
/*      */ 
/* 7604 */         root_0 = (CommonTree)this.adaptor.nil();
/*      */ 
/* 7609 */         CommonTree root_1 = (CommonTree)this.adaptor.nil();
/* 7610 */         root_1 = (CommonTree)this.adaptor.becomeRoot((CommonTree)this.adaptor.create(16, "ALT"), root_1);
/*      */ 
/* 7612 */         if (!stream_rewrite_tree_element.hasNext()) {
/* 7613 */           throw new RewriteEarlyExitException();
/*      */         }
/* 7615 */         while (stream_rewrite_tree_element.hasNext()) {
/* 7616 */           this.adaptor.addChild(root_1, stream_rewrite_tree_element.nextTree());
/*      */         }
/*      */ 
/* 7619 */         stream_rewrite_tree_element.reset();
/* 7620 */         this.adaptor.addChild(root_1, (CommonTree)this.adaptor.create(19, "EOA"));
/*      */ 
/* 7622 */         this.adaptor.addChild(root_0, root_1);
/*      */ 
/* 7627 */         retval.tree = root_0;
/*      */       }
/*      */ 
/* 7630 */       retval.stop = this.input.LT(-1);
/*      */ 
/* 7632 */       if (this.state.backtracking == 0)
/*      */       {
/* 7634 */         retval.tree = ((CommonTree)this.adaptor.rulePostProcessing(root_0));
/* 7635 */         this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
/*      */       }
/*      */     } catch (RecognitionException re) {
/* 7638 */       re = 
/* 7645 */         re;
/*      */ 
/* 7639 */       reportError(re);
/* 7640 */       recover(this.input, re);
/* 7641 */       retval.tree = ((CommonTree)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), re));
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/* 7646 */     return retval;
/*      */   }
/*      */ 
/*      */   public final rewrite_tree_element_return rewrite_tree_element()
/*      */     throws RecognitionException
/*      */   {
/* 7658 */     rewrite_tree_element_return retval = new rewrite_tree_element_return();
/* 7659 */     retval.start = this.input.LT(1);
/*      */ 
/* 7661 */     CommonTree root_0 = null;
/*      */ 
/* 7663 */     rewrite_tree_atom_return rewrite_tree_atom156 = null;
/*      */ 
/* 7665 */     rewrite_tree_atom_return rewrite_tree_atom157 = null;
/*      */ 
/* 7667 */     ebnfSuffix_return ebnfSuffix158 = null;
/*      */ 
/* 7669 */     rewrite_tree_return rewrite_tree159 = null;
/*      */ 
/* 7671 */     ebnfSuffix_return ebnfSuffix160 = null;
/*      */ 
/* 7673 */     rewrite_tree_ebnf_return rewrite_tree_ebnf161 = null;
/*      */ 
/* 7676 */     RewriteRuleSubtreeStream stream_rewrite_tree = new RewriteRuleSubtreeStream(this.adaptor, "rule rewrite_tree");
/* 7677 */     RewriteRuleSubtreeStream stream_ebnfSuffix = new RewriteRuleSubtreeStream(this.adaptor, "rule ebnfSuffix");
/* 7678 */     RewriteRuleSubtreeStream stream_rewrite_tree_atom = new RewriteRuleSubtreeStream(this.adaptor, "rule rewrite_tree_atom");
/*      */     try
/*      */     {
/* 7681 */       int alt76 = 4;
/* 7682 */       alt76 = this.dfa76.predict(this.input);
/*      */       rewrite_tree_element_return localrewrite_tree_element_return1;
/*      */       Object stream_retval;
/*      */       CommonTree root_1;
/*      */       int alt75;
/* 7683 */       switch (alt76)
/*      */       {
/*      */       case 1:
/* 7687 */         root_0 = (CommonTree)this.adaptor.nil();
/*      */ 
/* 7689 */         pushFollow(FOLLOW_rewrite_tree_atom_in_rewrite_tree_element2761);
/* 7690 */         rewrite_tree_atom156 = rewrite_tree_atom();
/*      */ 
/* 7692 */         this.state._fsp -= 1;
/* 7693 */         if (this.state.failed) { localrewrite_tree_element_return1 = retval; return localrewrite_tree_element_return1; }
/* 7694 */         if (this.state.backtracking == 0) this.adaptor.addChild(root_0, rewrite_tree_atom156.getTree()); break;
/*      */       case 2:
/* 7701 */         pushFollow(FOLLOW_rewrite_tree_atom_in_rewrite_tree_element2766);
/* 7702 */         rewrite_tree_atom157 = rewrite_tree_atom();
/*      */ 
/* 7704 */         this.state._fsp -= 1;
/* 7705 */         if (this.state.failed) { localrewrite_tree_element_return1 = retval; return localrewrite_tree_element_return1; }
/* 7706 */         if (this.state.backtracking == 0) stream_rewrite_tree_atom.add(rewrite_tree_atom157.getTree());
/* 7707 */         pushFollow(FOLLOW_ebnfSuffix_in_rewrite_tree_element2768);
/* 7708 */         ebnfSuffix158 = ebnfSuffix();
/*      */ 
/* 7710 */         this.state._fsp -= 1;
/* 7711 */         if (this.state.failed) { localrewrite_tree_element_return1 = retval; return localrewrite_tree_element_return1; }
/* 7712 */         if (this.state.backtracking == 0) stream_ebnfSuffix.add(ebnfSuffix158.getTree());
/*      */ 
/* 7722 */         if (this.state.backtracking == 0) {
/* 7723 */           retval.tree = root_0;
/* 7724 */           stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.tree : null);
/*      */ 
/* 7726 */           root_0 = (CommonTree)this.adaptor.nil();
/*      */ 
/* 7731 */           root_1 = (CommonTree)this.adaptor.nil();
/* 7732 */           root_1 = (CommonTree)this.adaptor.becomeRoot(stream_ebnfSuffix.nextNode(), root_1);
/*      */ 
/* 7736 */           CommonTree root_2 = (CommonTree)this.adaptor.nil();
/* 7737 */           root_2 = (CommonTree)this.adaptor.becomeRoot((CommonTree)this.adaptor.create(8, "BLOCK"), root_2);
/*      */ 
/* 7741 */           CommonTree root_3 = (CommonTree)this.adaptor.nil();
/* 7742 */           root_3 = (CommonTree)this.adaptor.becomeRoot((CommonTree)this.adaptor.create(16, "ALT"), root_3);
/*      */ 
/* 7744 */           this.adaptor.addChild(root_3, stream_rewrite_tree_atom.nextTree());
/* 7745 */           this.adaptor.addChild(root_3, (CommonTree)this.adaptor.create(19, "EOA"));
/*      */ 
/* 7747 */           this.adaptor.addChild(root_2, root_3);
/*      */ 
/* 7749 */           this.adaptor.addChild(root_2, (CommonTree)this.adaptor.create(18, "EOB"));
/*      */ 
/* 7751 */           this.adaptor.addChild(root_1, root_2);
/*      */ 
/* 7754 */           this.adaptor.addChild(root_0, root_1);
/*      */ 
/* 7759 */           retval.tree = root_0; } break;
/*      */       case 3:
/* 7765 */         pushFollow(FOLLOW_rewrite_tree_in_rewrite_tree_element2802);
/* 7766 */         rewrite_tree159 = rewrite_tree();
/*      */ 
/* 7768 */         this.state._fsp -= 1;
/* 7769 */         if (this.state.failed) { stream_retval = retval; return stream_retval; }
/* 7770 */         if (this.state.backtracking == 0) stream_rewrite_tree.add(rewrite_tree159.getTree());
/*      */ 
/* 7772 */         alt75 = 2;
/*      */         NoViableAltException nvae;
/* 7773 */         switch (this.input.LA(1))
/*      */         {
/*      */         case 74:
/*      */         case 91:
/*      */         case 92:
/* 7778 */           alt75 = 1;
/*      */ 
/* 7780 */           break;
/*      */         case -1:
/*      */         case 36:
/*      */         case 39:
/*      */         case 44:
/*      */         case 45:
/*      */         case 46:
/*      */         case 47:
/*      */         case 51:
/*      */         case 71:
/*      */         case 81:
/*      */         case 82:
/*      */         case 83:
/*      */         case 93:
/* 7795 */           alt75 = 2;
/*      */ 
/* 7797 */           break;
/*      */         default:
/* 7799 */           if (this.state.backtracking > 0) { this.state.failed = true; root_1 = retval; return root_1; }
/* 7800 */           nvae = new NoViableAltException("", 75, 0, this.input);
/*      */ 
/* 7803 */           throw nvae;
/*      */         }
/*      */ 
/* 7806 */         switch (alt75)
/*      */         {
/*      */         case 1:
/* 7810 */           pushFollow(FOLLOW_ebnfSuffix_in_rewrite_tree_element2808);
/* 7811 */           ebnfSuffix160 = ebnfSuffix();
/*      */ 
/* 7813 */           this.state._fsp -= 1;
/* 7814 */           if (this.state.failed) { nvae = retval; return nvae; }
/* 7815 */           if (this.state.backtracking == 0) stream_ebnfSuffix.add(ebnfSuffix160.getTree());
/*      */ 
/* 7825 */           if (this.state.backtracking == 0) {
/* 7826 */             retval.tree = root_0;
/* 7827 */             RewriteRuleSubtreeStream stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.tree : null);
/*      */ 
/* 7829 */             root_0 = (CommonTree)this.adaptor.nil();
/*      */ 
/* 7834 */             CommonTree root_1 = (CommonTree)this.adaptor.nil();
/* 7835 */             root_1 = (CommonTree)this.adaptor.becomeRoot(stream_ebnfSuffix.nextNode(), root_1);
/*      */ 
/* 7839 */             CommonTree root_2 = (CommonTree)this.adaptor.nil();
/* 7840 */             root_2 = (CommonTree)this.adaptor.becomeRoot((CommonTree)this.adaptor.create(8, "BLOCK"), root_2);
/*      */ 
/* 7844 */             CommonTree root_3 = (CommonTree)this.adaptor.nil();
/* 7845 */             root_3 = (CommonTree)this.adaptor.becomeRoot((CommonTree)this.adaptor.create(16, "ALT"), root_3);
/*      */ 
/* 7847 */             this.adaptor.addChild(root_3, stream_rewrite_tree.nextTree());
/* 7848 */             this.adaptor.addChild(root_3, (CommonTree)this.adaptor.create(19, "EOA"));
/*      */ 
/* 7850 */             this.adaptor.addChild(root_2, root_3);
/*      */ 
/* 7852 */             this.adaptor.addChild(root_2, (CommonTree)this.adaptor.create(18, "EOB"));
/*      */ 
/* 7854 */             this.adaptor.addChild(root_1, root_2);
/*      */ 
/* 7857 */             this.adaptor.addChild(root_0, root_1);
/*      */ 
/* 7862 */             retval.tree = root_0; } break;
/*      */         case 2:
/* 7876 */           if (this.state.backtracking == 0) {
/* 7877 */             retval.tree = root_0;
/* 7878 */             RewriteRuleSubtreeStream stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.tree : null);
/*      */ 
/* 7880 */             root_0 = (CommonTree)this.adaptor.nil();
/*      */ 
/* 7883 */             this.adaptor.addChild(root_0, stream_rewrite_tree.nextTree());
/*      */ 
/* 7887 */             retval.tree = root_0;
/*      */           }
/*      */ 
/*      */           break;
/*      */         }
/*      */ 
/* 7895 */         break;
/*      */       case 4:
/* 7899 */         root_0 = (CommonTree)this.adaptor.nil();
/*      */ 
/* 7901 */         pushFollow(FOLLOW_rewrite_tree_ebnf_in_rewrite_tree_element2854);
/* 7902 */         rewrite_tree_ebnf161 = rewrite_tree_ebnf();
/*      */ 
/* 7904 */         this.state._fsp -= 1;
/* 7905 */         if (this.state.failed) { rewrite_tree_element_return localrewrite_tree_element_return2 = retval; return localrewrite_tree_element_return2; }
/* 7906 */         if (this.state.backtracking == 0) this.adaptor.addChild(root_0, rewrite_tree_ebnf161.getTree());
/*      */ 
/*      */         break;
/*      */       }
/*      */ 
/* 7912 */       retval.stop = this.input.LT(-1);
/*      */ 
/* 7914 */       if (this.state.backtracking == 0)
/*      */       {
/* 7916 */         retval.tree = ((CommonTree)this.adaptor.rulePostProcessing(root_0));
/* 7917 */         this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
/*      */       }
/*      */     } catch (RecognitionException re) {
/* 7920 */       re = 
/* 7927 */         re;
/*      */ 
/* 7921 */       reportError(re);
/* 7922 */       recover(this.input, re);
/* 7923 */       retval.tree = ((CommonTree)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), re));
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/* 7928 */     return retval;
/*      */   }
/*      */ 
/*      */   public final rewrite_tree_atom_return rewrite_tree_atom()
/*      */     throws RecognitionException
/*      */   {
/* 7940 */     rewrite_tree_atom_return retval = new rewrite_tree_atom_return();
/* 7941 */     retval.start = this.input.LT(1);
/*      */ 
/* 7943 */     CommonTree root_0 = null;
/*      */ 
/* 7945 */     Token d = null;
/* 7946 */     Token CHAR_LITERAL162 = null;
/* 7947 */     Token TOKEN_REF163 = null;
/* 7948 */     Token ARG_ACTION164 = null;
/* 7949 */     Token RULE_REF165 = null;
/* 7950 */     Token STRING_LITERAL166 = null;
/* 7951 */     Token ACTION168 = null;
/* 7952 */     id_return id167 = null;
/*      */ 
/* 7955 */     CommonTree d_tree = null;
/* 7956 */     CommonTree CHAR_LITERAL162_tree = null;
/* 7957 */     CommonTree TOKEN_REF163_tree = null;
/* 7958 */     CommonTree ARG_ACTION164_tree = null;
/* 7959 */     CommonTree RULE_REF165_tree = null;
/* 7960 */     CommonTree STRING_LITERAL166_tree = null;
/* 7961 */     CommonTree ACTION168_tree = null;
/* 7962 */     RewriteRuleTokenStream stream_93 = new RewriteRuleTokenStream(this.adaptor, "token 93");
/* 7963 */     RewriteRuleTokenStream stream_TOKEN_REF = new RewriteRuleTokenStream(this.adaptor, "token TOKEN_REF");
/* 7964 */     RewriteRuleTokenStream stream_ARG_ACTION = new RewriteRuleTokenStream(this.adaptor, "token ARG_ACTION");
/* 7965 */     RewriteRuleSubtreeStream stream_id = new RewriteRuleSubtreeStream(this.adaptor, "rule id");
/*      */     try
/*      */     {
/* 7968 */       int alt78 = 6;
/*      */       Object nvae;
/* 7969 */       switch (this.input.LA(1))
/*      */       {
/*      */       case 46:
/* 7972 */         alt78 = 1;
/*      */ 
/* 7974 */         break;
/*      */       case 44:
/* 7977 */         alt78 = 2;
/*      */ 
/* 7979 */         break;
/*      */       case 51:
/* 7982 */         alt78 = 3;
/*      */ 
/* 7984 */         break;
/*      */       case 45:
/* 7987 */         alt78 = 4;
/*      */ 
/* 7989 */         break;
/*      */       case 93:
/* 7992 */         alt78 = 5;
/*      */ 
/* 7994 */         break;
/*      */       case 47:
/* 7997 */         alt78 = 6;
/*      */ 
/* 7999 */         break;
/*      */       default:
/* 8001 */         if (this.state.backtracking > 0) { this.state.failed = true; rewrite_tree_atom_return localrewrite_tree_atom_return1 = retval; return localrewrite_tree_atom_return1; }
/* 8002 */         nvae = new NoViableAltException("", 78, 0, this.input);
/*      */ 
/* 8005 */         throw ((Throwable)nvae);
/*      */       }
/*      */       int alt77;
/*      */       rewrite_tree_atom_return localrewrite_tree_atom_return2;
/*      */       Object stream_retval;
/* 8008 */       switch (alt78)
/*      */       {
/*      */       case 1:
/* 8012 */         root_0 = (CommonTree)this.adaptor.nil();
/*      */ 
/* 8014 */         CHAR_LITERAL162 = (Token)match(this.input, 46, FOLLOW_CHAR_LITERAL_in_rewrite_tree_atom2870); if (this.state.failed) { nvae = retval; return nvae; }
/* 8015 */         if (this.state.backtracking == 0) {
/* 8016 */           CHAR_LITERAL162_tree = (CommonTree)this.adaptor.create(CHAR_LITERAL162);
/* 8017 */           this.adaptor.addChild(root_0, CHAR_LITERAL162_tree); } break;
/*      */       case 2:
/* 8025 */         TOKEN_REF163 = (Token)match(this.input, 44, FOLLOW_TOKEN_REF_in_rewrite_tree_atom2877); if (this.state.failed) { nvae = retval; return nvae; }
/* 8026 */         if (this.state.backtracking == 0) stream_TOKEN_REF.add(TOKEN_REF163);
/*      */ 
/* 8029 */         alt77 = 2;
/* 8030 */         switch (this.input.LA(1))
/*      */         {
/*      */         case 50:
/* 8033 */           alt77 = 1;
/*      */         }
/*      */ 
/* 8038 */         switch (alt77)
/*      */         {
/*      */         case 1:
/* 8042 */           ARG_ACTION164 = (Token)match(this.input, 50, FOLLOW_ARG_ACTION_in_rewrite_tree_atom2879); if (this.state.failed) { rewrite_tree_atom_return localrewrite_tree_atom_return3 = retval; return localrewrite_tree_atom_return3; }
/* 8043 */           if (this.state.backtracking == 0) stream_ARG_ACTION.add(ARG_ACTION164);
/*      */ 
/*      */           break;
/*      */         }
/*      */ 
/* 8060 */         if (this.state.backtracking == 0) {
/* 8061 */           retval.tree = root_0;
/* 8062 */           RewriteRuleSubtreeStream stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.tree : null);
/*      */ 
/* 8064 */           root_0 = (CommonTree)this.adaptor.nil();
/*      */ 
/* 8069 */           CommonTree root_1 = (CommonTree)this.adaptor.nil();
/* 8070 */           root_1 = (CommonTree)this.adaptor.becomeRoot(stream_TOKEN_REF.nextNode(), root_1);
/*      */ 
/* 8073 */           if (stream_ARG_ACTION.hasNext()) {
/* 8074 */             this.adaptor.addChild(root_1, stream_ARG_ACTION.nextNode());
/*      */           }
/*      */ 
/* 8077 */           stream_ARG_ACTION.reset();
/*      */ 
/* 8079 */           this.adaptor.addChild(root_0, root_1);
/*      */ 
/* 8084 */           retval.tree = root_0;
/*      */         }
/* 8086 */         break;
/*      */       case 3:
/* 8090 */         root_0 = (CommonTree)this.adaptor.nil();
/*      */ 
/* 8092 */         RULE_REF165 = (Token)match(this.input, 51, FOLLOW_RULE_REF_in_rewrite_tree_atom2900); if (this.state.failed) { localrewrite_tree_atom_return2 = retval; return localrewrite_tree_atom_return2; }
/* 8093 */         if (this.state.backtracking == 0) {
/* 8094 */           RULE_REF165_tree = (CommonTree)this.adaptor.create(RULE_REF165);
/* 8095 */           this.adaptor.addChild(root_0, RULE_REF165_tree); } break;
/*      */       case 4:
/* 8103 */         root_0 = (CommonTree)this.adaptor.nil();
/*      */ 
/* 8105 */         STRING_LITERAL166 = (Token)match(this.input, 45, FOLLOW_STRING_LITERAL_in_rewrite_tree_atom2907); if (this.state.failed) { localrewrite_tree_atom_return2 = retval; return localrewrite_tree_atom_return2; }
/* 8106 */         if (this.state.backtracking == 0) {
/* 8107 */           STRING_LITERAL166_tree = (CommonTree)this.adaptor.create(STRING_LITERAL166);
/* 8108 */           this.adaptor.addChild(root_0, STRING_LITERAL166_tree); } break;
/*      */       case 5:
/* 8116 */         d = (Token)match(this.input, 93, FOLLOW_93_in_rewrite_tree_atom2916); if (this.state.failed) { localrewrite_tree_atom_return2 = retval; return localrewrite_tree_atom_return2; }
/* 8117 */         if (this.state.backtracking == 0) stream_93.add(d);
/*      */ 
/* 8119 */         pushFollow(FOLLOW_id_in_rewrite_tree_atom2918);
/* 8120 */         id167 = id();
/*      */ 
/* 8122 */         this.state._fsp -= 1;
/* 8123 */         if (this.state.failed) { localrewrite_tree_atom_return2 = retval; return localrewrite_tree_atom_return2; }
/* 8124 */         if (this.state.backtracking == 0) stream_id.add(id167.getTree());
/*      */ 
/* 8134 */         if (this.state.backtracking == 0) {
/* 8135 */           retval.tree = root_0;
/* 8136 */           stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.tree : null);
/*      */ 
/* 8138 */           root_0 = (CommonTree)this.adaptor.nil();
/*      */ 
/* 8141 */           this.adaptor.addChild(root_0, (CommonTree)this.adaptor.create(28, d, id167 != null ? this.input.toString(id167.start, id167.stop) : null));
/*      */ 
/* 8145 */           retval.tree = root_0; } break;
/*      */       case 6:
/* 8151 */         root_0 = (CommonTree)this.adaptor.nil();
/*      */ 
/* 8153 */         ACTION168 = (Token)match(this.input, 47, FOLLOW_ACTION_in_rewrite_tree_atom2929); if (this.state.failed) { stream_retval = retval; return stream_retval; }
/* 8154 */         if (this.state.backtracking == 0) {
/* 8155 */           ACTION168_tree = (CommonTree)this.adaptor.create(ACTION168);
/* 8156 */           this.adaptor.addChild(root_0, ACTION168_tree);
/*      */         }
/*      */ 
/*      */         break;
/*      */       }
/*      */ 
/* 8163 */       retval.stop = this.input.LT(-1);
/*      */ 
/* 8165 */       if (this.state.backtracking == 0)
/*      */       {
/* 8167 */         retval.tree = ((CommonTree)this.adaptor.rulePostProcessing(root_0));
/* 8168 */         this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
/*      */       }
/*      */     } catch (RecognitionException re) {
/* 8171 */       re = 
/* 8178 */         re;
/*      */ 
/* 8172 */       reportError(re);
/* 8173 */       recover(this.input, re);
/* 8174 */       retval.tree = ((CommonTree)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), re));
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/* 8179 */     return retval;
/*      */   }
/*      */ 
/*      */   public final rewrite_tree_ebnf_return rewrite_tree_ebnf()
/*      */     throws RecognitionException
/*      */   {
/* 8191 */     rewrite_tree_ebnf_return retval = new rewrite_tree_ebnf_return();
/* 8192 */     retval.start = this.input.LT(1);
/*      */ 
/* 8194 */     CommonTree root_0 = null;
/*      */ 
/* 8196 */     rewrite_tree_block_return rewrite_tree_block169 = null;
/*      */ 
/* 8198 */     ebnfSuffix_return ebnfSuffix170 = null;
/*      */ 
/* 8201 */     RewriteRuleSubtreeStream stream_ebnfSuffix = new RewriteRuleSubtreeStream(this.adaptor, "rule ebnfSuffix");
/* 8202 */     RewriteRuleSubtreeStream stream_rewrite_tree_block = new RewriteRuleSubtreeStream(this.adaptor, "rule rewrite_tree_block");
/*      */ 
/* 8204 */     Token firstToken = this.input.LT(1);
/*      */     try
/*      */     {
/* 8210 */       pushFollow(FOLLOW_rewrite_tree_block_in_rewrite_tree_ebnf2950);
/* 8211 */       rewrite_tree_block169 = rewrite_tree_block();
/*      */ 
/* 8213 */       this.state._fsp -= 1;
/*      */       rewrite_tree_ebnf_return localrewrite_tree_ebnf_return1;
/* 8214 */       if (this.state.failed) { localrewrite_tree_ebnf_return1 = retval; return localrewrite_tree_ebnf_return1; }
/* 8215 */       if (this.state.backtracking == 0) stream_rewrite_tree_block.add(rewrite_tree_block169.getTree());
/* 8216 */       pushFollow(FOLLOW_ebnfSuffix_in_rewrite_tree_ebnf2952);
/* 8217 */       ebnfSuffix170 = ebnfSuffix();
/*      */ 
/* 8219 */       this.state._fsp -= 1;
/* 8220 */       if (this.state.failed) { localrewrite_tree_ebnf_return1 = retval; return localrewrite_tree_ebnf_return1; }
/* 8221 */       if (this.state.backtracking == 0) stream_ebnfSuffix.add(ebnfSuffix170.getTree());
/*      */ 
/* 8231 */       if (this.state.backtracking == 0) {
/* 8232 */         retval.tree = root_0;
/* 8233 */         RewriteRuleSubtreeStream stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.tree : null);
/*      */ 
/* 8235 */         root_0 = (CommonTree)this.adaptor.nil();
/*      */ 
/* 8240 */         CommonTree root_1 = (CommonTree)this.adaptor.nil();
/* 8241 */         root_1 = (CommonTree)this.adaptor.becomeRoot(stream_ebnfSuffix.nextNode(), root_1);
/*      */ 
/* 8243 */         this.adaptor.addChild(root_1, stream_rewrite_tree_block.nextTree());
/*      */ 
/* 8245 */         this.adaptor.addChild(root_0, root_1);
/*      */ 
/* 8250 */         retval.tree = root_0;
/*      */       }
/*      */ 
/* 8253 */       retval.stop = this.input.LT(-1);
/*      */ 
/* 8255 */       if (this.state.backtracking == 0)
/*      */       {
/* 8257 */         retval.tree = ((CommonTree)this.adaptor.rulePostProcessing(root_0));
/* 8258 */         this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
/*      */       }
/* 8260 */       if (this.state.backtracking == 0)
/*      */       {
/* 8262 */         retval.tree.getToken().setLine(firstToken.getLine());
/* 8263 */         retval.tree.getToken().setCharPositionInLine(firstToken.getCharPositionInLine());
/*      */       }
/*      */     }
/*      */     catch (RecognitionException re) {
/* 8267 */       re = 
/* 8274 */         re;
/*      */ 
/* 8268 */       reportError(re);
/* 8269 */       recover(this.input, re);
/* 8270 */       retval.tree = ((CommonTree)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), re));
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/* 8275 */     return retval;
/*      */   }
/*      */ 
/*      */   public final rewrite_tree_return rewrite_tree()
/*      */     throws RecognitionException
/*      */   {
/* 8287 */     rewrite_tree_return retval = new rewrite_tree_return();
/* 8288 */     retval.start = this.input.LT(1);
/*      */ 
/* 8290 */     CommonTree root_0 = null;
/*      */ 
/* 8292 */     Token string_literal171 = null;
/* 8293 */     Token char_literal174 = null;
/* 8294 */     rewrite_tree_atom_return rewrite_tree_atom172 = null;
/*      */ 
/* 8296 */     rewrite_tree_element_return rewrite_tree_element173 = null;
/*      */ 
/* 8299 */     CommonTree string_literal171_tree = null;
/* 8300 */     CommonTree char_literal174_tree = null;
/* 8301 */     RewriteRuleTokenStream stream_83 = new RewriteRuleTokenStream(this.adaptor, "token 83");
/* 8302 */     RewriteRuleTokenStream stream_TREE_BEGIN = new RewriteRuleTokenStream(this.adaptor, "token TREE_BEGIN");
/* 8303 */     RewriteRuleSubtreeStream stream_rewrite_tree_element = new RewriteRuleSubtreeStream(this.adaptor, "rule rewrite_tree_element");
/* 8304 */     RewriteRuleSubtreeStream stream_rewrite_tree_atom = new RewriteRuleSubtreeStream(this.adaptor, "rule rewrite_tree_atom");
/*      */     try
/*      */     {
/* 8309 */       string_literal171 = (Token)match(this.input, 36, FOLLOW_TREE_BEGIN_in_rewrite_tree2972);
/*      */       rewrite_tree_return localrewrite_tree_return1;
/* 8309 */       if (this.state.failed) { localrewrite_tree_return1 = retval; return localrewrite_tree_return1; }
/* 8310 */       if (this.state.backtracking == 0) stream_TREE_BEGIN.add(string_literal171);
/*      */ 
/* 8312 */       pushFollow(FOLLOW_rewrite_tree_atom_in_rewrite_tree2974);
/* 8313 */       rewrite_tree_atom172 = rewrite_tree_atom();
/*      */ 
/* 8315 */       this.state._fsp -= 1;
/* 8316 */       if (this.state.failed) { localrewrite_tree_return1 = retval; return localrewrite_tree_return1; }
/* 8317 */       if (this.state.backtracking == 0) stream_rewrite_tree_atom.add(rewrite_tree_atom172.getTree());
/*      */       int alt79;
/*      */       while (true)
/*      */       {
/* 8321 */         alt79 = 2;
/* 8322 */         switch (this.input.LA(1))
/*      */         {
/*      */         case 36:
/*      */         case 44:
/*      */         case 45:
/*      */         case 46:
/*      */         case 47:
/*      */         case 51:
/*      */         case 81:
/*      */         case 93:
/* 8332 */           alt79 = 1;
/*      */         }
/*      */ 
/* 8338 */         switch (alt79)
/*      */         {
/*      */         case 1:
/* 8342 */           pushFollow(FOLLOW_rewrite_tree_element_in_rewrite_tree2976);
/* 8343 */           rewrite_tree_element173 = rewrite_tree_element();
/*      */ 
/* 8345 */           this.state._fsp -= 1;
/* 8346 */           if (this.state.failed) { rewrite_tree_return localrewrite_tree_return3 = retval; return localrewrite_tree_return3; }
/* 8347 */           if (this.state.backtracking == 0) stream_rewrite_tree_element.add(rewrite_tree_element173.getTree()); break;
/*      */         default:
/* 8353 */           break label399;
/*      */         }
/*      */       }
/*      */ 
/* 8357 */       label399: char_literal174 = (Token)match(this.input, 83, FOLLOW_83_in_rewrite_tree2979); if (this.state.failed) { rewrite_tree_return localrewrite_tree_return2 = retval; return localrewrite_tree_return2; }
/* 8358 */       if (this.state.backtracking == 0) stream_83.add(char_literal174);
/*      */ 
/* 8369 */       if (this.state.backtracking == 0) {
/* 8370 */         retval.tree = root_0;
/* 8371 */         RewriteRuleSubtreeStream stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.tree : null);
/*      */ 
/* 8373 */         root_0 = (CommonTree)this.adaptor.nil();
/*      */ 
/* 8378 */         CommonTree root_1 = (CommonTree)this.adaptor.nil();
/* 8379 */         root_1 = (CommonTree)this.adaptor.becomeRoot((CommonTree)this.adaptor.create(36, "TREE_BEGIN"), root_1);
/*      */ 
/* 8381 */         this.adaptor.addChild(root_1, stream_rewrite_tree_atom.nextTree());
/*      */ 
/* 8383 */         while (stream_rewrite_tree_element.hasNext()) {
/* 8384 */           this.adaptor.addChild(root_1, stream_rewrite_tree_element.nextTree());
/*      */         }
/*      */ 
/* 8387 */         stream_rewrite_tree_element.reset();
/*      */ 
/* 8389 */         this.adaptor.addChild(root_0, root_1);
/*      */ 
/* 8394 */         retval.tree = root_0;
/*      */       }
/*      */ 
/* 8397 */       retval.stop = this.input.LT(-1);
/*      */ 
/* 8399 */       if (this.state.backtracking == 0)
/*      */       {
/* 8401 */         retval.tree = ((CommonTree)this.adaptor.rulePostProcessing(root_0));
/* 8402 */         this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
/*      */       }
/*      */     } catch (RecognitionException re) {
/* 8405 */       re = 
/* 8412 */         re;
/*      */ 
/* 8406 */       reportError(re);
/* 8407 */       recover(this.input, re);
/* 8408 */       retval.tree = ((CommonTree)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), re));
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/* 8413 */     return retval;
/*      */   }
/*      */ 
/*      */   public final rewrite_template_return rewrite_template()
/*      */     throws RecognitionException
/*      */   {
/* 8425 */     rewrite_template_return retval = new rewrite_template_return();
/* 8426 */     retval.start = this.input.LT(1);
/*      */ 
/* 8428 */     CommonTree root_0 = null;
/*      */ 
/* 8430 */     Token lp = null;
/* 8431 */     Token str = null;
/* 8432 */     Token char_literal177 = null;
/* 8433 */     Token ACTION180 = null;
/* 8434 */     id_return id175 = null;
/*      */ 
/* 8436 */     rewrite_template_args_return rewrite_template_args176 = null;
/*      */ 
/* 8438 */     rewrite_template_ref_return rewrite_template_ref178 = null;
/*      */ 
/* 8440 */     rewrite_indirect_template_head_return rewrite_indirect_template_head179 = null;
/*      */ 
/* 8443 */     CommonTree lp_tree = null;
/* 8444 */     CommonTree str_tree = null;
/* 8445 */     CommonTree char_literal177_tree = null;
/* 8446 */     CommonTree ACTION180_tree = null;
/* 8447 */     RewriteRuleTokenStream stream_DOUBLE_QUOTE_STRING_LITERAL = new RewriteRuleTokenStream(this.adaptor, "token DOUBLE_QUOTE_STRING_LITERAL");
/* 8448 */     RewriteRuleTokenStream stream_83 = new RewriteRuleTokenStream(this.adaptor, "token 83");
/* 8449 */     RewriteRuleTokenStream stream_DOUBLE_ANGLE_STRING_LITERAL = new RewriteRuleTokenStream(this.adaptor, "token DOUBLE_ANGLE_STRING_LITERAL");
/* 8450 */     RewriteRuleTokenStream stream_81 = new RewriteRuleTokenStream(this.adaptor, "token 81");
/* 8451 */     RewriteRuleSubtreeStream stream_id = new RewriteRuleSubtreeStream(this.adaptor, "rule id");
/* 8452 */     RewriteRuleSubtreeStream stream_rewrite_template_args = new RewriteRuleSubtreeStream(this.adaptor, "rule rewrite_template_args");
/*      */     try
/*      */     {
/* 8455 */       int alt81 = 4;
/* 8456 */       alt81 = this.dfa81.predict(this.input);
/*      */       int alt80;
/*      */       rewrite_template_return localrewrite_template_return2;
/* 8457 */       switch (alt81)
/*      */       {
/*      */       case 1:
/* 8461 */         pushFollow(FOLLOW_id_in_rewrite_template3011);
/* 8462 */         id175 = id();
/*      */ 
/* 8464 */         this.state._fsp -= 1;
/*      */         rewrite_template_return localrewrite_template_return1;
/* 8465 */         if (this.state.failed) { localrewrite_template_return1 = retval; return localrewrite_template_return1; }
/* 8466 */         if (this.state.backtracking == 0) stream_id.add(id175.getTree());
/* 8467 */         lp = (Token)match(this.input, 81, FOLLOW_81_in_rewrite_template3015); if (this.state.failed) { localrewrite_template_return1 = retval; return localrewrite_template_return1; }
/* 8468 */         if (this.state.backtracking == 0) stream_81.add(lp);
/*      */ 
/* 8470 */         pushFollow(FOLLOW_rewrite_template_args_in_rewrite_template3017);
/* 8471 */         rewrite_template_args176 = rewrite_template_args();
/*      */ 
/* 8473 */         this.state._fsp -= 1;
/* 8474 */         if (this.state.failed) { localrewrite_template_return1 = retval; return localrewrite_template_return1; }
/* 8475 */         if (this.state.backtracking == 0) stream_rewrite_template_args.add(rewrite_template_args176.getTree());
/* 8476 */         char_literal177 = (Token)match(this.input, 83, FOLLOW_83_in_rewrite_template3019); if (this.state.failed) { localrewrite_template_return1 = retval; return localrewrite_template_return1; }
/* 8477 */         if (this.state.backtracking == 0) stream_83.add(char_literal177);
/*      */ 
/* 8480 */         alt80 = 2;
/*      */         Object nvae;
/* 8481 */         switch (this.input.LA(1))
/*      */         {
/*      */         case 52:
/* 8484 */           alt80 = 1;
/*      */ 
/* 8486 */           break;
/*      */         case 53:
/* 8489 */           alt80 = 2;
/*      */ 
/* 8491 */           break;
/*      */         default:
/* 8493 */           if (this.state.backtracking > 0) { this.state.failed = true; rewrite_template_return localrewrite_template_return3 = retval; return localrewrite_template_return3; }
/* 8494 */           nvae = new NoViableAltException("", 80, 0, this.input);
/*      */ 
/* 8497 */           throw ((Throwable)nvae);
/*      */         }
/*      */ 
/* 8500 */         switch (alt80)
/*      */         {
/*      */         case 1:
/* 8504 */           str = (Token)match(this.input, 52, FOLLOW_DOUBLE_QUOTE_STRING_LITERAL_in_rewrite_template3027); if (this.state.failed) { nvae = retval; return nvae; }
/* 8505 */           if (this.state.backtracking == 0) stream_DOUBLE_QUOTE_STRING_LITERAL.add(str); break;
/*      */         case 2:
/* 8513 */           str = (Token)match(this.input, 53, FOLLOW_DOUBLE_ANGLE_STRING_LITERAL_in_rewrite_template3033); if (this.state.failed) { nvae = retval; return nvae; }
/* 8514 */           if (this.state.backtracking == 0) stream_DOUBLE_ANGLE_STRING_LITERAL.add(str);
/*      */ 
/*      */           break;
/*      */         }
/*      */ 
/* 8531 */         if (this.state.backtracking == 0) {
/* 8532 */           retval.tree = root_0;
/* 8533 */           RewriteRuleTokenStream stream_str = new RewriteRuleTokenStream(this.adaptor, "token str", str);
/* 8534 */           RewriteRuleSubtreeStream stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.tree : null);
/*      */ 
/* 8536 */           root_0 = (CommonTree)this.adaptor.nil();
/*      */ 
/* 8541 */           CommonTree root_1 = (CommonTree)this.adaptor.nil();
/* 8542 */           root_1 = (CommonTree)this.adaptor.becomeRoot((CommonTree)this.adaptor.create(29, lp, "TEMPLATE"), root_1);
/*      */ 
/* 8544 */           this.adaptor.addChild(root_1, stream_id.nextTree());
/* 8545 */           this.adaptor.addChild(root_1, stream_rewrite_template_args.nextTree());
/* 8546 */           this.adaptor.addChild(root_1, stream_str.nextNode());
/*      */ 
/* 8548 */           this.adaptor.addChild(root_0, root_1);
/*      */ 
/* 8553 */           retval.tree = root_0;
/*      */         }
/* 8555 */         break;
/*      */       case 2:
/* 8559 */         root_0 = (CommonTree)this.adaptor.nil();
/*      */ 
/* 8561 */         pushFollow(FOLLOW_rewrite_template_ref_in_rewrite_template3060);
/* 8562 */         rewrite_template_ref178 = rewrite_template_ref();
/*      */ 
/* 8564 */         this.state._fsp -= 1;
/* 8565 */         if (this.state.failed) { localrewrite_template_return2 = retval; return localrewrite_template_return2; }
/* 8566 */         if (this.state.backtracking == 0) this.adaptor.addChild(root_0, rewrite_template_ref178.getTree()); break;
/*      */       case 3:
/* 8573 */         root_0 = (CommonTree)this.adaptor.nil();
/*      */ 
/* 8575 */         pushFollow(FOLLOW_rewrite_indirect_template_head_in_rewrite_template3069);
/* 8576 */         rewrite_indirect_template_head179 = rewrite_indirect_template_head();
/*      */ 
/* 8578 */         this.state._fsp -= 1;
/* 8579 */         if (this.state.failed) { localrewrite_template_return2 = retval; return localrewrite_template_return2; }
/* 8580 */         if (this.state.backtracking == 0) this.adaptor.addChild(root_0, rewrite_indirect_template_head179.getTree()); break;
/*      */       case 4:
/* 8587 */         root_0 = (CommonTree)this.adaptor.nil();
/*      */ 
/* 8589 */         ACTION180 = (Token)match(this.input, 47, FOLLOW_ACTION_in_rewrite_template3078); if (this.state.failed) { localrewrite_template_return2 = retval; return localrewrite_template_return2; }
/* 8590 */         if (this.state.backtracking == 0) {
/* 8591 */           ACTION180_tree = (CommonTree)this.adaptor.create(ACTION180);
/* 8592 */           this.adaptor.addChild(root_0, ACTION180_tree);
/*      */         }
/*      */ 
/*      */         break;
/*      */       }
/*      */ 
/* 8599 */       retval.stop = this.input.LT(-1);
/*      */ 
/* 8601 */       if (this.state.backtracking == 0)
/*      */       {
/* 8603 */         retval.tree = ((CommonTree)this.adaptor.rulePostProcessing(root_0));
/* 8604 */         this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
/*      */       }
/*      */     } catch (RecognitionException re) {
/* 8607 */       re = 
/* 8614 */         re;
/*      */ 
/* 8608 */       reportError(re);
/* 8609 */       recover(this.input, re);
/* 8610 */       retval.tree = ((CommonTree)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), re));
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/* 8615 */     return retval;
/*      */   }
/*      */ 
/*      */   public final rewrite_template_ref_return rewrite_template_ref()
/*      */     throws RecognitionException
/*      */   {
/* 8627 */     rewrite_template_ref_return retval = new rewrite_template_ref_return();
/* 8628 */     retval.start = this.input.LT(1);
/*      */ 
/* 8630 */     CommonTree root_0 = null;
/*      */ 
/* 8632 */     Token lp = null;
/* 8633 */     Token char_literal183 = null;
/* 8634 */     id_return id181 = null;
/*      */ 
/* 8636 */     rewrite_template_args_return rewrite_template_args182 = null;
/*      */ 
/* 8639 */     CommonTree lp_tree = null;
/* 8640 */     CommonTree char_literal183_tree = null;
/* 8641 */     RewriteRuleTokenStream stream_83 = new RewriteRuleTokenStream(this.adaptor, "token 83");
/* 8642 */     RewriteRuleTokenStream stream_81 = new RewriteRuleTokenStream(this.adaptor, "token 81");
/* 8643 */     RewriteRuleSubtreeStream stream_id = new RewriteRuleSubtreeStream(this.adaptor, "rule id");
/* 8644 */     RewriteRuleSubtreeStream stream_rewrite_template_args = new RewriteRuleSubtreeStream(this.adaptor, "rule rewrite_template_args");
/*      */     try
/*      */     {
/* 8649 */       pushFollow(FOLLOW_id_in_rewrite_template_ref3091);
/* 8650 */       id181 = id();
/*      */ 
/* 8652 */       this.state._fsp -= 1;
/*      */       rewrite_template_ref_return localrewrite_template_ref_return1;
/* 8653 */       if (this.state.failed) { localrewrite_template_ref_return1 = retval; return localrewrite_template_ref_return1; }
/* 8654 */       if (this.state.backtracking == 0) stream_id.add(id181.getTree());
/* 8655 */       lp = (Token)match(this.input, 81, FOLLOW_81_in_rewrite_template_ref3095); if (this.state.failed) { localrewrite_template_ref_return1 = retval; return localrewrite_template_ref_return1; }
/* 8656 */       if (this.state.backtracking == 0) stream_81.add(lp);
/*      */ 
/* 8658 */       pushFollow(FOLLOW_rewrite_template_args_in_rewrite_template_ref3097);
/* 8659 */       rewrite_template_args182 = rewrite_template_args();
/*      */ 
/* 8661 */       this.state._fsp -= 1;
/* 8662 */       if (this.state.failed) { localrewrite_template_ref_return1 = retval; return localrewrite_template_ref_return1; }
/* 8663 */       if (this.state.backtracking == 0) stream_rewrite_template_args.add(rewrite_template_args182.getTree());
/* 8664 */       char_literal183 = (Token)match(this.input, 83, FOLLOW_83_in_rewrite_template_ref3099); if (this.state.failed) { localrewrite_template_ref_return1 = retval; return localrewrite_template_ref_return1; }
/* 8665 */       if (this.state.backtracking == 0) stream_83.add(char_literal183);
/*      */ 
/* 8676 */       if (this.state.backtracking == 0) {
/* 8677 */         retval.tree = root_0;
/* 8678 */         RewriteRuleSubtreeStream stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.tree : null);
/*      */ 
/* 8680 */         root_0 = (CommonTree)this.adaptor.nil();
/*      */ 
/* 8685 */         CommonTree root_1 = (CommonTree)this.adaptor.nil();
/* 8686 */         root_1 = (CommonTree)this.adaptor.becomeRoot((CommonTree)this.adaptor.create(29, lp, "TEMPLATE"), root_1);
/*      */ 
/* 8688 */         this.adaptor.addChild(root_1, stream_id.nextTree());
/* 8689 */         this.adaptor.addChild(root_1, stream_rewrite_template_args.nextTree());
/*      */ 
/* 8691 */         this.adaptor.addChild(root_0, root_1);
/*      */ 
/* 8696 */         retval.tree = root_0;
/*      */       }
/*      */ 
/* 8699 */       retval.stop = this.input.LT(-1);
/*      */ 
/* 8701 */       if (this.state.backtracking == 0)
/*      */       {
/* 8703 */         retval.tree = ((CommonTree)this.adaptor.rulePostProcessing(root_0));
/* 8704 */         this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
/*      */       }
/*      */     } catch (RecognitionException re) {
/* 8707 */       re = 
/* 8714 */         re;
/*      */ 
/* 8708 */       reportError(re);
/* 8709 */       recover(this.input, re);
/* 8710 */       retval.tree = ((CommonTree)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), re));
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/* 8715 */     return retval;
/*      */   }
/*      */ 
/*      */   public final rewrite_indirect_template_head_return rewrite_indirect_template_head()
/*      */     throws RecognitionException
/*      */   {
/* 8727 */     rewrite_indirect_template_head_return retval = new rewrite_indirect_template_head_return();
/* 8728 */     retval.start = this.input.LT(1);
/*      */ 
/* 8730 */     CommonTree root_0 = null;
/*      */ 
/* 8732 */     Token lp = null;
/* 8733 */     Token ACTION184 = null;
/* 8734 */     Token char_literal185 = null;
/* 8735 */     Token char_literal186 = null;
/* 8736 */     Token char_literal188 = null;
/* 8737 */     rewrite_template_args_return rewrite_template_args187 = null;
/*      */ 
/* 8740 */     CommonTree lp_tree = null;
/* 8741 */     CommonTree ACTION184_tree = null;
/* 8742 */     CommonTree char_literal185_tree = null;
/* 8743 */     CommonTree char_literal186_tree = null;
/* 8744 */     CommonTree char_literal188_tree = null;
/* 8745 */     RewriteRuleTokenStream stream_83 = new RewriteRuleTokenStream(this.adaptor, "token 83");
/* 8746 */     RewriteRuleTokenStream stream_81 = new RewriteRuleTokenStream(this.adaptor, "token 81");
/* 8747 */     RewriteRuleTokenStream stream_ACTION = new RewriteRuleTokenStream(this.adaptor, "token ACTION");
/* 8748 */     RewriteRuleSubtreeStream stream_rewrite_template_args = new RewriteRuleSubtreeStream(this.adaptor, "rule rewrite_template_args");
/*      */     try
/*      */     {
/* 8753 */       lp = (Token)match(this.input, 81, FOLLOW_81_in_rewrite_indirect_template_head3127);
/*      */       rewrite_indirect_template_head_return localrewrite_indirect_template_head_return1;
/* 8753 */       if (this.state.failed) { localrewrite_indirect_template_head_return1 = retval; return localrewrite_indirect_template_head_return1; }
/* 8754 */       if (this.state.backtracking == 0) stream_81.add(lp);
/*      */ 
/* 8756 */       ACTION184 = (Token)match(this.input, 47, FOLLOW_ACTION_in_rewrite_indirect_template_head3129); if (this.state.failed) { localrewrite_indirect_template_head_return1 = retval; return localrewrite_indirect_template_head_return1; }
/* 8757 */       if (this.state.backtracking == 0) stream_ACTION.add(ACTION184);
/*      */ 
/* 8759 */       char_literal185 = (Token)match(this.input, 83, FOLLOW_83_in_rewrite_indirect_template_head3131); if (this.state.failed) { localrewrite_indirect_template_head_return1 = retval; return localrewrite_indirect_template_head_return1; }
/* 8760 */       if (this.state.backtracking == 0) stream_83.add(char_literal185);
/*      */ 
/* 8762 */       char_literal186 = (Token)match(this.input, 81, FOLLOW_81_in_rewrite_indirect_template_head3133); if (this.state.failed) { localrewrite_indirect_template_head_return1 = retval; return localrewrite_indirect_template_head_return1; }
/* 8763 */       if (this.state.backtracking == 0) stream_81.add(char_literal186);
/*      */ 
/* 8765 */       pushFollow(FOLLOW_rewrite_template_args_in_rewrite_indirect_template_head3135);
/* 8766 */       rewrite_template_args187 = rewrite_template_args();
/*      */ 
/* 8768 */       this.state._fsp -= 1;
/* 8769 */       if (this.state.failed) { localrewrite_indirect_template_head_return1 = retval; return localrewrite_indirect_template_head_return1; }
/* 8770 */       if (this.state.backtracking == 0) stream_rewrite_template_args.add(rewrite_template_args187.getTree());
/* 8771 */       char_literal188 = (Token)match(this.input, 83, FOLLOW_83_in_rewrite_indirect_template_head3137); if (this.state.failed) { localrewrite_indirect_template_head_return1 = retval; return localrewrite_indirect_template_head_return1; }
/* 8772 */       if (this.state.backtracking == 0) stream_83.add(char_literal188);
/*      */ 
/* 8783 */       if (this.state.backtracking == 0) {
/* 8784 */         retval.tree = root_0;
/* 8785 */         RewriteRuleSubtreeStream stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.tree : null);
/*      */ 
/* 8787 */         root_0 = (CommonTree)this.adaptor.nil();
/*      */ 
/* 8792 */         CommonTree root_1 = (CommonTree)this.adaptor.nil();
/* 8793 */         root_1 = (CommonTree)this.adaptor.becomeRoot((CommonTree)this.adaptor.create(29, lp, "TEMPLATE"), root_1);
/*      */ 
/* 8795 */         this.adaptor.addChild(root_1, stream_ACTION.nextNode());
/* 8796 */         this.adaptor.addChild(root_1, stream_rewrite_template_args.nextTree());
/*      */ 
/* 8798 */         this.adaptor.addChild(root_0, root_1);
/*      */ 
/* 8803 */         retval.tree = root_0;
/*      */       }
/*      */ 
/* 8806 */       retval.stop = this.input.LT(-1);
/*      */ 
/* 8808 */       if (this.state.backtracking == 0)
/*      */       {
/* 8810 */         retval.tree = ((CommonTree)this.adaptor.rulePostProcessing(root_0));
/* 8811 */         this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
/*      */       }
/*      */     } catch (RecognitionException re) {
/* 8814 */       re = 
/* 8821 */         re;
/*      */ 
/* 8815 */       reportError(re);
/* 8816 */       recover(this.input, re);
/* 8817 */       retval.tree = ((CommonTree)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), re));
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/* 8822 */     return retval;
/*      */   }
/*      */ 
/*      */   public final rewrite_template_args_return rewrite_template_args()
/*      */     throws RecognitionException
/*      */   {
/* 8834 */     rewrite_template_args_return retval = new rewrite_template_args_return();
/* 8835 */     retval.start = this.input.LT(1);
/*      */ 
/* 8837 */     CommonTree root_0 = null;
/*      */ 
/* 8839 */     Token char_literal190 = null;
/* 8840 */     rewrite_template_arg_return rewrite_template_arg189 = null;
/*      */ 
/* 8842 */     rewrite_template_arg_return rewrite_template_arg191 = null;
/*      */ 
/* 8845 */     CommonTree char_literal190_tree = null;
/* 8846 */     RewriteRuleTokenStream stream_80 = new RewriteRuleTokenStream(this.adaptor, "token 80");
/* 8847 */     RewriteRuleSubtreeStream stream_rewrite_template_arg = new RewriteRuleSubtreeStream(this.adaptor, "rule rewrite_template_arg");
/*      */     try
/*      */     {
/* 8850 */       int alt83 = 2;
/*      */       Object nvae;
/* 8851 */       switch (this.input.LA(1))
/*      */       {
/*      */       case 44:
/*      */       case 51:
/* 8855 */         alt83 = 1;
/*      */ 
/* 8857 */         break;
/*      */       case 83:
/* 8860 */         alt83 = 2;
/*      */ 
/* 8862 */         break;
/*      */       default:
/* 8864 */         if (this.state.backtracking > 0) { this.state.failed = true; rewrite_template_args_return localrewrite_template_args_return1 = retval; return localrewrite_template_args_return1; }
/* 8865 */         nvae = new NoViableAltException("", 83, 0, this.input);
/*      */ 
/* 8868 */         throw ((Throwable)nvae);
/*      */       }
/*      */ 
/* 8871 */       switch (alt83)
/*      */       {
/*      */       case 1:
/* 8875 */         pushFollow(FOLLOW_rewrite_template_arg_in_rewrite_template_args3161);
/* 8876 */         rewrite_template_arg189 = rewrite_template_arg();
/*      */ 
/* 8878 */         this.state._fsp -= 1;
/* 8879 */         if (this.state.failed) { nvae = retval; return nvae; }
/* 8880 */         if (this.state.backtracking == 0) stream_rewrite_template_arg.add(rewrite_template_arg189.getTree());
/*      */ 
/*      */         while (true)
/*      */         {
/* 8884 */           int alt82 = 2;
/* 8885 */           switch (this.input.LA(1))
/*      */           {
/*      */           case 80:
/* 8888 */             alt82 = 1;
/*      */           }
/*      */ 
/* 8894 */           switch (alt82)
/*      */           {
/*      */           case 1:
/* 8898 */             char_literal190 = (Token)match(this.input, 80, FOLLOW_80_in_rewrite_template_args3164);
/*      */             rewrite_template_args_return localrewrite_template_args_return2;
/* 8898 */             if (this.state.failed) { localrewrite_template_args_return2 = retval; return localrewrite_template_args_return2; }
/* 8899 */             if (this.state.backtracking == 0) stream_80.add(char_literal190);
/*      */ 
/* 8901 */             pushFollow(FOLLOW_rewrite_template_arg_in_rewrite_template_args3166);
/* 8902 */             rewrite_template_arg191 = rewrite_template_arg();
/*      */ 
/* 8904 */             this.state._fsp -= 1;
/* 8905 */             if (this.state.failed) { localrewrite_template_args_return2 = retval; return localrewrite_template_args_return2; }
/* 8906 */             if (this.state.backtracking == 0) stream_rewrite_template_arg.add(rewrite_template_arg191.getTree()); break;
/*      */           default:
/* 8912 */             break label436;
/*      */           }
/*      */ 
/*      */         }
/*      */ 
/* 8925 */         if (this.state.backtracking == 0) {
/* 8926 */           retval.tree = root_0;
/* 8927 */           RewriteRuleSubtreeStream stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.tree : null);
/*      */ 
/* 8929 */           root_0 = (CommonTree)this.adaptor.nil();
/*      */ 
/* 8934 */           CommonTree root_1 = (CommonTree)this.adaptor.nil();
/* 8935 */           root_1 = (CommonTree)this.adaptor.becomeRoot((CommonTree)this.adaptor.create(22, "ARGLIST"), root_1);
/*      */ 
/* 8937 */           if (!stream_rewrite_template_arg.hasNext()) {
/* 8938 */             throw new RewriteEarlyExitException();
/*      */           }
/* 8940 */           while (stream_rewrite_template_arg.hasNext()) {
/* 8941 */             this.adaptor.addChild(root_1, stream_rewrite_template_arg.nextTree());
/*      */           }
/*      */ 
/* 8944 */           stream_rewrite_template_arg.reset();
/*      */ 
/* 8946 */           this.adaptor.addChild(root_0, root_1);
/*      */ 
/* 8951 */           retval.tree = root_0; } break;
/*      */       case 2:
/* 8965 */         label436: if (this.state.backtracking == 0) {
/* 8966 */           retval.tree = root_0;
/* 8967 */           RewriteRuleSubtreeStream stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.tree : null);
/*      */ 
/* 8969 */           root_0 = (CommonTree)this.adaptor.nil();
/*      */ 
/* 8972 */           this.adaptor.addChild(root_0, (CommonTree)this.adaptor.create(22, "ARGLIST"));
/*      */ 
/* 8976 */           retval.tree = root_0;
/*      */         }
/*      */         break;
/*      */       }
/*      */ 
/* 8981 */       retval.stop = this.input.LT(-1);
/*      */ 
/* 8983 */       if (this.state.backtracking == 0)
/*      */       {
/* 8985 */         retval.tree = ((CommonTree)this.adaptor.rulePostProcessing(root_0));
/* 8986 */         this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
/*      */       }
/*      */     } catch (RecognitionException re) {
/* 8989 */       re = 
/* 8996 */         re;
/*      */ 
/* 8990 */       reportError(re);
/* 8991 */       recover(this.input, re);
/* 8992 */       retval.tree = ((CommonTree)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), re));
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/* 8997 */     return retval;
/*      */   }
/*      */ 
/*      */   public final rewrite_template_arg_return rewrite_template_arg()
/*      */     throws RecognitionException
/*      */   {
/* 9009 */     rewrite_template_arg_return retval = new rewrite_template_arg_return();
/* 9010 */     retval.start = this.input.LT(1);
/*      */ 
/* 9012 */     CommonTree root_0 = null;
/*      */ 
/* 9014 */     Token char_literal193 = null;
/* 9015 */     Token ACTION194 = null;
/* 9016 */     id_return id192 = null;
/*      */ 
/* 9019 */     CommonTree char_literal193_tree = null;
/* 9020 */     CommonTree ACTION194_tree = null;
/* 9021 */     RewriteRuleTokenStream stream_LABEL_ASSIGN = new RewriteRuleTokenStream(this.adaptor, "token LABEL_ASSIGN");
/* 9022 */     RewriteRuleTokenStream stream_ACTION = new RewriteRuleTokenStream(this.adaptor, "token ACTION");
/* 9023 */     RewriteRuleSubtreeStream stream_id = new RewriteRuleSubtreeStream(this.adaptor, "rule id");
/*      */     try
/*      */     {
/* 9028 */       pushFollow(FOLLOW_id_in_rewrite_template_arg3199);
/* 9029 */       id192 = id();
/*      */ 
/* 9031 */       this.state._fsp -= 1;
/*      */       rewrite_template_arg_return localrewrite_template_arg_return1;
/* 9032 */       if (this.state.failed) { localrewrite_template_arg_return1 = retval; return localrewrite_template_arg_return1; }
/* 9033 */       if (this.state.backtracking == 0) stream_id.add(id192.getTree());
/* 9034 */       char_literal193 = (Token)match(this.input, 41, FOLLOW_LABEL_ASSIGN_in_rewrite_template_arg3201); if (this.state.failed) { localrewrite_template_arg_return1 = retval; return localrewrite_template_arg_return1; }
/* 9035 */       if (this.state.backtracking == 0) stream_LABEL_ASSIGN.add(char_literal193);
/*      */ 
/* 9037 */       ACTION194 = (Token)match(this.input, 47, FOLLOW_ACTION_in_rewrite_template_arg3203); if (this.state.failed) { localrewrite_template_arg_return1 = retval; return localrewrite_template_arg_return1; }
/* 9038 */       if (this.state.backtracking == 0) stream_ACTION.add(ACTION194);
/*      */ 
/* 9049 */       if (this.state.backtracking == 0) {
/* 9050 */         retval.tree = root_0;
/* 9051 */         RewriteRuleSubtreeStream stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.tree : null);
/*      */ 
/* 9053 */         root_0 = (CommonTree)this.adaptor.nil();
/*      */ 
/* 9058 */         CommonTree root_1 = (CommonTree)this.adaptor.nil();
/* 9059 */         root_1 = (CommonTree)this.adaptor.becomeRoot((CommonTree)this.adaptor.create(21, id192 != null ? id192.start : null), root_1);
/*      */ 
/* 9061 */         this.adaptor.addChild(root_1, stream_id.nextTree());
/* 9062 */         this.adaptor.addChild(root_1, stream_ACTION.nextNode());
/*      */ 
/* 9064 */         this.adaptor.addChild(root_0, root_1);
/*      */ 
/* 9069 */         retval.tree = root_0;
/*      */       }
/*      */ 
/* 9072 */       retval.stop = this.input.LT(-1);
/*      */ 
/* 9074 */       if (this.state.backtracking == 0)
/*      */       {
/* 9076 */         retval.tree = ((CommonTree)this.adaptor.rulePostProcessing(root_0));
/* 9077 */         this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
/*      */       }
/*      */     } catch (RecognitionException re) {
/* 9080 */       re = 
/* 9087 */         re;
/*      */ 
/* 9081 */       reportError(re);
/* 9082 */       recover(this.input, re);
/* 9083 */       retval.tree = ((CommonTree)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), re));
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/* 9088 */     return retval;
/*      */   }
/*      */ 
/*      */   public final qid_return qid()
/*      */     throws RecognitionException
/*      */   {
/* 9100 */     qid_return retval = new qid_return();
/* 9101 */     retval.start = this.input.LT(1);
/*      */ 
/* 9103 */     CommonTree root_0 = null;
/*      */ 
/* 9105 */     Token char_literal196 = null;
/* 9106 */     id_return id195 = null;
/*      */ 
/* 9108 */     id_return id197 = null;
/*      */ 
/* 9111 */     CommonTree char_literal196_tree = null;
/*      */     try
/*      */     {
/* 9117 */       root_0 = (CommonTree)this.adaptor.nil();
/*      */ 
/* 9119 */       pushFollow(FOLLOW_id_in_qid3224);
/* 9120 */       id195 = id();
/*      */ 
/* 9122 */       this.state._fsp -= 1;
/* 9123 */       if (this.state.failed) { qid_return localqid_return1 = retval; return localqid_return1; }
/* 9124 */       if (this.state.backtracking == 0) this.adaptor.addChild(root_0, id195.getTree());
/*      */ 
/*      */       while (true)
/*      */       {
/* 9128 */         int alt84 = 2;
/* 9129 */         switch (this.input.LA(1))
/*      */         {
/*      */         case 90:
/* 9132 */           alt84 = 1;
/*      */         }
/*      */ 
/* 9138 */         switch (alt84)
/*      */         {
/*      */         case 1:
/* 9142 */           char_literal196 = (Token)match(this.input, 90, FOLLOW_90_in_qid3227);
/*      */           qid_return localqid_return2;
/* 9142 */           if (this.state.failed) { localqid_return2 = retval; return localqid_return2; }
/* 9143 */           if (this.state.backtracking == 0) {
/* 9144 */             char_literal196_tree = (CommonTree)this.adaptor.create(char_literal196);
/* 9145 */             this.adaptor.addChild(root_0, char_literal196_tree);
/*      */           }
/* 9147 */           pushFollow(FOLLOW_id_in_qid3229);
/* 9148 */           id197 = id();
/*      */ 
/* 9150 */           this.state._fsp -= 1;
/* 9151 */           if (this.state.failed) { localqid_return2 = retval; return localqid_return2; }
/* 9152 */           if (this.state.backtracking == 0) this.adaptor.addChild(root_0, id197.getTree()); break;
/*      */         default:
/* 9158 */           break label318;
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/* 9165 */       label318: retval.stop = this.input.LT(-1);
/*      */ 
/* 9167 */       if (this.state.backtracking == 0)
/*      */       {
/* 9169 */         retval.tree = ((CommonTree)this.adaptor.rulePostProcessing(root_0));
/* 9170 */         this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
/*      */       }
/*      */     } catch (RecognitionException re) {
/* 9173 */       re = 
/* 9180 */         re;
/*      */ 
/* 9174 */       reportError(re);
/* 9175 */       recover(this.input, re);
/* 9176 */       retval.tree = ((CommonTree)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), re));
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/* 9181 */     return retval;
/*      */   }
/*      */ 
/*      */   public final id_return id()
/*      */     throws RecognitionException
/*      */   {
/* 9193 */     id_return retval = new id_return();
/* 9194 */     retval.start = this.input.LT(1);
/*      */ 
/* 9196 */     CommonTree root_0 = null;
/*      */ 
/* 9198 */     Token TOKEN_REF198 = null;
/* 9199 */     Token RULE_REF199 = null;
/*      */ 
/* 9201 */     CommonTree TOKEN_REF198_tree = null;
/* 9202 */     CommonTree RULE_REF199_tree = null;
/* 9203 */     RewriteRuleTokenStream stream_RULE_REF = new RewriteRuleTokenStream(this.adaptor, "token RULE_REF");
/* 9204 */     RewriteRuleTokenStream stream_TOKEN_REF = new RewriteRuleTokenStream(this.adaptor, "token TOKEN_REF");
/*      */     try
/*      */     {
/* 9208 */       int alt85 = 2;
/*      */       Object nvae;
/* 9209 */       switch (this.input.LA(1))
/*      */       {
/*      */       case 44:
/* 9212 */         alt85 = 1;
/*      */ 
/* 9214 */         break;
/*      */       case 51:
/* 9217 */         alt85 = 2;
/*      */ 
/* 9219 */         break;
/*      */       default:
/* 9221 */         if (this.state.backtracking > 0) { this.state.failed = true; id_return localid_return1 = retval; return localid_return1; }
/* 9222 */         nvae = new NoViableAltException("", 85, 0, this.input);
/*      */ 
/* 9225 */         throw ((Throwable)nvae);
/*      */       }
/*      */       Object stream_retval;
/* 9228 */       switch (alt85)
/*      */       {
/*      */       case 1:
/* 9232 */         TOKEN_REF198 = (Token)match(this.input, 44, FOLLOW_TOKEN_REF_in_id3241); if (this.state.failed) { nvae = retval; return nvae; }
/* 9233 */         if (this.state.backtracking == 0) stream_TOKEN_REF.add(TOKEN_REF198);
/*      */ 
/* 9244 */         if (this.state.backtracking == 0) {
/* 9245 */           retval.tree = root_0;
/* 9246 */           stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.tree : null);
/*      */ 
/* 9248 */           root_0 = (CommonTree)this.adaptor.nil();
/*      */ 
/* 9251 */           this.adaptor.addChild(root_0, (CommonTree)this.adaptor.create(20, TOKEN_REF198));
/*      */ 
/* 9255 */           retval.tree = root_0; } break;
/*      */       case 2:
/* 9261 */         RULE_REF199 = (Token)match(this.input, 51, FOLLOW_RULE_REF_in_id3251); if (this.state.failed) { stream_retval = retval; return stream_retval; }
/* 9262 */         if (this.state.backtracking == 0) stream_RULE_REF.add(RULE_REF199);
/*      */ 
/* 9273 */         if (this.state.backtracking == 0) {
/* 9274 */           retval.tree = root_0;
/* 9275 */           RewriteRuleSubtreeStream stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.tree : null);
/*      */ 
/* 9277 */           root_0 = (CommonTree)this.adaptor.nil();
/*      */ 
/* 9280 */           this.adaptor.addChild(root_0, (CommonTree)this.adaptor.create(20, RULE_REF199));
/*      */ 
/* 9284 */           retval.tree = root_0;
/*      */         }
/*      */         break;
/*      */       }
/*      */ 
/* 9289 */       retval.stop = this.input.LT(-1);
/*      */ 
/* 9291 */       if (this.state.backtracking == 0)
/*      */       {
/* 9293 */         retval.tree = ((CommonTree)this.adaptor.rulePostProcessing(root_0));
/* 9294 */         this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
/*      */       }
/*      */     } catch (RecognitionException re) {
/* 9297 */       re = 
/* 9304 */         re;
/*      */ 
/* 9298 */       reportError(re);
/* 9299 */       recover(this.input, re);
/* 9300 */       retval.tree = ((CommonTree)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), re));
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/* 9305 */     return retval;
/*      */   }
/*      */ 
/*      */   public final void synpred1_ANTLRv3_fragment()
/*      */     throws RecognitionException
/*      */   {
/* 9314 */     pushFollow(FOLLOW_rewrite_template_in_synpred1_ANTLRv32648);
/* 9315 */     rewrite_template();
/*      */ 
/* 9317 */     this.state._fsp -= 1;
/* 9318 */     if (this.state.failed);
/*      */   }
/*      */ 
/*      */   public final void synpred2_ANTLRv3_fragment()
/*      */     throws RecognitionException
/*      */   {
/* 9329 */     pushFollow(FOLLOW_rewrite_tree_alternative_in_synpred2_ANTLRv32653);
/* 9330 */     rewrite_tree_alternative();
/*      */ 
/* 9332 */     this.state._fsp -= 1;
/* 9333 */     if (this.state.failed);
/*      */   }
/*      */ 
/*      */   public final boolean synpred2_ANTLRv3()
/*      */   {
/* 9342 */     this.state.backtracking += 1;
/* 9343 */     int start = this.input.mark();
/*      */     try {
/* 9345 */       synpred2_ANTLRv3_fragment();
/*      */     } catch (RecognitionException re) {
/* 9347 */       System.err.println("impossible: " + re);
/*      */     }
/* 9349 */     boolean success = !this.state.failed;
/* 9350 */     this.input.rewind(start);
/* 9351 */     this.state.backtracking -= 1;
/* 9352 */     this.state.failed = false;
/* 9353 */     return success;
/*      */   }
/*      */   public final boolean synpred1_ANTLRv3() {
/* 9356 */     this.state.backtracking += 1;
/* 9357 */     int start = this.input.mark();
/*      */     try {
/* 9359 */       synpred1_ANTLRv3_fragment();
/*      */     } catch (RecognitionException re) {
/* 9361 */       System.err.println("impossible: " + re);
/*      */     }
/* 9363 */     boolean success = !this.state.failed;
/* 9364 */     this.input.rewind(start);
/* 9365 */     this.state.backtracking -= 1;
/* 9366 */     this.state.failed = false;
/* 9367 */     return success;
/*      */   }
/*      */ 
/*      */   static
/*      */   {
/* 9416 */     int numStates = DFA46_transitionS.length;
/* 9417 */     DFA46_transition = new short[numStates][];
/* 9418 */     for (int i = 0; i < numStates; i++) {
/* 9419 */       DFA46_transition[i] = DFA.unpackEncodedString(DFA46_transitionS[i]);
/*      */     }
/*      */ 
/* 9452 */     DFA73_transitionS = new String[] { "\001\005\002ð¿¿\001\006\004ð¿¿\001\001\002\005\001\004\003ð¿¿\001\002\023ð¿¿\001\006\tð¿¿\001\003\002\006\tð¿¿\001\005", "\001\005\002ð¿¿\001\005\004ð¿¿\004\005\002ð¿¿\002\005\023ð¿¿\001\005\002ð¿¿\001\005\006ð¿¿\001\007\002\005\007ð¿¿\003\005", "\001\005\002ð¿¿\001\005\004ð¿¿\004\005\003ð¿¿\001\005\023ð¿¿\001\005\002ð¿¿\001\005\006ð¿¿\001\007\002\005\007ð¿¿\003\005", "\001\005\007ð¿¿\003\005\001\b\003ð¿¿\001\005\035ð¿¿\001\005\013ð¿¿\001\005", "\001ð¿¿", "", "", "\001\005\007ð¿¿\001\n\003\005\003ð¿¿\001\013\035ð¿¿\001\005\001ð¿¿\001\t\tð¿¿\001\005", "\001\005\007ð¿¿\004\005\003ð¿¿\001\005\026ð¿¿\001\005\006ð¿¿\001\005\001ð¿¿\001\f\007ð¿¿\003\005", "", "\001\005\004ð¿¿\001\t\002ð¿¿\004\005\002ð¿¿\002\005\026ð¿¿\001\005\006ð¿¿\001\005\001ð¿¿\001\005\007ð¿¿\003\005", "\001\005\004ð¿¿\001\t\002ð¿¿\004\005\003ð¿¿\001\005\026ð¿¿\001\005\006ð¿¿\001\005\001ð¿¿\001\005\007ð¿¿\003\005", "\001\005\006ð¿¿\001\t\tð¿¿\002\005" };
/*      */ 
/* 9475 */     DFA73_eot = DFA.unpackEncodedString("\rð¿¿");
/* 9476 */     DFA73_eof = DFA.unpackEncodedString("\rð¿¿");
/* 9477 */     DFA73_min = DFA.unpackEncodedStringToUnsignedChars("");
/* 9478 */     DFA73_max = DFA.unpackEncodedStringToUnsignedChars("");
/* 9479 */     DFA73_accept = DFA.unpackEncodedString("\005ð¿¿\001\002\001\003\002ð¿¿\001\001\003ð¿¿");
/* 9480 */     DFA73_special = DFA.unpackEncodedString("");
/*      */ 
/* 9484 */     int numStates = DFA73_transitionS.length;
/* 9485 */     DFA73_transition = new short[numStates][];
/* 9486 */     for (int i = 0; i < numStates; i++) {
/* 9487 */       DFA73_transition[i] = DFA.unpackEncodedString(DFA73_transitionS[i]);
/*      */     }
/*      */ 
/* 9546 */     DFA76_transitionS = new String[] { "\001\007\007ð¿¿\001\002\001\004\001\001\001\006\003ð¿¿\001\003\035ð¿¿\001\b\013ð¿¿\001\005", "\001\n\002ð¿¿\001\n\004ð¿¿\004\n\003ð¿¿\001\n\023ð¿¿\001\n\002ð¿¿\001\t\006ð¿¿\003\n\007ð¿¿\002\t\001\n", "\001\n\002ð¿¿\001\n\004ð¿¿\004\n\002ð¿¿\001\013\001\n\023ð¿¿\001\n\002ð¿¿\001\t\006ð¿¿\003\n\007ð¿¿\002\t\001\n", "\001\n\002ð¿¿\001\n\004ð¿¿\004\n\003ð¿¿\001\n\023ð¿¿\001\n\002ð¿¿\001\t\006ð¿¿\003\n\007ð¿¿\002\t\001\n", "\001\n\002ð¿¿\001\n\004ð¿¿\004\n\003ð¿¿\001\n\023ð¿¿\001\n\002ð¿¿\001\t\006ð¿¿\003\n\007ð¿¿\002\t\001\n", "\001\f\006ð¿¿\001\r", "\001\n\002ð¿¿\001\n\004ð¿¿\004\n\003ð¿¿\001\n\023ð¿¿\001\n\002ð¿¿\001\t\006ð¿¿\003\n\007ð¿¿\002\t\001\n", "", "", "", "", "\001\n\002ð¿¿\001\n\004ð¿¿\004\n\003ð¿¿\001\n\023ð¿¿\001\n\002ð¿¿\001\t\006ð¿¿\003\n\007ð¿¿\002\t\001\n", "\001\n\002ð¿¿\001\n\004ð¿¿\004\n\003ð¿¿\001\n\023ð¿¿\001\n\002ð¿¿\001\t\006ð¿¿\003\n\007ð¿¿\002\t\001\n", "\001\n\002ð¿¿\001\n\004ð¿¿\004\n\003ð¿¿\001\n\023ð¿¿\001\n\002ð¿¿\001\t\006ð¿¿\003\n\007ð¿¿\002\t\001\n" };
/*      */ 
/* 9572 */     DFA76_eot = DFA.unpackEncodedString("\016ð¿¿");
/* 9573 */     DFA76_eof = DFA.unpackEncodedString("\001ð¿¿\004\n\001ð¿¿\001\n\004ð¿¿\003\n");
/* 9574 */     DFA76_min = DFA.unpackEncodedStringToUnsignedChars("\005$\001,\001$\004ð¿¿\003$");
/* 9575 */     DFA76_max = DFA.unpackEncodedStringToUnsignedChars("\005]\0013\001]\004ð¿¿\003]");
/* 9576 */     DFA76_accept = DFA.unpackEncodedString("\007ð¿¿\001\003\001\004\001\002\001\001\003ð¿¿");
/* 9577 */     DFA76_special = DFA.unpackEncodedString("\016ð¿¿}>");
/*      */ 
/* 9581 */     int numStates = DFA76_transitionS.length;
/* 9582 */     DFA76_transition = new short[numStates][];
/* 9583 */     for (int i = 0; i < numStates; i++) {
/* 9584 */       DFA76_transition[i] = DFA.unpackEncodedString(DFA76_transitionS[i]);
/*      */     }
/*      */ 
/* 9619 */     DFA81_transitionS = new String[] { "\001\001\002ð¿¿\001\004\003ð¿¿\001\002\035ð¿¿\001\003", "\001\005", "\001\005", "", "", "\001\006\006ð¿¿\001\007\037ð¿¿\001\b", "\001\t", "\001\t", "\001\n\fð¿¿\002\013\021ð¿¿\001\n\nð¿¿\002\n", "\001\f", "", "", "\001\r\002ð¿¿\001\b", "\001\016\006ð¿¿\001\017", "\001\020", "\001\020", "\001\021", "\001\r\002ð¿¿\001\b" };
/*      */ 
/* 9640 */     DFA81_eot = DFA.unpackEncodedString("\022ð¿¿");
/* 9641 */     DFA81_eof = DFA.unpackEncodedString("\bð¿¿\001\n\tð¿¿");
/* 9642 */     DFA81_min = DFA.unpackEncodedStringToUnsignedChars("\001,\002Q\002ð¿¿\001,\002)\001'\001/\002ð¿¿\001P\001,\002)\001/\001P");
/* 9643 */     DFA81_max = DFA.unpackEncodedStringToUnsignedChars("\003Q\002ð¿¿\001S\002)\001S\001/\002ð¿¿\001S\0013\002)\001/\001S");
/* 9644 */     DFA81_accept = DFA.unpackEncodedString("\003ð¿¿\001\003\001\004\005ð¿¿\001\002\001\001\006ð¿¿");
/* 9645 */     DFA81_special = DFA.unpackEncodedString("\022ð¿¿}>");
/*      */ 
/* 9649 */     int numStates = DFA81_transitionS.length;
/* 9650 */     DFA81_transition = new short[numStates][];
/* 9651 */     for (int i = 0; i < numStates; i++)
/* 9652 */       DFA81_transition[i] = DFA.unpackEncodedString(DFA81_transitionS[i]);
/*      */   }
/*      */ 
/*      */   class DFA81 extends DFA
/*      */   {
/*      */     public DFA81(BaseRecognizer recognizer)
/*      */     {
/* 9659 */       this.recognizer = recognizer;
/* 9660 */       this.decisionNumber = 81;
/* 9661 */       this.eot = ANTLRv3Parser.DFA81_eot;
/* 9662 */       this.eof = ANTLRv3Parser.DFA81_eof;
/* 9663 */       this.min = ANTLRv3Parser.DFA81_min;
/* 9664 */       this.max = ANTLRv3Parser.DFA81_max;
/* 9665 */       this.accept = ANTLRv3Parser.DFA81_accept;
/* 9666 */       this.special = ANTLRv3Parser.DFA81_special;
/* 9667 */       this.transition = ANTLRv3Parser.DFA81_transition;
/*      */     }
/*      */     public String getDescription() {
/* 9670 */       return "413:1: rewrite_template : ( id lp= '(' rewrite_template_args ')' (str= DOUBLE_QUOTE_STRING_LITERAL | str= DOUBLE_ANGLE_STRING_LITERAL ) -> ^( TEMPLATE[$lp,\"TEMPLATE\"] id rewrite_template_args $str) | rewrite_template_ref | rewrite_indirect_template_head | ACTION );";
/*      */     }
/*      */   }
/*      */ 
/*      */   class DFA76 extends DFA
/*      */   {
/*      */     public DFA76(BaseRecognizer recognizer)
/*      */     {
/* 9591 */       this.recognizer = recognizer;
/* 9592 */       this.decisionNumber = 76;
/* 9593 */       this.eot = ANTLRv3Parser.DFA76_eot;
/* 9594 */       this.eof = ANTLRv3Parser.DFA76_eof;
/* 9595 */       this.min = ANTLRv3Parser.DFA76_min;
/* 9596 */       this.max = ANTLRv3Parser.DFA76_max;
/* 9597 */       this.accept = ANTLRv3Parser.DFA76_accept;
/* 9598 */       this.special = ANTLRv3Parser.DFA76_special;
/* 9599 */       this.transition = ANTLRv3Parser.DFA76_transition;
/*      */     }
/*      */     public String getDescription() {
/* 9602 */       return "376:1: rewrite_tree_element : ( rewrite_tree_atom | rewrite_tree_atom ebnfSuffix -> ^( ebnfSuffix ^( BLOCK[\"BLOCK\"] ^( ALT[\"ALT\"] rewrite_tree_atom EOA[\"EOA\"] ) EOB[\"EOB\"] ) ) | rewrite_tree ( ebnfSuffix -> ^( ebnfSuffix ^( BLOCK[\"BLOCK\"] ^( ALT[\"ALT\"] rewrite_tree EOA[\"EOA\"] ) EOB[\"EOB\"] ) ) | -> rewrite_tree ) | rewrite_tree_ebnf );";
/*      */     }
/*      */   }
/*      */ 
/*      */   class DFA73 extends DFA
/*      */   {
/*      */     public DFA73(BaseRecognizer recognizer)
/*      */     {
/* 9494 */       this.recognizer = recognizer;
/* 9495 */       this.decisionNumber = 73;
/* 9496 */       this.eot = ANTLRv3Parser.DFA73_eot;
/* 9497 */       this.eof = ANTLRv3Parser.DFA73_eof;
/* 9498 */       this.min = ANTLRv3Parser.DFA73_min;
/* 9499 */       this.max = ANTLRv3Parser.DFA73_max;
/* 9500 */       this.accept = ANTLRv3Parser.DFA73_accept;
/* 9501 */       this.special = ANTLRv3Parser.DFA73_special;
/* 9502 */       this.transition = ANTLRv3Parser.DFA73_transition;
/*      */     }
/*      */     public String getDescription() {
/* 9505 */       return "360:1: rewrite_alternative options {backtrack=true; } : ( rewrite_template | rewrite_tree_alternative | -> ^( ALT[\"ALT\"] EPSILON[\"EPSILON\"] EOA[\"EOA\"] ) );";
/*      */     }
/*      */     public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
/* 9508 */       TokenStream input = (TokenStream)_input;
/* 9509 */       int _s = s;
/* 9510 */       switch (s) {
/*      */       case 0:
/* 9512 */         int LA73_4 = input.LA(1);
/*      */ 
/* 9515 */         int index73_4 = input.index();
/* 9516 */         input.rewind();
/* 9517 */         s = -1;
/* 9518 */         if (ANTLRv3Parser.this.synpred1_ANTLRv3()) s = 9;
/* 9520 */         else if (ANTLRv3Parser.this.synpred2_ANTLRv3()) s = 5;
/*      */ 
/* 9523 */         input.seek(index73_4);
/* 9524 */         if (s >= 0) return s;
/*      */         break;
/*      */       }
/* 9527 */       if (ANTLRv3Parser.this.state.backtracking > 0) { ANTLRv3Parser.this.state.failed = true; return -1; }
/* 9528 */       NoViableAltException nvae = new NoViableAltException(getDescription(), 73, _s, input);
/*      */ 
/* 9530 */       error(nvae);
/* 9531 */       throw nvae;
/*      */     }
/*      */   }
/*      */ 
/*      */   class DFA46 extends DFA
/*      */   {
/*      */     public DFA46(BaseRecognizer recognizer)
/*      */     {
/* 9426 */       this.recognizer = recognizer;
/* 9427 */       this.decisionNumber = 46;
/* 9428 */       this.eot = ANTLRv3Parser.DFA46_eot;
/* 9429 */       this.eof = ANTLRv3Parser.DFA46_eof;
/* 9430 */       this.min = ANTLRv3Parser.DFA46_min;
/* 9431 */       this.max = ANTLRv3Parser.DFA46_max;
/* 9432 */       this.accept = ANTLRv3Parser.DFA46_accept;
/* 9433 */       this.special = ANTLRv3Parser.DFA46_special;
/* 9434 */       this.transition = ANTLRv3Parser.DFA46_transition;
/*      */     }
/*      */     public String getDescription() {
/* 9437 */       return "233:1: element : ( id (labelOp= '=' | labelOp= '+=' ) atom ( ebnfSuffix -> ^( ebnfSuffix ^( BLOCK[\"BLOCK\"] ^( ALT[\"ALT\"] ^( $labelOp id atom ) EOA[\"EOA\"] ) EOB[\"EOB\"] ) ) | -> ^( $labelOp id atom ) ) | id (labelOp= '=' | labelOp= '+=' ) block ( ebnfSuffix -> ^( ebnfSuffix ^( BLOCK[\"BLOCK\"] ^( ALT[\"ALT\"] ^( $labelOp id block ) EOA[\"EOA\"] ) EOB[\"EOB\"] ) ) | -> ^( $labelOp id block ) ) | atom ( ebnfSuffix -> ^( ebnfSuffix ^( BLOCK[\"BLOCK\"] ^( ALT[\"ALT\"] atom EOA[\"EOA\"] ) EOB[\"EOB\"] ) ) | -> atom ) | ebnf | ACTION | SEMPRED (g= '=>' -> GATED_SEMPRED[$g] | -> SEMPRED ) | treeSpec ( ebnfSuffix -> ^( ebnfSuffix ^( BLOCK[\"BLOCK\"] ^( ALT[\"ALT\"] treeSpec EOA[\"EOA\"] ) EOB[\"EOB\"] ) ) | -> treeSpec ) );";
/*      */     }
/*      */   }
/*      */ 
/*      */   public static class id_return extends ParserRuleReturnScope
/*      */   {
/*      */     CommonTree tree;
/*      */ 
/*      */     public Object getTree()
/*      */     {
/* 9187 */       return this.tree;
/*      */     }
/*      */   }
/*      */ 
/*      */   public static class qid_return extends ParserRuleReturnScope
/*      */   {
/*      */     CommonTree tree;
/*      */ 
/*      */     public Object getTree()
/*      */     {
/* 9094 */       return this.tree;
/*      */     }
/*      */   }
/*      */ 
/*      */   public static class rewrite_template_arg_return extends ParserRuleReturnScope
/*      */   {
/*      */     CommonTree tree;
/*      */ 
/*      */     public Object getTree()
/*      */     {
/* 9003 */       return this.tree;
/*      */     }
/*      */   }
/*      */ 
/*      */   public static class rewrite_template_args_return extends ParserRuleReturnScope
/*      */   {
/*      */     CommonTree tree;
/*      */ 
/*      */     public Object getTree()
/*      */     {
/* 8828 */       return this.tree;
/*      */     }
/*      */   }
/*      */ 
/*      */   public static class rewrite_indirect_template_head_return extends ParserRuleReturnScope
/*      */   {
/*      */     CommonTree tree;
/*      */ 
/*      */     public Object getTree()
/*      */     {
/* 8721 */       return this.tree;
/*      */     }
/*      */   }
/*      */ 
/*      */   public static class rewrite_template_ref_return extends ParserRuleReturnScope
/*      */   {
/*      */     CommonTree tree;
/*      */ 
/*      */     public Object getTree()
/*      */     {
/* 8621 */       return this.tree;
/*      */     }
/*      */   }
/*      */ 
/*      */   public static class rewrite_template_return extends ParserRuleReturnScope
/*      */   {
/*      */     CommonTree tree;
/*      */ 
/*      */     public Object getTree()
/*      */     {
/* 8419 */       return this.tree;
/*      */     }
/*      */   }
/*      */ 
/*      */   public static class rewrite_tree_return extends ParserRuleReturnScope
/*      */   {
/*      */     CommonTree tree;
/*      */ 
/*      */     public Object getTree()
/*      */     {
/* 8281 */       return this.tree;
/*      */     }
/*      */   }
/*      */ 
/*      */   public static class rewrite_tree_ebnf_return extends ParserRuleReturnScope
/*      */   {
/*      */     CommonTree tree;
/*      */ 
/*      */     public Object getTree()
/*      */     {
/* 8185 */       return this.tree;
/*      */     }
/*      */   }
/*      */ 
/*      */   public static class rewrite_tree_atom_return extends ParserRuleReturnScope
/*      */   {
/*      */     CommonTree tree;
/*      */ 
/*      */     public Object getTree()
/*      */     {
/* 7934 */       return this.tree;
/*      */     }
/*      */   }
/*      */ 
/*      */   public static class rewrite_tree_element_return extends ParserRuleReturnScope
/*      */   {
/*      */     CommonTree tree;
/*      */ 
/*      */     public Object getTree()
/*      */     {
/* 7652 */       return this.tree;
/*      */     }
/*      */   }
/*      */ 
/*      */   public static class rewrite_tree_alternative_return extends ParserRuleReturnScope
/*      */   {
/*      */     CommonTree tree;
/*      */ 
/*      */     public Object getTree()
/*      */     {
/* 7527 */       return this.tree;
/*      */     }
/*      */   }
/*      */ 
/*      */   public static class rewrite_tree_block_return extends ParserRuleReturnScope
/*      */   {
/*      */     CommonTree tree;
/*      */ 
/*      */     public Object getTree()
/*      */     {
/* 7436 */       return this.tree;
/*      */     }
/*      */   }
/*      */ 
/*      */   public static class rewrite_alternative_return extends ParserRuleReturnScope
/*      */   {
/*      */     CommonTree tree;
/*      */ 
/*      */     public Object getTree()
/*      */     {
/* 7328 */       return this.tree;
/*      */     }
/*      */   }
/*      */ 
/*      */   public static class rewrite_return extends ParserRuleReturnScope
/*      */   {
/*      */     CommonTree tree;
/*      */ 
/*      */     public Object getTree()
/*      */     {
/* 7124 */       return this.tree;
/*      */     }
/*      */   }
/*      */ 
/*      */   public static class ebnfSuffix_return extends ParserRuleReturnScope
/*      */   {
/*      */     CommonTree tree;
/*      */ 
/*      */     public Object getTree()
/*      */     {
/* 6960 */       return this.tree;
/*      */     }
/*      */   }
/*      */ 
/*      */   public static class ebnf_return extends ParserRuleReturnScope
/*      */   {
/*      */     CommonTree tree;
/*      */ 
/*      */     public Object getTree()
/*      */     {
/* 6661 */       return this.tree;
/*      */     }
/*      */   }
/*      */ 
/*      */   public static class terminal_return extends ParserRuleReturnScope
/*      */   {
/*      */     CommonTree tree;
/*      */ 
/*      */     public Object getTree()
/*      */     {
/* 6161 */       return this.tree;
/*      */     }
/*      */   }
/*      */ 
/*      */   public static class range_return extends ParserRuleReturnScope
/*      */   {
/*      */     CommonTree tree;
/*      */ 
/*      */     public Object getTree()
/*      */     {
/* 6037 */       return this.tree;
/*      */     }
/*      */   }
/*      */ 
/*      */   public static class treeSpec_return extends ParserRuleReturnScope
/*      */   {
/*      */     CommonTree tree;
/*      */ 
/*      */     public Object getTree()
/*      */     {
/* 5891 */       return this.tree;
/*      */     }
/*      */   }
/*      */ 
/*      */   public static class elementOption_return extends ParserRuleReturnScope
/*      */   {
/*      */     CommonTree tree;
/*      */ 
/*      */     public Object getTree()
/*      */     {
/* 5797 */       return this.tree;
/*      */     }
/*      */   }
/*      */ 
/*      */   public static class elementOptions_return extends ParserRuleReturnScope
/*      */   {
/*      */     CommonTree tree;
/*      */ 
/*      */     public Object getTree()
/*      */     {
/* 5532 */       return this.tree;
/*      */     }
/*      */   }
/*      */ 
/*      */   public static class notTerminal_return extends ParserRuleReturnScope
/*      */   {
/*      */     CommonTree tree;
/*      */ 
/*      */     public Object getTree()
/*      */     {
/* 5474 */       return this.tree;
/*      */     }
/*      */   }
/*      */ 
/*      */   public static class notSet_return extends ParserRuleReturnScope
/*      */   {
/*      */     CommonTree tree;
/*      */ 
/*      */     public Object getTree()
/*      */     {
/* 5245 */       return this.tree;
/*      */     }
/*      */   }
/*      */ 
/*      */   public static class atom_return extends ParserRuleReturnScope
/*      */   {
/*      */     CommonTree tree;
/*      */ 
/*      */     public Object getTree()
/*      */     {
/* 4595 */       return this.tree;
/*      */     }
/*      */   }
/*      */ 
/*      */   public static class element_return extends ParserRuleReturnScope
/*      */   {
/*      */     CommonTree tree;
/*      */ 
/*      */     public Object getTree()
/*      */     {
/* 3709 */       return this.tree;
/*      */     }
/*      */   }
/*      */ 
/*      */   public static class finallyClause_return extends ParserRuleReturnScope
/*      */   {
/*      */     CommonTree tree;
/*      */ 
/*      */     public Object getTree()
/*      */     {
/* 3627 */       return this.tree;
/*      */     }
/*      */   }
/*      */ 
/*      */   public static class exceptionHandler_return extends ParserRuleReturnScope
/*      */   {
/*      */     CommonTree tree;
/*      */ 
/*      */     public Object getTree()
/*      */     {
/* 3538 */       return this.tree;
/*      */     }
/*      */   }
/*      */ 
/*      */   public static class exceptionGroup_return extends ParserRuleReturnScope
/*      */   {
/*      */     CommonTree tree;
/*      */ 
/*      */     public Object getTree()
/*      */     {
/* 3386 */       return this.tree;
/*      */     }
/*      */   }
/*      */ 
/*      */   public static class alternative_return extends ParserRuleReturnScope
/*      */   {
/*      */     CommonTree tree;
/*      */ 
/*      */     public Object getTree()
/*      */     {
/* 3185 */       return this.tree;
/*      */     }
/*      */   }
/*      */ 
/*      */   public static class altList_return extends ParserRuleReturnScope
/*      */   {
/*      */     CommonTree tree;
/*      */ 
/*      */     public Object getTree()
/*      */     {
/* 3053 */       return this.tree;
/*      */     }
/*      */   }
/*      */ 
/*      */   public static class altpair_return extends ParserRuleReturnScope
/*      */   {
/*      */     CommonTree tree;
/*      */ 
/*      */     public Object getTree()
/*      */     {
/* 2993 */       return this.tree;
/*      */     }
/*      */   }
/*      */ 
/*      */   public static class block_return extends ParserRuleReturnScope
/*      */   {
/*      */     CommonTree tree;
/*      */ 
/*      */     public Object getTree()
/*      */     {
/* 2793 */       return this.tree;
/*      */     }
/*      */   }
/*      */ 
/*      */   public static class ruleScopeSpec_return extends ParserRuleReturnScope
/*      */   {
/*      */     CommonTree tree;
/*      */ 
/*      */     public Object getTree()
/*      */     {
/* 2445 */       return this.tree;
/*      */     }
/*      */   }
/*      */ 
/*      */   public static class throwsSpec_return extends ParserRuleReturnScope
/*      */   {
/*      */     CommonTree tree;
/*      */ 
/*      */     public Object getTree()
/*      */     {
/* 2314 */       return this.tree;
/*      */     }
/*      */   }
/*      */ 
/*      */   public static class ruleAction_return extends ParserRuleReturnScope
/*      */   {
/*      */     CommonTree tree;
/*      */ 
/*      */     public Object getTree()
/*      */     {
/* 2223 */       return this.tree;
/*      */     }
/*      */   }
/*      */ 
/*      */   public static class rule_return extends ParserRuleReturnScope
/*      */   {
/*      */     CommonTree tree;
/*      */ 
/*      */     public Object getTree()
/*      */     {
/* 1686 */       return this.tree;
/*      */     }
/*      */   }
/*      */ 
/*      */   protected static class rule_scope
/*      */   {
/*      */     String name;
/*      */   }
/*      */ 
/*      */   public static class optionValue_return extends ParserRuleReturnScope
/*      */   {
/*      */     CommonTree tree;
/*      */ 
/*      */     public Object getTree()
/*      */     {
/* 1512 */       return this.tree;
/*      */     }
/*      */   }
/*      */ 
/*      */   public static class option_return extends ParserRuleReturnScope
/*      */   {
/*      */     CommonTree tree;
/*      */ 
/*      */     public Object getTree()
/*      */     {
/* 1418 */       return this.tree;
/*      */     }
/*      */   }
/*      */ 
/*      */   public static class optionsSpec_return extends ParserRuleReturnScope
/*      */   {
/*      */     CommonTree tree;
/*      */ 
/*      */     public Object getTree()
/*      */     {
/* 1282 */       return this.tree;
/*      */     }
/*      */   }
/*      */ 
/*      */   public static class actionScopeName_return extends ParserRuleReturnScope
/*      */   {
/*      */     CommonTree tree;
/*      */ 
/*      */     public Object getTree()
/*      */     {
/* 1136 */       return this.tree;
/*      */     }
/*      */   }
/*      */ 
/*      */   public static class action_return extends ParserRuleReturnScope
/*      */   {
/*      */     CommonTree tree;
/*      */ 
/*      */     public Object getTree()
/*      */     {
/*  979 */       return this.tree;
/*      */     }
/*      */   }
/*      */ 
/*      */   public static class attrScope_return extends ParserRuleReturnScope
/*      */   {
/*      */     CommonTree tree;
/*      */ 
/*      */     public Object getTree()
/*      */     {
/*  888 */       return this.tree;
/*      */     }
/*      */   }
/*      */ 
/*      */   public static class tokenSpec_return extends ParserRuleReturnScope
/*      */   {
/*      */     CommonTree tree;
/*      */ 
/*      */     public Object getTree()
/*      */     {
/*  695 */       return this.tree;
/*      */     }
/*      */   }
/*      */ 
/*      */   public static class tokensSpec_return extends ParserRuleReturnScope
/*      */   {
/*      */     CommonTree tree;
/*      */ 
/*      */     public Object getTree()
/*      */     {
/*  566 */       return this.tree;
/*      */     }
/*      */   }
/*      */ 
/*      */   public static class grammarDef_return extends ParserRuleReturnScope
/*      */   {
/*      */     CommonTree tree;
/*      */ 
/*      */     public Object getTree()
/*      */     {
/*  142 */       return this.tree;
/*      */     }
/*      */   }
/*      */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.antlr.grammar.v3.ANTLRv3Parser
 * JD-Core Version:    0.6.2
 */