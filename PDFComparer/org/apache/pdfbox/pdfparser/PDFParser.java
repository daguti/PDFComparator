/*      */ package org.apache.pdfbox.pdfparser;
/*      */ 
/*      */ import java.io.File;
/*      */ import java.io.IOException;
/*      */ import java.io.InputStream;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Collection;
/*      */ import java.util.HashSet;
/*      */ import java.util.Iterator;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.regex.Matcher;
/*      */ import java.util.regex.Pattern;
/*      */ import org.apache.commons.logging.Log;
/*      */ import org.apache.commons.logging.LogFactory;
/*      */ import org.apache.pdfbox.cos.COSBase;
/*      */ import org.apache.pdfbox.cos.COSDictionary;
/*      */ import org.apache.pdfbox.cos.COSDocument;
/*      */ import org.apache.pdfbox.cos.COSInteger;
/*      */ import org.apache.pdfbox.cos.COSName;
/*      */ import org.apache.pdfbox.cos.COSNumber;
/*      */ import org.apache.pdfbox.cos.COSObject;
/*      */ import org.apache.pdfbox.cos.COSStream;
/*      */ import org.apache.pdfbox.exceptions.WrappedIOException;
/*      */ import org.apache.pdfbox.io.PushBackInputStream;
/*      */ import org.apache.pdfbox.io.RandomAccess;
/*      */ import org.apache.pdfbox.pdmodel.PDDocument;
/*      */ import org.apache.pdfbox.pdmodel.fdf.FDFDocument;
/*      */ import org.apache.pdfbox.persistence.util.COSObjectKey;
/*      */ 
/*      */ public class PDFParser extends BaseParser
/*      */ {
/*   57 */   private static final Log LOG = LogFactory.getLog(PDFParser.class);
/*      */   private static final int SPACE_BYTE = 32;
/*      */   private static final String PDF_HEADER = "%PDF-";
/*      */   private static final String FDF_HEADER = "%FDF-";
/*   64 */   protected boolean isFDFDocment = false;
/*      */   private static final String PDF_DEFAULT_VERSION = "1.4";
/*      */   private static final String FDF_DEFAULT_VERSION = "1.0";
/*   73 */   private List<ConflictObj> conflictList = new ArrayList();
/*      */ 
/*   78 */   private final HashSet<COSStream> streamLengthCheckSet = new HashSet();
/*      */ 
/*   83 */   protected XrefTrailerResolver xrefTrailerResolver = new XrefTrailerResolver();
/*      */ 
/*   88 */   private File tempDirectory = null;
/*      */ 
/*   90 */   private RandomAccess raf = null;
/*      */ 
/*      */   public PDFParser(InputStream input)
/*      */     throws IOException
/*      */   {
/*  101 */     this(input, null, FORCE_PARSING);
/*      */   }
/*      */ 
/*      */   public PDFParser(InputStream input, RandomAccess rafi)
/*      */     throws IOException
/*      */   {
/*  113 */     this(input, rafi, FORCE_PARSING);
/*      */   }
/*      */ 
/*      */   public PDFParser(InputStream input, RandomAccess rafi, boolean force)
/*      */     throws IOException
/*      */   {
/*  128 */     super(input, force);
/*  129 */     this.raf = rafi;
/*      */   }
/*      */ 
/*      */   public void setTempDirectory(File tmpDir)
/*      */   {
/*  142 */     this.tempDirectory = tmpDir;
/*      */   }
/*      */ 
/*      */   protected boolean isContinueOnError(Exception e)
/*      */   {
/*  155 */     return this.forceParsing;
/*      */   }
/*      */ 
/*      */   public void parse()
/*      */     throws IOException
/*      */   {
/*      */     try
/*      */     {
/*  169 */       if (this.raf == null)
/*      */       {
/*  171 */         if (this.tempDirectory != null)
/*      */         {
/*  173 */           this.document = new COSDocument(this.tempDirectory);
/*      */         }
/*      */         else
/*      */         {
/*  177 */           this.document = new COSDocument();
/*      */         }
/*      */       }
/*      */       else
/*      */       {
/*  182 */         this.document = new COSDocument(this.raf);
/*      */       }
/*  184 */       setDocument(this.document);
/*      */ 
/*  186 */       parseHeader();
/*      */ 
/*  190 */       skipToNextObj();
/*      */ 
/*  192 */       boolean wasLastParsedObjectEOF = false;
/*      */ 
/*  195 */       while (!this.pdfSource.isEOF())
/*      */       {
/*      */         try
/*      */         {
/*  203 */           wasLastParsedObjectEOF |= parseObject();
/*      */         }
/*      */         catch (IOException e)
/*      */         {
/*  211 */           if (!wasLastParsedObjectEOF) break label110;
/*      */         }
/*  213 */         break;
/*      */ 
/*  215 */         label110: if (isContinueOnError(e))
/*      */         {
/*  221 */           LOG.warn("Parsing Error, Skipping Object", e);
/*      */ 
/*  223 */           skipSpaces();
/*  224 */           long lastOffset = this.pdfSource.getOffset();
/*  225 */           skipToNextObj();
/*      */ 
/*  231 */           if (lastOffset == this.pdfSource.getOffset()) {
/*  232 */             readStringNumber();
/*  233 */             skipToNextObj();
/*      */           }
/*      */         }
/*      */         else
/*      */         {
/*  238 */           throw e;
/*      */         }
/*      */ 
/*  241 */         skipSpaces();
/*      */       }
/*      */ 
/*  245 */       this.xrefTrailerResolver.setStartxref(this.document.getStartXref());
/*      */ 
/*  248 */       this.document.setTrailer(this.xrefTrailerResolver.getTrailer());
/*  249 */       this.document.addXRefTable(this.xrefTrailerResolver.getXrefTable());
/*      */ 
/*  251 */       fixStreamsLength();
/*      */ 
/*  253 */       if (!this.document.isEncrypted())
/*      */       {
/*  255 */         this.document.dereferenceObjectStreams();
/*      */       }
/*      */       else
/*      */       {
/*  259 */         LOG.info("Document is encrypted");
/*      */       }
/*  261 */       ConflictObj.resolveConflicts(this.document, this.conflictList);
/*      */     }
/*      */     catch (Throwable t)
/*      */     {
/*  267 */       if (this.document != null)
/*      */       {
/*  269 */         this.document.close();
/*  270 */         this.document = null;
/*      */       }
/*  272 */       if ((t instanceof IOException))
/*      */       {
/*  274 */         throw ((IOException)t);
/*      */       }
/*      */ 
/*  278 */       throw new WrappedIOException(t);
/*      */     }
/*      */     finally
/*      */     {
/*  283 */       this.pdfSource.close();
/*      */     }
/*      */   }
/*      */ 
/*      */   private void fixStreamsLength()
/*      */     throws IOException
/*      */   {
/*  295 */     for (COSObject obj : this.document.getObjects())
/*      */     {
/*  297 */       if (((obj.getObject() instanceof COSStream)) && (this.streamLengthCheckSet.contains((COSStream)obj.getObject())))
/*      */       {
/*  300 */         COSStream stream = (COSStream)obj.getObject();
/*      */ 
/*  302 */         long filteredLength = stream.getFilteredLength();
/*  303 */         long filteredLengthWritten = stream.getFilteredLengthWritten();
/*  304 */         if (Math.abs(filteredLength - filteredLengthWritten) > 2L)
/*      */         {
/*  308 */           LOG.warn("/Length of " + obj + " corrected from " + filteredLength + " to " + filteredLengthWritten);
/*  309 */           stream.setLong(COSName.LENGTH, filteredLengthWritten);
/*  310 */           stream.setFilteredLength(filteredLengthWritten);
/*      */         }
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   private void skipToNextObj()
/*      */     throws IOException
/*      */   {
/*  326 */     byte[] b = new byte[16];
/*  327 */     Pattern p = Pattern.compile("\\d+\\s+\\d+\\s+obj.*", 32);
/*      */ 
/*  333 */     while (!this.pdfSource.isEOF())
/*      */     {
/*  335 */       int l = this.pdfSource.read(b);
/*  336 */       if (l < 1)
/*      */       {
/*      */         break;
/*      */       }
/*  340 */       String s = new String(b, "US-ASCII");
/*  341 */       if ((s.startsWith("trailer")) || (s.startsWith("xref")) || (s.startsWith("startxref")) || (s.startsWith("stream")) || (p.matcher(s).matches()))
/*      */       {
/*  347 */         this.pdfSource.unread(b);
/*  348 */         break;
/*      */       }
/*      */ 
/*  352 */       this.pdfSource.unread(b, 1, l - 1);
/*      */     }
/*      */   }
/*      */ 
/*      */   protected void parseHeader()
/*      */     throws IOException
/*      */   {
/*  360 */     String header = readLine();
/*      */ 
/*  362 */     if ((!header.contains("%PDF-")) && (!header.contains("%FDF-")))
/*      */     {
/*  364 */       header = readLine();
/*  365 */       while ((!header.contains("%PDF-")) && (!header.contains("%FDF-")))
/*      */       {
/*  368 */         if ((header.length() > 0) && (Character.isDigit(header.charAt(0))))
/*      */         {
/*      */           break;
/*      */         }
/*  372 */         header = readLine();
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/*  377 */     if ((header.indexOf("%PDF-") == -1) && (header.indexOf("%FDF-") == -1))
/*      */     {
/*  379 */       throw new IOException("Error: Header doesn't contain versioninfo");
/*      */     }
/*      */ 
/*  384 */     int headerStart = header.indexOf("%PDF-");
/*  385 */     if (headerStart == -1)
/*      */     {
/*  387 */       headerStart = header.indexOf("%FDF-");
/*      */     }
/*      */ 
/*  392 */     if (headerStart > 0)
/*      */     {
/*  395 */       header = header.substring(headerStart, header.length());
/*      */     }
/*      */ 
/*  401 */     if (header.startsWith("%PDF-"))
/*      */     {
/*  403 */       if (!header.matches("%PDF-\\d.\\d"))
/*      */       {
/*  406 */         if (header.length() < "%PDF-".length() + 3)
/*      */         {
/*  409 */           header = "%PDF-1.4";
/*  410 */           LOG.debug("No pdf version found, set to 1.4 as default.");
/*      */         }
/*      */         else
/*      */         {
/*  414 */           String headerGarbage = header.substring("%PDF-".length() + 3, header.length()) + "\n";
/*  415 */           header = header.substring(0, "%PDF-".length() + 3);
/*  416 */           this.pdfSource.unread(headerGarbage.getBytes("ISO-8859-1"));
/*      */         }
/*      */       }
/*      */     }
/*      */     else
/*      */     {
/*  422 */       this.isFDFDocment = true;
/*  423 */       if (!header.matches("%FDF-\\d.\\d"))
/*      */       {
/*  425 */         if (header.length() < "%FDF-".length() + 3)
/*      */         {
/*  428 */           header = "%FDF-1.0";
/*  429 */           LOG.debug("No fdf version found, set to 1.0 as default.");
/*      */         }
/*      */         else
/*      */         {
/*  433 */           String headerGarbage = header.substring("%FDF-".length() + 3, header.length()) + "\n";
/*  434 */           header = header.substring(0, "%FDF-".length() + 3);
/*  435 */           this.pdfSource.unread(headerGarbage.getBytes("ISO-8859-1"));
/*      */         }
/*      */       }
/*      */     }
/*  439 */     this.document.setHeaderString(header);
/*      */     try
/*      */     {
/*  443 */       if (header.startsWith("%PDF-"))
/*      */       {
/*  445 */         float pdfVersion = Float.parseFloat(header.substring("%PDF-".length(), Math.min(header.length(), "%PDF-".length() + 3)));
/*      */ 
/*  447 */         this.document.setVersion(pdfVersion);
/*      */       }
/*      */       else
/*      */       {
/*  451 */         float pdfVersion = Float.parseFloat(header.substring("%FDF-".length(), Math.min(header.length(), "%FDF-".length() + 3)));
/*      */ 
/*  453 */         this.document.setVersion(pdfVersion);
/*      */       }
/*      */     }
/*      */     catch (NumberFormatException e)
/*      */     {
/*  458 */       throw new IOException("Error getting pdf version:" + e);
/*      */     }
/*      */   }
/*      */ 
/*      */   public COSDocument getDocument()
/*      */     throws IOException
/*      */   {
/*  473 */     if (this.document == null)
/*      */     {
/*  475 */       throw new IOException("You must call parse() before calling getDocument()");
/*      */     }
/*  477 */     return this.document;
/*      */   }
/*      */ 
/*      */   public PDDocument getPDDocument()
/*      */     throws IOException
/*      */   {
/*  490 */     return new PDDocument(getDocument(), this);
/*      */   }
/*      */ 
/*      */   public FDFDocument getFDFDocument()
/*      */     throws IOException
/*      */   {
/*  503 */     return new FDFDocument(getDocument());
/*      */   }
/*      */ 
/*      */   private boolean parseObject()
/*      */     throws IOException
/*      */   {
/*  516 */     long currentObjByteOffset = this.pdfSource.getOffset();
/*  517 */     boolean isEndOfFile = false;
/*  518 */     skipSpaces();
/*      */ 
/*  520 */     char peekedChar = (char)this.pdfSource.peek();
/*      */ 
/*  523 */     while (peekedChar == 'e')
/*      */     {
/*  527 */       readString();
/*  528 */       skipSpaces();
/*  529 */       currentObjByteOffset = this.pdfSource.getOffset();
/*  530 */       peekedChar = (char)this.pdfSource.peek();
/*      */     }
/*  532 */     if (!this.pdfSource.isEOF())
/*      */     {
/*  538 */       if (peekedChar == 'x')
/*      */       {
/*  540 */         parseXrefTable(currentObjByteOffset);
/*      */       }
/*  543 */       else if ((peekedChar == 't') || (peekedChar == 's'))
/*      */       {
/*  545 */         if (peekedChar == 't')
/*      */         {
/*  547 */           parseTrailer();
/*  548 */           peekedChar = (char)this.pdfSource.peek();
/*      */         }
/*  550 */         if (peekedChar == 's')
/*      */         {
/*  552 */           parseStartXref();
/*      */ 
/*  555 */           while ((isWhitespace(this.pdfSource.peek())) && (!this.pdfSource.isEOF()))
/*      */           {
/*  557 */             this.pdfSource.read();
/*      */           }
/*  559 */           String eof = "";
/*  560 */           if (!this.pdfSource.isEOF())
/*      */           {
/*  562 */             eof = readLine();
/*      */           }
/*      */ 
/*  566 */           if (!"%%EOF".equals(eof))
/*      */           {
/*  568 */             if (eof.startsWith("%%EOF"))
/*      */             {
/*  571 */               this.pdfSource.unread(32);
/*  572 */               this.pdfSource.unread(eof.substring(5).getBytes("ISO-8859-1"));
/*      */             }
/*      */             else
/*      */             {
/*  577 */               LOG.warn("expected='%%EOF' actual='" + eof + "'");
/*      */ 
/*  579 */               if (!this.pdfSource.isEOF())
/*      */               {
/*  581 */                 this.pdfSource.unread(32);
/*  582 */                 this.pdfSource.unread(eof.getBytes("ISO-8859-1"));
/*      */               }
/*      */             }
/*      */           }
/*  586 */           isEndOfFile = true;
/*      */         }
/*      */ 
/*      */       }
/*      */       else
/*      */       {
/*  592 */         long number = -1L;
/*      */ 
/*  595 */         boolean missingObjectNumber = false;
/*      */         try
/*      */         {
/*  598 */           char peeked = (char)this.pdfSource.peek();
/*  599 */           if (peeked == '<')
/*      */           {
/*  601 */             missingObjectNumber = true;
/*      */           }
/*      */           else
/*      */           {
/*  605 */             number = readObjectNumber();
/*      */           }
/*      */ 
/*      */         }
/*      */         catch (IOException e)
/*      */         {
/*  614 */           number = readObjectNumber();
/*      */         }
/*      */         int genNum;
/*  616 */         if (!missingObjectNumber)
/*      */         {
/*  618 */           skipSpaces();
/*  619 */           int genNum = readGenerationNumber();
/*      */ 
/*  621 */           String objectKey = readString(3);
/*      */ 
/*  624 */           if (!objectKey.equals("obj"))
/*      */           {
/*  626 */             if ((!isContinueOnError(null)) || (!objectKey.equals("o")))
/*      */             {
/*  628 */               throw new IOException("expected='obj' actual='" + objectKey + "' " + this.pdfSource);
/*      */             }
/*      */ 
/*      */           }
/*      */ 
/*      */         }
/*      */         else
/*      */         {
/*  636 */           number = -1L;
/*  637 */           genNum = -1;
/*      */         }
/*      */ 
/*  640 */         skipSpaces();
/*  641 */         COSBase pb = parseDirObject();
/*  642 */         String endObjectKey = readString();
/*      */ 
/*  644 */         if (endObjectKey.equals("stream"))
/*      */         {
/*  646 */           this.pdfSource.unread(endObjectKey.getBytes("ISO-8859-1"));
/*  647 */           this.pdfSource.unread(32);
/*  648 */           if ((pb instanceof COSDictionary))
/*      */           {
/*  650 */             pb = parseCOSStream((COSDictionary)pb, getDocument().getScratchFile());
/*      */ 
/*  653 */             COSStream strmObj = (COSStream)pb;
/*      */ 
/*  656 */             COSBase streamLength = strmObj.getItem(COSName.LENGTH);
/*  657 */             int length = -1;
/*  658 */             if ((streamLength instanceof COSNumber))
/*      */             {
/*  660 */               length = ((COSNumber)streamLength).intValue();
/*      */             }
/*  662 */             if (length == -1)
/*      */             {
/*  664 */               this.streamLengthCheckSet.add(strmObj);
/*      */             }
/*      */ 
/*  667 */             COSName objectType = (COSName)strmObj.getItem(COSName.TYPE);
/*  668 */             if ((objectType != null) && (objectType.equals(COSName.XREF)))
/*      */             {
/*  671 */               parseXrefStream(strmObj, currentObjByteOffset);
/*      */             }
/*      */ 
/*      */           }
/*      */           else
/*      */           {
/*  678 */             throw new IOException("stream not preceded by dictionary");
/*      */           }
/*  680 */           skipSpaces();
/*  681 */           endObjectKey = readLine();
/*      */         }
/*      */ 
/*  684 */         COSObjectKey key = new COSObjectKey(number, genNum);
/*  685 */         COSObject pdfObject = this.document.getObjectFromPool(key);
/*  686 */         if (pdfObject.getObject() == null)
/*      */         {
/*  688 */           pdfObject.setObject(pb);
/*      */         }
/*      */         else
/*      */         {
/*  696 */           addObjectToConflicts(currentObjByteOffset, key, pb);
/*      */         }
/*      */ 
/*  699 */         if (!endObjectKey.equals("endobj"))
/*      */         {
/*  701 */           if (endObjectKey.startsWith("endobj"))
/*      */           {
/*  709 */             this.pdfSource.unread(32);
/*  710 */             this.pdfSource.unread(endObjectKey.substring(6).getBytes("ISO-8859-1"));
/*      */           }
/*  712 */           else if (endObjectKey.trim().endsWith("endobj"))
/*      */           {
/*  719 */             LOG.warn("expected='endobj' actual='" + endObjectKey + "' ");
/*      */           }
/*  721 */           else if (!this.pdfSource.isEOF())
/*      */           {
/*  726 */             this.pdfSource.unread(32);
/*  727 */             this.pdfSource.unread(endObjectKey.getBytes("ISO-8859-1"));
/*      */           }
/*      */         }
/*  730 */         skipSpaces();
/*      */       }
/*      */     }
/*  732 */     return isEndOfFile;
/*      */   }
/*      */ 
/*      */   private void addObjectToConflicts(long offset, COSObjectKey key, COSBase pb)
/*      */     throws IOException
/*      */   {
/*  744 */     COSObject obj = new COSObject(null);
/*  745 */     obj.setObjectNumber(COSInteger.get(key.getNumber()));
/*  746 */     obj.setGenerationNumber(COSInteger.get(key.getGeneration()));
/*  747 */     obj.setObject(pb);
/*  748 */     ConflictObj conflictObj = new ConflictObj(offset, key, obj);
/*  749 */     this.conflictList.add(conflictObj);
/*      */   }
/*      */ 
/*      */   protected boolean parseStartXref()
/*      */     throws IOException
/*      */   {
/*  761 */     if (this.pdfSource.peek() != 115)
/*      */     {
/*  763 */       return false;
/*      */     }
/*  765 */     String startXRef = readString();
/*  766 */     if (!startXRef.trim().equals("startxref"))
/*      */     {
/*  768 */       return false;
/*      */     }
/*  770 */     skipSpaces();
/*      */ 
/*  774 */     getDocument().setStartXref(readLong());
/*  775 */     return true;
/*      */   }
/*      */ 
/*      */   protected boolean parseXrefTable(long startByteOffset)
/*      */     throws IOException
/*      */   {
/*  788 */     if (this.pdfSource.peek() != 120)
/*      */     {
/*  790 */       return false;
/*      */     }
/*  792 */     String xref = readString();
/*  793 */     if (!xref.trim().equals("xref"))
/*      */     {
/*  795 */       return false;
/*      */     }
/*      */ 
/*  799 */     String str = readString();
/*  800 */     byte[] b = str.getBytes("ISO-8859-1");
/*  801 */     this.pdfSource.unread(b, 0, b.length);
/*      */ 
/*  804 */     this.xrefTrailerResolver.nextXrefObj(startByteOffset);
/*      */ 
/*  806 */     if (str.startsWith("trailer"))
/*      */     {
/*  808 */       LOG.warn("skipping empty xref table");
/*  809 */       return false;
/*      */     }
/*      */ 
/*      */     while (true)
/*      */     {
/*  818 */       long currObjID = readObjectNumber();
/*  819 */       long count = readLong();
/*  820 */       skipSpaces();
/*  821 */       for (int i = 0; i < count; i++)
/*      */       {
/*  823 */         if ((this.pdfSource.isEOF()) || (isEndOfName((char)this.pdfSource.peek())))
/*      */         {
/*      */           break;
/*      */         }
/*  827 */         if (this.pdfSource.peek() == 116)
/*      */         {
/*      */           break;
/*      */         }
/*      */ 
/*  832 */         String currentLine = readLine();
/*  833 */         String[] splitString = currentLine.split("\\s");
/*  834 */         if (splitString.length < 3)
/*      */         {
/*  836 */           LOG.warn("invalid xref line: " + currentLine);
/*  837 */           break;
/*      */         }
/*      */ 
/*  841 */         if (splitString[(splitString.length - 1)].equals("n"))
/*      */         {
/*      */           try
/*      */           {
/*  845 */             long currOffset = Long.parseLong(splitString[0]);
/*  846 */             int currGenID = Integer.parseInt(splitString[1]);
/*  847 */             COSObjectKey objKey = new COSObjectKey(currObjID, currGenID);
/*  848 */             this.xrefTrailerResolver.setXRef(objKey, currOffset);
/*      */           }
/*      */           catch (NumberFormatException e)
/*      */           {
/*  852 */             throw new IOException(e.getMessage());
/*      */           }
/*      */         }
/*  855 */         else if (!splitString[2].equals("f"))
/*      */         {
/*  857 */           throw new IOException("Corrupt XRefTable Entry - ObjID:" + currObjID);
/*      */         }
/*  859 */         currObjID += 1L;
/*  860 */         skipSpaces();
/*      */       }
/*  862 */       skipSpaces();
/*  863 */       char c = (char)this.pdfSource.peek();
/*  864 */       if ((c < '0') || (c > '9'))
/*      */       {
/*      */         break;
/*      */       }
/*      */     }
/*  869 */     return true;
/*      */   }
/*      */ 
/*      */   protected boolean parseTrailer()
/*      */     throws IOException
/*      */   {
/*  880 */     if (this.pdfSource.peek() != 116)
/*      */     {
/*  882 */       return false;
/*      */     }
/*      */ 
/*  885 */     String nextLine = readLine();
/*  886 */     if (!nextLine.trim().equals("trailer"))
/*      */     {
/*  892 */       if (nextLine.startsWith("trailer"))
/*      */       {
/*  894 */         byte[] b = nextLine.getBytes("ISO-8859-1");
/*  895 */         int len = "trailer".length();
/*  896 */         this.pdfSource.unread(10);
/*  897 */         this.pdfSource.unread(b, len, b.length - len);
/*      */       }
/*      */       else
/*      */       {
/*  901 */         return false;
/*      */       }
/*      */ 
/*      */     }
/*      */ 
/*  908 */     skipSpaces();
/*      */ 
/*  910 */     COSDictionary parsedTrailer = parseCOSDictionary();
/*  911 */     this.xrefTrailerResolver.setTrailer(parsedTrailer);
/*      */ 
/*  914 */     readVersionInTrailer(parsedTrailer);
/*      */ 
/*  916 */     skipSpaces();
/*  917 */     return true;
/*      */   }
/*      */ 
/*      */   protected void readVersionInTrailer(COSDictionary parsedTrailer)
/*      */   {
/*  928 */     COSObject root = (COSObject)parsedTrailer.getItem(COSName.ROOT);
/*  929 */     if (root != null)
/*      */     {
/*  931 */       COSBase item = root.getItem(COSName.VERSION);
/*  932 */       if ((item instanceof COSName))
/*      */       {
/*  934 */         COSName version = (COSName)item;
/*  935 */         float trailerVersion = Float.valueOf(version.getName()).floatValue();
/*  936 */         if (trailerVersion > this.document.getVersion())
/*      */         {
/*  938 */           this.document.setVersion(trailerVersion);
/*      */         }
/*      */       }
/*  941 */       else if (item != null)
/*      */       {
/*  943 */         LOG.warn("Incorrect /Version entry is ignored: " + item);
/*      */       }
/*      */     }
/*      */   }
/*      */ 
/*      */   public void parseXrefStream(COSStream stream, long objByteOffset)
/*      */     throws IOException
/*      */   {
/*  957 */     parseXrefStream(stream, objByteOffset, true);
/*      */   }
/*      */ 
/*      */   public void parseXrefStream(COSStream stream, long objByteOffset, boolean isStandalone)
/*      */     throws IOException
/*      */   {
/*  972 */     if (isStandalone)
/*      */     {
/*  974 */       this.xrefTrailerResolver.nextXrefObj(objByteOffset);
/*  975 */       this.xrefTrailerResolver.setTrailer(stream);
/*      */     }
/*  977 */     PDFXrefStreamParser parser = new PDFXrefStreamParser(stream, this.document, this.forceParsing, this.xrefTrailerResolver);
/*      */ 
/*  979 */     parser.parse();
/*      */   }
/*      */ 
/*      */   private static boolean tolerantConflicResolver(Collection<Long> values, long offset, int tolerance)
/*      */   {
/* 1060 */     if (values.contains(Long.valueOf(offset)))
/*      */     {
/* 1062 */       return true;
/*      */     }
/*      */ 
/* 1066 */     for (Long integer : values)
/*      */     {
/* 1068 */       if (Math.abs(integer.longValue() - offset) <= tolerance)
/*      */       {
/* 1070 */         return true;
/*      */       }
/*      */     }
/*      */ 
/* 1074 */     return false;
/*      */   }
/*      */ 
/*      */   public void clearResources()
/*      */   {
/* 1083 */     super.clearResources();
/* 1084 */     if (this.conflictList != null)
/*      */     {
/* 1086 */       this.conflictList.clear();
/* 1087 */       this.conflictList = null;
/*      */     }
/* 1089 */     if (this.xrefTrailerResolver != null)
/*      */     {
/* 1091 */       this.xrefTrailerResolver.clearResources();
/* 1092 */       this.xrefTrailerResolver = null;
/*      */     }
/*      */   }
/*      */ 
/*      */   private static class ConflictObj
/*      */   {
/*      */     private final long offset;
/*      */     private final COSObjectKey objectKey;
/*      */     private final COSObject object;
/*      */ 
/*      */     ConflictObj(long offsetValue, COSObjectKey key, COSObject pdfObject)
/*      */     {
/* 1000 */       this.offset = offsetValue;
/* 1001 */       this.objectKey = key;
/* 1002 */       this.object = pdfObject;
/*      */     }
/*      */ 
/*      */     public String toString()
/*      */     {
/* 1008 */       return "Object(" + this.offset + ", " + this.objectKey + ")";
/*      */     }
/*      */ 
/*      */     private static void resolveConflicts(COSDocument document, List<ConflictObj> conflictList)
/*      */       throws IOException
/*      */     {
/* 1021 */       Iterator conflicts = conflictList.iterator();
/* 1022 */       if (conflicts.hasNext())
/*      */       {
/* 1024 */         Collection values = document.getXrefTable().values();
/*      */         do
/*      */         {
/* 1027 */           ConflictObj o = (ConflictObj)conflicts.next();
/* 1028 */           if (PDFParser.tolerantConflicResolver(values, o.offset, 4))
/*      */           {
/* 1030 */             COSObject pdfObject = document.getObjectFromPool(o.objectKey);
/* 1031 */             if ((pdfObject.getObjectNumber() != null) && (pdfObject.getObjectNumber().equals(o.object.getObjectNumber())))
/*      */             {
/* 1034 */               pdfObject.setObject(o.object.getObject());
/*      */             }
/*      */             else
/*      */             {
/* 1038 */               PDFParser.LOG.debug("Conflict object [" + o.objectKey + "] at offset " + o.offset + " found in the xref table, but the object numbers differ. Ignoring this object." + " The document is maybe malformed.");
/*      */             }
/*      */           }
/*      */ 
/*      */         }
/*      */ 
/* 1044 */         while (conflicts.hasNext());
/*      */       }
/*      */     }
/*      */   }
/*      */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.pdfparser.PDFParser
 * JD-Core Version:    0.6.2
 */