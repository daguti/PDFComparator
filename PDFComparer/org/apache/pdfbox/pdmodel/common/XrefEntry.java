/*    */ package org.apache.pdfbox.pdmodel.common;
/*    */ 
/*    */ public class XrefEntry
/*    */ {
/* 25 */   private int objectNumber = 0;
/* 26 */   private int byteOffset = 0;
/* 27 */   private int generation = 0;
/* 28 */   private boolean inUse = true;
/*    */ 
/*    */   public XrefEntry() {
/*    */   }
/*    */ 
/*    */   public XrefEntry(int objectNumber, int byteOffset, int generation, String inUse) {
/* 34 */     this.objectNumber = objectNumber;
/* 35 */     this.byteOffset = byteOffset;
/* 36 */     this.generation = generation;
/* 37 */     this.inUse = "n".equals(inUse);
/*    */   }
/*    */ 
/*    */   public int getByteOffset() {
/* 41 */     return this.byteOffset;
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.pdmodel.common.XrefEntry
 * JD-Core Version:    0.6.2
 */