/*     */ package com.itextpdf.text.html.simpleparser;
/*     */ 
/*     */ import com.itextpdf.text.html.HtmlUtilities;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ 
/*     */ @Deprecated
/*     */ public class ChainedProperties
/*     */ {
/*  82 */   public List<TagAttributes> chain = new ArrayList();
/*     */ 
/*     */   public String getProperty(String key)
/*     */   {
/*  96 */     for (int k = this.chain.size() - 1; k >= 0; k--) {
/*  97 */       TagAttributes p = (TagAttributes)this.chain.get(k);
/*  98 */       Map attrs = p.attrs;
/*  99 */       String ret = (String)attrs.get(key);
/* 100 */       if (ret != null)
/* 101 */         return ret;
/*     */     }
/* 103 */     return null;
/*     */   }
/*     */ 
/*     */   public boolean hasProperty(String key)
/*     */   {
/* 114 */     for (int k = this.chain.size() - 1; k >= 0; k--) {
/* 115 */       TagAttributes p = (TagAttributes)this.chain.get(k);
/* 116 */       Map attrs = p.attrs;
/* 117 */       if (attrs.containsKey(key))
/* 118 */         return true;
/*     */     }
/* 120 */     return false;
/*     */   }
/*     */ 
/*     */   public void addToChain(String tag, Map<String, String> props)
/*     */   {
/* 129 */     adjustFontSize(props);
/* 130 */     this.chain.add(new TagAttributes(tag, props));
/*     */   }
/*     */ 
/*     */   public void removeChain(String tag)
/*     */   {
/* 139 */     for (int k = this.chain.size() - 1; k >= 0; k--)
/* 140 */       if (tag.equals(((TagAttributes)this.chain.get(k)).tag)) {
/* 141 */         this.chain.remove(k);
/* 142 */         return;
/*     */       }
/*     */   }
/*     */ 
/*     */   protected void adjustFontSize(Map<String, String> attrs)
/*     */   {
/* 155 */     String value = (String)attrs.get("size");
/*     */ 
/* 157 */     if (value == null) {
/* 158 */       return;
/*     */     }
/* 160 */     if (value.endsWith("pt")) {
/* 161 */       attrs.put("size", value.substring(0, value.length() - 2));
/*     */ 
/* 163 */       return;
/*     */     }
/* 165 */     String old = getProperty("size");
/* 166 */     attrs.put("size", Integer.toString(HtmlUtilities.getIndexedFontSize(value, old)));
/*     */   }
/*     */ 
/*     */   private static final class TagAttributes
/*     */   {
/*     */     final String tag;
/*     */     final Map<String, String> attrs;
/*     */ 
/*     */     TagAttributes(String tag, Map<String, String> attrs)
/*     */     {
/*  76 */       this.tag = tag;
/*  77 */       this.attrs = attrs;
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.html.simpleparser.ChainedProperties
 * JD-Core Version:    0.6.2
 */