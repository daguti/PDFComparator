/*    */ package org.apache.pdfbox.pdmodel.graphics.shading;
/*    */ 
/*    */ import org.apache.pdfbox.cos.COSDictionary;
/*    */ import org.apache.pdfbox.cos.COSName;
/*    */ 
/*    */ public class PDShadingType5 extends PDTriangleBasedShadingType
/*    */ {
/*    */   public PDShadingType5(COSDictionary shadingDictionary)
/*    */   {
/* 36 */     super(shadingDictionary);
/*    */   }
/*    */ 
/*    */   public int getShadingType()
/*    */   {
/* 45 */     return 5;
/*    */   }
/*    */ 
/*    */   public int getVerticesPerRow()
/*    */   {
/* 56 */     return getCOSDictionary().getInt(COSName.VERTICES_PER_ROW, -1);
/*    */   }
/*    */ 
/*    */   public void setVerticesPerRow(int vpr)
/*    */   {
/* 66 */     getCOSDictionary().setInt(COSName.VERTICES_PER_ROW, vpr);
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.pdmodel.graphics.shading.PDShadingType5
 * JD-Core Version:    0.6.2
 */