/*     */ package org.apache.fontbox.cff;
/*     */ 
/*     */ import java.io.EOFException;
/*     */ import java.io.IOException;
/*     */ 
/*     */ public class DataInput
/*     */ {
/*  31 */   private byte[] inputBuffer = null;
/*  32 */   private int bufferPosition = 0;
/*     */ 
/*     */   public DataInput(byte[] buffer)
/*     */   {
/*  40 */     this.inputBuffer = buffer;
/*     */   }
/*     */ 
/*     */   public boolean hasRemaining()
/*     */   {
/*  49 */     return this.bufferPosition < this.inputBuffer.length;
/*     */   }
/*     */ 
/*     */   public int getPosition()
/*     */   {
/*  58 */     return this.bufferPosition;
/*     */   }
/*     */ 
/*     */   public void setPosition(int position)
/*     */   {
/*  67 */     this.bufferPosition = position;
/*     */   }
/*     */ 
/*     */   public String getString()
/*     */     throws IOException
/*     */   {
/*  77 */     return new String(this.inputBuffer, "ISO-8859-1");
/*     */   }
/*     */ 
/*     */   public byte readByte()
/*     */     throws IOException
/*     */   {
/*  87 */     return (byte)readUnsignedByte();
/*     */   }
/*     */ 
/*     */   public int readUnsignedByte()
/*     */     throws IOException
/*     */   {
/*  97 */     int b = read();
/*  98 */     if (b < 0)
/*     */     {
/* 100 */       throw new EOFException();
/*     */     }
/* 102 */     return b;
/*     */   }
/*     */ 
/*     */   public short readShort()
/*     */     throws IOException
/*     */   {
/* 112 */     return (short)readUnsignedShort();
/*     */   }
/*     */ 
/*     */   public int readUnsignedShort()
/*     */     throws IOException
/*     */   {
/* 122 */     int b1 = read();
/* 123 */     int b2 = read();
/* 124 */     if ((b1 | b2) < 0)
/*     */     {
/* 126 */       throw new EOFException();
/*     */     }
/* 128 */     return b1 << 8 | b2;
/*     */   }
/*     */ 
/*     */   public int readInt()
/*     */     throws IOException
/*     */   {
/* 138 */     int b1 = read();
/* 139 */     int b2 = read();
/* 140 */     int b3 = read();
/* 141 */     int b4 = read();
/* 142 */     if ((b1 | b2 | b3 | b4) < 0)
/*     */     {
/* 144 */       throw new EOFException();
/*     */     }
/* 146 */     return b1 << 24 | b2 << 16 | b3 << 8 | b4;
/*     */   }
/*     */ 
/*     */   public byte[] readBytes(int length)
/*     */     throws IOException
/*     */   {
/* 157 */     byte[] bytes = new byte[length];
/* 158 */     for (int i = 0; i < length; i++)
/*     */     {
/* 160 */       bytes[i] = readByte();
/*     */     }
/* 162 */     return bytes;
/*     */   }
/*     */ 
/*     */   private int read()
/*     */   {
/*     */     try
/*     */     {
/* 169 */       int value = this.inputBuffer[this.bufferPosition] & 0xFF;
/* 170 */       this.bufferPosition += 1;
/* 171 */       return value;
/*     */     }
/*     */     catch (RuntimeException re) {
/*     */     }
/* 175 */     return -1;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.fontbox.cff.DataInput
 * JD-Core Version:    0.6.2
 */