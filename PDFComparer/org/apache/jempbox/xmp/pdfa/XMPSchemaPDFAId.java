/*     */ package org.apache.jempbox.xmp.pdfa;
/*     */ 
/*     */ import org.apache.jempbox.xmp.XMPMetadata;
/*     */ import org.apache.jempbox.xmp.XMPSchema;
/*     */ import org.w3c.dom.Element;
/*     */ 
/*     */ public class XMPSchemaPDFAId extends XMPSchema
/*     */ {
/*     */   public static final String NAMESPACE = "http://www.aiim.org/pdfa/ns/id/";
/*     */ 
/*     */   public XMPSchemaPDFAId(XMPMetadata parent)
/*     */   {
/*  45 */     super(parent, "pdfaid", "http://www.aiim.org/pdfa/ns/id/");
/*     */   }
/*     */ 
/*     */   public XMPSchemaPDFAId(Element element, String prefix)
/*     */   {
/*  56 */     super(element, prefix);
/*     */   }
/*     */ 
/*     */   public Integer getPart()
/*     */   {
/*  66 */     return getIntegerProperty(this.prefix + ":part");
/*     */   }
/*     */ 
/*     */   public void setPart(Integer part)
/*     */   {
/*  76 */     setIntegerProperty(this.prefix + ":part", part);
/*     */   }
/*     */ 
/*     */   public void setAmd(String amd)
/*     */   {
/*  86 */     setTextProperty(this.prefix + ":amd", amd);
/*     */   }
/*     */ 
/*     */   public String getAmd()
/*     */   {
/*  96 */     return getTextProperty(this.prefix + ":amd");
/*     */   }
/*     */ 
/*     */   public void setConformance(String conformance)
/*     */   {
/* 106 */     setTextProperty(this.prefix + ":conformance", conformance);
/*     */   }
/*     */ 
/*     */   public String getConformance()
/*     */   {
/* 116 */     return getTextProperty(this.prefix + ":conformance");
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.jempbox.xmp.pdfa.XMPSchemaPDFAId
 * JD-Core Version:    0.6.2
 */