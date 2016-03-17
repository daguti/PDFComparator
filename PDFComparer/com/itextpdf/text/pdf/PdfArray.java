/*     */ package com.itextpdf.text.pdf;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStream;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.ListIterator;
/*     */ 
/*     */ public class PdfArray extends PdfObject
/*     */   implements Iterable<PdfObject>
/*     */ {
/*     */   protected ArrayList<PdfObject> arrayList;
/*     */ 
/*     */   public PdfArray()
/*     */   {
/*  81 */     super(5);
/*  82 */     this.arrayList = new ArrayList();
/*     */   }
/*     */ 
/*     */   public PdfArray(PdfObject object)
/*     */   {
/*  92 */     super(5);
/*  93 */     this.arrayList = new ArrayList();
/*  94 */     this.arrayList.add(object);
/*     */   }
/*     */ 
/*     */   public PdfArray(float[] values)
/*     */   {
/* 107 */     super(5);
/* 108 */     this.arrayList = new ArrayList();
/* 109 */     add(values);
/*     */   }
/*     */ 
/*     */   public PdfArray(int[] values)
/*     */   {
/* 122 */     super(5);
/* 123 */     this.arrayList = new ArrayList();
/* 124 */     add(values);
/*     */   }
/*     */ 
/*     */   public PdfArray(List<PdfObject> l)
/*     */   {
/* 138 */     this();
/* 139 */     for (PdfObject element : l)
/* 140 */       add(element);
/*     */   }
/*     */ 
/*     */   public PdfArray(PdfArray array)
/*     */   {
/* 150 */     super(5);
/* 151 */     this.arrayList = new ArrayList(array.arrayList);
/*     */   }
/*     */ 
/*     */   public void toPdf(PdfWriter writer, OutputStream os)
/*     */     throws IOException
/*     */   {
/* 165 */     PdfWriter.checkPdfIsoConformance(writer, 11, this);
/* 166 */     os.write(91);
/*     */ 
/* 168 */     Iterator i = this.arrayList.iterator();
/*     */ 
/* 170 */     int type = 0;
/* 171 */     if (i.hasNext()) {
/* 172 */       PdfObject object = (PdfObject)i.next();
/* 173 */       if (object == null)
/* 174 */         object = PdfNull.PDFNULL;
/* 175 */       object.toPdf(writer, os);
/*     */     }
/* 177 */     while (i.hasNext()) {
/* 178 */       PdfObject object = (PdfObject)i.next();
/* 179 */       if (object == null)
/* 180 */         object = PdfNull.PDFNULL;
/* 181 */       type = object.type();
/* 182 */       if ((type != 5) && (type != 6) && (type != 4) && (type != 3))
/* 183 */         os.write(32);
/* 184 */       object.toPdf(writer, os);
/*     */     }
/* 186 */     os.write(93);
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 201 */     return this.arrayList.toString();
/*     */   }
/*     */ 
/*     */   public PdfObject set(int idx, PdfObject obj)
/*     */   {
/* 217 */     return (PdfObject)this.arrayList.set(idx, obj);
/*     */   }
/*     */ 
/*     */   public PdfObject remove(int idx)
/*     */   {
/* 231 */     return (PdfObject)this.arrayList.remove(idx);
/*     */   }
/*     */ 
/*     */   @Deprecated
/*     */   public ArrayList<PdfObject> getArrayList()
/*     */   {
/* 242 */     return this.arrayList;
/*     */   }
/*     */ 
/*     */   public int size()
/*     */   {
/* 251 */     return this.arrayList.size();
/*     */   }
/*     */ 
/*     */   public boolean isEmpty()
/*     */   {
/* 261 */     return this.arrayList.isEmpty();
/*     */   }
/*     */ 
/*     */   public boolean add(PdfObject object)
/*     */   {
/* 273 */     return this.arrayList.add(object);
/*     */   }
/*     */ 
/*     */   public boolean add(float[] values)
/*     */   {
/* 288 */     for (int k = 0; k < values.length; k++)
/* 289 */       this.arrayList.add(new PdfNumber(values[k]));
/* 290 */     return true;
/*     */   }
/*     */ 
/*     */   public boolean add(int[] values)
/*     */   {
/* 304 */     for (int k = 0; k < values.length; k++)
/* 305 */       this.arrayList.add(new PdfNumber(values[k]));
/* 306 */     return true;
/*     */   }
/*     */ 
/*     */   public void add(int index, PdfObject element)
/*     */   {
/* 322 */     this.arrayList.add(index, element);
/*     */   }
/*     */ 
/*     */   public void addFirst(PdfObject object)
/*     */   {
/* 335 */     this.arrayList.add(0, object);
/*     */   }
/*     */ 
/*     */   public boolean contains(PdfObject object)
/*     */   {
/* 346 */     return this.arrayList.contains(object);
/*     */   }
/*     */ 
/*     */   public ListIterator<PdfObject> listIterator()
/*     */   {
/* 355 */     return this.arrayList.listIterator();
/*     */   }
/*     */ 
/*     */   public PdfObject getPdfObject(int idx)
/*     */   {
/* 370 */     return (PdfObject)this.arrayList.get(idx);
/*     */   }
/*     */ 
/*     */   public PdfObject getDirectObject(int idx)
/*     */   {
/* 384 */     return PdfReader.getPdfObject(getPdfObject(idx));
/*     */   }
/*     */ 
/*     */   public PdfDictionary getAsDict(int idx)
/*     */   {
/* 404 */     PdfDictionary dict = null;
/* 405 */     PdfObject orig = getDirectObject(idx);
/* 406 */     if ((orig != null) && (orig.isDictionary()))
/* 407 */       dict = (PdfDictionary)orig;
/* 408 */     return dict;
/*     */   }
/*     */ 
/*     */   public PdfArray getAsArray(int idx)
/*     */   {
/* 425 */     PdfArray array = null;
/* 426 */     PdfObject orig = getDirectObject(idx);
/* 427 */     if ((orig != null) && (orig.isArray()))
/* 428 */       array = (PdfArray)orig;
/* 429 */     return array;
/*     */   }
/*     */ 
/*     */   public PdfStream getAsStream(int idx)
/*     */   {
/* 446 */     PdfStream stream = null;
/* 447 */     PdfObject orig = getDirectObject(idx);
/* 448 */     if ((orig != null) && (orig.isStream()))
/* 449 */       stream = (PdfStream)orig;
/* 450 */     return stream;
/*     */   }
/*     */ 
/*     */   public PdfString getAsString(int idx)
/*     */   {
/* 467 */     PdfString string = null;
/* 468 */     PdfObject orig = getDirectObject(idx);
/* 469 */     if ((orig != null) && (orig.isString()))
/* 470 */       string = (PdfString)orig;
/* 471 */     return string;
/*     */   }
/*     */ 
/*     */   public PdfNumber getAsNumber(int idx)
/*     */   {
/* 488 */     PdfNumber number = null;
/* 489 */     PdfObject orig = getDirectObject(idx);
/* 490 */     if ((orig != null) && (orig.isNumber()))
/* 491 */       number = (PdfNumber)orig;
/* 492 */     return number;
/*     */   }
/*     */ 
/*     */   public PdfName getAsName(int idx)
/*     */   {
/* 509 */     PdfName name = null;
/* 510 */     PdfObject orig = getDirectObject(idx);
/* 511 */     if ((orig != null) && (orig.isName()))
/* 512 */       name = (PdfName)orig;
/* 513 */     return name;
/*     */   }
/*     */ 
/*     */   public PdfBoolean getAsBoolean(int idx)
/*     */   {
/* 530 */     PdfBoolean bool = null;
/* 531 */     PdfObject orig = getDirectObject(idx);
/* 532 */     if ((orig != null) && (orig.isBoolean()))
/* 533 */       bool = (PdfBoolean)orig;
/* 534 */     return bool;
/*     */   }
/*     */ 
/*     */   public PdfIndirectReference getAsIndirectObject(int idx)
/*     */   {
/* 549 */     PdfIndirectReference ref = null;
/* 550 */     PdfObject orig = getPdfObject(idx);
/* 551 */     if ((orig instanceof PdfIndirectReference))
/* 552 */       ref = (PdfIndirectReference)orig;
/* 553 */     return ref;
/*     */   }
/*     */ 
/*     */   public Iterator<PdfObject> iterator()
/*     */   {
/* 560 */     return this.arrayList.iterator();
/*     */   }
/*     */ 
/*     */   public long[] asLongArray()
/*     */   {
/* 569 */     long[] rslt = new long[size()];
/* 570 */     for (int k = 0; k < rslt.length; k++) {
/* 571 */       rslt[k] = getAsNumber(k).longValue();
/*     */     }
/* 573 */     return rslt;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.pdf.PdfArray
 * JD-Core Version:    0.6.2
 */