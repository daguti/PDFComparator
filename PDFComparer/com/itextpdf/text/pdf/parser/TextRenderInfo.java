/*     */ package com.itextpdf.text.pdf.parser;
/*     */ 
/*     */ import com.itextpdf.text.BaseColor;
/*     */ import com.itextpdf.text.pdf.CMapAwareDocumentFont;
/*     */ import com.itextpdf.text.pdf.DocumentFont;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.List;
/*     */ 
/*     */ public class TextRenderInfo
/*     */ {
/*     */   private final String text;
/*     */   private final Matrix textToUserSpaceTransformMatrix;
/*     */   private final GraphicsState gs;
/*     */   private final Collection<MarkedContentInfo> markedContentInfos;
/*     */ 
/*     */   TextRenderInfo(String text, GraphicsState gs, Matrix textMatrix, Collection<MarkedContentInfo> markedContentInfo)
/*     */   {
/*  82 */     this.text = text;
/*  83 */     this.textToUserSpaceTransformMatrix = textMatrix.multiply(gs.ctm);
/*  84 */     this.gs = gs;
/*  85 */     this.markedContentInfos = new ArrayList(markedContentInfo);
/*     */   }
/*     */ 
/*     */   private TextRenderInfo(TextRenderInfo parent, int charIndex, float horizontalOffset)
/*     */   {
/*  96 */     this.text = parent.text.substring(charIndex, charIndex + 1);
/*  97 */     this.textToUserSpaceTransformMatrix = new Matrix(horizontalOffset, 0.0F).multiply(parent.textToUserSpaceTransformMatrix);
/*  98 */     this.gs = parent.gs;
/*  99 */     this.markedContentInfos = parent.markedContentInfos;
/*     */   }
/*     */ 
/*     */   public String getText()
/*     */   {
/* 106 */     return this.text;
/*     */   }
/*     */ 
/*     */   public boolean hasMcid(int mcid)
/*     */   {
/* 117 */     return hasMcid(mcid, false);
/*     */   }
/*     */ 
/*     */   public boolean hasMcid(int mcid, boolean checkTheTopmostLevelOnly)
/*     */   {
/* 129 */     if (checkTheTopmostLevelOnly) {
/* 130 */       if ((this.markedContentInfos instanceof ArrayList)) {
/* 131 */         Integer infoMcid = getMcid();
/* 132 */         return infoMcid.intValue() == mcid;
/*     */       }
/*     */     }
/* 135 */     else for (MarkedContentInfo info : this.markedContentInfos) {
/* 136 */         if ((info.hasMcid()) && 
/* 137 */           (info.getMcid() == mcid)) {
/* 138 */           return true;
/*     */         }
/*     */       }
/* 141 */     return false;
/*     */   }
/*     */ 
/*     */   public Integer getMcid()
/*     */   {
/* 148 */     if ((this.markedContentInfos instanceof ArrayList)) {
/* 149 */       ArrayList mci = (ArrayList)this.markedContentInfos;
/* 150 */       MarkedContentInfo info = mci.size() > 0 ? (MarkedContentInfo)mci.get(mci.size() - 1) : null;
/* 151 */       return (info != null) && (info.hasMcid()) ? Integer.valueOf(info.getMcid()) : null;
/*     */     }
/* 153 */     return null;
/*     */   }
/*     */ 
/*     */   float getUnscaledWidth()
/*     */   {
/* 160 */     return getStringWidth(this.text);
/*     */   }
/*     */ 
/*     */   public LineSegment getBaseline()
/*     */   {
/* 170 */     return getUnscaledBaselineWithOffset(0.0F + this.gs.rise).transformBy(this.textToUserSpaceTransformMatrix);
/*     */   }
/*     */ 
/*     */   public LineSegment getAscentLine()
/*     */   {
/* 180 */     float ascent = this.gs.getFont().getFontDescriptor(1, this.gs.getFontSize());
/* 181 */     return getUnscaledBaselineWithOffset(ascent + this.gs.rise).transformBy(this.textToUserSpaceTransformMatrix);
/*     */   }
/*     */ 
/*     */   public LineSegment getDescentLine()
/*     */   {
/* 192 */     float descent = this.gs.getFont().getFontDescriptor(3, this.gs.getFontSize());
/* 193 */     return getUnscaledBaselineWithOffset(descent + this.gs.rise).transformBy(this.textToUserSpaceTransformMatrix);
/*     */   }
/*     */ 
/*     */   private LineSegment getUnscaledBaselineWithOffset(float yOffset)
/*     */   {
/* 199 */     float correctedUnscaledWidth = getUnscaledWidth() - this.gs.characterSpacing * this.gs.horizontalScaling;
/*     */ 
/* 201 */     return new LineSegment(new Vector(0.0F, yOffset, 1.0F), new Vector(correctedUnscaledWidth, yOffset, 1.0F));
/*     */   }
/*     */ 
/*     */   public DocumentFont getFont()
/*     */   {
/* 210 */     return this.gs.getFont();
/*     */   }
/*     */ 
/*     */   public float getRise()
/*     */   {
/* 237 */     if (this.gs.rise == 0.0F) return 0.0F;
/*     */ 
/* 239 */     return convertHeightFromTextSpaceToUserSpace(this.gs.rise);
/*     */   }
/*     */ 
/*     */   private float convertWidthFromTextSpaceToUserSpace(float width)
/*     */   {
/* 249 */     LineSegment textSpace = new LineSegment(new Vector(0.0F, 0.0F, 1.0F), new Vector(width, 0.0F, 1.0F));
/* 250 */     LineSegment userSpace = textSpace.transformBy(this.textToUserSpaceTransformMatrix);
/* 251 */     return userSpace.getLength();
/*     */   }
/*     */ 
/*     */   private float convertHeightFromTextSpaceToUserSpace(float height)
/*     */   {
/* 261 */     LineSegment textSpace = new LineSegment(new Vector(0.0F, 0.0F, 1.0F), new Vector(0.0F, height, 1.0F));
/* 262 */     LineSegment userSpace = textSpace.transformBy(this.textToUserSpaceTransformMatrix);
/* 263 */     return userSpace.getLength();
/*     */   }
/*     */ 
/*     */   public float getSingleSpaceWidth()
/*     */   {
/* 271 */     return convertWidthFromTextSpaceToUserSpace(getUnscaledFontSpaceWidth());
/*     */   }
/*     */ 
/*     */   public int getTextRenderMode()
/*     */   {
/* 290 */     return this.gs.renderMode;
/*     */   }
/*     */ 
/*     */   public BaseColor getFillColor()
/*     */   {
/* 298 */     return this.gs.fillColor;
/*     */   }
/*     */ 
/*     */   public BaseColor getStrokeColor()
/*     */   {
/* 307 */     return this.gs.strokeColor;
/*     */   }
/*     */ 
/*     */   private float getUnscaledFontSpaceWidth()
/*     */   {
/* 317 */     char charToUse = ' ';
/* 318 */     if (this.gs.font.getWidth(charToUse) == 0)
/* 319 */       charToUse = ' ';
/* 320 */     return getStringWidth(String.valueOf(charToUse));
/*     */   }
/*     */ 
/*     */   private float getStringWidth(String string)
/*     */   {
/* 329 */     DocumentFont font = this.gs.font;
/* 330 */     char[] chars = string.toCharArray();
/* 331 */     float totalWidth = 0.0F;
/* 332 */     for (int i = 0; i < chars.length; i++) {
/* 333 */       float w = font.getWidth(chars[i]) / 1000.0F;
/* 334 */       float wordSpacing = chars[i] == ' ' ? this.gs.wordSpacing : 0.0F;
/* 335 */       totalWidth += (w * this.gs.fontSize + this.gs.characterSpacing + wordSpacing) * this.gs.horizontalScaling;
/*     */     }
/*     */ 
/* 338 */     return totalWidth;
/*     */   }
/*     */ 
/*     */   public List<TextRenderInfo> getCharacterRenderInfos()
/*     */   {
/* 347 */     List rslt = new ArrayList(this.text.length());
/*     */ 
/* 349 */     DocumentFont font = this.gs.font;
/* 350 */     char[] chars = this.text.toCharArray();
/* 351 */     float totalWidth = 0.0F;
/* 352 */     for (int i = 0; i < chars.length; i++) {
/* 353 */       float w = font.getWidth(chars[i]) / 1000.0F;
/* 354 */       float wordSpacing = chars[i] == ' ' ? this.gs.wordSpacing : 0.0F;
/*     */ 
/* 356 */       TextRenderInfo subInfo = new TextRenderInfo(this, i, totalWidth);
/* 357 */       rslt.add(subInfo);
/*     */ 
/* 359 */       totalWidth += (w * this.gs.fontSize + this.gs.characterSpacing + wordSpacing) * this.gs.horizontalScaling;
/*     */     }
/*     */ 
/* 363 */     return rslt;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.pdf.parser.TextRenderInfo
 * JD-Core Version:    0.6.2
 */