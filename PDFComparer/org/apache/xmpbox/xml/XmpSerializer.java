/*     */ package org.apache.xmpbox.xml;
/*     */ 
/*     */ import java.io.OutputStream;
/*     */ import java.io.PrintStream;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Map.Entry;
/*     */ import javax.xml.parsers.DocumentBuilder;
/*     */ import javax.xml.parsers.DocumentBuilderFactory;
/*     */ import javax.xml.parsers.ParserConfigurationException;
/*     */ import javax.xml.transform.Result;
/*     */ import javax.xml.transform.Transformer;
/*     */ import javax.xml.transform.TransformerException;
/*     */ import javax.xml.transform.TransformerFactory;
/*     */ import javax.xml.transform.dom.DOMSource;
/*     */ import javax.xml.transform.stream.StreamResult;
/*     */ import org.apache.xmpbox.XMPMetadata;
/*     */ import org.apache.xmpbox.schema.XMPSchema;
/*     */ import org.apache.xmpbox.type.AbstractComplexProperty;
/*     */ import org.apache.xmpbox.type.AbstractField;
/*     */ import org.apache.xmpbox.type.AbstractSimpleProperty;
/*     */ import org.apache.xmpbox.type.AbstractStructuredType;
/*     */ import org.apache.xmpbox.type.ArrayProperty;
/*     */ import org.apache.xmpbox.type.Attribute;
/*     */ import org.w3c.dom.Document;
/*     */ import org.w3c.dom.Element;
/*     */ import org.w3c.dom.Node;
/*     */ import org.w3c.dom.ProcessingInstruction;
/*     */ 
/*     */ public class XmpSerializer
/*     */ {
/*  56 */   private DocumentBuilder documentBuilder = null;
/*     */ 
/*  58 */   private boolean parseTypeResourceForLi = true;
/*     */ 
/*     */   public XmpSerializer()
/*     */     throws XmpSerializationException
/*     */   {
/*  63 */     DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
/*     */     try
/*     */     {
/*  66 */       this.documentBuilder = builderFactory.newDocumentBuilder();
/*     */     }
/*     */     catch (ParserConfigurationException e)
/*     */     {
/*  70 */       throw new XmpSerializationException("Failed to init XmpSerializer", e);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void serialize(XMPMetadata metadata, OutputStream os, boolean withXpacket)
/*     */     throws TransformerException
/*     */   {
/*  77 */     Document doc = this.documentBuilder.newDocument();
/*     */ 
/*  79 */     Element rdf = createRdfElement(doc, metadata, withXpacket);
/*  80 */     for (XMPSchema schema : metadata.getAllSchemas())
/*     */     {
/*  82 */       rdf.appendChild(serializeSchema(doc, schema));
/*     */     }
/*     */ 
/*  85 */     save(doc, os, "UTF-8");
/*     */   }
/*     */ 
/*     */   protected Element serializeSchema(Document doc, XMPSchema schema)
/*     */   {
/*  91 */     Element selem = doc.createElementNS("http://www.w3.org/1999/02/22-rdf-syntax-ns#", "rdf:Description");
/*  92 */     selem.setAttributeNS("http://www.w3.org/1999/02/22-rdf-syntax-ns#", "rdf:about", schema.getAboutValue());
/*  93 */     selem.setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns:" + schema.getPrefix(), schema.getNamespace());
/*     */ 
/*  95 */     fillElementWithAttributes(selem, schema);
/*     */ 
/*  97 */     List fields = schema.getAllProperties();
/*  98 */     serializeFields(doc, selem, fields, true);
/*     */ 
/* 100 */     return selem;
/*     */   }
/*     */ 
/*     */   public void serializeFields(Document doc, Element parent, List<AbstractField> fields, boolean wrapWithProperty)
/*     */   {
/* 105 */     for (AbstractField field : fields)
/*     */     {
/* 108 */       if ((field instanceof AbstractSimpleProperty))
/*     */       {
/* 110 */         AbstractSimpleProperty simple = (AbstractSimpleProperty)field;
/* 111 */         Element esimple = doc.createElement(simple.getPrefix() + ":" + simple.getPropertyName());
/* 112 */         esimple.setTextContent(simple.getStringValue());
/* 113 */         parent.appendChild(esimple);
/*     */       }
/* 115 */       else if ((field instanceof ArrayProperty))
/*     */       {
/* 117 */         ArrayProperty array = (ArrayProperty)field;
/*     */ 
/* 119 */         Element asimple = doc.createElement(array.getPrefix() + ":" + array.getPropertyName());
/* 120 */         parent.appendChild(asimple);
/*     */ 
/* 122 */         fillElementWithAttributes(asimple, array);
/*     */ 
/* 124 */         Element econtainer = doc.createElement("rdf:" + array.getArrayType());
/* 125 */         asimple.appendChild(econtainer);
/*     */ 
/* 127 */         List innerFields = array.getAllProperties();
/* 128 */         serializeFields(doc, econtainer, innerFields, false);
/*     */       }
/* 130 */       else if ((field instanceof AbstractStructuredType))
/*     */       {
/* 132 */         AbstractStructuredType structured = (AbstractStructuredType)field;
/* 133 */         List innerFields = structured.getAllProperties();
/*     */ 
/* 135 */         Element listParent = parent;
/* 136 */         if (wrapWithProperty)
/*     */         {
/* 138 */           Element nstructured = doc.createElement(structured.getPrefix() + ":" + structured.getPropertyName());
/*     */ 
/* 140 */           parent.appendChild(nstructured);
/* 141 */           listParent = nstructured;
/*     */         }
/*     */ 
/* 145 */         Element estructured = doc.createElement("rdf:li");
/* 146 */         listParent.appendChild(estructured);
/* 147 */         if (this.parseTypeResourceForLi)
/*     */         {
/* 149 */           estructured.setAttribute("rdf:parseType", "Resource");
/*     */ 
/* 151 */           serializeFields(doc, estructured, innerFields, true);
/*     */         }
/*     */         else
/*     */         {
/* 156 */           Element econtainer = doc.createElement("rdf:Description");
/* 157 */           estructured.appendChild(econtainer);
/*     */ 
/* 159 */           serializeFields(doc, econtainer, innerFields, true);
/*     */         }
/*     */ 
/*     */       }
/*     */       else
/*     */       {
/* 165 */         System.err.println(">> TODO >> " + field.getClass());
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   private void fillElementWithAttributes(Element target, AbstractComplexProperty property)
/*     */   {
/* 173 */     List attributes = property.getAllAttributes();
/* 174 */     for (Attribute attribute : attributes)
/*     */     {
/* 176 */       if ("http://www.w3.org/1999/02/22-rdf-syntax-ns#".equals(attribute.getNamespace()))
/*     */       {
/* 178 */         target.setAttribute("rdf:" + attribute.getName(), attribute.getValue());
/*     */       }
/* 180 */       else if (target.getNamespaceURI().equals(attribute.getNamespace()))
/*     */       {
/* 182 */         target.setAttribute(attribute.getName(), attribute.getValue());
/*     */       }
/*     */       else
/*     */       {
/* 186 */         target.setAttribute(attribute.getName(), attribute.getValue());
/*     */       }
/*     */     }
/* 189 */     for (Map.Entry ns : property.getAllNamespacesWithPrefix().entrySet())
/*     */     {
/* 191 */       target.setAttribute("xmlns:" + (String)ns.getValue(), (String)ns.getKey());
/*     */     }
/*     */   }
/*     */ 
/*     */   protected Element createRdfElement(Document doc, XMPMetadata metadata, boolean withXpacket)
/*     */   {
/* 198 */     if (withXpacket)
/*     */     {
/* 200 */       ProcessingInstruction beginXPacket = doc.createProcessingInstruction("xpacket", "begin=\"" + metadata.getXpacketBegin() + "\" id=\"" + metadata.getXpacketId() + "\"");
/*     */ 
/* 202 */       doc.appendChild(beginXPacket);
/*     */     }
/*     */ 
/* 205 */     Element xmpmeta = doc.createElementNS("adobe:ns:meta/", "x:xmpmeta");
/* 206 */     xmpmeta.setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns:x", "adobe:ns:meta/");
/* 207 */     doc.appendChild(xmpmeta);
/*     */ 
/* 209 */     if (withXpacket)
/*     */     {
/* 211 */       ProcessingInstruction endXPacket = doc.createProcessingInstruction("xpacket", "end=\"" + metadata.getEndXPacket() + "\"");
/*     */ 
/* 213 */       doc.appendChild(endXPacket);
/*     */     }
/*     */ 
/* 216 */     Element rdf = doc.createElementNS("http://www.w3.org/1999/02/22-rdf-syntax-ns#", "rdf:RDF");
/*     */ 
/* 218 */     xmpmeta.appendChild(rdf);
/*     */ 
/* 220 */     return rdf;
/*     */   }
/*     */ 
/*     */   private void save(Node doc, OutputStream outStream, String encoding)
/*     */     throws TransformerException
/*     */   {
/* 238 */     Transformer transformer = TransformerFactory.newInstance().newTransformer();
/*     */ 
/* 240 */     transformer.setOutputProperty("indent", "yes");
/*     */ 
/* 242 */     transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
/*     */ 
/* 244 */     transformer.setOutputProperty("encoding", encoding);
/* 245 */     transformer.setOutputProperty("omit-xml-declaration", "yes");
/*     */ 
/* 247 */     Result result = new StreamResult(outStream);
/* 248 */     DOMSource source = new DOMSource(doc);
/*     */ 
/* 250 */     transformer.transform(source, result);
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.xmpbox.xml.XmpSerializer
 * JD-Core Version:    0.6.2
 */