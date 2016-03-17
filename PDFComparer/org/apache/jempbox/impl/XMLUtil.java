/*     */ package org.apache.jempbox.impl;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.io.StringWriter;
/*     */ import java.io.UnsupportedEncodingException;
/*     */ import javax.xml.parsers.DocumentBuilder;
/*     */ import javax.xml.parsers.DocumentBuilderFactory;
/*     */ import javax.xml.transform.Result;
/*     */ import javax.xml.transform.Transformer;
/*     */ import javax.xml.transform.TransformerException;
/*     */ import javax.xml.transform.TransformerFactory;
/*     */ import javax.xml.transform.dom.DOMSource;
/*     */ import javax.xml.transform.stream.StreamResult;
/*     */ import org.apache.jempbox.xmp.Elementable;
/*     */ import org.w3c.dom.Document;
/*     */ import org.w3c.dom.Element;
/*     */ import org.w3c.dom.Node;
/*     */ import org.w3c.dom.NodeList;
/*     */ import org.w3c.dom.Text;
/*     */ import org.xml.sax.InputSource;
/*     */ 
/*     */ public class XMLUtil
/*     */ {
/*     */   public static Document parse(InputStream is)
/*     */     throws IOException
/*     */   {
/*     */     try
/*     */     {
/*  73 */       DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
/*  74 */       DocumentBuilder builder = builderFactory.newDocumentBuilder();
/*  75 */       return builder.parse(is);
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/*  79 */       IOException thrown = new IOException(e.getMessage());
/*  80 */       throw thrown;
/*     */     }
/*     */   }
/*     */ 
/*     */   public static Document parse(InputSource is)
/*     */     throws IOException
/*     */   {
/*     */     try
/*     */     {
/*  95 */       DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
/*  96 */       DocumentBuilder builder = builderFactory.newDocumentBuilder();
/*  97 */       return builder.parse(is);
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 101 */       IOException thrown = new IOException(e.getMessage());
/* 102 */       throw thrown;
/*     */     }
/*     */   }
/*     */ 
/*     */   public static Document parse(String fileName)
/*     */     throws IOException
/*     */   {
/*     */     try
/*     */     {
/* 117 */       DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
/* 118 */       DocumentBuilder builder = builderFactory.newDocumentBuilder();
/* 119 */       return builder.parse(fileName);
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 123 */       IOException thrown = new IOException(e.getMessage());
/* 124 */       throw thrown;
/*     */     }
/*     */   }
/*     */ 
/*     */   public static Document newDocument()
/*     */     throws IOException
/*     */   {
/*     */     try
/*     */     {
/* 139 */       DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
/* 140 */       DocumentBuilder builder = builderFactory.newDocumentBuilder();
/* 141 */       return builder.newDocument();
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 145 */       IOException thrown = new IOException(e.getMessage());
/* 146 */       throw thrown;
/*     */     }
/*     */   }
/*     */ 
/*     */   public static Element getElement(Element parent, String elementName)
/*     */   {
/* 159 */     Element retval = null;
/* 160 */     NodeList children = parent.getElementsByTagName(elementName);
/* 161 */     if (children.getLength() > 0)
/*     */     {
/* 163 */       retval = (Element)children.item(0);
/*     */     }
/* 165 */     return retval;
/*     */   }
/*     */ 
/*     */   public static Integer getIntValue(Element parent, String nodeName)
/*     */   {
/* 178 */     String intVal = getStringValue(getElement(parent, nodeName));
/* 179 */     Integer retval = null;
/* 180 */     if (intVal != null)
/*     */     {
/* 182 */       retval = new Integer(intVal);
/*     */     }
/* 184 */     return retval;
/*     */   }
/*     */ 
/*     */   public static void setIntValue(Element parent, String nodeName, Integer intValue)
/*     */   {
/* 196 */     Element currentValue = getElement(parent, nodeName);
/* 197 */     if (intValue == null)
/*     */     {
/* 199 */       if (currentValue != null)
/*     */       {
/* 201 */         parent.removeChild(currentValue);
/*     */       }
/*     */ 
/*     */     }
/*     */     else
/*     */     {
/* 210 */       if (currentValue == null)
/*     */       {
/* 212 */         currentValue = parent.getOwnerDocument().createElement(nodeName);
/* 213 */         parent.appendChild(currentValue);
/*     */       }
/* 215 */       setStringValue(currentValue, intValue.toString());
/*     */     }
/*     */   }
/*     */ 
/*     */   public static String getStringValue(Element parent, String nodeName)
/*     */   {
/* 229 */     return getStringValue(getElement(parent, nodeName));
/*     */   }
/*     */ 
/*     */   public static void setStringValue(Element parent, String nodeName, String nodeValue)
/*     */   {
/* 241 */     Element currentValue = getElement(parent, nodeName);
/* 242 */     if (nodeValue == null)
/*     */     {
/* 244 */       if (currentValue != null)
/*     */       {
/* 246 */         parent.removeChild(currentValue);
/*     */       }
/*     */ 
/*     */     }
/*     */     else
/*     */     {
/* 255 */       if (currentValue == null)
/*     */       {
/* 257 */         currentValue = parent.getOwnerDocument().createElement(nodeName);
/* 258 */         parent.appendChild(currentValue);
/*     */       }
/* 260 */       setStringValue(currentValue, nodeValue);
/*     */     }
/*     */   }
/*     */ 
/*     */   public static String getStringValue(Element node)
/*     */   {
/* 272 */     String retval = "";
/* 273 */     NodeList children = node.getChildNodes();
/* 274 */     for (int i = 0; i < children.getLength(); i++)
/*     */     {
/* 276 */       Node next = children.item(i);
/* 277 */       if ((next instanceof Text))
/*     */       {
/* 279 */         retval = next.getNodeValue();
/*     */       }
/*     */     }
/* 282 */     return retval;
/*     */   }
/*     */ 
/*     */   public static void setStringValue(Element node, String value)
/*     */   {
/* 293 */     NodeList children = node.getChildNodes();
/* 294 */     for (int i = 0; i < children.getLength(); i++)
/*     */     {
/* 296 */       Node next = children.item(i);
/* 297 */       if ((next instanceof Text))
/*     */       {
/* 299 */         node.removeChild(next);
/*     */       }
/*     */     }
/* 302 */     node.appendChild(node.getOwnerDocument().createTextNode(value));
/*     */   }
/*     */ 
/*     */   public static void setElementableValue(Element parent, String name, Elementable node)
/*     */   {
/* 314 */     NodeList nodes = parent.getElementsByTagName(name);
/* 315 */     if (node == null)
/*     */     {
/* 317 */       for (int i = 0; i < nodes.getLength(); i++)
/*     */       {
/* 319 */         parent.removeChild(nodes.item(i));
/*     */       }
/*     */ 
/*     */     }
/* 324 */     else if (nodes.getLength() == 0)
/*     */     {
/* 326 */       if (parent.hasChildNodes())
/*     */       {
/* 328 */         Node firstChild = parent.getChildNodes().item(0);
/* 329 */         parent.insertBefore(node.getElement(), firstChild);
/*     */       }
/*     */       else
/*     */       {
/* 333 */         parent.appendChild(node.getElement());
/*     */       }
/*     */     }
/*     */     else
/*     */     {
/* 338 */       Node oldNode = nodes.item(0);
/* 339 */       parent.replaceChild(node.getElement(), oldNode);
/*     */     }
/*     */   }
/*     */ 
/*     */   public static void save(Document doc, String file, String encoding)
/*     */     throws TransformerException
/*     */   {
/*     */     try
/*     */     {
/* 358 */       Transformer transformer = TransformerFactory.newInstance().newTransformer();
/* 359 */       transformer.setOutputProperty("indent", "yes");
/* 360 */       transformer.setOutputProperty("encoding", encoding);
/* 361 */       transformer.setOutputProperty("omit-xml-declaration", "yes");
/*     */ 
/* 364 */       Result result = new StreamResult(new File(file));
/* 365 */       DOMSource source = new DOMSource(doc);
/* 366 */       transformer.transform(source, result);
/*     */     }
/*     */     finally
/*     */     {
/*     */     }
/*     */   }
/*     */ 
/*     */   public static void save(Node doc, OutputStream outStream, String encoding)
/*     */     throws TransformerException
/*     */   {
/*     */     try
/*     */     {
/* 388 */       Transformer transformer = TransformerFactory.newInstance().newTransformer();
/* 389 */       transformer.setOutputProperty("indent", "yes");
/* 390 */       transformer.setOutputProperty("encoding", encoding);
/* 391 */       transformer.setOutputProperty("omit-xml-declaration", "yes");
/*     */ 
/* 394 */       Result result = new StreamResult(outStream);
/* 395 */       DOMSource source = new DOMSource(doc);
/* 396 */       transformer.transform(source, result);
/*     */     }
/*     */     finally
/*     */     {
/*     */     }
/*     */   }
/*     */ 
/*     */   public static byte[] asByteArray(Document doc, String encoding)
/*     */     throws TransformerException
/*     */   {
/* 417 */     Transformer transformer = TransformerFactory.newInstance().newTransformer();
/* 418 */     transformer.setOutputProperty("indent", "yes");
/* 419 */     transformer.setOutputProperty("encoding", encoding);
/* 420 */     transformer.setOutputProperty("omit-xml-declaration", "yes");
/*     */ 
/* 422 */     StringWriter writer = new StringWriter();
/* 423 */     Result result = new StreamResult(writer);
/* 424 */     DOMSource source = new DOMSource(doc);
/* 425 */     transformer.transform(source, result);
/*     */     try
/*     */     {
/* 428 */       return writer.getBuffer().toString().getBytes(encoding);
/*     */     }
/*     */     catch (UnsupportedEncodingException e)
/*     */     {
/* 432 */       throw new TransformerException("Unsupported Encoding", e);
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.jempbox.impl.XMLUtil
 * JD-Core Version:    0.6.2
 */