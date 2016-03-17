/*     */ package com.itextpdf.text.pdf.qrcode;
/*     */ 
/*     */ public final class BitVector
/*     */ {
/*     */   private int sizeInBits;
/*     */   private byte[] array;
/*     */   private static final int DEFAULT_SIZE_IN_BYTES = 32;
/*     */ 
/*     */   public BitVector()
/*     */   {
/*  37 */     this.sizeInBits = 0;
/*  38 */     this.array = new byte[32];
/*     */   }
/*     */ 
/*     */   public int at(int index)
/*     */   {
/*  43 */     if ((index < 0) || (index >= this.sizeInBits)) {
/*  44 */       throw new IllegalArgumentException("Bad index: " + index);
/*     */     }
/*  46 */     int value = this.array[(index >> 3)] & 0xFF;
/*  47 */     return value >> 7 - (index & 0x7) & 0x1;
/*     */   }
/*     */ 
/*     */   public int size()
/*     */   {
/*  52 */     return this.sizeInBits;
/*     */   }
/*     */ 
/*     */   public int sizeInBytes()
/*     */   {
/*  57 */     return this.sizeInBits + 7 >> 3;
/*     */   }
/*     */ 
/*     */   public void appendBit(int bit)
/*     */   {
/*  62 */     if ((bit != 0) && (bit != 1)) {
/*  63 */       throw new IllegalArgumentException("Bad bit");
/*     */     }
/*  65 */     int numBitsInLastByte = this.sizeInBits & 0x7;
/*     */ 
/*  67 */     if (numBitsInLastByte == 0) {
/*  68 */       appendByte(0);
/*  69 */       this.sizeInBits -= 8;
/*     */     }
/*     */     int tmp57_56 = (this.sizeInBits >> 3);
/*     */     byte[] tmp57_48 = this.array; tmp57_48[tmp57_56] = ((byte)(tmp57_48[tmp57_56] | bit << 7 - numBitsInLastByte));
/*  73 */     this.sizeInBits += 1;
/*     */   }
/*     */ 
/*     */   public void appendBits(int value, int numBits)
/*     */   {
/*  84 */     if ((numBits < 0) || (numBits > 32)) {
/*  85 */       throw new IllegalArgumentException("Num bits must be between 0 and 32");
/*     */     }
/*  87 */     int numBitsLeft = numBits;
/*  88 */     while (numBitsLeft > 0)
/*     */     {
/*  90 */       if (((this.sizeInBits & 0x7) == 0) && (numBitsLeft >= 8)) {
/*  91 */         int newByte = value >> numBitsLeft - 8 & 0xFF;
/*  92 */         appendByte(newByte);
/*  93 */         numBitsLeft -= 8;
/*     */       } else {
/*  95 */         int bit = value >> numBitsLeft - 1 & 0x1;
/*  96 */         appendBit(bit);
/*  97 */         numBitsLeft--;
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public void appendBitVector(BitVector bits)
/*     */   {
/* 104 */     int size = bits.size();
/* 105 */     for (int i = 0; i < size; i++)
/* 106 */       appendBit(bits.at(i));
/*     */   }
/*     */ 
/*     */   public void xor(BitVector other)
/*     */   {
/* 112 */     if (this.sizeInBits != other.size()) {
/* 113 */       throw new IllegalArgumentException("BitVector sizes don't match");
/*     */     }
/* 115 */     int sizeInBytes = this.sizeInBits + 7 >> 3;
/* 116 */     for (int i = 0; i < sizeInBytes; i++)
/*     */     {
/*     */       int tmp43_42 = i;
/*     */       byte[] tmp43_39 = this.array; tmp43_39[tmp43_42] = ((byte)(tmp43_39[tmp43_42] ^ other.array[i]));
/*     */     }
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 125 */     StringBuffer result = new StringBuffer(this.sizeInBits);
/* 126 */     for (int i = 0; i < this.sizeInBits; i++) {
/* 127 */       if (at(i) == 0)
/* 128 */         result.append('0');
/* 129 */       else if (at(i) == 1)
/* 130 */         result.append('1');
/*     */       else {
/* 132 */         throw new IllegalArgumentException("Byte isn't 0 or 1");
/*     */       }
/*     */     }
/* 135 */     return result.toString();
/*     */   }
/*     */ 
/*     */   public byte[] getArray()
/*     */   {
/* 141 */     return this.array;
/*     */   }
/*     */ 
/*     */   private void appendByte(int value)
/*     */   {
/* 147 */     if (this.sizeInBits >> 3 == this.array.length) {
/* 148 */       byte[] newArray = new byte[this.array.length << 1];
/* 149 */       System.arraycopy(this.array, 0, newArray, 0, this.array.length);
/* 150 */       this.array = newArray;
/*     */     }
/* 152 */     this.array[(this.sizeInBits >> 3)] = ((byte)value);
/* 153 */     this.sizeInBits += 8;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.pdf.qrcode.BitVector
 * JD-Core Version:    0.6.2
 */