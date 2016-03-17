/*     */ package antlr;
/*     */ 
/*     */ import antlr.collections.impl.BitSet;
/*     */ import antlr.collections.impl.Vector;
/*     */ import java.io.IOException;
/*     */ import java.io.PrintStream;
/*     */ import java.io.PrintWriter;
/*     */ 
/*     */ public abstract class CodeGenerator
/*     */ {
/*     */   protected Tool antlrTool;
/*  54 */   protected int tabs = 0;
/*     */   protected transient PrintWriter currentOutput;
/*  60 */   protected Grammar grammar = null;
/*     */   protected Vector bitsetsUsed;
/*     */   protected DefineGrammarSymbols behavior;
/*     */   protected LLkGrammarAnalyzer analyzer;
/*     */   protected CharFormatter charFormatter;
/*  77 */   protected boolean DEBUG_CODE_GENERATOR = false;
/*     */   protected static final int DEFAULT_MAKE_SWITCH_THRESHOLD = 2;
/*     */   protected static final int DEFAULT_BITSET_TEST_THRESHOLD = 4;
/*     */   protected static final int BITSET_OPTIMIZE_INIT_THRESHOLD = 8;
/*  94 */   protected int makeSwitchThreshold = 2;
/*     */ 
/* 102 */   protected int bitsetTestThreshold = 4;
/*     */ 
/* 104 */   private static boolean OLD_ACTION_TRANSLATOR = true;
/*     */ 
/* 106 */   public static String TokenTypesFileSuffix = "TokenTypes";
/* 107 */   public static String TokenTypesFileExt = ".txt";
/*     */ 
/*     */   protected void _print(String paramString)
/*     */   {
/* 118 */     if (paramString != null)
/* 119 */       this.currentOutput.print(paramString);
/*     */   }
/*     */ 
/*     */   protected void _printAction(String paramString)
/*     */   {
/* 129 */     if (paramString == null) {
/* 130 */       return;
/*     */     }
/*     */ 
/* 134 */     int i = 0;
/* 135 */     while ((i < paramString.length()) && (Character.isSpaceChar(paramString.charAt(i)))) {
/* 136 */       i++;
/*     */     }
/*     */ 
/* 140 */     int j = paramString.length() - 1;
/* 141 */     while ((j > i) && (Character.isSpaceChar(paramString.charAt(j)))) {
/* 142 */       j--;
/*     */     }
/*     */ 
/* 145 */     char c = '\000';
/* 146 */     for (int k = i; k <= j; ) {
/* 147 */       c = paramString.charAt(k);
/* 148 */       k++;
/* 149 */       int m = 0;
/* 150 */       switch (c) {
/*     */       case '\n':
/* 152 */         m = 1;
/* 153 */         break;
/*     */       case '\r':
/* 155 */         if ((k <= j) && (paramString.charAt(k) == '\n')) {
/* 156 */           k++;
/*     */         }
/* 158 */         m = 1;
/* 159 */         break;
/*     */       default:
/* 161 */         this.currentOutput.print(c);
/*     */       }
/*     */ 
/* 164 */       if (m != 0) {
/* 165 */         this.currentOutput.println();
/* 166 */         printTabs();
/*     */ 
/* 168 */         while ((k <= j) && (Character.isSpaceChar(paramString.charAt(k)))) {
/* 169 */           k++;
/*     */         }
/* 171 */         m = 0;
/*     */       }
/*     */     }
/* 174 */     this.currentOutput.println();
/*     */   }
/*     */ 
/*     */   protected void _println(String paramString)
/*     */   {
/* 182 */     if (paramString != null)
/* 183 */       this.currentOutput.println(paramString);
/*     */   }
/*     */ 
/*     */   public static boolean elementsAreRange(int[] paramArrayOfInt)
/*     */   {
/* 192 */     if (paramArrayOfInt.length == 0) {
/* 193 */       return false;
/*     */     }
/* 195 */     int i = paramArrayOfInt[0];
/* 196 */     int j = paramArrayOfInt[(paramArrayOfInt.length - 1)];
/* 197 */     if (paramArrayOfInt.length <= 2)
/*     */     {
/* 199 */       return false;
/*     */     }
/* 201 */     if (j - i + 1 > paramArrayOfInt.length)
/*     */     {
/* 203 */       return false;
/*     */     }
/* 205 */     int k = i + 1;
/* 206 */     for (int m = 1; m < paramArrayOfInt.length - 1; m++) {
/* 207 */       if (k != paramArrayOfInt[m])
/*     */       {
/* 209 */         return false;
/*     */       }
/* 211 */       k++;
/*     */     }
/* 213 */     return true;
/*     */   }
/*     */ 
/*     */   protected String extractIdOfAction(Token paramToken)
/*     */   {
/* 224 */     return extractIdOfAction(paramToken.getText(), paramToken.getLine(), paramToken.getColumn());
/*     */   }
/*     */ 
/*     */   protected String extractIdOfAction(String paramString, int paramInt1, int paramInt2)
/*     */   {
/* 237 */     paramString = removeAssignmentFromDeclaration(paramString);
/*     */ 
/* 240 */     for (int i = paramString.length() - 2; i >= 0; i--)
/*     */     {
/* 242 */       if ((!Character.isLetterOrDigit(paramString.charAt(i))) && (paramString.charAt(i) != '_'))
/*     */       {
/* 244 */         return paramString.substring(i + 1);
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 249 */     this.antlrTool.warning("Ill-formed action", this.grammar.getFilename(), paramInt1, paramInt2);
/* 250 */     return "";
/*     */   }
/*     */ 
/*     */   protected String extractTypeOfAction(Token paramToken)
/*     */   {
/* 261 */     return extractTypeOfAction(paramToken.getText(), paramToken.getLine(), paramToken.getColumn());
/*     */   }
/*     */ 
/*     */   protected String extractTypeOfAction(String paramString, int paramInt1, int paramInt2)
/*     */   {
/* 273 */     paramString = removeAssignmentFromDeclaration(paramString);
/*     */ 
/* 276 */     for (int i = paramString.length() - 2; i >= 0; i--)
/*     */     {
/* 278 */       if ((!Character.isLetterOrDigit(paramString.charAt(i))) && (paramString.charAt(i) != '_'))
/*     */       {
/* 280 */         return paramString.substring(0, i + 1);
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 285 */     this.antlrTool.warning("Ill-formed action", this.grammar.getFilename(), paramInt1, paramInt2);
/* 286 */     return "";
/*     */   }
/*     */ 
/*     */   public abstract void gen();
/*     */ 
/*     */   public abstract void gen(ActionElement paramActionElement);
/*     */ 
/*     */   public abstract void gen(AlternativeBlock paramAlternativeBlock);
/*     */ 
/*     */   public abstract void gen(BlockEndElement paramBlockEndElement);
/*     */ 
/*     */   public abstract void gen(CharLiteralElement paramCharLiteralElement);
/*     */ 
/*     */   public abstract void gen(CharRangeElement paramCharRangeElement);
/*     */ 
/*     */   public abstract void gen(LexerGrammar paramLexerGrammar)
/*     */     throws IOException;
/*     */ 
/*     */   public abstract void gen(OneOrMoreBlock paramOneOrMoreBlock);
/*     */ 
/*     */   public abstract void gen(ParserGrammar paramParserGrammar)
/*     */     throws IOException;
/*     */ 
/*     */   public abstract void gen(RuleRefElement paramRuleRefElement);
/*     */ 
/*     */   public abstract void gen(StringLiteralElement paramStringLiteralElement);
/*     */ 
/*     */   public abstract void gen(TokenRangeElement paramTokenRangeElement);
/*     */ 
/*     */   public abstract void gen(TokenRefElement paramTokenRefElement);
/*     */ 
/*     */   public abstract void gen(TreeElement paramTreeElement);
/*     */ 
/*     */   public abstract void gen(TreeWalkerGrammar paramTreeWalkerGrammar)
/*     */     throws IOException;
/*     */ 
/*     */   public abstract void gen(WildcardElement paramWildcardElement);
/*     */ 
/*     */   public abstract void gen(ZeroOrMoreBlock paramZeroOrMoreBlock);
/*     */ 
/*     */   protected void genTokenInterchange(TokenManager paramTokenManager)
/*     */     throws IOException
/*     */   {
/* 372 */     String str1 = paramTokenManager.getName() + TokenTypesFileSuffix + TokenTypesFileExt;
/* 373 */     this.currentOutput = this.antlrTool.openOutputFile(str1);
/*     */ 
/* 375 */     println("// $ANTLR " + Tool.version + ": " + this.antlrTool.fileMinusPath(this.antlrTool.grammarFile) + " -> " + str1 + "$");
/*     */ 
/* 381 */     this.tabs = 0;
/*     */ 
/* 384 */     println(paramTokenManager.getName() + "    // output token vocab name");
/*     */ 
/* 387 */     Vector localVector = paramTokenManager.getVocabulary();
/* 388 */     for (int i = 4; i < localVector.size(); i++) {
/* 389 */       String str2 = (String)localVector.elementAt(i);
/* 390 */       if (this.DEBUG_CODE_GENERATOR) {
/* 391 */         System.out.println("gen persistence file entry for: " + str2);
/*     */       }
/* 393 */       if ((str2 != null) && (!str2.startsWith("<")))
/*     */       {
/*     */         Object localObject;
/* 395 */         if (str2.startsWith("\"")) {
/* 396 */           localObject = (StringLiteralSymbol)paramTokenManager.getTokenSymbol(str2);
/* 397 */           if ((localObject != null) && (((StringLiteralSymbol)localObject).label != null)) {
/* 398 */             print(((StringLiteralSymbol)localObject).label + "=");
/*     */           }
/* 400 */           println(str2 + "=" + i);
/*     */         }
/*     */         else {
/* 403 */           print(str2);
/*     */ 
/* 405 */           localObject = paramTokenManager.getTokenSymbol(str2);
/* 406 */           if (localObject == null) {
/* 407 */             this.antlrTool.warning("undefined token symbol: " + str2);
/*     */           }
/* 410 */           else if (((TokenSymbol)localObject).getParaphrase() != null) {
/* 411 */             print("(" + ((TokenSymbol)localObject).getParaphrase() + ")");
/*     */           }
/*     */ 
/* 414 */           println("=" + i);
/*     */         }
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 420 */     this.currentOutput.close();
/* 421 */     this.currentOutput = null;
/*     */   }
/*     */ 
/*     */   public String processStringForASTConstructor(String paramString)
/*     */   {
/* 430 */     return paramString;
/*     */   }
/*     */ 
/*     */   public abstract String getASTCreateString(Vector paramVector);
/*     */ 
/*     */   public abstract String getASTCreateString(GrammarAtom paramGrammarAtom, String paramString);
/*     */ 
/*     */   protected String getBitsetName(int paramInt)
/*     */   {
/* 449 */     return "_tokenSet_" + paramInt;
/*     */   }
/*     */ 
/*     */   public static String encodeLexerRuleName(String paramString) {
/* 453 */     return "m" + paramString;
/*     */   }
/*     */ 
/*     */   public static String decodeLexerRuleName(String paramString) {
/* 457 */     if (paramString == null) {
/* 458 */       return null;
/*     */     }
/* 460 */     return paramString.substring(1, paramString.length());
/*     */   }
/*     */ 
/*     */   public abstract String mapTreeId(String paramString, ActionTransInfo paramActionTransInfo);
/*     */ 
/*     */   protected int markBitsetForGen(BitSet paramBitSet)
/*     */   {
/* 484 */     for (int i = 0; i < this.bitsetsUsed.size(); i++) {
/* 485 */       BitSet localBitSet = (BitSet)this.bitsetsUsed.elementAt(i);
/* 486 */       if (paramBitSet.equals(localBitSet))
/*     */       {
/* 488 */         return i;
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 493 */     this.bitsetsUsed.appendElement(paramBitSet.clone());
/* 494 */     return this.bitsetsUsed.size() - 1;
/*     */   }
/*     */ 
/*     */   protected void print(String paramString)
/*     */   {
/* 502 */     if (paramString != null) {
/* 503 */       printTabs();
/* 504 */       this.currentOutput.print(paramString);
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void printAction(String paramString)
/*     */   {
/* 514 */     if (paramString != null) {
/* 515 */       printTabs();
/* 516 */       _printAction(paramString);
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void println(String paramString)
/*     */   {
/* 525 */     if (paramString != null) {
/* 526 */       printTabs();
/* 527 */       this.currentOutput.println(paramString);
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void printTabs()
/*     */   {
/* 535 */     for (int i = 1; i <= this.tabs; i++)
/* 536 */       this.currentOutput.print("\t");
/*     */   }
/*     */ 
/*     */   protected abstract String processActionForSpecialSymbols(String paramString, int paramInt, RuleBlock paramRuleBlock, ActionTransInfo paramActionTransInfo);
/*     */ 
/*     */   public String getFOLLOWBitSet(String paramString, int paramInt)
/*     */   {
/* 550 */     GrammarSymbol localGrammarSymbol = this.grammar.getSymbol(paramString);
/* 551 */     if (!(localGrammarSymbol instanceof RuleSymbol)) {
/* 552 */       return null;
/*     */     }
/* 554 */     RuleBlock localRuleBlock = ((RuleSymbol)localGrammarSymbol).getBlock();
/* 555 */     Lookahead localLookahead = this.grammar.theLLkAnalyzer.FOLLOW(paramInt, localRuleBlock.endNode);
/* 556 */     String str = getBitsetName(markBitsetForGen(localLookahead.fset));
/* 557 */     return str;
/*     */   }
/*     */ 
/*     */   public String getFIRSTBitSet(String paramString, int paramInt) {
/* 561 */     GrammarSymbol localGrammarSymbol = this.grammar.getSymbol(paramString);
/* 562 */     if (!(localGrammarSymbol instanceof RuleSymbol)) {
/* 563 */       return null;
/*     */     }
/* 565 */     RuleBlock localRuleBlock = ((RuleSymbol)localGrammarSymbol).getBlock();
/* 566 */     Lookahead localLookahead = this.grammar.theLLkAnalyzer.look(paramInt, localRuleBlock);
/* 567 */     String str = getBitsetName(markBitsetForGen(localLookahead.fset));
/* 568 */     return str;
/*     */   }
/*     */ 
/*     */   protected String removeAssignmentFromDeclaration(String paramString)
/*     */   {
/* 579 */     if (paramString.indexOf('=') >= 0) paramString = paramString.substring(0, paramString.indexOf('=')).trim();
/* 580 */     return paramString;
/*     */   }
/*     */ 
/*     */   private void reset()
/*     */   {
/* 585 */     this.tabs = 0;
/*     */ 
/* 587 */     this.bitsetsUsed = new Vector();
/* 588 */     this.currentOutput = null;
/* 589 */     this.grammar = null;
/* 590 */     this.DEBUG_CODE_GENERATOR = false;
/* 591 */     this.makeSwitchThreshold = 2;
/* 592 */     this.bitsetTestThreshold = 4;
/*     */   }
/*     */ 
/*     */   public static String reverseLexerRuleName(String paramString) {
/* 596 */     return paramString.substring(1, paramString.length());
/*     */   }
/*     */ 
/*     */   public void setAnalyzer(LLkGrammarAnalyzer paramLLkGrammarAnalyzer) {
/* 600 */     this.analyzer = paramLLkGrammarAnalyzer;
/*     */   }
/*     */ 
/*     */   public void setBehavior(DefineGrammarSymbols paramDefineGrammarSymbols) {
/* 604 */     this.behavior = paramDefineGrammarSymbols;
/*     */   }
/*     */ 
/*     */   protected void setGrammar(Grammar paramGrammar)
/*     */   {
/* 609 */     reset();
/* 610 */     this.grammar = paramGrammar;
/*     */     Token localToken2;
/* 612 */     if (this.grammar.hasOption("codeGenMakeSwitchThreshold")) {
/*     */       try {
/* 614 */         this.makeSwitchThreshold = this.grammar.getIntegerOption("codeGenMakeSwitchThreshold");
/*     */       }
/*     */       catch (NumberFormatException localNumberFormatException1)
/*     */       {
/* 618 */         localToken2 = this.grammar.getOption("codeGenMakeSwitchThreshold");
/* 619 */         this.antlrTool.error("option 'codeGenMakeSwitchThreshold' must be an integer", this.grammar.getClassName(), localToken2.getLine(), localToken2.getColumn());
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 628 */     if (this.grammar.hasOption("codeGenBitsetTestThreshold")) {
/*     */       try {
/* 630 */         this.bitsetTestThreshold = this.grammar.getIntegerOption("codeGenBitsetTestThreshold");
/*     */       }
/*     */       catch (NumberFormatException localNumberFormatException2)
/*     */       {
/* 634 */         localToken2 = this.grammar.getOption("codeGenBitsetTestThreshold");
/* 635 */         this.antlrTool.error("option 'codeGenBitsetTestThreshold' must be an integer", this.grammar.getClassName(), localToken2.getLine(), localToken2.getColumn());
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 644 */     if (this.grammar.hasOption("codeGenDebug")) {
/* 645 */       Token localToken1 = this.grammar.getOption("codeGenDebug");
/* 646 */       if (localToken1.getText().equals("true"))
/*     */       {
/* 648 */         this.DEBUG_CODE_GENERATOR = true;
/*     */       }
/* 650 */       else if (localToken1.getText().equals("false"))
/*     */       {
/* 652 */         this.DEBUG_CODE_GENERATOR = false;
/*     */       }
/*     */       else
/* 655 */         this.antlrTool.error("option 'codeGenDebug' must be true or false", this.grammar.getClassName(), localToken1.getLine(), localToken1.getColumn());
/*     */     }
/*     */   }
/*     */ 
/*     */   public void setTool(Tool paramTool)
/*     */   {
/* 661 */     this.antlrTool = paramTool;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     antlr.CodeGenerator
 * JD-Core Version:    0.6.2
 */