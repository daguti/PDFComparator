/*     */ package com.itextpdf.text.pdf;
/*     */ 
/*     */ import com.itextpdf.text.BaseColor;
/*     */ import com.itextpdf.text.error_messages.MessageLocalization;
/*     */ import java.io.IOException;
/*     */ 
/*     */ public class PdfShading
/*     */ {
/*     */   protected PdfDictionary shading;
/*     */   protected PdfWriter writer;
/*     */   protected int shadingType;
/*     */   protected ColorDetails colorDetails;
/*     */   protected PdfName shadingName;
/*     */   protected PdfIndirectReference shadingReference;
/*     */   private BaseColor cspace;
/*     */   protected float[] bBox;
/*  74 */   protected boolean antiAlias = false;
/*     */ 
/*     */   protected PdfShading(PdfWriter writer)
/*     */   {
/*  78 */     this.writer = writer;
/*     */   }
/*     */ 
/*     */   protected void setColorSpace(BaseColor color) {
/*  82 */     this.cspace = color;
/*  83 */     int type = ExtendedColor.getType(color);
/*  84 */     PdfObject colorSpace = null;
/*  85 */     switch (type) {
/*     */     case 1:
/*  87 */       colorSpace = PdfName.DEVICEGRAY;
/*  88 */       break;
/*     */     case 2:
/*  91 */       colorSpace = PdfName.DEVICECMYK;
/*  92 */       break;
/*     */     case 3:
/*  95 */       SpotColor spot = (SpotColor)color;
/*  96 */       this.colorDetails = this.writer.addSimple(spot.getPdfSpotColor());
/*  97 */       colorSpace = this.colorDetails.getIndirectReference();
/*  98 */       break;
/*     */     case 6:
/* 101 */       DeviceNColor deviceNColor = (DeviceNColor)color;
/* 102 */       this.colorDetails = this.writer.addSimple(deviceNColor.getPdfDeviceNColor());
/* 103 */       colorSpace = this.colorDetails.getIndirectReference();
/* 104 */       break;
/*     */     case 4:
/*     */     case 5:
/* 108 */       throwColorSpaceError();
/*     */     default:
/* 111 */       colorSpace = PdfName.DEVICERGB;
/*     */     }
/*     */ 
/* 114 */     this.shading.put(PdfName.COLORSPACE, colorSpace);
/*     */   }
/*     */ 
/*     */   public BaseColor getColorSpace() {
/* 118 */     return this.cspace;
/*     */   }
/*     */ 
/*     */   public static void throwColorSpaceError() {
/* 122 */     throw new IllegalArgumentException(MessageLocalization.getComposedMessage("a.tiling.or.shading.pattern.cannot.be.used.as.a.color.space.in.a.shading.pattern", new Object[0]));
/*     */   }
/*     */ 
/*     */   public static void checkCompatibleColors(BaseColor c1, BaseColor c2) {
/* 126 */     int type1 = ExtendedColor.getType(c1);
/* 127 */     int type2 = ExtendedColor.getType(c2);
/* 128 */     if (type1 != type2)
/* 129 */       throw new IllegalArgumentException(MessageLocalization.getComposedMessage("both.colors.must.be.of.the.same.type", new Object[0]));
/* 130 */     if ((type1 == 3) && (((SpotColor)c1).getPdfSpotColor() != ((SpotColor)c2).getPdfSpotColor()))
/* 131 */       throw new IllegalArgumentException(MessageLocalization.getComposedMessage("the.spot.color.must.be.the.same.only.the.tint.can.vary", new Object[0]));
/* 132 */     if ((type1 == 4) || (type1 == 5))
/* 133 */       throwColorSpaceError();
/*     */   }
/*     */ 
/*     */   public static float[] getColorArray(BaseColor color) {
/* 137 */     int type = ExtendedColor.getType(color);
/* 138 */     switch (type) {
/*     */     case 1:
/* 140 */       return new float[] { ((GrayColor)color).getGray() };
/*     */     case 2:
/* 143 */       CMYKColor cmyk = (CMYKColor)color;
/* 144 */       return new float[] { cmyk.getCyan(), cmyk.getMagenta(), cmyk.getYellow(), cmyk.getBlack() };
/*     */     case 3:
/* 147 */       return new float[] { ((SpotColor)color).getTint() };
/*     */     case 6:
/* 150 */       return ((DeviceNColor)color).getTints();
/*     */     case 0:
/* 153 */       return new float[] { color.getRed() / 255.0F, color.getGreen() / 255.0F, color.getBlue() / 255.0F };
/*     */     case 4:
/*     */     case 5:
/* 156 */     }throwColorSpaceError();
/* 157 */     return null;
/*     */   }
/*     */ 
/*     */   public static PdfShading type1(PdfWriter writer, BaseColor colorSpace, float[] domain, float[] tMatrix, PdfFunction function) {
/* 161 */     PdfShading sp = new PdfShading(writer);
/* 162 */     sp.shading = new PdfDictionary();
/* 163 */     sp.shadingType = 1;
/* 164 */     sp.shading.put(PdfName.SHADINGTYPE, new PdfNumber(sp.shadingType));
/* 165 */     sp.setColorSpace(colorSpace);
/* 166 */     if (domain != null)
/* 167 */       sp.shading.put(PdfName.DOMAIN, new PdfArray(domain));
/* 168 */     if (tMatrix != null)
/* 169 */       sp.shading.put(PdfName.MATRIX, new PdfArray(tMatrix));
/* 170 */     sp.shading.put(PdfName.FUNCTION, function.getReference());
/* 171 */     return sp;
/*     */   }
/*     */ 
/*     */   public static PdfShading type2(PdfWriter writer, BaseColor colorSpace, float[] coords, float[] domain, PdfFunction function, boolean[] extend) {
/* 175 */     PdfShading sp = new PdfShading(writer);
/* 176 */     sp.shading = new PdfDictionary();
/* 177 */     sp.shadingType = 2;
/* 178 */     sp.shading.put(PdfName.SHADINGTYPE, new PdfNumber(sp.shadingType));
/* 179 */     sp.setColorSpace(colorSpace);
/* 180 */     sp.shading.put(PdfName.COORDS, new PdfArray(coords));
/* 181 */     if (domain != null)
/* 182 */       sp.shading.put(PdfName.DOMAIN, new PdfArray(domain));
/* 183 */     sp.shading.put(PdfName.FUNCTION, function.getReference());
/* 184 */     if ((extend != null) && ((extend[0] != 0) || (extend[1] != 0))) {
/* 185 */       PdfArray array = new PdfArray(extend[0] != 0 ? PdfBoolean.PDFTRUE : PdfBoolean.PDFFALSE);
/* 186 */       array.add(extend[1] != 0 ? PdfBoolean.PDFTRUE : PdfBoolean.PDFFALSE);
/* 187 */       sp.shading.put(PdfName.EXTEND, array);
/*     */     }
/* 189 */     return sp;
/*     */   }
/*     */ 
/*     */   public static PdfShading type3(PdfWriter writer, BaseColor colorSpace, float[] coords, float[] domain, PdfFunction function, boolean[] extend) {
/* 193 */     PdfShading sp = type2(writer, colorSpace, coords, domain, function, extend);
/* 194 */     sp.shadingType = 3;
/* 195 */     sp.shading.put(PdfName.SHADINGTYPE, new PdfNumber(sp.shadingType));
/* 196 */     return sp;
/*     */   }
/*     */ 
/*     */   public static PdfShading simpleAxial(PdfWriter writer, float x0, float y0, float x1, float y1, BaseColor startColor, BaseColor endColor, boolean extendStart, boolean extendEnd) {
/* 200 */     checkCompatibleColors(startColor, endColor);
/* 201 */     PdfFunction function = PdfFunction.type2(writer, new float[] { 0.0F, 1.0F }, null, getColorArray(startColor), getColorArray(endColor), 1.0F);
/*     */ 
/* 203 */     return type2(writer, startColor, new float[] { x0, y0, x1, y1 }, null, function, new boolean[] { extendStart, extendEnd });
/*     */   }
/*     */ 
/*     */   public static PdfShading simpleAxial(PdfWriter writer, float x0, float y0, float x1, float y1, BaseColor startColor, BaseColor endColor) {
/* 207 */     return simpleAxial(writer, x0, y0, x1, y1, startColor, endColor, true, true);
/*     */   }
/*     */ 
/*     */   public static PdfShading simpleRadial(PdfWriter writer, float x0, float y0, float r0, float x1, float y1, float r1, BaseColor startColor, BaseColor endColor, boolean extendStart, boolean extendEnd) {
/* 211 */     checkCompatibleColors(startColor, endColor);
/* 212 */     PdfFunction function = PdfFunction.type2(writer, new float[] { 0.0F, 1.0F }, null, getColorArray(startColor), getColorArray(endColor), 1.0F);
/*     */ 
/* 214 */     return type3(writer, startColor, new float[] { x0, y0, r0, x1, y1, r1 }, null, function, new boolean[] { extendStart, extendEnd });
/*     */   }
/*     */ 
/*     */   public static PdfShading simpleRadial(PdfWriter writer, float x0, float y0, float r0, float x1, float y1, float r1, BaseColor startColor, BaseColor endColor) {
/* 218 */     return simpleRadial(writer, x0, y0, r0, x1, y1, r1, startColor, endColor, true, true);
/*     */   }
/*     */ 
/*     */   PdfName getShadingName() {
/* 222 */     return this.shadingName;
/*     */   }
/*     */ 
/*     */   PdfIndirectReference getShadingReference() {
/* 226 */     if (this.shadingReference == null)
/* 227 */       this.shadingReference = this.writer.getPdfIndirectReference();
/* 228 */     return this.shadingReference;
/*     */   }
/*     */ 
/*     */   void setName(int number) {
/* 232 */     this.shadingName = new PdfName("Sh" + number);
/*     */   }
/*     */ 
/*     */   public void addToBody() throws IOException {
/* 236 */     if (this.bBox != null)
/* 237 */       this.shading.put(PdfName.BBOX, new PdfArray(this.bBox));
/* 238 */     if (this.antiAlias)
/* 239 */       this.shading.put(PdfName.ANTIALIAS, PdfBoolean.PDFTRUE);
/* 240 */     this.writer.addToBody(this.shading, getShadingReference());
/*     */   }
/*     */ 
/*     */   PdfWriter getWriter() {
/* 244 */     return this.writer;
/*     */   }
/*     */ 
/*     */   ColorDetails getColorDetails() {
/* 248 */     return this.colorDetails;
/*     */   }
/*     */ 
/*     */   public float[] getBBox() {
/* 252 */     return this.bBox;
/*     */   }
/*     */ 
/*     */   public void setBBox(float[] bBox) {
/* 256 */     if (bBox.length != 4)
/* 257 */       throw new IllegalArgumentException(MessageLocalization.getComposedMessage("bbox.must.be.a.4.element.array", new Object[0]));
/* 258 */     this.bBox = bBox;
/*     */   }
/*     */ 
/*     */   public boolean isAntiAlias() {
/* 262 */     return this.antiAlias;
/*     */   }
/*     */ 
/*     */   public void setAntiAlias(boolean antiAlias) {
/* 266 */     this.antiAlias = antiAlias;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.pdf.PdfShading
 * JD-Core Version:    0.6.2
 */