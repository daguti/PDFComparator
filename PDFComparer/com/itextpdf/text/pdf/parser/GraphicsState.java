/*     */ package com.itextpdf.text.pdf.parser;
/*     */ 
/*     */ import com.itextpdf.text.BaseColor;
/*     */ import com.itextpdf.text.pdf.CMapAwareDocumentFont;
/*     */ import com.itextpdf.text.pdf.PdfName;
/*     */ 
/*     */ public class GraphicsState
/*     */ {
/*     */   Matrix ctm;
/*     */   float characterSpacing;
/*     */   float wordSpacing;
/*     */   float horizontalScaling;
/*     */   float leading;
/*     */   CMapAwareDocumentFont font;
/*     */   float fontSize;
/*     */   int renderMode;
/*     */   float rise;
/*     */   boolean knockout;
/*     */   PdfName colorSpaceFill;
/*     */   PdfName colorSpaceStroke;
/*     */   BaseColor fillColor;
/*     */   BaseColor strokeColor;
/*     */ 
/*     */   public GraphicsState()
/*     */   {
/*  89 */     this.ctm = new Matrix();
/*  90 */     this.characterSpacing = 0.0F;
/*  91 */     this.wordSpacing = 0.0F;
/*  92 */     this.horizontalScaling = 1.0F;
/*  93 */     this.leading = 0.0F;
/*  94 */     this.font = null;
/*  95 */     this.fontSize = 0.0F;
/*  96 */     this.renderMode = 0;
/*  97 */     this.rise = 0.0F;
/*  98 */     this.knockout = true;
/*  99 */     this.colorSpaceFill = null;
/* 100 */     this.colorSpaceStroke = null;
/* 101 */     this.fillColor = null;
/* 102 */     this.strokeColor = null;
/*     */   }
/*     */ 
/*     */   public GraphicsState(GraphicsState source)
/*     */   {
/* 112 */     this.ctm = source.ctm;
/* 113 */     this.characterSpacing = source.characterSpacing;
/* 114 */     this.wordSpacing = source.wordSpacing;
/* 115 */     this.horizontalScaling = source.horizontalScaling;
/* 116 */     this.leading = source.leading;
/* 117 */     this.font = source.font;
/* 118 */     this.fontSize = source.fontSize;
/* 119 */     this.renderMode = source.renderMode;
/* 120 */     this.rise = source.rise;
/* 121 */     this.knockout = source.knockout;
/* 122 */     this.colorSpaceFill = source.colorSpaceFill;
/* 123 */     this.colorSpaceStroke = source.colorSpaceStroke;
/* 124 */     this.fillColor = source.fillColor;
/* 125 */     this.strokeColor = source.strokeColor;
/*     */   }
/*     */ 
/*     */   public Matrix getCtm()
/*     */   {
/* 134 */     return this.ctm;
/*     */   }
/*     */ 
/*     */   public float getCharacterSpacing()
/*     */   {
/* 143 */     return this.characterSpacing;
/*     */   }
/*     */ 
/*     */   public float getWordSpacing()
/*     */   {
/* 152 */     return this.wordSpacing;
/*     */   }
/*     */ 
/*     */   public float getHorizontalScaling()
/*     */   {
/* 161 */     return this.horizontalScaling;
/*     */   }
/*     */ 
/*     */   public float getLeading()
/*     */   {
/* 170 */     return this.leading;
/*     */   }
/*     */ 
/*     */   public CMapAwareDocumentFont getFont()
/*     */   {
/* 179 */     return this.font;
/*     */   }
/*     */ 
/*     */   public float getFontSize()
/*     */   {
/* 188 */     return this.fontSize;
/*     */   }
/*     */ 
/*     */   public int getRenderMode()
/*     */   {
/* 197 */     return this.renderMode;
/*     */   }
/*     */ 
/*     */   public float getRise()
/*     */   {
/* 206 */     return this.rise;
/*     */   }
/*     */ 
/*     */   public boolean isKnockout()
/*     */   {
/* 215 */     return this.knockout;
/*     */   }
/*     */ 
/*     */   public PdfName getColorSpaceFill()
/*     */   {
/* 222 */     return this.colorSpaceFill;
/*     */   }
/*     */ 
/*     */   public PdfName getColorSpaceStroke()
/*     */   {
/* 229 */     return this.colorSpaceStroke;
/*     */   }
/*     */ 
/*     */   public BaseColor getFillColor()
/*     */   {
/* 237 */     return this.fillColor;
/*     */   }
/*     */ 
/*     */   public BaseColor getStrokeColor()
/*     */   {
/* 245 */     return this.strokeColor;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.pdf.parser.GraphicsState
 * JD-Core Version:    0.6.2
 */