/*    */ package org.apache.fontbox.ttf;
/*    */ 
/*    */ import java.io.IOException;
/*    */ 
/*    */ public class IndexToLocationTable extends TTFTable
/*    */ {
/*    */   private static final short SHORT_OFFSETS = 0;
/*    */   private static final short LONG_OFFSETS = 1;
/*    */   public static final String TAG = "loca";
/*    */   private long[] offsets;
/*    */ 
/*    */   public void initData(TrueTypeFont ttf, TTFDataStream data)
/*    */     throws IOException
/*    */   {
/* 47 */     HeaderTable head = ttf.getHeader();
/* 48 */     int numGlyphs = ttf.getNumberOfGlyphs();
/* 49 */     this.offsets = new long[numGlyphs + 1];
/* 50 */     for (int i = 0; i < numGlyphs + 1; i++)
/*    */     {
/* 52 */       if (head.getIndexToLocFormat() == 0)
/*    */       {
/* 54 */         this.offsets[i] = (data.readUnsignedShort() * 2);
/*    */       }
/* 56 */       else if (head.getIndexToLocFormat() == 1)
/*    */       {
/* 58 */         this.offsets[i] = data.readUnsignedInt();
/*    */       }
/*    */       else
/*    */       {
/* 62 */         throw new IOException("Error:TTF.loca unknown offset format.");
/*    */       }
/*    */     }
/* 65 */     this.initialized = true;
/*    */   }
/*    */ 
/*    */   public long[] getOffsets()
/*    */   {
/* 72 */     return this.offsets;
/*    */   }
/*    */ 
/*    */   public void setOffsets(long[] offsetsValue)
/*    */   {
/* 79 */     this.offsets = offsetsValue;
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.fontbox.ttf.IndexToLocationTable
 * JD-Core Version:    0.6.2
 */