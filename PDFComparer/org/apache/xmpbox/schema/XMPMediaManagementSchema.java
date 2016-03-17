/*     */ package org.apache.xmpbox.schema;
/*     */ 
/*     */ import java.util.List;
/*     */ import org.apache.xmpbox.XMPMetadata;
/*     */ import org.apache.xmpbox.type.AgentNameType;
/*     */ import org.apache.xmpbox.type.ArrayProperty;
/*     */ import org.apache.xmpbox.type.Cardinality;
/*     */ import org.apache.xmpbox.type.PropertyType;
/*     */ import org.apache.xmpbox.type.RenditionClassType;
/*     */ import org.apache.xmpbox.type.ResourceRefType;
/*     */ import org.apache.xmpbox.type.StructuredType;
/*     */ import org.apache.xmpbox.type.TextType;
/*     */ import org.apache.xmpbox.type.Types;
/*     */ import org.apache.xmpbox.type.URIType;
/*     */ 
/*     */ @StructuredType(preferedPrefix="xmpMM", namespace="http://ns.adobe.com/xap/1.0/mm/")
/*     */ public class XMPMediaManagementSchema extends XMPSchema
/*     */ {
/*     */ 
/*     */   @PropertyType(type=Types.ResourceRef, card=Cardinality.Simple)
/*     */   public static final String DERIVED_FROM = "DerivedFrom";
/*     */ 
/*     */   @PropertyType(type=Types.URI, card=Cardinality.Simple)
/*     */   public static final String DOCUMENTID = "DocumentID";
/*     */ 
/*     */   @PropertyType(type=Types.AgentName, card=Cardinality.Simple)
/*     */   public static final String MANAGER = "Manager";
/*     */ 
/*     */   @PropertyType(type=Types.URI, card=Cardinality.Simple)
/*     */   public static final String MANAGETO = "ManageTo";
/*     */ 
/*     */   @PropertyType(type=Types.URI, card=Cardinality.Simple)
/*     */   public static final String MANAGEUI = "ManageUI";
/*     */ 
/*     */   @PropertyType(type=Types.Text, card=Cardinality.Simple)
/*     */   public static final String MANAGERVARIANT = "ManagerVariant";
/*     */ 
/*     */   @PropertyType(type=Types.URI, card=Cardinality.Simple)
/*     */   public static final String INSTANCEID = "InstanceID";
/*     */ 
/*     */   @PropertyType(type=Types.ResourceRef, card=Cardinality.Simple)
/*     */   public static final String MANAGED_FROM = "ManagedFrom";
/*     */ 
/*     */   @PropertyType(type=Types.Text, card=Cardinality.Simple)
/*     */   public static final String ORIGINALDOCUMENTID = "OriginalDocumentID";
/*     */ 
/*     */   @PropertyType(type=Types.RenditionClass, card=Cardinality.Simple)
/*     */   public static final String RENDITIONCLASS = "RenditionClass";
/*     */ 
/*     */   @PropertyType(type=Types.Text, card=Cardinality.Simple)
/*     */   public static final String RENDITIONPARAMS = "RenditionParams";
/*     */ 
/*     */   @PropertyType(type=Types.Text, card=Cardinality.Simple)
/*     */   public static final String VERSIONID = "VersionID";
/*     */ 
/*     */   @PropertyType(type=Types.Version, card=Cardinality.Seq)
/*     */   public static final String VERSIONS = "Versions";
/*     */ 
/*     */   @PropertyType(type=Types.ResourceEvent, card=Cardinality.Seq)
/*     */   public static final String HISTORY = "History";
/*     */ 
/*     */   @PropertyType(type=Types.Text, card=Cardinality.Bag)
/*     */   public static final String INGREDIENTS = "Ingredients";
/*     */ 
/*     */   public XMPMediaManagementSchema(XMPMetadata metadata)
/*     */   {
/*  56 */     super(metadata);
/*     */   }
/*     */ 
/*     */   public XMPMediaManagementSchema(XMPMetadata metadata, String ownPrefix)
/*     */   {
/*  70 */     super(metadata, ownPrefix);
/*     */   }
/*     */ 
/*     */   public void setDerivedFromProperty(ResourceRefType tt)
/*     */   {
/*  87 */     addProperty(tt);
/*     */   }
/*     */ 
/*     */   public ResourceRefType getResourceRefProperty()
/*     */   {
/*  97 */     return (ResourceRefType)getProperty("DerivedFrom");
/*     */   }
/*     */ 
/*     */   public void setDocumentID(String url)
/*     */   {
/* 114 */     URIType tt = (URIType)instanciateSimple("DocumentID", url);
/* 115 */     setDocumentIDProperty(tt);
/*     */   }
/*     */ 
/*     */   public void setDocumentIDProperty(URIType tt)
/*     */   {
/* 126 */     addProperty(tt);
/*     */   }
/*     */ 
/*     */   public TextType getDocumentIDProperty()
/*     */   {
/* 136 */     return (TextType)getProperty("DocumentID");
/*     */   }
/*     */ 
/*     */   public String getDocumentID()
/*     */   {
/* 146 */     TextType tt = getDocumentIDProperty();
/* 147 */     return tt != null ? tt.getStringValue() : null;
/*     */   }
/*     */ 
/*     */   public void setManager(String value)
/*     */   {
/* 164 */     AgentNameType tt = (AgentNameType)instanciateSimple("Manager", value);
/* 165 */     setManagerProperty(tt);
/*     */   }
/*     */ 
/*     */   public void setManagerProperty(AgentNameType tt)
/*     */   {
/* 176 */     addProperty(tt);
/*     */   }
/*     */ 
/*     */   public TextType getManagerProperty()
/*     */   {
/* 186 */     return (TextType)getProperty("Manager");
/*     */   }
/*     */ 
/*     */   public String getManager()
/*     */   {
/* 196 */     TextType tt = getManagerProperty();
/* 197 */     return tt != null ? tt.getStringValue() : null;
/*     */   }
/*     */ 
/*     */   public void setManageTo(String value)
/*     */   {
/* 214 */     URIType tt = (URIType)instanciateSimple("ManageTo", value);
/* 215 */     setManageToProperty(tt);
/*     */   }
/*     */ 
/*     */   public void setManageToProperty(URIType tt)
/*     */   {
/* 226 */     addProperty(tt);
/*     */   }
/*     */ 
/*     */   public TextType getManageToProperty()
/*     */   {
/* 236 */     return (TextType)getProperty("ManageTo");
/*     */   }
/*     */ 
/*     */   public String getManageTo()
/*     */   {
/* 246 */     TextType tt = getManageToProperty();
/* 247 */     return tt != null ? tt.getStringValue() : null;
/*     */   }
/*     */ 
/*     */   public void setManageUI(String value)
/*     */   {
/* 264 */     URIType tt = (URIType)instanciateSimple("ManageUI", value);
/* 265 */     setManageUIProperty(tt);
/*     */   }
/*     */ 
/*     */   public void setManageUIProperty(URIType tt)
/*     */   {
/* 276 */     addProperty(tt);
/*     */   }
/*     */ 
/*     */   public TextType getManageUIProperty()
/*     */   {
/* 286 */     return (TextType)getProperty("ManageUI");
/*     */   }
/*     */ 
/*     */   public String getManageUI()
/*     */   {
/* 296 */     TextType tt = getManageUIProperty();
/* 297 */     return tt != null ? tt.getStringValue() : null;
/*     */   }
/*     */ 
/*     */   public void setManagerVariant(String value)
/*     */   {
/* 314 */     TextType tt = (TextType)instanciateSimple("ManagerVariant", value);
/* 315 */     setManagerVariantProperty(tt);
/*     */   }
/*     */ 
/*     */   public void setManagerVariantProperty(TextType tt)
/*     */   {
/* 326 */     addProperty(tt);
/*     */   }
/*     */ 
/*     */   public TextType getManagerVariantProperty()
/*     */   {
/* 336 */     return (TextType)getProperty("ManagerVariant");
/*     */   }
/*     */ 
/*     */   public String getManagerVariant()
/*     */   {
/* 346 */     TextType tt = getManagerVariantProperty();
/* 347 */     return tt != null ? tt.getStringValue() : null;
/*     */   }
/*     */ 
/*     */   public void setInstanceID(String value)
/*     */   {
/* 364 */     URIType tt = (URIType)instanciateSimple("InstanceID", value);
/* 365 */     setInstanceIDProperty(tt);
/*     */   }
/*     */ 
/*     */   public void setInstanceIDProperty(URIType tt)
/*     */   {
/* 376 */     addProperty(tt);
/*     */   }
/*     */ 
/*     */   public TextType getInstanceIDProperty()
/*     */   {
/* 386 */     return (TextType)getProperty("InstanceID");
/*     */   }
/*     */ 
/*     */   public String getInstanceID()
/*     */   {
/* 396 */     TextType tt = getInstanceIDProperty();
/* 397 */     return tt != null ? tt.getStringValue() : null;
/*     */   }
/*     */ 
/*     */   public void setManagedFromProperty(ResourceRefType resourceRef)
/*     */   {
/* 426 */     addProperty(resourceRef);
/*     */   }
/*     */ 
/*     */   public ResourceRefType getManagedFromProperty()
/*     */   {
/* 436 */     return (ResourceRefType)getProperty("ManagedFrom");
/*     */   }
/*     */ 
/*     */   public void setOriginalDocumentID(String url)
/*     */   {
/* 463 */     TextType tt = (TextType)instanciateSimple("OriginalDocumentID", url);
/* 464 */     setOriginalDocumentIDProperty(tt);
/*     */   }
/*     */ 
/*     */   public void setOriginalDocumentIDProperty(TextType tt)
/*     */   {
/* 475 */     addProperty(tt);
/*     */   }
/*     */ 
/*     */   public TextType getOriginalDocumentIDProperty()
/*     */   {
/* 485 */     return (TextType)getProperty("OriginalDocumentID");
/*     */   }
/*     */ 
/*     */   public String getOriginalDocumentID()
/*     */   {
/* 495 */     TextType tt = getOriginalDocumentIDProperty();
/* 496 */     return tt != null ? tt.getStringValue() : null;
/*     */   }
/*     */ 
/*     */   public void setRenditionClass(String value)
/*     */   {
/* 513 */     RenditionClassType tt = (RenditionClassType)instanciateSimple("RenditionClass", value);
/* 514 */     setRenditionClassProperty(tt);
/*     */   }
/*     */ 
/*     */   public void setRenditionClassProperty(RenditionClassType tt)
/*     */   {
/* 525 */     addProperty(tt);
/*     */   }
/*     */ 
/*     */   public TextType getRenditionClassProperty()
/*     */   {
/* 535 */     return (TextType)getProperty("RenditionClass");
/*     */   }
/*     */ 
/*     */   public String getRenditionClass()
/*     */   {
/* 545 */     TextType tt = getRenditionClassProperty();
/* 546 */     return tt != null ? tt.getStringValue() : null;
/*     */   }
/*     */ 
/*     */   public void setRenditionParams(String url)
/*     */   {
/* 563 */     TextType tt = (TextType)instanciateSimple("RenditionParams", url);
/* 564 */     setRenditionParamsProperty(tt);
/*     */   }
/*     */ 
/*     */   public void setRenditionParamsProperty(TextType tt)
/*     */   {
/* 575 */     addProperty(tt);
/*     */   }
/*     */ 
/*     */   public TextType getRenditionParamsProperty()
/*     */   {
/* 585 */     return (TextType)getProperty("RenditionParams");
/*     */   }
/*     */ 
/*     */   public String getRenditionParams()
/*     */   {
/* 595 */     TextType tt = getRenditionParamsProperty();
/* 596 */     return tt != null ? tt.getStringValue() : null;
/*     */   }
/*     */ 
/*     */   public void setVersionID(String value)
/*     */   {
/* 613 */     TextType tt = (TextType)instanciateSimple("VersionID", value);
/* 614 */     setVersionIDProperty(tt);
/*     */   }
/*     */ 
/*     */   public void setVersionIDProperty(TextType tt)
/*     */   {
/* 625 */     addProperty(tt);
/*     */   }
/*     */ 
/*     */   public TextType getVersionIDProperty()
/*     */   {
/* 635 */     return (TextType)getProperty("VersionID");
/*     */   }
/*     */ 
/*     */   public String getVersionID()
/*     */   {
/* 645 */     TextType tt = getVersionIDProperty();
/* 646 */     return tt != null ? tt.getStringValue() : null;
/*     */   }
/*     */ 
/*     */   public void addVersions(String value)
/*     */   {
/* 657 */     addQualifiedBagValue("Versions", value);
/*     */   }
/*     */ 
/*     */   public ArrayProperty getVersionsProperty()
/*     */   {
/* 667 */     return (ArrayProperty)getProperty("Versions");
/*     */   }
/*     */ 
/*     */   public List<String> getVersions()
/*     */   {
/* 672 */     return getUnqualifiedBagValueList("Versions");
/*     */   }
/*     */ 
/*     */   public void addHistory(String history)
/*     */   {
/* 689 */     addUnqualifiedSequenceValue("History", history);
/*     */   }
/*     */ 
/*     */   public ArrayProperty getHistoryProperty()
/*     */   {
/* 699 */     return (ArrayProperty)getProperty("History");
/*     */   }
/*     */ 
/*     */   public List<String> getHistory()
/*     */   {
/* 709 */     return getUnqualifiedSequenceValueList("History");
/*     */   }
/*     */ 
/*     */   public void addIngredients(String ingredients)
/*     */   {
/* 726 */     addQualifiedBagValue("Ingredients", ingredients);
/*     */   }
/*     */ 
/*     */   public ArrayProperty getIngredientsProperty()
/*     */   {
/* 736 */     return (ArrayProperty)getProperty("Ingredients");
/*     */   }
/*     */ 
/*     */   public List<String> getIngredients()
/*     */   {
/* 746 */     return getUnqualifiedBagValueList("Ingredients");
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.xmpbox.schema.XMPMediaManagementSchema
 * JD-Core Version:    0.6.2
 */