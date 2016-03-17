/*      */ package org.antlr.gunit.swingui.parsers;
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
/*   19 */   public static final String[] tokenNames = { "<invalid>", "<EOR>", "<DOWN>", "<UP>", "DOC_COMMENT", "PARSER", "LEXER", "RULE", "BLOCK", "OPTIONAL", "CLOSURE", "POSITIVE_CLOSURE", "SYNPRED", "RANGE", "CHAR_RANGE", "EPSILON", "ALT", "EOR", "EOB", "EOA", "ID", "ARG", "ARGLIST", "RET", "LEXER_GRAMMAR", "PARSER_GRAMMAR", "TREE_GRAMMAR", "COMBINED_GRAMMAR", "INITACTION", "LABEL", "TEMPLATE", "SCOPE", "SEMPRED", "GATED_SEMPRED", "SYN_SEMPRED", "BACKTRACK_SEMPRED", "FRAGMENT", "TREE_BEGIN", "ROOT", "BANG", "REWRITE", "TOKENS", "TOKEN_REF", "STRING_LITERAL", "CHAR_LITERAL", "ACTION", "OPTIONS", "INT", "ARG_ACTION", "RULE_REF", "DOUBLE_QUOTE_STRING_LITERAL", "DOUBLE_ANGLE_STRING_LITERAL", "SRC", "SL_COMMENT", "ML_COMMENT", "LITERAL_CHAR", "ESC", "XDIGIT", "NESTED_ARG_ACTION", "ACTION_STRING_LITERAL", "ACTION_CHAR_LITERAL", "NESTED_ACTION", "ACTION_ESC", "WS_LOOP", "WS", "'lexer'", "'parser'", "'tree'", "'grammar'", "';'", "'}'", "'='", "'@'", "'::'", "'*'", "'protected'", "'public'", "'private'", "'returns'", "':'", "'throws'", "','", "'('", "'|'", "')'", "'catch'", "'finally'", "'+='", "'=>'", "'~'", "'?'", "'+'", "'.'", "'$'" };
/*      */   public static final int EOF = -1;
/*      */   public static final int T__65 = 65;
/*      */   public static final int T__66 = 66;
/*      */   public static final int T__67 = 67;
/*      */   public static final int T__68 = 68;
/*      */   public static final int T__69 = 69;
/*      */   public static final int T__70 = 70;
/*      */   public static final int T__71 = 71;
/*      */   public static final int T__72 = 72;
/*      */   public static final int T__73 = 73;
/*      */   public static final int T__74 = 74;
/*      */   public static final int T__75 = 75;
/*      */   public static final int T__76 = 76;
/*      */   public static final int T__77 = 77;
/*      */   public static final int T__78 = 78;
/*      */   public static final int T__79 = 79;
/*      */   public static final int T__80 = 80;
/*      */   public static final int T__81 = 81;
/*      */   public static final int T__82 = 82;
/*      */   public static final int T__83 = 83;
/*      */   public static final int T__84 = 84;
/*      */   public static final int T__85 = 85;
/*      */   public static final int T__86 = 86;
/*      */   public static final int T__87 = 87;
/*      */   public static final int T__88 = 88;
/*      */   public static final int T__89 = 89;
/*      */   public static final int T__90 = 90;
/*      */   public static final int T__91 = 91;
/*      */   public static final int T__92 = 92;
/*      */   public static final int T__93 = 93;
/*      */   public static final int DOC_COMMENT = 4;
/*      */   public static final int PARSER = 5;
/*      */   public static final int LEXER = 6;
/*      */   public static final int RULE = 7;
/*      */   public static final int BLOCK = 8;
/*      */   public static final int OPTIONAL = 9;
/*      */   public static final int CLOSURE = 10;
/*      */   public static final int POSITIVE_CLOSURE = 11;
/*      */   public static final int SYNPRED = 12;
/*      */   public static final int RANGE = 13;
/*      */   public static final int CHAR_RANGE = 14;
/*      */   public static final int EPSILON = 15;
/*      */   public static final int ALT = 16;
/*      */   public static final int EOR = 17;
/*      */   public static final int EOB = 18;
/*      */   public static final int EOA = 19;
/*      */   public static final int ID = 20;
/*      */   public static final int ARG = 21;
/*      */   public static final int ARGLIST = 22;
/*      */   public static final int RET = 23;
/*      */   public static final int LEXER_GRAMMAR = 24;
/*      */   public static final int PARSER_GRAMMAR = 25;
/*      */   public static final int TREE_GRAMMAR = 26;
/*      */   public static final int COMBINED_GRAMMAR = 27;
/*      */   public static final int INITACTION = 28;
/*      */   public static final int LABEL = 29;
/*      */   public static final int TEMPLATE = 30;
/*      */   public static final int SCOPE = 31;
/*      */   public static final int SEMPRED = 32;
/*      */   public static final int GATED_SEMPRED = 33;
/*      */   public static final int SYN_SEMPRED = 34;
/*      */   public static final int BACKTRACK_SEMPRED = 35;
/*      */   public static final int FRAGMENT = 36;
/*      */   public static final int TREE_BEGIN = 37;
/*      */   public static final int ROOT = 38;
/*      */   public static final int BANG = 39;
/*      */   public static final int REWRITE = 40;
/*      */   public static final int TOKENS = 41;
/*      */   public static final int TOKEN_REF = 42;
/*      */   public static final int STRING_LITERAL = 43;
/*      */   public static final int CHAR_LITERAL = 44;
/*      */   public static final int ACTION = 45;
/*      */   public static final int OPTIONS = 46;
/*      */   public static final int INT = 47;
/*      */   public static final int ARG_ACTION = 48;
/*      */   public static final int RULE_REF = 49;
/*      */   public static final int DOUBLE_QUOTE_STRING_LITERAL = 50;
/*      */   public static final int DOUBLE_ANGLE_STRING_LITERAL = 51;
/*      */   public static final int SRC = 52;
/*      */   public static final int SL_COMMENT = 53;
/*      */   public static final int ML_COMMENT = 54;
/*      */   public static final int LITERAL_CHAR = 55;
/*      */   public static final int ESC = 56;
/*      */   public static final int XDIGIT = 57;
/*      */   public static final int NESTED_ARG_ACTION = 58;
/*      */   public static final int ACTION_STRING_LITERAL = 59;
/*      */   public static final int ACTION_CHAR_LITERAL = 60;
/*      */   public static final int NESTED_ACTION = 61;
/*      */   public static final int ACTION_ESC = 62;
/*      */   public static final int WS_LOOP = 63;
/*      */   public static final int WS = 64;
/*  126 */   protected TreeAdaptor adaptor = new CommonTreeAdaptor();
/*      */   int gtype;
/*      */   public List<String> rules;
/* 1685 */   protected Stack rule_stack = new Stack();
/*      */ 
/* 8708 */   protected DFA46 dfa46 = new DFA46(this);
/* 8709 */   protected DFA64 dfa64 = new DFA64(this);
/* 8710 */   protected DFA67 dfa67 = new DFA67(this);
/* 8711 */   protected DFA72 dfa72 = new DFA72(this);
/*      */   static final String DFA46_eotS = "\fð¿¿";
/*      */   static final String DFA46_eofS = "\fð¿¿";
/*      */   static final String DFA46_minS = "\003 \005ð¿¿\002*\002ð¿¿";
/*      */   static final String DFA46_maxS = "\003\\\005ð¿¿\002\\\002ð¿¿";
/*      */   static final String DFA46_acceptS = "\003ð¿¿\001\003\001\004\001\005\001\006\001\007\002ð¿¿\001\001\001\002";
/*      */   static final String DFA46_specialS = "\fð¿¿}>";
/* 8724 */   static final String[] DFA46_transitionS = { "\001\006\004ð¿¿\001\007\004ð¿¿\001\001\002\003\001\005\003ð¿¿\001\002 ð¿¿\001\004\006ð¿¿\001\003\002ð¿¿\001\003", "\001\003\004ð¿¿\004\003\001ð¿¿\004\003\002ð¿¿\002\003\023ð¿¿\001\003\001ð¿¿\001\b\002ð¿¿\001\003\007ð¿¿\003\003\002ð¿¿\001\t\001ð¿¿\004\003", "\001\003\004ð¿¿\004\003\001ð¿¿\004\003\002ð¿¿\002\003\023ð¿¿\001\003\001ð¿¿\001\b\002ð¿¿\001\003\007ð¿¿\003\003\002ð¿¿\001\t\001ð¿¿\004\003", "", "", "", "", "", "\003\n\004ð¿¿\001\n ð¿¿\001\013\006ð¿¿\001\n\002ð¿¿\001\n", "\003\n\004ð¿¿\001\n ð¿¿\001\013\006ð¿¿\001\n\002ð¿¿\001\n", "", "" };
/*      */ 
/* 8742 */   static final short[] DFA46_eot = DFA.unpackEncodedString("\fð¿¿");
/* 8743 */   static final short[] DFA46_eof = DFA.unpackEncodedString("\fð¿¿");
/* 8744 */   static final char[] DFA46_min = DFA.unpackEncodedStringToUnsignedChars("\003 \005ð¿¿\002*\002ð¿¿");
/* 8745 */   static final char[] DFA46_max = DFA.unpackEncodedStringToUnsignedChars("\003\\\005ð¿¿\002\\\002ð¿¿");
/* 8746 */   static final short[] DFA46_accept = DFA.unpackEncodedString("\003ð¿¿\001\003\001\004\001\005\001\006\001\007\002ð¿¿\001\001\001\002");
/* 8747 */   static final short[] DFA46_special = DFA.unpackEncodedString("\fð¿¿}>");
/*      */   static final short[][] DFA46_transition;
/*      */   static final String DFA64_eotS = "\rð¿¿";
/*      */   static final String DFA64_eofS = "\rð¿¿";
/*      */   static final String DFA64_minS = "";
/*      */   static final String DFA64_maxS = "";
/*      */   static final String DFA64_acceptS = "\005ð¿¿\001\002\001\003\002ð¿¿\001\001\003ð¿¿";
/*      */   static final String DFA64_specialS = "";
/*      */   static final String[] DFA64_transitionS;
/*      */   static final short[] DFA64_eot;
/*      */   static final short[] DFA64_eof;
/*      */   static final char[] DFA64_min;
/*      */   static final char[] DFA64_max;
/*      */   static final short[] DFA64_accept;
/*      */   static final short[] DFA64_special;
/*      */   static final short[][] DFA64_transition;
/*      */   static final String DFA67_eotS = "\016ð¿¿";
/*      */   static final String DFA67_eofS = "\001ð¿¿\004\t\001ð¿¿\001\t\004ð¿¿\003\t";
/*      */   static final String DFA67_minS = "\005%\001*\001%\004ð¿¿\003%";
/*      */   static final String DFA67_maxS = "\005]\0011\001]\004ð¿¿\003]";
/*      */   static final String DFA67_acceptS = "\007ð¿¿\001\003\001\004\001\001\001\002\003ð¿¿";
/*      */   static final String DFA67_specialS = "\016ð¿¿}>";
/*      */   static final String[] DFA67_transitionS;
/*      */   static final short[] DFA67_eot;
/*      */   static final short[] DFA67_eof;
/*      */   static final char[] DFA67_min;
/*      */   static final char[] DFA67_max;
/*      */   static final short[] DFA67_accept;
/*      */   static final short[] DFA67_special;
/*      */   static final short[][] DFA67_transition;
/*      */   static final String DFA72_eotS = "\022ð¿¿";
/*      */   static final String DFA72_eofS = "\bð¿¿\001\013\tð¿¿";
/*      */   static final String DFA72_minS = "\001*\002R\002ð¿¿\001*\002G\001(\001-\002ð¿¿\001Q\001*\002G\001-\001Q";
/*      */   static final String DFA72_maxS = "\003R\002ð¿¿\001T\002G\001T\001-\002ð¿¿\001T\0011\002G\001-\001T";
/*      */   static final String DFA72_acceptS = "\003ð¿¿\001\003\001\004\005ð¿¿\001\001\001\002\006ð¿¿";
/*      */   static final String DFA72_specialS = "\022ð¿¿}>";
/*      */   static final String[] DFA72_transitionS;
/*      */   static final short[] DFA72_eot;
/*      */   static final short[] DFA72_eof;
/*      */   static final char[] DFA72_min;
/*      */   static final char[] DFA72_max;
/*      */   static final short[] DFA72_accept;
/*      */   static final short[] DFA72_special;
/*      */   static final short[][] DFA72_transition;
/* 9010 */   public static final BitSet FOLLOW_DOC_COMMENT_in_grammarDef347 = new BitSet(new long[] { 0L, 30L });
/* 9011 */   public static final BitSet FOLLOW_65_in_grammarDef357 = new BitSet(new long[] { 0L, 16L });
/* 9012 */   public static final BitSet FOLLOW_66_in_grammarDef375 = new BitSet(new long[] { 0L, 16L });
/* 9013 */   public static final BitSet FOLLOW_67_in_grammarDef391 = new BitSet(new long[] { 0L, 16L });
/* 9014 */   public static final BitSet FOLLOW_68_in_grammarDef432 = new BitSet(new long[] { 567347999932416L });
/* 9015 */   public static final BitSet FOLLOW_id_in_grammarDef434 = new BitSet(new long[] { 0L, 32L });
/* 9016 */   public static final BitSet FOLLOW_69_in_grammarDef436 = new BitSet(new long[] { 639986634326032L, 14592L });
/* 9017 */   public static final BitSet FOLLOW_optionsSpec_in_grammarDef438 = new BitSet(new long[] { 639986634326032L, 14592L });
/* 9018 */   public static final BitSet FOLLOW_tokensSpec_in_grammarDef441 = new BitSet(new long[] { 639986634326032L, 14592L });
/* 9019 */   public static final BitSet FOLLOW_attrScope_in_grammarDef444 = new BitSet(new long[] { 639986634326032L, 14592L });
/* 9020 */   public static final BitSet FOLLOW_action_in_grammarDef447 = new BitSet(new long[] { 639986634326032L, 14592L });
/* 9021 */   public static final BitSet FOLLOW_rule_in_grammarDef455 = new BitSet(new long[] { 639986634326032L, 14592L });
/* 9022 */   public static final BitSet FOLLOW_EOF_in_grammarDef463 = new BitSet(new long[] { 2L });
/* 9023 */   public static final BitSet FOLLOW_TOKENS_in_tokensSpec524 = new BitSet(new long[] { 4398046511104L });
/* 9024 */   public static final BitSet FOLLOW_tokenSpec_in_tokensSpec526 = new BitSet(new long[] { 4398046511104L, 64L });
/* 9025 */   public static final BitSet FOLLOW_70_in_tokensSpec529 = new BitSet(new long[] { 2L });
/* 9026 */   public static final BitSet FOLLOW_TOKEN_REF_in_tokenSpec549 = new BitSet(new long[] { 0L, 160L });
/* 9027 */   public static final BitSet FOLLOW_71_in_tokenSpec555 = new BitSet(new long[] { 26388279066624L });
/* 9028 */   public static final BitSet FOLLOW_STRING_LITERAL_in_tokenSpec560 = new BitSet(new long[] { 0L, 32L });
/* 9029 */   public static final BitSet FOLLOW_CHAR_LITERAL_in_tokenSpec564 = new BitSet(new long[] { 0L, 32L });
/* 9030 */   public static final BitSet FOLLOW_69_in_tokenSpec603 = new BitSet(new long[] { 2L });
/* 9031 */   public static final BitSet FOLLOW_SCOPE_in_attrScope614 = new BitSet(new long[] { 567347999932416L });
/* 9032 */   public static final BitSet FOLLOW_id_in_attrScope616 = new BitSet(new long[] { 35184372088832L });
/* 9033 */   public static final BitSet FOLLOW_ACTION_in_attrScope618 = new BitSet(new long[] { 2L });
/* 9034 */   public static final BitSet FOLLOW_72_in_action641 = new BitSet(new long[] { 567347999932416L, 6L });
/* 9035 */   public static final BitSet FOLLOW_actionScopeName_in_action644 = new BitSet(new long[] { 0L, 512L });
/* 9036 */   public static final BitSet FOLLOW_73_in_action646 = new BitSet(new long[] { 567347999932416L });
/* 9037 */   public static final BitSet FOLLOW_id_in_action650 = new BitSet(new long[] { 35184372088832L });
/* 9038 */   public static final BitSet FOLLOW_ACTION_in_action652 = new BitSet(new long[] { 2L });
/* 9039 */   public static final BitSet FOLLOW_id_in_actionScopeName678 = new BitSet(new long[] { 2L });
/* 9040 */   public static final BitSet FOLLOW_65_in_actionScopeName685 = new BitSet(new long[] { 2L });
/* 9041 */   public static final BitSet FOLLOW_66_in_actionScopeName702 = new BitSet(new long[] { 2L });
/* 9042 */   public static final BitSet FOLLOW_OPTIONS_in_optionsSpec718 = new BitSet(new long[] { 567347999932416L });
/* 9043 */   public static final BitSet FOLLOW_option_in_optionsSpec721 = new BitSet(new long[] { 0L, 32L });
/* 9044 */   public static final BitSet FOLLOW_69_in_optionsSpec723 = new BitSet(new long[] { 567347999932416L, 64L });
/* 9045 */   public static final BitSet FOLLOW_70_in_optionsSpec727 = new BitSet(new long[] { 2L });
/* 9046 */   public static final BitSet FOLLOW_id_in_option752 = new BitSet(new long[] { 0L, 128L });
/* 9047 */   public static final BitSet FOLLOW_71_in_option754 = new BitSet(new long[] { 734473767354368L, 1024L });
/* 9048 */   public static final BitSet FOLLOW_optionValue_in_option756 = new BitSet(new long[] { 2L });
/* 9049 */   public static final BitSet FOLLOW_id_in_optionValue785 = new BitSet(new long[] { 2L });
/* 9050 */   public static final BitSet FOLLOW_STRING_LITERAL_in_optionValue795 = new BitSet(new long[] { 2L });
/* 9051 */   public static final BitSet FOLLOW_CHAR_LITERAL_in_optionValue805 = new BitSet(new long[] { 2L });
/* 9052 */   public static final BitSet FOLLOW_INT_in_optionValue815 = new BitSet(new long[] { 2L });
/* 9053 */   public static final BitSet FOLLOW_74_in_optionValue825 = new BitSet(new long[] { 2L });
/* 9054 */   public static final BitSet FOLLOW_DOC_COMMENT_in_rule854 = new BitSet(new long[] { 567416719409152L, 14336L });
/* 9055 */   public static final BitSet FOLLOW_75_in_rule864 = new BitSet(new long[] { 567347999932416L });
/* 9056 */   public static final BitSet FOLLOW_76_in_rule866 = new BitSet(new long[] { 567347999932416L });
/* 9057 */   public static final BitSet FOLLOW_77_in_rule868 = new BitSet(new long[] { 567347999932416L });
/* 9058 */   public static final BitSet FOLLOW_FRAGMENT_in_rule870 = new BitSet(new long[] { 567347999932416L });
/* 9059 */   public static final BitSet FOLLOW_id_in_rule878 = new BitSet(new long[] { 352395624185856L, 114944L });
/* 9060 */   public static final BitSet FOLLOW_BANG_in_rule884 = new BitSet(new long[] { 351845868371968L, 114944L });
/* 9061 */   public static final BitSet FOLLOW_ARG_ACTION_in_rule893 = new BitSet(new long[] { 70370891661312L, 114944L });
/* 9062 */   public static final BitSet FOLLOW_78_in_rule902 = new BitSet(new long[] { 281474976710656L });
/* 9063 */   public static final BitSet FOLLOW_ARG_ACTION_in_rule906 = new BitSet(new long[] { 70370891661312L, 98560L });
/* 9064 */   public static final BitSet FOLLOW_throwsSpec_in_rule914 = new BitSet(new long[] { 70370891661312L, 33024L });
/* 9065 */   public static final BitSet FOLLOW_optionsSpec_in_rule917 = new BitSet(new long[] { 2147483648L, 33024L });
/* 9066 */   public static final BitSet FOLLOW_ruleScopeSpec_in_rule920 = new BitSet(new long[] { 0L, 33024L });
/* 9067 */   public static final BitSet FOLLOW_ruleAction_in_rule923 = new BitSet(new long[] { 0L, 33024L });
/* 9068 */   public static final BitSet FOLLOW_79_in_rule928 = new BitSet(new long[] { 630161896636416L, 302776320L });
/* 9069 */   public static final BitSet FOLLOW_altList_in_rule930 = new BitSet(new long[] { 0L, 32L });
/* 9070 */   public static final BitSet FOLLOW_69_in_rule932 = new BitSet(new long[] { 2L, 6291456L });
/* 9071 */   public static final BitSet FOLLOW_exceptionGroup_in_rule936 = new BitSet(new long[] { 2L });
/* 9072 */   public static final BitSet FOLLOW_72_in_ruleAction1038 = new BitSet(new long[] { 567347999932416L });
/* 9073 */   public static final BitSet FOLLOW_id_in_ruleAction1040 = new BitSet(new long[] { 35184372088832L });
/* 9074 */   public static final BitSet FOLLOW_ACTION_in_ruleAction1042 = new BitSet(new long[] { 2L });
/* 9075 */   public static final BitSet FOLLOW_80_in_throwsSpec1063 = new BitSet(new long[] { 567347999932416L });
/* 9076 */   public static final BitSet FOLLOW_id_in_throwsSpec1065 = new BitSet(new long[] { 2L, 131072L });
/* 9077 */   public static final BitSet FOLLOW_81_in_throwsSpec1069 = new BitSet(new long[] { 567347999932416L });
/* 9078 */   public static final BitSet FOLLOW_id_in_throwsSpec1071 = new BitSet(new long[] { 2L, 131072L });
/* 9079 */   public static final BitSet FOLLOW_SCOPE_in_ruleScopeSpec1094 = new BitSet(new long[] { 35184372088832L });
/* 9080 */   public static final BitSet FOLLOW_ACTION_in_ruleScopeSpec1096 = new BitSet(new long[] { 2L });
/* 9081 */   public static final BitSet FOLLOW_SCOPE_in_ruleScopeSpec1109 = new BitSet(new long[] { 567347999932416L });
/* 9082 */   public static final BitSet FOLLOW_id_in_ruleScopeSpec1111 = new BitSet(new long[] { 0L, 131104L });
/* 9083 */   public static final BitSet FOLLOW_81_in_ruleScopeSpec1114 = new BitSet(new long[] { 567347999932416L });
/* 9084 */   public static final BitSet FOLLOW_id_in_ruleScopeSpec1116 = new BitSet(new long[] { 0L, 131104L });
/* 9085 */   public static final BitSet FOLLOW_69_in_ruleScopeSpec1120 = new BitSet(new long[] { 2L });
/* 9086 */   public static final BitSet FOLLOW_SCOPE_in_ruleScopeSpec1134 = new BitSet(new long[] { 35184372088832L });
/* 9087 */   public static final BitSet FOLLOW_ACTION_in_ruleScopeSpec1136 = new BitSet(new long[] { 2147483648L });
/* 9088 */   public static final BitSet FOLLOW_SCOPE_in_ruleScopeSpec1140 = new BitSet(new long[] { 567347999932416L });
/* 9089 */   public static final BitSet FOLLOW_id_in_ruleScopeSpec1142 = new BitSet(new long[] { 0L, 131104L });
/* 9090 */   public static final BitSet FOLLOW_81_in_ruleScopeSpec1145 = new BitSet(new long[] { 567347999932416L });
/* 9091 */   public static final BitSet FOLLOW_id_in_ruleScopeSpec1147 = new BitSet(new long[] { 0L, 131104L });
/* 9092 */   public static final BitSet FOLLOW_69_in_ruleScopeSpec1151 = new BitSet(new long[] { 2L });
/* 9093 */   public static final BitSet FOLLOW_82_in_block1183 = new BitSet(new long[] { 700530640814080L, 303857664L });
/* 9094 */   public static final BitSet FOLLOW_optionsSpec_in_block1192 = new BitSet(new long[] { 0L, 32768L });
/* 9095 */   public static final BitSet FOLLOW_79_in_block1196 = new BitSet(new long[] { 630161896636416L, 303824896L });
/* 9096 */   public static final BitSet FOLLOW_alternative_in_block1205 = new BitSet(new long[] { 1099511627776L, 1572864L });
/* 9097 */   public static final BitSet FOLLOW_rewrite_in_block1207 = new BitSet(new long[] { 0L, 1572864L });
/* 9098 */   public static final BitSet FOLLOW_83_in_block1211 = new BitSet(new long[] { 630161896636416L, 303824896L });
/* 9099 */   public static final BitSet FOLLOW_alternative_in_block1215 = new BitSet(new long[] { 1099511627776L, 1572864L });
/* 9100 */   public static final BitSet FOLLOW_rewrite_in_block1217 = new BitSet(new long[] { 0L, 1572864L });
/* 9101 */   public static final BitSet FOLLOW_84_in_block1232 = new BitSet(new long[] { 2L });
/* 9102 */   public static final BitSet FOLLOW_alternative_in_altList1289 = new BitSet(new long[] { 1099511627776L, 524288L });
/* 9103 */   public static final BitSet FOLLOW_rewrite_in_altList1291 = new BitSet(new long[] { 2L, 524288L });
/* 9104 */   public static final BitSet FOLLOW_83_in_altList1295 = new BitSet(new long[] { 630161896636416L, 302776320L });
/* 9105 */   public static final BitSet FOLLOW_alternative_in_altList1299 = new BitSet(new long[] { 1099511627776L, 524288L });
/* 9106 */   public static final BitSet FOLLOW_rewrite_in_altList1301 = new BitSet(new long[] { 2L, 524288L });
/* 9107 */   public static final BitSet FOLLOW_element_in_alternative1349 = new BitSet(new long[] { 629062385008642L, 302252032L });
/* 9108 */   public static final BitSet FOLLOW_exceptionHandler_in_exceptionGroup1400 = new BitSet(new long[] { 2L, 6291456L });
/* 9109 */   public static final BitSet FOLLOW_finallyClause_in_exceptionGroup1407 = new BitSet(new long[] { 2L });
/* 9110 */   public static final BitSet FOLLOW_finallyClause_in_exceptionGroup1415 = new BitSet(new long[] { 2L });
/* 9111 */   public static final BitSet FOLLOW_85_in_exceptionHandler1435 = new BitSet(new long[] { 281474976710656L });
/* 9112 */   public static final BitSet FOLLOW_ARG_ACTION_in_exceptionHandler1437 = new BitSet(new long[] { 35184372088832L });
/* 9113 */   public static final BitSet FOLLOW_ACTION_in_exceptionHandler1439 = new BitSet(new long[] { 2L });
/* 9114 */   public static final BitSet FOLLOW_86_in_finallyClause1469 = new BitSet(new long[] { 35184372088832L });
/* 9115 */   public static final BitSet FOLLOW_ACTION_in_finallyClause1471 = new BitSet(new long[] { 2L });
/* 9116 */   public static final BitSet FOLLOW_elementNoOptionSpec_in_element1493 = new BitSet(new long[] { 2L });
/* 9117 */   public static final BitSet FOLLOW_id_in_elementNoOptionSpec1504 = new BitSet(new long[] { 0L, 8388736L });
/* 9118 */   public static final BitSet FOLLOW_71_in_elementNoOptionSpec1509 = new BitSet(new long[] { 593736278999040L, 301989888L });
/* 9119 */   public static final BitSet FOLLOW_87_in_elementNoOptionSpec1513 = new BitSet(new long[] { 593736278999040L, 301989888L });
/* 9120 */   public static final BitSet FOLLOW_atom_in_elementNoOptionSpec1516 = new BitSet(new long[] { 2L, 201327616L });
/* 9121 */   public static final BitSet FOLLOW_ebnfSuffix_in_elementNoOptionSpec1522 = new BitSet(new long[] { 2L });
/* 9122 */   public static final BitSet FOLLOW_id_in_elementNoOptionSpec1581 = new BitSet(new long[] { 0L, 8388736L });
/* 9123 */   public static final BitSet FOLLOW_71_in_elementNoOptionSpec1586 = new BitSet(new long[] { 0L, 262144L });
/* 9124 */   public static final BitSet FOLLOW_87_in_elementNoOptionSpec1590 = new BitSet(new long[] { 0L, 262144L });
/* 9125 */   public static final BitSet FOLLOW_block_in_elementNoOptionSpec1593 = new BitSet(new long[] { 2L, 201327616L });
/* 9126 */   public static final BitSet FOLLOW_ebnfSuffix_in_elementNoOptionSpec1599 = new BitSet(new long[] { 2L });
/* 9127 */   public static final BitSet FOLLOW_atom_in_elementNoOptionSpec1658 = new BitSet(new long[] { 2L, 201327616L });
/* 9128 */   public static final BitSet FOLLOW_ebnfSuffix_in_elementNoOptionSpec1664 = new BitSet(new long[] { 2L });
/* 9129 */   public static final BitSet FOLLOW_ebnf_in_elementNoOptionSpec1710 = new BitSet(new long[] { 2L });
/* 9130 */   public static final BitSet FOLLOW_ACTION_in_elementNoOptionSpec1717 = new BitSet(new long[] { 2L });
/* 9131 */   public static final BitSet FOLLOW_SEMPRED_in_elementNoOptionSpec1724 = new BitSet(new long[] { 2L, 16777216L });
/* 9132 */   public static final BitSet FOLLOW_88_in_elementNoOptionSpec1728 = new BitSet(new long[] { 2L });
/* 9133 */   public static final BitSet FOLLOW_treeSpec_in_elementNoOptionSpec1747 = new BitSet(new long[] { 2L, 201327616L });
/* 9134 */   public static final BitSet FOLLOW_ebnfSuffix_in_elementNoOptionSpec1753 = new BitSet(new long[] { 2L });
/* 9135 */   public static final BitSet FOLLOW_range_in_atom1805 = new BitSet(new long[] { 824633720834L });
/* 9136 */   public static final BitSet FOLLOW_ROOT_in_atom1812 = new BitSet(new long[] { 2L });
/* 9137 */   public static final BitSet FOLLOW_BANG_in_atom1816 = new BitSet(new long[] { 2L });
/* 9138 */   public static final BitSet FOLLOW_terminal_in_atom1844 = new BitSet(new long[] { 2L });
/* 9139 */   public static final BitSet FOLLOW_notSet_in_atom1852 = new BitSet(new long[] { 824633720834L });
/* 9140 */   public static final BitSet FOLLOW_ROOT_in_atom1859 = new BitSet(new long[] { 2L });
/* 9141 */   public static final BitSet FOLLOW_BANG_in_atom1863 = new BitSet(new long[] { 2L });
/* 9142 */   public static final BitSet FOLLOW_RULE_REF_in_atom1891 = new BitSet(new long[] { 282299610431490L });
/* 9143 */   public static final BitSet FOLLOW_ARG_ACTION_in_atom1897 = new BitSet(new long[] { 824633720834L });
/* 9144 */   public static final BitSet FOLLOW_ROOT_in_atom1907 = new BitSet(new long[] { 2L });
/* 9145 */   public static final BitSet FOLLOW_BANG_in_atom1911 = new BitSet(new long[] { 2L });
/* 9146 */   public static final BitSet FOLLOW_89_in_notSet1994 = new BitSet(new long[] { 30786325577728L, 262144L });
/* 9147 */   public static final BitSet FOLLOW_notTerminal_in_notSet2000 = new BitSet(new long[] { 2L });
/* 9148 */   public static final BitSet FOLLOW_block_in_notSet2014 = new BitSet(new long[] { 2L });
/* 9149 */   public static final BitSet FOLLOW_TREE_BEGIN_in_treeSpec2038 = new BitSet(new long[] { 629062385008640L, 302252032L });
/* 9150 */   public static final BitSet FOLLOW_element_in_treeSpec2040 = new BitSet(new long[] { 629062385008640L, 302252032L });
/* 9151 */   public static final BitSet FOLLOW_element_in_treeSpec2044 = new BitSet(new long[] { 629062385008640L, 303300608L });
/* 9152 */   public static final BitSet FOLLOW_84_in_treeSpec2049 = new BitSet(new long[] { 2L });
/* 9153 */   public static final BitSet FOLLOW_block_in_ebnf2081 = new BitSet(new long[] { 2L, 218104832L });
/* 9154 */   public static final BitSet FOLLOW_90_in_ebnf2089 = new BitSet(new long[] { 2L });
/* 9155 */   public static final BitSet FOLLOW_74_in_ebnf2106 = new BitSet(new long[] { 2L });
/* 9156 */   public static final BitSet FOLLOW_91_in_ebnf2123 = new BitSet(new long[] { 2L });
/* 9157 */   public static final BitSet FOLLOW_88_in_ebnf2140 = new BitSet(new long[] { 2L });
/* 9158 */   public static final BitSet FOLLOW_CHAR_LITERAL_in_range2223 = new BitSet(new long[] { 8192L });
/* 9159 */   public static final BitSet FOLLOW_RANGE_in_range2225 = new BitSet(new long[] { 17592186044416L });
/* 9160 */   public static final BitSet FOLLOW_CHAR_LITERAL_in_range2229 = new BitSet(new long[] { 2L });
/* 9161 */   public static final BitSet FOLLOW_CHAR_LITERAL_in_terminal2260 = new BitSet(new long[] { 824633720834L });
/* 9162 */   public static final BitSet FOLLOW_TOKEN_REF_in_terminal2282 = new BitSet(new long[] { 282299610431490L });
/* 9163 */   public static final BitSet FOLLOW_ARG_ACTION_in_terminal2289 = new BitSet(new long[] { 824633720834L });
/* 9164 */   public static final BitSet FOLLOW_STRING_LITERAL_in_terminal2328 = new BitSet(new long[] { 824633720834L });
/* 9165 */   public static final BitSet FOLLOW_92_in_terminal2343 = new BitSet(new long[] { 824633720834L });
/* 9166 */   public static final BitSet FOLLOW_ROOT_in_terminal2364 = new BitSet(new long[] { 2L });
/* 9167 */   public static final BitSet FOLLOW_BANG_in_terminal2385 = new BitSet(new long[] { 2L });
/* 9168 */   public static final BitSet FOLLOW_set_in_notTerminal0 = new BitSet(new long[] { 2L });
/* 9169 */   public static final BitSet FOLLOW_90_in_ebnfSuffix2445 = new BitSet(new long[] { 2L });
/* 9170 */   public static final BitSet FOLLOW_74_in_ebnfSuffix2457 = new BitSet(new long[] { 2L });
/* 9171 */   public static final BitSet FOLLOW_91_in_ebnfSuffix2470 = new BitSet(new long[] { 2L });
/* 9172 */   public static final BitSet FOLLOW_REWRITE_in_rewrite2499 = new BitSet(new long[] { 4294967296L });
/* 9173 */   public static final BitSet FOLLOW_SEMPRED_in_rewrite2503 = new BitSet(new long[] { 630157601669120L, 537133056L });
/* 9174 */   public static final BitSet FOLLOW_rewrite_alternative_in_rewrite2507 = new BitSet(new long[] { 1099511627776L });
/* 9175 */   public static final BitSet FOLLOW_REWRITE_in_rewrite2515 = new BitSet(new long[] { 629058090041344L, 537133056L });
/* 9176 */   public static final BitSet FOLLOW_rewrite_alternative_in_rewrite2519 = new BitSet(new long[] { 2L });
/* 9177 */   public static final BitSet FOLLOW_rewrite_template_in_rewrite_alternative2570 = new BitSet(new long[] { 2L });
/* 9178 */   public static final BitSet FOLLOW_rewrite_tree_alternative_in_rewrite_alternative2575 = new BitSet(new long[] { 2L });
/* 9179 */   public static final BitSet FOLLOW_82_in_rewrite_tree_block2617 = new BitSet(new long[] { 629058090041344L, 537133056L });
/* 9180 */   public static final BitSet FOLLOW_rewrite_tree_alternative_in_rewrite_tree_block2619 = new BitSet(new long[] { 0L, 1048576L });
/* 9181 */   public static final BitSet FOLLOW_84_in_rewrite_tree_block2621 = new BitSet(new long[] { 2L });
/* 9182 */   public static final BitSet FOLLOW_rewrite_tree_element_in_rewrite_tree_alternative2655 = new BitSet(new long[] { 629058090041346L, 537133056L });
/* 9183 */   public static final BitSet FOLLOW_rewrite_tree_atom_in_rewrite_tree_element2683 = new BitSet(new long[] { 2L });
/* 9184 */   public static final BitSet FOLLOW_rewrite_tree_atom_in_rewrite_tree_element2688 = new BitSet(new long[] { 0L, 201327616L });
/* 9185 */   public static final BitSet FOLLOW_ebnfSuffix_in_rewrite_tree_element2690 = new BitSet(new long[] { 2L });
/* 9186 */   public static final BitSet FOLLOW_rewrite_tree_in_rewrite_tree_element2724 = new BitSet(new long[] { 2L, 201327616L });
/* 9187 */   public static final BitSet FOLLOW_ebnfSuffix_in_rewrite_tree_element2730 = new BitSet(new long[] { 2L });
/* 9188 */   public static final BitSet FOLLOW_rewrite_tree_ebnf_in_rewrite_tree_element2776 = new BitSet(new long[] { 2L });
/* 9189 */   public static final BitSet FOLLOW_CHAR_LITERAL_in_rewrite_tree_atom2792 = new BitSet(new long[] { 2L });
/* 9190 */   public static final BitSet FOLLOW_TOKEN_REF_in_rewrite_tree_atom2799 = new BitSet(new long[] { 281474976710658L });
/* 9191 */   public static final BitSet FOLLOW_ARG_ACTION_in_rewrite_tree_atom2801 = new BitSet(new long[] { 2L });
/* 9192 */   public static final BitSet FOLLOW_RULE_REF_in_rewrite_tree_atom2822 = new BitSet(new long[] { 2L });
/* 9193 */   public static final BitSet FOLLOW_STRING_LITERAL_in_rewrite_tree_atom2829 = new BitSet(new long[] { 2L });
/* 9194 */   public static final BitSet FOLLOW_93_in_rewrite_tree_atom2838 = new BitSet(new long[] { 567347999932416L });
/* 9195 */   public static final BitSet FOLLOW_id_in_rewrite_tree_atom2840 = new BitSet(new long[] { 2L });
/* 9196 */   public static final BitSet FOLLOW_ACTION_in_rewrite_tree_atom2851 = new BitSet(new long[] { 2L });
/* 9197 */   public static final BitSet FOLLOW_rewrite_tree_block_in_rewrite_tree_ebnf2872 = new BitSet(new long[] { 0L, 201327616L });
/* 9198 */   public static final BitSet FOLLOW_ebnfSuffix_in_rewrite_tree_ebnf2874 = new BitSet(new long[] { 2L });
/* 9199 */   public static final BitSet FOLLOW_TREE_BEGIN_in_rewrite_tree2894 = new BitSet(new long[] { 628920651087872L, 536870912L });
/* 9200 */   public static final BitSet FOLLOW_rewrite_tree_atom_in_rewrite_tree2896 = new BitSet(new long[] { 629058090041344L, 538181632L });
/* 9201 */   public static final BitSet FOLLOW_rewrite_tree_element_in_rewrite_tree2898 = new BitSet(new long[] { 629058090041344L, 538181632L });
/* 9202 */   public static final BitSet FOLLOW_84_in_rewrite_tree2901 = new BitSet(new long[] { 2L });
/* 9203 */   public static final BitSet FOLLOW_id_in_rewrite_template2933 = new BitSet(new long[] { 0L, 262144L });
/* 9204 */   public static final BitSet FOLLOW_82_in_rewrite_template2937 = new BitSet(new long[] { 567347999932416L, 1048576L });
/* 9205 */   public static final BitSet FOLLOW_rewrite_template_args_in_rewrite_template2939 = new BitSet(new long[] { 0L, 1048576L });
/* 9206 */   public static final BitSet FOLLOW_84_in_rewrite_template2941 = new BitSet(new long[] { 3377699720527872L });
/* 9207 */   public static final BitSet FOLLOW_DOUBLE_QUOTE_STRING_LITERAL_in_rewrite_template2949 = new BitSet(new long[] { 2L });
/* 9208 */   public static final BitSet FOLLOW_DOUBLE_ANGLE_STRING_LITERAL_in_rewrite_template2955 = new BitSet(new long[] { 2L });
/* 9209 */   public static final BitSet FOLLOW_rewrite_template_ref_in_rewrite_template2982 = new BitSet(new long[] { 2L });
/* 9210 */   public static final BitSet FOLLOW_rewrite_indirect_template_head_in_rewrite_template2991 = new BitSet(new long[] { 2L });
/* 9211 */   public static final BitSet FOLLOW_ACTION_in_rewrite_template3000 = new BitSet(new long[] { 2L });
/* 9212 */   public static final BitSet FOLLOW_id_in_rewrite_template_ref3013 = new BitSet(new long[] { 0L, 262144L });
/* 9213 */   public static final BitSet FOLLOW_82_in_rewrite_template_ref3017 = new BitSet(new long[] { 567347999932416L, 1048576L });
/* 9214 */   public static final BitSet FOLLOW_rewrite_template_args_in_rewrite_template_ref3019 = new BitSet(new long[] { 0L, 1048576L });
/* 9215 */   public static final BitSet FOLLOW_84_in_rewrite_template_ref3021 = new BitSet(new long[] { 2L });
/* 9216 */   public static final BitSet FOLLOW_82_in_rewrite_indirect_template_head3049 = new BitSet(new long[] { 35184372088832L });
/* 9217 */   public static final BitSet FOLLOW_ACTION_in_rewrite_indirect_template_head3051 = new BitSet(new long[] { 0L, 1048576L });
/* 9218 */   public static final BitSet FOLLOW_84_in_rewrite_indirect_template_head3053 = new BitSet(new long[] { 0L, 262144L });
/* 9219 */   public static final BitSet FOLLOW_82_in_rewrite_indirect_template_head3055 = new BitSet(new long[] { 567347999932416L, 1048576L });
/* 9220 */   public static final BitSet FOLLOW_rewrite_template_args_in_rewrite_indirect_template_head3057 = new BitSet(new long[] { 0L, 1048576L });
/* 9221 */   public static final BitSet FOLLOW_84_in_rewrite_indirect_template_head3059 = new BitSet(new long[] { 2L });
/* 9222 */   public static final BitSet FOLLOW_rewrite_template_arg_in_rewrite_template_args3083 = new BitSet(new long[] { 2L, 131072L });
/* 9223 */   public static final BitSet FOLLOW_81_in_rewrite_template_args3086 = new BitSet(new long[] { 567347999932416L });
/* 9224 */   public static final BitSet FOLLOW_rewrite_template_arg_in_rewrite_template_args3088 = new BitSet(new long[] { 2L, 131072L });
/* 9225 */   public static final BitSet FOLLOW_id_in_rewrite_template_arg3121 = new BitSet(new long[] { 0L, 128L });
/* 9226 */   public static final BitSet FOLLOW_71_in_rewrite_template_arg3123 = new BitSet(new long[] { 35184372088832L });
/* 9227 */   public static final BitSet FOLLOW_ACTION_in_rewrite_template_arg3125 = new BitSet(new long[] { 2L });
/* 9228 */   public static final BitSet FOLLOW_TOKEN_REF_in_id3146 = new BitSet(new long[] { 2L });
/* 9229 */   public static final BitSet FOLLOW_RULE_REF_in_id3156 = new BitSet(new long[] { 2L });
/* 9230 */   public static final BitSet FOLLOW_rewrite_template_in_synpred1_ANTLRv32570 = new BitSet(new long[] { 2L });
/* 9231 */   public static final BitSet FOLLOW_rewrite_tree_alternative_in_synpred2_ANTLRv32575 = new BitSet(new long[] { 2L });
/*      */ 
/*      */   public ANTLRv3Parser(TokenStream input)
/*      */   {
/*  119 */     this(input, new RecognizerSharedState());
/*      */   }
/*      */   public ANTLRv3Parser(TokenStream input, RecognizerSharedState state) {
/*  122 */     super(input, state);
/*      */   }
/*      */ 
/*      */   public void setTreeAdaptor(TreeAdaptor adaptor)
/*      */   {
/*  129 */     this.adaptor = adaptor;
/*      */   }
/*      */   public TreeAdaptor getTreeAdaptor() {
/*  132 */     return this.adaptor;
/*      */   }
/*      */   public String[] getTokenNames() {
/*  135 */     return tokenNames; } 
/*  136 */   public String getGrammarFileName() { return "org/antlr/gunit/swingui/parsers/ANTLRv3.g"; }
/*      */ 
/*      */ 
/*      */   public final grammarDef_return grammarDef()
/*      */     throws RecognitionException
/*      */   {
/*  151 */     grammarDef_return retval = new grammarDef_return();
/*  152 */     retval.start = this.input.LT(1);
/*      */ 
/*  154 */     CommonTree root_0 = null;
/*      */ 
/*  156 */     Token g = null;
/*  157 */     Token DOC_COMMENT1 = null;
/*  158 */     Token string_literal2 = null;
/*  159 */     Token string_literal3 = null;
/*  160 */     Token string_literal4 = null;
/*  161 */     Token char_literal6 = null;
/*  162 */     Token EOF12 = null;
/*  163 */     id_return id5 = null;
/*      */ 
/*  165 */     optionsSpec_return optionsSpec7 = null;
/*      */ 
/*  167 */     tokensSpec_return tokensSpec8 = null;
/*      */ 
/*  169 */     attrScope_return attrScope9 = null;
/*      */ 
/*  171 */     action_return action10 = null;
/*      */ 
/*  173 */     rule_return rule11 = null;
/*      */ 
/*  176 */     CommonTree g_tree = null;
/*  177 */     CommonTree DOC_COMMENT1_tree = null;
/*  178 */     CommonTree string_literal2_tree = null;
/*  179 */     CommonTree string_literal3_tree = null;
/*  180 */     CommonTree string_literal4_tree = null;
/*  181 */     CommonTree char_literal6_tree = null;
/*  182 */     CommonTree EOF12_tree = null;
/*  183 */     RewriteRuleTokenStream stream_67 = new RewriteRuleTokenStream(this.adaptor, "token 67");
/*  184 */     RewriteRuleTokenStream stream_DOC_COMMENT = new RewriteRuleTokenStream(this.adaptor, "token DOC_COMMENT");
/*  185 */     RewriteRuleTokenStream stream_66 = new RewriteRuleTokenStream(this.adaptor, "token 66");
/*  186 */     RewriteRuleTokenStream stream_69 = new RewriteRuleTokenStream(this.adaptor, "token 69");
/*  187 */     RewriteRuleTokenStream stream_68 = new RewriteRuleTokenStream(this.adaptor, "token 68");
/*  188 */     RewriteRuleTokenStream stream_65 = new RewriteRuleTokenStream(this.adaptor, "token 65");
/*  189 */     RewriteRuleTokenStream stream_EOF = new RewriteRuleTokenStream(this.adaptor, "token EOF");
/*  190 */     RewriteRuleSubtreeStream stream_id = new RewriteRuleSubtreeStream(this.adaptor, "rule id");
/*  191 */     RewriteRuleSubtreeStream stream_tokensSpec = new RewriteRuleSubtreeStream(this.adaptor, "rule tokensSpec");
/*  192 */     RewriteRuleSubtreeStream stream_attrScope = new RewriteRuleSubtreeStream(this.adaptor, "rule attrScope");
/*  193 */     RewriteRuleSubtreeStream stream_rule = new RewriteRuleSubtreeStream(this.adaptor, "rule rule");
/*  194 */     RewriteRuleSubtreeStream stream_action = new RewriteRuleSubtreeStream(this.adaptor, "rule action");
/*  195 */     RewriteRuleSubtreeStream stream_optionsSpec = new RewriteRuleSubtreeStream(this.adaptor, "rule optionsSpec");
/*      */     try
/*      */     {
/*  201 */       int alt1 = 2;
/*  202 */       switch (this.input.LA(1))
/*      */       {
/*      */       case 4:
/*  205 */         alt1 = 1;
/*      */       }
/*      */ 
/*  210 */       switch (alt1)
/*      */       {
/*      */       case 1:
/*  214 */         DOC_COMMENT1 = (Token)match(this.input, 4, FOLLOW_DOC_COMMENT_in_grammarDef347); if (this.state.failed) { grammarDef_return localgrammarDef_return1 = retval; return localgrammarDef_return1; }
/*  215 */         if (this.state.backtracking == 0) stream_DOC_COMMENT.add(DOC_COMMENT1);
/*      */ 
/*      */         break;
/*      */       }
/*      */ 
/*  224 */       int alt2 = 4;
/*      */       Object nvae;
/*  225 */       switch (this.input.LA(1))
/*      */       {
/*      */       case 65:
/*  228 */         alt2 = 1;
/*      */ 
/*  230 */         break;
/*      */       case 66:
/*  233 */         alt2 = 2;
/*      */ 
/*  235 */         break;
/*      */       case 67:
/*  238 */         alt2 = 3;
/*      */ 
/*  240 */         break;
/*      */       case 68:
/*  243 */         alt2 = 4;
/*      */ 
/*  245 */         break;
/*      */       default:
/*  247 */         if (this.state.backtracking > 0) { this.state.failed = true; grammarDef_return localgrammarDef_return2 = retval; return localgrammarDef_return2; }
/*  248 */         nvae = new NoViableAltException("", 2, 0, this.input);
/*      */ 
/*  251 */         throw ((Throwable)nvae);
/*      */       }
/*      */ 
/*  254 */       switch (alt2)
/*      */       {
/*      */       case 1:
/*  258 */         string_literal2 = (Token)match(this.input, 65, FOLLOW_65_in_grammarDef357); if (this.state.failed) { nvae = retval; return nvae; }
/*  259 */         if (this.state.backtracking == 0) stream_65.add(string_literal2);
/*      */ 
/*  261 */         if (this.state.backtracking == 0)
/*  262 */           this.gtype = 24; break;
/*      */       case 2:
/*  270 */         string_literal3 = (Token)match(this.input, 66, FOLLOW_66_in_grammarDef375); if (this.state.failed) { nvae = retval; return nvae; }
/*  271 */         if (this.state.backtracking == 0) stream_66.add(string_literal3);
/*      */ 
/*  273 */         if (this.state.backtracking == 0)
/*  274 */           this.gtype = 25; break;
/*      */       case 3:
/*  282 */         string_literal4 = (Token)match(this.input, 67, FOLLOW_67_in_grammarDef391); if (this.state.failed) { nvae = retval; return nvae; }
/*  283 */         if (this.state.backtracking == 0) stream_67.add(string_literal4);
/*      */ 
/*  285 */         if (this.state.backtracking == 0)
/*  286 */           this.gtype = 26; break;
/*      */       case 4:
/*  294 */         if (this.state.backtracking == 0) {
/*  295 */           this.gtype = 27;
/*      */         }
/*      */ 
/*      */         break;
/*      */       }
/*      */ 
/*  303 */       g = (Token)match(this.input, 68, FOLLOW_68_in_grammarDef432); if (this.state.failed) { nvae = retval; return nvae; }
/*  304 */       if (this.state.backtracking == 0) stream_68.add(g);
/*      */ 
/*  306 */       pushFollow(FOLLOW_id_in_grammarDef434);
/*  307 */       id5 = id();
/*      */ 
/*  309 */       this.state._fsp -= 1;
/*  310 */       if (this.state.failed) { nvae = retval; return nvae; }
/*  311 */       if (this.state.backtracking == 0) stream_id.add(id5.getTree());
/*  312 */       char_literal6 = (Token)match(this.input, 69, FOLLOW_69_in_grammarDef436); if (this.state.failed) { nvae = retval; return nvae; }
/*  313 */       if (this.state.backtracking == 0) stream_69.add(char_literal6);
/*      */ 
/*  316 */       int alt3 = 2;
/*  317 */       switch (this.input.LA(1))
/*      */       {
/*      */       case 46:
/*  320 */         alt3 = 1;
/*      */       }
/*      */ 
/*  325 */       switch (alt3)
/*      */       {
/*      */       case 1:
/*  329 */         pushFollow(FOLLOW_optionsSpec_in_grammarDef438);
/*  330 */         optionsSpec7 = optionsSpec();
/*      */ 
/*  332 */         this.state._fsp -= 1;
/*  333 */         if (this.state.failed) { grammarDef_return localgrammarDef_return3 = retval; return localgrammarDef_return3; }
/*  334 */         if (this.state.backtracking == 0) stream_optionsSpec.add(optionsSpec7.getTree());
/*      */ 
/*      */         break;
/*      */       }
/*      */ 
/*  342 */       int alt4 = 2;
/*  343 */       switch (this.input.LA(1))
/*      */       {
/*      */       case 41:
/*  346 */         alt4 = 1;
/*      */       }
/*      */ 
/*  351 */       switch (alt4)
/*      */       {
/*      */       case 1:
/*  355 */         pushFollow(FOLLOW_tokensSpec_in_grammarDef441);
/*  356 */         tokensSpec8 = tokensSpec();
/*      */ 
/*  358 */         this.state._fsp -= 1;
/*  359 */         if (this.state.failed) { grammarDef_return localgrammarDef_return4 = retval; return localgrammarDef_return4; }
/*  360 */         if (this.state.backtracking == 0) stream_tokensSpec.add(tokensSpec8.getTree());
/*      */ 
/*      */         break;
/*      */       }
/*      */ 
/*      */       grammarDef_return localgrammarDef_return5;
/*      */       while (true)
/*      */       {
/*  370 */         int alt5 = 2;
/*  371 */         switch (this.input.LA(1))
/*      */         {
/*      */         case 31:
/*  374 */           alt5 = 1;
/*      */         }
/*      */ 
/*  380 */         switch (alt5)
/*      */         {
/*      */         case 1:
/*  384 */           pushFollow(FOLLOW_attrScope_in_grammarDef444);
/*  385 */           attrScope9 = attrScope();
/*      */ 
/*  387 */           this.state._fsp -= 1;
/*  388 */           if (this.state.failed) { localgrammarDef_return5 = retval; return localgrammarDef_return5; }
/*  389 */           if (this.state.backtracking == 0) stream_attrScope.add(attrScope9.getTree()); break;
/*      */         default:
/*  395 */           break label1271;
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/*      */       while (true)
/*      */       {
/*  402 */         label1271: int alt6 = 2;
/*  403 */         switch (this.input.LA(1))
/*      */         {
/*      */         case 72:
/*  406 */           alt6 = 1;
/*      */         }
/*      */ 
/*  412 */         switch (alt6)
/*      */         {
/*      */         case 1:
/*  416 */           pushFollow(FOLLOW_action_in_grammarDef447);
/*  417 */           action10 = action();
/*      */ 
/*  419 */           this.state._fsp -= 1;
/*  420 */           if (this.state.failed) { localgrammarDef_return5 = retval; return localgrammarDef_return5; }
/*  421 */           if (this.state.backtracking == 0) stream_action.add(action10.getTree()); break;
/*      */         default:
/*  427 */           break label1399;
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/*  432 */       label1399: int cnt7 = 0;
/*      */       int alt7;
/*      */       while (true) { alt7 = 2;
/*  436 */         switch (this.input.LA(1))
/*      */         {
/*      */         case 4:
/*      */         case 36:
/*      */         case 42:
/*      */         case 49:
/*      */         case 75:
/*      */         case 76:
/*      */         case 77:
/*  445 */           alt7 = 1;
/*      */         }
/*      */         grammarDef_return localgrammarDef_return7;
/*  451 */         switch (alt7)
/*      */         {
/*      */         case 1:
/*  455 */           pushFollow(FOLLOW_rule_in_grammarDef455);
/*  456 */           rule11 = rule();
/*      */ 
/*  458 */           this.state._fsp -= 1;
/*  459 */           if (this.state.failed) { localgrammarDef_return7 = retval; return localgrammarDef_return7; }
/*  460 */           if (this.state.backtracking == 0) stream_rule.add(rule11.getTree()); break;
/*      */         default:
/*  466 */           if (cnt7 >= 1) break label1626;
/*  467 */           if (this.state.backtracking > 0) { this.state.failed = true; localgrammarDef_return7 = retval; return localgrammarDef_return7; }
/*  468 */           EarlyExitException eee = new EarlyExitException(7, this.input);
/*      */ 
/*  470 */           throw eee;
/*      */         }
/*  472 */         cnt7++;
/*      */       }
/*      */ 
/*  475 */       label1626: EOF12 = (Token)match(this.input, -1, FOLLOW_EOF_in_grammarDef463); if (this.state.failed) { grammarDef_return localgrammarDef_return6 = retval; return localgrammarDef_return6; }
/*  476 */       if (this.state.backtracking == 0) stream_EOF.add(EOF12);
/*      */ 
/*  487 */       if (this.state.backtracking == 0) {
/*  488 */         retval.tree = root_0;
/*  489 */         RewriteRuleSubtreeStream stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.tree : null);
/*      */ 
/*  491 */         root_0 = (CommonTree)this.adaptor.nil();
/*      */ 
/*  496 */         CommonTree root_1 = (CommonTree)this.adaptor.nil();
/*  497 */         root_1 = (CommonTree)this.adaptor.becomeRoot(this.adaptor.create(this.gtype, g), root_1);
/*      */ 
/*  499 */         this.adaptor.addChild(root_1, stream_id.nextTree());
/*      */ 
/*  501 */         if (stream_DOC_COMMENT.hasNext()) {
/*  502 */           this.adaptor.addChild(root_1, stream_DOC_COMMENT.nextNode());
/*      */         }
/*      */ 
/*  505 */         stream_DOC_COMMENT.reset();
/*      */ 
/*  507 */         if (stream_optionsSpec.hasNext()) {
/*  508 */           this.adaptor.addChild(root_1, stream_optionsSpec.nextTree());
/*      */         }
/*      */ 
/*  511 */         stream_optionsSpec.reset();
/*      */ 
/*  513 */         if (stream_tokensSpec.hasNext()) {
/*  514 */           this.adaptor.addChild(root_1, stream_tokensSpec.nextTree());
/*      */         }
/*      */ 
/*  517 */         stream_tokensSpec.reset();
/*      */ 
/*  519 */         while (stream_attrScope.hasNext()) {
/*  520 */           this.adaptor.addChild(root_1, stream_attrScope.nextTree());
/*      */         }
/*      */ 
/*  523 */         stream_attrScope.reset();
/*      */ 
/*  525 */         while (stream_action.hasNext()) {
/*  526 */           this.adaptor.addChild(root_1, stream_action.nextTree());
/*      */         }
/*      */ 
/*  529 */         stream_action.reset();
/*  530 */         if (!stream_rule.hasNext()) {
/*  531 */           throw new RewriteEarlyExitException();
/*      */         }
/*  533 */         while (stream_rule.hasNext()) {
/*  534 */           this.adaptor.addChild(root_1, stream_rule.nextTree());
/*      */         }
/*      */ 
/*  537 */         stream_rule.reset();
/*      */ 
/*  539 */         this.adaptor.addChild(root_0, root_1);
/*      */ 
/*  544 */         retval.tree = root_0;
/*      */       }
/*      */ 
/*  547 */       retval.stop = this.input.LT(-1);
/*      */ 
/*  549 */       if (this.state.backtracking == 0)
/*      */       {
/*  551 */         retval.tree = ((CommonTree)this.adaptor.rulePostProcessing(root_0));
/*  552 */         this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
/*      */       }
/*      */     } catch (RecognitionException re) {
/*  555 */       re = 
/*  562 */         re;
/*      */ 
/*  556 */       reportError(re);
/*  557 */       recover(this.input, re);
/*  558 */       retval.tree = ((CommonTree)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), re));
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*  563 */     return retval;
/*      */   }
/*      */ 
/*      */   public final tokensSpec_return tokensSpec()
/*      */     throws RecognitionException
/*      */   {
/*  575 */     tokensSpec_return retval = new tokensSpec_return();
/*  576 */     retval.start = this.input.LT(1);
/*      */ 
/*  578 */     CommonTree root_0 = null;
/*      */ 
/*  580 */     Token TOKENS13 = null;
/*  581 */     Token char_literal15 = null;
/*  582 */     tokenSpec_return tokenSpec14 = null;
/*      */ 
/*  585 */     CommonTree TOKENS13_tree = null;
/*  586 */     CommonTree char_literal15_tree = null;
/*  587 */     RewriteRuleTokenStream stream_TOKENS = new RewriteRuleTokenStream(this.adaptor, "token TOKENS");
/*  588 */     RewriteRuleTokenStream stream_70 = new RewriteRuleTokenStream(this.adaptor, "token 70");
/*  589 */     RewriteRuleSubtreeStream stream_tokenSpec = new RewriteRuleSubtreeStream(this.adaptor, "rule tokenSpec");
/*      */     try
/*      */     {
/*  594 */       TOKENS13 = (Token)match(this.input, 41, FOLLOW_TOKENS_in_tokensSpec524); if (this.state.failed) { tokensSpec_return localtokensSpec_return1 = retval; return localtokensSpec_return1; }
/*  595 */       if (this.state.backtracking == 0) stream_TOKENS.add(TOKENS13); 
/*      */ int cnt8 = 0;
/*      */       int alt8;
/*      */       while (true)
/*      */       {
/*  601 */         alt8 = 2;
/*  602 */         switch (this.input.LA(1))
/*      */         {
/*      */         case 42:
/*  605 */           alt8 = 1;
/*      */         }
/*      */         tokensSpec_return localtokensSpec_return2;
/*  611 */         switch (alt8)
/*      */         {
/*      */         case 1:
/*  615 */           pushFollow(FOLLOW_tokenSpec_in_tokensSpec526);
/*  616 */           tokenSpec14 = tokenSpec();
/*      */ 
/*  618 */           this.state._fsp -= 1;
/*  619 */           if (this.state.failed) { localtokensSpec_return2 = retval; return localtokensSpec_return2; }
/*  620 */           if (this.state.backtracking == 0) stream_tokenSpec.add(tokenSpec14.getTree()); break;
/*      */         default:
/*  626 */           if (cnt8 >= 1) break label314;
/*  627 */           if (this.state.backtracking > 0) { this.state.failed = true; localtokensSpec_return2 = retval; return localtokensSpec_return2; }
/*  628 */           EarlyExitException eee = new EarlyExitException(8, this.input);
/*      */ 
/*  630 */           throw eee;
/*      */         }
/*  632 */         cnt8++;
/*      */       }
/*      */ 
/*  635 */       label314: char_literal15 = (Token)match(this.input, 70, FOLLOW_70_in_tokensSpec529); if (this.state.failed) { alt8 = retval; return alt8; }
/*  636 */       if (this.state.backtracking == 0) stream_70.add(char_literal15);
/*      */ 
/*  647 */       if (this.state.backtracking == 0) {
/*  648 */         retval.tree = root_0;
/*  649 */         RewriteRuleSubtreeStream stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.tree : null);
/*      */ 
/*  651 */         root_0 = (CommonTree)this.adaptor.nil();
/*      */ 
/*  656 */         CommonTree root_1 = (CommonTree)this.adaptor.nil();
/*  657 */         root_1 = (CommonTree)this.adaptor.becomeRoot(stream_TOKENS.nextNode(), root_1);
/*      */ 
/*  659 */         if (!stream_tokenSpec.hasNext()) {
/*  660 */           throw new RewriteEarlyExitException();
/*      */         }
/*  662 */         while (stream_tokenSpec.hasNext()) {
/*  663 */           this.adaptor.addChild(root_1, stream_tokenSpec.nextTree());
/*      */         }
/*      */ 
/*  666 */         stream_tokenSpec.reset();
/*      */ 
/*  668 */         this.adaptor.addChild(root_0, root_1);
/*      */ 
/*  673 */         retval.tree = root_0;
/*      */       }
/*      */ 
/*  676 */       retval.stop = this.input.LT(-1);
/*      */ 
/*  678 */       if (this.state.backtracking == 0)
/*      */       {
/*  680 */         retval.tree = ((CommonTree)this.adaptor.rulePostProcessing(root_0));
/*  681 */         this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
/*      */       }
/*      */     } catch (RecognitionException re) {
/*  684 */       re = 
/*  691 */         re;
/*      */ 
/*  685 */       reportError(re);
/*  686 */       recover(this.input, re);
/*  687 */       retval.tree = ((CommonTree)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), re));
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*  692 */     return retval;
/*      */   }
/*      */ 
/*      */   public final tokenSpec_return tokenSpec()
/*      */     throws RecognitionException
/*      */   {
/*  704 */     tokenSpec_return retval = new tokenSpec_return();
/*  705 */     retval.start = this.input.LT(1);
/*      */ 
/*  707 */     CommonTree root_0 = null;
/*      */ 
/*  709 */     Token lit = null;
/*  710 */     Token TOKEN_REF16 = null;
/*  711 */     Token char_literal17 = null;
/*  712 */     Token char_literal18 = null;
/*      */ 
/*  714 */     CommonTree lit_tree = null;
/*  715 */     CommonTree TOKEN_REF16_tree = null;
/*  716 */     CommonTree char_literal17_tree = null;
/*  717 */     CommonTree char_literal18_tree = null;
/*  718 */     RewriteRuleTokenStream stream_69 = new RewriteRuleTokenStream(this.adaptor, "token 69");
/*  719 */     RewriteRuleTokenStream stream_STRING_LITERAL = new RewriteRuleTokenStream(this.adaptor, "token STRING_LITERAL");
/*  720 */     RewriteRuleTokenStream stream_CHAR_LITERAL = new RewriteRuleTokenStream(this.adaptor, "token CHAR_LITERAL");
/*  721 */     RewriteRuleTokenStream stream_71 = new RewriteRuleTokenStream(this.adaptor, "token 71");
/*  722 */     RewriteRuleTokenStream stream_TOKEN_REF = new RewriteRuleTokenStream(this.adaptor, "token TOKEN_REF");
/*      */     try
/*      */     {
/*  728 */       TOKEN_REF16 = (Token)match(this.input, 42, FOLLOW_TOKEN_REF_in_tokenSpec549); if (this.state.failed) { tokenSpec_return localtokenSpec_return1 = retval; return localtokenSpec_return1; }
/*  729 */       if (this.state.backtracking == 0) stream_TOKEN_REF.add(TOKEN_REF16);
/*      */ 
/*  732 */       int alt10 = 2;
/*      */       Object nvae;
/*  733 */       switch (this.input.LA(1))
/*      */       {
/*      */       case 71:
/*  736 */         alt10 = 1;
/*      */ 
/*  738 */         break;
/*      */       case 69:
/*  741 */         alt10 = 2;
/*      */ 
/*  743 */         break;
/*      */       default:
/*  745 */         if (this.state.backtracking > 0) { this.state.failed = true; tokenSpec_return localtokenSpec_return2 = retval; return localtokenSpec_return2; }
/*  746 */         nvae = new NoViableAltException("", 10, 0, this.input);
/*      */ 
/*  749 */         throw ((Throwable)nvae);
/*      */       }
/*      */       Object stream_retval;
/*  752 */       switch (alt10)
/*      */       {
/*      */       case 1:
/*  756 */         char_literal17 = (Token)match(this.input, 71, FOLLOW_71_in_tokenSpec555); if (this.state.failed) { nvae = retval; return nvae; }
/*  757 */         if (this.state.backtracking == 0) stream_71.add(char_literal17);
/*      */ 
/*  760 */         int alt9 = 2;
/*      */         Object nvae;
/*  761 */         switch (this.input.LA(1))
/*      */         {
/*      */         case 43:
/*  764 */           alt9 = 1;
/*      */ 
/*  766 */           break;
/*      */         case 44:
/*  769 */           alt9 = 2;
/*      */ 
/*  771 */           break;
/*      */         default:
/*  773 */           if (this.state.backtracking > 0) { this.state.failed = true; tokenSpec_return localtokenSpec_return3 = retval; return localtokenSpec_return3; }
/*  774 */           nvae = new NoViableAltException("", 9, 0, this.input);
/*      */ 
/*  777 */           throw ((Throwable)nvae);
/*      */         }
/*      */ 
/*  780 */         switch (alt9)
/*      */         {
/*      */         case 1:
/*  784 */           lit = (Token)match(this.input, 43, FOLLOW_STRING_LITERAL_in_tokenSpec560); if (this.state.failed) { nvae = retval; return nvae; }
/*  785 */           if (this.state.backtracking == 0) stream_STRING_LITERAL.add(lit); break;
/*      */         case 2:
/*  793 */           lit = (Token)match(this.input, 44, FOLLOW_CHAR_LITERAL_in_tokenSpec564); if (this.state.failed) { nvae = retval; return nvae; }
/*  794 */           if (this.state.backtracking == 0) stream_CHAR_LITERAL.add(lit);
/*      */ 
/*      */           break;
/*      */         }
/*      */ 
/*  811 */         if (this.state.backtracking == 0) {
/*  812 */           retval.tree = root_0;
/*  813 */           RewriteRuleTokenStream stream_lit = new RewriteRuleTokenStream(this.adaptor, "token lit", lit);
/*  814 */           RewriteRuleSubtreeStream stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.tree : null);
/*      */ 
/*  816 */           root_0 = (CommonTree)this.adaptor.nil();
/*      */ 
/*  821 */           CommonTree root_1 = (CommonTree)this.adaptor.nil();
/*  822 */           root_1 = (CommonTree)this.adaptor.becomeRoot(stream_71.nextNode(), root_1);
/*      */ 
/*  824 */           this.adaptor.addChild(root_1, stream_TOKEN_REF.nextNode());
/*  825 */           this.adaptor.addChild(root_1, stream_lit.nextNode());
/*      */ 
/*  827 */           this.adaptor.addChild(root_0, root_1);
/*      */ 
/*  832 */           retval.tree = root_0;
/*      */         }
/*  834 */         break;
/*      */       case 2:
/*  846 */         if (this.state.backtracking == 0) {
/*  847 */           retval.tree = root_0;
/*  848 */           stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.tree : null);
/*      */ 
/*  850 */           root_0 = (CommonTree)this.adaptor.nil();
/*      */ 
/*  853 */           this.adaptor.addChild(root_0, stream_TOKEN_REF.nextNode());
/*      */ 
/*  857 */           retval.tree = root_0;
/*      */         }
/*      */ 
/*      */         break;
/*      */       }
/*      */ 
/*  863 */       char_literal18 = (Token)match(this.input, 69, FOLLOW_69_in_tokenSpec603); if (this.state.failed) { stream_retval = retval; return stream_retval; }
/*  864 */       if (this.state.backtracking == 0) stream_69.add(char_literal18);
/*      */ 
/*  869 */       retval.stop = this.input.LT(-1);
/*      */ 
/*  871 */       if (this.state.backtracking == 0)
/*      */       {
/*  873 */         retval.tree = ((CommonTree)this.adaptor.rulePostProcessing(root_0));
/*  874 */         this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
/*      */       }
/*      */     } catch (RecognitionException re) {
/*  877 */       re = 
/*  884 */         re;
/*      */ 
/*  878 */       reportError(re);
/*  879 */       recover(this.input, re);
/*  880 */       retval.tree = ((CommonTree)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), re));
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*  885 */     return retval;
/*      */   }
/*      */ 
/*      */   public final attrScope_return attrScope()
/*      */     throws RecognitionException
/*      */   {
/*  897 */     attrScope_return retval = new attrScope_return();
/*  898 */     retval.start = this.input.LT(1);
/*      */ 
/*  900 */     CommonTree root_0 = null;
/*      */ 
/*  902 */     Token string_literal19 = null;
/*  903 */     Token ACTION21 = null;
/*  904 */     id_return id20 = null;
/*      */ 
/*  907 */     CommonTree string_literal19_tree = null;
/*  908 */     CommonTree ACTION21_tree = null;
/*  909 */     RewriteRuleTokenStream stream_SCOPE = new RewriteRuleTokenStream(this.adaptor, "token SCOPE");
/*  910 */     RewriteRuleTokenStream stream_ACTION = new RewriteRuleTokenStream(this.adaptor, "token ACTION");
/*  911 */     RewriteRuleSubtreeStream stream_id = new RewriteRuleSubtreeStream(this.adaptor, "rule id");
/*      */     try
/*      */     {
/*  916 */       string_literal19 = (Token)match(this.input, 31, FOLLOW_SCOPE_in_attrScope614);
/*      */       attrScope_return localattrScope_return1;
/*  916 */       if (this.state.failed) { localattrScope_return1 = retval; return localattrScope_return1; }
/*  917 */       if (this.state.backtracking == 0) stream_SCOPE.add(string_literal19);
/*      */ 
/*  919 */       pushFollow(FOLLOW_id_in_attrScope616);
/*  920 */       id20 = id();
/*      */ 
/*  922 */       this.state._fsp -= 1;
/*  923 */       if (this.state.failed) { localattrScope_return1 = retval; return localattrScope_return1; }
/*  924 */       if (this.state.backtracking == 0) stream_id.add(id20.getTree());
/*  925 */       ACTION21 = (Token)match(this.input, 45, FOLLOW_ACTION_in_attrScope618); if (this.state.failed) { localattrScope_return1 = retval; return localattrScope_return1; }
/*  926 */       if (this.state.backtracking == 0) stream_ACTION.add(ACTION21);
/*      */ 
/*  937 */       if (this.state.backtracking == 0) {
/*  938 */         retval.tree = root_0;
/*  939 */         RewriteRuleSubtreeStream stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.tree : null);
/*      */ 
/*  941 */         root_0 = (CommonTree)this.adaptor.nil();
/*      */ 
/*  946 */         CommonTree root_1 = (CommonTree)this.adaptor.nil();
/*  947 */         root_1 = (CommonTree)this.adaptor.becomeRoot(stream_SCOPE.nextNode(), root_1);
/*      */ 
/*  949 */         this.adaptor.addChild(root_1, stream_id.nextTree());
/*  950 */         this.adaptor.addChild(root_1, stream_ACTION.nextNode());
/*      */ 
/*  952 */         this.adaptor.addChild(root_0, root_1);
/*      */ 
/*  957 */         retval.tree = root_0;
/*      */       }
/*      */ 
/*  960 */       retval.stop = this.input.LT(-1);
/*      */ 
/*  962 */       if (this.state.backtracking == 0)
/*      */       {
/*  964 */         retval.tree = ((CommonTree)this.adaptor.rulePostProcessing(root_0));
/*  965 */         this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
/*      */       }
/*      */     } catch (RecognitionException re) {
/*  968 */       re = 
/*  975 */         re;
/*      */ 
/*  969 */       reportError(re);
/*  970 */       recover(this.input, re);
/*  971 */       retval.tree = ((CommonTree)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), re));
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*  976 */     return retval;
/*      */   }
/*      */ 
/*      */   public final action_return action()
/*      */     throws RecognitionException
/*      */   {
/*  988 */     action_return retval = new action_return();
/*  989 */     retval.start = this.input.LT(1);
/*      */ 
/*  991 */     CommonTree root_0 = null;
/*      */ 
/*  993 */     Token char_literal22 = null;
/*  994 */     Token string_literal24 = null;
/*  995 */     Token ACTION26 = null;
/*  996 */     actionScopeName_return actionScopeName23 = null;
/*      */ 
/*  998 */     id_return id25 = null;
/*      */ 
/* 1001 */     CommonTree char_literal22_tree = null;
/* 1002 */     CommonTree string_literal24_tree = null;
/* 1003 */     CommonTree ACTION26_tree = null;
/* 1004 */     RewriteRuleTokenStream stream_72 = new RewriteRuleTokenStream(this.adaptor, "token 72");
/* 1005 */     RewriteRuleTokenStream stream_ACTION = new RewriteRuleTokenStream(this.adaptor, "token ACTION");
/* 1006 */     RewriteRuleTokenStream stream_73 = new RewriteRuleTokenStream(this.adaptor, "token 73");
/* 1007 */     RewriteRuleSubtreeStream stream_id = new RewriteRuleSubtreeStream(this.adaptor, "rule id");
/* 1008 */     RewriteRuleSubtreeStream stream_actionScopeName = new RewriteRuleSubtreeStream(this.adaptor, "rule actionScopeName");
/*      */     try
/*      */     {
/* 1013 */       char_literal22 = (Token)match(this.input, 72, FOLLOW_72_in_action641); if (this.state.failed) { action_return localaction_return1 = retval; return localaction_return1; }
/* 1014 */       if (this.state.backtracking == 0) stream_72.add(char_literal22);
/*      */ 
/* 1017 */       int alt11 = 2;
/* 1018 */       switch (this.input.LA(1))
/*      */       {
/*      */       case 42:
/* 1021 */         switch (this.input.LA(2))
/*      */         {
/*      */         case 73:
/* 1024 */           alt11 = 1;
/*      */         }
/*      */ 
/* 1030 */         break;
/*      */       case 49:
/* 1033 */         switch (this.input.LA(2))
/*      */         {
/*      */         case 73:
/* 1036 */           alt11 = 1;
/*      */         }
/*      */ 
/* 1042 */         break;
/*      */       case 65:
/*      */       case 66:
/* 1046 */         alt11 = 1;
/*      */       }
/*      */       action_return localaction_return2;
/* 1051 */       switch (alt11)
/*      */       {
/*      */       case 1:
/* 1055 */         pushFollow(FOLLOW_actionScopeName_in_action644);
/* 1056 */         actionScopeName23 = actionScopeName();
/*      */ 
/* 1058 */         this.state._fsp -= 1;
/* 1059 */         if (this.state.failed) { localaction_return2 = retval; return localaction_return2; }
/* 1060 */         if (this.state.backtracking == 0) stream_actionScopeName.add(actionScopeName23.getTree());
/* 1061 */         string_literal24 = (Token)match(this.input, 73, FOLLOW_73_in_action646); if (this.state.failed) { localaction_return2 = retval; return localaction_return2; }
/* 1062 */         if (this.state.backtracking == 0) stream_73.add(string_literal24);
/*      */ 
/*      */         break;
/*      */       }
/*      */ 
/* 1070 */       pushFollow(FOLLOW_id_in_action650);
/* 1071 */       id25 = id();
/*      */ 
/* 1073 */       this.state._fsp -= 1;
/* 1074 */       if (this.state.failed) { localaction_return2 = retval; return localaction_return2; }
/* 1075 */       if (this.state.backtracking == 0) stream_id.add(id25.getTree());
/* 1076 */       ACTION26 = (Token)match(this.input, 45, FOLLOW_ACTION_in_action652); if (this.state.failed) { localaction_return2 = retval; return localaction_return2; }
/* 1077 */       if (this.state.backtracking == 0) stream_ACTION.add(ACTION26);
/*      */ 
/* 1088 */       if (this.state.backtracking == 0) {
/* 1089 */         retval.tree = root_0;
/* 1090 */         RewriteRuleSubtreeStream stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.tree : null);
/*      */ 
/* 1092 */         root_0 = (CommonTree)this.adaptor.nil();
/*      */ 
/* 1097 */         CommonTree root_1 = (CommonTree)this.adaptor.nil();
/* 1098 */         root_1 = (CommonTree)this.adaptor.becomeRoot(stream_72.nextNode(), root_1);
/*      */ 
/* 1101 */         if (stream_actionScopeName.hasNext()) {
/* 1102 */           this.adaptor.addChild(root_1, stream_actionScopeName.nextTree());
/*      */         }
/*      */ 
/* 1105 */         stream_actionScopeName.reset();
/* 1106 */         this.adaptor.addChild(root_1, stream_id.nextTree());
/* 1107 */         this.adaptor.addChild(root_1, stream_ACTION.nextNode());
/*      */ 
/* 1109 */         this.adaptor.addChild(root_0, root_1);
/*      */ 
/* 1114 */         retval.tree = root_0;
/*      */       }
/*      */ 
/* 1117 */       retval.stop = this.input.LT(-1);
/*      */ 
/* 1119 */       if (this.state.backtracking == 0)
/*      */       {
/* 1121 */         retval.tree = ((CommonTree)this.adaptor.rulePostProcessing(root_0));
/* 1122 */         this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
/*      */       }
/*      */     } catch (RecognitionException re) {
/* 1125 */       re = 
/* 1132 */         re;
/*      */ 
/* 1126 */       reportError(re);
/* 1127 */       recover(this.input, re);
/* 1128 */       retval.tree = ((CommonTree)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), re));
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/* 1133 */     return retval;
/*      */   }
/*      */ 
/*      */   public final actionScopeName_return actionScopeName()
/*      */     throws RecognitionException
/*      */   {
/* 1145 */     actionScopeName_return retval = new actionScopeName_return();
/* 1146 */     retval.start = this.input.LT(1);
/*      */ 
/* 1148 */     CommonTree root_0 = null;
/*      */ 
/* 1150 */     Token l = null;
/* 1151 */     Token p = null;
/* 1152 */     id_return id27 = null;
/*      */ 
/* 1155 */     CommonTree l_tree = null;
/* 1156 */     CommonTree p_tree = null;
/* 1157 */     RewriteRuleTokenStream stream_66 = new RewriteRuleTokenStream(this.adaptor, "token 66");
/* 1158 */     RewriteRuleTokenStream stream_65 = new RewriteRuleTokenStream(this.adaptor, "token 65");
/*      */     try
/*      */     {
/* 1162 */       int alt12 = 3;
/*      */       Object nvae;
/* 1163 */       switch (this.input.LA(1))
/*      */       {
/*      */       case 42:
/*      */       case 49:
/* 1167 */         alt12 = 1;
/*      */ 
/* 1169 */         break;
/*      */       case 65:
/* 1172 */         alt12 = 2;
/*      */ 
/* 1174 */         break;
/*      */       case 66:
/* 1177 */         alt12 = 3;
/*      */ 
/* 1179 */         break;
/*      */       default:
/* 1181 */         if (this.state.backtracking > 0) { this.state.failed = true; actionScopeName_return localactionScopeName_return1 = retval; return localactionScopeName_return1; }
/* 1182 */         nvae = new NoViableAltException("", 12, 0, this.input);
/*      */ 
/* 1185 */         throw ((Throwable)nvae);
/*      */       }
/*      */       Object stream_retval;
/* 1188 */       switch (alt12)
/*      */       {
/*      */       case 1:
/* 1192 */         root_0 = (CommonTree)this.adaptor.nil();
/*      */ 
/* 1194 */         pushFollow(FOLLOW_id_in_actionScopeName678);
/* 1195 */         id27 = id();
/*      */ 
/* 1197 */         this.state._fsp -= 1;
/* 1198 */         if (this.state.failed) { nvae = retval; return nvae; }
/* 1199 */         if (this.state.backtracking == 0) this.adaptor.addChild(root_0, id27.getTree()); break;
/*      */       case 2:
/* 1206 */         l = (Token)match(this.input, 65, FOLLOW_65_in_actionScopeName685); if (this.state.failed) { nvae = retval; return nvae; }
/* 1207 */         if (this.state.backtracking == 0) stream_65.add(l);
/*      */ 
/* 1218 */         if (this.state.backtracking == 0) {
/* 1219 */           retval.tree = root_0;
/* 1220 */           stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.tree : null);
/*      */ 
/* 1222 */           root_0 = (CommonTree)this.adaptor.nil();
/*      */ 
/* 1225 */           this.adaptor.addChild(root_0, (CommonTree)this.adaptor.create(20, l));
/*      */ 
/* 1229 */           retval.tree = root_0; } break;
/*      */       case 3:
/* 1235 */         p = (Token)match(this.input, 66, FOLLOW_66_in_actionScopeName702); if (this.state.failed) { stream_retval = retval; return stream_retval; }
/* 1236 */         if (this.state.backtracking == 0) stream_66.add(p);
/*      */ 
/* 1247 */         if (this.state.backtracking == 0) {
/* 1248 */           retval.tree = root_0;
/* 1249 */           RewriteRuleSubtreeStream stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.tree : null);
/*      */ 
/* 1251 */           root_0 = (CommonTree)this.adaptor.nil();
/*      */ 
/* 1254 */           this.adaptor.addChild(root_0, (CommonTree)this.adaptor.create(20, p));
/*      */ 
/* 1258 */           retval.tree = root_0;
/*      */         }
/*      */         break;
/*      */       }
/*      */ 
/* 1263 */       retval.stop = this.input.LT(-1);
/*      */ 
/* 1265 */       if (this.state.backtracking == 0)
/*      */       {
/* 1267 */         retval.tree = ((CommonTree)this.adaptor.rulePostProcessing(root_0));
/* 1268 */         this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
/*      */       }
/*      */     } catch (RecognitionException re) {
/* 1271 */       re = 
/* 1278 */         re;
/*      */ 
/* 1272 */       reportError(re);
/* 1273 */       recover(this.input, re);
/* 1274 */       retval.tree = ((CommonTree)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), re));
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/* 1279 */     return retval;
/*      */   }
/*      */ 
/*      */   public final optionsSpec_return optionsSpec()
/*      */     throws RecognitionException
/*      */   {
/* 1291 */     optionsSpec_return retval = new optionsSpec_return();
/* 1292 */     retval.start = this.input.LT(1);
/*      */ 
/* 1294 */     CommonTree root_0 = null;
/*      */ 
/* 1296 */     Token OPTIONS28 = null;
/* 1297 */     Token char_literal30 = null;
/* 1298 */     Token char_literal31 = null;
/* 1299 */     option_return option29 = null;
/*      */ 
/* 1302 */     CommonTree OPTIONS28_tree = null;
/* 1303 */     CommonTree char_literal30_tree = null;
/* 1304 */     CommonTree char_literal31_tree = null;
/* 1305 */     RewriteRuleTokenStream stream_69 = new RewriteRuleTokenStream(this.adaptor, "token 69");
/* 1306 */     RewriteRuleTokenStream stream_70 = new RewriteRuleTokenStream(this.adaptor, "token 70");
/* 1307 */     RewriteRuleTokenStream stream_OPTIONS = new RewriteRuleTokenStream(this.adaptor, "token OPTIONS");
/* 1308 */     RewriteRuleSubtreeStream stream_option = new RewriteRuleSubtreeStream(this.adaptor, "rule option");
/*      */     try
/*      */     {
/* 1313 */       OPTIONS28 = (Token)match(this.input, 46, FOLLOW_OPTIONS_in_optionsSpec718); if (this.state.failed) { optionsSpec_return localoptionsSpec_return1 = retval; return localoptionsSpec_return1; }
/* 1314 */       if (this.state.backtracking == 0) stream_OPTIONS.add(OPTIONS28); 
/*      */ int cnt13 = 0;
/*      */       int alt13;
/*      */       while (true)
/*      */       {
/* 1320 */         alt13 = 2;
/* 1321 */         switch (this.input.LA(1))
/*      */         {
/*      */         case 42:
/*      */         case 49:
/* 1325 */           alt13 = 1;
/*      */         }
/*      */         optionsSpec_return localoptionsSpec_return2;
/* 1331 */         switch (alt13)
/*      */         {
/*      */         case 1:
/* 1335 */           pushFollow(FOLLOW_option_in_optionsSpec721);
/* 1336 */           option29 = option();
/*      */ 
/* 1338 */           this.state._fsp -= 1;
/* 1339 */           if (this.state.failed) { localoptionsSpec_return2 = retval; return localoptionsSpec_return2; }
/* 1340 */           if (this.state.backtracking == 0) stream_option.add(option29.getTree());
/* 1341 */           char_literal30 = (Token)match(this.input, 69, FOLLOW_69_in_optionsSpec723); if (this.state.failed) { localoptionsSpec_return2 = retval; return localoptionsSpec_return2; }
/* 1342 */           if (this.state.backtracking == 0) stream_69.add(char_literal30); break;
/*      */         default:
/* 1349 */           if (cnt13 >= 1) break label393;
/* 1350 */           if (this.state.backtracking > 0) { this.state.failed = true; localoptionsSpec_return2 = retval; return localoptionsSpec_return2; }
/* 1351 */           EarlyExitException eee = new EarlyExitException(13, this.input);
/*      */ 
/* 1353 */           throw eee;
/*      */         }
/* 1355 */         cnt13++;
/*      */       }
/*      */ 
/* 1358 */       label393: char_literal31 = (Token)match(this.input, 70, FOLLOW_70_in_optionsSpec727); if (this.state.failed) { alt13 = retval; return alt13; }
/* 1359 */       if (this.state.backtracking == 0) stream_70.add(char_literal31);
/*      */ 
/* 1370 */       if (this.state.backtracking == 0) {
/* 1371 */         retval.tree = root_0;
/* 1372 */         RewriteRuleSubtreeStream stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.tree : null);
/*      */ 
/* 1374 */         root_0 = (CommonTree)this.adaptor.nil();
/*      */ 
/* 1379 */         CommonTree root_1 = (CommonTree)this.adaptor.nil();
/* 1380 */         root_1 = (CommonTree)this.adaptor.becomeRoot(stream_OPTIONS.nextNode(), root_1);
/*      */ 
/* 1382 */         if (!stream_option.hasNext()) {
/* 1383 */           throw new RewriteEarlyExitException();
/*      */         }
/* 1385 */         while (stream_option.hasNext()) {
/* 1386 */           this.adaptor.addChild(root_1, stream_option.nextTree());
/*      */         }
/*      */ 
/* 1389 */         stream_option.reset();
/*      */ 
/* 1391 */         this.adaptor.addChild(root_0, root_1);
/*      */ 
/* 1396 */         retval.tree = root_0;
/*      */       }
/*      */ 
/* 1399 */       retval.stop = this.input.LT(-1);
/*      */ 
/* 1401 */       if (this.state.backtracking == 0)
/*      */       {
/* 1403 */         retval.tree = ((CommonTree)this.adaptor.rulePostProcessing(root_0));
/* 1404 */         this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
/*      */       }
/*      */     } catch (RecognitionException re) {
/* 1407 */       re = 
/* 1414 */         re;
/*      */ 
/* 1408 */       reportError(re);
/* 1409 */       recover(this.input, re);
/* 1410 */       retval.tree = ((CommonTree)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), re));
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/* 1415 */     return retval;
/*      */   }
/*      */ 
/*      */   public final option_return option()
/*      */     throws RecognitionException
/*      */   {
/* 1427 */     option_return retval = new option_return();
/* 1428 */     retval.start = this.input.LT(1);
/*      */ 
/* 1430 */     CommonTree root_0 = null;
/*      */ 
/* 1432 */     Token char_literal33 = null;
/* 1433 */     id_return id32 = null;
/*      */ 
/* 1435 */     optionValue_return optionValue34 = null;
/*      */ 
/* 1438 */     CommonTree char_literal33_tree = null;
/* 1439 */     RewriteRuleTokenStream stream_71 = new RewriteRuleTokenStream(this.adaptor, "token 71");
/* 1440 */     RewriteRuleSubtreeStream stream_id = new RewriteRuleSubtreeStream(this.adaptor, "rule id");
/* 1441 */     RewriteRuleSubtreeStream stream_optionValue = new RewriteRuleSubtreeStream(this.adaptor, "rule optionValue");
/*      */     try
/*      */     {
/* 1446 */       pushFollow(FOLLOW_id_in_option752);
/* 1447 */       id32 = id();
/*      */ 
/* 1449 */       this.state._fsp -= 1;
/*      */       option_return localoption_return1;
/* 1450 */       if (this.state.failed) { localoption_return1 = retval; return localoption_return1; }
/* 1451 */       if (this.state.backtracking == 0) stream_id.add(id32.getTree());
/* 1452 */       char_literal33 = (Token)match(this.input, 71, FOLLOW_71_in_option754); if (this.state.failed) { localoption_return1 = retval; return localoption_return1; }
/* 1453 */       if (this.state.backtracking == 0) stream_71.add(char_literal33);
/*      */ 
/* 1455 */       pushFollow(FOLLOW_optionValue_in_option756);
/* 1456 */       optionValue34 = optionValue();
/*      */ 
/* 1458 */       this.state._fsp -= 1;
/* 1459 */       if (this.state.failed) { localoption_return1 = retval; return localoption_return1; }
/* 1460 */       if (this.state.backtracking == 0) stream_optionValue.add(optionValue34.getTree());
/*      */ 
/* 1470 */       if (this.state.backtracking == 0) {
/* 1471 */         retval.tree = root_0;
/* 1472 */         RewriteRuleSubtreeStream stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.tree : null);
/*      */ 
/* 1474 */         root_0 = (CommonTree)this.adaptor.nil();
/*      */ 
/* 1479 */         CommonTree root_1 = (CommonTree)this.adaptor.nil();
/* 1480 */         root_1 = (CommonTree)this.adaptor.becomeRoot(stream_71.nextNode(), root_1);
/*      */ 
/* 1482 */         this.adaptor.addChild(root_1, stream_id.nextTree());
/* 1483 */         this.adaptor.addChild(root_1, stream_optionValue.nextTree());
/*      */ 
/* 1485 */         this.adaptor.addChild(root_0, root_1);
/*      */ 
/* 1490 */         retval.tree = root_0;
/*      */       }
/*      */ 
/* 1493 */       retval.stop = this.input.LT(-1);
/*      */ 
/* 1495 */       if (this.state.backtracking == 0)
/*      */       {
/* 1497 */         retval.tree = ((CommonTree)this.adaptor.rulePostProcessing(root_0));
/* 1498 */         this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
/*      */       }
/*      */     } catch (RecognitionException re) {
/* 1501 */       re = 
/* 1508 */         re;
/*      */ 
/* 1502 */       reportError(re);
/* 1503 */       recover(this.input, re);
/* 1504 */       retval.tree = ((CommonTree)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), re));
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/* 1509 */     return retval;
/*      */   }
/*      */ 
/*      */   public final optionValue_return optionValue()
/*      */     throws RecognitionException
/*      */   {
/* 1521 */     optionValue_return retval = new optionValue_return();
/* 1522 */     retval.start = this.input.LT(1);
/*      */ 
/* 1524 */     CommonTree root_0 = null;
/*      */ 
/* 1526 */     Token s = null;
/* 1527 */     Token STRING_LITERAL36 = null;
/* 1528 */     Token CHAR_LITERAL37 = null;
/* 1529 */     Token INT38 = null;
/* 1530 */     id_return id35 = null;
/*      */ 
/* 1533 */     CommonTree s_tree = null;
/* 1534 */     CommonTree STRING_LITERAL36_tree = null;
/* 1535 */     CommonTree CHAR_LITERAL37_tree = null;
/* 1536 */     CommonTree INT38_tree = null;
/* 1537 */     RewriteRuleTokenStream stream_74 = new RewriteRuleTokenStream(this.adaptor, "token 74");
/*      */     try
/*      */     {
/* 1541 */       int alt14 = 5;
/*      */       Object nvae;
/* 1542 */       switch (this.input.LA(1))
/*      */       {
/*      */       case 42:
/*      */       case 49:
/* 1546 */         alt14 = 1;
/*      */ 
/* 1548 */         break;
/*      */       case 43:
/* 1551 */         alt14 = 2;
/*      */ 
/* 1553 */         break;
/*      */       case 44:
/* 1556 */         alt14 = 3;
/*      */ 
/* 1558 */         break;
/*      */       case 47:
/* 1561 */         alt14 = 4;
/*      */ 
/* 1563 */         break;
/*      */       case 74:
/* 1566 */         alt14 = 5;
/*      */ 
/* 1568 */         break;
/*      */       default:
/* 1570 */         if (this.state.backtracking > 0) { this.state.failed = true; optionValue_return localoptionValue_return1 = retval; return localoptionValue_return1; }
/* 1571 */         nvae = new NoViableAltException("", 14, 0, this.input);
/*      */ 
/* 1574 */         throw ((Throwable)nvae);
/*      */       }
/*      */ 
/* 1577 */       switch (alt14)
/*      */       {
/*      */       case 1:
/* 1581 */         root_0 = (CommonTree)this.adaptor.nil();
/*      */ 
/* 1583 */         pushFollow(FOLLOW_id_in_optionValue785);
/* 1584 */         id35 = id();
/*      */ 
/* 1586 */         this.state._fsp -= 1;
/* 1587 */         if (this.state.failed) { nvae = retval; return nvae; }
/* 1588 */         if (this.state.backtracking == 0) this.adaptor.addChild(root_0, id35.getTree()); break;
/*      */       case 2:
/* 1595 */         root_0 = (CommonTree)this.adaptor.nil();
/*      */ 
/* 1597 */         STRING_LITERAL36 = (Token)match(this.input, 43, FOLLOW_STRING_LITERAL_in_optionValue795); if (this.state.failed) { nvae = retval; return nvae; }
/* 1598 */         if (this.state.backtracking == 0) {
/* 1599 */           STRING_LITERAL36_tree = (CommonTree)this.adaptor.create(STRING_LITERAL36);
/* 1600 */           this.adaptor.addChild(root_0, STRING_LITERAL36_tree); } break;
/*      */       case 3:
/* 1608 */         root_0 = (CommonTree)this.adaptor.nil();
/*      */ 
/* 1610 */         CHAR_LITERAL37 = (Token)match(this.input, 44, FOLLOW_CHAR_LITERAL_in_optionValue805); if (this.state.failed) { nvae = retval; return nvae; }
/* 1611 */         if (this.state.backtracking == 0) {
/* 1612 */           CHAR_LITERAL37_tree = (CommonTree)this.adaptor.create(CHAR_LITERAL37);
/* 1613 */           this.adaptor.addChild(root_0, CHAR_LITERAL37_tree); } break;
/*      */       case 4:
/* 1621 */         root_0 = (CommonTree)this.adaptor.nil();
/*      */ 
/* 1623 */         INT38 = (Token)match(this.input, 47, FOLLOW_INT_in_optionValue815); if (this.state.failed) { nvae = retval; return nvae; }
/* 1624 */         if (this.state.backtracking == 0) {
/* 1625 */           INT38_tree = (CommonTree)this.adaptor.create(INT38);
/* 1626 */           this.adaptor.addChild(root_0, INT38_tree); } break;
/*      */       case 5:
/* 1634 */         s = (Token)match(this.input, 74, FOLLOW_74_in_optionValue825); if (this.state.failed) { nvae = retval; return nvae; }
/* 1635 */         if (this.state.backtracking == 0) stream_74.add(s);
/*      */ 
/* 1646 */         if (this.state.backtracking == 0) {
/* 1647 */           retval.tree = root_0;
/* 1648 */           RewriteRuleSubtreeStream stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.tree : null);
/*      */ 
/* 1650 */           root_0 = (CommonTree)this.adaptor.nil();
/*      */ 
/* 1653 */           this.adaptor.addChild(root_0, (CommonTree)this.adaptor.create(43, s));
/*      */ 
/* 1657 */           retval.tree = root_0;
/*      */         }
/*      */         break;
/*      */       }
/*      */ 
/* 1662 */       retval.stop = this.input.LT(-1);
/*      */ 
/* 1664 */       if (this.state.backtracking == 0)
/*      */       {
/* 1666 */         retval.tree = ((CommonTree)this.adaptor.rulePostProcessing(root_0));
/* 1667 */         this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
/*      */       }
/*      */     } catch (RecognitionException re) {
/* 1670 */       re = 
/* 1677 */         re;
/*      */ 
/* 1671 */       reportError(re);
/* 1672 */       recover(this.input, re);
/* 1673 */       retval.tree = ((CommonTree)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), re));
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/* 1678 */     return retval;
/*      */   }
/*      */ 
/*      */   public final rule_return rule()
/*      */     throws RecognitionException
/*      */   {
/* 1695 */     this.rule_stack.push(new rule_scope());
/* 1696 */     rule_return retval = new rule_return();
/* 1697 */     retval.start = this.input.LT(1);
/*      */ 
/* 1699 */     CommonTree root_0 = null;
/*      */ 
/* 1701 */     Token modifier = null;
/* 1702 */     Token arg = null;
/* 1703 */     Token rt = null;
/* 1704 */     Token DOC_COMMENT39 = null;
/* 1705 */     Token string_literal40 = null;
/* 1706 */     Token string_literal41 = null;
/* 1707 */     Token string_literal42 = null;
/* 1708 */     Token string_literal43 = null;
/* 1709 */     Token char_literal45 = null;
/* 1710 */     Token string_literal46 = null;
/* 1711 */     Token char_literal51 = null;
/* 1712 */     Token char_literal53 = null;
/* 1713 */     id_return id44 = null;
/*      */ 
/* 1715 */     throwsSpec_return throwsSpec47 = null;
/*      */ 
/* 1717 */     optionsSpec_return optionsSpec48 = null;
/*      */ 
/* 1719 */     ruleScopeSpec_return ruleScopeSpec49 = null;
/*      */ 
/* 1721 */     ruleAction_return ruleAction50 = null;
/*      */ 
/* 1723 */     altList_return altList52 = null;
/*      */ 
/* 1725 */     exceptionGroup_return exceptionGroup54 = null;
/*      */ 
/* 1728 */     CommonTree modifier_tree = null;
/* 1729 */     CommonTree arg_tree = null;
/* 1730 */     CommonTree rt_tree = null;
/* 1731 */     CommonTree DOC_COMMENT39_tree = null;
/* 1732 */     CommonTree string_literal40_tree = null;
/* 1733 */     CommonTree string_literal41_tree = null;
/* 1734 */     CommonTree string_literal42_tree = null;
/* 1735 */     CommonTree string_literal43_tree = null;
/* 1736 */     CommonTree char_literal45_tree = null;
/* 1737 */     CommonTree string_literal46_tree = null;
/* 1738 */     CommonTree char_literal51_tree = null;
/* 1739 */     CommonTree char_literal53_tree = null;
/* 1740 */     RewriteRuleTokenStream stream_DOC_COMMENT = new RewriteRuleTokenStream(this.adaptor, "token DOC_COMMENT");
/* 1741 */     RewriteRuleTokenStream stream_79 = new RewriteRuleTokenStream(this.adaptor, "token 79");
/* 1742 */     RewriteRuleTokenStream stream_78 = new RewriteRuleTokenStream(this.adaptor, "token 78");
/* 1743 */     RewriteRuleTokenStream stream_69 = new RewriteRuleTokenStream(this.adaptor, "token 69");
/* 1744 */     RewriteRuleTokenStream stream_77 = new RewriteRuleTokenStream(this.adaptor, "token 77");
/* 1745 */     RewriteRuleTokenStream stream_BANG = new RewriteRuleTokenStream(this.adaptor, "token BANG");
/* 1746 */     RewriteRuleTokenStream stream_FRAGMENT = new RewriteRuleTokenStream(this.adaptor, "token FRAGMENT");
/* 1747 */     RewriteRuleTokenStream stream_75 = new RewriteRuleTokenStream(this.adaptor, "token 75");
/* 1748 */     RewriteRuleTokenStream stream_ARG_ACTION = new RewriteRuleTokenStream(this.adaptor, "token ARG_ACTION");
/* 1749 */     RewriteRuleTokenStream stream_76 = new RewriteRuleTokenStream(this.adaptor, "token 76");
/* 1750 */     RewriteRuleSubtreeStream stream_id = new RewriteRuleSubtreeStream(this.adaptor, "rule id");
/* 1751 */     RewriteRuleSubtreeStream stream_exceptionGroup = new RewriteRuleSubtreeStream(this.adaptor, "rule exceptionGroup");
/* 1752 */     RewriteRuleSubtreeStream stream_throwsSpec = new RewriteRuleSubtreeStream(this.adaptor, "rule throwsSpec");
/* 1753 */     RewriteRuleSubtreeStream stream_ruleScopeSpec = new RewriteRuleSubtreeStream(this.adaptor, "rule ruleScopeSpec");
/* 1754 */     RewriteRuleSubtreeStream stream_optionsSpec = new RewriteRuleSubtreeStream(this.adaptor, "rule optionsSpec");
/* 1755 */     RewriteRuleSubtreeStream stream_altList = new RewriteRuleSubtreeStream(this.adaptor, "rule altList");
/* 1756 */     RewriteRuleSubtreeStream stream_ruleAction = new RewriteRuleSubtreeStream(this.adaptor, "rule ruleAction");
/*      */     try
/*      */     {
/* 1762 */       int alt15 = 2;
/* 1763 */       switch (this.input.LA(1))
/*      */       {
/*      */       case 4:
/* 1766 */         alt15 = 1;
/*      */       }
/*      */ 
/* 1771 */       switch (alt15)
/*      */       {
/*      */       case 1:
/* 1775 */         DOC_COMMENT39 = (Token)match(this.input, 4, FOLLOW_DOC_COMMENT_in_rule854); if (this.state.failed) return retval;
/* 1776 */         if (this.state.backtracking == 0) stream_DOC_COMMENT.add(DOC_COMMENT39);
/*      */ 
/*      */         break;
/*      */       }
/*      */ 
/* 1785 */       int alt17 = 2;
/* 1786 */       switch (this.input.LA(1))
/*      */       {
/*      */       case 36:
/*      */       case 75:
/*      */       case 76:
/*      */       case 77:
/* 1792 */         alt17 = 1;
/*      */       }
/*      */       int alt16;
/*      */       Object nvae;
/* 1797 */       switch (alt17)
/*      */       {
/*      */       case 1:
/* 1802 */         alt16 = 4;
/* 1803 */         switch (this.input.LA(1))
/*      */         {
/*      */         case 75:
/* 1806 */           alt16 = 1;
/*      */ 
/* 1808 */           break;
/*      */         case 76:
/* 1811 */           alt16 = 2;
/*      */ 
/* 1813 */           break;
/*      */         case 77:
/* 1816 */           alt16 = 3;
/*      */ 
/* 1818 */           break;
/*      */         case 36:
/* 1821 */           alt16 = 4;
/*      */ 
/* 1823 */           break;
/*      */         default:
/* 1825 */           if (this.state.backtracking > 0) { this.state.failed = true; return retval; }
/* 1826 */           nvae = new NoViableAltException("", 16, 0, this.input);
/*      */ 
/* 1829 */           throw ((Throwable)nvae);
/*      */         }
/*      */ 
/* 1832 */         switch (alt16)
/*      */         {
/*      */         case 1:
/* 1836 */           string_literal40 = (Token)match(this.input, 75, FOLLOW_75_in_rule864); if (this.state.failed) return retval;
/* 1837 */           if (this.state.backtracking == 0) stream_75.add(string_literal40); break;
/*      */         case 2:
/* 1845 */           string_literal41 = (Token)match(this.input, 76, FOLLOW_76_in_rule866); if (this.state.failed) return retval;
/* 1846 */           if (this.state.backtracking == 0) stream_76.add(string_literal41); break;
/*      */         case 3:
/* 1854 */           string_literal42 = (Token)match(this.input, 77, FOLLOW_77_in_rule868); if (this.state.failed) return retval;
/* 1855 */           if (this.state.backtracking == 0) stream_77.add(string_literal42); break;
/*      */         case 4:
/* 1863 */           string_literal43 = (Token)match(this.input, 36, FOLLOW_FRAGMENT_in_rule870); if (this.state.failed) return retval;
/* 1864 */           if (this.state.backtracking == 0) stream_FRAGMENT.add(string_literal43);
/*      */ 
/*      */           break;
/*      */         }
/*      */ 
/*      */         break;
/*      */       }
/*      */ 
/* 1878 */       pushFollow(FOLLOW_id_in_rule878);
/* 1879 */       id44 = id();
/*      */ 
/* 1881 */       this.state._fsp -= 1;
/* 1882 */       if (this.state.failed) return retval;
/* 1883 */       if (this.state.backtracking == 0) stream_id.add(id44.getTree());
/* 1884 */       if (this.state.backtracking == 0) {
/* 1885 */         ((rule_scope)this.rule_stack.peek()).name = (id44 != null ? this.input.toString(id44.start, id44.stop) : null);
/*      */       }
/*      */ 
/* 1888 */       int alt18 = 2;
/* 1889 */       switch (this.input.LA(1))
/*      */       {
/*      */       case 39:
/* 1892 */         alt18 = 1;
/*      */       }
/*      */ 
/* 1897 */       switch (alt18)
/*      */       {
/*      */       case 1:
/* 1901 */         char_literal45 = (Token)match(this.input, 39, FOLLOW_BANG_in_rule884); if (this.state.failed) return retval;
/* 1902 */         if (this.state.backtracking == 0) stream_BANG.add(char_literal45);
/*      */ 
/*      */         break;
/*      */       }
/*      */ 
/* 1911 */       int alt19 = 2;
/* 1912 */       switch (this.input.LA(1))
/*      */       {
/*      */       case 48:
/* 1915 */         alt19 = 1;
/*      */       }
/*      */ 
/* 1920 */       switch (alt19)
/*      */       {
/*      */       case 1:
/* 1924 */         arg = (Token)match(this.input, 48, FOLLOW_ARG_ACTION_in_rule893); if (this.state.failed) return retval;
/* 1925 */         if (this.state.backtracking == 0) stream_ARG_ACTION.add(arg);
/*      */ 
/*      */         break;
/*      */       }
/*      */ 
/* 1934 */       int alt20 = 2;
/* 1935 */       switch (this.input.LA(1))
/*      */       {
/*      */       case 78:
/* 1938 */         alt20 = 1;
/*      */       }
/*      */ 
/* 1943 */       switch (alt20)
/*      */       {
/*      */       case 1:
/* 1947 */         string_literal46 = (Token)match(this.input, 78, FOLLOW_78_in_rule902);
/*      */         rule_return localrule_return4;
/* 1947 */         if (this.state.failed) return retval;
/* 1948 */         if (this.state.backtracking == 0) stream_78.add(string_literal46);
/*      */ 
/* 1950 */         rt = (Token)match(this.input, 48, FOLLOW_ARG_ACTION_in_rule906); if (this.state.failed) return retval;
/* 1951 */         if (this.state.backtracking == 0) stream_ARG_ACTION.add(rt);
/*      */ 
/*      */         break;
/*      */       }
/*      */ 
/* 1960 */       int alt21 = 2;
/* 1961 */       switch (this.input.LA(1))
/*      */       {
/*      */       case 80:
/* 1964 */         alt21 = 1;
/*      */       }
/*      */ 
/* 1969 */       switch (alt21)
/*      */       {
/*      */       case 1:
/* 1973 */         pushFollow(FOLLOW_throwsSpec_in_rule914);
/* 1974 */         throwsSpec47 = throwsSpec();
/*      */ 
/* 1976 */         this.state._fsp -= 1;
/* 1977 */         if (this.state.failed) return retval;
/* 1978 */         if (this.state.backtracking == 0) stream_throwsSpec.add(throwsSpec47.getTree());
/*      */ 
/*      */         break;
/*      */       }
/*      */ 
/* 1986 */       int alt22 = 2;
/* 1987 */       switch (this.input.LA(1))
/*      */       {
/*      */       case 46:
/* 1990 */         alt22 = 1;
/*      */       }
/*      */ 
/* 1995 */       switch (alt22)
/*      */       {
/*      */       case 1:
/* 1999 */         pushFollow(FOLLOW_optionsSpec_in_rule917);
/* 2000 */         optionsSpec48 = optionsSpec();
/*      */ 
/* 2002 */         this.state._fsp -= 1;
/* 2003 */         if (this.state.failed) return retval;
/* 2004 */         if (this.state.backtracking == 0) stream_optionsSpec.add(optionsSpec48.getTree());
/*      */ 
/*      */         break;
/*      */       }
/*      */ 
/* 2012 */       int alt23 = 2;
/* 2013 */       switch (this.input.LA(1))
/*      */       {
/*      */       case 31:
/* 2016 */         alt23 = 1;
/*      */       }
/*      */ 
/* 2021 */       switch (alt23)
/*      */       {
/*      */       case 1:
/* 2025 */         pushFollow(FOLLOW_ruleScopeSpec_in_rule920);
/* 2026 */         ruleScopeSpec49 = ruleScopeSpec();
/*      */ 
/* 2028 */         this.state._fsp -= 1;
/* 2029 */         if (this.state.failed) return retval;
/* 2030 */         if (this.state.backtracking == 0) stream_ruleScopeSpec.add(ruleScopeSpec49.getTree());
/*      */ 
/*      */         break;
/*      */       }
/*      */ 
/*      */       int alt24;
/*      */       rule_return localrule_return9;
/*      */       while (true)
/*      */       {
/* 2040 */         alt24 = 2;
/* 2041 */         switch (this.input.LA(1))
/*      */         {
/*      */         case 72:
/* 2044 */           alt24 = 1;
/*      */         }
/*      */ 
/* 2050 */         switch (alt24)
/*      */         {
/*      */         case 1:
/* 2054 */           pushFollow(FOLLOW_ruleAction_in_rule923);
/* 2055 */           ruleAction50 = ruleAction();
/*      */ 
/* 2057 */           this.state._fsp -= 1;
/* 2058 */           if (this.state.failed) return retval;
/* 2059 */           if (this.state.backtracking == 0) stream_ruleAction.add(ruleAction50.getTree()); break;
/*      */         default:
/* 2065 */           break label2015;
/*      */         }
/*      */       }
/*      */ 
/* 2069 */       label2015: char_literal51 = (Token)match(this.input, 79, FOLLOW_79_in_rule928);
/*      */       rule_return localrule_return8;
/* 2069 */       if (this.state.failed) return retval;
/* 2070 */       if (this.state.backtracking == 0) stream_79.add(char_literal51);
/*      */ 
/* 2072 */       pushFollow(FOLLOW_altList_in_rule930);
/* 2073 */       altList52 = altList();
/*      */ 
/* 2075 */       this.state._fsp -= 1;
/* 2076 */       if (this.state.failed) return retval;
/* 2077 */       if (this.state.backtracking == 0) stream_altList.add(altList52.getTree());
/* 2078 */       char_literal53 = (Token)match(this.input, 69, FOLLOW_69_in_rule932); if (this.state.failed) return retval;
/* 2079 */       if (this.state.backtracking == 0) stream_69.add(char_literal53);
/*      */ 
/* 2082 */       int alt25 = 2;
/* 2083 */       switch (this.input.LA(1))
/*      */       {
/*      */       case 85:
/*      */       case 86:
/* 2087 */         alt25 = 1;
/*      */       }
/*      */ 
/* 2092 */       switch (alt25)
/*      */       {
/*      */       case 1:
/* 2096 */         pushFollow(FOLLOW_exceptionGroup_in_rule936);
/* 2097 */         exceptionGroup54 = exceptionGroup();
/*      */ 
/* 2099 */         this.state._fsp -= 1;
/* 2100 */         if (this.state.failed) return retval;
/* 2101 */         if (this.state.backtracking == 0) stream_exceptionGroup.add(exceptionGroup54.getTree());
/*      */ 
/*      */         break;
/*      */       }
/*      */ 
/* 2117 */       if (this.state.backtracking == 0) {
/* 2118 */         retval.tree = root_0;
/* 2119 */         RewriteRuleTokenStream stream_arg = new RewriteRuleTokenStream(this.adaptor, "token arg", arg);
/* 2120 */         RewriteRuleTokenStream stream_rt = new RewriteRuleTokenStream(this.adaptor, "token rt", rt);
/* 2121 */         RewriteRuleSubtreeStream stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.tree : null);
/*      */ 
/* 2123 */         root_0 = (CommonTree)this.adaptor.nil();
/*      */ 
/* 2128 */         CommonTree root_1 = (CommonTree)this.adaptor.nil();
/* 2129 */         root_1 = (CommonTree)this.adaptor.becomeRoot((CommonTree)this.adaptor.create(7, "RULE"), root_1);
/*      */ 
/* 2131 */         this.adaptor.addChild(root_1, stream_id.nextTree());
/* 2132 */         this.adaptor.addChild(root_1, modifier != null ? this.adaptor.create(modifier) : null);
/*      */ 
/* 2134 */         if (stream_arg.hasNext())
/*      */         {
/* 2137 */           CommonTree root_2 = (CommonTree)this.adaptor.nil();
/* 2138 */           root_2 = (CommonTree)this.adaptor.becomeRoot((CommonTree)this.adaptor.create(21, "ARG"), root_2);
/*      */ 
/* 2140 */           this.adaptor.addChild(root_2, stream_arg.nextNode());
/*      */ 
/* 2142 */           this.adaptor.addChild(root_1, root_2);
/*      */         }
/*      */ 
/* 2146 */         stream_arg.reset();
/*      */ 
/* 2148 */         if (stream_rt.hasNext())
/*      */         {
/* 2151 */           CommonTree root_2 = (CommonTree)this.adaptor.nil();
/* 2152 */           root_2 = (CommonTree)this.adaptor.becomeRoot((CommonTree)this.adaptor.create(23, "RET"), root_2);
/*      */ 
/* 2154 */           this.adaptor.addChild(root_2, stream_rt.nextNode());
/*      */ 
/* 2156 */           this.adaptor.addChild(root_1, root_2);
/*      */         }
/*      */ 
/* 2160 */         stream_rt.reset();
/*      */ 
/* 2162 */         if (stream_optionsSpec.hasNext()) {
/* 2163 */           this.adaptor.addChild(root_1, stream_optionsSpec.nextTree());
/*      */         }
/*      */ 
/* 2166 */         stream_optionsSpec.reset();
/*      */ 
/* 2168 */         if (stream_ruleScopeSpec.hasNext()) {
/* 2169 */           this.adaptor.addChild(root_1, stream_ruleScopeSpec.nextTree());
/*      */         }
/*      */ 
/* 2172 */         stream_ruleScopeSpec.reset();
/*      */ 
/* 2174 */         while (stream_ruleAction.hasNext()) {
/* 2175 */           this.adaptor.addChild(root_1, stream_ruleAction.nextTree());
/*      */         }
/*      */ 
/* 2178 */         stream_ruleAction.reset();
/* 2179 */         this.adaptor.addChild(root_1, stream_altList.nextTree());
/*      */ 
/* 2181 */         if (stream_exceptionGroup.hasNext()) {
/* 2182 */           this.adaptor.addChild(root_1, stream_exceptionGroup.nextTree());
/*      */         }
/*      */ 
/* 2185 */         stream_exceptionGroup.reset();
/* 2186 */         this.adaptor.addChild(root_1, (CommonTree)this.adaptor.create(17, "EOR"));
/*      */ 
/* 2188 */         this.adaptor.addChild(root_0, root_1);
/*      */ 
/* 2193 */         retval.tree = root_0;
/*      */       }
/*      */ 
/* 2196 */       retval.stop = this.input.LT(-1);
/*      */ 
/* 2198 */       if (this.state.backtracking == 0)
/*      */       {
/* 2200 */         retval.tree = ((CommonTree)this.adaptor.rulePostProcessing(root_0));
/* 2201 */         this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
/*      */       }
/* 2203 */       if (this.state.backtracking == 0)
/*      */       {
/* 2205 */         this.rules.add(((rule_scope)this.rule_stack.peek()).name);
/*      */       }
/*      */     }
/*      */     catch (RecognitionException re)
/*      */     {
/* 2210 */       reportError(re);
/* 2211 */       recover(this.input, re);
/* 2212 */       retval.tree = ((CommonTree)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), re));
/*      */     }
/*      */     finally
/*      */     {
/* 2216 */       this.rule_stack.pop();
/*      */     }
/* 2218 */     return retval;
/*      */   }
/*      */ 
/*      */   public final ruleAction_return ruleAction()
/*      */     throws RecognitionException
/*      */   {
/* 2230 */     ruleAction_return retval = new ruleAction_return();
/* 2231 */     retval.start = this.input.LT(1);
/*      */ 
/* 2233 */     CommonTree root_0 = null;
/*      */ 
/* 2235 */     Token char_literal55 = null;
/* 2236 */     Token ACTION57 = null;
/* 2237 */     id_return id56 = null;
/*      */ 
/* 2240 */     CommonTree char_literal55_tree = null;
/* 2241 */     CommonTree ACTION57_tree = null;
/* 2242 */     RewriteRuleTokenStream stream_72 = new RewriteRuleTokenStream(this.adaptor, "token 72");
/* 2243 */     RewriteRuleTokenStream stream_ACTION = new RewriteRuleTokenStream(this.adaptor, "token ACTION");
/* 2244 */     RewriteRuleSubtreeStream stream_id = new RewriteRuleSubtreeStream(this.adaptor, "rule id");
/*      */     try
/*      */     {
/* 2249 */       char_literal55 = (Token)match(this.input, 72, FOLLOW_72_in_ruleAction1038);
/*      */       ruleAction_return localruleAction_return1;
/* 2249 */       if (this.state.failed) { localruleAction_return1 = retval; return localruleAction_return1; }
/* 2250 */       if (this.state.backtracking == 0) stream_72.add(char_literal55);
/*      */ 
/* 2252 */       pushFollow(FOLLOW_id_in_ruleAction1040);
/* 2253 */       id56 = id();
/*      */ 
/* 2255 */       this.state._fsp -= 1;
/* 2256 */       if (this.state.failed) { localruleAction_return1 = retval; return localruleAction_return1; }
/* 2257 */       if (this.state.backtracking == 0) stream_id.add(id56.getTree());
/* 2258 */       ACTION57 = (Token)match(this.input, 45, FOLLOW_ACTION_in_ruleAction1042); if (this.state.failed) { localruleAction_return1 = retval; return localruleAction_return1; }
/* 2259 */       if (this.state.backtracking == 0) stream_ACTION.add(ACTION57);
/*      */ 
/* 2270 */       if (this.state.backtracking == 0) {
/* 2271 */         retval.tree = root_0;
/* 2272 */         RewriteRuleSubtreeStream stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.tree : null);
/*      */ 
/* 2274 */         root_0 = (CommonTree)this.adaptor.nil();
/*      */ 
/* 2279 */         CommonTree root_1 = (CommonTree)this.adaptor.nil();
/* 2280 */         root_1 = (CommonTree)this.adaptor.becomeRoot(stream_72.nextNode(), root_1);
/*      */ 
/* 2282 */         this.adaptor.addChild(root_1, stream_id.nextTree());
/* 2283 */         this.adaptor.addChild(root_1, stream_ACTION.nextNode());
/*      */ 
/* 2285 */         this.adaptor.addChild(root_0, root_1);
/*      */ 
/* 2290 */         retval.tree = root_0;
/*      */       }
/*      */ 
/* 2293 */       retval.stop = this.input.LT(-1);
/*      */ 
/* 2295 */       if (this.state.backtracking == 0)
/*      */       {
/* 2297 */         retval.tree = ((CommonTree)this.adaptor.rulePostProcessing(root_0));
/* 2298 */         this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
/*      */       }
/*      */     } catch (RecognitionException re) {
/* 2301 */       re = 
/* 2308 */         re;
/*      */ 
/* 2302 */       reportError(re);
/* 2303 */       recover(this.input, re);
/* 2304 */       retval.tree = ((CommonTree)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), re));
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/* 2309 */     return retval;
/*      */   }
/*      */ 
/*      */   public final throwsSpec_return throwsSpec()
/*      */     throws RecognitionException
/*      */   {
/* 2321 */     throwsSpec_return retval = new throwsSpec_return();
/* 2322 */     retval.start = this.input.LT(1);
/*      */ 
/* 2324 */     CommonTree root_0 = null;
/*      */ 
/* 2326 */     Token string_literal58 = null;
/* 2327 */     Token char_literal60 = null;
/* 2328 */     id_return id59 = null;
/*      */ 
/* 2330 */     id_return id61 = null;
/*      */ 
/* 2333 */     CommonTree string_literal58_tree = null;
/* 2334 */     CommonTree char_literal60_tree = null;
/* 2335 */     RewriteRuleTokenStream stream_80 = new RewriteRuleTokenStream(this.adaptor, "token 80");
/* 2336 */     RewriteRuleTokenStream stream_81 = new RewriteRuleTokenStream(this.adaptor, "token 81");
/* 2337 */     RewriteRuleSubtreeStream stream_id = new RewriteRuleSubtreeStream(this.adaptor, "rule id");
/*      */     try
/*      */     {
/* 2342 */       string_literal58 = (Token)match(this.input, 80, FOLLOW_80_in_throwsSpec1063);
/*      */       throwsSpec_return localthrowsSpec_return1;
/* 2342 */       if (this.state.failed) { localthrowsSpec_return1 = retval; return localthrowsSpec_return1; }
/* 2343 */       if (this.state.backtracking == 0) stream_80.add(string_literal58);
/*      */ 
/* 2345 */       pushFollow(FOLLOW_id_in_throwsSpec1065);
/* 2346 */       id59 = id();
/*      */ 
/* 2348 */       this.state._fsp -= 1;
/* 2349 */       if (this.state.failed) { localthrowsSpec_return1 = retval; return localthrowsSpec_return1; }
/* 2350 */       if (this.state.backtracking == 0) stream_id.add(id59.getTree());
/*      */ 
/*      */       while (true)
/*      */       {
/* 2354 */         int alt26 = 2;
/* 2355 */         switch (this.input.LA(1))
/*      */         {
/*      */         case 81:
/* 2358 */           alt26 = 1;
/*      */         }
/*      */ 
/* 2364 */         switch (alt26)
/*      */         {
/*      */         case 1:
/* 2368 */           char_literal60 = (Token)match(this.input, 81, FOLLOW_81_in_throwsSpec1069);
/*      */           throwsSpec_return localthrowsSpec_return2;
/* 2368 */           if (this.state.failed) { localthrowsSpec_return2 = retval; return localthrowsSpec_return2; }
/* 2369 */           if (this.state.backtracking == 0) stream_81.add(char_literal60);
/*      */ 
/* 2371 */           pushFollow(FOLLOW_id_in_throwsSpec1071);
/* 2372 */           id61 = id();
/*      */ 
/* 2374 */           this.state._fsp -= 1;
/* 2375 */           if (this.state.failed) { localthrowsSpec_return2 = retval; return localthrowsSpec_return2; }
/* 2376 */           if (this.state.backtracking == 0) stream_id.add(id61.getTree()); break;
/*      */         default:
/* 2382 */           break label378;
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/* 2395 */       label378: if (this.state.backtracking == 0) {
/* 2396 */         retval.tree = root_0;
/* 2397 */         RewriteRuleSubtreeStream stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.tree : null);
/*      */ 
/* 2399 */         root_0 = (CommonTree)this.adaptor.nil();
/*      */ 
/* 2404 */         CommonTree root_1 = (CommonTree)this.adaptor.nil();
/* 2405 */         root_1 = (CommonTree)this.adaptor.becomeRoot(stream_80.nextNode(), root_1);
/*      */ 
/* 2407 */         if (!stream_id.hasNext()) {
/* 2408 */           throw new RewriteEarlyExitException();
/*      */         }
/* 2410 */         while (stream_id.hasNext()) {
/* 2411 */           this.adaptor.addChild(root_1, stream_id.nextTree());
/*      */         }
/*      */ 
/* 2414 */         stream_id.reset();
/*      */ 
/* 2416 */         this.adaptor.addChild(root_0, root_1);
/*      */ 
/* 2421 */         retval.tree = root_0;
/*      */       }
/*      */ 
/* 2424 */       retval.stop = this.input.LT(-1);
/*      */ 
/* 2426 */       if (this.state.backtracking == 0)
/*      */       {
/* 2428 */         retval.tree = ((CommonTree)this.adaptor.rulePostProcessing(root_0));
/* 2429 */         this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
/*      */       }
/*      */     } catch (RecognitionException re) {
/* 2432 */       re = 
/* 2439 */         re;
/*      */ 
/* 2433 */       reportError(re);
/* 2434 */       recover(this.input, re);
/* 2435 */       retval.tree = ((CommonTree)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), re));
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/* 2440 */     return retval;
/*      */   }
/*      */ 
/*      */   public final ruleScopeSpec_return ruleScopeSpec()
/*      */     throws RecognitionException
/*      */   {
/* 2452 */     ruleScopeSpec_return retval = new ruleScopeSpec_return();
/* 2453 */     retval.start = this.input.LT(1);
/*      */ 
/* 2455 */     CommonTree root_0 = null;
/*      */ 
/* 2457 */     Token string_literal62 = null;
/* 2458 */     Token ACTION63 = null;
/* 2459 */     Token string_literal64 = null;
/* 2460 */     Token char_literal66 = null;
/* 2461 */     Token char_literal68 = null;
/* 2462 */     Token string_literal69 = null;
/* 2463 */     Token ACTION70 = null;
/* 2464 */     Token string_literal71 = null;
/* 2465 */     Token char_literal73 = null;
/* 2466 */     Token char_literal75 = null;
/* 2467 */     id_return id65 = null;
/*      */ 
/* 2469 */     id_return id67 = null;
/*      */ 
/* 2471 */     id_return id72 = null;
/*      */ 
/* 2473 */     id_return id74 = null;
/*      */ 
/* 2476 */     CommonTree string_literal62_tree = null;
/* 2477 */     CommonTree ACTION63_tree = null;
/* 2478 */     CommonTree string_literal64_tree = null;
/* 2479 */     CommonTree char_literal66_tree = null;
/* 2480 */     CommonTree char_literal68_tree = null;
/* 2481 */     CommonTree string_literal69_tree = null;
/* 2482 */     CommonTree ACTION70_tree = null;
/* 2483 */     CommonTree string_literal71_tree = null;
/* 2484 */     CommonTree char_literal73_tree = null;
/* 2485 */     CommonTree char_literal75_tree = null;
/* 2486 */     RewriteRuleTokenStream stream_69 = new RewriteRuleTokenStream(this.adaptor, "token 69");
/* 2487 */     RewriteRuleTokenStream stream_SCOPE = new RewriteRuleTokenStream(this.adaptor, "token SCOPE");
/* 2488 */     RewriteRuleTokenStream stream_81 = new RewriteRuleTokenStream(this.adaptor, "token 81");
/* 2489 */     RewriteRuleTokenStream stream_ACTION = new RewriteRuleTokenStream(this.adaptor, "token ACTION");
/* 2490 */     RewriteRuleSubtreeStream stream_id = new RewriteRuleSubtreeStream(this.adaptor, "rule id");
/*      */     try
/*      */     {
/* 2493 */       int alt29 = 3;
/*      */       Object nvae;
/*      */       Object nvae;
/* 2494 */       switch (this.input.LA(1))
/*      */       {
/*      */       case 31:
/*      */         Object nvae;
/* 2497 */         switch (this.input.LA(2))
/*      */         {
/*      */         case 45:
/* 2500 */           switch (this.input.LA(3))
/*      */           {
/*      */           case 31:
/* 2503 */             alt29 = 3;
/*      */ 
/* 2505 */             break;
/*      */           case 72:
/*      */           case 79:
/* 2509 */             alt29 = 1;
/*      */ 
/* 2511 */             break;
/*      */           default:
/* 2513 */             if (this.state.backtracking > 0) { this.state.failed = true; ruleScopeSpec_return localruleScopeSpec_return1 = retval; return localruleScopeSpec_return1; }
/* 2514 */             nvae = new NoViableAltException("", 29, 2, this.input);
/*      */ 
/* 2517 */             throw ((Throwable)nvae);
/*      */           }
/*      */ 
/* 2521 */           break;
/*      */         case 42:
/*      */         case 49:
/* 2525 */           alt29 = 2;
/*      */ 
/* 2527 */           break;
/*      */         default:
/* 2529 */           if (this.state.backtracking > 0) { this.state.failed = true; nvae = retval; return nvae; }
/* 2530 */           nvae = new NoViableAltException("", 29, 1, this.input);
/*      */ 
/* 2533 */           throw ((Throwable)nvae);
/*      */         }
/*      */ 
/* 2537 */         break;
/*      */       default:
/* 2539 */         if (this.state.backtracking > 0) { this.state.failed = true; nvae = retval; return nvae; }
/* 2540 */         nvae = new NoViableAltException("", 29, 0, this.input);
/*      */ 
/* 2543 */         throw ((Throwable)nvae);
/*      */       }
/*      */       Object stream_retval;
/*      */       CommonTree root_1;
/*      */       label998: Object stream_retval;
/*      */       CommonTree root_1;
/* 2546 */       switch (alt29)
/*      */       {
/*      */       case 1:
/* 2550 */         string_literal62 = (Token)match(this.input, 31, FOLLOW_SCOPE_in_ruleScopeSpec1094); if (this.state.failed) { nvae = retval; return nvae; }
/* 2551 */         if (this.state.backtracking == 0) stream_SCOPE.add(string_literal62);
/*      */ 
/* 2553 */         ACTION63 = (Token)match(this.input, 45, FOLLOW_ACTION_in_ruleScopeSpec1096); if (this.state.failed) { nvae = retval; return nvae; }
/* 2554 */         if (this.state.backtracking == 0) stream_ACTION.add(ACTION63);
/*      */ 
/* 2565 */         if (this.state.backtracking == 0) {
/* 2566 */           retval.tree = root_0;
/* 2567 */           stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.tree : null);
/*      */ 
/* 2569 */           root_0 = (CommonTree)this.adaptor.nil();
/*      */ 
/* 2574 */           root_1 = (CommonTree)this.adaptor.nil();
/* 2575 */           root_1 = (CommonTree)this.adaptor.becomeRoot(stream_SCOPE.nextNode(), root_1);
/*      */ 
/* 2577 */           this.adaptor.addChild(root_1, stream_ACTION.nextNode());
/*      */ 
/* 2579 */           this.adaptor.addChild(root_0, root_1);
/*      */ 
/* 2584 */           retval.tree = root_0; } break;
/*      */       case 2:
/* 2590 */         string_literal64 = (Token)match(this.input, 31, FOLLOW_SCOPE_in_ruleScopeSpec1109); if (this.state.failed) { stream_retval = retval; return stream_retval; }
/* 2591 */         if (this.state.backtracking == 0) stream_SCOPE.add(string_literal64);
/*      */ 
/* 2593 */         pushFollow(FOLLOW_id_in_ruleScopeSpec1111);
/* 2594 */         id65 = id();
/*      */ 
/* 2596 */         this.state._fsp -= 1;
/* 2597 */         if (this.state.failed) { stream_retval = retval; return stream_retval; }
/* 2598 */         if (this.state.backtracking == 0) stream_id.add(id65.getTree());
/*      */         int alt27;
/*      */         while (true)
/*      */         {
/* 2602 */           alt27 = 2;
/* 2603 */           switch (this.input.LA(1))
/*      */           {
/*      */           case 81:
/* 2606 */             alt27 = 1;
/*      */           }
/*      */ 
/* 2612 */           switch (alt27)
/*      */           {
/*      */           case 1:
/* 2616 */             char_literal66 = (Token)match(this.input, 81, FOLLOW_81_in_ruleScopeSpec1114); if (this.state.failed) { root_1 = retval; return root_1; }
/* 2617 */             if (this.state.backtracking == 0) stream_81.add(char_literal66);
/*      */ 
/* 2619 */             pushFollow(FOLLOW_id_in_ruleScopeSpec1116);
/* 2620 */             id67 = id();
/*      */ 
/* 2622 */             this.state._fsp -= 1;
/* 2623 */             if (this.state.failed) { root_1 = retval; return root_1; }
/* 2624 */             if (this.state.backtracking == 0) stream_id.add(id67.getTree()); break;
/*      */           default:
/* 2630 */             break label998;
/*      */           }
/*      */         }
/*      */ 
/* 2634 */         char_literal68 = (Token)match(this.input, 69, FOLLOW_69_in_ruleScopeSpec1120); if (this.state.failed) { ruleScopeSpec_return localruleScopeSpec_return2 = retval; return localruleScopeSpec_return2; }
/* 2635 */         if (this.state.backtracking == 0) stream_69.add(char_literal68);
/*      */ 
/* 2646 */         if (this.state.backtracking == 0) {
/* 2647 */           retval.tree = root_0;
/* 2648 */           stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.tree : null);
/*      */ 
/* 2650 */           root_0 = (CommonTree)this.adaptor.nil();
/*      */ 
/* 2655 */           root_1 = (CommonTree)this.adaptor.nil();
/* 2656 */           root_1 = (CommonTree)this.adaptor.becomeRoot(stream_SCOPE.nextNode(), root_1);
/*      */ 
/* 2658 */           if (!stream_id.hasNext()) {
/* 2659 */             throw new RewriteEarlyExitException();
/*      */           }
/* 2661 */           while (stream_id.hasNext()) {
/* 2662 */             this.adaptor.addChild(root_1, stream_id.nextTree());
/*      */           }
/*      */ 
/* 2665 */           stream_id.reset();
/*      */ 
/* 2667 */           this.adaptor.addChild(root_0, root_1);
/*      */ 
/* 2672 */           retval.tree = root_0; } break;
/*      */       case 3:
/* 2678 */         string_literal69 = (Token)match(this.input, 31, FOLLOW_SCOPE_in_ruleScopeSpec1134); if (this.state.failed) { stream_retval = retval; return stream_retval; }
/* 2679 */         if (this.state.backtracking == 0) stream_SCOPE.add(string_literal69);
/*      */ 
/* 2681 */         ACTION70 = (Token)match(this.input, 45, FOLLOW_ACTION_in_ruleScopeSpec1136); if (this.state.failed) { stream_retval = retval; return stream_retval; }
/* 2682 */         if (this.state.backtracking == 0) stream_ACTION.add(ACTION70);
/*      */ 
/* 2684 */         string_literal71 = (Token)match(this.input, 31, FOLLOW_SCOPE_in_ruleScopeSpec1140); if (this.state.failed) { stream_retval = retval; return stream_retval; }
/* 2685 */         if (this.state.backtracking == 0) stream_SCOPE.add(string_literal71);
/*      */ 
/* 2687 */         pushFollow(FOLLOW_id_in_ruleScopeSpec1142);
/* 2688 */         id72 = id();
/*      */ 
/* 2690 */         this.state._fsp -= 1;
/* 2691 */         if (this.state.failed) { stream_retval = retval; return stream_retval; }
/* 2692 */         if (this.state.backtracking == 0) stream_id.add(id72.getTree());
/*      */         int alt28;
/*      */         while (true)
/*      */         {
/* 2696 */           alt28 = 2;
/* 2697 */           switch (this.input.LA(1))
/*      */           {
/*      */           case 81:
/* 2700 */             alt28 = 1;
/*      */           }
/*      */ 
/* 2706 */           switch (alt28)
/*      */           {
/*      */           case 1:
/* 2710 */             char_literal73 = (Token)match(this.input, 81, FOLLOW_81_in_ruleScopeSpec1145); if (this.state.failed) { root_1 = retval; return root_1; }
/* 2711 */             if (this.state.backtracking == 0) stream_81.add(char_literal73);
/*      */ 
/* 2713 */             pushFollow(FOLLOW_id_in_ruleScopeSpec1147);
/* 2714 */             id74 = id();
/*      */ 
/* 2716 */             this.state._fsp -= 1;
/* 2717 */             if (this.state.failed) { root_1 = retval; return root_1; }
/* 2718 */             if (this.state.backtracking == 0) stream_id.add(id74.getTree()); break;
/*      */           default:
/* 2724 */             break label1598;
/*      */           }
/*      */         }
/*      */ 
/* 2728 */         label1598: char_literal75 = (Token)match(this.input, 69, FOLLOW_69_in_ruleScopeSpec1151); if (this.state.failed) { ruleScopeSpec_return localruleScopeSpec_return3 = retval; return localruleScopeSpec_return3; }
/* 2729 */         if (this.state.backtracking == 0) stream_69.add(char_literal75);
/*      */ 
/* 2740 */         if (this.state.backtracking == 0) {
/* 2741 */           retval.tree = root_0;
/* 2742 */           RewriteRuleSubtreeStream stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.tree : null);
/*      */ 
/* 2744 */           root_0 = (CommonTree)this.adaptor.nil();
/*      */ 
/* 2749 */           CommonTree root_1 = (CommonTree)this.adaptor.nil();
/* 2750 */           root_1 = (CommonTree)this.adaptor.becomeRoot(stream_SCOPE.nextNode(), root_1);
/*      */ 
/* 2752 */           this.adaptor.addChild(root_1, stream_ACTION.nextNode());
/* 2753 */           if (!stream_id.hasNext()) {
/* 2754 */             throw new RewriteEarlyExitException();
/*      */           }
/* 2756 */           while (stream_id.hasNext()) {
/* 2757 */             this.adaptor.addChild(root_1, stream_id.nextTree());
/*      */           }
/*      */ 
/* 2760 */           stream_id.reset();
/*      */ 
/* 2762 */           this.adaptor.addChild(root_0, root_1);
/*      */ 
/* 2767 */           retval.tree = root_0;
/*      */         }
/*      */         break;
/*      */       }
/*      */ 
/* 2772 */       retval.stop = this.input.LT(-1);
/*      */ 
/* 2774 */       if (this.state.backtracking == 0)
/*      */       {
/* 2776 */         retval.tree = ((CommonTree)this.adaptor.rulePostProcessing(root_0));
/* 2777 */         this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
/*      */       }
/*      */     } catch (RecognitionException re) {
/* 2780 */       re = 
/* 2787 */         re;
/*      */ 
/* 2781 */       reportError(re);
/* 2782 */       recover(this.input, re);
/* 2783 */       retval.tree = ((CommonTree)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), re));
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/* 2788 */     return retval;
/*      */   }
/*      */ 
/*      */   public final block_return block()
/*      */     throws RecognitionException
/*      */   {
/* 2800 */     block_return retval = new block_return();
/* 2801 */     retval.start = this.input.LT(1);
/*      */ 
/* 2803 */     CommonTree root_0 = null;
/*      */ 
/* 2805 */     Token lp = null;
/* 2806 */     Token rp = null;
/* 2807 */     Token char_literal76 = null;
/* 2808 */     Token char_literal78 = null;
/* 2809 */     optionsSpec_return opts = null;
/*      */ 
/* 2811 */     alternative_return a1 = null;
/*      */ 
/* 2813 */     alternative_return a2 = null;
/*      */ 
/* 2815 */     rewrite_return rewrite77 = null;
/*      */ 
/* 2817 */     rewrite_return rewrite79 = null;
/*      */ 
/* 2820 */     CommonTree lp_tree = null;
/* 2821 */     CommonTree rp_tree = null;
/* 2822 */     CommonTree char_literal76_tree = null;
/* 2823 */     CommonTree char_literal78_tree = null;
/* 2824 */     RewriteRuleTokenStream stream_79 = new RewriteRuleTokenStream(this.adaptor, "token 79");
/* 2825 */     RewriteRuleTokenStream stream_82 = new RewriteRuleTokenStream(this.adaptor, "token 82");
/* 2826 */     RewriteRuleTokenStream stream_83 = new RewriteRuleTokenStream(this.adaptor, "token 83");
/* 2827 */     RewriteRuleTokenStream stream_84 = new RewriteRuleTokenStream(this.adaptor, "token 84");
/* 2828 */     RewriteRuleSubtreeStream stream_rewrite = new RewriteRuleSubtreeStream(this.adaptor, "rule rewrite");
/* 2829 */     RewriteRuleSubtreeStream stream_alternative = new RewriteRuleSubtreeStream(this.adaptor, "rule alternative");
/* 2830 */     RewriteRuleSubtreeStream stream_optionsSpec = new RewriteRuleSubtreeStream(this.adaptor, "rule optionsSpec");
/*      */     try
/*      */     {
/* 2835 */       lp = (Token)match(this.input, 82, FOLLOW_82_in_block1183); if (this.state.failed) { block_return localblock_return1 = retval; return localblock_return1; }
/* 2836 */       if (this.state.backtracking == 0) stream_82.add(lp);
/*      */ 
/* 2839 */       int alt31 = 2;
/* 2840 */       switch (this.input.LA(1))
/*      */       {
/*      */       case 46:
/*      */       case 79:
/* 2844 */         alt31 = 1;
/*      */       }
/*      */       int alt30;
/*      */       block_return localblock_return2;
/* 2849 */       switch (alt31)
/*      */       {
/*      */       case 1:
/* 2854 */         alt30 = 2;
/* 2855 */         switch (this.input.LA(1))
/*      */         {
/*      */         case 46:
/* 2858 */           alt30 = 1;
/*      */         }
/*      */ 
/* 2863 */         switch (alt30)
/*      */         {
/*      */         case 1:
/* 2867 */           pushFollow(FOLLOW_optionsSpec_in_block1192);
/* 2868 */           opts = optionsSpec();
/*      */ 
/* 2870 */           this.state._fsp -= 1;
/* 2871 */           if (this.state.failed) { localblock_return2 = retval; return localblock_return2; }
/* 2872 */           if (this.state.backtracking == 0) stream_optionsSpec.add(opts.getTree());
/*      */ 
/*      */           break;
/*      */         }
/*      */ 
/* 2879 */         char_literal76 = (Token)match(this.input, 79, FOLLOW_79_in_block1196); if (this.state.failed) { localblock_return2 = retval; return localblock_return2; }
/* 2880 */         if (this.state.backtracking == 0) stream_79.add(char_literal76);
/*      */ 
/*      */         break;
/*      */       }
/*      */ 
/* 2888 */       pushFollow(FOLLOW_alternative_in_block1205);
/* 2889 */       a1 = alternative();
/*      */ 
/* 2891 */       this.state._fsp -= 1;
/* 2892 */       if (this.state.failed) { alt30 = retval; return alt30; }
/* 2893 */       if (this.state.backtracking == 0) stream_alternative.add(a1.getTree());
/* 2894 */       pushFollow(FOLLOW_rewrite_in_block1207);
/* 2895 */       rewrite77 = rewrite();
/*      */ 
/* 2897 */       this.state._fsp -= 1;
/* 2898 */       if (this.state.failed) { alt30 = retval; return alt30; }
/* 2899 */       if (this.state.backtracking == 0) stream_rewrite.add(rewrite77.getTree());
/*      */       int alt32;
/*      */       while (true)
/*      */       {
/* 2903 */         alt32 = 2;
/* 2904 */         switch (this.input.LA(1))
/*      */         {
/*      */         case 83:
/* 2907 */           alt32 = 1;
/*      */         }
/*      */ 
/* 2913 */         switch (alt32)
/*      */         {
/*      */         case 1:
/* 2917 */           char_literal78 = (Token)match(this.input, 83, FOLLOW_83_in_block1211); if (this.state.failed) { localblock_return2 = retval; return localblock_return2; }
/* 2918 */           if (this.state.backtracking == 0) stream_83.add(char_literal78);
/*      */ 
/* 2920 */           pushFollow(FOLLOW_alternative_in_block1215);
/* 2921 */           a2 = alternative();
/*      */ 
/* 2923 */           this.state._fsp -= 1;
/* 2924 */           if (this.state.failed) { localblock_return2 = retval; return localblock_return2; }
/* 2925 */           if (this.state.backtracking == 0) stream_alternative.add(a2.getTree());
/* 2926 */           pushFollow(FOLLOW_rewrite_in_block1217);
/* 2927 */           rewrite79 = rewrite();
/*      */ 
/* 2929 */           this.state._fsp -= 1;
/* 2930 */           if (this.state.failed) { localblock_return2 = retval; return localblock_return2; }
/* 2931 */           if (this.state.backtracking == 0) stream_rewrite.add(rewrite79.getTree()); break;
/*      */         default:
/* 2937 */           break label816;
/*      */         }
/*      */       }
/*      */ 
/* 2941 */       label816: rp = (Token)match(this.input, 84, FOLLOW_84_in_block1232); if (this.state.failed) { alt32 = retval; return alt32; }
/* 2942 */       if (this.state.backtracking == 0) stream_84.add(rp);
/*      */ 
/* 2953 */       if (this.state.backtracking == 0) {
/* 2954 */         retval.tree = root_0;
/* 2955 */         RewriteRuleSubtreeStream stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.tree : null);
/*      */ 
/* 2957 */         root_0 = (CommonTree)this.adaptor.nil();
/*      */ 
/* 2962 */         CommonTree root_1 = (CommonTree)this.adaptor.nil();
/* 2963 */         root_1 = (CommonTree)this.adaptor.becomeRoot((CommonTree)this.adaptor.create(8, lp, "BLOCK"), root_1);
/*      */ 
/* 2966 */         if (stream_optionsSpec.hasNext()) {
/* 2967 */           this.adaptor.addChild(root_1, stream_optionsSpec.nextTree());
/*      */         }
/*      */ 
/* 2970 */         stream_optionsSpec.reset();
/* 2971 */         if (!stream_alternative.hasNext()) {
/* 2972 */           throw new RewriteEarlyExitException();
/*      */         }
/* 2974 */         while (stream_alternative.hasNext()) {
/* 2975 */           this.adaptor.addChild(root_1, stream_alternative.nextTree());
/*      */ 
/* 2977 */           if (stream_rewrite.hasNext()) {
/* 2978 */             this.adaptor.addChild(root_1, stream_rewrite.nextTree());
/*      */           }
/*      */ 
/* 2981 */           stream_rewrite.reset();
/*      */         }
/*      */ 
/* 2984 */         stream_alternative.reset();
/* 2985 */         this.adaptor.addChild(root_1, (CommonTree)this.adaptor.create(18, rp, "EOB"));
/*      */ 
/* 2987 */         this.adaptor.addChild(root_0, root_1);
/*      */ 
/* 2992 */         retval.tree = root_0;
/*      */       }
/*      */ 
/* 2995 */       retval.stop = this.input.LT(-1);
/*      */ 
/* 2997 */       if (this.state.backtracking == 0)
/*      */       {
/* 2999 */         retval.tree = ((CommonTree)this.adaptor.rulePostProcessing(root_0));
/* 3000 */         this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
/*      */       }
/*      */     } catch (RecognitionException re) {
/* 3003 */       re = 
/* 3010 */         re;
/*      */ 
/* 3004 */       reportError(re);
/* 3005 */       recover(this.input, re);
/* 3006 */       retval.tree = ((CommonTree)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), re));
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/* 3011 */     return retval;
/*      */   }
/*      */ 
/*      */   public final altList_return altList()
/*      */     throws RecognitionException
/*      */   {
/* 3023 */     altList_return retval = new altList_return();
/* 3024 */     retval.start = this.input.LT(1);
/*      */ 
/* 3026 */     CommonTree root_0 = null;
/*      */ 
/* 3028 */     Token char_literal81 = null;
/* 3029 */     alternative_return a1 = null;
/*      */ 
/* 3031 */     alternative_return a2 = null;
/*      */ 
/* 3033 */     rewrite_return rewrite80 = null;
/*      */ 
/* 3035 */     rewrite_return rewrite82 = null;
/*      */ 
/* 3038 */     CommonTree char_literal81_tree = null;
/* 3039 */     RewriteRuleTokenStream stream_83 = new RewriteRuleTokenStream(this.adaptor, "token 83");
/* 3040 */     RewriteRuleSubtreeStream stream_rewrite = new RewriteRuleSubtreeStream(this.adaptor, "rule rewrite");
/* 3041 */     RewriteRuleSubtreeStream stream_alternative = new RewriteRuleSubtreeStream(this.adaptor, "rule alternative");
/*      */ 
/* 3046 */     CommonTree blkRoot = (CommonTree)this.adaptor.create(8, this.input.LT(-1), "BLOCK");
/*      */     try
/*      */     {
/* 3052 */       pushFollow(FOLLOW_alternative_in_altList1289);
/* 3053 */       a1 = alternative();
/*      */ 
/* 3055 */       this.state._fsp -= 1;
/*      */       altList_return localaltList_return1;
/* 3056 */       if (this.state.failed) { localaltList_return1 = retval; return localaltList_return1; }
/* 3057 */       if (this.state.backtracking == 0) stream_alternative.add(a1.getTree());
/* 3058 */       pushFollow(FOLLOW_rewrite_in_altList1291);
/* 3059 */       rewrite80 = rewrite();
/*      */ 
/* 3061 */       this.state._fsp -= 1;
/* 3062 */       if (this.state.failed) { localaltList_return1 = retval; return localaltList_return1; }
/* 3063 */       if (this.state.backtracking == 0) stream_rewrite.add(rewrite80.getTree());
/*      */ 
/*      */       while (true)
/*      */       {
/* 3067 */         int alt33 = 2;
/* 3068 */         switch (this.input.LA(1))
/*      */         {
/*      */         case 83:
/* 3071 */           alt33 = 1;
/*      */         }
/*      */ 
/* 3077 */         switch (alt33)
/*      */         {
/*      */         case 1:
/* 3081 */           char_literal81 = (Token)match(this.input, 83, FOLLOW_83_in_altList1295);
/*      */           altList_return localaltList_return2;
/* 3081 */           if (this.state.failed) { localaltList_return2 = retval; return localaltList_return2; }
/* 3082 */           if (this.state.backtracking == 0) stream_83.add(char_literal81);
/*      */ 
/* 3084 */           pushFollow(FOLLOW_alternative_in_altList1299);
/* 3085 */           a2 = alternative();
/*      */ 
/* 3087 */           this.state._fsp -= 1;
/* 3088 */           if (this.state.failed) { localaltList_return2 = retval; return localaltList_return2; }
/* 3089 */           if (this.state.backtracking == 0) stream_alternative.add(a2.getTree());
/* 3090 */           pushFollow(FOLLOW_rewrite_in_altList1301);
/* 3091 */           rewrite82 = rewrite();
/*      */ 
/* 3093 */           this.state._fsp -= 1;
/* 3094 */           if (this.state.failed) { localaltList_return2 = retval; return localaltList_return2; }
/* 3095 */           if (this.state.backtracking == 0) stream_rewrite.add(rewrite82.getTree()); break;
/*      */         default:
/* 3101 */           break label478;
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/* 3114 */       label478: if (this.state.backtracking == 0) {
/* 3115 */         retval.tree = root_0;
/* 3116 */         RewriteRuleSubtreeStream stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.tree : null);
/*      */ 
/* 3118 */         root_0 = (CommonTree)this.adaptor.nil();
/*      */ 
/* 3123 */         CommonTree root_1 = (CommonTree)this.adaptor.nil();
/* 3124 */         root_1 = (CommonTree)this.adaptor.becomeRoot(blkRoot, root_1);
/*      */ 
/* 3126 */         if (!stream_alternative.hasNext()) {
/* 3127 */           throw new RewriteEarlyExitException();
/*      */         }
/* 3129 */         while (stream_alternative.hasNext()) {
/* 3130 */           this.adaptor.addChild(root_1, stream_alternative.nextTree());
/*      */ 
/* 3132 */           if (stream_rewrite.hasNext()) {
/* 3133 */             this.adaptor.addChild(root_1, stream_rewrite.nextTree());
/*      */           }
/*      */ 
/* 3136 */           stream_rewrite.reset();
/*      */         }
/*      */ 
/* 3139 */         stream_alternative.reset();
/* 3140 */         this.adaptor.addChild(root_1, (CommonTree)this.adaptor.create(18, "EOB"));
/*      */ 
/* 3142 */         this.adaptor.addChild(root_0, root_1);
/*      */ 
/* 3147 */         retval.tree = root_0;
/*      */       }
/*      */ 
/* 3150 */       retval.stop = this.input.LT(-1);
/*      */ 
/* 3152 */       if (this.state.backtracking == 0)
/*      */       {
/* 3154 */         retval.tree = ((CommonTree)this.adaptor.rulePostProcessing(root_0));
/* 3155 */         this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
/*      */       }
/*      */     } catch (RecognitionException re) {
/* 3158 */       re = 
/* 3165 */         re;
/*      */ 
/* 3159 */       reportError(re);
/* 3160 */       recover(this.input, re);
/* 3161 */       retval.tree = ((CommonTree)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), re));
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/* 3166 */     return retval;
/*      */   }
/*      */ 
/*      */   public final alternative_return alternative()
/*      */     throws RecognitionException
/*      */   {
/* 3178 */     alternative_return retval = new alternative_return();
/* 3179 */     retval.start = this.input.LT(1);
/*      */ 
/* 3181 */     CommonTree root_0 = null;
/*      */ 
/* 3183 */     element_return element83 = null;
/*      */ 
/* 3186 */     RewriteRuleSubtreeStream stream_element = new RewriteRuleSubtreeStream(this.adaptor, "rule element");
/*      */ 
/* 3188 */     Token firstToken = this.input.LT(1);
/* 3189 */     Token prevToken = this.input.LT(-1);
/*      */     try
/*      */     {
/* 3193 */       int alt35 = 2;
/* 3194 */       switch (this.input.LA(1))
/*      */       {
/*      */       case 32:
/*      */       case 37:
/*      */       case 42:
/*      */       case 43:
/*      */       case 44:
/*      */       case 45:
/*      */       case 49:
/*      */       case 82:
/*      */       case 89:
/*      */       case 92:
/* 3206 */         alt35 = 1;
/*      */ 
/* 3208 */         break;
/*      */       case 40:
/*      */       case 69:
/*      */       case 83:
/*      */       case 84:
/* 3214 */         alt35 = 2;
/*      */ 
/* 3216 */         break;
/*      */       default:
/* 3218 */         if (this.state.backtracking > 0) { this.state.failed = true; alternative_return localalternative_return1 = retval; return localalternative_return1; }
/* 3219 */         NoViableAltException nvae = new NoViableAltException("", 35, 0, this.input);
/*      */ 
/* 3222 */         throw nvae;
/*      */       }
/*      */ 
/* 3225 */       switch (alt35)
/*      */       {
/*      */       case 1:
/* 3230 */         int cnt34 = 0;
/*      */         while (true)
/*      */         {
/* 3233 */           int alt34 = 2;
/* 3234 */           switch (this.input.LA(1))
/*      */           {
/*      */           case 32:
/*      */           case 37:
/*      */           case 42:
/*      */           case 43:
/*      */           case 44:
/*      */           case 45:
/*      */           case 49:
/*      */           case 82:
/*      */           case 89:
/*      */           case 92:
/* 3246 */             alt34 = 1;
/*      */           }
/*      */           alternative_return localalternative_return2;
/* 3252 */           switch (alt34)
/*      */           {
/*      */           case 1:
/* 3256 */             pushFollow(FOLLOW_element_in_alternative1349);
/* 3257 */             element83 = element();
/*      */ 
/* 3259 */             this.state._fsp -= 1;
/* 3260 */             if (this.state.failed) { localalternative_return2 = retval; return localalternative_return2; }
/* 3261 */             if (this.state.backtracking == 0) stream_element.add(element83.getTree()); break;
/*      */           default:
/* 3267 */             if (cnt34 >= 1) break label536;
/* 3268 */             if (this.state.backtracking > 0) { this.state.failed = true; localalternative_return2 = retval; return localalternative_return2; }
/* 3269 */             EarlyExitException eee = new EarlyExitException(34, this.input);
/*      */ 
/* 3271 */             throw eee;
/*      */           }
/* 3273 */           cnt34++;
/*      */         }
/*      */ 
/* 3285 */         if (this.state.backtracking == 0) {
/* 3286 */           retval.tree = root_0;
/* 3287 */           RewriteRuleSubtreeStream stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.tree : null);
/*      */ 
/* 3289 */           root_0 = (CommonTree)this.adaptor.nil();
/*      */ 
/* 3294 */           CommonTree root_1 = (CommonTree)this.adaptor.nil();
/* 3295 */           root_1 = (CommonTree)this.adaptor.becomeRoot((CommonTree)this.adaptor.create(16, firstToken, "ALT"), root_1);
/*      */ 
/* 3297 */           if (!stream_element.hasNext()) {
/* 3298 */             throw new RewriteEarlyExitException();
/*      */           }
/* 3300 */           while (stream_element.hasNext()) {
/* 3301 */             this.adaptor.addChild(root_1, stream_element.nextTree());
/*      */           }
/*      */ 
/* 3304 */           stream_element.reset();
/* 3305 */           this.adaptor.addChild(root_1, (CommonTree)this.adaptor.create(19, "EOA"));
/*      */ 
/* 3307 */           this.adaptor.addChild(root_0, root_1);
/*      */ 
/* 3312 */           retval.tree = root_0;
/*      */         }
/* 3314 */         break;
/*      */       case 2:
/* 3326 */         label536: if (this.state.backtracking == 0) {
/* 3327 */           retval.tree = root_0;
/* 3328 */           RewriteRuleSubtreeStream stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.tree : null);
/*      */ 
/* 3330 */           root_0 = (CommonTree)this.adaptor.nil();
/*      */ 
/* 3335 */           CommonTree root_1 = (CommonTree)this.adaptor.nil();
/* 3336 */           root_1 = (CommonTree)this.adaptor.becomeRoot((CommonTree)this.adaptor.create(16, prevToken, "ALT"), root_1);
/*      */ 
/* 3338 */           this.adaptor.addChild(root_1, (CommonTree)this.adaptor.create(15, prevToken, "EPSILON"));
/* 3339 */           this.adaptor.addChild(root_1, (CommonTree)this.adaptor.create(19, "EOA"));
/*      */ 
/* 3341 */           this.adaptor.addChild(root_0, root_1);
/*      */ 
/* 3346 */           retval.tree = root_0;
/*      */         }
/*      */         break;
/*      */       }
/*      */ 
/* 3351 */       retval.stop = this.input.LT(-1);
/*      */ 
/* 3353 */       if (this.state.backtracking == 0)
/*      */       {
/* 3355 */         retval.tree = ((CommonTree)this.adaptor.rulePostProcessing(root_0));
/* 3356 */         this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
/*      */       }
/*      */     } catch (RecognitionException re) {
/* 3359 */       re = 
/* 3366 */         re;
/*      */ 
/* 3360 */       reportError(re);
/* 3361 */       recover(this.input, re);
/* 3362 */       retval.tree = ((CommonTree)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), re));
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/* 3367 */     return retval;
/*      */   }
/*      */ 
/*      */   public final exceptionGroup_return exceptionGroup()
/*      */     throws RecognitionException
/*      */   {
/* 3379 */     exceptionGroup_return retval = new exceptionGroup_return();
/* 3380 */     retval.start = this.input.LT(1);
/*      */ 
/* 3382 */     CommonTree root_0 = null;
/*      */ 
/* 3384 */     exceptionHandler_return exceptionHandler84 = null;
/*      */ 
/* 3386 */     finallyClause_return finallyClause85 = null;
/*      */ 
/* 3388 */     finallyClause_return finallyClause86 = null;
/*      */     try
/*      */     {
/* 3394 */       int alt38 = 2;
/* 3395 */       switch (this.input.LA(1))
/*      */       {
/*      */       case 85:
/* 3398 */         alt38 = 1;
/*      */ 
/* 3400 */         break;
/*      */       case 86:
/* 3403 */         alt38 = 2;
/*      */ 
/* 3405 */         break;
/*      */       default:
/* 3407 */         if (this.state.backtracking > 0) { this.state.failed = true; exceptionGroup_return localexceptionGroup_return1 = retval; return localexceptionGroup_return1; }
/* 3408 */         NoViableAltException nvae = new NoViableAltException("", 38, 0, this.input);
/*      */ 
/* 3411 */         throw nvae;
/*      */       }
/*      */       int cnt36;
/* 3414 */       switch (alt38)
/*      */       {
/*      */       case 1:
/* 3418 */         root_0 = (CommonTree)this.adaptor.nil();
/*      */ 
/* 3421 */         cnt36 = 0;
/*      */         Object eee;
/*      */         while (true) {
/* 3424 */           int alt36 = 2;
/* 3425 */           switch (this.input.LA(1))
/*      */           {
/*      */           case 85:
/* 3428 */             alt36 = 1;
/*      */           }
/*      */           exceptionGroup_return localexceptionGroup_return3;
/* 3434 */           switch (alt36)
/*      */           {
/*      */           case 1:
/* 3438 */             pushFollow(FOLLOW_exceptionHandler_in_exceptionGroup1400);
/* 3439 */             exceptionHandler84 = exceptionHandler();
/*      */ 
/* 3441 */             this.state._fsp -= 1;
/* 3442 */             if (this.state.failed) { localexceptionGroup_return3 = retval; return localexceptionGroup_return3; }
/* 3443 */             if (this.state.backtracking == 0) this.adaptor.addChild(root_0, exceptionHandler84.getTree()); break;
/*      */           default:
/* 3449 */             if (cnt36 >= 1) break label353;
/* 3450 */             if (this.state.backtracking > 0) { this.state.failed = true; localexceptionGroup_return3 = retval; return localexceptionGroup_return3; }
/* 3451 */             eee = new EarlyExitException(36, this.input);
/*      */ 
/* 3453 */             throw ((Throwable)eee);
/*      */           }
/* 3455 */           cnt36++;
/*      */         }
/*      */ 
/* 3459 */         int alt37 = 2;
/* 3460 */         switch (this.input.LA(1))
/*      */         {
/*      */         case 86:
/* 3463 */           alt37 = 1;
/*      */         }
/*      */ 
/* 3468 */         switch (alt37)
/*      */         {
/*      */         case 1:
/* 3472 */           pushFollow(FOLLOW_finallyClause_in_exceptionGroup1407);
/* 3473 */           finallyClause85 = finallyClause();
/*      */ 
/* 3475 */           this.state._fsp -= 1;
/* 3476 */           if (this.state.failed) { eee = retval; return eee; }
/* 3477 */           if (this.state.backtracking == 0) this.adaptor.addChild(root_0, finallyClause85.getTree());
/*      */ 
/*      */           break;
/*      */         }
/*      */ 
/* 3486 */         break;
/*      */       case 2:
/* 3490 */         label353: root_0 = (CommonTree)this.adaptor.nil();
/*      */ 
/* 3492 */         pushFollow(FOLLOW_finallyClause_in_exceptionGroup1415);
/* 3493 */         finallyClause86 = finallyClause();
/*      */ 
/* 3495 */         this.state._fsp -= 1;
/* 3496 */         if (this.state.failed) { exceptionGroup_return localexceptionGroup_return2 = retval; return localexceptionGroup_return2; }
/* 3497 */         if (this.state.backtracking == 0) this.adaptor.addChild(root_0, finallyClause86.getTree());
/*      */ 
/*      */         break;
/*      */       }
/*      */ 
/* 3503 */       retval.stop = this.input.LT(-1);
/*      */ 
/* 3505 */       if (this.state.backtracking == 0)
/*      */       {
/* 3507 */         retval.tree = ((CommonTree)this.adaptor.rulePostProcessing(root_0));
/* 3508 */         this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
/*      */       }
/*      */     } catch (RecognitionException re) {
/* 3511 */       re = 
/* 3518 */         re;
/*      */ 
/* 3512 */       reportError(re);
/* 3513 */       recover(this.input, re);
/* 3514 */       retval.tree = ((CommonTree)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), re));
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/* 3519 */     return retval;
/*      */   }
/*      */ 
/*      */   public final exceptionHandler_return exceptionHandler()
/*      */     throws RecognitionException
/*      */   {
/* 3531 */     exceptionHandler_return retval = new exceptionHandler_return();
/* 3532 */     retval.start = this.input.LT(1);
/*      */ 
/* 3534 */     CommonTree root_0 = null;
/*      */ 
/* 3536 */     Token string_literal87 = null;
/* 3537 */     Token ARG_ACTION88 = null;
/* 3538 */     Token ACTION89 = null;
/*      */ 
/* 3540 */     CommonTree string_literal87_tree = null;
/* 3541 */     CommonTree ARG_ACTION88_tree = null;
/* 3542 */     CommonTree ACTION89_tree = null;
/* 3543 */     RewriteRuleTokenStream stream_ACTION = new RewriteRuleTokenStream(this.adaptor, "token ACTION");
/* 3544 */     RewriteRuleTokenStream stream_ARG_ACTION = new RewriteRuleTokenStream(this.adaptor, "token ARG_ACTION");
/* 3545 */     RewriteRuleTokenStream stream_85 = new RewriteRuleTokenStream(this.adaptor, "token 85");
/*      */     try
/*      */     {
/* 3551 */       string_literal87 = (Token)match(this.input, 85, FOLLOW_85_in_exceptionHandler1435);
/*      */       exceptionHandler_return localexceptionHandler_return1;
/* 3551 */       if (this.state.failed) { localexceptionHandler_return1 = retval; return localexceptionHandler_return1; }
/* 3552 */       if (this.state.backtracking == 0) stream_85.add(string_literal87);
/*      */ 
/* 3554 */       ARG_ACTION88 = (Token)match(this.input, 48, FOLLOW_ARG_ACTION_in_exceptionHandler1437); if (this.state.failed) { localexceptionHandler_return1 = retval; return localexceptionHandler_return1; }
/* 3555 */       if (this.state.backtracking == 0) stream_ARG_ACTION.add(ARG_ACTION88);
/*      */ 
/* 3557 */       ACTION89 = (Token)match(this.input, 45, FOLLOW_ACTION_in_exceptionHandler1439); if (this.state.failed) { localexceptionHandler_return1 = retval; return localexceptionHandler_return1; }
/* 3558 */       if (this.state.backtracking == 0) stream_ACTION.add(ACTION89);
/*      */ 
/* 3569 */       if (this.state.backtracking == 0) {
/* 3570 */         retval.tree = root_0;
/* 3571 */         RewriteRuleSubtreeStream stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.tree : null);
/*      */ 
/* 3573 */         root_0 = (CommonTree)this.adaptor.nil();
/*      */ 
/* 3578 */         CommonTree root_1 = (CommonTree)this.adaptor.nil();
/* 3579 */         root_1 = (CommonTree)this.adaptor.becomeRoot(stream_85.nextNode(), root_1);
/*      */ 
/* 3581 */         this.adaptor.addChild(root_1, stream_ARG_ACTION.nextNode());
/* 3582 */         this.adaptor.addChild(root_1, stream_ACTION.nextNode());
/*      */ 
/* 3584 */         this.adaptor.addChild(root_0, root_1);
/*      */ 
/* 3589 */         retval.tree = root_0;
/*      */       }
/*      */ 
/* 3592 */       retval.stop = this.input.LT(-1);
/*      */ 
/* 3594 */       if (this.state.backtracking == 0)
/*      */       {
/* 3596 */         retval.tree = ((CommonTree)this.adaptor.rulePostProcessing(root_0));
/* 3597 */         this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
/*      */       }
/*      */     } catch (RecognitionException re) {
/* 3600 */       re = 
/* 3607 */         re;
/*      */ 
/* 3601 */       reportError(re);
/* 3602 */       recover(this.input, re);
/* 3603 */       retval.tree = ((CommonTree)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), re));
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/* 3608 */     return retval;
/*      */   }
/*      */ 
/*      */   public final finallyClause_return finallyClause()
/*      */     throws RecognitionException
/*      */   {
/* 3620 */     finallyClause_return retval = new finallyClause_return();
/* 3621 */     retval.start = this.input.LT(1);
/*      */ 
/* 3623 */     CommonTree root_0 = null;
/*      */ 
/* 3625 */     Token string_literal90 = null;
/* 3626 */     Token ACTION91 = null;
/*      */ 
/* 3628 */     CommonTree string_literal90_tree = null;
/* 3629 */     CommonTree ACTION91_tree = null;
/* 3630 */     RewriteRuleTokenStream stream_ACTION = new RewriteRuleTokenStream(this.adaptor, "token ACTION");
/* 3631 */     RewriteRuleTokenStream stream_86 = new RewriteRuleTokenStream(this.adaptor, "token 86");
/*      */     try
/*      */     {
/* 3637 */       string_literal90 = (Token)match(this.input, 86, FOLLOW_86_in_finallyClause1469);
/*      */       finallyClause_return localfinallyClause_return1;
/* 3637 */       if (this.state.failed) { localfinallyClause_return1 = retval; return localfinallyClause_return1; }
/* 3638 */       if (this.state.backtracking == 0) stream_86.add(string_literal90);
/*      */ 
/* 3640 */       ACTION91 = (Token)match(this.input, 45, FOLLOW_ACTION_in_finallyClause1471); if (this.state.failed) { localfinallyClause_return1 = retval; return localfinallyClause_return1; }
/* 3641 */       if (this.state.backtracking == 0) stream_ACTION.add(ACTION91);
/*      */ 
/* 3652 */       if (this.state.backtracking == 0) {
/* 3653 */         retval.tree = root_0;
/* 3654 */         RewriteRuleSubtreeStream stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.tree : null);
/*      */ 
/* 3656 */         root_0 = (CommonTree)this.adaptor.nil();
/*      */ 
/* 3661 */         CommonTree root_1 = (CommonTree)this.adaptor.nil();
/* 3662 */         root_1 = (CommonTree)this.adaptor.becomeRoot(stream_86.nextNode(), root_1);
/*      */ 
/* 3664 */         this.adaptor.addChild(root_1, stream_ACTION.nextNode());
/*      */ 
/* 3666 */         this.adaptor.addChild(root_0, root_1);
/*      */ 
/* 3671 */         retval.tree = root_0;
/*      */       }
/*      */ 
/* 3674 */       retval.stop = this.input.LT(-1);
/*      */ 
/* 3676 */       if (this.state.backtracking == 0)
/*      */       {
/* 3678 */         retval.tree = ((CommonTree)this.adaptor.rulePostProcessing(root_0));
/* 3679 */         this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
/*      */       }
/*      */     } catch (RecognitionException re) {
/* 3682 */       re = 
/* 3689 */         re;
/*      */ 
/* 3683 */       reportError(re);
/* 3684 */       recover(this.input, re);
/* 3685 */       retval.tree = ((CommonTree)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), re));
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/* 3690 */     return retval;
/*      */   }
/*      */ 
/*      */   public final element_return element()
/*      */     throws RecognitionException
/*      */   {
/* 3702 */     element_return retval = new element_return();
/* 3703 */     retval.start = this.input.LT(1);
/*      */ 
/* 3705 */     CommonTree root_0 = null;
/*      */ 
/* 3707 */     elementNoOptionSpec_return elementNoOptionSpec92 = null;
/*      */     try
/*      */     {
/* 3715 */       root_0 = (CommonTree)this.adaptor.nil();
/*      */ 
/* 3717 */       pushFollow(FOLLOW_elementNoOptionSpec_in_element1493);
/* 3718 */       elementNoOptionSpec92 = elementNoOptionSpec();
/*      */ 
/* 3720 */       this.state._fsp -= 1;
/* 3721 */       if (this.state.failed) { element_return localelement_return1 = retval; return localelement_return1; }
/* 3722 */       if (this.state.backtracking == 0) this.adaptor.addChild(root_0, elementNoOptionSpec92.getTree());
/*      */ 
/* 3726 */       retval.stop = this.input.LT(-1);
/*      */ 
/* 3728 */       if (this.state.backtracking == 0)
/*      */       {
/* 3730 */         retval.tree = ((CommonTree)this.adaptor.rulePostProcessing(root_0));
/* 3731 */         this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
/*      */       }
/*      */     } catch (RecognitionException re) {
/* 3734 */       re = 
/* 3741 */         re;
/*      */ 
/* 3735 */       reportError(re);
/* 3736 */       recover(this.input, re);
/* 3737 */       retval.tree = ((CommonTree)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), re));
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/* 3742 */     return retval;
/*      */   }
/*      */ 
/*      */   public final elementNoOptionSpec_return elementNoOptionSpec()
/*      */     throws RecognitionException
/*      */   {
/* 3754 */     elementNoOptionSpec_return retval = new elementNoOptionSpec_return();
/* 3755 */     retval.start = this.input.LT(1);
/*      */ 
/* 3757 */     CommonTree root_0 = null;
/*      */ 
/* 3759 */     Token labelOp = null;
/* 3760 */     Token ACTION102 = null;
/* 3761 */     Token SEMPRED103 = null;
/* 3762 */     Token string_literal104 = null;
/* 3763 */     id_return id93 = null;
/*      */ 
/* 3765 */     atom_return atom94 = null;
/*      */ 
/* 3767 */     ebnfSuffix_return ebnfSuffix95 = null;
/*      */ 
/* 3769 */     id_return id96 = null;
/*      */ 
/* 3771 */     block_return block97 = null;
/*      */ 
/* 3773 */     ebnfSuffix_return ebnfSuffix98 = null;
/*      */ 
/* 3775 */     atom_return atom99 = null;
/*      */ 
/* 3777 */     ebnfSuffix_return ebnfSuffix100 = null;
/*      */ 
/* 3779 */     ebnf_return ebnf101 = null;
/*      */ 
/* 3781 */     treeSpec_return treeSpec105 = null;
/*      */ 
/* 3783 */     ebnfSuffix_return ebnfSuffix106 = null;
/*      */ 
/* 3786 */     CommonTree labelOp_tree = null;
/* 3787 */     CommonTree ACTION102_tree = null;
/* 3788 */     CommonTree SEMPRED103_tree = null;
/* 3789 */     CommonTree string_literal104_tree = null;
/* 3790 */     RewriteRuleTokenStream stream_SEMPRED = new RewriteRuleTokenStream(this.adaptor, "token SEMPRED");
/* 3791 */     RewriteRuleTokenStream stream_71 = new RewriteRuleTokenStream(this.adaptor, "token 71");
/* 3792 */     RewriteRuleTokenStream stream_87 = new RewriteRuleTokenStream(this.adaptor, "token 87");
/* 3793 */     RewriteRuleTokenStream stream_88 = new RewriteRuleTokenStream(this.adaptor, "token 88");
/* 3794 */     RewriteRuleSubtreeStream stream_id = new RewriteRuleSubtreeStream(this.adaptor, "rule id");
/* 3795 */     RewriteRuleSubtreeStream stream_atom = new RewriteRuleSubtreeStream(this.adaptor, "rule atom");
/* 3796 */     RewriteRuleSubtreeStream stream_ebnfSuffix = new RewriteRuleSubtreeStream(this.adaptor, "rule ebnfSuffix");
/* 3797 */     RewriteRuleSubtreeStream stream_block = new RewriteRuleSubtreeStream(this.adaptor, "rule block");
/* 3798 */     RewriteRuleSubtreeStream stream_treeSpec = new RewriteRuleSubtreeStream(this.adaptor, "rule treeSpec");
/*      */     try
/*      */     {
/* 3801 */       int alt46 = 7;
/* 3802 */       alt46 = this.dfa46.predict(this.input);
/*      */       int alt39;
/*      */       int alt40;
/*      */       Object stream_labelOp;
/*      */       int alt41;
/*      */       int alt42;
/*      */       int alt43;
/*      */       Object stream_retval;
/*      */       elementNoOptionSpec_return localelementNoOptionSpec_return4;
/*      */       int alt44;
/*      */       Object stream_retval;
/* 3803 */       switch (alt46)
/*      */       {
/*      */       case 1:
/* 3807 */         pushFollow(FOLLOW_id_in_elementNoOptionSpec1504);
/* 3808 */         id93 = id();
/*      */ 
/* 3810 */         this.state._fsp -= 1;
/* 3811 */         if (this.state.failed) { elementNoOptionSpec_return localelementNoOptionSpec_return1 = retval; return localelementNoOptionSpec_return1; }
/* 3812 */         if (this.state.backtracking == 0) stream_id.add(id93.getTree());
/*      */ 
/* 3814 */         alt39 = 2;
/*      */         Object nvae;
/* 3815 */         switch (this.input.LA(1))
/*      */         {
/*      */         case 71:
/* 3818 */           alt39 = 1;
/*      */ 
/* 3820 */           break;
/*      */         case 87:
/* 3823 */           alt39 = 2;
/*      */ 
/* 3825 */           break;
/*      */         default:
/* 3827 */           if (this.state.backtracking > 0) { this.state.failed = true; elementNoOptionSpec_return localelementNoOptionSpec_return6 = retval; return localelementNoOptionSpec_return6; }
/* 3828 */           nvae = new NoViableAltException("", 39, 0, this.input);
/*      */ 
/* 3831 */           throw ((Throwable)nvae);
/*      */         }
/*      */ 
/* 3834 */         switch (alt39)
/*      */         {
/*      */         case 1:
/* 3838 */           labelOp = (Token)match(this.input, 71, FOLLOW_71_in_elementNoOptionSpec1509); if (this.state.failed) { nvae = retval; return nvae; }
/* 3839 */           if (this.state.backtracking == 0) stream_71.add(labelOp); break;
/*      */         case 2:
/* 3847 */           labelOp = (Token)match(this.input, 87, FOLLOW_87_in_elementNoOptionSpec1513); if (this.state.failed) { nvae = retval; return nvae; }
/* 3848 */           if (this.state.backtracking == 0) stream_87.add(labelOp);
/*      */ 
/*      */           break;
/*      */         }
/*      */ 
/* 3856 */         pushFollow(FOLLOW_atom_in_elementNoOptionSpec1516);
/* 3857 */         atom94 = atom();
/*      */ 
/* 3859 */         this.state._fsp -= 1;
/* 3860 */         if (this.state.failed) { nvae = retval; return nvae; }
/* 3861 */         if (this.state.backtracking == 0) stream_atom.add(atom94.getTree());
/*      */ 
/* 3863 */         alt40 = 2;
/*      */         Object nvae;
/* 3864 */         switch (this.input.LA(1))
/*      */         {
/*      */         case 74:
/*      */         case 90:
/*      */         case 91:
/* 3869 */           alt40 = 1;
/*      */ 
/* 3871 */           break;
/*      */         case 32:
/*      */         case 37:
/*      */         case 40:
/*      */         case 42:
/*      */         case 43:
/*      */         case 44:
/*      */         case 45:
/*      */         case 49:
/*      */         case 69:
/*      */         case 82:
/*      */         case 83:
/*      */         case 84:
/*      */         case 89:
/*      */         case 92:
/* 3887 */           alt40 = 2;
/*      */ 
/* 3889 */           break;
/*      */         case 33:
/*      */         case 34:
/*      */         case 35:
/*      */         case 36:
/*      */         case 38:
/*      */         case 39:
/*      */         case 41:
/*      */         case 46:
/*      */         case 47:
/*      */         case 48:
/*      */         case 50:
/*      */         case 51:
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
/*      */         case 70:
/*      */         case 71:
/*      */         case 72:
/*      */         case 73:
/*      */         case 75:
/*      */         case 76:
/*      */         case 77:
/*      */         case 78:
/*      */         case 79:
/*      */         case 80:
/*      */         case 81:
/*      */         case 85:
/*      */         case 86:
/*      */         case 87:
/*      */         case 88:
/*      */         default:
/* 3891 */           if (this.state.backtracking > 0) { this.state.failed = true; elementNoOptionSpec_return localelementNoOptionSpec_return9 = retval; return localelementNoOptionSpec_return9; }
/* 3892 */           nvae = new NoViableAltException("", 40, 0, this.input);
/*      */ 
/* 3895 */           throw ((Throwable)nvae);
/*      */         }
/*      */ 
/* 3898 */         switch (alt40)
/*      */         {
/*      */         case 1:
/* 3902 */           pushFollow(FOLLOW_ebnfSuffix_in_elementNoOptionSpec1522);
/* 3903 */           ebnfSuffix95 = ebnfSuffix();
/*      */ 
/* 3905 */           this.state._fsp -= 1;
/* 3906 */           if (this.state.failed) { nvae = retval; return nvae; }
/* 3907 */           if (this.state.backtracking == 0) stream_ebnfSuffix.add(ebnfSuffix95.getTree());
/*      */ 
/* 3917 */           if (this.state.backtracking == 0) {
/* 3918 */             retval.tree = root_0;
/* 3919 */             RewriteRuleTokenStream stream_labelOp = new RewriteRuleTokenStream(this.adaptor, "token labelOp", labelOp);
/* 3920 */             RewriteRuleSubtreeStream stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.tree : null);
/*      */ 
/* 3922 */             root_0 = (CommonTree)this.adaptor.nil();
/*      */ 
/* 3927 */             CommonTree root_1 = (CommonTree)this.adaptor.nil();
/* 3928 */             root_1 = (CommonTree)this.adaptor.becomeRoot(stream_ebnfSuffix.nextNode(), root_1);
/*      */ 
/* 3932 */             CommonTree root_2 = (CommonTree)this.adaptor.nil();
/* 3933 */             root_2 = (CommonTree)this.adaptor.becomeRoot((CommonTree)this.adaptor.create(8, "BLOCK"), root_2);
/*      */ 
/* 3937 */             CommonTree root_3 = (CommonTree)this.adaptor.nil();
/* 3938 */             root_3 = (CommonTree)this.adaptor.becomeRoot((CommonTree)this.adaptor.create(16, "ALT"), root_3);
/*      */ 
/* 3942 */             CommonTree root_4 = (CommonTree)this.adaptor.nil();
/* 3943 */             root_4 = (CommonTree)this.adaptor.becomeRoot(stream_labelOp.nextNode(), root_4);
/*      */ 
/* 3945 */             this.adaptor.addChild(root_4, stream_id.nextTree());
/* 3946 */             this.adaptor.addChild(root_4, stream_atom.nextTree());
/*      */ 
/* 3948 */             this.adaptor.addChild(root_3, root_4);
/*      */ 
/* 3950 */             this.adaptor.addChild(root_3, (CommonTree)this.adaptor.create(19, "EOA"));
/*      */ 
/* 3952 */             this.adaptor.addChild(root_2, root_3);
/*      */ 
/* 3954 */             this.adaptor.addChild(root_2, (CommonTree)this.adaptor.create(18, "EOB"));
/*      */ 
/* 3956 */             this.adaptor.addChild(root_1, root_2);
/*      */ 
/* 3959 */             this.adaptor.addChild(root_0, root_1);
/*      */ 
/* 3964 */             retval.tree = root_0; } break;
/*      */         case 2:
/* 3978 */           if (this.state.backtracking == 0) {
/* 3979 */             retval.tree = root_0;
/* 3980 */             stream_labelOp = new RewriteRuleTokenStream(this.adaptor, "token labelOp", labelOp);
/* 3981 */             RewriteRuleSubtreeStream stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.tree : null);
/*      */ 
/* 3983 */             root_0 = (CommonTree)this.adaptor.nil();
/*      */ 
/* 3988 */             CommonTree root_1 = (CommonTree)this.adaptor.nil();
/* 3989 */             root_1 = (CommonTree)this.adaptor.becomeRoot(((RewriteRuleTokenStream)stream_labelOp).nextNode(), root_1);
/*      */ 
/* 3991 */             this.adaptor.addChild(root_1, stream_id.nextTree());
/* 3992 */             this.adaptor.addChild(root_1, stream_atom.nextTree());
/*      */ 
/* 3994 */             this.adaptor.addChild(root_0, root_1);
/*      */ 
/* 3999 */             retval.tree = root_0;
/*      */           }
/*      */ 
/*      */           break;
/*      */         }
/*      */ 
/* 4007 */         break;
/*      */       case 2:
/* 4011 */         pushFollow(FOLLOW_id_in_elementNoOptionSpec1581);
/* 4012 */         id96 = id();
/*      */ 
/* 4014 */         this.state._fsp -= 1;
/* 4015 */         if (this.state.failed) { elementNoOptionSpec_return localelementNoOptionSpec_return2 = retval; return localelementNoOptionSpec_return2; }
/* 4016 */         if (this.state.backtracking == 0) stream_id.add(id96.getTree());
/*      */ 
/* 4018 */         alt41 = 2;
/*      */         Object nvae;
/* 4019 */         switch (this.input.LA(1))
/*      */         {
/*      */         case 71:
/* 4022 */           alt41 = 1;
/*      */ 
/* 4024 */           break;
/*      */         case 87:
/* 4027 */           alt41 = 2;
/*      */ 
/* 4029 */           break;
/*      */         default:
/* 4031 */           if (this.state.backtracking > 0) { this.state.failed = true; elementNoOptionSpec_return localelementNoOptionSpec_return7 = retval; return localelementNoOptionSpec_return7; }
/* 4032 */           nvae = new NoViableAltException("", 41, 0, this.input);
/*      */ 
/* 4035 */           throw ((Throwable)nvae);
/*      */         }
/*      */ 
/* 4038 */         switch (alt41)
/*      */         {
/*      */         case 1:
/* 4042 */           labelOp = (Token)match(this.input, 71, FOLLOW_71_in_elementNoOptionSpec1586); if (this.state.failed) { nvae = retval; return nvae; }
/* 4043 */           if (this.state.backtracking == 0) stream_71.add(labelOp); break;
/*      */         case 2:
/* 4051 */           labelOp = (Token)match(this.input, 87, FOLLOW_87_in_elementNoOptionSpec1590); if (this.state.failed) { nvae = retval; return nvae; }
/* 4052 */           if (this.state.backtracking == 0) stream_87.add(labelOp);
/*      */ 
/*      */           break;
/*      */         }
/*      */ 
/* 4060 */         pushFollow(FOLLOW_block_in_elementNoOptionSpec1593);
/* 4061 */         block97 = block();
/*      */ 
/* 4063 */         this.state._fsp -= 1;
/* 4064 */         if (this.state.failed) { nvae = retval; return nvae; }
/* 4065 */         if (this.state.backtracking == 0) stream_block.add(block97.getTree());
/*      */ 
/* 4067 */         alt42 = 2;
/*      */         Object nvae;
/* 4068 */         switch (this.input.LA(1))
/*      */         {
/*      */         case 74:
/*      */         case 90:
/*      */         case 91:
/* 4073 */           alt42 = 1;
/*      */ 
/* 4075 */           break;
/*      */         case 32:
/*      */         case 37:
/*      */         case 40:
/*      */         case 42:
/*      */         case 43:
/*      */         case 44:
/*      */         case 45:
/*      */         case 49:
/*      */         case 69:
/*      */         case 82:
/*      */         case 83:
/*      */         case 84:
/*      */         case 89:
/*      */         case 92:
/* 4091 */           alt42 = 2;
/*      */ 
/* 4093 */           break;
/*      */         case 33:
/*      */         case 34:
/*      */         case 35:
/*      */         case 36:
/*      */         case 38:
/*      */         case 39:
/*      */         case 41:
/*      */         case 46:
/*      */         case 47:
/*      */         case 48:
/*      */         case 50:
/*      */         case 51:
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
/*      */         case 70:
/*      */         case 71:
/*      */         case 72:
/*      */         case 73:
/*      */         case 75:
/*      */         case 76:
/*      */         case 77:
/*      */         case 78:
/*      */         case 79:
/*      */         case 80:
/*      */         case 81:
/*      */         case 85:
/*      */         case 86:
/*      */         case 87:
/*      */         case 88:
/*      */         default:
/* 4095 */           if (this.state.backtracking > 0) { this.state.failed = true; stream_labelOp = retval; return stream_labelOp; }
/* 4096 */           nvae = new NoViableAltException("", 42, 0, this.input);
/*      */ 
/* 4099 */           throw ((Throwable)nvae);
/*      */         }
/*      */ 
/* 4102 */         switch (alt42)
/*      */         {
/*      */         case 1:
/* 4106 */           pushFollow(FOLLOW_ebnfSuffix_in_elementNoOptionSpec1599);
/* 4107 */           ebnfSuffix98 = ebnfSuffix();
/*      */ 
/* 4109 */           this.state._fsp -= 1;
/* 4110 */           if (this.state.failed) { nvae = retval; return nvae; }
/* 4111 */           if (this.state.backtracking == 0) stream_ebnfSuffix.add(ebnfSuffix98.getTree());
/*      */ 
/* 4121 */           if (this.state.backtracking == 0) {
/* 4122 */             retval.tree = root_0;
/* 4123 */             RewriteRuleTokenStream stream_labelOp = new RewriteRuleTokenStream(this.adaptor, "token labelOp", labelOp);
/* 4124 */             RewriteRuleSubtreeStream stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.tree : null);
/*      */ 
/* 4126 */             root_0 = (CommonTree)this.adaptor.nil();
/*      */ 
/* 4131 */             CommonTree root_1 = (CommonTree)this.adaptor.nil();
/* 4132 */             root_1 = (CommonTree)this.adaptor.becomeRoot(stream_ebnfSuffix.nextNode(), root_1);
/*      */ 
/* 4136 */             CommonTree root_2 = (CommonTree)this.adaptor.nil();
/* 4137 */             root_2 = (CommonTree)this.adaptor.becomeRoot((CommonTree)this.adaptor.create(8, "BLOCK"), root_2);
/*      */ 
/* 4141 */             CommonTree root_3 = (CommonTree)this.adaptor.nil();
/* 4142 */             root_3 = (CommonTree)this.adaptor.becomeRoot((CommonTree)this.adaptor.create(16, "ALT"), root_3);
/*      */ 
/* 4146 */             CommonTree root_4 = (CommonTree)this.adaptor.nil();
/* 4147 */             root_4 = (CommonTree)this.adaptor.becomeRoot(stream_labelOp.nextNode(), root_4);
/*      */ 
/* 4149 */             this.adaptor.addChild(root_4, stream_id.nextTree());
/* 4150 */             this.adaptor.addChild(root_4, stream_block.nextTree());
/*      */ 
/* 4152 */             this.adaptor.addChild(root_3, root_4);
/*      */ 
/* 4154 */             this.adaptor.addChild(root_3, (CommonTree)this.adaptor.create(19, "EOA"));
/*      */ 
/* 4156 */             this.adaptor.addChild(root_2, root_3);
/*      */ 
/* 4158 */             this.adaptor.addChild(root_2, (CommonTree)this.adaptor.create(18, "EOB"));
/*      */ 
/* 4160 */             this.adaptor.addChild(root_1, root_2);
/*      */ 
/* 4163 */             this.adaptor.addChild(root_0, root_1);
/*      */ 
/* 4168 */             retval.tree = root_0; } break;
/*      */         case 2:
/* 4182 */           if (this.state.backtracking == 0) {
/* 4183 */             retval.tree = root_0;
/* 4184 */             RewriteRuleTokenStream stream_labelOp = new RewriteRuleTokenStream(this.adaptor, "token labelOp", labelOp);
/* 4185 */             RewriteRuleSubtreeStream stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.tree : null);
/*      */ 
/* 4187 */             root_0 = (CommonTree)this.adaptor.nil();
/*      */ 
/* 4192 */             CommonTree root_1 = (CommonTree)this.adaptor.nil();
/* 4193 */             root_1 = (CommonTree)this.adaptor.becomeRoot(stream_labelOp.nextNode(), root_1);
/*      */ 
/* 4195 */             this.adaptor.addChild(root_1, stream_id.nextTree());
/* 4196 */             this.adaptor.addChild(root_1, stream_block.nextTree());
/*      */ 
/* 4198 */             this.adaptor.addChild(root_0, root_1);
/*      */ 
/* 4203 */             retval.tree = root_0;
/*      */           }
/*      */ 
/*      */           break;
/*      */         }
/*      */ 
/* 4211 */         break;
/*      */       case 3:
/* 4215 */         pushFollow(FOLLOW_atom_in_elementNoOptionSpec1658);
/* 4216 */         atom99 = atom();
/*      */ 
/* 4218 */         this.state._fsp -= 1;
/* 4219 */         if (this.state.failed) { elementNoOptionSpec_return localelementNoOptionSpec_return3 = retval; return localelementNoOptionSpec_return3; }
/* 4220 */         if (this.state.backtracking == 0) stream_atom.add(atom99.getTree());
/*      */ 
/* 4222 */         alt43 = 2;
/*      */         Object nvae;
/* 4223 */         switch (this.input.LA(1))
/*      */         {
/*      */         case 74:
/*      */         case 90:
/*      */         case 91:
/* 4228 */           alt43 = 1;
/*      */ 
/* 4230 */           break;
/*      */         case 32:
/*      */         case 37:
/*      */         case 40:
/*      */         case 42:
/*      */         case 43:
/*      */         case 44:
/*      */         case 45:
/*      */         case 49:
/*      */         case 69:
/*      */         case 82:
/*      */         case 83:
/*      */         case 84:
/*      */         case 89:
/*      */         case 92:
/* 4246 */           alt43 = 2;
/*      */ 
/* 4248 */           break;
/*      */         case 33:
/*      */         case 34:
/*      */         case 35:
/*      */         case 36:
/*      */         case 38:
/*      */         case 39:
/*      */         case 41:
/*      */         case 46:
/*      */         case 47:
/*      */         case 48:
/*      */         case 50:
/*      */         case 51:
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
/*      */         case 70:
/*      */         case 71:
/*      */         case 72:
/*      */         case 73:
/*      */         case 75:
/*      */         case 76:
/*      */         case 77:
/*      */         case 78:
/*      */         case 79:
/*      */         case 80:
/*      */         case 81:
/*      */         case 85:
/*      */         case 86:
/*      */         case 87:
/*      */         case 88:
/*      */         default:
/* 4250 */           if (this.state.backtracking > 0) { this.state.failed = true; elementNoOptionSpec_return localelementNoOptionSpec_return8 = retval; return localelementNoOptionSpec_return8; }
/* 4251 */           nvae = new NoViableAltException("", 43, 0, this.input);
/*      */ 
/* 4254 */           throw ((Throwable)nvae);
/*      */         }
/*      */ 
/* 4257 */         switch (alt43)
/*      */         {
/*      */         case 1:
/* 4261 */           pushFollow(FOLLOW_ebnfSuffix_in_elementNoOptionSpec1664);
/* 4262 */           ebnfSuffix100 = ebnfSuffix();
/*      */ 
/* 4264 */           this.state._fsp -= 1;
/* 4265 */           if (this.state.failed) { nvae = retval; return nvae; }
/* 4266 */           if (this.state.backtracking == 0) stream_ebnfSuffix.add(ebnfSuffix100.getTree());
/*      */ 
/* 4276 */           if (this.state.backtracking == 0) {
/* 4277 */             retval.tree = root_0;
/* 4278 */             RewriteRuleSubtreeStream stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.tree : null);
/*      */ 
/* 4280 */             root_0 = (CommonTree)this.adaptor.nil();
/*      */ 
/* 4285 */             CommonTree root_1 = (CommonTree)this.adaptor.nil();
/* 4286 */             root_1 = (CommonTree)this.adaptor.becomeRoot(stream_ebnfSuffix.nextNode(), root_1);
/*      */ 
/* 4290 */             CommonTree root_2 = (CommonTree)this.adaptor.nil();
/* 4291 */             root_2 = (CommonTree)this.adaptor.becomeRoot((CommonTree)this.adaptor.create(8, "BLOCK"), root_2);
/*      */ 
/* 4295 */             CommonTree root_3 = (CommonTree)this.adaptor.nil();
/* 4296 */             root_3 = (CommonTree)this.adaptor.becomeRoot((CommonTree)this.adaptor.create(16, "ALT"), root_3);
/*      */ 
/* 4298 */             this.adaptor.addChild(root_3, stream_atom.nextTree());
/* 4299 */             this.adaptor.addChild(root_3, (CommonTree)this.adaptor.create(19, "EOA"));
/*      */ 
/* 4301 */             this.adaptor.addChild(root_2, root_3);
/*      */ 
/* 4303 */             this.adaptor.addChild(root_2, (CommonTree)this.adaptor.create(18, "EOB"));
/*      */ 
/* 4305 */             this.adaptor.addChild(root_1, root_2);
/*      */ 
/* 4308 */             this.adaptor.addChild(root_0, root_1);
/*      */ 
/* 4313 */             retval.tree = root_0; } break;
/*      */         case 2:
/* 4327 */           if (this.state.backtracking == 0) {
/* 4328 */             retval.tree = root_0;
/* 4329 */             stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.tree : null);
/*      */ 
/* 4331 */             root_0 = (CommonTree)this.adaptor.nil();
/*      */ 
/* 4334 */             this.adaptor.addChild(root_0, stream_atom.nextTree());
/*      */ 
/* 4338 */             retval.tree = root_0;
/*      */           }
/*      */ 
/*      */           break;
/*      */         }
/*      */ 
/* 4346 */         break;
/*      */       case 4:
/* 4350 */         root_0 = (CommonTree)this.adaptor.nil();
/*      */ 
/* 4352 */         pushFollow(FOLLOW_ebnf_in_elementNoOptionSpec1710);
/* 4353 */         ebnf101 = ebnf();
/*      */ 
/* 4355 */         this.state._fsp -= 1;
/* 4356 */         if (this.state.failed) { localelementNoOptionSpec_return4 = retval; return localelementNoOptionSpec_return4; }
/* 4357 */         if (this.state.backtracking == 0) this.adaptor.addChild(root_0, ebnf101.getTree()); break;
/*      */       case 5:
/* 4364 */         root_0 = (CommonTree)this.adaptor.nil();
/*      */ 
/* 4366 */         ACTION102 = (Token)match(this.input, 45, FOLLOW_ACTION_in_elementNoOptionSpec1717); if (this.state.failed) { localelementNoOptionSpec_return4 = retval; return localelementNoOptionSpec_return4; }
/* 4367 */         if (this.state.backtracking == 0) {
/* 4368 */           ACTION102_tree = (CommonTree)this.adaptor.create(ACTION102);
/* 4369 */           this.adaptor.addChild(root_0, ACTION102_tree); } break;
/*      */       case 6:
/* 4377 */         SEMPRED103 = (Token)match(this.input, 32, FOLLOW_SEMPRED_in_elementNoOptionSpec1724); if (this.state.failed) { localelementNoOptionSpec_return4 = retval; return localelementNoOptionSpec_return4; }
/* 4378 */         if (this.state.backtracking == 0) stream_SEMPRED.add(SEMPRED103);
/*      */ 
/* 4381 */         alt44 = 2;
/*      */         Object nvae;
/* 4382 */         switch (this.input.LA(1))
/*      */         {
/*      */         case 88:
/* 4385 */           alt44 = 1;
/*      */ 
/* 4387 */           break;
/*      */         case 32:
/*      */         case 37:
/*      */         case 40:
/*      */         case 42:
/*      */         case 43:
/*      */         case 44:
/*      */         case 45:
/*      */         case 49:
/*      */         case 69:
/*      */         case 82:
/*      */         case 83:
/*      */         case 84:
/*      */         case 89:
/*      */         case 92:
/* 4403 */           alt44 = 2;
/*      */ 
/* 4405 */           break;
/*      */         case 33:
/*      */         case 34:
/*      */         case 35:
/*      */         case 36:
/*      */         case 38:
/*      */         case 39:
/*      */         case 41:
/*      */         case 46:
/*      */         case 47:
/*      */         case 48:
/*      */         case 50:
/*      */         case 51:
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
/*      */         case 70:
/*      */         case 71:
/*      */         case 72:
/*      */         case 73:
/*      */         case 74:
/*      */         case 75:
/*      */         case 76:
/*      */         case 77:
/*      */         case 78:
/*      */         case 79:
/*      */         case 80:
/*      */         case 81:
/*      */         case 85:
/*      */         case 86:
/*      */         case 87:
/*      */         case 90:
/*      */         case 91:
/*      */         default:
/* 4407 */           if (this.state.backtracking > 0) { this.state.failed = true; stream_retval = retval; return stream_retval; }
/* 4408 */           nvae = new NoViableAltException("", 44, 0, this.input);
/*      */ 
/* 4411 */           throw ((Throwable)nvae);
/*      */         }
/*      */ 
/* 4414 */         switch (alt44)
/*      */         {
/*      */         case 1:
/* 4418 */           string_literal104 = (Token)match(this.input, 88, FOLLOW_88_in_elementNoOptionSpec1728); if (this.state.failed) { nvae = retval; return nvae; }
/* 4419 */           if (this.state.backtracking == 0) stream_88.add(string_literal104);
/*      */ 
/* 4430 */           if (this.state.backtracking == 0) {
/* 4431 */             retval.tree = root_0;
/* 4432 */             RewriteRuleSubtreeStream stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.tree : null);
/*      */ 
/* 4434 */             root_0 = (CommonTree)this.adaptor.nil();
/*      */ 
/* 4437 */             this.adaptor.addChild(root_0, (CommonTree)this.adaptor.create(33, "GATED_SEMPRED"));
/*      */ 
/* 4441 */             retval.tree = root_0; } break;
/*      */         case 2:
/* 4455 */           if (this.state.backtracking == 0) {
/* 4456 */             retval.tree = root_0;
/* 4457 */             stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.tree : null);
/*      */ 
/* 4459 */             root_0 = (CommonTree)this.adaptor.nil();
/*      */ 
/* 4462 */             this.adaptor.addChild(root_0, stream_SEMPRED.nextNode());
/*      */ 
/* 4466 */             retval.tree = root_0;
/*      */           }
/*      */ 
/*      */           break;
/*      */         }
/*      */ 
/* 4474 */         break;
/*      */       case 7:
/* 4478 */         pushFollow(FOLLOW_treeSpec_in_elementNoOptionSpec1747);
/* 4479 */         treeSpec105 = treeSpec();
/*      */ 
/* 4481 */         this.state._fsp -= 1;
/* 4482 */         if (this.state.failed) { elementNoOptionSpec_return localelementNoOptionSpec_return5 = retval; return localelementNoOptionSpec_return5; }
/* 4483 */         if (this.state.backtracking == 0) stream_treeSpec.add(treeSpec105.getTree());
/*      */ 
/* 4485 */         int alt45 = 2;
/*      */         Object nvae;
/* 4486 */         switch (this.input.LA(1))
/*      */         {
/*      */         case 74:
/*      */         case 90:
/*      */         case 91:
/* 4491 */           alt45 = 1;
/*      */ 
/* 4493 */           break;
/*      */         case 32:
/*      */         case 37:
/*      */         case 40:
/*      */         case 42:
/*      */         case 43:
/*      */         case 44:
/*      */         case 45:
/*      */         case 49:
/*      */         case 69:
/*      */         case 82:
/*      */         case 83:
/*      */         case 84:
/*      */         case 89:
/*      */         case 92:
/* 4509 */           alt45 = 2;
/*      */ 
/* 4511 */           break;
/*      */         case 33:
/*      */         case 34:
/*      */         case 35:
/*      */         case 36:
/*      */         case 38:
/*      */         case 39:
/*      */         case 41:
/*      */         case 46:
/*      */         case 47:
/*      */         case 48:
/*      */         case 50:
/*      */         case 51:
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
/*      */         case 70:
/*      */         case 71:
/*      */         case 72:
/*      */         case 73:
/*      */         case 75:
/*      */         case 76:
/*      */         case 77:
/*      */         case 78:
/*      */         case 79:
/*      */         case 80:
/*      */         case 81:
/*      */         case 85:
/*      */         case 86:
/*      */         case 87:
/*      */         case 88:
/*      */         default:
/* 4513 */           if (this.state.backtracking > 0) { this.state.failed = true; stream_retval = retval; return stream_retval; }
/* 4514 */           nvae = new NoViableAltException("", 45, 0, this.input);
/*      */ 
/* 4517 */           throw ((Throwable)nvae);
/*      */         }
/*      */ 
/* 4520 */         switch (alt45)
/*      */         {
/*      */         case 1:
/* 4524 */           pushFollow(FOLLOW_ebnfSuffix_in_elementNoOptionSpec1753);
/* 4525 */           ebnfSuffix106 = ebnfSuffix();
/*      */ 
/* 4527 */           this.state._fsp -= 1;
/* 4528 */           if (this.state.failed) { nvae = retval; return nvae; }
/* 4529 */           if (this.state.backtracking == 0) stream_ebnfSuffix.add(ebnfSuffix106.getTree());
/*      */ 
/* 4539 */           if (this.state.backtracking == 0) {
/* 4540 */             retval.tree = root_0;
/* 4541 */             RewriteRuleSubtreeStream stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.tree : null);
/*      */ 
/* 4543 */             root_0 = (CommonTree)this.adaptor.nil();
/*      */ 
/* 4548 */             CommonTree root_1 = (CommonTree)this.adaptor.nil();
/* 4549 */             root_1 = (CommonTree)this.adaptor.becomeRoot(stream_ebnfSuffix.nextNode(), root_1);
/*      */ 
/* 4553 */             CommonTree root_2 = (CommonTree)this.adaptor.nil();
/* 4554 */             root_2 = (CommonTree)this.adaptor.becomeRoot((CommonTree)this.adaptor.create(8, "BLOCK"), root_2);
/*      */ 
/* 4558 */             CommonTree root_3 = (CommonTree)this.adaptor.nil();
/* 4559 */             root_3 = (CommonTree)this.adaptor.becomeRoot((CommonTree)this.adaptor.create(16, "ALT"), root_3);
/*      */ 
/* 4561 */             this.adaptor.addChild(root_3, stream_treeSpec.nextTree());
/* 4562 */             this.adaptor.addChild(root_3, (CommonTree)this.adaptor.create(19, "EOA"));
/*      */ 
/* 4564 */             this.adaptor.addChild(root_2, root_3);
/*      */ 
/* 4566 */             this.adaptor.addChild(root_2, (CommonTree)this.adaptor.create(18, "EOB"));
/*      */ 
/* 4568 */             this.adaptor.addChild(root_1, root_2);
/*      */ 
/* 4571 */             this.adaptor.addChild(root_0, root_1);
/*      */ 
/* 4576 */             retval.tree = root_0; } break;
/*      */         case 2:
/* 4590 */           if (this.state.backtracking == 0) {
/* 4591 */             retval.tree = root_0;
/* 4592 */             RewriteRuleSubtreeStream stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.tree : null);
/*      */ 
/* 4594 */             root_0 = (CommonTree)this.adaptor.nil();
/*      */ 
/* 4597 */             this.adaptor.addChild(root_0, stream_treeSpec.nextTree());
/*      */ 
/* 4601 */             retval.tree = root_0;
/*      */           }
/*      */ 
/*      */           break;
/*      */         }
/*      */ 
/*      */         break;
/*      */       }
/*      */ 
/* 4612 */       retval.stop = this.input.LT(-1);
/*      */ 
/* 4614 */       if (this.state.backtracking == 0)
/*      */       {
/* 4616 */         retval.tree = ((CommonTree)this.adaptor.rulePostProcessing(root_0));
/* 4617 */         this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
/*      */       }
/*      */     } catch (RecognitionException re) {
/* 4620 */       re = 
/* 4627 */         re;
/*      */ 
/* 4621 */       reportError(re);
/* 4622 */       recover(this.input, re);
/* 4623 */       retval.tree = ((CommonTree)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), re));
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/* 4628 */     return retval;
/*      */   }
/*      */ 
/*      */   public final atom_return atom()
/*      */     throws RecognitionException
/*      */   {
/* 4640 */     atom_return retval = new atom_return();
/* 4641 */     retval.start = this.input.LT(1);
/*      */ 
/* 4643 */     CommonTree root_0 = null;
/*      */ 
/* 4645 */     Token op = null;
/* 4646 */     Token arg = null;
/* 4647 */     Token RULE_REF110 = null;
/* 4648 */     range_return range107 = null;
/*      */ 
/* 4650 */     terminal_return terminal108 = null;
/*      */ 
/* 4652 */     notSet_return notSet109 = null;
/*      */ 
/* 4655 */     CommonTree op_tree = null;
/* 4656 */     CommonTree arg_tree = null;
/* 4657 */     CommonTree RULE_REF110_tree = null;
/* 4658 */     RewriteRuleTokenStream stream_BANG = new RewriteRuleTokenStream(this.adaptor, "token BANG");
/* 4659 */     RewriteRuleTokenStream stream_ROOT = new RewriteRuleTokenStream(this.adaptor, "token ROOT");
/* 4660 */     RewriteRuleTokenStream stream_RULE_REF = new RewriteRuleTokenStream(this.adaptor, "token RULE_REF");
/* 4661 */     RewriteRuleTokenStream stream_ARG_ACTION = new RewriteRuleTokenStream(this.adaptor, "token ARG_ACTION");
/* 4662 */     RewriteRuleSubtreeStream stream_range = new RewriteRuleSubtreeStream(this.adaptor, "rule range");
/* 4663 */     RewriteRuleSubtreeStream stream_notSet = new RewriteRuleSubtreeStream(this.adaptor, "rule notSet");
/*      */     try
/*      */     {
/* 4666 */       int alt54 = 4;
/*      */       Object nvae;
/*      */       Object nvae;
/* 4667 */       switch (this.input.LA(1))
/*      */       {
/*      */       case 44:
/* 4670 */         switch (this.input.LA(2))
/*      */         {
/*      */         case 13:
/* 4673 */           alt54 = 1;
/*      */ 
/* 4675 */           break;
/*      */         case 32:
/*      */         case 37:
/*      */         case 38:
/*      */         case 39:
/*      */         case 40:
/*      */         case 42:
/*      */         case 43:
/*      */         case 44:
/*      */         case 45:
/*      */         case 49:
/*      */         case 69:
/*      */         case 74:
/*      */         case 82:
/*      */         case 83:
/*      */         case 84:
/*      */         case 89:
/*      */         case 90:
/*      */         case 91:
/*      */         case 92:
/* 4696 */           alt54 = 2;
/*      */ 
/* 4698 */           break;
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
/*      */         case 31:
/*      */         case 33:
/*      */         case 34:
/*      */         case 35:
/*      */         case 36:
/*      */         case 41:
/*      */         case 46:
/*      */         case 47:
/*      */         case 48:
/*      */         case 50:
/*      */         case 51:
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
/*      */         case 70:
/*      */         case 71:
/*      */         case 72:
/*      */         case 73:
/*      */         case 75:
/*      */         case 76:
/*      */         case 77:
/*      */         case 78:
/*      */         case 79:
/*      */         case 80:
/*      */         case 81:
/*      */         case 85:
/*      */         case 86:
/*      */         case 87:
/*      */         case 88:
/*      */         default:
/* 4700 */           if (this.state.backtracking > 0) { this.state.failed = true; atom_return localatom_return1 = retval; return localatom_return1; }
/* 4701 */           nvae = new NoViableAltException("", 54, 1, this.input);
/*      */ 
/* 4704 */           throw ((Throwable)nvae);
/*      */         }
/*      */ 
/* 4708 */         break;
/*      */       case 42:
/*      */       case 43:
/*      */       case 92:
/* 4713 */         alt54 = 2;
/*      */ 
/* 4715 */         break;
/*      */       case 89:
/* 4718 */         alt54 = 3;
/*      */ 
/* 4720 */         break;
/*      */       case 49:
/* 4723 */         alt54 = 4;
/*      */ 
/* 4725 */         break;
/*      */       default:
/* 4727 */         if (this.state.backtracking > 0) { this.state.failed = true; nvae = retval; return nvae; }
/* 4728 */         nvae = new NoViableAltException("", 54, 0, this.input);
/*      */ 
/* 4731 */         throw ((Throwable)nvae);
/*      */       }
/*      */       int alt48;
/*      */       Object stream_op;
/*      */       Object stream_retval;
/*      */       atom_return localatom_return2;
/*      */       int alt50;
/*      */       RewriteRuleSubtreeStream stream_retval;
/*      */       Object stream_retval;
/* 4734 */       switch (alt54)
/*      */       {
/*      */       case 1:
/* 4738 */         pushFollow(FOLLOW_range_in_atom1805);
/* 4739 */         range107 = range();
/*      */ 
/* 4741 */         this.state._fsp -= 1;
/* 4742 */         if (this.state.failed) { nvae = retval; return nvae; }
/* 4743 */         if (this.state.backtracking == 0) stream_range.add(range107.getTree());
/*      */ 
/* 4745 */         alt48 = 2;
/* 4746 */         switch (this.input.LA(1))
/*      */         {
/*      */         case 38:
/*      */         case 39:
/* 4750 */           alt48 = 1;
/*      */ 
/* 4752 */           break;
/*      */         case 32:
/*      */         case 37:
/*      */         case 40:
/*      */         case 42:
/*      */         case 43:
/*      */         case 44:
/*      */         case 45:
/*      */         case 49:
/*      */         case 69:
/*      */         case 74:
/*      */         case 82:
/*      */         case 83:
/*      */         case 84:
/*      */         case 89:
/*      */         case 90:
/*      */         case 91:
/*      */         case 92:
/* 4771 */           alt48 = 2;
/*      */ 
/* 4773 */           break;
/*      */         case 33:
/*      */         case 34:
/*      */         case 35:
/*      */         case 36:
/*      */         case 41:
/*      */         case 46:
/*      */         case 47:
/*      */         case 48:
/*      */         case 50:
/*      */         case 51:
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
/*      */         case 70:
/*      */         case 71:
/*      */         case 72:
/*      */         case 73:
/*      */         case 75:
/*      */         case 76:
/*      */         case 77:
/*      */         case 78:
/*      */         case 79:
/*      */         case 80:
/*      */         case 81:
/*      */         case 85:
/*      */         case 86:
/*      */         case 87:
/*      */         case 88:
/*      */         default:
/* 4775 */           if (this.state.backtracking > 0) { this.state.failed = true; atom_return localatom_return4 = retval; return localatom_return4; }
/* 4776 */           NoViableAltException nvae = new NoViableAltException("", 48, 0, this.input);
/*      */ 
/* 4779 */           throw nvae;
/*      */         }
/*      */ 
/* 4782 */         switch (alt48)
/*      */         {
/*      */         case 1:
/* 4787 */           int alt47 = 2;
/*      */           Object nvae;
/* 4788 */           switch (this.input.LA(1))
/*      */           {
/*      */           case 38:
/* 4791 */             alt47 = 1;
/*      */ 
/* 4793 */             break;
/*      */           case 39:
/* 4796 */             alt47 = 2;
/*      */ 
/* 4798 */             break;
/*      */           default:
/* 4800 */             if (this.state.backtracking > 0) { this.state.failed = true; atom_return localatom_return5 = retval; return localatom_return5; }
/* 4801 */             nvae = new NoViableAltException("", 47, 0, this.input);
/*      */ 
/* 4804 */             throw ((Throwable)nvae);
/*      */           }
/*      */ 
/* 4807 */           switch (alt47)
/*      */           {
/*      */           case 1:
/* 4811 */             op = (Token)match(this.input, 38, FOLLOW_ROOT_in_atom1812); if (this.state.failed) { nvae = retval; return nvae; }
/* 4812 */             if (this.state.backtracking == 0) stream_ROOT.add(op); break;
/*      */           case 2:
/* 4820 */             op = (Token)match(this.input, 39, FOLLOW_BANG_in_atom1816); if (this.state.failed) { nvae = retval; return nvae; }
/* 4821 */             if (this.state.backtracking == 0) stream_BANG.add(op);
/*      */ 
/*      */             break;
/*      */           }
/*      */ 
/* 4838 */           if (this.state.backtracking == 0) {
/* 4839 */             retval.tree = root_0;
/* 4840 */             stream_op = new RewriteRuleTokenStream(this.adaptor, "token op", op);
/* 4841 */             RewriteRuleSubtreeStream stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.tree : null);
/*      */ 
/* 4843 */             root_0 = (CommonTree)this.adaptor.nil();
/*      */ 
/* 4848 */             CommonTree root_1 = (CommonTree)this.adaptor.nil();
/* 4849 */             root_1 = (CommonTree)this.adaptor.becomeRoot(((RewriteRuleTokenStream)stream_op).nextNode(), root_1);
/*      */ 
/* 4851 */             this.adaptor.addChild(root_1, stream_range.nextTree());
/*      */ 
/* 4853 */             this.adaptor.addChild(root_0, root_1);
/*      */ 
/* 4858 */             retval.tree = root_0;
/*      */           }
/* 4860 */           break;
/*      */         case 2:
/* 4872 */           if (this.state.backtracking == 0) {
/* 4873 */             retval.tree = root_0;
/* 4874 */             stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.tree : null);
/*      */ 
/* 4876 */             root_0 = (CommonTree)this.adaptor.nil();
/*      */ 
/* 4879 */             this.adaptor.addChild(root_0, stream_range.nextTree());
/*      */ 
/* 4883 */             retval.tree = root_0;
/*      */           }
/*      */ 
/*      */           break;
/*      */         }
/*      */ 
/* 4891 */         break;
/*      */       case 2:
/* 4895 */         root_0 = (CommonTree)this.adaptor.nil();
/*      */ 
/* 4897 */         pushFollow(FOLLOW_terminal_in_atom1844);
/* 4898 */         terminal108 = terminal();
/*      */ 
/* 4900 */         this.state._fsp -= 1;
/* 4901 */         if (this.state.failed) { localatom_return2 = retval; return localatom_return2; }
/* 4902 */         if (this.state.backtracking == 0) this.adaptor.addChild(root_0, terminal108.getTree()); break;
/*      */       case 3:
/* 4909 */         pushFollow(FOLLOW_notSet_in_atom1852);
/* 4910 */         notSet109 = notSet();
/*      */ 
/* 4912 */         this.state._fsp -= 1;
/* 4913 */         if (this.state.failed) { localatom_return2 = retval; return localatom_return2; }
/* 4914 */         if (this.state.backtracking == 0) stream_notSet.add(notSet109.getTree());
/*      */ 
/* 4916 */         alt50 = 2;
/* 4917 */         switch (this.input.LA(1))
/*      */         {
/*      */         case 38:
/*      */         case 39:
/* 4921 */           alt50 = 1;
/*      */ 
/* 4923 */           break;
/*      */         case 32:
/*      */         case 37:
/*      */         case 40:
/*      */         case 42:
/*      */         case 43:
/*      */         case 44:
/*      */         case 45:
/*      */         case 49:
/*      */         case 69:
/*      */         case 74:
/*      */         case 82:
/*      */         case 83:
/*      */         case 84:
/*      */         case 89:
/*      */         case 90:
/*      */         case 91:
/*      */         case 92:
/* 4942 */           alt50 = 2;
/*      */ 
/* 4944 */           break;
/*      */         case 33:
/*      */         case 34:
/*      */         case 35:
/*      */         case 36:
/*      */         case 41:
/*      */         case 46:
/*      */         case 47:
/*      */         case 48:
/*      */         case 50:
/*      */         case 51:
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
/*      */         case 70:
/*      */         case 71:
/*      */         case 72:
/*      */         case 73:
/*      */         case 75:
/*      */         case 76:
/*      */         case 77:
/*      */         case 78:
/*      */         case 79:
/*      */         case 80:
/*      */         case 81:
/*      */         case 85:
/*      */         case 86:
/*      */         case 87:
/*      */         case 88:
/*      */         default:
/* 4946 */           if (this.state.backtracking > 0) { this.state.failed = true; stream_retval = retval; return stream_retval; }
/* 4947 */           NoViableAltException nvae = new NoViableAltException("", 50, 0, this.input);
/*      */ 
/* 4950 */           throw nvae;
/*      */         }
/*      */ 
/* 4953 */         switch (alt50)
/*      */         {
/*      */         case 1:
/* 4958 */           int alt49 = 2;
/*      */           Object nvae;
/* 4959 */           switch (this.input.LA(1))
/*      */           {
/*      */           case 38:
/* 4962 */             alt49 = 1;
/*      */ 
/* 4964 */             break;
/*      */           case 39:
/* 4967 */             alt49 = 2;
/*      */ 
/* 4969 */             break;
/*      */           default:
/* 4971 */             if (this.state.backtracking > 0) { this.state.failed = true; stream_op = retval; return stream_op; }
/* 4972 */             nvae = new NoViableAltException("", 49, 0, this.input);
/*      */ 
/* 4975 */             throw ((Throwable)nvae);
/*      */           }
/*      */ 
/* 4978 */           switch (alt49)
/*      */           {
/*      */           case 1:
/* 4982 */             op = (Token)match(this.input, 38, FOLLOW_ROOT_in_atom1859); if (this.state.failed) { nvae = retval; return nvae; }
/* 4983 */             if (this.state.backtracking == 0) stream_ROOT.add(op); break;
/*      */           case 2:
/* 4991 */             op = (Token)match(this.input, 39, FOLLOW_BANG_in_atom1863); if (this.state.failed) { nvae = retval; return nvae; }
/* 4992 */             if (this.state.backtracking == 0) stream_BANG.add(op);
/*      */ 
/*      */             break;
/*      */           }
/*      */ 
/* 5009 */           if (this.state.backtracking == 0) {
/* 5010 */             retval.tree = root_0;
/* 5011 */             RewriteRuleTokenStream stream_op = new RewriteRuleTokenStream(this.adaptor, "token op", op);
/* 5012 */             stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.tree : null);
/*      */ 
/* 5014 */             root_0 = (CommonTree)this.adaptor.nil();
/*      */ 
/* 5019 */             CommonTree root_1 = (CommonTree)this.adaptor.nil();
/* 5020 */             root_1 = (CommonTree)this.adaptor.becomeRoot(stream_op.nextNode(), root_1);
/*      */ 
/* 5022 */             this.adaptor.addChild(root_1, stream_notSet.nextTree());
/*      */ 
/* 5024 */             this.adaptor.addChild(root_0, root_1);
/*      */ 
/* 5029 */             retval.tree = root_0;
/*      */           }
/* 5031 */           break;
/*      */         case 2:
/* 5043 */           if (this.state.backtracking == 0) {
/* 5044 */             retval.tree = root_0;
/* 5045 */             stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.tree : null);
/*      */ 
/* 5047 */             root_0 = (CommonTree)this.adaptor.nil();
/*      */ 
/* 5050 */             this.adaptor.addChild(root_0, stream_notSet.nextTree());
/*      */ 
/* 5054 */             retval.tree = root_0;
/*      */           }
/*      */ 
/*      */           break;
/*      */         }
/*      */ 
/* 5062 */         break;
/*      */       case 4:
/* 5066 */         RULE_REF110 = (Token)match(this.input, 49, FOLLOW_RULE_REF_in_atom1891); if (this.state.failed) { atom_return localatom_return3 = retval; return localatom_return3; }
/* 5067 */         if (this.state.backtracking == 0) stream_RULE_REF.add(RULE_REF110);
/*      */ 
/* 5070 */         int alt51 = 2;
/* 5071 */         switch (this.input.LA(1))
/*      */         {
/*      */         case 48:
/* 5074 */           alt51 = 1;
/*      */         }
/*      */ 
/* 5079 */         switch (alt51)
/*      */         {
/*      */         case 1:
/* 5083 */           arg = (Token)match(this.input, 48, FOLLOW_ARG_ACTION_in_atom1897); if (this.state.failed) { stream_retval = retval; return stream_retval; }
/* 5084 */           if (this.state.backtracking == 0) stream_ARG_ACTION.add(arg);
/*      */ 
/*      */           break;
/*      */         }
/*      */ 
/* 5093 */         int alt53 = 2;
/* 5094 */         switch (this.input.LA(1))
/*      */         {
/*      */         case 38:
/*      */         case 39:
/* 5098 */           alt53 = 1;
/*      */         }
/*      */ 
/* 5103 */         switch (alt53)
/*      */         {
/*      */         case 1:
/* 5108 */           int alt52 = 2;
/*      */           NoViableAltException nvae;
/* 5109 */           switch (this.input.LA(1))
/*      */           {
/*      */           case 38:
/* 5112 */             alt52 = 1;
/*      */ 
/* 5114 */             break;
/*      */           case 39:
/* 5117 */             alt52 = 2;
/*      */ 
/* 5119 */             break;
/*      */           default:
/* 5121 */             if (this.state.backtracking > 0) { this.state.failed = true; stream_retval = retval; return stream_retval; }
/* 5122 */             nvae = new NoViableAltException("", 52, 0, this.input);
/*      */ 
/* 5125 */             throw nvae;
/*      */           }
/*      */ 
/* 5128 */           switch (alt52)
/*      */           {
/*      */           case 1:
/* 5132 */             op = (Token)match(this.input, 38, FOLLOW_ROOT_in_atom1907); if (this.state.failed) { nvae = retval; return nvae; }
/* 5133 */             if (this.state.backtracking == 0) stream_ROOT.add(op); break;
/*      */           case 2:
/* 5141 */             op = (Token)match(this.input, 39, FOLLOW_BANG_in_atom1911); if (this.state.failed) { nvae = retval; return nvae; }
/* 5142 */             if (this.state.backtracking == 0) stream_BANG.add(op);
/*      */ 
/*      */             break;
/*      */           }
/*      */ 
/*      */           break;
/*      */         }
/*      */ 
/* 5165 */         if (this.state.backtracking == 0) {
/* 5166 */           retval.tree = root_0;
/* 5167 */           RewriteRuleTokenStream stream_arg = new RewriteRuleTokenStream(this.adaptor, "token arg", arg);
/* 5168 */           RewriteRuleTokenStream stream_op = new RewriteRuleTokenStream(this.adaptor, "token op", op);
/* 5169 */           RewriteRuleSubtreeStream stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.tree : null);
/*      */ 
/* 5171 */           root_0 = (CommonTree)this.adaptor.nil();
/*      */ 
/* 5173 */           if ((arg != null) && (op != null))
/*      */           {
/* 5176 */             CommonTree root_1 = (CommonTree)this.adaptor.nil();
/* 5177 */             root_1 = (CommonTree)this.adaptor.becomeRoot(stream_op.nextNode(), root_1);
/*      */ 
/* 5179 */             this.adaptor.addChild(root_1, stream_RULE_REF.nextNode());
/* 5180 */             this.adaptor.addChild(root_1, stream_arg.nextNode());
/*      */ 
/* 5182 */             this.adaptor.addChild(root_0, root_1);
/*      */           }
/* 5187 */           else if (arg != null)
/*      */           {
/* 5190 */             CommonTree root_1 = (CommonTree)this.adaptor.nil();
/* 5191 */             root_1 = (CommonTree)this.adaptor.becomeRoot(stream_RULE_REF.nextNode(), root_1);
/*      */ 
/* 5193 */             this.adaptor.addChild(root_1, stream_arg.nextNode());
/*      */ 
/* 5195 */             this.adaptor.addChild(root_0, root_1);
/*      */           }
/* 5200 */           else if (op != null)
/*      */           {
/* 5203 */             CommonTree root_1 = (CommonTree)this.adaptor.nil();
/* 5204 */             root_1 = (CommonTree)this.adaptor.becomeRoot(stream_op.nextNode(), root_1);
/*      */ 
/* 5206 */             this.adaptor.addChild(root_1, stream_RULE_REF.nextNode());
/*      */ 
/* 5208 */             this.adaptor.addChild(root_0, root_1);
/*      */           }
/*      */           else
/*      */           {
/* 5214 */             this.adaptor.addChild(root_0, stream_RULE_REF.nextNode());
/*      */           }
/*      */ 
/* 5218 */           retval.tree = root_0;
/*      */         }
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
/* 5256 */     Token char_literal111 = null;
/* 5257 */     notTerminal_return notTerminal112 = null;
/*      */ 
/* 5259 */     block_return block113 = null;
/*      */ 
/* 5262 */     CommonTree char_literal111_tree = null;
/* 5263 */     RewriteRuleTokenStream stream_89 = new RewriteRuleTokenStream(this.adaptor, "token 89");
/* 5264 */     RewriteRuleSubtreeStream stream_notTerminal = new RewriteRuleSubtreeStream(this.adaptor, "rule notTerminal");
/* 5265 */     RewriteRuleSubtreeStream stream_block = new RewriteRuleSubtreeStream(this.adaptor, "rule block");
/*      */     try
/*      */     {
/* 5270 */       char_literal111 = (Token)match(this.input, 89, FOLLOW_89_in_notSet1994); if (this.state.failed) { notSet_return localnotSet_return1 = retval; return localnotSet_return1; }
/* 5271 */       if (this.state.backtracking == 0) stream_89.add(char_literal111);
/*      */ 
/* 5274 */       int alt55 = 2;
/*      */       Object nvae;
/* 5275 */       switch (this.input.LA(1))
/*      */       {
/*      */       case 42:
/*      */       case 43:
/*      */       case 44:
/* 5280 */         alt55 = 1;
/*      */ 
/* 5282 */         break;
/*      */       case 82:
/* 5285 */         alt55 = 2;
/*      */ 
/* 5287 */         break;
/*      */       default:
/* 5289 */         if (this.state.backtracking > 0) { this.state.failed = true; notSet_return localnotSet_return2 = retval; return localnotSet_return2; }
/* 5290 */         nvae = new NoViableAltException("", 55, 0, this.input);
/*      */ 
/* 5293 */         throw ((Throwable)nvae);
/*      */       }
/*      */       Object stream_retval;
/* 5296 */       switch (alt55)
/*      */       {
/*      */       case 1:
/* 5300 */         pushFollow(FOLLOW_notTerminal_in_notSet2000);
/* 5301 */         notTerminal112 = notTerminal();
/*      */ 
/* 5303 */         this.state._fsp -= 1;
/* 5304 */         if (this.state.failed) { nvae = retval; return nvae; }
/* 5305 */         if (this.state.backtracking == 0) stream_notTerminal.add(notTerminal112.getTree());
/*      */ 
/* 5315 */         if (this.state.backtracking == 0) {
/* 5316 */           retval.tree = root_0;
/* 5317 */           stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.tree : null);
/*      */ 
/* 5319 */           root_0 = (CommonTree)this.adaptor.nil();
/*      */ 
/* 5324 */           CommonTree root_1 = (CommonTree)this.adaptor.nil();
/* 5325 */           root_1 = (CommonTree)this.adaptor.becomeRoot(stream_89.nextNode(), root_1);
/*      */ 
/* 5327 */           this.adaptor.addChild(root_1, stream_notTerminal.nextTree());
/*      */ 
/* 5329 */           this.adaptor.addChild(root_0, root_1);
/*      */ 
/* 5334 */           retval.tree = root_0; } break;
/*      */       case 2:
/* 5340 */         pushFollow(FOLLOW_block_in_notSet2014);
/* 5341 */         block113 = block();
/*      */ 
/* 5343 */         this.state._fsp -= 1;
/* 5344 */         if (this.state.failed) { stream_retval = retval; return stream_retval; }
/* 5345 */         if (this.state.backtracking == 0) stream_block.add(block113.getTree());
/*      */ 
/* 5355 */         if (this.state.backtracking == 0) {
/* 5356 */           retval.tree = root_0;
/* 5357 */           RewriteRuleSubtreeStream stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.tree : null);
/*      */ 
/* 5359 */           root_0 = (CommonTree)this.adaptor.nil();
/*      */ 
/* 5364 */           CommonTree root_1 = (CommonTree)this.adaptor.nil();
/* 5365 */           root_1 = (CommonTree)this.adaptor.becomeRoot(stream_89.nextNode(), root_1);
/*      */ 
/* 5367 */           this.adaptor.addChild(root_1, stream_block.nextTree());
/*      */ 
/* 5369 */           this.adaptor.addChild(root_0, root_1);
/*      */ 
/* 5374 */           retval.tree = root_0;
/*      */         }
/*      */ 
/*      */         break;
/*      */       }
/*      */ 
/* 5383 */       retval.stop = this.input.LT(-1);
/*      */ 
/* 5385 */       if (this.state.backtracking == 0)
/*      */       {
/* 5387 */         retval.tree = ((CommonTree)this.adaptor.rulePostProcessing(root_0));
/* 5388 */         this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
/*      */       }
/*      */     } catch (RecognitionException re) {
/* 5391 */       re = 
/* 5398 */         re;
/*      */ 
/* 5392 */       reportError(re);
/* 5393 */       recover(this.input, re);
/* 5394 */       retval.tree = ((CommonTree)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), re));
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/* 5399 */     return retval;
/*      */   }
/*      */ 
/*      */   public final treeSpec_return treeSpec()
/*      */     throws RecognitionException
/*      */   {
/* 5411 */     treeSpec_return retval = new treeSpec_return();
/* 5412 */     retval.start = this.input.LT(1);
/*      */ 
/* 5414 */     CommonTree root_0 = null;
/*      */ 
/* 5416 */     Token string_literal114 = null;
/* 5417 */     Token char_literal117 = null;
/* 5418 */     element_return element115 = null;
/*      */ 
/* 5420 */     element_return element116 = null;
/*      */ 
/* 5423 */     CommonTree string_literal114_tree = null;
/* 5424 */     CommonTree char_literal117_tree = null;
/* 5425 */     RewriteRuleTokenStream stream_TREE_BEGIN = new RewriteRuleTokenStream(this.adaptor, "token TREE_BEGIN");
/* 5426 */     RewriteRuleTokenStream stream_84 = new RewriteRuleTokenStream(this.adaptor, "token 84");
/* 5427 */     RewriteRuleSubtreeStream stream_element = new RewriteRuleSubtreeStream(this.adaptor, "rule element");
/*      */     try
/*      */     {
/* 5432 */       string_literal114 = (Token)match(this.input, 37, FOLLOW_TREE_BEGIN_in_treeSpec2038);
/*      */       treeSpec_return localtreeSpec_return1;
/* 5432 */       if (this.state.failed) { localtreeSpec_return1 = retval; return localtreeSpec_return1; }
/* 5433 */       if (this.state.backtracking == 0) stream_TREE_BEGIN.add(string_literal114);
/*      */ 
/* 5435 */       pushFollow(FOLLOW_element_in_treeSpec2040);
/* 5436 */       element115 = element();
/*      */ 
/* 5438 */       this.state._fsp -= 1;
/* 5439 */       if (this.state.failed) { localtreeSpec_return1 = retval; return localtreeSpec_return1; }
/* 5440 */       if (this.state.backtracking == 0) stream_element.add(element115.getTree()); 
/*      */ int cnt56 = 0;
/*      */       int alt56;
/*      */       while (true) {
/* 5445 */         alt56 = 2;
/* 5446 */         switch (this.input.LA(1))
/*      */         {
/*      */         case 32:
/*      */         case 37:
/*      */         case 42:
/*      */         case 43:
/*      */         case 44:
/*      */         case 45:
/*      */         case 49:
/*      */         case 82:
/*      */         case 89:
/*      */         case 92:
/* 5458 */           alt56 = 1;
/*      */         }
/*      */         treeSpec_return localtreeSpec_return2;
/* 5464 */         switch (alt56)
/*      */         {
/*      */         case 1:
/* 5468 */           pushFollow(FOLLOW_element_in_treeSpec2044);
/* 5469 */           element116 = element();
/*      */ 
/* 5471 */           this.state._fsp -= 1;
/* 5472 */           if (this.state.failed) { localtreeSpec_return2 = retval; return localtreeSpec_return2; }
/* 5473 */           if (this.state.backtracking == 0) stream_element.add(element116.getTree()); break;
/*      */         default:
/* 5479 */           if (cnt56 >= 1) break label454;
/* 5480 */           if (this.state.backtracking > 0) { this.state.failed = true; localtreeSpec_return2 = retval; return localtreeSpec_return2; }
/* 5481 */           EarlyExitException eee = new EarlyExitException(56, this.input);
/*      */ 
/* 5483 */           throw eee;
/*      */         }
/* 5485 */         cnt56++;
/*      */       }
/*      */ 
/* 5488 */       label454: char_literal117 = (Token)match(this.input, 84, FOLLOW_84_in_treeSpec2049); if (this.state.failed) { alt56 = retval; return alt56; }
/* 5489 */       if (this.state.backtracking == 0) stream_84.add(char_literal117);
/*      */ 
/* 5500 */       if (this.state.backtracking == 0) {
/* 5501 */         retval.tree = root_0;
/* 5502 */         RewriteRuleSubtreeStream stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.tree : null);
/*      */ 
/* 5504 */         root_0 = (CommonTree)this.adaptor.nil();
/*      */ 
/* 5509 */         CommonTree root_1 = (CommonTree)this.adaptor.nil();
/* 5510 */         root_1 = (CommonTree)this.adaptor.becomeRoot((CommonTree)this.adaptor.create(37, "TREE_BEGIN"), root_1);
/*      */ 
/* 5512 */         if (!stream_element.hasNext()) {
/* 5513 */           throw new RewriteEarlyExitException();
/*      */         }
/* 5515 */         while (stream_element.hasNext()) {
/* 5516 */           this.adaptor.addChild(root_1, stream_element.nextTree());
/*      */         }
/*      */ 
/* 5519 */         stream_element.reset();
/*      */ 
/* 5521 */         this.adaptor.addChild(root_0, root_1);
/*      */ 
/* 5526 */         retval.tree = root_0;
/*      */       }
/*      */ 
/* 5529 */       retval.stop = this.input.LT(-1);
/*      */ 
/* 5531 */       if (this.state.backtracking == 0)
/*      */       {
/* 5533 */         retval.tree = ((CommonTree)this.adaptor.rulePostProcessing(root_0));
/* 5534 */         this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
/*      */       }
/*      */     } catch (RecognitionException re) {
/* 5537 */       re = 
/* 5544 */         re;
/*      */ 
/* 5538 */       reportError(re);
/* 5539 */       recover(this.input, re);
/* 5540 */       retval.tree = ((CommonTree)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), re));
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/* 5545 */     return retval;
/*      */   }
/*      */ 
/*      */   public final ebnf_return ebnf()
/*      */     throws RecognitionException
/*      */   {
/* 5557 */     ebnf_return retval = new ebnf_return();
/* 5558 */     retval.start = this.input.LT(1);
/*      */ 
/* 5560 */     CommonTree root_0 = null;
/*      */ 
/* 5562 */     Token op = null;
/* 5563 */     Token string_literal119 = null;
/* 5564 */     block_return block118 = null;
/*      */ 
/* 5567 */     CommonTree op_tree = null;
/* 5568 */     CommonTree string_literal119_tree = null;
/* 5569 */     RewriteRuleTokenStream stream_91 = new RewriteRuleTokenStream(this.adaptor, "token 91");
/* 5570 */     RewriteRuleTokenStream stream_90 = new RewriteRuleTokenStream(this.adaptor, "token 90");
/* 5571 */     RewriteRuleTokenStream stream_74 = new RewriteRuleTokenStream(this.adaptor, "token 74");
/* 5572 */     RewriteRuleTokenStream stream_88 = new RewriteRuleTokenStream(this.adaptor, "token 88");
/* 5573 */     RewriteRuleSubtreeStream stream_block = new RewriteRuleSubtreeStream(this.adaptor, "rule block");
/*      */ 
/* 5575 */     Token firstToken = this.input.LT(1);
/*      */     try
/*      */     {
/* 5581 */       pushFollow(FOLLOW_block_in_ebnf2081);
/* 5582 */       block118 = block();
/*      */ 
/* 5584 */       this.state._fsp -= 1;
/* 5585 */       if (this.state.failed) { ebnf_return localebnf_return1 = retval; return localebnf_return1; }
/* 5586 */       if (this.state.backtracking == 0) stream_block.add(block118.getTree());
/*      */ 
/* 5588 */       int alt57 = 5;
/*      */       Object nvae;
/* 5589 */       switch (this.input.LA(1))
/*      */       {
/*      */       case 90:
/* 5592 */         alt57 = 1;
/*      */ 
/* 5594 */         break;
/*      */       case 74:
/* 5597 */         alt57 = 2;
/*      */ 
/* 5599 */         break;
/*      */       case 91:
/* 5602 */         alt57 = 3;
/*      */ 
/* 5604 */         break;
/*      */       case 88:
/* 5607 */         alt57 = 4;
/*      */ 
/* 5609 */         break;
/*      */       case 32:
/*      */       case 37:
/*      */       case 40:
/*      */       case 42:
/*      */       case 43:
/*      */       case 44:
/*      */       case 45:
/*      */       case 49:
/*      */       case 69:
/*      */       case 82:
/*      */       case 83:
/*      */       case 84:
/*      */       case 89:
/*      */       case 92:
/* 5625 */         alt57 = 5;
/*      */ 
/* 5627 */         break;
/*      */       case 33:
/*      */       case 34:
/*      */       case 35:
/*      */       case 36:
/*      */       case 38:
/*      */       case 39:
/*      */       case 41:
/*      */       case 46:
/*      */       case 47:
/*      */       case 48:
/*      */       case 50:
/*      */       case 51:
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
/*      */       case 70:
/*      */       case 71:
/*      */       case 72:
/*      */       case 73:
/*      */       case 75:
/*      */       case 76:
/*      */       case 77:
/*      */       case 78:
/*      */       case 79:
/*      */       case 80:
/*      */       case 81:
/*      */       case 85:
/*      */       case 86:
/*      */       case 87:
/*      */       default:
/* 5629 */         if (this.state.backtracking > 0) { this.state.failed = true; ebnf_return localebnf_return2 = retval; return localebnf_return2; }
/* 5630 */         nvae = new NoViableAltException("", 57, 0, this.input);
/*      */ 
/* 5633 */         throw ((Throwable)nvae);
/*      */       }
/*      */       Object stream_retval;
/*      */       Object stream_retval;
/*      */       Object stream_retval;
/* 5636 */       switch (alt57)
/*      */       {
/*      */       case 1:
/* 5640 */         op = (Token)match(this.input, 90, FOLLOW_90_in_ebnf2089); if (this.state.failed) { nvae = retval; return nvae; }
/* 5641 */         if (this.state.backtracking == 0) stream_90.add(op);
/*      */ 
/* 5652 */         if (this.state.backtracking == 0) {
/* 5653 */           retval.tree = root_0;
/* 5654 */           stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.tree : null);
/*      */ 
/* 5656 */           root_0 = (CommonTree)this.adaptor.nil();
/*      */ 
/* 5661 */           CommonTree root_1 = (CommonTree)this.adaptor.nil();
/* 5662 */           root_1 = (CommonTree)this.adaptor.becomeRoot((CommonTree)this.adaptor.create(9, op), root_1);
/*      */ 
/* 5664 */           this.adaptor.addChild(root_1, stream_block.nextTree());
/*      */ 
/* 5666 */           this.adaptor.addChild(root_0, root_1);
/*      */ 
/* 5671 */           retval.tree = root_0; } break;
/*      */       case 2:
/* 5677 */         op = (Token)match(this.input, 74, FOLLOW_74_in_ebnf2106); if (this.state.failed) { stream_retval = retval; return stream_retval; }
/* 5678 */         if (this.state.backtracking == 0) stream_74.add(op);
/*      */ 
/* 5689 */         if (this.state.backtracking == 0) {
/* 5690 */           retval.tree = root_0;
/* 5691 */           stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.tree : null);
/*      */ 
/* 5693 */           root_0 = (CommonTree)this.adaptor.nil();
/*      */ 
/* 5698 */           CommonTree root_1 = (CommonTree)this.adaptor.nil();
/* 5699 */           root_1 = (CommonTree)this.adaptor.becomeRoot((CommonTree)this.adaptor.create(10, op), root_1);
/*      */ 
/* 5701 */           this.adaptor.addChild(root_1, stream_block.nextTree());
/*      */ 
/* 5703 */           this.adaptor.addChild(root_0, root_1);
/*      */ 
/* 5708 */           retval.tree = root_0; } break;
/*      */       case 3:
/* 5714 */         op = (Token)match(this.input, 91, FOLLOW_91_in_ebnf2123); if (this.state.failed) { stream_retval = retval; return stream_retval; }
/* 5715 */         if (this.state.backtracking == 0) stream_91.add(op);
/*      */ 
/* 5726 */         if (this.state.backtracking == 0) {
/* 5727 */           retval.tree = root_0;
/* 5728 */           stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.tree : null);
/*      */ 
/* 5730 */           root_0 = (CommonTree)this.adaptor.nil();
/*      */ 
/* 5735 */           CommonTree root_1 = (CommonTree)this.adaptor.nil();
/* 5736 */           root_1 = (CommonTree)this.adaptor.becomeRoot((CommonTree)this.adaptor.create(11, op), root_1);
/*      */ 
/* 5738 */           this.adaptor.addChild(root_1, stream_block.nextTree());
/*      */ 
/* 5740 */           this.adaptor.addChild(root_0, root_1);
/*      */ 
/* 5745 */           retval.tree = root_0; } break;
/*      */       case 4:
/* 5751 */         string_literal119 = (Token)match(this.input, 88, FOLLOW_88_in_ebnf2140); if (this.state.failed) { stream_retval = retval; return stream_retval; }
/* 5752 */         if (this.state.backtracking == 0) stream_88.add(string_literal119);
/*      */ 
/* 5763 */         if (this.state.backtracking == 0) {
/* 5764 */           retval.tree = root_0;
/* 5765 */           RewriteRuleSubtreeStream stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.tree : null);
/*      */ 
/* 5767 */           root_0 = (CommonTree)this.adaptor.nil();
/*      */ 
/* 5769 */           if ((this.gtype == 27) && (Character.isUpperCase(((rule_scope)this.rule_stack.peek()).name.charAt(0))))
/*      */           {
/* 5773 */             CommonTree root_1 = (CommonTree)this.adaptor.nil();
/* 5774 */             root_1 = (CommonTree)this.adaptor.becomeRoot((CommonTree)this.adaptor.create(12, "=>"), root_1);
/*      */ 
/* 5776 */             this.adaptor.addChild(root_1, stream_block.nextTree());
/*      */ 
/* 5778 */             this.adaptor.addChild(root_0, root_1);
/*      */           }
/*      */           else
/*      */           {
/* 5784 */             this.adaptor.addChild(root_0, (CommonTree)this.adaptor.create(34, "SYN_SEMPRED"));
/*      */           }
/*      */ 
/* 5788 */           retval.tree = root_0; } break;
/*      */       case 5:
/* 5802 */         if (this.state.backtracking == 0) {
/* 5803 */           retval.tree = root_0;
/* 5804 */           RewriteRuleSubtreeStream stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.tree : null);
/*      */ 
/* 5806 */           root_0 = (CommonTree)this.adaptor.nil();
/*      */ 
/* 5809 */           this.adaptor.addChild(root_0, stream_block.nextTree());
/*      */ 
/* 5813 */           retval.tree = root_0;
/*      */         }
/*      */ 
/*      */         break;
/*      */       }
/*      */ 
/* 5822 */       retval.stop = this.input.LT(-1);
/*      */ 
/* 5824 */       if (this.state.backtracking == 0)
/*      */       {
/* 5826 */         retval.tree = ((CommonTree)this.adaptor.rulePostProcessing(root_0));
/* 5827 */         this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
/*      */       }
/* 5829 */       if (this.state.backtracking == 0)
/*      */       {
/* 5831 */         retval.tree.getToken().setLine(firstToken.getLine());
/* 5832 */         retval.tree.getToken().setCharPositionInLine(firstToken.getCharPositionInLine());
/*      */       }
/*      */     }
/*      */     catch (RecognitionException re) {
/* 5836 */       re = 
/* 5843 */         re;
/*      */ 
/* 5837 */       reportError(re);
/* 5838 */       recover(this.input, re);
/* 5839 */       retval.tree = ((CommonTree)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), re));
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/* 5844 */     return retval;
/*      */   }
/*      */ 
/*      */   public final range_return range()
/*      */     throws RecognitionException
/*      */   {
/* 5856 */     range_return retval = new range_return();
/* 5857 */     retval.start = this.input.LT(1);
/*      */ 
/* 5859 */     CommonTree root_0 = null;
/*      */ 
/* 5861 */     Token c1 = null;
/* 5862 */     Token c2 = null;
/* 5863 */     Token RANGE120 = null;
/*      */ 
/* 5865 */     CommonTree c1_tree = null;
/* 5866 */     CommonTree c2_tree = null;
/* 5867 */     CommonTree RANGE120_tree = null;
/* 5868 */     RewriteRuleTokenStream stream_RANGE = new RewriteRuleTokenStream(this.adaptor, "token RANGE");
/* 5869 */     RewriteRuleTokenStream stream_CHAR_LITERAL = new RewriteRuleTokenStream(this.adaptor, "token CHAR_LITERAL");
/*      */     try
/*      */     {
/* 5875 */       c1 = (Token)match(this.input, 44, FOLLOW_CHAR_LITERAL_in_range2223);
/*      */       range_return localrange_return1;
/* 5875 */       if (this.state.failed) { localrange_return1 = retval; return localrange_return1; }
/* 5876 */       if (this.state.backtracking == 0) stream_CHAR_LITERAL.add(c1);
/*      */ 
/* 5878 */       RANGE120 = (Token)match(this.input, 13, FOLLOW_RANGE_in_range2225); if (this.state.failed) { localrange_return1 = retval; return localrange_return1; }
/* 5879 */       if (this.state.backtracking == 0) stream_RANGE.add(RANGE120);
/*      */ 
/* 5881 */       c2 = (Token)match(this.input, 44, FOLLOW_CHAR_LITERAL_in_range2229); if (this.state.failed) { localrange_return1 = retval; return localrange_return1; }
/* 5882 */       if (this.state.backtracking == 0) stream_CHAR_LITERAL.add(c2);
/*      */ 
/* 5893 */       if (this.state.backtracking == 0) {
/* 5894 */         retval.tree = root_0;
/* 5895 */         RewriteRuleTokenStream stream_c1 = new RewriteRuleTokenStream(this.adaptor, "token c1", c1);
/* 5896 */         RewriteRuleTokenStream stream_c2 = new RewriteRuleTokenStream(this.adaptor, "token c2", c2);
/* 5897 */         RewriteRuleSubtreeStream stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.tree : null);
/*      */ 
/* 5899 */         root_0 = (CommonTree)this.adaptor.nil();
/*      */ 
/* 5904 */         CommonTree root_1 = (CommonTree)this.adaptor.nil();
/* 5905 */         root_1 = (CommonTree)this.adaptor.becomeRoot((CommonTree)this.adaptor.create(14, c1, ".."), root_1);
/*      */ 
/* 5907 */         this.adaptor.addChild(root_1, stream_c1.nextNode());
/* 5908 */         this.adaptor.addChild(root_1, stream_c2.nextNode());
/*      */ 
/* 5910 */         this.adaptor.addChild(root_0, root_1);
/*      */ 
/* 5915 */         retval.tree = root_0;
/*      */       }
/*      */ 
/* 5918 */       retval.stop = this.input.LT(-1);
/*      */ 
/* 5920 */       if (this.state.backtracking == 0)
/*      */       {
/* 5922 */         retval.tree = ((CommonTree)this.adaptor.rulePostProcessing(root_0));
/* 5923 */         this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
/*      */       }
/*      */     } catch (RecognitionException re) {
/* 5926 */       re = 
/* 5933 */         re;
/*      */ 
/* 5927 */       reportError(re);
/* 5928 */       recover(this.input, re);
/* 5929 */       retval.tree = ((CommonTree)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), re));
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/* 5934 */     return retval;
/*      */   }
/*      */ 
/*      */   public final terminal_return terminal()
/*      */     throws RecognitionException
/*      */   {
/* 5946 */     terminal_return retval = new terminal_return();
/* 5947 */     retval.start = this.input.LT(1);
/*      */ 
/* 5949 */     CommonTree root_0 = null;
/*      */ 
/* 5951 */     Token CHAR_LITERAL121 = null;
/* 5952 */     Token TOKEN_REF122 = null;
/* 5953 */     Token ARG_ACTION123 = null;
/* 5954 */     Token STRING_LITERAL124 = null;
/* 5955 */     Token char_literal125 = null;
/* 5956 */     Token char_literal126 = null;
/* 5957 */     Token char_literal127 = null;
/*      */ 
/* 5959 */     CommonTree CHAR_LITERAL121_tree = null;
/* 5960 */     CommonTree TOKEN_REF122_tree = null;
/* 5961 */     CommonTree ARG_ACTION123_tree = null;
/* 5962 */     CommonTree STRING_LITERAL124_tree = null;
/* 5963 */     CommonTree char_literal125_tree = null;
/* 5964 */     CommonTree char_literal126_tree = null;
/* 5965 */     CommonTree char_literal127_tree = null;
/* 5966 */     RewriteRuleTokenStream stream_STRING_LITERAL = new RewriteRuleTokenStream(this.adaptor, "token STRING_LITERAL");
/* 5967 */     RewriteRuleTokenStream stream_BANG = new RewriteRuleTokenStream(this.adaptor, "token BANG");
/* 5968 */     RewriteRuleTokenStream stream_92 = new RewriteRuleTokenStream(this.adaptor, "token 92");
/* 5969 */     RewriteRuleTokenStream stream_CHAR_LITERAL = new RewriteRuleTokenStream(this.adaptor, "token CHAR_LITERAL");
/* 5970 */     RewriteRuleTokenStream stream_ROOT = new RewriteRuleTokenStream(this.adaptor, "token ROOT");
/* 5971 */     RewriteRuleTokenStream stream_TOKEN_REF = new RewriteRuleTokenStream(this.adaptor, "token TOKEN_REF");
/* 5972 */     RewriteRuleTokenStream stream_ARG_ACTION = new RewriteRuleTokenStream(this.adaptor, "token ARG_ACTION");
/*      */     try
/*      */     {
/* 5979 */       int alt59 = 4;
/*      */       Object nvae;
/* 5980 */       switch (this.input.LA(1))
/*      */       {
/*      */       case 44:
/* 5983 */         alt59 = 1;
/*      */ 
/* 5985 */         break;
/*      */       case 42:
/* 5988 */         alt59 = 2;
/*      */ 
/* 5990 */         break;
/*      */       case 43:
/* 5993 */         alt59 = 3;
/*      */ 
/* 5995 */         break;
/*      */       case 92:
/* 5998 */         alt59 = 4;
/*      */ 
/* 6000 */         break;
/*      */       default:
/* 6002 */         if (this.state.backtracking > 0) { this.state.failed = true; terminal_return localterminal_return1 = retval; return localterminal_return1; }
/* 6003 */         nvae = new NoViableAltException("", 59, 0, this.input);
/*      */ 
/* 6006 */         throw ((Throwable)nvae);
/*      */       }
/*      */       Object stream_retval;
/*      */       int alt58;
/*      */       Object stream_retval;
/*      */       Object stream_retval;
/* 6009 */       switch (alt59)
/*      */       {
/*      */       case 1:
/* 6013 */         CHAR_LITERAL121 = (Token)match(this.input, 44, FOLLOW_CHAR_LITERAL_in_terminal2260); if (this.state.failed) { nvae = retval; return nvae; }
/* 6014 */         if (this.state.backtracking == 0) stream_CHAR_LITERAL.add(CHAR_LITERAL121);
/*      */ 
/* 6025 */         if (this.state.backtracking == 0) {
/* 6026 */           retval.tree = root_0;
/* 6027 */           stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.tree : null);
/*      */ 
/* 6029 */           root_0 = (CommonTree)this.adaptor.nil();
/*      */ 
/* 6032 */           this.adaptor.addChild(root_0, stream_CHAR_LITERAL.nextNode());
/*      */ 
/* 6036 */           retval.tree = root_0; } break;
/*      */       case 2:
/* 6042 */         TOKEN_REF122 = (Token)match(this.input, 42, FOLLOW_TOKEN_REF_in_terminal2282); if (this.state.failed) { stream_retval = retval; return stream_retval; }
/* 6043 */         if (this.state.backtracking == 0) stream_TOKEN_REF.add(TOKEN_REF122);
/*      */ 
/* 6046 */         alt58 = 2;
/*      */         Object nvae;
/* 6047 */         switch (this.input.LA(1))
/*      */         {
/*      */         case 48:
/* 6050 */           alt58 = 1;
/*      */ 
/* 6052 */           break;
/*      */         case 32:
/*      */         case 37:
/*      */         case 38:
/*      */         case 39:
/*      */         case 40:
/*      */         case 42:
/*      */         case 43:
/*      */         case 44:
/*      */         case 45:
/*      */         case 49:
/*      */         case 69:
/*      */         case 74:
/*      */         case 82:
/*      */         case 83:
/*      */         case 84:
/*      */         case 89:
/*      */         case 90:
/*      */         case 91:
/*      */         case 92:
/* 6073 */           alt58 = 2;
/*      */ 
/* 6075 */           break;
/*      */         case 33:
/*      */         case 34:
/*      */         case 35:
/*      */         case 36:
/*      */         case 41:
/*      */         case 46:
/*      */         case 47:
/*      */         case 50:
/*      */         case 51:
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
/*      */         case 70:
/*      */         case 71:
/*      */         case 72:
/*      */         case 73:
/*      */         case 75:
/*      */         case 76:
/*      */         case 77:
/*      */         case 78:
/*      */         case 79:
/*      */         case 80:
/*      */         case 81:
/*      */         case 85:
/*      */         case 86:
/*      */         case 87:
/*      */         case 88:
/*      */         default:
/* 6077 */           if (this.state.backtracking > 0) { this.state.failed = true; terminal_return localterminal_return3 = retval; return localterminal_return3; }
/* 6078 */           nvae = new NoViableAltException("", 58, 0, this.input);
/*      */ 
/* 6081 */           throw ((Throwable)nvae);
/*      */         }
/*      */ 
/* 6084 */         switch (alt58)
/*      */         {
/*      */         case 1:
/* 6088 */           ARG_ACTION123 = (Token)match(this.input, 48, FOLLOW_ARG_ACTION_in_terminal2289); if (this.state.failed) { nvae = retval; return nvae; }
/* 6089 */           if (this.state.backtracking == 0) stream_ARG_ACTION.add(ARG_ACTION123);
/*      */ 
/* 6100 */           if (this.state.backtracking == 0) {
/* 6101 */             retval.tree = root_0;
/* 6102 */             RewriteRuleSubtreeStream stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.tree : null);
/*      */ 
/* 6104 */             root_0 = (CommonTree)this.adaptor.nil();
/*      */ 
/* 6109 */             CommonTree root_1 = (CommonTree)this.adaptor.nil();
/* 6110 */             root_1 = (CommonTree)this.adaptor.becomeRoot(stream_TOKEN_REF.nextNode(), root_1);
/*      */ 
/* 6112 */             this.adaptor.addChild(root_1, stream_ARG_ACTION.nextNode());
/*      */ 
/* 6114 */             this.adaptor.addChild(root_0, root_1);
/*      */ 
/* 6119 */             retval.tree = root_0; } break;
/*      */         case 2:
/* 6133 */           if (this.state.backtracking == 0) {
/* 6134 */             retval.tree = root_0;
/* 6135 */             stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.tree : null);
/*      */ 
/* 6137 */             root_0 = (CommonTree)this.adaptor.nil();
/*      */ 
/* 6140 */             this.adaptor.addChild(root_0, stream_TOKEN_REF.nextNode());
/*      */ 
/* 6144 */             retval.tree = root_0;
/*      */           }
/*      */ 
/*      */           break;
/*      */         }
/*      */ 
/* 6152 */         break;
/*      */       case 3:
/* 6156 */         STRING_LITERAL124 = (Token)match(this.input, 43, FOLLOW_STRING_LITERAL_in_terminal2328); if (this.state.failed) { terminal_return localterminal_return2 = retval; return localterminal_return2; }
/* 6157 */         if (this.state.backtracking == 0) stream_STRING_LITERAL.add(STRING_LITERAL124);
/*      */ 
/* 6168 */         if (this.state.backtracking == 0) {
/* 6169 */           retval.tree = root_0;
/* 6170 */           stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.tree : null);
/*      */ 
/* 6172 */           root_0 = (CommonTree)this.adaptor.nil();
/*      */ 
/* 6175 */           this.adaptor.addChild(root_0, stream_STRING_LITERAL.nextNode());
/*      */ 
/* 6179 */           retval.tree = root_0; } break;
/*      */       case 4:
/* 6185 */         char_literal125 = (Token)match(this.input, 92, FOLLOW_92_in_terminal2343); if (this.state.failed) { stream_retval = retval; return stream_retval; }
/* 6186 */         if (this.state.backtracking == 0) stream_92.add(char_literal125);
/*      */ 
/* 6197 */         if (this.state.backtracking == 0) {
/* 6198 */           retval.tree = root_0;
/* 6199 */           RewriteRuleSubtreeStream stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.tree : null);
/*      */ 
/* 6201 */           root_0 = (CommonTree)this.adaptor.nil();
/*      */ 
/* 6204 */           this.adaptor.addChild(root_0, stream_92.nextNode());
/*      */ 
/* 6208 */           retval.tree = root_0;
/*      */         }
/*      */ 
/*      */         break;
/*      */       }
/*      */ 
/* 6215 */       int alt60 = 3;
/* 6216 */       switch (this.input.LA(1))
/*      */       {
/*      */       case 38:
/* 6219 */         alt60 = 1;
/*      */ 
/* 6221 */         break;
/*      */       case 39:
/* 6224 */         alt60 = 2;
/*      */       }
/*      */       Object stream_retval;
/* 6229 */       switch (alt60)
/*      */       {
/*      */       case 1:
/* 6233 */         char_literal126 = (Token)match(this.input, 38, FOLLOW_ROOT_in_terminal2364); if (this.state.failed) { stream_retval = retval; return stream_retval; }
/* 6234 */         if (this.state.backtracking == 0) stream_ROOT.add(char_literal126);
/*      */ 
/* 6245 */         if (this.state.backtracking == 0) {
/* 6246 */           retval.tree = root_0;
/* 6247 */           stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.tree : null);
/*      */ 
/* 6249 */           root_0 = (CommonTree)this.adaptor.nil();
/*      */ 
/* 6254 */           CommonTree root_1 = (CommonTree)this.adaptor.nil();
/* 6255 */           root_1 = (CommonTree)this.adaptor.becomeRoot(stream_ROOT.nextNode(), root_1);
/*      */ 
/* 6257 */           this.adaptor.addChild(root_1, ((RewriteRuleSubtreeStream)stream_retval).nextTree());
/*      */ 
/* 6259 */           this.adaptor.addChild(root_0, root_1);
/*      */ 
/* 6264 */           retval.tree = root_0; } break;
/*      */       case 2:
/* 6270 */         char_literal127 = (Token)match(this.input, 39, FOLLOW_BANG_in_terminal2385); if (this.state.failed) { stream_retval = retval; return stream_retval; }
/* 6271 */         if (this.state.backtracking == 0) stream_BANG.add(char_literal127);
/*      */ 
/* 6282 */         if (this.state.backtracking == 0) {
/* 6283 */           retval.tree = root_0;
/* 6284 */           RewriteRuleSubtreeStream stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.tree : null);
/*      */ 
/* 6286 */           root_0 = (CommonTree)this.adaptor.nil();
/*      */ 
/* 6291 */           CommonTree root_1 = (CommonTree)this.adaptor.nil();
/* 6292 */           root_1 = (CommonTree)this.adaptor.becomeRoot(stream_BANG.nextNode(), root_1);
/*      */ 
/* 6294 */           this.adaptor.addChild(root_1, stream_retval.nextTree());
/*      */ 
/* 6296 */           this.adaptor.addChild(root_0, root_1);
/*      */ 
/* 6301 */           retval.tree = root_0;
/*      */         }
/*      */ 
/*      */         break;
/*      */       }
/*      */ 
/* 6310 */       retval.stop = this.input.LT(-1);
/*      */ 
/* 6312 */       if (this.state.backtracking == 0)
/*      */       {
/* 6314 */         retval.tree = ((CommonTree)this.adaptor.rulePostProcessing(root_0));
/* 6315 */         this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
/*      */       }
/*      */     } catch (RecognitionException re) {
/* 6318 */       re = 
/* 6325 */         re;
/*      */ 
/* 6319 */       reportError(re);
/* 6320 */       recover(this.input, re);
/* 6321 */       retval.tree = ((CommonTree)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), re));
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/* 6326 */     return retval;
/*      */   }
/*      */ 
/*      */   public final notTerminal_return notTerminal()
/*      */     throws RecognitionException
/*      */   {
/* 6338 */     notTerminal_return retval = new notTerminal_return();
/* 6339 */     retval.start = this.input.LT(1);
/*      */ 
/* 6341 */     CommonTree root_0 = null;
/*      */ 
/* 6343 */     Token set128 = null;
/*      */ 
/* 6345 */     CommonTree set128_tree = null;
/*      */     try
/*      */     {
/* 6351 */       root_0 = (CommonTree)this.adaptor.nil();
/*      */ 
/* 6353 */       set128 = this.input.LT(1);
/* 6354 */       if ((this.input.LA(1) >= 42) && (this.input.LA(1) <= 44)) {
/* 6355 */         this.input.consume();
/* 6356 */         if (this.state.backtracking == 0) this.adaptor.addChild(root_0, (CommonTree)this.adaptor.create(set128));
/* 6357 */         this.state.errorRecovery = false; this.state.failed = false;
/*      */       }
/*      */       else {
/* 6360 */         if (this.state.backtracking > 0) { this.state.failed = true; notTerminal_return localnotTerminal_return1 = retval; return localnotTerminal_return1; }
/* 6361 */         MismatchedSetException mse = new MismatchedSetException(null, this.input);
/* 6362 */         throw mse;
/*      */       }
/*      */ 
/* 6368 */       retval.stop = this.input.LT(-1);
/*      */ 
/* 6370 */       if (this.state.backtracking == 0)
/*      */       {
/* 6372 */         retval.tree = ((CommonTree)this.adaptor.rulePostProcessing(root_0));
/* 6373 */         this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
/*      */       }
/*      */     } catch (RecognitionException re) {
/* 6376 */       re = 
/* 6383 */         re;
/*      */ 
/* 6377 */       reportError(re);
/* 6378 */       recover(this.input, re);
/* 6379 */       retval.tree = ((CommonTree)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), re));
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/* 6384 */     return retval;
/*      */   }
/*      */ 
/*      */   public final ebnfSuffix_return ebnfSuffix()
/*      */     throws RecognitionException
/*      */   {
/* 6396 */     ebnfSuffix_return retval = new ebnfSuffix_return();
/* 6397 */     retval.start = this.input.LT(1);
/*      */ 
/* 6399 */     CommonTree root_0 = null;
/*      */ 
/* 6401 */     Token char_literal129 = null;
/* 6402 */     Token char_literal130 = null;
/* 6403 */     Token char_literal131 = null;
/*      */ 
/* 6405 */     CommonTree char_literal129_tree = null;
/* 6406 */     CommonTree char_literal130_tree = null;
/* 6407 */     CommonTree char_literal131_tree = null;
/* 6408 */     RewriteRuleTokenStream stream_91 = new RewriteRuleTokenStream(this.adaptor, "token 91");
/* 6409 */     RewriteRuleTokenStream stream_90 = new RewriteRuleTokenStream(this.adaptor, "token 90");
/* 6410 */     RewriteRuleTokenStream stream_74 = new RewriteRuleTokenStream(this.adaptor, "token 74");
/*      */ 
/* 6413 */     Token op = this.input.LT(1);
/*      */     try
/*      */     {
/* 6417 */       int alt61 = 3;
/*      */       Object nvae;
/* 6418 */       switch (this.input.LA(1))
/*      */       {
/*      */       case 90:
/* 6421 */         alt61 = 1;
/*      */ 
/* 6423 */         break;
/*      */       case 74:
/* 6426 */         alt61 = 2;
/*      */ 
/* 6428 */         break;
/*      */       case 91:
/* 6431 */         alt61 = 3;
/*      */ 
/* 6433 */         break;
/*      */       default:
/* 6435 */         if (this.state.backtracking > 0) { this.state.failed = true; ebnfSuffix_return localebnfSuffix_return1 = retval; return localebnfSuffix_return1; }
/* 6436 */         nvae = new NoViableAltException("", 61, 0, this.input);
/*      */ 
/* 6439 */         throw ((Throwable)nvae);
/*      */       }
/*      */       Object stream_retval;
/*      */       Object stream_retval;
/* 6442 */       switch (alt61)
/*      */       {
/*      */       case 1:
/* 6446 */         char_literal129 = (Token)match(this.input, 90, FOLLOW_90_in_ebnfSuffix2445); if (this.state.failed) { nvae = retval; return nvae; }
/* 6447 */         if (this.state.backtracking == 0) stream_90.add(char_literal129);
/*      */ 
/* 6458 */         if (this.state.backtracking == 0) {
/* 6459 */           retval.tree = root_0;
/* 6460 */           stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.tree : null);
/*      */ 
/* 6462 */           root_0 = (CommonTree)this.adaptor.nil();
/*      */ 
/* 6465 */           this.adaptor.addChild(root_0, (CommonTree)this.adaptor.create(9, op));
/*      */ 
/* 6469 */           retval.tree = root_0; } break;
/*      */       case 2:
/* 6475 */         char_literal130 = (Token)match(this.input, 74, FOLLOW_74_in_ebnfSuffix2457); if (this.state.failed) { stream_retval = retval; return stream_retval; }
/* 6476 */         if (this.state.backtracking == 0) stream_74.add(char_literal130);
/*      */ 
/* 6487 */         if (this.state.backtracking == 0) {
/* 6488 */           retval.tree = root_0;
/* 6489 */           stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.tree : null);
/*      */ 
/* 6491 */           root_0 = (CommonTree)this.adaptor.nil();
/*      */ 
/* 6494 */           this.adaptor.addChild(root_0, (CommonTree)this.adaptor.create(10, op));
/*      */ 
/* 6498 */           retval.tree = root_0; } break;
/*      */       case 3:
/* 6504 */         char_literal131 = (Token)match(this.input, 91, FOLLOW_91_in_ebnfSuffix2470); if (this.state.failed) { stream_retval = retval; return stream_retval; }
/* 6505 */         if (this.state.backtracking == 0) stream_91.add(char_literal131);
/*      */ 
/* 6516 */         if (this.state.backtracking == 0) {
/* 6517 */           retval.tree = root_0;
/* 6518 */           RewriteRuleSubtreeStream stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.tree : null);
/*      */ 
/* 6520 */           root_0 = (CommonTree)this.adaptor.nil();
/*      */ 
/* 6523 */           this.adaptor.addChild(root_0, (CommonTree)this.adaptor.create(11, op));
/*      */ 
/* 6527 */           retval.tree = root_0;
/*      */         }
/*      */         break;
/*      */       }
/*      */ 
/* 6532 */       retval.stop = this.input.LT(-1);
/*      */ 
/* 6534 */       if (this.state.backtracking == 0)
/*      */       {
/* 6536 */         retval.tree = ((CommonTree)this.adaptor.rulePostProcessing(root_0));
/* 6537 */         this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
/*      */       }
/*      */     } catch (RecognitionException re) {
/* 6540 */       re = 
/* 6547 */         re;
/*      */ 
/* 6541 */       reportError(re);
/* 6542 */       recover(this.input, re);
/* 6543 */       retval.tree = ((CommonTree)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), re));
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/* 6548 */     return retval;
/*      */   }
/*      */ 
/*      */   public final rewrite_return rewrite()
/*      */     throws RecognitionException
/*      */   {
/* 6560 */     rewrite_return retval = new rewrite_return();
/* 6561 */     retval.start = this.input.LT(1);
/*      */ 
/* 6563 */     CommonTree root_0 = null;
/*      */ 
/* 6565 */     Token rew2 = null;
/* 6566 */     Token rew = null;
/* 6567 */     Token preds = null;
/* 6568 */     List list_rew = null;
/* 6569 */     List list_preds = null;
/* 6570 */     List list_predicated = null;
/* 6571 */     rewrite_alternative_return last = null;
/*      */ 
/* 6573 */     RuleReturnScope predicated = null;
/* 6574 */     CommonTree rew2_tree = null;
/* 6575 */     CommonTree rew_tree = null;
/* 6576 */     CommonTree preds_tree = null;
/* 6577 */     RewriteRuleTokenStream stream_SEMPRED = new RewriteRuleTokenStream(this.adaptor, "token SEMPRED");
/* 6578 */     RewriteRuleTokenStream stream_REWRITE = new RewriteRuleTokenStream(this.adaptor, "token REWRITE");
/* 6579 */     RewriteRuleSubtreeStream stream_rewrite_alternative = new RewriteRuleSubtreeStream(this.adaptor, "rule rewrite_alternative");
/*      */ 
/* 6581 */     Token firstToken = this.input.LT(1);
/*      */     try
/*      */     {
/* 6585 */       int alt63 = 2;
/* 6586 */       switch (this.input.LA(1))
/*      */       {
/*      */       case 40:
/* 6589 */         alt63 = 1;
/*      */ 
/* 6591 */         break;
/*      */       case 69:
/*      */       case 83:
/*      */       case 84:
/* 6596 */         alt63 = 2;
/*      */ 
/* 6598 */         break;
/*      */       default:
/* 6600 */         if (this.state.backtracking > 0) { this.state.failed = true; rewrite_return localrewrite_return1 = retval; return localrewrite_return1; }
/* 6601 */         NoViableAltException nvae = new NoViableAltException("", 63, 0, this.input);
/*      */ 
/* 6604 */         throw nvae;
/*      */       }
/*      */ 
/* 6607 */       switch (alt63)
/*      */       {
/*      */       case 1:
/*      */         int alt62;
/*      */         while (true)
/*      */         {
/* 6614 */           alt62 = 2;
/* 6615 */           switch (this.input.LA(1))
/*      */           {
/*      */           case 40:
/* 6618 */             switch (this.input.LA(2))
/*      */             {
/*      */             case 32:
/* 6621 */               alt62 = 1;
/*      */             }
/*      */ 
/*      */             break;
/*      */           }
/*      */ 
/* 6632 */           switch (alt62)
/*      */           {
/*      */           case 1:
/* 6636 */             rew = (Token)match(this.input, 40, FOLLOW_REWRITE_in_rewrite2499);
/*      */             rewrite_return localrewrite_return3;
/* 6636 */             if (this.state.failed) { localrewrite_return3 = retval; return localrewrite_return3; }
/* 6637 */             if (this.state.backtracking == 0) stream_REWRITE.add(rew);
/*      */ 
/* 6639 */             if (list_rew == null) list_rew = new ArrayList();
/* 6640 */             list_rew.add(rew);
/*      */ 
/* 6642 */             preds = (Token)match(this.input, 32, FOLLOW_SEMPRED_in_rewrite2503); if (this.state.failed) { localrewrite_return3 = retval; return localrewrite_return3; }
/* 6643 */             if (this.state.backtracking == 0) stream_SEMPRED.add(preds);
/*      */ 
/* 6645 */             if (list_preds == null) list_preds = new ArrayList();
/* 6646 */             list_preds.add(preds);
/*      */ 
/* 6648 */             pushFollow(FOLLOW_rewrite_alternative_in_rewrite2507);
/* 6649 */             predicated = rewrite_alternative();
/*      */ 
/* 6651 */             this.state._fsp -= 1;
/* 6652 */             if (this.state.failed) { localrewrite_return3 = retval; return localrewrite_return3; }
/* 6653 */             if (this.state.backtracking == 0) stream_rewrite_alternative.add(predicated.getTree());
/* 6654 */             if (list_predicated == null) list_predicated = new ArrayList();
/* 6655 */             list_predicated.add(predicated.getTree());
/*      */ 
/* 6659 */             break;
/*      */           default:
/* 6662 */             break label588;
/*      */           }
/*      */         }
/*      */ 
/* 6666 */         rew2 = (Token)match(this.input, 40, FOLLOW_REWRITE_in_rewrite2515);
/*      */         rewrite_return localrewrite_return2;
/* 6666 */         if (this.state.failed) { localrewrite_return2 = retval; return localrewrite_return2; }
/* 6667 */         if (this.state.backtracking == 0) stream_REWRITE.add(rew2);
/*      */ 
/* 6669 */         pushFollow(FOLLOW_rewrite_alternative_in_rewrite2519);
/* 6670 */         last = rewrite_alternative();
/*      */ 
/* 6672 */         this.state._fsp -= 1;
/* 6673 */         if (this.state.failed) { localrewrite_return2 = retval; return localrewrite_return2; }
/* 6674 */         if (this.state.backtracking == 0) stream_rewrite_alternative.add(last.getTree());
/*      */ 
/* 6684 */         if (this.state.backtracking == 0) {
/* 6685 */           retval.tree = root_0;
/* 6686 */           RewriteRuleTokenStream stream_rew2 = new RewriteRuleTokenStream(this.adaptor, "token rew2", rew2);
/* 6687 */           RewriteRuleTokenStream stream_rew = new RewriteRuleTokenStream(this.adaptor, "token rew", list_rew);
/* 6688 */           RewriteRuleTokenStream stream_preds = new RewriteRuleTokenStream(this.adaptor, "token preds", list_preds);
/* 6689 */           RewriteRuleSubtreeStream stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.tree : null);
/* 6690 */           RewriteRuleSubtreeStream stream_last = new RewriteRuleSubtreeStream(this.adaptor, "rule last", last != null ? last.tree : null);
/* 6691 */           RewriteRuleSubtreeStream stream_predicated = new RewriteRuleSubtreeStream(this.adaptor, "token predicated", list_predicated);
/* 6692 */           root_0 = (CommonTree)this.adaptor.nil();
/*      */ 
/* 6696 */           while ((stream_preds.hasNext()) || (stream_rew.hasNext()) || (stream_predicated.hasNext()))
/*      */           {
/* 6699 */             CommonTree root_1 = (CommonTree)this.adaptor.nil();
/* 6700 */             root_1 = (CommonTree)this.adaptor.becomeRoot(stream_rew.nextNode(), root_1);
/*      */ 
/* 6702 */             this.adaptor.addChild(root_1, stream_preds.nextNode());
/* 6703 */             this.adaptor.addChild(root_1, stream_predicated.nextTree());
/*      */ 
/* 6705 */             this.adaptor.addChild(root_0, root_1);
/*      */           }
/*      */ 
/* 6709 */           stream_preds.reset();
/* 6710 */           stream_rew.reset();
/* 6711 */           stream_predicated.reset();
/*      */ 
/* 6714 */           CommonTree root_1 = (CommonTree)this.adaptor.nil();
/* 6715 */           root_1 = (CommonTree)this.adaptor.becomeRoot(stream_rew2.nextNode(), root_1);
/*      */ 
/* 6717 */           this.adaptor.addChild(root_1, stream_last.nextTree());
/*      */ 
/* 6719 */           this.adaptor.addChild(root_0, root_1);
/*      */ 
/* 6724 */           retval.tree = root_0; } break;
/*      */       case 2:
/* 6730 */         label588: root_0 = (CommonTree)this.adaptor.nil();
/*      */       }
/*      */ 
/* 6736 */       retval.stop = this.input.LT(-1);
/*      */ 
/* 6738 */       if (this.state.backtracking == 0)
/*      */       {
/* 6740 */         retval.tree = ((CommonTree)this.adaptor.rulePostProcessing(root_0));
/* 6741 */         this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
/*      */       }
/*      */     } catch (RecognitionException re) {
/* 6744 */       re = 
/* 6751 */         re;
/*      */ 
/* 6745 */       reportError(re);
/* 6746 */       recover(this.input, re);
/* 6747 */       retval.tree = ((CommonTree)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), re));
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/* 6752 */     return retval;
/*      */   }
/*      */ 
/*      */   public final rewrite_alternative_return rewrite_alternative()
/*      */     throws RecognitionException
/*      */   {
/* 6764 */     rewrite_alternative_return retval = new rewrite_alternative_return();
/* 6765 */     retval.start = this.input.LT(1);
/*      */ 
/* 6767 */     CommonTree root_0 = null;
/*      */ 
/* 6769 */     rewrite_template_return rewrite_template132 = null;
/*      */ 
/* 6771 */     rewrite_tree_alternative_return rewrite_tree_alternative133 = null;
/*      */     try
/*      */     {
/* 6777 */       int alt64 = 3;
/* 6778 */       alt64 = this.dfa64.predict(this.input);
/*      */       rewrite_alternative_return localrewrite_alternative_return1;
/* 6779 */       switch (alt64)
/*      */       {
/*      */       case 1:
/* 6783 */         root_0 = (CommonTree)this.adaptor.nil();
/*      */ 
/* 6785 */         pushFollow(FOLLOW_rewrite_template_in_rewrite_alternative2570);
/* 6786 */         rewrite_template132 = rewrite_template();
/*      */ 
/* 6788 */         this.state._fsp -= 1;
/* 6789 */         if (this.state.failed) { localrewrite_alternative_return1 = retval; return localrewrite_alternative_return1; }
/* 6790 */         if (this.state.backtracking == 0) this.adaptor.addChild(root_0, rewrite_template132.getTree()); break;
/*      */       case 2:
/* 6797 */         root_0 = (CommonTree)this.adaptor.nil();
/*      */ 
/* 6799 */         pushFollow(FOLLOW_rewrite_tree_alternative_in_rewrite_alternative2575);
/* 6800 */         rewrite_tree_alternative133 = rewrite_tree_alternative();
/*      */ 
/* 6802 */         this.state._fsp -= 1;
/* 6803 */         if (this.state.failed) { localrewrite_alternative_return1 = retval; return localrewrite_alternative_return1; }
/* 6804 */         if (this.state.backtracking == 0) this.adaptor.addChild(root_0, rewrite_tree_alternative133.getTree()); break;
/*      */       case 3:
/* 6819 */         if (this.state.backtracking == 0) {
/* 6820 */           retval.tree = root_0;
/* 6821 */           RewriteRuleSubtreeStream stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.tree : null);
/*      */ 
/* 6823 */           root_0 = (CommonTree)this.adaptor.nil();
/*      */ 
/* 6828 */           CommonTree root_1 = (CommonTree)this.adaptor.nil();
/* 6829 */           root_1 = (CommonTree)this.adaptor.becomeRoot((CommonTree)this.adaptor.create(16, "ALT"), root_1);
/*      */ 
/* 6831 */           this.adaptor.addChild(root_1, (CommonTree)this.adaptor.create(15, "EPSILON"));
/* 6832 */           this.adaptor.addChild(root_1, (CommonTree)this.adaptor.create(19, "EOA"));
/*      */ 
/* 6834 */           this.adaptor.addChild(root_0, root_1);
/*      */ 
/* 6839 */           retval.tree = root_0;
/*      */         }
/*      */         break;
/*      */       }
/*      */ 
/* 6844 */       retval.stop = this.input.LT(-1);
/*      */ 
/* 6846 */       if (this.state.backtracking == 0)
/*      */       {
/* 6848 */         retval.tree = ((CommonTree)this.adaptor.rulePostProcessing(root_0));
/* 6849 */         this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
/*      */       }
/*      */     } catch (RecognitionException re) {
/* 6852 */       re = 
/* 6859 */         re;
/*      */ 
/* 6853 */       reportError(re);
/* 6854 */       recover(this.input, re);
/* 6855 */       retval.tree = ((CommonTree)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), re));
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/* 6860 */     return retval;
/*      */   }
/*      */ 
/*      */   public final rewrite_tree_block_return rewrite_tree_block()
/*      */     throws RecognitionException
/*      */   {
/* 6872 */     rewrite_tree_block_return retval = new rewrite_tree_block_return();
/* 6873 */     retval.start = this.input.LT(1);
/*      */ 
/* 6875 */     CommonTree root_0 = null;
/*      */ 
/* 6877 */     Token lp = null;
/* 6878 */     Token char_literal135 = null;
/* 6879 */     rewrite_tree_alternative_return rewrite_tree_alternative134 = null;
/*      */ 
/* 6882 */     CommonTree lp_tree = null;
/* 6883 */     CommonTree char_literal135_tree = null;
/* 6884 */     RewriteRuleTokenStream stream_82 = new RewriteRuleTokenStream(this.adaptor, "token 82");
/* 6885 */     RewriteRuleTokenStream stream_84 = new RewriteRuleTokenStream(this.adaptor, "token 84");
/* 6886 */     RewriteRuleSubtreeStream stream_rewrite_tree_alternative = new RewriteRuleSubtreeStream(this.adaptor, "rule rewrite_tree_alternative");
/*      */     try
/*      */     {
/* 6891 */       lp = (Token)match(this.input, 82, FOLLOW_82_in_rewrite_tree_block2617);
/*      */       rewrite_tree_block_return localrewrite_tree_block_return1;
/* 6891 */       if (this.state.failed) { localrewrite_tree_block_return1 = retval; return localrewrite_tree_block_return1; }
/* 6892 */       if (this.state.backtracking == 0) stream_82.add(lp);
/*      */ 
/* 6894 */       pushFollow(FOLLOW_rewrite_tree_alternative_in_rewrite_tree_block2619);
/* 6895 */       rewrite_tree_alternative134 = rewrite_tree_alternative();
/*      */ 
/* 6897 */       this.state._fsp -= 1;
/* 6898 */       if (this.state.failed) { localrewrite_tree_block_return1 = retval; return localrewrite_tree_block_return1; }
/* 6899 */       if (this.state.backtracking == 0) stream_rewrite_tree_alternative.add(rewrite_tree_alternative134.getTree());
/* 6900 */       char_literal135 = (Token)match(this.input, 84, FOLLOW_84_in_rewrite_tree_block2621); if (this.state.failed) { localrewrite_tree_block_return1 = retval; return localrewrite_tree_block_return1; }
/* 6901 */       if (this.state.backtracking == 0) stream_84.add(char_literal135);
/*      */ 
/* 6912 */       if (this.state.backtracking == 0) {
/* 6913 */         retval.tree = root_0;
/* 6914 */         RewriteRuleSubtreeStream stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.tree : null);
/*      */ 
/* 6916 */         root_0 = (CommonTree)this.adaptor.nil();
/*      */ 
/* 6921 */         CommonTree root_1 = (CommonTree)this.adaptor.nil();
/* 6922 */         root_1 = (CommonTree)this.adaptor.becomeRoot((CommonTree)this.adaptor.create(8, lp, "BLOCK"), root_1);
/*      */ 
/* 6924 */         this.adaptor.addChild(root_1, stream_rewrite_tree_alternative.nextTree());
/* 6925 */         this.adaptor.addChild(root_1, (CommonTree)this.adaptor.create(18, lp, "EOB"));
/*      */ 
/* 6927 */         this.adaptor.addChild(root_0, root_1);
/*      */ 
/* 6932 */         retval.tree = root_0;
/*      */       }
/*      */ 
/* 6935 */       retval.stop = this.input.LT(-1);
/*      */ 
/* 6937 */       if (this.state.backtracking == 0)
/*      */       {
/* 6939 */         retval.tree = ((CommonTree)this.adaptor.rulePostProcessing(root_0));
/* 6940 */         this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
/*      */       }
/*      */     } catch (RecognitionException re) {
/* 6943 */       re = 
/* 6950 */         re;
/*      */ 
/* 6944 */       reportError(re);
/* 6945 */       recover(this.input, re);
/* 6946 */       retval.tree = ((CommonTree)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), re));
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/* 6951 */     return retval;
/*      */   }
/*      */ 
/*      */   public final rewrite_tree_alternative_return rewrite_tree_alternative()
/*      */     throws RecognitionException
/*      */   {
/* 6963 */     rewrite_tree_alternative_return retval = new rewrite_tree_alternative_return();
/* 6964 */     retval.start = this.input.LT(1);
/*      */ 
/* 6966 */     CommonTree root_0 = null;
/*      */ 
/* 6968 */     rewrite_tree_element_return rewrite_tree_element136 = null;
/*      */ 
/* 6971 */     RewriteRuleSubtreeStream stream_rewrite_tree_element = new RewriteRuleSubtreeStream(this.adaptor, "rule rewrite_tree_element");
/*      */     try
/*      */     {
/* 6977 */       int cnt65 = 0;
/*      */       while (true)
/*      */       {
/* 6980 */         int alt65 = 2;
/* 6981 */         switch (this.input.LA(1))
/*      */         {
/*      */         case 37:
/*      */         case 42:
/*      */         case 43:
/*      */         case 44:
/*      */         case 45:
/*      */         case 49:
/*      */         case 82:
/*      */         case 93:
/* 6991 */           alt65 = 1;
/*      */         }
/*      */         rewrite_tree_alternative_return localrewrite_tree_alternative_return1;
/* 6997 */         switch (alt65)
/*      */         {
/*      */         case 1:
/* 7001 */           pushFollow(FOLLOW_rewrite_tree_element_in_rewrite_tree_alternative2655);
/* 7002 */           rewrite_tree_element136 = rewrite_tree_element();
/*      */ 
/* 7004 */           this.state._fsp -= 1;
/* 7005 */           if (this.state.failed) { localrewrite_tree_alternative_return1 = retval; return localrewrite_tree_alternative_return1; }
/* 7006 */           if (this.state.backtracking == 0) stream_rewrite_tree_element.add(rewrite_tree_element136.getTree()); break;
/*      */         default:
/* 7012 */           if (cnt65 >= 1) break label276;
/* 7013 */           if (this.state.backtracking > 0) { this.state.failed = true; localrewrite_tree_alternative_return1 = retval; return localrewrite_tree_alternative_return1; }
/* 7014 */           EarlyExitException eee = new EarlyExitException(65, this.input);
/*      */ 
/* 7016 */           throw eee;
/*      */         }
/* 7018 */         cnt65++;
/*      */       }
/*      */ 
/* 7030 */       label276: if (this.state.backtracking == 0) {
/* 7031 */         retval.tree = root_0;
/* 7032 */         RewriteRuleSubtreeStream stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.tree : null);
/*      */ 
/* 7034 */         root_0 = (CommonTree)this.adaptor.nil();
/*      */ 
/* 7039 */         CommonTree root_1 = (CommonTree)this.adaptor.nil();
/* 7040 */         root_1 = (CommonTree)this.adaptor.becomeRoot((CommonTree)this.adaptor.create(16, "ALT"), root_1);
/*      */ 
/* 7042 */         if (!stream_rewrite_tree_element.hasNext()) {
/* 7043 */           throw new RewriteEarlyExitException();
/*      */         }
/* 7045 */         while (stream_rewrite_tree_element.hasNext()) {
/* 7046 */           this.adaptor.addChild(root_1, stream_rewrite_tree_element.nextTree());
/*      */         }
/*      */ 
/* 7049 */         stream_rewrite_tree_element.reset();
/* 7050 */         this.adaptor.addChild(root_1, (CommonTree)this.adaptor.create(19, "EOA"));
/*      */ 
/* 7052 */         this.adaptor.addChild(root_0, root_1);
/*      */ 
/* 7057 */         retval.tree = root_0;
/*      */       }
/*      */ 
/* 7060 */       retval.stop = this.input.LT(-1);
/*      */ 
/* 7062 */       if (this.state.backtracking == 0)
/*      */       {
/* 7064 */         retval.tree = ((CommonTree)this.adaptor.rulePostProcessing(root_0));
/* 7065 */         this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
/*      */       }
/*      */     } catch (RecognitionException re) {
/* 7068 */       re = 
/* 7075 */         re;
/*      */ 
/* 7069 */       reportError(re);
/* 7070 */       recover(this.input, re);
/* 7071 */       retval.tree = ((CommonTree)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), re));
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/* 7076 */     return retval;
/*      */   }
/*      */ 
/*      */   public final rewrite_tree_element_return rewrite_tree_element()
/*      */     throws RecognitionException
/*      */   {
/* 7088 */     rewrite_tree_element_return retval = new rewrite_tree_element_return();
/* 7089 */     retval.start = this.input.LT(1);
/*      */ 
/* 7091 */     CommonTree root_0 = null;
/*      */ 
/* 7093 */     rewrite_tree_atom_return rewrite_tree_atom137 = null;
/*      */ 
/* 7095 */     rewrite_tree_atom_return rewrite_tree_atom138 = null;
/*      */ 
/* 7097 */     ebnfSuffix_return ebnfSuffix139 = null;
/*      */ 
/* 7099 */     rewrite_tree_return rewrite_tree140 = null;
/*      */ 
/* 7101 */     ebnfSuffix_return ebnfSuffix141 = null;
/*      */ 
/* 7103 */     rewrite_tree_ebnf_return rewrite_tree_ebnf142 = null;
/*      */ 
/* 7106 */     RewriteRuleSubtreeStream stream_rewrite_tree = new RewriteRuleSubtreeStream(this.adaptor, "rule rewrite_tree");
/* 7107 */     RewriteRuleSubtreeStream stream_ebnfSuffix = new RewriteRuleSubtreeStream(this.adaptor, "rule ebnfSuffix");
/* 7108 */     RewriteRuleSubtreeStream stream_rewrite_tree_atom = new RewriteRuleSubtreeStream(this.adaptor, "rule rewrite_tree_atom");
/*      */     try
/*      */     {
/* 7111 */       int alt67 = 4;
/* 7112 */       alt67 = this.dfa67.predict(this.input);
/*      */       rewrite_tree_element_return localrewrite_tree_element_return1;
/*      */       Object stream_retval;
/*      */       CommonTree root_1;
/*      */       int alt66;
/* 7113 */       switch (alt67)
/*      */       {
/*      */       case 1:
/* 7117 */         root_0 = (CommonTree)this.adaptor.nil();
/*      */ 
/* 7119 */         pushFollow(FOLLOW_rewrite_tree_atom_in_rewrite_tree_element2683);
/* 7120 */         rewrite_tree_atom137 = rewrite_tree_atom();
/*      */ 
/* 7122 */         this.state._fsp -= 1;
/* 7123 */         if (this.state.failed) { localrewrite_tree_element_return1 = retval; return localrewrite_tree_element_return1; }
/* 7124 */         if (this.state.backtracking == 0) this.adaptor.addChild(root_0, rewrite_tree_atom137.getTree()); break;
/*      */       case 2:
/* 7131 */         pushFollow(FOLLOW_rewrite_tree_atom_in_rewrite_tree_element2688);
/* 7132 */         rewrite_tree_atom138 = rewrite_tree_atom();
/*      */ 
/* 7134 */         this.state._fsp -= 1;
/* 7135 */         if (this.state.failed) { localrewrite_tree_element_return1 = retval; return localrewrite_tree_element_return1; }
/* 7136 */         if (this.state.backtracking == 0) stream_rewrite_tree_atom.add(rewrite_tree_atom138.getTree());
/* 7137 */         pushFollow(FOLLOW_ebnfSuffix_in_rewrite_tree_element2690);
/* 7138 */         ebnfSuffix139 = ebnfSuffix();
/*      */ 
/* 7140 */         this.state._fsp -= 1;
/* 7141 */         if (this.state.failed) { localrewrite_tree_element_return1 = retval; return localrewrite_tree_element_return1; }
/* 7142 */         if (this.state.backtracking == 0) stream_ebnfSuffix.add(ebnfSuffix139.getTree());
/*      */ 
/* 7152 */         if (this.state.backtracking == 0) {
/* 7153 */           retval.tree = root_0;
/* 7154 */           stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.tree : null);
/*      */ 
/* 7156 */           root_0 = (CommonTree)this.adaptor.nil();
/*      */ 
/* 7161 */           root_1 = (CommonTree)this.adaptor.nil();
/* 7162 */           root_1 = (CommonTree)this.adaptor.becomeRoot(stream_ebnfSuffix.nextNode(), root_1);
/*      */ 
/* 7166 */           CommonTree root_2 = (CommonTree)this.adaptor.nil();
/* 7167 */           root_2 = (CommonTree)this.adaptor.becomeRoot((CommonTree)this.adaptor.create(8, "BLOCK"), root_2);
/*      */ 
/* 7171 */           CommonTree root_3 = (CommonTree)this.adaptor.nil();
/* 7172 */           root_3 = (CommonTree)this.adaptor.becomeRoot((CommonTree)this.adaptor.create(16, "ALT"), root_3);
/*      */ 
/* 7174 */           this.adaptor.addChild(root_3, stream_rewrite_tree_atom.nextTree());
/* 7175 */           this.adaptor.addChild(root_3, (CommonTree)this.adaptor.create(19, "EOA"));
/*      */ 
/* 7177 */           this.adaptor.addChild(root_2, root_3);
/*      */ 
/* 7179 */           this.adaptor.addChild(root_2, (CommonTree)this.adaptor.create(18, "EOB"));
/*      */ 
/* 7181 */           this.adaptor.addChild(root_1, root_2);
/*      */ 
/* 7184 */           this.adaptor.addChild(root_0, root_1);
/*      */ 
/* 7189 */           retval.tree = root_0; } break;
/*      */       case 3:
/* 7195 */         pushFollow(FOLLOW_rewrite_tree_in_rewrite_tree_element2724);
/* 7196 */         rewrite_tree140 = rewrite_tree();
/*      */ 
/* 7198 */         this.state._fsp -= 1;
/* 7199 */         if (this.state.failed) { stream_retval = retval; return stream_retval; }
/* 7200 */         if (this.state.backtracking == 0) stream_rewrite_tree.add(rewrite_tree140.getTree());
/*      */ 
/* 7202 */         alt66 = 2;
/*      */         NoViableAltException nvae;
/* 7203 */         switch (this.input.LA(1))
/*      */         {
/*      */         case 74:
/*      */         case 90:
/*      */         case 91:
/* 7208 */           alt66 = 1;
/*      */ 
/* 7210 */           break;
/*      */         case -1:
/*      */         case 37:
/*      */         case 40:
/*      */         case 42:
/*      */         case 43:
/*      */         case 44:
/*      */         case 45:
/*      */         case 49:
/*      */         case 69:
/*      */         case 82:
/*      */         case 83:
/*      */         case 84:
/*      */         case 93:
/* 7225 */           alt66 = 2;
/*      */ 
/* 7227 */           break;
/*      */         default:
/* 7229 */           if (this.state.backtracking > 0) { this.state.failed = true; root_1 = retval; return root_1; }
/* 7230 */           nvae = new NoViableAltException("", 66, 0, this.input);
/*      */ 
/* 7233 */           throw nvae;
/*      */         }
/*      */ 
/* 7236 */         switch (alt66)
/*      */         {
/*      */         case 1:
/* 7240 */           pushFollow(FOLLOW_ebnfSuffix_in_rewrite_tree_element2730);
/* 7241 */           ebnfSuffix141 = ebnfSuffix();
/*      */ 
/* 7243 */           this.state._fsp -= 1;
/* 7244 */           if (this.state.failed) { nvae = retval; return nvae; }
/* 7245 */           if (this.state.backtracking == 0) stream_ebnfSuffix.add(ebnfSuffix141.getTree());
/*      */ 
/* 7255 */           if (this.state.backtracking == 0) {
/* 7256 */             retval.tree = root_0;
/* 7257 */             RewriteRuleSubtreeStream stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.tree : null);
/*      */ 
/* 7259 */             root_0 = (CommonTree)this.adaptor.nil();
/*      */ 
/* 7264 */             CommonTree root_1 = (CommonTree)this.adaptor.nil();
/* 7265 */             root_1 = (CommonTree)this.adaptor.becomeRoot(stream_ebnfSuffix.nextNode(), root_1);
/*      */ 
/* 7269 */             CommonTree root_2 = (CommonTree)this.adaptor.nil();
/* 7270 */             root_2 = (CommonTree)this.adaptor.becomeRoot((CommonTree)this.adaptor.create(8, "BLOCK"), root_2);
/*      */ 
/* 7274 */             CommonTree root_3 = (CommonTree)this.adaptor.nil();
/* 7275 */             root_3 = (CommonTree)this.adaptor.becomeRoot((CommonTree)this.adaptor.create(16, "ALT"), root_3);
/*      */ 
/* 7277 */             this.adaptor.addChild(root_3, stream_rewrite_tree.nextTree());
/* 7278 */             this.adaptor.addChild(root_3, (CommonTree)this.adaptor.create(19, "EOA"));
/*      */ 
/* 7280 */             this.adaptor.addChild(root_2, root_3);
/*      */ 
/* 7282 */             this.adaptor.addChild(root_2, (CommonTree)this.adaptor.create(18, "EOB"));
/*      */ 
/* 7284 */             this.adaptor.addChild(root_1, root_2);
/*      */ 
/* 7287 */             this.adaptor.addChild(root_0, root_1);
/*      */ 
/* 7292 */             retval.tree = root_0; } break;
/*      */         case 2:
/* 7306 */           if (this.state.backtracking == 0) {
/* 7307 */             retval.tree = root_0;
/* 7308 */             RewriteRuleSubtreeStream stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.tree : null);
/*      */ 
/* 7310 */             root_0 = (CommonTree)this.adaptor.nil();
/*      */ 
/* 7313 */             this.adaptor.addChild(root_0, stream_rewrite_tree.nextTree());
/*      */ 
/* 7317 */             retval.tree = root_0;
/*      */           }
/*      */ 
/*      */           break;
/*      */         }
/*      */ 
/* 7325 */         break;
/*      */       case 4:
/* 7329 */         root_0 = (CommonTree)this.adaptor.nil();
/*      */ 
/* 7331 */         pushFollow(FOLLOW_rewrite_tree_ebnf_in_rewrite_tree_element2776);
/* 7332 */         rewrite_tree_ebnf142 = rewrite_tree_ebnf();
/*      */ 
/* 7334 */         this.state._fsp -= 1;
/* 7335 */         if (this.state.failed) { rewrite_tree_element_return localrewrite_tree_element_return2 = retval; return localrewrite_tree_element_return2; }
/* 7336 */         if (this.state.backtracking == 0) this.adaptor.addChild(root_0, rewrite_tree_ebnf142.getTree());
/*      */ 
/*      */         break;
/*      */       }
/*      */ 
/* 7342 */       retval.stop = this.input.LT(-1);
/*      */ 
/* 7344 */       if (this.state.backtracking == 0)
/*      */       {
/* 7346 */         retval.tree = ((CommonTree)this.adaptor.rulePostProcessing(root_0));
/* 7347 */         this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
/*      */       }
/*      */     } catch (RecognitionException re) {
/* 7350 */       re = 
/* 7357 */         re;
/*      */ 
/* 7351 */       reportError(re);
/* 7352 */       recover(this.input, re);
/* 7353 */       retval.tree = ((CommonTree)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), re));
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/* 7358 */     return retval;
/*      */   }
/*      */ 
/*      */   public final rewrite_tree_atom_return rewrite_tree_atom()
/*      */     throws RecognitionException
/*      */   {
/* 7370 */     rewrite_tree_atom_return retval = new rewrite_tree_atom_return();
/* 7371 */     retval.start = this.input.LT(1);
/*      */ 
/* 7373 */     CommonTree root_0 = null;
/*      */ 
/* 7375 */     Token d = null;
/* 7376 */     Token CHAR_LITERAL143 = null;
/* 7377 */     Token TOKEN_REF144 = null;
/* 7378 */     Token ARG_ACTION145 = null;
/* 7379 */     Token RULE_REF146 = null;
/* 7380 */     Token STRING_LITERAL147 = null;
/* 7381 */     Token ACTION149 = null;
/* 7382 */     id_return id148 = null;
/*      */ 
/* 7385 */     CommonTree d_tree = null;
/* 7386 */     CommonTree CHAR_LITERAL143_tree = null;
/* 7387 */     CommonTree TOKEN_REF144_tree = null;
/* 7388 */     CommonTree ARG_ACTION145_tree = null;
/* 7389 */     CommonTree RULE_REF146_tree = null;
/* 7390 */     CommonTree STRING_LITERAL147_tree = null;
/* 7391 */     CommonTree ACTION149_tree = null;
/* 7392 */     RewriteRuleTokenStream stream_93 = new RewriteRuleTokenStream(this.adaptor, "token 93");
/* 7393 */     RewriteRuleTokenStream stream_TOKEN_REF = new RewriteRuleTokenStream(this.adaptor, "token TOKEN_REF");
/* 7394 */     RewriteRuleTokenStream stream_ARG_ACTION = new RewriteRuleTokenStream(this.adaptor, "token ARG_ACTION");
/* 7395 */     RewriteRuleSubtreeStream stream_id = new RewriteRuleSubtreeStream(this.adaptor, "rule id");
/*      */     try
/*      */     {
/* 7398 */       int alt69 = 6;
/*      */       Object nvae;
/* 7399 */       switch (this.input.LA(1))
/*      */       {
/*      */       case 44:
/* 7402 */         alt69 = 1;
/*      */ 
/* 7404 */         break;
/*      */       case 42:
/* 7407 */         alt69 = 2;
/*      */ 
/* 7409 */         break;
/*      */       case 49:
/* 7412 */         alt69 = 3;
/*      */ 
/* 7414 */         break;
/*      */       case 43:
/* 7417 */         alt69 = 4;
/*      */ 
/* 7419 */         break;
/*      */       case 93:
/* 7422 */         alt69 = 5;
/*      */ 
/* 7424 */         break;
/*      */       case 45:
/* 7427 */         alt69 = 6;
/*      */ 
/* 7429 */         break;
/*      */       default:
/* 7431 */         if (this.state.backtracking > 0) { this.state.failed = true; rewrite_tree_atom_return localrewrite_tree_atom_return1 = retval; return localrewrite_tree_atom_return1; }
/* 7432 */         nvae = new NoViableAltException("", 69, 0, this.input);
/*      */ 
/* 7435 */         throw ((Throwable)nvae);
/*      */       }
/*      */       int alt68;
/*      */       rewrite_tree_atom_return localrewrite_tree_atom_return2;
/*      */       Object stream_retval;
/* 7438 */       switch (alt69)
/*      */       {
/*      */       case 1:
/* 7442 */         root_0 = (CommonTree)this.adaptor.nil();
/*      */ 
/* 7444 */         CHAR_LITERAL143 = (Token)match(this.input, 44, FOLLOW_CHAR_LITERAL_in_rewrite_tree_atom2792); if (this.state.failed) { nvae = retval; return nvae; }
/* 7445 */         if (this.state.backtracking == 0) {
/* 7446 */           CHAR_LITERAL143_tree = (CommonTree)this.adaptor.create(CHAR_LITERAL143);
/* 7447 */           this.adaptor.addChild(root_0, CHAR_LITERAL143_tree); } break;
/*      */       case 2:
/* 7455 */         TOKEN_REF144 = (Token)match(this.input, 42, FOLLOW_TOKEN_REF_in_rewrite_tree_atom2799); if (this.state.failed) { nvae = retval; return nvae; }
/* 7456 */         if (this.state.backtracking == 0) stream_TOKEN_REF.add(TOKEN_REF144);
/*      */ 
/* 7459 */         alt68 = 2;
/* 7460 */         switch (this.input.LA(1))
/*      */         {
/*      */         case 48:
/* 7463 */           alt68 = 1;
/*      */         }
/*      */ 
/* 7468 */         switch (alt68)
/*      */         {
/*      */         case 1:
/* 7472 */           ARG_ACTION145 = (Token)match(this.input, 48, FOLLOW_ARG_ACTION_in_rewrite_tree_atom2801); if (this.state.failed) { rewrite_tree_atom_return localrewrite_tree_atom_return3 = retval; return localrewrite_tree_atom_return3; }
/* 7473 */           if (this.state.backtracking == 0) stream_ARG_ACTION.add(ARG_ACTION145);
/*      */ 
/*      */           break;
/*      */         }
/*      */ 
/* 7490 */         if (this.state.backtracking == 0) {
/* 7491 */           retval.tree = root_0;
/* 7492 */           RewriteRuleSubtreeStream stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.tree : null);
/*      */ 
/* 7494 */           root_0 = (CommonTree)this.adaptor.nil();
/*      */ 
/* 7499 */           CommonTree root_1 = (CommonTree)this.adaptor.nil();
/* 7500 */           root_1 = (CommonTree)this.adaptor.becomeRoot(stream_TOKEN_REF.nextNode(), root_1);
/*      */ 
/* 7503 */           if (stream_ARG_ACTION.hasNext()) {
/* 7504 */             this.adaptor.addChild(root_1, stream_ARG_ACTION.nextNode());
/*      */           }
/*      */ 
/* 7507 */           stream_ARG_ACTION.reset();
/*      */ 
/* 7509 */           this.adaptor.addChild(root_0, root_1);
/*      */ 
/* 7514 */           retval.tree = root_0;
/*      */         }
/* 7516 */         break;
/*      */       case 3:
/* 7520 */         root_0 = (CommonTree)this.adaptor.nil();
/*      */ 
/* 7522 */         RULE_REF146 = (Token)match(this.input, 49, FOLLOW_RULE_REF_in_rewrite_tree_atom2822); if (this.state.failed) { localrewrite_tree_atom_return2 = retval; return localrewrite_tree_atom_return2; }
/* 7523 */         if (this.state.backtracking == 0) {
/* 7524 */           RULE_REF146_tree = (CommonTree)this.adaptor.create(RULE_REF146);
/* 7525 */           this.adaptor.addChild(root_0, RULE_REF146_tree); } break;
/*      */       case 4:
/* 7533 */         root_0 = (CommonTree)this.adaptor.nil();
/*      */ 
/* 7535 */         STRING_LITERAL147 = (Token)match(this.input, 43, FOLLOW_STRING_LITERAL_in_rewrite_tree_atom2829); if (this.state.failed) { localrewrite_tree_atom_return2 = retval; return localrewrite_tree_atom_return2; }
/* 7536 */         if (this.state.backtracking == 0) {
/* 7537 */           STRING_LITERAL147_tree = (CommonTree)this.adaptor.create(STRING_LITERAL147);
/* 7538 */           this.adaptor.addChild(root_0, STRING_LITERAL147_tree); } break;
/*      */       case 5:
/* 7546 */         d = (Token)match(this.input, 93, FOLLOW_93_in_rewrite_tree_atom2838); if (this.state.failed) { localrewrite_tree_atom_return2 = retval; return localrewrite_tree_atom_return2; }
/* 7547 */         if (this.state.backtracking == 0) stream_93.add(d);
/*      */ 
/* 7549 */         pushFollow(FOLLOW_id_in_rewrite_tree_atom2840);
/* 7550 */         id148 = id();
/*      */ 
/* 7552 */         this.state._fsp -= 1;
/* 7553 */         if (this.state.failed) { localrewrite_tree_atom_return2 = retval; return localrewrite_tree_atom_return2; }
/* 7554 */         if (this.state.backtracking == 0) stream_id.add(id148.getTree());
/*      */ 
/* 7564 */         if (this.state.backtracking == 0) {
/* 7565 */           retval.tree = root_0;
/* 7566 */           stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.tree : null);
/*      */ 
/* 7568 */           root_0 = (CommonTree)this.adaptor.nil();
/*      */ 
/* 7571 */           this.adaptor.addChild(root_0, (CommonTree)this.adaptor.create(29, d, id148 != null ? this.input.toString(id148.start, id148.stop) : null));
/*      */ 
/* 7575 */           retval.tree = root_0; } break;
/*      */       case 6:
/* 7581 */         root_0 = (CommonTree)this.adaptor.nil();
/*      */ 
/* 7583 */         ACTION149 = (Token)match(this.input, 45, FOLLOW_ACTION_in_rewrite_tree_atom2851); if (this.state.failed) { stream_retval = retval; return stream_retval; }
/* 7584 */         if (this.state.backtracking == 0) {
/* 7585 */           ACTION149_tree = (CommonTree)this.adaptor.create(ACTION149);
/* 7586 */           this.adaptor.addChild(root_0, ACTION149_tree);
/*      */         }
/*      */ 
/*      */         break;
/*      */       }
/*      */ 
/* 7593 */       retval.stop = this.input.LT(-1);
/*      */ 
/* 7595 */       if (this.state.backtracking == 0)
/*      */       {
/* 7597 */         retval.tree = ((CommonTree)this.adaptor.rulePostProcessing(root_0));
/* 7598 */         this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
/*      */       }
/*      */     } catch (RecognitionException re) {
/* 7601 */       re = 
/* 7608 */         re;
/*      */ 
/* 7602 */       reportError(re);
/* 7603 */       recover(this.input, re);
/* 7604 */       retval.tree = ((CommonTree)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), re));
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/* 7609 */     return retval;
/*      */   }
/*      */ 
/*      */   public final rewrite_tree_ebnf_return rewrite_tree_ebnf()
/*      */     throws RecognitionException
/*      */   {
/* 7621 */     rewrite_tree_ebnf_return retval = new rewrite_tree_ebnf_return();
/* 7622 */     retval.start = this.input.LT(1);
/*      */ 
/* 7624 */     CommonTree root_0 = null;
/*      */ 
/* 7626 */     rewrite_tree_block_return rewrite_tree_block150 = null;
/*      */ 
/* 7628 */     ebnfSuffix_return ebnfSuffix151 = null;
/*      */ 
/* 7631 */     RewriteRuleSubtreeStream stream_ebnfSuffix = new RewriteRuleSubtreeStream(this.adaptor, "rule ebnfSuffix");
/* 7632 */     RewriteRuleSubtreeStream stream_rewrite_tree_block = new RewriteRuleSubtreeStream(this.adaptor, "rule rewrite_tree_block");
/*      */ 
/* 7634 */     Token firstToken = this.input.LT(1);
/*      */     try
/*      */     {
/* 7640 */       pushFollow(FOLLOW_rewrite_tree_block_in_rewrite_tree_ebnf2872);
/* 7641 */       rewrite_tree_block150 = rewrite_tree_block();
/*      */ 
/* 7643 */       this.state._fsp -= 1;
/*      */       rewrite_tree_ebnf_return localrewrite_tree_ebnf_return1;
/* 7644 */       if (this.state.failed) { localrewrite_tree_ebnf_return1 = retval; return localrewrite_tree_ebnf_return1; }
/* 7645 */       if (this.state.backtracking == 0) stream_rewrite_tree_block.add(rewrite_tree_block150.getTree());
/* 7646 */       pushFollow(FOLLOW_ebnfSuffix_in_rewrite_tree_ebnf2874);
/* 7647 */       ebnfSuffix151 = ebnfSuffix();
/*      */ 
/* 7649 */       this.state._fsp -= 1;
/* 7650 */       if (this.state.failed) { localrewrite_tree_ebnf_return1 = retval; return localrewrite_tree_ebnf_return1; }
/* 7651 */       if (this.state.backtracking == 0) stream_ebnfSuffix.add(ebnfSuffix151.getTree());
/*      */ 
/* 7661 */       if (this.state.backtracking == 0) {
/* 7662 */         retval.tree = root_0;
/* 7663 */         RewriteRuleSubtreeStream stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.tree : null);
/*      */ 
/* 7665 */         root_0 = (CommonTree)this.adaptor.nil();
/*      */ 
/* 7670 */         CommonTree root_1 = (CommonTree)this.adaptor.nil();
/* 7671 */         root_1 = (CommonTree)this.adaptor.becomeRoot(stream_ebnfSuffix.nextNode(), root_1);
/*      */ 
/* 7673 */         this.adaptor.addChild(root_1, stream_rewrite_tree_block.nextTree());
/*      */ 
/* 7675 */         this.adaptor.addChild(root_0, root_1);
/*      */ 
/* 7680 */         retval.tree = root_0;
/*      */       }
/*      */ 
/* 7683 */       retval.stop = this.input.LT(-1);
/*      */ 
/* 7685 */       if (this.state.backtracking == 0)
/*      */       {
/* 7687 */         retval.tree = ((CommonTree)this.adaptor.rulePostProcessing(root_0));
/* 7688 */         this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
/*      */       }
/* 7690 */       if (this.state.backtracking == 0)
/*      */       {
/* 7692 */         retval.tree.getToken().setLine(firstToken.getLine());
/* 7693 */         retval.tree.getToken().setCharPositionInLine(firstToken.getCharPositionInLine());
/*      */       }
/*      */     }
/*      */     catch (RecognitionException re) {
/* 7697 */       re = 
/* 7704 */         re;
/*      */ 
/* 7698 */       reportError(re);
/* 7699 */       recover(this.input, re);
/* 7700 */       retval.tree = ((CommonTree)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), re));
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/* 7705 */     return retval;
/*      */   }
/*      */ 
/*      */   public final rewrite_tree_return rewrite_tree()
/*      */     throws RecognitionException
/*      */   {
/* 7717 */     rewrite_tree_return retval = new rewrite_tree_return();
/* 7718 */     retval.start = this.input.LT(1);
/*      */ 
/* 7720 */     CommonTree root_0 = null;
/*      */ 
/* 7722 */     Token string_literal152 = null;
/* 7723 */     Token char_literal155 = null;
/* 7724 */     rewrite_tree_atom_return rewrite_tree_atom153 = null;
/*      */ 
/* 7726 */     rewrite_tree_element_return rewrite_tree_element154 = null;
/*      */ 
/* 7729 */     CommonTree string_literal152_tree = null;
/* 7730 */     CommonTree char_literal155_tree = null;
/* 7731 */     RewriteRuleTokenStream stream_TREE_BEGIN = new RewriteRuleTokenStream(this.adaptor, "token TREE_BEGIN");
/* 7732 */     RewriteRuleTokenStream stream_84 = new RewriteRuleTokenStream(this.adaptor, "token 84");
/* 7733 */     RewriteRuleSubtreeStream stream_rewrite_tree_element = new RewriteRuleSubtreeStream(this.adaptor, "rule rewrite_tree_element");
/* 7734 */     RewriteRuleSubtreeStream stream_rewrite_tree_atom = new RewriteRuleSubtreeStream(this.adaptor, "rule rewrite_tree_atom");
/*      */     try
/*      */     {
/* 7739 */       string_literal152 = (Token)match(this.input, 37, FOLLOW_TREE_BEGIN_in_rewrite_tree2894);
/*      */       rewrite_tree_return localrewrite_tree_return1;
/* 7739 */       if (this.state.failed) { localrewrite_tree_return1 = retval; return localrewrite_tree_return1; }
/* 7740 */       if (this.state.backtracking == 0) stream_TREE_BEGIN.add(string_literal152);
/*      */ 
/* 7742 */       pushFollow(FOLLOW_rewrite_tree_atom_in_rewrite_tree2896);
/* 7743 */       rewrite_tree_atom153 = rewrite_tree_atom();
/*      */ 
/* 7745 */       this.state._fsp -= 1;
/* 7746 */       if (this.state.failed) { localrewrite_tree_return1 = retval; return localrewrite_tree_return1; }
/* 7747 */       if (this.state.backtracking == 0) stream_rewrite_tree_atom.add(rewrite_tree_atom153.getTree());
/*      */       int alt70;
/*      */       while (true)
/*      */       {
/* 7751 */         alt70 = 2;
/* 7752 */         switch (this.input.LA(1))
/*      */         {
/*      */         case 37:
/*      */         case 42:
/*      */         case 43:
/*      */         case 44:
/*      */         case 45:
/*      */         case 49:
/*      */         case 82:
/*      */         case 93:
/* 7762 */           alt70 = 1;
/*      */         }
/*      */ 
/* 7768 */         switch (alt70)
/*      */         {
/*      */         case 1:
/* 7772 */           pushFollow(FOLLOW_rewrite_tree_element_in_rewrite_tree2898);
/* 7773 */           rewrite_tree_element154 = rewrite_tree_element();
/*      */ 
/* 7775 */           this.state._fsp -= 1;
/* 7776 */           if (this.state.failed) { rewrite_tree_return localrewrite_tree_return3 = retval; return localrewrite_tree_return3; }
/* 7777 */           if (this.state.backtracking == 0) stream_rewrite_tree_element.add(rewrite_tree_element154.getTree()); break;
/*      */         default:
/* 7783 */           break label399;
/*      */         }
/*      */       }
/*      */ 
/* 7787 */       label399: char_literal155 = (Token)match(this.input, 84, FOLLOW_84_in_rewrite_tree2901); if (this.state.failed) { rewrite_tree_return localrewrite_tree_return2 = retval; return localrewrite_tree_return2; }
/* 7788 */       if (this.state.backtracking == 0) stream_84.add(char_literal155);
/*      */ 
/* 7799 */       if (this.state.backtracking == 0) {
/* 7800 */         retval.tree = root_0;
/* 7801 */         RewriteRuleSubtreeStream stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.tree : null);
/*      */ 
/* 7803 */         root_0 = (CommonTree)this.adaptor.nil();
/*      */ 
/* 7808 */         CommonTree root_1 = (CommonTree)this.adaptor.nil();
/* 7809 */         root_1 = (CommonTree)this.adaptor.becomeRoot((CommonTree)this.adaptor.create(37, "TREE_BEGIN"), root_1);
/*      */ 
/* 7811 */         this.adaptor.addChild(root_1, stream_rewrite_tree_atom.nextTree());
/*      */ 
/* 7813 */         while (stream_rewrite_tree_element.hasNext()) {
/* 7814 */           this.adaptor.addChild(root_1, stream_rewrite_tree_element.nextTree());
/*      */         }
/*      */ 
/* 7817 */         stream_rewrite_tree_element.reset();
/*      */ 
/* 7819 */         this.adaptor.addChild(root_0, root_1);
/*      */ 
/* 7824 */         retval.tree = root_0;
/*      */       }
/*      */ 
/* 7827 */       retval.stop = this.input.LT(-1);
/*      */ 
/* 7829 */       if (this.state.backtracking == 0)
/*      */       {
/* 7831 */         retval.tree = ((CommonTree)this.adaptor.rulePostProcessing(root_0));
/* 7832 */         this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
/*      */       }
/*      */     } catch (RecognitionException re) {
/* 7835 */       re = 
/* 7842 */         re;
/*      */ 
/* 7836 */       reportError(re);
/* 7837 */       recover(this.input, re);
/* 7838 */       retval.tree = ((CommonTree)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), re));
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/* 7843 */     return retval;
/*      */   }
/*      */ 
/*      */   public final rewrite_template_return rewrite_template()
/*      */     throws RecognitionException
/*      */   {
/* 7855 */     rewrite_template_return retval = new rewrite_template_return();
/* 7856 */     retval.start = this.input.LT(1);
/*      */ 
/* 7858 */     CommonTree root_0 = null;
/*      */ 
/* 7860 */     Token lp = null;
/* 7861 */     Token str = null;
/* 7862 */     Token char_literal158 = null;
/* 7863 */     Token ACTION161 = null;
/* 7864 */     id_return id156 = null;
/*      */ 
/* 7866 */     rewrite_template_args_return rewrite_template_args157 = null;
/*      */ 
/* 7868 */     rewrite_template_ref_return rewrite_template_ref159 = null;
/*      */ 
/* 7870 */     rewrite_indirect_template_head_return rewrite_indirect_template_head160 = null;
/*      */ 
/* 7873 */     CommonTree lp_tree = null;
/* 7874 */     CommonTree str_tree = null;
/* 7875 */     CommonTree char_literal158_tree = null;
/* 7876 */     CommonTree ACTION161_tree = null;
/* 7877 */     RewriteRuleTokenStream stream_DOUBLE_QUOTE_STRING_LITERAL = new RewriteRuleTokenStream(this.adaptor, "token DOUBLE_QUOTE_STRING_LITERAL");
/* 7878 */     RewriteRuleTokenStream stream_82 = new RewriteRuleTokenStream(this.adaptor, "token 82");
/* 7879 */     RewriteRuleTokenStream stream_DOUBLE_ANGLE_STRING_LITERAL = new RewriteRuleTokenStream(this.adaptor, "token DOUBLE_ANGLE_STRING_LITERAL");
/* 7880 */     RewriteRuleTokenStream stream_84 = new RewriteRuleTokenStream(this.adaptor, "token 84");
/* 7881 */     RewriteRuleSubtreeStream stream_id = new RewriteRuleSubtreeStream(this.adaptor, "rule id");
/* 7882 */     RewriteRuleSubtreeStream stream_rewrite_template_args = new RewriteRuleSubtreeStream(this.adaptor, "rule rewrite_template_args");
/*      */     try
/*      */     {
/* 7885 */       int alt72 = 4;
/* 7886 */       alt72 = this.dfa72.predict(this.input);
/*      */       int alt71;
/*      */       rewrite_template_return localrewrite_template_return2;
/* 7887 */       switch (alt72)
/*      */       {
/*      */       case 1:
/* 7891 */         pushFollow(FOLLOW_id_in_rewrite_template2933);
/* 7892 */         id156 = id();
/*      */ 
/* 7894 */         this.state._fsp -= 1;
/*      */         rewrite_template_return localrewrite_template_return1;
/* 7895 */         if (this.state.failed) { localrewrite_template_return1 = retval; return localrewrite_template_return1; }
/* 7896 */         if (this.state.backtracking == 0) stream_id.add(id156.getTree());
/* 7897 */         lp = (Token)match(this.input, 82, FOLLOW_82_in_rewrite_template2937); if (this.state.failed) { localrewrite_template_return1 = retval; return localrewrite_template_return1; }
/* 7898 */         if (this.state.backtracking == 0) stream_82.add(lp);
/*      */ 
/* 7900 */         pushFollow(FOLLOW_rewrite_template_args_in_rewrite_template2939);
/* 7901 */         rewrite_template_args157 = rewrite_template_args();
/*      */ 
/* 7903 */         this.state._fsp -= 1;
/* 7904 */         if (this.state.failed) { localrewrite_template_return1 = retval; return localrewrite_template_return1; }
/* 7905 */         if (this.state.backtracking == 0) stream_rewrite_template_args.add(rewrite_template_args157.getTree());
/* 7906 */         char_literal158 = (Token)match(this.input, 84, FOLLOW_84_in_rewrite_template2941); if (this.state.failed) { localrewrite_template_return1 = retval; return localrewrite_template_return1; }
/* 7907 */         if (this.state.backtracking == 0) stream_84.add(char_literal158);
/*      */ 
/* 7910 */         alt71 = 2;
/*      */         Object nvae;
/* 7911 */         switch (this.input.LA(1))
/*      */         {
/*      */         case 50:
/* 7914 */           alt71 = 1;
/*      */ 
/* 7916 */           break;
/*      */         case 51:
/* 7919 */           alt71 = 2;
/*      */ 
/* 7921 */           break;
/*      */         default:
/* 7923 */           if (this.state.backtracking > 0) { this.state.failed = true; rewrite_template_return localrewrite_template_return3 = retval; return localrewrite_template_return3; }
/* 7924 */           nvae = new NoViableAltException("", 71, 0, this.input);
/*      */ 
/* 7927 */           throw ((Throwable)nvae);
/*      */         }
/*      */ 
/* 7930 */         switch (alt71)
/*      */         {
/*      */         case 1:
/* 7934 */           str = (Token)match(this.input, 50, FOLLOW_DOUBLE_QUOTE_STRING_LITERAL_in_rewrite_template2949); if (this.state.failed) { nvae = retval; return nvae; }
/* 7935 */           if (this.state.backtracking == 0) stream_DOUBLE_QUOTE_STRING_LITERAL.add(str); break;
/*      */         case 2:
/* 7943 */           str = (Token)match(this.input, 51, FOLLOW_DOUBLE_ANGLE_STRING_LITERAL_in_rewrite_template2955); if (this.state.failed) { nvae = retval; return nvae; }
/* 7944 */           if (this.state.backtracking == 0) stream_DOUBLE_ANGLE_STRING_LITERAL.add(str);
/*      */ 
/*      */           break;
/*      */         }
/*      */ 
/* 7961 */         if (this.state.backtracking == 0) {
/* 7962 */           retval.tree = root_0;
/* 7963 */           RewriteRuleTokenStream stream_str = new RewriteRuleTokenStream(this.adaptor, "token str", str);
/* 7964 */           RewriteRuleSubtreeStream stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.tree : null);
/*      */ 
/* 7966 */           root_0 = (CommonTree)this.adaptor.nil();
/*      */ 
/* 7971 */           CommonTree root_1 = (CommonTree)this.adaptor.nil();
/* 7972 */           root_1 = (CommonTree)this.adaptor.becomeRoot((CommonTree)this.adaptor.create(30, lp, "TEMPLATE"), root_1);
/*      */ 
/* 7974 */           this.adaptor.addChild(root_1, stream_id.nextTree());
/* 7975 */           this.adaptor.addChild(root_1, stream_rewrite_template_args.nextTree());
/* 7976 */           this.adaptor.addChild(root_1, stream_str.nextNode());
/*      */ 
/* 7978 */           this.adaptor.addChild(root_0, root_1);
/*      */ 
/* 7983 */           retval.tree = root_0;
/*      */         }
/* 7985 */         break;
/*      */       case 2:
/* 7989 */         root_0 = (CommonTree)this.adaptor.nil();
/*      */ 
/* 7991 */         pushFollow(FOLLOW_rewrite_template_ref_in_rewrite_template2982);
/* 7992 */         rewrite_template_ref159 = rewrite_template_ref();
/*      */ 
/* 7994 */         this.state._fsp -= 1;
/* 7995 */         if (this.state.failed) { localrewrite_template_return2 = retval; return localrewrite_template_return2; }
/* 7996 */         if (this.state.backtracking == 0) this.adaptor.addChild(root_0, rewrite_template_ref159.getTree()); break;
/*      */       case 3:
/* 8003 */         root_0 = (CommonTree)this.adaptor.nil();
/*      */ 
/* 8005 */         pushFollow(FOLLOW_rewrite_indirect_template_head_in_rewrite_template2991);
/* 8006 */         rewrite_indirect_template_head160 = rewrite_indirect_template_head();
/*      */ 
/* 8008 */         this.state._fsp -= 1;
/* 8009 */         if (this.state.failed) { localrewrite_template_return2 = retval; return localrewrite_template_return2; }
/* 8010 */         if (this.state.backtracking == 0) this.adaptor.addChild(root_0, rewrite_indirect_template_head160.getTree()); break;
/*      */       case 4:
/* 8017 */         root_0 = (CommonTree)this.adaptor.nil();
/*      */ 
/* 8019 */         ACTION161 = (Token)match(this.input, 45, FOLLOW_ACTION_in_rewrite_template3000); if (this.state.failed) { localrewrite_template_return2 = retval; return localrewrite_template_return2; }
/* 8020 */         if (this.state.backtracking == 0) {
/* 8021 */           ACTION161_tree = (CommonTree)this.adaptor.create(ACTION161);
/* 8022 */           this.adaptor.addChild(root_0, ACTION161_tree);
/*      */         }
/*      */ 
/*      */         break;
/*      */       }
/*      */ 
/* 8029 */       retval.stop = this.input.LT(-1);
/*      */ 
/* 8031 */       if (this.state.backtracking == 0)
/*      */       {
/* 8033 */         retval.tree = ((CommonTree)this.adaptor.rulePostProcessing(root_0));
/* 8034 */         this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
/*      */       }
/*      */     } catch (RecognitionException re) {
/* 8037 */       re = 
/* 8044 */         re;
/*      */ 
/* 8038 */       reportError(re);
/* 8039 */       recover(this.input, re);
/* 8040 */       retval.tree = ((CommonTree)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), re));
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/* 8045 */     return retval;
/*      */   }
/*      */ 
/*      */   public final rewrite_template_ref_return rewrite_template_ref()
/*      */     throws RecognitionException
/*      */   {
/* 8057 */     rewrite_template_ref_return retval = new rewrite_template_ref_return();
/* 8058 */     retval.start = this.input.LT(1);
/*      */ 
/* 8060 */     CommonTree root_0 = null;
/*      */ 
/* 8062 */     Token lp = null;
/* 8063 */     Token char_literal164 = null;
/* 8064 */     id_return id162 = null;
/*      */ 
/* 8066 */     rewrite_template_args_return rewrite_template_args163 = null;
/*      */ 
/* 8069 */     CommonTree lp_tree = null;
/* 8070 */     CommonTree char_literal164_tree = null;
/* 8071 */     RewriteRuleTokenStream stream_82 = new RewriteRuleTokenStream(this.adaptor, "token 82");
/* 8072 */     RewriteRuleTokenStream stream_84 = new RewriteRuleTokenStream(this.adaptor, "token 84");
/* 8073 */     RewriteRuleSubtreeStream stream_id = new RewriteRuleSubtreeStream(this.adaptor, "rule id");
/* 8074 */     RewriteRuleSubtreeStream stream_rewrite_template_args = new RewriteRuleSubtreeStream(this.adaptor, "rule rewrite_template_args");
/*      */     try
/*      */     {
/* 8079 */       pushFollow(FOLLOW_id_in_rewrite_template_ref3013);
/* 8080 */       id162 = id();
/*      */ 
/* 8082 */       this.state._fsp -= 1;
/*      */       rewrite_template_ref_return localrewrite_template_ref_return1;
/* 8083 */       if (this.state.failed) { localrewrite_template_ref_return1 = retval; return localrewrite_template_ref_return1; }
/* 8084 */       if (this.state.backtracking == 0) stream_id.add(id162.getTree());
/* 8085 */       lp = (Token)match(this.input, 82, FOLLOW_82_in_rewrite_template_ref3017); if (this.state.failed) { localrewrite_template_ref_return1 = retval; return localrewrite_template_ref_return1; }
/* 8086 */       if (this.state.backtracking == 0) stream_82.add(lp);
/*      */ 
/* 8088 */       pushFollow(FOLLOW_rewrite_template_args_in_rewrite_template_ref3019);
/* 8089 */       rewrite_template_args163 = rewrite_template_args();
/*      */ 
/* 8091 */       this.state._fsp -= 1;
/* 8092 */       if (this.state.failed) { localrewrite_template_ref_return1 = retval; return localrewrite_template_ref_return1; }
/* 8093 */       if (this.state.backtracking == 0) stream_rewrite_template_args.add(rewrite_template_args163.getTree());
/* 8094 */       char_literal164 = (Token)match(this.input, 84, FOLLOW_84_in_rewrite_template_ref3021); if (this.state.failed) { localrewrite_template_ref_return1 = retval; return localrewrite_template_ref_return1; }
/* 8095 */       if (this.state.backtracking == 0) stream_84.add(char_literal164);
/*      */ 
/* 8106 */       if (this.state.backtracking == 0) {
/* 8107 */         retval.tree = root_0;
/* 8108 */         RewriteRuleSubtreeStream stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.tree : null);
/*      */ 
/* 8110 */         root_0 = (CommonTree)this.adaptor.nil();
/*      */ 
/* 8115 */         CommonTree root_1 = (CommonTree)this.adaptor.nil();
/* 8116 */         root_1 = (CommonTree)this.adaptor.becomeRoot((CommonTree)this.adaptor.create(30, lp, "TEMPLATE"), root_1);
/*      */ 
/* 8118 */         this.adaptor.addChild(root_1, stream_id.nextTree());
/* 8119 */         this.adaptor.addChild(root_1, stream_rewrite_template_args.nextTree());
/*      */ 
/* 8121 */         this.adaptor.addChild(root_0, root_1);
/*      */ 
/* 8126 */         retval.tree = root_0;
/*      */       }
/*      */ 
/* 8129 */       retval.stop = this.input.LT(-1);
/*      */ 
/* 8131 */       if (this.state.backtracking == 0)
/*      */       {
/* 8133 */         retval.tree = ((CommonTree)this.adaptor.rulePostProcessing(root_0));
/* 8134 */         this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
/*      */       }
/*      */     } catch (RecognitionException re) {
/* 8137 */       re = 
/* 8144 */         re;
/*      */ 
/* 8138 */       reportError(re);
/* 8139 */       recover(this.input, re);
/* 8140 */       retval.tree = ((CommonTree)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), re));
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/* 8145 */     return retval;
/*      */   }
/*      */ 
/*      */   public final rewrite_indirect_template_head_return rewrite_indirect_template_head()
/*      */     throws RecognitionException
/*      */   {
/* 8157 */     rewrite_indirect_template_head_return retval = new rewrite_indirect_template_head_return();
/* 8158 */     retval.start = this.input.LT(1);
/*      */ 
/* 8160 */     CommonTree root_0 = null;
/*      */ 
/* 8162 */     Token lp = null;
/* 8163 */     Token ACTION165 = null;
/* 8164 */     Token char_literal166 = null;
/* 8165 */     Token char_literal167 = null;
/* 8166 */     Token char_literal169 = null;
/* 8167 */     rewrite_template_args_return rewrite_template_args168 = null;
/*      */ 
/* 8170 */     CommonTree lp_tree = null;
/* 8171 */     CommonTree ACTION165_tree = null;
/* 8172 */     CommonTree char_literal166_tree = null;
/* 8173 */     CommonTree char_literal167_tree = null;
/* 8174 */     CommonTree char_literal169_tree = null;
/* 8175 */     RewriteRuleTokenStream stream_82 = new RewriteRuleTokenStream(this.adaptor, "token 82");
/* 8176 */     RewriteRuleTokenStream stream_ACTION = new RewriteRuleTokenStream(this.adaptor, "token ACTION");
/* 8177 */     RewriteRuleTokenStream stream_84 = new RewriteRuleTokenStream(this.adaptor, "token 84");
/* 8178 */     RewriteRuleSubtreeStream stream_rewrite_template_args = new RewriteRuleSubtreeStream(this.adaptor, "rule rewrite_template_args");
/*      */     try
/*      */     {
/* 8183 */       lp = (Token)match(this.input, 82, FOLLOW_82_in_rewrite_indirect_template_head3049);
/*      */       rewrite_indirect_template_head_return localrewrite_indirect_template_head_return1;
/* 8183 */       if (this.state.failed) { localrewrite_indirect_template_head_return1 = retval; return localrewrite_indirect_template_head_return1; }
/* 8184 */       if (this.state.backtracking == 0) stream_82.add(lp);
/*      */ 
/* 8186 */       ACTION165 = (Token)match(this.input, 45, FOLLOW_ACTION_in_rewrite_indirect_template_head3051); if (this.state.failed) { localrewrite_indirect_template_head_return1 = retval; return localrewrite_indirect_template_head_return1; }
/* 8187 */       if (this.state.backtracking == 0) stream_ACTION.add(ACTION165);
/*      */ 
/* 8189 */       char_literal166 = (Token)match(this.input, 84, FOLLOW_84_in_rewrite_indirect_template_head3053); if (this.state.failed) { localrewrite_indirect_template_head_return1 = retval; return localrewrite_indirect_template_head_return1; }
/* 8190 */       if (this.state.backtracking == 0) stream_84.add(char_literal166);
/*      */ 
/* 8192 */       char_literal167 = (Token)match(this.input, 82, FOLLOW_82_in_rewrite_indirect_template_head3055); if (this.state.failed) { localrewrite_indirect_template_head_return1 = retval; return localrewrite_indirect_template_head_return1; }
/* 8193 */       if (this.state.backtracking == 0) stream_82.add(char_literal167);
/*      */ 
/* 8195 */       pushFollow(FOLLOW_rewrite_template_args_in_rewrite_indirect_template_head3057);
/* 8196 */       rewrite_template_args168 = rewrite_template_args();
/*      */ 
/* 8198 */       this.state._fsp -= 1;
/* 8199 */       if (this.state.failed) { localrewrite_indirect_template_head_return1 = retval; return localrewrite_indirect_template_head_return1; }
/* 8200 */       if (this.state.backtracking == 0) stream_rewrite_template_args.add(rewrite_template_args168.getTree());
/* 8201 */       char_literal169 = (Token)match(this.input, 84, FOLLOW_84_in_rewrite_indirect_template_head3059); if (this.state.failed) { localrewrite_indirect_template_head_return1 = retval; return localrewrite_indirect_template_head_return1; }
/* 8202 */       if (this.state.backtracking == 0) stream_84.add(char_literal169);
/*      */ 
/* 8213 */       if (this.state.backtracking == 0) {
/* 8214 */         retval.tree = root_0;
/* 8215 */         RewriteRuleSubtreeStream stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.tree : null);
/*      */ 
/* 8217 */         root_0 = (CommonTree)this.adaptor.nil();
/*      */ 
/* 8222 */         CommonTree root_1 = (CommonTree)this.adaptor.nil();
/* 8223 */         root_1 = (CommonTree)this.adaptor.becomeRoot((CommonTree)this.adaptor.create(30, lp, "TEMPLATE"), root_1);
/*      */ 
/* 8225 */         this.adaptor.addChild(root_1, stream_ACTION.nextNode());
/* 8226 */         this.adaptor.addChild(root_1, stream_rewrite_template_args.nextTree());
/*      */ 
/* 8228 */         this.adaptor.addChild(root_0, root_1);
/*      */ 
/* 8233 */         retval.tree = root_0;
/*      */       }
/*      */ 
/* 8236 */       retval.stop = this.input.LT(-1);
/*      */ 
/* 8238 */       if (this.state.backtracking == 0)
/*      */       {
/* 8240 */         retval.tree = ((CommonTree)this.adaptor.rulePostProcessing(root_0));
/* 8241 */         this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
/*      */       }
/*      */     } catch (RecognitionException re) {
/* 8244 */       re = 
/* 8251 */         re;
/*      */ 
/* 8245 */       reportError(re);
/* 8246 */       recover(this.input, re);
/* 8247 */       retval.tree = ((CommonTree)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), re));
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/* 8252 */     return retval;
/*      */   }
/*      */ 
/*      */   public final rewrite_template_args_return rewrite_template_args()
/*      */     throws RecognitionException
/*      */   {
/* 8264 */     rewrite_template_args_return retval = new rewrite_template_args_return();
/* 8265 */     retval.start = this.input.LT(1);
/*      */ 
/* 8267 */     CommonTree root_0 = null;
/*      */ 
/* 8269 */     Token char_literal171 = null;
/* 8270 */     rewrite_template_arg_return rewrite_template_arg170 = null;
/*      */ 
/* 8272 */     rewrite_template_arg_return rewrite_template_arg172 = null;
/*      */ 
/* 8275 */     CommonTree char_literal171_tree = null;
/* 8276 */     RewriteRuleTokenStream stream_81 = new RewriteRuleTokenStream(this.adaptor, "token 81");
/* 8277 */     RewriteRuleSubtreeStream stream_rewrite_template_arg = new RewriteRuleSubtreeStream(this.adaptor, "rule rewrite_template_arg");
/*      */     try
/*      */     {
/* 8280 */       int alt74 = 2;
/*      */       Object nvae;
/* 8281 */       switch (this.input.LA(1))
/*      */       {
/*      */       case 42:
/*      */       case 49:
/* 8285 */         alt74 = 1;
/*      */ 
/* 8287 */         break;
/*      */       case 84:
/* 8290 */         alt74 = 2;
/*      */ 
/* 8292 */         break;
/*      */       default:
/* 8294 */         if (this.state.backtracking > 0) { this.state.failed = true; rewrite_template_args_return localrewrite_template_args_return1 = retval; return localrewrite_template_args_return1; }
/* 8295 */         nvae = new NoViableAltException("", 74, 0, this.input);
/*      */ 
/* 8298 */         throw ((Throwable)nvae);
/*      */       }
/*      */ 
/* 8301 */       switch (alt74)
/*      */       {
/*      */       case 1:
/* 8305 */         pushFollow(FOLLOW_rewrite_template_arg_in_rewrite_template_args3083);
/* 8306 */         rewrite_template_arg170 = rewrite_template_arg();
/*      */ 
/* 8308 */         this.state._fsp -= 1;
/* 8309 */         if (this.state.failed) { nvae = retval; return nvae; }
/* 8310 */         if (this.state.backtracking == 0) stream_rewrite_template_arg.add(rewrite_template_arg170.getTree());
/*      */ 
/*      */         while (true)
/*      */         {
/* 8314 */           int alt73 = 2;
/* 8315 */           switch (this.input.LA(1))
/*      */           {
/*      */           case 81:
/* 8318 */             alt73 = 1;
/*      */           }
/*      */ 
/* 8324 */           switch (alt73)
/*      */           {
/*      */           case 1:
/* 8328 */             char_literal171 = (Token)match(this.input, 81, FOLLOW_81_in_rewrite_template_args3086);
/*      */             rewrite_template_args_return localrewrite_template_args_return2;
/* 8328 */             if (this.state.failed) { localrewrite_template_args_return2 = retval; return localrewrite_template_args_return2; }
/* 8329 */             if (this.state.backtracking == 0) stream_81.add(char_literal171);
/*      */ 
/* 8331 */             pushFollow(FOLLOW_rewrite_template_arg_in_rewrite_template_args3088);
/* 8332 */             rewrite_template_arg172 = rewrite_template_arg();
/*      */ 
/* 8334 */             this.state._fsp -= 1;
/* 8335 */             if (this.state.failed) { localrewrite_template_args_return2 = retval; return localrewrite_template_args_return2; }
/* 8336 */             if (this.state.backtracking == 0) stream_rewrite_template_arg.add(rewrite_template_arg172.getTree()); break;
/*      */           default:
/* 8342 */             break label436;
/*      */           }
/*      */ 
/*      */         }
/*      */ 
/* 8355 */         if (this.state.backtracking == 0) {
/* 8356 */           retval.tree = root_0;
/* 8357 */           RewriteRuleSubtreeStream stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.tree : null);
/*      */ 
/* 8359 */           root_0 = (CommonTree)this.adaptor.nil();
/*      */ 
/* 8364 */           CommonTree root_1 = (CommonTree)this.adaptor.nil();
/* 8365 */           root_1 = (CommonTree)this.adaptor.becomeRoot((CommonTree)this.adaptor.create(22, "ARGLIST"), root_1);
/*      */ 
/* 8367 */           if (!stream_rewrite_template_arg.hasNext()) {
/* 8368 */             throw new RewriteEarlyExitException();
/*      */           }
/* 8370 */           while (stream_rewrite_template_arg.hasNext()) {
/* 8371 */             this.adaptor.addChild(root_1, stream_rewrite_template_arg.nextTree());
/*      */           }
/*      */ 
/* 8374 */           stream_rewrite_template_arg.reset();
/*      */ 
/* 8376 */           this.adaptor.addChild(root_0, root_1);
/*      */ 
/* 8381 */           retval.tree = root_0; } break;
/*      */       case 2:
/* 8395 */         label436: if (this.state.backtracking == 0) {
/* 8396 */           retval.tree = root_0;
/* 8397 */           RewriteRuleSubtreeStream stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.tree : null);
/*      */ 
/* 8399 */           root_0 = (CommonTree)this.adaptor.nil();
/*      */ 
/* 8402 */           this.adaptor.addChild(root_0, (CommonTree)this.adaptor.create(22, "ARGLIST"));
/*      */ 
/* 8406 */           retval.tree = root_0;
/*      */         }
/*      */         break;
/*      */       }
/*      */ 
/* 8411 */       retval.stop = this.input.LT(-1);
/*      */ 
/* 8413 */       if (this.state.backtracking == 0)
/*      */       {
/* 8415 */         retval.tree = ((CommonTree)this.adaptor.rulePostProcessing(root_0));
/* 8416 */         this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
/*      */       }
/*      */     } catch (RecognitionException re) {
/* 8419 */       re = 
/* 8426 */         re;
/*      */ 
/* 8420 */       reportError(re);
/* 8421 */       recover(this.input, re);
/* 8422 */       retval.tree = ((CommonTree)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), re));
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/* 8427 */     return retval;
/*      */   }
/*      */ 
/*      */   public final rewrite_template_arg_return rewrite_template_arg()
/*      */     throws RecognitionException
/*      */   {
/* 8439 */     rewrite_template_arg_return retval = new rewrite_template_arg_return();
/* 8440 */     retval.start = this.input.LT(1);
/*      */ 
/* 8442 */     CommonTree root_0 = null;
/*      */ 
/* 8444 */     Token char_literal174 = null;
/* 8445 */     Token ACTION175 = null;
/* 8446 */     id_return id173 = null;
/*      */ 
/* 8449 */     CommonTree char_literal174_tree = null;
/* 8450 */     CommonTree ACTION175_tree = null;
/* 8451 */     RewriteRuleTokenStream stream_71 = new RewriteRuleTokenStream(this.adaptor, "token 71");
/* 8452 */     RewriteRuleTokenStream stream_ACTION = new RewriteRuleTokenStream(this.adaptor, "token ACTION");
/* 8453 */     RewriteRuleSubtreeStream stream_id = new RewriteRuleSubtreeStream(this.adaptor, "rule id");
/*      */     try
/*      */     {
/* 8458 */       pushFollow(FOLLOW_id_in_rewrite_template_arg3121);
/* 8459 */       id173 = id();
/*      */ 
/* 8461 */       this.state._fsp -= 1;
/*      */       rewrite_template_arg_return localrewrite_template_arg_return1;
/* 8462 */       if (this.state.failed) { localrewrite_template_arg_return1 = retval; return localrewrite_template_arg_return1; }
/* 8463 */       if (this.state.backtracking == 0) stream_id.add(id173.getTree());
/* 8464 */       char_literal174 = (Token)match(this.input, 71, FOLLOW_71_in_rewrite_template_arg3123); if (this.state.failed) { localrewrite_template_arg_return1 = retval; return localrewrite_template_arg_return1; }
/* 8465 */       if (this.state.backtracking == 0) stream_71.add(char_literal174);
/*      */ 
/* 8467 */       ACTION175 = (Token)match(this.input, 45, FOLLOW_ACTION_in_rewrite_template_arg3125); if (this.state.failed) { localrewrite_template_arg_return1 = retval; return localrewrite_template_arg_return1; }
/* 8468 */       if (this.state.backtracking == 0) stream_ACTION.add(ACTION175);
/*      */ 
/* 8479 */       if (this.state.backtracking == 0) {
/* 8480 */         retval.tree = root_0;
/* 8481 */         RewriteRuleSubtreeStream stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.tree : null);
/*      */ 
/* 8483 */         root_0 = (CommonTree)this.adaptor.nil();
/*      */ 
/* 8488 */         CommonTree root_1 = (CommonTree)this.adaptor.nil();
/* 8489 */         root_1 = (CommonTree)this.adaptor.becomeRoot((CommonTree)this.adaptor.create(21, id173 != null ? id173.start : null), root_1);
/*      */ 
/* 8491 */         this.adaptor.addChild(root_1, stream_id.nextTree());
/* 8492 */         this.adaptor.addChild(root_1, stream_ACTION.nextNode());
/*      */ 
/* 8494 */         this.adaptor.addChild(root_0, root_1);
/*      */ 
/* 8499 */         retval.tree = root_0;
/*      */       }
/*      */ 
/* 8502 */       retval.stop = this.input.LT(-1);
/*      */ 
/* 8504 */       if (this.state.backtracking == 0)
/*      */       {
/* 8506 */         retval.tree = ((CommonTree)this.adaptor.rulePostProcessing(root_0));
/* 8507 */         this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
/*      */       }
/*      */     } catch (RecognitionException re) {
/* 8510 */       re = 
/* 8517 */         re;
/*      */ 
/* 8511 */       reportError(re);
/* 8512 */       recover(this.input, re);
/* 8513 */       retval.tree = ((CommonTree)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), re));
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/* 8518 */     return retval;
/*      */   }
/*      */ 
/*      */   public final id_return id()
/*      */     throws RecognitionException
/*      */   {
/* 8530 */     id_return retval = new id_return();
/* 8531 */     retval.start = this.input.LT(1);
/*      */ 
/* 8533 */     CommonTree root_0 = null;
/*      */ 
/* 8535 */     Token TOKEN_REF176 = null;
/* 8536 */     Token RULE_REF177 = null;
/*      */ 
/* 8538 */     CommonTree TOKEN_REF176_tree = null;
/* 8539 */     CommonTree RULE_REF177_tree = null;
/* 8540 */     RewriteRuleTokenStream stream_RULE_REF = new RewriteRuleTokenStream(this.adaptor, "token RULE_REF");
/* 8541 */     RewriteRuleTokenStream stream_TOKEN_REF = new RewriteRuleTokenStream(this.adaptor, "token TOKEN_REF");
/*      */     try
/*      */     {
/* 8545 */       int alt75 = 2;
/*      */       Object nvae;
/* 8546 */       switch (this.input.LA(1))
/*      */       {
/*      */       case 42:
/* 8549 */         alt75 = 1;
/*      */ 
/* 8551 */         break;
/*      */       case 49:
/* 8554 */         alt75 = 2;
/*      */ 
/* 8556 */         break;
/*      */       default:
/* 8558 */         if (this.state.backtracking > 0) { this.state.failed = true; id_return localid_return1 = retval; return localid_return1; }
/* 8559 */         nvae = new NoViableAltException("", 75, 0, this.input);
/*      */ 
/* 8562 */         throw ((Throwable)nvae);
/*      */       }
/*      */       Object stream_retval;
/* 8565 */       switch (alt75)
/*      */       {
/*      */       case 1:
/* 8569 */         TOKEN_REF176 = (Token)match(this.input, 42, FOLLOW_TOKEN_REF_in_id3146); if (this.state.failed) { nvae = retval; return nvae; }
/* 8570 */         if (this.state.backtracking == 0) stream_TOKEN_REF.add(TOKEN_REF176);
/*      */ 
/* 8581 */         if (this.state.backtracking == 0) {
/* 8582 */           retval.tree = root_0;
/* 8583 */           stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.tree : null);
/*      */ 
/* 8585 */           root_0 = (CommonTree)this.adaptor.nil();
/*      */ 
/* 8588 */           this.adaptor.addChild(root_0, (CommonTree)this.adaptor.create(20, TOKEN_REF176));
/*      */ 
/* 8592 */           retval.tree = root_0; } break;
/*      */       case 2:
/* 8598 */         RULE_REF177 = (Token)match(this.input, 49, FOLLOW_RULE_REF_in_id3156); if (this.state.failed) { stream_retval = retval; return stream_retval; }
/* 8599 */         if (this.state.backtracking == 0) stream_RULE_REF.add(RULE_REF177);
/*      */ 
/* 8610 */         if (this.state.backtracking == 0) {
/* 8611 */           retval.tree = root_0;
/* 8612 */           RewriteRuleSubtreeStream stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.tree : null);
/*      */ 
/* 8614 */           root_0 = (CommonTree)this.adaptor.nil();
/*      */ 
/* 8617 */           this.adaptor.addChild(root_0, (CommonTree)this.adaptor.create(20, RULE_REF177));
/*      */ 
/* 8621 */           retval.tree = root_0;
/*      */         }
/*      */         break;
/*      */       }
/*      */ 
/* 8626 */       retval.stop = this.input.LT(-1);
/*      */ 
/* 8628 */       if (this.state.backtracking == 0)
/*      */       {
/* 8630 */         retval.tree = ((CommonTree)this.adaptor.rulePostProcessing(root_0));
/* 8631 */         this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
/*      */       }
/*      */     } catch (RecognitionException re) {
/* 8634 */       re = 
/* 8641 */         re;
/*      */ 
/* 8635 */       reportError(re);
/* 8636 */       recover(this.input, re);
/* 8637 */       retval.tree = ((CommonTree)this.adaptor.errorNode(this.input, retval.start, this.input.LT(-1), re));
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/* 8642 */     return retval;
/*      */   }
/*      */ 
/*      */   public final void synpred1_ANTLRv3_fragment()
/*      */     throws RecognitionException
/*      */   {
/* 8651 */     pushFollow(FOLLOW_rewrite_template_in_synpred1_ANTLRv32570);
/* 8652 */     rewrite_template();
/*      */ 
/* 8654 */     this.state._fsp -= 1;
/* 8655 */     if (this.state.failed);
/*      */   }
/*      */ 
/*      */   public final void synpred2_ANTLRv3_fragment()
/*      */     throws RecognitionException
/*      */   {
/* 8666 */     pushFollow(FOLLOW_rewrite_tree_alternative_in_synpred2_ANTLRv32575);
/* 8667 */     rewrite_tree_alternative();
/*      */ 
/* 8669 */     this.state._fsp -= 1;
/* 8670 */     if (this.state.failed);
/*      */   }
/*      */ 
/*      */   public final boolean synpred2_ANTLRv3()
/*      */   {
/* 8679 */     this.state.backtracking += 1;
/* 8680 */     int start = this.input.mark();
/*      */     try {
/* 8682 */       synpred2_ANTLRv3_fragment();
/*      */     } catch (RecognitionException re) {
/* 8684 */       System.err.println("impossible: " + re);
/*      */     }
/* 8686 */     boolean success = !this.state.failed;
/* 8687 */     this.input.rewind(start);
/* 8688 */     this.state.backtracking -= 1;
/* 8689 */     this.state.failed = false;
/* 8690 */     return success;
/*      */   }
/*      */   public final boolean synpred1_ANTLRv3() {
/* 8693 */     this.state.backtracking += 1;
/* 8694 */     int start = this.input.mark();
/*      */     try {
/* 8696 */       synpred1_ANTLRv3_fragment();
/*      */     } catch (RecognitionException re) {
/* 8698 */       System.err.println("impossible: " + re);
/*      */     }
/* 8700 */     boolean success = !this.state.failed;
/* 8701 */     this.input.rewind(start);
/* 8702 */     this.state.backtracking -= 1;
/* 8703 */     this.state.failed = false;
/* 8704 */     return success;
/*      */   }
/*      */ 
/*      */   static
/*      */   {
/* 8751 */     int numStates = DFA46_transitionS.length;
/* 8752 */     DFA46_transition = new short[numStates][];
/* 8753 */     for (int i = 0; i < numStates; i++) {
/* 8754 */       DFA46_transition[i] = DFA.unpackEncodedString(DFA46_transitionS[i]);
/*      */     }
/*      */ 
/* 8787 */     DFA64_transitionS = new String[] { "\001\005\002ð¿¿\001\006\001ð¿¿\001\001\002\005\001\004\003ð¿¿\001\002\023ð¿¿\001\006\fð¿¿\001\003\002\006\bð¿¿\001\005", "\001\005\002ð¿¿\001\005\001ð¿¿\004\005\002ð¿¿\002\005\023ð¿¿\001\005\004ð¿¿\001\005\007ð¿¿\001\007\002\005\005ð¿¿\002\005\001ð¿¿\001\005", "\001\005\002ð¿¿\001\005\001ð¿¿\004\005\003ð¿¿\001\005\023ð¿¿\001\005\004ð¿¿\001\005\007ð¿¿\001\007\002\005\005ð¿¿\002\005\001ð¿¿\001\005", "\001\005\004ð¿¿\003\005\001\b\003ð¿¿\001\005 ð¿¿\001\005\nð¿¿\001\005", "\001ð¿¿", "", "", "\001\005\004ð¿¿\001\n\003\005\003ð¿¿\001\013 ð¿¿\001\005\001ð¿¿\001\t\bð¿¿\001\005", "\001\005\004ð¿¿\004\005\003ð¿¿\001\005\030ð¿¿\001\005\007ð¿¿\001\005\001ð¿¿\001\f\005ð¿¿\002\005\001ð¿¿\001\005", "", "\001\005\004ð¿¿\004\005\002ð¿¿\002\005\025ð¿¿\001\t\002ð¿¿\001\005\007ð¿¿\001\005\001ð¿¿\001\005\005ð¿¿\002\005\001ð¿¿\001\005", "\001\005\004ð¿¿\004\005\003ð¿¿\001\005\025ð¿¿\001\t\002ð¿¿\001\005\007ð¿¿\001\005\001ð¿¿\001\005\005ð¿¿\002\005\001ð¿¿\001\005", "\001\005\007ð¿¿\001\t\007ð¿¿\002\005" };
/*      */ 
/* 8810 */     DFA64_eot = DFA.unpackEncodedString("\rð¿¿");
/* 8811 */     DFA64_eof = DFA.unpackEncodedString("\rð¿¿");
/* 8812 */     DFA64_min = DFA.unpackEncodedStringToUnsignedChars("");
/* 8813 */     DFA64_max = DFA.unpackEncodedStringToUnsignedChars("");
/* 8814 */     DFA64_accept = DFA.unpackEncodedString("\005ð¿¿\001\002\001\003\002ð¿¿\001\001\003ð¿¿");
/* 8815 */     DFA64_special = DFA.unpackEncodedString("");
/*      */ 
/* 8819 */     int numStates = DFA64_transitionS.length;
/* 8820 */     DFA64_transition = new short[numStates][];
/* 8821 */     for (int i = 0; i < numStates; i++) {
/* 8822 */       DFA64_transition[i] = DFA.unpackEncodedString(DFA64_transitionS[i]);
/*      */     }
/*      */ 
/* 8881 */     DFA67_transitionS = new String[] { "\001\007\004ð¿¿\001\002\001\004\001\001\001\006\003ð¿¿\001\003 ð¿¿\001\b\nð¿¿\001\005", "\001\t\002ð¿¿\001\t\001ð¿¿\004\t\003ð¿¿\001\t\023ð¿¿\001\t\004ð¿¿\001\n\007ð¿¿\003\t\005ð¿¿\002\n\001ð¿¿\001\t", "\001\t\002ð¿¿\001\t\001ð¿¿\004\t\002ð¿¿\001\013\001\t\023ð¿¿\001\t\004ð¿¿\001\n\007ð¿¿\003\t\005ð¿¿\002\n\001ð¿¿\001\t", "\001\t\002ð¿¿\001\t\001ð¿¿\004\t\003ð¿¿\001\t\023ð¿¿\001\t\004ð¿¿\001\n\007ð¿¿\003\t\005ð¿¿\002\n\001ð¿¿\001\t", "\001\t\002ð¿¿\001\t\001ð¿¿\004\t\003ð¿¿\001\t\023ð¿¿\001\t\004ð¿¿\001\n\007ð¿¿\003\t\005ð¿¿\002\n\001ð¿¿\001\t", "\001\f\006ð¿¿\001\r", "\001\t\002ð¿¿\001\t\001ð¿¿\004\t\003ð¿¿\001\t\023ð¿¿\001\t\004ð¿¿\001\n\007ð¿¿\003\t\005ð¿¿\002\n\001ð¿¿\001\t", "", "", "", "", "\001\t\002ð¿¿\001\t\001ð¿¿\004\t\003ð¿¿\001\t\023ð¿¿\001\t\004ð¿¿\001\n\007ð¿¿\003\t\005ð¿¿\002\n\001ð¿¿\001\t", "\001\t\002ð¿¿\001\t\001ð¿¿\004\t\003ð¿¿\001\t\023ð¿¿\001\t\004ð¿¿\001\n\007ð¿¿\003\t\005ð¿¿\002\n\001ð¿¿\001\t", "\001\t\002ð¿¿\001\t\001ð¿¿\004\t\003ð¿¿\001\t\023ð¿¿\001\t\004ð¿¿\001\n\007ð¿¿\003\t\005ð¿¿\002\n\001ð¿¿\001\t" };
/*      */ 
/* 8907 */     DFA67_eot = DFA.unpackEncodedString("\016ð¿¿");
/* 8908 */     DFA67_eof = DFA.unpackEncodedString("\001ð¿¿\004\t\001ð¿¿\001\t\004ð¿¿\003\t");
/* 8909 */     DFA67_min = DFA.unpackEncodedStringToUnsignedChars("\005%\001*\001%\004ð¿¿\003%");
/* 8910 */     DFA67_max = DFA.unpackEncodedStringToUnsignedChars("\005]\0011\001]\004ð¿¿\003]");
/* 8911 */     DFA67_accept = DFA.unpackEncodedString("\007ð¿¿\001\003\001\004\001\001\001\002\003ð¿¿");
/* 8912 */     DFA67_special = DFA.unpackEncodedString("\016ð¿¿}>");
/*      */ 
/* 8916 */     int numStates = DFA67_transitionS.length;
/* 8917 */     DFA67_transition = new short[numStates][];
/* 8918 */     for (int i = 0; i < numStates; i++) {
/* 8919 */       DFA67_transition[i] = DFA.unpackEncodedString(DFA67_transitionS[i]);
/*      */     }
/*      */ 
/* 8954 */     DFA72_transitionS = new String[] { "\001\001\002ð¿¿\001\004\003ð¿¿\001\002 ð¿¿\001\003", "\001\005", "\001\005", "", "", "\001\006\006ð¿¿\001\007\"ð¿¿\001\b", "\001\t", "\001\t", "\001\013\tð¿¿\002\n\021ð¿¿\001\013\rð¿¿\002\013", "\001\f", "", "", "\001\r\002ð¿¿\001\b", "\001\016\006ð¿¿\001\017", "\001\020", "\001\020", "\001\021", "\001\r\002ð¿¿\001\b" };
/*      */ 
/* 8975 */     DFA72_eot = DFA.unpackEncodedString("\022ð¿¿");
/* 8976 */     DFA72_eof = DFA.unpackEncodedString("\bð¿¿\001\013\tð¿¿");
/* 8977 */     DFA72_min = DFA.unpackEncodedStringToUnsignedChars("\001*\002R\002ð¿¿\001*\002G\001(\001-\002ð¿¿\001Q\001*\002G\001-\001Q");
/* 8978 */     DFA72_max = DFA.unpackEncodedStringToUnsignedChars("\003R\002ð¿¿\001T\002G\001T\001-\002ð¿¿\001T\0011\002G\001-\001T");
/* 8979 */     DFA72_accept = DFA.unpackEncodedString("\003ð¿¿\001\003\001\004\005ð¿¿\001\001\001\002\006ð¿¿");
/* 8980 */     DFA72_special = DFA.unpackEncodedString("\022ð¿¿}>");
/*      */ 
/* 8984 */     int numStates = DFA72_transitionS.length;
/* 8985 */     DFA72_transition = new short[numStates][];
/* 8986 */     for (int i = 0; i < numStates; i++)
/* 8987 */       DFA72_transition[i] = DFA.unpackEncodedString(DFA72_transitionS[i]);
/*      */   }
/*      */ 
/*      */   class DFA72 extends DFA
/*      */   {
/*      */     public DFA72(BaseRecognizer recognizer)
/*      */     {
/* 8994 */       this.recognizer = recognizer;
/* 8995 */       this.decisionNumber = 72;
/* 8996 */       this.eot = ANTLRv3Parser.DFA72_eot;
/* 8997 */       this.eof = ANTLRv3Parser.DFA72_eof;
/* 8998 */       this.min = ANTLRv3Parser.DFA72_min;
/* 8999 */       this.max = ANTLRv3Parser.DFA72_max;
/* 9000 */       this.accept = ANTLRv3Parser.DFA72_accept;
/* 9001 */       this.special = ANTLRv3Parser.DFA72_special;
/* 9002 */       this.transition = ANTLRv3Parser.DFA72_transition;
/*      */     }
/*      */     public String getDescription() {
/* 9005 */       return "409:1: rewrite_template : ( id lp= '(' rewrite_template_args ')' (str= DOUBLE_QUOTE_STRING_LITERAL | str= DOUBLE_ANGLE_STRING_LITERAL ) -> ^( TEMPLATE[$lp,\"TEMPLATE\"] id rewrite_template_args $str) | rewrite_template_ref | rewrite_indirect_template_head | ACTION );";
/*      */     }
/*      */   }
/*      */ 
/*      */   class DFA67 extends DFA
/*      */   {
/*      */     public DFA67(BaseRecognizer recognizer)
/*      */     {
/* 8926 */       this.recognizer = recognizer;
/* 8927 */       this.decisionNumber = 67;
/* 8928 */       this.eot = ANTLRv3Parser.DFA67_eot;
/* 8929 */       this.eof = ANTLRv3Parser.DFA67_eof;
/* 8930 */       this.min = ANTLRv3Parser.DFA67_min;
/* 8931 */       this.max = ANTLRv3Parser.DFA67_max;
/* 8932 */       this.accept = ANTLRv3Parser.DFA67_accept;
/* 8933 */       this.special = ANTLRv3Parser.DFA67_special;
/* 8934 */       this.transition = ANTLRv3Parser.DFA67_transition;
/*      */     }
/*      */     public String getDescription() {
/* 8937 */       return "372:1: rewrite_tree_element : ( rewrite_tree_atom | rewrite_tree_atom ebnfSuffix -> ^( ebnfSuffix ^( BLOCK[\"BLOCK\"] ^( ALT[\"ALT\"] rewrite_tree_atom EOA[\"EOA\"] ) EOB[\"EOB\"] ) ) | rewrite_tree ( ebnfSuffix -> ^( ebnfSuffix ^( BLOCK[\"BLOCK\"] ^( ALT[\"ALT\"] rewrite_tree EOA[\"EOA\"] ) EOB[\"EOB\"] ) ) | -> rewrite_tree ) | rewrite_tree_ebnf );";
/*      */     }
/*      */   }
/*      */ 
/*      */   class DFA64 extends DFA
/*      */   {
/*      */     public DFA64(BaseRecognizer recognizer)
/*      */     {
/* 8829 */       this.recognizer = recognizer;
/* 8830 */       this.decisionNumber = 64;
/* 8831 */       this.eot = ANTLRv3Parser.DFA64_eot;
/* 8832 */       this.eof = ANTLRv3Parser.DFA64_eof;
/* 8833 */       this.min = ANTLRv3Parser.DFA64_min;
/* 8834 */       this.max = ANTLRv3Parser.DFA64_max;
/* 8835 */       this.accept = ANTLRv3Parser.DFA64_accept;
/* 8836 */       this.special = ANTLRv3Parser.DFA64_special;
/* 8837 */       this.transition = ANTLRv3Parser.DFA64_transition;
/*      */     }
/*      */     public String getDescription() {
/* 8840 */       return "356:1: rewrite_alternative options {backtrack=true; } : ( rewrite_template | rewrite_tree_alternative | -> ^( ALT[\"ALT\"] EPSILON[\"EPSILON\"] EOA[\"EOA\"] ) );";
/*      */     }
/*      */     public int specialStateTransition(int s, IntStream _input) throws NoViableAltException {
/* 8843 */       TokenStream input = (TokenStream)_input;
/* 8844 */       int _s = s;
/* 8845 */       switch (s) {
/*      */       case 0:
/* 8847 */         int LA64_4 = input.LA(1);
/*      */ 
/* 8850 */         int index64_4 = input.index();
/* 8851 */         input.rewind();
/* 8852 */         s = -1;
/* 8853 */         if (ANTLRv3Parser.this.synpred1_ANTLRv3()) s = 9;
/* 8855 */         else if (ANTLRv3Parser.this.synpred2_ANTLRv3()) s = 5;
/*      */ 
/* 8858 */         input.seek(index64_4);
/* 8859 */         if (s >= 0) return s;
/*      */         break;
/*      */       }
/* 8862 */       if (ANTLRv3Parser.this.state.backtracking > 0) { ANTLRv3Parser.this.state.failed = true; return -1; }
/* 8863 */       NoViableAltException nvae = new NoViableAltException(getDescription(), 64, _s, input);
/*      */ 
/* 8865 */       error(nvae);
/* 8866 */       throw nvae;
/*      */     }
/*      */   }
/*      */ 
/*      */   class DFA46 extends DFA
/*      */   {
/*      */     public DFA46(BaseRecognizer recognizer)
/*      */     {
/* 8761 */       this.recognizer = recognizer;
/* 8762 */       this.decisionNumber = 46;
/* 8763 */       this.eot = ANTLRv3Parser.DFA46_eot;
/* 8764 */       this.eof = ANTLRv3Parser.DFA46_eof;
/* 8765 */       this.min = ANTLRv3Parser.DFA46_min;
/* 8766 */       this.max = ANTLRv3Parser.DFA46_max;
/* 8767 */       this.accept = ANTLRv3Parser.DFA46_accept;
/* 8768 */       this.special = ANTLRv3Parser.DFA46_special;
/* 8769 */       this.transition = ANTLRv3Parser.DFA46_transition;
/*      */     }
/*      */     public String getDescription() {
/* 8772 */       return "241:1: elementNoOptionSpec : ( id (labelOp= '=' | labelOp= '+=' ) atom ( ebnfSuffix -> ^( ebnfSuffix ^( BLOCK[\"BLOCK\"] ^( ALT[\"ALT\"] ^( $labelOp id atom ) EOA[\"EOA\"] ) EOB[\"EOB\"] ) ) | -> ^( $labelOp id atom ) ) | id (labelOp= '=' | labelOp= '+=' ) block ( ebnfSuffix -> ^( ebnfSuffix ^( BLOCK[\"BLOCK\"] ^( ALT[\"ALT\"] ^( $labelOp id block ) EOA[\"EOA\"] ) EOB[\"EOB\"] ) ) | -> ^( $labelOp id block ) ) | atom ( ebnfSuffix -> ^( ebnfSuffix ^( BLOCK[\"BLOCK\"] ^( ALT[\"ALT\"] atom EOA[\"EOA\"] ) EOB[\"EOB\"] ) ) | -> atom ) | ebnf | ACTION | SEMPRED ( '=>' -> GATED_SEMPRED | -> SEMPRED ) | treeSpec ( ebnfSuffix -> ^( ebnfSuffix ^( BLOCK[\"BLOCK\"] ^( ALT[\"ALT\"] treeSpec EOA[\"EOA\"] ) EOB[\"EOB\"] ) ) | -> treeSpec ) );";
/*      */     }
/*      */   }
/*      */ 
/*      */   public static class id_return extends ParserRuleReturnScope
/*      */   {
/*      */     CommonTree tree;
/*      */ 
/*      */     public Object getTree()
/*      */     {
/* 8524 */       return this.tree;
/*      */     }
/*      */   }
/*      */ 
/*      */   public static class rewrite_template_arg_return extends ParserRuleReturnScope
/*      */   {
/*      */     CommonTree tree;
/*      */ 
/*      */     public Object getTree()
/*      */     {
/* 8433 */       return this.tree;
/*      */     }
/*      */   }
/*      */ 
/*      */   public static class rewrite_template_args_return extends ParserRuleReturnScope
/*      */   {
/*      */     CommonTree tree;
/*      */ 
/*      */     public Object getTree()
/*      */     {
/* 8258 */       return this.tree;
/*      */     }
/*      */   }
/*      */ 
/*      */   public static class rewrite_indirect_template_head_return extends ParserRuleReturnScope
/*      */   {
/*      */     CommonTree tree;
/*      */ 
/*      */     public Object getTree()
/*      */     {
/* 8151 */       return this.tree;
/*      */     }
/*      */   }
/*      */ 
/*      */   public static class rewrite_template_ref_return extends ParserRuleReturnScope
/*      */   {
/*      */     CommonTree tree;
/*      */ 
/*      */     public Object getTree()
/*      */     {
/* 8051 */       return this.tree;
/*      */     }
/*      */   }
/*      */ 
/*      */   public static class rewrite_template_return extends ParserRuleReturnScope
/*      */   {
/*      */     CommonTree tree;
/*      */ 
/*      */     public Object getTree()
/*      */     {
/* 7849 */       return this.tree;
/*      */     }
/*      */   }
/*      */ 
/*      */   public static class rewrite_tree_return extends ParserRuleReturnScope
/*      */   {
/*      */     CommonTree tree;
/*      */ 
/*      */     public Object getTree()
/*      */     {
/* 7711 */       return this.tree;
/*      */     }
/*      */   }
/*      */ 
/*      */   public static class rewrite_tree_ebnf_return extends ParserRuleReturnScope
/*      */   {
/*      */     CommonTree tree;
/*      */ 
/*      */     public Object getTree()
/*      */     {
/* 7615 */       return this.tree;
/*      */     }
/*      */   }
/*      */ 
/*      */   public static class rewrite_tree_atom_return extends ParserRuleReturnScope
/*      */   {
/*      */     CommonTree tree;
/*      */ 
/*      */     public Object getTree()
/*      */     {
/* 7364 */       return this.tree;
/*      */     }
/*      */   }
/*      */ 
/*      */   public static class rewrite_tree_element_return extends ParserRuleReturnScope
/*      */   {
/*      */     CommonTree tree;
/*      */ 
/*      */     public Object getTree()
/*      */     {
/* 7082 */       return this.tree;
/*      */     }
/*      */   }
/*      */ 
/*      */   public static class rewrite_tree_alternative_return extends ParserRuleReturnScope
/*      */   {
/*      */     CommonTree tree;
/*      */ 
/*      */     public Object getTree()
/*      */     {
/* 6957 */       return this.tree;
/*      */     }
/*      */   }
/*      */ 
/*      */   public static class rewrite_tree_block_return extends ParserRuleReturnScope
/*      */   {
/*      */     CommonTree tree;
/*      */ 
/*      */     public Object getTree()
/*      */     {
/* 6866 */       return this.tree;
/*      */     }
/*      */   }
/*      */ 
/*      */   public static class rewrite_alternative_return extends ParserRuleReturnScope
/*      */   {
/*      */     CommonTree tree;
/*      */ 
/*      */     public Object getTree()
/*      */     {
/* 6758 */       return this.tree;
/*      */     }
/*      */   }
/*      */ 
/*      */   public static class rewrite_return extends ParserRuleReturnScope
/*      */   {
/*      */     CommonTree tree;
/*      */ 
/*      */     public Object getTree()
/*      */     {
/* 6554 */       return this.tree;
/*      */     }
/*      */   }
/*      */ 
/*      */   public static class ebnfSuffix_return extends ParserRuleReturnScope
/*      */   {
/*      */     CommonTree tree;
/*      */ 
/*      */     public Object getTree()
/*      */     {
/* 6390 */       return this.tree;
/*      */     }
/*      */   }
/*      */ 
/*      */   public static class notTerminal_return extends ParserRuleReturnScope
/*      */   {
/*      */     CommonTree tree;
/*      */ 
/*      */     public Object getTree()
/*      */     {
/* 6332 */       return this.tree;
/*      */     }
/*      */   }
/*      */ 
/*      */   public static class terminal_return extends ParserRuleReturnScope
/*      */   {
/*      */     CommonTree tree;
/*      */ 
/*      */     public Object getTree()
/*      */     {
/* 5940 */       return this.tree;
/*      */     }
/*      */   }
/*      */ 
/*      */   public static class range_return extends ParserRuleReturnScope
/*      */   {
/*      */     CommonTree tree;
/*      */ 
/*      */     public Object getTree()
/*      */     {
/* 5850 */       return this.tree;
/*      */     }
/*      */   }
/*      */ 
/*      */   public static class ebnf_return extends ParserRuleReturnScope
/*      */   {
/*      */     CommonTree tree;
/*      */ 
/*      */     public Object getTree()
/*      */     {
/* 5551 */       return this.tree;
/*      */     }
/*      */   }
/*      */ 
/*      */   public static class treeSpec_return extends ParserRuleReturnScope
/*      */   {
/*      */     CommonTree tree;
/*      */ 
/*      */     public Object getTree()
/*      */     {
/* 5405 */       return this.tree;
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
/* 4634 */       return this.tree;
/*      */     }
/*      */   }
/*      */ 
/*      */   public static class elementNoOptionSpec_return extends ParserRuleReturnScope
/*      */   {
/*      */     CommonTree tree;
/*      */ 
/*      */     public Object getTree()
/*      */     {
/* 3748 */       return this.tree;
/*      */     }
/*      */   }
/*      */ 
/*      */   public static class element_return extends ParserRuleReturnScope
/*      */   {
/*      */     CommonTree tree;
/*      */ 
/*      */     public Object getTree()
/*      */     {
/* 3696 */       return this.tree;
/*      */     }
/*      */   }
/*      */ 
/*      */   public static class finallyClause_return extends ParserRuleReturnScope
/*      */   {
/*      */     CommonTree tree;
/*      */ 
/*      */     public Object getTree()
/*      */     {
/* 3614 */       return this.tree;
/*      */     }
/*      */   }
/*      */ 
/*      */   public static class exceptionHandler_return extends ParserRuleReturnScope
/*      */   {
/*      */     CommonTree tree;
/*      */ 
/*      */     public Object getTree()
/*      */     {
/* 3525 */       return this.tree;
/*      */     }
/*      */   }
/*      */ 
/*      */   public static class exceptionGroup_return extends ParserRuleReturnScope
/*      */   {
/*      */     CommonTree tree;
/*      */ 
/*      */     public Object getTree()
/*      */     {
/* 3373 */       return this.tree;
/*      */     }
/*      */   }
/*      */ 
/*      */   public static class alternative_return extends ParserRuleReturnScope
/*      */   {
/*      */     CommonTree tree;
/*      */ 
/*      */     public Object getTree()
/*      */     {
/* 3172 */       return this.tree;
/*      */     }
/*      */   }
/*      */ 
/*      */   public static class altList_return extends ParserRuleReturnScope
/*      */   {
/*      */     CommonTree tree;
/*      */ 
/*      */     public Object getTree()
/*      */     {
/* 3017 */       return this.tree;
/*      */     }
/*      */   }
/*      */ 
/*      */   public static class block_return extends ParserRuleReturnScope
/*      */   {
/*      */     CommonTree tree;
/*      */ 
/*      */     public Object getTree()
/*      */     {
/* 2794 */       return this.tree;
/*      */     }
/*      */   }
/*      */ 
/*      */   public static class ruleScopeSpec_return extends ParserRuleReturnScope
/*      */   {
/*      */     CommonTree tree;
/*      */ 
/*      */     public Object getTree()
/*      */     {
/* 2446 */       return this.tree;
/*      */     }
/*      */   }
/*      */ 
/*      */   public static class throwsSpec_return extends ParserRuleReturnScope
/*      */   {
/*      */     CommonTree tree;
/*      */ 
/*      */     public Object getTree()
/*      */     {
/* 2315 */       return this.tree;
/*      */     }
/*      */   }
/*      */ 
/*      */   public static class ruleAction_return extends ParserRuleReturnScope
/*      */   {
/*      */     CommonTree tree;
/*      */ 
/*      */     public Object getTree()
/*      */     {
/* 2224 */       return this.tree;
/*      */     }
/*      */   }
/*      */ 
/*      */   public static class rule_return extends ParserRuleReturnScope
/*      */   {
/*      */     CommonTree tree;
/*      */ 
/*      */     public Object getTree()
/*      */     {
/* 1689 */       return this.tree;
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
/* 1515 */       return this.tree;
/*      */     }
/*      */   }
/*      */ 
/*      */   public static class option_return extends ParserRuleReturnScope
/*      */   {
/*      */     CommonTree tree;
/*      */ 
/*      */     public Object getTree()
/*      */     {
/* 1421 */       return this.tree;
/*      */     }
/*      */   }
/*      */ 
/*      */   public static class optionsSpec_return extends ParserRuleReturnScope
/*      */   {
/*      */     CommonTree tree;
/*      */ 
/*      */     public Object getTree()
/*      */     {
/* 1285 */       return this.tree;
/*      */     }
/*      */   }
/*      */ 
/*      */   public static class actionScopeName_return extends ParserRuleReturnScope
/*      */   {
/*      */     CommonTree tree;
/*      */ 
/*      */     public Object getTree()
/*      */     {
/* 1139 */       return this.tree;
/*      */     }
/*      */   }
/*      */ 
/*      */   public static class action_return extends ParserRuleReturnScope
/*      */   {
/*      */     CommonTree tree;
/*      */ 
/*      */     public Object getTree()
/*      */     {
/*  982 */       return this.tree;
/*      */     }
/*      */   }
/*      */ 
/*      */   public static class attrScope_return extends ParserRuleReturnScope
/*      */   {
/*      */     CommonTree tree;
/*      */ 
/*      */     public Object getTree()
/*      */     {
/*  891 */       return this.tree;
/*      */     }
/*      */   }
/*      */ 
/*      */   public static class tokenSpec_return extends ParserRuleReturnScope
/*      */   {
/*      */     CommonTree tree;
/*      */ 
/*      */     public Object getTree()
/*      */     {
/*  698 */       return this.tree;
/*      */     }
/*      */   }
/*      */ 
/*      */   public static class tokensSpec_return extends ParserRuleReturnScope
/*      */   {
/*      */     CommonTree tree;
/*      */ 
/*      */     public Object getTree()
/*      */     {
/*  569 */       return this.tree;
/*      */     }
/*      */   }
/*      */ 
/*      */   public static class grammarDef_return extends ParserRuleReturnScope
/*      */   {
/*      */     CommonTree tree;
/*      */ 
/*      */     public Object getTree()
/*      */     {
/*  145 */       return this.tree;
/*      */     }
/*      */   }
/*      */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.antlr.gunit.swingui.parsers.ANTLRv3Parser
 * JD-Core Version:    0.6.2
 */