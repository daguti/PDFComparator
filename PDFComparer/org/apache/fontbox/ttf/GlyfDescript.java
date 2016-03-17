/*     */ package org.apache.fontbox.ttf;
/*     */ 
/*     */ import java.io.IOException;
/*     */ 
/*     */ public abstract class GlyfDescript
/*     */   implements GlyphDescription
/*     */ {
/*     */   public static final byte ON_CURVE = 1;
/*     */   public static final byte X_SHORT_VECTOR = 2;
/*     */   public static final byte Y_SHORT_VECTOR = 4;
/*     */   public static final byte REPEAT = 8;
/*     */   public static final byte X_DUAL = 16;
/*     */   public static final byte Y_DUAL = 32;
/*     */   private int[] instructions;
/*     */   private int contourCount;
/*     */ 
/*     */   protected GlyfDescript(short numberOfContours, TTFDataStream bais)
/*     */     throws IOException
/*     */   {
/*  80 */     this.contourCount = numberOfContours;
/*     */   }
/*     */ 
/*     */   public void resolve()
/*     */   {
/*     */   }
/*     */ 
/*     */   public int getContourCount()
/*     */   {
/*  95 */     return this.contourCount;
/*     */   }
/*     */ 
/*     */   public int[] getInstructions()
/*     */   {
/* 104 */     return this.instructions;
/*     */   }
/*     */ 
/*     */   protected void readInstructions(TTFDataStream bais, int count)
/*     */     throws IOException
/*     */   {
/* 115 */     this.instructions = bais.readUnsignedByteArray(count);
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.fontbox.ttf.GlyfDescript
 * JD-Core Version:    0.6.2
 */