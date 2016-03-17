/*     */ package org.apache.xmpbox.xml;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import javax.xml.namespace.QName;
/*     */ import org.w3c.dom.Attr;
/*     */ import org.w3c.dom.Element;
/*     */ import org.w3c.dom.NodeList;
/*     */ 
/*     */ public final class DomHelper
/*     */ {
/*     */   public static Element getUniqueElementChild(Element description)
/*     */     throws XmpParsingException
/*     */   {
/*  44 */     NodeList nl = description.getChildNodes();
/*  45 */     int pos = -1;
/*  46 */     for (int i = 0; i < nl.getLength(); i++)
/*     */     {
/*  48 */       if ((nl.item(i) instanceof Element))
/*     */       {
/*  50 */         if (pos >= 0)
/*     */         {
/*  53 */           throw new XmpParsingException(XmpParsingException.ErrorType.Undefined, "Found two child elements in " + description);
/*     */         }
/*     */ 
/*  57 */         pos = i;
/*     */       }
/*     */     }
/*     */ 
/*  61 */     return (Element)nl.item(pos);
/*     */   }
/*     */ 
/*     */   public static Element getFirstChildElement(Element description)
/*     */     throws XmpParsingException
/*     */   {
/*  73 */     NodeList nl = description.getChildNodes();
/*  74 */     for (int i = 0; i < nl.getLength(); i++)
/*     */     {
/*  76 */       if ((nl.item(i) instanceof Element))
/*     */       {
/*  78 */         return (Element)nl.item(i);
/*     */       }
/*     */     }
/*  81 */     return null;
/*     */   }
/*     */ 
/*     */   public static List<Element> getElementChildren(Element description) throws XmpParsingException
/*     */   {
/*  86 */     NodeList nl = description.getChildNodes();
/*  87 */     List ret = new ArrayList(nl.getLength());
/*  88 */     for (int i = 0; i < nl.getLength(); i++)
/*     */     {
/*  90 */       if ((nl.item(i) instanceof Element))
/*     */       {
/*  92 */         ret.add((Element)nl.item(i));
/*     */       }
/*     */     }
/*  95 */     return ret;
/*     */   }
/*     */ 
/*     */   public static QName getQName(Element element)
/*     */   {
/* 100 */     return new QName(element.getNamespaceURI(), element.getLocalName(), element.getPrefix());
/*     */   }
/*     */ 
/*     */   public static boolean isRdfDescription(Element element)
/*     */   {
/* 105 */     return ("rdf".equals(element.getPrefix())) && ("Description".equals(element.getLocalName()));
/*     */   }
/*     */ 
/*     */   public static boolean isParseTypeResource(Element element)
/*     */   {
/* 111 */     Attr parseType = element.getAttributeNodeNS("http://www.w3.org/1999/02/22-rdf-syntax-ns#", "parseType");
/* 112 */     if ((parseType != null) && ("Resource".equals(parseType.getValue())))
/*     */     {
/* 115 */       return true;
/*     */     }
/*     */ 
/* 118 */     return false;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.xmpbox.xml.DomHelper
 * JD-Core Version:    0.6.2
 */