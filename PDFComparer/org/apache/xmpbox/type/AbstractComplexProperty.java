/*     */ package org.apache.xmpbox.type;
/*     */ 
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import org.apache.xmpbox.XMPMetadata;
/*     */ 
/*     */ public abstract class AbstractComplexProperty extends AbstractField
/*     */ {
/*     */   private ComplexPropertyContainer container;
/*     */   private Map<String, String> namespaceToPrefix;
/*     */ 
/*     */   public AbstractComplexProperty(XMPMetadata metadata, String propertyName)
/*     */   {
/*  39 */     super(metadata, propertyName);
/*  40 */     this.container = new ComplexPropertyContainer();
/*  41 */     this.namespaceToPrefix = new HashMap();
/*     */   }
/*     */ 
/*     */   public void addNamespace(String namespace, String prefix)
/*     */   {
/*  46 */     this.namespaceToPrefix.put(namespace, prefix);
/*     */   }
/*     */ 
/*     */   public String getNamespacePrefix(String namespace)
/*     */   {
/*  51 */     return (String)this.namespaceToPrefix.get(namespace);
/*     */   }
/*     */ 
/*     */   public Map<String, String> getAllNamespacesWithPrefix()
/*     */   {
/*  56 */     return this.namespaceToPrefix;
/*     */   }
/*     */ 
/*     */   public final void addProperty(AbstractField obj)
/*     */   {
/*  67 */     this.container.addProperty(obj);
/*     */   }
/*     */ 
/*     */   public final void removeProperty(AbstractField property)
/*     */   {
/*  78 */     this.container.removeProperty(property);
/*     */   }
/*     */ 
/*     */   public final ComplexPropertyContainer getContainer()
/*     */   {
/*  89 */     return this.container;
/*     */   }
/*     */ 
/*     */   public final List<AbstractField> getAllProperties()
/*     */   {
/*  94 */     return this.container.getAllProperties();
/*     */   }
/*     */ 
/*     */   public final AbstractField getProperty(String fieldName)
/*     */   {
/*  99 */     List list = this.container.getPropertiesByLocalName(fieldName);
/*     */ 
/* 101 */     if (list == null)
/*     */     {
/* 103 */       return null;
/*     */     }
/*     */ 
/* 106 */     return (AbstractField)list.get(0);
/*     */   }
/*     */ 
/*     */   public final ArrayProperty getArrayProperty(String fieldName)
/*     */   {
/* 111 */     List list = this.container.getPropertiesByLocalName(fieldName);
/*     */ 
/* 113 */     if (list == null)
/*     */     {
/* 115 */       return null;
/*     */     }
/*     */ 
/* 118 */     return (ArrayProperty)list.get(0);
/*     */   }
/*     */ 
/*     */   protected final AbstractField getFirstEquivalentProperty(String localName, Class<? extends AbstractField> type)
/*     */   {
/* 123 */     return this.container.getFirstEquivalentProperty(localName, type);
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.xmpbox.type.AbstractComplexProperty
 * JD-Core Version:    0.6.2
 */