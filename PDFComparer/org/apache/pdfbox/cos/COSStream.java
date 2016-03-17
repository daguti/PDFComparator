/*     */ package org.apache.pdfbox.cos;
/*     */ 
/*     */ import java.io.BufferedInputStream;
/*     */ import java.io.BufferedOutputStream;
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.Closeable;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.util.List;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.commons.logging.LogFactory;
/*     */ import org.apache.pdfbox.exceptions.COSVisitorException;
/*     */ import org.apache.pdfbox.filter.Filter;
/*     */ import org.apache.pdfbox.filter.FilterManager;
/*     */ import org.apache.pdfbox.io.IOUtils;
/*     */ import org.apache.pdfbox.io.RandomAccess;
/*     */ import org.apache.pdfbox.io.RandomAccessBuffer;
/*     */ import org.apache.pdfbox.io.RandomAccessFile;
/*     */ import org.apache.pdfbox.io.RandomAccessFileInputStream;
/*     */ import org.apache.pdfbox.io.RandomAccessFileOutputStream;
/*     */ import org.apache.pdfbox.pdfparser.PDFStreamParser;
/*     */ 
/*     */ public class COSStream extends COSDictionary
/*     */   implements Closeable
/*     */ {
/*  51 */   private static final Log LOG = LogFactory.getLog(COSStream.class);
/*     */   private static final int BUFFER_SIZE = 16384;
/*     */   private RandomAccess file;
/*     */   private RandomAccessFileOutputStream filteredStream;
/*     */   private RandomAccessFileOutputStream unFilteredStream;
/*     */ 
/*     */   private RandomAccess clone(RandomAccess file)
/*     */   {
/*  68 */     if (file == null)
/*     */     {
/*  70 */       return null;
/*     */     }
/*  72 */     if ((file instanceof RandomAccessFile))
/*     */     {
/*  74 */       return file;
/*     */     }
/*     */ 
/*  78 */     return ((RandomAccessBuffer)file).clone();
/*     */   }
/*     */ 
/*     */   public COSStream(RandomAccess storage)
/*     */   {
/*  90 */     this.file = clone(storage);
/*     */   }
/*     */ 
/*     */   public COSStream(COSDictionary dictionary, RandomAccess storage)
/*     */   {
/* 101 */     super(dictionary);
/* 102 */     this.file = clone(storage);
/*     */   }
/*     */ 
/*     */   /** @deprecated */
/*     */   public void replaceWithStream(COSStream stream)
/*     */   {
/* 116 */     clear();
/* 117 */     addAll(stream);
/* 118 */     this.file = stream.file;
/* 119 */     this.filteredStream = stream.filteredStream;
/* 120 */     this.unFilteredStream = stream.unFilteredStream;
/*     */   }
/*     */ 
/*     */   /** @deprecated */
/*     */   public RandomAccess getScratchFile()
/*     */   {
/* 132 */     return this.file;
/*     */   }
/*     */ 
/*     */   public List<Object> getStreamTokens()
/*     */     throws IOException
/*     */   {
/* 144 */     PDFStreamParser parser = new PDFStreamParser(this);
/* 145 */     parser.parse();
/* 146 */     return parser.getTokens();
/*     */   }
/*     */ 
/*     */   public InputStream getFilteredStream()
/*     */     throws IOException
/*     */   {
/* 158 */     if (this.filteredStream == null)
/*     */     {
/* 160 */       doEncode();
/*     */     }
/* 162 */     long position = this.filteredStream.getPosition();
/* 163 */     long length = this.filteredStream.getLengthWritten();
/*     */ 
/* 165 */     RandomAccessFileInputStream input = new RandomAccessFileInputStream(this.file, position, length);
/*     */ 
/* 167 */     return new BufferedInputStream(input, 16384);
/*     */   }
/*     */ 
/*     */   public long getFilteredLength()
/*     */     throws IOException
/*     */   {
/* 179 */     if (this.filteredStream == null)
/*     */     {
/* 181 */       doEncode();
/*     */     }
/* 183 */     return this.filteredStream.getLength();
/*     */   }
/*     */ 
/*     */   public void setFilteredLength(long length)
/*     */   {
/* 194 */     this.filteredStream.setExpectedLength(COSInteger.get(length));
/*     */   }
/*     */ 
/*     */   public long getFilteredLengthWritten()
/*     */     throws IOException
/*     */   {
/* 206 */     if (this.filteredStream == null)
/*     */     {
/* 208 */       doEncode();
/*     */     }
/* 210 */     return this.filteredStream.getLengthWritten();
/*     */   }
/*     */ 
/*     */   public InputStream getUnfilteredStream()
/*     */     throws IOException
/*     */   {
/* 223 */     if (this.unFilteredStream == null)
/*     */     {
/* 225 */       doDecode();
/*     */     }
/*     */     InputStream retval;
/*     */     InputStream retval;
/* 230 */     if (this.unFilteredStream != null)
/*     */     {
/* 232 */       long position = this.unFilteredStream.getPosition();
/* 233 */       long length = this.unFilteredStream.getLengthWritten();
/* 234 */       RandomAccessFileInputStream input = new RandomAccessFileInputStream(this.file, position, length);
/*     */ 
/* 236 */       retval = new BufferedInputStream(input, 16384);
/*     */     }
/*     */     else
/*     */     {
/* 255 */       retval = new ByteArrayInputStream(new byte[0]);
/*     */     }
/*     */ 
/* 258 */     return retval;
/*     */   }
/*     */ 
/*     */   public Object accept(ICOSVisitor visitor)
/*     */     throws COSVisitorException
/*     */   {
/* 271 */     return visitor.visitFromStream(this);
/*     */   }
/*     */ 
/*     */   private void doDecode()
/*     */     throws IOException
/*     */   {
/* 282 */     this.unFilteredStream = this.filteredStream;
/*     */ 
/* 284 */     COSBase filters = getFilters();
/* 285 */     if (filters != null)
/*     */     {
/* 289 */       if ((filters instanceof COSName))
/*     */       {
/* 291 */         doDecode((COSName)filters, 0);
/*     */       }
/* 293 */       else if ((filters instanceof COSArray))
/*     */       {
/* 295 */         COSArray filterArray = (COSArray)filters;
/* 296 */         for (int i = 0; i < filterArray.size(); i++)
/*     */         {
/* 298 */           COSName filterName = (COSName)filterArray.get(i);
/* 299 */           doDecode(filterName, i);
/*     */         }
/*     */       }
/*     */       else
/*     */       {
/* 304 */         throw new IOException("Error: Unknown filter type:" + filters);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   private void doDecode(COSName filterName, int filterIndex)
/*     */     throws IOException
/*     */   {
/* 318 */     FilterManager manager = getFilterManager();
/* 319 */     Filter filter = manager.getFilter(filterName);
/*     */ 
/* 321 */     boolean done = false;
/* 322 */     IOException exception = null;
/* 323 */     long position = this.unFilteredStream.getPosition();
/* 324 */     long length = this.unFilteredStream.getLength();
/*     */ 
/* 326 */     long writtenLength = this.unFilteredStream.getLengthWritten();
/*     */ 
/* 328 */     if ((length == 0L) && (writtenLength == 0L))
/*     */     {
/* 333 */       IOUtils.closeQuietly(this.unFilteredStream);
/* 334 */       this.unFilteredStream = new RandomAccessFileOutputStream(this.file);
/* 335 */       done = true;
/*     */     }
/*     */     else
/*     */     {
/* 342 */       for (int tryCount = 0; (length > 0L) && (!done) && (tryCount < 5); tryCount++)
/*     */       {
/* 344 */         InputStream input = null;
/*     */         try
/*     */         {
/* 347 */           input = new BufferedInputStream(new RandomAccessFileInputStream(this.file, position, length), 16384);
/*     */ 
/* 349 */           IOUtils.closeQuietly(this.unFilteredStream);
/* 350 */           this.unFilteredStream = new RandomAccessFileOutputStream(this.file);
/* 351 */           filter.decode(input, this.unFilteredStream, this, filterIndex);
/* 352 */           done = true;
/*     */         }
/*     */         catch (IOException io)
/*     */         {
/* 356 */           length -= 1L;
/* 357 */           exception = io;
/*     */         }
/*     */         finally
/*     */         {
/* 361 */           IOUtils.closeQuietly(input);
/*     */         }
/*     */       }
/* 364 */       if (!done)
/*     */       {
/* 369 */         length = writtenLength;
/* 370 */         for (int tryCount = 0; (!done) && (tryCount < 5); tryCount++)
/*     */         {
/* 372 */           InputStream input = null;
/*     */           try
/*     */           {
/* 375 */             input = new BufferedInputStream(new RandomAccessFileInputStream(this.file, position, length), 16384);
/*     */ 
/* 377 */             IOUtils.closeQuietly(this.unFilteredStream);
/* 378 */             this.unFilteredStream = new RandomAccessFileOutputStream(this.file);
/* 379 */             filter.decode(input, this.unFilteredStream, this, filterIndex);
/* 380 */             done = true;
/*     */           }
/*     */           catch (IOException io)
/*     */           {
/* 384 */             length -= 1L;
/* 385 */             exception = io;
/*     */           }
/*     */           finally
/*     */           {
/* 389 */             IOUtils.closeQuietly(input);
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/* 394 */     if (!done)
/*     */     {
/* 396 */       throw exception;
/*     */     }
/*     */   }
/*     */ 
/*     */   private void doEncode()
/*     */     throws IOException
/*     */   {
/* 407 */     this.filteredStream = this.unFilteredStream;
/*     */ 
/* 409 */     COSBase filters = getFilters();
/* 410 */     if (filters != null)
/*     */     {
/* 414 */       if ((filters instanceof COSName))
/*     */       {
/* 416 */         doEncode((COSName)filters, 0);
/*     */       }
/* 418 */       else if ((filters instanceof COSArray))
/*     */       {
/* 421 */         COSArray filterArray = (COSArray)filters;
/* 422 */         for (int i = filterArray.size() - 1; i >= 0; i--)
/*     */         {
/* 424 */           COSName filterName = (COSName)filterArray.get(i);
/* 425 */           doEncode(filterName, i);
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   private void doEncode(COSName filterName, int filterIndex)
/*     */     throws IOException
/*     */   {
/* 440 */     FilterManager manager = getFilterManager();
/* 441 */     Filter filter = manager.getFilter(filterName);
/*     */ 
/* 443 */     InputStream input = new BufferedInputStream(new RandomAccessFileInputStream(this.file, this.filteredStream.getPosition(), this.filteredStream.getLength()), 16384);
/*     */ 
/* 446 */     IOUtils.closeQuietly(this.filteredStream);
/* 447 */     this.filteredStream = new RandomAccessFileOutputStream(this.file);
/* 448 */     filter.encode(input, this.filteredStream, this, filterIndex);
/* 449 */     IOUtils.closeQuietly(input);
/*     */   }
/*     */ 
/*     */   public COSBase getFilters()
/*     */   {
/* 463 */     return getDictionaryObject(COSName.FILTER);
/*     */   }
/*     */ 
/*     */   public OutputStream createFilteredStream()
/*     */     throws IOException
/*     */   {
/* 477 */     IOUtils.closeQuietly(this.unFilteredStream);
/* 478 */     this.unFilteredStream = null;
/* 479 */     IOUtils.closeQuietly(this.filteredStream);
/* 480 */     this.filteredStream = new RandomAccessFileOutputStream(this.file);
/* 481 */     return new BufferedOutputStream(this.filteredStream, 16384);
/*     */   }
/*     */ 
/*     */   public OutputStream createFilteredStream(COSBase expectedLength)
/*     */     throws IOException
/*     */   {
/* 497 */     IOUtils.closeQuietly(this.unFilteredStream);
/* 498 */     this.unFilteredStream = null;
/* 499 */     IOUtils.closeQuietly(this.filteredStream);
/* 500 */     this.filteredStream = new RandomAccessFileOutputStream(this.file);
/* 501 */     this.filteredStream.setExpectedLength(expectedLength);
/* 502 */     return new BufferedOutputStream(this.filteredStream, 16384);
/*     */   }
/*     */ 
/*     */   public void setFilters(COSBase filters)
/*     */     throws IOException
/*     */   {
/* 514 */     setItem(COSName.FILTER, filters);
/*     */ 
/* 516 */     IOUtils.closeQuietly(this.filteredStream);
/* 517 */     this.filteredStream = null;
/*     */   }
/*     */ 
/*     */   public OutputStream createUnfilteredStream()
/*     */     throws IOException
/*     */   {
/* 529 */     IOUtils.closeQuietly(this.filteredStream);
/* 530 */     this.filteredStream = null;
/* 531 */     IOUtils.closeQuietly(this.unFilteredStream);
/* 532 */     this.unFilteredStream = new RandomAccessFileOutputStream(this.file);
/* 533 */     return new BufferedOutputStream(this.unFilteredStream, 16384);
/*     */   }
/*     */ 
/*     */   public void close()
/*     */   {
/*     */     try
/*     */     {
/* 540 */       if (this.file != null)
/*     */       {
/* 542 */         this.file.close();
/* 543 */         this.file = null;
/*     */       }
/*     */     }
/*     */     catch (IOException exception)
/*     */     {
/* 548 */       LOG.error("Exception occured when closing the file.", exception);
/*     */     }
/* 550 */     if (this.filteredStream != null)
/*     */     {
/* 552 */       IOUtils.closeQuietly(this.filteredStream);
/*     */     }
/* 554 */     if (this.unFilteredStream != null)
/*     */     {
/* 556 */       IOUtils.closeQuietly(this.unFilteredStream);
/*     */     }
/* 558 */     clear();
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.cos.COSStream
 * JD-Core Version:    0.6.2
 */