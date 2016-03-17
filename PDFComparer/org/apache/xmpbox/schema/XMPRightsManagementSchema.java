/*     */ package org.apache.xmpbox.schema;
/*     */ 
/*     */ import java.util.List;
/*     */ import org.apache.xmpbox.XMPMetadata;
/*     */ import org.apache.xmpbox.type.ArrayProperty;
/*     */ import org.apache.xmpbox.type.BooleanType;
/*     */ import org.apache.xmpbox.type.Cardinality;
/*     */ import org.apache.xmpbox.type.PropertyType;
/*     */ import org.apache.xmpbox.type.StructuredType;
/*     */ import org.apache.xmpbox.type.TextType;
/*     */ import org.apache.xmpbox.type.Types;
/*     */ import org.apache.xmpbox.type.URLType;
/*     */ 
/*     */ @StructuredType(preferedPrefix="xmpRights", namespace="http://ns.adobe.com/xap/1.0/rights/")
/*     */ public class XMPRightsManagementSchema extends XMPSchema
/*     */ {
/*     */ 
/*     */   @PropertyType(type=Types.URL, card=Cardinality.Simple)
/*     */   public static final String CERTIFICATE = "Certificate";
/*     */ 
/*     */   @PropertyType(type=Types.Boolean, card=Cardinality.Simple)
/*     */   public static final String MARKED = "Marked";
/*     */ 
/*     */   @PropertyType(type=Types.ProperName, card=Cardinality.Bag)
/*     */   public static final String OWNER = "Owner";
/*     */ 
/*     */   @PropertyType(type=Types.LangAlt, card=Cardinality.Simple)
/*     */   public static final String USAGETERMS = "UsageTerms";
/*     */ 
/*     */   @PropertyType(type=Types.URL, card=Cardinality.Simple)
/*     */   public static final String WEBSTATEMENT = "WebStatement";
/*     */ 
/*     */   public XMPRightsManagementSchema(XMPMetadata metadata)
/*     */   {
/*  69 */     super(metadata);
/*     */   }
/*     */ 
/*     */   public XMPRightsManagementSchema(XMPMetadata metadata, String ownPrefix)
/*     */   {
/*  82 */     super(metadata, ownPrefix);
/*     */   }
/*     */ 
/*     */   public void addOwner(String value)
/*     */   {
/*  93 */     addQualifiedBagValue("Owner", value);
/*     */   }
/*     */ 
/*     */   public void removeOwner(String value)
/*     */   {
/*  98 */     removeUnqualifiedBagValue("Owner", value);
/*     */   }
/*     */ 
/*     */   public ArrayProperty getOwnersProperty()
/*     */   {
/* 108 */     return (ArrayProperty)getProperty("Owner");
/*     */   }
/*     */ 
/*     */   public List<String> getOwners()
/*     */   {
/* 118 */     return getUnqualifiedBagValueList("Owner");
/*     */   }
/*     */ 
/*     */   public void setMarked(Boolean marked)
/*     */   {
/* 129 */     BooleanType tt = (BooleanType)instanciateSimple("Marked", marked.booleanValue() ? "True" : "False");
/* 130 */     setMarkedProperty(tt);
/*     */   }
/*     */ 
/*     */   public void setMarkedProperty(BooleanType marked)
/*     */   {
/* 141 */     addProperty(marked);
/*     */   }
/*     */ 
/*     */   public BooleanType getMarkedProperty()
/*     */   {
/* 151 */     return (BooleanType)getProperty("Marked");
/*     */   }
/*     */ 
/*     */   public Boolean getMarked()
/*     */   {
/* 161 */     BooleanType bt = (BooleanType)getProperty("Marked");
/* 162 */     return bt == null ? null : bt.getValue();
/*     */   }
/*     */ 
/*     */   public void addUsageTerms(String lang, String value)
/*     */   {
/* 175 */     setUnqualifiedLanguagePropertyValue("UsageTerms", lang, value);
/*     */   }
/*     */ 
/*     */   public void setUsageTerms(String terms)
/*     */   {
/* 186 */     addUsageTerms(null, terms);
/*     */   }
/*     */ 
/*     */   @Deprecated
/*     */   public void setDescription(String language, String terms)
/*     */   {
/* 197 */     addUsageTerms(language, terms);
/*     */   }
/*     */ 
/*     */   public ArrayProperty getUsageTermsProperty()
/*     */   {
/* 207 */     return (ArrayProperty)getProperty("UsageTerms");
/*     */   }
/*     */ 
/*     */   public List<String> getUsageTermsLanguages()
/*     */   {
/* 217 */     return getUnqualifiedLanguagePropertyLanguagesValue("UsageTerms");
/*     */   }
/*     */ 
/*     */   public String getUsageTerms(String lang)
/*     */   {
/* 229 */     return getUnqualifiedLanguagePropertyValue("UsageTerms", lang);
/*     */   }
/*     */ 
/*     */   public String getUsageTerms()
/*     */   {
/* 239 */     return getUsageTerms(null);
/*     */   }
/*     */ 
/*     */   public TextType getWebStatementProperty()
/*     */   {
/* 249 */     return (TextType)getProperty("WebStatement");
/*     */   }
/*     */ 
/*     */   public String getWebStatement()
/*     */   {
/* 259 */     TextType tt = (TextType)getProperty("WebStatement");
/* 260 */     return tt == null ? null : tt.getStringValue();
/*     */   }
/*     */ 
/*     */   public void setWebStatement(String url)
/*     */   {
/* 271 */     URLType tt = (URLType)instanciateSimple("WebStatement", url);
/* 272 */     setWebStatementProperty(tt);
/*     */   }
/*     */ 
/*     */   public void setWebStatementProperty(URLType url)
/*     */   {
/* 283 */     addProperty(url);
/*     */   }
/*     */ 
/*     */   public TextType getCertificateProperty()
/*     */   {
/* 293 */     return (TextType)getProperty("Certificate");
/*     */   }
/*     */ 
/*     */   public String getCertificate()
/*     */   {
/* 303 */     TextType tt = (TextType)getProperty("Certificate");
/* 304 */     return tt == null ? null : tt.getStringValue();
/*     */   }
/*     */ 
/*     */   @Deprecated
/*     */   public String getCopyright()
/*     */   {
/* 315 */     return getCertificate();
/*     */   }
/*     */ 
/*     */   @Deprecated
/*     */   public String getCertificateURL()
/*     */   {
/* 326 */     return getCertificate();
/*     */   }
/*     */ 
/*     */   public void setCertificate(String url)
/*     */   {
/* 337 */     URLType tt = (URLType)instanciateSimple("Certificate", url);
/* 338 */     setCertificateProperty(tt);
/*     */   }
/*     */ 
/*     */   @Deprecated
/*     */   public void setCertificateURL(String certificate)
/*     */   {
/* 349 */     setCertificate(certificate);
/*     */   }
/*     */ 
/*     */   @Deprecated
/*     */   public void setCopyright(String certificate)
/*     */   {
/* 360 */     setCertificate(certificate);
/*     */   }
/*     */ 
/*     */   public void setCertificateProperty(URLType url)
/*     */   {
/* 371 */     addProperty(url);
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.xmpbox.schema.XMPRightsManagementSchema
 * JD-Core Version:    0.6.2
 */