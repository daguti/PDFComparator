/*     */ package org.apache.pdfbox.pdmodel.common;
/*     */ 
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import org.apache.pdfbox.cos.COSArray;
/*     */ import org.apache.pdfbox.cos.COSBase;
/*     */ import org.apache.pdfbox.cos.COSDictionary;
/*     */ import org.apache.pdfbox.cos.COSDocument;
/*     */ import org.apache.pdfbox.cos.COSName;
/*     */ import org.apache.pdfbox.cos.COSNull;
/*     */ import org.apache.pdfbox.cos.COSStream;
/*     */ import org.apache.pdfbox.filter.Filter;
/*     */ import org.apache.pdfbox.filter.FilterManager;
/*     */ import org.apache.pdfbox.io.IOUtils;
/*     */ import org.apache.pdfbox.pdmodel.PDDocument;
/*     */ import org.apache.pdfbox.pdmodel.common.filespecification.PDFileSpecification;
/*     */ 
/*     */ public class PDStream
/*     */   implements COSObjectable
/*     */ {
/*     */   private COSStream stream;
/*     */ 
/*     */   protected PDStream()
/*     */   {
/*     */   }
/*     */ 
/*     */   public PDStream(PDDocument document)
/*     */   {
/*  71 */     this.stream = document.getDocument().createCOSStream();
/*     */   }
/*     */ 
/*     */   public PDStream(COSStream str)
/*     */   {
/*  82 */     this.stream = str;
/*     */   }
/*     */ 
/*     */   public PDStream(PDDocument doc, InputStream str)
/*     */     throws IOException
/*     */   {
/*  98 */     this(doc, str, false);
/*     */   }
/*     */ 
/*     */   public PDStream(PDDocument doc, InputStream str, boolean filtered)
/*     */     throws IOException
/*     */   {
/* 117 */     OutputStream output = null;
/*     */     try
/*     */     {
/* 120 */       this.stream = doc.getDocument().createCOSStream();
/* 121 */       if (filtered)
/*     */       {
/* 123 */         output = this.stream.createFilteredStream();
/*     */       }
/*     */       else
/*     */       {
/* 127 */         output = this.stream.createUnfilteredStream();
/*     */       }
/* 129 */       byte[] buffer = new byte[1024];
/* 130 */       int amountRead = -1;
/* 131 */       while ((amountRead = str.read(buffer)) != -1)
/*     */       {
/* 133 */         output.write(buffer, 0, amountRead);
/*     */       }
/*     */     }
/*     */     finally
/*     */     {
/* 138 */       if (output != null)
/*     */       {
/* 140 */         output.close();
/*     */       }
/* 142 */       if (str != null)
/*     */       {
/* 144 */         str.close();
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public void addCompression()
/*     */   {
/* 155 */     List filters = getFilters();
/* 156 */     if (filters == null)
/*     */     {
/* 158 */       filters = new ArrayList();
/* 159 */       filters.add(COSName.FLATE_DECODE);
/* 160 */       setFilters(filters);
/*     */     }
/*     */   }
/*     */ 
/*     */   public static PDStream createFromCOS(COSBase base)
/*     */     throws IOException
/*     */   {
/* 176 */     PDStream retval = null;
/* 177 */     if ((base instanceof COSStream))
/*     */     {
/* 179 */       retval = new PDStream((COSStream)base);
/*     */     }
/* 181 */     else if ((base instanceof COSArray))
/*     */     {
/* 183 */       if (((COSArray)base).size() > 0)
/*     */       {
/* 185 */         retval = new PDStream(new COSStreamArray((COSArray)base));
/*     */       }
/*     */ 
/*     */     }
/* 190 */     else if (base != null)
/*     */     {
/* 192 */       throw new IOException("Contents are unknown type:" + base.getClass().getName());
/*     */     }
/*     */ 
/* 196 */     return retval;
/*     */   }
/*     */ 
/*     */   public COSBase getCOSObject()
/*     */   {
/* 206 */     return this.stream;
/*     */   }
/*     */ 
/*     */   public OutputStream createOutputStream()
/*     */     throws IOException
/*     */   {
/* 219 */     return this.stream.createUnfilteredStream();
/*     */   }
/*     */ 
/*     */   public InputStream createInputStream()
/*     */     throws IOException
/*     */   {
/* 232 */     return this.stream.getUnfilteredStream();
/*     */   }
/*     */ 
/*     */   public InputStream getPartiallyFilteredStream(List<String> stopFilters)
/*     */     throws IOException
/*     */   {
/* 249 */     FilterManager manager = this.stream.getFilterManager();
/* 250 */     InputStream is = this.stream.getFilteredStream();
/* 251 */     ByteArrayOutputStream os = new ByteArrayOutputStream();
/* 252 */     List filters = getFilters();
/* 253 */     boolean done = false;
/* 254 */     for (int i = 0; (i < filters.size()) && (!done); i++)
/*     */     {
/* 256 */       COSName nextFilter = (COSName)filters.get(i);
/* 257 */       if (stopFilters.contains(nextFilter.getName()))
/*     */       {
/* 259 */         done = true;
/*     */       }
/*     */       else
/*     */       {
/* 263 */         Filter filter = manager.getFilter(nextFilter);
/* 264 */         filter.decode(is, os, this.stream, i);
/* 265 */         IOUtils.closeQuietly(is);
/* 266 */         is = new ByteArrayInputStream(os.toByteArray());
/* 267 */         os.reset();
/*     */       }
/*     */     }
/* 270 */     return is;
/*     */   }
/*     */ 
/*     */   public COSStream getStream()
/*     */   {
/* 280 */     return this.stream;
/*     */   }
/*     */ 
/*     */   public int getLength()
/*     */   {
/* 291 */     return this.stream.getInt(COSName.LENGTH, 0);
/*     */   }
/*     */ 
/*     */   public List<COSName> getFilters()
/*     */   {
/* 302 */     List retval = null;
/* 303 */     COSBase filters = this.stream.getFilters();
/* 304 */     if ((filters instanceof COSName))
/*     */     {
/* 306 */       COSName name = (COSName)filters;
/* 307 */       retval = new COSArrayList(name, name, this.stream, COSName.FILTER);
/*     */     }
/* 310 */     else if ((filters instanceof COSArray))
/*     */     {
/* 312 */       retval = ((COSArray)filters).toList();
/*     */     }
/* 314 */     return retval;
/*     */   }
/*     */ 
/*     */   public void setFilters(List<COSName> filters)
/*     */   {
/* 325 */     COSBase obj = COSArrayList.converterToCOSArray(filters);
/* 326 */     this.stream.setItem(COSName.FILTER, obj);
/*     */   }
/*     */ 
/*     */   public List<Object> getDecodeParms()
/*     */     throws IOException
/*     */   {
/* 340 */     List retval = null;
/*     */ 
/* 342 */     COSBase dp = this.stream.getDictionaryObject(COSName.DECODE_PARMS);
/* 343 */     if (dp == null)
/*     */     {
/* 347 */       dp = this.stream.getDictionaryObject(COSName.DP);
/*     */     }
/* 349 */     if ((dp instanceof COSDictionary))
/*     */     {
/* 351 */       Map map = COSDictionaryMap.convertBasicTypesToMap((COSDictionary)dp);
/*     */ 
/* 353 */       retval = new COSArrayList(map, dp, this.stream, COSName.DECODE_PARMS);
/*     */     }
/* 356 */     else if ((dp instanceof COSArray))
/*     */     {
/* 358 */       COSArray array = (COSArray)dp;
/* 359 */       List actuals = new ArrayList();
/* 360 */       for (int i = 0; i < array.size(); i++)
/*     */       {
/* 362 */         actuals.add(COSDictionaryMap.convertBasicTypesToMap((COSDictionary)array.getObject(i)));
/*     */       }
/*     */ 
/* 366 */       retval = new COSArrayList(actuals, array);
/*     */     }
/*     */ 
/* 369 */     return retval;
/*     */   }
/*     */ 
/*     */   public void setDecodeParms(List<?> decodeParams)
/*     */   {
/* 380 */     this.stream.setItem(COSName.DECODE_PARMS, COSArrayList.converterToCOSArray(decodeParams));
/*     */   }
/*     */ 
/*     */   public PDFileSpecification getFile()
/*     */     throws IOException
/*     */   {
/* 395 */     COSBase f = this.stream.getDictionaryObject(COSName.F);
/* 396 */     PDFileSpecification retval = PDFileSpecification.createFS(f);
/* 397 */     return retval;
/*     */   }
/*     */ 
/*     */   public void setFile(PDFileSpecification f)
/*     */   {
/* 408 */     this.stream.setItem(COSName.F, f);
/*     */   }
/*     */ 
/*     */   public List<String> getFileFilters()
/*     */   {
/* 419 */     List retval = null;
/* 420 */     COSBase filters = this.stream.getDictionaryObject(COSName.F_FILTER);
/* 421 */     if ((filters instanceof COSName))
/*     */     {
/* 423 */       COSName name = (COSName)filters;
/* 424 */       retval = new COSArrayList(name.getName(), name, this.stream, COSName.F_FILTER);
/*     */     }
/* 427 */     else if ((filters instanceof COSArray))
/*     */     {
/* 429 */       retval = COSArrayList.convertCOSNameCOSArrayToList((COSArray)filters);
/*     */     }
/*     */ 
/* 432 */     return retval;
/*     */   }
/*     */ 
/*     */   public void setFileFilters(List<String> filters)
/*     */   {
/* 443 */     COSBase obj = COSArrayList.convertStringListToCOSNameCOSArray(filters);
/* 444 */     this.stream.setItem(COSName.F_FILTER, obj);
/*     */   }
/*     */ 
/*     */   public List<Object> getFileDecodeParams()
/*     */     throws IOException
/*     */   {
/* 458 */     List retval = null;
/*     */ 
/* 460 */     COSBase dp = this.stream.getDictionaryObject(COSName.F_DECODE_PARMS);
/* 461 */     if ((dp instanceof COSDictionary))
/*     */     {
/* 463 */       Map map = COSDictionaryMap.convertBasicTypesToMap((COSDictionary)dp);
/*     */ 
/* 465 */       retval = new COSArrayList(map, dp, this.stream, COSName.F_DECODE_PARMS);
/*     */     }
/* 468 */     else if ((dp instanceof COSArray))
/*     */     {
/* 470 */       COSArray array = (COSArray)dp;
/* 471 */       List actuals = new ArrayList();
/* 472 */       for (int i = 0; i < array.size(); i++)
/*     */       {
/* 474 */         actuals.add(COSDictionaryMap.convertBasicTypesToMap((COSDictionary)array.getObject(i)));
/*     */       }
/*     */ 
/* 478 */       retval = new COSArrayList(actuals, array);
/*     */     }
/*     */ 
/* 481 */     return retval;
/*     */   }
/*     */ 
/*     */   public void setFileDecodeParams(List<?> decodeParams)
/*     */   {
/* 492 */     this.stream.setItem("FDecodeParams", COSArrayList.converterToCOSArray(decodeParams));
/*     */   }
/*     */ 
/*     */   public byte[] getByteArray()
/*     */     throws IOException
/*     */   {
/* 505 */     ByteArrayOutputStream output = new ByteArrayOutputStream();
/* 506 */     byte[] buf = new byte[1024];
/* 507 */     InputStream is = null;
/*     */     try
/*     */     {
/* 510 */       is = createInputStream();
/* 511 */       int amountRead = -1;
/* 512 */       while ((amountRead = is.read(buf)) != -1)
/*     */       {
/* 514 */         output.write(buf, 0, amountRead);
/*     */       }
/*     */     }
/*     */     finally
/*     */     {
/* 519 */       if (is != null)
/*     */       {
/* 521 */         is.close();
/*     */       }
/*     */     }
/* 524 */     return output.toByteArray();
/*     */   }
/*     */ 
/*     */   public String getInputStreamAsString()
/*     */     throws IOException
/*     */   {
/* 538 */     byte[] bStream = getByteArray();
/* 539 */     return new String(bStream, "ISO-8859-1");
/*     */   }
/*     */ 
/*     */   public PDMetadata getMetadata()
/*     */   {
/* 553 */     PDMetadata retval = null;
/* 554 */     COSBase mdStream = this.stream.getDictionaryObject(COSName.METADATA);
/* 555 */     if (mdStream != null)
/*     */     {
/* 557 */       if ((mdStream instanceof COSStream))
/*     */       {
/* 559 */         retval = new PDMetadata((COSStream)mdStream);
/*     */       }
/* 561 */       else if (!(mdStream instanceof COSNull))
/*     */       {
/* 567 */         throw new IllegalStateException("Expected a COSStream but was a " + mdStream.getClass().getSimpleName());
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 572 */     return retval;
/*     */   }
/*     */ 
/*     */   public void setMetadata(PDMetadata meta)
/*     */   {
/* 583 */     this.stream.setItem(COSName.METADATA, meta);
/*     */   }
/*     */ 
/*     */   public int getDecodedStreamLength()
/*     */   {
/* 596 */     return this.stream.getInt(COSName.DL);
/*     */   }
/*     */ 
/*     */   public void setDecodedStreamLength(int decodedStreamLength)
/*     */   {
/* 610 */     this.stream.setInt(COSName.DL, decodedStreamLength);
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.pdmodel.common.PDStream
 * JD-Core Version:    0.6.2
 */