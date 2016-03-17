/*     */ package org.antlr.runtime.tree;
/*     */ 
/*     */ import java.util.regex.Pattern;
/*     */ import org.antlr.runtime.BaseRecognizer;
/*     */ import org.antlr.runtime.BitSet;
/*     */ import org.antlr.runtime.CommonToken;
/*     */ import org.antlr.runtime.IntStream;
/*     */ import org.antlr.runtime.MismatchedTreeNodeException;
/*     */ import org.antlr.runtime.RecognitionException;
/*     */ import org.antlr.runtime.RecognizerSharedState;
/*     */ 
/*     */ public class TreeParser extends BaseRecognizer
/*     */ {
/*     */   public static final int DOWN = 2;
/*     */   public static final int UP = 3;
/*  44 */   static String dotdot = ".*[^.]\\.\\.[^.].*";
/*  45 */   static String doubleEtc = ".*\\.\\.\\.\\s+\\.\\.\\..*";
/*  46 */   static Pattern dotdotPattern = Pattern.compile(dotdot);
/*  47 */   static Pattern doubleEtcPattern = Pattern.compile(doubleEtc);
/*     */   protected TreeNodeStream input;
/*     */ 
/*     */   public TreeParser(TreeNodeStream input)
/*     */   {
/*  53 */     setTreeNodeStream(input);
/*     */   }
/*     */ 
/*     */   public TreeParser(TreeNodeStream input, RecognizerSharedState state) {
/*  57 */     super(state);
/*  58 */     setTreeNodeStream(input);
/*     */   }
/*     */ 
/*     */   public void reset() {
/*  62 */     super.reset();
/*  63 */     if (this.input != null)
/*  64 */       this.input.seek(0);
/*     */   }
/*     */ 
/*     */   public void setTreeNodeStream(TreeNodeStream input)
/*     */   {
/*  70 */     this.input = input;
/*     */   }
/*     */ 
/*     */   public TreeNodeStream getTreeNodeStream() {
/*  74 */     return this.input;
/*     */   }
/*     */ 
/*     */   public String getSourceName() {
/*  78 */     return this.input.getSourceName();
/*     */   }
/*     */ 
/*     */   protected Object getCurrentInputSymbol(IntStream input) {
/*  82 */     return ((TreeNodeStream)input).LT(1);
/*     */   }
/*     */ 
/*     */   protected Object getMissingSymbol(IntStream input, RecognitionException e, int expectedTokenType, BitSet follow)
/*     */   {
/*  90 */     String tokenText = "<missing " + getTokenNames()[expectedTokenType] + ">";
/*     */ 
/*  92 */     TreeAdaptor adaptor = ((TreeNodeStream)e.input).getTreeAdaptor();
/*  93 */     return adaptor.create(new CommonToken(expectedTokenType, tokenText));
/*     */   }
/*     */ 
/*     */   public void matchAny(IntStream ignore)
/*     */   {
/* 101 */     this.state.errorRecovery = false;
/* 102 */     this.state.failed = false;
/* 103 */     Object look = this.input.LT(1);
/* 104 */     if (this.input.getTreeAdaptor().getChildCount(look) == 0) {
/* 105 */       this.input.consume();
/* 106 */       return;
/*     */     }
/*     */ 
/* 110 */     int level = 0;
/* 111 */     int tokenType = this.input.getTreeAdaptor().getType(look);
/* 112 */     while ((tokenType != -1) && ((tokenType != 3) || (level != 0))) {
/* 113 */       this.input.consume();
/* 114 */       look = this.input.LT(1);
/* 115 */       tokenType = this.input.getTreeAdaptor().getType(look);
/* 116 */       if (tokenType == 2) {
/* 117 */         level++;
/*     */       }
/* 119 */       else if (tokenType == 3) {
/* 120 */         level--;
/*     */       }
/*     */     }
/* 123 */     this.input.consume();
/*     */   }
/*     */ 
/*     */   protected Object recoverFromMismatchedToken(IntStream input, int ttype, BitSet follow)
/*     */     throws RecognitionException
/*     */   {
/* 135 */     throw new MismatchedTreeNodeException(ttype, (TreeNodeStream)input);
/*     */   }
/*     */ 
/*     */   public String getErrorHeader(RecognitionException e)
/*     */   {
/* 143 */     return getGrammarFileName() + ": node from " + (e.approximateLineInfo ? "after " : "") + "line " + e.line + ":" + e.charPositionInLine;
/*     */   }
/*     */ 
/*     */   public String getErrorMessage(RecognitionException e, String[] tokenNames)
/*     */   {
/* 151 */     if ((this instanceof TreeParser)) {
/* 152 */       TreeAdaptor adaptor = ((TreeNodeStream)e.input).getTreeAdaptor();
/* 153 */       e.token = adaptor.getToken(e.node);
/* 154 */       if (e.token == null) {
/* 155 */         e.token = new CommonToken(adaptor.getType(e.node), adaptor.getText(e.node));
/*     */       }
/*     */     }
/*     */ 
/* 159 */     return super.getErrorMessage(e, tokenNames);
/*     */   }
/*     */ 
/*     */   public void traceIn(String ruleName, int ruleIndex) {
/* 163 */     super.traceIn(ruleName, ruleIndex, this.input.LT(1));
/*     */   }
/*     */ 
/*     */   public void traceOut(String ruleName, int ruleIndex) {
/* 167 */     super.traceOut(ruleName, ruleIndex, this.input.LT(1));
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.antlr.runtime.tree.TreeParser
 * JD-Core Version:    0.6.2
 */