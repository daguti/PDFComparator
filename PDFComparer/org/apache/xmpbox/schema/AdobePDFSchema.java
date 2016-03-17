/*     */ package org.apache.xmpbox.schema;
/*     */ 
/*     */ import org.apache.xmpbox.XMPMetadata;
/*     */ import org.apache.xmpbox.type.AbstractField;
/*     */ import org.apache.xmpbox.type.Cardinality;
/*     */ import org.apache.xmpbox.type.PropertyType;
/*     */ import org.apache.xmpbox.type.StructuredType;
/*     */ import org.apache.xmpbox.type.TextType;
/*     */ import org.apache.xmpbox.type.Types;
/*     */ 
/*     */ @StructuredType(preferedPrefix="pdf", namespace="http://ns.adobe.com/pdf/1.3/")
/*     */ public class AdobePDFSchema extends XMPSchema
/*     */ {
/*     */ 
/*     */   @PropertyType(type=Types.Text, card=Cardinality.Simple)
/*     */   public static final String KEYWORDS = "Keywords";
/*     */ 
/*     */   @PropertyType(type=Types.Text, card=Cardinality.Simple)
/*     */   public static final String PDF_VERSION = "PDFVersion";
/*     */ 
/*     */   @PropertyType(type=Types.Text, card=Cardinality.Simple)
/*     */   public static final String PRODUCER = "Producer";
/*     */ 
/*     */   public AdobePDFSchema(XMPMetadata metadata)
/*     */   {
/*  59 */     super(metadata);
/*     */   }
/*     */ 
/*     */   public AdobePDFSchema(XMPMetadata metadata, String ownPrefix)
/*     */   {
/*  72 */     super(metadata, ownPrefix);
/*     */   }
/*     */ 
/*     */   public void setKeywords(String value)
/*     */   {
/*  84 */     TextType keywords = createTextType("Keywords", value);
/*  85 */     addProperty(keywords);
/*     */   }
/*     */ 
/*     */   public void setKeywordsProperty(TextType keywords)
/*     */   {
/*  96 */     addProperty(keywords);
/*     */   }
/*     */ 
/*     */   public void setPDFVersion(String value)
/*     */   {
/* 108 */     TextType version = createTextType("PDFVersion", value);
/* 109 */     addProperty(version);
/*     */   }
/*     */ 
/*     */   public void setPDFVersionProperty(TextType version)
/*     */   {
/* 121 */     addProperty(version);
/*     */   }
/*     */ 
/*     */   public void setProducer(String value)
/*     */   {
/* 133 */     TextType producer = createTextType("Producer", value);
/* 134 */     addProperty(producer);
/*     */   }
/*     */ 
/*     */   public void setProducerProperty(TextType producer)
/*     */   {
/* 145 */     addProperty(producer);
/*     */   }
/*     */ 
/*     */   public TextType getKeywordsProperty()
/*     */   {
/* 155 */     AbstractField tmp = getProperty("Keywords");
/* 156 */     if ((tmp instanceof TextType))
/*     */     {
/* 158 */       return (TextType)tmp;
/*     */     }
/* 160 */     return null;
/*     */   }
/*     */ 
/*     */   public String getKeywords()
/*     */   {
/* 170 */     AbstractField tmp = getProperty("Keywords");
/* 171 */     if ((tmp instanceof TextType))
/*     */     {
/* 173 */       return ((TextType)tmp).getStringValue();
/*     */     }
/* 175 */     return null;
/*     */   }
/*     */ 
/*     */   public TextType getPDFVersionProperty()
/*     */   {
/* 185 */     AbstractField tmp = getProperty("PDFVersion");
/* 186 */     if ((tmp instanceof TextType))
/*     */     {
/* 188 */       return (TextType)tmp;
/*     */     }
/* 190 */     return null;
/*     */   }
/*     */ 
/*     */   public String getPDFVersion()
/*     */   {
/* 200 */     AbstractField tmp = getProperty("PDFVersion");
/* 201 */     if ((tmp instanceof TextType))
/*     */     {
/* 203 */       return ((TextType)tmp).getStringValue();
/*     */     }
/* 205 */     return null;
/*     */   }
/*     */ 
/*     */   public TextType getProducerProperty()
/*     */   {
/* 215 */     AbstractField tmp = getProperty("Producer");
/* 216 */     if ((tmp instanceof TextType))
/*     */     {
/* 218 */       return (TextType)tmp;
/*     */     }
/* 220 */     return null;
/*     */   }
/*     */ 
/*     */   public String getProducer()
/*     */   {
/* 230 */     AbstractField tmp = getProperty("Producer");
/* 231 */     if ((tmp instanceof TextType))
/*     */     {
/* 233 */       return ((TextType)tmp).getStringValue();
/*     */     }
/* 235 */     return null;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.xmpbox.schema.AdobePDFSchema
 * JD-Core Version:    0.6.2
 */