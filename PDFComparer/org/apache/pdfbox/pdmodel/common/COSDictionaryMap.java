/*     */ package org.apache.pdfbox.pdmodel.common;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.Map;
/*     */ import java.util.Map.Entry;
/*     */ import java.util.Set;
/*     */ import org.apache.pdfbox.cos.COSBase;
/*     */ import org.apache.pdfbox.cos.COSBoolean;
/*     */ import org.apache.pdfbox.cos.COSDictionary;
/*     */ import org.apache.pdfbox.cos.COSFloat;
/*     */ import org.apache.pdfbox.cos.COSInteger;
/*     */ import org.apache.pdfbox.cos.COSName;
/*     */ import org.apache.pdfbox.cos.COSString;
/*     */ 
/*     */ public class COSDictionaryMap<K, V>
/*     */   implements Map<K, V>
/*     */ {
/*     */   private COSDictionary map;
/*     */   private Map<K, V> actuals;
/*     */ 
/*     */   public COSDictionaryMap(Map<K, V> actualsMap, COSDictionary dicMap)
/*     */   {
/*  54 */     this.actuals = actualsMap;
/*  55 */     this.map = dicMap;
/*     */   }
/*     */ 
/*     */   public int size()
/*     */   {
/*  64 */     return this.map.size();
/*     */   }
/*     */ 
/*     */   public boolean isEmpty()
/*     */   {
/*  72 */     return size() == 0;
/*     */   }
/*     */ 
/*     */   public boolean containsKey(Object key)
/*     */   {
/*  80 */     return this.map.keySet().contains(key);
/*     */   }
/*     */ 
/*     */   public boolean containsValue(Object value)
/*     */   {
/*  88 */     return this.actuals.containsValue(value);
/*     */   }
/*     */ 
/*     */   public V get(Object key)
/*     */   {
/*  96 */     return this.actuals.get(key);
/*     */   }
/*     */ 
/*     */   public V put(K key, V value)
/*     */   {
/* 104 */     COSObjectable object = (COSObjectable)value;
/*     */ 
/* 106 */     this.map.setItem(COSName.getPDFName((String)key), object.getCOSObject());
/* 107 */     return this.actuals.put(key, value);
/*     */   }
/*     */ 
/*     */   public V remove(Object key)
/*     */   {
/* 115 */     this.map.removeItem(COSName.getPDFName((String)key));
/* 116 */     return this.actuals.remove(key);
/*     */   }
/*     */ 
/*     */   public void putAll(Map<? extends K, ? extends V> t)
/*     */   {
/* 124 */     throw new RuntimeException("Not yet implemented");
/*     */   }
/*     */ 
/*     */   public void clear()
/*     */   {
/* 132 */     this.map.clear();
/* 133 */     this.actuals.clear();
/*     */   }
/*     */ 
/*     */   public Set<K> keySet()
/*     */   {
/* 141 */     return this.actuals.keySet();
/*     */   }
/*     */ 
/*     */   public Collection<V> values()
/*     */   {
/* 149 */     return this.actuals.values();
/*     */   }
/*     */ 
/*     */   public Set<Map.Entry<K, V>> entrySet()
/*     */   {
/* 157 */     return Collections.unmodifiableSet(this.actuals.entrySet());
/*     */   }
/*     */ 
/*     */   public boolean equals(Object o)
/*     */   {
/* 165 */     boolean retval = false;
/* 166 */     if ((o instanceof COSDictionaryMap))
/*     */     {
/* 168 */       COSDictionaryMap other = (COSDictionaryMap)o;
/* 169 */       retval = other.map.equals(this.map);
/*     */     }
/* 171 */     return retval;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 179 */     return this.actuals.toString();
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/* 187 */     return this.map.hashCode();
/*     */   }
/*     */ 
/*     */   public static COSDictionary convert(Map<?, ?> someMap)
/*     */   {
/* 200 */     Iterator iter = someMap.keySet().iterator();
/* 201 */     COSDictionary dic = new COSDictionary();
/* 202 */     while (iter.hasNext())
/*     */     {
/* 204 */       String name = (String)iter.next();
/* 205 */       COSObjectable object = (COSObjectable)someMap.get(name);
/* 206 */       dic.setItem(COSName.getPDFName(name), object.getCOSObject());
/*     */     }
/* 208 */     return dic;
/*     */   }
/*     */ 
/*     */   public static COSDictionaryMap<String, Object> convertBasicTypesToMap(COSDictionary map)
/*     */     throws IOException
/*     */   {
/* 221 */     COSDictionaryMap retval = null;
/* 222 */     if (map != null)
/*     */     {
/* 224 */       Map actualMap = new HashMap();
/* 225 */       for (COSName key : map.keySet())
/*     */       {
/* 227 */         COSBase cosObj = map.getDictionaryObject(key);
/* 228 */         Object actualObject = null;
/* 229 */         if ((cosObj instanceof COSString))
/*     */         {
/* 231 */           actualObject = ((COSString)cosObj).getString();
/*     */         }
/* 233 */         else if ((cosObj instanceof COSInteger))
/*     */         {
/* 235 */           actualObject = new Integer(((COSInteger)cosObj).intValue());
/*     */         }
/* 237 */         else if ((cosObj instanceof COSName))
/*     */         {
/* 239 */           actualObject = ((COSName)cosObj).getName();
/*     */         }
/* 241 */         else if ((cosObj instanceof COSFloat))
/*     */         {
/* 243 */           actualObject = new Float(((COSFloat)cosObj).floatValue());
/*     */         }
/* 245 */         else if ((cosObj instanceof COSBoolean))
/*     */         {
/* 247 */           actualObject = ((COSBoolean)cosObj).getValue() ? Boolean.TRUE : Boolean.FALSE;
/*     */         }
/*     */         else
/*     */         {
/* 251 */           throw new IOException("Error:unknown type of object to convert:" + cosObj);
/*     */         }
/* 253 */         actualMap.put(key.getName(), actualObject);
/*     */       }
/* 255 */       retval = new COSDictionaryMap(actualMap, map);
/*     */     }
/*     */ 
/* 258 */     return retval;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.pdmodel.common.COSDictionaryMap
 * JD-Core Version:    0.6.2
 */