/*     */ package com.itextpdf.text.pdf;
/*     */ 
/*     */ import com.itextpdf.text.DocumentException;
/*     */ import com.itextpdf.text.Element;
/*     */ import com.itextpdf.text.api.Spaceable;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ 
/*     */ public class FloatLayout
/*     */ {
/*     */   protected float maxY;
/*     */   protected float minY;
/*     */   protected float leftX;
/*     */   protected float rightX;
/*     */   protected float yLine;
/*     */   protected float floatLeftX;
/*     */   protected float floatRightX;
/*     */   protected float filledWidth;
/*     */   protected final ColumnText compositeColumn;
/*     */   protected final List<Element> content;
/*     */   protected final boolean useAscender;
/*     */ 
/*     */   public float getYLine()
/*     */   {
/*  68 */     return this.yLine;
/*     */   }
/*     */ 
/*     */   public void setYLine(float yLine) {
/*  72 */     this.yLine = yLine;
/*     */   }
/*     */ 
/*     */   public float getFilledWidth()
/*     */   {
/*  82 */     return this.filledWidth;
/*     */   }
/*     */ 
/*     */   public void setFilledWidth(float filledWidth) {
/*  86 */     this.filledWidth = filledWidth;
/*     */   }
/*     */ 
/*     */   public FloatLayout(List<Element> elements, boolean useAscender)
/*     */   {
/*  98 */     this.compositeColumn = new ColumnText(null);
/*  99 */     this.compositeColumn.setUseAscender(useAscender);
/* 100 */     this.useAscender = useAscender;
/* 101 */     this.content = elements;
/*     */   }
/*     */ 
/*     */   public void setSimpleColumn(float llx, float lly, float urx, float ury) {
/* 105 */     this.leftX = Math.min(llx, urx);
/* 106 */     this.maxY = Math.max(lly, ury);
/* 107 */     this.minY = Math.min(lly, ury);
/* 108 */     this.rightX = Math.max(llx, urx);
/* 109 */     this.floatLeftX = this.leftX;
/* 110 */     this.floatRightX = this.rightX;
/* 111 */     this.yLine = this.maxY;
/* 112 */     this.filledWidth = 0.0F;
/*     */   }
/*     */ 
/*     */   public int layout(PdfContentByte canvas, boolean simulate) throws DocumentException {
/* 116 */     this.compositeColumn.setCanvas(canvas);
/* 117 */     int status = 1;
/*     */ 
/* 119 */     ArrayList floatingElements = new ArrayList();
/* 120 */     List content = simulate ? new ArrayList(this.content) : this.content;
/*     */ 
/* 122 */     while (!content.isEmpty()) {
/* 123 */       if ((content.get(0) instanceof PdfDiv)) {
/* 124 */         PdfDiv floatingElement = (PdfDiv)content.get(0);
/* 125 */         if ((floatingElement.getFloatType() == PdfDiv.FloatType.LEFT) || (floatingElement.getFloatType() == PdfDiv.FloatType.RIGHT)) {
/* 126 */           floatingElements.add(floatingElement);
/* 127 */           content.remove(0);
/*     */         } else {
/* 129 */           if (!floatingElements.isEmpty()) {
/* 130 */             status = floatingLayout(floatingElements, simulate);
/* 131 */             if ((status & 0x1) == 0)
/*     */             {
/*     */               break;
/*     */             }
/*     */           }
/* 136 */           content.remove(0);
/*     */ 
/* 138 */           status = floatingElement.layout(canvas, this.useAscender, true, this.floatLeftX, this.minY, this.floatRightX, this.yLine);
/*     */ 
/* 140 */           if (!simulate) {
/* 141 */             canvas.openMCBlock(floatingElement);
/* 142 */             status = floatingElement.layout(canvas, this.useAscender, simulate, this.floatLeftX, this.minY, this.floatRightX, this.yLine);
/* 143 */             canvas.closeMCBlock(floatingElement);
/*     */           }
/*     */ 
/* 146 */           if (floatingElement.getActualWidth() > this.filledWidth) {
/* 147 */             this.filledWidth = floatingElement.getActualWidth();
/*     */           }
/* 149 */           if ((status & 0x1) == 0) {
/* 150 */             content.add(0, floatingElement);
/* 151 */             this.yLine = floatingElement.getYLine();
/* 152 */             break;
/*     */           }
/* 154 */           this.yLine -= floatingElement.getActualHeight();
/*     */         }
/*     */       }
/*     */       else {
/* 158 */         floatingElements.add(content.get(0));
/* 159 */         content.remove(0);
/*     */       }
/*     */     }
/*     */ 
/* 163 */     if (((status & 0x1) != 0) && (!floatingElements.isEmpty())) {
/* 164 */       status = floatingLayout(floatingElements, simulate);
/*     */     }
/*     */ 
/* 167 */     content.addAll(0, floatingElements);
/*     */ 
/* 169 */     return status;
/*     */   }
/*     */ 
/*     */   private int floatingLayout(List<Element> floatingElements, boolean simulate) throws DocumentException {
/* 173 */     int status = 1;
/* 174 */     float minYLine = this.yLine;
/* 175 */     float leftWidth = 0.0F;
/* 176 */     float rightWidth = 0.0F;
/*     */ 
/* 178 */     ColumnText currentCompositeColumn = this.compositeColumn;
/* 179 */     if (simulate) {
/* 180 */       currentCompositeColumn = ColumnText.duplicate(this.compositeColumn);
/*     */     }
/*     */ 
/* 183 */     while (!floatingElements.isEmpty()) {
/* 184 */       Element nextElement = (Element)floatingElements.get(0);
/* 185 */       floatingElements.remove(0);
/* 186 */       if ((nextElement instanceof PdfDiv)) {
/* 187 */         PdfDiv floatingElement = (PdfDiv)nextElement;
/* 188 */         status = floatingElement.layout(this.compositeColumn.getCanvas(), this.useAscender, true, this.floatLeftX, this.minY, this.floatRightX, this.yLine);
/* 189 */         if ((status & 0x1) == 0) {
/* 190 */           this.yLine = minYLine;
/* 191 */           this.floatLeftX = this.leftX;
/* 192 */           this.floatRightX = this.rightX;
/* 193 */           status = floatingElement.layout(this.compositeColumn.getCanvas(), this.useAscender, true, this.floatLeftX, this.minY, this.floatRightX, this.yLine);
/* 194 */           if ((status & 0x1) == 0) {
/* 195 */             floatingElements.add(0, floatingElement);
/* 196 */             break;
/*     */           }
/*     */         }
/* 199 */         if (floatingElement.getFloatType() == PdfDiv.FloatType.LEFT) {
/* 200 */           status = floatingElement.layout(this.compositeColumn.getCanvas(), this.useAscender, simulate, this.floatLeftX, this.minY, this.floatRightX, this.yLine);
/* 201 */           this.floatLeftX += floatingElement.getActualWidth();
/* 202 */           leftWidth += floatingElement.getActualWidth();
/* 203 */         } else if (floatingElement.getFloatType() == PdfDiv.FloatType.RIGHT) {
/* 204 */           status = floatingElement.layout(this.compositeColumn.getCanvas(), this.useAscender, simulate, this.floatRightX - floatingElement.getActualWidth() - 0.01F, this.minY, this.floatRightX, this.yLine);
/* 205 */           this.floatRightX -= floatingElement.getActualWidth();
/* 206 */           rightWidth += floatingElement.getActualWidth();
/*     */         }
/* 208 */         minYLine = Math.min(minYLine, this.yLine - floatingElement.getActualHeight());
/*     */       } else {
/* 210 */         if ((nextElement instanceof Spaceable)) {
/* 211 */           this.yLine -= ((Spaceable)nextElement).getSpacingBefore();
/*     */         }
/* 213 */         if (simulate) {
/* 214 */           if ((nextElement instanceof PdfPTable))
/* 215 */             currentCompositeColumn.addElement(new PdfPTable((PdfPTable)nextElement));
/*     */           else
/* 217 */             currentCompositeColumn.addElement(nextElement);
/*     */         }
/* 219 */         else currentCompositeColumn.addElement(nextElement);
/*     */ 
/* 222 */         if (this.yLine > minYLine)
/* 223 */           currentCompositeColumn.setSimpleColumn(this.floatLeftX, this.yLine, this.floatRightX, minYLine);
/*     */         else {
/* 225 */           currentCompositeColumn.setSimpleColumn(this.floatLeftX, this.yLine, this.floatRightX, this.minY);
/*     */         }
/* 227 */         currentCompositeColumn.setFilledWidth(0.0F);
/*     */ 
/* 229 */         status = currentCompositeColumn.go(simulate);
/* 230 */         if ((this.yLine > minYLine) && ((this.floatLeftX > this.leftX) || (this.floatRightX < this.rightX)) && ((status & 0x1) == 0)) {
/* 231 */           this.yLine = minYLine;
/* 232 */           this.floatLeftX = this.leftX;
/* 233 */           this.floatRightX = this.rightX;
/* 234 */           if ((leftWidth != 0.0F) && (rightWidth != 0.0F)) {
/* 235 */             this.filledWidth = (this.rightX - this.leftX);
/*     */           } else {
/* 237 */             if (leftWidth > this.filledWidth) {
/* 238 */               this.filledWidth = leftWidth;
/*     */             }
/* 240 */             if (rightWidth > this.filledWidth) {
/* 241 */               this.filledWidth = rightWidth;
/*     */             }
/*     */           }
/*     */ 
/* 245 */           leftWidth = 0.0F;
/* 246 */           rightWidth = 0.0F;
/* 247 */           if ((simulate) && ((nextElement instanceof PdfPTable))) {
/* 248 */             currentCompositeColumn.addElement(new PdfPTable((PdfPTable)nextElement));
/*     */           }
/*     */ 
/* 251 */           currentCompositeColumn.setSimpleColumn(this.floatLeftX, this.yLine, this.floatRightX, this.minY);
/* 252 */           status = currentCompositeColumn.go(simulate);
/* 253 */           minYLine = currentCompositeColumn.getYLine() + currentCompositeColumn.getDescender();
/* 254 */           this.yLine = minYLine;
/* 255 */           if (currentCompositeColumn.getFilledWidth() > this.filledWidth)
/* 256 */             this.filledWidth = currentCompositeColumn.getFilledWidth();
/*     */         }
/*     */         else {
/* 259 */           if (rightWidth > 0.0F)
/* 260 */             rightWidth += currentCompositeColumn.getFilledWidth();
/* 261 */           else if (leftWidth > 0.0F)
/* 262 */             leftWidth += currentCompositeColumn.getFilledWidth();
/* 263 */           else if (currentCompositeColumn.getFilledWidth() > this.filledWidth) {
/* 264 */             this.filledWidth = currentCompositeColumn.getFilledWidth();
/*     */           }
/* 266 */           minYLine = Math.min(currentCompositeColumn.getYLine() + currentCompositeColumn.getDescender(), minYLine);
/* 267 */           this.yLine = (currentCompositeColumn.getYLine() + currentCompositeColumn.getDescender());
/*     */         }
/*     */ 
/* 270 */         if ((status & 0x1) == 0) {
/* 271 */           if (!simulate) {
/* 272 */             floatingElements.addAll(0, currentCompositeColumn.getCompositeElements());
/* 273 */             currentCompositeColumn.getCompositeElements().clear(); break;
/*     */           }
/* 275 */           floatingElements.add(0, nextElement);
/* 276 */           currentCompositeColumn.setText(null);
/*     */ 
/* 278 */           break;
/*     */         }
/* 280 */         currentCompositeColumn.setText(null);
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 286 */     if ((leftWidth != 0.0F) && (rightWidth != 0.0F)) {
/* 287 */       this.filledWidth = (this.rightX - this.leftX);
/*     */     } else {
/* 289 */       if (leftWidth > this.filledWidth) {
/* 290 */         this.filledWidth = leftWidth;
/*     */       }
/* 292 */       if (rightWidth > this.filledWidth) {
/* 293 */         this.filledWidth = rightWidth;
/*     */       }
/*     */     }
/*     */ 
/* 297 */     this.yLine = minYLine;
/* 298 */     this.floatLeftX = this.leftX;
/* 299 */     this.floatRightX = this.rightX;
/*     */ 
/* 301 */     return status;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.pdf.FloatLayout
 * JD-Core Version:    0.6.2
 */