/*     */ package org.apache.pdfbox.pdmodel.common;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.ListIterator;
/*     */ import org.apache.pdfbox.cos.COSArray;
/*     */ import org.apache.pdfbox.cos.COSBase;
/*     */ import org.apache.pdfbox.cos.COSDictionary;
/*     */ import org.apache.pdfbox.cos.COSFloat;
/*     */ import org.apache.pdfbox.cos.COSInteger;
/*     */ import org.apache.pdfbox.cos.COSName;
/*     */ import org.apache.pdfbox.cos.COSNull;
/*     */ import org.apache.pdfbox.cos.COSNumber;
/*     */ import org.apache.pdfbox.cos.COSObject;
/*     */ import org.apache.pdfbox.cos.COSString;
/*     */ 
/*     */ public class COSArrayList<E>
/*     */   implements List<E>
/*     */ {
/*     */   private COSArray array;
/*     */   private List<E> actual;
/*     */   private COSDictionary parentDict;
/*     */   private COSName dictKey;
/*     */ 
/*     */   public COSArrayList()
/*     */   {
/*  55 */     this.array = new COSArray();
/*  56 */     this.actual = new ArrayList();
/*     */   }
/*     */ 
/*     */   public COSArrayList(List<E> actualList, COSArray cosArray)
/*     */   {
/*  67 */     this.actual = actualList;
/*  68 */     this.array = cosArray;
/*     */   }
/*     */ 
/*     */   public COSArrayList(E actualObject, COSBase item, COSDictionary dictionary, COSName dictionaryKey)
/*     */   {
/*  88 */     this.array = new COSArray();
/*  89 */     this.array.add(item);
/*  90 */     this.actual = new ArrayList();
/*  91 */     this.actual.add(actualObject);
/*     */ 
/*  93 */     this.parentDict = dictionary;
/*  94 */     this.dictKey = dictionaryKey;
/*     */   }
/*     */ 
/*     */   /** @deprecated */
/*     */   public COSArrayList(E actualObject, COSBase item, COSDictionary dictionary, String dictionaryKey)
/*     */   {
/* 102 */     this(actualObject, item, dictionary, COSName.getPDFName(dictionaryKey));
/*     */   }
/*     */ 
/*     */   public int size()
/*     */   {
/* 110 */     return this.actual.size();
/*     */   }
/*     */ 
/*     */   public boolean isEmpty()
/*     */   {
/* 118 */     return this.actual.isEmpty();
/*     */   }
/*     */ 
/*     */   public boolean contains(Object o)
/*     */   {
/* 126 */     return this.actual.contains(o);
/*     */   }
/*     */ 
/*     */   public Iterator<E> iterator()
/*     */   {
/* 134 */     return this.actual.iterator();
/*     */   }
/*     */ 
/*     */   public Object[] toArray()
/*     */   {
/* 142 */     return this.actual.toArray();
/*     */   }
/*     */ 
/*     */   public <X> X[] toArray(X[] a)
/*     */   {
/* 150 */     return this.actual.toArray(a);
/*     */   }
/*     */ 
/*     */   public boolean add(E o)
/*     */   {
/* 161 */     if (this.parentDict != null)
/*     */     {
/* 163 */       this.parentDict.setItem(this.dictKey, this.array);
/*     */ 
/* 166 */       this.parentDict = null;
/*     */     }
/*     */ 
/* 169 */     if ((o instanceof String))
/*     */     {
/* 171 */       this.array.add(new COSString((String)o));
/*     */     }
/* 173 */     else if ((o instanceof DualCOSObjectable))
/*     */     {
/* 175 */       DualCOSObjectable dual = (DualCOSObjectable)o;
/* 176 */       this.array.add(dual.getFirstCOSObject());
/* 177 */       this.array.add(dual.getSecondCOSObject());
/*     */     }
/* 181 */     else if (this.array != null)
/*     */     {
/* 183 */       this.array.add(((COSObjectable)o).getCOSObject());
/*     */     }
/*     */ 
/* 186 */     return this.actual.add(o);
/*     */   }
/*     */ 
/*     */   public boolean remove(Object o)
/*     */   {
/* 194 */     boolean retval = true;
/* 195 */     int index = this.actual.indexOf(o);
/* 196 */     if (index >= 0)
/*     */     {
/* 198 */       this.actual.remove(index);
/* 199 */       this.array.remove(index);
/*     */     }
/*     */     else
/*     */     {
/* 203 */       retval = false;
/*     */     }
/* 205 */     return retval;
/*     */   }
/*     */ 
/*     */   public boolean containsAll(Collection<?> c)
/*     */   {
/* 213 */     return this.actual.containsAll(c);
/*     */   }
/*     */ 
/*     */   public boolean addAll(Collection<? extends E> c)
/*     */   {
/* 223 */     if ((this.parentDict != null) && (c.size() > 0))
/*     */     {
/* 225 */       this.parentDict.setItem(this.dictKey, this.array);
/*     */ 
/* 228 */       this.parentDict = null;
/*     */     }
/* 230 */     this.array.addAll(toCOSObjectList(c));
/* 231 */     return this.actual.addAll(c);
/*     */   }
/*     */ 
/*     */   public boolean addAll(int index, Collection<? extends E> c)
/*     */   {
/* 241 */     if ((this.parentDict != null) && (c.size() > 0))
/*     */     {
/* 243 */       this.parentDict.setItem(this.dictKey, this.array);
/*     */ 
/* 246 */       this.parentDict = null;
/*     */     }
/*     */ 
/* 249 */     if ((c.size() > 0) && ((c.toArray()[0] instanceof DualCOSObjectable)))
/*     */     {
/* 251 */       this.array.addAll(index * 2, toCOSObjectList(c));
/*     */     }
/*     */     else
/*     */     {
/* 255 */       this.array.addAll(index, toCOSObjectList(c));
/*     */     }
/* 257 */     return this.actual.addAll(index, c);
/*     */   }
/*     */ 
/*     */   public static List<Integer> convertIntegerCOSArrayToList(COSArray intArray)
/*     */   {
/* 270 */     List retval = null;
/* 271 */     if (intArray != null)
/*     */     {
/* 273 */       List numbers = new ArrayList();
/* 274 */       for (int i = 0; i < intArray.size(); i++)
/*     */       {
/* 276 */         numbers.add(new Integer(((COSNumber)intArray.get(i)).intValue()));
/*     */       }
/* 278 */       retval = new COSArrayList(numbers, intArray);
/*     */     }
/* 280 */     return retval;
/*     */   }
/*     */ 
/*     */   public static List<Float> convertFloatCOSArrayToList(COSArray floatArray)
/*     */   {
/* 293 */     List retval = null;
/* 294 */     if (floatArray != null)
/*     */     {
/* 296 */       List numbers = new ArrayList();
/* 297 */       for (int i = 0; i < floatArray.size(); i++)
/*     */       {
/*     */         COSNumber num;
/*     */         COSNumber num;
/* 300 */         if ((floatArray.get(i) instanceof COSObject))
/*     */         {
/* 302 */           num = (COSNumber)((COSObject)floatArray.get(i)).getObject();
/*     */         }
/*     */         else
/*     */         {
/* 306 */           num = (COSNumber)floatArray.get(i);
/*     */         }
/* 308 */         numbers.add(Float.valueOf(num.floatValue()));
/*     */       }
/* 310 */       retval = new COSArrayList(numbers, floatArray);
/*     */     }
/* 312 */     return retval;
/*     */   }
/*     */ 
/*     */   public static List<String> convertCOSNameCOSArrayToList(COSArray nameArray)
/*     */   {
/* 325 */     List retval = null;
/* 326 */     if (nameArray != null)
/*     */     {
/* 328 */       List names = new ArrayList();
/* 329 */       for (int i = 0; i < nameArray.size(); i++)
/*     */       {
/* 331 */         names.add(((COSName)nameArray.getObject(i)).getName());
/*     */       }
/* 333 */       retval = new COSArrayList(names, nameArray);
/*     */     }
/* 335 */     return retval;
/*     */   }
/*     */ 
/*     */   public static List<String> convertCOSStringCOSArrayToList(COSArray stringArray)
/*     */   {
/* 348 */     List retval = null;
/* 349 */     if (stringArray != null)
/*     */     {
/* 351 */       List string = new ArrayList();
/* 352 */       for (int i = 0; i < stringArray.size(); i++)
/*     */       {
/* 354 */         string.add(((COSString)stringArray.getObject(i)).getString());
/*     */       }
/* 356 */       retval = new COSArrayList(string, stringArray);
/*     */     }
/* 358 */     return retval;
/*     */   }
/*     */ 
/*     */   public static COSArray convertStringListToCOSNameCOSArray(List<String> strings)
/*     */   {
/* 371 */     COSArray retval = new COSArray();
/* 372 */     for (int i = 0; i < strings.size(); i++)
/*     */     {
/* 374 */       retval.add(COSName.getPDFName((String)strings.get(i)));
/*     */     }
/* 376 */     return retval;
/*     */   }
/*     */ 
/*     */   public static COSArray convertStringListToCOSStringCOSArray(List<String> strings)
/*     */   {
/* 389 */     COSArray retval = new COSArray();
/* 390 */     for (int i = 0; i < strings.size(); i++)
/*     */     {
/* 392 */       retval.add(new COSString((String)strings.get(i)));
/*     */     }
/* 394 */     return retval;
/*     */   }
/*     */ 
/*     */   public static COSArray converterToCOSArray(List<?> cosObjectableList)
/*     */   {
/* 407 */     COSArray array = null;
/* 408 */     if (cosObjectableList != null)
/*     */     {
/* 410 */       if ((cosObjectableList instanceof COSArrayList))
/*     */       {
/* 413 */         array = ((COSArrayList)cosObjectableList).array;
/*     */       }
/*     */       else
/*     */       {
/* 417 */         array = new COSArray();
/* 418 */         Iterator iter = cosObjectableList.iterator();
/* 419 */         while (iter.hasNext())
/*     */         {
/* 421 */           Object next = iter.next();
/* 422 */           if ((next instanceof String))
/*     */           {
/* 424 */             array.add(new COSString((String)next));
/*     */           }
/* 426 */           else if (((next instanceof Integer)) || ((next instanceof Long)))
/*     */           {
/* 428 */             array.add(COSInteger.get(((Number)next).longValue()));
/*     */           }
/* 430 */           else if (((next instanceof Float)) || ((next instanceof Double)))
/*     */           {
/* 432 */             array.add(new COSFloat(((Number)next).floatValue()));
/*     */           }
/* 434 */           else if ((next instanceof COSObjectable))
/*     */           {
/* 436 */             COSObjectable object = (COSObjectable)next;
/* 437 */             array.add(object.getCOSObject());
/*     */           }
/* 439 */           else if ((next instanceof DualCOSObjectable))
/*     */           {
/* 441 */             DualCOSObjectable object = (DualCOSObjectable)next;
/* 442 */             array.add(object.getFirstCOSObject());
/* 443 */             array.add(object.getSecondCOSObject());
/*     */           }
/* 445 */           else if (next == null)
/*     */           {
/* 447 */             array.add(COSNull.NULL);
/*     */           }
/*     */           else
/*     */           {
/* 451 */             throw new RuntimeException("Error: Don't know how to convert type to COSBase '" + next.getClass().getName() + "'");
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/* 457 */     return array;
/*     */   }
/*     */ 
/*     */   private List<COSBase> toCOSObjectList(Collection<?> list)
/*     */   {
/* 462 */     List cosObjects = new ArrayList();
/* 463 */     Iterator iter = list.iterator();
/* 464 */     while (iter.hasNext())
/*     */     {
/* 466 */       Object next = iter.next();
/* 467 */       if ((next instanceof String))
/*     */       {
/* 469 */         cosObjects.add(new COSString((String)next));
/*     */       }
/* 471 */       else if ((next instanceof DualCOSObjectable))
/*     */       {
/* 473 */         DualCOSObjectable object = (DualCOSObjectable)next;
/* 474 */         this.array.add(object.getFirstCOSObject());
/* 475 */         this.array.add(object.getSecondCOSObject());
/*     */       }
/*     */       else
/*     */       {
/* 479 */         COSObjectable cos = (COSObjectable)next;
/* 480 */         cosObjects.add(cos.getCOSObject());
/*     */       }
/*     */     }
/* 483 */     return cosObjects;
/*     */   }
/*     */ 
/*     */   public boolean removeAll(Collection<?> c)
/*     */   {
/* 491 */     this.array.removeAll(toCOSObjectList(c));
/* 492 */     return this.actual.removeAll(c);
/*     */   }
/*     */ 
/*     */   public boolean retainAll(Collection<?> c)
/*     */   {
/* 500 */     this.array.retainAll(toCOSObjectList(c));
/* 501 */     return this.actual.retainAll(c);
/*     */   }
/*     */ 
/*     */   public void clear()
/*     */   {
/* 511 */     if (this.parentDict != null)
/*     */     {
/* 513 */       this.parentDict.setItem(this.dictKey, (COSBase)null);
/*     */     }
/* 515 */     this.actual.clear();
/* 516 */     this.array.clear();
/*     */   }
/*     */ 
/*     */   public boolean equals(Object o)
/*     */   {
/* 524 */     return this.actual.equals(o);
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/* 532 */     return this.actual.hashCode();
/*     */   }
/*     */ 
/*     */   public E get(int index)
/*     */   {
/* 540 */     return this.actual.get(index);
/*     */   }
/*     */ 
/*     */   public E set(int index, E element)
/*     */   {
/* 549 */     if ((element instanceof String))
/*     */     {
/* 551 */       COSString item = new COSString((String)element);
/* 552 */       if ((this.parentDict != null) && (index == 0))
/*     */       {
/* 554 */         this.parentDict.setItem(this.dictKey, item);
/*     */       }
/* 556 */       this.array.set(index, item);
/*     */     }
/* 558 */     else if ((element instanceof DualCOSObjectable))
/*     */     {
/* 560 */       DualCOSObjectable dual = (DualCOSObjectable)element;
/* 561 */       this.array.set(index * 2, dual.getFirstCOSObject());
/* 562 */       this.array.set(index * 2 + 1, dual.getSecondCOSObject());
/*     */     }
/*     */     else
/*     */     {
/* 566 */       if ((this.parentDict != null) && (index == 0))
/*     */       {
/* 568 */         this.parentDict.setItem(this.dictKey, ((COSObjectable)element).getCOSObject());
/*     */       }
/* 570 */       this.array.set(index, ((COSObjectable)element).getCOSObject());
/*     */     }
/* 572 */     return this.actual.set(index, element);
/*     */   }
/*     */ 
/*     */   public void add(int index, E element)
/*     */   {
/* 582 */     if (this.parentDict != null)
/*     */     {
/* 584 */       this.parentDict.setItem(this.dictKey, this.array);
/*     */ 
/* 587 */       this.parentDict = null;
/*     */     }
/* 589 */     this.actual.add(index, element);
/* 590 */     if ((element instanceof String))
/*     */     {
/* 592 */       this.array.add(index, new COSString((String)element));
/*     */     }
/* 594 */     else if ((element instanceof DualCOSObjectable))
/*     */     {
/* 596 */       DualCOSObjectable dual = (DualCOSObjectable)element;
/* 597 */       this.array.add(index * 2, dual.getFirstCOSObject());
/* 598 */       this.array.add(index * 2 + 1, dual.getSecondCOSObject());
/*     */     }
/*     */     else
/*     */     {
/* 602 */       this.array.add(index, ((COSObjectable)element).getCOSObject());
/*     */     }
/*     */   }
/*     */ 
/*     */   public E remove(int index)
/*     */   {
/* 611 */     if ((this.array.size() > index) && ((this.array.get(index) instanceof DualCOSObjectable)))
/*     */     {
/* 614 */       this.array.remove(index);
/* 615 */       this.array.remove(index);
/*     */     }
/*     */     else
/*     */     {
/* 619 */       this.array.remove(index);
/*     */     }
/* 621 */     return this.actual.remove(index);
/*     */   }
/*     */ 
/*     */   public int indexOf(Object o)
/*     */   {
/* 629 */     return this.actual.indexOf(o);
/*     */   }
/*     */ 
/*     */   public int lastIndexOf(Object o)
/*     */   {
/* 637 */     return this.actual.indexOf(o);
/*     */   }
/*     */ 
/*     */   public ListIterator<E> listIterator()
/*     */   {
/* 646 */     return this.actual.listIterator();
/*     */   }
/*     */ 
/*     */   public ListIterator<E> listIterator(int index)
/*     */   {
/* 654 */     return this.actual.listIterator(index);
/*     */   }
/*     */ 
/*     */   public List<E> subList(int fromIndex, int toIndex)
/*     */   {
/* 662 */     return this.actual.subList(fromIndex, toIndex);
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 670 */     return "COSArrayList{" + this.array.toString() + "}";
/*     */   }
/*     */ 
/*     */   public COSArray toList()
/*     */   {
/* 680 */     return this.array;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.pdmodel.common.COSArrayList
 * JD-Core Version:    0.6.2
 */