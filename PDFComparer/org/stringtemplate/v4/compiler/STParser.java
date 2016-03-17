/*      */ package org.stringtemplate.v4.compiler;
/*      */ 
/*      */ import java.util.ArrayList;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.Stack;
/*      */ import org.antlr.runtime.BitSet;
/*      */ import org.antlr.runtime.CommonToken;
/*      */ import org.antlr.runtime.EarlyExitException;
/*      */ import org.antlr.runtime.FailedPredicateException;
/*      */ import org.antlr.runtime.IntStream;
/*      */ import org.antlr.runtime.MismatchedTokenException;
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
/*      */ import org.stringtemplate.v4.misc.ErrorManager;
/*      */ import org.stringtemplate.v4.misc.ErrorType;
/*      */ 
/*      */ public class STParser extends Parser
/*      */ {
/*   19 */   public static final String[] tokenNames = { "<invalid>", "<EOR>", "<DOWN>", "<UP>", "IF", "ELSE", "ELSEIF", "ENDIF", "SUPER", "SEMI", "BANG", "ELLIPSIS", "EQUALS", "COLON", "LPAREN", "RPAREN", "LBRACK", "RBRACK", "COMMA", "DOT", "LCURLY", "RCURLY", "TEXT", "LDELIM", "RDELIM", "ID", "STRING", "WS", "PIPE", "OR", "AND", "INDENT", "NEWLINE", "AT", "END", "TRUE", "FALSE", "COMMENT", "ARGS", "ELEMENTS", "EXEC_FUNC", "EXPR", "INCLUDE", "INCLUDE_IND", "INCLUDE_REGION", "INCLUDE_SUPER", "INCLUDE_SUPER_REGION", "INDENTED_EXPR", "LIST", "MAP", "NULL", "OPTIONS", "PROP", "PROP_IND", "REGION", "SUBTEMPLATE", "TO_STR", "ZIP" };
/*      */   public static final int EOF = -1;
/*      */   public static final int RBRACK = 17;
/*      */   public static final int LBRACK = 16;
/*      */   public static final int ELSE = 5;
/*      */   public static final int ELLIPSIS = 11;
/*      */   public static final int LCURLY = 20;
/*      */   public static final int BANG = 10;
/*      */   public static final int EQUALS = 12;
/*      */   public static final int TEXT = 22;
/*      */   public static final int ID = 25;
/*      */   public static final int SEMI = 9;
/*      */   public static final int LPAREN = 14;
/*      */   public static final int IF = 4;
/*      */   public static final int ELSEIF = 6;
/*      */   public static final int COLON = 13;
/*      */   public static final int RPAREN = 15;
/*      */   public static final int WS = 27;
/*      */   public static final int COMMA = 18;
/*      */   public static final int RCURLY = 21;
/*      */   public static final int ENDIF = 7;
/*      */   public static final int RDELIM = 24;
/*      */   public static final int SUPER = 8;
/*      */   public static final int DOT = 19;
/*      */   public static final int LDELIM = 23;
/*      */   public static final int STRING = 26;
/*      */   public static final int PIPE = 28;
/*      */   public static final int OR = 29;
/*      */   public static final int AND = 30;
/*      */   public static final int INDENT = 31;
/*      */   public static final int NEWLINE = 32;
/*      */   public static final int AT = 33;
/*      */   public static final int END = 34;
/*      */   public static final int TRUE = 35;
/*      */   public static final int FALSE = 36;
/*      */   public static final int COMMENT = 37;
/*      */   public static final int ARGS = 38;
/*      */   public static final int ELEMENTS = 39;
/*      */   public static final int EXEC_FUNC = 40;
/*      */   public static final int EXPR = 41;
/*      */   public static final int INCLUDE = 42;
/*      */   public static final int INCLUDE_IND = 43;
/*      */   public static final int INCLUDE_REGION = 44;
/*      */   public static final int INCLUDE_SUPER = 45;
/*      */   public static final int INCLUDE_SUPER_REGION = 46;
/*      */   public static final int INDENTED_EXPR = 47;
/*      */   public static final int LIST = 48;
/*      */   public static final int MAP = 49;
/*      */   public static final int NULL = 50;
/*      */   public static final int OPTIONS = 51;
/*      */   public static final int PROP = 52;
/*      */   public static final int PROP_IND = 53;
/*      */   public static final int REGION = 54;
/*      */   public static final int SUBTEMPLATE = 55;
/*      */   public static final int TO_STR = 56;
/*      */   public static final int ZIP = 57;
/*   94 */   protected TreeAdaptor adaptor = new CommonTreeAdaptor();
/*      */   ErrorManager errMgr;
/*      */   Token templateToken;
/* 2176 */   protected Stack conditional_stack = new Stack();
/*      */ 
/* 5783 */   public static final BitSet FOLLOW_template_in_templateAndEOF139 = new BitSet(new long[] { 0L });
/* 5784 */   public static final BitSet FOLLOW_EOF_in_templateAndEOF141 = new BitSet(new long[] { 2L });
/* 5785 */   public static final BitSet FOLLOW_element_in_template155 = new BitSet(new long[] { 143893987330L });
/* 5786 */   public static final BitSet FOLLOW_INDENT_in_element168 = new BitSet(new long[] { 137438953472L });
/* 5787 */   public static final BitSet FOLLOW_COMMENT_in_element171 = new BitSet(new long[] { 4294967296L });
/* 5788 */   public static final BitSet FOLLOW_NEWLINE_in_element173 = new BitSet(new long[] { 2L });
/* 5789 */   public static final BitSet FOLLOW_INDENT_in_element181 = new BitSet(new long[] { 141746503680L });
/* 5790 */   public static final BitSet FOLLOW_singleElement_in_element183 = new BitSet(new long[] { 2L });
/* 5791 */   public static final BitSet FOLLOW_singleElement_in_element200 = new BitSet(new long[] { 2L });
/* 5792 */   public static final BitSet FOLLOW_compoundElement_in_element205 = new BitSet(new long[] { 2L });
/* 5793 */   public static final BitSet FOLLOW_exprTag_in_singleElement216 = new BitSet(new long[] { 2L });
/* 5794 */   public static final BitSet FOLLOW_TEXT_in_singleElement221 = new BitSet(new long[] { 2L });
/* 5795 */   public static final BitSet FOLLOW_NEWLINE_in_singleElement226 = new BitSet(new long[] { 2L });
/* 5796 */   public static final BitSet FOLLOW_COMMENT_in_singleElement231 = new BitSet(new long[] { 2L });
/* 5797 */   public static final BitSet FOLLOW_ifstat_in_compoundElement244 = new BitSet(new long[] { 2L });
/* 5798 */   public static final BitSet FOLLOW_region_in_compoundElement249 = new BitSet(new long[] { 2L });
/* 5799 */   public static final BitSet FOLLOW_LDELIM_in_exprTag260 = new BitSet(new long[] { 111770943744L });
/* 5800 */   public static final BitSet FOLLOW_expr_in_exprTag262 = new BitSet(new long[] { 16777728L });
/* 5801 */   public static final BitSet FOLLOW_SEMI_in_exprTag266 = new BitSet(new long[] { 33554432L });
/* 5802 */   public static final BitSet FOLLOW_exprOptions_in_exprTag268 = new BitSet(new long[] { 16777216L });
/* 5803 */   public static final BitSet FOLLOW_RDELIM_in_exprTag273 = new BitSet(new long[] { 2L });
/* 5804 */   public static final BitSet FOLLOW_INDENT_in_region305 = new BitSet(new long[] { 8388608L });
/* 5805 */   public static final BitSet FOLLOW_LDELIM_in_region310 = new BitSet(new long[] { 8589934592L });
/* 5806 */   public static final BitSet FOLLOW_AT_in_region312 = new BitSet(new long[] { 33554432L });
/* 5807 */   public static final BitSet FOLLOW_ID_in_region314 = new BitSet(new long[] { 16777216L });
/* 5808 */   public static final BitSet FOLLOW_RDELIM_in_region316 = new BitSet(new long[] { 143893987328L });
/* 5809 */   public static final BitSet FOLLOW_template_in_region322 = new BitSet(new long[] { 2155872256L });
/* 5810 */   public static final BitSet FOLLOW_INDENT_in_region326 = new BitSet(new long[] { 8388608L });
/* 5811 */   public static final BitSet FOLLOW_LDELIM_in_region329 = new BitSet(new long[] { 17179869184L });
/* 5812 */   public static final BitSet FOLLOW_END_in_region331 = new BitSet(new long[] { 16777216L });
/* 5813 */   public static final BitSet FOLLOW_RDELIM_in_region333 = new BitSet(new long[] { 4294967298L });
/* 5814 */   public static final BitSet FOLLOW_NEWLINE_in_region344 = new BitSet(new long[] { 2L });
/* 5815 */   public static final BitSet FOLLOW_LCURLY_in_subtemplate420 = new BitSet(new long[] { 143929638912L });
/* 5816 */   public static final BitSet FOLLOW_ID_in_subtemplate426 = new BitSet(new long[] { 268697600L });
/* 5817 */   public static final BitSet FOLLOW_COMMA_in_subtemplate430 = new BitSet(new long[] { 33554432L });
/* 5818 */   public static final BitSet FOLLOW_ID_in_subtemplate435 = new BitSet(new long[] { 268697600L });
/* 5819 */   public static final BitSet FOLLOW_PIPE_in_subtemplate440 = new BitSet(new long[] { 143896084480L });
/* 5820 */   public static final BitSet FOLLOW_template_in_subtemplate445 = new BitSet(new long[] { 2149580800L });
/* 5821 */   public static final BitSet FOLLOW_INDENT_in_subtemplate447 = new BitSet(new long[] { 2097152L });
/* 5822 */   public static final BitSet FOLLOW_RCURLY_in_subtemplate450 = new BitSet(new long[] { 2L });
/* 5823 */   public static final BitSet FOLLOW_INDENT_in_ifstat491 = new BitSet(new long[] { 8388608L });
/* 5824 */   public static final BitSet FOLLOW_LDELIM_in_ifstat494 = new BitSet(new long[] { 16L });
/* 5825 */   public static final BitSet FOLLOW_IF_in_ifstat496 = new BitSet(new long[] { 16384L });
/* 5826 */   public static final BitSet FOLLOW_LPAREN_in_ifstat498 = new BitSet(new long[] { 111770944768L });
/* 5827 */   public static final BitSet FOLLOW_conditional_in_ifstat502 = new BitSet(new long[] { 32768L });
/* 5828 */   public static final BitSet FOLLOW_RPAREN_in_ifstat504 = new BitSet(new long[] { 16777216L });
/* 5829 */   public static final BitSet FOLLOW_RDELIM_in_ifstat506 = new BitSet(new long[] { 143893987328L });
/* 5830 */   public static final BitSet FOLLOW_template_in_ifstat515 = new BitSet(new long[] { 2155872256L });
/* 5831 */   public static final BitSet FOLLOW_INDENT_in_ifstat522 = new BitSet(new long[] { 8388608L });
/* 5832 */   public static final BitSet FOLLOW_LDELIM_in_ifstat525 = new BitSet(new long[] { 64L });
/* 5833 */   public static final BitSet FOLLOW_ELSEIF_in_ifstat527 = new BitSet(new long[] { 16384L });
/* 5834 */   public static final BitSet FOLLOW_LPAREN_in_ifstat529 = new BitSet(new long[] { 111770944768L });
/* 5835 */   public static final BitSet FOLLOW_conditional_in_ifstat533 = new BitSet(new long[] { 32768L });
/* 5836 */   public static final BitSet FOLLOW_RPAREN_in_ifstat535 = new BitSet(new long[] { 16777216L });
/* 5837 */   public static final BitSet FOLLOW_RDELIM_in_ifstat537 = new BitSet(new long[] { 143893987328L });
/* 5838 */   public static final BitSet FOLLOW_template_in_ifstat541 = new BitSet(new long[] { 2155872256L });
/* 5839 */   public static final BitSet FOLLOW_INDENT_in_ifstat551 = new BitSet(new long[] { 8388608L });
/* 5840 */   public static final BitSet FOLLOW_LDELIM_in_ifstat554 = new BitSet(new long[] { 32L });
/* 5841 */   public static final BitSet FOLLOW_ELSE_in_ifstat556 = new BitSet(new long[] { 16777216L });
/* 5842 */   public static final BitSet FOLLOW_RDELIM_in_ifstat558 = new BitSet(new long[] { 143893987328L });
/* 5843 */   public static final BitSet FOLLOW_template_in_ifstat562 = new BitSet(new long[] { 2155872256L });
/* 5844 */   public static final BitSet FOLLOW_INDENT_in_ifstat570 = new BitSet(new long[] { 8388608L });
/* 5845 */   public static final BitSet FOLLOW_LDELIM_in_ifstat576 = new BitSet(new long[] { 128L });
/* 5846 */   public static final BitSet FOLLOW_ENDIF_in_ifstat578 = new BitSet(new long[] { 16777216L });
/* 5847 */   public static final BitSet FOLLOW_RDELIM_in_ifstat582 = new BitSet(new long[] { 4294967298L });
/* 5848 */   public static final BitSet FOLLOW_NEWLINE_in_ifstat593 = new BitSet(new long[] { 2L });
/* 5849 */   public static final BitSet FOLLOW_andConditional_in_conditional713 = new BitSet(new long[] { 536870914L });
/* 5850 */   public static final BitSet FOLLOW_OR_in_conditional717 = new BitSet(new long[] { 111770944768L });
/* 5851 */   public static final BitSet FOLLOW_andConditional_in_conditional720 = new BitSet(new long[] { 536870914L });
/* 5852 */   public static final BitSet FOLLOW_notConditional_in_andConditional733 = new BitSet(new long[] { 1073741826L });
/* 5853 */   public static final BitSet FOLLOW_AND_in_andConditional737 = new BitSet(new long[] { 111770944768L });
/* 5854 */   public static final BitSet FOLLOW_notConditional_in_andConditional740 = new BitSet(new long[] { 1073741826L });
/* 5855 */   public static final BitSet FOLLOW_BANG_in_notConditional753 = new BitSet(new long[] { 111770944768L });
/* 5856 */   public static final BitSet FOLLOW_notConditional_in_notConditional756 = new BitSet(new long[] { 2L });
/* 5857 */   public static final BitSet FOLLOW_memberExpr_in_notConditional761 = new BitSet(new long[] { 2L });
/* 5858 */   public static final BitSet FOLLOW_ID_in_notConditionalExpr773 = new BitSet(new long[] { 524290L });
/* 5859 */   public static final BitSet FOLLOW_DOT_in_notConditionalExpr784 = new BitSet(new long[] { 33554432L });
/* 5860 */   public static final BitSet FOLLOW_ID_in_notConditionalExpr788 = new BitSet(new long[] { 524290L });
/* 5861 */   public static final BitSet FOLLOW_DOT_in_notConditionalExpr814 = new BitSet(new long[] { 16384L });
/* 5862 */   public static final BitSet FOLLOW_LPAREN_in_notConditionalExpr816 = new BitSet(new long[] { 111770943744L });
/* 5863 */   public static final BitSet FOLLOW_mapExpr_in_notConditionalExpr818 = new BitSet(new long[] { 32768L });
/* 5864 */   public static final BitSet FOLLOW_RPAREN_in_notConditionalExpr820 = new BitSet(new long[] { 524290L });
/* 5865 */   public static final BitSet FOLLOW_option_in_exprOptions850 = new BitSet(new long[] { 262146L });
/* 5866 */   public static final BitSet FOLLOW_COMMA_in_exprOptions854 = new BitSet(new long[] { 33554432L });
/* 5867 */   public static final BitSet FOLLOW_option_in_exprOptions856 = new BitSet(new long[] { 262146L });
/* 5868 */   public static final BitSet FOLLOW_ID_in_option883 = new BitSet(new long[] { 4098L });
/* 5869 */   public static final BitSet FOLLOW_EQUALS_in_option893 = new BitSet(new long[] { 111770943744L });
/* 5870 */   public static final BitSet FOLLOW_exprNoComma_in_option895 = new BitSet(new long[] { 2L });
/* 5871 */   public static final BitSet FOLLOW_memberExpr_in_exprNoComma1002 = new BitSet(new long[] { 8194L });
/* 5872 */   public static final BitSet FOLLOW_COLON_in_exprNoComma1008 = new BitSet(new long[] { 34619392L });
/* 5873 */   public static final BitSet FOLLOW_mapTemplateRef_in_exprNoComma1010 = new BitSet(new long[] { 2L });
/* 5874 */   public static final BitSet FOLLOW_mapExpr_in_expr1055 = new BitSet(new long[] { 2L });
/* 5875 */   public static final BitSet FOLLOW_memberExpr_in_mapExpr1067 = new BitSet(new long[] { 270338L });
/* 5876 */   public static final BitSet FOLLOW_COMMA_in_mapExpr1076 = new BitSet(new long[] { 111770943744L });
/* 5877 */   public static final BitSet FOLLOW_memberExpr_in_mapExpr1078 = new BitSet(new long[] { 270336L });
/* 5878 */   public static final BitSet FOLLOW_COLON_in_mapExpr1084 = new BitSet(new long[] { 34619392L });
/* 5879 */   public static final BitSet FOLLOW_mapTemplateRef_in_mapExpr1086 = new BitSet(new long[] { 8194L });
/* 5880 */   public static final BitSet FOLLOW_COLON_in_mapExpr1149 = new BitSet(new long[] { 34619392L });
/* 5881 */   public static final BitSet FOLLOW_mapTemplateRef_in_mapExpr1153 = new BitSet(new long[] { 270338L });
/* 5882 */   public static final BitSet FOLLOW_COMMA_in_mapExpr1159 = new BitSet(new long[] { 34619392L });
/* 5883 */   public static final BitSet FOLLOW_mapTemplateRef_in_mapExpr1163 = new BitSet(new long[] { 270338L });
/* 5884 */   public static final BitSet FOLLOW_ID_in_mapTemplateRef1210 = new BitSet(new long[] { 16384L });
/* 5885 */   public static final BitSet FOLLOW_LPAREN_in_mapTemplateRef1212 = new BitSet(new long[] { 111770978560L });
/* 5886 */   public static final BitSet FOLLOW_args_in_mapTemplateRef1214 = new BitSet(new long[] { 32768L });
/* 5887 */   public static final BitSet FOLLOW_RPAREN_in_mapTemplateRef1216 = new BitSet(new long[] { 2L });
/* 5888 */   public static final BitSet FOLLOW_subtemplate_in_mapTemplateRef1238 = new BitSet(new long[] { 2L });
/* 5889 */   public static final BitSet FOLLOW_LPAREN_in_mapTemplateRef1245 = new BitSet(new long[] { 111770943744L });
/* 5890 */   public static final BitSet FOLLOW_mapExpr_in_mapTemplateRef1247 = new BitSet(new long[] { 32768L });
/* 5891 */   public static final BitSet FOLLOW_RPAREN_in_mapTemplateRef1251 = new BitSet(new long[] { 16384L });
/* 5892 */   public static final BitSet FOLLOW_LPAREN_in_mapTemplateRef1253 = new BitSet(new long[] { 111770976512L });
/* 5893 */   public static final BitSet FOLLOW_argExprList_in_mapTemplateRef1255 = new BitSet(new long[] { 32768L });
/* 5894 */   public static final BitSet FOLLOW_RPAREN_in_mapTemplateRef1258 = new BitSet(new long[] { 2L });
/* 5895 */   public static final BitSet FOLLOW_includeExpr_in_memberExpr1281 = new BitSet(new long[] { 524290L });
/* 5896 */   public static final BitSet FOLLOW_DOT_in_memberExpr1292 = new BitSet(new long[] { 33554432L });
/* 5897 */   public static final BitSet FOLLOW_ID_in_memberExpr1294 = new BitSet(new long[] { 524290L });
/* 5898 */   public static final BitSet FOLLOW_DOT_in_memberExpr1320 = new BitSet(new long[] { 16384L });
/* 5899 */   public static final BitSet FOLLOW_LPAREN_in_memberExpr1322 = new BitSet(new long[] { 111770943744L });
/* 5900 */   public static final BitSet FOLLOW_mapExpr_in_memberExpr1324 = new BitSet(new long[] { 32768L });
/* 5901 */   public static final BitSet FOLLOW_RPAREN_in_memberExpr1326 = new BitSet(new long[] { 524290L });
/* 5902 */   public static final BitSet FOLLOW_ID_in_includeExpr1370 = new BitSet(new long[] { 16384L });
/* 5903 */   public static final BitSet FOLLOW_LPAREN_in_includeExpr1372 = new BitSet(new long[] { 111770976512L });
/* 5904 */   public static final BitSet FOLLOW_expr_in_includeExpr1374 = new BitSet(new long[] { 32768L });
/* 5905 */   public static final BitSet FOLLOW_RPAREN_in_includeExpr1377 = new BitSet(new long[] { 2L });
/* 5906 */   public static final BitSet FOLLOW_SUPER_in_includeExpr1398 = new BitSet(new long[] { 524288L });
/* 5907 */   public static final BitSet FOLLOW_DOT_in_includeExpr1400 = new BitSet(new long[] { 33554432L });
/* 5908 */   public static final BitSet FOLLOW_ID_in_includeExpr1402 = new BitSet(new long[] { 16384L });
/* 5909 */   public static final BitSet FOLLOW_LPAREN_in_includeExpr1404 = new BitSet(new long[] { 111770978560L });
/* 5910 */   public static final BitSet FOLLOW_args_in_includeExpr1406 = new BitSet(new long[] { 32768L });
/* 5911 */   public static final BitSet FOLLOW_RPAREN_in_includeExpr1408 = new BitSet(new long[] { 2L });
/* 5912 */   public static final BitSet FOLLOW_ID_in_includeExpr1427 = new BitSet(new long[] { 16384L });
/* 5913 */   public static final BitSet FOLLOW_LPAREN_in_includeExpr1429 = new BitSet(new long[] { 111770978560L });
/* 5914 */   public static final BitSet FOLLOW_args_in_includeExpr1431 = new BitSet(new long[] { 32768L });
/* 5915 */   public static final BitSet FOLLOW_RPAREN_in_includeExpr1433 = new BitSet(new long[] { 2L });
/* 5916 */   public static final BitSet FOLLOW_AT_in_includeExpr1455 = new BitSet(new long[] { 256L });
/* 5917 */   public static final BitSet FOLLOW_SUPER_in_includeExpr1457 = new BitSet(new long[] { 524288L });
/* 5918 */   public static final BitSet FOLLOW_DOT_in_includeExpr1459 = new BitSet(new long[] { 33554432L });
/* 5919 */   public static final BitSet FOLLOW_ID_in_includeExpr1461 = new BitSet(new long[] { 16384L });
/* 5920 */   public static final BitSet FOLLOW_LPAREN_in_includeExpr1463 = new BitSet(new long[] { 32768L });
/* 5921 */   public static final BitSet FOLLOW_RPAREN_in_includeExpr1467 = new BitSet(new long[] { 2L });
/* 5922 */   public static final BitSet FOLLOW_AT_in_includeExpr1482 = new BitSet(new long[] { 33554432L });
/* 5923 */   public static final BitSet FOLLOW_ID_in_includeExpr1484 = new BitSet(new long[] { 16384L });
/* 5924 */   public static final BitSet FOLLOW_LPAREN_in_includeExpr1486 = new BitSet(new long[] { 32768L });
/* 5925 */   public static final BitSet FOLLOW_RPAREN_in_includeExpr1490 = new BitSet(new long[] { 2L });
/* 5926 */   public static final BitSet FOLLOW_primary_in_includeExpr1508 = new BitSet(new long[] { 2L });
/* 5927 */   public static final BitSet FOLLOW_ID_in_primary1519 = new BitSet(new long[] { 2L });
/* 5928 */   public static final BitSet FOLLOW_STRING_in_primary1524 = new BitSet(new long[] { 2L });
/* 5929 */   public static final BitSet FOLLOW_TRUE_in_primary1529 = new BitSet(new long[] { 2L });
/* 5930 */   public static final BitSet FOLLOW_FALSE_in_primary1534 = new BitSet(new long[] { 2L });
/* 5931 */   public static final BitSet FOLLOW_subtemplate_in_primary1539 = new BitSet(new long[] { 2L });
/* 5932 */   public static final BitSet FOLLOW_list_in_primary1544 = new BitSet(new long[] { 2L });
/* 5933 */   public static final BitSet FOLLOW_LPAREN_in_primary1553 = new BitSet(new long[] { 111770944768L });
/* 5934 */   public static final BitSet FOLLOW_conditional_in_primary1556 = new BitSet(new long[] { 32768L });
/* 5935 */   public static final BitSet FOLLOW_RPAREN_in_primary1558 = new BitSet(new long[] { 2L });
/* 5936 */   public static final BitSet FOLLOW_LPAREN_in_primary1569 = new BitSet(new long[] { 111770943744L });
/* 5937 */   public static final BitSet FOLLOW_expr_in_primary1571 = new BitSet(new long[] { 32768L });
/* 5938 */   public static final BitSet FOLLOW_RPAREN_in_primary1573 = new BitSet(new long[] { 16386L });
/* 5939 */   public static final BitSet FOLLOW_LPAREN_in_primary1579 = new BitSet(new long[] { 111770976512L });
/* 5940 */   public static final BitSet FOLLOW_argExprList_in_primary1581 = new BitSet(new long[] { 32768L });
/* 5941 */   public static final BitSet FOLLOW_RPAREN_in_primary1584 = new BitSet(new long[] { 2L });
/* 5942 */   public static final BitSet FOLLOW_argExprList_in_args1640 = new BitSet(new long[] { 2L });
/* 5943 */   public static final BitSet FOLLOW_namedArg_in_args1645 = new BitSet(new long[] { 262146L });
/* 5944 */   public static final BitSet FOLLOW_COMMA_in_args1649 = new BitSet(new long[] { 33554432L });
/* 5945 */   public static final BitSet FOLLOW_namedArg_in_args1651 = new BitSet(new long[] { 262146L });
/* 5946 */   public static final BitSet FOLLOW_COMMA_in_args1657 = new BitSet(new long[] { 2048L });
/* 5947 */   public static final BitSet FOLLOW_ELLIPSIS_in_args1659 = new BitSet(new long[] { 2L });
/* 5948 */   public static final BitSet FOLLOW_ELLIPSIS_in_args1679 = new BitSet(new long[] { 2L });
/* 5949 */   public static final BitSet FOLLOW_arg_in_argExprList1692 = new BitSet(new long[] { 262146L });
/* 5950 */   public static final BitSet FOLLOW_COMMA_in_argExprList1696 = new BitSet(new long[] { 111770943744L });
/* 5951 */   public static final BitSet FOLLOW_arg_in_argExprList1698 = new BitSet(new long[] { 262146L });
/* 5952 */   public static final BitSet FOLLOW_exprNoComma_in_arg1715 = new BitSet(new long[] { 2L });
/* 5953 */   public static final BitSet FOLLOW_ID_in_namedArg1724 = new BitSet(new long[] { 4096L });
/* 5954 */   public static final BitSet FOLLOW_EQUALS_in_namedArg1726 = new BitSet(new long[] { 111770943744L });
/* 5955 */   public static final BitSet FOLLOW_arg_in_namedArg1728 = new BitSet(new long[] { 2L });
/* 5956 */   public static final BitSet FOLLOW_LBRACK_in_list1753 = new BitSet(new long[] { 131072L });
/* 5957 */   public static final BitSet FOLLOW_RBRACK_in_list1755 = new BitSet(new long[] { 2L });
/* 5958 */   public static final BitSet FOLLOW_LBRACK_in_list1767 = new BitSet(new long[] { 111771336960L });
/* 5959 */   public static final BitSet FOLLOW_listElement_in_list1769 = new BitSet(new long[] { 393216L });
/* 5960 */   public static final BitSet FOLLOW_COMMA_in_list1773 = new BitSet(new long[] { 111771336960L });
/* 5961 */   public static final BitSet FOLLOW_listElement_in_list1775 = new BitSet(new long[] { 393216L });
/* 5962 */   public static final BitSet FOLLOW_RBRACK_in_list1780 = new BitSet(new long[] { 2L });
/* 5963 */   public static final BitSet FOLLOW_exprNoComma_in_listElement1800 = new BitSet(new long[] { 2L });
/*      */ 
/*      */   public Parser[] getDelegates()
/*      */   {
/*   81 */     return new Parser[0];
/*      */   }
/*      */ 
/*      */   public STParser(TokenStream input)
/*      */   {
/*   88 */     this(input, new RecognizerSharedState());
/*      */   }
/*      */   public STParser(TokenStream input, RecognizerSharedState state) {
/*   91 */     super(input, state);
/*      */   }
/*      */ 
/*      */   public void setTreeAdaptor(TreeAdaptor adaptor)
/*      */   {
/*   97 */     this.adaptor = adaptor;
/*      */   }
/*      */   public TreeAdaptor getTreeAdaptor() {
/*  100 */     return this.adaptor;
/*      */   }
/*  102 */   public String[] getTokenNames() { return tokenNames; } 
/*  103 */   public String getGrammarFileName() { return "/usr/local/website/st/depot/stringtemplate4/src/org/stringtemplate/v4/compiler/STParser.g"; }
/*      */ 
/*      */ 
/*      */   public STParser(TokenStream input, ErrorManager errMgr, Token templateToken)
/*      */   {
/*  109 */     this(input);
/*  110 */     this.errMgr = errMgr;
/*  111 */     this.templateToken = templateToken;
/*      */   }
/*      */ 
/*      */   protected Object recoverFromMismatchedToken(IntStream input, int ttype, BitSet follow) throws RecognitionException
/*      */   {
/*  116 */     throw new MismatchedTokenException(ttype, input);
/*      */   }
/*      */ 
/*      */   public final templateAndEOF_return templateAndEOF()
/*      */     throws RecognitionException
/*      */   {
/*  129 */     templateAndEOF_return retval = new templateAndEOF_return();
/*  130 */     retval.start = this.input.LT(1);
/*      */ 
/*  133 */     CommonTree root_0 = null;
/*      */ 
/*  135 */     CommonToken EOF2 = null;
/*  136 */     template_return template1 = null;
/*      */ 
/*  139 */     CommonTree EOF2_tree = null;
/*  140 */     RewriteRuleTokenStream stream_EOF = new RewriteRuleTokenStream(this.adaptor, "token EOF");
/*  141 */     RewriteRuleSubtreeStream stream_template = new RewriteRuleSubtreeStream(this.adaptor, "rule template");
/*      */     try
/*      */     {
/*  146 */       pushFollow(FOLLOW_template_in_templateAndEOF139);
/*  147 */       template1 = template();
/*      */ 
/*  149 */       this.state._fsp -= 1;
/*      */ 
/*  151 */       stream_template.add(template1.getTree());
/*      */ 
/*  153 */       EOF2 = (CommonToken)match(this.input, -1, FOLLOW_EOF_in_templateAndEOF141);
/*  154 */       stream_EOF.add(EOF2);
/*      */ 
/*  164 */       retval.tree = root_0;
/*  165 */       RewriteRuleSubtreeStream stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.tree : null);
/*      */ 
/*  167 */       root_0 = (CommonTree)this.adaptor.nil();
/*      */ 
/*  171 */       if (stream_template.hasNext()) {
/*  172 */         this.adaptor.addChild(root_0, stream_template.nextTree());
/*      */       }
/*      */ 
/*  175 */       stream_template.reset();
/*      */ 
/*  180 */       retval.tree = root_0;
/*      */ 
/*  184 */       retval.stop = this.input.LT(-1);
/*      */ 
/*  187 */       retval.tree = ((CommonTree)this.adaptor.rulePostProcessing(root_0));
/*  188 */       this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
/*      */     }
/*      */     catch (RecognitionException re)
/*      */     {
/*  192 */       re = 
/*  196 */         re;
/*      */ 
/*  192 */       throw re;
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*  197 */     return retval;
/*      */   }
/*      */ 
/*      */   public final template_return template()
/*      */     throws RecognitionException
/*      */   {
/*  211 */     template_return retval = new template_return();
/*  212 */     retval.start = this.input.LT(1);
/*      */ 
/*  215 */     CommonTree root_0 = null;
/*      */ 
/*  217 */     element_return element3 = null;
/*      */     try
/*      */     {
/*  225 */       root_0 = (CommonTree)this.adaptor.nil();
/*      */       while (true)
/*      */       {
/*  231 */         int alt1 = 2;
/*  232 */         switch (this.input.LA(1))
/*      */         {
/*      */         case 31:
/*  235 */           int LA1_2 = this.input.LA(2);
/*      */ 
/*  237 */           if (LA1_2 == 23) {
/*  238 */             int LA1_5 = this.input.LA(3);
/*      */ 
/*  240 */             if ((LA1_5 == 4) || (LA1_5 == 8) || (LA1_5 == 14) || (LA1_5 == 16) || (LA1_5 == 20) || ((LA1_5 >= 25) && (LA1_5 <= 26)) || (LA1_5 == 33) || ((LA1_5 >= 35) && (LA1_5 <= 36))) {
/*  241 */               alt1 = 1;
/*      */             }
/*      */ 
/*      */           }
/*  246 */           else if ((LA1_2 == 22) || (LA1_2 == 32) || (LA1_2 == 37)) {
/*  247 */             alt1 = 1;
/*      */           }
/*      */ 
/*  252 */           break;
/*      */         case 23:
/*  255 */           int LA1_3 = this.input.LA(2);
/*      */ 
/*  257 */           if ((LA1_3 == 4) || (LA1_3 == 8) || (LA1_3 == 14) || (LA1_3 == 16) || (LA1_3 == 20) || ((LA1_3 >= 25) && (LA1_3 <= 26)) || (LA1_3 == 33) || ((LA1_3 >= 35) && (LA1_3 <= 36))) {
/*  258 */             alt1 = 1;
/*      */           }
/*      */ 
/*  263 */           break;
/*      */         case 22:
/*      */         case 32:
/*      */         case 37:
/*  268 */           alt1 = 1;
/*      */         }
/*      */ 
/*  274 */         switch (alt1)
/*      */         {
/*      */         case 1:
/*  278 */           pushFollow(FOLLOW_element_in_template155);
/*  279 */           element3 = element();
/*      */ 
/*  281 */           this.state._fsp -= 1;
/*      */ 
/*  283 */           this.adaptor.addChild(root_0, element3.getTree());
/*      */ 
/*  286 */           break;
/*      */         default:
/*  289 */           break label396;
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/*  296 */       label396: retval.stop = this.input.LT(-1);
/*      */ 
/*  299 */       retval.tree = ((CommonTree)this.adaptor.rulePostProcessing(root_0));
/*  300 */       this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
/*      */     }
/*      */     catch (RecognitionException re)
/*      */     {
/*  304 */       re = 
/*  308 */         re;
/*      */ 
/*  304 */       throw re;
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*  309 */     return retval;
/*      */   }
/*      */ 
/*      */   public final element_return element()
/*      */     throws RecognitionException
/*      */   {
/*  323 */     element_return retval = new element_return();
/*  324 */     retval.start = this.input.LT(1);
/*      */ 
/*  327 */     CommonTree root_0 = null;
/*      */ 
/*  329 */     CommonToken INDENT4 = null;
/*  330 */     CommonToken COMMENT5 = null;
/*  331 */     CommonToken NEWLINE6 = null;
/*  332 */     CommonToken INDENT7 = null;
/*  333 */     singleElement_return singleElement8 = null;
/*      */ 
/*  335 */     singleElement_return singleElement9 = null;
/*      */ 
/*  337 */     compoundElement_return compoundElement10 = null;
/*      */ 
/*  340 */     CommonTree INDENT4_tree = null;
/*  341 */     CommonTree COMMENT5_tree = null;
/*  342 */     CommonTree NEWLINE6_tree = null;
/*  343 */     CommonTree INDENT7_tree = null;
/*  344 */     RewriteRuleTokenStream stream_NEWLINE = new RewriteRuleTokenStream(this.adaptor, "token NEWLINE");
/*  345 */     RewriteRuleTokenStream stream_COMMENT = new RewriteRuleTokenStream(this.adaptor, "token COMMENT");
/*  346 */     RewriteRuleTokenStream stream_INDENT = new RewriteRuleTokenStream(this.adaptor, "token INDENT");
/*  347 */     RewriteRuleSubtreeStream stream_singleElement = new RewriteRuleSubtreeStream(this.adaptor, "rule singleElement");
/*      */     try
/*      */     {
/*  350 */       int alt3 = 4;
/*  351 */       switch (this.input.LA(1))
/*      */       {
/*      */       case 31:
/*  354 */         switch (this.input.LA(2))
/*      */         {
/*      */         case 37:
/*  357 */           int LA3_5 = this.input.LA(3);
/*      */ 
/*  359 */           if (LA3_5 == 32) {
/*  360 */             int LA3_11 = this.input.LA(4);
/*      */ 
/*  362 */             if (this.input.LT(1).getCharPositionInLine() == 0) {
/*  363 */               alt3 = 1;
/*      */             }
/*      */             else {
/*  366 */               alt3 = 2;
/*      */             }
/*      */ 
/*      */           }
/*  376 */           else if ((LA3_5 == -1) || ((LA3_5 >= 21) && (LA3_5 <= 23)) || (LA3_5 == 31) || (LA3_5 == 37)) {
/*  377 */             alt3 = 2;
/*      */           }
/*      */           else {
/*  380 */             NoViableAltException nvae = new NoViableAltException("", 3, 5, this.input);
/*      */ 
/*  383 */             throw nvae;
/*      */           }
/*      */ 
/*  387 */           break;
/*      */         case 23:
/*  390 */           switch (this.input.LA(3))
/*      */           {
/*      */           case 4:
/*  393 */             alt3 = 4;
/*      */ 
/*  395 */             break;
/*      */           case 33:
/*  398 */             int LA3_12 = this.input.LA(4);
/*      */ 
/*  400 */             if (LA3_12 == 25) {
/*  401 */               int LA3_15 = this.input.LA(5);
/*      */ 
/*  403 */               if (LA3_15 == 24) {
/*  404 */                 alt3 = 4;
/*      */               }
/*  406 */               else if (LA3_15 == 14) {
/*  407 */                 alt3 = 2;
/*      */               }
/*      */               else {
/*  410 */                 NoViableAltException nvae = new NoViableAltException("", 3, 15, this.input);
/*      */ 
/*  413 */                 throw nvae;
/*      */               }
/*      */ 
/*      */             }
/*  417 */             else if (LA3_12 == 8) {
/*  418 */               alt3 = 2;
/*      */             }
/*      */             else {
/*  421 */               NoViableAltException nvae = new NoViableAltException("", 3, 12, this.input);
/*      */ 
/*  424 */               throw nvae;
/*      */             }
/*      */ 
/*  428 */             break;
/*      */           case 8:
/*      */           case 14:
/*      */           case 16:
/*      */           case 20:
/*      */           case 25:
/*      */           case 26:
/*      */           case 35:
/*      */           case 36:
/*  438 */             alt3 = 2;
/*      */ 
/*  440 */             break;
/*      */           case 5:
/*      */           case 6:
/*      */           case 7:
/*      */           case 9:
/*      */           case 10:
/*      */           case 11:
/*      */           case 12:
/*      */           case 13:
/*      */           case 15:
/*      */           case 17:
/*      */           case 18:
/*      */           case 19:
/*      */           case 21:
/*      */           case 22:
/*      */           case 23:
/*      */           case 24:
/*      */           case 27:
/*      */           case 28:
/*      */           case 29:
/*      */           case 30:
/*      */           case 31:
/*      */           case 32:
/*      */           case 34:
/*      */           default:
/*  442 */             NoViableAltException nvae = new NoViableAltException("", 3, 6, this.input);
/*      */ 
/*  445 */             throw nvae;
/*      */           }
/*      */ 
/*  450 */           break;
/*      */         case 22:
/*      */         case 32:
/*  454 */           alt3 = 2;
/*      */ 
/*  456 */           break;
/*      */         default:
/*  458 */           NoViableAltException nvae = new NoViableAltException("", 3, 1, this.input);
/*      */ 
/*  461 */           throw nvae;
/*      */         }
/*      */ 
/*  466 */         break;
/*      */       case 37:
/*  469 */         int LA3_2 = this.input.LA(2);
/*      */ 
/*  471 */         if (LA3_2 == 32) {
/*  472 */           int LA3_8 = this.input.LA(3);
/*      */ 
/*  474 */           if (this.input.LT(1).getCharPositionInLine() == 0) {
/*  475 */             alt3 = 1;
/*      */           }
/*      */           else {
/*  478 */             alt3 = 3;
/*      */           }
/*      */ 
/*      */         }
/*  488 */         else if ((LA3_2 == -1) || ((LA3_2 >= 21) && (LA3_2 <= 23)) || (LA3_2 == 31) || (LA3_2 == 37)) {
/*  489 */           alt3 = 3;
/*      */         }
/*      */         else {
/*  492 */           NoViableAltException nvae = new NoViableAltException("", 3, 2, this.input);
/*      */ 
/*  495 */           throw nvae;
/*      */         }
/*      */ 
/*  499 */         break;
/*      */       case 23:
/*  502 */         switch (this.input.LA(2))
/*      */         {
/*      */         case 4:
/*  505 */           alt3 = 4;
/*      */ 
/*  507 */           break;
/*      */         case 33:
/*  510 */           int LA3_10 = this.input.LA(3);
/*      */ 
/*  512 */           if (LA3_10 == 25) {
/*  513 */             int LA3_14 = this.input.LA(4);
/*      */ 
/*  515 */             if (LA3_14 == 24) {
/*  516 */               alt3 = 4;
/*      */             }
/*  518 */             else if (LA3_14 == 14) {
/*  519 */               alt3 = 3;
/*      */             }
/*      */             else {
/*  522 */               NoViableAltException nvae = new NoViableAltException("", 3, 14, this.input);
/*      */ 
/*  525 */               throw nvae;
/*      */             }
/*      */ 
/*      */           }
/*  529 */           else if (LA3_10 == 8) {
/*  530 */             alt3 = 3;
/*      */           }
/*      */           else {
/*  533 */             NoViableAltException nvae = new NoViableAltException("", 3, 10, this.input);
/*      */ 
/*  536 */             throw nvae;
/*      */           }
/*      */ 
/*  540 */           break;
/*      */         case 8:
/*      */         case 14:
/*      */         case 16:
/*      */         case 20:
/*      */         case 25:
/*      */         case 26:
/*      */         case 35:
/*      */         case 36:
/*  550 */           alt3 = 3;
/*      */ 
/*  552 */           break;
/*      */         case 5:
/*      */         case 6:
/*      */         case 7:
/*      */         case 9:
/*      */         case 10:
/*      */         case 11:
/*      */         case 12:
/*      */         case 13:
/*      */         case 15:
/*      */         case 17:
/*      */         case 18:
/*      */         case 19:
/*      */         case 21:
/*      */         case 22:
/*      */         case 23:
/*      */         case 24:
/*      */         case 27:
/*      */         case 28:
/*      */         case 29:
/*      */         case 30:
/*      */         case 31:
/*      */         case 32:
/*      */         case 34:
/*      */         default:
/*  554 */           NoViableAltException nvae = new NoViableAltException("", 3, 3, this.input);
/*      */ 
/*  557 */           throw nvae;
/*      */         }
/*      */ 
/*  562 */         break;
/*      */       case 22:
/*      */       case 32:
/*  566 */         alt3 = 3;
/*      */ 
/*  568 */         break;
/*      */       default:
/*  570 */         NoViableAltException nvae = new NoViableAltException("", 3, 0, this.input);
/*      */ 
/*  573 */         throw nvae;
/*      */       }
/*      */ 
/*  577 */       switch (alt3)
/*      */       {
/*      */       case 1:
/*  581 */         if (this.input.LT(1).getCharPositionInLine() != 0) {
/*  582 */           throw new FailedPredicateException(this.input, "element", "input.LT(1).getCharPositionInLine()==0");
/*      */         }
/*      */ 
/*  586 */         int alt2 = 2;
/*  587 */         int LA2_0 = this.input.LA(1);
/*      */ 
/*  589 */         if (LA2_0 == 31) {
/*  590 */           alt2 = 1;
/*      */         }
/*  592 */         switch (alt2)
/*      */         {
/*      */         case 1:
/*  596 */           INDENT4 = (CommonToken)match(this.input, 31, FOLLOW_INDENT_in_element168);
/*  597 */           stream_INDENT.add(INDENT4);
/*      */         }
/*      */ 
/*  606 */         COMMENT5 = (CommonToken)match(this.input, 37, FOLLOW_COMMENT_in_element171);
/*  607 */         stream_COMMENT.add(COMMENT5);
/*      */ 
/*  610 */         NEWLINE6 = (CommonToken)match(this.input, 32, FOLLOW_NEWLINE_in_element173);
/*  611 */         stream_NEWLINE.add(NEWLINE6);
/*      */ 
/*  621 */         retval.tree = root_0;
/*  622 */         RewriteRuleSubtreeStream stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.tree : null);
/*      */ 
/*  624 */         root_0 = (CommonTree)this.adaptor.nil();
/*      */ 
/*  627 */         root_0 = null;
/*      */ 
/*  631 */         retval.tree = root_0;
/*      */ 
/*  634 */         break;
/*      */       case 2:
/*  638 */         INDENT7 = (CommonToken)match(this.input, 31, FOLLOW_INDENT_in_element181);
/*  639 */         stream_INDENT.add(INDENT7);
/*      */ 
/*  642 */         pushFollow(FOLLOW_singleElement_in_element183);
/*  643 */         singleElement8 = singleElement();
/*      */ 
/*  645 */         this.state._fsp -= 1;
/*      */ 
/*  647 */         stream_singleElement.add(singleElement8.getTree());
/*      */ 
/*  656 */         retval.tree = root_0;
/*  657 */         RewriteRuleSubtreeStream stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.tree : null);
/*      */ 
/*  659 */         root_0 = (CommonTree)this.adaptor.nil();
/*      */ 
/*  664 */         CommonTree root_1 = (CommonTree)this.adaptor.nil();
/*  665 */         root_1 = (CommonTree)this.adaptor.becomeRoot((CommonTree)this.adaptor.create(47, "INDENTED_EXPR"), root_1);
/*      */ 
/*  669 */         this.adaptor.addChild(root_1, stream_INDENT.nextNode());
/*      */ 
/*  674 */         if (stream_singleElement.hasNext()) {
/*  675 */           this.adaptor.addChild(root_1, stream_singleElement.nextTree());
/*      */         }
/*      */ 
/*  678 */         stream_singleElement.reset();
/*      */ 
/*  680 */         this.adaptor.addChild(root_0, root_1);
/*      */ 
/*  686 */         retval.tree = root_0;
/*      */ 
/*  689 */         break;
/*      */       case 3:
/*  693 */         root_0 = (CommonTree)this.adaptor.nil();
/*      */ 
/*  696 */         pushFollow(FOLLOW_singleElement_in_element200);
/*  697 */         singleElement9 = singleElement();
/*      */ 
/*  699 */         this.state._fsp -= 1;
/*      */ 
/*  701 */         this.adaptor.addChild(root_0, singleElement9.getTree());
/*      */ 
/*  704 */         break;
/*      */       case 4:
/*  708 */         root_0 = (CommonTree)this.adaptor.nil();
/*      */ 
/*  711 */         pushFollow(FOLLOW_compoundElement_in_element205);
/*  712 */         compoundElement10 = compoundElement();
/*      */ 
/*  714 */         this.state._fsp -= 1;
/*      */ 
/*  716 */         this.adaptor.addChild(root_0, compoundElement10.getTree());
/*      */       }
/*      */ 
/*  722 */       retval.stop = this.input.LT(-1);
/*      */ 
/*  725 */       retval.tree = ((CommonTree)this.adaptor.rulePostProcessing(root_0));
/*  726 */       this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
/*      */     }
/*      */     catch (RecognitionException re)
/*      */     {
/*  730 */       re = 
/*  734 */         re;
/*      */ 
/*  730 */       throw re;
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*  735 */     return retval;
/*      */   }
/*      */ 
/*      */   public final singleElement_return singleElement()
/*      */     throws RecognitionException
/*      */   {
/*  749 */     singleElement_return retval = new singleElement_return();
/*  750 */     retval.start = this.input.LT(1);
/*      */ 
/*  753 */     CommonTree root_0 = null;
/*      */ 
/*  755 */     CommonToken TEXT12 = null;
/*  756 */     CommonToken NEWLINE13 = null;
/*  757 */     CommonToken COMMENT14 = null;
/*  758 */     exprTag_return exprTag11 = null;
/*      */ 
/*  761 */     CommonTree TEXT12_tree = null;
/*  762 */     CommonTree NEWLINE13_tree = null;
/*  763 */     CommonTree COMMENT14_tree = null;
/*      */     try
/*      */     {
/*  767 */       int alt4 = 4;
/*  768 */       switch (this.input.LA(1))
/*      */       {
/*      */       case 23:
/*  771 */         alt4 = 1;
/*      */ 
/*  773 */         break;
/*      */       case 22:
/*  776 */         alt4 = 2;
/*      */ 
/*  778 */         break;
/*      */       case 32:
/*  781 */         alt4 = 3;
/*      */ 
/*  783 */         break;
/*      */       case 37:
/*  786 */         alt4 = 4;
/*      */ 
/*  788 */         break;
/*      */       default:
/*  790 */         NoViableAltException nvae = new NoViableAltException("", 4, 0, this.input);
/*      */ 
/*  793 */         throw nvae;
/*      */       }
/*      */ 
/*  797 */       switch (alt4)
/*      */       {
/*      */       case 1:
/*  801 */         root_0 = (CommonTree)this.adaptor.nil();
/*      */ 
/*  804 */         pushFollow(FOLLOW_exprTag_in_singleElement216);
/*  805 */         exprTag11 = exprTag();
/*      */ 
/*  807 */         this.state._fsp -= 1;
/*      */ 
/*  809 */         this.adaptor.addChild(root_0, exprTag11.getTree());
/*      */ 
/*  812 */         break;
/*      */       case 2:
/*  816 */         root_0 = (CommonTree)this.adaptor.nil();
/*      */ 
/*  819 */         TEXT12 = (CommonToken)match(this.input, 22, FOLLOW_TEXT_in_singleElement221);
/*  820 */         TEXT12_tree = (CommonTree)this.adaptor.create(TEXT12);
/*      */ 
/*  823 */         this.adaptor.addChild(root_0, TEXT12_tree);
/*      */ 
/*  827 */         break;
/*      */       case 3:
/*  831 */         root_0 = (CommonTree)this.adaptor.nil();
/*      */ 
/*  834 */         NEWLINE13 = (CommonToken)match(this.input, 32, FOLLOW_NEWLINE_in_singleElement226);
/*  835 */         NEWLINE13_tree = (CommonTree)this.adaptor.create(NEWLINE13);
/*      */ 
/*  838 */         this.adaptor.addChild(root_0, NEWLINE13_tree);
/*      */ 
/*  842 */         break;
/*      */       case 4:
/*  846 */         root_0 = (CommonTree)this.adaptor.nil();
/*      */ 
/*  849 */         COMMENT14 = (CommonToken)match(this.input, 37, FOLLOW_COMMENT_in_singleElement231);
/*      */       }
/*      */ 
/*  855 */       retval.stop = this.input.LT(-1);
/*      */ 
/*  858 */       retval.tree = ((CommonTree)this.adaptor.rulePostProcessing(root_0));
/*  859 */       this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
/*      */     }
/*      */     catch (RecognitionException re)
/*      */     {
/*  863 */       re = 
/*  867 */         re;
/*      */ 
/*  863 */       throw re;
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*  868 */     return retval;
/*      */   }
/*      */ 
/*      */   public final compoundElement_return compoundElement()
/*      */     throws RecognitionException
/*      */   {
/*  882 */     compoundElement_return retval = new compoundElement_return();
/*  883 */     retval.start = this.input.LT(1);
/*      */ 
/*  886 */     CommonTree root_0 = null;
/*      */ 
/*  888 */     ifstat_return ifstat15 = null;
/*      */ 
/*  890 */     region_return region16 = null;
/*      */     try
/*      */     {
/*  896 */       int alt5 = 2;
/*  897 */       int LA5_0 = this.input.LA(1);
/*      */ 
/*  899 */       if (LA5_0 == 31) {
/*  900 */         int LA5_1 = this.input.LA(2);
/*      */ 
/*  902 */         if (LA5_1 == 23) {
/*  903 */           int LA5_2 = this.input.LA(3);
/*      */ 
/*  905 */           if (LA5_2 == 4) {
/*  906 */             alt5 = 1;
/*      */           }
/*  908 */           else if (LA5_2 == 33) {
/*  909 */             alt5 = 2;
/*      */           }
/*      */           else {
/*  912 */             NoViableAltException nvae = new NoViableAltException("", 5, 2, this.input);
/*      */ 
/*  915 */             throw nvae;
/*      */           }
/*      */         }
/*      */         else
/*      */         {
/*  920 */           NoViableAltException nvae = new NoViableAltException("", 5, 1, this.input);
/*      */ 
/*  923 */           throw nvae;
/*      */         }
/*      */ 
/*      */       }
/*  927 */       else if (LA5_0 == 23) {
/*  928 */         int LA5_2 = this.input.LA(2);
/*      */ 
/*  930 */         if (LA5_2 == 4) {
/*  931 */           alt5 = 1;
/*      */         }
/*  933 */         else if (LA5_2 == 33) {
/*  934 */           alt5 = 2;
/*      */         }
/*      */         else {
/*  937 */           NoViableAltException nvae = new NoViableAltException("", 5, 2, this.input);
/*      */ 
/*  940 */           throw nvae;
/*      */         }
/*      */       }
/*      */       else
/*      */       {
/*  945 */         NoViableAltException nvae = new NoViableAltException("", 5, 0, this.input);
/*      */ 
/*  948 */         throw nvae;
/*      */       }
/*      */ 
/*  951 */       switch (alt5)
/*      */       {
/*      */       case 1:
/*  955 */         root_0 = (CommonTree)this.adaptor.nil();
/*      */ 
/*  958 */         pushFollow(FOLLOW_ifstat_in_compoundElement244);
/*  959 */         ifstat15 = ifstat();
/*      */ 
/*  961 */         this.state._fsp -= 1;
/*      */ 
/*  963 */         this.adaptor.addChild(root_0, ifstat15.getTree());
/*      */ 
/*  966 */         break;
/*      */       case 2:
/*  970 */         root_0 = (CommonTree)this.adaptor.nil();
/*      */ 
/*  973 */         pushFollow(FOLLOW_region_in_compoundElement249);
/*  974 */         region16 = region();
/*      */ 
/*  976 */         this.state._fsp -= 1;
/*      */ 
/*  978 */         this.adaptor.addChild(root_0, region16.getTree());
/*      */       }
/*      */ 
/*  984 */       retval.stop = this.input.LT(-1);
/*      */ 
/*  987 */       retval.tree = ((CommonTree)this.adaptor.rulePostProcessing(root_0));
/*  988 */       this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
/*      */     }
/*      */     catch (RecognitionException re)
/*      */     {
/*  992 */       re = 
/*  996 */         re;
/*      */ 
/*  992 */       throw re;
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*  997 */     return retval;
/*      */   }
/*      */ 
/*      */   public final exprTag_return exprTag()
/*      */     throws RecognitionException
/*      */   {
/* 1011 */     exprTag_return retval = new exprTag_return();
/* 1012 */     retval.start = this.input.LT(1);
/*      */ 
/* 1015 */     CommonTree root_0 = null;
/*      */ 
/* 1017 */     CommonToken LDELIM17 = null;
/* 1018 */     CommonToken char_literal19 = null;
/* 1019 */     CommonToken RDELIM21 = null;
/* 1020 */     expr_return expr18 = null;
/*      */ 
/* 1022 */     exprOptions_return exprOptions20 = null;
/*      */ 
/* 1025 */     CommonTree LDELIM17_tree = null;
/* 1026 */     CommonTree char_literal19_tree = null;
/* 1027 */     CommonTree RDELIM21_tree = null;
/* 1028 */     RewriteRuleTokenStream stream_RDELIM = new RewriteRuleTokenStream(this.adaptor, "token RDELIM");
/* 1029 */     RewriteRuleTokenStream stream_LDELIM = new RewriteRuleTokenStream(this.adaptor, "token LDELIM");
/* 1030 */     RewriteRuleTokenStream stream_SEMI = new RewriteRuleTokenStream(this.adaptor, "token SEMI");
/* 1031 */     RewriteRuleSubtreeStream stream_exprOptions = new RewriteRuleSubtreeStream(this.adaptor, "rule exprOptions");
/* 1032 */     RewriteRuleSubtreeStream stream_expr = new RewriteRuleSubtreeStream(this.adaptor, "rule expr");
/*      */     try
/*      */     {
/* 1037 */       LDELIM17 = (CommonToken)match(this.input, 23, FOLLOW_LDELIM_in_exprTag260);
/* 1038 */       stream_LDELIM.add(LDELIM17);
/*      */ 
/* 1041 */       pushFollow(FOLLOW_expr_in_exprTag262);
/* 1042 */       expr18 = expr();
/*      */ 
/* 1044 */       this.state._fsp -= 1;
/*      */ 
/* 1046 */       stream_expr.add(expr18.getTree());
/*      */ 
/* 1049 */       int alt6 = 2;
/* 1050 */       int LA6_0 = this.input.LA(1);
/*      */ 
/* 1052 */       if (LA6_0 == 9) {
/* 1053 */         alt6 = 1;
/*      */       }
/* 1055 */       switch (alt6)
/*      */       {
/*      */       case 1:
/* 1059 */         char_literal19 = (CommonToken)match(this.input, 9, FOLLOW_SEMI_in_exprTag266);
/* 1060 */         stream_SEMI.add(char_literal19);
/*      */ 
/* 1063 */         pushFollow(FOLLOW_exprOptions_in_exprTag268);
/* 1064 */         exprOptions20 = exprOptions();
/*      */ 
/* 1066 */         this.state._fsp -= 1;
/*      */ 
/* 1068 */         stream_exprOptions.add(exprOptions20.getTree());
/*      */       }
/*      */ 
/* 1076 */       RDELIM21 = (CommonToken)match(this.input, 24, FOLLOW_RDELIM_in_exprTag273);
/* 1077 */       stream_RDELIM.add(RDELIM21);
/*      */ 
/* 1087 */       retval.tree = root_0;
/* 1088 */       RewriteRuleSubtreeStream stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.tree : null);
/*      */ 
/* 1090 */       root_0 = (CommonTree)this.adaptor.nil();
/*      */ 
/* 1095 */       CommonTree root_1 = (CommonTree)this.adaptor.nil();
/* 1096 */       root_1 = (CommonTree)this.adaptor.becomeRoot((CommonTree)this.adaptor.create(41, LDELIM17, "EXPR"), root_1);
/*      */ 
/* 1100 */       this.adaptor.addChild(root_1, stream_expr.nextTree());
/*      */ 
/* 1103 */       if (stream_exprOptions.hasNext()) {
/* 1104 */         this.adaptor.addChild(root_1, stream_exprOptions.nextTree());
/*      */       }
/*      */ 
/* 1107 */       stream_exprOptions.reset();
/*      */ 
/* 1109 */       this.adaptor.addChild(root_0, root_1);
/*      */ 
/* 1115 */       retval.tree = root_0;
/*      */ 
/* 1119 */       retval.stop = this.input.LT(-1);
/*      */ 
/* 1122 */       retval.tree = ((CommonTree)this.adaptor.rulePostProcessing(root_0));
/* 1123 */       this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
/*      */     }
/*      */     catch (RecognitionException re)
/*      */     {
/* 1127 */       re = 
/* 1131 */         re;
/*      */ 
/* 1127 */       throw re;
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/* 1132 */     return retval;
/*      */   }
/*      */ 
/*      */   public final region_return region()
/*      */     throws RecognitionException
/*      */   {
/* 1146 */     region_return retval = new region_return();
/* 1147 */     retval.start = this.input.LT(1);
/*      */ 
/* 1150 */     CommonTree root_0 = null;
/*      */ 
/* 1152 */     CommonToken i = null;
/* 1153 */     CommonToken x = null;
/* 1154 */     CommonToken char_literal22 = null;
/* 1155 */     CommonToken ID23 = null;
/* 1156 */     CommonToken RDELIM24 = null;
/* 1157 */     CommonToken INDENT26 = null;
/* 1158 */     CommonToken LDELIM27 = null;
/* 1159 */     CommonToken string_literal28 = null;
/* 1160 */     CommonToken RDELIM29 = null;
/* 1161 */     CommonToken NEWLINE30 = null;
/* 1162 */     template_return template25 = null;
/*      */ 
/* 1165 */     CommonTree i_tree = null;
/* 1166 */     CommonTree x_tree = null;
/* 1167 */     CommonTree char_literal22_tree = null;
/* 1168 */     CommonTree ID23_tree = null;
/* 1169 */     CommonTree RDELIM24_tree = null;
/* 1170 */     CommonTree INDENT26_tree = null;
/* 1171 */     CommonTree LDELIM27_tree = null;
/* 1172 */     CommonTree string_literal28_tree = null;
/* 1173 */     CommonTree RDELIM29_tree = null;
/* 1174 */     CommonTree NEWLINE30_tree = null;
/* 1175 */     RewriteRuleTokenStream stream_AT = new RewriteRuleTokenStream(this.adaptor, "token AT");
/* 1176 */     RewriteRuleTokenStream stream_RDELIM = new RewriteRuleTokenStream(this.adaptor, "token RDELIM");
/* 1177 */     RewriteRuleTokenStream stream_NEWLINE = new RewriteRuleTokenStream(this.adaptor, "token NEWLINE");
/* 1178 */     RewriteRuleTokenStream stream_ID = new RewriteRuleTokenStream(this.adaptor, "token ID");
/* 1179 */     RewriteRuleTokenStream stream_END = new RewriteRuleTokenStream(this.adaptor, "token END");
/* 1180 */     RewriteRuleTokenStream stream_LDELIM = new RewriteRuleTokenStream(this.adaptor, "token LDELIM");
/* 1181 */     RewriteRuleTokenStream stream_INDENT = new RewriteRuleTokenStream(this.adaptor, "token INDENT");
/* 1182 */     RewriteRuleSubtreeStream stream_template = new RewriteRuleSubtreeStream(this.adaptor, "rule template");
/* 1183 */     Token indent = null;
/*      */     try
/*      */     {
/* 1189 */       int alt7 = 2;
/* 1190 */       int LA7_0 = this.input.LA(1);
/*      */ 
/* 1192 */       if (LA7_0 == 31) {
/* 1193 */         alt7 = 1;
/*      */       }
/* 1195 */       switch (alt7)
/*      */       {
/*      */       case 1:
/* 1199 */         i = (CommonToken)match(this.input, 31, FOLLOW_INDENT_in_region305);
/* 1200 */         stream_INDENT.add(i);
/*      */       }
/*      */ 
/* 1209 */       x = (CommonToken)match(this.input, 23, FOLLOW_LDELIM_in_region310);
/* 1210 */       stream_LDELIM.add(x);
/*      */ 
/* 1213 */       char_literal22 = (CommonToken)match(this.input, 33, FOLLOW_AT_in_region312);
/* 1214 */       stream_AT.add(char_literal22);
/*      */ 
/* 1217 */       ID23 = (CommonToken)match(this.input, 25, FOLLOW_ID_in_region314);
/* 1218 */       stream_ID.add(ID23);
/*      */ 
/* 1221 */       RDELIM24 = (CommonToken)match(this.input, 24, FOLLOW_RDELIM_in_region316);
/* 1222 */       stream_RDELIM.add(RDELIM24);
/*      */ 
/* 1225 */       if (this.input.LA(1) != 32) indent = i;
/*      */ 
/* 1227 */       pushFollow(FOLLOW_template_in_region322);
/* 1228 */       template25 = template();
/*      */ 
/* 1230 */       this.state._fsp -= 1;
/*      */ 
/* 1232 */       stream_template.add(template25.getTree());
/*      */ 
/* 1235 */       int alt8 = 2;
/* 1236 */       int LA8_0 = this.input.LA(1);
/*      */ 
/* 1238 */       if (LA8_0 == 31) {
/* 1239 */         alt8 = 1;
/*      */       }
/* 1241 */       switch (alt8)
/*      */       {
/*      */       case 1:
/* 1245 */         INDENT26 = (CommonToken)match(this.input, 31, FOLLOW_INDENT_in_region326);
/* 1246 */         stream_INDENT.add(INDENT26);
/*      */       }
/*      */ 
/* 1255 */       LDELIM27 = (CommonToken)match(this.input, 23, FOLLOW_LDELIM_in_region329);
/* 1256 */       stream_LDELIM.add(LDELIM27);
/*      */ 
/* 1259 */       string_literal28 = (CommonToken)match(this.input, 34, FOLLOW_END_in_region331);
/* 1260 */       stream_END.add(string_literal28);
/*      */ 
/* 1263 */       RDELIM29 = (CommonToken)match(this.input, 24, FOLLOW_RDELIM_in_region333);
/* 1264 */       stream_RDELIM.add(RDELIM29);
/*      */ 
/* 1268 */       int alt9 = 2;
/* 1269 */       int LA9_0 = this.input.LA(1);
/*      */ 
/* 1271 */       if (LA9_0 == 32) {
/* 1272 */         int LA9_1 = this.input.LA(2);
/*      */ 
/* 1274 */         if (((CommonToken)retval.start).getLine() != this.input.LT(1).getLine()) {
/* 1275 */           alt9 = 1;
/*      */         }
/*      */       }
/* 1278 */       switch (alt9)
/*      */       {
/*      */       case 1:
/* 1282 */         if (((CommonToken)retval.start).getLine() == this.input.LT(1).getLine()) {
/* 1283 */           throw new FailedPredicateException(this.input, "region", "$region.start.getLine()!=input.LT(1).getLine()");
/*      */         }
/*      */ 
/* 1286 */         NEWLINE30 = (CommonToken)match(this.input, 32, FOLLOW_NEWLINE_in_region344);
/* 1287 */         stream_NEWLINE.add(NEWLINE30);
/*      */       }
/*      */ 
/* 1303 */       retval.tree = root_0;
/* 1304 */       RewriteRuleTokenStream stream_i = new RewriteRuleTokenStream(this.adaptor, "token i", i);
/* 1305 */       RewriteRuleSubtreeStream stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.tree : null);
/*      */ 
/* 1307 */       root_0 = (CommonTree)this.adaptor.nil();
/*      */ 
/* 1309 */       if (indent != null)
/*      */       {
/* 1312 */         CommonTree root_1 = (CommonTree)this.adaptor.nil();
/* 1313 */         root_1 = (CommonTree)this.adaptor.becomeRoot((CommonTree)this.adaptor.create(47, "INDENTED_EXPR"), root_1);
/*      */ 
/* 1317 */         this.adaptor.addChild(root_1, stream_i.nextNode());
/*      */ 
/* 1321 */         CommonTree root_2 = (CommonTree)this.adaptor.nil();
/* 1322 */         root_2 = (CommonTree)this.adaptor.becomeRoot((CommonTree)this.adaptor.create(54, x), root_2);
/*      */ 
/* 1326 */         this.adaptor.addChild(root_2, stream_ID.nextNode());
/*      */ 
/* 1331 */         if (stream_template.hasNext()) {
/* 1332 */           this.adaptor.addChild(root_2, stream_template.nextTree());
/*      */         }
/*      */ 
/* 1335 */         stream_template.reset();
/*      */ 
/* 1337 */         this.adaptor.addChild(root_1, root_2);
/*      */ 
/* 1340 */         this.adaptor.addChild(root_0, root_1);
/*      */       }
/*      */       else
/*      */       {
/* 1349 */         CommonTree root_1 = (CommonTree)this.adaptor.nil();
/* 1350 */         root_1 = (CommonTree)this.adaptor.becomeRoot((CommonTree)this.adaptor.create(54, x), root_1);
/*      */ 
/* 1354 */         this.adaptor.addChild(root_1, stream_ID.nextNode());
/*      */ 
/* 1359 */         if (stream_template.hasNext()) {
/* 1360 */           this.adaptor.addChild(root_1, stream_template.nextTree());
/*      */         }
/*      */ 
/* 1363 */         stream_template.reset();
/*      */ 
/* 1365 */         this.adaptor.addChild(root_0, root_1);
/*      */       }
/*      */ 
/* 1371 */       retval.tree = root_0;
/*      */ 
/* 1375 */       retval.stop = this.input.LT(-1);
/*      */ 
/* 1378 */       retval.tree = ((CommonTree)this.adaptor.rulePostProcessing(root_0));
/* 1379 */       this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
/*      */     }
/*      */     catch (RecognitionException re)
/*      */     {
/* 1383 */       re = 
/* 1387 */         re;
/*      */ 
/* 1383 */       throw re;
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/* 1388 */     return retval;
/*      */   }
/*      */ 
/*      */   public final subtemplate_return subtemplate()
/*      */     throws RecognitionException
/*      */   {
/* 1402 */     subtemplate_return retval = new subtemplate_return();
/* 1403 */     retval.start = this.input.LT(1);
/*      */ 
/* 1406 */     CommonTree root_0 = null;
/*      */ 
/* 1408 */     CommonToken lc = null;
/* 1409 */     CommonToken char_literal31 = null;
/* 1410 */     CommonToken char_literal32 = null;
/* 1411 */     CommonToken INDENT34 = null;
/* 1412 */     CommonToken char_literal35 = null;
/* 1413 */     CommonToken ids = null;
/* 1414 */     List list_ids = null;
/* 1415 */     template_return template33 = null;
/*      */ 
/* 1418 */     CommonTree lc_tree = null;
/* 1419 */     CommonTree char_literal31_tree = null;
/* 1420 */     CommonTree char_literal32_tree = null;
/* 1421 */     CommonTree INDENT34_tree = null;
/* 1422 */     CommonTree char_literal35_tree = null;
/* 1423 */     CommonTree ids_tree = null;
/* 1424 */     RewriteRuleTokenStream stream_LCURLY = new RewriteRuleTokenStream(this.adaptor, "token LCURLY");
/* 1425 */     RewriteRuleTokenStream stream_PIPE = new RewriteRuleTokenStream(this.adaptor, "token PIPE");
/* 1426 */     RewriteRuleTokenStream stream_ID = new RewriteRuleTokenStream(this.adaptor, "token ID");
/* 1427 */     RewriteRuleTokenStream stream_COMMA = new RewriteRuleTokenStream(this.adaptor, "token COMMA");
/* 1428 */     RewriteRuleTokenStream stream_INDENT = new RewriteRuleTokenStream(this.adaptor, "token INDENT");
/* 1429 */     RewriteRuleTokenStream stream_RCURLY = new RewriteRuleTokenStream(this.adaptor, "token RCURLY");
/* 1430 */     RewriteRuleSubtreeStream stream_template = new RewriteRuleSubtreeStream(this.adaptor, "rule template");
/*      */     try
/*      */     {
/* 1435 */       lc = (CommonToken)match(this.input, 20, FOLLOW_LCURLY_in_subtemplate420);
/* 1436 */       stream_LCURLY.add(lc);
/*      */ 
/* 1440 */       int alt11 = 2;
/* 1441 */       int LA11_0 = this.input.LA(1);
/*      */ 
/* 1443 */       if (LA11_0 == 25) {
/* 1444 */         alt11 = 1;
/*      */       }
/* 1446 */       switch (alt11)
/*      */       {
/*      */       case 1:
/* 1450 */         ids = (CommonToken)match(this.input, 25, FOLLOW_ID_in_subtemplate426);
/* 1451 */         stream_ID.add(ids);
/*      */ 
/* 1453 */         if (list_ids == null) list_ids = new ArrayList();
/* 1454 */         list_ids.add(ids);
/*      */         while (true)
/*      */         {
/* 1460 */           int alt10 = 2;
/* 1461 */           int LA10_0 = this.input.LA(1);
/*      */ 
/* 1463 */           if (LA10_0 == 18) {
/* 1464 */             alt10 = 1;
/*      */           }
/*      */ 
/* 1468 */           switch (alt10)
/*      */           {
/*      */           case 1:
/* 1472 */             char_literal31 = (CommonToken)match(this.input, 18, FOLLOW_COMMA_in_subtemplate430);
/* 1473 */             stream_COMMA.add(char_literal31);
/*      */ 
/* 1476 */             ids = (CommonToken)match(this.input, 25, FOLLOW_ID_in_subtemplate435);
/* 1477 */             stream_ID.add(ids);
/*      */ 
/* 1479 */             if (list_ids == null) list_ids = new ArrayList();
/* 1480 */             list_ids.add(ids);
/*      */ 
/* 1484 */             break;
/*      */           default:
/* 1487 */             break label419;
/*      */           }
/*      */ 
/*      */         }
/*      */ 
/* 1492 */         label419: char_literal32 = (CommonToken)match(this.input, 28, FOLLOW_PIPE_in_subtemplate440);
/* 1493 */         stream_PIPE.add(char_literal32);
/*      */       }
/*      */ 
/* 1502 */       pushFollow(FOLLOW_template_in_subtemplate445);
/* 1503 */       template33 = template();
/*      */ 
/* 1505 */       this.state._fsp -= 1;
/*      */ 
/* 1507 */       stream_template.add(template33.getTree());
/*      */ 
/* 1510 */       int alt12 = 2;
/* 1511 */       int LA12_0 = this.input.LA(1);
/*      */ 
/* 1513 */       if (LA12_0 == 31) {
/* 1514 */         alt12 = 1;
/*      */       }
/* 1516 */       switch (alt12)
/*      */       {
/*      */       case 1:
/* 1520 */         INDENT34 = (CommonToken)match(this.input, 31, FOLLOW_INDENT_in_subtemplate447);
/* 1521 */         stream_INDENT.add(INDENT34);
/*      */       }
/*      */ 
/* 1530 */       char_literal35 = (CommonToken)match(this.input, 21, FOLLOW_RCURLY_in_subtemplate450);
/* 1531 */       stream_RCURLY.add(char_literal35);
/*      */ 
/* 1541 */       retval.tree = root_0;
/* 1542 */       RewriteRuleTokenStream stream_ids = new RewriteRuleTokenStream(this.adaptor, "token ids", list_ids);
/* 1543 */       RewriteRuleSubtreeStream stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.tree : null);
/*      */ 
/* 1545 */       root_0 = (CommonTree)this.adaptor.nil();
/*      */ 
/* 1550 */       CommonTree root_1 = (CommonTree)this.adaptor.nil();
/* 1551 */       root_1 = (CommonTree)this.adaptor.becomeRoot((CommonTree)this.adaptor.create(55, lc, "SUBTEMPLATE"), root_1);
/*      */ 
/* 1556 */       while (stream_ids.hasNext())
/*      */       {
/* 1559 */         CommonTree root_2 = (CommonTree)this.adaptor.nil();
/* 1560 */         root_2 = (CommonTree)this.adaptor.becomeRoot((CommonTree)this.adaptor.create(38, "ARGS"), root_2);
/*      */ 
/* 1564 */         this.adaptor.addChild(root_2, stream_ids.nextNode());
/*      */ 
/* 1566 */         this.adaptor.addChild(root_1, root_2);
/*      */       }
/*      */ 
/* 1570 */       stream_ids.reset();
/*      */ 
/* 1573 */       if (stream_template.hasNext()) {
/* 1574 */         this.adaptor.addChild(root_1, stream_template.nextTree());
/*      */       }
/*      */ 
/* 1577 */       stream_template.reset();
/*      */ 
/* 1579 */       this.adaptor.addChild(root_0, root_1);
/*      */ 
/* 1585 */       retval.tree = root_0;
/*      */ 
/* 1589 */       retval.stop = this.input.LT(-1);
/*      */ 
/* 1592 */       retval.tree = ((CommonTree)this.adaptor.rulePostProcessing(root_0));
/* 1593 */       this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
/*      */     }
/*      */     catch (RecognitionException re)
/*      */     {
/* 1597 */       re = 
/* 1601 */         re;
/*      */ 
/* 1597 */       throw re;
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/* 1602 */     return retval;
/*      */   }
/*      */ 
/*      */   public final ifstat_return ifstat()
/*      */     throws RecognitionException
/*      */   {
/* 1616 */     ifstat_return retval = new ifstat_return();
/* 1617 */     retval.start = this.input.LT(1);
/*      */ 
/* 1620 */     CommonTree root_0 = null;
/*      */ 
/* 1622 */     CommonToken i = null;
/* 1623 */     CommonToken endif = null;
/* 1624 */     CommonToken LDELIM36 = null;
/* 1625 */     CommonToken string_literal37 = null;
/* 1626 */     CommonToken char_literal38 = null;
/* 1627 */     CommonToken char_literal39 = null;
/* 1628 */     CommonToken RDELIM40 = null;
/* 1629 */     CommonToken INDENT41 = null;
/* 1630 */     CommonToken LDELIM42 = null;
/* 1631 */     CommonToken string_literal43 = null;
/* 1632 */     CommonToken char_literal44 = null;
/* 1633 */     CommonToken char_literal45 = null;
/* 1634 */     CommonToken RDELIM46 = null;
/* 1635 */     CommonToken INDENT47 = null;
/* 1636 */     CommonToken LDELIM48 = null;
/* 1637 */     CommonToken string_literal49 = null;
/* 1638 */     CommonToken RDELIM50 = null;
/* 1639 */     CommonToken INDENT51 = null;
/* 1640 */     CommonToken string_literal52 = null;
/* 1641 */     CommonToken RDELIM53 = null;
/* 1642 */     CommonToken NEWLINE54 = null;
/* 1643 */     List list_c2 = null;
/* 1644 */     List list_t2 = null;
/* 1645 */     conditional_return c1 = null;
/*      */ 
/* 1647 */     template_return t1 = null;
/*      */ 
/* 1649 */     template_return t3 = null;
/*      */ 
/* 1651 */     RuleReturnScope c2 = null;
/* 1652 */     RuleReturnScope t2 = null;
/* 1653 */     CommonTree i_tree = null;
/* 1654 */     CommonTree endif_tree = null;
/* 1655 */     CommonTree LDELIM36_tree = null;
/* 1656 */     CommonTree string_literal37_tree = null;
/* 1657 */     CommonTree char_literal38_tree = null;
/* 1658 */     CommonTree char_literal39_tree = null;
/* 1659 */     CommonTree RDELIM40_tree = null;
/* 1660 */     CommonTree INDENT41_tree = null;
/* 1661 */     CommonTree LDELIM42_tree = null;
/* 1662 */     CommonTree string_literal43_tree = null;
/* 1663 */     CommonTree char_literal44_tree = null;
/* 1664 */     CommonTree char_literal45_tree = null;
/* 1665 */     CommonTree RDELIM46_tree = null;
/* 1666 */     CommonTree INDENT47_tree = null;
/* 1667 */     CommonTree LDELIM48_tree = null;
/* 1668 */     CommonTree string_literal49_tree = null;
/* 1669 */     CommonTree RDELIM50_tree = null;
/* 1670 */     CommonTree INDENT51_tree = null;
/* 1671 */     CommonTree string_literal52_tree = null;
/* 1672 */     CommonTree RDELIM53_tree = null;
/* 1673 */     CommonTree NEWLINE54_tree = null;
/* 1674 */     RewriteRuleTokenStream stream_ENDIF = new RewriteRuleTokenStream(this.adaptor, "token ENDIF");
/* 1675 */     RewriteRuleTokenStream stream_RDELIM = new RewriteRuleTokenStream(this.adaptor, "token RDELIM");
/* 1676 */     RewriteRuleTokenStream stream_RPAREN = new RewriteRuleTokenStream(this.adaptor, "token RPAREN");
/* 1677 */     RewriteRuleTokenStream stream_NEWLINE = new RewriteRuleTokenStream(this.adaptor, "token NEWLINE");
/* 1678 */     RewriteRuleTokenStream stream_LDELIM = new RewriteRuleTokenStream(this.adaptor, "token LDELIM");
/* 1679 */     RewriteRuleTokenStream stream_INDENT = new RewriteRuleTokenStream(this.adaptor, "token INDENT");
/* 1680 */     RewriteRuleTokenStream stream_LPAREN = new RewriteRuleTokenStream(this.adaptor, "token LPAREN");
/* 1681 */     RewriteRuleTokenStream stream_IF = new RewriteRuleTokenStream(this.adaptor, "token IF");
/* 1682 */     RewriteRuleTokenStream stream_ELSE = new RewriteRuleTokenStream(this.adaptor, "token ELSE");
/* 1683 */     RewriteRuleTokenStream stream_ELSEIF = new RewriteRuleTokenStream(this.adaptor, "token ELSEIF");
/* 1684 */     RewriteRuleSubtreeStream stream_template = new RewriteRuleSubtreeStream(this.adaptor, "rule template");
/* 1685 */     RewriteRuleSubtreeStream stream_conditional = new RewriteRuleSubtreeStream(this.adaptor, "rule conditional");
/* 1686 */     Token indent = null;
/*      */     try
/*      */     {
/* 1692 */       int alt13 = 2;
/* 1693 */       int LA13_0 = this.input.LA(1);
/*      */ 
/* 1695 */       if (LA13_0 == 31) {
/* 1696 */         alt13 = 1;
/*      */       }
/* 1698 */       switch (alt13)
/*      */       {
/*      */       case 1:
/* 1702 */         i = (CommonToken)match(this.input, 31, FOLLOW_INDENT_in_ifstat491);
/* 1703 */         stream_INDENT.add(i);
/*      */       }
/*      */ 
/* 1712 */       LDELIM36 = (CommonToken)match(this.input, 23, FOLLOW_LDELIM_in_ifstat494);
/* 1713 */       stream_LDELIM.add(LDELIM36);
/*      */ 
/* 1716 */       string_literal37 = (CommonToken)match(this.input, 4, FOLLOW_IF_in_ifstat496);
/* 1717 */       stream_IF.add(string_literal37);
/*      */ 
/* 1720 */       char_literal38 = (CommonToken)match(this.input, 14, FOLLOW_LPAREN_in_ifstat498);
/* 1721 */       stream_LPAREN.add(char_literal38);
/*      */ 
/* 1724 */       pushFollow(FOLLOW_conditional_in_ifstat502);
/* 1725 */       c1 = conditional();
/*      */ 
/* 1727 */       this.state._fsp -= 1;
/*      */ 
/* 1729 */       stream_conditional.add(c1.getTree());
/*      */ 
/* 1731 */       char_literal39 = (CommonToken)match(this.input, 15, FOLLOW_RPAREN_in_ifstat504);
/* 1732 */       stream_RPAREN.add(char_literal39);
/*      */ 
/* 1735 */       RDELIM40 = (CommonToken)match(this.input, 24, FOLLOW_RDELIM_in_ifstat506);
/* 1736 */       stream_RDELIM.add(RDELIM40);
/*      */ 
/* 1739 */       if (this.input.LA(1) != 32) indent = i;
/*      */ 
/* 1741 */       pushFollow(FOLLOW_template_in_ifstat515);
/* 1742 */       t1 = template();
/*      */ 
/* 1744 */       this.state._fsp -= 1;
/*      */ 
/* 1746 */       stream_template.add(t1.getTree());
/*      */       while (true)
/*      */       {
/* 1751 */         int alt15 = 2;
/* 1752 */         int LA15_0 = this.input.LA(1);
/*      */ 
/* 1754 */         if (LA15_0 == 31) {
/* 1755 */           int LA15_1 = this.input.LA(2);
/*      */ 
/* 1757 */           if (LA15_1 == 23) {
/* 1758 */             int LA15_2 = this.input.LA(3);
/*      */ 
/* 1760 */             if (LA15_2 == 6) {
/* 1761 */               alt15 = 1;
/*      */             }
/*      */ 
/*      */           }
/*      */ 
/*      */         }
/* 1769 */         else if (LA15_0 == 23) {
/* 1770 */           int LA15_2 = this.input.LA(2);
/*      */ 
/* 1772 */           if (LA15_2 == 6) {
/* 1773 */             alt15 = 1;
/*      */           }
/*      */ 
/*      */         }
/*      */ 
/* 1780 */         switch (alt15)
/*      */         {
/*      */         case 1:
/* 1785 */           int alt14 = 2;
/* 1786 */           int LA14_0 = this.input.LA(1);
/*      */ 
/* 1788 */           if (LA14_0 == 31) {
/* 1789 */             alt14 = 1;
/*      */           }
/* 1791 */           switch (alt14)
/*      */           {
/*      */           case 1:
/* 1795 */             INDENT41 = (CommonToken)match(this.input, 31, FOLLOW_INDENT_in_ifstat522);
/* 1796 */             stream_INDENT.add(INDENT41);
/*      */           }
/*      */ 
/* 1805 */           LDELIM42 = (CommonToken)match(this.input, 23, FOLLOW_LDELIM_in_ifstat525);
/* 1806 */           stream_LDELIM.add(LDELIM42);
/*      */ 
/* 1809 */           string_literal43 = (CommonToken)match(this.input, 6, FOLLOW_ELSEIF_in_ifstat527);
/* 1810 */           stream_ELSEIF.add(string_literal43);
/*      */ 
/* 1813 */           char_literal44 = (CommonToken)match(this.input, 14, FOLLOW_LPAREN_in_ifstat529);
/* 1814 */           stream_LPAREN.add(char_literal44);
/*      */ 
/* 1817 */           pushFollow(FOLLOW_conditional_in_ifstat533);
/* 1818 */           c2 = conditional();
/*      */ 
/* 1820 */           this.state._fsp -= 1;
/*      */ 
/* 1822 */           stream_conditional.add(c2.getTree());
/* 1823 */           if (list_c2 == null) list_c2 = new ArrayList();
/* 1824 */           list_c2.add(c2.getTree());
/*      */ 
/* 1827 */           char_literal45 = (CommonToken)match(this.input, 15, FOLLOW_RPAREN_in_ifstat535);
/* 1828 */           stream_RPAREN.add(char_literal45);
/*      */ 
/* 1831 */           RDELIM46 = (CommonToken)match(this.input, 24, FOLLOW_RDELIM_in_ifstat537);
/* 1832 */           stream_RDELIM.add(RDELIM46);
/*      */ 
/* 1835 */           pushFollow(FOLLOW_template_in_ifstat541);
/* 1836 */           t2 = template();
/*      */ 
/* 1838 */           this.state._fsp -= 1;
/*      */ 
/* 1840 */           stream_template.add(t2.getTree());
/* 1841 */           if (list_t2 == null) list_t2 = new ArrayList();
/* 1842 */           list_t2.add(t2.getTree());
/*      */ 
/* 1846 */           break;
/*      */         default:
/* 1849 */           break label1081;
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/* 1855 */       label1081: int alt17 = 2;
/* 1856 */       int LA17_0 = this.input.LA(1);
/*      */ 
/* 1858 */       if (LA17_0 == 31) {
/* 1859 */         int LA17_1 = this.input.LA(2);
/*      */ 
/* 1861 */         if (LA17_1 == 23) {
/* 1862 */           int LA17_2 = this.input.LA(3);
/*      */ 
/* 1864 */           if (LA17_2 == 5) {
/* 1865 */             alt17 = 1;
/*      */           }
/*      */         }
/*      */       }
/* 1869 */       else if (LA17_0 == 23) {
/* 1870 */         int LA17_2 = this.input.LA(2);
/*      */ 
/* 1872 */         if (LA17_2 == 5) {
/* 1873 */           alt17 = 1;
/*      */         }
/*      */       }
/* 1876 */       switch (alt17)
/*      */       {
/*      */       case 1:
/* 1881 */         int alt16 = 2;
/* 1882 */         int LA16_0 = this.input.LA(1);
/*      */ 
/* 1884 */         if (LA16_0 == 31) {
/* 1885 */           alt16 = 1;
/*      */         }
/* 1887 */         switch (alt16)
/*      */         {
/*      */         case 1:
/* 1891 */           INDENT47 = (CommonToken)match(this.input, 31, FOLLOW_INDENT_in_ifstat551);
/* 1892 */           stream_INDENT.add(INDENT47);
/*      */         }
/*      */ 
/* 1901 */         LDELIM48 = (CommonToken)match(this.input, 23, FOLLOW_LDELIM_in_ifstat554);
/* 1902 */         stream_LDELIM.add(LDELIM48);
/*      */ 
/* 1905 */         string_literal49 = (CommonToken)match(this.input, 5, FOLLOW_ELSE_in_ifstat556);
/* 1906 */         stream_ELSE.add(string_literal49);
/*      */ 
/* 1909 */         RDELIM50 = (CommonToken)match(this.input, 24, FOLLOW_RDELIM_in_ifstat558);
/* 1910 */         stream_RDELIM.add(RDELIM50);
/*      */ 
/* 1913 */         pushFollow(FOLLOW_template_in_ifstat562);
/* 1914 */         t3 = template();
/*      */ 
/* 1916 */         this.state._fsp -= 1;
/*      */ 
/* 1918 */         stream_template.add(t3.getTree());
/*      */       }
/*      */ 
/* 1927 */       int alt18 = 2;
/* 1928 */       int LA18_0 = this.input.LA(1);
/*      */ 
/* 1930 */       if (LA18_0 == 31) {
/* 1931 */         alt18 = 1;
/*      */       }
/* 1933 */       switch (alt18)
/*      */       {
/*      */       case 1:
/* 1937 */         INDENT51 = (CommonToken)match(this.input, 31, FOLLOW_INDENT_in_ifstat570);
/* 1938 */         stream_INDENT.add(INDENT51);
/*      */       }
/*      */ 
/* 1947 */       endif = (CommonToken)match(this.input, 23, FOLLOW_LDELIM_in_ifstat576);
/* 1948 */       stream_LDELIM.add(endif);
/*      */ 
/* 1951 */       string_literal52 = (CommonToken)match(this.input, 7, FOLLOW_ENDIF_in_ifstat578);
/* 1952 */       stream_ENDIF.add(string_literal52);
/*      */ 
/* 1955 */       RDELIM53 = (CommonToken)match(this.input, 24, FOLLOW_RDELIM_in_ifstat582);
/* 1956 */       stream_RDELIM.add(RDELIM53);
/*      */ 
/* 1960 */       int alt19 = 2;
/* 1961 */       int LA19_0 = this.input.LA(1);
/*      */ 
/* 1963 */       if (LA19_0 == 32) {
/* 1964 */         int LA19_1 = this.input.LA(2);
/*      */ 
/* 1966 */         if (((CommonToken)retval.start).getLine() != this.input.LT(1).getLine()) {
/* 1967 */           alt19 = 1;
/*      */         }
/*      */       }
/* 1970 */       switch (alt19)
/*      */       {
/*      */       case 1:
/* 1974 */         if (((CommonToken)retval.start).getLine() == this.input.LT(1).getLine()) {
/* 1975 */           throw new FailedPredicateException(this.input, "ifstat", "$ifstat.start.getLine()!=input.LT(1).getLine()");
/*      */         }
/*      */ 
/* 1978 */         NEWLINE54 = (CommonToken)match(this.input, 32, FOLLOW_NEWLINE_in_ifstat593);
/* 1979 */         stream_NEWLINE.add(NEWLINE54);
/*      */       }
/*      */ 
/* 1995 */       retval.tree = root_0;
/* 1996 */       RewriteRuleTokenStream stream_i = new RewriteRuleTokenStream(this.adaptor, "token i", i);
/* 1997 */       RewriteRuleSubtreeStream stream_t3 = new RewriteRuleSubtreeStream(this.adaptor, "rule t3", t3 != null ? t3.tree : null);
/* 1998 */       RewriteRuleSubtreeStream stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.tree : null);
/* 1999 */       RewriteRuleSubtreeStream stream_t1 = new RewriteRuleSubtreeStream(this.adaptor, "rule t1", t1 != null ? t1.tree : null);
/* 2000 */       RewriteRuleSubtreeStream stream_c1 = new RewriteRuleSubtreeStream(this.adaptor, "rule c1", c1 != null ? c1.tree : null);
/* 2001 */       RewriteRuleSubtreeStream stream_t2 = new RewriteRuleSubtreeStream(this.adaptor, "token t2", list_t2);
/* 2002 */       RewriteRuleSubtreeStream stream_c2 = new RewriteRuleSubtreeStream(this.adaptor, "token c2", list_c2);
/* 2003 */       root_0 = (CommonTree)this.adaptor.nil();
/*      */ 
/* 2005 */       if (indent != null)
/*      */       {
/* 2008 */         CommonTree root_1 = (CommonTree)this.adaptor.nil();
/* 2009 */         root_1 = (CommonTree)this.adaptor.becomeRoot((CommonTree)this.adaptor.create(47, "INDENTED_EXPR"), root_1);
/*      */ 
/* 2013 */         this.adaptor.addChild(root_1, stream_i.nextNode());
/*      */ 
/* 2017 */         CommonTree root_2 = (CommonTree)this.adaptor.nil();
/* 2018 */         root_2 = (CommonTree)this.adaptor.becomeRoot(stream_IF.nextNode(), root_2);
/*      */ 
/* 2022 */         this.adaptor.addChild(root_2, stream_c1.nextTree());
/*      */ 
/* 2025 */         if (stream_t1.hasNext()) {
/* 2026 */           this.adaptor.addChild(root_2, stream_t1.nextTree());
/*      */         }
/*      */ 
/* 2029 */         stream_t1.reset();
/*      */ 
/* 2032 */         while ((stream_ELSEIF.hasNext()) || (stream_c2.hasNext()) || (stream_t2.hasNext()))
/*      */         {
/* 2035 */           CommonTree root_3 = (CommonTree)this.adaptor.nil();
/* 2036 */           root_3 = (CommonTree)this.adaptor.becomeRoot(stream_ELSEIF.nextNode(), root_3);
/*      */ 
/* 2040 */           this.adaptor.addChild(root_3, stream_c2.nextTree());
/*      */ 
/* 2042 */           this.adaptor.addChild(root_3, stream_t2.nextTree());
/*      */ 
/* 2044 */           this.adaptor.addChild(root_2, root_3);
/*      */         }
/*      */ 
/* 2048 */         stream_ELSEIF.reset();
/* 2049 */         stream_c2.reset();
/* 2050 */         stream_t2.reset();
/*      */ 
/* 2053 */         if ((stream_t3.hasNext()) || (stream_ELSE.hasNext()))
/*      */         {
/* 2056 */           CommonTree root_3 = (CommonTree)this.adaptor.nil();
/* 2057 */           root_3 = (CommonTree)this.adaptor.becomeRoot(stream_ELSE.nextNode(), root_3);
/*      */ 
/* 2062 */           if (stream_t3.hasNext()) {
/* 2063 */             this.adaptor.addChild(root_3, stream_t3.nextTree());
/*      */           }
/*      */ 
/* 2066 */           stream_t3.reset();
/*      */ 
/* 2068 */           this.adaptor.addChild(root_2, root_3);
/*      */         }
/*      */ 
/* 2072 */         stream_t3.reset();
/* 2073 */         stream_ELSE.reset();
/*      */ 
/* 2075 */         this.adaptor.addChild(root_1, root_2);
/*      */ 
/* 2078 */         this.adaptor.addChild(root_0, root_1);
/*      */       }
/*      */       else
/*      */       {
/* 2087 */         CommonTree root_1 = (CommonTree)this.adaptor.nil();
/* 2088 */         root_1 = (CommonTree)this.adaptor.becomeRoot(stream_IF.nextNode(), root_1);
/*      */ 
/* 2092 */         this.adaptor.addChild(root_1, stream_c1.nextTree());
/*      */ 
/* 2095 */         if (stream_t1.hasNext()) {
/* 2096 */           this.adaptor.addChild(root_1, stream_t1.nextTree());
/*      */         }
/*      */ 
/* 2099 */         stream_t1.reset();
/*      */ 
/* 2102 */         while ((stream_t2.hasNext()) || (stream_c2.hasNext()) || (stream_ELSEIF.hasNext()))
/*      */         {
/* 2105 */           CommonTree root_2 = (CommonTree)this.adaptor.nil();
/* 2106 */           root_2 = (CommonTree)this.adaptor.becomeRoot(stream_ELSEIF.nextNode(), root_2);
/*      */ 
/* 2110 */           this.adaptor.addChild(root_2, stream_c2.nextTree());
/*      */ 
/* 2112 */           this.adaptor.addChild(root_2, stream_t2.nextTree());
/*      */ 
/* 2114 */           this.adaptor.addChild(root_1, root_2);
/*      */         }
/*      */ 
/* 2118 */         stream_t2.reset();
/* 2119 */         stream_c2.reset();
/* 2120 */         stream_ELSEIF.reset();
/*      */ 
/* 2123 */         if ((stream_t3.hasNext()) || (stream_ELSE.hasNext()))
/*      */         {
/* 2126 */           CommonTree root_2 = (CommonTree)this.adaptor.nil();
/* 2127 */           root_2 = (CommonTree)this.adaptor.becomeRoot(stream_ELSE.nextNode(), root_2);
/*      */ 
/* 2132 */           if (stream_t3.hasNext()) {
/* 2133 */             this.adaptor.addChild(root_2, stream_t3.nextTree());
/*      */           }
/*      */ 
/* 2136 */           stream_t3.reset();
/*      */ 
/* 2138 */           this.adaptor.addChild(root_1, root_2);
/*      */         }
/*      */ 
/* 2142 */         stream_t3.reset();
/* 2143 */         stream_ELSE.reset();
/*      */ 
/* 2145 */         this.adaptor.addChild(root_0, root_1);
/*      */       }
/*      */ 
/* 2151 */       retval.tree = root_0;
/*      */ 
/* 2155 */       retval.stop = this.input.LT(-1);
/*      */ 
/* 2158 */       retval.tree = ((CommonTree)this.adaptor.rulePostProcessing(root_0));
/* 2159 */       this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
/*      */     }
/*      */     catch (RecognitionException re)
/*      */     {
/* 2163 */       re = 
/* 2167 */         re;
/*      */ 
/* 2163 */       throw re;
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/* 2168 */     return retval;
/*      */   }
/*      */ 
/*      */   public final conditional_return conditional()
/*      */     throws RecognitionException
/*      */   {
/* 2188 */     this.conditional_stack.push(new conditional_scope());
/* 2189 */     conditional_return retval = new conditional_return();
/* 2190 */     retval.start = this.input.LT(1);
/*      */ 
/* 2193 */     CommonTree root_0 = null;
/*      */ 
/* 2195 */     CommonToken string_literal56 = null;
/* 2196 */     andConditional_return andConditional55 = null;
/*      */ 
/* 2198 */     andConditional_return andConditional57 = null;
/*      */ 
/* 2201 */     CommonTree string_literal56_tree = null;
/*      */     try
/*      */     {
/* 2207 */       root_0 = (CommonTree)this.adaptor.nil();
/*      */ 
/* 2210 */       pushFollow(FOLLOW_andConditional_in_conditional713);
/* 2211 */       andConditional55 = andConditional();
/*      */ 
/* 2213 */       this.state._fsp -= 1;
/*      */ 
/* 2215 */       this.adaptor.addChild(root_0, andConditional55.getTree());
/*      */       while (true)
/*      */       {
/* 2220 */         int alt20 = 2;
/* 2221 */         int LA20_0 = this.input.LA(1);
/*      */ 
/* 2223 */         if (LA20_0 == 29) {
/* 2224 */           alt20 = 1;
/*      */         }
/*      */ 
/* 2228 */         switch (alt20)
/*      */         {
/*      */         case 1:
/* 2232 */           string_literal56 = (CommonToken)match(this.input, 29, FOLLOW_OR_in_conditional717);
/* 2233 */           string_literal56_tree = (CommonTree)this.adaptor.create(string_literal56);
/*      */ 
/* 2236 */           root_0 = (CommonTree)this.adaptor.becomeRoot(string_literal56_tree, root_0);
/*      */ 
/* 2239 */           pushFollow(FOLLOW_andConditional_in_conditional720);
/* 2240 */           andConditional57 = andConditional();
/*      */ 
/* 2242 */           this.state._fsp -= 1;
/*      */ 
/* 2244 */           this.adaptor.addChild(root_0, andConditional57.getTree());
/*      */ 
/* 2247 */           break;
/*      */         default:
/* 2250 */           break label246;
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/* 2257 */       label246: retval.stop = this.input.LT(-1);
/*      */ 
/* 2260 */       retval.tree = ((CommonTree)this.adaptor.rulePostProcessing(root_0));
/* 2261 */       this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
/*      */     }
/*      */     catch (RecognitionException re)
/*      */     {
/* 2265 */       throw re;
/*      */     }
/*      */     finally
/*      */     {
/* 2269 */       this.conditional_stack.pop();
/*      */     }
/* 2271 */     return retval;
/*      */   }
/*      */ 
/*      */   public final andConditional_return andConditional()
/*      */     throws RecognitionException
/*      */   {
/* 2285 */     andConditional_return retval = new andConditional_return();
/* 2286 */     retval.start = this.input.LT(1);
/*      */ 
/* 2289 */     CommonTree root_0 = null;
/*      */ 
/* 2291 */     CommonToken string_literal59 = null;
/* 2292 */     notConditional_return notConditional58 = null;
/*      */ 
/* 2294 */     notConditional_return notConditional60 = null;
/*      */ 
/* 2297 */     CommonTree string_literal59_tree = null;
/*      */     try
/*      */     {
/* 2303 */       root_0 = (CommonTree)this.adaptor.nil();
/*      */ 
/* 2306 */       pushFollow(FOLLOW_notConditional_in_andConditional733);
/* 2307 */       notConditional58 = notConditional();
/*      */ 
/* 2309 */       this.state._fsp -= 1;
/*      */ 
/* 2311 */       this.adaptor.addChild(root_0, notConditional58.getTree());
/*      */       while (true)
/*      */       {
/* 2316 */         int alt21 = 2;
/* 2317 */         int LA21_0 = this.input.LA(1);
/*      */ 
/* 2319 */         if (LA21_0 == 30) {
/* 2320 */           alt21 = 1;
/*      */         }
/*      */ 
/* 2324 */         switch (alt21)
/*      */         {
/*      */         case 1:
/* 2328 */           string_literal59 = (CommonToken)match(this.input, 30, FOLLOW_AND_in_andConditional737);
/* 2329 */           string_literal59_tree = (CommonTree)this.adaptor.create(string_literal59);
/*      */ 
/* 2332 */           root_0 = (CommonTree)this.adaptor.becomeRoot(string_literal59_tree, root_0);
/*      */ 
/* 2335 */           pushFollow(FOLLOW_notConditional_in_andConditional740);
/* 2336 */           notConditional60 = notConditional();
/*      */ 
/* 2338 */           this.state._fsp -= 1;
/*      */ 
/* 2340 */           this.adaptor.addChild(root_0, notConditional60.getTree());
/*      */ 
/* 2343 */           break;
/*      */         default:
/* 2346 */           break label234;
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/* 2353 */       label234: retval.stop = this.input.LT(-1);
/*      */ 
/* 2356 */       retval.tree = ((CommonTree)this.adaptor.rulePostProcessing(root_0));
/* 2357 */       this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
/*      */     }
/*      */     catch (RecognitionException re)
/*      */     {
/* 2361 */       re = 
/* 2365 */         re;
/*      */ 
/* 2361 */       throw re;
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/* 2366 */     return retval;
/*      */   }
/*      */ 
/*      */   public final notConditional_return notConditional()
/*      */     throws RecognitionException
/*      */   {
/* 2380 */     notConditional_return retval = new notConditional_return();
/* 2381 */     retval.start = this.input.LT(1);
/*      */ 
/* 2384 */     CommonTree root_0 = null;
/*      */ 
/* 2386 */     CommonToken char_literal61 = null;
/* 2387 */     notConditional_return notConditional62 = null;
/*      */ 
/* 2389 */     memberExpr_return memberExpr63 = null;
/*      */ 
/* 2392 */     CommonTree char_literal61_tree = null;
/*      */     try
/*      */     {
/* 2396 */       int alt22 = 2;
/* 2397 */       int LA22_0 = this.input.LA(1);
/*      */ 
/* 2399 */       if (LA22_0 == 10) {
/* 2400 */         alt22 = 1;
/*      */       }
/* 2402 */       else if ((LA22_0 == 8) || (LA22_0 == 16) || (LA22_0 == 20) || ((LA22_0 >= 25) && (LA22_0 <= 26)) || (LA22_0 == 33) || ((LA22_0 >= 35) && (LA22_0 <= 36))) {
/* 2403 */         alt22 = 2;
/*      */       }
/* 2405 */       else if ((LA22_0 == 14) && ((this.conditional_stack.size() == 0) || (this.conditional_stack.size() > 0))) {
/* 2406 */         alt22 = 2;
/*      */       }
/*      */       else {
/* 2409 */         NoViableAltException nvae = new NoViableAltException("", 22, 0, this.input);
/*      */ 
/* 2412 */         throw nvae;
/*      */       }
/*      */ 
/* 2415 */       switch (alt22)
/*      */       {
/*      */       case 1:
/* 2419 */         root_0 = (CommonTree)this.adaptor.nil();
/*      */ 
/* 2422 */         char_literal61 = (CommonToken)match(this.input, 10, FOLLOW_BANG_in_notConditional753);
/* 2423 */         char_literal61_tree = (CommonTree)this.adaptor.create(char_literal61);
/*      */ 
/* 2426 */         root_0 = (CommonTree)this.adaptor.becomeRoot(char_literal61_tree, root_0);
/*      */ 
/* 2429 */         pushFollow(FOLLOW_notConditional_in_notConditional756);
/* 2430 */         notConditional62 = notConditional();
/*      */ 
/* 2432 */         this.state._fsp -= 1;
/*      */ 
/* 2434 */         this.adaptor.addChild(root_0, notConditional62.getTree());
/*      */ 
/* 2437 */         break;
/*      */       case 2:
/* 2441 */         root_0 = (CommonTree)this.adaptor.nil();
/*      */ 
/* 2444 */         pushFollow(FOLLOW_memberExpr_in_notConditional761);
/* 2445 */         memberExpr63 = memberExpr();
/*      */ 
/* 2447 */         this.state._fsp -= 1;
/*      */ 
/* 2449 */         this.adaptor.addChild(root_0, memberExpr63.getTree());
/*      */       }
/*      */ 
/* 2455 */       retval.stop = this.input.LT(-1);
/*      */ 
/* 2458 */       retval.tree = ((CommonTree)this.adaptor.rulePostProcessing(root_0));
/* 2459 */       this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
/*      */     }
/*      */     catch (RecognitionException re)
/*      */     {
/* 2463 */       re = 
/* 2467 */         re;
/*      */ 
/* 2463 */       throw re;
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/* 2468 */     return retval;
/*      */   }
/*      */ 
/*      */   public final notConditionalExpr_return notConditionalExpr()
/*      */     throws RecognitionException
/*      */   {
/* 2482 */     notConditionalExpr_return retval = new notConditionalExpr_return();
/* 2483 */     retval.start = this.input.LT(1);
/*      */ 
/* 2486 */     CommonTree root_0 = null;
/*      */ 
/* 2488 */     CommonToken p = null;
/* 2489 */     CommonToken prop = null;
/* 2490 */     CommonToken ID64 = null;
/* 2491 */     CommonToken char_literal65 = null;
/* 2492 */     CommonToken char_literal67 = null;
/* 2493 */     mapExpr_return mapExpr66 = null;
/*      */ 
/* 2496 */     CommonTree p_tree = null;
/* 2497 */     CommonTree prop_tree = null;
/* 2498 */     CommonTree ID64_tree = null;
/* 2499 */     CommonTree char_literal65_tree = null;
/* 2500 */     CommonTree char_literal67_tree = null;
/* 2501 */     RewriteRuleTokenStream stream_RPAREN = new RewriteRuleTokenStream(this.adaptor, "token RPAREN");
/* 2502 */     RewriteRuleTokenStream stream_DOT = new RewriteRuleTokenStream(this.adaptor, "token DOT");
/* 2503 */     RewriteRuleTokenStream stream_ID = new RewriteRuleTokenStream(this.adaptor, "token ID");
/* 2504 */     RewriteRuleTokenStream stream_LPAREN = new RewriteRuleTokenStream(this.adaptor, "token LPAREN");
/* 2505 */     RewriteRuleSubtreeStream stream_mapExpr = new RewriteRuleSubtreeStream(this.adaptor, "rule mapExpr");
/*      */     try
/*      */     {
/* 2513 */       ID64 = (CommonToken)match(this.input, 25, FOLLOW_ID_in_notConditionalExpr773);
/* 2514 */       stream_ID.add(ID64);
/*      */ 
/* 2524 */       retval.tree = root_0;
/* 2525 */       RewriteRuleSubtreeStream stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.tree : null);
/*      */ 
/* 2527 */       root_0 = (CommonTree)this.adaptor.nil();
/*      */ 
/* 2530 */       this.adaptor.addChild(root_0, stream_ID.nextNode());
/*      */ 
/* 2537 */       retval.tree = root_0;
/*      */       while (true)
/*      */       {
/* 2545 */         int alt23 = 3;
/* 2546 */         int LA23_0 = this.input.LA(1);
/*      */ 
/* 2548 */         if (LA23_0 == 19) {
/* 2549 */           int LA23_2 = this.input.LA(2);
/*      */ 
/* 2551 */           if (LA23_2 == 25) {
/* 2552 */             alt23 = 1;
/*      */           }
/* 2554 */           else if (LA23_2 == 14) {
/* 2555 */             alt23 = 2;
/*      */           }
/*      */ 
/*      */         }
/*      */ 
/* 2562 */         switch (alt23)
/*      */         {
/*      */         case 1:
/* 2566 */           p = (CommonToken)match(this.input, 19, FOLLOW_DOT_in_notConditionalExpr784);
/* 2567 */           stream_DOT.add(p);
/*      */ 
/* 2570 */           prop = (CommonToken)match(this.input, 25, FOLLOW_ID_in_notConditionalExpr788);
/* 2571 */           stream_ID.add(prop);
/*      */ 
/* 2581 */           retval.tree = root_0;
/* 2582 */           RewriteRuleTokenStream stream_prop = new RewriteRuleTokenStream(this.adaptor, "token prop", prop);
/* 2583 */           RewriteRuleSubtreeStream stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.tree : null);
/*      */ 
/* 2585 */           root_0 = (CommonTree)this.adaptor.nil();
/*      */ 
/* 2590 */           CommonTree root_1 = (CommonTree)this.adaptor.nil();
/* 2591 */           root_1 = (CommonTree)this.adaptor.becomeRoot((CommonTree)this.adaptor.create(52, p, "PROP"), root_1);
/*      */ 
/* 2595 */           this.adaptor.addChild(root_1, stream_retval.nextTree());
/*      */ 
/* 2597 */           this.adaptor.addChild(root_1, stream_prop.nextNode());
/*      */ 
/* 2599 */           this.adaptor.addChild(root_0, root_1);
/*      */ 
/* 2605 */           retval.tree = root_0;
/*      */ 
/* 2608 */           break;
/*      */         case 2:
/* 2612 */           p = (CommonToken)match(this.input, 19, FOLLOW_DOT_in_notConditionalExpr814);
/* 2613 */           stream_DOT.add(p);
/*      */ 
/* 2616 */           char_literal65 = (CommonToken)match(this.input, 14, FOLLOW_LPAREN_in_notConditionalExpr816);
/* 2617 */           stream_LPAREN.add(char_literal65);
/*      */ 
/* 2620 */           pushFollow(FOLLOW_mapExpr_in_notConditionalExpr818);
/* 2621 */           mapExpr66 = mapExpr();
/*      */ 
/* 2623 */           this.state._fsp -= 1;
/*      */ 
/* 2625 */           stream_mapExpr.add(mapExpr66.getTree());
/*      */ 
/* 2627 */           char_literal67 = (CommonToken)match(this.input, 15, FOLLOW_RPAREN_in_notConditionalExpr820);
/* 2628 */           stream_RPAREN.add(char_literal67);
/*      */ 
/* 2638 */           retval.tree = root_0;
/* 2639 */           RewriteRuleSubtreeStream stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.tree : null);
/*      */ 
/* 2641 */           root_0 = (CommonTree)this.adaptor.nil();
/*      */ 
/* 2646 */           CommonTree root_1 = (CommonTree)this.adaptor.nil();
/* 2647 */           root_1 = (CommonTree)this.adaptor.becomeRoot((CommonTree)this.adaptor.create(53, p, "PROP_IND"), root_1);
/*      */ 
/* 2651 */           this.adaptor.addChild(root_1, stream_retval.nextTree());
/*      */ 
/* 2653 */           this.adaptor.addChild(root_1, stream_mapExpr.nextTree());
/*      */ 
/* 2655 */           this.adaptor.addChild(root_0, root_1);
/*      */ 
/* 2661 */           retval.tree = root_0;
/*      */ 
/* 2664 */           break;
/*      */         default:
/* 2667 */           break label779;
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/* 2674 */       label779: retval.stop = this.input.LT(-1);
/*      */ 
/* 2677 */       retval.tree = ((CommonTree)this.adaptor.rulePostProcessing(root_0));
/* 2678 */       this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
/*      */     }
/*      */     catch (RecognitionException re)
/*      */     {
/* 2682 */       re = 
/* 2686 */         re;
/*      */ 
/* 2682 */       throw re;
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/* 2687 */     return retval;
/*      */   }
/*      */ 
/*      */   public final exprOptions_return exprOptions()
/*      */     throws RecognitionException
/*      */   {
/* 2701 */     exprOptions_return retval = new exprOptions_return();
/* 2702 */     retval.start = this.input.LT(1);
/*      */ 
/* 2705 */     CommonTree root_0 = null;
/*      */ 
/* 2707 */     CommonToken char_literal69 = null;
/* 2708 */     option_return option68 = null;
/*      */ 
/* 2710 */     option_return option70 = null;
/*      */ 
/* 2713 */     CommonTree char_literal69_tree = null;
/* 2714 */     RewriteRuleTokenStream stream_COMMA = new RewriteRuleTokenStream(this.adaptor, "token COMMA");
/* 2715 */     RewriteRuleSubtreeStream stream_option = new RewriteRuleSubtreeStream(this.adaptor, "rule option");
/*      */     try
/*      */     {
/* 2720 */       pushFollow(FOLLOW_option_in_exprOptions850);
/* 2721 */       option68 = option();
/*      */ 
/* 2723 */       this.state._fsp -= 1;
/*      */ 
/* 2725 */       stream_option.add(option68.getTree());
/*      */       while (true)
/*      */       {
/* 2730 */         int alt24 = 2;
/* 2731 */         int LA24_0 = this.input.LA(1);
/*      */ 
/* 2733 */         if (LA24_0 == 18) {
/* 2734 */           alt24 = 1;
/*      */         }
/*      */ 
/* 2738 */         switch (alt24)
/*      */         {
/*      */         case 1:
/* 2742 */           char_literal69 = (CommonToken)match(this.input, 18, FOLLOW_COMMA_in_exprOptions854);
/* 2743 */           stream_COMMA.add(char_literal69);
/*      */ 
/* 2746 */           pushFollow(FOLLOW_option_in_exprOptions856);
/* 2747 */           option70 = option();
/*      */ 
/* 2749 */           this.state._fsp -= 1;
/*      */ 
/* 2751 */           stream_option.add(option70.getTree());
/*      */ 
/* 2754 */           break;
/*      */         default:
/* 2757 */           break label216;
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/* 2769 */       label216: retval.tree = root_0;
/* 2770 */       RewriteRuleSubtreeStream stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.tree : null);
/*      */ 
/* 2772 */       root_0 = (CommonTree)this.adaptor.nil();
/*      */ 
/* 2777 */       CommonTree root_1 = (CommonTree)this.adaptor.nil();
/* 2778 */       root_1 = (CommonTree)this.adaptor.becomeRoot((CommonTree)this.adaptor.create(51, "OPTIONS"), root_1);
/*      */ 
/* 2783 */       while (stream_option.hasNext()) {
/* 2784 */         this.adaptor.addChild(root_1, stream_option.nextTree());
/*      */       }
/*      */ 
/* 2787 */       stream_option.reset();
/*      */ 
/* 2789 */       this.adaptor.addChild(root_0, root_1);
/*      */ 
/* 2795 */       retval.tree = root_0;
/*      */ 
/* 2799 */       retval.stop = this.input.LT(-1);
/*      */ 
/* 2802 */       retval.tree = ((CommonTree)this.adaptor.rulePostProcessing(root_0));
/* 2803 */       this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
/*      */     }
/*      */     catch (RecognitionException re)
/*      */     {
/* 2807 */       re = 
/* 2811 */         re;
/*      */ 
/* 2807 */       throw re;
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/* 2812 */     return retval;
/*      */   }
/*      */ 
/*      */   public final option_return option()
/*      */     throws RecognitionException
/*      */   {
/* 2826 */     option_return retval = new option_return();
/* 2827 */     retval.start = this.input.LT(1);
/*      */ 
/* 2830 */     CommonTree root_0 = null;
/*      */ 
/* 2832 */     CommonToken ID71 = null;
/* 2833 */     CommonToken char_literal72 = null;
/* 2834 */     exprNoComma_return exprNoComma73 = null;
/*      */ 
/* 2837 */     CommonTree ID71_tree = null;
/* 2838 */     CommonTree char_literal72_tree = null;
/* 2839 */     RewriteRuleTokenStream stream_EQUALS = new RewriteRuleTokenStream(this.adaptor, "token EQUALS");
/* 2840 */     RewriteRuleTokenStream stream_ID = new RewriteRuleTokenStream(this.adaptor, "token ID");
/* 2841 */     RewriteRuleSubtreeStream stream_exprNoComma = new RewriteRuleSubtreeStream(this.adaptor, "rule exprNoComma");
/*      */ 
/* 2843 */     String id = this.input.LT(1).getText();
/* 2844 */     String defVal = (String)Compiler.defaultOptionValues.get(id);
/* 2845 */     boolean validOption = Compiler.supportedOptions.get(id) != null;
/*      */     try
/*      */     {
/* 2851 */       ID71 = (CommonToken)match(this.input, 25, FOLLOW_ID_in_option883);
/* 2852 */       stream_ID.add(ID71);
/*      */ 
/* 2856 */       if (!validOption) {
/* 2857 */         this.errMgr.compileTimeError(ErrorType.NO_SUCH_OPTION, this.templateToken, ID71, ID71 != null ? ID71.getText() : null);
/*      */       }
/*      */ 
/* 2862 */       int alt25 = 2;
/* 2863 */       int LA25_0 = this.input.LA(1);
/*      */ 
/* 2865 */       if (LA25_0 == 12) {
/* 2866 */         alt25 = 1;
/*      */       }
/* 2868 */       else if ((LA25_0 == 18) || (LA25_0 == 24)) {
/* 2869 */         alt25 = 2;
/*      */       }
/*      */       else {
/* 2872 */         NoViableAltException nvae = new NoViableAltException("", 25, 0, this.input);
/*      */ 
/* 2875 */         throw nvae;
/*      */       }
/*      */ 
/* 2878 */       switch (alt25)
/*      */       {
/*      */       case 1:
/* 2882 */         char_literal72 = (CommonToken)match(this.input, 12, FOLLOW_EQUALS_in_option893);
/* 2883 */         stream_EQUALS.add(char_literal72);
/*      */ 
/* 2886 */         pushFollow(FOLLOW_exprNoComma_in_option895);
/* 2887 */         exprNoComma73 = exprNoComma();
/*      */ 
/* 2889 */         this.state._fsp -= 1;
/*      */ 
/* 2891 */         stream_exprNoComma.add(exprNoComma73.getTree());
/*      */ 
/* 2900 */         retval.tree = root_0;
/* 2901 */         RewriteRuleSubtreeStream stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.tree : null);
/*      */ 
/* 2903 */         root_0 = (CommonTree)this.adaptor.nil();
/*      */ 
/* 2905 */         if (validOption)
/*      */         {
/* 2908 */           CommonTree root_1 = (CommonTree)this.adaptor.nil();
/* 2909 */           root_1 = (CommonTree)this.adaptor.becomeRoot(stream_EQUALS.nextNode(), root_1);
/*      */ 
/* 2913 */           this.adaptor.addChild(root_1, stream_ID.nextNode());
/*      */ 
/* 2917 */           this.adaptor.addChild(root_1, stream_exprNoComma.nextTree());
/*      */ 
/* 2919 */           this.adaptor.addChild(root_0, root_1);
/*      */         }
/*      */         else
/*      */         {
/* 2926 */           root_0 = null;
/*      */         }
/*      */ 
/* 2930 */         retval.tree = root_0;
/*      */ 
/* 2933 */         break;
/*      */       case 2:
/* 2938 */         if (defVal == null) {
/* 2939 */           this.errMgr.compileTimeError(ErrorType.NO_DEFAULT_VALUE, this.templateToken, ID71);
/*      */         }
/*      */ 
/* 2950 */         retval.tree = root_0;
/* 2951 */         RewriteRuleSubtreeStream stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.tree : null);
/*      */ 
/* 2953 */         root_0 = (CommonTree)this.adaptor.nil();
/*      */ 
/* 2955 */         if ((validOption) && (defVal != null))
/*      */         {
/* 2958 */           CommonTree root_1 = (CommonTree)this.adaptor.nil();
/* 2959 */           root_1 = (CommonTree)this.adaptor.becomeRoot((CommonTree)this.adaptor.create(12, "="), root_1);
/*      */ 
/* 2963 */           this.adaptor.addChild(root_1, stream_ID.nextNode());
/*      */ 
/* 2967 */           this.adaptor.addChild(root_1, (CommonTree)this.adaptor.create(26, ID71, '"' + defVal + '"'));
/*      */ 
/* 2971 */           this.adaptor.addChild(root_0, root_1);
/*      */         }
/*      */         else
/*      */         {
/* 2978 */           root_0 = null;
/*      */         }
/*      */ 
/* 2982 */         retval.tree = root_0;
/*      */       }
/*      */ 
/* 2992 */       retval.stop = this.input.LT(-1);
/*      */ 
/* 2995 */       retval.tree = ((CommonTree)this.adaptor.rulePostProcessing(root_0));
/* 2996 */       this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
/*      */     }
/*      */     catch (RecognitionException re)
/*      */     {
/* 3000 */       re = 
/* 3004 */         re;
/*      */ 
/* 3000 */       throw re;
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/* 3005 */     return retval;
/*      */   }
/*      */ 
/*      */   public final exprNoComma_return exprNoComma()
/*      */     throws RecognitionException
/*      */   {
/* 3019 */     exprNoComma_return retval = new exprNoComma_return();
/* 3020 */     retval.start = this.input.LT(1);
/*      */ 
/* 3023 */     CommonTree root_0 = null;
/*      */ 
/* 3025 */     CommonToken char_literal75 = null;
/* 3026 */     memberExpr_return memberExpr74 = null;
/*      */ 
/* 3028 */     mapTemplateRef_return mapTemplateRef76 = null;
/*      */ 
/* 3031 */     CommonTree char_literal75_tree = null;
/* 3032 */     RewriteRuleTokenStream stream_COLON = new RewriteRuleTokenStream(this.adaptor, "token COLON");
/* 3033 */     RewriteRuleSubtreeStream stream_memberExpr = new RewriteRuleSubtreeStream(this.adaptor, "rule memberExpr");
/* 3034 */     RewriteRuleSubtreeStream stream_mapTemplateRef = new RewriteRuleSubtreeStream(this.adaptor, "rule mapTemplateRef");
/*      */     try
/*      */     {
/* 3039 */       pushFollow(FOLLOW_memberExpr_in_exprNoComma1002);
/* 3040 */       memberExpr74 = memberExpr();
/*      */ 
/* 3042 */       this.state._fsp -= 1;
/*      */ 
/* 3044 */       stream_memberExpr.add(memberExpr74.getTree());
/*      */ 
/* 3047 */       int alt26 = 2;
/* 3048 */       int LA26_0 = this.input.LA(1);
/*      */ 
/* 3050 */       if (LA26_0 == 13) {
/* 3051 */         alt26 = 1;
/*      */       }
/* 3053 */       else if ((LA26_0 == 15) || ((LA26_0 >= 17) && (LA26_0 <= 18)) || (LA26_0 == 24)) {
/* 3054 */         alt26 = 2;
/*      */       }
/*      */       else {
/* 3057 */         NoViableAltException nvae = new NoViableAltException("", 26, 0, this.input);
/*      */ 
/* 3060 */         throw nvae;
/*      */       }
/*      */ 
/* 3063 */       switch (alt26)
/*      */       {
/*      */       case 1:
/* 3067 */         char_literal75 = (CommonToken)match(this.input, 13, FOLLOW_COLON_in_exprNoComma1008);
/* 3068 */         stream_COLON.add(char_literal75);
/*      */ 
/* 3071 */         pushFollow(FOLLOW_mapTemplateRef_in_exprNoComma1010);
/* 3072 */         mapTemplateRef76 = mapTemplateRef();
/*      */ 
/* 3074 */         this.state._fsp -= 1;
/*      */ 
/* 3076 */         stream_mapTemplateRef.add(mapTemplateRef76.getTree());
/*      */ 
/* 3085 */         retval.tree = root_0;
/* 3086 */         RewriteRuleSubtreeStream stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.tree : null);
/*      */ 
/* 3088 */         root_0 = (CommonTree)this.adaptor.nil();
/*      */ 
/* 3093 */         CommonTree root_1 = (CommonTree)this.adaptor.nil();
/* 3094 */         root_1 = (CommonTree)this.adaptor.becomeRoot((CommonTree)this.adaptor.create(49, "MAP"), root_1);
/*      */ 
/* 3098 */         this.adaptor.addChild(root_1, stream_memberExpr.nextTree());
/*      */ 
/* 3100 */         this.adaptor.addChild(root_1, stream_mapTemplateRef.nextTree());
/*      */ 
/* 3102 */         this.adaptor.addChild(root_0, root_1);
/*      */ 
/* 3108 */         retval.tree = root_0;
/*      */ 
/* 3111 */         break;
/*      */       case 2:
/* 3122 */         retval.tree = root_0;
/* 3123 */         RewriteRuleSubtreeStream stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.tree : null);
/*      */ 
/* 3125 */         root_0 = (CommonTree)this.adaptor.nil();
/*      */ 
/* 3128 */         this.adaptor.addChild(root_0, stream_memberExpr.nextTree());
/*      */ 
/* 3133 */         retval.tree = root_0;
/*      */       }
/*      */ 
/* 3143 */       retval.stop = this.input.LT(-1);
/*      */ 
/* 3146 */       retval.tree = ((CommonTree)this.adaptor.rulePostProcessing(root_0));
/* 3147 */       this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
/*      */     }
/*      */     catch (RecognitionException re)
/*      */     {
/* 3151 */       re = 
/* 3155 */         re;
/*      */ 
/* 3151 */       throw re;
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/* 3156 */     return retval;
/*      */   }
/*      */ 
/*      */   public final expr_return expr()
/*      */     throws RecognitionException
/*      */   {
/* 3170 */     expr_return retval = new expr_return();
/* 3171 */     retval.start = this.input.LT(1);
/*      */ 
/* 3174 */     CommonTree root_0 = null;
/*      */ 
/* 3176 */     mapExpr_return mapExpr77 = null;
/*      */     try
/*      */     {
/* 3184 */       root_0 = (CommonTree)this.adaptor.nil();
/*      */ 
/* 3187 */       pushFollow(FOLLOW_mapExpr_in_expr1055);
/* 3188 */       mapExpr77 = mapExpr();
/*      */ 
/* 3190 */       this.state._fsp -= 1;
/*      */ 
/* 3192 */       this.adaptor.addChild(root_0, mapExpr77.getTree());
/*      */ 
/* 3196 */       retval.stop = this.input.LT(-1);
/*      */ 
/* 3199 */       retval.tree = ((CommonTree)this.adaptor.rulePostProcessing(root_0));
/* 3200 */       this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
/*      */     }
/*      */     catch (RecognitionException re)
/*      */     {
/* 3204 */       re = 
/* 3208 */         re;
/*      */ 
/* 3204 */       throw re;
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/* 3209 */     return retval;
/*      */   }
/*      */ 
/*      */   public final mapExpr_return mapExpr()
/*      */     throws RecognitionException
/*      */   {
/* 3223 */     mapExpr_return retval = new mapExpr_return();
/* 3224 */     retval.start = this.input.LT(1);
/*      */ 
/* 3227 */     CommonTree root_0 = null;
/*      */ 
/* 3229 */     CommonToken c = null;
/* 3230 */     CommonToken col = null;
/* 3231 */     CommonToken char_literal81 = null;
/* 3232 */     List list_x = null;
/* 3233 */     memberExpr_return memberExpr78 = null;
/*      */ 
/* 3235 */     memberExpr_return memberExpr79 = null;
/*      */ 
/* 3237 */     mapTemplateRef_return mapTemplateRef80 = null;
/*      */ 
/* 3239 */     RuleReturnScope x = null;
/* 3240 */     CommonTree c_tree = null;
/* 3241 */     CommonTree col_tree = null;
/* 3242 */     CommonTree char_literal81_tree = null;
/* 3243 */     RewriteRuleTokenStream stream_COLON = new RewriteRuleTokenStream(this.adaptor, "token COLON");
/* 3244 */     RewriteRuleTokenStream stream_COMMA = new RewriteRuleTokenStream(this.adaptor, "token COMMA");
/* 3245 */     RewriteRuleSubtreeStream stream_memberExpr = new RewriteRuleSubtreeStream(this.adaptor, "rule memberExpr");
/* 3246 */     RewriteRuleSubtreeStream stream_mapTemplateRef = new RewriteRuleSubtreeStream(this.adaptor, "rule mapTemplateRef");
/*      */     try
/*      */     {
/* 3251 */       pushFollow(FOLLOW_memberExpr_in_mapExpr1067);
/* 3252 */       memberExpr78 = memberExpr();
/*      */ 
/* 3254 */       this.state._fsp -= 1;
/*      */ 
/* 3256 */       stream_memberExpr.add(memberExpr78.getTree());
/*      */ 
/* 3259 */       int alt28 = 2;
/* 3260 */       int LA28_0 = this.input.LA(1);
/*      */ 
/* 3262 */       if (LA28_0 == 18) {
/* 3263 */         alt28 = 1;
/*      */       }
/* 3265 */       else if ((LA28_0 == 9) || (LA28_0 == 13) || (LA28_0 == 15) || (LA28_0 == 24)) {
/* 3266 */         alt28 = 2;
/*      */       }
/*      */       else {
/* 3269 */         NoViableAltException nvae = new NoViableAltException("", 28, 0, this.input);
/*      */ 
/* 3272 */         throw nvae;
/*      */       }
/*      */ 
/* 3275 */       switch (alt28)
/*      */       {
/*      */       case 1:
/* 3280 */         int cnt27 = 0;
/*      */         while (true)
/*      */         {
/* 3283 */           int alt27 = 2;
/* 3284 */           int LA27_0 = this.input.LA(1);
/*      */ 
/* 3286 */           if (LA27_0 == 18) {
/* 3287 */             alt27 = 1;
/*      */           }
/*      */ 
/* 3291 */           switch (alt27)
/*      */           {
/*      */           case 1:
/* 3295 */             c = (CommonToken)match(this.input, 18, FOLLOW_COMMA_in_mapExpr1076);
/* 3296 */             stream_COMMA.add(c);
/*      */ 
/* 3299 */             pushFollow(FOLLOW_memberExpr_in_mapExpr1078);
/* 3300 */             memberExpr79 = memberExpr();
/*      */ 
/* 3302 */             this.state._fsp -= 1;
/*      */ 
/* 3304 */             stream_memberExpr.add(memberExpr79.getTree());
/*      */ 
/* 3307 */             break;
/*      */           default:
/* 3310 */             if (cnt27 >= 1) break label411;
/* 3311 */             EarlyExitException eee = new EarlyExitException(27, this.input);
/*      */ 
/* 3313 */             throw eee;
/*      */           }
/* 3315 */           cnt27++;
/*      */         }
/*      */ 
/* 3319 */         col = (CommonToken)match(this.input, 13, FOLLOW_COLON_in_mapExpr1084);
/* 3320 */         stream_COLON.add(col);
/*      */ 
/* 3323 */         pushFollow(FOLLOW_mapTemplateRef_in_mapExpr1086);
/* 3324 */         mapTemplateRef80 = mapTemplateRef();
/*      */ 
/* 3326 */         this.state._fsp -= 1;
/*      */ 
/* 3328 */         stream_mapTemplateRef.add(mapTemplateRef80.getTree());
/*      */ 
/* 3337 */         retval.tree = root_0;
/* 3338 */         RewriteRuleSubtreeStream stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.tree : null);
/*      */ 
/* 3340 */         root_0 = (CommonTree)this.adaptor.nil();
/*      */ 
/* 3345 */         CommonTree root_1 = (CommonTree)this.adaptor.nil();
/* 3346 */         root_1 = (CommonTree)this.adaptor.becomeRoot((CommonTree)this.adaptor.create(57, col), root_1);
/*      */ 
/* 3352 */         CommonTree root_2 = (CommonTree)this.adaptor.nil();
/* 3353 */         root_2 = (CommonTree)this.adaptor.becomeRoot((CommonTree)this.adaptor.create(39, "ELEMENTS"), root_2);
/*      */ 
/* 3357 */         if (!stream_memberExpr.hasNext()) {
/* 3358 */           throw new RewriteEarlyExitException();
/*      */         }
/* 3360 */         while (stream_memberExpr.hasNext()) {
/* 3361 */           this.adaptor.addChild(root_2, stream_memberExpr.nextTree());
/*      */         }
/*      */ 
/* 3364 */         stream_memberExpr.reset();
/*      */ 
/* 3366 */         this.adaptor.addChild(root_1, root_2);
/*      */ 
/* 3369 */         this.adaptor.addChild(root_1, stream_mapTemplateRef.nextTree());
/*      */ 
/* 3371 */         this.adaptor.addChild(root_0, root_1);
/*      */ 
/* 3377 */         retval.tree = root_0;
/*      */ 
/* 3380 */         break;
/*      */       case 2:
/* 3391 */         label411: retval.tree = root_0;
/* 3392 */         RewriteRuleSubtreeStream stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.tree : null);
/*      */ 
/* 3394 */         root_0 = (CommonTree)this.adaptor.nil();
/*      */ 
/* 3397 */         this.adaptor.addChild(root_0, stream_memberExpr.nextTree());
/*      */ 
/* 3402 */         retval.tree = root_0;
/*      */       }
/*      */ 
/*      */       while (true)
/*      */       {
/* 3413 */         int alt30 = 2;
/* 3414 */         int LA30_0 = this.input.LA(1);
/*      */ 
/* 3416 */         if (LA30_0 == 13) {
/* 3417 */           alt30 = 1;
/*      */         }
/*      */ 
/* 3421 */         switch (alt30)
/*      */         {
/*      */         case 1:
/* 3425 */           if (list_x != null) list_x.clear();
/*      */ 
/* 3427 */           col = (CommonToken)match(this.input, 13, FOLLOW_COLON_in_mapExpr1149);
/* 3428 */           stream_COLON.add(col);
/*      */ 
/* 3431 */           pushFollow(FOLLOW_mapTemplateRef_in_mapExpr1153);
/* 3432 */           x = mapTemplateRef();
/*      */ 
/* 3434 */           this.state._fsp -= 1;
/*      */ 
/* 3436 */           stream_mapTemplateRef.add(x.getTree());
/* 3437 */           if (list_x == null) list_x = new ArrayList();
/* 3438 */           list_x.add(x.getTree());
/*      */           while (true)
/*      */           {
/* 3444 */             int alt29 = 2;
/* 3445 */             int LA29_0 = this.input.LA(1);
/*      */ 
/* 3447 */             if ((LA29_0 == 18) && (c == null)) {
/* 3448 */               alt29 = 1;
/*      */             }
/*      */ 
/* 3452 */             switch (alt29)
/*      */             {
/*      */             case 1:
/* 3456 */               if (c != null) {
/* 3457 */                 throw new FailedPredicateException(this.input, "mapExpr", "$c==null");
/*      */               }
/*      */ 
/* 3460 */               char_literal81 = (CommonToken)match(this.input, 18, FOLLOW_COMMA_in_mapExpr1159);
/* 3461 */               stream_COMMA.add(char_literal81);
/*      */ 
/* 3464 */               pushFollow(FOLLOW_mapTemplateRef_in_mapExpr1163);
/* 3465 */               x = mapTemplateRef();
/*      */ 
/* 3467 */               this.state._fsp -= 1;
/*      */ 
/* 3469 */               stream_mapTemplateRef.add(x.getTree());
/* 3470 */               if (list_x == null) list_x = new ArrayList();
/* 3471 */               list_x.add(x.getTree());
/*      */ 
/* 3475 */               break;
/*      */             default:
/* 3478 */               break label1083;
/*      */             }
/*      */ 
/*      */           }
/*      */ 
/* 3490 */           retval.tree = root_0;
/* 3491 */           RewriteRuleSubtreeStream stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.tree : null);
/* 3492 */           RewriteRuleSubtreeStream stream_x = new RewriteRuleSubtreeStream(this.adaptor, "token x", list_x);
/* 3493 */           root_0 = (CommonTree)this.adaptor.nil();
/*      */ 
/* 3498 */           CommonTree root_1 = (CommonTree)this.adaptor.nil();
/* 3499 */           root_1 = (CommonTree)this.adaptor.becomeRoot((CommonTree)this.adaptor.create(49, col), root_1);
/*      */ 
/* 3503 */           this.adaptor.addChild(root_1, stream_retval.nextTree());
/*      */ 
/* 3505 */           if (!stream_x.hasNext()) {
/* 3506 */             throw new RewriteEarlyExitException();
/*      */           }
/* 3508 */           while (stream_x.hasNext()) {
/* 3509 */             this.adaptor.addChild(root_1, stream_x.nextTree());
/*      */           }
/*      */ 
/* 3512 */           stream_x.reset();
/*      */ 
/* 3514 */           this.adaptor.addChild(root_0, root_1);
/*      */ 
/* 3520 */           retval.tree = root_0;
/*      */ 
/* 3523 */           break;
/*      */         default:
/* 3526 */           label1083: break label1282;
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/* 3533 */       label1282: retval.stop = this.input.LT(-1);
/*      */ 
/* 3536 */       retval.tree = ((CommonTree)this.adaptor.rulePostProcessing(root_0));
/* 3537 */       this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
/*      */     }
/*      */     catch (RecognitionException re)
/*      */     {
/* 3541 */       re = 
/* 3545 */         re;
/*      */ 
/* 3541 */       throw re;
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/* 3546 */     return retval;
/*      */   }
/*      */ 
/*      */   public final mapTemplateRef_return mapTemplateRef()
/*      */     throws RecognitionException
/*      */   {
/* 3560 */     mapTemplateRef_return retval = new mapTemplateRef_return();
/* 3561 */     retval.start = this.input.LT(1);
/*      */ 
/* 3564 */     CommonTree root_0 = null;
/*      */ 
/* 3566 */     CommonToken lp = null;
/* 3567 */     CommonToken rp = null;
/* 3568 */     CommonToken ID82 = null;
/* 3569 */     CommonToken char_literal83 = null;
/* 3570 */     CommonToken char_literal85 = null;
/* 3571 */     CommonToken char_literal88 = null;
/* 3572 */     CommonToken char_literal90 = null;
/* 3573 */     args_return args84 = null;
/*      */ 
/* 3575 */     subtemplate_return subtemplate86 = null;
/*      */ 
/* 3577 */     mapExpr_return mapExpr87 = null;
/*      */ 
/* 3579 */     argExprList_return argExprList89 = null;
/*      */ 
/* 3582 */     CommonTree lp_tree = null;
/* 3583 */     CommonTree rp_tree = null;
/* 3584 */     CommonTree ID82_tree = null;
/* 3585 */     CommonTree char_literal83_tree = null;
/* 3586 */     CommonTree char_literal85_tree = null;
/* 3587 */     CommonTree char_literal88_tree = null;
/* 3588 */     CommonTree char_literal90_tree = null;
/* 3589 */     RewriteRuleTokenStream stream_RPAREN = new RewriteRuleTokenStream(this.adaptor, "token RPAREN");
/* 3590 */     RewriteRuleTokenStream stream_ID = new RewriteRuleTokenStream(this.adaptor, "token ID");
/* 3591 */     RewriteRuleTokenStream stream_LPAREN = new RewriteRuleTokenStream(this.adaptor, "token LPAREN");
/* 3592 */     RewriteRuleSubtreeStream stream_argExprList = new RewriteRuleSubtreeStream(this.adaptor, "rule argExprList");
/* 3593 */     RewriteRuleSubtreeStream stream_args = new RewriteRuleSubtreeStream(this.adaptor, "rule args");
/* 3594 */     RewriteRuleSubtreeStream stream_mapExpr = new RewriteRuleSubtreeStream(this.adaptor, "rule mapExpr");
/*      */     try
/*      */     {
/* 3597 */       int alt32 = 3;
/* 3598 */       switch (this.input.LA(1))
/*      */       {
/*      */       case 25:
/* 3601 */         alt32 = 1;
/*      */ 
/* 3603 */         break;
/*      */       case 20:
/* 3606 */         alt32 = 2;
/*      */ 
/* 3608 */         break;
/*      */       case 14:
/* 3611 */         alt32 = 3;
/*      */ 
/* 3613 */         break;
/*      */       default:
/* 3615 */         NoViableAltException nvae = new NoViableAltException("", 32, 0, this.input);
/*      */ 
/* 3618 */         throw nvae;
/*      */       }
/*      */ 
/* 3622 */       switch (alt32)
/*      */       {
/*      */       case 1:
/* 3626 */         ID82 = (CommonToken)match(this.input, 25, FOLLOW_ID_in_mapTemplateRef1210);
/* 3627 */         stream_ID.add(ID82);
/*      */ 
/* 3630 */         char_literal83 = (CommonToken)match(this.input, 14, FOLLOW_LPAREN_in_mapTemplateRef1212);
/* 3631 */         stream_LPAREN.add(char_literal83);
/*      */ 
/* 3634 */         pushFollow(FOLLOW_args_in_mapTemplateRef1214);
/* 3635 */         args84 = args();
/*      */ 
/* 3637 */         this.state._fsp -= 1;
/*      */ 
/* 3639 */         stream_args.add(args84.getTree());
/*      */ 
/* 3641 */         char_literal85 = (CommonToken)match(this.input, 15, FOLLOW_RPAREN_in_mapTemplateRef1216);
/* 3642 */         stream_RPAREN.add(char_literal85);
/*      */ 
/* 3652 */         retval.tree = root_0;
/* 3653 */         RewriteRuleSubtreeStream stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.tree : null);
/*      */ 
/* 3655 */         root_0 = (CommonTree)this.adaptor.nil();
/*      */ 
/* 3660 */         CommonTree root_1 = (CommonTree)this.adaptor.nil();
/* 3661 */         root_1 = (CommonTree)this.adaptor.becomeRoot((CommonTree)this.adaptor.create(42, "INCLUDE"), root_1);
/*      */ 
/* 3665 */         this.adaptor.addChild(root_1, stream_ID.nextNode());
/*      */ 
/* 3670 */         if (stream_args.hasNext()) {
/* 3671 */           this.adaptor.addChild(root_1, stream_args.nextTree());
/*      */         }
/*      */ 
/* 3674 */         stream_args.reset();
/*      */ 
/* 3676 */         this.adaptor.addChild(root_0, root_1);
/*      */ 
/* 3682 */         retval.tree = root_0;
/*      */ 
/* 3685 */         break;
/*      */       case 2:
/* 3689 */         root_0 = (CommonTree)this.adaptor.nil();
/*      */ 
/* 3692 */         pushFollow(FOLLOW_subtemplate_in_mapTemplateRef1238);
/* 3693 */         subtemplate86 = subtemplate();
/*      */ 
/* 3695 */         this.state._fsp -= 1;
/*      */ 
/* 3697 */         this.adaptor.addChild(root_0, subtemplate86.getTree());
/*      */ 
/* 3700 */         break;
/*      */       case 3:
/* 3704 */         lp = (CommonToken)match(this.input, 14, FOLLOW_LPAREN_in_mapTemplateRef1245);
/* 3705 */         stream_LPAREN.add(lp);
/*      */ 
/* 3708 */         pushFollow(FOLLOW_mapExpr_in_mapTemplateRef1247);
/* 3709 */         mapExpr87 = mapExpr();
/*      */ 
/* 3711 */         this.state._fsp -= 1;
/*      */ 
/* 3713 */         stream_mapExpr.add(mapExpr87.getTree());
/*      */ 
/* 3715 */         rp = (CommonToken)match(this.input, 15, FOLLOW_RPAREN_in_mapTemplateRef1251);
/* 3716 */         stream_RPAREN.add(rp);
/*      */ 
/* 3719 */         char_literal88 = (CommonToken)match(this.input, 14, FOLLOW_LPAREN_in_mapTemplateRef1253);
/* 3720 */         stream_LPAREN.add(char_literal88);
/*      */ 
/* 3724 */         int alt31 = 2;
/* 3725 */         int LA31_0 = this.input.LA(1);
/*      */ 
/* 3727 */         if ((LA31_0 == 8) || (LA31_0 == 16) || (LA31_0 == 20) || ((LA31_0 >= 25) && (LA31_0 <= 26)) || (LA31_0 == 33) || ((LA31_0 >= 35) && (LA31_0 <= 36))) {
/* 3728 */           alt31 = 1;
/*      */         }
/* 3730 */         else if ((LA31_0 == 14) && ((this.conditional_stack.size() == 0) || (this.conditional_stack.size() > 0))) {
/* 3731 */           alt31 = 1;
/*      */         }
/* 3733 */         switch (alt31)
/*      */         {
/*      */         case 1:
/* 3737 */           pushFollow(FOLLOW_argExprList_in_mapTemplateRef1255);
/* 3738 */           argExprList89 = argExprList();
/*      */ 
/* 3740 */           this.state._fsp -= 1;
/*      */ 
/* 3742 */           stream_argExprList.add(argExprList89.getTree());
/*      */         }
/*      */ 
/* 3750 */         char_literal90 = (CommonToken)match(this.input, 15, FOLLOW_RPAREN_in_mapTemplateRef1258);
/* 3751 */         stream_RPAREN.add(char_literal90);
/*      */ 
/* 3761 */         retval.tree = root_0;
/* 3762 */         RewriteRuleSubtreeStream stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.tree : null);
/*      */ 
/* 3764 */         root_0 = (CommonTree)this.adaptor.nil();
/*      */ 
/* 3769 */         CommonTree root_1 = (CommonTree)this.adaptor.nil();
/* 3770 */         root_1 = (CommonTree)this.adaptor.becomeRoot((CommonTree)this.adaptor.create(43, "INCLUDE_IND"), root_1);
/*      */ 
/* 3774 */         this.adaptor.addChild(root_1, stream_mapExpr.nextTree());
/*      */ 
/* 3777 */         if (stream_argExprList.hasNext()) {
/* 3778 */           this.adaptor.addChild(root_1, stream_argExprList.nextTree());
/*      */         }
/*      */ 
/* 3781 */         stream_argExprList.reset();
/*      */ 
/* 3783 */         this.adaptor.addChild(root_0, root_1);
/*      */ 
/* 3789 */         retval.tree = root_0;
/*      */       }
/*      */ 
/* 3795 */       retval.stop = this.input.LT(-1);
/*      */ 
/* 3798 */       retval.tree = ((CommonTree)this.adaptor.rulePostProcessing(root_0));
/* 3799 */       this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
/*      */     }
/*      */     catch (RecognitionException re)
/*      */     {
/* 3803 */       re = 
/* 3807 */         re;
/*      */ 
/* 3803 */       throw re;
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/* 3808 */     return retval;
/*      */   }
/*      */ 
/*      */   public final memberExpr_return memberExpr()
/*      */     throws RecognitionException
/*      */   {
/* 3822 */     memberExpr_return retval = new memberExpr_return();
/* 3823 */     retval.start = this.input.LT(1);
/*      */ 
/* 3826 */     CommonTree root_0 = null;
/*      */ 
/* 3828 */     CommonToken p = null;
/* 3829 */     CommonToken ID92 = null;
/* 3830 */     CommonToken char_literal93 = null;
/* 3831 */     CommonToken char_literal95 = null;
/* 3832 */     includeExpr_return includeExpr91 = null;
/*      */ 
/* 3834 */     mapExpr_return mapExpr94 = null;
/*      */ 
/* 3837 */     CommonTree p_tree = null;
/* 3838 */     CommonTree ID92_tree = null;
/* 3839 */     CommonTree char_literal93_tree = null;
/* 3840 */     CommonTree char_literal95_tree = null;
/* 3841 */     RewriteRuleTokenStream stream_RPAREN = new RewriteRuleTokenStream(this.adaptor, "token RPAREN");
/* 3842 */     RewriteRuleTokenStream stream_DOT = new RewriteRuleTokenStream(this.adaptor, "token DOT");
/* 3843 */     RewriteRuleTokenStream stream_ID = new RewriteRuleTokenStream(this.adaptor, "token ID");
/* 3844 */     RewriteRuleTokenStream stream_LPAREN = new RewriteRuleTokenStream(this.adaptor, "token LPAREN");
/* 3845 */     RewriteRuleSubtreeStream stream_includeExpr = new RewriteRuleSubtreeStream(this.adaptor, "rule includeExpr");
/* 3846 */     RewriteRuleSubtreeStream stream_mapExpr = new RewriteRuleSubtreeStream(this.adaptor, "rule mapExpr");
/*      */     try
/*      */     {
/* 3854 */       pushFollow(FOLLOW_includeExpr_in_memberExpr1281);
/* 3855 */       includeExpr91 = includeExpr();
/*      */ 
/* 3857 */       this.state._fsp -= 1;
/*      */ 
/* 3859 */       stream_includeExpr.add(includeExpr91.getTree());
/*      */ 
/* 3868 */       retval.tree = root_0;
/* 3869 */       RewriteRuleSubtreeStream stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.tree : null);
/*      */ 
/* 3871 */       root_0 = (CommonTree)this.adaptor.nil();
/*      */ 
/* 3874 */       this.adaptor.addChild(root_0, stream_includeExpr.nextTree());
/*      */ 
/* 3879 */       retval.tree = root_0;
/*      */       while (true)
/*      */       {
/* 3887 */         int alt33 = 3;
/* 3888 */         int LA33_0 = this.input.LA(1);
/*      */ 
/* 3890 */         if (LA33_0 == 19) {
/* 3891 */           int LA33_2 = this.input.LA(2);
/*      */ 
/* 3893 */           if (LA33_2 == 25) {
/* 3894 */             alt33 = 1;
/*      */           }
/* 3896 */           else if (LA33_2 == 14) {
/* 3897 */             alt33 = 2;
/*      */           }
/*      */ 
/*      */         }
/*      */ 
/* 3904 */         switch (alt33)
/*      */         {
/*      */         case 1:
/* 3908 */           p = (CommonToken)match(this.input, 19, FOLLOW_DOT_in_memberExpr1292);
/* 3909 */           stream_DOT.add(p);
/*      */ 
/* 3912 */           ID92 = (CommonToken)match(this.input, 25, FOLLOW_ID_in_memberExpr1294);
/* 3913 */           stream_ID.add(ID92);
/*      */ 
/* 3923 */           retval.tree = root_0;
/* 3924 */           RewriteRuleSubtreeStream stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.tree : null);
/*      */ 
/* 3926 */           root_0 = (CommonTree)this.adaptor.nil();
/*      */ 
/* 3931 */           CommonTree root_1 = (CommonTree)this.adaptor.nil();
/* 3932 */           root_1 = (CommonTree)this.adaptor.becomeRoot((CommonTree)this.adaptor.create(52, p, "PROP"), root_1);
/*      */ 
/* 3936 */           this.adaptor.addChild(root_1, stream_retval.nextTree());
/*      */ 
/* 3938 */           this.adaptor.addChild(root_1, stream_ID.nextNode());
/*      */ 
/* 3942 */           this.adaptor.addChild(root_0, root_1);
/*      */ 
/* 3948 */           retval.tree = root_0;
/*      */ 
/* 3951 */           break;
/*      */         case 2:
/* 3955 */           p = (CommonToken)match(this.input, 19, FOLLOW_DOT_in_memberExpr1320);
/* 3956 */           stream_DOT.add(p);
/*      */ 
/* 3959 */           char_literal93 = (CommonToken)match(this.input, 14, FOLLOW_LPAREN_in_memberExpr1322);
/* 3960 */           stream_LPAREN.add(char_literal93);
/*      */ 
/* 3963 */           pushFollow(FOLLOW_mapExpr_in_memberExpr1324);
/* 3964 */           mapExpr94 = mapExpr();
/*      */ 
/* 3966 */           this.state._fsp -= 1;
/*      */ 
/* 3968 */           stream_mapExpr.add(mapExpr94.getTree());
/*      */ 
/* 3970 */           char_literal95 = (CommonToken)match(this.input, 15, FOLLOW_RPAREN_in_memberExpr1326);
/* 3971 */           stream_RPAREN.add(char_literal95);
/*      */ 
/* 3981 */           retval.tree = root_0;
/* 3982 */           RewriteRuleSubtreeStream stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.tree : null);
/*      */ 
/* 3984 */           root_0 = (CommonTree)this.adaptor.nil();
/*      */ 
/* 3989 */           CommonTree root_1 = (CommonTree)this.adaptor.nil();
/* 3990 */           root_1 = (CommonTree)this.adaptor.becomeRoot((CommonTree)this.adaptor.create(53, p, "PROP_IND"), root_1);
/*      */ 
/* 3994 */           this.adaptor.addChild(root_1, stream_retval.nextTree());
/*      */ 
/* 3996 */           this.adaptor.addChild(root_1, stream_mapExpr.nextTree());
/*      */ 
/* 3998 */           this.adaptor.addChild(root_0, root_1);
/*      */ 
/* 4004 */           retval.tree = root_0;
/*      */ 
/* 4007 */           break;
/*      */         default:
/* 4010 */           break label785;
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/* 4017 */       label785: retval.stop = this.input.LT(-1);
/*      */ 
/* 4020 */       retval.tree = ((CommonTree)this.adaptor.rulePostProcessing(root_0));
/* 4021 */       this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
/*      */     }
/*      */     catch (RecognitionException re)
/*      */     {
/* 4025 */       re = 
/* 4029 */         re;
/*      */ 
/* 4025 */       throw re;
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/* 4030 */     return retval;
/*      */   }
/*      */ 
/*      */   public final includeExpr_return includeExpr()
/*      */     throws RecognitionException
/*      */   {
/* 4044 */     includeExpr_return retval = new includeExpr_return();
/* 4045 */     retval.start = this.input.LT(1);
/*      */ 
/* 4048 */     CommonTree root_0 = null;
/*      */ 
/* 4050 */     CommonToken rp = null;
/* 4051 */     CommonToken ID96 = null;
/* 4052 */     CommonToken char_literal97 = null;
/* 4053 */     CommonToken char_literal99 = null;
/* 4054 */     CommonToken string_literal100 = null;
/* 4055 */     CommonToken char_literal101 = null;
/* 4056 */     CommonToken ID102 = null;
/* 4057 */     CommonToken char_literal103 = null;
/* 4058 */     CommonToken char_literal105 = null;
/* 4059 */     CommonToken ID106 = null;
/* 4060 */     CommonToken char_literal107 = null;
/* 4061 */     CommonToken char_literal109 = null;
/* 4062 */     CommonToken char_literal110 = null;
/* 4063 */     CommonToken string_literal111 = null;
/* 4064 */     CommonToken char_literal112 = null;
/* 4065 */     CommonToken ID113 = null;
/* 4066 */     CommonToken char_literal114 = null;
/* 4067 */     CommonToken char_literal115 = null;
/* 4068 */     CommonToken ID116 = null;
/* 4069 */     CommonToken char_literal117 = null;
/* 4070 */     expr_return expr98 = null;
/*      */ 
/* 4072 */     args_return args104 = null;
/*      */ 
/* 4074 */     args_return args108 = null;
/*      */ 
/* 4076 */     primary_return primary118 = null;
/*      */ 
/* 4079 */     CommonTree rp_tree = null;
/* 4080 */     CommonTree ID96_tree = null;
/* 4081 */     CommonTree char_literal97_tree = null;
/* 4082 */     CommonTree char_literal99_tree = null;
/* 4083 */     CommonTree string_literal100_tree = null;
/* 4084 */     CommonTree char_literal101_tree = null;
/* 4085 */     CommonTree ID102_tree = null;
/* 4086 */     CommonTree char_literal103_tree = null;
/* 4087 */     CommonTree char_literal105_tree = null;
/* 4088 */     CommonTree ID106_tree = null;
/* 4089 */     CommonTree char_literal107_tree = null;
/* 4090 */     CommonTree char_literal109_tree = null;
/* 4091 */     CommonTree char_literal110_tree = null;
/* 4092 */     CommonTree string_literal111_tree = null;
/* 4093 */     CommonTree char_literal112_tree = null;
/* 4094 */     CommonTree ID113_tree = null;
/* 4095 */     CommonTree char_literal114_tree = null;
/* 4096 */     CommonTree char_literal115_tree = null;
/* 4097 */     CommonTree ID116_tree = null;
/* 4098 */     CommonTree char_literal117_tree = null;
/* 4099 */     RewriteRuleTokenStream stream_AT = new RewriteRuleTokenStream(this.adaptor, "token AT");
/* 4100 */     RewriteRuleTokenStream stream_RPAREN = new RewriteRuleTokenStream(this.adaptor, "token RPAREN");
/* 4101 */     RewriteRuleTokenStream stream_SUPER = new RewriteRuleTokenStream(this.adaptor, "token SUPER");
/* 4102 */     RewriteRuleTokenStream stream_DOT = new RewriteRuleTokenStream(this.adaptor, "token DOT");
/* 4103 */     RewriteRuleTokenStream stream_ID = new RewriteRuleTokenStream(this.adaptor, "token ID");
/* 4104 */     RewriteRuleTokenStream stream_LPAREN = new RewriteRuleTokenStream(this.adaptor, "token LPAREN");
/* 4105 */     RewriteRuleSubtreeStream stream_args = new RewriteRuleSubtreeStream(this.adaptor, "rule args");
/* 4106 */     RewriteRuleSubtreeStream stream_expr = new RewriteRuleSubtreeStream(this.adaptor, "rule expr");
/*      */     try
/*      */     {
/* 4109 */       int alt35 = 6;
/* 4110 */       int LA35_0 = this.input.LA(1);
/*      */ 
/* 4112 */       if (LA35_0 == 25) {
/* 4113 */         int LA35_1 = this.input.LA(2);
/*      */ 
/* 4115 */         if (LA35_1 == 14) {
/* 4116 */           int LA35_10 = this.input.LA(3);
/*      */ 
/* 4118 */           if (Compiler.funcs.containsKey(this.input.LT(1).getText())) {
/* 4119 */             alt35 = 1;
/*      */           }
/*      */           else {
/* 4122 */             alt35 = 3;
/*      */           }
/*      */ 
/*      */         }
/* 4132 */         else if ((LA35_1 == 9) || (LA35_1 == 13) || (LA35_1 == 15) || ((LA35_1 >= 17) && (LA35_1 <= 19)) || (LA35_1 == 24) || ((LA35_1 >= 29) && (LA35_1 <= 30))) {
/* 4133 */           alt35 = 6;
/*      */         }
/*      */         else {
/* 4136 */           NoViableAltException nvae = new NoViableAltException("", 35, 1, this.input);
/*      */ 
/* 4139 */           throw nvae;
/*      */         }
/*      */ 
/*      */       }
/* 4143 */       else if (LA35_0 == 8) {
/* 4144 */         alt35 = 2;
/*      */       }
/* 4146 */       else if (LA35_0 == 33) {
/* 4147 */         int LA35_3 = this.input.LA(2);
/*      */ 
/* 4149 */         if (LA35_3 == 8) {
/* 4150 */           alt35 = 4;
/*      */         }
/* 4152 */         else if (LA35_3 == 25) {
/* 4153 */           alt35 = 5;
/*      */         }
/*      */         else {
/* 4156 */           NoViableAltException nvae = new NoViableAltException("", 35, 3, this.input);
/*      */ 
/* 4159 */           throw nvae;
/*      */         }
/*      */ 
/*      */       }
/* 4163 */       else if ((LA35_0 == 16) || (LA35_0 == 20) || (LA35_0 == 26) || ((LA35_0 >= 35) && (LA35_0 <= 36))) {
/* 4164 */         alt35 = 6;
/*      */       }
/* 4166 */       else if ((LA35_0 == 14) && ((this.conditional_stack.size() == 0) || (this.conditional_stack.size() > 0))) {
/* 4167 */         alt35 = 6;
/*      */       }
/*      */       else {
/* 4170 */         NoViableAltException nvae = new NoViableAltException("", 35, 0, this.input);
/*      */ 
/* 4173 */         throw nvae;
/*      */       }
/*      */ 
/* 4176 */       switch (alt35)
/*      */       {
/*      */       case 1:
/* 4180 */         if (!Compiler.funcs.containsKey(this.input.LT(1).getText())) {
/* 4181 */           throw new FailedPredicateException(this.input, "includeExpr", "Compiler.funcs.containsKey(input.LT(1).getText())");
/*      */         }
/*      */ 
/* 4184 */         ID96 = (CommonToken)match(this.input, 25, FOLLOW_ID_in_includeExpr1370);
/* 4185 */         stream_ID.add(ID96);
/*      */ 
/* 4188 */         char_literal97 = (CommonToken)match(this.input, 14, FOLLOW_LPAREN_in_includeExpr1372);
/* 4189 */         stream_LPAREN.add(char_literal97);
/*      */ 
/* 4193 */         int alt34 = 2;
/* 4194 */         int LA34_0 = this.input.LA(1);
/*      */ 
/* 4196 */         if ((LA34_0 == 8) || (LA34_0 == 16) || (LA34_0 == 20) || ((LA34_0 >= 25) && (LA34_0 <= 26)) || (LA34_0 == 33) || ((LA34_0 >= 35) && (LA34_0 <= 36))) {
/* 4197 */           alt34 = 1;
/*      */         }
/* 4199 */         else if ((LA34_0 == 14) && ((this.conditional_stack.size() == 0) || (this.conditional_stack.size() > 0))) {
/* 4200 */           alt34 = 1;
/*      */         }
/* 4202 */         switch (alt34)
/*      */         {
/*      */         case 1:
/* 4206 */           pushFollow(FOLLOW_expr_in_includeExpr1374);
/* 4207 */           expr98 = expr();
/*      */ 
/* 4209 */           this.state._fsp -= 1;
/*      */ 
/* 4211 */           stream_expr.add(expr98.getTree());
/*      */         }
/*      */ 
/* 4219 */         char_literal99 = (CommonToken)match(this.input, 15, FOLLOW_RPAREN_in_includeExpr1377);
/* 4220 */         stream_RPAREN.add(char_literal99);
/*      */ 
/* 4230 */         retval.tree = root_0;
/* 4231 */         RewriteRuleSubtreeStream stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.tree : null);
/*      */ 
/* 4233 */         root_0 = (CommonTree)this.adaptor.nil();
/*      */ 
/* 4238 */         CommonTree root_1 = (CommonTree)this.adaptor.nil();
/* 4239 */         root_1 = (CommonTree)this.adaptor.becomeRoot((CommonTree)this.adaptor.create(40, "EXEC_FUNC"), root_1);
/*      */ 
/* 4243 */         this.adaptor.addChild(root_1, stream_ID.nextNode());
/*      */ 
/* 4248 */         if (stream_expr.hasNext()) {
/* 4249 */           this.adaptor.addChild(root_1, stream_expr.nextTree());
/*      */         }
/*      */ 
/* 4252 */         stream_expr.reset();
/*      */ 
/* 4254 */         this.adaptor.addChild(root_0, root_1);
/*      */ 
/* 4260 */         retval.tree = root_0;
/*      */ 
/* 4263 */         break;
/*      */       case 2:
/* 4267 */         string_literal100 = (CommonToken)match(this.input, 8, FOLLOW_SUPER_in_includeExpr1398);
/* 4268 */         stream_SUPER.add(string_literal100);
/*      */ 
/* 4271 */         char_literal101 = (CommonToken)match(this.input, 19, FOLLOW_DOT_in_includeExpr1400);
/* 4272 */         stream_DOT.add(char_literal101);
/*      */ 
/* 4275 */         ID102 = (CommonToken)match(this.input, 25, FOLLOW_ID_in_includeExpr1402);
/* 4276 */         stream_ID.add(ID102);
/*      */ 
/* 4279 */         char_literal103 = (CommonToken)match(this.input, 14, FOLLOW_LPAREN_in_includeExpr1404);
/* 4280 */         stream_LPAREN.add(char_literal103);
/*      */ 
/* 4283 */         pushFollow(FOLLOW_args_in_includeExpr1406);
/* 4284 */         args104 = args();
/*      */ 
/* 4286 */         this.state._fsp -= 1;
/*      */ 
/* 4288 */         stream_args.add(args104.getTree());
/*      */ 
/* 4290 */         char_literal105 = (CommonToken)match(this.input, 15, FOLLOW_RPAREN_in_includeExpr1408);
/* 4291 */         stream_RPAREN.add(char_literal105);
/*      */ 
/* 4301 */         retval.tree = root_0;
/* 4302 */         RewriteRuleSubtreeStream stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.tree : null);
/*      */ 
/* 4304 */         root_0 = (CommonTree)this.adaptor.nil();
/*      */ 
/* 4309 */         CommonTree root_1 = (CommonTree)this.adaptor.nil();
/* 4310 */         root_1 = (CommonTree)this.adaptor.becomeRoot((CommonTree)this.adaptor.create(45, "INCLUDE_SUPER"), root_1);
/*      */ 
/* 4314 */         this.adaptor.addChild(root_1, stream_ID.nextNode());
/*      */ 
/* 4319 */         if (stream_args.hasNext()) {
/* 4320 */           this.adaptor.addChild(root_1, stream_args.nextTree());
/*      */         }
/*      */ 
/* 4323 */         stream_args.reset();
/*      */ 
/* 4325 */         this.adaptor.addChild(root_0, root_1);
/*      */ 
/* 4331 */         retval.tree = root_0;
/*      */ 
/* 4334 */         break;
/*      */       case 3:
/* 4338 */         ID106 = (CommonToken)match(this.input, 25, FOLLOW_ID_in_includeExpr1427);
/* 4339 */         stream_ID.add(ID106);
/*      */ 
/* 4342 */         char_literal107 = (CommonToken)match(this.input, 14, FOLLOW_LPAREN_in_includeExpr1429);
/* 4343 */         stream_LPAREN.add(char_literal107);
/*      */ 
/* 4346 */         pushFollow(FOLLOW_args_in_includeExpr1431);
/* 4347 */         args108 = args();
/*      */ 
/* 4349 */         this.state._fsp -= 1;
/*      */ 
/* 4351 */         stream_args.add(args108.getTree());
/*      */ 
/* 4353 */         char_literal109 = (CommonToken)match(this.input, 15, FOLLOW_RPAREN_in_includeExpr1433);
/* 4354 */         stream_RPAREN.add(char_literal109);
/*      */ 
/* 4364 */         retval.tree = root_0;
/* 4365 */         RewriteRuleSubtreeStream stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.tree : null);
/*      */ 
/* 4367 */         root_0 = (CommonTree)this.adaptor.nil();
/*      */ 
/* 4372 */         CommonTree root_1 = (CommonTree)this.adaptor.nil();
/* 4373 */         root_1 = (CommonTree)this.adaptor.becomeRoot((CommonTree)this.adaptor.create(42, "INCLUDE"), root_1);
/*      */ 
/* 4377 */         this.adaptor.addChild(root_1, stream_ID.nextNode());
/*      */ 
/* 4382 */         if (stream_args.hasNext()) {
/* 4383 */           this.adaptor.addChild(root_1, stream_args.nextTree());
/*      */         }
/*      */ 
/* 4386 */         stream_args.reset();
/*      */ 
/* 4388 */         this.adaptor.addChild(root_0, root_1);
/*      */ 
/* 4394 */         retval.tree = root_0;
/*      */ 
/* 4397 */         break;
/*      */       case 4:
/* 4401 */         char_literal110 = (CommonToken)match(this.input, 33, FOLLOW_AT_in_includeExpr1455);
/* 4402 */         stream_AT.add(char_literal110);
/*      */ 
/* 4405 */         string_literal111 = (CommonToken)match(this.input, 8, FOLLOW_SUPER_in_includeExpr1457);
/* 4406 */         stream_SUPER.add(string_literal111);
/*      */ 
/* 4409 */         char_literal112 = (CommonToken)match(this.input, 19, FOLLOW_DOT_in_includeExpr1459);
/* 4410 */         stream_DOT.add(char_literal112);
/*      */ 
/* 4413 */         ID113 = (CommonToken)match(this.input, 25, FOLLOW_ID_in_includeExpr1461);
/* 4414 */         stream_ID.add(ID113);
/*      */ 
/* 4417 */         char_literal114 = (CommonToken)match(this.input, 14, FOLLOW_LPAREN_in_includeExpr1463);
/* 4418 */         stream_LPAREN.add(char_literal114);
/*      */ 
/* 4421 */         rp = (CommonToken)match(this.input, 15, FOLLOW_RPAREN_in_includeExpr1467);
/* 4422 */         stream_RPAREN.add(rp);
/*      */ 
/* 4432 */         retval.tree = root_0;
/* 4433 */         RewriteRuleSubtreeStream stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.tree : null);
/*      */ 
/* 4435 */         root_0 = (CommonTree)this.adaptor.nil();
/*      */ 
/* 4440 */         CommonTree root_1 = (CommonTree)this.adaptor.nil();
/* 4441 */         root_1 = (CommonTree)this.adaptor.becomeRoot((CommonTree)this.adaptor.create(46, "INCLUDE_SUPER_REGION"), root_1);
/*      */ 
/* 4445 */         this.adaptor.addChild(root_1, stream_ID.nextNode());
/*      */ 
/* 4449 */         this.adaptor.addChild(root_0, root_1);
/*      */ 
/* 4455 */         retval.tree = root_0;
/*      */ 
/* 4458 */         break;
/*      */       case 5:
/* 4462 */         char_literal115 = (CommonToken)match(this.input, 33, FOLLOW_AT_in_includeExpr1482);
/* 4463 */         stream_AT.add(char_literal115);
/*      */ 
/* 4466 */         ID116 = (CommonToken)match(this.input, 25, FOLLOW_ID_in_includeExpr1484);
/* 4467 */         stream_ID.add(ID116);
/*      */ 
/* 4470 */         char_literal117 = (CommonToken)match(this.input, 14, FOLLOW_LPAREN_in_includeExpr1486);
/* 4471 */         stream_LPAREN.add(char_literal117);
/*      */ 
/* 4474 */         rp = (CommonToken)match(this.input, 15, FOLLOW_RPAREN_in_includeExpr1490);
/* 4475 */         stream_RPAREN.add(rp);
/*      */ 
/* 4485 */         retval.tree = root_0;
/* 4486 */         RewriteRuleSubtreeStream stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.tree : null);
/*      */ 
/* 4488 */         root_0 = (CommonTree)this.adaptor.nil();
/*      */ 
/* 4493 */         CommonTree root_1 = (CommonTree)this.adaptor.nil();
/* 4494 */         root_1 = (CommonTree)this.adaptor.becomeRoot((CommonTree)this.adaptor.create(44, "INCLUDE_REGION"), root_1);
/*      */ 
/* 4498 */         this.adaptor.addChild(root_1, stream_ID.nextNode());
/*      */ 
/* 4502 */         this.adaptor.addChild(root_0, root_1);
/*      */ 
/* 4508 */         retval.tree = root_0;
/*      */ 
/* 4511 */         break;
/*      */       case 6:
/* 4515 */         root_0 = (CommonTree)this.adaptor.nil();
/*      */ 
/* 4518 */         pushFollow(FOLLOW_primary_in_includeExpr1508);
/* 4519 */         primary118 = primary();
/*      */ 
/* 4521 */         this.state._fsp -= 1;
/*      */ 
/* 4523 */         this.adaptor.addChild(root_0, primary118.getTree());
/*      */       }
/*      */ 
/* 4529 */       retval.stop = this.input.LT(-1);
/*      */ 
/* 4532 */       retval.tree = ((CommonTree)this.adaptor.rulePostProcessing(root_0));
/* 4533 */       this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
/*      */     }
/*      */     catch (RecognitionException re)
/*      */     {
/* 4537 */       re = 
/* 4541 */         re;
/*      */ 
/* 4537 */       throw re;
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/* 4542 */     return retval;
/*      */   }
/*      */ 
/*      */   public final primary_return primary()
/*      */     throws RecognitionException
/*      */   {
/* 4556 */     primary_return retval = new primary_return();
/* 4557 */     retval.start = this.input.LT(1);
/*      */ 
/* 4560 */     CommonTree root_0 = null;
/*      */ 
/* 4562 */     CommonToken lp = null;
/* 4563 */     CommonToken ID119 = null;
/* 4564 */     CommonToken STRING120 = null;
/* 4565 */     CommonToken TRUE121 = null;
/* 4566 */     CommonToken FALSE122 = null;
/* 4567 */     CommonToken char_literal125 = null;
/* 4568 */     CommonToken char_literal127 = null;
/* 4569 */     CommonToken char_literal129 = null;
/* 4570 */     CommonToken char_literal130 = null;
/* 4571 */     CommonToken char_literal132 = null;
/* 4572 */     subtemplate_return subtemplate123 = null;
/*      */ 
/* 4574 */     list_return list124 = null;
/*      */ 
/* 4576 */     conditional_return conditional126 = null;
/*      */ 
/* 4578 */     expr_return expr128 = null;
/*      */ 
/* 4580 */     argExprList_return argExprList131 = null;
/*      */ 
/* 4583 */     CommonTree lp_tree = null;
/* 4584 */     CommonTree ID119_tree = null;
/* 4585 */     CommonTree STRING120_tree = null;
/* 4586 */     CommonTree TRUE121_tree = null;
/* 4587 */     CommonTree FALSE122_tree = null;
/* 4588 */     CommonTree char_literal125_tree = null;
/* 4589 */     CommonTree char_literal127_tree = null;
/* 4590 */     CommonTree char_literal129_tree = null;
/* 4591 */     CommonTree char_literal130_tree = null;
/* 4592 */     CommonTree char_literal132_tree = null;
/* 4593 */     RewriteRuleTokenStream stream_RPAREN = new RewriteRuleTokenStream(this.adaptor, "token RPAREN");
/* 4594 */     RewriteRuleTokenStream stream_LPAREN = new RewriteRuleTokenStream(this.adaptor, "token LPAREN");
/* 4595 */     RewriteRuleSubtreeStream stream_argExprList = new RewriteRuleSubtreeStream(this.adaptor, "rule argExprList");
/* 4596 */     RewriteRuleSubtreeStream stream_expr = new RewriteRuleSubtreeStream(this.adaptor, "rule expr");
/*      */     try
/*      */     {
/* 4599 */       int alt38 = 8;
/* 4600 */       int LA38_0 = this.input.LA(1);
/*      */ 
/* 4602 */       if (LA38_0 == 25) {
/* 4603 */         alt38 = 1;
/*      */       }
/* 4605 */       else if (LA38_0 == 26) {
/* 4606 */         alt38 = 2;
/*      */       }
/* 4608 */       else if (LA38_0 == 35) {
/* 4609 */         alt38 = 3;
/*      */       }
/* 4611 */       else if (LA38_0 == 36) {
/* 4612 */         alt38 = 4;
/*      */       }
/* 4614 */       else if (LA38_0 == 20) {
/* 4615 */         alt38 = 5;
/*      */       }
/* 4617 */       else if (LA38_0 == 16) {
/* 4618 */         alt38 = 6;
/*      */       }
/* 4620 */       else if ((LA38_0 == 14) && ((this.conditional_stack.size() == 0) || (this.conditional_stack.size() > 0))) {
/* 4621 */         int LA38_7 = this.input.LA(2);
/*      */ 
/* 4623 */         if (this.conditional_stack.size() > 0) {
/* 4624 */           alt38 = 7;
/*      */         }
/* 4626 */         else if (this.conditional_stack.size() == 0) {
/* 4627 */           alt38 = 8;
/*      */         }
/*      */         else {
/* 4630 */           NoViableAltException nvae = new NoViableAltException("", 38, 7, this.input);
/*      */ 
/* 4633 */           throw nvae;
/*      */         }
/*      */       }
/*      */       else
/*      */       {
/* 4638 */         NoViableAltException nvae = new NoViableAltException("", 38, 0, this.input);
/*      */ 
/* 4641 */         throw nvae;
/*      */       }
/*      */ 
/* 4644 */       switch (alt38)
/*      */       {
/*      */       case 1:
/* 4648 */         root_0 = (CommonTree)this.adaptor.nil();
/*      */ 
/* 4651 */         ID119 = (CommonToken)match(this.input, 25, FOLLOW_ID_in_primary1519);
/* 4652 */         ID119_tree = (CommonTree)this.adaptor.create(ID119);
/*      */ 
/* 4655 */         this.adaptor.addChild(root_0, ID119_tree);
/*      */ 
/* 4659 */         break;
/*      */       case 2:
/* 4663 */         root_0 = (CommonTree)this.adaptor.nil();
/*      */ 
/* 4666 */         STRING120 = (CommonToken)match(this.input, 26, FOLLOW_STRING_in_primary1524);
/* 4667 */         STRING120_tree = (CommonTree)this.adaptor.create(STRING120);
/*      */ 
/* 4670 */         this.adaptor.addChild(root_0, STRING120_tree);
/*      */ 
/* 4674 */         break;
/*      */       case 3:
/* 4678 */         root_0 = (CommonTree)this.adaptor.nil();
/*      */ 
/* 4681 */         TRUE121 = (CommonToken)match(this.input, 35, FOLLOW_TRUE_in_primary1529);
/* 4682 */         TRUE121_tree = (CommonTree)this.adaptor.create(TRUE121);
/*      */ 
/* 4685 */         this.adaptor.addChild(root_0, TRUE121_tree);
/*      */ 
/* 4689 */         break;
/*      */       case 4:
/* 4693 */         root_0 = (CommonTree)this.adaptor.nil();
/*      */ 
/* 4696 */         FALSE122 = (CommonToken)match(this.input, 36, FOLLOW_FALSE_in_primary1534);
/* 4697 */         FALSE122_tree = (CommonTree)this.adaptor.create(FALSE122);
/*      */ 
/* 4700 */         this.adaptor.addChild(root_0, FALSE122_tree);
/*      */ 
/* 4704 */         break;
/*      */       case 5:
/* 4708 */         root_0 = (CommonTree)this.adaptor.nil();
/*      */ 
/* 4711 */         pushFollow(FOLLOW_subtemplate_in_primary1539);
/* 4712 */         subtemplate123 = subtemplate();
/*      */ 
/* 4714 */         this.state._fsp -= 1;
/*      */ 
/* 4716 */         this.adaptor.addChild(root_0, subtemplate123.getTree());
/*      */ 
/* 4719 */         break;
/*      */       case 6:
/* 4723 */         root_0 = (CommonTree)this.adaptor.nil();
/*      */ 
/* 4726 */         pushFollow(FOLLOW_list_in_primary1544);
/* 4727 */         list124 = list();
/*      */ 
/* 4729 */         this.state._fsp -= 1;
/*      */ 
/* 4731 */         this.adaptor.addChild(root_0, list124.getTree());
/*      */ 
/* 4734 */         break;
/*      */       case 7:
/* 4738 */         root_0 = (CommonTree)this.adaptor.nil();
/*      */ 
/* 4741 */         if (this.conditional_stack.size() <= 0) {
/* 4742 */           throw new FailedPredicateException(this.input, "primary", "$conditional.size()>0");
/*      */         }
/*      */ 
/* 4745 */         char_literal125 = (CommonToken)match(this.input, 14, FOLLOW_LPAREN_in_primary1553);
/*      */ 
/* 4747 */         pushFollow(FOLLOW_conditional_in_primary1556);
/* 4748 */         conditional126 = conditional();
/*      */ 
/* 4750 */         this.state._fsp -= 1;
/*      */ 
/* 4752 */         this.adaptor.addChild(root_0, conditional126.getTree());
/*      */ 
/* 4754 */         char_literal127 = (CommonToken)match(this.input, 15, FOLLOW_RPAREN_in_primary1558);
/*      */ 
/* 4757 */         break;
/*      */       case 8:
/* 4761 */         if (this.conditional_stack.size() != 0) {
/* 4762 */           throw new FailedPredicateException(this.input, "primary", "$conditional.size()==0");
/*      */         }
/*      */ 
/* 4765 */         lp = (CommonToken)match(this.input, 14, FOLLOW_LPAREN_in_primary1569);
/* 4766 */         stream_LPAREN.add(lp);
/*      */ 
/* 4769 */         pushFollow(FOLLOW_expr_in_primary1571);
/* 4770 */         expr128 = expr();
/*      */ 
/* 4772 */         this.state._fsp -= 1;
/*      */ 
/* 4774 */         stream_expr.add(expr128.getTree());
/*      */ 
/* 4776 */         char_literal129 = (CommonToken)match(this.input, 15, FOLLOW_RPAREN_in_primary1573);
/* 4777 */         stream_RPAREN.add(char_literal129);
/*      */ 
/* 4781 */         int alt37 = 2;
/* 4782 */         int LA37_0 = this.input.LA(1);
/*      */ 
/* 4784 */         if (LA37_0 == 14) {
/* 4785 */           alt37 = 1;
/*      */         }
/* 4787 */         else if ((LA37_0 == 9) || (LA37_0 == 13) || (LA37_0 == 15) || ((LA37_0 >= 17) && (LA37_0 <= 19)) || (LA37_0 == 24) || ((LA37_0 >= 29) && (LA37_0 <= 30))) {
/* 4788 */           alt37 = 2;
/*      */         }
/*      */         else {
/* 4791 */           NoViableAltException nvae = new NoViableAltException("", 37, 0, this.input);
/*      */ 
/* 4794 */           throw nvae;
/*      */         }
/*      */ 
/* 4797 */         switch (alt37)
/*      */         {
/*      */         case 1:
/* 4801 */           char_literal130 = (CommonToken)match(this.input, 14, FOLLOW_LPAREN_in_primary1579);
/* 4802 */           stream_LPAREN.add(char_literal130);
/*      */ 
/* 4806 */           int alt36 = 2;
/* 4807 */           int LA36_0 = this.input.LA(1);
/*      */ 
/* 4809 */           if ((LA36_0 == 8) || (LA36_0 == 16) || (LA36_0 == 20) || ((LA36_0 >= 25) && (LA36_0 <= 26)) || (LA36_0 == 33) || ((LA36_0 >= 35) && (LA36_0 <= 36))) {
/* 4810 */             alt36 = 1;
/*      */           }
/* 4812 */           else if ((LA36_0 == 14) && ((this.conditional_stack.size() == 0) || (this.conditional_stack.size() > 0))) {
/* 4813 */             alt36 = 1;
/*      */           }
/* 4815 */           switch (alt36)
/*      */           {
/*      */           case 1:
/* 4819 */             pushFollow(FOLLOW_argExprList_in_primary1581);
/* 4820 */             argExprList131 = argExprList();
/*      */ 
/* 4822 */             this.state._fsp -= 1;
/*      */ 
/* 4824 */             stream_argExprList.add(argExprList131.getTree());
/*      */           }
/*      */ 
/* 4832 */           char_literal132 = (CommonToken)match(this.input, 15, FOLLOW_RPAREN_in_primary1584);
/* 4833 */           stream_RPAREN.add(char_literal132);
/*      */ 
/* 4843 */           retval.tree = root_0;
/* 4844 */           RewriteRuleSubtreeStream stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.tree : null);
/*      */ 
/* 4846 */           root_0 = (CommonTree)this.adaptor.nil();
/*      */ 
/* 4851 */           CommonTree root_1 = (CommonTree)this.adaptor.nil();
/* 4852 */           root_1 = (CommonTree)this.adaptor.becomeRoot((CommonTree)this.adaptor.create(43, lp), root_1);
/*      */ 
/* 4856 */           this.adaptor.addChild(root_1, stream_expr.nextTree());
/*      */ 
/* 4859 */           if (stream_argExprList.hasNext()) {
/* 4860 */             this.adaptor.addChild(root_1, stream_argExprList.nextTree());
/*      */           }
/*      */ 
/* 4863 */           stream_argExprList.reset();
/*      */ 
/* 4865 */           this.adaptor.addChild(root_0, root_1);
/*      */ 
/* 4871 */           retval.tree = root_0;
/*      */ 
/* 4874 */           break;
/*      */         case 2:
/* 4885 */           retval.tree = root_0;
/* 4886 */           RewriteRuleSubtreeStream stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.tree : null);
/*      */ 
/* 4888 */           root_0 = (CommonTree)this.adaptor.nil();
/*      */ 
/* 4893 */           CommonTree root_1 = (CommonTree)this.adaptor.nil();
/* 4894 */           root_1 = (CommonTree)this.adaptor.becomeRoot((CommonTree)this.adaptor.create(56, lp), root_1);
/*      */ 
/* 4898 */           this.adaptor.addChild(root_1, stream_expr.nextTree());
/*      */ 
/* 4900 */           this.adaptor.addChild(root_0, root_1);
/*      */ 
/* 4906 */           retval.tree = root_0;
/*      */         }
/*      */ 
/*      */         break;
/*      */       }
/*      */ 
/* 4918 */       retval.stop = this.input.LT(-1);
/*      */ 
/* 4921 */       retval.tree = ((CommonTree)this.adaptor.rulePostProcessing(root_0));
/* 4922 */       this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
/*      */     }
/*      */     catch (RecognitionException re)
/*      */     {
/* 4926 */       re = 
/* 4930 */         re;
/*      */ 
/* 4926 */       throw re;
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/* 4931 */     return retval;
/*      */   }
/*      */ 
/*      */   public final args_return args()
/*      */     throws RecognitionException
/*      */   {
/* 4945 */     args_return retval = new args_return();
/* 4946 */     retval.start = this.input.LT(1);
/*      */ 
/* 4949 */     CommonTree root_0 = null;
/*      */ 
/* 4951 */     CommonToken char_literal135 = null;
/* 4952 */     CommonToken char_literal137 = null;
/* 4953 */     CommonToken string_literal138 = null;
/* 4954 */     CommonToken string_literal139 = null;
/* 4955 */     argExprList_return argExprList133 = null;
/*      */ 
/* 4957 */     namedArg_return namedArg134 = null;
/*      */ 
/* 4959 */     namedArg_return namedArg136 = null;
/*      */ 
/* 4962 */     CommonTree char_literal135_tree = null;
/* 4963 */     CommonTree char_literal137_tree = null;
/* 4964 */     CommonTree string_literal138_tree = null;
/* 4965 */     CommonTree string_literal139_tree = null;
/* 4966 */     RewriteRuleTokenStream stream_ELLIPSIS = new RewriteRuleTokenStream(this.adaptor, "token ELLIPSIS");
/* 4967 */     RewriteRuleTokenStream stream_COMMA = new RewriteRuleTokenStream(this.adaptor, "token COMMA");
/* 4968 */     RewriteRuleSubtreeStream stream_namedArg = new RewriteRuleSubtreeStream(this.adaptor, "rule namedArg");
/*      */     try
/*      */     {
/* 4971 */       int alt41 = 4;
/* 4972 */       int LA41_0 = this.input.LA(1);
/*      */ 
/* 4974 */       if (LA41_0 == 25) {
/* 4975 */         int LA41_1 = this.input.LA(2);
/*      */ 
/* 4977 */         if (((LA41_1 >= 13) && (LA41_1 <= 15)) || ((LA41_1 >= 18) && (LA41_1 <= 19))) {
/* 4978 */           alt41 = 1;
/*      */         }
/* 4980 */         else if (LA41_1 == 12) {
/* 4981 */           alt41 = 2;
/*      */         }
/*      */         else {
/* 4984 */           NoViableAltException nvae = new NoViableAltException("", 41, 1, this.input);
/*      */ 
/* 4987 */           throw nvae;
/*      */         }
/*      */ 
/*      */       }
/* 4991 */       else if ((LA41_0 == 8) || (LA41_0 == 16) || (LA41_0 == 20) || (LA41_0 == 26) || (LA41_0 == 33) || ((LA41_0 >= 35) && (LA41_0 <= 36))) {
/* 4992 */         alt41 = 1;
/*      */       }
/* 4994 */       else if ((LA41_0 == 14) && ((this.conditional_stack.size() == 0) || (this.conditional_stack.size() > 0))) {
/* 4995 */         alt41 = 1;
/*      */       }
/* 4997 */       else if (LA41_0 == 11) {
/* 4998 */         alt41 = 3;
/*      */       }
/* 5000 */       else if (LA41_0 == 15) {
/* 5001 */         alt41 = 4;
/*      */       }
/*      */       else {
/* 5004 */         NoViableAltException nvae = new NoViableAltException("", 41, 0, this.input);
/*      */ 
/* 5007 */         throw nvae;
/*      */       }
/*      */ 
/* 5010 */       switch (alt41)
/*      */       {
/*      */       case 1:
/* 5014 */         root_0 = (CommonTree)this.adaptor.nil();
/*      */ 
/* 5017 */         pushFollow(FOLLOW_argExprList_in_args1640);
/* 5018 */         argExprList133 = argExprList();
/*      */ 
/* 5020 */         this.state._fsp -= 1;
/*      */ 
/* 5022 */         this.adaptor.addChild(root_0, argExprList133.getTree());
/*      */ 
/* 5025 */         break;
/*      */       case 2:
/* 5029 */         pushFollow(FOLLOW_namedArg_in_args1645);
/* 5030 */         namedArg134 = namedArg();
/*      */ 
/* 5032 */         this.state._fsp -= 1;
/*      */ 
/* 5034 */         stream_namedArg.add(namedArg134.getTree());
/*      */         while (true)
/*      */         {
/* 5039 */           int alt39 = 2;
/* 5040 */           int LA39_0 = this.input.LA(1);
/*      */ 
/* 5042 */           if (LA39_0 == 18) {
/* 5043 */             int LA39_1 = this.input.LA(2);
/*      */ 
/* 5045 */             if (LA39_1 == 25) {
/* 5046 */               alt39 = 1;
/*      */             }
/*      */ 
/*      */           }
/*      */ 
/* 5053 */           switch (alt39)
/*      */           {
/*      */           case 1:
/* 5057 */             char_literal135 = (CommonToken)match(this.input, 18, FOLLOW_COMMA_in_args1649);
/* 5058 */             stream_COMMA.add(char_literal135);
/*      */ 
/* 5061 */             pushFollow(FOLLOW_namedArg_in_args1651);
/* 5062 */             namedArg136 = namedArg();
/*      */ 
/* 5064 */             this.state._fsp -= 1;
/*      */ 
/* 5066 */             stream_namedArg.add(namedArg136.getTree());
/*      */ 
/* 5069 */             break;
/*      */           default:
/* 5072 */             break label600;
/*      */           }
/*      */ 
/*      */         }
/*      */ 
/* 5078 */         int alt40 = 2;
/* 5079 */         int LA40_0 = this.input.LA(1);
/*      */ 
/* 5081 */         if (LA40_0 == 18) {
/* 5082 */           alt40 = 1;
/*      */         }
/* 5084 */         switch (alt40)
/*      */         {
/*      */         case 1:
/* 5088 */           char_literal137 = (CommonToken)match(this.input, 18, FOLLOW_COMMA_in_args1657);
/* 5089 */           stream_COMMA.add(char_literal137);
/*      */ 
/* 5092 */           string_literal138 = (CommonToken)match(this.input, 11, FOLLOW_ELLIPSIS_in_args1659);
/* 5093 */           stream_ELLIPSIS.add(string_literal138);
/*      */         }
/*      */ 
/* 5109 */         retval.tree = root_0;
/* 5110 */         RewriteRuleSubtreeStream stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.tree : null);
/*      */ 
/* 5112 */         root_0 = (CommonTree)this.adaptor.nil();
/*      */ 
/* 5115 */         if (!stream_namedArg.hasNext()) {
/* 5116 */           throw new RewriteEarlyExitException();
/*      */         }
/* 5118 */         while (stream_namedArg.hasNext()) {
/* 5119 */           this.adaptor.addChild(root_0, stream_namedArg.nextTree());
/*      */         }
/*      */ 
/* 5122 */         stream_namedArg.reset();
/*      */ 
/* 5125 */         if (stream_ELLIPSIS.hasNext()) {
/* 5126 */           this.adaptor.addChild(root_0, stream_ELLIPSIS.nextNode());
/*      */         }
/*      */ 
/* 5131 */         stream_ELLIPSIS.reset();
/*      */ 
/* 5136 */         retval.tree = root_0;
/*      */ 
/* 5139 */         break;
/*      */       case 3:
/* 5143 */         root_0 = (CommonTree)this.adaptor.nil();
/*      */ 
/* 5146 */         string_literal139 = (CommonToken)match(this.input, 11, FOLLOW_ELLIPSIS_in_args1679);
/* 5147 */         string_literal139_tree = (CommonTree)this.adaptor.create(string_literal139);
/*      */ 
/* 5150 */         this.adaptor.addChild(root_0, string_literal139_tree);
/*      */ 
/* 5154 */         break;
/*      */       case 4:
/* 5158 */         label600: root_0 = (CommonTree)this.adaptor.nil();
/*      */       }
/*      */ 
/* 5165 */       retval.stop = this.input.LT(-1);
/*      */ 
/* 5168 */       retval.tree = ((CommonTree)this.adaptor.rulePostProcessing(root_0));
/* 5169 */       this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
/*      */     }
/*      */     catch (RecognitionException re)
/*      */     {
/* 5173 */       re = 
/* 5177 */         re;
/*      */ 
/* 5173 */       throw re;
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/* 5178 */     return retval;
/*      */   }
/*      */ 
/*      */   public final argExprList_return argExprList()
/*      */     throws RecognitionException
/*      */   {
/* 5192 */     argExprList_return retval = new argExprList_return();
/* 5193 */     retval.start = this.input.LT(1);
/*      */ 
/* 5196 */     CommonTree root_0 = null;
/*      */ 
/* 5198 */     CommonToken char_literal141 = null;
/* 5199 */     arg_return arg140 = null;
/*      */ 
/* 5201 */     arg_return arg142 = null;
/*      */ 
/* 5204 */     CommonTree char_literal141_tree = null;
/* 5205 */     RewriteRuleTokenStream stream_COMMA = new RewriteRuleTokenStream(this.adaptor, "token COMMA");
/* 5206 */     RewriteRuleSubtreeStream stream_arg = new RewriteRuleSubtreeStream(this.adaptor, "rule arg");
/*      */     try
/*      */     {
/* 5211 */       pushFollow(FOLLOW_arg_in_argExprList1692);
/* 5212 */       arg140 = arg();
/*      */ 
/* 5214 */       this.state._fsp -= 1;
/*      */ 
/* 5216 */       stream_arg.add(arg140.getTree());
/*      */       while (true)
/*      */       {
/* 5221 */         int alt42 = 2;
/* 5222 */         int LA42_0 = this.input.LA(1);
/*      */ 
/* 5224 */         if (LA42_0 == 18) {
/* 5225 */           alt42 = 1;
/*      */         }
/*      */ 
/* 5229 */         switch (alt42)
/*      */         {
/*      */         case 1:
/* 5233 */           char_literal141 = (CommonToken)match(this.input, 18, FOLLOW_COMMA_in_argExprList1696);
/* 5234 */           stream_COMMA.add(char_literal141);
/*      */ 
/* 5237 */           pushFollow(FOLLOW_arg_in_argExprList1698);
/* 5238 */           arg142 = arg();
/*      */ 
/* 5240 */           this.state._fsp -= 1;
/*      */ 
/* 5242 */           stream_arg.add(arg142.getTree());
/*      */ 
/* 5245 */           break;
/*      */         default:
/* 5248 */           break label216;
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/* 5260 */       label216: retval.tree = root_0;
/* 5261 */       RewriteRuleSubtreeStream stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.tree : null);
/*      */ 
/* 5263 */       root_0 = (CommonTree)this.adaptor.nil();
/*      */ 
/* 5266 */       if (!stream_arg.hasNext()) {
/* 5267 */         throw new RewriteEarlyExitException();
/*      */       }
/* 5269 */       while (stream_arg.hasNext()) {
/* 5270 */         this.adaptor.addChild(root_0, stream_arg.nextTree());
/*      */       }
/*      */ 
/* 5273 */       stream_arg.reset();
/*      */ 
/* 5278 */       retval.tree = root_0;
/*      */ 
/* 5282 */       retval.stop = this.input.LT(-1);
/*      */ 
/* 5285 */       retval.tree = ((CommonTree)this.adaptor.rulePostProcessing(root_0));
/* 5286 */       this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
/*      */     }
/*      */     catch (RecognitionException re)
/*      */     {
/* 5290 */       re = 
/* 5294 */         re;
/*      */ 
/* 5290 */       throw re;
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/* 5295 */     return retval;
/*      */   }
/*      */ 
/*      */   public final arg_return arg()
/*      */     throws RecognitionException
/*      */   {
/* 5309 */     arg_return retval = new arg_return();
/* 5310 */     retval.start = this.input.LT(1);
/*      */ 
/* 5313 */     CommonTree root_0 = null;
/*      */ 
/* 5315 */     exprNoComma_return exprNoComma143 = null;
/*      */     try
/*      */     {
/* 5323 */       root_0 = (CommonTree)this.adaptor.nil();
/*      */ 
/* 5326 */       pushFollow(FOLLOW_exprNoComma_in_arg1715);
/* 5327 */       exprNoComma143 = exprNoComma();
/*      */ 
/* 5329 */       this.state._fsp -= 1;
/*      */ 
/* 5331 */       this.adaptor.addChild(root_0, exprNoComma143.getTree());
/*      */ 
/* 5335 */       retval.stop = this.input.LT(-1);
/*      */ 
/* 5338 */       retval.tree = ((CommonTree)this.adaptor.rulePostProcessing(root_0));
/* 5339 */       this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
/*      */     }
/*      */     catch (RecognitionException re)
/*      */     {
/* 5343 */       re = 
/* 5347 */         re;
/*      */ 
/* 5343 */       throw re;
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/* 5348 */     return retval;
/*      */   }
/*      */ 
/*      */   public final namedArg_return namedArg()
/*      */     throws RecognitionException
/*      */   {
/* 5362 */     namedArg_return retval = new namedArg_return();
/* 5363 */     retval.start = this.input.LT(1);
/*      */ 
/* 5366 */     CommonTree root_0 = null;
/*      */ 
/* 5368 */     CommonToken ID144 = null;
/* 5369 */     CommonToken char_literal145 = null;
/* 5370 */     arg_return arg146 = null;
/*      */ 
/* 5373 */     CommonTree ID144_tree = null;
/* 5374 */     CommonTree char_literal145_tree = null;
/* 5375 */     RewriteRuleTokenStream stream_EQUALS = new RewriteRuleTokenStream(this.adaptor, "token EQUALS");
/* 5376 */     RewriteRuleTokenStream stream_ID = new RewriteRuleTokenStream(this.adaptor, "token ID");
/* 5377 */     RewriteRuleSubtreeStream stream_arg = new RewriteRuleSubtreeStream(this.adaptor, "rule arg");
/*      */     try
/*      */     {
/* 5382 */       ID144 = (CommonToken)match(this.input, 25, FOLLOW_ID_in_namedArg1724);
/* 5383 */       stream_ID.add(ID144);
/*      */ 
/* 5386 */       char_literal145 = (CommonToken)match(this.input, 12, FOLLOW_EQUALS_in_namedArg1726);
/* 5387 */       stream_EQUALS.add(char_literal145);
/*      */ 
/* 5390 */       pushFollow(FOLLOW_arg_in_namedArg1728);
/* 5391 */       arg146 = arg();
/*      */ 
/* 5393 */       this.state._fsp -= 1;
/*      */ 
/* 5395 */       stream_arg.add(arg146.getTree());
/*      */ 
/* 5404 */       retval.tree = root_0;
/* 5405 */       RewriteRuleSubtreeStream stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.tree : null);
/*      */ 
/* 5407 */       root_0 = (CommonTree)this.adaptor.nil();
/*      */ 
/* 5412 */       CommonTree root_1 = (CommonTree)this.adaptor.nil();
/* 5413 */       root_1 = (CommonTree)this.adaptor.becomeRoot(stream_EQUALS.nextNode(), root_1);
/*      */ 
/* 5417 */       this.adaptor.addChild(root_1, stream_ID.nextNode());
/*      */ 
/* 5421 */       this.adaptor.addChild(root_1, stream_arg.nextTree());
/*      */ 
/* 5423 */       this.adaptor.addChild(root_0, root_1);
/*      */ 
/* 5429 */       retval.tree = root_0;
/*      */ 
/* 5433 */       retval.stop = this.input.LT(-1);
/*      */ 
/* 5436 */       retval.tree = ((CommonTree)this.adaptor.rulePostProcessing(root_0));
/* 5437 */       this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
/*      */     }
/*      */     catch (RecognitionException re)
/*      */     {
/* 5441 */       re = 
/* 5445 */         re;
/*      */ 
/* 5441 */       throw re;
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/* 5446 */     return retval;
/*      */   }
/*      */ 
/*      */   public final list_return list()
/*      */     throws RecognitionException
/*      */   {
/* 5460 */     list_return retval = new list_return();
/* 5461 */     retval.start = this.input.LT(1);
/*      */ 
/* 5464 */     CommonTree root_0 = null;
/*      */ 
/* 5466 */     CommonToken lb = null;
/* 5467 */     CommonToken char_literal147 = null;
/* 5468 */     CommonToken char_literal149 = null;
/* 5469 */     CommonToken char_literal151 = null;
/* 5470 */     listElement_return listElement148 = null;
/*      */ 
/* 5472 */     listElement_return listElement150 = null;
/*      */ 
/* 5475 */     CommonTree lb_tree = null;
/* 5476 */     CommonTree char_literal147_tree = null;
/* 5477 */     CommonTree char_literal149_tree = null;
/* 5478 */     CommonTree char_literal151_tree = null;
/* 5479 */     RewriteRuleTokenStream stream_RBRACK = new RewriteRuleTokenStream(this.adaptor, "token RBRACK");
/* 5480 */     RewriteRuleTokenStream stream_LBRACK = new RewriteRuleTokenStream(this.adaptor, "token LBRACK");
/* 5481 */     RewriteRuleTokenStream stream_COMMA = new RewriteRuleTokenStream(this.adaptor, "token COMMA");
/* 5482 */     RewriteRuleSubtreeStream stream_listElement = new RewriteRuleSubtreeStream(this.adaptor, "rule listElement");
/*      */     try
/*      */     {
/* 5485 */       int alt44 = 2;
/* 5486 */       int LA44_0 = this.input.LA(1);
/*      */ 
/* 5488 */       if (LA44_0 == 16) {
/* 5489 */         int LA44_1 = this.input.LA(2);
/*      */ 
/* 5491 */         if (LA44_1 == 17) {
/* 5492 */           int LA44_2 = this.input.LA(3);
/*      */ 
/* 5494 */           if (this.input.LA(2) == 17) {
/* 5495 */             alt44 = 1;
/*      */           }
/*      */           else {
/* 5498 */             alt44 = 2;
/*      */           }
/*      */ 
/*      */         }
/* 5508 */         else if ((LA44_1 == 8) || (LA44_1 == 14) || (LA44_1 == 16) || (LA44_1 == 18) || (LA44_1 == 20) || ((LA44_1 >= 25) && (LA44_1 <= 26)) || (LA44_1 == 33) || ((LA44_1 >= 35) && (LA44_1 <= 36))) {
/* 5509 */           alt44 = 2;
/*      */         }
/*      */         else {
/* 5512 */           NoViableAltException nvae = new NoViableAltException("", 44, 1, this.input);
/*      */ 
/* 5515 */           throw nvae;
/*      */         }
/*      */       }
/*      */       else
/*      */       {
/* 5520 */         NoViableAltException nvae = new NoViableAltException("", 44, 0, this.input);
/*      */ 
/* 5523 */         throw nvae;
/*      */       }
/*      */ 
/* 5526 */       switch (alt44)
/*      */       {
/*      */       case 1:
/* 5530 */         if (this.input.LA(2) != 17) {
/* 5531 */           throw new FailedPredicateException(this.input, "list", "input.LA(2)==RBRACK");
/*      */         }
/*      */ 
/* 5534 */         lb = (CommonToken)match(this.input, 16, FOLLOW_LBRACK_in_list1753);
/* 5535 */         stream_LBRACK.add(lb);
/*      */ 
/* 5538 */         char_literal147 = (CommonToken)match(this.input, 17, FOLLOW_RBRACK_in_list1755);
/* 5539 */         stream_RBRACK.add(char_literal147);
/*      */ 
/* 5549 */         retval.tree = root_0;
/* 5550 */         RewriteRuleSubtreeStream stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.tree : null);
/*      */ 
/* 5552 */         root_0 = (CommonTree)this.adaptor.nil();
/*      */ 
/* 5555 */         this.adaptor.addChild(root_0, (CommonTree)this.adaptor.create(48, lb));
/*      */ 
/* 5562 */         retval.tree = root_0;
/*      */ 
/* 5565 */         break;
/*      */       case 2:
/* 5569 */         lb = (CommonToken)match(this.input, 16, FOLLOW_LBRACK_in_list1767);
/* 5570 */         stream_LBRACK.add(lb);
/*      */ 
/* 5573 */         pushFollow(FOLLOW_listElement_in_list1769);
/* 5574 */         listElement148 = listElement();
/*      */ 
/* 5576 */         this.state._fsp -= 1;
/*      */ 
/* 5578 */         stream_listElement.add(listElement148.getTree());
/*      */         while (true)
/*      */         {
/* 5583 */           int alt43 = 2;
/* 5584 */           int LA43_0 = this.input.LA(1);
/*      */ 
/* 5586 */           if (LA43_0 == 18) {
/* 5587 */             alt43 = 1;
/*      */           }
/*      */ 
/* 5591 */           switch (alt43)
/*      */           {
/*      */           case 1:
/* 5595 */             char_literal149 = (CommonToken)match(this.input, 18, FOLLOW_COMMA_in_list1773);
/* 5596 */             stream_COMMA.add(char_literal149);
/*      */ 
/* 5599 */             pushFollow(FOLLOW_listElement_in_list1775);
/* 5600 */             listElement150 = listElement();
/*      */ 
/* 5602 */             this.state._fsp -= 1;
/*      */ 
/* 5604 */             stream_listElement.add(listElement150.getTree());
/*      */ 
/* 5607 */             break;
/*      */           default:
/* 5610 */             break label678;
/*      */           }
/*      */ 
/*      */         }
/*      */ 
/* 5615 */         label678: char_literal151 = (CommonToken)match(this.input, 17, FOLLOW_RBRACK_in_list1780);
/* 5616 */         stream_RBRACK.add(char_literal151);
/*      */ 
/* 5626 */         retval.tree = root_0;
/* 5627 */         RewriteRuleSubtreeStream stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.tree : null);
/*      */ 
/* 5629 */         root_0 = (CommonTree)this.adaptor.nil();
/*      */ 
/* 5634 */         CommonTree root_1 = (CommonTree)this.adaptor.nil();
/* 5635 */         root_1 = (CommonTree)this.adaptor.becomeRoot((CommonTree)this.adaptor.create(48, lb), root_1);
/*      */ 
/* 5640 */         while (stream_listElement.hasNext()) {
/* 5641 */           this.adaptor.addChild(root_1, stream_listElement.nextTree());
/*      */         }
/*      */ 
/* 5644 */         stream_listElement.reset();
/*      */ 
/* 5646 */         this.adaptor.addChild(root_0, root_1);
/*      */ 
/* 5652 */         retval.tree = root_0;
/*      */       }
/*      */ 
/* 5658 */       retval.stop = this.input.LT(-1);
/*      */ 
/* 5661 */       retval.tree = ((CommonTree)this.adaptor.rulePostProcessing(root_0));
/* 5662 */       this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
/*      */     }
/*      */     catch (RecognitionException re)
/*      */     {
/* 5666 */       re = 
/* 5670 */         re;
/*      */ 
/* 5666 */       throw re;
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/* 5671 */     return retval;
/*      */   }
/*      */ 
/*      */   public final listElement_return listElement()
/*      */     throws RecognitionException
/*      */   {
/* 5685 */     listElement_return retval = new listElement_return();
/* 5686 */     retval.start = this.input.LT(1);
/*      */ 
/* 5689 */     CommonTree root_0 = null;
/*      */ 
/* 5691 */     exprNoComma_return exprNoComma152 = null;
/*      */     try
/*      */     {
/* 5697 */       int alt45 = 2;
/* 5698 */       int LA45_0 = this.input.LA(1);
/*      */ 
/* 5700 */       if ((LA45_0 == 8) || (LA45_0 == 16) || (LA45_0 == 20) || ((LA45_0 >= 25) && (LA45_0 <= 26)) || (LA45_0 == 33) || ((LA45_0 >= 35) && (LA45_0 <= 36))) {
/* 5701 */         alt45 = 1;
/*      */       }
/* 5703 */       else if ((LA45_0 == 14) && ((this.conditional_stack.size() == 0) || (this.conditional_stack.size() > 0))) {
/* 5704 */         alt45 = 1;
/*      */       }
/* 5706 */       else if ((LA45_0 >= 17) && (LA45_0 <= 18)) {
/* 5707 */         alt45 = 2;
/*      */       }
/*      */       else {
/* 5710 */         NoViableAltException nvae = new NoViableAltException("", 45, 0, this.input);
/*      */ 
/* 5713 */         throw nvae;
/*      */       }
/*      */ 
/* 5716 */       switch (alt45)
/*      */       {
/*      */       case 1:
/* 5720 */         root_0 = (CommonTree)this.adaptor.nil();
/*      */ 
/* 5723 */         pushFollow(FOLLOW_exprNoComma_in_listElement1800);
/* 5724 */         exprNoComma152 = exprNoComma();
/*      */ 
/* 5726 */         this.state._fsp -= 1;
/*      */ 
/* 5728 */         this.adaptor.addChild(root_0, exprNoComma152.getTree());
/*      */ 
/* 5731 */         break;
/*      */       case 2:
/* 5742 */         retval.tree = root_0;
/* 5743 */         RewriteRuleSubtreeStream stream_retval = new RewriteRuleSubtreeStream(this.adaptor, "rule retval", retval != null ? retval.tree : null);
/*      */ 
/* 5745 */         root_0 = (CommonTree)this.adaptor.nil();
/*      */ 
/* 5748 */         this.adaptor.addChild(root_0, (CommonTree)this.adaptor.create(50, "NULL"));
/*      */ 
/* 5755 */         retval.tree = root_0;
/*      */       }
/*      */ 
/* 5761 */       retval.stop = this.input.LT(-1);
/*      */ 
/* 5764 */       retval.tree = ((CommonTree)this.adaptor.rulePostProcessing(root_0));
/* 5765 */       this.adaptor.setTokenBoundaries(retval.tree, retval.start, retval.stop);
/*      */     }
/*      */     catch (RecognitionException re)
/*      */     {
/* 5769 */       re = 
/* 5773 */         re;
/*      */ 
/* 5769 */       throw re;
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/* 5774 */     return retval;
/*      */   }
/*      */ 
/*      */   public static class listElement_return extends ParserRuleReturnScope
/*      */   {
/*      */     CommonTree tree;
/*      */ 
/*      */     public Object getTree()
/*      */     {
/* 5678 */       return this.tree;
/*      */     }
/*      */   }
/*      */ 
/*      */   public static class list_return extends ParserRuleReturnScope
/*      */   {
/*      */     CommonTree tree;
/*      */ 
/*      */     public Object getTree()
/*      */     {
/* 5453 */       return this.tree;
/*      */     }
/*      */   }
/*      */ 
/*      */   public static class namedArg_return extends ParserRuleReturnScope
/*      */   {
/*      */     CommonTree tree;
/*      */ 
/*      */     public Object getTree()
/*      */     {
/* 5355 */       return this.tree;
/*      */     }
/*      */   }
/*      */ 
/*      */   public static class arg_return extends ParserRuleReturnScope
/*      */   {
/*      */     CommonTree tree;
/*      */ 
/*      */     public Object getTree()
/*      */     {
/* 5302 */       return this.tree;
/*      */     }
/*      */   }
/*      */ 
/*      */   public static class argExprList_return extends ParserRuleReturnScope
/*      */   {
/*      */     CommonTree tree;
/*      */ 
/*      */     public Object getTree()
/*      */     {
/* 5185 */       return this.tree;
/*      */     }
/*      */   }
/*      */ 
/*      */   public static class args_return extends ParserRuleReturnScope
/*      */   {
/*      */     CommonTree tree;
/*      */ 
/*      */     public Object getTree()
/*      */     {
/* 4938 */       return this.tree;
/*      */     }
/*      */   }
/*      */ 
/*      */   public static class primary_return extends ParserRuleReturnScope
/*      */   {
/*      */     CommonTree tree;
/*      */ 
/*      */     public Object getTree()
/*      */     {
/* 4549 */       return this.tree;
/*      */     }
/*      */   }
/*      */ 
/*      */   public static class includeExpr_return extends ParserRuleReturnScope
/*      */   {
/*      */     CommonTree tree;
/*      */ 
/*      */     public Object getTree()
/*      */     {
/* 4037 */       return this.tree;
/*      */     }
/*      */   }
/*      */ 
/*      */   public static class memberExpr_return extends ParserRuleReturnScope
/*      */   {
/*      */     CommonTree tree;
/*      */ 
/*      */     public Object getTree()
/*      */     {
/* 3815 */       return this.tree;
/*      */     }
/*      */   }
/*      */ 
/*      */   public static class mapTemplateRef_return extends ParserRuleReturnScope
/*      */   {
/*      */     CommonTree tree;
/*      */ 
/*      */     public Object getTree()
/*      */     {
/* 3553 */       return this.tree;
/*      */     }
/*      */   }
/*      */ 
/*      */   public static class mapExpr_return extends ParserRuleReturnScope
/*      */   {
/*      */     CommonTree tree;
/*      */ 
/*      */     public Object getTree()
/*      */     {
/* 3216 */       return this.tree;
/*      */     }
/*      */   }
/*      */ 
/*      */   public static class expr_return extends ParserRuleReturnScope
/*      */   {
/*      */     CommonTree tree;
/*      */ 
/*      */     public Object getTree()
/*      */     {
/* 3163 */       return this.tree;
/*      */     }
/*      */   }
/*      */ 
/*      */   public static class exprNoComma_return extends ParserRuleReturnScope
/*      */   {
/*      */     CommonTree tree;
/*      */ 
/*      */     public Object getTree()
/*      */     {
/* 3012 */       return this.tree;
/*      */     }
/*      */   }
/*      */ 
/*      */   public static class option_return extends ParserRuleReturnScope
/*      */   {
/*      */     CommonTree tree;
/*      */ 
/*      */     public Object getTree()
/*      */     {
/* 2819 */       return this.tree;
/*      */     }
/*      */   }
/*      */ 
/*      */   public static class exprOptions_return extends ParserRuleReturnScope
/*      */   {
/*      */     CommonTree tree;
/*      */ 
/*      */     public Object getTree()
/*      */     {
/* 2694 */       return this.tree;
/*      */     }
/*      */   }
/*      */ 
/*      */   public static class notConditionalExpr_return extends ParserRuleReturnScope
/*      */   {
/*      */     CommonTree tree;
/*      */ 
/*      */     public Object getTree()
/*      */     {
/* 2475 */       return this.tree;
/*      */     }
/*      */   }
/*      */ 
/*      */   public static class notConditional_return extends ParserRuleReturnScope
/*      */   {
/*      */     CommonTree tree;
/*      */ 
/*      */     public Object getTree()
/*      */     {
/* 2373 */       return this.tree;
/*      */     }
/*      */   }
/*      */ 
/*      */   public static class andConditional_return extends ParserRuleReturnScope
/*      */   {
/*      */     CommonTree tree;
/*      */ 
/*      */     public Object getTree()
/*      */     {
/* 2278 */       return this.tree;
/*      */     }
/*      */   }
/*      */ 
/*      */   public static class conditional_return extends ParserRuleReturnScope
/*      */   {
/*      */     CommonTree tree;
/*      */ 
/*      */     public Object getTree()
/*      */     {
/* 2181 */       return this.tree;
/*      */     }
/*      */   }
/*      */ 
/*      */   protected static class conditional_scope
/*      */   {
/*      */     boolean inside;
/*      */   }
/*      */ 
/*      */   public static class ifstat_return extends ParserRuleReturnScope
/*      */   {
/*      */     CommonTree tree;
/*      */ 
/*      */     public Object getTree()
/*      */     {
/* 1609 */       return this.tree;
/*      */     }
/*      */   }
/*      */ 
/*      */   public static class subtemplate_return extends ParserRuleReturnScope
/*      */   {
/*      */     CommonTree tree;
/*      */ 
/*      */     public Object getTree()
/*      */     {
/* 1395 */       return this.tree;
/*      */     }
/*      */   }
/*      */ 
/*      */   public static class region_return extends ParserRuleReturnScope
/*      */   {
/*      */     CommonTree tree;
/*      */ 
/*      */     public Object getTree()
/*      */     {
/* 1139 */       return this.tree;
/*      */     }
/*      */   }
/*      */ 
/*      */   public static class exprTag_return extends ParserRuleReturnScope
/*      */   {
/*      */     CommonTree tree;
/*      */ 
/*      */     public Object getTree()
/*      */     {
/* 1004 */       return this.tree;
/*      */     }
/*      */   }
/*      */ 
/*      */   public static class compoundElement_return extends ParserRuleReturnScope
/*      */   {
/*      */     CommonTree tree;
/*      */ 
/*      */     public Object getTree()
/*      */     {
/*  875 */       return this.tree;
/*      */     }
/*      */   }
/*      */ 
/*      */   public static class singleElement_return extends ParserRuleReturnScope
/*      */   {
/*      */     CommonTree tree;
/*      */ 
/*      */     public Object getTree()
/*      */     {
/*  742 */       return this.tree;
/*      */     }
/*      */   }
/*      */ 
/*      */   public static class element_return extends ParserRuleReturnScope
/*      */   {
/*      */     CommonTree tree;
/*      */ 
/*      */     public Object getTree()
/*      */     {
/*  316 */       return this.tree;
/*      */     }
/*      */   }
/*      */ 
/*      */   public static class template_return extends ParserRuleReturnScope
/*      */   {
/*      */     CommonTree tree;
/*      */ 
/*      */     public Object getTree()
/*      */     {
/*  204 */       return this.tree;
/*      */     }
/*      */   }
/*      */ 
/*      */   public static class templateAndEOF_return extends ParserRuleReturnScope
/*      */   {
/*      */     CommonTree tree;
/*      */ 
/*      */     public Object getTree()
/*      */     {
/*  122 */       return this.tree;
/*      */     }
/*      */   }
/*      */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.stringtemplate.v4.compiler.STParser
 * JD-Core Version:    0.6.2
 */