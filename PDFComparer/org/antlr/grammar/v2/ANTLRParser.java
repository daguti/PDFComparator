/*      */ package org.antlr.grammar.v2;
/*      */ 
/*      */ import antlr.ASTFactory;
/*      */ import antlr.ASTPair;
/*      */ import antlr.LLkParser;
/*      */ import antlr.NoViableAltException;
/*      */ import antlr.ParserSharedInputState;
/*      */ import antlr.RecognitionException;
/*      */ import antlr.Token;
/*      */ import antlr.TokenBuffer;
/*      */ import antlr.TokenStream;
/*      */ import antlr.TokenStreamException;
/*      */ import antlr.TokenWithIndex;
/*      */ import antlr.collections.AST;
/*      */ import antlr.collections.impl.ASTArray;
/*      */ import antlr.collections.impl.BitSet;
/*      */ import java.util.HashMap;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.Set;
/*      */ import org.antlr.misc.IntSet;
/*      */ import org.antlr.tool.ErrorManager;
/*      */ import org.antlr.tool.Grammar;
/*      */ import org.antlr.tool.GrammarAST;
/*      */ 
/*      */ public class ANTLRParser extends LLkParser
/*      */   implements ANTLRTokenTypes
/*      */ {
/*   88 */   protected Grammar grammar = null;
/*   89 */   protected int gtype = 0;
/*      */ 
/*  107 */   protected String currentRuleName = null;
/*  108 */   protected GrammarAST currentBlockAST = null;
/*      */   protected boolean atTreeRoot;
/* 4490 */   public static final String[] _tokenNames = { "<0>", "EOF", "<2>", "NULL_TREE_LOOKAHEAD", "\"options\"", "\"tokens\"", "\"parser\"", "LEXER", "RULE", "BLOCK", "OPTIONAL", "CLOSURE", "POSITIVE_CLOSURE", "SYNPRED", "RANGE", "CHAR_RANGE", "EPSILON", "ALT", "EOR", "EOB", "EOA", "ID", "ARG", "ARGLIST", "RET", "LEXER_GRAMMAR", "PARSER_GRAMMAR", "TREE_GRAMMAR", "COMBINED_GRAMMAR", "INITACTION", "FORCED_ACTION", "LABEL", "TEMPLATE", "\"scope\"", "\"import\"", "GATED_SEMPRED", "SYN_SEMPRED", "BACKTRACK_SEMPRED", "\"fragment\"", "DOT", "ACTION", "DOC_COMMENT", "SEMI", "\"lexer\"", "\"tree\"", "\"grammar\"", "AMPERSAND", "COLON", "RCURLY", "ASSIGN", "STRING_LITERAL", "CHAR_LITERAL", "INT", "STAR", "COMMA", "TOKEN_REF", "\"protected\"", "\"public\"", "\"private\"", "BANG", "ARG_ACTION", "\"returns\"", "\"throws\"", "LPAREN", "OR", "RPAREN", "\"catch\"", "\"finally\"", "PLUS_ASSIGN", "SEMPRED", "IMPLIES", "ROOT", "WILDCARD", "RULE_REF", "NOT", "TREE_BEGIN", "QUESTION", "PLUS", "OPEN_ELEMENT_OPTION", "CLOSE_ELEMENT_OPTION", "REWRITE", "ETC", "DOLLAR", "DOUBLE_QUOTE_STRING_LITERAL", "DOUBLE_ANGLE_STRING_LITERAL", "WS", "COMMENT", "SL_COMMENT", "ML_COMMENT", "STRAY_BRACKET", "ESC", "DIGIT", "XDIGIT", "NESTED_ARG_ACTION", "NESTED_ACTION", "ACTION_CHAR_LITERAL", "ACTION_STRING_LITERAL", "ACTION_ESC", "WS_LOOP", "INTERNAL_RULE_REF", "WS_OPT", "SRC" };
/*      */ 
/* 4603 */   public static final BitSet _tokenSet_0 = new BitSet(mk_tokenSet_0());
/*      */ 
/* 4608 */   public static final BitSet _tokenSet_1 = new BitSet(mk_tokenSet_1());
/*      */ 
/* 4613 */   public static final BitSet _tokenSet_2 = new BitSet(mk_tokenSet_2());
/*      */ 
/* 4618 */   public static final BitSet _tokenSet_3 = new BitSet(mk_tokenSet_3());
/*      */ 
/* 4623 */   public static final BitSet _tokenSet_4 = new BitSet(mk_tokenSet_4());
/*      */ 
/* 4628 */   public static final BitSet _tokenSet_5 = new BitSet(mk_tokenSet_5());
/*      */ 
/* 4633 */   public static final BitSet _tokenSet_6 = new BitSet(mk_tokenSet_6());
/*      */ 
/* 4638 */   public static final BitSet _tokenSet_7 = new BitSet(mk_tokenSet_7());
/*      */ 
/* 4643 */   public static final BitSet _tokenSet_8 = new BitSet(mk_tokenSet_8());
/*      */ 
/* 4648 */   public static final BitSet _tokenSet_9 = new BitSet(mk_tokenSet_9());
/*      */ 
/* 4653 */   public static final BitSet _tokenSet_10 = new BitSet(mk_tokenSet_10());
/*      */ 
/* 4658 */   public static final BitSet _tokenSet_11 = new BitSet(mk_tokenSet_11());
/*      */ 
/* 4663 */   public static final BitSet _tokenSet_12 = new BitSet(mk_tokenSet_12());
/*      */ 
/* 4668 */   public static final BitSet _tokenSet_13 = new BitSet(mk_tokenSet_13());
/*      */ 
/* 4673 */   public static final BitSet _tokenSet_14 = new BitSet(mk_tokenSet_14());
/*      */ 
/* 4678 */   public static final BitSet _tokenSet_15 = new BitSet(mk_tokenSet_15());
/*      */ 
/* 4683 */   public static final BitSet _tokenSet_16 = new BitSet(mk_tokenSet_16());
/*      */ 
/* 4688 */   public static final BitSet _tokenSet_17 = new BitSet(mk_tokenSet_17());
/*      */ 
/* 4693 */   public static final BitSet _tokenSet_18 = new BitSet(mk_tokenSet_18());
/*      */ 
/* 4698 */   public static final BitSet _tokenSet_19 = new BitSet(mk_tokenSet_19());
/*      */ 
/* 4703 */   public static final BitSet _tokenSet_20 = new BitSet(mk_tokenSet_20());
/*      */ 
/* 4708 */   public static final BitSet _tokenSet_21 = new BitSet(mk_tokenSet_21());
/*      */ 
/* 4713 */   public static final BitSet _tokenSet_22 = new BitSet(mk_tokenSet_22());
/*      */ 
/* 4718 */   public static final BitSet _tokenSet_23 = new BitSet(mk_tokenSet_23());
/*      */ 
/* 4723 */   public static final BitSet _tokenSet_24 = new BitSet(mk_tokenSet_24());
/*      */ 
/* 4728 */   public static final BitSet _tokenSet_25 = new BitSet(mk_tokenSet_25());
/*      */ 
/* 4733 */   public static final BitSet _tokenSet_26 = new BitSet(mk_tokenSet_26());
/*      */ 
/* 4738 */   public static final BitSet _tokenSet_27 = new BitSet(mk_tokenSet_27());
/*      */ 
/* 4743 */   public static final BitSet _tokenSet_28 = new BitSet(mk_tokenSet_28());
/*      */ 
/* 4748 */   public static final BitSet _tokenSet_29 = new BitSet(mk_tokenSet_29());
/*      */ 
/* 4753 */   public static final BitSet _tokenSet_30 = new BitSet(mk_tokenSet_30());
/*      */ 
/* 4758 */   public static final BitSet _tokenSet_31 = new BitSet(mk_tokenSet_31());
/*      */ 
/* 4763 */   public static final BitSet _tokenSet_32 = new BitSet(mk_tokenSet_32());
/*      */ 
/* 4768 */   public static final BitSet _tokenSet_33 = new BitSet(mk_tokenSet_33());
/*      */ 
/* 4773 */   public static final BitSet _tokenSet_34 = new BitSet(mk_tokenSet_34());
/*      */ 
/* 4778 */   public static final BitSet _tokenSet_35 = new BitSet(mk_tokenSet_35());
/*      */ 
/* 4783 */   public static final BitSet _tokenSet_36 = new BitSet(mk_tokenSet_36());
/*      */ 
/* 4788 */   public static final BitSet _tokenSet_37 = new BitSet(mk_tokenSet_37());
/*      */ 
/* 4793 */   public static final BitSet _tokenSet_38 = new BitSet(mk_tokenSet_38());
/*      */ 
/* 4798 */   public static final BitSet _tokenSet_39 = new BitSet(mk_tokenSet_39());
/*      */ 
/* 4803 */   public static final BitSet _tokenSet_40 = new BitSet(mk_tokenSet_40());
/*      */ 
/* 4808 */   public static final BitSet _tokenSet_41 = new BitSet(mk_tokenSet_41());
/*      */ 
/* 4813 */   public static final BitSet _tokenSet_42 = new BitSet(mk_tokenSet_42());
/*      */ 
/* 4818 */   public static final BitSet _tokenSet_43 = new BitSet(mk_tokenSet_43());
/*      */ 
/* 4823 */   public static final BitSet _tokenSet_44 = new BitSet(mk_tokenSet_44());
/*      */ 
/* 4828 */   public static final BitSet _tokenSet_45 = new BitSet(mk_tokenSet_45());
/*      */ 
/* 4833 */   public static final BitSet _tokenSet_46 = new BitSet(mk_tokenSet_46());
/*      */ 
/* 4838 */   public static final BitSet _tokenSet_47 = new BitSet(mk_tokenSet_47());
/*      */ 
/* 4843 */   public static final BitSet _tokenSet_48 = new BitSet(mk_tokenSet_48());
/*      */ 
/* 4848 */   public static final BitSet _tokenSet_49 = new BitSet(mk_tokenSet_49());
/*      */ 
/* 4853 */   public static final BitSet _tokenSet_50 = new BitSet(mk_tokenSet_50());
/*      */ 
/* 4858 */   public static final BitSet _tokenSet_51 = new BitSet(mk_tokenSet_51());
/*      */ 
/*      */   public Grammar getGrammar()
/*      */   {
/*   92 */     return this.grammar;
/*      */   }
/*      */ 
/*      */   public void setGrammar(Grammar grammar) {
/*   96 */     this.grammar = grammar;
/*      */   }
/*      */ 
/*      */   public int getGtype() {
/*  100 */     return this.gtype;
/*      */   }
/*      */ 
/*      */   public void setGtype(int gtype) {
/*  104 */     this.gtype = gtype;
/*      */   }
/*      */ 
/*      */   protected GrammarAST setToBlockWithSet(GrammarAST b)
/*      */   {
/*  112 */     GrammarAST alt = (GrammarAST)this.astFactory.make(new ASTArray(3).add((GrammarAST)this.astFactory.create(17, "ALT")).add(b).add((GrammarAST)this.astFactory.create(20, "<end-of-alt>")));
/*  113 */     prefixWithSynPred(alt);
/*  114 */     return (GrammarAST)this.astFactory.make(new ASTArray(3).add((GrammarAST)this.astFactory.create(9, "BLOCK")).add(alt).add((GrammarAST)this.astFactory.create(19, "<end-of-block>")));
/*      */   }
/*      */ 
/*      */   protected GrammarAST createBlockFromDupAlt(GrammarAST alt)
/*      */   {
/*  121 */     GrammarAST nalt = GrammarAST.dupTreeNoActions(alt, null);
/*  122 */     GrammarAST blk = (GrammarAST)this.astFactory.make(new ASTArray(3).add((GrammarAST)this.astFactory.create(9, "BLOCK")).add(nalt).add((GrammarAST)this.astFactory.create(19, "<end-of-block>")));
/*  123 */     return blk;
/*      */   }
/*      */ 
/*      */   protected void prefixWithSynPred(GrammarAST alt)
/*      */   {
/*  132 */     String autoBacktrack = (String)this.grammar.getBlockOption(this.currentBlockAST, "backtrack");
/*  133 */     if (autoBacktrack == null) {
/*  134 */       autoBacktrack = (String)this.grammar.getOption("backtrack");
/*      */     }
/*  136 */     if ((autoBacktrack != null) && (autoBacktrack.equals("true")) && ((this.gtype != 28) || (!Character.isUpperCase(this.currentRuleName.charAt(0)))) && (alt.getFirstChild().getType() != 36))
/*      */     {
/*  142 */       GrammarAST synpredBlockAST = createBlockFromDupAlt(alt);
/*      */ 
/*  146 */       GrammarAST synpredAST = createSynSemPredFromBlock(synpredBlockAST, 37);
/*      */ 
/*  150 */       synpredAST.getLastSibling().setNextSibling(alt.getFirstChild());
/*  151 */       alt.setFirstChild(synpredAST);
/*      */     }
/*      */   }
/*      */ 
/*      */   protected GrammarAST createSynSemPredFromBlock(GrammarAST synpredBlockAST, int synpredTokenType)
/*      */   {
/*  160 */     String predName = this.grammar.defineSyntacticPredicate(synpredBlockAST, this.currentRuleName);
/*      */ 
/*  163 */     String synpredinvoke = predName;
/*  164 */     GrammarAST p = (GrammarAST)this.astFactory.create(synpredTokenType, synpredinvoke);
/*      */ 
/*  166 */     this.grammar.blocksWithSynPreds.add(this.currentBlockAST);
/*  167 */     return p;
/*      */   }
/*      */ 
/*      */   public GrammarAST createSimpleRuleAST(String name, GrammarAST block, boolean fragment)
/*      */   {
/*  174 */     GrammarAST modifier = null;
/*  175 */     if (fragment) {
/*  176 */       modifier = (GrammarAST)this.astFactory.create(38, "fragment");
/*      */     }
/*  178 */     GrammarAST EORAST = (GrammarAST)this.astFactory.create(18, "<end-of-rule>");
/*  179 */     GrammarAST EOBAST = block.getLastChild();
/*  180 */     EORAST.setLine(EOBAST.getLine());
/*  181 */     EORAST.setColumn(EOBAST.getColumn());
/*  182 */     GrammarAST ruleAST = (GrammarAST)this.astFactory.make(new ASTArray(8).add((GrammarAST)this.astFactory.create(8, "rule")).add((GrammarAST)this.astFactory.create(21, name)).add(modifier).add((GrammarAST)this.astFactory.create(22, "ARG")).add((GrammarAST)this.astFactory.create(24, "RET")).add((GrammarAST)this.astFactory.create(33, "scope")).add(block).add(EORAST));
/*      */ 
/*  184 */     ruleAST.setLine(block.getLine());
/*  185 */     ruleAST.setColumn(block.getColumn());
/*  186 */     return ruleAST;
/*      */   }
/*      */ 
/*      */   public void reportError(RecognitionException ex) {
/*  190 */     Token token = null;
/*      */     try {
/*  192 */       token = LT(1);
/*      */     }
/*      */     catch (TokenStreamException tse) {
/*  195 */       ErrorManager.internalError("can't get token???", tse);
/*      */     }
/*  197 */     ErrorManager.syntaxError(100, this.grammar, token, "antlr: " + ex.toString(), ex);
/*      */   }
/*      */ 
/*      */   public void cleanup(GrammarAST root)
/*      */   {
/*      */     GrammarAST tokensRuleAST;
/*  206 */     if (this.gtype == 25) {
/*  207 */       String filter = (String)this.grammar.getOption("filter");
/*  208 */       tokensRuleAST = this.grammar.addArtificialMatchTokensRule(root, this.grammar.lexerRuleNamesInCombined, this.grammar.getDelegateNames(), (filter != null) && (filter.equals("true")));
/*      */     }
/*      */   }
/*      */ 
/*      */   protected ANTLRParser(TokenBuffer tokenBuf, int k)
/*      */   {
/*  218 */     super(tokenBuf, k);
/*  219 */     this.tokenNames = _tokenNames;
/*  220 */     buildTokenTypeASTClassMap();
/*  221 */     this.astFactory = new ASTFactory(getTokenTypeToASTClassMap());
/*      */   }
/*      */ 
/*      */   public ANTLRParser(TokenBuffer tokenBuf) {
/*  225 */     this(tokenBuf, 3);
/*      */   }
/*      */ 
/*      */   protected ANTLRParser(TokenStream lexer, int k) {
/*  229 */     super(lexer, k);
/*  230 */     this.tokenNames = _tokenNames;
/*  231 */     buildTokenTypeASTClassMap();
/*  232 */     this.astFactory = new ASTFactory(getTokenTypeToASTClassMap());
/*      */   }
/*      */ 
/*      */   public ANTLRParser(TokenStream lexer) {
/*  236 */     this(lexer, 3);
/*      */   }
/*      */ 
/*      */   public ANTLRParser(ParserSharedInputState state) {
/*  240 */     super(state, 3);
/*  241 */     this.tokenNames = _tokenNames;
/*  242 */     buildTokenTypeASTClassMap();
/*  243 */     this.astFactory = new ASTFactory(getTokenTypeToASTClassMap());
/*      */   }
/*      */ 
/*      */   public final void grammar(Grammar g)
/*      */     throws RecognitionException, TokenStreamException
/*      */   {
/*  250 */     this.returnAST = null;
/*  251 */     ASTPair currentAST = new ASTPair();
/*  252 */     GrammarAST grammar_AST = null;
/*  253 */     Token cmt = null;
/*  254 */     GrammarAST cmt_AST = null;
/*  255 */     GrammarAST gr_AST = null;
/*  256 */     GrammarAST gid_AST = null;
/*  257 */     GrammarAST ig_AST = null;
/*  258 */     GrammarAST ts_AST = null;
/*  259 */     GrammarAST scopes_AST = null;
/*  260 */     GrammarAST a_AST = null;
/*  261 */     GrammarAST r_AST = null;
/*      */ 
/*  263 */     this.grammar = g;
/*  264 */     GrammarAST opt = null;
/*  265 */     Token optionsStartToken = null;
/*      */ 
/*  268 */     this.astFactory = new ASTFactory()
/*      */     {
/*      */       public AST create(Token token)
/*      */       {
/*  274 */         AST t = super.create(token);
/*  275 */         ((GrammarAST)t).enclosingRuleName = ANTLRParser.this.currentRuleName;
/*  276 */         return t;
/*      */       }
/*      */       public AST create(int i) {
/*  279 */         AST t = super.create(i);
/*  280 */         ((GrammarAST)t).enclosingRuleName = ANTLRParser.this.currentRuleName;
/*  281 */         return t;
/*      */       }
/*      */ 
/*      */     };
/*      */     try
/*      */     {
/*  288 */       switch (LA(1))
/*      */       {
/*      */       case 40:
/*  291 */         GrammarAST tmp1_AST = null;
/*  292 */         tmp1_AST = (GrammarAST)this.astFactory.create(LT(1));
/*  293 */         match(40);
/*  294 */         break;
/*      */       case 6:
/*      */       case 41:
/*      */       case 43:
/*      */       case 44:
/*      */       case 45:
/*  302 */         break;
/*      */       default:
/*  306 */         throw new NoViableAltException(LT(1), getFilename());
/*      */       }
/*      */ 
/*  311 */       switch (LA(1))
/*      */       {
/*      */       case 41:
/*  314 */         cmt = LT(1);
/*  315 */         cmt_AST = (GrammarAST)this.astFactory.create(cmt);
/*  316 */         match(41);
/*  317 */         break;
/*      */       case 6:
/*      */       case 43:
/*      */       case 44:
/*      */       case 45:
/*  324 */         break;
/*      */       default:
/*  328 */         throw new NoViableAltException(LT(1), getFilename());
/*      */       }
/*      */ 
/*  332 */       grammarType();
/*  333 */       gr_AST = (GrammarAST)this.returnAST;
/*  334 */       id();
/*  335 */       gid_AST = (GrammarAST)this.returnAST;
/*  336 */       this.grammar.setName(gid_AST.getText());
/*  337 */       GrammarAST tmp2_AST = null;
/*  338 */       tmp2_AST = (GrammarAST)this.astFactory.create(LT(1));
/*  339 */       match(42);
/*      */ 
/*  341 */       switch (LA(1))
/*      */       {
/*      */       case 4:
/*  344 */         optionsStartToken = LT(1);
/*  345 */         Map opts = optionsSpec();
/*  346 */         this.grammar.setOptions(opts, optionsStartToken);
/*  347 */         opt = (GrammarAST)this.returnAST;
/*  348 */         break;
/*      */       case 5:
/*      */       case 33:
/*      */       case 34:
/*      */       case 38:
/*      */       case 41:
/*      */       case 46:
/*      */       case 55:
/*      */       case 56:
/*      */       case 57:
/*      */       case 58:
/*      */       case 73:
/*  362 */         break;
/*      */       default:
/*  366 */         throw new NoViableAltException(LT(1), getFilename());
/*      */       }
/*      */ 
/*  371 */       switch (LA(1))
/*      */       {
/*      */       case 34:
/*  374 */         delegateGrammars();
/*  375 */         ig_AST = (GrammarAST)this.returnAST;
/*  376 */         break;
/*      */       case 5:
/*      */       case 33:
/*      */       case 38:
/*      */       case 41:
/*      */       case 46:
/*      */       case 55:
/*      */       case 56:
/*      */       case 57:
/*      */       case 58:
/*      */       case 73:
/*  389 */         break;
/*      */       default:
/*  393 */         throw new NoViableAltException(LT(1), getFilename());
/*      */       }
/*      */ 
/*  398 */       switch (LA(1))
/*      */       {
/*      */       case 5:
/*  401 */         tokensSpec();
/*  402 */         ts_AST = (GrammarAST)this.returnAST;
/*  403 */         break;
/*      */       case 33:
/*      */       case 38:
/*      */       case 41:
/*      */       case 46:
/*      */       case 55:
/*      */       case 56:
/*      */       case 57:
/*      */       case 58:
/*      */       case 73:
/*  415 */         break;
/*      */       default:
/*  419 */         throw new NoViableAltException(LT(1), getFilename());
/*      */       }
/*      */ 
/*  423 */       attrScopes();
/*  424 */       scopes_AST = (GrammarAST)this.returnAST;
/*      */ 
/*  426 */       switch (LA(1))
/*      */       {
/*      */       case 46:
/*  429 */         actions();
/*  430 */         a_AST = (GrammarAST)this.returnAST;
/*  431 */         break;
/*      */       case 38:
/*      */       case 41:
/*      */       case 55:
/*      */       case 56:
/*      */       case 57:
/*      */       case 58:
/*      */       case 73:
/*  441 */         break;
/*      */       default:
/*  445 */         throw new NoViableAltException(LT(1), getFilename());
/*      */       }
/*      */ 
/*  449 */       rules();
/*  450 */       r_AST = (GrammarAST)this.returnAST;
/*  451 */       GrammarAST tmp3_AST = null;
/*  452 */       tmp3_AST = (GrammarAST)this.astFactory.create(LT(1));
/*  453 */       match(1);
/*  454 */       grammar_AST = (GrammarAST)currentAST.root;
/*      */ 
/*  456 */       grammar_AST = (GrammarAST)this.astFactory.make(new ASTArray(2).add(null).add((GrammarAST)this.astFactory.make(new ASTArray(9).add(gr_AST).add(gid_AST).add(cmt_AST).add(opt).add(ig_AST).add(ts_AST).add(scopes_AST).add(a_AST).add(r_AST))));
/*  457 */       cleanup(grammar_AST);
/*      */ 
/*  459 */       currentAST.root = grammar_AST;
/*  460 */       currentAST.child = ((grammar_AST != null) && (grammar_AST.getFirstChild() != null) ? grammar_AST.getFirstChild() : grammar_AST);
/*      */ 
/*  462 */       currentAST.advanceChildToEnd();
/*      */     }
/*      */     catch (RecognitionException ex) {
/*  465 */       reportError(ex);
/*  466 */       recover(ex, _tokenSet_0);
/*      */     }
/*  468 */     this.returnAST = grammar_AST;
/*      */   }
/*      */ 
/*      */   public final void grammarType() throws RecognitionException, TokenStreamException
/*      */   {
/*  473 */     this.returnAST = null;
/*  474 */     ASTPair currentAST = new ASTPair();
/*  475 */     GrammarAST grammarType_AST = null;
/*  476 */     Token gr = null;
/*  477 */     GrammarAST gr_AST = null;
/*      */     try
/*      */     {
/*  481 */       switch (LA(1))
/*      */       {
/*      */       case 43:
/*  484 */         match(43);
/*  485 */         this.gtype = 25; this.grammar.type = 1;
/*  486 */         break;
/*      */       case 6:
/*  490 */         match(6);
/*  491 */         this.gtype = 26; this.grammar.type = 2;
/*  492 */         break;
/*      */       case 44:
/*  496 */         match(44);
/*  497 */         this.gtype = 27; this.grammar.type = 3;
/*  498 */         break;
/*      */       case 45:
/*  502 */         this.gtype = 28; this.grammar.type = 4;
/*  503 */         break;
/*      */       default:
/*  507 */         throw new NoViableAltException(LT(1), getFilename());
/*      */       }
/*      */ 
/*  511 */       gr = LT(1);
/*  512 */       gr_AST = (GrammarAST)this.astFactory.create(gr);
/*  513 */       this.astFactory.addASTChild(currentAST, gr_AST);
/*  514 */       match(45);
/*  515 */       gr_AST.setType(this.gtype);
/*  516 */       grammarType_AST = (GrammarAST)currentAST.root;
/*      */     }
/*      */     catch (RecognitionException ex) {
/*  519 */       reportError(ex);
/*  520 */       recover(ex, _tokenSet_1);
/*      */     }
/*  522 */     this.returnAST = grammarType_AST;
/*      */   }
/*      */ 
/*      */   public final void id() throws RecognitionException, TokenStreamException
/*      */   {
/*  527 */     this.returnAST = null;
/*  528 */     ASTPair currentAST = new ASTPair();
/*  529 */     GrammarAST id_AST = null;
/*      */     try
/*      */     {
/*  532 */       switch (LA(1))
/*      */       {
/*      */       case 55:
/*  535 */         GrammarAST tmp7_AST = null;
/*  536 */         tmp7_AST = (GrammarAST)this.astFactory.create(LT(1));
/*  537 */         this.astFactory.addASTChild(currentAST, tmp7_AST);
/*  538 */         match(55);
/*  539 */         id_AST = (GrammarAST)currentAST.root;
/*  540 */         id_AST.setType(21);
/*  541 */         id_AST = (GrammarAST)currentAST.root;
/*  542 */         break;
/*      */       case 73:
/*  546 */         GrammarAST tmp8_AST = null;
/*  547 */         tmp8_AST = (GrammarAST)this.astFactory.create(LT(1));
/*  548 */         this.astFactory.addASTChild(currentAST, tmp8_AST);
/*  549 */         match(73);
/*  550 */         id_AST = (GrammarAST)currentAST.root;
/*  551 */         id_AST.setType(21);
/*  552 */         id_AST = (GrammarAST)currentAST.root;
/*  553 */         break;
/*      */       default:
/*  557 */         throw new NoViableAltException(LT(1), getFilename());
/*      */       }
/*      */     }
/*      */     catch (RecognitionException ex)
/*      */     {
/*  562 */       reportError(ex);
/*  563 */       recover(ex, _tokenSet_2);
/*      */     }
/*  565 */     this.returnAST = id_AST;
/*      */   }
/*      */ 
/*      */   public final Map optionsSpec() throws RecognitionException, TokenStreamException {
/*  569 */     Map opts = new HashMap();
/*      */ 
/*  571 */     this.returnAST = null;
/*  572 */     ASTPair currentAST = new ASTPair();
/*  573 */     GrammarAST optionsSpec_AST = null;
/*      */     try
/*      */     {
/*  576 */       GrammarAST tmp9_AST = null;
/*  577 */       tmp9_AST = (GrammarAST)this.astFactory.create(LT(1));
/*  578 */       this.astFactory.makeASTRoot(currentAST, tmp9_AST);
/*  579 */       match(4);
/*      */ 
/*  581 */       int _cnt18 = 0;
/*      */       while (true)
/*      */       {
/*  584 */         if ((LA(1) == 55) || (LA(1) == 73)) {
/*  585 */           option(opts);
/*  586 */           this.astFactory.addASTChild(currentAST, this.returnAST);
/*  587 */           match(42);
/*      */         }
/*      */         else {
/*  590 */           if (_cnt18 >= 1) break; throw new NoViableAltException(LT(1), getFilename());
/*      */         }
/*      */ 
/*  593 */         _cnt18++;
/*      */       }
/*      */ 
/*  596 */       match(48);
/*  597 */       optionsSpec_AST = (GrammarAST)currentAST.root;
/*      */     }
/*      */     catch (RecognitionException ex) {
/*  600 */       reportError(ex);
/*  601 */       recover(ex, _tokenSet_3);
/*      */     }
/*  603 */     this.returnAST = optionsSpec_AST;
/*  604 */     return opts;
/*      */   }
/*      */ 
/*      */   public final void delegateGrammars() throws RecognitionException, TokenStreamException
/*      */   {
/*  609 */     this.returnAST = null;
/*  610 */     ASTPair currentAST = new ASTPair();
/*  611 */     GrammarAST delegateGrammars_AST = null;
/*      */     try
/*      */     {
/*  614 */       GrammarAST tmp12_AST = null;
/*  615 */       tmp12_AST = (GrammarAST)this.astFactory.create(LT(1));
/*  616 */       this.astFactory.makeASTRoot(currentAST, tmp12_AST);
/*  617 */       match(34);
/*  618 */       delegateGrammar();
/*  619 */       this.astFactory.addASTChild(currentAST, this.returnAST);
/*      */ 
/*  623 */       while (LA(1) == 54) {
/*  624 */         match(54);
/*  625 */         delegateGrammar();
/*  626 */         this.astFactory.addASTChild(currentAST, this.returnAST);
/*      */       }
/*      */ 
/*  634 */       match(42);
/*  635 */       delegateGrammars_AST = (GrammarAST)currentAST.root;
/*      */     }
/*      */     catch (RecognitionException ex) {
/*  638 */       reportError(ex);
/*  639 */       recover(ex, _tokenSet_4);
/*      */     }
/*  641 */     this.returnAST = delegateGrammars_AST;
/*      */   }
/*      */ 
/*      */   public final void tokensSpec() throws RecognitionException, TokenStreamException
/*      */   {
/*  646 */     this.returnAST = null;
/*  647 */     ASTPair currentAST = new ASTPair();
/*  648 */     GrammarAST tokensSpec_AST = null;
/*      */     try
/*      */     {
/*  651 */       GrammarAST tmp15_AST = null;
/*  652 */       tmp15_AST = (GrammarAST)this.astFactory.create(LT(1));
/*  653 */       this.astFactory.makeASTRoot(currentAST, tmp15_AST);
/*  654 */       match(5);
/*      */ 
/*  656 */       int _cnt27 = 0;
/*      */       while (true)
/*      */       {
/*  659 */         if (LA(1) == 55) {
/*  660 */           tokenSpec();
/*  661 */           this.astFactory.addASTChild(currentAST, this.returnAST);
/*      */         }
/*      */         else {
/*  664 */           if (_cnt27 >= 1) break; throw new NoViableAltException(LT(1), getFilename());
/*      */         }
/*      */ 
/*  667 */         _cnt27++;
/*      */       }
/*      */ 
/*  670 */       match(48);
/*  671 */       tokensSpec_AST = (GrammarAST)currentAST.root;
/*      */     }
/*      */     catch (RecognitionException ex) {
/*  674 */       reportError(ex);
/*  675 */       recover(ex, _tokenSet_5);
/*      */     }
/*  677 */     this.returnAST = tokensSpec_AST;
/*      */   }
/*      */ 
/*      */   public final void attrScopes() throws RecognitionException, TokenStreamException
/*      */   {
/*  682 */     this.returnAST = null;
/*  683 */     ASTPair currentAST = new ASTPair();
/*  684 */     GrammarAST attrScopes_AST = null;
/*      */     try
/*      */     {
/*  690 */       while (LA(1) == 33) {
/*  691 */         attrScope();
/*  692 */         this.astFactory.addASTChild(currentAST, this.returnAST);
/*      */       }
/*      */ 
/*  700 */       attrScopes_AST = (GrammarAST)currentAST.root;
/*      */     }
/*      */     catch (RecognitionException ex) {
/*  703 */       reportError(ex);
/*  704 */       recover(ex, _tokenSet_6);
/*      */     }
/*  706 */     this.returnAST = attrScopes_AST;
/*      */   }
/*      */ 
/*      */   public final void actions() throws RecognitionException, TokenStreamException
/*      */   {
/*  711 */     this.returnAST = null;
/*  712 */     ASTPair currentAST = new ASTPair();
/*  713 */     GrammarAST actions_AST = null;
/*      */     try
/*      */     {
/*  717 */       int _cnt12 = 0;
/*      */       while (true)
/*      */       {
/*  720 */         if (LA(1) == 46) {
/*  721 */           action();
/*  722 */           this.astFactory.addASTChild(currentAST, this.returnAST);
/*      */         }
/*      */         else {
/*  725 */           if (_cnt12 >= 1) break; throw new NoViableAltException(LT(1), getFilename());
/*      */         }
/*      */ 
/*  728 */         _cnt12++;
/*      */       }
/*      */ 
/*  731 */       actions_AST = (GrammarAST)currentAST.root;
/*      */     }
/*      */     catch (RecognitionException ex) {
/*  734 */       reportError(ex);
/*  735 */       recover(ex, _tokenSet_7);
/*      */     }
/*  737 */     this.returnAST = actions_AST;
/*      */   }
/*      */ 
/*      */   public final void rules() throws RecognitionException, TokenStreamException
/*      */   {
/*  742 */     this.returnAST = null;
/*  743 */     ASTPair currentAST = new ASTPair();
/*  744 */     GrammarAST rules_AST = null;
/*      */     try
/*      */     {
/*  748 */       int _cnt37 = 0;
/*      */       while (true)
/*      */       {
/*  751 */         if (_tokenSet_7.member(LA(1))) {
/*  752 */           rule();
/*  753 */           this.astFactory.addASTChild(currentAST, this.returnAST);
/*      */         }
/*      */         else {
/*  756 */           if (_cnt37 >= 1) break; throw new NoViableAltException(LT(1), getFilename());
/*      */         }
/*      */ 
/*  759 */         _cnt37++;
/*      */       }
/*      */ 
/*  762 */       rules_AST = (GrammarAST)currentAST.root;
/*      */     }
/*      */     catch (RecognitionException ex) {
/*  765 */       reportError(ex);
/*  766 */       recover(ex, _tokenSet_0);
/*      */     }
/*  768 */     this.returnAST = rules_AST;
/*      */   }
/*      */ 
/*      */   public final void action()
/*      */     throws RecognitionException, TokenStreamException
/*      */   {
/*  774 */     this.returnAST = null;
/*  775 */     ASTPair currentAST = new ASTPair();
/*  776 */     GrammarAST action_AST = null;
/*      */     try
/*      */     {
/*  779 */       GrammarAST tmp17_AST = null;
/*  780 */       tmp17_AST = (GrammarAST)this.astFactory.create(LT(1));
/*  781 */       this.astFactory.makeASTRoot(currentAST, tmp17_AST);
/*  782 */       match(46);
/*      */ 
/*  784 */       if ((_tokenSet_8.member(LA(1))) && (LA(2) == 47)) {
/*  785 */         actionScopeName();
/*  786 */         this.astFactory.addASTChild(currentAST, this.returnAST);
/*  787 */         match(47);
/*  788 */         match(47);
/*      */       }
/*  790 */       else if (((LA(1) != 55) && (LA(1) != 73)) || (LA(2) != 40))
/*      */       {
/*  793 */         throw new NoViableAltException(LT(1), getFilename());
/*      */       }
/*      */ 
/*  797 */       id();
/*  798 */       this.astFactory.addASTChild(currentAST, this.returnAST);
/*  799 */       GrammarAST tmp20_AST = null;
/*  800 */       tmp20_AST = (GrammarAST)this.astFactory.create(LT(1));
/*  801 */       this.astFactory.addASTChild(currentAST, tmp20_AST);
/*  802 */       match(40);
/*  803 */       action_AST = (GrammarAST)currentAST.root;
/*      */     }
/*      */     catch (RecognitionException ex) {
/*  806 */       reportError(ex);
/*  807 */       recover(ex, _tokenSet_6);
/*      */     }
/*  809 */     this.returnAST = action_AST;
/*      */   }
/*      */ 
/*      */   public final void actionScopeName()
/*      */     throws RecognitionException, TokenStreamException
/*      */   {
/*  817 */     this.returnAST = null;
/*  818 */     ASTPair currentAST = new ASTPair();
/*  819 */     GrammarAST actionScopeName_AST = null;
/*  820 */     Token l = null;
/*  821 */     GrammarAST l_AST = null;
/*  822 */     Token p = null;
/*  823 */     GrammarAST p_AST = null;
/*      */     try
/*      */     {
/*  826 */       switch (LA(1))
/*      */       {
/*      */       case 55:
/*      */       case 73:
/*  830 */         id();
/*  831 */         this.astFactory.addASTChild(currentAST, this.returnAST);
/*  832 */         actionScopeName_AST = (GrammarAST)currentAST.root;
/*  833 */         break;
/*      */       case 43:
/*  837 */         l = LT(1);
/*  838 */         l_AST = (GrammarAST)this.astFactory.create(l);
/*  839 */         this.astFactory.addASTChild(currentAST, l_AST);
/*  840 */         match(43);
/*  841 */         l_AST.setType(21);
/*  842 */         actionScopeName_AST = (GrammarAST)currentAST.root;
/*  843 */         break;
/*      */       case 6:
/*  847 */         p = LT(1);
/*  848 */         p_AST = (GrammarAST)this.astFactory.create(p);
/*  849 */         this.astFactory.addASTChild(currentAST, p_AST);
/*  850 */         match(6);
/*  851 */         p_AST.setType(21);
/*  852 */         actionScopeName_AST = (GrammarAST)currentAST.root;
/*  853 */         break;
/*      */       default:
/*  857 */         throw new NoViableAltException(LT(1), getFilename());
/*      */       }
/*      */     }
/*      */     catch (RecognitionException ex)
/*      */     {
/*  862 */       reportError(ex);
/*  863 */       recover(ex, _tokenSet_9);
/*      */     }
/*  865 */     this.returnAST = actionScopeName_AST;
/*      */   }
/*      */ 
/*      */   public final void option(Map opts)
/*      */     throws RecognitionException, TokenStreamException
/*      */   {
/*  872 */     this.returnAST = null;
/*  873 */     ASTPair currentAST = new ASTPair();
/*  874 */     GrammarAST option_AST = null;
/*  875 */     GrammarAST o_AST = null;
/*      */ 
/*  877 */     Object value = null;
/*      */     try
/*      */     {
/*  881 */       id();
/*  882 */       o_AST = (GrammarAST)this.returnAST;
/*  883 */       this.astFactory.addASTChild(currentAST, this.returnAST);
/*  884 */       GrammarAST tmp21_AST = null;
/*  885 */       tmp21_AST = (GrammarAST)this.astFactory.create(LT(1));
/*  886 */       this.astFactory.makeASTRoot(currentAST, tmp21_AST);
/*  887 */       match(49);
/*  888 */       value = optionValue();
/*  889 */       this.astFactory.addASTChild(currentAST, this.returnAST);
/*      */ 
/*  891 */       opts.put(o_AST.getText(), value);
/*      */ 
/*  893 */       option_AST = (GrammarAST)currentAST.root;
/*      */     }
/*      */     catch (RecognitionException ex) {
/*  896 */       reportError(ex);
/*  897 */       recover(ex, _tokenSet_10);
/*      */     }
/*  899 */     this.returnAST = option_AST;
/*      */   }
/*      */ 
/*      */   public final Object optionValue() throws RecognitionException, TokenStreamException {
/*  903 */     Object value = null;
/*      */ 
/*  905 */     this.returnAST = null;
/*  906 */     ASTPair currentAST = new ASTPair();
/*  907 */     GrammarAST optionValue_AST = null;
/*  908 */     GrammarAST x_AST = null;
/*  909 */     Token s = null;
/*  910 */     GrammarAST s_AST = null;
/*  911 */     Token c = null;
/*  912 */     GrammarAST c_AST = null;
/*  913 */     Token i = null;
/*  914 */     GrammarAST i_AST = null;
/*  915 */     Token ss = null;
/*  916 */     GrammarAST ss_AST = null;
/*      */     try
/*      */     {
/*  919 */       switch (LA(1))
/*      */       {
/*      */       case 55:
/*      */       case 73:
/*  923 */         id();
/*  924 */         x_AST = (GrammarAST)this.returnAST;
/*  925 */         this.astFactory.addASTChild(currentAST, this.returnAST);
/*  926 */         value = x_AST.getText();
/*  927 */         optionValue_AST = (GrammarAST)currentAST.root;
/*  928 */         break;
/*      */       case 50:
/*  932 */         s = LT(1);
/*  933 */         s_AST = (GrammarAST)this.astFactory.create(s);
/*  934 */         this.astFactory.addASTChild(currentAST, s_AST);
/*  935 */         match(50);
/*  936 */         String vs = s_AST.getText();
/*  937 */         value = vs.substring(1, vs.length() - 1);
/*  938 */         optionValue_AST = (GrammarAST)currentAST.root;
/*  939 */         break;
/*      */       case 51:
/*  943 */         c = LT(1);
/*  944 */         c_AST = (GrammarAST)this.astFactory.create(c);
/*  945 */         this.astFactory.addASTChild(currentAST, c_AST);
/*  946 */         match(51);
/*  947 */         String vs = c_AST.getText();
/*  948 */         value = vs.substring(1, vs.length() - 1);
/*  949 */         optionValue_AST = (GrammarAST)currentAST.root;
/*  950 */         break;
/*      */       case 52:
/*  954 */         i = LT(1);
/*  955 */         i_AST = (GrammarAST)this.astFactory.create(i);
/*  956 */         this.astFactory.addASTChild(currentAST, i_AST);
/*  957 */         match(52);
/*  958 */         value = new Integer(i_AST.getText());
/*  959 */         optionValue_AST = (GrammarAST)currentAST.root;
/*  960 */         break;
/*      */       case 53:
/*  964 */         ss = LT(1);
/*  965 */         ss_AST = (GrammarAST)this.astFactory.create(ss);
/*  966 */         this.astFactory.addASTChild(currentAST, ss_AST);
/*  967 */         match(53);
/*  968 */         ss_AST.setType(50); value = "*";
/*  969 */         optionValue_AST = (GrammarAST)currentAST.root;
/*  970 */         break;
/*      */       default:
/*  974 */         throw new NoViableAltException(LT(1), getFilename());
/*      */       }
/*      */     }
/*      */     catch (RecognitionException ex)
/*      */     {
/*  979 */       reportError(ex);
/*  980 */       recover(ex, _tokenSet_10);
/*      */     }
/*  982 */     this.returnAST = optionValue_AST;
/*  983 */     return value;
/*      */   }
/*      */ 
/*      */   public final void delegateGrammar() throws RecognitionException, TokenStreamException
/*      */   {
/*  988 */     this.returnAST = null;
/*  989 */     ASTPair currentAST = new ASTPair();
/*  990 */     GrammarAST delegateGrammar_AST = null;
/*  991 */     GrammarAST lab_AST = null;
/*  992 */     GrammarAST g_AST = null;
/*  993 */     GrammarAST g2_AST = null;
/*      */     try
/*      */     {
/*  996 */       if (((LA(1) == 55) || (LA(1) == 73)) && (LA(2) == 49)) {
/*  997 */         id();
/*  998 */         lab_AST = (GrammarAST)this.returnAST;
/*  999 */         this.astFactory.addASTChild(currentAST, this.returnAST);
/* 1000 */         GrammarAST tmp22_AST = null;
/* 1001 */         tmp22_AST = (GrammarAST)this.astFactory.create(LT(1));
/* 1002 */         this.astFactory.makeASTRoot(currentAST, tmp22_AST);
/* 1003 */         match(49);
/* 1004 */         id();
/* 1005 */         g_AST = (GrammarAST)this.returnAST;
/* 1006 */         this.astFactory.addASTChild(currentAST, this.returnAST);
/* 1007 */         this.grammar.importGrammar(g_AST, lab_AST.getText());
/* 1008 */         delegateGrammar_AST = (GrammarAST)currentAST.root;
/*      */       }
/* 1010 */       else if (((LA(1) == 55) || (LA(1) == 73)) && ((LA(2) == 42) || (LA(2) == 54))) {
/* 1011 */         id();
/* 1012 */         g2_AST = (GrammarAST)this.returnAST;
/* 1013 */         this.astFactory.addASTChild(currentAST, this.returnAST);
/* 1014 */         this.grammar.importGrammar(g2_AST, null);
/* 1015 */         delegateGrammar_AST = (GrammarAST)currentAST.root;
/*      */       }
/*      */       else {
/* 1018 */         throw new NoViableAltException(LT(1), getFilename());
/*      */       }
/*      */     }
/*      */     catch (RecognitionException ex)
/*      */     {
/* 1023 */       reportError(ex);
/* 1024 */       recover(ex, _tokenSet_11);
/*      */     }
/* 1026 */     this.returnAST = delegateGrammar_AST;
/*      */   }
/*      */ 
/*      */   public final void tokenSpec() throws RecognitionException, TokenStreamException
/*      */   {
/* 1031 */     this.returnAST = null;
/* 1032 */     ASTPair currentAST = new ASTPair();
/* 1033 */     GrammarAST tokenSpec_AST = null;
/*      */     try
/*      */     {
/* 1036 */       GrammarAST tmp23_AST = null;
/* 1037 */       tmp23_AST = (GrammarAST)this.astFactory.create(LT(1));
/* 1038 */       this.astFactory.addASTChild(currentAST, tmp23_AST);
/* 1039 */       match(55);
/*      */ 
/* 1041 */       switch (LA(1))
/*      */       {
/*      */       case 49:
/* 1044 */         GrammarAST tmp24_AST = null;
/* 1045 */         tmp24_AST = (GrammarAST)this.astFactory.create(LT(1));
/* 1046 */         this.astFactory.makeASTRoot(currentAST, tmp24_AST);
/* 1047 */         match(49);
/*      */ 
/* 1049 */         switch (LA(1))
/*      */         {
/*      */         case 50:
/* 1052 */           GrammarAST tmp25_AST = null;
/* 1053 */           tmp25_AST = (GrammarAST)this.astFactory.create(LT(1));
/* 1054 */           this.astFactory.addASTChild(currentAST, tmp25_AST);
/* 1055 */           match(50);
/* 1056 */           break;
/*      */         case 51:
/* 1060 */           GrammarAST tmp26_AST = null;
/* 1061 */           tmp26_AST = (GrammarAST)this.astFactory.create(LT(1));
/* 1062 */           this.astFactory.addASTChild(currentAST, tmp26_AST);
/* 1063 */           match(51);
/* 1064 */           break;
/*      */         default:
/* 1068 */           throw new NoViableAltException(LT(1), getFilename());
/*      */         }
/*      */ 
/*      */         break;
/*      */       case 42:
/* 1076 */         break;
/*      */       default:
/* 1080 */         throw new NoViableAltException(LT(1), getFilename());
/*      */       }
/*      */ 
/* 1084 */       match(42);
/* 1085 */       tokenSpec_AST = (GrammarAST)currentAST.root;
/*      */     }
/*      */     catch (RecognitionException ex) {
/* 1088 */       reportError(ex);
/* 1089 */       recover(ex, _tokenSet_12);
/*      */     }
/* 1091 */     this.returnAST = tokenSpec_AST;
/*      */   }
/*      */ 
/*      */   public final void attrScope() throws RecognitionException, TokenStreamException
/*      */   {
/* 1096 */     this.returnAST = null;
/* 1097 */     ASTPair currentAST = new ASTPair();
/* 1098 */     GrammarAST attrScope_AST = null;
/*      */     try
/*      */     {
/* 1101 */       GrammarAST tmp28_AST = null;
/* 1102 */       tmp28_AST = (GrammarAST)this.astFactory.create(LT(1));
/* 1103 */       this.astFactory.makeASTRoot(currentAST, tmp28_AST);
/* 1104 */       match(33);
/* 1105 */       id();
/* 1106 */       this.astFactory.addASTChild(currentAST, this.returnAST);
/* 1107 */       GrammarAST tmp29_AST = null;
/* 1108 */       tmp29_AST = (GrammarAST)this.astFactory.create(LT(1));
/* 1109 */       this.astFactory.addASTChild(currentAST, tmp29_AST);
/* 1110 */       match(40);
/* 1111 */       attrScope_AST = (GrammarAST)currentAST.root;
/*      */     }
/*      */     catch (RecognitionException ex) {
/* 1114 */       reportError(ex);
/* 1115 */       recover(ex, _tokenSet_5);
/*      */     }
/* 1117 */     this.returnAST = attrScope_AST;
/*      */   }
/*      */ 
/*      */   public final void rule() throws RecognitionException, TokenStreamException
/*      */   {
/* 1122 */     this.returnAST = null;
/* 1123 */     ASTPair currentAST = new ASTPair();
/* 1124 */     GrammarAST rule_AST = null;
/* 1125 */     Token d = null;
/* 1126 */     GrammarAST d_AST = null;
/* 1127 */     Token p1 = null;
/* 1128 */     GrammarAST p1_AST = null;
/* 1129 */     Token p2 = null;
/* 1130 */     GrammarAST p2_AST = null;
/* 1131 */     Token p3 = null;
/* 1132 */     GrammarAST p3_AST = null;
/* 1133 */     Token p4 = null;
/* 1134 */     GrammarAST p4_AST = null;
/* 1135 */     GrammarAST ruleName_AST = null;
/* 1136 */     Token aa = null;
/* 1137 */     GrammarAST aa_AST = null;
/* 1138 */     Token rt = null;
/* 1139 */     GrammarAST rt_AST = null;
/* 1140 */     GrammarAST scopes_AST = null;
/* 1141 */     GrammarAST a_AST = null;
/* 1142 */     Token colon = null;
/* 1143 */     GrammarAST colon_AST = null;
/* 1144 */     GrammarAST b_AST = null;
/* 1145 */     Token semi = null;
/* 1146 */     GrammarAST semi_AST = null;
/* 1147 */     GrammarAST ex_AST = null;
/*      */ 
/* 1149 */     GrammarAST modifier = null; GrammarAST blk = null; GrammarAST blkRoot = null; GrammarAST eob = null;
/* 1150 */     int start = ((TokenWithIndex)LT(1)).getIndex();
/* 1151 */     int startLine = LT(1).getLine();
/* 1152 */     GrammarAST opt = null;
/* 1153 */     Map opts = null;
/*      */     try
/*      */     {
/* 1158 */       switch (LA(1))
/*      */       {
/*      */       case 41:
/* 1161 */         d = LT(1);
/* 1162 */         d_AST = (GrammarAST)this.astFactory.create(d);
/* 1163 */         match(41);
/* 1164 */         break;
/*      */       case 38:
/*      */       case 55:
/*      */       case 56:
/*      */       case 57:
/*      */       case 58:
/*      */       case 73:
/* 1173 */         break;
/*      */       default:
/* 1177 */         throw new NoViableAltException(LT(1), getFilename());
/*      */       }
/*      */ 
/* 1182 */       switch (LA(1))
/*      */       {
/*      */       case 56:
/* 1185 */         p1 = LT(1);
/* 1186 */         p1_AST = (GrammarAST)this.astFactory.create(p1);
/* 1187 */         match(56);
/* 1188 */         modifier = p1_AST;
/* 1189 */         break;
/*      */       case 57:
/* 1193 */         p2 = LT(1);
/* 1194 */         p2_AST = (GrammarAST)this.astFactory.create(p2);
/* 1195 */         match(57);
/* 1196 */         modifier = p2_AST;
/* 1197 */         break;
/*      */       case 58:
/* 1201 */         p3 = LT(1);
/* 1202 */         p3_AST = (GrammarAST)this.astFactory.create(p3);
/* 1203 */         match(58);
/* 1204 */         modifier = p3_AST;
/* 1205 */         break;
/*      */       case 38:
/* 1209 */         p4 = LT(1);
/* 1210 */         p4_AST = (GrammarAST)this.astFactory.create(p4);
/* 1211 */         match(38);
/* 1212 */         modifier = p4_AST;
/* 1213 */         break;
/*      */       case 55:
/*      */       case 73:
/* 1218 */         break;
/*      */       default:
/* 1222 */         throw new NoViableAltException(LT(1), getFilename());
/*      */       }
/*      */ 
/* 1226 */       id();
/* 1227 */       ruleName_AST = (GrammarAST)this.returnAST;
/* 1228 */       this.currentRuleName = ruleName_AST.getText();
/* 1229 */       if ((this.gtype == 25) && (p4_AST == null)) {
/* 1230 */         this.grammar.lexerRuleNamesInCombined.add(this.currentRuleName);
/*      */       }
/*      */ 
/* 1234 */       switch (LA(1))
/*      */       {
/*      */       case 59:
/* 1237 */         GrammarAST tmp30_AST = null;
/* 1238 */         tmp30_AST = (GrammarAST)this.astFactory.create(LT(1));
/* 1239 */         match(59);
/* 1240 */         break;
/*      */       case 4:
/*      */       case 33:
/*      */       case 46:
/*      */       case 47:
/*      */       case 60:
/*      */       case 61:
/*      */       case 62:
/* 1250 */         break;
/*      */       default:
/* 1254 */         throw new NoViableAltException(LT(1), getFilename());
/*      */       }
/*      */ 
/* 1259 */       switch (LA(1))
/*      */       {
/*      */       case 60:
/* 1262 */         aa = LT(1);
/* 1263 */         aa_AST = (GrammarAST)this.astFactory.create(aa);
/* 1264 */         match(60);
/* 1265 */         break;
/*      */       case 4:
/*      */       case 33:
/*      */       case 46:
/*      */       case 47:
/*      */       case 61:
/*      */       case 62:
/* 1274 */         break;
/*      */       default:
/* 1278 */         throw new NoViableAltException(LT(1), getFilename());
/*      */       }
/*      */ 
/* 1283 */       switch (LA(1))
/*      */       {
/*      */       case 61:
/* 1286 */         match(61);
/* 1287 */         rt = LT(1);
/* 1288 */         rt_AST = (GrammarAST)this.astFactory.create(rt);
/* 1289 */         match(60);
/* 1290 */         break;
/*      */       case 4:
/*      */       case 33:
/*      */       case 46:
/*      */       case 47:
/*      */       case 62:
/* 1298 */         break;
/*      */       default:
/* 1302 */         throw new NoViableAltException(LT(1), getFilename());
/*      */       }
/*      */ 
/* 1307 */       switch (LA(1))
/*      */       {
/*      */       case 62:
/* 1310 */         throwsSpec();
/* 1311 */         break;
/*      */       case 4:
/*      */       case 33:
/*      */       case 46:
/*      */       case 47:
/* 1318 */         break;
/*      */       default:
/* 1322 */         throw new NoViableAltException(LT(1), getFilename());
/*      */       }
/*      */ 
/* 1327 */       switch (LA(1))
/*      */       {
/*      */       case 4:
/* 1330 */         opts = optionsSpec();
/* 1331 */         opt = (GrammarAST)this.returnAST;
/* 1332 */         break;
/*      */       case 33:
/*      */       case 46:
/*      */       case 47:
/* 1338 */         break;
/*      */       default:
/* 1342 */         throw new NoViableAltException(LT(1), getFilename());
/*      */       }
/*      */ 
/* 1346 */       ruleScopeSpec();
/* 1347 */       scopes_AST = (GrammarAST)this.returnAST;
/*      */ 
/* 1349 */       switch (LA(1))
/*      */       {
/*      */       case 46:
/* 1352 */         ruleActions();
/* 1353 */         a_AST = (GrammarAST)this.returnAST;
/* 1354 */         break;
/*      */       case 47:
/* 1358 */         break;
/*      */       default:
/* 1362 */         throw new NoViableAltException(LT(1), getFilename());
/*      */       }
/*      */ 
/* 1366 */       colon = LT(1);
/* 1367 */       colon_AST = (GrammarAST)this.astFactory.create(colon);
/* 1368 */       match(47);
/*      */ 
/* 1370 */       blkRoot = (GrammarAST)this.astFactory.create(9, "BLOCK");
/* 1371 */       blkRoot.setBlockOptions(opts);
/* 1372 */       blkRoot.setLine(colon.getLine());
/* 1373 */       blkRoot.setColumn(colon.getColumn());
/* 1374 */       eob = (GrammarAST)this.astFactory.create(19, "<end-of-block>");
/*      */ 
/* 1376 */       altList(opts);
/* 1377 */       b_AST = (GrammarAST)this.returnAST;
/* 1378 */       blk = b_AST;
/* 1379 */       semi = LT(1);
/* 1380 */       semi_AST = (GrammarAST)this.astFactory.create(semi);
/* 1381 */       match(42);
/*      */ 
/* 1383 */       switch (LA(1))
/*      */       {
/*      */       case 66:
/*      */       case 67:
/* 1387 */         exceptionGroup();
/* 1388 */         ex_AST = (GrammarAST)this.returnAST;
/* 1389 */         break;
/*      */       case 1:
/*      */       case 38:
/*      */       case 41:
/*      */       case 55:
/*      */       case 56:
/*      */       case 57:
/*      */       case 58:
/*      */       case 73:
/* 1400 */         break;
/*      */       default:
/* 1404 */         throw new NoViableAltException(LT(1), getFilename());
/*      */       }
/*      */ 
/* 1408 */       rule_AST = (GrammarAST)currentAST.root;
/*      */ 
/* 1410 */       int stop = ((TokenWithIndex)LT(1)).getIndex() - 1;
/* 1411 */       eob.setLine(semi.getLine());
/* 1412 */       eob.setColumn(semi.getColumn());
/* 1413 */       GrammarAST eor = (GrammarAST)this.astFactory.create(18, "<end-of-rule>");
/* 1414 */       eor.setLine(semi.getLine());
/* 1415 */       eor.setColumn(semi.getColumn());
/* 1416 */       GrammarAST root = (GrammarAST)this.astFactory.create(8, "rule");
/* 1417 */       root.ruleStartTokenIndex = start;
/* 1418 */       root.ruleStopTokenIndex = stop;
/* 1419 */       root.setLine(startLine);
/* 1420 */       root.setBlockOptions(opts);
/* 1421 */       rule_AST = (GrammarAST)this.astFactory.make(new ASTArray(11).add(root).add(ruleName_AST).add(modifier).add((GrammarAST)this.astFactory.make(new ASTArray(2).add((GrammarAST)this.astFactory.create(22, "ARG")).add(aa_AST))).add((GrammarAST)this.astFactory.make(new ASTArray(2).add((GrammarAST)this.astFactory.create(24, "RET")).add(rt_AST))).add(opt).add(scopes_AST).add(a_AST).add(blk).add(ex_AST).add(eor));
/* 1422 */       this.currentRuleName = null;
/*      */ 
/* 1424 */       currentAST.root = rule_AST;
/* 1425 */       currentAST.child = ((rule_AST != null) && (rule_AST.getFirstChild() != null) ? rule_AST.getFirstChild() : rule_AST);
/*      */ 
/* 1427 */       currentAST.advanceChildToEnd();
/*      */     }
/*      */     catch (RecognitionException ex) {
/* 1430 */       reportError(ex);
/* 1431 */       recover(ex, _tokenSet_13);
/*      */     }
/* 1433 */     this.returnAST = rule_AST;
/*      */   }
/*      */ 
/*      */   public final void throwsSpec() throws RecognitionException, TokenStreamException
/*      */   {
/* 1438 */     this.returnAST = null;
/* 1439 */     ASTPair currentAST = new ASTPair();
/* 1440 */     GrammarAST throwsSpec_AST = null;
/*      */     try
/*      */     {
/* 1443 */       GrammarAST tmp32_AST = null;
/* 1444 */       tmp32_AST = (GrammarAST)this.astFactory.create(LT(1));
/* 1445 */       this.astFactory.addASTChild(currentAST, tmp32_AST);
/* 1446 */       match(62);
/* 1447 */       id();
/* 1448 */       this.astFactory.addASTChild(currentAST, this.returnAST);
/*      */ 
/* 1452 */       while (LA(1) == 54) {
/* 1453 */         GrammarAST tmp33_AST = null;
/* 1454 */         tmp33_AST = (GrammarAST)this.astFactory.create(LT(1));
/* 1455 */         this.astFactory.addASTChild(currentAST, tmp33_AST);
/* 1456 */         match(54);
/* 1457 */         id();
/* 1458 */         this.astFactory.addASTChild(currentAST, this.returnAST);
/*      */       }
/*      */ 
/* 1466 */       throwsSpec_AST = (GrammarAST)currentAST.root;
/*      */     }
/*      */     catch (RecognitionException ex) {
/* 1469 */       reportError(ex);
/* 1470 */       recover(ex, _tokenSet_14);
/*      */     }
/* 1472 */     this.returnAST = throwsSpec_AST;
/*      */   }
/*      */ 
/*      */   public final void ruleScopeSpec() throws RecognitionException, TokenStreamException
/*      */   {
/* 1477 */     this.returnAST = null;
/* 1478 */     ASTPair currentAST = new ASTPair();
/* 1479 */     GrammarAST ruleScopeSpec_AST = null;
/* 1480 */     Token a = null;
/* 1481 */     GrammarAST a_AST = null;
/* 1482 */     GrammarAST ids_AST = null;
/*      */ 
/* 1484 */     int line = LT(1).getLine();
/* 1485 */     int column = LT(1).getColumn();
/*      */     try
/*      */     {
/* 1490 */       if ((LA(1) == 33) && (LA(2) == 40) && ((LA(3) == 33) || (LA(3) == 46) || (LA(3) == 47))) {
/* 1491 */         match(33);
/* 1492 */         a = LT(1);
/* 1493 */         a_AST = (GrammarAST)this.astFactory.create(a);
/* 1494 */         match(40);
/*      */       }
/* 1496 */       else if (((LA(1) != 33) && (LA(1) != 46) && (LA(1) != 47)) || (!_tokenSet_15.member(LA(2))) || (!_tokenSet_16.member(LA(3))))
/*      */       {
/* 1499 */         throw new NoViableAltException(LT(1), getFilename());
/*      */       }
/*      */ 
/* 1506 */       while (LA(1) == 33) {
/* 1507 */         match(33);
/* 1508 */         idList();
/* 1509 */         ids_AST = (GrammarAST)this.returnAST;
/* 1510 */         match(42);
/*      */       }
/*      */ 
/* 1518 */       ruleScopeSpec_AST = (GrammarAST)currentAST.root;
/*      */ 
/* 1520 */       GrammarAST scopeRoot = (GrammarAST)this.astFactory.create(33, "scope");
/* 1521 */       scopeRoot.setLine(line);
/* 1522 */       scopeRoot.setColumn(column);
/* 1523 */       ruleScopeSpec_AST = (GrammarAST)this.astFactory.make(new ASTArray(3).add(scopeRoot).add(a_AST).add(ids_AST));
/*      */ 
/* 1525 */       currentAST.root = ruleScopeSpec_AST;
/* 1526 */       currentAST.child = ((ruleScopeSpec_AST != null) && (ruleScopeSpec_AST.getFirstChild() != null) ? ruleScopeSpec_AST.getFirstChild() : ruleScopeSpec_AST);
/*      */ 
/* 1528 */       currentAST.advanceChildToEnd();
/*      */     }
/*      */     catch (RecognitionException ex) {
/* 1531 */       reportError(ex);
/* 1532 */       recover(ex, _tokenSet_17);
/*      */     }
/* 1534 */     this.returnAST = ruleScopeSpec_AST;
/*      */   }
/*      */ 
/*      */   public final void ruleActions() throws RecognitionException, TokenStreamException
/*      */   {
/* 1539 */     this.returnAST = null;
/* 1540 */     ASTPair currentAST = new ASTPair();
/* 1541 */     GrammarAST ruleActions_AST = null;
/*      */     try
/*      */     {
/* 1545 */       int _cnt50 = 0;
/*      */       while (true)
/*      */       {
/* 1548 */         if (LA(1) == 46) {
/* 1549 */           ruleAction();
/* 1550 */           this.astFactory.addASTChild(currentAST, this.returnAST);
/*      */         }
/*      */         else {
/* 1553 */           if (_cnt50 >= 1) break; throw new NoViableAltException(LT(1), getFilename());
/*      */         }
/*      */ 
/* 1556 */         _cnt50++;
/*      */       }
/*      */ 
/* 1559 */       ruleActions_AST = (GrammarAST)currentAST.root;
/*      */     }
/*      */     catch (RecognitionException ex) {
/* 1562 */       reportError(ex);
/* 1563 */       recover(ex, _tokenSet_9);
/*      */     }
/* 1565 */     this.returnAST = ruleActions_AST;
/*      */   }
/*      */ 
/*      */   public final void altList(Map opts)
/*      */     throws RecognitionException, TokenStreamException
/*      */   {
/* 1572 */     this.returnAST = null;
/* 1573 */     ASTPair currentAST = new ASTPair();
/* 1574 */     GrammarAST altList_AST = null;
/* 1575 */     GrammarAST a1_AST = null;
/* 1576 */     GrammarAST a2_AST = null;
/*      */ 
/* 1578 */     GrammarAST blkRoot = (GrammarAST)this.astFactory.create(9, "BLOCK");
/* 1579 */     blkRoot.setBlockOptions(opts);
/* 1580 */     blkRoot.setLine(LT(0).getLine());
/* 1581 */     blkRoot.setColumn(LT(0).getColumn());
/* 1582 */     GrammarAST save = this.currentBlockAST;
/* 1583 */     this.currentBlockAST = blkRoot;
/*      */     try
/*      */     {
/* 1587 */       alternative();
/* 1588 */       a1_AST = (GrammarAST)this.returnAST;
/* 1589 */       this.astFactory.addASTChild(currentAST, this.returnAST);
/* 1590 */       rewrite();
/* 1591 */       this.astFactory.addASTChild(currentAST, this.returnAST);
/* 1592 */       if ((LA(1) == 64) || (LA(2) == 76) || (LA(2) == 77) || (LA(2) == 53)) prefixWithSynPred(a1_AST);
/*      */ 
/* 1603 */       for (; LA(1) == 64; 
/* 1603 */         prefixWithSynPred(a2_AST))
/*      */       {
/* 1597 */         label168: match(64);
/* 1598 */         alternative();
/* 1599 */         a2_AST = (GrammarAST)this.returnAST;
/* 1600 */         this.astFactory.addASTChild(currentAST, this.returnAST);
/* 1601 */         rewrite();
/* 1602 */         this.astFactory.addASTChild(currentAST, this.returnAST);
/* 1603 */         if ((LA(1) != 64) && (LA(2) != 76) && (LA(2) != 77) && (LA(2) != 53))
/*      */         {
/*      */           break label168;
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/* 1611 */       altList_AST = (GrammarAST)currentAST.root;
/*      */ 
/* 1613 */       altList_AST = (GrammarAST)this.astFactory.make(new ASTArray(3).add(blkRoot).add(altList_AST).add((GrammarAST)this.astFactory.create(19, "<end-of-block>")));
/* 1614 */       this.currentBlockAST = save;
/*      */ 
/* 1616 */       currentAST.root = altList_AST;
/* 1617 */       currentAST.child = ((altList_AST != null) && (altList_AST.getFirstChild() != null) ? altList_AST.getFirstChild() : altList_AST);
/*      */ 
/* 1619 */       currentAST.advanceChildToEnd();
/* 1620 */       altList_AST = (GrammarAST)currentAST.root;
/*      */     }
/*      */     catch (RecognitionException ex) {
/* 1623 */       reportError(ex);
/* 1624 */       recover(ex, _tokenSet_10);
/*      */     }
/* 1626 */     this.returnAST = altList_AST;
/*      */   }
/*      */ 
/*      */   public final void exceptionGroup() throws RecognitionException, TokenStreamException
/*      */   {
/* 1631 */     this.returnAST = null;
/* 1632 */     ASTPair currentAST = new ASTPair();
/* 1633 */     GrammarAST exceptionGroup_AST = null;
/*      */     try
/*      */     {
/* 1636 */       switch (LA(1))
/*      */       {
/*      */       case 66:
/* 1640 */         int _cnt73 = 0;
/*      */         while (true)
/*      */         {
/* 1643 */           if (LA(1) == 66) {
/* 1644 */             exceptionHandler();
/* 1645 */             this.astFactory.addASTChild(currentAST, this.returnAST);
/*      */           }
/*      */           else {
/* 1648 */             if (_cnt73 >= 1) break; throw new NoViableAltException(LT(1), getFilename());
/*      */           }
/*      */ 
/* 1651 */           _cnt73++;
/*      */         }
/*      */ 
/* 1655 */         switch (LA(1))
/*      */         {
/*      */         case 67:
/* 1658 */           finallyClause();
/* 1659 */           this.astFactory.addASTChild(currentAST, this.returnAST);
/* 1660 */           break;
/*      */         case 1:
/*      */         case 38:
/*      */         case 41:
/*      */         case 55:
/*      */         case 56:
/*      */         case 57:
/*      */         case 58:
/*      */         case 73:
/* 1671 */           break;
/*      */         default:
/* 1675 */           throw new NoViableAltException(LT(1), getFilename());
/*      */         }
/*      */ 
/* 1679 */         exceptionGroup_AST = (GrammarAST)currentAST.root;
/* 1680 */         break;
/*      */       case 67:
/* 1684 */         finallyClause();
/* 1685 */         this.astFactory.addASTChild(currentAST, this.returnAST);
/* 1686 */         exceptionGroup_AST = (GrammarAST)currentAST.root;
/* 1687 */         break;
/*      */       default:
/* 1691 */         throw new NoViableAltException(LT(1), getFilename());
/*      */       }
/*      */     }
/*      */     catch (RecognitionException ex)
/*      */     {
/* 1696 */       reportError(ex);
/* 1697 */       recover(ex, _tokenSet_13);
/*      */     }
/* 1699 */     this.returnAST = exceptionGroup_AST;
/*      */   }
/*      */ 
/*      */   public final void ruleAction()
/*      */     throws RecognitionException, TokenStreamException
/*      */   {
/* 1705 */     this.returnAST = null;
/* 1706 */     ASTPair currentAST = new ASTPair();
/* 1707 */     GrammarAST ruleAction_AST = null;
/*      */     try
/*      */     {
/* 1710 */       GrammarAST tmp38_AST = null;
/* 1711 */       tmp38_AST = (GrammarAST)this.astFactory.create(LT(1));
/* 1712 */       this.astFactory.makeASTRoot(currentAST, tmp38_AST);
/* 1713 */       match(46);
/* 1714 */       id();
/* 1715 */       this.astFactory.addASTChild(currentAST, this.returnAST);
/* 1716 */       GrammarAST tmp39_AST = null;
/* 1717 */       tmp39_AST = (GrammarAST)this.astFactory.create(LT(1));
/* 1718 */       this.astFactory.addASTChild(currentAST, tmp39_AST);
/* 1719 */       match(40);
/* 1720 */       ruleAction_AST = (GrammarAST)currentAST.root;
/*      */     }
/*      */     catch (RecognitionException ex) {
/* 1723 */       reportError(ex);
/* 1724 */       recover(ex, _tokenSet_17);
/*      */     }
/* 1726 */     this.returnAST = ruleAction_AST;
/*      */   }
/*      */ 
/*      */   public final void idList() throws RecognitionException, TokenStreamException
/*      */   {
/* 1731 */     this.returnAST = null;
/* 1732 */     ASTPair currentAST = new ASTPair();
/* 1733 */     GrammarAST idList_AST = null;
/*      */     try
/*      */     {
/* 1736 */       id();
/* 1737 */       this.astFactory.addASTChild(currentAST, this.returnAST);
/*      */ 
/* 1741 */       while (LA(1) == 54) {
/* 1742 */         match(54);
/* 1743 */         id();
/* 1744 */         this.astFactory.addASTChild(currentAST, this.returnAST);
/*      */       }
/*      */ 
/* 1752 */       idList_AST = (GrammarAST)currentAST.root;
/*      */     }
/*      */     catch (RecognitionException ex) {
/* 1755 */       reportError(ex);
/* 1756 */       recover(ex, _tokenSet_10);
/*      */     }
/* 1758 */     this.returnAST = idList_AST;
/*      */   }
/*      */ 
/*      */   public final void block()
/*      */     throws RecognitionException, TokenStreamException
/*      */   {
/* 1764 */     this.returnAST = null;
/* 1765 */     ASTPair currentAST = new ASTPair();
/* 1766 */     GrammarAST block_AST = null;
/* 1767 */     Token lp = null;
/* 1768 */     GrammarAST lp_AST = null;
/* 1769 */     GrammarAST a1_AST = null;
/* 1770 */     GrammarAST a2_AST = null;
/* 1771 */     Token rp = null;
/* 1772 */     GrammarAST rp_AST = null;
/*      */ 
/* 1774 */     GrammarAST save = this.currentBlockAST;
/* 1775 */     Map opts = null;
/*      */     try
/*      */     {
/* 1779 */       lp = LT(1);
/* 1780 */       lp_AST = (GrammarAST)this.astFactory.create(lp);
/* 1781 */       this.astFactory.makeASTRoot(currentAST, lp_AST);
/* 1782 */       match(63);
/* 1783 */       lp_AST.setType(9); lp_AST.setText("BLOCK");
/*      */ 
/* 1785 */       if ((LA(1) == 4) || (LA(1) == 46) || (LA(1) == 47))
/*      */       {
/* 1787 */         switch (LA(1))
/*      */         {
/*      */         case 4:
/* 1790 */           opts = optionsSpec();
/* 1791 */           this.astFactory.addASTChild(currentAST, this.returnAST);
/* 1792 */           block_AST = (GrammarAST)currentAST.root;
/* 1793 */           block_AST.setOptions(this.grammar, opts);
/* 1794 */           break;
/*      */         case 46:
/*      */         case 47:
/* 1799 */           break;
/*      */         default:
/* 1803 */           throw new NoViableAltException(LT(1), getFilename());
/*      */         }
/*      */ 
/* 1808 */         switch (LA(1))
/*      */         {
/*      */         case 46:
/* 1811 */           ruleActions();
/* 1812 */           this.astFactory.addASTChild(currentAST, this.returnAST);
/* 1813 */           break;
/*      */         case 47:
/* 1817 */           break;
/*      */         default:
/* 1821 */           throw new NoViableAltException(LT(1), getFilename());
/*      */         }
/*      */ 
/* 1825 */         match(47);
/*      */       }
/* 1827 */       else if ((LA(1) == 40) && (LA(2) == 47) && (_tokenSet_18.member(LA(3)))) {
/* 1828 */         GrammarAST tmp42_AST = null;
/* 1829 */         tmp42_AST = (GrammarAST)this.astFactory.create(LT(1));
/* 1830 */         this.astFactory.addASTChild(currentAST, tmp42_AST);
/* 1831 */         match(40);
/* 1832 */         match(47);
/*      */       }
/* 1834 */       else if ((!_tokenSet_18.member(LA(1))) || (!_tokenSet_19.member(LA(2))) || (!_tokenSet_20.member(LA(3))))
/*      */       {
/* 1837 */         throw new NoViableAltException(LT(1), getFilename());
/*      */       }
/*      */ 
/* 1841 */       this.currentBlockAST = lp_AST;
/* 1842 */       alternative();
/* 1843 */       a1_AST = (GrammarAST)this.returnAST;
/* 1844 */       this.astFactory.addASTChild(currentAST, this.returnAST);
/* 1845 */       rewrite();
/* 1846 */       this.astFactory.addASTChild(currentAST, this.returnAST);
/* 1847 */       if ((LA(1) == 64) || (LA(2) == 76) || (LA(2) == 77) || (LA(2) == 53)) prefixWithSynPred(a1_AST);
/*      */ 
/* 1858 */       for (; LA(1) == 64; 
/* 1858 */         prefixWithSynPred(a2_AST))
/*      */       {
/* 1852 */         label534: match(64);
/* 1853 */         alternative();
/* 1854 */         a2_AST = (GrammarAST)this.returnAST;
/* 1855 */         this.astFactory.addASTChild(currentAST, this.returnAST);
/* 1856 */         rewrite();
/* 1857 */         this.astFactory.addASTChild(currentAST, this.returnAST);
/* 1858 */         if ((LA(1) != 64) && (LA(2) != 76) && (LA(2) != 77) && (LA(2) != 53))
/*      */         {
/*      */           break label534;
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/* 1866 */       rp = LT(1);
/* 1867 */       rp_AST = (GrammarAST)this.astFactory.create(rp);
/* 1868 */       match(65);
/* 1869 */       block_AST = (GrammarAST)currentAST.root;
/*      */ 
/* 1871 */       this.currentBlockAST = save;
/* 1872 */       GrammarAST eob = (GrammarAST)this.astFactory.create(19, "<end-of-block>");
/* 1873 */       eob.setLine(rp.getLine());
/* 1874 */       eob.setColumn(rp.getColumn());
/* 1875 */       block_AST.addChild(eob);
/*      */ 
/* 1877 */       block_AST = (GrammarAST)currentAST.root;
/*      */     }
/*      */     catch (RecognitionException ex) {
/* 1880 */       reportError(ex);
/* 1881 */       recover(ex, _tokenSet_21);
/*      */     }
/* 1883 */     this.returnAST = block_AST;
/*      */   }
/*      */ 
/*      */   public final void alternative() throws RecognitionException, TokenStreamException
/*      */   {
/* 1888 */     this.returnAST = null;
/* 1889 */     ASTPair currentAST = new ASTPair();
/* 1890 */     GrammarAST alternative_AST = null;
/* 1891 */     GrammarAST el_AST = null;
/*      */ 
/* 1893 */     GrammarAST eoa = (GrammarAST)this.astFactory.create(20, "<end-of-alt>");
/* 1894 */     GrammarAST altRoot = (GrammarAST)this.astFactory.create(17, "ALT");
/* 1895 */     altRoot.setLine(LT(1).getLine());
/* 1896 */     altRoot.setColumn(LT(1).getColumn());
/*      */     try
/*      */     {
/* 1900 */       switch (LA(1))
/*      */       {
/*      */       case 30:
/*      */       case 40:
/*      */       case 50:
/*      */       case 51:
/*      */       case 55:
/*      */       case 63:
/*      */       case 69:
/*      */       case 72:
/*      */       case 73:
/*      */       case 74:
/*      */       case 75:
/* 1914 */         int _cnt70 = 0;
/*      */         while (true)
/*      */         {
/* 1917 */           if (_tokenSet_22.member(LA(1))) {
/* 1918 */             element();
/* 1919 */             el_AST = (GrammarAST)this.returnAST;
/* 1920 */             this.astFactory.addASTChild(currentAST, this.returnAST);
/*      */           }
/*      */           else {
/* 1923 */             if (_cnt70 >= 1) break; throw new NoViableAltException(LT(1), getFilename());
/*      */           }
/*      */ 
/* 1926 */           _cnt70++;
/*      */         }
/*      */ 
/* 1929 */         alternative_AST = (GrammarAST)currentAST.root;
/*      */ 
/* 1931 */         if (alternative_AST == null) {
/* 1932 */           alternative_AST = (GrammarAST)this.astFactory.make(new ASTArray(3).add(altRoot).add((GrammarAST)this.astFactory.create(16, "epsilon")).add(eoa));
/*      */         }
/*      */         else
/*      */         {
/* 1936 */           alternative_AST = (GrammarAST)this.astFactory.make(new ASTArray(3).add(altRoot).add(alternative_AST).add(eoa));
/*      */         }
/*      */ 
/* 1939 */         currentAST.root = alternative_AST;
/* 1940 */         currentAST.child = ((alternative_AST != null) && (alternative_AST.getFirstChild() != null) ? alternative_AST.getFirstChild() : alternative_AST);
/*      */ 
/* 1942 */         currentAST.advanceChildToEnd();
/* 1943 */         alternative_AST = (GrammarAST)currentAST.root;
/* 1944 */         break;
/*      */       case 42:
/*      */       case 64:
/*      */       case 65:
/*      */       case 80:
/* 1951 */         alternative_AST = (GrammarAST)currentAST.root;
/*      */ 
/* 1953 */         GrammarAST eps = (GrammarAST)this.astFactory.create(16, "epsilon");
/* 1954 */         eps.setLine(LT(0).getLine());
/* 1955 */         eps.setColumn(LT(0).getColumn());
/* 1956 */         alternative_AST = (GrammarAST)this.astFactory.make(new ASTArray(3).add(altRoot).add(eps).add(eoa));
/*      */ 
/* 1958 */         currentAST.root = alternative_AST;
/* 1959 */         currentAST.child = ((alternative_AST != null) && (alternative_AST.getFirstChild() != null) ? alternative_AST.getFirstChild() : alternative_AST);
/*      */ 
/* 1961 */         currentAST.advanceChildToEnd();
/* 1962 */         alternative_AST = (GrammarAST)currentAST.root;
/* 1963 */         break;
/*      */       case 31:
/*      */       case 32:
/*      */       case 33:
/*      */       case 34:
/*      */       case 35:
/*      */       case 36:
/*      */       case 37:
/*      */       case 38:
/*      */       case 39:
/*      */       case 41:
/*      */       case 43:
/*      */       case 44:
/*      */       case 45:
/*      */       case 46:
/*      */       case 47:
/*      */       case 48:
/*      */       case 49:
/*      */       case 52:
/*      */       case 53:
/*      */       case 54:
/*      */       case 56:
/*      */       case 57:
/*      */       case 58:
/*      */       case 59:
/*      */       case 60:
/*      */       case 61:
/*      */       case 62:
/*      */       case 66:
/*      */       case 67:
/*      */       case 68:
/*      */       case 70:
/*      */       case 71:
/*      */       case 76:
/*      */       case 77:
/*      */       case 78:
/*      */       case 79:
/*      */       default:
/* 1967 */         throw new NoViableAltException(LT(1), getFilename());
/*      */       }
/*      */     }
/*      */     catch (RecognitionException ex)
/*      */     {
/* 1972 */       reportError(ex);
/* 1973 */       recover(ex, _tokenSet_23);
/*      */     }
/* 1975 */     this.returnAST = alternative_AST;
/*      */   }
/*      */ 
/*      */   public final void rewrite() throws RecognitionException, TokenStreamException
/*      */   {
/* 1980 */     this.returnAST = null;
/* 1981 */     ASTPair currentAST = new ASTPair();
/* 1982 */     GrammarAST rewrite_AST = null;
/* 1983 */     Token rew = null;
/* 1984 */     GrammarAST rew_AST = null;
/* 1985 */     Token pred = null;
/* 1986 */     GrammarAST pred_AST = null;
/* 1987 */     GrammarAST alt_AST = null;
/* 1988 */     Token rew2 = null;
/* 1989 */     GrammarAST rew2_AST = null;
/* 1990 */     GrammarAST alt2_AST = null;
/*      */ 
/* 1992 */     GrammarAST root = new GrammarAST();
/*      */     try
/*      */     {
/* 1996 */       switch (LA(1))
/*      */       {
/*      */       case 80:
/* 2002 */         while ((LA(1) == 80) && (LA(2) == 69)) {
/* 2003 */           rew = LT(1);
/* 2004 */           rew_AST = (GrammarAST)this.astFactory.create(rew);
/* 2005 */           match(80);
/* 2006 */           pred = LT(1);
/* 2007 */           pred_AST = (GrammarAST)this.astFactory.create(pred);
/* 2008 */           match(69);
/* 2009 */           rewrite_alternative();
/* 2010 */           alt_AST = (GrammarAST)this.returnAST;
/* 2011 */           root.addChild((GrammarAST)this.astFactory.make(new ASTArray(3).add(rew_AST).add(pred_AST).add(alt_AST)));
/*      */         }
/*      */ 
/* 2019 */         rew2 = LT(1);
/* 2020 */         rew2_AST = (GrammarAST)this.astFactory.create(rew2);
/* 2021 */         match(80);
/* 2022 */         rewrite_alternative();
/* 2023 */         alt2_AST = (GrammarAST)this.returnAST;
/* 2024 */         rewrite_AST = (GrammarAST)currentAST.root;
/*      */ 
/* 2026 */         root.addChild((GrammarAST)this.astFactory.make(new ASTArray(2).add(rew2_AST).add(alt2_AST)));
/* 2027 */         rewrite_AST = (GrammarAST)root.getFirstChild();
/*      */ 
/* 2029 */         currentAST.root = rewrite_AST;
/* 2030 */         currentAST.child = ((rewrite_AST != null) && (rewrite_AST.getFirstChild() != null) ? rewrite_AST.getFirstChild() : rewrite_AST);
/*      */ 
/* 2032 */         currentAST.advanceChildToEnd();
/* 2033 */         break;
/*      */       case 42:
/*      */       case 64:
/*      */       case 65:
/* 2039 */         rewrite_AST = (GrammarAST)currentAST.root;
/* 2040 */         break;
/*      */       default:
/* 2044 */         throw new NoViableAltException(LT(1), getFilename());
/*      */       }
/*      */     }
/*      */     catch (RecognitionException ex)
/*      */     {
/* 2049 */       reportError(ex);
/* 2050 */       recover(ex, _tokenSet_24);
/*      */     }
/* 2052 */     this.returnAST = rewrite_AST;
/*      */   }
/*      */ 
/*      */   public final void element() throws RecognitionException, TokenStreamException
/*      */   {
/* 2057 */     this.returnAST = null;
/* 2058 */     ASTPair currentAST = new ASTPair();
/* 2059 */     GrammarAST element_AST = null;
/*      */     try
/*      */     {
/* 2062 */       elementNoOptionSpec();
/* 2063 */       this.astFactory.addASTChild(currentAST, this.returnAST);
/* 2064 */       element_AST = (GrammarAST)currentAST.root;
/*      */     }
/*      */     catch (RecognitionException ex) {
/* 2067 */       reportError(ex);
/* 2068 */       recover(ex, _tokenSet_25);
/*      */     }
/* 2070 */     this.returnAST = element_AST;
/*      */   }
/*      */ 
/*      */   public final void exceptionHandler() throws RecognitionException, TokenStreamException
/*      */   {
/* 2075 */     this.returnAST = null;
/* 2076 */     ASTPair currentAST = new ASTPair();
/* 2077 */     GrammarAST exceptionHandler_AST = null;
/*      */     try
/*      */     {
/* 2080 */       GrammarAST tmp45_AST = null;
/* 2081 */       tmp45_AST = (GrammarAST)this.astFactory.create(LT(1));
/* 2082 */       this.astFactory.makeASTRoot(currentAST, tmp45_AST);
/* 2083 */       match(66);
/* 2084 */       GrammarAST tmp46_AST = null;
/* 2085 */       tmp46_AST = (GrammarAST)this.astFactory.create(LT(1));
/* 2086 */       this.astFactory.addASTChild(currentAST, tmp46_AST);
/* 2087 */       match(60);
/* 2088 */       GrammarAST tmp47_AST = null;
/* 2089 */       tmp47_AST = (GrammarAST)this.astFactory.create(LT(1));
/* 2090 */       this.astFactory.addASTChild(currentAST, tmp47_AST);
/* 2091 */       match(40);
/* 2092 */       exceptionHandler_AST = (GrammarAST)currentAST.root;
/*      */     }
/*      */     catch (RecognitionException ex) {
/* 2095 */       reportError(ex);
/* 2096 */       recover(ex, _tokenSet_26);
/*      */     }
/* 2098 */     this.returnAST = exceptionHandler_AST;
/*      */   }
/*      */ 
/*      */   public final void finallyClause() throws RecognitionException, TokenStreamException
/*      */   {
/* 2103 */     this.returnAST = null;
/* 2104 */     ASTPair currentAST = new ASTPair();
/* 2105 */     GrammarAST finallyClause_AST = null;
/*      */     try
/*      */     {
/* 2108 */       GrammarAST tmp48_AST = null;
/* 2109 */       tmp48_AST = (GrammarAST)this.astFactory.create(LT(1));
/* 2110 */       this.astFactory.makeASTRoot(currentAST, tmp48_AST);
/* 2111 */       match(67);
/* 2112 */       GrammarAST tmp49_AST = null;
/* 2113 */       tmp49_AST = (GrammarAST)this.astFactory.create(LT(1));
/* 2114 */       this.astFactory.addASTChild(currentAST, tmp49_AST);
/* 2115 */       match(40);
/* 2116 */       finallyClause_AST = (GrammarAST)currentAST.root;
/*      */     }
/*      */     catch (RecognitionException ex) {
/* 2119 */       reportError(ex);
/* 2120 */       recover(ex, _tokenSet_13);
/*      */     }
/* 2122 */     this.returnAST = finallyClause_AST;
/*      */   }
/*      */ 
/*      */   public final void elementNoOptionSpec() throws RecognitionException, TokenStreamException
/*      */   {
/* 2127 */     this.returnAST = null;
/* 2128 */     ASTPair currentAST = new ASTPair();
/* 2129 */     GrammarAST elementNoOptionSpec_AST = null;
/* 2130 */     Token p = null;
/* 2131 */     GrammarAST p_AST = null;
/* 2132 */     GrammarAST t3_AST = null;
/*      */ 
/* 2134 */     IntSet elements = null;
/*      */     try
/*      */     {
/* 2140 */       switch (LA(1))
/*      */       {
/*      */       case 63:
/* 2143 */         ebnf();
/* 2144 */         this.astFactory.addASTChild(currentAST, this.returnAST);
/* 2145 */         break;
/*      */       case 30:
/* 2149 */         GrammarAST tmp50_AST = null;
/* 2150 */         tmp50_AST = (GrammarAST)this.astFactory.create(LT(1));
/* 2151 */         this.astFactory.addASTChild(currentAST, tmp50_AST);
/* 2152 */         match(30);
/* 2153 */         break;
/*      */       case 40:
/* 2157 */         GrammarAST tmp51_AST = null;
/* 2158 */         tmp51_AST = (GrammarAST)this.astFactory.create(LT(1));
/* 2159 */         this.astFactory.addASTChild(currentAST, tmp51_AST);
/* 2160 */         match(40);
/* 2161 */         break;
/*      */       case 69:
/* 2165 */         p = LT(1);
/* 2166 */         p_AST = (GrammarAST)this.astFactory.create(p);
/* 2167 */         this.astFactory.addASTChild(currentAST, p_AST);
/* 2168 */         match(69);
/*      */ 
/* 2170 */         switch (LA(1))
/*      */         {
/*      */         case 70:
/* 2173 */           match(70);
/* 2174 */           p_AST.setType(35);
/* 2175 */           break;
/*      */         case 30:
/*      */         case 40:
/*      */         case 42:
/*      */         case 50:
/*      */         case 51:
/*      */         case 55:
/*      */         case 63:
/*      */         case 64:
/*      */         case 65:
/*      */         case 69:
/*      */         case 72:
/*      */         case 73:
/*      */         case 74:
/*      */         case 75:
/*      */         case 80:
/* 2193 */           break;
/*      */         case 31:
/*      */         case 32:
/*      */         case 33:
/*      */         case 34:
/*      */         case 35:
/*      */         case 36:
/*      */         case 37:
/*      */         case 38:
/*      */         case 39:
/*      */         case 41:
/*      */         case 43:
/*      */         case 44:
/*      */         case 45:
/*      */         case 46:
/*      */         case 47:
/*      */         case 48:
/*      */         case 49:
/*      */         case 52:
/*      */         case 53:
/*      */         case 54:
/*      */         case 56:
/*      */         case 57:
/*      */         case 58:
/*      */         case 59:
/*      */         case 60:
/*      */         case 61:
/*      */         case 62:
/*      */         case 66:
/*      */         case 67:
/*      */         case 68:
/*      */         case 71:
/*      */         case 76:
/*      */         case 77:
/*      */         case 78:
/*      */         case 79:
/*      */         default:
/* 2197 */           throw new NoViableAltException(LT(1), getFilename());
/*      */         }
/*      */ 
/* 2202 */         this.grammar.blocksWithSemPreds.add(this.currentBlockAST);
/*      */ 
/* 2204 */         break;
/*      */       case 75:
/* 2208 */         tree();
/* 2209 */         t3_AST = (GrammarAST)this.returnAST;
/* 2210 */         this.astFactory.addASTChild(currentAST, this.returnAST);
/* 2211 */         break;
/*      */       default:
/* 2214 */         if (((LA(1) == 55) || (LA(1) == 73)) && ((LA(2) == 49) || (LA(2) == 68))) {
/* 2215 */           id();
/* 2216 */           this.astFactory.addASTChild(currentAST, this.returnAST);
/*      */ 
/* 2218 */           switch (LA(1))
/*      */           {
/*      */           case 49:
/* 2221 */             GrammarAST tmp53_AST = null;
/* 2222 */             tmp53_AST = (GrammarAST)this.astFactory.create(LT(1));
/* 2223 */             this.astFactory.makeASTRoot(currentAST, tmp53_AST);
/* 2224 */             match(49);
/* 2225 */             break;
/*      */           case 68:
/* 2229 */             GrammarAST tmp54_AST = null;
/* 2230 */             tmp54_AST = (GrammarAST)this.astFactory.create(LT(1));
/* 2231 */             this.astFactory.makeASTRoot(currentAST, tmp54_AST);
/* 2232 */             match(68);
/* 2233 */             break;
/*      */           default:
/* 2237 */             throw new NoViableAltException(LT(1), getFilename());
/*      */           }
/*      */ 
/* 2242 */           switch (LA(1))
/*      */           {
/*      */           case 50:
/*      */           case 51:
/*      */           case 55:
/*      */           case 72:
/*      */           case 73:
/*      */           case 74:
/* 2250 */             atom();
/* 2251 */             this.astFactory.addASTChild(currentAST, this.returnAST);
/* 2252 */             break;
/*      */           case 63:
/* 2256 */             block();
/* 2257 */             this.astFactory.addASTChild(currentAST, this.returnAST);
/* 2258 */             break;
/*      */           case 52:
/*      */           case 53:
/*      */           case 54:
/*      */           case 56:
/*      */           case 57:
/*      */           case 58:
/*      */           case 59:
/*      */           case 60:
/*      */           case 61:
/*      */           case 62:
/*      */           case 64:
/*      */           case 65:
/*      */           case 66:
/*      */           case 67:
/*      */           case 68:
/*      */           case 69:
/*      */           case 70:
/*      */           case 71:
/*      */           default:
/* 2262 */             throw new NoViableAltException(LT(1), getFilename());
/*      */           }
/*      */ 
/*      */         }
/*      */ 
/* 2267 */         switch (LA(1))
/*      */         {
/*      */         case 53:
/*      */         case 76:
/*      */         case 77:
/* 2272 */           GrammarAST sub = ebnfSuffix((GrammarAST)currentAST.root, false);
/* 2273 */           elementNoOptionSpec_AST = (GrammarAST)currentAST.root;
/* 2274 */           elementNoOptionSpec_AST = sub;
/* 2275 */           currentAST.root = elementNoOptionSpec_AST;
/* 2276 */           currentAST.child = ((elementNoOptionSpec_AST != null) && (elementNoOptionSpec_AST.getFirstChild() != null) ? elementNoOptionSpec_AST.getFirstChild() : elementNoOptionSpec_AST);
/*      */ 
/* 2278 */           currentAST.advanceChildToEnd();
/* 2279 */           break;
/*      */         case 30:
/*      */         case 40:
/*      */         case 42:
/*      */         case 50:
/*      */         case 51:
/*      */         case 55:
/*      */         case 63:
/*      */         case 64:
/*      */         case 65:
/*      */         case 69:
/*      */         case 72:
/*      */         case 73:
/*      */         case 74:
/*      */         case 75:
/*      */         case 80:
/* 2297 */           break;
/*      */         case 31:
/*      */         case 32:
/*      */         case 33:
/*      */         case 34:
/*      */         case 35:
/*      */         case 36:
/*      */         case 37:
/*      */         case 38:
/*      */         case 39:
/*      */         case 41:
/*      */         case 43:
/*      */         case 44:
/*      */         case 45:
/*      */         case 46:
/*      */         case 47:
/*      */         case 48:
/*      */         case 49:
/*      */         case 52:
/*      */         case 54:
/*      */         case 56:
/*      */         case 57:
/*      */         case 58:
/*      */         case 59:
/*      */         case 60:
/*      */         case 61:
/*      */         case 62:
/*      */         case 66:
/*      */         case 67:
/*      */         case 68:
/*      */         case 70:
/*      */         case 71:
/*      */         case 78:
/*      */         case 79:
/*      */         default:
/* 2301 */           throw new NoViableAltException(LT(1), getFilename());
/*      */ 
/* 2306 */           if ((_tokenSet_27.member(LA(1))) && (_tokenSet_28.member(LA(2)))) {
/* 2307 */             atom();
/* 2308 */             this.astFactory.addASTChild(currentAST, this.returnAST);
/*      */           }
/* 2310 */           switch (LA(1))
/*      */           {
/*      */           case 53:
/*      */           case 76:
/*      */           case 77:
/* 2315 */             GrammarAST sub2 = ebnfSuffix((GrammarAST)currentAST.root, false);
/* 2316 */             elementNoOptionSpec_AST = (GrammarAST)currentAST.root;
/* 2317 */             elementNoOptionSpec_AST = sub2;
/* 2318 */             currentAST.root = elementNoOptionSpec_AST;
/* 2319 */             currentAST.child = ((elementNoOptionSpec_AST != null) && (elementNoOptionSpec_AST.getFirstChild() != null) ? elementNoOptionSpec_AST.getFirstChild() : elementNoOptionSpec_AST);
/*      */ 
/* 2321 */             currentAST.advanceChildToEnd();
/* 2322 */             break;
/*      */           case 30:
/*      */           case 40:
/*      */           case 42:
/*      */           case 50:
/*      */           case 51:
/*      */           case 55:
/*      */           case 63:
/*      */           case 64:
/*      */           case 65:
/*      */           case 69:
/*      */           case 72:
/*      */           case 73:
/*      */           case 74:
/*      */           case 75:
/*      */           case 80:
/* 2340 */             break;
/*      */           case 31:
/*      */           case 32:
/*      */           case 33:
/*      */           case 34:
/*      */           case 35:
/*      */           case 36:
/*      */           case 37:
/*      */           case 38:
/*      */           case 39:
/*      */           case 41:
/*      */           case 43:
/*      */           case 44:
/*      */           case 45:
/*      */           case 46:
/*      */           case 47:
/*      */           case 48:
/*      */           case 49:
/*      */           case 52:
/*      */           case 54:
/*      */           case 56:
/*      */           case 57:
/*      */           case 58:
/*      */           case 59:
/*      */           case 60:
/*      */           case 61:
/*      */           case 62:
/*      */           case 66:
/*      */           case 67:
/*      */           case 68:
/*      */           case 70:
/*      */           case 71:
/*      */           case 78:
/*      */           case 79:
/*      */           default:
/* 2344 */             throw new NoViableAltException(LT(1), getFilename());
/*      */ 
/* 2350 */             throw new NoViableAltException(LT(1), getFilename());
/*      */           }break;
/*      */         }break;
/*      */       }
/* 2354 */       elementNoOptionSpec_AST = (GrammarAST)currentAST.root;
/*      */     }
/*      */     catch (RecognitionException ex) {
/* 2357 */       reportError(ex);
/* 2358 */       recover(ex, _tokenSet_25);
/*      */     }
/* 2360 */     this.returnAST = elementNoOptionSpec_AST;
/*      */   }
/*      */ 
/*      */   public final void atom() throws RecognitionException, TokenStreamException
/*      */   {
/* 2365 */     this.returnAST = null;
/* 2366 */     ASTPair currentAST = new ASTPair();
/* 2367 */     GrammarAST atom_AST = null;
/* 2368 */     Token w = null;
/* 2369 */     GrammarAST w_AST = null;
/*      */     try
/*      */     {
/* 2372 */       if ((LA(1) == 51) && (LA(2) == 14)) {
/* 2373 */         range();
/* 2374 */         this.astFactory.addASTChild(currentAST, this.returnAST);
/*      */ 
/* 2376 */         switch (LA(1))
/*      */         {
/*      */         case 71:
/* 2379 */           GrammarAST tmp55_AST = null;
/* 2380 */           tmp55_AST = (GrammarAST)this.astFactory.create(LT(1));
/* 2381 */           this.astFactory.makeASTRoot(currentAST, tmp55_AST);
/* 2382 */           match(71);
/* 2383 */           break;
/*      */         case 59:
/* 2387 */           GrammarAST tmp56_AST = null;
/* 2388 */           tmp56_AST = (GrammarAST)this.astFactory.create(LT(1));
/* 2389 */           this.astFactory.makeASTRoot(currentAST, tmp56_AST);
/* 2390 */           match(59);
/* 2391 */           break;
/*      */         case 30:
/*      */         case 40:
/*      */         case 42:
/*      */         case 50:
/*      */         case 51:
/*      */         case 53:
/*      */         case 55:
/*      */         case 63:
/*      */         case 64:
/*      */         case 65:
/*      */         case 69:
/*      */         case 72:
/*      */         case 73:
/*      */         case 74:
/*      */         case 75:
/*      */         case 76:
/*      */         case 77:
/*      */         case 80:
/* 2412 */           break;
/*      */         case 31:
/*      */         case 32:
/*      */         case 33:
/*      */         case 34:
/*      */         case 35:
/*      */         case 36:
/*      */         case 37:
/*      */         case 38:
/*      */         case 39:
/*      */         case 41:
/*      */         case 43:
/*      */         case 44:
/*      */         case 45:
/*      */         case 46:
/*      */         case 47:
/*      */         case 48:
/*      */         case 49:
/*      */         case 52:
/*      */         case 54:
/*      */         case 56:
/*      */         case 57:
/*      */         case 58:
/*      */         case 60:
/*      */         case 61:
/*      */         case 62:
/*      */         case 66:
/*      */         case 67:
/*      */         case 68:
/*      */         case 70:
/*      */         case 78:
/*      */         case 79:
/*      */         default:
/* 2416 */           throw new NoViableAltException(LT(1), getFilename());
/*      */         }
/*      */ 
/* 2420 */         atom_AST = (GrammarAST)currentAST.root;
/*      */       }
/* 2422 */       else if ((_tokenSet_29.member(LA(1))) && (_tokenSet_30.member(LA(2))))
/*      */       {
/* 2424 */         if (((LA(1) == 55) || (LA(1) == 73)) && (LA(2) == 72) && (_tokenSet_29.member(LA(3))) && (LT(1).getColumn() + LT(1).getText().length() == LT(2).getColumn()) && (LT(2).getColumn() + 1 == LT(3).getColumn()))
/*      */         {
/* 2426 */           id();
/* 2427 */           this.astFactory.addASTChild(currentAST, this.returnAST);
/* 2428 */           w = LT(1);
/* 2429 */           w_AST = (GrammarAST)this.astFactory.create(w);
/* 2430 */           this.astFactory.makeASTRoot(currentAST, w_AST);
/* 2431 */           match(72);
/*      */ 
/* 2433 */           switch (LA(1))
/*      */           {
/*      */           case 50:
/*      */           case 51:
/*      */           case 55:
/*      */           case 72:
/* 2439 */             terminal();
/* 2440 */             this.astFactory.addASTChild(currentAST, this.returnAST);
/* 2441 */             break;
/*      */           case 73:
/* 2445 */             ruleref();
/* 2446 */             this.astFactory.addASTChild(currentAST, this.returnAST);
/* 2447 */             break;
/*      */           default:
/* 2451 */             throw new NoViableAltException(LT(1), getFilename());
/*      */           }
/*      */ 
/* 2455 */           w_AST.setType(39);
/*      */         }
/* 2457 */         else if ((_tokenSet_31.member(LA(1))) && (_tokenSet_30.member(LA(2))) && (_tokenSet_20.member(LA(3)))) {
/* 2458 */           terminal();
/* 2459 */           this.astFactory.addASTChild(currentAST, this.returnAST);
/*      */         }
/* 2461 */         else if ((LA(1) == 73) && (_tokenSet_32.member(LA(2))) && (_tokenSet_20.member(LA(3)))) {
/* 2462 */           ruleref();
/* 2463 */           this.astFactory.addASTChild(currentAST, this.returnAST);
/*      */         }
/*      */         else {
/* 2466 */           throw new NoViableAltException(LT(1), getFilename());
/*      */         }
/*      */ 
/* 2470 */         atom_AST = (GrammarAST)currentAST.root;
/*      */       }
/* 2472 */       else if (LA(1) == 74) {
/* 2473 */         notSet();
/* 2474 */         this.astFactory.addASTChild(currentAST, this.returnAST);
/*      */ 
/* 2476 */         switch (LA(1))
/*      */         {
/*      */         case 71:
/* 2479 */           GrammarAST tmp57_AST = null;
/* 2480 */           tmp57_AST = (GrammarAST)this.astFactory.create(LT(1));
/* 2481 */           this.astFactory.makeASTRoot(currentAST, tmp57_AST);
/* 2482 */           match(71);
/* 2483 */           break;
/*      */         case 59:
/* 2487 */           GrammarAST tmp58_AST = null;
/* 2488 */           tmp58_AST = (GrammarAST)this.astFactory.create(LT(1));
/* 2489 */           this.astFactory.makeASTRoot(currentAST, tmp58_AST);
/* 2490 */           match(59);
/* 2491 */           break;
/*      */         case 30:
/*      */         case 40:
/*      */         case 42:
/*      */         case 50:
/*      */         case 51:
/*      */         case 53:
/*      */         case 55:
/*      */         case 63:
/*      */         case 64:
/*      */         case 65:
/*      */         case 69:
/*      */         case 72:
/*      */         case 73:
/*      */         case 74:
/*      */         case 75:
/*      */         case 76:
/*      */         case 77:
/*      */         case 80:
/* 2512 */           break;
/*      */         case 31:
/*      */         case 32:
/*      */         case 33:
/*      */         case 34:
/*      */         case 35:
/*      */         case 36:
/*      */         case 37:
/*      */         case 38:
/*      */         case 39:
/*      */         case 41:
/*      */         case 43:
/*      */         case 44:
/*      */         case 45:
/*      */         case 46:
/*      */         case 47:
/*      */         case 48:
/*      */         case 49:
/*      */         case 52:
/*      */         case 54:
/*      */         case 56:
/*      */         case 57:
/*      */         case 58:
/*      */         case 60:
/*      */         case 61:
/*      */         case 62:
/*      */         case 66:
/*      */         case 67:
/*      */         case 68:
/*      */         case 70:
/*      */         case 78:
/*      */         case 79:
/*      */         default:
/* 2516 */           throw new NoViableAltException(LT(1), getFilename());
/*      */         }
/*      */ 
/* 2520 */         atom_AST = (GrammarAST)currentAST.root;
/*      */       }
/*      */       else {
/* 2523 */         throw new NoViableAltException(LT(1), getFilename());
/*      */       }
/*      */     }
/*      */     catch (RecognitionException ex)
/*      */     {
/* 2528 */       reportError(ex);
/* 2529 */       recover(ex, _tokenSet_33);
/*      */     }
/* 2531 */     this.returnAST = atom_AST;
/*      */   }
/*      */ 
/*      */   public final GrammarAST ebnfSuffix(GrammarAST elemAST, boolean inRewrite)
/*      */     throws RecognitionException, TokenStreamException
/*      */   {
/* 2537 */     GrammarAST subrule = null;
/*      */ 
/* 2539 */     this.returnAST = null;
/* 2540 */     ASTPair currentAST = new ASTPair();
/* 2541 */     GrammarAST ebnfSuffix_AST = null;
/*      */ 
/* 2543 */     GrammarAST ebnfRoot = null;
/*      */     try
/*      */     {
/* 2548 */       switch (LA(1))
/*      */       {
/*      */       case 76:
/* 2551 */         GrammarAST tmp59_AST = null;
/* 2552 */         tmp59_AST = (GrammarAST)this.astFactory.create(LT(1));
/* 2553 */         match(76);
/* 2554 */         ebnfRoot = (GrammarAST)this.astFactory.create(10, "?");
/* 2555 */         break;
/*      */       case 53:
/* 2559 */         GrammarAST tmp60_AST = null;
/* 2560 */         tmp60_AST = (GrammarAST)this.astFactory.create(LT(1));
/* 2561 */         match(53);
/* 2562 */         ebnfRoot = (GrammarAST)this.astFactory.create(11, "*");
/* 2563 */         break;
/*      */       case 77:
/* 2567 */         GrammarAST tmp61_AST = null;
/* 2568 */         tmp61_AST = (GrammarAST)this.astFactory.create(LT(1));
/* 2569 */         match(77);
/* 2570 */         ebnfRoot = (GrammarAST)this.astFactory.create(12, "+");
/* 2571 */         break;
/*      */       default:
/* 2575 */         throw new NoViableAltException(LT(1), getFilename());
/*      */       }
/*      */ 
/* 2580 */       GrammarAST save = this.currentBlockAST;
/* 2581 */       ebnfRoot.setLine(elemAST.getLine());
/* 2582 */       ebnfRoot.setColumn(elemAST.getColumn());
/* 2583 */       GrammarAST blkRoot = (GrammarAST)this.astFactory.create(9, "BLOCK");
/* 2584 */       this.currentBlockAST = blkRoot;
/* 2585 */       GrammarAST eob = (GrammarAST)this.astFactory.create(19, "<end-of-block>");
/* 2586 */       eob.setLine(elemAST.getLine());
/* 2587 */       eob.setColumn(elemAST.getColumn());
/* 2588 */       GrammarAST alt = (GrammarAST)this.astFactory.make(new ASTArray(3).add((GrammarAST)this.astFactory.create(17, "ALT")).add(elemAST).add((GrammarAST)this.astFactory.create(20, "<end-of-alt>")));
/* 2589 */       if (!inRewrite) {
/* 2590 */         prefixWithSynPred(alt);
/*      */       }
/* 2592 */       subrule = (GrammarAST)this.astFactory.make(new ASTArray(2).add(ebnfRoot).add((GrammarAST)this.astFactory.make(new ASTArray(3).add(blkRoot).add(alt).add(eob))));
/*      */ 
/* 2594 */       this.currentBlockAST = save;
/*      */     }
/*      */     catch (RecognitionException ex)
/*      */     {
/* 2598 */       reportError(ex);
/* 2599 */       recover(ex, _tokenSet_34);
/*      */     }
/* 2601 */     this.returnAST = ebnfSuffix_AST;
/* 2602 */     return subrule;
/*      */   }
/*      */ 
/*      */   public final void ebnf()
/*      */     throws RecognitionException, TokenStreamException
/*      */   {
/* 2608 */     this.returnAST = null;
/* 2609 */     ASTPair currentAST = new ASTPair();
/* 2610 */     GrammarAST ebnf_AST = null;
/* 2611 */     GrammarAST b_AST = null;
/*      */ 
/* 2613 */     int line = LT(1).getLine();
/* 2614 */     int col = LT(1).getColumn();
/*      */     try
/*      */     {
/* 2618 */       block();
/* 2619 */       b_AST = (GrammarAST)this.returnAST;
/*      */ 
/* 2621 */       switch (LA(1))
/*      */       {
/*      */       case 76:
/* 2624 */         GrammarAST tmp62_AST = null;
/* 2625 */         tmp62_AST = (GrammarAST)this.astFactory.create(LT(1));
/* 2626 */         match(76);
/* 2627 */         ebnf_AST = (GrammarAST)currentAST.root;
/* 2628 */         ebnf_AST = (GrammarAST)this.astFactory.make(new ASTArray(2).add((GrammarAST)this.astFactory.create(10, "?")).add(b_AST));
/* 2629 */         currentAST.root = ebnf_AST;
/* 2630 */         currentAST.child = ((ebnf_AST != null) && (ebnf_AST.getFirstChild() != null) ? ebnf_AST.getFirstChild() : ebnf_AST);
/*      */ 
/* 2632 */         currentAST.advanceChildToEnd();
/* 2633 */         break;
/*      */       case 53:
/* 2637 */         GrammarAST tmp63_AST = null;
/* 2638 */         tmp63_AST = (GrammarAST)this.astFactory.create(LT(1));
/* 2639 */         match(53);
/* 2640 */         ebnf_AST = (GrammarAST)currentAST.root;
/* 2641 */         ebnf_AST = (GrammarAST)this.astFactory.make(new ASTArray(2).add((GrammarAST)this.astFactory.create(11, "*")).add(b_AST));
/* 2642 */         currentAST.root = ebnf_AST;
/* 2643 */         currentAST.child = ((ebnf_AST != null) && (ebnf_AST.getFirstChild() != null) ? ebnf_AST.getFirstChild() : ebnf_AST);
/*      */ 
/* 2645 */         currentAST.advanceChildToEnd();
/* 2646 */         break;
/*      */       case 77:
/* 2650 */         GrammarAST tmp64_AST = null;
/* 2651 */         tmp64_AST = (GrammarAST)this.astFactory.create(LT(1));
/* 2652 */         match(77);
/* 2653 */         ebnf_AST = (GrammarAST)currentAST.root;
/* 2654 */         ebnf_AST = (GrammarAST)this.astFactory.make(new ASTArray(2).add((GrammarAST)this.astFactory.create(12, "+")).add(b_AST));
/* 2655 */         currentAST.root = ebnf_AST;
/* 2656 */         currentAST.child = ((ebnf_AST != null) && (ebnf_AST.getFirstChild() != null) ? ebnf_AST.getFirstChild() : ebnf_AST);
/*      */ 
/* 2658 */         currentAST.advanceChildToEnd();
/* 2659 */         break;
/*      */       case 70:
/* 2663 */         match(70);
/* 2664 */         ebnf_AST = (GrammarAST)currentAST.root;
/*      */ 
/* 2666 */         if ((this.gtype == 28) && (Character.isUpperCase(this.currentRuleName.charAt(0))))
/*      */         {
/* 2670 */           ebnf_AST = (GrammarAST)this.astFactory.make(new ASTArray(2).add((GrammarAST)this.astFactory.create(13, "=>")).add(b_AST));
/*      */         }
/*      */         else
/*      */         {
/* 2675 */           ebnf_AST = createSynSemPredFromBlock(b_AST, 36);
/*      */         }
/*      */ 
/* 2678 */         currentAST.root = ebnf_AST;
/* 2679 */         currentAST.child = ((ebnf_AST != null) && (ebnf_AST.getFirstChild() != null) ? ebnf_AST.getFirstChild() : ebnf_AST);
/*      */ 
/* 2681 */         currentAST.advanceChildToEnd();
/* 2682 */         break;
/*      */       case 71:
/* 2686 */         GrammarAST tmp66_AST = null;
/* 2687 */         tmp66_AST = (GrammarAST)this.astFactory.create(LT(1));
/* 2688 */         match(71);
/* 2689 */         ebnf_AST = (GrammarAST)currentAST.root;
/* 2690 */         ebnf_AST = (GrammarAST)this.astFactory.make(new ASTArray(2).add(tmp66_AST).add(b_AST));
/* 2691 */         currentAST.root = ebnf_AST;
/* 2692 */         currentAST.child = ((ebnf_AST != null) && (ebnf_AST.getFirstChild() != null) ? ebnf_AST.getFirstChild() : ebnf_AST);
/*      */ 
/* 2694 */         currentAST.advanceChildToEnd();
/* 2695 */         break;
/*      */       case 59:
/* 2699 */         GrammarAST tmp67_AST = null;
/* 2700 */         tmp67_AST = (GrammarAST)this.astFactory.create(LT(1));
/* 2701 */         match(59);
/* 2702 */         ebnf_AST = (GrammarAST)currentAST.root;
/* 2703 */         ebnf_AST = (GrammarAST)this.astFactory.make(new ASTArray(2).add(tmp67_AST).add(b_AST));
/* 2704 */         currentAST.root = ebnf_AST;
/* 2705 */         currentAST.child = ((ebnf_AST != null) && (ebnf_AST.getFirstChild() != null) ? ebnf_AST.getFirstChild() : ebnf_AST);
/*      */ 
/* 2707 */         currentAST.advanceChildToEnd();
/* 2708 */         break;
/*      */       case 30:
/*      */       case 40:
/*      */       case 42:
/*      */       case 50:
/*      */       case 51:
/*      */       case 55:
/*      */       case 63:
/*      */       case 64:
/*      */       case 65:
/*      */       case 69:
/*      */       case 72:
/*      */       case 73:
/*      */       case 74:
/*      */       case 75:
/*      */       case 80:
/* 2726 */         ebnf_AST = (GrammarAST)currentAST.root;
/* 2727 */         ebnf_AST = b_AST;
/* 2728 */         currentAST.root = ebnf_AST;
/* 2729 */         currentAST.child = ((ebnf_AST != null) && (ebnf_AST.getFirstChild() != null) ? ebnf_AST.getFirstChild() : ebnf_AST);
/*      */ 
/* 2731 */         currentAST.advanceChildToEnd();
/* 2732 */         break;
/*      */       case 31:
/*      */       case 32:
/*      */       case 33:
/*      */       case 34:
/*      */       case 35:
/*      */       case 36:
/*      */       case 37:
/*      */       case 38:
/*      */       case 39:
/*      */       case 41:
/*      */       case 43:
/*      */       case 44:
/*      */       case 45:
/*      */       case 46:
/*      */       case 47:
/*      */       case 48:
/*      */       case 49:
/*      */       case 52:
/*      */       case 54:
/*      */       case 56:
/*      */       case 57:
/*      */       case 58:
/*      */       case 60:
/*      */       case 61:
/*      */       case 62:
/*      */       case 66:
/*      */       case 67:
/*      */       case 68:
/*      */       case 78:
/*      */       case 79:
/*      */       default:
/* 2736 */         throw new NoViableAltException(LT(1), getFilename());
/*      */       }
/*      */ 
/* 2740 */       ebnf_AST = (GrammarAST)currentAST.root;
/* 2741 */       ebnf_AST.setLine(line); ebnf_AST.setColumn(col);
/*      */     }
/*      */     catch (RecognitionException ex) {
/* 2744 */       reportError(ex);
/* 2745 */       recover(ex, _tokenSet_25);
/*      */     }
/* 2747 */     this.returnAST = ebnf_AST;
/*      */   }
/*      */ 
/*      */   public final void tree() throws RecognitionException, TokenStreamException
/*      */   {
/* 2752 */     this.returnAST = null;
/* 2753 */     ASTPair currentAST = new ASTPair();
/* 2754 */     GrammarAST tree_AST = null;
/*      */     try
/*      */     {
/* 2757 */       GrammarAST tmp68_AST = null;
/* 2758 */       tmp68_AST = (GrammarAST)this.astFactory.create(LT(1));
/* 2759 */       this.astFactory.makeASTRoot(currentAST, tmp68_AST);
/* 2760 */       match(75);
/* 2761 */       treeRoot();
/* 2762 */       this.astFactory.addASTChild(currentAST, this.returnAST);
/*      */ 
/* 2764 */       int _cnt101 = 0;
/*      */       while (true)
/*      */       {
/* 2767 */         if (_tokenSet_22.member(LA(1))) {
/* 2768 */           element();
/* 2769 */           this.astFactory.addASTChild(currentAST, this.returnAST);
/*      */         }
/*      */         else {
/* 2772 */           if (_cnt101 >= 1) break; throw new NoViableAltException(LT(1), getFilename());
/*      */         }
/*      */ 
/* 2775 */         _cnt101++;
/*      */       }
/*      */ 
/* 2778 */       match(65);
/* 2779 */       tree_AST = (GrammarAST)currentAST.root;
/*      */     }
/*      */     catch (RecognitionException ex) {
/* 2782 */       reportError(ex);
/* 2783 */       recover(ex, _tokenSet_25);
/*      */     }
/* 2785 */     this.returnAST = tree_AST;
/*      */   }
/*      */ 
/*      */   public final void range() throws RecognitionException, TokenStreamException
/*      */   {
/* 2790 */     this.returnAST = null;
/* 2791 */     ASTPair currentAST = new ASTPair();
/* 2792 */     GrammarAST range_AST = null;
/* 2793 */     Token c1 = null;
/* 2794 */     GrammarAST c1_AST = null;
/* 2795 */     Token c2 = null;
/* 2796 */     GrammarAST c2_AST = null;
/*      */ 
/* 2798 */     GrammarAST subrule = null; GrammarAST root = null;
/*      */     try
/*      */     {
/* 2802 */       c1 = LT(1);
/* 2803 */       c1_AST = (GrammarAST)this.astFactory.create(c1);
/* 2804 */       match(51);
/* 2805 */       GrammarAST tmp70_AST = null;
/* 2806 */       tmp70_AST = (GrammarAST)this.astFactory.create(LT(1));
/* 2807 */       match(14);
/* 2808 */       c2 = LT(1);
/* 2809 */       c2_AST = (GrammarAST)this.astFactory.create(c2);
/* 2810 */       match(51);
/* 2811 */       range_AST = (GrammarAST)currentAST.root;
/*      */ 
/* 2813 */       GrammarAST r = (GrammarAST)this.astFactory.create(15, "..");
/* 2814 */       r.setLine(c1.getLine());
/* 2815 */       r.setColumn(c1.getColumn());
/* 2816 */       range_AST = (GrammarAST)this.astFactory.make(new ASTArray(3).add(r).add(c1_AST).add(c2_AST));
/* 2817 */       root = range_AST;
/*      */ 
/* 2819 */       currentAST.root = range_AST;
/* 2820 */       currentAST.child = ((range_AST != null) && (range_AST.getFirstChild() != null) ? range_AST.getFirstChild() : range_AST);
/*      */ 
/* 2822 */       currentAST.advanceChildToEnd();
/*      */     }
/*      */     catch (RecognitionException ex) {
/* 2825 */       reportError(ex);
/* 2826 */       recover(ex, _tokenSet_35);
/*      */     }
/* 2828 */     this.returnAST = range_AST;
/*      */   }
/*      */ 
/*      */   public final void terminal() throws RecognitionException, TokenStreamException
/*      */   {
/* 2833 */     this.returnAST = null;
/* 2834 */     ASTPair currentAST = new ASTPair();
/* 2835 */     GrammarAST terminal_AST = null;
/* 2836 */     Token cl = null;
/* 2837 */     GrammarAST cl_AST = null;
/* 2838 */     Token tr = null;
/* 2839 */     GrammarAST tr_AST = null;
/* 2840 */     Token sl = null;
/* 2841 */     GrammarAST sl_AST = null;
/* 2842 */     Token wi = null;
/* 2843 */     GrammarAST wi_AST = null;
/*      */ 
/* 2845 */     GrammarAST ebnfRoot = null; GrammarAST subrule = null;
/*      */     try
/*      */     {
/* 2849 */       switch (LA(1))
/*      */       {
/*      */       case 51:
/* 2852 */         cl = LT(1);
/* 2853 */         cl_AST = (GrammarAST)this.astFactory.create(cl);
/* 2854 */         this.astFactory.makeASTRoot(currentAST, cl_AST);
/* 2855 */         match(51);
/*      */ 
/* 2857 */         switch (LA(1))
/*      */         {
/*      */         case 78:
/* 2860 */           elementOptions(cl_AST);
/* 2861 */           break;
/*      */         case 30:
/*      */         case 40:
/*      */         case 42:
/*      */         case 50:
/*      */         case 51:
/*      */         case 53:
/*      */         case 55:
/*      */         case 59:
/*      */         case 63:
/*      */         case 64:
/*      */         case 65:
/*      */         case 69:
/*      */         case 71:
/*      */         case 72:
/*      */         case 73:
/*      */         case 74:
/*      */         case 75:
/*      */         case 76:
/*      */         case 77:
/*      */         case 80:
/* 2884 */           break;
/*      */         case 31:
/*      */         case 32:
/*      */         case 33:
/*      */         case 34:
/*      */         case 35:
/*      */         case 36:
/*      */         case 37:
/*      */         case 38:
/*      */         case 39:
/*      */         case 41:
/*      */         case 43:
/*      */         case 44:
/*      */         case 45:
/*      */         case 46:
/*      */         case 47:
/*      */         case 48:
/*      */         case 49:
/*      */         case 52:
/*      */         case 54:
/*      */         case 56:
/*      */         case 57:
/*      */         case 58:
/*      */         case 60:
/*      */         case 61:
/*      */         case 62:
/*      */         case 66:
/*      */         case 67:
/*      */         case 68:
/*      */         case 70:
/*      */         case 79:
/*      */         default:
/* 2888 */           throw new NoViableAltException(LT(1), getFilename());
/*      */         }
/*      */ 
/* 2893 */         switch (LA(1))
/*      */         {
/*      */         case 71:
/* 2896 */           GrammarAST tmp71_AST = null;
/* 2897 */           tmp71_AST = (GrammarAST)this.astFactory.create(LT(1));
/* 2898 */           this.astFactory.makeASTRoot(currentAST, tmp71_AST);
/* 2899 */           match(71);
/* 2900 */           break;
/*      */         case 59:
/* 2904 */           GrammarAST tmp72_AST = null;
/* 2905 */           tmp72_AST = (GrammarAST)this.astFactory.create(LT(1));
/* 2906 */           this.astFactory.makeASTRoot(currentAST, tmp72_AST);
/* 2907 */           match(59);
/* 2908 */           break;
/*      */         case 30:
/*      */         case 40:
/*      */         case 42:
/*      */         case 50:
/*      */         case 51:
/*      */         case 53:
/*      */         case 55:
/*      */         case 63:
/*      */         case 64:
/*      */         case 65:
/*      */         case 69:
/*      */         case 72:
/*      */         case 73:
/*      */         case 74:
/*      */         case 75:
/*      */         case 76:
/*      */         case 77:
/*      */         case 80:
/* 2929 */           break;
/*      */         case 31:
/*      */         case 32:
/*      */         case 33:
/*      */         case 34:
/*      */         case 35:
/*      */         case 36:
/*      */         case 37:
/*      */         case 38:
/*      */         case 39:
/*      */         case 41:
/*      */         case 43:
/*      */         case 44:
/*      */         case 45:
/*      */         case 46:
/*      */         case 47:
/*      */         case 48:
/*      */         case 49:
/*      */         case 52:
/*      */         case 54:
/*      */         case 56:
/*      */         case 57:
/*      */         case 58:
/*      */         case 60:
/*      */         case 61:
/*      */         case 62:
/*      */         case 66:
/*      */         case 67:
/*      */         case 68:
/*      */         case 70:
/*      */         case 78:
/*      */         case 79:
/*      */         default:
/* 2933 */           throw new NoViableAltException(LT(1), getFilename());
/*      */         }
/*      */ 
/* 2937 */         terminal_AST = (GrammarAST)currentAST.root;
/* 2938 */         break;
/*      */       case 55:
/* 2942 */         tr = LT(1);
/* 2943 */         tr_AST = (GrammarAST)this.astFactory.create(tr);
/* 2944 */         this.astFactory.makeASTRoot(currentAST, tr_AST);
/* 2945 */         match(55);
/*      */ 
/* 2947 */         switch (LA(1))
/*      */         {
/*      */         case 78:
/* 2950 */           elementOptions(tr_AST);
/* 2951 */           break;
/*      */         case 30:
/*      */         case 40:
/*      */         case 42:
/*      */         case 50:
/*      */         case 51:
/*      */         case 53:
/*      */         case 55:
/*      */         case 59:
/*      */         case 60:
/*      */         case 63:
/*      */         case 64:
/*      */         case 65:
/*      */         case 69:
/*      */         case 71:
/*      */         case 72:
/*      */         case 73:
/*      */         case 74:
/*      */         case 75:
/*      */         case 76:
/*      */         case 77:
/*      */         case 80:
/* 2975 */           break;
/*      */         case 31:
/*      */         case 32:
/*      */         case 33:
/*      */         case 34:
/*      */         case 35:
/*      */         case 36:
/*      */         case 37:
/*      */         case 38:
/*      */         case 39:
/*      */         case 41:
/*      */         case 43:
/*      */         case 44:
/*      */         case 45:
/*      */         case 46:
/*      */         case 47:
/*      */         case 48:
/*      */         case 49:
/*      */         case 52:
/*      */         case 54:
/*      */         case 56:
/*      */         case 57:
/*      */         case 58:
/*      */         case 61:
/*      */         case 62:
/*      */         case 66:
/*      */         case 67:
/*      */         case 68:
/*      */         case 70:
/*      */         case 79:
/*      */         default:
/* 2979 */           throw new NoViableAltException(LT(1), getFilename());
/*      */         }
/*      */ 
/* 2984 */         switch (LA(1))
/*      */         {
/*      */         case 60:
/* 2987 */           GrammarAST tmp73_AST = null;
/* 2988 */           tmp73_AST = (GrammarAST)this.astFactory.create(LT(1));
/* 2989 */           this.astFactory.addASTChild(currentAST, tmp73_AST);
/* 2990 */           match(60);
/* 2991 */           break;
/*      */         case 30:
/*      */         case 40:
/*      */         case 42:
/*      */         case 50:
/*      */         case 51:
/*      */         case 53:
/*      */         case 55:
/*      */         case 59:
/*      */         case 63:
/*      */         case 64:
/*      */         case 65:
/*      */         case 69:
/*      */         case 71:
/*      */         case 72:
/*      */         case 73:
/*      */         case 74:
/*      */         case 75:
/*      */         case 76:
/*      */         case 77:
/*      */         case 80:
/* 3014 */           break;
/*      */         case 31:
/*      */         case 32:
/*      */         case 33:
/*      */         case 34:
/*      */         case 35:
/*      */         case 36:
/*      */         case 37:
/*      */         case 38:
/*      */         case 39:
/*      */         case 41:
/*      */         case 43:
/*      */         case 44:
/*      */         case 45:
/*      */         case 46:
/*      */         case 47:
/*      */         case 48:
/*      */         case 49:
/*      */         case 52:
/*      */         case 54:
/*      */         case 56:
/*      */         case 57:
/*      */         case 58:
/*      */         case 61:
/*      */         case 62:
/*      */         case 66:
/*      */         case 67:
/*      */         case 68:
/*      */         case 70:
/*      */         case 78:
/*      */         case 79:
/*      */         default:
/* 3018 */           throw new NoViableAltException(LT(1), getFilename());
/*      */         }
/*      */ 
/* 3023 */         switch (LA(1))
/*      */         {
/*      */         case 71:
/* 3026 */           GrammarAST tmp74_AST = null;
/* 3027 */           tmp74_AST = (GrammarAST)this.astFactory.create(LT(1));
/* 3028 */           this.astFactory.makeASTRoot(currentAST, tmp74_AST);
/* 3029 */           match(71);
/* 3030 */           break;
/*      */         case 59:
/* 3034 */           GrammarAST tmp75_AST = null;
/* 3035 */           tmp75_AST = (GrammarAST)this.astFactory.create(LT(1));
/* 3036 */           this.astFactory.makeASTRoot(currentAST, tmp75_AST);
/* 3037 */           match(59);
/* 3038 */           break;
/*      */         case 30:
/*      */         case 40:
/*      */         case 42:
/*      */         case 50:
/*      */         case 51:
/*      */         case 53:
/*      */         case 55:
/*      */         case 63:
/*      */         case 64:
/*      */         case 65:
/*      */         case 69:
/*      */         case 72:
/*      */         case 73:
/*      */         case 74:
/*      */         case 75:
/*      */         case 76:
/*      */         case 77:
/*      */         case 80:
/* 3059 */           break;
/*      */         case 31:
/*      */         case 32:
/*      */         case 33:
/*      */         case 34:
/*      */         case 35:
/*      */         case 36:
/*      */         case 37:
/*      */         case 38:
/*      */         case 39:
/*      */         case 41:
/*      */         case 43:
/*      */         case 44:
/*      */         case 45:
/*      */         case 46:
/*      */         case 47:
/*      */         case 48:
/*      */         case 49:
/*      */         case 52:
/*      */         case 54:
/*      */         case 56:
/*      */         case 57:
/*      */         case 58:
/*      */         case 60:
/*      */         case 61:
/*      */         case 62:
/*      */         case 66:
/*      */         case 67:
/*      */         case 68:
/*      */         case 70:
/*      */         case 78:
/*      */         case 79:
/*      */         default:
/* 3063 */           throw new NoViableAltException(LT(1), getFilename());
/*      */         }
/*      */ 
/* 3067 */         terminal_AST = (GrammarAST)currentAST.root;
/* 3068 */         break;
/*      */       case 50:
/* 3072 */         sl = LT(1);
/* 3073 */         sl_AST = (GrammarAST)this.astFactory.create(sl);
/* 3074 */         this.astFactory.makeASTRoot(currentAST, sl_AST);
/* 3075 */         match(50);
/*      */ 
/* 3077 */         switch (LA(1))
/*      */         {
/*      */         case 78:
/* 3080 */           elementOptions(sl_AST);
/* 3081 */           break;
/*      */         case 30:
/*      */         case 40:
/*      */         case 42:
/*      */         case 50:
/*      */         case 51:
/*      */         case 53:
/*      */         case 55:
/*      */         case 59:
/*      */         case 63:
/*      */         case 64:
/*      */         case 65:
/*      */         case 69:
/*      */         case 71:
/*      */         case 72:
/*      */         case 73:
/*      */         case 74:
/*      */         case 75:
/*      */         case 76:
/*      */         case 77:
/*      */         case 80:
/* 3104 */           break;
/*      */         case 31:
/*      */         case 32:
/*      */         case 33:
/*      */         case 34:
/*      */         case 35:
/*      */         case 36:
/*      */         case 37:
/*      */         case 38:
/*      */         case 39:
/*      */         case 41:
/*      */         case 43:
/*      */         case 44:
/*      */         case 45:
/*      */         case 46:
/*      */         case 47:
/*      */         case 48:
/*      */         case 49:
/*      */         case 52:
/*      */         case 54:
/*      */         case 56:
/*      */         case 57:
/*      */         case 58:
/*      */         case 60:
/*      */         case 61:
/*      */         case 62:
/*      */         case 66:
/*      */         case 67:
/*      */         case 68:
/*      */         case 70:
/*      */         case 79:
/*      */         default:
/* 3108 */           throw new NoViableAltException(LT(1), getFilename());
/*      */         }
/*      */ 
/* 3113 */         switch (LA(1))
/*      */         {
/*      */         case 71:
/* 3116 */           GrammarAST tmp76_AST = null;
/* 3117 */           tmp76_AST = (GrammarAST)this.astFactory.create(LT(1));
/* 3118 */           this.astFactory.makeASTRoot(currentAST, tmp76_AST);
/* 3119 */           match(71);
/* 3120 */           break;
/*      */         case 59:
/* 3124 */           GrammarAST tmp77_AST = null;
/* 3125 */           tmp77_AST = (GrammarAST)this.astFactory.create(LT(1));
/* 3126 */           this.astFactory.makeASTRoot(currentAST, tmp77_AST);
/* 3127 */           match(59);
/* 3128 */           break;
/*      */         case 30:
/*      */         case 40:
/*      */         case 42:
/*      */         case 50:
/*      */         case 51:
/*      */         case 53:
/*      */         case 55:
/*      */         case 63:
/*      */         case 64:
/*      */         case 65:
/*      */         case 69:
/*      */         case 72:
/*      */         case 73:
/*      */         case 74:
/*      */         case 75:
/*      */         case 76:
/*      */         case 77:
/*      */         case 80:
/* 3149 */           break;
/*      */         case 31:
/*      */         case 32:
/*      */         case 33:
/*      */         case 34:
/*      */         case 35:
/*      */         case 36:
/*      */         case 37:
/*      */         case 38:
/*      */         case 39:
/*      */         case 41:
/*      */         case 43:
/*      */         case 44:
/*      */         case 45:
/*      */         case 46:
/*      */         case 47:
/*      */         case 48:
/*      */         case 49:
/*      */         case 52:
/*      */         case 54:
/*      */         case 56:
/*      */         case 57:
/*      */         case 58:
/*      */         case 60:
/*      */         case 61:
/*      */         case 62:
/*      */         case 66:
/*      */         case 67:
/*      */         case 68:
/*      */         case 70:
/*      */         case 78:
/*      */         case 79:
/*      */         default:
/* 3153 */           throw new NoViableAltException(LT(1), getFilename());
/*      */         }
/*      */ 
/* 3157 */         terminal_AST = (GrammarAST)currentAST.root;
/* 3158 */         break;
/*      */       case 72:
/* 3162 */         wi = LT(1);
/* 3163 */         wi_AST = (GrammarAST)this.astFactory.create(wi);
/* 3164 */         this.astFactory.addASTChild(currentAST, wi_AST);
/* 3165 */         match(72);
/*      */ 
/* 3167 */         switch (LA(1))
/*      */         {
/*      */         case 71:
/* 3170 */           GrammarAST tmp78_AST = null;
/* 3171 */           tmp78_AST = (GrammarAST)this.astFactory.create(LT(1));
/* 3172 */           this.astFactory.makeASTRoot(currentAST, tmp78_AST);
/* 3173 */           match(71);
/* 3174 */           break;
/*      */         case 59:
/* 3178 */           GrammarAST tmp79_AST = null;
/* 3179 */           tmp79_AST = (GrammarAST)this.astFactory.create(LT(1));
/* 3180 */           this.astFactory.makeASTRoot(currentAST, tmp79_AST);
/* 3181 */           match(59);
/* 3182 */           break;
/*      */         case 30:
/*      */         case 40:
/*      */         case 42:
/*      */         case 50:
/*      */         case 51:
/*      */         case 53:
/*      */         case 55:
/*      */         case 63:
/*      */         case 64:
/*      */         case 65:
/*      */         case 69:
/*      */         case 72:
/*      */         case 73:
/*      */         case 74:
/*      */         case 75:
/*      */         case 76:
/*      */         case 77:
/*      */         case 80:
/* 3203 */           break;
/*      */         case 31:
/*      */         case 32:
/*      */         case 33:
/*      */         case 34:
/*      */         case 35:
/*      */         case 36:
/*      */         case 37:
/*      */         case 38:
/*      */         case 39:
/*      */         case 41:
/*      */         case 43:
/*      */         case 44:
/*      */         case 45:
/*      */         case 46:
/*      */         case 47:
/*      */         case 48:
/*      */         case 49:
/*      */         case 52:
/*      */         case 54:
/*      */         case 56:
/*      */         case 57:
/*      */         case 58:
/*      */         case 60:
/*      */         case 61:
/*      */         case 62:
/*      */         case 66:
/*      */         case 67:
/*      */         case 68:
/*      */         case 70:
/*      */         case 78:
/*      */         case 79:
/*      */         default:
/* 3207 */           throw new NoViableAltException(LT(1), getFilename());
/*      */         }
/*      */ 
/* 3212 */         if (this.atTreeRoot) {
/* 3213 */           ErrorManager.syntaxError(166, this.grammar, wi, null, null);
/*      */         }
/*      */ 
/* 3217 */         terminal_AST = (GrammarAST)currentAST.root;
/* 3218 */         break;
/*      */       default:
/* 3222 */         throw new NoViableAltException(LT(1), getFilename());
/*      */       }
/*      */     }
/*      */     catch (RecognitionException ex)
/*      */     {
/* 3227 */       reportError(ex);
/* 3228 */       recover(ex, _tokenSet_33);
/*      */     }
/* 3230 */     this.returnAST = terminal_AST;
/*      */   }
/*      */ 
/*      */   public final void ruleref() throws RecognitionException, TokenStreamException
/*      */   {
/* 3235 */     this.returnAST = null;
/* 3236 */     ASTPair currentAST = new ASTPair();
/* 3237 */     GrammarAST ruleref_AST = null;
/* 3238 */     Token rr = null;
/* 3239 */     GrammarAST rr_AST = null;
/*      */     try
/*      */     {
/* 3242 */       rr = LT(1);
/* 3243 */       rr_AST = (GrammarAST)this.astFactory.create(rr);
/* 3244 */       this.astFactory.makeASTRoot(currentAST, rr_AST);
/* 3245 */       match(73);
/*      */ 
/* 3247 */       switch (LA(1))
/*      */       {
/*      */       case 60:
/* 3250 */         GrammarAST tmp80_AST = null;
/* 3251 */         tmp80_AST = (GrammarAST)this.astFactory.create(LT(1));
/* 3252 */         this.astFactory.addASTChild(currentAST, tmp80_AST);
/* 3253 */         match(60);
/* 3254 */         break;
/*      */       case 30:
/*      */       case 40:
/*      */       case 42:
/*      */       case 50:
/*      */       case 51:
/*      */       case 53:
/*      */       case 55:
/*      */       case 59:
/*      */       case 63:
/*      */       case 64:
/*      */       case 65:
/*      */       case 69:
/*      */       case 71:
/*      */       case 72:
/*      */       case 73:
/*      */       case 74:
/*      */       case 75:
/*      */       case 76:
/*      */       case 77:
/*      */       case 80:
/* 3277 */         break;
/*      */       case 31:
/*      */       case 32:
/*      */       case 33:
/*      */       case 34:
/*      */       case 35:
/*      */       case 36:
/*      */       case 37:
/*      */       case 38:
/*      */       case 39:
/*      */       case 41:
/*      */       case 43:
/*      */       case 44:
/*      */       case 45:
/*      */       case 46:
/*      */       case 47:
/*      */       case 48:
/*      */       case 49:
/*      */       case 52:
/*      */       case 54:
/*      */       case 56:
/*      */       case 57:
/*      */       case 58:
/*      */       case 61:
/*      */       case 62:
/*      */       case 66:
/*      */       case 67:
/*      */       case 68:
/*      */       case 70:
/*      */       case 78:
/*      */       case 79:
/*      */       default:
/* 3281 */         throw new NoViableAltException(LT(1), getFilename());
/*      */       }
/*      */ 
/* 3286 */       switch (LA(1))
/*      */       {
/*      */       case 71:
/* 3289 */         GrammarAST tmp81_AST = null;
/* 3290 */         tmp81_AST = (GrammarAST)this.astFactory.create(LT(1));
/* 3291 */         this.astFactory.makeASTRoot(currentAST, tmp81_AST);
/* 3292 */         match(71);
/* 3293 */         break;
/*      */       case 59:
/* 3297 */         GrammarAST tmp82_AST = null;
/* 3298 */         tmp82_AST = (GrammarAST)this.astFactory.create(LT(1));
/* 3299 */         this.astFactory.makeASTRoot(currentAST, tmp82_AST);
/* 3300 */         match(59);
/* 3301 */         break;
/*      */       case 30:
/*      */       case 40:
/*      */       case 42:
/*      */       case 50:
/*      */       case 51:
/*      */       case 53:
/*      */       case 55:
/*      */       case 63:
/*      */       case 64:
/*      */       case 65:
/*      */       case 69:
/*      */       case 72:
/*      */       case 73:
/*      */       case 74:
/*      */       case 75:
/*      */       case 76:
/*      */       case 77:
/*      */       case 80:
/* 3322 */         break;
/*      */       case 31:
/*      */       case 32:
/*      */       case 33:
/*      */       case 34:
/*      */       case 35:
/*      */       case 36:
/*      */       case 37:
/*      */       case 38:
/*      */       case 39:
/*      */       case 41:
/*      */       case 43:
/*      */       case 44:
/*      */       case 45:
/*      */       case 46:
/*      */       case 47:
/*      */       case 48:
/*      */       case 49:
/*      */       case 52:
/*      */       case 54:
/*      */       case 56:
/*      */       case 57:
/*      */       case 58:
/*      */       case 60:
/*      */       case 61:
/*      */       case 62:
/*      */       case 66:
/*      */       case 67:
/*      */       case 68:
/*      */       case 70:
/*      */       case 78:
/*      */       case 79:
/*      */       default:
/* 3326 */         throw new NoViableAltException(LT(1), getFilename());
/*      */       }
/*      */ 
/* 3330 */       ruleref_AST = (GrammarAST)currentAST.root;
/*      */     }
/*      */     catch (RecognitionException ex) {
/* 3333 */       reportError(ex);
/* 3334 */       recover(ex, _tokenSet_33);
/*      */     }
/* 3336 */     this.returnAST = ruleref_AST;
/*      */   }
/*      */ 
/*      */   public final void notSet() throws RecognitionException, TokenStreamException
/*      */   {
/* 3341 */     this.returnAST = null;
/* 3342 */     ASTPair currentAST = new ASTPair();
/* 3343 */     GrammarAST notSet_AST = null;
/* 3344 */     Token n = null;
/* 3345 */     GrammarAST n_AST = null;
/*      */ 
/* 3347 */     int line = LT(1).getLine();
/* 3348 */     int col = LT(1).getColumn();
/* 3349 */     GrammarAST subrule = null;
/*      */     try
/*      */     {
/* 3353 */       n = LT(1);
/* 3354 */       n_AST = (GrammarAST)this.astFactory.create(n);
/* 3355 */       this.astFactory.makeASTRoot(currentAST, n_AST);
/* 3356 */       match(74);
/*      */ 
/* 3358 */       switch (LA(1))
/*      */       {
/*      */       case 50:
/*      */       case 51:
/*      */       case 55:
/* 3363 */         notTerminal();
/* 3364 */         this.astFactory.addASTChild(currentAST, this.returnAST);
/* 3365 */         break;
/*      */       case 63:
/* 3369 */         block();
/* 3370 */         this.astFactory.addASTChild(currentAST, this.returnAST);
/* 3371 */         break;
/*      */       default:
/* 3375 */         throw new NoViableAltException(LT(1), getFilename());
/*      */       }
/*      */ 
/* 3379 */       notSet_AST = (GrammarAST)currentAST.root;
/* 3380 */       notSet_AST.setLine(line); notSet_AST.setColumn(col);
/* 3381 */       notSet_AST = (GrammarAST)currentAST.root;
/*      */     }
/*      */     catch (RecognitionException ex) {
/* 3384 */       reportError(ex);
/* 3385 */       recover(ex, _tokenSet_35);
/*      */     }
/* 3387 */     this.returnAST = notSet_AST;
/*      */   }
/*      */ 
/*      */   public final void notTerminal() throws RecognitionException, TokenStreamException
/*      */   {
/* 3392 */     this.returnAST = null;
/* 3393 */     ASTPair currentAST = new ASTPair();
/* 3394 */     GrammarAST notTerminal_AST = null;
/* 3395 */     Token cl = null;
/* 3396 */     GrammarAST cl_AST = null;
/* 3397 */     Token tr = null;
/* 3398 */     GrammarAST tr_AST = null;
/*      */     try
/*      */     {
/* 3401 */       switch (LA(1))
/*      */       {
/*      */       case 51:
/* 3404 */         cl = LT(1);
/* 3405 */         cl_AST = (GrammarAST)this.astFactory.create(cl);
/* 3406 */         this.astFactory.addASTChild(currentAST, cl_AST);
/* 3407 */         match(51);
/* 3408 */         notTerminal_AST = (GrammarAST)currentAST.root;
/* 3409 */         break;
/*      */       case 55:
/* 3413 */         tr = LT(1);
/* 3414 */         tr_AST = (GrammarAST)this.astFactory.create(tr);
/* 3415 */         this.astFactory.addASTChild(currentAST, tr_AST);
/* 3416 */         match(55);
/* 3417 */         notTerminal_AST = (GrammarAST)currentAST.root;
/* 3418 */         break;
/*      */       case 50:
/* 3422 */         GrammarAST tmp83_AST = null;
/* 3423 */         tmp83_AST = (GrammarAST)this.astFactory.create(LT(1));
/* 3424 */         this.astFactory.addASTChild(currentAST, tmp83_AST);
/* 3425 */         match(50);
/* 3426 */         notTerminal_AST = (GrammarAST)currentAST.root;
/* 3427 */         break;
/*      */       default:
/* 3431 */         throw new NoViableAltException(LT(1), getFilename());
/*      */       }
/*      */     }
/*      */     catch (RecognitionException ex)
/*      */     {
/* 3436 */       reportError(ex);
/* 3437 */       recover(ex, _tokenSet_35);
/*      */     }
/* 3439 */     this.returnAST = notTerminal_AST;
/*      */   }
/*      */ 
/*      */   public final void treeRoot() throws RecognitionException, TokenStreamException
/*      */   {
/* 3444 */     this.returnAST = null;
/* 3445 */     ASTPair currentAST = new ASTPair();
/* 3446 */     GrammarAST treeRoot_AST = null;
/*      */     try
/*      */     {
/* 3449 */       this.atTreeRoot = true;
/*      */ 
/* 3451 */       if (((LA(1) == 55) || (LA(1) == 73)) && ((LA(2) == 49) || (LA(2) == 68))) {
/* 3452 */         id();
/* 3453 */         this.astFactory.addASTChild(currentAST, this.returnAST);
/*      */ 
/* 3455 */         switch (LA(1))
/*      */         {
/*      */         case 49:
/* 3458 */           GrammarAST tmp84_AST = null;
/* 3459 */           tmp84_AST = (GrammarAST)this.astFactory.create(LT(1));
/* 3460 */           this.astFactory.makeASTRoot(currentAST, tmp84_AST);
/* 3461 */           match(49);
/* 3462 */           break;
/*      */         case 68:
/* 3466 */           GrammarAST tmp85_AST = null;
/* 3467 */           tmp85_AST = (GrammarAST)this.astFactory.create(LT(1));
/* 3468 */           this.astFactory.makeASTRoot(currentAST, tmp85_AST);
/* 3469 */           match(68);
/* 3470 */           break;
/*      */         default:
/* 3474 */           throw new NoViableAltException(LT(1), getFilename());
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/* 3479 */       switch (LA(1))
/*      */       {
/*      */       case 50:
/*      */       case 51:
/*      */       case 55:
/*      */       case 72:
/*      */       case 73:
/*      */       case 74:
/* 3487 */         atom();
/* 3488 */         this.astFactory.addASTChild(currentAST, this.returnAST);
/* 3489 */         break;
/*      */       case 63:
/* 3493 */         block();
/* 3494 */         this.astFactory.addASTChild(currentAST, this.returnAST);
/* 3495 */         break;
/*      */       case 52:
/*      */       case 53:
/*      */       case 54:
/*      */       case 56:
/*      */       case 57:
/*      */       case 58:
/*      */       case 59:
/*      */       case 60:
/*      */       case 61:
/*      */       case 62:
/*      */       case 64:
/*      */       case 65:
/*      */       case 66:
/*      */       case 67:
/*      */       case 68:
/*      */       case 69:
/*      */       case 70:
/*      */       case 71:
/*      */       default:
/* 3499 */         throw new NoViableAltException(LT(1), getFilename());
/*      */ 
/* 3504 */         if ((_tokenSet_27.member(LA(1))) && (_tokenSet_36.member(LA(2)))) {
/* 3505 */           atom();
/* 3506 */           this.astFactory.addASTChild(currentAST, this.returnAST);
/*      */         }
/* 3508 */         else if (LA(1) == 63) {
/* 3509 */           block();
/* 3510 */           this.astFactory.addASTChild(currentAST, this.returnAST);
/*      */         }
/*      */         else {
/* 3513 */           throw new NoViableAltException(LT(1), getFilename());
/*      */         }
/*      */         break;
/*      */       }
/* 3517 */       this.atTreeRoot = false;
/* 3518 */       treeRoot_AST = (GrammarAST)currentAST.root;
/*      */     }
/*      */     catch (RecognitionException ex) {
/* 3521 */       reportError(ex);
/* 3522 */       recover(ex, _tokenSet_22);
/*      */     }
/* 3524 */     this.returnAST = treeRoot_AST;
/*      */   }
/*      */ 
/*      */   public final void elementOptions(GrammarAST terminalAST)
/*      */     throws RecognitionException, TokenStreamException
/*      */   {
/* 3531 */     this.returnAST = null;
/* 3532 */     ASTPair currentAST = new ASTPair();
/* 3533 */     GrammarAST elementOptions_AST = null;
/*      */     try
/*      */     {
/* 3536 */       if ((LA(1) == 78) && ((LA(2) == 55) || (LA(2) == 73)) && ((LA(3) == 72) || (LA(3) == 79))) {
/* 3537 */         GrammarAST tmp86_AST = null;
/* 3538 */         tmp86_AST = (GrammarAST)this.astFactory.create(LT(1));
/* 3539 */         this.astFactory.makeASTRoot(currentAST, tmp86_AST);
/* 3540 */         match(78);
/* 3541 */         defaultNodeOption(terminalAST);
/* 3542 */         this.astFactory.addASTChild(currentAST, this.returnAST);
/* 3543 */         match(79);
/* 3544 */         elementOptions_AST = (GrammarAST)currentAST.root;
/*      */       }
/* 3546 */       else if ((LA(1) == 78) && ((LA(2) == 55) || (LA(2) == 73)) && (LA(3) == 49)) {
/* 3547 */         GrammarAST tmp88_AST = null;
/* 3548 */         tmp88_AST = (GrammarAST)this.astFactory.create(LT(1));
/* 3549 */         this.astFactory.makeASTRoot(currentAST, tmp88_AST);
/* 3550 */         match(78);
/* 3551 */         elementOption(terminalAST);
/* 3552 */         this.astFactory.addASTChild(currentAST, this.returnAST);
/*      */ 
/* 3556 */         while (LA(1) == 42) {
/* 3557 */           match(42);
/* 3558 */           elementOption(terminalAST);
/* 3559 */           this.astFactory.addASTChild(currentAST, this.returnAST);
/*      */         }
/*      */ 
/* 3567 */         match(79);
/* 3568 */         elementOptions_AST = (GrammarAST)currentAST.root;
/*      */       }
/*      */       else {
/* 3571 */         throw new NoViableAltException(LT(1), getFilename());
/*      */       }
/*      */     }
/*      */     catch (RecognitionException ex)
/*      */     {
/* 3576 */       reportError(ex);
/* 3577 */       recover(ex, _tokenSet_37);
/*      */     }
/* 3579 */     this.returnAST = elementOptions_AST;
/*      */   }
/*      */ 
/*      */   public final void defaultNodeOption(GrammarAST terminalAST)
/*      */     throws RecognitionException, TokenStreamException
/*      */   {
/* 3586 */     this.returnAST = null;
/* 3587 */     ASTPair currentAST = new ASTPair();
/* 3588 */     GrammarAST defaultNodeOption_AST = null;
/* 3589 */     GrammarAST i_AST = null;
/* 3590 */     GrammarAST i2_AST = null;
/*      */ 
/* 3592 */     StringBuffer buf = new StringBuffer();
/*      */     try
/*      */     {
/* 3596 */       id();
/* 3597 */       i_AST = (GrammarAST)this.returnAST;
/* 3598 */       this.astFactory.addASTChild(currentAST, this.returnAST);
/* 3599 */       buf.append(i_AST.getText());
/*      */ 
/* 3603 */       while (LA(1) == 72) {
/* 3604 */         GrammarAST tmp91_AST = null;
/* 3605 */         tmp91_AST = (GrammarAST)this.astFactory.create(LT(1));
/* 3606 */         this.astFactory.addASTChild(currentAST, tmp91_AST);
/* 3607 */         match(72);
/* 3608 */         id();
/* 3609 */         i2_AST = (GrammarAST)this.returnAST;
/* 3610 */         this.astFactory.addASTChild(currentAST, this.returnAST);
/* 3611 */         buf.append("." + i2_AST.getText());
/*      */       }
/*      */ 
/* 3619 */       terminalAST.setTerminalOption(this.grammar, "node", buf.toString());
/* 3620 */       defaultNodeOption_AST = (GrammarAST)currentAST.root;
/*      */     }
/*      */     catch (RecognitionException ex) {
/* 3623 */       reportError(ex);
/* 3624 */       recover(ex, _tokenSet_38);
/*      */     }
/* 3626 */     this.returnAST = defaultNodeOption_AST;
/*      */   }
/*      */ 
/*      */   public final void elementOption(GrammarAST terminalAST)
/*      */     throws RecognitionException, TokenStreamException
/*      */   {
/* 3633 */     this.returnAST = null;
/* 3634 */     ASTPair currentAST = new ASTPair();
/* 3635 */     GrammarAST elementOption_AST = null;
/* 3636 */     GrammarAST a_AST = null;
/* 3637 */     GrammarAST b_AST = null;
/* 3638 */     Token s = null;
/* 3639 */     GrammarAST s_AST = null;
/*      */     try
/*      */     {
/* 3642 */       id();
/* 3643 */       a_AST = (GrammarAST)this.returnAST;
/* 3644 */       this.astFactory.addASTChild(currentAST, this.returnAST);
/* 3645 */       GrammarAST tmp92_AST = null;
/* 3646 */       tmp92_AST = (GrammarAST)this.astFactory.create(LT(1));
/* 3647 */       this.astFactory.makeASTRoot(currentAST, tmp92_AST);
/* 3648 */       match(49);
/*      */ 
/* 3650 */       switch (LA(1))
/*      */       {
/*      */       case 55:
/*      */       case 73:
/* 3654 */         id();
/* 3655 */         b_AST = (GrammarAST)this.returnAST;
/* 3656 */         this.astFactory.addASTChild(currentAST, this.returnAST);
/* 3657 */         break;
/*      */       case 50:
/* 3661 */         s = LT(1);
/* 3662 */         s_AST = (GrammarAST)this.astFactory.create(s);
/* 3663 */         this.astFactory.addASTChild(currentAST, s_AST);
/* 3664 */         match(50);
/* 3665 */         break;
/*      */       default:
/* 3669 */         throw new NoViableAltException(LT(1), getFilename());
/*      */       }
/*      */ 
/* 3674 */       Object v = b_AST != null ? b_AST.getText() : s_AST.getText();
/* 3675 */       terminalAST.setTerminalOption(this.grammar, a_AST.getText(), v);
/*      */ 
/* 3677 */       elementOption_AST = (GrammarAST)currentAST.root;
/*      */     }
/*      */     catch (RecognitionException ex) {
/* 3680 */       reportError(ex);
/* 3681 */       recover(ex, _tokenSet_39);
/*      */     }
/* 3683 */     this.returnAST = elementOption_AST;
/*      */   }
/*      */ 
/*      */   public final void rewrite_alternative() throws RecognitionException, TokenStreamException
/*      */   {
/* 3688 */     this.returnAST = null;
/* 3689 */     ASTPair currentAST = new ASTPair();
/* 3690 */     GrammarAST rewrite_alternative_AST = null;
/*      */ 
/* 3692 */     GrammarAST eoa = (GrammarAST)this.astFactory.create(20, "<end-of-alt>");
/* 3693 */     GrammarAST altRoot = (GrammarAST)this.astFactory.create(17, "ALT");
/* 3694 */     altRoot.setLine(LT(1).getLine());
/* 3695 */     altRoot.setColumn(LT(1).getColumn());
/*      */     try
/*      */     {
/* 3699 */       if ((_tokenSet_40.member(LA(1))) && (_tokenSet_41.member(LA(2))) && (_tokenSet_42.member(LA(3))) && (this.grammar.buildTemplate())) {
/* 3700 */         rewrite_template();
/* 3701 */         this.astFactory.addASTChild(currentAST, this.returnAST);
/* 3702 */         rewrite_alternative_AST = (GrammarAST)currentAST.root;
/*      */       }
/* 3704 */       else if ((_tokenSet_43.member(LA(1))) && (_tokenSet_44.member(LA(2))) && (_tokenSet_45.member(LA(3))) && (this.grammar.buildAST()))
/*      */       {
/* 3706 */         int _cnt135 = 0;
/*      */         while (true)
/*      */         {
/* 3709 */           if (_tokenSet_43.member(LA(1))) {
/* 3710 */             rewrite_element();
/* 3711 */             this.astFactory.addASTChild(currentAST, this.returnAST);
/*      */           }
/*      */           else {
/* 3714 */             if (_cnt135 >= 1) break; throw new NoViableAltException(LT(1), getFilename());
/*      */           }
/*      */ 
/* 3717 */           _cnt135++;
/*      */         }
/*      */ 
/* 3720 */         rewrite_alternative_AST = (GrammarAST)currentAST.root;
/*      */ 
/* 3722 */         if (rewrite_alternative_AST == null) {
/* 3723 */           rewrite_alternative_AST = (GrammarAST)this.astFactory.make(new ASTArray(3).add(altRoot).add((GrammarAST)this.astFactory.create(16, "epsilon")).add(eoa));
/*      */         }
/*      */         else {
/* 3726 */           rewrite_alternative_AST = (GrammarAST)this.astFactory.make(new ASTArray(3).add(altRoot).add(rewrite_alternative_AST).add(eoa));
/*      */         }
/*      */ 
/* 3729 */         currentAST.root = rewrite_alternative_AST;
/* 3730 */         currentAST.child = ((rewrite_alternative_AST != null) && (rewrite_alternative_AST.getFirstChild() != null) ? rewrite_alternative_AST.getFirstChild() : rewrite_alternative_AST);
/*      */ 
/* 3732 */         currentAST.advanceChildToEnd();
/* 3733 */         rewrite_alternative_AST = (GrammarAST)currentAST.root;
/*      */       }
/* 3735 */       else if (_tokenSet_23.member(LA(1))) {
/* 3736 */         rewrite_alternative_AST = (GrammarAST)currentAST.root;
/* 3737 */         rewrite_alternative_AST = (GrammarAST)this.astFactory.make(new ASTArray(3).add(altRoot).add((GrammarAST)this.astFactory.create(16, "epsilon")).add(eoa));
/* 3738 */         currentAST.root = rewrite_alternative_AST;
/* 3739 */         currentAST.child = ((rewrite_alternative_AST != null) && (rewrite_alternative_AST.getFirstChild() != null) ? rewrite_alternative_AST.getFirstChild() : rewrite_alternative_AST);
/*      */ 
/* 3741 */         currentAST.advanceChildToEnd();
/* 3742 */         rewrite_alternative_AST = (GrammarAST)currentAST.root;
/*      */       }
/* 3744 */       else if ((LA(1) == 81) && (this.grammar.buildAST())) {
/* 3745 */         GrammarAST tmp93_AST = null;
/* 3746 */         tmp93_AST = (GrammarAST)this.astFactory.create(LT(1));
/* 3747 */         this.astFactory.addASTChild(currentAST, tmp93_AST);
/* 3748 */         match(81);
/* 3749 */         rewrite_alternative_AST = (GrammarAST)currentAST.root;
/*      */       }
/*      */       else {
/* 3752 */         throw new NoViableAltException(LT(1), getFilename());
/*      */       }
/*      */     }
/*      */     catch (RecognitionException ex)
/*      */     {
/* 3757 */       reportError(ex);
/* 3758 */       recover(ex, _tokenSet_23);
/*      */     }
/* 3760 */     this.returnAST = rewrite_alternative_AST;
/*      */   }
/*      */ 
/*      */   public final void rewrite_block() throws RecognitionException, TokenStreamException
/*      */   {
/* 3765 */     this.returnAST = null;
/* 3766 */     ASTPair currentAST = new ASTPair();
/* 3767 */     GrammarAST rewrite_block_AST = null;
/* 3768 */     Token lp = null;
/* 3769 */     GrammarAST lp_AST = null;
/*      */     try
/*      */     {
/* 3772 */       lp = LT(1);
/* 3773 */       lp_AST = (GrammarAST)this.astFactory.create(lp);
/* 3774 */       this.astFactory.makeASTRoot(currentAST, lp_AST);
/* 3775 */       match(63);
/* 3776 */       lp_AST.setType(9); lp_AST.setText("BLOCK");
/* 3777 */       rewrite_alternative();
/* 3778 */       this.astFactory.addASTChild(currentAST, this.returnAST);
/* 3779 */       match(65);
/* 3780 */       rewrite_block_AST = (GrammarAST)currentAST.root;
/*      */ 
/* 3782 */       GrammarAST eob = (GrammarAST)this.astFactory.create(19, "<end-of-block>");
/* 3783 */       eob.setLine(lp.getLine());
/* 3784 */       eob.setColumn(lp.getColumn());
/* 3785 */       rewrite_block_AST.addChild(eob);
/*      */ 
/* 3787 */       rewrite_block_AST = (GrammarAST)currentAST.root;
/*      */     }
/*      */     catch (RecognitionException ex) {
/* 3790 */       reportError(ex);
/* 3791 */       recover(ex, _tokenSet_46);
/*      */     }
/* 3793 */     this.returnAST = rewrite_block_AST;
/*      */   }
/*      */ 
/*      */   public final void rewrite_template()
/*      */     throws RecognitionException, TokenStreamException
/*      */   {
/* 3809 */     this.returnAST = null;
/* 3810 */     ASTPair currentAST = new ASTPair();
/* 3811 */     GrammarAST rewrite_template_AST = null;
/* 3812 */     Token st = null;
/*      */     try
/*      */     {
/* 3815 */       switch (LA(1))
/*      */       {
/*      */       case 63:
/* 3818 */         rewrite_indirect_template_head();
/* 3819 */         this.astFactory.addASTChild(currentAST, this.returnAST);
/* 3820 */         rewrite_template_AST = (GrammarAST)currentAST.root;
/* 3821 */         break;
/*      */       case 40:
/* 3825 */         GrammarAST tmp95_AST = null;
/* 3826 */         tmp95_AST = (GrammarAST)this.astFactory.create(LT(1));
/* 3827 */         this.astFactory.addASTChild(currentAST, tmp95_AST);
/* 3828 */         match(40);
/* 3829 */         rewrite_template_AST = (GrammarAST)currentAST.root;
/* 3830 */         break;
/*      */       default:
/* 3833 */         if (((LA(1) == 55) || (LA(1) == 73)) && (LA(2) == 63) && ((LA(3) == 55) || (LA(3) == 65) || (LA(3) == 73)) && (LT(1).getText().equals("template"))) {
/* 3834 */           rewrite_template_head();
/* 3835 */           this.astFactory.addASTChild(currentAST, this.returnAST);
/* 3836 */           st = LT(1);
/*      */ 
/* 3838 */           switch (LA(1))
/*      */           {
/*      */           case 83:
/* 3841 */             match(83);
/* 3842 */             break;
/*      */           case 84:
/* 3846 */             match(84);
/* 3847 */             break;
/*      */           default:
/* 3851 */             throw new NoViableAltException(LT(1), getFilename());
/*      */           }
/*      */ 
/* 3855 */           rewrite_template_AST = (GrammarAST)currentAST.root;
/* 3856 */           rewrite_template_AST.addChild((GrammarAST)this.astFactory.create(st));
/* 3857 */           rewrite_template_AST = (GrammarAST)currentAST.root;
/*      */         }
/* 3859 */         else if (((LA(1) == 55) || (LA(1) == 73)) && (LA(2) == 63) && ((LA(3) == 55) || (LA(3) == 65) || (LA(3) == 73))) {
/* 3860 */           rewrite_template_head();
/* 3861 */           this.astFactory.addASTChild(currentAST, this.returnAST);
/* 3862 */           rewrite_template_AST = (GrammarAST)currentAST.root;
/*      */         }
/*      */         else {
/* 3865 */           throw new NoViableAltException(LT(1), getFilename());
/*      */         }
/*      */         break;
/*      */       }
/*      */     } catch (RecognitionException ex) {
/* 3870 */       reportError(ex);
/* 3871 */       recover(ex, _tokenSet_23);
/*      */     }
/* 3873 */     this.returnAST = rewrite_template_AST;
/*      */   }
/*      */ 
/*      */   public final void rewrite_element() throws RecognitionException, TokenStreamException
/*      */   {
/* 3878 */     this.returnAST = null;
/* 3879 */     ASTPair currentAST = new ASTPair();
/* 3880 */     GrammarAST rewrite_element_AST = null;
/* 3881 */     GrammarAST t_AST = null;
/* 3882 */     GrammarAST tr_AST = null;
/*      */ 
/* 3884 */     GrammarAST subrule = null;
/*      */     try
/*      */     {
/* 3888 */       switch (LA(1))
/*      */       {
/*      */       case 40:
/*      */       case 50:
/*      */       case 51:
/*      */       case 55:
/*      */       case 73:
/*      */       case 82:
/* 3896 */         rewrite_atom();
/* 3897 */         t_AST = (GrammarAST)this.returnAST;
/* 3898 */         this.astFactory.addASTChild(currentAST, this.returnAST);
/*      */ 
/* 3900 */         switch (LA(1))
/*      */         {
/*      */         case 53:
/*      */         case 76:
/*      */         case 77:
/* 3905 */           subrule = ebnfSuffix(t_AST, true);
/* 3906 */           this.astFactory.addASTChild(currentAST, this.returnAST);
/* 3907 */           rewrite_element_AST = (GrammarAST)currentAST.root;
/* 3908 */           rewrite_element_AST = subrule;
/* 3909 */           currentAST.root = rewrite_element_AST;
/* 3910 */           currentAST.child = ((rewrite_element_AST != null) && (rewrite_element_AST.getFirstChild() != null) ? rewrite_element_AST.getFirstChild() : rewrite_element_AST);
/*      */ 
/* 3912 */           currentAST.advanceChildToEnd();
/* 3913 */           break;
/*      */         case 40:
/*      */         case 42:
/*      */         case 50:
/*      */         case 51:
/*      */         case 55:
/*      */         case 63:
/*      */         case 64:
/*      */         case 65:
/*      */         case 73:
/*      */         case 75:
/*      */         case 80:
/*      */         case 82:
/* 3928 */           break;
/*      */         case 41:
/*      */         case 43:
/*      */         case 44:
/*      */         case 45:
/*      */         case 46:
/*      */         case 47:
/*      */         case 48:
/*      */         case 49:
/*      */         case 52:
/*      */         case 54:
/*      */         case 56:
/*      */         case 57:
/*      */         case 58:
/*      */         case 59:
/*      */         case 60:
/*      */         case 61:
/*      */         case 62:
/*      */         case 66:
/*      */         case 67:
/*      */         case 68:
/*      */         case 69:
/*      */         case 70:
/*      */         case 71:
/*      */         case 72:
/*      */         case 74:
/*      */         case 78:
/*      */         case 79:
/*      */         case 81:
/*      */         default:
/* 3932 */           throw new NoViableAltException(LT(1), getFilename());
/*      */         }
/*      */ 
/* 3936 */         rewrite_element_AST = (GrammarAST)currentAST.root;
/* 3937 */         break;
/*      */       case 63:
/* 3941 */         rewrite_ebnf();
/* 3942 */         this.astFactory.addASTChild(currentAST, this.returnAST);
/* 3943 */         rewrite_element_AST = (GrammarAST)currentAST.root;
/* 3944 */         break;
/*      */       case 75:
/* 3948 */         rewrite_tree();
/* 3949 */         tr_AST = (GrammarAST)this.returnAST;
/* 3950 */         this.astFactory.addASTChild(currentAST, this.returnAST);
/*      */ 
/* 3952 */         switch (LA(1))
/*      */         {
/*      */         case 53:
/*      */         case 76:
/*      */         case 77:
/* 3957 */           subrule = ebnfSuffix(tr_AST, true);
/* 3958 */           this.astFactory.addASTChild(currentAST, this.returnAST);
/* 3959 */           rewrite_element_AST = (GrammarAST)currentAST.root;
/* 3960 */           rewrite_element_AST = subrule;
/* 3961 */           currentAST.root = rewrite_element_AST;
/* 3962 */           currentAST.child = ((rewrite_element_AST != null) && (rewrite_element_AST.getFirstChild() != null) ? rewrite_element_AST.getFirstChild() : rewrite_element_AST);
/*      */ 
/* 3964 */           currentAST.advanceChildToEnd();
/* 3965 */           break;
/*      */         case 40:
/*      */         case 42:
/*      */         case 50:
/*      */         case 51:
/*      */         case 55:
/*      */         case 63:
/*      */         case 64:
/*      */         case 65:
/*      */         case 73:
/*      */         case 75:
/*      */         case 80:
/*      */         case 82:
/* 3980 */           break;
/*      */         case 41:
/*      */         case 43:
/*      */         case 44:
/*      */         case 45:
/*      */         case 46:
/*      */         case 47:
/*      */         case 48:
/*      */         case 49:
/*      */         case 52:
/*      */         case 54:
/*      */         case 56:
/*      */         case 57:
/*      */         case 58:
/*      */         case 59:
/*      */         case 60:
/*      */         case 61:
/*      */         case 62:
/*      */         case 66:
/*      */         case 67:
/*      */         case 68:
/*      */         case 69:
/*      */         case 70:
/*      */         case 71:
/*      */         case 72:
/*      */         case 74:
/*      */         case 78:
/*      */         case 79:
/*      */         case 81:
/*      */         default:
/* 3984 */           throw new NoViableAltException(LT(1), getFilename());
/*      */         }
/*      */ 
/* 3988 */         rewrite_element_AST = (GrammarAST)currentAST.root;
/* 3989 */         break;
/*      */       default:
/* 3993 */         throw new NoViableAltException(LT(1), getFilename());
/*      */       }
/*      */     }
/*      */     catch (RecognitionException ex)
/*      */     {
/* 3998 */       reportError(ex);
/* 3999 */       recover(ex, _tokenSet_47);
/*      */     }
/* 4001 */     this.returnAST = rewrite_element_AST;
/*      */   }
/*      */ 
/*      */   public final void rewrite_atom() throws RecognitionException, TokenStreamException
/*      */   {
/* 4006 */     this.returnAST = null;
/* 4007 */     ASTPair currentAST = new ASTPair();
/* 4008 */     GrammarAST rewrite_atom_AST = null;
/* 4009 */     Token tr = null;
/* 4010 */     GrammarAST tr_AST = null;
/* 4011 */     Token rr = null;
/* 4012 */     GrammarAST rr_AST = null;
/* 4013 */     Token cl = null;
/* 4014 */     GrammarAST cl_AST = null;
/* 4015 */     Token sl = null;
/* 4016 */     GrammarAST sl_AST = null;
/* 4017 */     Token d = null;
/* 4018 */     GrammarAST d_AST = null;
/* 4019 */     GrammarAST i_AST = null;
/*      */ 
/* 4021 */     GrammarAST subrule = null;
/*      */     try
/*      */     {
/* 4025 */       switch (LA(1))
/*      */       {
/*      */       case 55:
/* 4028 */         tr = LT(1);
/* 4029 */         tr_AST = (GrammarAST)this.astFactory.create(tr);
/* 4030 */         this.astFactory.makeASTRoot(currentAST, tr_AST);
/* 4031 */         match(55);
/*      */ 
/* 4033 */         switch (LA(1))
/*      */         {
/*      */         case 78:
/* 4036 */           elementOptions(tr_AST);
/* 4037 */           break;
/*      */         case 40:
/*      */         case 42:
/*      */         case 50:
/*      */         case 51:
/*      */         case 53:
/*      */         case 55:
/*      */         case 60:
/*      */         case 63:
/*      */         case 64:
/*      */         case 65:
/*      */         case 73:
/*      */         case 75:
/*      */         case 76:
/*      */         case 77:
/*      */         case 80:
/*      */         case 82:
/* 4056 */           break;
/*      */         case 41:
/*      */         case 43:
/*      */         case 44:
/*      */         case 45:
/*      */         case 46:
/*      */         case 47:
/*      */         case 48:
/*      */         case 49:
/*      */         case 52:
/*      */         case 54:
/*      */         case 56:
/*      */         case 57:
/*      */         case 58:
/*      */         case 59:
/*      */         case 61:
/*      */         case 62:
/*      */         case 66:
/*      */         case 67:
/*      */         case 68:
/*      */         case 69:
/*      */         case 70:
/*      */         case 71:
/*      */         case 72:
/*      */         case 74:
/*      */         case 79:
/*      */         case 81:
/*      */         default:
/* 4060 */           throw new NoViableAltException(LT(1), getFilename());
/*      */         }
/*      */ 
/* 4065 */         switch (LA(1))
/*      */         {
/*      */         case 60:
/* 4068 */           GrammarAST tmp98_AST = null;
/* 4069 */           tmp98_AST = (GrammarAST)this.astFactory.create(LT(1));
/* 4070 */           this.astFactory.addASTChild(currentAST, tmp98_AST);
/* 4071 */           match(60);
/* 4072 */           break;
/*      */         case 40:
/*      */         case 42:
/*      */         case 50:
/*      */         case 51:
/*      */         case 53:
/*      */         case 55:
/*      */         case 63:
/*      */         case 64:
/*      */         case 65:
/*      */         case 73:
/*      */         case 75:
/*      */         case 76:
/*      */         case 77:
/*      */         case 80:
/*      */         case 82:
/* 4090 */           break;
/*      */         case 41:
/*      */         case 43:
/*      */         case 44:
/*      */         case 45:
/*      */         case 46:
/*      */         case 47:
/*      */         case 48:
/*      */         case 49:
/*      */         case 52:
/*      */         case 54:
/*      */         case 56:
/*      */         case 57:
/*      */         case 58:
/*      */         case 59:
/*      */         case 61:
/*      */         case 62:
/*      */         case 66:
/*      */         case 67:
/*      */         case 68:
/*      */         case 69:
/*      */         case 70:
/*      */         case 71:
/*      */         case 72:
/*      */         case 74:
/*      */         case 78:
/*      */         case 79:
/*      */         case 81:
/*      */         default:
/* 4094 */           throw new NoViableAltException(LT(1), getFilename());
/*      */         }
/*      */ 
/* 4098 */         rewrite_atom_AST = (GrammarAST)currentAST.root;
/* 4099 */         break;
/*      */       case 73:
/* 4103 */         rr = LT(1);
/* 4104 */         rr_AST = (GrammarAST)this.astFactory.create(rr);
/* 4105 */         this.astFactory.addASTChild(currentAST, rr_AST);
/* 4106 */         match(73);
/* 4107 */         rewrite_atom_AST = (GrammarAST)currentAST.root;
/* 4108 */         break;
/*      */       case 51:
/* 4112 */         cl = LT(1);
/* 4113 */         cl_AST = (GrammarAST)this.astFactory.create(cl);
/* 4114 */         this.astFactory.makeASTRoot(currentAST, cl_AST);
/* 4115 */         match(51);
/*      */ 
/* 4117 */         switch (LA(1))
/*      */         {
/*      */         case 78:
/* 4120 */           elementOptions(cl_AST);
/* 4121 */           break;
/*      */         case 40:
/*      */         case 42:
/*      */         case 50:
/*      */         case 51:
/*      */         case 53:
/*      */         case 55:
/*      */         case 63:
/*      */         case 64:
/*      */         case 65:
/*      */         case 73:
/*      */         case 75:
/*      */         case 76:
/*      */         case 77:
/*      */         case 80:
/*      */         case 82:
/* 4139 */           break;
/*      */         case 41:
/*      */         case 43:
/*      */         case 44:
/*      */         case 45:
/*      */         case 46:
/*      */         case 47:
/*      */         case 48:
/*      */         case 49:
/*      */         case 52:
/*      */         case 54:
/*      */         case 56:
/*      */         case 57:
/*      */         case 58:
/*      */         case 59:
/*      */         case 60:
/*      */         case 61:
/*      */         case 62:
/*      */         case 66:
/*      */         case 67:
/*      */         case 68:
/*      */         case 69:
/*      */         case 70:
/*      */         case 71:
/*      */         case 72:
/*      */         case 74:
/*      */         case 79:
/*      */         case 81:
/*      */         default:
/* 4143 */           throw new NoViableAltException(LT(1), getFilename());
/*      */         }
/*      */ 
/* 4147 */         rewrite_atom_AST = (GrammarAST)currentAST.root;
/* 4148 */         break;
/*      */       case 50:
/* 4152 */         sl = LT(1);
/* 4153 */         sl_AST = (GrammarAST)this.astFactory.create(sl);
/* 4154 */         this.astFactory.makeASTRoot(currentAST, sl_AST);
/* 4155 */         match(50);
/*      */ 
/* 4157 */         switch (LA(1))
/*      */         {
/*      */         case 78:
/* 4160 */           elementOptions(sl_AST);
/* 4161 */           break;
/*      */         case 40:
/*      */         case 42:
/*      */         case 50:
/*      */         case 51:
/*      */         case 53:
/*      */         case 55:
/*      */         case 63:
/*      */         case 64:
/*      */         case 65:
/*      */         case 73:
/*      */         case 75:
/*      */         case 76:
/*      */         case 77:
/*      */         case 80:
/*      */         case 82:
/* 4179 */           break;
/*      */         case 41:
/*      */         case 43:
/*      */         case 44:
/*      */         case 45:
/*      */         case 46:
/*      */         case 47:
/*      */         case 48:
/*      */         case 49:
/*      */         case 52:
/*      */         case 54:
/*      */         case 56:
/*      */         case 57:
/*      */         case 58:
/*      */         case 59:
/*      */         case 60:
/*      */         case 61:
/*      */         case 62:
/*      */         case 66:
/*      */         case 67:
/*      */         case 68:
/*      */         case 69:
/*      */         case 70:
/*      */         case 71:
/*      */         case 72:
/*      */         case 74:
/*      */         case 79:
/*      */         case 81:
/*      */         default:
/* 4183 */           throw new NoViableAltException(LT(1), getFilename());
/*      */         }
/*      */ 
/* 4187 */         rewrite_atom_AST = (GrammarAST)currentAST.root;
/* 4188 */         break;
/*      */       case 82:
/* 4192 */         d = LT(1);
/* 4193 */         d_AST = (GrammarAST)this.astFactory.create(d);
/* 4194 */         match(82);
/* 4195 */         id();
/* 4196 */         i_AST = (GrammarAST)this.returnAST;
/* 4197 */         rewrite_atom_AST = (GrammarAST)currentAST.root;
/*      */ 
/* 4199 */         rewrite_atom_AST = (GrammarAST)this.astFactory.create(31, i_AST.getText());
/* 4200 */         rewrite_atom_AST.setLine(d_AST.getLine());
/* 4201 */         rewrite_atom_AST.setColumn(d_AST.getColumn());
/*      */ 
/* 4203 */         currentAST.root = rewrite_atom_AST;
/* 4204 */         currentAST.child = ((rewrite_atom_AST != null) && (rewrite_atom_AST.getFirstChild() != null) ? rewrite_atom_AST.getFirstChild() : rewrite_atom_AST);
/*      */ 
/* 4206 */         currentAST.advanceChildToEnd();
/* 4207 */         break;
/*      */       case 40:
/* 4211 */         GrammarAST tmp99_AST = null;
/* 4212 */         tmp99_AST = (GrammarAST)this.astFactory.create(LT(1));
/* 4213 */         this.astFactory.addASTChild(currentAST, tmp99_AST);
/* 4214 */         match(40);
/* 4215 */         rewrite_atom_AST = (GrammarAST)currentAST.root;
/* 4216 */         break;
/*      */       default:
/* 4220 */         throw new NoViableAltException(LT(1), getFilename());
/*      */       }
/*      */     }
/*      */     catch (RecognitionException ex)
/*      */     {
/* 4225 */       reportError(ex);
/* 4226 */       recover(ex, _tokenSet_48);
/*      */     }
/* 4228 */     this.returnAST = rewrite_atom_AST;
/*      */   }
/*      */ 
/*      */   public final void rewrite_ebnf() throws RecognitionException, TokenStreamException
/*      */   {
/* 4233 */     this.returnAST = null;
/* 4234 */     ASTPair currentAST = new ASTPair();
/* 4235 */     GrammarAST rewrite_ebnf_AST = null;
/* 4236 */     GrammarAST b_AST = null;
/*      */ 
/* 4238 */     int line = LT(1).getLine();
/* 4239 */     int col = LT(1).getColumn();
/*      */     try
/*      */     {
/* 4243 */       rewrite_block();
/* 4244 */       b_AST = (GrammarAST)this.returnAST;
/*      */ 
/* 4246 */       switch (LA(1))
/*      */       {
/*      */       case 76:
/* 4249 */         GrammarAST tmp100_AST = null;
/* 4250 */         tmp100_AST = (GrammarAST)this.astFactory.create(LT(1));
/* 4251 */         match(76);
/* 4252 */         rewrite_ebnf_AST = (GrammarAST)currentAST.root;
/* 4253 */         rewrite_ebnf_AST = (GrammarAST)this.astFactory.make(new ASTArray(2).add((GrammarAST)this.astFactory.create(10, "?")).add(b_AST));
/* 4254 */         currentAST.root = rewrite_ebnf_AST;
/* 4255 */         currentAST.child = ((rewrite_ebnf_AST != null) && (rewrite_ebnf_AST.getFirstChild() != null) ? rewrite_ebnf_AST.getFirstChild() : rewrite_ebnf_AST);
/*      */ 
/* 4257 */         currentAST.advanceChildToEnd();
/* 4258 */         break;
/*      */       case 53:
/* 4262 */         GrammarAST tmp101_AST = null;
/* 4263 */         tmp101_AST = (GrammarAST)this.astFactory.create(LT(1));
/* 4264 */         match(53);
/* 4265 */         rewrite_ebnf_AST = (GrammarAST)currentAST.root;
/* 4266 */         rewrite_ebnf_AST = (GrammarAST)this.astFactory.make(new ASTArray(2).add((GrammarAST)this.astFactory.create(11, "*")).add(b_AST));
/* 4267 */         currentAST.root = rewrite_ebnf_AST;
/* 4268 */         currentAST.child = ((rewrite_ebnf_AST != null) && (rewrite_ebnf_AST.getFirstChild() != null) ? rewrite_ebnf_AST.getFirstChild() : rewrite_ebnf_AST);
/*      */ 
/* 4270 */         currentAST.advanceChildToEnd();
/* 4271 */         break;
/*      */       case 77:
/* 4275 */         GrammarAST tmp102_AST = null;
/* 4276 */         tmp102_AST = (GrammarAST)this.astFactory.create(LT(1));
/* 4277 */         match(77);
/* 4278 */         rewrite_ebnf_AST = (GrammarAST)currentAST.root;
/* 4279 */         rewrite_ebnf_AST = (GrammarAST)this.astFactory.make(new ASTArray(2).add((GrammarAST)this.astFactory.create(12, "+")).add(b_AST));
/* 4280 */         currentAST.root = rewrite_ebnf_AST;
/* 4281 */         currentAST.child = ((rewrite_ebnf_AST != null) && (rewrite_ebnf_AST.getFirstChild() != null) ? rewrite_ebnf_AST.getFirstChild() : rewrite_ebnf_AST);
/*      */ 
/* 4283 */         currentAST.advanceChildToEnd();
/* 4284 */         break;
/*      */       default:
/* 4288 */         throw new NoViableAltException(LT(1), getFilename());
/*      */       }
/*      */ 
/* 4292 */       rewrite_ebnf_AST = (GrammarAST)currentAST.root;
/* 4293 */       rewrite_ebnf_AST.setLine(line); rewrite_ebnf_AST.setColumn(col);
/*      */     }
/*      */     catch (RecognitionException ex) {
/* 4296 */       reportError(ex);
/* 4297 */       recover(ex, _tokenSet_47);
/*      */     }
/* 4299 */     this.returnAST = rewrite_ebnf_AST;
/*      */   }
/*      */ 
/*      */   public final void rewrite_tree() throws RecognitionException, TokenStreamException
/*      */   {
/* 4304 */     this.returnAST = null;
/* 4305 */     ASTPair currentAST = new ASTPair();
/* 4306 */     GrammarAST rewrite_tree_AST = null;
/*      */     try
/*      */     {
/* 4309 */       GrammarAST tmp103_AST = null;
/* 4310 */       tmp103_AST = (GrammarAST)this.astFactory.create(LT(1));
/* 4311 */       this.astFactory.makeASTRoot(currentAST, tmp103_AST);
/* 4312 */       match(75);
/* 4313 */       rewrite_atom();
/* 4314 */       this.astFactory.addASTChild(currentAST, this.returnAST);
/*      */ 
/* 4318 */       while (_tokenSet_43.member(LA(1))) {
/* 4319 */         rewrite_element();
/* 4320 */         this.astFactory.addASTChild(currentAST, this.returnAST);
/*      */       }
/*      */ 
/* 4328 */       match(65);
/* 4329 */       rewrite_tree_AST = (GrammarAST)currentAST.root;
/*      */     }
/*      */     catch (RecognitionException ex) {
/* 4332 */       reportError(ex);
/* 4333 */       recover(ex, _tokenSet_48);
/*      */     }
/* 4335 */     this.returnAST = rewrite_tree_AST;
/*      */   }
/*      */ 
/*      */   public final void rewrite_template_head()
/*      */     throws RecognitionException, TokenStreamException
/*      */   {
/* 4341 */     this.returnAST = null;
/* 4342 */     ASTPair currentAST = new ASTPair();
/* 4343 */     GrammarAST rewrite_template_head_AST = null;
/* 4344 */     Token lp = null;
/* 4345 */     GrammarAST lp_AST = null;
/*      */     try
/*      */     {
/* 4348 */       id();
/* 4349 */       this.astFactory.addASTChild(currentAST, this.returnAST);
/* 4350 */       lp = LT(1);
/* 4351 */       lp_AST = (GrammarAST)this.astFactory.create(lp);
/* 4352 */       this.astFactory.makeASTRoot(currentAST, lp_AST);
/* 4353 */       match(63);
/* 4354 */       lp_AST.setType(32); lp_AST.setText("TEMPLATE");
/* 4355 */       rewrite_template_args();
/* 4356 */       this.astFactory.addASTChild(currentAST, this.returnAST);
/* 4357 */       match(65);
/* 4358 */       rewrite_template_head_AST = (GrammarAST)currentAST.root;
/*      */     }
/*      */     catch (RecognitionException ex) {
/* 4361 */       reportError(ex);
/* 4362 */       recover(ex, _tokenSet_49);
/*      */     }
/* 4364 */     this.returnAST = rewrite_template_head_AST;
/*      */   }
/*      */ 
/*      */   public final void rewrite_indirect_template_head()
/*      */     throws RecognitionException, TokenStreamException
/*      */   {
/* 4370 */     this.returnAST = null;
/* 4371 */     ASTPair currentAST = new ASTPair();
/* 4372 */     GrammarAST rewrite_indirect_template_head_AST = null;
/* 4373 */     Token lp = null;
/* 4374 */     GrammarAST lp_AST = null;
/*      */     try
/*      */     {
/* 4377 */       lp = LT(1);
/* 4378 */       lp_AST = (GrammarAST)this.astFactory.create(lp);
/* 4379 */       this.astFactory.makeASTRoot(currentAST, lp_AST);
/* 4380 */       match(63);
/* 4381 */       lp_AST.setType(32); lp_AST.setText("TEMPLATE");
/* 4382 */       GrammarAST tmp106_AST = null;
/* 4383 */       tmp106_AST = (GrammarAST)this.astFactory.create(LT(1));
/* 4384 */       this.astFactory.addASTChild(currentAST, tmp106_AST);
/* 4385 */       match(40);
/* 4386 */       match(65);
/* 4387 */       match(63);
/* 4388 */       rewrite_template_args();
/* 4389 */       this.astFactory.addASTChild(currentAST, this.returnAST);
/* 4390 */       match(65);
/* 4391 */       rewrite_indirect_template_head_AST = (GrammarAST)currentAST.root;
/*      */     }
/*      */     catch (RecognitionException ex) {
/* 4394 */       reportError(ex);
/* 4395 */       recover(ex, _tokenSet_23);
/*      */     }
/* 4397 */     this.returnAST = rewrite_indirect_template_head_AST;
/*      */   }
/*      */ 
/*      */   public final void rewrite_template_args() throws RecognitionException, TokenStreamException
/*      */   {
/* 4402 */     this.returnAST = null;
/* 4403 */     ASTPair currentAST = new ASTPair();
/* 4404 */     GrammarAST rewrite_template_args_AST = null;
/*      */     try
/*      */     {
/* 4407 */       switch (LA(1))
/*      */       {
/*      */       case 55:
/*      */       case 73:
/* 4411 */         rewrite_template_arg();
/* 4412 */         this.astFactory.addASTChild(currentAST, this.returnAST);
/*      */ 
/* 4416 */         while (LA(1) == 54) {
/* 4417 */           match(54);
/* 4418 */           rewrite_template_arg();
/* 4419 */           this.astFactory.addASTChild(currentAST, this.returnAST);
/*      */         }
/*      */ 
/* 4427 */         rewrite_template_args_AST = (GrammarAST)currentAST.root;
/* 4428 */         rewrite_template_args_AST = (GrammarAST)this.astFactory.make(new ASTArray(2).add((GrammarAST)this.astFactory.create(23, "ARGLIST")).add(rewrite_template_args_AST));
/* 4429 */         currentAST.root = rewrite_template_args_AST;
/* 4430 */         currentAST.child = ((rewrite_template_args_AST != null) && (rewrite_template_args_AST.getFirstChild() != null) ? rewrite_template_args_AST.getFirstChild() : rewrite_template_args_AST);
/*      */ 
/* 4432 */         currentAST.advanceChildToEnd();
/* 4433 */         rewrite_template_args_AST = (GrammarAST)currentAST.root;
/* 4434 */         break;
/*      */       case 65:
/* 4438 */         rewrite_template_args_AST = (GrammarAST)currentAST.root;
/* 4439 */         rewrite_template_args_AST = (GrammarAST)this.astFactory.create(23, "ARGLIST");
/* 4440 */         currentAST.root = rewrite_template_args_AST;
/* 4441 */         currentAST.child = ((rewrite_template_args_AST != null) && (rewrite_template_args_AST.getFirstChild() != null) ? rewrite_template_args_AST.getFirstChild() : rewrite_template_args_AST);
/*      */ 
/* 4443 */         currentAST.advanceChildToEnd();
/* 4444 */         rewrite_template_args_AST = (GrammarAST)currentAST.root;
/* 4445 */         break;
/*      */       default:
/* 4449 */         throw new NoViableAltException(LT(1), getFilename());
/*      */       }
/*      */     }
/*      */     catch (RecognitionException ex)
/*      */     {
/* 4454 */       reportError(ex);
/* 4455 */       recover(ex, _tokenSet_50);
/*      */     }
/* 4457 */     this.returnAST = rewrite_template_args_AST;
/*      */   }
/*      */ 
/*      */   public final void rewrite_template_arg() throws RecognitionException, TokenStreamException
/*      */   {
/* 4462 */     this.returnAST = null;
/* 4463 */     ASTPair currentAST = new ASTPair();
/* 4464 */     GrammarAST rewrite_template_arg_AST = null;
/* 4465 */     Token a = null;
/* 4466 */     GrammarAST a_AST = null;
/*      */     try
/*      */     {
/* 4469 */       id();
/* 4470 */       this.astFactory.addASTChild(currentAST, this.returnAST);
/* 4471 */       a = LT(1);
/* 4472 */       a_AST = (GrammarAST)this.astFactory.create(a);
/* 4473 */       this.astFactory.makeASTRoot(currentAST, a_AST);
/* 4474 */       match(49);
/* 4475 */       a_AST.setType(22); a_AST.setText("ARG");
/* 4476 */       GrammarAST tmp111_AST = null;
/* 4477 */       tmp111_AST = (GrammarAST)this.astFactory.create(LT(1));
/* 4478 */       this.astFactory.addASTChild(currentAST, tmp111_AST);
/* 4479 */       match(40);
/* 4480 */       rewrite_template_arg_AST = (GrammarAST)currentAST.root;
/*      */     }
/*      */     catch (RecognitionException ex) {
/* 4483 */       reportError(ex);
/* 4484 */       recover(ex, _tokenSet_51);
/*      */     }
/* 4486 */     this.returnAST = rewrite_template_arg_AST;
/*      */   }
/*      */ 
/*      */   protected void buildTokenTypeASTClassMap()
/*      */   {
/* 4596 */     this.tokenTypeToASTClassMap = null;
/*      */   }
/*      */ 
/*      */   private static final long[] mk_tokenSet_0() {
/* 4600 */     long[] data = { 2L, 0L };
/* 4601 */     return data;
/*      */   }
/*      */ 
/*      */   private static final long[] mk_tokenSet_1() {
/* 4605 */     long[] data = { 36028797018963968L, 512L, 0L, 0L };
/* 4606 */     return data;
/*      */   }
/*      */ 
/*      */   private static final long[] mk_tokenSet_2() {
/* 4610 */     long[] data = { -509253095465680880L, 375571L, 0L, 0L };
/* 4611 */     return data;
/*      */   }
/*      */ 
/*      */   private static final long[] mk_tokenSet_3() {
/* 4615 */     long[] data = { 540645561187958816L, 512L, 0L, 0L };
/* 4616 */     return data;
/*      */   }
/*      */ 
/*      */   private static final long[] mk_tokenSet_4() {
/* 4620 */     long[] data = { 540504806519734304L, 512L, 0L, 0L };
/* 4621 */     return data;
/*      */   }
/*      */ 
/*      */   private static final long[] mk_tokenSet_5() {
/* 4625 */     long[] data = { 540504806519734272L, 512L, 0L, 0L };
/* 4626 */     return data;
/*      */   }
/*      */ 
/*      */   private static final long[] mk_tokenSet_6() {
/* 4630 */     long[] data = { 540504797929799680L, 512L, 0L, 0L };
/* 4631 */     return data;
/*      */   }
/*      */ 
/*      */   private static final long[] mk_tokenSet_7() {
/* 4635 */     long[] data = { 540434429185622016L, 512L, 0L, 0L };
/* 4636 */     return data;
/*      */   }
/*      */ 
/*      */   private static final long[] mk_tokenSet_8() {
/* 4640 */     long[] data = { 36037593111986240L, 512L, 0L, 0L };
/* 4641 */     return data;
/*      */   }
/*      */ 
/*      */   private static final long[] mk_tokenSet_9() {
/* 4645 */     long[] data = { 140737488355328L, 0L };
/* 4646 */     return data;
/*      */   }
/*      */ 
/*      */   private static final long[] mk_tokenSet_10() {
/* 4650 */     long[] data = { 4398046511104L, 0L };
/* 4651 */     return data;
/*      */   }
/*      */ 
/*      */   private static final long[] mk_tokenSet_11() {
/* 4655 */     long[] data = { 18018796555993088L, 0L };
/* 4656 */     return data;
/*      */   }
/*      */ 
/*      */   private static final long[] mk_tokenSet_12() {
/* 4660 */     long[] data = { 36310271995674624L, 0L };
/* 4661 */     return data;
/*      */   }
/*      */ 
/*      */   private static final long[] mk_tokenSet_13() {
/* 4665 */     long[] data = { 540434429185622018L, 512L, 0L, 0L };
/* 4666 */     return data;
/*      */   }
/*      */ 
/*      */   private static final long[] mk_tokenSet_14() {
/* 4670 */     long[] data = { 211114822467600L, 0L };
/* 4671 */     return data;
/*      */   }
/*      */ 
/*      */   private static final long[] mk_tokenSet_15() {
/* 4675 */     long[] data = { -9183960041483403264L, 69409L, 0L, 0L };
/* 4676 */     return data;
/*      */   }
/*      */ 
/*      */   private static final long[] mk_tokenSet_16() {
/* 4680 */     long[] data = { -6922376498456281070L, 491519L, 0L, 0L };
/* 4681 */     return data;
/*      */   }
/*      */ 
/*      */   private static final long[] mk_tokenSet_17() {
/* 4685 */     long[] data = { 211106232532992L, 0L };
/* 4686 */     return data;
/*      */   }
/*      */ 
/*      */   private static final long[] mk_tokenSet_18() {
/* 4690 */     long[] data = { -9183964439529914368L, 69411L, 0L, 0L };
/* 4691 */     return data;
/*      */   }
/*      */ 
/*      */   private static final long[] mk_tokenSet_19() {
/* 4695 */     long[] data = { -7444796529132421104L, 491507L, 0L, 0L };
/* 4696 */     return data;
/*      */   }
/*      */ 
/*      */   private static final long[] mk_tokenSet_20() {
/* 4700 */     long[] data = { -6940390896965763054L, 491519L, 0L, 0L };
/* 4701 */     return data;
/*      */   }
/*      */ 
/*      */   private static final long[] mk_tokenSet_21() {
/* 4705 */     long[] data = { -8598492089925238784L, 81891L, 0L, 0L };
/* 4706 */     return data;
/*      */   }
/*      */ 
/*      */   private static final long[] mk_tokenSet_22() {
/* 4710 */     long[] data = { -9183964439529914368L, 3872L, 0L, 0L };
/* 4711 */     return data;
/*      */   }
/*      */ 
/*      */   private static final long[] mk_tokenSet_23() {
/* 4715 */     long[] data = { 4398046511104L, 65539L, 0L, 0L };
/* 4716 */     return data;
/*      */   }
/*      */ 
/*      */   private static final long[] mk_tokenSet_24() {
/* 4720 */     long[] data = { 4398046511104L, 3L, 0L, 0L };
/* 4721 */     return data;
/*      */   }
/*      */ 
/*      */   private static final long[] mk_tokenSet_25() {
/* 4725 */     long[] data = { -9183960041483403264L, 69411L, 0L, 0L };
/* 4726 */     return data;
/*      */   }
/*      */ 
/*      */   private static final long[] mk_tokenSet_26() {
/* 4730 */     long[] data = { 540434429185622018L, 524L, 0L, 0L };
/* 4731 */     return data;
/*      */   }
/*      */ 
/*      */   private static final long[] mk_tokenSet_27() {
/* 4735 */     long[] data = { 39406496739491840L, 1792L, 0L, 0L };
/* 4736 */     return data;
/*      */   }
/*      */ 
/*      */   private static final long[] mk_tokenSet_28() {
/* 4740 */     long[] data = { -7445570585318375424L, 98211L, 0L, 0L };
/* 4741 */     return data;
/*      */   }
/*      */ 
/*      */   private static final long[] mk_tokenSet_29() {
/* 4745 */     long[] data = { 39406496739491840L, 768L, 0L, 0L };
/* 4746 */     return data;
/*      */   }
/*      */ 
/*      */   private static final long[] mk_tokenSet_30() {
/* 4750 */     long[] data = { -7445570585318391808L, 98211L, 0L, 0L };
/* 4751 */     return data;
/*      */   }
/*      */ 
/*      */   private static final long[] mk_tokenSet_31() {
/* 4755 */     long[] data = { 39406496739491840L, 256L, 0L, 0L };
/* 4756 */     return data;
/*      */   }
/*      */ 
/*      */   private static final long[] mk_tokenSet_32() {
/* 4760 */     long[] data = { -7445570585318391808L, 81827L, 0L, 0L };
/* 4761 */     return data;
/*      */   }
/*      */ 
/*      */   private static final long[] mk_tokenSet_33() {
/* 4765 */     long[] data = { -9174952842228662272L, 81699L, 0L, 0L };
/* 4766 */     return data;
/*      */   }
/*      */ 
/*      */   private static final long[] mk_tokenSet_34() {
/* 4770 */     long[] data = { -9183960041483403264L, 331555L, 0L, 0L };
/* 4771 */     return data;
/*      */   }
/*      */ 
/*      */   private static final long[] mk_tokenSet_35() {
/* 4775 */     long[] data = { -8598492089925238784L, 81827L, 0L, 0L };
/* 4776 */     return data;
/*      */   }
/*      */ 
/*      */   private static final long[] mk_tokenSet_36() {
/* 4780 */     long[] data = { -7454582182619627520L, 20384L, 0L, 0L };
/* 4781 */     return data;
/*      */   }
/*      */ 
/*      */   private static final long[] mk_tokenSet_37() {
/* 4785 */     long[] data = { -7445570585318391808L, 343971L, 0L, 0L };
/* 4786 */     return data;
/*      */   }
/*      */ 
/*      */   private static final long[] mk_tokenSet_38() {
/* 4790 */     long[] data = { 0L, 32768L, 0L, 0L };
/* 4791 */     return data;
/*      */   }
/*      */ 
/*      */   private static final long[] mk_tokenSet_39() {
/* 4795 */     long[] data = { 4398046511104L, 32768L, 0L, 0L };
/* 4796 */     return data;
/*      */   }
/*      */ 
/*      */   private static final long[] mk_tokenSet_40() {
/* 4800 */     long[] data = { -9187342140324184064L, 512L, 0L, 0L };
/* 4801 */     return data;
/*      */   }
/*      */ 
/*      */   private static final long[] mk_tokenSet_41() {
/* 4805 */     long[] data = { -9223366539296636928L, 65539L, 0L, 0L };
/* 4806 */     return data;
/*      */   }
/*      */ 
/*      */   private static final long[] mk_tokenSet_42() {
/* 4810 */     long[] data = { -8094086457758580734L, 475119L, 0L, 0L };
/* 4811 */     return data;
/*      */   }
/*      */ 
/*      */   private static final long[] mk_tokenSet_43() {
/* 4815 */     long[] data = { -9183964440603656192L, 264704L, 0L, 0L };
/* 4816 */     return data;
/*      */   }
/*      */ 
/*      */   private static final long[] mk_tokenSet_44() {
/* 4820 */     long[] data = { -8022031338695557120L, 489987L, 0L, 0L };
/* 4821 */     return data;
/*      */   }
/*      */ 
/*      */   private static final long[] mk_tokenSet_45() {
/* 4825 */     long[] data = { -6941164953151733758L, 491503L, 0L, 0L };
/* 4826 */     return data;
/*      */   }
/*      */ 
/*      */   private static final long[] mk_tokenSet_46() {
/* 4830 */     long[] data = { 9007199254740992L, 12288L, 0L, 0L };
/* 4831 */     return data;
/*      */   }
/*      */ 
/*      */   private static final long[] mk_tokenSet_47() {
/* 4835 */     long[] data = { -9183960042557145088L, 330243L, 0L, 0L };
/* 4836 */     return data;
/*      */   }
/*      */ 
/*      */   private static final long[] mk_tokenSet_48() {
/* 4840 */     long[] data = { -9174952843302404096L, 342531L, 0L, 0L };
/* 4841 */     return data;
/*      */   }
/*      */ 
/*      */   private static final long[] mk_tokenSet_49() {
/* 4845 */     long[] data = { 4398046511104L, 1638403L, 0L, 0L };
/* 4846 */     return data;
/*      */   }
/*      */ 
/*      */   private static final long[] mk_tokenSet_50() {
/* 4850 */     long[] data = { 0L, 2L, 0L, 0L };
/* 4851 */     return data;
/*      */   }
/*      */ 
/*      */   private static final long[] mk_tokenSet_51() {
/* 4855 */     long[] data = { 18014398509481984L, 2L, 0L, 0L };
/* 4856 */     return data;
/*      */   }
/*      */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.antlr.grammar.v2.ANTLRParser
 * JD-Core Version:    0.6.2
 */