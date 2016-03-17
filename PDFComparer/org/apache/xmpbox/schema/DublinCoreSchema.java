/*     */ package org.apache.xmpbox.schema;
/*     */ 
/*     */ import java.util.Calendar;
/*     */ import java.util.List;
/*     */ import org.apache.xmpbox.XMPMetadata;
/*     */ import org.apache.xmpbox.type.ArrayProperty;
/*     */ import org.apache.xmpbox.type.Cardinality;
/*     */ import org.apache.xmpbox.type.MIMEType;
/*     */ import org.apache.xmpbox.type.PropertyType;
/*     */ import org.apache.xmpbox.type.StructuredType;
/*     */ import org.apache.xmpbox.type.TextType;
/*     */ import org.apache.xmpbox.type.Types;
/*     */ 
/*     */ @StructuredType(preferedPrefix="dc", namespace="http://purl.org/dc/elements/1.1/")
/*     */ public class DublinCoreSchema extends XMPSchema
/*     */ {
/*     */ 
/*     */   @PropertyType(type=Types.Text, card=Cardinality.Bag)
/*     */   public static final String CONTRIBUTOR = "contributor";
/*     */ 
/*     */   @PropertyType(type=Types.Text, card=Cardinality.Simple)
/*     */   public static final String COVERAGE = "coverage";
/*     */ 
/*     */   @PropertyType(type=Types.Text, card=Cardinality.Seq)
/*     */   public static final String CREATOR = "creator";
/*     */ 
/*     */   @PropertyType(type=Types.Date, card=Cardinality.Seq)
/*     */   public static final String DATE = "date";
/*     */ 
/*     */   @PropertyType(type=Types.LangAlt, card=Cardinality.Simple)
/*     */   public static final String DESCRIPTION = "description";
/*     */ 
/*     */   @PropertyType(type=Types.MIMEType, card=Cardinality.Simple)
/*     */   public static final String FORMAT = "format";
/*     */ 
/*     */   @PropertyType(type=Types.Text, card=Cardinality.Simple)
/*     */   public static final String IDENTIFIER = "identifier";
/*     */ 
/*     */   @PropertyType(type=Types.Text, card=Cardinality.Bag)
/*     */   public static final String LANGUAGE = "language";
/*     */ 
/*     */   @PropertyType(type=Types.Text, card=Cardinality.Bag)
/*     */   public static final String PUBLISHER = "publisher";
/*     */ 
/*     */   @PropertyType(type=Types.Text, card=Cardinality.Bag)
/*     */   public static final String RELATION = "relation";
/*     */ 
/*     */   @PropertyType(type=Types.LangAlt, card=Cardinality.Simple)
/*     */   public static final String RIGHTS = "rights";
/*     */ 
/*     */   @PropertyType(type=Types.Text, card=Cardinality.Simple)
/*     */   public static final String SOURCE = "source";
/*     */ 
/*     */   @PropertyType(type=Types.Text, card=Cardinality.Bag)
/*     */   public static final String SUBJECT = "subject";
/*     */ 
/*     */   @PropertyType(type=Types.LangAlt, card=Cardinality.Simple)
/*     */   public static final String TITLE = "title";
/*     */ 
/*     */   @PropertyType(type=Types.Text, card=Cardinality.Bag)
/*     */   public static final String TYPE = "type";
/*     */ 
/*     */   public DublinCoreSchema(XMPMetadata metadata)
/*     */   {
/*  99 */     super(metadata);
/*     */   }
/*     */ 
/*     */   public DublinCoreSchema(XMPMetadata metadata, String ownPrefix)
/*     */   {
/* 112 */     super(metadata, ownPrefix);
/*     */   }
/*     */ 
/*     */   public void addContributor(String properName)
/*     */   {
/* 123 */     addQualifiedBagValue("contributor", properName);
/*     */   }
/*     */ 
/*     */   public void removeContributor(String properName)
/*     */   {
/* 128 */     removeUnqualifiedBagValue("contributor", properName);
/*     */   }
/*     */ 
/*     */   public void setCoverage(String text)
/*     */   {
/* 139 */     addProperty(createTextType("coverage", text));
/*     */   }
/*     */ 
/*     */   public void setCoverageProperty(TextType text)
/*     */   {
/* 150 */     addProperty(text);
/*     */   }
/*     */ 
/*     */   public void addCreator(String properName)
/*     */   {
/* 162 */     addUnqualifiedSequenceValue("creator", properName);
/*     */   }
/*     */ 
/*     */   public void removeCreator(String name)
/*     */   {
/* 167 */     removeUnqualifiedSequenceValue("creator", name);
/*     */   }
/*     */ 
/*     */   public void addDate(Calendar date)
/*     */   {
/* 178 */     addUnqualifiedSequenceDateValue("date", date);
/*     */   }
/*     */ 
/*     */   public void removeDate(Calendar date)
/*     */   {
/* 183 */     removeUnqualifiedSequenceDateValue("date", date);
/*     */   }
/*     */ 
/*     */   public void addDescription(String lang, String value)
/*     */   {
/* 196 */     setUnqualifiedLanguagePropertyValue("description", lang, value);
/*     */   }
/*     */ 
/*     */   public void setDescription(String value)
/*     */   {
/* 207 */     addDescription(null, value);
/*     */   }
/*     */ 
/*     */   @Deprecated
/*     */   public void setDescription(String language, String description)
/*     */   {
/* 218 */     addDescription(language, description);
/*     */   }
/*     */ 
/*     */   public void setFormat(String mimeType)
/*     */   {
/* 229 */     addProperty(createTextType("format", mimeType));
/*     */   }
/*     */ 
/*     */   public void setIdentifier(String text)
/*     */   {
/* 240 */     addProperty(createTextType("identifier", text));
/*     */   }
/*     */ 
/*     */   public void setIdentifierProperty(TextType text)
/*     */   {
/* 251 */     addProperty(text);
/*     */   }
/*     */ 
/*     */   public void addLanguage(String locale)
/*     */   {
/* 262 */     addQualifiedBagValue("language", locale);
/*     */   }
/*     */ 
/*     */   public void removeLanguage(String locale)
/*     */   {
/* 267 */     removeUnqualifiedBagValue("language", locale);
/*     */   }
/*     */ 
/*     */   public void addPublisher(String properName)
/*     */   {
/* 278 */     addQualifiedBagValue("publisher", properName);
/*     */   }
/*     */ 
/*     */   public void removePublisher(String name)
/*     */   {
/* 283 */     removeUnqualifiedBagValue("publisher", name);
/*     */   }
/*     */ 
/*     */   public void addRelation(String text)
/*     */   {
/* 294 */     addQualifiedBagValue("relation", text);
/*     */   }
/*     */ 
/*     */   public void removeRelation(String text)
/*     */   {
/* 299 */     removeUnqualifiedBagValue("relation", text);
/*     */   }
/*     */ 
/*     */   public void addRights(String lang, String value)
/*     */   {
/* 312 */     setUnqualifiedLanguagePropertyValue("rights", lang, value);
/*     */   }
/*     */ 
/*     */   @Deprecated
/*     */   public void setRights(String language, String rights)
/*     */   {
/* 323 */     addRights(language, rights);
/*     */   }
/*     */ 
/*     */   @Deprecated
/*     */   public void setRights(String rights)
/*     */   {
/* 334 */     addRights(null, rights);
/*     */   }
/*     */ 
/*     */   public void setSource(String text)
/*     */   {
/* 345 */     addProperty(createTextType("source", text));
/*     */   }
/*     */ 
/*     */   public void setSourceProperty(TextType text)
/*     */   {
/* 356 */     addProperty(text);
/*     */   }
/*     */ 
/*     */   public void setFormatProperty(MIMEType text)
/*     */   {
/* 367 */     addProperty(text);
/*     */   }
/*     */ 
/*     */   public void addSubject(String text)
/*     */   {
/* 378 */     addQualifiedBagValue("subject", text);
/*     */   }
/*     */ 
/*     */   public void removeSubject(String text)
/*     */   {
/* 383 */     removeUnqualifiedBagValue("subject", text);
/*     */   }
/*     */ 
/*     */   public void setTitle(String lang, String value)
/*     */   {
/* 396 */     setUnqualifiedLanguagePropertyValue("title", lang, value);
/*     */   }
/*     */ 
/*     */   public void setTitle(String value)
/*     */   {
/* 407 */     setTitle(null, value);
/*     */   }
/*     */ 
/*     */   public void addTitle(String lang, String value)
/*     */   {
/* 418 */     setTitle(lang, value);
/*     */   }
/*     */ 
/*     */   public void addType(String type)
/*     */   {
/* 429 */     addQualifiedBagValue("type", type);
/*     */   }
/*     */ 
/*     */   public ArrayProperty getContributorsProperty()
/*     */   {
/* 439 */     return (ArrayProperty)getProperty("contributor");
/*     */   }
/*     */ 
/*     */   public List<String> getContributors()
/*     */   {
/* 449 */     return getUnqualifiedBagValueList("contributor");
/*     */   }
/*     */ 
/*     */   public TextType getCoverageProperty()
/*     */   {
/* 460 */     return (TextType)getProperty("coverage");
/*     */   }
/*     */ 
/*     */   public String getCoverage()
/*     */   {
/* 470 */     TextType tt = (TextType)getProperty("coverage");
/* 471 */     return tt == null ? null : tt.getStringValue();
/*     */   }
/*     */ 
/*     */   public ArrayProperty getCreatorsProperty()
/*     */   {
/* 481 */     return (ArrayProperty)getProperty("creator");
/*     */   }
/*     */ 
/*     */   public List<String> getCreators()
/*     */   {
/* 491 */     return getUnqualifiedSequenceValueList("creator");
/*     */   }
/*     */ 
/*     */   public ArrayProperty getDatesProperty()
/*     */   {
/* 501 */     return (ArrayProperty)getProperty("date");
/*     */   }
/*     */ 
/*     */   public List<Calendar> getDates()
/*     */   {
/* 511 */     return getUnqualifiedSequenceDateValueList("date");
/*     */   }
/*     */ 
/*     */   public ArrayProperty getDescriptionProperty()
/*     */   {
/* 521 */     return (ArrayProperty)getProperty("description");
/*     */   }
/*     */ 
/*     */   public List<String> getDescriptionLanguages()
/*     */   {
/* 531 */     return getUnqualifiedLanguagePropertyLanguagesValue("description");
/*     */   }
/*     */ 
/*     */   public String getDescription(String lang)
/*     */   {
/* 543 */     return getUnqualifiedLanguagePropertyValue("description", lang);
/*     */   }
/*     */ 
/*     */   public String getDescription()
/*     */   {
/* 553 */     return getDescription(null);
/*     */   }
/*     */ 
/*     */   public TextType getFormatProperty()
/*     */   {
/* 563 */     return (TextType)getProperty("format");
/*     */   }
/*     */ 
/*     */   public String getFormat()
/*     */   {
/* 573 */     TextType tt = (TextType)getProperty("format");
/* 574 */     return tt == null ? null : tt.getStringValue();
/*     */   }
/*     */ 
/*     */   public TextType getIdentifierProperty()
/*     */   {
/* 584 */     return (TextType)getProperty("identifier");
/*     */   }
/*     */ 
/*     */   public String getIdentifier()
/*     */   {
/* 594 */     TextType tt = (TextType)getProperty("identifier");
/* 595 */     return tt == null ? null : tt.getStringValue();
/*     */   }
/*     */ 
/*     */   public ArrayProperty getLanguagesProperty()
/*     */   {
/* 605 */     return (ArrayProperty)getProperty("language");
/*     */   }
/*     */ 
/*     */   public List<String> getLanguages()
/*     */   {
/* 615 */     return getUnqualifiedBagValueList("language");
/*     */   }
/*     */ 
/*     */   public ArrayProperty getPublishersProperty()
/*     */   {
/* 625 */     return (ArrayProperty)getProperty("publisher");
/*     */   }
/*     */ 
/*     */   public List<String> getPublishers()
/*     */   {
/* 635 */     return getUnqualifiedBagValueList("publisher");
/*     */   }
/*     */ 
/*     */   public ArrayProperty getRelationsProperty()
/*     */   {
/* 645 */     return (ArrayProperty)getProperty("relation");
/*     */   }
/*     */ 
/*     */   public List<String> getRelations()
/*     */   {
/* 655 */     return getUnqualifiedBagValueList("relation");
/*     */   }
/*     */ 
/*     */   @Deprecated
/*     */   public List<String> getRelationships()
/*     */   {
/* 666 */     return getRelations();
/*     */   }
/*     */ 
/*     */   public ArrayProperty getRightsProperty()
/*     */   {
/* 676 */     return (ArrayProperty)getProperty("rights");
/*     */   }
/*     */ 
/*     */   public List<String> getRightsLanguages()
/*     */   {
/* 686 */     return getUnqualifiedLanguagePropertyLanguagesValue("rights");
/*     */   }
/*     */ 
/*     */   public String getRights(String lang)
/*     */   {
/* 698 */     return getUnqualifiedLanguagePropertyValue("rights", lang);
/*     */   }
/*     */ 
/*     */   public String getRights()
/*     */   {
/* 708 */     return getRights(null);
/*     */   }
/*     */ 
/*     */   public TextType getSourceProperty()
/*     */   {
/* 718 */     return (TextType)getProperty("source");
/*     */   }
/*     */ 
/*     */   public String getSource()
/*     */   {
/* 728 */     TextType tt = (TextType)getProperty("source");
/* 729 */     return tt == null ? null : tt.getStringValue();
/*     */   }
/*     */ 
/*     */   public ArrayProperty getSubjectsProperty()
/*     */   {
/* 739 */     return (ArrayProperty)getProperty("subject");
/*     */   }
/*     */ 
/*     */   public List<String> getSubjects()
/*     */   {
/* 749 */     return getUnqualifiedBagValueList("subject");
/*     */   }
/*     */ 
/*     */   public ArrayProperty getTitleProperty()
/*     */   {
/* 759 */     return (ArrayProperty)getProperty("title");
/*     */   }
/*     */ 
/*     */   public List<String> getTitleLanguages()
/*     */   {
/* 769 */     return getUnqualifiedLanguagePropertyLanguagesValue("title");
/*     */   }
/*     */ 
/*     */   public String getTitle(String lang)
/*     */   {
/* 781 */     return getUnqualifiedLanguagePropertyValue("title", lang);
/*     */   }
/*     */ 
/*     */   public String getTitle()
/*     */   {
/* 791 */     return getTitle(null);
/*     */   }
/*     */ 
/*     */   public ArrayProperty getTypesProperty()
/*     */   {
/* 801 */     return (ArrayProperty)getProperty("type");
/*     */   }
/*     */ 
/*     */   public List<String> getTypes()
/*     */   {
/* 811 */     return getUnqualifiedBagValueList("type");
/*     */   }
/*     */ 
/*     */   public void removeType(String type)
/*     */   {
/* 816 */     removeUnqualifiedBagValue("type", type);
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.xmpbox.schema.DublinCoreSchema
 * JD-Core Version:    0.6.2
 */