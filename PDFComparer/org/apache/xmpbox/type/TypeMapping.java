/*     */ package org.apache.xmpbox.type;
/*     */ 
/*     */ import java.lang.annotation.Annotation;
/*     */ import java.lang.reflect.Constructor;
/*     */ import java.lang.reflect.Field;
/*     */ import java.lang.reflect.InvocationTargetException;
/*     */ import java.util.Calendar;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import javax.xml.namespace.QName;
/*     */ import org.apache.xmpbox.XMPMetadata;
/*     */ import org.apache.xmpbox.schema.AdobePDFSchema;
/*     */ import org.apache.xmpbox.schema.DublinCoreSchema;
/*     */ import org.apache.xmpbox.schema.PDFAExtensionSchema;
/*     */ import org.apache.xmpbox.schema.PDFAIdentificationSchema;
/*     */ import org.apache.xmpbox.schema.PhotoshopSchema;
/*     */ import org.apache.xmpbox.schema.XMPBasicJobTicketSchema;
/*     */ import org.apache.xmpbox.schema.XMPBasicSchema;
/*     */ import org.apache.xmpbox.schema.XMPMediaManagementSchema;
/*     */ import org.apache.xmpbox.schema.XMPRightsManagementSchema;
/*     */ import org.apache.xmpbox.schema.XMPSchema;
/*     */ import org.apache.xmpbox.schema.XMPSchemaFactory;
/*     */ import org.apache.xmpbox.schema.XmpSchemaException;
/*     */ 
/*     */ public final class TypeMapping
/*     */ {
/*     */   private Map<Types, PropertiesDescription> structuredMappings;
/*     */   private Map<String, Types> structuredNamespaces;
/*     */   private Map<String, String> definedStructuredNamespaces;
/*     */   private Map<String, PropertiesDescription> definedStructuredMappings;
/*     */   private XMPMetadata metadata;
/*     */   private Map<String, XMPSchemaFactory> schemaMap;
/*  71 */   private static Class<?>[] simplePropertyConstParams = { XMPMetadata.class, String.class, String.class, String.class, Object.class };
/*     */ 
/*     */   public TypeMapping(XMPMetadata metadata)
/*     */   {
/*  67 */     this.metadata = metadata;
/*  68 */     initialize();
/*     */   }
/*     */ 
/*     */   private void initialize()
/*     */   {
/*  77 */     this.structuredMappings = new HashMap();
/*  78 */     this.structuredNamespaces = new HashMap();
/*  79 */     for (Types type : Types.values())
/*     */     {
/*  81 */       if (type.isStructured())
/*     */       {
/*  83 */         Class clz = type.getImplementingClass().asSubclass(AbstractStructuredType.class);
/*     */ 
/*  85 */         StructuredType st = (StructuredType)clz.getAnnotation(StructuredType.class);
/*  86 */         String ns = st.namespace();
/*  87 */         PropertiesDescription pm = initializePropMapping(clz);
/*  88 */         this.structuredNamespaces.put(ns, type);
/*  89 */         this.structuredMappings.put(type, pm);
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/*  94 */     this.definedStructuredNamespaces = new HashMap();
/*  95 */     this.definedStructuredMappings = new HashMap();
/*     */ 
/*  98 */     this.schemaMap = new HashMap();
/*  99 */     addNameSpace(XMPBasicSchema.class);
/* 100 */     addNameSpace(DublinCoreSchema.class);
/* 101 */     addNameSpace(PDFAExtensionSchema.class);
/* 102 */     addNameSpace(XMPMediaManagementSchema.class);
/* 103 */     addNameSpace(AdobePDFSchema.class);
/* 104 */     addNameSpace(PDFAIdentificationSchema.class);
/* 105 */     addNameSpace(XMPRightsManagementSchema.class);
/* 106 */     addNameSpace(PhotoshopSchema.class);
/* 107 */     addNameSpace(XMPBasicJobTicketSchema.class);
/*     */   }
/*     */ 
/*     */   public void addToDefinedStructuredTypes(String typeName, String ns, PropertiesDescription pm)
/*     */   {
/* 113 */     this.definedStructuredNamespaces.put(ns, typeName);
/* 114 */     this.definedStructuredMappings.put(typeName, pm);
/*     */   }
/*     */ 
/*     */   public PropertiesDescription getDefinedDescriptionByNamespace(String namespace)
/*     */   {
/* 119 */     String dt = (String)this.definedStructuredNamespaces.get(namespace);
/* 120 */     return (PropertiesDescription)this.definedStructuredMappings.get(dt);
/*     */   }
/*     */ 
/*     */   public AbstractStructuredType instanciateStructuredType(Types type, String propertyName)
/*     */     throws BadFieldValueException
/*     */   {
/*     */     try
/*     */     {
/* 128 */       Class propertyTypeClass = type.getImplementingClass().asSubclass(AbstractStructuredType.class);
/*     */ 
/* 130 */       Constructor construct = propertyTypeClass.getConstructor(new Class[] { XMPMetadata.class });
/*     */ 
/* 132 */       AbstractStructuredType tmp = (AbstractStructuredType)construct.newInstance(new Object[] { this.metadata });
/* 133 */       tmp.setPropertyName(propertyName);
/* 134 */       return tmp;
/*     */     }
/*     */     catch (InvocationTargetException e)
/*     */     {
/* 138 */       throw new BadFieldValueException("Failed to instanciate structured type : " + type, e);
/*     */     }
/*     */     catch (IllegalArgumentException e)
/*     */     {
/* 142 */       throw new BadFieldValueException("Failed to instanciate structured type : " + type, e);
/*     */     }
/*     */     catch (InstantiationException e)
/*     */     {
/* 146 */       throw new BadFieldValueException("Failed to instanciate structured type : " + type, e);
/*     */     }
/*     */     catch (IllegalAccessException e)
/*     */     {
/* 150 */       throw new BadFieldValueException("Failed to instanciate structured type : " + type, e);
/*     */     }
/*     */     catch (SecurityException e)
/*     */     {
/* 154 */       throw new BadFieldValueException("Failed to instanciate structured type : " + type, e);
/*     */     }
/*     */     catch (NoSuchMethodException e)
/*     */     {
/* 158 */       throw new BadFieldValueException("Failed to instanciate structured type : " + type, e);
/*     */     }
/*     */   }
/*     */ 
/*     */   public AbstractStructuredType instanciateDefinedType(String propertyName, String namespace)
/*     */   {
/* 164 */     return new DefinedStructuredType(this.metadata, namespace, null, propertyName);
/*     */   }
/*     */ 
/*     */   public AbstractSimpleProperty instanciateSimpleProperty(String nsuri, String prefix, String name, Object value, Types type)
/*     */   {
/* 171 */     Object[] params = { this.metadata, nsuri, prefix, name, value };
/*     */     try
/*     */     {
/* 175 */       Class clz = type.getImplementingClass().asSubclass(AbstractSimpleProperty.class);
/*     */ 
/* 177 */       Constructor cons = clz.getConstructor(simplePropertyConstParams);
/* 178 */       return (AbstractSimpleProperty)cons.newInstance(params);
/*     */     }
/*     */     catch (NoSuchMethodError e)
/*     */     {
/* 182 */       throw new IllegalArgumentException("Failed to instanciate property", e);
/*     */     }
/*     */     catch (IllegalArgumentException e)
/*     */     {
/* 186 */       throw new IllegalArgumentException("Failed to instanciate property", e);
/*     */     }
/*     */     catch (InstantiationException e)
/*     */     {
/* 190 */       throw new IllegalArgumentException("Failed to instanciate property", e);
/*     */     }
/*     */     catch (IllegalAccessException e)
/*     */     {
/* 194 */       throw new IllegalArgumentException("Failed to instanciate property", e);
/*     */     }
/*     */     catch (InvocationTargetException e)
/*     */     {
/* 198 */       throw new IllegalArgumentException("Failed to instanciate property", e);
/*     */     }
/*     */     catch (SecurityException e)
/*     */     {
/* 202 */       throw new IllegalArgumentException("Failed to instanciate property", e);
/*     */     }
/*     */     catch (NoSuchMethodException e)
/*     */     {
/* 206 */       throw new IllegalArgumentException("Failed to instanciate property", e);
/*     */     }
/*     */   }
/*     */ 
/*     */   public AbstractSimpleProperty instanciateSimpleField(Class<?> clz, String nsuri, String prefix, String propertyName, Object value)
/*     */   {
/* 213 */     PropertiesDescription pm = initializePropMapping(clz);
/* 214 */     PropertyType simpleType = pm.getPropertyType(propertyName);
/* 215 */     Types type = simpleType.type();
/* 216 */     return instanciateSimpleProperty(nsuri, prefix, propertyName, value, type);
/*     */   }
/*     */ 
/*     */   public boolean isStructuredTypeNamespace(String namespace)
/*     */   {
/* 228 */     return this.structuredNamespaces.containsKey(namespace);
/*     */   }
/*     */ 
/*     */   public boolean isDefinedTypeNamespace(String namespace)
/*     */   {
/* 233 */     return this.definedStructuredNamespaces.containsKey(namespace);
/*     */   }
/*     */ 
/*     */   public boolean isDefinedType(String name)
/*     */   {
/* 248 */     return this.definedStructuredMappings.containsKey(name);
/*     */   }
/*     */ 
/*     */   private void addNameSpace(Class<? extends XMPSchema> classSchem)
/*     */   {
/* 253 */     StructuredType st = (StructuredType)classSchem.getAnnotation(StructuredType.class);
/* 254 */     String ns = st.namespace();
/* 255 */     this.schemaMap.put(ns, new XMPSchemaFactory(ns, classSchem, initializePropMapping(classSchem)));
/*     */   }
/*     */ 
/*     */   public void addNewNameSpace(String ns, String prefered)
/*     */   {
/* 260 */     PropertiesDescription mapping = new PropertiesDescription();
/* 261 */     this.schemaMap.put(ns, new XMPSchemaFactory(ns, XMPSchema.class, mapping));
/*     */   }
/*     */ 
/*     */   public PropertiesDescription getStructuredPropMapping(Types type)
/*     */   {
/* 266 */     return (PropertiesDescription)this.structuredMappings.get(type);
/*     */   }
/*     */ 
/*     */   public XMPSchema getAssociatedSchemaObject(XMPMetadata metadata, String namespace, String prefix)
/*     */     throws XmpSchemaException
/*     */   {
/* 284 */     if (this.schemaMap.containsKey(namespace))
/*     */     {
/* 286 */       XMPSchemaFactory factory = (XMPSchemaFactory)this.schemaMap.get(namespace);
/* 287 */       return factory.createXMPSchema(metadata, prefix);
/*     */     }
/*     */ 
/* 291 */     XMPSchemaFactory factory = getSchemaFactory(namespace);
/* 292 */     return factory != null ? factory.createXMPSchema(metadata, prefix) : null;
/*     */   }
/*     */ 
/*     */   public XMPSchemaFactory getSchemaFactory(String namespace)
/*     */   {
/* 298 */     return (XMPSchemaFactory)this.schemaMap.get(namespace);
/*     */   }
/*     */ 
/*     */   public boolean isDefinedSchema(String namespace)
/*     */   {
/* 310 */     return this.schemaMap.containsKey(namespace);
/*     */   }
/*     */ 
/*     */   public boolean isDefinedNamespace(String namespace)
/*     */   {
/* 315 */     return (isDefinedSchema(namespace)) || (isStructuredTypeNamespace(namespace)) || (isDefinedTypeNamespace(namespace));
/*     */   }
/*     */ 
/*     */   public PropertyType getSpecifiedPropertyType(QName name)
/*     */     throws BadFieldValueException
/*     */   {
/* 327 */     XMPSchemaFactory factory = getSchemaFactory(name.getNamespaceURI());
/* 328 */     if (factory != null)
/*     */     {
/* 331 */       return factory.getPropertyType(name.getLocalPart());
/*     */     }
/*     */ 
/* 336 */     Types st = (Types)this.structuredNamespaces.get(name.getNamespaceURI());
/* 337 */     if (st != null)
/*     */     {
/* 339 */       return createPropertyType(st, Cardinality.Simple);
/*     */     }
/*     */ 
/* 344 */     String dt = (String)this.definedStructuredNamespaces.get(name.getNamespaceURI());
/* 345 */     if (dt == null)
/*     */     {
/* 348 */       throw new BadFieldValueException("No descriptor found for " + name);
/*     */     }
/*     */ 
/* 352 */     return createPropertyType(Types.DefinedType, Cardinality.Simple);
/*     */   }
/*     */ 
/*     */   public PropertiesDescription initializePropMapping(Class<?> classSchem)
/*     */   {
/* 360 */     PropertiesDescription propMap = new PropertiesDescription();
/* 361 */     Field[] fields = classSchem.getFields();
/* 362 */     String propName = null;
/* 363 */     for (Field field : fields)
/*     */     {
/* 365 */       if (field.isAnnotationPresent(PropertyType.class))
/*     */       {
/*     */         try
/*     */         {
/* 369 */           propName = (String)field.get(propName);
/*     */         }
/*     */         catch (Exception e)
/*     */         {
/* 373 */           throw new IllegalArgumentException("couldn't read one type declaration, please check accessibility and declaration of fields annoted in " + classSchem.getName(), e);
/*     */         }
/*     */ 
/* 377 */         PropertyType propType = (PropertyType)field.getAnnotation(PropertyType.class);
/* 378 */         propMap.addNewProperty(propName, propType);
/*     */       }
/*     */     }
/* 381 */     return propMap;
/*     */   }
/*     */ 
/*     */   public BooleanType createBoolean(String namespaceURI, String prefix, String propertyName, boolean value)
/*     */   {
/* 386 */     return new BooleanType(this.metadata, namespaceURI, prefix, propertyName, Boolean.valueOf(value));
/*     */   }
/*     */ 
/*     */   public DateType createDate(String namespaceURI, String prefix, String propertyName, Calendar value)
/*     */   {
/* 391 */     return new DateType(this.metadata, namespaceURI, prefix, propertyName, value);
/*     */   }
/*     */ 
/*     */   public IntegerType createInteger(String namespaceURI, String prefix, String propertyName, int value)
/*     */   {
/* 396 */     return new IntegerType(this.metadata, namespaceURI, prefix, propertyName, Integer.valueOf(value));
/*     */   }
/*     */ 
/*     */   public RealType createReal(String namespaceURI, String prefix, String propertyName, float value)
/*     */   {
/* 401 */     return new RealType(this.metadata, namespaceURI, prefix, propertyName, Float.valueOf(value));
/*     */   }
/*     */ 
/*     */   public TextType createText(String namespaceURI, String prefix, String propertyName, String value)
/*     */   {
/* 406 */     return new TextType(this.metadata, namespaceURI, prefix, propertyName, value);
/*     */   }
/*     */ 
/*     */   public ProperNameType createProperName(String namespaceURI, String prefix, String propertyName, String value)
/*     */   {
/* 411 */     return new ProperNameType(this.metadata, namespaceURI, prefix, propertyName, value);
/*     */   }
/*     */ 
/*     */   public URIType createURI(String namespaceURI, String prefix, String propertyName, String value)
/*     */   {
/* 416 */     return new URIType(this.metadata, namespaceURI, prefix, propertyName, value);
/*     */   }
/*     */ 
/*     */   public URLType createURL(String namespaceURI, String prefix, String propertyName, String value)
/*     */   {
/* 421 */     return new URLType(this.metadata, namespaceURI, prefix, propertyName, value);
/*     */   }
/*     */ 
/*     */   public RenditionClassType createRenditionClass(String namespaceURI, String prefix, String propertyName, String value)
/*     */   {
/* 426 */     return new RenditionClassType(this.metadata, namespaceURI, prefix, propertyName, value);
/*     */   }
/*     */ 
/*     */   public PartType createPart(String namespaceURI, String prefix, String propertyName, String value)
/*     */   {
/* 431 */     return new PartType(this.metadata, namespaceURI, prefix, propertyName, value);
/*     */   }
/*     */ 
/*     */   public MIMEType createMIMEType(String namespaceURI, String prefix, String propertyName, String value)
/*     */   {
/* 436 */     return new MIMEType(this.metadata, namespaceURI, prefix, propertyName, value);
/*     */   }
/*     */ 
/*     */   public LocaleType createLocale(String namespaceURI, String prefix, String propertyName, String value)
/*     */   {
/* 441 */     return new LocaleType(this.metadata, namespaceURI, prefix, propertyName, value);
/*     */   }
/*     */ 
/*     */   public GUIDType createGUID(String namespaceURI, String prefix, String propertyName, String value)
/*     */   {
/* 446 */     return new GUIDType(this.metadata, namespaceURI, prefix, propertyName, value);
/*     */   }
/*     */ 
/*     */   public ChoiceType createChoice(String namespaceURI, String prefix, String propertyName, String value)
/*     */   {
/* 451 */     return new ChoiceType(this.metadata, namespaceURI, prefix, propertyName, value);
/*     */   }
/*     */ 
/*     */   public AgentNameType createAgentName(String namespaceURI, String prefix, String propertyName, String value)
/*     */   {
/* 456 */     return new AgentNameType(this.metadata, namespaceURI, prefix, propertyName, value);
/*     */   }
/*     */ 
/*     */   public XPathType createXPath(String namespaceURI, String prefix, String propertyName, String value)
/*     */   {
/* 461 */     return new XPathType(this.metadata, namespaceURI, prefix, propertyName, value);
/*     */   }
/*     */ 
/*     */   public ArrayProperty createArrayProperty(String namespace, String prefix, String propertyName, Cardinality type)
/*     */   {
/* 466 */     return new ArrayProperty(this.metadata, namespace, prefix, propertyName, type);
/*     */   }
/*     */ 
/*     */   public static PropertyType createPropertyType(Types type, final Cardinality card)
/*     */   {
/* 471 */     return new PropertyType()
/*     */     {
/*     */       public Class<? extends Annotation> annotationType()
/*     */       {
/* 476 */         return null;
/*     */       }
/*     */ 
/*     */       public Types type()
/*     */       {
/* 481 */         return this.val$type;
/*     */       }
/*     */ 
/*     */       public Cardinality card()
/*     */       {
/* 486 */         return card;
/*     */       }
/*     */     };
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.xmpbox.type.TypeMapping
 * JD-Core Version:    0.6.2
 */