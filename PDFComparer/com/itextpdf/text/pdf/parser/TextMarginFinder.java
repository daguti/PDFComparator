/*     */ package com.itextpdf.text.pdf.parser;
/*     */ 
/*     */ import com.itextpdf.awt.geom.Rectangle2D.Float;
/*     */ 
/*     */ public class TextMarginFinder
/*     */   implements RenderListener
/*     */ {
/*  54 */   private Rectangle2D.Float textRectangle = null;
/*     */ 
/*     */   public void renderText(TextRenderInfo renderInfo)
/*     */   {
/*  63 */     if (this.textRectangle == null)
/*  64 */       this.textRectangle = renderInfo.getDescentLine().getBoundingRectange();
/*     */     else {
/*  66 */       this.textRectangle.add(renderInfo.getDescentLine().getBoundingRectange());
/*     */     }
/*  68 */     this.textRectangle.add(renderInfo.getAscentLine().getBoundingRectange());
/*     */   }
/*     */ 
/*     */   public float getLlx()
/*     */   {
/*  77 */     return this.textRectangle.x;
/*     */   }
/*     */ 
/*     */   public float getLly()
/*     */   {
/*  85 */     return this.textRectangle.y;
/*     */   }
/*     */ 
/*     */   public float getUrx()
/*     */   {
/*  93 */     return this.textRectangle.x + this.textRectangle.width;
/*     */   }
/*     */ 
/*     */   public float getUry()
/*     */   {
/* 101 */     return this.textRectangle.y + this.textRectangle.height;
/*     */   }
/*     */ 
/*     */   public float getWidth()
/*     */   {
/* 109 */     return this.textRectangle.width;
/*     */   }
/*     */ 
/*     */   public float getHeight()
/*     */   {
/* 117 */     return this.textRectangle.height;
/*     */   }
/*     */ 
/*     */   public void beginTextBlock()
/*     */   {
/*     */   }
/*     */ 
/*     */   public void endTextBlock()
/*     */   {
/*     */   }
/*     */ 
/*     */   public void renderImage(ImageRenderInfo renderInfo)
/*     */   {
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.pdf.parser.TextMarginFinder
 * JD-Core Version:    0.6.2
 */