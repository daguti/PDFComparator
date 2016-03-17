/*     */ package org.apache.pdfbox.preflight.parser;
/*     */ 
/*     */ import java.io.BufferedReader;
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStreamReader;
/*     */ import java.nio.charset.Charset;
/*     */ import java.util.Arrays;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Map.Entry;
/*     */ import java.util.Set;
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
/*     */ import javax.activation.DataSource;
/*     */ import javax.activation.FileDataSource;
/*     */ import org.apache.pdfbox.cos.COSArray;
/*     */ import org.apache.pdfbox.cos.COSBase;
/*     */ import org.apache.pdfbox.cos.COSDictionary;
/*     */ import org.apache.pdfbox.cos.COSDocument;
/*     */ import org.apache.pdfbox.cos.COSFloat;
/*     */ import org.apache.pdfbox.cos.COSName;
/*     */ import org.apache.pdfbox.cos.COSNull;
/*     */ import org.apache.pdfbox.cos.COSNumber;
/*     */ import org.apache.pdfbox.cos.COSObject;
/*     */ import org.apache.pdfbox.cos.COSStream;
/*     */ import org.apache.pdfbox.cos.COSString;
/*     */ import org.apache.pdfbox.exceptions.CryptographyException;
/*     */ import org.apache.pdfbox.io.IOUtils;
/*     */ import org.apache.pdfbox.io.PushBackInputStream;
/*     */ import org.apache.pdfbox.io.RandomAccess;
/*     */ import org.apache.pdfbox.pdfparser.NonSequentialPDFParser;
/*     */ import org.apache.pdfbox.pdfparser.PDFObjectStreamParser;
/*     */ import org.apache.pdfbox.pdfparser.XrefTrailerResolver;
/*     */ import org.apache.pdfbox.pdmodel.PDDocument;
/*     */ import org.apache.pdfbox.pdmodel.encryption.SecurityHandler;
/*     */ import org.apache.pdfbox.persistence.util.COSObjectKey;
/*     */ import org.apache.pdfbox.preflight.Format;
/*     */ import org.apache.pdfbox.preflight.PreflightConfiguration;
/*     */ import org.apache.pdfbox.preflight.PreflightContext;
/*     */ import org.apache.pdfbox.preflight.PreflightDocument;
/*     */ import org.apache.pdfbox.preflight.ValidationResult;
/*     */ import org.apache.pdfbox.preflight.ValidationResult.ValidationError;
/*     */ import org.apache.pdfbox.preflight.exception.SyntaxValidationException;
/*     */ 
/*     */ public class PreflightParser extends NonSequentialPDFParser
/*     */ {
/*  96 */   public static final Charset encoding = Charset.forName("ISO-8859-1");
/*     */   protected DataSource originalDocument;
/*     */   protected ValidationResult validationResult;
/*     */   protected PreflightDocument preflightDocument;
/*     */   protected PreflightContext ctx;
/*     */ 
/*     */   public PreflightParser(File file, RandomAccess rafi)
/*     */     throws IOException
/*     */   {
/* 108 */     super(file, rafi);
/* 109 */     setLenient(false);
/* 110 */     this.originalDocument = new FileDataSource(file);
/*     */   }
/*     */ 
/*     */   public PreflightParser(File file) throws IOException
/*     */   {
/* 115 */     this(file, null);
/*     */   }
/*     */ 
/*     */   public PreflightParser(String filename) throws IOException
/*     */   {
/* 120 */     this(new File(filename), null);
/*     */   }
/*     */ 
/*     */   public PreflightParser(DataSource input) throws IOException
/*     */   {
/* 125 */     super(input.getInputStream());
/* 126 */     setLenient(false);
/* 127 */     this.originalDocument = input;
/*     */   }
/*     */ 
/*     */   protected static ValidationResult createUnknownErrorResult()
/*     */   {
/* 137 */     ValidationResult.ValidationError error = new ValidationResult.ValidationError("-1");
/* 138 */     ValidationResult result = new ValidationResult(error);
/* 139 */     return result;
/*     */   }
/*     */ 
/*     */   protected void addValidationError(ValidationResult.ValidationError error)
/*     */   {
/* 150 */     if (this.validationResult == null)
/*     */     {
/* 152 */       this.validationResult = new ValidationResult(error.isWarning());
/*     */     }
/* 154 */     this.validationResult.addError(error);
/*     */   }
/*     */ 
/*     */   protected void addValidationErrors(List<ValidationResult.ValidationError> errors)
/*     */   {
/* 159 */     for (ValidationResult.ValidationError error : errors)
/*     */     {
/* 161 */       addValidationError(error);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void parse()
/*     */     throws IOException
/*     */   {
/* 168 */     parse(Format.PDF_A1B);
/*     */   }
/*     */ 
/*     */   public void parse(Format format)
/*     */     throws IOException
/*     */   {
/* 180 */     parse(format, null);
/*     */   }
/*     */ 
/*     */   public void parse(Format format, PreflightConfiguration config)
/*     */     throws IOException
/*     */   {
/* 195 */     checkPdfHeader();
/*     */     try
/*     */     {
/* 198 */       super.parse();
/*     */     }
/*     */     catch (IOException e)
/*     */     {
/* 202 */       addValidationError(new ValidationResult.ValidationError("1.0", e.getMessage()));
/* 203 */       throw new SyntaxValidationException(e, this.validationResult);
/*     */     }
/* 205 */     Format formatToUse = format == null ? Format.PDF_A1B : format;
/* 206 */     createPdfADocument(formatToUse, config);
/* 207 */     createContext();
/*     */   }
/*     */ 
/*     */   protected void createPdfADocument(Format format, PreflightConfiguration config) throws IOException
/*     */   {
/* 212 */     COSDocument cosDocument = getDocument();
/* 213 */     this.preflightDocument = new PreflightDocument(cosDocument, format, config);
/*     */   }
/*     */ 
/*     */   protected void createContext()
/*     */   {
/* 221 */     this.ctx = new PreflightContext(this.originalDocument);
/* 222 */     this.ctx.setDocument(this.preflightDocument);
/* 223 */     this.preflightDocument.setContext(this.ctx);
/* 224 */     this.ctx.setXrefTableResolver(this.xrefTrailerResolver);
/*     */   }
/*     */ 
/*     */   public PDDocument getPDDocument()
/*     */     throws IOException
/*     */   {
/* 230 */     this.preflightDocument.setResult(this.validationResult);
/*     */ 
/* 232 */     return this.preflightDocument;
/*     */   }
/*     */ 
/*     */   public PreflightDocument getPreflightDocument() throws IOException
/*     */   {
/* 237 */     return (PreflightDocument)getPDDocument();
/*     */   }
/*     */ 
/*     */   protected void initialParse()
/*     */     throws IOException
/*     */   {
/* 250 */     super.initialParse();
/*     */ 
/* 253 */     this.document.addXRefTable(this.xrefTrailerResolver.getXrefTable());
/*     */ 
/* 256 */     for (COSBase trailerEntry : getDocument().getTrailer().getValues())
/*     */     {
/* 258 */       if ((trailerEntry instanceof COSObject))
/*     */       {
/* 260 */         COSObject tmpObj = (COSObject)trailerEntry;
/* 261 */         parseObjectDynamically(tmpObj, true);
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 266 */     Map xrefTable = this.document.getXrefTable();
/* 267 */     for (Map.Entry entry : xrefTable.entrySet())
/*     */     {
/* 269 */       COSObject co = this.document.getObjectFromPool((COSObjectKey)entry.getKey());
/* 270 */       if (co.getObject() == null)
/*     */       {
/* 273 */         parseObjectDynamically(co, true);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void checkPdfHeader()
/*     */   {
/* 285 */     BufferedReader reader = null;
/*     */     try
/*     */     {
/* 288 */       reader = new BufferedReader(new InputStreamReader(new FileInputStream(getPdfFile()), encoding));
/* 289 */       String firstLine = reader.readLine();
/* 290 */       if ((firstLine == null) || ((firstLine != null) && (!firstLine.matches("%PDF-1\\.[1-9]"))))
/*     */       {
/* 292 */         addValidationError(new ValidationResult.ValidationError("1.1", "First line must match %PDF-1.\\d"));
/*     */       }
/*     */ 
/* 296 */       String secondLine = reader.readLine();
/* 297 */       if (secondLine != null)
/*     */       {
/* 299 */         byte[] secondLineAsBytes = secondLine.getBytes(encoding.name());
/* 300 */         if (secondLineAsBytes.length >= 5)
/*     */         {
/* 302 */           for (int i = 0; i < secondLineAsBytes.length; i++)
/*     */           {
/* 304 */             byte b = secondLineAsBytes[i];
/* 305 */             if ((i == 0) && ((char)b != '%'))
/*     */             {
/* 307 */               addValidationError(new ValidationResult.ValidationError("1.1", "Second line must contains at least 4 bytes greater than 127"));
/*     */ 
/* 309 */               break;
/*     */             }
/* 311 */             if ((i > 0) && ((b & 0xFF) < 128))
/*     */             {
/* 313 */               addValidationError(new ValidationResult.ValidationError("1.1", "Second line must contains at least 4 bytes greater than 127"));
/*     */ 
/* 315 */               break;
/*     */             }
/*     */           }
/*     */         }
/*     */         else
/*     */         {
/* 321 */           addValidationError(new ValidationResult.ValidationError("1.1", "Second line must contains at least 4 bytes greater than 127"));
/*     */         }
/*     */       }
/*     */ 
/*     */     }
/*     */     catch (IOException e)
/*     */     {
/* 328 */       addValidationError(new ValidationResult.ValidationError("1.1", "Unable to read the PDF file : " + e.getMessage()));
/*     */     }
/*     */     finally
/*     */     {
/* 333 */       IOUtils.closeQuietly(reader);
/*     */     }
/*     */   }
/*     */ 
/*     */   protected boolean parseXrefTable(long startByteOffset)
/*     */     throws IOException
/*     */   {
/* 344 */     if (this.pdfSource.peek() != 120)
/*     */     {
/* 346 */       return false;
/*     */     }
/* 348 */     String xref = readString();
/* 349 */     if (!xref.equals("xref"))
/*     */     {
/* 351 */       addValidationError(new ValidationResult.ValidationError("1.3", "xref must be followed by a EOL character"));
/*     */ 
/* 353 */       return false;
/*     */     }
/* 355 */     if (!nextIsEOL())
/*     */     {
/* 357 */       addValidationError(new ValidationResult.ValidationError("1.3", "xref must be followed by EOL"));
/*     */     }
/*     */ 
/* 362 */     this.xrefTrailerResolver.nextXrefObj(startByteOffset);
/*     */     while (true)
/*     */     {
/* 370 */       long currObjID = 0L;
/* 371 */       long count = 0L;
/*     */ 
/* 373 */       long offset = this.pdfSource.getOffset();
/* 374 */       String line = readLine();
/* 375 */       Pattern pattern = Pattern.compile("(\\d+)\\s(\\d+)(\\s*)");
/* 376 */       Matcher matcher = pattern.matcher(line);
/* 377 */       if (matcher.matches())
/*     */       {
/* 379 */         currObjID = Integer.parseInt(matcher.group(1));
/* 380 */         count = Integer.parseInt(matcher.group(2));
/*     */       }
/*     */       else
/*     */       {
/* 384 */         addValidationError(new ValidationResult.ValidationError("1.3", "Cross reference subsection header is invalid"));
/*     */ 
/* 387 */         this.pdfSource.seek(offset);
/* 388 */         currObjID = readObjectNumber();
/* 389 */         count = readLong();
/*     */       }
/*     */ 
/* 392 */       skipSpaces();
/* 393 */       for (int i = 0; i < count; i++)
/*     */       {
/* 395 */         if ((this.pdfSource.isEOF()) || (isEndOfName((char)this.pdfSource.peek())))
/*     */         {
/*     */           break;
/*     */         }
/* 399 */         if (this.pdfSource.peek() == 116)
/*     */         {
/* 401 */           addValidationError(new ValidationResult.ValidationError("1.3", "Expected xref line but 't' found"));
/*     */ 
/* 403 */           break;
/*     */         }
/*     */ 
/* 406 */         String currentLine = readLine();
/* 407 */         String[] splitString = currentLine.split(" ");
/* 408 */         if (splitString.length < 3)
/*     */         {
/* 410 */           addValidationError(new ValidationResult.ValidationError("1.3", "invalid xref line: " + currentLine));
/*     */ 
/* 412 */           break;
/*     */         }
/*     */ 
/* 417 */         if (splitString[(splitString.length - 1)].equals("n"))
/*     */         {
/*     */           try
/*     */           {
/* 421 */             long currOffset = Long.parseLong(splitString[0]);
/* 422 */             int currGenID = Integer.parseInt(splitString[1]);
/* 423 */             COSObjectKey objKey = new COSObjectKey(currObjID, currGenID);
/* 424 */             this.xrefTrailerResolver.setXRef(objKey, currOffset);
/*     */           }
/*     */           catch (NumberFormatException e)
/*     */           {
/* 428 */             addValidationError(new ValidationResult.ValidationError("1.3", "offset or genid can't be read as number " + e.getMessage()));
/*     */           }
/*     */ 
/*     */         }
/* 432 */         else if (!splitString[2].equals("f"))
/*     */         {
/* 434 */           addValidationError(new ValidationResult.ValidationError("1.3", "Corrupt XRefTable Entry - ObjID:" + currObjID));
/*     */         }
/*     */ 
/* 437 */         currObjID += 1L;
/* 438 */         skipSpaces();
/*     */       }
/* 440 */       skipSpaces();
/* 441 */       char c = (char)this.pdfSource.peek();
/* 442 */       if ((c < '0') || (c > '9'))
/*     */       {
/*     */         break;
/*     */       }
/*     */     }
/* 447 */     return true;
/*     */   }
/*     */ 
/*     */   protected COSStream parseCOSStream(COSDictionary dic, RandomAccess file)
/*     */     throws IOException
/*     */   {
/* 457 */     checkStreamKeyWord();
/* 458 */     COSStream result = super.parseCOSStream(dic, file);
/* 459 */     checkEndstreamKeyWord();
/* 460 */     return result;
/*     */   }
/*     */ 
/*     */   protected void checkStreamKeyWord()
/*     */     throws IOException
/*     */   {
/* 470 */     String streamV = readString();
/* 471 */     if (!streamV.equals("stream"))
/*     */     {
/* 473 */       addValidationError(new ValidationResult.ValidationError("1.2.2", "Expected 'stream' keyword but found '" + streamV + "'"));
/*     */     }
/*     */ 
/* 476 */     int nextChar = this.pdfSource.read();
/* 477 */     if (((nextChar != 13) || (this.pdfSource.peek() != 10)) && (nextChar != 10))
/*     */     {
/* 479 */       addValidationError(new ValidationResult.ValidationError("1.2.2", "Expected 'EOL' after the stream keyword"));
/*     */     }
/*     */ 
/* 483 */     this.pdfSource.seek(this.pdfSource.getOffset() - 7L);
/*     */   }
/*     */ 
/*     */   protected void checkEndstreamKeyWord()
/*     */     throws IOException
/*     */   {
/* 493 */     this.pdfSource.seek(this.pdfSource.getOffset() - 10L);
/* 494 */     if (!nextIsEOL())
/*     */     {
/* 496 */       addValidationError(new ValidationResult.ValidationError("1.2.2", "Expected 'EOL' before the endstream keyword"));
/*     */     }
/*     */ 
/* 499 */     String endstreamV = readString();
/* 500 */     if (!endstreamV.equals("endstream"))
/*     */     {
/* 502 */       addValidationError(new ValidationResult.ValidationError("1.2.2", "Expected 'endstream' keyword but found '" + endstreamV + "'"));
/*     */     }
/*     */   }
/*     */ 
/*     */   protected boolean nextIsEOL()
/*     */     throws IOException
/*     */   {
/* 509 */     boolean succeed = false;
/* 510 */     int nextChar = this.pdfSource.read();
/* 511 */     if ((nextChar == 13) && (this.pdfSource.peek() == 10))
/*     */     {
/* 513 */       this.pdfSource.read();
/* 514 */       succeed = true;
/*     */     }
/* 516 */     else if ((nextChar == 13) || (nextChar == 10))
/*     */     {
/* 518 */       succeed = true;
/*     */     }
/* 520 */     return succeed;
/*     */   }
/*     */ 
/*     */   protected boolean nextIsSpace()
/*     */     throws IOException
/*     */   {
/* 529 */     return 32 == this.pdfSource.read();
/*     */   }
/*     */ 
/*     */   protected COSArray parseCOSArray()
/*     */     throws IOException
/*     */   {
/* 538 */     COSArray result = super.parseCOSArray();
/* 539 */     if ((result != null) && (result.size() > 8191))
/*     */     {
/* 541 */       addValidationError(new ValidationResult.ValidationError("1.0.2", "Array too long : " + result.size()));
/*     */     }
/* 543 */     return result;
/*     */   }
/*     */ 
/*     */   protected COSName parseCOSName()
/*     */     throws IOException
/*     */   {
/* 552 */     COSName result = super.parseCOSName();
/* 553 */     if ((result != null) && (result.getName().getBytes().length > 127))
/*     */     {
/* 555 */       addValidationError(new ValidationResult.ValidationError("1.0.3", "Name too long"));
/*     */     }
/* 557 */     return result;
/*     */   }
/*     */ 
/*     */   @Deprecated
/*     */   protected COSString parseCOSString(boolean isDictionary)
/*     */     throws IOException
/*     */   {
/* 569 */     return parseCOSString();
/*     */   }
/*     */ 
/*     */   protected COSString parseCOSString()
/*     */     throws IOException
/*     */   {
/* 580 */     long offset = this.pdfSource.getOffset();
/* 581 */     char nextChar = (char)this.pdfSource.read();
/* 582 */     int count = 0;
/* 583 */     if (nextChar == '<')
/*     */     {
/*     */       do
/*     */       {
/* 587 */         nextChar = (char)this.pdfSource.read();
/* 588 */         if (nextChar != '>')
/*     */         {
/* 590 */           if (Character.digit(nextChar, 16) >= 0)
/*     */           {
/* 592 */             count++;
/*     */           }
/*     */           else
/*     */           {
/* 596 */             addValidationError(new ValidationResult.ValidationError("1.0.12", "Hexa String must have only Hexadecimal Characters (found '" + nextChar + "')"));
/*     */ 
/* 598 */             break;
/*     */           }
/*     */         }
/*     */       }
/* 601 */       while (nextChar != '>');
/*     */     }
/*     */ 
/* 604 */     if (count % 2 != 0)
/*     */     {
/* 606 */       addValidationError(new ValidationResult.ValidationError("1.0.11", "Hexa string shall contain even number of non white space char"));
/*     */     }
/*     */ 
/* 611 */     this.pdfSource.seek(offset);
/* 612 */     COSString result = super.parseCOSString();
/*     */ 
/* 614 */     if (result.getString().length() > 65535)
/*     */     {
/* 616 */       addValidationError(new ValidationResult.ValidationError("1.0.5", "Hexa string is too long"));
/*     */     }
/* 618 */     return result;
/*     */   }
/*     */ 
/*     */   protected COSBase parseDirObject()
/*     */     throws IOException
/*     */   {
/* 627 */     COSBase result = super.parseDirObject();
/*     */ 
/* 629 */     if ((result instanceof COSNumber))
/*     */     {
/* 631 */       COSNumber number = (COSNumber)result;
/* 632 */       if ((number instanceof COSFloat))
/*     */       {
/* 634 */         Double real = Double.valueOf(number.doubleValue());
/* 635 */         if ((real.doubleValue() > 32767.0D) || (real.doubleValue() < -32767.0D))
/*     */         {
/* 637 */           addValidationError(new ValidationResult.ValidationError("1.0.6", "Float is too long or too small: " + real));
/*     */         }
/*     */ 
/*     */       }
/*     */       else
/*     */       {
/* 643 */         long numAsLong = number.longValue();
/* 644 */         if ((numAsLong > 2147483647L) || (numAsLong < -2147483648L))
/*     */         {
/* 646 */           addValidationError(new ValidationResult.ValidationError("1.0.6", "Numeric is too long or too small: " + numAsLong));
/*     */         }
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 652 */     if ((result instanceof COSDictionary))
/*     */     {
/* 654 */       COSDictionary dic = (COSDictionary)result;
/* 655 */       if (dic.size() > 4095)
/*     */       {
/* 657 */         addValidationError(new ValidationResult.ValidationError("1.0.1", "Too Many Entries In Dictionary"));
/*     */       }
/*     */     }
/* 660 */     return result;
/*     */   }
/*     */ 
/*     */   protected COSBase parseObjectDynamically(int objNr, int objGenNr, boolean requireExistingNotCompressedObj)
/*     */     throws IOException
/*     */   {
/* 668 */     COSObjectKey objKey = new COSObjectKey(objNr, objGenNr);
/* 669 */     COSObject pdfObject = this.document.getObjectFromPool(objKey);
/*     */     Set refObjNrs;
/* 671 */     if (pdfObject.getObject() == null)
/*     */     {
/* 675 */       Long offsetOrObjstmObNr = (Long)this.xrefTrailerResolver.getXrefTable().get(objKey);
/*     */ 
/* 678 */       if ((requireExistingNotCompressedObj) && (offsetOrObjstmObNr == null))
/*     */       {
/* 680 */         addValidationError(new ValidationResult.ValidationError("1.0.13", "Object must be defined and must not be compressed object: " + objKey.getNumber() + ":" + objKey.getGeneration()));
/*     */ 
/* 683 */         throw new SyntaxValidationException("Object must be defined and must not be compressed object: " + objKey.getNumber() + ":" + objKey.getGeneration(), this.validationResult);
/*     */       }
/*     */ 
/* 687 */       if (offsetOrObjstmObNr == null)
/*     */       {
/* 690 */         pdfObject.setObject(COSNull.NULL);
/*     */       }
/* 692 */       else if (offsetOrObjstmObNr.longValue() == 0L)
/*     */       {
/* 694 */         addValidationError(new ValidationResult.ValidationError("1.0.14", "Object {" + objKey.getNumber() + ":" + objKey.getGeneration() + "} has an offset of 0"));
/*     */       }
/* 697 */       else if (offsetOrObjstmObNr.longValue() > 0L)
/*     */       {
/* 701 */         setPdfSource(offsetOrObjstmObNr.longValue());
/*     */ 
/* 703 */         long readObjNr = 0L;
/* 704 */         int readObjGen = 0;
/*     */ 
/* 706 */         long offset = this.pdfSource.getOffset();
/* 707 */         String line = readLine();
/* 708 */         Pattern pattern = Pattern.compile("(\\d+)\\s(\\d+)\\sobj");
/* 709 */         Matcher matcher = pattern.matcher(line);
/* 710 */         if (matcher.matches())
/*     */         {
/* 712 */           readObjNr = Integer.parseInt(matcher.group(1));
/* 713 */           readObjGen = Integer.parseInt(matcher.group(2));
/*     */         }
/*     */         else
/*     */         {
/* 718 */           addValidationError(new ValidationResult.ValidationError("1.2.1", "Single space expected"));
/*     */ 
/* 721 */           this.pdfSource.seek(offset);
/* 722 */           readObjNr = readObjectNumber();
/* 723 */           readObjGen = readGenerationNumber();
/* 724 */           skipSpaces();
/* 725 */           for (char c : OBJ_MARKER)
/*     */           {
/* 727 */             if (this.pdfSource.read() != c)
/*     */             {
/* 729 */               addValidationError(new ValidationResult.ValidationError("1.2.1", "Expected pattern '" + new String(OBJ_MARKER) + " but missed at character '" + c + "'"));
/*     */ 
/* 731 */               throw new SyntaxValidationException("Expected pattern '" + new String(OBJ_MARKER) + " but missed at character '" + c + "'", this.validationResult);
/*     */             }
/*     */ 
/*     */           }
/*     */ 
/*     */         }
/*     */ 
/* 738 */         if ((readObjNr != objKey.getNumber()) || (readObjGen != objKey.getGeneration()))
/*     */         {
/* 740 */           throw new IOException("XREF for " + objKey.getNumber() + ":" + objKey.getGeneration() + " points to wrong object: " + readObjNr + ":" + readObjGen);
/*     */         }
/*     */ 
/* 744 */         skipSpaces();
/* 745 */         COSBase pb = parseDirObject();
/* 746 */         skipSpaces();
/* 747 */         long endObjectOffset = this.pdfSource.getOffset();
/* 748 */         String endObjectKey = readString();
/*     */ 
/* 750 */         if (endObjectKey.equals("stream"))
/*     */         {
/* 752 */           this.pdfSource.seek(endObjectOffset);
/* 753 */           if ((pb instanceof COSDictionary))
/*     */           {
/* 755 */             COSStream stream = parseCOSStream((COSDictionary)pb, getDocument().getScratchFile());
/* 756 */             if (this.securityHandler != null)
/*     */             {
/*     */               try
/*     */               {
/* 760 */                 this.securityHandler.decryptStream(stream, objNr, objGenNr);
/*     */               }
/*     */               catch (CryptographyException ce)
/*     */               {
/* 764 */                 throw new IOException("Error decrypting stream object " + objNr + ": " + ce.getMessage());
/*     */               }
/*     */ 
/*     */             }
/*     */ 
/* 769 */             pb = stream;
/*     */           }
/*     */           else
/*     */           {
/* 775 */             throw new IOException("Stream not preceded by dictionary (offset: " + offsetOrObjstmObNr + ").");
/*     */           }
/* 777 */           skipSpaces();
/* 778 */           endObjectOffset = this.pdfSource.getOffset();
/* 779 */           endObjectKey = readString();
/*     */ 
/* 782 */           if (!endObjectKey.startsWith("endobj"))
/*     */           {
/* 784 */             if (endObjectKey.startsWith("endstream"))
/*     */             {
/* 786 */               endObjectKey = endObjectKey.substring(9).trim();
/* 787 */               if (endObjectKey.length() == 0)
/*     */               {
/* 790 */                 endObjectKey = readString();
/*     */               }
/*     */             }
/*     */           }
/*     */         }
/* 795 */         else if (this.securityHandler != null)
/*     */         {
/* 797 */           decrypt(pb, objNr, objGenNr);
/*     */         }
/*     */ 
/* 800 */         pdfObject.setObject(pb);
/*     */ 
/* 802 */         if (!endObjectKey.startsWith("endobj"))
/*     */         {
/* 804 */           throw new IOException("Object (" + readObjNr + ":" + readObjGen + ") at offset " + offsetOrObjstmObNr + " does not end with 'endobj'.");
/*     */         }
/*     */ 
/* 809 */         offset = this.pdfSource.getOffset();
/* 810 */         this.pdfSource.seek(endObjectOffset - 1L);
/* 811 */         if (!nextIsEOL())
/*     */         {
/* 813 */           addValidationError(new ValidationResult.ValidationError("1.2.1", "EOL expected before the 'endobj' keyword"));
/*     */         }
/*     */ 
/* 816 */         this.pdfSource.seek(offset);
/*     */ 
/* 819 */         if (!nextIsEOL())
/*     */         {
/* 821 */           addValidationError(new ValidationResult.ValidationError("1.2.1", "EOL expected after the 'endobj' keyword"));
/*     */         }
/*     */ 
/* 825 */         releasePdfSourceInputStream();
/*     */       }
/*     */       else
/*     */       {
/* 831 */         int objstmObjNr = (int)-offsetOrObjstmObNr.longValue();
/* 832 */         COSBase objstmBaseObj = parseObjectDynamically(objstmObjNr, 0, true);
/* 833 */         if ((objstmBaseObj instanceof COSStream))
/*     */         {
/* 836 */           PDFObjectStreamParser parser = new PDFObjectStreamParser((COSStream)objstmBaseObj, this.document, this.forceParsing);
/*     */ 
/* 838 */           parser.parse();
/*     */ 
/* 841 */           refObjNrs = this.xrefTrailerResolver.getContainedObjectNumbers(objstmObjNr);
/*     */ 
/* 844 */           for (COSObject next : parser.getObjects())
/*     */           {
/* 846 */             COSObjectKey stmObjKey = new COSObjectKey(next);
/* 847 */             if (refObjNrs.contains(Long.valueOf(stmObjKey.getNumber())))
/*     */             {
/* 849 */               COSObject stmObj = this.document.getObjectFromPool(stmObjKey);
/* 850 */               stmObj.setObject(next.getObject());
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/* 856 */     return pdfObject.getObject();
/*     */   }
/*     */ 
/*     */   protected int lastIndexOf(char[] pattern, byte[] buf, int endOff)
/*     */   {
/* 862 */     int offset = super.lastIndexOf(pattern, buf, endOff);
/* 863 */     if ((offset > 0) && (Arrays.equals(pattern, EOF_MARKER)))
/*     */     {
/* 867 */       int tmpOffset = offset + pattern.length;
/* 868 */       if (tmpOffset != buf.length)
/*     */       {
/* 871 */         if ((buf.length - tmpOffset > 2) || ((buf.length - tmpOffset == 2) && ((buf[tmpOffset] != 13) || (buf[(tmpOffset + 1)] != 10))) || ((buf.length - tmpOffset == 1) && (buf[tmpOffset] != 10)))
/*     */         {
/* 875 */           addValidationError(new ValidationResult.ValidationError("1.4.10", "File contains data after the last %%EOF sequence"));
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/* 880 */     return offset;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.preflight.parser.PreflightParser
 * JD-Core Version:    0.6.2
 */