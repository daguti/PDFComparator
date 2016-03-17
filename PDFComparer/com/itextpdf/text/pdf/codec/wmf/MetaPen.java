/*    */ package com.itextpdf.text.pdf.codec.wmf;
/*    */ 
/*    */ import com.itextpdf.text.BaseColor;
/*    */ import java.io.IOException;
/*    */ 
/*    */ public class MetaPen extends MetaObject
/*    */ {
/*    */   public static final int PS_SOLID = 0;
/*    */   public static final int PS_DASH = 1;
/*    */   public static final int PS_DOT = 2;
/*    */   public static final int PS_DASHDOT = 3;
/*    */   public static final int PS_DASHDOTDOT = 4;
/*    */   public static final int PS_NULL = 5;
/*    */   public static final int PS_INSIDEFRAME = 6;
/* 59 */   int style = 0;
/* 60 */   int penWidth = 1;
/* 61 */   BaseColor color = BaseColor.BLACK;
/*    */ 
/*    */   public MetaPen() {
/* 64 */     this.type = 1;
/*    */   }
/*    */ 
/*    */   public void init(InputMeta in) throws IOException {
/* 68 */     this.style = in.readWord();
/* 69 */     this.penWidth = in.readShort();
/* 70 */     in.readWord();
/* 71 */     this.color = in.readColor();
/*    */   }
/*    */ 
/*    */   public int getStyle() {
/* 75 */     return this.style;
/*    */   }
/*    */ 
/*    */   public int getPenWidth() {
/* 79 */     return this.penWidth;
/*    */   }
/*    */ 
/*    */   public BaseColor getColor() {
/* 83 */     return this.color;
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.pdf.codec.wmf.MetaPen
 * JD-Core Version:    0.6.2
 */