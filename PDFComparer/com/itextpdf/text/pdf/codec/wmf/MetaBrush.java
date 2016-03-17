/*    */ package com.itextpdf.text.pdf.codec.wmf;
/*    */ 
/*    */ import com.itextpdf.text.BaseColor;
/*    */ import java.io.IOException;
/*    */ 
/*    */ public class MetaBrush extends MetaObject
/*    */ {
/*    */   public static final int BS_SOLID = 0;
/*    */   public static final int BS_NULL = 1;
/*    */   public static final int BS_HATCHED = 2;
/*    */   public static final int BS_PATTERN = 3;
/*    */   public static final int BS_DIBPATTERN = 5;
/*    */   public static final int HS_HORIZONTAL = 0;
/*    */   public static final int HS_VERTICAL = 1;
/*    */   public static final int HS_FDIAGONAL = 2;
/*    */   public static final int HS_BDIAGONAL = 3;
/*    */   public static final int HS_CROSS = 4;
/*    */   public static final int HS_DIAGCROSS = 5;
/* 63 */   int style = 0;
/*    */   int hatch;
/* 65 */   BaseColor color = BaseColor.WHITE;
/*    */ 
/*    */   public MetaBrush() {
/* 68 */     this.type = 2;
/*    */   }
/*    */ 
/*    */   public void init(InputMeta in) throws IOException {
/* 72 */     this.style = in.readWord();
/* 73 */     this.color = in.readColor();
/* 74 */     this.hatch = in.readWord();
/*    */   }
/*    */ 
/*    */   public int getStyle() {
/* 78 */     return this.style;
/*    */   }
/*    */ 
/*    */   public int getHatch() {
/* 82 */     return this.hatch;
/*    */   }
/*    */ 
/*    */   public BaseColor getColor() {
/* 86 */     return this.color;
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.pdf.codec.wmf.MetaBrush
 * JD-Core Version:    0.6.2
 */