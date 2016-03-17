/*     */ package org.apache.xmpbox.type;
/*     */ 
/*     */ public class Attribute
/*     */ {
/*     */   private String nsURI;
/*     */   private String name;
/*     */   private String value;
/*     */ 
/*     */   public Attribute(String nsURI, String localName, String value)
/*     */   {
/*  51 */     this.nsURI = nsURI;
/*  52 */     this.name = localName;
/*  53 */     this.value = value;
/*     */   }
/*     */ 
/*     */   public String getName()
/*     */   {
/*  63 */     return this.name;
/*     */   }
/*     */ 
/*     */   public void setName(String lname)
/*     */   {
/*  74 */     this.name = lname;
/*     */   }
/*     */ 
/*     */   public String getNamespace()
/*     */   {
/*  84 */     return this.nsURI;
/*     */   }
/*     */ 
/*     */   public void setNsURI(String nsURI)
/*     */   {
/*  95 */     this.nsURI = nsURI;
/*     */   }
/*     */ 
/*     */   public String getValue()
/*     */   {
/* 105 */     return this.value;
/*     */   }
/*     */ 
/*     */   public void setValue(String value)
/*     */   {
/* 116 */     this.value = value;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 121 */     StringBuilder sb = new StringBuilder(80);
/* 122 */     sb.append("[attr:{").append(this.nsURI).append("}").append(this.name).append("=").append(this.value).append("]");
/* 123 */     return sb.toString();
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.xmpbox.type.Attribute
 * JD-Core Version:    0.6.2
 */