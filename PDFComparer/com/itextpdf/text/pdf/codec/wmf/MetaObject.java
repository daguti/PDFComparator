/*    */ package com.itextpdf.text.pdf.codec.wmf;
/*    */ 
/*    */ public class MetaObject
/*    */ {
/*    */   public static final int META_NOT_SUPPORTED = 0;
/*    */   public static final int META_PEN = 1;
/*    */   public static final int META_BRUSH = 2;
/*    */   public static final int META_FONT = 3;
/* 52 */   public int type = 0;
/*    */ 
/*    */   public MetaObject() {
/*    */   }
/*    */ 
/*    */   public MetaObject(int type) {
/* 58 */     this.type = type;
/*    */   }
/*    */ 
/*    */   public int getType() {
/* 62 */     return this.type;
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.pdf.codec.wmf.MetaObject
 * JD-Core Version:    0.6.2
 */