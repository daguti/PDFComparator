/*     */ package org.apache.pdfbox.filter;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.util.Collection;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import org.apache.pdfbox.cos.COSName;
/*     */ 
/*     */ public class FilterManager
/*     */ {
/*  35 */   private final Map<COSName, Filter> filters = new HashMap();
/*     */ 
/*     */   public FilterManager()
/*     */   {
/*  42 */     Filter flateFilter = new FlateFilter();
/*  43 */     Filter ccittFaxFilter = new CCITTFaxDecodeFilter();
/*  44 */     Filter lzwFilter = new LZWFilter();
/*  45 */     Filter asciiHexFilter = new ASCIIHexFilter();
/*  46 */     Filter ascii85Filter = new ASCII85Filter();
/*  47 */     Filter runLengthFilter = new RunLengthDecodeFilter();
/*  48 */     Filter cryptFilter = new CryptFilter();
/*  49 */     Filter jpxFilter = new JPXFilter();
/*  50 */     Filter jbig2Filter = new JBIG2Filter();
/*     */ 
/*  52 */     addFilter(COSName.FLATE_DECODE, flateFilter);
/*  53 */     addFilter(COSName.FLATE_DECODE_ABBREVIATION, flateFilter);
/*  54 */     addFilter(COSName.DCT_DECODE, jpxFilter);
/*  55 */     addFilter(COSName.DCT_DECODE_ABBREVIATION, jpxFilter);
/*  56 */     addFilter(COSName.CCITTFAX_DECODE, ccittFaxFilter);
/*  57 */     addFilter(COSName.CCITTFAX_DECODE_ABBREVIATION, ccittFaxFilter);
/*  58 */     addFilter(COSName.LZW_DECODE, lzwFilter);
/*  59 */     addFilter(COSName.LZW_DECODE_ABBREVIATION, lzwFilter);
/*  60 */     addFilter(COSName.ASCII_HEX_DECODE, asciiHexFilter);
/*  61 */     addFilter(COSName.ASCII_HEX_DECODE_ABBREVIATION, asciiHexFilter);
/*  62 */     addFilter(COSName.ASCII85_DECODE, ascii85Filter);
/*  63 */     addFilter(COSName.ASCII85_DECODE_ABBREVIATION, ascii85Filter);
/*  64 */     addFilter(COSName.RUN_LENGTH_DECODE, runLengthFilter);
/*  65 */     addFilter(COSName.RUN_LENGTH_DECODE_ABBREVIATION, runLengthFilter);
/*  66 */     addFilter(COSName.CRYPT, cryptFilter);
/*  67 */     addFilter(COSName.JPX_DECODE, jpxFilter);
/*  68 */     addFilter(COSName.JBIG2_DECODE, jbig2Filter);
/*     */   }
/*     */ 
/*     */   public Collection<Filter> getFilters()
/*     */   {
/*  78 */     return this.filters.values();
/*     */   }
/*     */ 
/*     */   public void addFilter(COSName filterName, Filter filter)
/*     */   {
/*  89 */     this.filters.put(filterName, filter);
/*     */   }
/*     */ 
/*     */   public Filter getFilter(COSName filterName)
/*     */     throws IOException
/*     */   {
/* 103 */     Filter filter = (Filter)this.filters.get(filterName);
/* 104 */     if (filter == null)
/*     */     {
/* 106 */       throw new IOException("Unknown stream filter:" + filterName);
/*     */     }
/*     */ 
/* 109 */     return filter;
/*     */   }
/*     */ 
/*     */   public Filter getFilter(String filterName)
/*     */     throws IOException
/*     */   {
/* 123 */     return getFilter(COSName.getPDFName(filterName));
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.filter.FilterManager
 * JD-Core Version:    0.6.2
 */