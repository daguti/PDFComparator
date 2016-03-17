/*     */ package org.apache.pdfbox.io.ccitt;
/*     */ 
/*     */ public class PackedBitArray
/*     */ {
/*     */   private int bitCount;
/*     */   private byte[] data;
/*     */ 
/*     */   public PackedBitArray(int bitCount)
/*     */   {
/*  38 */     this.bitCount = bitCount;
/*  39 */     int byteCount = (bitCount + 7) / 8;
/*  40 */     this.data = new byte[byteCount];
/*     */   }
/*     */ 
/*     */   private int byteOffset(int offset)
/*     */   {
/*  45 */     return offset / 8;
/*     */   }
/*     */ 
/*     */   private int bitOffset(int offset)
/*     */   {
/*  50 */     return offset % 8;
/*     */   }
/*     */ 
/*     */   public void set(int offset)
/*     */   {
/*  59 */     int byteOffset = byteOffset(offset);
/*     */     int tmp11_10 = byteOffset;
/*     */     byte[] tmp11_7 = this.data; tmp11_7[tmp11_10] = ((byte)(tmp11_7[tmp11_10] | 1 << bitOffset(offset)));
/*     */   }
/*     */ 
/*     */   public void clear(int offset)
/*     */   {
/*  69 */     int byteOffset = byteOffset(offset);
/*  70 */     int bitOffset = bitOffset(offset);
/*     */     int tmp17_16 = byteOffset;
/*     */     byte[] tmp17_13 = this.data; tmp17_13[tmp17_16] = ((byte)(tmp17_13[tmp17_16] & (1 << bitOffset ^ 0xFFFFFFFF)));
/*     */   }
/*     */ 
/*     */   public void setBits(int offset, int length, int bit)
/*     */   {
/*  82 */     if (bit == 0)
/*     */     {
/*  84 */       clearBits(offset, length);
/*     */     }
/*     */     else
/*     */     {
/*  88 */       setBits(offset, length);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void setBits(int offset, int length)
/*     */   {
/*  99 */     if (length == 0)
/*     */     {
/* 101 */       return;
/*     */     }
/* 103 */     int startBitOffset = bitOffset(offset);
/* 104 */     int firstByte = byteOffset(offset);
/* 105 */     int lastBitOffset = offset + length;
/* 106 */     if (lastBitOffset > getBitCount())
/*     */     {
/* 108 */       throw new IndexOutOfBoundsException("offset + length > bit count");
/*     */     }
/* 110 */     int lastByte = byteOffset(lastBitOffset);
/* 111 */     int endBitOffset = bitOffset(lastBitOffset);
/*     */ 
/* 113 */     if (firstByte == lastByte)
/*     */     {
/* 116 */       int mask = (1 << endBitOffset) - (1 << startBitOffset);
/*     */       int tmp81_79 = firstByte;
/*     */       byte[] tmp81_76 = this.data; tmp81_76[tmp81_79] = ((byte)(tmp81_76[tmp81_79] | mask));
/*     */     }
/*     */     else
/*     */     {
/*     */       int tmp97_95 = firstByte;
/*     */       byte[] tmp97_92 = this.data; tmp97_92[tmp97_95] = ((byte)(tmp97_92[tmp97_95] | 255 << startBitOffset));
/* 123 */       for (int i = firstByte + 1; i < lastByte; i++)
/*     */       {
/* 125 */         this.data[i] = -1;
/*     */       }
/* 127 */       if (endBitOffset > 0)
/*     */       {
/*     */         int tmp145_143 = lastByte;
/*     */         byte[] tmp145_140 = this.data; tmp145_140[tmp145_143] = ((byte)(tmp145_140[tmp145_143] | 255 >> 8 - endBitOffset));
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public void clearBits(int offset, int length)
/*     */   {
/* 141 */     if (length == 0)
/*     */     {
/* 143 */       return;
/*     */     }
/* 145 */     int startBitOffset = offset % 8;
/* 146 */     int firstByte = byteOffset(offset);
/* 147 */     int lastBitOffset = offset + length;
/* 148 */     int lastByte = byteOffset(lastBitOffset);
/* 149 */     int endBitOffset = lastBitOffset % 8;
/*     */ 
/* 151 */     if (firstByte == lastByte)
/*     */     {
/* 154 */       int mask = (1 << endBitOffset) - (1 << startBitOffset);
/*     */       int tmp60_58 = firstByte;
/*     */       byte[] tmp60_55 = this.data; tmp60_55[tmp60_58] = ((byte)(tmp60_55[tmp60_58] & (mask ^ 0xFFFFFFFF)));
/*     */     }
/*     */     else
/*     */     {
/*     */       int tmp78_76 = firstByte;
/*     */       byte[] tmp78_73 = this.data; tmp78_73[tmp78_76] = ((byte)(tmp78_73[tmp78_76] & (255 << startBitOffset ^ 0xFFFFFFFF)));
/* 161 */       for (int i = firstByte + 1; i < lastByte; i++)
/*     */       {
/* 163 */         this.data[i] = 0;
/*     */       }
/* 165 */       if (endBitOffset > 0)
/*     */       {
/*     */         int tmp128_126 = lastByte;
/*     */         byte[] tmp128_123 = this.data; tmp128_123[tmp128_126] = ((byte)(tmp128_123[tmp128_126] & (255 >> 8 - endBitOffset ^ 0xFFFFFFFF)));
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public void clear()
/*     */   {
/* 177 */     clearBits(0, getBitCount());
/*     */   }
/*     */ 
/*     */   public int getBitCount()
/*     */   {
/* 186 */     return this.bitCount;
/*     */   }
/*     */ 
/*     */   public int getByteCount()
/*     */   {
/* 195 */     return this.data.length;
/*     */   }
/*     */ 
/*     */   public byte[] getData()
/*     */   {
/* 207 */     return this.data;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 213 */     return toBitString(this.data).substring(0, this.bitCount);
/*     */   }
/*     */ 
/*     */   public static String toBitString(byte data)
/*     */   {
/* 223 */     byte[] buf = { data };
/* 224 */     return toBitString(buf);
/*     */   }
/*     */ 
/*     */   public static String toBitString(byte[] data)
/*     */   {
/* 234 */     return toBitString(data, 0, data.length);
/*     */   }
/*     */ 
/*     */   public static String toBitString(byte[] data, int start, int len)
/*     */   {
/* 246 */     StringBuffer sb = new StringBuffer();
/* 247 */     int x = start; for (int end = start + len; x < end; x++)
/*     */     {
/* 249 */       for (int i = 0; i < 8; i++)
/*     */       {
/* 251 */         int mask = 1 << i;
/* 252 */         int value = data[x] & mask;
/* 253 */         sb.append(value != 0 ? '1' : '0');
/*     */       }
/*     */     }
/* 256 */     return sb.toString();
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.io.ccitt.PackedBitArray
 * JD-Core Version:    0.6.2
 */