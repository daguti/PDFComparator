/*     */ package antlr;
/*     */ 
/*     */ import antlr.collections.impl.BitSet;
/*     */ 
/*     */ public class MismatchedCharException extends RecognitionException
/*     */ {
/*     */   public static final int CHAR = 1;
/*     */   public static final int NOT_CHAR = 2;
/*     */   public static final int RANGE = 3;
/*     */   public static final int NOT_RANGE = 4;
/*     */   public static final int SET = 5;
/*     */   public static final int NOT_SET = 6;
/*     */   public int mismatchType;
/*     */   public int foundChar;
/*     */   public int expecting;
/*     */   public int upper;
/*     */   public BitSet set;
/*     */   public CharScanner scanner;
/*     */ 
/*     */   public MismatchedCharException()
/*     */   {
/*  43 */     super("Mismatched char");
/*     */   }
/*     */ 
/*     */   public MismatchedCharException(char paramChar1, char paramChar2, char paramChar3, boolean paramBoolean, CharScanner paramCharScanner)
/*     */   {
/*  48 */     super("Mismatched char", paramCharScanner.getFilename(), paramCharScanner.getLine(), paramCharScanner.getColumn());
/*  49 */     this.mismatchType = (paramBoolean ? 4 : 3);
/*  50 */     this.foundChar = paramChar1;
/*  51 */     this.expecting = paramChar2;
/*  52 */     this.upper = paramChar3;
/*  53 */     this.scanner = paramCharScanner;
/*     */   }
/*     */ 
/*     */   public MismatchedCharException(char paramChar1, char paramChar2, boolean paramBoolean, CharScanner paramCharScanner)
/*     */   {
/*  58 */     super("Mismatched char", paramCharScanner.getFilename(), paramCharScanner.getLine(), paramCharScanner.getColumn());
/*  59 */     this.mismatchType = (paramBoolean ? 2 : 1);
/*  60 */     this.foundChar = paramChar1;
/*  61 */     this.expecting = paramChar2;
/*  62 */     this.scanner = paramCharScanner;
/*     */   }
/*     */ 
/*     */   public MismatchedCharException(char paramChar, BitSet paramBitSet, boolean paramBoolean, CharScanner paramCharScanner)
/*     */   {
/*  67 */     super("Mismatched char", paramCharScanner.getFilename(), paramCharScanner.getLine(), paramCharScanner.getColumn());
/*  68 */     this.mismatchType = (paramBoolean ? 6 : 5);
/*  69 */     this.foundChar = paramChar;
/*  70 */     this.set = paramBitSet;
/*  71 */     this.scanner = paramCharScanner;
/*     */   }
/*     */ 
/*     */   public String getMessage()
/*     */   {
/*  78 */     StringBuffer localStringBuffer = new StringBuffer();
/*     */ 
/*  80 */     switch (this.mismatchType) {
/*     */     case 1:
/*  82 */       localStringBuffer.append("expecting "); appendCharName(localStringBuffer, this.expecting);
/*  83 */       localStringBuffer.append(", found "); appendCharName(localStringBuffer, this.foundChar);
/*  84 */       break;
/*     */     case 2:
/*  86 */       localStringBuffer.append("expecting anything but '");
/*  87 */       appendCharName(localStringBuffer, this.expecting);
/*  88 */       localStringBuffer.append("'; got it anyway");
/*  89 */       break;
/*     */     case 3:
/*     */     case 4:
/*  92 */       localStringBuffer.append("expecting token ");
/*  93 */       if (this.mismatchType == 4)
/*  94 */         localStringBuffer.append("NOT ");
/*  95 */       localStringBuffer.append("in range: ");
/*  96 */       appendCharName(localStringBuffer, this.expecting);
/*  97 */       localStringBuffer.append("..");
/*  98 */       appendCharName(localStringBuffer, this.upper);
/*  99 */       localStringBuffer.append(", found ");
/* 100 */       appendCharName(localStringBuffer, this.foundChar);
/* 101 */       break;
/*     */     case 5:
/*     */     case 6:
/* 104 */       localStringBuffer.append("expecting " + (this.mismatchType == 6 ? "NOT " : "") + "one of (");
/* 105 */       int[] arrayOfInt = this.set.toArray();
/* 106 */       for (int i = 0; i < arrayOfInt.length; i++) {
/* 107 */         appendCharName(localStringBuffer, arrayOfInt[i]);
/*     */       }
/* 109 */       localStringBuffer.append("), found ");
/* 110 */       appendCharName(localStringBuffer, this.foundChar);
/* 111 */       break;
/*     */     default:
/* 113 */       localStringBuffer.append(super.getMessage());
/*     */     }
/*     */ 
/* 117 */     return localStringBuffer.toString();
/*     */   }
/*     */ 
/*     */   private void appendCharName(StringBuffer paramStringBuffer, int paramInt)
/*     */   {
/* 124 */     switch (paramInt)
/*     */     {
/*     */     case 65535:
/* 127 */       paramStringBuffer.append("'<EOF>'");
/* 128 */       break;
/*     */     case 10:
/* 130 */       paramStringBuffer.append("'\\n'");
/* 131 */       break;
/*     */     case 13:
/* 133 */       paramStringBuffer.append("'\\r'");
/* 134 */       break;
/*     */     case 9:
/* 136 */       paramStringBuffer.append("'\\t'");
/* 137 */       break;
/*     */     default:
/* 139 */       paramStringBuffer.append('\'');
/* 140 */       paramStringBuffer.append((char)paramInt);
/* 141 */       paramStringBuffer.append('\'');
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     antlr.MismatchedCharException
 * JD-Core Version:    0.6.2
 */