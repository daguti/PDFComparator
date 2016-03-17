/*     */ package org.apache.fontbox.cff;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ 
/*     */ public class CFFFontROS extends CFFFont
/*     */ {
/*     */   private String registry;
/*     */   private String ordering;
/*     */   private int supplement;
/*  30 */   private List<Map<String, Object>> fontDictionaries = new LinkedList();
/*  31 */   private List<Map<String, Object>> privateDictionaries = new LinkedList();
/*  32 */   private CIDKeyedFDSelect fdSelect = null;
/*     */ 
/*     */   public String getRegistry()
/*     */   {
/*  39 */     return this.registry;
/*     */   }
/*     */ 
/*     */   public void setRegistry(String registry)
/*     */   {
/*  48 */     this.registry = registry;
/*     */   }
/*     */ 
/*     */   public String getOrdering()
/*     */   {
/*  57 */     return this.ordering;
/*     */   }
/*     */ 
/*     */   public void setOrdering(String ordering)
/*     */   {
/*  66 */     this.ordering = ordering;
/*     */   }
/*     */ 
/*     */   public int getSupplement()
/*     */   {
/*  75 */     return this.supplement;
/*     */   }
/*     */ 
/*     */   public void setSupplement(int supplement)
/*     */   {
/*  84 */     this.supplement = supplement;
/*     */   }
/*     */ 
/*     */   public List<Map<String, Object>> getFontDict()
/*     */   {
/*  93 */     return this.fontDictionaries;
/*     */   }
/*     */ 
/*     */   public void setFontDict(List<Map<String, Object>> fontDict)
/*     */   {
/* 102 */     this.fontDictionaries = fontDict;
/*     */   }
/*     */ 
/*     */   public List<Map<String, Object>> getPrivDict()
/*     */   {
/* 111 */     return this.privateDictionaries;
/*     */   }
/*     */ 
/*     */   public void setPrivDict(List<Map<String, Object>> privDict)
/*     */   {
/* 120 */     this.privateDictionaries = privDict;
/*     */   }
/*     */ 
/*     */   public CIDKeyedFDSelect getFdSelect()
/*     */   {
/* 129 */     return this.fdSelect;
/*     */   }
/*     */ 
/*     */   public void setFdSelect(CIDKeyedFDSelect fdSelect)
/*     */   {
/* 138 */     this.fdSelect = fdSelect;
/*     */   }
/*     */ 
/*     */   public int getWidth(int CID)
/*     */     throws IOException
/*     */   {
/* 152 */     int fdArrayIndex = this.fdSelect.getFd(CID);
/* 153 */     if ((fdArrayIndex == -1) && (CID == 0))
/* 154 */       return super.getWidth(CID);
/* 155 */     if (fdArrayIndex == -1) {
/* 156 */       return 1000;
/*     */     }
/*     */ 
/* 159 */     Map fontDict = (Map)this.fontDictionaries.get(fdArrayIndex);
/* 160 */     Map privDict = (Map)this.privateDictionaries.get(fdArrayIndex);
/*     */ 
/* 162 */     int nominalWidth = privDict.containsKey("nominalWidthX") ? ((Number)privDict.get("nominalWidthX")).intValue() : 0;
/* 163 */     int defaultWidth = privDict.containsKey("defaultWidthX") ? ((Number)privDict.get("defaultWidthX")).intValue() : 1000;
/*     */ 
/* 165 */     for (CFFFont.Mapping m : getMappings()) {
/* 166 */       if (m.getSID() == CID)
/*     */       {
/* 168 */         CharStringRenderer csr = null;
/* 169 */         Number charStringType = (Number)getProperty("CharstringType");
/* 170 */         if (charStringType.intValue() == 2) {
/* 171 */           List lSeq = m.toType2Sequence();
/* 172 */           csr = new CharStringRenderer(false);
/* 173 */           csr.render(lSeq);
/*     */         } else {
/* 175 */           List lSeq = m.toType1Sequence();
/* 176 */           csr = new CharStringRenderer();
/* 177 */           csr.render(lSeq);
/*     */         }
/*     */ 
/* 182 */         return csr.getWidth() != 0 ? csr.getWidth() + nominalWidth : defaultWidth;
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 187 */     return getNotDefWidth(defaultWidth, nominalWidth);
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.fontbox.cff.CFFFontROS
 * JD-Core Version:    0.6.2
 */