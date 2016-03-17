/*     */ package org.apache.xmpbox.type;
/*     */ 
/*     */ import java.util.Calendar;
/*     */ import java.util.List;
/*     */ import org.apache.xmpbox.XMPMetadata;
/*     */ 
/*     */ @StructuredType(preferedPrefix="stRef", namespace="http://ns.adobe.com/xap/1.0/sType/ResourceRef#")
/*     */ public class ResourceRefType extends AbstractStructuredType
/*     */ {
/*     */ 
/*     */   @PropertyType(type=Types.URI, card=Cardinality.Simple)
/*     */   public static final String DOCUMENT_ID = "documentID";
/*     */ 
/*     */   @PropertyType(type=Types.URI, card=Cardinality.Simple)
/*     */   public static final String FILE_PATH = "filePath";
/*     */ 
/*     */   @PropertyType(type=Types.URI, card=Cardinality.Simple)
/*     */   public static final String INSTANCE_ID = "instanceID";
/*     */ 
/*     */   @PropertyType(type=Types.Date, card=Cardinality.Simple)
/*     */   public static final String LAST_MODIFY_DATE = "lastModifyDate";
/*     */ 
/*     */   @PropertyType(type=Types.URI, card=Cardinality.Simple)
/*     */   public static final String MANAGE_TO = "manageTo";
/*     */ 
/*     */   @PropertyType(type=Types.URI, card=Cardinality.Simple)
/*     */   public static final String MANAGE_UI = "manageUI";
/*     */ 
/*     */   @PropertyType(type=Types.AgentName, card=Cardinality.Simple)
/*     */   public static final String MANAGER = "manager";
/*     */ 
/*     */   @PropertyType(type=Types.Text, card=Cardinality.Simple)
/*     */   public static final String MANAGER_VARIANT = "managerVariant";
/*     */ 
/*     */   @PropertyType(type=Types.Text, card=Cardinality.Simple)
/*     */   public static final String PART_MAPPING = "partMapping";
/*     */ 
/*     */   @PropertyType(type=Types.Text, card=Cardinality.Simple)
/*     */   public static final String RENDITION_PARAMS = "renditionParams";
/*     */ 
/*     */   @PropertyType(type=Types.Text, card=Cardinality.Simple)
/*     */   public static final String VERSION_ID = "versionID";
/*     */ 
/*     */   @PropertyType(type=Types.Choice, card=Cardinality.Simple)
/*     */   public static final String MASK_MARKERS = "maskMarkers";
/*     */ 
/*     */   @PropertyType(type=Types.RenditionClass, card=Cardinality.Simple)
/*     */   public static final String RENDITION_CLASS = "renditionClass";
/*     */ 
/*     */   @PropertyType(type=Types.Part, card=Cardinality.Simple)
/*     */   public static final String FROM_PART = "fromPart";
/*     */ 
/*     */   @PropertyType(type=Types.Part, card=Cardinality.Simple)
/*     */   public static final String TO_PART = "toPart";
/*     */   public static final String ALTERNATE_PATHS = "alternatePaths";
/*     */ 
/*     */   public ResourceRefType(XMPMetadata metadata)
/*     */   {
/*  87 */     super(metadata);
/*  88 */     addNamespace(getNamespace(), getPreferedPrefix());
/*     */   }
/*     */ 
/*     */   public String getDocumentID()
/*     */   {
/*  94 */     TextType absProp = (TextType)getFirstEquivalentProperty("documentID", URIType.class);
/*  95 */     if (absProp != null)
/*     */     {
/*  97 */       return absProp.getStringValue();
/*     */     }
/*     */ 
/* 101 */     return null;
/*     */   }
/*     */ 
/*     */   public void setDocumentID(String value)
/*     */   {
/* 107 */     addSimpleProperty("documentID", value);
/*     */   }
/*     */ 
/*     */   public String getFilePath()
/*     */   {
/* 112 */     TextType absProp = (TextType)getFirstEquivalentProperty("filePath", URIType.class);
/* 113 */     if (absProp != null)
/*     */     {
/* 115 */       return absProp.getStringValue();
/*     */     }
/*     */ 
/* 119 */     return null;
/*     */   }
/*     */ 
/*     */   public void setFilePath(String value)
/*     */   {
/* 125 */     addSimpleProperty("filePath", value);
/*     */   }
/*     */ 
/*     */   public String getInstanceID()
/*     */   {
/* 130 */     TextType absProp = (TextType)getFirstEquivalentProperty("instanceID", URIType.class);
/* 131 */     if (absProp != null)
/*     */     {
/* 133 */       return absProp.getStringValue();
/*     */     }
/*     */ 
/* 137 */     return null;
/*     */   }
/*     */ 
/*     */   public void setInstanceID(String value)
/*     */   {
/* 143 */     addSimpleProperty("instanceID", value);
/*     */   }
/*     */ 
/*     */   public Calendar getLastModifyDate()
/*     */   {
/* 148 */     DateType absProp = (DateType)getFirstEquivalentProperty("lastModifyDate", DateType.class);
/* 149 */     if (absProp != null)
/*     */     {
/* 151 */       return absProp.getValue();
/*     */     }
/*     */ 
/* 155 */     return null;
/*     */   }
/*     */ 
/*     */   public void setLastModifyDate(Calendar value)
/*     */   {
/* 161 */     addSimpleProperty("lastModifyDate", value);
/*     */   }
/*     */ 
/*     */   public String getManageUI()
/*     */   {
/* 166 */     TextType absProp = (TextType)getFirstEquivalentProperty("manageUI", URIType.class);
/* 167 */     if (absProp != null)
/*     */     {
/* 169 */       return absProp.getStringValue();
/*     */     }
/*     */ 
/* 173 */     return null;
/*     */   }
/*     */ 
/*     */   public void setManageUI(String value)
/*     */   {
/* 179 */     addSimpleProperty("manageUI", value);
/*     */   }
/*     */ 
/*     */   public String getManageTo()
/*     */   {
/* 184 */     TextType absProp = (TextType)getFirstEquivalentProperty("manageTo", URIType.class);
/* 185 */     if (absProp != null)
/*     */     {
/* 187 */       return absProp.getStringValue();
/*     */     }
/*     */ 
/* 191 */     return null;
/*     */   }
/*     */ 
/*     */   public void setManageTo(String value)
/*     */   {
/* 197 */     addSimpleProperty("manageTo", value);
/*     */   }
/*     */ 
/*     */   public String getManager()
/*     */   {
/* 202 */     TextType absProp = (TextType)getFirstEquivalentProperty("manager", AgentNameType.class);
/* 203 */     if (absProp != null)
/*     */     {
/* 205 */       return absProp.getStringValue();
/*     */     }
/*     */ 
/* 209 */     return null;
/*     */   }
/*     */ 
/*     */   public void setManager(String value)
/*     */   {
/* 215 */     addSimpleProperty("manager", value);
/*     */   }
/*     */ 
/*     */   public String getManagerVariant()
/*     */   {
/* 220 */     TextType absProp = (TextType)getFirstEquivalentProperty("managerVariant", TextType.class);
/* 221 */     if (absProp != null)
/*     */     {
/* 223 */       return absProp.getStringValue();
/*     */     }
/*     */ 
/* 227 */     return null;
/*     */   }
/*     */ 
/*     */   public void setManagerVariant(String value)
/*     */   {
/* 233 */     addSimpleProperty("managerVariant", value);
/*     */   }
/*     */ 
/*     */   public String getPartMapping()
/*     */   {
/* 238 */     TextType absProp = (TextType)getFirstEquivalentProperty("partMapping", TextType.class);
/* 239 */     if (absProp != null)
/*     */     {
/* 241 */       return absProp.getStringValue();
/*     */     }
/*     */ 
/* 245 */     return null;
/*     */   }
/*     */ 
/*     */   public void setPartMapping(String value)
/*     */   {
/* 251 */     addSimpleProperty("partMapping", value);
/*     */   }
/*     */ 
/*     */   public String getRenditionParams()
/*     */   {
/* 256 */     TextType absProp = (TextType)getFirstEquivalentProperty("renditionParams", TextType.class);
/* 257 */     if (absProp != null)
/*     */     {
/* 259 */       return absProp.getStringValue();
/*     */     }
/*     */ 
/* 263 */     return null;
/*     */   }
/*     */ 
/*     */   public void setRenditionParams(String value)
/*     */   {
/* 269 */     addSimpleProperty("renditionParams", value);
/*     */   }
/*     */ 
/*     */   public String getVersionID()
/*     */   {
/* 274 */     TextType absProp = (TextType)getFirstEquivalentProperty("versionID", TextType.class);
/* 275 */     if (absProp != null)
/*     */     {
/* 277 */       return absProp.getStringValue();
/*     */     }
/*     */ 
/* 281 */     return null;
/*     */   }
/*     */ 
/*     */   public void setVersionID(String value)
/*     */   {
/* 287 */     addSimpleProperty("versionID", value);
/*     */   }
/*     */ 
/*     */   public String getMaskMarkers()
/*     */   {
/* 292 */     TextType absProp = (TextType)getFirstEquivalentProperty("maskMarkers", ChoiceType.class);
/* 293 */     if (absProp != null)
/*     */     {
/* 295 */       return absProp.getStringValue();
/*     */     }
/*     */ 
/* 299 */     return null;
/*     */   }
/*     */ 
/*     */   public void setMaskMarkers(String value)
/*     */   {
/* 305 */     addSimpleProperty("maskMarkers", value);
/*     */   }
/*     */ 
/*     */   public String getRenditionClass()
/*     */   {
/* 310 */     TextType absProp = (TextType)getFirstEquivalentProperty("renditionClass", RenditionClassType.class);
/* 311 */     if (absProp != null)
/*     */     {
/* 313 */       return absProp.getStringValue();
/*     */     }
/*     */ 
/* 317 */     return null;
/*     */   }
/*     */ 
/*     */   public void setRenditionClass(String value)
/*     */   {
/* 323 */     addSimpleProperty("renditionClass", value);
/*     */   }
/*     */ 
/*     */   public String getFromPart()
/*     */   {
/* 328 */     TextType absProp = (TextType)getFirstEquivalentProperty("fromPart", PartType.class);
/* 329 */     if (absProp != null)
/*     */     {
/* 331 */       return absProp.getStringValue();
/*     */     }
/*     */ 
/* 335 */     return null;
/*     */   }
/*     */ 
/*     */   public void setFromPart(String value)
/*     */   {
/* 341 */     addSimpleProperty("fromPart", value);
/*     */   }
/*     */ 
/*     */   public String getToPart()
/*     */   {
/* 346 */     TextType absProp = (TextType)getFirstEquivalentProperty("toPart", PartType.class);
/* 347 */     if (absProp != null)
/*     */     {
/* 349 */       return absProp.getStringValue();
/*     */     }
/*     */ 
/* 353 */     return null;
/*     */   }
/*     */ 
/*     */   public void setToPart(String value)
/*     */   {
/* 359 */     addSimpleProperty("toPart", value);
/*     */   }
/*     */ 
/*     */   public void addAlternatePath(String value)
/*     */   {
/* 364 */     ArrayProperty seq = (ArrayProperty)getFirstEquivalentProperty("alternatePaths", ArrayProperty.class);
/* 365 */     if (seq == null)
/*     */     {
/* 367 */       seq = getMetadata().getTypeMapping().createArrayProperty(null, getPreferedPrefix(), "alternatePaths", Cardinality.Seq);
/*     */ 
/* 369 */       addProperty(seq);
/*     */     }
/* 371 */     TypeMapping tm = getMetadata().getTypeMapping();
/* 372 */     TextType tt = (TextType)tm.instanciateSimpleProperty(null, "rdf", "li", value, Types.Text);
/* 373 */     seq.addProperty(tt);
/*     */   }
/*     */ 
/*     */   public ArrayProperty getAlternatePathsProperty()
/*     */   {
/* 383 */     return (ArrayProperty)getFirstEquivalentProperty("alternatePaths", ArrayProperty.class);
/*     */   }
/*     */ 
/*     */   public List<String> getAlternatePaths()
/*     */   {
/* 393 */     ArrayProperty seq = (ArrayProperty)getFirstEquivalentProperty("alternatePaths", ArrayProperty.class);
/* 394 */     if (seq != null)
/*     */     {
/* 396 */       return seq.getElementsAsString();
/*     */     }
/*     */ 
/* 400 */     return null;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.xmpbox.type.ResourceRefType
 * JD-Core Version:    0.6.2
 */