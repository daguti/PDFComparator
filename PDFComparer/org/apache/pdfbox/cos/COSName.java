/*      */ package org.apache.pdfbox.cos;
/*      */ 
/*      */ import java.io.IOException;
/*      */ import java.io.OutputStream;
/*      */ import java.util.HashMap;
/*      */ import java.util.Map;
/*      */ import java.util.concurrent.ConcurrentHashMap;
/*      */ import org.apache.pdfbox.exceptions.COSVisitorException;
/*      */ 
/*      */ public final class COSName extends COSBase
/*      */   implements Comparable<COSName>
/*      */ {
/*   40 */   private static Map<String, COSName> nameMap = new ConcurrentHashMap(8192);
/*      */ 
/*   46 */   private static Map<String, COSName> commonNameMap = new HashMap();
/*      */ 
/*   52 */   public static final COSName A = new COSName("A");
/*      */ 
/*   56 */   public static final COSName AA = new COSName("AA");
/*      */ 
/*   60 */   public static final COSName ACRO_FORM = new COSName("AcroForm");
/*      */ 
/*   64 */   public static final COSName ACTUAL_TEXT = new COSName("ActualText");
/*      */ 
/*   68 */   public static final COSName AIS = new COSName("AIS");
/*      */ 
/*   72 */   public static final COSName ALT = new COSName("Alt");
/*      */ 
/*   76 */   public static final COSName ALTERNATE = new COSName("Alternate");
/*      */ 
/*   80 */   public static final COSName ANNOT = new COSName("Annot");
/*      */ 
/*   84 */   public static final COSName ANNOTS = new COSName("Annots");
/*      */ 
/*   88 */   public static final COSName ANTI_ALIAS = new COSName("AntiAlias");
/*      */ 
/*   92 */   public static final COSName AP_REF = new COSName("APRef");
/*      */ 
/*   96 */   public static final COSName ARTIFACT = new COSName("Artifact");
/*      */ 
/*  100 */   public static final COSName ART_BOX = new COSName("ArtBox");
/*      */ 
/*  104 */   public static final COSName AS = new COSName("AS");
/*      */ 
/*  108 */   public static final COSName ASCII85_DECODE = new COSName("ASCII85Decode");
/*      */ 
/*  112 */   public static final COSName ASCII85_DECODE_ABBREVIATION = new COSName("A85");
/*      */ 
/*  116 */   public static final COSName ATTACHED = new COSName("Attached");
/*      */ 
/*  120 */   public static final COSName ASCENT = new COSName("Ascent");
/*      */ 
/*  124 */   public static final COSName ASCII_HEX_DECODE = new COSName("ASCIIHexDecode");
/*      */ 
/*  128 */   public static final COSName ASCII_HEX_DECODE_ABBREVIATION = new COSName("AHx");
/*      */ 
/*  133 */   public static final COSName AP = new COSName("AP");
/*      */ 
/*  137 */   public static final COSName APP = new COSName("App");
/*      */ 
/*  142 */   public static final COSName AUTHOR = new COSName("Author");
/*      */ 
/*  147 */   public static final COSName AVG_WIDTH = new COSName("AvgWidth");
/*      */ 
/*  152 */   public static final COSName B = new COSName("B");
/*      */ 
/*  156 */   public static final COSName BACKGROUND = new COSName("Background");
/*      */ 
/*  160 */   public static final COSName BASE_ENCODING = new COSName("BaseEncoding");
/*      */ 
/*  164 */   public static final COSName BASE_FONT = new COSName("BaseFont");
/*      */ 
/*  167 */   public static final COSName BASE_STATE = new COSName("BaseState");
/*      */ 
/*  172 */   public static final COSName BBOX = new COSName("BBox");
/*      */ 
/*  176 */   public static final COSName BLACK_IS_1 = new COSName("BlackIs1");
/*      */ 
/*  180 */   public static final COSName BLACK_POINT = new COSName("BlackPoint");
/*      */ 
/*  185 */   public static final COSName BLEED_BOX = new COSName("BleedBox");
/*      */ 
/*  189 */   public static final COSName BITS_PER_COMPONENT = new COSName("BitsPerComponent");
/*      */ 
/*  193 */   public static final COSName BITS_PER_COORDINATE = new COSName("BitsPerCoordinate");
/*      */ 
/*  197 */   public static final COSName BITS_PER_FLAG = new COSName("BitsPerFlag");
/*      */ 
/*  201 */   public static final COSName BITS_PER_SAMPLE = new COSName("BitsPerSample");
/*      */ 
/*  205 */   public static final COSName BOUNDS = new COSName("Bounds");
/*      */ 
/*  209 */   public static final COSName BPC = new COSName("BPC");
/*      */ 
/*  213 */   public static final COSName CATALOG = new COSName("Catalog");
/*      */ 
/*  217 */   public static final COSName C = new COSName("C");
/*      */ 
/*  221 */   public static final COSName C0 = new COSName("C0");
/*      */ 
/*  225 */   public static final COSName C1 = new COSName("C1");
/*      */ 
/*  229 */   public static final COSName CA = new COSName("CA");
/*      */ 
/*  233 */   public static final COSName CA_NS = new COSName("ca");
/*      */ 
/*  237 */   public static final COSName CALGRAY = new COSName("CalGray");
/*      */ 
/*  241 */   public static final COSName CALRGB = new COSName("CalRGB");
/*      */ 
/*  245 */   public static final COSName CAP_HEIGHT = new COSName("CapHeight");
/*      */ 
/*  249 */   public static final COSName CCITTFAX_DECODE = new COSName("CCITTFaxDecode");
/*      */ 
/*  253 */   public static final COSName CCITTFAX_DECODE_ABBREVIATION = new COSName("CCF");
/*      */ 
/*  257 */   public static final COSName CENTER_WINDOW = new COSName("CenterWindow");
/*      */ 
/*  261 */   public static final COSName CF = new COSName("CF");
/*      */ 
/*  265 */   public static final COSName CFM = new COSName("CFM");
/*      */ 
/*  269 */   public static final COSName CHAR_PROCS = new COSName("CharProcs");
/*      */ 
/*  273 */   public static final COSName CHAR_SET = new COSName("CharSet");
/*      */ 
/*  277 */   public static final COSName CID_FONT_TYPE0 = new COSName("CIDFontType0");
/*      */ 
/*  281 */   public static final COSName CID_FONT_TYPE2 = new COSName("CIDFontType2");
/*      */ 
/*  285 */   public static final COSName CIDSYSTEMINFO = new COSName("CIDSystemInfo");
/*      */ 
/*  289 */   public static final COSName CID_TO_GID_MAP = new COSName("CIDToGIDMap");
/*      */ 
/*  293 */   public static final COSName COLORANTS = new COSName("Colorants");
/*      */ 
/*  297 */   public static final COSName COLORS = new COSName("Colors");
/*      */ 
/*  301 */   public static final COSName COLORSPACE = new COSName("ColorSpace");
/*      */ 
/*  305 */   public static final COSName COLUMNS = new COSName("Columns");
/*      */ 
/*  309 */   public static final COSName CONTACT_INFO = new COSName("ContactInfo");
/*      */ 
/*  313 */   public static final COSName CONTENTS = new COSName("Contents");
/*      */ 
/*  317 */   public static final COSName COORDS = new COSName("Coords");
/*      */ 
/*  321 */   public static final COSName COUNT = new COSName("Count");
/*      */ 
/*  325 */   public static final COSName CLR_F = new COSName("ClrF");
/*      */ 
/*  329 */   public static final COSName CLR_FF = new COSName("ClrFf");
/*      */ 
/*  333 */   public static final COSName CREATION_DATE = new COSName("CreationDate");
/*      */ 
/*  337 */   public static final COSName CREATOR = new COSName("Creator");
/*      */ 
/*  341 */   public static final COSName CROP_BOX = new COSName("CropBox");
/*      */ 
/*  345 */   public static final COSName CRYPT = new COSName("Crypt");
/*      */ 
/*  349 */   public static final COSName CS = new COSName("CS");
/*      */ 
/*  353 */   public static final COSName DEFAULT = new COSName("default");
/*      */ 
/*  357 */   public static final COSName D = new COSName("D");
/*      */ 
/*  361 */   public static final COSName DA = new COSName("DA");
/*      */ 
/*  365 */   public static final COSName DATE = new COSName("Date");
/*      */ 
/*  369 */   public static final COSName DCT_DECODE = new COSName("DCTDecode");
/*      */ 
/*  373 */   public static final COSName DCT_DECODE_ABBREVIATION = new COSName("DCT");
/*      */ 
/*  378 */   public static final COSName DECODE = new COSName("Decode");
/*      */ 
/*  382 */   public static final COSName DECODE_PARMS = new COSName("DecodeParms");
/*      */ 
/*  387 */   public static final COSName DESC = new COSName("Desc");
/*      */ 
/*  391 */   public static final COSName DESCENT = new COSName("Descent");
/*      */ 
/*  395 */   public static final COSName DESCENDANT_FONTS = new COSName("DescendantFonts");
/*      */ 
/*  399 */   public static final COSName DEST = new COSName("Dest");
/*      */ 
/*  404 */   public static final COSName DESTS = new COSName("Dests");
/*      */ 
/*  409 */   public static final COSName DEST_OUTPUT_PROFILE = new COSName("DestOutputProfile");
/*      */ 
/*  414 */   public static final COSName DEVICECMYK = new COSName("DeviceCMYK");
/*      */ 
/*  418 */   public static final COSName DEVICEGRAY = new COSName("DeviceGray");
/*      */ 
/*  422 */   public static final COSName DEVICEN = new COSName("DeviceN");
/*      */ 
/*  426 */   public static final COSName DEVICERGB = new COSName("DeviceRGB");
/*      */ 
/*  430 */   public static final COSName DIFFERENCES = new COSName("Differences");
/*      */ 
/*  434 */   public static final COSName DIGEST_METHOD = new COSName("DigestMethod");
/*      */ 
/*  438 */   public static final COSName DIGEST_SHA1 = new COSName("SHA1");
/*      */ 
/*  443 */   public static final COSName DIGEST_SHA256 = new COSName("SHA256");
/*      */ 
/*  448 */   public static final COSName DIGEST_SHA384 = new COSName("SHA384");
/*      */ 
/*  453 */   public static final COSName DIGEST_SHA512 = new COSName("SHA512");
/*      */ 
/*  458 */   public static final COSName DIGEST_RIPEMD160 = new COSName("RIPEMD160");
/*      */ 
/*  462 */   public static final COSName DIRECTION = new COSName("Direction");
/*      */ 
/*  466 */   public static final COSName DISPLAY_DOC_TITLE = new COSName("DisplayDocTitle");
/*      */ 
/*  471 */   public static final COSName DL = new COSName("DL");
/*      */ 
/*  476 */   public static final COSName DOC_CHECKSUM = new COSName("DocChecksum");
/*      */ 
/*  480 */   public static final COSName DOC_TIME_STAMP = new COSName("DocTimeStamp");
/*      */ 
/*  485 */   public static final COSName DOMAIN = new COSName("Domain");
/*      */ 
/*  489 */   public static final COSName DOS = new COSName("DOS");
/*      */ 
/*  493 */   public static final COSName DP = new COSName("DP");
/*      */ 
/*  498 */   public static final COSName DR = new COSName("DR");
/*      */ 
/*  502 */   public static final COSName DUPLEX = new COSName("Duplex");
/*      */ 
/*  506 */   public static final COSName DV = new COSName("DV");
/*      */ 
/*  510 */   public static final COSName DW = new COSName("DW");
/*      */ 
/*  515 */   public static final COSName E = new COSName("E");
/*      */ 
/*  519 */   public static final COSName EF = new COSName("EF");
/*      */ 
/*  524 */   public static final COSName EARLY_CHANGE = new COSName("EarlyChange");
/*      */ 
/*  529 */   public static final COSName EMBEDDED_FILES = new COSName("EmbeddedFiles");
/*      */ 
/*  534 */   public static final COSName EMBEDDED_FDFS = new COSName("EmbeddedFDFs");
/*      */ 
/*  539 */   public static final COSName ENCODE = new COSName("Encode");
/*      */ 
/*  543 */   public static final COSName ENCODED_BYTE_ALIGN = new COSName("EncodedByteAlign");
/*      */ 
/*  547 */   public static final COSName ENCODING = new COSName("Encoding");
/*      */ 
/*  551 */   public static final COSName ENCODING_90MS_RKSJ_H = new COSName("90ms-RKSJ-H");
/*      */ 
/*  555 */   public static final COSName ENCODING_90MS_RKSJ_V = new COSName("90ms-RKSJ-V");
/*      */ 
/*  559 */   public static final COSName ENCODING_ETEN_B5_H = new COSName("ETen?B5?H");
/*      */ 
/*  563 */   public static final COSName ENCODING_ETEN_B5_V = new COSName("ETen?B5?V");
/*      */ 
/*  568 */   public static final COSName ENCRYPT = new COSName("Encrypt");
/*      */ 
/*  573 */   public static final COSName ENCRYPT_META_DATA = new COSName("EncryptMetadata");
/*      */ 
/*  578 */   public static final COSName END_OF_LINE = new COSName("EndOfLine");
/*      */ 
/*  583 */   public static final COSName EXT_G_STATE = new COSName("ExtGState");
/*      */ 
/*  588 */   public static final COSName EXTEND = new COSName("Extend");
/*      */ 
/*  593 */   public static final COSName EXTENDS = new COSName("Extends");
/*      */ 
/*  598 */   public static final COSName F = new COSName("F");
/*      */ 
/*  603 */   public static final COSName F_DECODE_PARMS = new COSName("FDecodeParms");
/*      */ 
/*  608 */   public static final COSName F_FILTER = new COSName("FFilter");
/*      */ 
/*  613 */   public static final COSName FF = new COSName("Ff");
/*      */ 
/*  617 */   public static final COSName FIELDS = new COSName("Fields");
/*      */ 
/*  621 */   public static final COSName FILESPEC = new COSName("Filespec");
/*      */ 
/*  625 */   public static final COSName FILTER = new COSName("Filter");
/*      */ 
/*  629 */   public static final COSName FIRST = new COSName("First");
/*      */ 
/*  633 */   public static final COSName FIRST_CHAR = new COSName("FirstChar");
/*      */ 
/*  637 */   public static final COSName FIT_WINDOW = new COSName("FitWindow");
/*      */ 
/*  641 */   public static final COSName FL = new COSName("FL");
/*      */ 
/*  645 */   public static final COSName FLAGS = new COSName("Flags");
/*      */ 
/*  649 */   public static final COSName FLATE_DECODE = new COSName("FlateDecode");
/*      */ 
/*  653 */   public static final COSName FLATE_DECODE_ABBREVIATION = new COSName("Fl");
/*      */ 
/*  657 */   public static final COSName FONT = new COSName("Font");
/*      */ 
/*  661 */   public static final COSName FONT_BBOX = new COSName("FontBBox");
/*      */ 
/*  665 */   public static final COSName FONT_FAMILY = new COSName("FontFamily");
/*      */ 
/*  669 */   public static final COSName FONT_FILE = new COSName("FontFile");
/*      */ 
/*  673 */   public static final COSName FONT_FILE2 = new COSName("FontFile2");
/*      */ 
/*  677 */   public static final COSName FONT_FILE3 = new COSName("FontFile3");
/*      */ 
/*  681 */   public static final COSName FONT_DESC = new COSName("FontDescriptor");
/*      */ 
/*  685 */   public static final COSName FONT_MATRIX = new COSName("FontMatrix");
/*      */ 
/*  689 */   public static final COSName FONT_NAME = new COSName("FontName");
/*      */ 
/*  693 */   public static final COSName FONT_STRETCH = new COSName("FontStretch");
/*      */ 
/*  697 */   public static final COSName FONT_WEIGHT = new COSName("FontWeight");
/*      */ 
/*  701 */   public static final COSName FORM = new COSName("Form");
/*      */ 
/*  705 */   public static final COSName FORMTYPE = new COSName("FormType");
/*      */ 
/*  709 */   public static final COSName FRM = new COSName("FRM");
/*      */ 
/*  713 */   public static final COSName FT = new COSName("FT");
/*      */ 
/*  717 */   public static final COSName FUNCTION = new COSName("Function");
/*      */ 
/*  721 */   public static final COSName FUNCTION_TYPE = new COSName("FunctionType");
/*      */ 
/*  725 */   public static final COSName FUNCTIONS = new COSName("Functions");
/*      */ 
/*  729 */   public static final COSName GAMMA = new COSName("Gamma");
/*      */ 
/*  733 */   public static final COSName H = new COSName("H");
/*      */ 
/*  737 */   public static final COSName GTS_PDFA1 = new COSName("GTS_PDFA1");
/*      */ 
/*  741 */   public static final COSName HEIGHT = new COSName("Height");
/*      */ 
/*  745 */   public static final COSName HIDE_MENUBAR = new COSName("HideMenubar");
/*      */ 
/*  749 */   public static final COSName HIDE_TOOLBAR = new COSName("HideToolbar");
/*      */ 
/*  753 */   public static final COSName HIDE_WINDOWUI = new COSName("HideWindowUI");
/*      */ 
/*  757 */   public static final COSName ICCBASED = new COSName("ICCBased");
/*      */ 
/*  762 */   public static final COSName I = new COSName("I");
/*      */ 
/*  767 */   public static final COSName ID = new COSName("ID");
/*      */ 
/*  772 */   public static final COSName ID_TREE = new COSName("IDTree");
/*      */ 
/*  777 */   public static final COSName IDENTITY = new COSName("Identity");
/*      */ 
/*  781 */   public static final COSName IDENTITY_H = new COSName("Identity-H");
/*      */ 
/*  785 */   public static final COSName IM = new COSName("IM");
/*      */ 
/*  789 */   public static final COSName IMAGE = new COSName("Image");
/*      */ 
/*  793 */   public static final COSName IMAGE_MASK = new COSName("ImageMask");
/*      */ 
/*  798 */   public static final COSName INDEX = new COSName("Index");
/*      */ 
/*  803 */   public static final COSName INDEXED = new COSName("Indexed");
/*      */ 
/*  807 */   public static final COSName INFO = new COSName("Info");
/*      */ 
/*  811 */   public static final COSName ITALIC_ANGLE = new COSName("ItalicAngle");
/*      */ 
/*  816 */   public static final COSName JAVA_SCRIPT = new COSName("JavaScript");
/*      */ 
/*  821 */   public static final COSName JBIG2_DECODE = new COSName("JBIG2Decode");
/*      */ 
/*  825 */   public static final COSName JBIG2_GLOBALS = new COSName("JBIG2Globals");
/*      */ 
/*  829 */   public static final COSName JPX_DECODE = new COSName("JPXDecode");
/*      */ 
/*  834 */   public static final COSName K = new COSName("K");
/*      */ 
/*  839 */   public static final COSName KEYWORDS = new COSName("Keywords");
/*      */ 
/*  844 */   public static final COSName KIDS = new COSName("Kids");
/*      */ 
/*  849 */   public static final COSName LAB = new COSName("Lab");
/*      */ 
/*  854 */   public static final COSName LANG = new COSName("Lang");
/*      */ 
/*  859 */   public static final COSName LAST_CHAR = new COSName("LastChar");
/*      */ 
/*  863 */   public static final COSName LAST_MODIFIED = new COSName("LastModified");
/*      */ 
/*  867 */   public static final COSName LC = new COSName("LC");
/*      */ 
/*  871 */   public static final COSName L = new COSName("L");
/*      */ 
/*  875 */   public static final COSName LEADING = new COSName("Leading");
/*      */ 
/*  879 */   public static final COSName LEGAL_ATTESTATION = new COSName("LegalAttestation");
/*      */ 
/*  883 */   public static final COSName LENGTH = new COSName("Length");
/*      */ 
/*  887 */   public static final COSName LENGTH1 = new COSName("Length1");
/*      */ 
/*  889 */   public static final COSName LENGTH2 = new COSName("Length2");
/*      */ 
/*  893 */   public static final COSName LIMITS = new COSName("Limits");
/*      */ 
/*  897 */   public static final COSName LJ = new COSName("LJ");
/*      */ 
/*  901 */   public static final COSName LW = new COSName("LW");
/*      */ 
/*  905 */   public static final COSName LZW_DECODE = new COSName("LZWDecode");
/*      */ 
/*  909 */   public static final COSName LZW_DECODE_ABBREVIATION = new COSName("LZW");
/*      */ 
/*  913 */   public static final COSName M = new COSName("M");
/*      */ 
/*  917 */   public static final COSName MAC = new COSName("Mac");
/*      */ 
/*  921 */   public static final COSName MAC_ROMAN_ENCODING = new COSName("MacRomanEncoding");
/*      */ 
/*  926 */   public static final COSName MARK_INFO = new COSName("MarkInfo");
/*      */ 
/*  931 */   public static final COSName MASK = new COSName("Mask");
/*      */ 
/*  935 */   public static final COSName MATRIX = new COSName("Matrix");
/*      */ 
/*  939 */   public static final COSName MAX_LEN = new COSName("MaxLen");
/*      */ 
/*  943 */   public static final COSName MAX_WIDTH = new COSName("MaxWidth");
/*      */ 
/*  947 */   public static final COSName MCID = new COSName("MCID");
/*      */ 
/*  951 */   public static final COSName MDP = new COSName("MDP");
/*      */ 
/*  955 */   public static final COSName MEDIA_BOX = new COSName("MediaBox");
/*      */ 
/*  959 */   public static final COSName METADATA = new COSName("Metadata");
/*      */ 
/*  963 */   public static final COSName MISSING_WIDTH = new COSName("MissingWidth");
/*      */ 
/*  967 */   public static final COSName ML = new COSName("ML");
/*      */ 
/*  971 */   public static final COSName MM_TYPE1 = new COSName("MMType1");
/*      */ 
/*  975 */   public static final COSName MOD_DATE = new COSName("ModDate");
/*      */ 
/*  979 */   public static final COSName N = new COSName("N");
/*      */ 
/*  983 */   public static final COSName NAME = new COSName("Name");
/*      */ 
/*  988 */   public static final COSName NAMES = new COSName("Names");
/*      */ 
/*  993 */   public static final COSName NEXT = new COSName("Next");
/*      */ 
/*  997 */   public static final COSName NM = new COSName("NM");
/*      */ 
/* 1001 */   public static final COSName NON_EFONT_NO_WARN = new COSName("NonEFontNoWarn");
/*      */ 
/* 1005 */   public static final COSName NON_FULL_SCREEN_PAGE_MODE = new COSName("NonFullScreenPageMode");
/*      */ 
/* 1009 */   public static final COSName NUMS = new COSName("Nums");
/*      */ 
/* 1014 */   public static final COSName O = new COSName("O");
/*      */ 
/* 1018 */   public static final COSName OBJ = new COSName("Obj");
/*      */ 
/* 1023 */   public static final COSName OBJ_STM = new COSName("ObjStm");
/*      */ 
/* 1026 */   public static final COSName OC = new COSName("OC");
/*      */ 
/* 1028 */   public static final COSName OCG = new COSName("OCG");
/*      */ 
/* 1030 */   public static final COSName OCGS = new COSName("OCGs");
/*      */ 
/* 1032 */   public static final COSName OCPROPERTIES = new COSName("OCProperties");
/*      */ 
/* 1035 */   public static final COSName OFF = new COSName("OFF");
/*      */ 
/* 1037 */   public static final COSName ON = new COSName("ON");
/*      */ 
/* 1042 */   public static final COSName OP = new COSName("OP");
/*      */ 
/* 1046 */   public static final COSName OP_NS = new COSName("op");
/*      */ 
/* 1050 */   public static final COSName OPM = new COSName("OPM");
/*      */ 
/* 1054 */   public static final COSName OPT = new COSName("Opt");
/*      */ 
/* 1058 */   public static final COSName OS = new COSName("OS");
/*      */ 
/* 1062 */   public static final COSName OUTLINES = new COSName("Outlines");
/*      */ 
/* 1067 */   public static final COSName OUTPUT_INTENT = new COSName("OutputIntent");
/*      */ 
/* 1072 */   public static final COSName OUTPUT_INTENTS = new COSName("OutputIntents");
/*      */ 
/* 1077 */   public static final COSName OUTPUT_CONDITION = new COSName("OutputCondition");
/*      */ 
/* 1082 */   public static final COSName OUTPUT_CONDITION_IDENTIFIER = new COSName("OutputConditionIdentifier");
/*      */ 
/* 1087 */   public static final COSName OPEN_ACTION = new COSName("OpenAction");
/*      */ 
/* 1090 */   public static final COSName ORDER = new COSName("Order");
/*      */ 
/* 1095 */   public static final COSName ORDERING = new COSName("Ordering");
/*      */ 
/* 1099 */   public static final COSName P = new COSName("P");
/*      */ 
/* 1103 */   public static final COSName PAGE = new COSName("Page");
/*      */ 
/* 1108 */   public static final COSName PAGE_LABELS = new COSName("PageLabels");
/*      */ 
/* 1113 */   public static final COSName PAGE_LAYOUT = new COSName("PageLayout");
/*      */ 
/* 1118 */   public static final COSName PAGE_MODE = new COSName("PageMode");
/*      */ 
/* 1123 */   public static final COSName PAGES = new COSName("Pages");
/*      */ 
/* 1127 */   public static final COSName PAINT_TYPE = new COSName("PaintType");
/*      */ 
/* 1131 */   public static final COSName PARENT = new COSName("Parent");
/*      */ 
/* 1135 */   public static final COSName PARENT_TREE = new COSName("ParentTree");
/*      */ 
/* 1139 */   public static final COSName PARENT_TREE_NEXT_KEY = new COSName("ParentTreeNextKey");
/*      */ 
/* 1143 */   public static final COSName PATTERN = new COSName("Pattern");
/*      */ 
/* 1147 */   public static final COSName PATTERN_TYPE = new COSName("PatternType");
/*      */ 
/* 1151 */   public static final COSName PDF_DOC_ENCODING = new COSName("PDFDocEncoding");
/*      */ 
/* 1155 */   public static final COSName PG = new COSName("Pg");
/*      */ 
/* 1159 */   public static final COSName PRE_RELEASE = new COSName("PreRelease");
/*      */ 
/* 1163 */   public static final COSName PREDICTOR = new COSName("Predictor");
/*      */ 
/* 1167 */   public static final COSName PREV = new COSName("Prev");
/*      */ 
/* 1172 */   public static final COSName PRINT_AREA = new COSName("PrintArea");
/*      */ 
/* 1176 */   public static final COSName PRINT_CLIP = new COSName("PrintClip");
/*      */ 
/* 1180 */   public static final COSName PRINT_SCALING = new COSName("PrintScaling");
/*      */ 
/* 1183 */   public static final COSName PROC_SET = new COSName("ProcSet");
/*      */ 
/* 1188 */   public static final COSName PRODUCER = new COSName("Producer");
/*      */ 
/* 1193 */   public static final COSName PROP_BUILD = new COSName("Prop_Build");
/*      */ 
/* 1195 */   public static final COSName PROPERTIES = new COSName("Properties");
/*      */ 
/* 1200 */   public static final COSName PUB_SEC = new COSName("PubSec");
/*      */ 
/* 1204 */   public static final COSName Q = new COSName("Q");
/*      */ 
/* 1208 */   public static final COSName R = new COSName("R");
/*      */ 
/* 1212 */   public static final COSName RANGE = new COSName("Range");
/*      */ 
/* 1216 */   public static final COSName REASONS = new COSName("Reasons");
/*      */ 
/* 1220 */   public static final COSName RECIPIENTS = new COSName("Recipients");
/*      */ 
/* 1224 */   public static final COSName RECT = new COSName("Rect");
/*      */ 
/* 1228 */   public static final COSName REGISTRY = new COSName("Registry");
/*      */ 
/* 1233 */   public static final COSName REGISTRY_NAME = new COSName("RegistryName");
/*      */ 
/* 1238 */   public static final COSName RESOURCES = new COSName("Resources");
/*      */ 
/* 1242 */   public static final COSName RI = new COSName("RI");
/*      */ 
/* 1246 */   public static final COSName ROLE_MAP = new COSName("RoleMap");
/*      */ 
/* 1250 */   public static final COSName ROOT = new COSName("Root");
/*      */ 
/* 1254 */   public static final COSName ROTATE = new COSName("Rotate");
/*      */ 
/* 1258 */   public static final COSName ROWS = new COSName("Rows");
/*      */ 
/* 1262 */   public static final COSName RUN_LENGTH_DECODE = new COSName("RunLengthDecode");
/*      */ 
/* 1266 */   public static final COSName RUN_LENGTH_DECODE_ABBREVIATION = new COSName("RL");
/*      */ 
/* 1270 */   public static final COSName RV = new COSName("RV");
/*      */ 
/* 1274 */   public static final COSName S = new COSName("S");
/*      */ 
/* 1278 */   public static final COSName SA = new COSName("SA");
/*      */ 
/* 1282 */   public static final COSName SE = new COSName("SE");
/*      */ 
/* 1286 */   public static final COSName SEPARATION = new COSName("Separation");
/*      */ 
/* 1290 */   public static final COSName SET_F = new COSName("SetF");
/*      */ 
/* 1294 */   public static final COSName SET_FF = new COSName("SetFf");
/*      */ 
/* 1299 */   public static final COSName SHADING = new COSName("Shading");
/*      */ 
/* 1303 */   public static final COSName SHADING_TYPE = new COSName("ShadingType");
/*      */ 
/* 1307 */   public static final COSName SM = new COSName("SM");
/*      */ 
/* 1311 */   public static final COSName SMASK = new COSName("SMask");
/*      */ 
/* 1315 */   public static final COSName SIZE = new COSName("Size");
/*      */ 
/* 1320 */   public static final COSName STANDARD_ENCODING = new COSName("StandardEncoding");
/*      */ 
/* 1324 */   public static final COSName STATUS = new COSName("Status");
/*      */ 
/* 1328 */   public static final COSName STD_CF = new COSName("StdCF");
/*      */ 
/* 1332 */   public static final COSName STEM_H = new COSName("StemH");
/*      */ 
/* 1336 */   public static final COSName STEM_V = new COSName("StemV");
/*      */ 
/* 1340 */   public static final COSName STM_F = new COSName("StmF");
/*      */ 
/* 1344 */   public static final COSName STR_F = new COSName("StrF");
/*      */ 
/* 1348 */   public static final COSName STRUCT_PARENT = new COSName("StructParent");
/*      */ 
/* 1352 */   public static final COSName STRUCT_PARENTS = new COSName("StructParents");
/*      */ 
/* 1356 */   public static final COSName STRUCT_TREE_ROOT = new COSName("StructTreeRoot");
/*      */ 
/* 1361 */   public static final COSName SUB_FILTER = new COSName("SubFilter");
/*      */ 
/* 1365 */   public static final COSName SUBJ = new COSName("Subj");
/*      */ 
/* 1369 */   public static final COSName SUBJECT = new COSName("Subject");
/*      */ 
/* 1373 */   public static final COSName SUPPLEMENT = new COSName("Supplement");
/*      */ 
/* 1377 */   public static final COSName SUBTYPE = new COSName("Subtype");
/*      */ 
/* 1381 */   public static final COSName SV = new COSName("SV");
/*      */ 
/* 1386 */   public static final COSName T = new COSName("T");
/*      */ 
/* 1391 */   public static final COSName TARGET = new COSName("Target");
/*      */ 
/* 1396 */   public static final COSName THREADS = new COSName("Threads");
/*      */ 
/* 1401 */   public static final COSName TILING_TYPE = new COSName("TilingType");
/*      */ 
/* 1405 */   public static final COSName TIME_STAMP = new COSName("TimeStamp");
/*      */ 
/* 1409 */   public static final COSName TITLE = new COSName("Title");
/*      */ 
/* 1413 */   public static final COSName TK = new COSName("TK");
/*      */ 
/* 1417 */   public static final COSName TRAPPED = new COSName("Trapped");
/*      */ 
/* 1421 */   public static final COSName TRIM_BOX = new COSName("TrimBox");
/*      */ 
/* 1425 */   public static final COSName TRUE_TYPE = new COSName("TrueType");
/*      */ 
/* 1429 */   public static final COSName TRUSTED_MODE = new COSName("TrustedMode");
/*      */ 
/* 1433 */   public static final COSName TO_UNICODE = new COSName("ToUnicode");
/*      */ 
/* 1437 */   public static final COSName TU = new COSName("TU");
/*      */ 
/* 1441 */   public static final COSName TYPE = new COSName("Type");
/*      */ 
/* 1445 */   public static final COSName TYPE0 = new COSName("Type0");
/*      */ 
/* 1449 */   public static final COSName TYPE1 = new COSName("Type1");
/*      */ 
/* 1453 */   public static final COSName TYPE3 = new COSName("Type3");
/*      */ 
/* 1458 */   public static final COSName U = new COSName("U");
/*      */ 
/* 1462 */   public static final COSName UF = new COSName("UF");
/*      */ 
/* 1464 */   public static final COSName UNCHANGED = new COSName("Unchanged");
/*      */ 
/* 1468 */   public static final COSName UNIX = new COSName("Unix");
/*      */ 
/* 1472 */   public static final COSName URI = new COSName("URI");
/*      */ 
/* 1476 */   public static final COSName URL = new COSName("URL");
/*      */ 
/* 1481 */   public static final COSName V = new COSName("V");
/*      */ 
/* 1485 */   public static final COSName VERSION = new COSName("Version");
/*      */ 
/* 1489 */   public static final COSName VERTICES_PER_ROW = new COSName("VerticesPerRow");
/*      */ 
/* 1494 */   public static final COSName VIEW_AREA = new COSName("ViewArea");
/*      */ 
/* 1498 */   public static final COSName VIEW_CLIP = new COSName("ViewClip");
/*      */ 
/* 1502 */   public static final COSName VIEWER_PREFERENCES = new COSName("ViewerPreferences");
/*      */ 
/* 1507 */   public static final COSName W = new COSName("W");
/*      */ 
/* 1511 */   public static final COSName WIDTH = new COSName("Width");
/*      */ 
/* 1515 */   public static final COSName WIDTHS = new COSName("Widths");
/*      */ 
/* 1519 */   public static final COSName WIN_ANSI_ENCODING = new COSName("WinAnsiEncoding");
/*      */ 
/* 1523 */   public static final COSName WHITE_POINT = new COSName("WhitePoint");
/*      */ 
/* 1528 */   public static final COSName XHEIGHT = new COSName("XHeight");
/*      */ 
/* 1533 */   public static final COSName XOBJECT = new COSName("XObject");
/*      */ 
/* 1537 */   public static final COSName XREF = new COSName("XRef");
/*      */ 
/* 1542 */   public static final COSName XREF_STM = new COSName("XRefStm");
/*      */ 
/* 1546 */   public static final COSName X_STEP = new COSName("XStep");
/*      */ 
/* 1550 */   public static final COSName Y_STEP = new COSName("YStep");
/*      */ 
/* 1554 */   public static final byte[] NAME_PREFIX = { 47 };
/*      */ 
/* 1558 */   public static final byte[] NAME_ESCAPE = { 35 };
/*      */ 
/* 1563 */   public static final COSName SUBFILTER = new COSName("SubFilter");
/*      */ 
/* 1567 */   public static final COSName ADOBE_PPKLITE = new COSName("Adobe.PPKLite");
/*      */ 
/* 1571 */   public static final COSName ENTRUST_PPKEF = new COSName("Entrust.PPKEF");
/*      */ 
/* 1575 */   public static final COSName CICI_SIGNIT = new COSName("CICI.SignIt");
/*      */ 
/* 1579 */   public static final COSName VERISIGN_PPKVS = new COSName("VeriSign.PPKVS");
/*      */ 
/* 1583 */   public static final COSName ADBE_X509_RSA_SHA1 = new COSName("adbe.x509.rsa_sha1");
/*      */ 
/* 1587 */   public static final COSName ADBE_PKCS7_DETACHED = new COSName("adbe.pkcs7.detached");
/*      */ 
/* 1591 */   public static final COSName ADBE_PKCS7_SHA1 = new COSName("adbe.pkcs7.sha1");
/*      */ 
/* 1595 */   public static final COSName LOCATION = new COSName("Location");
/*      */ 
/* 1599 */   public static final COSName REASON = new COSName("Reason");
/*      */ 
/* 1603 */   public static final COSName BYTERANGE = new COSName("ByteRange");
/*      */ 
/* 1607 */   public static final COSName SIG = new COSName("Sig");
/*      */ 
/* 1611 */   public static final COSName SIG_FLAGS = new COSName("SigFlags");
/*      */   private String name;
/*      */   private int hashCode;
/*      */ 
/*      */   public static final COSName getPDFName(String aName)
/*      */   {
/* 1626 */     COSName name = null;
/* 1627 */     if (aName != null)
/*      */     {
/* 1630 */       name = (COSName)commonNameMap.get(aName);
/* 1631 */       if (name == null)
/*      */       {
/* 1634 */         name = (COSName)nameMap.get(aName);
/* 1635 */         if (name == null)
/*      */         {
/* 1638 */           name = new COSName(aName, false);
/*      */         }
/*      */       }
/*      */     }
/* 1642 */     return name;
/*      */   }
/*      */ 
/*      */   private COSName(String aName, boolean staticValue)
/*      */   {
/* 1655 */     this.name = aName;
/* 1656 */     if (staticValue)
/*      */     {
/* 1658 */       commonNameMap.put(aName, this);
/*      */     }
/*      */     else
/*      */     {
/* 1662 */       nameMap.put(aName, this);
/*      */     }
/* 1664 */     this.hashCode = this.name.hashCode();
/*      */   }
/*      */ 
/*      */   private COSName(String aName)
/*      */   {
/* 1675 */     this(aName, true);
/*      */   }
/*      */ 
/*      */   public String getName()
/*      */   {
/* 1685 */     return this.name;
/*      */   }
/*      */ 
/*      */   public String toString()
/*      */   {
/* 1693 */     return "COSName{" + this.name + "}";
/*      */   }
/*      */ 
/*      */   public boolean equals(Object o)
/*      */   {
/* 1701 */     boolean retval = this == o;
/* 1702 */     if ((!retval) && ((o instanceof COSName)))
/*      */     {
/* 1704 */       COSName other = (COSName)o;
/* 1705 */       retval = (this.name == other.name) || (this.name.equals(other.name));
/*      */     }
/* 1707 */     return retval;
/*      */   }
/*      */ 
/*      */   public int hashCode()
/*      */   {
/* 1715 */     return this.hashCode;
/*      */   }
/*      */ 
/*      */   public int compareTo(COSName other)
/*      */   {
/* 1723 */     return this.name.compareTo(other.name);
/*      */   }
/*      */ 
/*      */   public Object accept(ICOSVisitor visitor)
/*      */     throws COSVisitorException
/*      */   {
/* 1737 */     return visitor.visitFromName(this);
/*      */   }
/*      */ 
/*      */   public void writePDF(OutputStream output)
/*      */     throws IOException
/*      */   {
/* 1748 */     output.write(NAME_PREFIX);
/* 1749 */     byte[] bytes = getName().getBytes("ISO-8859-1");
/* 1750 */     for (int i = 0; i < bytes.length; i++)
/*      */     {
/* 1752 */       int current = (bytes[i] + 256) % 256;
/*      */ 
/* 1756 */       if (((current >= 65) && (current <= 90)) || ((current >= 97) && (current <= 122)) || ((current >= 48) && (current <= 57)) || (current == 43) || (current == 45) || (current == 95) || (current == 64) || (current == 42) || (current == 36) || (current == 59) || (current == 46))
/*      */       {
/* 1768 */         output.write(current);
/*      */       }
/*      */       else
/*      */       {
/* 1772 */         output.write(NAME_ESCAPE);
/* 1773 */         output.write(org.apache.pdfbox.persistence.util.COSHEXTable.TABLE[current]);
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   public static synchronized void clearResources()
/*      */   {
/* 1787 */     nameMap.clear();
/*      */   }
/*      */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.cos.COSName
 * JD-Core Version:    0.6.2
 */