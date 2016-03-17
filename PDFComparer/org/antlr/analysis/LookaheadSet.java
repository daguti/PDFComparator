/*     */ package org.antlr.analysis;
/*     */ 
/*     */ import org.antlr.misc.IntSet;
/*     */ import org.antlr.misc.IntervalSet;
/*     */ import org.antlr.tool.Grammar;
/*     */ 
/*     */ public class LookaheadSet
/*     */ {
/*     */   public IntervalSet tokenTypeSet;
/*     */ 
/*     */   public LookaheadSet()
/*     */   {
/*  43 */     this.tokenTypeSet = new IntervalSet();
/*     */   }
/*     */ 
/*     */   public LookaheadSet(IntSet s) {
/*  47 */     this();
/*  48 */     this.tokenTypeSet.addAll(s);
/*     */   }
/*     */ 
/*     */   public LookaheadSet(int atom) {
/*  52 */     this.tokenTypeSet = IntervalSet.of(atom);
/*     */   }
/*     */ 
/*     */   public LookaheadSet(LookaheadSet other) {
/*  56 */     this();
/*  57 */     this.tokenTypeSet.addAll(other.tokenTypeSet);
/*     */   }
/*     */ 
/*     */   public void orInPlace(LookaheadSet other) {
/*  61 */     this.tokenTypeSet.addAll(other.tokenTypeSet);
/*     */   }
/*     */ 
/*     */   public LookaheadSet or(LookaheadSet other) {
/*  65 */     return new LookaheadSet(this.tokenTypeSet.or(other.tokenTypeSet));
/*     */   }
/*     */ 
/*     */   public LookaheadSet subtract(LookaheadSet other) {
/*  69 */     return new LookaheadSet(this.tokenTypeSet.subtract(other.tokenTypeSet));
/*     */   }
/*     */ 
/*     */   public boolean member(int a) {
/*  73 */     return this.tokenTypeSet.member(a);
/*     */   }
/*     */ 
/*     */   public LookaheadSet intersection(LookaheadSet s) {
/*  77 */     IntSet i = this.tokenTypeSet.and(s.tokenTypeSet);
/*  78 */     LookaheadSet intersection = new LookaheadSet(i);
/*  79 */     return intersection;
/*     */   }
/*     */ 
/*     */   public boolean isNil() {
/*  83 */     return this.tokenTypeSet.isNil();
/*     */   }
/*     */ 
/*     */   public void remove(int a) {
/*  87 */     this.tokenTypeSet = ((IntervalSet)this.tokenTypeSet.subtract(IntervalSet.of(a)));
/*     */   }
/*     */ 
/*     */   public int hashCode() {
/*  91 */     return this.tokenTypeSet.hashCode();
/*     */   }
/*     */ 
/*     */   public boolean equals(Object other) {
/*  95 */     return this.tokenTypeSet.equals(((LookaheadSet)other).tokenTypeSet);
/*     */   }
/*     */ 
/*     */   public String toString(Grammar g) {
/*  99 */     if (this.tokenTypeSet == null) {
/* 100 */       return "";
/*     */     }
/* 102 */     String r = this.tokenTypeSet.toString(g);
/* 103 */     return r;
/*     */   }
/*     */ 
/*     */   public String toString() {
/* 107 */     return toString(null);
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.antlr.analysis.LookaheadSet
 * JD-Core Version:    0.6.2
 */