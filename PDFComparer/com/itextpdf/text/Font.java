/*     */ package com.itextpdf.text;
/*     */ 
/*     */ import com.itextpdf.text.pdf.BaseFont;
/*     */ 
/*     */ public class Font
/*     */   implements Comparable<Font>
/*     */ {
/*     */   public static final int NORMAL = 0;
/*     */   public static final int BOLD = 1;
/*     */   public static final int ITALIC = 2;
/*     */   public static final int UNDERLINE = 4;
/*     */   public static final int STRIKETHRU = 8;
/*     */   public static final int BOLDITALIC = 3;
/*     */   public static final int UNDEFINED = -1;
/*     */   public static final int DEFAULTSIZE = 12;
/* 138 */   private FontFamily family = FontFamily.UNDEFINED;
/*     */ 
/* 141 */   private float size = -1.0F;
/*     */ 
/* 144 */   private int style = -1;
/*     */ 
/* 147 */   private BaseColor color = null;
/*     */ 
/* 150 */   private BaseFont baseFont = null;
/*     */ 
/*     */   public Font(Font other)
/*     */   {
/* 161 */     this.family = other.family;
/* 162 */     this.size = other.size;
/* 163 */     this.style = other.style;
/* 164 */     this.color = other.color;
/* 165 */     this.baseFont = other.baseFont;
/*     */   }
/*     */ 
/*     */   public Font(FontFamily family, float size, int style, BaseColor color)
/*     */   {
/* 183 */     this.family = family;
/* 184 */     this.size = size;
/* 185 */     this.style = style;
/* 186 */     this.color = color;
/*     */   }
/*     */ 
/*     */   public Font(BaseFont bf, float size, int style, BaseColor color)
/*     */   {
/* 203 */     this.baseFont = bf;
/* 204 */     this.size = size;
/* 205 */     this.style = style;
/* 206 */     this.color = color;
/*     */   }
/*     */ 
/*     */   public Font(BaseFont bf, float size, int style)
/*     */   {
/* 220 */     this(bf, size, style, null);
/*     */   }
/*     */ 
/*     */   public Font(BaseFont bf, float size)
/*     */   {
/* 232 */     this(bf, size, -1, null);
/*     */   }
/*     */ 
/*     */   public Font(BaseFont bf)
/*     */   {
/* 242 */     this(bf, -1.0F, -1, null);
/*     */   }
/*     */ 
/*     */   public Font(FontFamily family, float size, int style)
/*     */   {
/* 258 */     this(family, size, style, null);
/*     */   }
/*     */ 
/*     */   public Font(FontFamily family, float size)
/*     */   {
/* 272 */     this(family, size, -1, null);
/*     */   }
/*     */ 
/*     */   public Font(FontFamily family)
/*     */   {
/* 284 */     this(family, -1.0F, -1, null);
/*     */   }
/*     */ 
/*     */   public Font()
/*     */   {
/* 292 */     this(FontFamily.UNDEFINED, -1.0F, -1, null);
/*     */   }
/*     */ 
/*     */   public int compareTo(Font font)
/*     */   {
/* 305 */     if (font == null) {
/* 306 */       return -1;
/*     */     }
/*     */     try
/*     */     {
/* 310 */       if ((this.baseFont != null) && (!this.baseFont.equals(font.getBaseFont()))) {
/* 311 */         return -2;
/*     */       }
/* 313 */       if (this.family != font.getFamily()) {
/* 314 */         return 1;
/*     */       }
/* 316 */       if (this.size != font.getSize()) {
/* 317 */         return 2;
/*     */       }
/* 319 */       if (this.style != font.getStyle()) {
/* 320 */         return 3;
/*     */       }
/* 322 */       if (this.color == null) {
/* 323 */         if (font.color == null) {
/* 324 */           return 0;
/*     */         }
/* 326 */         return 4;
/*     */       }
/* 328 */       if (font.color == null) {
/* 329 */         return 4;
/*     */       }
/* 331 */       if (this.color.equals(font.getColor())) {
/* 332 */         return 0;
/*     */       }
/* 334 */       return 4; } catch (ClassCastException cce) {
/*     */     }
/* 336 */     return -3;
/*     */   }
/*     */ 
/*     */   public FontFamily getFamily()
/*     */   {
/* 348 */     return this.family;
/*     */   }
/*     */ 
/*     */   public String getFamilyname()
/*     */   {
/* 357 */     String tmp = "unknown";
/* 358 */     switch (1.$SwitchMap$com$itextpdf$text$Font$FontFamily[getFamily().ordinal()]) {
/*     */     case 1:
/* 360 */       return "Courier";
/*     */     case 2:
/* 362 */       return "Helvetica";
/*     */     case 3:
/* 364 */       return "Times-Roman";
/*     */     case 4:
/* 366 */       return "Symbol";
/*     */     case 5:
/* 368 */       return "ZapfDingbats";
/*     */     }
/* 370 */     if (this.baseFont != null) {
/* 371 */       String[][] names = this.baseFont.getFamilyFontName();
/* 372 */       for (String[] name : names) {
/* 373 */         if ("0".equals(name[2])) {
/* 374 */           return name[3];
/*     */         }
/* 376 */         if ("1033".equals(name[2])) {
/* 377 */           tmp = name[3];
/*     */         }
/* 379 */         if ("".equals(name[2])) {
/* 380 */           tmp = name[3];
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/* 385 */     return tmp;
/*     */   }
/*     */ 
/*     */   public void setFamily(String family)
/*     */   {
/* 396 */     this.family = getFamily(family);
/*     */   }
/*     */ 
/*     */   public static FontFamily getFamily(String family)
/*     */   {
/* 410 */     if (family.equalsIgnoreCase("Courier")) {
/* 411 */       return FontFamily.COURIER;
/*     */     }
/* 413 */     if (family.equalsIgnoreCase("Helvetica")) {
/* 414 */       return FontFamily.HELVETICA;
/*     */     }
/* 416 */     if (family.equalsIgnoreCase("Times-Roman")) {
/* 417 */       return FontFamily.TIMES_ROMAN;
/*     */     }
/* 419 */     if (family.equalsIgnoreCase("Symbol")) {
/* 420 */       return FontFamily.SYMBOL;
/*     */     }
/* 422 */     if (family.equalsIgnoreCase("ZapfDingbats")) {
/* 423 */       return FontFamily.ZAPFDINGBATS;
/*     */     }
/* 425 */     return FontFamily.UNDEFINED;
/*     */   }
/*     */ 
/*     */   public float getSize()
/*     */   {
/* 436 */     return this.size;
/*     */   }
/*     */ 
/*     */   public float getCalculatedSize()
/*     */   {
/* 447 */     float s = this.size;
/* 448 */     if (s == -1.0F) {
/* 449 */       s = 12.0F;
/*     */     }
/* 451 */     return s;
/*     */   }
/*     */ 
/*     */   public float getCalculatedLeading(float linespacing)
/*     */   {
/* 462 */     return linespacing * getCalculatedSize();
/*     */   }
/*     */ 
/*     */   public void setSize(float size)
/*     */   {
/* 472 */     this.size = size;
/*     */   }
/*     */ 
/*     */   public int getStyle()
/*     */   {
/* 483 */     return this.style;
/*     */   }
/*     */ 
/*     */   public int getCalculatedStyle()
/*     */   {
/* 494 */     int style = this.style;
/* 495 */     if (style == -1) {
/* 496 */       style = 0;
/*     */     }
/* 498 */     if (this.baseFont != null)
/* 499 */       return style;
/* 500 */     if ((this.family == FontFamily.SYMBOL) || (this.family == FontFamily.ZAPFDINGBATS)) {
/* 501 */       return style;
/*     */     }
/* 503 */     return style & 0xFFFFFFFC;
/*     */   }
/*     */ 
/*     */   public boolean isBold()
/*     */   {
/* 512 */     if (this.style == -1) {
/* 513 */       return false;
/*     */     }
/* 515 */     return (this.style & 0x1) == 1;
/*     */   }
/*     */ 
/*     */   public boolean isItalic()
/*     */   {
/* 524 */     if (this.style == -1) {
/* 525 */       return false;
/*     */     }
/* 527 */     return (this.style & 0x2) == 2;
/*     */   }
/*     */ 
/*     */   public boolean isUnderlined()
/*     */   {
/* 536 */     if (this.style == -1) {
/* 537 */       return false;
/*     */     }
/* 539 */     return (this.style & 0x4) == 4;
/*     */   }
/*     */ 
/*     */   public boolean isStrikethru()
/*     */   {
/* 548 */     if (this.style == -1) {
/* 549 */       return false;
/*     */     }
/* 551 */     return (this.style & 0x8) == 8;
/*     */   }
/*     */ 
/*     */   public void setStyle(int style)
/*     */   {
/* 561 */     this.style = style;
/*     */   }
/*     */ 
/*     */   public void setStyle(String style)
/*     */   {
/* 572 */     if (this.style == -1)
/* 573 */       this.style = 0;
/* 574 */     this.style |= getStyleValue(style);
/*     */   }
/*     */ 
/*     */   public static int getStyleValue(String style)
/*     */   {
/* 587 */     int s = 0;
/* 588 */     if (style.indexOf(FontStyle.NORMAL.getValue()) != -1) {
/* 589 */       s |= 0;
/*     */     }
/* 591 */     if (style.indexOf(FontStyle.BOLD.getValue()) != -1) {
/* 592 */       s |= 1;
/*     */     }
/* 594 */     if (style.indexOf(FontStyle.ITALIC.getValue()) != -1) {
/* 595 */       s |= 2;
/*     */     }
/* 597 */     if (style.indexOf(FontStyle.OBLIQUE.getValue()) != -1) {
/* 598 */       s |= 2;
/*     */     }
/* 600 */     if (style.indexOf(FontStyle.UNDERLINE.getValue()) != -1) {
/* 601 */       s |= 4;
/*     */     }
/* 603 */     if (style.indexOf(FontStyle.LINETHROUGH.getValue()) != -1) {
/* 604 */       s |= 8;
/*     */     }
/* 606 */     return s;
/*     */   }
/*     */ 
/*     */   public BaseColor getColor()
/*     */   {
/* 617 */     return this.color;
/*     */   }
/*     */ 
/*     */   public void setColor(BaseColor color)
/*     */   {
/* 628 */     this.color = color;
/*     */   }
/*     */ 
/*     */   public void setColor(int red, int green, int blue)
/*     */   {
/* 642 */     this.color = new BaseColor(red, green, blue);
/*     */   }
/*     */ 
/*     */   public BaseFont getBaseFont()
/*     */   {
/* 653 */     return this.baseFont;
/*     */   }
/*     */ 
/*     */   public BaseFont getCalculatedBaseFont(boolean specialEncoding)
/*     */   {
/* 667 */     if (this.baseFont != null)
/* 668 */       return this.baseFont;
/* 669 */     int style = this.style;
/* 670 */     if (style == -1) {
/* 671 */       style = 0;
/*     */     }
/* 673 */     String fontName = "Helvetica";
/* 674 */     String encoding = "Cp1252";
/* 675 */     BaseFont cfont = null;
/* 676 */     switch (1.$SwitchMap$com$itextpdf$text$Font$FontFamily[this.family.ordinal()]) {
/*     */     case 1:
/* 678 */       switch (style & 0x3) {
/*     */       case 1:
/* 680 */         fontName = "Courier-Bold";
/* 681 */         break;
/*     */       case 2:
/* 683 */         fontName = "Courier-Oblique";
/* 684 */         break;
/*     */       case 3:
/* 686 */         fontName = "Courier-BoldOblique";
/* 687 */         break;
/*     */       default:
/* 690 */         fontName = "Courier";
/* 691 */       }break;
/*     */     case 3:
/* 695 */       switch (style & 0x3) {
/*     */       case 1:
/* 697 */         fontName = "Times-Bold";
/* 698 */         break;
/*     */       case 2:
/* 700 */         fontName = "Times-Italic";
/* 701 */         break;
/*     */       case 3:
/* 703 */         fontName = "Times-BoldItalic";
/* 704 */         break;
/*     */       case 0:
/*     */       default:
/* 707 */         fontName = "Times-Roman";
/* 708 */       }break;
/*     */     case 4:
/* 712 */       fontName = "Symbol";
/* 713 */       if (specialEncoding)
/* 714 */         encoding = "Symbol"; break;
/*     */     case 5:
/* 717 */       fontName = "ZapfDingbats";
/* 718 */       if (specialEncoding)
/* 719 */         encoding = "ZapfDingbats"; break;
/*     */     case 2:
/*     */     default:
/* 723 */       switch (style & 0x3) {
/*     */       case 1:
/* 725 */         fontName = "Helvetica-Bold";
/* 726 */         break;
/*     */       case 2:
/* 728 */         fontName = "Helvetica-Oblique";
/* 729 */         break;
/*     */       case 3:
/* 731 */         fontName = "Helvetica-BoldOblique";
/* 732 */         break;
/*     */       case 0:
/*     */       default:
/* 735 */         fontName = "Helvetica";
/*     */       }
/*     */       break;
/*     */     }
/*     */     try
/*     */     {
/* 741 */       cfont = BaseFont.createFont(fontName, encoding, false);
/*     */     } catch (Exception ee) {
/* 743 */       throw new ExceptionConverter(ee);
/*     */     }
/* 745 */     return cfont;
/*     */   }
/*     */ 
/*     */   public boolean isStandardFont()
/*     */   {
/* 758 */     return (this.family == FontFamily.UNDEFINED) && (this.size == -1.0F) && (this.style == -1) && (this.color == null) && (this.baseFont == null);
/*     */   }
/*     */ 
/*     */   public Font difference(Font font)
/*     */   {
/* 771 */     if (font == null) {
/* 772 */       return this;
/*     */     }
/* 774 */     float dSize = font.size;
/* 775 */     if (dSize == -1.0F) {
/* 776 */       dSize = this.size;
/*     */     }
/*     */ 
/* 779 */     int dStyle = -1;
/* 780 */     int style1 = this.style;
/* 781 */     int style2 = font.getStyle();
/* 782 */     if ((style1 != -1) || (style2 != -1)) {
/* 783 */       if (style1 == -1)
/* 784 */         style1 = 0;
/* 785 */       if (style2 == -1)
/* 786 */         style2 = 0;
/* 787 */       dStyle = style1 | style2;
/*     */     }
/*     */ 
/* 790 */     BaseColor dColor = font.color;
/* 791 */     if (dColor == null) {
/* 792 */       dColor = this.color;
/*     */     }
/*     */ 
/* 795 */     if (font.baseFont != null) {
/* 796 */       return new Font(font.baseFont, dSize, dStyle, dColor);
/*     */     }
/* 798 */     if (font.getFamily() != FontFamily.UNDEFINED) {
/* 799 */       return new Font(font.family, dSize, dStyle, dColor);
/*     */     }
/* 801 */     if (this.baseFont != null) {
/* 802 */       if (dStyle == style1) {
/* 803 */         return new Font(this.baseFont, dSize, dStyle, dColor);
/*     */       }
/* 805 */       return FontFactory.getFont(getFamilyname(), dSize, dStyle, dColor);
/*     */     }
/*     */ 
/* 809 */     return new Font(this.family, dSize, dStyle, dColor);
/*     */   }
/*     */ 
/*     */   public static enum FontStyle
/*     */   {
/*  88 */     NORMAL("normal"), BOLD("bold"), ITALIC("italic"), OBLIQUE("oblique"), UNDERLINE("underline"), 
/*  89 */     LINETHROUGH("line-through");
/*     */ 
/*     */     private String code;
/*     */ 
/*     */     private FontStyle(String code) {
/*  94 */       this.code = code;
/*     */     }
/*     */ 
/*     */     public String getValue()
/*     */     {
/* 102 */       return this.code;
/*     */     }
/*     */   }
/*     */ 
/*     */   public static enum FontFamily
/*     */   {
/*  72 */     COURIER, 
/*  73 */     HELVETICA, 
/*  74 */     TIMES_ROMAN, 
/*  75 */     SYMBOL, 
/*  76 */     ZAPFDINGBATS, 
/*  77 */     UNDEFINED;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.Font
 * JD-Core Version:    0.6.2
 */