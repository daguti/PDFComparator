/*      */ package org.stringtemplate.v4.compiler;
/*      */ 
/*      */ import java.util.ArrayList;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import java.util.Stack;
/*      */ import org.antlr.runtime.BitSet;
/*      */ import org.antlr.runtime.EarlyExitException;
/*      */ import org.antlr.runtime.NoViableAltException;
/*      */ import org.antlr.runtime.RecognitionException;
/*      */ import org.antlr.runtime.RecognizerSharedState;
/*      */ import org.antlr.runtime.Token;
/*      */ import org.antlr.runtime.tree.CommonTree;
/*      */ import org.antlr.runtime.tree.TreeNodeStream;
/*      */ import org.antlr.runtime.tree.TreeParser;
/*      */ import org.antlr.runtime.tree.TreeRuleReturnScope;
/*      */ import org.stringtemplate.v4.ST.RegionType;
/*      */ import org.stringtemplate.v4.STGroup;
/*      */ import org.stringtemplate.v4.misc.ErrorManager;
/*      */ import org.stringtemplate.v4.misc.ErrorType;
/*      */ import org.stringtemplate.v4.misc.Misc;
/*      */ 
/*      */ public class CodeGenerator extends TreeParser
/*      */ {
/*   43 */   public static final String[] tokenNames = { "<invalid>", "<EOR>", "<DOWN>", "<UP>", "IF", "ELSE", "ELSEIF", "ENDIF", "SUPER", "SEMI", "BANG", "ELLIPSIS", "EQUALS", "COLON", "LPAREN", "RPAREN", "LBRACK", "RBRACK", "COMMA", "DOT", "LCURLY", "RCURLY", "TEXT", "LDELIM", "RDELIM", "ID", "STRING", "WS", "PIPE", "OR", "AND", "INDENT", "NEWLINE", "AT", "END", "TRUE", "FALSE", "COMMENT", "ARGS", "ELEMENTS", "EXEC_FUNC", "EXPR", "INCLUDE", "INCLUDE_IND", "INCLUDE_REGION", "INCLUDE_SUPER", "INCLUDE_SUPER_REGION", "INDENTED_EXPR", "LIST", "MAP", "NULL", "OPTIONS", "PROP", "PROP_IND", "REGION", "SUBTEMPLATE", "TO_STR", "ZIP" };
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
/*      */   String outermostTemplateName;
/*      */   CompiledST outermostImpl;
/*      */   Token templateToken;
/*      */   String template;
/*      */   ErrorManager errMgr;
/*  206 */   protected Stack template_stack = new Stack();
/*      */ 
/* 2632 */   public static final BitSet FOLLOW_template_in_templateAndEOF50 = new BitSet(new long[] { 0L });
/* 2633 */   public static final BitSet FOLLOW_EOF_in_templateAndEOF53 = new BitSet(new long[] { 2L });
/* 2634 */   public static final BitSet FOLLOW_chunk_in_template77 = new BitSet(new long[] { 2L });
/* 2635 */   public static final BitSet FOLLOW_element_in_chunk92 = new BitSet(new long[] { 18157339320254482L });
/* 2636 */   public static final BitSet FOLLOW_INDENTED_EXPR_in_element105 = new BitSet(new long[] { 4L });
/* 2637 */   public static final BitSet FOLLOW_INDENT_in_element107 = new BitSet(new long[] { 18014398509482000L });
/* 2638 */   public static final BitSet FOLLOW_compoundElement_in_element109 = new BitSet(new long[] { 8L });
/* 2639 */   public static final BitSet FOLLOW_compoundElement_in_element117 = new BitSet(new long[] { 2L });
/* 2640 */   public static final BitSet FOLLOW_INDENTED_EXPR_in_element124 = new BitSet(new long[] { 4L });
/* 2641 */   public static final BitSet FOLLOW_INDENT_in_element126 = new BitSet(new long[] { 2203322417152L });
/* 2642 */   public static final BitSet FOLLOW_singleElement_in_element130 = new BitSet(new long[] { 8L });
/* 2643 */   public static final BitSet FOLLOW_singleElement_in_element138 = new BitSet(new long[] { 2L });
/* 2644 */   public static final BitSet FOLLOW_exprElement_in_singleElement149 = new BitSet(new long[] { 2L });
/* 2645 */   public static final BitSet FOLLOW_TEXT_in_singleElement154 = new BitSet(new long[] { 2L });
/* 2646 */   public static final BitSet FOLLOW_NEWLINE_in_singleElement164 = new BitSet(new long[] { 2L });
/* 2647 */   public static final BitSet FOLLOW_ifstat_in_compoundElement178 = new BitSet(new long[] { 2L });
/* 2648 */   public static final BitSet FOLLOW_region_in_compoundElement184 = new BitSet(new long[] { 2L });
/* 2649 */   public static final BitSet FOLLOW_EXPR_in_exprElement203 = new BitSet(new long[] { 4L });
/* 2650 */   public static final BitSet FOLLOW_expr_in_exprElement205 = new BitSet(new long[] { 2251799813685256L });
/* 2651 */   public static final BitSet FOLLOW_exprOptions_in_exprElement208 = new BitSet(new long[] { 8L });
/* 2652 */   public static final BitSet FOLLOW_REGION_in_region246 = new BitSet(new long[] { 4L });
/* 2653 */   public static final BitSet FOLLOW_ID_in_region248 = new BitSet(new long[] { 18157339320254480L });
/* 2654 */   public static final BitSet FOLLOW_template_in_region258 = new BitSet(new long[] { 8L });
/* 2655 */   public static final BitSet FOLLOW_SUBTEMPLATE_in_subtemplate291 = new BitSet(new long[] { 4L });
/* 2656 */   public static final BitSet FOLLOW_ARGS_in_subtemplate298 = new BitSet(new long[] { 4L });
/* 2657 */   public static final BitSet FOLLOW_ID_in_subtemplate301 = new BitSet(new long[] { 33554440L });
/* 2658 */   public static final BitSet FOLLOW_template_in_subtemplate318 = new BitSet(new long[] { 8L });
/* 2659 */   public static final BitSet FOLLOW_SUBTEMPLATE_in_subtemplate334 = new BitSet(new long[] { 2L });
/* 2660 */   public static final BitSet FOLLOW_IF_in_ifstat366 = new BitSet(new long[] { 4L });
/* 2661 */   public static final BitSet FOLLOW_conditional_in_ifstat368 = new BitSet(new long[] { 18157339320254584L });
/* 2662 */   public static final BitSet FOLLOW_chunk_in_ifstat378 = new BitSet(new long[] { 104L });
/* 2663 */   public static final BitSet FOLLOW_ELSEIF_in_ifstat388 = new BitSet(new long[] { 4L });
/* 2664 */   public static final BitSet FOLLOW_conditional_in_ifstat402 = new BitSet(new long[] { 18157339320254488L });
/* 2665 */   public static final BitSet FOLLOW_chunk_in_ifstat414 = new BitSet(new long[] { 8L });
/* 2666 */   public static final BitSet FOLLOW_ELSE_in_ifstat437 = new BitSet(new long[] { 4L });
/* 2667 */   public static final BitSet FOLLOW_chunk_in_ifstat451 = new BitSet(new long[] { 8L });
/* 2668 */   public static final BitSet FOLLOW_OR_in_conditional485 = new BitSet(new long[] { 4L });
/* 2669 */   public static final BitSet FOLLOW_conditional_in_conditional487 = new BitSet(new long[] { 266694346688955392L });
/* 2670 */   public static final BitSet FOLLOW_conditional_in_conditional489 = new BitSet(new long[] { 8L });
/* 2671 */   public static final BitSet FOLLOW_AND_in_conditional499 = new BitSet(new long[] { 4L });
/* 2672 */   public static final BitSet FOLLOW_conditional_in_conditional501 = new BitSet(new long[] { 266694346688955392L });
/* 2673 */   public static final BitSet FOLLOW_conditional_in_conditional503 = new BitSet(new long[] { 8L });
/* 2674 */   public static final BitSet FOLLOW_BANG_in_conditional513 = new BitSet(new long[] { 4L });
/* 2675 */   public static final BitSet FOLLOW_conditional_in_conditional515 = new BitSet(new long[] { 8L });
/* 2676 */   public static final BitSet FOLLOW_expr_in_conditional527 = new BitSet(new long[] { 2L });
/* 2677 */   public static final BitSet FOLLOW_OPTIONS_in_exprOptions541 = new BitSet(new long[] { 4L });
/* 2678 */   public static final BitSet FOLLOW_option_in_exprOptions543 = new BitSet(new long[] { 4104L });
/* 2679 */   public static final BitSet FOLLOW_EQUALS_in_option555 = new BitSet(new long[] { 4L });
/* 2680 */   public static final BitSet FOLLOW_ID_in_option557 = new BitSet(new long[] { 266694345078341632L });
/* 2681 */   public static final BitSet FOLLOW_expr_in_option559 = new BitSet(new long[] { 8L });
/* 2682 */   public static final BitSet FOLLOW_ZIP_in_expr578 = new BitSet(new long[] { 4L });
/* 2683 */   public static final BitSet FOLLOW_ELEMENTS_in_expr581 = new BitSet(new long[] { 4L });
/* 2684 */   public static final BitSet FOLLOW_expr_in_expr584 = new BitSet(new long[] { 266694345078341640L });
/* 2685 */   public static final BitSet FOLLOW_mapTemplateRef_in_expr591 = new BitSet(new long[] { 8L });
/* 2686 */   public static final BitSet FOLLOW_MAP_in_expr603 = new BitSet(new long[] { 4L });
/* 2687 */   public static final BitSet FOLLOW_expr_in_expr605 = new BitSet(new long[] { 36041991158497280L });
/* 2688 */   public static final BitSet FOLLOW_mapTemplateRef_in_expr608 = new BitSet(new long[] { 36041991158497288L });
/* 2689 */   public static final BitSet FOLLOW_prop_in_expr623 = new BitSet(new long[] { 2L });
/* 2690 */   public static final BitSet FOLLOW_includeExpr_in_expr628 = new BitSet(new long[] { 2L });
/* 2691 */   public static final BitSet FOLLOW_PROP_in_prop638 = new BitSet(new long[] { 4L });
/* 2692 */   public static final BitSet FOLLOW_expr_in_prop640 = new BitSet(new long[] { 33554432L });
/* 2693 */   public static final BitSet FOLLOW_ID_in_prop642 = new BitSet(new long[] { 8L });
/* 2694 */   public static final BitSet FOLLOW_PROP_IND_in_prop656 = new BitSet(new long[] { 4L });
/* 2695 */   public static final BitSet FOLLOW_expr_in_prop658 = new BitSet(new long[] { 266694345078341632L });
/* 2696 */   public static final BitSet FOLLOW_expr_in_prop660 = new BitSet(new long[] { 8L });
/* 2697 */   public static final BitSet FOLLOW_INCLUDE_in_mapTemplateRef680 = new BitSet(new long[] { 4L });
/* 2698 */   public static final BitSet FOLLOW_ID_in_mapTemplateRef682 = new BitSet(new long[] { 266694345078347784L });
/* 2699 */   public static final BitSet FOLLOW_args_in_mapTemplateRef692 = new BitSet(new long[] { 8L });
/* 2700 */   public static final BitSet FOLLOW_subtemplate_in_mapTemplateRef705 = new BitSet(new long[] { 2L });
/* 2701 */   public static final BitSet FOLLOW_INCLUDE_IND_in_mapTemplateRef717 = new BitSet(new long[] { 4L });
/* 2702 */   public static final BitSet FOLLOW_expr_in_mapTemplateRef719 = new BitSet(new long[] { 266694345078347784L });
/* 2703 */   public static final BitSet FOLLOW_args_in_mapTemplateRef729 = new BitSet(new long[] { 8L });
/* 2704 */   public static final BitSet FOLLOW_EXEC_FUNC_in_includeExpr751 = new BitSet(new long[] { 4L });
/* 2705 */   public static final BitSet FOLLOW_ID_in_includeExpr753 = new BitSet(new long[] { 266694345078341640L });
/* 2706 */   public static final BitSet FOLLOW_expr_in_includeExpr755 = new BitSet(new long[] { 8L });
/* 2707 */   public static final BitSet FOLLOW_INCLUDE_in_includeExpr766 = new BitSet(new long[] { 4L });
/* 2708 */   public static final BitSet FOLLOW_ID_in_includeExpr768 = new BitSet(new long[] { 266694345078347784L });
/* 2709 */   public static final BitSet FOLLOW_args_in_includeExpr770 = new BitSet(new long[] { 8L });
/* 2710 */   public static final BitSet FOLLOW_INCLUDE_SUPER_in_includeExpr781 = new BitSet(new long[] { 4L });
/* 2711 */   public static final BitSet FOLLOW_ID_in_includeExpr783 = new BitSet(new long[] { 266694345078347784L });
/* 2712 */   public static final BitSet FOLLOW_args_in_includeExpr785 = new BitSet(new long[] { 8L });
/* 2713 */   public static final BitSet FOLLOW_INCLUDE_REGION_in_includeExpr796 = new BitSet(new long[] { 4L });
/* 2714 */   public static final BitSet FOLLOW_ID_in_includeExpr798 = new BitSet(new long[] { 8L });
/* 2715 */   public static final BitSet FOLLOW_INCLUDE_SUPER_REGION_in_includeExpr808 = new BitSet(new long[] { 4L });
/* 2716 */   public static final BitSet FOLLOW_ID_in_includeExpr810 = new BitSet(new long[] { 8L });
/* 2717 */   public static final BitSet FOLLOW_primary_in_includeExpr818 = new BitSet(new long[] { 2L });
/* 2718 */   public static final BitSet FOLLOW_ID_in_primary829 = new BitSet(new long[] { 2L });
/* 2719 */   public static final BitSet FOLLOW_STRING_in_primary839 = new BitSet(new long[] { 2L });
/* 2720 */   public static final BitSet FOLLOW_TRUE_in_primary848 = new BitSet(new long[] { 2L });
/* 2721 */   public static final BitSet FOLLOW_FALSE_in_primary857 = new BitSet(new long[] { 2L });
/* 2722 */   public static final BitSet FOLLOW_subtemplate_in_primary866 = new BitSet(new long[] { 2L });
/* 2723 */   public static final BitSet FOLLOW_list_in_primary893 = new BitSet(new long[] { 2L });
/* 2724 */   public static final BitSet FOLLOW_INCLUDE_IND_in_primary900 = new BitSet(new long[] { 4L });
/* 2725 */   public static final BitSet FOLLOW_expr_in_primary905 = new BitSet(new long[] { 266694345078347784L });
/* 2726 */   public static final BitSet FOLLOW_args_in_primary914 = new BitSet(new long[] { 8L });
/* 2727 */   public static final BitSet FOLLOW_TO_STR_in_primary934 = new BitSet(new long[] { 4L });
/* 2728 */   public static final BitSet FOLLOW_expr_in_primary936 = new BitSet(new long[] { 8L });
/* 2729 */   public static final BitSet FOLLOW_expr_in_arg949 = new BitSet(new long[] { 2L });
/* 2730 */   public static final BitSet FOLLOW_arg_in_args965 = new BitSet(new long[] { 266694345078341634L });
/* 2731 */   public static final BitSet FOLLOW_EQUALS_in_args984 = new BitSet(new long[] { 4L });
/* 2732 */   public static final BitSet FOLLOW_ID_in_args986 = new BitSet(new long[] { 266694345078341632L });
/* 2733 */   public static final BitSet FOLLOW_expr_in_args988 = new BitSet(new long[] { 8L });
/* 2734 */   public static final BitSet FOLLOW_ELLIPSIS_in_args1005 = new BitSet(new long[] { 2L });
/* 2735 */   public static final BitSet FOLLOW_ELLIPSIS_in_args1020 = new BitSet(new long[] { 2L });
/* 2736 */   public static final BitSet FOLLOW_LIST_in_list1040 = new BitSet(new long[] { 4L });
/* 2737 */   public static final BitSet FOLLOW_listElement_in_list1043 = new BitSet(new long[] { 267820244985184264L });
/* 2738 */   public static final BitSet FOLLOW_expr_in_listElement1059 = new BitSet(new long[] { 2L });
/* 2739 */   public static final BitSet FOLLOW_NULL_in_listElement1063 = new BitSet(new long[] { 2L });
/*      */ 
/*      */   public TreeParser[] getDelegates()
/*      */   {
/*  105 */     return new TreeParser[0];
/*      */   }
/*      */ 
/*      */   public CodeGenerator(TreeNodeStream input)
/*      */   {
/*  112 */     this(input, new RecognizerSharedState());
/*      */   }
/*      */   public CodeGenerator(TreeNodeStream input, RecognizerSharedState state) {
/*  115 */     super(input, state);
/*      */   }
/*      */   public String[] getTokenNames() {
/*  118 */     return tokenNames; } 
/*  119 */   public String getGrammarFileName() { return "/usr/local/website/st/depot/stringtemplate4/src/org/stringtemplate/v4/compiler/CodeGenerator.g"; }
/*      */ 
/*      */ 
/*      */   public CodeGenerator(TreeNodeStream input, ErrorManager errMgr, String name, String template, Token templateToken)
/*      */   {
/*  128 */     this(input, new RecognizerSharedState());
/*  129 */     this.errMgr = errMgr;
/*  130 */     this.outermostTemplateName = name;
/*  131 */     this.template = template;
/*  132 */     this.templateToken = templateToken;
/*      */   }
/*      */ 
/*      */   public void emit1(CommonTree opAST, short opcode, int arg)
/*      */   {
/*  139 */     ((template_scope)this.template_stack.peek()).state.emit1(opAST, opcode, arg);
/*      */   }
/*      */   public void emit1(CommonTree opAST, short opcode, String arg) {
/*  142 */     ((template_scope)this.template_stack.peek()).state.emit1(opAST, opcode, arg);
/*      */   }
/*      */   public void emit2(CommonTree opAST, short opcode, int arg, int arg2) {
/*  145 */     ((template_scope)this.template_stack.peek()).state.emit2(opAST, opcode, arg, arg2);
/*      */   }
/*      */   public void emit2(CommonTree opAST, short opcode, String s, int arg2) {
/*  148 */     ((template_scope)this.template_stack.peek()).state.emit2(opAST, opcode, s, arg2);
/*      */   }
/*      */   public void emit(short opcode) {
/*  151 */     ((template_scope)this.template_stack.peek()).state.emit(opcode);
/*      */   }
/*      */   public void emit(CommonTree opAST, short opcode) {
/*  154 */     ((template_scope)this.template_stack.peek()).state.emit(opAST, opcode);
/*      */   }
/*      */   public void insert(int addr, short opcode, String s) {
/*  157 */     ((template_scope)this.template_stack.peek()).state.insert(addr, opcode, s);
/*      */   }
/*      */   public void setOption(CommonTree id) {
/*  160 */     ((template_scope)this.template_stack.peek()).state.setOption(id);
/*      */   }
/*      */   public void write(int addr, short value) {
/*  163 */     ((template_scope)this.template_stack.peek()).state.write(addr, value);
/*      */   }
/*  165 */   public int address() { return ((template_scope)this.template_stack.peek()).state.ip; } 
/*  166 */   public void func(CommonTree id) { ((template_scope)this.template_stack.peek()).state.func(this.templateToken, id); } 
/*  167 */   public void refAttr(CommonTree id) { ((template_scope)this.template_stack.peek()).state.refAttr(this.templateToken, id); } 
/*  168 */   public int defineString(String s) { return ((template_scope)this.template_stack.peek()).state.defineString(s); }
/*      */ 
/*      */ 
/*      */   public final void templateAndEOF()
/*      */     throws RecognitionException
/*      */   {
/*      */     try
/*      */     {
/*  179 */       pushFollow(FOLLOW_template_in_templateAndEOF50);
/*  180 */       template(null, null);
/*      */ 
/*  182 */       this.state._fsp -= 1;
/*      */ 
/*  185 */       match(this.input, -1, FOLLOW_EOF_in_templateAndEOF53);
/*      */     }
/*      */     catch (RecognitionException re)
/*      */     {
/*  190 */       re = 
/*  197 */         re;
/*      */ 
/*  191 */       reportError(re);
/*  192 */       recover(this.input, re);
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public final CompiledST template(String name, List<FormalArgument> args)
/*      */     throws RecognitionException
/*      */   {
/*  213 */     this.template_stack.push(new template_scope());
/*  214 */     CompiledST impl = null;
/*      */ 
/*  218 */     ((template_scope)this.template_stack.peek()).state = new CompilationState(this.errMgr, name, this.input.getTokenStream());
/*  219 */     impl = ((template_scope)this.template_stack.peek()).state.impl;
/*  220 */     if (this.template_stack.size() == 1) this.outermostImpl = impl;
/*  221 */     impl.defineFormalArgs(args);
/*  222 */     if ((name != null) && (name.startsWith("_sub"))) {
/*  223 */       impl.addArg(new FormalArgument("i"));
/*  224 */       impl.addArg(new FormalArgument("i0"));
/*      */     }
/*  226 */     impl.template = this.template;
/*      */     try
/*      */     {
/*  232 */       pushFollow(FOLLOW_chunk_in_template77);
/*  233 */       chunk();
/*      */ 
/*  235 */       this.state._fsp -= 1;
/*      */ 
/*  239 */       if (((template_scope)this.template_stack.peek()).state.stringtable != null) impl.strings = ((template_scope)this.template_stack.peek()).state.stringtable.toArray();
/*  240 */       impl.codeSize = ((template_scope)this.template_stack.peek()).state.ip;
/*      */     }
/*      */     catch (RecognitionException re)
/*      */     {
/*  247 */       reportError(re);
/*  248 */       recover(this.input, re);
/*      */     }
/*      */     finally
/*      */     {
/*  253 */       this.template_stack.pop();
/*      */     }
/*  255 */     return impl;
/*      */   }
/*      */ 
/*      */   public final void chunk()
/*      */     throws RecognitionException
/*      */   {
/*      */     try
/*      */     {
/*      */       while (true)
/*      */       {
/*  271 */         int alt1 = 2;
/*  272 */         int LA1_0 = this.input.LA(1);
/*      */ 
/*  274 */         if ((LA1_0 == 4) || (LA1_0 == 22) || (LA1_0 == 32) || (LA1_0 == 41) || (LA1_0 == 47) || (LA1_0 == 54)) {
/*  275 */           alt1 = 1;
/*      */         }
/*      */ 
/*  279 */         switch (alt1)
/*      */         {
/*      */         case 1:
/*  283 */           pushFollow(FOLLOW_element_in_chunk92);
/*  284 */           element();
/*      */ 
/*  286 */           this.state._fsp -= 1;
/*      */ 
/*  290 */           break;
/*      */         default:
/*  293 */           return;
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (RecognitionException re)
/*      */     {
/*  301 */       re = 
/*  308 */         re;
/*      */ 
/*  302 */       reportError(re);
/*  303 */       recover(this.input, re);
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public final void element()
/*      */     throws RecognitionException
/*      */   {
/*  318 */     CommonTree INDENT1 = null;
/*  319 */     CommonTree INDENT2 = null;
/*      */     try
/*      */     {
/*  323 */       int alt2 = 4;
/*  324 */       switch (this.input.LA(1))
/*      */       {
/*      */       case 47:
/*  327 */         int LA2_1 = this.input.LA(2);
/*      */ 
/*  329 */         if (LA2_1 == 2) {
/*  330 */           int LA2_4 = this.input.LA(3);
/*      */ 
/*  332 */           if (LA2_4 == 31) {
/*  333 */             int LA2_5 = this.input.LA(4);
/*      */ 
/*  335 */             if ((LA2_5 == 4) || (LA2_5 == 54)) {
/*  336 */               alt2 = 1;
/*      */             }
/*  338 */             else if ((LA2_5 == 22) || (LA2_5 == 32) || (LA2_5 == 41)) {
/*  339 */               alt2 = 3;
/*      */             }
/*      */             else {
/*  342 */               NoViableAltException nvae = new NoViableAltException("", 2, 5, this.input);
/*      */ 
/*  345 */               throw nvae;
/*      */             }
/*      */           }
/*      */           else
/*      */           {
/*  350 */             NoViableAltException nvae = new NoViableAltException("", 2, 4, this.input);
/*      */ 
/*  353 */             throw nvae;
/*      */           }
/*      */         }
/*      */         else
/*      */         {
/*  358 */           NoViableAltException nvae = new NoViableAltException("", 2, 1, this.input);
/*      */ 
/*  361 */           throw nvae;
/*      */         }
/*      */ 
/*  365 */         break;
/*      */       case 4:
/*      */       case 54:
/*  369 */         alt2 = 2;
/*      */ 
/*  371 */         break;
/*      */       case 22:
/*      */       case 32:
/*      */       case 41:
/*  376 */         alt2 = 4;
/*      */ 
/*  378 */         break;
/*      */       default:
/*  380 */         NoViableAltException nvae = new NoViableAltException("", 2, 0, this.input);
/*      */ 
/*  383 */         throw nvae;
/*      */       }
/*      */ 
/*  387 */       switch (alt2)
/*      */       {
/*      */       case 1:
/*  391 */         match(this.input, 47, FOLLOW_INDENTED_EXPR_in_element105);
/*      */ 
/*  393 */         match(this.input, 2, null);
/*  394 */         INDENT1 = (CommonTree)match(this.input, 31, FOLLOW_INDENT_in_element107);
/*      */ 
/*  396 */         pushFollow(FOLLOW_compoundElement_in_element109);
/*  397 */         compoundElement(INDENT1);
/*      */ 
/*  399 */         this.state._fsp -= 1;
/*      */ 
/*  402 */         match(this.input, 3, null);
/*      */ 
/*  406 */         break;
/*      */       case 2:
/*  410 */         pushFollow(FOLLOW_compoundElement_in_element117);
/*  411 */         compoundElement(null);
/*      */ 
/*  413 */         this.state._fsp -= 1;
/*      */ 
/*  417 */         break;
/*      */       case 3:
/*  421 */         match(this.input, 47, FOLLOW_INDENTED_EXPR_in_element124);
/*      */ 
/*  423 */         match(this.input, 2, null);
/*  424 */         INDENT2 = (CommonTree)match(this.input, 31, FOLLOW_INDENT_in_element126);
/*      */ 
/*  426 */         ((template_scope)this.template_stack.peek()).state.indent(INDENT2);
/*      */ 
/*  428 */         pushFollow(FOLLOW_singleElement_in_element130);
/*  429 */         singleElement();
/*      */ 
/*  431 */         this.state._fsp -= 1;
/*      */ 
/*  434 */         ((template_scope)this.template_stack.peek()).state.emit((short)40);
/*      */ 
/*  436 */         match(this.input, 3, null);
/*      */ 
/*  440 */         break;
/*      */       case 4:
/*  444 */         pushFollow(FOLLOW_singleElement_in_element138);
/*  445 */         singleElement();
/*      */ 
/*  447 */         this.state._fsp -= 1;
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (RecognitionException re)
/*      */     {
/*  455 */       re = 
/*  462 */         re;
/*      */ 
/*  456 */       reportError(re);
/*  457 */       recover(this.input, re);
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public final void singleElement()
/*      */     throws RecognitionException
/*      */   {
/*  472 */     CommonTree TEXT3 = null;
/*      */     try
/*      */     {
/*  476 */       int alt3 = 3;
/*  477 */       switch (this.input.LA(1))
/*      */       {
/*      */       case 41:
/*  480 */         alt3 = 1;
/*      */ 
/*  482 */         break;
/*      */       case 22:
/*  485 */         alt3 = 2;
/*      */ 
/*  487 */         break;
/*      */       case 32:
/*  490 */         alt3 = 3;
/*      */ 
/*  492 */         break;
/*      */       default:
/*  494 */         NoViableAltException nvae = new NoViableAltException("", 3, 0, this.input);
/*      */ 
/*  497 */         throw nvae;
/*      */       }
/*      */ 
/*  501 */       switch (alt3)
/*      */       {
/*      */       case 1:
/*  505 */         pushFollow(FOLLOW_exprElement_in_singleElement149);
/*  506 */         exprElement();
/*      */ 
/*  508 */         this.state._fsp -= 1;
/*      */ 
/*  512 */         break;
/*      */       case 2:
/*  516 */         TEXT3 = (CommonTree)match(this.input, 22, FOLLOW_TEXT_in_singleElement154);
/*      */ 
/*  519 */         if ((TEXT3 != null ? TEXT3.getText() : null).length() > 0)
/*  520 */           emit1(TEXT3, (short)47, TEXT3 != null ? TEXT3.getText() : null); break;
/*      */       case 3:
/*  529 */         match(this.input, 32, FOLLOW_NEWLINE_in_singleElement164);
/*      */ 
/*  531 */         emit((short)41);
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (RecognitionException re)
/*      */     {
/*  538 */       re = 
/*  545 */         re;
/*      */ 
/*  539 */       reportError(re);
/*  540 */       recover(this.input, re);
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public final void compoundElement(CommonTree indent)
/*      */     throws RecognitionException
/*      */   {
/*      */     try
/*      */     {
/*  557 */       int alt4 = 2;
/*  558 */       int LA4_0 = this.input.LA(1);
/*      */ 
/*  560 */       if (LA4_0 == 4) {
/*  561 */         alt4 = 1;
/*      */       }
/*  563 */       else if (LA4_0 == 54) {
/*  564 */         alt4 = 2;
/*      */       }
/*      */       else {
/*  567 */         NoViableAltException nvae = new NoViableAltException("", 4, 0, this.input);
/*      */ 
/*  570 */         throw nvae;
/*      */       }
/*      */ 
/*  573 */       switch (alt4)
/*      */       {
/*      */       case 1:
/*  577 */         pushFollow(FOLLOW_ifstat_in_compoundElement178);
/*  578 */         ifstat(indent);
/*      */ 
/*  580 */         this.state._fsp -= 1;
/*      */ 
/*  584 */         break;
/*      */       case 2:
/*  588 */         pushFollow(FOLLOW_region_in_compoundElement184);
/*  589 */         region(indent);
/*      */ 
/*  591 */         this.state._fsp -= 1;
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (RecognitionException re)
/*      */     {
/*  599 */       re = 
/*  606 */         re;
/*      */ 
/*  600 */       reportError(re);
/*  601 */       recover(this.input, re);
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public final void exprElement()
/*      */     throws RecognitionException
/*      */   {
/*  616 */     CommonTree EXPR4 = null;
/*      */ 
/*  618 */     short op = 13;
/*      */     try
/*      */     {
/*  623 */       EXPR4 = (CommonTree)match(this.input, 41, FOLLOW_EXPR_in_exprElement203);
/*      */ 
/*  625 */       match(this.input, 2, null);
/*  626 */       pushFollow(FOLLOW_expr_in_exprElement205);
/*  627 */       expr();
/*      */ 
/*  629 */       this.state._fsp -= 1;
/*      */ 
/*  633 */       int alt5 = 2;
/*  634 */       int LA5_0 = this.input.LA(1);
/*      */ 
/*  636 */       if (LA5_0 == 51) {
/*  637 */         alt5 = 1;
/*      */       }
/*  639 */       switch (alt5)
/*      */       {
/*      */       case 1:
/*  643 */         pushFollow(FOLLOW_exprOptions_in_exprElement208);
/*  644 */         exprOptions();
/*      */ 
/*  646 */         this.state._fsp -= 1;
/*      */ 
/*  649 */         op = 14;
/*      */       }
/*      */ 
/*  657 */       match(this.input, 3, null);
/*      */ 
/*  671 */       emit(EXPR4, op);
/*      */     }
/*      */     catch (RecognitionException re)
/*      */     {
/*  677 */       re = 
/*  684 */         re;
/*      */ 
/*  678 */       reportError(re);
/*  679 */       recover(this.input, re);
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public final region_return region(CommonTree indent)
/*      */     throws RecognitionException
/*      */   {
/*  698 */     region_return retval = new region_return();
/*  699 */     retval.start = this.input.LT(1);
/*      */ 
/*  702 */     CommonTree ID5 = null;
/*  703 */     CompiledST template6 = null;
/*      */ 
/*  707 */     if (indent != null) ((template_scope)this.template_stack.peek()).state.indent(indent);
/*      */ 
/*      */     try
/*      */     {
/*  713 */       match(this.input, 54, FOLLOW_REGION_in_region246);
/*      */ 
/*  715 */       match(this.input, 2, null);
/*  716 */       ID5 = (CommonTree)match(this.input, 25, FOLLOW_ID_in_region248);
/*      */ 
/*  718 */       retval.name = STGroup.getMangledRegionName(this.outermostTemplateName, ID5 != null ? ID5.getText() : null);
/*      */ 
/*  720 */       pushFollow(FOLLOW_template_in_region258);
/*  721 */       template6 = template(retval.name, null);
/*      */ 
/*  723 */       this.state._fsp -= 1;
/*      */ 
/*  727 */       CompiledST sub = template6;
/*  728 */       sub.isRegion = true;
/*  729 */       sub.regionDefType = ST.RegionType.EMBEDDED;
/*  730 */       sub.templateDefStartToken = ID5.token;
/*      */ 
/*  732 */       this.outermostImpl.addImplicitlyDefinedTemplate(sub);
/*  733 */       emit2((CommonTree)retval.start, (short)8, retval.name, 0);
/*  734 */       emit((CommonTree)retval.start, (short)13);
/*      */ 
/*  737 */       match(this.input, 3, null);
/*      */ 
/*  743 */       if (indent != null) ((template_scope)this.template_stack.peek()).state.emit((short)40); 
/*      */     }
/*      */     catch (RecognitionException re)
/*      */     {
/*  746 */       re = 
/*  753 */         re;
/*      */ 
/*  747 */       reportError(re);
/*  748 */       recover(this.input, re);
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*      */ 
/*  754 */     return retval;
/*      */   }
/*      */ 
/*      */   public final subtemplate_return subtemplate()
/*      */     throws RecognitionException
/*      */   {
/*  768 */     subtemplate_return retval = new subtemplate_return();
/*  769 */     retval.start = this.input.LT(1);
/*      */ 
/*  772 */     CommonTree ID7 = null;
/*  773 */     CommonTree SUBTEMPLATE9 = null;
/*  774 */     CommonTree SUBTEMPLATE10 = null;
/*  775 */     CompiledST template8 = null;
/*      */ 
/*  779 */     retval.name = Compiler.getNewSubtemplateName();
/*  780 */     List args = new ArrayList();
/*      */     try
/*      */     {
/*  784 */       int alt8 = 2;
/*  785 */       int LA8_0 = this.input.LA(1);
/*      */ 
/*  787 */       if (LA8_0 == 55) {
/*  788 */         int LA8_1 = this.input.LA(2);
/*      */ 
/*  790 */         if (LA8_1 == 2) {
/*  791 */           alt8 = 1;
/*      */         }
/*  793 */         else if (((LA8_1 >= 3) && (LA8_1 <= 6)) || ((LA8_1 >= 10) && (LA8_1 <= 12)) || (LA8_1 == 22) || ((LA8_1 >= 25) && (LA8_1 <= 26)) || ((LA8_1 >= 29) && (LA8_1 <= 30)) || (LA8_1 == 32) || ((LA8_1 >= 35) && (LA8_1 <= 36)) || ((LA8_1 >= 40) && (LA8_1 <= 57))) {
/*  794 */           alt8 = 2;
/*      */         }
/*      */         else {
/*  797 */           NoViableAltException nvae = new NoViableAltException("", 8, 1, this.input);
/*      */ 
/*  800 */           throw nvae;
/*      */         }
/*      */       }
/*      */       else
/*      */       {
/*  805 */         NoViableAltException nvae = new NoViableAltException("", 8, 0, this.input);
/*      */ 
/*  808 */         throw nvae;
/*      */       }
/*      */ 
/*  811 */       switch (alt8)
/*      */       {
/*      */       case 1:
/*  815 */         SUBTEMPLATE9 = (CommonTree)match(this.input, 55, FOLLOW_SUBTEMPLATE_in_subtemplate291);
/*      */ 
/*  817 */         if (this.input.LA(1) == 2) {
/*  818 */           match(this.input, 2, null);
/*      */           while (true)
/*      */           {
/*  822 */             int alt7 = 2;
/*  823 */             int LA7_0 = this.input.LA(1);
/*      */ 
/*  825 */             if (LA7_0 == 38) {
/*  826 */               alt7 = 1;
/*      */             }
/*      */ 
/*  830 */             switch (alt7)
/*      */             {
/*      */             case 1:
/*  834 */               match(this.input, 38, FOLLOW_ARGS_in_subtemplate298);
/*      */ 
/*  836 */               match(this.input, 2, null);
/*      */ 
/*  838 */               int cnt6 = 0;
/*      */               while (true)
/*      */               {
/*  841 */                 int alt6 = 2;
/*  842 */                 int LA6_0 = this.input.LA(1);
/*      */ 
/*  844 */                 if (LA6_0 == 25) {
/*  845 */                   alt6 = 1;
/*      */                 }
/*      */ 
/*  849 */                 switch (alt6)
/*      */                 {
/*      */                 case 1:
/*  853 */                   ID7 = (CommonTree)match(this.input, 25, FOLLOW_ID_in_subtemplate301);
/*      */ 
/*  855 */                   args.add(new FormalArgument(ID7 != null ? ID7.getText() : null));
/*      */ 
/*  858 */                   break;
/*      */                 default:
/*  861 */                   if (cnt6 >= 1) break label512;
/*  862 */                   EarlyExitException eee = new EarlyExitException(6, this.input);
/*      */ 
/*  864 */                   throw eee;
/*      */                 }
/*  866 */                 cnt6++;
/*      */               }
/*      */ 
/*  870 */               match(this.input, 3, null);
/*      */ 
/*  874 */               break;
/*      */             default:
/*  877 */               break label532;
/*      */             }
/*      */ 
/*      */           }
/*      */ 
/*  882 */           retval.nargs = args.size();
/*      */ 
/*  884 */           pushFollow(FOLLOW_template_in_subtemplate318);
/*  885 */           template8 = template(retval.name, args);
/*      */ 
/*  887 */           this.state._fsp -= 1;
/*      */ 
/*  891 */           CompiledST sub = template8;
/*  892 */           sub.isAnonSubtemplate = true;
/*  893 */           sub.templateDefStartToken = SUBTEMPLATE9.token;
/*  894 */           sub.ast = SUBTEMPLATE9;
/*  895 */           sub.ast.setUnknownTokenBoundaries();
/*  896 */           sub.tokens = this.input.getTokenStream();
/*      */ 
/*  898 */           this.outermostImpl.addImplicitlyDefinedTemplate(sub);
/*      */ 
/*  901 */           match(this.input, 3, null);
/*  902 */         }break;
/*      */       case 2:
/*  910 */         label512: label532: SUBTEMPLATE10 = (CommonTree)match(this.input, 55, FOLLOW_SUBTEMPLATE_in_subtemplate334);
/*      */ 
/*  913 */         CompiledST sub = new CompiledST();
/*  914 */         sub.name = retval.name;
/*  915 */         sub.template = "";
/*  916 */         sub.addArg(new FormalArgument("i"));
/*  917 */         sub.addArg(new FormalArgument("i0"));
/*  918 */         sub.isAnonSubtemplate = true;
/*  919 */         sub.templateDefStartToken = SUBTEMPLATE10.token;
/*  920 */         sub.ast = SUBTEMPLATE10;
/*  921 */         sub.ast.setUnknownTokenBoundaries();
/*  922 */         sub.tokens = this.input.getTokenStream();
/*      */ 
/*  924 */         this.outermostImpl.addImplicitlyDefinedTemplate(sub);
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (RecognitionException re)
/*      */     {
/*  932 */       re = 
/*  939 */         re;
/*      */ 
/*  933 */       reportError(re);
/*  934 */       recover(this.input, re);
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*      */ 
/*  940 */     return retval;
/*      */   }
/*      */ 
/*      */   public final void ifstat(CommonTree indent)
/*      */     throws RecognitionException
/*      */   {
/*  949 */     CommonTree i = null;
/*  950 */     CommonTree eif = null;
/*  951 */     CommonTree el = null;
/*  952 */     conditional_return ec = null;
/*      */ 
/*  959 */     int prevBranchOperand = -1;
/*      */ 
/*  963 */     List endRefs = new ArrayList();
/*  964 */     if (indent != null) ((template_scope)this.template_stack.peek()).state.indent(indent);
/*      */ 
/*      */     try
/*      */     {
/*  970 */       i = (CommonTree)match(this.input, 4, FOLLOW_IF_in_ifstat366);
/*      */ 
/*  972 */       match(this.input, 2, null);
/*  973 */       pushFollow(FOLLOW_conditional_in_ifstat368);
/*  974 */       conditional();
/*      */ 
/*  976 */       this.state._fsp -= 1;
/*      */ 
/*  980 */       prevBranchOperand = address() + 1;
/*  981 */       emit1(i, (short)19, -1);
/*      */ 
/*  984 */       pushFollow(FOLLOW_chunk_in_ifstat378);
/*  985 */       chunk();
/*      */ 
/*  987 */       this.state._fsp -= 1;
/*      */       while (true)
/*      */       {
/*  993 */         int alt9 = 2;
/*  994 */         int LA9_0 = this.input.LA(1);
/*      */ 
/*  996 */         if (LA9_0 == 6) {
/*  997 */           alt9 = 1;
/*      */         }
/*      */ 
/* 1001 */         switch (alt9)
/*      */         {
/*      */         case 1:
/* 1005 */           eif = (CommonTree)match(this.input, 6, FOLLOW_ELSEIF_in_ifstat388);
/*      */ 
/* 1008 */           endRefs.add(Integer.valueOf(address() + 1));
/* 1009 */           emit1(eif, (short)18, -1);
/*      */ 
/* 1011 */           write(prevBranchOperand, (short)address());
/* 1012 */           prevBranchOperand = -1;
/*      */ 
/* 1015 */           match(this.input, 2, null);
/* 1016 */           pushFollow(FOLLOW_conditional_in_ifstat402);
/* 1017 */           ec = conditional();
/*      */ 
/* 1019 */           this.state._fsp -= 1;
/*      */ 
/* 1023 */           prevBranchOperand = address() + 1;
/*      */ 
/* 1025 */           emit1(null, ec != null ? (CommonTree)ec.start : (short)19, -1);
/*      */ 
/* 1028 */           pushFollow(FOLLOW_chunk_in_ifstat414);
/* 1029 */           chunk();
/*      */ 
/* 1031 */           this.state._fsp -= 1;
/*      */ 
/* 1034 */           match(this.input, 3, null);
/*      */ 
/* 1038 */           break;
/*      */         default:
/* 1041 */           break label349;
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/* 1047 */       label349: int alt10 = 2;
/* 1048 */       int LA10_0 = this.input.LA(1);
/*      */ 
/* 1050 */       if (LA10_0 == 5) {
/* 1051 */         alt10 = 1;
/*      */       }
/* 1053 */       switch (alt10)
/*      */       {
/*      */       case 1:
/* 1057 */         el = (CommonTree)match(this.input, 5, FOLLOW_ELSE_in_ifstat437);
/*      */ 
/* 1060 */         endRefs.add(Integer.valueOf(address() + 1));
/* 1061 */         emit1(el, (short)18, -1);
/*      */ 
/* 1063 */         write(prevBranchOperand, (short)address());
/* 1064 */         prevBranchOperand = -1;
/*      */ 
/* 1067 */         if (this.input.LA(1) == 2) {
/* 1068 */           match(this.input, 2, null);
/* 1069 */           pushFollow(FOLLOW_chunk_in_ifstat451);
/* 1070 */           chunk();
/*      */ 
/* 1072 */           this.state._fsp -= 1;
/*      */ 
/* 1075 */           match(this.input, 3, null);
/*      */         }
/*      */ 
/*      */         break;
/*      */       }
/*      */ 
/* 1085 */       match(this.input, 3, null);
/*      */ 
/* 1089 */       if (prevBranchOperand >= 0)
/* 1090 */         write(prevBranchOperand, (short)address());
/* 1092 */       int opnd;
/* 1092 */       for (Iterator i$ = endRefs.iterator(); i$.hasNext(); write(opnd, (short)address())) opnd = ((Integer)i$.next()).intValue();
/*      */ 
/* 1098 */       if (indent != null) ((template_scope)this.template_stack.peek()).state.emit((short)40); 
/*      */     }
/*      */     catch (RecognitionException re)
/*      */     {
/* 1101 */       re = 
/* 1108 */         re;
/*      */ 
/* 1102 */       reportError(re);
/* 1103 */       recover(this.input, re);
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public final conditional_return conditional()
/*      */     throws RecognitionException
/*      */   {
/* 1121 */     conditional_return retval = new conditional_return();
/* 1122 */     retval.start = this.input.LT(1);
/*      */     try
/*      */     {
/* 1127 */       int alt11 = 4;
/* 1128 */       switch (this.input.LA(1))
/*      */       {
/*      */       case 29:
/* 1131 */         alt11 = 1;
/*      */ 
/* 1133 */         break;
/*      */       case 30:
/* 1136 */         alt11 = 2;
/*      */ 
/* 1138 */         break;
/*      */       case 10:
/* 1141 */         alt11 = 3;
/*      */ 
/* 1143 */         break;
/*      */       case 25:
/*      */       case 26:
/*      */       case 35:
/*      */       case 36:
/*      */       case 40:
/*      */       case 42:
/*      */       case 43:
/*      */       case 44:
/*      */       case 45:
/*      */       case 46:
/*      */       case 48:
/*      */       case 49:
/*      */       case 52:
/*      */       case 53:
/*      */       case 55:
/*      */       case 56:
/*      */       case 57:
/* 1162 */         alt11 = 4;
/*      */ 
/* 1164 */         break;
/*      */       case 11:
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
/*      */       case 27:
/*      */       case 28:
/*      */       case 31:
/*      */       case 32:
/*      */       case 33:
/*      */       case 34:
/*      */       case 37:
/*      */       case 38:
/*      */       case 39:
/*      */       case 41:
/*      */       case 47:
/*      */       case 50:
/*      */       case 51:
/*      */       case 54:
/*      */       default:
/* 1166 */         NoViableAltException nvae = new NoViableAltException("", 11, 0, this.input);
/*      */ 
/* 1169 */         throw nvae;
/*      */       }
/*      */ 
/* 1173 */       switch (alt11)
/*      */       {
/*      */       case 1:
/* 1177 */         match(this.input, 29, FOLLOW_OR_in_conditional485);
/*      */ 
/* 1179 */         match(this.input, 2, null);
/* 1180 */         pushFollow(FOLLOW_conditional_in_conditional487);
/* 1181 */         conditional();
/*      */ 
/* 1183 */         this.state._fsp -= 1;
/*      */ 
/* 1186 */         pushFollow(FOLLOW_conditional_in_conditional489);
/* 1187 */         conditional();
/*      */ 
/* 1189 */         this.state._fsp -= 1;
/*      */ 
/* 1192 */         match(this.input, 3, null);
/*      */ 
/* 1195 */         emit((short)37);
/*      */ 
/* 1198 */         break;
/*      */       case 2:
/* 1202 */         match(this.input, 30, FOLLOW_AND_in_conditional499);
/*      */ 
/* 1204 */         match(this.input, 2, null);
/* 1205 */         pushFollow(FOLLOW_conditional_in_conditional501);
/* 1206 */         conditional();
/*      */ 
/* 1208 */         this.state._fsp -= 1;
/*      */ 
/* 1211 */         pushFollow(FOLLOW_conditional_in_conditional503);
/* 1212 */         conditional();
/*      */ 
/* 1214 */         this.state._fsp -= 1;
/*      */ 
/* 1217 */         match(this.input, 3, null);
/*      */ 
/* 1220 */         emit((short)38);
/*      */ 
/* 1223 */         break;
/*      */       case 3:
/* 1227 */         match(this.input, 10, FOLLOW_BANG_in_conditional513);
/*      */ 
/* 1229 */         match(this.input, 2, null);
/* 1230 */         pushFollow(FOLLOW_conditional_in_conditional515);
/* 1231 */         conditional();
/*      */ 
/* 1233 */         this.state._fsp -= 1;
/*      */ 
/* 1236 */         match(this.input, 3, null);
/*      */ 
/* 1239 */         emit((short)36);
/*      */ 
/* 1242 */         break;
/*      */       case 4:
/* 1246 */         pushFollow(FOLLOW_expr_in_conditional527);
/* 1247 */         expr();
/*      */ 
/* 1249 */         this.state._fsp -= 1;
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (RecognitionException re)
/*      */     {
/* 1257 */       re = 
/* 1264 */         re;
/*      */ 
/* 1258 */       reportError(re);
/* 1259 */       recover(this.input, re);
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*      */ 
/* 1265 */     return retval;
/*      */   }
/*      */ 
/*      */   public final void exprOptions()
/*      */     throws RecognitionException
/*      */   {
/*      */     try
/*      */     {
/* 1278 */       emit((short)20);
/*      */ 
/* 1280 */       match(this.input, 51, FOLLOW_OPTIONS_in_exprOptions541);
/*      */ 
/* 1282 */       if (this.input.LA(1) == 2) {
/* 1283 */         match(this.input, 2, null);
/*      */         while (true)
/*      */         {
/* 1287 */           int alt12 = 2;
/* 1288 */           int LA12_0 = this.input.LA(1);
/*      */ 
/* 1290 */           if (LA12_0 == 12) {
/* 1291 */             alt12 = 1;
/*      */           }
/*      */ 
/* 1295 */           switch (alt12)
/*      */           {
/*      */           case 1:
/* 1299 */             pushFollow(FOLLOW_option_in_exprOptions543);
/* 1300 */             option();
/*      */ 
/* 1302 */             this.state._fsp -= 1;
/*      */ 
/* 1306 */             break;
/*      */           default:
/* 1309 */             break label117;
/*      */           }
/*      */ 
/*      */         }
/*      */ 
/* 1314 */         label117: match(this.input, 3, null);
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (RecognitionException re)
/*      */     {
/* 1321 */       re = 
/* 1328 */         re;
/*      */ 
/* 1322 */       reportError(re);
/* 1323 */       recover(this.input, re);
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public final void option()
/*      */     throws RecognitionException
/*      */   {
/* 1338 */     CommonTree ID11 = null;
/*      */     try
/*      */     {
/* 1344 */       match(this.input, 12, FOLLOW_EQUALS_in_option555);
/*      */ 
/* 1346 */       match(this.input, 2, null);
/* 1347 */       ID11 = (CommonTree)match(this.input, 25, FOLLOW_ID_in_option557);
/*      */ 
/* 1349 */       pushFollow(FOLLOW_expr_in_option559);
/* 1350 */       expr();
/*      */ 
/* 1352 */       this.state._fsp -= 1;
/*      */ 
/* 1355 */       match(this.input, 3, null);
/*      */ 
/* 1358 */       setOption(ID11);
/*      */     }
/*      */     catch (RecognitionException re)
/*      */     {
/* 1363 */       re = 
/* 1370 */         re;
/*      */ 
/* 1364 */       reportError(re);
/* 1365 */       recover(this.input, re);
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public final void expr()
/*      */     throws RecognitionException
/*      */   {
/* 1380 */     CommonTree ZIP12 = null;
/* 1381 */     CommonTree MAP13 = null;
/*      */ 
/* 1383 */     int nt = 0; int ne = 0;
/*      */     try
/*      */     {
/* 1386 */       int alt15 = 4;
/* 1387 */       switch (this.input.LA(1))
/*      */       {
/*      */       case 57:
/* 1390 */         alt15 = 1;
/*      */ 
/* 1392 */         break;
/*      */       case 49:
/* 1395 */         alt15 = 2;
/*      */ 
/* 1397 */         break;
/*      */       case 52:
/*      */       case 53:
/* 1401 */         alt15 = 3;
/*      */ 
/* 1403 */         break;
/*      */       case 25:
/*      */       case 26:
/*      */       case 35:
/*      */       case 36:
/*      */       case 40:
/*      */       case 42:
/*      */       case 43:
/*      */       case 44:
/*      */       case 45:
/*      */       case 46:
/*      */       case 48:
/*      */       case 55:
/*      */       case 56:
/* 1418 */         alt15 = 4;
/*      */ 
/* 1420 */         break;
/*      */       case 27:
/*      */       case 28:
/*      */       case 29:
/*      */       case 30:
/*      */       case 31:
/*      */       case 32:
/*      */       case 33:
/*      */       case 34:
/*      */       case 37:
/*      */       case 38:
/*      */       case 39:
/*      */       case 41:
/*      */       case 47:
/*      */       case 50:
/*      */       case 51:
/*      */       case 54:
/*      */       default:
/* 1422 */         NoViableAltException nvae = new NoViableAltException("", 15, 0, this.input);
/*      */ 
/* 1425 */         throw nvae;
/*      */       }
/*      */ 
/* 1429 */       switch (alt15)
/*      */       {
/*      */       case 1:
/* 1433 */         ZIP12 = (CommonTree)match(this.input, 57, FOLLOW_ZIP_in_expr578);
/*      */ 
/* 1435 */         match(this.input, 2, null);
/* 1436 */         match(this.input, 39, FOLLOW_ELEMENTS_in_expr581);
/*      */ 
/* 1438 */         match(this.input, 2, null);
/*      */ 
/* 1440 */         int cnt13 = 0;
/*      */         while (true)
/*      */         {
/* 1443 */           int alt13 = 2;
/* 1444 */           int LA13_0 = this.input.LA(1);
/*      */ 
/* 1446 */           if (((LA13_0 >= 25) && (LA13_0 <= 26)) || ((LA13_0 >= 35) && (LA13_0 <= 36)) || (LA13_0 == 40) || ((LA13_0 >= 42) && (LA13_0 <= 46)) || ((LA13_0 >= 48) && (LA13_0 <= 49)) || ((LA13_0 >= 52) && (LA13_0 <= 53)) || ((LA13_0 >= 55) && (LA13_0 <= 57))) {
/* 1447 */             alt13 = 1;
/*      */           }
/*      */ 
/* 1451 */           switch (alt13)
/*      */           {
/*      */           case 1:
/* 1455 */             pushFollow(FOLLOW_expr_in_expr584);
/* 1456 */             expr();
/*      */ 
/* 1458 */             this.state._fsp -= 1;
/*      */ 
/* 1461 */             ne++;
/*      */ 
/* 1464 */             break;
/*      */           default:
/* 1467 */             if (cnt13 >= 1) break label491;
/* 1468 */             EarlyExitException eee = new EarlyExitException(13, this.input);
/*      */ 
/* 1470 */             throw eee;
/*      */           }
/* 1472 */           cnt13++;
/*      */         }
/*      */ 
/* 1476 */         match(this.input, 3, null);
/*      */ 
/* 1479 */         pushFollow(FOLLOW_mapTemplateRef_in_expr591);
/* 1480 */         mapTemplateRef(ne);
/*      */ 
/* 1482 */         this.state._fsp -= 1;
/*      */ 
/* 1485 */         match(this.input, 3, null);
/*      */ 
/* 1488 */         emit1(ZIP12, (short)17, ne);
/*      */ 
/* 1491 */         break;
/*      */       case 2:
/* 1495 */         MAP13 = (CommonTree)match(this.input, 49, FOLLOW_MAP_in_expr603);
/*      */ 
/* 1497 */         match(this.input, 2, null);
/* 1498 */         pushFollow(FOLLOW_expr_in_expr605);
/* 1499 */         expr();
/*      */ 
/* 1501 */         this.state._fsp -= 1;
/*      */ 
/* 1505 */         int cnt14 = 0;
/*      */         while (true)
/*      */         {
/* 1508 */           int alt14 = 2;
/* 1509 */           int LA14_0 = this.input.LA(1);
/*      */ 
/* 1511 */           if (((LA14_0 >= 42) && (LA14_0 <= 43)) || (LA14_0 == 55)) {
/* 1512 */             alt14 = 1;
/*      */           }
/*      */ 
/* 1516 */           switch (alt14)
/*      */           {
/*      */           case 1:
/* 1520 */             pushFollow(FOLLOW_mapTemplateRef_in_expr608);
/* 1521 */             mapTemplateRef(1);
/*      */ 
/* 1523 */             this.state._fsp -= 1;
/*      */ 
/* 1526 */             nt++;
/*      */ 
/* 1529 */             break;
/*      */           default:
/* 1532 */             if (cnt14 >= 1) break label733;
/* 1533 */             EarlyExitException eee = new EarlyExitException(14, this.input);
/*      */ 
/* 1535 */             throw eee;
/*      */           }
/* 1537 */           cnt14++;
/*      */         }
/*      */ 
/* 1541 */         match(this.input, 3, null);
/*      */ 
/* 1545 */         if (nt > 1) emit1(MAP13, (short)(nt > 1 ? 16 : 15), nt); else {
/* 1546 */           emit(MAP13, (short)15);
/*      */         }
/*      */ 
/* 1550 */         break;
/*      */       case 3:
/* 1554 */         pushFollow(FOLLOW_prop_in_expr623);
/* 1555 */         prop();
/*      */ 
/* 1557 */         this.state._fsp -= 1;
/*      */ 
/* 1561 */         break;
/*      */       case 4:
/* 1565 */         label491: pushFollow(FOLLOW_includeExpr_in_expr628);
/* 1566 */         label733: includeExpr();
/*      */ 
/* 1568 */         this.state._fsp -= 1;
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (RecognitionException re)
/*      */     {
/* 1576 */       re = 
/* 1583 */         re;
/*      */ 
/* 1577 */       reportError(re);
/* 1578 */       recover(this.input, re);
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public final void prop()
/*      */     throws RecognitionException
/*      */   {
/* 1593 */     CommonTree PROP14 = null;
/* 1594 */     CommonTree ID15 = null;
/* 1595 */     CommonTree PROP_IND16 = null;
/*      */     try
/*      */     {
/* 1599 */       int alt16 = 2;
/* 1600 */       int LA16_0 = this.input.LA(1);
/*      */ 
/* 1602 */       if (LA16_0 == 52) {
/* 1603 */         alt16 = 1;
/*      */       }
/* 1605 */       else if (LA16_0 == 53) {
/* 1606 */         alt16 = 2;
/*      */       }
/*      */       else {
/* 1609 */         NoViableAltException nvae = new NoViableAltException("", 16, 0, this.input);
/*      */ 
/* 1612 */         throw nvae;
/*      */       }
/*      */ 
/* 1615 */       switch (alt16)
/*      */       {
/*      */       case 1:
/* 1619 */         PROP14 = (CommonTree)match(this.input, 52, FOLLOW_PROP_in_prop638);
/*      */ 
/* 1621 */         match(this.input, 2, null);
/* 1622 */         pushFollow(FOLLOW_expr_in_prop640);
/* 1623 */         expr();
/*      */ 
/* 1625 */         this.state._fsp -= 1;
/*      */ 
/* 1628 */         ID15 = (CommonTree)match(this.input, 25, FOLLOW_ID_in_prop642);
/*      */ 
/* 1630 */         match(this.input, 3, null);
/*      */ 
/* 1633 */         emit1(PROP14, (short)4, ID15 != null ? ID15.getText() : null);
/*      */ 
/* 1636 */         break;
/*      */       case 2:
/* 1640 */         PROP_IND16 = (CommonTree)match(this.input, 53, FOLLOW_PROP_IND_in_prop656);
/*      */ 
/* 1642 */         match(this.input, 2, null);
/* 1643 */         pushFollow(FOLLOW_expr_in_prop658);
/* 1644 */         expr();
/*      */ 
/* 1646 */         this.state._fsp -= 1;
/*      */ 
/* 1649 */         pushFollow(FOLLOW_expr_in_prop660);
/* 1650 */         expr();
/*      */ 
/* 1652 */         this.state._fsp -= 1;
/*      */ 
/* 1655 */         match(this.input, 3, null);
/*      */ 
/* 1658 */         emit(PROP_IND16, (short)5);
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (RecognitionException re)
/*      */     {
/* 1665 */       re = 
/* 1672 */         re;
/*      */ 
/* 1666 */       reportError(re);
/* 1667 */       recover(this.input, re);
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public final mapTemplateRef_return mapTemplateRef(int num_exprs)
/*      */     throws RecognitionException
/*      */   {
/* 1685 */     mapTemplateRef_return retval = new mapTemplateRef_return();
/* 1686 */     retval.start = this.input.LT(1);
/*      */ 
/* 1689 */     CommonTree INCLUDE17 = null;
/* 1690 */     CommonTree ID19 = null;
/* 1691 */     CommonTree INCLUDE_IND21 = null;
/* 1692 */     args_return args18 = null;
/*      */ 
/* 1694 */     subtemplate_return subtemplate20 = null;
/*      */ 
/* 1696 */     args_return args22 = null;
/*      */     try
/*      */     {
/* 1701 */       int alt17 = 3;
/* 1702 */       switch (this.input.LA(1))
/*      */       {
/*      */       case 42:
/* 1705 */         alt17 = 1;
/*      */ 
/* 1707 */         break;
/*      */       case 55:
/* 1710 */         alt17 = 2;
/*      */ 
/* 1712 */         break;
/*      */       case 43:
/* 1715 */         alt17 = 3;
/*      */ 
/* 1717 */         break;
/*      */       default:
/* 1719 */         NoViableAltException nvae = new NoViableAltException("", 17, 0, this.input);
/*      */ 
/* 1722 */         throw nvae;
/*      */       }
/*      */ 
/* 1726 */       switch (alt17)
/*      */       {
/*      */       case 1:
/* 1730 */         INCLUDE17 = (CommonTree)match(this.input, 42, FOLLOW_INCLUDE_in_mapTemplateRef680);
/*      */ 
/* 1732 */         match(this.input, 2, null);
/* 1733 */         ID19 = (CommonTree)match(this.input, 25, FOLLOW_ID_in_mapTemplateRef682);
/*      */ 
/* 1735 */         for (int i = 1; i <= num_exprs; i++) emit(INCLUDE17, (short)44);
/*      */ 
/* 1737 */         pushFollow(FOLLOW_args_in_mapTemplateRef692);
/* 1738 */         args18 = args();
/*      */ 
/* 1740 */         this.state._fsp -= 1;
/*      */ 
/* 1743 */         match(this.input, 3, null);
/*      */ 
/* 1747 */         if ((args18 != null) && (args18.passThru)) emit1((CommonTree)retval.start, (short)22, ID19 != null ? ID19.getText() : null);
/* 1748 */         if ((args18 != null) && (args18.namedArgs)) emit1(INCLUDE17, (short)10, ID19 != null ? ID19.getText() : null); else {
/* 1749 */           emit2(INCLUDE17, (short)8, ID19 != null ? ID19.getText() : null, (args18 != null ? args18.n : 0) + num_exprs);
/*      */         }
/*      */ 
/* 1753 */         break;
/*      */       case 2:
/* 1757 */         pushFollow(FOLLOW_subtemplate_in_mapTemplateRef705);
/* 1758 */         subtemplate20 = subtemplate();
/*      */ 
/* 1760 */         this.state._fsp -= 1;
/*      */ 
/* 1764 */         if ((subtemplate20 != null ? subtemplate20.nargs : 0) != num_exprs) {
/* 1765 */           this.errMgr.compileTimeError(ErrorType.ANON_ARGUMENT_MISMATCH, this.templateToken, (subtemplate20 != null ? (CommonTree)subtemplate20.start : null).token, Integer.valueOf(subtemplate20 != null ? subtemplate20.nargs : 0), Integer.valueOf(num_exprs));
/*      */         }
/*      */ 
/* 1768 */         for (int i = 1; i <= num_exprs; i++) emit(null, subtemplate20 != null ? (CommonTree)subtemplate20.start : (short)44);
/* 1769 */         emit2(null, subtemplate20 != null ? (CommonTree)subtemplate20.start : (short)8, subtemplate20 != null ? subtemplate20.name : null, num_exprs);
/*      */ 
/* 1775 */         break;
/*      */       case 3:
/* 1779 */         INCLUDE_IND21 = (CommonTree)match(this.input, 43, FOLLOW_INCLUDE_IND_in_mapTemplateRef717);
/*      */ 
/* 1781 */         match(this.input, 2, null);
/* 1782 */         pushFollow(FOLLOW_expr_in_mapTemplateRef719);
/* 1783 */         expr();
/*      */ 
/* 1785 */         this.state._fsp -= 1;
/*      */ 
/* 1789 */         emit(INCLUDE_IND21, (short)26);
/* 1790 */         for (int i = 1; i <= num_exprs; i++) emit(INCLUDE_IND21, (short)44);
/*      */ 
/* 1793 */         pushFollow(FOLLOW_args_in_mapTemplateRef729);
/* 1794 */         args22 = args();
/*      */ 
/* 1796 */         this.state._fsp -= 1;
/*      */ 
/* 1800 */         emit1(INCLUDE_IND21, (short)9, (args22 != null ? args22.n : 0) + num_exprs);
/*      */ 
/* 1803 */         match(this.input, 3, null);
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (RecognitionException re)
/*      */     {
/* 1811 */       re = 
/* 1818 */         re;
/*      */ 
/* 1812 */       reportError(re);
/* 1813 */       recover(this.input, re);
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*      */ 
/* 1819 */     return retval;
/*      */   }
/*      */ 
/*      */   public final includeExpr_return includeExpr()
/*      */     throws RecognitionException
/*      */   {
/* 1831 */     includeExpr_return retval = new includeExpr_return();
/* 1832 */     retval.start = this.input.LT(1);
/*      */ 
/* 1835 */     CommonTree ID23 = null;
/* 1836 */     CommonTree ID25 = null;
/* 1837 */     CommonTree INCLUDE26 = null;
/* 1838 */     CommonTree ID28 = null;
/* 1839 */     CommonTree INCLUDE_SUPER29 = null;
/* 1840 */     CommonTree ID30 = null;
/* 1841 */     CommonTree INCLUDE_REGION31 = null;
/* 1842 */     CommonTree ID32 = null;
/* 1843 */     CommonTree INCLUDE_SUPER_REGION33 = null;
/* 1844 */     args_return args24 = null;
/*      */ 
/* 1846 */     args_return args27 = null;
/*      */     try
/*      */     {
/* 1851 */       int alt19 = 6;
/* 1852 */       switch (this.input.LA(1))
/*      */       {
/*      */       case 40:
/* 1855 */         alt19 = 1;
/*      */ 
/* 1857 */         break;
/*      */       case 42:
/* 1860 */         alt19 = 2;
/*      */ 
/* 1862 */         break;
/*      */       case 45:
/* 1865 */         alt19 = 3;
/*      */ 
/* 1867 */         break;
/*      */       case 44:
/* 1870 */         alt19 = 4;
/*      */ 
/* 1872 */         break;
/*      */       case 46:
/* 1875 */         alt19 = 5;
/*      */ 
/* 1877 */         break;
/*      */       case 25:
/*      */       case 26:
/*      */       case 35:
/*      */       case 36:
/*      */       case 43:
/*      */       case 48:
/*      */       case 55:
/*      */       case 56:
/* 1887 */         alt19 = 6;
/*      */ 
/* 1889 */         break;
/*      */       case 27:
/*      */       case 28:
/*      */       case 29:
/*      */       case 30:
/*      */       case 31:
/*      */       case 32:
/*      */       case 33:
/*      */       case 34:
/*      */       case 37:
/*      */       case 38:
/*      */       case 39:
/*      */       case 41:
/*      */       case 47:
/*      */       case 49:
/*      */       case 50:
/*      */       case 51:
/*      */       case 52:
/*      */       case 53:
/*      */       case 54:
/*      */       default:
/* 1891 */         NoViableAltException nvae = new NoViableAltException("", 19, 0, this.input);
/*      */ 
/* 1894 */         throw nvae;
/*      */       }
/*      */ 
/* 1898 */       switch (alt19)
/*      */       {
/*      */       case 1:
/* 1902 */         match(this.input, 40, FOLLOW_EXEC_FUNC_in_includeExpr751);
/*      */ 
/* 1904 */         match(this.input, 2, null);
/* 1905 */         ID23 = (CommonTree)match(this.input, 25, FOLLOW_ID_in_includeExpr753);
/*      */ 
/* 1908 */         int alt18 = 2;
/* 1909 */         int LA18_0 = this.input.LA(1);
/*      */ 
/* 1911 */         if (((LA18_0 >= 25) && (LA18_0 <= 26)) || ((LA18_0 >= 35) && (LA18_0 <= 36)) || (LA18_0 == 40) || ((LA18_0 >= 42) && (LA18_0 <= 46)) || ((LA18_0 >= 48) && (LA18_0 <= 49)) || ((LA18_0 >= 52) && (LA18_0 <= 53)) || ((LA18_0 >= 55) && (LA18_0 <= 57))) {
/* 1912 */           alt18 = 1;
/*      */         }
/* 1914 */         switch (alt18)
/*      */         {
/*      */         case 1:
/* 1918 */           pushFollow(FOLLOW_expr_in_includeExpr755);
/* 1919 */           expr();
/*      */ 
/* 1921 */           this.state._fsp -= 1;
/*      */         }
/*      */ 
/* 1930 */         match(this.input, 3, null);
/*      */ 
/* 1933 */         func(ID23);
/*      */ 
/* 1936 */         break;
/*      */       case 2:
/* 1940 */         INCLUDE26 = (CommonTree)match(this.input, 42, FOLLOW_INCLUDE_in_includeExpr766);
/*      */ 
/* 1942 */         match(this.input, 2, null);
/* 1943 */         ID25 = (CommonTree)match(this.input, 25, FOLLOW_ID_in_includeExpr768);
/*      */ 
/* 1945 */         pushFollow(FOLLOW_args_in_includeExpr770);
/* 1946 */         args24 = args();
/*      */ 
/* 1948 */         this.state._fsp -= 1;
/*      */ 
/* 1951 */         match(this.input, 3, null);
/*      */ 
/* 1955 */         if ((args24 != null) && (args24.passThru)) emit1((CommonTree)retval.start, (short)22, ID25 != null ? ID25.getText() : null);
/* 1956 */         if ((args24 != null) && (args24.namedArgs)) emit1(INCLUDE26, (short)10, ID25 != null ? ID25.getText() : null); else {
/* 1957 */           emit2(INCLUDE26, (short)8, ID25 != null ? ID25.getText() : null, args24 != null ? args24.n : 0);
/*      */         }
/*      */ 
/* 1961 */         break;
/*      */       case 3:
/* 1965 */         INCLUDE_SUPER29 = (CommonTree)match(this.input, 45, FOLLOW_INCLUDE_SUPER_in_includeExpr781);
/*      */ 
/* 1967 */         match(this.input, 2, null);
/* 1968 */         ID28 = (CommonTree)match(this.input, 25, FOLLOW_ID_in_includeExpr783);
/*      */ 
/* 1970 */         pushFollow(FOLLOW_args_in_includeExpr785);
/* 1971 */         args27 = args();
/*      */ 
/* 1973 */         this.state._fsp -= 1;
/*      */ 
/* 1976 */         match(this.input, 3, null);
/*      */ 
/* 1980 */         if ((args27 != null) && (args27.passThru)) emit1((CommonTree)retval.start, (short)22, ID28 != null ? ID28.getText() : null);
/* 1981 */         if ((args27 != null) && (args27.namedArgs)) emit1(INCLUDE_SUPER29, (short)12, ID28 != null ? ID28.getText() : null); else {
/* 1982 */           emit2(INCLUDE_SUPER29, (short)11, ID28 != null ? ID28.getText() : null, args27 != null ? args27.n : 0);
/*      */         }
/*      */ 
/* 1986 */         break;
/*      */       case 4:
/* 1990 */         INCLUDE_REGION31 = (CommonTree)match(this.input, 44, FOLLOW_INCLUDE_REGION_in_includeExpr796);
/*      */ 
/* 1992 */         match(this.input, 2, null);
/* 1993 */         ID30 = (CommonTree)match(this.input, 25, FOLLOW_ID_in_includeExpr798);
/*      */ 
/* 1995 */         match(this.input, 3, null);
/*      */ 
/* 1999 */         CompiledST impl = Compiler.defineBlankRegion(this.outermostImpl, ID30.token);
/*      */ 
/* 2002 */         emit2(INCLUDE_REGION31, (short)8, impl.name, 0);
/*      */ 
/* 2006 */         break;
/*      */       case 5:
/* 2010 */         INCLUDE_SUPER_REGION33 = (CommonTree)match(this.input, 46, FOLLOW_INCLUDE_SUPER_REGION_in_includeExpr808);
/*      */ 
/* 2012 */         match(this.input, 2, null);
/* 2013 */         ID32 = (CommonTree)match(this.input, 25, FOLLOW_ID_in_includeExpr810);
/*      */ 
/* 2015 */         match(this.input, 3, null);
/*      */ 
/* 2019 */         String mangled = STGroup.getMangledRegionName(this.outermostImpl.name, ID32 != null ? ID32.getText() : null);
/*      */ 
/* 2021 */         emit2(INCLUDE_SUPER_REGION33, (short)11, mangled, 0);
/*      */ 
/* 2025 */         break;
/*      */       case 6:
/* 2029 */         pushFollow(FOLLOW_primary_in_includeExpr818);
/* 2030 */         primary();
/*      */ 
/* 2032 */         this.state._fsp -= 1;
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (RecognitionException re)
/*      */     {
/* 2040 */       re = 
/* 2047 */         re;
/*      */ 
/* 2041 */       reportError(re);
/* 2042 */       recover(this.input, re);
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*      */ 
/* 2048 */     return retval;
/*      */   }
/*      */ 
/*      */   public final primary_return primary()
/*      */     throws RecognitionException
/*      */   {
/* 2060 */     primary_return retval = new primary_return();
/* 2061 */     retval.start = this.input.LT(1);
/*      */ 
/* 2064 */     CommonTree ID34 = null;
/* 2065 */     CommonTree STRING35 = null;
/* 2066 */     CommonTree TRUE36 = null;
/* 2067 */     CommonTree FALSE37 = null;
/* 2068 */     CommonTree INCLUDE_IND39 = null;
/* 2069 */     CommonTree TO_STR41 = null;
/* 2070 */     subtemplate_return subtemplate38 = null;
/*      */ 
/* 2072 */     args_return args40 = null;
/*      */     try
/*      */     {
/* 2077 */       int alt20 = 8;
/* 2078 */       switch (this.input.LA(1))
/*      */       {
/*      */       case 25:
/* 2081 */         alt20 = 1;
/*      */ 
/* 2083 */         break;
/*      */       case 26:
/* 2086 */         alt20 = 2;
/*      */ 
/* 2088 */         break;
/*      */       case 35:
/* 2091 */         alt20 = 3;
/*      */ 
/* 2093 */         break;
/*      */       case 36:
/* 2096 */         alt20 = 4;
/*      */ 
/* 2098 */         break;
/*      */       case 55:
/* 2101 */         alt20 = 5;
/*      */ 
/* 2103 */         break;
/*      */       case 48:
/* 2106 */         alt20 = 6;
/*      */ 
/* 2108 */         break;
/*      */       case 43:
/* 2111 */         alt20 = 7;
/*      */ 
/* 2113 */         break;
/*      */       case 56:
/* 2116 */         alt20 = 8;
/*      */ 
/* 2118 */         break;
/*      */       default:
/* 2120 */         NoViableAltException nvae = new NoViableAltException("", 20, 0, this.input);
/*      */ 
/* 2123 */         throw nvae;
/*      */       }
/*      */ 
/* 2127 */       switch (alt20)
/*      */       {
/*      */       case 1:
/* 2131 */         ID34 = (CommonTree)match(this.input, 25, FOLLOW_ID_in_primary829);
/*      */ 
/* 2133 */         refAttr(ID34);
/*      */ 
/* 2136 */         break;
/*      */       case 2:
/* 2140 */         STRING35 = (CommonTree)match(this.input, 26, FOLLOW_STRING_in_primary839);
/*      */ 
/* 2142 */         emit1(STRING35, (short)1, Misc.strip(STRING35 != null ? STRING35.getText() : null, 1));
/*      */ 
/* 2145 */         break;
/*      */       case 3:
/* 2149 */         TRUE36 = (CommonTree)match(this.input, 35, FOLLOW_TRUE_in_primary848);
/*      */ 
/* 2151 */         emit(TRUE36, (short)45);
/*      */ 
/* 2154 */         break;
/*      */       case 4:
/* 2158 */         FALSE37 = (CommonTree)match(this.input, 36, FOLLOW_FALSE_in_primary857);
/*      */ 
/* 2160 */         emit(FALSE37, (short)46);
/*      */ 
/* 2163 */         break;
/*      */       case 5:
/* 2167 */         pushFollow(FOLLOW_subtemplate_in_primary866);
/* 2168 */         subtemplate38 = subtemplate();
/*      */ 
/* 2170 */         this.state._fsp -= 1;
/*      */ 
/* 2173 */         emit2((CommonTree)retval.start, (short)8, subtemplate38 != null ? subtemplate38.name : null, 0);
/*      */ 
/* 2176 */         break;
/*      */       case 6:
/* 2180 */         pushFollow(FOLLOW_list_in_primary893);
/* 2181 */         list();
/*      */ 
/* 2183 */         this.state._fsp -= 1;
/*      */ 
/* 2187 */         break;
/*      */       case 7:
/* 2191 */         INCLUDE_IND39 = (CommonTree)match(this.input, 43, FOLLOW_INCLUDE_IND_in_primary900);
/*      */ 
/* 2193 */         match(this.input, 2, null);
/* 2194 */         pushFollow(FOLLOW_expr_in_primary905);
/* 2195 */         expr();
/*      */ 
/* 2197 */         this.state._fsp -= 1;
/*      */ 
/* 2200 */         emit(INCLUDE_IND39, (short)26);
/*      */ 
/* 2202 */         pushFollow(FOLLOW_args_in_primary914);
/* 2203 */         args40 = args();
/*      */ 
/* 2205 */         this.state._fsp -= 1;
/*      */ 
/* 2208 */         emit1(INCLUDE_IND39, (short)9, args40 != null ? args40.n : 0);
/*      */ 
/* 2210 */         match(this.input, 3, null);
/*      */ 
/* 2214 */         break;
/*      */       case 8:
/* 2218 */         TO_STR41 = (CommonTree)match(this.input, 56, FOLLOW_TO_STR_in_primary934);
/*      */ 
/* 2220 */         match(this.input, 2, null);
/* 2221 */         pushFollow(FOLLOW_expr_in_primary936);
/* 2222 */         expr();
/*      */ 
/* 2224 */         this.state._fsp -= 1;
/*      */ 
/* 2227 */         match(this.input, 3, null);
/*      */ 
/* 2230 */         emit(TO_STR41, (short)26);
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (RecognitionException re)
/*      */     {
/* 2237 */       re = 
/* 2244 */         re;
/*      */ 
/* 2238 */       reportError(re);
/* 2239 */       recover(this.input, re);
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*      */ 
/* 2245 */     return retval;
/*      */   }
/*      */ 
/*      */   public final void arg()
/*      */     throws RecognitionException
/*      */   {
/*      */     try
/*      */     {
/* 2258 */       pushFollow(FOLLOW_expr_in_arg949);
/* 2259 */       expr();
/*      */ 
/* 2261 */       this.state._fsp -= 1;
/*      */     }
/*      */     catch (RecognitionException re)
/*      */     {
/* 2267 */       re = 
/* 2274 */         re;
/*      */ 
/* 2268 */       reportError(re);
/* 2269 */       recover(this.input, re);
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public final args_return args()
/*      */     throws RecognitionException
/*      */   {
/* 2290 */     args_return retval = new args_return();
/* 2291 */     retval.start = this.input.LT(1);
/*      */ 
/* 2294 */     CommonTree eq = null;
/* 2295 */     CommonTree ID42 = null;
/*      */     try
/*      */     {
/* 2299 */       int alt24 = 4;
/* 2300 */       switch (this.input.LA(1))
/*      */       {
/*      */       case 25:
/*      */       case 26:
/*      */       case 35:
/*      */       case 36:
/*      */       case 40:
/*      */       case 42:
/*      */       case 43:
/*      */       case 44:
/*      */       case 45:
/*      */       case 46:
/*      */       case 48:
/*      */       case 49:
/*      */       case 52:
/*      */       case 53:
/*      */       case 55:
/*      */       case 56:
/*      */       case 57:
/* 2319 */         alt24 = 1;
/*      */ 
/* 2321 */         break;
/*      */       case 12:
/* 2324 */         alt24 = 2;
/*      */ 
/* 2326 */         break;
/*      */       case 11:
/* 2329 */         alt24 = 3;
/*      */ 
/* 2331 */         break;
/*      */       case 3:
/* 2334 */         alt24 = 4;
/*      */ 
/* 2336 */         break;
/*      */       case 4:
/*      */       case 5:
/*      */       case 6:
/*      */       case 7:
/*      */       case 8:
/*      */       case 9:
/*      */       case 10:
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
/*      */       case 27:
/*      */       case 28:
/*      */       case 29:
/*      */       case 30:
/*      */       case 31:
/*      */       case 32:
/*      */       case 33:
/*      */       case 34:
/*      */       case 37:
/*      */       case 38:
/*      */       case 39:
/*      */       case 41:
/*      */       case 47:
/*      */       case 50:
/*      */       case 51:
/*      */       case 54:
/*      */       default:
/* 2338 */         NoViableAltException nvae = new NoViableAltException("", 24, 0, this.input);
/*      */ 
/* 2341 */         throw nvae;
/*      */       }
/*      */ 
/* 2345 */       switch (alt24)
/*      */       {
/*      */       case 1:
/* 2350 */         int cnt21 = 0;
/*      */         while (true)
/*      */         {
/* 2353 */           int alt21 = 2;
/* 2354 */           int LA21_0 = this.input.LA(1);
/*      */ 
/* 2356 */           if (((LA21_0 >= 25) && (LA21_0 <= 26)) || ((LA21_0 >= 35) && (LA21_0 <= 36)) || (LA21_0 == 40) || ((LA21_0 >= 42) && (LA21_0 <= 46)) || ((LA21_0 >= 48) && (LA21_0 <= 49)) || ((LA21_0 >= 52) && (LA21_0 <= 53)) || ((LA21_0 >= 55) && (LA21_0 <= 57))) {
/* 2357 */             alt21 = 1;
/*      */           }
/*      */ 
/* 2361 */           switch (alt21)
/*      */           {
/*      */           case 1:
/* 2365 */             pushFollow(FOLLOW_arg_in_args965);
/* 2366 */             arg();
/*      */ 
/* 2368 */             this.state._fsp -= 1;
/*      */ 
/* 2371 */             retval.n += 1;
/*      */ 
/* 2374 */             break;
/*      */           default:
/* 2377 */             if (cnt21 >= 1) break label550;
/* 2378 */             EarlyExitException eee = new EarlyExitException(21, this.input);
/*      */ 
/* 2380 */             throw eee;
/*      */           }
/* 2382 */           cnt21++;
/*      */         }
/*      */ 
/* 2387 */         break;
/*      */       case 2:
/* 2391 */         emit((CommonTree)retval.start, (short)21); retval.namedArgs = true;
/*      */ 
/* 2394 */         int cnt22 = 0;
/*      */         while (true)
/*      */         {
/* 2397 */           int alt22 = 2;
/* 2398 */           int LA22_0 = this.input.LA(1);
/*      */ 
/* 2400 */           if (LA22_0 == 12) {
/* 2401 */             alt22 = 1;
/*      */           }
/*      */ 
/* 2405 */           switch (alt22)
/*      */           {
/*      */           case 1:
/* 2409 */             eq = (CommonTree)match(this.input, 12, FOLLOW_EQUALS_in_args984);
/*      */ 
/* 2411 */             match(this.input, 2, null);
/* 2412 */             ID42 = (CommonTree)match(this.input, 25, FOLLOW_ID_in_args986);
/*      */ 
/* 2414 */             pushFollow(FOLLOW_expr_in_args988);
/* 2415 */             expr();
/*      */ 
/* 2417 */             this.state._fsp -= 1;
/*      */ 
/* 2420 */             match(this.input, 3, null);
/*      */ 
/* 2423 */             retval.n += 1; emit1(eq, (short)7, defineString(ID42 != null ? ID42.getText() : null));
/*      */ 
/* 2426 */             break;
/*      */           default:
/* 2429 */             if (cnt22 >= 1) break label769;
/* 2430 */             EarlyExitException eee = new EarlyExitException(22, this.input);
/*      */ 
/* 2432 */             throw eee;
/*      */           }
/* 2434 */           cnt22++;
/*      */         }
/*      */ 
/* 2439 */         int alt23 = 2;
/* 2440 */         int LA23_0 = this.input.LA(1);
/*      */ 
/* 2442 */         if (LA23_0 == 11) {
/* 2443 */           alt23 = 1;
/*      */         }
/* 2445 */         switch (alt23)
/*      */         {
/*      */         case 1:
/* 2449 */           match(this.input, 11, FOLLOW_ELLIPSIS_in_args1005);
/*      */ 
/* 2451 */           retval.passThru = true;
/*      */         }
/*      */ 
/* 2460 */         break;
/*      */       case 3:
/* 2464 */         label550: match(this.input, 11, FOLLOW_ELLIPSIS_in_args1020);
/*      */ 
/* 2466 */         label769: retval.passThru = true; emit((CommonTree)retval.start, (short)21); retval.namedArgs = true;
/*      */ 
/* 2469 */         break;
/*      */       case 4:
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (RecognitionException re)
/*      */     {
/* 2478 */       re = 
/* 2485 */         re;
/*      */ 
/* 2479 */       reportError(re);
/* 2480 */       recover(this.input, re);
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*      */ 
/* 2486 */     return retval;
/*      */   }
/*      */ 
/*      */   public final void list()
/*      */     throws RecognitionException
/*      */   {
/* 2495 */     listElement_return listElement43 = null;
/*      */     try
/*      */     {
/* 2502 */       emit((short)24);
/*      */ 
/* 2504 */       match(this.input, 48, FOLLOW_LIST_in_list1040);
/*      */ 
/* 2506 */       if (this.input.LA(1) == 2) {
/* 2507 */         match(this.input, 2, null);
/*      */         while (true)
/*      */         {
/* 2511 */           int alt25 = 2;
/* 2512 */           int LA25_0 = this.input.LA(1);
/*      */ 
/* 2514 */           if (((LA25_0 >= 25) && (LA25_0 <= 26)) || ((LA25_0 >= 35) && (LA25_0 <= 36)) || (LA25_0 == 40) || ((LA25_0 >= 42) && (LA25_0 <= 46)) || ((LA25_0 >= 48) && (LA25_0 <= 50)) || ((LA25_0 >= 52) && (LA25_0 <= 53)) || ((LA25_0 >= 55) && (LA25_0 <= 57))) {
/* 2515 */             alt25 = 1;
/*      */           }
/*      */ 
/* 2519 */           switch (alt25)
/*      */           {
/*      */           case 1:
/* 2523 */             pushFollow(FOLLOW_listElement_in_list1043);
/* 2524 */             listElement43 = listElement();
/*      */ 
/* 2526 */             this.state._fsp -= 1;
/*      */ 
/* 2529 */             emit(null, listElement43 != null ? (CommonTree)listElement43.start : (short)25);
/*      */ 
/* 2532 */             break;
/*      */           default:
/* 2535 */             break label215;
/*      */           }
/*      */ 
/*      */         }
/*      */ 
/* 2540 */         label215: match(this.input, 3, null);
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (RecognitionException re)
/*      */     {
/* 2547 */       re = 
/* 2554 */         re;
/*      */ 
/* 2548 */       reportError(re);
/* 2549 */       recover(this.input, re);
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*      */   }
/*      */ 
/*      */   public final listElement_return listElement()
/*      */     throws RecognitionException
/*      */   {
/* 2567 */     listElement_return retval = new listElement_return();
/* 2568 */     retval.start = this.input.LT(1);
/*      */ 
/* 2571 */     CommonTree NULL44 = null;
/*      */     try
/*      */     {
/* 2575 */       int alt26 = 2;
/* 2576 */       int LA26_0 = this.input.LA(1);
/*      */ 
/* 2578 */       if (((LA26_0 >= 25) && (LA26_0 <= 26)) || ((LA26_0 >= 35) && (LA26_0 <= 36)) || (LA26_0 == 40) || ((LA26_0 >= 42) && (LA26_0 <= 46)) || ((LA26_0 >= 48) && (LA26_0 <= 49)) || ((LA26_0 >= 52) && (LA26_0 <= 53)) || ((LA26_0 >= 55) && (LA26_0 <= 57))) {
/* 2579 */         alt26 = 1;
/*      */       }
/* 2581 */       else if (LA26_0 == 50) {
/* 2582 */         alt26 = 2;
/*      */       }
/*      */       else {
/* 2585 */         NoViableAltException nvae = new NoViableAltException("", 26, 0, this.input);
/*      */ 
/* 2588 */         throw nvae;
/*      */       }
/*      */ 
/* 2591 */       switch (alt26)
/*      */       {
/*      */       case 1:
/* 2595 */         pushFollow(FOLLOW_expr_in_listElement1059);
/* 2596 */         expr();
/*      */ 
/* 2598 */         this.state._fsp -= 1;
/*      */ 
/* 2602 */         break;
/*      */       case 2:
/* 2606 */         NULL44 = (CommonTree)match(this.input, 50, FOLLOW_NULL_in_listElement1063);
/*      */ 
/* 2608 */         emit(NULL44, (short)44);
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (RecognitionException re)
/*      */     {
/* 2615 */       re = 
/* 2622 */         re;
/*      */ 
/* 2616 */       reportError(re);
/* 2617 */       recover(this.input, re);
/*      */     }
/*      */     finally
/*      */     {
/*      */     }
/*      */ 
/* 2623 */     return retval;
/*      */   }
/*      */ 
/*      */   public static class listElement_return extends TreeRuleReturnScope
/*      */   {
/*      */   }
/*      */ 
/*      */   public static class args_return extends TreeRuleReturnScope
/*      */   {
/* 2281 */     public int n = 0;
/* 2282 */     public boolean namedArgs = false;
/*      */     public boolean passThru;
/*      */   }
/*      */ 
/*      */   public static class primary_return extends TreeRuleReturnScope
/*      */   {
/*      */   }
/*      */ 
/*      */   public static class includeExpr_return extends TreeRuleReturnScope
/*      */   {
/*      */   }
/*      */ 
/*      */   public static class mapTemplateRef_return extends TreeRuleReturnScope
/*      */   {
/*      */   }
/*      */ 
/*      */   public static class conditional_return extends TreeRuleReturnScope
/*      */   {
/*      */   }
/*      */ 
/*      */   public static class subtemplate_return extends TreeRuleReturnScope
/*      */   {
/*      */     public String name;
/*      */     public int nargs;
/*      */   }
/*      */ 
/*      */   public static class region_return extends TreeRuleReturnScope
/*      */   {
/*      */     public String name;
/*      */   }
/*      */ 
/*      */   protected static class template_scope
/*      */   {
/*      */     CompilationState state;
/*      */   }
/*      */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.stringtemplate.v4.compiler.CodeGenerator
 * JD-Core Version:    0.6.2
 */