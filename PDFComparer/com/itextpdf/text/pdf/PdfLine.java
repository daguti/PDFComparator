/*     */ package com.itextpdf.text.pdf;
/*     */ 
/*     */ import com.itextpdf.text.Chunk;
/*     */ import com.itextpdf.text.Image;
/*     */ import com.itextpdf.text.ListItem;
/*     */ import com.itextpdf.text.TabStop;
/*     */ import com.itextpdf.text.TabStop.Alignment;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ 
/*     */ public class PdfLine
/*     */ {
/*     */   protected ArrayList<PdfChunk> line;
/*     */   protected float left;
/*     */   protected float width;
/*     */   protected int alignment;
/*     */   protected float height;
/*  83 */   protected boolean newlineSplit = false;
/*     */   protected float originalWidth;
/*  88 */   protected boolean isRTL = false;
/*     */ 
/*  90 */   protected ListItem listItem = null;
/*     */ 
/*  92 */   protected TabStop tabStop = null;
/*     */ 
/*  94 */   protected float tabStopAnchorPosition = (0.0F / 0.0F);
/*     */ 
/*  96 */   protected float tabPosition = (0.0F / 0.0F);
/*     */ 
/*     */   PdfLine(float left, float right, int alignment, float height)
/*     */   {
/* 110 */     this.left = left;
/* 111 */     this.width = (right - left);
/* 112 */     this.originalWidth = this.width;
/* 113 */     this.alignment = alignment;
/* 114 */     this.height = height;
/* 115 */     this.line = new ArrayList();
/*     */   }
/*     */ 
/*     */   PdfLine(float left, float originalWidth, float remainingWidth, int alignment, boolean newlineSplit, ArrayList<PdfChunk> line, boolean isRTL)
/*     */   {
/* 129 */     this.left = left;
/* 130 */     this.originalWidth = originalWidth;
/* 131 */     this.width = remainingWidth;
/* 132 */     this.alignment = alignment;
/* 133 */     this.line = line;
/* 134 */     this.newlineSplit = newlineSplit;
/* 135 */     this.isRTL = isRTL;
/*     */   }
/*     */ 
/*     */   PdfChunk add(PdfChunk chunk)
/*     */   {
/* 151 */     if ((chunk == null) || (chunk.toString().equals(""))) {
/* 152 */       return null;
/*     */     }
/*     */ 
/* 156 */     PdfChunk overflow = chunk.split(this.width);
/* 157 */     this.newlineSplit = ((chunk.isNewlineSplit()) || (overflow == null));
/* 158 */     if (chunk.isTab()) {
/* 159 */       Object[] tab = (Object[])chunk.getAttribute("TAB");
/* 160 */       if (chunk.isAttribute("TABSETTINGS")) {
/* 161 */         boolean isWhiteSpace = ((Boolean)tab[1]).booleanValue();
/* 162 */         if ((!isWhiteSpace) || (!this.line.isEmpty())) {
/* 163 */           flush();
/* 164 */           this.tabStopAnchorPosition = (0.0F / 0.0F);
/* 165 */           this.tabStop = PdfChunk.getTabStop(chunk, this.originalWidth - this.width);
/* 166 */           if (this.tabStop.getPosition() > this.originalWidth) {
/* 167 */             if (isWhiteSpace) {
/* 168 */               overflow = null;
/* 169 */             } else if (Math.abs(this.originalWidth - this.width) < 0.001D) {
/* 170 */               addToLine(chunk);
/* 171 */               overflow = null;
/*     */             } else {
/* 173 */               overflow = chunk;
/*     */             }
/* 175 */             this.width = 0.0F;
/*     */           } else {
/* 177 */             chunk.setTabStop(this.tabStop);
/* 178 */             if (this.tabStop.getAlignment() == TabStop.Alignment.LEFT) {
/* 179 */               this.width = (this.originalWidth - this.tabStop.getPosition());
/* 180 */               this.tabStop = null;
/* 181 */               this.tabPosition = (0.0F / 0.0F);
/*     */             } else {
/* 183 */               this.tabPosition = (this.originalWidth - this.width);
/* 184 */             }addToLine(chunk);
/*     */           }
/*     */         } else {
/* 187 */           return null;
/*     */         }
/*     */       } else {
/* 190 */         Float tabStopPosition = Float.valueOf(((Float)tab[1]).floatValue());
/* 191 */         boolean newline = ((Boolean)tab[2]).booleanValue();
/* 192 */         if ((newline) && (tabStopPosition.floatValue() < this.originalWidth - this.width)) {
/* 193 */           return chunk;
/*     */         }
/* 195 */         chunk.adjustLeft(this.left);
/* 196 */         this.width = (this.originalWidth - tabStopPosition.floatValue());
/* 197 */         addToLine(chunk);
/*     */       }
/*     */ 
/*     */     }
/* 201 */     else if ((chunk.length() > 0) || (chunk.isImage())) {
/* 202 */       if (overflow != null)
/* 203 */         chunk.trimLastSpace();
/* 204 */       this.width -= chunk.width();
/* 205 */       addToLine(chunk);
/*     */     }
/*     */     else
/*     */     {
/* 209 */       if (this.line.size() < 1) {
/* 210 */         chunk = overflow;
/* 211 */         overflow = chunk.truncate(this.width);
/* 212 */         this.width -= chunk.width();
/* 213 */         if (chunk.length() > 0) {
/* 214 */           addToLine(chunk);
/* 215 */           return overflow;
/*     */         }
/*     */ 
/* 219 */         if (overflow != null)
/* 220 */           addToLine(overflow);
/* 221 */         return null;
/*     */       }
/*     */ 
/* 225 */       this.width += ((PdfChunk)this.line.get(this.line.size() - 1)).trimLastSpace();
/*     */     }
/* 227 */     return overflow;
/*     */   }
/*     */ 
/*     */   private void addToLine(PdfChunk chunk) {
/* 231 */     if (chunk.changeLeading)
/*     */     {
/*     */       float f;
/*     */       float f;
/* 233 */       if (chunk.isImage()) {
/* 234 */         Image img = chunk.getImage();
/* 235 */         f = chunk.getImageHeight() + chunk.getImageOffsetY() + img.getBorderWidthTop() + img.getSpacingBefore();
/*     */       }
/*     */       else
/*     */       {
/* 239 */         f = chunk.getLeading();
/*     */       }
/* 241 */       if (f > this.height) this.height = f;
/*     */     }
/* 243 */     if ((this.tabStop != null) && (this.tabStop.getAlignment() == TabStop.Alignment.ANCHOR) && (Float.isNaN(this.tabStopAnchorPosition))) {
/* 244 */       String value = chunk.toString();
/* 245 */       int anchorIndex = value.indexOf(this.tabStop.getAnchorChar());
/* 246 */       if (anchorIndex != -1) {
/* 247 */         float subWidth = chunk.width(value.substring(anchorIndex, value.length()));
/* 248 */         this.tabStopAnchorPosition = (this.originalWidth - this.width - subWidth);
/*     */       }
/*     */     }
/* 251 */     this.line.add(chunk);
/*     */   }
/*     */ 
/*     */   public int size()
/*     */   {
/* 263 */     return this.line.size();
/*     */   }
/*     */ 
/*     */   public Iterator<PdfChunk> iterator()
/*     */   {
/* 273 */     return this.line.iterator();
/*     */   }
/*     */ 
/*     */   float height()
/*     */   {
/* 283 */     return this.height;
/*     */   }
/*     */ 
/*     */   float indentLeft()
/*     */   {
/* 293 */     if (this.isRTL) {
/* 294 */       switch (this.alignment) {
/*     */       case 0:
/* 296 */         return this.left + this.width;
/*     */       case 1:
/* 298 */         return this.left + this.width / 2.0F;
/*     */       }
/* 300 */       return this.left;
/*     */     }
/*     */ 
/* 303 */     if (getSeparatorCount() <= 0) {
/* 304 */       switch (this.alignment) {
/*     */       case 2:
/* 306 */         return this.left + this.width;
/*     */       case 1:
/* 308 */         return this.left + this.width / 2.0F;
/*     */       }
/*     */     }
/* 311 */     return this.left;
/*     */   }
/*     */ 
/*     */   public boolean hasToBeJustified()
/*     */   {
/* 321 */     return ((this.alignment == 3) && (!this.newlineSplit)) || ((this.alignment == 8) && (this.width != 0.0F));
/*     */   }
/*     */ 
/*     */   public void resetAlignment()
/*     */   {
/* 332 */     if (this.alignment == 3)
/* 333 */       this.alignment = 0;
/*     */   }
/*     */ 
/*     */   void setExtraIndent(float extra)
/*     */   {
/* 339 */     this.left += extra;
/* 340 */     this.width -= extra;
/* 341 */     this.originalWidth -= extra;
/*     */   }
/*     */ 
/*     */   float widthLeft()
/*     */   {
/* 351 */     return this.width;
/*     */   }
/*     */ 
/*     */   int numberOfSpaces()
/*     */   {
/* 361 */     int numberOfSpaces = 0;
/* 362 */     for (PdfChunk pdfChunk : this.line) {
/* 363 */       String tmp = pdfChunk.toString();
/* 364 */       int length = tmp.length();
/* 365 */       for (int i = 0; i < length; i++) {
/* 366 */         if (tmp.charAt(i) == ' ') {
/* 367 */           numberOfSpaces++;
/*     */         }
/*     */       }
/*     */     }
/* 371 */     return numberOfSpaces;
/*     */   }
/*     */ 
/*     */   public void setListItem(ListItem listItem)
/*     */   {
/* 383 */     this.listItem = listItem;
/*     */   }
/*     */ 
/*     */   public Chunk listSymbol()
/*     */   {
/* 395 */     return this.listItem != null ? this.listItem.getListSymbol() : null;
/*     */   }
/*     */ 
/*     */   public float listIndent()
/*     */   {
/* 405 */     return this.listItem != null ? this.listItem.getIndentationLeft() : 0.0F;
/*     */   }
/*     */ 
/*     */   public ListItem listItem() {
/* 409 */     return this.listItem;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 420 */     StringBuffer tmp = new StringBuffer();
/* 421 */     for (PdfChunk pdfChunk : this.line) {
/* 422 */       tmp.append(pdfChunk.toString());
/*     */     }
/* 424 */     return tmp.toString();
/*     */   }
/*     */ 
/*     */   public int getLineLengthUtf32()
/*     */   {
/* 433 */     int total = 0;
/* 434 */     for (Object element : this.line) {
/* 435 */       total += ((PdfChunk)element).lengthUtf32();
/*     */     }
/* 437 */     return total;
/*     */   }
/*     */ 
/*     */   public boolean isNewlineSplit()
/*     */   {
/* 445 */     return (this.newlineSplit) && (this.alignment != 8);
/*     */   }
/*     */ 
/*     */   public int getLastStrokeChunk()
/*     */   {
/* 453 */     for (int lastIdx = this.line.size() - 1; 
/* 454 */       lastIdx >= 0; lastIdx--) {
/* 455 */       PdfChunk chunk = (PdfChunk)this.line.get(lastIdx);
/* 456 */       if (chunk.isStroked())
/*     */         break;
/*     */     }
/* 459 */     return lastIdx;
/*     */   }
/*     */ 
/*     */   public PdfChunk getChunk(int idx)
/*     */   {
/* 468 */     if ((idx < 0) || (idx >= this.line.size()))
/* 469 */       return null;
/* 470 */     return (PdfChunk)this.line.get(idx);
/*     */   }
/*     */ 
/*     */   public float getOriginalWidth()
/*     */   {
/* 478 */     return this.originalWidth;
/*     */   }
/*     */ 
/*     */   float[] getMaxSize(float fixedLeading, float multipliedLeading)
/*     */   {
/* 508 */     float normal_leading = 0.0F;
/* 509 */     float image_leading = -10000.0F;
/*     */ 
/* 511 */     for (int k = 0; k < this.line.size(); k++) {
/* 512 */       PdfChunk chunk = (PdfChunk)this.line.get(k);
/* 513 */       if (chunk.isImage()) {
/* 514 */         Image img = chunk.getImage();
/* 515 */         if (chunk.changeLeading()) {
/* 516 */           float height = chunk.getImageHeight() + chunk.getImageOffsetY() + img.getSpacingBefore();
/* 517 */           image_leading = Math.max(height, image_leading);
/*     */         }
/*     */       }
/* 520 */       else if (chunk.changeLeading()) {
/* 521 */         normal_leading = Math.max(chunk.getLeading(), normal_leading);
/*     */       } else {
/* 523 */         normal_leading = Math.max(fixedLeading + multipliedLeading * chunk.font().size(), normal_leading);
/*     */       }
/*     */     }
/* 526 */     return new float[] { normal_leading > 0.0F ? normal_leading : fixedLeading, image_leading };
/*     */   }
/*     */ 
/*     */   boolean isRTL() {
/* 530 */     return this.isRTL;
/*     */   }
/*     */ 
/*     */   int getSeparatorCount()
/*     */   {
/* 540 */     int s = 0;
/*     */ 
/* 542 */     for (Object element : this.line) {
/* 543 */       PdfChunk ck = (PdfChunk)element;
/* 544 */       if (ck.isTab()) {
/* 545 */         if (!ck.isAttribute("TABSETTINGS"))
/*     */         {
/* 548 */           return -1;
/*     */         }
/* 550 */       } else if (ck.isHorizontalSeparator()) {
/* 551 */         s++;
/*     */       }
/*     */     }
/* 554 */     return s;
/*     */   }
/*     */ 
/*     */   public float getWidthCorrected(float charSpacing, float wordSpacing)
/*     */   {
/* 564 */     float total = 0.0F;
/* 565 */     for (int k = 0; k < this.line.size(); k++) {
/* 566 */       PdfChunk ck = (PdfChunk)this.line.get(k);
/* 567 */       total += ck.getWidthCorrected(charSpacing, wordSpacing);
/*     */     }
/* 569 */     return total;
/*     */   }
/*     */ 
/*     */   public float getAscender()
/*     */   {
/* 578 */     float ascender = 0.0F;
/* 579 */     for (int k = 0; k < this.line.size(); k++) {
/* 580 */       PdfChunk ck = (PdfChunk)this.line.get(k);
/* 581 */       if (ck.isImage()) {
/* 582 */         ascender = Math.max(ascender, ck.getImageHeight() + ck.getImageOffsetY());
/*     */       } else {
/* 584 */         PdfFont font = ck.font();
/* 585 */         float textRise = ck.getTextRise();
/* 586 */         ascender = Math.max(ascender, (textRise > 0.0F ? textRise : 0.0F) + font.getFont().getFontDescriptor(1, font.size()));
/*     */       }
/*     */     }
/* 589 */     return ascender;
/*     */   }
/*     */ 
/*     */   public float getDescender()
/*     */   {
/* 598 */     float descender = 0.0F;
/* 599 */     for (int k = 0; k < this.line.size(); k++) {
/* 600 */       PdfChunk ck = (PdfChunk)this.line.get(k);
/* 601 */       if (ck.isImage()) {
/* 602 */         descender = Math.min(descender, ck.getImageOffsetY());
/*     */       } else {
/* 604 */         PdfFont font = ck.font();
/* 605 */         float textRise = ck.getTextRise();
/* 606 */         descender = Math.min(descender, (textRise < 0.0F ? textRise : 0.0F) + font.getFont().getFontDescriptor(3, font.size()));
/*     */       }
/*     */     }
/* 609 */     return descender;
/*     */   }
/*     */ 
/*     */   public void flush() {
/* 613 */     if (this.tabStop != null) {
/* 614 */       float textWidth = this.originalWidth - this.width - this.tabPosition;
/* 615 */       float tabStopPosition = this.tabStop.getPosition(this.tabPosition, this.originalWidth - this.width, this.tabStopAnchorPosition);
/* 616 */       this.width = (this.originalWidth - tabStopPosition - textWidth);
/* 617 */       if (this.width < 0.0F)
/* 618 */         tabStopPosition += this.width;
/* 619 */       this.tabStop.setPosition(tabStopPosition);
/* 620 */       this.tabStop = null;
/* 621 */       this.tabPosition = (0.0F / 0.0F);
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.pdf.PdfLine
 * JD-Core Version:    0.6.2
 */