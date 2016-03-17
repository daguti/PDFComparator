/*     */ package org.apache.jempbox.xmp;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.util.Calendar;
/*     */ import java.util.List;
/*     */ import org.w3c.dom.Element;
/*     */ 
/*     */ public class XMPSchemaDublinCore extends XMPSchema
/*     */ {
/*     */   public static final String NAMESPACE = "http://purl.org/dc/elements/1.1/";
/*     */ 
/*     */   public XMPSchemaDublinCore(XMPMetadata parent)
/*     */   {
/*  44 */     super(parent, "dc", "http://purl.org/dc/elements/1.1/");
/*     */   }
/*     */ 
/*     */   public XMPSchemaDublinCore(Element element, String prefix)
/*     */   {
/*  55 */     super(element, prefix);
/*     */   }
/*     */ 
/*     */   public void removeContributor(String contributor)
/*     */   {
/*  65 */     removeBagValue(this.prefix + ":contributor", contributor);
/*     */   }
/*     */ 
/*     */   public void addContributor(String contributor)
/*     */   {
/*  75 */     addBagValue(this.prefix + ":contributor", contributor);
/*     */   }
/*     */ 
/*     */   public List<String> getContributors()
/*     */   {
/*  85 */     return getBagList(this.prefix + ":contributor");
/*     */   }
/*     */ 
/*     */   public void setCoverage(String coverage)
/*     */   {
/*  95 */     setTextProperty(this.prefix + ":coverage", coverage);
/*     */   }
/*     */ 
/*     */   public String getCoverage()
/*     */   {
/* 105 */     return getTextProperty(this.prefix + ":coverage");
/*     */   }
/*     */ 
/*     */   public void removeCreator(String creator)
/*     */   {
/* 115 */     removeSequenceValue(this.prefix + ":creator", creator);
/*     */   }
/*     */ 
/*     */   public void addCreator(String creator)
/*     */   {
/* 125 */     addSequenceValue(this.prefix + ":creator", creator);
/*     */   }
/*     */ 
/*     */   public List<String> getCreators()
/*     */   {
/* 135 */     return getSequenceList(this.prefix + ":creator");
/*     */   }
/*     */ 
/*     */   public void removeDate(Calendar date)
/*     */   {
/* 145 */     removeSequenceDateValue(this.prefix + ":date", date);
/*     */   }
/*     */ 
/*     */   public void addDate(Calendar date)
/*     */   {
/* 155 */     addSequenceDateValue(this.prefix + ":date", date);
/*     */   }
/*     */ 
/*     */   public List<Calendar> getDates()
/*     */     throws IOException
/*     */   {
/* 167 */     return getSequenceDateList(this.prefix + ":date");
/*     */   }
/*     */ 
/*     */   public void setDescription(String description)
/*     */   {
/* 177 */     setLanguageProperty(this.prefix + ":description", null, description);
/*     */   }
/*     */ 
/*     */   public String getDescription()
/*     */   {
/* 187 */     return getLanguageProperty(this.prefix + ":description", null);
/*     */   }
/*     */ 
/*     */   public void setDescription(String language, String description)
/*     */   {
/* 198 */     setLanguageProperty(this.prefix + ":description", language, description);
/*     */   }
/*     */ 
/*     */   public String getDescription(String language)
/*     */   {
/* 210 */     return getLanguageProperty(this.prefix + ":description", language);
/*     */   }
/*     */ 
/*     */   public List<String> getDescriptionLanguages()
/*     */   {
/* 220 */     return getLanguagePropertyLanguages(this.prefix + ":description");
/*     */   }
/*     */ 
/*     */   public void setFormat(String format)
/*     */   {
/* 230 */     setTextProperty(this.prefix + ":format", format);
/*     */   }
/*     */ 
/*     */   public String getFormat()
/*     */   {
/* 240 */     return getTextProperty(this.prefix + ":format");
/*     */   }
/*     */ 
/*     */   public void setIdentifier(String id)
/*     */   {
/* 250 */     setTextProperty(this.prefix + ":identifier", id);
/*     */   }
/*     */ 
/*     */   public String getIdentifier()
/*     */   {
/* 260 */     return getTextProperty(this.prefix + ":identifier");
/*     */   }
/*     */ 
/*     */   public void removeLanguage(String language)
/*     */   {
/* 270 */     removeBagValue(this.prefix + ":language", language);
/*     */   }
/*     */ 
/*     */   public void addLanguage(String language)
/*     */   {
/* 280 */     addBagValue(this.prefix + ":language", language);
/*     */   }
/*     */ 
/*     */   public List<String> getLanguages()
/*     */   {
/* 290 */     return getBagList(this.prefix + ":language");
/*     */   }
/*     */ 
/*     */   public void removePublisher(String publisher)
/*     */   {
/* 300 */     removeBagValue(this.prefix + ":publisher", publisher);
/*     */   }
/*     */ 
/*     */   public void addPublisher(String publisher)
/*     */   {
/* 310 */     addBagValue(this.prefix + ":publisher", publisher);
/*     */   }
/*     */ 
/*     */   public List<String> getPublishers()
/*     */   {
/* 320 */     return getBagList(this.prefix + ":publisher");
/*     */   }
/*     */ 
/*     */   public void removeRelation(String relation)
/*     */   {
/* 331 */     removeBagValue(this.prefix + ":relation", relation);
/*     */   }
/*     */ 
/*     */   public void addRelation(String relation)
/*     */   {
/* 342 */     addBagValue(this.prefix + ":relation", relation);
/*     */   }
/*     */ 
/*     */   public List<String> getRelationships()
/*     */   {
/* 352 */     return getBagList(this.prefix + ":relation");
/*     */   }
/*     */ 
/*     */   public void setRights(String rights)
/*     */   {
/* 363 */     setLanguageProperty(this.prefix + ":rights", null, rights);
/*     */   }
/*     */ 
/*     */   public String getRights()
/*     */   {
/* 373 */     return getLanguageProperty(this.prefix + ":rights", null);
/*     */   }
/*     */ 
/*     */   public void setRights(String language, String rights)
/*     */   {
/* 384 */     setLanguageProperty(this.prefix + ":rights", language, rights);
/*     */   }
/*     */ 
/*     */   public String getRights(String language)
/*     */   {
/* 396 */     return getLanguageProperty(this.prefix + ":rights", language);
/*     */   }
/*     */ 
/*     */   public List<String> getRightsLanguages()
/*     */   {
/* 406 */     return getLanguagePropertyLanguages(this.prefix + ":rights");
/*     */   }
/*     */ 
/*     */   public void setSource(String id)
/*     */   {
/* 416 */     setTextProperty(this.prefix + ":source", id);
/*     */   }
/*     */ 
/*     */   public String getSource()
/*     */   {
/* 426 */     return getTextProperty(this.prefix + ":source");
/*     */   }
/*     */ 
/*     */   public void removeSubject(String subject)
/*     */   {
/* 436 */     removeBagValue(this.prefix + ":subject", subject);
/*     */   }
/*     */ 
/*     */   public void addSubject(String subject)
/*     */   {
/* 446 */     addBagValue(this.prefix + ":subject", subject);
/*     */   }
/*     */ 
/*     */   public List<String> getSubjects()
/*     */   {
/* 456 */     return getBagList(this.prefix + ":subject");
/*     */   }
/*     */ 
/*     */   public void setTitle(String title)
/*     */   {
/* 466 */     setLanguageProperty(this.prefix + ":title", null, title);
/*     */   }
/*     */ 
/*     */   public String getTitle()
/*     */   {
/* 476 */     return getLanguageProperty(this.prefix + ":title", null);
/*     */   }
/*     */ 
/*     */   public void setTitle(String language, String title)
/*     */   {
/* 487 */     setLanguageProperty(this.prefix + ":title", language, title);
/*     */   }
/*     */ 
/*     */   public String getTitle(String language)
/*     */   {
/* 499 */     return getLanguageProperty(this.prefix + ":title", language);
/*     */   }
/*     */ 
/*     */   public List<String> getTitleLanguages()
/*     */   {
/* 509 */     return getLanguagePropertyLanguages(this.prefix + ":title");
/*     */   }
/*     */ 
/*     */   public void addType(String type)
/*     */   {
/* 519 */     addBagValue(this.prefix + ":type", type);
/*     */   }
/*     */ 
/*     */   public List<String> getTypes()
/*     */   {
/* 529 */     return getBagList(this.prefix + ":type");
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.jempbox.xmp.XMPSchemaDublinCore
 * JD-Core Version:    0.6.2
 */