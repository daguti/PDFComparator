/*     */ package antlr;
/*     */ 
/*     */ import antlr.collections.AST;
/*     */ import antlr.collections.impl.BitSet;
/*     */ import java.io.PrintStream;
/*     */ 
/*     */ public class TreeParser
/*     */ {
/*  19 */   public static ASTNULLType ASTNULL = new ASTNULLType();
/*     */   protected AST _retTree;
/*     */   protected TreeParserSharedInputState inputState;
/*     */   protected String[] tokenNames;
/*     */   protected AST returnAST;
/*  39 */   protected ASTFactory astFactory = new ASTFactory();
/*     */ 
/*  42 */   protected int traceDepth = 0;
/*     */ 
/*     */   public TreeParser() {
/*  45 */     this.inputState = new TreeParserSharedInputState();
/*     */   }
/*     */ 
/*     */   public AST getAST()
/*     */   {
/*  50 */     return this.returnAST;
/*     */   }
/*     */ 
/*     */   public ASTFactory getASTFactory() {
/*  54 */     return this.astFactory;
/*     */   }
/*     */ 
/*     */   public String getTokenName(int paramInt) {
/*  58 */     return this.tokenNames[paramInt];
/*     */   }
/*     */ 
/*     */   public String[] getTokenNames() {
/*  62 */     return this.tokenNames;
/*     */   }
/*     */ 
/*     */   protected void match(AST paramAST, int paramInt) throws MismatchedTokenException
/*     */   {
/*  67 */     if ((paramAST == null) || (paramAST == ASTNULL) || (paramAST.getType() != paramInt))
/*  68 */       throw new MismatchedTokenException(getTokenNames(), paramAST, paramInt, false);
/*     */   }
/*     */ 
/*     */   public void match(AST paramAST, BitSet paramBitSet)
/*     */     throws MismatchedTokenException
/*     */   {
/*  77 */     if ((paramAST == null) || (paramAST == ASTNULL) || (!paramBitSet.member(paramAST.getType())))
/*  78 */       throw new MismatchedTokenException(getTokenNames(), paramAST, paramBitSet, false);
/*     */   }
/*     */ 
/*     */   protected void matchNot(AST paramAST, int paramInt)
/*     */     throws MismatchedTokenException
/*     */   {
/*  84 */     if ((paramAST == null) || (paramAST == ASTNULL) || (paramAST.getType() == paramInt))
/*  85 */       throw new MismatchedTokenException(getTokenNames(), paramAST, paramInt, true);
/*     */   }
/*     */ 
/*     */   /** @deprecated */
/*     */   public static void panic()
/*     */   {
/*  96 */     System.err.println("TreeWalker: panic");
/*  97 */     Utils.error("");
/*     */   }
/*     */ 
/*     */   public void reportError(RecognitionException paramRecognitionException)
/*     */   {
/* 102 */     System.err.println(paramRecognitionException.toString());
/*     */   }
/*     */ 
/*     */   public void reportError(String paramString)
/*     */   {
/* 107 */     System.err.println("error: " + paramString);
/*     */   }
/*     */ 
/*     */   public void reportWarning(String paramString)
/*     */   {
/* 112 */     System.err.println("warning: " + paramString);
/*     */   }
/*     */ 
/*     */   public void setASTFactory(ASTFactory paramASTFactory)
/*     */   {
/* 120 */     this.astFactory = paramASTFactory;
/*     */   }
/*     */ 
/*     */   /** @deprecated */
/*     */   public void setASTNodeType(String paramString)
/*     */   {
/* 127 */     setASTNodeClass(paramString);
/*     */   }
/*     */ 
/*     */   public void setASTNodeClass(String paramString)
/*     */   {
/* 132 */     this.astFactory.setASTNodeType(paramString);
/*     */   }
/*     */ 
/*     */   public void traceIndent() {
/* 136 */     for (int i = 0; i < this.traceDepth; i++)
/* 137 */       System.out.print(" ");
/*     */   }
/*     */ 
/*     */   public void traceIn(String paramString, AST paramAST) {
/* 141 */     this.traceDepth += 1;
/* 142 */     traceIndent();
/* 143 */     System.out.println("> " + paramString + "(" + (paramAST != null ? paramAST.toString() : "null") + ")" + (this.inputState.guessing > 0 ? " [guessing]" : ""));
/*     */   }
/*     */ 
/*     */   public void traceOut(String paramString, AST paramAST)
/*     */   {
/* 149 */     traceIndent();
/* 150 */     System.out.println("< " + paramString + "(" + (paramAST != null ? paramAST.toString() : "null") + ")" + (this.inputState.guessing > 0 ? " [guessing]" : ""));
/*     */ 
/* 153 */     this.traceDepth -= 1;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     antlr.TreeParser
 * JD-Core Version:    0.6.2
 */