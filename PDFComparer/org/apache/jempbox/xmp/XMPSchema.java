/*      */ package org.apache.jempbox.xmp;
/*      */ 
/*      */ import java.io.IOException;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Calendar;
/*      */ import java.util.Collections;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import org.apache.jempbox.impl.DateConverter;
/*      */ import org.apache.jempbox.impl.XMLUtil;
/*      */ import org.w3c.dom.Document;
/*      */ import org.w3c.dom.Element;
/*      */ import org.w3c.dom.NamedNodeMap;
/*      */ import org.w3c.dom.Node;
/*      */ import org.w3c.dom.NodeList;
/*      */ 
/*      */ public class XMPSchema
/*      */ {
/*      */   public static final String NS_NAMESPACE = "http://www.w3.org/2000/xmlns/";
/*      */   protected String prefix;
/*   56 */   protected Element schema = null;
/*      */ 
/*      */   public XMPSchema(XMPMetadata parent, String namespaceName, String namespaceURI)
/*      */   {
/*   71 */     this.schema = parent.xmpDocument.createElementNS("http://www.w3.org/1999/02/22-rdf-syntax-ns#", "rdf:Description");
/*      */ 
/*   74 */     this.prefix = namespaceName;
/*   75 */     this.schema.setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns:" + namespaceName, namespaceURI);
/*      */   }
/*      */ 
/*      */   public XMPSchema(Element element, String aPrefix)
/*      */   {
/*   89 */     this.schema = element;
/*   90 */     if (aPrefix != null)
/*      */     {
/*   92 */       this.prefix = aPrefix;
/*      */     }
/*      */     else
/*      */     {
/*   96 */       this.prefix = "";
/*      */     }
/*      */   }
/*      */ 
/*      */   public Element getElement()
/*      */   {
/*  107 */     return this.schema;
/*      */   }
/*      */ 
/*      */   public String getAbout()
/*      */   {
/*  117 */     return getTextProperty("rdf:about");
/*      */   }
/*      */ 
/*      */   public void setAbout(String about)
/*      */   {
/*  128 */     if (about == null)
/*      */     {
/*  130 */       this.schema.removeAttribute("rdf:about");
/*      */     }
/*      */     else
/*      */     {
/*  134 */       this.schema.setAttribute("rdf:about", about);
/*      */     }
/*      */   }
/*      */ 
/*      */   public void setTextProperty(String propertyName, String propertyValue)
/*      */   {
/*  150 */     if (propertyValue == null)
/*      */     {
/*  152 */       this.schema.removeAttribute(propertyName);
/*  153 */       NodeList keywordList = this.schema.getElementsByTagName(propertyName);
/*  154 */       for (int i = 0; i < keywordList.getLength(); i++)
/*      */       {
/*  156 */         this.schema.removeChild(keywordList.item(i));
/*      */       }
/*      */ 
/*      */     }
/*  162 */     else if (this.schema.hasAttribute(propertyName))
/*      */     {
/*  164 */       this.schema.setAttribute(propertyName, propertyValue);
/*      */     }
/*  168 */     else if (this.schema.hasChildNodes())
/*      */     {
/*  170 */       NodeList nodeList = this.schema.getElementsByTagName(propertyName);
/*      */ 
/*  172 */       if (nodeList.getLength() > 0)
/*      */       {
/*  174 */         Element node = (Element)nodeList.item(0);
/*  175 */         node.setNodeValue(propertyValue);
/*      */       }
/*      */       else
/*      */       {
/*  179 */         Element textNode = this.schema.getOwnerDocument().createElement(propertyName);
/*      */ 
/*  181 */         XMLUtil.setStringValue(textNode, propertyValue);
/*  182 */         this.schema.appendChild(textNode);
/*      */       }
/*      */     }
/*      */     else
/*      */     {
/*  187 */       this.schema.setAttribute(propertyName, propertyValue);
/*      */     }
/*      */   }
/*      */ 
/*      */   public String getTextProperty(String propertyName)
/*      */   {
/*  207 */     if (this.schema.hasAttribute(propertyName))
/*      */     {
/*  209 */       return this.schema.getAttribute(propertyName);
/*      */     }
/*      */ 
/*  213 */     NodeList nodes = this.schema.getElementsByTagName(propertyName);
/*  214 */     if (nodes.getLength() > 0)
/*      */     {
/*  216 */       Element node = (Element)nodes.item(0);
/*  217 */       return XMLUtil.getStringValue(node);
/*      */     }
/*  219 */     return null;
/*      */   }
/*      */ 
/*      */   public Calendar getDateProperty(String propertyName)
/*      */     throws IOException
/*      */   {
/*  236 */     return DateConverter.toCalendar(getTextProperty(propertyName));
/*      */   }
/*      */ 
/*      */   public void setDateProperty(String propertyName, Calendar date)
/*      */   {
/*  249 */     if (date != null)
/*      */     {
/*  251 */       setTextProperty(propertyName, DateConverter.toISO8601(date));
/*      */     }
/*      */     else
/*      */     {
/*  256 */       setTextProperty(propertyName, null);
/*      */     }
/*      */   }
/*      */ 
/*      */   public Boolean getBooleanProperty(String propertyName)
/*      */   {
/*  270 */     Boolean value = null;
/*  271 */     String stringValue = getTextProperty(propertyName);
/*  272 */     if (stringValue != null)
/*      */     {
/*  274 */       value = stringValue.equals("True") ? Boolean.TRUE : Boolean.FALSE;
/*      */     }
/*  276 */     return value;
/*      */   }
/*      */ 
/*      */   public void setBooleanProperty(String propertyName, Boolean bool)
/*      */   {
/*  289 */     String value = null;
/*  290 */     if (bool != null)
/*      */     {
/*  292 */       value = bool.booleanValue() ? "True" : "False";
/*      */     }
/*  294 */     setTextProperty(propertyName, value);
/*      */   }
/*      */ 
/*      */   public Integer getIntegerProperty(String propertyName)
/*      */   {
/*  307 */     Integer retval = null;
/*  308 */     String intProperty = getTextProperty(propertyName);
/*  309 */     if ((intProperty != null) && (intProperty.length() > 0))
/*      */     {
/*  311 */       retval = new Integer(intProperty);
/*      */     }
/*  313 */     return retval;
/*      */   }
/*      */ 
/*      */   public void setIntegerProperty(String propertyName, Integer intValue)
/*      */   {
/*  326 */     String textValue = null;
/*  327 */     if (intValue != null)
/*      */     {
/*  329 */       textValue = intValue.toString();
/*      */     }
/*  331 */     setTextProperty(propertyName, textValue);
/*      */   }
/*      */ 
/*      */   public void removeBagValue(String bagName, String bagValue)
/*      */   {
/*  345 */     Element bagElement = null;
/*  346 */     NodeList nodes = this.schema.getElementsByTagName(bagName);
/*  347 */     if (nodes.getLength() > 0)
/*      */     {
/*  349 */       Element contElement = (Element)nodes.item(0);
/*  350 */       NodeList bagList = contElement.getElementsByTagName("rdf:Bag");
/*  351 */       if (bagList.getLength() > 0)
/*      */       {
/*  353 */         bagElement = (Element)bagList.item(0);
/*  354 */         NodeList items = bagElement.getElementsByTagName("rdf:li");
/*  355 */         for (int i = items.getLength() - 1; i >= 0; i--)
/*      */         {
/*  357 */           Element li = (Element)items.item(i);
/*  358 */           String value = XMLUtil.getStringValue(li);
/*  359 */           if (value.equals(bagValue))
/*      */           {
/*  361 */             bagElement.removeChild(li);
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   public void addBagValue(String bagName, String bagValue)
/*      */   {
/*  379 */     Element bagElement = null;
/*  380 */     NodeList nodes = this.schema.getElementsByTagName(bagName);
/*  381 */     if (nodes.getLength() > 0)
/*      */     {
/*  383 */       Element contElement = (Element)nodes.item(0);
/*  384 */       NodeList bagList = contElement.getElementsByTagName("rdf:Bag");
/*  385 */       if (bagList.getLength() > 0)
/*      */       {
/*  387 */         bagElement = (Element)bagList.item(0);
/*      */       }
/*      */     }
/*      */     else
/*      */     {
/*  392 */       Element contElement = this.schema.getOwnerDocument().createElement(bagName);
/*      */ 
/*  394 */       this.schema.appendChild(contElement);
/*  395 */       bagElement = this.schema.getOwnerDocument().createElement("rdf:Bag");
/*  396 */       contElement.appendChild(bagElement);
/*      */     }
/*  398 */     Element liElement = this.schema.getOwnerDocument().createElement("rdf:li");
/*  399 */     XMLUtil.setStringValue(liElement, bagValue);
/*  400 */     if (bagElement != null)
/*      */     {
/*  402 */       bagElement.appendChild(liElement);
/*      */     }
/*      */   }
/*      */ 
/*      */   public List<String> getBagList(String bagName)
/*      */   {
/*  418 */     List retval = null;
/*  419 */     NodeList nodes = this.schema.getElementsByTagName(bagName);
/*  420 */     if (nodes.getLength() > 0)
/*      */     {
/*  422 */       Element contributor = (Element)nodes.item(0);
/*  423 */       NodeList bagList = contributor.getElementsByTagName("rdf:Bag");
/*  424 */       if (bagList.getLength() > 0)
/*      */       {
/*  426 */         Element bag = (Element)bagList.item(0);
/*  427 */         retval = new ArrayList();
/*  428 */         NodeList items = bag.getElementsByTagName("rdf:li");
/*  429 */         for (int i = 0; i < items.getLength(); i++)
/*      */         {
/*  431 */           Element li = (Element)items.item(i);
/*  432 */           retval.add(XMLUtil.getStringValue(li));
/*      */         }
/*  434 */         retval = Collections.unmodifiableList(retval);
/*      */       }
/*      */     }
/*      */ 
/*  438 */     return retval;
/*      */   }
/*      */ 
/*      */   public void removeSequenceValue(String seqName, String seqValue)
/*      */   {
/*  452 */     Element bagElement = null;
/*  453 */     NodeList nodes = this.schema.getElementsByTagName(seqName);
/*  454 */     if (nodes.getLength() > 0)
/*      */     {
/*  456 */       Element contElement = (Element)nodes.item(0);
/*  457 */       NodeList bagList = contElement.getElementsByTagName("rdf:Seq");
/*  458 */       if (bagList.getLength() > 0)
/*      */       {
/*  460 */         bagElement = (Element)bagList.item(0);
/*  461 */         NodeList items = bagElement.getElementsByTagName("rdf:li");
/*  462 */         for (int i = items.getLength() - 1; i >= 0; i--)
/*      */         {
/*  464 */           Element li = (Element)items.item(i);
/*  465 */           String value = XMLUtil.getStringValue(li);
/*  466 */           if (value.equals(seqValue))
/*      */           {
/*  468 */             bagElement.removeChild(li);
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   public void removeSequenceValue(String seqName, Elementable seqValue)
/*      */   {
/*  487 */     Element bagElement = null;
/*  488 */     NodeList nodes = this.schema.getElementsByTagName(seqName);
/*  489 */     if (nodes.getLength() > 0)
/*      */     {
/*  491 */       Element contElement = (Element)nodes.item(0);
/*  492 */       NodeList bagList = contElement.getElementsByTagName("rdf:Seq");
/*  493 */       if (bagList.getLength() > 0)
/*      */       {
/*  495 */         bagElement = (Element)bagList.item(0);
/*  496 */         NodeList items = bagElement.getElementsByTagName("rdf:li");
/*  497 */         for (int i = 0; i < items.getLength(); i++)
/*      */         {
/*  499 */           Element li = (Element)items.item(i);
/*  500 */           if (li == seqValue.getElement())
/*      */           {
/*  502 */             bagElement.removeChild(li);
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   public void addSequenceValue(String seqName, String seqValue)
/*      */   {
/*  520 */     Element bagElement = null;
/*  521 */     NodeList nodes = this.schema.getElementsByTagName(seqName);
/*  522 */     if (nodes.getLength() > 0)
/*      */     {
/*  524 */       Element contElement = (Element)nodes.item(0);
/*  525 */       NodeList bagList = contElement.getElementsByTagName("rdf:Seq");
/*  526 */       if (bagList.getLength() > 0)
/*      */       {
/*  528 */         bagElement = (Element)bagList.item(0);
/*      */       }
/*      */       else
/*      */       {
/*  533 */         this.schema.removeChild(nodes.item(0));
/*      */       }
/*      */     }
/*  536 */     if (bagElement == null)
/*      */     {
/*  538 */       Element contElement = this.schema.getOwnerDocument().createElement(seqName);
/*      */ 
/*  540 */       this.schema.appendChild(contElement);
/*  541 */       bagElement = this.schema.getOwnerDocument().createElement("rdf:Seq");
/*  542 */       contElement.appendChild(bagElement);
/*      */     }
/*  544 */     Element liElement = this.schema.getOwnerDocument().createElement("rdf:li");
/*  545 */     liElement.appendChild(this.schema.getOwnerDocument().createTextNode(seqValue));
/*      */ 
/*  547 */     bagElement.appendChild(liElement);
/*      */   }
/*      */ 
/*      */   public void addSequenceValue(String seqName, Elementable seqValue)
/*      */   {
/*  561 */     Element bagElement = null;
/*  562 */     NodeList nodes = this.schema.getElementsByTagName(seqName);
/*  563 */     if (nodes.getLength() > 0)
/*      */     {
/*  565 */       Element contElement = (Element)nodes.item(0);
/*  566 */       NodeList bagList = contElement.getElementsByTagName("rdf:Seq");
/*  567 */       if (bagList.getLength() > 0)
/*      */       {
/*  569 */         bagElement = (Element)bagList.item(0);
/*      */       }
/*      */     }
/*      */     else
/*      */     {
/*  574 */       Element contElement = this.schema.getOwnerDocument().createElement(seqName);
/*      */ 
/*  576 */       this.schema.appendChild(contElement);
/*  577 */       bagElement = this.schema.getOwnerDocument().createElement("rdf:Seq");
/*  578 */       contElement.appendChild(bagElement);
/*      */     }
/*  580 */     if (bagElement != null)
/*      */     {
/*  582 */       bagElement.appendChild(seqValue.getElement());
/*      */     }
/*      */   }
/*      */ 
/*      */   public List<String> getSequenceList(String seqName)
/*      */   {
/*  598 */     List retval = null;
/*  599 */     NodeList nodes = this.schema.getElementsByTagName(seqName);
/*  600 */     if (nodes.getLength() > 0)
/*      */     {
/*  602 */       Element contributor = (Element)nodes.item(0);
/*  603 */       NodeList bagList = contributor.getElementsByTagName("rdf:Seq");
/*  604 */       if (bagList.getLength() > 0)
/*      */       {
/*  606 */         Element bag = (Element)bagList.item(0);
/*  607 */         retval = new ArrayList();
/*  608 */         NodeList items = bag.getElementsByTagName("rdf:li");
/*  609 */         for (int i = 0; i < items.getLength(); i++)
/*      */         {
/*  611 */           Element li = (Element)items.item(i);
/*  612 */           retval.add(XMLUtil.getStringValue(li));
/*      */         }
/*  614 */         retval = Collections.unmodifiableList(retval);
/*      */       }
/*      */     }
/*  617 */     return retval;
/*      */   }
/*      */ 
/*      */   public List<ResourceEvent> getEventSequenceList(String seqName)
/*      */   {
/*  630 */     List retval = null;
/*  631 */     NodeList nodes = this.schema.getElementsByTagName(seqName);
/*  632 */     if (nodes.getLength() > 0)
/*      */     {
/*  634 */       Element contributor = (Element)nodes.item(0);
/*  635 */       NodeList bagList = contributor.getElementsByTagName("rdf:Seq");
/*  636 */       if (bagList.getLength() > 0)
/*      */       {
/*  638 */         Element bag = (Element)bagList.item(0);
/*  639 */         retval = new ArrayList();
/*  640 */         NodeList items = bag.getElementsByTagName("rdf:li");
/*  641 */         for (int i = 0; i < items.getLength(); i++)
/*      */         {
/*  643 */           Element li = (Element)items.item(i);
/*  644 */           retval.add(new ResourceEvent(li));
/*      */         }
/*  646 */         retval = Collections.unmodifiableList(retval);
/*      */       }
/*      */     }
/*  649 */     return retval;
/*      */   }
/*      */ 
/*      */   public void removeSequenceDateValue(String seqName, Calendar date)
/*      */   {
/*  663 */     String dateAsString = DateConverter.toISO8601(date);
/*  664 */     removeSequenceValue(seqName, dateAsString);
/*      */   }
/*      */ 
/*      */   public void addSequenceDateValue(String seqName, Calendar date)
/*      */   {
/*  678 */     String dateAsString = DateConverter.toISO8601(date);
/*  679 */     addSequenceValue(seqName, dateAsString);
/*      */   }
/*      */ 
/*      */   public List<Calendar> getSequenceDateList(String seqName)
/*      */     throws IOException
/*      */   {
/*  697 */     List strings = getSequenceList(seqName);
/*  698 */     List retval = null;
/*  699 */     if (strings != null)
/*      */     {
/*  701 */       retval = new ArrayList();
/*  702 */       for (int i = 0; i < strings.size(); i++)
/*      */       {
/*  704 */         retval.add(DateConverter.toCalendar((String)strings.get(i)));
/*      */       }
/*      */     }
/*  707 */     return retval;
/*      */   }
/*      */ 
/*      */   public void setLanguageProperty(String propertyName, String language, String value)
/*      */   {
/*  725 */     NodeList nodes = this.schema.getElementsByTagName(propertyName);
/*  726 */     Element property = null;
/*  727 */     if (nodes.getLength() == 0)
/*      */     {
/*  729 */       if (value == null)
/*      */       {
/*  733 */         return;
/*      */       }
/*  735 */       property = this.schema.getOwnerDocument().createElement(propertyName);
/*  736 */       this.schema.appendChild(property);
/*      */     }
/*      */     else
/*      */     {
/*  740 */       property = (Element)nodes.item(0);
/*      */     }
/*  742 */     Element alt = null;
/*  743 */     NodeList altList = property.getElementsByTagName("rdf:Alt");
/*  744 */     if (altList.getLength() == 0)
/*      */     {
/*  746 */       if (value == null)
/*      */       {
/*  750 */         return;
/*      */       }
/*  752 */       alt = this.schema.getOwnerDocument().createElement("rdf:Alt");
/*  753 */       property.appendChild(alt);
/*      */     }
/*      */     else
/*      */     {
/*  757 */       alt = (Element)altList.item(0);
/*      */     }
/*  759 */     NodeList items = alt.getElementsByTagName("rdf:li");
/*  760 */     if (language == null)
/*      */     {
/*  762 */       language = "x-default";
/*      */     }
/*  764 */     boolean foundValue = false;
/*  765 */     for (int i = 0; i < items.getLength(); i++)
/*      */     {
/*  767 */       Element li = (Element)items.item(i);
/*  768 */       if (value == null)
/*      */       {
/*  770 */         alt.removeChild(li);
/*      */       }
/*  772 */       else if (language.equals(li.getAttribute("xml:lang")))
/*      */       {
/*  774 */         foundValue = true;
/*  775 */         XMLUtil.setStringValue(li, value);
/*      */       }
/*      */     }
/*  778 */     if ((value != null) && (!foundValue))
/*      */     {
/*  780 */       Element li = this.schema.getOwnerDocument().createElement("rdf:li");
/*  781 */       li.setAttribute("xml:lang", language);
/*  782 */       XMLUtil.setStringValue(li, value);
/*  783 */       if (language.equals("x-default"))
/*      */       {
/*  786 */         alt.insertBefore(li, alt.getFirstChild());
/*      */       }
/*      */       else
/*      */       {
/*  790 */         alt.appendChild(li);
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   public String getLanguageProperty(String propertyName, String language)
/*      */   {
/*  810 */     String retval = null;
/*  811 */     if (language == null)
/*      */     {
/*  813 */       language = "x-default";
/*      */     }
/*      */ 
/*  816 */     NodeList nodes = this.schema.getElementsByTagName(propertyName);
/*  817 */     if (nodes.getLength() > 0)
/*      */     {
/*  819 */       Element property = (Element)nodes.item(0);
/*  820 */       NodeList altList = property.getElementsByTagName("rdf:Alt");
/*  821 */       if (altList.getLength() > 0)
/*      */       {
/*  823 */         Element alt = (Element)altList.item(0);
/*  824 */         NodeList items = alt.getElementsByTagName("rdf:li");
/*  825 */         for (int i = 0; (i < items.getLength()) && (retval == null); i++)
/*      */         {
/*  827 */           Element li = (Element)items.item(i);
/*  828 */           String elementLanguage = li.getAttribute("xml:lang");
/*  829 */           if (language.equals(elementLanguage))
/*      */           {
/*  831 */             retval = XMLUtil.getStringValue(li);
/*      */           }
/*      */         }
/*      */       }
/*  835 */       else if ((property.getChildNodes().getLength() == 1) && (3 == property.getFirstChild().getNodeType()))
/*      */       {
/*  838 */         retval = property.getFirstChild().getNodeValue();
/*      */       }
/*      */     }
/*  841 */     return retval;
/*      */   }
/*      */ 
/*      */   public void setThumbnailProperty(String propertyName, String language, Thumbnail value)
/*      */   {
/*  859 */     NodeList nodes = this.schema.getElementsByTagName(propertyName);
/*  860 */     Element property = null;
/*  861 */     if (nodes.getLength() == 0)
/*      */     {
/*  863 */       if (value == null)
/*      */       {
/*  867 */         return;
/*      */       }
/*  869 */       property = this.schema.getOwnerDocument().createElement(propertyName);
/*  870 */       this.schema.appendChild(property);
/*      */     }
/*      */     else
/*      */     {
/*  874 */       property = (Element)nodes.item(0);
/*      */     }
/*  876 */     Element alt = null;
/*  877 */     NodeList altList = property.getElementsByTagName("rdf:Alt");
/*  878 */     if (altList.getLength() == 0)
/*      */     {
/*  880 */       if (value == null)
/*      */       {
/*  884 */         return;
/*      */       }
/*  886 */       alt = this.schema.getOwnerDocument().createElement("rdf:Alt");
/*  887 */       property.appendChild(alt);
/*      */     }
/*      */     else
/*      */     {
/*  891 */       alt = (Element)altList.item(0);
/*      */     }
/*  893 */     NodeList items = alt.getElementsByTagName("rdf:li");
/*  894 */     if (language == null)
/*      */     {
/*  896 */       language = "x-default";
/*      */     }
/*  898 */     boolean foundValue = false;
/*  899 */     for (int i = 0; i < items.getLength(); i++)
/*      */     {
/*  901 */       Element li = (Element)items.item(i);
/*  902 */       if (value == null)
/*      */       {
/*  904 */         alt.removeChild(li);
/*      */       }
/*  906 */       else if (language.equals(li.getAttribute("xml:lang")))
/*      */       {
/*  908 */         foundValue = true;
/*  909 */         alt.replaceChild(li, value.getElement());
/*      */       }
/*      */     }
/*  912 */     if ((value != null) && (!foundValue))
/*      */     {
/*  914 */       Element li = value.getElement();
/*  915 */       li.setAttribute("xml:lang", language);
/*  916 */       if (language.equals("x-default"))
/*      */       {
/*  919 */         alt.insertBefore(li, alt.getFirstChild());
/*      */       }
/*      */       else
/*      */       {
/*  923 */         alt.appendChild(li);
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   public Thumbnail getThumbnailProperty(String propertyName, String language)
/*      */   {
/*  943 */     Thumbnail retval = null;
/*  944 */     if (language == null)
/*      */     {
/*  946 */       language = "x-default";
/*      */     }
/*      */ 
/*  949 */     NodeList nodes = this.schema.getElementsByTagName(propertyName);
/*  950 */     if (nodes.getLength() > 0)
/*      */     {
/*  952 */       Element property = (Element)nodes.item(0);
/*  953 */       NodeList altList = property.getElementsByTagName("rdf:Alt");
/*  954 */       if (altList.getLength() > 0)
/*      */       {
/*  956 */         Element alt = (Element)altList.item(0);
/*  957 */         NodeList items = alt.getElementsByTagName("rdf:li");
/*  958 */         for (int i = 0; (i < items.getLength()) && (retval == null); i++)
/*      */         {
/*  960 */           Element li = (Element)items.item(i);
/*  961 */           String elementLanguage = li.getAttribute("xml:lang");
/*  962 */           if (language.equals(elementLanguage))
/*      */           {
/*  964 */             retval = new Thumbnail(li);
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*  969 */     return retval;
/*      */   }
/*      */ 
/*      */   public List<String> getLanguagePropertyLanguages(String propertyName)
/*      */   {
/*  985 */     List retval = new ArrayList();
/*      */ 
/*  987 */     NodeList nodes = this.schema.getElementsByTagName(propertyName);
/*  988 */     if (nodes.getLength() > 0)
/*      */     {
/*  990 */       Element property = (Element)nodes.item(0);
/*  991 */       NodeList altList = property.getElementsByTagName("rdf:Alt");
/*  992 */       if (altList.getLength() > 0)
/*      */       {
/*  994 */         Element alt = (Element)altList.item(0);
/*  995 */         NodeList items = alt.getElementsByTagName("rdf:li");
/*  996 */         for (int i = 0; i < items.getLength(); i++)
/*      */         {
/*  998 */           Element li = (Element)items.item(i);
/*  999 */           String elementLanguage = li.getAttribute("xml:lang");
/* 1000 */           if (elementLanguage == null)
/*      */           {
/* 1002 */             retval.add("x-default");
/*      */           }
/*      */           else
/*      */           {
/* 1006 */             retval.add(elementLanguage);
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/* 1011 */     return retval;
/*      */   }
/*      */ 
/*      */   public void merge(XMPSchema xmpSchema)
/*      */     throws IOException
/*      */   {
/* 1023 */     if (!xmpSchema.getClass().equals(getClass()))
/*      */     {
/* 1025 */       throw new IOException("Can only merge schemas of the same type.");
/*      */     }
/*      */ 
/* 1028 */     NamedNodeMap attributes = xmpSchema.getElement().getAttributes();
/* 1029 */     for (int i = 0; i < attributes.getLength(); i++)
/*      */     {
/* 1031 */       Node a = attributes.item(i);
/* 1032 */       String name = a.getNodeName();
/* 1033 */       if (name.startsWith(this.prefix))
/*      */       {
/* 1035 */         String newValue = xmpSchema.getTextProperty(name);
/* 1036 */         setTextProperty(name, newValue);
/*      */       }
/*      */     }
/* 1039 */     NodeList nodes = xmpSchema.getElement().getChildNodes();
/*      */ 
/* 1041 */     for (int i = 0; i < nodes.getLength(); i++)
/*      */     {
/* 1043 */       Node a = nodes.item(i);
/* 1044 */       String name = a.getNodeName();
/* 1045 */       if (name.startsWith(this.prefix))
/*      */       {
/* 1047 */         if ((a instanceof Element))
/*      */         {
/* 1049 */           Element e = (Element)a;
/* 1050 */           if (nodes.getLength() > 0)
/*      */           {
/* 1052 */             NodeList seqList = e.getElementsByTagName("rdf:Seq");
/* 1053 */             if (seqList.getLength() > 0)
/*      */             {
/* 1055 */               List newList = xmpSchema.getSequenceList(name);
/* 1056 */               List oldList = getSequenceList(name);
/*      */ 
/* 1058 */               Iterator it = newList.iterator();
/*      */ 
/* 1060 */               while (it.hasNext())
/*      */               {
/* 1062 */                 String object = (String)it.next();
/* 1063 */                 if ((oldList == null) || (!oldList.contains(object)))
/*      */                 {
/* 1066 */                   addSequenceValue(name, object);
/*      */                 }
/*      */               }
/*      */             }
/*      */ 
/* 1071 */             NodeList bagList = e.getElementsByTagName("rdf:Bag");
/* 1072 */             if (bagList.getLength() > 0)
/*      */             {
/* 1074 */               List newList = xmpSchema.getBagList(name);
/* 1075 */               List oldList = getBagList(name);
/*      */ 
/* 1077 */               Iterator it = newList.iterator();
/*      */ 
/* 1079 */               while (it.hasNext())
/*      */               {
/* 1081 */                 String object = (String)it.next();
/* 1082 */                 if ((oldList == null) || (!oldList.contains(object)))
/*      */                 {
/* 1085 */                   addBagValue(name, object);
/*      */                 }
/*      */               }
/*      */             }
/*      */           }
/*      */         }
/*      */ 
/* 1092 */         String newValue = xmpSchema.getTextProperty(name);
/* 1093 */         setTextProperty(name, newValue);
/*      */       }
/*      */     }
/*      */   }
/*      */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.jempbox.xmp.XMPSchema
 * JD-Core Version:    0.6.2
 */