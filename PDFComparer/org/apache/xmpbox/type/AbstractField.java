/*     */ package org.apache.xmpbox.type;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import org.apache.xmpbox.XMPMetadata;
/*     */ 
/*     */ public abstract class AbstractField
/*     */ {
/*     */   private XMPMetadata metadata;
/*     */   private String propertyName;
/*     */   private Map<String, Attribute> attributes;
/*     */ 
/*     */   public AbstractField(XMPMetadata metadata, String propertyName)
/*     */   {
/*  56 */     this.metadata = metadata;
/*  57 */     this.propertyName = propertyName;
/*  58 */     this.attributes = new HashMap();
/*     */   }
/*     */ 
/*     */   public final String getPropertyName()
/*     */   {
/*  68 */     return this.propertyName;
/*     */   }
/*     */ 
/*     */   public final void setPropertyName(String value)
/*     */   {
/*  73 */     this.propertyName = value;
/*     */   }
/*     */ 
/*     */   public final void setAttribute(Attribute value)
/*     */   {
/*  84 */     if (this.attributes.containsKey(value.getName()))
/*     */     {
/*  87 */       this.attributes.remove(value.getName());
/*     */     }
/*  89 */     if (value.getNamespace() == null)
/*     */     {
/*  91 */       this.attributes.put(value.getName(), value);
/*     */     }
/*     */     else
/*     */     {
/*  95 */       this.attributes.put(value.getName(), value);
/*     */     }
/*     */   }
/*     */ 
/*     */   public final boolean containsAttribute(String qualifiedName)
/*     */   {
/* 108 */     return this.attributes.containsKey(qualifiedName);
/*     */   }
/*     */ 
/*     */   public final Attribute getAttribute(String qualifiedName)
/*     */   {
/* 120 */     return (Attribute)this.attributes.get(qualifiedName);
/*     */   }
/*     */ 
/*     */   public final List<Attribute> getAllAttributes()
/*     */   {
/* 130 */     return new ArrayList(this.attributes.values());
/*     */   }
/*     */ 
/*     */   public final void removeAttribute(String qualifiedName)
/*     */   {
/* 141 */     if (containsAttribute(qualifiedName))
/*     */     {
/* 143 */       this.attributes.remove(qualifiedName);
/*     */     }
/*     */   }
/*     */ 
/*     */   public final XMPMetadata getMetadata()
/*     */   {
/* 150 */     return this.metadata;
/*     */   }
/*     */ 
/*     */   public abstract String getNamespace();
/*     */ 
/*     */   public abstract String getPrefix();
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.xmpbox.type.AbstractField
 * JD-Core Version:    0.6.2
 */