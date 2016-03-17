/*     */ package org.antlr.misc;
/*     */ 
/*     */ public class Interval
/*     */ {
/*     */   public static final int INTERVAL_POOL_MAX_VALUE = 1000;
/*  34 */   static Interval[] cache = new Interval[1001];
/*     */   public int a;
/*     */   public int b;
/*  39 */   public static int creates = 0;
/*  40 */   public static int misses = 0;
/*  41 */   public static int hits = 0;
/*  42 */   public static int outOfRange = 0;
/*     */ 
/*  44 */   public Interval(int a, int b) { this.a = a; this.b = b;
/*     */   }
/*     */ 
/*     */   public static Interval create(int a, int b)
/*     */   {
/*  55 */     if ((a != b) || (a < 0) || (a > 1000)) {
/*  56 */       return new Interval(a, b);
/*     */     }
/*  58 */     if (cache[a] == null) {
/*  59 */       cache[a] = new Interval(a, a);
/*     */     }
/*  61 */     return cache[a];
/*     */   }
/*     */ 
/*     */   public boolean equals(Object o) {
/*  65 */     if (o == null) {
/*  66 */       return false;
/*     */     }
/*  68 */     Interval other = (Interval)o;
/*  69 */     return (this.a == other.a) && (this.b == other.b);
/*     */   }
/*     */ 
/*     */   public boolean startsBeforeDisjoint(Interval other)
/*     */   {
/*  74 */     return (this.a < other.a) && (this.b < other.a);
/*     */   }
/*     */ 
/*     */   public boolean startsBeforeNonDisjoint(Interval other)
/*     */   {
/*  79 */     return (this.a <= other.a) && (this.b >= other.a);
/*     */   }
/*     */ 
/*     */   public boolean startsAfter(Interval other) {
/*  83 */     return this.a > other.a;
/*     */   }
/*     */ 
/*     */   public boolean startsAfterDisjoint(Interval other) {
/*  87 */     return this.a > other.b;
/*     */   }
/*     */ 
/*     */   public boolean startsAfterNonDisjoint(Interval other)
/*     */   {
/*  92 */     return (this.a > other.a) && (this.a <= other.b);
/*     */   }
/*     */ 
/*     */   public boolean disjoint(Interval other)
/*     */   {
/*  97 */     return (startsBeforeDisjoint(other)) || (startsAfterDisjoint(other));
/*     */   }
/*     */ 
/*     */   public boolean adjacent(Interval other)
/*     */   {
/* 102 */     return (this.a == other.b + 1) || (this.b == other.a - 1);
/*     */   }
/*     */ 
/*     */   public boolean properlyContains(Interval other) {
/* 106 */     return (other.a >= this.a) && (other.b <= this.b);
/*     */   }
/*     */ 
/*     */   public Interval union(Interval other)
/*     */   {
/* 111 */     return create(Math.min(this.a, other.a), Math.max(this.b, other.b));
/*     */   }
/*     */ 
/*     */   public Interval intersection(Interval other)
/*     */   {
/* 116 */     return create(Math.max(this.a, other.a), Math.min(this.b, other.b));
/*     */   }
/*     */ 
/*     */   public Interval differenceNotProperlyContained(Interval other)
/*     */   {
/* 125 */     Interval diff = null;
/*     */ 
/* 127 */     if (other.startsBeforeNonDisjoint(this)) {
/* 128 */       diff = create(Math.max(this.a, other.b + 1), this.b);
/*     */     }
/* 133 */     else if (other.startsAfterNonDisjoint(this)) {
/* 134 */       diff = create(this.a, other.a - 1);
/*     */     }
/* 136 */     return diff;
/*     */   }
/*     */ 
/*     */   public String toString() {
/* 140 */     return this.a + ".." + this.b;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.antlr.misc.Interval
 * JD-Core Version:    0.6.2
 */