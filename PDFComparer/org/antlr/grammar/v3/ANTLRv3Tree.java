/*      */ package org.antlr.grammar.v3;
/*      */ 
/*      */ import org.antlr.runtime.BaseRecognizer;
/*      */ import org.antlr.runtime.BitSet;
/*      */ import org.antlr.runtime.DFA;
/*      */ import org.antlr.runtime.EarlyExitException;
/*      */ import org.antlr.runtime.MismatchedSetException;
/*      */ import org.antlr.runtime.NoViableAltException;
/*      */ import org.antlr.runtime.RecognitionException;
/*      */ import org.antlr.runtime.RecognizerSharedState;
/*      */ import org.antlr.runtime.tree.TreeNodeStream;
/*      */ import org.antlr.runtime.tree.TreeParser;
/*      */ 
/*      */ public class ANTLRv3Tree extends TreeParser
/*      */ {
/*   13 */   public static final String[] tokenNames = { "<invalid>", "<EOR>", "<DOWN>", "<UP>", "DOC_COMMENT", "PARSER", "LEXER", "RULE", "BLOCK", "OPTIONAL", "CLOSURE", "POSITIVE_CLOSURE", "SYNPRED", "RANGE", "CHAR_RANGE", "EPSILON", "ALT", "EOR", "EOB", "EOA", "ID", "ARG", "ARGLIST", "RET", "LEXER_GRAMMAR", "PARSER_GRAMMAR", "TREE_GRAMMAR", "COMBINED_GRAMMAR", "LABEL", "TEMPLATE", "SCOPE", "SEMPRED", "GATED_SEMPRED", "SYN_SEMPRED", "BACKTRACK_SEMPRED", "FRAGMENT", "TREE_BEGIN", "ROOT", "BANG", "REWRITE", "AT", "LABEL_ASSIGN", "LIST_LABEL_ASSIGN", "TOKENS", "TOKEN_REF", "STRING_LITERAL", "CHAR_LITERAL", "ACTION", "OPTIONS", "INT", "ARG_ACTION", "RULE_REF", "DOUBLE_QUOTE_STRING_LITERAL", "DOUBLE_ANGLE_STRING_LITERAL", "SRC", "SL_COMMENT", "ML_COMMENT", "LITERAL_CHAR", "ESC", "XDIGIT", "NESTED_ARG_ACTION", "ACTION_STRING_LITERAL", "ACTION_CHAR_LITERAL", "NESTED_ACTION", "ACTION_ESC", "WS_LOOP", "WS", "'lexer'", "'parser'", "'tree'", "'grammar'", "';'", "'}'", "'::'", "'*'", "'protected'", "'public'", "'private'", "':'", "'throws'", "','", "'('", "'|'", "')'", "'catch'", "'finally'", "'=>'", "'~'", "'<'", "'>'", "'.'", "'?'", "'+'", "'$'" };
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
/*      */   public static final int T__82 = 82;
/*      */   public static final int RULE = 7;
/*      */   public static final int ACTION_ESC = 64;
/*      */   public static final int T__83 = 83;
/*      */   public static final int PARSER_GRAMMAR = 25;
/*      */   public static final int SRC = 54;
/*      */   public static final int INT = 49;
/*      */   public static final int CHAR_RANGE = 14;
/*      */   public static final int EPSILON = 15;
/*      */   public static final int T__85 = 85;
/*      */   public static final int T__84 = 84;
/*      */   public static final int T__87 = 87;
/*      */   public static final int T__86 = 86;
/*      */   public static final int REWRITE = 39;
/*      */   public static final int T__89 = 89;
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
/* 3629 */   protected DFA33 dfa33 = new DFA33(this);
/* 3630 */   protected DFA38 dfa38 = new DFA38(this);
/* 3631 */   protected DFA48 dfa48 = new DFA48(this);
/*      */   static final String DFA33_eotS = "\fð¿¿";
/*      */   static final String DFA33_eofS = "\fð¿¿";
/*      */   static final String DFA33_minS = "\001\b\001\002\006ð¿¿\001\024\001\b\002ð¿¿";
/*      */   static final String DFA33_maxS = "\001Z\001\002\006ð¿¿\001\024\001Z\002ð¿¿";
/*      */   static final String DFA33_acceptS = "\002ð¿¿\001\003\001\004\001\005\001\006\001\007\001\b\002ð¿¿\001\001\001\002";
/*      */   static final String DFA33_specialS = "\fð¿¿}>";
/* 3644 */   static final String[] DFA33_transitionS = { "\005\003\001ð¿¿\001\002\020ð¿¿\001\005\001\006\001\003\002ð¿¿\001\007\002\002\002ð¿¿\002\001\001ð¿¿\003\002\001\004\003ð¿¿\001\002#ð¿¿\001\002\002ð¿¿\001\002", "\001\b", "", "", "", "", "", "", "\001\t", "\001\n\005ð¿¿\001\013\026ð¿¿\002\013\005ð¿¿\003\013\004ð¿¿\001\013#ð¿¿\001\013\002ð¿¿\001\013", "", "" };
/*      */ 
/* 3661 */   static final short[] DFA33_eot = DFA.unpackEncodedString("\fð¿¿");
/* 3662 */   static final short[] DFA33_eof = DFA.unpackEncodedString("\fð¿¿");
/* 3663 */   static final char[] DFA33_min = DFA.unpackEncodedStringToUnsignedChars("\001\b\001\002\006ð¿¿\001\024\001\b\002ð¿¿");
/* 3664 */   static final char[] DFA33_max = DFA.unpackEncodedStringToUnsignedChars("\001Z\001\002\006ð¿¿\001\024\001Z\002ð¿¿");
/* 3665 */   static final short[] DFA33_accept = DFA.unpackEncodedString("\002ð¿¿\001\003\001\004\001\005\001\006\001\007\001\b\002ð¿¿\001\001\001\002");
/* 3666 */   static final short[] DFA33_special = DFA.unpackEncodedString("\fð¿¿}>");
/*      */   static final short[][] DFA33_transition;
/*      */   static final String DFA38_eotS = "\032ð¿¿";
/*      */   static final String DFA38_eofS = "\032ð¿¿";
/*      */   static final String DFA38_minS = "\001\016\002ð¿¿\006\002\001\b\004ð¿¿\0010\007ð¿¿\001\003\003ð¿¿";
/*      */   static final String DFA38_maxS = "\001Z\002ð¿¿\001\002\005Z\001.\004ð¿¿\0012\007ð¿¿\0010\003ð¿¿";
/*      */   static final String DFA38_acceptS = "\001ð¿¿\001\001\001\002\007ð¿¿\001\005\001\006\001\b\001\007\001ð¿¿\001\t\001\016\001\r\001\020\001\017\001\004\001\003\001ð¿¿\001\n\001\f\001\013";
/*      */   static final String DFA38_specialS = "\032ð¿¿}>";
/*      */   static final String[] DFA38_transitionS;
/*      */   static final short[] DFA38_eot;
/*      */   static final short[] DFA38_eof;
/*      */   static final char[] DFA38_min;
/*      */   static final char[] DFA38_max;
/*      */   static final short[] DFA38_accept;
/*      */   static final short[] DFA38_special;
/*      */   static final short[][] DFA38_transition;
/*      */   static final String DFA48_eotS = "\020ð¿¿";
/*      */   static final String DFA48_eofS = "\020ð¿¿";
/*      */   static final String DFA48_minS = "\001\035\001\002\001ð¿¿\001\024\001\026\001ð¿¿\001\002\001\025\002ð¿¿\001\002\001\024\001/\003\003";
/*      */   static final String DFA48_maxS = "\001/\001\002\001ð¿¿\001/\001\026\001ð¿¿\0015\001\025\002ð¿¿\001\002\001\024\001/\001\003\001\025\0015";
/*      */   static final String DFA48_acceptS = "\002ð¿¿\001\004\002ð¿¿\001\003\002ð¿¿\001\002\001\001\006ð¿¿";
/*      */   static final String DFA48_specialS = "\020ð¿¿}>";
/*      */   static final String[] DFA48_transitionS;
/*      */   static final short[] DFA48_eot;
/*      */   static final short[] DFA48_eof;
/*      */   static final char[] DFA48_min;
/*      */   static final char[] DFA48_max;
/*      */   static final short[] DFA48_accept;
/*      */   static final short[] DFA48_special;
/*      */   static final short[][] DFA48_transition;
/* 3848 */   public static final BitSet FOLLOW_grammarType_in_grammarDef52 = new BitSet(new long[] { 4L });
/* 3849 */   public static final BitSet FOLLOW_ID_in_grammarDef54 = new BitSet(new long[] { 291371655102608L });
/* 3850 */   public static final BitSet FOLLOW_DOC_COMMENT_in_grammarDef56 = new BitSet(new long[] { 291371655102608L });
/* 3851 */   public static final BitSet FOLLOW_optionsSpec_in_grammarDef59 = new BitSet(new long[] { 291371655102608L });
/* 3852 */   public static final BitSet FOLLOW_tokensSpec_in_grammarDef62 = new BitSet(new long[] { 291371655102608L });
/* 3853 */   public static final BitSet FOLLOW_attrScope_in_grammarDef65 = new BitSet(new long[] { 291371655102608L });
/* 3854 */   public static final BitSet FOLLOW_action_in_grammarDef68 = new BitSet(new long[] { 291371655102608L });
/* 3855 */   public static final BitSet FOLLOW_rule_in_grammarDef71 = new BitSet(new long[] { 291371655102616L });
/* 3856 */   public static final BitSet FOLLOW_set_in_grammarType0 = new BitSet(new long[] { 2L });
/* 3857 */   public static final BitSet FOLLOW_TOKENS_in_tokensSpec127 = new BitSet(new long[] { 4L });
/* 3858 */   public static final BitSet FOLLOW_tokenSpec_in_tokensSpec129 = new BitSet(new long[] { 19791209299976L });
/* 3859 */   public static final BitSet FOLLOW_LABEL_ASSIGN_in_tokenSpec143 = new BitSet(new long[] { 4L });
/* 3860 */   public static final BitSet FOLLOW_TOKEN_REF_in_tokenSpec145 = new BitSet(new long[] { 35184372088832L });
/* 3861 */   public static final BitSet FOLLOW_STRING_LITERAL_in_tokenSpec147 = new BitSet(new long[] { 8L });
/* 3862 */   public static final BitSet FOLLOW_LABEL_ASSIGN_in_tokenSpec154 = new BitSet(new long[] { 4L });
/* 3863 */   public static final BitSet FOLLOW_TOKEN_REF_in_tokenSpec156 = new BitSet(new long[] { 70368744177664L });
/* 3864 */   public static final BitSet FOLLOW_CHAR_LITERAL_in_tokenSpec158 = new BitSet(new long[] { 8L });
/* 3865 */   public static final BitSet FOLLOW_TOKEN_REF_in_tokenSpec164 = new BitSet(new long[] { 2L });
/* 3866 */   public static final BitSet FOLLOW_SCOPE_in_attrScope176 = new BitSet(new long[] { 4L });
/* 3867 */   public static final BitSet FOLLOW_ID_in_attrScope178 = new BitSet(new long[] { 140737488355328L });
/* 3868 */   public static final BitSet FOLLOW_ACTION_in_attrScope180 = new BitSet(new long[] { 8L });
/* 3869 */   public static final BitSet FOLLOW_AT_in_action193 = new BitSet(new long[] { 4L });
/* 3870 */   public static final BitSet FOLLOW_ID_in_action195 = new BitSet(new long[] { 1048576L });
/* 3871 */   public static final BitSet FOLLOW_ID_in_action197 = new BitSet(new long[] { 140737488355328L });
/* 3872 */   public static final BitSet FOLLOW_ACTION_in_action199 = new BitSet(new long[] { 8L });
/* 3873 */   public static final BitSet FOLLOW_AT_in_action206 = new BitSet(new long[] { 4L });
/* 3874 */   public static final BitSet FOLLOW_ID_in_action208 = new BitSet(new long[] { 140737488355328L });
/* 3875 */   public static final BitSet FOLLOW_ACTION_in_action210 = new BitSet(new long[] { 8L });
/* 3876 */   public static final BitSet FOLLOW_OPTIONS_in_optionsSpec223 = new BitSet(new long[] { 4L });
/* 3877 */   public static final BitSet FOLLOW_option_in_optionsSpec225 = new BitSet(new long[] { 2199024304136L });
/* 3878 */   public static final BitSet FOLLOW_qid_in_option243 = new BitSet(new long[] { 2L });
/* 3879 */   public static final BitSet FOLLOW_LABEL_ASSIGN_in_option253 = new BitSet(new long[] { 4L });
/* 3880 */   public static final BitSet FOLLOW_ID_in_option255 = new BitSet(new long[] { 668503070736384L });
/* 3881 */   public static final BitSet FOLLOW_optionValue_in_option257 = new BitSet(new long[] { 8L });
/* 3882 */   public static final BitSet FOLLOW_set_in_optionValue0 = new BitSet(new long[] { 2L });
/* 3883 */   public static final BitSet FOLLOW_RULE_in_rule323 = new BitSet(new long[] { 4L });
/* 3884 */   public static final BitSet FOLLOW_ID_in_rule325 = new BitSet(new long[] { 282609932304640L, 47104L });
/* 3885 */   public static final BitSet FOLLOW_modifier_in_rule327 = new BitSet(new long[] { 282609932304640L, 47104L });
/* 3886 */   public static final BitSet FOLLOW_ARG_in_rule332 = new BitSet(new long[] { 4L });
/* 3887 */   public static final BitSet FOLLOW_ARG_ACTION_in_rule334 = new BitSet(new long[] { 8L });
/* 3888 */   public static final BitSet FOLLOW_RET_in_rule341 = new BitSet(new long[] { 4L });
/* 3889 */   public static final BitSet FOLLOW_ARG_ACTION_in_rule343 = new BitSet(new long[] { 8L });
/* 3890 */   public static final BitSet FOLLOW_throwsSpec_in_rule356 = new BitSet(new long[] { 282609932304640L, 47104L });
/* 3891 */   public static final BitSet FOLLOW_optionsSpec_in_rule359 = new BitSet(new long[] { 282609932304640L, 47104L });
/* 3892 */   public static final BitSet FOLLOW_ruleScopeSpec_in_rule362 = new BitSet(new long[] { 282609932304640L, 47104L });
/* 3893 */   public static final BitSet FOLLOW_ruleAction_in_rule365 = new BitSet(new long[] { 282609932304640L, 47104L });
/* 3894 */   public static final BitSet FOLLOW_altList_in_rule376 = new BitSet(new long[] { 131072L, 3145728L });
/* 3895 */   public static final BitSet FOLLOW_exceptionGroup_in_rule386 = new BitSet(new long[] { 131072L });
/* 3896 */   public static final BitSet FOLLOW_EOR_in_rule389 = new BitSet(new long[] { 8L });
/* 3897 */   public static final BitSet FOLLOW_set_in_modifier0 = new BitSet(new long[] { 2L });
/* 3898 */   public static final BitSet FOLLOW_AT_in_ruleAction428 = new BitSet(new long[] { 4L });
/* 3899 */   public static final BitSet FOLLOW_ID_in_ruleAction430 = new BitSet(new long[] { 140737488355328L });
/* 3900 */   public static final BitSet FOLLOW_ACTION_in_ruleAction432 = new BitSet(new long[] { 8L });
/* 3901 */   public static final BitSet FOLLOW_79_in_throwsSpec445 = new BitSet(new long[] { 4L });
/* 3902 */   public static final BitSet FOLLOW_ID_in_throwsSpec447 = new BitSet(new long[] { 1048584L });
/* 3903 */   public static final BitSet FOLLOW_SCOPE_in_ruleScopeSpec461 = new BitSet(new long[] { 4L });
/* 3904 */   public static final BitSet FOLLOW_ACTION_in_ruleScopeSpec463 = new BitSet(new long[] { 8L });
/* 3905 */   public static final BitSet FOLLOW_SCOPE_in_ruleScopeSpec470 = new BitSet(new long[] { 4L });
/* 3906 */   public static final BitSet FOLLOW_ACTION_in_ruleScopeSpec472 = new BitSet(new long[] { 1048576L });
/* 3907 */   public static final BitSet FOLLOW_ID_in_ruleScopeSpec474 = new BitSet(new long[] { 1048584L });
/* 3908 */   public static final BitSet FOLLOW_SCOPE_in_ruleScopeSpec482 = new BitSet(new long[] { 4L });
/* 3909 */   public static final BitSet FOLLOW_ID_in_ruleScopeSpec484 = new BitSet(new long[] { 1048584L });
/* 3910 */   public static final BitSet FOLLOW_BLOCK_in_block504 = new BitSet(new long[] { 4L });
/* 3911 */   public static final BitSet FOLLOW_optionsSpec_in_block506 = new BitSet(new long[] { 65536L });
/* 3912 */   public static final BitSet FOLLOW_alternative_in_block510 = new BitSet(new long[] { 549756141568L });
/* 3913 */   public static final BitSet FOLLOW_rewrite_in_block512 = new BitSet(new long[] { 327680L });
/* 3914 */   public static final BitSet FOLLOW_EOB_in_block516 = new BitSet(new long[] { 8L });
/* 3915 */   public static final BitSet FOLLOW_BLOCK_in_altList539 = new BitSet(new long[] { 4L });
/* 3916 */   public static final BitSet FOLLOW_alternative_in_altList542 = new BitSet(new long[] { 549756141568L });
/* 3917 */   public static final BitSet FOLLOW_rewrite_in_altList544 = new BitSet(new long[] { 327680L });
/* 3918 */   public static final BitSet FOLLOW_EOB_in_altList548 = new BitSet(new long[] { 8L });
/* 3919 */   public static final BitSet FOLLOW_ALT_in_alternative570 = new BitSet(new long[] { 4L });
/* 3920 */   public static final BitSet FOLLOW_element_in_alternative572 = new BitSet(new long[] { 2522775743389440L, 75497472L });
/* 3921 */   public static final BitSet FOLLOW_EOA_in_alternative575 = new BitSet(new long[] { 8L });
/* 3922 */   public static final BitSet FOLLOW_ALT_in_alternative587 = new BitSet(new long[] { 4L });
/* 3923 */   public static final BitSet FOLLOW_EPSILON_in_alternative589 = new BitSet(new long[] { 524288L });
/* 3924 */   public static final BitSet FOLLOW_EOA_in_alternative591 = new BitSet(new long[] { 8L });
/* 3925 */   public static final BitSet FOLLOW_exceptionHandler_in_exceptionGroup606 = new BitSet(new long[] { 2L, 3145728L });
/* 3926 */   public static final BitSet FOLLOW_finallyClause_in_exceptionGroup609 = new BitSet(new long[] { 2L });
/* 3927 */   public static final BitSet FOLLOW_finallyClause_in_exceptionGroup615 = new BitSet(new long[] { 2L });
/* 3928 */   public static final BitSet FOLLOW_84_in_exceptionHandler636 = new BitSet(new long[] { 4L });
/* 3929 */   public static final BitSet FOLLOW_ARG_ACTION_in_exceptionHandler638 = new BitSet(new long[] { 140737488355328L });
/* 3930 */   public static final BitSet FOLLOW_ACTION_in_exceptionHandler640 = new BitSet(new long[] { 8L });
/* 3931 */   public static final BitSet FOLLOW_85_in_finallyClause662 = new BitSet(new long[] { 4L });
/* 3932 */   public static final BitSet FOLLOW_ACTION_in_finallyClause664 = new BitSet(new long[] { 8L });
/* 3933 */   public static final BitSet FOLLOW_set_in_element680 = new BitSet(new long[] { 4L });
/* 3934 */   public static final BitSet FOLLOW_ID_in_element686 = new BitSet(new long[] { 8589942528L });
/* 3935 */   public static final BitSet FOLLOW_block_in_element688 = new BitSet(new long[] { 8L });
/* 3936 */   public static final BitSet FOLLOW_set_in_element695 = new BitSet(new long[] { 4L });
/* 3937 */   public static final BitSet FOLLOW_ID_in_element701 = new BitSet(new long[] { 2375357432872960L, 75497472L });
/* 3938 */   public static final BitSet FOLLOW_atom_in_element703 = new BitSet(new long[] { 8L });
/* 3939 */   public static final BitSet FOLLOW_atom_in_element709 = new BitSet(new long[] { 2L });
/* 3940 */   public static final BitSet FOLLOW_ebnf_in_element714 = new BitSet(new long[] { 2L });
/* 3941 */   public static final BitSet FOLLOW_ACTION_in_element721 = new BitSet(new long[] { 2L });
/* 3942 */   public static final BitSet FOLLOW_SEMPRED_in_element728 = new BitSet(new long[] { 2L });
/* 3943 */   public static final BitSet FOLLOW_GATED_SEMPRED_in_element733 = new BitSet(new long[] { 2L });
/* 3944 */   public static final BitSet FOLLOW_TREE_BEGIN_in_element741 = new BitSet(new long[] { 4L });
/* 3945 */   public static final BitSet FOLLOW_element_in_element743 = new BitSet(new long[] { 2522775743389448L, 75497472L });
/* 3946 */   public static final BitSet FOLLOW_set_in_atom757 = new BitSet(new long[] { 4L });
/* 3947 */   public static final BitSet FOLLOW_atom_in_atom763 = new BitSet(new long[] { 8L });
/* 3948 */   public static final BitSet FOLLOW_CHAR_RANGE_in_atom770 = new BitSet(new long[] { 4L });
/* 3949 */   public static final BitSet FOLLOW_CHAR_LITERAL_in_atom772 = new BitSet(new long[] { 70368744177664L });
/* 3950 */   public static final BitSet FOLLOW_CHAR_LITERAL_in_atom774 = new BitSet(new long[] { 281474976710664L });
/* 3951 */   public static final BitSet FOLLOW_optionsSpec_in_atom776 = new BitSet(new long[] { 8L });
/* 3952 */   public static final BitSet FOLLOW_87_in_atom784 = new BitSet(new long[] { 4L });
/* 3953 */   public static final BitSet FOLLOW_notTerminal_in_atom786 = new BitSet(new long[] { 281474976710664L });
/* 3954 */   public static final BitSet FOLLOW_optionsSpec_in_atom788 = new BitSet(new long[] { 8L });
/* 3955 */   public static final BitSet FOLLOW_87_in_atom796 = new BitSet(new long[] { 4L });
/* 3956 */   public static final BitSet FOLLOW_block_in_atom798 = new BitSet(new long[] { 281474976710664L });
/* 3957 */   public static final BitSet FOLLOW_optionsSpec_in_atom800 = new BitSet(new long[] { 8L });
/* 3958 */   public static final BitSet FOLLOW_RULE_REF_in_atom811 = new BitSet(new long[] { 4L });
/* 3959 */   public static final BitSet FOLLOW_ARG_ACTION_in_atom813 = new BitSet(new long[] { 8L });
/* 3960 */   public static final BitSet FOLLOW_RULE_REF_in_atom822 = new BitSet(new long[] { 2L });
/* 3961 */   public static final BitSet FOLLOW_CHAR_LITERAL_in_atom832 = new BitSet(new long[] { 2L });
/* 3962 */   public static final BitSet FOLLOW_CHAR_LITERAL_in_atom843 = new BitSet(new long[] { 4L });
/* 3963 */   public static final BitSet FOLLOW_optionsSpec_in_atom845 = new BitSet(new long[] { 8L });
/* 3964 */   public static final BitSet FOLLOW_TOKEN_REF_in_atom854 = new BitSet(new long[] { 2L });
/* 3965 */   public static final BitSet FOLLOW_TOKEN_REF_in_atom863 = new BitSet(new long[] { 4L });
/* 3966 */   public static final BitSet FOLLOW_optionsSpec_in_atom865 = new BitSet(new long[] { 8L });
/* 3967 */   public static final BitSet FOLLOW_TOKEN_REF_in_atom875 = new BitSet(new long[] { 4L });
/* 3968 */   public static final BitSet FOLLOW_ARG_ACTION_in_atom877 = new BitSet(new long[] { 281474976710656L });
/* 3969 */   public static final BitSet FOLLOW_optionsSpec_in_atom879 = new BitSet(new long[] { 8L });
/* 3970 */   public static final BitSet FOLLOW_TOKEN_REF_in_atom889 = new BitSet(new long[] { 4L });
/* 3971 */   public static final BitSet FOLLOW_ARG_ACTION_in_atom891 = new BitSet(new long[] { 8L });
/* 3972 */   public static final BitSet FOLLOW_STRING_LITERAL_in_atom900 = new BitSet(new long[] { 2L });
/* 3973 */   public static final BitSet FOLLOW_STRING_LITERAL_in_atom909 = new BitSet(new long[] { 4L });
/* 3974 */   public static final BitSet FOLLOW_optionsSpec_in_atom911 = new BitSet(new long[] { 8L });
/* 3975 */   public static final BitSet FOLLOW_90_in_atom920 = new BitSet(new long[] { 2L });
/* 3976 */   public static final BitSet FOLLOW_90_in_atom929 = new BitSet(new long[] { 4L });
/* 3977 */   public static final BitSet FOLLOW_optionsSpec_in_atom931 = new BitSet(new long[] { 8L });
/* 3978 */   public static final BitSet FOLLOW_SYNPRED_in_ebnf950 = new BitSet(new long[] { 4L });
/* 3979 */   public static final BitSet FOLLOW_block_in_ebnf952 = new BitSet(new long[] { 8L });
/* 3980 */   public static final BitSet FOLLOW_OPTIONAL_in_ebnf959 = new BitSet(new long[] { 4L });
/* 3981 */   public static final BitSet FOLLOW_block_in_ebnf961 = new BitSet(new long[] { 8L });
/* 3982 */   public static final BitSet FOLLOW_CLOSURE_in_ebnf970 = new BitSet(new long[] { 4L });
/* 3983 */   public static final BitSet FOLLOW_block_in_ebnf972 = new BitSet(new long[] { 8L });
/* 3984 */   public static final BitSet FOLLOW_POSITIVE_CLOSURE_in_ebnf982 = new BitSet(new long[] { 4L });
/* 3985 */   public static final BitSet FOLLOW_block_in_ebnf984 = new BitSet(new long[] { 8L });
/* 3986 */   public static final BitSet FOLLOW_SYN_SEMPRED_in_ebnf990 = new BitSet(new long[] { 2L });
/* 3987 */   public static final BitSet FOLLOW_block_in_ebnf995 = new BitSet(new long[] { 2L });
/* 3988 */   public static final BitSet FOLLOW_set_in_notTerminal0 = new BitSet(new long[] { 2L });
/* 3989 */   public static final BitSet FOLLOW_REWRITE_in_rewrite1035 = new BitSet(new long[] { 4L });
/* 3990 */   public static final BitSet FOLLOW_SEMPRED_in_rewrite1037 = new BitSet(new long[] { 140738025291776L });
/* 3991 */   public static final BitSet FOLLOW_rewrite_alternative_in_rewrite1039 = new BitSet(new long[] { 8L });
/* 3992 */   public static final BitSet FOLLOW_REWRITE_in_rewrite1045 = new BitSet(new long[] { 4L });
/* 3993 */   public static final BitSet FOLLOW_rewrite_alternative_in_rewrite1047 = new BitSet(new long[] { 8L });
/* 3994 */   public static final BitSet FOLLOW_rewrite_template_in_rewrite_alternative1062 = new BitSet(new long[] { 2L });
/* 3995 */   public static final BitSet FOLLOW_rewrite_tree_alternative_in_rewrite_alternative1067 = new BitSet(new long[] { 2L });
/* 3996 */   public static final BitSet FOLLOW_ALT_in_rewrite_alternative1078 = new BitSet(new long[] { 4L });
/* 3997 */   public static final BitSet FOLLOW_EPSILON_in_rewrite_alternative1080 = new BitSet(new long[] { 524288L });
/* 3998 */   public static final BitSet FOLLOW_EOA_in_rewrite_alternative1082 = new BitSet(new long[] { 8L });
/* 3999 */   public static final BitSet FOLLOW_BLOCK_in_rewrite_tree_block1101 = new BitSet(new long[] { 4L });
/* 4000 */   public static final BitSet FOLLOW_rewrite_tree_alternative_in_rewrite_tree_block1103 = new BitSet(new long[] { 262144L });
/* 4001 */   public static final BitSet FOLLOW_EOB_in_rewrite_tree_block1105 = new BitSet(new long[] { 8L });
/* 4002 */   public static final BitSet FOLLOW_ALT_in_rewrite_tree_alternative1124 = new BitSet(new long[] { 4L });
/* 4003 */   public static final BitSet FOLLOW_rewrite_tree_element_in_rewrite_tree_alternative1126 = new BitSet(new long[] { 2515751592791808L });
/* 4004 */   public static final BitSet FOLLOW_EOA_in_rewrite_tree_alternative1129 = new BitSet(new long[] { 8L });
/* 4005 */   public static final BitSet FOLLOW_rewrite_tree_atom_in_rewrite_tree_element1144 = new BitSet(new long[] { 2L });
/* 4006 */   public static final BitSet FOLLOW_rewrite_tree_in_rewrite_tree_element1149 = new BitSet(new long[] { 2L });
/* 4007 */   public static final BitSet FOLLOW_rewrite_tree_block_in_rewrite_tree_element1156 = new BitSet(new long[] { 2L });
/* 4008 */   public static final BitSet FOLLOW_rewrite_tree_ebnf_in_rewrite_tree_element1163 = new BitSet(new long[] { 2L });
/* 4009 */   public static final BitSet FOLLOW_CHAR_LITERAL_in_rewrite_tree_atom1179 = new BitSet(new long[] { 2L });
/* 4010 */   public static final BitSet FOLLOW_TOKEN_REF_in_rewrite_tree_atom1186 = new BitSet(new long[] { 2L });
/* 4011 */   public static final BitSet FOLLOW_TOKEN_REF_in_rewrite_tree_atom1194 = new BitSet(new long[] { 4L });
/* 4012 */   public static final BitSet FOLLOW_ARG_ACTION_in_rewrite_tree_atom1196 = new BitSet(new long[] { 8L });
/* 4013 */   public static final BitSet FOLLOW_RULE_REF_in_rewrite_tree_atom1208 = new BitSet(new long[] { 2L });
/* 4014 */   public static final BitSet FOLLOW_STRING_LITERAL_in_rewrite_tree_atom1215 = new BitSet(new long[] { 2L });
/* 4015 */   public static final BitSet FOLLOW_LABEL_in_rewrite_tree_atom1222 = new BitSet(new long[] { 2L });
/* 4016 */   public static final BitSet FOLLOW_ACTION_in_rewrite_tree_atom1227 = new BitSet(new long[] { 2L });
/* 4017 */   public static final BitSet FOLLOW_OPTIONAL_in_rewrite_tree_ebnf1239 = new BitSet(new long[] { 4L });
/* 4018 */   public static final BitSet FOLLOW_rewrite_tree_block_in_rewrite_tree_ebnf1241 = new BitSet(new long[] { 8L });
/* 4019 */   public static final BitSet FOLLOW_CLOSURE_in_rewrite_tree_ebnf1250 = new BitSet(new long[] { 4L });
/* 4020 */   public static final BitSet FOLLOW_rewrite_tree_block_in_rewrite_tree_ebnf1252 = new BitSet(new long[] { 8L });
/* 4021 */   public static final BitSet FOLLOW_POSITIVE_CLOSURE_in_rewrite_tree_ebnf1262 = new BitSet(new long[] { 4L });
/* 4022 */   public static final BitSet FOLLOW_rewrite_tree_block_in_rewrite_tree_ebnf1264 = new BitSet(new long[] { 8L });
/* 4023 */   public static final BitSet FOLLOW_TREE_BEGIN_in_rewrite_tree1278 = new BitSet(new long[] { 4L });
/* 4024 */   public static final BitSet FOLLOW_rewrite_tree_atom_in_rewrite_tree1280 = new BitSet(new long[] { 2515751592791816L });
/* 4025 */   public static final BitSet FOLLOW_rewrite_tree_element_in_rewrite_tree1282 = new BitSet(new long[] { 2515751592791816L });
/* 4026 */   public static final BitSet FOLLOW_TEMPLATE_in_rewrite_template1300 = new BitSet(new long[] { 4L });
/* 4027 */   public static final BitSet FOLLOW_ID_in_rewrite_template1302 = new BitSet(new long[] { 4194304L });
/* 4028 */   public static final BitSet FOLLOW_rewrite_template_args_in_rewrite_template1304 = new BitSet(new long[] { 13510798882111488L });
/* 4029 */   public static final BitSet FOLLOW_set_in_rewrite_template1311 = new BitSet(new long[] { 8L });
/* 4030 */   public static final BitSet FOLLOW_rewrite_template_ref_in_rewrite_template1327 = new BitSet(new long[] { 2L });
/* 4031 */   public static final BitSet FOLLOW_rewrite_indirect_template_head_in_rewrite_template1332 = new BitSet(new long[] { 2L });
/* 4032 */   public static final BitSet FOLLOW_ACTION_in_rewrite_template1337 = new BitSet(new long[] { 2L });
/* 4033 */   public static final BitSet FOLLOW_TEMPLATE_in_rewrite_template_ref1351 = new BitSet(new long[] { 4L });
/* 4034 */   public static final BitSet FOLLOW_ID_in_rewrite_template_ref1353 = new BitSet(new long[] { 4194304L });
/* 4035 */   public static final BitSet FOLLOW_rewrite_template_args_in_rewrite_template_ref1355 = new BitSet(new long[] { 8L });
/* 4036 */   public static final BitSet FOLLOW_TEMPLATE_in_rewrite_indirect_template_head1370 = new BitSet(new long[] { 4L });
/* 4037 */   public static final BitSet FOLLOW_ACTION_in_rewrite_indirect_template_head1372 = new BitSet(new long[] { 4194304L });
/* 4038 */   public static final BitSet FOLLOW_rewrite_template_args_in_rewrite_indirect_template_head1374 = new BitSet(new long[] { 8L });
/* 4039 */   public static final BitSet FOLLOW_ARGLIST_in_rewrite_template_args1387 = new BitSet(new long[] { 4L });
/* 4040 */   public static final BitSet FOLLOW_rewrite_template_arg_in_rewrite_template_args1389 = new BitSet(new long[] { 2097160L });
/* 4041 */   public static final BitSet FOLLOW_ARGLIST_in_rewrite_template_args1396 = new BitSet(new long[] { 2L });
/* 4042 */   public static final BitSet FOLLOW_ARG_in_rewrite_template_arg1410 = new BitSet(new long[] { 4L });
/* 4043 */   public static final BitSet FOLLOW_ID_in_rewrite_template_arg1412 = new BitSet(new long[] { 140737488355328L });
/* 4044 */   public static final BitSet FOLLOW_ACTION_in_rewrite_template_arg1414 = new BitSet(new long[] { 8L });
/* 4045 */   public static final BitSet FOLLOW_ID_in_qid1425 = new BitSet(new long[] { 2L, 67108864L });
/* 4046 */   public static final BitSet FOLLOW_90_in_qid1428 = new BitSet(new long[] { 1048576L });
/* 4047 */   public static final BitSet FOLLOW_ID_in_qid1430 = new BitSet(new long[] { 2L, 67108864L });
/*      */ 
/*      */   public ANTLRv3Tree(TreeNodeStream input)
/*      */   {
/*  113 */     this(input, new RecognizerSharedState());
/*      */   }
/*      */   public ANTLRv3Tree(TreeNodeStream input, RecognizerSharedState state) {
/*  116 */     super(input, state);
/*      */   }
/*      */ 
/*      */   public String[] getTokenNames()
/*      */   {
/*  121 */     return tokenNames; } 
/*  122 */   public String getGrammarFileName() { return "org/antlr/grammar/v3/ANTLRv3Tree.g"; }
/*      */ 
/*      */ 
/*      */   public final void grammarDef()
/*      */     throws RecognitionException
/*      */   {
/*      */     try
/*      */     {
/*  133 */       pushFollow(FOLLOW_grammarType_in_grammarDef52);
/*  134 */       grammarType();
/*      */ 
/*  136 */       this.state._fsp -= 1;
/*      */ 
/*  139 */       match(this.input, 2, null);
/*  140 */       match(this.input, 20, FOLLOW_ID_in_grammarDef54);
/*      */ 
/*  142 */       int alt1 = 2;
/*  143 */       switch (this.input.LA(1))
/*      */       {
/*      */       case 4:
/*  146 */         alt1 = 1;
/*      */       }
/*      */ 
/*  151 */       switch (alt1)
/*      */       {
/*      */       case 1:
/*  155 */         match(this.input, 4, FOLLOW_DOC_COMMENT_in_grammarDef56);
/*      */       }
/*      */ 
/*  163 */       int alt2 = 2;
/*  164 */       switch (this.input.LA(1))
/*      */       {
/*      */       case 48:
/*  167 */         alt2 = 1;
/*      */       }
/*      */ 
/*  172 */       switch (alt2)
/*      */       {
/*      */       case 1:
/*  176 */         pushFollow(FOLLOW_optionsSpec_in_grammarDef59);
/*  177 */         optionsSpec();
/*      */ 
/*  179 */         this.state._fsp -= 1;
/*      */       }
/*      */ 
/*  188 */       int alt3 = 2;
/*  189 */       switch (this.input.LA(1))
/*      */       {
/*      */       case 43:
/*  192 */         alt3 = 1;
/*      */       }
/*      */ 
/*  197 */       switch (alt3)
/*      */       {
/*      */       case 1:
/*  201 */         pushFollow(FOLLOW_tokensSpec_in_grammarDef62);
/*  202 */         tokensSpec();
/*      */ 
/*  204 */         this.state._fsp -= 1;
/*      */       }
/*      */ 
/*      */       while (true)
/*      */       {
/*  215 */         int alt4 = 2;
/*  216 */         switch (this.input.LA(1))
/*      */         {
/*      */         case 30:
/*  219 */           alt4 = 1;
/*      */         }
/*      */ 
/*  225 */         switch (alt4)
/*      */         {
/*      */         case 1:
/*  229 */           pushFollow(FOLLOW_attrScope_in_grammarDef65);
/*  230 */           attrScope();
/*      */ 
/*  232 */           this.state._fsp -= 1;
/*      */ 
/*  236 */           break;
/*      */         default:
/*  239 */           break label353;
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/*      */       while (true)
/*      */       {
/*  246 */         label353: int alt5 = 2;
/*  247 */         switch (this.input.LA(1))
/*      */         {
/*      */         case 40:
/*  250 */           alt5 = 1;
/*      */         }
/*      */ 
/*  256 */         switch (alt5)
/*      */         {
/*      */         case 1:
/*  260 */           pushFollow(FOLLOW_action_in_grammarDef68);
/*  261 */           action();
/*      */ 
/*  263 */           this.state._fsp -= 1;
/*      */ 
/*  267 */           break;
/*      */         default:
/*  270 */           break label441;
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/*  275 */       label441: int cnt6 = 0;
/*      */       while (true)
/*      */       {
/*  278 */         int alt6 = 2;
/*  279 */         switch (this.input.LA(1))
/*      */         {
/*      */         case 7:
/*  282 */           alt6 = 1;
/*      */         }
/*      */ 
/*  288 */         switch (alt6)
/*      */         {
/*      */         case 1:
/*  292 */           pushFollow(FOLLOW_rule_in_grammarDef71);
/*  293 */           rule();
/*      */ 
/*  295 */           this.state._fsp -= 1;
/*      */ 
/*  299 */           break;
/*      */         default:
/*  302 */           if (cnt6 >= 1) break label560;
/*  303 */           EarlyExitException eee = new EarlyExitException(6, this.input);
/*      */ 
/*  305 */           throw eee;
/*      */         }
/*  307 */         cnt6++;
/*      */       }
/*      */ 
/*  311 */       label560: match(this.input, 3, null);
/*      */     }
/*      */     catch (RecognitionException re)
/*      */     {
/*  316 */       re = 
/*  321 */         re;
/*      */ 
/*  317 */       reportError(re);
/*  318 */       recover(this.input, re);
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public final void grammarType()
/*      */     throws RecognitionException
/*      */   {
/*      */     try
/*      */     {
/*  334 */       if ((this.input.LA(1) >= 24) && (this.input.LA(1) <= 27)) {
/*  335 */         this.input.consume();
/*  336 */         this.state.errorRecovery = false;
/*      */       }
/*      */       else {
/*  339 */         MismatchedSetException mse = new MismatchedSetException(null, this.input);
/*  340 */         throw mse;
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (RecognitionException re)
/*      */     {
/*  347 */       re = 
/*  352 */         re;
/*      */ 
/*  348 */       reportError(re);
/*  349 */       recover(this.input, re);
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public final void tokensSpec()
/*      */     throws RecognitionException
/*      */   {
/*      */     try
/*      */     {
/*  365 */       match(this.input, 43, FOLLOW_TOKENS_in_tokensSpec127);
/*      */ 
/*  367 */       match(this.input, 2, null);
/*      */ 
/*  369 */       int cnt7 = 0;
/*      */       while (true)
/*      */       {
/*  372 */         int alt7 = 2;
/*  373 */         switch (this.input.LA(1))
/*      */         {
/*      */         case 41:
/*      */         case 44:
/*  377 */           alt7 = 1;
/*      */         }
/*      */ 
/*  383 */         switch (alt7)
/*      */         {
/*      */         case 1:
/*  387 */           pushFollow(FOLLOW_tokenSpec_in_tokensSpec129);
/*  388 */           tokenSpec();
/*      */ 
/*  390 */           this.state._fsp -= 1;
/*      */ 
/*  394 */           break;
/*      */         default:
/*  397 */           if (cnt7 >= 1) break label141;
/*  398 */           EarlyExitException eee = new EarlyExitException(7, this.input);
/*      */ 
/*  400 */           throw eee;
/*      */         }
/*  402 */         cnt7++;
/*      */       }
/*      */ 
/*  406 */       label141: match(this.input, 3, null);
/*      */     }
/*      */     catch (RecognitionException re)
/*      */     {
/*  411 */       re = 
/*  416 */         re;
/*      */ 
/*  412 */       reportError(re);
/*  413 */       recover(this.input, re);
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public final void tokenSpec()
/*      */     throws RecognitionException
/*      */   {
/*      */     try
/*      */     {
/*  427 */       int alt8 = 3;
/*  428 */       switch (this.input.LA(1))
/*      */       {
/*      */       case 41:
/*  431 */         switch (this.input.LA(2))
/*      */         {
/*      */         case 2:
/*  434 */           switch (this.input.LA(3))
/*      */           {
/*      */           case 44:
/*  437 */             switch (this.input.LA(4))
/*      */             {
/*      */             case 45:
/*  440 */               alt8 = 1;
/*      */ 
/*  442 */               break;
/*      */             case 46:
/*  445 */               alt8 = 2;
/*      */ 
/*  447 */               break;
/*      */             default:
/*  449 */               NoViableAltException nvae = new NoViableAltException("", 8, 4, this.input);
/*      */ 
/*  452 */               throw nvae;
/*      */             }
/*      */ 
/*  456 */             break;
/*      */           default:
/*  458 */             NoViableAltException nvae = new NoViableAltException("", 8, 3, this.input);
/*      */ 
/*  461 */             throw nvae;
/*      */           }
/*      */ 
/*  465 */           break;
/*      */         default:
/*  467 */           NoViableAltException nvae = new NoViableAltException("", 8, 1, this.input);
/*      */ 
/*  470 */           throw nvae;
/*      */         }
/*      */ 
/*  474 */         break;
/*      */       case 44:
/*  477 */         alt8 = 3;
/*      */ 
/*  479 */         break;
/*      */       default:
/*  481 */         NoViableAltException nvae = new NoViableAltException("", 8, 0, this.input);
/*      */ 
/*  484 */         throw nvae;
/*      */       }
/*      */ 
/*  487 */       switch (alt8)
/*      */       {
/*      */       case 1:
/*  491 */         match(this.input, 41, FOLLOW_LABEL_ASSIGN_in_tokenSpec143);
/*      */ 
/*  493 */         match(this.input, 2, null);
/*  494 */         match(this.input, 44, FOLLOW_TOKEN_REF_in_tokenSpec145);
/*  495 */         match(this.input, 45, FOLLOW_STRING_LITERAL_in_tokenSpec147);
/*      */ 
/*  497 */         match(this.input, 3, null);
/*      */ 
/*  500 */         break;
/*      */       case 2:
/*  504 */         match(this.input, 41, FOLLOW_LABEL_ASSIGN_in_tokenSpec154);
/*      */ 
/*  506 */         match(this.input, 2, null);
/*  507 */         match(this.input, 44, FOLLOW_TOKEN_REF_in_tokenSpec156);
/*  508 */         match(this.input, 46, FOLLOW_CHAR_LITERAL_in_tokenSpec158);
/*      */ 
/*  510 */         match(this.input, 3, null);
/*      */ 
/*  513 */         break;
/*      */       case 3:
/*  517 */         match(this.input, 44, FOLLOW_TOKEN_REF_in_tokenSpec164);
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (RecognitionException re)
/*      */     {
/*  524 */       re = 
/*  529 */         re;
/*      */ 
/*  525 */       reportError(re);
/*  526 */       recover(this.input, re);
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public final void attrScope()
/*      */     throws RecognitionException
/*      */   {
/*      */     try
/*      */     {
/*  542 */       match(this.input, 30, FOLLOW_SCOPE_in_attrScope176);
/*      */ 
/*  544 */       match(this.input, 2, null);
/*  545 */       match(this.input, 20, FOLLOW_ID_in_attrScope178);
/*  546 */       match(this.input, 47, FOLLOW_ACTION_in_attrScope180);
/*      */ 
/*  548 */       match(this.input, 3, null);
/*      */     }
/*      */     catch (RecognitionException re)
/*      */     {
/*  553 */       re = 
/*  558 */         re;
/*      */ 
/*  554 */       reportError(re);
/*  555 */       recover(this.input, re);
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public final void action()
/*      */     throws RecognitionException
/*      */   {
/*      */     try
/*      */     {
/*  569 */       int alt9 = 2;
/*  570 */       switch (this.input.LA(1))
/*      */       {
/*      */       case 40:
/*  573 */         switch (this.input.LA(2))
/*      */         {
/*      */         case 2:
/*  576 */           switch (this.input.LA(3))
/*      */           {
/*      */           case 20:
/*  579 */             switch (this.input.LA(4))
/*      */             {
/*      */             case 20:
/*  582 */               alt9 = 1;
/*      */ 
/*  584 */               break;
/*      */             case 47:
/*  587 */               alt9 = 2;
/*      */ 
/*  589 */               break;
/*      */             default:
/*  591 */               NoViableAltException nvae = new NoViableAltException("", 9, 3, this.input);
/*      */ 
/*  594 */               throw nvae;
/*      */             }
/*      */ 
/*  598 */             break;
/*      */           default:
/*  600 */             NoViableAltException nvae = new NoViableAltException("", 9, 2, this.input);
/*      */ 
/*  603 */             throw nvae;
/*      */           }
/*      */ 
/*  607 */           break;
/*      */         default:
/*  609 */           NoViableAltException nvae = new NoViableAltException("", 9, 1, this.input);
/*      */ 
/*  612 */           throw nvae;
/*      */         }
/*      */ 
/*  616 */         break;
/*      */       default:
/*  618 */         NoViableAltException nvae = new NoViableAltException("", 9, 0, this.input);
/*      */ 
/*  621 */         throw nvae;
/*      */       }
/*      */ 
/*  624 */       switch (alt9)
/*      */       {
/*      */       case 1:
/*  628 */         match(this.input, 40, FOLLOW_AT_in_action193);
/*      */ 
/*  630 */         match(this.input, 2, null);
/*  631 */         match(this.input, 20, FOLLOW_ID_in_action195);
/*  632 */         match(this.input, 20, FOLLOW_ID_in_action197);
/*  633 */         match(this.input, 47, FOLLOW_ACTION_in_action199);
/*      */ 
/*  635 */         match(this.input, 3, null);
/*      */ 
/*  638 */         break;
/*      */       case 2:
/*  642 */         match(this.input, 40, FOLLOW_AT_in_action206);
/*      */ 
/*  644 */         match(this.input, 2, null);
/*  645 */         match(this.input, 20, FOLLOW_ID_in_action208);
/*  646 */         match(this.input, 47, FOLLOW_ACTION_in_action210);
/*      */ 
/*  648 */         match(this.input, 3, null);
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (RecognitionException re)
/*      */     {
/*  655 */       re = 
/*  660 */         re;
/*      */ 
/*  656 */       reportError(re);
/*  657 */       recover(this.input, re);
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
/*  673 */       match(this.input, 48, FOLLOW_OPTIONS_in_optionsSpec223);
/*      */ 
/*  675 */       match(this.input, 2, null);
/*      */ 
/*  677 */       int cnt10 = 0;
/*      */       while (true)
/*      */       {
/*  680 */         int alt10 = 2;
/*  681 */         switch (this.input.LA(1))
/*      */         {
/*      */         case 20:
/*      */         case 41:
/*  685 */           alt10 = 1;
/*      */         }
/*      */ 
/*  691 */         switch (alt10)
/*      */         {
/*      */         case 1:
/*  695 */           pushFollow(FOLLOW_option_in_optionsSpec225);
/*  696 */           option();
/*      */ 
/*  698 */           this.state._fsp -= 1;
/*      */ 
/*  702 */           break;
/*      */         default:
/*  705 */           if (cnt10 >= 1) break label141;
/*  706 */           EarlyExitException eee = new EarlyExitException(10, this.input);
/*      */ 
/*  708 */           throw eee;
/*      */         }
/*  710 */         cnt10++;
/*      */       }
/*      */ 
/*  714 */       label141: match(this.input, 3, null);
/*      */     }
/*      */     catch (RecognitionException re)
/*      */     {
/*  719 */       re = 
/*  724 */         re;
/*      */ 
/*  720 */       reportError(re);
/*  721 */       recover(this.input, re);
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public final void option()
/*      */     throws RecognitionException
/*      */   {
/*      */     try
/*      */     {
/*  735 */       int alt11 = 2;
/*  736 */       switch (this.input.LA(1))
/*      */       {
/*      */       case 20:
/*  739 */         alt11 = 1;
/*      */ 
/*  741 */         break;
/*      */       case 41:
/*  744 */         alt11 = 2;
/*      */ 
/*  746 */         break;
/*      */       default:
/*  748 */         NoViableAltException nvae = new NoViableAltException("", 11, 0, this.input);
/*      */ 
/*  751 */         throw nvae;
/*      */       }
/*      */ 
/*  754 */       switch (alt11)
/*      */       {
/*      */       case 1:
/*  758 */         pushFollow(FOLLOW_qid_in_option243);
/*  759 */         qid();
/*      */ 
/*  761 */         this.state._fsp -= 1;
/*      */ 
/*  765 */         break;
/*      */       case 2:
/*  769 */         match(this.input, 41, FOLLOW_LABEL_ASSIGN_in_option253);
/*      */ 
/*  771 */         match(this.input, 2, null);
/*  772 */         match(this.input, 20, FOLLOW_ID_in_option255);
/*  773 */         pushFollow(FOLLOW_optionValue_in_option257);
/*  774 */         optionValue();
/*      */ 
/*  776 */         this.state._fsp -= 1;
/*      */ 
/*  779 */         match(this.input, 3, null);
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (RecognitionException re)
/*      */     {
/*  786 */       re = 
/*  791 */         re;
/*      */ 
/*  787 */       reportError(re);
/*  788 */       recover(this.input, re);
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public final void optionValue()
/*      */     throws RecognitionException
/*      */   {
/*      */     try
/*      */     {
/*  804 */       if ((this.input.LA(1) == 20) || ((this.input.LA(1) >= 45) && (this.input.LA(1) <= 46)) || (this.input.LA(1) == 49)) {
/*  805 */         this.input.consume();
/*  806 */         this.state.errorRecovery = false;
/*      */       }
/*      */       else {
/*  809 */         MismatchedSetException mse = new MismatchedSetException(null, this.input);
/*  810 */         throw mse;
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (RecognitionException re)
/*      */     {
/*  817 */       re = 
/*  822 */         re;
/*      */ 
/*  818 */       reportError(re);
/*  819 */       recover(this.input, re);
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public final void rule()
/*      */     throws RecognitionException
/*      */   {
/*      */     try
/*      */     {
/*  835 */       match(this.input, 7, FOLLOW_RULE_in_rule323);
/*      */ 
/*  837 */       match(this.input, 2, null);
/*  838 */       match(this.input, 20, FOLLOW_ID_in_rule325);
/*      */ 
/*  840 */       int alt12 = 2;
/*  841 */       switch (this.input.LA(1))
/*      */       {
/*      */       case 35:
/*      */       case 75:
/*      */       case 76:
/*      */       case 77:
/*  847 */         alt12 = 1;
/*      */       }
/*      */ 
/*  852 */       switch (alt12)
/*      */       {
/*      */       case 1:
/*  856 */         pushFollow(FOLLOW_modifier_in_rule327);
/*  857 */         modifier();
/*      */ 
/*  859 */         this.state._fsp -= 1;
/*      */       }
/*      */ 
/*  868 */       int alt13 = 2;
/*  869 */       switch (this.input.LA(1))
/*      */       {
/*      */       case 21:
/*  872 */         alt13 = 1;
/*      */       }
/*      */ 
/*  877 */       switch (alt13)
/*      */       {
/*      */       case 1:
/*  881 */         match(this.input, 21, FOLLOW_ARG_in_rule332);
/*      */ 
/*  883 */         match(this.input, 2, null);
/*  884 */         match(this.input, 50, FOLLOW_ARG_ACTION_in_rule334);
/*      */ 
/*  886 */         match(this.input, 3, null);
/*      */       }
/*      */ 
/*  894 */       int alt14 = 2;
/*  895 */       switch (this.input.LA(1))
/*      */       {
/*      */       case 23:
/*  898 */         alt14 = 1;
/*      */       }
/*      */ 
/*  903 */       switch (alt14)
/*      */       {
/*      */       case 1:
/*  907 */         match(this.input, 23, FOLLOW_RET_in_rule341);
/*      */ 
/*  909 */         match(this.input, 2, null);
/*  910 */         match(this.input, 50, FOLLOW_ARG_ACTION_in_rule343);
/*      */ 
/*  912 */         match(this.input, 3, null);
/*      */       }
/*      */ 
/*  920 */       int alt15 = 2;
/*  921 */       switch (this.input.LA(1))
/*      */       {
/*      */       case 79:
/*  924 */         alt15 = 1;
/*      */       }
/*      */ 
/*  929 */       switch (alt15)
/*      */       {
/*      */       case 1:
/*  933 */         pushFollow(FOLLOW_throwsSpec_in_rule356);
/*  934 */         throwsSpec();
/*      */ 
/*  936 */         this.state._fsp -= 1;
/*      */       }
/*      */ 
/*  945 */       int alt16 = 2;
/*  946 */       switch (this.input.LA(1))
/*      */       {
/*      */       case 48:
/*  949 */         alt16 = 1;
/*      */       }
/*      */ 
/*  954 */       switch (alt16)
/*      */       {
/*      */       case 1:
/*  958 */         pushFollow(FOLLOW_optionsSpec_in_rule359);
/*  959 */         optionsSpec();
/*      */ 
/*  961 */         this.state._fsp -= 1;
/*      */       }
/*      */ 
/*  970 */       int alt17 = 2;
/*  971 */       switch (this.input.LA(1))
/*      */       {
/*      */       case 30:
/*  974 */         alt17 = 1;
/*      */       }
/*      */ 
/*  979 */       switch (alt17)
/*      */       {
/*      */       case 1:
/*  983 */         pushFollow(FOLLOW_ruleScopeSpec_in_rule362);
/*  984 */         ruleScopeSpec();
/*      */ 
/*  986 */         this.state._fsp -= 1;
/*      */       }
/*      */ 
/*      */       while (true)
/*      */       {
/*  997 */         int alt18 = 2;
/*  998 */         switch (this.input.LA(1))
/*      */         {
/*      */         case 40:
/* 1001 */           alt18 = 1;
/*      */         }
/*      */ 
/* 1007 */         switch (alt18)
/*      */         {
/*      */         case 1:
/* 1011 */           pushFollow(FOLLOW_ruleAction_in_rule365);
/* 1012 */           ruleAction();
/*      */ 
/* 1014 */           this.state._fsp -= 1;
/*      */ 
/* 1018 */           break;
/*      */         default:
/* 1021 */           break label665;
/*      */         }
/*      */       }
/*      */ 
/* 1025 */       label665: pushFollow(FOLLOW_altList_in_rule376);
/* 1026 */       altList();
/*      */ 
/* 1028 */       this.state._fsp -= 1;
/*      */ 
/* 1031 */       int alt19 = 2;
/* 1032 */       switch (this.input.LA(1))
/*      */       {
/*      */       case 84:
/*      */       case 85:
/* 1036 */         alt19 = 1;
/*      */       }
/*      */ 
/* 1041 */       switch (alt19)
/*      */       {
/*      */       case 1:
/* 1045 */         pushFollow(FOLLOW_exceptionGroup_in_rule386);
/* 1046 */         exceptionGroup();
/*      */ 
/* 1048 */         this.state._fsp -= 1;
/*      */       }
/*      */ 
/* 1056 */       match(this.input, 17, FOLLOW_EOR_in_rule389);
/*      */ 
/* 1058 */       match(this.input, 3, null);
/*      */     }
/*      */     catch (RecognitionException re)
/*      */     {
/* 1063 */       re = 
/* 1068 */         re;
/*      */ 
/* 1064 */       reportError(re);
/* 1065 */       recover(this.input, re);
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public final void modifier()
/*      */     throws RecognitionException
/*      */   {
/*      */     try
/*      */     {
/* 1081 */       if ((this.input.LA(1) == 35) || ((this.input.LA(1) >= 75) && (this.input.LA(1) <= 77))) {
/* 1082 */         this.input.consume();
/* 1083 */         this.state.errorRecovery = false;
/*      */       }
/*      */       else {
/* 1086 */         MismatchedSetException mse = new MismatchedSetException(null, this.input);
/* 1087 */         throw mse;
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (RecognitionException re)
/*      */     {
/* 1094 */       re = 
/* 1099 */         re;
/*      */ 
/* 1095 */       reportError(re);
/* 1096 */       recover(this.input, re);
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public final void ruleAction()
/*      */     throws RecognitionException
/*      */   {
/*      */     try
/*      */     {
/* 1112 */       match(this.input, 40, FOLLOW_AT_in_ruleAction428);
/*      */ 
/* 1114 */       match(this.input, 2, null);
/* 1115 */       match(this.input, 20, FOLLOW_ID_in_ruleAction430);
/* 1116 */       match(this.input, 47, FOLLOW_ACTION_in_ruleAction432);
/*      */ 
/* 1118 */       match(this.input, 3, null);
/*      */     }
/*      */     catch (RecognitionException re)
/*      */     {
/* 1123 */       re = 
/* 1128 */         re;
/*      */ 
/* 1124 */       reportError(re);
/* 1125 */       recover(this.input, re);
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public final void throwsSpec()
/*      */     throws RecognitionException
/*      */   {
/*      */     try
/*      */     {
/* 1141 */       match(this.input, 79, FOLLOW_79_in_throwsSpec445);
/*      */ 
/* 1143 */       match(this.input, 2, null);
/*      */ 
/* 1145 */       int cnt20 = 0;
/*      */       while (true)
/*      */       {
/* 1148 */         int alt20 = 2;
/* 1149 */         switch (this.input.LA(1))
/*      */         {
/*      */         case 20:
/* 1152 */           alt20 = 1;
/*      */         }
/*      */ 
/* 1158 */         switch (alt20)
/*      */         {
/*      */         case 1:
/* 1162 */           match(this.input, 20, FOLLOW_ID_in_throwsSpec447);
/*      */ 
/* 1165 */           break;
/*      */         default:
/* 1168 */           if (cnt20 >= 1) break label123;
/* 1169 */           EarlyExitException eee = new EarlyExitException(20, this.input);
/*      */ 
/* 1171 */           throw eee;
/*      */         }
/* 1173 */         cnt20++;
/*      */       }
/*      */ 
/* 1177 */       label123: match(this.input, 3, null);
/*      */     }
/*      */     catch (RecognitionException re)
/*      */     {
/* 1182 */       re = 
/* 1187 */         re;
/*      */ 
/* 1183 */       reportError(re);
/* 1184 */       recover(this.input, re);
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public final void ruleScopeSpec()
/*      */     throws RecognitionException
/*      */   {
/*      */     try
/*      */     {
/* 1198 */       int alt23 = 3;
/* 1199 */       switch (this.input.LA(1))
/*      */       {
/*      */       case 30:
/* 1202 */         switch (this.input.LA(2))
/*      */         {
/*      */         case 2:
/* 1205 */           switch (this.input.LA(3))
/*      */           {
/*      */           case 47:
/* 1208 */             switch (this.input.LA(4))
/*      */             {
/*      */             case 3:
/* 1211 */               alt23 = 1;
/*      */ 
/* 1213 */               break;
/*      */             case 20:
/* 1216 */               alt23 = 2;
/*      */ 
/* 1218 */               break;
/*      */             default:
/* 1220 */               NoViableAltException nvae = new NoViableAltException("", 23, 3, this.input);
/*      */ 
/* 1223 */               throw nvae;
/*      */             }
/*      */ 
/* 1227 */             break;
/*      */           case 20:
/* 1230 */             alt23 = 3;
/*      */ 
/* 1232 */             break;
/*      */           default:
/* 1234 */             NoViableAltException nvae = new NoViableAltException("", 23, 2, this.input);
/*      */ 
/* 1237 */             throw nvae;
/*      */           }
/*      */ 
/* 1241 */           break;
/*      */         default:
/* 1243 */           NoViableAltException nvae = new NoViableAltException("", 23, 1, this.input);
/*      */ 
/* 1246 */           throw nvae;
/*      */         }
/*      */ 
/* 1250 */         break;
/*      */       default:
/* 1252 */         NoViableAltException nvae = new NoViableAltException("", 23, 0, this.input);
/*      */ 
/* 1255 */         throw nvae;
/*      */       }
/*      */ 
/* 1258 */       switch (alt23)
/*      */       {
/*      */       case 1:
/* 1262 */         match(this.input, 30, FOLLOW_SCOPE_in_ruleScopeSpec461);
/*      */ 
/* 1264 */         match(this.input, 2, null);
/* 1265 */         match(this.input, 47, FOLLOW_ACTION_in_ruleScopeSpec463);
/*      */ 
/* 1267 */         match(this.input, 3, null);
/*      */ 
/* 1270 */         break;
/*      */       case 2:
/* 1274 */         match(this.input, 30, FOLLOW_SCOPE_in_ruleScopeSpec470);
/*      */ 
/* 1276 */         match(this.input, 2, null);
/* 1277 */         match(this.input, 47, FOLLOW_ACTION_in_ruleScopeSpec472);
/*      */ 
/* 1279 */         int cnt21 = 0;
/*      */         while (true)
/*      */         {
/* 1282 */           int alt21 = 2;
/* 1283 */           switch (this.input.LA(1))
/*      */           {
/*      */           case 20:
/* 1286 */             alt21 = 1;
/*      */           }
/*      */ 
/* 1292 */           switch (alt21)
/*      */           {
/*      */           case 1:
/* 1296 */             match(this.input, 20, FOLLOW_ID_in_ruleScopeSpec474);
/*      */ 
/* 1299 */             break;
/*      */           default:
/* 1302 */             if (cnt21 >= 1) break label453;
/* 1303 */             EarlyExitException eee = new EarlyExitException(21, this.input);
/*      */ 
/* 1305 */             throw eee;
/*      */           }
/* 1307 */           cnt21++;
/*      */         }
/*      */ 
/* 1311 */         match(this.input, 3, null);
/*      */ 
/* 1314 */         break;
/*      */       case 3:
/* 1318 */         label453: match(this.input, 30, FOLLOW_SCOPE_in_ruleScopeSpec482);
/*      */ 
/* 1320 */         match(this.input, 2, null);
/*      */ 
/* 1322 */         int cnt22 = 0;
/*      */         while (true)
/*      */         {
/* 1325 */           int alt22 = 2;
/* 1326 */           switch (this.input.LA(1))
/*      */           {
/*      */           case 20:
/* 1329 */             alt22 = 1;
/*      */           }
/*      */ 
/* 1335 */           switch (alt22)
/*      */           {
/*      */           case 1:
/* 1339 */             match(this.input, 20, FOLLOW_ID_in_ruleScopeSpec484);
/*      */ 
/* 1342 */             break;
/*      */           default:
/* 1345 */             if (cnt22 >= 1) break label593;
/* 1346 */             EarlyExitException eee = new EarlyExitException(22, this.input);
/*      */ 
/* 1348 */             throw eee;
/*      */           }
/* 1350 */           cnt22++;
/*      */         }
/*      */ 
/* 1354 */         label593: match(this.input, 3, null);
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (RecognitionException re)
/*      */     {
/* 1361 */       re = 
/* 1366 */         re;
/*      */ 
/* 1362 */       reportError(re);
/* 1363 */       recover(this.input, re);
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public final void block()
/*      */     throws RecognitionException
/*      */   {
/*      */     try
/*      */     {
/* 1379 */       match(this.input, 8, FOLLOW_BLOCK_in_block504);
/*      */ 
/* 1381 */       match(this.input, 2, null);
/*      */ 
/* 1383 */       int alt24 = 2;
/* 1384 */       switch (this.input.LA(1))
/*      */       {
/*      */       case 48:
/* 1387 */         alt24 = 1;
/*      */       }
/*      */ 
/* 1392 */       switch (alt24)
/*      */       {
/*      */       case 1:
/* 1396 */         pushFollow(FOLLOW_optionsSpec_in_block506);
/* 1397 */         optionsSpec();
/*      */ 
/* 1399 */         this.state._fsp -= 1;
/*      */       }
/*      */ 
/* 1408 */       int cnt25 = 0;
/*      */       while (true)
/*      */       {
/* 1411 */         int alt25 = 2;
/* 1412 */         switch (this.input.LA(1))
/*      */         {
/*      */         case 16:
/* 1415 */           alt25 = 1;
/*      */         }
/*      */ 
/* 1421 */         switch (alt25)
/*      */         {
/*      */         case 1:
/* 1425 */           pushFollow(FOLLOW_alternative_in_block510);
/* 1426 */           alternative();
/*      */ 
/* 1428 */           this.state._fsp -= 1;
/*      */ 
/* 1430 */           pushFollow(FOLLOW_rewrite_in_block512);
/* 1431 */           rewrite();
/*      */ 
/* 1433 */           this.state._fsp -= 1;
/*      */ 
/* 1437 */           break;
/*      */         default:
/* 1440 */           if (cnt25 >= 1) break label235;
/* 1441 */           EarlyExitException eee = new EarlyExitException(25, this.input);
/*      */ 
/* 1443 */           throw eee;
/*      */         }
/* 1445 */         cnt25++;
/*      */       }
/*      */ 
/* 1448 */       label235: match(this.input, 18, FOLLOW_EOB_in_block516);
/*      */ 
/* 1450 */       match(this.input, 3, null);
/*      */     }
/*      */     catch (RecognitionException re)
/*      */     {
/* 1455 */       re = 
/* 1460 */         re;
/*      */ 
/* 1456 */       reportError(re);
/* 1457 */       recover(this.input, re);
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public final void altList()
/*      */     throws RecognitionException
/*      */   {
/*      */     try
/*      */     {
/* 1473 */       match(this.input, 8, FOLLOW_BLOCK_in_altList539);
/*      */ 
/* 1475 */       match(this.input, 2, null);
/*      */ 
/* 1477 */       int cnt26 = 0;
/*      */       while (true)
/*      */       {
/* 1480 */         int alt26 = 2;
/* 1481 */         switch (this.input.LA(1))
/*      */         {
/*      */         case 16:
/* 1484 */           alt26 = 1;
/*      */         }
/*      */ 
/* 1490 */         switch (alt26)
/*      */         {
/*      */         case 1:
/* 1494 */           pushFollow(FOLLOW_alternative_in_altList542);
/* 1495 */           alternative();
/*      */ 
/* 1497 */           this.state._fsp -= 1;
/*      */ 
/* 1499 */           pushFollow(FOLLOW_rewrite_in_altList544);
/* 1500 */           rewrite();
/*      */ 
/* 1502 */           this.state._fsp -= 1;
/*      */ 
/* 1506 */           break;
/*      */         default:
/* 1509 */           if (cnt26 >= 1) break label157;
/* 1510 */           EarlyExitException eee = new EarlyExitException(26, this.input);
/*      */ 
/* 1512 */           throw eee;
/*      */         }
/* 1514 */         cnt26++;
/*      */       }
/*      */ 
/* 1517 */       label157: match(this.input, 18, FOLLOW_EOB_in_altList548);
/*      */ 
/* 1519 */       match(this.input, 3, null);
/*      */     }
/*      */     catch (RecognitionException re)
/*      */     {
/* 1524 */       re = 
/* 1529 */         re;
/*      */ 
/* 1525 */       reportError(re);
/* 1526 */       recover(this.input, re);
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public final void alternative()
/*      */     throws RecognitionException
/*      */   {
/*      */     try
/*      */     {
/* 1540 */       int alt28 = 2;
/* 1541 */       switch (this.input.LA(1))
/*      */       {
/*      */       case 16:
/* 1544 */         switch (this.input.LA(2))
/*      */         {
/*      */         case 2:
/* 1547 */           switch (this.input.LA(3))
/*      */           {
/*      */           case 15:
/* 1550 */             alt28 = 2;
/*      */ 
/* 1552 */             break;
/*      */           case 8:
/*      */           case 9:
/*      */           case 10:
/*      */           case 11:
/*      */           case 12:
/*      */           case 14:
/*      */           case 31:
/*      */           case 32:
/*      */           case 33:
/*      */           case 36:
/*      */           case 37:
/*      */           case 38:
/*      */           case 41:
/*      */           case 42:
/*      */           case 44:
/*      */           case 45:
/*      */           case 46:
/*      */           case 47:
/*      */           case 51:
/*      */           case 87:
/*      */           case 90:
/* 1575 */             alt28 = 1;
/*      */ 
/* 1577 */             break;
/*      */           case 13:
/*      */           case 16:
/*      */           case 17:
/*      */           case 18:
/*      */           case 19:
/*      */           case 20:
/*      */           case 21:
/*      */           case 22:
/*      */           case 23:
/*      */           case 24:
/*      */           case 25:
/*      */           case 26:
/*      */           case 27:
/*      */           case 28:
/*      */           case 29:
/*      */           case 30:
/*      */           case 34:
/*      */           case 35:
/*      */           case 39:
/*      */           case 40:
/*      */           case 43:
/*      */           case 48:
/*      */           case 49:
/*      */           case 50:
/*      */           case 52:
/*      */           case 53:
/*      */           case 54:
/*      */           case 55:
/*      */           case 56:
/*      */           case 57:
/*      */           case 58:
/*      */           case 59:
/*      */           case 60:
/*      */           case 61:
/*      */           case 62:
/*      */           case 63:
/*      */           case 64:
/*      */           case 65:
/*      */           case 66:
/*      */           case 67:
/*      */           case 68:
/*      */           case 69:
/*      */           case 70:
/*      */           case 71:
/*      */           case 72:
/*      */           case 73:
/*      */           case 74:
/*      */           case 75:
/*      */           case 76:
/*      */           case 77:
/*      */           case 78:
/*      */           case 79:
/*      */           case 80:
/*      */           case 81:
/*      */           case 82:
/*      */           case 83:
/*      */           case 84:
/*      */           case 85:
/*      */           case 86:
/*      */           case 88:
/*      */           case 89:
/*      */           default:
/* 1579 */             NoViableAltException nvae = new NoViableAltException("", 28, 2, this.input);
/*      */ 
/* 1582 */             throw nvae;
/*      */           }
/*      */ 
/* 1586 */           break;
/*      */         default:
/* 1588 */           NoViableAltException nvae = new NoViableAltException("", 28, 1, this.input);
/*      */ 
/* 1591 */           throw nvae;
/*      */         }
/*      */ 
/* 1595 */         break;
/*      */       default:
/* 1597 */         NoViableAltException nvae = new NoViableAltException("", 28, 0, this.input);
/*      */ 
/* 1600 */         throw nvae;
/*      */       }
/*      */ 
/* 1603 */       switch (alt28)
/*      */       {
/*      */       case 1:
/* 1607 */         match(this.input, 16, FOLLOW_ALT_in_alternative570);
/*      */ 
/* 1609 */         match(this.input, 2, null);
/*      */ 
/* 1611 */         int cnt27 = 0;
/*      */         while (true)
/*      */         {
/* 1614 */           int alt27 = 2;
/* 1615 */           switch (this.input.LA(1))
/*      */           {
/*      */           case 8:
/*      */           case 9:
/*      */           case 10:
/*      */           case 11:
/*      */           case 12:
/*      */           case 14:
/*      */           case 31:
/*      */           case 32:
/*      */           case 33:
/*      */           case 36:
/*      */           case 37:
/*      */           case 38:
/*      */           case 41:
/*      */           case 42:
/*      */           case 44:
/*      */           case 45:
/*      */           case 46:
/*      */           case 47:
/*      */           case 51:
/*      */           case 87:
/*      */           case 90:
/* 1638 */             alt27 = 1;
/*      */           case 13:
/*      */           case 15:
/*      */           case 16:
/*      */           case 17:
/*      */           case 18:
/*      */           case 19:
/*      */           case 20:
/*      */           case 21:
/*      */           case 22:
/*      */           case 23:
/*      */           case 24:
/*      */           case 25:
/*      */           case 26:
/*      */           case 27:
/*      */           case 28:
/*      */           case 29:
/*      */           case 30:
/*      */           case 34:
/*      */           case 35:
/*      */           case 39:
/*      */           case 40:
/*      */           case 43:
/*      */           case 48:
/*      */           case 49:
/*      */           case 50:
/*      */           case 52:
/*      */           case 53:
/*      */           case 54:
/*      */           case 55:
/*      */           case 56:
/*      */           case 57:
/*      */           case 58:
/*      */           case 59:
/*      */           case 60:
/*      */           case 61:
/*      */           case 62:
/*      */           case 63:
/*      */           case 64:
/*      */           case 65:
/*      */           case 66:
/*      */           case 67:
/*      */           case 68:
/*      */           case 69:
/*      */           case 70:
/*      */           case 71:
/*      */           case 72:
/*      */           case 73:
/*      */           case 74:
/*      */           case 75:
/*      */           case 76:
/*      */           case 77:
/*      */           case 78:
/*      */           case 79:
/*      */           case 80:
/*      */           case 81:
/*      */           case 82:
/*      */           case 83:
/*      */           case 84:
/*      */           case 85:
/*      */           case 86:
/*      */           case 88:
/* 1644 */           case 89: } switch (alt27)
/*      */           {
/*      */           case 1:
/* 1648 */             pushFollow(FOLLOW_element_in_alternative572);
/* 1649 */             element();
/*      */ 
/* 1651 */             this.state._fsp -= 1;
/*      */ 
/* 1655 */             break;
/*      */           default:
/* 1658 */             if (cnt27 >= 1) break label979;
/* 1659 */             EarlyExitException eee = new EarlyExitException(27, this.input);
/*      */ 
/* 1661 */             throw eee;
/*      */           }
/* 1663 */           cnt27++;
/*      */         }
/*      */ 
/* 1666 */         match(this.input, 19, FOLLOW_EOA_in_alternative575);
/*      */ 
/* 1668 */         match(this.input, 3, null);
/*      */ 
/* 1671 */         break;
/*      */       case 2:
/* 1675 */         label979: match(this.input, 16, FOLLOW_ALT_in_alternative587);
/*      */ 
/* 1677 */         match(this.input, 2, null);
/* 1678 */         match(this.input, 15, FOLLOW_EPSILON_in_alternative589);
/* 1679 */         match(this.input, 19, FOLLOW_EOA_in_alternative591);
/*      */ 
/* 1681 */         match(this.input, 3, null);
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (RecognitionException re)
/*      */     {
/* 1688 */       re = 
/* 1693 */         re;
/*      */ 
/* 1689 */       reportError(re);
/* 1690 */       recover(this.input, re);
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public final void exceptionGroup()
/*      */     throws RecognitionException
/*      */   {
/*      */     try
/*      */     {
/* 1704 */       int alt31 = 2;
/* 1705 */       switch (this.input.LA(1))
/*      */       {
/*      */       case 84:
/* 1708 */         alt31 = 1;
/*      */ 
/* 1710 */         break;
/*      */       case 85:
/* 1713 */         alt31 = 2;
/*      */ 
/* 1715 */         break;
/*      */       default:
/* 1717 */         NoViableAltException nvae = new NoViableAltException("", 31, 0, this.input);
/*      */ 
/* 1720 */         throw nvae;
/*      */       }
/*      */ 
/* 1723 */       switch (alt31)
/*      */       {
/*      */       case 1:
/* 1728 */         int cnt29 = 0;
/*      */         while (true)
/*      */         {
/* 1731 */           int alt29 = 2;
/* 1732 */           switch (this.input.LA(1))
/*      */           {
/*      */           case 84:
/* 1735 */             alt29 = 1;
/*      */           }
/*      */ 
/* 1741 */           switch (alt29)
/*      */           {
/*      */           case 1:
/* 1745 */             pushFollow(FOLLOW_exceptionHandler_in_exceptionGroup606);
/* 1746 */             exceptionHandler();
/*      */ 
/* 1748 */             this.state._fsp -= 1;
/*      */ 
/* 1752 */             break;
/*      */           default:
/* 1755 */             if (cnt29 >= 1) break label207;
/* 1756 */             EarlyExitException eee = new EarlyExitException(29, this.input);
/*      */ 
/* 1758 */             throw eee;
/*      */           }
/* 1760 */           cnt29++;
/*      */         }
/*      */ 
/* 1764 */         int alt30 = 2;
/* 1765 */         switch (this.input.LA(1))
/*      */         {
/*      */         case 85:
/* 1768 */           alt30 = 1;
/*      */         }
/*      */ 
/* 1773 */         switch (alt30)
/*      */         {
/*      */         case 1:
/* 1777 */           pushFollow(FOLLOW_finallyClause_in_exceptionGroup609);
/* 1778 */           finallyClause();
/*      */ 
/* 1780 */           this.state._fsp -= 1;
/*      */         }
/*      */ 
/* 1790 */         break;
/*      */       case 2:
/* 1794 */         label207: pushFollow(FOLLOW_finallyClause_in_exceptionGroup615);
/* 1795 */         finallyClause();
/*      */ 
/* 1797 */         this.state._fsp -= 1;
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (RecognitionException re)
/*      */     {
/* 1805 */       re = 
/* 1810 */         re;
/*      */ 
/* 1806 */       reportError(re);
/* 1807 */       recover(this.input, re);
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public final void exceptionHandler()
/*      */     throws RecognitionException
/*      */   {
/*      */     try
/*      */     {
/* 1823 */       match(this.input, 84, FOLLOW_84_in_exceptionHandler636);
/*      */ 
/* 1825 */       match(this.input, 2, null);
/* 1826 */       match(this.input, 50, FOLLOW_ARG_ACTION_in_exceptionHandler638);
/* 1827 */       match(this.input, 47, FOLLOW_ACTION_in_exceptionHandler640);
/*      */ 
/* 1829 */       match(this.input, 3, null);
/*      */     }
/*      */     catch (RecognitionException re)
/*      */     {
/* 1834 */       re = 
/* 1839 */         re;
/*      */ 
/* 1835 */       reportError(re);
/* 1836 */       recover(this.input, re);
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public final void finallyClause()
/*      */     throws RecognitionException
/*      */   {
/*      */     try
/*      */     {
/* 1852 */       match(this.input, 85, FOLLOW_85_in_finallyClause662);
/*      */ 
/* 1854 */       match(this.input, 2, null);
/* 1855 */       match(this.input, 47, FOLLOW_ACTION_in_finallyClause664);
/*      */ 
/* 1857 */       match(this.input, 3, null);
/*      */     }
/*      */     catch (RecognitionException re)
/*      */     {
/* 1862 */       re = 
/* 1867 */         re;
/*      */ 
/* 1863 */       reportError(re);
/* 1864 */       recover(this.input, re);
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public final void element()
/*      */     throws RecognitionException
/*      */   {
/*      */     try
/*      */     {
/* 1878 */       int alt33 = 8;
/* 1879 */       alt33 = this.dfa33.predict(this.input);
/* 1880 */       switch (alt33)
/*      */       {
/*      */       case 1:
/* 1884 */         if ((this.input.LA(1) >= 41) && (this.input.LA(1) <= 42)) {
/* 1885 */           this.input.consume();
/* 1886 */           this.state.errorRecovery = false;
/*      */         }
/*      */         else {
/* 1889 */           MismatchedSetException mse = new MismatchedSetException(null, this.input);
/* 1890 */           throw mse;
/*      */         }
/*      */ 
/* 1894 */         match(this.input, 2, null);
/* 1895 */         match(this.input, 20, FOLLOW_ID_in_element686);
/* 1896 */         pushFollow(FOLLOW_block_in_element688);
/* 1897 */         block();
/*      */ 
/* 1899 */         this.state._fsp -= 1;
/*      */ 
/* 1902 */         match(this.input, 3, null);
/*      */ 
/* 1905 */         break;
/*      */       case 2:
/* 1909 */         if ((this.input.LA(1) >= 41) && (this.input.LA(1) <= 42)) {
/* 1910 */           this.input.consume();
/* 1911 */           this.state.errorRecovery = false;
/*      */         }
/*      */         else {
/* 1914 */           MismatchedSetException mse = new MismatchedSetException(null, this.input);
/* 1915 */           throw mse;
/*      */         }
/*      */ 
/* 1919 */         match(this.input, 2, null);
/* 1920 */         match(this.input, 20, FOLLOW_ID_in_element701);
/* 1921 */         pushFollow(FOLLOW_atom_in_element703);
/* 1922 */         atom();
/*      */ 
/* 1924 */         this.state._fsp -= 1;
/*      */ 
/* 1927 */         match(this.input, 3, null);
/*      */ 
/* 1930 */         break;
/*      */       case 3:
/* 1934 */         pushFollow(FOLLOW_atom_in_element709);
/* 1935 */         atom();
/*      */ 
/* 1937 */         this.state._fsp -= 1;
/*      */ 
/* 1941 */         break;
/*      */       case 4:
/* 1945 */         pushFollow(FOLLOW_ebnf_in_element714);
/* 1946 */         ebnf();
/*      */ 
/* 1948 */         this.state._fsp -= 1;
/*      */ 
/* 1952 */         break;
/*      */       case 5:
/* 1956 */         match(this.input, 47, FOLLOW_ACTION_in_element721);
/*      */ 
/* 1959 */         break;
/*      */       case 6:
/* 1963 */         match(this.input, 31, FOLLOW_SEMPRED_in_element728);
/*      */ 
/* 1966 */         break;
/*      */       case 7:
/* 1970 */         match(this.input, 32, FOLLOW_GATED_SEMPRED_in_element733);
/*      */ 
/* 1973 */         break;
/*      */       case 8:
/* 1977 */         match(this.input, 36, FOLLOW_TREE_BEGIN_in_element741);
/*      */ 
/* 1979 */         match(this.input, 2, null);
/*      */ 
/* 1981 */         int cnt32 = 0;
/*      */         while (true)
/*      */         {
/* 1984 */           int alt32 = 2;
/* 1985 */           switch (this.input.LA(1))
/*      */           {
/*      */           case 8:
/*      */           case 9:
/*      */           case 10:
/*      */           case 11:
/*      */           case 12:
/*      */           case 14:
/*      */           case 31:
/*      */           case 32:
/*      */           case 33:
/*      */           case 36:
/*      */           case 37:
/*      */           case 38:
/*      */           case 41:
/*      */           case 42:
/*      */           case 44:
/*      */           case 45:
/*      */           case 46:
/*      */           case 47:
/*      */           case 51:
/*      */           case 87:
/*      */           case 90:
/* 2008 */             alt32 = 1;
/*      */           case 13:
/*      */           case 15:
/*      */           case 16:
/*      */           case 17:
/*      */           case 18:
/*      */           case 19:
/*      */           case 20:
/*      */           case 21:
/*      */           case 22:
/*      */           case 23:
/*      */           case 24:
/*      */           case 25:
/*      */           case 26:
/*      */           case 27:
/*      */           case 28:
/*      */           case 29:
/*      */           case 30:
/*      */           case 34:
/*      */           case 35:
/*      */           case 39:
/*      */           case 40:
/*      */           case 43:
/*      */           case 48:
/*      */           case 49:
/*      */           case 50:
/*      */           case 52:
/*      */           case 53:
/*      */           case 54:
/*      */           case 55:
/*      */           case 56:
/*      */           case 57:
/*      */           case 58:
/*      */           case 59:
/*      */           case 60:
/*      */           case 61:
/*      */           case 62:
/*      */           case 63:
/*      */           case 64:
/*      */           case 65:
/*      */           case 66:
/*      */           case 67:
/*      */           case 68:
/*      */           case 69:
/*      */           case 70:
/*      */           case 71:
/*      */           case 72:
/*      */           case 73:
/*      */           case 74:
/*      */           case 75:
/*      */           case 76:
/*      */           case 77:
/*      */           case 78:
/*      */           case 79:
/*      */           case 80:
/*      */           case 81:
/*      */           case 82:
/*      */           case 83:
/*      */           case 84:
/*      */           case 85:
/*      */           case 86:
/*      */           case 88:
/* 2014 */           case 89: } switch (alt32)
/*      */           {
/*      */           case 1:
/* 2018 */             pushFollow(FOLLOW_element_in_element743);
/* 2019 */             element();
/*      */ 
/* 2021 */             this.state._fsp -= 1;
/*      */ 
/* 2025 */             break;
/*      */           default:
/* 2028 */             if (cnt32 >= 1) break label891;
/* 2029 */             EarlyExitException eee = new EarlyExitException(32, this.input);
/*      */ 
/* 2031 */             throw eee;
/*      */           }
/* 2033 */           cnt32++;
/*      */         }
/*      */ 
/* 2037 */         label891: match(this.input, 3, null);
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (RecognitionException re)
/*      */     {
/* 2044 */       re = 
/* 2049 */         re;
/*      */ 
/* 2045 */       reportError(re);
/* 2046 */       recover(this.input, re);
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public final void atom()
/*      */     throws RecognitionException
/*      */   {
/*      */     try
/*      */     {
/* 2060 */       int alt38 = 16;
/* 2061 */       alt38 = this.dfa38.predict(this.input);
/* 2062 */       switch (alt38)
/*      */       {
/*      */       case 1:
/* 2066 */         if ((this.input.LA(1) >= 37) && (this.input.LA(1) <= 38)) {
/* 2067 */           this.input.consume();
/* 2068 */           this.state.errorRecovery = false;
/*      */         }
/*      */         else {
/* 2071 */           MismatchedSetException mse = new MismatchedSetException(null, this.input);
/* 2072 */           throw mse;
/*      */         }
/*      */ 
/* 2076 */         match(this.input, 2, null);
/* 2077 */         pushFollow(FOLLOW_atom_in_atom763);
/* 2078 */         atom();
/*      */ 
/* 2080 */         this.state._fsp -= 1;
/*      */ 
/* 2083 */         match(this.input, 3, null);
/*      */ 
/* 2086 */         break;
/*      */       case 2:
/* 2090 */         match(this.input, 14, FOLLOW_CHAR_RANGE_in_atom770);
/*      */ 
/* 2092 */         match(this.input, 2, null);
/* 2093 */         match(this.input, 46, FOLLOW_CHAR_LITERAL_in_atom772);
/* 2094 */         match(this.input, 46, FOLLOW_CHAR_LITERAL_in_atom774);
/*      */ 
/* 2096 */         int alt34 = 2;
/* 2097 */         switch (this.input.LA(1))
/*      */         {
/*      */         case 48:
/* 2100 */           alt34 = 1;
/*      */         }
/*      */ 
/* 2105 */         switch (alt34)
/*      */         {
/*      */         case 1:
/* 2109 */           pushFollow(FOLLOW_optionsSpec_in_atom776);
/* 2110 */           optionsSpec();
/*      */ 
/* 2112 */           this.state._fsp -= 1;
/*      */         }
/*      */ 
/* 2121 */         match(this.input, 3, null);
/*      */ 
/* 2124 */         break;
/*      */       case 3:
/* 2128 */         match(this.input, 87, FOLLOW_87_in_atom784);
/*      */ 
/* 2130 */         match(this.input, 2, null);
/* 2131 */         pushFollow(FOLLOW_notTerminal_in_atom786);
/* 2132 */         notTerminal();
/*      */ 
/* 2134 */         this.state._fsp -= 1;
/*      */ 
/* 2137 */         int alt35 = 2;
/* 2138 */         switch (this.input.LA(1))
/*      */         {
/*      */         case 48:
/* 2141 */           alt35 = 1;
/*      */         }
/*      */ 
/* 2146 */         switch (alt35)
/*      */         {
/*      */         case 1:
/* 2150 */           pushFollow(FOLLOW_optionsSpec_in_atom788);
/* 2151 */           optionsSpec();
/*      */ 
/* 2153 */           this.state._fsp -= 1;
/*      */         }
/*      */ 
/* 2162 */         match(this.input, 3, null);
/*      */ 
/* 2165 */         break;
/*      */       case 4:
/* 2169 */         match(this.input, 87, FOLLOW_87_in_atom796);
/*      */ 
/* 2171 */         match(this.input, 2, null);
/* 2172 */         pushFollow(FOLLOW_block_in_atom798);
/* 2173 */         block();
/*      */ 
/* 2175 */         this.state._fsp -= 1;
/*      */ 
/* 2178 */         int alt36 = 2;
/* 2179 */         switch (this.input.LA(1))
/*      */         {
/*      */         case 48:
/* 2182 */           alt36 = 1;
/*      */         }
/*      */ 
/* 2187 */         switch (alt36)
/*      */         {
/*      */         case 1:
/* 2191 */           pushFollow(FOLLOW_optionsSpec_in_atom800);
/* 2192 */           optionsSpec();
/*      */ 
/* 2194 */           this.state._fsp -= 1;
/*      */         }
/*      */ 
/* 2203 */         match(this.input, 3, null);
/*      */ 
/* 2206 */         break;
/*      */       case 5:
/* 2210 */         match(this.input, 51, FOLLOW_RULE_REF_in_atom811);
/*      */ 
/* 2212 */         match(this.input, 2, null);
/* 2213 */         match(this.input, 50, FOLLOW_ARG_ACTION_in_atom813);
/*      */ 
/* 2215 */         match(this.input, 3, null);
/*      */ 
/* 2218 */         break;
/*      */       case 6:
/* 2222 */         match(this.input, 51, FOLLOW_RULE_REF_in_atom822);
/*      */ 
/* 2225 */         break;
/*      */       case 7:
/* 2229 */         match(this.input, 46, FOLLOW_CHAR_LITERAL_in_atom832);
/*      */ 
/* 2232 */         break;
/*      */       case 8:
/* 2236 */         match(this.input, 46, FOLLOW_CHAR_LITERAL_in_atom843);
/*      */ 
/* 2238 */         match(this.input, 2, null);
/* 2239 */         pushFollow(FOLLOW_optionsSpec_in_atom845);
/* 2240 */         optionsSpec();
/*      */ 
/* 2242 */         this.state._fsp -= 1;
/*      */ 
/* 2245 */         match(this.input, 3, null);
/*      */ 
/* 2248 */         break;
/*      */       case 9:
/* 2252 */         match(this.input, 44, FOLLOW_TOKEN_REF_in_atom854);
/*      */ 
/* 2255 */         break;
/*      */       case 10:
/* 2259 */         match(this.input, 44, FOLLOW_TOKEN_REF_in_atom863);
/*      */ 
/* 2261 */         match(this.input, 2, null);
/* 2262 */         pushFollow(FOLLOW_optionsSpec_in_atom865);
/* 2263 */         optionsSpec();
/*      */ 
/* 2265 */         this.state._fsp -= 1;
/*      */ 
/* 2268 */         match(this.input, 3, null);
/*      */ 
/* 2271 */         break;
/*      */       case 11:
/* 2275 */         match(this.input, 44, FOLLOW_TOKEN_REF_in_atom875);
/*      */ 
/* 2277 */         match(this.input, 2, null);
/* 2278 */         match(this.input, 50, FOLLOW_ARG_ACTION_in_atom877);
/* 2279 */         pushFollow(FOLLOW_optionsSpec_in_atom879);
/* 2280 */         optionsSpec();
/*      */ 
/* 2282 */         this.state._fsp -= 1;
/*      */ 
/* 2285 */         match(this.input, 3, null);
/*      */ 
/* 2288 */         break;
/*      */       case 12:
/* 2292 */         match(this.input, 44, FOLLOW_TOKEN_REF_in_atom889);
/*      */ 
/* 2294 */         match(this.input, 2, null);
/* 2295 */         match(this.input, 50, FOLLOW_ARG_ACTION_in_atom891);
/*      */ 
/* 2297 */         match(this.input, 3, null);
/*      */ 
/* 2300 */         break;
/*      */       case 13:
/* 2304 */         match(this.input, 45, FOLLOW_STRING_LITERAL_in_atom900);
/*      */ 
/* 2307 */         break;
/*      */       case 14:
/* 2311 */         match(this.input, 45, FOLLOW_STRING_LITERAL_in_atom909);
/*      */ 
/* 2313 */         match(this.input, 2, null);
/* 2314 */         pushFollow(FOLLOW_optionsSpec_in_atom911);
/* 2315 */         optionsSpec();
/*      */ 
/* 2317 */         this.state._fsp -= 1;
/*      */ 
/* 2320 */         match(this.input, 3, null);
/*      */ 
/* 2323 */         break;
/*      */       case 15:
/* 2327 */         match(this.input, 90, FOLLOW_90_in_atom920);
/*      */ 
/* 2330 */         break;
/*      */       case 16:
/* 2334 */         match(this.input, 90, FOLLOW_90_in_atom929);
/*      */ 
/* 2336 */         if (this.input.LA(1) == 2) {
/* 2337 */           match(this.input, 2, null);
/*      */ 
/* 2339 */           int alt37 = 2;
/* 2340 */           switch (this.input.LA(1))
/*      */           {
/*      */           case 48:
/* 2343 */             alt37 = 1;
/*      */           }
/*      */ 
/* 2348 */           switch (alt37)
/*      */           {
/*      */           case 1:
/* 2352 */             pushFollow(FOLLOW_optionsSpec_in_atom931);
/* 2353 */             optionsSpec();
/*      */ 
/* 2355 */             this.state._fsp -= 1;
/*      */           }
/*      */ 
/* 2364 */           match(this.input, 3, null);
/*      */         }
/*      */         break;
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (RecognitionException re)
/*      */     {
/* 2372 */       re = 
/* 2377 */         re;
/*      */ 
/* 2373 */       reportError(re);
/* 2374 */       recover(this.input, re);
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public final void ebnf()
/*      */     throws RecognitionException
/*      */   {
/*      */     try
/*      */     {
/* 2388 */       int alt39 = 6;
/* 2389 */       switch (this.input.LA(1))
/*      */       {
/*      */       case 12:
/* 2392 */         alt39 = 1;
/*      */ 
/* 2394 */         break;
/*      */       case 9:
/* 2397 */         alt39 = 2;
/*      */ 
/* 2399 */         break;
/*      */       case 10:
/* 2402 */         alt39 = 3;
/*      */ 
/* 2404 */         break;
/*      */       case 11:
/* 2407 */         alt39 = 4;
/*      */ 
/* 2409 */         break;
/*      */       case 33:
/* 2412 */         alt39 = 5;
/*      */ 
/* 2414 */         break;
/*      */       case 8:
/* 2417 */         alt39 = 6;
/*      */ 
/* 2419 */         break;
/*      */       default:
/* 2421 */         NoViableAltException nvae = new NoViableAltException("", 39, 0, this.input);
/*      */ 
/* 2424 */         throw nvae;
/*      */       }
/*      */ 
/* 2427 */       switch (alt39)
/*      */       {
/*      */       case 1:
/* 2431 */         match(this.input, 12, FOLLOW_SYNPRED_in_ebnf950);
/*      */ 
/* 2433 */         match(this.input, 2, null);
/* 2434 */         pushFollow(FOLLOW_block_in_ebnf952);
/* 2435 */         block();
/*      */ 
/* 2437 */         this.state._fsp -= 1;
/*      */ 
/* 2440 */         match(this.input, 3, null);
/*      */ 
/* 2443 */         break;
/*      */       case 2:
/* 2447 */         match(this.input, 9, FOLLOW_OPTIONAL_in_ebnf959);
/*      */ 
/* 2449 */         match(this.input, 2, null);
/* 2450 */         pushFollow(FOLLOW_block_in_ebnf961);
/* 2451 */         block();
/*      */ 
/* 2453 */         this.state._fsp -= 1;
/*      */ 
/* 2456 */         match(this.input, 3, null);
/*      */ 
/* 2459 */         break;
/*      */       case 3:
/* 2463 */         match(this.input, 10, FOLLOW_CLOSURE_in_ebnf970);
/*      */ 
/* 2465 */         match(this.input, 2, null);
/* 2466 */         pushFollow(FOLLOW_block_in_ebnf972);
/* 2467 */         block();
/*      */ 
/* 2469 */         this.state._fsp -= 1;
/*      */ 
/* 2472 */         match(this.input, 3, null);
/*      */ 
/* 2475 */         break;
/*      */       case 4:
/* 2479 */         match(this.input, 11, FOLLOW_POSITIVE_CLOSURE_in_ebnf982);
/*      */ 
/* 2481 */         match(this.input, 2, null);
/* 2482 */         pushFollow(FOLLOW_block_in_ebnf984);
/* 2483 */         block();
/*      */ 
/* 2485 */         this.state._fsp -= 1;
/*      */ 
/* 2488 */         match(this.input, 3, null);
/*      */ 
/* 2491 */         break;
/*      */       case 5:
/* 2495 */         match(this.input, 33, FOLLOW_SYN_SEMPRED_in_ebnf990);
/*      */ 
/* 2498 */         break;
/*      */       case 6:
/* 2502 */         pushFollow(FOLLOW_block_in_ebnf995);
/* 2503 */         block();
/*      */ 
/* 2505 */         this.state._fsp -= 1;
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (RecognitionException re)
/*      */     {
/* 2513 */       re = 
/* 2518 */         re;
/*      */ 
/* 2514 */       reportError(re);
/* 2515 */       recover(this.input, re);
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public final void notTerminal()
/*      */     throws RecognitionException
/*      */   {
/*      */     try
/*      */     {
/* 2531 */       if ((this.input.LA(1) >= 44) && (this.input.LA(1) <= 46)) {
/* 2532 */         this.input.consume();
/* 2533 */         this.state.errorRecovery = false;
/*      */       }
/*      */       else {
/* 2536 */         MismatchedSetException mse = new MismatchedSetException(null, this.input);
/* 2537 */         throw mse;
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (RecognitionException re)
/*      */     {
/* 2544 */       re = 
/* 2549 */         re;
/*      */ 
/* 2545 */       reportError(re);
/* 2546 */       recover(this.input, re);
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public final void rewrite()
/*      */     throws RecognitionException
/*      */   {
/*      */     try
/*      */     {
/* 2560 */       int alt41 = 2;
/* 2561 */       switch (this.input.LA(1))
/*      */       {
/*      */       case 39:
/* 2564 */         alt41 = 1;
/*      */ 
/* 2566 */         break;
/*      */       case 16:
/*      */       case 18:
/* 2570 */         alt41 = 2;
/*      */ 
/* 2572 */         break;
/*      */       default:
/* 2574 */         NoViableAltException nvae = new NoViableAltException("", 41, 0, this.input);
/*      */ 
/* 2577 */         throw nvae;
/*      */       }
/*      */ 
/* 2580 */       switch (alt41)
/*      */       {
/*      */       case 1:
/*      */         while (true)
/*      */         {
/* 2587 */           int alt40 = 2;
/* 2588 */           switch (this.input.LA(1))
/*      */           {
/*      */           case 39:
/* 2591 */             switch (this.input.LA(2))
/*      */             {
/*      */             case 2:
/* 2594 */               switch (this.input.LA(3))
/*      */               {
/*      */               case 31:
/* 2597 */                 alt40 = 1;
/*      */               }
/*      */ 
/*      */               break;
/*      */             }
/*      */ 
/*      */             break;
/*      */           }
/*      */ 
/* 2613 */           switch (alt40)
/*      */           {
/*      */           case 1:
/* 2617 */             match(this.input, 39, FOLLOW_REWRITE_in_rewrite1035);
/*      */ 
/* 2619 */             match(this.input, 2, null);
/* 2620 */             match(this.input, 31, FOLLOW_SEMPRED_in_rewrite1037);
/* 2621 */             pushFollow(FOLLOW_rewrite_alternative_in_rewrite1039);
/* 2622 */             rewrite_alternative();
/*      */ 
/* 2624 */             this.state._fsp -= 1;
/*      */ 
/* 2627 */             match(this.input, 3, null);
/*      */ 
/* 2630 */             break;
/*      */           default:
/* 2633 */             break label295;
/*      */           }
/*      */         }
/*      */ 
/* 2637 */         label295: match(this.input, 39, FOLLOW_REWRITE_in_rewrite1045);
/*      */ 
/* 2639 */         match(this.input, 2, null);
/* 2640 */         pushFollow(FOLLOW_rewrite_alternative_in_rewrite1047);
/* 2641 */         rewrite_alternative();
/*      */ 
/* 2643 */         this.state._fsp -= 1;
/*      */ 
/* 2646 */         match(this.input, 3, null);
/*      */ 
/* 2649 */         break;
/*      */       case 2:
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (RecognitionException re)
/*      */     {
/* 2658 */       re = 
/* 2663 */         re;
/*      */ 
/* 2659 */       reportError(re);
/* 2660 */       recover(this.input, re);
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public final void rewrite_alternative()
/*      */     throws RecognitionException
/*      */   {
/*      */     try
/*      */     {
/* 2674 */       int alt42 = 3;
/* 2675 */       switch (this.input.LA(1))
/*      */       {
/*      */       case 29:
/*      */       case 47:
/* 2679 */         alt42 = 1;
/*      */ 
/* 2681 */         break;
/*      */       case 16:
/* 2684 */         switch (this.input.LA(2))
/*      */         {
/*      */         case 2:
/* 2687 */           switch (this.input.LA(3))
/*      */           {
/*      */           case 15:
/* 2690 */             alt42 = 3;
/*      */ 
/* 2692 */             break;
/*      */           case 8:
/*      */           case 9:
/*      */           case 10:
/*      */           case 11:
/*      */           case 28:
/*      */           case 36:
/*      */           case 44:
/*      */           case 45:
/*      */           case 46:
/*      */           case 47:
/*      */           case 51:
/* 2705 */             alt42 = 2;
/*      */ 
/* 2707 */             break;
/*      */           case 12:
/*      */           case 13:
/*      */           case 14:
/*      */           case 16:
/*      */           case 17:
/*      */           case 18:
/*      */           case 19:
/*      */           case 20:
/*      */           case 21:
/*      */           case 22:
/*      */           case 23:
/*      */           case 24:
/*      */           case 25:
/*      */           case 26:
/*      */           case 27:
/*      */           case 29:
/*      */           case 30:
/*      */           case 31:
/*      */           case 32:
/*      */           case 33:
/*      */           case 34:
/*      */           case 35:
/*      */           case 37:
/*      */           case 38:
/*      */           case 39:
/*      */           case 40:
/*      */           case 41:
/*      */           case 42:
/*      */           case 43:
/*      */           case 48:
/*      */           case 49:
/*      */           case 50:
/*      */           default:
/* 2709 */             NoViableAltException nvae = new NoViableAltException("", 42, 3, this.input);
/*      */ 
/* 2712 */             throw nvae;
/*      */           }
/*      */ 
/* 2716 */           break;
/*      */         default:
/* 2718 */           NoViableAltException nvae = new NoViableAltException("", 42, 2, this.input);
/*      */ 
/* 2721 */           throw nvae;
/*      */         }
/*      */ 
/* 2725 */         break;
/*      */       default:
/* 2727 */         NoViableAltException nvae = new NoViableAltException("", 42, 0, this.input);
/*      */ 
/* 2730 */         throw nvae;
/*      */       }
/*      */ 
/* 2733 */       switch (alt42)
/*      */       {
/*      */       case 1:
/* 2737 */         pushFollow(FOLLOW_rewrite_template_in_rewrite_alternative1062);
/* 2738 */         rewrite_template();
/*      */ 
/* 2740 */         this.state._fsp -= 1;
/*      */ 
/* 2744 */         break;
/*      */       case 2:
/* 2748 */         pushFollow(FOLLOW_rewrite_tree_alternative_in_rewrite_alternative1067);
/* 2749 */         rewrite_tree_alternative();
/*      */ 
/* 2751 */         this.state._fsp -= 1;
/*      */ 
/* 2755 */         break;
/*      */       case 3:
/* 2759 */         match(this.input, 16, FOLLOW_ALT_in_rewrite_alternative1078);
/*      */ 
/* 2761 */         match(this.input, 2, null);
/* 2762 */         match(this.input, 15, FOLLOW_EPSILON_in_rewrite_alternative1080);
/* 2763 */         match(this.input, 19, FOLLOW_EOA_in_rewrite_alternative1082);
/*      */ 
/* 2765 */         match(this.input, 3, null);
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (RecognitionException re)
/*      */     {
/* 2772 */       re = 
/* 2777 */         re;
/*      */ 
/* 2773 */       reportError(re);
/* 2774 */       recover(this.input, re);
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public final void rewrite_tree_block()
/*      */     throws RecognitionException
/*      */   {
/*      */     try
/*      */     {
/* 2790 */       match(this.input, 8, FOLLOW_BLOCK_in_rewrite_tree_block1101);
/*      */ 
/* 2792 */       match(this.input, 2, null);
/* 2793 */       pushFollow(FOLLOW_rewrite_tree_alternative_in_rewrite_tree_block1103);
/* 2794 */       rewrite_tree_alternative();
/*      */ 
/* 2796 */       this.state._fsp -= 1;
/*      */ 
/* 2798 */       match(this.input, 18, FOLLOW_EOB_in_rewrite_tree_block1105);
/*      */ 
/* 2800 */       match(this.input, 3, null);
/*      */     }
/*      */     catch (RecognitionException re)
/*      */     {
/* 2805 */       re = 
/* 2810 */         re;
/*      */ 
/* 2806 */       reportError(re);
/* 2807 */       recover(this.input, re);
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public final void rewrite_tree_alternative()
/*      */     throws RecognitionException
/*      */   {
/*      */     try
/*      */     {
/* 2823 */       match(this.input, 16, FOLLOW_ALT_in_rewrite_tree_alternative1124);
/*      */ 
/* 2825 */       match(this.input, 2, null);
/*      */ 
/* 2827 */       int cnt43 = 0;
/*      */       while (true)
/*      */       {
/* 2830 */         int alt43 = 2;
/* 2831 */         switch (this.input.LA(1))
/*      */         {
/*      */         case 8:
/*      */         case 9:
/*      */         case 10:
/*      */         case 11:
/*      */         case 28:
/*      */         case 36:
/*      */         case 44:
/*      */         case 45:
/*      */         case 46:
/*      */         case 47:
/*      */         case 51:
/* 2844 */           alt43 = 1;
/*      */         case 12:
/*      */         case 13:
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
/*      */         case 29:
/*      */         case 30:
/*      */         case 31:
/*      */         case 32:
/*      */         case 33:
/*      */         case 34:
/*      */         case 35:
/*      */         case 37:
/*      */         case 38:
/*      */         case 39:
/*      */         case 40:
/*      */         case 41:
/*      */         case 42:
/*      */         case 43:
/*      */         case 48:
/*      */         case 49:
/* 2850 */         case 50: } switch (alt43)
/*      */         {
/*      */         case 1:
/* 2854 */           pushFollow(FOLLOW_rewrite_tree_element_in_rewrite_tree_alternative1126);
/* 2855 */           rewrite_tree_element();
/*      */ 
/* 2857 */           this.state._fsp -= 1;
/*      */ 
/* 2861 */           break;
/*      */         default:
/* 2864 */           if (cnt43 >= 1) break label305;
/* 2865 */           EarlyExitException eee = new EarlyExitException(43, this.input);
/*      */ 
/* 2867 */           throw eee;
/*      */         }
/* 2869 */         cnt43++;
/*      */       }
/*      */ 
/* 2872 */       label305: match(this.input, 19, FOLLOW_EOA_in_rewrite_tree_alternative1129);
/*      */ 
/* 2874 */       match(this.input, 3, null);
/*      */     }
/*      */     catch (RecognitionException re)
/*      */     {
/* 2879 */       re = 
/* 2884 */         re;
/*      */ 
/* 2880 */       reportError(re);
/* 2881 */       recover(this.input, re);
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public final void rewrite_tree_element()
/*      */     throws RecognitionException
/*      */   {
/*      */     try
/*      */     {
/* 2895 */       int alt44 = 4;
/* 2896 */       switch (this.input.LA(1))
/*      */       {
/*      */       case 28:
/*      */       case 44:
/*      */       case 45:
/*      */       case 46:
/*      */       case 47:
/*      */       case 51:
/* 2904 */         alt44 = 1;
/*      */ 
/* 2906 */         break;
/*      */       case 36:
/* 2909 */         alt44 = 2;
/*      */ 
/* 2911 */         break;
/*      */       case 8:
/* 2914 */         alt44 = 3;
/*      */ 
/* 2916 */         break;
/*      */       case 9:
/*      */       case 10:
/*      */       case 11:
/* 2921 */         alt44 = 4;
/*      */ 
/* 2923 */         break;
/*      */       case 12:
/*      */       case 13:
/*      */       case 14:
/*      */       case 15:
/*      */       case 16:
/*      */       case 17:
/*      */       case 18:
/*      */       case 19:
/*      */       case 20:
/*      */       case 21:
/*      */       case 22:
/*      */       case 23:
/*      */       case 24:
/*      */       case 25:
/*      */       case 26:
/*      */       case 27:
/*      */       case 29:
/*      */       case 30:
/*      */       case 31:
/*      */       case 32:
/*      */       case 33:
/*      */       case 34:
/*      */       case 35:
/*      */       case 37:
/*      */       case 38:
/*      */       case 39:
/*      */       case 40:
/*      */       case 41:
/*      */       case 42:
/*      */       case 43:
/*      */       case 48:
/*      */       case 49:
/*      */       case 50:
/*      */       default:
/* 2925 */         NoViableAltException nvae = new NoViableAltException("", 44, 0, this.input);
/*      */ 
/* 2928 */         throw nvae;
/*      */       }
/*      */ 
/* 2931 */       switch (alt44)
/*      */       {
/*      */       case 1:
/* 2935 */         pushFollow(FOLLOW_rewrite_tree_atom_in_rewrite_tree_element1144);
/* 2936 */         rewrite_tree_atom();
/*      */ 
/* 2938 */         this.state._fsp -= 1;
/*      */ 
/* 2942 */         break;
/*      */       case 2:
/* 2946 */         pushFollow(FOLLOW_rewrite_tree_in_rewrite_tree_element1149);
/* 2947 */         rewrite_tree();
/*      */ 
/* 2949 */         this.state._fsp -= 1;
/*      */ 
/* 2953 */         break;
/*      */       case 3:
/* 2957 */         pushFollow(FOLLOW_rewrite_tree_block_in_rewrite_tree_element1156);
/* 2958 */         rewrite_tree_block();
/*      */ 
/* 2960 */         this.state._fsp -= 1;
/*      */ 
/* 2964 */         break;
/*      */       case 4:
/* 2968 */         pushFollow(FOLLOW_rewrite_tree_ebnf_in_rewrite_tree_element1163);
/* 2969 */         rewrite_tree_ebnf();
/*      */ 
/* 2971 */         this.state._fsp -= 1;
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (RecognitionException re)
/*      */     {
/* 2979 */       re = 
/* 2984 */         re;
/*      */ 
/* 2980 */       reportError(re);
/* 2981 */       recover(this.input, re);
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public final void rewrite_tree_atom()
/*      */     throws RecognitionException
/*      */   {
/*      */     try
/*      */     {
/* 2995 */       int alt45 = 7;
/* 2996 */       switch (this.input.LA(1))
/*      */       {
/*      */       case 46:
/* 2999 */         alt45 = 1;
/*      */ 
/* 3001 */         break;
/*      */       case 44:
/* 3004 */         switch (this.input.LA(2))
/*      */         {
/*      */         case 2:
/* 3007 */           alt45 = 3;
/*      */ 
/* 3009 */           break;
/*      */         case 3:
/*      */         case 8:
/*      */         case 9:
/*      */         case 10:
/*      */         case 11:
/*      */         case 19:
/*      */         case 28:
/*      */         case 36:
/*      */         case 44:
/*      */         case 45:
/*      */         case 46:
/*      */         case 47:
/*      */         case 51:
/* 3024 */           alt45 = 2;
/*      */ 
/* 3026 */           break;
/*      */         case 4:
/*      */         case 5:
/*      */         case 6:
/*      */         case 7:
/*      */         case 12:
/*      */         case 13:
/*      */         case 14:
/*      */         case 15:
/*      */         case 16:
/*      */         case 17:
/*      */         case 18:
/*      */         case 20:
/*      */         case 21:
/*      */         case 22:
/*      */         case 23:
/*      */         case 24:
/*      */         case 25:
/*      */         case 26:
/*      */         case 27:
/*      */         case 29:
/*      */         case 30:
/*      */         case 31:
/*      */         case 32:
/*      */         case 33:
/*      */         case 34:
/*      */         case 35:
/*      */         case 37:
/*      */         case 38:
/*      */         case 39:
/*      */         case 40:
/*      */         case 41:
/*      */         case 42:
/*      */         case 43:
/*      */         case 48:
/*      */         case 49:
/*      */         case 50:
/*      */         default:
/* 3028 */           NoViableAltException nvae = new NoViableAltException("", 45, 2, this.input);
/*      */ 
/* 3031 */           throw nvae;
/*      */         }
/*      */ 
/* 3035 */         break;
/*      */       case 51:
/* 3038 */         alt45 = 4;
/*      */ 
/* 3040 */         break;
/*      */       case 45:
/* 3043 */         alt45 = 5;
/*      */ 
/* 3045 */         break;
/*      */       case 28:
/* 3048 */         alt45 = 6;
/*      */ 
/* 3050 */         break;
/*      */       case 47:
/* 3053 */         alt45 = 7;
/*      */ 
/* 3055 */         break;
/*      */       default:
/* 3057 */         NoViableAltException nvae = new NoViableAltException("", 45, 0, this.input);
/*      */ 
/* 3060 */         throw nvae;
/*      */       }
/*      */ 
/* 3063 */       switch (alt45)
/*      */       {
/*      */       case 1:
/* 3067 */         match(this.input, 46, FOLLOW_CHAR_LITERAL_in_rewrite_tree_atom1179);
/*      */ 
/* 3070 */         break;
/*      */       case 2:
/* 3074 */         match(this.input, 44, FOLLOW_TOKEN_REF_in_rewrite_tree_atom1186);
/*      */ 
/* 3077 */         break;
/*      */       case 3:
/* 3081 */         match(this.input, 44, FOLLOW_TOKEN_REF_in_rewrite_tree_atom1194);
/*      */ 
/* 3083 */         match(this.input, 2, null);
/* 3084 */         match(this.input, 50, FOLLOW_ARG_ACTION_in_rewrite_tree_atom1196);
/*      */ 
/* 3086 */         match(this.input, 3, null);
/*      */ 
/* 3089 */         break;
/*      */       case 4:
/* 3093 */         match(this.input, 51, FOLLOW_RULE_REF_in_rewrite_tree_atom1208);
/*      */ 
/* 3096 */         break;
/*      */       case 5:
/* 3100 */         match(this.input, 45, FOLLOW_STRING_LITERAL_in_rewrite_tree_atom1215);
/*      */ 
/* 3103 */         break;
/*      */       case 6:
/* 3107 */         match(this.input, 28, FOLLOW_LABEL_in_rewrite_tree_atom1222);
/*      */ 
/* 3110 */         break;
/*      */       case 7:
/* 3114 */         match(this.input, 47, FOLLOW_ACTION_in_rewrite_tree_atom1227);
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (RecognitionException re)
/*      */     {
/* 3121 */       re = 
/* 3126 */         re;
/*      */ 
/* 3122 */       reportError(re);
/* 3123 */       recover(this.input, re);
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public final void rewrite_tree_ebnf()
/*      */     throws RecognitionException
/*      */   {
/*      */     try
/*      */     {
/* 3137 */       int alt46 = 3;
/* 3138 */       switch (this.input.LA(1))
/*      */       {
/*      */       case 9:
/* 3141 */         alt46 = 1;
/*      */ 
/* 3143 */         break;
/*      */       case 10:
/* 3146 */         alt46 = 2;
/*      */ 
/* 3148 */         break;
/*      */       case 11:
/* 3151 */         alt46 = 3;
/*      */ 
/* 3153 */         break;
/*      */       default:
/* 3155 */         NoViableAltException nvae = new NoViableAltException("", 46, 0, this.input);
/*      */ 
/* 3158 */         throw nvae;
/*      */       }
/*      */ 
/* 3161 */       switch (alt46)
/*      */       {
/*      */       case 1:
/* 3165 */         match(this.input, 9, FOLLOW_OPTIONAL_in_rewrite_tree_ebnf1239);
/*      */ 
/* 3167 */         match(this.input, 2, null);
/* 3168 */         pushFollow(FOLLOW_rewrite_tree_block_in_rewrite_tree_ebnf1241);
/* 3169 */         rewrite_tree_block();
/*      */ 
/* 3171 */         this.state._fsp -= 1;
/*      */ 
/* 3174 */         match(this.input, 3, null);
/*      */ 
/* 3177 */         break;
/*      */       case 2:
/* 3181 */         match(this.input, 10, FOLLOW_CLOSURE_in_rewrite_tree_ebnf1250);
/*      */ 
/* 3183 */         match(this.input, 2, null);
/* 3184 */         pushFollow(FOLLOW_rewrite_tree_block_in_rewrite_tree_ebnf1252);
/* 3185 */         rewrite_tree_block();
/*      */ 
/* 3187 */         this.state._fsp -= 1;
/*      */ 
/* 3190 */         match(this.input, 3, null);
/*      */ 
/* 3193 */         break;
/*      */       case 3:
/* 3197 */         match(this.input, 11, FOLLOW_POSITIVE_CLOSURE_in_rewrite_tree_ebnf1262);
/*      */ 
/* 3199 */         match(this.input, 2, null);
/* 3200 */         pushFollow(FOLLOW_rewrite_tree_block_in_rewrite_tree_ebnf1264);
/* 3201 */         rewrite_tree_block();
/*      */ 
/* 3203 */         this.state._fsp -= 1;
/*      */ 
/* 3206 */         match(this.input, 3, null);
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (RecognitionException re)
/*      */     {
/* 3213 */       re = 
/* 3218 */         re;
/*      */ 
/* 3214 */       reportError(re);
/* 3215 */       recover(this.input, re);
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public final void rewrite_tree()
/*      */     throws RecognitionException
/*      */   {
/*      */     try
/*      */     {
/* 3231 */       match(this.input, 36, FOLLOW_TREE_BEGIN_in_rewrite_tree1278);
/*      */ 
/* 3233 */       match(this.input, 2, null);
/* 3234 */       pushFollow(FOLLOW_rewrite_tree_atom_in_rewrite_tree1280);
/* 3235 */       rewrite_tree_atom();
/*      */ 
/* 3237 */       this.state._fsp -= 1;
/*      */       while (true)
/*      */       {
/* 3242 */         int alt47 = 2;
/* 3243 */         switch (this.input.LA(1))
/*      */         {
/*      */         case 8:
/*      */         case 9:
/*      */         case 10:
/*      */         case 11:
/*      */         case 28:
/*      */         case 36:
/*      */         case 44:
/*      */         case 45:
/*      */         case 46:
/*      */         case 47:
/*      */         case 51:
/* 3256 */           alt47 = 1;
/*      */         case 12:
/*      */         case 13:
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
/*      */         case 29:
/*      */         case 30:
/*      */         case 31:
/*      */         case 32:
/*      */         case 33:
/*      */         case 34:
/*      */         case 35:
/*      */         case 37:
/*      */         case 38:
/*      */         case 39:
/*      */         case 40:
/*      */         case 41:
/*      */         case 42:
/*      */         case 43:
/*      */         case 48:
/*      */         case 49:
/* 3262 */         case 50: } switch (alt47)
/*      */         {
/*      */         case 1:
/* 3266 */           pushFollow(FOLLOW_rewrite_tree_element_in_rewrite_tree1282);
/* 3267 */           rewrite_tree_element();
/*      */ 
/* 3269 */           this.state._fsp -= 1;
/*      */ 
/* 3273 */           break;
/*      */         default:
/* 3276 */           break label305;
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/* 3281 */       label305: match(this.input, 3, null);
/*      */     }
/*      */     catch (RecognitionException re)
/*      */     {
/* 3286 */       re = 
/* 3291 */         re;
/*      */ 
/* 3287 */       reportError(re);
/* 3288 */       recover(this.input, re);
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public final void rewrite_template()
/*      */     throws RecognitionException
/*      */   {
/*      */     try
/*      */     {
/* 3302 */       int alt48 = 4;
/* 3303 */       alt48 = this.dfa48.predict(this.input);
/* 3304 */       switch (alt48)
/*      */       {
/*      */       case 1:
/* 3308 */         match(this.input, 29, FOLLOW_TEMPLATE_in_rewrite_template1300);
/*      */ 
/* 3310 */         match(this.input, 2, null);
/* 3311 */         match(this.input, 20, FOLLOW_ID_in_rewrite_template1302);
/* 3312 */         pushFollow(FOLLOW_rewrite_template_args_in_rewrite_template1304);
/* 3313 */         rewrite_template_args();
/*      */ 
/* 3315 */         this.state._fsp -= 1;
/*      */ 
/* 3317 */         if ((this.input.LA(1) >= 52) && (this.input.LA(1) <= 53)) {
/* 3318 */           this.input.consume();
/* 3319 */           this.state.errorRecovery = false;
/*      */         }
/*      */         else {
/* 3322 */           MismatchedSetException mse = new MismatchedSetException(null, this.input);
/* 3323 */           throw mse;
/*      */         }
/*      */ 
/* 3327 */         match(this.input, 3, null);
/*      */ 
/* 3330 */         break;
/*      */       case 2:
/* 3334 */         pushFollow(FOLLOW_rewrite_template_ref_in_rewrite_template1327);
/* 3335 */         rewrite_template_ref();
/*      */ 
/* 3337 */         this.state._fsp -= 1;
/*      */ 
/* 3341 */         break;
/*      */       case 3:
/* 3345 */         pushFollow(FOLLOW_rewrite_indirect_template_head_in_rewrite_template1332);
/* 3346 */         rewrite_indirect_template_head();
/*      */ 
/* 3348 */         this.state._fsp -= 1;
/*      */ 
/* 3352 */         break;
/*      */       case 4:
/* 3356 */         match(this.input, 47, FOLLOW_ACTION_in_rewrite_template1337);
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (RecognitionException re)
/*      */     {
/* 3363 */       re = 
/* 3368 */         re;
/*      */ 
/* 3364 */       reportError(re);
/* 3365 */       recover(this.input, re);
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public final void rewrite_template_ref()
/*      */     throws RecognitionException
/*      */   {
/*      */     try
/*      */     {
/* 3381 */       match(this.input, 29, FOLLOW_TEMPLATE_in_rewrite_template_ref1351);
/*      */ 
/* 3383 */       match(this.input, 2, null);
/* 3384 */       match(this.input, 20, FOLLOW_ID_in_rewrite_template_ref1353);
/* 3385 */       pushFollow(FOLLOW_rewrite_template_args_in_rewrite_template_ref1355);
/* 3386 */       rewrite_template_args();
/*      */ 
/* 3388 */       this.state._fsp -= 1;
/*      */ 
/* 3391 */       match(this.input, 3, null);
/*      */     }
/*      */     catch (RecognitionException re)
/*      */     {
/* 3396 */       re = 
/* 3401 */         re;
/*      */ 
/* 3397 */       reportError(re);
/* 3398 */       recover(this.input, re);
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public final void rewrite_indirect_template_head()
/*      */     throws RecognitionException
/*      */   {
/*      */     try
/*      */     {
/* 3414 */       match(this.input, 29, FOLLOW_TEMPLATE_in_rewrite_indirect_template_head1370);
/*      */ 
/* 3416 */       match(this.input, 2, null);
/* 3417 */       match(this.input, 47, FOLLOW_ACTION_in_rewrite_indirect_template_head1372);
/* 3418 */       pushFollow(FOLLOW_rewrite_template_args_in_rewrite_indirect_template_head1374);
/* 3419 */       rewrite_template_args();
/*      */ 
/* 3421 */       this.state._fsp -= 1;
/*      */ 
/* 3424 */       match(this.input, 3, null);
/*      */     }
/*      */     catch (RecognitionException re)
/*      */     {
/* 3429 */       re = 
/* 3434 */         re;
/*      */ 
/* 3430 */       reportError(re);
/* 3431 */       recover(this.input, re);
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public final void rewrite_template_args()
/*      */     throws RecognitionException
/*      */   {
/*      */     try
/*      */     {
/* 3445 */       int alt50 = 2;
/* 3446 */       switch (this.input.LA(1))
/*      */       {
/*      */       case 22:
/* 3449 */         switch (this.input.LA(2))
/*      */         {
/*      */         case 2:
/* 3452 */           alt50 = 1;
/*      */ 
/* 3454 */           break;
/*      */         case 3:
/*      */         case 52:
/*      */         case 53:
/* 3459 */           alt50 = 2;
/*      */ 
/* 3461 */           break;
/*      */         default:
/* 3463 */           NoViableAltException nvae = new NoViableAltException("", 50, 1, this.input);
/*      */ 
/* 3466 */           throw nvae;
/*      */         }
/*      */ 
/* 3470 */         break;
/*      */       default:
/* 3472 */         NoViableAltException nvae = new NoViableAltException("", 50, 0, this.input);
/*      */ 
/* 3475 */         throw nvae;
/*      */       }
/*      */ 
/* 3478 */       switch (alt50)
/*      */       {
/*      */       case 1:
/* 3482 */         match(this.input, 22, FOLLOW_ARGLIST_in_rewrite_template_args1387);
/*      */ 
/* 3484 */         match(this.input, 2, null);
/*      */ 
/* 3486 */         int cnt49 = 0;
/*      */         while (true)
/*      */         {
/* 3489 */           int alt49 = 2;
/* 3490 */           switch (this.input.LA(1))
/*      */           {
/*      */           case 21:
/* 3493 */             alt49 = 1;
/*      */           }
/*      */ 
/* 3499 */           switch (alt49)
/*      */           {
/*      */           case 1:
/* 3503 */             pushFollow(FOLLOW_rewrite_template_arg_in_rewrite_template_args1389);
/* 3504 */             rewrite_template_arg();
/*      */ 
/* 3506 */             this.state._fsp -= 1;
/*      */ 
/* 3510 */             break;
/*      */           default:
/* 3513 */             if (cnt49 >= 1) break label299;
/* 3514 */             EarlyExitException eee = new EarlyExitException(49, this.input);
/*      */ 
/* 3516 */             throw eee;
/*      */           }
/* 3518 */           cnt49++;
/*      */         }
/*      */ 
/* 3522 */         match(this.input, 3, null);
/*      */ 
/* 3525 */         break;
/*      */       case 2:
/* 3529 */         label299: match(this.input, 22, FOLLOW_ARGLIST_in_rewrite_template_args1396);
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (RecognitionException re)
/*      */     {
/* 3536 */       re = 
/* 3541 */         re;
/*      */ 
/* 3537 */       reportError(re);
/* 3538 */       recover(this.input, re);
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public final void rewrite_template_arg()
/*      */     throws RecognitionException
/*      */   {
/*      */     try
/*      */     {
/* 3554 */       match(this.input, 21, FOLLOW_ARG_in_rewrite_template_arg1410);
/*      */ 
/* 3556 */       match(this.input, 2, null);
/* 3557 */       match(this.input, 20, FOLLOW_ID_in_rewrite_template_arg1412);
/* 3558 */       match(this.input, 47, FOLLOW_ACTION_in_rewrite_template_arg1414);
/*      */ 
/* 3560 */       match(this.input, 3, null);
/*      */     }
/*      */     catch (RecognitionException re)
/*      */     {
/* 3565 */       re = 
/* 3570 */         re;
/*      */ 
/* 3566 */       reportError(re);
/* 3567 */       recover(this.input, re);
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public final void qid()
/*      */     throws RecognitionException
/*      */   {
/*      */     try
/*      */     {
/* 3583 */       match(this.input, 20, FOLLOW_ID_in_qid1425);
/*      */       while (true)
/*      */       {
/* 3587 */         int alt51 = 2;
/* 3588 */         switch (this.input.LA(1))
/*      */         {
/*      */         case 90:
/* 3591 */           alt51 = 1;
/*      */         }
/*      */ 
/* 3597 */         switch (alt51)
/*      */         {
/*      */         case 1:
/* 3601 */           match(this.input, 90, FOLLOW_90_in_qid1428);
/* 3602 */           match(this.input, 20, FOLLOW_ID_in_qid1430);
/*      */ 
/* 3605 */           break;
/*      */         default:
/* 3608 */           return;
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (RecognitionException re)
/*      */     {
/* 3616 */       re = 
/* 3621 */         re;
/*      */ 
/* 3617 */       reportError(re);
/* 3618 */       recover(this.input, re);
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   static
/*      */   {
/* 3670 */     int numStates = DFA33_transitionS.length;
/* 3671 */     DFA33_transition = new short[numStates][];
/* 3672 */     for (int i = 0; i < numStates; i++) {
/* 3673 */       DFA33_transition[i] = DFA.unpackEncodedString(DFA33_transitionS[i]);
/*      */     }
/*      */ 
/* 3707 */     DFA38_transitionS = new String[] { "\001\002\026ð¿¿\002\001\005ð¿¿\001\006\001\007\001\005\004ð¿¿\001\004#ð¿¿\001\003\002ð¿¿\001\b", "", "", "\001\t", "\001\n\001\013\004ð¿¿\005\013\001ð¿¿\001\013\004ð¿¿\001\013\013ð¿¿\003\013\002ð¿¿\003\013\002ð¿¿\002\013\001ð¿¿\004\013\003ð¿¿\001\013#ð¿¿\001\013\002ð¿¿\001\013", "\001\f\001\r\004ð¿¿\005\r\001ð¿¿\001\r\004ð¿¿\001\r\013ð¿¿\003\r\002ð¿¿\003\r\002ð¿¿\002\r\001ð¿¿\004\r\003ð¿¿\001\r#ð¿¿\001\r\002ð¿¿\001\r", "\001\016\001\017\004ð¿¿\005\017\001ð¿¿\001\017\004ð¿¿\001\017\013ð¿¿\003\017\002ð¿¿\003\017\002ð¿¿\002\017\001ð¿¿\004\017\003ð¿¿\001\017#ð¿¿\001\017\002ð¿¿\001\017", "\001\020\001\021\004ð¿¿\005\021\001ð¿¿\001\021\004ð¿¿\001\021\013ð¿¿\003\021\002ð¿¿\003\021\002ð¿¿\002\021\001ð¿¿\004\021\003ð¿¿\001\021#ð¿¿\001\021\002ð¿¿\001\021", "\001\022\001\023\004ð¿¿\005\023\001ð¿¿\001\023\004ð¿¿\001\023\013ð¿¿\003\023\002ð¿¿\003\023\002ð¿¿\002\023\001ð¿¿\004\023\003ð¿¿\001\023#ð¿¿\001\023\002ð¿¿\001\023", "\001\024#ð¿¿\003\025", "", "", "", "", "\001\027\001ð¿¿\001\026", "", "", "", "", "", "", "", "\001\030,ð¿¿\001\031", "", "", "" };
/*      */ 
/* 3747 */     DFA38_eot = DFA.unpackEncodedString("\032ð¿¿");
/* 3748 */     DFA38_eof = DFA.unpackEncodedString("\032ð¿¿");
/* 3749 */     DFA38_min = DFA.unpackEncodedStringToUnsignedChars("\001\016\002ð¿¿\006\002\001\b\004ð¿¿\0010\007ð¿¿\001\003\003ð¿¿");
/* 3750 */     DFA38_max = DFA.unpackEncodedStringToUnsignedChars("\001Z\002ð¿¿\001\002\005Z\001.\004ð¿¿\0012\007ð¿¿\0010\003ð¿¿");
/* 3751 */     DFA38_accept = DFA.unpackEncodedString("\001ð¿¿\001\001\001\002\007ð¿¿\001\005\001\006\001\b\001\007\001ð¿¿\001\t\001\016\001\r\001\020\001\017\001\004\001\003\001ð¿¿\001\n\001\f\001\013");
/* 3752 */     DFA38_special = DFA.unpackEncodedString("\032ð¿¿}>");
/*      */ 
/* 3756 */     int numStates = DFA38_transitionS.length;
/* 3757 */     DFA38_transition = new short[numStates][];
/* 3758 */     for (int i = 0; i < numStates; i++) {
/* 3759 */       DFA38_transition[i] = DFA.unpackEncodedString(DFA38_transitionS[i]);
/*      */     }
/*      */ 
/* 3794 */     DFA48_transitionS = new String[] { "\001\001\021ð¿¿\001\002", "\001\003", "", "\001\004\032ð¿¿\001\005", "\001\006", "", "\001\007\001\b0ð¿¿\002\t", "\001\n", "", "", "\001\013", "\001\f", "\001\r", "\001\016", "\001\017\021ð¿¿\001\n", "\001\b0ð¿¿\002\t" };
/*      */ 
/* 3813 */     DFA48_eot = DFA.unpackEncodedString("\020ð¿¿");
/* 3814 */     DFA48_eof = DFA.unpackEncodedString("\020ð¿¿");
/* 3815 */     DFA48_min = DFA.unpackEncodedStringToUnsignedChars("\001\035\001\002\001ð¿¿\001\024\001\026\001ð¿¿\001\002\001\025\002ð¿¿\001\002\001\024\001/\003\003");
/* 3816 */     DFA48_max = DFA.unpackEncodedStringToUnsignedChars("\001/\001\002\001ð¿¿\001/\001\026\001ð¿¿\0015\001\025\002ð¿¿\001\002\001\024\001/\001\003\001\025\0015");
/* 3817 */     DFA48_accept = DFA.unpackEncodedString("\002ð¿¿\001\004\002ð¿¿\001\003\002ð¿¿\001\002\001\001\006ð¿¿");
/* 3818 */     DFA48_special = DFA.unpackEncodedString("\020ð¿¿}>");
/*      */ 
/* 3822 */     int numStates = DFA48_transitionS.length;
/* 3823 */     DFA48_transition = new short[numStates][];
/* 3824 */     for (int i = 0; i < numStates; i++)
/* 3825 */       DFA48_transition[i] = DFA.unpackEncodedString(DFA48_transitionS[i]);
/*      */   }
/*      */ 
/*      */   class DFA48 extends DFA
/*      */   {
/*      */     public DFA48(BaseRecognizer recognizer)
/*      */     {
/* 3832 */       this.recognizer = recognizer;
/* 3833 */       this.decisionNumber = 48;
/* 3834 */       this.eot = ANTLRv3Tree.DFA48_eot;
/* 3835 */       this.eof = ANTLRv3Tree.DFA48_eof;
/* 3836 */       this.min = ANTLRv3Tree.DFA48_min;
/* 3837 */       this.max = ANTLRv3Tree.DFA48_max;
/* 3838 */       this.accept = ANTLRv3Tree.DFA48_accept;
/* 3839 */       this.special = ANTLRv3Tree.DFA48_special;
/* 3840 */       this.transition = ANTLRv3Tree.DFA48_transition;
/*      */     }
/*      */     public String getDescription() {
/* 3843 */       return "233:1: rewrite_template : ( ^( TEMPLATE ID rewrite_template_args ( DOUBLE_QUOTE_STRING_LITERAL | DOUBLE_ANGLE_STRING_LITERAL ) ) | rewrite_template_ref | rewrite_indirect_template_head | ACTION );";
/*      */     }
/*      */   }
/*      */ 
/*      */   class DFA38 extends DFA
/*      */   {
/*      */     public DFA38(BaseRecognizer recognizer)
/*      */     {
/* 3766 */       this.recognizer = recognizer;
/* 3767 */       this.decisionNumber = 38;
/* 3768 */       this.eot = ANTLRv3Tree.DFA38_eot;
/* 3769 */       this.eof = ANTLRv3Tree.DFA38_eof;
/* 3770 */       this.min = ANTLRv3Tree.DFA38_min;
/* 3771 */       this.max = ANTLRv3Tree.DFA38_max;
/* 3772 */       this.accept = ANTLRv3Tree.DFA38_accept;
/* 3773 */       this.special = ANTLRv3Tree.DFA38_special;
/* 3774 */       this.transition = ANTLRv3Tree.DFA38_transition;
/*      */     }
/*      */     public String getDescription() {
/* 3777 */       return "151:1: atom : ( ^( ( '^' | '!' ) atom ) | ^( CHAR_RANGE CHAR_LITERAL CHAR_LITERAL ( optionsSpec )? ) | ^( '~' notTerminal ( optionsSpec )? ) | ^( '~' block ( optionsSpec )? ) | ^( RULE_REF ARG_ACTION ) | RULE_REF | CHAR_LITERAL | ^( CHAR_LITERAL optionsSpec ) | TOKEN_REF | ^( TOKEN_REF optionsSpec ) | ^( TOKEN_REF ARG_ACTION optionsSpec ) | ^( TOKEN_REF ARG_ACTION ) | STRING_LITERAL | ^( STRING_LITERAL optionsSpec ) | '.' | ^( '.' ( optionsSpec )? ) );";
/*      */     }
/*      */   }
/*      */ 
/*      */   class DFA33 extends DFA
/*      */   {
/*      */     public DFA33(BaseRecognizer recognizer)
/*      */     {
/* 3680 */       this.recognizer = recognizer;
/* 3681 */       this.decisionNumber = 33;
/* 3682 */       this.eot = ANTLRv3Tree.DFA33_eot;
/* 3683 */       this.eof = ANTLRv3Tree.DFA33_eof;
/* 3684 */       this.min = ANTLRv3Tree.DFA33_min;
/* 3685 */       this.max = ANTLRv3Tree.DFA33_max;
/* 3686 */       this.accept = ANTLRv3Tree.DFA33_accept;
/* 3687 */       this.special = ANTLRv3Tree.DFA33_special;
/* 3688 */       this.transition = ANTLRv3Tree.DFA33_transition;
/*      */     }
/*      */     public String getDescription() {
/* 3691 */       return "140:1: element : ( ^( ( '=' | '+=' ) ID block ) | ^( ( '=' | '+=' ) ID atom ) | atom | ebnf | ACTION | SEMPRED | GATED_SEMPRED | ^( TREE_BEGIN ( element )+ ) );";
/*      */     }
/*      */   }
/*      */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.antlr.grammar.v3.ANTLRv3Tree
 * JD-Core Version:    0.6.2
 */