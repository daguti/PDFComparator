/*     */ package org.apache.pdfbox.preflight.font.container;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import org.apache.fontbox.ttf.CMAPEncodingEntry;
/*     */ import org.apache.fontbox.ttf.CMAPTable;
/*     */ import org.apache.fontbox.ttf.HeaderTable;
/*     */ import org.apache.fontbox.ttf.HorizontalHeaderTable;
/*     */ import org.apache.fontbox.ttf.HorizontalMetricsTable;
/*     */ import org.apache.fontbox.ttf.TrueTypeFont;
/*     */ import org.apache.pdfbox.encoding.Encoding;
/*     */ import org.apache.pdfbox.pdmodel.font.PDFont;
/*     */ import org.apache.pdfbox.pdmodel.font.PDFontDescriptor;
/*     */ 
/*     */ public class TrueTypeContainer extends FontContainer
/*     */ {
/*     */   protected TrueTypeFont ttFont;
/*  39 */   private CMAPEncodingEntry[] cmapEncodingEntries = null;
/*     */ 
/*     */   public TrueTypeContainer(PDFont font)
/*     */   {
/*  43 */     super(font);
/*     */   }
/*     */ 
/*     */   public void setTrueTypeFont(TrueTypeFont ttFont)
/*     */   {
/*  48 */     this.ttFont = ttFont;
/*  49 */     initCMapEncodingEntries();
/*     */   }
/*     */ 
/*     */   protected void initCMapEncodingEntries()
/*     */   {
/*  65 */     if (this.cmapEncodingEntries != null) {
/*  66 */       return;
/*     */     }
/*  68 */     CMAPTable cmap = this.ttFont.getCMAP();
/*  69 */     if (this.font.getFontDescriptor().isSymbolic())
/*     */     {
/*  71 */       this.cmapEncodingEntries = cmap.getCmaps();
/*     */     }
/*     */     else
/*     */     {
/*  75 */       this.cmapEncodingEntries = orderCMapEntries(cmap);
/*     */     }
/*     */   }
/*     */ 
/*     */   private CMAPEncodingEntry[] orderCMapEntries(CMAPTable cmap)
/*     */   {
/*  81 */     List res = new ArrayList();
/*  82 */     boolean firstIs31 = false;
/*  83 */     for (CMAPEncodingEntry cmapEntry : cmap.getCmaps())
/*     */     {
/*  86 */       if ((cmapEntry.getPlatformId() == 3) && (cmapEntry.getPlatformEncodingId() == 1))
/*     */       {
/*  88 */         res.add(0, cmapEntry);
/*  89 */         firstIs31 = true;
/*     */       }
/*  91 */       else if ((cmapEntry.getPlatformId() == 1) && (cmapEntry.getPlatformEncodingId() == 0))
/*     */       {
/*  94 */         if (firstIs31)
/*     */         {
/*  97 */           res.add(1, cmapEntry);
/*     */         }
/*     */         else
/*     */         {
/* 102 */           res.add(0, cmapEntry);
/*     */         }
/*     */       }
/*     */       else
/*     */       {
/* 107 */         res.add(cmapEntry);
/*     */       }
/*     */     }
/* 110 */     return (CMAPEncodingEntry[])res.toArray(new CMAPEncodingEntry[res.size()]);
/*     */   }
/*     */ 
/*     */   protected float getFontProgramWidth(int cid)
/*     */   {
/* 116 */     float result = -1.0F;
/* 117 */     if (this.cmapEncodingEntries != null)
/*     */     {
/* 119 */       for (CMAPEncodingEntry entry : this.cmapEncodingEntries)
/*     */       {
/* 121 */         int glyphID = extractGlyphID(cid, entry);
/* 122 */         if (glyphID > 0)
/*     */         {
/* 124 */           result = extractGlyphWidth(glyphID);
/* 125 */           break;
/*     */         }
/*     */       }
/*     */     }
/* 129 */     return result;
/*     */   }
/*     */ 
/*     */   private int extractGlyphID(int cid, CMAPEncodingEntry cmap)
/*     */   {
/* 142 */     int notFoundGlyphID = 0;
/*     */ 
/* 144 */     int innerFontCid = cid;
/* 145 */     if ((cmap.getPlatformEncodingId() == 1) && (cmap.getPlatformId() == 3))
/*     */     {
/*     */       try
/*     */       {
/* 149 */         Encoding fontEncoding = this.font.getFontEncoding();
/* 150 */         String character = fontEncoding.getCharacter(cid);
/* 151 */         if (character == null)
/*     */         {
/* 153 */           return notFoundGlyphID;
/*     */         }
/*     */ 
/* 156 */         char[] characterArray = character.toCharArray();
/* 157 */         if (characterArray.length == 1)
/*     */         {
/* 159 */           innerFontCid = characterArray[0];
/*     */         }
/*     */         else
/*     */         {
/* 164 */           innerFontCid = characterArray[0];
/* 165 */           for (int i = 1; i < characterArray.length; i++)
/*     */           {
/* 167 */             if (cmap.getGlyphId(characterArray[i]) == 0)
/*     */             {
/* 169 */               return notFoundGlyphID;
/*     */             }
/*     */           }
/*     */         }
/*     */ 
/*     */       }
/*     */       catch (IOException ioe)
/*     */       {
/* 177 */         return notFoundGlyphID;
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 182 */     return cmap.getGlyphId(innerFontCid);
/*     */   }
/*     */ 
/*     */   private float extractGlyphWidth(int glyphID)
/*     */   {
/* 187 */     int unitsPerEm = this.ttFont.getHeader().getUnitsPerEm();
/* 188 */     int[] glyphWidths = this.ttFont.getHorizontalMetrics().getAdvanceWidth();
/*     */ 
/* 194 */     int numberOfLongHorMetrics = this.ttFont.getHorizontalHeader().getNumberOfHMetrics();
/* 195 */     float glypdWidth = glyphWidths[(numberOfLongHorMetrics - 1)];
/* 196 */     if (glyphID < numberOfLongHorMetrics)
/*     */     {
/* 198 */       glypdWidth = glyphWidths[glyphID];
/*     */     }
/* 200 */     return glypdWidth * 1000.0F / unitsPerEm;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.preflight.font.container.TrueTypeContainer
 * JD-Core Version:    0.6.2
 */