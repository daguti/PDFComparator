/*     */ package org.apache.fontbox.cff;
/*     */ 
/*     */ import java.util.Arrays;
/*     */ 
/*     */ public class IndexData
/*     */ {
/*     */   private int count;
/*     */   private int[] offset;
/*     */   private int[] data;
/*     */ 
/*     */   public IndexData(int count)
/*     */   {
/*  37 */     this.count = count;
/*  38 */     this.offset = new int[count + 1];
/*     */   }
/*     */ 
/*     */   public byte[] getBytes(int index)
/*     */   {
/*  43 */     int length = this.offset[(index + 1)] - this.offset[index];
/*  44 */     byte[] bytes = new byte[length];
/*  45 */     for (int i = 0; i < length; i++)
/*     */     {
/*  47 */       bytes[i] = ((byte)this.data[(this.offset[index] - 1 + i)]);
/*     */     }
/*  49 */     return bytes;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/*  55 */     return getClass().getName() + "[count=" + this.count + ", offset=" + Arrays.toString(this.offset) + ", data=" + Arrays.toString(this.data) + "]";
/*     */   }
/*     */ 
/*     */   public int getCount()
/*     */   {
/*  66 */     return this.count;
/*     */   }
/*     */ 
/*     */   public void setOffset(int index, int value)
/*     */   {
/*  76 */     this.offset[index] = value;
/*     */   }
/*     */ 
/*     */   public int getOffset(int index)
/*     */   {
/*  86 */     return this.offset[index];
/*     */   }
/*     */ 
/*     */   public void initData(int dataSize)
/*     */   {
/*  95 */     this.data = new int[dataSize];
/*     */   }
/*     */ 
/*     */   public void setData(int index, int value)
/*     */   {
/* 105 */     this.data[index] = value;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.fontbox.cff.IndexData
 * JD-Core Version:    0.6.2
 */