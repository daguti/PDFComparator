/*     */ package org.apache.xmpbox.type;
/*     */ 
/*     */ import org.apache.xmpbox.XMPMetadata;
/*     */ 
/*     */ public class BooleanType extends AbstractSimpleProperty
/*     */ {
/*     */   public static final String TRUE = "True";
/*     */   public static final String FALSE = "False";
/*     */   private boolean booleanValue;
/*     */ 
/*     */   public BooleanType(XMPMetadata metadata, String namespaceURI, String prefix, String propertyName, Object value)
/*     */   {
/*  57 */     super(metadata, namespaceURI, prefix, propertyName, value);
/*     */   }
/*     */ 
/*     */   public Boolean getValue()
/*     */   {
/*  67 */     return Boolean.valueOf(this.booleanValue);
/*     */   }
/*     */ 
/*     */   public void setValue(Object value)
/*     */   {
/*  79 */     if ((value instanceof Boolean))
/*     */     {
/*  81 */       this.booleanValue = ((Boolean)value).booleanValue();
/*     */     }
/*  83 */     else if ((value instanceof String))
/*     */     {
/*  86 */       String s = value.toString().trim().toUpperCase();
/*  87 */       if ("TRUE".equals(s))
/*     */       {
/*  89 */         this.booleanValue = true;
/*     */       }
/*  91 */       else if ("FALSE".equals(s))
/*     */       {
/*  93 */         this.booleanValue = false;
/*     */       }
/*     */       else
/*     */       {
/*  98 */         throw new IllegalArgumentException("Not a valid boolean value : '" + value + "'");
/*     */       }
/*     */ 
/*     */     }
/*     */     else
/*     */     {
/* 104 */       throw new IllegalArgumentException("Value given is not allowed for the Boolean type.");
/*     */     }
/*     */   }
/*     */ 
/*     */   public String getStringValue()
/*     */   {
/* 111 */     return this.booleanValue ? "True" : "False";
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.xmpbox.type.BooleanType
 * JD-Core Version:    0.6.2
 */