/*      */ package com.itextpdf.text.pdf;
/*      */ 
/*      */ import com.itextpdf.text.BaseColor;
/*      */ import com.itextpdf.text.DocListener;
/*      */ import com.itextpdf.text.DocWriter;
/*      */ import com.itextpdf.text.Document;
/*      */ import com.itextpdf.text.DocumentException;
/*      */ import com.itextpdf.text.ExceptionConverter;
/*      */ import com.itextpdf.text.Image;
/*      */ import com.itextpdf.text.ImgJBIG2;
/*      */ import com.itextpdf.text.ImgWMF;
/*      */ import com.itextpdf.text.Version;
/*      */ import com.itextpdf.text.error_messages.MessageLocalization;
/*      */ import com.itextpdf.text.log.Counter;
/*      */ import com.itextpdf.text.log.CounterFactory;
/*      */ import com.itextpdf.text.pdf.collection.PdfCollection;
/*      */ import com.itextpdf.text.pdf.events.PdfPageEventForwarder;
/*      */ import com.itextpdf.text.pdf.interfaces.IAccessibleElement;
/*      */ import com.itextpdf.text.pdf.interfaces.PdfAnnotations;
/*      */ import com.itextpdf.text.pdf.interfaces.PdfDocumentActions;
/*      */ import com.itextpdf.text.pdf.interfaces.PdfEncryptionSettings;
/*      */ import com.itextpdf.text.pdf.interfaces.PdfIsoConformance;
/*      */ import com.itextpdf.text.pdf.interfaces.PdfPageActions;
/*      */ import com.itextpdf.text.pdf.interfaces.PdfRunDirection;
/*      */ import com.itextpdf.text.pdf.interfaces.PdfVersion;
/*      */ import com.itextpdf.text.pdf.interfaces.PdfViewerPreferences;
/*      */ import com.itextpdf.text.pdf.interfaces.PdfXConformance;
/*      */ import com.itextpdf.text.pdf.internal.PdfVersionImp;
/*      */ import com.itextpdf.text.pdf.internal.PdfXConformanceImp;
/*      */ import com.itextpdf.text.xml.xmp.XmpWriter;
/*      */ import com.itextpdf.xmp.XMPException;
/*      */ import com.itextpdf.xmp.XMPMeta;
/*      */ import com.itextpdf.xmp.options.PropertyOptions;
/*      */ import java.io.ByteArrayOutputStream;
/*      */ import java.io.IOException;
/*      */ import java.io.OutputStream;
/*      */ import java.security.cert.Certificate;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Arrays;
/*      */ import java.util.HashMap;
/*      */ import java.util.HashSet;
/*      */ import java.util.Iterator;
/*      */ import java.util.LinkedHashMap;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.Map.Entry;
/*      */ import java.util.TreeMap;
/*      */ import java.util.TreeSet;
/*      */ 
/*      */ public class PdfWriter extends DocWriter
/*      */   implements PdfViewerPreferences, PdfEncryptionSettings, PdfVersion, PdfDocumentActions, PdfPageActions, PdfRunDirection, PdfAnnotations
/*      */ {
/*      */   public static final int GENERATION_MAX = 65535;
/*  600 */   protected static Counter COUNTER = CounterFactory.getCounter(PdfWriter.class);
/*      */   protected PdfDocument pdf;
/*      */   protected PdfContentByte directContent;
/*      */   protected PdfContentByte directContentUnder;
/*      */   protected PdfBody body;
/*      */   protected ICC_Profile colorProfile;
/*      */   protected PdfDictionary extraCatalog;
/*  984 */   protected PdfPages root = new PdfPages(this);
/*      */ 
/*  986 */   protected ArrayList<PdfIndirectReference> pageReferences = new ArrayList();
/*      */ 
/*  988 */   protected int currentPageNumber = 1;
/*      */ 
/*  993 */   protected PdfName tabs = null;
/*      */ 
/*  999 */   protected PdfDictionary pageDictEntries = new PdfDictionary();
/*      */   private PdfPageEvent pageEvent;
/* 1213 */   protected long prevxref = 0L;
/*      */ 
/* 1215 */   protected byte[] originalFileID = null;
/*      */   protected List<HashMap<String, Object>> newBookmarks;
/*      */   public static final char VERSION_1_2 = '2';
/*      */   public static final char VERSION_1_3 = '3';
/*      */   public static final char VERSION_1_4 = '4';
/*      */   public static final char VERSION_1_5 = '5';
/*      */   public static final char VERSION_1_6 = '6';
/*      */   public static final char VERSION_1_7 = '7';
/* 1472 */   public static final PdfName PDF_VERSION_1_2 = new PdfName("1.2");
/*      */ 
/* 1474 */   public static final PdfName PDF_VERSION_1_3 = new PdfName("1.3");
/*      */ 
/* 1476 */   public static final PdfName PDF_VERSION_1_4 = new PdfName("1.4");
/*      */ 
/* 1478 */   public static final PdfName PDF_VERSION_1_5 = new PdfName("1.5");
/*      */ 
/* 1480 */   public static final PdfName PDF_VERSION_1_6 = new PdfName("1.6");
/*      */ 
/* 1482 */   public static final PdfName PDF_VERSION_1_7 = new PdfName("1.7");
/*      */ 
/* 1485 */   protected PdfVersionImp pdf_version = new PdfVersionImp();
/*      */   public static final int PageLayoutSinglePage = 1;
/*      */   public static final int PageLayoutOneColumn = 2;
/*      */   public static final int PageLayoutTwoColumnLeft = 4;
/*      */   public static final int PageLayoutTwoColumnRight = 8;
/*      */   public static final int PageLayoutTwoPageLeft = 16;
/*      */   public static final int PageLayoutTwoPageRight = 32;
/*      */   public static final int PageModeUseNone = 64;
/*      */   public static final int PageModeUseOutlines = 128;
/*      */   public static final int PageModeUseThumbs = 256;
/*      */   public static final int PageModeFullScreen = 512;
/*      */   public static final int PageModeUseOC = 1024;
/*      */   public static final int PageModeUseAttachments = 2048;
/*      */   public static final int HideToolbar = 4096;
/*      */   public static final int HideMenubar = 8192;
/*      */   public static final int HideWindowUI = 16384;
/*      */   public static final int FitWindow = 32768;
/*      */   public static final int CenterWindow = 65536;
/*      */   public static final int DisplayDocTitle = 131072;
/*      */   public static final int NonFullScreenPageModeUseNone = 262144;
/*      */   public static final int NonFullScreenPageModeUseOutlines = 524288;
/*      */   public static final int NonFullScreenPageModeUseThumbs = 1048576;
/*      */   public static final int NonFullScreenPageModeUseOC = 2097152;
/*      */   public static final int DirectionL2R = 4194304;
/*      */   public static final int DirectionR2L = 8388608;
/*      */   public static final int PrintScalingNone = 16777216;
/* 1737 */   public static final PdfName DOCUMENT_CLOSE = PdfName.WC;
/*      */ 
/* 1739 */   public static final PdfName WILL_SAVE = PdfName.WS;
/*      */ 
/* 1741 */   public static final PdfName DID_SAVE = PdfName.DS;
/*      */ 
/* 1743 */   public static final PdfName WILL_PRINT = PdfName.WP;
/*      */ 
/* 1745 */   public static final PdfName DID_PRINT = PdfName.DP;
/*      */   public static final int SIGNATURE_EXISTS = 1;
/*      */   public static final int SIGNATURE_APPEND_ONLY = 2;
/* 1818 */   protected byte[] xmpMetadata = null;
/*      */ 
/* 1837 */   protected XmpWriter xmpWriter = null;
/*      */   public static final int PDFXNONE = 0;
/*      */   public static final int PDFX1A2001 = 1;
/*      */   public static final int PDFX32002 = 2;
/* 1873 */   protected PdfIsoConformance pdfIsoConformance = initPdfIsoConformance();
/*      */   public static final int STANDARD_ENCRYPTION_40 = 0;
/*      */   public static final int STANDARD_ENCRYPTION_128 = 1;
/*      */   public static final int ENCRYPTION_AES_128 = 2;
/*      */   public static final int ENCRYPTION_AES_256 = 3;
/*      */   static final int ENCRYPTION_MASK = 7;
/*      */   public static final int DO_NOT_ENCRYPT_METADATA = 8;
/*      */   public static final int EMBEDDED_FILES_ONLY = 24;
/*      */   public static final int ALLOW_PRINTING = 2052;
/*      */   public static final int ALLOW_MODIFY_CONTENTS = 8;
/*      */   public static final int ALLOW_COPY = 16;
/*      */   public static final int ALLOW_MODIFY_ANNOTATIONS = 32;
/*      */   public static final int ALLOW_FILL_IN = 256;
/*      */   public static final int ALLOW_SCREENREADERS = 512;
/*      */   public static final int ALLOW_ASSEMBLY = 1024;
/*      */   public static final int ALLOW_DEGRADED_PRINTING = 4;
/*      */ 
/*      */   @Deprecated
/*      */   public static final int AllowPrinting = 2052;
/*      */ 
/*      */   @Deprecated
/*      */   public static final int AllowModifyContents = 8;
/*      */ 
/*      */   @Deprecated
/*      */   public static final int AllowCopy = 16;
/*      */ 
/*      */   @Deprecated
/*      */   public static final int AllowModifyAnnotations = 32;
/*      */ 
/*      */   @Deprecated
/*      */   public static final int AllowFillIn = 256;
/*      */ 
/*      */   @Deprecated
/*      */   public static final int AllowScreenReaders = 512;
/*      */ 
/*      */   @Deprecated
/*      */   public static final int AllowAssembly = 1024;
/*      */ 
/*      */   @Deprecated
/*      */   public static final int AllowDegradedPrinting = 4;
/*      */ 
/*      */   @Deprecated
/*      */   public static final boolean STRENGTH40BITS = false;
/*      */ 
/*      */   @Deprecated
/*      */   public static final boolean STRENGTH128BITS = true;
/*      */   protected PdfEncryption crypto;
/* 2211 */   protected boolean fullCompression = false;
/*      */ 
/* 2237 */   protected int compressionLevel = -1;
/*      */ 
/* 2263 */   protected LinkedHashMap<BaseFont, FontDetails> documentFonts = new LinkedHashMap();
/*      */ 
/* 2266 */   protected int fontNumber = 1;
/*      */ 
/* 2302 */   protected HashMap<PdfIndirectReference, Object[]> formXObjects = new HashMap();
/*      */ 
/* 2305 */   protected int formXObjectsCounter = 1;
/*      */ 
/* 2374 */   protected HashMap<PdfReader, PdfReaderInstance> readerInstances = new HashMap();
/*      */   protected PdfReaderInstance currentPdfReaderInstance;
/* 2453 */   protected HashMap<ICachedColorSpace, ColorDetails> documentColors = new HashMap();
/*      */ 
/* 2456 */   protected int colorNumber = 1;
/*      */ 
/* 2483 */   protected HashMap<PdfPatternPainter, PdfName> documentPatterns = new HashMap();
/*      */ 
/* 2486 */   protected int patternNumber = 1;
/*      */ 
/* 2504 */   protected HashSet<PdfShadingPattern> documentShadingPatterns = new HashSet();
/*      */ 
/* 2517 */   protected HashSet<PdfShading> documentShadings = new HashSet();
/*      */ 
/* 2528 */   protected HashMap<PdfDictionary, PdfObject[]> documentExtGState = new HashMap();
/*      */ 
/* 2539 */   protected HashMap<Object, PdfObject[]> documentProperties = new HashMap();
/*      */   public static final int markAll = 0;
/*      */   public static final int markInlineElementsOnly = 1;
/* 2558 */   protected boolean tagged = false;
/* 2559 */   protected int taggingMode = 1;
/*      */   protected PdfStructureTreeRoot structureTreeRoot;
/* 2623 */   protected HashSet<PdfOCG> documentOCG = new HashSet();
/*      */ 
/* 2625 */   protected ArrayList<PdfOCG> documentOCGorder = new ArrayList();
/*      */   protected PdfOCProperties OCProperties;
/* 2629 */   protected PdfArray OCGRadioGroup = new PdfArray();
/*      */ 
/* 2634 */   protected PdfArray OCGLocked = new PdfArray();
/*      */ 
/* 2899 */   public static final PdfName PAGE_OPEN = PdfName.O;
/*      */ 
/* 2901 */   public static final PdfName PAGE_CLOSE = PdfName.C;
/*      */   protected PdfDictionary group;
/*      */   public static final float SPACE_CHAR_RATIO_DEFAULT = 2.5F;
/*      */   public static final float NO_SPACE_CHAR_RATIO = 10000000.0F;
/* 2968 */   private float spaceCharRatio = 2.5F;
/*      */   public static final int RUN_DIRECTION_DEFAULT = 0;
/*      */   public static final int RUN_DIRECTION_NO_BIDI = 1;
/*      */   public static final int RUN_DIRECTION_LTR = 2;
/*      */   public static final int RUN_DIRECTION_RTL = 3;
/* 3009 */   protected int runDirection = 1;
/*      */ 
/* 3050 */   protected PdfDictionary defaultColorspace = new PdfDictionary();
/*      */ 
/* 3078 */   protected HashMap<ColorDetails, ColorDetails> documentSpotPatterns = new HashMap();
/*      */   protected ColorDetails patternColorspaceRGB;
/*      */   protected ColorDetails patternColorspaceGRAY;
/*      */   protected ColorDetails patternColorspaceCMYK;
/* 3163 */   protected PdfDictionary imageDictionary = new PdfDictionary();
/*      */ 
/* 3166 */   private final HashMap<Long, PdfName> images = new HashMap();
/*      */ 
/* 3317 */   protected HashMap<PdfStream, PdfIndirectReference> JBIG2Globals = new HashMap();
/*      */   private boolean userProperties;
/*      */   private boolean rgbTransparencyBlending;
/* 3404 */   protected TtfUnicodeWriter ttfUnicodeWriter = null;
/*      */ 
/* 3465 */   private static final List<PdfName> standardStructElems_1_4 = Arrays.asList(new PdfName[] { PdfName.DOCUMENT, PdfName.PART, PdfName.ART, PdfName.SECT, PdfName.DIV, PdfName.BLOCKQUOTE, PdfName.CAPTION, PdfName.TOC, PdfName.TOCI, PdfName.INDEX, PdfName.NONSTRUCT, PdfName.PRIVATE, PdfName.P, PdfName.H, PdfName.H1, PdfName.H2, PdfName.H3, PdfName.H4, PdfName.H5, PdfName.H6, PdfName.L, PdfName.LBL, PdfName.LI, PdfName.LBODY, PdfName.TABLE, PdfName.TR, PdfName.TH, PdfName.TD, PdfName.SPAN, PdfName.QUOTE, PdfName.NOTE, PdfName.REFERENCE, PdfName.BIBENTRY, PdfName.CODE, PdfName.LINK, PdfName.FIGURE, PdfName.FORMULA, PdfName.FORM });
/*      */ 
/* 3472 */   private static final List<PdfName> standardStructElems_1_7 = Arrays.asList(new PdfName[] { PdfName.DOCUMENT, PdfName.PART, PdfName.ART, PdfName.SECT, PdfName.DIV, PdfName.BLOCKQUOTE, PdfName.CAPTION, PdfName.TOC, PdfName.TOCI, PdfName.INDEX, PdfName.NONSTRUCT, PdfName.PRIVATE, PdfName.P, PdfName.H, PdfName.H1, PdfName.H2, PdfName.H3, PdfName.H4, PdfName.H5, PdfName.H6, PdfName.L, PdfName.LBL, PdfName.LI, PdfName.LBODY, PdfName.TABLE, PdfName.TR, PdfName.TH, PdfName.TD, PdfName.THEAD, PdfName.TBODY, PdfName.TFOOT, PdfName.SPAN, PdfName.QUOTE, PdfName.NOTE, PdfName.REFERENCE, PdfName.BIBENTRY, PdfName.CODE, PdfName.LINK, PdfName.ANNOT, PdfName.RUBY, PdfName.RB, PdfName.RT, PdfName.RP, PdfName.WARICHU, PdfName.WT, PdfName.WP, PdfName.FIGURE, PdfName.FORMULA, PdfName.FORM });
/*      */ 
/*      */   protected Counter getCounter()
/*      */   {
/*  602 */     return COUNTER;
/*      */   }
/*      */ 
/*      */   protected PdfWriter()
/*      */   {
/*      */   }
/*      */ 
/*      */   protected PdfWriter(PdfDocument document, OutputStream os)
/*      */   {
/*  624 */     super(document, os);
/*  625 */     this.pdf = document;
/*  626 */     this.directContentUnder = new PdfContentByte(this);
/*  627 */     this.directContent = this.directContentUnder.getDuplicate();
/*      */   }
/*      */ 
/*      */   public static PdfWriter getInstance(Document document, OutputStream os)
/*      */     throws DocumentException
/*      */   {
/*  642 */     PdfDocument pdf = new PdfDocument();
/*  643 */     document.addDocListener(pdf);
/*  644 */     PdfWriter writer = new PdfWriter(pdf, os);
/*  645 */     pdf.addWriter(writer);
/*  646 */     return writer;
/*      */   }
/*      */ 
/*      */   public static PdfWriter getInstance(Document document, OutputStream os, DocListener listener)
/*      */     throws DocumentException
/*      */   {
/*  661 */     PdfDocument pdf = new PdfDocument();
/*  662 */     pdf.addDocListener(listener);
/*  663 */     document.addDocListener(pdf);
/*  664 */     PdfWriter writer = new PdfWriter(pdf, os);
/*  665 */     pdf.addWriter(writer);
/*  666 */     return writer;
/*      */   }
/*      */ 
/*      */   PdfDocument getPdfDocument()
/*      */   {
/*  680 */     return this.pdf;
/*      */   }
/*      */ 
/*      */   public PdfDictionary getInfo()
/*      */   {
/*  689 */     return this.pdf.getInfo();
/*      */   }
/*      */ 
/*      */   public float getVerticalPosition(boolean ensureNewLine)
/*      */   {
/*  700 */     return this.pdf.getVerticalPosition(ensureNewLine);
/*      */   }
/*      */ 
/*      */   public void setInitialLeading(float leading)
/*      */     throws DocumentException
/*      */   {
/*  711 */     if (this.open)
/*  712 */       throw new DocumentException(MessageLocalization.getComposedMessage("you.can.t.set.the.initial.leading.if.the.document.is.already.open", new Object[0]));
/*  713 */     this.pdf.setLeading(leading);
/*      */   }
/*      */ 
/*      */   public PdfContentByte getDirectContent()
/*      */   {
/*  741 */     if (!this.open)
/*  742 */       throw new RuntimeException(MessageLocalization.getComposedMessage("the.document.is.not.open", new Object[0]));
/*  743 */     return this.directContent;
/*      */   }
/*      */ 
/*      */   public PdfContentByte getDirectContentUnder()
/*      */   {
/*  754 */     if (!this.open)
/*  755 */       throw new RuntimeException(MessageLocalization.getComposedMessage("the.document.is.not.open", new Object[0]));
/*  756 */     return this.directContentUnder;
/*      */   }
/*      */ 
/*      */   void resetContent()
/*      */   {
/*  764 */     this.directContent.reset();
/*  765 */     this.directContentUnder.reset();
/*      */   }
/*      */ 
/*      */   public ICC_Profile getColorProfile()
/*      */   {
/*  784 */     return this.colorProfile;
/*      */   }
/*      */ 
/*      */   void addLocalDestinations(TreeMap<String, PdfDocument.Destination> desto)
/*      */     throws IOException
/*      */   {
/*  794 */     for (Map.Entry entry : desto.entrySet()) {
/*  795 */       String name = (String)entry.getKey();
/*  796 */       PdfDocument.Destination dest = (PdfDocument.Destination)entry.getValue();
/*  797 */       PdfDestination destination = dest.destination;
/*  798 */       if (dest.reference == null)
/*  799 */         dest.reference = getPdfIndirectReference();
/*  800 */       if (destination == null)
/*  801 */         addToBody(new PdfString("invalid_" + name), dest.reference);
/*      */       else
/*  803 */         addToBody(destination, dest.reference);
/*      */     }
/*      */   }
/*      */ 
/*      */   public PdfIndirectObject addToBody(PdfObject object)
/*      */     throws IOException
/*      */   {
/*  815 */     PdfIndirectObject iobj = this.body.add(object);
/*  816 */     cacheObject(iobj);
/*  817 */     return iobj;
/*      */   }
/*      */ 
/*      */   public PdfIndirectObject addToBody(PdfObject object, boolean inObjStm)
/*      */     throws IOException
/*      */   {
/*  829 */     PdfIndirectObject iobj = this.body.add(object, inObjStm);
/*  830 */     cacheObject(iobj);
/*  831 */     return iobj;
/*      */   }
/*      */ 
/*      */   public PdfIndirectObject addToBody(PdfObject object, PdfIndirectReference ref)
/*      */     throws IOException
/*      */   {
/*  843 */     PdfIndirectObject iobj = this.body.add(object, ref);
/*  844 */     cacheObject(iobj);
/*  845 */     return iobj;
/*      */   }
/*      */ 
/*      */   public PdfIndirectObject addToBody(PdfObject object, PdfIndirectReference ref, boolean inObjStm)
/*      */     throws IOException
/*      */   {
/*  858 */     PdfIndirectObject iobj = this.body.add(object, ref, inObjStm);
/*  859 */     cacheObject(iobj);
/*  860 */     return iobj;
/*      */   }
/*      */ 
/*      */   public PdfIndirectObject addToBody(PdfObject object, int refNumber)
/*      */     throws IOException
/*      */   {
/*  872 */     PdfIndirectObject iobj = this.body.add(object, refNumber);
/*  873 */     cacheObject(iobj);
/*  874 */     return iobj;
/*      */   }
/*      */ 
/*      */   public PdfIndirectObject addToBody(PdfObject object, int refNumber, boolean inObjStm)
/*      */     throws IOException
/*      */   {
/*  887 */     PdfIndirectObject iobj = this.body.add(object, refNumber, 0, inObjStm);
/*  888 */     cacheObject(iobj);
/*  889 */     return iobj;
/*      */   }
/*      */ 
/*      */   protected void cacheObject(PdfIndirectObject iobj)
/*      */   {
/*      */   }
/*      */ 
/*      */   public PdfIndirectReference getPdfIndirectReference()
/*      */   {
/*  906 */     return this.body.getPdfIndirectReference();
/*      */   }
/*      */ 
/*      */   protected int getIndirectReferenceNumber() {
/*  910 */     return this.body.getIndirectReferenceNumber();
/*      */   }
/*      */ 
/*      */   public OutputStreamCounter getOs()
/*      */   {
/*  918 */     return this.os;
/*      */   }
/*      */ 
/*      */   protected PdfDictionary getCatalog(PdfIndirectReference rootObj)
/*      */   {
/*  933 */     PdfDictionary catalog = this.pdf.getCatalog(rootObj);
/*      */ 
/*  935 */     buildStructTreeRootForTagged(catalog);
/*      */ 
/*  937 */     if (!this.documentOCG.isEmpty()) {
/*  938 */       fillOCProperties(false);
/*  939 */       catalog.put(PdfName.OCPROPERTIES, this.OCProperties);
/*      */     }
/*  941 */     return catalog;
/*      */   }
/*      */ 
/*      */   protected void buildStructTreeRootForTagged(PdfDictionary catalog) {
/*  945 */     if (this.tagged) {
/*      */       try {
/*  947 */         getStructureTreeRoot().buildTree();
/*      */       }
/*      */       catch (Exception e) {
/*  950 */         throw new ExceptionConverter(e);
/*      */       }
/*  952 */       catalog.put(PdfName.STRUCTTREEROOT, this.structureTreeRoot.getReference());
/*  953 */       PdfDictionary mi = new PdfDictionary();
/*  954 */       mi.put(PdfName.MARKED, PdfBoolean.PDFTRUE);
/*  955 */       if (this.userProperties)
/*  956 */         mi.put(PdfName.USERPROPERTIES, PdfBoolean.PDFTRUE);
/*  957 */       catalog.put(PdfName.MARKINFO, mi);
/*      */     }
/*      */   }
/*      */ 
/*      */   public PdfDictionary getExtraCatalog()
/*      */   {
/*  969 */     if (this.extraCatalog == null)
/*  970 */       this.extraCatalog = new PdfDictionary();
/*  971 */     return this.extraCatalog;
/*      */   }
/*      */ 
/*      */   public void addPageDictEntry(PdfName key, PdfObject object)
/*      */   {
/* 1008 */     this.pageDictEntries.put(key, object);
/*      */   }
/*      */ 
/*      */   public PdfDictionary getPageDictEntries()
/*      */   {
/* 1017 */     return this.pageDictEntries;
/*      */   }
/*      */ 
/*      */   public void resetPageDictEntries()
/*      */   {
/* 1025 */     this.pageDictEntries = new PdfDictionary();
/*      */   }
/*      */ 
/*      */   public void setLinearPageMode()
/*      */   {
/* 1034 */     this.root.setLinearMode(null);
/*      */   }
/*      */ 
/*      */   public int reorderPages(int[] order)
/*      */     throws DocumentException
/*      */   {
/* 1047 */     return this.root.reorderPages(order);
/*      */   }
/*      */ 
/*      */   public PdfIndirectReference getPageReference(int page)
/*      */   {
/* 1060 */     page--;
/* 1061 */     if (page < 0)
/* 1062 */       throw new IndexOutOfBoundsException(MessageLocalization.getComposedMessage("the.page.number.must.be.gt.eq.1", new Object[0]));
/*      */     PdfIndirectReference ref;
/* 1064 */     if (page < this.pageReferences.size()) {
/* 1065 */       PdfIndirectReference ref = (PdfIndirectReference)this.pageReferences.get(page);
/* 1066 */       if (ref == null) {
/* 1067 */         ref = this.body.getPdfIndirectReference();
/* 1068 */         this.pageReferences.set(page, ref);
/*      */       }
/*      */     }
/*      */     else {
/* 1072 */       int empty = page - this.pageReferences.size();
/* 1073 */       for (int k = 0; k < empty; k++)
/* 1074 */         this.pageReferences.add(null);
/* 1075 */       ref = this.body.getPdfIndirectReference();
/* 1076 */       this.pageReferences.add(ref);
/*      */     }
/* 1078 */     return ref;
/*      */   }
/*      */ 
/*      */   public int getPageNumber()
/*      */   {
/* 1089 */     return this.pdf.getPageNumber();
/*      */   }
/*      */ 
/*      */   PdfIndirectReference getCurrentPage() {
/* 1093 */     return getPageReference(this.currentPageNumber);
/*      */   }
/*      */ 
/*      */   public int getCurrentPageNumber() {
/* 1097 */     return this.currentPageNumber;
/*      */   }
/*      */ 
/*      */   public void setPageViewport(PdfArray vp)
/*      */   {
/* 1106 */     addPageDictEntry(PdfName.VP, vp);
/*      */   }
/*      */ 
/*      */   public void setTabs(PdfName tabs)
/*      */   {
/* 1117 */     this.tabs = tabs;
/*      */   }
/*      */ 
/*      */   public PdfName getTabs()
/*      */   {
/* 1126 */     return this.tabs;
/*      */   }
/*      */ 
/*      */   PdfIndirectReference add(PdfPage page, PdfContents contents)
/*      */     throws PdfException
/*      */   {
/* 1142 */     if (!this.open)
/* 1143 */       throw new PdfException(MessageLocalization.getComposedMessage("the.document.is.not.open", new Object[0]));
/*      */     PdfIndirectObject object;
/*      */     try
/*      */     {
/* 1147 */       object = addToBody(contents);
/*      */     }
/*      */     catch (IOException ioe) {
/* 1150 */       throw new ExceptionConverter(ioe);
/*      */     }
/* 1152 */     page.add(object.getIndirectReference());
/*      */ 
/* 1154 */     if (this.group != null) {
/* 1155 */       page.put(PdfName.GROUP, this.group);
/* 1156 */       this.group = null;
/*      */     }
/* 1158 */     else if (this.rgbTransparencyBlending) {
/* 1159 */       PdfDictionary pp = new PdfDictionary();
/* 1160 */       pp.put(PdfName.TYPE, PdfName.GROUP);
/* 1161 */       pp.put(PdfName.S, PdfName.TRANSPARENCY);
/* 1162 */       pp.put(PdfName.CS, PdfName.DEVICERGB);
/* 1163 */       page.put(PdfName.GROUP, pp);
/*      */     }
/* 1165 */     this.root.addPage(page);
/* 1166 */     this.currentPageNumber += 1;
/* 1167 */     return null;
/*      */   }
/*      */ 
/*      */   public void setPageEvent(PdfPageEvent event)
/*      */   {
/* 1188 */     if (event == null) { this.pageEvent = null;
/* 1189 */     } else if (this.pageEvent == null) { this.pageEvent = event;
/* 1190 */     } else if ((this.pageEvent instanceof PdfPageEventForwarder)) { ((PdfPageEventForwarder)this.pageEvent).addPageEvent(event);
/*      */     } else {
/* 1192 */       PdfPageEventForwarder forward = new PdfPageEventForwarder();
/* 1193 */       forward.addPageEvent(this.pageEvent);
/* 1194 */       forward.addPageEvent(event);
/* 1195 */       this.pageEvent = forward;
/*      */     }
/*      */   }
/*      */ 
/*      */   public PdfPageEvent getPageEvent()
/*      */   {
/* 1207 */     return this.pageEvent;
/*      */   }
/*      */ 
/*      */   public void open()
/*      */   {
/* 1227 */     super.open();
/*      */     try {
/* 1229 */       this.pdf_version.writeHeader(this.os);
/* 1230 */       this.body = new PdfBody(this);
/* 1231 */       if ((isPdfX()) && (((PdfXConformanceImp)this.pdfIsoConformance).isPdfX32002())) {
/* 1232 */         PdfDictionary sec = new PdfDictionary();
/* 1233 */         sec.put(PdfName.GAMMA, new PdfArray(new float[] { 2.2F, 2.2F, 2.2F }));
/* 1234 */         sec.put(PdfName.MATRIX, new PdfArray(new float[] { 0.4124F, 0.2126F, 0.0193F, 0.3576F, 0.7152F, 0.1192F, 0.1805F, 0.0722F, 0.9505F }));
/* 1235 */         sec.put(PdfName.WHITEPOINT, new PdfArray(new float[] { 0.9505F, 1.0F, 1.089F }));
/* 1236 */         PdfArray arr = new PdfArray(PdfName.CALRGB);
/* 1237 */         arr.add(sec);
/* 1238 */         setDefaultColorspace(PdfName.DEFAULTRGB, addToBody(arr).getIndirectReference());
/*      */       }
/*      */     }
/*      */     catch (IOException ioe) {
/* 1242 */       throw new ExceptionConverter(ioe);
/*      */     }
/*      */   }
/*      */ 
/*      */   public void close()
/*      */   {
/* 1258 */     if (this.open) {
/* 1259 */       if (this.currentPageNumber - 1 != this.pageReferences.size()) {
/* 1260 */         throw new RuntimeException("The page " + this.pageReferences.size() + " was requested but the document has only " + (this.currentPageNumber - 1) + " pages.");
/*      */       }
/* 1262 */       this.pdf.close();
/*      */       try {
/* 1264 */         addSharedObjectsToBody();
/* 1265 */         for (PdfOCG layer : this.documentOCG) {
/* 1266 */           addToBody(layer.getPdfObject(), layer.getRef());
/*      */         }
/*      */ 
/* 1269 */         PdfIndirectReference rootRef = this.root.writePageTree();
/*      */ 
/* 1271 */         PdfDictionary catalog = getCatalog(rootRef);
/* 1272 */         if (!this.documentOCG.isEmpty()) {
/* 1273 */           checkPdfIsoConformance(this, 7, this.OCProperties);
/*      */         }
/* 1275 */         if ((this.xmpMetadata == null) && (this.xmpWriter != null)) {
/*      */           try {
/* 1277 */             ByteArrayOutputStream baos = new ByteArrayOutputStream();
/* 1278 */             this.xmpWriter.serialize(baos);
/* 1279 */             this.xmpWriter.close();
/* 1280 */             this.xmpMetadata = baos.toByteArray();
/*      */           } catch (IOException exc) {
/* 1282 */             this.xmpWriter = null;
/*      */           } catch (XMPException exc) {
/* 1284 */             this.xmpWriter = null;
/*      */           }
/*      */         }
/* 1287 */         if (this.xmpMetadata != null) {
/* 1288 */           PdfStream xmp = new PdfStream(this.xmpMetadata);
/* 1289 */           xmp.put(PdfName.TYPE, PdfName.METADATA);
/* 1290 */           xmp.put(PdfName.SUBTYPE, PdfName.XML);
/* 1291 */           if ((this.crypto != null) && (!this.crypto.isMetadataEncrypted())) {
/* 1292 */             PdfArray ar = new PdfArray();
/* 1293 */             ar.add(PdfName.CRYPT);
/* 1294 */             xmp.put(PdfName.FILTER, ar);
/*      */           }
/* 1296 */           catalog.put(PdfName.METADATA, this.body.add(xmp).getIndirectReference());
/*      */         }
/*      */ 
/* 1299 */         if (isPdfX()) {
/* 1300 */           completeInfoDictionary(getInfo());
/* 1301 */           completeExtraCatalog(getExtraCatalog());
/*      */         }
/*      */ 
/* 1304 */         if (this.extraCatalog != null) {
/* 1305 */           catalog.mergeDifferent(this.extraCatalog);
/*      */         }
/*      */ 
/* 1308 */         writeOutlines(catalog, false);
/*      */ 
/* 1311 */         PdfIndirectObject indirectCatalog = addToBody(catalog, false);
/*      */ 
/* 1313 */         PdfIndirectObject infoObj = addToBody(getInfo(), false);
/*      */ 
/* 1316 */         PdfIndirectReference encryption = null;
/* 1317 */         PdfObject fileID = null;
/* 1318 */         this.body.flushObjStm();
/* 1319 */         boolean isModified = this.originalFileID != null;
/* 1320 */         if (this.crypto != null) {
/* 1321 */           PdfIndirectObject encryptionObject = addToBody(this.crypto.getEncryptionDictionary(), false);
/* 1322 */           encryption = encryptionObject.getIndirectReference();
/* 1323 */           fileID = this.crypto.getFileID(isModified);
/*      */         }
/*      */         else {
/* 1326 */           fileID = PdfEncryption.createInfoId(isModified ? this.originalFileID : PdfEncryption.createDocumentId(), isModified);
/*      */         }
/*      */ 
/* 1330 */         this.body.writeCrossReferenceTable(this.os, indirectCatalog.getIndirectReference(), infoObj.getIndirectReference(), encryption, fileID, this.prevxref);
/*      */ 
/* 1335 */         if (this.fullCompression) {
/* 1336 */           writeKeyInfo(this.os);
/* 1337 */           this.os.write(getISOBytes("startxref\n"));
/* 1338 */           this.os.write(getISOBytes(String.valueOf(this.body.offset())));
/* 1339 */           this.os.write(getISOBytes("\n%%EOF\n"));
/*      */         }
/*      */         else {
/* 1342 */           PdfTrailer trailer = new PdfTrailer(this.body.size(), this.body.offset(), indirectCatalog.getIndirectReference(), infoObj.getIndirectReference(), encryption, fileID, this.prevxref);
/*      */ 
/* 1348 */           trailer.toPdf(this, this.os);
/*      */         }
/*      */       } catch (IOException ioe) {
/* 1351 */         throw new ExceptionConverter(ioe);
/*      */       } finally {
/* 1353 */         super.close();
/*      */       }
/*      */     }
/* 1356 */     getCounter().written(this.os.getCounter());
/*      */   }
/*      */ 
/*      */   protected void addXFormsToBody() throws IOException {
/* 1360 */     for (Object[] objs : this.formXObjects.values()) {
/* 1361 */       PdfTemplate template = (PdfTemplate)objs[1];
/* 1362 */       if ((template == null) || (!(template.getIndirectReference() instanceof PRIndirectReference)))
/*      */       {
/* 1364 */         if ((template != null) && (template.getType() == 1))
/* 1365 */           addToBody(template.getFormXObject(this.compressionLevel), template.getIndirectReference());
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   protected void addSharedObjectsToBody() throws IOException
/*      */   {
/* 1372 */     for (FontDetails details : this.documentFonts.values()) {
/* 1373 */       details.writeFont(this);
/*      */     }
/*      */ 
/* 1376 */     addXFormsToBody();
/*      */ 
/* 1378 */     for (PdfReaderInstance element : this.readerInstances.values()) {
/* 1379 */       this.currentPdfReaderInstance = element;
/* 1380 */       this.currentPdfReaderInstance.writeAllPages();
/*      */     }
/* 1382 */     this.currentPdfReaderInstance = null;
/*      */ 
/* 1384 */     for (ColorDetails color : this.documentColors.values()) {
/* 1385 */       addToBody(color.getPdfObject(this), color.getIndirectReference());
/*      */     }
/*      */ 
/* 1388 */     for (PdfPatternPainter pat : this.documentPatterns.keySet()) {
/* 1389 */       addToBody(pat.getPattern(this.compressionLevel), pat.getIndirectReference());
/*      */     }
/*      */ 
/* 1392 */     for (PdfShadingPattern shadingPattern : this.documentShadingPatterns) {
/* 1393 */       shadingPattern.addToBody();
/*      */     }
/*      */ 
/* 1396 */     for (PdfShading shading : this.documentShadings) {
/* 1397 */       shading.addToBody();
/*      */     }
/*      */ 
/* 1400 */     for (Map.Entry entry : this.documentExtGState.entrySet()) {
/* 1401 */       PdfDictionary gstate = (PdfDictionary)entry.getKey();
/* 1402 */       PdfObject[] obj = (PdfObject[])entry.getValue();
/* 1403 */       addToBody(gstate, (PdfIndirectReference)obj[1]);
/*      */     }
/*      */ 
/* 1406 */     for (Map.Entry entry : this.documentProperties.entrySet()) {
/* 1407 */       Object prop = entry.getKey();
/* 1408 */       PdfObject[] obj = (PdfObject[])entry.getValue();
/* 1409 */       if ((prop instanceof PdfLayerMembership)) {
/* 1410 */         PdfLayerMembership layer = (PdfLayerMembership)prop;
/* 1411 */         addToBody(layer.getPdfObject(), layer.getRef());
/*      */       }
/* 1413 */       else if (((prop instanceof PdfDictionary)) && (!(prop instanceof PdfLayer))) {
/* 1414 */         addToBody((PdfDictionary)prop, (PdfIndirectReference)obj[1]);
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   public PdfOutline getRootOutline()
/*      */   {
/* 1430 */     return this.directContent.getRootOutline();
/*      */   }
/*      */ 
/*      */   public void setOutlines(List<HashMap<String, Object>> outlines)
/*      */   {
/* 1441 */     this.newBookmarks = outlines;
/*      */   }
/*      */ 
/*      */   protected void writeOutlines(PdfDictionary catalog, boolean namedAsNames) throws IOException {
/* 1445 */     if ((this.newBookmarks == null) || (this.newBookmarks.isEmpty()))
/* 1446 */       return;
/* 1447 */     PdfDictionary top = new PdfDictionary();
/* 1448 */     PdfIndirectReference topRef = getPdfIndirectReference();
/* 1449 */     Object[] kids = SimpleBookmark.iterateOutlines(this, topRef, this.newBookmarks, namedAsNames);
/* 1450 */     top.put(PdfName.FIRST, (PdfIndirectReference)kids[0]);
/* 1451 */     top.put(PdfName.LAST, (PdfIndirectReference)kids[1]);
/* 1452 */     top.put(PdfName.COUNT, new PdfNumber(((Integer)kids[2]).intValue()));
/* 1453 */     addToBody(top, topRef);
/* 1454 */     catalog.put(PdfName.OUTLINES, topRef);
/*      */   }
/*      */ 
/*      */   public void setPdfVersion(char version)
/*      */   {
/* 1489 */     this.pdf_version.setPdfVersion(version);
/*      */   }
/*      */ 
/*      */   public void setAtLeastPdfVersion(char version)
/*      */   {
/* 1494 */     this.pdf_version.setAtLeastPdfVersion(version);
/*      */   }
/*      */ 
/*      */   public void setPdfVersion(PdfName version)
/*      */   {
/* 1499 */     this.pdf_version.setPdfVersion(version);
/*      */   }
/*      */ 
/*      */   public void addDeveloperExtension(PdfDeveloperExtension de)
/*      */   {
/* 1507 */     this.pdf_version.addDeveloperExtension(de);
/*      */   }
/*      */ 
/*      */   PdfVersionImp getPdfVersion()
/*      */   {
/* 1515 */     return this.pdf_version;
/*      */   }
/*      */ 
/*      */   public void setViewerPreferences(int preferences)
/*      */   {
/* 1584 */     this.pdf.setViewerPreferences(preferences);
/*      */   }
/*      */ 
/*      */   public void addViewerPreference(PdfName key, PdfObject value)
/*      */   {
/* 1589 */     this.pdf.addViewerPreference(key, value);
/*      */   }
/*      */ 
/*      */   public void setPageLabels(PdfPageLabels pageLabels)
/*      */   {
/* 1599 */     this.pdf.setPageLabels(pageLabels);
/*      */   }
/*      */ 
/*      */   public void addNamedDestinations(Map<String, String> map, int page_offset)
/*      */   {
/* 1619 */     for (Map.Entry entry : map.entrySet()) {
/* 1620 */       String dest = (String)entry.getValue();
/* 1621 */       int page = Integer.parseInt(dest.substring(0, dest.indexOf(" ")));
/* 1622 */       PdfDestination destination = new PdfDestination(dest.substring(dest.indexOf(" ") + 1));
/* 1623 */       addNamedDestination((String)entry.getKey(), page + page_offset, destination);
/*      */     }
/*      */   }
/*      */ 
/*      */   public void addNamedDestination(String name, int page, PdfDestination dest)
/*      */   {
/* 1635 */     dest.addPage(getPageReference(page));
/* 1636 */     this.pdf.localDestination(name, dest);
/*      */   }
/*      */ 
/*      */   public void addJavaScript(PdfAction js)
/*      */   {
/* 1645 */     this.pdf.addJavaScript(js);
/*      */   }
/*      */ 
/*      */   public void addJavaScript(String code, boolean unicode)
/*      */   {
/* 1657 */     addJavaScript(PdfAction.javaScript(code, this, unicode));
/*      */   }
/*      */ 
/*      */   public void addJavaScript(String code)
/*      */   {
/* 1666 */     addJavaScript(code, false);
/*      */   }
/*      */ 
/*      */   public void addJavaScript(String name, PdfAction js)
/*      */   {
/* 1675 */     this.pdf.addJavaScript(name, js);
/*      */   }
/*      */ 
/*      */   public void addJavaScript(String name, String code, boolean unicode)
/*      */   {
/* 1688 */     addJavaScript(name, PdfAction.javaScript(code, this, unicode));
/*      */   }
/*      */ 
/*      */   public void addJavaScript(String name, String code)
/*      */   {
/* 1698 */     addJavaScript(name, code, false);
/*      */   }
/*      */ 
/*      */   public void addFileAttachment(String description, byte[] fileStore, String file, String fileDisplay)
/*      */     throws IOException
/*      */   {
/* 1712 */     addFileAttachment(description, PdfFileSpecification.fileEmbedded(this, file, fileDisplay, fileStore));
/*      */   }
/*      */ 
/*      */   public void addFileAttachment(String description, PdfFileSpecification fs)
/*      */     throws IOException
/*      */   {
/* 1722 */     this.pdf.addFileAttachment(description, fs);
/*      */   }
/*      */ 
/*      */   public void addFileAttachment(PdfFileSpecification fs)
/*      */     throws IOException
/*      */   {
/* 1731 */     addFileAttachment(null, fs);
/*      */   }
/*      */ 
/*      */   public void setOpenAction(String name)
/*      */   {
/* 1749 */     this.pdf.setOpenAction(name);
/*      */   }
/*      */ 
/*      */   public void setOpenAction(PdfAction action)
/*      */   {
/* 1754 */     this.pdf.setOpenAction(action);
/*      */   }
/*      */ 
/*      */   public void setAdditionalAction(PdfName actionType, PdfAction action) throws DocumentException
/*      */   {
/* 1759 */     if ((!actionType.equals(DOCUMENT_CLOSE)) && (!actionType.equals(WILL_SAVE)) && (!actionType.equals(DID_SAVE)) && (!actionType.equals(WILL_PRINT)) && (!actionType.equals(DID_PRINT)))
/*      */     {
/* 1764 */       throw new DocumentException(MessageLocalization.getComposedMessage("invalid.additional.action.type.1", new Object[] { actionType.toString() }));
/*      */     }
/* 1766 */     this.pdf.addAdditionalAction(actionType, action);
/*      */   }
/*      */ 
/*      */   public void setCollection(PdfCollection collection)
/*      */   {
/* 1776 */     setAtLeastPdfVersion('7');
/* 1777 */     this.pdf.setCollection(collection);
/*      */   }
/*      */ 
/*      */   public PdfAcroForm getAcroForm()
/*      */   {
/* 1789 */     return this.pdf.getAcroForm();
/*      */   }
/*      */ 
/*      */   public void addAnnotation(PdfAnnotation annot)
/*      */   {
/* 1794 */     this.pdf.addAnnotation(annot);
/*      */   }
/*      */ 
/*      */   void addAnnotation(PdfAnnotation annot, int page) {
/* 1798 */     addAnnotation(annot);
/*      */   }
/*      */ 
/*      */   public void addCalculationOrder(PdfFormField annot)
/*      */   {
/* 1803 */     this.pdf.addCalculationOrder(annot);
/*      */   }
/*      */ 
/*      */   public void setSigFlags(int f)
/*      */   {
/* 1808 */     this.pdf.setSigFlags(f);
/*      */   }
/*      */ 
/*      */   public void setLanguage(String language) {
/* 1812 */     this.pdf.setLanguage(language);
/*      */   }
/*      */ 
/*      */   public void setXmpMetadata(byte[] xmpMetadata)
/*      */   {
/* 1825 */     this.xmpMetadata = xmpMetadata;
/*      */   }
/*      */ 
/*      */   public void setPageXmpMetadata(byte[] xmpMetadata)
/*      */     throws IOException
/*      */   {
/* 1834 */     this.pdf.setXmpMetadata(xmpMetadata);
/*      */   }
/*      */ 
/*      */   public XmpWriter getXmpWriter()
/*      */   {
/* 1840 */     return this.xmpWriter;
/*      */   }
/*      */ 
/*      */   public void createXmpMetadata()
/*      */   {
/*      */     try
/*      */     {
/* 1850 */       this.xmpWriter = createXmpWriter(null, this.pdf.getInfo());
/* 1851 */       if (isTagged()) {
/*      */         try {
/* 1853 */           this.xmpWriter.getXmpMeta().setPropertyInteger("http://www.aiim.org/pdfua/ns/id/", "part", 1, new PropertyOptions(1073741824));
/*      */         } catch (XMPException e) {
/* 1855 */           throw new ExceptionConverter(e);
/*      */         }
/*      */       }
/* 1858 */       this.xmpMetadata = null;
/*      */     } catch (IOException ioe) {
/* 1860 */       ioe.printStackTrace();
/*      */     }
/*      */   }
/*      */ 
/*      */   protected PdfIsoConformance initPdfIsoConformance()
/*      */   {
/* 1876 */     return new PdfXConformanceImp(this);
/*      */   }
/*      */ 
/*      */   public void setPDFXConformance(int pdfx)
/*      */   {
/* 1881 */     if (!(this.pdfIsoConformance instanceof PdfXConformanceImp))
/* 1882 */       return;
/* 1883 */     if (((PdfXConformance)this.pdfIsoConformance).getPDFXConformance() == pdfx)
/* 1884 */       return;
/* 1885 */     if (this.pdf.isOpen())
/* 1886 */       throw new PdfXConformanceException(MessageLocalization.getComposedMessage("pdfx.conformance.can.only.be.set.before.opening.the.document", new Object[0]));
/* 1887 */     if (this.crypto != null)
/* 1888 */       throw new PdfXConformanceException(MessageLocalization.getComposedMessage("a.pdfx.conforming.document.cannot.be.encrypted", new Object[0]));
/* 1889 */     if (pdfx != 0)
/* 1890 */       setPdfVersion('3');
/* 1891 */     ((PdfXConformance)this.pdfIsoConformance).setPDFXConformance(pdfx);
/*      */   }
/*      */ 
/*      */   public int getPDFXConformance()
/*      */   {
/* 1896 */     if ((this.pdfIsoConformance instanceof PdfXConformanceImp)) {
/* 1897 */       return ((PdfXConformance)this.pdfIsoConformance).getPDFXConformance();
/*      */     }
/* 1899 */     return 0;
/*      */   }
/*      */ 
/*      */   public boolean isPdfX()
/*      */   {
/* 1904 */     if ((this.pdfIsoConformance instanceof PdfXConformanceImp)) {
/* 1905 */       return ((PdfXConformance)this.pdfIsoConformance).isPdfX();
/*      */     }
/* 1907 */     return false;
/*      */   }
/*      */ 
/*      */   public boolean isPdfIso()
/*      */   {
/* 1915 */     return this.pdfIsoConformance.isPdfIso();
/*      */   }
/*      */ 
/*      */   public void setOutputIntents(String outputConditionIdentifier, String outputCondition, String registryName, String info, ICC_Profile colorProfile)
/*      */     throws IOException
/*      */   {
/* 1932 */     checkPdfIsoConformance(this, 19, colorProfile);
/* 1933 */     getExtraCatalog();
/* 1934 */     PdfDictionary out = new PdfDictionary(PdfName.OUTPUTINTENT);
/* 1935 */     if (outputCondition != null)
/* 1936 */       out.put(PdfName.OUTPUTCONDITION, new PdfString(outputCondition, "UnicodeBig"));
/* 1937 */     if (outputConditionIdentifier != null)
/* 1938 */       out.put(PdfName.OUTPUTCONDITIONIDENTIFIER, new PdfString(outputConditionIdentifier, "UnicodeBig"));
/* 1939 */     if (registryName != null)
/* 1940 */       out.put(PdfName.REGISTRYNAME, new PdfString(registryName, "UnicodeBig"));
/* 1941 */     if (info != null)
/* 1942 */       out.put(PdfName.INFO, new PdfString(info, "UnicodeBig"));
/* 1943 */     if (colorProfile != null) {
/* 1944 */       PdfStream stream = new PdfICCBased(colorProfile, this.compressionLevel);
/* 1945 */       out.put(PdfName.DESTOUTPUTPROFILE, addToBody(stream).getIndirectReference());
/*      */     }
/*      */ 
/* 1948 */     out.put(PdfName.S, PdfName.GTS_PDFX);
/*      */ 
/* 1950 */     this.extraCatalog.put(PdfName.OUTPUTINTENTS, new PdfArray(out));
/* 1951 */     this.colorProfile = colorProfile;
/*      */   }
/*      */ 
/*      */   public void setOutputIntents(String outputConditionIdentifier, String outputCondition, String registryName, String info, byte[] destOutputProfile)
/*      */     throws IOException
/*      */   {
/* 1969 */     ICC_Profile colorProfile = destOutputProfile == null ? null : ICC_Profile.getInstance(destOutputProfile);
/* 1970 */     setOutputIntents(outputConditionIdentifier, outputCondition, registryName, info, colorProfile);
/*      */   }
/*      */ 
/*      */   public boolean setOutputIntents(PdfReader reader, boolean checkExistence)
/*      */     throws IOException
/*      */   {
/* 1985 */     PdfDictionary catalog = reader.getCatalog();
/* 1986 */     PdfArray outs = catalog.getAsArray(PdfName.OUTPUTINTENTS);
/* 1987 */     if (outs == null)
/* 1988 */       return false;
/* 1989 */     if (outs.isEmpty())
/* 1990 */       return false;
/* 1991 */     PdfDictionary out = outs.getAsDict(0);
/* 1992 */     PdfObject obj = PdfReader.getPdfObject(out.get(PdfName.S));
/* 1993 */     if ((obj == null) || (!PdfName.GTS_PDFX.equals(obj)))
/* 1994 */       return false;
/* 1995 */     if (checkExistence)
/* 1996 */       return true;
/* 1997 */     PRStream stream = (PRStream)PdfReader.getPdfObject(out.get(PdfName.DESTOUTPUTPROFILE));
/* 1998 */     byte[] destProfile = null;
/* 1999 */     if (stream != null) {
/* 2000 */       destProfile = PdfReader.getStreamBytes(stream);
/*      */     }
/* 2002 */     setOutputIntents(getNameString(out, PdfName.OUTPUTCONDITIONIDENTIFIER), getNameString(out, PdfName.OUTPUTCONDITION), getNameString(out, PdfName.REGISTRYNAME), getNameString(out, PdfName.INFO), destProfile);
/*      */ 
/* 2004 */     return true;
/*      */   }
/*      */ 
/*      */   private static String getNameString(PdfDictionary dic, PdfName key) {
/* 2008 */     PdfObject obj = PdfReader.getPdfObject(dic.get(key));
/* 2009 */     if ((obj == null) || (!obj.isString()))
/* 2010 */       return null;
/* 2011 */     return ((PdfString)obj).toUnicodeString();
/*      */   }
/*      */ 
/*      */   PdfEncryption getEncryption()
/*      */   {
/* 2124 */     return this.crypto;
/*      */   }
/*      */ 
/*      */   public void setEncryption(byte[] userPassword, byte[] ownerPassword, int permissions, int encryptionType) throws DocumentException
/*      */   {
/* 2129 */     if (this.pdf.isOpen())
/* 2130 */       throw new DocumentException(MessageLocalization.getComposedMessage("encryption.can.only.be.added.before.opening.the.document", new Object[0]));
/* 2131 */     this.crypto = new PdfEncryption();
/* 2132 */     this.crypto.setCryptoMode(encryptionType, 0);
/* 2133 */     this.crypto.setupAllKeys(userPassword, ownerPassword, permissions);
/*      */   }
/*      */ 
/*      */   public void setEncryption(Certificate[] certs, int[] permissions, int encryptionType) throws DocumentException
/*      */   {
/* 2138 */     if (this.pdf.isOpen())
/* 2139 */       throw new DocumentException(MessageLocalization.getComposedMessage("encryption.can.only.be.added.before.opening.the.document", new Object[0]));
/* 2140 */     this.crypto = new PdfEncryption();
/* 2141 */     if (certs != null) {
/* 2142 */       for (int i = 0; i < certs.length; i++) {
/* 2143 */         this.crypto.addRecipient(certs[i], permissions[i]);
/*      */       }
/*      */     }
/* 2146 */     this.crypto.setCryptoMode(encryptionType, 0);
/* 2147 */     this.crypto.getEncryptionDictionary();
/*      */   }
/*      */ 
/*      */   @Deprecated
/*      */   public void setEncryption(byte[] userPassword, byte[] ownerPassword, int permissions, boolean strength128Bits)
/*      */     throws DocumentException
/*      */   {
/* 2166 */     setEncryption(userPassword, ownerPassword, permissions, strength128Bits ? 1 : 0);
/*      */   }
/*      */ 
/*      */   @Deprecated
/*      */   public void setEncryption(boolean strength, String userPassword, String ownerPassword, int permissions)
/*      */     throws DocumentException
/*      */   {
/* 2185 */     setEncryption(getISOBytes(userPassword), getISOBytes(ownerPassword), permissions, strength ? 1 : 0);
/*      */   }
/*      */ 
/*      */   @Deprecated
/*      */   public void setEncryption(int encryptionType, String userPassword, String ownerPassword, int permissions)
/*      */     throws DocumentException
/*      */   {
/* 2205 */     setEncryption(getISOBytes(userPassword), getISOBytes(ownerPassword), permissions, encryptionType);
/*      */   }
/*      */ 
/*      */   public boolean isFullCompression()
/*      */   {
/* 2218 */     return this.fullCompression;
/*      */   }
/*      */ 
/*      */   public void setFullCompression()
/*      */     throws DocumentException
/*      */   {
/* 2227 */     if (this.open)
/* 2228 */       throw new DocumentException(MessageLocalization.getComposedMessage("you.can.t.set.the.full.compression.if.the.document.is.already.open", new Object[0]));
/* 2229 */     this.fullCompression = true;
/* 2230 */     setAtLeastPdfVersion('5');
/*      */   }
/*      */ 
/*      */   public int getCompressionLevel()
/*      */   {
/* 2245 */     return this.compressionLevel;
/*      */   }
/*      */ 
/*      */   public void setCompressionLevel(int compressionLevel)
/*      */   {
/* 2254 */     if ((compressionLevel < 0) || (compressionLevel > 9))
/* 2255 */       this.compressionLevel = -1;
/*      */     else
/* 2257 */       this.compressionLevel = compressionLevel;
/*      */   }
/*      */ 
/*      */   FontDetails addSimple(BaseFont bf)
/*      */   {
/* 2277 */     FontDetails ret = (FontDetails)this.documentFonts.get(bf);
/* 2278 */     if (ret == null) {
/* 2279 */       checkPdfIsoConformance(this, 4, bf);
/* 2280 */       if (bf.getFontType() == 4)
/* 2281 */         ret = new FontDetails(new PdfName("F" + this.fontNumber++), ((DocumentFont)bf).getIndirectReference(), bf);
/*      */       else {
/* 2283 */         ret = new FontDetails(new PdfName("F" + this.fontNumber++), this.body.getPdfIndirectReference(), bf);
/*      */       }
/* 2285 */       this.documentFonts.put(bf, ret);
/*      */     }
/* 2287 */     return ret;
/*      */   }
/*      */ 
/*      */   void eliminateFontSubset(PdfDictionary fonts) {
/* 2291 */     for (Object element : this.documentFonts.values()) {
/* 2292 */       FontDetails ft = (FontDetails)element;
/* 2293 */       if (fonts.get(ft.getFontName()) != null)
/* 2294 */         ft.setSubset(false);
/*      */     }
/*      */   }
/*      */ 
/*      */   PdfName addDirectTemplateSimple(PdfTemplate template, PdfName forcedName)
/*      */   {
/* 2315 */     PdfIndirectReference ref = template.getIndirectReference();
/* 2316 */     Object[] obj = (Object[])this.formXObjects.get(ref);
/* 2317 */     PdfName name = null;
/*      */     try {
/* 2319 */       if (obj == null) {
/* 2320 */         if (forcedName == null) {
/* 2321 */           name = new PdfName("Xf" + this.formXObjectsCounter);
/* 2322 */           this.formXObjectsCounter += 1;
/*      */         }
/*      */         else {
/* 2325 */           name = forcedName;
/* 2326 */         }if (template.getType() == 2)
/*      */         {
/* 2328 */           PdfImportedPage ip = (PdfImportedPage)template;
/* 2329 */           PdfReader r = ip.getPdfReaderInstance().getReader();
/* 2330 */           if (!this.readerInstances.containsKey(r)) {
/* 2331 */             this.readerInstances.put(r, ip.getPdfReaderInstance());
/*      */           }
/* 2333 */           template = null;
/*      */         }
/* 2335 */         this.formXObjects.put(ref, new Object[] { name, template });
/*      */       }
/*      */       else {
/* 2338 */         name = (PdfName)obj[0];
/*      */       }
/*      */     } catch (Exception e) {
/* 2341 */       throw new ExceptionConverter(e);
/*      */     }
/* 2343 */     return name;
/*      */   }
/*      */ 
/*      */   public void releaseTemplate(PdfTemplate tp)
/*      */     throws IOException
/*      */   {
/* 2355 */     PdfIndirectReference ref = tp.getIndirectReference();
/* 2356 */     Object[] objs = (Object[])this.formXObjects.get(ref);
/* 2357 */     if ((objs == null) || (objs[1] == null))
/* 2358 */       return;
/* 2359 */     PdfTemplate template = (PdfTemplate)objs[1];
/* 2360 */     if ((template.getIndirectReference() instanceof PRIndirectReference))
/* 2361 */       return;
/* 2362 */     if (template.getType() == 1) {
/* 2363 */       addToBody(template.getFormXObject(this.compressionLevel), template.getIndirectReference());
/* 2364 */       objs[1] = null;
/*      */     }
/*      */   }
/*      */ 
/*      */   public PdfImportedPage getImportedPage(PdfReader reader, int pageNumber)
/*      */   {
/* 2386 */     return getPdfReaderInstance(reader).getImportedPage(pageNumber);
/*      */   }
/*      */ 
/*      */   protected PdfReaderInstance getPdfReaderInstance(PdfReader reader)
/*      */   {
/* 2398 */     PdfReaderInstance inst = (PdfReaderInstance)this.readerInstances.get(reader);
/* 2399 */     if (inst == null) {
/* 2400 */       inst = reader.getPdfReaderInstance(this);
/* 2401 */       this.readerInstances.put(reader, inst);
/*      */     }
/* 2403 */     return inst;
/*      */   }
/*      */ 
/*      */   public void freeReader(PdfReader reader)
/*      */     throws IOException
/*      */   {
/* 2416 */     this.currentPdfReaderInstance = ((PdfReaderInstance)this.readerInstances.get(reader));
/* 2417 */     if (this.currentPdfReaderInstance == null)
/* 2418 */       return;
/* 2419 */     this.currentPdfReaderInstance.writeAllPages();
/* 2420 */     this.currentPdfReaderInstance = null;
/* 2421 */     this.readerInstances.remove(reader);
/*      */   }
/*      */ 
/*      */   public long getCurrentDocumentSize()
/*      */   {
/* 2434 */     return this.body.offset() + this.body.size() * 20 + 72L;
/*      */   }
/*      */ 
/*      */   protected int getNewObjectNumber(PdfReader reader, int number, int generation)
/*      */   {
/* 2440 */     if ((this.currentPdfReaderInstance == null) || (this.currentPdfReaderInstance.getReader() != reader)) {
/* 2441 */       this.currentPdfReaderInstance = getPdfReaderInstance(reader);
/*      */     }
/* 2443 */     return this.currentPdfReaderInstance.getNewObjectNumber(number, generation);
/*      */   }
/*      */ 
/*      */   RandomAccessFileOrArray getReaderFile(PdfReader reader) {
/* 2447 */     return this.currentPdfReaderInstance.getReaderFile();
/*      */   }
/*      */ 
/*      */   PdfName getColorspaceName()
/*      */   {
/* 2459 */     return new PdfName("CS" + this.colorNumber++);
/*      */   }
/*      */ 
/*      */   ColorDetails addSimple(ICachedColorSpace spc)
/*      */   {
/* 2469 */     ColorDetails ret = (ColorDetails)this.documentColors.get(spc);
/* 2470 */     if (ret == null) {
/* 2471 */       ret = new ColorDetails(getColorspaceName(), this.body.getPdfIndirectReference(), spc);
/* 2472 */       if ((spc instanceof IPdfSpecialColorSpace)) {
/* 2473 */         ((IPdfSpecialColorSpace)spc).getColorantDetails(this);
/*      */       }
/* 2475 */       this.documentColors.put(spc, ret);
/*      */     }
/* 2477 */     return ret;
/*      */   }
/*      */ 
/*      */   PdfName addSimplePattern(PdfPatternPainter painter)
/*      */   {
/* 2489 */     PdfName name = (PdfName)this.documentPatterns.get(painter);
/*      */     try {
/* 2491 */       if (name == null) {
/* 2492 */         name = new PdfName("P" + this.patternNumber);
/* 2493 */         this.patternNumber += 1;
/* 2494 */         this.documentPatterns.put(painter, name);
/*      */       }
/*      */     } catch (Exception e) {
/* 2497 */       throw new ExceptionConverter(e);
/*      */     }
/* 2499 */     return name;
/*      */   }
/*      */ 
/*      */   void addSimpleShadingPattern(PdfShadingPattern shading)
/*      */   {
/* 2507 */     if (!this.documentShadingPatterns.contains(shading)) {
/* 2508 */       shading.setName(this.patternNumber);
/* 2509 */       this.patternNumber += 1;
/* 2510 */       this.documentShadingPatterns.add(shading);
/* 2511 */       addSimpleShading(shading.getShading());
/*      */     }
/*      */   }
/*      */ 
/*      */   void addSimpleShading(PdfShading shading)
/*      */   {
/* 2520 */     if (!this.documentShadings.contains(shading)) {
/* 2521 */       this.documentShadings.add(shading);
/* 2522 */       shading.setName(this.documentShadings.size());
/*      */     }
/*      */   }
/*      */ 
/*      */   PdfObject[] addSimpleExtGState(PdfDictionary gstate)
/*      */   {
/* 2531 */     if (!this.documentExtGState.containsKey(gstate)) {
/* 2532 */       this.documentExtGState.put(gstate, new PdfObject[] { new PdfName("GS" + (this.documentExtGState.size() + 1)), getPdfIndirectReference() });
/*      */     }
/* 2534 */     return (PdfObject[])this.documentExtGState.get(gstate);
/*      */   }
/*      */ 
/*      */   PdfObject[] addSimpleProperty(Object prop, PdfIndirectReference refi)
/*      */   {
/* 2541 */     if (!this.documentProperties.containsKey(prop)) {
/* 2542 */       if ((prop instanceof PdfOCG))
/* 2543 */         checkPdfIsoConformance(this, 7, prop);
/* 2544 */       this.documentProperties.put(prop, new PdfObject[] { new PdfName("Pr" + (this.documentProperties.size() + 1)), refi });
/*      */     }
/* 2546 */     return (PdfObject[])this.documentProperties.get(prop);
/*      */   }
/*      */ 
/*      */   boolean propertyExists(Object prop) {
/* 2550 */     return this.documentProperties.containsKey(prop);
/*      */   }
/*      */ 
/*      */   public void setTagged()
/*      */   {
/* 2566 */     setTagged(1);
/*      */   }
/*      */ 
/*      */   public void setTagged(int taggingMode) {
/* 2570 */     if (this.open)
/* 2571 */       throw new IllegalArgumentException(MessageLocalization.getComposedMessage("tagging.must.be.set.before.opening.the.document", new Object[0]));
/* 2572 */     this.tagged = true;
/* 2573 */     this.taggingMode = taggingMode;
/*      */   }
/*      */ 
/*      */   public boolean needToBeMarkedInContent(IAccessibleElement element) {
/* 2577 */     if ((this.taggingMode & 0x1) != 0) {
/* 2578 */       if ((element.isInline()) || (PdfName.ARTIFACT.equals(element.getRole()))) {
/* 2579 */         return true;
/*      */       }
/* 2581 */       return false;
/*      */     }
/* 2583 */     return true;
/*      */   }
/*      */ 
/*      */   public void checkElementRole(IAccessibleElement element, IAccessibleElement parent) {
/* 2587 */     if ((parent != null) && ((parent.getRole() == null) || (PdfName.ARTIFACT.equals(parent.getRole()))))
/* 2588 */       element.setRole(null);
/* 2589 */     else if (((this.taggingMode & 0x1) != 0) && 
/* 2590 */       (element.isInline()) && (element.getRole() == null) && (
/* 2590 */       (parent == null) || (!parent.isInline())))
/* 2591 */       throw new IllegalArgumentException(MessageLocalization.getComposedMessage("inline.elements.with.role.null.are.not.allowed", new Object[0]));
/*      */   }
/*      */ 
/*      */   public boolean isTagged()
/*      */   {
/* 2600 */     return this.tagged;
/*      */   }
/*      */ 
/*      */   protected void flushTaggedObjects()
/*      */     throws IOException
/*      */   {
/*      */   }
/*      */ 
/*      */   protected void flushAcroFields()
/*      */     throws IOException, BadPdfFormatException
/*      */   {
/*      */   }
/*      */ 
/*      */   public PdfStructureTreeRoot getStructureTreeRoot()
/*      */   {
/* 2616 */     if ((this.tagged) && (this.structureTreeRoot == null))
/* 2617 */       this.structureTreeRoot = new PdfStructureTreeRoot(this);
/* 2618 */     return this.structureTreeRoot;
/*      */   }
/*      */ 
/*      */   public PdfOCProperties getOCProperties()
/*      */   {
/* 2644 */     fillOCProperties(true);
/* 2645 */     return this.OCProperties;
/*      */   }
/*      */ 
/*      */   public void addOCGRadioGroup(ArrayList<PdfLayer> group)
/*      */   {
/* 2657 */     PdfArray ar = new PdfArray();
/* 2658 */     for (int k = 0; k < group.size(); k++) {
/* 2659 */       PdfLayer layer = (PdfLayer)group.get(k);
/* 2660 */       if (layer.getTitle() == null)
/* 2661 */         ar.add(layer.getRef());
/*      */     }
/* 2663 */     if (ar.size() == 0)
/* 2664 */       return;
/* 2665 */     this.OCGRadioGroup.add(ar);
/*      */   }
/*      */ 
/*      */   public void lockLayer(PdfLayer layer)
/*      */   {
/* 2677 */     this.OCGLocked.add(layer.getRef());
/*      */   }
/*      */ 
/*      */   private static void getOCGOrder(PdfArray order, PdfLayer layer) {
/* 2681 */     if (!layer.isOnPanel())
/* 2682 */       return;
/* 2683 */     if (layer.getTitle() == null)
/* 2684 */       order.add(layer.getRef());
/* 2685 */     ArrayList children = layer.getChildren();
/* 2686 */     if (children == null)
/* 2687 */       return;
/* 2688 */     PdfArray kids = new PdfArray();
/* 2689 */     if (layer.getTitle() != null)
/* 2690 */       kids.add(new PdfString(layer.getTitle(), "UnicodeBig"));
/* 2691 */     for (int k = 0; k < children.size(); k++) {
/* 2692 */       getOCGOrder(kids, (PdfLayer)children.get(k));
/*      */     }
/* 2694 */     if (kids.size() > 0)
/* 2695 */       order.add(kids);
/*      */   }
/*      */ 
/*      */   private void addASEvent(PdfName event, PdfName category) {
/* 2699 */     PdfArray arr = new PdfArray();
/* 2700 */     for (Object element : this.documentOCG) {
/* 2701 */       PdfLayer layer = (PdfLayer)element;
/* 2702 */       PdfDictionary usage = layer.getAsDict(PdfName.USAGE);
/* 2703 */       if ((usage != null) && (usage.get(category) != null))
/* 2704 */         arr.add(layer.getRef());
/*      */     }
/* 2706 */     if (arr.size() == 0)
/* 2707 */       return;
/* 2708 */     PdfDictionary d = this.OCProperties.getAsDict(PdfName.D);
/* 2709 */     PdfArray arras = d.getAsArray(PdfName.AS);
/* 2710 */     if (arras == null) {
/* 2711 */       arras = new PdfArray();
/* 2712 */       d.put(PdfName.AS, arras);
/*      */     }
/* 2714 */     PdfDictionary as = new PdfDictionary();
/* 2715 */     as.put(PdfName.EVENT, event);
/* 2716 */     as.put(PdfName.CATEGORY, new PdfArray(category));
/* 2717 */     as.put(PdfName.OCGS, arr);
/* 2718 */     arras.add(as);
/*      */   }
/*      */ 
/*      */   protected void fillOCProperties(boolean erase)
/*      */   {
/* 2726 */     if (this.OCProperties == null)
/* 2727 */       this.OCProperties = new PdfOCProperties();
/* 2728 */     if (erase) {
/* 2729 */       this.OCProperties.remove(PdfName.OCGS);
/* 2730 */       this.OCProperties.remove(PdfName.D);
/*      */     }
/* 2732 */     if (this.OCProperties.get(PdfName.OCGS) == null) {
/* 2733 */       PdfArray gr = new PdfArray();
/* 2734 */       for (Object element : this.documentOCG) {
/* 2735 */         PdfLayer layer = (PdfLayer)element;
/* 2736 */         gr.add(layer.getRef());
/*      */       }
/* 2738 */       this.OCProperties.put(PdfName.OCGS, gr);
/*      */     }
/* 2740 */     if (this.OCProperties.get(PdfName.D) != null)
/* 2741 */       return;
/* 2742 */     ArrayList docOrder = new ArrayList(this.documentOCGorder);
/* 2743 */     for (Iterator it = docOrder.iterator(); it.hasNext(); ) {
/* 2744 */       PdfLayer layer = (PdfLayer)it.next();
/* 2745 */       if (layer.getParent() != null)
/* 2746 */         it.remove();
/*      */     }
/* 2748 */     PdfArray order = new PdfArray();
/* 2749 */     for (Object element : docOrder) {
/* 2750 */       PdfLayer layer = (PdfLayer)element;
/* 2751 */       getOCGOrder(order, layer);
/*      */     }
/* 2753 */     PdfDictionary d = new PdfDictionary();
/* 2754 */     this.OCProperties.put(PdfName.D, d);
/* 2755 */     d.put(PdfName.ORDER, order);
/* 2756 */     if ((docOrder.size() > 0) && ((docOrder.get(0) instanceof PdfLayer))) {
/* 2757 */       PdfLayer l = (PdfLayer)docOrder.get(0);
/* 2758 */       PdfString name = l.getAsString(PdfName.NAME);
/* 2759 */       if (name != null) {
/* 2760 */         d.put(PdfName.NAME, name);
/*      */       }
/*      */     }
/* 2763 */     PdfArray gr = new PdfArray();
/* 2764 */     for (Object element : this.documentOCG) {
/* 2765 */       PdfLayer layer = (PdfLayer)element;
/* 2766 */       if (!layer.isOn())
/* 2767 */         gr.add(layer.getRef());
/*      */     }
/* 2769 */     if (gr.size() > 0)
/* 2770 */       d.put(PdfName.OFF, gr);
/* 2771 */     if (this.OCGRadioGroup.size() > 0)
/* 2772 */       d.put(PdfName.RBGROUPS, this.OCGRadioGroup);
/* 2773 */     if (this.OCGLocked.size() > 0)
/* 2774 */       d.put(PdfName.LOCKED, this.OCGLocked);
/* 2775 */     addASEvent(PdfName.VIEW, PdfName.ZOOM);
/* 2776 */     addASEvent(PdfName.VIEW, PdfName.VIEW);
/* 2777 */     addASEvent(PdfName.PRINT, PdfName.PRINT);
/* 2778 */     addASEvent(PdfName.EXPORT, PdfName.EXPORT);
/* 2779 */     d.put(PdfName.LISTMODE, PdfName.VISIBLEPAGES);
/*      */   }
/*      */ 
/*      */   void registerLayer(PdfOCG layer) {
/* 2783 */     checkPdfIsoConformance(this, 7, layer);
/* 2784 */     if ((layer instanceof PdfLayer)) {
/* 2785 */       PdfLayer la = (PdfLayer)layer;
/* 2786 */       if (la.getTitle() == null) {
/* 2787 */         if (!this.documentOCG.contains(layer)) {
/* 2788 */           this.documentOCG.add(layer);
/* 2789 */           this.documentOCGorder.add(layer);
/*      */         }
/*      */       }
/*      */       else
/* 2793 */         this.documentOCGorder.add(layer);
/*      */     }
/*      */     else
/*      */     {
/* 2797 */       throw new IllegalArgumentException(MessageLocalization.getComposedMessage("only.pdflayer.is.accepted", new Object[0]));
/*      */     }
/*      */   }
/*      */ 
/*      */   public com.itextpdf.text.Rectangle getPageSize()
/*      */   {
/* 2809 */     return this.pdf.getPageSize();
/*      */   }
/*      */ 
/*      */   public void setCropBoxSize(com.itextpdf.text.Rectangle crop)
/*      */   {
/* 2819 */     this.pdf.setCropBoxSize(crop);
/*      */   }
/*      */ 
/*      */   public void setBoxSize(String boxName, com.itextpdf.text.Rectangle size)
/*      */   {
/* 2829 */     this.pdf.setBoxSize(boxName, size);
/*      */   }
/*      */ 
/*      */   public com.itextpdf.text.Rectangle getBoxSize(String boxName)
/*      */   {
/* 2838 */     return this.pdf.getBoxSize(boxName);
/*      */   }
/*      */ 
/*      */   public com.itextpdf.text.Rectangle getBoxSize(String boxName, com.itextpdf.text.Rectangle intersectingRectangle)
/*      */   {
/* 2853 */     com.itextpdf.text.Rectangle pdfRectangle = this.pdf.getBoxSize(boxName);
/*      */ 
/* 2855 */     if ((pdfRectangle == null) || (intersectingRectangle == null)) {
/* 2856 */       return null;
/*      */     }
/*      */ 
/* 2859 */     com.itextpdf.awt.geom.Rectangle boxRect = new com.itextpdf.awt.geom.Rectangle(pdfRectangle);
/* 2860 */     com.itextpdf.awt.geom.Rectangle intRect = new com.itextpdf.awt.geom.Rectangle(intersectingRectangle);
/* 2861 */     com.itextpdf.awt.geom.Rectangle outRect = boxRect.intersection(intRect);
/*      */ 
/* 2863 */     if (outRect.isEmpty()) {
/* 2864 */       return null;
/*      */     }
/*      */ 
/* 2867 */     com.itextpdf.text.Rectangle output = new com.itextpdf.text.Rectangle((float)outRect.getX(), (float)outRect.getY(), (float)(outRect.getX() + outRect.getWidth()), (float)(outRect.getY() + outRect.getHeight()));
/* 2868 */     output.normalize();
/* 2869 */     return output;
/*      */   }
/*      */ 
/*      */   public void setPageEmpty(boolean pageEmpty)
/*      */   {
/* 2882 */     if (pageEmpty)
/* 2883 */       return;
/* 2884 */     this.pdf.setPageEmpty(pageEmpty);
/*      */   }
/*      */ 
/*      */   public boolean isPageEmpty()
/*      */   {
/* 2893 */     return this.pdf.isPageEmpty();
/*      */   }
/*      */ 
/*      */   public void setPageAction(PdfName actionType, PdfAction action)
/*      */     throws DocumentException
/*      */   {
/* 2905 */     if ((!actionType.equals(PAGE_OPEN)) && (!actionType.equals(PAGE_CLOSE)))
/* 2906 */       throw new DocumentException(MessageLocalization.getComposedMessage("invalid.page.additional.action.type.1", new Object[] { actionType.toString() }));
/* 2907 */     this.pdf.setPageAction(actionType, action);
/*      */   }
/*      */ 
/*      */   public void setDuration(int seconds)
/*      */   {
/* 2912 */     this.pdf.setDuration(seconds);
/*      */   }
/*      */ 
/*      */   public void setTransition(PdfTransition transition)
/*      */   {
/* 2917 */     this.pdf.setTransition(transition);
/*      */   }
/*      */ 
/*      */   public void setThumbnail(Image image)
/*      */     throws PdfException, DocumentException
/*      */   {
/* 2929 */     this.pdf.setThumbnail(image);
/*      */   }
/*      */ 
/*      */   public PdfDictionary getGroup()
/*      */   {
/* 2946 */     return this.group;
/*      */   }
/*      */ 
/*      */   public void setGroup(PdfDictionary group)
/*      */   {
/* 2954 */     this.group = group;
/*      */   }
/*      */ 
/*      */   public float getSpaceCharRatio()
/*      */   {
/* 2976 */     return this.spaceCharRatio;
/*      */   }
/*      */ 
/*      */   public void setSpaceCharRatio(float spaceCharRatio)
/*      */   {
/* 2988 */     if (spaceCharRatio < 0.001F)
/* 2989 */       this.spaceCharRatio = 0.001F;
/*      */     else
/* 2991 */       this.spaceCharRatio = spaceCharRatio;
/*      */   }
/*      */ 
/*      */   public void setRunDirection(int runDirection)
/*      */   {
/* 3017 */     if ((runDirection < 1) || (runDirection > 3))
/* 3018 */       throw new RuntimeException(MessageLocalization.getComposedMessage("invalid.run.direction.1", runDirection));
/* 3019 */     this.runDirection = runDirection;
/*      */   }
/*      */ 
/*      */   public int getRunDirection()
/*      */   {
/* 3027 */     return this.runDirection;
/*      */   }
/*      */ 
/*      */   public void setUserunit(float userunit)
/*      */     throws DocumentException
/*      */   {
/* 3041 */     if ((userunit < 1.0F) || (userunit > 75000.0F)) throw new DocumentException(MessageLocalization.getComposedMessage("userunit.should.be.a.value.between.1.and.75000", new Object[0]));
/* 3042 */     addPageDictEntry(PdfName.USERUNIT, new PdfNumber(userunit));
/* 3043 */     setAtLeastPdfVersion('6');
/*      */   }
/*      */ 
/*      */   public PdfDictionary getDefaultColorspace()
/*      */   {
/* 3056 */     return this.defaultColorspace;
/*      */   }
/*      */ 
/*      */   public void setDefaultColorspace(PdfName key, PdfObject cs)
/*      */   {
/* 3071 */     if ((cs == null) || (cs.isNull()))
/* 3072 */       this.defaultColorspace.remove(key);
/* 3073 */     this.defaultColorspace.put(key, cs);
/*      */   }
/*      */ 
/*      */   ColorDetails addSimplePatternColorspace(BaseColor color)
/*      */   {
/* 3084 */     int type = ExtendedColor.getType(color);
/* 3085 */     if ((type == 4) || (type == 5))
/* 3086 */       throw new RuntimeException(MessageLocalization.getComposedMessage("an.uncolored.tile.pattern.can.not.have.another.pattern.or.shading.as.color", new Object[0]));
/*      */     try {
/* 3088 */       switch (type) {
/*      */       case 0:
/* 3090 */         if (this.patternColorspaceRGB == null) {
/* 3091 */           this.patternColorspaceRGB = new ColorDetails(getColorspaceName(), this.body.getPdfIndirectReference(), null);
/* 3092 */           PdfArray array = new PdfArray(PdfName.PATTERN);
/* 3093 */           array.add(PdfName.DEVICERGB);
/* 3094 */           addToBody(array, this.patternColorspaceRGB.getIndirectReference());
/*      */         }
/* 3096 */         return this.patternColorspaceRGB;
/*      */       case 2:
/* 3098 */         if (this.patternColorspaceCMYK == null) {
/* 3099 */           this.patternColorspaceCMYK = new ColorDetails(getColorspaceName(), this.body.getPdfIndirectReference(), null);
/* 3100 */           PdfArray array = new PdfArray(PdfName.PATTERN);
/* 3101 */           array.add(PdfName.DEVICECMYK);
/* 3102 */           addToBody(array, this.patternColorspaceCMYK.getIndirectReference());
/*      */         }
/* 3104 */         return this.patternColorspaceCMYK;
/*      */       case 1:
/* 3106 */         if (this.patternColorspaceGRAY == null) {
/* 3107 */           this.patternColorspaceGRAY = new ColorDetails(getColorspaceName(), this.body.getPdfIndirectReference(), null);
/* 3108 */           PdfArray array = new PdfArray(PdfName.PATTERN);
/* 3109 */           array.add(PdfName.DEVICEGRAY);
/* 3110 */           addToBody(array, this.patternColorspaceGRAY.getIndirectReference());
/*      */         }
/* 3112 */         return this.patternColorspaceGRAY;
/*      */       case 3:
/* 3114 */         ColorDetails details = addSimple(((SpotColor)color).getPdfSpotColor());
/* 3115 */         ColorDetails patternDetails = (ColorDetails)this.documentSpotPatterns.get(details);
/* 3116 */         if (patternDetails == null) {
/* 3117 */           patternDetails = new ColorDetails(getColorspaceName(), this.body.getPdfIndirectReference(), null);
/* 3118 */           PdfArray array = new PdfArray(PdfName.PATTERN);
/* 3119 */           array.add(details.getIndirectReference());
/* 3120 */           addToBody(array, patternDetails.getIndirectReference());
/* 3121 */           this.documentSpotPatterns.put(details, patternDetails);
/*      */         }
/* 3123 */         return patternDetails;
/*      */       }
/*      */ 
/* 3126 */       throw new RuntimeException(MessageLocalization.getComposedMessage("invalid.color.type", new Object[0]));
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 3130 */       throw new RuntimeException(e.getMessage());
/*      */     }
/*      */   }
/*      */ 
/*      */   public boolean isStrictImageSequence()
/*      */   {
/* 3141 */     return this.pdf.isStrictImageSequence();
/*      */   }
/*      */ 
/*      */   public void setStrictImageSequence(boolean strictImageSequence)
/*      */   {
/* 3151 */     this.pdf.setStrictImageSequence(strictImageSequence);
/*      */   }
/*      */ 
/*      */   public void clearTextWrap()
/*      */     throws DocumentException
/*      */   {
/* 3159 */     this.pdf.clearTextWrap();
/*      */   }
/*      */ 
/*      */   public PdfName addDirectImageSimple(Image image)
/*      */     throws PdfException, DocumentException
/*      */   {
/* 3179 */     return addDirectImageSimple(image, null);
/*      */   }
/*      */ 
/*      */   public PdfName addDirectImageSimple(Image image, PdfIndirectReference fixedRef)
/*      */     throws PdfException, DocumentException
/*      */   {
/*      */     PdfName name;
/*      */     PdfName name;
/* 3196 */     if (this.images.containsKey(image.getMySerialId())) {
/* 3197 */       name = (PdfName)this.images.get(image.getMySerialId());
/*      */     }
/*      */     else
/*      */     {
/* 3201 */       if (image.isImgTemplate()) {
/* 3202 */         PdfName name = new PdfName("img" + this.images.size());
/* 3203 */         if ((image instanceof ImgWMF))
/*      */           try {
/* 3205 */             ImgWMF wmf = (ImgWMF)image;
/* 3206 */             wmf.readWMF(PdfTemplate.createTemplate(this, 0.0F, 0.0F));
/*      */           }
/*      */           catch (Exception e) {
/* 3209 */             throw new DocumentException(e);
/*      */           }
/*      */       }
/*      */       else
/*      */       {
/* 3214 */         PdfIndirectReference dref = image.getDirectReference();
/* 3215 */         if (dref != null) {
/* 3216 */           PdfName rname = new PdfName("img" + this.images.size());
/* 3217 */           this.images.put(image.getMySerialId(), rname);
/* 3218 */           this.imageDictionary.put(rname, dref);
/* 3219 */           return rname;
/*      */         }
/* 3221 */         Image maskImage = image.getImageMask();
/* 3222 */         PdfIndirectReference maskRef = null;
/* 3223 */         if (maskImage != null) {
/* 3224 */           PdfName mname = (PdfName)this.images.get(maskImage.getMySerialId());
/* 3225 */           maskRef = getImageReference(mname);
/*      */         }
/* 3227 */         PdfImage i = new PdfImage(image, "img" + this.images.size(), maskRef);
/* 3228 */         if ((image instanceof ImgJBIG2)) {
/* 3229 */           byte[] globals = ((ImgJBIG2)image).getGlobalBytes();
/* 3230 */           if (globals != null) {
/* 3231 */             PdfDictionary decodeparms = new PdfDictionary();
/* 3232 */             decodeparms.put(PdfName.JBIG2GLOBALS, getReferenceJBIG2Globals(globals));
/* 3233 */             i.put(PdfName.DECODEPARMS, decodeparms);
/*      */           }
/*      */         }
/* 3236 */         if (image.hasICCProfile()) {
/* 3237 */           PdfICCBased icc = new PdfICCBased(image.getICCProfile(), image.getCompressionLevel());
/* 3238 */           PdfIndirectReference iccRef = add(icc);
/* 3239 */           PdfArray iccArray = new PdfArray();
/* 3240 */           iccArray.add(PdfName.ICCBASED);
/* 3241 */           iccArray.add(iccRef);
/* 3242 */           PdfArray colorspace = i.getAsArray(PdfName.COLORSPACE);
/* 3243 */           if (colorspace != null) {
/* 3244 */             if ((colorspace.size() > 1) && (PdfName.INDEXED.equals(colorspace.getPdfObject(0))))
/* 3245 */               colorspace.set(1, iccArray);
/*      */             else
/* 3247 */               i.put(PdfName.COLORSPACE, iccArray);
/*      */           }
/*      */           else
/* 3250 */             i.put(PdfName.COLORSPACE, iccArray);
/*      */         }
/* 3252 */         add(i, fixedRef);
/* 3253 */         name = i.name();
/*      */       }
/* 3255 */       this.images.put(image.getMySerialId(), name);
/*      */     }
/* 3257 */     return name;
/*      */   }
/*      */ 
/*      */   PdfIndirectReference add(PdfImage pdfImage, PdfIndirectReference fixedRef)
/*      */     throws PdfException
/*      */   {
/* 3270 */     if (!this.imageDictionary.contains(pdfImage.name())) {
/* 3271 */       checkPdfIsoConformance(this, 5, pdfImage);
/* 3272 */       if ((fixedRef instanceof PRIndirectReference)) {
/* 3273 */         PRIndirectReference r2 = (PRIndirectReference)fixedRef;
/* 3274 */         fixedRef = new PdfIndirectReference(0, getNewObjectNumber(r2.getReader(), r2.getNumber(), r2.getGeneration()));
/*      */       }
/*      */       try {
/* 3277 */         if (fixedRef == null)
/* 3278 */           fixedRef = addToBody(pdfImage).getIndirectReference();
/*      */         else
/* 3280 */           addToBody(pdfImage, fixedRef);
/*      */       }
/*      */       catch (IOException ioe) {
/* 3283 */         throw new ExceptionConverter(ioe);
/*      */       }
/* 3285 */       this.imageDictionary.put(pdfImage.name(), fixedRef);
/* 3286 */       return fixedRef;
/*      */     }
/* 3288 */     return (PdfIndirectReference)this.imageDictionary.get(pdfImage.name());
/*      */   }
/*      */ 
/*      */   PdfIndirectReference getImageReference(PdfName name)
/*      */   {
/* 3299 */     return (PdfIndirectReference)this.imageDictionary.get(name);
/*      */   }
/*      */ 
/*      */   protected PdfIndirectReference add(PdfICCBased icc) {
/*      */     PdfIndirectObject object;
/*      */     try {
/* 3305 */       object = addToBody(icc);
/*      */     }
/*      */     catch (IOException ioe) {
/* 3308 */       throw new ExceptionConverter(ioe);
/*      */     }
/* 3310 */     return object.getIndirectReference();
/*      */   }
/*      */ 
/*      */   protected PdfIndirectReference getReferenceJBIG2Globals(byte[] content)
/*      */   {
/* 3326 */     if (content == null) return null;
/* 3327 */     for (PdfStream stream : this.JBIG2Globals.keySet()) {
/* 3328 */       if (Arrays.equals(content, stream.getBytes())) {
/* 3329 */         return (PdfIndirectReference)this.JBIG2Globals.get(stream);
/*      */       }
/*      */     }
/* 3332 */     PdfStream stream = new PdfStream(content);
/*      */     PdfIndirectObject ref;
/*      */     try { ref = addToBody(stream);
/*      */     } catch (IOException e) {
/* 3337 */       return null;
/*      */     }
/* 3339 */     this.JBIG2Globals.put(stream, ref.getIndirectReference());
/* 3340 */     return ref.getIndirectReference();
/*      */   }
/*      */ 
/*      */   public boolean isUserProperties()
/*      */   {
/* 3354 */     return this.userProperties;
/*      */   }
/*      */ 
/*      */   public void setUserProperties(boolean userProperties)
/*      */   {
/* 3362 */     this.userProperties = userProperties;
/*      */   }
/*      */ 
/*      */   public boolean isRgbTransparencyBlending()
/*      */   {
/* 3377 */     return this.rgbTransparencyBlending;
/*      */   }
/*      */ 
/*      */   public void setRgbTransparencyBlending(boolean rgbTransparencyBlending)
/*      */   {
/* 3391 */     this.rgbTransparencyBlending = rgbTransparencyBlending;
/*      */   }
/*      */ 
/*      */   protected static void writeKeyInfo(OutputStream os) throws IOException {
/* 3395 */     Version version = Version.getInstance();
/* 3396 */     String k = version.getKey();
/* 3397 */     if (k == null) {
/* 3398 */       k = "iText";
/*      */     }
/* 3400 */     os.write(getISOBytes(String.format("%%%s-%s\n", new Object[] { k, version.getRelease() })));
/*      */   }
/*      */ 
/*      */   protected TtfUnicodeWriter getTtfUnicodeWriter()
/*      */   {
/* 3407 */     if (this.ttfUnicodeWriter == null)
/* 3408 */       this.ttfUnicodeWriter = new TtfUnicodeWriter(this);
/* 3409 */     return this.ttfUnicodeWriter;
/*      */   }
/*      */ 
/*      */   protected XmpWriter createXmpWriter(ByteArrayOutputStream baos, PdfDictionary info) throws IOException {
/* 3413 */     return new XmpWriter(baos, info);
/*      */   }
/*      */ 
/*      */   protected XmpWriter createXmpWriter(ByteArrayOutputStream baos, HashMap<String, String> info) throws IOException {
/* 3417 */     return new XmpWriter(baos, info);
/*      */   }
/*      */ 
/*      */   public static void checkPdfIsoConformance(PdfWriter writer, int key, Object obj1) {
/* 3421 */     if (writer != null)
/* 3422 */       writer.checkPdfIsoConformance(key, obj1);
/*      */   }
/*      */ 
/*      */   public void checkPdfIsoConformance(int key, Object obj1) {
/* 3426 */     this.pdfIsoConformance.checkPdfIsoConformance(key, obj1);
/*      */   }
/*      */ 
/*      */   private void completeInfoDictionary(PdfDictionary info) {
/* 3430 */     if (isPdfX()) {
/* 3431 */       if (info.get(PdfName.GTS_PDFXVERSION) == null)
/* 3432 */         if (((PdfXConformanceImp)this.pdfIsoConformance).isPdfX1A2001()) {
/* 3433 */           info.put(PdfName.GTS_PDFXVERSION, new PdfString("PDF/X-1:2001"));
/* 3434 */           info.put(new PdfName("GTS_PDFXConformance"), new PdfString("PDF/X-1a:2001"));
/*      */         }
/* 3436 */         else if (((PdfXConformanceImp)this.pdfIsoConformance).isPdfX32002()) {
/* 3437 */           info.put(PdfName.GTS_PDFXVERSION, new PdfString("PDF/X-3:2002"));
/*      */         }
/* 3439 */       if (info.get(PdfName.TITLE) == null) {
/* 3440 */         info.put(PdfName.TITLE, new PdfString("Pdf document"));
/*      */       }
/* 3442 */       if (info.get(PdfName.CREATOR) == null) {
/* 3443 */         info.put(PdfName.CREATOR, new PdfString("Unknown"));
/*      */       }
/* 3445 */       if (info.get(PdfName.TRAPPED) == null)
/* 3446 */         info.put(PdfName.TRAPPED, new PdfName("False"));
/*      */     }
/*      */   }
/*      */ 
/*      */   private void completeExtraCatalog(PdfDictionary extraCatalog)
/*      */   {
/* 3452 */     if ((isPdfX()) && 
/* 3453 */       (extraCatalog.get(PdfName.OUTPUTINTENTS) == null)) {
/* 3454 */       PdfDictionary out = new PdfDictionary(PdfName.OUTPUTINTENT);
/* 3455 */       out.put(PdfName.OUTPUTCONDITION, new PdfString("SWOP CGATS TR 001-1995"));
/* 3456 */       out.put(PdfName.OUTPUTCONDITIONIDENTIFIER, new PdfString("CGATS TR 001"));
/* 3457 */       out.put(PdfName.REGISTRYNAME, new PdfString("http://www.color.org"));
/* 3458 */       out.put(PdfName.INFO, new PdfString(""));
/* 3459 */       out.put(PdfName.S, PdfName.GTS_PDFX);
/* 3460 */       extraCatalog.put(PdfName.OUTPUTINTENTS, new PdfArray(out));
/*      */     }
/*      */   }
/*      */ 
/*      */   public List<PdfName> getStandardStructElems()
/*      */   {
/* 3486 */     if (this.pdf_version.getVersion() < '7') {
/* 3487 */       return standardStructElems_1_4;
/*      */     }
/* 3489 */     return standardStructElems_1_7;
/*      */   }
/*      */ 
/*      */   public static class PdfTrailer extends PdfDictionary
/*      */   {
/*      */     long offset;
/*      */ 
/*      */     public PdfTrailer(int size, long offset, PdfIndirectReference root, PdfIndirectReference info, PdfIndirectReference encryption, PdfObject fileID, long prevxref)
/*      */     {
/*  566 */       this.offset = offset;
/*  567 */       put(PdfName.SIZE, new PdfNumber(size));
/*  568 */       put(PdfName.ROOT, root);
/*  569 */       if (info != null) {
/*  570 */         put(PdfName.INFO, info);
/*      */       }
/*  572 */       if (encryption != null)
/*  573 */         put(PdfName.ENCRYPT, encryption);
/*  574 */       if (fileID != null)
/*  575 */         put(PdfName.ID, fileID);
/*  576 */       if (prevxref > 0L)
/*  577 */         put(PdfName.PREV, new PdfNumber(prevxref));
/*      */     }
/*      */ 
/*      */     public void toPdf(PdfWriter writer, OutputStream os)
/*      */       throws IOException
/*      */     {
/*  588 */       PdfWriter.checkPdfIsoConformance(writer, 8, this);
/*  589 */       os.write(DocWriter.getISOBytes("trailer\n"));
/*  590 */       super.toPdf(null, os);
/*  591 */       os.write(10);
/*  592 */       PdfWriter.writeKeyInfo(os);
/*  593 */       os.write(DocWriter.getISOBytes("startxref\n"));
/*  594 */       os.write(DocWriter.getISOBytes(String.valueOf(this.offset)));
/*  595 */       os.write(DocWriter.getISOBytes("\n%%EOF\n"));
/*      */     }
/*      */   }
/*      */ 
/*      */   public static class PdfBody
/*      */   {
/*      */     private static final int OBJSINSTREAM = 200;
/*      */     protected final TreeSet<PdfCrossReference> xrefs;
/*      */     protected int refnum;
/*      */     protected long position;
/*      */     protected final PdfWriter writer;
/*      */     protected ByteBuffer index;
/*      */     protected ByteBuffer streamObjects;
/*      */     protected int currentObjNum;
/*  257 */     protected int numObj = 0;
/*      */ 
/*      */     protected PdfBody(PdfWriter writer)
/*      */     {
/*  266 */       this.xrefs = new TreeSet();
/*  267 */       this.xrefs.add(new PdfCrossReference(0, 0L, 65535));
/*  268 */       this.position = writer.getOs().getCounter();
/*  269 */       this.refnum = 1;
/*  270 */       this.writer = writer;
/*      */     }
/*      */ 
/*      */     void setRefnum(int refnum)
/*      */     {
/*  276 */       this.refnum = refnum;
/*      */     }
/*      */ 
/*      */     protected PdfCrossReference addToObjStm(PdfObject obj, int nObj) throws IOException {
/*  280 */       if (this.numObj >= 200)
/*  281 */         flushObjStm();
/*  282 */       if (this.index == null) {
/*  283 */         this.index = new ByteBuffer();
/*  284 */         this.streamObjects = new ByteBuffer();
/*  285 */         this.currentObjNum = getIndirectReferenceNumber();
/*  286 */         this.numObj = 0;
/*      */       }
/*  288 */       int p = this.streamObjects.size();
/*  289 */       int idx = this.numObj++;
/*  290 */       PdfEncryption enc = this.writer.crypto;
/*  291 */       this.writer.crypto = null;
/*  292 */       obj.toPdf(this.writer, this.streamObjects);
/*  293 */       this.writer.crypto = enc;
/*  294 */       this.streamObjects.append(' ');
/*  295 */       this.index.append(nObj).append(' ').append(p).append(' ');
/*  296 */       return new PdfCrossReference(2, nObj, this.currentObjNum, idx);
/*      */     }
/*      */ 
/*      */     public void flushObjStm() throws IOException {
/*  300 */       if (this.numObj == 0)
/*  301 */         return;
/*  302 */       int first = this.index.size();
/*  303 */       this.index.append(this.streamObjects);
/*  304 */       PdfStream stream = new PdfStream(this.index.toByteArray());
/*  305 */       stream.flateCompress(this.writer.getCompressionLevel());
/*  306 */       stream.put(PdfName.TYPE, PdfName.OBJSTM);
/*  307 */       stream.put(PdfName.N, new PdfNumber(this.numObj));
/*  308 */       stream.put(PdfName.FIRST, new PdfNumber(first));
/*  309 */       add(stream, this.currentObjNum);
/*  310 */       this.index = null;
/*  311 */       this.streamObjects = null;
/*  312 */       this.numObj = 0;
/*      */     }
/*      */ 
/*      */     PdfIndirectObject add(PdfObject object)
/*      */       throws IOException
/*      */     {
/*  330 */       return add(object, getIndirectReferenceNumber());
/*      */     }
/*      */ 
/*      */     PdfIndirectObject add(PdfObject object, boolean inObjStm) throws IOException {
/*  334 */       return add(object, getIndirectReferenceNumber(), 0, inObjStm);
/*      */     }
/*      */ 
/*      */     public PdfIndirectReference getPdfIndirectReference()
/*      */     {
/*  343 */       return new PdfIndirectReference(0, getIndirectReferenceNumber());
/*      */     }
/*      */ 
/*      */     protected int getIndirectReferenceNumber() {
/*  347 */       int n = this.refnum++;
/*  348 */       this.xrefs.add(new PdfCrossReference(n, 0L, 65535));
/*  349 */       return n;
/*      */     }
/*      */ 
/*      */     PdfIndirectObject add(PdfObject object, PdfIndirectReference ref)
/*      */       throws IOException
/*      */     {
/*  369 */       return add(object, ref, true);
/*      */     }
/*      */ 
/*      */     PdfIndirectObject add(PdfObject object, PdfIndirectReference ref, boolean inObjStm) throws IOException {
/*  373 */       return add(object, ref.getNumber(), ref.getGeneration(), inObjStm);
/*      */     }
/*      */ 
/*      */     PdfIndirectObject add(PdfObject object, int refNumber) throws IOException {
/*  377 */       return add(object, refNumber, 0, true);
/*      */     }
/*      */ 
/*      */     protected PdfIndirectObject add(PdfObject object, int refNumber, int generation, boolean inObjStm) throws IOException {
/*  381 */       if ((inObjStm) && (object.canBeInObjStm()) && (this.writer.isFullCompression())) {
/*  382 */         PdfCrossReference pxref = addToObjStm(object, refNumber);
/*  383 */         PdfIndirectObject indirect = new PdfIndirectObject(refNumber, object, this.writer);
/*  384 */         if (!this.xrefs.add(pxref)) {
/*  385 */           this.xrefs.remove(pxref);
/*  386 */           this.xrefs.add(pxref);
/*      */         }
/*  388 */         return indirect;
/*      */       }
/*      */       PdfIndirectObject indirect;
/*  392 */       if (this.writer.isFullCompression()) {
/*  393 */         PdfIndirectObject indirect = new PdfIndirectObject(refNumber, object, this.writer);
/*  394 */         write(indirect, refNumber);
/*      */       }
/*      */       else {
/*  397 */         indirect = new PdfIndirectObject(refNumber, generation, object, this.writer);
/*  398 */         write(indirect, refNumber, generation);
/*      */       }
/*  400 */       return indirect;
/*      */     }
/*      */ 
/*      */     protected void write(PdfIndirectObject indirect, int refNumber) throws IOException
/*      */     {
/*  405 */       PdfCrossReference pxref = new PdfCrossReference(refNumber, this.position);
/*  406 */       if (!this.xrefs.add(pxref)) {
/*  407 */         this.xrefs.remove(pxref);
/*  408 */         this.xrefs.add(pxref);
/*      */       }
/*  410 */       indirect.writeTo(this.writer.getOs());
/*  411 */       this.position = this.writer.getOs().getCounter();
/*      */     }
/*      */ 
/*      */     protected void write(PdfIndirectObject indirect, int refNumber, int generation) throws IOException {
/*  415 */       PdfCrossReference pxref = new PdfCrossReference(refNumber, this.position, generation);
/*  416 */       if (!this.xrefs.add(pxref)) {
/*  417 */         this.xrefs.remove(pxref);
/*  418 */         this.xrefs.add(pxref);
/*      */       }
/*  420 */       indirect.writeTo(this.writer.getOs());
/*  421 */       this.position = this.writer.getOs().getCounter();
/*      */     }
/*      */ 
/*      */     public long offset()
/*      */     {
/*  431 */       return this.position;
/*      */     }
/*      */ 
/*      */     public int size()
/*      */     {
/*  441 */       return Math.max(((PdfCrossReference)this.xrefs.last()).getRefnum() + 1, this.refnum);
/*      */     }
/*      */ 
/*      */     public void writeCrossReferenceTable(OutputStream os, PdfIndirectReference root, PdfIndirectReference info, PdfIndirectReference encryption, PdfObject fileID, long prevxref)
/*      */       throws IOException
/*      */     {
/*  456 */       int refNumber = 0;
/*  457 */       if (this.writer.isFullCompression()) {
/*  458 */         flushObjStm();
/*  459 */         refNumber = getIndirectReferenceNumber();
/*  460 */         this.xrefs.add(new PdfCrossReference(refNumber, this.position));
/*      */       }
/*  462 */       PdfCrossReference entry = (PdfCrossReference)this.xrefs.first();
/*  463 */       int first = entry.getRefnum();
/*  464 */       int len = 0;
/*  465 */       ArrayList sections = new ArrayList();
/*  466 */       for (PdfCrossReference pdfCrossReference : this.xrefs) {
/*  467 */         entry = pdfCrossReference;
/*  468 */         if (first + len == entry.getRefnum()) {
/*  469 */           len++;
/*      */         } else {
/*  471 */           sections.add(Integer.valueOf(first));
/*  472 */           sections.add(Integer.valueOf(len));
/*  473 */           first = entry.getRefnum();
/*  474 */           len = 1;
/*      */         }
/*      */       }
/*  477 */       sections.add(Integer.valueOf(first));
/*  478 */       sections.add(Integer.valueOf(len));
/*  479 */       if (this.writer.isFullCompression()) {
/*  480 */         int mid = 5;
/*  481 */         long mask = 1095216660480L;
/*  482 */         for (; (mid > 1) && 
/*  483 */           ((mask & this.position) == 0L); mid--)
/*      */         {
/*  485 */           mask >>>= 8;
/*      */         }
/*  487 */         ByteBuffer buf = new ByteBuffer();
/*      */ 
/*  489 */         for (Object element : this.xrefs) {
/*  490 */           entry = (PdfCrossReference)element;
/*  491 */           entry.toPdf(mid, buf);
/*      */         }
/*  493 */         PdfStream xr = new PdfStream(buf.toByteArray());
/*  494 */         buf = null;
/*  495 */         xr.flateCompress(this.writer.getCompressionLevel());
/*  496 */         xr.put(PdfName.SIZE, new PdfNumber(size()));
/*  497 */         xr.put(PdfName.ROOT, root);
/*  498 */         if (info != null) {
/*  499 */           xr.put(PdfName.INFO, info);
/*      */         }
/*  501 */         if (encryption != null)
/*  502 */           xr.put(PdfName.ENCRYPT, encryption);
/*  503 */         if (fileID != null)
/*  504 */           xr.put(PdfName.ID, fileID);
/*  505 */         xr.put(PdfName.W, new PdfArray(new int[] { 1, mid, 2 }));
/*  506 */         xr.put(PdfName.TYPE, PdfName.XREF);
/*  507 */         PdfArray idx = new PdfArray();
/*  508 */         for (int k = 0; k < sections.size(); k++)
/*  509 */           idx.add(new PdfNumber(((Integer)sections.get(k)).intValue()));
/*  510 */         xr.put(PdfName.INDEX, idx);
/*  511 */         if (prevxref > 0L)
/*  512 */           xr.put(PdfName.PREV, new PdfNumber(prevxref));
/*  513 */         PdfEncryption enc = this.writer.crypto;
/*  514 */         this.writer.crypto = null;
/*  515 */         PdfIndirectObject indirect = new PdfIndirectObject(refNumber, xr, this.writer);
/*  516 */         indirect.writeTo(this.writer.getOs());
/*  517 */         this.writer.crypto = enc;
/*      */       }
/*      */       else {
/*  520 */         os.write(DocWriter.getISOBytes("xref\n"));
/*  521 */         Iterator i = this.xrefs.iterator();
/*  522 */         for (int k = 0; k < sections.size(); k += 2) {
/*  523 */           first = ((Integer)sections.get(k)).intValue();
/*  524 */           len = ((Integer)sections.get(k + 1)).intValue();
/*  525 */           os.write(DocWriter.getISOBytes(String.valueOf(first)));
/*  526 */           os.write(DocWriter.getISOBytes(" "));
/*  527 */           os.write(DocWriter.getISOBytes(String.valueOf(len)));
/*  528 */           os.write(10);
/*  529 */           while (len-- > 0) {
/*  530 */             entry = (PdfCrossReference)i.next();
/*  531 */             entry.toPdf(os);
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/*      */     public static class PdfCrossReference
/*      */       implements Comparable<PdfCrossReference>
/*      */     {
/*      */       private final int type;
/*      */       private final long offset;
/*      */       private final int refnum;
/*      */       private final int generation;
/*      */ 
/*      */       public PdfCrossReference(int refnum, long offset, int generation)
/*      */       {
/*  154 */         this.type = 0;
/*  155 */         this.offset = offset;
/*  156 */         this.refnum = refnum;
/*  157 */         this.generation = generation;
/*      */       }
/*      */ 
/*      */       public PdfCrossReference(int refnum, long offset)
/*      */       {
/*  167 */         this.type = 1;
/*  168 */         this.offset = offset;
/*  169 */         this.refnum = refnum;
/*  170 */         this.generation = 0;
/*      */       }
/*      */ 
/*      */       public PdfCrossReference(int type, int refnum, long offset, int generation) {
/*  174 */         this.type = type;
/*  175 */         this.offset = offset;
/*  176 */         this.refnum = refnum;
/*  177 */         this.generation = generation;
/*      */       }
/*      */ 
/*      */       public int getRefnum() {
/*  181 */         return this.refnum;
/*      */       }
/*      */ 
/*      */       public void toPdf(OutputStream os)
/*      */         throws IOException
/*      */       {
/*  191 */         StringBuffer off = new StringBuffer("0000000000").append(this.offset);
/*  192 */         off.delete(0, off.length() - 10);
/*  193 */         StringBuffer gen = new StringBuffer("00000").append(this.generation);
/*  194 */         gen.delete(0, gen.length() - 5);
/*      */ 
/*  196 */         off.append(' ').append(gen).append(this.generation == 65535 ? " f \n" : " n \n");
/*  197 */         os.write(DocWriter.getISOBytes(off.toString()));
/*      */       }
/*      */ 
/*      */       public void toPdf(int midSize, OutputStream os)
/*      */         throws IOException
/*      */       {
/*  207 */         os.write((byte)this.type);
/*      */         while (true) { midSize--; if (midSize < 0) break;
/*  209 */           os.write((byte)(int)(this.offset >>> 8 * midSize & 0xFF)); }
/*  210 */         os.write((byte)(this.generation >>> 8 & 0xFF));
/*  211 */         os.write((byte)(this.generation & 0xFF));
/*      */       }
/*      */ 
/*      */       public int compareTo(PdfCrossReference other)
/*      */       {
/*  218 */         return this.refnum == other.refnum ? 0 : this.refnum < other.refnum ? -1 : 1;
/*      */       }
/*      */ 
/*      */       public boolean equals(Object obj)
/*      */       {
/*  226 */         if ((obj instanceof PdfCrossReference)) {
/*  227 */           PdfCrossReference other = (PdfCrossReference)obj;
/*  228 */           return this.refnum == other.refnum;
/*      */         }
/*      */ 
/*  231 */         return false;
/*      */       }
/*      */ 
/*      */       public int hashCode()
/*      */       {
/*  239 */         return this.refnum;
/*      */       }
/*      */     }
/*      */   }
/*      */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.pdf.PdfWriter
 * JD-Core Version:    0.6.2
 */