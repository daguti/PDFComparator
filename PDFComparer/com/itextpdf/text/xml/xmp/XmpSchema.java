/*     */ package com.itextpdf.text.xml.xmp;
/*     */ 
/*     */ import com.itextpdf.text.xml.XMLUtil;
/*     */ import java.util.Enumeration;
/*     */ import java.util.Properties;
/*     */ 
/*     */ @Deprecated
/*     */ public abstract class XmpSchema extends Properties
/*     */ {
/*     */   private static final long serialVersionUID = -176374295948945272L;
/*     */   protected String xmlns;
/*     */ 
/*     */   public XmpSchema(String xmlns)
/*     */   {
/*  68 */     this.xmlns = xmlns;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/*  76 */     StringBuffer buf = new StringBuffer();
/*  77 */     for (Enumeration e = propertyNames(); e.hasMoreElements(); ) {
/*  78 */       process(buf, e.nextElement());
/*     */     }
/*  80 */     return buf.toString();
/*     */   }
/*     */ 
/*     */   protected void process(StringBuffer buf, Object p)
/*     */   {
/*  88 */     buf.append('<');
/*  89 */     buf.append(p);
/*  90 */     buf.append('>');
/*  91 */     buf.append(get(p));
/*  92 */     buf.append("</");
/*  93 */     buf.append(p);
/*  94 */     buf.append('>');
/*     */   }
/*     */ 
/*     */   public String getXmlns()
/*     */   {
/* 100 */     return this.xmlns;
/*     */   }
/*     */ 
/*     */   public Object addProperty(String key, String value)
/*     */   {
/* 109 */     return setProperty(key, value);
/*     */   }
/*     */ 
/*     */   public Object setProperty(String key, String value)
/*     */   {
/* 117 */     return super.setProperty(key, XMLUtil.escapeXML(value, false));
/*     */   }
/*     */ 
/*     */   public Object setProperty(String key, XmpArray value)
/*     */   {
/* 128 */     return super.setProperty(key, value.toString());
/*     */   }
/*     */ 
/*     */   public Object setProperty(String key, LangAlt value)
/*     */   {
/* 139 */     return super.setProperty(key, value.toString());
/*     */   }
/*     */ 
/*     */   /** @deprecated */
/*     */   public static String escape(String content)
/*     */   {
/* 148 */     return XMLUtil.escapeXML(content, false);
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.xml.xmp.XmpSchema
 * JD-Core Version:    0.6.2
 */