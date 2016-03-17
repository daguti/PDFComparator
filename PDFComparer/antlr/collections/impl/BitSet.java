/*     */ package antlr.collections.impl;
/*     */ 
/*     */ import antlr.CharFormatter;
/*     */ 
/*     */ public class BitSet
/*     */   implements Cloneable
/*     */ {
/*     */   protected static final int BITS = 64;
/*     */   protected static final int NIBBLE = 4;
/*     */   protected static final int LOG_BITS = 6;
/*     */   protected static final int MOD_MASK = 63;
/*     */   protected long[] bits;
/*     */ 
/*     */   public BitSet()
/*     */   {
/*  44 */     this(64);
/*     */   }
/*     */ 
/*     */   public BitSet(long[] paramArrayOfLong)
/*     */   {
/*  49 */     this.bits = paramArrayOfLong;
/*     */   }
/*     */ 
/*     */   public BitSet(int paramInt)
/*     */   {
/*  56 */     this.bits = new long[(paramInt - 1 >> 6) + 1];
/*     */   }
/*     */ 
/*     */   public void add(int paramInt)
/*     */   {
/*  62 */     int i = wordNumber(paramInt);
/*     */ 
/*  65 */     if (i >= this.bits.length) {
/*  66 */       growToInclude(paramInt);
/*     */     }
/*  68 */     this.bits[i] |= bitMask(paramInt);
/*     */   }
/*     */ 
/*     */   public BitSet and(BitSet paramBitSet) {
/*  72 */     BitSet localBitSet = (BitSet)clone();
/*  73 */     localBitSet.andInPlace(paramBitSet);
/*  74 */     return localBitSet;
/*     */   }
/*     */ 
/*     */   public void andInPlace(BitSet paramBitSet) {
/*  78 */     int i = Math.min(this.bits.length, paramBitSet.bits.length);
/*  79 */     for (int j = i - 1; j >= 0; j--) {
/*  80 */       this.bits[j] &= paramBitSet.bits[j];
/*     */     }
/*     */ 
/*  83 */     for (j = i; j < this.bits.length; j++)
/*  84 */       this.bits[j] = 0L;
/*     */   }
/*     */ 
/*     */   private static final long bitMask(int paramInt)
/*     */   {
/*  89 */     int i = paramInt & 0x3F;
/*  90 */     return 1L << i;
/*     */   }
/*     */ 
/*     */   public void clear() {
/*  94 */     for (int i = this.bits.length - 1; i >= 0; i--)
/*  95 */       this.bits[i] = 0L;
/*     */   }
/*     */ 
/*     */   public void clear(int paramInt)
/*     */   {
/* 100 */     int i = wordNumber(paramInt);
/* 101 */     if (i >= this.bits.length) {
/* 102 */       growToInclude(paramInt);
/*     */     }
/* 104 */     this.bits[i] &= (bitMask(paramInt) ^ 0xFFFFFFFF);
/*     */   }
/*     */ 
/*     */   public Object clone() {
/*     */     BitSet localBitSet;
/*     */     try {
/* 110 */       localBitSet = (BitSet)super.clone();
/* 111 */       localBitSet.bits = new long[this.bits.length];
/* 112 */       System.arraycopy(this.bits, 0, localBitSet.bits, 0, this.bits.length);
/*     */     }
/*     */     catch (CloneNotSupportedException localCloneNotSupportedException) {
/* 115 */       throw new InternalError();
/*     */     }
/* 117 */     return localBitSet;
/*     */   }
/*     */ 
/*     */   public int degree() {
/* 121 */     int i = 0;
/* 122 */     for (int j = this.bits.length - 1; j >= 0; j--) {
/* 123 */       long l = this.bits[j];
/* 124 */       if (l != 0L) {
/* 125 */         for (int k = 63; k >= 0; k--) {
/* 126 */           if ((l & 1L << k) != 0L) {
/* 127 */             i++;
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/* 132 */     return i;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object paramObject)
/*     */   {
/* 137 */     if ((paramObject != null) && ((paramObject instanceof BitSet))) {
/* 138 */       BitSet localBitSet = (BitSet)paramObject;
/*     */ 
/* 140 */       int i = Math.min(this.bits.length, localBitSet.bits.length);
/* 141 */       for (int j = i; j-- > 0; ) {
/* 142 */         if (this.bits[j] != localBitSet.bits[j]) {
/* 143 */           return false;
/*     */         }
/*     */       }
/* 146 */       if (this.bits.length > i) {
/* 147 */         j = this.bits.length;
/*     */         do if (j-- <= i)
/*     */             break; while (this.bits[j] == 0L);
/* 149 */         return false;
/*     */       }
/*     */ 
/* 153 */       if (localBitSet.bits.length > i) {
/* 154 */         for (j = localBitSet.bits.length; j-- > i; ) {
/* 155 */           if (localBitSet.bits[j] != 0L) {
/* 156 */             return false;
/*     */           }
/*     */         }
/*     */       }
/* 160 */       return true;
/*     */     }
/* 162 */     return false;
/*     */   }
/*     */ 
/*     */   public static Vector getRanges(int[] paramArrayOfInt)
/*     */   {
/* 170 */     if (paramArrayOfInt.length == 0) {
/* 171 */       return null;
/*     */     }
/* 173 */     int i = paramArrayOfInt[0];
/* 174 */     int j = paramArrayOfInt[(paramArrayOfInt.length - 1)];
/* 175 */     if (paramArrayOfInt.length <= 2)
/*     */     {
/* 177 */       return null;
/*     */     }
/*     */ 
/* 180 */     Vector localVector = new Vector(5);
/*     */ 
/* 182 */     for (int k = 0; k < paramArrayOfInt.length - 2; k++)
/*     */     {
/* 184 */       int m = paramArrayOfInt.length - 1;
/* 185 */       for (int n = k + 1; n < paramArrayOfInt.length; n++) {
/* 186 */         if (paramArrayOfInt[n] != paramArrayOfInt[(n - 1)] + 1) {
/* 187 */           m = n - 1;
/* 188 */           break;
/*     */         }
/*     */       }
/*     */ 
/* 192 */       if (m - k > 2) {
/* 193 */         localVector.appendElement(new IntRange(paramArrayOfInt[k], paramArrayOfInt[m]));
/*     */       }
/*     */     }
/* 196 */     return localVector;
/*     */   }
/*     */ 
/*     */   public void growToInclude(int paramInt)
/*     */   {
/* 204 */     int i = Math.max(this.bits.length << 1, numWordsToHold(paramInt));
/* 205 */     long[] arrayOfLong = new long[i];
/* 206 */     System.arraycopy(this.bits, 0, arrayOfLong, 0, this.bits.length);
/* 207 */     this.bits = arrayOfLong;
/*     */   }
/*     */ 
/*     */   public boolean member(int paramInt) {
/* 211 */     int i = wordNumber(paramInt);
/* 212 */     if (i >= this.bits.length) return false;
/* 213 */     return (this.bits[i] & bitMask(paramInt)) != 0L;
/*     */   }
/*     */ 
/*     */   public boolean nil() {
/* 217 */     for (int i = this.bits.length - 1; i >= 0; i--) {
/* 218 */       if (this.bits[i] != 0L) return false;
/*     */     }
/* 220 */     return true;
/*     */   }
/*     */ 
/*     */   public BitSet not() {
/* 224 */     BitSet localBitSet = (BitSet)clone();
/* 225 */     localBitSet.notInPlace();
/* 226 */     return localBitSet;
/*     */   }
/*     */ 
/*     */   public void notInPlace() {
/* 230 */     for (int i = this.bits.length - 1; i >= 0; i--)
/* 231 */       this.bits[i] ^= -1L;
/*     */   }
/*     */ 
/*     */   public void notInPlace(int paramInt)
/*     */   {
/* 237 */     notInPlace(0, paramInt);
/*     */   }
/*     */ 
/*     */   public void notInPlace(int paramInt1, int paramInt2)
/*     */   {
/* 243 */     growToInclude(paramInt2);
/* 244 */     for (int i = paramInt1; i <= paramInt2; i++) {
/* 245 */       int j = wordNumber(i);
/* 246 */       this.bits[j] ^= bitMask(i);
/*     */     }
/*     */   }
/*     */ 
/*     */   private final int numWordsToHold(int paramInt) {
/* 251 */     return (paramInt >> 6) + 1;
/*     */   }
/*     */ 
/*     */   public static BitSet of(int paramInt) {
/* 255 */     BitSet localBitSet = new BitSet(paramInt + 1);
/* 256 */     localBitSet.add(paramInt);
/* 257 */     return localBitSet;
/*     */   }
/*     */ 
/*     */   public BitSet or(BitSet paramBitSet)
/*     */   {
/* 262 */     BitSet localBitSet = (BitSet)clone();
/* 263 */     localBitSet.orInPlace(paramBitSet);
/* 264 */     return localBitSet;
/*     */   }
/*     */ 
/*     */   public void orInPlace(BitSet paramBitSet)
/*     */   {
/* 269 */     if (paramBitSet.bits.length > this.bits.length) {
/* 270 */       setSize(paramBitSet.bits.length);
/*     */     }
/* 272 */     int i = Math.min(this.bits.length, paramBitSet.bits.length);
/* 273 */     for (int j = i - 1; j >= 0; j--)
/* 274 */       this.bits[j] |= paramBitSet.bits[j];
/*     */   }
/*     */ 
/*     */   public void remove(int paramInt)
/*     */   {
/* 280 */     int i = wordNumber(paramInt);
/* 281 */     if (i >= this.bits.length) {
/* 282 */       growToInclude(paramInt);
/*     */     }
/* 284 */     this.bits[i] &= (bitMask(paramInt) ^ 0xFFFFFFFF);
/*     */   }
/*     */ 
/*     */   private void setSize(int paramInt)
/*     */   {
/* 292 */     long[] arrayOfLong = new long[paramInt];
/* 293 */     int i = Math.min(paramInt, this.bits.length);
/* 294 */     System.arraycopy(this.bits, 0, arrayOfLong, 0, i);
/* 295 */     this.bits = arrayOfLong;
/*     */   }
/*     */ 
/*     */   public int size() {
/* 299 */     return this.bits.length << 6;
/*     */   }
/*     */ 
/*     */   public int lengthInLongWords()
/*     */   {
/* 306 */     return this.bits.length;
/*     */   }
/*     */ 
/*     */   public boolean subset(BitSet paramBitSet)
/*     */   {
/* 311 */     if ((paramBitSet == null) || (!(paramBitSet instanceof BitSet))) return false;
/* 312 */     return and(paramBitSet).equals(this);
/*     */   }
/*     */ 
/*     */   public void subtractInPlace(BitSet paramBitSet)
/*     */   {
/* 319 */     if (paramBitSet == null) return;
/*     */ 
/* 321 */     for (int i = 0; (i < this.bits.length) && (i < paramBitSet.bits.length); i++)
/* 322 */       this.bits[i] &= (paramBitSet.bits[i] ^ 0xFFFFFFFF);
/*     */   }
/*     */ 
/*     */   public int[] toArray()
/*     */   {
/* 327 */     int[] arrayOfInt = new int[degree()];
/* 328 */     int i = 0;
/* 329 */     for (int j = 0; j < this.bits.length << 6; j++) {
/* 330 */       if (member(j)) {
/* 331 */         arrayOfInt[(i++)] = j;
/*     */       }
/*     */     }
/* 334 */     return arrayOfInt;
/*     */   }
/*     */ 
/*     */   public long[] toPackedArray() {
/* 338 */     return this.bits;
/*     */   }
/*     */ 
/*     */   public String toString() {
/* 342 */     return toString(",");
/*     */   }
/*     */ 
/*     */   public String toString(String paramString)
/*     */   {
/* 350 */     String str = "";
/* 351 */     for (int i = 0; i < this.bits.length << 6; i++) {
/* 352 */       if (member(i)) {
/* 353 */         if (str.length() > 0) {
/* 354 */           str = str + paramString;
/*     */         }
/* 356 */         str = str + i;
/*     */       }
/*     */     }
/* 359 */     return str;
/*     */   }
/*     */ 
/*     */   public String toString(String paramString, CharFormatter paramCharFormatter)
/*     */   {
/* 368 */     String str = "";
/*     */ 
/* 370 */     for (int i = 0; i < this.bits.length << 6; i++) {
/* 371 */       if (member(i)) {
/* 372 */         if (str.length() > 0) {
/* 373 */           str = str + paramString;
/*     */         }
/* 375 */         str = str + paramCharFormatter.literalChar(i);
/*     */       }
/*     */     }
/* 378 */     return str;
/*     */   }
/*     */ 
/*     */   public String toString(String paramString, Vector paramVector)
/*     */   {
/* 388 */     if (paramVector == null) {
/* 389 */       return toString(paramString);
/*     */     }
/* 391 */     String str = "";
/* 392 */     for (int i = 0; i < this.bits.length << 6; i++) {
/* 393 */       if (member(i)) {
/* 394 */         if (str.length() > 0) {
/* 395 */           str = str + paramString;
/*     */         }
/* 397 */         if (i >= paramVector.size()) {
/* 398 */           str = str + "<bad element " + i + ">";
/*     */         }
/* 400 */         else if (paramVector.elementAt(i) == null) {
/* 401 */           str = str + "<" + i + ">";
/*     */         }
/*     */         else {
/* 404 */           str = str + (String)paramVector.elementAt(i);
/*     */         }
/*     */       }
/*     */     }
/* 408 */     return str;
/*     */   }
/*     */ 
/*     */   public String toStringOfHalfWords()
/*     */   {
/* 417 */     String str = new String();
/* 418 */     for (int i = 0; i < this.bits.length; i++) {
/* 419 */       if (i != 0) str = str + ", ";
/* 420 */       long l = this.bits[i];
/* 421 */       l &= 4294967295L;
/* 422 */       str = str + l + "UL";
/* 423 */       str = str + ", ";
/* 424 */       l = this.bits[i] >>> 32;
/* 425 */       l &= 4294967295L;
/* 426 */       str = str + l + "UL";
/*     */     }
/* 428 */     return str;
/*     */   }
/*     */ 
/*     */   public String toStringOfWords()
/*     */   {
/* 436 */     String str = new String();
/* 437 */     for (int i = 0; i < this.bits.length; i++) {
/* 438 */       if (i != 0) str = str + ", ";
/* 439 */       str = str + this.bits[i] + "L";
/*     */     }
/* 441 */     return str;
/*     */   }
/*     */ 
/*     */   public String toStringWithRanges(String paramString, CharFormatter paramCharFormatter)
/*     */   {
/* 446 */     String str = "";
/* 447 */     int[] arrayOfInt = toArray();
/* 448 */     if (arrayOfInt.length == 0) {
/* 449 */       return "";
/*     */     }
/*     */ 
/* 452 */     int i = 0;
/* 453 */     while (i < arrayOfInt.length)
/*     */     {
/* 455 */       int j = 0;
/* 456 */       for (int k = i + 1; (k < arrayOfInt.length) && 
/* 457 */         (arrayOfInt[k] == arrayOfInt[(k - 1)] + 1); k++)
/*     */       {
/* 460 */         j = k;
/*     */       }
/*     */ 
/* 463 */       if (str.length() > 0) {
/* 464 */         str = str + paramString;
/*     */       }
/* 466 */       if (j - i >= 2) {
/* 467 */         str = str + paramCharFormatter.literalChar(arrayOfInt[i]);
/* 468 */         str = str + "..";
/* 469 */         str = str + paramCharFormatter.literalChar(arrayOfInt[j]);
/* 470 */         i = j;
/*     */       }
/*     */       else {
/* 473 */         str = str + paramCharFormatter.literalChar(arrayOfInt[i]);
/*     */       }
/* 475 */       i++;
/*     */     }
/* 477 */     return str;
/*     */   }
/*     */ 
/*     */   private static final int wordNumber(int paramInt) {
/* 481 */     return paramInt >> 6;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     antlr.collections.impl.BitSet
 * JD-Core Version:    0.6.2
 */