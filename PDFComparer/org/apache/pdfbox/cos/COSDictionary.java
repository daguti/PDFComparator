/*      */ package org.apache.pdfbox.cos;
/*      */ 
/*      */ import java.io.IOException;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Calendar;
/*      */ import java.util.Collection;
/*      */ import java.util.LinkedHashMap;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.Map.Entry;
/*      */ import java.util.Set;
/*      */ import org.apache.pdfbox.exceptions.COSVisitorException;
/*      */ import org.apache.pdfbox.pdmodel.common.COSObjectable;
/*      */ import org.apache.pdfbox.util.DateConverter;
/*      */ 
/*      */ public class COSDictionary extends COSBase
/*      */ {
/*      */   private static final String PATH_SEPARATOR = "/";
/*   45 */   protected final Map<COSName, COSBase> items = new LinkedHashMap();
/*      */ 
/*      */   public COSDictionary()
/*      */   {
/*      */   }
/*      */ 
/*      */   public COSDictionary(COSDictionary dict)
/*      */   {
/*   63 */     this.items.putAll(dict.items);
/*      */   }
/*      */ 
/*      */   public boolean containsValue(Object value)
/*      */   {
/*   76 */     boolean contains = this.items.containsValue(value);
/*   77 */     if ((!contains) && ((value instanceof COSObject)))
/*      */     {
/*   79 */       contains = this.items.containsValue(((COSObject)value).getObject());
/*      */     }
/*   81 */     return contains;
/*      */   }
/*      */ 
/*      */   public COSName getKeyForValue(Object value)
/*      */   {
/*   93 */     for (Map.Entry entry : this.items.entrySet())
/*      */     {
/*   95 */       Object nextValue = entry.getValue();
/*   96 */       if ((nextValue.equals(value)) || (((nextValue instanceof COSObject)) && (((COSObject)nextValue).getObject().equals(value))))
/*      */       {
/*   99 */         return (COSName)entry.getKey();
/*      */       }
/*      */     }
/*      */ 
/*  103 */     return null;
/*      */   }
/*      */ 
/*      */   public int size()
/*      */   {
/*  113 */     return this.items.size();
/*      */   }
/*      */ 
/*      */   public void clear()
/*      */   {
/*  121 */     this.items.clear();
/*      */   }
/*      */ 
/*      */   public COSBase getDictionaryObject(String key)
/*      */   {
/*  135 */     return getDictionaryObject(COSName.getPDFName(key));
/*      */   }
/*      */ 
/*      */   /** @deprecated */
/*      */   public COSBase getDictionaryObject(String firstKey, String secondKey)
/*      */   {
/*  155 */     COSBase retval = getDictionaryObject(COSName.getPDFName(firstKey));
/*  156 */     if (retval == null)
/*      */     {
/*  158 */       retval = getDictionaryObject(COSName.getPDFName(secondKey));
/*      */     }
/*  160 */     return retval;
/*      */   }
/*      */ 
/*      */   public COSBase getDictionaryObject(COSName firstKey, COSName secondKey)
/*      */   {
/*  178 */     COSBase retval = getDictionaryObject(firstKey);
/*  179 */     if ((retval == null) && (secondKey != null))
/*      */     {
/*  181 */       retval = getDictionaryObject(secondKey);
/*      */     }
/*  183 */     return retval;
/*      */   }
/*      */ 
/*      */   public COSBase getDictionaryObject(String[] keyList)
/*      */   {
/*  199 */     COSBase retval = null;
/*  200 */     for (int i = 0; (i < keyList.length) && (retval == null); i++)
/*      */     {
/*  202 */       retval = getDictionaryObject(COSName.getPDFName(keyList[i]));
/*      */     }
/*  204 */     return retval;
/*      */   }
/*      */ 
/*      */   public COSBase getDictionaryObject(COSName key)
/*      */   {
/*  218 */     COSBase retval = (COSBase)this.items.get(key);
/*  219 */     if ((retval instanceof COSObject))
/*      */     {
/*  221 */       retval = ((COSObject)retval).getObject();
/*      */     }
/*  223 */     if ((retval instanceof COSNull))
/*      */     {
/*  225 */       retval = null;
/*      */     }
/*  227 */     return retval;
/*      */   }
/*      */ 
/*      */   public void setItem(COSName key, COSBase value)
/*      */   {
/*  240 */     if (value == null)
/*      */     {
/*  242 */       removeItem(key);
/*      */     }
/*      */     else
/*      */     {
/*  246 */       this.items.put(key, value);
/*      */     }
/*      */   }
/*      */ 
/*      */   public void setItem(COSName key, COSObjectable value)
/*      */   {
/*  260 */     COSBase base = null;
/*  261 */     if (value != null)
/*      */     {
/*  263 */       base = value.getCOSObject();
/*      */     }
/*  265 */     setItem(key, base);
/*      */   }
/*      */ 
/*      */   public void setItem(String key, COSObjectable value)
/*      */   {
/*  278 */     setItem(COSName.getPDFName(key), value);
/*      */   }
/*      */ 
/*      */   public void setBoolean(String key, boolean value)
/*      */   {
/*  291 */     setItem(COSName.getPDFName(key), COSBoolean.getBoolean(value));
/*      */   }
/*      */ 
/*      */   public void setBoolean(COSName key, boolean value)
/*      */   {
/*  304 */     setItem(key, COSBoolean.getBoolean(value));
/*      */   }
/*      */ 
/*      */   public void setItem(String key, COSBase value)
/*      */   {
/*  317 */     setItem(COSName.getPDFName(key), value);
/*      */   }
/*      */ 
/*      */   public void setName(String key, String value)
/*      */   {
/*  331 */     setName(COSName.getPDFName(key), value);
/*      */   }
/*      */ 
/*      */   public void setName(COSName key, String value)
/*      */   {
/*  345 */     COSName name = null;
/*  346 */     if (value != null)
/*      */     {
/*  348 */       name = COSName.getPDFName(value);
/*      */     }
/*  350 */     setItem(key, name);
/*      */   }
/*      */ 
/*      */   public void setDate(String key, Calendar date)
/*      */   {
/*  363 */     setDate(COSName.getPDFName(key), date);
/*      */   }
/*      */ 
/*      */   public void setDate(COSName key, Calendar date)
/*      */   {
/*  376 */     setString(key, DateConverter.toString(date));
/*      */   }
/*      */ 
/*      */   public void setEmbeddedDate(String embedded, String key, Calendar date)
/*      */   {
/*  391 */     setEmbeddedDate(embedded, COSName.getPDFName(key), date);
/*      */   }
/*      */ 
/*      */   public void setEmbeddedDate(String embedded, COSName key, Calendar date)
/*      */   {
/*  406 */     COSDictionary dic = (COSDictionary)getDictionaryObject(embedded);
/*  407 */     if ((dic == null) && (date != null))
/*      */     {
/*  409 */       dic = new COSDictionary();
/*  410 */       setItem(embedded, dic);
/*      */     }
/*  412 */     if (dic != null)
/*      */     {
/*  414 */       dic.setDate(key, date);
/*      */     }
/*      */   }
/*      */ 
/*      */   public void setString(String key, String value)
/*      */   {
/*  429 */     setString(COSName.getPDFName(key), value);
/*      */   }
/*      */ 
/*      */   public void setString(COSName key, String value)
/*      */   {
/*  443 */     COSString name = null;
/*  444 */     if (value != null)
/*      */     {
/*  446 */       name = new COSString(value);
/*      */     }
/*  448 */     setItem(key, name);
/*      */   }
/*      */ 
/*      */   public void setEmbeddedString(String embedded, String key, String value)
/*      */   {
/*  464 */     setEmbeddedString(embedded, COSName.getPDFName(key), value);
/*      */   }
/*      */ 
/*      */   public void setEmbeddedString(String embedded, COSName key, String value)
/*      */   {
/*  480 */     COSDictionary dic = (COSDictionary)getDictionaryObject(embedded);
/*  481 */     if ((dic == null) && (value != null))
/*      */     {
/*  483 */       dic = new COSDictionary();
/*  484 */       setItem(embedded, dic);
/*      */     }
/*  486 */     if (dic != null)
/*      */     {
/*  488 */       dic.setString(key, value);
/*      */     }
/*      */   }
/*      */ 
/*      */   public void setInt(String key, int value)
/*      */   {
/*  502 */     setInt(COSName.getPDFName(key), value);
/*      */   }
/*      */ 
/*      */   public void setInt(COSName key, int value)
/*      */   {
/*  515 */     setItem(key, COSInteger.get(value));
/*      */   }
/*      */ 
/*      */   public void setLong(String key, long value)
/*      */   {
/*  528 */     setLong(COSName.getPDFName(key), value);
/*      */   }
/*      */ 
/*      */   public void setLong(COSName key, long value)
/*      */   {
/*  541 */     COSInteger intVal = null;
/*  542 */     intVal = COSInteger.get(value);
/*  543 */     setItem(key, intVal);
/*      */   }
/*      */ 
/*      */   public void setEmbeddedInt(String embeddedDictionary, String key, int value)
/*      */   {
/*  558 */     setEmbeddedInt(embeddedDictionary, COSName.getPDFName(key), value);
/*      */   }
/*      */ 
/*      */   public void setEmbeddedInt(String embeddedDictionary, COSName key, int value)
/*      */   {
/*  573 */     COSDictionary embedded = (COSDictionary)getDictionaryObject(embeddedDictionary);
/*  574 */     if (embedded == null)
/*      */     {
/*  576 */       embedded = new COSDictionary();
/*  577 */       setItem(embeddedDictionary, embedded);
/*      */     }
/*  579 */     embedded.setInt(key, value);
/*      */   }
/*      */ 
/*      */   public void setFloat(String key, float value)
/*      */   {
/*  592 */     setFloat(COSName.getPDFName(key), value);
/*      */   }
/*      */ 
/*      */   public void setFloat(COSName key, float value)
/*      */   {
/*  605 */     COSFloat fltVal = new COSFloat(value);
/*  606 */     setItem(key, fltVal);
/*      */   }
/*      */ 
/*      */   public String getNameAsString(String key)
/*      */   {
/*  619 */     return getNameAsString(COSName.getPDFName(key));
/*      */   }
/*      */ 
/*      */   public String getNameAsString(COSName key)
/*      */   {
/*  632 */     String retval = null;
/*  633 */     COSBase name = getDictionaryObject(key);
/*  634 */     if (name != null)
/*      */     {
/*  636 */       if ((name instanceof COSName))
/*      */       {
/*  638 */         retval = ((COSName)name).getName();
/*      */       }
/*  640 */       else if ((name instanceof COSString))
/*      */       {
/*  642 */         retval = ((COSString)name).getString();
/*      */       }
/*      */     }
/*  645 */     return retval;
/*      */   }
/*      */ 
/*      */   public String getNameAsString(String key, String defaultValue)
/*      */   {
/*  660 */     return getNameAsString(COSName.getPDFName(key), defaultValue);
/*      */   }
/*      */ 
/*      */   public String getNameAsString(COSName key, String defaultValue)
/*      */   {
/*  675 */     String retval = getNameAsString(key);
/*  676 */     if (retval == null)
/*      */     {
/*  678 */       retval = defaultValue;
/*      */     }
/*  680 */     return retval;
/*      */   }
/*      */ 
/*      */   public String getString(String key)
/*      */   {
/*  693 */     return getString(COSName.getPDFName(key));
/*      */   }
/*      */ 
/*      */   public String getString(COSName key)
/*      */   {
/*  706 */     String retval = null;
/*  707 */     COSBase value = getDictionaryObject(key);
/*  708 */     if ((value != null) && ((value instanceof COSString)))
/*      */     {
/*  710 */       retval = ((COSString)value).getString();
/*      */     }
/*  712 */     return retval;
/*      */   }
/*      */ 
/*      */   public String getString(String key, String defaultValue)
/*      */   {
/*  727 */     return getString(COSName.getPDFName(key), defaultValue);
/*      */   }
/*      */ 
/*      */   public String getString(COSName key, String defaultValue)
/*      */   {
/*  742 */     String retval = getString(key);
/*  743 */     if (retval == null)
/*      */     {
/*  745 */       retval = defaultValue;
/*      */     }
/*  747 */     return retval;
/*      */   }
/*      */ 
/*      */   public String getEmbeddedString(String embedded, String key)
/*      */   {
/*  762 */     return getEmbeddedString(embedded, COSName.getPDFName(key), null);
/*      */   }
/*      */ 
/*      */   public String getEmbeddedString(String embedded, COSName key)
/*      */   {
/*  777 */     return getEmbeddedString(embedded, key, null);
/*      */   }
/*      */ 
/*      */   public String getEmbeddedString(String embedded, String key, String defaultValue)
/*      */   {
/*  794 */     return getEmbeddedString(embedded, COSName.getPDFName(key), defaultValue);
/*      */   }
/*      */ 
/*      */   public String getEmbeddedString(String embedded, COSName key, String defaultValue)
/*      */   {
/*  811 */     String retval = defaultValue;
/*  812 */     COSDictionary dic = (COSDictionary)getDictionaryObject(embedded);
/*  813 */     if (dic != null)
/*      */     {
/*  815 */       retval = dic.getString(key, defaultValue);
/*      */     }
/*  817 */     return retval;
/*      */   }
/*      */ 
/*      */   public Calendar getDate(String key)
/*      */     throws IOException
/*      */   {
/*  832 */     return getDate(COSName.getPDFName(key));
/*      */   }
/*      */ 
/*      */   public Calendar getDate(COSName key)
/*      */     throws IOException
/*      */   {
/*  848 */     COSString date = (COSString)getDictionaryObject(key);
/*  849 */     return DateConverter.toCalendar(date);
/*      */   }
/*      */ 
/*      */   public Calendar getDate(String key, Calendar defaultValue)
/*      */     throws IOException
/*      */   {
/*  866 */     return getDate(COSName.getPDFName(key), defaultValue);
/*      */   }
/*      */ 
/*      */   public Calendar getDate(COSName key, Calendar defaultValue)
/*      */     throws IOException
/*      */   {
/*  883 */     Calendar retval = getDate(key);
/*  884 */     if (retval == null)
/*      */     {
/*  886 */       retval = defaultValue;
/*      */     }
/*  888 */     return retval;
/*      */   }
/*      */ 
/*      */   public Calendar getEmbeddedDate(String embedded, String key)
/*      */     throws IOException
/*      */   {
/*  905 */     return getEmbeddedDate(embedded, COSName.getPDFName(key), null);
/*      */   }
/*      */ 
/*      */   public Calendar getEmbeddedDate(String embedded, COSName key)
/*      */     throws IOException
/*      */   {
/*  923 */     return getEmbeddedDate(embedded, key, null);
/*      */   }
/*      */ 
/*      */   public Calendar getEmbeddedDate(String embedded, String key, Calendar defaultValue)
/*      */     throws IOException
/*      */   {
/*  942 */     return getEmbeddedDate(embedded, COSName.getPDFName(key), defaultValue);
/*      */   }
/*      */ 
/*      */   public Calendar getEmbeddedDate(String embedded, COSName key, Calendar defaultValue)
/*      */     throws IOException
/*      */   {
/*  961 */     Calendar retval = defaultValue;
/*  962 */     COSDictionary eDic = (COSDictionary)getDictionaryObject(embedded);
/*  963 */     if (eDic != null)
/*      */     {
/*  965 */       retval = eDic.getDate(key, defaultValue);
/*      */     }
/*  967 */     return retval;
/*      */   }
/*      */ 
/*      */   public boolean getBoolean(String key, boolean defaultValue)
/*      */   {
/*  983 */     return getBoolean(COSName.getPDFName(key), defaultValue);
/*      */   }
/*      */ 
/*      */   public boolean getBoolean(COSName key, boolean defaultValue)
/*      */   {
/*  999 */     return getBoolean(key, null, defaultValue);
/*      */   }
/*      */ 
/*      */   public boolean getBoolean(COSName firstKey, COSName secondKey, boolean defaultValue)
/*      */   {
/* 1017 */     boolean retval = defaultValue;
/* 1018 */     COSBase bool = getDictionaryObject(firstKey, secondKey);
/* 1019 */     if ((bool != null) && ((bool instanceof COSBoolean)))
/*      */     {
/* 1021 */       retval = ((COSBoolean)bool).getValue();
/*      */     }
/* 1023 */     return retval;
/*      */   }
/*      */ 
/*      */   public int getEmbeddedInt(String embeddedDictionary, String key)
/*      */   {
/* 1038 */     return getEmbeddedInt(embeddedDictionary, COSName.getPDFName(key));
/*      */   }
/*      */ 
/*      */   public int getEmbeddedInt(String embeddedDictionary, COSName key)
/*      */   {
/* 1053 */     return getEmbeddedInt(embeddedDictionary, key, -1);
/*      */   }
/*      */ 
/*      */   public int getEmbeddedInt(String embeddedDictionary, String key, int defaultValue)
/*      */   {
/* 1070 */     return getEmbeddedInt(embeddedDictionary, COSName.getPDFName(key), defaultValue);
/*      */   }
/*      */ 
/*      */   public int getEmbeddedInt(String embeddedDictionary, COSName key, int defaultValue)
/*      */   {
/* 1087 */     int retval = defaultValue;
/* 1088 */     COSDictionary embedded = (COSDictionary)getDictionaryObject(embeddedDictionary);
/* 1089 */     if (embedded != null)
/*      */     {
/* 1091 */       retval = embedded.getInt(key, defaultValue);
/*      */     }
/* 1093 */     return retval;
/*      */   }
/*      */ 
/*      */   public int getInt(String key)
/*      */   {
/* 1106 */     return getInt(COSName.getPDFName(key), -1);
/*      */   }
/*      */ 
/*      */   public int getInt(COSName key)
/*      */   {
/* 1119 */     return getInt(key, -1);
/*      */   }
/*      */ 
/*      */   public int getInt(String[] keyList, int defaultValue)
/*      */   {
/* 1134 */     int retval = defaultValue;
/* 1135 */     COSBase obj = getDictionaryObject(keyList);
/* 1136 */     if ((obj != null) && ((obj instanceof COSNumber)))
/*      */     {
/* 1138 */       retval = ((COSNumber)obj).intValue();
/*      */     }
/* 1140 */     return retval;
/*      */   }
/*      */ 
/*      */   public int getInt(String key, int defaultValue)
/*      */   {
/* 1155 */     return getInt(COSName.getPDFName(key), defaultValue);
/*      */   }
/*      */ 
/*      */   public int getInt(COSName key, int defaultValue)
/*      */   {
/* 1170 */     return getInt(key, null, defaultValue);
/*      */   }
/*      */ 
/*      */   public int getInt(COSName firstKey, COSName secondKey)
/*      */   {
/* 1185 */     return getInt(firstKey, secondKey, -1);
/*      */   }
/*      */ 
/*      */   public int getInt(COSName firstKey, COSName secondKey, int defaultValue)
/*      */   {
/* 1202 */     int retval = defaultValue;
/* 1203 */     COSBase obj = getDictionaryObject(firstKey, secondKey);
/* 1204 */     if ((obj != null) && ((obj instanceof COSNumber)))
/*      */     {
/* 1206 */       retval = ((COSNumber)obj).intValue();
/*      */     }
/* 1208 */     return retval;
/*      */   }
/*      */ 
/*      */   public long getLong(String key)
/*      */   {
/* 1222 */     return getLong(COSName.getPDFName(key), -1L);
/*      */   }
/*      */ 
/*      */   public long getLong(COSName key)
/*      */   {
/* 1235 */     return getLong(key, -1L);
/*      */   }
/*      */ 
/*      */   public long getLong(String[] keyList, long defaultValue)
/*      */   {
/* 1250 */     long retval = defaultValue;
/* 1251 */     COSBase obj = getDictionaryObject(keyList);
/* 1252 */     if ((obj != null) && ((obj instanceof COSNumber)))
/*      */     {
/* 1254 */       retval = ((COSNumber)obj).longValue();
/*      */     }
/* 1256 */     return retval;
/*      */   }
/*      */ 
/*      */   public long getLong(String key, long defaultValue)
/*      */   {
/* 1271 */     return getLong(COSName.getPDFName(key), defaultValue);
/*      */   }
/*      */ 
/*      */   public long getLong(COSName key, long defaultValue)
/*      */   {
/* 1286 */     long retval = defaultValue;
/* 1287 */     COSBase obj = getDictionaryObject(key);
/* 1288 */     if ((obj != null) && ((obj instanceof COSNumber)))
/*      */     {
/* 1290 */       retval = ((COSNumber)obj).longValue();
/*      */     }
/* 1292 */     return retval;
/*      */   }
/*      */ 
/*      */   public float getFloat(String key)
/*      */   {
/* 1305 */     return getFloat(COSName.getPDFName(key), -1.0F);
/*      */   }
/*      */ 
/*      */   public float getFloat(COSName key)
/*      */   {
/* 1318 */     return getFloat(key, -1.0F);
/*      */   }
/*      */ 
/*      */   public float getFloat(String key, float defaultValue)
/*      */   {
/* 1333 */     return getFloat(COSName.getPDFName(key), defaultValue);
/*      */   }
/*      */ 
/*      */   public float getFloat(COSName key, float defaultValue)
/*      */   {
/* 1348 */     float retval = defaultValue;
/* 1349 */     COSBase obj = getDictionaryObject(key);
/* 1350 */     if ((obj != null) && ((obj instanceof COSNumber)))
/*      */     {
/* 1352 */       retval = ((COSNumber)obj).floatValue();
/*      */     }
/* 1354 */     return retval;
/*      */   }
/*      */ 
/*      */   public void removeItem(COSName key)
/*      */   {
/* 1365 */     this.items.remove(key);
/*      */   }
/*      */ 
/*      */   public COSBase getItem(COSName key)
/*      */   {
/* 1378 */     return (COSBase)this.items.get(key);
/*      */   }
/*      */ 
/*      */   public COSBase getItem(String key)
/*      */   {
/* 1391 */     return getItem(COSName.getPDFName(key));
/*      */   }
/*      */ 
/*      */   /** @deprecated */
/*      */   public List<COSName> keyList()
/*      */   {
/* 1402 */     return new ArrayList(this.items.keySet());
/*      */   }
/*      */ 
/*      */   public Set<COSName> keySet()
/*      */   {
/* 1414 */     return this.items.keySet();
/*      */   }
/*      */ 
/*      */   public Set<Map.Entry<COSName, COSBase>> entrySet()
/*      */   {
/* 1426 */     return this.items.entrySet();
/*      */   }
/*      */ 
/*      */   public Collection<COSBase> getValues()
/*      */   {
/* 1436 */     return this.items.values();
/*      */   }
/*      */ 
/*      */   public Object accept(ICOSVisitor visitor)
/*      */     throws COSVisitorException
/*      */   {
/* 1451 */     return visitor.visitFromDictionary(this);
/*      */   }
/*      */ 
/*      */   public void addAll(COSDictionary dic)
/*      */   {
/* 1463 */     for (Map.Entry entry : dic.entrySet())
/*      */     {
/* 1469 */       if ((!((COSName)entry.getKey()).getName().equals("Size")) || (!this.items.containsKey(COSName.getPDFName("Size"))))
/*      */       {
/* 1471 */         setItem((COSName)entry.getKey(), (COSBase)entry.getValue());
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   public boolean containsKey(COSName name)
/*      */   {
/* 1485 */     return this.items.containsKey(name);
/*      */   }
/*      */ 
/*      */   public boolean containsKey(String name)
/*      */   {
/* 1497 */     return containsKey(COSName.getPDFName(name));
/*      */   }
/*      */ 
/*      */   public void mergeInto(COSDictionary dic)
/*      */   {
/* 1509 */     for (Map.Entry entry : dic.entrySet())
/*      */     {
/* 1511 */       if (getItem((COSName)entry.getKey()) == null)
/*      */       {
/* 1513 */         setItem((COSName)entry.getKey(), (COSBase)entry.getValue());
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   public COSBase getObjectFromPath(String objPath)
/*      */   {
/* 1528 */     COSBase retval = null;
/* 1529 */     String[] path = objPath.split("/");
/* 1530 */     retval = this;
/*      */ 
/* 1532 */     for (int i = 0; i < path.length; i++)
/*      */     {
/* 1534 */       if ((retval instanceof COSArray))
/*      */       {
/* 1536 */         int idx = new Integer(path[i].replaceAll("\\[", "").replaceAll("\\]", "")).intValue();
/* 1537 */         retval = ((COSArray)retval).getObject(idx);
/*      */       }
/* 1539 */       else if ((retval instanceof COSDictionary))
/*      */       {
/* 1541 */         retval = ((COSDictionary)retval).getDictionaryObject(path[i]);
/*      */       }
/*      */     }
/* 1544 */     return retval;
/*      */   }
/*      */ 
/*      */   public String toString()
/*      */   {
/* 1553 */     StringBuilder retVal = new StringBuilder("COSDictionary{");
/* 1554 */     for (COSName key : this.items.keySet())
/*      */     {
/* 1556 */       retVal.append("(");
/* 1557 */       retVal.append(key);
/* 1558 */       retVal.append(":");
/* 1559 */       if (getDictionaryObject(key) != null)
/* 1560 */         retVal.append(getDictionaryObject(key).toString());
/*      */       else
/* 1562 */         retVal.append("<null>");
/* 1563 */       retVal.append(") ");
/*      */     }
/* 1565 */     retVal.append("}");
/* 1566 */     return retVal.toString();
/*      */   }
/*      */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.cos.COSDictionary
 * JD-Core Version:    0.6.2
 */