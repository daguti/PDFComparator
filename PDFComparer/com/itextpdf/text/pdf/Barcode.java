/*     */ package com.itextpdf.text.pdf;
/*     */ 
/*     */ import com.itextpdf.text.BaseColor;
/*     */ import com.itextpdf.text.ExceptionConverter;
/*     */ import com.itextpdf.text.Rectangle;
/*     */ import java.awt.Color;
/*     */ 
/*     */ public abstract class Barcode
/*     */ {
/*     */   public static final int EAN13 = 1;
/*     */   public static final int EAN8 = 2;
/*     */   public static final int UPCA = 3;
/*     */   public static final int UPCE = 4;
/*     */   public static final int SUPP2 = 5;
/*     */   public static final int SUPP5 = 6;
/*     */   public static final int POSTNET = 7;
/*     */   public static final int PLANET = 8;
/*     */   public static final int CODE128 = 9;
/*     */   public static final int CODE128_UCC = 10;
/*     */   public static final int CODE128_RAW = 11;
/*     */   public static final int CODABAR = 12;
/*     */   protected float x;
/*     */   protected float n;
/*     */   protected BaseFont font;
/*     */   protected float size;
/*     */   protected float baseline;
/*     */   protected float barHeight;
/*     */   protected int textAlignment;
/*     */   protected boolean generateChecksum;
/*     */   protected boolean checksumText;
/*     */   protected boolean startStopText;
/*     */   protected boolean extended;
/* 133 */   protected String code = "";
/*     */   protected boolean guardBars;
/*     */   protected int codeType;
/* 144 */   protected float inkSpreading = 0.0F;
/*     */   protected String altText;
/*     */ 
/*     */   public float getX()
/*     */   {
/* 150 */     return this.x;
/*     */   }
/*     */ 
/*     */   public void setX(float x)
/*     */   {
/* 157 */     this.x = x;
/*     */   }
/*     */ 
/*     */   public float getN()
/*     */   {
/* 164 */     return this.n;
/*     */   }
/*     */ 
/*     */   public void setN(float n)
/*     */   {
/* 171 */     this.n = n;
/*     */   }
/*     */ 
/*     */   public BaseFont getFont()
/*     */   {
/* 178 */     return this.font;
/*     */   }
/*     */ 
/*     */   public void setFont(BaseFont font)
/*     */   {
/* 185 */     this.font = font;
/*     */   }
/*     */ 
/*     */   public float getSize()
/*     */   {
/* 192 */     return this.size;
/*     */   }
/*     */ 
/*     */   public void setSize(float size)
/*     */   {
/* 199 */     this.size = size;
/*     */   }
/*     */ 
/*     */   public float getBaseline()
/*     */   {
/* 208 */     return this.baseline;
/*     */   }
/*     */ 
/*     */   public void setBaseline(float baseline)
/*     */   {
/* 217 */     this.baseline = baseline;
/*     */   }
/*     */ 
/*     */   public float getBarHeight()
/*     */   {
/* 224 */     return this.barHeight;
/*     */   }
/*     */ 
/*     */   public void setBarHeight(float barHeight)
/*     */   {
/* 231 */     this.barHeight = barHeight;
/*     */   }
/*     */ 
/*     */   public int getTextAlignment()
/*     */   {
/* 239 */     return this.textAlignment;
/*     */   }
/*     */ 
/*     */   public void setTextAlignment(int textAlignment)
/*     */   {
/* 247 */     this.textAlignment = textAlignment;
/*     */   }
/*     */ 
/*     */   public boolean isGenerateChecksum()
/*     */   {
/* 254 */     return this.generateChecksum;
/*     */   }
/*     */ 
/*     */   public void setGenerateChecksum(boolean generateChecksum)
/*     */   {
/* 261 */     this.generateChecksum = generateChecksum;
/*     */   }
/*     */ 
/*     */   public boolean isChecksumText()
/*     */   {
/* 268 */     return this.checksumText;
/*     */   }
/*     */ 
/*     */   public void setChecksumText(boolean checksumText)
/*     */   {
/* 275 */     this.checksumText = checksumText;
/*     */   }
/*     */ 
/*     */   public boolean isStartStopText()
/*     */   {
/* 283 */     return this.startStopText;
/*     */   }
/*     */ 
/*     */   public void setStartStopText(boolean startStopText)
/*     */   {
/* 291 */     this.startStopText = startStopText;
/*     */   }
/*     */ 
/*     */   public boolean isExtended()
/*     */   {
/* 298 */     return this.extended;
/*     */   }
/*     */ 
/*     */   public void setExtended(boolean extended)
/*     */   {
/* 305 */     this.extended = extended;
/*     */   }
/*     */ 
/*     */   public String getCode()
/*     */   {
/* 312 */     return this.code;
/*     */   }
/*     */ 
/*     */   public void setCode(String code)
/*     */   {
/* 319 */     this.code = code;
/*     */   }
/*     */ 
/*     */   public boolean isGuardBars()
/*     */   {
/* 326 */     return this.guardBars;
/*     */   }
/*     */ 
/*     */   public void setGuardBars(boolean guardBars)
/*     */   {
/* 333 */     this.guardBars = guardBars;
/*     */   }
/*     */ 
/*     */   public int getCodeType()
/*     */   {
/* 340 */     return this.codeType;
/*     */   }
/*     */ 
/*     */   public void setCodeType(int codeType)
/*     */   {
/* 347 */     this.codeType = codeType;
/*     */   }
/*     */ 
/*     */   public abstract Rectangle getBarcodeSize();
/*     */ 
/*     */   public abstract Rectangle placeBarcode(PdfContentByte paramPdfContentByte, BaseColor paramBaseColor1, BaseColor paramBaseColor2);
/*     */ 
/*     */   public PdfTemplate createTemplateWithBarcode(PdfContentByte cb, BaseColor barColor, BaseColor textColor)
/*     */   {
/* 403 */     PdfTemplate tp = cb.createTemplate(0.0F, 0.0F);
/* 404 */     Rectangle rect = placeBarcode(tp, barColor, textColor);
/* 405 */     tp.setBoundingBox(rect);
/* 406 */     return tp;
/*     */   }
/*     */ 
/*     */   public com.itextpdf.text.Image createImageWithBarcode(PdfContentByte cb, BaseColor barColor, BaseColor textColor)
/*     */   {
/*     */     try
/*     */     {
/* 419 */       return com.itextpdf.text.Image.getInstance(createTemplateWithBarcode(cb, barColor, textColor));
/*     */     }
/*     */     catch (Exception e) {
/* 422 */       throw new ExceptionConverter(e);
/*     */     }
/*     */   }
/*     */ 
/*     */   public float getInkSpreading()
/*     */   {
/* 431 */     return this.inkSpreading;
/*     */   }
/*     */ 
/*     */   public void setInkSpreading(float inkSpreading)
/*     */   {
/* 441 */     this.inkSpreading = inkSpreading;
/*     */   }
/*     */ 
/*     */   public String getAltText()
/*     */   {
/* 454 */     return this.altText;
/*     */   }
/*     */ 
/*     */   public void setAltText(String altText)
/*     */   {
/* 463 */     this.altText = altText;
/*     */   }
/*     */ 
/*     */   public abstract java.awt.Image createAwtImage(Color paramColor1, Color paramColor2);
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.pdf.Barcode
 * JD-Core Version:    0.6.2
 */