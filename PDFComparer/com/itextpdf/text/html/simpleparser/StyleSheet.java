/*     */ package com.itextpdf.text.html.simpleparser;
/*     */ 
/*     */ import com.itextpdf.text.BaseColor;
/*     */ import com.itextpdf.text.html.HtmlUtilities;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import java.util.Properties;
/*     */ import java.util.Set;
/*     */ 
/*     */ @Deprecated
/*     */ public class StyleSheet
/*     */ {
/*  68 */   protected Map<String, Map<String, String>> tagMap = new HashMap();
/*     */ 
/*  75 */   protected Map<String, Map<String, String>> classMap = new HashMap();
/*     */ 
/*     */   public void loadTagStyle(String tag, Map<String, String> attrs)
/*     */   {
/*  89 */     this.tagMap.put(tag.toLowerCase(), attrs);
/*     */   }
/*     */ 
/*     */   public void loadTagStyle(String tag, String key, String value)
/*     */   {
/* 100 */     tag = tag.toLowerCase();
/* 101 */     Map styles = (Map)this.tagMap.get(tag);
/* 102 */     if (styles == null) {
/* 103 */       styles = new HashMap();
/* 104 */       this.tagMap.put(tag, styles);
/*     */     }
/* 106 */     styles.put(key, value);
/*     */   }
/*     */ 
/*     */   public void loadStyle(String className, HashMap<String, String> attrs)
/*     */   {
/* 115 */     this.classMap.put(className.toLowerCase(), attrs);
/*     */   }
/*     */ 
/*     */   public void loadStyle(String className, String key, String value)
/*     */   {
/* 126 */     className = className.toLowerCase();
/* 127 */     Map styles = (Map)this.classMap.get(className);
/* 128 */     if (styles == null) {
/* 129 */       styles = new HashMap();
/* 130 */       this.classMap.put(className, styles);
/*     */     }
/* 132 */     styles.put(key, value);
/*     */   }
/*     */ 
/*     */   public void applyStyle(String tag, Map<String, String> attrs)
/*     */   {
/* 143 */     Map map = (Map)this.tagMap.get(tag.toLowerCase());
/* 144 */     if (map != null)
/*     */     {
/* 146 */       Map temp = new HashMap(map);
/*     */ 
/* 148 */       temp.putAll(attrs);
/*     */ 
/* 150 */       attrs.putAll(temp);
/*     */     }
/*     */ 
/* 153 */     String cm = (String)attrs.get("class");
/* 154 */     if (cm == null) {
/* 155 */       return;
/*     */     }
/* 157 */     map = (Map)this.classMap.get(cm.toLowerCase());
/* 158 */     if (map == null) {
/* 159 */       return;
/*     */     }
/* 161 */     attrs.remove("class");
/*     */ 
/* 163 */     Map temp = new HashMap(map);
/*     */ 
/* 165 */     temp.putAll(attrs);
/*     */ 
/* 167 */     attrs.putAll(temp);
/*     */   }
/*     */ 
/*     */   public static void resolveStyleAttribute(Map<String, String> h, ChainedProperties chain)
/*     */   {
/* 177 */     String style = (String)h.get("style");
/* 178 */     if (style == null)
/* 179 */       return;
/* 180 */     Properties prop = HtmlUtilities.parseAttributes(style);
/* 181 */     for (Iterator i$ = prop.keySet().iterator(); i$.hasNext(); ) { Object element = i$.next();
/* 182 */       String key = (String)element;
/* 183 */       if (key.equals("font-family")) {
/* 184 */         h.put("face", prop.getProperty(key));
/* 185 */       } else if (key.equals("font-size")) {
/* 186 */         float actualFontSize = HtmlUtilities.parseLength(chain.getProperty("size"), 12.0F);
/*     */ 
/* 189 */         if (actualFontSize <= 0.0F)
/* 190 */           actualFontSize = 12.0F;
/* 191 */         h.put("size", Float.toString(HtmlUtilities.parseLength(prop.getProperty(key), actualFontSize)) + "pt");
/*     */       }
/* 194 */       else if (key.equals("font-style")) {
/* 195 */         String ss = prop.getProperty(key).trim().toLowerCase();
/* 196 */         if ((ss.equals("italic")) || (ss.equals("oblique")))
/* 197 */           h.put("i", null);
/* 198 */       } else if (key.equals("font-weight")) {
/* 199 */         String ss = prop.getProperty(key).trim().toLowerCase();
/* 200 */         if ((ss.equals("bold")) || (ss.equals("700")) || (ss.equals("800")) || (ss.equals("900")))
/*     */         {
/* 202 */           h.put("b", null);
/*     */         } } else if (key.equals("text-decoration")) {
/* 204 */         String ss = prop.getProperty(key).trim().toLowerCase();
/* 205 */         if (ss.equals("underline"))
/* 206 */           h.put("u", null);
/* 207 */       } else if (key.equals("color")) {
/* 208 */         BaseColor c = HtmlUtilities.decodeColor(prop.getProperty(key));
/* 209 */         if (c != null) {
/* 210 */           int hh = c.getRGB();
/* 211 */           String hs = Integer.toHexString(hh);
/* 212 */           hs = "000000" + hs;
/* 213 */           hs = "#" + hs.substring(hs.length() - 6);
/* 214 */           h.put("color", hs);
/*     */         }
/* 216 */       } else if (key.equals("line-height")) {
/* 217 */         String ss = prop.getProperty(key).trim();
/* 218 */         float actualFontSize = HtmlUtilities.parseLength(chain.getProperty("size"), 12.0F);
/*     */ 
/* 221 */         if (actualFontSize <= 0.0F)
/* 222 */           actualFontSize = 12.0F;
/* 223 */         float v = HtmlUtilities.parseLength(prop.getProperty(key), actualFontSize);
/*     */ 
/* 225 */         if (ss.endsWith("%")) {
/* 226 */           h.put("leading", "0," + v / 100.0F);
/* 227 */           return;
/*     */         }
/* 229 */         if ("normal".equalsIgnoreCase(ss)) {
/* 230 */           h.put("leading", "0,1.5");
/* 231 */           return;
/*     */         }
/* 233 */         h.put("leading", v + ",0");
/* 234 */       } else if (key.equals("text-align")) {
/* 235 */         String ss = prop.getProperty(key).trim().toLowerCase();
/* 236 */         h.put("align", ss);
/* 237 */       } else if (key.equals("padding-left")) {
/* 238 */         String ss = prop.getProperty(key).trim().toLowerCase();
/* 239 */         h.put("indent", Float.toString(HtmlUtilities.parseLength(ss)));
/*     */       }
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.html.simpleparser.StyleSheet
 * JD-Core Version:    0.6.2
 */