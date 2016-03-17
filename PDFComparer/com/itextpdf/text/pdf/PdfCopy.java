/*      */ package com.itextpdf.text.pdf;
/*      */ 
/*      */ import com.itextpdf.text.Document;
/*      */ import com.itextpdf.text.DocumentException;
/*      */ import com.itextpdf.text.ExceptionConverter;
/*      */ import com.itextpdf.text.Rectangle;
/*      */ import com.itextpdf.text.error_messages.MessageLocalization;
/*      */ import com.itextpdf.text.exceptions.BadPasswordException;
/*      */ import com.itextpdf.text.log.Counter;
/*      */ import com.itextpdf.text.log.CounterFactory;
/*      */ import java.io.IOException;
/*      */ import java.io.OutputStream;
/*      */ import java.io.PrintStream;
/*      */ import java.util.ArrayList;
/*      */ import java.util.HashMap;
/*      */ import java.util.HashSet;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.Map.Entry;
/*      */ import java.util.StringTokenizer;
/*      */ import java.util.TreeSet;
/*      */ 
/*      */ public class PdfCopy extends PdfWriter
/*      */ {
/*   91 */   protected static Counter COUNTER = CounterFactory.getCounter(PdfCopy.class);
/*      */   protected HashMap<RefKey, IndirectReferences> indirects;
/*      */   protected HashMap<PdfReader, HashMap<RefKey, IndirectReferences>> indirectMap;
/*      */   protected HashMap<PdfObject, PdfObject> parentObjects;
/*      */   protected HashSet<PdfObject> disableIndirects;
/*      */   protected PdfReader reader;
/*  100 */   protected int[] namePtr = { 0 };
/*      */ 
/*  102 */   private boolean rotateContents = true;
/*      */   protected PdfArray fieldArray;
/*      */   protected HashSet<PdfTemplate> fieldTemplates;
/*  105 */   private PdfStructTreeController structTreeController = null;
/*  106 */   private int currentStructArrayNumber = 0;
/*      */   protected PRIndirectReference structTreeRootReference;
/*      */   protected HashMap<RefKey, PdfIndirectObject> indirectObjects;
/*      */   protected ArrayList<PdfIndirectObject> savedObjects;
/*      */   protected ArrayList<ImportedPage> importedPages;
/*  116 */   protected boolean updateRootKids = false;
/*      */ 
/*  118 */   private static final PdfName annotId = new PdfName("iTextAnnotId");
/*  119 */   private static int annotIdCnt = 0;
/*      */ 
/*  121 */   protected boolean mergeFields = false;
/*  122 */   private boolean needAppearances = false;
/*      */   private boolean hasSignature;
/*      */   private PdfIndirectReference acroForm;
/*      */   private HashMap<PdfArray, ArrayList<Integer>> tabOrder;
/*      */   private ArrayList<Object> calculationOrderRefs;
/*      */   private PdfDictionary resources;
/*      */   protected ArrayList<AcroFields> fields;
/*      */   private ArrayList<String> calculationOrder;
/*      */   private HashMap<String, Object> fieldTree;
/*      */   private HashMap<Integer, PdfIndirectObject> unmergedMap;
/*      */   private HashSet<PdfIndirectObject> unmergedSet;
/*      */   private HashMap<Integer, PdfIndirectObject> mergedMap;
/*      */   private HashSet<PdfIndirectObject> mergedSet;
/*  135 */   private boolean mergeFieldsInternalCall = false;
/*  136 */   private static final PdfName iTextTag = new PdfName("_iTextTag_");
/*  137 */   private static final Integer zero = Integer.valueOf(0);
/*  138 */   private HashSet<Object> mergedRadioButtons = new HashSet();
/*  139 */   private HashMap<Object, PdfObject> mergedTextFields = new HashMap();
/*      */ 
/*  141 */   private HashSet<PdfReader> readersWithImportedStructureTreeRootKids = new HashSet();
/*      */ 
/* 1720 */   protected static final HashSet<PdfName> widgetKeys = new HashSet();
/* 1721 */   protected static final HashSet<PdfName> fieldKeys = new HashSet();
/*      */ 
/*      */   protected Counter getCounter()
/*      */   {
/*   93 */     return COUNTER;
/*      */   }
/*      */ 
/*      */   public PdfCopy(Document document, OutputStream os)
/*      */     throws DocumentException
/*      */   {
/*  175 */     super(new PdfDocument(), os);
/*  176 */     document.addDocListener(this.pdf);
/*  177 */     this.pdf.addWriter(this);
/*  178 */     this.indirectMap = new HashMap();
/*  179 */     this.parentObjects = new HashMap();
/*  180 */     this.disableIndirects = new HashSet();
/*      */ 
/*  182 */     this.indirectObjects = new HashMap();
/*  183 */     this.savedObjects = new ArrayList();
/*  184 */     this.importedPages = new ArrayList();
/*      */   }
/*      */ 
/*      */   public void setPageEvent(PdfPageEvent event)
/*      */   {
/*  194 */     throw new UnsupportedOperationException();
/*      */   }
/*      */ 
/*      */   public boolean isRotateContents()
/*      */   {
/*  202 */     return this.rotateContents;
/*      */   }
/*      */ 
/*      */   public void setRotateContents(boolean rotateContents)
/*      */   {
/*  210 */     this.rotateContents = rotateContents;
/*      */   }
/*      */ 
/*      */   public void setMergeFields() {
/*  214 */     this.mergeFields = true;
/*  215 */     this.resources = new PdfDictionary();
/*  216 */     this.fields = new ArrayList();
/*  217 */     this.calculationOrder = new ArrayList();
/*  218 */     this.fieldTree = new HashMap();
/*  219 */     this.unmergedMap = new HashMap();
/*  220 */     this.unmergedSet = new HashSet();
/*  221 */     this.mergedMap = new HashMap();
/*  222 */     this.mergedSet = new HashSet();
/*      */   }
/*      */ 
/*      */   public PdfImportedPage getImportedPage(PdfReader reader, int pageNumber)
/*      */   {
/*  233 */     if ((this.mergeFields) && (!this.mergeFieldsInternalCall)) {
/*  234 */       throw new IllegalArgumentException(MessageLocalization.getComposedMessage("1.method.cannot.be.used.in.mergeFields.mode.please.use.addDocument", new Object[] { "getImportedPage" }));
/*      */     }
/*  236 */     if (this.mergeFields) {
/*  237 */       ImportedPage newPage = new ImportedPage(reader, pageNumber, this.mergeFields);
/*  238 */       this.importedPages.add(newPage);
/*      */     }
/*  240 */     if (this.structTreeController != null)
/*  241 */       this.structTreeController.reader = null;
/*  242 */     this.disableIndirects.clear();
/*  243 */     this.parentObjects.clear();
/*  244 */     return getImportedPageImpl(reader, pageNumber);
/*      */   }
/*      */ 
/*      */   public PdfImportedPage getImportedPage(PdfReader reader, int pageNumber, boolean keepTaggedPdfStructure) throws BadPdfFormatException {
/*  248 */     if ((this.mergeFields) && (!this.mergeFieldsInternalCall)) {
/*  249 */       throw new IllegalArgumentException(MessageLocalization.getComposedMessage("1.method.cannot.be.used.in.mergeFields.mode.please.use.addDocument", new Object[] { "getImportedPage" }));
/*      */     }
/*  251 */     this.updateRootKids = false;
/*  252 */     if (!keepTaggedPdfStructure) {
/*  253 */       if (this.mergeFields) {
/*  254 */         ImportedPage newPage = new ImportedPage(reader, pageNumber, this.mergeFields);
/*  255 */         this.importedPages.add(newPage);
/*      */       }
/*  257 */       return getImportedPageImpl(reader, pageNumber);
/*      */     }
/*  259 */     if (this.structTreeController != null) {
/*  260 */       if (reader != this.structTreeController.reader)
/*  261 */         this.structTreeController.setReader(reader);
/*      */     }
/*  263 */     else this.structTreeController = new PdfStructTreeController(reader, this);
/*      */ 
/*  265 */     ImportedPage newPage = new ImportedPage(reader, pageNumber, this.mergeFields);
/*  266 */     switch (checkStructureTreeRootKids(newPage)) {
/*      */     case -1:
/*  268 */       clearIndirects(reader);
/*  269 */       this.updateRootKids = true;
/*  270 */       break;
/*      */     case 0:
/*  272 */       this.updateRootKids = false;
/*  273 */       break;
/*      */     case 1:
/*  275 */       this.updateRootKids = true;
/*      */     }
/*      */ 
/*  278 */     this.importedPages.add(newPage);
/*      */ 
/*  280 */     this.disableIndirects.clear();
/*  281 */     this.parentObjects.clear();
/*  282 */     return getImportedPageImpl(reader, pageNumber);
/*      */   }
/*      */ 
/*      */   private void clearIndirects(PdfReader reader) {
/*  286 */     HashMap currIndirects = (HashMap)this.indirectMap.get(reader);
/*  287 */     ArrayList forDelete = new ArrayList();
/*  288 */     for (Map.Entry entry : currIndirects.entrySet()) {
/*  289 */       PdfIndirectReference iRef = ((IndirectReferences)entry.getValue()).theRef;
/*  290 */       RefKey key = new RefKey(iRef);
/*  291 */       PdfIndirectObject iobj = (PdfIndirectObject)this.indirectObjects.get(key);
/*  292 */       if (iobj == null) {
/*  293 */         forDelete.add(entry.getKey());
/*      */       }
/*  295 */       else if ((iobj.object.isArray()) || (iobj.object.isDictionary()) || (iobj.object.isStream())) {
/*  296 */         forDelete.add(entry.getKey());
/*      */       }
/*      */     }
/*      */ 
/*  300 */     for (RefKey key : forDelete)
/*  301 */       currIndirects.remove(key);
/*      */   }
/*      */ 
/*      */   private int checkStructureTreeRootKids(ImportedPage newPage)
/*      */   {
/*  309 */     if (this.importedPages.size() == 0) return 1;
/*  310 */     boolean readerExist = false;
/*  311 */     for (ImportedPage page : this.importedPages) {
/*  312 */       if (page.reader.equals(newPage.reader)) {
/*  313 */         readerExist = true;
/*  314 */         break;
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/*  319 */     if (!readerExist) return 1;
/*      */ 
/*  321 */     ImportedPage lastPage = (ImportedPage)this.importedPages.get(this.importedPages.size() - 1);
/*  322 */     boolean equalReader = lastPage.reader.equals(newPage.reader);
/*      */ 
/*  324 */     if ((equalReader) && (newPage.pageNumber > lastPage.pageNumber)) {
/*  325 */       if (this.readersWithImportedStructureTreeRootKids.contains(newPage.reader)) {
/*  326 */         return 0;
/*      */       }
/*  328 */       return 1;
/*      */     }
/*      */ 
/*  331 */     return -1;
/*      */   }
/*      */ 
/*      */   protected void structureTreeRootKidsForReaderImported(PdfReader reader) {
/*  335 */     this.readersWithImportedStructureTreeRootKids.add(reader);
/*      */   }
/*      */ 
/*      */   protected void fixStructureTreeRoot(HashSet<RefKey> activeKeys, HashSet<PdfName> activeClassMaps) {
/*  339 */     HashMap newClassMap = new HashMap(activeClassMaps.size());
/*  340 */     for (PdfName key : activeClassMaps) {
/*  341 */       PdfObject cm = (PdfObject)this.structureTreeRoot.classes.get(key);
/*  342 */       if (cm != null) newClassMap.put(key, cm);
/*      */     }
/*      */ 
/*  345 */     this.structureTreeRoot.classes = newClassMap;
/*      */ 
/*  347 */     PdfArray kids = this.structureTreeRoot.getAsArray(PdfName.K);
/*  348 */     if (kids != null)
/*  349 */       for (int i = 0; i < kids.size(); i++) {
/*  350 */         PdfIndirectReference iref = (PdfIndirectReference)kids.getPdfObject(i);
/*  351 */         RefKey key = new RefKey(iref);
/*  352 */         if (!activeKeys.contains(key)) kids.remove(i--);
/*      */       }
/*      */   }
/*      */ 
/*      */   protected PdfImportedPage getImportedPageImpl(PdfReader reader, int pageNumber)
/*      */   {
/*  358 */     if (this.currentPdfReaderInstance != null) {
/*  359 */       if (this.currentPdfReaderInstance.getReader() != reader)
/*      */       {
/*  369 */         this.currentPdfReaderInstance = super.getPdfReaderInstance(reader);
/*      */       }
/*      */     }
/*      */     else {
/*  373 */       this.currentPdfReaderInstance = super.getPdfReaderInstance(reader);
/*      */     }
/*      */ 
/*  377 */     return this.currentPdfReaderInstance.getImportedPage(pageNumber);
/*      */   }
/*      */ 
/*      */   protected PdfIndirectReference copyIndirect(PRIndirectReference in, boolean keepStructure, boolean directRootKids)
/*      */     throws IOException, BadPdfFormatException
/*      */   {
/*  391 */     RefKey key = new RefKey(in);
/*  392 */     IndirectReferences iRef = (IndirectReferences)this.indirects.get(key);
/*  393 */     PdfObject obj = PdfReader.getPdfObjectRelease(in);
/*  394 */     if ((keepStructure) && (directRootKids) && 
/*  395 */       ((obj instanceof PdfDictionary))) {
/*  396 */       PdfDictionary dict = (PdfDictionary)obj;
/*  397 */       if (dict.contains(PdfName.PG))
/*  398 */         return null;
/*      */     }
/*      */     PdfIndirectReference theRef;
/*  401 */     if (iRef != null) {
/*  402 */       PdfIndirectReference theRef = iRef.getRef();
/*  403 */       if (iRef.getCopied())
/*  404 */         return theRef;
/*      */     }
/*      */     else
/*      */     {
/*  408 */       theRef = this.body.getPdfIndirectReference();
/*  409 */       iRef = new IndirectReferences(theRef);
/*  410 */       this.indirects.put(key, iRef);
/*      */     }
/*      */ 
/*  413 */     if ((obj != null) && (obj.isDictionary())) {
/*  414 */       PdfObject type = PdfReader.getPdfObjectRelease(((PdfDictionary)obj).get(PdfName.TYPE));
/*  415 */       if ((type != null) && (PdfName.PAGE.equals(type))) {
/*  416 */         return theRef;
/*      */       }
/*      */     }
/*  419 */     iRef.setCopied();
/*  420 */     if (obj != null) this.parentObjects.put(obj, in);
/*  421 */     PdfObject res = copyObject(obj, keepStructure, directRootKids);
/*  422 */     if (this.disableIndirects.contains(obj))
/*  423 */       iRef.setNotCopied();
/*  424 */     if (res != null)
/*      */     {
/*  426 */       addToBody(res, theRef);
/*  427 */       return theRef;
/*      */     }
/*      */ 
/*  430 */     this.indirects.remove(key);
/*  431 */     return null;
/*      */   }
/*      */ 
/*      */   protected PdfIndirectReference copyIndirect(PRIndirectReference in)
/*      */     throws IOException, BadPdfFormatException
/*      */   {
/*  446 */     return copyIndirect(in, false, false);
/*      */   }
/*      */ 
/*      */   protected PdfDictionary copyDictionary(PdfDictionary in, boolean keepStruct, boolean directRootKids)
/*      */     throws IOException, BadPdfFormatException
/*      */   {
/*  455 */     PdfDictionary out = new PdfDictionary();
/*  456 */     PdfObject type = PdfReader.getPdfObjectRelease(in.get(PdfName.TYPE));
/*      */ 
/*  458 */     if (keepStruct)
/*      */     {
/*  460 */       if ((directRootKids) && (in.contains(PdfName.PG)))
/*      */       {
/*  462 */         PdfObject curr = in;
/*  463 */         this.disableIndirects.add(curr);
/*  464 */         while ((this.parentObjects.containsKey(curr)) && (!this.disableIndirects.contains(curr))) {
/*  465 */           curr = (PdfObject)this.parentObjects.get(curr);
/*  466 */           this.disableIndirects.add(curr);
/*      */         }
/*  468 */         return null;
/*      */       }
/*      */ 
/*  471 */       PdfName structType = in.getAsName(PdfName.S);
/*  472 */       this.structTreeController.addRole(structType);
/*  473 */       this.structTreeController.addClass(in);
/*      */     }
/*  475 */     if ((this.structTreeController != null) && (this.structTreeController.reader != null) && ((in.contains(PdfName.STRUCTPARENTS)) || (in.contains(PdfName.STRUCTPARENT)))) {
/*  476 */       PdfName key = PdfName.STRUCTPARENT;
/*  477 */       if (in.contains(PdfName.STRUCTPARENTS)) {
/*  478 */         key = PdfName.STRUCTPARENTS;
/*      */       }
/*  480 */       PdfObject value = in.get(key);
/*  481 */       out.put(key, new PdfNumber(this.currentStructArrayNumber));
/*  482 */       this.structTreeController.copyStructTreeForPage((PdfNumber)value, this.currentStructArrayNumber++);
/*      */     }
/*  484 */     for (Object element : in.getKeys()) {
/*  485 */       PdfName key = (PdfName)element;
/*  486 */       PdfObject value = in.get(key);
/*  487 */       if ((this.structTreeController == null) || (this.structTreeController.reader == null) || ((!key.equals(PdfName.STRUCTPARENTS)) && (!key.equals(PdfName.STRUCTPARENT))))
/*      */       {
/*  490 */         if (PdfName.PAGE.equals(type)) {
/*  491 */           if ((!key.equals(PdfName.B)) && (!key.equals(PdfName.PARENT))) {
/*  492 */             this.parentObjects.put(value, in);
/*  493 */             PdfObject res = copyObject(value, keepStruct, directRootKids);
/*  494 */             if (res != null)
/*  495 */               out.put(key, res);
/*      */           }
/*      */         }
/*      */         else
/*      */         {
/*      */           PdfObject res;
/*      */           PdfObject res;
/*  500 */           if ((this.tagged) && (value.isIndirect()) && (isStructTreeRootReference((PRIndirectReference)value)))
/*  501 */             res = this.structureTreeRoot.getReference();
/*      */           else {
/*  503 */             res = copyObject(value, keepStruct, directRootKids);
/*      */           }
/*  505 */           if (res != null)
/*  506 */             out.put(key, res);
/*      */         }
/*      */       }
/*      */     }
/*  510 */     return out;
/*      */   }
/*      */ 
/*      */   protected PdfDictionary copyDictionary(PdfDictionary in)
/*      */     throws IOException, BadPdfFormatException
/*      */   {
/*  519 */     return copyDictionary(in, false, false);
/*      */   }
/*      */ 
/*      */   protected PdfStream copyStream(PRStream in)
/*      */     throws IOException, BadPdfFormatException
/*      */   {
/*  526 */     PRStream out = new PRStream(in, null);
/*      */ 
/*  528 */     for (Object element : in.getKeys()) {
/*  529 */       PdfName key = (PdfName)element;
/*  530 */       PdfObject value = in.get(key);
/*  531 */       this.parentObjects.put(value, in);
/*  532 */       PdfObject res = copyObject(value);
/*  533 */       if (res != null) {
/*  534 */         out.put(key, res);
/*      */       }
/*      */     }
/*  537 */     return out;
/*      */   }
/*      */ 
/*      */   protected PdfArray copyArray(PdfArray in, boolean keepStruct, boolean directRootKids)
/*      */     throws IOException, BadPdfFormatException
/*      */   {
/*  545 */     PdfArray out = new PdfArray();
/*      */ 
/*  547 */     for (Iterator i = in.listIterator(); i.hasNext(); ) {
/*  548 */       PdfObject value = (PdfObject)i.next();
/*  549 */       this.parentObjects.put(value, in);
/*  550 */       PdfObject res = copyObject(value, keepStruct, directRootKids);
/*  551 */       if (res != null)
/*  552 */         out.add(res);
/*      */     }
/*  554 */     return out;
/*      */   }
/*      */ 
/*      */   protected PdfArray copyArray(PdfArray in)
/*      */     throws IOException, BadPdfFormatException
/*      */   {
/*  562 */     return copyArray(in, false, false);
/*      */   }
/*      */ 
/*      */   protected PdfObject copyObject(PdfObject in, boolean keepStruct, boolean directRootKids)
/*      */     throws IOException, BadPdfFormatException
/*      */   {
/*  569 */     if (in == null)
/*  570 */       return PdfNull.PDFNULL;
/*  571 */     switch (in.type) {
/*      */     case 6:
/*  573 */       return copyDictionary((PdfDictionary)in, keepStruct, directRootKids);
/*      */     case 10:
/*  575 */       if ((!keepStruct) && (!directRootKids))
/*      */       {
/*  577 */         return copyIndirect((PRIndirectReference)in);
/*      */       }
/*  579 */       return copyIndirect((PRIndirectReference)in, keepStruct, directRootKids);
/*      */     case 5:
/*  581 */       return copyArray((PdfArray)in, keepStruct, directRootKids);
/*      */     case 0:
/*      */     case 1:
/*      */     case 2:
/*      */     case 3:
/*      */     case 4:
/*      */     case 8:
/*  588 */       return in;
/*      */     case 7:
/*  590 */       return copyStream((PRStream)in);
/*      */     case 9:
/*      */     }
/*  593 */     if (in.type < 0) {
/*  594 */       String lit = ((PdfLiteral)in).toString();
/*  595 */       if ((lit.equals("true")) || (lit.equals("false"))) {
/*  596 */         return new PdfBoolean(lit);
/*      */       }
/*  598 */       return new PdfLiteral(lit);
/*      */     }
/*  600 */     System.out.println("CANNOT COPY type " + in.type);
/*  601 */     return null;
/*      */   }
/*      */ 
/*      */   protected PdfObject copyObject(PdfObject in)
/*      */     throws IOException, BadPdfFormatException
/*      */   {
/*  609 */     return copyObject(in, false, false);
/*      */   }
/*      */ 
/*      */   protected int setFromIPage(PdfImportedPage iPage)
/*      */   {
/*  616 */     int pageNum = iPage.getPageNumber();
/*  617 */     PdfReaderInstance inst = this.currentPdfReaderInstance = iPage.getPdfReaderInstance();
/*  618 */     this.reader = inst.getReader();
/*  619 */     setFromReader(this.reader);
/*  620 */     return pageNum;
/*      */   }
/*      */ 
/*      */   protected void setFromReader(PdfReader reader)
/*      */   {
/*  627 */     this.reader = reader;
/*  628 */     this.indirects = ((HashMap)this.indirectMap.get(reader));
/*  629 */     if (this.indirects == null) {
/*  630 */       this.indirects = new HashMap();
/*  631 */       this.indirectMap.put(reader, this.indirects);
/*      */     }
/*      */   }
/*      */ 
/*      */   public void addPage(PdfImportedPage iPage)
/*      */     throws IOException, BadPdfFormatException
/*      */   {
/*  640 */     if ((this.mergeFields) && (!this.mergeFieldsInternalCall)) {
/*  641 */       throw new IllegalArgumentException(MessageLocalization.getComposedMessage("1.method.cannot.be.used.in.mergeFields.mode.please.use.addDocument", new Object[] { "addPage" }));
/*      */     }
/*      */ 
/*  644 */     int pageNum = setFromIPage(iPage);
/*  645 */     PdfDictionary thePage = this.reader.getPageN(pageNum);
/*  646 */     PRIndirectReference origRef = this.reader.getPageOrigRef(pageNum);
/*  647 */     this.reader.releasePage(pageNum);
/*  648 */     RefKey key = new RefKey(origRef);
/*      */ 
/*  650 */     IndirectReferences iRef = (IndirectReferences)this.indirects.get(key);
/*  651 */     if ((iRef != null) && (!iRef.getCopied())) {
/*  652 */       this.pageReferences.add(iRef.getRef());
/*  653 */       iRef.setCopied();
/*      */     }
/*  655 */     PdfIndirectReference pageRef = getCurrentPage();
/*  656 */     if (iRef == null) {
/*  657 */       iRef = new IndirectReferences(pageRef);
/*  658 */       this.indirects.put(key, iRef);
/*      */     }
/*  660 */     iRef.setCopied();
/*  661 */     if (this.tagged)
/*  662 */       this.structTreeRootReference = ((PRIndirectReference)this.reader.getCatalog().get(PdfName.STRUCTTREEROOT));
/*  663 */     PdfDictionary newPage = copyDictionary(thePage);
/*  664 */     if (this.mergeFields) {
/*  665 */       ImportedPage importedPage = (ImportedPage)this.importedPages.get(this.importedPages.size() - 1);
/*  666 */       importedPage.annotsIndirectReference = this.body.getPdfIndirectReference();
/*  667 */       newPage.put(PdfName.ANNOTS, importedPage.annotsIndirectReference);
/*      */     }
/*  669 */     this.root.addPage(newPage);
/*  670 */     iPage.setCopied();
/*  671 */     this.currentPageNumber += 1;
/*  672 */     this.structTreeRootReference = null;
/*      */   }
/*      */ 
/*      */   public void addPage(Rectangle rect, int rotation)
/*      */     throws DocumentException
/*      */   {
/*  683 */     if ((this.mergeFields) && (!this.mergeFieldsInternalCall)) {
/*  684 */       throw new IllegalArgumentException(MessageLocalization.getComposedMessage("1.method.cannot.be.used.in.mergeFields.mode.please.use.addDocument", new Object[] { "addPage" }));
/*      */     }
/*  686 */     PdfRectangle mediabox = new PdfRectangle(rect, rotation);
/*  687 */     PageResources resources = new PageResources();
/*  688 */     PdfPage page = new PdfPage(mediabox, new HashMap(), resources.getResources(), 0);
/*  689 */     page.put(PdfName.TABS, getTabs());
/*  690 */     this.root.addPage(page);
/*  691 */     this.currentPageNumber += 1;
/*      */   }
/*      */ 
/*      */   public void addDocument(PdfReader reader, List<Integer> pagesToKeep) throws DocumentException, IOException {
/*  695 */     if (this.indirectMap.containsKey(reader)) {
/*  696 */       throw new IllegalArgumentException(MessageLocalization.getComposedMessage("document.1.has.already.been.added", new Object[] { reader.toString() }));
/*      */     }
/*  698 */     reader.selectPages(pagesToKeep, false);
/*  699 */     addDocument(reader);
/*      */   }
/*      */ 
/*      */   public void copyDocumentFields(PdfReader reader)
/*      */     throws DocumentException, IOException
/*      */   {
/*  709 */     if (!this.document.isOpen()) {
/*  710 */       throw new DocumentException(MessageLocalization.getComposedMessage("the.document.is.not.open.yet.you.can.only.add.meta.information", new Object[0]));
/*      */     }
/*      */ 
/*  713 */     if (this.indirectMap.containsKey(reader)) {
/*  714 */       throw new IllegalArgumentException(MessageLocalization.getComposedMessage("document.1.has.already.been.added", new Object[] { reader.toString() }));
/*      */     }
/*      */ 
/*  717 */     if (!reader.isOpenedWithFullPermissions()) {
/*  718 */       throw new BadPasswordException(MessageLocalization.getComposedMessage("pdfreader.not.opened.with.owner.password", new Object[0]));
/*      */     }
/*  720 */     if (!this.mergeFields) {
/*  721 */       throw new IllegalArgumentException(MessageLocalization.getComposedMessage("1.method.can.be.only.used.in.mergeFields.mode.please.use.addDocument", new Object[] { "copyDocumentFields" }));
/*      */     }
/*  723 */     this.indirects = new HashMap();
/*  724 */     this.indirectMap.put(reader, this.indirects);
/*      */ 
/*  726 */     reader.consolidateNamedDestinations();
/*  727 */     reader.shuffleSubsetNames();
/*  728 */     if ((this.tagged) && (PdfStructTreeController.checkTagged(reader))) {
/*  729 */       this.structTreeRootReference = ((PRIndirectReference)reader.getCatalog().get(PdfName.STRUCTTREEROOT));
/*  730 */       if (this.structTreeController != null) {
/*  731 */         if (reader != this.structTreeController.reader)
/*  732 */           this.structTreeController.setReader(reader);
/*      */       }
/*  734 */       else this.structTreeController = new PdfStructTreeController(reader, this);
/*      */ 
/*      */     }
/*      */ 
/*  738 */     List annotationsToBeCopied = new ArrayList();
/*      */ 
/*  740 */     for (int i = 1; i <= reader.getNumberOfPages(); i++) {
/*  741 */       PdfDictionary page = reader.getPageNRelease(i);
/*  742 */       if ((page != null) && (page.contains(PdfName.ANNOTS))) {
/*  743 */         PdfArray annots = page.getAsArray(PdfName.ANNOTS);
/*  744 */         if ((annots != null) && (annots.size() > 0)) {
/*  745 */           if (this.importedPages.size() < i)
/*  746 */             throw new DocumentException(MessageLocalization.getComposedMessage("there.are.not.enough.imported.pages.for.copied.fields", new Object[0]));
/*  747 */           ((HashMap)this.indirectMap.get(reader)).put(new RefKey(reader.pageRefs.getPageOrigRef(i)), new IndirectReferences((PdfIndirectReference)this.pageReferences.get(i - 1)));
/*  748 */           for (int j = 0; j < annots.size(); j++) {
/*  749 */             PdfDictionary annot = annots.getAsDict(j);
/*  750 */             if (annot != null) {
/*  751 */               annot.put(annotId, new PdfNumber(++annotIdCnt));
/*  752 */               annotationsToBeCopied.add(annots.getPdfObject(j));
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/*  759 */     for (PdfObject annot : annotationsToBeCopied) {
/*  760 */       copyObject(annot);
/*      */     }
/*      */ 
/*  763 */     if ((this.tagged) && (this.structTreeController != null)) {
/*  764 */       this.structTreeController.attachStructTreeRootKids(null);
/*      */     }
/*  766 */     AcroFields acro = reader.getAcroFields();
/*  767 */     boolean needapp = !acro.isGenerateAppearances();
/*  768 */     if (needapp)
/*  769 */       this.needAppearances = true;
/*  770 */     this.fields.add(acro);
/*  771 */     updateCalculationOrder(reader);
/*  772 */     this.structTreeRootReference = null;
/*      */   }
/*      */ 
/*      */   public void addDocument(PdfReader reader) throws DocumentException, IOException {
/*  776 */     if (!this.document.isOpen()) {
/*  777 */       throw new DocumentException(MessageLocalization.getComposedMessage("the.document.is.not.open.yet.you.can.only.add.meta.information", new Object[0]));
/*      */     }
/*  779 */     if (this.indirectMap.containsKey(reader)) {
/*  780 */       throw new IllegalArgumentException(MessageLocalization.getComposedMessage("document.1.has.already.been.added", new Object[] { reader.toString() }));
/*      */     }
/*  782 */     if (!reader.isOpenedWithFullPermissions())
/*  783 */       throw new BadPasswordException(MessageLocalization.getComposedMessage("pdfreader.not.opened.with.owner.password", new Object[0]));
/*  784 */     if (this.mergeFields) {
/*  785 */       reader.consolidateNamedDestinations();
/*  786 */       reader.shuffleSubsetNames();
/*  787 */       for (int i = 1; i <= reader.getNumberOfPages(); i++) {
/*  788 */         PdfDictionary page = reader.getPageNRelease(i);
/*  789 */         if ((page != null) && (page.contains(PdfName.ANNOTS))) {
/*  790 */           PdfArray annots = page.getAsArray(PdfName.ANNOTS);
/*  791 */           if (annots != null) {
/*  792 */             for (int j = 0; j < annots.size(); j++) {
/*  793 */               PdfDictionary annot = annots.getAsDict(j);
/*  794 */               if (annot != null)
/*  795 */                 annot.put(annotId, new PdfNumber(++annotIdCnt));
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*  800 */       AcroFields acro = reader.getAcroFields();
/*      */ 
/*  803 */       boolean needapp = !acro.isGenerateAppearances();
/*  804 */       if (needapp)
/*  805 */         this.needAppearances = true;
/*  806 */       this.fields.add(reader.getAcroFields());
/*  807 */       updateCalculationOrder(reader);
/*      */     }
/*  809 */     boolean tagged = (this.tagged) && (PdfStructTreeController.checkTagged(reader));
/*  810 */     this.mergeFieldsInternalCall = true;
/*  811 */     for (int i = 1; i <= reader.getNumberOfPages(); i++) {
/*  812 */       addPage(getImportedPage(reader, i, tagged));
/*      */     }
/*  814 */     this.mergeFieldsInternalCall = false;
/*      */   }
/*      */ 
/*      */   public PdfIndirectObject addToBody(PdfObject object, PdfIndirectReference ref) throws IOException
/*      */   {
/*  819 */     return addToBody(object, ref, false);
/*      */   }
/*      */ 
/*      */   public PdfIndirectObject addToBody(PdfObject object, PdfIndirectReference ref, boolean formBranching) throws IOException
/*      */   {
/*  824 */     if (formBranching)
/*  825 */       updateReferences(object);
/*      */     PdfIndirectObject iobj;
/*      */     PdfIndirectObject iobj;
/*  828 */     if (((this.tagged) || (this.mergeFields)) && (this.indirectObjects != null) && ((object.isArray()) || (object.isDictionary()) || (object.isStream()) || (object.isNull()))) {
/*  829 */       RefKey key = new RefKey(ref);
/*  830 */       PdfIndirectObject obj = (PdfIndirectObject)this.indirectObjects.get(key);
/*  831 */       if (obj == null) {
/*  832 */         obj = new PdfIndirectObject(ref, object, this);
/*  833 */         this.indirectObjects.put(key, obj);
/*      */       }
/*  835 */       iobj = obj;
/*      */     } else {
/*  837 */       iobj = super.addToBody(object, ref);
/*      */     }
/*  839 */     if ((this.mergeFields) && (object.isDictionary())) {
/*  840 */       PdfNumber annotId = ((PdfDictionary)object).getAsNumber(annotId);
/*  841 */       if (annotId != null) {
/*  842 */         if (formBranching) {
/*  843 */           this.mergedMap.put(Integer.valueOf(annotId.intValue()), iobj);
/*  844 */           this.mergedSet.add(iobj);
/*      */         } else {
/*  846 */           this.unmergedMap.put(Integer.valueOf(annotId.intValue()), iobj);
/*  847 */           this.unmergedSet.add(iobj);
/*      */         }
/*      */       }
/*      */     }
/*  851 */     return iobj;
/*      */   }
/*      */ 
/*      */   protected void cacheObject(PdfIndirectObject iobj)
/*      */   {
/*  856 */     if (((this.tagged) || (this.mergeFields)) && (this.indirectObjects != null)) {
/*  857 */       this.savedObjects.add(iobj);
/*  858 */       RefKey key = new RefKey(iobj.number, iobj.generation);
/*  859 */       if (!this.indirectObjects.containsKey(key)) this.indirectObjects.put(key, iobj); 
/*      */     }
/*      */   }
/*      */ 
/*      */   protected void flushTaggedObjects() throws IOException
/*      */   {
/*      */     try {
/*  866 */       fixTaggedStructure(); } catch (ClassCastException ex) {
/*      */     } finally {
/*  868 */       flushIndirectObjects();
/*      */     }
/*      */   }
/*      */ 
/*      */   protected void flushAcroFields() throws IOException, BadPdfFormatException {
/*  873 */     if (this.mergeFields)
/*      */       try {
/*  875 */         for (PdfReader reader : this.indirectMap.keySet()) {
/*  876 */           reader.removeFields();
/*      */         }
/*  878 */         mergeFields();
/*  879 */         createAcroForms();
/*      */       } catch (ClassCastException ex) {
/*      */       } finally {
/*  882 */         if (!this.tagged)
/*  883 */           flushIndirectObjects();
/*      */       }
/*      */   }
/*      */ 
/*      */   protected void fixTaggedStructure()
/*      */     throws IOException
/*      */   {
/*  890 */     HashMap numTree = this.structureTreeRoot.getNumTree();
/*  891 */     HashSet activeKeys = new HashSet();
/*  892 */     ArrayList actives = new ArrayList();
/*  893 */     int pageRefIndex = 0;
/*      */ 
/*  895 */     if ((this.mergeFields) && (this.acroForm != null)) {
/*  896 */       actives.add(this.acroForm);
/*  897 */       activeKeys.add(new RefKey(this.acroForm));
/*      */     }
/*  899 */     for (PdfIndirectReference page : this.pageReferences) {
/*  900 */       actives.add(page);
/*  901 */       activeKeys.add(new RefKey(page));
/*      */     }
/*      */ 
/*  905 */     for (int i = numTree.size() - 1; i >= 0; i--) {
/*  906 */       PdfIndirectReference currNum = (PdfIndirectReference)numTree.get(Integer.valueOf(i));
/*  907 */       RefKey numKey = new RefKey(currNum);
/*  908 */       PdfObject obj = ((PdfIndirectObject)this.indirectObjects.get(numKey)).object;
/*  909 */       if (obj.isDictionary()) {
/*  910 */         boolean addActiveKeys = false;
/*  911 */         if (this.pageReferences.contains(((PdfDictionary)obj).get(PdfName.PG))) {
/*  912 */           addActiveKeys = true;
/*      */         } else {
/*  914 */           PdfDictionary k = PdfStructTreeController.getKDict((PdfDictionary)obj);
/*  915 */           if ((k != null) && (this.pageReferences.contains(k.get(PdfName.PG)))) {
/*  916 */             addActiveKeys = true;
/*      */           }
/*      */         }
/*  919 */         if (addActiveKeys) {
/*  920 */           activeKeys.add(numKey);
/*  921 */           actives.add(currNum);
/*      */         } else {
/*  923 */           numTree.remove(Integer.valueOf(i));
/*      */         }
/*  925 */       } else if (obj.isArray()) {
/*  926 */         activeKeys.add(numKey);
/*  927 */         actives.add(currNum);
/*  928 */         PdfArray currNums = (PdfArray)obj;
/*  929 */         PdfIndirectReference currPage = (PdfIndirectReference)this.pageReferences.get(pageRefIndex++);
/*  930 */         actives.add(currPage);
/*  931 */         activeKeys.add(new RefKey(currPage));
/*  932 */         PdfIndirectReference prevKid = null;
/*  933 */         for (int j = 0; j < currNums.size(); j++) {
/*  934 */           PdfIndirectReference currKid = (PdfIndirectReference)currNums.getDirectObject(j);
/*  935 */           if (!currKid.equals(prevKid)) {
/*  936 */             RefKey kidKey = new RefKey(currKid);
/*  937 */             activeKeys.add(kidKey);
/*  938 */             actives.add(currKid);
/*      */ 
/*  940 */             PdfIndirectObject iobj = (PdfIndirectObject)this.indirectObjects.get(kidKey);
/*  941 */             if (iobj.object.isDictionary()) {
/*  942 */               PdfDictionary dict = (PdfDictionary)iobj.object;
/*  943 */               PdfIndirectReference pg = (PdfIndirectReference)dict.get(PdfName.PG);
/*      */ 
/*  945 */               if ((pg != null) && (!this.pageReferences.contains(pg)) && (!pg.equals(currPage))) {
/*  946 */                 dict.put(PdfName.PG, currPage);
/*  947 */                 PdfArray kids = dict.getAsArray(PdfName.K);
/*  948 */                 if (kids != null) {
/*  949 */                   PdfObject firstKid = kids.getDirectObject(0);
/*  950 */                   if (firstKid.isNumber()) kids.remove(0);
/*      */                 }
/*      */               }
/*      */             }
/*  954 */             prevKid = currKid;
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*  959 */     HashSet activeClassMaps = new HashSet();
/*      */ 
/*  961 */     findActives(actives, activeKeys, activeClassMaps);
/*      */ 
/*  963 */     ArrayList newRefs = findActiveParents(activeKeys);
/*      */ 
/*  965 */     fixPgKey(newRefs, activeKeys);
/*      */ 
/*  967 */     fixStructureTreeRoot(activeKeys, activeClassMaps);
/*      */ 
/*  969 */     for (Map.Entry entry : this.indirectObjects.entrySet())
/*  970 */       if (!activeKeys.contains(entry.getKey())) {
/*  971 */         entry.setValue(null);
/*      */       }
/*  974 */       else if (((PdfIndirectObject)entry.getValue()).object.isArray()) {
/*  975 */         removeInactiveReferences((PdfArray)((PdfIndirectObject)entry.getValue()).object, activeKeys);
/*  976 */       } else if (((PdfIndirectObject)entry.getValue()).object.isDictionary()) {
/*  977 */         PdfObject kids = ((PdfDictionary)((PdfIndirectObject)entry.getValue()).object).get(PdfName.K);
/*  978 */         if ((kids != null) && (kids.isArray()))
/*  979 */           removeInactiveReferences((PdfArray)kids, activeKeys);
/*      */       }
/*      */   }
/*      */ 
/*      */   private void removeInactiveReferences(PdfArray array, HashSet<RefKey> activeKeys)
/*      */   {
/*  986 */     for (int i = 0; i < array.size(); i++) {
/*  987 */       PdfObject obj = array.getPdfObject(i);
/*  988 */       if (((obj.type() == 0) && (!activeKeys.contains(new RefKey((PdfIndirectReference)obj)))) || ((obj.isDictionary()) && (containsInactivePg((PdfDictionary)obj, activeKeys))))
/*      */       {
/*  990 */         array.remove(i--);
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*  995 */   private boolean containsInactivePg(PdfDictionary dict, HashSet<RefKey> activeKeys) { PdfObject pg = dict.get(PdfName.PG);
/*  996 */     if ((pg != null) && (!activeKeys.contains(new RefKey((PdfIndirectReference)pg))))
/*  997 */       return true;
/*  998 */     return false;
/*      */   }
/*      */ 
/*      */   private ArrayList<PdfIndirectReference> findActiveParents(HashSet<RefKey> activeKeys)
/*      */   {
/* 1003 */     ArrayList newRefs = new ArrayList();
/* 1004 */     ArrayList tmpActiveKeys = new ArrayList(activeKeys);
/* 1005 */     for (int i = 0; i < tmpActiveKeys.size(); i++) {
/* 1006 */       PdfIndirectObject iobj = (PdfIndirectObject)this.indirectObjects.get(tmpActiveKeys.get(i));
/* 1007 */       if ((iobj != null) && (iobj.object.isDictionary())) {
/* 1008 */         PdfObject parent = ((PdfDictionary)iobj.object).get(PdfName.P);
/* 1009 */         if ((parent != null) && (parent.type() == 0)) {
/* 1010 */           RefKey key = new RefKey((PdfIndirectReference)parent);
/* 1011 */           if (!activeKeys.contains(key)) {
/* 1012 */             activeKeys.add(key);
/* 1013 */             tmpActiveKeys.add(key);
/* 1014 */             newRefs.add((PdfIndirectReference)parent);
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/* 1018 */     return newRefs;
/*      */   }
/*      */ 
/*      */   private void fixPgKey(ArrayList<PdfIndirectReference> newRefs, HashSet<RefKey> activeKeys) {
/* 1022 */     for (PdfIndirectReference iref : newRefs) {
/* 1023 */       PdfIndirectObject iobj = (PdfIndirectObject)this.indirectObjects.get(new RefKey(iref));
/* 1024 */       if ((iobj != null) && (iobj.object.isDictionary())) {
/* 1025 */         PdfDictionary dict = (PdfDictionary)iobj.object;
/* 1026 */         PdfObject pg = dict.get(PdfName.PG);
/* 1027 */         if ((pg != null) && (!activeKeys.contains(new RefKey((PdfIndirectReference)pg)))) {
/* 1028 */           PdfArray kids = dict.getAsArray(PdfName.K);
/* 1029 */           if (kids != null)
/* 1030 */             for (int i = 0; i < kids.size(); i++) {
/* 1031 */               PdfObject obj = kids.getPdfObject(i);
/* 1032 */               if (obj.type() != 0) {
/* 1033 */                 kids.remove(i--);
/*      */               } else {
/* 1035 */                 PdfIndirectObject kid = (PdfIndirectObject)this.indirectObjects.get(new RefKey((PdfIndirectReference)obj));
/* 1036 */                 if ((kid != null) && (kid.object.isDictionary())) {
/* 1037 */                   PdfObject kidPg = ((PdfDictionary)kid.object).get(PdfName.PG);
/* 1038 */                   if ((kidPg != null) && (activeKeys.contains(new RefKey((PdfIndirectReference)kidPg)))) {
/* 1039 */                     dict.put(PdfName.PG, kidPg);
/* 1040 */                     break;
/*      */                   }
/*      */                 }
/*      */               }
/*      */             }
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/* 1050 */   private void findActives(ArrayList<PdfIndirectReference> actives, HashSet<RefKey> activeKeys, HashSet<PdfName> activeClassMaps) { for (int i = 0; i < actives.size(); i++) {
/* 1051 */       RefKey key = new RefKey((PdfIndirectReference)actives.get(i));
/* 1052 */       PdfIndirectObject iobj = (PdfIndirectObject)this.indirectObjects.get(key);
/* 1053 */       if ((iobj != null) && (iobj.object != null))
/* 1054 */         switch (iobj.object.type()) {
/*      */         case 0:
/* 1056 */           findActivesFromReference((PdfIndirectReference)iobj.object, actives, activeKeys);
/* 1057 */           break;
/*      */         case 5:
/* 1059 */           findActivesFromArray((PdfArray)iobj.object, actives, activeKeys, activeClassMaps);
/* 1060 */           break;
/*      */         case 6:
/*      */         case 7:
/* 1063 */           findActivesFromDict((PdfDictionary)iobj.object, actives, activeKeys, activeClassMaps);
/*      */         case 1:
/*      */         case 2:
/*      */         case 3:
/*      */         case 4:
/*      */         } 
/*      */     } } 
/* 1070 */   private void findActivesFromReference(PdfIndirectReference iref, ArrayList<PdfIndirectReference> actives, HashSet<RefKey> activeKeys) { RefKey key = new RefKey(iref);
/* 1071 */     PdfIndirectObject iobj = (PdfIndirectObject)this.indirectObjects.get(key);
/* 1072 */     if ((iobj != null) && (iobj.object.isDictionary()) && (containsInactivePg((PdfDictionary)iobj.object, activeKeys))) return;
/*      */ 
/* 1074 */     if (!activeKeys.contains(key)) {
/* 1075 */       activeKeys.add(key);
/* 1076 */       actives.add(iref);
/*      */     } }
/*      */ 
/*      */   private void findActivesFromArray(PdfArray array, ArrayList<PdfIndirectReference> actives, HashSet<RefKey> activeKeys, HashSet<PdfName> activeClassMaps)
/*      */   {
/* 1081 */     for (PdfObject obj : array)
/* 1082 */       switch (obj.type()) {
/*      */       case 0:
/* 1084 */         findActivesFromReference((PdfIndirectReference)obj, actives, activeKeys);
/* 1085 */         break;
/*      */       case 5:
/* 1087 */         findActivesFromArray((PdfArray)obj, actives, activeKeys, activeClassMaps);
/* 1088 */         break;
/*      */       case 6:
/*      */       case 7:
/* 1091 */         findActivesFromDict((PdfDictionary)obj, actives, activeKeys, activeClassMaps);
/*      */       case 1:
/*      */       case 2:
/*      */       case 3:
/*      */       case 4: }  
/*      */   }
/*      */ 
/* 1098 */   private void findActivesFromDict(PdfDictionary dict, ArrayList<PdfIndirectReference> actives, HashSet<RefKey> activeKeys, HashSet<PdfName> activeClassMaps) { if (containsInactivePg(dict, activeKeys)) return;
/* 1099 */     for (PdfName key : dict.getKeys()) {
/* 1100 */       PdfObject obj = dict.get(key);
/* 1101 */       if (!key.equals(PdfName.P))
/* 1102 */         if (key.equals(PdfName.C)) {
/* 1103 */           if (obj.isArray()) {
/* 1104 */             for (PdfObject cm : (PdfArray)obj) {
/* 1105 */               if (cm.isName()) activeClassMaps.add((PdfName)cm);
/*      */             }
/*      */           }
/* 1108 */           else if (obj.isName()) activeClassMaps.add((PdfName)obj);
/*      */         }
/*      */         else
/* 1111 */           switch (obj.type()) {
/*      */           case 0:
/* 1113 */             findActivesFromReference((PdfIndirectReference)obj, actives, activeKeys);
/* 1114 */             break;
/*      */           case 5:
/* 1116 */             findActivesFromArray((PdfArray)obj, actives, activeKeys, activeClassMaps);
/* 1117 */             break;
/*      */           case 6:
/*      */           case 7:
/* 1120 */             findActivesFromDict((PdfDictionary)obj, actives, activeKeys, activeClassMaps);
/*      */           case 1:
/*      */           case 2:
/*      */           case 3:
/*      */           case 4:
/*      */           } 
/*      */     } } 
/* 1127 */   protected void flushIndirectObjects() throws IOException { for (PdfIndirectObject iobj : this.savedObjects)
/* 1128 */       this.indirectObjects.remove(new RefKey(iobj.number, iobj.generation));
/* 1129 */     HashSet inactives = new HashSet();
/* 1130 */     for (Map.Entry entry : this.indirectObjects.entrySet()) {
/* 1131 */       if (entry.getValue() != null) writeObjectToBody((PdfIndirectObject)entry.getValue()); else
/* 1132 */         inactives.add(entry.getKey());
/*      */     }
/* 1134 */     ArrayList pdfCrossReferences = new ArrayList(this.body.xrefs);
/* 1135 */     for (PdfWriter.PdfBody.PdfCrossReference cr : pdfCrossReferences) {
/* 1136 */       RefKey key = new RefKey(cr.getRefnum(), 0);
/* 1137 */       if (inactives.contains(key)) this.body.xrefs.remove(cr);
/*      */     }
/* 1139 */     this.indirectObjects = null; }
/*      */ 
/*      */   private void writeObjectToBody(PdfIndirectObject object) throws IOException
/*      */   {
/* 1143 */     boolean skipWriting = false;
/* 1144 */     if (this.mergeFields) {
/* 1145 */       updateAnnotationReferences(object.object);
/* 1146 */       if ((object.object.isDictionary()) || (object.object.isStream())) {
/* 1147 */         PdfDictionary dictionary = (PdfDictionary)object.object;
/* 1148 */         if (this.unmergedSet.contains(object)) {
/* 1149 */           PdfNumber annotId = dictionary.getAsNumber(annotId);
/* 1150 */           if ((annotId != null) && (this.mergedMap.containsKey(Integer.valueOf(annotId.intValue()))))
/* 1151 */             skipWriting = true;
/*      */         }
/* 1153 */         if (this.mergedSet.contains(object)) {
/* 1154 */           PdfNumber annotId = dictionary.getAsNumber(annotId);
/* 1155 */           if (annotId != null) {
/* 1156 */             PdfIndirectObject unmerged = (PdfIndirectObject)this.unmergedMap.get(Integer.valueOf(annotId.intValue()));
/* 1157 */             if ((unmerged != null) && (unmerged.object.isDictionary())) {
/* 1158 */               PdfNumber structParent = ((PdfDictionary)unmerged.object).getAsNumber(PdfName.STRUCTPARENT);
/* 1159 */               if (structParent != null) {
/* 1160 */                 dictionary.put(PdfName.STRUCTPARENT, structParent);
/*      */               }
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/* 1167 */     if (!skipWriting) {
/* 1168 */       PdfDictionary dictionary = null;
/* 1169 */       PdfNumber annotId = null;
/* 1170 */       if ((this.mergeFields) && (object.object.isDictionary())) {
/* 1171 */         dictionary = (PdfDictionary)object.object;
/* 1172 */         annotId = dictionary.getAsNumber(annotId);
/* 1173 */         if (annotId != null)
/* 1174 */           dictionary.remove(annotId);
/*      */       }
/* 1176 */       this.body.add(object.object, object.number, object.generation, true);
/* 1177 */       if (annotId != null)
/* 1178 */         dictionary.put(annotId, annotId);
/*      */     }
/*      */   }
/*      */ 
/*      */   private void updateAnnotationReferences(PdfObject obj)
/*      */   {
/*      */     PdfDictionary dictionary;
/* 1184 */     if (obj.isArray()) {
/* 1185 */       PdfArray array = (PdfArray)obj;
/* 1186 */       for (int i = 0; i < array.size(); i++) {
/* 1187 */         PdfObject o = array.getPdfObject(i);
/* 1188 */         if ((o instanceof PdfIndirectReference)) {
/* 1189 */           for (PdfIndirectObject entry : this.unmergedSet) {
/* 1190 */             if ((entry.getIndirectReference().toString().equals(o.toString())) && 
/* 1191 */               (entry.object.isDictionary())) {
/* 1192 */               PdfNumber annotId = ((PdfDictionary)entry.object).getAsNumber(annotId);
/* 1193 */               if (annotId != null) {
/* 1194 */                 PdfIndirectObject merged = (PdfIndirectObject)this.mergedMap.get(Integer.valueOf(annotId.intValue()));
/* 1195 */                 if (merged != null) {
/* 1196 */                   array.set(i, merged.getIndirectReference());
/*      */                 }
/*      */               }
/*      */             }
/*      */           }
/*      */         }
/*      */         else
/* 1203 */           updateAnnotationReferences(o);
/*      */       }
/*      */     }
/* 1206 */     else if ((obj.isDictionary()) || (obj.isStream())) {
/* 1207 */       dictionary = (PdfDictionary)obj;
/* 1208 */       for (PdfName key : dictionary.getKeys()) {
/* 1209 */         PdfObject o = dictionary.get(key);
/* 1210 */         if ((o instanceof PdfIndirectReference)) {
/* 1211 */           for (PdfIndirectObject entry : this.unmergedSet) {
/* 1212 */             if ((entry.getIndirectReference().toString().equals(o.toString())) && 
/* 1213 */               (entry.object.isDictionary())) {
/* 1214 */               PdfNumber annotId = ((PdfDictionary)entry.object).getAsNumber(annotId);
/* 1215 */               if (annotId != null) {
/* 1216 */                 PdfIndirectObject merged = (PdfIndirectObject)this.mergedMap.get(Integer.valueOf(annotId.intValue()));
/* 1217 */                 if (merged != null) {
/* 1218 */                   dictionary.put(key, merged.getIndirectReference());
/*      */                 }
/*      */               }
/*      */             }
/*      */           }
/*      */         }
/*      */         else
/* 1225 */           updateAnnotationReferences(o);
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   private void updateCalculationOrder(PdfReader reader)
/*      */   {
/* 1232 */     PdfDictionary catalog = reader.getCatalog();
/* 1233 */     PdfDictionary acro = catalog.getAsDict(PdfName.ACROFORM);
/* 1234 */     if (acro == null)
/* 1235 */       return;
/* 1236 */     PdfArray co = acro.getAsArray(PdfName.CO);
/* 1237 */     if ((co == null) || (co.size() == 0))
/* 1238 */       return;
/* 1239 */     AcroFields af = reader.getAcroFields();
/* 1240 */     for (int k = 0; k < co.size(); k++) {
/* 1241 */       PdfObject obj = co.getPdfObject(k);
/* 1242 */       if ((obj != null) && (obj.isIndirect()))
/*      */       {
/* 1244 */         String name = getCOName(reader, (PRIndirectReference)obj);
/* 1245 */         if (af.getFieldItem(name) != null)
/*      */         {
/* 1247 */           name = "." + name;
/* 1248 */           if (!this.calculationOrder.contains(name))
/*      */           {
/* 1250 */             this.calculationOrder.add(name); }  } 
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/* 1255 */   private static String getCOName(PdfReader reader, PRIndirectReference ref) { String name = "";
/* 1256 */     while (ref != null) {
/* 1257 */       PdfObject obj = PdfReader.getPdfObject(ref);
/* 1258 */       if ((obj == null) || (obj.type() != 6))
/*      */         break;
/* 1260 */       PdfDictionary dic = (PdfDictionary)obj;
/* 1261 */       PdfString t = dic.getAsString(PdfName.T);
/* 1262 */       if (t != null) {
/* 1263 */         name = t.toUnicodeString() + "." + name;
/*      */       }
/* 1265 */       ref = (PRIndirectReference)dic.get(PdfName.PARENT);
/*      */     }
/* 1267 */     if (name.endsWith("."))
/* 1268 */       name = name.substring(0, name.length() - 2);
/* 1269 */     return name; }
/*      */ 
/*      */   private void mergeFields()
/*      */   {
/* 1273 */     int pageOffset = 0;
/* 1274 */     for (int k = 0; k < this.fields.size(); k++) {
/* 1275 */       AcroFields af = (AcroFields)this.fields.get(k);
/* 1276 */       Map fd = af.getFields();
/* 1277 */       if ((pageOffset < this.importedPages.size()) && (((ImportedPage)this.importedPages.get(pageOffset)).reader == af.reader)) {
/* 1278 */         addPageOffsetToField(fd, pageOffset);
/* 1279 */         pageOffset += af.reader.getNumberOfPages();
/*      */       }
/* 1281 */       mergeWithMaster(fd);
/*      */     }
/*      */   }
/*      */ 
/*      */   private void addPageOffsetToField(Map<String, AcroFields.Item> fd, int pageOffset) {
/* 1286 */     if (pageOffset == 0)
/* 1287 */       return;
/* 1288 */     for (AcroFields.Item item : fd.values())
/* 1289 */       for (int k = 0; k < item.size(); k++) {
/* 1290 */         int p = item.getPage(k).intValue();
/* 1291 */         item.forcePage(k, p + pageOffset);
/*      */       }
/*      */   }
/*      */ 
/*      */   private void mergeWithMaster(Map<String, AcroFields.Item> fd)
/*      */   {
/* 1297 */     for (Map.Entry entry : fd.entrySet()) {
/* 1298 */       String name = (String)entry.getKey();
/* 1299 */       mergeField(name, (AcroFields.Item)entry.getValue());
/*      */     }
/*      */   }
/*      */ 
/*      */   private void mergeField(String name, AcroFields.Item item)
/*      */   {
/* 1305 */     HashMap map = this.fieldTree;
/* 1306 */     StringTokenizer tk = new StringTokenizer(name, ".");
/* 1307 */     if (!tk.hasMoreTokens())
/* 1308 */       return;
/*      */     while (true) {
/* 1310 */       String s = tk.nextToken();
/* 1311 */       Object obj = map.get(s);
/* 1312 */       if (tk.hasMoreTokens()) {
/* 1313 */         if (obj == null) {
/* 1314 */           obj = new HashMap();
/* 1315 */           map.put(s, obj);
/* 1316 */           map = (HashMap)obj;
/*      */         }
/* 1319 */         else if ((obj instanceof HashMap)) {
/* 1320 */           map = (HashMap)obj;
/*      */         }
/*      */       }
/*      */       else
/*      */       {
/* 1325 */         if ((obj instanceof HashMap))
/* 1326 */           return;
/* 1327 */         PdfDictionary merged = item.getMerged(0);
/* 1328 */         if (obj == null) {
/* 1329 */           PdfDictionary field = new PdfDictionary();
/* 1330 */           if (PdfName.SIG.equals(merged.get(PdfName.FT)))
/* 1331 */             this.hasSignature = true;
/* 1332 */           for (Object element : merged.getKeys()) {
/* 1333 */             PdfName key = (PdfName)element;
/* 1334 */             if (fieldKeys.contains(key))
/* 1335 */               field.put(key, merged.get(key));
/*      */           }
/* 1337 */           ArrayList list = new ArrayList();
/* 1338 */           list.add(field);
/* 1339 */           createWidgets(list, item);
/* 1340 */           map.put(s, list);
/*      */         }
/*      */         else {
/* 1343 */           ArrayList list = (ArrayList)obj;
/* 1344 */           PdfDictionary field = (PdfDictionary)list.get(0);
/* 1345 */           PdfName type1 = (PdfName)field.get(PdfName.FT);
/* 1346 */           PdfName type2 = (PdfName)merged.get(PdfName.FT);
/* 1347 */           if ((type1 == null) || (!type1.equals(type2)))
/* 1348 */             return;
/* 1349 */           int flag1 = 0;
/* 1350 */           PdfObject f1 = field.get(PdfName.FF);
/* 1351 */           if ((f1 != null) && (f1.isNumber()))
/* 1352 */             flag1 = ((PdfNumber)f1).intValue();
/* 1353 */           int flag2 = 0;
/* 1354 */           PdfObject f2 = merged.get(PdfName.FF);
/* 1355 */           if ((f2 != null) && (f2.isNumber()))
/* 1356 */             flag2 = ((PdfNumber)f2).intValue();
/* 1357 */           if (type1.equals(PdfName.BTN)) {
/* 1358 */             if (((flag1 ^ flag2) & 0x10000) != 0) {
/* 1359 */               return;
/*      */             }
/* 1360 */             if (((flag1 & 0x10000) != 0) || (((flag1 ^ flag2) & 0x8000) == 0));
/*      */           }
/* 1363 */           else if ((type1.equals(PdfName.CH)) && 
/* 1364 */             (((flag1 ^ flag2) & 0x20000) != 0)) {
/* 1365 */             return;
/*      */           }
/* 1367 */           createWidgets(list, item);
/*      */         }
/* 1369 */         return;
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   private void createWidgets(ArrayList<Object> list, AcroFields.Item item) {
/* 1375 */     for (int k = 0; k < item.size(); k++) {
/* 1376 */       list.add(item.getPage(k));
/* 1377 */       PdfDictionary merged = item.getMerged(k);
/* 1378 */       PdfObject dr = merged.get(PdfName.DR);
/* 1379 */       if (dr != null)
/* 1380 */         PdfFormField.mergeResources(this.resources, (PdfDictionary)PdfReader.getPdfObject(dr));
/* 1381 */       PdfDictionary widget = new PdfDictionary();
/* 1382 */       for (Object element : merged.getKeys()) {
/* 1383 */         PdfName key = (PdfName)element;
/* 1384 */         if (widgetKeys.contains(key))
/* 1385 */           widget.put(key, merged.get(key));
/*      */       }
/* 1387 */       widget.put(iTextTag, new PdfNumber(item.getTabOrder(k).intValue() + 1));
/* 1388 */       list.add(widget);
/*      */     }
/*      */   }
/*      */ 
/*      */   private PdfObject propagate(PdfObject obj) throws IOException {
/* 1393 */     if (obj == null)
/* 1394 */       return new PdfNull();
/* 1395 */     if (obj.isArray()) {
/* 1396 */       PdfArray a = (PdfArray)obj;
/* 1397 */       for (int i = 0; i < a.size(); i++) {
/* 1398 */         a.set(i, propagate(a.getPdfObject(i)));
/*      */       }
/* 1400 */       return a;
/* 1401 */     }if ((obj.isDictionary()) || (obj.isStream())) {
/* 1402 */       PdfDictionary d = (PdfDictionary)obj;
/* 1403 */       for (PdfName key : d.getKeys()) {
/* 1404 */         d.put(key, propagate(d.get(key)));
/*      */       }
/* 1406 */       return d;
/* 1407 */     }if (obj.isIndirect()) {
/* 1408 */       obj = PdfReader.getPdfObject(obj);
/* 1409 */       return addToBody(propagate(obj)).getIndirectReference();
/*      */     }
/* 1411 */     return obj;
/*      */   }
/*      */ 
/*      */   private void createAcroForms() throws IOException, BadPdfFormatException {
/* 1415 */     if (this.fieldTree.isEmpty())
/* 1416 */       return;
/* 1417 */     PdfDictionary form = new PdfDictionary();
/* 1418 */     form.put(PdfName.DR, propagate(this.resources));
/*      */ 
/* 1420 */     if (this.needAppearances) {
/* 1421 */       form.put(PdfName.NEEDAPPEARANCES, PdfBoolean.PDFTRUE);
/*      */     }
/* 1423 */     form.put(PdfName.DA, new PdfString("/Helv 0 Tf 0 g "));
/* 1424 */     this.tabOrder = new HashMap();
/* 1425 */     this.calculationOrderRefs = new ArrayList(this.calculationOrder);
/* 1426 */     form.put(PdfName.FIELDS, branchForm(this.fieldTree, null, ""));
/* 1427 */     if (this.hasSignature)
/* 1428 */       form.put(PdfName.SIGFLAGS, new PdfNumber(3));
/* 1429 */     PdfArray co = new PdfArray();
/* 1430 */     for (int k = 0; k < this.calculationOrderRefs.size(); k++) {
/* 1431 */       Object obj = this.calculationOrderRefs.get(k);
/* 1432 */       if ((obj instanceof PdfIndirectReference))
/* 1433 */         co.add((PdfIndirectReference)obj);
/*      */     }
/* 1435 */     if (co.size() > 0)
/* 1436 */       form.put(PdfName.CO, co);
/* 1437 */     this.acroForm = addToBody(form).getIndirectReference();
/* 1438 */     for (ImportedPage importedPage : this.importedPages)
/* 1439 */       addToBody(importedPage.mergedFields, importedPage.annotsIndirectReference);
/*      */   }
/*      */ 
/*      */   private void updateReferences(PdfObject obj)
/*      */   {
/*      */     PdfDictionary dictionary;
/* 1444 */     if ((obj.isDictionary()) || (obj.isStream())) {
/* 1445 */       dictionary = (PdfDictionary)obj;
/* 1446 */       for (PdfName key : dictionary.getKeys()) {
/* 1447 */         PdfObject o = dictionary.get(key);
/* 1448 */         if (o.isIndirect()) {
/* 1449 */           PdfReader reader = ((PRIndirectReference)o).getReader();
/* 1450 */           HashMap indirects = (HashMap)this.indirectMap.get(reader);
/* 1451 */           IndirectReferences indRef = (IndirectReferences)indirects.get(new RefKey((PRIndirectReference)o));
/* 1452 */           if (indRef != null)
/* 1453 */             dictionary.put(key, indRef.getRef());
/*      */         }
/*      */         else {
/* 1456 */           updateReferences(o);
/*      */         }
/*      */       }
/* 1459 */     } else if (obj.isArray()) {
/* 1460 */       PdfArray array = (PdfArray)obj;
/* 1461 */       for (int i = 0; i < array.size(); i++) {
/* 1462 */         PdfObject o = array.getPdfObject(i);
/* 1463 */         if (o.isIndirect()) {
/* 1464 */           PdfReader reader = ((PRIndirectReference)o).getReader();
/* 1465 */           HashMap indirects = (HashMap)this.indirectMap.get(reader);
/* 1466 */           IndirectReferences indRef = (IndirectReferences)indirects.get(new RefKey((PRIndirectReference)o));
/* 1467 */           if (indRef != null)
/* 1468 */             array.set(i, indRef.getRef());
/*      */         }
/*      */         else {
/* 1471 */           updateReferences(o);
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   private PdfArray branchForm(HashMap<String, Object> level, PdfIndirectReference parent, String fname) throws IOException, BadPdfFormatException
/*      */   {
/* 1479 */     PdfArray arr = new PdfArray();
/* 1480 */     for (Map.Entry entry : level.entrySet()) {
/* 1481 */       String name = (String)entry.getKey();
/* 1482 */       Object obj = entry.getValue();
/* 1483 */       PdfIndirectReference ind = getPdfIndirectReference();
/* 1484 */       PdfDictionary dic = new PdfDictionary();
/* 1485 */       if (parent != null)
/* 1486 */         dic.put(PdfName.PARENT, parent);
/* 1487 */       dic.put(PdfName.T, new PdfString(name, "UnicodeBig"));
/* 1488 */       String fname2 = fname + "." + name;
/* 1489 */       int coidx = this.calculationOrder.indexOf(fname2);
/* 1490 */       if (coidx >= 0)
/* 1491 */         this.calculationOrderRefs.set(coidx, ind);
/* 1492 */       if ((obj instanceof HashMap)) {
/* 1493 */         dic.put(PdfName.KIDS, branchForm((HashMap)obj, ind, fname2));
/* 1494 */         arr.add(ind);
/* 1495 */         addToBody(dic, ind, true);
/*      */       }
/*      */       else {
/* 1498 */         ArrayList list = (ArrayList)obj;
/* 1499 */         dic.mergeDifferent((PdfDictionary)list.get(0));
/* 1500 */         if (list.size() == 3) {
/* 1501 */           dic.mergeDifferent((PdfDictionary)list.get(2));
/* 1502 */           int page = ((Integer)list.get(1)).intValue();
/* 1503 */           PdfArray annots = ((ImportedPage)this.importedPages.get(page - 1)).mergedFields;
/* 1504 */           PdfNumber nn = (PdfNumber)dic.get(iTextTag);
/* 1505 */           dic.remove(iTextTag);
/* 1506 */           dic.put(PdfName.TYPE, PdfName.ANNOT);
/* 1507 */           adjustTabOrder(annots, ind, nn);
/*      */         }
/*      */         else {
/* 1510 */           PdfDictionary field = (PdfDictionary)list.get(0);
/* 1511 */           PdfArray kids = new PdfArray();
/* 1512 */           for (int k = 1; k < list.size(); k += 2) {
/* 1513 */             int page = ((Integer)list.get(k)).intValue();
/* 1514 */             PdfArray annots = ((ImportedPage)this.importedPages.get(page - 1)).mergedFields;
/* 1515 */             PdfDictionary widget = new PdfDictionary();
/* 1516 */             widget.merge((PdfDictionary)list.get(k + 1));
/* 1517 */             widget.put(PdfName.PARENT, ind);
/* 1518 */             PdfNumber nn = (PdfNumber)widget.get(iTextTag);
/* 1519 */             widget.remove(iTextTag);
/* 1520 */             if (isTextField(field)) {
/* 1521 */               PdfString v = field.getAsString(PdfName.V);
/* 1522 */               PdfObject ap = widget.get(PdfName.AP);
/* 1523 */               if ((v != null) && (ap != null))
/* 1524 */                 if (!this.mergedTextFields.containsKey(list)) {
/* 1525 */                   this.mergedTextFields.put(list, ap);
/*      */                 } else {
/* 1527 */                   PdfObject ap1 = (PdfObject)this.mergedTextFields.get(list);
/* 1528 */                   widget.put(PdfName.AP, copyObject(ap1));
/*      */                 }
/*      */             }
/* 1531 */             else if (isCheckButton(field)) {
/* 1532 */               PdfName v = field.getAsName(PdfName.V);
/* 1533 */               PdfName as = widget.getAsName(PdfName.AS);
/* 1534 */               if ((v != null) && (as != null))
/* 1535 */                 widget.put(PdfName.AS, v);
/* 1536 */             } else if (isRadioButton(field)) {
/* 1537 */               PdfName v = field.getAsName(PdfName.V);
/* 1538 */               PdfName as = widget.getAsName(PdfName.AS);
/* 1539 */               if ((v != null) && (as != null) && (!as.equals(getOffStateName(widget)))) {
/* 1540 */                 if (!this.mergedRadioButtons.contains(list)) {
/* 1541 */                   this.mergedRadioButtons.add(list);
/* 1542 */                   widget.put(PdfName.AS, v);
/*      */                 } else {
/* 1544 */                   widget.put(PdfName.AS, getOffStateName(widget));
/*      */                 }
/*      */               }
/*      */             }
/* 1548 */             widget.put(PdfName.TYPE, PdfName.ANNOT);
/* 1549 */             PdfIndirectReference wref = addToBody(widget, getPdfIndirectReference(), true).getIndirectReference();
/* 1550 */             adjustTabOrder(annots, wref, nn);
/* 1551 */             kids.add(wref);
/*      */           }
/* 1553 */           dic.put(PdfName.KIDS, kids);
/*      */         }
/* 1555 */         arr.add(ind);
/* 1556 */         addToBody(dic, ind, true);
/*      */       }
/*      */     }
/* 1559 */     return arr;
/*      */   }
/*      */ 
/*      */   private void adjustTabOrder(PdfArray annots, PdfIndirectReference ind, PdfNumber nn) {
/* 1563 */     int v = nn.intValue();
/* 1564 */     ArrayList t = (ArrayList)this.tabOrder.get(annots);
/* 1565 */     if (t == null) {
/* 1566 */       t = new ArrayList();
/* 1567 */       int size = annots.size() - 1;
/* 1568 */       for (int k = 0; k < size; k++) {
/* 1569 */         t.add(zero);
/*      */       }
/* 1571 */       t.add(Integer.valueOf(v));
/* 1572 */       this.tabOrder.put(annots, t);
/* 1573 */       annots.add(ind);
/*      */     }
/*      */     else {
/* 1576 */       int size = t.size() - 1;
/* 1577 */       for (int k = size; k >= 0; k--) {
/* 1578 */         if (((Integer)t.get(k)).intValue() <= v) {
/* 1579 */           t.add(k + 1, Integer.valueOf(v));
/* 1580 */           annots.add(k + 1, ind);
/* 1581 */           size = -2;
/* 1582 */           break;
/*      */         }
/*      */       }
/* 1585 */       if (size != -2) {
/* 1586 */         t.add(0, Integer.valueOf(v));
/* 1587 */         annots.add(0, ind);
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   protected PdfDictionary getCatalog(PdfIndirectReference rootObj)
/*      */   {
/*      */     try
/*      */     {
/* 1599 */       PdfDictionary theCat = this.pdf.getCatalog(rootObj);
/* 1600 */       buildStructTreeRootForTagged(theCat);
/* 1601 */       if (this.fieldArray != null)
/* 1602 */         addFieldResources(theCat);
/* 1603 */       else if ((this.mergeFields) && (this.acroForm != null)) {
/* 1604 */         theCat.put(PdfName.ACROFORM, this.acroForm);
/*      */       }
/* 1606 */       return theCat;
/*      */     }
/*      */     catch (IOException e) {
/* 1609 */       throw new ExceptionConverter(e);
/*      */     }
/*      */   }
/*      */ 
/*      */   protected boolean isStructTreeRootReference(PdfIndirectReference prRef) {
/* 1614 */     if ((prRef == null) || (this.structTreeRootReference == null))
/* 1615 */       return false;
/* 1616 */     return (prRef.number == this.structTreeRootReference.number) && (prRef.generation == this.structTreeRootReference.generation);
/*      */   }
/*      */ 
/*      */   private void addFieldResources(PdfDictionary catalog) throws IOException {
/* 1620 */     if (this.fieldArray == null)
/* 1621 */       return;
/* 1622 */     PdfDictionary acroForm = new PdfDictionary();
/* 1623 */     catalog.put(PdfName.ACROFORM, acroForm);
/* 1624 */     acroForm.put(PdfName.FIELDS, this.fieldArray);
/* 1625 */     acroForm.put(PdfName.DA, new PdfString("/Helv 0 Tf 0 g "));
/* 1626 */     if (this.fieldTemplates.isEmpty())
/* 1627 */       return;
/* 1628 */     PdfDictionary dr = new PdfDictionary();
/* 1629 */     acroForm.put(PdfName.DR, dr);
/* 1630 */     for (PdfTemplate template : this.fieldTemplates) {
/* 1631 */       PdfFormField.mergeResources(dr, (PdfDictionary)template.getResources());
/*      */     }
/*      */ 
/* 1634 */     PdfDictionary fonts = dr.getAsDict(PdfName.FONT);
/* 1635 */     if (fonts == null) {
/* 1636 */       fonts = new PdfDictionary();
/* 1637 */       dr.put(PdfName.FONT, fonts);
/*      */     }
/* 1639 */     if (!fonts.contains(PdfName.HELV)) {
/* 1640 */       PdfDictionary dic = new PdfDictionary(PdfName.FONT);
/* 1641 */       dic.put(PdfName.BASEFONT, PdfName.HELVETICA);
/* 1642 */       dic.put(PdfName.ENCODING, PdfName.WIN_ANSI_ENCODING);
/* 1643 */       dic.put(PdfName.NAME, PdfName.HELV);
/* 1644 */       dic.put(PdfName.SUBTYPE, PdfName.TYPE1);
/* 1645 */       fonts.put(PdfName.HELV, addToBody(dic).getIndirectReference());
/*      */     }
/* 1647 */     if (!fonts.contains(PdfName.ZADB)) {
/* 1648 */       PdfDictionary dic = new PdfDictionary(PdfName.FONT);
/* 1649 */       dic.put(PdfName.BASEFONT, PdfName.ZAPFDINGBATS);
/* 1650 */       dic.put(PdfName.NAME, PdfName.ZADB);
/* 1651 */       dic.put(PdfName.SUBTYPE, PdfName.TYPE1);
/* 1652 */       fonts.put(PdfName.ZADB, addToBody(dic).getIndirectReference());
/*      */     }
/*      */   }
/*      */ 
/*      */   public void close()
/*      */   {
/* 1668 */     if (this.open) {
/* 1669 */       this.pdf.close();
/* 1670 */       super.close();
/*      */     }
/*      */   }
/*      */ 
/*      */   public PdfIndirectReference add(PdfOutline outline)
/*      */   {
/* 1684 */     return null;
/*      */   }
/*      */   public void addAnnotation(PdfAnnotation annot) {
/*      */   }
/*      */ 
/*      */   PdfIndirectReference add(PdfPage page, PdfContents contents) throws PdfException {
/* 1690 */     return null;
/*      */   }
/*      */ 
/*      */   public void freeReader(PdfReader reader) throws IOException {
/* 1694 */     if (this.mergeFields)
/* 1695 */       throw new UnsupportedOperationException(MessageLocalization.getComposedMessage("it.is.not.possible.to.free.reader.in.merge.fields.mode", new Object[0]));
/* 1696 */     PdfArray array = reader.trailer.getAsArray(PdfName.ID);
/* 1697 */     if (array != null)
/* 1698 */       this.originalFileID = array.getAsString(0).getBytes();
/* 1699 */     this.indirectMap.remove(reader);
/*      */ 
/* 1710 */     this.currentPdfReaderInstance = null;
/*      */ 
/* 1713 */     super.freeReader(reader);
/*      */   }
/*      */ 
/*      */   protected PdfName getOffStateName(PdfDictionary widget) {
/* 1717 */     return PdfName.Off;
/*      */   }
/*      */ 
/*      */   static Integer getFlags(PdfDictionary field)
/*      */   {
/* 1762 */     PdfName type = field.getAsName(PdfName.FT);
/* 1763 */     if (!PdfName.BTN.equals(type))
/* 1764 */       return null;
/* 1765 */     PdfNumber flags = field.getAsNumber(PdfName.FF);
/* 1766 */     if (flags == null)
/* 1767 */       return null;
/* 1768 */     return Integer.valueOf(flags.intValue());
/*      */   }
/*      */ 
/*      */   static boolean isCheckButton(PdfDictionary field) {
/* 1772 */     Integer flags = getFlags(field);
/* 1773 */     return (flags == null) || (((flags.intValue() & 0x10000) == 0) && ((flags.intValue() & 0x8000) == 0));
/*      */   }
/*      */ 
/*      */   static boolean isRadioButton(PdfDictionary field) {
/* 1777 */     Integer flags = getFlags(field);
/* 1778 */     return (flags != null) && ((flags.intValue() & 0x10000) == 0) && ((flags.intValue() & 0x8000) != 0);
/*      */   }
/*      */ 
/*      */   static boolean isTextField(PdfDictionary field) {
/* 1782 */     PdfName type = field.getAsName(PdfName.FT);
/* 1783 */     return PdfName.TX.equals(type);
/*      */   }
/*      */ 
/*      */   public PageStamp createPageStamp(PdfImportedPage iPage)
/*      */   {
/* 1810 */     int pageNum = iPage.getPageNumber();
/* 1811 */     PdfReader reader = iPage.getPdfReaderInstance().getReader();
/* 1812 */     if (isTagged())
/* 1813 */       throw new RuntimeException(MessageLocalization.getComposedMessage("creating.page.stamp.not.allowed.for.tagged.reader", new Object[0]));
/* 1814 */     PdfDictionary pageN = reader.getPageN(pageNum);
/* 1815 */     return new PageStamp(reader, pageN, this);
/*      */   }
/*      */ 
/*      */   static
/*      */   {
/* 1723 */     widgetKeys.add(PdfName.SUBTYPE);
/* 1724 */     widgetKeys.add(PdfName.CONTENTS);
/* 1725 */     widgetKeys.add(PdfName.RECT);
/* 1726 */     widgetKeys.add(PdfName.NM);
/* 1727 */     widgetKeys.add(PdfName.M);
/* 1728 */     widgetKeys.add(PdfName.F);
/* 1729 */     widgetKeys.add(PdfName.BS);
/* 1730 */     widgetKeys.add(PdfName.BORDER);
/* 1731 */     widgetKeys.add(PdfName.AP);
/* 1732 */     widgetKeys.add(PdfName.AS);
/* 1733 */     widgetKeys.add(PdfName.C);
/* 1734 */     widgetKeys.add(PdfName.A);
/* 1735 */     widgetKeys.add(PdfName.STRUCTPARENT);
/* 1736 */     widgetKeys.add(PdfName.OC);
/* 1737 */     widgetKeys.add(PdfName.H);
/* 1738 */     widgetKeys.add(PdfName.MK);
/* 1739 */     widgetKeys.add(PdfName.DA);
/* 1740 */     widgetKeys.add(PdfName.Q);
/* 1741 */     widgetKeys.add(PdfName.P);
/* 1742 */     widgetKeys.add(PdfName.TYPE);
/* 1743 */     widgetKeys.add(annotId);
/* 1744 */     fieldKeys.add(PdfName.AA);
/* 1745 */     fieldKeys.add(PdfName.FT);
/* 1746 */     fieldKeys.add(PdfName.TU);
/* 1747 */     fieldKeys.add(PdfName.TM);
/* 1748 */     fieldKeys.add(PdfName.FF);
/* 1749 */     fieldKeys.add(PdfName.V);
/* 1750 */     fieldKeys.add(PdfName.DV);
/* 1751 */     fieldKeys.add(PdfName.DS);
/* 1752 */     fieldKeys.add(PdfName.RV);
/* 1753 */     fieldKeys.add(PdfName.OPT);
/* 1754 */     fieldKeys.add(PdfName.MAXLEN);
/* 1755 */     fieldKeys.add(PdfName.TI);
/* 1756 */     fieldKeys.add(PdfName.I);
/* 1757 */     fieldKeys.add(PdfName.LOCK);
/* 1758 */     fieldKeys.add(PdfName.SV);
/*      */   }
/*      */ 
/*      */   public static class StampContent extends PdfContentByte
/*      */   {
/*      */     PageResources pageResources;
/*      */ 
/*      */     StampContent(PdfWriter writer, PageResources pageResources)
/*      */     {
/* 2028 */       super();
/* 2029 */       this.pageResources = pageResources;
/*      */     }
/*      */ 
/*      */     public PdfContentByte getDuplicate()
/*      */     {
/* 2040 */       return new StampContent(this.writer, this.pageResources);
/*      */     }
/*      */ 
/*      */     PageResources getPageResources()
/*      */     {
/* 2045 */       return this.pageResources;
/*      */     }
/*      */   }
/*      */ 
/*      */   public static class PageStamp
/*      */   {
/*      */     PdfDictionary pageN;
/*      */     PdfCopy.StampContent under;
/*      */     PdfCopy.StampContent over;
/*      */     PageResources pageResources;
/*      */     PdfReader reader;
/*      */     PdfCopy cstp;
/*      */ 
/*      */     PageStamp(PdfReader reader, PdfDictionary pageN, PdfCopy cstp)
/*      */     {
/* 1828 */       this.pageN = pageN;
/* 1829 */       this.reader = reader;
/* 1830 */       this.cstp = cstp;
/*      */     }
/*      */ 
/*      */     public PdfContentByte getUnderContent() {
/* 1834 */       if (this.under == null) {
/* 1835 */         if (this.pageResources == null) {
/* 1836 */           this.pageResources = new PageResources();
/* 1837 */           PdfDictionary resources = this.pageN.getAsDict(PdfName.RESOURCES);
/* 1838 */           this.pageResources.setOriginalResources(resources, this.cstp.namePtr);
/*      */         }
/* 1840 */         this.under = new PdfCopy.StampContent(this.cstp, this.pageResources);
/*      */       }
/* 1842 */       return this.under;
/*      */     }
/*      */ 
/*      */     public PdfContentByte getOverContent() {
/* 1846 */       if (this.over == null) {
/* 1847 */         if (this.pageResources == null) {
/* 1848 */           this.pageResources = new PageResources();
/* 1849 */           PdfDictionary resources = this.pageN.getAsDict(PdfName.RESOURCES);
/* 1850 */           this.pageResources.setOriginalResources(resources, this.cstp.namePtr);
/*      */         }
/* 1852 */         this.over = new PdfCopy.StampContent(this.cstp, this.pageResources);
/*      */       }
/* 1854 */       return this.over;
/*      */     }
/*      */ 
/*      */     public void alterContents() throws IOException {
/* 1858 */       if ((this.over == null) && (this.under == null))
/* 1859 */         return;
/* 1860 */       PdfArray ar = null;
/* 1861 */       PdfObject content = PdfReader.getPdfObject(this.pageN.get(PdfName.CONTENTS), this.pageN);
/* 1862 */       if (content == null) {
/* 1863 */         ar = new PdfArray();
/* 1864 */         this.pageN.put(PdfName.CONTENTS, ar);
/* 1865 */       } else if (content.isArray()) {
/* 1866 */         ar = (PdfArray)content;
/* 1867 */       } else if (content.isStream()) {
/* 1868 */         ar = new PdfArray();
/* 1869 */         ar.add(this.pageN.get(PdfName.CONTENTS));
/* 1870 */         this.pageN.put(PdfName.CONTENTS, ar);
/*      */       } else {
/* 1872 */         ar = new PdfArray();
/* 1873 */         this.pageN.put(PdfName.CONTENTS, ar);
/*      */       }
/* 1875 */       ByteBuffer out = new ByteBuffer();
/* 1876 */       if (this.under != null) {
/* 1877 */         out.append(PdfContents.SAVESTATE);
/* 1878 */         applyRotation(this.pageN, out);
/* 1879 */         out.append(this.under.getInternalBuffer());
/* 1880 */         out.append(PdfContents.RESTORESTATE);
/*      */       }
/* 1882 */       if (this.over != null)
/* 1883 */         out.append(PdfContents.SAVESTATE);
/* 1884 */       PdfStream stream = new PdfStream(out.toByteArray());
/* 1885 */       stream.flateCompress(this.cstp.getCompressionLevel());
/* 1886 */       PdfIndirectReference ref1 = this.cstp.addToBody(stream).getIndirectReference();
/* 1887 */       ar.addFirst(ref1);
/* 1888 */       out.reset();
/* 1889 */       if (this.over != null) {
/* 1890 */         out.append(' ');
/* 1891 */         out.append(PdfContents.RESTORESTATE);
/* 1892 */         out.append(PdfContents.SAVESTATE);
/* 1893 */         applyRotation(this.pageN, out);
/* 1894 */         out.append(this.over.getInternalBuffer());
/* 1895 */         out.append(PdfContents.RESTORESTATE);
/* 1896 */         stream = new PdfStream(out.toByteArray());
/* 1897 */         stream.flateCompress(this.cstp.getCompressionLevel());
/* 1898 */         ar.add(this.cstp.addToBody(stream).getIndirectReference());
/*      */       }
/* 1900 */       this.pageN.put(PdfName.RESOURCES, this.pageResources.getResources());
/*      */     }
/*      */ 
/*      */     void applyRotation(PdfDictionary pageN, ByteBuffer out) {
/* 1904 */       if (!this.cstp.rotateContents)
/* 1905 */         return;
/* 1906 */       Rectangle page = this.reader.getPageSizeWithRotation(pageN);
/* 1907 */       int rotation = page.getRotation();
/* 1908 */       switch (rotation) {
/*      */       case 90:
/* 1910 */         out.append(PdfContents.ROTATE90);
/* 1911 */         out.append(page.getTop());
/* 1912 */         out.append(' ').append('0').append(PdfContents.ROTATEFINAL);
/* 1913 */         break;
/*      */       case 180:
/* 1915 */         out.append(PdfContents.ROTATE180);
/* 1916 */         out.append(page.getRight());
/* 1917 */         out.append(' ');
/* 1918 */         out.append(page.getTop());
/* 1919 */         out.append(PdfContents.ROTATEFINAL);
/* 1920 */         break;
/*      */       case 270:
/* 1922 */         out.append(PdfContents.ROTATE270);
/* 1923 */         out.append('0').append(' ');
/* 1924 */         out.append(page.getRight());
/* 1925 */         out.append(PdfContents.ROTATEFINAL);
/*      */       }
/*      */     }
/*      */ 
/*      */     private void addDocumentField(PdfIndirectReference ref)
/*      */     {
/* 1931 */       if (this.cstp.fieldArray == null)
/* 1932 */         this.cstp.fieldArray = new PdfArray();
/* 1933 */       this.cstp.fieldArray.add(ref);
/*      */     }
/*      */ 
/*      */     private void expandFields(PdfFormField field, ArrayList<PdfAnnotation> allAnnots) {
/* 1937 */       allAnnots.add(field);
/* 1938 */       ArrayList kids = field.getKids();
/* 1939 */       if (kids != null)
/* 1940 */         for (PdfFormField f : kids)
/* 1941 */           expandFields(f, allAnnots);
/*      */     }
/*      */ 
/*      */     public void addAnnotation(PdfAnnotation annot)
/*      */     {
/*      */       try {
/* 1947 */         ArrayList allAnnots = new ArrayList();
/* 1948 */         if (annot.isForm()) {
/* 1949 */           PdfFormField field = (PdfFormField)annot;
/* 1950 */           if (field.getParent() != null)
/* 1951 */             return;
/* 1952 */           expandFields(field, allAnnots);
/* 1953 */           if (this.cstp.fieldTemplates == null)
/* 1954 */             this.cstp.fieldTemplates = new HashSet();
/*      */         }
/*      */         else {
/* 1957 */           allAnnots.add(annot);
/* 1958 */         }for (int k = 0; k < allAnnots.size(); k++) {
/* 1959 */           annot = (PdfAnnotation)allAnnots.get(k);
/* 1960 */           if (annot.isForm()) {
/* 1961 */             if (!annot.isUsed()) {
/* 1962 */               HashSet templates = annot.getTemplates();
/* 1963 */               if (templates != null)
/* 1964 */                 this.cstp.fieldTemplates.addAll(templates);
/*      */             }
/* 1966 */             PdfFormField field = (PdfFormField)annot;
/* 1967 */             if (field.getParent() == null)
/* 1968 */               addDocumentField(field.getIndirectReference());
/*      */           }
/* 1970 */           if (annot.isAnnotation()) {
/* 1971 */             PdfObject pdfobj = PdfReader.getPdfObject(this.pageN.get(PdfName.ANNOTS), this.pageN);
/* 1972 */             PdfArray annots = null;
/* 1973 */             if ((pdfobj == null) || (!pdfobj.isArray())) {
/* 1974 */               annots = new PdfArray();
/* 1975 */               this.pageN.put(PdfName.ANNOTS, annots);
/*      */             }
/*      */             else {
/* 1978 */               annots = (PdfArray)pdfobj;
/* 1979 */             }annots.add(annot.getIndirectReference());
/* 1980 */             if (!annot.isUsed()) {
/* 1981 */               PdfRectangle rect = (PdfRectangle)annot.get(PdfName.RECT);
/* 1982 */               if ((rect != null) && ((rect.left() != 0.0F) || (rect.right() != 0.0F) || (rect.top() != 0.0F) || (rect.bottom() != 0.0F))) {
/* 1983 */                 int rotation = this.reader.getPageRotation(this.pageN);
/* 1984 */                 Rectangle pageSize = this.reader.getPageSizeWithRotation(this.pageN);
/* 1985 */                 switch (rotation) {
/*      */                 case 90:
/* 1987 */                   annot.put(PdfName.RECT, new PdfRectangle(pageSize.getTop() - rect.bottom(), rect.left(), pageSize.getTop() - rect.top(), rect.right()));
/*      */ 
/* 1992 */                   break;
/*      */                 case 180:
/* 1994 */                   annot.put(PdfName.RECT, new PdfRectangle(pageSize.getRight() - rect.left(), pageSize.getTop() - rect.bottom(), pageSize.getRight() - rect.right(), pageSize.getTop() - rect.top()));
/*      */ 
/* 1999 */                   break;
/*      */                 case 270:
/* 2001 */                   annot.put(PdfName.RECT, new PdfRectangle(rect.bottom(), pageSize.getRight() - rect.left(), rect.top(), pageSize.getRight() - rect.right()));
/*      */                 }
/*      */ 
/*      */               }
/*      */ 
/*      */             }
/*      */ 
/*      */           }
/*      */ 
/* 2011 */           if (!annot.isUsed()) {
/* 2012 */             annot.setUsed();
/* 2013 */             this.cstp.addToBody(annot, annot.getIndirectReference());
/*      */           }
/*      */         }
/*      */       }
/*      */       catch (IOException e) {
/* 2018 */         throw new ExceptionConverter(e);
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   protected static class ImportedPage
/*      */   {
/*      */     int pageNumber;
/*      */     PdfReader reader;
/*      */     PdfArray mergedFields;
/*      */     PdfIndirectReference annotsIndirectReference;
/*      */ 
/*      */     ImportedPage(PdfReader reader, int pageNumber, boolean keepFields)
/*      */     {
/*  150 */       this.pageNumber = pageNumber;
/*  151 */       this.reader = reader;
/*  152 */       if (keepFields)
/*  153 */         this.mergedFields = new PdfArray();
/*      */     }
/*      */ 
/*      */     public boolean equals(Object o)
/*      */     {
/*  159 */       if (!(o instanceof ImportedPage)) return false;
/*  160 */       ImportedPage other = (ImportedPage)o;
/*  161 */       return (this.pageNumber == other.pageNumber) && (this.reader.equals(other.reader));
/*      */     }
/*      */ 
/*      */     public String toString() {
/*  165 */       return Integer.toString(this.pageNumber);
/*      */     }
/*      */   }
/*      */ 
/*      */   static class IndirectReferences
/*      */   {
/*      */     PdfIndirectReference theRef;
/*      */     boolean hasCopied;
/*      */ 
/*      */     IndirectReferences(PdfIndirectReference ref)
/*      */     {
/*   75 */       this.theRef = ref;
/*   76 */       this.hasCopied = false;
/*      */     }
/*   78 */     void setCopied() { this.hasCopied = true; } 
/*   79 */     void setNotCopied() { this.hasCopied = false; } 
/*   80 */     boolean getCopied() { return this.hasCopied; } 
/*   81 */     PdfIndirectReference getRef() { return this.theRef; }
/*      */ 
/*      */     public String toString()
/*      */     {
/*   85 */       String ext = "";
/*   86 */       if (this.hasCopied) ext = ext + " Copied";
/*   87 */       return getRef() + ext;
/*      */     }
/*      */   }
/*      */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.pdf.PdfCopy
 * JD-Core Version:    0.6.2
 */