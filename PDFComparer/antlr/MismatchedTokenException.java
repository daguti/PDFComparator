/*     */ package antlr;
/*     */ 
/*     */ import antlr.collections.AST;
/*     */ import antlr.collections.impl.BitSet;
/*     */ 
/*     */ public class MismatchedTokenException extends RecognitionException
/*     */ {
/*     */   String[] tokenNames;
/*     */   public Token token;
/*     */   public AST node;
/*  21 */   String tokenText = null;
/*     */   public static final int TOKEN = 1;
/*     */   public static final int NOT_TOKEN = 2;
/*     */   public static final int RANGE = 3;
/*     */   public static final int NOT_RANGE = 4;
/*     */   public static final int SET = 5;
/*     */   public static final int NOT_SET = 6;
/*     */   public int mismatchType;
/*     */   public int expecting;
/*     */   public int upper;
/*     */   public BitSet set;
/*     */ 
/*     */   public MismatchedTokenException()
/*     */   {
/*  44 */     super("Mismatched Token: expecting any AST node", "<AST>", -1, -1);
/*     */   }
/*     */ 
/*     */   public MismatchedTokenException(String[] paramArrayOfString, AST paramAST, int paramInt1, int paramInt2, boolean paramBoolean)
/*     */   {
/*  49 */     super("Mismatched Token", "<AST>", paramAST == null ? -1 : paramAST.getLine(), paramAST == null ? -1 : paramAST.getColumn());
/*  50 */     this.tokenNames = paramArrayOfString;
/*  51 */     this.node = paramAST;
/*  52 */     if (paramAST == null) {
/*  53 */       this.tokenText = "<empty tree>";
/*     */     }
/*     */     else {
/*  56 */       this.tokenText = paramAST.toString();
/*     */     }
/*  58 */     this.mismatchType = (paramBoolean ? 4 : 3);
/*  59 */     this.expecting = paramInt1;
/*  60 */     this.upper = paramInt2;
/*     */   }
/*     */ 
/*     */   public MismatchedTokenException(String[] paramArrayOfString, AST paramAST, int paramInt, boolean paramBoolean)
/*     */   {
/*  65 */     super("Mismatched Token", "<AST>", paramAST == null ? -1 : paramAST.getLine(), paramAST == null ? -1 : paramAST.getColumn());
/*  66 */     this.tokenNames = paramArrayOfString;
/*  67 */     this.node = paramAST;
/*  68 */     if (paramAST == null) {
/*  69 */       this.tokenText = "<empty tree>";
/*     */     }
/*     */     else {
/*  72 */       this.tokenText = paramAST.toString();
/*     */     }
/*  74 */     this.mismatchType = (paramBoolean ? 2 : 1);
/*  75 */     this.expecting = paramInt;
/*     */   }
/*     */ 
/*     */   public MismatchedTokenException(String[] paramArrayOfString, AST paramAST, BitSet paramBitSet, boolean paramBoolean)
/*     */   {
/*  80 */     super("Mismatched Token", "<AST>", paramAST == null ? -1 : paramAST.getLine(), paramAST == null ? -1 : paramAST.getColumn());
/*  81 */     this.tokenNames = paramArrayOfString;
/*  82 */     this.node = paramAST;
/*  83 */     if (paramAST == null) {
/*  84 */       this.tokenText = "<empty tree>";
/*     */     }
/*     */     else {
/*  87 */       this.tokenText = paramAST.toString();
/*     */     }
/*  89 */     this.mismatchType = (paramBoolean ? 6 : 5);
/*  90 */     this.set = paramBitSet;
/*     */   }
/*     */ 
/*     */   public MismatchedTokenException(String[] paramArrayOfString, Token paramToken, int paramInt1, int paramInt2, boolean paramBoolean, String paramString)
/*     */   {
/*  95 */     super("Mismatched Token", paramString, paramToken.getLine(), paramToken.getColumn());
/*  96 */     this.tokenNames = paramArrayOfString;
/*  97 */     this.token = paramToken;
/*  98 */     this.tokenText = paramToken.getText();
/*  99 */     this.mismatchType = (paramBoolean ? 4 : 3);
/* 100 */     this.expecting = paramInt1;
/* 101 */     this.upper = paramInt2;
/*     */   }
/*     */ 
/*     */   public MismatchedTokenException(String[] paramArrayOfString, Token paramToken, int paramInt, boolean paramBoolean, String paramString)
/*     */   {
/* 106 */     super("Mismatched Token", paramString, paramToken.getLine(), paramToken.getColumn());
/* 107 */     this.tokenNames = paramArrayOfString;
/* 108 */     this.token = paramToken;
/* 109 */     this.tokenText = paramToken.getText();
/* 110 */     this.mismatchType = (paramBoolean ? 2 : 1);
/* 111 */     this.expecting = paramInt;
/*     */   }
/*     */ 
/*     */   public MismatchedTokenException(String[] paramArrayOfString, Token paramToken, BitSet paramBitSet, boolean paramBoolean, String paramString)
/*     */   {
/* 116 */     super("Mismatched Token", paramString, paramToken.getLine(), paramToken.getColumn());
/* 117 */     this.tokenNames = paramArrayOfString;
/* 118 */     this.token = paramToken;
/* 119 */     this.tokenText = paramToken.getText();
/* 120 */     this.mismatchType = (paramBoolean ? 6 : 5);
/* 121 */     this.set = paramBitSet;
/*     */   }
/*     */ 
/*     */   public String getMessage()
/*     */   {
/* 128 */     StringBuffer localStringBuffer = new StringBuffer();
/*     */ 
/* 130 */     switch (this.mismatchType) {
/*     */     case 1:
/* 132 */       localStringBuffer.append("expecting " + tokenName(this.expecting) + ", found '" + this.tokenText + "'");
/* 133 */       break;
/*     */     case 2:
/* 135 */       localStringBuffer.append("expecting anything but " + tokenName(this.expecting) + "; got it anyway");
/* 136 */       break;
/*     */     case 3:
/* 138 */       localStringBuffer.append("expecting token in range: " + tokenName(this.expecting) + ".." + tokenName(this.upper) + ", found '" + this.tokenText + "'");
/* 139 */       break;
/*     */     case 4:
/* 141 */       localStringBuffer.append("expecting token NOT in range: " + tokenName(this.expecting) + ".." + tokenName(this.upper) + ", found '" + this.tokenText + "'");
/* 142 */       break;
/*     */     case 5:
/*     */     case 6:
/* 145 */       localStringBuffer.append("expecting " + (this.mismatchType == 6 ? "NOT " : "") + "one of (");
/* 146 */       int[] arrayOfInt = this.set.toArray();
/* 147 */       for (int i = 0; i < arrayOfInt.length; i++) {
/* 148 */         localStringBuffer.append(" ");
/* 149 */         localStringBuffer.append(tokenName(arrayOfInt[i]));
/*     */       }
/* 151 */       localStringBuffer.append("), found '" + this.tokenText + "'");
/* 152 */       break;
/*     */     default:
/* 154 */       localStringBuffer.append(super.getMessage());
/*     */     }
/*     */ 
/* 158 */     return localStringBuffer.toString();
/*     */   }
/*     */ 
/*     */   private String tokenName(int paramInt) {
/* 162 */     if (paramInt == 0) {
/* 163 */       return "<Set of tokens>";
/*     */     }
/* 165 */     if ((paramInt < 0) || (paramInt >= this.tokenNames.length)) {
/* 166 */       return "<" + String.valueOf(paramInt) + ">";
/*     */     }
/*     */ 
/* 169 */     return this.tokenNames[paramInt];
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     antlr.MismatchedTokenException
 * JD-Core Version:    0.6.2
 */