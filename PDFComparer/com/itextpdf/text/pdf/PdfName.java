/*      */ package com.itextpdf.text.pdf;
/*      */ 
/*      */ import com.itextpdf.text.error_messages.MessageLocalization;
/*      */ import java.lang.reflect.Field;
/*      */ import java.util.HashMap;
/*      */ import java.util.Map;
/*      */ 
/*      */ public class PdfName extends PdfObject
/*      */   implements Comparable<PdfName>
/*      */ {
/*   80 */   public static final PdfName _3D = new PdfName("3D");
/*      */ 
/*   82 */   public static final PdfName A = new PdfName("A");
/*      */ 
/*   87 */   public static final PdfName A85 = new PdfName("A85");
/*      */ 
/*   89 */   public static final PdfName AA = new PdfName("AA");
/*      */ 
/*   94 */   public static final PdfName ABSOLUTECOLORIMETRIC = new PdfName("AbsoluteColorimetric");
/*      */ 
/*   96 */   public static final PdfName AC = new PdfName("AC");
/*      */ 
/*   98 */   public static final PdfName ACROFORM = new PdfName("AcroForm");
/*      */ 
/*  100 */   public static final PdfName ACTION = new PdfName("Action");
/*      */ 
/*  105 */   public static final PdfName ACTIVATION = new PdfName("Activation");
/*      */ 
/*  110 */   public static final PdfName ADBE = new PdfName("ADBE");
/*      */ 
/*  115 */   public static final PdfName ACTUALTEXT = new PdfName("ActualText");
/*      */ 
/*  117 */   public static final PdfName ADBE_PKCS7_DETACHED = new PdfName("adbe.pkcs7.detached");
/*      */ 
/*  119 */   public static final PdfName ADBE_PKCS7_S4 = new PdfName("adbe.pkcs7.s4");
/*      */ 
/*  121 */   public static final PdfName ADBE_PKCS7_S5 = new PdfName("adbe.pkcs7.s5");
/*      */ 
/*  123 */   public static final PdfName ADBE_PKCS7_SHA1 = new PdfName("adbe.pkcs7.sha1");
/*      */ 
/*  125 */   public static final PdfName ADBE_X509_RSA_SHA1 = new PdfName("adbe.x509.rsa_sha1");
/*      */ 
/*  127 */   public static final PdfName ADOBE_PPKLITE = new PdfName("Adobe.PPKLite");
/*      */ 
/*  129 */   public static final PdfName ADOBE_PPKMS = new PdfName("Adobe.PPKMS");
/*      */ 
/*  131 */   public static final PdfName AESV2 = new PdfName("AESV2");
/*      */ 
/*  133 */   public static final PdfName AESV3 = new PdfName("AESV3");
/*      */ 
/*  138 */   public static final PdfName AF = new PdfName("AF");
/*      */ 
/*  143 */   public static final PdfName AFRELATIONSHIP = new PdfName("AFRelationship");
/*      */ 
/*  148 */   public static final PdfName AHX = new PdfName("AHx");
/*      */ 
/*  150 */   public static final PdfName AIS = new PdfName("AIS");
/*      */ 
/*  152 */   public static final PdfName ALL = new PdfName("All");
/*      */ 
/*  154 */   public static final PdfName ALLPAGES = new PdfName("AllPages");
/*      */ 
/*  156 */   public static final PdfName ALT = new PdfName("Alt");
/*      */ 
/*  158 */   public static final PdfName ALTERNATE = new PdfName("Alternate");
/*      */ 
/*  163 */   public static final PdfName ALTERNATEPRESENTATION = new PdfName("AlternatePresentations");
/*      */ 
/*  168 */   public static final PdfName ALTERNATES = new PdfName("Alternates");
/*      */ 
/*  173 */   public static final PdfName AND = new PdfName("And");
/*      */ 
/*  178 */   public static final PdfName ANIMATION = new PdfName("Animation");
/*      */ 
/*  180 */   public static final PdfName ANNOT = new PdfName("Annot");
/*      */ 
/*  182 */   public static final PdfName ANNOTS = new PdfName("Annots");
/*      */ 
/*  184 */   public static final PdfName ANTIALIAS = new PdfName("AntiAlias");
/*      */ 
/*  186 */   public static final PdfName AP = new PdfName("AP");
/*      */ 
/*  188 */   public static final PdfName APP = new PdfName("App");
/*      */ 
/*  190 */   public static final PdfName APPDEFAULT = new PdfName("AppDefault");
/*      */ 
/*  195 */   public static final PdfName ART = new PdfName("Art");
/*      */ 
/*  197 */   public static final PdfName ARTBOX = new PdfName("ArtBox");
/*      */ 
/*  202 */   public static final PdfName ARTIFACT = new PdfName("Artifact");
/*      */ 
/*  204 */   public static final PdfName ASCENT = new PdfName("Ascent");
/*      */ 
/*  206 */   public static final PdfName AS = new PdfName("AS");
/*      */ 
/*  208 */   public static final PdfName ASCII85DECODE = new PdfName("ASCII85Decode");
/*      */ 
/*  210 */   public static final PdfName ASCIIHEXDECODE = new PdfName("ASCIIHexDecode");
/*      */ 
/*  215 */   public static final PdfName ASSET = new PdfName("Asset");
/*      */ 
/*  220 */   public static final PdfName ASSETS = new PdfName("Assets");
/*      */ 
/*  225 */   public static final PdfName ATTACHED = new PdfName("Attached");
/*      */ 
/*  227 */   public static final PdfName AUTHEVENT = new PdfName("AuthEvent");
/*      */ 
/*  229 */   public static final PdfName AUTHOR = new PdfName("Author");
/*      */ 
/*  231 */   public static final PdfName B = new PdfName("B");
/*      */ 
/*  236 */   public static final PdfName BACKGROUND = new PdfName("Background");
/*      */ 
/*  241 */   public static final PdfName BACKGROUNDCOLOR = new PdfName("BackgroundColor");
/*      */ 
/*  243 */   public static final PdfName BASEENCODING = new PdfName("BaseEncoding");
/*      */ 
/*  245 */   public static final PdfName BASEFONT = new PdfName("BaseFont");
/*      */ 
/*  250 */   public static final PdfName BASEVERSION = new PdfName("BaseVersion");
/*      */ 
/*  252 */   public static final PdfName BBOX = new PdfName("BBox");
/*      */ 
/*  254 */   public static final PdfName BC = new PdfName("BC");
/*      */ 
/*  256 */   public static final PdfName BG = new PdfName("BG");
/*      */ 
/*  261 */   public static final PdfName BIBENTRY = new PdfName("BibEntry");
/*      */ 
/*  263 */   public static final PdfName BIGFIVE = new PdfName("BigFive");
/*      */ 
/*  268 */   public static final PdfName BINDING = new PdfName("Binding");
/*      */ 
/*  273 */   public static final PdfName BINDINGMATERIALNAME = new PdfName("BindingMaterialName");
/*      */ 
/*  275 */   public static final PdfName BITSPERCOMPONENT = new PdfName("BitsPerComponent");
/*      */ 
/*  277 */   public static final PdfName BITSPERSAMPLE = new PdfName("BitsPerSample");
/*      */ 
/*  279 */   public static final PdfName BL = new PdfName("Bl");
/*      */ 
/*  281 */   public static final PdfName BLACKIS1 = new PdfName("BlackIs1");
/*      */ 
/*  283 */   public static final PdfName BLACKPOINT = new PdfName("BlackPoint");
/*      */ 
/*  288 */   public static final PdfName BLOCKQUOTE = new PdfName("BlockQuote");
/*      */ 
/*  290 */   public static final PdfName BLEEDBOX = new PdfName("BleedBox");
/*      */ 
/*  292 */   public static final PdfName BLINDS = new PdfName("Blinds");
/*      */ 
/*  294 */   public static final PdfName BM = new PdfName("BM");
/*      */ 
/*  296 */   public static final PdfName BORDER = new PdfName("Border");
/*      */ 
/*  301 */   public static final PdfName BOTH = new PdfName("Both");
/*      */ 
/*  303 */   public static final PdfName BOUNDS = new PdfName("Bounds");
/*      */ 
/*  305 */   public static final PdfName BOX = new PdfName("Box");
/*      */ 
/*  307 */   public static final PdfName BS = new PdfName("BS");
/*      */ 
/*  309 */   public static final PdfName BTN = new PdfName("Btn");
/*      */ 
/*  311 */   public static final PdfName BYTERANGE = new PdfName("ByteRange");
/*      */ 
/*  313 */   public static final PdfName C = new PdfName("C");
/*      */ 
/*  315 */   public static final PdfName C0 = new PdfName("C0");
/*      */ 
/*  317 */   public static final PdfName C1 = new PdfName("C1");
/*      */ 
/*  319 */   public static final PdfName CA = new PdfName("CA");
/*      */ 
/*  321 */   public static final PdfName ca = new PdfName("ca");
/*      */ 
/*  323 */   public static final PdfName CALGRAY = new PdfName("CalGray");
/*      */ 
/*  325 */   public static final PdfName CALRGB = new PdfName("CalRGB");
/*      */ 
/*  327 */   public static final PdfName CAPHEIGHT = new PdfName("CapHeight");
/*      */ 
/*  332 */   public static final PdfName CARET = new PdfName("Caret");
/*      */ 
/*  337 */   public static final PdfName CAPTION = new PdfName("Caption");
/*      */ 
/*  339 */   public static final PdfName CATALOG = new PdfName("Catalog");
/*      */ 
/*  341 */   public static final PdfName CATEGORY = new PdfName("Category");
/*      */ 
/*  346 */   public static final PdfName CB = new PdfName("cb");
/*      */ 
/*  348 */   public static final PdfName CCITTFAXDECODE = new PdfName("CCITTFaxDecode");
/*      */ 
/*  353 */   public static final PdfName CENTER = new PdfName("Center");
/*      */ 
/*  355 */   public static final PdfName CENTERWINDOW = new PdfName("CenterWindow");
/*      */ 
/*  357 */   public static final PdfName CERT = new PdfName("Cert");
/*      */ 
/*  361 */   public static final PdfName CERTS = new PdfName("Certs");
/*      */ 
/*  363 */   public static final PdfName CF = new PdfName("CF");
/*      */ 
/*  365 */   public static final PdfName CFM = new PdfName("CFM");
/*      */ 
/*  367 */   public static final PdfName CH = new PdfName("Ch");
/*      */ 
/*  369 */   public static final PdfName CHARPROCS = new PdfName("CharProcs");
/*      */ 
/*  371 */   public static final PdfName CHECKSUM = new PdfName("CheckSum");
/*      */ 
/*  373 */   public static final PdfName CI = new PdfName("CI");
/*      */ 
/*  375 */   public static final PdfName CIDFONTTYPE0 = new PdfName("CIDFontType0");
/*      */ 
/*  377 */   public static final PdfName CIDFONTTYPE2 = new PdfName("CIDFontType2");
/*      */ 
/*  382 */   public static final PdfName CIDSET = new PdfName("CIDSet");
/*      */ 
/*  384 */   public static final PdfName CIDSYSTEMINFO = new PdfName("CIDSystemInfo");
/*      */ 
/*  386 */   public static final PdfName CIDTOGIDMAP = new PdfName("CIDToGIDMap");
/*      */ 
/*  388 */   public static final PdfName CIRCLE = new PdfName("Circle");
/*      */ 
/*  393 */   public static final PdfName CLASSMAP = new PdfName("ClassMap");
/*      */ 
/*  398 */   public static final PdfName CLOUD = new PdfName("Cloud");
/*      */ 
/*  403 */   public static final PdfName CMD = new PdfName("CMD");
/*      */ 
/*  405 */   public static final PdfName CO = new PdfName("CO");
/*      */ 
/*  410 */   public static final PdfName CODE = new PdfName("Code");
/*      */ 
/*  415 */   public static final PdfName COLOR = new PdfName("Color");
/*  416 */   public static final PdfName COLORANTS = new PdfName("Colorants");
/*      */ 
/*  418 */   public static final PdfName COLORS = new PdfName("Colors");
/*      */ 
/*  420 */   public static final PdfName COLORSPACE = new PdfName("ColorSpace");
/*      */ 
/*  425 */   public static final PdfName COLORTRANSFORM = new PdfName("ColorTransform");
/*      */ 
/*  427 */   public static final PdfName COLLECTION = new PdfName("Collection");
/*      */ 
/*  429 */   public static final PdfName COLLECTIONFIELD = new PdfName("CollectionField");
/*      */ 
/*  431 */   public static final PdfName COLLECTIONITEM = new PdfName("CollectionItem");
/*      */ 
/*  433 */   public static final PdfName COLLECTIONSCHEMA = new PdfName("CollectionSchema");
/*      */ 
/*  435 */   public static final PdfName COLLECTIONSORT = new PdfName("CollectionSort");
/*      */ 
/*  437 */   public static final PdfName COLLECTIONSUBITEM = new PdfName("CollectionSubitem");
/*      */ 
/*  442 */   public static final PdfName COLSPAN = new PdfName("Colspan");
/*      */ 
/*  447 */   public static final PdfName COLUMN = new PdfName("Column");
/*      */ 
/*  449 */   public static final PdfName COLUMNS = new PdfName("Columns");
/*      */ 
/*  454 */   public static final PdfName CONDITION = new PdfName("Condition");
/*      */ 
/*  459 */   public static final PdfName CONFIGS = new PdfName("Configs");
/*      */ 
/*  464 */   public static final PdfName CONFIGURATION = new PdfName("Configuration");
/*      */ 
/*  469 */   public static final PdfName CONFIGURATIONS = new PdfName("Configurations");
/*      */ 
/*  471 */   public static final PdfName CONTACTINFO = new PdfName("ContactInfo");
/*      */ 
/*  473 */   public static final PdfName CONTENT = new PdfName("Content");
/*      */ 
/*  475 */   public static final PdfName CONTENTS = new PdfName("Contents");
/*      */ 
/*  477 */   public static final PdfName COORDS = new PdfName("Coords");
/*      */ 
/*  479 */   public static final PdfName COUNT = new PdfName("Count");
/*      */ 
/*  481 */   public static final PdfName COURIER = new PdfName("Courier");
/*      */ 
/*  483 */   public static final PdfName COURIER_BOLD = new PdfName("Courier-Bold");
/*      */ 
/*  485 */   public static final PdfName COURIER_OBLIQUE = new PdfName("Courier-Oblique");
/*      */ 
/*  487 */   public static final PdfName COURIER_BOLDOBLIQUE = new PdfName("Courier-BoldOblique");
/*      */ 
/*  489 */   public static final PdfName CREATIONDATE = new PdfName("CreationDate");
/*      */ 
/*  491 */   public static final PdfName CREATOR = new PdfName("Creator");
/*      */ 
/*  493 */   public static final PdfName CREATORINFO = new PdfName("CreatorInfo");
/*      */ 
/*  497 */   public static final PdfName CRL = new PdfName("CRL");
/*      */ 
/*  501 */   public static final PdfName CRLS = new PdfName("CRLs");
/*      */ 
/*  503 */   public static final PdfName CROPBOX = new PdfName("CropBox");
/*      */ 
/*  505 */   public static final PdfName CRYPT = new PdfName("Crypt");
/*      */ 
/*  507 */   public static final PdfName CS = new PdfName("CS");
/*      */ 
/*  512 */   public static final PdfName CUEPOINT = new PdfName("CuePoint");
/*      */ 
/*  517 */   public static final PdfName CUEPOINTS = new PdfName("CuePoints");
/*      */ 
/*  522 */   public static final PdfName CYX = new PdfName("CYX");
/*      */ 
/*  524 */   public static final PdfName D = new PdfName("D");
/*      */ 
/*  526 */   public static final PdfName DA = new PdfName("DA");
/*      */ 
/*  528 */   public static final PdfName DATA = new PdfName("Data");
/*      */ 
/*  530 */   public static final PdfName DC = new PdfName("DC");
/*      */ 
/*  535 */   public static final PdfName DCS = new PdfName("DCS");
/*      */ 
/*  537 */   public static final PdfName DCTDECODE = new PdfName("DCTDecode");
/*      */ 
/*  542 */   public static final PdfName DECIMAL = new PdfName("Decimal");
/*      */ 
/*  547 */   public static final PdfName DEACTIVATION = new PdfName("Deactivation");
/*      */ 
/*  549 */   public static final PdfName DECODE = new PdfName("Decode");
/*      */ 
/*  551 */   public static final PdfName DECODEPARMS = new PdfName("DecodeParms");
/*      */ 
/*  556 */   public static final PdfName DEFAULT = new PdfName("Default");
/*      */ 
/*  561 */   public static final PdfName DEFAULTCRYPTFILTER = new PdfName("DefaultCryptFilter");
/*      */ 
/*  563 */   public static final PdfName DEFAULTCMYK = new PdfName("DefaultCMYK");
/*      */ 
/*  565 */   public static final PdfName DEFAULTGRAY = new PdfName("DefaultGray");
/*      */ 
/*  567 */   public static final PdfName DEFAULTRGB = new PdfName("DefaultRGB");
/*      */ 
/*  569 */   public static final PdfName DESC = new PdfName("Desc");
/*      */ 
/*  571 */   public static final PdfName DESCENDANTFONTS = new PdfName("DescendantFonts");
/*      */ 
/*  573 */   public static final PdfName DESCENT = new PdfName("Descent");
/*      */ 
/*  575 */   public static final PdfName DEST = new PdfName("Dest");
/*      */ 
/*  577 */   public static final PdfName DESTOUTPUTPROFILE = new PdfName("DestOutputProfile");
/*      */ 
/*  579 */   public static final PdfName DESTS = new PdfName("Dests");
/*      */ 
/*  581 */   public static final PdfName DEVICEGRAY = new PdfName("DeviceGray");
/*      */ 
/*  583 */   public static final PdfName DEVICERGB = new PdfName("DeviceRGB");
/*      */ 
/*  585 */   public static final PdfName DEVICECMYK = new PdfName("DeviceCMYK");
/*      */ 
/*  590 */   public static final PdfName DEVICEN = new PdfName("DeviceN");
/*      */ 
/*  592 */   public static final PdfName DI = new PdfName("Di");
/*      */ 
/*  594 */   public static final PdfName DIFFERENCES = new PdfName("Differences");
/*      */ 
/*  596 */   public static final PdfName DISSOLVE = new PdfName("Dissolve");
/*      */ 
/*  598 */   public static final PdfName DIRECTION = new PdfName("Direction");
/*      */ 
/*  600 */   public static final PdfName DISPLAYDOCTITLE = new PdfName("DisplayDocTitle");
/*      */ 
/*  602 */   public static final PdfName DIV = new PdfName("Div");
/*      */ 
/*  604 */   public static final PdfName DL = new PdfName("DL");
/*      */ 
/*  606 */   public static final PdfName DM = new PdfName("Dm");
/*      */ 
/*  608 */   public static final PdfName DOCMDP = new PdfName("DocMDP");
/*      */ 
/*  610 */   public static final PdfName DOCOPEN = new PdfName("DocOpen");
/*      */ 
/*  615 */   public static final PdfName DOCTIMESTAMP = new PdfName("DocTimeStamp");
/*      */ 
/*  620 */   public static final PdfName DOCUMENT = new PdfName("Document");
/*      */ 
/*  622 */   public static final PdfName DOMAIN = new PdfName("Domain");
/*      */ 
/*  627 */   public static final PdfName DOS = new PdfName("DOS");
/*      */ 
/*  629 */   public static final PdfName DP = new PdfName("DP");
/*      */ 
/*  631 */   public static final PdfName DR = new PdfName("DR");
/*      */ 
/*  633 */   public static final PdfName DS = new PdfName("DS");
/*      */ 
/*  637 */   public static final PdfName DSS = new PdfName("DSS");
/*      */ 
/*  639 */   public static final PdfName DUR = new PdfName("Dur");
/*      */ 
/*  641 */   public static final PdfName DUPLEX = new PdfName("Duplex");
/*      */ 
/*  643 */   public static final PdfName DUPLEXFLIPSHORTEDGE = new PdfName("DuplexFlipShortEdge");
/*      */ 
/*  645 */   public static final PdfName DUPLEXFLIPLONGEDGE = new PdfName("DuplexFlipLongEdge");
/*      */ 
/*  647 */   public static final PdfName DV = new PdfName("DV");
/*      */ 
/*  649 */   public static final PdfName DW = new PdfName("DW");
/*      */ 
/*  651 */   public static final PdfName E = new PdfName("E");
/*      */ 
/*  653 */   public static final PdfName EARLYCHANGE = new PdfName("EarlyChange");
/*      */ 
/*  655 */   public static final PdfName EF = new PdfName("EF");
/*      */ 
/*  660 */   public static final PdfName EFF = new PdfName("EFF");
/*      */ 
/*  665 */   public static final PdfName EFOPEN = new PdfName("EFOpen");
/*      */ 
/*  670 */   public static final PdfName EMBEDDED = new PdfName("Embedded");
/*      */ 
/*  672 */   public static final PdfName EMBEDDEDFILE = new PdfName("EmbeddedFile");
/*      */ 
/*  674 */   public static final PdfName EMBEDDEDFILES = new PdfName("EmbeddedFiles");
/*      */ 
/*  676 */   public static final PdfName ENCODE = new PdfName("Encode");
/*      */ 
/*  678 */   public static final PdfName ENCODEDBYTEALIGN = new PdfName("EncodedByteAlign");
/*      */ 
/*  680 */   public static final PdfName ENCODING = new PdfName("Encoding");
/*      */ 
/*  682 */   public static final PdfName ENCRYPT = new PdfName("Encrypt");
/*      */ 
/*  684 */   public static final PdfName ENCRYPTMETADATA = new PdfName("EncryptMetadata");
/*      */ 
/*  689 */   public static final PdfName END = new PdfName("End");
/*      */ 
/*  694 */   public static final PdfName ENDINDENT = new PdfName("EndIndent");
/*      */ 
/*  696 */   public static final PdfName ENDOFBLOCK = new PdfName("EndOfBlock");
/*      */ 
/*  698 */   public static final PdfName ENDOFLINE = new PdfName("EndOfLine");
/*      */ 
/*  703 */   public static final PdfName EPSG = new PdfName("EPSG");
/*      */ 
/*  708 */   public static final PdfName ESIC = new PdfName("ESIC");
/*      */ 
/*  712 */   public static final PdfName ETSI_CADES_DETACHED = new PdfName("ETSI.CAdES.detached");
/*      */ 
/*  714 */   public static final PdfName ETSI_RFC3161 = new PdfName("ETSI.RFC3161");
/*      */ 
/*  716 */   public static final PdfName EXCLUDE = new PdfName("Exclude");
/*      */ 
/*  718 */   public static final PdfName EXTEND = new PdfName("Extend");
/*      */ 
/*  723 */   public static final PdfName EXTENSIONS = new PdfName("Extensions");
/*      */ 
/*  728 */   public static final PdfName EXTENSIONLEVEL = new PdfName("ExtensionLevel");
/*      */ 
/*  730 */   public static final PdfName EXTGSTATE = new PdfName("ExtGState");
/*      */ 
/*  732 */   public static final PdfName EXPORT = new PdfName("Export");
/*      */ 
/*  734 */   public static final PdfName EXPORTSTATE = new PdfName("ExportState");
/*      */ 
/*  736 */   public static final PdfName EVENT = new PdfName("Event");
/*      */ 
/*  738 */   public static final PdfName F = new PdfName("F");
/*      */ 
/*  743 */   public static final PdfName FAR = new PdfName("Far");
/*      */ 
/*  745 */   public static final PdfName FB = new PdfName("FB");
/*      */ 
/*  750 */   public static final PdfName FD = new PdfName("FD");
/*      */ 
/*  752 */   public static final PdfName FDECODEPARMS = new PdfName("FDecodeParms");
/*      */ 
/*  754 */   public static final PdfName FDF = new PdfName("FDF");
/*      */ 
/*  756 */   public static final PdfName FF = new PdfName("Ff");
/*      */ 
/*  758 */   public static final PdfName FFILTER = new PdfName("FFilter");
/*      */ 
/*  763 */   public static final PdfName FG = new PdfName("FG");
/*      */ 
/*  765 */   public static final PdfName FIELDMDP = new PdfName("FieldMDP");
/*      */ 
/*  767 */   public static final PdfName FIELDS = new PdfName("Fields");
/*      */ 
/*  772 */   public static final PdfName FIGURE = new PdfName("Figure");
/*      */ 
/*  774 */   public static final PdfName FILEATTACHMENT = new PdfName("FileAttachment");
/*      */ 
/*  776 */   public static final PdfName FILESPEC = new PdfName("Filespec");
/*      */ 
/*  778 */   public static final PdfName FILTER = new PdfName("Filter");
/*      */ 
/*  780 */   public static final PdfName FIRST = new PdfName("First");
/*      */ 
/*  782 */   public static final PdfName FIRSTCHAR = new PdfName("FirstChar");
/*      */ 
/*  784 */   public static final PdfName FIRSTPAGE = new PdfName("FirstPage");
/*      */ 
/*  786 */   public static final PdfName FIT = new PdfName("Fit");
/*      */ 
/*  788 */   public static final PdfName FITH = new PdfName("FitH");
/*      */ 
/*  790 */   public static final PdfName FITV = new PdfName("FitV");
/*      */ 
/*  792 */   public static final PdfName FITR = new PdfName("FitR");
/*      */ 
/*  794 */   public static final PdfName FITB = new PdfName("FitB");
/*      */ 
/*  796 */   public static final PdfName FITBH = new PdfName("FitBH");
/*      */ 
/*  798 */   public static final PdfName FITBV = new PdfName("FitBV");
/*      */ 
/*  800 */   public static final PdfName FITWINDOW = new PdfName("FitWindow");
/*      */ 
/*  805 */   public static final PdfName FL = new PdfName("Fl");
/*      */ 
/*  807 */   public static final PdfName FLAGS = new PdfName("Flags");
/*      */ 
/*  812 */   public static final PdfName FLASH = new PdfName("Flash");
/*      */ 
/*  817 */   public static final PdfName FLASHVARS = new PdfName("FlashVars");
/*      */ 
/*  819 */   public static final PdfName FLATEDECODE = new PdfName("FlateDecode");
/*      */ 
/*  821 */   public static final PdfName FO = new PdfName("Fo");
/*      */ 
/*  823 */   public static final PdfName FONT = new PdfName("Font");
/*      */ 
/*  825 */   public static final PdfName FONTBBOX = new PdfName("FontBBox");
/*      */ 
/*  827 */   public static final PdfName FONTDESCRIPTOR = new PdfName("FontDescriptor");
/*      */ 
/*  829 */   public static final PdfName FONTFAMILY = new PdfName("FontFamily");
/*      */ 
/*  831 */   public static final PdfName FONTFILE = new PdfName("FontFile");
/*      */ 
/*  833 */   public static final PdfName FONTFILE2 = new PdfName("FontFile2");
/*      */ 
/*  835 */   public static final PdfName FONTFILE3 = new PdfName("FontFile3");
/*      */ 
/*  837 */   public static final PdfName FONTMATRIX = new PdfName("FontMatrix");
/*      */ 
/*  839 */   public static final PdfName FONTNAME = new PdfName("FontName");
/*      */ 
/*  841 */   public static final PdfName FONTWEIGHT = new PdfName("FontWeight");
/*      */ 
/*  846 */   public static final PdfName FOREGROUND = new PdfName("Foreground");
/*      */ 
/*  848 */   public static final PdfName FORM = new PdfName("Form");
/*      */ 
/*  850 */   public static final PdfName FORMTYPE = new PdfName("FormType");
/*      */ 
/*  855 */   public static final PdfName FORMULA = new PdfName("Formula");
/*      */ 
/*  857 */   public static final PdfName FREETEXT = new PdfName("FreeText");
/*      */ 
/*  859 */   public static final PdfName FRM = new PdfName("FRM");
/*      */ 
/*  861 */   public static final PdfName FS = new PdfName("FS");
/*      */ 
/*  863 */   public static final PdfName FT = new PdfName("FT");
/*      */ 
/*  865 */   public static final PdfName FULLSCREEN = new PdfName("FullScreen");
/*      */ 
/*  867 */   public static final PdfName FUNCTION = new PdfName("Function");
/*      */ 
/*  869 */   public static final PdfName FUNCTIONS = new PdfName("Functions");
/*      */ 
/*  871 */   public static final PdfName FUNCTIONTYPE = new PdfName("FunctionType");
/*      */ 
/*  873 */   public static final PdfName GAMMA = new PdfName("Gamma");
/*      */ 
/*  875 */   public static final PdfName GBK = new PdfName("GBK");
/*      */ 
/*  880 */   public static final PdfName GCS = new PdfName("GCS");
/*      */ 
/*  885 */   public static final PdfName GEO = new PdfName("GEO");
/*      */ 
/*  890 */   public static final PdfName GEOGCS = new PdfName("GEOGCS");
/*      */ 
/*  892 */   public static final PdfName GLITTER = new PdfName("Glitter");
/*      */ 
/*  894 */   public static final PdfName GOTO = new PdfName("GoTo");
/*      */ 
/*  899 */   public static final PdfName GOTO3DVIEW = new PdfName("GoTo3DView");
/*      */ 
/*  901 */   public static final PdfName GOTOE = new PdfName("GoToE");
/*      */ 
/*  903 */   public static final PdfName GOTOR = new PdfName("GoToR");
/*      */ 
/*  908 */   public static final PdfName GPTS = new PdfName("GPTS");
/*      */ 
/*  910 */   public static final PdfName GROUP = new PdfName("Group");
/*      */ 
/*  912 */   public static final PdfName GTS_PDFA1 = new PdfName("GTS_PDFA1");
/*      */ 
/*  914 */   public static final PdfName GTS_PDFX = new PdfName("GTS_PDFX");
/*      */ 
/*  916 */   public static final PdfName GTS_PDFXVERSION = new PdfName("GTS_PDFXVersion");
/*      */ 
/*  918 */   public static final PdfName H = new PdfName("H");
/*      */ 
/*  923 */   public static final PdfName H1 = new PdfName("H1");
/*      */ 
/*  928 */   public static final PdfName H2 = new PdfName("H2");
/*      */ 
/*  933 */   public static final PdfName H3 = new PdfName("H3");
/*      */ 
/*  938 */   public static final PdfName H4 = new PdfName("H4");
/*      */ 
/*  943 */   public static final PdfName H5 = new PdfName("H5");
/*      */ 
/*  948 */   public static final PdfName H6 = new PdfName("H6");
/*      */ 
/*  953 */   public static final PdfName HALFTONENAME = new PdfName("HalftoneName");
/*      */ 
/*  958 */   public static final PdfName HALFTONETYPE = new PdfName("HalftoneType");
/*      */ 
/*  964 */   public static final PdfName HALIGN = new PdfName("HAlign");
/*      */ 
/*  969 */   public static final PdfName HEADERS = new PdfName("Headers");
/*      */ 
/*  971 */   public static final PdfName HEIGHT = new PdfName("Height");
/*      */ 
/*  973 */   public static final PdfName HELV = new PdfName("Helv");
/*      */ 
/*  975 */   public static final PdfName HELVETICA = new PdfName("Helvetica");
/*      */ 
/*  977 */   public static final PdfName HELVETICA_BOLD = new PdfName("Helvetica-Bold");
/*      */ 
/*  979 */   public static final PdfName HELVETICA_OBLIQUE = new PdfName("Helvetica-Oblique");
/*      */ 
/*  981 */   public static final PdfName HELVETICA_BOLDOBLIQUE = new PdfName("Helvetica-BoldOblique");
/*      */ 
/*  986 */   public static final PdfName HF = new PdfName("HF");
/*      */ 
/*  988 */   public static final PdfName HID = new PdfName("Hid");
/*      */ 
/*  990 */   public static final PdfName HIDE = new PdfName("Hide");
/*      */ 
/*  992 */   public static final PdfName HIDEMENUBAR = new PdfName("HideMenubar");
/*      */ 
/*  994 */   public static final PdfName HIDETOOLBAR = new PdfName("HideToolbar");
/*      */ 
/*  996 */   public static final PdfName HIDEWINDOWUI = new PdfName("HideWindowUI");
/*      */ 
/*  998 */   public static final PdfName HIGHLIGHT = new PdfName("Highlight");
/*      */ 
/* 1003 */   public static final PdfName HOFFSET = new PdfName("HOffset");
/*      */ 
/* 1008 */   public static final PdfName HT = new PdfName("HT");
/*      */ 
/* 1013 */   public static final PdfName HTP = new PdfName("HTP");
/*      */ 
/* 1015 */   public static final PdfName I = new PdfName("I");
/*      */ 
/* 1020 */   public static final PdfName IC = new PdfName("IC");
/*      */ 
/* 1022 */   public static final PdfName ICCBASED = new PdfName("ICCBased");
/*      */ 
/* 1024 */   public static final PdfName ID = new PdfName("ID");
/*      */ 
/* 1026 */   public static final PdfName IDENTITY = new PdfName("Identity");
/*      */ 
/* 1028 */   public static final PdfName IF = new PdfName("IF");
/*      */ 
/* 1030 */   public static final PdfName IMAGE = new PdfName("Image");
/*      */ 
/* 1032 */   public static final PdfName IMAGEB = new PdfName("ImageB");
/*      */ 
/* 1034 */   public static final PdfName IMAGEC = new PdfName("ImageC");
/*      */ 
/* 1036 */   public static final PdfName IMAGEI = new PdfName("ImageI");
/*      */ 
/* 1038 */   public static final PdfName IMAGEMASK = new PdfName("ImageMask");
/*      */ 
/* 1040 */   public static final PdfName INCLUDE = new PdfName("Include");
/*      */ 
/* 1045 */   public static final PdfName IND = new PdfName("Ind");
/*      */ 
/* 1047 */   public static final PdfName INDEX = new PdfName("Index");
/*      */ 
/* 1049 */   public static final PdfName INDEXED = new PdfName("Indexed");
/*      */ 
/* 1051 */   public static final PdfName INFO = new PdfName("Info");
/*      */ 
/* 1053 */   public static final PdfName INK = new PdfName("Ink");
/*      */ 
/* 1055 */   public static final PdfName INKLIST = new PdfName("InkList");
/*      */ 
/* 1060 */   public static final PdfName INSTANCES = new PdfName("Instances");
/*      */ 
/* 1062 */   public static final PdfName IMPORTDATA = new PdfName("ImportData");
/*      */ 
/* 1064 */   public static final PdfName INTENT = new PdfName("Intent");
/*      */ 
/* 1066 */   public static final PdfName INTERPOLATE = new PdfName("Interpolate");
/*      */ 
/* 1068 */   public static final PdfName ISMAP = new PdfName("IsMap");
/*      */ 
/* 1070 */   public static final PdfName IRT = new PdfName("IRT");
/*      */ 
/* 1072 */   public static final PdfName ITALICANGLE = new PdfName("ItalicAngle");
/*      */ 
/* 1077 */   public static final PdfName ITXT = new PdfName("ITXT");
/*      */ 
/* 1079 */   public static final PdfName IX = new PdfName("IX");
/*      */ 
/* 1081 */   public static final PdfName JAVASCRIPT = new PdfName("JavaScript");
/*      */ 
/* 1086 */   public static final PdfName JBIG2DECODE = new PdfName("JBIG2Decode");
/*      */ 
/* 1091 */   public static final PdfName JBIG2GLOBALS = new PdfName("JBIG2Globals");
/*      */ 
/* 1093 */   public static final PdfName JPXDECODE = new PdfName("JPXDecode");
/*      */ 
/* 1095 */   public static final PdfName JS = new PdfName("JS");
/*      */ 
/* 1100 */   public static final PdfName JUSTIFY = new PdfName("Justify");
/*      */ 
/* 1102 */   public static final PdfName K = new PdfName("K");
/*      */ 
/* 1104 */   public static final PdfName KEYWORDS = new PdfName("Keywords");
/*      */ 
/* 1106 */   public static final PdfName KIDS = new PdfName("Kids");
/*      */ 
/* 1108 */   public static final PdfName L = new PdfName("L");
/*      */ 
/* 1110 */   public static final PdfName L2R = new PdfName("L2R");
/*      */ 
/* 1115 */   public static final PdfName LAB = new PdfName("Lab");
/*      */ 
/* 1117 */   public static final PdfName LANG = new PdfName("Lang");
/*      */ 
/* 1119 */   public static final PdfName LANGUAGE = new PdfName("Language");
/*      */ 
/* 1121 */   public static final PdfName LAST = new PdfName("Last");
/*      */ 
/* 1123 */   public static final PdfName LASTCHAR = new PdfName("LastChar");
/*      */ 
/* 1125 */   public static final PdfName LASTPAGE = new PdfName("LastPage");
/*      */ 
/* 1127 */   public static final PdfName LAUNCH = new PdfName("Launch");
/*      */ 
/* 1132 */   public static final PdfName LAYOUT = new PdfName("Layout");
/*      */ 
/* 1137 */   public static final PdfName LBL = new PdfName("Lbl");
/*      */ 
/* 1142 */   public static final PdfName LBODY = new PdfName("LBody");
/*      */ 
/* 1144 */   public static final PdfName LENGTH = new PdfName("Length");
/*      */ 
/* 1146 */   public static final PdfName LENGTH1 = new PdfName("Length1");
/*      */ 
/* 1151 */   public static final PdfName LI = new PdfName("LI");
/*      */ 
/* 1153 */   public static final PdfName LIMITS = new PdfName("Limits");
/*      */ 
/* 1155 */   public static final PdfName LINE = new PdfName("Line");
/*      */ 
/* 1160 */   public static final PdfName LINEAR = new PdfName("Linear");
/*      */ 
/* 1165 */   public static final PdfName LINEHEIGHT = new PdfName("LineHeight");
/*      */ 
/* 1167 */   public static final PdfName LINK = new PdfName("Link");
/*      */ 
/* 1172 */   public static final PdfName LIST = new PdfName("List");
/*      */ 
/* 1174 */   public static final PdfName LISTMODE = new PdfName("ListMode");
/*      */ 
/* 1176 */   public static final PdfName LISTNUMBERING = new PdfName("ListNumbering");
/*      */ 
/* 1178 */   public static final PdfName LOCATION = new PdfName("Location");
/*      */ 
/* 1180 */   public static final PdfName LOCK = new PdfName("Lock");
/*      */ 
/* 1185 */   public static final PdfName LOCKED = new PdfName("Locked");
/*      */ 
/* 1190 */   public static final PdfName LOWERALPHA = new PdfName("LowerAlpha");
/*      */ 
/* 1195 */   public static final PdfName LOWERROMAN = new PdfName("LowerRoman");
/*      */ 
/* 1200 */   public static final PdfName LPTS = new PdfName("LPTS");
/*      */ 
/* 1202 */   public static final PdfName LZWDECODE = new PdfName("LZWDecode");
/*      */ 
/* 1204 */   public static final PdfName M = new PdfName("M");
/*      */ 
/* 1209 */   public static final PdfName MAC = new PdfName("Mac");
/*      */ 
/* 1214 */   public static final PdfName MATERIAL = new PdfName("Material");
/*      */ 
/* 1216 */   public static final PdfName MATRIX = new PdfName("Matrix");
/*      */ 
/* 1218 */   public static final PdfName MAC_EXPERT_ENCODING = new PdfName("MacExpertEncoding");
/*      */ 
/* 1220 */   public static final PdfName MAC_ROMAN_ENCODING = new PdfName("MacRomanEncoding");
/*      */ 
/* 1222 */   public static final PdfName MARKED = new PdfName("Marked");
/*      */ 
/* 1224 */   public static final PdfName MARKINFO = new PdfName("MarkInfo");
/*      */ 
/* 1226 */   public static final PdfName MASK = new PdfName("Mask");
/*      */ 
/* 1231 */   public static final PdfName MAX_LOWER_CASE = new PdfName("max");
/*      */ 
/* 1236 */   public static final PdfName MAX_CAMEL_CASE = new PdfName("Max");
/*      */ 
/* 1238 */   public static final PdfName MAXLEN = new PdfName("MaxLen");
/*      */ 
/* 1240 */   public static final PdfName MEDIABOX = new PdfName("MediaBox");
/*      */ 
/* 1242 */   public static final PdfName MCID = new PdfName("MCID");
/*      */ 
/* 1244 */   public static final PdfName MCR = new PdfName("MCR");
/*      */ 
/* 1249 */   public static final PdfName MEASURE = new PdfName("Measure");
/*      */ 
/* 1251 */   public static final PdfName METADATA = new PdfName("Metadata");
/*      */ 
/* 1256 */   public static final PdfName MIN_LOWER_CASE = new PdfName("min");
/*      */ 
/* 1261 */   public static final PdfName MIN_CAMEL_CASE = new PdfName("Min");
/*      */ 
/* 1263 */   public static final PdfName MK = new PdfName("MK");
/*      */ 
/* 1265 */   public static final PdfName MMTYPE1 = new PdfName("MMType1");
/*      */ 
/* 1267 */   public static final PdfName MODDATE = new PdfName("ModDate");
/*      */ 
/* 1272 */   public static final PdfName MOVIE = new PdfName("Movie");
/*      */ 
/* 1274 */   public static final PdfName N = new PdfName("N");
/*      */ 
/* 1276 */   public static final PdfName N0 = new PdfName("n0");
/*      */ 
/* 1278 */   public static final PdfName N1 = new PdfName("n1");
/*      */ 
/* 1280 */   public static final PdfName N2 = new PdfName("n2");
/*      */ 
/* 1282 */   public static final PdfName N3 = new PdfName("n3");
/*      */ 
/* 1284 */   public static final PdfName N4 = new PdfName("n4");
/*      */ 
/* 1286 */   public static final PdfName NAME = new PdfName("Name");
/*      */ 
/* 1288 */   public static final PdfName NAMED = new PdfName("Named");
/*      */ 
/* 1290 */   public static final PdfName NAMES = new PdfName("Names");
/*      */ 
/* 1295 */   public static final PdfName NAVIGATION = new PdfName("Navigation");
/*      */ 
/* 1300 */   public static final PdfName NAVIGATIONPANE = new PdfName("NavigationPane");
/* 1301 */   public static final PdfName NCHANNEL = new PdfName("NChannel");
/*      */ 
/* 1306 */   public static final PdfName NEAR = new PdfName("Near");
/*      */ 
/* 1308 */   public static final PdfName NEEDAPPEARANCES = new PdfName("NeedAppearances");
/*      */ 
/* 1313 */   public static final PdfName NEEDRENDERING = new PdfName("NeedsRendering");
/*      */ 
/* 1315 */   public static final PdfName NEWWINDOW = new PdfName("NewWindow");
/*      */ 
/* 1317 */   public static final PdfName NEXT = new PdfName("Next");
/*      */ 
/* 1319 */   public static final PdfName NEXTPAGE = new PdfName("NextPage");
/*      */ 
/* 1321 */   public static final PdfName NM = new PdfName("NM");
/*      */ 
/* 1323 */   public static final PdfName NONE = new PdfName("None");
/*      */ 
/* 1325 */   public static final PdfName NONFULLSCREENPAGEMODE = new PdfName("NonFullScreenPageMode");
/*      */ 
/* 1330 */   public static final PdfName NONSTRUCT = new PdfName("NonStruct");
/*      */ 
/* 1335 */   public static final PdfName NOT = new PdfName("Not");
/*      */ 
/* 1340 */   public static final PdfName NOTE = new PdfName("Note");
/*      */ 
/* 1345 */   public static final PdfName NUMBERFORMAT = new PdfName("NumberFormat");
/*      */ 
/* 1347 */   public static final PdfName NUMCOPIES = new PdfName("NumCopies");
/*      */ 
/* 1349 */   public static final PdfName NUMS = new PdfName("Nums");
/*      */ 
/* 1351 */   public static final PdfName O = new PdfName("O");
/*      */ 
/* 1356 */   public static final PdfName OBJ = new PdfName("Obj");
/*      */ 
/* 1361 */   public static final PdfName OBJR = new PdfName("OBJR");
/*      */ 
/* 1363 */   public static final PdfName OBJSTM = new PdfName("ObjStm");
/*      */ 
/* 1365 */   public static final PdfName OC = new PdfName("OC");
/*      */ 
/* 1367 */   public static final PdfName OCG = new PdfName("OCG");
/*      */ 
/* 1369 */   public static final PdfName OCGS = new PdfName("OCGs");
/*      */ 
/* 1371 */   public static final PdfName OCMD = new PdfName("OCMD");
/*      */ 
/* 1373 */   public static final PdfName OCPROPERTIES = new PdfName("OCProperties");
/*      */ 
/* 1377 */   public static final PdfName OCSP = new PdfName("OCSP");
/*      */ 
/* 1381 */   public static final PdfName OCSPS = new PdfName("OCSPs");
/*      */ 
/* 1383 */   public static final PdfName OE = new PdfName("OE");
/*      */ 
/* 1385 */   public static final PdfName Off = new PdfName("Off");
/*      */ 
/* 1387 */   public static final PdfName OFF = new PdfName("OFF");
/*      */ 
/* 1389 */   public static final PdfName ON = new PdfName("ON");
/*      */ 
/* 1391 */   public static final PdfName ONECOLUMN = new PdfName("OneColumn");
/*      */ 
/* 1393 */   public static final PdfName OPEN = new PdfName("Open");
/*      */ 
/* 1395 */   public static final PdfName OPENACTION = new PdfName("OpenAction");
/*      */ 
/* 1397 */   public static final PdfName OP = new PdfName("OP");
/*      */ 
/* 1399 */   public static final PdfName op = new PdfName("op");
/*      */ 
/* 1403 */   public static final PdfName OPI = new PdfName("OPI");
/*      */ 
/* 1405 */   public static final PdfName OPM = new PdfName("OPM");
/*      */ 
/* 1407 */   public static final PdfName OPT = new PdfName("Opt");
/*      */ 
/* 1412 */   public static final PdfName OR = new PdfName("Or");
/*      */ 
/* 1414 */   public static final PdfName ORDER = new PdfName("Order");
/*      */ 
/* 1416 */   public static final PdfName ORDERING = new PdfName("Ordering");
/*      */ 
/* 1421 */   public static final PdfName ORG = new PdfName("Org");
/*      */ 
/* 1426 */   public static final PdfName OSCILLATING = new PdfName("Oscillating");
/*      */ 
/* 1429 */   public static final PdfName OUTLINES = new PdfName("Outlines");
/*      */ 
/* 1431 */   public static final PdfName OUTPUTCONDITION = new PdfName("OutputCondition");
/*      */ 
/* 1433 */   public static final PdfName OUTPUTCONDITIONIDENTIFIER = new PdfName("OutputConditionIdentifier");
/*      */ 
/* 1435 */   public static final PdfName OUTPUTINTENT = new PdfName("OutputIntent");
/*      */ 
/* 1437 */   public static final PdfName OUTPUTINTENTS = new PdfName("OutputIntents");
/*      */ 
/* 1439 */   public static final PdfName P = new PdfName("P");
/*      */ 
/* 1441 */   public static final PdfName PAGE = new PdfName("Page");
/*      */ 
/* 1446 */   public static final PdfName PAGEELEMENT = new PdfName("PageElement");
/*      */ 
/* 1448 */   public static final PdfName PAGELABELS = new PdfName("PageLabels");
/*      */ 
/* 1450 */   public static final PdfName PAGELAYOUT = new PdfName("PageLayout");
/*      */ 
/* 1452 */   public static final PdfName PAGEMODE = new PdfName("PageMode");
/*      */ 
/* 1454 */   public static final PdfName PAGES = new PdfName("Pages");
/*      */ 
/* 1456 */   public static final PdfName PAINTTYPE = new PdfName("PaintType");
/*      */ 
/* 1458 */   public static final PdfName PANOSE = new PdfName("Panose");
/*      */ 
/* 1460 */   public static final PdfName PARAMS = new PdfName("Params");
/*      */ 
/* 1462 */   public static final PdfName PARENT = new PdfName("Parent");
/*      */ 
/* 1464 */   public static final PdfName PARENTTREE = new PdfName("ParentTree");
/*      */ 
/* 1469 */   public static final PdfName PARENTTREENEXTKEY = new PdfName("ParentTreeNextKey");
/*      */ 
/* 1474 */   public static final PdfName PART = new PdfName("Part");
/*      */ 
/* 1479 */   public static final PdfName PASSCONTEXTCLICK = new PdfName("PassContextClick");
/*      */ 
/* 1481 */   public static final PdfName PATTERN = new PdfName("Pattern");
/*      */ 
/* 1483 */   public static final PdfName PATTERNTYPE = new PdfName("PatternType");
/*      */ 
/* 1488 */   public static final PdfName PB = new PdfName("pb");
/*      */ 
/* 1493 */   public static final PdfName PC = new PdfName("PC");
/*      */ 
/* 1495 */   public static final PdfName PDF = new PdfName("PDF");
/*      */ 
/* 1497 */   public static final PdfName PDFDOCENCODING = new PdfName("PDFDocEncoding");
/*      */ 
/* 1502 */   public static final PdfName PDU = new PdfName("PDU");
/*      */ 
/* 1504 */   public static final PdfName PERCEPTUAL = new PdfName("Perceptual");
/*      */ 
/* 1506 */   public static final PdfName PERMS = new PdfName("Perms");
/*      */ 
/* 1508 */   public static final PdfName PG = new PdfName("Pg");
/*      */ 
/* 1513 */   public static final PdfName PI = new PdfName("PI");
/*      */ 
/* 1515 */   public static final PdfName PICKTRAYBYPDFSIZE = new PdfName("PickTrayByPDFSize");
/*      */ 
/* 1520 */   public static final PdfName PIECEINFO = new PdfName("PieceInfo");
/*      */ 
/* 1525 */   public static final PdfName PLAYCOUNT = new PdfName("PlayCount");
/*      */ 
/* 1530 */   public static final PdfName PO = new PdfName("PO");
/*      */ 
/* 1535 */   public static final PdfName POLYGON = new PdfName("Polygon");
/*      */ 
/* 1540 */   public static final PdfName POLYLINE = new PdfName("PolyLine");
/*      */ 
/* 1542 */   public static final PdfName POPUP = new PdfName("Popup");
/*      */ 
/* 1547 */   public static final PdfName POSITION = new PdfName("Position");
/*      */ 
/* 1549 */   public static final PdfName PREDICTOR = new PdfName("Predictor");
/*      */ 
/* 1551 */   public static final PdfName PREFERRED = new PdfName("Preferred");
/*      */ 
/* 1556 */   public static final PdfName PRESENTATION = new PdfName("Presentation");
/*      */ 
/* 1558 */   public static final PdfName PRESERVERB = new PdfName("PreserveRB");
/*      */ 
/* 1563 */   public static final PdfName PRESSTEPS = new PdfName("PresSteps");
/*      */ 
/* 1565 */   public static final PdfName PREV = new PdfName("Prev");
/*      */ 
/* 1567 */   public static final PdfName PREVPAGE = new PdfName("PrevPage");
/*      */ 
/* 1569 */   public static final PdfName PRINT = new PdfName("Print");
/*      */ 
/* 1571 */   public static final PdfName PRINTAREA = new PdfName("PrintArea");
/*      */ 
/* 1573 */   public static final PdfName PRINTCLIP = new PdfName("PrintClip");
/*      */ 
/* 1578 */   public static final PdfName PRINTERMARK = new PdfName("PrinterMark");
/*      */ 
/* 1583 */   public static final PdfName PRINTFIELD = new PdfName("PrintField");
/*      */ 
/* 1585 */   public static final PdfName PRINTPAGERANGE = new PdfName("PrintPageRange");
/*      */ 
/* 1587 */   public static final PdfName PRINTSCALING = new PdfName("PrintScaling");
/*      */ 
/* 1589 */   public static final PdfName PRINTSTATE = new PdfName("PrintState");
/*      */ 
/* 1594 */   public static final PdfName PRIVATE = new PdfName("Private");
/*      */ 
/* 1596 */   public static final PdfName PROCSET = new PdfName("ProcSet");
/*      */ 
/* 1598 */   public static final PdfName PRODUCER = new PdfName("Producer");
/*      */ 
/* 1603 */   public static final PdfName PROJCS = new PdfName("PROJCS");
/*      */ 
/* 1605 */   public static final PdfName PROP_BUILD = new PdfName("Prop_Build");
/*      */ 
/* 1607 */   public static final PdfName PROPERTIES = new PdfName("Properties");
/*      */ 
/* 1609 */   public static final PdfName PS = new PdfName("PS");
/*      */ 
/* 1614 */   public static final PdfName PTDATA = new PdfName("PtData");
/*      */ 
/* 1616 */   public static final PdfName PUBSEC = new PdfName("Adobe.PubSec");
/*      */ 
/* 1621 */   public static final PdfName PV = new PdfName("PV");
/*      */ 
/* 1623 */   public static final PdfName Q = new PdfName("Q");
/*      */ 
/* 1625 */   public static final PdfName QUADPOINTS = new PdfName("QuadPoints");
/*      */ 
/* 1630 */   public static final PdfName QUOTE = new PdfName("Quote");
/*      */ 
/* 1632 */   public static final PdfName R = new PdfName("R");
/*      */ 
/* 1634 */   public static final PdfName R2L = new PdfName("R2L");
/*      */ 
/* 1636 */   public static final PdfName RANGE = new PdfName("Range");
/*      */ 
/* 1641 */   public static final PdfName RB = new PdfName("RB");
/*      */ 
/* 1646 */   public static final PdfName rb = new PdfName("rb");
/*      */ 
/* 1648 */   public static final PdfName RBGROUPS = new PdfName("RBGroups");
/*      */ 
/* 1650 */   public static final PdfName RC = new PdfName("RC");
/*      */ 
/* 1655 */   public static final PdfName RD = new PdfName("RD");
/*      */ 
/* 1657 */   public static final PdfName REASON = new PdfName("Reason");
/*      */ 
/* 1659 */   public static final PdfName RECIPIENTS = new PdfName("Recipients");
/*      */ 
/* 1661 */   public static final PdfName RECT = new PdfName("Rect");
/*      */ 
/* 1663 */   public static final PdfName REFERENCE = new PdfName("Reference");
/*      */ 
/* 1665 */   public static final PdfName REGISTRY = new PdfName("Registry");
/*      */ 
/* 1667 */   public static final PdfName REGISTRYNAME = new PdfName("RegistryName");
/*      */ 
/* 1672 */   public static final PdfName RELATIVECOLORIMETRIC = new PdfName("RelativeColorimetric");
/*      */ 
/* 1674 */   public static final PdfName RENDITION = new PdfName("Rendition");
/*      */ 
/* 1676 */   public static final PdfName RESETFORM = new PdfName("ResetForm");
/*      */ 
/* 1678 */   public static final PdfName RESOURCES = new PdfName("Resources");
/* 1679 */   public static final PdfName REQUIREMENTS = new PdfName("Requirements");
/*      */ 
/* 1681 */   public static final PdfName RI = new PdfName("RI");
/*      */ 
/* 1686 */   public static final PdfName RICHMEDIA = new PdfName("RichMedia");
/*      */ 
/* 1691 */   public static final PdfName RICHMEDIAACTIVATION = new PdfName("RichMediaActivation");
/*      */ 
/* 1696 */   public static final PdfName RICHMEDIAANIMATION = new PdfName("RichMediaAnimation");
/*      */ 
/* 1701 */   public static final PdfName RICHMEDIACOMMAND = new PdfName("RichMediaCommand");
/*      */ 
/* 1706 */   public static final PdfName RICHMEDIACONFIGURATION = new PdfName("RichMediaConfiguration");
/*      */ 
/* 1711 */   public static final PdfName RICHMEDIACONTENT = new PdfName("RichMediaContent");
/*      */ 
/* 1716 */   public static final PdfName RICHMEDIADEACTIVATION = new PdfName("RichMediaDeactivation");
/*      */ 
/* 1721 */   public static final PdfName RICHMEDIAEXECUTE = new PdfName("RichMediaExecute");
/*      */ 
/* 1726 */   public static final PdfName RICHMEDIAINSTANCE = new PdfName("RichMediaInstance");
/*      */ 
/* 1731 */   public static final PdfName RICHMEDIAPARAMS = new PdfName("RichMediaParams");
/*      */ 
/* 1736 */   public static final PdfName RICHMEDIAPOSITION = new PdfName("RichMediaPosition");
/*      */ 
/* 1741 */   public static final PdfName RICHMEDIAPRESENTATION = new PdfName("RichMediaPresentation");
/*      */ 
/* 1746 */   public static final PdfName RICHMEDIASETTINGS = new PdfName("RichMediaSettings");
/*      */ 
/* 1751 */   public static final PdfName RICHMEDIAWINDOW = new PdfName("RichMediaWindow");
/*      */ 
/* 1756 */   public static final PdfName RL = new PdfName("RL");
/*      */ 
/* 1761 */   public static final PdfName ROLE = new PdfName("Role");
/*      */ 
/* 1763 */   public static final PdfName ROLEMAP = new PdfName("RoleMap");
/*      */ 
/* 1765 */   public static final PdfName ROOT = new PdfName("Root");
/*      */ 
/* 1767 */   public static final PdfName ROTATE = new PdfName("Rotate");
/*      */ 
/* 1772 */   public static final PdfName ROW = new PdfName("Row");
/*      */ 
/* 1774 */   public static final PdfName ROWS = new PdfName("Rows");
/*      */ 
/* 1779 */   public static final PdfName ROWSPAN = new PdfName("RowSpan");
/*      */ 
/* 1784 */   public static final PdfName RP = new PdfName("RP");
/*      */ 
/* 1789 */   public static final PdfName RT = new PdfName("RT");
/*      */ 
/* 1794 */   public static final PdfName RUBY = new PdfName("Ruby");
/*      */ 
/* 1796 */   public static final PdfName RUNLENGTHDECODE = new PdfName("RunLengthDecode");
/*      */ 
/* 1798 */   public static final PdfName RV = new PdfName("RV");
/*      */ 
/* 1800 */   public static final PdfName S = new PdfName("S");
/*      */ 
/* 1802 */   public static final PdfName SATURATION = new PdfName("Saturation");
/*      */ 
/* 1804 */   public static final PdfName SCHEMA = new PdfName("Schema");
/*      */ 
/* 1809 */   public static final PdfName SCOPE = new PdfName("Scope");
/*      */ 
/* 1811 */   public static final PdfName SCREEN = new PdfName("Screen");
/*      */ 
/* 1816 */   public static final PdfName SCRIPTS = new PdfName("Scripts");
/*      */ 
/* 1818 */   public static final PdfName SECT = new PdfName("Sect");
/*      */ 
/* 1820 */   public static final PdfName SEPARATION = new PdfName("Separation");
/*      */ 
/* 1822 */   public static final PdfName SETOCGSTATE = new PdfName("SetOCGState");
/*      */ 
/* 1827 */   public static final PdfName SETTINGS = new PdfName("Settings");
/*      */ 
/* 1829 */   public static final PdfName SHADING = new PdfName("Shading");
/*      */ 
/* 1831 */   public static final PdfName SHADINGTYPE = new PdfName("ShadingType");
/*      */ 
/* 1833 */   public static final PdfName SHIFT_JIS = new PdfName("Shift-JIS");
/*      */ 
/* 1835 */   public static final PdfName SIG = new PdfName("Sig");
/*      */ 
/* 1837 */   public static final PdfName SIGFIELDLOCK = new PdfName("SigFieldLock");
/*      */ 
/* 1839 */   public static final PdfName SIGFLAGS = new PdfName("SigFlags");
/*      */ 
/* 1841 */   public static final PdfName SIGREF = new PdfName("SigRef");
/*      */ 
/* 1843 */   public static final PdfName SIMPLEX = new PdfName("Simplex");
/*      */ 
/* 1845 */   public static final PdfName SINGLEPAGE = new PdfName("SinglePage");
/*      */ 
/* 1847 */   public static final PdfName SIZE = new PdfName("Size");
/*      */ 
/* 1849 */   public static final PdfName SMASK = new PdfName("SMask");
/*      */ 
/* 1851 */   public static final PdfName SMASKINDATA = new PdfName("SMaskInData");
/*      */ 
/* 1853 */   public static final PdfName SORT = new PdfName("Sort");
/*      */ 
/* 1858 */   public static final PdfName SOUND = new PdfName("Sound");
/*      */ 
/* 1863 */   public static final PdfName SPACEAFTER = new PdfName("SpaceAfter");
/*      */ 
/* 1868 */   public static final PdfName SPACEBEFORE = new PdfName("SpaceBefore");
/*      */ 
/* 1870 */   public static final PdfName SPAN = new PdfName("Span");
/*      */ 
/* 1875 */   public static final PdfName SPEED = new PdfName("Speed");
/*      */ 
/* 1877 */   public static final PdfName SPLIT = new PdfName("Split");
/*      */ 
/* 1879 */   public static final PdfName SQUARE = new PdfName("Square");
/*      */ 
/* 1884 */   public static final PdfName SQUIGGLY = new PdfName("Squiggly");
/*      */ 
/* 1889 */   public static final PdfName SS = new PdfName("SS");
/*      */ 
/* 1891 */   public static final PdfName ST = new PdfName("St");
/*      */ 
/* 1893 */   public static final PdfName STAMP = new PdfName("Stamp");
/*      */ 
/* 1895 */   public static final PdfName STATUS = new PdfName("Status");
/*      */ 
/* 1897 */   public static final PdfName STANDARD = new PdfName("Standard");
/*      */ 
/* 1902 */   public static final PdfName START = new PdfName("Start");
/*      */ 
/* 1907 */   public static final PdfName STARTINDENT = new PdfName("StartIndent");
/*      */ 
/* 1909 */   public static final PdfName STATE = new PdfName("State");
/*      */ 
/* 1911 */   public static final PdfName STDCF = new PdfName("StdCF");
/*      */ 
/* 1913 */   public static final PdfName STEMV = new PdfName("StemV");
/*      */ 
/* 1915 */   public static final PdfName STMF = new PdfName("StmF");
/*      */ 
/* 1917 */   public static final PdfName STRF = new PdfName("StrF");
/*      */ 
/* 1919 */   public static final PdfName STRIKEOUT = new PdfName("StrikeOut");
/*      */ 
/* 1924 */   public static final PdfName STRUCTELEM = new PdfName("StructElem");
/*      */ 
/* 1926 */   public static final PdfName STRUCTPARENT = new PdfName("StructParent");
/*      */ 
/* 1928 */   public static final PdfName STRUCTPARENTS = new PdfName("StructParents");
/*      */ 
/* 1930 */   public static final PdfName STRUCTTREEROOT = new PdfName("StructTreeRoot");
/*      */ 
/* 1932 */   public static final PdfName STYLE = new PdfName("Style");
/*      */ 
/* 1934 */   public static final PdfName SUBFILTER = new PdfName("SubFilter");
/*      */ 
/* 1936 */   public static final PdfName SUBJECT = new PdfName("Subject");
/*      */ 
/* 1938 */   public static final PdfName SUBMITFORM = new PdfName("SubmitForm");
/*      */ 
/* 1940 */   public static final PdfName SUBTYPE = new PdfName("Subtype");
/*      */ 
/* 1942 */   public static final PdfName SUPPLEMENT = new PdfName("Supplement");
/*      */ 
/* 1944 */   public static final PdfName SV = new PdfName("SV");
/*      */ 
/* 1946 */   public static final PdfName SW = new PdfName("SW");
/*      */ 
/* 1948 */   public static final PdfName SYMBOL = new PdfName("Symbol");
/*      */ 
/* 1950 */   public static final PdfName T = new PdfName("T");
/*      */ 
/* 1955 */   public static final PdfName TA = new PdfName("TA");
/*      */ 
/* 1960 */   public static final PdfName TABLE = new PdfName("Table");
/*      */ 
/* 1965 */   public static final PdfName TABS = new PdfName("Tabs");
/*      */ 
/* 1970 */   public static final PdfName TBODY = new PdfName("TBody");
/*      */ 
/* 1975 */   public static final PdfName TD = new PdfName("TD");
/*      */ 
/* 1980 */   public static final PdfName TR = new PdfName("TR");
/*      */ 
/* 1985 */   public static final PdfName TR2 = new PdfName("TR2");
/*      */ 
/* 1987 */   public static final PdfName TEXT = new PdfName("Text");
/*      */ 
/* 1992 */   public static final PdfName TEXTALIGN = new PdfName("TextAlign");
/*      */ 
/* 1997 */   public static final PdfName TEXTDECORATIONCOLOR = new PdfName("TextDecorationColor");
/*      */ 
/* 2002 */   public static final PdfName TEXTDECORATIONTHICKNESS = new PdfName("TextDecorationThickness");
/*      */ 
/* 2007 */   public static final PdfName TEXTDECORATIONTYPE = new PdfName("TextDecorationType");
/*      */ 
/* 2012 */   public static final PdfName TEXTINDENT = new PdfName("TextIndent");
/*      */ 
/* 2017 */   public static final PdfName TFOOT = new PdfName("TFoot");
/*      */ 
/* 2022 */   public static final PdfName TH = new PdfName("TH");
/*      */ 
/* 2027 */   public static final PdfName THEAD = new PdfName("THead");
/*      */ 
/* 2029 */   public static final PdfName THUMB = new PdfName("Thumb");
/*      */ 
/* 2031 */   public static final PdfName THREADS = new PdfName("Threads");
/*      */ 
/* 2033 */   public static final PdfName TI = new PdfName("TI");
/*      */ 
/* 2038 */   public static final PdfName TIME = new PdfName("Time");
/*      */ 
/* 2040 */   public static final PdfName TILINGTYPE = new PdfName("TilingType");
/*      */ 
/* 2042 */   public static final PdfName TIMES_ROMAN = new PdfName("Times-Roman");
/*      */ 
/* 2044 */   public static final PdfName TIMES_BOLD = new PdfName("Times-Bold");
/*      */ 
/* 2046 */   public static final PdfName TIMES_ITALIC = new PdfName("Times-Italic");
/*      */ 
/* 2048 */   public static final PdfName TIMES_BOLDITALIC = new PdfName("Times-BoldItalic");
/*      */ 
/* 2050 */   public static final PdfName TITLE = new PdfName("Title");
/*      */ 
/* 2052 */   public static final PdfName TK = new PdfName("TK");
/*      */ 
/* 2054 */   public static final PdfName TM = new PdfName("TM");
/*      */ 
/* 2059 */   public static final PdfName TOC = new PdfName("TOC");
/*      */ 
/* 2064 */   public static final PdfName TOCI = new PdfName("TOCI");
/*      */ 
/* 2066 */   public static final PdfName TOGGLE = new PdfName("Toggle");
/*      */ 
/* 2071 */   public static final PdfName TOOLBAR = new PdfName("Toolbar");
/*      */ 
/* 2073 */   public static final PdfName TOUNICODE = new PdfName("ToUnicode");
/*      */ 
/* 2075 */   public static final PdfName TP = new PdfName("TP");
/*      */ 
/* 2080 */   public static final PdfName TABLEROW = new PdfName("TR");
/*      */ 
/* 2082 */   public static final PdfName TRANS = new PdfName("Trans");
/*      */ 
/* 2084 */   public static final PdfName TRANSFORMPARAMS = new PdfName("TransformParams");
/*      */ 
/* 2086 */   public static final PdfName TRANSFORMMETHOD = new PdfName("TransformMethod");
/*      */ 
/* 2088 */   public static final PdfName TRANSPARENCY = new PdfName("Transparency");
/*      */ 
/* 2093 */   public static final PdfName TRANSPARENT = new PdfName("Transparent");
/*      */ 
/* 2098 */   public static final PdfName TRAPNET = new PdfName("TrapNet");
/*      */ 
/* 2100 */   public static final PdfName TRAPPED = new PdfName("Trapped");
/*      */ 
/* 2102 */   public static final PdfName TRIMBOX = new PdfName("TrimBox");
/*      */ 
/* 2104 */   public static final PdfName TRUETYPE = new PdfName("TrueType");
/*      */ 
/* 2109 */   public static final PdfName TS = new PdfName("TS");
/*      */ 
/* 2114 */   public static final PdfName TTL = new PdfName("Ttl");
/*      */ 
/* 2116 */   public static final PdfName TU = new PdfName("TU");
/*      */ 
/* 2121 */   public static final PdfName TV = new PdfName("tv");
/*      */ 
/* 2123 */   public static final PdfName TWOCOLUMNLEFT = new PdfName("TwoColumnLeft");
/*      */ 
/* 2125 */   public static final PdfName TWOCOLUMNRIGHT = new PdfName("TwoColumnRight");
/*      */ 
/* 2127 */   public static final PdfName TWOPAGELEFT = new PdfName("TwoPageLeft");
/*      */ 
/* 2129 */   public static final PdfName TWOPAGERIGHT = new PdfName("TwoPageRight");
/*      */ 
/* 2131 */   public static final PdfName TX = new PdfName("Tx");
/*      */ 
/* 2133 */   public static final PdfName TYPE = new PdfName("Type");
/*      */ 
/* 2135 */   public static final PdfName TYPE0 = new PdfName("Type0");
/*      */ 
/* 2137 */   public static final PdfName TYPE1 = new PdfName("Type1");
/*      */ 
/* 2139 */   public static final PdfName TYPE3 = new PdfName("Type3");
/*      */ 
/* 2141 */   public static final PdfName U = new PdfName("U");
/*      */ 
/* 2143 */   public static final PdfName UE = new PdfName("UE");
/*      */ 
/* 2145 */   public static final PdfName UF = new PdfName("UF");
/*      */ 
/* 2147 */   public static final PdfName UHC = new PdfName("UHC");
/*      */ 
/* 2149 */   public static final PdfName UNDERLINE = new PdfName("Underline");
/*      */ 
/* 2154 */   public static final PdfName UNIX = new PdfName("Unix");
/*      */ 
/* 2159 */   public static final PdfName UPPERALPHA = new PdfName("UpperAlpha");
/*      */ 
/* 2164 */   public static final PdfName UPPERROMAN = new PdfName("UpperRoman");
/*      */ 
/* 2166 */   public static final PdfName UR = new PdfName("UR");
/*      */ 
/* 2168 */   public static final PdfName UR3 = new PdfName("UR3");
/*      */ 
/* 2170 */   public static final PdfName URI = new PdfName("URI");
/*      */ 
/* 2172 */   public static final PdfName URL = new PdfName("URL");
/*      */ 
/* 2174 */   public static final PdfName USAGE = new PdfName("Usage");
/*      */ 
/* 2176 */   public static final PdfName USEATTACHMENTS = new PdfName("UseAttachments");
/*      */ 
/* 2178 */   public static final PdfName USENONE = new PdfName("UseNone");
/*      */ 
/* 2180 */   public static final PdfName USEOC = new PdfName("UseOC");
/*      */ 
/* 2182 */   public static final PdfName USEOUTLINES = new PdfName("UseOutlines");
/*      */ 
/* 2184 */   public static final PdfName USER = new PdfName("User");
/*      */ 
/* 2186 */   public static final PdfName USERPROPERTIES = new PdfName("UserProperties");
/*      */ 
/* 2188 */   public static final PdfName USERUNIT = new PdfName("UserUnit");
/*      */ 
/* 2190 */   public static final PdfName USETHUMBS = new PdfName("UseThumbs");
/*      */ 
/* 2195 */   public static final PdfName UTF_8 = new PdfName("utf_8");
/*      */ 
/* 2197 */   public static final PdfName V = new PdfName("V");
/*      */ 
/* 2199 */   public static final PdfName V2 = new PdfName("V2");
/*      */ 
/* 2204 */   public static final PdfName VALIGN = new PdfName("VAlign");
/*      */ 
/* 2209 */   public static final PdfName VE = new PdfName("VE");
/*      */ 
/* 2211 */   public static final PdfName VERISIGN_PPKVS = new PdfName("VeriSign.PPKVS");
/*      */ 
/* 2213 */   public static final PdfName VERSION = new PdfName("Version");
/*      */ 
/* 2218 */   public static final PdfName VERTICES = new PdfName("Vertices");
/*      */ 
/* 2223 */   public static final PdfName VIDEO = new PdfName("Video");
/*      */ 
/* 2225 */   public static final PdfName VIEW = new PdfName("View");
/*      */ 
/* 2230 */   public static final PdfName VIEWS = new PdfName("Views");
/*      */ 
/* 2232 */   public static final PdfName VIEWAREA = new PdfName("ViewArea");
/*      */ 
/* 2234 */   public static final PdfName VIEWCLIP = new PdfName("ViewClip");
/*      */ 
/* 2236 */   public static final PdfName VIEWERPREFERENCES = new PdfName("ViewerPreferences");
/*      */ 
/* 2241 */   public static final PdfName VIEWPORT = new PdfName("Viewport");
/*      */ 
/* 2243 */   public static final PdfName VIEWSTATE = new PdfName("ViewState");
/*      */ 
/* 2245 */   public static final PdfName VISIBLEPAGES = new PdfName("VisiblePages");
/*      */ 
/* 2250 */   public static final PdfName VOFFSET = new PdfName("VOffset");
/*      */ 
/* 2255 */   public static final PdfName VP = new PdfName("VP");
/*      */ 
/* 2260 */   public static final PdfName VRI = new PdfName("VRI");
/*      */ 
/* 2262 */   public static final PdfName W = new PdfName("W");
/*      */ 
/* 2264 */   public static final PdfName W2 = new PdfName("W2");
/*      */ 
/* 2269 */   public static final PdfName WARICHU = new PdfName("Warichu");
/*      */ 
/* 2274 */   public static final PdfName WATERMARK = new PdfName("Watermark");
/*      */ 
/* 2276 */   public static final PdfName WC = new PdfName("WC");
/*      */ 
/* 2278 */   public static final PdfName WIDGET = new PdfName("Widget");
/*      */ 
/* 2280 */   public static final PdfName WIDTH = new PdfName("Width");
/*      */ 
/* 2282 */   public static final PdfName WIDTHS = new PdfName("Widths");
/*      */ 
/* 2284 */   public static final PdfName WIN = new PdfName("Win");
/*      */ 
/* 2286 */   public static final PdfName WIN_ANSI_ENCODING = new PdfName("WinAnsiEncoding");
/*      */ 
/* 2291 */   public static final PdfName WINDOW = new PdfName("Window");
/*      */ 
/* 2296 */   public static final PdfName WINDOWED = new PdfName("Windowed");
/*      */ 
/* 2298 */   public static final PdfName WIPE = new PdfName("Wipe");
/*      */ 
/* 2300 */   public static final PdfName WHITEPOINT = new PdfName("WhitePoint");
/*      */ 
/* 2305 */   public static final PdfName WKT = new PdfName("WKT");
/*      */ 
/* 2307 */   public static final PdfName WP = new PdfName("WP");
/*      */ 
/* 2309 */   public static final PdfName WS = new PdfName("WS");
/*      */ 
/* 2314 */   public static final PdfName WT = new PdfName("WT");
/*      */ 
/* 2316 */   public static final PdfName X = new PdfName("X");
/*      */ 
/* 2321 */   public static final PdfName XA = new PdfName("XA");
/*      */ 
/* 2326 */   public static final PdfName XD = new PdfName("XD");
/*      */ 
/* 2328 */   public static final PdfName XFA = new PdfName("XFA");
/*      */ 
/* 2330 */   public static final PdfName XML = new PdfName("XML");
/*      */ 
/* 2332 */   public static final PdfName XOBJECT = new PdfName("XObject");
/*      */ 
/* 2337 */   public static final PdfName XPTS = new PdfName("XPTS");
/*      */ 
/* 2339 */   public static final PdfName XREF = new PdfName("XRef");
/*      */ 
/* 2341 */   public static final PdfName XREFSTM = new PdfName("XRefStm");
/*      */ 
/* 2343 */   public static final PdfName XSTEP = new PdfName("XStep");
/*      */ 
/* 2345 */   public static final PdfName XYZ = new PdfName("XYZ");
/*      */ 
/* 2347 */   public static final PdfName YSTEP = new PdfName("YStep");
/*      */ 
/* 2349 */   public static final PdfName ZADB = new PdfName("ZaDb");
/*      */ 
/* 2351 */   public static final PdfName ZAPFDINGBATS = new PdfName("ZapfDingbats");
/*      */ 
/* 2353 */   public static final PdfName ZOOM = new PdfName("Zoom");
/*      */   public static Map<String, PdfName> staticNames;
/* 2388 */   private int hash = 0;
/*      */ 
/*      */   public PdfName(String name)
/*      */   {
/* 2398 */     this(name, true);
/*      */   }
/*      */ 
/*      */   public PdfName(String name, boolean lengthCheck)
/*      */   {
/* 2408 */     super(4);
/*      */ 
/* 2410 */     int length = name.length();
/* 2411 */     if ((lengthCheck) && (length > 127))
/* 2412 */       throw new IllegalArgumentException(MessageLocalization.getComposedMessage("the.name.1.is.too.long.2.characters", new Object[] { name, String.valueOf(length) }));
/* 2413 */     this.bytes = encodeName(name);
/*      */   }
/*      */ 
/*      */   public PdfName(byte[] bytes)
/*      */   {
/* 2422 */     super(4, bytes);
/*      */   }
/*      */ 
/*      */   public int compareTo(PdfName name)
/*      */   {
/* 2439 */     byte[] myBytes = this.bytes;
/* 2440 */     byte[] objBytes = name.bytes;
/* 2441 */     int len = Math.min(myBytes.length, objBytes.length);
/* 2442 */     for (int i = 0; i < len; i++) {
/* 2443 */       if (myBytes[i] > objBytes[i])
/* 2444 */         return 1;
/* 2445 */       if (myBytes[i] < objBytes[i])
/* 2446 */         return -1;
/*      */     }
/* 2448 */     if (myBytes.length < objBytes.length)
/* 2449 */       return -1;
/* 2450 */     if (myBytes.length > objBytes.length)
/* 2451 */       return 1;
/* 2452 */     return 0;
/*      */   }
/*      */ 
/*      */   public boolean equals(Object obj)
/*      */   {
/* 2464 */     if (this == obj)
/* 2465 */       return true;
/* 2466 */     if ((obj instanceof PdfName))
/* 2467 */       return compareTo((PdfName)obj) == 0;
/* 2468 */     return false;
/*      */   }
/*      */ 
/*      */   public int hashCode()
/*      */   {
/* 2480 */     int h = this.hash;
/* 2481 */     if (h == 0) {
/* 2482 */       int ptr = 0;
/* 2483 */       int len = this.bytes.length;
/* 2484 */       for (int i = 0; i < len; i++)
/* 2485 */         h = 31 * h + (this.bytes[(ptr++)] & 0xFF);
/* 2486 */       this.hash = h;
/*      */     }
/* 2488 */     return h;
/*      */   }
/*      */ 
/*      */   public static byte[] encodeName(String name)
/*      */   {
/* 2499 */     int length = name.length();
/* 2500 */     ByteBuffer buf = new ByteBuffer(length + 20);
/* 2501 */     buf.append('/');
/*      */ 
/* 2503 */     char[] chars = name.toCharArray();
/* 2504 */     for (int k = 0; k < length; k++) {
/* 2505 */       char c = (char)(chars[k] & 0xFF);
/*      */ 
/* 2507 */       switch (c) {
/*      */       case ' ':
/*      */       case '#':
/*      */       case '%':
/*      */       case '(':
/*      */       case ')':
/*      */       case '/':
/*      */       case '<':
/*      */       case '>':
/*      */       case '[':
/*      */       case ']':
/*      */       case '{':
/*      */       case '}':
/* 2520 */         buf.append('#');
/* 2521 */         buf.append(Integer.toString(c, 16));
/* 2522 */         break;
/*      */       default:
/* 2524 */         if ((c >= ' ') && (c <= '~')) {
/* 2525 */           buf.append(c);
/*      */         } else {
/* 2527 */           buf.append('#');
/* 2528 */           if (c < '\020')
/* 2529 */             buf.append('0');
/* 2530 */           buf.append(Integer.toString(c, 16));
/*      */         }
/*      */         break;
/*      */       }
/*      */     }
/* 2535 */     return buf.toByteArray();
/*      */   }
/*      */ 
/*      */   public static String decodeName(String name)
/*      */   {
/* 2545 */     StringBuffer buf = new StringBuffer();
/*      */     try {
/* 2547 */       int len = name.length();
/* 2548 */       for (int k = 1; k < len; k++) {
/* 2549 */         char c = name.charAt(k);
/* 2550 */         if (c == '#') {
/* 2551 */           char c1 = name.charAt(k + 1);
/* 2552 */           char c2 = name.charAt(k + 2);
/* 2553 */           c = (char)((PRTokeniser.getHex(c1) << 4) + PRTokeniser.getHex(c2));
/* 2554 */           k += 2;
/*      */         }
/* 2556 */         buf.append(c);
/*      */       }
/*      */     }
/*      */     catch (IndexOutOfBoundsException e)
/*      */     {
/*      */     }
/* 2562 */     return buf.toString();
/*      */   }
/*      */ 
/*      */   static
/*      */   {
/* 2370 */     Field[] fields = PdfName.class.getDeclaredFields();
/* 2371 */     staticNames = new HashMap(fields.length);
/* 2372 */     int flags = 25;
/*      */     try {
/* 2374 */       for (int fldIdx = 0; fldIdx < fields.length; fldIdx++) {
/* 2375 */         Field curFld = fields[fldIdx];
/* 2376 */         if (((curFld.getModifiers() & 0x19) == 25) && (curFld.getType().equals(PdfName.class)))
/*      */         {
/* 2378 */           PdfName name = (PdfName)curFld.get(null);
/* 2379 */           staticNames.put(decodeName(name.toString()), name);
/*      */         }
/*      */       }
/*      */     } catch (Exception e) {
/* 2383 */       e.printStackTrace();
/*      */     }
/*      */   }
/*      */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.pdf.PdfName
 * JD-Core Version:    0.6.2
 */