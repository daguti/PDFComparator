/*     */ package org.apache.xmpbox.type;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import org.apache.xmpbox.XMPMetadata;
/*     */ 
/*     */ public class ArrayProperty extends AbstractComplexProperty
/*     */ {
/*     */   private Cardinality arrayType;
/*     */   private String namespace;
/*     */   private String prefix;
/*     */ 
/*     */   public ArrayProperty(XMPMetadata metadata, String namespace, String prefix, String propertyName, Cardinality type)
/*     */   {
/*  62 */     super(metadata, propertyName);
/*  63 */     this.arrayType = type;
/*  64 */     this.namespace = namespace;
/*  65 */     this.prefix = prefix;
/*     */   }
/*     */ 
/*     */   public Cardinality getArrayType()
/*     */   {
/*  70 */     return this.arrayType;
/*     */   }
/*     */ 
/*     */   public List<String> getElementsAsString()
/*     */   {
/*  75 */     List retval = null;
/*  76 */     retval = new ArrayList();
/*  77 */     Iterator it = getContainer().getAllProperties().iterator();
/*     */ 
/*  79 */     while (it.hasNext())
/*     */     {
/*  81 */       AbstractSimpleProperty tmp = (AbstractSimpleProperty)it.next();
/*  82 */       retval.add(tmp.getStringValue());
/*     */     }
/*  84 */     retval = Collections.unmodifiableList(retval);
/*  85 */     return retval;
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
 * Qualified Name:     org.apache.xmpbox.type.ArrayProperty
 * JD-Core Version:    0.6.2
 */