/*     */ package com.itextpdf.text.xml.xmp;
/*     */ 
/*     */ import com.itextpdf.text.ExceptionConverter;
/*     */ import com.itextpdf.text.xml.XmlDomWriter;
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.IOException;
/*     */ import javax.xml.parsers.DocumentBuilder;
/*     */ import javax.xml.parsers.DocumentBuilderFactory;
/*     */ import javax.xml.parsers.ParserConfigurationException;
/*     */ import org.w3c.dom.Document;
/*     */ import org.w3c.dom.NamedNodeMap;
/*     */ import org.w3c.dom.Node;
/*     */ import org.w3c.dom.NodeList;
/*     */ import org.xml.sax.SAXException;
/*     */ 
/*     */ @Deprecated
/*     */ public class XmpReader
/*     */ {
/*     */   public static final String EXTRASPACE = "                                                                                                   \n";
/*     */   public static final String XPACKET_PI_BEGIN = "<?xpacket begin=\"﻿\" id=\"W5M0MpCehiHzreSzNTczkc9d\"?>\n";
/*     */   public static final String XPACKET_PI_END_W = "<?xpacket end=\"w\"?>";
/*     */   private Document domDocument;
/*     */ 
/*     */   public XmpReader(byte[] bytes)
/*     */     throws SAXException, IOException
/*     */   {
/*     */     try
/*     */     {
/*  97 */       DocumentBuilderFactory fact = DocumentBuilderFactory.newInstance();
/*  98 */       fact.setNamespaceAware(true);
/*  99 */       DocumentBuilder db = fact.newDocumentBuilder();
/* 100 */       ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
/* 101 */       this.domDocument = db.parse(bais);
/*     */     } catch (ParserConfigurationException e) {
/* 103 */       throw new ExceptionConverter(e);
/*     */     }
/*     */   }
/*     */ 
/*     */   public boolean replaceNode(String namespaceURI, String localName, String value)
/*     */   {
/* 116 */     NodeList nodes = this.domDocument.getElementsByTagNameNS(namespaceURI, localName);
/*     */ 
/* 118 */     if (nodes.getLength() == 0)
/* 119 */       return false;
/* 120 */     for (int i = 0; i < nodes.getLength(); i++) {
/* 121 */       Node node = nodes.item(i);
/* 122 */       setNodeText(this.domDocument, node, value);
/*     */     }
/* 124 */     return true;
/*     */   }
/*     */ 
/*     */   public boolean replaceDescriptionAttribute(String namespaceURI, String localName, String value)
/*     */   {
/* 136 */     NodeList descNodes = this.domDocument.getElementsByTagNameNS("http://www.w3.org/1999/02/22-rdf-syntax-ns#", "Description");
/* 137 */     if (descNodes.getLength() == 0) {
/* 138 */       return false;
/*     */     }
/*     */ 
/* 141 */     for (int i = 0; i < descNodes.getLength(); i++) {
/* 142 */       Node node = descNodes.item(i);
/* 143 */       Node attr = node.getAttributes().getNamedItemNS(namespaceURI, localName);
/* 144 */       if (attr != null) {
/* 145 */         attr.setNodeValue(value);
/* 146 */         return true;
/*     */       }
/*     */     }
/* 149 */     return false;
/*     */   }
/*     */ 
/*     */   public boolean add(String parent, String namespaceURI, String localName, String value)
/*     */   {
/* 162 */     NodeList nodes = this.domDocument.getElementsByTagName(parent);
/* 163 */     if (nodes.getLength() == 0) {
/* 164 */       return false;
/*     */     }
/*     */ 
/* 168 */     for (int i = 0; i < nodes.getLength(); i++) {
/* 169 */       Node pNode = nodes.item(i);
/* 170 */       NamedNodeMap attrs = pNode.getAttributes();
/* 171 */       for (int j = 0; j < attrs.getLength(); j++) {
/* 172 */         Node node = attrs.item(j);
/* 173 */         if (namespaceURI.equals(node.getNodeValue())) {
/* 174 */           String prefix = node.getLocalName();
/* 175 */           node = this.domDocument.createElementNS(namespaceURI, localName);
/* 176 */           node.setPrefix(prefix);
/* 177 */           node.appendChild(this.domDocument.createTextNode(value));
/* 178 */           pNode.appendChild(node);
/* 179 */           return true;
/*     */         }
/*     */       }
/*     */     }
/* 183 */     return false;
/*     */   }
/*     */ 
/*     */   public boolean setNodeText(Document domDocument, Node n, String value)
/*     */   {
/* 194 */     if (n == null)
/* 195 */       return false;
/* 196 */     Node nc = null;
/* 197 */     while ((nc = n.getFirstChild()) != null) {
/* 198 */       n.removeChild(nc);
/*     */     }
/* 200 */     n.appendChild(domDocument.createTextNode(value));
/* 201 */     return true;
/*     */   }
/*     */ 
/*     */   public byte[] serializeDoc()
/*     */     throws IOException
/*     */   {
/* 208 */     XmlDomWriter xw = new XmlDomWriter();
/* 209 */     ByteArrayOutputStream fout = new ByteArrayOutputStream();
/* 210 */     xw.setOutput(fout, null);
/* 211 */     fout.write("<?xpacket begin=\"﻿\" id=\"W5M0MpCehiHzreSzNTczkc9d\"?>\n".getBytes("UTF-8"));
/* 212 */     fout.flush();
/* 213 */     NodeList xmpmeta = this.domDocument.getElementsByTagName("x:xmpmeta");
/* 214 */     xw.write(xmpmeta.item(0));
/* 215 */     fout.flush();
/* 216 */     for (int i = 0; i < 20; i++) {
/* 217 */       fout.write("                                                                                                   \n".getBytes());
/*     */     }
/* 219 */     fout.write("<?xpacket end=\"w\"?>".getBytes());
/* 220 */     fout.close();
/* 221 */     return fout.toByteArray();
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.xml.xmp.XmpReader
 * JD-Core Version:    0.6.2
 */