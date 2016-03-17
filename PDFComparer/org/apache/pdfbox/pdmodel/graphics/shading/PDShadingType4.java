/*    */ package org.apache.pdfbox.pdmodel.graphics.shading;
/*    */ 
/*    */ import org.apache.pdfbox.cos.COSDictionary;
/*    */ import org.apache.pdfbox.cos.COSName;
/*    */ 
/*    */ public class PDShadingType4 extends PDTriangleBasedShadingType
/*    */ {
/*    */   public PDShadingType4(COSDictionary shadingDictionary)
/*    */   {
/* 37 */     super(shadingDictionary);
/*    */   }
/*    */ 
/*    */   public int getShadingType()
/*    */   {
/* 46 */     return 4;
/*    */   }
/*    */ 
/*    */   public int getBitsPerFlag()
/*    */   {
/* 57 */     return getCOSDictionary().getInt(COSName.BITS_PER_FLAG, -1);
/*    */   }
/*    */ 
/*    */   public void setBitsPerFlag(int bpf)
/*    */   {
/* 67 */     getCOSDictionary().setInt(COSName.BITS_PER_FLAG, bpf);
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.pdmodel.graphics.shading.PDShadingType4
 * JD-Core Version:    0.6.2
 */