/*      */ package org.apache.pdfbox.pdfwriter;
/*      */ 
/*      */ import java.io.BufferedInputStream;
/*      */ import java.io.Closeable;
/*      */ import java.io.IOException;
/*      */ import java.io.InputStream;
/*      */ import java.io.OutputStream;
/*      */ import java.io.UnsupportedEncodingException;
/*      */ import java.security.MessageDigest;
/*      */ import java.security.NoSuchAlgorithmException;
/*      */ import java.text.DecimalFormat;
/*      */ import java.text.NumberFormat;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Collection;
/*      */ import java.util.Collections;
/*      */ import java.util.HashSet;
/*      */ import java.util.Hashtable;
/*      */ import java.util.Iterator;
/*      */ import java.util.LinkedList;
/*      */ import java.util.List;
/*      */ import java.util.Locale;
/*      */ import java.util.Map;
/*      */ import java.util.Map.Entry;
/*      */ import java.util.Set;
/*      */ import org.apache.pdfbox.cos.COSArray;
/*      */ import org.apache.pdfbox.cos.COSBase;
/*      */ import org.apache.pdfbox.cos.COSBoolean;
/*      */ import org.apache.pdfbox.cos.COSDictionary;
/*      */ import org.apache.pdfbox.cos.COSDocument;
/*      */ import org.apache.pdfbox.cos.COSFloat;
/*      */ import org.apache.pdfbox.cos.COSInteger;
/*      */ import org.apache.pdfbox.cos.COSName;
/*      */ import org.apache.pdfbox.cos.COSNull;
/*      */ import org.apache.pdfbox.cos.COSNumber;
/*      */ import org.apache.pdfbox.cos.COSObject;
/*      */ import org.apache.pdfbox.cos.COSStream;
/*      */ import org.apache.pdfbox.cos.COSString;
/*      */ import org.apache.pdfbox.cos.ICOSVisitor;
/*      */ import org.apache.pdfbox.exceptions.COSVisitorException;
/*      */ import org.apache.pdfbox.exceptions.CryptographyException;
/*      */ import org.apache.pdfbox.exceptions.SignatureException;
/*      */ import org.apache.pdfbox.pdfparser.PDFXRefStream;
/*      */ import org.apache.pdfbox.pdmodel.PDDocument;
/*      */ import org.apache.pdfbox.pdmodel.encryption.SecurityHandler;
/*      */ import org.apache.pdfbox.pdmodel.interactive.digitalsignature.SignatureInterface;
/*      */ import org.apache.pdfbox.persistence.util.COSObjectKey;
/*      */ import org.apache.pdfbox.util.StringUtil;
/*      */ 
/*      */ public class COSWriter
/*      */   implements ICOSVisitor, Closeable
/*      */ {
/*   80 */   public static final byte[] DICT_OPEN = StringUtil.getBytes("<<");
/*      */ 
/*   84 */   public static final byte[] DICT_CLOSE = StringUtil.getBytes(">>");
/*      */ 
/*   88 */   public static final byte[] SPACE = StringUtil.getBytes(" ");
/*      */ 
/*   92 */   public static final byte[] COMMENT = StringUtil.getBytes("%");
/*      */ 
/*   97 */   public static final byte[] VERSION = StringUtil.getBytes("PDF-1.4");
/*      */ 
/*  101 */   public static final byte[] GARBAGE = { -10, -28, -4, -33 };
/*      */ 
/*  105 */   public static final byte[] EOF = StringUtil.getBytes("%%EOF");
/*      */ 
/*  111 */   public static final byte[] REFERENCE = StringUtil.getBytes("R");
/*      */ 
/*  115 */   public static final byte[] XREF = StringUtil.getBytes("xref");
/*      */ 
/*  119 */   public static final byte[] XREF_FREE = StringUtil.getBytes("f");
/*      */ 
/*  123 */   public static final byte[] XREF_USED = StringUtil.getBytes("n");
/*      */ 
/*  127 */   public static final byte[] TRAILER = StringUtil.getBytes("trailer");
/*      */ 
/*  131 */   public static final byte[] STARTXREF = StringUtil.getBytes("startxref");
/*      */ 
/*  135 */   public static final byte[] OBJ = StringUtil.getBytes("obj");
/*      */ 
/*  139 */   public static final byte[] ENDOBJ = StringUtil.getBytes("endobj");
/*      */ 
/*  143 */   public static final byte[] ARRAY_OPEN = StringUtil.getBytes("[");
/*      */ 
/*  147 */   public static final byte[] ARRAY_CLOSE = StringUtil.getBytes("]");
/*      */ 
/*  151 */   public static final byte[] STREAM = StringUtil.getBytes("stream");
/*      */ 
/*  155 */   public static final byte[] ENDSTREAM = StringUtil.getBytes("endstream");
/*      */ 
/*  157 */   private NumberFormat formatXrefOffset = new DecimalFormat("0000000000");
/*      */ 
/*  161 */   private NumberFormat formatXrefGeneration = new DecimalFormat("00000");
/*      */ 
/*  163 */   private NumberFormat formatDecimal = NumberFormat.getNumberInstance(Locale.US);
/*      */   private OutputStream output;
/*      */   private COSStandardOutputStream standardOutput;
/*  172 */   private long startxref = 0L;
/*      */ 
/*  175 */   private long number = 0L;
/*      */ 
/*  181 */   private Map<COSBase, COSObjectKey> objectKeys = new Hashtable();
/*  182 */   private Map<COSObjectKey, COSBase> keyObject = new Hashtable();
/*      */ 
/*  185 */   private List<COSWriterXRefEntry> xRefEntries = new ArrayList();
/*  186 */   private HashSet<COSBase> objectsToWriteSet = new HashSet();
/*      */ 
/*  189 */   private LinkedList<COSBase> objectsToWrite = new LinkedList();
/*      */ 
/*  192 */   private Set<COSBase> writtenObjects = new HashSet();
/*      */ 
/*  199 */   private Set<COSBase> actualsAdded = new HashSet();
/*      */ 
/*  201 */   private COSObjectKey currentObjectKey = null;
/*      */ 
/*  203 */   private PDDocument document = null;
/*      */ 
/*  205 */   private boolean willEncrypt = false;
/*      */ 
/*  207 */   private boolean incrementalUpdate = false;
/*      */ 
/*  209 */   private boolean reachedSignature = false;
/*      */ 
/*  211 */   private int[] signaturePosition = new int[2];
/*      */ 
/*  213 */   private int[] byterangePosition = new int[2];
/*      */   private InputStream in;
/*      */ 
/*      */   public COSWriter(OutputStream os)
/*      */   {
/*  225 */     setOutput(os);
/*  226 */     setStandardOutput(new COSStandardOutputStream(this.output));
/*  227 */     this.formatDecimal.setMaximumFractionDigits(10);
/*  228 */     this.formatDecimal.setGroupingUsed(false);
/*      */   }
/*      */ 
/*      */   public COSWriter(OutputStream os, InputStream is)
/*      */   {
/*  239 */     this(os);
/*  240 */     this.in = is;
/*  241 */     this.incrementalUpdate = true;
/*      */   }
/*      */ 
/*      */   private void prepareIncrement(PDDocument doc)
/*      */   {
/*      */     try
/*      */     {
/*  248 */       if (doc != null)
/*      */       {
/*  250 */         COSDocument cosDoc = doc.getDocument();
/*      */ 
/*  252 */         Map xrefTable = cosDoc.getXrefTable();
/*  253 */         Set keySet = xrefTable.keySet();
/*  254 */         long highestNumber = 0L;
/*  255 */         for (COSObjectKey cosObjectKey : keySet)
/*      */         {
/*  257 */           COSBase object = cosDoc.getObjectFromPool(cosObjectKey).getObject();
/*  258 */           if ((object != null) && (cosObjectKey != null) && (!(object instanceof COSNumber)))
/*      */           {
/*  260 */             this.objectKeys.put(object, cosObjectKey);
/*  261 */             this.keyObject.put(cosObjectKey, object);
/*      */           }
/*      */ 
/*  264 */           long num = cosObjectKey.getNumber();
/*  265 */           if (num > highestNumber)
/*      */           {
/*  267 */             highestNumber = num;
/*      */           }
/*      */         }
/*  270 */         setNumber(highestNumber);
/*      */       }
/*      */ 
/*      */     }
/*      */     catch (IOException e)
/*      */     {
/*  277 */       e.printStackTrace();
/*      */     }
/*      */   }
/*      */ 
/*      */   protected void addXRefEntry(COSWriterXRefEntry entry)
/*      */   {
/*  288 */     getXRefEntries().add(entry);
/*      */   }
/*      */ 
/*      */   public void close()
/*      */     throws IOException
/*      */   {
/*  298 */     if (getStandardOutput() != null)
/*      */     {
/*  300 */       getStandardOutput().close();
/*      */     }
/*  302 */     if (getOutput() != null)
/*      */     {
/*  304 */       getOutput().close();
/*      */     }
/*      */   }
/*      */ 
/*      */   protected long getNumber()
/*      */   {
/*  315 */     return this.number;
/*      */   }
/*      */ 
/*      */   public Map<COSBase, COSObjectKey> getObjectKeys()
/*      */   {
/*  325 */     return this.objectKeys;
/*      */   }
/*      */ 
/*      */   protected OutputStream getOutput()
/*      */   {
/*  335 */     return this.output;
/*      */   }
/*      */ 
/*      */   protected COSStandardOutputStream getStandardOutput()
/*      */   {
/*  345 */     return this.standardOutput;
/*      */   }
/*      */ 
/*      */   protected long getStartxref()
/*      */   {
/*  355 */     return this.startxref;
/*      */   }
/*      */ 
/*      */   protected List<COSWriterXRefEntry> getXRefEntries()
/*      */   {
/*  364 */     return this.xRefEntries;
/*      */   }
/*      */ 
/*      */   protected void setNumber(long newNumber)
/*      */   {
/*  374 */     this.number = newNumber;
/*      */   }
/*      */ 
/*      */   private void setOutput(OutputStream newOutput)
/*      */   {
/*  384 */     this.output = newOutput;
/*      */   }
/*      */ 
/*      */   private void setStandardOutput(COSStandardOutputStream newStandardOutput)
/*      */   {
/*  394 */     this.standardOutput = newStandardOutput;
/*      */   }
/*      */ 
/*      */   protected void setStartxref(long newStartxref)
/*      */   {
/*  404 */     this.startxref = newStartxref;
/*      */   }
/*      */ 
/*      */   protected void doWriteBody(COSDocument doc)
/*      */     throws IOException, COSVisitorException
/*      */   {
/*  417 */     COSDictionary trailer = doc.getTrailer();
/*  418 */     COSDictionary root = (COSDictionary)trailer.getDictionaryObject(COSName.ROOT);
/*  419 */     COSDictionary info = (COSDictionary)trailer.getDictionaryObject(COSName.INFO);
/*  420 */     COSDictionary encrypt = (COSDictionary)trailer.getDictionaryObject(COSName.ENCRYPT);
/*  421 */     if (root != null)
/*      */     {
/*  423 */       addObjectToWrite(root);
/*      */     }
/*  425 */     if (info != null)
/*      */     {
/*  427 */       addObjectToWrite(info);
/*      */     }
/*      */ 
/*  430 */     while (this.objectsToWrite.size() > 0)
/*      */     {
/*  432 */       COSBase nextObject = (COSBase)this.objectsToWrite.removeFirst();
/*  433 */       this.objectsToWriteSet.remove(nextObject);
/*  434 */       doWriteObject(nextObject);
/*      */     }
/*      */ 
/*  438 */     this.willEncrypt = false;
/*      */ 
/*  440 */     if (encrypt != null)
/*      */     {
/*  442 */       addObjectToWrite(encrypt);
/*      */     }
/*      */ 
/*  445 */     while (this.objectsToWrite.size() > 0)
/*      */     {
/*  447 */       COSBase nextObject = (COSBase)this.objectsToWrite.removeFirst();
/*  448 */       this.objectsToWriteSet.remove(nextObject);
/*  449 */       doWriteObject(nextObject);
/*      */     }
/*      */   }
/*      */ 
/*      */   private void addObjectToWrite(COSBase object)
/*      */   {
/*  455 */     COSBase actual = object;
/*  456 */     if ((actual instanceof COSObject))
/*      */     {
/*  458 */       actual = ((COSObject)actual).getObject();
/*      */     }
/*      */ 
/*  461 */     if ((!this.writtenObjects.contains(object)) && (!this.objectsToWriteSet.contains(object)) && (!this.actualsAdded.contains(actual)))
/*      */     {
/*  465 */       COSBase cosBase = null;
/*  466 */       COSObjectKey cosObjectKey = null;
/*  467 */       if (actual != null)
/*      */       {
/*  469 */         cosObjectKey = (COSObjectKey)this.objectKeys.get(actual);
/*      */       }
/*  471 */       if (cosObjectKey != null)
/*      */       {
/*  473 */         cosBase = (COSBase)this.keyObject.get(cosObjectKey);
/*      */       }
/*  475 */       if ((actual != null) && (this.objectKeys.containsKey(actual)) && (!object.isNeedToBeUpdate()) && (cosBase != null) && (!cosBase.isNeedToBeUpdate()))
/*      */       {
/*  479 */         return;
/*      */       }
/*      */ 
/*  482 */       this.objectsToWrite.add(object);
/*  483 */       this.objectsToWriteSet.add(object);
/*  484 */       if (actual != null)
/*      */       {
/*  486 */         this.actualsAdded.add(actual);
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   public void doWriteObject(COSBase obj)
/*      */     throws COSVisitorException
/*      */   {
/*      */     try
/*      */     {
/*  502 */       this.writtenObjects.add(obj);
/*  503 */       if ((obj instanceof COSDictionary))
/*      */       {
/*  505 */         COSDictionary dict = (COSDictionary)obj;
/*  506 */         COSBase itemType = dict.getItem(COSName.TYPE);
/*  507 */         if ((itemType instanceof COSName))
/*      */         {
/*  509 */           COSName item = (COSName)itemType;
/*  510 */           if ((COSName.SIG.equals(item)) || (COSName.DOC_TIME_STAMP.equals(item)))
/*      */           {
/*  512 */             this.reachedSignature = true;
/*      */           }
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/*  518 */       this.currentObjectKey = getObjectKey(obj);
/*      */ 
/*  520 */       addXRefEntry(new COSWriterXRefEntry(getStandardOutput().getPos(), obj, this.currentObjectKey));
/*      */ 
/*  522 */       getStandardOutput().write(String.valueOf(this.currentObjectKey.getNumber()).getBytes("ISO-8859-1"));
/*  523 */       getStandardOutput().write(SPACE);
/*  524 */       getStandardOutput().write(String.valueOf(this.currentObjectKey.getGeneration()).getBytes("ISO-8859-1"));
/*  525 */       getStandardOutput().write(SPACE);
/*  526 */       getStandardOutput().write(OBJ);
/*  527 */       getStandardOutput().writeEOL();
/*  528 */       obj.accept(this);
/*  529 */       getStandardOutput().writeEOL();
/*  530 */       getStandardOutput().write(ENDOBJ);
/*  531 */       getStandardOutput().writeEOL();
/*      */     }
/*      */     catch (IOException e)
/*      */     {
/*  535 */       throw new COSVisitorException(e);
/*      */     }
/*      */   }
/*      */ 
/*      */   protected void doWriteHeader(COSDocument doc)
/*      */     throws IOException
/*      */   {
/*  548 */     getStandardOutput().write(doc.getHeaderString().getBytes("ISO-8859-1"));
/*  549 */     getStandardOutput().writeEOL();
/*  550 */     getStandardOutput().write(COMMENT);
/*  551 */     getStandardOutput().write(GARBAGE);
/*  552 */     getStandardOutput().writeEOL();
/*      */   }
/*      */ 
/*      */   protected void doWriteTrailer(COSDocument doc)
/*      */     throws IOException, COSVisitorException
/*      */   {
/*  566 */     getStandardOutput().write(TRAILER);
/*  567 */     getStandardOutput().writeEOL();
/*      */ 
/*  569 */     COSDictionary trailer = doc.getTrailer();
/*      */ 
/*  571 */     Collections.sort(getXRefEntries());
/*  572 */     COSWriterXRefEntry lastEntry = (COSWriterXRefEntry)getXRefEntries().get(getXRefEntries().size() - 1);
/*  573 */     trailer.setInt(COSName.SIZE, (int)lastEntry.getKey().getNumber() + 1);
/*      */ 
/*  575 */     if (!this.incrementalUpdate)
/*      */     {
/*  577 */       trailer.removeItem(COSName.PREV);
/*      */     }
/*  579 */     if (!doc.isXRefStream())
/*      */     {
/*  581 */       trailer.removeItem(COSName.XREF_STM);
/*      */     }
/*      */ 
/*  584 */     trailer.removeItem(COSName.DOC_CHECKSUM);
/*      */ 
/*  586 */     trailer.accept(this);
/*      */   }
/*      */ 
/*      */   protected void doWriteXRef(COSDocument doc)
/*      */     throws IOException
/*      */   {
/*      */     long lastObjectNumber;
/*      */     Iterator i;
/*  602 */     if (doc.isXRefStream())
/*      */     {
/*  605 */       Collections.sort(getXRefEntries());
/*  606 */       COSWriterXRefEntry lastEntry = (COSWriterXRefEntry)getXRefEntries().get(getXRefEntries().size() - 1);
/*      */ 
/*  609 */       setStartxref(getStandardOutput().getPos());
/*      */ 
/*  611 */       getStandardOutput().write(XREF);
/*  612 */       getStandardOutput().writeEOL();
/*      */ 
/*  615 */       writeXrefRange(0L, lastEntry.getKey().getNumber() + 1L);
/*      */ 
/*  617 */       writeXrefEntry(COSWriterXRefEntry.getNullEntry());
/*      */ 
/*  619 */       lastObjectNumber = 0L;
/*  620 */       for (i = getXRefEntries().iterator(); i.hasNext(); )
/*      */       {
/*  622 */         COSWriterXRefEntry entry = (COSWriterXRefEntry)i.next();
/*  623 */         while (lastObjectNumber < entry.getKey().getNumber() - 1L)
/*      */         {
/*  625 */           writeXrefEntry(COSWriterXRefEntry.getNullEntry());
/*      */         }
/*  627 */         lastObjectNumber = entry.getKey().getNumber();
/*  628 */         writeXrefEntry(entry);
/*      */       }
/*      */ 
/*      */     }
/*      */     else
/*      */     {
/*  634 */       COSDictionary trailer = doc.getTrailer();
/*  635 */       trailer.setLong(COSName.PREV, doc.getStartXref());
/*  636 */       addXRefEntry(COSWriterXRefEntry.getNullEntry());
/*      */ 
/*  639 */       Collections.sort(getXRefEntries());
/*      */ 
/*  642 */       setStartxref(getStandardOutput().getPos());
/*      */ 
/*  644 */       getStandardOutput().write(XREF);
/*  645 */       getStandardOutput().writeEOL();
/*      */ 
/*  649 */       Integer[] xRefRanges = getXRefRanges(getXRefEntries());
/*  650 */       int xRefLength = xRefRanges.length;
/*  651 */       int x = 0;
/*  652 */       int j = 0;
/*  653 */       while ((x < xRefLength) && (xRefLength % 2 == 0))
/*      */       {
/*  655 */         writeXrefRange(xRefRanges[x].intValue(), xRefRanges[(x + 1)].intValue());
/*      */ 
/*  657 */         for (int i = 0; i < xRefRanges[(x + 1)].intValue(); i++)
/*      */         {
/*  659 */           writeXrefEntry((COSWriterXRefEntry)this.xRefEntries.get(j++));
/*      */         }
/*  661 */         x += 2;
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   private void doWriteXRefInc(COSDocument doc, long hybridPrev) throws IOException, COSVisitorException
/*      */   {
/*  668 */     if ((doc.isXRefStream()) || (hybridPrev != -1L))
/*      */     {
/*  678 */       PDFXRefStream pdfxRefStream = new PDFXRefStream();
/*      */ 
/*  681 */       List xRefEntries2 = getXRefEntries();
/*  682 */       for (COSWriterXRefEntry cosWriterXRefEntry : xRefEntries2)
/*      */       {
/*  684 */         pdfxRefStream.addEntry(cosWriterXRefEntry);
/*      */       }
/*      */ 
/*  687 */       COSDictionary trailer = doc.getTrailer();
/*      */ 
/*  689 */       trailer.setLong(COSName.PREV, doc.getStartXref());
/*      */ 
/*  691 */       pdfxRefStream.addTrailerInfo(trailer);
/*      */ 
/*  694 */       pdfxRefStream.setSize(getNumber() + 2L);
/*      */ 
/*  696 */       setStartxref(getStandardOutput().getPos());
/*  697 */       COSStream stream2 = pdfxRefStream.getStream();
/*  698 */       doWriteObject(stream2);
/*      */     }
/*      */ 
/*  701 */     if ((!doc.isXRefStream()) || (hybridPrev != -1L))
/*      */     {
/*  703 */       COSDictionary trailer = doc.getTrailer();
/*  704 */       trailer.setLong(COSName.PREV, doc.getStartXref());
/*  705 */       if (hybridPrev != -1L)
/*      */       {
/*  707 */         COSName xrefStm = COSName.XREF_STM;
/*  708 */         trailer.removeItem(xrefStm);
/*  709 */         trailer.setLong(xrefStm, getStartxref());
/*      */       }
/*  711 */       addXRefEntry(COSWriterXRefEntry.getNullEntry());
/*      */ 
/*  714 */       Collections.sort(getXRefEntries());
/*      */ 
/*  717 */       setStartxref(getStandardOutput().getPos());
/*      */ 
/*  719 */       getStandardOutput().write(XREF);
/*  720 */       getStandardOutput().writeEOL();
/*      */ 
/*  724 */       Integer[] xRefRanges = getXRefRanges(getXRefEntries());
/*  725 */       int xRefLength = xRefRanges.length;
/*  726 */       int x = 0;
/*  727 */       int j = 0;
/*  728 */       while ((x < xRefLength) && (xRefLength % 2 == 0))
/*      */       {
/*  730 */         writeXrefRange(xRefRanges[x].intValue(), xRefRanges[(x + 1)].intValue());
/*      */ 
/*  732 */         for (int i = 0; i < xRefRanges[(x + 1)].intValue(); i++)
/*      */         {
/*  734 */           writeXrefEntry((COSWriterXRefEntry)this.xRefEntries.get(j++));
/*      */         }
/*  736 */         x += 2;
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   private void doWriteSignature(COSDocument doc)
/*      */     throws IOException, SignatureException
/*      */   {
/*  744 */     if ((this.signaturePosition[0] > 0) && (this.byterangePosition[1] > 0))
/*      */     {
/*  746 */       int left = (int)getStandardOutput().getPos() - this.signaturePosition[1];
/*  747 */       String newByteRange = "0 " + this.signaturePosition[0] + " " + this.signaturePosition[1] + " " + left + "]";
/*  748 */       int leftByterange = this.byterangePosition[1] - this.byterangePosition[0] - newByteRange.length();
/*  749 */       if (leftByterange < 0)
/*      */       {
/*  751 */         throw new IOException("Can't write new ByteRange, not enough space");
/*      */       }
/*  753 */       getStandardOutput().setPos(this.byterangePosition[0]);
/*  754 */       getStandardOutput().write(newByteRange.getBytes());
/*  755 */       for (int i = 0; i < leftByterange; i++)
/*      */       {
/*  757 */         getStandardOutput().write(32);
/*      */       }
/*      */ 
/*  760 */       getStandardOutput().setPos(0L);
/*      */ 
/*  762 */       InputStream filterInputStream = null;
/*      */       try
/*      */       {
/*  765 */         filterInputStream = new COSFilterInputStream(new BufferedInputStream(this.in), new int[] { 0, this.signaturePosition[0], this.signaturePosition[1], left });
/*  766 */         SignatureInterface signatureInterface = doc.getSignatureInterface();
/*  767 */         byte[] sign = signatureInterface.sign(filterInputStream);
/*  768 */         String signature = new COSString(sign).getHexString();
/*  769 */         int startPos = this.signaturePosition[0] + 1;
/*  770 */         int endPos = this.signaturePosition[1] - 1;
/*  771 */         if (startPos + signature.length() > endPos)
/*      */         {
/*  773 */           throw new IOException("Can't write signature, not enough space");
/*      */         }
/*  775 */         getStandardOutput().setPos(startPos);
/*  776 */         getStandardOutput().write(signature.getBytes());
/*      */       }
/*      */       finally
/*      */       {
/*  780 */         if (filterInputStream != null)
/*      */         {
/*  782 */           filterInputStream.close();
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   private void writeXrefRange(long x, long y) throws IOException
/*      */   {
/*  790 */     getStandardOutput().write(String.valueOf(x).getBytes());
/*  791 */     getStandardOutput().write(SPACE);
/*  792 */     getStandardOutput().write(String.valueOf(y).getBytes());
/*  793 */     getStandardOutput().writeEOL();
/*      */   }
/*      */ 
/*      */   private void writeXrefEntry(COSWriterXRefEntry entry) throws IOException
/*      */   {
/*  798 */     String offset = this.formatXrefOffset.format(entry.getOffset());
/*  799 */     String generation = this.formatXrefGeneration.format(entry.getKey().getGeneration());
/*  800 */     getStandardOutput().write(offset.getBytes("ISO-8859-1"));
/*  801 */     getStandardOutput().write(SPACE);
/*  802 */     getStandardOutput().write(generation.getBytes("ISO-8859-1"));
/*  803 */     getStandardOutput().write(SPACE);
/*  804 */     getStandardOutput().write(entry.isFree() ? XREF_FREE : XREF_USED);
/*  805 */     getStandardOutput().writeCRLF();
/*      */   }
/*      */ 
/*      */   protected Integer[] getXRefRanges(List<COSWriterXRefEntry> xRefEntriesList)
/*      */   {
/*  828 */     int nr = 0;
/*  829 */     int last = -2;
/*  830 */     int count = 1;
/*      */ 
/*  832 */     ArrayList list = new ArrayList();
/*  833 */     for (Object object : xRefEntriesList)
/*      */     {
/*  835 */       nr = (int)((COSWriterXRefEntry)object).getKey().getNumber();
/*  836 */       if (nr == last + 1)
/*      */       {
/*  838 */         count++;
/*  839 */         last = nr;
/*      */       }
/*  841 */       else if (last == -2)
/*      */       {
/*  843 */         last = nr;
/*      */       }
/*      */       else
/*      */       {
/*  847 */         list.add(Integer.valueOf(last - count + 1));
/*  848 */         list.add(Integer.valueOf(count));
/*  849 */         last = nr;
/*  850 */         count = 1;
/*      */       }
/*      */     }
/*      */ 
/*  854 */     if (xRefEntriesList.size() > 0)
/*      */     {
/*  856 */       list.add(Integer.valueOf(last - count + 1));
/*  857 */       list.add(Integer.valueOf(count));
/*      */     }
/*  859 */     return (Integer[])list.toArray(new Integer[list.size()]);
/*      */   }
/*      */ 
/*      */   private COSObjectKey getObjectKey(COSBase obj)
/*      */   {
/*  871 */     COSBase actual = obj;
/*  872 */     if ((actual instanceof COSObject))
/*      */     {
/*  874 */       actual = ((COSObject)obj).getObject();
/*      */     }
/*  876 */     COSObjectKey key = null;
/*  877 */     if (actual != null)
/*      */     {
/*  879 */       key = (COSObjectKey)this.objectKeys.get(actual);
/*      */     }
/*  881 */     if (key == null)
/*      */     {
/*  883 */       key = (COSObjectKey)this.objectKeys.get(obj);
/*      */     }
/*  885 */     if (key == null)
/*      */     {
/*  887 */       setNumber(getNumber() + 1L);
/*  888 */       key = new COSObjectKey(getNumber(), 0L);
/*  889 */       this.objectKeys.put(obj, key);
/*  890 */       if (actual != null)
/*      */       {
/*  892 */         this.objectKeys.put(actual, key);
/*      */       }
/*      */     }
/*  895 */     return key;
/*      */   }
/*      */ 
/*      */   public Object visitFromArray(COSArray obj)
/*      */     throws COSVisitorException
/*      */   {
/*      */     try
/*      */     {
/*  911 */       int count = 0;
/*  912 */       getStandardOutput().write(ARRAY_OPEN);
/*  913 */       for (Iterator i = obj.iterator(); i.hasNext(); )
/*      */       {
/*  915 */         COSBase current = (COSBase)i.next();
/*  916 */         if ((current instanceof COSDictionary))
/*      */         {
/*  918 */           if (current.isDirect())
/*      */           {
/*  920 */             visitFromDictionary((COSDictionary)current);
/*      */           }
/*      */           else
/*      */           {
/*  924 */             addObjectToWrite(current);
/*  925 */             writeReference(current);
/*      */           }
/*      */         }
/*  928 */         else if ((current instanceof COSObject))
/*      */         {
/*  930 */           COSBase subValue = ((COSObject)current).getObject();
/*  931 */           if (((subValue instanceof COSDictionary)) || (subValue == null))
/*      */           {
/*  933 */             addObjectToWrite(current);
/*  934 */             writeReference(current);
/*      */           }
/*      */           else
/*      */           {
/*  938 */             subValue.accept(this);
/*      */           }
/*      */         }
/*  941 */         else if (current == null)
/*      */         {
/*  943 */           COSNull.NULL.accept(this);
/*      */         }
/*  945 */         else if ((current instanceof COSString))
/*      */         {
/*  947 */           COSString copy = new COSString();
/*  948 */           copy.append(((COSString)current).getBytes());
/*  949 */           copy.accept(this);
/*      */         }
/*      */         else
/*      */         {
/*  953 */           current.accept(this);
/*      */         }
/*  955 */         count++;
/*  956 */         if (i.hasNext())
/*      */         {
/*  958 */           if (count % 10 == 0)
/*      */           {
/*  960 */             getStandardOutput().writeEOL();
/*      */           }
/*      */           else
/*      */           {
/*  964 */             getStandardOutput().write(SPACE);
/*      */           }
/*      */         }
/*      */       }
/*  968 */       getStandardOutput().write(ARRAY_CLOSE);
/*  969 */       getStandardOutput().writeEOL();
/*  970 */       return null;
/*      */     }
/*      */     catch (IOException e)
/*      */     {
/*  974 */       throw new COSVisitorException(e);
/*      */     }
/*      */   }
/*      */ 
/*      */   public Object visitFromBoolean(COSBoolean obj)
/*      */     throws COSVisitorException
/*      */   {
/*      */     try
/*      */     {
/*  992 */       obj.writePDF(getStandardOutput());
/*  993 */       return null;
/*      */     }
/*      */     catch (IOException e)
/*      */     {
/*  997 */       throw new COSVisitorException(e);
/*      */     }
/*      */   }
/*      */ 
/*      */   public Object visitFromDictionary(COSDictionary obj)
/*      */     throws COSVisitorException
/*      */   {
/*      */     try
/*      */     {
/* 1014 */       getStandardOutput().write(DICT_OPEN);
/* 1015 */       getStandardOutput().writeEOL();
/* 1016 */       for (Map.Entry entry : obj.entrySet())
/*      */       {
/* 1018 */         COSBase value = (COSBase)entry.getValue();
/* 1019 */         if (value != null)
/*      */         {
/* 1021 */           ((COSName)entry.getKey()).accept(this);
/* 1022 */           getStandardOutput().write(SPACE);
/* 1023 */           if ((value instanceof COSDictionary))
/*      */           {
/* 1025 */             COSDictionary dict = (COSDictionary)value;
/*      */ 
/* 1028 */             COSBase item = dict.getItem(COSName.XOBJECT);
/* 1029 */             if (item != null)
/*      */             {
/* 1031 */               item.setDirect(true);
/*      */             }
/* 1033 */             item = dict.getItem(COSName.RESOURCES);
/* 1034 */             if (item != null)
/*      */             {
/* 1036 */               item.setDirect(true);
/*      */             }
/*      */ 
/* 1039 */             if (dict.isDirect())
/*      */             {
/* 1043 */               visitFromDictionary(dict);
/*      */             }
/*      */             else
/*      */             {
/* 1047 */               addObjectToWrite(dict);
/* 1048 */               writeReference(dict);
/*      */             }
/*      */           }
/* 1051 */           else if ((value instanceof COSObject))
/*      */           {
/* 1053 */             COSBase subValue = ((COSObject)value).getObject();
/* 1054 */             if (((subValue instanceof COSDictionary)) || (subValue == null))
/*      */             {
/* 1056 */               addObjectToWrite(value);
/* 1057 */               writeReference(value);
/*      */             }
/*      */             else
/*      */             {
/* 1061 */               subValue.accept(this);
/*      */             }
/*      */ 
/*      */           }
/* 1068 */           else if ((this.reachedSignature) && (COSName.CONTENTS.equals(entry.getKey())))
/*      */           {
/* 1070 */             this.signaturePosition = new int[2];
/* 1071 */             this.signaturePosition[0] = ((int)getStandardOutput().getPos());
/* 1072 */             value.accept(this);
/* 1073 */             this.signaturePosition[1] = ((int)getStandardOutput().getPos());
/*      */           }
/* 1075 */           else if ((this.reachedSignature) && (COSName.BYTERANGE.equals(entry.getKey())))
/*      */           {
/* 1077 */             this.byterangePosition = new int[2];
/* 1078 */             this.byterangePosition[0] = ((int)getStandardOutput().getPos() + 1);
/* 1079 */             value.accept(this);
/* 1080 */             this.byterangePosition[1] = ((int)getStandardOutput().getPos() - 1);
/* 1081 */             this.reachedSignature = false;
/*      */           }
/*      */           else
/*      */           {
/* 1085 */             value.accept(this);
/*      */           }
/*      */ 
/* 1088 */           getStandardOutput().writeEOL();
/*      */         }
/*      */ 
/*      */       }
/*      */ 
/* 1099 */       getStandardOutput().write(DICT_CLOSE);
/* 1100 */       getStandardOutput().writeEOL();
/* 1101 */       return null;
/*      */     }
/*      */     catch (IOException e)
/*      */     {
/* 1105 */       throw new COSVisitorException(e);
/*      */     }
/*      */   }
/*      */ 
/*      */   public Object visitFromDocument(COSDocument doc)
/*      */     throws COSVisitorException
/*      */   {
/*      */     try
/*      */     {
/* 1122 */       if (!this.incrementalUpdate)
/*      */       {
/* 1124 */         doWriteHeader(doc);
/*      */       }
/* 1126 */       doWriteBody(doc);
/*      */ 
/* 1129 */       COSDictionary trailer = doc.getTrailer();
/* 1130 */       long hybridPrev = -1L;
/*      */ 
/* 1132 */       if (trailer != null)
/*      */       {
/* 1134 */         hybridPrev = trailer.getLong(COSName.XREF_STM);
/*      */       }
/*      */ 
/* 1137 */       if (this.incrementalUpdate)
/*      */       {
/* 1139 */         doWriteXRefInc(doc, hybridPrev);
/*      */       }
/*      */       else
/*      */       {
/* 1143 */         doWriteXRef(doc);
/*      */       }
/*      */ 
/* 1147 */       if ((!this.incrementalUpdate) || (!doc.isXRefStream()) || (hybridPrev != -1L))
/*      */       {
/* 1149 */         doWriteTrailer(doc);
/*      */       }
/*      */ 
/* 1153 */       getStandardOutput().write(STARTXREF);
/* 1154 */       getStandardOutput().writeEOL();
/* 1155 */       getStandardOutput().write(String.valueOf(getStartxref()).getBytes("ISO-8859-1"));
/* 1156 */       getStandardOutput().writeEOL();
/* 1157 */       getStandardOutput().write(EOF);
/* 1158 */       getStandardOutput().writeEOL();
/*      */ 
/* 1160 */       if (this.incrementalUpdate)
/*      */       {
/* 1162 */         doWriteSignature(doc);
/*      */       }
/*      */ 
/* 1165 */       return null;
/*      */     }
/*      */     catch (IOException e)
/*      */     {
/* 1169 */       throw new COSVisitorException(e);
/*      */     }
/*      */     catch (SignatureException e)
/*      */     {
/* 1173 */       throw new COSVisitorException(e);
/*      */     }
/*      */   }
/*      */ 
/*      */   public Object visitFromFloat(COSFloat obj)
/*      */     throws COSVisitorException
/*      */   {
/*      */     try
/*      */     {
/* 1191 */       obj.writePDF(getStandardOutput());
/* 1192 */       return null;
/*      */     }
/*      */     catch (IOException e)
/*      */     {
/* 1196 */       throw new COSVisitorException(e);
/*      */     }
/*      */   }
/*      */ 
/*      */   public Object visitFromInt(COSInteger obj)
/*      */     throws COSVisitorException
/*      */   {
/*      */     try
/*      */     {
/* 1213 */       obj.writePDF(getStandardOutput());
/* 1214 */       return null;
/*      */     }
/*      */     catch (IOException e)
/*      */     {
/* 1218 */       throw new COSVisitorException(e);
/*      */     }
/*      */   }
/*      */ 
/*      */   public Object visitFromName(COSName obj)
/*      */     throws COSVisitorException
/*      */   {
/*      */     try
/*      */     {
/* 1235 */       obj.writePDF(getStandardOutput());
/* 1236 */       return null;
/*      */     }
/*      */     catch (IOException e)
/*      */     {
/* 1240 */       throw new COSVisitorException(e);
/*      */     }
/*      */   }
/*      */ 
/*      */   public Object visitFromNull(COSNull obj)
/*      */     throws COSVisitorException
/*      */   {
/*      */     try
/*      */     {
/* 1257 */       obj.writePDF(getStandardOutput());
/* 1258 */       return null;
/*      */     }
/*      */     catch (IOException e)
/*      */     {
/* 1262 */       throw new COSVisitorException(e);
/*      */     }
/*      */   }
/*      */ 
/*      */   public void writeReference(COSBase obj)
/*      */     throws COSVisitorException
/*      */   {
/*      */     try
/*      */     {
/* 1277 */       COSObjectKey key = getObjectKey(obj);
/* 1278 */       getStandardOutput().write(String.valueOf(key.getNumber()).getBytes("ISO-8859-1"));
/* 1279 */       getStandardOutput().write(SPACE);
/* 1280 */       getStandardOutput().write(String.valueOf(key.getGeneration()).getBytes("ISO-8859-1"));
/* 1281 */       getStandardOutput().write(SPACE);
/* 1282 */       getStandardOutput().write(REFERENCE);
/*      */     }
/*      */     catch (IOException e)
/*      */     {
/* 1286 */       throw new COSVisitorException(e);
/*      */     }
/*      */   }
/*      */ 
/*      */   public Object visitFromStream(COSStream obj)
/*      */     throws COSVisitorException
/*      */   {
/* 1301 */     InputStream input = null;
/*      */     try
/*      */     {
/* 1304 */       if (this.willEncrypt)
/*      */       {
/* 1306 */         this.document.getSecurityHandler().encryptStream(obj, this.currentObjectKey.getNumber(), this.currentObjectKey.getGeneration());
/*      */       }
/*      */ 
/* 1310 */       COSObject lengthObject = null;
/*      */ 
/* 1313 */       COSBase lengthEntry = obj.getDictionaryObject(COSName.LENGTH);
/* 1314 */       String type = obj.getNameAsString(COSName.TYPE);
/* 1315 */       if (((lengthEntry != null) && (lengthEntry.isDirect())) || ("XRef".equals(type)))
/*      */       {
/* 1319 */         COSInteger cosInteger = COSInteger.get(obj.getFilteredLength());
/* 1320 */         cosInteger.setDirect(true);
/* 1321 */         obj.setItem(COSName.LENGTH, cosInteger);
/*      */       }
/*      */       else
/*      */       {
/* 1328 */         lengthObject = new COSObject(null);
/*      */ 
/* 1330 */         obj.setItem(COSName.LENGTH, lengthObject);
/*      */       }
/* 1332 */       input = obj.getFilteredStream();
/*      */ 
/* 1335 */       visitFromDictionary(obj);
/* 1336 */       getStandardOutput().write(STREAM);
/* 1337 */       getStandardOutput().writeCRLF();
/* 1338 */       byte[] buffer = new byte[1024];
/* 1339 */       int amountRead = 0;
/* 1340 */       int totalAmountWritten = 0;
/* 1341 */       while ((amountRead = input.read(buffer, 0, 1024)) != -1)
/*      */       {
/* 1343 */         getStandardOutput().write(buffer, 0, amountRead);
/* 1344 */         totalAmountWritten += amountRead;
/*      */       }
/*      */ 
/* 1347 */       if (lengthObject != null)
/*      */       {
/* 1349 */         lengthObject.setObject(COSInteger.get(totalAmountWritten));
/*      */       }
/* 1351 */       getStandardOutput().writeCRLF();
/* 1352 */       getStandardOutput().write(ENDSTREAM);
/* 1353 */       getStandardOutput().writeEOL();
/* 1354 */       return null;
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 1358 */       throw new COSVisitorException(e);
/*      */     }
/*      */     finally
/*      */     {
/* 1362 */       if (input != null)
/*      */       {
/*      */         try
/*      */         {
/* 1366 */           input.close();
/*      */         }
/*      */         catch (IOException e)
/*      */         {
/* 1370 */           throw new COSVisitorException(e);
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   public Object visitFromString(COSString obj)
/*      */     throws COSVisitorException
/*      */   {
/*      */     try
/*      */     {
/* 1389 */       if (this.willEncrypt)
/*      */       {
/* 1391 */         this.document.getSecurityHandler().encryptString(obj, this.currentObjectKey.getNumber(), this.currentObjectKey.getGeneration());
/*      */       }
/*      */ 
/* 1397 */       obj.writePDF(getStandardOutput());
/*      */     }
/*      */     catch (Exception e)
/*      */     {
/* 1401 */       throw new COSVisitorException(e);
/*      */     }
/* 1403 */     return null;
/*      */   }
/*      */ 
/*      */   public void write(COSDocument doc)
/*      */     throws COSVisitorException
/*      */   {
/* 1415 */     PDDocument pdDoc = new PDDocument(doc);
/* 1416 */     write(pdDoc);
/*      */   }
/*      */ 
/*      */   public void write(PDDocument doc)
/*      */     throws COSVisitorException
/*      */   {
/* 1428 */     Long idTime = Long.valueOf(doc.getDocumentId() == null ? System.currentTimeMillis() : doc.getDocumentId().longValue());
/*      */ 
/* 1431 */     this.document = doc;
/* 1432 */     if (this.incrementalUpdate)
/*      */     {
/* 1434 */       prepareIncrement(doc);
/*      */     }
/*      */ 
/* 1438 */     if (doc.isAllSecurityToBeRemoved())
/*      */     {
/* 1440 */       this.willEncrypt = false;
/*      */ 
/* 1443 */       COSDocument cosDoc = doc.getDocument();
/* 1444 */       COSDictionary trailer = cosDoc.getTrailer();
/* 1445 */       trailer.removeItem(COSName.ENCRYPT);
/*      */     }
/*      */     else
/*      */     {
/* 1449 */       SecurityHandler securityHandler = this.document.getSecurityHandler();
/* 1450 */       if (securityHandler != null)
/*      */       {
/*      */         try
/*      */         {
/* 1454 */           securityHandler.prepareDocumentForEncryption(this.document);
/* 1455 */           this.willEncrypt = true;
/*      */         }
/*      */         catch (IOException e)
/*      */         {
/* 1459 */           throw new COSVisitorException(e);
/*      */         }
/*      */         catch (CryptographyException e)
/*      */         {
/* 1463 */           throw new COSVisitorException(e);
/*      */         }
/*      */       }
/*      */       else
/*      */       {
/* 1468 */         this.willEncrypt = false;
/*      */       }
/*      */     }
/*      */ 
/* 1472 */     COSDocument cosDoc = this.document.getDocument();
/* 1473 */     COSDictionary trailer = cosDoc.getTrailer();
/* 1474 */     COSArray idArray = (COSArray)trailer.getDictionaryObject(COSName.ID);
/* 1475 */     boolean missingID = true;
/*      */ 
/* 1477 */     if ((idArray != null) && (idArray.size() == 2))
/*      */     {
/* 1479 */       missingID = false;
/*      */     }
/* 1481 */     if ((missingID) || (this.incrementalUpdate))
/*      */     {
/*      */       try
/*      */       {
/* 1488 */         MessageDigest md = MessageDigest.getInstance("MD5");
/* 1489 */         md.update(Long.toString(idTime.longValue()).getBytes("ISO-8859-1"));
/* 1490 */         COSDictionary info = (COSDictionary)trailer.getDictionaryObject(COSName.INFO);
/* 1491 */         if (info != null)
/*      */         {
/* 1493 */           Iterator values = info.getValues().iterator();
/* 1494 */           while (values.hasNext())
/*      */           {
/* 1496 */             md.update(((COSBase)values.next()).toString().getBytes("ISO-8859-1"));
/*      */           }
/*      */         }
/*      */ 
/* 1500 */         COSString firstID = missingID ? new COSString(md.digest()) : (COSString)idArray.get(0);
/* 1501 */         COSString secondID = new COSString(md.digest());
/* 1502 */         idArray = new COSArray();
/* 1503 */         idArray.add(firstID);
/* 1504 */         idArray.add(secondID);
/* 1505 */         trailer.setItem(COSName.ID, idArray);
/*      */       }
/*      */       catch (NoSuchAlgorithmException e)
/*      */       {
/* 1509 */         throw new COSVisitorException(e);
/*      */       }
/*      */       catch (UnsupportedEncodingException e)
/*      */       {
/* 1513 */         throw new COSVisitorException(e);
/*      */       }
/*      */     }
/* 1516 */     cosDoc.accept(this);
/*      */   }
/*      */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.pdfwriter.COSWriter
 * JD-Core Version:    0.6.2
 */