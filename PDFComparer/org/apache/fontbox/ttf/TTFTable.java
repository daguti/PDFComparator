/*     */ package org.apache.fontbox.ttf;
/*     */ 
/*     */ import java.io.IOException;
/*     */ 
/*     */ public class TTFTable
/*     */ {
/*     */   private String tag;
/*     */   private long checkSum;
/*     */   private long offset;
/*     */   private long length;
/*  36 */   protected boolean initialized = false;
/*     */ 
/*     */   public long getCheckSum()
/*     */   {
/*  43 */     return this.checkSum;
/*     */   }
/*     */ 
/*     */   public void setCheckSum(long checkSumValue)
/*     */   {
/*  50 */     this.checkSum = checkSumValue;
/*     */   }
/*     */ 
/*     */   public long getLength()
/*     */   {
/*  57 */     return this.length;
/*     */   }
/*     */ 
/*     */   public void setLength(long lengthValue)
/*     */   {
/*  64 */     this.length = lengthValue;
/*     */   }
/*     */ 
/*     */   public long getOffset()
/*     */   {
/*  71 */     return this.offset;
/*     */   }
/*     */ 
/*     */   public void setOffset(long offsetValue)
/*     */   {
/*  78 */     this.offset = offsetValue;
/*     */   }
/*     */ 
/*     */   public String getTag()
/*     */   {
/*  85 */     return this.tag;
/*     */   }
/*     */ 
/*     */   public void setTag(String tagValue)
/*     */   {
/*  92 */     this.tag = tagValue;
/*     */   }
/*     */ 
/*     */   public boolean getInitialized()
/*     */   {
/* 102 */     return this.initialized;
/*     */   }
/*     */ 
/*     */   public void initData(TrueTypeFont ttf, TTFDataStream data)
/*     */     throws IOException
/*     */   {
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.fontbox.ttf.TTFTable
 * JD-Core Version:    0.6.2
 */