/*    */ package com.itextpdf.text.pdf.draw;
/*    */ 
/*    */ import com.itextpdf.text.pdf.PdfContentByte;
/*    */ 
/*    */ public class DottedLineSeparator extends LineSeparator
/*    */ {
/* 58 */   protected float gap = 5.0F;
/*    */ 
/*    */   public void draw(PdfContentByte canvas, float llx, float lly, float urx, float ury, float y)
/*    */   {
/* 64 */     canvas.saveState();
/* 65 */     canvas.setLineWidth(this.lineWidth);
/* 66 */     canvas.setLineCap(1);
/* 67 */     canvas.setLineDash(0.0F, this.gap, this.gap / 2.0F);
/* 68 */     drawLine(canvas, llx, urx, y);
/* 69 */     canvas.restoreState();
/*    */   }
/*    */ 
/*    */   public float getGap()
/*    */   {
/* 77 */     return this.gap;
/*    */   }
/*    */ 
/*    */   public void setGap(float gap)
/*    */   {
/* 85 */     this.gap = gap;
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.pdf.draw.DottedLineSeparator
 * JD-Core Version:    0.6.2
 */