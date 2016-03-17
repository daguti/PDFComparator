/*     */ package org.antlr.misc;
/*     */ 
/*     */ import java.util.Collection;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import org.antlr.tool.Grammar;
/*     */ 
/*     */ public class BitSet
/*     */   implements IntSet, Cloneable
/*     */ {
/*     */   protected static final int BITS = 64;
/*     */   protected static final int LOG_BITS = 6;
/*     */   protected static final int MOD_MASK = 63;
/*     */   protected long[] bits;
/*     */ 
/*     */   public BitSet()
/*     */   {
/*  69 */     this(64);
/*     */   }
/*     */ 
/*     */   public BitSet(long[] bits_)
/*     */   {
/*  74 */     this.bits = bits_;
/*     */   }
/*     */ 
/*     */   public BitSet(int nbits)
/*     */   {
/*  81 */     this.bits = new long[(nbits - 1 >> 6) + 1];
/*     */   }
/*     */ 
/*     */   public void add(int el)
/*     */   {
/*  87 */     int n = wordNumber(el);
/*     */ 
/*  90 */     if (n >= this.bits.length) {
/*  91 */       growToInclude(el);
/*     */     }
/*  93 */     this.bits[n] |= bitMask(el);
/*     */   }
/*     */ 
/*     */   public void addAll(IntSet set) {
/*  97 */     if ((set instanceof BitSet)) {
/*  98 */       orInPlace((BitSet)set);
/*     */     }
/*     */     else
/*     */     {
/*     */       Iterator iter;
/* 100 */       if ((set instanceof IntervalSet)) {
/* 101 */         IntervalSet other = (IntervalSet)set;
/*     */ 
/* 103 */         for (iter = other.intervals.iterator(); iter.hasNext(); ) {
/* 104 */           Interval I = (Interval)iter.next();
/* 105 */           orInPlace(range(I.a, I.b));
/*     */         }
/*     */       }
/*     */       else {
/* 109 */         throw new IllegalArgumentException("can't add " + set.getClass().getName() + " to BitSet");
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public void addAll(int[] elements)
/*     */   {
/* 116 */     if (elements == null) {
/* 117 */       return;
/*     */     }
/* 119 */     for (int i = 0; i < elements.length; i++) {
/* 120 */       int e = elements[i];
/* 121 */       add(e);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void addAll(Iterable elements) {
/* 126 */     if (elements == null) {
/* 127 */       return;
/*     */     }
/* 129 */     Iterator it = elements.iterator();
/* 130 */     while (it.hasNext()) {
/* 131 */       Object o = it.next();
/* 132 */       if (!(o instanceof Integer)) {
/* 133 */         throw new IllegalArgumentException();
/*     */       }
/* 135 */       Integer eI = (Integer)o;
/* 136 */       add(eI.intValue());
/*     */     }
/*     */   }
/*     */ 
/*     */   public IntSet and(IntSet a)
/*     */   {
/* 152 */     BitSet s = (BitSet)clone();
/* 153 */     s.andInPlace((BitSet)a);
/* 154 */     return s;
/*     */   }
/*     */ 
/*     */   public void andInPlace(BitSet a) {
/* 158 */     int min = Math.min(this.bits.length, a.bits.length);
/* 159 */     for (int i = min - 1; i >= 0; i--) {
/* 160 */       this.bits[i] &= a.bits[i];
/*     */     }
/*     */ 
/* 163 */     for (int i = min; i < this.bits.length; i++)
/* 164 */       this.bits[i] = 0L;
/*     */   }
/*     */ 
/*     */   private static final long bitMask(int bitNumber)
/*     */   {
/* 169 */     int bitPosition = bitNumber & 0x3F;
/* 170 */     return 1L << bitPosition;
/*     */   }
/*     */ 
/*     */   public void clear() {
/* 174 */     for (int i = this.bits.length - 1; i >= 0; i--)
/* 175 */       this.bits[i] = 0L;
/*     */   }
/*     */ 
/*     */   public void clear(int el)
/*     */   {
/* 180 */     int n = wordNumber(el);
/* 181 */     if (n >= this.bits.length) {
/* 182 */       growToInclude(el);
/*     */     }
/* 184 */     this.bits[n] &= (bitMask(el) ^ 0xFFFFFFFF);
/*     */   }
/*     */ 
/*     */   public Object clone() {
/*     */     BitSet s;
/*     */     try {
/* 190 */       s = (BitSet)super.clone();
/* 191 */       s.bits = new long[this.bits.length];
/* 192 */       System.arraycopy(this.bits, 0, s.bits, 0, this.bits.length);
/*     */     }
/*     */     catch (CloneNotSupportedException e) {
/* 195 */       throw new InternalError();
/*     */     }
/* 197 */     return s;
/*     */   }
/*     */ 
/*     */   public int size() {
/* 201 */     int deg = 0;
/* 202 */     for (int i = this.bits.length - 1; i >= 0; i--) {
/* 203 */       long word = this.bits[i];
/* 204 */       if (word != 0L) {
/* 205 */         for (int bit = 63; bit >= 0; bit--) {
/* 206 */           if ((word & 1L << bit) != 0L) {
/* 207 */             deg++;
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/* 212 */     return deg;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object other) {
/* 216 */     if ((other == null) || (!(other instanceof BitSet))) {
/* 217 */       return false;
/*     */     }
/*     */ 
/* 220 */     BitSet otherSet = (BitSet)other;
/*     */ 
/* 222 */     int n = Math.min(this.bits.length, otherSet.bits.length);
/*     */ 
/* 225 */     for (int i = 0; i < n; i++) {
/* 226 */       if (this.bits[i] != otherSet.bits[i]) {
/* 227 */         return false;
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 233 */     if (this.bits.length > n) {
/* 234 */       for (int i = n + 1; i < this.bits.length; i++) {
/* 235 */         if (this.bits[i] != 0L) {
/* 236 */           return false;
/*     */         }
/*     */       }
/*     */     }
/* 240 */     else if (otherSet.bits.length > n) {
/* 241 */       for (int i = n + 1; i < otherSet.bits.length; i++) {
/* 242 */         if (otherSet.bits[i] != 0L) {
/* 243 */           return false;
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/* 248 */     return true;
/*     */   }
/*     */ 
/*     */   public void growToInclude(int bit)
/*     */   {
/* 256 */     int newSize = Math.max(this.bits.length << 1, numWordsToHold(bit));
/* 257 */     long[] newbits = new long[newSize];
/* 258 */     System.arraycopy(this.bits, 0, newbits, 0, this.bits.length);
/* 259 */     this.bits = newbits;
/*     */   }
/*     */ 
/*     */   public boolean member(int el) {
/* 263 */     int n = wordNumber(el);
/* 264 */     if (n >= this.bits.length) return false;
/* 265 */     return (this.bits[n] & bitMask(el)) != 0L;
/*     */   }
/*     */ 
/*     */   public int getSingleElement()
/*     */   {
/* 272 */     for (int i = 0; i < this.bits.length << 6; i++) {
/* 273 */       if (member(i)) {
/* 274 */         return i;
/*     */       }
/*     */     }
/* 277 */     return -7;
/*     */   }
/*     */ 
/*     */   public boolean isNil() {
/* 281 */     for (int i = this.bits.length - 1; i >= 0; i--) {
/* 282 */       if (this.bits[i] != 0L) return false;
/*     */     }
/* 284 */     return true;
/*     */   }
/*     */ 
/*     */   public IntSet complement() {
/* 288 */     BitSet s = (BitSet)clone();
/* 289 */     s.notInPlace();
/* 290 */     return s;
/*     */   }
/*     */ 
/*     */   public IntSet complement(IntSet set) {
/* 294 */     if (set == null) {
/* 295 */       return complement();
/*     */     }
/* 297 */     return set.subtract(this);
/*     */   }
/*     */ 
/*     */   public void notInPlace() {
/* 301 */     for (int i = this.bits.length - 1; i >= 0; i--)
/* 302 */       this.bits[i] ^= -1L;
/*     */   }
/*     */ 
/*     */   public void notInPlace(int maxBit)
/*     */   {
/* 308 */     notInPlace(0, maxBit);
/*     */   }
/*     */ 
/*     */   public void notInPlace(int minBit, int maxBit)
/*     */   {
/* 314 */     growToInclude(maxBit);
/* 315 */     for (int i = minBit; i <= maxBit; i++) {
/* 316 */       int n = wordNumber(i);
/* 317 */       this.bits[n] ^= bitMask(i);
/*     */     }
/*     */   }
/*     */ 
/*     */   private final int numWordsToHold(int el) {
/* 322 */     return (el >> 6) + 1;
/*     */   }
/*     */ 
/*     */   public static BitSet of(int el) {
/* 326 */     BitSet s = new BitSet(el + 1);
/* 327 */     s.add(el);
/* 328 */     return s;
/*     */   }
/*     */ 
/*     */   public static BitSet of(Collection elements) {
/* 332 */     BitSet s = new BitSet();
/* 333 */     Iterator iter = elements.iterator();
/* 334 */     while (iter.hasNext()) {
/* 335 */       Integer el = (Integer)iter.next();
/* 336 */       s.add(el.intValue());
/*     */     }
/* 338 */     return s;
/*     */   }
/*     */ 
/*     */   public static BitSet of(IntSet set) {
/* 342 */     if (set == null) {
/* 343 */       return null;
/*     */     }
/*     */ 
/* 346 */     if ((set instanceof BitSet)) {
/* 347 */       return (BitSet)set;
/*     */     }
/* 349 */     if ((set instanceof IntervalSet)) {
/* 350 */       BitSet s = new BitSet();
/* 351 */       s.addAll(set);
/* 352 */       return s;
/*     */     }
/* 354 */     throw new IllegalArgumentException("can't create BitSet from " + set.getClass().getName());
/*     */   }
/*     */ 
/*     */   public static BitSet of(Map elements) {
/* 358 */     return of(elements.keySet());
/*     */   }
/*     */ 
/*     */   public static BitSet range(int a, int b) {
/* 362 */     BitSet s = new BitSet(b + 1);
/* 363 */     for (int i = a; i <= b; i++) {
/* 364 */       int n = wordNumber(i);
/* 365 */       s.bits[n] |= bitMask(i);
/*     */     }
/* 367 */     return s;
/*     */   }
/*     */ 
/*     */   public IntSet or(IntSet a)
/*     */   {
/* 372 */     if (a == null) {
/* 373 */       return this;
/*     */     }
/* 375 */     BitSet s = (BitSet)clone();
/* 376 */     s.orInPlace((BitSet)a);
/* 377 */     return s;
/*     */   }
/*     */ 
/*     */   public void orInPlace(BitSet a) {
/* 381 */     if (a == null) {
/* 382 */       return;
/*     */     }
/*     */ 
/* 385 */     if (a.bits.length > this.bits.length) {
/* 386 */       setSize(a.bits.length);
/*     */     }
/* 388 */     int min = Math.min(this.bits.length, a.bits.length);
/* 389 */     for (int i = min - 1; i >= 0; i--)
/* 390 */       this.bits[i] |= a.bits[i];
/*     */   }
/*     */ 
/*     */   public void remove(int el)
/*     */   {
/* 396 */     int n = wordNumber(el);
/* 397 */     if (n >= this.bits.length) {
/* 398 */       growToInclude(el);
/*     */     }
/* 400 */     this.bits[n] &= (bitMask(el) ^ 0xFFFFFFFF);
/*     */   }
/*     */ 
/*     */   private void setSize(int nwords)
/*     */   {
/* 408 */     long[] newbits = new long[nwords];
/* 409 */     int n = Math.min(nwords, this.bits.length);
/* 410 */     System.arraycopy(this.bits, 0, newbits, 0, n);
/* 411 */     this.bits = newbits;
/*     */   }
/*     */ 
/*     */   public int numBits() {
/* 415 */     return this.bits.length << 6;
/*     */   }
/*     */ 
/*     */   public int lengthInLongWords()
/*     */   {
/* 422 */     return this.bits.length;
/*     */   }
/*     */ 
/*     */   public boolean subset(BitSet a)
/*     */   {
/* 427 */     if (a == null) return false;
/* 428 */     return and(a).equals(this);
/*     */   }
/*     */ 
/*     */   public void subtractInPlace(BitSet a)
/*     */   {
/* 435 */     if (a == null) return;
/*     */ 
/* 437 */     for (int i = 0; (i < this.bits.length) && (i < a.bits.length); i++)
/* 438 */       this.bits[i] &= (a.bits[i] ^ 0xFFFFFFFF);
/*     */   }
/*     */ 
/*     */   public IntSet subtract(IntSet a)
/*     */   {
/* 443 */     if ((a == null) || (!(a instanceof BitSet))) return null;
/*     */ 
/* 445 */     BitSet s = (BitSet)clone();
/* 446 */     s.subtractInPlace((BitSet)a);
/* 447 */     return s;
/*     */   }
/*     */ 
/*     */   public List toList() {
/* 451 */     throw new NoSuchMethodError("BitSet.toList() unimplemented");
/*     */   }
/*     */ 
/*     */   public int[] toArray() {
/* 455 */     int[] elems = new int[size()];
/* 456 */     int en = 0;
/* 457 */     for (int i = 0; i < this.bits.length << 6; i++) {
/* 458 */       if (member(i)) {
/* 459 */         elems[(en++)] = i;
/*     */       }
/*     */     }
/* 462 */     return elems;
/*     */   }
/*     */ 
/*     */   public long[] toPackedArray() {
/* 466 */     return this.bits;
/*     */   }
/*     */ 
/*     */   public String toString() {
/* 470 */     return toString(null);
/*     */   }
/*     */ 
/*     */   public String toString(Grammar g)
/*     */   {
/* 478 */     StringBuffer buf = new StringBuffer();
/* 479 */     String separator = ",";
/* 480 */     boolean havePrintedAnElement = false;
/* 481 */     buf.append('{');
/*     */ 
/* 483 */     for (int i = 0; i < this.bits.length << 6; i++) {
/* 484 */       if (member(i)) {
/* 485 */         if ((i > 0) && (havePrintedAnElement)) {
/* 486 */           buf.append(separator);
/*     */         }
/* 488 */         if (g != null) {
/* 489 */           buf.append(g.getTokenDisplayName(i));
/*     */         }
/*     */         else {
/* 492 */           buf.append(i);
/*     */         }
/* 494 */         havePrintedAnElement = true;
/*     */       }
/*     */     }
/* 497 */     buf.append('}');
/* 498 */     return buf.toString();
/*     */   }
/*     */ 
/*     */   public String toString(String separator, List vocabulary)
/*     */   {
/* 508 */     if (vocabulary == null) {
/* 509 */       return toString(null);
/*     */     }
/* 511 */     String str = "";
/* 512 */     for (int i = 0; i < this.bits.length << 6; i++) {
/* 513 */       if (member(i)) {
/* 514 */         if (str.length() > 0) {
/* 515 */           str = str + separator;
/*     */         }
/* 517 */         if (i >= vocabulary.size()) {
/* 518 */           str = str + "'" + (char)i + "'";
/*     */         }
/* 520 */         else if (vocabulary.get(i) == null) {
/* 521 */           str = str + "'" + (char)i + "'";
/*     */         }
/*     */         else {
/* 524 */           str = str + (String)vocabulary.get(i);
/*     */         }
/*     */       }
/*     */     }
/* 528 */     return str;
/*     */   }
/*     */ 
/*     */   public String toStringOfHalfWords()
/*     */   {
/* 537 */     StringBuffer s = new StringBuffer();
/* 538 */     for (int i = 0; i < this.bits.length; i++) {
/* 539 */       if (i != 0) s.append(", ");
/* 540 */       long tmp = this.bits[i];
/* 541 */       tmp &= 4294967295L;
/* 542 */       s.append(tmp);
/* 543 */       s.append("UL");
/* 544 */       s.append(", ");
/* 545 */       tmp = this.bits[i] >>> 32;
/* 546 */       tmp &= 4294967295L;
/* 547 */       s.append(tmp);
/* 548 */       s.append("UL");
/*     */     }
/* 550 */     return s.toString();
/*     */   }
/*     */ 
/*     */   public String toStringOfWords()
/*     */   {
/* 558 */     StringBuffer s = new StringBuffer();
/* 559 */     for (int i = 0; i < this.bits.length; i++) {
/* 560 */       if (i != 0) s.append(", ");
/* 561 */       s.append(this.bits[i]);
/* 562 */       s.append("L");
/*     */     }
/* 564 */     return s.toString();
/*     */   }
/*     */ 
/*     */   public String toStringWithRanges() {
/* 568 */     return toString();
/*     */   }
/*     */ 
/*     */   private static final int wordNumber(int bit) {
/* 572 */     return bit >> 6;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.antlr.misc.BitSet
 * JD-Core Version:    0.6.2
 */