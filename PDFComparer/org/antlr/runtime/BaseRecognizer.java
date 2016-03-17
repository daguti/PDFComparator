/*     */ package org.antlr.runtime;
/*     */ 
/*     */ import java.io.PrintStream;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ 
/*     */ public abstract class BaseRecognizer
/*     */ {
/*     */   public static final int MEMO_RULE_FAILED = -2;
/*     */   public static final int MEMO_RULE_UNKNOWN = -1;
/*     */   public static final int INITIAL_FOLLOW_STACK_SIZE = 100;
/*     */   public static final int DEFAULT_TOKEN_CHANNEL = 0;
/*     */   public static final int HIDDEN = 99;
/*     */   public static final String NEXT_TOKEN_RULE_NAME = "nextToken";
/*     */   protected RecognizerSharedState state;
/*     */ 
/*     */   public BaseRecognizer()
/*     */   {
/*  60 */     this.state = new RecognizerSharedState();
/*     */   }
/*     */ 
/*     */   public BaseRecognizer(RecognizerSharedState state) {
/*  64 */     if (state == null) {
/*  65 */       state = new RecognizerSharedState();
/*     */     }
/*  67 */     this.state = state;
/*     */   }
/*     */ 
/*     */   public void reset()
/*     */   {
/*  73 */     if (this.state == null) {
/*  74 */       return;
/*     */     }
/*  76 */     this.state._fsp = -1;
/*  77 */     this.state.errorRecovery = false;
/*  78 */     this.state.lastErrorIndex = -1;
/*  79 */     this.state.failed = false;
/*  80 */     this.state.syntaxErrors = 0;
/*     */ 
/*  82 */     this.state.backtracking = 0;
/*  83 */     for (int i = 0; (this.state.ruleMemo != null) && (i < this.state.ruleMemo.length); i++)
/*  84 */       this.state.ruleMemo[i] = null;
/*     */   }
/*     */ 
/*     */   public Object match(IntStream input, int ttype, BitSet follow)
/*     */     throws RecognitionException
/*     */   {
/* 104 */     Object matchedSymbol = getCurrentInputSymbol(input);
/* 105 */     if (input.LA(1) == ttype) {
/* 106 */       input.consume();
/* 107 */       this.state.errorRecovery = false;
/* 108 */       this.state.failed = false;
/* 109 */       return matchedSymbol;
/*     */     }
/* 111 */     if (this.state.backtracking > 0) {
/* 112 */       this.state.failed = true;
/* 113 */       return matchedSymbol;
/*     */     }
/* 115 */     matchedSymbol = recoverFromMismatchedToken(input, ttype, follow);
/* 116 */     return matchedSymbol;
/*     */   }
/*     */ 
/*     */   public void matchAny(IntStream input)
/*     */   {
/* 121 */     this.state.errorRecovery = false;
/* 122 */     this.state.failed = false;
/* 123 */     input.consume();
/*     */   }
/*     */ 
/*     */   public boolean mismatchIsUnwantedToken(IntStream input, int ttype) {
/* 127 */     return input.LA(2) == ttype;
/*     */   }
/*     */ 
/*     */   public boolean mismatchIsMissingToken(IntStream input, BitSet follow) {
/* 131 */     if (follow == null)
/*     */     {
/* 134 */       return false;
/*     */     }
/*     */ 
/* 137 */     if (follow.member(1)) {
/* 138 */       BitSet viableTokensFollowingThisRule = computeContextSensitiveRuleFOLLOW();
/* 139 */       follow = follow.or(viableTokensFollowingThisRule);
/* 140 */       if (this.state._fsp >= 0) {
/* 141 */         follow.remove(1);
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 154 */     if ((follow.member(input.LA(1))) || (follow.member(1)))
/*     */     {
/* 156 */       return true;
/*     */     }
/* 158 */     return false;
/*     */   }
/*     */ 
/*     */   public void reportError(RecognitionException e)
/*     */   {
/* 179 */     if (this.state.errorRecovery)
/*     */     {
/* 181 */       return;
/*     */     }
/* 183 */     this.state.syntaxErrors += 1;
/* 184 */     this.state.errorRecovery = true;
/*     */ 
/* 186 */     displayRecognitionError(getTokenNames(), e);
/*     */   }
/*     */ 
/*     */   public void displayRecognitionError(String[] tokenNames, RecognitionException e)
/*     */   {
/* 192 */     String hdr = getErrorHeader(e);
/* 193 */     String msg = getErrorMessage(e, tokenNames);
/* 194 */     emitErrorMessage(hdr + " " + msg);
/*     */   }
/*     */ 
/*     */   public String getErrorMessage(RecognitionException e, String[] tokenNames)
/*     */   {
/* 220 */     String msg = e.getMessage();
/* 221 */     if ((e instanceof UnwantedTokenException)) {
/* 222 */       UnwantedTokenException ute = (UnwantedTokenException)e;
/* 223 */       String tokenName = "<unknown>";
/* 224 */       if (ute.expecting == -1) {
/* 225 */         tokenName = "EOF";
/*     */       }
/*     */       else {
/* 228 */         tokenName = tokenNames[ute.expecting];
/*     */       }
/* 230 */       msg = "extraneous input " + getTokenErrorDisplay(ute.getUnexpectedToken()) + " expecting " + tokenName;
/*     */     }
/* 233 */     else if ((e instanceof MissingTokenException)) {
/* 234 */       MissingTokenException mte = (MissingTokenException)e;
/* 235 */       String tokenName = "<unknown>";
/* 236 */       if (mte.expecting == -1) {
/* 237 */         tokenName = "EOF";
/*     */       }
/*     */       else {
/* 240 */         tokenName = tokenNames[mte.expecting];
/*     */       }
/* 242 */       msg = "missing " + tokenName + " at " + getTokenErrorDisplay(e.token);
/*     */     }
/* 244 */     else if ((e instanceof MismatchedTokenException)) {
/* 245 */       MismatchedTokenException mte = (MismatchedTokenException)e;
/* 246 */       String tokenName = "<unknown>";
/* 247 */       if (mte.expecting == -1) {
/* 248 */         tokenName = "EOF";
/*     */       }
/*     */       else {
/* 251 */         tokenName = tokenNames[mte.expecting];
/*     */       }
/* 253 */       msg = "mismatched input " + getTokenErrorDisplay(e.token) + " expecting " + tokenName;
/*     */     }
/* 256 */     else if ((e instanceof MismatchedTreeNodeException)) {
/* 257 */       MismatchedTreeNodeException mtne = (MismatchedTreeNodeException)e;
/* 258 */       String tokenName = "<unknown>";
/* 259 */       if (mtne.expecting == -1) {
/* 260 */         tokenName = "EOF";
/*     */       }
/*     */       else {
/* 263 */         tokenName = tokenNames[mtne.expecting];
/*     */       }
/* 265 */       msg = "mismatched tree node: " + mtne.node + " expecting " + tokenName;
/*     */     }
/* 268 */     else if ((e instanceof NoViableAltException))
/*     */     {
/* 273 */       msg = "no viable alternative at input " + getTokenErrorDisplay(e.token);
/*     */     }
/* 275 */     else if ((e instanceof EarlyExitException))
/*     */     {
/* 278 */       msg = "required (...)+ loop did not match anything at input " + getTokenErrorDisplay(e.token);
/*     */     }
/* 281 */     else if ((e instanceof MismatchedSetException)) {
/* 282 */       MismatchedSetException mse = (MismatchedSetException)e;
/* 283 */       msg = "mismatched input " + getTokenErrorDisplay(e.token) + " expecting set " + mse.expecting;
/*     */     }
/* 286 */     else if ((e instanceof MismatchedNotSetException)) {
/* 287 */       MismatchedNotSetException mse = (MismatchedNotSetException)e;
/* 288 */       msg = "mismatched input " + getTokenErrorDisplay(e.token) + " expecting set " + mse.expecting;
/*     */     }
/* 291 */     else if ((e instanceof FailedPredicateException)) {
/* 292 */       FailedPredicateException fpe = (FailedPredicateException)e;
/* 293 */       msg = "rule " + fpe.ruleName + " failed predicate: {" + fpe.predicateText + "}?";
/*     */     }
/*     */ 
/* 296 */     return msg;
/*     */   }
/*     */ 
/*     */   public int getNumberOfSyntaxErrors()
/*     */   {
/* 307 */     return this.state.syntaxErrors;
/*     */   }
/*     */ 
/*     */   public String getErrorHeader(RecognitionException e)
/*     */   {
/* 312 */     if (getSourceName() != null) {
/* 313 */       return getSourceName() + " line " + e.line + ":" + e.charPositionInLine;
/*     */     }
/* 315 */     return "line " + e.line + ":" + e.charPositionInLine;
/*     */   }
/*     */ 
/*     */   public String getTokenErrorDisplay(Token t)
/*     */   {
/* 327 */     String s = t.getText();
/* 328 */     if (s == null) {
/* 329 */       if (t.getType() == -1) {
/* 330 */         s = "<EOF>";
/*     */       }
/*     */       else {
/* 333 */         s = "<" + t.getType() + ">";
/*     */       }
/*     */     }
/* 336 */     s = s.replaceAll("\n", "\\\\n");
/* 337 */     s = s.replaceAll("\r", "\\\\r");
/* 338 */     s = s.replaceAll("\t", "\\\\t");
/* 339 */     return "'" + s + "'";
/*     */   }
/*     */ 
/*     */   public void emitErrorMessage(String msg)
/*     */   {
/* 344 */     System.err.println(msg);
/*     */   }
/*     */ 
/*     */   public void recover(IntStream input, RecognitionException re)
/*     */   {
/* 354 */     if (this.state.lastErrorIndex == input.index())
/*     */     {
/* 359 */       input.consume();
/*     */     }
/* 361 */     this.state.lastErrorIndex = input.index();
/* 362 */     BitSet followSet = computeErrorRecoverySet();
/* 363 */     beginResync();
/* 364 */     consumeUntil(input, followSet);
/* 365 */     endResync();
/*     */   }
/*     */ 
/*     */   public void beginResync()
/*     */   {
/*     */   }
/*     */ 
/*     */   public void endResync()
/*     */   {
/*     */   }
/*     */ 
/*     */   protected BitSet computeErrorRecoverySet()
/*     */   {
/* 469 */     return combineFollows(false);
/*     */   }
/*     */ 
/*     */   protected BitSet computeContextSensitiveRuleFOLLOW()
/*     */   {
/* 525 */     return combineFollows(true);
/*     */   }
/*     */ 
/*     */   protected BitSet combineFollows(boolean exact)
/*     */   {
/* 533 */     int top = this.state._fsp;
/* 534 */     BitSet followSet = new BitSet();
/* 535 */     for (int i = top; i >= 0; i--) {
/* 536 */       BitSet localFollowSet = this.state.following[i];
/*     */ 
/* 541 */       followSet.orInPlace(localFollowSet);
/* 542 */       if (exact)
/*     */       {
/* 544 */         if (!localFollowSet.member(1)) {
/*     */           break;
/*     */         }
/* 547 */         if (i > 0) {
/* 548 */           followSet.remove(1);
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 556 */     return followSet;
/*     */   }
/*     */ 
/*     */   protected Object recoverFromMismatchedToken(IntStream input, int ttype, BitSet follow)
/*     */     throws RecognitionException
/*     */   {
/* 591 */     RecognitionException e = null;
/*     */ 
/* 593 */     if (mismatchIsUnwantedToken(input, ttype)) {
/* 594 */       e = new UnwantedTokenException(ttype, input);
/*     */ 
/* 600 */       beginResync();
/* 601 */       input.consume();
/* 602 */       endResync();
/* 603 */       reportError(e);
/*     */ 
/* 605 */       Object matchedSymbol = getCurrentInputSymbol(input);
/* 606 */       input.consume();
/* 607 */       return matchedSymbol;
/*     */     }
/*     */ 
/* 610 */     if (mismatchIsMissingToken(input, follow)) {
/* 611 */       Object inserted = getMissingSymbol(input, e, ttype, follow);
/* 612 */       e = new MissingTokenException(ttype, input, inserted);
/* 613 */       reportError(e);
/* 614 */       return inserted;
/*     */     }
/*     */ 
/* 617 */     e = new MismatchedTokenException(ttype, input);
/* 618 */     throw e;
/*     */   }
/*     */ 
/*     */   public Object recoverFromMismatchedSet(IntStream input, RecognitionException e, BitSet follow)
/*     */     throws RecognitionException
/*     */   {
/* 627 */     if (mismatchIsMissingToken(input, follow))
/*     */     {
/* 629 */       reportError(e);
/*     */ 
/* 631 */       return getMissingSymbol(input, e, 0, follow);
/*     */     }
/*     */ 
/* 634 */     throw e;
/*     */   }
/*     */ 
/*     */   protected Object getCurrentInputSymbol(IntStream input)
/*     */   {
/* 646 */     return null;
/*     */   }
/*     */ 
/*     */   protected Object getMissingSymbol(IntStream input, RecognitionException e, int expectedTokenType, BitSet follow)
/*     */   {
/* 672 */     return null;
/*     */   }
/*     */ 
/*     */   public void consumeUntil(IntStream input, int tokenType)
/*     */   {
/* 677 */     int ttype = input.LA(1);
/* 678 */     while ((ttype != -1) && (ttype != tokenType)) {
/* 679 */       input.consume();
/* 680 */       ttype = input.LA(1);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void consumeUntil(IntStream input, BitSet set)
/*     */   {
/* 687 */     int ttype = input.LA(1);
/* 688 */     while ((ttype != -1) && (!set.member(ttype)))
/*     */     {
/* 690 */       input.consume();
/* 691 */       ttype = input.LA(1);
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void pushFollow(BitSet fset)
/*     */   {
/* 697 */     if (this.state._fsp + 1 >= this.state.following.length) {
/* 698 */       BitSet[] f = new BitSet[this.state.following.length * 2];
/* 699 */       System.arraycopy(this.state.following, 0, f, 0, this.state.following.length);
/* 700 */       this.state.following = f;
/*     */     }
/* 702 */     this.state.following[(++this.state._fsp)] = fset;
/*     */   }
/*     */ 
/*     */   public List getRuleInvocationStack()
/*     */   {
/* 714 */     String parserClassName = getClass().getName();
/* 715 */     return getRuleInvocationStack(new Throwable(), parserClassName);
/*     */   }
/*     */ 
/*     */   public static List getRuleInvocationStack(Throwable e, String recognizerClassName)
/*     */   {
/* 728 */     List rules = new ArrayList();
/* 729 */     StackTraceElement[] stack = e.getStackTrace();
/* 730 */     int i = 0;
/* 731 */     for (i = stack.length - 1; i >= 0; i--) {
/* 732 */       StackTraceElement t = stack[i];
/* 733 */       if (!t.getClassName().startsWith("org.antlr.runtime."))
/*     */       {
/* 736 */         if (!t.getMethodName().equals("nextToken"))
/*     */         {
/* 739 */           if (t.getClassName().equals(recognizerClassName))
/*     */           {
/* 742 */             rules.add(t.getMethodName());
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/* 744 */     return rules;
/*     */   }
/*     */   public int getBacktrackingLevel() {
/* 747 */     return this.state.backtracking;
/*     */   }
/* 749 */   public void setBacktrackingLevel(int n) { this.state.backtracking = n; }
/*     */ 
/*     */   public boolean failed() {
/* 752 */     return this.state.failed;
/*     */   }
/*     */ 
/*     */   public String[] getTokenNames()
/*     */   {
/* 759 */     return null;
/*     */   }
/*     */ 
/*     */   public String getGrammarFileName()
/*     */   {
/* 766 */     return null;
/*     */   }
/*     */ 
/*     */   public abstract String getSourceName();
/*     */ 
/*     */   public List toStrings(List tokens)
/*     */   {
/* 775 */     if (tokens == null) return null;
/* 776 */     List strings = new ArrayList(tokens.size());
/* 777 */     for (int i = 0; i < tokens.size(); i++) {
/* 778 */       strings.add(((Token)tokens.get(i)).getText());
/*     */     }
/* 780 */     return strings;
/*     */   }
/*     */ 
/*     */   public int getRuleMemoization(int ruleIndex, int ruleStartIndex)
/*     */   {
/* 794 */     if (this.state.ruleMemo[ruleIndex] == null) {
/* 795 */       this.state.ruleMemo[ruleIndex] = new HashMap();
/*     */     }
/* 797 */     Integer stopIndexI = (Integer)this.state.ruleMemo[ruleIndex].get(new Integer(ruleStartIndex));
/*     */ 
/* 799 */     if (stopIndexI == null) {
/* 800 */       return -1;
/*     */     }
/* 802 */     return stopIndexI.intValue();
/*     */   }
/*     */ 
/*     */   public boolean alreadyParsedRule(IntStream input, int ruleIndex)
/*     */   {
/* 815 */     int stopIndex = getRuleMemoization(ruleIndex, input.index());
/* 816 */     if (stopIndex == -1) {
/* 817 */       return false;
/*     */     }
/* 819 */     if (stopIndex == -2)
/*     */     {
/* 821 */       this.state.failed = true;
/*     */     }
/*     */     else
/*     */     {
/* 825 */       input.seek(stopIndex + 1);
/*     */     }
/* 827 */     return true;
/*     */   }
/*     */ 
/*     */   public void memoize(IntStream input, int ruleIndex, int ruleStartIndex)
/*     */   {
/* 837 */     int stopTokenIndex = this.state.failed ? -2 : input.index() - 1;
/* 838 */     if (this.state.ruleMemo == null) {
/* 839 */       System.err.println("!!!!!!!!! memo array is null for " + getGrammarFileName());
/*     */     }
/* 841 */     if (ruleIndex >= this.state.ruleMemo.length) {
/* 842 */       System.err.println("!!!!!!!!! memo size is " + this.state.ruleMemo.length + ", but rule index is " + ruleIndex);
/*     */     }
/* 844 */     if (this.state.ruleMemo[ruleIndex] != null)
/* 845 */       this.state.ruleMemo[ruleIndex].put(new Integer(ruleStartIndex), new Integer(stopTokenIndex));
/*     */   }
/*     */ 
/*     */   public int getRuleMemoizationCacheSize()
/*     */   {
/* 855 */     int n = 0;
/* 856 */     for (int i = 0; (this.state.ruleMemo != null) && (i < this.state.ruleMemo.length); i++) {
/* 857 */       Map ruleMap = this.state.ruleMemo[i];
/* 858 */       if (ruleMap != null) {
/* 859 */         n += ruleMap.size();
/*     */       }
/*     */     }
/* 862 */     return n;
/*     */   }
/*     */ 
/*     */   public void traceIn(String ruleName, int ruleIndex, Object inputSymbol) {
/* 866 */     System.out.print("enter " + ruleName + " " + inputSymbol);
/* 867 */     if (this.state.backtracking > 0) {
/* 868 */       System.out.print(" backtracking=" + this.state.backtracking);
/*     */     }
/* 870 */     System.out.println();
/*     */   }
/*     */ 
/*     */   public void traceOut(String ruleName, int ruleIndex, Object inputSymbol)
/*     */   {
/* 877 */     System.out.print("exit " + ruleName + " " + inputSymbol);
/* 878 */     if (this.state.backtracking > 0) {
/* 879 */       System.out.print(" backtracking=" + this.state.backtracking);
/* 880 */       if (this.state.failed) System.out.print(" failed"); else
/* 881 */         System.out.print(" succeeded");
/*     */     }
/* 883 */     System.out.println();
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.antlr.runtime.BaseRecognizer
 * JD-Core Version:    0.6.2
 */