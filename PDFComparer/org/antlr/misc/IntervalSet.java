/*     */ package org.antlr.misc;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.ListIterator;
/*     */ import org.antlr.runtime.BitSet;
/*     */ import org.antlr.tool.Grammar;
/*     */ 
/*     */ public class IntervalSet
/*     */   implements IntSet
/*     */ {
/*  53 */   public static final IntervalSet COMPLETE_SET = of(0, 65535);
/*     */   protected List<Interval> intervals;
/*     */ 
/*     */   public IntervalSet()
/*     */   {
/*  60 */     this.intervals = new ArrayList(2);
/*     */   }
/*     */ 
/*     */   public IntervalSet(List<Interval> intervals) {
/*  64 */     this.intervals = intervals;
/*     */   }
/*     */ 
/*     */   public static IntervalSet of(int a)
/*     */   {
/*  69 */     IntervalSet s = new IntervalSet();
/*  70 */     s.add(a);
/*  71 */     return s;
/*     */   }
/*     */ 
/*     */   public static IntervalSet of(int a, int b)
/*     */   {
/*  76 */     IntervalSet s = new IntervalSet();
/*  77 */     s.add(a, b);
/*  78 */     return s;
/*     */   }
/*     */ 
/*     */   public void add(int el)
/*     */   {
/*  85 */     add(el, el);
/*     */   }
/*     */ 
/*     */   public void add(int a, int b)
/*     */   {
/*  96 */     add(Interval.create(a, b));
/*     */   }
/*     */ 
/*     */   protected void add(Interval addition)
/*     */   {
/* 102 */     if (addition.b < addition.a) {
/* 103 */       return;
/*     */     }
/*     */ 
/* 107 */     for (ListIterator iter = this.intervals.listIterator(); iter.hasNext(); ) {
/* 108 */       Interval r = (Interval)iter.next();
/* 109 */       if (addition.equals(r)) {
/* 110 */         return;
/*     */       }
/* 112 */       if ((addition.adjacent(r)) || (!addition.disjoint(r)))
/*     */       {
/* 114 */         Interval bigger = addition.union(r);
/* 115 */         iter.set(bigger);
/*     */ 
/* 118 */         if (iter.hasNext()) {
/* 119 */           Interval next = (Interval)iter.next();
/* 120 */           if ((bigger.adjacent(next)) || (!bigger.disjoint(next)))
/*     */           {
/* 122 */             iter.remove();
/* 123 */             iter.previous();
/* 124 */             iter.set(bigger.union(next));
/*     */           }
/*     */         }
/* 127 */         return;
/*     */       }
/* 129 */       if (addition.startsBeforeDisjoint(r))
/*     */       {
/* 131 */         iter.previous();
/* 132 */         iter.add(addition);
/* 133 */         return;
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 139 */     this.intervals.add(addition);
/*     */   }
/*     */ 
/*     */   public void addAll(IntSet set)
/*     */   {
/* 188 */     if (set == null) {
/* 189 */       return;
/*     */     }
/* 191 */     if (!(set instanceof IntervalSet)) {
/* 192 */       throw new IllegalArgumentException("can't add non IntSet (" + set.getClass().getName() + ") to IntervalSet");
/*     */     }
/*     */ 
/* 196 */     IntervalSet other = (IntervalSet)set;
/*     */ 
/* 198 */     int n = other.intervals.size();
/* 199 */     for (int i = 0; i < n; i++) {
/* 200 */       Interval I = (Interval)other.intervals.get(i);
/* 201 */       add(I.a, I.b);
/*     */     }
/*     */   }
/*     */ 
/*     */   public IntSet complement(int minElement, int maxElement) {
/* 206 */     return complement(of(minElement, maxElement));
/*     */   }
/*     */ 
/*     */   public IntSet complement(IntSet vocabulary)
/*     */   {
/* 216 */     if (vocabulary == null) {
/* 217 */       return null;
/*     */     }
/* 219 */     if (!(vocabulary instanceof IntervalSet)) {
/* 220 */       throw new IllegalArgumentException("can't complement with non IntervalSet (" + vocabulary.getClass().getName() + ")");
/*     */     }
/*     */ 
/* 223 */     IntervalSet vocabularyIS = (IntervalSet)vocabulary;
/* 224 */     int maxElement = vocabularyIS.getMaxElement();
/*     */ 
/* 226 */     IntervalSet compl = new IntervalSet();
/* 227 */     int n = this.intervals.size();
/* 228 */     if (n == 0) {
/* 229 */       return compl;
/*     */     }
/* 231 */     Interval first = (Interval)this.intervals.get(0);
/*     */ 
/* 233 */     if (first.a > 0) {
/* 234 */       IntervalSet s = of(0, first.a - 1);
/* 235 */       IntervalSet a = (IntervalSet)s.and(vocabularyIS);
/* 236 */       compl.addAll(a);
/*     */     }
/* 238 */     for (int i = 1; i < n; i++) {
/* 239 */       Interval previous = (Interval)this.intervals.get(i - 1);
/* 240 */       Interval current = (Interval)this.intervals.get(i);
/* 241 */       IntervalSet s = of(previous.b + 1, current.a - 1);
/* 242 */       IntervalSet a = (IntervalSet)s.and(vocabularyIS);
/* 243 */       compl.addAll(a);
/*     */     }
/* 245 */     Interval last = (Interval)this.intervals.get(n - 1);
/*     */ 
/* 247 */     if (last.b < maxElement) {
/* 248 */       IntervalSet s = of(last.b + 1, maxElement);
/* 249 */       IntervalSet a = (IntervalSet)s.and(vocabularyIS);
/* 250 */       compl.addAll(a);
/*     */     }
/* 252 */     return compl;
/*     */   }
/*     */ 
/*     */   public IntSet subtract(IntSet other)
/*     */   {
/* 267 */     return and(((IntervalSet)other).complement(COMPLETE_SET));
/*     */   }
/*     */ 
/*     */   public IntSet or(IntSet a)
/*     */   {
/* 391 */     IntervalSet o = new IntervalSet();
/* 392 */     o.addAll(this);
/* 393 */     o.addAll(a);
/*     */ 
/* 395 */     return o;
/*     */   }
/*     */ 
/*     */   public IntSet and(IntSet other)
/*     */   {
/* 404 */     if (other == null) {
/* 405 */       return null;
/*     */     }
/*     */ 
/* 408 */     ArrayList myIntervals = (ArrayList)this.intervals;
/* 409 */     ArrayList theirIntervals = (ArrayList)((IntervalSet)other).intervals;
/* 410 */     IntervalSet intersection = null;
/* 411 */     int mySize = myIntervals.size();
/* 412 */     int theirSize = theirIntervals.size();
/* 413 */     int i = 0;
/* 414 */     int j = 0;
/*     */ 
/* 416 */     while ((i < mySize) && (j < theirSize)) {
/* 417 */       Interval mine = (Interval)myIntervals.get(i);
/* 418 */       Interval theirs = (Interval)theirIntervals.get(j);
/*     */ 
/* 420 */       if (mine.startsBeforeDisjoint(theirs))
/*     */       {
/* 422 */         i++;
/*     */       }
/* 424 */       else if (theirs.startsBeforeDisjoint(mine))
/*     */       {
/* 426 */         j++;
/*     */       }
/* 428 */       else if (mine.properlyContains(theirs))
/*     */       {
/* 430 */         if (intersection == null) {
/* 431 */           intersection = new IntervalSet();
/*     */         }
/* 433 */         intersection.add(mine.intersection(theirs));
/* 434 */         j++;
/*     */       }
/* 436 */       else if (theirs.properlyContains(mine))
/*     */       {
/* 438 */         if (intersection == null) {
/* 439 */           intersection = new IntervalSet();
/*     */         }
/* 441 */         intersection.add(mine.intersection(theirs));
/* 442 */         i++;
/*     */       }
/* 444 */       else if (!mine.disjoint(theirs))
/*     */       {
/* 446 */         if (intersection == null) {
/* 447 */           intersection = new IntervalSet();
/*     */         }
/* 449 */         intersection.add(mine.intersection(theirs));
/*     */ 
/* 457 */         if (mine.startsAfterNonDisjoint(theirs)) {
/* 458 */           j++;
/*     */         }
/* 460 */         else if (theirs.startsAfterNonDisjoint(mine)) {
/* 461 */           i++;
/*     */         }
/*     */       }
/*     */     }
/* 465 */     if (intersection == null) {
/* 466 */       return new IntervalSet();
/*     */     }
/* 468 */     return intersection;
/*     */   }
/*     */ 
/*     */   public boolean member(int el)
/*     */   {
/* 473 */     int n = this.intervals.size();
/* 474 */     for (int i = 0; i < n; i++) {
/* 475 */       Interval I = (Interval)this.intervals.get(i);
/* 476 */       int a = I.a;
/* 477 */       int b = I.b;
/* 478 */       if (el < a) {
/*     */         break;
/*     */       }
/* 481 */       if ((el >= a) && (el <= b)) {
/* 482 */         return true;
/*     */       }
/*     */     }
/* 485 */     return false;
/*     */   }
/*     */ 
/*     */   public boolean isNil()
/*     */   {
/* 502 */     return (this.intervals == null) || (this.intervals.size() == 0);
/*     */   }
/*     */ 
/*     */   public int getSingleElement()
/*     */   {
/* 507 */     if ((this.intervals != null) && (this.intervals.size() == 1)) {
/* 508 */       Interval I = (Interval)this.intervals.get(0);
/* 509 */       if (I.a == I.b) {
/* 510 */         return I.a;
/*     */       }
/*     */     }
/* 513 */     return -7;
/*     */   }
/*     */ 
/*     */   public int getMaxElement() {
/* 517 */     if (isNil()) {
/* 518 */       return -7;
/*     */     }
/* 520 */     Interval last = (Interval)this.intervals.get(this.intervals.size() - 1);
/* 521 */     return last.b;
/*     */   }
/*     */ 
/*     */   public int getMinElement()
/*     */   {
/* 526 */     if (isNil()) {
/* 527 */       return -7;
/*     */     }
/* 529 */     int n = this.intervals.size();
/* 530 */     for (int i = 0; i < n; i++) {
/* 531 */       Interval I = (Interval)this.intervals.get(i);
/* 532 */       int a = I.a;
/* 533 */       int b = I.b;
/* 534 */       for (int v = a; v <= b; v++) {
/* 535 */         if (v >= 0) return v;
/*     */       }
/*     */     }
/* 538 */     return -7;
/*     */   }
/*     */ 
/*     */   public List<Interval> getIntervals()
/*     */   {
/* 543 */     return this.intervals;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/* 552 */     if ((obj == null) || (!(obj instanceof IntervalSet))) {
/* 553 */       return false;
/*     */     }
/* 555 */     IntervalSet other = (IntervalSet)obj;
/* 556 */     return this.intervals.equals(other.intervals);
/*     */   }
/*     */ 
/*     */   public String toString() {
/* 560 */     return toString(null);
/*     */   }
/*     */ 
/*     */   public String toString(Grammar g) {
/* 564 */     StringBuffer buf = new StringBuffer();
/* 565 */     if ((this.intervals == null) || (this.intervals.size() == 0)) {
/* 566 */       return "{}";
/*     */     }
/* 568 */     if (this.intervals.size() > 1) {
/* 569 */       buf.append("{");
/*     */     }
/* 571 */     Iterator iter = this.intervals.iterator();
/* 572 */     while (iter.hasNext()) {
/* 573 */       Interval I = (Interval)iter.next();
/* 574 */       int a = I.a;
/* 575 */       int b = I.b;
/* 576 */       if (a == b) {
/* 577 */         if (g != null) {
/* 578 */           buf.append(g.getTokenDisplayName(a));
/*     */         }
/*     */         else {
/* 581 */           buf.append(a);
/*     */         }
/*     */ 
/*     */       }
/* 585 */       else if (g != null) {
/* 586 */         buf.append(g.getTokenDisplayName(a) + ".." + g.getTokenDisplayName(b));
/*     */       }
/*     */       else {
/* 589 */         buf.append(a + ".." + b);
/*     */       }
/*     */ 
/* 592 */       if (iter.hasNext()) {
/* 593 */         buf.append(", ");
/*     */       }
/*     */     }
/* 596 */     if (this.intervals.size() > 1) {
/* 597 */       buf.append("}");
/*     */     }
/* 599 */     return buf.toString();
/*     */   }
/*     */ 
/*     */   public int size() {
/* 603 */     int n = 0;
/* 604 */     int numIntervals = this.intervals.size();
/* 605 */     if (numIntervals == 1) {
/* 606 */       Interval firstInterval = (Interval)this.intervals.get(0);
/* 607 */       return firstInterval.b - firstInterval.a + 1;
/*     */     }
/* 609 */     for (int i = 0; i < numIntervals; i++) {
/* 610 */       Interval I = (Interval)this.intervals.get(i);
/* 611 */       n += I.b - I.a + 1;
/*     */     }
/* 613 */     return n;
/*     */   }
/*     */ 
/*     */   public List toList() {
/* 617 */     List values = new ArrayList();
/* 618 */     int n = this.intervals.size();
/* 619 */     for (int i = 0; i < n; i++) {
/* 620 */       Interval I = (Interval)this.intervals.get(i);
/* 621 */       int a = I.a;
/* 622 */       int b = I.b;
/* 623 */       for (int v = a; v <= b; v++) {
/* 624 */         values.add(Utils.integer(v));
/*     */       }
/*     */     }
/* 627 */     return values;
/*     */   }
/*     */ 
/*     */   public int get(int i)
/*     */   {
/* 635 */     int n = this.intervals.size();
/* 636 */     int index = 0;
/* 637 */     for (int j = 0; j < n; j++) {
/* 638 */       Interval I = (Interval)this.intervals.get(j);
/* 639 */       int a = I.a;
/* 640 */       int b = I.b;
/* 641 */       for (int v = a; v <= b; v++) {
/* 642 */         if (index == i) {
/* 643 */           return v;
/*     */         }
/* 645 */         index++;
/*     */       }
/*     */     }
/* 648 */     return -1;
/*     */   }
/*     */ 
/*     */   public int[] toArray() {
/* 652 */     int[] values = new int[size()];
/* 653 */     int n = this.intervals.size();
/* 654 */     int j = 0;
/* 655 */     for (int i = 0; i < n; i++) {
/* 656 */       Interval I = (Interval)this.intervals.get(i);
/* 657 */       int a = I.a;
/* 658 */       int b = I.b;
/* 659 */       for (int v = a; v <= b; v++) {
/* 660 */         values[j] = v;
/* 661 */         j++;
/*     */       }
/*     */     }
/* 664 */     return values;
/*     */   }
/*     */ 
/*     */   public BitSet toRuntimeBitSet() {
/* 668 */     BitSet s = new BitSet(getMaxElement() + 1);
/*     */ 
/* 670 */     int n = this.intervals.size();
/* 671 */     for (int i = 0; i < n; i++) {
/* 672 */       Interval I = (Interval)this.intervals.get(i);
/* 673 */       int a = I.a;
/* 674 */       int b = I.b;
/* 675 */       for (int v = a; v <= b; v++) {
/* 676 */         s.add(v);
/*     */       }
/*     */     }
/* 679 */     return s;
/*     */   }
/*     */ 
/*     */   public void remove(int el) {
/* 683 */     throw new NoSuchMethodError("IntervalSet.remove() unimplemented");
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.antlr.misc.IntervalSet
 * JD-Core Version:    0.6.2
 */