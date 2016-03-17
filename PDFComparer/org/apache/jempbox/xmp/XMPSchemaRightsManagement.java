/*     */ package org.apache.jempbox.xmp;
/*     */ 
/*     */ import java.util.List;
/*     */ import org.w3c.dom.Element;
/*     */ 
/*     */ public class XMPSchemaRightsManagement extends XMPSchema
/*     */ {
/*     */   public static final String NAMESPACE = "http://ns.adobe.com/xap/1.0/rights/";
/*     */ 
/*     */   public XMPSchemaRightsManagement(XMPMetadata parent)
/*     */   {
/*  43 */     super(parent, "xmpRights", "http://ns.adobe.com/xap/1.0/rights/");
/*     */   }
/*     */ 
/*     */   public XMPSchemaRightsManagement(Element element, String prefix)
/*     */   {
/*  54 */     super(element, prefix);
/*     */   }
/*     */ 
/*     */   public void setCertificateURL(String certificate)
/*     */   {
/*  64 */     setTextProperty("xmpRights:Certificate", certificate);
/*     */   }
/*     */ 
/*     */   public String getCertificateURL()
/*     */   {
/*  74 */     return getTextProperty(this.prefix + ":Certificate");
/*     */   }
/*     */ 
/*     */   public void setMarked(Boolean marked)
/*     */   {
/*  84 */     setBooleanProperty(this.prefix + ":Marked", marked);
/*     */   }
/*     */ 
/*     */   public Boolean getMarked()
/*     */   {
/*  94 */     Boolean b = getBooleanProperty(this.prefix + ":Marked");
/*  95 */     return b != null ? b : Boolean.FALSE;
/*     */   }
/*     */ 
/*     */   public void removeOwner(String owner)
/*     */   {
/* 105 */     removeBagValue(this.prefix + ":Owner", owner);
/*     */   }
/*     */ 
/*     */   public void addOwner(String owner)
/*     */   {
/* 115 */     addBagValue(this.prefix + ":Owner", owner);
/*     */   }
/*     */ 
/*     */   public List<String> getOwners()
/*     */   {
/* 125 */     return getBagList(this.prefix + ":Owner");
/*     */   }
/*     */ 
/*     */   public void setUsageTerms(String terms)
/*     */   {
/* 135 */     setLanguageProperty(this.prefix + ":UsageTerms", null, terms);
/*     */   }
/*     */ 
/*     */   public String getUsageTerms()
/*     */   {
/* 145 */     return getLanguageProperty(this.prefix + ":UsageTerms", null);
/*     */   }
/*     */ 
/*     */   public void setDescription(String language, String terms)
/*     */   {
/* 156 */     setLanguageProperty(this.prefix + ":UsageTerms", language, terms);
/*     */   }
/*     */ 
/*     */   public String getUsageTerms(String language)
/*     */   {
/* 168 */     return getLanguageProperty(this.prefix + ":UsageTerms", language);
/*     */   }
/*     */ 
/*     */   public List<String> getUsageTermsLanguages()
/*     */   {
/* 178 */     return getLanguagePropertyLanguages(this.prefix + ":UsageTerms");
/*     */   }
/*     */ 
/*     */   public void setWebStatement(String webStatement)
/*     */   {
/* 188 */     setTextProperty(this.prefix + ":WebStatement", webStatement);
/*     */   }
/*     */ 
/*     */   public String getWebStatement()
/*     */   {
/* 198 */     return getTextProperty(this.prefix + ":WebStatement");
/*     */   }
/*     */ 
/*     */   public void setCopyright(String copyright)
/*     */   {
/* 208 */     setTextProperty(this.prefix + ":Copyright", copyright);
/*     */   }
/*     */ 
/*     */   public String getCopyright()
/*     */   {
/* 218 */     return getTextProperty(this.prefix + ":Copyright");
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.jempbox.xmp.XMPSchemaRightsManagement
 * JD-Core Version:    0.6.2
 */