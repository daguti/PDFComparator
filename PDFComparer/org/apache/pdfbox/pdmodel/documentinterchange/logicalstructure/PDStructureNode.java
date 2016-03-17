/*     */ package org.apache.pdfbox.pdmodel.documentinterchange.logicalstructure;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import org.apache.pdfbox.cos.COSArray;
/*     */ import org.apache.pdfbox.cos.COSBase;
/*     */ import org.apache.pdfbox.cos.COSDictionary;
/*     */ import org.apache.pdfbox.cos.COSInteger;
/*     */ import org.apache.pdfbox.cos.COSName;
/*     */ import org.apache.pdfbox.cos.COSObject;
/*     */ import org.apache.pdfbox.pdmodel.common.COSArrayList;
/*     */ import org.apache.pdfbox.pdmodel.common.COSObjectable;
/*     */ 
/*     */ public abstract class PDStructureNode
/*     */   implements COSObjectable
/*     */ {
/*     */   private COSDictionary dictionary;
/*     */ 
/*     */   public static PDStructureNode create(COSDictionary node)
/*     */   {
/*  52 */     String type = node.getNameAsString(COSName.TYPE);
/*  53 */     if ("StructTreeRoot".equals(type))
/*     */     {
/*  55 */       return new PDStructureTreeRoot(node);
/*     */     }
/*  57 */     if ((type == null) || ("StructElem".equals(type)))
/*     */     {
/*  59 */       return new PDStructureElement(node);
/*     */     }
/*  61 */     throw new IllegalArgumentException("Dictionary must not include a Type entry with a value that is neither StructTreeRoot nor StructElem.");
/*     */   }
/*     */ 
/*     */   protected COSDictionary getCOSDictionary()
/*     */   {
/*  69 */     return this.dictionary;
/*     */   }
/*     */ 
/*     */   protected PDStructureNode(String type)
/*     */   {
/*  79 */     this.dictionary = new COSDictionary();
/*  80 */     this.dictionary.setName(COSName.TYPE, type);
/*     */   }
/*     */ 
/*     */   protected PDStructureNode(COSDictionary dictionary)
/*     */   {
/*  90 */     this.dictionary = dictionary;
/*     */   }
/*     */ 
/*     */   public COSBase getCOSObject()
/*     */   {
/*  98 */     return this.dictionary;
/*     */   }
/*     */ 
/*     */   public String getType()
/*     */   {
/* 108 */     return getCOSDictionary().getNameAsString(COSName.TYPE);
/*     */   }
/*     */ 
/*     */   public List<Object> getKids()
/*     */   {
/* 118 */     List kidObjects = new ArrayList();
/* 119 */     COSBase k = getCOSDictionary().getDictionaryObject(COSName.K);
/* 120 */     if ((k instanceof COSArray))
/*     */     {
/* 122 */       Iterator kids = ((COSArray)k).iterator();
/* 123 */       while (kids.hasNext())
/*     */       {
/* 125 */         COSBase kid = (COSBase)kids.next();
/* 126 */         Object kidObject = createObject(kid);
/* 127 */         if (kidObject != null)
/*     */         {
/* 129 */           kidObjects.add(kidObject);
/*     */         }
/*     */       }
/*     */     }
/*     */     else
/*     */     {
/* 135 */       Object kidObject = createObject(k);
/* 136 */       if (kidObject != null)
/*     */       {
/* 138 */         kidObjects.add(kidObject);
/*     */       }
/*     */     }
/* 141 */     return kidObjects;
/*     */   }
/*     */ 
/*     */   public void setKids(List<Object> kids)
/*     */   {
/* 151 */     getCOSDictionary().setItem(COSName.K, COSArrayList.converterToCOSArray(kids));
/*     */   }
/*     */ 
/*     */   public void appendKid(PDStructureElement structureElement)
/*     */   {
/* 162 */     appendObjectableKid(structureElement);
/* 163 */     structureElement.setParent(this);
/*     */   }
/*     */ 
/*     */   protected void appendObjectableKid(COSObjectable objectable)
/*     */   {
/* 173 */     if (objectable == null)
/*     */     {
/* 175 */       return;
/*     */     }
/* 177 */     appendKid(objectable.getCOSObject());
/*     */   }
/*     */ 
/*     */   protected void appendKid(COSBase object)
/*     */   {
/* 187 */     if (object == null)
/*     */     {
/* 189 */       return;
/*     */     }
/* 191 */     COSBase k = getCOSDictionary().getDictionaryObject(COSName.K);
/* 192 */     if (k == null)
/*     */     {
/* 195 */       getCOSDictionary().setItem(COSName.K, object);
/*     */     }
/* 197 */     else if ((k instanceof COSArray))
/*     */     {
/* 200 */       COSArray array = (COSArray)k;
/* 201 */       array.add(object);
/*     */     }
/*     */     else
/*     */     {
/* 206 */       COSArray array = new COSArray();
/* 207 */       array.add(k);
/* 208 */       array.add(object);
/* 209 */       getCOSDictionary().setItem(COSName.K, array);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void insertBefore(PDStructureElement newKid, Object refKid)
/*     */   {
/* 221 */     insertObjectableBefore(newKid, refKid);
/*     */   }
/*     */ 
/*     */   protected void insertObjectableBefore(COSObjectable newKid, Object refKid)
/*     */   {
/* 232 */     if (newKid == null)
/*     */     {
/* 234 */       return;
/*     */     }
/* 236 */     insertBefore(newKid.getCOSObject(), refKid);
/*     */   }
/*     */ 
/*     */   protected void insertBefore(COSBase newKid, Object refKid)
/*     */   {
/* 247 */     if ((newKid == null) || (refKid == null))
/*     */     {
/* 249 */       return;
/*     */     }
/* 251 */     COSBase k = getCOSDictionary().getDictionaryObject(COSName.K);
/* 252 */     if (k == null)
/*     */     {
/* 254 */       return;
/*     */     }
/* 256 */     COSBase refKidBase = null;
/* 257 */     if ((refKid instanceof COSObjectable))
/*     */     {
/* 259 */       refKidBase = ((COSObjectable)refKid).getCOSObject();
/*     */     }
/* 261 */     else if ((refKid instanceof COSInteger))
/*     */     {
/* 263 */       refKidBase = (COSInteger)refKid;
/*     */     }
/* 265 */     if ((k instanceof COSArray))
/*     */     {
/* 267 */       COSArray array = (COSArray)k;
/* 268 */       int refIndex = array.indexOfObject(refKidBase);
/* 269 */       array.add(refIndex, newKid.getCOSObject());
/*     */     }
/*     */     else
/*     */     {
/* 273 */       boolean onlyKid = k.equals(refKidBase);
/* 274 */       if ((!onlyKid) && ((k instanceof COSObject)))
/*     */       {
/* 276 */         COSBase kObj = ((COSObject)k).getObject();
/* 277 */         onlyKid = kObj.equals(refKidBase);
/*     */       }
/* 279 */       if (onlyKid)
/*     */       {
/* 281 */         COSArray array = new COSArray();
/* 282 */         array.add(newKid);
/* 283 */         array.add(refKidBase);
/* 284 */         getCOSDictionary().setItem(COSName.K, array);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public boolean removeKid(PDStructureElement structureElement)
/*     */   {
/* 297 */     boolean removed = removeObjectableKid(structureElement);
/* 298 */     if (removed)
/*     */     {
/* 300 */       structureElement.setParent(null);
/*     */     }
/* 302 */     return removed;
/*     */   }
/*     */ 
/*     */   protected boolean removeObjectableKid(COSObjectable objectable)
/*     */   {
/* 313 */     if (objectable == null)
/*     */     {
/* 315 */       return false;
/*     */     }
/* 317 */     return removeKid(objectable.getCOSObject());
/*     */   }
/*     */ 
/*     */   protected boolean removeKid(COSBase object)
/*     */   {
/* 328 */     if (object == null)
/*     */     {
/* 330 */       return false;
/*     */     }
/* 332 */     COSBase k = getCOSDictionary().getDictionaryObject(COSName.K);
/* 333 */     if (k == null)
/*     */     {
/* 336 */       return false;
/*     */     }
/* 338 */     if ((k instanceof COSArray))
/*     */     {
/* 341 */       COSArray array = (COSArray)k;
/* 342 */       boolean removed = array.removeObject(object);
/*     */ 
/* 344 */       if (array.size() == 1)
/*     */       {
/* 346 */         getCOSDictionary().setItem(COSName.K, array.getObject(0));
/*     */       }
/* 348 */       return removed;
/*     */     }
/*     */ 
/* 353 */     boolean onlyKid = k.equals(object);
/* 354 */     if ((!onlyKid) && ((k instanceof COSObject)))
/*     */     {
/* 356 */       COSBase kObj = ((COSObject)k).getObject();
/* 357 */       onlyKid = kObj.equals(object);
/*     */     }
/* 359 */     if (onlyKid)
/*     */     {
/* 361 */       getCOSDictionary().setItem(COSName.K, null);
/* 362 */       return true;
/*     */     }
/* 364 */     return false;
/*     */   }
/*     */ 
/*     */   protected Object createObject(COSBase kid)
/*     */   {
/* 384 */     COSDictionary kidDic = null;
/* 385 */     if ((kid instanceof COSDictionary))
/*     */     {
/* 387 */       kidDic = (COSDictionary)kid;
/*     */     }
/* 389 */     else if ((kid instanceof COSObject))
/*     */     {
/* 391 */       COSBase base = ((COSObject)kid).getObject();
/* 392 */       if ((base instanceof COSDictionary))
/*     */       {
/* 394 */         kidDic = (COSDictionary)base;
/*     */       }
/*     */     }
/* 397 */     if (kidDic != null)
/*     */     {
/* 399 */       String type = kidDic.getNameAsString(COSName.TYPE);
/* 400 */       if ((type == null) || ("StructElem".equals(type)))
/*     */       {
/* 404 */         return new PDStructureElement(kidDic);
/*     */       }
/* 406 */       if ("OBJR".equals(type))
/*     */       {
/* 409 */         return new PDObjectReference(kidDic);
/*     */       }
/* 411 */       if ("MCR".equals(type))
/*     */       {
/* 415 */         return new PDMarkedContentReference(kidDic);
/*     */       }
/*     */     }
/* 418 */     else if ((kid instanceof COSInteger))
/*     */     {
/* 422 */       COSInteger mcid = (COSInteger)kid;
/* 423 */       return Integer.valueOf(mcid.intValue());
/*     */     }
/* 425 */     return null;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.pdmodel.documentinterchange.logicalstructure.PDStructureNode
 * JD-Core Version:    0.6.2
 */