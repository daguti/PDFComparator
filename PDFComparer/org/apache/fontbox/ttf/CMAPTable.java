/*     */ package org.apache.fontbox.ttf;
/*     */ 
/*     */ import java.io.IOException;
/*     */ 
/*     */ public class CMAPTable extends TTFTable
/*     */ {
/*     */   public static final String TAG = "cmap";
/*     */   public static final int PLATFORM_MISC = 0;
/*     */   public static final int PLATFORM_MACINTOSH = 1;
/*     */   public static final int PLATFORM_WINDOWS = 3;
/*     */   public static final int ENCODING_SYMBOL = 0;
/*     */   public static final int ENCODING_UNICODE = 1;
/*     */   public static final int ENCODING_SHIFT_JIS = 2;
/*     */   public static final int ENCODING_BIG5 = 3;
/*     */   public static final int ENCODING_PRC = 4;
/*     */   public static final int ENCODING_WANSUNG = 5;
/*     */   public static final int ENCODING_JOHAB = 6;
/*     */   private CMAPEncodingEntry[] cmaps;
/*     */ 
/*     */   public void initData(TrueTypeFont ttf, TTFDataStream data)
/*     */     throws IOException
/*     */   {
/*  89 */     int version = data.readUnsignedShort();
/*  90 */     int numberOfTables = data.readUnsignedShort();
/*  91 */     this.cmaps = new CMAPEncodingEntry[numberOfTables];
/*  92 */     for (int i = 0; i < numberOfTables; i++)
/*     */     {
/*  94 */       CMAPEncodingEntry cmap = new CMAPEncodingEntry();
/*  95 */       cmap.initData(data);
/*  96 */       this.cmaps[i] = cmap;
/*     */     }
/*  98 */     for (int i = 0; i < numberOfTables; i++)
/*     */     {
/* 100 */       this.cmaps[i].initSubtable(this, ttf.getNumberOfGlyphs(), data);
/*     */     }
/* 102 */     this.initialized = true;
/*     */   }
/*     */ 
/*     */   public CMAPEncodingEntry[] getCmaps()
/*     */   {
/* 109 */     return this.cmaps;
/*     */   }
/*     */ 
/*     */   public void setCmaps(CMAPEncodingEntry[] cmapsValue)
/*     */   {
/* 116 */     this.cmaps = cmapsValue;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.fontbox.ttf.CMAPTable
 * JD-Core Version:    0.6.2
 */