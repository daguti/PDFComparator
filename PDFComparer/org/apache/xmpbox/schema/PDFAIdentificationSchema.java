/*     */ package org.apache.xmpbox.schema;
/*     */ 
/*     */ import org.apache.xmpbox.XMPMetadata;
/*     */ import org.apache.xmpbox.type.AbstractField;
/*     */ import org.apache.xmpbox.type.Attribute;
/*     */ import org.apache.xmpbox.type.BadFieldValueException;
/*     */ import org.apache.xmpbox.type.Cardinality;
/*     */ import org.apache.xmpbox.type.IntegerType;
/*     */ import org.apache.xmpbox.type.PropertyType;
/*     */ import org.apache.xmpbox.type.StructuredType;
/*     */ import org.apache.xmpbox.type.TextType;
/*     */ import org.apache.xmpbox.type.Types;
/*     */ 
/*     */ @StructuredType(preferedPrefix="pdfaid", namespace="http://www.aiim.org/pdfa/ns/id/")
/*     */ public class PDFAIdentificationSchema extends XMPSchema
/*     */ {
/*     */ 
/*     */   @PropertyType(type=Types.Integer, card=Cardinality.Simple)
/*     */   public static final String PART = "part";
/*     */ 
/*     */   @PropertyType(type=Types.Text, card=Cardinality.Simple)
/*     */   public static final String AMD = "amd";
/*     */ 
/*     */   @PropertyType(type=Types.Text, card=Cardinality.Simple)
/*     */   public static final String CONFORMANCE = "conformance";
/*     */ 
/*     */   public PDFAIdentificationSchema(XMPMetadata metadata)
/*     */   {
/*  67 */     super(metadata);
/*     */   }
/*     */ 
/*     */   public PDFAIdentificationSchema(XMPMetadata metadata, String prefix)
/*     */   {
/*  72 */     super(metadata, prefix);
/*     */   }
/*     */ 
/*     */   public void setPartValueWithString(String value)
/*     */   {
/*  84 */     IntegerType part = (IntegerType)instanciateSimple("part", value);
/*  85 */     addProperty(part);
/*     */   }
/*     */ 
/*     */   public void setPartValueWithInt(int value)
/*     */   {
/*  96 */     IntegerType part = (IntegerType)instanciateSimple("part", Integer.valueOf(value));
/*  97 */     addProperty(part);
/*     */   }
/*     */ 
/*     */   public void setPart(Integer value)
/*     */   {
/* 108 */     setPartValueWithInt(value.intValue());
/*     */   }
/*     */ 
/*     */   public void setPartProperty(IntegerType part)
/*     */   {
/* 119 */     addProperty(part);
/*     */   }
/*     */ 
/*     */   public void setAmd(String value)
/*     */   {
/* 130 */     TextType amd = createTextType("amd", value);
/* 131 */     addProperty(amd);
/*     */   }
/*     */ 
/*     */   public void setAmdProperty(TextType amd)
/*     */   {
/* 142 */     addProperty(amd);
/*     */   }
/*     */ 
/*     */   public void setConformance(String value)
/*     */     throws BadFieldValueException
/*     */   {
/* 155 */     if ((value.equals("A")) || (value.equals("B")))
/*     */     {
/* 157 */       TextType conf = createTextType("conformance", value);
/* 158 */       addProperty(conf);
/*     */     }
/*     */     else
/*     */     {
/* 163 */       throw new BadFieldValueException("The property given not seems to be a PDF/A conformance level (must be A or B)");
/*     */     }
/*     */   }
/*     */ 
/*     */   public void setConformanceProperty(TextType conf)
/*     */     throws BadFieldValueException
/*     */   {
/* 178 */     String value = conf.getStringValue();
/* 179 */     if ((value.equals("A")) || (value.equals("B")))
/*     */     {
/* 181 */       addProperty(conf);
/*     */     }
/*     */     else
/*     */     {
/* 185 */       throw new BadFieldValueException("The property given not seems to be a PDF/A conformance level (must be A or B)");
/*     */     }
/*     */   }
/*     */ 
/*     */   public Integer getPart()
/*     */   {
/* 197 */     AbstractField tmp = getPartProperty();
/* 198 */     if ((tmp instanceof IntegerType))
/*     */     {
/* 200 */       return ((IntegerType)tmp).getValue();
/*     */     }
/*     */ 
/* 204 */     for (Attribute attribute : getAllAttributes())
/*     */     {
/* 206 */       if (attribute.getName().equals("part"))
/*     */       {
/* 208 */         return Integer.valueOf(attribute.getValue());
/*     */       }
/*     */     }
/* 211 */     return null;
/*     */   }
/*     */ 
/*     */   public IntegerType getPartProperty()
/*     */   {
/* 222 */     AbstractField tmp = getProperty("part");
/* 223 */     if ((tmp instanceof IntegerType))
/*     */     {
/* 225 */       return (IntegerType)tmp;
/*     */     }
/* 227 */     return null;
/*     */   }
/*     */ 
/*     */   public String getAmendment()
/*     */   {
/* 237 */     AbstractField tmp = getProperty("amd");
/* 238 */     if ((tmp instanceof TextType))
/*     */     {
/* 240 */       return ((TextType)tmp).getStringValue();
/*     */     }
/* 242 */     return null;
/*     */   }
/*     */ 
/*     */   public TextType getAmdProperty()
/*     */   {
/* 252 */     AbstractField tmp = getProperty("amd");
/* 253 */     if ((tmp instanceof TextType))
/*     */     {
/* 255 */       return (TextType)tmp;
/*     */     }
/* 257 */     return null;
/*     */   }
/*     */ 
/*     */   public String getAmd()
/*     */   {
/* 267 */     TextType tmp = getAmdProperty();
/* 268 */     if (tmp == null)
/*     */     {
/* 270 */       for (Attribute attribute : getAllAttributes())
/*     */       {
/* 272 */         if (attribute.getName().equals("amd"))
/*     */         {
/* 274 */           return attribute.getValue();
/*     */         }
/*     */       }
/* 277 */       return null;
/*     */     }
/*     */ 
/* 281 */     return tmp.getStringValue();
/*     */   }
/*     */ 
/*     */   public TextType getConformanceProperty()
/*     */   {
/* 292 */     AbstractField tmp = getProperty("conformance");
/* 293 */     if ((tmp instanceof TextType))
/*     */     {
/* 295 */       return (TextType)tmp;
/*     */     }
/* 297 */     return null;
/*     */   }
/*     */ 
/*     */   public String getConformance()
/*     */   {
/* 307 */     TextType tt = getConformanceProperty();
/* 308 */     if (tt == null)
/*     */     {
/* 310 */       for (Attribute attribute : getAllAttributes())
/*     */       {
/* 312 */         if (attribute.getName().equals("conformance"))
/*     */         {
/* 314 */           return attribute.getValue();
/*     */         }
/*     */       }
/* 317 */       return null;
/*     */     }
/*     */ 
/* 321 */     return tt.getStringValue();
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.xmpbox.schema.PDFAIdentificationSchema
 * JD-Core Version:    0.6.2
 */