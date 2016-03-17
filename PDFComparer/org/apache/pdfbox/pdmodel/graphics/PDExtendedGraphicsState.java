/*     */ package org.apache.pdfbox.pdmodel.graphics;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import org.apache.pdfbox.cos.COSArray;
/*     */ import org.apache.pdfbox.cos.COSBase;
/*     */ import org.apache.pdfbox.cos.COSDictionary;
/*     */ import org.apache.pdfbox.cos.COSFloat;
/*     */ import org.apache.pdfbox.cos.COSName;
/*     */ import org.apache.pdfbox.cos.COSNumber;
/*     */ import org.apache.pdfbox.pdmodel.common.COSObjectable;
/*     */ import org.apache.pdfbox.pdmodel.text.PDTextState;
/*     */ 
/*     */ public class PDExtendedGraphicsState
/*     */   implements COSObjectable
/*     */ {
/*     */   public static final String RENDERING_INTENT_ABSOLUTE_COLORIMETRIC = "AbsoluteColorimetric";
/*     */   public static final String RENDERING_INTENT_RELATIVE_COLORIMETRIC = "RelativeColorimetric";
/*     */   public static final String RENDERING_INTENT_SATURATION = "Saturation";
/*     */   public static final String RENDERING_INTENT_PERCEPTUAL = "Perceptual";
/*     */   private COSDictionary graphicsState;
/*     */ 
/*     */   public PDExtendedGraphicsState()
/*     */   {
/*  65 */     this.graphicsState = new COSDictionary();
/*  66 */     this.graphicsState.setItem(COSName.TYPE, COSName.EXT_G_STATE);
/*     */   }
/*     */ 
/*     */   public PDExtendedGraphicsState(COSDictionary dictionary)
/*     */   {
/*  76 */     this.graphicsState = dictionary;
/*     */   }
/*     */ 
/*     */   public void copyIntoGraphicsState(PDGraphicsState gs)
/*     */     throws IOException
/*     */   {
/*  88 */     for (COSName key : this.graphicsState.keySet())
/*     */     {
/*  90 */       if (key.equals(COSName.LW))
/*     */       {
/*  92 */         gs.setLineWidth(getLineWidth().doubleValue());
/*     */       }
/*  94 */       else if (key.equals(COSName.LC))
/*     */       {
/*  96 */         gs.setLineCap(getLineCapStyle());
/*     */       }
/*  98 */       else if (key.equals(COSName.LJ))
/*     */       {
/* 100 */         gs.setLineJoin(getLineJoinStyle());
/*     */       }
/* 102 */       else if (key.equals(COSName.ML))
/*     */       {
/* 104 */         gs.setMiterLimit(getMiterLimit().doubleValue());
/*     */       }
/* 106 */       else if (key.equals(COSName.D))
/*     */       {
/* 108 */         gs.setLineDashPattern(getLineDashPattern());
/*     */       }
/* 110 */       else if (key.equals(COSName.RI))
/*     */       {
/* 112 */         gs.setRenderingIntent(getRenderingIntent());
/*     */       }
/* 114 */       else if (key.equals(COSName.OPM))
/*     */       {
/* 116 */         gs.setOverprintMode(getOverprintMode().doubleValue());
/*     */       }
/* 118 */       else if (key.equals(COSName.FONT))
/*     */       {
/* 120 */         PDFontSetting setting = getFontSetting();
/* 121 */         gs.getTextState().setFont(setting.getFont());
/* 122 */         gs.getTextState().setFontSize(setting.getFontSize());
/*     */       }
/* 124 */       else if (key.equals(COSName.FL))
/*     */       {
/* 126 */         gs.setFlatness(getFlatnessTolerance().floatValue());
/*     */       }
/* 128 */       else if (key.equals(COSName.SM))
/*     */       {
/* 130 */         gs.setSmoothness(getSmoothnessTolerance().floatValue());
/*     */       }
/* 132 */       else if (key.equals(COSName.SA))
/*     */       {
/* 134 */         gs.setStrokeAdjustment(getAutomaticStrokeAdjustment());
/*     */       }
/* 136 */       else if (key.equals(COSName.CA))
/*     */       {
/* 138 */         gs.setAlphaConstants(getStrokingAlphaConstant().floatValue());
/*     */       }
/* 140 */       else if (key.equals(COSName.CA_NS))
/*     */       {
/* 142 */         gs.setNonStrokeAlphaConstants(getNonStrokingAlphaConstant().floatValue());
/*     */       }
/* 144 */       else if (key.equals(COSName.AIS))
/*     */       {
/* 146 */         gs.setAlphaSource(getAlphaSourceFlag());
/*     */       }
/* 148 */       else if (key.equals(COSName.TK))
/*     */       {
/* 150 */         gs.getTextState().setKnockoutFlag(getTextKnockoutFlag());
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public COSDictionary getCOSDictionary()
/*     */   {
/* 162 */     return this.graphicsState;
/*     */   }
/*     */ 
/*     */   public COSBase getCOSObject()
/*     */   {
/* 172 */     return this.graphicsState;
/*     */   }
/*     */ 
/*     */   public Float getLineWidth()
/*     */   {
/* 182 */     return getFloatItem(COSName.LW);
/*     */   }
/*     */ 
/*     */   public void setLineWidth(Float width)
/*     */   {
/* 192 */     setFloatItem(COSName.LW, width);
/*     */   }
/*     */ 
/*     */   public int getLineCapStyle()
/*     */   {
/* 202 */     return this.graphicsState.getInt(COSName.LC);
/*     */   }
/*     */ 
/*     */   public void setLineCapStyle(int style)
/*     */   {
/* 212 */     this.graphicsState.setInt(COSName.LC, style);
/*     */   }
/*     */ 
/*     */   public int getLineJoinStyle()
/*     */   {
/* 222 */     return this.graphicsState.getInt(COSName.LJ);
/*     */   }
/*     */ 
/*     */   public void setLineJoinStyle(int style)
/*     */   {
/* 232 */     this.graphicsState.setInt(COSName.LJ, style);
/*     */   }
/*     */ 
/*     */   public Float getMiterLimit()
/*     */   {
/* 243 */     return getFloatItem(COSName.ML);
/*     */   }
/*     */ 
/*     */   public void setMiterLimit(Float miterLimit)
/*     */   {
/* 253 */     setFloatItem(COSName.ML, miterLimit);
/*     */   }
/*     */ 
/*     */   public PDLineDashPattern getLineDashPattern()
/*     */   {
/* 263 */     PDLineDashPattern retval = null;
/* 264 */     COSArray dp = (COSArray)this.graphicsState.getDictionaryObject(COSName.D);
/* 265 */     if (dp != null)
/*     */     {
/* 267 */       retval = new PDLineDashPattern(dp);
/*     */     }
/* 269 */     return retval;
/*     */   }
/*     */ 
/*     */   public void setLineDashPattern(PDLineDashPattern dashPattern)
/*     */   {
/* 279 */     this.graphicsState.setItem(COSName.D, dashPattern.getCOSObject());
/*     */   }
/*     */ 
/*     */   public String getRenderingIntent()
/*     */   {
/* 289 */     return this.graphicsState.getNameAsString("RI");
/*     */   }
/*     */ 
/*     */   public void setRenderingIntent(String ri)
/*     */   {
/* 299 */     this.graphicsState.setName("RI", ri);
/*     */   }
/*     */ 
/*     */   public boolean getStrokingOverprintControl()
/*     */   {
/* 309 */     return this.graphicsState.getBoolean(COSName.OP, false);
/*     */   }
/*     */ 
/*     */   public void setStrokingOverprintControl(boolean op)
/*     */   {
/* 319 */     this.graphicsState.setBoolean(COSName.OP, op);
/*     */   }
/*     */ 
/*     */   public boolean getNonStrokingOverprintControl()
/*     */   {
/* 330 */     return this.graphicsState.getBoolean(COSName.OP_NS, getStrokingOverprintControl());
/*     */   }
/*     */ 
/*     */   public void setNonStrokingOverprintControl(boolean op)
/*     */   {
/* 340 */     this.graphicsState.setBoolean(COSName.OP_NS, op);
/*     */   }
/*     */ 
/*     */   public Float getOverprintMode()
/*     */   {
/* 350 */     return getFloatItem(COSName.OPM);
/*     */   }
/*     */ 
/*     */   public void setOverprintMode(Float overprintMode)
/*     */   {
/* 360 */     setFloatItem(COSName.OPM, overprintMode);
/*     */   }
/*     */ 
/*     */   public PDFontSetting getFontSetting()
/*     */   {
/* 370 */     PDFontSetting setting = null;
/* 371 */     COSArray font = (COSArray)this.graphicsState.getDictionaryObject(COSName.FONT);
/* 372 */     if (font != null)
/*     */     {
/* 374 */       setting = new PDFontSetting(font);
/*     */     }
/* 376 */     return setting;
/*     */   }
/*     */ 
/*     */   public void setFontSetting(PDFontSetting fs)
/*     */   {
/* 386 */     this.graphicsState.setItem(COSName.FONT, fs);
/*     */   }
/*     */ 
/*     */   public Float getFlatnessTolerance()
/*     */   {
/* 396 */     return getFloatItem(COSName.FL);
/*     */   }
/*     */ 
/*     */   public void setFlatnessTolerance(Float flatness)
/*     */   {
/* 406 */     setFloatItem(COSName.FL, flatness);
/*     */   }
/*     */ 
/*     */   public Float getSmoothnessTolerance()
/*     */   {
/* 416 */     return getFloatItem(COSName.SM);
/*     */   }
/*     */ 
/*     */   public void setSmoothnessTolerance(Float smoothness)
/*     */   {
/* 426 */     setFloatItem(COSName.SM, smoothness);
/*     */   }
/*     */ 
/*     */   public boolean getAutomaticStrokeAdjustment()
/*     */   {
/* 436 */     return this.graphicsState.getBoolean(COSName.SA, false);
/*     */   }
/*     */ 
/*     */   public void setAutomaticStrokeAdjustment(boolean sa)
/*     */   {
/* 446 */     this.graphicsState.setBoolean(COSName.SA, sa);
/*     */   }
/*     */ 
/*     */   public Float getStrokingAlphaConstant()
/*     */   {
/* 456 */     return getFloatItem(COSName.CA);
/*     */   }
/*     */ 
/*     */   public void setStrokingAlphaConstant(Float alpha)
/*     */   {
/* 466 */     setFloatItem(COSName.CA, alpha);
/*     */   }
/*     */ 
/*     */   public Float getNonStrokingAlphaConstant()
/*     */   {
/* 476 */     return getFloatItem(COSName.CA_NS);
/*     */   }
/*     */ 
/*     */   public void setNonStrokingAlphaConstant(Float alpha)
/*     */   {
/* 486 */     setFloatItem(COSName.CA_NS, alpha);
/*     */   }
/*     */ 
/*     */   public boolean getAlphaSourceFlag()
/*     */   {
/* 496 */     return this.graphicsState.getBoolean(COSName.AIS, false);
/*     */   }
/*     */ 
/*     */   public void setAlphaSourceFlag(boolean alpha)
/*     */   {
/* 506 */     this.graphicsState.setBoolean(COSName.AIS, alpha);
/*     */   }
/*     */ 
/*     */   public boolean getTextKnockoutFlag()
/*     */   {
/* 516 */     return this.graphicsState.getBoolean(COSName.TK, true);
/*     */   }
/*     */ 
/*     */   public void setTextKnockoutFlag(boolean tk)
/*     */   {
/* 526 */     this.graphicsState.setBoolean(COSName.TK, tk);
/*     */   }
/*     */ 
/*     */   private Float getFloatItem(COSName key)
/*     */   {
/* 538 */     Float retval = null;
/* 539 */     COSNumber value = (COSNumber)this.graphicsState.getDictionaryObject(key);
/* 540 */     if (value != null)
/*     */     {
/* 542 */       retval = new Float(value.floatValue());
/*     */     }
/* 544 */     return retval;
/*     */   }
/*     */ 
/*     */   private void setFloatItem(COSName key, Float value)
/*     */   {
/* 555 */     if (value == null)
/*     */     {
/* 557 */       this.graphicsState.removeItem(key);
/*     */     }
/*     */     else
/*     */     {
/* 561 */       this.graphicsState.setItem(key, new COSFloat(value.floatValue()));
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.pdmodel.graphics.PDExtendedGraphicsState
 * JD-Core Version:    0.6.2
 */