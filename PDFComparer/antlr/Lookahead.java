/*     */ package antlr;
/*     */ 
/*     */ import antlr.collections.impl.BitSet;
/*     */ import antlr.collections.impl.Vector;
/*     */ 
/*     */ public class Lookahead
/*     */   implements Cloneable
/*     */ {
/*     */   BitSet fset;
/*     */   String cycle;
/*     */   BitSet epsilonDepth;
/*  79 */   boolean hasEpsilon = false;
/*     */ 
/*     */   public Lookahead() {
/*  82 */     this.fset = new BitSet();
/*     */   }
/*     */ 
/*     */   public Lookahead(BitSet paramBitSet)
/*     */   {
/*  87 */     this.fset = paramBitSet;
/*     */   }
/*     */ 
/*     */   public Lookahead(String paramString)
/*     */   {
/*  92 */     this();
/*  93 */     this.cycle = paramString;
/*     */   }
/*     */ 
/*     */   public Object clone()
/*     */   {
/*  98 */     Lookahead localLookahead = null;
/*     */     try {
/* 100 */       localLookahead = (Lookahead)super.clone();
/* 101 */       localLookahead.fset = ((BitSet)this.fset.clone());
/* 102 */       localLookahead.cycle = this.cycle;
/* 103 */       if (this.epsilonDepth != null)
/* 104 */         localLookahead.epsilonDepth = ((BitSet)this.epsilonDepth.clone());
/*     */     }
/*     */     catch (CloneNotSupportedException localCloneNotSupportedException)
/*     */     {
/* 108 */       throw new InternalError();
/*     */     }
/* 110 */     return localLookahead;
/*     */   }
/*     */ 
/*     */   public void combineWith(Lookahead paramLookahead) {
/* 114 */     if (this.cycle == null) {
/* 115 */       this.cycle = paramLookahead.cycle;
/*     */     }
/*     */ 
/* 118 */     if (paramLookahead.containsEpsilon()) {
/* 119 */       this.hasEpsilon = true;
/*     */     }
/*     */ 
/* 123 */     if (this.epsilonDepth != null) {
/* 124 */       if (paramLookahead.epsilonDepth != null) {
/* 125 */         this.epsilonDepth.orInPlace(paramLookahead.epsilonDepth);
/*     */       }
/*     */     }
/* 128 */     else if (paramLookahead.epsilonDepth != null) {
/* 129 */       this.epsilonDepth = ((BitSet)paramLookahead.epsilonDepth.clone());
/*     */     }
/* 131 */     this.fset.orInPlace(paramLookahead.fset);
/*     */   }
/*     */ 
/*     */   public boolean containsEpsilon() {
/* 135 */     return this.hasEpsilon;
/*     */   }
/*     */ 
/*     */   public Lookahead intersection(Lookahead paramLookahead)
/*     */   {
/* 142 */     Lookahead localLookahead = new Lookahead(this.fset.and(paramLookahead.fset));
/* 143 */     if ((this.hasEpsilon) && (paramLookahead.hasEpsilon)) {
/* 144 */       localLookahead.setEpsilon();
/*     */     }
/* 146 */     return localLookahead;
/*     */   }
/*     */ 
/*     */   public boolean nil() {
/* 150 */     return (this.fset.nil()) && (!this.hasEpsilon);
/*     */   }
/*     */ 
/*     */   public static Lookahead of(int paramInt) {
/* 154 */     Lookahead localLookahead = new Lookahead();
/* 155 */     localLookahead.fset.add(paramInt);
/* 156 */     return localLookahead;
/*     */   }
/*     */ 
/*     */   public void resetEpsilon() {
/* 160 */     this.hasEpsilon = false;
/*     */   }
/*     */ 
/*     */   public void setEpsilon() {
/* 164 */     this.hasEpsilon = true;
/*     */   }
/*     */ 
/*     */   public String toString() {
/* 168 */     String str1 = ""; String str3 = ""; String str4 = "";
/* 169 */     String str2 = this.fset.toString(",");
/* 170 */     if (containsEpsilon()) {
/* 171 */       str1 = "+<epsilon>";
/*     */     }
/* 173 */     if (this.cycle != null) {
/* 174 */       str3 = "; FOLLOW(" + this.cycle + ")";
/*     */     }
/* 176 */     if (this.epsilonDepth != null) {
/* 177 */       str4 = "; depths=" + this.epsilonDepth.toString(",");
/*     */     }
/* 179 */     return str2 + str1 + str3 + str4;
/*     */   }
/*     */ 
/*     */   public String toString(String paramString, CharFormatter paramCharFormatter)
/*     */   {
/* 184 */     String str1 = ""; String str3 = ""; String str4 = "";
/* 185 */     String str2 = this.fset.toString(paramString, paramCharFormatter);
/* 186 */     if (containsEpsilon()) {
/* 187 */       str1 = "+<epsilon>";
/*     */     }
/* 189 */     if (this.cycle != null) {
/* 190 */       str3 = "; FOLLOW(" + this.cycle + ")";
/*     */     }
/* 192 */     if (this.epsilonDepth != null) {
/* 193 */       str4 = "; depths=" + this.epsilonDepth.toString(",");
/*     */     }
/* 195 */     return str2 + str1 + str3 + str4;
/*     */   }
/*     */ 
/*     */   public String toString(String paramString, CharFormatter paramCharFormatter, Grammar paramGrammar) {
/* 199 */     if ((paramGrammar instanceof LexerGrammar)) {
/* 200 */       return toString(paramString, paramCharFormatter);
/*     */     }
/*     */ 
/* 203 */     return toString(paramString, paramGrammar.tokenManager.getVocabulary());
/*     */   }
/*     */ 
/*     */   public String toString(String paramString, Vector paramVector)
/*     */   {
/* 208 */     String str2 = ""; String str3 = "";
/* 209 */     String str1 = this.fset.toString(paramString, paramVector);
/* 210 */     if (this.cycle != null) {
/* 211 */       str2 = "; FOLLOW(" + this.cycle + ")";
/*     */     }
/* 213 */     if (this.epsilonDepth != null) {
/* 214 */       str3 = "; depths=" + this.epsilonDepth.toString(",");
/*     */     }
/* 216 */     return str1 + str2 + str3;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     antlr.Lookahead
 * JD-Core Version:    0.6.2
 */