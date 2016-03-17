/*      */ package org.apache.pdfbox.pdfparser;
/*      */ 
/*      */ import java.io.ByteArrayInputStream;
/*      */ import java.io.File;
/*      */ import java.io.FileInputStream;
/*      */ import java.io.FileOutputStream;
/*      */ import java.io.IOException;
/*      */ import java.io.InputStream;
/*      */ import java.io.OutputStream;
/*      */ import java.security.KeyStore;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Arrays;
/*      */ import java.util.Collection;
/*      */ import java.util.Collections;
/*      */ import java.util.HashMap;
/*      */ import java.util.HashSet;
/*      */ import java.util.Iterator;
/*      */ import java.util.LinkedList;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.Map.Entry;
/*      */ import java.util.Queue;
/*      */ import java.util.Set;
/*      */ import java.util.TreeMap;
/*      */ import java.util.Vector;
/*      */ import org.apache.commons.logging.Log;
/*      */ import org.apache.commons.logging.LogFactory;
/*      */ import org.apache.pdfbox.cos.COSArray;
/*      */ import org.apache.pdfbox.cos.COSBase;
/*      */ import org.apache.pdfbox.cos.COSDictionary;
/*      */ import org.apache.pdfbox.cos.COSDocument;
/*      */ import org.apache.pdfbox.cos.COSInteger;
/*      */ import org.apache.pdfbox.cos.COSName;
/*      */ import org.apache.pdfbox.cos.COSNull;
/*      */ import org.apache.pdfbox.cos.COSNumber;
/*      */ import org.apache.pdfbox.cos.COSObject;
/*      */ import org.apache.pdfbox.cos.COSStream;
/*      */ import org.apache.pdfbox.cos.COSString;
/*      */ import org.apache.pdfbox.exceptions.CryptographyException;
/*      */ import org.apache.pdfbox.io.IOUtils;
/*      */ import org.apache.pdfbox.io.PushBackInputStream;
/*      */ import org.apache.pdfbox.io.RandomAccess;
/*      */ import org.apache.pdfbox.io.RandomAccessBuffer;
/*      */ import org.apache.pdfbox.io.RandomAccessBufferedFileInputStream;
/*      */ import org.apache.pdfbox.pdmodel.PDDocument;
/*      */ import org.apache.pdfbox.pdmodel.PDPage;
/*      */ import org.apache.pdfbox.pdmodel.encryption.AccessPermission;
/*      */ import org.apache.pdfbox.pdmodel.encryption.DecryptionMaterial;
/*      */ import org.apache.pdfbox.pdmodel.encryption.PDEncryptionDictionary;
/*      */ import org.apache.pdfbox.pdmodel.encryption.PublicKeyDecryptionMaterial;
/*      */ import org.apache.pdfbox.pdmodel.encryption.SecurityHandler;
/*      */ import org.apache.pdfbox.pdmodel.encryption.SecurityHandlersManager;
/*      */ import org.apache.pdfbox.pdmodel.encryption.StandardDecryptionMaterial;
/*      */ import org.apache.pdfbox.persistence.util.COSObjectKey;
/*      */ 
/*      */ public class NonSequentialPDFParser extends PDFParser
/*      */ {
/*   88 */   private static final byte[] XREF_TABLE = { 120, 114, 101, 102 };
/*   89 */   private static final byte[] XREF_STREAM = { 47, 88, 82, 101, 102 };
/*      */   private static final long MINIMUM_SEARCH_OFFSET = 6L;
/*      */   private static final int X = 120;
/*      */   public static final String SYSPROP_PARSEMINIMAL = "org.apache.pdfbox.pdfparser.nonSequentialPDFParser.parseMinimal";
/*      */   public static final String SYSPROP_EOFLOOKUPRANGE = "org.apache.pdfbox.pdfparser.nonSequentialPDFParser.eofLookupRange";
/*   97 */   private static final InputStream EMPTY_INPUT_STREAM = new ByteArrayInputStream(new byte[0]);
/*      */   protected static final int DEFAULT_TRAIL_BYTECOUNT = 2048;
/*  103 */   protected static final char[] EOF_MARKER = { '%', '%', 'E', 'O', 'F' };
/*      */ 
/*  107 */   protected static final char[] STARTXREF_MARKER = { 's', 't', 'a', 'r', 't', 'x', 'r', 'e', 'f' };
/*      */ 
/*  111 */   protected static final char[] OBJ_MARKER = { 'o', 'b', 'j' };
/*      */ 
/*  116 */   private static final char[] TRAILER_MARKER = { 't', 'r', 'a', 'i', 'l', 'e', 'r' };
/*      */   private long trailerOffset;
/*      */   private final File pdfFile;
/*      */   private long fileLen;
/*      */   private final RandomAccessBufferedFileInputStream raStream;
/*  126 */   private boolean isLenient = true;
/*      */ 
/*  131 */   private HashMap<String, Long> bfSearchObjectOffsets = null;
/*  132 */   private HashMap<COSObjectKey, Long> bfSearchCOSObjectKeyOffsets = null;
/*  133 */   private Vector<Long> bfSearchXRefOffsets = null;
/*      */ 
/*  138 */   protected SecurityHandler securityHandler = null;
/*      */ 
/*  140 */   private String keyStoreFilename = null;
/*  141 */   private String alias = null;
/*  142 */   private String password = "";
/*  143 */   private int readTrailBytes = 2048;
/*      */ 
/*  154 */   private boolean parseMinimalCatalog = "true".equals(System.getProperty("org.apache.pdfbox.pdfparser.nonSequentialPDFParser.parseMinimal"));
/*      */ 
/*  156 */   private boolean initialParseDone = false;
/*  157 */   private boolean allPagesParsed = false;
/*      */ 
/*  159 */   private static final Log LOG = LogFactory.getLog(NonSequentialPDFParser.class);
/*      */ 
/*  166 */   private boolean isTmpPDFFile = false;
/*      */   public static final String TMP_FILE_PREFIX = "tmpPDF";
/*  836 */   private COSDictionary pagesDictionary = null;
/*      */ 
/* 1611 */   private boolean inGetLength = false;
/*      */ 
/* 1683 */   private final int streamCopyBufLen = 8192;
/* 1684 */   private final byte[] streamCopyBuf = new byte[8192];
/*      */ 
/*      */   public NonSequentialPDFParser(String filename)
/*      */     throws IOException
/*      */   {
/*  180 */     this(new File(filename), null);
/*      */   }
/*      */ 
/*      */   public NonSequentialPDFParser(File file, RandomAccess raBuf)
/*      */     throws IOException
/*      */   {
/*  203 */     this(file, raBuf, "");
/*      */   }
/*      */ 
/*      */   public NonSequentialPDFParser(File file, RandomAccess raBuf, String decryptionPassword)
/*      */     throws IOException
/*      */   {
/*  227 */     super(EMPTY_INPUT_STREAM, null, false);
/*  228 */     this.pdfFile = file;
/*  229 */     this.raStream = new RandomAccessBufferedFileInputStream(this.pdfFile);
/*  230 */     init(file, raBuf, decryptionPassword);
/*      */   }
/*      */ 
/*      */   private void init(File file, RandomAccess raBuf, String decryptionPassword) throws IOException
/*      */   {
/*  235 */     String eofLookupRangeStr = System.getProperty("org.apache.pdfbox.pdfparser.nonSequentialPDFParser.eofLookupRange");
/*  236 */     if (eofLookupRangeStr != null)
/*      */     {
/*      */       try
/*      */       {
/*  240 */         setEOFLookupRange(Integer.parseInt(eofLookupRangeStr));
/*      */       }
/*      */       catch (NumberFormatException nfe)
/*      */       {
/*  244 */         LOG.warn("System property org.apache.pdfbox.pdfparser.nonSequentialPDFParser.eofLookupRange does not contain an integer value, but: '" + eofLookupRangeStr + "'");
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/*  249 */     setDocument(raBuf == null ? new COSDocument(new RandomAccessBuffer(), false) : new COSDocument(raBuf, false));
/*      */ 
/*  251 */     this.pdfSource = new PushBackInputStream(this.raStream, 4096);
/*      */ 
/*  253 */     this.password = decryptionPassword;
/*      */   }
/*      */ 
/*      */   public NonSequentialPDFParser(InputStream input)
/*      */     throws IOException
/*      */   {
/*  264 */     this(input, null, "");
/*      */   }
/*      */ 
/*      */   public NonSequentialPDFParser(InputStream input, RandomAccess raBuf, String decryptionPassword)
/*      */     throws IOException
/*      */   {
/*  277 */     super(EMPTY_INPUT_STREAM, null, false);
/*  278 */     this.pdfFile = createTmpFile(input);
/*  279 */     this.raStream = new RandomAccessBufferedFileInputStream(this.pdfFile);
/*  280 */     init(this.pdfFile, raBuf, decryptionPassword);
/*      */   }
/*      */ 
/*      */   private File createTmpFile(InputStream input)
/*      */     throws IOException
/*      */   {
/*  294 */     File tmpFile = null;
/*  295 */     FileOutputStream fos = null;
/*      */     try
/*      */     {
/*  298 */       tmpFile = File.createTempFile("tmpPDF", ".pdf");
/*  299 */       fos = new FileOutputStream(tmpFile);
/*  300 */       IOUtils.copy(input, fos);
/*  301 */       this.isTmpPDFFile = true;
/*  302 */       return tmpFile;
/*      */     }
/*      */     finally
/*      */     {
/*  306 */       IOUtils.closeQuietly(input);
/*  307 */       IOUtils.closeQuietly(fos);
/*      */     }
/*      */   }
/*      */ 
/*      */   public void setEOFLookupRange(int byteCount)
/*      */   {
/*  330 */     if (byteCount > 15)
/*      */     {
/*  332 */       this.readTrailBytes = byteCount;
/*      */     }
/*      */   }
/*      */ 
/*      */   protected void initialParse()
/*      */     throws IOException
/*      */   {
/*  346 */     COSDictionary trailer = null;
/*      */ 
/*  348 */     long startXRefOffset = getStartxrefOffset();
/*  349 */     if (startXRefOffset > 0L)
/*      */     {
/*  351 */       trailer = parseXref(startXRefOffset);
/*      */     }
/*  353 */     else if ((this.isFDFDocment) || (this.isLenient))
/*      */     {
/*  356 */       this.xrefTrailerResolver.nextXrefObj(startXRefOffset);
/*  357 */       bfSearchForObjects();
/*  358 */       for (COSObjectKey objectKey : this.bfSearchCOSObjectKeyOffsets.keySet())
/*      */       {
/*  360 */         this.xrefTrailerResolver.setXRef(objectKey, ((Long)this.bfSearchCOSObjectKeyOffsets.get(objectKey)).longValue());
/*      */       }
/*      */ 
/*  363 */       this.pdfSource.seek(this.trailerOffset);
/*  364 */       if (!parseTrailer())
/*      */       {
/*  366 */         throw new IOException("Expected trailer object at position: " + this.pdfSource.getOffset());
/*      */       }
/*      */ 
/*  369 */       this.xrefTrailerResolver.setStartxref(startXRefOffset);
/*  370 */       trailer = this.xrefTrailerResolver.getCurrentTrailer();
/*  371 */       this.document.setTrailer(trailer);
/*      */     }
/*      */ 
/*  374 */     prepareDecryption();
/*      */ 
/*  378 */     for (COSBase trailerEntry : trailer.getValues())
/*      */     {
/*  380 */       if ((trailerEntry instanceof COSObject))
/*      */       {
/*  382 */         COSObject tmpObj = (COSObject)trailerEntry;
/*  383 */         parseObjectDynamically(tmpObj, false);
/*      */       }
/*      */     }
/*      */ 
/*  387 */     COSObject root = (COSObject)this.xrefTrailerResolver.getTrailer().getItem(COSName.ROOT);
/*      */ 
/*  389 */     if (root == null)
/*      */     {
/*  391 */       throw new IOException("Missing root object specification in trailer.");
/*      */     }
/*      */ 
/*  394 */     COSBase rootObject = parseObjectDynamically(root, false);
/*      */ 
/*  397 */     if (this.isFDFDocment)
/*      */     {
/*  400 */       if ((rootObject instanceof COSDictionary))
/*      */       {
/*  402 */         parseDictObjects((COSDictionary)rootObject, (COSName[])null);
/*  403 */         this.allPagesParsed = true;
/*  404 */         this.document.setDecrypted();
/*      */       }
/*      */     }
/*  407 */     else if (!this.parseMinimalCatalog)
/*      */     {
/*  409 */       COSObject catalogObj = this.document.getCatalog();
/*  410 */       if (catalogObj != null)
/*      */       {
/*  412 */         if ((catalogObj.getObject() instanceof COSDictionary))
/*      */         {
/*  414 */           parseDictObjects((COSDictionary)catalogObj.getObject(), (COSName[])null);
/*  415 */           this.allPagesParsed = true;
/*  416 */           this.document.setDecrypted();
/*      */         }
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/*  422 */     readVersionInTrailer(trailer);
/*      */ 
/*  424 */     this.initialParseDone = true;
/*      */   }
/*      */ 
/*      */   private void parseDictionaryRecursive(COSObject dictionaryObject)
/*      */     throws IOException
/*      */   {
/*  436 */     parseObjectDynamically(dictionaryObject, true);
/*  437 */     COSDictionary dictionary = (COSDictionary)dictionaryObject.getObject();
/*  438 */     for (COSBase value : dictionary.getValues())
/*      */     {
/*  440 */       if ((value instanceof COSObject))
/*      */       {
/*  442 */         COSObject object = (COSObject)value;
/*  443 */         if (object.getObject() == null)
/*      */         {
/*  445 */           parseDictionaryRecursive(object);
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   private void prepareDecryption()
/*      */     throws IOException
/*      */   {
/*  457 */     COSBase trailerEncryptItem = this.document.getTrailer().getItem(COSName.ENCRYPT);
/*  458 */     if ((trailerEncryptItem != null) && (!(trailerEncryptItem instanceof COSNull)))
/*      */     {
/*  460 */       if ((trailerEncryptItem instanceof COSObject))
/*      */       {
/*  462 */         COSObject trailerEncryptObj = (COSObject)trailerEncryptItem;
/*  463 */         parseDictionaryRecursive(trailerEncryptObj);
/*      */       }
/*      */       try
/*      */       {
/*  467 */         PDEncryptionDictionary encParameters = new PDEncryptionDictionary(this.document.getEncryptionDictionary());
/*      */ 
/*  469 */         DecryptionMaterial decryptionMaterial = null;
/*  470 */         if (this.keyStoreFilename != null)
/*      */         {
/*  472 */           KeyStore ks = KeyStore.getInstance("PKCS12");
/*  473 */           ks.load(new FileInputStream(this.keyStoreFilename), this.password.toCharArray());
/*      */ 
/*  475 */           decryptionMaterial = new PublicKeyDecryptionMaterial(ks, this.alias, this.password);
/*      */         }
/*      */         else
/*      */         {
/*  479 */           decryptionMaterial = new StandardDecryptionMaterial(this.password);
/*      */         }
/*      */ 
/*  482 */         this.securityHandler = SecurityHandlersManager.getInstance().getSecurityHandler(encParameters.getFilter());
/*  483 */         this.securityHandler.prepareForDecryption(encParameters, this.document.getDocumentID(), decryptionMaterial);
/*      */ 
/*  485 */         AccessPermission permission = this.securityHandler.getCurrentAccessPermission();
/*  486 */         if (!permission.canExtractContent())
/*      */         {
/*  488 */           LOG.warn("PDF file '" + this.pdfFile.getPath() + "' does not allow extracting content.");
/*      */         }
/*      */ 
/*      */       }
/*      */       catch (Exception e)
/*      */       {
/*  494 */         throw new IOException("Error (" + e.getClass().getSimpleName() + ") while creating security handler for decryption: " + e.getMessage());
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   private COSDictionary parseXref(long startXRefOffset)
/*      */     throws IOException
/*      */   {
/*  512 */     setPdfSource(startXRefOffset);
/*  513 */     parseStartXref();
/*      */ 
/*  515 */     long startXrefOffset = this.document.getStartXref();
/*      */ 
/*  517 */     long fixedOffset = checkXRefOffset(startXrefOffset);
/*  518 */     if (fixedOffset > -1L)
/*      */     {
/*  520 */       startXrefOffset = fixedOffset;
/*  521 */       this.document.setStartXref(startXrefOffset);
/*      */     }
/*  523 */     long prev = startXrefOffset;
/*      */ 
/*  526 */     while (prev > -1L)
/*      */     {
/*  529 */       setPdfSource(prev);
/*      */ 
/*  532 */       skipSpaces();
/*      */ 
/*  534 */       if (this.pdfSource.peek() == 120)
/*      */       {
/*  538 */         parseXrefTable(prev);
/*      */ 
/*  540 */         this.trailerOffset = this.pdfSource.getOffset();
/*      */ 
/*  542 */         while ((this.isLenient) && (this.pdfSource.peek() != 116))
/*      */         {
/*  544 */           if (this.pdfSource.getOffset() == this.trailerOffset)
/*      */           {
/*  547 */             LOG.warn("Expected trailer object at position " + this.trailerOffset + ", keep trying");
/*      */           }
/*  549 */           readLine();
/*      */         }
/*  551 */         if (!parseTrailer())
/*      */         {
/*  553 */           throw new IOException("Expected trailer object at position: " + this.pdfSource.getOffset());
/*      */         }
/*  555 */         COSDictionary trailer = this.xrefTrailerResolver.getCurrentTrailer();
/*      */ 
/*  557 */         if (trailer.containsKey(COSName.XREF_STM))
/*      */         {
/*  559 */           int streamOffset = trailer.getInt(COSName.XREF_STM);
/*      */ 
/*  561 */           fixedOffset = checkXRefOffset(streamOffset);
/*  562 */           if ((fixedOffset > -1L) && (fixedOffset != streamOffset))
/*      */           {
/*  564 */             streamOffset = (int)fixedOffset;
/*  565 */             trailer.setInt(COSName.XREF_STM, streamOffset);
/*      */           }
/*  567 */           setPdfSource(streamOffset);
/*  568 */           skipSpaces();
/*  569 */           parseXrefObjStream(prev, false);
/*      */         }
/*  571 */         prev = trailer.getInt(COSName.PREV);
/*  572 */         if (prev > -1L)
/*      */         {
/*  575 */           fixedOffset = checkXRefOffset(prev);
/*  576 */           if ((fixedOffset > -1L) && (fixedOffset != prev))
/*      */           {
/*  578 */             prev = fixedOffset;
/*  579 */             trailer.setLong(COSName.PREV, prev);
/*      */           }
/*      */         }
/*      */ 
/*      */       }
/*      */       else
/*      */       {
/*  586 */         prev = parseXrefObjStream(prev, true);
/*  587 */         if (prev > -1L)
/*      */         {
/*  590 */           fixedOffset = checkXRefOffset(prev);
/*  591 */           if ((fixedOffset > -1L) && (fixedOffset != prev))
/*      */           {
/*  593 */             prev = fixedOffset;
/*  594 */             COSDictionary trailer = this.xrefTrailerResolver.getCurrentTrailer();
/*  595 */             trailer.setLong(COSName.PREV, prev);
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/*  601 */     this.xrefTrailerResolver.setStartxref(startXrefOffset);
/*  602 */     COSDictionary trailer = this.xrefTrailerResolver.getTrailer();
/*  603 */     this.document.setTrailer(trailer);
/*      */ 
/*  606 */     checkXrefOffsets();
/*  607 */     return trailer;
/*      */   }
/*      */ 
/*      */   private long parseXrefObjStream(long objByteOffset, boolean isStandalone)
/*      */     throws IOException
/*      */   {
/*  619 */     readObjectNumber();
/*  620 */     readGenerationNumber();
/*  621 */     readPattern(OBJ_MARKER);
/*      */ 
/*  623 */     COSDictionary dict = parseCOSDictionary();
/*  624 */     COSStream xrefStream = parseCOSStream(dict, getDocument().getScratchFile());
/*  625 */     parseXrefStream(xrefStream, (int)objByteOffset, isStandalone);
/*  626 */     return dict.getLong(COSName.PREV);
/*      */   }
/*      */ 
/*      */   private final long getPdfSourceOffset()
/*      */   {
/*  633 */     return this.pdfSource.getOffset();
/*      */   }
/*      */ 
/*      */   protected final void setPdfSource(long fileOffset)
/*      */     throws IOException
/*      */   {
/*  645 */     this.pdfSource.seek(fileOffset);
/*      */   }
/*      */ 
/*      */   protected final void releasePdfSourceInputStream()
/*      */     throws IOException
/*      */   {
/*      */   }
/*      */ 
/*      */   private final void closeFileStream()
/*      */     throws IOException
/*      */   {
/*  669 */     if (this.pdfSource != null)
/*      */     {
/*  671 */       this.pdfSource.close();
/*      */     }
/*      */   }
/*      */ 
/*      */   protected final long getStartxrefOffset()
/*      */     throws IOException
/*      */   {
/*  691 */     this.fileLen = this.pdfFile.length();
/*      */ 
/*  693 */     FileInputStream fIn = null;
/*      */     byte[] buf;
/*      */     long skipBytes;
/*      */     try
/*      */     {
/*  696 */       fIn = new FileInputStream(this.pdfFile);
/*      */ 
/*  698 */       int trailByteCount = this.fileLen < this.readTrailBytes ? (int)this.fileLen : this.readTrailBytes;
/*  699 */       buf = new byte[trailByteCount];
/*  700 */       fIn.skip(skipBytes = this.fileLen - trailByteCount);
/*      */ 
/*  702 */       int off = 0;
/*      */ 
/*  704 */       while (off < trailByteCount)
/*      */       {
/*  706 */         int readBytes = fIn.read(buf, off, trailByteCount - off);
/*      */ 
/*  709 */         if (readBytes < 1)
/*      */         {
/*  711 */           throw new IOException("No more bytes to read for trailing buffer, but expected: " + (trailByteCount - off));
/*      */         }
/*      */ 
/*  714 */         off += readBytes;
/*      */       }
/*      */     }
/*      */     finally
/*      */     {
/*  719 */       if (fIn != null)
/*      */       {
/*      */         try
/*      */         {
/*  723 */           fIn.close();
/*      */         }
/*      */         catch (IOException ioe)
/*      */         {
/*      */         }
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/*  732 */     int bufOff = lastIndexOf(EOF_MARKER, buf, buf.length);
/*      */ 
/*  734 */     if (bufOff < 0)
/*      */     {
/*  736 */       if (this.isLenient)
/*      */       {
/*  739 */         bufOff = buf.length;
/*  740 */         LOG.debug("Missing end of file marker '" + new String(EOF_MARKER) + "'");
/*      */       }
/*      */       else
/*      */       {
/*  744 */         throw new IOException("Missing end of file marker '" + new String(EOF_MARKER) + "'");
/*      */       }
/*      */     }
/*      */ 
/*  748 */     bufOff = lastIndexOf(STARTXREF_MARKER, buf, bufOff);
/*      */ 
/*  750 */     if (bufOff < 0)
/*      */     {
/*  752 */       if (this.isLenient)
/*      */       {
/*  754 */         this.trailerOffset = lastIndexOf(TRAILER_MARKER, buf, buf.length);
/*  755 */         if (this.trailerOffset > 0L)
/*      */         {
/*  757 */           this.trailerOffset += skipBytes;
/*      */         }
/*  759 */         return -1L;
/*      */       }
/*      */ 
/*  763 */       throw new IOException("Missing 'startxref' marker.");
/*      */     }
/*      */ 
/*  766 */     return skipBytes + bufOff;
/*      */   }
/*      */ 
/*      */   protected int lastIndexOf(char[] pattern, byte[] buf, int endOff)
/*      */   {
/*  783 */     int lastPatternChOff = pattern.length - 1;
/*      */ 
/*  785 */     int bufOff = endOff;
/*  786 */     int patOff = lastPatternChOff;
/*  787 */     char lookupCh = pattern[patOff];
/*      */     while (true) {
/*  789 */       bufOff--; if (bufOff < 0)
/*      */         break;
/*  791 */       if (buf[bufOff] == lookupCh)
/*      */       {
/*  793 */         patOff--; if (patOff < 0)
/*      */         {
/*  796 */           return bufOff;
/*      */         }
/*      */ 
/*  799 */         lookupCh = pattern[patOff];
/*      */       }
/*  801 */       else if (patOff < lastPatternChOff)
/*      */       {
/*  804 */         lookupCh = pattern[(patOff = lastPatternChOff)];
/*      */       }
/*      */     }
/*      */ 
/*  808 */     return -1;
/*      */   }
/*      */ 
/*      */   protected final void readPattern(char[] pattern)
/*      */     throws IOException
/*      */   {
/*  821 */     skipSpaces();
/*      */ 
/*  823 */     for (char c : pattern)
/*      */     {
/*  825 */       if (this.pdfSource.read() != c)
/*      */       {
/*  827 */         throw new IOException("Expected pattern '" + new String(pattern) + "' but missed at character '" + c + "' at offset " + this.pdfSource.getOffset());
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/*  832 */     skipSpaces();
/*      */   }
/*      */ 
/*      */   private COSDictionary getPagesObject()
/*      */     throws IOException
/*      */   {
/*  844 */     if (this.pagesDictionary != null)
/*      */     {
/*  846 */       return this.pagesDictionary;
/*      */     }
/*  848 */     COSObject pages = (COSObject)this.document.getCatalog().getItem(COSName.PAGES);
/*      */ 
/*  850 */     if (pages == null)
/*      */     {
/*  852 */       throw new IOException("Missing PAGES entry in document catalog.");
/*      */     }
/*      */ 
/*  855 */     COSBase object = parseObjectDynamically(pages, false);
/*      */ 
/*  857 */     if (!(object instanceof COSDictionary))
/*      */     {
/*  859 */       throw new IOException("PAGES not a dictionary object, but: " + object.getClass().getSimpleName());
/*      */     }
/*      */ 
/*  862 */     this.pagesDictionary = ((COSDictionary)object);
/*      */ 
/*  864 */     return this.pagesDictionary;
/*      */   }
/*      */ 
/*      */   public void parse()
/*      */     throws IOException
/*      */   {
/*  875 */     boolean exceptionOccurred = true;
/*      */     try
/*      */     {
/*  881 */       parseHeader();
/*  882 */       this.pdfSource.seek(0L);
/*      */ 
/*  884 */       if (!this.initialParseDone)
/*      */       {
/*  886 */         initialParse();
/*      */       }
/*      */ 
/*  890 */       if (!this.isFDFDocment)
/*      */       {
/*  892 */         int pageCount = getPageNumber();
/*      */ 
/*  894 */         if (!this.allPagesParsed)
/*      */         {
/*  896 */           for (int pNr = 0; pNr < pageCount; pNr++)
/*      */           {
/*  898 */             getPage(pNr);
/*      */           }
/*  900 */           this.allPagesParsed = true;
/*  901 */           this.document.setDecrypted();
/*      */         }
/*      */       }
/*  904 */       exceptionOccurred = false;
/*      */     }
/*      */     finally
/*      */     {
/*      */       try
/*      */       {
/*  910 */         closeFileStream();
/*      */       }
/*      */       catch (IOException ioe)
/*      */       {
/*      */       }
/*      */ 
/*  916 */       deleteTempFile();
/*      */ 
/*  918 */       if ((exceptionOccurred) && (this.document != null))
/*      */       {
/*      */         try
/*      */         {
/*  922 */           this.document.close();
/*  923 */           this.document = null;
/*      */         }
/*      */         catch (IOException ioe)
/*      */         {
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   protected File getPdfFile()
/*      */   {
/*  939 */     return this.pdfFile;
/*      */   }
/*      */ 
/*      */   public boolean isLenient()
/*      */   {
/*  948 */     return this.isLenient;
/*      */   }
/*      */ 
/*      */   public void setLenient(boolean lenient)
/*      */     throws IllegalArgumentException
/*      */   {
/*  962 */     if (this.initialParseDone)
/*      */     {
/*  964 */       throw new IllegalArgumentException("Cannot change leniency after parsing");
/*      */     }
/*  966 */     this.isLenient = lenient;
/*      */   }
/*      */ 
/*      */   protected void deleteTempFile()
/*      */   {
/*  974 */     if (this.isTmpPDFFile)
/*      */     {
/*      */       try
/*      */       {
/*  978 */         if (!this.pdfFile.delete())
/*      */         {
/*  980 */           LOG.warn("Temporary file '" + this.pdfFile.getName() + "' can't be deleted");
/*      */         }
/*      */       }
/*      */       catch (SecurityException e)
/*      */       {
/*  985 */         LOG.warn("Temporary file '" + this.pdfFile.getName() + "' can't be deleted", e);
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   public SecurityHandler getSecurityHandler()
/*      */   {
/*  999 */     return this.securityHandler;
/*      */   }
/*      */ 
/*      */   public PDDocument getPDDocument()
/*      */     throws IOException
/*      */   {
/* 1016 */     PDDocument pdDocument = super.getPDDocument();
/* 1017 */     if (this.securityHandler != null)
/*      */     {
/* 1019 */       pdDocument.setSecurityHandler(this.securityHandler);
/*      */     }
/* 1021 */     return pdDocument;
/*      */   }
/*      */ 
/*      */   public int getPageNumber()
/*      */     throws IOException
/*      */   {
/* 1034 */     int pageCount = getPagesObject().getInt(COSName.COUNT);
/*      */ 
/* 1036 */     if (pageCount < 0)
/*      */     {
/* 1038 */       throw new IOException("No page number specified.");
/*      */     }
/* 1040 */     return pageCount;
/*      */   }
/*      */ 
/*      */   public PDPage getPage(int pageNr)
/*      */     throws IOException
/*      */   {
/* 1053 */     getPagesObject();
/*      */ 
/* 1056 */     COSArray kids = (COSArray)this.pagesDictionary.getDictionaryObject(COSName.KIDS);
/*      */ 
/* 1058 */     if (kids == null)
/*      */     {
/* 1060 */       throw new IOException("Missing 'Kids' entry in pages dictionary.");
/*      */     }
/*      */ 
/* 1065 */     COSObject pageObj = getPageObject(pageNr, kids, 0);
/*      */ 
/* 1067 */     if (pageObj == null)
/*      */     {
/* 1069 */       throw new IOException("Page " + pageNr + " not found.");
/*      */     }
/*      */ 
/* 1073 */     COSDictionary pageDict = (COSDictionary)pageObj.getObject();
/*      */ 
/* 1075 */     if ((this.parseMinimalCatalog) && (!this.allPagesParsed))
/*      */     {
/* 1078 */       COSDictionary resDict = (COSDictionary)pageDict.getDictionaryObject(COSName.RESOURCES);
/* 1079 */       parseDictObjects(resDict, new COSName[0]);
/*      */     }
/*      */ 
/* 1082 */     return new PDPage(pageDict);
/*      */   }
/*      */ 
/*      */   private COSObject getPageObject(int num, COSArray startKids, int startPageCount)
/*      */     throws IOException
/*      */   {
/* 1101 */     int curPageCount = startPageCount;
/* 1102 */     Iterator kidsIter = startKids.iterator();
/*      */ 
/* 1104 */     while (kidsIter.hasNext())
/*      */     {
/* 1106 */       COSObject obj = (COSObject)kidsIter.next();
/* 1107 */       COSBase base = obj.getObject();
/* 1108 */       if (base == null)
/*      */       {
/* 1110 */         base = parseObjectDynamically(obj, false);
/* 1111 */         obj.setObject(base);
/*      */       }
/*      */ 
/* 1114 */       COSDictionary dic = (COSDictionary)base;
/* 1115 */       int count = dic.getInt(COSName.COUNT);
/* 1116 */       if (count >= 0)
/*      */       {
/* 1119 */         if (curPageCount + count <= num)
/*      */         {
/* 1121 */           curPageCount += count;
/*      */         }
/*      */       }
/*      */       else
/*      */       {
/* 1126 */         COSArray kids = (COSArray)dic.getDictionaryObject(COSName.KIDS);
/* 1127 */         if (kids != null)
/*      */         {
/* 1130 */           COSObject ans = getPageObject(num, kids, curPageCount);
/*      */ 
/* 1132 */           if (ans != null)
/*      */           {
/* 1134 */             return ans;
/*      */           }
/*      */ 
/*      */         }
/*      */         else
/*      */         {
/* 1140 */           if (curPageCount == num)
/*      */           {
/* 1142 */             return obj;
/*      */           }
/*      */ 
/* 1145 */           curPageCount++;
/*      */         }
/*      */       }
/*      */     }
/* 1148 */     return null;
/*      */   }
/*      */ 
/*      */   private final long getObjectId(COSObject obj)
/*      */   {
/* 1157 */     return obj.getObjectNumber().longValue() << 32 | obj.getGenerationNumber().longValue();
/*      */   }
/*      */ 
/*      */   private final void addNewToList(Queue<COSBase> toBeParsedList, Collection<COSBase> newObjects, Set<Long> addedObjects)
/*      */   {
/* 1167 */     for (COSBase newObject : newObjects)
/*      */     {
/* 1169 */       if ((newObject instanceof COSObject))
/*      */       {
/* 1171 */         long objId = getObjectId((COSObject)newObject);
/* 1172 */         if (!addedObjects.add(Long.valueOf(objId)));
/*      */       }
/*      */       else
/*      */       {
/* 1177 */         toBeParsedList.add(newObject);
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   private final void addNewToList(Queue<COSBase> toBeParsedList, COSBase newObject, Set<Long> addedObjects)
/*      */   {
/* 1188 */     if ((newObject instanceof COSObject))
/*      */     {
/* 1190 */       long objId = getObjectId((COSObject)newObject);
/* 1191 */       if (!addedObjects.add(Long.valueOf(objId)))
/*      */       {
/* 1193 */         return;
/*      */       }
/*      */     }
/* 1196 */     toBeParsedList.add(newObject);
/*      */   }
/*      */ 
/*      */   private void parseDictObjects(COSDictionary dict, COSName[] excludeObjects)
/*      */     throws IOException
/*      */   {
/* 1213 */     Queue toBeParsedList = new LinkedList();
/*      */ 
/* 1215 */     TreeMap objToBeParsed = new TreeMap();
/*      */ 
/* 1217 */     Set parsedObjects = new HashSet();
/* 1218 */     Set addedObjects = new HashSet();
/*      */ 
/* 1221 */     if (excludeObjects != null)
/*      */     {
/* 1223 */       for (COSName objName : excludeObjects)
/*      */       {
/* 1225 */         COSBase baseObj = dict.getItem(objName);
/* 1226 */         if ((baseObj instanceof COSObject))
/*      */         {
/* 1228 */           parsedObjects.add(Long.valueOf(getObjectId((COSObject)baseObj)));
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/* 1233 */     addNewToList(toBeParsedList, dict.getValues(), addedObjects);
/*      */ 
/* 1236 */     while ((!toBeParsedList.isEmpty()) || (!objToBeParsed.isEmpty()))
/*      */     {
/*      */       COSBase baseObj;
/* 1241 */       while ((baseObj = (COSBase)toBeParsedList.poll()) != null)
/*      */       {
/* 1243 */         if ((baseObj instanceof COSStream))
/*      */         {
/* 1245 */           addNewToList(toBeParsedList, ((COSStream)baseObj).getValues(), addedObjects);
/*      */         }
/* 1247 */         else if ((baseObj instanceof COSDictionary))
/*      */         {
/* 1249 */           addNewToList(toBeParsedList, ((COSDictionary)baseObj).getValues(), addedObjects);
/*      */         }
/* 1251 */         else if ((baseObj instanceof COSArray))
/*      */         {
/* 1253 */           Iterator arrIter = ((COSArray)baseObj).iterator();
/* 1254 */           while (arrIter.hasNext())
/*      */           {
/* 1256 */             addNewToList(toBeParsedList, (COSBase)arrIter.next(), addedObjects);
/*      */           }
/*      */         }
/* 1259 */         else if ((baseObj instanceof COSObject))
/*      */         {
/* 1261 */           COSObject obj = (COSObject)baseObj;
/* 1262 */           long objId = getObjectId(obj);
/* 1263 */           COSObjectKey objKey = new COSObjectKey(obj.getObjectNumber().intValue(), obj.getGenerationNumber().intValue());
/*      */ 
/* 1266 */           if (!parsedObjects.contains(Long.valueOf(objId)))
/*      */           {
/* 1270 */             Long fileOffset = (Long)this.xrefTrailerResolver.getXrefTable().get(objKey);
/*      */ 
/* 1273 */             if ((fileOffset != null) && (fileOffset.longValue() != 0L))
/*      */             {
/* 1275 */               if (fileOffset.longValue() > 0L)
/*      */               {
/* 1277 */                 objToBeParsed.put(fileOffset, Collections.singletonList(obj));
/*      */               }
/*      */               else
/*      */               {
/* 1284 */                 fileOffset = (Long)this.xrefTrailerResolver.getXrefTable().get(new COSObjectKey(-fileOffset.longValue(), 0L));
/* 1285 */                 if ((fileOffset == null) || (fileOffset.longValue() <= 0L))
/*      */                 {
/* 1287 */                   throw new IOException("Invalid object stream xref object reference for key '" + objKey + "': " + fileOffset);
/*      */                 }
/*      */ 
/* 1292 */                 List stmObjects = (List)objToBeParsed.get(fileOffset);
/* 1293 */                 if (stmObjects == null)
/*      */                 {
/* 1295 */                   objToBeParsed.put(fileOffset, stmObjects = new ArrayList());
/*      */                 }
/* 1297 */                 stmObjects.add(obj);
/*      */               }
/*      */ 
/*      */             }
/*      */             else
/*      */             {
/* 1303 */               COSObject pdfObject = this.document.getObjectFromPool(objKey);
/* 1304 */               pdfObject.setObject(COSNull.NULL);
/*      */             }
/*      */           }
/*      */ 
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/* 1312 */       if (objToBeParsed.isEmpty())
/*      */       {
/*      */         break;
/*      */       }
/*      */ 
/* 1317 */       for (COSObject obj : (List)objToBeParsed.remove(objToBeParsed.firstKey()))
/*      */       {
/* 1319 */         COSBase parsedObj = parseObjectDynamically(obj, false);
/*      */ 
/* 1321 */         obj.setObject(parsedObj);
/* 1322 */         addNewToList(toBeParsedList, parsedObj, addedObjects);
/*      */ 
/* 1324 */         parsedObjects.add(Long.valueOf(getObjectId(obj)));
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   protected final COSBase parseObjectDynamically(COSObject obj, boolean requireExistingNotCompressedObj)
/*      */     throws IOException
/*      */   {
/* 1345 */     return parseObjectDynamically(obj.getObjectNumber().intValue(), obj.getGenerationNumber().intValue(), requireExistingNotCompressedObj);
/*      */   }
/*      */ 
/*      */   protected COSBase parseObjectDynamically(int objNr, int objGenNr, boolean requireExistingNotCompressedObj)
/*      */     throws IOException
/*      */   {
/* 1370 */     COSObjectKey objKey = new COSObjectKey(objNr, objGenNr);
/* 1371 */     COSObject pdfObject = this.document.getObjectFromPool(objKey);
/*      */     Set refObjNrs;
/* 1373 */     if (pdfObject.getObject() == null)
/*      */     {
/* 1377 */       Long offsetOrObjstmObNr = (Long)this.xrefTrailerResolver.getXrefTable().get(objKey);
/*      */ 
/* 1380 */       if ((requireExistingNotCompressedObj) && ((offsetOrObjstmObNr == null) || (offsetOrObjstmObNr.longValue() <= 0L)))
/*      */       {
/* 1382 */         throw new IOException("Object must be defined and must not be compressed object: " + objKey.getNumber() + ":" + objKey.getGeneration());
/*      */       }
/*      */ 
/* 1386 */       if (offsetOrObjstmObNr == null)
/*      */       {
/* 1389 */         pdfObject.setObject(COSNull.NULL);
/*      */       }
/* 1391 */       else if (offsetOrObjstmObNr.longValue() > 0L)
/*      */       {
/* 1395 */         setPdfSource(offsetOrObjstmObNr.longValue());
/*      */ 
/* 1398 */         long readObjNr = readObjectNumber();
/* 1399 */         long readObjGen = readGenerationNumber();
/* 1400 */         readPattern(OBJ_MARKER);
/*      */ 
/* 1403 */         if ((readObjNr != objKey.getNumber()) || (readObjGen != objKey.getGeneration()))
/*      */         {
/* 1405 */           throw new IOException("XREF for " + objKey.getNumber() + ":" + objKey.getGeneration() + " points to wrong object: " + readObjNr + ":" + readObjGen);
/*      */         }
/*      */ 
/* 1409 */         skipSpaces();
/* 1410 */         COSBase pb = parseDirObject();
/* 1411 */         String endObjectKey = readString();
/*      */ 
/* 1413 */         if (endObjectKey.equals("stream"))
/*      */         {
/* 1415 */           this.pdfSource.unread(endObjectKey.getBytes("ISO-8859-1"));
/* 1416 */           this.pdfSource.unread(32);
/* 1417 */           if ((pb instanceof COSDictionary))
/*      */           {
/* 1419 */             COSStream stream = parseCOSStream((COSDictionary)pb, getDocument().getScratchFile());
/*      */ 
/* 1421 */             if (this.securityHandler != null)
/*      */             {
/*      */               try
/*      */               {
/* 1425 */                 this.securityHandler.decryptStream(stream, objNr, objGenNr);
/*      */               }
/*      */               catch (CryptographyException ce)
/*      */               {
/* 1429 */                 throw new IOException("Error decrypting stream object " + objNr + ": " + ce.getMessage());
/*      */               }
/*      */ 
/*      */             }
/*      */ 
/* 1434 */             pb = stream;
/*      */           }
/*      */           else
/*      */           {
/* 1441 */             throw new IOException("Stream not preceded by dictionary (offset: " + offsetOrObjstmObNr + ").");
/*      */           }
/* 1443 */           skipSpaces();
/* 1444 */           endObjectKey = readLine();
/*      */ 
/* 1447 */           if (!endObjectKey.startsWith("endobj"))
/*      */           {
/* 1449 */             if (endObjectKey.startsWith("endstream"))
/*      */             {
/* 1451 */               endObjectKey = endObjectKey.substring(9).trim();
/* 1452 */               if (endObjectKey.length() == 0)
/*      */               {
/* 1455 */                 endObjectKey = readLine();
/*      */               }
/*      */             }
/*      */           }
/*      */         }
/* 1460 */         else if (this.securityHandler != null)
/*      */         {
/* 1462 */           decrypt(pb, objNr, objGenNr);
/*      */         }
/*      */ 
/* 1465 */         pdfObject.setObject(pb);
/*      */ 
/* 1467 */         if (!endObjectKey.startsWith("endobj"))
/*      */         {
/* 1469 */           if (this.isLenient)
/*      */           {
/* 1471 */             LOG.warn("Object (" + readObjNr + ":" + readObjGen + ") at offset " + offsetOrObjstmObNr + " does not end with 'endobj' but with '" + endObjectKey + "'");
/*      */           }
/*      */           else
/*      */           {
/* 1476 */             throw new IOException("Object (" + readObjNr + ":" + readObjGen + ") at offset " + offsetOrObjstmObNr + " does not end with 'endobj' but with '" + endObjectKey + "'");
/*      */           }
/*      */ 
/*      */         }
/*      */ 
/* 1481 */         releasePdfSourceInputStream();
/*      */       }
/*      */       else
/*      */       {
/* 1490 */         int objstmObjNr = (int)-offsetOrObjstmObNr.longValue();
/* 1491 */         COSBase objstmBaseObj = parseObjectDynamically(objstmObjNr, 0, true);
/* 1492 */         if ((objstmBaseObj instanceof COSStream))
/*      */         {
/* 1495 */           PDFObjectStreamParser parser = new PDFObjectStreamParser((COSStream)objstmBaseObj, this.document, this.forceParsing);
/*      */ 
/* 1497 */           parser.parse();
/*      */ 
/* 1501 */           refObjNrs = this.xrefTrailerResolver.getContainedObjectNumbers(objstmObjNr);
/*      */ 
/* 1505 */           for (COSObject next : parser.getObjects())
/*      */           {
/* 1507 */             COSObjectKey stmObjKey = new COSObjectKey(next);
/* 1508 */             if (refObjNrs.contains(Long.valueOf(stmObjKey.getNumber())))
/*      */             {
/* 1510 */               COSObject stmObj = this.document.getObjectFromPool(stmObjKey);
/* 1511 */               stmObj.setObject(next.getObject());
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/* 1517 */     return pdfObject.getObject();
/*      */   }
/*      */ 
/*      */   protected final void decryptDictionary(COSDictionary dict, long objNr, long objGenNr)
/*      */     throws IOException
/*      */   {
/* 1531 */     if (!COSName.SIG.equals(dict.getItem(COSName.TYPE)))
/*      */     {
/* 1533 */       for (Map.Entry entry : dict.entrySet())
/*      */       {
/* 1535 */         if ((entry.getValue() instanceof COSString))
/*      */         {
/* 1537 */           decryptString((COSString)entry.getValue(), objNr, objGenNr);
/*      */         }
/* 1539 */         else if ((entry.getValue() instanceof COSArray))
/*      */         {
/*      */           try
/*      */           {
/* 1543 */             this.securityHandler.decryptArray((COSArray)entry.getValue(), objNr, objGenNr);
/*      */           }
/*      */           catch (CryptographyException ce)
/*      */           {
/* 1547 */             throw new IOException("Error decrypting stream object " + objNr + ": " + ce.getMessage());
/*      */           }
/*      */ 
/*      */         }
/* 1552 */         else if ((entry.getValue() instanceof COSDictionary))
/*      */         {
/* 1554 */           decryptDictionary((COSDictionary)entry.getValue(), objNr, objGenNr);
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   protected final void decryptString(COSString str, long objNr, long objGenNr)
/*      */     throws IOException
/*      */   {
/*      */     try
/*      */     {
/* 1573 */       this.securityHandler.decryptString(str, objNr, objGenNr);
/*      */     }
/*      */     catch (CryptographyException ce)
/*      */     {
/* 1577 */       throw new IOException("Error decrypting string: " + ce.getMessage());
/*      */     }
/*      */   }
/*      */ 
/*      */   protected final void decrypt(COSBase pb, int objNr, int objGenNr)
/*      */     throws IOException
/*      */   {
/* 1592 */     if ((pb instanceof COSString))
/*      */     {
/* 1594 */       decryptString((COSString)pb, objNr, objGenNr);
/*      */     }
/* 1596 */     else if ((pb instanceof COSDictionary))
/*      */     {
/* 1598 */       decryptDictionary((COSDictionary)pb, objNr, objGenNr);
/*      */     }
/* 1600 */     else if ((pb instanceof COSArray))
/*      */     {
/* 1602 */       COSArray array = (COSArray)pb;
/* 1603 */       int aIdx = 0; for (int len = array.size(); aIdx < len; aIdx++)
/*      */       {
/* 1605 */         decrypt(array.get(aIdx), objNr, objGenNr);
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   private COSNumber getLength(COSBase lengthBaseObj)
/*      */     throws IOException
/*      */   {
/* 1616 */     if (lengthBaseObj == null)
/*      */     {
/* 1618 */       return null;
/*      */     }
/*      */ 
/* 1621 */     if (this.inGetLength)
/*      */     {
/* 1623 */       throw new IOException("Loop while reading length from " + lengthBaseObj);
/*      */     }
/*      */ 
/* 1626 */     COSNumber retVal = null;
/*      */     try
/*      */     {
/* 1630 */       this.inGetLength = true;
/*      */ 
/* 1633 */       if ((lengthBaseObj instanceof COSNumber))
/*      */       {
/* 1635 */         retVal = (COSNumber)lengthBaseObj;
/*      */       }
/* 1638 */       else if ((lengthBaseObj instanceof COSObject))
/*      */       {
/* 1640 */         COSObject lengthObj = (COSObject)lengthBaseObj;
/*      */ 
/* 1642 */         if (lengthObj.getObject() == null)
/*      */         {
/* 1647 */           long curFileOffset = getPdfSourceOffset();
/* 1648 */           releasePdfSourceInputStream();
/*      */ 
/* 1650 */           parseObjectDynamically(lengthObj, true);
/*      */ 
/* 1653 */           setPdfSource(curFileOffset);
/*      */ 
/* 1655 */           if (lengthObj.getObject() == null)
/*      */           {
/* 1657 */             throw new IOException("Length object content was not read.");
/*      */           }
/*      */         }
/*      */ 
/* 1661 */         if (!(lengthObj.getObject() instanceof COSNumber))
/*      */         {
/* 1663 */           throw new IOException("Wrong type of referenced length object " + lengthObj + ": " + lengthObj.getObject().getClass().getSimpleName());
/*      */         }
/*      */ 
/* 1667 */         retVal = (COSNumber)lengthObj.getObject();
/*      */       }
/*      */       else
/*      */       {
/* 1672 */         throw new IOException("Wrong type of length object: " + lengthBaseObj.getClass().getSimpleName());
/*      */       }
/*      */     }
/*      */     finally
/*      */     {
/* 1677 */       this.inGetLength = false;
/*      */     }
/* 1679 */     return retVal;
/*      */   }
/*      */ 
/*      */   protected COSStream parseCOSStream(COSDictionary dic, RandomAccess file)
/*      */     throws IOException
/*      */   {
/* 1706 */     COSStream stream = new COSStream(dic, file);
/* 1707 */     OutputStream out = null;
/*      */     try
/*      */     {
/* 1710 */       readString();
/*      */ 
/* 1717 */       int whitespace = this.pdfSource.read();
/*      */ 
/* 1722 */       while (whitespace == 32)
/*      */       {
/* 1724 */         whitespace = this.pdfSource.read();
/*      */       }
/*      */ 
/* 1727 */       if (whitespace == 13)
/*      */       {
/* 1729 */         whitespace = this.pdfSource.read();
/* 1730 */         if (whitespace != 10)
/*      */         {
/* 1734 */           this.pdfSource.unread(whitespace);
/*      */         }
/*      */       }
/* 1737 */       else if (whitespace != 10)
/*      */       {
/* 1741 */         this.pdfSource.unread(whitespace);
/*      */       }
/*      */ 
/* 1747 */       COSNumber streamLengthObj = getLength(dic.getItem(COSName.LENGTH));
/* 1748 */       if (streamLengthObj == null)
/*      */       {
/* 1750 */         if (this.isLenient)
/*      */         {
/* 1752 */           LOG.warn("The stream doesn't provide any stream length, using fallback readUntilEnd");
/*      */         }
/*      */         else
/*      */         {
/* 1756 */           throw new IOException("Missing length for stream.");
/*      */         }
/*      */       }
/*      */ 
/* 1760 */       boolean useReadUntilEnd = false;
/*      */ 
/* 1762 */       if ((streamLengthObj != null) && (validateStreamLength(streamLengthObj.longValue())))
/*      */       {
/* 1764 */         out = stream.createFilteredStream(streamLengthObj);
/* 1765 */         long remainBytes = streamLengthObj.longValue();
/* 1766 */         int bytesRead = 0;
/* 1767 */         while (remainBytes > 0L)
/*      */         {
/* 1769 */           int readBytes = this.pdfSource.read(this.streamCopyBuf, 0, remainBytes > 8192L ? 8192 : (int)remainBytes);
/*      */ 
/* 1771 */           if (readBytes <= 0)
/*      */           {
/* 1773 */             useReadUntilEnd = true;
/* 1774 */             out.close();
/* 1775 */             this.pdfSource.unread(bytesRead);
/* 1776 */             break;
/*      */           }
/* 1778 */           out.write(this.streamCopyBuf, 0, readBytes);
/* 1779 */           remainBytes -= readBytes;
/* 1780 */           bytesRead += readBytes;
/*      */         }
/*      */       }
/*      */       else
/*      */       {
/* 1785 */         useReadUntilEnd = true;
/*      */       }
/* 1787 */       if (useReadUntilEnd)
/*      */       {
/* 1789 */         out = stream.createFilteredStream();
/* 1790 */         readUntilEndStream(new EndstreamOutputStream(out));
/*      */       }
/* 1792 */       String endStream = readString();
/* 1793 */       if ((endStream.equals("endobj")) && (this.isLenient))
/*      */       {
/* 1795 */         LOG.warn("stream ends with 'endobj' instead of 'endstream' at offset " + this.pdfSource.getOffset());
/*      */ 
/* 1798 */         this.pdfSource.unread("endobj".getBytes("ISO-8859-1"));
/*      */       }
/* 1800 */       else if ((endStream.length() > 9) && (this.isLenient) && (endStream.substring(0, 9).equals("endstream")))
/*      */       {
/* 1802 */         LOG.warn("stream ends with '" + endStream + "' instead of 'endstream' at offset " + this.pdfSource.getOffset());
/*      */ 
/* 1805 */         this.pdfSource.unread(endStream.substring(9).getBytes("ISO-8859-1"));
/*      */       }
/* 1807 */       else if (!endStream.equals("endstream"))
/*      */       {
/* 1809 */         throw new IOException("Error reading stream, expected='endstream' actual='" + endStream + "' at offset " + this.pdfSource.getOffset());
/*      */       }
/*      */ 
/*      */     }
/*      */     finally
/*      */     {
/* 1816 */       if (out != null)
/*      */       {
/* 1818 */         out.close();
/*      */       }
/*      */     }
/* 1821 */     return stream;
/*      */   }
/*      */ 
/*      */   private boolean validateStreamLength(long streamLength) throws IOException
/*      */   {
/* 1826 */     boolean streamLengthIsValid = true;
/* 1827 */     long originOffset = this.pdfSource.getOffset();
/* 1828 */     long expectedEndOfStream = originOffset + streamLength;
/* 1829 */     if (expectedEndOfStream > this.fileLen)
/*      */     {
/* 1831 */       streamLengthIsValid = false;
/* 1832 */       LOG.error("The end of the stream is out of range, using workaround to read the stream");
/* 1833 */       LOG.error("Stream start offset: " + originOffset);
/* 1834 */       LOG.error("Expected endofstream offset: " + expectedEndOfStream);
/*      */     }
/*      */     else
/*      */     {
/* 1838 */       this.pdfSource.seek(expectedEndOfStream);
/* 1839 */       skipSpaces();
/* 1840 */       if (!checkBytesAtOffset("endstream".getBytes("ISO-8859-1")))
/*      */       {
/* 1842 */         streamLengthIsValid = false;
/* 1843 */         LOG.error("The end of the stream doesn't point to the correct offset, using workaround to read the stream");
/* 1844 */         LOG.error("Stream start offset: " + originOffset);
/* 1845 */         LOG.error("Expected endofstream offset: " + expectedEndOfStream);
/*      */       }
/* 1847 */       this.pdfSource.seek(originOffset);
/*      */     }
/* 1849 */     return streamLengthIsValid;
/*      */   }
/*      */ 
/*      */   private long checkXRefOffset(long startXRefOffset)
/*      */     throws IOException
/*      */   {
/* 1862 */     if (!this.isLenient)
/*      */     {
/* 1864 */       return startXRefOffset;
/*      */     }
/* 1866 */     setPdfSource(startXRefOffset - 1L);
/*      */ 
/* 1868 */     int previous = this.pdfSource.read();
/* 1869 */     if ((this.pdfSource.peek() == 120) && (checkBytesAtOffset(XREF_TABLE)))
/*      */     {
/* 1871 */       return startXRefOffset;
/*      */     }
/*      */ 
/* 1874 */     if (isWhitespace(previous))
/*      */     {
/* 1876 */       int nextValue = this.pdfSource.peek();
/*      */ 
/* 1879 */       if ((nextValue > 47) && (nextValue < 58))
/*      */       {
/*      */         try
/*      */         {
/* 1884 */           readObjectNumber();
/* 1885 */           readGenerationNumber();
/* 1886 */           readPattern(OBJ_MARKER);
/* 1887 */           setPdfSource(startXRefOffset);
/* 1888 */           return startXRefOffset;
/*      */         }
/*      */         catch (IOException exception)
/*      */         {
/* 1894 */           this.pdfSource.seek(startXRefOffset);
/*      */         }
/*      */       }
/*      */     }
/*      */ 
/* 1899 */     return calculateXRefFixedOffset(startXRefOffset);
/*      */   }
/*      */ 
/*      */   private boolean checkBytesAtOffset(byte[] string)
/*      */     throws IOException
/*      */   {
/* 1911 */     boolean bytesMatching = false;
/* 1912 */     if (this.pdfSource.peek() == string[0])
/*      */     {
/* 1914 */       int length = string.length;
/* 1915 */       byte[] bytesRead = new byte[length];
/* 1916 */       int numberOfBytes = this.pdfSource.read(bytesRead, 0, length);
/* 1917 */       while (numberOfBytes < length)
/*      */       {
/* 1919 */         int readMore = this.pdfSource.read(bytesRead, numberOfBytes, length - numberOfBytes);
/* 1920 */         if (readMore < 0)
/*      */         {
/*      */           break;
/*      */         }
/* 1924 */         numberOfBytes += readMore;
/*      */       }
/* 1926 */       if (Arrays.equals(string, bytesRead))
/*      */       {
/* 1928 */         bytesMatching = true;
/*      */       }
/* 1930 */       this.pdfSource.unread(bytesRead, 0, numberOfBytes);
/*      */     }
/* 1932 */     return bytesMatching;
/*      */   }
/*      */ 
/*      */   private long calculateXRefFixedOffset(long objectOffset)
/*      */     throws IOException
/*      */   {
/* 1945 */     if (objectOffset < 0L)
/*      */     {
/* 1947 */       LOG.error("Invalid object offset " + objectOffset + " when searching for a xref table/stream");
/* 1948 */       return 0L;
/*      */     }
/*      */ 
/* 1951 */     long newOffset = bfSearchForXRef(objectOffset);
/* 1952 */     if (newOffset > -1L)
/*      */     {
/* 1954 */       LOG.debug("Fixed reference for xref table/stream " + objectOffset + " -> " + newOffset);
/* 1955 */       return newOffset;
/*      */     }
/* 1957 */     LOG.error("Can't find the object axref table/stream at offset " + objectOffset);
/* 1958 */     return 0L;
/*      */   }
/*      */ 
/*      */   private void checkXrefOffsets()
/*      */     throws IOException
/*      */   {
/* 1970 */     if (!this.isLenient)
/*      */     {
/* 1972 */       return;
/*      */     }
/* 1974 */     Map xrefOffset = this.xrefTrailerResolver.getXrefTable();
/* 1975 */     if (xrefOffset != null)
/*      */     {
/* 1977 */       for (COSObjectKey objectKey : xrefOffset.keySet())
/*      */       {
/* 1979 */         Long objectOffset = (Long)xrefOffset.get(objectKey);
/*      */ 
/* 1982 */         if ((objectOffset != null) && (objectOffset.longValue() >= 0L))
/*      */         {
/* 1984 */           long objectNr = objectKey.getNumber();
/* 1985 */           long objectGen = objectKey.getGeneration();
/* 1986 */           String objectString = createObjectString(objectNr, objectGen);
/* 1987 */           if (!checkObjectId(objectString, objectOffset.longValue()))
/*      */           {
/* 1989 */             long newOffset = bfSearchForObject(objectString);
/* 1990 */             if (newOffset > -1L)
/*      */             {
/* 1992 */               xrefOffset.put(objectKey, Long.valueOf(newOffset));
/* 1993 */               LOG.debug("Fixed reference for object " + objectNr + " " + objectGen + " " + objectOffset + " -> " + newOffset);
/*      */             }
/*      */             else
/*      */             {
/* 1998 */               LOG.error("Can't find the object " + objectNr + " " + objectGen + " (origin offset " + objectOffset + ")");
/*      */             }
/*      */           }
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   private boolean checkObjectId(String objectString, long offset)
/*      */     throws IOException
/*      */   {
/* 2017 */     boolean objectFound = false;
/* 2018 */     long originOffset = this.pdfSource.getOffset();
/* 2019 */     this.pdfSource.seek(offset);
/* 2020 */     objectFound = checkBytesAtOffset(objectString.getBytes("ISO-8859-1"));
/* 2021 */     this.pdfSource.seek(originOffset);
/* 2022 */     return objectFound;
/*      */   }
/*      */ 
/*      */   private String createObjectString(long objectID, long genID)
/*      */   {
/* 2034 */     return Long.toString(objectID) + " " + Long.toString(genID) + " obj";
/*      */   }
/*      */ 
/*      */   private long bfSearchForObject(String objectString)
/*      */     throws IOException
/*      */   {
/* 2046 */     long newOffset = -1L;
/* 2047 */     bfSearchForObjects();
/* 2048 */     if (this.bfSearchObjectOffsets.containsKey(objectString))
/*      */     {
/* 2050 */       newOffset = ((Long)this.bfSearchObjectOffsets.get(objectString)).longValue();
/*      */     }
/* 2052 */     return newOffset;
/*      */   }
/*      */ 
/*      */   private void bfSearchForObjects()
/*      */     throws IOException
/*      */   {
/* 2062 */     if (this.bfSearchObjectOffsets == null)
/*      */     {
/* 2064 */       this.bfSearchObjectOffsets = new HashMap();
/* 2065 */       this.bfSearchCOSObjectKeyOffsets = new HashMap();
/* 2066 */       long originOffset = this.pdfSource.getOffset();
/* 2067 */       long currentOffset = 6L;
/* 2068 */       String objString = " obj";
/* 2069 */       byte[] string = objString.getBytes("ISO-8859-1");
/*      */       do
/*      */       {
/* 2072 */         this.pdfSource.seek(currentOffset);
/* 2073 */         if (checkBytesAtOffset(string))
/*      */         {
/* 2075 */           long tempOffset = currentOffset - 1L;
/* 2076 */           this.pdfSource.seek(tempOffset);
/* 2077 */           int genID = this.pdfSource.peek();
/*      */ 
/* 2079 */           if ((genID > 47) && (genID < 58))
/*      */           {
/* 2081 */             genID -= 48;
/* 2082 */             tempOffset -= 1L;
/* 2083 */             this.pdfSource.seek(tempOffset);
/* 2084 */             if (this.pdfSource.peek() == 32)
/*      */             {
/* 2086 */               while ((tempOffset > 6L) && (this.pdfSource.peek() == 32))
/*      */               {
/* 2088 */                 this.pdfSource.seek(--tempOffset);
/*      */               }
/* 2090 */               int length = 0;
/*      */ 
/* 2092 */               while ((tempOffset > 6L) && (this.pdfSource.peek() > 47) && (this.pdfSource.peek() < 58))
/*      */               {
/* 2094 */                 this.pdfSource.seek(--tempOffset);
/* 2095 */                 length++;
/*      */               }
/* 2097 */               if (length > 0)
/*      */               {
/* 2099 */                 this.pdfSource.read();
/* 2100 */                 byte[] objIDBytes = this.pdfSource.readFully(length);
/* 2101 */                 String objIdString = new String(objIDBytes, 0, objIDBytes.length, "ISO-8859-1");
/*      */ 
/* 2103 */                 Long objectID = null;
/*      */                 try
/*      */                 {
/* 2106 */                   objectID = Long.valueOf(objIdString);
/*      */                 }
/*      */                 catch (NumberFormatException excpetion)
/*      */                 {
/* 2110 */                   objectID = null;
/*      */                 }
/* 2112 */                 if (objectID != null)
/*      */                 {
/* 2114 */                   this.bfSearchObjectOffsets.put(createObjectString(objectID.longValue(), genID), Long.valueOf(++tempOffset));
/*      */ 
/* 2116 */                   this.bfSearchCOSObjectKeyOffsets.put(new COSObjectKey(objectID.longValue(), genID), Long.valueOf(tempOffset));
/*      */                 }
/*      */               }
/*      */             }
/*      */           }
/*      */         }
/* 2122 */         currentOffset += 1L;
/* 2123 */       }while (!this.pdfSource.isEOF());
/*      */ 
/* 2125 */       this.pdfSource.seek(originOffset);
/*      */     }
/*      */   }
/*      */ 
/*      */   private long bfSearchForXRef(long xrefOffset)
/*      */     throws IOException
/*      */   {
/* 2137 */     long newOffset = -1L;
/* 2138 */     bfSearchForXRefs();
/* 2139 */     if (this.bfSearchXRefOffsets != null)
/*      */     {
/* 2141 */       long currentDifference = -1L;
/* 2142 */       int currentOffsetIndex = -1;
/* 2143 */       int numberOfOffsets = this.bfSearchXRefOffsets.size();
/*      */ 
/* 2146 */       for (int i = 0; i < numberOfOffsets; i++)
/*      */       {
/* 2148 */         long newDifference = xrefOffset - ((Long)this.bfSearchXRefOffsets.get(i)).longValue();
/*      */ 
/* 2150 */         if ((currentDifference == -1L) || (Math.abs(currentDifference) > Math.abs(newDifference)))
/*      */         {
/* 2152 */           currentDifference = newDifference;
/* 2153 */           currentOffsetIndex = i;
/*      */         }
/*      */       }
/* 2156 */       if (currentOffsetIndex > -1)
/*      */       {
/* 2158 */         newOffset = ((Long)this.bfSearchXRefOffsets.remove(currentOffsetIndex)).longValue();
/*      */       }
/*      */     }
/* 2161 */     return newOffset;
/*      */   }
/*      */ 
/*      */   private void bfSearchForXRefs()
/*      */     throws IOException
/*      */   {
/* 2171 */     if (this.bfSearchXRefOffsets == null)
/*      */     {
/* 2174 */       this.bfSearchXRefOffsets = new Vector();
/* 2175 */       long originOffset = this.pdfSource.getOffset();
/* 2176 */       this.pdfSource.seek(6L);
/*      */ 
/* 2178 */       while (!this.pdfSource.isEOF())
/*      */       {
/* 2180 */         if (checkBytesAtOffset(XREF_TABLE))
/*      */         {
/* 2182 */           long newOffset = this.pdfSource.getOffset();
/* 2183 */           this.pdfSource.seek(newOffset - 1L);
/*      */ 
/* 2185 */           if (isWhitespace())
/*      */           {
/* 2187 */             this.bfSearchXRefOffsets.add(Long.valueOf(newOffset));
/*      */           }
/* 2189 */           this.pdfSource.seek(newOffset + 4L);
/*      */         }
/* 2191 */         this.pdfSource.read();
/*      */       }
/* 2193 */       this.pdfSource.seek(6L);
/*      */ 
/* 2195 */       String objString = " obj";
/* 2196 */       byte[] string = objString.getBytes("ISO-8859-1");
/* 2197 */       while (!this.pdfSource.isEOF())
/*      */       {
/* 2199 */         if (checkBytesAtOffset(XREF_STREAM))
/*      */         {
/* 2202 */           long newOffset = -1L;
/* 2203 */           long xrefOffset = this.pdfSource.getOffset();
/* 2204 */           long currentOffset = xrefOffset;
/* 2205 */           boolean objFound = false;
/* 2206 */           for (int i = 1; (i < 30) && (!objFound); i++)
/*      */           {
/* 2208 */             currentOffset = xrefOffset - i * 10;
/* 2209 */             if (currentOffset > 0L)
/*      */             {
/* 2211 */               this.pdfSource.seek(currentOffset);
/* 2212 */               for (int j = 0; j < 10; j++)
/*      */               {
/* 2214 */                 if (checkBytesAtOffset(string))
/*      */                 {
/* 2216 */                   long tempOffset = currentOffset - 1L;
/* 2217 */                   this.pdfSource.seek(tempOffset);
/* 2218 */                   int genID = this.pdfSource.peek();
/*      */ 
/* 2220 */                   if ((genID > 47) && (genID < 58))
/*      */                   {
/* 2222 */                     genID -= 48;
/* 2223 */                     tempOffset -= 1L;
/* 2224 */                     this.pdfSource.seek(tempOffset);
/* 2225 */                     if (this.pdfSource.peek() == 32)
/*      */                     {
/* 2227 */                       int length = 0;
/* 2228 */                       this.pdfSource.seek(--tempOffset);
/*      */ 
/* 2230 */                       while ((tempOffset > 6L) && (this.pdfSource.peek() > 47) && (this.pdfSource.peek() < 58))
/*      */                       {
/* 2232 */                         this.pdfSource.seek(--tempOffset);
/* 2233 */                         length++;
/*      */                       }
/* 2235 */                       if (length > 0)
/*      */                       {
/* 2237 */                         this.pdfSource.read();
/* 2238 */                         newOffset = this.pdfSource.getOffset();
/*      */                       }
/*      */                     }
/*      */                   }
/* 2242 */                   LOG.debug("Fixed reference for xref stream " + xrefOffset + " -> " + newOffset);
/* 2243 */                   objFound = true;
/* 2244 */                   break;
/*      */                 }
/*      */ 
/* 2248 */                 currentOffset += 1L;
/* 2249 */                 this.pdfSource.read();
/*      */               }
/*      */             }
/*      */           }
/*      */ 
/* 2254 */           if (newOffset > -1L)
/*      */           {
/* 2256 */             this.bfSearchXRefOffsets.add(Long.valueOf(newOffset));
/*      */           }
/* 2258 */           this.pdfSource.seek(xrefOffset + 5L);
/*      */         }
/* 2260 */         this.pdfSource.read();
/*      */       }
/* 2262 */       this.pdfSource.seek(originOffset);
/*      */     }
/*      */   }
/*      */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.pdfparser.NonSequentialPDFParser
 * JD-Core Version:    0.6.2
 */