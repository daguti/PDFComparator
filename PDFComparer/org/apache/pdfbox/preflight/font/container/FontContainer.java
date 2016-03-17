/*     */ package org.apache.pdfbox.preflight.font.container;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import org.apache.pdfbox.pdmodel.font.PDFont;
/*     */ import org.apache.pdfbox.preflight.ValidationResult.ValidationError;
/*     */ import org.apache.pdfbox.preflight.font.util.GlyphDetail;
/*     */ import org.apache.pdfbox.preflight.font.util.GlyphException;
/*     */ 
/*     */ public abstract class FontContainer
/*     */ {
/*  41 */   protected List<ValidationResult.ValidationError> errorBuffer = new ArrayList();
/*     */ 
/*  45 */   protected boolean embeddedFont = true;
/*     */ 
/*  50 */   protected Map<Integer, GlyphDetail> computedCid = new HashMap();
/*     */ 
/*  52 */   protected boolean errorsAleadyMerged = false;
/*     */   protected PDFont font;
/*     */ 
/*     */   public FontContainer(PDFont font)
/*     */   {
/*  59 */     this.font = font;
/*     */   }
/*     */ 
/*     */   public void push(ValidationResult.ValidationError error)
/*     */   {
/*  64 */     this.errorBuffer.add(error);
/*     */   }
/*     */ 
/*     */   public void push(List<ValidationResult.ValidationError> errors)
/*     */   {
/*  69 */     this.errorBuffer.addAll(errors);
/*     */   }
/*     */ 
/*     */   public List<ValidationResult.ValidationError> getAllErrors()
/*     */   {
/*  74 */     return this.errorBuffer;
/*     */   }
/*     */ 
/*     */   public boolean isValid()
/*     */   {
/*  79 */     return (this.errorBuffer.isEmpty()) && (isEmbeddedFont());
/*     */   }
/*     */ 
/*     */   public boolean errorsAleadyMerged()
/*     */   {
/*  84 */     return this.errorsAleadyMerged;
/*     */   }
/*     */ 
/*     */   public void setErrorsAleadyMerged(boolean errorsAleadyMerged)
/*     */   {
/*  89 */     this.errorsAleadyMerged = errorsAleadyMerged;
/*     */   }
/*     */ 
/*     */   public boolean isEmbeddedFont()
/*     */   {
/*  94 */     return this.embeddedFont;
/*     */   }
/*     */ 
/*     */   public void notEmbedded()
/*     */   {
/*  99 */     this.embeddedFont = false;
/*     */   }
/*     */ 
/*     */   public void checkGlyphWith(int cid)
/*     */     throws GlyphException
/*     */   {
/* 109 */     if (isAlreadyComputedCid(cid))
/*     */     {
/* 111 */       return;
/*     */     }
/*     */ 
/* 114 */     float expectedWidth = this.font.getFontWidth(cid);
/* 115 */     float foundWidth = getFontProgramWidth(cid);
/* 116 */     checkWidthsConsistency(cid, expectedWidth, foundWidth);
/*     */   }
/*     */ 
/*     */   protected boolean isAlreadyComputedCid(int cid)
/*     */     throws GlyphException
/*     */   {
/* 130 */     boolean already = false;
/* 131 */     GlyphDetail gdetail = (GlyphDetail)this.computedCid.get(Integer.valueOf(cid));
/* 132 */     if (gdetail != null)
/*     */     {
/* 134 */       gdetail.throwExceptionIfNotValid();
/* 135 */       already = true;
/*     */     }
/* 137 */     return already;
/*     */   }
/*     */ 
/*     */   protected abstract float getFontProgramWidth(int paramInt);
/*     */ 
/*     */   protected void checkWidthsConsistency(int cid, float expectedWidth, float foundWidth)
/*     */     throws GlyphException
/*     */   {
/* 159 */     if (foundWidth < 0.0F)
/*     */     {
/* 161 */       GlyphException e = new GlyphException("3.3.1", cid, "The character \"" + cid + "\" in the font program \"" + this.font.getBaseFont() + "\"is missing from the Charater Encoding.");
/*     */ 
/* 164 */       markCIDAsInvalid(cid, e);
/* 165 */       throw e;
/*     */     }
/*     */ 
/* 169 */     if (Math.abs(foundWidth - expectedWidth) > 1.0F)
/*     */     {
/* 171 */       GlyphException e = new GlyphException("3.1.6", cid, "Width of the character \"" + cid + "\" in the font program \"" + this.font.getBaseFont() + "\"is inconsistent with the width in the PDF dictionary.");
/*     */ 
/* 174 */       markCIDAsInvalid(cid, e);
/* 175 */       throw e;
/*     */     }
/* 177 */     markCIDAsValid(cid);
/*     */   }
/*     */ 
/*     */   public final void markCIDAsValid(int cid)
/*     */   {
/* 182 */     this.computedCid.put(Integer.valueOf(cid), new GlyphDetail(cid));
/*     */   }
/*     */ 
/*     */   public final void markCIDAsInvalid(int cid, GlyphException gex)
/*     */   {
/* 187 */     this.computedCid.put(Integer.valueOf(cid), new GlyphDetail(cid, gex));
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.preflight.font.container.FontContainer
 * JD-Core Version:    0.6.2
 */