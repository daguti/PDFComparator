/*     */ package org.apache.xmpbox.type;
/*     */ 
/*     */ import org.apache.xmpbox.XMPMetadata;
/*     */ 
/*     */ public abstract class AbstractSimpleProperty extends AbstractField
/*     */ {
/*     */   private String namespace;
/*     */   private String prefix;
/*     */ 
/*     */   public AbstractSimpleProperty(XMPMetadata metadata, String namespaceURI, String prefix, String propertyName, Object value)
/*     */   {
/*  56 */     super(metadata, propertyName);
/*  57 */     setValue(value);
/*  58 */     this.namespace = namespaceURI;
/*  59 */     this.prefix = prefix;
/*     */   }
/*     */ 
/*     */   public abstract void setValue(Object paramObject);
/*     */ 
/*     */   public abstract String getStringValue();
/*     */ 
/*     */   public abstract Object getValue();
/*     */ 
/*     */   public String toString()
/*     */   {
/*  82 */     StringBuilder sb = new StringBuilder();
/*  83 */     sb.append("[").append(getClass().getSimpleName()).append(":");
/*  84 */     sb.append(getStringValue()).append("]");
/*  85 */     return sb.toString();
/*     */   }
/*     */ 
/*     */   public final String getNamespace()
/*     */   {
/*  95 */     return this.namespace;
/*     */   }
/*     */ 
/*     */   public String getPrefix()
/*     */   {
/* 105 */     return this.prefix;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.xmpbox.type.AbstractSimpleProperty
 * JD-Core Version:    0.6.2
 */