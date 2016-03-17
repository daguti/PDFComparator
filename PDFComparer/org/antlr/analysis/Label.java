/*     */ package org.antlr.analysis;
/*     */ 
/*     */ import org.antlr.misc.IntSet;
/*     */ import org.antlr.misc.IntervalSet;
/*     */ import org.antlr.tool.Grammar;
/*     */ 
/*     */ public class Label
/*     */   implements Comparable, Cloneable
/*     */ {
/*     */   public static final int INVALID = -7;
/*     */   public static final int ACTION = -6;
/*     */   public static final int EPSILON = -5;
/*     */   public static final String EPSILON_STR = "<EPSILON>";
/*     */   public static final int SEMPRED = -4;
/*     */   public static final int SET = -3;
/*     */   public static final int EOT = -2;
/*     */   public static final int EOF = -1;
/*     */   public static final int NUM_FAUX_LABELS = 7;
/*     */   public static final int MIN_ATOM_VALUE = -2;
/*     */   public static final int MIN_CHAR_VALUE = 0;
/*     */   public static final int MAX_CHAR_VALUE = 65535;
/*     */   public static final int EOR_TOKEN_TYPE = 1;
/*     */   public static final int DOWN = 2;
/*     */   public static final int UP = 3;
/*     */   public static final int MIN_TOKEN_TYPE = 4;
/*     */   protected int label;
/*     */   protected IntSet labelSet;
/*     */ 
/*     */   public Label(int label)
/*     */   {
/* 125 */     this.label = label;
/*     */   }
/*     */ 
/*     */   public Label(IntSet labelSet)
/*     */   {
/* 130 */     if (labelSet == null) {
/* 131 */       this.label = -3;
/* 132 */       this.labelSet = IntervalSet.of(-7);
/* 133 */       return;
/*     */     }
/* 135 */     int singleAtom = labelSet.getSingleElement();
/* 136 */     if (singleAtom != -7)
/*     */     {
/* 138 */       this.label = singleAtom;
/* 139 */       return;
/*     */     }
/* 141 */     this.label = -3;
/* 142 */     this.labelSet = labelSet;
/*     */   }
/*     */ 
/*     */   public Object clone() {
/*     */     Label l;
/*     */     try {
/* 148 */       l = (Label)super.clone();
/* 149 */       l.label = this.label;
/* 150 */       l.labelSet = new IntervalSet();
/* 151 */       l.labelSet.addAll(this.labelSet);
/*     */     }
/*     */     catch (CloneNotSupportedException e) {
/* 154 */       throw new InternalError();
/*     */     }
/* 156 */     return l;
/*     */   }
/*     */ 
/*     */   public void add(Label a) {
/* 160 */     if (isAtom()) {
/* 161 */       this.labelSet = IntervalSet.of(this.label);
/* 162 */       this.label = -3;
/* 163 */       if (a.isAtom()) {
/* 164 */         this.labelSet.add(a.getAtom());
/*     */       }
/* 166 */       else if (a.isSet()) {
/* 167 */         this.labelSet.addAll(a.getSet());
/*     */       }
/*     */       else {
/* 170 */         throw new IllegalStateException("can't add element to Label of type " + this.label);
/*     */       }
/* 172 */       return;
/*     */     }
/* 174 */     if (isSet()) {
/* 175 */       if (a.isAtom()) {
/* 176 */         this.labelSet.add(a.getAtom());
/*     */       }
/* 178 */       else if (a.isSet()) {
/* 179 */         this.labelSet.addAll(a.getSet());
/*     */       }
/*     */       else {
/* 182 */         throw new IllegalStateException("can't add element to Label of type " + this.label);
/*     */       }
/* 184 */       return;
/*     */     }
/* 186 */     throw new IllegalStateException("can't add element to Label of type " + this.label);
/*     */   }
/*     */ 
/*     */   public boolean isAtom() {
/* 190 */     return this.label >= -2;
/*     */   }
/*     */ 
/*     */   public boolean isEpsilon() {
/* 194 */     return this.label == -5;
/*     */   }
/*     */ 
/*     */   public boolean isSemanticPredicate() {
/* 198 */     return false;
/*     */   }
/*     */ 
/*     */   public boolean isAction() {
/* 202 */     return false;
/*     */   }
/*     */ 
/*     */   public boolean isSet() {
/* 206 */     return this.label == -3;
/*     */   }
/*     */ 
/*     */   public int getAtom()
/*     */   {
/* 211 */     if (isAtom()) {
/* 212 */       return this.label;
/*     */     }
/* 214 */     return -7;
/*     */   }
/*     */ 
/*     */   public IntSet getSet() {
/* 218 */     if (this.label != -3)
/*     */     {
/* 220 */       return IntervalSet.of(this.label);
/*     */     }
/* 222 */     return this.labelSet;
/*     */   }
/*     */ 
/*     */   public void setSet(IntSet set) {
/* 226 */     this.label = -3;
/* 227 */     this.labelSet = set;
/*     */   }
/*     */ 
/*     */   public SemanticContext getSemanticContext() {
/* 231 */     return null;
/*     */   }
/*     */ 
/*     */   public boolean matches(int atom) {
/* 235 */     if (this.label == atom) {
/* 236 */       return true;
/*     */     }
/* 238 */     if (isSet()) {
/* 239 */       return this.labelSet.member(atom);
/*     */     }
/* 241 */     return false;
/*     */   }
/*     */ 
/*     */   public boolean matches(IntSet set) {
/* 245 */     if (isAtom()) {
/* 246 */       return set.member(getAtom());
/*     */     }
/* 248 */     if (isSet())
/*     */     {
/* 250 */       return !getSet().and(set).isNil();
/*     */     }
/* 252 */     return false;
/*     */   }
/*     */ 
/*     */   public boolean matches(Label other)
/*     */   {
/* 257 */     if (other.isSet()) {
/* 258 */       return matches(other.getSet());
/*     */     }
/* 260 */     if (other.isAtom()) {
/* 261 */       return matches(other.getAtom());
/*     */     }
/* 263 */     return false;
/*     */   }
/*     */ 
/*     */   public int hashCode() {
/* 267 */     if (this.label == -3) {
/* 268 */       return this.labelSet.hashCode();
/*     */     }
/*     */ 
/* 271 */     return this.label;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object o)
/*     */   {
/* 277 */     if (o == null) {
/* 278 */       return false;
/*     */     }
/* 280 */     if (this == o) {
/* 281 */       return true;
/*     */     }
/*     */ 
/* 284 */     if (this.label != ((Label)o).label) {
/* 285 */       return false;
/*     */     }
/* 287 */     if (this.label == -3) {
/* 288 */       return this.labelSet.equals(((Label)o).labelSet);
/*     */     }
/* 290 */     return true;
/*     */   }
/*     */ 
/*     */   public int compareTo(Object o) {
/* 294 */     return this.label - ((Label)o).label;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 326 */     switch (this.label) {
/*     */     case -3:
/* 328 */       return this.labelSet.toString();
/*     */     }
/* 330 */     return String.valueOf(this.label);
/*     */   }
/*     */ 
/*     */   public String toString(Grammar g)
/*     */   {
/* 335 */     switch (this.label) {
/*     */     case -3:
/* 337 */       return this.labelSet.toString(g);
/*     */     }
/* 339 */     return g.getTokenDisplayName(this.label);
/*     */   }
/*     */ 
/*     */   public static boolean intersect(Label label, Label edgeLabel)
/*     */   {
/* 362 */     boolean hasIntersection = false;
/* 363 */     boolean labelIsSet = label.isSet();
/* 364 */     boolean edgeIsSet = edgeLabel.isSet();
/* 365 */     if ((!labelIsSet) && (!edgeIsSet) && (edgeLabel.label == label.label)) {
/* 366 */       hasIntersection = true;
/*     */     }
/* 368 */     else if ((labelIsSet) && (edgeIsSet) && (!edgeLabel.getSet().and(label.getSet()).isNil()))
/*     */     {
/* 370 */       hasIntersection = true;
/*     */     }
/* 372 */     else if ((labelIsSet) && (!edgeIsSet) && (label.getSet().member(edgeLabel.label)))
/*     */     {
/* 374 */       hasIntersection = true;
/*     */     }
/* 376 */     else if ((!labelIsSet) && (edgeIsSet) && (edgeLabel.getSet().member(label.label)))
/*     */     {
/* 378 */       hasIntersection = true;
/*     */     }
/* 380 */     return hasIntersection;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.antlr.analysis.Label
 * JD-Core Version:    0.6.2
 */