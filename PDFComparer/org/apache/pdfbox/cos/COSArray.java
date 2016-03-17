/*     */ package org.apache.pdfbox.cos;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import org.apache.pdfbox.exceptions.COSVisitorException;
/*     */ import org.apache.pdfbox.pdmodel.common.COSObjectable;
/*     */ 
/*     */ public class COSArray extends COSBase
/*     */   implements Iterable<COSBase>
/*     */ {
/*  35 */   private List<COSBase> objects = new ArrayList();
/*     */ 
/*     */   public void add(COSBase object)
/*     */   {
/*  52 */     this.objects.add(object);
/*     */   }
/*     */ 
/*     */   public void add(COSObjectable object)
/*     */   {
/*  62 */     this.objects.add(object.getCOSObject());
/*     */   }
/*     */ 
/*     */   public void add(int i, COSBase object)
/*     */   {
/*  74 */     this.objects.add(i, object);
/*     */   }
/*     */ 
/*     */   public void clear()
/*     */   {
/*  82 */     this.objects.clear();
/*     */   }
/*     */ 
/*     */   public void removeAll(Collection<COSBase> objectsList)
/*     */   {
/*  92 */     this.objects.removeAll(objectsList);
/*     */   }
/*     */ 
/*     */   public void retainAll(Collection<COSBase> objectsList)
/*     */   {
/* 102 */     this.objects.retainAll(objectsList);
/*     */   }
/*     */ 
/*     */   public void addAll(Collection<COSBase> objectsList)
/*     */   {
/* 112 */     this.objects.addAll(objectsList);
/*     */   }
/*     */ 
/*     */   public void addAll(COSArray objectList)
/*     */   {
/* 122 */     if (objectList != null)
/*     */     {
/* 124 */       this.objects.addAll(objectList.objects);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void addAll(int i, Collection<COSBase> objectList)
/*     */   {
/* 137 */     this.objects.addAll(i, objectList);
/*     */   }
/*     */ 
/*     */   public void set(int index, COSBase object)
/*     */   {
/* 148 */     this.objects.set(index, object);
/*     */   }
/*     */ 
/*     */   public void set(int index, int intVal)
/*     */   {
/* 159 */     this.objects.set(index, COSInteger.get(intVal));
/*     */   }
/*     */ 
/*     */   public void set(int index, COSObjectable object)
/*     */   {
/* 170 */     COSBase base = null;
/* 171 */     if (object != null)
/*     */     {
/* 173 */       base = object.getCOSObject();
/*     */     }
/* 175 */     this.objects.set(index, base);
/*     */   }
/*     */ 
/*     */   public COSBase getObject(int index)
/*     */   {
/* 188 */     Object obj = this.objects.get(index);
/* 189 */     if ((obj instanceof COSObject))
/*     */     {
/* 191 */       obj = ((COSObject)obj).getObject();
/*     */     }
/* 193 */     else if ((obj instanceof COSNull))
/*     */     {
/* 195 */       obj = null;
/*     */     }
/* 197 */     return (COSBase)obj;
/*     */   }
/*     */ 
/*     */   public COSBase get(int index)
/*     */   {
/* 210 */     return (COSBase)this.objects.get(index);
/*     */   }
/*     */ 
/*     */   public int getInt(int index)
/*     */   {
/* 222 */     return getInt(index, -1);
/*     */   }
/*     */ 
/*     */   public int getInt(int index, int defaultValue)
/*     */   {
/* 235 */     int retval = defaultValue;
/* 236 */     if (index < size())
/*     */     {
/* 238 */       Object obj = this.objects.get(index);
/* 239 */       if ((obj instanceof COSNumber))
/*     */       {
/* 241 */         retval = ((COSNumber)obj).intValue();
/*     */       }
/*     */     }
/* 244 */     return retval;
/*     */   }
/*     */ 
/*     */   public void setInt(int index, int value)
/*     */   {
/* 255 */     set(index, COSInteger.get(value));
/*     */   }
/*     */ 
/*     */   public void setName(int index, String name)
/*     */   {
/* 265 */     set(index, COSName.getPDFName(name));
/*     */   }
/*     */ 
/*     */   public String getName(int index)
/*     */   {
/* 276 */     return getName(index, null);
/*     */   }
/*     */ 
/*     */   public String getName(int index, String defaultValue)
/*     */   {
/* 287 */     String retval = defaultValue;
/* 288 */     if (index < size())
/*     */     {
/* 290 */       Object obj = this.objects.get(index);
/* 291 */       if ((obj instanceof COSName))
/*     */       {
/* 293 */         retval = ((COSName)obj).getName();
/*     */       }
/*     */     }
/* 296 */     return retval;
/*     */   }
/*     */ 
/*     */   public void setString(int index, String string)
/*     */   {
/* 306 */     if (string != null)
/*     */     {
/* 308 */       set(index, new COSString(string));
/*     */     }
/*     */     else
/*     */     {
/* 312 */       set(index, null);
/*     */     }
/*     */   }
/*     */ 
/*     */   public String getString(int index)
/*     */   {
/* 324 */     return getString(index, null);
/*     */   }
/*     */ 
/*     */   public String getString(int index, String defaultValue)
/*     */   {
/* 335 */     String retval = defaultValue;
/* 336 */     if (index < size())
/*     */     {
/* 338 */       Object obj = this.objects.get(index);
/* 339 */       if ((obj instanceof COSString))
/*     */       {
/* 341 */         retval = ((COSString)obj).getString();
/*     */       }
/*     */     }
/* 344 */     return retval;
/*     */   }
/*     */ 
/*     */   public int size()
/*     */   {
/* 354 */     return this.objects.size();
/*     */   }
/*     */ 
/*     */   public COSBase remove(int i)
/*     */   {
/* 366 */     return (COSBase)this.objects.remove(i);
/*     */   }
/*     */ 
/*     */   public boolean remove(COSBase o)
/*     */   {
/* 379 */     return this.objects.remove(o);
/*     */   }
/*     */ 
/*     */   public boolean removeObject(COSBase o)
/*     */   {
/* 392 */     boolean removed = remove(o);
/* 393 */     if (!removed)
/*     */     {
/* 395 */       for (int i = 0; i < size(); i++)
/*     */       {
/* 397 */         COSBase entry = get(i);
/* 398 */         if ((entry instanceof COSObject))
/*     */         {
/* 400 */           COSObject objEntry = (COSObject)entry;
/* 401 */           if (objEntry.getObject().equals(o))
/*     */           {
/* 403 */             return remove(entry);
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/* 408 */     return removed;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 417 */     return "COSArray{" + this.objects + "}";
/*     */   }
/*     */ 
/*     */   public Iterator<COSBase> iterator()
/*     */   {
/* 427 */     return this.objects.iterator();
/*     */   }
/*     */ 
/*     */   public int indexOf(COSBase object)
/*     */   {
/* 438 */     int retval = -1;
/* 439 */     for (int i = 0; (retval < 0) && (i < size()); i++)
/*     */     {
/* 441 */       if (get(i).equals(object))
/*     */       {
/* 443 */         retval = i;
/*     */       }
/*     */     }
/* 446 */     return retval;
/*     */   }
/*     */ 
/*     */   public int indexOfObject(COSBase object)
/*     */   {
/* 458 */     int retval = -1;
/* 459 */     for (int i = 0; (retval < 0) && (i < size()); i++)
/*     */     {
/* 461 */       COSBase item = get(i);
/* 462 */       if (item.equals(object))
/*     */       {
/* 464 */         retval = i;
/* 465 */         break;
/*     */       }
/* 467 */       if ((item instanceof COSObject))
/*     */       {
/* 469 */         if (((COSObject)item).getObject().equals(object))
/*     */         {
/* 471 */           retval = i;
/* 472 */           break;
/*     */         }
/*     */       }
/*     */     }
/* 476 */     return retval;
/*     */   }
/*     */ 
/*     */   public void growToSize(int size)
/*     */   {
/* 488 */     growToSize(size, null);
/*     */   }
/*     */ 
/*     */   public void growToSize(int size, COSBase object)
/*     */   {
/* 501 */     while (size() < size)
/*     */     {
/* 503 */       add(object);
/*     */     }
/*     */   }
/*     */ 
/*     */   public Object accept(ICOSVisitor visitor)
/*     */     throws COSVisitorException
/*     */   {
/* 517 */     return visitor.visitFromArray(this);
/*     */   }
/*     */ 
/*     */   public float[] toFloatArray()
/*     */   {
/* 527 */     float[] retval = new float[size()];
/* 528 */     for (int i = 0; i < size(); i++)
/*     */     {
/* 530 */       retval[i] = ((COSNumber)getObject(i)).floatValue();
/*     */     }
/* 532 */     return retval;
/*     */   }
/*     */ 
/*     */   public void setFloatArray(float[] value)
/*     */   {
/* 542 */     clear();
/* 543 */     for (int i = 0; i < value.length; i++)
/*     */     {
/* 545 */       add(new COSFloat(value[i]));
/*     */     }
/*     */   }
/*     */ 
/*     */   public List<?> toList()
/*     */   {
/* 556 */     ArrayList retList = new ArrayList(size());
/* 557 */     for (int i = 0; i < size(); i++)
/*     */     {
/* 559 */       retList.add(get(i));
/*     */     }
/* 561 */     return retList;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.cos.COSArray
 * JD-Core Version:    0.6.2
 */