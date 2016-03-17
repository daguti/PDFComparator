/*     */ package com.itextpdf.text.pdf;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStream;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map.Entry;
/*     */ import java.util.Set;
/*     */ 
/*     */ public class PdfDictionary extends PdfObject
/*     */ {
/*  82 */   public static final PdfName FONT = PdfName.FONT;
/*     */ 
/*  85 */   public static final PdfName OUTLINES = PdfName.OUTLINES;
/*     */ 
/*  88 */   public static final PdfName PAGE = PdfName.PAGE;
/*     */ 
/*  91 */   public static final PdfName PAGES = PdfName.PAGES;
/*     */ 
/*  94 */   public static final PdfName CATALOG = PdfName.CATALOG;
/*     */ 
/*  99 */   private PdfName dictionaryType = null;
/*     */   protected HashMap<PdfName, PdfObject> hashMap;
/*     */ 
/*     */   public PdfDictionary()
/*     */   {
/* 110 */     super(6);
/* 111 */     this.hashMap = new HashMap();
/*     */   }
/*     */ 
/*     */   public PdfDictionary(PdfName type)
/*     */   {
/* 120 */     this();
/* 121 */     this.dictionaryType = type;
/* 122 */     put(PdfName.TYPE, this.dictionaryType);
/*     */   }
/*     */ 
/*     */   public void toPdf(PdfWriter writer, OutputStream os)
/*     */     throws IOException
/*     */   {
/* 137 */     PdfWriter.checkPdfIsoConformance(writer, 11, this);
/* 138 */     os.write(60);
/* 139 */     os.write(60);
/*     */ 
/* 142 */     int type = 0;
/* 143 */     for (Map.Entry e : this.hashMap.entrySet()) {
/* 144 */       ((PdfName)e.getKey()).toPdf(writer, os);
/* 145 */       PdfObject value = (PdfObject)e.getValue();
/* 146 */       type = value.type();
/* 147 */       if ((type != 5) && (type != 6) && (type != 4) && (type != 3))
/* 148 */         os.write(32);
/* 149 */       value.toPdf(writer, os);
/*     */     }
/* 151 */     os.write(62);
/* 152 */     os.write(62);
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 167 */     if (get(PdfName.TYPE) == null)
/* 168 */       return "Dictionary";
/* 169 */     return "Dictionary of type: " + get(PdfName.TYPE);
/*     */   }
/*     */ 
/*     */   public void put(PdfName key, PdfObject object)
/*     */   {
/* 187 */     if ((object == null) || (object.isNull()))
/* 188 */       this.hashMap.remove(key);
/*     */     else
/* 190 */       this.hashMap.put(key, object);
/*     */   }
/*     */ 
/*     */   public void putEx(PdfName key, PdfObject value)
/*     */   {
/* 206 */     if (value == null)
/* 207 */       return;
/* 208 */     put(key, value);
/*     */   }
/*     */ 
/*     */   public void putAll(PdfDictionary dic)
/*     */   {
/* 222 */     this.hashMap.putAll(dic.hashMap);
/*     */   }
/*     */ 
/*     */   public void remove(PdfName key)
/*     */   {
/* 232 */     this.hashMap.remove(key);
/*     */   }
/*     */ 
/*     */   public void clear()
/*     */   {
/* 241 */     this.hashMap.clear();
/*     */   }
/*     */ 
/*     */   public PdfObject get(PdfName key)
/*     */   {
/* 253 */     return (PdfObject)this.hashMap.get(key);
/*     */   }
/*     */ 
/*     */   public PdfObject getDirectObject(PdfName key)
/*     */   {
/* 268 */     return PdfReader.getPdfObject(get(key));
/*     */   }
/*     */ 
/*     */   public Set<PdfName> getKeys()
/*     */   {
/* 277 */     return this.hashMap.keySet();
/*     */   }
/*     */ 
/*     */   public int size()
/*     */   {
/* 288 */     return this.hashMap.size();
/*     */   }
/*     */ 
/*     */   public boolean contains(PdfName key)
/*     */   {
/* 298 */     return this.hashMap.containsKey(key);
/*     */   }
/*     */ 
/*     */   public boolean isFont()
/*     */   {
/* 309 */     return checkType(FONT);
/*     */   }
/*     */ 
/*     */   public boolean isPage()
/*     */   {
/* 318 */     return checkType(PAGE);
/*     */   }
/*     */ 
/*     */   public boolean isPages()
/*     */   {
/* 327 */     return checkType(PAGES);
/*     */   }
/*     */ 
/*     */   public boolean isCatalog()
/*     */   {
/* 336 */     return checkType(CATALOG);
/*     */   }
/*     */ 
/*     */   public boolean isOutlineTree()
/*     */   {
/* 345 */     return checkType(OUTLINES);
/*     */   }
/*     */ 
/*     */   public boolean checkType(PdfName type)
/*     */   {
/* 354 */     if (type == null)
/* 355 */       return false;
/* 356 */     if (this.dictionaryType == null)
/* 357 */       this.dictionaryType = getAsName(PdfName.TYPE);
/* 358 */     return type.equals(this.dictionaryType);
/*     */   }
/*     */ 
/*     */   public void merge(PdfDictionary other)
/*     */   {
/* 364 */     this.hashMap.putAll(other.hashMap);
/*     */   }
/*     */ 
/*     */   public void mergeDifferent(PdfDictionary other) {
/* 368 */     for (PdfName key : other.hashMap.keySet())
/* 369 */       if (!this.hashMap.containsKey(key))
/* 370 */         this.hashMap.put(key, other.hashMap.get(key));
/*     */   }
/*     */ 
/*     */   public PdfDictionary getAsDict(PdfName key)
/*     */   {
/* 391 */     PdfDictionary dict = null;
/* 392 */     PdfObject orig = getDirectObject(key);
/* 393 */     if ((orig != null) && (orig.isDictionary()))
/* 394 */       dict = (PdfDictionary)orig;
/* 395 */     return dict;
/*     */   }
/*     */ 
/*     */   public PdfArray getAsArray(PdfName key)
/*     */   {
/* 412 */     PdfArray array = null;
/* 413 */     PdfObject orig = getDirectObject(key);
/* 414 */     if ((orig != null) && (orig.isArray()))
/* 415 */       array = (PdfArray)orig;
/* 416 */     return array;
/*     */   }
/*     */ 
/*     */   public PdfStream getAsStream(PdfName key)
/*     */   {
/* 433 */     PdfStream stream = null;
/* 434 */     PdfObject orig = getDirectObject(key);
/* 435 */     if ((orig != null) && (orig.isStream()))
/* 436 */       stream = (PdfStream)orig;
/* 437 */     return stream;
/*     */   }
/*     */ 
/*     */   public PdfString getAsString(PdfName key)
/*     */   {
/* 454 */     PdfString string = null;
/* 455 */     PdfObject orig = getDirectObject(key);
/* 456 */     if ((orig != null) && (orig.isString()))
/* 457 */       string = (PdfString)orig;
/* 458 */     return string;
/*     */   }
/*     */ 
/*     */   public PdfNumber getAsNumber(PdfName key)
/*     */   {
/* 475 */     PdfNumber number = null;
/* 476 */     PdfObject orig = getDirectObject(key);
/* 477 */     if ((orig != null) && (orig.isNumber()))
/* 478 */       number = (PdfNumber)orig;
/* 479 */     return number;
/*     */   }
/*     */ 
/*     */   public PdfName getAsName(PdfName key)
/*     */   {
/* 496 */     PdfName name = null;
/* 497 */     PdfObject orig = getDirectObject(key);
/* 498 */     if ((orig != null) && (orig.isName()))
/* 499 */       name = (PdfName)orig;
/* 500 */     return name;
/*     */   }
/*     */ 
/*     */   public PdfBoolean getAsBoolean(PdfName key)
/*     */   {
/* 517 */     PdfBoolean bool = null;
/* 518 */     PdfObject orig = getDirectObject(key);
/* 519 */     if ((orig != null) && (orig.isBoolean()))
/* 520 */       bool = (PdfBoolean)orig;
/* 521 */     return bool;
/*     */   }
/*     */ 
/*     */   public PdfIndirectReference getAsIndirectObject(PdfName key)
/*     */   {
/* 536 */     PdfIndirectReference ref = null;
/* 537 */     PdfObject orig = get(key);
/* 538 */     if ((orig != null) && (orig.isIndirect()))
/* 539 */       ref = (PdfIndirectReference)orig;
/* 540 */     return ref;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.pdf.PdfDictionary
 * JD-Core Version:    0.6.2
 */