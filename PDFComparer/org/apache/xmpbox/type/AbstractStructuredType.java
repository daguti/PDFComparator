/*     */ package org.apache.xmpbox.type;
/*     */ 
/*     */ import java.util.Calendar;
/*     */ import org.apache.xmpbox.XMPMetadata;
/*     */ 
/*     */ public abstract class AbstractStructuredType extends AbstractComplexProperty
/*     */ {
/*     */   protected static final String STRUCTURE_ARRAY_NAME = "li";
/*     */   private String namespace;
/*     */   private String preferedPrefix;
/*     */   private String prefix;
/*     */ 
/*     */   public AbstractStructuredType(XMPMetadata metadata)
/*     */   {
/*  41 */     this(metadata, null, null, null);
/*     */   }
/*     */ 
/*     */   public AbstractStructuredType(XMPMetadata metadata, String namespaceURI)
/*     */   {
/*  46 */     this(metadata, namespaceURI, null, null);
/*  47 */     StructuredType st = (StructuredType)getClass().getAnnotation(StructuredType.class);
/*  48 */     if (st != null)
/*     */     {
/*  51 */       this.namespace = st.namespace();
/*  52 */       this.preferedPrefix = st.preferedPrefix();
/*     */     }
/*     */     else
/*     */     {
/*  56 */       throw new IllegalArgumentException(" StructuredType annotation cannot be null");
/*     */     }
/*  58 */     this.prefix = this.preferedPrefix;
/*     */   }
/*     */ 
/*     */   public AbstractStructuredType(XMPMetadata metadata, String namespaceURI, String fieldPrefix, String propertyName)
/*     */   {
/*  63 */     super(metadata, propertyName);
/*  64 */     StructuredType st = (StructuredType)getClass().getAnnotation(StructuredType.class);
/*  65 */     if (st != null)
/*     */     {
/*  68 */       this.namespace = st.namespace();
/*  69 */       this.preferedPrefix = st.preferedPrefix();
/*     */     }
/*     */     else
/*     */     {
/*  74 */       if (namespaceURI == null)
/*     */       {
/*  76 */         throw new IllegalArgumentException("Both StructuredType annotation and namespace parameter cannot be null");
/*     */       }
/*     */ 
/*  79 */       this.namespace = namespaceURI;
/*  80 */       this.preferedPrefix = fieldPrefix;
/*     */     }
/*  82 */     this.prefix = (fieldPrefix == null ? this.preferedPrefix : fieldPrefix);
/*     */   }
/*     */ 
/*     */   public final String getNamespace()
/*     */   {
/*  92 */     return this.namespace;
/*     */   }
/*     */ 
/*     */   public final void setNamespace(String ns)
/*     */   {
/*  97 */     this.namespace = ns;
/*     */   }
/*     */ 
/*     */   public final String getPrefix()
/*     */   {
/* 107 */     return this.prefix;
/*     */   }
/*     */ 
/*     */   public final void setPrefix(String pf)
/*     */   {
/* 112 */     this.prefix = pf;
/*     */   }
/*     */ 
/*     */   public final String getPreferedPrefix()
/*     */   {
/* 117 */     return this.prefix;
/*     */   }
/*     */ 
/*     */   protected void addSimpleProperty(String propertyName, Object value)
/*     */   {
/* 122 */     TypeMapping tm = getMetadata().getTypeMapping();
/* 123 */     AbstractSimpleProperty asp = tm.instanciateSimpleField(getClass(), null, getPrefix(), propertyName, value);
/* 124 */     addProperty(asp);
/*     */   }
/*     */ 
/*     */   protected String getPropertyValueAsString(String fieldName)
/*     */   {
/* 129 */     AbstractSimpleProperty absProp = (AbstractSimpleProperty)getProperty(fieldName);
/* 130 */     if (absProp == null)
/*     */     {
/* 132 */       return null;
/*     */     }
/*     */ 
/* 136 */     return absProp.getStringValue();
/*     */   }
/*     */ 
/*     */   protected Calendar getDatePropertyAsCalendar(String fieldName)
/*     */   {
/* 142 */     DateType absProp = (DateType)getFirstEquivalentProperty(fieldName, DateType.class);
/* 143 */     if (absProp != null)
/*     */     {
/* 145 */       return absProp.getValue();
/*     */     }
/*     */ 
/* 149 */     return null;
/*     */   }
/*     */ 
/*     */   public TextType createTextType(String propertyName, String value)
/*     */   {
/* 155 */     return getMetadata().getTypeMapping().createText(getNamespace(), getPrefix(), propertyName, value);
/*     */   }
/*     */ 
/*     */   public ArrayProperty createArrayProperty(String propertyName, Cardinality type)
/*     */   {
/* 160 */     return getMetadata().getTypeMapping().createArrayProperty(getNamespace(), getPrefix(), propertyName, type);
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.xmpbox.type.AbstractStructuredType
 * JD-Core Version:    0.6.2
 */