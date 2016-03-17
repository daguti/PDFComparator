/*     */ package com.itextpdf.text;
/*     */ 
/*     */ import com.itextpdf.text.error_messages.MessageLocalization;
/*     */ 
/*     */ public class RectangleReadOnly extends Rectangle
/*     */ {
/*     */   public RectangleReadOnly(float llx, float lly, float urx, float ury)
/*     */   {
/*  76 */     super(llx, lly, urx, ury);
/*     */   }
/*     */ 
/*     */   public RectangleReadOnly(float llx, float lly, float urx, float ury, int rotation)
/*     */   {
/*  90 */     super(llx, lly, urx, ury);
/*  91 */     super.setRotation(rotation);
/*     */   }
/*     */ 
/*     */   public RectangleReadOnly(float urx, float ury)
/*     */   {
/* 102 */     super(0.0F, 0.0F, urx, ury);
/*     */   }
/*     */ 
/*     */   public RectangleReadOnly(float urx, float ury, int rotation)
/*     */   {
/* 115 */     super(0.0F, 0.0F, urx, ury);
/* 116 */     super.setRotation(rotation);
/*     */   }
/*     */ 
/*     */   public RectangleReadOnly(Rectangle rect)
/*     */   {
/* 125 */     super(rect.llx, rect.lly, rect.urx, rect.ury);
/* 126 */     super.cloneNonPositionParameters(rect);
/*     */   }
/*     */ 
/*     */   private void throwReadOnlyError()
/*     */   {
/* 133 */     throw new UnsupportedOperationException(MessageLocalization.getComposedMessage("rectanglereadonly.this.rectangle.is.read.only", new Object[0]));
/*     */   }
/*     */ 
/*     */   public void setRotation(int rotation)
/*     */   {
/* 143 */     throwReadOnlyError();
/*     */   }
/*     */ 
/*     */   public void setLeft(float llx)
/*     */   {
/* 155 */     throwReadOnlyError();
/*     */   }
/*     */ 
/*     */   public void setRight(float urx)
/*     */   {
/* 166 */     throwReadOnlyError();
/*     */   }
/*     */ 
/*     */   public void setTop(float ury)
/*     */   {
/* 176 */     throwReadOnlyError();
/*     */   }
/*     */ 
/*     */   public void setBottom(float lly)
/*     */   {
/* 186 */     throwReadOnlyError();
/*     */   }
/*     */ 
/*     */   public void normalize()
/*     */   {
/* 195 */     throwReadOnlyError();
/*     */   }
/*     */ 
/*     */   public void setBackgroundColor(BaseColor value)
/*     */   {
/* 207 */     throwReadOnlyError();
/*     */   }
/*     */ 
/*     */   public void setGrayFill(float value)
/*     */   {
/* 217 */     throwReadOnlyError();
/*     */   }
/*     */ 
/*     */   public void setBorder(int border)
/*     */   {
/* 233 */     throwReadOnlyError();
/*     */   }
/*     */ 
/*     */   public void setUseVariableBorders(boolean useVariableBorders)
/*     */   {
/* 243 */     throwReadOnlyError();
/*     */   }
/*     */ 
/*     */   public void enableBorderSide(int side)
/*     */   {
/* 254 */     throwReadOnlyError();
/*     */   }
/*     */ 
/*     */   public void disableBorderSide(int side)
/*     */   {
/* 265 */     throwReadOnlyError();
/*     */   }
/*     */ 
/*     */   public void setBorderWidth(float borderWidth)
/*     */   {
/* 278 */     throwReadOnlyError();
/*     */   }
/*     */ 
/*     */   public void setBorderWidthLeft(float borderWidthLeft)
/*     */   {
/* 288 */     throwReadOnlyError();
/*     */   }
/*     */ 
/*     */   public void setBorderWidthRight(float borderWidthRight)
/*     */   {
/* 298 */     throwReadOnlyError();
/*     */   }
/*     */ 
/*     */   public void setBorderWidthTop(float borderWidthTop)
/*     */   {
/* 308 */     throwReadOnlyError();
/*     */   }
/*     */ 
/*     */   public void setBorderWidthBottom(float borderWidthBottom)
/*     */   {
/* 318 */     throwReadOnlyError();
/*     */   }
/*     */ 
/*     */   public void setBorderColor(BaseColor borderColor)
/*     */   {
/* 331 */     throwReadOnlyError();
/*     */   }
/*     */ 
/*     */   public void setBorderColorLeft(BaseColor borderColorLeft)
/*     */   {
/* 341 */     throwReadOnlyError();
/*     */   }
/*     */ 
/*     */   public void setBorderColorRight(BaseColor borderColorRight)
/*     */   {
/* 351 */     throwReadOnlyError();
/*     */   }
/*     */ 
/*     */   public void setBorderColorTop(BaseColor borderColorTop)
/*     */   {
/* 361 */     throwReadOnlyError();
/*     */   }
/*     */ 
/*     */   public void setBorderColorBottom(BaseColor borderColorBottom)
/*     */   {
/* 371 */     throwReadOnlyError();
/*     */   }
/*     */ 
/*     */   public void cloneNonPositionParameters(Rectangle rect)
/*     */   {
/* 384 */     throwReadOnlyError();
/*     */   }
/*     */ 
/*     */   public void softCloneNonPositionParameters(Rectangle rect)
/*     */   {
/* 395 */     throwReadOnlyError();
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 404 */     StringBuffer buf = new StringBuffer("RectangleReadOnly: ");
/* 405 */     buf.append(getWidth());
/* 406 */     buf.append('x');
/* 407 */     buf.append(getHeight());
/* 408 */     buf.append(" (rot: ");
/* 409 */     buf.append(this.rotation);
/* 410 */     buf.append(" degrees)");
/* 411 */     return buf.toString();
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.RectangleReadOnly
 * JD-Core Version:    0.6.2
 */