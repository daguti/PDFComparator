/*      */ package org.apache.xmpbox.schema;
/*      */ 
/*      */ import java.io.IOException;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Calendar;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import org.apache.xmpbox.XMPMetadata;
/*      */ import org.apache.xmpbox.type.AbstractField;
/*      */ import org.apache.xmpbox.type.AbstractSimpleProperty;
/*      */ import org.apache.xmpbox.type.AbstractStructuredType;
/*      */ import org.apache.xmpbox.type.ArrayProperty;
/*      */ import org.apache.xmpbox.type.Attribute;
/*      */ import org.apache.xmpbox.type.BadFieldValueException;
/*      */ import org.apache.xmpbox.type.BooleanType;
/*      */ import org.apache.xmpbox.type.Cardinality;
/*      */ import org.apache.xmpbox.type.ComplexPropertyContainer;
/*      */ import org.apache.xmpbox.type.DateType;
/*      */ import org.apache.xmpbox.type.IntegerType;
/*      */ import org.apache.xmpbox.type.TextType;
/*      */ import org.apache.xmpbox.type.TypeMapping;
/*      */ import org.apache.xmpbox.type.Types;
/*      */ 
/*      */ public class XMPSchema extends AbstractStructuredType
/*      */ {
/*      */   public XMPSchema(XMPMetadata metadata, String namespaceURI, String prefix, String name)
/*      */   {
/*   72 */     super(metadata, namespaceURI, prefix, name);
/*   73 */     addNamespace(getNamespace(), getPrefix());
/*      */   }
/*      */ 
/*      */   public XMPSchema(XMPMetadata metadata)
/*      */   {
/*   78 */     this(metadata, null, null, null);
/*      */   }
/*      */ 
/*      */   public XMPSchema(XMPMetadata metadata, String prefix)
/*      */   {
/*   83 */     this(metadata, null, prefix, null);
/*      */   }
/*      */ 
/*      */   public XMPSchema(XMPMetadata metadata, String namespaceURI, String prefix)
/*      */   {
/*   88 */     this(metadata, namespaceURI, prefix, null);
/*      */   }
/*      */ 
/*      */   public AbstractField getAbstractProperty(String qualifiedName)
/*      */   {
/*  100 */     Iterator it = getContainer().getAllProperties().iterator();
/*      */ 
/*  102 */     while (it.hasNext())
/*      */     {
/*  104 */       AbstractField tmp = (AbstractField)it.next();
/*  105 */       if (tmp.getPropertyName().equals(qualifiedName))
/*      */       {
/*  107 */         return tmp;
/*      */       }
/*      */     }
/*  110 */     return null;
/*      */   }
/*      */ 
/*      */   public Attribute getAboutAttribute()
/*      */   {
/*  121 */     return getAttribute("about");
/*      */   }
/*      */ 
/*      */   public String getAboutValue()
/*      */   {
/*  131 */     Attribute prop = getAttribute("about");
/*  132 */     if (prop != null)
/*      */     {
/*  134 */       return prop.getValue();
/*      */     }
/*  136 */     return "";
/*      */   }
/*      */ 
/*      */   public void setAbout(Attribute about)
/*      */     throws BadFieldValueException
/*      */   {
/*  149 */     if ("http://www.w3.org/1999/02/22-rdf-syntax-ns#".equals(about.getNamespace()))
/*      */     {
/*  151 */       if ("about".equals(about.getName()))
/*      */       {
/*  153 */         setAttribute(about);
/*  154 */         return;
/*      */       }
/*      */     }
/*      */ 
/*  158 */     throw new BadFieldValueException("Attribute 'about' must be named 'rdf:about' or 'about'");
/*      */   }
/*      */ 
/*      */   public void setAboutAsSimple(String about)
/*      */   {
/*  169 */     if (about == null)
/*      */     {
/*  171 */       removeAttribute("about");
/*      */     }
/*      */     else
/*      */     {
/*  175 */       setAttribute(new Attribute("http://www.w3.org/1999/02/22-rdf-syntax-ns#", "about", about));
/*      */     }
/*      */   }
/*      */ 
/*      */   private void setSpecifiedSimpleTypeProperty(Types type, String qualifiedName, Object propertyValue)
/*      */   {
/*  182 */     if (propertyValue == null)
/*      */     {
/*  185 */       Iterator it = getContainer().getAllProperties().iterator();
/*      */ 
/*  187 */       while (it.hasNext())
/*      */       {
/*  189 */         AbstractField tmp = (AbstractField)it.next();
/*  190 */         if (tmp.getPropertyName().equals(qualifiedName))
/*      */         {
/*  192 */           getContainer().removeProperty(tmp);
/*  193 */           return;
/*      */         }
/*      */       }
/*      */     }
/*      */     else
/*      */     {
/*      */       AbstractSimpleProperty specifiedTypeProperty;
/*      */       try
/*      */       {
/*  202 */         TypeMapping tm = getMetadata().getTypeMapping();
/*  203 */         specifiedTypeProperty = tm.instanciateSimpleProperty(null, getPrefix(), qualifiedName, propertyValue, type);
/*      */       }
/*      */       catch (Exception e)
/*      */       {
/*  208 */         throw new IllegalArgumentException("Failed to create property with the specified type given in parameters", e);
/*      */       }
/*      */ 
/*  213 */       Iterator it = getAllProperties().iterator();
/*      */ 
/*  215 */       while (it.hasNext())
/*      */       {
/*  217 */         AbstractField tmp = (AbstractField)it.next();
/*  218 */         if (tmp.getPropertyName().equals(qualifiedName))
/*      */         {
/*  220 */           removeProperty(tmp);
/*  221 */           addProperty(specifiedTypeProperty);
/*  222 */           return;
/*      */         }
/*      */       }
/*  225 */       addProperty(specifiedTypeProperty);
/*      */     }
/*      */   }
/*      */ 
/*      */   private void setSpecifiedSimpleTypeProperty(AbstractSimpleProperty prop)
/*      */   {
/*  239 */     Iterator it = getAllProperties().iterator();
/*      */ 
/*  241 */     while (it.hasNext())
/*      */     {
/*  243 */       AbstractField tmp = (AbstractField)it.next();
/*  244 */       if (tmp.getPropertyName().equals(prop.getPropertyName()))
/*      */       {
/*  246 */         removeProperty(tmp);
/*  247 */         addProperty(prop);
/*  248 */         return;
/*      */       }
/*      */     }
/*  251 */     addProperty(prop);
/*      */   }
/*      */ 
/*      */   public void setTextProperty(TextType prop)
/*      */   {
/*  262 */     setSpecifiedSimpleTypeProperty(prop);
/*      */   }
/*      */ 
/*      */   public void setTextPropertyValue(String qualifiedName, String propertyValue)
/*      */   {
/*  275 */     setSpecifiedSimpleTypeProperty(Types.Text, qualifiedName, propertyValue);
/*      */   }
/*      */ 
/*      */   public void setTextPropertyValueAsSimple(String simpleName, String propertyValue)
/*      */   {
/*  288 */     setTextPropertyValue(simpleName, propertyValue);
/*      */   }
/*      */ 
/*      */   public TextType getUnqualifiedTextProperty(String name)
/*      */   {
/*  300 */     String qualifiedName = name;
/*  301 */     AbstractField prop = getAbstractProperty(qualifiedName);
/*  302 */     if (prop != null)
/*      */     {
/*  304 */       if ((prop instanceof TextType))
/*      */       {
/*  306 */         return (TextType)prop;
/*      */       }
/*      */ 
/*  310 */       throw new IllegalArgumentException("Property asked is not a Text Property");
/*      */     }
/*      */ 
/*  313 */     return null;
/*      */   }
/*      */ 
/*      */   public String getUnqualifiedTextPropertyValue(String name)
/*      */   {
/*  327 */     TextType tt = getUnqualifiedTextProperty(name);
/*  328 */     return tt == null ? null : tt.getStringValue();
/*      */   }
/*      */ 
/*      */   public DateType getDateProperty(String qualifiedName)
/*      */   {
/*  341 */     AbstractField prop = getAbstractProperty(qualifiedName);
/*  342 */     if (prop != null)
/*      */     {
/*  344 */       if ((prop instanceof DateType))
/*      */       {
/*  346 */         return (DateType)prop;
/*      */       }
/*      */ 
/*  350 */       throw new IllegalArgumentException("Property asked is not a Date Property");
/*      */     }
/*      */ 
/*  354 */     return null;
/*      */   }
/*      */ 
/*      */   public Calendar getDatePropertyValueAsSimple(String simpleName)
/*      */   {
/*  367 */     return getDatePropertyValue(simpleName);
/*      */   }
/*      */ 
/*      */   public Calendar getDatePropertyValue(String qualifiedName)
/*      */   {
/*  381 */     AbstractField prop = getAbstractProperty(qualifiedName);
/*  382 */     if (prop != null)
/*      */     {
/*  384 */       if ((prop instanceof DateType))
/*      */       {
/*  386 */         return ((DateType)prop).getValue();
/*      */       }
/*      */ 
/*  390 */       throw new IllegalArgumentException("Property asked is not a Date Property");
/*      */     }
/*      */ 
/*  394 */     return null;
/*      */   }
/*      */ 
/*      */   public void setDateProperty(DateType date)
/*      */   {
/*  405 */     setSpecifiedSimpleTypeProperty(date);
/*      */   }
/*      */ 
/*      */   public void setDatePropertyValueAsSimple(String simpleName, Calendar date)
/*      */   {
/*  418 */     setDatePropertyValue(simpleName, date);
/*      */   }
/*      */ 
/*      */   public void setDatePropertyValue(String qualifiedName, Calendar date)
/*      */   {
/*  431 */     setSpecifiedSimpleTypeProperty(Types.Date, qualifiedName, date);
/*      */   }
/*      */ 
/*      */   public BooleanType getBooleanProperty(String qualifiedName)
/*      */   {
/*  444 */     AbstractField prop = getAbstractProperty(qualifiedName);
/*  445 */     if (prop != null)
/*      */     {
/*  447 */       if ((prop instanceof BooleanType))
/*      */       {
/*  449 */         return (BooleanType)prop;
/*      */       }
/*      */ 
/*  453 */       throw new IllegalArgumentException("Property asked is not a Boolean Property");
/*      */     }
/*      */ 
/*  456 */     return null;
/*      */   }
/*      */ 
/*      */   public Boolean getBooleanPropertyValueAsSimple(String simpleName)
/*      */   {
/*  468 */     return getBooleanPropertyValue(simpleName);
/*      */   }
/*      */ 
/*      */   public Boolean getBooleanPropertyValue(String qualifiedName)
/*      */   {
/*  481 */     AbstractField prop = getAbstractProperty(qualifiedName);
/*  482 */     if (prop != null)
/*      */     {
/*  484 */       if ((prop instanceof BooleanType))
/*      */       {
/*  486 */         return ((BooleanType)prop).getValue();
/*      */       }
/*      */ 
/*  490 */       throw new IllegalArgumentException("Property asked is not a Boolean Property");
/*      */     }
/*      */ 
/*  497 */     return null;
/*      */   }
/*      */ 
/*      */   public void setBooleanProperty(BooleanType bool)
/*      */   {
/*  508 */     setSpecifiedSimpleTypeProperty(bool);
/*      */   }
/*      */ 
/*      */   public void setBooleanPropertyValueAsSimple(String simpleName, Boolean bool)
/*      */   {
/*  521 */     setBooleanPropertyValue(simpleName, bool);
/*      */   }
/*      */ 
/*      */   public void setBooleanPropertyValue(String qualifiedName, Boolean bool)
/*      */   {
/*  534 */     setSpecifiedSimpleTypeProperty(Types.Boolean, qualifiedName, bool);
/*      */   }
/*      */ 
/*      */   public IntegerType getIntegerProperty(String qualifiedName)
/*      */   {
/*  546 */     AbstractField prop = getAbstractProperty(qualifiedName);
/*  547 */     if (prop != null)
/*      */     {
/*  549 */       if ((prop instanceof IntegerType))
/*      */       {
/*  551 */         return (IntegerType)prop;
/*      */       }
/*      */ 
/*  555 */       throw new IllegalArgumentException("Property asked is not an Integer Property");
/*      */     }
/*      */ 
/*  558 */     return null;
/*      */   }
/*      */ 
/*      */   public Integer getIntegerPropertyValueAsSimple(String simpleName)
/*      */   {
/*  570 */     return getIntegerPropertyValue(simpleName);
/*      */   }
/*      */ 
/*      */   public Integer getIntegerPropertyValue(String qualifiedName)
/*      */   {
/*  583 */     AbstractField prop = getAbstractProperty(qualifiedName);
/*  584 */     if (prop != null)
/*      */     {
/*  586 */       if ((prop instanceof IntegerType))
/*      */       {
/*  588 */         return ((IntegerType)prop).getValue();
/*      */       }
/*      */ 
/*  592 */       throw new IllegalArgumentException("Property asked is not an Integer Property");
/*      */     }
/*      */ 
/*  595 */     return null;
/*      */   }
/*      */ 
/*      */   public void setIntegerProperty(IntegerType prop)
/*      */   {
/*  606 */     setSpecifiedSimpleTypeProperty(prop);
/*      */   }
/*      */ 
/*      */   public void setIntegerPropertyValueAsSimple(String simpleName, Integer intValue)
/*      */   {
/*  619 */     setIntegerPropertyValue(simpleName, intValue);
/*      */   }
/*      */ 
/*      */   public void setIntegerPropertyValue(String qualifiedName, Integer intValue)
/*      */   {
/*  632 */     setSpecifiedSimpleTypeProperty(Types.Integer, qualifiedName, intValue);
/*      */   }
/*      */ 
/*      */   private void removeUnqualifiedArrayValue(String arrayName, String fieldValue)
/*      */   {
/*  645 */     ArrayProperty array = (ArrayProperty)getAbstractProperty(arrayName);
/*  646 */     if (array != null)
/*      */     {
/*  648 */       ArrayList toDelete = new ArrayList();
/*  649 */       Iterator it = array.getContainer().getAllProperties().iterator();
/*      */ 
/*  651 */       while (it.hasNext())
/*      */       {
/*  653 */         AbstractSimpleProperty tmp = (AbstractSimpleProperty)it.next();
/*  654 */         if (tmp.getStringValue().equals(fieldValue))
/*      */         {
/*  656 */           toDelete.add(tmp);
/*      */         }
/*      */       }
/*  659 */       Iterator eraseProperties = toDelete.iterator();
/*  660 */       while (eraseProperties.hasNext())
/*      */       {
/*  662 */         array.getContainer().removeProperty((AbstractField)eraseProperties.next());
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   public void removeUnqualifiedBagValue(String bagName, String bagValue)
/*      */   {
/*  678 */     removeUnqualifiedArrayValue(bagName, bagValue);
/*      */   }
/*      */ 
/*      */   public void addBagValueAsSimple(String simpleName, String bagValue)
/*      */   {
/*  691 */     internalAddBagValue(simpleName, bagValue);
/*      */   }
/*      */ 
/*      */   private void internalAddBagValue(String qualifiedBagName, String bagValue)
/*      */   {
/*  696 */     ArrayProperty bag = (ArrayProperty)getAbstractProperty(qualifiedBagName);
/*  697 */     TextType li = createTextType("li", bagValue);
/*  698 */     if (bag != null)
/*      */     {
/*  700 */       bag.getContainer().addProperty(li);
/*      */     }
/*      */     else
/*      */     {
/*  704 */       ArrayProperty newBag = createArrayProperty(qualifiedBagName, Cardinality.Bag);
/*  705 */       newBag.getContainer().addProperty(li);
/*  706 */       addProperty(newBag);
/*      */     }
/*      */   }
/*      */ 
/*      */   public void addQualifiedBagValue(String simpleName, String bagValue)
/*      */   {
/*  720 */     internalAddBagValue(simpleName, bagValue);
/*      */   }
/*      */ 
/*      */   public List<String> getUnqualifiedBagValueList(String bagName)
/*      */   {
/*  734 */     ArrayProperty array = (ArrayProperty)getAbstractProperty(bagName);
/*  735 */     if (array != null)
/*      */     {
/*  737 */       return array.getElementsAsString();
/*      */     }
/*      */ 
/*  741 */     return null;
/*      */   }
/*      */ 
/*      */   public void removeUnqualifiedSequenceValue(String qualifiedSeqName, String seqValue)
/*      */   {
/*  755 */     removeUnqualifiedArrayValue(qualifiedSeqName, seqValue);
/*      */   }
/*      */ 
/*      */   public void removeUnqualifiedArrayValue(String arrayName, AbstractField fieldValue)
/*      */   {
/*  768 */     String qualifiedArrayName = arrayName;
/*  769 */     ArrayProperty array = (ArrayProperty)getAbstractProperty(qualifiedArrayName);
/*  770 */     if (array != null)
/*      */     {
/*  772 */       ArrayList toDelete = new ArrayList();
/*  773 */       Iterator it = array.getContainer().getAllProperties().iterator();
/*      */ 
/*  775 */       while (it.hasNext())
/*      */       {
/*  777 */         AbstractSimpleProperty tmp = (AbstractSimpleProperty)it.next();
/*  778 */         if (tmp.equals(fieldValue))
/*      */         {
/*  780 */           toDelete.add(tmp);
/*      */         }
/*      */       }
/*  783 */       Iterator eraseProperties = toDelete.iterator();
/*  784 */       while (eraseProperties.hasNext())
/*      */       {
/*  786 */         array.getContainer().removeProperty((AbstractField)eraseProperties.next());
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   public void removeUnqualifiedSequenceValue(String qualifiedSeqName, AbstractField seqValue)
/*      */   {
/*  801 */     removeUnqualifiedArrayValue(qualifiedSeqName, seqValue);
/*      */   }
/*      */ 
/*      */   public void addUnqualifiedSequenceValue(String simpleSeqName, String seqValue)
/*      */   {
/*  814 */     String qualifiedSeqName = simpleSeqName;
/*  815 */     ArrayProperty seq = (ArrayProperty)getAbstractProperty(qualifiedSeqName);
/*  816 */     TextType li = createTextType("li", seqValue);
/*  817 */     if (seq != null)
/*      */     {
/*  819 */       seq.getContainer().addProperty(li);
/*      */     }
/*      */     else
/*      */     {
/*  823 */       ArrayProperty newSeq = createArrayProperty(simpleSeqName, Cardinality.Seq);
/*  824 */       newSeq.getContainer().addProperty(li);
/*  825 */       addProperty(newSeq);
/*      */     }
/*      */   }
/*      */ 
/*      */   public void addBagValue(String qualifiedSeqName, AbstractField seqValue)
/*      */   {
/*  839 */     ArrayProperty bag = (ArrayProperty)getAbstractProperty(qualifiedSeqName);
/*  840 */     if (bag != null)
/*      */     {
/*  842 */       bag.getContainer().addProperty(seqValue);
/*      */     }
/*      */     else
/*      */     {
/*  846 */       ArrayProperty newBag = createArrayProperty(qualifiedSeqName, Cardinality.Bag);
/*  847 */       newBag.getContainer().addProperty(seqValue);
/*  848 */       addProperty(newBag);
/*      */     }
/*      */   }
/*      */ 
/*      */   public void addUnqualifiedSequenceValue(String seqName, AbstractField seqValue)
/*      */   {
/*  862 */     String qualifiedSeqName = seqName;
/*  863 */     ArrayProperty seq = (ArrayProperty)getAbstractProperty(qualifiedSeqName);
/*  864 */     if (seq != null)
/*      */     {
/*  866 */       seq.getContainer().addProperty(seqValue);
/*      */     }
/*      */     else
/*      */     {
/*  870 */       ArrayProperty newSeq = createArrayProperty(seqName, Cardinality.Seq);
/*  871 */       newSeq.getContainer().addProperty(seqValue);
/*  872 */       addProperty(newSeq);
/*      */     }
/*      */   }
/*      */ 
/*      */   public List<String> getUnqualifiedSequenceValueList(String seqName)
/*      */   {
/*  886 */     ArrayProperty array = (ArrayProperty)getAbstractProperty(seqName);
/*  887 */     if (array != null)
/*      */     {
/*  889 */       return array.getElementsAsString();
/*      */     }
/*      */ 
/*  893 */     return null;
/*      */   }
/*      */ 
/*      */   public void removeUnqualifiedSequenceDateValue(String seqName, Calendar date)
/*      */   {
/*  907 */     String qualifiedSeqName = seqName;
/*  908 */     ArrayProperty seq = (ArrayProperty)getAbstractProperty(qualifiedSeqName);
/*  909 */     if (seq != null)
/*      */     {
/*  911 */       ArrayList toDelete = new ArrayList();
/*  912 */       Iterator it = seq.getContainer().getAllProperties().iterator();
/*      */ 
/*  914 */       while (it.hasNext())
/*      */       {
/*  916 */         AbstractField tmp = (AbstractField)it.next();
/*  917 */         if (((tmp instanceof DateType)) && 
/*  919 */           (((DateType)tmp).getValue().equals(date)))
/*      */         {
/*  921 */           toDelete.add(tmp);
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/*  926 */       Iterator eraseProperties = toDelete.iterator();
/*  927 */       while (eraseProperties.hasNext())
/*      */       {
/*  929 */         seq.getContainer().removeProperty((AbstractField)eraseProperties.next());
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   public void addSequenceDateValueAsSimple(String simpleName, Calendar date)
/*      */   {
/*  944 */     addUnqualifiedSequenceDateValue(simpleName, date);
/*      */   }
/*      */ 
/*      */   public void addUnqualifiedSequenceDateValue(String seqName, Calendar date)
/*      */   {
/*  957 */     addUnqualifiedSequenceValue(seqName, getMetadata().getTypeMapping().createDate(null, "RDF", "li", date));
/*      */   }
/*      */ 
/*      */   public List<Calendar> getUnqualifiedSequenceDateValueList(String seqName)
/*      */   {
/*  974 */     String qualifiedSeqName = seqName;
/*  975 */     List retval = null;
/*  976 */     ArrayProperty seq = (ArrayProperty)getAbstractProperty(qualifiedSeqName);
/*  977 */     if (seq != null)
/*      */     {
/*  979 */       retval = new ArrayList();
/*  980 */       Iterator it = seq.getContainer().getAllProperties().iterator();
/*      */ 
/*  982 */       while (it.hasNext())
/*      */       {
/*  984 */         AbstractField tmp = (AbstractField)it.next();
/*  985 */         if ((tmp instanceof DateType))
/*      */         {
/*  987 */           retval.add(((DateType)tmp).getValue());
/*      */         }
/*      */       }
/*      */     }
/*  991 */     return retval;
/*      */   }
/*      */ 
/*      */   public void reorganizeAltOrder(ComplexPropertyContainer alt)
/*      */   {
/* 1002 */     Iterator it = alt.getAllProperties().iterator();
/* 1003 */     AbstractField xdefault = null;
/* 1004 */     boolean xdefaultFound = false;
/*      */ 
/* 1006 */     if (it.hasNext())
/*      */     {
/* 1008 */       if (((AbstractField)it.next()).getAttribute("lang").getValue().equals("x-default"))
/*      */       {
/* 1010 */         return;
/*      */       }
/*      */     }
/*      */ 
/* 1014 */     while ((it.hasNext()) && (!xdefaultFound))
/*      */     {
/* 1016 */       xdefault = (AbstractField)it.next();
/* 1017 */       if (xdefault.getAttribute("lang").getValue().equals("x-default"))
/*      */       {
/* 1019 */         alt.removeProperty(xdefault);
/* 1020 */         xdefaultFound = true;
/*      */       }
/*      */     }
/* 1023 */     if (xdefaultFound)
/*      */     {
/* 1025 */       it = alt.getAllProperties().iterator();
/* 1026 */       ArrayList reordered = new ArrayList();
/* 1027 */       ArrayList toDelete = new ArrayList();
/* 1028 */       reordered.add(xdefault);
/*      */ 
/* 1030 */       while (it.hasNext())
/*      */       {
/* 1032 */         AbstractField tmp = (AbstractField)it.next();
/* 1033 */         reordered.add(tmp);
/* 1034 */         toDelete.add(tmp);
/*      */       }
/* 1036 */       Iterator eraseProperties = toDelete.iterator();
/* 1037 */       while (eraseProperties.hasNext())
/*      */       {
/* 1039 */         alt.removeProperty((AbstractField)eraseProperties.next());
/*      */       }
/* 1041 */       it = reordered.iterator();
/* 1042 */       while (it.hasNext())
/*      */       {
/* 1044 */         alt.addProperty((AbstractField)it.next());
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   public void setUnqualifiedLanguagePropertyValue(String name, String language, String value)
/*      */   {
/* 1062 */     String qualifiedName = name;
/* 1063 */     AbstractField property = getAbstractProperty(qualifiedName);
/*      */ 
/* 1065 */     if (property != null)
/*      */     {
/* 1068 */       if ((property instanceof ArrayProperty))
/*      */       {
/* 1070 */         ArrayProperty prop = (ArrayProperty)property;
/* 1071 */         Iterator itCplx = prop.getContainer().getAllProperties().iterator();
/*      */ 
/* 1075 */         while (itCplx.hasNext())
/*      */         {
/* 1077 */           AbstractField tmp = (AbstractField)itCplx.next();
/*      */ 
/* 1079 */           if (tmp.getAttribute("lang").getValue().equals(language))
/*      */           {
/* 1082 */             if (value == null)
/*      */             {
/* 1085 */               prop.getContainer().removeProperty(tmp);
/*      */             }
/*      */             else
/*      */             {
/* 1089 */               prop.getContainer().removeProperty(tmp);
/*      */ 
/* 1091 */               TextType langValue = createTextType("li", value);
/*      */ 
/* 1093 */               langValue.setAttribute(new Attribute("http://www.w3.org/XML/1998/namespace", "lang", language));
/*      */ 
/* 1095 */               prop.getContainer().addProperty(langValue);
/*      */             }
/* 1097 */             reorganizeAltOrder(prop.getContainer());
/* 1098 */             return;
/*      */           }
/*      */ 
/*      */         }
/*      */ 
/* 1103 */         TextType langValue = createTextType("li", value);
/* 1104 */         langValue.setAttribute(new Attribute("http://www.w3.org/XML/1998/namespace", "lang", language));
/* 1105 */         prop.getContainer().addProperty(langValue);
/* 1106 */         reorganizeAltOrder(prop.getContainer());
/*      */       }
/*      */     }
/*      */     else
/*      */     {
/* 1111 */       ArrayProperty prop = createArrayProperty(name, Cardinality.Alt);
/*      */ 
/* 1113 */       TextType langValue = createTextType("li", value);
/* 1114 */       langValue.setAttribute(new Attribute("http://www.w3.org/XML/1998/namespace", "lang", language));
/* 1115 */       prop.getContainer().addProperty(langValue);
/* 1116 */       addProperty(prop);
/*      */     }
/*      */   }
/*      */ 
/*      */   public String getUnqualifiedLanguagePropertyValue(String name, String expectedLanguage)
/*      */   {
/* 1132 */     String language = expectedLanguage != null ? expectedLanguage : "x-default";
/* 1133 */     AbstractField property = getAbstractProperty(name);
/* 1134 */     if (property != null)
/*      */     {
/* 1136 */       if ((property instanceof ArrayProperty))
/*      */       {
/* 1138 */         ArrayProperty prop = (ArrayProperty)property;
/* 1139 */         Iterator langsDef = prop.getContainer().getAllProperties().iterator();
/*      */ 
/* 1142 */         while (langsDef.hasNext())
/*      */         {
/* 1144 */           AbstractField tmp = (AbstractField)langsDef.next();
/* 1145 */           Attribute text = tmp.getAttribute("lang");
/* 1146 */           if ((text != null) && 
/* 1148 */             (text.getValue().equals(language)))
/*      */           {
/* 1150 */             return ((TextType)tmp).getStringValue();
/*      */           }
/*      */         }
/*      */ 
/* 1154 */         return null;
/*      */       }
/*      */ 
/* 1158 */       throw new IllegalArgumentException("The property '" + name + "' is not of Lang Alt type");
/*      */     }
/*      */ 
/* 1161 */     return null;
/*      */   }
/*      */ 
/*      */   public List<String> getUnqualifiedLanguagePropertyLanguagesValue(String name)
/*      */   {
/* 1174 */     List retval = new ArrayList();
/* 1175 */     AbstractField property = getAbstractProperty(name);
/* 1176 */     if (property != null)
/*      */     {
/* 1178 */       if ((property instanceof ArrayProperty))
/*      */       {
/* 1180 */         ArrayProperty prop = (ArrayProperty)property;
/* 1181 */         Iterator langsDef = prop.getContainer().getAllProperties().iterator();
/*      */ 
/* 1184 */         while (langsDef.hasNext())
/*      */         {
/* 1186 */           AbstractField tmp = (AbstractField)langsDef.next();
/* 1187 */           Attribute text = tmp.getAttribute("lang");
/* 1188 */           if (text != null)
/*      */           {
/* 1190 */             retval.add(text.getValue());
/*      */           }
/*      */           else
/*      */           {
/* 1194 */             retval.add("x-default");
/*      */           }
/*      */         }
/* 1197 */         return retval;
/*      */       }
/*      */ 
/* 1201 */       throw new IllegalArgumentException("The property '" + name + "' is not of Lang Alt type");
/*      */     }
/*      */ 
/* 1205 */     return null;
/*      */   }
/*      */ 
/*      */   public void merge(XMPSchema xmpSchema)
/*      */     throws IOException
/*      */   {
/* 1218 */     if (!xmpSchema.getClass().equals(getClass()))
/*      */     {
/* 1220 */       throw new IOException("Can only merge schemas of the same type.");
/*      */     }
/*      */ 
/* 1223 */     Iterator itAtt = xmpSchema.getAllAttributes().iterator();
/*      */ 
/* 1225 */     while (itAtt.hasNext())
/*      */     {
/* 1227 */       Attribute att = (Attribute)itAtt.next();
/* 1228 */       if (att.getNamespace().equals(getNamespace()))
/*      */       {
/* 1230 */         setAttribute(att);
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/* 1235 */     Iterator itProp = xmpSchema.getContainer().getAllProperties().iterator();
/*      */ 
/* 1237 */     while (itProp.hasNext())
/*      */     {
/* 1239 */       AbstractField prop = (AbstractField)itProp.next();
/* 1240 */       if (prop.getPrefix().equals(getPrefix()))
/*      */       {
/* 1242 */         if ((prop instanceof ArrayProperty))
/*      */         {
/* 1244 */           String analyzedPropQualifiedName = prop.getPropertyName();
/* 1245 */           Iterator itActualEmbeddedProperties = getAllProperties().iterator();
/*      */ 
/* 1254 */           boolean alreadyPresent = false;
/*      */ 
/* 1256 */           while (itActualEmbeddedProperties.hasNext())
/*      */           {
/* 1258 */             AbstractField tmpEmbeddedProperty = (AbstractField)itActualEmbeddedProperties.next();
/* 1259 */             if (((tmpEmbeddedProperty instanceof ArrayProperty)) && 
/* 1261 */               (tmpEmbeddedProperty.getPropertyName().equals(analyzedPropQualifiedName)))
/*      */             {
/* 1263 */               Iterator itNewValues = ((ArrayProperty)prop).getContainer().getAllProperties().iterator();
/*      */ 
/* 1265 */               while (itNewValues.hasNext())
/*      */               {
/* 1267 */                 TextType tmpNewValue = (TextType)itNewValues.next();
/* 1268 */                 Iterator itOldValues = ((ArrayProperty)tmpEmbeddedProperty).getContainer().getAllProperties().iterator();
/*      */ 
/* 1270 */                 while ((itOldValues.hasNext()) && (!alreadyPresent))
/*      */                 {
/* 1272 */                   TextType tmpOldValue = (TextType)itOldValues.next();
/* 1273 */                   if (tmpOldValue.getStringValue().equals(tmpNewValue.getStringValue()))
/*      */                   {
/* 1275 */                     alreadyPresent = true;
/*      */                   }
/*      */                 }
/* 1278 */                 if (!alreadyPresent)
/*      */                 {
/* 1280 */                   ((ArrayProperty)tmpEmbeddedProperty).getContainer().addProperty(tmpNewValue);
/*      */                 }
/*      */               }
/*      */             }
/*      */ 
/*      */           }
/*      */ 
/*      */         }
/*      */         else
/*      */         {
/* 1290 */           addProperty(prop);
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   public List<AbstractField> getUnqualifiedArrayList(String name)
/*      */     throws BadFieldValueException
/*      */   {
/* 1307 */     ArrayProperty array = null;
/* 1308 */     Iterator itProp = getAllProperties().iterator();
/*      */ 
/* 1310 */     while (itProp.hasNext())
/*      */     {
/* 1312 */       AbstractField tmp = (AbstractField)itProp.next();
/* 1313 */       if (tmp.getPropertyName().equals(name))
/*      */       {
/* 1315 */         if ((tmp instanceof ArrayProperty))
/*      */         {
/* 1317 */           array = (ArrayProperty)tmp;
/*      */         }
/*      */         else
/*      */         {
/* 1322 */           throw new BadFieldValueException("Property asked not seems to be an array");
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/* 1327 */     if (array != null)
/*      */     {
/* 1329 */       Iterator it = array.getContainer().getAllProperties().iterator();
/* 1330 */       List list = new ArrayList();
/* 1331 */       while (it.hasNext())
/*      */       {
/* 1333 */         list.add(it.next());
/*      */       }
/* 1335 */       return list;
/*      */     }
/* 1337 */     return null;
/*      */   }
/*      */ 
/*      */   protected AbstractSimpleProperty instanciateSimple(String param, Object value)
/*      */   {
/* 1342 */     TypeMapping tm = getMetadata().getTypeMapping();
/* 1343 */     return tm.instanciateSimpleField(getClass(), null, getPrefix(), param, value);
/*      */   }
/*      */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.xmpbox.schema.XMPSchema
 * JD-Core Version:    0.6.2
 */