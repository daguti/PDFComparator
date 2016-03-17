/*     */ package com.itextpdf.text.pdf;
/*     */ 
/*     */ import com.itextpdf.text.BaseColor;
/*     */ import com.itextpdf.text.DocumentException;
/*     */ import com.itextpdf.text.Image;
/*     */ import com.itextpdf.text.Rectangle;
/*     */ import com.itextpdf.text.error_messages.MessageLocalization;
/*     */ 
/*     */ public final class PdfPatternPainter extends PdfTemplate
/*     */ {
/*     */   float xstep;
/*     */   float ystep;
/*  61 */   boolean stencil = false;
/*     */   BaseColor defaultColor;
/*     */ 
/*     */   private PdfPatternPainter()
/*     */   {
/*  70 */     this.type = 3;
/*     */   }
/*     */ 
/*     */   PdfPatternPainter(PdfWriter wr)
/*     */   {
/*  80 */     super(wr);
/*  81 */     this.type = 3;
/*     */   }
/*     */ 
/*     */   PdfPatternPainter(PdfWriter wr, BaseColor defaultColor) {
/*  85 */     this(wr);
/*  86 */     this.stencil = true;
/*  87 */     if (defaultColor == null)
/*  88 */       this.defaultColor = BaseColor.GRAY;
/*     */     else
/*  90 */       this.defaultColor = defaultColor;
/*     */   }
/*     */ 
/*     */   public void setXStep(float xstep)
/*     */   {
/* 100 */     this.xstep = xstep;
/*     */   }
/*     */ 
/*     */   public void setYStep(float ystep)
/*     */   {
/* 110 */     this.ystep = ystep;
/*     */   }
/*     */ 
/*     */   public float getXStep()
/*     */   {
/* 118 */     return this.xstep;
/*     */   }
/*     */ 
/*     */   public float getYStep()
/*     */   {
/* 126 */     return this.ystep;
/*     */   }
/*     */ 
/*     */   public boolean isStencil()
/*     */   {
/* 134 */     return this.stencil;
/*     */   }
/*     */ 
/*     */   public void setPatternMatrix(float a, float b, float c, float d, float e, float f)
/*     */   {
/* 147 */     setMatrix(a, b, c, d, e, f);
/*     */   }
/*     */ 
/*     */   public PdfPattern getPattern()
/*     */   {
/* 154 */     return new PdfPattern(this);
/*     */   }
/*     */ 
/*     */   public PdfPattern getPattern(int compressionLevel)
/*     */   {
/* 164 */     return new PdfPattern(this, compressionLevel);
/*     */   }
/*     */ 
/*     */   public PdfContentByte getDuplicate()
/*     */   {
/* 174 */     PdfPatternPainter tpl = new PdfPatternPainter();
/* 175 */     tpl.writer = this.writer;
/* 176 */     tpl.pdf = this.pdf;
/* 177 */     tpl.thisReference = this.thisReference;
/* 178 */     tpl.pageResources = this.pageResources;
/* 179 */     tpl.bBox = new Rectangle(this.bBox);
/* 180 */     tpl.xstep = this.xstep;
/* 181 */     tpl.ystep = this.ystep;
/* 182 */     tpl.matrix = this.matrix;
/* 183 */     tpl.stencil = this.stencil;
/* 184 */     tpl.defaultColor = this.defaultColor;
/* 185 */     return tpl;
/*     */   }
/*     */ 
/*     */   public BaseColor getDefaultColor()
/*     */   {
/* 193 */     return this.defaultColor;
/*     */   }
/*     */ 
/*     */   public void setGrayFill(float gray)
/*     */   {
/* 200 */     checkNoColor();
/* 201 */     super.setGrayFill(gray);
/*     */   }
/*     */ 
/*     */   public void resetGrayFill()
/*     */   {
/* 208 */     checkNoColor();
/* 209 */     super.resetGrayFill();
/*     */   }
/*     */ 
/*     */   public void setGrayStroke(float gray)
/*     */   {
/* 216 */     checkNoColor();
/* 217 */     super.setGrayStroke(gray);
/*     */   }
/*     */ 
/*     */   public void resetGrayStroke()
/*     */   {
/* 224 */     checkNoColor();
/* 225 */     super.resetGrayStroke();
/*     */   }
/*     */ 
/*     */   public void setRGBColorFillF(float red, float green, float blue)
/*     */   {
/* 232 */     checkNoColor();
/* 233 */     super.setRGBColorFillF(red, green, blue);
/*     */   }
/*     */ 
/*     */   public void resetRGBColorFill()
/*     */   {
/* 240 */     checkNoColor();
/* 241 */     super.resetRGBColorFill();
/*     */   }
/*     */ 
/*     */   public void setRGBColorStrokeF(float red, float green, float blue)
/*     */   {
/* 248 */     checkNoColor();
/* 249 */     super.setRGBColorStrokeF(red, green, blue);
/*     */   }
/*     */ 
/*     */   public void resetRGBColorStroke()
/*     */   {
/* 256 */     checkNoColor();
/* 257 */     super.resetRGBColorStroke();
/*     */   }
/*     */ 
/*     */   public void setCMYKColorFillF(float cyan, float magenta, float yellow, float black)
/*     */   {
/* 264 */     checkNoColor();
/* 265 */     super.setCMYKColorFillF(cyan, magenta, yellow, black);
/*     */   }
/*     */ 
/*     */   public void resetCMYKColorFill()
/*     */   {
/* 272 */     checkNoColor();
/* 273 */     super.resetCMYKColorFill();
/*     */   }
/*     */ 
/*     */   public void setCMYKColorStrokeF(float cyan, float magenta, float yellow, float black)
/*     */   {
/* 280 */     checkNoColor();
/* 281 */     super.setCMYKColorStrokeF(cyan, magenta, yellow, black);
/*     */   }
/*     */ 
/*     */   public void resetCMYKColorStroke()
/*     */   {
/* 288 */     checkNoColor();
/* 289 */     super.resetCMYKColorStroke();
/*     */   }
/*     */ 
/*     */   public void addImage(Image image, float a, float b, float c, float d, float e, float f)
/*     */     throws DocumentException
/*     */   {
/* 296 */     if ((this.stencil) && (!image.isMask()))
/* 297 */       checkNoColor();
/* 298 */     super.addImage(image, a, b, c, d, e, f);
/*     */   }
/*     */ 
/*     */   public void setCMYKColorFill(int cyan, int magenta, int yellow, int black)
/*     */   {
/* 305 */     checkNoColor();
/* 306 */     super.setCMYKColorFill(cyan, magenta, yellow, black);
/*     */   }
/*     */ 
/*     */   public void setCMYKColorStroke(int cyan, int magenta, int yellow, int black)
/*     */   {
/* 313 */     checkNoColor();
/* 314 */     super.setCMYKColorStroke(cyan, magenta, yellow, black);
/*     */   }
/*     */ 
/*     */   public void setRGBColorFill(int red, int green, int blue)
/*     */   {
/* 321 */     checkNoColor();
/* 322 */     super.setRGBColorFill(red, green, blue);
/*     */   }
/*     */ 
/*     */   public void setRGBColorStroke(int red, int green, int blue)
/*     */   {
/* 329 */     checkNoColor();
/* 330 */     super.setRGBColorStroke(red, green, blue);
/*     */   }
/*     */ 
/*     */   public void setColorStroke(BaseColor color)
/*     */   {
/* 337 */     checkNoColor();
/* 338 */     super.setColorStroke(color);
/*     */   }
/*     */ 
/*     */   public void setColorFill(BaseColor color)
/*     */   {
/* 345 */     checkNoColor();
/* 346 */     super.setColorFill(color);
/*     */   }
/*     */ 
/*     */   public void setColorFill(PdfSpotColor sp, float tint)
/*     */   {
/* 353 */     checkNoColor();
/* 354 */     super.setColorFill(sp, tint);
/*     */   }
/*     */ 
/*     */   public void setColorStroke(PdfSpotColor sp, float tint)
/*     */   {
/* 361 */     checkNoColor();
/* 362 */     super.setColorStroke(sp, tint);
/*     */   }
/*     */ 
/*     */   public void setPatternFill(PdfPatternPainter p)
/*     */   {
/* 369 */     checkNoColor();
/* 370 */     super.setPatternFill(p);
/*     */   }
/*     */ 
/*     */   public void setPatternFill(PdfPatternPainter p, BaseColor color, float tint)
/*     */   {
/* 377 */     checkNoColor();
/* 378 */     super.setPatternFill(p, color, tint);
/*     */   }
/*     */ 
/*     */   public void setPatternStroke(PdfPatternPainter p, BaseColor color, float tint)
/*     */   {
/* 385 */     checkNoColor();
/* 386 */     super.setPatternStroke(p, color, tint);
/*     */   }
/*     */ 
/*     */   public void setPatternStroke(PdfPatternPainter p)
/*     */   {
/* 393 */     checkNoColor();
/* 394 */     super.setPatternStroke(p);
/*     */   }
/*     */ 
/*     */   void checkNoColor() {
/* 398 */     if (this.stencil)
/* 399 */       throw new RuntimeException(MessageLocalization.getComposedMessage("colors.are.not.allowed.in.uncolored.tile.patterns", new Object[0]));
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.pdf.PdfPatternPainter
 * JD-Core Version:    0.6.2
 */