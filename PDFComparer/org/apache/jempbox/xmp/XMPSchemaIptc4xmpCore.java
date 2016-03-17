/*     */ package org.apache.jempbox.xmp;
/*     */ 
/*     */ import java.util.List;
/*     */ import org.w3c.dom.Element;
/*     */ 
/*     */ public class XMPSchemaIptc4xmpCore extends XMPSchema
/*     */ {
/*     */   public static final String NAMESPACE = "http://iptc.org/std/Iptc4xmpCore/1.0/xmlns/";
/*     */ 
/*     */   public XMPSchemaIptc4xmpCore(XMPMetadata metadata)
/*     */   {
/*  43 */     super(metadata, "Iptc4xmpCore", "http://iptc.org/std/Iptc4xmpCore/1.0/xmlns/");
/*     */   }
/*     */ 
/*     */   public XMPSchemaIptc4xmpCore(Element element, String aPrefix)
/*     */   {
/*  54 */     super(element, aPrefix);
/*     */   }
/*     */ 
/*     */   public void setCiAdrCity(String city)
/*     */   {
/*  64 */     setTextProperty(this.prefix + ":CiAdrCity", city);
/*     */   }
/*     */ 
/*     */   public String getCiAdrCity()
/*     */   {
/*  74 */     return getTextProperty(this.prefix + ":CiAdrCity");
/*     */   }
/*     */ 
/*     */   public void setCiAdrCtry(String country)
/*     */   {
/*  84 */     setTextProperty(this.prefix + ":CiAdrCtry", country);
/*     */   }
/*     */ 
/*     */   public String getCiAdrCtry()
/*     */   {
/*  94 */     return getTextProperty(this.prefix + ":CiAdrCtry");
/*     */   }
/*     */ 
/*     */   public void setCiAdrExtadr(String adr)
/*     */   {
/* 104 */     setTextProperty(this.prefix + ":CiAdrExtadr", adr);
/*     */   }
/*     */ 
/*     */   public String getCiAdrExtadr()
/*     */   {
/* 114 */     return getTextProperty(this.prefix + ":CiAdrExtadr");
/*     */   }
/*     */ 
/*     */   public void setCiAdrPcode(String po)
/*     */   {
/* 124 */     setTextProperty(this.prefix + ":CiAdrPcode", po);
/*     */   }
/*     */ 
/*     */   public String getCiAdrPcode()
/*     */   {
/* 134 */     return getTextProperty(this.prefix + ":CiAdrPcode");
/*     */   }
/*     */ 
/*     */   public void setCiAdrRegion(String state)
/*     */   {
/* 144 */     setTextProperty(this.prefix + ":CiAdrRegion", state);
/*     */   }
/*     */ 
/*     */   public String getCiAdrRegion()
/*     */   {
/* 154 */     return getTextProperty(this.prefix + ":CiAdrRegion");
/*     */   }
/*     */ 
/*     */   public void setCiEmailWork(String email)
/*     */   {
/* 164 */     setTextProperty(this.prefix + ":CiEmailWork", email);
/*     */   }
/*     */ 
/*     */   public String getCiEmailWork()
/*     */   {
/* 174 */     return getTextProperty(this.prefix + ":CiEmailWork");
/*     */   }
/*     */ 
/*     */   public void setCiTelWork(String tel)
/*     */   {
/* 184 */     setTextProperty(this.prefix + ":CiTelWork", tel);
/*     */   }
/*     */ 
/*     */   public String getCiTelWork()
/*     */   {
/* 194 */     return getTextProperty(this.prefix + ":CiTelWork");
/*     */   }
/*     */ 
/*     */   public void setCiUrlWork(String url)
/*     */   {
/* 204 */     setTextProperty(this.prefix + ":CiUrlWork", url);
/*     */   }
/*     */ 
/*     */   public String getCiUrlWork()
/*     */   {
/* 214 */     return getTextProperty(this.prefix + ":CiUrlWork");
/*     */   }
/*     */ 
/*     */   public void setLocation(String loc)
/*     */   {
/* 224 */     setTextProperty(this.prefix + ":Location", loc);
/*     */   }
/*     */ 
/*     */   public String getLocation()
/*     */   {
/* 233 */     return getTextProperty(this.prefix + ":Location");
/*     */   }
/*     */ 
/*     */   public void addScene(String scene)
/*     */   {
/* 243 */     addBagValue(this.prefix + ":Scene", scene);
/*     */   }
/*     */ 
/*     */   public List<String> getScenes()
/*     */   {
/* 253 */     return getBagList(this.prefix + ":Scene");
/*     */   }
/*     */ 
/*     */   public void addSubjectCode(String subject)
/*     */   {
/* 262 */     addBagValue(this.prefix + ":SubjectCode", subject);
/*     */   }
/*     */ 
/*     */   public List<String> getSubjectCodes()
/*     */   {
/* 272 */     return getBagList(this.prefix + ":SubjectCode");
/*     */   }
/*     */ 
/*     */   public void setIntellectualGenre(String genre)
/*     */   {
/* 282 */     setTextProperty(this.prefix + ":IntellectualGenre", genre);
/*     */   }
/*     */ 
/*     */   public String getIntellectualGenre()
/*     */   {
/* 292 */     return getTextProperty(this.prefix + ":IntellectualGenre");
/*     */   }
/*     */ 
/*     */   public void setCountryCode(String code)
/*     */   {
/* 302 */     setTextProperty(this.prefix + ":CountryCode", code);
/*     */   }
/*     */ 
/*     */   public String getCountryCode()
/*     */   {
/* 312 */     return getTextProperty(this.prefix + ":CountryCode");
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.jempbox.xmp.XMPSchemaIptc4xmpCore
 * JD-Core Version:    0.6.2
 */