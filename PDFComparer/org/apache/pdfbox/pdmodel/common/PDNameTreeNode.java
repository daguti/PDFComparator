/*     */ package org.apache.pdfbox.pdmodel.common;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.LinkedHashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.commons.logging.LogFactory;
/*     */ import org.apache.pdfbox.cos.COSArray;
/*     */ import org.apache.pdfbox.cos.COSBase;
/*     */ import org.apache.pdfbox.cos.COSDictionary;
/*     */ import org.apache.pdfbox.cos.COSName;
/*     */ import org.apache.pdfbox.cos.COSString;
/*     */ 
/*     */ public class PDNameTreeNode
/*     */   implements COSObjectable
/*     */ {
/*  43 */   private static final Log LOG = LogFactory.getLog(PDNameTreeNode.class);
/*     */   private COSDictionary node;
/*  45 */   private Class<? extends COSObjectable> valueType = null;
/*  46 */   private PDNameTreeNode parent = null;
/*     */ 
/*     */   public PDNameTreeNode(Class<? extends COSObjectable> valueClass)
/*     */   {
/*  55 */     this.node = new COSDictionary();
/*  56 */     this.valueType = valueClass;
/*     */   }
/*     */ 
/*     */   public PDNameTreeNode(COSDictionary dict, Class<? extends COSObjectable> valueClass)
/*     */   {
/*  67 */     this.node = dict;
/*  68 */     this.valueType = valueClass;
/*     */   }
/*     */ 
/*     */   public COSBase getCOSObject()
/*     */   {
/*  78 */     return this.node;
/*     */   }
/*     */ 
/*     */   public COSDictionary getCOSDictionary()
/*     */   {
/*  88 */     return this.node;
/*     */   }
/*     */ 
/*     */   public PDNameTreeNode getParent()
/*     */   {
/*  98 */     return this.parent;
/*     */   }
/*     */ 
/*     */   public void setParent(PDNameTreeNode parentNode)
/*     */   {
/* 108 */     this.parent = parentNode;
/* 109 */     calculateLimits();
/*     */   }
/*     */ 
/*     */   public boolean isRootNode()
/*     */   {
/* 119 */     return this.parent == null;
/*     */   }
/*     */ 
/*     */   public List<PDNameTreeNode> getKids()
/*     */   {
/* 129 */     List retval = null;
/* 130 */     COSArray kids = (COSArray)this.node.getDictionaryObject(COSName.KIDS);
/* 131 */     if (kids != null)
/*     */     {
/* 133 */       List pdObjects = new ArrayList();
/* 134 */       for (int i = 0; i < kids.size(); i++)
/*     */       {
/* 136 */         pdObjects.add(createChildNode((COSDictionary)kids.getObject(i)));
/*     */       }
/* 138 */       retval = new COSArrayList(pdObjects, kids);
/*     */     }
/*     */ 
/* 141 */     return retval;
/*     */   }
/*     */ 
/*     */   public void setKids(List<? extends PDNameTreeNode> kids)
/*     */   {
/* 151 */     if ((kids != null) && (kids.size() > 0))
/*     */     {
/* 153 */       for (PDNameTreeNode kidsNode : kids)
/* 154 */         kidsNode.setParent(this);
/* 155 */       this.node.setItem(COSName.KIDS, COSArrayList.converterToCOSArray(kids));
/*     */ 
/* 157 */       if (isRootNode())
/*     */       {
/* 159 */         this.node.setItem(COSName.NAMES, null);
/*     */       }
/*     */ 
/*     */     }
/*     */     else
/*     */     {
/* 165 */       this.node.setItem(COSName.KIDS, null);
/*     */ 
/* 167 */       this.node.setItem(COSName.LIMITS, null);
/*     */     }
/* 169 */     calculateLimits();
/*     */   }
/*     */ 
/*     */   private void calculateLimits()
/*     */   {
/* 174 */     if (isRootNode())
/*     */     {
/* 176 */       this.node.setItem(COSName.LIMITS, null);
/*     */     }
/*     */     else
/*     */     {
/* 180 */       List kids = getKids();
/* 181 */       if ((kids != null) && (kids.size() > 0))
/*     */       {
/* 183 */         PDNameTreeNode firstKid = (PDNameTreeNode)kids.get(0);
/* 184 */         PDNameTreeNode lastKid = (PDNameTreeNode)kids.get(kids.size() - 1);
/* 185 */         String lowerLimit = firstKid.getLowerLimit();
/* 186 */         setLowerLimit(lowerLimit);
/* 187 */         String upperLimit = lastKid.getUpperLimit();
/* 188 */         setUpperLimit(upperLimit);
/*     */       }
/*     */       else
/*     */       {
/*     */         try
/*     */         {
/* 194 */           Map names = getNames();
/* 195 */           if ((names != null) && (names.size() > 0))
/*     */           {
/* 197 */             Object[] keys = names.keySet().toArray();
/* 198 */             String lowerLimit = (String)keys[0];
/* 199 */             setLowerLimit(lowerLimit);
/* 200 */             String upperLimit = (String)keys[(keys.length - 1)];
/* 201 */             setUpperLimit(upperLimit);
/*     */           }
/*     */           else
/*     */           {
/* 205 */             this.node.setItem(COSName.LIMITS, null);
/*     */           }
/*     */         }
/*     */         catch (IOException exception)
/*     */         {
/* 210 */           this.node.setItem(COSName.LIMITS, null);
/* 211 */           LOG.error("Error while calculating the Limits of a PageNameTreeNode:", exception);
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public Object getValue(String name)
/*     */     throws IOException
/*     */   {
/* 227 */     Object retval = null;
/* 228 */     Map names = getNames();
/* 229 */     if (names != null)
/*     */     {
/* 231 */       retval = names.get(name);
/*     */     }
/*     */     else
/*     */     {
/* 235 */       List kids = getKids();
/* 236 */       if (kids != null)
/*     */       {
/* 238 */         for (int i = 0; (i < kids.size()) && (retval == null); i++)
/*     */         {
/* 240 */           PDNameTreeNode childNode = (PDNameTreeNode)kids.get(i);
/* 241 */           if ((childNode.getLowerLimit().compareTo(name) <= 0) && (childNode.getUpperLimit().compareTo(name) >= 0))
/*     */           {
/* 244 */             retval = childNode.getValue(name);
/*     */           }
/*     */         }
/*     */       }
/*     */       else
/*     */       {
/* 250 */         LOG.warn("NameTreeNode does not have \"names\" nor \"kids\" objects.");
/*     */       }
/*     */     }
/* 253 */     return retval;
/*     */   }
/*     */ 
/*     */   public Map<String, COSObjectable> getNames()
/*     */     throws IOException
/*     */   {
/* 267 */     COSArray namesArray = (COSArray)this.node.getDictionaryObject(COSName.NAMES);
/* 268 */     if (namesArray != null)
/*     */     {
/* 270 */       Map names = new LinkedHashMap();
/* 271 */       for (int i = 0; i < namesArray.size(); i += 2)
/*     */       {
/* 273 */         COSString key = (COSString)namesArray.getObject(i);
/* 274 */         COSBase cosValue = namesArray.getObject(i + 1);
/* 275 */         names.put(key.getString(), convertCOSToPD(cosValue));
/*     */       }
/* 277 */       return Collections.unmodifiableMap(names);
/*     */     }
/*     */ 
/* 281 */     return null;
/*     */   }
/*     */ 
/*     */   protected COSObjectable convertCOSToPD(COSBase base)
/*     */     throws IOException
/*     */   {
/* 296 */     return base;
/*     */   }
/*     */ 
/*     */   protected PDNameTreeNode createChildNode(COSDictionary dic)
/*     */   {
/* 307 */     return new PDNameTreeNode(dic, this.valueType);
/*     */   }
/*     */ 
/*     */   public void setNames(Map<String, ? extends COSObjectable> names)
/*     */   {
/* 319 */     if (names == null)
/*     */     {
/* 321 */       this.node.setItem(COSName.NAMES, (COSObjectable)null);
/* 322 */       this.node.setItem(COSName.LIMITS, (COSObjectable)null);
/*     */     }
/*     */     else
/*     */     {
/* 326 */       COSArray array = new COSArray();
/* 327 */       List keys = new ArrayList(names.keySet());
/* 328 */       Collections.sort(keys);
/* 329 */       for (String key : keys)
/*     */       {
/* 331 */         array.add(new COSString(key));
/* 332 */         array.add((COSObjectable)names.get(key));
/*     */       }
/* 334 */       this.node.setItem(COSName.NAMES, array);
/* 335 */       calculateLimits();
/*     */     }
/*     */   }
/*     */ 
/*     */   public String getUpperLimit()
/*     */   {
/* 346 */     String retval = null;
/* 347 */     COSArray arr = (COSArray)this.node.getDictionaryObject(COSName.LIMITS);
/* 348 */     if (arr != null)
/*     */     {
/* 350 */       retval = arr.getString(1);
/*     */     }
/* 352 */     return retval;
/*     */   }
/*     */ 
/*     */   private void setUpperLimit(String upper)
/*     */   {
/* 362 */     COSArray arr = (COSArray)this.node.getDictionaryObject(COSName.LIMITS);
/* 363 */     if (arr == null)
/*     */     {
/* 365 */       arr = new COSArray();
/* 366 */       arr.add(null);
/* 367 */       arr.add(null);
/* 368 */       this.node.setItem(COSName.LIMITS, arr);
/*     */     }
/* 370 */     arr.setString(1, upper);
/*     */   }
/*     */ 
/*     */   public String getLowerLimit()
/*     */   {
/* 380 */     String retval = null;
/* 381 */     COSArray arr = (COSArray)this.node.getDictionaryObject(COSName.LIMITS);
/* 382 */     if (arr != null)
/*     */     {
/* 384 */       retval = arr.getString(0);
/*     */     }
/* 386 */     return retval;
/*     */   }
/*     */ 
/*     */   private void setLowerLimit(String lower)
/*     */   {
/* 396 */     COSArray arr = (COSArray)this.node.getDictionaryObject(COSName.LIMITS);
/* 397 */     if (arr == null)
/*     */     {
/* 399 */       arr = new COSArray();
/* 400 */       arr.add(null);
/* 401 */       arr.add(null);
/* 402 */       this.node.setItem(COSName.LIMITS, arr);
/*     */     }
/* 404 */     arr.setString(0, lower);
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.pdmodel.common.PDNameTreeNode
 * JD-Core Version:    0.6.2
 */