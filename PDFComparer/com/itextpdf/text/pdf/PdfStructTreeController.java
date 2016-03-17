/*     */ package com.itextpdf.text.pdf;
/*     */ 
/*     */ import com.itextpdf.text.error_messages.MessageLocalization;
/*     */ import java.io.IOException;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map.Entry;
/*     */ 
/*     */ public class PdfStructTreeController
/*     */ {
/*     */   private PdfDictionary structTreeRoot;
/*     */   private PdfCopy writer;
/*     */   private PdfStructureTreeRoot structureTreeRoot;
/*     */   private PdfDictionary parentTree;
/*     */   protected PdfReader reader;
/*  59 */   private PdfDictionary roleMap = null;
/*  60 */   private PdfDictionary sourceRoleMap = null;
/*  61 */   private PdfDictionary sourceClassMap = null;
/*  62 */   private PdfIndirectReference nullReference = null;
/*     */ 
/*     */   protected PdfStructTreeController(PdfReader reader, PdfCopy writer)
/*     */     throws BadPdfFormatException
/*     */   {
/*  68 */     if (!writer.isTagged())
/*  69 */       throw new BadPdfFormatException(MessageLocalization.getComposedMessage("no.structtreeroot.found", new Object[0]));
/*  70 */     this.writer = writer;
/*  71 */     this.structureTreeRoot = writer.getStructureTreeRoot();
/*  72 */     this.structureTreeRoot.put(PdfName.PARENTTREE, new PdfDictionary(PdfName.STRUCTELEM));
/*  73 */     setReader(reader);
/*     */   }
/*     */ 
/*     */   protected void setReader(PdfReader reader) throws BadPdfFormatException {
/*  77 */     this.reader = reader;
/*  78 */     PdfObject obj = reader.getCatalog().get(PdfName.STRUCTTREEROOT);
/*  79 */     obj = getDirectObject(obj);
/*  80 */     if ((obj == null) || (!obj.isDictionary()))
/*  81 */       throw new BadPdfFormatException(MessageLocalization.getComposedMessage("no.structtreeroot.found", new Object[0]));
/*  82 */     this.structTreeRoot = ((PdfDictionary)obj);
/*  83 */     obj = getDirectObject(this.structTreeRoot.get(PdfName.PARENTTREE));
/*  84 */     if ((obj == null) || (!obj.isDictionary()))
/*  85 */       throw new BadPdfFormatException(MessageLocalization.getComposedMessage("the.document.does.not.contain.parenttree", new Object[0]));
/*  86 */     this.parentTree = ((PdfDictionary)obj);
/*  87 */     this.sourceRoleMap = null;
/*  88 */     this.sourceClassMap = null;
/*  89 */     this.nullReference = null;
/*     */   }
/*     */ 
/*     */   public static boolean checkTagged(PdfReader reader) {
/*  93 */     PdfObject obj = reader.getCatalog().get(PdfName.STRUCTTREEROOT);
/*  94 */     obj = getDirectObject(obj);
/*  95 */     if ((obj == null) || (!obj.isDictionary()))
/*  96 */       return false;
/*  97 */     PdfDictionary structTreeRoot = (PdfDictionary)obj;
/*  98 */     obj = getDirectObject(structTreeRoot.get(PdfName.PARENTTREE));
/*  99 */     if ((obj == null) || (!obj.isDictionary()))
/* 100 */       return false;
/* 101 */     return true;
/*     */   }
/*     */ 
/*     */   public static PdfObject getDirectObject(PdfObject object) {
/* 105 */     if (object == null)
/* 106 */       return null;
/* 107 */     while (object.isIndirect())
/* 108 */       object = PdfReader.getPdfObjectRelease(object);
/* 109 */     return object;
/*     */   }
/*     */ 
/*     */   public void copyStructTreeForPage(PdfNumber sourceArrayNumber, int newArrayNumber)
/*     */     throws BadPdfFormatException, IOException
/*     */   {
/* 118 */     if (copyPageMarks(this.parentTree, sourceArrayNumber, newArrayNumber) == returnType.NOTFOUND)
/* 119 */       throw new BadPdfFormatException(MessageLocalization.getComposedMessage("invalid.structparent", new Object[0]));
/*     */   }
/*     */ 
/*     */   private returnType copyPageMarks(PdfDictionary parentTree, PdfNumber arrayNumber, int newArrayNumber) throws BadPdfFormatException, IOException
/*     */   {
/* 124 */     PdfArray pages = (PdfArray)getDirectObject(parentTree.get(PdfName.NUMS));
/* 125 */     if (pages == null) {
/* 126 */       PdfArray kids = (PdfArray)getDirectObject(parentTree.get(PdfName.KIDS));
/* 127 */       if (kids == null)
/* 128 */         return returnType.NOTFOUND;
/* 129 */       int cur = kids.size() / 2;
/* 130 */       int begin = 0;
/*     */       while (true) {
/* 132 */         PdfDictionary kidTree = (PdfDictionary)getDirectObject(kids.getPdfObject(cur + begin));
/* 133 */         switch (1.$SwitchMap$com$itextpdf$text$pdf$PdfStructTreeController$returnType[copyPageMarks(kidTree, arrayNumber, newArrayNumber).ordinal()]) {
/*     */         case 1:
/* 135 */           return returnType.FOUND;
/*     */         case 2:
/* 137 */           begin += cur;
/* 138 */           cur /= 2;
/* 139 */           if (cur == 0)
/* 140 */             cur = 1;
/* 141 */           if (cur + begin == kids.size())
/* 142 */             return returnType.ABOVE;
/*     */           break;
/*     */         case 3:
/* 145 */           if (cur + begin == 0)
/* 146 */             return returnType.BELOW;
/* 147 */           if (cur == 0)
/* 148 */             return returnType.NOTFOUND;
/* 149 */           cur /= 2;
/* 150 */           break;
/*     */         default:
/* 152 */           return returnType.NOTFOUND;
/*     */         }
/*     */       }
/*     */     }
/* 156 */     if (pages.size() == 0)
/* 157 */       return returnType.NOTFOUND;
/* 158 */     return findAndCopyMarks(pages, arrayNumber.intValue(), newArrayNumber);
/*     */   }
/*     */ 
/*     */   private returnType findAndCopyMarks(PdfArray pages, int arrayNumber, int newArrayNumber) throws BadPdfFormatException, IOException
/*     */   {
/* 163 */     if (pages.getAsNumber(0).intValue() > arrayNumber)
/* 164 */       return returnType.BELOW;
/* 165 */     if (pages.getAsNumber(pages.size() - 2).intValue() < arrayNumber)
/* 166 */       return returnType.ABOVE;
/* 167 */     int cur = pages.size() / 4;
/* 168 */     int begin = 0;
/*     */     while (true)
/*     */     {
/* 171 */       int curNumber = pages.getAsNumber((begin + cur) * 2).intValue();
/* 172 */       if (curNumber == arrayNumber) {
/* 173 */         PdfObject obj = pages.getPdfObject((begin + cur) * 2 + 1);
/* 174 */         PdfObject obj1 = obj;
/* 175 */         while (obj.isIndirect()) obj = PdfReader.getPdfObjectRelease(obj);
/* 176 */         if (obj.isArray()) {
/* 177 */           PdfObject firstNotNullKid = null;
/* 178 */           for (PdfObject numObj : (PdfArray)obj) {
/* 179 */             if (numObj.isNull()) {
/* 180 */               if (this.nullReference == null)
/* 181 */                 this.nullReference = this.writer.addToBody(new PdfNull()).getIndirectReference();
/* 182 */               this.structureTreeRoot.setPageMark(newArrayNumber, this.nullReference);
/*     */             } else {
/* 184 */               PdfObject res = this.writer.copyObject(numObj, true, false);
/* 185 */               if (firstNotNullKid == null) firstNotNullKid = res;
/* 186 */               this.structureTreeRoot.setPageMark(newArrayNumber, (PdfIndirectReference)res);
/*     */             }
/*     */           }
/* 189 */           attachStructTreeRootKids(firstNotNullKid);
/* 190 */         } else if (obj.isDictionary()) {
/* 191 */           PdfDictionary k = getKDict((PdfDictionary)obj);
/* 192 */           if (k == null)
/* 193 */             return returnType.NOTFOUND;
/* 194 */           PdfObject res = this.writer.copyObject(obj1, true, false);
/* 195 */           this.structureTreeRoot.setAnnotationMark(newArrayNumber, (PdfIndirectReference)res);
/*     */         } else {
/* 197 */           return returnType.NOTFOUND;
/* 198 */         }return returnType.FOUND;
/*     */       }
/* 200 */       if (curNumber < arrayNumber) {
/* 201 */         begin += cur;
/* 202 */         cur /= 2;
/* 203 */         if (cur == 0)
/* 204 */           cur = 1;
/* 205 */         if (cur + begin == pages.size())
/* 206 */           return returnType.NOTFOUND;
/*     */       }
/*     */       else {
/* 209 */         if (cur + begin == 0)
/* 210 */           return returnType.BELOW;
/* 211 */         if (cur == 0)
/* 212 */           return returnType.NOTFOUND;
/* 213 */         cur /= 2;
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void attachStructTreeRootKids(PdfObject firstNotNullKid)
/*     */     throws IOException, BadPdfFormatException
/*     */   {
/* 221 */     PdfObject structKids = this.structTreeRoot.get(PdfName.K);
/* 222 */     if ((structKids == null) || ((!structKids.isArray()) && (!structKids.isIndirect())))
/*     */     {
/* 224 */       addKid(this.structureTreeRoot, firstNotNullKid);
/*     */     }
/* 226 */     else if (structKids.isIndirect())
/* 227 */       addKid(structKids);
/*     */     else
/* 229 */       for (PdfObject kid : (PdfArray)structKids)
/* 230 */         addKid(kid);
/*     */   }
/*     */ 
/*     */   static PdfDictionary getKDict(PdfDictionary obj)
/*     */   {
/* 236 */     PdfDictionary k = obj.getAsDict(PdfName.K);
/* 237 */     if (k != null) {
/* 238 */       if (PdfName.OBJR.equals(k.getAsName(PdfName.TYPE)))
/* 239 */         return k;
/*     */     }
/*     */     else {
/* 242 */       PdfArray k1 = obj.getAsArray(PdfName.K);
/* 243 */       if (k1 == null)
/* 244 */         return null;
/* 245 */       for (int i = 0; i < k1.size(); i++) {
/* 246 */         k = k1.getAsDict(i);
/* 247 */         if ((k != null) && 
/* 248 */           (PdfName.OBJR.equals(k.getAsName(PdfName.TYPE)))) {
/* 249 */           return k;
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/* 254 */     return null;
/*     */   }
/*     */ 
/*     */   private void addKid(PdfObject obj) throws IOException, BadPdfFormatException {
/* 258 */     if (!obj.isIndirect()) return;
/* 259 */     PRIndirectReference currRef = (PRIndirectReference)obj;
/* 260 */     RefKey key = new RefKey(currRef);
/* 261 */     if (!this.writer.indirects.containsKey(key)) {
/* 262 */       this.writer.copyIndirect(currRef, true, false);
/*     */     }
/* 264 */     PdfIndirectReference newKid = ((PdfCopy.IndirectReferences)this.writer.indirects.get(key)).getRef();
/*     */ 
/* 266 */     if (this.writer.updateRootKids) {
/* 267 */       addKid(this.structureTreeRoot, newKid);
/* 268 */       this.writer.structureTreeRootKidsForReaderImported(this.reader);
/*     */     }
/*     */   }
/*     */ 
/*     */   private static PdfArray getDirectArray(PdfArray in) {
/* 273 */     PdfArray out = new PdfArray();
/* 274 */     for (int i = 0; i < in.size(); i++) {
/* 275 */       PdfObject value = getDirectObject(in.getPdfObject(i));
/* 276 */       if (value != null)
/*     */       {
/* 278 */         if (value.isArray())
/* 279 */           out.add(getDirectArray((PdfArray)value));
/* 280 */         else if (value.isDictionary())
/* 281 */           out.add(getDirectDict((PdfDictionary)value));
/*     */         else
/* 283 */           out.add(value);
/*     */       }
/*     */     }
/* 286 */     return out;
/*     */   }
/*     */ 
/*     */   private static PdfDictionary getDirectDict(PdfDictionary in) {
/* 290 */     PdfDictionary out = new PdfDictionary();
/* 291 */     for (Map.Entry entry : in.hashMap.entrySet()) {
/* 292 */       PdfObject value = getDirectObject((PdfObject)entry.getValue());
/* 293 */       if (value != null)
/*     */       {
/* 295 */         if (value.isArray())
/* 296 */           out.put((PdfName)entry.getKey(), getDirectArray((PdfArray)value));
/* 297 */         else if (value.isDictionary())
/* 298 */           out.put((PdfName)entry.getKey(), getDirectDict((PdfDictionary)value));
/*     */         else
/* 300 */           out.put((PdfName)entry.getKey(), value);
/*     */       }
/*     */     }
/* 303 */     return out;
/*     */   }
/*     */ 
/*     */   public static boolean compareObjects(PdfObject value1, PdfObject value2) {
/* 307 */     value2 = getDirectObject(value2);
/* 308 */     if (value2 == null)
/* 309 */       return false;
/* 310 */     if (value1.type() != value2.type()) {
/* 311 */       return false;
/*     */     }
/* 313 */     if (value1.isBoolean()) {
/* 314 */       if (value1 == value2)
/* 315 */         return true;
/* 316 */       if ((value2 instanceof PdfBoolean)) {
/* 317 */         return ((PdfBoolean)value1).booleanValue() == ((PdfBoolean)value2).booleanValue();
/*     */       }
/* 319 */       return false;
/* 320 */     }if (value1.isName())
/* 321 */       return value1.equals(value2);
/* 322 */     if (value1.isNumber()) {
/* 323 */       if (value1 == value2)
/* 324 */         return true;
/* 325 */       if ((value2 instanceof PdfNumber)) {
/* 326 */         return ((PdfNumber)value1).doubleValue() == ((PdfNumber)value2).doubleValue();
/*     */       }
/* 328 */       return false;
/* 329 */     }if (value1.isNull()) {
/* 330 */       if (value1 == value2)
/* 331 */         return true;
/* 332 */       if ((value2 instanceof PdfNull))
/* 333 */         return true;
/* 334 */       return false;
/* 335 */     }if (value1.isString()) {
/* 336 */       if (value1 == value2)
/* 337 */         return true;
/* 338 */       if ((value2 instanceof PdfString)) {
/* 339 */         return ((((PdfString)value2).value == null) && (((PdfString)value1).value == null)) || ((((PdfString)value1).value != null) && (((PdfString)value1).value.equals(((PdfString)value2).value)));
/*     */       }
/*     */ 
/* 342 */       return false;
/*     */     }
/* 344 */     if (value1.isArray()) {
/* 345 */       PdfArray array1 = (PdfArray)value1;
/* 346 */       PdfArray array2 = (PdfArray)value2;
/* 347 */       if (array1.size() != array2.size())
/* 348 */         return false;
/* 349 */       for (int i = 0; i < array1.size(); i++)
/* 350 */         if (!compareObjects(array1.getPdfObject(i), array2.getPdfObject(i)))
/* 351 */           return false;
/* 352 */       return true;
/*     */     }
/* 354 */     if (value1.isDictionary()) {
/* 355 */       PdfDictionary first = (PdfDictionary)value1;
/* 356 */       PdfDictionary second = (PdfDictionary)value2;
/* 357 */       if (first.size() != second.size())
/* 358 */         return false;
/* 359 */       for (PdfName name : first.hashMap.keySet()) {
/* 360 */         if (!compareObjects(first.get(name), second.get(name)))
/* 361 */           return false;
/*     */       }
/* 363 */       return true;
/*     */     }
/* 365 */     return false;
/*     */   }
/*     */ 
/*     */   protected void addClass(PdfObject object) throws BadPdfFormatException {
/* 369 */     object = getDirectObject(object);
/* 370 */     if (object.isDictionary()) {
/* 371 */       PdfObject curClass = ((PdfDictionary)object).get(PdfName.C);
/* 372 */       if (curClass == null)
/* 373 */         return;
/* 374 */       if (curClass.isArray()) {
/* 375 */         PdfArray array = (PdfArray)curClass;
/* 376 */         for (int i = 0; i < array.size(); i++)
/* 377 */           addClass(array.getPdfObject(i));
/*     */       }
/* 379 */       else if (curClass.isName()) {
/* 380 */         addClass(curClass);
/*     */       } } else if (object.isName()) {
/* 382 */       PdfName name = (PdfName)object;
/* 383 */       if (this.sourceClassMap == null) {
/* 384 */         object = getDirectObject(this.structTreeRoot.get(PdfName.CLASSMAP));
/* 385 */         if ((object == null) || (!object.isDictionary())) {
/* 386 */           return;
/*     */         }
/* 388 */         this.sourceClassMap = ((PdfDictionary)object);
/*     */       }
/* 390 */       object = getDirectObject(this.sourceClassMap.get(name));
/* 391 */       if (object == null) {
/* 392 */         return;
/*     */       }
/* 394 */       PdfObject put = this.structureTreeRoot.getMappedClass(name);
/* 395 */       if (put != null) {
/* 396 */         if (!compareObjects(put, object)) {
/* 397 */           throw new BadPdfFormatException(MessageLocalization.getComposedMessage("conflict.in.classmap", new Object[] { name }));
/*     */         }
/*     */       }
/* 400 */       else if (object.isDictionary())
/* 401 */         this.structureTreeRoot.mapClass(name, getDirectDict((PdfDictionary)object));
/* 402 */       else if (object.isArray())
/* 403 */         this.structureTreeRoot.mapClass(name, getDirectArray((PdfArray)object));
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void addRole(PdfName structType)
/*     */     throws BadPdfFormatException
/*     */   {
/* 410 */     if (structType == null) {
/* 411 */       return;
/*     */     }
/* 413 */     for (PdfName name : this.writer.getStandardStructElems()) {
/* 414 */       if (name.equals(structType))
/* 415 */         return;
/*     */     }
/* 417 */     if (this.sourceRoleMap == null) {
/* 418 */       PdfObject object = getDirectObject(this.structTreeRoot.get(PdfName.ROLEMAP));
/* 419 */       if ((object == null) || (!object.isDictionary())) {
/* 420 */         return;
/*     */       }
/* 422 */       this.sourceRoleMap = ((PdfDictionary)object);
/*     */     }
/* 424 */     PdfObject object = this.sourceRoleMap.get(structType);
/* 425 */     if ((object == null) || (!object.isName())) {
/* 426 */       return;
/*     */     }
/*     */ 
/* 429 */     if (this.roleMap == null) {
/* 430 */       this.roleMap = new PdfDictionary();
/* 431 */       this.structureTreeRoot.put(PdfName.ROLEMAP, this.roleMap);
/* 432 */       this.roleMap.put(structType, object);
/*     */     }
/*     */     else
/*     */     {
/*     */       PdfObject currentRole;
/* 433 */       if ((currentRole = this.roleMap.get(structType)) != null) {
/* 434 */         if (!currentRole.equals(object))
/* 435 */           throw new BadPdfFormatException(MessageLocalization.getComposedMessage("conflict.in.rolemap", new Object[] { object }));
/*     */       }
/*     */       else
/* 438 */         this.roleMap.put(structType, object);
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void addKid(PdfDictionary parent, PdfObject kid) {
/* 443 */     PdfObject kidObj = parent.get(PdfName.K);
/*     */     PdfArray kids;
/*     */     PdfArray kids;
/* 445 */     if ((kidObj instanceof PdfArray)) {
/* 446 */       kids = (PdfArray)kidObj;
/*     */     } else {
/* 448 */       kids = new PdfArray();
/* 449 */       if (kidObj != null)
/* 450 */         kids.add(kidObj);
/*     */     }
/* 452 */     kids.add(kid);
/* 453 */     parent.put(PdfName.K, kids);
/*     */   }
/*     */ 
/*     */   public static enum returnType
/*     */   {
/*  65 */     BELOW, FOUND, ABOVE, NOTFOUND;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.pdf.PdfStructTreeController
 * JD-Core Version:    0.6.2
 */