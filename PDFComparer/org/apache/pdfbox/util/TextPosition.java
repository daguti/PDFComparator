/*     */ package org.apache.pdfbox.util;
/*     */ 
/*     */ import org.apache.pdfbox.pdmodel.PDPage;
/*     */ import org.apache.pdfbox.pdmodel.common.PDRectangle;
/*     */ import org.apache.pdfbox.pdmodel.font.PDFont;
/*     */ 
/*     */ public class TextPosition
/*     */ {
/*     */   private Matrix textPos;
/*     */   private float endX;
/*     */   private float endY;
/*     */   private float maxTextHeight;
/*     */   private int rot;
/*  40 */   private float x = (1.0F / -1.0F);
/*  41 */   private float y = (1.0F / -1.0F);
/*     */   private float pageHeight;
/*     */   private float pageWidth;
/*     */   private float[] widths;
/*     */   private float widthOfSpace;
/*     */   private String str;
/*     */   private int[] unicodeCP;
/*     */   private PDFont font;
/*     */   private float fontSize;
/*     */   private int fontSizePt;
/*     */   private float wordSpacing;
/*     */ 
/*     */   protected TextPosition()
/*     */   {
/*     */   }
/*     */ 
/*     */   public TextPosition(PDPage page, Matrix textPositionSt, Matrix textPositionEnd, float maxFontH, float[] individualWidths, float spaceWidth, String string, PDFont currentFont, float fontSizeValue, int fontSizeInPt, float ws)
/*     */   {
/*  91 */     this.textPos = textPositionSt;
/*     */ 
/*  93 */     this.endX = textPositionEnd.getXPosition();
/*  94 */     this.endY = textPositionEnd.getYPosition();
/*     */ 
/*  96 */     this.rot = page.findRotation();
/*     */ 
/*  98 */     if (this.rot < 0)
/*     */     {
/* 100 */       this.rot += 360;
/*     */     }
/* 102 */     else if (this.rot >= 360)
/*     */     {
/* 104 */       this.rot -= 360;
/*     */     }
/*     */ 
/* 107 */     this.maxTextHeight = maxFontH;
/* 108 */     this.pageHeight = page.findMediaBox().getHeight();
/* 109 */     this.pageWidth = page.findMediaBox().getWidth();
/*     */ 
/* 111 */     this.widths = individualWidths;
/* 112 */     this.widthOfSpace = spaceWidth;
/* 113 */     this.str = string;
/* 114 */     this.font = currentFont;
/* 115 */     this.fontSize = fontSizeValue;
/* 116 */     this.fontSizePt = fontSizeInPt;
/* 117 */     this.wordSpacing = ws;
/*     */   }
/*     */ 
/*     */   /** @deprecated */
/*     */   public TextPosition(int pageRotation, float pageWidthValue, float pageHeightValue, Matrix textPositionSt, Matrix textPositionEnd, float maxFontH, float individualWidth, float spaceWidth, String string, PDFont currentFont, float fontSizeValue, int fontSizeInPt)
/*     */   {
/* 154 */     this(pageRotation, pageWidthValue, pageHeightValue, textPositionSt, textPositionEnd.getXPosition(), textPositionEnd.getYPosition(), maxFontH, individualWidth, spaceWidth, string, null, currentFont, fontSizeValue, fontSizeInPt);
/*     */   }
/*     */ 
/*     */   /** @deprecated */
/*     */   public TextPosition(int pageRotation, float pageWidthValue, float pageHeightValue, Matrix textPositionSt, float endXValue, float endYValue, float maxFontH, float individualWidth, float spaceWidth, String string, PDFont currentFont, float fontSizeValue, int fontSizeInPt)
/*     */   {
/* 195 */     this(pageRotation, pageWidthValue, pageHeightValue, textPositionSt, endXValue, endYValue, maxFontH, individualWidth, spaceWidth, string, null, currentFont, fontSizeValue, fontSizeInPt);
/*     */   }
/*     */ 
/*     */   public TextPosition(int pageRotation, float pageWidthValue, float pageHeightValue, Matrix textPositionSt, float endXValue, float endYValue, float maxFontH, float individualWidth, float spaceWidth, String string, int[] codePoints, PDFont currentFont, float fontSizeValue, int fontSizeInPt)
/*     */   {
/* 233 */     this.textPos = textPositionSt;
/*     */ 
/* 235 */     this.endX = endXValue;
/* 236 */     this.endY = endYValue;
/*     */ 
/* 238 */     this.rot = pageRotation;
/*     */ 
/* 240 */     if (this.rot < 0)
/*     */     {
/* 242 */       this.rot += 360;
/*     */     }
/* 244 */     else if (this.rot >= 360)
/*     */     {
/* 246 */       this.rot -= 360;
/*     */     }
/*     */ 
/* 249 */     this.maxTextHeight = maxFontH;
/* 250 */     this.pageHeight = pageHeightValue;
/* 251 */     this.pageWidth = pageWidthValue;
/*     */ 
/* 253 */     this.widths = new float[] { individualWidth };
/* 254 */     this.widthOfSpace = spaceWidth;
/* 255 */     this.str = string;
/* 256 */     this.unicodeCP = codePoints;
/* 257 */     this.font = currentFont;
/* 258 */     this.fontSize = fontSizeValue;
/* 259 */     this.fontSizePt = fontSizeInPt;
/*     */   }
/*     */ 
/*     */   public String getCharacter()
/*     */   {
/* 269 */     return this.str;
/*     */   }
/*     */ 
/*     */   public int[] getCodePoints()
/*     */   {
/* 279 */     return this.unicodeCP;
/*     */   }
/*     */ 
/*     */   public Matrix getTextPos()
/*     */   {
/* 289 */     return this.textPos;
/*     */   }
/*     */ 
/*     */   public float getDir()
/*     */   {
/* 299 */     float a = this.textPos.getValue(0, 0);
/* 300 */     float b = this.textPos.getValue(0, 1);
/* 301 */     float c = this.textPos.getValue(1, 0);
/* 302 */     float d = this.textPos.getValue(1, 1);
/*     */ 
/* 306 */     if ((a > 0.0F) && (Math.abs(b) < d) && (Math.abs(c) < a) && (d > 0.0F))
/*     */     {
/* 308 */       return 0.0F;
/*     */     }
/*     */ 
/* 312 */     if ((a < 0.0F) && (Math.abs(b) < Math.abs(d)) && (Math.abs(c) < Math.abs(a)) && (d < 0.0F))
/*     */     {
/* 314 */       return 180.0F;
/*     */     }
/*     */ 
/* 318 */     if ((Math.abs(a) < Math.abs(c)) && (b > 0.0F) && (c < 0.0F) && (Math.abs(d) < b))
/*     */     {
/* 320 */       return 90.0F;
/*     */     }
/*     */ 
/* 324 */     if ((Math.abs(a) < c) && (b < 0.0F) && (c > 0.0F) && (Math.abs(d) < Math.abs(b)))
/*     */     {
/* 326 */       return 270.0F;
/*     */     }
/* 328 */     return 0.0F;
/*     */   }
/*     */ 
/*     */   private float getXRot(float rotation)
/*     */   {
/* 341 */     if (rotation == 0.0F)
/*     */     {
/* 343 */       return this.textPos.getValue(2, 0);
/*     */     }
/* 345 */     if (rotation == 90.0F)
/*     */     {
/* 347 */       return this.textPos.getValue(2, 1);
/*     */     }
/* 349 */     if (rotation == 180.0F)
/*     */     {
/* 351 */       return this.pageWidth - this.textPos.getValue(2, 0);
/*     */     }
/* 353 */     if (rotation == 270.0F)
/*     */     {
/* 355 */       return this.pageHeight - this.textPos.getValue(2, 1);
/*     */     }
/* 357 */     return 0.0F;
/*     */   }
/*     */ 
/*     */   public float getX()
/*     */   {
/* 369 */     if (this.x == (1.0F / -1.0F))
/*     */     {
/* 371 */       this.x = getXRot(this.rot);
/*     */     }
/* 373 */     return this.x;
/*     */   }
/*     */ 
/*     */   public float getXDirAdj()
/*     */   {
/* 385 */     return getXRot(getDir());
/*     */   }
/*     */ 
/*     */   private float getYLowerLeftRot(float rotation)
/*     */   {
/* 397 */     if (rotation == 0.0F)
/*     */     {
/* 399 */       return this.textPos.getValue(2, 1);
/*     */     }
/* 401 */     if (rotation == 90.0F)
/*     */     {
/* 403 */       return this.pageWidth - this.textPos.getValue(2, 0);
/*     */     }
/* 405 */     if (rotation == 180.0F)
/*     */     {
/* 407 */       return this.pageHeight - this.textPos.getValue(2, 1);
/*     */     }
/* 409 */     if (rotation == 270.0F)
/*     */     {
/* 411 */       return this.textPos.getValue(2, 0);
/*     */     }
/* 413 */     return 0.0F;
/*     */   }
/*     */ 
/*     */   public float getY()
/*     */   {
/* 424 */     if (this.y == (1.0F / -1.0F))
/*     */     {
/* 426 */       if ((this.rot == 0) || (this.rot == 180))
/*     */       {
/* 428 */         this.y = (this.pageHeight - getYLowerLeftRot(this.rot));
/*     */       }
/*     */       else
/*     */       {
/* 432 */         this.y = (this.pageWidth - getYLowerLeftRot(this.rot));
/*     */       }
/*     */     }
/* 435 */     return this.y;
/*     */   }
/*     */ 
/*     */   public float getYDirAdj()
/*     */   {
/* 446 */     float dir = getDir();
/*     */ 
/* 448 */     if ((dir == 0.0F) || (dir == 180.0F))
/*     */     {
/* 450 */       return this.pageHeight - getYLowerLeftRot(dir);
/*     */     }
/*     */ 
/* 454 */     return this.pageWidth - getYLowerLeftRot(dir);
/*     */   }
/*     */ 
/*     */   private float getWidthRot(float rotation)
/*     */   {
/* 468 */     if ((rotation == 90.0F) || (rotation == 270.0F))
/*     */     {
/* 470 */       return Math.abs(this.endY - this.textPos.getYPosition());
/*     */     }
/*     */ 
/* 474 */     return Math.abs(this.endX - this.textPos.getXPosition());
/*     */   }
/*     */ 
/*     */   public float getWidth()
/*     */   {
/* 485 */     return getWidthRot(this.rot);
/*     */   }
/*     */ 
/*     */   public float getWidthDirAdj()
/*     */   {
/* 495 */     return getWidthRot(getDir());
/*     */   }
/*     */ 
/*     */   public float getHeight()
/*     */   {
/* 505 */     return this.maxTextHeight;
/*     */   }
/*     */ 
/*     */   public float getHeightDir()
/*     */   {
/* 516 */     return this.maxTextHeight;
/*     */   }
/*     */ 
/*     */   public float getFontSize()
/*     */   {
/* 527 */     return this.fontSize;
/*     */   }
/*     */ 
/*     */   public float getFontSizeInPt()
/*     */   {
/* 538 */     return this.fontSizePt;
/*     */   }
/*     */ 
/*     */   public PDFont getFont()
/*     */   {
/* 548 */     return this.font;
/*     */   }
/*     */ 
/*     */   @Deprecated
/*     */   public float getWordSpacing()
/*     */   {
/* 559 */     return this.wordSpacing;
/*     */   }
/*     */ 
/*     */   public float getWidthOfSpace()
/*     */   {
/* 571 */     return this.widthOfSpace;
/*     */   }
/*     */ 
/*     */   public float getXScale()
/*     */   {
/* 578 */     return this.textPos.getXScale();
/*     */   }
/*     */ 
/*     */   public float getYScale()
/*     */   {
/* 586 */     return this.textPos.getYScale();
/*     */   }
/*     */ 
/*     */   public float[] getIndividualWidths()
/*     */   {
/* 596 */     return this.widths;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 606 */     return getCharacter();
/*     */   }
/*     */ 
/*     */   public boolean contains(TextPosition tp2)
/*     */   {
/* 620 */     double thisXstart = getXDirAdj();
/* 621 */     double thisXend = getXDirAdj() + getWidthDirAdj();
/*     */ 
/* 623 */     double tp2Xstart = tp2.getXDirAdj();
/* 624 */     double tp2Xend = tp2.getXDirAdj() + tp2.getWidthDirAdj();
/*     */ 
/* 629 */     if ((tp2Xend <= thisXstart) || (tp2Xstart >= thisXend))
/*     */     {
/* 631 */       return false;
/*     */     }
/*     */ 
/* 638 */     if ((tp2.getYDirAdj() + tp2.getHeightDir() < getYDirAdj()) || (tp2.getYDirAdj() > getYDirAdj() + getHeightDir()))
/*     */     {
/* 641 */       return false;
/*     */     }
/*     */ 
/* 647 */     if ((tp2Xstart > thisXstart) && (tp2Xend > thisXend))
/*     */     {
/* 649 */       double overlap = thisXend - tp2Xstart;
/* 650 */       double overlapPercent = overlap / getWidthDirAdj();
/* 651 */       return overlapPercent > 0.15D;
/*     */     }
/* 653 */     if ((tp2Xstart < thisXstart) && (tp2Xend < thisXend))
/*     */     {
/* 655 */       double overlap = tp2Xend - thisXstart;
/* 656 */       double overlapPercent = overlap / getWidthDirAdj();
/* 657 */       return overlapPercent > 0.15D;
/*     */     }
/* 659 */     return true;
/*     */   }
/*     */ 
/*     */   public void mergeDiacritic(TextPosition diacritic, TextNormalize normalize)
/*     */   {
/* 674 */     if (diacritic.getCharacter().length() > 1)
/*     */     {
/* 676 */       return;
/*     */     }
/*     */ 
/* 679 */     float diacXStart = diacritic.getXDirAdj();
/* 680 */     float diacXEnd = diacXStart + diacritic.widths[0];
/*     */ 
/* 682 */     float currCharXStart = getXDirAdj();
/*     */ 
/* 684 */     int strLen = this.str.length();
/* 685 */     boolean wasAdded = false;
/*     */ 
/* 687 */     for (int i = 0; (i < strLen) && (!wasAdded); i++)
/*     */     {
/* 689 */       float currCharXEnd = currCharXStart + this.widths[i];
/*     */ 
/* 696 */       if ((diacXStart < currCharXStart) && (diacXEnd <= currCharXEnd))
/*     */       {
/* 698 */         if (i == 0)
/*     */         {
/* 700 */           insertDiacritic(i, diacritic, normalize);
/*     */         }
/*     */         else
/*     */         {
/* 704 */           float distanceOverlapping1 = diacXEnd - currCharXStart;
/* 705 */           float percentage1 = distanceOverlapping1 / this.widths[i];
/*     */ 
/* 707 */           float distanceOverlapping2 = currCharXStart - diacXStart;
/* 708 */           float percentage2 = distanceOverlapping2 / this.widths[(i - 1)];
/*     */ 
/* 710 */           if (percentage1 >= percentage2)
/*     */           {
/* 712 */             insertDiacritic(i, diacritic, normalize);
/*     */           }
/*     */           else
/*     */           {
/* 716 */             insertDiacritic(i - 1, diacritic, normalize);
/*     */           }
/*     */         }
/* 719 */         wasAdded = true;
/*     */       }
/* 723 */       else if ((diacXStart < currCharXStart) && (diacXEnd > currCharXEnd))
/*     */       {
/* 725 */         insertDiacritic(i, diacritic, normalize);
/* 726 */         wasAdded = true;
/*     */       }
/* 730 */       else if ((diacXStart >= currCharXStart) && (diacXEnd <= currCharXEnd))
/*     */       {
/* 732 */         insertDiacritic(i, diacritic, normalize);
/* 733 */         wasAdded = true;
/*     */       }
/* 738 */       else if ((diacXStart >= currCharXStart) && (diacXEnd > currCharXEnd) && (i == strLen - 1))
/*     */       {
/* 740 */         insertDiacritic(i, diacritic, normalize);
/* 741 */         wasAdded = true;
/*     */       }
/*     */ 
/* 747 */       currCharXStart += this.widths[i];
/*     */     }
/*     */   }
/*     */ 
/*     */   private void insertDiacritic(int i, TextPosition diacritic, TextNormalize normalize)
/*     */   {
/* 764 */     int dir = Character.getDirectionality(this.str.charAt(i));
/* 765 */     StringBuffer buf = new StringBuffer();
/*     */ 
/* 767 */     buf.append(this.str.substring(0, i));
/*     */ 
/* 769 */     float[] widths2 = new float[this.widths.length + 1];
/* 770 */     System.arraycopy(this.widths, 0, widths2, 0, i);
/*     */ 
/* 772 */     if ((dir == 1) || (dir == 2) || (dir == 16) || (dir == 17))
/*     */     {
/* 777 */       buf.append(normalize.normalizeDiac(diacritic.getCharacter()));
/* 778 */       widths2[i] = 0.0F;
/* 779 */       buf.append(this.str.charAt(i));
/* 780 */       widths2[(i + 1)] = this.widths[i];
/*     */     }
/*     */     else
/*     */     {
/* 784 */       buf.append(this.str.charAt(i));
/* 785 */       widths2[i] = this.widths[i];
/* 786 */       buf.append(normalize.normalizeDiac(diacritic.getCharacter()));
/* 787 */       widths2[(i + 1)] = 0.0F;
/*     */     }
/*     */ 
/* 791 */     buf.append(this.str.substring(i + 1, this.str.length()));
/* 792 */     System.arraycopy(this.widths, i + 1, widths2, i + 2, this.widths.length - i - 1);
/*     */ 
/* 794 */     this.str = buf.toString();
/* 795 */     this.widths = widths2;
/*     */   }
/*     */ 
/*     */   public boolean isDiacritic()
/*     */   {
/* 804 */     String cText = getCharacter();
/* 805 */     if (cText.length() != 1)
/*     */     {
/* 807 */       return false;
/*     */     }
/* 809 */     int type = Character.getType(cText.charAt(0));
/* 810 */     return (type == 6) || (type == 27) || (type == 4);
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.util.TextPosition
 * JD-Core Version:    0.6.2
 */