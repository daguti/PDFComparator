/*     */ package org.apache.xmpbox.type;
/*     */ 
/*     */ import org.apache.xmpbox.XMPMetadata;
/*     */ 
/*     */ @StructuredType(preferedPrefix="xmpGImg", namespace="http://ns.adobe.com/xap/1.0/g/img/")
/*     */ public class ThumbnailType extends AbstractStructuredType
/*     */ {
/*     */ 
/*     */   @PropertyType(type=Types.Choice, card=Cardinality.Simple)
/*     */   public static final String FORMAT = "format";
/*     */ 
/*     */   @PropertyType(type=Types.Integer, card=Cardinality.Simple)
/*     */   public static final String HEIGHT = "height";
/*     */ 
/*     */   @PropertyType(type=Types.Integer, card=Cardinality.Simple)
/*     */   public static final String WIDTH = "width";
/*     */ 
/*     */   @PropertyType(type=Types.Text, card=Cardinality.Simple)
/*     */   public static final String IMAGE = "image";
/*     */ 
/*     */   public ThumbnailType(XMPMetadata metadata)
/*     */   {
/*  55 */     super(metadata);
/*  56 */     setAttribute(new Attribute("http://www.w3.org/1999/02/22-rdf-syntax-ns#", "parseType", "Resource"));
/*     */   }
/*     */ 
/*     */   public Integer getHeight()
/*     */   {
/*  66 */     AbstractField absProp = getFirstEquivalentProperty("height", IntegerType.class);
/*  67 */     if (absProp != null)
/*     */     {
/*  69 */       return ((IntegerType)absProp).getValue();
/*     */     }
/*  71 */     return null;
/*     */   }
/*     */ 
/*     */   public void setHeight(Integer height)
/*     */   {
/*  82 */     addSimpleProperty("height", height);
/*     */   }
/*     */ 
/*     */   public Integer getWidth()
/*     */   {
/*  92 */     AbstractField absProp = getFirstEquivalentProperty("width", IntegerType.class);
/*  93 */     if (absProp != null)
/*     */     {
/*  96 */       return ((IntegerType)absProp).getValue();
/*     */     }
/*  98 */     return null;
/*     */   }
/*     */ 
/*     */   public void setWidth(Integer width)
/*     */   {
/* 109 */     addSimpleProperty("width", width);
/*     */   }
/*     */ 
/*     */   public String getImage()
/*     */   {
/* 119 */     AbstractField absProp = getFirstEquivalentProperty("image", TextType.class);
/* 120 */     if (absProp != null)
/*     */     {
/* 122 */       return ((TextType)absProp).getStringValue();
/*     */     }
/* 124 */     return null;
/*     */   }
/*     */ 
/*     */   public void setImage(String image)
/*     */   {
/* 135 */     addSimpleProperty("image", image);
/*     */   }
/*     */ 
/*     */   public String getFormat()
/*     */   {
/* 145 */     AbstractField absProp = getFirstEquivalentProperty("format", ChoiceType.class);
/* 146 */     if (absProp != null)
/*     */     {
/* 148 */       return ((TextType)absProp).getStringValue();
/*     */     }
/* 150 */     return null;
/*     */   }
/*     */ 
/*     */   public void setFormat(String format)
/*     */   {
/* 161 */     addSimpleProperty("format", format);
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.xmpbox.type.ThumbnailType
 * JD-Core Version:    0.6.2
 */