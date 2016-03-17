/*     */ package org.apache.jempbox.xmp;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.io.PrintStream;
/*     */ import java.lang.reflect.Constructor;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.LinkedList;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import javax.xml.transform.TransformerException;
/*     */ import org.apache.jempbox.impl.XMLUtil;
/*     */ import org.w3c.dom.Document;
/*     */ import org.w3c.dom.Element;
/*     */ import org.w3c.dom.NamedNodeMap;
/*     */ import org.w3c.dom.Node;
/*     */ import org.w3c.dom.NodeList;
/*     */ import org.w3c.dom.ProcessingInstruction;
/*     */ import org.xml.sax.InputSource;
/*     */ 
/*     */ public class XMPMetadata
/*     */ {
/*     */   public static final String ENCODING_UTF8 = "UTF-8";
/*     */   public static final String ENCODING_UTF16BE = "UTF-16BE";
/*     */   public static final String ENCODING_UTF16LE = "UTF-16LE";
/*     */   protected Document xmpDocument;
/*  73 */   protected String encoding = "UTF-8";
/*     */ 
/*  78 */   protected Map<String, Class<?>> nsMappings = new HashMap();
/*     */ 
/*     */   public XMPMetadata()
/*     */     throws IOException
/*     */   {
/*  88 */     this.xmpDocument = XMLUtil.newDocument();
/*  89 */     ProcessingInstruction beginXPacket = this.xmpDocument.createProcessingInstruction("xpacket", "begin=\"﻿\" id=\"W5M0MpCehiHzreSzNTczkc9d\"");
/*     */ 
/*  93 */     this.xmpDocument.appendChild(beginXPacket);
/*  94 */     Element xmpMeta = this.xmpDocument.createElementNS("adobe:ns:meta/", "x:xmpmeta");
/*     */ 
/*  96 */     xmpMeta.setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns:x", "adobe:ns:meta/");
/*     */ 
/*  99 */     this.xmpDocument.appendChild(xmpMeta);
/*     */ 
/* 101 */     Element rdf = this.xmpDocument.createElement("rdf:RDF");
/* 102 */     rdf.setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns:rdf", "http://www.w3.org/1999/02/22-rdf-syntax-ns#");
/*     */ 
/* 105 */     xmpMeta.appendChild(rdf);
/*     */ 
/* 107 */     ProcessingInstruction endXPacket = this.xmpDocument.createProcessingInstruction("xpacket", "end=\"w\"");
/*     */ 
/* 109 */     this.xmpDocument.appendChild(endXPacket);
/* 110 */     init();
/*     */   }
/*     */ 
/*     */   public XMPMetadata(Document doc)
/*     */   {
/* 121 */     this.xmpDocument = doc;
/* 122 */     init();
/*     */   }
/*     */ 
/*     */   private void init()
/*     */   {
/* 127 */     this.nsMappings.put("http://ns.adobe.com/pdf/1.3/", XMPSchemaPDF.class);
/* 128 */     this.nsMappings.put("http://ns.adobe.com/xap/1.0/", XMPSchemaBasic.class);
/* 129 */     this.nsMappings.put("http://purl.org/dc/elements/1.1/", XMPSchemaDublinCore.class);
/*     */ 
/* 131 */     this.nsMappings.put("http://ns.adobe.com/xap/1.0/mm/", XMPSchemaMediaManagement.class);
/*     */ 
/* 133 */     this.nsMappings.put("http://ns.adobe.com/xap/1.0/rights/", XMPSchemaRightsManagement.class);
/*     */ 
/* 135 */     this.nsMappings.put("http://ns.adobe.com/xap/1.0/bj/", XMPSchemaBasicJobTicket.class);
/*     */ 
/* 137 */     this.nsMappings.put("http://ns.adobe.com/xmp/1.0/DynamicMedia/", XMPSchemaDynamicMedia.class);
/*     */ 
/* 139 */     this.nsMappings.put("http://ns.adobe.com/xap/1.0/t/pg/", XMPSchemaPagedText.class);
/* 140 */     this.nsMappings.put("http://iptc.org/std/Iptc4xmpCore/1.0/xmlns/", XMPSchemaIptc4xmpCore.class);
/*     */ 
/* 142 */     this.nsMappings.put("http://ns.adobe.com/photoshop/1.0/", XMPSchemaPhotoshop.class);
/*     */   }
/*     */ 
/*     */   public void addXMLNSMapping(String namespace, Class<?> xmpSchema)
/*     */   {
/* 159 */     if (!XMPSchema.class.isAssignableFrom(xmpSchema))
/*     */     {
/* 161 */       throw new IllegalArgumentException("Only XMPSchemas can be mapped to.");
/*     */     }
/*     */ 
/* 165 */     this.nsMappings.put(namespace, xmpSchema);
/*     */   }
/*     */ 
/*     */   public XMPSchemaPDF getPDFSchema()
/*     */     throws IOException
/*     */   {
/* 178 */     return (XMPSchemaPDF)getSchemaByClass(XMPSchemaPDF.class);
/*     */   }
/*     */ 
/*     */   public XMPSchemaBasic getBasicSchema()
/*     */     throws IOException
/*     */   {
/* 191 */     return (XMPSchemaBasic)getSchemaByClass(XMPSchemaBasic.class);
/*     */   }
/*     */ 
/*     */   public XMPSchemaDublinCore getDublinCoreSchema()
/*     */     throws IOException
/*     */   {
/* 204 */     return (XMPSchemaDublinCore)getSchemaByClass(XMPSchemaDublinCore.class);
/*     */   }
/*     */ 
/*     */   public XMPSchemaMediaManagement getMediaManagementSchema()
/*     */     throws IOException
/*     */   {
/* 218 */     return (XMPSchemaMediaManagement)getSchemaByClass(XMPSchemaMediaManagement.class);
/*     */   }
/*     */ 
/*     */   public XMPSchemaRightsManagement getRightsManagementSchema()
/*     */     throws IOException
/*     */   {
/* 232 */     return (XMPSchemaRightsManagement)getSchemaByClass(XMPSchemaRightsManagement.class);
/*     */   }
/*     */ 
/*     */   public XMPSchemaBasicJobTicket getBasicJobTicketSchema()
/*     */     throws IOException
/*     */   {
/* 245 */     return (XMPSchemaBasicJobTicket)getSchemaByClass(XMPSchemaBasicJobTicket.class);
/*     */   }
/*     */ 
/*     */   public XMPSchemaDynamicMedia getDynamicMediaSchema()
/*     */     throws IOException
/*     */   {
/* 258 */     return (XMPSchemaDynamicMedia)getSchemaByClass(XMPSchemaDynamicMedia.class);
/*     */   }
/*     */ 
/*     */   public XMPSchemaPagedText getPagedTextSchema()
/*     */     throws IOException
/*     */   {
/* 271 */     return (XMPSchemaPagedText)getSchemaByClass(XMPSchemaPagedText.class);
/*     */   }
/*     */ 
/*     */   public XMPSchemaMediaManagement addMediaManagementSchema()
/*     */   {
/* 281 */     XMPSchemaMediaManagement schema = new XMPSchemaMediaManagement(this);
/* 282 */     return (XMPSchemaMediaManagement)basicAddSchema(schema);
/*     */   }
/*     */ 
/*     */   public XMPSchemaRightsManagement addRightsManagementSchema()
/*     */   {
/* 292 */     XMPSchemaRightsManagement schema = new XMPSchemaRightsManagement(this);
/* 293 */     return (XMPSchemaRightsManagement)basicAddSchema(schema);
/*     */   }
/*     */ 
/*     */   public XMPSchemaBasicJobTicket addBasicJobTicketSchema()
/*     */   {
/* 303 */     XMPSchemaBasicJobTicket schema = new XMPSchemaBasicJobTicket(this);
/* 304 */     return (XMPSchemaBasicJobTicket)basicAddSchema(schema);
/*     */   }
/*     */ 
/*     */   public XMPSchemaDynamicMedia addDynamicMediaSchema()
/*     */   {
/* 314 */     XMPSchemaDynamicMedia schema = new XMPSchemaDynamicMedia(this);
/* 315 */     return (XMPSchemaDynamicMedia)basicAddSchema(schema);
/*     */   }
/*     */ 
/*     */   public XMPSchemaPagedText addPagedTextSchema()
/*     */   {
/* 325 */     XMPSchemaPagedText schema = new XMPSchemaPagedText(this);
/* 326 */     return (XMPSchemaPagedText)basicAddSchema(schema);
/*     */   }
/*     */ 
/*     */   public void addSchema(XMPSchema schema)
/*     */   {
/* 338 */     Element rdf = getRDFElement();
/* 339 */     rdf.appendChild(schema.getElement());
/*     */   }
/*     */ 
/*     */   public void save(String file)
/*     */     throws Exception
/*     */   {
/* 353 */     XMLUtil.save(this.xmpDocument, file, this.encoding);
/*     */   }
/*     */ 
/*     */   public void save(OutputStream outStream)
/*     */     throws TransformerException
/*     */   {
/* 367 */     XMLUtil.save(this.xmpDocument, outStream, this.encoding);
/*     */   }
/*     */ 
/*     */   public byte[] asByteArray()
/*     */     throws Exception
/*     */   {
/* 379 */     return XMLUtil.asByteArray(this.xmpDocument, this.encoding);
/*     */   }
/*     */ 
/*     */   public Document getXMPDocument()
/*     */   {
/* 389 */     return this.xmpDocument;
/*     */   }
/*     */ 
/*     */   protected XMPSchema basicAddSchema(XMPSchema schema)
/*     */   {
/* 402 */     Element rdf = getRDFElement();
/* 403 */     rdf.appendChild(schema.getElement());
/* 404 */     return schema;
/*     */   }
/*     */ 
/*     */   public XMPSchemaPDF addPDFSchema()
/*     */   {
/* 417 */     XMPSchemaPDF schema = new XMPSchemaPDF(this);
/* 418 */     return (XMPSchemaPDF)basicAddSchema(schema);
/*     */   }
/*     */ 
/*     */   public XMPSchemaDublinCore addDublinCoreSchema()
/*     */   {
/* 431 */     XMPSchemaDublinCore schema = new XMPSchemaDublinCore(this);
/* 432 */     return (XMPSchemaDublinCore)basicAddSchema(schema);
/*     */   }
/*     */ 
/*     */   public XMPSchemaBasic addBasicSchema()
/*     */   {
/* 445 */     XMPSchemaBasic schema = new XMPSchemaBasic(this);
/* 446 */     return (XMPSchemaBasic)basicAddSchema(schema);
/*     */   }
/*     */ 
/*     */   public XMPSchemaIptc4xmpCore addIptc4xmpCoreSchema()
/*     */   {
/* 456 */     XMPSchemaIptc4xmpCore schema = new XMPSchemaIptc4xmpCore(this);
/* 457 */     return (XMPSchemaIptc4xmpCore)basicAddSchema(schema);
/*     */   }
/*     */ 
/*     */   public XMPSchemaPhotoshop addPhotoshopSchema()
/*     */   {
/* 467 */     XMPSchemaPhotoshop schema = new XMPSchemaPhotoshop(this);
/* 468 */     return (XMPSchemaPhotoshop)basicAddSchema(schema);
/*     */   }
/*     */ 
/*     */   public void setEncoding(String xmlEncoding)
/*     */   {
/* 480 */     this.encoding = xmlEncoding;
/*     */   }
/*     */ 
/*     */   public String getEncoding()
/*     */   {
/* 490 */     return this.encoding;
/*     */   }
/*     */ 
/*     */   private Element getRDFElement()
/*     */   {
/* 500 */     Element rdf = null;
/* 501 */     NodeList nodes = this.xmpDocument.getElementsByTagName("rdf:RDF");
/* 502 */     if (nodes.getLength() > 0)
/*     */     {
/* 504 */       rdf = (Element)nodes.item(0);
/*     */     }
/* 506 */     return rdf;
/*     */   }
/*     */ 
/*     */   public static XMPMetadata load(String file)
/*     */     throws IOException
/*     */   {
/* 522 */     return new XMPMetadata(XMLUtil.parse(file));
/*     */   }
/*     */ 
/*     */   public static XMPMetadata load(InputSource is)
/*     */     throws IOException
/*     */   {
/* 538 */     return new XMPMetadata(XMLUtil.parse(is));
/*     */   }
/*     */ 
/*     */   public static XMPMetadata load(InputStream is)
/*     */     throws IOException
/*     */   {
/* 554 */     return new XMPMetadata(XMLUtil.parse(is));
/*     */   }
/*     */ 
/*     */   public static void main(String[] args)
/*     */     throws Exception
/*     */   {
/* 567 */     XMPMetadata metadata = new XMPMetadata();
/* 568 */     XMPSchemaPDF pdf = metadata.addPDFSchema();
/* 569 */     pdf.setAbout("uuid:b8659d3a-369e-11d9-b951-000393c97fd8");
/* 570 */     pdf.setKeywords("ben,bob,pdf");
/* 571 */     pdf.setPDFVersion("1.3");
/* 572 */     pdf.setProducer("Acrobat Distiller 6.0.1 for Macintosh");
/*     */ 
/* 574 */     XMPSchemaDublinCore dc = metadata.addDublinCoreSchema();
/* 575 */     dc.addContributor("Ben Litchfield");
/* 576 */     dc.addContributor("Solar Eclipse");
/* 577 */     dc.addContributor("Some Other Guy");
/*     */ 
/* 579 */     XMPSchemaBasic basic = metadata.addBasicSchema();
/* 580 */     Thumbnail t = new Thumbnail(metadata);
/* 581 */     t.setFormat("JPEG");
/* 582 */     t.setImage("IMAGE_DATA");
/* 583 */     t.setHeight(new Integer(100));
/* 584 */     t.setWidth(new Integer(200));
/* 585 */     basic.setThumbnail(t);
/* 586 */     basic.setBaseURL("http://www.pdfbox.org/");
/*     */ 
/* 588 */     List schemas = metadata.getSchemas();
/* 589 */     System.out.println("schemas=" + schemas);
/*     */ 
/* 591 */     metadata.save("test.xmp");
/*     */   }
/*     */ 
/*     */   public List<XMPSchema> getSchemas()
/*     */     throws IOException
/*     */   {
/* 605 */     NodeList schemaList = this.xmpDocument.getElementsByTagName("rdf:Description");
/*     */ 
/* 607 */     List retval = new ArrayList(schemaList.getLength());
/* 608 */     for (int i = 0; i < schemaList.getLength(); i++)
/*     */     {
/* 610 */       Element schema = (Element)schemaList.item(i);
/* 611 */       boolean found = false;
/* 612 */       NamedNodeMap attributes = schema.getAttributes();
/* 613 */       for (int j = 0; j < attributes.getLength(); j++)
/*     */       {
/* 615 */         Node attribute = attributes.item(j);
/* 616 */         String name = attribute.getNodeName();
/* 617 */         String value = attribute.getNodeValue();
/* 618 */         if ((name.startsWith("xmlns:")) && (this.nsMappings.containsKey(value)))
/*     */         {
/* 620 */           Class schemaClass = (Class)this.nsMappings.get(value);
/*     */           try
/*     */           {
/* 623 */             Constructor ctor = schemaClass.getConstructor(new Class[] { Element.class, String.class });
/*     */ 
/* 626 */             retval.add((XMPSchema)ctor.newInstance(new Object[] { schema, name.substring(6) }));
/*     */ 
/* 628 */             found = true;
/*     */           }
/*     */           catch (NoSuchMethodException e)
/*     */           {
/* 632 */             throw new IOException("Error: Class " + schemaClass.getName() + " must have a constructor with the signature of " + schemaClass.getName() + "( org.w3c.dom.Element, java.lang.String )");
/*     */           }
/*     */           catch (Exception e)
/*     */           {
/* 641 */             e.printStackTrace();
/* 642 */             throw new IOException(e.getMessage());
/*     */           }
/*     */         }
/*     */       }
/* 646 */       if (!found)
/*     */       {
/* 648 */         retval.add(new XMPSchema(schema, null));
/*     */       }
/*     */     }
/* 651 */     return retval;
/*     */   }
/*     */ 
/*     */   public List<XMPSchema> getSchemasByNamespaceURI(String namespaceURI)
/*     */     throws IOException
/*     */   {
/* 670 */     List result = new LinkedList();
/*     */ 
/* 672 */     Class schemaClass = (Class)this.nsMappings.get(namespaceURI);
/* 673 */     if (schemaClass == null)
/*     */     {
/* 675 */       return result;
/*     */     }
/*     */ 
/* 678 */     Iterator i = getSchemas().iterator();
/* 679 */     while (i.hasNext())
/*     */     {
/* 681 */       XMPSchema schema = (XMPSchema)i.next();
/* 682 */       if (schemaClass.isAssignableFrom(schema.getClass()))
/*     */       {
/* 684 */         result.add(schema);
/*     */       }
/*     */     }
/* 687 */     return result;
/*     */   }
/*     */ 
/*     */   public boolean hasUnknownSchema()
/*     */     throws IOException
/*     */   {
/* 700 */     NodeList schemaList = this.xmpDocument.getElementsByTagName("rdf:Description");
/*     */ 
/* 702 */     for (int i = 0; i < schemaList.getLength(); i++)
/*     */     {
/* 704 */       Element schema = (Element)schemaList.item(i);
/* 705 */       NamedNodeMap attributes = schema.getAttributes();
/* 706 */       for (int j = 0; j < attributes.getLength(); j++)
/*     */       {
/* 708 */         Node attribute = attributes.item(j);
/* 709 */         String name = attribute.getNodeName();
/* 710 */         String value = attribute.getNodeValue();
/* 711 */         if ((name.startsWith("xmlns:")) && (!this.nsMappings.containsKey(value)) && (!value.equals("http://ns.adobe.com/xap/1.0/sType/ResourceEvent#")))
/*     */         {
/* 714 */           return true;
/*     */         }
/*     */       }
/*     */     }
/* 718 */     return false;
/*     */   }
/*     */ 
/*     */   public XMPSchema getSchemaByClass(Class<?> targetSchema)
/*     */     throws IOException
/*     */   {
/* 734 */     Iterator iter = getSchemas().iterator();
/* 735 */     while (iter.hasNext())
/*     */     {
/* 737 */       XMPSchema element = (XMPSchema)iter.next();
/* 738 */       if (element.getClass().getName().equals(targetSchema.getName()))
/*     */       {
/* 740 */         return element;
/*     */       }
/*     */     }
/*     */ 
/* 744 */     return null;
/*     */   }
/*     */ 
/*     */   public void merge(XMPMetadata metadata)
/*     */     throws IOException
/*     */   {
/* 756 */     List schemas2 = metadata.getSchemas();
/* 757 */     for (Iterator iterator = schemas2.iterator(); iterator.hasNext(); )
/*     */     {
/* 759 */       XMPSchema schema2 = (XMPSchema)iterator.next();
/* 760 */       XMPSchema schema1 = getSchemaByClass(schema2.getClass());
/* 761 */       if (schema1 == null)
/*     */       {
/* 763 */         Element rdf = getRDFElement();
/* 764 */         rdf.appendChild(this.xmpDocument.importNode(schema2.getElement(), true));
/*     */       }
/*     */       else
/*     */       {
/* 769 */         schema1.merge(schema2);
/*     */       }
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.jempbox.xmp.XMPMetadata
 * JD-Core Version:    0.6.2
 */