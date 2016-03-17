/*     */ package com.itextpdf.text.pdf;
/*     */ 
/*     */ import com.itextpdf.text.BaseColor;
/*     */ import com.itextpdf.text.error_messages.MessageLocalization;
/*     */ import java.util.Arrays;
/*     */ 
/*     */ public class PdfLabColor
/*     */   implements ICachedColorSpace
/*     */ {
/*   9 */   float[] whitePoint = { 0.9505F, 1.0F, 1.089F };
/*  10 */   float[] blackPoint = null;
/*  11 */   float[] range = null;
/*     */ 
/*     */   public PdfLabColor() {
/*     */   }
/*     */   public PdfLabColor(float[] whitePoint) {
/*  16 */     if ((whitePoint == null) || (whitePoint.length != 3) || (whitePoint[0] < 1.0E-006F) || (whitePoint[2] < 1.0E-006F) || (whitePoint[1] < 0.999999F) || (whitePoint[1] > 1.000001F))
/*     */     {
/*  20 */       throw new RuntimeException(MessageLocalization.getComposedMessage("lab.cs.white.point", new Object[0]));
/*  21 */     }this.whitePoint = whitePoint;
/*     */   }
/*     */ 
/*     */   public PdfLabColor(float[] whitePoint, float[] blackPoint) {
/*  25 */     this(whitePoint);
/*  26 */     this.blackPoint = blackPoint;
/*     */   }
/*     */ 
/*     */   public PdfLabColor(float[] whitePoint, float[] blackPoint, float[] range) {
/*  30 */     this(whitePoint, blackPoint);
/*  31 */     this.range = range;
/*     */   }
/*     */ 
/*     */   public PdfObject getPdfObject(PdfWriter writer) {
/*  35 */     PdfArray array = new PdfArray(PdfName.LAB);
/*  36 */     PdfDictionary dictionary = new PdfDictionary();
/*  37 */     if ((this.whitePoint == null) || (this.whitePoint.length != 3) || (this.whitePoint[0] < 1.0E-006F) || (this.whitePoint[2] < 1.0E-006F) || (this.whitePoint[1] < 0.999999F) || (this.whitePoint[1] > 1.000001F))
/*     */     {
/*  41 */       throw new RuntimeException(MessageLocalization.getComposedMessage("lab.cs.white.point", new Object[0]));
/*  42 */     }dictionary.put(PdfName.WHITEPOINT, new PdfArray(this.whitePoint));
/*  43 */     if (this.blackPoint != null) {
/*  44 */       if ((this.blackPoint.length != 3) || (this.blackPoint[0] < -1.0E-006F) || (this.blackPoint[1] < -1.0E-006F) || (this.blackPoint[2] < -1.0E-006F))
/*     */       {
/*  46 */         throw new RuntimeException(MessageLocalization.getComposedMessage("lab.cs.black.point", new Object[0]));
/*  47 */       }dictionary.put(PdfName.BLACKPOINT, new PdfArray(this.blackPoint));
/*     */     }
/*  49 */     if (this.range != null) {
/*  50 */       if ((this.range.length != 4) || (this.range[0] > this.range[1]) || (this.range[2] > this.range[3]))
/*  51 */         throw new RuntimeException(MessageLocalization.getComposedMessage("lab.cs.range", new Object[0]));
/*  52 */       dictionary.put(PdfName.RANGE, new PdfArray(this.range));
/*     */     }
/*  54 */     array.add(dictionary);
/*  55 */     return array;
/*     */   }
/*     */ 
/*     */   public BaseColor lab2Rgb(float l, float a, float b) {
/*  59 */     double[] clinear = lab2RgbLinear(l, a, b);
/*  60 */     return new BaseColor((float)clinear[0], (float)clinear[1], (float)clinear[2]);
/*     */   }
/*     */ 
/*     */   CMYKColor lab2Cmyk(float l, float a, float b) {
/*  64 */     double[] clinear = lab2RgbLinear(l, a, b);
/*     */ 
/*  66 */     double r = clinear[0];
/*  67 */     double g = clinear[1];
/*  68 */     double bee = clinear[2];
/*  69 */     double computedC = 0.0D; double computedM = 0.0D; double computedY = 0.0D; double computedK = 0.0D;
/*     */ 
/*  72 */     if ((r == 0.0D) && (g == 0.0D) && (b == 0.0F)) {
/*  73 */       computedK = 1.0D;
/*     */     } else {
/*  75 */       computedC = 1.0D - r;
/*  76 */       computedM = 1.0D - g;
/*  77 */       computedY = 1.0D - bee;
/*     */ 
/*  79 */       double minCMY = Math.min(computedC, Math.min(computedM, computedY));
/*     */ 
/*  81 */       computedC = (computedC - minCMY) / (1.0D - minCMY);
/*  82 */       computedM = (computedM - minCMY) / (1.0D - minCMY);
/*  83 */       computedY = (computedY - minCMY) / (1.0D - minCMY);
/*  84 */       computedK = minCMY;
/*     */     }
/*     */ 
/*  87 */     return new CMYKColor((float)computedC, (float)computedM, (float)computedY, (float)computedK);
/*     */   }
/*     */ 
/*     */   protected double[] lab2RgbLinear(float l, float a, float b) {
/*  91 */     if ((this.range != null) && (this.range.length == 4)) {
/*  92 */       if (a < this.range[0])
/*  93 */         a = this.range[0];
/*  94 */       if (a > this.range[1])
/*  95 */         a = this.range[1];
/*  96 */       if (b < this.range[2])
/*  97 */         b = this.range[2];
/*  98 */       if (b > this.range[3])
/*  99 */         b = this.range[3];
/*     */     }
/* 101 */     double theta = 0.2068965517241379D;
/*     */ 
/* 103 */     double fy = (l + 16.0F) / 116.0D;
/* 104 */     double fx = fy + a / 500.0D;
/* 105 */     double fz = fy - b / 200.0D;
/*     */ 
/* 107 */     double x = fx > theta ? this.whitePoint[0] * (fx * fx * fx) : (fx - 0.1379310344827586D) * 3.0D * (theta * theta) * this.whitePoint[0];
/* 108 */     double y = fy > theta ? this.whitePoint[1] * (fy * fy * fy) : (fy - 0.1379310344827586D) * 3.0D * (theta * theta) * this.whitePoint[1];
/* 109 */     double z = fz > theta ? this.whitePoint[2] * (fz * fz * fz) : (fz - 0.1379310344827586D) * 3.0D * (theta * theta) * this.whitePoint[2];
/*     */ 
/* 111 */     double[] clinear = new double[3];
/* 112 */     clinear[0] = (x * 3.241D - y * 1.5374D - z * 0.4986D);
/* 113 */     clinear[1] = (-x * 0.9692D + y * 1.876D - z * 0.0416D);
/* 114 */     clinear[2] = (x * 0.0556D - y * 0.204D + z * 1.057D);
/*     */ 
/* 116 */     for (int i = 0; i < 3; i++) {
/* 117 */       clinear[i] = (clinear[i] <= 0.0031308D ? 12.92D * clinear[i] : 1.055D * Math.pow(clinear[i], 0.4166666666666667D) - 0.055D);
/*     */ 
/* 120 */       if (clinear[i] < 0.0D)
/* 121 */         clinear[i] = 0.0D;
/* 122 */       else if (clinear[i] > 1.0D) {
/* 123 */         clinear[i] = 1.0D;
/*     */       }
/*     */     }
/* 126 */     return clinear;
/*     */   }
/*     */ 
/*     */   public LabColor rgb2lab(BaseColor baseColor) {
/* 130 */     double rLinear = baseColor.getRed() / 255.0F;
/* 131 */     double gLinear = baseColor.getGreen() / 255.0F;
/* 132 */     double bLinear = baseColor.getBlue() / 255.0F;
/*     */ 
/* 135 */     double r = rLinear > 0.04045D ? Math.pow((rLinear + 0.055D) / 1.055D, 2.2D) : rLinear / 12.92D;
/* 136 */     double g = gLinear > 0.04045D ? Math.pow((gLinear + 0.055D) / 1.055D, 2.2D) : gLinear / 12.92D;
/* 137 */     double b = bLinear > 0.04045D ? Math.pow((bLinear + 0.055D) / 1.055D, 2.2D) : bLinear / 12.92D;
/*     */ 
/* 140 */     double x = r * 0.4124D + g * 0.3576D + b * 0.1805D;
/* 141 */     double y = r * 0.2126D + g * 0.7152D + b * 0.0722D;
/* 142 */     double z = r * 0.0193D + g * 0.1192D + b * 0.9505D;
/*     */ 
/* 144 */     float l = (float)Math.round((116.0D * fXyz(y / this.whitePoint[1]) - 16.0D) * 1000.0D) / 1000.0F;
/* 145 */     float a = (float)Math.round(500.0D * (fXyz(x / this.whitePoint[0]) - fXyz(y / this.whitePoint[1])) * 1000.0D) / 1000.0F;
/* 146 */     float bee = (float)Math.round(200.0D * (fXyz(y / this.whitePoint[1]) - fXyz(z / this.whitePoint[2])) * 1000.0D) / 1000.0F;
/*     */ 
/* 148 */     return new LabColor(this, l, a, bee);
/*     */   }
/*     */ 
/*     */   private static double fXyz(double t) {
/* 152 */     return t > 0.008855999999999999D ? Math.pow(t, 0.3333333333333333D) : 7.787D * t + 0.1379310344827586D;
/*     */   }
/*     */ 
/*     */   public boolean equals(Object o)
/*     */   {
/* 157 */     if (this == o) return true;
/* 158 */     if (!(o instanceof PdfLabColor)) return false;
/*     */ 
/* 160 */     PdfLabColor that = (PdfLabColor)o;
/*     */ 
/* 162 */     if (!Arrays.equals(this.blackPoint, that.blackPoint)) return false;
/* 163 */     if (!Arrays.equals(this.range, that.range)) return false;
/* 164 */     if (!Arrays.equals(this.whitePoint, that.whitePoint)) return false;
/*     */ 
/* 166 */     return true;
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/* 171 */     int result = Arrays.hashCode(this.whitePoint);
/* 172 */     result = 31 * result + (this.blackPoint != null ? Arrays.hashCode(this.blackPoint) : 0);
/* 173 */     result = 31 * result + (this.range != null ? Arrays.hashCode(this.range) : 0);
/* 174 */     return result;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.pdf.PdfLabColor
 * JD-Core Version:    0.6.2
 */