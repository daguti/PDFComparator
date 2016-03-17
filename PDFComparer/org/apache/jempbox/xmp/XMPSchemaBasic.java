/*     */ package org.apache.jempbox.xmp;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.util.Calendar;
/*     */ import java.util.List;
/*     */ import org.w3c.dom.Element;
/*     */ 
/*     */ public class XMPSchemaBasic extends XMPSchema
/*     */ {
/*     */   public static final String NAMESPACE = "http://ns.adobe.com/xap/1.0/";
/*     */ 
/*     */   public XMPSchemaBasic(XMPMetadata parent)
/*     */   {
/*  45 */     super(parent, "xmp", "http://ns.adobe.com/xap/1.0/");
/*  46 */     this.schema.setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns:xapGImg", "http://ns.adobe.com/xap/1.0/g/img/");
/*     */   }
/*     */ 
/*     */   public XMPSchemaBasic(Element element, String prefix)
/*     */   {
/*  60 */     super(element, prefix);
/*  61 */     if (this.schema.getAttribute("xmlns:xapGImg") == null)
/*     */     {
/*  63 */       this.schema.setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns:xapGImg", "http://ns.adobe.com/xap/1.0/g/img/");
/*     */     }
/*     */   }
/*     */ 
/*     */   public void removeAdvisory(String advisory)
/*     */   {
/*  78 */     removeBagValue(this.prefix + ":Advisory", advisory);
/*     */   }
/*     */ 
/*     */   public void addAdvisory(String advisory)
/*     */   {
/*  88 */     addBagValue(this.prefix + ":Advisory", advisory);
/*     */   }
/*     */ 
/*     */   public List<String> getAdvisories()
/*     */   {
/*  98 */     return getBagList(this.prefix + ":Advisory");
/*     */   }
/*     */ 
/*     */   public void setBaseURL(String url)
/*     */   {
/* 108 */     setTextProperty(this.prefix + ":BaseURL", url);
/*     */   }
/*     */ 
/*     */   public String getBaseURL()
/*     */   {
/* 118 */     return getTextProperty(this.prefix + ":BaseURL");
/*     */   }
/*     */ 
/*     */   public void setCreateDate(Calendar date)
/*     */   {
/* 128 */     setDateProperty(this.prefix + ":CreateDate", date);
/*     */   }
/*     */ 
/*     */   public Calendar getCreateDate()
/*     */     throws IOException
/*     */   {
/* 141 */     return getDateProperty(this.prefix + ":CreateDate");
/*     */   }
/*     */ 
/*     */   public void setCreatorTool(String creator)
/*     */   {
/* 152 */     setTextProperty(this.prefix + ":CreatorTool", creator);
/*     */   }
/*     */ 
/*     */   public String getCreatorTool()
/*     */   {
/* 163 */     return getTextProperty(this.prefix + ":CreatorTool");
/*     */   }
/*     */ 
/*     */   public void removeIdentifier(String id)
/*     */   {
/* 173 */     removeBagValue(this.prefix + ":Identifier", id);
/*     */   }
/*     */ 
/*     */   public void addIdentifier(String id)
/*     */   {
/* 183 */     addBagValue(this.prefix + ":Identifier", id);
/*     */   }
/*     */ 
/*     */   public List<String> getIdentifiers()
/*     */   {
/* 193 */     return getBagList(this.prefix + ":Identifier");
/*     */   }
/*     */ 
/*     */   public void setLabel(String label)
/*     */   {
/* 203 */     setTextProperty(this.prefix + ":Label", label);
/*     */   }
/*     */ 
/*     */   public String getLabel()
/*     */   {
/* 213 */     return getTextProperty(this.prefix + "p:Label");
/*     */   }
/*     */ 
/*     */   public void setTitle(String title)
/*     */   {
/* 223 */     setTextProperty(this.prefix + ":Title", title);
/*     */   }
/*     */ 
/*     */   public String getTitle()
/*     */   {
/* 233 */     return getTextProperty(this.prefix + ":Title");
/*     */   }
/*     */ 
/*     */   public void setMetadataDate(Calendar date)
/*     */   {
/* 243 */     setDateProperty(this.prefix + ":MetadataDate", date);
/*     */   }
/*     */ 
/*     */   public Calendar getMetadataDate()
/*     */     throws IOException
/*     */   {
/* 256 */     return getDateProperty(this.prefix + ":MetadataDate");
/*     */   }
/*     */ 
/*     */   public void setModifyDate(Calendar date)
/*     */   {
/* 266 */     setDateProperty(this.prefix + ":ModifyDate", date);
/*     */   }
/*     */ 
/*     */   public Calendar getModifyDate()
/*     */     throws IOException
/*     */   {
/* 279 */     return getDateProperty(this.prefix + ":ModifyDate");
/*     */   }
/*     */ 
/*     */   public void setNickname(String nickname)
/*     */   {
/* 289 */     setTextProperty(this.prefix + ":Nickname", nickname);
/*     */   }
/*     */ 
/*     */   public String getNickname()
/*     */   {
/* 299 */     return getTextProperty(this.prefix + ":Nickname");
/*     */   }
/*     */ 
/*     */   public Integer getRating()
/*     */   {
/* 309 */     return getIntegerProperty(this.prefix + ":Rating");
/*     */   }
/*     */ 
/*     */   public void setRating(Integer rating)
/*     */   {
/* 319 */     setIntegerProperty(this.prefix + ":Rating", rating);
/*     */   }
/*     */ 
/*     */   public void setThumbnail(Thumbnail thumbnail)
/*     */   {
/* 329 */     setThumbnailProperty(this.prefix + ":Thumbnails", null, thumbnail);
/*     */   }
/*     */ 
/*     */   public Thumbnail getThumbnail()
/*     */   {
/* 339 */     return getThumbnailProperty(this.prefix + ":Thumbnails", null);
/*     */   }
/*     */ 
/*     */   public void setThumbnail(String language, Thumbnail thumbnail)
/*     */   {
/* 350 */     setThumbnailProperty(this.prefix + ":Thumbnails", language, thumbnail);
/*     */   }
/*     */ 
/*     */   public Thumbnail getThumbnail(String language)
/*     */   {
/* 362 */     return getThumbnailProperty(this.prefix + ":Thumbnails", language);
/*     */   }
/*     */ 
/*     */   public List<String> getThumbnailLanguages()
/*     */   {
/* 372 */     return getLanguagePropertyLanguages(this.prefix + ":Thumbnails");
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.jempbox.xmp.XMPSchemaBasic
 * JD-Core Version:    0.6.2
 */