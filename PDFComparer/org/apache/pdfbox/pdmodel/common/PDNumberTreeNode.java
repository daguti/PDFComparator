/*     */ package org.apache.pdfbox.pdmodel.common;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.lang.reflect.Constructor;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.commons.logging.LogFactory;
/*     */ import org.apache.pdfbox.cos.COSArray;
/*     */ import org.apache.pdfbox.cos.COSBase;
/*     */ import org.apache.pdfbox.cos.COSDictionary;
/*     */ import org.apache.pdfbox.cos.COSInteger;
/*     */ import org.apache.pdfbox.cos.COSName;
/*     */ 
/*     */ public class PDNumberTreeNode
/*     */   implements COSObjectable
/*     */ {
/*  45 */   private static final Log LOG = LogFactory.getLog(PDNumberTreeNode.class);
/*     */   private COSDictionary node;
/*  48 */   private Class<? extends COSObjectable> valueType = null;
/*     */ 
/*     */   public PDNumberTreeNode(Class<? extends COSObjectable> valueClass)
/*     */   {
/*  57 */     this.node = new COSDictionary();
/*  58 */     this.valueType = valueClass;
/*     */   }
/*     */ 
/*     */   public PDNumberTreeNode(COSDictionary dict, Class<? extends COSObjectable> valueClass)
/*     */   {
/*  69 */     this.node = dict;
/*  70 */     this.valueType = valueClass;
/*     */   }
/*     */ 
/*     */   public COSBase getCOSObject()
/*     */   {
/*  80 */     return this.node;
/*     */   }
/*     */ 
/*     */   public COSDictionary getCOSDictionary()
/*     */   {
/*  90 */     return this.node;
/*     */   }
/*     */ 
/*     */   public List<PDNumberTreeNode> getKids()
/*     */   {
/* 100 */     List retval = null;
/* 101 */     COSArray kids = (COSArray)this.node.getDictionaryObject(COSName.KIDS);
/* 102 */     if (kids != null)
/*     */     {
/* 104 */       List pdObjects = new ArrayList();
/* 105 */       for (int i = 0; i < kids.size(); i++)
/*     */       {
/* 107 */         pdObjects.add(createChildNode((COSDictionary)kids.getObject(i)));
/*     */       }
/* 109 */       retval = new COSArrayList(pdObjects, kids);
/*     */     }
/*     */ 
/* 112 */     return retval;
/*     */   }
/*     */ 
/*     */   public void setKids(List<? extends PDNumberTreeNode> kids)
/*     */   {
/* 122 */     if ((kids != null) && (kids.size() > 0))
/*     */     {
/* 124 */       PDNumberTreeNode firstKid = (PDNumberTreeNode)kids.get(0);
/* 125 */       PDNumberTreeNode lastKid = (PDNumberTreeNode)kids.get(kids.size() - 1);
/* 126 */       Integer lowerLimit = firstKid.getLowerLimit();
/* 127 */       setLowerLimit(lowerLimit);
/* 128 */       Integer upperLimit = lastKid.getUpperLimit();
/* 129 */       setUpperLimit(upperLimit);
/*     */     }
/* 131 */     else if (this.node.getDictionaryObject(COSName.NUMS) == null)
/*     */     {
/* 134 */       this.node.setItem(COSName.LIMITS, null);
/*     */     }
/* 136 */     this.node.setItem(COSName.KIDS, COSArrayList.converterToCOSArray(kids));
/*     */   }
/*     */ 
/*     */   public Object getValue(Integer index)
/*     */     throws IOException
/*     */   {
/* 150 */     Object retval = null;
/* 151 */     Map names = getNumbers();
/* 152 */     if (names != null)
/*     */     {
/* 154 */       retval = names.get(index);
/*     */     }
/*     */     else
/*     */     {
/* 158 */       List kids = getKids();
/* 159 */       if (kids != null)
/*     */       {
/* 161 */         for (int i = 0; (i < kids.size()) && (retval == null); i++)
/*     */         {
/* 163 */           PDNumberTreeNode childNode = (PDNumberTreeNode)kids.get(i);
/* 164 */           if ((childNode.getLowerLimit().compareTo(index) <= 0) && (childNode.getUpperLimit().compareTo(index) >= 0))
/*     */           {
/* 167 */             retval = childNode.getValue(index);
/*     */           }
/*     */         }
/*     */       }
/*     */       else
/*     */       {
/* 173 */         LOG.warn("NumberTreeNode does not have \"nums\" nor \"kids\" objects.");
/*     */       }
/*     */     }
/* 176 */     return retval;
/*     */   }
/*     */ 
/*     */   public Map<Integer, COSObjectable> getNumbers()
/*     */     throws IOException
/*     */   {
/* 190 */     Map indices = null;
/* 191 */     COSArray namesArray = (COSArray)this.node.getDictionaryObject(COSName.NUMS);
/* 192 */     if (namesArray != null)
/*     */     {
/* 194 */       indices = new HashMap();
/* 195 */       for (int i = 0; i < namesArray.size(); i += 2)
/*     */       {
/* 197 */         COSInteger key = (COSInteger)namesArray.getObject(i);
/* 198 */         COSBase cosValue = namesArray.getObject(i + 1);
/* 199 */         COSObjectable pdValue = convertCOSToPD(cosValue);
/* 200 */         indices.put(Integer.valueOf(key.intValue()), pdValue);
/*     */       }
/* 202 */       indices = Collections.unmodifiableMap(indices);
/*     */     }
/* 204 */     return indices;
/*     */   }
/*     */ 
/*     */   protected COSObjectable convertCOSToPD(COSBase base)
/*     */     throws IOException
/*     */   {
/* 218 */     COSObjectable retval = null;
/*     */     try
/*     */     {
/* 221 */       Constructor ctor = this.valueType.getConstructor(new Class[] { base.getClass() });
/* 222 */       retval = (COSObjectable)ctor.newInstance(new Object[] { base });
/*     */     }
/*     */     catch (Throwable t)
/*     */     {
/* 226 */       throw new IOException("Error while trying to create value in number tree:" + t.getMessage());
/*     */     }
/*     */ 
/* 229 */     return retval;
/*     */   }
/*     */ 
/*     */   protected PDNumberTreeNode createChildNode(COSDictionary dic)
/*     */   {
/* 240 */     return new PDNumberTreeNode(dic, this.valueType);
/*     */   }
/*     */ 
/*     */   public void setNumbers(Map<Integer, ? extends COSObjectable> numbers)
/*     */   {
/* 252 */     if (numbers == null)
/*     */     {
/* 254 */       this.node.setItem(COSName.NUMS, (COSObjectable)null);
/* 255 */       this.node.setItem(COSName.LIMITS, (COSObjectable)null);
/*     */     }
/*     */     else
/*     */     {
/* 259 */       List keys = new ArrayList(numbers.keySet());
/* 260 */       Collections.sort(keys);
/* 261 */       COSArray array = new COSArray();
/* 262 */       for (int i = 0; i < keys.size(); i++)
/*     */       {
/* 264 */         Integer key = (Integer)keys.get(i);
/* 265 */         array.add(COSInteger.get(key.intValue()));
/* 266 */         COSObjectable obj = (COSObjectable)numbers.get(key);
/* 267 */         array.add(obj);
/*     */       }
/* 269 */       Integer lower = null;
/* 270 */       Integer upper = null;
/* 271 */       if (keys.size() > 0)
/*     */       {
/* 273 */         lower = (Integer)keys.get(0);
/* 274 */         upper = (Integer)keys.get(keys.size() - 1);
/*     */       }
/* 276 */       setUpperLimit(upper);
/* 277 */       setLowerLimit(lower);
/* 278 */       this.node.setItem(COSName.NUMS, array);
/*     */     }
/*     */   }
/*     */ 
/*     */   public Integer getUpperLimit()
/*     */   {
/* 289 */     Integer retval = null;
/* 290 */     COSArray arr = (COSArray)this.node.getDictionaryObject(COSName.LIMITS);
/* 291 */     if ((arr != null) && (arr.get(0) != null))
/*     */     {
/* 293 */       retval = Integer.valueOf(arr.getInt(1));
/*     */     }
/* 295 */     return retval;
/*     */   }
/*     */ 
/*     */   private void setUpperLimit(Integer upper)
/*     */   {
/* 305 */     COSArray arr = (COSArray)this.node.getDictionaryObject(COSName.LIMITS);
/* 306 */     if (arr == null)
/*     */     {
/* 308 */       arr = new COSArray();
/* 309 */       arr.add(null);
/* 310 */       arr.add(null);
/* 311 */       this.node.setItem(COSName.LIMITS, arr);
/*     */     }
/* 313 */     if (upper != null)
/*     */     {
/* 315 */       arr.setInt(1, upper.intValue());
/*     */     }
/*     */     else
/*     */     {
/* 319 */       arr.set(1, null);
/*     */     }
/*     */   }
/*     */ 
/*     */   public Integer getLowerLimit()
/*     */   {
/* 330 */     Integer retval = null;
/* 331 */     COSArray arr = (COSArray)this.node.getDictionaryObject(COSName.LIMITS);
/* 332 */     if ((arr != null) && (arr.get(0) != null))
/*     */     {
/* 334 */       retval = Integer.valueOf(arr.getInt(0));
/*     */     }
/* 336 */     return retval;
/*     */   }
/*     */ 
/*     */   private void setLowerLimit(Integer lower)
/*     */   {
/* 346 */     COSArray arr = (COSArray)this.node.getDictionaryObject(COSName.LIMITS);
/* 347 */     if (arr == null)
/*     */     {
/* 349 */       arr = new COSArray();
/* 350 */       arr.add(null);
/* 351 */       arr.add(null);
/* 352 */       this.node.setItem(COSName.LIMITS, arr);
/*     */     }
/* 354 */     if (lower != null)
/*     */     {
/* 356 */       arr.setInt(0, lower.intValue());
/*     */     }
/*     */     else
/*     */     {
/* 360 */       arr.set(0, null);
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.pdmodel.common.PDNumberTreeNode
 * JD-Core Version:    0.6.2
 */