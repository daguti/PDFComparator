/*     */ package com.itextpdf.text.pdf;
/*     */ 
/*     */ import com.itextpdf.text.Document;
/*     */ import com.itextpdf.text.DocumentException;
/*     */ import com.itextpdf.text.ExceptionConverter;
/*     */ import com.itextpdf.text.error_messages.MessageLocalization;
/*     */ import com.itextpdf.text.exceptions.BadPasswordException;
/*     */ import com.itextpdf.text.log.Counter;
/*     */ import com.itextpdf.text.log.CounterFactory;
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStream;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Map.Entry;
/*     */ import java.util.StringTokenizer;
/*     */ 
/*     */ @Deprecated
/*     */ class PdfCopyFieldsImp extends PdfWriter
/*     */ {
/*  67 */   private static final PdfName iTextTag = new PdfName("_iTextTag_");
/*  68 */   private static final Integer zero = Integer.valueOf(0);
/*  69 */   ArrayList<PdfReader> readers = new ArrayList();
/*  70 */   HashMap<PdfReader, IntHashtable> readers2intrefs = new HashMap();
/*  71 */   HashMap<PdfReader, IntHashtable> pages2intrefs = new HashMap();
/*  72 */   HashMap<PdfReader, IntHashtable> visited = new HashMap();
/*  73 */   ArrayList<AcroFields> fields = new ArrayList();
/*     */   RandomAccessFileOrArray file;
/*  75 */   HashMap<String, Object> fieldTree = new HashMap();
/*  76 */   ArrayList<PdfIndirectReference> pageRefs = new ArrayList();
/*  77 */   ArrayList<PdfDictionary> pageDics = new ArrayList();
/*  78 */   PdfDictionary resources = new PdfDictionary();
/*     */   PdfDictionary form;
/*  80 */   boolean closing = false;
/*     */   Document nd;
/*     */   private HashMap<PdfArray, ArrayList<Integer>> tabOrder;
/*  83 */   private ArrayList<String> calculationOrder = new ArrayList();
/*     */   private ArrayList<Object> calculationOrderRefs;
/*     */   private boolean hasSignature;
/*  86 */   private boolean needAppearances = false;
/*  87 */   private HashSet<Object> mergedRadioButtons = new HashSet();
/*     */ 
/*  91 */   protected Counter COUNTER = CounterFactory.getCounter(PdfCopyFields.class);
/*     */ 
/* 663 */   protected static final HashMap<PdfName, Integer> widgetKeys = new HashMap();
/* 664 */   protected static final HashMap<PdfName, Integer> fieldKeys = new HashMap();
/*     */ 
/*     */   protected Counter getCounter()
/*     */   {
/*  93 */     return this.COUNTER;
/*     */   }
/*     */ 
/*     */   PdfCopyFieldsImp(OutputStream os) throws DocumentException {
/*  97 */     this(os, '\000');
/*     */   }
/*     */ 
/*     */   PdfCopyFieldsImp(OutputStream os, char pdfVersion) throws DocumentException {
/* 101 */     super(new PdfDocument(), os);
/* 102 */     this.pdf.addWriter(this);
/* 103 */     if (pdfVersion != 0)
/* 104 */       super.setPdfVersion(pdfVersion);
/* 105 */     this.nd = new Document();
/* 106 */     this.nd.addDocListener(this.pdf);
/*     */   }
/*     */ 
/*     */   void addDocument(PdfReader reader, List<Integer> pagesToKeep) throws DocumentException, IOException {
/* 110 */     if ((!this.readers2intrefs.containsKey(reader)) && (reader.isTampered()))
/* 111 */       throw new DocumentException(MessageLocalization.getComposedMessage("the.document.was.reused", new Object[0]));
/* 112 */     reader = new PdfReader(reader);
/* 113 */     reader.selectPages(pagesToKeep);
/* 114 */     if (reader.getNumberOfPages() == 0)
/* 115 */       return;
/* 116 */     reader.setTampered(false);
/* 117 */     addDocument(reader);
/*     */   }
/*     */ 
/*     */   void addDocument(PdfReader reader) throws DocumentException, IOException {
/* 121 */     if (!reader.isOpenedWithFullPermissions())
/* 122 */       throw new BadPasswordException(MessageLocalization.getComposedMessage("pdfreader.not.opened.with.owner.password", new Object[0]));
/* 123 */     openDoc();
/* 124 */     if (this.readers2intrefs.containsKey(reader)) {
/* 125 */       reader = new PdfReader(reader);
/*     */     }
/*     */     else {
/* 128 */       if (reader.isTampered())
/* 129 */         throw new DocumentException(MessageLocalization.getComposedMessage("the.document.was.reused", new Object[0]));
/* 130 */       reader.consolidateNamedDestinations();
/* 131 */       reader.setTampered(true);
/*     */     }
/* 133 */     reader.shuffleSubsetNames();
/* 134 */     this.readers2intrefs.put(reader, new IntHashtable());
/* 135 */     this.readers.add(reader);
/* 136 */     int len = reader.getNumberOfPages();
/* 137 */     IntHashtable refs = new IntHashtable();
/* 138 */     for (int p = 1; p <= len; p++) {
/* 139 */       refs.put(reader.getPageOrigRef(p).getNumber(), 1);
/* 140 */       reader.releasePage(p);
/*     */     }
/* 142 */     this.pages2intrefs.put(reader, refs);
/* 143 */     this.visited.put(reader, new IntHashtable());
/* 144 */     AcroFields acro = reader.getAcroFields();
/*     */ 
/* 147 */     boolean needapp = !acro.isGenerateAppearances();
/* 148 */     if (needapp)
/* 149 */       this.needAppearances = true;
/* 150 */     this.fields.add(acro);
/* 151 */     updateCalculationOrder(reader);
/*     */   }
/*     */ 
/*     */   private static String getCOName(PdfReader reader, PRIndirectReference ref) {
/* 155 */     String name = "";
/* 156 */     while (ref != null) {
/* 157 */       PdfObject obj = PdfReader.getPdfObject(ref);
/* 158 */       if ((obj == null) || (obj.type() != 6))
/*     */         break;
/* 160 */       PdfDictionary dic = (PdfDictionary)obj;
/* 161 */       PdfString t = dic.getAsString(PdfName.T);
/* 162 */       if (t != null) {
/* 163 */         name = t.toUnicodeString() + "." + name;
/*     */       }
/* 165 */       ref = (PRIndirectReference)dic.get(PdfName.PARENT);
/*     */     }
/* 167 */     if (name.endsWith("."))
/* 168 */       name = name.substring(0, name.length() - 1);
/* 169 */     return name;
/*     */   }
/*     */ 
/*     */   protected void updateCalculationOrder(PdfReader reader)
/*     */   {
/* 176 */     PdfDictionary catalog = reader.getCatalog();
/* 177 */     PdfDictionary acro = catalog.getAsDict(PdfName.ACROFORM);
/* 178 */     if (acro == null)
/* 179 */       return;
/* 180 */     PdfArray co = acro.getAsArray(PdfName.CO);
/* 181 */     if ((co == null) || (co.size() == 0))
/* 182 */       return;
/* 183 */     AcroFields af = reader.getAcroFields();
/* 184 */     for (int k = 0; k < co.size(); k++) {
/* 185 */       PdfObject obj = co.getPdfObject(k);
/* 186 */       if ((obj != null) && (obj.isIndirect()))
/*     */       {
/* 188 */         String name = getCOName(reader, (PRIndirectReference)obj);
/* 189 */         if (af.getFieldItem(name) != null)
/*     */         {
/* 191 */           name = "." + name;
/* 192 */           if (!this.calculationOrder.contains(name))
/*     */           {
/* 194 */             this.calculationOrder.add(name); }  } 
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/* 199 */   void propagate(PdfObject obj, PdfIndirectReference refo, boolean restricted) throws IOException { if (obj == null) {
/* 200 */       return;
/*     */     }
/*     */ 
/* 203 */     if ((obj instanceof PdfIndirectReference))
/*     */       return;
/*     */     PdfDictionary dic;
/*     */     Iterator it;
/* 205 */     switch (obj.type()) {
/*     */     case 6:
/*     */     case 7:
/* 208 */       dic = (PdfDictionary)obj;
/* 209 */       for (PdfName key : dic.getKeys())
/* 210 */         if ((!restricted) || ((!key.equals(PdfName.PARENT)) && (!key.equals(PdfName.KIDS))))
/*     */         {
/* 212 */           PdfObject ob = dic.get(key);
/* 213 */           if ((ob != null) && (ob.isIndirect())) {
/* 214 */             PRIndirectReference ind = (PRIndirectReference)ob;
/* 215 */             if ((!setVisited(ind)) && (!isPage(ind))) {
/* 216 */               PdfIndirectReference ref = getNewReference(ind);
/* 217 */               propagate(PdfReader.getPdfObjectRelease(ind), ref, restricted);
/*     */             }
/*     */           }
/*     */           else {
/* 221 */             propagate(ob, null, restricted);
/*     */           }
/*     */         }
/* 223 */       break;
/*     */     case 5:
/* 227 */       for (it = ((PdfArray)obj).listIterator(); it.hasNext(); ) {
/* 228 */         PdfObject ob = (PdfObject)it.next();
/* 229 */         if ((ob != null) && (ob.isIndirect())) {
/* 230 */           PRIndirectReference ind = (PRIndirectReference)ob;
/* 231 */           if ((!isVisited(ind)) && (!isPage(ind))) {
/* 232 */             PdfIndirectReference ref = getNewReference(ind);
/* 233 */             propagate(PdfReader.getPdfObjectRelease(ind), ref, restricted);
/*     */           }
/*     */         }
/*     */         else {
/* 237 */           propagate(ob, null, restricted);
/*     */         }
/*     */       }
/* 239 */       break;
/*     */     case 10:
/* 242 */       throw new RuntimeException(MessageLocalization.getComposedMessage("reference.pointing.to.reference", new Object[0]));
/*     */     case 8:
/*     */     case 9:
/*     */     } }
/*     */ 
/*     */   private void adjustTabOrder(PdfArray annots, PdfIndirectReference ind, PdfNumber nn) {
/* 248 */     int v = nn.intValue();
/* 249 */     ArrayList t = (ArrayList)this.tabOrder.get(annots);
/* 250 */     if (t == null) {
/* 251 */       t = new ArrayList();
/* 252 */       int size = annots.size() - 1;
/* 253 */       for (int k = 0; k < size; k++) {
/* 254 */         t.add(zero);
/*     */       }
/* 256 */       t.add(Integer.valueOf(v));
/* 257 */       this.tabOrder.put(annots, t);
/* 258 */       annots.add(ind);
/*     */     }
/*     */     else {
/* 261 */       int size = t.size() - 1;
/* 262 */       for (int k = size; k >= 0; k--) {
/* 263 */         if (((Integer)t.get(k)).intValue() <= v) {
/* 264 */           t.add(k + 1, Integer.valueOf(v));
/* 265 */           annots.add(k + 1, ind);
/* 266 */           size = -2;
/* 267 */           break;
/*     */         }
/*     */       }
/* 270 */       if (size != -2) {
/* 271 */         t.add(0, Integer.valueOf(v));
/* 272 */         annots.add(0, ind);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   protected PdfArray branchForm(HashMap<String, Object> level, PdfIndirectReference parent, String fname) throws IOException
/*     */   {
/* 279 */     PdfArray arr = new PdfArray();
/* 280 */     for (Map.Entry entry : level.entrySet()) {
/* 281 */       String name = (String)entry.getKey();
/* 282 */       Object obj = entry.getValue();
/* 283 */       PdfIndirectReference ind = getPdfIndirectReference();
/* 284 */       PdfDictionary dic = new PdfDictionary();
/* 285 */       if (parent != null)
/* 286 */         dic.put(PdfName.PARENT, parent);
/* 287 */       dic.put(PdfName.T, new PdfString(name, "UnicodeBig"));
/* 288 */       String fname2 = fname + "." + name;
/* 289 */       int coidx = this.calculationOrder.indexOf(fname2);
/* 290 */       if (coidx >= 0)
/* 291 */         this.calculationOrderRefs.set(coidx, ind);
/* 292 */       if ((obj instanceof HashMap)) {
/* 293 */         dic.put(PdfName.KIDS, branchForm((HashMap)obj, ind, fname2));
/* 294 */         arr.add(ind);
/* 295 */         addToBody(dic, ind);
/*     */       }
/*     */       else {
/* 298 */         ArrayList list = (ArrayList)obj;
/* 299 */         dic.mergeDifferent((PdfDictionary)list.get(0));
/* 300 */         if (list.size() == 3) {
/* 301 */           dic.mergeDifferent((PdfDictionary)list.get(2));
/* 302 */           int page = ((Integer)list.get(1)).intValue();
/* 303 */           PdfDictionary pageDic = (PdfDictionary)this.pageDics.get(page - 1);
/* 304 */           PdfArray annots = pageDic.getAsArray(PdfName.ANNOTS);
/* 305 */           if (annots == null) {
/* 306 */             annots = new PdfArray();
/* 307 */             pageDic.put(PdfName.ANNOTS, annots);
/*     */           }
/* 309 */           PdfNumber nn = (PdfNumber)dic.get(iTextTag);
/* 310 */           dic.remove(iTextTag);
/* 311 */           adjustTabOrder(annots, ind, nn);
/*     */         }
/*     */         else {
/* 314 */           PdfDictionary field = (PdfDictionary)list.get(0);
/* 315 */           PdfName v = field.getAsName(PdfName.V);
/* 316 */           PdfArray kids = new PdfArray();
/* 317 */           for (int k = 1; k < list.size(); k += 2) {
/* 318 */             int page = ((Integer)list.get(k)).intValue();
/* 319 */             PdfDictionary pageDic = (PdfDictionary)this.pageDics.get(page - 1);
/* 320 */             PdfArray annots = pageDic.getAsArray(PdfName.ANNOTS);
/* 321 */             if (annots == null) {
/* 322 */               annots = new PdfArray();
/* 323 */               pageDic.put(PdfName.ANNOTS, annots);
/*     */             }
/* 325 */             PdfDictionary widget = new PdfDictionary();
/* 326 */             widget.merge((PdfDictionary)list.get(k + 1));
/* 327 */             widget.put(PdfName.PARENT, ind);
/* 328 */             PdfNumber nn = (PdfNumber)widget.get(iTextTag);
/* 329 */             widget.remove(iTextTag);
/* 330 */             if (PdfCopy.isCheckButton(field)) {
/* 331 */               PdfName as = widget.getAsName(PdfName.AS);
/* 332 */               if ((v != null) && (as != null))
/* 333 */                 widget.put(PdfName.AS, v);
/* 334 */             } else if (PdfCopy.isRadioButton(field)) {
/* 335 */               PdfName as = widget.getAsName(PdfName.AS);
/* 336 */               if ((v != null) && (as != null) && (!as.equals(getOffStateName(widget)))) {
/* 337 */                 if (!this.mergedRadioButtons.contains(list)) {
/* 338 */                   this.mergedRadioButtons.add(list);
/* 339 */                   widget.put(PdfName.AS, v);
/*     */                 } else {
/* 341 */                   widget.put(PdfName.AS, getOffStateName(widget));
/*     */                 }
/*     */               }
/*     */             }
/* 345 */             PdfIndirectReference wref = addToBody(widget).getIndirectReference();
/* 346 */             adjustTabOrder(annots, wref, nn);
/* 347 */             kids.add(wref);
/* 348 */             propagate(widget, null, false);
/*     */           }
/* 350 */           dic.put(PdfName.KIDS, kids);
/*     */         }
/* 352 */         arr.add(ind);
/* 353 */         addToBody(dic, ind);
/* 354 */         propagate(dic, null, false);
/*     */       }
/*     */     }
/* 357 */     return arr;
/*     */   }
/*     */ 
/*     */   protected PdfName getOffStateName(PdfDictionary widget) {
/* 361 */     return PdfName.Off;
/*     */   }
/*     */ 
/*     */   protected void createAcroForms() throws IOException {
/* 365 */     if (this.fieldTree.isEmpty())
/* 366 */       return;
/* 367 */     this.form = new PdfDictionary();
/* 368 */     this.form.put(PdfName.DR, this.resources);
/* 369 */     propagate(this.resources, null, false);
/* 370 */     if (this.needAppearances) {
/* 371 */       this.form.put(PdfName.NEEDAPPEARANCES, PdfBoolean.PDFTRUE);
/*     */     }
/* 373 */     this.form.put(PdfName.DA, new PdfString("/Helv 0 Tf 0 g "));
/* 374 */     this.tabOrder = new HashMap();
/* 375 */     this.calculationOrderRefs = new ArrayList(this.calculationOrder);
/* 376 */     this.form.put(PdfName.FIELDS, branchForm(this.fieldTree, null, ""));
/* 377 */     if (this.hasSignature)
/* 378 */       this.form.put(PdfName.SIGFLAGS, new PdfNumber(3));
/* 379 */     PdfArray co = new PdfArray();
/* 380 */     for (int k = 0; k < this.calculationOrderRefs.size(); k++) {
/* 381 */       Object obj = this.calculationOrderRefs.get(k);
/* 382 */       if ((obj instanceof PdfIndirectReference))
/* 383 */         co.add((PdfIndirectReference)obj);
/*     */     }
/* 385 */     if (co.size() > 0)
/* 386 */       this.form.put(PdfName.CO, co);
/*     */   }
/*     */ 
/*     */   public void close()
/*     */   {
/* 391 */     if (this.closing) {
/* 392 */       super.close();
/* 393 */       return;
/*     */     }
/* 395 */     this.closing = true;
/*     */     try {
/* 397 */       closeIt();
/*     */     }
/*     */     catch (Exception e) {
/* 400 */       throw new ExceptionConverter(e);
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void closeIt()
/*     */     throws IOException
/*     */   {
/* 408 */     for (int k = 0; k < this.readers.size(); k++) {
/* 409 */       ((PdfReader)this.readers.get(k)).removeFields();
/*     */     }
/* 411 */     for (int r = 0; r < this.readers.size(); r++) {
/* 412 */       PdfReader reader = (PdfReader)this.readers.get(r);
/* 413 */       for (int page = 1; page <= reader.getNumberOfPages(); page++) {
/* 414 */         this.pageRefs.add(getNewReference(reader.getPageOrigRef(page)));
/* 415 */         this.pageDics.add(reader.getPageN(page));
/*     */       }
/*     */     }
/* 418 */     mergeFields();
/* 419 */     createAcroForms();
/* 420 */     for (int r = 0; r < this.readers.size(); r++) {
/* 421 */       PdfReader reader = (PdfReader)this.readers.get(r);
/* 422 */       for (int page = 1; page <= reader.getNumberOfPages(); page++) {
/* 423 */         PdfDictionary dic = reader.getPageN(page);
/* 424 */         PdfIndirectReference pageRef = getNewReference(reader.getPageOrigRef(page));
/* 425 */         PdfIndirectReference parent = this.root.addPageRef(pageRef);
/* 426 */         dic.put(PdfName.PARENT, parent);
/* 427 */         propagate(dic, pageRef, false);
/*     */       }
/*     */     }
/* 430 */     for (Map.Entry entry : this.readers2intrefs.entrySet()) {
/* 431 */       PdfReader reader = (PdfReader)entry.getKey();
/*     */       try {
/* 433 */         this.file = reader.getSafeFile();
/* 434 */         this.file.reOpen();
/* 435 */         IntHashtable t = (IntHashtable)entry.getValue();
/* 436 */         int[] keys = t.toOrderedKeys();
/* 437 */         for (int k = 0; k < keys.length; k++) {
/* 438 */           PRIndirectReference ref = new PRIndirectReference(reader, keys[k]);
/* 439 */           addToBody(PdfReader.getPdfObjectRelease(ref), t.get(keys[k]));
/*     */         }
/*     */       }
/*     */       finally {
/*     */         try {
/* 444 */           this.file.close();
/*     */         }
/*     */         catch (Exception e)
/*     */         {
/*     */         }
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 453 */     this.pdf.close();
/*     */   }
/*     */ 
/*     */   void addPageOffsetToField(Map<String, AcroFields.Item> fd, int pageOffset) {
/* 457 */     if (pageOffset == 0)
/* 458 */       return;
/* 459 */     for (AcroFields.Item item : fd.values())
/* 460 */       for (int k = 0; k < item.size(); k++) {
/* 461 */         int p = item.getPage(k).intValue();
/* 462 */         item.forcePage(k, p + pageOffset);
/*     */       }
/*     */   }
/*     */ 
/*     */   void createWidgets(ArrayList<Object> list, AcroFields.Item item)
/*     */   {
/* 468 */     for (int k = 0; k < item.size(); k++) {
/* 469 */       list.add(item.getPage(k));
/* 470 */       PdfDictionary merged = item.getMerged(k);
/* 471 */       PdfObject dr = merged.get(PdfName.DR);
/* 472 */       if (dr != null)
/* 473 */         PdfFormField.mergeResources(this.resources, (PdfDictionary)PdfReader.getPdfObject(dr));
/* 474 */       PdfDictionary widget = new PdfDictionary();
/* 475 */       for (Object element : merged.getKeys()) {
/* 476 */         PdfName key = (PdfName)element;
/* 477 */         if (widgetKeys.containsKey(key))
/* 478 */           widget.put(key, merged.get(key));
/*     */       }
/* 480 */       widget.put(iTextTag, new PdfNumber(item.getTabOrder(k).intValue() + 1));
/* 481 */       list.add(widget);
/*     */     }
/*     */   }
/*     */ 
/*     */   void mergeField(String name, AcroFields.Item item)
/*     */   {
/* 487 */     HashMap map = this.fieldTree;
/* 488 */     StringTokenizer tk = new StringTokenizer(name, ".");
/* 489 */     if (!tk.hasMoreTokens())
/* 490 */       return;
/*     */     while (true) {
/* 492 */       String s = tk.nextToken();
/* 493 */       Object obj = map.get(s);
/* 494 */       if (tk.hasMoreTokens()) {
/* 495 */         if (obj == null) {
/* 496 */           obj = new HashMap();
/* 497 */           map.put(s, obj);
/* 498 */           map = (HashMap)obj;
/*     */         }
/* 501 */         else if ((obj instanceof HashMap)) {
/* 502 */           map = (HashMap)obj;
/*     */         }
/*     */       }
/*     */       else
/*     */       {
/* 507 */         if ((obj instanceof HashMap))
/* 508 */           return;
/* 509 */         PdfDictionary merged = item.getMerged(0);
/* 510 */         if (obj == null) {
/* 511 */           PdfDictionary field = new PdfDictionary();
/* 512 */           if (PdfName.SIG.equals(merged.get(PdfName.FT)))
/* 513 */             this.hasSignature = true;
/* 514 */           for (Object element : merged.getKeys()) {
/* 515 */             PdfName key = (PdfName)element;
/* 516 */             if (fieldKeys.containsKey(key))
/* 517 */               field.put(key, merged.get(key));
/*     */           }
/* 519 */           ArrayList list = new ArrayList();
/* 520 */           list.add(field);
/* 521 */           createWidgets(list, item);
/* 522 */           map.put(s, list);
/*     */         }
/*     */         else {
/* 525 */           ArrayList list = (ArrayList)obj;
/* 526 */           PdfDictionary field = (PdfDictionary)list.get(0);
/* 527 */           PdfName type1 = (PdfName)field.get(PdfName.FT);
/* 528 */           PdfName type2 = (PdfName)merged.get(PdfName.FT);
/* 529 */           if ((type1 == null) || (!type1.equals(type2)))
/* 530 */             return;
/* 531 */           int flag1 = 0;
/* 532 */           PdfObject f1 = field.get(PdfName.FF);
/* 533 */           if ((f1 != null) && (f1.isNumber()))
/* 534 */             flag1 = ((PdfNumber)f1).intValue();
/* 535 */           int flag2 = 0;
/* 536 */           PdfObject f2 = merged.get(PdfName.FF);
/* 537 */           if ((f2 != null) && (f2.isNumber()))
/* 538 */             flag2 = ((PdfNumber)f2).intValue();
/* 539 */           if (type1.equals(PdfName.BTN)) {
/* 540 */             if (((flag1 ^ flag2) & 0x10000) != 0) {
/* 541 */               return;
/*     */             }
/* 542 */             if (((flag1 & 0x10000) != 0) || (((flag1 ^ flag2) & 0x8000) == 0));
/*     */           }
/* 545 */           else if ((type1.equals(PdfName.CH)) && 
/* 546 */             (((flag1 ^ flag2) & 0x20000) != 0)) {
/* 547 */             return;
/*     */           }
/* 549 */           createWidgets(list, item);
/*     */         }
/* 551 */         return;
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   void mergeWithMaster(Map<String, AcroFields.Item> fd) {
/* 557 */     for (Map.Entry entry : fd.entrySet()) {
/* 558 */       String name = (String)entry.getKey();
/* 559 */       mergeField(name, (AcroFields.Item)entry.getValue());
/*     */     }
/*     */   }
/*     */ 
/*     */   void mergeFields() {
/* 564 */     int pageOffset = 0;
/* 565 */     for (int k = 0; k < this.fields.size(); k++) {
/* 566 */       Map fd = ((AcroFields)this.fields.get(k)).getFields();
/* 567 */       addPageOffsetToField(fd, pageOffset);
/* 568 */       mergeWithMaster(fd);
/* 569 */       pageOffset += ((PdfReader)this.readers.get(k)).getNumberOfPages();
/*     */     }
/*     */   }
/*     */ 
/*     */   public PdfIndirectReference getPageReference(int page)
/*     */   {
/* 575 */     return (PdfIndirectReference)this.pageRefs.get(page - 1);
/*     */   }
/*     */ 
/*     */   protected PdfDictionary getCatalog(PdfIndirectReference rootObj)
/*     */   {
/*     */     try {
/* 581 */       PdfDictionary cat = this.pdf.getCatalog(rootObj);
/* 582 */       if (this.form != null) {
/* 583 */         PdfIndirectReference ref = addToBody(this.form).getIndirectReference();
/* 584 */         cat.put(PdfName.ACROFORM, ref);
/*     */       }
/* 586 */       return cat;
/*     */     }
/*     */     catch (IOException e) {
/* 589 */       throw new ExceptionConverter(e);
/*     */     }
/*     */   }
/*     */ 
/*     */   protected PdfIndirectReference getNewReference(PRIndirectReference ref) {
/* 594 */     return new PdfIndirectReference(0, getNewObjectNumber(ref.getReader(), ref.getNumber(), 0));
/*     */   }
/*     */ 
/*     */   protected int getNewObjectNumber(PdfReader reader, int number, int generation)
/*     */   {
/* 599 */     IntHashtable refs = (IntHashtable)this.readers2intrefs.get(reader);
/* 600 */     int n = refs.get(number);
/* 601 */     if (n == 0) {
/* 602 */       n = getIndirectReferenceNumber();
/* 603 */       refs.put(number, n);
/*     */     }
/* 605 */     return n;
/*     */   }
/*     */ 
/*     */   protected boolean setVisited(PRIndirectReference ref)
/*     */   {
/* 615 */     IntHashtable refs = (IntHashtable)this.visited.get(ref.getReader());
/* 616 */     if (refs != null) {
/* 617 */       return refs.put(ref.getNumber(), 1) != 0;
/*     */     }
/* 619 */     return false;
/*     */   }
/*     */ 
/*     */   protected boolean isVisited(PRIndirectReference ref)
/*     */   {
/* 628 */     IntHashtable refs = (IntHashtable)this.visited.get(ref.getReader());
/* 629 */     if (refs != null) {
/* 630 */       return refs.containsKey(ref.getNumber());
/*     */     }
/* 632 */     return false;
/*     */   }
/*     */ 
/*     */   protected boolean isVisited(PdfReader reader, int number, int generation) {
/* 636 */     IntHashtable refs = (IntHashtable)this.readers2intrefs.get(reader);
/* 637 */     return refs.containsKey(number);
/*     */   }
/*     */ 
/*     */   protected boolean isPage(PRIndirectReference ref)
/*     */   {
/* 646 */     IntHashtable refs = (IntHashtable)this.pages2intrefs.get(ref.getReader());
/* 647 */     if (refs != null) {
/* 648 */       return refs.containsKey(ref.getNumber());
/*     */     }
/* 650 */     return false;
/*     */   }
/*     */ 
/*     */   RandomAccessFileOrArray getReaderFile(PdfReader reader)
/*     */   {
/* 655 */     return this.file;
/*     */   }
/*     */ 
/*     */   public void openDoc() {
/* 659 */     if (!this.nd.isOpen())
/* 660 */       this.nd.open();
/*     */   }
/*     */ 
/*     */   static
/*     */   {
/* 666 */     Integer one = Integer.valueOf(1);
/* 667 */     widgetKeys.put(PdfName.SUBTYPE, one);
/* 668 */     widgetKeys.put(PdfName.CONTENTS, one);
/* 669 */     widgetKeys.put(PdfName.RECT, one);
/* 670 */     widgetKeys.put(PdfName.NM, one);
/* 671 */     widgetKeys.put(PdfName.M, one);
/* 672 */     widgetKeys.put(PdfName.F, one);
/* 673 */     widgetKeys.put(PdfName.BS, one);
/* 674 */     widgetKeys.put(PdfName.BORDER, one);
/* 675 */     widgetKeys.put(PdfName.AP, one);
/* 676 */     widgetKeys.put(PdfName.AS, one);
/* 677 */     widgetKeys.put(PdfName.C, one);
/* 678 */     widgetKeys.put(PdfName.A, one);
/* 679 */     widgetKeys.put(PdfName.STRUCTPARENT, one);
/* 680 */     widgetKeys.put(PdfName.OC, one);
/* 681 */     widgetKeys.put(PdfName.H, one);
/* 682 */     widgetKeys.put(PdfName.MK, one);
/* 683 */     widgetKeys.put(PdfName.DA, one);
/* 684 */     widgetKeys.put(PdfName.Q, one);
/* 685 */     widgetKeys.put(PdfName.P, one);
/* 686 */     fieldKeys.put(PdfName.AA, one);
/* 687 */     fieldKeys.put(PdfName.FT, one);
/* 688 */     fieldKeys.put(PdfName.TU, one);
/* 689 */     fieldKeys.put(PdfName.TM, one);
/* 690 */     fieldKeys.put(PdfName.FF, one);
/* 691 */     fieldKeys.put(PdfName.V, one);
/* 692 */     fieldKeys.put(PdfName.DV, one);
/* 693 */     fieldKeys.put(PdfName.DS, one);
/* 694 */     fieldKeys.put(PdfName.RV, one);
/* 695 */     fieldKeys.put(PdfName.OPT, one);
/* 696 */     fieldKeys.put(PdfName.MAXLEN, one);
/* 697 */     fieldKeys.put(PdfName.TI, one);
/* 698 */     fieldKeys.put(PdfName.I, one);
/* 699 */     fieldKeys.put(PdfName.LOCK, one);
/* 700 */     fieldKeys.put(PdfName.SV, one);
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.pdf.PdfCopyFieldsImp
 * JD-Core Version:    0.6.2
 */