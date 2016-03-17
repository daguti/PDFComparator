/*     */ package com.itextpdf.text.pdf.codec;
/*     */ 
/*     */ import com.itextpdf.text.error_messages.MessageLocalization;
/*     */ import com.itextpdf.text.pdf.RandomAccessFileOrArray;
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.util.Iterator;
/*     */ import java.util.Set;
/*     */ import java.util.SortedMap;
/*     */ import java.util.SortedSet;
/*     */ import java.util.TreeMap;
/*     */ import java.util.TreeSet;
/*     */ 
/*     */ public class JBIG2SegmentReader
/*     */ {
/*     */   public static final int SYMBOL_DICTIONARY = 0;
/*     */   public static final int INTERMEDIATE_TEXT_REGION = 4;
/*     */   public static final int IMMEDIATE_TEXT_REGION = 6;
/*     */   public static final int IMMEDIATE_LOSSLESS_TEXT_REGION = 7;
/*     */   public static final int PATTERN_DICTIONARY = 16;
/*     */   public static final int INTERMEDIATE_HALFTONE_REGION = 20;
/*     */   public static final int IMMEDIATE_HALFTONE_REGION = 22;
/*     */   public static final int IMMEDIATE_LOSSLESS_HALFTONE_REGION = 23;
/*     */   public static final int INTERMEDIATE_GENERIC_REGION = 36;
/*     */   public static final int IMMEDIATE_GENERIC_REGION = 38;
/*     */   public static final int IMMEDIATE_LOSSLESS_GENERIC_REGION = 39;
/*     */   public static final int INTERMEDIATE_GENERIC_REFINEMENT_REGION = 40;
/*     */   public static final int IMMEDIATE_GENERIC_REFINEMENT_REGION = 42;
/*     */   public static final int IMMEDIATE_LOSSLESS_GENERIC_REFINEMENT_REGION = 43;
/*     */   public static final int PAGE_INFORMATION = 48;
/*     */   public static final int END_OF_PAGE = 49;
/*     */   public static final int END_OF_STRIPE = 50;
/*     */   public static final int END_OF_FILE = 51;
/*     */   public static final int PROFILES = 52;
/*     */   public static final int TABLES = 53;
/*     */   public static final int EXTENSION = 62;
/*  97 */   private final SortedMap<Integer, JBIG2Segment> segments = new TreeMap();
/*  98 */   private final SortedMap<Integer, JBIG2Page> pages = new TreeMap();
/*  99 */   private final SortedSet<JBIG2Segment> globals = new TreeSet();
/*     */   private RandomAccessFileOrArray ra;
/*     */   private boolean sequential;
/*     */   private boolean number_of_pages_known;
/* 103 */   private int number_of_pages = -1;
/* 104 */   private boolean read = false;
/*     */ 
/*     */   public JBIG2SegmentReader(RandomAccessFileOrArray ra)
/*     */     throws IOException
/*     */   {
/* 196 */     this.ra = ra;
/*     */   }
/*     */ 
/*     */   public static byte[] copyByteArray(byte[] b) {
/* 200 */     byte[] bc = new byte[b.length];
/* 201 */     System.arraycopy(b, 0, bc, 0, b.length);
/* 202 */     return bc;
/*     */   }
/*     */ 
/*     */   public void read() throws IOException {
/* 206 */     if (this.read) {
/* 207 */       throw new IllegalStateException(MessageLocalization.getComposedMessage("already.attempted.a.read.on.this.jbig2.file", new Object[0]));
/*     */     }
/* 209 */     this.read = true;
/*     */ 
/* 211 */     readFileHeader();
/*     */ 
/* 213 */     if (this.sequential)
/*     */     {
/*     */       do {
/* 216 */         JBIG2Segment tmp = readHeader();
/* 217 */         readSegment(tmp);
/* 218 */         this.segments.put(Integer.valueOf(tmp.segmentNumber), tmp);
/* 219 */       }while (this.ra.getFilePointer() < this.ra.length());
/*     */     }
/*     */     else {
/*     */       JBIG2Segment tmp;
/*     */       do {
/* 224 */         tmp = readHeader();
/* 225 */         this.segments.put(Integer.valueOf(tmp.segmentNumber), tmp);
/* 226 */       }while (tmp.type != 51);
/* 227 */       Iterator segs = this.segments.keySet().iterator();
/* 228 */       while (segs.hasNext())
/* 229 */         readSegment((JBIG2Segment)this.segments.get(segs.next()));
/*     */     }
/*     */   }
/*     */ 
/*     */   void readSegment(JBIG2Segment s) throws IOException
/*     */   {
/* 235 */     int ptr = (int)this.ra.getFilePointer();
/*     */ 
/* 237 */     if (s.dataLength == 4294967295L)
/*     */     {
/* 239 */       return;
/*     */     }
/*     */ 
/* 242 */     byte[] data = new byte[(int)s.dataLength];
/* 243 */     this.ra.read(data);
/* 244 */     s.data = data;
/*     */ 
/* 246 */     if (s.type == 48) {
/* 247 */       int last = (int)this.ra.getFilePointer();
/* 248 */       this.ra.seek(ptr);
/* 249 */       int page_bitmap_width = this.ra.readInt();
/* 250 */       int page_bitmap_height = this.ra.readInt();
/* 251 */       this.ra.seek(last);
/* 252 */       JBIG2Page p = (JBIG2Page)this.pages.get(Integer.valueOf(s.page));
/* 253 */       if (p == null) {
/* 254 */         throw new IllegalStateException(MessageLocalization.getComposedMessage("referring.to.widht.height.of.page.we.havent.seen.yet.1", s.page));
/*     */       }
/*     */ 
/* 257 */       p.pageBitmapWidth = page_bitmap_width;
/* 258 */       p.pageBitmapHeight = page_bitmap_height;
/*     */     }
/*     */   }
/*     */ 
/*     */   JBIG2Segment readHeader() throws IOException {
/* 263 */     int ptr = (int)this.ra.getFilePointer();
/*     */ 
/* 265 */     int segment_number = this.ra.readInt();
/* 266 */     JBIG2Segment s = new JBIG2Segment(segment_number);
/*     */ 
/* 269 */     int segment_header_flags = this.ra.read();
/* 270 */     boolean deferred_non_retain = (segment_header_flags & 0x80) == 128;
/* 271 */     s.deferredNonRetain = deferred_non_retain;
/* 272 */     boolean page_association_size = (segment_header_flags & 0x40) == 64;
/* 273 */     int segment_type = segment_header_flags & 0x3F;
/* 274 */     s.type = segment_type;
/*     */ 
/* 277 */     int referred_to_byte0 = this.ra.read();
/* 278 */     int count_of_referred_to_segments = (referred_to_byte0 & 0xE0) >> 5;
/* 279 */     int[] referred_to_segment_numbers = null;
/* 280 */     boolean[] segment_retention_flags = null;
/*     */ 
/* 282 */     if (count_of_referred_to_segments == 7)
/*     */     {
/* 284 */       this.ra.seek(this.ra.getFilePointer() - 1L);
/* 285 */       count_of_referred_to_segments = this.ra.readInt() & 0x1FFFFFFF;
/* 286 */       segment_retention_flags = new boolean[count_of_referred_to_segments + 1];
/* 287 */       int i = 0;
/* 288 */       int referred_to_current_byte = 0;
/*     */       do {
/* 290 */         int j = i % 8;
/* 291 */         if (j == 0) {
/* 292 */           referred_to_current_byte = this.ra.read();
/*     */         }
/* 294 */         segment_retention_flags[i] = ((1 << j & referred_to_current_byte) >> j == 1 ? 1 : false);
/* 295 */         i++;
/* 296 */       }while (i <= count_of_referred_to_segments);
/*     */     }
/* 298 */     else if (count_of_referred_to_segments <= 4)
/*     */     {
/* 300 */       segment_retention_flags = new boolean[count_of_referred_to_segments + 1];
/* 301 */       referred_to_byte0 &= 31;
/* 302 */       for (int i = 0; i <= count_of_referred_to_segments; i++) {
/* 303 */         segment_retention_flags[i] = ((1 << i & referred_to_byte0) >> i == 1 ? 1 : false);
/*     */       }
/*     */     }
/* 306 */     else if ((count_of_referred_to_segments == 5) || (count_of_referred_to_segments == 6)) {
/* 307 */       throw new IllegalStateException(MessageLocalization.getComposedMessage("count.of.referred.to.segments.had.bad.value.in.header.for.segment.1.starting.at.2", new Object[] { String.valueOf(segment_number), String.valueOf(ptr) }));
/*     */     }
/* 309 */     s.segmentRetentionFlags = segment_retention_flags;
/* 310 */     s.countOfReferredToSegments = count_of_referred_to_segments;
/*     */ 
/* 313 */     referred_to_segment_numbers = new int[count_of_referred_to_segments + 1];
/* 314 */     for (int i = 1; i <= count_of_referred_to_segments; i++) {
/* 315 */       if (segment_number <= 256)
/* 316 */         referred_to_segment_numbers[i] = this.ra.read();
/* 317 */       else if (segment_number <= 65536)
/* 318 */         referred_to_segment_numbers[i] = this.ra.readUnsignedShort();
/*     */       else {
/* 320 */         referred_to_segment_numbers[i] = ((int)this.ra.readUnsignedInt());
/*     */       }
/*     */     }
/* 323 */     s.referredToSegmentNumbers = referred_to_segment_numbers;
/*     */ 
/* 327 */     int page_association_offset = (int)this.ra.getFilePointer() - ptr;
/*     */     int segment_page_association;
/*     */     int segment_page_association;
/* 328 */     if (page_association_size)
/* 329 */       segment_page_association = this.ra.readInt();
/*     */     else {
/* 331 */       segment_page_association = this.ra.read();
/*     */     }
/* 333 */     if (segment_page_association < 0) {
/* 334 */       throw new IllegalStateException(MessageLocalization.getComposedMessage("page.1.invalid.for.segment.2.starting.at.3", new Object[] { String.valueOf(segment_page_association), String.valueOf(segment_number), String.valueOf(ptr) }));
/*     */     }
/* 336 */     s.page = segment_page_association;
/*     */ 
/* 338 */     s.page_association_size = page_association_size;
/* 339 */     s.page_association_offset = page_association_offset;
/*     */ 
/* 341 */     if ((segment_page_association > 0) && (!this.pages.containsKey(Integer.valueOf(segment_page_association)))) {
/* 342 */       this.pages.put(Integer.valueOf(segment_page_association), new JBIG2Page(segment_page_association, this));
/*     */     }
/* 344 */     if (segment_page_association > 0)
/* 345 */       ((JBIG2Page)this.pages.get(Integer.valueOf(segment_page_association))).addSegment(s);
/*     */     else {
/* 347 */       this.globals.add(s);
/*     */     }
/*     */ 
/* 351 */     long segment_data_length = this.ra.readUnsignedInt();
/*     */ 
/* 353 */     s.dataLength = segment_data_length;
/*     */ 
/* 355 */     int end_ptr = (int)this.ra.getFilePointer();
/* 356 */     this.ra.seek(ptr);
/* 357 */     byte[] header_data = new byte[end_ptr - ptr];
/* 358 */     this.ra.read(header_data);
/* 359 */     s.headerData = header_data;
/*     */ 
/* 361 */     return s;
/*     */   }
/*     */ 
/*     */   void readFileHeader() throws IOException {
/* 365 */     this.ra.seek(0L);
/* 366 */     byte[] idstring = new byte[8];
/* 367 */     this.ra.read(idstring);
/*     */ 
/* 369 */     byte[] refidstring = { -105, 74, 66, 50, 13, 10, 26, 10 };
/*     */ 
/* 371 */     for (int i = 0; i < idstring.length; i++) {
/* 372 */       if (idstring[i] != refidstring[i]) {
/* 373 */         throw new IllegalStateException(MessageLocalization.getComposedMessage("file.header.idstring.not.good.at.byte.1", i));
/*     */       }
/*     */     }
/*     */ 
/* 377 */     int fileheaderflags = this.ra.read();
/*     */ 
/* 379 */     this.sequential = ((fileheaderflags & 0x1) == 1);
/* 380 */     this.number_of_pages_known = ((fileheaderflags & 0x2) == 0);
/*     */ 
/* 382 */     if ((fileheaderflags & 0xFC) != 0) {
/* 383 */       throw new IllegalStateException(MessageLocalization.getComposedMessage("file.header.flags.bits.2.7.not.0", new Object[0]));
/*     */     }
/*     */ 
/* 386 */     if (this.number_of_pages_known)
/* 387 */       this.number_of_pages = this.ra.readInt();
/*     */   }
/*     */ 
/*     */   public int numberOfPages()
/*     */   {
/* 392 */     return this.pages.size();
/*     */   }
/*     */ 
/*     */   public int getPageHeight(int i) {
/* 396 */     return ((JBIG2Page)this.pages.get(Integer.valueOf(i))).pageBitmapHeight;
/*     */   }
/*     */ 
/*     */   public int getPageWidth(int i) {
/* 400 */     return ((JBIG2Page)this.pages.get(Integer.valueOf(i))).pageBitmapWidth;
/*     */   }
/*     */ 
/*     */   public JBIG2Page getPage(int page) {
/* 404 */     return (JBIG2Page)this.pages.get(Integer.valueOf(page));
/*     */   }
/*     */ 
/*     */   public byte[] getGlobal(boolean for_embedding) {
/* 408 */     ByteArrayOutputStream os = new ByteArrayOutputStream();
/*     */     try {
/* 410 */       for (Object element : this.globals) {
/* 411 */         JBIG2Segment s = (JBIG2Segment)element;
/* 412 */         if ((!for_embedding) || ((s.type != 51) && (s.type != 49)))
/*     */         {
/* 416 */           os.write(s.headerData);
/* 417 */           os.write(s.data);
/*     */         }
/*     */       }
/* 419 */       os.close();
/*     */     } catch (IOException e) {
/* 421 */       e.printStackTrace();
/*     */     }
/* 423 */     if (os.size() <= 0) {
/* 424 */       return null;
/*     */     }
/* 426 */     return os.toByteArray();
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 431 */     if (this.read) {
/* 432 */       return "Jbig2SegmentReader: number of pages: " + numberOfPages();
/*     */     }
/* 434 */     return "Jbig2SegmentReader in indeterminate state.";
/*     */   }
/*     */ 
/*     */   public static class JBIG2Page
/*     */   {
/*     */     public final int page;
/*     */     private final JBIG2SegmentReader sr;
/* 142 */     private final SortedMap<Integer, JBIG2SegmentReader.JBIG2Segment> segs = new TreeMap();
/* 143 */     public int pageBitmapWidth = -1;
/* 144 */     public int pageBitmapHeight = -1;
/*     */ 
/* 146 */     public JBIG2Page(int page, JBIG2SegmentReader sr) { this.page = page;
/* 147 */       this.sr = sr;
/*     */     }
/*     */ 
/*     */     public byte[] getData(boolean for_embedding)
/*     */       throws IOException
/*     */     {
/* 158 */       ByteArrayOutputStream os = new ByteArrayOutputStream();
/* 159 */       for (Integer sn : this.segs.keySet()) {
/* 160 */         JBIG2SegmentReader.JBIG2Segment s = (JBIG2SegmentReader.JBIG2Segment)this.segs.get(sn);
/*     */ 
/* 164 */         if ((!for_embedding) || ((s.type != 51) && (s.type != 49)))
/*     */         {
/* 169 */           if (for_embedding)
/*     */           {
/* 171 */             byte[] headerData_emb = JBIG2SegmentReader.copyByteArray(s.headerData);
/* 172 */             if (s.page_association_size) {
/* 173 */               headerData_emb[s.page_association_offset] = 0;
/* 174 */               headerData_emb[(s.page_association_offset + 1)] = 0;
/* 175 */               headerData_emb[(s.page_association_offset + 2)] = 0;
/* 176 */               headerData_emb[(s.page_association_offset + 3)] = 1;
/*     */             } else {
/* 178 */               headerData_emb[s.page_association_offset] = 1;
/*     */             }
/* 180 */             os.write(headerData_emb);
/*     */           } else {
/* 182 */             os.write(s.headerData);
/*     */           }
/* 184 */           os.write(s.data);
/*     */         }
/*     */       }
/* 186 */       os.close();
/* 187 */       return os.toByteArray();
/*     */     }
/*     */     public void addSegment(JBIG2SegmentReader.JBIG2Segment s) {
/* 190 */       this.segs.put(Integer.valueOf(s.segmentNumber), s);
/*     */     }
/*     */   }
/*     */ 
/*     */   public static class JBIG2Segment
/*     */     implements Comparable<JBIG2Segment>
/*     */   {
/*     */     public final int segmentNumber;
/* 113 */     public long dataLength = -1L;
/* 114 */     public int page = -1;
/* 115 */     public int[] referredToSegmentNumbers = null;
/* 116 */     public boolean[] segmentRetentionFlags = null;
/* 117 */     public int type = -1;
/* 118 */     public boolean deferredNonRetain = false;
/* 119 */     public int countOfReferredToSegments = -1;
/* 120 */     public byte[] data = null;
/* 121 */     public byte[] headerData = null;
/* 122 */     public boolean page_association_size = false;
/* 123 */     public int page_association_offset = -1;
/*     */ 
/*     */     public JBIG2Segment(int segment_number) {
/* 126 */       this.segmentNumber = segment_number;
/*     */     }
/*     */ 
/*     */     public int compareTo(JBIG2Segment s) {
/* 130 */       return this.segmentNumber - s.segmentNumber;
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.pdf.codec.JBIG2SegmentReader
 * JD-Core Version:    0.6.2
 */