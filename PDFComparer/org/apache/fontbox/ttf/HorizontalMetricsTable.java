/*    */ package org.apache.fontbox.ttf;
/*    */ 
/*    */ import java.io.IOException;
/*    */ 
/*    */ public class HorizontalMetricsTable extends TTFTable
/*    */ {
/*    */   public static final String TAG = "hmtx";
/*    */   private int[] advanceWidth;
/*    */   private short[] leftSideBearing;
/*    */   private short[] nonHorizontalLeftSideBearing;
/*    */ 
/*    */   public void initData(TrueTypeFont ttf, TTFDataStream data)
/*    */     throws IOException
/*    */   {
/* 46 */     HorizontalHeaderTable hHeader = ttf.getHorizontalHeader();
/* 47 */     int numHMetrics = hHeader.getNumberOfHMetrics();
/* 48 */     int numGlyphs = ttf.getNumberOfGlyphs();
/*    */ 
/* 50 */     this.advanceWidth = new int[numHMetrics];
/* 51 */     this.leftSideBearing = new short[numHMetrics];
/* 52 */     for (int i = 0; i < numHMetrics; i++)
/*    */     {
/* 54 */       this.advanceWidth[i] = data.readUnsignedShort();
/* 55 */       this.leftSideBearing[i] = data.readSignedShort();
/*    */     }
/*    */ 
/* 58 */     int numberNonHorizontal = numGlyphs - numHMetrics;
/* 59 */     this.nonHorizontalLeftSideBearing = new short[numberNonHorizontal];
/* 60 */     for (int i = 0; i < numberNonHorizontal; i++)
/*    */     {
/* 62 */       this.nonHorizontalLeftSideBearing[i] = data.readSignedShort();
/*    */     }
/* 64 */     this.initialized = true;
/*    */   }
/*    */ 
/*    */   public int[] getAdvanceWidth()
/*    */   {
/* 71 */     return this.advanceWidth;
/*    */   }
/*    */ 
/*    */   public void setAdvanceWidth(int[] advanceWidthValue)
/*    */   {
/* 78 */     this.advanceWidth = advanceWidthValue;
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.fontbox.ttf.HorizontalMetricsTable
 * JD-Core Version:    0.6.2
 */