/*     */ package org.apache.xmpbox;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import org.apache.xmpbox.schema.AdobePDFSchema;
/*     */ import org.apache.xmpbox.schema.DublinCoreSchema;
/*     */ import org.apache.xmpbox.schema.PDFAExtensionSchema;
/*     */ import org.apache.xmpbox.schema.PDFAIdentificationSchema;
/*     */ import org.apache.xmpbox.schema.PhotoshopSchema;
/*     */ import org.apache.xmpbox.schema.XMPBasicJobTicketSchema;
/*     */ import org.apache.xmpbox.schema.XMPBasicSchema;
/*     */ import org.apache.xmpbox.schema.XMPMediaManagementSchema;
/*     */ import org.apache.xmpbox.schema.XMPRightsManagementSchema;
/*     */ import org.apache.xmpbox.schema.XMPSchema;
/*     */ import org.apache.xmpbox.schema.XmpSchemaException;
/*     */ import org.apache.xmpbox.type.StructuredType;
/*     */ import org.apache.xmpbox.type.TypeMapping;
/*     */ 
/*     */ public class XMPMetadata
/*     */ {
/*  54 */   private String xpacketId = null;
/*     */ 
/*  56 */   private String xpacketBegin = null;
/*     */ 
/*  58 */   private String xpacketBytes = null;
/*     */ 
/*  60 */   private String xpacketEncoding = null;
/*     */ 
/*  62 */   private String xpacketEndData = "w";
/*     */   private List<XMPSchema> schemas;
/*     */   private TypeMapping typeMapping;
/*     */ 
/*     */   protected XMPMetadata()
/*     */   {
/*  76 */     this("﻿", "W5M0MpCehiHzreSzNTczkc9d", XmpConstants.DEFAULT_XPACKET_BYTES, "UTF-8");
/*     */   }
/*     */ 
/*     */   protected XMPMetadata(String xpacketBegin, String xpacketId, String xpacketBytes, String xpacketEncoding)
/*     */   {
/*  97 */     this.schemas = new ArrayList();
/*  98 */     this.typeMapping = new TypeMapping(this);
/*     */ 
/* 100 */     this.xpacketBegin = xpacketBegin;
/* 101 */     this.xpacketId = xpacketId;
/* 102 */     this.xpacketBytes = xpacketBytes;
/* 103 */     this.xpacketEncoding = xpacketEncoding;
/*     */   }
/*     */ 
/*     */   public static XMPMetadata createXMPMetadata()
/*     */   {
/* 108 */     return new XMPMetadata();
/*     */   }
/*     */ 
/*     */   public static XMPMetadata createXMPMetadata(String xpacketBegin, String xpacketId, String xpacketBytes, String xpacketEncoding)
/*     */   {
/* 114 */     return new XMPMetadata(xpacketBegin, xpacketId, xpacketBytes, xpacketEncoding);
/*     */   }
/*     */ 
/*     */   public TypeMapping getTypeMapping()
/*     */   {
/* 119 */     return this.typeMapping;
/*     */   }
/*     */ 
/*     */   public String getXpacketBytes()
/*     */   {
/* 129 */     return this.xpacketBytes;
/*     */   }
/*     */ 
/*     */   public String getXpacketEncoding()
/*     */   {
/* 139 */     return this.xpacketEncoding;
/*     */   }
/*     */ 
/*     */   public String getXpacketBegin()
/*     */   {
/* 149 */     return this.xpacketBegin;
/*     */   }
/*     */ 
/*     */   public String getXpacketId()
/*     */   {
/* 159 */     return this.xpacketId;
/*     */   }
/*     */ 
/*     */   public List<XMPSchema> getAllSchemas()
/*     */   {
/* 169 */     ArrayList schem = new ArrayList();
/* 170 */     Iterator it = this.schemas.iterator();
/* 171 */     while (it.hasNext())
/*     */     {
/* 173 */       schem.add((XMPSchema)it.next());
/*     */     }
/* 175 */     return schem;
/*     */   }
/*     */ 
/*     */   public void setEndXPacket(String data)
/*     */   {
/* 186 */     this.xpacketEndData = data;
/*     */   }
/*     */ 
/*     */   public String getEndXPacket()
/*     */   {
/* 196 */     return this.xpacketEndData;
/*     */   }
/*     */ 
/*     */   public XMPSchema getSchema(String nsURI)
/*     */   {
/* 209 */     Iterator it = this.schemas.iterator();
/*     */ 
/* 211 */     while (it.hasNext())
/*     */     {
/* 213 */       XMPSchema tmp = (XMPSchema)it.next();
/* 214 */       if (tmp.getNamespace().equals(nsURI))
/*     */       {
/* 216 */         return tmp;
/*     */       }
/*     */     }
/* 219 */     return null;
/*     */   }
/*     */ 
/*     */   public XMPSchema getSchema(Class<? extends XMPSchema> clz)
/*     */   {
/* 224 */     StructuredType st = (StructuredType)clz.getAnnotation(StructuredType.class);
/* 225 */     return getSchema(st.namespace());
/*     */   }
/*     */ 
/*     */   public void clearSchemas()
/*     */   {
/* 230 */     this.schemas.clear();
/*     */   }
/*     */ 
/*     */   public XMPSchema getSchema(String prefix, String nsURI)
/*     */   {
/* 245 */     Iterator it = getAllSchemas().iterator();
/*     */ 
/* 247 */     while (it.hasNext())
/*     */     {
/* 249 */       XMPSchema tmp = (XMPSchema)it.next();
/* 250 */       if ((tmp.getNamespace().equals(nsURI)) && (tmp.getPrefix().equals(prefix)))
/*     */       {
/* 252 */         return tmp;
/*     */       }
/*     */     }
/* 255 */     return null;
/*     */   }
/*     */ 
/*     */   public XMPSchema createAndAddDefaultSchema(String nsPrefix, String nsURI)
/*     */   {
/* 269 */     XMPSchema schem = new XMPSchema(this, nsURI, nsPrefix);
/* 270 */     schem.setAboutAsSimple("");
/* 271 */     addSchema(schem);
/* 272 */     return schem;
/*     */   }
/*     */ 
/*     */   public PDFAExtensionSchema createAndAddPDFAExtensionSchemaWithDefaultNS()
/*     */   {
/* 283 */     PDFAExtensionSchema pdfAExt = new PDFAExtensionSchema(this);
/* 284 */     pdfAExt.setAboutAsSimple("");
/* 285 */     addSchema(pdfAExt);
/* 286 */     return pdfAExt;
/*     */   }
/*     */ 
/*     */   public XMPRightsManagementSchema createAndAddXMPRightsManagementSchema()
/*     */   {
/* 297 */     XMPRightsManagementSchema rights = new XMPRightsManagementSchema(this);
/* 298 */     rights.setAboutAsSimple("");
/* 299 */     addSchema(rights);
/* 300 */     return rights;
/*     */   }
/*     */ 
/*     */   public PDFAExtensionSchema createAndAddPDFAExtensionSchemaWithNS(Map<String, String> namespaces)
/*     */     throws XmpSchemaException
/*     */   {
/* 316 */     PDFAExtensionSchema pdfAExt = new PDFAExtensionSchema(this);
/* 317 */     pdfAExt.setAboutAsSimple("");
/* 318 */     addSchema(pdfAExt);
/* 319 */     return pdfAExt;
/*     */   }
/*     */ 
/*     */   public PDFAExtensionSchema getPDFExtensionSchema()
/*     */   {
/* 329 */     return (PDFAExtensionSchema)getSchema(PDFAExtensionSchema.class);
/*     */   }
/*     */ 
/*     */   public PDFAIdentificationSchema createAndAddPFAIdentificationSchema()
/*     */   {
/* 340 */     PDFAIdentificationSchema pdfAId = new PDFAIdentificationSchema(this);
/* 341 */     pdfAId.setAboutAsSimple("");
/* 342 */     addSchema(pdfAId);
/* 343 */     return pdfAId;
/*     */   }
/*     */ 
/*     */   public PDFAIdentificationSchema getPDFIdentificationSchema()
/*     */   {
/* 353 */     return (PDFAIdentificationSchema)getSchema(PDFAIdentificationSchema.class);
/*     */   }
/*     */ 
/*     */   public DublinCoreSchema createAndAddDublinCoreSchema()
/*     */   {
/* 364 */     DublinCoreSchema dc = new DublinCoreSchema(this);
/* 365 */     dc.setAboutAsSimple("");
/* 366 */     addSchema(dc);
/* 367 */     return dc;
/*     */   }
/*     */ 
/*     */   public XMPBasicJobTicketSchema createAndAddBasicJobTicketSchema()
/*     */   {
/* 378 */     XMPBasicJobTicketSchema sc = new XMPBasicJobTicketSchema(this);
/* 379 */     sc.setAboutAsSimple("");
/* 380 */     addSchema(sc);
/* 381 */     return sc;
/*     */   }
/*     */ 
/*     */   public DublinCoreSchema getDublinCoreSchema()
/*     */   {
/* 391 */     return (DublinCoreSchema)getSchema(DublinCoreSchema.class);
/*     */   }
/*     */ 
/*     */   public XMPBasicJobTicketSchema getBasicJobTicketSchema()
/*     */   {
/* 401 */     return (XMPBasicJobTicketSchema)getSchema(XMPBasicJobTicketSchema.class);
/*     */   }
/*     */ 
/*     */   public XMPRightsManagementSchema getXMPRightsManagementSchema()
/*     */   {
/* 411 */     return (XMPRightsManagementSchema)getSchema(XMPRightsManagementSchema.class);
/*     */   }
/*     */ 
/*     */   public PhotoshopSchema getPhotoshopSchema()
/*     */   {
/* 421 */     return (PhotoshopSchema)getSchema("http://ns.adobe.com/photoshop/1.0/");
/*     */   }
/*     */ 
/*     */   public XMPBasicSchema createAndAddXMPBasicSchema()
/*     */   {
/* 431 */     XMPBasicSchema xmpB = new XMPBasicSchema(this);
/* 432 */     xmpB.setAboutAsSimple("");
/* 433 */     addSchema(xmpB);
/* 434 */     return xmpB;
/*     */   }
/*     */ 
/*     */   public XMPBasicSchema getXMPBasicSchema()
/*     */   {
/* 444 */     return (XMPBasicSchema)getSchema(XMPBasicSchema.class);
/*     */   }
/*     */ 
/*     */   public XMPMediaManagementSchema createAndAddXMPMediaManagementSchema()
/*     */   {
/* 455 */     XMPMediaManagementSchema xmpMM = new XMPMediaManagementSchema(this);
/* 456 */     xmpMM.setAboutAsSimple("");
/* 457 */     addSchema(xmpMM);
/* 458 */     return xmpMM;
/*     */   }
/*     */ 
/*     */   public PhotoshopSchema createAndAddPhotoshopSchema()
/*     */   {
/* 468 */     PhotoshopSchema photoshop = new PhotoshopSchema(this);
/* 469 */     photoshop.setAboutAsSimple("");
/* 470 */     addSchema(photoshop);
/* 471 */     return photoshop;
/*     */   }
/*     */ 
/*     */   public XMPMediaManagementSchema getXMPMediaManagementSchema()
/*     */   {
/* 481 */     return (XMPMediaManagementSchema)getSchema(XMPMediaManagementSchema.class);
/*     */   }
/*     */ 
/*     */   public AdobePDFSchema createAndAddAdobePDFSchema()
/*     */   {
/* 491 */     AdobePDFSchema pdf = new AdobePDFSchema(this);
/* 492 */     pdf.setAboutAsSimple("");
/* 493 */     addSchema(pdf);
/* 494 */     return pdf;
/*     */   }
/*     */ 
/*     */   public AdobePDFSchema getAdobePDFSchema()
/*     */   {
/* 504 */     return (AdobePDFSchema)getSchema(AdobePDFSchema.class);
/*     */   }
/*     */ 
/*     */   public void addSchema(XMPSchema obj)
/*     */   {
/* 515 */     this.schemas.add(obj);
/*     */   }
/*     */ 
/*     */   public void removeSchema(XMPSchema schema)
/*     */   {
/* 526 */     this.schemas.remove(schema);
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.xmpbox.XMPMetadata
 * JD-Core Version:    0.6.2
 */