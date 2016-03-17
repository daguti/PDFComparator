/*     */ package org.apache.xmpbox.schema;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import org.apache.xmpbox.XMPMetadata;
/*     */ import org.apache.xmpbox.type.AbstractField;
/*     */ import org.apache.xmpbox.type.ArrayProperty;
/*     */ import org.apache.xmpbox.type.BadFieldValueException;
/*     */ import org.apache.xmpbox.type.Cardinality;
/*     */ import org.apache.xmpbox.type.ComplexPropertyContainer;
/*     */ import org.apache.xmpbox.type.DateType;
/*     */ import org.apache.xmpbox.type.IntegerType;
/*     */ import org.apache.xmpbox.type.LayerType;
/*     */ import org.apache.xmpbox.type.ProperNameType;
/*     */ import org.apache.xmpbox.type.PropertyType;
/*     */ import org.apache.xmpbox.type.StructuredType;
/*     */ import org.apache.xmpbox.type.TextType;
/*     */ import org.apache.xmpbox.type.Types;
/*     */ import org.apache.xmpbox.type.URIType;
/*     */ 
/*     */ @StructuredType(preferedPrefix="photoshop", namespace="http://ns.adobe.com/photoshop/1.0/")
/*     */ public class PhotoshopSchema extends XMPSchema
/*     */ {
/*     */   public static final String PREFERED_PREFIX = "photoshop";
/*     */   public static final String PHOTOSHOPURI = "http://ns.adobe.com/photoshop/1.0/";
/*     */ 
/*     */   @PropertyType(type=Types.URI, card=Cardinality.Simple)
/*     */   public static final String ANCESTORID = "AncestorID";
/*     */ 
/*     */   @PropertyType(type=Types.Text, card=Cardinality.Simple)
/*     */   public static final String AUTHORS_POSITION = "AuthorsPosition";
/*     */ 
/*     */   @PropertyType(type=Types.ProperName, card=Cardinality.Simple)
/*     */   public static final String CAPTION_WRITER = "CaptionWriter";
/*     */ 
/*     */   @PropertyType(type=Types.Text, card=Cardinality.Simple)
/*     */   public static final String CATEGORY = "Category";
/*     */ 
/*     */   @PropertyType(type=Types.Text, card=Cardinality.Simple)
/*     */   public static final String CITY = "City";
/*     */ 
/*     */   @PropertyType(type=Types.Integer, card=Cardinality.Simple)
/*     */   public static final String COLOR_MODE = "ColorMode";
/*     */ 
/*     */   @PropertyType(type=Types.Text, card=Cardinality.Simple)
/*     */   public static final String COUNTRY = "Country";
/*     */ 
/*     */   @PropertyType(type=Types.Text, card=Cardinality.Simple)
/*     */   public static final String CREDIT = "Credit";
/*     */ 
/*     */   @PropertyType(type=Types.Date, card=Cardinality.Simple)
/*     */   public static final String DATE_CREATED = "DateCreated";
/*     */ 
/*     */   @PropertyType(type=Types.Text, card=Cardinality.Bag)
/*     */   public static final String DOCUMENT_ANCESTORS = "DocumentAncestors";
/*     */ 
/*     */   @PropertyType(type=Types.Text, card=Cardinality.Simple)
/*     */   public static final String HEADLINE = "Headline";
/*     */ 
/*     */   @PropertyType(type=Types.Text, card=Cardinality.Simple)
/*     */   public static final String HISTORY = "History";
/*     */ 
/*     */   @PropertyType(type=Types.Text, card=Cardinality.Simple)
/*     */   public static final String ICC_PROFILE = "ICCProfile";
/*     */ 
/*     */   @PropertyType(type=Types.Text, card=Cardinality.Simple)
/*     */   public static final String INSTRUCTIONS = "Instructions";
/*     */ 
/*     */   @PropertyType(type=Types.Text, card=Cardinality.Simple)
/*     */   public static final String SOURCE = "Source";
/*     */ 
/*     */   @PropertyType(type=Types.Text, card=Cardinality.Simple)
/*     */   public static final String STATE = "State";
/*     */ 
/*     */   @PropertyType(type=Types.Text, card=Cardinality.Bag)
/*     */   public static final String SUPPLEMENTAL_CATEGORIES = "SupplementalCategories";
/*     */ 
/*     */   @PropertyType(type=Types.Layer, card=Cardinality.Seq)
/*     */   public static final String TEXT_LAYERS = "TextLayers";
/*     */   private ArrayProperty seqLayer;
/*     */ 
/*     */   @PropertyType(type=Types.Text, card=Cardinality.Simple)
/*     */   public static final String TRANSMISSION_REFERENCE = "TransmissionReference";
/*     */ 
/*     */   @PropertyType(type=Types.Integer, card=Cardinality.Simple)
/*     */   public static final String URGENCY = "Urgency";
/*     */ 
/*     */   public PhotoshopSchema(XMPMetadata metadata)
/*     */   {
/*  48 */     super(metadata);
/*     */   }
/*     */ 
/*     */   public PhotoshopSchema(XMPMetadata metadata, String ownPrefix)
/*     */   {
/*  53 */     super(metadata, ownPrefix);
/*     */   }
/*     */ 
/*     */   public URIType getAncestorIDProperty()
/*     */   {
/* 124 */     return (URIType)getProperty("AncestorID");
/*     */   }
/*     */ 
/*     */   public String getAncestorID()
/*     */   {
/* 129 */     TextType tt = (TextType)getProperty("AncestorID");
/* 130 */     return tt == null ? null : tt.getStringValue();
/*     */   }
/*     */ 
/*     */   public void setAncestorID(String text)
/*     */   {
/* 135 */     URIType tt = (URIType)instanciateSimple("AncestorID", text);
/* 136 */     setAncestorIDProperty(tt);
/*     */   }
/*     */ 
/*     */   public void setAncestorIDProperty(URIType text)
/*     */   {
/* 141 */     addProperty(text);
/*     */   }
/*     */ 
/*     */   public TextType getAuthorsPositionProperty()
/*     */   {
/* 146 */     return (TextType)getProperty("AuthorsPosition");
/*     */   }
/*     */ 
/*     */   public String getAuthorsPosition()
/*     */   {
/* 151 */     TextType tt = (TextType)getProperty("AuthorsPosition");
/* 152 */     return tt == null ? null : tt.getStringValue();
/*     */   }
/*     */ 
/*     */   public void setAuthorsPosition(String text)
/*     */   {
/* 157 */     TextType tt = (TextType)instanciateSimple("AuthorsPosition", text);
/* 158 */     setAuthorsPositionProperty(tt);
/*     */   }
/*     */ 
/*     */   public void setAuthorsPositionProperty(TextType text)
/*     */   {
/* 163 */     addProperty(text);
/*     */   }
/*     */ 
/*     */   public TextType getCaptionWriterProperty()
/*     */   {
/* 168 */     return (TextType)getProperty("CaptionWriter");
/*     */   }
/*     */ 
/*     */   public String getCaptionWriter()
/*     */   {
/* 173 */     TextType tt = (TextType)getProperty("CaptionWriter");
/* 174 */     return tt == null ? null : tt.getStringValue();
/*     */   }
/*     */ 
/*     */   public void setCaptionWriter(String text)
/*     */   {
/* 179 */     ProperNameType tt = (ProperNameType)instanciateSimple("CaptionWriter", text);
/* 180 */     setCaptionWriterProperty(tt);
/*     */   }
/*     */ 
/*     */   public void setCaptionWriterProperty(ProperNameType text)
/*     */   {
/* 185 */     addProperty(text);
/*     */   }
/*     */ 
/*     */   public TextType getCategoryProperty()
/*     */   {
/* 190 */     return (TextType)getProperty("Category");
/*     */   }
/*     */ 
/*     */   public String getCategory()
/*     */   {
/* 195 */     TextType tt = (TextType)getProperty("Category");
/* 196 */     return tt == null ? null : tt.getStringValue();
/*     */   }
/*     */ 
/*     */   public void setCategory(String text)
/*     */   {
/* 201 */     TextType tt = (TextType)instanciateSimple("Category", text);
/* 202 */     setCategoryProperty(tt);
/*     */   }
/*     */ 
/*     */   public void setCategoryProperty(TextType text)
/*     */   {
/* 207 */     addProperty(text);
/*     */   }
/*     */ 
/*     */   public TextType getCityProperty()
/*     */   {
/* 212 */     return (TextType)getProperty("City");
/*     */   }
/*     */ 
/*     */   public String getCity()
/*     */   {
/* 217 */     TextType tt = (TextType)getProperty("City");
/* 218 */     return tt == null ? null : tt.getStringValue();
/*     */   }
/*     */ 
/*     */   public void setCity(String text)
/*     */   {
/* 223 */     TextType tt = (TextType)instanciateSimple("City", text);
/* 224 */     setCityProperty(tt);
/*     */   }
/*     */ 
/*     */   public void setCityProperty(TextType text)
/*     */   {
/* 229 */     addProperty(text);
/*     */   }
/*     */ 
/*     */   public IntegerType getColorModeProperty()
/*     */   {
/* 234 */     return (IntegerType)getProperty("ColorMode");
/*     */   }
/*     */ 
/*     */   public Integer getColorMode()
/*     */   {
/* 239 */     IntegerType tt = (IntegerType)getProperty("ColorMode");
/* 240 */     return tt == null ? null : tt.getValue();
/*     */   }
/*     */ 
/*     */   public void setColorMode(String text)
/*     */   {
/* 245 */     IntegerType tt = (IntegerType)instanciateSimple("ColorMode", text);
/* 246 */     setColorModeProperty(tt);
/*     */   }
/*     */ 
/*     */   public void setColorModeProperty(IntegerType text)
/*     */   {
/* 251 */     addProperty(text);
/*     */   }
/*     */ 
/*     */   public TextType getCountryProperty()
/*     */   {
/* 256 */     return (TextType)getProperty("Country");
/*     */   }
/*     */ 
/*     */   public String getCountry()
/*     */   {
/* 261 */     TextType tt = (TextType)getProperty("Country");
/* 262 */     return tt == null ? null : tt.getStringValue();
/*     */   }
/*     */ 
/*     */   public void setCountry(String text)
/*     */   {
/* 267 */     TextType tt = (TextType)instanciateSimple("Country", text);
/* 268 */     setCountryProperty(tt);
/*     */   }
/*     */ 
/*     */   public void setCountryProperty(TextType text)
/*     */   {
/* 273 */     addProperty(text);
/*     */   }
/*     */ 
/*     */   public TextType getCreditProperty()
/*     */   {
/* 278 */     return (TextType)getProperty("Credit");
/*     */   }
/*     */ 
/*     */   public String getCredit()
/*     */   {
/* 283 */     TextType tt = (TextType)getProperty("Credit");
/* 284 */     return tt == null ? null : tt.getStringValue();
/*     */   }
/*     */ 
/*     */   public void setCredit(String text)
/*     */   {
/* 289 */     TextType tt = (TextType)instanciateSimple("Credit", text);
/* 290 */     setCreditProperty(tt);
/*     */   }
/*     */ 
/*     */   public void setCreditProperty(TextType text)
/*     */   {
/* 295 */     addProperty(text);
/*     */   }
/*     */ 
/*     */   public DateType getDateCreatedProperty()
/*     */   {
/* 300 */     return (DateType)getProperty("DateCreated");
/*     */   }
/*     */ 
/*     */   public String getDateCreated()
/*     */   {
/* 305 */     TextType tt = (TextType)getProperty("DateCreated");
/* 306 */     return tt == null ? null : tt.getStringValue();
/*     */   }
/*     */ 
/*     */   public void setDateCreated(String text)
/*     */   {
/* 311 */     DateType tt = (DateType)instanciateSimple("DateCreated", text);
/* 312 */     setDateCreatedProperty(tt);
/*     */   }
/*     */ 
/*     */   public void setDateCreatedProperty(DateType text)
/*     */   {
/* 317 */     addProperty(text);
/*     */   }
/*     */ 
/*     */   public void addDocumentAncestors(String text)
/*     */   {
/* 322 */     addQualifiedBagValue("DocumentAncestors", text);
/*     */   }
/*     */ 
/*     */   public ArrayProperty getDocumentAncestorsProperty()
/*     */   {
/* 327 */     return (ArrayProperty)getProperty("DocumentAncestors");
/*     */   }
/*     */ 
/*     */   public List<String> getDocumentAncestors()
/*     */   {
/* 332 */     return getUnqualifiedBagValueList("DocumentAncestors");
/*     */   }
/*     */ 
/*     */   public TextType getHeadlineProperty()
/*     */   {
/* 337 */     return (TextType)getProperty("Headline");
/*     */   }
/*     */ 
/*     */   public String getHeadline()
/*     */   {
/* 342 */     TextType tt = (TextType)getProperty("Headline");
/* 343 */     return tt == null ? null : tt.getStringValue();
/*     */   }
/*     */ 
/*     */   public void setHeadline(String text)
/*     */   {
/* 348 */     TextType tt = (TextType)instanciateSimple("Headline", text);
/* 349 */     setHeadlineProperty(tt);
/*     */   }
/*     */ 
/*     */   public void setHeadlineProperty(TextType text)
/*     */   {
/* 354 */     addProperty(text);
/*     */   }
/*     */ 
/*     */   public TextType getHistoryProperty()
/*     */   {
/* 359 */     return (TextType)getProperty("History");
/*     */   }
/*     */ 
/*     */   public String getHistory()
/*     */   {
/* 364 */     TextType tt = (TextType)getProperty("History");
/* 365 */     return tt == null ? null : tt.getStringValue();
/*     */   }
/*     */ 
/*     */   public void setHistory(String text)
/*     */   {
/* 370 */     TextType tt = (TextType)instanciateSimple("History", text);
/* 371 */     setHistoryProperty(tt);
/*     */   }
/*     */ 
/*     */   public void setHistoryProperty(TextType text)
/*     */   {
/* 376 */     addProperty(text);
/*     */   }
/*     */ 
/*     */   public TextType getICCProfileProperty()
/*     */   {
/* 381 */     return (TextType)getProperty("ICCProfile");
/*     */   }
/*     */ 
/*     */   public String getICCProfile()
/*     */   {
/* 386 */     TextType tt = (TextType)getProperty("ICCProfile");
/* 387 */     return tt == null ? null : tt.getStringValue();
/*     */   }
/*     */ 
/*     */   public void setICCProfile(String text)
/*     */   {
/* 392 */     TextType tt = (TextType)instanciateSimple("ICCProfile", text);
/* 393 */     setICCProfileProperty(tt);
/*     */   }
/*     */ 
/*     */   public void setICCProfileProperty(TextType text)
/*     */   {
/* 398 */     addProperty(text);
/*     */   }
/*     */ 
/*     */   public TextType getInstructionsProperty()
/*     */   {
/* 403 */     return (TextType)getProperty("Instructions");
/*     */   }
/*     */ 
/*     */   public String getInstructions()
/*     */   {
/* 408 */     TextType tt = (TextType)getProperty("Instructions");
/* 409 */     return tt == null ? null : tt.getStringValue();
/*     */   }
/*     */ 
/*     */   public void setInstructions(String text)
/*     */   {
/* 414 */     TextType tt = (TextType)instanciateSimple("Instructions", text);
/* 415 */     setInstructionsProperty(tt);
/*     */   }
/*     */ 
/*     */   public void setInstructionsProperty(TextType text)
/*     */   {
/* 421 */     addProperty(text);
/*     */   }
/*     */ 
/*     */   public TextType getSourceProperty()
/*     */   {
/* 426 */     return (TextType)getProperty("Source");
/*     */   }
/*     */ 
/*     */   public String getSource()
/*     */   {
/* 431 */     TextType tt = (TextType)getProperty("Source");
/* 432 */     return tt == null ? null : tt.getStringValue();
/*     */   }
/*     */ 
/*     */   public void setSource(String text)
/*     */   {
/* 437 */     TextType source = (TextType)instanciateSimple("Source", text);
/* 438 */     setSourceProperty(source);
/*     */   }
/*     */ 
/*     */   public void setSourceProperty(TextType text)
/*     */   {
/* 443 */     addProperty(text);
/*     */   }
/*     */ 
/*     */   public TextType getStateProperty()
/*     */   {
/* 448 */     return (TextType)getProperty("State");
/*     */   }
/*     */ 
/*     */   public String getState()
/*     */   {
/* 453 */     TextType tt = (TextType)getProperty("State");
/* 454 */     return tt == null ? null : tt.getStringValue();
/*     */   }
/*     */ 
/*     */   public void setState(String text)
/*     */   {
/* 459 */     TextType tt = (TextType)instanciateSimple("State", text);
/* 460 */     setStateProperty(tt);
/*     */   }
/*     */ 
/*     */   public void setStateProperty(TextType text)
/*     */   {
/* 465 */     addProperty(text);
/*     */   }
/*     */ 
/*     */   public void addSupplementalCategories(String text)
/*     */   {
/* 470 */     addQualifiedBagValue("SupplementalCategories", text);
/*     */   }
/*     */ 
/*     */   public void removeSupplementalCategories(String text)
/*     */   {
/* 475 */     removeUnqualifiedBagValue("SupplementalCategories", text);
/*     */   }
/*     */ 
/*     */   public void addSupplementalCategory(String s)
/*     */   {
/* 486 */     addSupplementalCategories(s);
/*     */   }
/*     */ 
/*     */   public void removeSupplementalCategory(String text)
/*     */   {
/* 491 */     removeSupplementalCategories(text);
/*     */   }
/*     */ 
/*     */   public ArrayProperty getSupplementalCategoriesProperty()
/*     */   {
/* 496 */     return (ArrayProperty)getProperty("SupplementalCategories");
/*     */   }
/*     */ 
/*     */   public List<String> getSupplementalCategories()
/*     */   {
/* 501 */     return getUnqualifiedBagValueList("SupplementalCategories");
/*     */   }
/*     */ 
/*     */   public void addTextLayers(String layerName, String layerText)
/*     */   {
/* 506 */     if (this.seqLayer == null)
/*     */     {
/* 508 */       this.seqLayer = createArrayProperty("TextLayers", Cardinality.Seq);
/* 509 */       addProperty(this.seqLayer);
/*     */     }
/* 511 */     LayerType layer = new LayerType(getMetadata());
/* 512 */     layer.setLayerName(layerName);
/* 513 */     layer.setLayerText(layerText);
/* 514 */     this.seqLayer.getContainer().addProperty(layer);
/*     */   }
/*     */ 
/*     */   public List<LayerType> getTextLayers() throws BadFieldValueException
/*     */   {
/* 519 */     List tmp = getUnqualifiedArrayList("TextLayers");
/* 520 */     if (tmp != null)
/*     */     {
/* 522 */       List layers = new ArrayList();
/* 523 */       for (AbstractField abstractField : tmp)
/*     */       {
/* 525 */         if ((abstractField instanceof LayerType))
/*     */         {
/* 527 */           layers.add((LayerType)abstractField);
/*     */         }
/*     */         else
/*     */         {
/* 531 */           throw new BadFieldValueException("Layer expected and " + abstractField.getClass().getName() + " found.");
/*     */         }
/*     */       }
/*     */ 
/* 535 */       return layers;
/*     */     }
/* 537 */     return null;
/*     */   }
/*     */ 
/*     */   public TextType getTransmissionReferenceProperty()
/*     */   {
/* 543 */     return (TextType)getProperty("TransmissionReference");
/*     */   }
/*     */ 
/*     */   public String getTransmissionReference()
/*     */   {
/* 548 */     TextType tt = (TextType)getProperty("TransmissionReference");
/* 549 */     return tt == null ? null : tt.getStringValue();
/*     */   }
/*     */ 
/*     */   public void setTransmissionReference(String text)
/*     */   {
/* 554 */     TextType tt = (TextType)instanciateSimple("TransmissionReference", text);
/* 555 */     setTransmissionReferenceProperty(tt);
/*     */   }
/*     */ 
/*     */   public void setTransmissionReferenceProperty(TextType text)
/*     */   {
/* 560 */     addProperty(text);
/*     */   }
/*     */ 
/*     */   public IntegerType getUrgencyProperty()
/*     */   {
/* 565 */     return (IntegerType)getProperty("Urgency");
/*     */   }
/*     */ 
/*     */   public Integer getUrgency()
/*     */   {
/* 570 */     IntegerType tt = (IntegerType)getProperty("Urgency");
/* 571 */     return tt == null ? null : tt.getValue();
/*     */   }
/*     */ 
/*     */   public void setUrgency(String s)
/*     */   {
/* 576 */     IntegerType tt = (IntegerType)instanciateSimple("Urgency", s);
/* 577 */     setUrgencyProperty(tt);
/*     */   }
/*     */ 
/*     */   public void setUrgency(Integer s)
/*     */   {
/* 582 */     IntegerType tt = (IntegerType)instanciateSimple("Urgency", s);
/* 583 */     setUrgencyProperty(tt);
/*     */   }
/*     */ 
/*     */   public void setUrgencyProperty(IntegerType text)
/*     */   {
/* 588 */     addProperty(text);
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.xmpbox.schema.PhotoshopSchema
 * JD-Core Version:    0.6.2
 */