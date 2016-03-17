/*     */ package com.itextpdf.text.xml.xmp;
/*     */ 
/*     */ import com.itextpdf.text.xml.XMLUtil;
/*     */ import java.util.Enumeration;
/*     */ import java.util.Properties;
/*     */ 
/*     */ @Deprecated
/*     */ public class LangAlt extends Properties
/*     */ {
/*     */   private static final long serialVersionUID = 4396971487200843099L;
/*     */   public static final String DEFAULT = "x-default";
/*     */ 
/*     */   public LangAlt(String defaultValue)
/*     */   {
/*  64 */     addLanguage("x-default", defaultValue);
/*     */   }
/*     */ 
/*     */   public LangAlt()
/*     */   {
/*     */   }
/*     */ 
/*     */   public void addLanguage(String language, String value)
/*     */   {
/*  76 */     setProperty(language, XMLUtil.escapeXML(value, false));
/*     */   }
/*     */ 
/*     */   protected void process(StringBuffer buf, Object lang)
/*     */   {
/*  83 */     buf.append("<rdf:li xml:lang=\"");
/*  84 */     buf.append(lang);
/*  85 */     buf.append("\" >");
/*  86 */     buf.append(get(lang));
/*  87 */     buf.append("</rdf:li>");
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/*  95 */     StringBuffer sb = new StringBuffer();
/*  96 */     sb.append("<rdf:Alt>");
/*  97 */     for (Enumeration e = propertyNames(); e.hasMoreElements(); ) {
/*  98 */       process(sb, e.nextElement());
/*     */     }
/* 100 */     sb.append("</rdf:Alt>");
/* 101 */     return sb.toString();
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.xml.xmp.LangAlt
 * JD-Core Version:    0.6.2
 */