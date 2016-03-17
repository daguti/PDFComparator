/*     */ package org.apache.xmpbox.schema;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Calendar;
/*     */ import java.util.List;
/*     */ import org.apache.xmpbox.XMPMetadata;
/*     */ import org.apache.xmpbox.type.AbstractField;
/*     */ import org.apache.xmpbox.type.AgentNameType;
/*     */ import org.apache.xmpbox.type.ArrayProperty;
/*     */ import org.apache.xmpbox.type.BadFieldValueException;
/*     */ import org.apache.xmpbox.type.Cardinality;
/*     */ import org.apache.xmpbox.type.ComplexPropertyContainer;
/*     */ import org.apache.xmpbox.type.DateType;
/*     */ import org.apache.xmpbox.type.IntegerType;
/*     */ import org.apache.xmpbox.type.PropertyType;
/*     */ import org.apache.xmpbox.type.StructuredType;
/*     */ import org.apache.xmpbox.type.TextType;
/*     */ import org.apache.xmpbox.type.ThumbnailType;
/*     */ import org.apache.xmpbox.type.Types;
/*     */ import org.apache.xmpbox.type.URLType;
/*     */ 
/*     */ @StructuredType(preferedPrefix="xmp", namespace="http://ns.adobe.com/xap/1.0/")
/*     */ public class XMPBasicSchema extends XMPSchema
/*     */ {
/*     */ 
/*     */   @PropertyType(type=Types.XPath, card=Cardinality.Bag)
/*     */   public static final String ADVISORY = "Advisory";
/*     */ 
/*     */   @PropertyType(type=Types.URL, card=Cardinality.Simple)
/*     */   public static final String BASEURL = "BaseURL";
/*     */ 
/*     */   @PropertyType(type=Types.Date, card=Cardinality.Simple)
/*     */   public static final String CREATEDATE = "CreateDate";
/*     */ 
/*     */   @PropertyType(type=Types.AgentName, card=Cardinality.Simple)
/*     */   public static final String CREATORTOOL = "CreatorTool";
/*     */ 
/*     */   @PropertyType(type=Types.Text, card=Cardinality.Bag)
/*     */   public static final String IDENTIFIER = "Identifier";
/*     */ 
/*     */   @PropertyType(type=Types.Text, card=Cardinality.Simple)
/*     */   public static final String LABEL = "Label";
/*     */ 
/*     */   @PropertyType(type=Types.Date, card=Cardinality.Simple)
/*     */   public static final String METADATADATE = "MetadataDate";
/*     */ 
/*     */   @PropertyType(type=Types.Date, card=Cardinality.Simple)
/*     */   public static final String MODIFYDATE = "ModifyDate";
/*     */ 
/*     */   @PropertyType(type=Types.Text, card=Cardinality.Simple)
/*     */   public static final String NICKNAME = "Nickname";
/*     */ 
/*     */   @PropertyType(type=Types.Integer, card=Cardinality.Simple)
/*     */   public static final String RATING = "Rating";
/*     */ 
/*     */   @PropertyType(type=Types.Thumbnail, card=Cardinality.Alt)
/*     */   public static final String THUMBNAILS = "Thumbnails";
/*     */   private ArrayProperty altThumbs;
/*     */ 
/*     */   public XMPBasicSchema(XMPMetadata metadata)
/*     */   {
/*  96 */     super(metadata);
/*     */   }
/*     */ 
/*     */   public XMPBasicSchema(XMPMetadata metadata, String ownPrefix)
/*     */   {
/* 110 */     super(metadata, ownPrefix);
/*     */   }
/*     */ 
/*     */   public void addThumbnails(Integer height, Integer width, String format, String img)
/*     */   {
/* 128 */     if (this.altThumbs == null)
/*     */     {
/* 130 */       this.altThumbs = createArrayProperty("Thumbnails", Cardinality.Alt);
/* 131 */       addProperty(this.altThumbs);
/*     */     }
/* 133 */     ThumbnailType thumb = new ThumbnailType(getMetadata());
/* 134 */     thumb.setHeight(height);
/* 135 */     thumb.setWidth(width);
/* 136 */     thumb.setFormat(format);
/* 137 */     thumb.setImage(img);
/* 138 */     this.altThumbs.getContainer().addProperty(thumb);
/*     */   }
/*     */ 
/*     */   public void addAdvisory(String xpath)
/*     */   {
/* 149 */     addQualifiedBagValue("Advisory", xpath);
/*     */   }
/*     */ 
/*     */   public void removeAdvisory(String xpath)
/*     */   {
/* 154 */     removeUnqualifiedBagValue("Advisory", xpath);
/*     */   }
/*     */ 
/*     */   public void setBaseURL(String url)
/*     */   {
/* 165 */     URLType tt = (URLType)instanciateSimple("BaseURL", url);
/* 166 */     setBaseURLProperty(tt);
/*     */   }
/*     */ 
/*     */   public void setBaseURLProperty(URLType url)
/*     */   {
/* 177 */     addProperty(url);
/*     */   }
/*     */ 
/*     */   public void setCreateDate(Calendar date)
/*     */   {
/* 188 */     DateType tt = (DateType)instanciateSimple("CreateDate", date);
/* 189 */     setCreateDateProperty(tt);
/*     */   }
/*     */ 
/*     */   public void setCreateDateProperty(DateType date)
/*     */   {
/* 200 */     addProperty(date);
/*     */   }
/*     */ 
/*     */   public void setCreatorTool(String creatorTool)
/*     */   {
/* 211 */     AgentNameType tt = (AgentNameType)instanciateSimple("CreatorTool", creatorTool);
/* 212 */     setCreatorToolProperty(tt);
/*     */   }
/*     */ 
/*     */   public void setCreatorToolProperty(AgentNameType creatorTool)
/*     */   {
/* 223 */     addProperty(creatorTool);
/*     */   }
/*     */ 
/*     */   public void addIdentifier(String text)
/*     */   {
/* 234 */     addQualifiedBagValue("Identifier", text);
/*     */   }
/*     */ 
/*     */   public void removeIdentifier(String text)
/*     */   {
/* 239 */     removeUnqualifiedBagValue("Identifier", text);
/*     */   }
/*     */ 
/*     */   public void setLabel(String text)
/*     */   {
/* 250 */     TextType tt = (TextType)instanciateSimple("Label", text);
/* 251 */     setLabelProperty(tt);
/*     */   }
/*     */ 
/*     */   public void setLabelProperty(TextType text)
/*     */   {
/* 262 */     addProperty(text);
/*     */   }
/*     */ 
/*     */   public void setMetadataDate(Calendar date)
/*     */   {
/* 274 */     DateType tt = (DateType)instanciateSimple("MetadataDate", date);
/* 275 */     setMetadataDateProperty(tt);
/*     */   }
/*     */ 
/*     */   public void setMetadataDateProperty(DateType date)
/*     */   {
/* 286 */     addProperty(date);
/*     */   }
/*     */ 
/*     */   public void setModifyDate(Calendar date)
/*     */   {
/* 297 */     DateType tt = (DateType)instanciateSimple("ModifyDate", date);
/* 298 */     setModifyDateProperty(tt);
/*     */   }
/*     */ 
/*     */   public void setModifyDateProperty(DateType date)
/*     */   {
/* 309 */     addProperty(date);
/*     */   }
/*     */ 
/*     */   public void setNickname(String text)
/*     */   {
/* 320 */     TextType tt = (TextType)instanciateSimple("Nickname", text);
/* 321 */     setNicknameProperty(tt);
/*     */   }
/*     */ 
/*     */   public void setNicknameProperty(TextType text)
/*     */   {
/* 332 */     addProperty(text);
/*     */   }
/*     */ 
/*     */   public void setRating(Integer rate)
/*     */   {
/* 345 */     IntegerType tt = (IntegerType)instanciateSimple("Rating", rate);
/* 346 */     setRatingProperty(tt);
/*     */   }
/*     */ 
/*     */   public void setRatingProperty(IntegerType rate)
/*     */   {
/* 358 */     addProperty(rate);
/*     */   }
/*     */ 
/*     */   public ArrayProperty getAdvisoryProperty()
/*     */   {
/* 368 */     return (ArrayProperty)getProperty("Advisory");
/*     */   }
/*     */ 
/*     */   public List<String> getAdvisory()
/*     */   {
/* 378 */     return getUnqualifiedBagValueList("Advisory");
/*     */   }
/*     */ 
/*     */   @Deprecated
/*     */   public List<String> getAdvisories()
/*     */   {
/* 389 */     return getAdvisory();
/*     */   }
/*     */ 
/*     */   public TextType getBaseURLProperty()
/*     */   {
/* 399 */     return (TextType)getProperty("BaseURL");
/*     */   }
/*     */ 
/*     */   public String getBaseURL()
/*     */   {
/* 409 */     TextType tt = (TextType)getProperty("BaseURL");
/* 410 */     return tt == null ? null : tt.getStringValue();
/*     */   }
/*     */ 
/*     */   public DateType getCreateDateProperty()
/*     */   {
/* 420 */     return (DateType)getProperty("CreateDate");
/*     */   }
/*     */ 
/*     */   public Calendar getCreateDate()
/*     */   {
/* 430 */     DateType createDate = (DateType)getProperty("CreateDate");
/* 431 */     if (createDate != null)
/*     */     {
/* 433 */       return createDate.getValue();
/*     */     }
/* 435 */     return null;
/*     */   }
/*     */ 
/*     */   public TextType getCreatorToolProperty()
/*     */   {
/* 445 */     return (TextType)getProperty("CreatorTool");
/*     */   }
/*     */ 
/*     */   public String getCreatorTool()
/*     */   {
/* 455 */     TextType tt = (TextType)getProperty("CreatorTool");
/* 456 */     return tt == null ? null : tt.getStringValue();
/*     */   }
/*     */ 
/*     */   public ArrayProperty getIdentifiersProperty()
/*     */   {
/* 466 */     return (ArrayProperty)getProperty("Identifier");
/*     */   }
/*     */ 
/*     */   public List<String> getIdentifiers()
/*     */   {
/* 476 */     return getUnqualifiedBagValueList("Identifier");
/*     */   }
/*     */ 
/*     */   public TextType getLabelProperty()
/*     */   {
/* 486 */     return (TextType)getProperty("Label");
/*     */   }
/*     */ 
/*     */   public String getLabel()
/*     */   {
/* 496 */     TextType tt = (TextType)getProperty("Label");
/* 497 */     return tt == null ? null : tt.getStringValue();
/*     */   }
/*     */ 
/*     */   public DateType getMetadataDateProperty()
/*     */   {
/* 507 */     return (DateType)getProperty("MetadataDate");
/*     */   }
/*     */ 
/*     */   public Calendar getMetadataDate()
/*     */   {
/* 517 */     DateType dt = (DateType)getProperty("MetadataDate");
/* 518 */     return dt == null ? null : dt.getValue();
/*     */   }
/*     */ 
/*     */   public DateType getModifyDateProperty()
/*     */   {
/* 528 */     return (DateType)getProperty("ModifyDate");
/*     */   }
/*     */ 
/*     */   public Calendar getModifyDate()
/*     */   {
/* 538 */     DateType modifyDate = (DateType)getProperty("ModifyDate");
/* 539 */     if (modifyDate != null)
/*     */     {
/* 541 */       return modifyDate.getValue();
/*     */     }
/* 543 */     return null;
/*     */   }
/*     */ 
/*     */   public TextType getNicknameProperty()
/*     */   {
/* 554 */     return (TextType)getProperty("Nickname");
/*     */   }
/*     */ 
/*     */   public String getNickname()
/*     */   {
/* 564 */     TextType tt = (TextType)getProperty("Nickname");
/* 565 */     return tt == null ? null : tt.getStringValue();
/*     */   }
/*     */ 
/*     */   public IntegerType getRatingProperty()
/*     */   {
/* 575 */     return (IntegerType)getProperty("Rating");
/*     */   }
/*     */ 
/*     */   public Integer getRating()
/*     */   {
/* 585 */     IntegerType it = (IntegerType)getProperty("Rating");
/* 586 */     return it == null ? null : it.getValue();
/*     */   }
/*     */ 
/*     */   public List<ThumbnailType> getThumbnailsProperty()
/*     */     throws BadFieldValueException
/*     */   {
/* 598 */     List tmp = getUnqualifiedArrayList("Thumbnails");
/* 599 */     if (tmp != null)
/*     */     {
/* 601 */       List thumbs = new ArrayList();
/* 602 */       for (AbstractField abstractField : tmp)
/*     */       {
/* 604 */         if ((abstractField instanceof ThumbnailType))
/*     */         {
/* 606 */           thumbs.add((ThumbnailType)abstractField);
/*     */         }
/*     */         else
/*     */         {
/* 610 */           throw new BadFieldValueException("Thumbnail expected and " + abstractField.getClass().getName() + " found.");
/*     */         }
/*     */       }
/*     */ 
/* 614 */       return thumbs;
/*     */     }
/* 616 */     return null;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.xmpbox.schema.XMPBasicSchema
 * JD-Core Version:    0.6.2
 */