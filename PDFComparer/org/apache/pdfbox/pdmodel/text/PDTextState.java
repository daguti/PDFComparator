/*     */ package org.apache.pdfbox.pdmodel.text;
/*     */ 
/*     */ import org.apache.pdfbox.pdmodel.font.PDFont;
/*     */ 
/*     */ public class PDTextState
/*     */   implements Cloneable
/*     */ {
/*     */   public static final int RENDERING_MODE_FILL_TEXT = 0;
/*     */   public static final int RENDERING_MODE_STROKE_TEXT = 1;
/*     */   public static final int RENDERING_MODE_FILL_THEN_STROKE_TEXT = 2;
/*     */   public static final int RENDERING_MODE_NEITHER_FILL_NOR_STROKE_TEXT = 3;
/*     */   public static final int RENDERING_MODE_FILL_TEXT_AND_ADD_TO_PATH_FOR_CLIPPING = 4;
/*     */   public static final int RENDERING_MODE_STROKE_TEXT_AND_ADD_TO_PATH_FOR_CLIPPING = 5;
/*     */   public static final int RENDERING_MODE_FILL_THEN_STROKE_TEXT_AND_ADD_TO_PATH_FOR_CLIPPING = 6;
/*     */   public static final int RENDERING_MODE_ADD_TEXT_TO_PATH_FOR_CLIPPING = 7;
/*  65 */   private float characterSpacing = 0.0F;
/*  66 */   private float wordSpacing = 0.0F;
/*  67 */   private float horizontalScaling = 100.0F;
/*  68 */   private float leading = 0.0F;
/*     */   private PDFont font;
/*     */   private float fontSize;
/*  71 */   private int renderingMode = 0;
/*  72 */   private float rise = 0.0F;
/*  73 */   private boolean knockout = true;
/*     */ 
/*     */   public float getCharacterSpacing()
/*     */   {
/*  82 */     return this.characterSpacing;
/*     */   }
/*     */ 
/*     */   public void setCharacterSpacing(float value)
/*     */   {
/*  92 */     this.characterSpacing = value;
/*     */   }
/*     */ 
/*     */   public float getWordSpacing()
/*     */   {
/* 102 */     return this.wordSpacing;
/*     */   }
/*     */ 
/*     */   public void setWordSpacing(float value)
/*     */   {
/* 112 */     this.wordSpacing = value;
/*     */   }
/*     */ 
/*     */   public float getHorizontalScalingPercent()
/*     */   {
/* 124 */     return this.horizontalScaling;
/*     */   }
/*     */ 
/*     */   public void setHorizontalScalingPercent(float value)
/*     */   {
/* 134 */     this.horizontalScaling = value;
/*     */   }
/*     */ 
/*     */   public float getLeading()
/*     */   {
/* 144 */     return this.leading;
/*     */   }
/*     */ 
/*     */   public void setLeading(float value)
/*     */   {
/* 154 */     this.leading = value;
/*     */   }
/*     */ 
/*     */   public PDFont getFont()
/*     */   {
/* 164 */     return this.font;
/*     */   }
/*     */ 
/*     */   public void setFont(PDFont value)
/*     */   {
/* 174 */     this.font = value;
/*     */   }
/*     */ 
/*     */   public float getFontSize()
/*     */   {
/* 184 */     return this.fontSize;
/*     */   }
/*     */ 
/*     */   public void setFontSize(float value)
/*     */   {
/* 194 */     this.fontSize = value;
/*     */   }
/*     */ 
/*     */   public int getRenderingMode()
/*     */   {
/* 204 */     return this.renderingMode;
/*     */   }
/*     */ 
/*     */   public void setRenderingMode(int value)
/*     */   {
/* 214 */     this.renderingMode = value;
/*     */   }
/*     */ 
/*     */   public float getRise()
/*     */   {
/* 224 */     return this.rise;
/*     */   }
/*     */ 
/*     */   public void setRise(float value)
/*     */   {
/* 234 */     this.rise = value;
/*     */   }
/*     */ 
/*     */   public boolean getKnockoutFlag()
/*     */   {
/* 244 */     return this.knockout;
/*     */   }
/*     */ 
/*     */   public void setKnockoutFlag(boolean value)
/*     */   {
/* 254 */     this.knockout = value;
/*     */   }
/*     */ 
/*     */   public Object clone()
/*     */   {
/*     */     try
/*     */     {
/* 264 */       return super.clone();
/*     */     }
/*     */     catch (CloneNotSupportedException ignore)
/*     */     {
/*     */     }
/*     */ 
/* 270 */     return null;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.pdmodel.text.PDTextState
 * JD-Core Version:    0.6.2
 */