/*     */ package org.apache.xmpbox.xml;
/*     */ 
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Stack;
/*     */ import java.util.StringTokenizer;
/*     */ import javax.xml.namespace.QName;
/*     */ import javax.xml.parsers.DocumentBuilder;
/*     */ import javax.xml.parsers.DocumentBuilderFactory;
/*     */ import javax.xml.parsers.ParserConfigurationException;
/*     */ import org.apache.xmpbox.XMPMetadata;
/*     */ import org.apache.xmpbox.schema.XMPSchema;
/*     */ import org.apache.xmpbox.schema.XMPSchemaFactory;
/*     */ import org.apache.xmpbox.schema.XmpSchemaException;
/*     */ import org.apache.xmpbox.type.AbstractField;
/*     */ import org.apache.xmpbox.type.AbstractSimpleProperty;
/*     */ import org.apache.xmpbox.type.AbstractStructuredType;
/*     */ import org.apache.xmpbox.type.ArrayProperty;
/*     */ import org.apache.xmpbox.type.Attribute;
/*     */ import org.apache.xmpbox.type.BadFieldValueException;
/*     */ import org.apache.xmpbox.type.Cardinality;
/*     */ import org.apache.xmpbox.type.ComplexPropertyContainer;
/*     */ import org.apache.xmpbox.type.PropertiesDescription;
/*     */ import org.apache.xmpbox.type.PropertyType;
/*     */ import org.apache.xmpbox.type.TypeMapping;
/*     */ import org.apache.xmpbox.type.Types;
/*     */ import org.w3c.dom.Attr;
/*     */ import org.w3c.dom.Comment;
/*     */ import org.w3c.dom.Document;
/*     */ import org.w3c.dom.Element;
/*     */ import org.w3c.dom.NamedNodeMap;
/*     */ import org.w3c.dom.Node;
/*     */ import org.w3c.dom.NodeList;
/*     */ import org.w3c.dom.ProcessingInstruction;
/*     */ import org.w3c.dom.Text;
/*     */ import org.xml.sax.SAXException;
/*     */ 
/*     */ public class DomXmpParser
/*     */ {
/*     */   private DocumentBuilder dBuilder;
/*     */   private NamespaceFinder nsFinder;
/*  75 */   private boolean strictParsing = true;
/*     */ 
/*     */   public DomXmpParser() throws XmpParsingException
/*     */   {
/*     */     try
/*     */     {
/*  81 */       DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
/*  82 */       dbFactory.setNamespaceAware(true);
/*  83 */       this.dBuilder = dbFactory.newDocumentBuilder();
/*  84 */       this.nsFinder = new NamespaceFinder();
/*     */     }
/*     */     catch (ParserConfigurationException e)
/*     */     {
/*  88 */       throw new XmpParsingException(XmpParsingException.ErrorType.Configuration, "Failed to initilalize", e);
/*     */     }
/*     */   }
/*     */ 
/*     */   public boolean isStrictParsing()
/*     */   {
/*  95 */     return this.strictParsing;
/*     */   }
/*     */ 
/*     */   public void setStrictParsing(boolean strictParsing)
/*     */   {
/* 100 */     this.strictParsing = strictParsing;
/*     */   }
/*     */ 
/*     */   public XMPMetadata parse(byte[] xmp) throws XmpParsingException
/*     */   {
/* 105 */     ByteArrayInputStream input = new ByteArrayInputStream(xmp);
/* 106 */     return parse(input);
/*     */   }
/*     */ 
/*     */   public XMPMetadata parse(InputStream input) throws XmpParsingException
/*     */   {
/* 111 */     Document document = null;
/*     */     try
/*     */     {
/* 114 */       document = this.dBuilder.parse(input);
/*     */     }
/*     */     catch (SAXException e)
/*     */     {
/* 118 */       throw new XmpParsingException(XmpParsingException.ErrorType.Undefined, "Failed to parse", e);
/*     */     }
/*     */     catch (IOException e)
/*     */     {
/* 122 */       throw new XmpParsingException(XmpParsingException.ErrorType.Undefined, "Failed to parse", e);
/*     */     }
/*     */ 
/* 125 */     XMPMetadata xmp = null;
/*     */ 
/* 128 */     removeComments(document.getFirstChild());
/* 129 */     Node node = document.getFirstChild();
/*     */ 
/* 132 */     if (!(node instanceof ProcessingInstruction))
/*     */     {
/* 134 */       throw new XmpParsingException(XmpParsingException.ErrorType.XpacketBadStart, "xmp should start with a processing instruction");
/*     */     }
/*     */ 
/* 138 */     xmp = parseInitialXpacket((ProcessingInstruction)node);
/* 139 */     node = node.getNextSibling();
/*     */ 
/* 142 */     while ((node instanceof ProcessingInstruction))
/*     */     {
/* 144 */       node = node.getNextSibling();
/*     */     }
/*     */ 
/* 147 */     Element root = null;
/* 148 */     if (!(node instanceof Element))
/*     */     {
/* 150 */       throw new XmpParsingException(XmpParsingException.ErrorType.NoRootElement, "xmp should contain a root element");
/*     */     }
/*     */ 
/* 155 */     root = (Element)node;
/* 156 */     node = node.getNextSibling();
/*     */ 
/* 159 */     if (!(node instanceof ProcessingInstruction))
/*     */     {
/* 161 */       throw new XmpParsingException(XmpParsingException.ErrorType.XpacketBadEnd, "xmp should end with a processing instruction");
/*     */     }
/*     */ 
/* 165 */     parseEndPacket(xmp, (ProcessingInstruction)node);
/* 166 */     node = node.getNextSibling();
/*     */ 
/* 169 */     if (node != null)
/*     */     {
/* 171 */       throw new XmpParsingException(XmpParsingException.ErrorType.XpacketBadEnd, "xmp should end after xpacket end processing instruction");
/*     */     }
/*     */ 
/* 176 */     Element rdfRdf = findDescriptionsParent(root);
/* 177 */     List descriptions = DomHelper.getElementChildren(rdfRdf);
/* 178 */     List dataDescriptions = new ArrayList(descriptions.size());
/* 179 */     for (Element description : descriptions)
/*     */     {
/* 181 */       Element first = DomHelper.getFirstChildElement(description);
/* 182 */       if ((first != null) && ("pdfaExtension".equals(first.getPrefix())))
/*     */       {
/* 184 */         PdfaExtensionHelper.validateNaming(xmp, description);
/* 185 */         parseDescriptionRoot(xmp, description);
/*     */       }
/*     */       else
/*     */       {
/* 189 */         dataDescriptions.add(description);
/*     */       }
/*     */     }
/*     */ 
/* 193 */     PdfaExtensionHelper.populateSchemaMapping(xmp);
/*     */ 
/* 195 */     for (Element description : dataDescriptions)
/*     */     {
/* 197 */       parseDescriptionRoot(xmp, description);
/*     */     }
/*     */ 
/* 200 */     return xmp;
/*     */   }
/*     */ 
/*     */   private void parseDescriptionRoot(XMPMetadata xmp, Element description) throws XmpParsingException
/*     */   {
/* 205 */     this.nsFinder.push(description);
/* 206 */     TypeMapping tm = xmp.getTypeMapping();
/*     */     try
/*     */     {
/* 209 */       List properties = DomHelper.getElementChildren(description);
/*     */ 
/* 211 */       NamedNodeMap nnm = description.getAttributes();
/* 212 */       for (int i = 0; i < nnm.getLength(); i++)
/*     */       {
/* 214 */         Attr attr = (Attr)nnm.item(i);
/* 215 */         if (!"xmlns".equals(attr.getPrefix()))
/*     */         {
/* 219 */           if ((!"rdf".equals(attr.getPrefix())) || (!"about".equals(attr.getLocalName())))
/*     */           {
/* 224 */             if ((attr.getPrefix() != null) || (!"about".equals(attr.getLocalName())))
/*     */             {
/* 230 */               String namespace = attr.getNamespaceURI();
/* 231 */               XMPSchema schema = xmp.getSchema(namespace);
/* 232 */               if (schema == null)
/*     */               {
/* 234 */                 schema = tm.getSchemaFactory(namespace).createXMPSchema(xmp, attr.getPrefix());
/* 235 */                 loadAttributes(schema, description);
/*     */               }
/* 237 */               ComplexPropertyContainer container = schema.getContainer();
/* 238 */               PropertyType type = checkPropertyDefinition(xmp, new QName(attr.getNamespaceURI(), attr.getLocalName()));
/*     */ 
/* 240 */               AbstractSimpleProperty sp = tm.instanciateSimpleProperty(namespace, schema.getPrefix(), attr.getLocalName(), attr.getValue(), type.type());
/*     */ 
/* 242 */               container.addProperty(sp);
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/* 247 */       for (Element property : properties)
/*     */       {
/* 249 */         String namespace = property.getNamespaceURI();
/* 250 */         PropertyType type = checkPropertyDefinition(xmp, DomHelper.getQName(property));
/*     */ 
/* 252 */         if (!tm.isDefinedSchema(namespace))
/*     */         {
/* 254 */           throw new XmpParsingException(XmpParsingException.ErrorType.NoSchema, "This namespace is not a schema or a structured type : " + namespace);
/*     */         }
/*     */ 
/* 257 */         XMPSchema schema = xmp.getSchema(namespace);
/* 258 */         if (schema == null)
/*     */         {
/* 260 */           schema = tm.getSchemaFactory(namespace).createXMPSchema(xmp, property.getPrefix());
/* 261 */           loadAttributes(schema, description);
/*     */         }
/* 263 */         ComplexPropertyContainer container = schema.getContainer();
/*     */ 
/* 265 */         createProperty(xmp, property, type, container);
/*     */       }
/*     */     }
/*     */     catch (XmpSchemaException e)
/*     */     {
/* 270 */       throw new XmpParsingException(XmpParsingException.ErrorType.Undefined, "Parsing failed", e);
/*     */     }
/*     */     finally
/*     */     {
/* 274 */       this.nsFinder.pop();
/*     */     }
/*     */   }
/*     */ 
/*     */   private void createProperty(XMPMetadata xmp, Element property, PropertyType type, ComplexPropertyContainer container)
/*     */     throws XmpParsingException
/*     */   {
/* 281 */     String prefix = property.getPrefix();
/* 282 */     String name = property.getLocalName();
/* 283 */     String namespace = property.getNamespaceURI();
/*     */ 
/* 285 */     this.nsFinder.push(property);
/*     */     try
/*     */     {
/* 288 */       if (type == null)
/*     */       {
/* 290 */         if (this.strictParsing)
/*     */         {
/* 292 */           throw new XmpParsingException(XmpParsingException.ErrorType.InvalidType, "No type defined for {" + namespace + "}" + name);
/*     */         }
/*     */ 
/* 298 */         manageSimpleType(xmp, property, Types.Text, container);
/*     */       }
/* 301 */       else if (type.type() == Types.LangAlt)
/*     */       {
/* 303 */         manageLangAlt(xmp, property, container);
/*     */       }
/* 305 */       else if (type.card().isArray())
/*     */       {
/* 307 */         manageArray(xmp, property, type, container);
/*     */       }
/* 309 */       else if (type.type().isSimple())
/*     */       {
/* 311 */         manageSimpleType(xmp, property, type.type(), container);
/*     */       }
/* 313 */       else if (type.type().isStructured())
/*     */       {
/* 315 */         if (DomHelper.isParseTypeResource(property))
/*     */         {
/* 317 */           AbstractStructuredType ast = parseLiDescription(xmp, DomHelper.getQName(property), property);
/* 318 */           if (ast != null)
/*     */           {
/* 320 */             ast.setPrefix(prefix);
/* 321 */             container.addProperty(ast);
/*     */           }
/*     */         }
/*     */         else
/*     */         {
/* 326 */           Element inner = DomHelper.getFirstChildElement(property);
/* 327 */           if (inner != null)
/*     */           {
/* 329 */             AbstractStructuredType ast = parseLiDescription(xmp, DomHelper.getQName(property), inner);
/* 330 */             ast.setPrefix(prefix);
/* 331 */             container.addProperty(ast);
/*     */           }
/*     */         }
/*     */       }
/* 335 */       else if (type.type() == Types.DefinedType)
/*     */       {
/* 337 */         if (DomHelper.isParseTypeResource(property))
/*     */         {
/* 339 */           AbstractStructuredType ast = parseLiDescription(xmp, DomHelper.getQName(property), property);
/* 340 */           ast.setPrefix(prefix);
/* 341 */           container.addProperty(ast);
/*     */         }
/*     */         else
/*     */         {
/* 345 */           Element inner = DomHelper.getFirstChildElement(property);
/* 346 */           if (inner == null)
/*     */           {
/* 348 */             throw new XmpParsingException(XmpParsingException.ErrorType.Format, "property should contain child element : " + property);
/*     */           }
/*     */ 
/* 351 */           AbstractStructuredType ast = parseLiDescription(xmp, DomHelper.getQName(property), inner);
/* 352 */           ast.setPrefix(prefix);
/* 353 */           container.addProperty(ast);
/*     */         }
/*     */       }
/*     */     }
/*     */     finally
/*     */     {
/* 359 */       this.nsFinder.pop();
/*     */     }
/*     */   }
/*     */ 
/*     */   private void manageSimpleType(XMPMetadata xmp, Element property, Types type, ComplexPropertyContainer container)
/*     */     throws XmpParsingException
/*     */   {
/* 367 */     TypeMapping tm = xmp.getTypeMapping();
/* 368 */     String prefix = property.getPrefix();
/* 369 */     String name = property.getLocalName();
/* 370 */     String namespace = property.getNamespaceURI();
/* 371 */     AbstractSimpleProperty sp = tm.instanciateSimpleProperty(namespace, prefix, name, property.getTextContent(), type);
/*     */ 
/* 373 */     loadAttributes(sp, property);
/* 374 */     container.addProperty(sp);
/*     */   }
/*     */ 
/*     */   private void manageArray(XMPMetadata xmp, Element property, PropertyType type, ComplexPropertyContainer container)
/*     */     throws XmpParsingException
/*     */   {
/* 380 */     TypeMapping tm = xmp.getTypeMapping();
/* 381 */     String prefix = property.getPrefix();
/* 382 */     String name = property.getLocalName();
/* 383 */     String namespace = property.getNamespaceURI();
/* 384 */     Element bagOrSeq = DomHelper.getUniqueElementChild(property);
/*     */ 
/* 386 */     if (bagOrSeq == null)
/*     */     {
/* 389 */       throw new XmpParsingException(XmpParsingException.ErrorType.Format, "Invalid array definition, expecting " + type.card() + " and found nothing");
/*     */     }
/*     */ 
/* 392 */     if (!bagOrSeq.getLocalName().equals(type.card().name()))
/*     */     {
/* 395 */       throw new XmpParsingException(XmpParsingException.ErrorType.Format, "Invalid array type, expecting " + type.card() + " and found " + bagOrSeq.getLocalName());
/*     */     }
/*     */ 
/* 398 */     ArrayProperty array = tm.createArrayProperty(namespace, prefix, name, type.card());
/* 399 */     container.addProperty(array);
/* 400 */     List lis = DomHelper.getElementChildren(bagOrSeq);
/*     */ 
/* 402 */     for (Element element : lis)
/*     */     {
/* 404 */       QName propertyQName = DomHelper.getQName(property);
/* 405 */       AbstractField ast = parseLiElement(xmp, propertyQName, element);
/* 406 */       if (ast != null)
/*     */       {
/* 408 */         array.addProperty(ast);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   private void manageLangAlt(XMPMetadata xmp, Element property, ComplexPropertyContainer container)
/*     */     throws XmpParsingException
/*     */   {
/* 416 */     manageArray(xmp, property, TypeMapping.createPropertyType(Types.LangAlt, Cardinality.Alt), container);
/*     */   }
/*     */ 
/*     */   private void parseDescriptionInner(XMPMetadata xmp, Element description, ComplexPropertyContainer parentContainer)
/*     */     throws XmpParsingException
/*     */   {
/* 422 */     this.nsFinder.push(description);
/* 423 */     TypeMapping tm = xmp.getTypeMapping();
/*     */     try
/*     */     {
/* 426 */       List properties = DomHelper.getElementChildren(description);
/* 427 */       for (Element property : properties)
/*     */       {
/* 429 */         String name = property.getLocalName();
/* 430 */         PropertyType dtype = checkPropertyDefinition(xmp, DomHelper.getQName(property));
/* 431 */         PropertyType ptype = tm.getStructuredPropMapping(dtype.type()).getPropertyType(name);
/*     */ 
/* 433 */         createProperty(xmp, property, ptype, parentContainer);
/*     */       }
/*     */     }
/*     */     finally
/*     */     {
/* 438 */       this.nsFinder.pop();
/*     */     }
/*     */   }
/*     */ 
/*     */   private AbstractField parseLiElement(XMPMetadata xmp, QName descriptor, Element liElement)
/*     */     throws XmpParsingException
/*     */   {
/* 445 */     if (DomHelper.isParseTypeResource(liElement))
/*     */     {
/* 447 */       return parseLiDescription(xmp, descriptor, liElement);
/*     */     }
/*     */ 
/* 450 */     Element liChild = DomHelper.getUniqueElementChild(liElement);
/* 451 */     if (liChild != null)
/*     */     {
/* 453 */       return parseLiDescription(xmp, descriptor, liChild);
/*     */     }
/*     */ 
/* 458 */     String text = liElement.getTextContent();
/* 459 */     TypeMapping tm = xmp.getTypeMapping();
/* 460 */     AbstractSimpleProperty sp = tm.instanciateSimpleProperty(descriptor.getNamespaceURI(), descriptor.getPrefix(), descriptor.getLocalPart(), text, Types.Text);
/*     */ 
/* 462 */     loadAttributes(sp, liElement);
/* 463 */     return sp;
/*     */   }
/*     */ 
/*     */   private void loadAttributes(AbstractField sp, Element element)
/*     */   {
/* 469 */     NamedNodeMap nnm = element.getAttributes();
/* 470 */     for (int i = 0; i < nnm.getLength(); i++)
/*     */     {
/* 472 */       Attr attr = (Attr)nnm.item(i);
/* 473 */       if (!"xmlns".equals(attr.getPrefix()))
/*     */       {
/* 477 */         if (("rdf".equals(attr.getPrefix())) && ("about".equals(attr.getLocalName())))
/*     */         {
/* 481 */           if ((sp instanceof XMPSchema))
/*     */           {
/* 483 */             ((XMPSchema)sp).setAboutAsSimple(attr.getValue());
/*     */           }
/*     */         }
/*     */         else
/*     */         {
/* 488 */           Attribute attribute = new Attribute("http://www.w3.org/XML/1998/namespace", attr.getLocalName(), attr.getValue());
/* 489 */           sp.setAttribute(attribute);
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   private AbstractStructuredType parseLiDescription(XMPMetadata xmp, QName descriptor, Element liElement) throws XmpParsingException
/*     */   {
/* 497 */     TypeMapping tm = xmp.getTypeMapping();
/* 498 */     List elements = DomHelper.getElementChildren(liElement);
/* 499 */     if (elements.size() == 0)
/*     */     {
/* 502 */       return null;
/*     */     }
/*     */ 
/* 505 */     Element first = (Element)elements.get(0);
/* 506 */     PropertyType ctype = checkPropertyDefinition(xmp, DomHelper.getQName(first));
/* 507 */     Types tt = ctype.type();
/* 508 */     AbstractStructuredType ast = instanciateStructured(tm, tt, descriptor.getLocalPart(), first.getNamespaceURI());
/*     */ 
/* 510 */     ast.setNamespace(descriptor.getNamespaceURI());
/* 511 */     ast.setPrefix(descriptor.getPrefix());
/*     */     PropertiesDescription pm;
/*     */     PropertiesDescription pm;
/* 514 */     if (tt.isStructured())
/*     */     {
/* 516 */       pm = tm.getStructuredPropMapping(tt);
/*     */     }
/*     */     else
/*     */     {
/* 520 */       pm = tm.getDefinedDescriptionByNamespace(first.getNamespaceURI());
/*     */     }
/* 522 */     for (Element element : elements)
/*     */     {
/* 524 */       String prefix = element.getPrefix();
/* 525 */       String name = element.getLocalName();
/* 526 */       String namespace = element.getNamespaceURI();
/* 527 */       PropertyType type = pm.getPropertyType(name);
/* 528 */       if (type == null)
/*     */       {
/* 531 */         throw new XmpParsingException(XmpParsingException.ErrorType.NoType, "Type '" + name + "' not defined in " + element.getNamespaceURI());
/*     */       }
/*     */       ArrayProperty array;
/* 534 */       if (type.card().isArray())
/*     */       {
/* 536 */         array = tm.createArrayProperty(namespace, prefix, name, type.card());
/* 537 */         ast.getContainer().addProperty(array);
/* 538 */         Element bagOrSeq = DomHelper.getUniqueElementChild(element);
/* 539 */         List lis = DomHelper.getElementChildren(bagOrSeq);
/* 540 */         for (Element element2 : lis)
/*     */         {
/* 542 */           AbstractField ast2 = parseLiElement(xmp, descriptor, element2);
/* 543 */           if (ast2 != null)
/*     */           {
/* 545 */             array.addProperty(ast2);
/*     */           }
/*     */         }
/*     */       }
/* 549 */       else if (type.type().isSimple())
/*     */       {
/* 551 */         AbstractSimpleProperty sp = tm.instanciateSimpleProperty(namespace, prefix, name, element.getTextContent(), type.type());
/*     */ 
/* 553 */         loadAttributes(sp, element);
/* 554 */         ast.getContainer().addProperty(sp);
/*     */       }
/* 556 */       else if (type.type().isStructured())
/*     */       {
/* 559 */         AbstractStructuredType inner = instanciateStructured(tm, type.type(), name, null);
/* 560 */         inner.setNamespace(namespace);
/* 561 */         inner.setPrefix(prefix);
/* 562 */         ast.getContainer().addProperty(inner);
/* 563 */         ComplexPropertyContainer cpc = inner.getContainer();
/* 564 */         if (DomHelper.isParseTypeResource(element))
/*     */         {
/* 566 */           parseDescriptionInner(xmp, element, cpc);
/*     */         }
/*     */         else
/*     */         {
/* 570 */           Element descElement = DomHelper.getFirstChildElement(element);
/* 571 */           if (descElement != null)
/*     */           {
/* 573 */             parseDescriptionInner(xmp, descElement, cpc);
/*     */           }
/*     */         }
/*     */       }
/*     */       else
/*     */       {
/* 579 */         throw new XmpParsingException(XmpParsingException.ErrorType.NoType, "Unidentified element to parse " + element + " (type=" + type + ")");
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 584 */     return ast;
/*     */   }
/*     */ 
/*     */   private XMPMetadata parseInitialXpacket(ProcessingInstruction pi) throws XmpParsingException
/*     */   {
/* 589 */     if (!"xpacket".equals(pi.getNodeName()))
/*     */     {
/* 591 */       throw new XmpParsingException(XmpParsingException.ErrorType.XpacketBadStart, "Bad processing instruction name : " + pi.getNodeName());
/*     */     }
/*     */ 
/* 594 */     String data = pi.getData();
/* 595 */     StringTokenizer tokens = new StringTokenizer(data, " ");
/* 596 */     String id = null;
/* 597 */     String begin = null;
/* 598 */     String bytes = null;
/* 599 */     String encoding = null;
/* 600 */     while (tokens.hasMoreTokens())
/*     */     {
/* 602 */       String token = tokens.nextToken();
/* 603 */       if ((!token.endsWith("\"")) && (!token.endsWith("'")))
/*     */       {
/* 605 */         throw new XmpParsingException(XmpParsingException.ErrorType.XpacketBadStart, "Cannot understand PI data part : '" + token + "'");
/*     */       }
/*     */ 
/* 608 */       String quote = token.substring(token.length() - 1);
/* 609 */       int pos = token.indexOf("=" + quote);
/* 610 */       if (pos <= 0)
/*     */       {
/* 612 */         throw new XmpParsingException(XmpParsingException.ErrorType.XpacketBadStart, "Cannot understand PI data part : '" + token + "'");
/*     */       }
/*     */ 
/* 615 */       String name = token.substring(0, pos);
/* 616 */       String value = token.substring(pos + 2, token.length() - 1);
/* 617 */       if ("id".equals(name))
/*     */       {
/* 619 */         id = value;
/*     */       }
/* 621 */       else if ("begin".equals(name))
/*     */       {
/* 623 */         begin = value;
/*     */       }
/* 625 */       else if ("bytes".equals(name))
/*     */       {
/* 627 */         bytes = value;
/*     */       }
/* 629 */       else if ("encoding".equals(name))
/*     */       {
/* 631 */         encoding = value;
/*     */       }
/*     */       else
/*     */       {
/* 635 */         throw new XmpParsingException(XmpParsingException.ErrorType.XpacketBadStart, "Unknown attribute in xpacket PI : '" + token + "'");
/*     */       }
/*     */     }
/*     */ 
/* 639 */     return XMPMetadata.createXMPMetadata(begin, id, bytes, encoding);
/*     */   }
/*     */ 
/*     */   private void parseEndPacket(XMPMetadata metadata, ProcessingInstruction pi) throws XmpParsingException
/*     */   {
/* 644 */     String xpackData = pi.getData();
/*     */ 
/* 648 */     if (xpackData.startsWith("end="))
/*     */     {
/* 650 */       char end = xpackData.charAt(5);
/*     */ 
/* 652 */       if ((end != 'r') && (end != 'w'))
/*     */       {
/* 654 */         throw new XmpParsingException(XmpParsingException.ErrorType.XpacketBadEnd, "Excepted xpacket 'end' attribute with value 'r' or 'w' ");
/*     */       }
/*     */ 
/* 659 */       metadata.setEndXPacket(Character.toString(end));
/*     */     }
/*     */     else
/*     */     {
/* 665 */       throw new XmpParsingException(XmpParsingException.ErrorType.XpacketBadEnd, "Excepted xpacket 'end' attribute (must be present and placed in first)");
/*     */     }
/*     */   }
/*     */ 
/*     */   private Element findDescriptionsParent(Element root)
/*     */     throws XmpParsingException
/*     */   {
/* 673 */     expectNaming(root, "adobe:ns:meta/", "x", "xmpmeta");
/*     */ 
/* 675 */     NodeList nl = root.getChildNodes();
/* 676 */     if (nl.getLength() == 0)
/*     */     {
/* 679 */       throw new XmpParsingException(XmpParsingException.ErrorType.Format, "No rdf description found in xmp");
/*     */     }
/* 681 */     if (nl.getLength() > 1)
/*     */     {
/* 684 */       throw new XmpParsingException(XmpParsingException.ErrorType.Format, "More than one element found in x:xmpmeta");
/*     */     }
/* 686 */     if (!(root.getFirstChild() instanceof Element))
/*     */     {
/* 689 */       throw new XmpParsingException(XmpParsingException.ErrorType.Format, "x:xmpmeta does not contains rdf:RDF element");
/*     */     }
/* 691 */     Element rdfRdf = (Element)root.getFirstChild();
/*     */ 
/* 694 */     expectNaming(rdfRdf, "http://www.w3.org/1999/02/22-rdf-syntax-ns#", "rdf", "RDF");
/*     */ 
/* 697 */     return rdfRdf;
/*     */   }
/*     */ 
/*     */   private void expectNaming(Element element, String ns, String prefix, String ln) throws XmpParsingException
/*     */   {
/* 702 */     if ((ns != null) && (!ns.equals(element.getNamespaceURI())))
/*     */     {
/* 704 */       throw new XmpParsingException(XmpParsingException.ErrorType.Format, "Expecting namespace '" + ns + "' and found '" + element.getNamespaceURI() + "'");
/*     */     }
/*     */ 
/* 707 */     if ((prefix != null) && (!prefix.equals(element.getPrefix())))
/*     */     {
/* 709 */       throw new XmpParsingException(XmpParsingException.ErrorType.Format, "Expecting prefix '" + prefix + "' and found '" + element.getPrefix() + "'");
/*     */     }
/*     */ 
/* 712 */     if ((ln != null) && (!ln.equals(element.getLocalName())))
/*     */     {
/* 714 */       throw new XmpParsingException(XmpParsingException.ErrorType.Format, "Expecting local name '" + ln + "' and found '" + element.getLocalName() + "'");
/*     */     }
/*     */   }
/*     */ 
/*     */   private void removeComments(Node root)
/*     */   {
/* 727 */     Node node = root;
/* 728 */     while (node != null)
/*     */     {
/* 730 */       Node next = node.getNextSibling();
/* 731 */       if ((node instanceof Comment))
/*     */       {
/* 734 */         node.getParentNode().removeChild(node);
/*     */       }
/* 736 */       else if ((node instanceof Text))
/*     */       {
/* 738 */         Text t = (Text)node;
/* 739 */         if (t.getTextContent().trim().length() == 0)
/*     */         {
/* 742 */           node.getParentNode().removeChild(node);
/*     */         }
/*     */       }
/* 745 */       else if ((node instanceof Element))
/*     */       {
/* 748 */         removeComments(node.getFirstChild());
/*     */       }
/* 750 */       node = next;
/*     */     }
/*     */   }
/*     */ 
/*     */   private AbstractStructuredType instanciateStructured(TypeMapping tm, Types type, String name, String structuredNamespace)
/*     */     throws XmpParsingException
/*     */   {
/*     */     try
/*     */     {
/* 760 */       if (type.isStructured())
/*     */       {
/* 762 */         return tm.instanciateStructuredType(type, name);
/*     */       }
/* 764 */       if (type.isDefined())
/*     */       {
/* 766 */         return tm.instanciateDefinedType(name, structuredNamespace);
/*     */       }
/*     */ 
/* 770 */       throw new XmpParsingException(XmpParsingException.ErrorType.InvalidType, "Type not structured : " + type);
/*     */     }
/*     */     catch (BadFieldValueException e)
/*     */     {
/* 775 */       throw new XmpParsingException(XmpParsingException.ErrorType.InvalidType, "Parsing failed", e);
/*     */     }
/*     */   }
/*     */ 
/*     */   private PropertyType checkPropertyDefinition(XMPMetadata xmp, QName prop) throws XmpParsingException
/*     */   {
/* 781 */     TypeMapping tm = xmp.getTypeMapping();
/*     */ 
/* 783 */     if (!this.nsFinder.containsNamespace(prop.getNamespaceURI()))
/*     */     {
/* 785 */       throw new XmpParsingException(XmpParsingException.ErrorType.NoSchema, "Schema is not set in this document : " + prop.getNamespaceURI());
/*     */     }
/*     */ 
/* 789 */     String nsuri = prop.getNamespaceURI();
/* 790 */     if (!tm.isDefinedNamespace(nsuri))
/*     */     {
/* 792 */       throw new XmpParsingException(XmpParsingException.ErrorType.NoSchema, "Cannot find a definition for the namespace " + prop.getNamespaceURI());
/*     */     }
/*     */ 
/*     */     try
/*     */     {
/* 797 */       return tm.getSpecifiedPropertyType(prop);
/*     */     }
/*     */     catch (BadFieldValueException e)
/*     */     {
/* 801 */       throw new XmpParsingException(XmpParsingException.ErrorType.InvalidType, "Failed to retreive property definition", e);
/*     */     }
/*     */   }
/*     */ 
/*     */   protected class NamespaceFinder
/*     */   {
/* 808 */     private Stack<Map<String, String>> stack = new Stack();
/*     */ 
/*     */     protected NamespaceFinder() {
/*     */     }
/* 812 */     protected void push(Element description) { NamedNodeMap nnm = description.getAttributes();
/* 813 */       Map map = new HashMap(nnm.getLength());
/* 814 */       for (int j = 0; j < nnm.getLength(); j++)
/*     */       {
/* 816 */         Attr no = (Attr)nnm.item(j);
/*     */ 
/* 818 */         if ("http://www.w3.org/2000/xmlns/".equals(no.getNamespaceURI()))
/*     */         {
/* 820 */           map.put(no.getLocalName(), no.getValue());
/*     */         }
/*     */       }
/* 823 */       this.stack.push(map);
/*     */     }
/*     */ 
/*     */     protected Map<String, String> pop()
/*     */     {
/* 828 */       return (Map)this.stack.pop();
/*     */     }
/*     */ 
/*     */     protected boolean containsNamespace(String namespace)
/*     */     {
/* 833 */       for (int i = this.stack.size() - 1; i >= 0; i--)
/*     */       {
/* 835 */         Map map = (Map)this.stack.get(i);
/* 836 */         if (map.containsValue(namespace))
/*     */         {
/* 838 */           return true;
/*     */         }
/*     */       }
/*     */ 
/* 842 */       return false;
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.xmpbox.xml.DomXmpParser
 * JD-Core Version:    0.6.2
 */