/*     */ package org.apache.pdfbox.preflight.font.container;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.util.List;
/*     */ import org.apache.fontbox.cff.CFFFont;
/*     */ import org.apache.fontbox.cff.CFFFont.Mapping;
/*     */ import org.apache.fontbox.cff.encoding.CFFEncoding;
/*     */ import org.apache.pdfbox.encoding.Encoding;
/*     */ import org.apache.pdfbox.pdmodel.font.PDFont;
/*     */ import org.apache.pdfbox.preflight.font.util.GlyphException;
/*     */ import org.apache.pdfbox.preflight.font.util.Type1;
/*     */ 
/*     */ public class Type1Container extends FontContainer
/*     */ {
/*  39 */   private float defaultGlyphWidth = 0.0F;
/*     */ 
/*  44 */   protected boolean isFontFile1 = true;
/*     */   protected Type1 type1Font;
/*     */   protected List<CFFFont> lCFonts;
/*     */ 
/*     */   public Type1Container(PDFont font)
/*     */   {
/*  51 */     super(font);
/*     */   }
/*     */ 
/*     */   protected float getFontProgramWidth(int cid)
/*     */   {
/*  57 */     float widthResult = -1.0F;
/*     */     try
/*     */     {
/*  60 */       if (this.isFontFile1)
/*     */       {
/*  62 */         if (this.type1Font != null)
/*     */         {
/*  64 */           widthResult = this.type1Font.getWidthOfCID(cid);
/*     */         }
/*     */ 
/*     */       }
/*     */       else
/*     */       {
/*  73 */         name = this.font.getFontEncoding().getName(cid);
/*  74 */         for (CFFFont cff : this.lCFonts)
/*     */         {
/*  76 */           int SID = cff.getEncoding().getSID(cid);
/*  77 */           for (CFFFont.Mapping m : cff.getMappings())
/*     */           {
/*  79 */             if (m.getName().equals(name))
/*     */             {
/*  81 */               SID = m.getSID();
/*  82 */               break;
/*     */             }
/*     */           }
/*  85 */           widthResult = cff.getWidth(SID);
/*  86 */           if (widthResult != this.defaultGlyphWidth)
/*     */             break;
/*     */         }
/*     */       }
/*     */     }
/*     */     catch (GlyphException e)
/*     */     {
/*     */       String name;
/*  95 */       widthResult = -1.0F;
/*     */     }
/*     */     catch (IOException e)
/*     */     {
/*  99 */       widthResult = -1.0F;
/*     */     }
/*     */ 
/* 102 */     return widthResult;
/*     */   }
/*     */ 
/*     */   public void setType1Font(Type1 type1Font)
/*     */   {
/* 107 */     this.type1Font = type1Font;
/*     */   }
/*     */ 
/*     */   public void setFontFile1(boolean isFontFile1)
/*     */   {
/* 112 */     this.isFontFile1 = isFontFile1;
/*     */   }
/*     */ 
/*     */   public void setCFFFontObjects(List<CFFFont> lCFonts)
/*     */   {
/* 117 */     this.lCFonts = lCFonts;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.preflight.font.container.Type1Container
 * JD-Core Version:    0.6.2
 */