/*     */ package com.itextpdf.text.pdf.draw;
/*     */ 
/*     */ import com.itextpdf.text.BaseColor;
/*     */ import com.itextpdf.text.Font;
/*     */ import com.itextpdf.text.pdf.PdfContentByte;
/*     */ 
/*     */ public class LineSeparator extends VerticalPositionMark
/*     */ {
/*  63 */   protected float lineWidth = 1.0F;
/*     */ 
/*  65 */   protected float percentage = 100.0F;
/*     */   protected BaseColor lineColor;
/*  69 */   protected int alignment = 6;
/*     */ 
/*     */   public LineSeparator(float lineWidth, float percentage, BaseColor lineColor, int align, float offset)
/*     */   {
/*  80 */     this.lineWidth = lineWidth;
/*  81 */     this.percentage = percentage;
/*  82 */     this.lineColor = lineColor;
/*  83 */     this.alignment = align;
/*  84 */     this.offset = offset;
/*     */   }
/*     */ 
/*     */   public LineSeparator(Font font)
/*     */   {
/*  92 */     this.lineWidth = (0.0666667F * font.getSize());
/*  93 */     this.offset = (-0.3333333F * font.getSize());
/*  94 */     this.percentage = 100.0F;
/*  95 */     this.lineColor = font.getColor();
/*     */   }
/*     */ 
/*     */   public LineSeparator()
/*     */   {
/*     */   }
/*     */ 
/*     */   public void draw(PdfContentByte canvas, float llx, float lly, float urx, float ury, float y)
/*     */   {
/* 109 */     canvas.saveState();
/* 110 */     drawLine(canvas, llx, urx, y);
/* 111 */     canvas.restoreState();
/*     */   }
/*     */ 
/*     */   public void drawLine(PdfContentByte canvas, float leftX, float rightX, float y)
/*     */   {
/*     */     float w;
/*     */     float w;
/* 123 */     if (getPercentage() < 0.0F)
/* 124 */       w = -getPercentage();
/*     */     else
/* 126 */       w = (rightX - leftX) * getPercentage() / 100.0F;
/*     */     float s;
/* 128 */     switch (getAlignment()) {
/*     */     case 0:
/* 130 */       s = 0.0F;
/* 131 */       break;
/*     */     case 2:
/* 133 */       s = rightX - leftX - w;
/* 134 */       break;
/*     */     default:
/* 136 */       s = (rightX - leftX - w) / 2.0F;
/*     */     }
/*     */ 
/* 139 */     canvas.setLineWidth(getLineWidth());
/* 140 */     if (getLineColor() != null)
/* 141 */       canvas.setColorStroke(getLineColor());
/* 142 */     canvas.moveTo(s + leftX, y + this.offset);
/* 143 */     canvas.lineTo(s + w + leftX, y + this.offset);
/* 144 */     canvas.stroke();
/*     */   }
/*     */ 
/*     */   public float getLineWidth()
/*     */   {
/* 152 */     return this.lineWidth;
/*     */   }
/*     */ 
/*     */   public void setLineWidth(float lineWidth)
/*     */   {
/* 160 */     this.lineWidth = lineWidth;
/*     */   }
/*     */ 
/*     */   public float getPercentage()
/*     */   {
/* 168 */     return this.percentage;
/*     */   }
/*     */ 
/*     */   public void setPercentage(float percentage)
/*     */   {
/* 176 */     this.percentage = percentage;
/*     */   }
/*     */ 
/*     */   public BaseColor getLineColor()
/*     */   {
/* 184 */     return this.lineColor;
/*     */   }
/*     */ 
/*     */   public void setLineColor(BaseColor color)
/*     */   {
/* 192 */     this.lineColor = color;
/*     */   }
/*     */ 
/*     */   public int getAlignment()
/*     */   {
/* 200 */     return this.alignment;
/*     */   }
/*     */ 
/*     */   public void setAlignment(int align)
/*     */   {
/* 208 */     this.alignment = align;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.pdf.draw.LineSeparator
 * JD-Core Version:    0.6.2
 */