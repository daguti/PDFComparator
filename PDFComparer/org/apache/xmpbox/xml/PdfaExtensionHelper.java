/*     */ package org.apache.xmpbox.xml;
/*     */ 
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Map.Entry;
/*     */ import org.apache.xmpbox.XMPMetadata;
/*     */ import org.apache.xmpbox.schema.PDFAExtensionSchema;
/*     */ import org.apache.xmpbox.schema.XMPSchema;
/*     */ import org.apache.xmpbox.schema.XMPSchemaFactory;
/*     */ import org.apache.xmpbox.type.AbstractField;
/*     */ import org.apache.xmpbox.type.AbstractStructuredType;
/*     */ import org.apache.xmpbox.type.ArrayProperty;
/*     */ import org.apache.xmpbox.type.Cardinality;
/*     */ import org.apache.xmpbox.type.DefinedStructuredType;
/*     */ import org.apache.xmpbox.type.PDFAFieldType;
/*     */ import org.apache.xmpbox.type.PDFAPropertyType;
/*     */ import org.apache.xmpbox.type.PDFASchemaType;
/*     */ import org.apache.xmpbox.type.PDFATypeType;
/*     */ import org.apache.xmpbox.type.PropertiesDescription;
/*     */ import org.apache.xmpbox.type.PropertyType;
/*     */ import org.apache.xmpbox.type.StructuredType;
/*     */ import org.apache.xmpbox.type.TypeMapping;
/*     */ import org.apache.xmpbox.type.Types;
/*     */ import org.w3c.dom.Attr;
/*     */ import org.w3c.dom.Element;
/*     */ import org.w3c.dom.NamedNodeMap;
/*     */ 
/*     */ public final class PdfaExtensionHelper
/*     */ {
/*     */   public static void validateNaming(XMPMetadata meta, Element description)
/*     */     throws XmpParsingException
/*     */   {
/*  59 */     NamedNodeMap nnm = description.getAttributes();
/*  60 */     for (int i = 0; i < nnm.getLength(); i++)
/*     */     {
/*  62 */       Attr attr = (Attr)nnm.item(i);
/*  63 */       checkNamespaceDeclaration(attr, PDFAExtensionSchema.class);
/*  64 */       checkNamespaceDeclaration(attr, PDFAFieldType.class);
/*  65 */       checkNamespaceDeclaration(attr, PDFAPropertyType.class);
/*  66 */       checkNamespaceDeclaration(attr, PDFASchemaType.class);
/*  67 */       checkNamespaceDeclaration(attr, PDFATypeType.class);
/*     */     }
/*     */   }
/*     */ 
/*     */   private static void checkNamespaceDeclaration(Attr attr, Class<? extends AbstractStructuredType> clz)
/*     */     throws XmpParsingException
/*     */   {
/*  74 */     String prefix = attr.getLocalName();
/*  75 */     String namespace = attr.getValue();
/*  76 */     String cprefix = ((StructuredType)clz.getAnnotation(StructuredType.class)).preferedPrefix();
/*  77 */     String cnamespace = ((StructuredType)clz.getAnnotation(StructuredType.class)).namespace();
/*     */ 
/*  79 */     if ((cprefix.equals(prefix)) && (!cnamespace.equals(namespace)))
/*     */     {
/*  81 */       throw new XmpParsingException(XmpParsingException.ErrorType.InvalidPdfaSchema, "Invalid PDF/A namespace definition");
/*     */     }
/*  83 */     if ((cnamespace.equals(namespace)) && (!cprefix.equals(prefix)))
/*     */     {
/*  85 */       throw new XmpParsingException(XmpParsingException.ErrorType.InvalidPdfaSchema, "Invalid PDF/A namespace definition");
/*     */     }
/*     */   }
/*     */ 
/*     */   public static void populateSchemaMapping(XMPMetadata meta)
/*     */     throws XmpParsingException
/*     */   {
/*  92 */     List schems = meta.getAllSchemas();
/*  93 */     TypeMapping tm = meta.getTypeMapping();
/*  94 */     StructuredType stPdfaExt = (StructuredType)PDFAExtensionSchema.class.getAnnotation(StructuredType.class);
/*  95 */     for (XMPSchema xmpSchema : schems)
/*     */     {
/*  97 */       if (xmpSchema.getNamespace().equals(stPdfaExt.namespace()))
/*     */       {
/* 101 */         if (!xmpSchema.getPrefix().equals(stPdfaExt.preferedPrefix()))
/*     */         {
/* 103 */           throw new XmpParsingException(XmpParsingException.ErrorType.InvalidPrefix, "Found invalid prefix for PDF/A extension, found '" + xmpSchema.getPrefix() + "', should be '" + stPdfaExt.preferedPrefix() + "'");
/*     */         }
/*     */ 
/* 108 */         PDFAExtensionSchema pes = (PDFAExtensionSchema)xmpSchema;
/* 109 */         ArrayProperty sp = pes.getSchemasProperty();
/* 110 */         for (AbstractField af : sp.getAllProperties())
/*     */         {
/* 112 */           if ((af instanceof PDFASchemaType))
/*     */           {
/* 114 */             PDFASchemaType st = (PDFASchemaType)af;
/* 115 */             String namespaceUri = st.getNamespaceURI();
/* 116 */             String prefix = st.getPrefixValue();
/* 117 */             ArrayProperty properties = st.getProperty();
/* 118 */             ArrayProperty valueTypes = st.getValueType();
/* 119 */             xsf = tm.getSchemaFactory(namespaceUri);
/*     */ 
/* 121 */             if (xsf == null)
/*     */             {
/* 124 */               tm.addNewNameSpace(namespaceUri, prefix);
/* 125 */               xsf = tm.getSchemaFactory(namespaceUri);
/*     */             }
/*     */ 
/* 128 */             if (valueTypes != null)
/*     */             {
/* 130 */               for (AbstractField af2 : valueTypes.getAllProperties())
/*     */               {
/* 132 */                 if ((af2 instanceof PDFATypeType))
/*     */                 {
/* 134 */                   PDFATypeType type = (PDFATypeType)af2;
/* 135 */                   String ttype = type.getType();
/* 136 */                   String tns = type.getNamespaceURI();
/* 137 */                   String tprefix = type.getPrefixValue();
/* 138 */                   String tdescription = type.getDescription();
/* 139 */                   ArrayProperty fields = type.getFields();
/* 140 */                   if ((ttype == null) || (tns == null) || (tprefix == null) || (tdescription == null))
/*     */                   {
/* 143 */                     throw new XmpParsingException(XmpParsingException.ErrorType.RequiredProperty, "Missing field in type definition");
/*     */                   }
/*     */ 
/* 147 */                   DefinedStructuredType structuredType = new DefinedStructuredType(meta, tns, tprefix, null);
/*     */ 
/* 152 */                   if (fields != null)
/*     */                   {
/* 154 */                     List definedFields = fields.getAllProperties();
/* 155 */                     for (AbstractField af3 : definedFields)
/*     */                     {
/* 157 */                       if ((af3 instanceof PDFAFieldType))
/*     */                       {
/* 159 */                         PDFAFieldType field = (PDFAFieldType)af3;
/* 160 */                         String fName = field.getName();
/* 161 */                         String fDescription = field.getDescription();
/* 162 */                         String fValueType = field.getValueType();
/* 163 */                         if ((fName == null) || (fDescription == null) || (fValueType == null))
/*     */                         {
/* 165 */                           throw new XmpParsingException(XmpParsingException.ErrorType.RequiredProperty, "Missing field in field definition");
/*     */                         }
/*     */ 
/*     */                         try
/*     */                         {
/* 170 */                           Types fValue = Types.valueOf(fValueType);
/* 171 */                           structuredType.addProperty(fName, TypeMapping.createPropertyType(fValue, Cardinality.Simple));
/*     */                         }
/*     */                         catch (IllegalArgumentException e)
/*     */                         {
/* 176 */                           throw new XmpParsingException(XmpParsingException.ErrorType.NoValueType, "Type not defined : " + fValueType, e);
/*     */                         }
/*     */ 
/*     */                       }
/*     */ 
/*     */                     }
/*     */ 
/*     */                   }
/*     */ 
/* 185 */                   PropertiesDescription pm = new PropertiesDescription();
/*     */ 
/* 187 */                   for (Map.Entry entry : structuredType.getDefinedProperties().entrySet())
/*     */                   {
/* 189 */                     pm.addNewProperty((String)entry.getKey(), (PropertyType)entry.getValue());
/*     */                   }
/* 191 */                   tm.addToDefinedStructuredTypes(ttype, tns, pm);
/*     */                 }
/*     */               }
/*     */             }
/*     */ 
/* 196 */             if (properties == null)
/*     */             {
/* 198 */               throw new XmpParsingException(XmpParsingException.ErrorType.RequiredProperty, "Missing pdfaSchema:property in type definition");
/*     */             }
/*     */ 
/* 201 */             for (AbstractField af2 : properties.getAllProperties())
/*     */             {
/* 203 */               if ((af2 instanceof PDFAPropertyType))
/*     */               {
/* 205 */                 PDFAPropertyType property = (PDFAPropertyType)af2;
/* 206 */                 String pname = property.getName();
/* 207 */                 String ptype = property.getValueType();
/* 208 */                 String pdescription = property.getDescription();
/* 209 */                 String pCategory = property.getCategory();
/*     */ 
/* 211 */                 if ((pname == null) || (ptype == null) || (pdescription == null) || (pCategory == null))
/*     */                 {
/* 214 */                   throw new XmpParsingException(XmpParsingException.ErrorType.RequiredProperty, "Missing field in property definition");
/*     */                 }
/*     */ 
/* 218 */                 PropertyType pt = transformValueType(tm, ptype);
/* 219 */                 if (pt.type() == null)
/*     */                 {
/* 221 */                   throw new XmpParsingException(XmpParsingException.ErrorType.NoValueType, "Type not defined : " + ptype);
/*     */                 }
/* 223 */                 if ((pt.type().isSimple()) || (pt.type().isStructured()) || (pt.type() == Types.DefinedType))
/*     */                 {
/* 226 */                   xsf.getPropertyDefinition().addNewProperty(pname, pt);
/*     */                 }
/*     */                 else
/*     */                 {
/* 230 */                   throw new XmpParsingException(XmpParsingException.ErrorType.NoValueType, "Type not defined : " + ptype);
/*     */                 }
/*     */               }
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */     XMPSchemaFactory xsf;
/*     */   }
/*     */ 
/*     */   private static PropertyType transformValueType(TypeMapping tm, String valueType) throws XmpParsingException
/*     */   {
/* 243 */     if ("Lang Alt".equals(valueType))
/*     */     {
/* 245 */       return TypeMapping.createPropertyType(Types.LangAlt, Cardinality.Simple);
/*     */     }
/*     */ 
/* 248 */     int pos = valueType.indexOf(' ');
/* 249 */     Cardinality card = Cardinality.Simple;
/* 250 */     if (pos > 0)
/*     */     {
/* 252 */       String scard = valueType.substring(0, pos);
/* 253 */       if ("seq".equals(scard))
/*     */       {
/* 255 */         card = Cardinality.Seq;
/*     */       }
/* 257 */       else if ("bag".equals(scard))
/*     */       {
/* 259 */         card = Cardinality.Bag;
/*     */       }
/* 261 */       else if ("alt".equals(scard))
/*     */       {
/* 263 */         card = Cardinality.Alt;
/*     */       }
/*     */       else
/*     */       {
/* 267 */         return null;
/*     */       }
/*     */     }
/* 270 */     String vt = valueType.substring(pos + 1);
/* 271 */     Types type = null;
/*     */     try
/*     */     {
/* 274 */       type = pos < 0 ? Types.valueOf(valueType) : Types.valueOf(vt);
/*     */     }
/*     */     catch (IllegalArgumentException e)
/*     */     {
/* 278 */       if (tm.isDefinedType(vt))
/*     */       {
/* 280 */         type = Types.DefinedType;
/*     */       }
/*     */     }
/* 283 */     return TypeMapping.createPropertyType(type, card);
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.xmpbox.xml.PdfaExtensionHelper
 * JD-Core Version:    0.6.2
 */