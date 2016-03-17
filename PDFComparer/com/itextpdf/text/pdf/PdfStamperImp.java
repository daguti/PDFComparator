/*      */ package com.itextpdf.text.pdf;
/*      */ 
/*      */ import com.itextpdf.text.DocumentException;
/*      */ import com.itextpdf.text.ExceptionConverter;
/*      */ import com.itextpdf.text.Image;
/*      */ import com.itextpdf.text.Rectangle;
/*      */ import com.itextpdf.text.Version;
/*      */ import com.itextpdf.text.error_messages.MessageLocalization;
/*      */ import com.itextpdf.text.exceptions.BadPasswordException;
/*      */ import com.itextpdf.text.log.Counter;
/*      */ import com.itextpdf.text.log.CounterFactory;
/*      */ import com.itextpdf.text.pdf.collection.PdfCollection;
/*      */ import com.itextpdf.text.pdf.internal.PdfVersionImp;
/*      */ import com.itextpdf.text.pdf.internal.PdfViewerPreferencesImp;
/*      */ import com.itextpdf.text.xml.xmp.PdfProperties;
/*      */ import com.itextpdf.text.xml.xmp.XmpBasicProperties;
/*      */ import com.itextpdf.text.xml.xmp.XmpWriter;
/*      */ import com.itextpdf.xmp.XMPException;
/*      */ import com.itextpdf.xmp.XMPMeta;
/*      */ import com.itextpdf.xmp.XMPMetaFactory;
/*      */ import com.itextpdf.xmp.options.SerializeOptions;
/*      */ import java.io.ByteArrayOutputStream;
/*      */ import java.io.IOException;
/*      */ import java.io.OutputStream;
/*      */ import java.util.ArrayList;
/*      */ import java.util.HashMap;
/*      */ import java.util.HashSet;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.Map.Entry;
/*      */ 
/*      */ class PdfStamperImp extends PdfWriter
/*      */ {
/*   79 */   HashMap<PdfReader, IntHashtable> readers2intrefs = new HashMap();
/*   80 */   HashMap<PdfReader, RandomAccessFileOrArray> readers2file = new HashMap();
/*      */   protected RandomAccessFileOrArray file;
/*      */   PdfReader reader;
/*   83 */   IntHashtable myXref = new IntHashtable();
/*      */ 
/*   85 */   HashMap<PdfDictionary, PageStamp> pagesToContent = new HashMap();
/*   86 */   protected boolean closed = false;
/*      */ 
/*   88 */   private boolean rotateContents = true;
/*      */   protected AcroFields acroFields;
/*   90 */   protected boolean flat = false;
/*   91 */   protected boolean flatFreeText = false;
/*   92 */   protected int[] namePtr = { 0 };
/*   93 */   protected HashSet<String> partialFlattening = new HashSet();
/*   94 */   protected boolean useVp = false;
/*   95 */   protected PdfViewerPreferencesImp viewerPreferences = new PdfViewerPreferencesImp();
/*   96 */   protected HashSet<PdfTemplate> fieldTemplates = new HashSet();
/*   97 */   protected boolean fieldsAdded = false;
/*   98 */   protected int sigFlags = 0;
/*      */   protected boolean append;
/*      */   protected IntHashtable marked;
/*      */   protected int initialXrefSize;
/*      */   protected PdfAction openAction;
/*  104 */   protected Counter COUNTER = CounterFactory.getCounter(PdfStamper.class);
/*      */ 
/*  106 */   protected Counter getCounter() { return this.COUNTER; }
/*      */ 
/*      */ 
/*      */   protected PdfStamperImp(PdfReader reader, OutputStream os, char pdfVersion, boolean append)
/*      */     throws DocumentException, IOException
/*      */   {
/*  119 */     super(new PdfDocument(), os);
/*  120 */     if (!reader.isOpenedWithFullPermissions())
/*  121 */       throw new BadPasswordException(MessageLocalization.getComposedMessage("pdfreader.not.opened.with.owner.password", new Object[0]));
/*  122 */     if (reader.isTampered())
/*  123 */       throw new DocumentException(MessageLocalization.getComposedMessage("the.original.document.was.reused.read.it.again.from.file", new Object[0]));
/*  124 */     reader.setTampered(true);
/*  125 */     this.reader = reader;
/*  126 */     this.file = reader.getSafeFile();
/*  127 */     this.append = append;
/*  128 */     if ((reader.isEncrypted()) && ((append) || (PdfReader.unethicalreading))) {
/*  129 */       this.crypto = new PdfEncryption(reader.getDecrypt());
/*      */     }
/*  131 */     if (append) {
/*  132 */       if (reader.isRebuilt())
/*  133 */         throw new DocumentException(MessageLocalization.getComposedMessage("append.mode.requires.a.document.without.errors.even.if.recovery.was.possible", new Object[0]));
/*  134 */       this.pdf_version.setAppendmode(true);
/*  135 */       byte[] buf = new byte[8192];
/*      */       int n;
/*  137 */       while ((n = this.file.read(buf)) > 0)
/*  138 */         this.os.write(buf, 0, n);
/*  139 */       this.prevxref = reader.getLastXref();
/*  140 */       reader.setAppendable(true);
/*      */     }
/*  143 */     else if (pdfVersion == 0) {
/*  144 */       super.setPdfVersion(reader.getPdfVersion());
/*      */     } else {
/*  146 */       super.setPdfVersion(pdfVersion);
/*      */     }
/*  148 */     super.open();
/*  149 */     this.pdf.addWriter(this);
/*  150 */     if (append) {
/*  151 */       this.body.setRefnum(reader.getXrefSize());
/*  152 */       this.marked = new IntHashtable();
/*  153 */       if (reader.isNewXrefType())
/*  154 */         this.fullCompression = true;
/*  155 */       if (reader.isHybridXref())
/*  156 */         this.fullCompression = false;
/*      */     }
/*  158 */     this.initialXrefSize = reader.getXrefSize();
/*  159 */     readColorProfile();
/*      */   }
/*      */ 
/*      */   protected void readColorProfile() {
/*  163 */     PdfObject outputIntents = this.reader.getCatalog().getAsArray(PdfName.OUTPUTINTENTS);
/*  164 */     if ((outputIntents != null) && (((PdfArray)outputIntents).size() > 0)) {
/*  165 */       PdfStream iccProfileStream = null;
/*  166 */       for (int i = 0; i < ((PdfArray)outputIntents).size(); i++) {
/*  167 */         PdfDictionary outputIntentDictionary = ((PdfArray)outputIntents).getAsDict(i);
/*  168 */         if (outputIntentDictionary != null) {
/*  169 */           iccProfileStream = outputIntentDictionary.getAsStream(PdfName.DESTOUTPUTPROFILE);
/*  170 */           if (iccProfileStream != null) {
/*      */             break;
/*      */           }
/*      */         }
/*      */       }
/*  175 */       if ((iccProfileStream instanceof PRStream))
/*      */         try {
/*  177 */           this.colorProfile = ICC_Profile.getInstance(PdfReader.getStreamBytes((PRStream)iccProfileStream));
/*      */         } catch (IOException exc) {
/*  179 */           throw new ExceptionConverter(exc);
/*      */         }
/*      */     }
/*      */   }
/*      */ 
/*      */   protected void setViewerPreferences()
/*      */   {
/*  186 */     this.reader.setViewerPreferences(this.viewerPreferences);
/*  187 */     markUsed(this.reader.getTrailer().get(PdfName.ROOT));
/*      */   }
/*      */ 
/*      */   protected void close(Map<String, String> moreInfo) throws IOException {
/*  191 */     if (this.closed)
/*  192 */       return;
/*  193 */     if (this.useVp) {
/*  194 */       setViewerPreferences();
/*      */     }
/*  196 */     if (this.flat)
/*  197 */       flatFields();
/*  198 */     if (this.flatFreeText)
/*  199 */       flatFreeTextFields();
/*  200 */     addFieldResources();
/*  201 */     PdfDictionary catalog = this.reader.getCatalog();
/*  202 */     getPdfVersion().addToCatalog(catalog);
/*  203 */     PdfDictionary acroForm = (PdfDictionary)PdfReader.getPdfObject(catalog.get(PdfName.ACROFORM), this.reader.getCatalog());
/*  204 */     if ((this.acroFields != null) && (this.acroFields.getXfa().isChanged())) {
/*  205 */       markUsed(acroForm);
/*  206 */       if (!this.flat)
/*  207 */         this.acroFields.getXfa().setXfa(this);
/*      */     }
/*  209 */     if ((this.sigFlags != 0) && 
/*  210 */       (acroForm != null)) {
/*  211 */       acroForm.put(PdfName.SIGFLAGS, new PdfNumber(this.sigFlags));
/*  212 */       markUsed(acroForm);
/*  213 */       markUsed(catalog);
/*      */     }
/*      */ 
/*  216 */     this.closed = true;
/*  217 */     addSharedObjectsToBody();
/*  218 */     setOutlines();
/*  219 */     setJavaScript();
/*  220 */     addFileAttachments();
/*      */ 
/*  222 */     if (this.extraCatalog != null) {
/*  223 */       catalog.mergeDifferent(this.extraCatalog);
/*      */     }
/*  225 */     if (this.openAction != null) {
/*  226 */       catalog.put(PdfName.OPENACTION, this.openAction);
/*      */     }
/*  228 */     if (this.pdf.pageLabels != null) {
/*  229 */       catalog.put(PdfName.PAGELABELS, this.pdf.pageLabels.getDictionary(this));
/*      */     }
/*      */ 
/*  232 */     if (!this.documentOCG.isEmpty()) {
/*  233 */       fillOCProperties(false);
/*  234 */       PdfDictionary ocdict = catalog.getAsDict(PdfName.OCPROPERTIES);
/*  235 */       if (ocdict == null) {
/*  236 */         this.reader.getCatalog().put(PdfName.OCPROPERTIES, this.OCProperties);
/*      */       }
/*      */       else {
/*  239 */         ocdict.put(PdfName.OCGS, this.OCProperties.get(PdfName.OCGS));
/*  240 */         PdfDictionary ddict = ocdict.getAsDict(PdfName.D);
/*  241 */         if (ddict == null) {
/*  242 */           ddict = new PdfDictionary();
/*  243 */           ocdict.put(PdfName.D, ddict);
/*      */         }
/*  245 */         ddict.put(PdfName.ORDER, this.OCProperties.getAsDict(PdfName.D).get(PdfName.ORDER));
/*  246 */         ddict.put(PdfName.RBGROUPS, this.OCProperties.getAsDict(PdfName.D).get(PdfName.RBGROUPS));
/*  247 */         ddict.put(PdfName.OFF, this.OCProperties.getAsDict(PdfName.D).get(PdfName.OFF));
/*  248 */         ddict.put(PdfName.AS, this.OCProperties.getAsDict(PdfName.D).get(PdfName.AS));
/*      */       }
/*  250 */       PdfWriter.checkPdfIsoConformance(this, 7, this.OCProperties);
/*      */     }
/*      */ 
/*  253 */     int skipInfo = -1;
/*  254 */     PdfIndirectReference iInfo = this.reader.getTrailer().getAsIndirectObject(PdfName.INFO);
/*  255 */     if (iInfo != null) {
/*  256 */       skipInfo = iInfo.getNumber();
/*      */     }
/*  258 */     PdfDictionary oldInfo = this.reader.getTrailer().getAsDict(PdfName.INFO);
/*  259 */     String producer = null;
/*  260 */     if ((oldInfo != null) && (oldInfo.get(PdfName.PRODUCER) != null)) {
/*  261 */       producer = oldInfo.getAsString(PdfName.PRODUCER).toUnicodeString();
/*      */     }
/*  263 */     Version version = Version.getInstance();
/*  264 */     if ((producer == null) || (version.getVersion().indexOf(version.getProduct()) == -1)) {
/*  265 */       producer = version.getVersion();
/*      */     }
/*      */     else {
/*  268 */       int idx = producer.indexOf("; modified using");
/*      */       StringBuffer buf;
/*      */       StringBuffer buf;
/*  270 */       if (idx == -1)
/*  271 */         buf = new StringBuffer(producer);
/*      */       else
/*  273 */         buf = new StringBuffer(producer.substring(0, idx));
/*  274 */       buf.append("; modified using ");
/*  275 */       buf.append(version.getVersion());
/*  276 */       producer = buf.toString();
/*      */     }
/*  278 */     PdfIndirectReference info = null;
/*  279 */     PdfDictionary newInfo = new PdfDictionary();
/*  280 */     if (oldInfo != null) {
/*  281 */       for (Object element : oldInfo.getKeys()) {
/*  282 */         PdfName key = (PdfName)element;
/*  283 */         PdfObject value = PdfReader.getPdfObject(oldInfo.get(key));
/*  284 */         newInfo.put(key, value);
/*      */       }
/*      */     }
/*  287 */     if (moreInfo != null) {
/*  288 */       for (Map.Entry entry : moreInfo.entrySet()) {
/*  289 */         String key = (String)entry.getKey();
/*  290 */         PdfName keyName = new PdfName(key);
/*  291 */         String value = (String)entry.getValue();
/*  292 */         if (value == null)
/*  293 */           newInfo.remove(keyName);
/*      */         else
/*  295 */           newInfo.put(keyName, new PdfString(value, "UnicodeBig"));
/*      */       }
/*      */     }
/*  298 */     PdfDate date = new PdfDate();
/*  299 */     newInfo.put(PdfName.MODDATE, date);
/*  300 */     newInfo.put(PdfName.PRODUCER, new PdfString(producer, "UnicodeBig"));
/*  301 */     if (this.append) {
/*  302 */       if (iInfo == null)
/*  303 */         info = addToBody(newInfo, false).getIndirectReference();
/*      */       else
/*  305 */         info = addToBody(newInfo, iInfo.getNumber(), false).getIndirectReference();
/*      */     }
/*      */     else {
/*  308 */       info = addToBody(newInfo, false).getIndirectReference();
/*      */     }
/*      */ 
/*  311 */     byte[] altMetadata = null;
/*  312 */     PdfObject xmpo = PdfReader.getPdfObject(catalog.get(PdfName.METADATA));
/*  313 */     if ((xmpo != null) && (xmpo.isStream())) {
/*  314 */       altMetadata = PdfReader.getStreamBytesRaw((PRStream)xmpo);
/*  315 */       PdfReader.killIndirect(catalog.get(PdfName.METADATA));
/*      */     }
/*  317 */     PdfStream xmp = null;
/*  318 */     if (this.xmpMetadata != null)
/*  319 */       altMetadata = this.xmpMetadata;
/*  320 */     else if (this.xmpWriter != null) {
/*      */       try {
/*  322 */         ByteArrayOutputStream baos = new ByteArrayOutputStream();
/*  323 */         PdfProperties.setProducer(this.xmpWriter.getXmpMeta(), producer);
/*  324 */         XmpBasicProperties.setModDate(this.xmpWriter.getXmpMeta(), date.getW3CDate());
/*  325 */         XmpBasicProperties.setMetaDataDate(this.xmpWriter.getXmpMeta(), date.getW3CDate());
/*  326 */         this.xmpWriter.serialize(baos);
/*  327 */         this.xmpWriter.close();
/*  328 */         xmp = new PdfStream(baos.toByteArray());
/*      */       } catch (XMPException exc) {
/*  330 */         this.xmpWriter = null;
/*      */       }
/*      */     }
/*  333 */     if ((xmp == null) && (altMetadata != null)) {
/*      */       try {
/*  335 */         ByteArrayOutputStream baos = new ByteArrayOutputStream();
/*  336 */         if ((moreInfo == null) || (this.xmpMetadata != null)) {
/*  337 */           XMPMeta xmpMeta = XMPMetaFactory.parseFromBuffer(altMetadata);
/*      */ 
/*  339 */           PdfProperties.setProducer(xmpMeta, producer);
/*  340 */           XmpBasicProperties.setModDate(xmpMeta, date.getW3CDate());
/*  341 */           XmpBasicProperties.setMetaDataDate(xmpMeta, date.getW3CDate());
/*      */ 
/*  343 */           SerializeOptions serializeOptions = new SerializeOptions();
/*  344 */           serializeOptions.setPadding(2000);
/*  345 */           XMPMetaFactory.serialize(xmpMeta, baos, serializeOptions);
/*      */         } else {
/*  347 */           XmpWriter xmpw = createXmpWriter(baos, newInfo);
/*  348 */           xmpw.close();
/*      */         }
/*  350 */         xmp = new PdfStream(baos.toByteArray());
/*      */       } catch (XMPException e) {
/*  352 */         xmp = new PdfStream(altMetadata);
/*      */       } catch (IOException e) {
/*  354 */         xmp = new PdfStream(altMetadata);
/*      */       }
/*      */     }
/*  357 */     if (xmp != null) {
/*  358 */       xmp.put(PdfName.TYPE, PdfName.METADATA);
/*  359 */       xmp.put(PdfName.SUBTYPE, PdfName.XML);
/*  360 */       if ((this.crypto != null) && (!this.crypto.isMetadataEncrypted())) {
/*  361 */         PdfArray ar = new PdfArray();
/*  362 */         ar.add(PdfName.CRYPT);
/*  363 */         xmp.put(PdfName.FILTER, ar);
/*      */       }
/*  365 */       if ((this.append) && (xmpo != null)) {
/*  366 */         this.body.add(xmp, xmpo.getIndRef());
/*      */       }
/*      */       else {
/*  369 */         catalog.put(PdfName.METADATA, this.body.add(xmp).getIndirectReference());
/*  370 */         markUsed(catalog);
/*      */       }
/*      */     }
/*  373 */     close(info, skipInfo);
/*      */   }
/*      */ 
/*      */   protected void close(PdfIndirectReference info, int skipInfo) throws IOException {
/*  377 */     alterContents();
/*  378 */     int rootN = ((PRIndirectReference)this.reader.trailer.get(PdfName.ROOT)).getNumber();
/*  379 */     if (this.append) {
/*  380 */       int[] keys = this.marked.getKeys();
/*  381 */       for (int k = 0; k < keys.length; k++) {
/*  382 */         int j = keys[k];
/*  383 */         PdfObject obj = this.reader.getPdfObjectRelease(j);
/*  384 */         if ((obj != null) && (skipInfo != j) && (j < this.initialXrefSize)) {
/*  385 */           addToBody(obj, obj.getIndRef(), j != rootN);
/*      */         }
/*      */       }
/*  388 */       for (int k = this.initialXrefSize; k < this.reader.getXrefSize(); k++) {
/*  389 */         PdfObject obj = this.reader.getPdfObject(k);
/*  390 */         if (obj != null)
/*  391 */           addToBody(obj, getNewObjectNumber(this.reader, k, 0));
/*      */       }
/*      */     }
/*      */     else
/*      */     {
/*  396 */       for (int k = 1; k < this.reader.getXrefSize(); k++) {
/*  397 */         PdfObject obj = this.reader.getPdfObjectRelease(k);
/*  398 */         if ((obj != null) && (skipInfo != k)) {
/*  399 */           addToBody(obj, getNewObjectNumber(this.reader, k, 0), k != rootN);
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/*  404 */     PdfIndirectReference encryption = null;
/*  405 */     PdfObject fileID = null;
/*  406 */     if (this.crypto != null) {
/*  407 */       if (this.append) {
/*  408 */         encryption = this.reader.getCryptoRef();
/*      */       }
/*      */       else {
/*  411 */         PdfIndirectObject encryptionObject = addToBody(this.crypto.getEncryptionDictionary(), false);
/*  412 */         encryption = encryptionObject.getIndirectReference();
/*      */       }
/*  414 */       fileID = this.crypto.getFileID(true);
/*      */     }
/*      */     else {
/*  417 */       PdfArray IDs = this.reader.trailer.getAsArray(PdfName.ID);
/*  418 */       if ((IDs != null) && (IDs.getAsString(0) != null)) {
/*  419 */         fileID = PdfEncryption.createInfoId(IDs.getAsString(0).getBytes(), true);
/*      */       }
/*      */       else {
/*  422 */         fileID = PdfEncryption.createInfoId(PdfEncryption.createDocumentId(), true);
/*      */       }
/*      */     }
/*  425 */     PRIndirectReference iRoot = (PRIndirectReference)this.reader.trailer.get(PdfName.ROOT);
/*  426 */     PdfIndirectReference root = new PdfIndirectReference(0, getNewObjectNumber(this.reader, iRoot.getNumber(), 0));
/*      */ 
/*  428 */     this.body.writeCrossReferenceTable(this.os, root, info, encryption, fileID, this.prevxref);
/*  429 */     if (this.fullCompression) {
/*  430 */       writeKeyInfo(this.os);
/*  431 */       this.os.write(getISOBytes("startxref\n"));
/*  432 */       this.os.write(getISOBytes(String.valueOf(this.body.offset())));
/*  433 */       this.os.write(getISOBytes("\n%%EOF\n"));
/*      */     }
/*      */     else {
/*  436 */       PdfWriter.PdfTrailer trailer = new PdfWriter.PdfTrailer(this.body.size(), this.body.offset(), root, info, encryption, fileID, this.prevxref);
/*      */ 
/*  442 */       trailer.toPdf(this, this.os);
/*      */     }
/*  444 */     this.os.flush();
/*  445 */     if (isCloseStream())
/*  446 */       this.os.close();
/*  447 */     getCounter().written(this.os.getCounter());
/*      */   }
/*      */ 
/*      */   void applyRotation(PdfDictionary pageN, ByteBuffer out) {
/*  451 */     if (!this.rotateContents)
/*  452 */       return;
/*  453 */     Rectangle page = this.reader.getPageSizeWithRotation(pageN);
/*  454 */     int rotation = page.getRotation();
/*  455 */     switch (rotation) {
/*      */     case 90:
/*  457 */       out.append(PdfContents.ROTATE90);
/*  458 */       out.append(page.getTop());
/*  459 */       out.append(' ').append('0').append(PdfContents.ROTATEFINAL);
/*  460 */       break;
/*      */     case 180:
/*  462 */       out.append(PdfContents.ROTATE180);
/*  463 */       out.append(page.getRight());
/*  464 */       out.append(' ');
/*  465 */       out.append(page.getTop());
/*  466 */       out.append(PdfContents.ROTATEFINAL);
/*  467 */       break;
/*      */     case 270:
/*  469 */       out.append(PdfContents.ROTATE270);
/*  470 */       out.append('0').append(' ');
/*  471 */       out.append(page.getRight());
/*  472 */       out.append(PdfContents.ROTATEFINAL);
/*      */     }
/*      */   }
/*      */ 
/*      */   protected void alterContents() throws IOException
/*      */   {
/*  478 */     for (Object element : this.pagesToContent.values()) {
/*  479 */       PageStamp ps = (PageStamp)element;
/*  480 */       PdfDictionary pageN = ps.pageN;
/*  481 */       markUsed(pageN);
/*  482 */       PdfArray ar = null;
/*  483 */       PdfObject content = PdfReader.getPdfObject(pageN.get(PdfName.CONTENTS), pageN);
/*  484 */       if (content == null) {
/*  485 */         ar = new PdfArray();
/*  486 */         pageN.put(PdfName.CONTENTS, ar);
/*      */       }
/*  488 */       else if (content.isArray()) {
/*  489 */         ar = (PdfArray)content;
/*  490 */         markUsed(ar);
/*      */       }
/*  492 */       else if (content.isStream()) {
/*  493 */         ar = new PdfArray();
/*  494 */         ar.add(pageN.get(PdfName.CONTENTS));
/*  495 */         pageN.put(PdfName.CONTENTS, ar);
/*      */       }
/*      */       else {
/*  498 */         ar = new PdfArray();
/*  499 */         pageN.put(PdfName.CONTENTS, ar);
/*      */       }
/*  501 */       ByteBuffer out = new ByteBuffer();
/*  502 */       if (ps.under != null) {
/*  503 */         out.append(PdfContents.SAVESTATE);
/*  504 */         applyRotation(pageN, out);
/*  505 */         out.append(ps.under.getInternalBuffer());
/*  506 */         out.append(PdfContents.RESTORESTATE);
/*      */       }
/*  508 */       if (ps.over != null)
/*  509 */         out.append(PdfContents.SAVESTATE);
/*  510 */       PdfStream stream = new PdfStream(out.toByteArray());
/*  511 */       stream.flateCompress(this.compressionLevel);
/*  512 */       ar.addFirst(addToBody(stream).getIndirectReference());
/*  513 */       out.reset();
/*  514 */       if (ps.over != null) {
/*  515 */         out.append(' ');
/*  516 */         out.append(PdfContents.RESTORESTATE);
/*  517 */         ByteBuffer buf = ps.over.getInternalBuffer();
/*  518 */         out.append(buf.getBuffer(), 0, ps.replacePoint);
/*  519 */         out.append(PdfContents.SAVESTATE);
/*  520 */         applyRotation(pageN, out);
/*  521 */         out.append(buf.getBuffer(), ps.replacePoint, buf.size() - ps.replacePoint);
/*  522 */         out.append(PdfContents.RESTORESTATE);
/*  523 */         stream = new PdfStream(out.toByteArray());
/*  524 */         stream.flateCompress(this.compressionLevel);
/*  525 */         ar.add(addToBody(stream).getIndirectReference());
/*      */       }
/*  527 */       alterResources(ps);
/*      */     }
/*      */   }
/*      */ 
/*      */   void alterResources(PageStamp ps) {
/*  532 */     ps.pageN.put(PdfName.RESOURCES, ps.pageResources.getResources());
/*      */   }
/*      */ 
/*      */   protected int getNewObjectNumber(PdfReader reader, int number, int generation)
/*      */   {
/*  537 */     IntHashtable ref = (IntHashtable)this.readers2intrefs.get(reader);
/*  538 */     if (ref != null) {
/*  539 */       int n = ref.get(number);
/*  540 */       if (n == 0) {
/*  541 */         n = getIndirectReferenceNumber();
/*  542 */         ref.put(number, n);
/*      */       }
/*  544 */       return n;
/*      */     }
/*  546 */     if (this.currentPdfReaderInstance == null) {
/*  547 */       if ((this.append) && (number < this.initialXrefSize))
/*  548 */         return number;
/*  549 */       int n = this.myXref.get(number);
/*  550 */       if (n == 0) {
/*  551 */         n = getIndirectReferenceNumber();
/*  552 */         this.myXref.put(number, n);
/*      */       }
/*  554 */       return n;
/*      */     }
/*      */ 
/*  557 */     return this.currentPdfReaderInstance.getNewObjectNumber(number, generation);
/*      */   }
/*      */ 
/*      */   RandomAccessFileOrArray getReaderFile(PdfReader reader)
/*      */   {
/*  562 */     if (this.readers2intrefs.containsKey(reader)) {
/*  563 */       RandomAccessFileOrArray raf = (RandomAccessFileOrArray)this.readers2file.get(reader);
/*  564 */       if (raf != null)
/*  565 */         return raf;
/*  566 */       return reader.getSafeFile();
/*      */     }
/*  568 */     if (this.currentPdfReaderInstance == null) {
/*  569 */       return this.file;
/*      */     }
/*  571 */     return this.currentPdfReaderInstance.getReaderFile();
/*      */   }
/*      */ 
/*      */   public void registerReader(PdfReader reader, boolean openFile)
/*      */     throws IOException
/*      */   {
/*  580 */     if (this.readers2intrefs.containsKey(reader))
/*  581 */       return;
/*  582 */     this.readers2intrefs.put(reader, new IntHashtable());
/*  583 */     if (openFile) {
/*  584 */       RandomAccessFileOrArray raf = reader.getSafeFile();
/*  585 */       this.readers2file.put(reader, raf);
/*  586 */       raf.reOpen();
/*      */     }
/*      */   }
/*      */ 
/*      */   public void unRegisterReader(PdfReader reader)
/*      */   {
/*  594 */     if (!this.readers2intrefs.containsKey(reader))
/*  595 */       return;
/*  596 */     this.readers2intrefs.remove(reader);
/*  597 */     RandomAccessFileOrArray raf = (RandomAccessFileOrArray)this.readers2file.get(reader);
/*  598 */     if (raf == null)
/*  599 */       return;
/*  600 */     this.readers2file.remove(reader);
/*      */     try { raf.close(); } catch (Exception e) {
/*      */     }
/*      */   }
/*      */ 
/*  605 */   static void findAllObjects(PdfReader reader, PdfObject obj, IntHashtable hits) { if (obj == null)
/*  606 */       return;
/*  607 */     switch (obj.type()) {
/*      */     case 10:
/*  609 */       PRIndirectReference iref = (PRIndirectReference)obj;
/*  610 */       if (reader != iref.getReader())
/*  611 */         return;
/*  612 */       if (hits.containsKey(iref.getNumber()))
/*  613 */         return;
/*  614 */       hits.put(iref.getNumber(), 1);
/*  615 */       findAllObjects(reader, PdfReader.getPdfObject(obj), hits);
/*  616 */       return;
/*      */     case 5:
/*  618 */       PdfArray a = (PdfArray)obj;
/*  619 */       for (int k = 0; k < a.size(); k++) {
/*  620 */         findAllObjects(reader, a.getPdfObject(k), hits);
/*      */       }
/*  622 */       return;
/*      */     case 6:
/*      */     case 7:
/*  625 */       PdfDictionary dic = (PdfDictionary)obj;
/*  626 */       for (Object element : dic.getKeys()) {
/*  627 */         PdfName name = (PdfName)element;
/*  628 */         findAllObjects(reader, dic.get(name), hits);
/*      */       }
/*  630 */       return;
/*      */     case 8:
/*      */     case 9:
/*      */     }
/*      */   }
/*      */ 
/*      */   public void addComments(FdfReader fdf)
/*      */     throws IOException
/*      */   {
/*  639 */     if (this.readers2intrefs.containsKey(fdf))
/*  640 */       return;
/*  641 */     PdfDictionary catalog = fdf.getCatalog();
/*  642 */     catalog = catalog.getAsDict(PdfName.FDF);
/*  643 */     if (catalog == null)
/*  644 */       return;
/*  645 */     PdfArray annots = catalog.getAsArray(PdfName.ANNOTS);
/*  646 */     if ((annots == null) || (annots.size() == 0))
/*  647 */       return;
/*  648 */     registerReader(fdf, false);
/*  649 */     IntHashtable hits = new IntHashtable();
/*  650 */     HashMap irt = new HashMap();
/*  651 */     ArrayList an = new ArrayList();
/*  652 */     for (int k = 0; k < annots.size(); k++) {
/*  653 */       PdfObject obj = annots.getPdfObject(k);
/*  654 */       PdfDictionary annot = (PdfDictionary)PdfReader.getPdfObject(obj);
/*  655 */       PdfNumber page = annot.getAsNumber(PdfName.PAGE);
/*  656 */       if ((page != null) && (page.intValue() < this.reader.getNumberOfPages()))
/*      */       {
/*  658 */         findAllObjects(fdf, obj, hits);
/*  659 */         an.add(obj);
/*  660 */         if (obj.type() == 10) {
/*  661 */           PdfObject nm = PdfReader.getPdfObject(annot.get(PdfName.NM));
/*  662 */           if ((nm != null) && (nm.type() == 3))
/*  663 */             irt.put(nm.toString(), obj); 
/*      */         }
/*      */       }
/*      */     }
/*  666 */     int[] arhits = hits.getKeys();
/*  667 */     for (int k = 0; k < arhits.length; k++) {
/*  668 */       int n = arhits[k];
/*  669 */       PdfObject obj = fdf.getPdfObject(n);
/*  670 */       if (obj.type() == 6) {
/*  671 */         PdfObject str = PdfReader.getPdfObject(((PdfDictionary)obj).get(PdfName.IRT));
/*  672 */         if ((str != null) && (str.type() == 3)) {
/*  673 */           PdfObject i = (PdfObject)irt.get(str.toString());
/*  674 */           if (i != null) {
/*  675 */             PdfDictionary dic2 = new PdfDictionary();
/*  676 */             dic2.merge((PdfDictionary)obj);
/*  677 */             dic2.put(PdfName.IRT, i);
/*  678 */             obj = dic2;
/*      */           }
/*      */         }
/*      */       }
/*  682 */       addToBody(obj, getNewObjectNumber(fdf, n, 0));
/*      */     }
/*  684 */     for (int k = 0; k < an.size(); k++) {
/*  685 */       PdfObject obj = (PdfObject)an.get(k);
/*  686 */       PdfDictionary annot = (PdfDictionary)PdfReader.getPdfObject(obj);
/*  687 */       PdfNumber page = annot.getAsNumber(PdfName.PAGE);
/*  688 */       PdfDictionary dic = this.reader.getPageN(page.intValue() + 1);
/*  689 */       PdfArray annotsp = (PdfArray)PdfReader.getPdfObject(dic.get(PdfName.ANNOTS), dic);
/*  690 */       if (annotsp == null) {
/*  691 */         annotsp = new PdfArray();
/*  692 */         dic.put(PdfName.ANNOTS, annotsp);
/*  693 */         markUsed(dic);
/*      */       }
/*  695 */       markUsed(annotsp);
/*  696 */       annotsp.add(obj);
/*      */     }
/*      */   }
/*      */ 
/*      */   PageStamp getPageStamp(int pageNum) {
/*  701 */     PdfDictionary pageN = this.reader.getPageN(pageNum);
/*  702 */     PageStamp ps = (PageStamp)this.pagesToContent.get(pageN);
/*  703 */     if (ps == null) {
/*  704 */       ps = new PageStamp(this, this.reader, pageN);
/*  705 */       this.pagesToContent.put(pageN, ps);
/*      */     }
/*  707 */     return ps;
/*      */   }
/*      */ 
/*      */   PdfContentByte getUnderContent(int pageNum) {
/*  711 */     if ((pageNum < 1) || (pageNum > this.reader.getNumberOfPages()))
/*  712 */       return null;
/*  713 */     PageStamp ps = getPageStamp(pageNum);
/*  714 */     if (ps.under == null)
/*  715 */       ps.under = new StampContent(this, ps);
/*  716 */     return ps.under;
/*      */   }
/*      */ 
/*      */   PdfContentByte getOverContent(int pageNum) {
/*  720 */     if ((pageNum < 1) || (pageNum > this.reader.getNumberOfPages()))
/*  721 */       return null;
/*  722 */     PageStamp ps = getPageStamp(pageNum);
/*  723 */     if (ps.over == null)
/*  724 */       ps.over = new StampContent(this, ps);
/*  725 */     return ps.over;
/*      */   }
/*      */ 
/*      */   void correctAcroFieldPages(int page) {
/*  729 */     if (this.acroFields == null)
/*  730 */       return;
/*  731 */     if (page > this.reader.getNumberOfPages())
/*  732 */       return;
/*  733 */     Map fields = this.acroFields.getFields();
/*  734 */     for (AcroFields.Item item : fields.values())
/*  735 */       for (int k = 0; k < item.size(); k++) {
/*  736 */         int p = item.getPage(k).intValue();
/*  737 */         if (p >= page)
/*  738 */           item.forcePage(k, p + 1);
/*      */       }
/*      */   }
/*      */ 
/*      */   private static void moveRectangle(PdfDictionary dic2, PdfReader r, int pageImported, PdfName key, String name)
/*      */   {
/*  744 */     Rectangle m = r.getBoxSize(pageImported, name);
/*  745 */     if (m == null)
/*  746 */       dic2.remove(key);
/*      */     else
/*  748 */       dic2.put(key, new PdfRectangle(m));
/*      */   }
/*      */ 
/*      */   void replacePage(PdfReader r, int pageImported, int pageReplaced) {
/*  752 */     PdfDictionary pageN = this.reader.getPageN(pageReplaced);
/*  753 */     if (this.pagesToContent.containsKey(pageN))
/*  754 */       throw new IllegalStateException(MessageLocalization.getComposedMessage("this.page.cannot.be.replaced.new.content.was.already.added", new Object[0]));
/*  755 */     PdfImportedPage p = getImportedPage(r, pageImported);
/*  756 */     PdfDictionary dic2 = this.reader.getPageNRelease(pageReplaced);
/*  757 */     dic2.remove(PdfName.RESOURCES);
/*  758 */     dic2.remove(PdfName.CONTENTS);
/*  759 */     moveRectangle(dic2, r, pageImported, PdfName.MEDIABOX, "media");
/*  760 */     moveRectangle(dic2, r, pageImported, PdfName.CROPBOX, "crop");
/*  761 */     moveRectangle(dic2, r, pageImported, PdfName.TRIMBOX, "trim");
/*  762 */     moveRectangle(dic2, r, pageImported, PdfName.ARTBOX, "art");
/*  763 */     moveRectangle(dic2, r, pageImported, PdfName.BLEEDBOX, "bleed");
/*  764 */     dic2.put(PdfName.ROTATE, new PdfNumber(r.getPageRotation(pageImported)));
/*  765 */     PdfContentByte cb = getOverContent(pageReplaced);
/*  766 */     cb.addTemplate(p, 0.0F, 0.0F);
/*  767 */     PageStamp ps = (PageStamp)this.pagesToContent.get(pageN);
/*  768 */     ps.replacePoint = ps.over.getInternalBuffer().size();
/*      */   }
/*      */ 
/*      */   void insertPage(int pageNumber, Rectangle mediabox) {
/*  772 */     Rectangle media = new Rectangle(mediabox);
/*  773 */     int rotation = media.getRotation() % 360;
/*  774 */     PdfDictionary page = new PdfDictionary(PdfName.PAGE);
/*  775 */     page.put(PdfName.RESOURCES, new PdfDictionary());
/*  776 */     page.put(PdfName.ROTATE, new PdfNumber(rotation));
/*  777 */     page.put(PdfName.MEDIABOX, new PdfRectangle(media, rotation));
/*  778 */     PRIndirectReference pref = this.reader.addPdfObject(page);
/*      */     PRIndirectReference parentRef;
/*      */     PdfDictionary parent;
/*  781 */     if (pageNumber > this.reader.getNumberOfPages()) {
/*  782 */       PdfDictionary lastPage = this.reader.getPageNRelease(this.reader.getNumberOfPages());
/*  783 */       PRIndirectReference parentRef = (PRIndirectReference)lastPage.get(PdfName.PARENT);
/*  784 */       parentRef = new PRIndirectReference(this.reader, parentRef.getNumber());
/*  785 */       PdfDictionary parent = (PdfDictionary)PdfReader.getPdfObject(parentRef);
/*  786 */       PdfArray kids = (PdfArray)PdfReader.getPdfObject(parent.get(PdfName.KIDS), parent);
/*  787 */       kids.add(pref);
/*  788 */       markUsed(kids);
/*  789 */       this.reader.pageRefs.insertPage(pageNumber, pref);
/*      */     }
/*      */     else {
/*  792 */       if (pageNumber < 1)
/*  793 */         pageNumber = 1;
/*  794 */       PdfDictionary firstPage = this.reader.getPageN(pageNumber);
/*  795 */       PRIndirectReference firstPageRef = this.reader.getPageOrigRef(pageNumber);
/*  796 */       this.reader.releasePage(pageNumber);
/*  797 */       parentRef = (PRIndirectReference)firstPage.get(PdfName.PARENT);
/*  798 */       parentRef = new PRIndirectReference(this.reader, parentRef.getNumber());
/*  799 */       parent = (PdfDictionary)PdfReader.getPdfObject(parentRef);
/*  800 */       PdfArray kids = (PdfArray)PdfReader.getPdfObject(parent.get(PdfName.KIDS), parent);
/*  801 */       int len = kids.size();
/*  802 */       int num = firstPageRef.getNumber();
/*  803 */       for (int k = 0; k < len; k++) {
/*  804 */         PRIndirectReference cur = (PRIndirectReference)kids.getPdfObject(k);
/*  805 */         if (num == cur.getNumber()) {
/*  806 */           kids.add(k, pref);
/*  807 */           break;
/*      */         }
/*      */       }
/*  810 */       if (len == kids.size())
/*  811 */         throw new RuntimeException(MessageLocalization.getComposedMessage("internal.inconsistence", new Object[0]));
/*  812 */       markUsed(kids);
/*  813 */       this.reader.pageRefs.insertPage(pageNumber, pref);
/*  814 */       correctAcroFieldPages(pageNumber);
/*      */     }
/*  816 */     page.put(PdfName.PARENT, parentRef);
/*  817 */     while (parent != null) {
/*  818 */       markUsed(parent);
/*  819 */       PdfNumber count = (PdfNumber)PdfReader.getPdfObjectRelease(parent.get(PdfName.COUNT));
/*  820 */       parent.put(PdfName.COUNT, new PdfNumber(count.intValue() + 1));
/*  821 */       parent = parent.getAsDict(PdfName.PARENT);
/*      */     }
/*      */   }
/*      */ 
/*      */   boolean isRotateContents()
/*      */   {
/*  830 */     return this.rotateContents;
/*      */   }
/*      */ 
/*      */   void setRotateContents(boolean rotateContents)
/*      */   {
/*  838 */     this.rotateContents = rotateContents;
/*      */   }
/*      */ 
/*      */   boolean isContentWritten() {
/*  842 */     return this.body.size() > 1;
/*      */   }
/*      */ 
/*      */   AcroFields getAcroFields() {
/*  846 */     if (this.acroFields == null) {
/*  847 */       this.acroFields = new AcroFields(this.reader, this);
/*      */     }
/*  849 */     return this.acroFields;
/*      */   }
/*      */ 
/*      */   void setFormFlattening(boolean flat) {
/*  853 */     this.flat = flat;
/*      */   }
/*      */ 
/*      */   void setFreeTextFlattening(boolean flat) {
/*  857 */     this.flatFreeText = flat;
/*      */   }
/*      */ 
/*      */   boolean partialFormFlattening(String name) {
/*  861 */     getAcroFields();
/*  862 */     if (this.acroFields.getXfa().isXfaPresent())
/*  863 */       throw new UnsupportedOperationException(MessageLocalization.getComposedMessage("partial.form.flattening.is.not.supported.with.xfa.forms", new Object[0]));
/*  864 */     if (!this.acroFields.getFields().containsKey(name))
/*  865 */       return false;
/*  866 */     this.partialFlattening.add(name);
/*  867 */     return true;
/*      */   }
/*      */ 
/*      */   protected void flatFields() {
/*  871 */     if (this.append)
/*  872 */       throw new IllegalArgumentException(MessageLocalization.getComposedMessage("field.flattening.is.not.supported.in.append.mode", new Object[0]));
/*  873 */     getAcroFields();
/*  874 */     Map fields = this.acroFields.getFields();
/*  875 */     if ((this.fieldsAdded) && (this.partialFlattening.isEmpty())) {
/*  876 */       for (String s : fields.keySet()) {
/*  877 */         this.partialFlattening.add(s);
/*      */       }
/*      */     }
/*  880 */     PdfDictionary acroForm = this.reader.getCatalog().getAsDict(PdfName.ACROFORM);
/*  881 */     PdfArray acroFds = null;
/*  882 */     if (acroForm != null) {
/*  883 */       acroFds = (PdfArray)PdfReader.getPdfObject(acroForm.get(PdfName.FIELDS), acroForm);
/*      */     }
/*  885 */     for (Map.Entry entry : fields.entrySet()) {
/*  886 */       String name = (String)entry.getKey();
/*  887 */       if ((this.partialFlattening.isEmpty()) || (this.partialFlattening.contains(name)))
/*      */       {
/*  889 */         AcroFields.Item item = (AcroFields.Item)entry.getValue();
/*  890 */         for (int k = 0; k < item.size(); k++) {
/*  891 */           PdfDictionary merged = item.getMerged(k);
/*  892 */           PdfNumber ff = merged.getAsNumber(PdfName.F);
/*  893 */           int flags = 0;
/*  894 */           if (ff != null)
/*  895 */             flags = ff.intValue();
/*  896 */           int page = item.getPage(k).intValue();
/*  897 */           if (page >= 1)
/*      */           {
/*  899 */             PdfDictionary appDic = merged.getAsDict(PdfName.AP);
/*  900 */             if ((appDic != null) && ((flags & 0x4) != 0) && ((flags & 0x2) == 0)) {
/*  901 */               PdfObject obj = appDic.get(PdfName.N);
/*  902 */               PdfAppearance app = null;
/*  903 */               if (obj != null) {
/*  904 */                 PdfObject objReal = PdfReader.getPdfObject(obj);
/*  905 */                 if (((obj instanceof PdfIndirectReference)) && (!obj.isIndirect())) {
/*  906 */                   app = new PdfAppearance((PdfIndirectReference)obj);
/*  907 */                 } else if ((objReal instanceof PdfStream)) {
/*  908 */                   ((PdfDictionary)objReal).put(PdfName.SUBTYPE, PdfName.FORM);
/*  909 */                   app = new PdfAppearance((PdfIndirectReference)obj);
/*      */                 }
/*  912 */                 else if ((objReal != null) && (objReal.isDictionary())) {
/*  913 */                   PdfName as = merged.getAsName(PdfName.AS);
/*  914 */                   if (as != null) {
/*  915 */                     PdfIndirectReference iref = (PdfIndirectReference)((PdfDictionary)objReal).get(as);
/*  916 */                     if (iref != null) {
/*  917 */                       app = new PdfAppearance(iref);
/*  918 */                       if (iref.isIndirect()) {
/*  919 */                         objReal = PdfReader.getPdfObject(iref);
/*  920 */                         ((PdfDictionary)objReal).put(PdfName.SUBTYPE, PdfName.FORM);
/*      */                       }
/*      */                     }
/*      */                   }
/*      */                 }
/*      */               }
/*      */ 
/*  927 */               if (app != null) {
/*  928 */                 Rectangle box = PdfReader.getNormalizedRectangle(merged.getAsArray(PdfName.RECT));
/*  929 */                 PdfContentByte cb = getOverContent(page);
/*  930 */                 cb.setLiteral("Q ");
/*  931 */                 cb.addTemplate(app, box.getLeft(), box.getBottom());
/*  932 */                 cb.setLiteral("q ");
/*      */               }
/*      */             }
/*  935 */             if (!this.partialFlattening.isEmpty())
/*      */             {
/*  937 */               PdfDictionary pageDic = this.reader.getPageN(page);
/*  938 */               PdfArray annots = pageDic.getAsArray(PdfName.ANNOTS);
/*  939 */               if (annots != null)
/*      */               {
/*  941 */                 for (int idx = 0; idx < annots.size(); idx++) {
/*  942 */                   PdfObject ran = annots.getPdfObject(idx);
/*  943 */                   if (ran.isIndirect())
/*      */                   {
/*  945 */                     PdfObject ran2 = item.getWidgetRef(k);
/*  946 */                     if (ran2.isIndirect())
/*      */                     {
/*  948 */                       if (((PRIndirectReference)ran).getNumber() == ((PRIndirectReference)ran2).getNumber()) {
/*  949 */                         annots.remove(idx--);
/*  950 */                         PRIndirectReference wdref = (PRIndirectReference)ran2;
/*      */                         while (true) {
/*  952 */                           PdfDictionary wd = (PdfDictionary)PdfReader.getPdfObject(wdref);
/*  953 */                           PRIndirectReference parentRef = (PRIndirectReference)wd.get(PdfName.PARENT);
/*  954 */                           PdfReader.killIndirect(wdref);
/*  955 */                           if (parentRef == null) {
/*  956 */                             for (int fr = 0; fr < acroFds.size(); fr++) {
/*  957 */                               PdfObject h = acroFds.getPdfObject(fr);
/*  958 */                               if ((h.isIndirect()) && (((PRIndirectReference)h).getNumber() == wdref.getNumber())) {
/*  959 */                                 acroFds.remove(fr);
/*  960 */                                 fr--;
/*      */                               }
/*      */                             }
/*  963 */                             break;
/*      */                           }
/*  965 */                           PdfDictionary parent = (PdfDictionary)PdfReader.getPdfObject(parentRef);
/*  966 */                           PdfArray kids = parent.getAsArray(PdfName.KIDS);
/*  967 */                           for (int fr = 0; fr < kids.size(); fr++) {
/*  968 */                             PdfObject h = kids.getPdfObject(fr);
/*  969 */                             if ((h.isIndirect()) && (((PRIndirectReference)h).getNumber() == wdref.getNumber())) {
/*  970 */                               kids.remove(fr);
/*  971 */                               fr--;
/*      */                             }
/*      */                           }
/*  974 */                           if (!kids.isEmpty())
/*      */                             break;
/*  976 */                           wdref = parentRef;
/*      */                         }
/*      */                       }
/*      */                     }
/*      */                   }
/*      */                 }
/*  980 */                 if (annots.isEmpty()) {
/*  981 */                   PdfReader.killIndirect(pageDic.get(PdfName.ANNOTS));
/*  982 */                   pageDic.remove(PdfName.ANNOTS);
/*      */                 }
/*      */               }
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*  986 */     if ((!this.fieldsAdded) && (this.partialFlattening.isEmpty())) {
/*  987 */       for (int page = 1; page <= this.reader.getNumberOfPages(); page++) {
/*  988 */         PdfDictionary pageDic = this.reader.getPageN(page);
/*  989 */         PdfArray annots = pageDic.getAsArray(PdfName.ANNOTS);
/*  990 */         if (annots != null)
/*      */         {
/*  992 */           for (int idx = 0; idx < annots.size(); idx++) {
/*  993 */             PdfObject annoto = annots.getDirectObject(idx);
/*  994 */             if ((!(annoto instanceof PdfIndirectReference)) || (annoto.isIndirect()))
/*      */             {
/*  996 */               if ((!annoto.isDictionary()) || (PdfName.WIDGET.equals(((PdfDictionary)annoto).get(PdfName.SUBTYPE)))) {
/*  997 */                 annots.remove(idx);
/*  998 */                 idx--;
/*      */               }
/*      */             }
/*      */           }
/* 1001 */           if (annots.isEmpty()) {
/* 1002 */             PdfReader.killIndirect(pageDic.get(PdfName.ANNOTS));
/* 1003 */             pageDic.remove(PdfName.ANNOTS);
/*      */           }
/*      */         }
/*      */       }
/* 1006 */       eliminateAcroformObjects();
/*      */     }
/*      */   }
/*      */ 
/*      */   void eliminateAcroformObjects() {
/* 1011 */     PdfObject acro = this.reader.getCatalog().get(PdfName.ACROFORM);
/* 1012 */     if (acro == null)
/* 1013 */       return;
/* 1014 */     PdfDictionary acrodic = (PdfDictionary)PdfReader.getPdfObject(acro);
/* 1015 */     this.reader.killXref(acrodic.get(PdfName.XFA));
/* 1016 */     acrodic.remove(PdfName.XFA);
/* 1017 */     PdfObject iFields = acrodic.get(PdfName.FIELDS);
/* 1018 */     if (iFields != null) {
/* 1019 */       PdfDictionary kids = new PdfDictionary();
/* 1020 */       kids.put(PdfName.KIDS, iFields);
/* 1021 */       sweepKids(kids);
/* 1022 */       PdfReader.killIndirect(iFields);
/* 1023 */       acrodic.put(PdfName.FIELDS, new PdfArray());
/*      */     }
/* 1025 */     acrodic.remove(PdfName.SIGFLAGS);
/* 1026 */     acrodic.remove(PdfName.NEEDAPPEARANCES);
/* 1027 */     acrodic.remove(PdfName.DR);
/*      */   }
/*      */ 
/*      */   void sweepKids(PdfObject obj)
/*      */   {
/* 1033 */     PdfObject oo = PdfReader.killIndirect(obj);
/* 1034 */     if ((oo == null) || (!oo.isDictionary()))
/* 1035 */       return;
/* 1036 */     PdfDictionary dic = (PdfDictionary)oo;
/* 1037 */     PdfArray kids = (PdfArray)PdfReader.killIndirect(dic.get(PdfName.KIDS));
/* 1038 */     if (kids == null)
/* 1039 */       return;
/* 1040 */     for (int k = 0; k < kids.size(); k++)
/* 1041 */       sweepKids(kids.getPdfObject(k));
/*      */   }
/*      */ 
/*      */   protected void flatFreeTextFields()
/*      */   {
/* 1047 */     if (this.append) {
/* 1048 */       throw new IllegalArgumentException(MessageLocalization.getComposedMessage("freetext.flattening.is.not.supported.in.append.mode", new Object[0]));
/*      */     }
/* 1050 */     for (int page = 1; page <= this.reader.getNumberOfPages(); page++)
/*      */     {
/* 1052 */       PdfDictionary pageDic = this.reader.getPageN(page);
/* 1053 */       PdfArray annots = pageDic.getAsArray(PdfName.ANNOTS);
/* 1054 */       if (annots != null)
/*      */       {
/* 1056 */         for (int idx = 0; idx < annots.size(); idx++)
/*      */         {
/* 1058 */           PdfObject annoto = annots.getDirectObject(idx);
/* 1059 */           if ((!(annoto instanceof PdfIndirectReference)) || (annoto.isIndirect()))
/*      */           {
/* 1062 */             PdfDictionary annDic = (PdfDictionary)annoto;
/* 1063 */             if (((PdfName)annDic.get(PdfName.SUBTYPE)).equals(PdfName.FREETEXT))
/*      */             {
/* 1065 */               PdfNumber ff = annDic.getAsNumber(PdfName.F);
/* 1066 */               int flags = ff != null ? ff.intValue() : 0;
/*      */ 
/* 1068 */               if (((flags & 0x4) != 0) && ((flags & 0x2) == 0))
/*      */               {
/* 1070 */                 PdfObject obj1 = annDic.get(PdfName.AP);
/* 1071 */                 if (obj1 != null)
/*      */                 {
/* 1073 */                   PdfDictionary appDic = (obj1 instanceof PdfIndirectReference) ? (PdfDictionary)PdfReader.getPdfObject(obj1) : (PdfDictionary)obj1;
/*      */ 
/* 1075 */                   PdfObject obj = appDic.get(PdfName.N);
/* 1076 */                   PdfAppearance app = null;
/* 1077 */                   PdfObject objReal = PdfReader.getPdfObject(obj);
/*      */ 
/* 1079 */                   if (((obj instanceof PdfIndirectReference)) && (!obj.isIndirect())) {
/* 1080 */                     app = new PdfAppearance((PdfIndirectReference)obj);
/* 1081 */                   } else if ((objReal instanceof PdfStream))
/*      */                   {
/* 1083 */                     ((PdfDictionary)objReal).put(PdfName.SUBTYPE, PdfName.FORM);
/* 1084 */                     app = new PdfAppearance((PdfIndirectReference)obj);
/*      */                   }
/* 1088 */                   else if (objReal.isDictionary())
/*      */                   {
/* 1090 */                     PdfName as_p = appDic.getAsName(PdfName.AS);
/* 1091 */                     if (as_p != null)
/*      */                     {
/* 1093 */                       PdfIndirectReference iref = (PdfIndirectReference)((PdfDictionary)objReal).get(as_p);
/* 1094 */                       if (iref != null)
/*      */                       {
/* 1096 */                         app = new PdfAppearance(iref);
/* 1097 */                         if (iref.isIndirect())
/*      */                         {
/* 1099 */                           objReal = PdfReader.getPdfObject(iref);
/* 1100 */                           ((PdfDictionary)objReal).put(PdfName.SUBTYPE, PdfName.FORM);
/*      */                         }
/*      */                       }
/*      */                     }
/*      */                   }
/*      */ 
/* 1106 */                   if (app != null)
/*      */                   {
/* 1108 */                     Rectangle box = PdfReader.getNormalizedRectangle(annDic.getAsArray(PdfName.RECT));
/* 1109 */                     PdfContentByte cb = getOverContent(page);
/* 1110 */                     cb.setLiteral("Q ");
/* 1111 */                     cb.addTemplate(app, box.getLeft(), box.getBottom());
/* 1112 */                     cb.setLiteral("q ");
/*      */                   }
/*      */                 }
/*      */               }
/*      */             }
/*      */           }
/*      */         }
/* 1116 */         for (int idx = 0; idx < annots.size(); idx++)
/*      */         {
/* 1118 */           PdfDictionary annot = annots.getAsDict(idx);
/* 1119 */           if (annot != null)
/*      */           {
/* 1121 */             if (PdfName.FREETEXT.equals(annot.get(PdfName.SUBTYPE)))
/*      */             {
/* 1123 */               annots.remove(idx);
/* 1124 */               idx--;
/*      */             }
/*      */           }
/*      */         }
/* 1128 */         if (annots.isEmpty())
/*      */         {
/* 1130 */           PdfReader.killIndirect(pageDic.get(PdfName.ANNOTS));
/* 1131 */           pageDic.remove(PdfName.ANNOTS);
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   public PdfIndirectReference getPageReference(int page)
/*      */   {
/* 1141 */     PdfIndirectReference ref = this.reader.getPageOrigRef(page);
/* 1142 */     if (ref == null)
/* 1143 */       throw new IllegalArgumentException(MessageLocalization.getComposedMessage("invalid.page.number.1", page));
/* 1144 */     return ref;
/*      */   }
/*      */ 
/*      */   public void addAnnotation(PdfAnnotation annot)
/*      */   {
/* 1152 */     throw new RuntimeException(MessageLocalization.getComposedMessage("unsupported.in.this.context.use.pdfstamper.addannotation", new Object[0]));
/*      */   }
/*      */ 
/*      */   void addDocumentField(PdfIndirectReference ref) {
/* 1156 */     PdfDictionary catalog = this.reader.getCatalog();
/* 1157 */     PdfDictionary acroForm = (PdfDictionary)PdfReader.getPdfObject(catalog.get(PdfName.ACROFORM), catalog);
/* 1158 */     if (acroForm == null) {
/* 1159 */       acroForm = new PdfDictionary();
/* 1160 */       catalog.put(PdfName.ACROFORM, acroForm);
/* 1161 */       markUsed(catalog);
/*      */     }
/* 1163 */     PdfArray fields = (PdfArray)PdfReader.getPdfObject(acroForm.get(PdfName.FIELDS), acroForm);
/* 1164 */     if (fields == null) {
/* 1165 */       fields = new PdfArray();
/* 1166 */       acroForm.put(PdfName.FIELDS, fields);
/* 1167 */       markUsed(acroForm);
/*      */     }
/* 1169 */     if (!acroForm.contains(PdfName.DA)) {
/* 1170 */       acroForm.put(PdfName.DA, new PdfString("/Helv 0 Tf 0 g "));
/* 1171 */       markUsed(acroForm);
/*      */     }
/* 1173 */     fields.add(ref);
/* 1174 */     markUsed(fields);
/*      */   }
/*      */ 
/*      */   protected void addFieldResources() throws IOException {
/* 1178 */     if (this.fieldTemplates.isEmpty())
/* 1179 */       return;
/* 1180 */     PdfDictionary catalog = this.reader.getCatalog();
/* 1181 */     PdfDictionary acroForm = (PdfDictionary)PdfReader.getPdfObject(catalog.get(PdfName.ACROFORM), catalog);
/* 1182 */     if (acroForm == null) {
/* 1183 */       acroForm = new PdfDictionary();
/* 1184 */       catalog.put(PdfName.ACROFORM, acroForm);
/* 1185 */       markUsed(catalog);
/*      */     }
/* 1187 */     PdfDictionary dr = (PdfDictionary)PdfReader.getPdfObject(acroForm.get(PdfName.DR), acroForm);
/* 1188 */     if (dr == null) {
/* 1189 */       dr = new PdfDictionary();
/* 1190 */       acroForm.put(PdfName.DR, dr);
/* 1191 */       markUsed(acroForm);
/*      */     }
/* 1193 */     markUsed(dr);
/* 1194 */     for (PdfTemplate template : this.fieldTemplates) {
/* 1195 */       PdfFormField.mergeResources(dr, (PdfDictionary)template.getResources(), this);
/*      */     }
/*      */ 
/* 1198 */     PdfDictionary fonts = dr.getAsDict(PdfName.FONT);
/* 1199 */     if (fonts == null) {
/* 1200 */       fonts = new PdfDictionary();
/* 1201 */       dr.put(PdfName.FONT, fonts);
/*      */     }
/* 1203 */     if (!fonts.contains(PdfName.HELV)) {
/* 1204 */       PdfDictionary dic = new PdfDictionary(PdfName.FONT);
/* 1205 */       dic.put(PdfName.BASEFONT, PdfName.HELVETICA);
/* 1206 */       dic.put(PdfName.ENCODING, PdfName.WIN_ANSI_ENCODING);
/* 1207 */       dic.put(PdfName.NAME, PdfName.HELV);
/* 1208 */       dic.put(PdfName.SUBTYPE, PdfName.TYPE1);
/* 1209 */       fonts.put(PdfName.HELV, addToBody(dic).getIndirectReference());
/*      */     }
/* 1211 */     if (!fonts.contains(PdfName.ZADB)) {
/* 1212 */       PdfDictionary dic = new PdfDictionary(PdfName.FONT);
/* 1213 */       dic.put(PdfName.BASEFONT, PdfName.ZAPFDINGBATS);
/* 1214 */       dic.put(PdfName.NAME, PdfName.ZADB);
/* 1215 */       dic.put(PdfName.SUBTYPE, PdfName.TYPE1);
/* 1216 */       fonts.put(PdfName.ZADB, addToBody(dic).getIndirectReference());
/*      */     }
/* 1218 */     if (acroForm.get(PdfName.DA) == null) {
/* 1219 */       acroForm.put(PdfName.DA, new PdfString("/Helv 0 Tf 0 g "));
/* 1220 */       markUsed(acroForm);
/*      */     }
/*      */   }
/*      */ 
/*      */   void expandFields(PdfFormField field, ArrayList<PdfAnnotation> allAnnots) {
/* 1225 */     allAnnots.add(field);
/* 1226 */     ArrayList kids = field.getKids();
/* 1227 */     if (kids != null)
/* 1228 */       for (int k = 0; k < kids.size(); k++)
/* 1229 */         expandFields((PdfFormField)kids.get(k), allAnnots);
/*      */   }
/*      */ 
/*      */   void addAnnotation(PdfAnnotation annot, PdfDictionary pageN)
/*      */   {
/*      */     try {
/* 1235 */       ArrayList allAnnots = new ArrayList();
/* 1236 */       if (annot.isForm()) {
/* 1237 */         this.fieldsAdded = true;
/* 1238 */         getAcroFields();
/* 1239 */         PdfFormField field = (PdfFormField)annot;
/* 1240 */         if (field.getParent() != null)
/* 1241 */           return;
/* 1242 */         expandFields(field, allAnnots);
/*      */       }
/*      */       else {
/* 1245 */         allAnnots.add(annot);
/* 1246 */       }for (int k = 0; k < allAnnots.size(); k++) {
/* 1247 */         annot = (PdfAnnotation)allAnnots.get(k);
/* 1248 */         if (annot.getPlaceInPage() > 0)
/* 1249 */           pageN = this.reader.getPageN(annot.getPlaceInPage());
/* 1250 */         if (annot.isForm()) {
/* 1251 */           if (!annot.isUsed()) {
/* 1252 */             HashSet templates = annot.getTemplates();
/* 1253 */             if (templates != null)
/* 1254 */               this.fieldTemplates.addAll(templates);
/*      */           }
/* 1256 */           PdfFormField field = (PdfFormField)annot;
/* 1257 */           if (field.getParent() == null)
/* 1258 */             addDocumentField(field.getIndirectReference());
/*      */         }
/* 1260 */         if (annot.isAnnotation()) {
/* 1261 */           PdfObject pdfobj = PdfReader.getPdfObject(pageN.get(PdfName.ANNOTS), pageN);
/* 1262 */           PdfArray annots = null;
/* 1263 */           if ((pdfobj == null) || (!pdfobj.isArray())) {
/* 1264 */             annots = new PdfArray();
/* 1265 */             pageN.put(PdfName.ANNOTS, annots);
/* 1266 */             markUsed(pageN);
/*      */           }
/*      */           else {
/* 1269 */             annots = (PdfArray)pdfobj;
/* 1270 */           }annots.add(annot.getIndirectReference());
/* 1271 */           markUsed(annots);
/* 1272 */           if (!annot.isUsed()) {
/* 1273 */             PdfRectangle rect = (PdfRectangle)annot.get(PdfName.RECT);
/* 1274 */             if ((rect != null) && ((rect.left() != 0.0F) || (rect.right() != 0.0F) || (rect.top() != 0.0F) || (rect.bottom() != 0.0F))) {
/* 1275 */               int rotation = this.reader.getPageRotation(pageN);
/* 1276 */               Rectangle pageSize = this.reader.getPageSizeWithRotation(pageN);
/* 1277 */               switch (rotation) {
/*      */               case 90:
/* 1279 */                 annot.put(PdfName.RECT, new PdfRectangle(pageSize.getTop() - rect.top(), rect.right(), pageSize.getTop() - rect.bottom(), rect.left()));
/*      */ 
/* 1284 */                 break;
/*      */               case 180:
/* 1286 */                 annot.put(PdfName.RECT, new PdfRectangle(pageSize.getRight() - rect.left(), pageSize.getTop() - rect.bottom(), pageSize.getRight() - rect.right(), pageSize.getTop() - rect.top()));
/*      */ 
/* 1291 */                 break;
/*      */               case 270:
/* 1293 */                 annot.put(PdfName.RECT, new PdfRectangle(rect.bottom(), pageSize.getRight() - rect.left(), rect.top(), pageSize.getRight() - rect.right()));
/*      */               }
/*      */ 
/*      */             }
/*      */ 
/*      */           }
/*      */ 
/*      */         }
/*      */ 
/* 1303 */         if (!annot.isUsed()) {
/* 1304 */           annot.setUsed();
/* 1305 */           addToBody(annot, annot.getIndirectReference());
/*      */         }
/*      */       }
/*      */     }
/*      */     catch (IOException e) {
/* 1310 */       throw new ExceptionConverter(e);
/*      */     }
/*      */   }
/*      */ 
/*      */   void addAnnotation(PdfAnnotation annot, int page)
/*      */   {
/* 1316 */     annot.setPage(page);
/* 1317 */     addAnnotation(annot, this.reader.getPageN(page));
/*      */   }
/*      */ 
/*      */   private void outlineTravel(PRIndirectReference outline) {
/* 1321 */     while (outline != null) {
/* 1322 */       PdfDictionary outlineR = (PdfDictionary)PdfReader.getPdfObjectRelease(outline);
/* 1323 */       PRIndirectReference first = (PRIndirectReference)outlineR.get(PdfName.FIRST);
/* 1324 */       if (first != null) {
/* 1325 */         outlineTravel(first);
/*      */       }
/* 1327 */       PdfReader.killIndirect(outlineR.get(PdfName.DEST));
/* 1328 */       PdfReader.killIndirect(outlineR.get(PdfName.A));
/* 1329 */       PdfReader.killIndirect(outline);
/* 1330 */       outline = (PRIndirectReference)outlineR.get(PdfName.NEXT);
/*      */     }
/*      */   }
/*      */ 
/*      */   void deleteOutlines() {
/* 1335 */     PdfDictionary catalog = this.reader.getCatalog();
/* 1336 */     PdfObject obj = catalog.get(PdfName.OUTLINES);
/* 1337 */     if (obj == null)
/* 1338 */       return;
/* 1339 */     if ((obj instanceof PRIndirectReference)) {
/* 1340 */       PRIndirectReference outlines = (PRIndirectReference)obj;
/* 1341 */       outlineTravel(outlines);
/* 1342 */       PdfReader.killIndirect(outlines);
/*      */     }
/* 1344 */     catalog.remove(PdfName.OUTLINES);
/* 1345 */     markUsed(catalog);
/*      */   }
/*      */ 
/*      */   protected void setJavaScript() throws IOException {
/* 1349 */     HashMap djs = this.pdf.getDocumentLevelJS();
/* 1350 */     if (djs.isEmpty())
/* 1351 */       return;
/* 1352 */     PdfDictionary catalog = this.reader.getCatalog();
/* 1353 */     PdfDictionary names = (PdfDictionary)PdfReader.getPdfObject(catalog.get(PdfName.NAMES), catalog);
/* 1354 */     if (names == null) {
/* 1355 */       names = new PdfDictionary();
/* 1356 */       catalog.put(PdfName.NAMES, names);
/* 1357 */       markUsed(catalog);
/*      */     }
/* 1359 */     markUsed(names);
/* 1360 */     PdfDictionary tree = PdfNameTree.writeTree(djs, this);
/* 1361 */     names.put(PdfName.JAVASCRIPT, addToBody(tree).getIndirectReference());
/*      */   }
/*      */ 
/*      */   protected void addFileAttachments() throws IOException {
/* 1365 */     HashMap fs = this.pdf.getDocumentFileAttachment();
/* 1366 */     if (fs.isEmpty())
/* 1367 */       return;
/* 1368 */     PdfDictionary catalog = this.reader.getCatalog();
/* 1369 */     PdfDictionary names = (PdfDictionary)PdfReader.getPdfObject(catalog.get(PdfName.NAMES), catalog);
/* 1370 */     if (names == null) {
/* 1371 */       names = new PdfDictionary();
/* 1372 */       catalog.put(PdfName.NAMES, names);
/* 1373 */       markUsed(catalog);
/*      */     }
/* 1375 */     markUsed(names);
/* 1376 */     HashMap old = PdfNameTree.readTree((PdfDictionary)PdfReader.getPdfObjectRelease(names.get(PdfName.EMBEDDEDFILES)));
/* 1377 */     for (Map.Entry entry : fs.entrySet()) {
/* 1378 */       String name = (String)entry.getKey();
/* 1379 */       int k = 0;
/* 1380 */       StringBuilder nn = new StringBuilder(name);
/* 1381 */       while (old.containsKey(nn.toString())) {
/* 1382 */         k++;
/* 1383 */         nn.append(" ").append(k);
/*      */       }
/* 1385 */       old.put(nn.toString(), entry.getValue());
/*      */     }
/* 1387 */     PdfDictionary tree = PdfNameTree.writeTree(old, this);
/*      */ 
/* 1389 */     PdfObject oldEmbeddedFiles = names.get(PdfName.EMBEDDEDFILES);
/* 1390 */     if (oldEmbeddedFiles != null) {
/* 1391 */       PdfReader.killIndirect(oldEmbeddedFiles);
/*      */     }
/*      */ 
/* 1395 */     names.put(PdfName.EMBEDDEDFILES, addToBody(tree).getIndirectReference());
/*      */   }
/*      */ 
/*      */   void makePackage(PdfCollection collection)
/*      */   {
/* 1403 */     PdfDictionary catalog = this.reader.getCatalog();
/* 1404 */     catalog.put(PdfName.COLLECTION, collection);
/*      */   }
/*      */ 
/*      */   protected void setOutlines() throws IOException {
/* 1408 */     if (this.newBookmarks == null)
/* 1409 */       return;
/* 1410 */     deleteOutlines();
/* 1411 */     if (this.newBookmarks.isEmpty())
/* 1412 */       return;
/* 1413 */     PdfDictionary catalog = this.reader.getCatalog();
/* 1414 */     boolean namedAsNames = catalog.get(PdfName.DESTS) != null;
/* 1415 */     writeOutlines(catalog, namedAsNames);
/* 1416 */     markUsed(catalog);
/*      */   }
/*      */ 
/*      */   public void setViewerPreferences(int preferences)
/*      */   {
/* 1426 */     this.useVp = true;
/* 1427 */     this.viewerPreferences.setViewerPreferences(preferences);
/*      */   }
/*      */ 
/*      */   public void addViewerPreference(PdfName key, PdfObject value)
/*      */   {
/* 1437 */     this.useVp = true;
/* 1438 */     this.viewerPreferences.addViewerPreference(key, value);
/*      */   }
/*      */ 
/*      */   public void setSigFlags(int f)
/*      */   {
/* 1447 */     this.sigFlags |= f;
/*      */   }
/*      */ 
/*      */   public void setPageAction(PdfName actionType, PdfAction action)
/*      */     throws PdfException
/*      */   {
/* 1458 */     throw new UnsupportedOperationException(MessageLocalization.getComposedMessage("use.setpageaction.pdfname.actiontype.pdfaction.action.int.page", new Object[0]));
/*      */   }
/*      */ 
/*      */   void setPageAction(PdfName actionType, PdfAction action, int page)
/*      */     throws PdfException
/*      */   {
/* 1470 */     if ((!actionType.equals(PAGE_OPEN)) && (!actionType.equals(PAGE_CLOSE)))
/* 1471 */       throw new PdfException(MessageLocalization.getComposedMessage("invalid.page.additional.action.type.1", new Object[] { actionType.toString() }));
/* 1472 */     PdfDictionary pg = this.reader.getPageN(page);
/* 1473 */     PdfDictionary aa = (PdfDictionary)PdfReader.getPdfObject(pg.get(PdfName.AA), pg);
/* 1474 */     if (aa == null) {
/* 1475 */       aa = new PdfDictionary();
/* 1476 */       pg.put(PdfName.AA, aa);
/* 1477 */       markUsed(pg);
/*      */     }
/* 1479 */     aa.put(actionType, action);
/* 1480 */     markUsed(aa);
/*      */   }
/*      */ 
/*      */   public void setDuration(int seconds)
/*      */   {
/* 1489 */     throw new UnsupportedOperationException(MessageLocalization.getComposedMessage("use.setpageaction.pdfname.actiontype.pdfaction.action.int.page", new Object[0]));
/*      */   }
/*      */ 
/*      */   public void setTransition(PdfTransition transition)
/*      */   {
/* 1498 */     throw new UnsupportedOperationException(MessageLocalization.getComposedMessage("use.setpageaction.pdfname.actiontype.pdfaction.action.int.page", new Object[0]));
/*      */   }
/*      */ 
/*      */   void setDuration(int seconds, int page)
/*      */   {
/* 1507 */     PdfDictionary pg = this.reader.getPageN(page);
/* 1508 */     if (seconds < 0)
/* 1509 */       pg.remove(PdfName.DUR);
/*      */     else
/* 1511 */       pg.put(PdfName.DUR, new PdfNumber(seconds));
/* 1512 */     markUsed(pg);
/*      */   }
/*      */ 
/*      */   void setTransition(PdfTransition transition, int page)
/*      */   {
/* 1521 */     PdfDictionary pg = this.reader.getPageN(page);
/* 1522 */     if (transition == null)
/* 1523 */       pg.remove(PdfName.TRANS);
/*      */     else
/* 1525 */       pg.put(PdfName.TRANS, transition.getTransitionDictionary());
/* 1526 */     markUsed(pg);
/*      */   }
/*      */ 
/*      */   protected void markUsed(PdfObject obj) {
/* 1530 */     if ((this.append) && (obj != null)) {
/* 1531 */       PRIndirectReference ref = null;
/* 1532 */       if (obj.type() == 10)
/* 1533 */         ref = (PRIndirectReference)obj;
/*      */       else
/* 1535 */         ref = obj.getIndRef();
/* 1536 */       if (ref != null)
/* 1537 */         this.marked.put(ref.getNumber(), 1);
/*      */     }
/*      */   }
/*      */ 
/*      */   protected void markUsed(int num) {
/* 1542 */     if (this.append)
/* 1543 */       this.marked.put(num, 1);
/*      */   }
/*      */ 
/*      */   boolean isAppend()
/*      */   {
/* 1551 */     return this.append;
/*      */   }
/*      */ 
/*      */   public void setAdditionalAction(PdfName actionType, PdfAction action)
/*      */     throws PdfException
/*      */   {
/* 1566 */     if ((!actionType.equals(DOCUMENT_CLOSE)) && (!actionType.equals(WILL_SAVE)) && (!actionType.equals(DID_SAVE)) && (!actionType.equals(WILL_PRINT)) && (!actionType.equals(DID_PRINT)))
/*      */     {
/* 1571 */       throw new PdfException(MessageLocalization.getComposedMessage("invalid.additional.action.type.1", new Object[] { actionType.toString() }));
/*      */     }
/* 1573 */     PdfDictionary aa = this.reader.getCatalog().getAsDict(PdfName.AA);
/* 1574 */     if (aa == null) {
/* 1575 */       if (action == null)
/* 1576 */         return;
/* 1577 */       aa = new PdfDictionary();
/* 1578 */       this.reader.getCatalog().put(PdfName.AA, aa);
/*      */     }
/* 1580 */     markUsed(aa);
/* 1581 */     if (action == null)
/* 1582 */       aa.remove(actionType);
/*      */     else
/* 1584 */       aa.put(actionType, action);
/*      */   }
/*      */ 
/*      */   public void setOpenAction(PdfAction action)
/*      */   {
/* 1592 */     this.openAction = action;
/*      */   }
/*      */ 
/*      */   public void setOpenAction(String name)
/*      */   {
/* 1600 */     throw new UnsupportedOperationException(MessageLocalization.getComposedMessage("open.actions.by.name.are.not.supported", new Object[0]));
/*      */   }
/*      */ 
/*      */   public void setThumbnail(Image image)
/*      */   {
/* 1608 */     throw new UnsupportedOperationException(MessageLocalization.getComposedMessage("use.pdfstamper.setthumbnail", new Object[0]));
/*      */   }
/*      */ 
/*      */   void setThumbnail(Image image, int page) throws PdfException, DocumentException {
/* 1612 */     PdfIndirectReference thumb = getImageReference(addDirectImageSimple(image));
/* 1613 */     this.reader.resetReleasePage();
/* 1614 */     PdfDictionary dic = this.reader.getPageN(page);
/* 1615 */     dic.put(PdfName.THUMB, thumb);
/* 1616 */     this.reader.resetReleasePage();
/*      */   }
/*      */ 
/*      */   public PdfContentByte getDirectContentUnder()
/*      */   {
/* 1621 */     throw new UnsupportedOperationException(MessageLocalization.getComposedMessage("use.pdfstamper.getundercontent.or.pdfstamper.getovercontent", new Object[0]));
/*      */   }
/*      */ 
/*      */   public PdfContentByte getDirectContent()
/*      */   {
/* 1626 */     throw new UnsupportedOperationException(MessageLocalization.getComposedMessage("use.pdfstamper.getundercontent.or.pdfstamper.getovercontent", new Object[0]));
/*      */   }
/*      */ 
/*      */   protected void readOCProperties()
/*      */   {
/* 1636 */     if (!this.documentOCG.isEmpty()) {
/* 1637 */       return;
/*      */     }
/* 1639 */     PdfDictionary dict = this.reader.getCatalog().getAsDict(PdfName.OCPROPERTIES);
/* 1640 */     if (dict == null) {
/* 1641 */       return;
/*      */     }
/* 1643 */     PdfArray ocgs = dict.getAsArray(PdfName.OCGS);
/*      */ 
/* 1646 */     HashMap ocgmap = new HashMap();
/* 1647 */     for (Iterator i = ocgs.listIterator(); i.hasNext(); ) {
/* 1648 */       PdfIndirectReference ref = (PdfIndirectReference)i.next();
/* 1649 */       PdfLayer layer = new PdfLayer(null);
/* 1650 */       layer.setRef(ref);
/* 1651 */       layer.setOnPanel(false);
/* 1652 */       layer.merge((PdfDictionary)PdfReader.getPdfObject(ref));
/* 1653 */       ocgmap.put(ref.toString(), layer);
/*      */     }
/* 1655 */     PdfDictionary d = dict.getAsDict(PdfName.D);
/* 1656 */     PdfArray off = d.getAsArray(PdfName.OFF);
/*      */     Iterator i;
/* 1657 */     if (off != null) {
/* 1658 */       for (i = off.listIterator(); i.hasNext(); ) {
/* 1659 */         PdfIndirectReference ref = (PdfIndirectReference)i.next();
/* 1660 */         PdfLayer layer = (PdfLayer)ocgmap.get(ref.toString());
/* 1661 */         layer.setOn(false);
/*      */       }
/*      */     }
/* 1664 */     PdfArray order = d.getAsArray(PdfName.ORDER);
/* 1665 */     if (order != null) {
/* 1666 */       addOrder(null, order, ocgmap);
/*      */     }
/* 1668 */     this.documentOCG.addAll(ocgmap.values());
/* 1669 */     this.OCGRadioGroup = d.getAsArray(PdfName.RBGROUPS);
/* 1670 */     if (this.OCGRadioGroup == null)
/* 1671 */       this.OCGRadioGroup = new PdfArray();
/* 1672 */     this.OCGLocked = d.getAsArray(PdfName.LOCKED);
/* 1673 */     if (this.OCGLocked == null)
/* 1674 */       this.OCGLocked = new PdfArray();
/*      */   }
/*      */ 
/*      */   private void addOrder(PdfLayer parent, PdfArray arr, Map<String, PdfLayer> ocgmap)
/*      */   {
/* 1687 */     for (int i = 0; i < arr.size(); i++) {
/* 1688 */       PdfObject obj = arr.getPdfObject(i);
/* 1689 */       if (obj.isIndirect()) {
/* 1690 */         PdfLayer layer = (PdfLayer)ocgmap.get(obj.toString());
/* 1691 */         if (layer != null) {
/* 1692 */           layer.setOnPanel(true);
/* 1693 */           registerLayer(layer);
/* 1694 */           if (parent != null) {
/* 1695 */             parent.addChild(layer);
/*      */           }
/* 1697 */           if ((arr.size() > i + 1) && (arr.getPdfObject(i + 1).isArray())) {
/* 1698 */             i++;
/* 1699 */             addOrder(layer, (PdfArray)arr.getPdfObject(i), ocgmap);
/*      */           }
/*      */         }
/*      */       }
/* 1703 */       else if (obj.isArray()) {
/* 1704 */         PdfArray sub = (PdfArray)obj;
/* 1705 */         if (sub.isEmpty()) return;
/* 1706 */         obj = sub.getPdfObject(0);
/* 1707 */         if (obj.isString()) {
/* 1708 */           PdfLayer layer = new PdfLayer(obj.toString());
/* 1709 */           layer.setOnPanel(true);
/* 1710 */           registerLayer(layer);
/* 1711 */           if (parent != null) {
/* 1712 */             parent.addChild(layer);
/*      */           }
/* 1714 */           PdfArray array = new PdfArray();
/* 1715 */           for (Iterator j = sub.listIterator(); j.hasNext(); ) {
/* 1716 */             array.add((PdfObject)j.next());
/*      */           }
/* 1718 */           addOrder(layer, array, ocgmap);
/*      */         }
/*      */         else {
/* 1721 */           addOrder(parent, (PdfArray)obj, ocgmap);
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   public Map<String, PdfLayer> getPdfLayers()
/*      */   {
/* 1734 */     if (this.documentOCG.isEmpty()) {
/* 1735 */       readOCProperties();
/*      */     }
/* 1737 */     HashMap map = new HashMap();
/*      */ 
/* 1740 */     for (PdfOCG pdfOCG : this.documentOCG) {
/* 1741 */       PdfLayer layer = (PdfLayer)pdfOCG;
/*      */       String key;
/*      */       String key;
/* 1742 */       if (layer.getTitle() == null) {
/* 1743 */         key = layer.getAsString(PdfName.NAME).toString();
/*      */       }
/*      */       else {
/* 1746 */         key = layer.getTitle();
/*      */       }
/* 1748 */       if (map.containsKey(key)) {
/* 1749 */         int seq = 2;
/* 1750 */         String tmp = key + "(" + seq + ")";
/* 1751 */         while (map.containsKey(tmp)) {
/* 1752 */           seq++;
/* 1753 */           tmp = key + "(" + seq + ")";
/*      */         }
/* 1755 */         key = tmp;
/*      */       }
/* 1757 */       map.put(key, layer);
/*      */     }
/* 1759 */     return map;
/*      */   }
/*      */ 
/*      */   public void createXmpMetadata() {
/*      */     try {
/* 1764 */       this.xmpWriter = createXmpWriter(null, this.reader.getInfo());
/* 1765 */       this.xmpMetadata = null;
/*      */     } catch (IOException ioe) {
/* 1767 */       ioe.printStackTrace();
/*      */     }
/*      */   }
/*      */ 
/*      */   static class PageStamp
/*      */   {
/*      */     PdfDictionary pageN;
/*      */     StampContent under;
/*      */     StampContent over;
/*      */     PageResources pageResources;
/* 1777 */     int replacePoint = 0;
/*      */ 
/*      */     PageStamp(PdfStamperImp stamper, PdfReader reader, PdfDictionary pageN) {
/* 1780 */       this.pageN = pageN;
/* 1781 */       this.pageResources = new PageResources();
/* 1782 */       PdfDictionary resources = pageN.getAsDict(PdfName.RESOURCES);
/* 1783 */       this.pageResources.setOriginalResources(resources, stamper.namePtr);
/*      */     }
/*      */   }
/*      */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.pdf.PdfStamperImp
 * JD-Core Version:    0.6.2
 */