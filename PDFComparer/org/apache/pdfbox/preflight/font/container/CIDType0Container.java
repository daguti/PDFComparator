/*     */ package org.apache.pdfbox.preflight.font.container;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.List;
/*     */ import org.apache.fontbox.cff.CFFFont;
/*     */ import org.apache.fontbox.cff.CFFFont.Mapping;
/*     */ import org.apache.pdfbox.pdmodel.font.PDFont;
/*     */ import org.apache.pdfbox.pdmodel.font.PDFontDescriptor;
/*     */ 
/*     */ public class CIDType0Container extends FontContainer
/*     */ {
/*  35 */   protected List<CFFFont> lCFonts = new ArrayList();
/*     */ 
/*     */   public CIDType0Container(PDFont font)
/*     */   {
/*  39 */     super(font);
/*     */   }
/*     */ 
/*     */   protected float getFontProgramWidth(int cid)
/*     */   {
/*  46 */     boolean cidFound = false;
/*  47 */     for (CFFFont font : this.lCFonts)
/*     */     {
/*  49 */       Collection cMapping = font.getMappings();
/*  50 */       for (CFFFont.Mapping mapping : cMapping)
/*     */       {
/*  56 */         if (mapping.getSID() == cid)
/*     */         {
/*  58 */           cidFound = true;
/*  59 */           break;
/*     */         }
/*     */       }
/*  62 */       if (cidFound)
/*     */       {
/*     */         break;
/*     */       }
/*     */     }
/*     */ 
/*  68 */     float widthInFontProgram = 0.0F;
/*  69 */     if ((cidFound) || (cid == 0))
/*     */     {
/*  72 */       float defaultGlyphWidth = 0.0F;
/*  73 */       if (this.font.getFontDescriptor() != null)
/*     */       {
/*  75 */         defaultGlyphWidth = this.font.getFontDescriptor().getMissingWidth();
/*     */       }
/*     */ 
/*     */       try
/*     */       {
/*  81 */         for (CFFFont cff : this.lCFonts)
/*     */         {
/*  83 */           widthInFontProgram = cff.getWidth(cid);
/*  84 */           if (widthInFontProgram != defaultGlyphWidth)
/*     */           {
/*     */             break;
/*     */           }
/*     */         }
/*     */       }
/*     */       catch (IOException e)
/*     */       {
/*  92 */         widthInFontProgram = -1.0F;
/*     */       }
/*     */ 
/*     */     }
/*     */     else
/*     */     {
/* 101 */       widthInFontProgram = -1.0F;
/*     */     }
/* 103 */     return widthInFontProgram;
/*     */   }
/*     */ 
/*     */   public void setlCFonts(List<CFFFont> lCFonts)
/*     */   {
/* 108 */     this.lCFonts = lCFonts;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.preflight.font.container.CIDType0Container
 * JD-Core Version:    0.6.2
 */