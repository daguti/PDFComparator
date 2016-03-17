/*     */ package org.apache.xmpbox.schema;
/*     */ 
/*     */ import java.lang.reflect.Constructor;
/*     */ import org.apache.xmpbox.XMPMetadata;
/*     */ import org.apache.xmpbox.type.PropertiesDescription;
/*     */ import org.apache.xmpbox.type.PropertyType;
/*     */ 
/*     */ public class XMPSchemaFactory
/*     */ {
/*     */   private String namespace;
/*     */   private Class<? extends XMPSchema> schemaClass;
/*     */   private PropertiesDescription propDef;
/*     */   private String nsName;
/*     */ 
/*     */   public XMPSchemaFactory(String namespace, Class<? extends XMPSchema> schemaClass, PropertiesDescription propDef)
/*     */   {
/*  59 */     this.namespace = namespace;
/*  60 */     this.schemaClass = schemaClass;
/*  61 */     this.propDef = propDef;
/*     */   }
/*     */ 
/*     */   public String getNamespace()
/*     */   {
/*  71 */     return this.namespace;
/*     */   }
/*     */ 
/*     */   public PropertyType getPropertyType(String name)
/*     */   {
/*  83 */     return this.propDef.getPropertyType(name);
/*     */   }
/*     */ 
/*     */   public XMPSchema createXMPSchema(XMPMetadata metadata, String prefix)
/*     */     throws XmpSchemaException
/*     */   {
/*  99 */     XMPSchema schema = null;
/*     */     Object[] schemaArgs;
/*     */     Class[] argsClass;
/*     */     Object[] schemaArgs;
/* 103 */     if (this.schemaClass == XMPSchema.class)
/*     */     {
/* 105 */       Class[] argsClass = { XMPMetadata.class, String.class, String.class };
/* 106 */       schemaArgs = new Object[] { metadata, this.namespace, this.nsName };
/*     */     }
/*     */     else
/*     */     {
/*     */       Object[] schemaArgs;
/* 108 */       if ((prefix != null) && (!"".equals(prefix)))
/*     */       {
/* 110 */         Class[] argsClass = { XMPMetadata.class, String.class };
/* 111 */         schemaArgs = new Object[] { metadata, prefix };
/*     */       }
/*     */       else
/*     */       {
/* 115 */         argsClass = new Class[] { XMPMetadata.class };
/* 116 */         schemaArgs = new Object[] { metadata };
/*     */       }
/*     */     }
/*     */ 
/*     */     try
/*     */     {
/* 122 */       Constructor schemaConstructor = this.schemaClass.getConstructor(argsClass);
/* 123 */       schema = (XMPSchema)schemaConstructor.newInstance(schemaArgs);
/* 124 */       if (schema != null)
/*     */       {
/* 126 */         metadata.addSchema(schema);
/*     */       }
/* 128 */       return schema;
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 132 */       throw new XmpSchemaException("Cannot Instanciate specified Object Schema", e);
/*     */     }
/*     */   }
/*     */ 
/*     */   public PropertiesDescription getPropertyDefinition()
/*     */   {
/* 138 */     return this.propDef;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.xmpbox.schema.XMPSchemaFactory
 * JD-Core Version:    0.6.2
 */