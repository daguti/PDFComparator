/*     */ package com.itextpdf.text;
/*     */ 
/*     */ import com.itextpdf.text.error_messages.MessageLocalization;
/*     */ 
/*     */ public class BaseColor
/*     */ {
/*  54 */   public static final BaseColor WHITE = new BaseColor(255, 255, 255);
/*  55 */   public static final BaseColor LIGHT_GRAY = new BaseColor(192, 192, 192);
/*  56 */   public static final BaseColor GRAY = new BaseColor(128, 128, 128);
/*  57 */   public static final BaseColor DARK_GRAY = new BaseColor(64, 64, 64);
/*  58 */   public static final BaseColor BLACK = new BaseColor(0, 0, 0);
/*  59 */   public static final BaseColor RED = new BaseColor(255, 0, 0);
/*  60 */   public static final BaseColor PINK = new BaseColor(255, 175, 175);
/*  61 */   public static final BaseColor ORANGE = new BaseColor(255, 200, 0);
/*  62 */   public static final BaseColor YELLOW = new BaseColor(255, 255, 0);
/*  63 */   public static final BaseColor GREEN = new BaseColor(0, 255, 0);
/*  64 */   public static final BaseColor MAGENTA = new BaseColor(255, 0, 255);
/*  65 */   public static final BaseColor CYAN = new BaseColor(0, 255, 255);
/*  66 */   public static final BaseColor BLUE = new BaseColor(0, 0, 255);
/*     */   private static final double FACTOR = 0.7D;
/*     */   private int value;
/*     */ 
/*     */   public BaseColor(int red, int green, int blue, int alpha)
/*     */   {
/*  78 */     setValue(red, green, blue, alpha);
/*     */   }
/*     */ 
/*     */   public BaseColor(int red, int green, int blue)
/*     */   {
/*  87 */     this(red, green, blue, 255);
/*     */   }
/*     */ 
/*     */   public BaseColor(float red, float green, float blue, float alpha)
/*     */   {
/*  98 */     this((int)(red * 255.0F + 0.5D), (int)(green * 255.0F + 0.5D), (int)(blue * 255.0F + 0.5D), (int)(alpha * 255.0F + 0.5D));
/*     */   }
/*     */ 
/*     */   public BaseColor(float red, float green, float blue)
/*     */   {
/* 108 */     this(red, green, blue, 1.0F);
/*     */   }
/*     */ 
/*     */   public BaseColor(int argb)
/*     */   {
/* 115 */     this.value = argb;
/*     */   }
/*     */ 
/*     */   public int getRGB()
/*     */   {
/* 122 */     return this.value;
/*     */   }
/*     */ 
/*     */   public int getRed()
/*     */   {
/* 129 */     return getRGB() >> 16 & 0xFF;
/*     */   }
/*     */ 
/*     */   public int getGreen()
/*     */   {
/* 136 */     return getRGB() >> 8 & 0xFF;
/*     */   }
/*     */ 
/*     */   public int getBlue()
/*     */   {
/* 143 */     return getRGB() >> 0 & 0xFF;
/*     */   }
/*     */ 
/*     */   public int getAlpha()
/*     */   {
/* 150 */     return getRGB() >> 24 & 0xFF;
/*     */   }
/*     */ 
/*     */   public BaseColor brighter()
/*     */   {
/* 158 */     int r = getRed();
/* 159 */     int g = getGreen();
/* 160 */     int b = getBlue();
/*     */ 
/* 162 */     int i = 3;
/* 163 */     if ((r == 0) && (g == 0) && (b == 0)) {
/* 164 */       return new BaseColor(i, i, i);
/*     */     }
/* 166 */     if ((r > 0) && (r < i))
/* 167 */       r = i;
/* 168 */     if ((g > 0) && (g < i))
/* 169 */       g = i;
/* 170 */     if ((b > 0) && (b < i)) {
/* 171 */       b = i;
/*     */     }
/* 173 */     return new BaseColor(Math.min((int)(r / 0.7D), 255), Math.min((int)(g / 0.7D), 255), Math.min((int)(b / 0.7D), 255));
/*     */   }
/*     */ 
/*     */   public BaseColor darker()
/*     */   {
/* 183 */     return new BaseColor(Math.max((int)(getRed() * 0.7D), 0), Math.max((int)(getGreen() * 0.7D), 0), Math.max((int)(getBlue() * 0.7D), 0));
/*     */   }
/*     */ 
/*     */   public boolean equals(Object obj)
/*     */   {
/* 190 */     return ((obj instanceof BaseColor)) && (((BaseColor)obj).value == this.value);
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/* 195 */     return this.value;
/*     */   }
/*     */ 
/*     */   protected void setValue(int red, int green, int blue, int alpha) {
/* 199 */     validate(red);
/* 200 */     validate(green);
/* 201 */     validate(blue);
/* 202 */     validate(alpha);
/* 203 */     this.value = ((alpha & 0xFF) << 24 | (red & 0xFF) << 16 | (green & 0xFF) << 8 | (blue & 0xFF) << 0);
/*     */   }
/*     */ 
/*     */   private static void validate(int value)
/*     */   {
/* 208 */     if ((value < 0) || (value > 255))
/* 209 */       throw new IllegalArgumentException(MessageLocalization.getComposedMessage("color.value.outside.range.0.255", new Object[0]));
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 217 */     return "Color value[" + Integer.toString(this.value, 16) + "]";
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.BaseColor
 * JD-Core Version:    0.6.2
 */