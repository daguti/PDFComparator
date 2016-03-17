/*     */ package com.itextpdf.text.pdf;
/*     */ 
/*     */ import com.itextpdf.text.AccessibleElementId;
/*     */ import com.itextpdf.text.BaseColor;
/*     */ import com.itextpdf.text.DocumentException;
/*     */ import com.itextpdf.text.ExceptionConverter;
/*     */ import com.itextpdf.text.Image;
/*     */ import com.itextpdf.text.Rectangle;
/*     */ import com.itextpdf.text.log.Logger;
/*     */ import com.itextpdf.text.log.LoggerFactory;
/*     */ import com.itextpdf.text.pdf.interfaces.IAccessibleElement;
/*     */ import java.util.HashMap;
/*     */ 
/*     */ public class PdfPRow
/*     */   implements IAccessibleElement
/*     */ {
/*  61 */   private final Logger LOGGER = LoggerFactory.getLogger(PdfPRow.class);
/*     */ 
/*  64 */   public boolean mayNotBreak = false;
/*     */   public static final float BOTTOM_LIMIT = -1.073742E+009F;
/*     */   public static final float RIGHT_LIMIT = 20000.0F;
/*     */   protected PdfPCell[] cells;
/*     */   protected float[] widths;
/*     */   protected float[] extraHeights;
/*  84 */   protected float maxHeight = 0.0F;
/*     */ 
/*  86 */   protected boolean calculated = false;
/*  87 */   protected boolean adjusted = false;
/*     */   private int[] canvasesPos;
/*  91 */   protected PdfName role = PdfName.TR;
/*  92 */   protected HashMap<PdfName, PdfObject> accessibleAttributes = null;
/*  93 */   protected AccessibleElementId id = new AccessibleElementId();
/*     */ 
/*     */   public PdfPRow(PdfPCell[] cells)
/*     */   {
/* 102 */     this(cells, null);
/*     */   }
/*     */ 
/*     */   public PdfPRow(PdfPCell[] cells, PdfPRow source) {
/* 106 */     this.cells = cells;
/* 107 */     this.widths = new float[cells.length];
/* 108 */     initExtraHeights();
/* 109 */     if (source != null) {
/* 110 */       this.id = source.id;
/* 111 */       this.role = source.role;
/* 112 */       if (source.accessibleAttributes != null)
/* 113 */         this.accessibleAttributes = new HashMap(source.accessibleAttributes);
/*     */     }
/*     */   }
/*     */ 
/*     */   public PdfPRow(PdfPRow row)
/*     */   {
/* 123 */     this.mayNotBreak = row.mayNotBreak;
/* 124 */     this.maxHeight = row.maxHeight;
/* 125 */     this.calculated = row.calculated;
/* 126 */     this.cells = new PdfPCell[row.cells.length];
/* 127 */     for (int k = 0; k < this.cells.length; k++) {
/* 128 */       if (row.cells[k] != null) {
/* 129 */         if ((row.cells[k] instanceof PdfPHeaderCell))
/* 130 */           this.cells[k] = new PdfPHeaderCell((PdfPHeaderCell)row.cells[k]);
/*     */         else
/* 132 */           this.cells[k] = new PdfPCell(row.cells[k]);
/*     */       }
/*     */     }
/* 135 */     this.widths = new float[this.cells.length];
/* 136 */     System.arraycopy(row.widths, 0, this.widths, 0, this.cells.length);
/* 137 */     initExtraHeights();
/* 138 */     this.id = row.id;
/* 139 */     this.role = row.role;
/* 140 */     if (row.accessibleAttributes != null)
/* 141 */       this.accessibleAttributes = new HashMap(row.accessibleAttributes);
/*     */   }
/*     */ 
/*     */   public boolean setWidths(float[] widths)
/*     */   {
/* 151 */     if (widths.length != this.cells.length)
/* 152 */       return false;
/* 153 */     System.arraycopy(widths, 0, this.widths, 0, this.cells.length);
/* 154 */     float total = 0.0F;
/* 155 */     this.calculated = false;
/* 156 */     for (int k = 0; k < widths.length; k++) {
/* 157 */       PdfPCell cell = this.cells[k];
/*     */ 
/* 159 */       if (cell == null) {
/* 160 */         total += widths[k];
/*     */       }
/*     */       else
/*     */       {
/* 164 */         cell.setLeft(total);
/* 165 */         int last = k + cell.getColspan();
/* 166 */         for (; k < last; k++)
/* 167 */           total += widths[k];
/* 168 */         k--;
/* 169 */         cell.setRight(total);
/* 170 */         cell.setTop(0.0F);
/*     */       }
/*     */     }
/* 172 */     return true;
/*     */   }
/*     */ 
/*     */   protected void initExtraHeights()
/*     */   {
/* 180 */     this.extraHeights = new float[this.cells.length];
/* 181 */     for (int i = 0; i < this.extraHeights.length; i++)
/* 182 */       this.extraHeights[i] = 0.0F;
/*     */   }
/*     */ 
/*     */   public void setExtraHeight(int cell, float height)
/*     */   {
/* 193 */     if ((cell < 0) || (cell >= this.cells.length))
/* 194 */       return;
/* 195 */     this.extraHeights[cell] = height;
/*     */   }
/*     */ 
/*     */   protected void calculateHeights()
/*     */   {
/* 202 */     this.maxHeight = 0.0F;
/* 203 */     for (int k = 0; k < this.cells.length; k++) {
/* 204 */       PdfPCell cell = this.cells[k];
/* 205 */       float height = 0.0F;
/* 206 */       if (cell != null)
/*     */       {
/* 210 */         height = cell.getMaxHeight();
/* 211 */         if ((height > this.maxHeight) && (cell.getRowspan() == 1))
/* 212 */           this.maxHeight = height;
/*     */       }
/*     */     }
/* 215 */     this.calculated = true;
/*     */   }
/*     */ 
/*     */   public void setMayNotBreak(boolean mayNotBreak)
/*     */   {
/* 222 */     this.mayNotBreak = mayNotBreak;
/*     */   }
/*     */ 
/*     */   public boolean isMayNotBreak()
/*     */   {
/* 229 */     return this.mayNotBreak;
/*     */   }
/*     */ 
/*     */   public void writeBorderAndBackground(float xPos, float yPos, float currentMaxHeight, PdfPCell cell, PdfContentByte[] canvases)
/*     */   {
/* 243 */     BaseColor background = cell.getBackgroundColor();
/* 244 */     if ((background != null) || (cell.hasBorders()))
/*     */     {
/* 246 */       float right = cell.getRight() + xPos;
/* 247 */       float top = cell.getTop() + yPos;
/* 248 */       float left = cell.getLeft() + xPos;
/* 249 */       float bottom = top - currentMaxHeight;
/*     */ 
/* 251 */       if (background != null) {
/* 252 */         PdfContentByte backgr = canvases[1];
/* 253 */         backgr.setColorFill(background);
/* 254 */         backgr.rectangle(left, bottom, right - left, top - bottom);
/* 255 */         backgr.fill();
/*     */       }
/* 257 */       if (cell.hasBorders()) {
/* 258 */         Rectangle newRect = new Rectangle(left, bottom, right, top);
/*     */ 
/* 260 */         newRect.cloneNonPositionParameters(cell);
/* 261 */         newRect.setBackgroundColor(null);
/*     */ 
/* 263 */         PdfContentByte lineCanvas = canvases[2];
/* 264 */         lineCanvas.rectangle(newRect);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void saveAndRotateCanvases(PdfContentByte[] canvases, float a, float b, float c, float d, float e, float f)
/*     */   {
/* 273 */     int last = 4;
/* 274 */     if (this.canvasesPos == null)
/* 275 */       this.canvasesPos = new int[last * 2];
/* 276 */     for (int k = 0; k < last; k++) {
/* 277 */       ByteBuffer bb = canvases[k].getInternalBuffer();
/* 278 */       this.canvasesPos[(k * 2)] = bb.size();
/* 279 */       canvases[k].saveState();
/* 280 */       canvases[k].concatCTM(a, b, c, d, e, f);
/* 281 */       this.canvasesPos[(k * 2 + 1)] = bb.size();
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void restoreCanvases(PdfContentByte[] canvases)
/*     */   {
/* 289 */     int last = 4;
/* 290 */     for (int k = 0; k < last; k++) {
/* 291 */       ByteBuffer bb = canvases[k].getInternalBuffer();
/* 292 */       int p1 = bb.size();
/* 293 */       canvases[k].restoreState();
/* 294 */       if (p1 == this.canvasesPos[(k * 2 + 1)])
/* 295 */         bb.setSize(this.canvasesPos[(k * 2)]);
/*     */     }
/*     */   }
/*     */ 
/*     */   public static float setColumn(ColumnText ct, float left, float bottom, float right, float top)
/*     */   {
/* 303 */     if (left > right)
/* 304 */       right = left;
/* 305 */     if (bottom > top)
/* 306 */       top = bottom;
/* 307 */     ct.setSimpleColumn(left, bottom, right, top);
/* 308 */     return top;
/*     */   }
/*     */ 
/*     */   public void writeCells(int colStart, int colEnd, float xPos, float yPos, PdfContentByte[] canvases, boolean reusable)
/*     */   {
/* 326 */     if (!this.calculated)
/* 327 */       calculateHeights();
/* 328 */     if (colEnd < 0)
/* 329 */       colEnd = this.cells.length;
/*     */     else
/* 331 */       colEnd = Math.min(colEnd, this.cells.length);
/* 332 */     if (colStart < 0)
/* 333 */       colStart = 0;
/* 334 */     if (colStart >= colEnd) {
/* 335 */       return;
/*     */     }
/*     */ 
/* 338 */     for (int newStart = colStart; (newStart >= 0) && 
/* 339 */       (this.cells[newStart] == null); newStart--)
/*     */     {
/* 341 */       if (newStart > 0) {
/* 342 */         xPos -= this.widths[(newStart - 1)];
/*     */       }
/*     */     }
/* 345 */     if (newStart < 0)
/* 346 */       newStart = 0;
/* 347 */     if (this.cells[newStart] != null) {
/* 348 */       xPos -= this.cells[newStart].getLeft();
/*     */     }
/* 350 */     if (isTagged(canvases[3])) {
/* 351 */       canvases[3].openMCBlock(this);
/*     */     }
/* 353 */     for (int k = newStart; k < colEnd; k++) {
/* 354 */       PdfPCell cell = this.cells[k];
/* 355 */       if (cell != null)
/*     */       {
/* 357 */         if (isTagged(canvases[3])) {
/* 358 */           canvases[3].openMCBlock(cell);
/*     */         }
/* 360 */         float currentMaxHeight = this.maxHeight + this.extraHeights[k];
/*     */ 
/* 362 */         writeBorderAndBackground(xPos, yPos, currentMaxHeight, cell, canvases);
/*     */ 
/* 364 */         Image img = cell.getImage();
/*     */ 
/* 366 */         float tly = cell.getTop() + yPos - cell.getEffectivePaddingTop();
/* 367 */         if (cell.getHeight() <= currentMaxHeight) {
/* 368 */           switch (cell.getVerticalAlignment()) {
/*     */           case 6:
/* 370 */             tly = cell.getTop() + yPos - currentMaxHeight + cell.getHeight() - cell.getEffectivePaddingTop();
/*     */ 
/* 372 */             break;
/*     */           case 5:
/* 374 */             tly = cell.getTop() + yPos + (cell.getHeight() - currentMaxHeight) / 2.0F - cell.getEffectivePaddingTop();
/*     */ 
/* 376 */             break;
/*     */           }
/*     */ 
/*     */         }
/*     */ 
/* 381 */         if (img != null) {
/* 382 */           if (cell.getRotation() != 0) {
/* 383 */             img = Image.getInstance(img);
/* 384 */             img.setRotation(img.getImageRotation() + (float)(cell.getRotation() * 3.141592653589793D / 180.0D));
/*     */           }
/* 386 */           boolean vf = false;
/* 387 */           if (cell.getHeight() > currentMaxHeight) {
/* 388 */             if (!img.isScaleToFitHeight()) {
/*     */               continue;
/*     */             }
/* 391 */             img.scalePercent(100.0F);
/* 392 */             float scale = (currentMaxHeight - cell.getEffectivePaddingTop() - cell.getEffectivePaddingBottom()) / img.getScaledHeight();
/*     */ 
/* 395 */             img.scalePercent(scale * 100.0F);
/* 396 */             vf = true;
/*     */           }
/* 398 */           float left = cell.getLeft() + xPos + cell.getEffectivePaddingLeft();
/*     */ 
/* 400 */           if (vf) {
/* 401 */             switch (cell.getHorizontalAlignment()) {
/*     */             case 1:
/* 403 */               left = xPos + (cell.getLeft() + cell.getEffectivePaddingLeft() + cell.getRight() - cell.getEffectivePaddingRight() - img.getScaledWidth()) / 2.0F;
/*     */ 
/* 408 */               break;
/*     */             case 2:
/* 410 */               left = xPos + cell.getRight() - cell.getEffectivePaddingRight() - img.getScaledWidth();
/*     */ 
/* 413 */               break;
/*     */             }
/*     */ 
/* 417 */             tly = cell.getTop() + yPos - cell.getEffectivePaddingTop();
/*     */           }
/* 419 */           img.setAbsolutePosition(left, tly - img.getScaledHeight());
/*     */           try {
/* 421 */             if (isTagged(canvases[3])) {
/* 422 */               canvases[3].openMCBlock(img);
/*     */             }
/* 424 */             canvases[3].addImage(img);
/* 425 */             if (isTagged(canvases[3]))
/* 426 */               canvases[3].closeMCBlock(img);
/*     */           }
/*     */           catch (DocumentException e) {
/* 429 */             throw new ExceptionConverter(e);
/*     */           }
/*     */ 
/*     */         }
/* 433 */         else if ((cell.getRotation() == 90) || (cell.getRotation() == 270)) {
/* 434 */           float netWidth = currentMaxHeight - cell.getEffectivePaddingTop() - cell.getEffectivePaddingBottom();
/* 435 */           float netHeight = cell.getWidth() - cell.getEffectivePaddingLeft() - cell.getEffectivePaddingRight();
/* 436 */           ColumnText ct = ColumnText.duplicate(cell.getColumn());
/* 437 */           ct.setCanvases(canvases);
/* 438 */           ct.setSimpleColumn(0.0F, 0.0F, netWidth + 0.001F, -netHeight);
/*     */           try {
/* 440 */             ct.go(true);
/*     */           } catch (DocumentException e) {
/* 442 */             throw new ExceptionConverter(e);
/*     */           }
/* 444 */           float calcHeight = -ct.getYLine();
/* 445 */           if ((netWidth <= 0.0F) || (netHeight <= 0.0F))
/* 446 */             calcHeight = 0.0F;
/* 447 */           if (calcHeight > 0.0F) {
/* 448 */             if (cell.isUseDescender())
/* 449 */               calcHeight -= ct.getDescender();
/* 450 */             if (reusable)
/* 451 */               ct = ColumnText.duplicate(cell.getColumn());
/*     */             else
/* 453 */               ct = cell.getColumn();
/* 454 */             ct.setCanvases(canvases);
/* 455 */             ct.setSimpleColumn(-0.003F, -0.001F, netWidth + 0.003F, calcHeight);
/*     */ 
/* 458 */             if (cell.getRotation() == 90) {
/* 459 */               float pivotY = cell.getTop() + yPos - currentMaxHeight + cell.getEffectivePaddingBottom();
/*     */               float pivotX;
/* 460 */               switch (cell.getVerticalAlignment()) {
/*     */               case 6:
/* 462 */                 pivotX = cell.getLeft() + xPos + cell.getWidth() - cell.getEffectivePaddingRight();
/* 463 */                 break;
/*     */               case 5:
/* 465 */                 pivotX = cell.getLeft() + xPos + (cell.getWidth() + cell.getEffectivePaddingLeft() - cell.getEffectivePaddingRight() + calcHeight) / 2.0F;
/* 466 */                 break;
/*     */               default:
/* 468 */                 pivotX = cell.getLeft() + xPos + cell.getEffectivePaddingLeft() + calcHeight;
/*     */               }
/*     */ 
/* 471 */               saveAndRotateCanvases(canvases, 0.0F, 1.0F, -1.0F, 0.0F, pivotX, pivotY);
/*     */             }
/*     */             else {
/* 474 */               float pivotY = cell.getTop() + yPos - cell.getEffectivePaddingTop();
/*     */               float pivotX;
/* 475 */               switch (cell.getVerticalAlignment()) {
/*     */               case 6:
/* 477 */                 pivotX = cell.getLeft() + xPos + cell.getEffectivePaddingLeft();
/* 478 */                 break;
/*     */               case 5:
/* 480 */                 pivotX = cell.getLeft() + xPos + (cell.getWidth() + cell.getEffectivePaddingLeft() - cell.getEffectivePaddingRight() - calcHeight) / 2.0F;
/* 481 */                 break;
/*     */               default:
/* 483 */                 pivotX = cell.getLeft() + xPos + cell.getWidth() - cell.getEffectivePaddingRight() - calcHeight;
/*     */               }
/*     */ 
/* 486 */               saveAndRotateCanvases(canvases, 0.0F, -1.0F, 1.0F, 0.0F, pivotX, pivotY);
/*     */             }
/*     */             try {
/* 489 */               ct.go();
/*     */             } catch (DocumentException e) {
/* 491 */               throw new ExceptionConverter(e);
/*     */             } finally {
/* 493 */               restoreCanvases(canvases);
/*     */             }
/*     */           }
/*     */         }
/*     */         else {
/* 498 */           float fixedHeight = cell.getFixedHeight();
/* 499 */           float rightLimit = cell.getRight() + xPos - cell.getEffectivePaddingRight();
/*     */ 
/* 501 */           float leftLimit = cell.getLeft() + xPos + cell.getEffectivePaddingLeft();
/*     */ 
/* 503 */           if (cell.isNoWrap())
/* 504 */             switch (cell.getHorizontalAlignment()) {
/*     */             case 1:
/* 506 */               rightLimit += 10000.0F;
/* 507 */               leftLimit -= 10000.0F;
/* 508 */               break;
/*     */             case 2:
/* 510 */               if (cell.getRotation() == 180) {
/* 511 */                 rightLimit += 20000.0F;
/*     */               }
/*     */               else {
/* 514 */                 leftLimit -= 20000.0F;
/*     */               }
/* 516 */               break;
/*     */             default:
/* 518 */               if (cell.getRotation() == 180) {
/* 519 */                 leftLimit -= 20000.0F;
/*     */               }
/*     */               else
/* 522 */                 rightLimit += 20000.0F;
/*     */               break;
/*     */             }
/*     */           ColumnText ct;
/*     */           ColumnText ct;
/* 528 */           if (reusable)
/* 529 */             ct = ColumnText.duplicate(cell.getColumn());
/*     */           else
/* 531 */             ct = cell.getColumn();
/* 532 */           ct.setCanvases(canvases);
/* 533 */           float bry = tly - (currentMaxHeight - cell.getEffectivePaddingTop() - cell.getEffectivePaddingBottom());
/*     */ 
/* 536 */           if ((fixedHeight > 0.0F) && 
/* 537 */             (cell.getHeight() > currentMaxHeight)) {
/* 538 */             tly = cell.getTop() + yPos - cell.getEffectivePaddingTop();
/* 539 */             bry = cell.getTop() + yPos - currentMaxHeight + cell.getEffectivePaddingBottom();
/*     */           }
/*     */ 
/* 542 */           if (((tly > bry) || (ct.zeroHeightElement())) && (leftLimit < rightLimit)) {
/* 543 */             ct.setSimpleColumn(leftLimit, bry - 0.001F, rightLimit, tly);
/* 544 */             if (cell.getRotation() == 180) {
/* 545 */               float shx = leftLimit + rightLimit;
/* 546 */               float shy = yPos + yPos - currentMaxHeight + cell.getEffectivePaddingBottom() - cell.getEffectivePaddingTop();
/* 547 */               saveAndRotateCanvases(canvases, -1.0F, 0.0F, 0.0F, -1.0F, shx, shy);
/*     */             }
/*     */             try {
/* 550 */               ct.go();
/*     */ 
/* 554 */               if (cell.getRotation() == 180)
/* 555 */                 restoreCanvases(canvases);
/*     */             }
/*     */             catch (DocumentException e)
/*     */             {
/* 552 */               throw new ExceptionConverter(e);
/*     */             } finally {
/* 554 */               if (cell.getRotation() == 180) {
/* 555 */                 restoreCanvases(canvases);
/*     */               }
/*     */             }
/*     */           }
/*     */         }
/*     */ 
/* 561 */         PdfPCellEvent evt = cell.getCellEvent();
/* 562 */         if (evt != null) {
/* 563 */           Rectangle rect = new Rectangle(cell.getLeft() + xPos, cell.getTop() + yPos - currentMaxHeight, cell.getRight() + xPos, cell.getTop() + yPos);
/*     */ 
/* 566 */           evt.cellLayout(cell, rect, canvases);
/*     */         }
/* 568 */         if (isTagged(canvases[3]))
/* 569 */           canvases[3].closeMCBlock(cell);
/*     */       }
/*     */     }
/* 572 */     if (isTagged(canvases[3]))
/* 573 */       canvases[3].closeMCBlock(this);
/*     */   }
/*     */ 
/*     */   public boolean isCalculated()
/*     */   {
/* 583 */     return this.calculated;
/*     */   }
/*     */ 
/*     */   public float getMaxHeights()
/*     */   {
/* 592 */     if (!this.calculated)
/* 593 */       calculateHeights();
/* 594 */     return this.maxHeight;
/*     */   }
/*     */ 
/*     */   public void setMaxHeights(float maxHeight)
/*     */   {
/* 604 */     this.maxHeight = maxHeight;
/*     */   }
/*     */ 
/*     */   float[] getEventWidth(float xPos, float[] absoluteWidths)
/*     */   {
/* 610 */     int n = 1;
/* 611 */     for (int k = 0; k < this.cells.length; ) {
/* 612 */       if (this.cells[k] != null) {
/* 613 */         n++;
/* 614 */         k += this.cells[k].getColspan();
/*     */       }
/*     */       else {
/* 617 */         while ((k < this.cells.length) && (this.cells[k] == null)) {
/* 618 */           n++;
/* 619 */           k++;
/*     */         }
/*     */       }
/*     */     }
/* 623 */     float[] width = new float[n];
/* 624 */     width[0] = xPos;
/* 625 */     n = 1;
/* 626 */     for (int k = 0; (k < this.cells.length) && (n < width.length); ) {
/* 627 */       if (this.cells[k] != null) {
/* 628 */         int colspan = this.cells[k].getColspan();
/* 629 */         width[n] = width[(n - 1)];
/* 630 */         for (int i = 0; (i < colspan) && (k < absoluteWidths.length); i++) {
/* 631 */           width[n] += absoluteWidths[(k++)];
/*     */         }
/* 633 */         n++;
/*     */       }
/*     */       else {
/* 636 */         width[n] = width[(n - 1)];
/* 637 */         while ((k < this.cells.length) && (this.cells[k] == null)) {
/* 638 */           width[n] += absoluteWidths[(k++)];
/*     */         }
/* 640 */         n++;
/*     */       }
/*     */     }
/* 643 */     return width;
/*     */   }
/*     */ 
/*     */   public void copyRowContent(PdfPTable table, int idx)
/*     */   {
/* 654 */     if (table == null) {
/* 655 */       return;
/*     */     }
/*     */ 
/* 658 */     for (int i = 0; i < this.cells.length; i++) {
/* 659 */       int lastRow = idx;
/* 660 */       PdfPCell copy = table.getRow(lastRow).getCells()[i];
/* 661 */       while ((copy == null) && (lastRow > 0)) {
/* 662 */         copy = table.getRow(--lastRow).getCells()[i];
/*     */       }
/* 664 */       if ((this.cells[i] != null) && (copy != null)) {
/* 665 */         this.cells[i].setColumn(copy.getColumn());
/* 666 */         this.calculated = false;
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public PdfPRow splitRow(PdfPTable table, int rowIndex, float new_height)
/*     */   {
/* 681 */     this.LOGGER.info("Splitting " + rowIndex + " " + new_height);
/*     */ 
/* 683 */     PdfPCell[] newCells = new PdfPCell[this.cells.length];
/* 684 */     float[] fixHs = new float[this.cells.length];
/* 685 */     float[] minHs = new float[this.cells.length];
/* 686 */     boolean allEmpty = true;
/*     */ 
/* 688 */     for (int k = 0; k < this.cells.length; k++) {
/* 689 */       float newHeight = new_height;
/* 690 */       PdfPCell cell = this.cells[k];
/* 691 */       if (cell == null) {
/* 692 */         int index = rowIndex;
/* 693 */         if (table.rowSpanAbove(index, k)) {
/* 694 */           while (table.rowSpanAbove(--index, k)) {
/* 695 */             newHeight += table.getRow(index).getMaxHeights();
/*     */           }
/* 697 */           PdfPRow row = table.getRow(index);
/* 698 */           if ((row != null) && (row.getCells()[k] != null)) {
/* 699 */             newCells[k] = new PdfPCell(row.getCells()[k]);
/* 700 */             newCells[k].setColumn(null);
/* 701 */             newCells[k].setRowspan(row.getCells()[k].getRowspan() - rowIndex + index);
/* 702 */             allEmpty = false;
/*     */           }
/*     */         }
/*     */       }
/*     */       else {
/* 707 */         fixHs[k] = cell.getFixedHeight();
/* 708 */         minHs[k] = cell.getMinimumHeight();
/* 709 */         Image img = cell.getImage();
/* 710 */         PdfPCell newCell = new PdfPCell(cell);
/* 711 */         if (img != null) {
/* 712 */           float padding = cell.getEffectivePaddingBottom() + cell.getEffectivePaddingTop() + 2.0F;
/* 713 */           if (((img.isScaleToFitHeight()) || (img.getScaledHeight() + padding < newHeight)) && (newHeight > padding))
/*     */           {
/* 715 */             newCell.setPhrase(null);
/* 716 */             allEmpty = false;
/*     */           }
/*     */         }
/*     */         else
/*     */         {
/* 721 */           ColumnText ct = ColumnText.duplicate(cell.getColumn());
/* 722 */           float left = cell.getLeft() + cell.getEffectivePaddingLeft();
/* 723 */           float bottom = cell.getTop() + cell.getEffectivePaddingBottom() - newHeight;
/* 724 */           float right = cell.getRight() - cell.getEffectivePaddingRight();
/* 725 */           float top = cell.getTop() - cell.getEffectivePaddingTop();
/*     */           float y;
/* 726 */           switch (cell.getRotation()) {
/*     */           case 90:
/*     */           case 270:
/* 729 */             y = setColumn(ct, bottom, left, top, right);
/* 730 */             break;
/*     */           default:
/* 732 */             y = setColumn(ct, left, bottom + 1.0E-005F, cell.isNoWrap() ? 20000.0F : right, top);
/*     */           }
/*     */           int status;
/*     */           try
/*     */           {
/* 737 */             status = ct.go(true);
/*     */           }
/*     */           catch (DocumentException e) {
/* 740 */             throw new ExceptionConverter(e);
/*     */           }
/* 742 */           boolean thisEmpty = ct.getYLine() == y;
/* 743 */           if (thisEmpty) {
/* 744 */             newCell.setColumn(ColumnText.duplicate(cell.getColumn()));
/* 745 */             ct.setFilledWidth(0.0F);
/*     */           }
/* 747 */           else if ((status & 0x1) == 0) {
/* 748 */             newCell.setColumn(ct);
/* 749 */             ct.setFilledWidth(0.0F);
/*     */           }
/*     */           else {
/* 752 */             newCell.setPhrase(null);
/* 753 */           }allEmpty = (allEmpty) && (thisEmpty);
/*     */         }
/* 755 */         newCells[k] = newCell;
/* 756 */         cell.setFixedHeight(newHeight);
/*     */       }
/*     */     }
/* 758 */     if (allEmpty) {
/* 759 */       for (int k = 0; k < this.cells.length; k++) {
/* 760 */         PdfPCell cell = this.cells[k];
/* 761 */         if (cell != null)
/*     */         {
/* 763 */           if (fixHs[k] > 0.0F)
/* 764 */             cell.setFixedHeight(fixHs[k]);
/*     */           else
/* 766 */             cell.setMinimumHeight(minHs[k]); 
/*     */         }
/*     */       }
/* 768 */       return null;
/*     */     }
/* 770 */     calculateHeights();
/* 771 */     PdfPRow split = new PdfPRow(newCells, this);
/* 772 */     split.widths = ((float[])this.widths.clone());
/* 773 */     return split;
/*     */   }
/*     */ 
/*     */   public float getMaxRowHeightsWithoutCalculating()
/*     */   {
/* 778 */     return this.maxHeight;
/*     */   }
/*     */ 
/*     */   public void setFinalMaxHeights(float maxHeight)
/*     */   {
/* 783 */     setMaxHeights(maxHeight);
/* 784 */     this.calculated = true;
/*     */   }
/*     */ 
/*     */   public void splitRowspans(PdfPTable original, int originalIdx, PdfPTable part, int partIdx)
/*     */   {
/* 797 */     if ((original == null) || (part == null)) {
/* 798 */       return;
/*     */     }
/* 800 */     int i = 0;
/* 801 */     while (i < this.cells.length)
/* 802 */       if (this.cells[i] == null) {
/* 803 */         int splittedRowIdx = original.getCellStartRowIndex(originalIdx, i);
/* 804 */         int copyRowIdx = part.getCellStartRowIndex(partIdx, i);
/* 805 */         PdfPCell splitted = original.getRow(splittedRowIdx).getCells()[i];
/*     */ 
/* 807 */         PdfPCell copy = part.getRow(copyRowIdx).getCells()[i];
/*     */ 
/* 809 */         if (splitted != null) {
/* 810 */           assert (copy != null);
/* 811 */           this.cells[i] = new PdfPCell(copy);
/* 812 */           int rowspanOnPreviousPage = partIdx - copyRowIdx + 1;
/* 813 */           this.cells[i].setRowspan(copy.getRowspan() - rowspanOnPreviousPage);
/* 814 */           splitted.setRowspan(rowspanOnPreviousPage);
/* 815 */           this.calculated = false;
/*     */         }
/* 817 */         i++;
/*     */       }
/*     */       else {
/* 820 */         i += this.cells[i].getColspan();
/*     */       }
/*     */   }
/*     */ 
/*     */   public PdfPCell[] getCells()
/*     */   {
/* 834 */     return this.cells;
/*     */   }
/*     */ 
/*     */   public boolean hasRowspan()
/*     */   {
/* 842 */     for (int i = 0; i < this.cells.length; i++) {
/* 843 */       if ((this.cells[i] != null) && (this.cells[i].getRowspan() > 1))
/* 844 */         return true;
/*     */     }
/* 846 */     return false;
/*     */   }
/*     */ 
/*     */   public boolean isAdjusted() {
/* 850 */     return this.adjusted;
/*     */   }
/*     */ 
/*     */   public void setAdjusted(boolean adjusted) {
/* 854 */     this.adjusted = adjusted;
/*     */   }
/*     */ 
/*     */   public PdfObject getAccessibleAttribute(PdfName key) {
/* 858 */     if (this.accessibleAttributes != null) {
/* 859 */       return (PdfObject)this.accessibleAttributes.get(key);
/*     */     }
/* 861 */     return null;
/*     */   }
/*     */ 
/*     */   public void setAccessibleAttribute(PdfName key, PdfObject value) {
/* 865 */     if (this.accessibleAttributes == null)
/* 866 */       this.accessibleAttributes = new HashMap();
/* 867 */     this.accessibleAttributes.put(key, value);
/*     */   }
/*     */ 
/*     */   public HashMap<PdfName, PdfObject> getAccessibleAttributes() {
/* 871 */     return this.accessibleAttributes;
/*     */   }
/*     */ 
/*     */   public PdfName getRole() {
/* 875 */     return this.role;
/*     */   }
/*     */ 
/*     */   public void setRole(PdfName role) {
/* 879 */     this.role = role;
/*     */   }
/*     */ 
/*     */   public AccessibleElementId getId() {
/* 883 */     return this.id;
/*     */   }
/*     */ 
/*     */   public void setId(AccessibleElementId id) {
/* 887 */     this.id = id;
/*     */   }
/*     */ 
/*     */   private static boolean isTagged(PdfContentByte canvas) {
/* 891 */     return (canvas != null) && (canvas.writer != null) && (canvas.writer.isTagged());
/*     */   }
/*     */ 
/*     */   public boolean isInline() {
/* 895 */     return false;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.pdf.PdfPRow
 * JD-Core Version:    0.6.2
 */