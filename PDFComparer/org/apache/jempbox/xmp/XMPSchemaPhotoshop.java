/*     */ package org.apache.jempbox.xmp;
/*     */ 
/*     */ import java.util.List;
/*     */ import org.w3c.dom.Element;
/*     */ 
/*     */ public class XMPSchemaPhotoshop extends XMPSchema
/*     */ {
/*     */   public static final String NAMESPACE = "http://ns.adobe.com/photoshop/1.0/";
/*     */ 
/*     */   public XMPSchemaPhotoshop(XMPMetadata parent)
/*     */   {
/*  44 */     super(parent, "photoshop", "http://ns.adobe.com/photoshop/1.0/");
/*     */   }
/*     */ 
/*     */   public XMPSchemaPhotoshop(Element element, String aPrefix)
/*     */   {
/*  55 */     super(element, aPrefix);
/*     */   }
/*     */ 
/*     */   public void setAuthorsPosition(String s)
/*     */   {
/*  65 */     setTextProperty(this.prefix + ":AuthorsPosition", s);
/*     */   }
/*     */ 
/*     */   public String getAuthorsPosition()
/*     */   {
/*  75 */     return getTextProperty(this.prefix + ":AuthorsPosition");
/*     */   }
/*     */ 
/*     */   public void setCaptionWriter(String s)
/*     */   {
/*  85 */     setTextProperty(this.prefix + ":CaptionWriter", s);
/*     */   }
/*     */ 
/*     */   public String getCaptionWriter()
/*     */   {
/*  95 */     return getTextProperty(this.prefix + ":CaptionWriter");
/*     */   }
/*     */ 
/*     */   public void setCategory(String s)
/*     */   {
/* 104 */     if ((s != null) && (s.length() > 3))
/*     */     {
/* 106 */       throw new RuntimeException("Error: photoshop:Category is limited to three characters value='" + s + "'");
/*     */     }
/* 108 */     setTextProperty(this.prefix + ":Category", s);
/*     */   }
/*     */ 
/*     */   public String getCategory()
/*     */   {
/* 118 */     return getTextProperty(this.prefix + ":Category");
/*     */   }
/*     */ 
/*     */   public void setCity(String s)
/*     */   {
/* 128 */     setTextProperty(this.prefix + ":City", s);
/*     */   }
/*     */ 
/*     */   public String getCity()
/*     */   {
/* 138 */     return getTextProperty(this.prefix + ":City");
/*     */   }
/*     */ 
/*     */   public void setCountry(String s)
/*     */   {
/* 148 */     setTextProperty(this.prefix + ":Country", s);
/*     */   }
/*     */ 
/*     */   public String getCountry()
/*     */   {
/* 158 */     return getTextProperty(this.prefix + ":Country");
/*     */   }
/*     */ 
/*     */   public void setCredit(String s)
/*     */   {
/* 168 */     setTextProperty(this.prefix + ":Credit", s);
/*     */   }
/*     */ 
/*     */   public String getCredit()
/*     */   {
/* 178 */     return getTextProperty(this.prefix + ":Credit");
/*     */   }
/*     */ 
/*     */   public void setDateCreated(String s)
/*     */   {
/* 189 */     setTextProperty(this.prefix + ":DateCreated", s);
/*     */   }
/*     */ 
/*     */   public String getDateCreated()
/*     */   {
/* 199 */     return getTextProperty(this.prefix + ":DateCreated");
/*     */   }
/*     */ 
/*     */   public void setHeadline(String s)
/*     */   {
/* 209 */     setTextProperty(this.prefix + ":Headline", s);
/*     */   }
/*     */ 
/*     */   public String getHeadline()
/*     */   {
/* 219 */     return getTextProperty(this.prefix + ":Headline");
/*     */   }
/*     */ 
/*     */   public void setInstructions(String s)
/*     */   {
/* 229 */     setTextProperty(this.prefix + ":Instructions", s);
/*     */   }
/*     */ 
/*     */   public String getInstructions()
/*     */   {
/* 239 */     return getTextProperty(this.prefix + ":Instructions");
/*     */   }
/*     */ 
/*     */   public void setSource(String s)
/*     */   {
/* 249 */     setTextProperty(this.prefix + ":Source", s);
/*     */   }
/*     */ 
/*     */   public String getSource()
/*     */   {
/* 259 */     return getTextProperty(this.prefix + ":Source");
/*     */   }
/*     */ 
/*     */   public void setState(String s)
/*     */   {
/* 269 */     setTextProperty(this.prefix + ":State", s);
/*     */   }
/*     */ 
/*     */   public String getState()
/*     */   {
/* 279 */     return getTextProperty(this.prefix + ":State");
/*     */   }
/*     */ 
/*     */   public void addSupplementalCategory(String s)
/*     */   {
/* 289 */     addBagValue(this.prefix + ":SupplementalCategories", s);
/*     */   }
/*     */ 
/*     */   public List<String> getSupplementalCategories()
/*     */   {
/* 299 */     return getBagList(this.prefix + ":SupplementalCategories");
/*     */   }
/*     */ 
/*     */   public void removeSupplementalCategory(String s)
/*     */   {
/* 309 */     removeBagValue(this.prefix + ":SupplementalCategories", s);
/*     */   }
/*     */ 
/*     */   public void setTransmissionReference(String s)
/*     */   {
/* 319 */     setTextProperty(this.prefix + ":TransmissionReference", s);
/*     */   }
/*     */ 
/*     */   public String getTransmissionReference()
/*     */   {
/* 329 */     return getTextProperty(this.prefix + ":TransmissionReference");
/*     */   }
/*     */ 
/*     */   public void setUrgency(Integer s)
/*     */   {
/* 339 */     if (s != null)
/*     */     {
/* 341 */       if ((s.intValue() < 1) || (s.intValue() > 8))
/*     */       {
/* 343 */         throw new RuntimeException("Error: photoshop:Urgency must be between 1 and 8.  value=" + s);
/*     */       }
/*     */     }
/* 346 */     setIntegerProperty(this.prefix + ":Urgency", s);
/*     */   }
/*     */ 
/*     */   public Integer getUrgency()
/*     */   {
/* 356 */     return getIntegerProperty(this.prefix + ":Urgency");
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.jempbox.xmp.XMPSchemaPhotoshop
 * JD-Core Version:    0.6.2
 */