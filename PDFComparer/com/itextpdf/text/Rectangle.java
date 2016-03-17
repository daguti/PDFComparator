/*     */ package com.itextpdf.text;
/*     */ 
/*     */ import com.itextpdf.text.pdf.GrayColor;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ 
/*     */ public class Rectangle
/*     */   implements Element
/*     */ {
/*     */   public static final int UNDEFINED = -1;
/*     */   public static final int TOP = 1;
/*     */   public static final int BOTTOM = 2;
/*     */   public static final int LEFT = 4;
/*     */   public static final int RIGHT = 8;
/*     */   public static final int NO_BORDER = 0;
/*     */   public static final int BOX = 15;
/*     */   protected float llx;
/*     */   protected float lly;
/*     */   protected float urx;
/*     */   protected float ury;
/* 103 */   protected int rotation = 0;
/*     */ 
/* 106 */   protected BaseColor backgroundColor = null;
/*     */ 
/* 109 */   protected int border = -1;
/*     */ 
/* 112 */   protected boolean useVariableBorders = false;
/*     */ 
/* 115 */   protected float borderWidth = -1.0F;
/*     */ 
/* 118 */   protected float borderWidthLeft = -1.0F;
/*     */ 
/* 121 */   protected float borderWidthRight = -1.0F;
/*     */ 
/* 124 */   protected float borderWidthTop = -1.0F;
/*     */ 
/* 127 */   protected float borderWidthBottom = -1.0F;
/*     */ 
/* 130 */   protected BaseColor borderColor = null;
/*     */ 
/* 133 */   protected BaseColor borderColorLeft = null;
/*     */ 
/* 136 */   protected BaseColor borderColorRight = null;
/*     */ 
/* 139 */   protected BaseColor borderColorTop = null;
/*     */ 
/* 142 */   protected BaseColor borderColorBottom = null;
/*     */ 
/*     */   public Rectangle(float llx, float lly, float urx, float ury)
/*     */   {
/* 155 */     this.llx = llx;
/* 156 */     this.lly = lly;
/* 157 */     this.urx = urx;
/* 158 */     this.ury = ury;
/*     */   }
/*     */ 
/*     */   public Rectangle(float llx, float lly, float urx, float ury, int rotation)
/*     */   {
/* 172 */     this(llx, lly, urx, ury);
/* 173 */     setRotation(rotation);
/*     */   }
/*     */ 
/*     */   public Rectangle(float urx, float ury)
/*     */   {
/* 184 */     this(0.0F, 0.0F, urx, ury);
/*     */   }
/*     */ 
/*     */   public Rectangle(float urx, float ury, int rotation)
/*     */   {
/* 197 */     this(0.0F, 0.0F, urx, ury, rotation);
/*     */   }
/*     */ 
/*     */   public Rectangle(Rectangle rect)
/*     */   {
/* 206 */     this(rect.llx, rect.lly, rect.urx, rect.ury);
/* 207 */     cloneNonPositionParameters(rect);
/*     */   }
/*     */ 
/*     */   public Rectangle(com.itextpdf.awt.geom.Rectangle rect)
/*     */   {
/* 215 */     this((float)rect.getX(), (float)rect.getY(), (float)(rect.getX() + rect.getWidth()), (float)(rect.getY() + rect.getHeight()));
/*     */   }
/*     */ 
/*     */   public boolean process(ElementListener listener)
/*     */   {
/*     */     try
/*     */     {
/* 229 */       return listener.add(this);
/*     */     } catch (DocumentException de) {
/*     */     }
/* 232 */     return false;
/*     */   }
/*     */ 
/*     */   public int type()
/*     */   {
/* 242 */     return 30;
/*     */   }
/*     */ 
/*     */   public List<Chunk> getChunks()
/*     */   {
/* 251 */     return new ArrayList();
/*     */   }
/*     */ 
/*     */   public boolean isContent()
/*     */   {
/* 259 */     return true;
/*     */   }
/*     */ 
/*     */   public boolean isNestable()
/*     */   {
/* 267 */     return false;
/*     */   }
/*     */ 
/*     */   public void setLeft(float llx)
/*     */   {
/* 278 */     this.llx = llx;
/*     */   }
/*     */ 
/*     */   public float getLeft()
/*     */   {
/* 287 */     return this.llx;
/*     */   }
/*     */ 
/*     */   public float getLeft(float margin)
/*     */   {
/* 297 */     return this.llx + margin;
/*     */   }
/*     */ 
/*     */   public void setRight(float urx)
/*     */   {
/* 306 */     this.urx = urx;
/*     */   }
/*     */ 
/*     */   public float getRight()
/*     */   {
/* 315 */     return this.urx;
/*     */   }
/*     */ 
/*     */   public float getRight(float margin)
/*     */   {
/* 325 */     return this.urx - margin;
/*     */   }
/*     */ 
/*     */   public float getWidth()
/*     */   {
/* 334 */     return this.urx - this.llx;
/*     */   }
/*     */ 
/*     */   public void setTop(float ury)
/*     */   {
/* 343 */     this.ury = ury;
/*     */   }
/*     */ 
/*     */   public float getTop()
/*     */   {
/* 352 */     return this.ury;
/*     */   }
/*     */ 
/*     */   public float getTop(float margin)
/*     */   {
/* 362 */     return this.ury - margin;
/*     */   }
/*     */ 
/*     */   public void setBottom(float lly)
/*     */   {
/* 371 */     this.lly = lly;
/*     */   }
/*     */ 
/*     */   public float getBottom()
/*     */   {
/* 380 */     return this.lly;
/*     */   }
/*     */ 
/*     */   public float getBottom(float margin)
/*     */   {
/* 390 */     return this.lly + margin;
/*     */   }
/*     */ 
/*     */   public float getHeight()
/*     */   {
/* 399 */     return this.ury - this.lly;
/*     */   }
/*     */ 
/*     */   public void normalize()
/*     */   {
/* 407 */     if (this.llx > this.urx) {
/* 408 */       float a = this.llx;
/* 409 */       this.llx = this.urx;
/* 410 */       this.urx = a;
/*     */     }
/* 412 */     if (this.lly > this.ury) {
/* 413 */       float a = this.lly;
/* 414 */       this.lly = this.ury;
/* 415 */       this.ury = a;
/*     */     }
/*     */   }
/*     */ 
/*     */   public int getRotation()
/*     */   {
/* 427 */     return this.rotation;
/*     */   }
/*     */ 
/*     */   public void setRotation(int rotation)
/*     */   {
/* 436 */     this.rotation = (rotation % 360);
/* 437 */     switch (this.rotation) {
/*     */     case 90:
/*     */     case 180:
/*     */     case 270:
/* 441 */       break;
/*     */     default:
/* 443 */       this.rotation = 0;
/*     */     }
/*     */   }
/*     */ 
/*     */   public Rectangle rotate()
/*     */   {
/* 454 */     Rectangle rect = new Rectangle(this.lly, this.llx, this.ury, this.urx);
/* 455 */     rect.setRotation(this.rotation + 90);
/* 456 */     return rect;
/*     */   }
/*     */ 
/*     */   public BaseColor getBackgroundColor()
/*     */   {
/* 467 */     return this.backgroundColor;
/*     */   }
/*     */ 
/*     */   public void setBackgroundColor(BaseColor backgroundColor)
/*     */   {
/* 477 */     this.backgroundColor = backgroundColor;
/*     */   }
/*     */ 
/*     */   public float getGrayFill()
/*     */   {
/* 487 */     if ((this.backgroundColor instanceof GrayColor))
/* 488 */       return ((GrayColor)this.backgroundColor).getGray();
/* 489 */     return 0.0F;
/*     */   }
/*     */ 
/*     */   public void setGrayFill(float value)
/*     */   {
/* 498 */     this.backgroundColor = new GrayColor(value);
/*     */   }
/*     */ 
/*     */   public int getBorder()
/*     */   {
/* 509 */     return this.border;
/*     */   }
/*     */ 
/*     */   public boolean hasBorders()
/*     */   {
/* 518 */     switch (this.border) {
/*     */     case -1:
/*     */     case 0:
/* 521 */       return false;
/*     */     }
/* 523 */     return (this.borderWidth > 0.0F) || (this.borderWidthLeft > 0.0F) || (this.borderWidthRight > 0.0F) || (this.borderWidthTop > 0.0F) || (this.borderWidthBottom > 0.0F);
/*     */   }
/*     */ 
/*     */   public boolean hasBorder(int type)
/*     */   {
/* 535 */     if (this.border == -1)
/* 536 */       return false;
/* 537 */     return (this.border & type) == type;
/*     */   }
/*     */ 
/*     */   public void setBorder(int border)
/*     */   {
/* 550 */     this.border = border;
/*     */   }
/*     */ 
/*     */   public boolean isUseVariableBorders()
/*     */   {
/* 561 */     return this.useVariableBorders;
/*     */   }
/*     */ 
/*     */   public void setUseVariableBorders(boolean useVariableBorders)
/*     */   {
/* 570 */     this.useVariableBorders = useVariableBorders;
/*     */   }
/*     */ 
/*     */   public void enableBorderSide(int side)
/*     */   {
/* 580 */     if (this.border == -1)
/* 581 */       this.border = 0;
/* 582 */     this.border |= side;
/*     */   }
/*     */ 
/*     */   public void disableBorderSide(int side)
/*     */   {
/* 592 */     if (this.border == -1)
/* 593 */       this.border = 0;
/* 594 */     this.border &= (side ^ 0xFFFFFFFF);
/*     */   }
/*     */ 
/*     */   public float getBorderWidth()
/*     */   {
/* 605 */     return this.borderWidth;
/*     */   }
/*     */ 
/*     */   public void setBorderWidth(float borderWidth)
/*     */   {
/* 614 */     this.borderWidth = borderWidth;
/*     */   }
/*     */ 
/*     */   private float getVariableBorderWidth(float variableWidthValue, int side)
/*     */   {
/* 625 */     if ((this.border & side) != 0)
/* 626 */       return variableWidthValue != -1.0F ? variableWidthValue : this.borderWidth;
/* 627 */     return 0.0F;
/*     */   }
/*     */ 
/*     */   private void updateBorderBasedOnWidth(float width, int side)
/*     */   {
/* 640 */     this.useVariableBorders = true;
/* 641 */     if (width > 0.0F)
/* 642 */       enableBorderSide(side);
/*     */     else
/* 644 */       disableBorderSide(side);
/*     */   }
/*     */ 
/*     */   public float getBorderWidthLeft()
/*     */   {
/* 653 */     return getVariableBorderWidth(this.borderWidthLeft, 4);
/*     */   }
/*     */ 
/*     */   public void setBorderWidthLeft(float borderWidthLeft)
/*     */   {
/* 662 */     this.borderWidthLeft = borderWidthLeft;
/* 663 */     updateBorderBasedOnWidth(borderWidthLeft, 4);
/*     */   }
/*     */ 
/*     */   public float getBorderWidthRight()
/*     */   {
/* 672 */     return getVariableBorderWidth(this.borderWidthRight, 8);
/*     */   }
/*     */ 
/*     */   public void setBorderWidthRight(float borderWidthRight)
/*     */   {
/* 681 */     this.borderWidthRight = borderWidthRight;
/* 682 */     updateBorderBasedOnWidth(borderWidthRight, 8);
/*     */   }
/*     */ 
/*     */   public float getBorderWidthTop()
/*     */   {
/* 691 */     return getVariableBorderWidth(this.borderWidthTop, 1);
/*     */   }
/*     */ 
/*     */   public void setBorderWidthTop(float borderWidthTop)
/*     */   {
/* 700 */     this.borderWidthTop = borderWidthTop;
/* 701 */     updateBorderBasedOnWidth(borderWidthTop, 1);
/*     */   }
/*     */ 
/*     */   public float getBorderWidthBottom()
/*     */   {
/* 710 */     return getVariableBorderWidth(this.borderWidthBottom, 2);
/*     */   }
/*     */ 
/*     */   public void setBorderWidthBottom(float borderWidthBottom)
/*     */   {
/* 719 */     this.borderWidthBottom = borderWidthBottom;
/* 720 */     updateBorderBasedOnWidth(borderWidthBottom, 2);
/*     */   }
/*     */ 
/*     */   public BaseColor getBorderColor()
/*     */   {
/* 731 */     return this.borderColor;
/*     */   }
/*     */ 
/*     */   public void setBorderColor(BaseColor borderColor)
/*     */   {
/* 740 */     this.borderColor = borderColor;
/*     */   }
/*     */ 
/*     */   public BaseColor getBorderColorLeft()
/*     */   {
/* 749 */     if (this.borderColorLeft == null)
/* 750 */       return this.borderColor;
/* 751 */     return this.borderColorLeft;
/*     */   }
/*     */ 
/*     */   public void setBorderColorLeft(BaseColor borderColorLeft)
/*     */   {
/* 760 */     this.borderColorLeft = borderColorLeft;
/*     */   }
/*     */ 
/*     */   public BaseColor getBorderColorRight()
/*     */   {
/* 769 */     if (this.borderColorRight == null)
/* 770 */       return this.borderColor;
/* 771 */     return this.borderColorRight;
/*     */   }
/*     */ 
/*     */   public void setBorderColorRight(BaseColor borderColorRight)
/*     */   {
/* 780 */     this.borderColorRight = borderColorRight;
/*     */   }
/*     */ 
/*     */   public BaseColor getBorderColorTop()
/*     */   {
/* 789 */     if (this.borderColorTop == null)
/* 790 */       return this.borderColor;
/* 791 */     return this.borderColorTop;
/*     */   }
/*     */ 
/*     */   public void setBorderColorTop(BaseColor borderColorTop)
/*     */   {
/* 800 */     this.borderColorTop = borderColorTop;
/*     */   }
/*     */ 
/*     */   public BaseColor getBorderColorBottom()
/*     */   {
/* 809 */     if (this.borderColorBottom == null)
/* 810 */       return this.borderColor;
/* 811 */     return this.borderColorBottom;
/*     */   }
/*     */ 
/*     */   public void setBorderColorBottom(BaseColor borderColorBottom)
/*     */   {
/* 820 */     this.borderColorBottom = borderColorBottom;
/*     */   }
/*     */ 
/*     */   public Rectangle rectangle(float top, float bottom)
/*     */   {
/* 833 */     Rectangle tmp = new Rectangle(this);
/* 834 */     if (getTop() > top) {
/* 835 */       tmp.setTop(top);
/* 836 */       tmp.disableBorderSide(1);
/*     */     }
/* 838 */     if (getBottom() < bottom) {
/* 839 */       tmp.setBottom(bottom);
/* 840 */       tmp.disableBorderSide(2);
/*     */     }
/* 842 */     return tmp;
/*     */   }
/*     */ 
/*     */   public void cloneNonPositionParameters(Rectangle rect)
/*     */   {
/* 852 */     this.rotation = rect.rotation;
/* 853 */     this.backgroundColor = rect.backgroundColor;
/* 854 */     this.border = rect.border;
/* 855 */     this.useVariableBorders = rect.useVariableBorders;
/* 856 */     this.borderWidth = rect.borderWidth;
/* 857 */     this.borderWidthLeft = rect.borderWidthLeft;
/* 858 */     this.borderWidthRight = rect.borderWidthRight;
/* 859 */     this.borderWidthTop = rect.borderWidthTop;
/* 860 */     this.borderWidthBottom = rect.borderWidthBottom;
/* 861 */     this.borderColor = rect.borderColor;
/* 862 */     this.borderColorLeft = rect.borderColorLeft;
/* 863 */     this.borderColorRight = rect.borderColorRight;
/* 864 */     this.borderColorTop = rect.borderColorTop;
/* 865 */     this.borderColorBottom = rect.borderColorBottom;
/*     */   }
/*     */ 
/*     */   public void softCloneNonPositionParameters(Rectangle rect)
/*     */   {
/* 875 */     if (rect.rotation != 0)
/* 876 */       this.rotation = rect.rotation;
/* 877 */     if (rect.backgroundColor != null)
/* 878 */       this.backgroundColor = rect.backgroundColor;
/* 879 */     if (rect.border != -1)
/* 880 */       this.border = rect.border;
/* 881 */     if (this.useVariableBorders)
/* 882 */       this.useVariableBorders = rect.useVariableBorders;
/* 883 */     if (rect.borderWidth != -1.0F)
/* 884 */       this.borderWidth = rect.borderWidth;
/* 885 */     if (rect.borderWidthLeft != -1.0F)
/* 886 */       this.borderWidthLeft = rect.borderWidthLeft;
/* 887 */     if (rect.borderWidthRight != -1.0F)
/* 888 */       this.borderWidthRight = rect.borderWidthRight;
/* 889 */     if (rect.borderWidthTop != -1.0F)
/* 890 */       this.borderWidthTop = rect.borderWidthTop;
/* 891 */     if (rect.borderWidthBottom != -1.0F)
/* 892 */       this.borderWidthBottom = rect.borderWidthBottom;
/* 893 */     if (rect.borderColor != null)
/* 894 */       this.borderColor = rect.borderColor;
/* 895 */     if (rect.borderColorLeft != null)
/* 896 */       this.borderColorLeft = rect.borderColorLeft;
/* 897 */     if (rect.borderColorRight != null)
/* 898 */       this.borderColorRight = rect.borderColorRight;
/* 899 */     if (rect.borderColorTop != null)
/* 900 */       this.borderColorTop = rect.borderColorTop;
/* 901 */     if (rect.borderColorBottom != null)
/* 902 */       this.borderColorBottom = rect.borderColorBottom;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 911 */     StringBuffer buf = new StringBuffer("Rectangle: ");
/* 912 */     buf.append(getWidth());
/* 913 */     buf.append('x');
/* 914 */     buf.append(getHeight());
/* 915 */     buf.append(" (rot: ");
/* 916 */     buf.append(this.rotation);
/* 917 */     buf.append(" degrees)");
/* 918 */     return buf.toString();
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/* 923 */     if ((obj instanceof Rectangle)) {
/* 924 */       Rectangle other = (Rectangle)obj;
/*     */ 
/* 928 */       return (other.llx == this.llx) && (other.lly == this.lly) && (other.urx == this.urx) && (other.ury == this.ury) && (other.rotation == this.rotation);
/*     */     }
/* 930 */     return false;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.Rectangle
 * JD-Core Version:    0.6.2
 */