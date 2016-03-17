/*     */ package org.apache.jempbox.xmp;
/*     */ 
/*     */ import org.w3c.dom.Element;
/*     */ 
/*     */ public class XMPSchemaPDF extends XMPSchema
/*     */ {
/*     */   public static final String NAMESPACE = "http://ns.adobe.com/pdf/1.3/";
/*     */ 
/*     */   public XMPSchemaPDF(XMPMetadata parent)
/*     */   {
/*  41 */     super(parent, "pdf", "http://ns.adobe.com/pdf/1.3/");
/*     */   }
/*     */ 
/*     */   public XMPSchemaPDF(Element element, String prefix)
/*     */   {
/*  52 */     super(element, prefix);
/*     */   }
/*     */ 
/*     */   public void setKeywords(String keywords)
/*     */   {
/*  62 */     setTextProperty(this.prefix + ":Keywords", keywords);
/*     */   }
/*     */ 
/*     */   public String getKeywords()
/*     */   {
/*  72 */     return getTextProperty(this.prefix + ":Keywords");
/*     */   }
/*     */ 
/*     */   public void setPDFVersion(String pdfVersion)
/*     */   {
/*  82 */     setTextProperty(this.prefix + ":PDFVersion", pdfVersion);
/*     */   }
/*     */ 
/*     */   public String getPDFVersion()
/*     */   {
/*  92 */     return getTextProperty(this.prefix + ":PDFVersion");
/*     */   }
/*     */ 
/*     */   public void setProducer(String producer)
/*     */   {
/* 102 */     setTextProperty(this.prefix + ":Producer", producer);
/*     */   }
/*     */ 
/*     */   public String getProducer()
/*     */   {
/* 112 */     return getTextProperty(this.prefix + ":Producer");
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.jempbox.xmp.XMPSchemaPDF
 * JD-Core Version:    0.6.2
 */