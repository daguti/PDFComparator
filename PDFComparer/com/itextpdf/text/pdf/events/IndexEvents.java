/*     */ package com.itextpdf.text.pdf.events;
/*     */ 
/*     */ import com.itextpdf.text.Chunk;
/*     */ import com.itextpdf.text.Document;
/*     */ import com.itextpdf.text.Rectangle;
/*     */ import com.itextpdf.text.pdf.PdfPageEventHelper;
/*     */ import com.itextpdf.text.pdf.PdfWriter;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.Comparator;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.TreeMap;
/*     */ 
/*     */ public class IndexEvents extends PdfPageEventHelper
/*     */ {
/*     */   private Map<String, Integer> indextag;
/*     */   private long indexcounter;
/*     */   private List<Entry> indexentry;
/*     */   private Comparator<Entry> comparator;
/*     */ 
/*     */   public IndexEvents()
/*     */   {
/*  71 */     this.indextag = new TreeMap();
/*     */ 
/*  90 */     this.indexcounter = 0L;
/*     */ 
/*  95 */     this.indexentry = new ArrayList();
/*     */ 
/* 183 */     this.comparator = new Comparator()
/*     */     {
/*     */       public int compare(IndexEvents.Entry en1, IndexEvents.Entry en2) {
/* 186 */         int rt = 0;
/* 187 */         if ((en1.getIn1() != null) && (en2.getIn1() != null) && 
/* 188 */           ((rt = en1.getIn1().compareToIgnoreCase(en2.getIn1())) == 0))
/*     */         {
/* 190 */           if ((en1.getIn2() != null) && (en2.getIn2() != null) && 
/* 191 */             ((rt = en1.getIn2().compareToIgnoreCase(en2.getIn2())) == 0))
/*     */           {
/* 194 */             if ((en1.getIn3() != null) && (en2.getIn3() != null)) {
/* 195 */               rt = en1.getIn3().compareToIgnoreCase(en2.getIn3());
/*     */             }
/*     */ 
/*     */           }
/*     */ 
/*     */         }
/*     */ 
/* 202 */         return rt;
/*     */       }
/*     */     };
/*     */   }
/*     */ 
/*     */   public void onGenericTag(PdfWriter writer, Document document, Rectangle rect, String text)
/*     */   {
/*  83 */     this.indextag.put(text, Integer.valueOf(writer.getPageNumber()));
/*     */   }
/*     */ 
/*     */   public Chunk create(String text, String in1, String in2, String in3)
/*     */   {
/* 109 */     Chunk chunk = new Chunk(text);
/* 110 */     String tag = "idx_" + this.indexcounter++;
/* 111 */     chunk.setGenericTag(tag);
/* 112 */     chunk.setLocalDestination(tag);
/* 113 */     Entry entry = new Entry(in1, in2, in3, tag);
/* 114 */     this.indexentry.add(entry);
/* 115 */     return chunk;
/*     */   }
/*     */ 
/*     */   public Chunk create(String text, String in1)
/*     */   {
/* 126 */     return create(text, in1, "", "");
/*     */   }
/*     */ 
/*     */   public Chunk create(String text, String in1, String in2)
/*     */   {
/* 138 */     return create(text, in1, in2, "");
/*     */   }
/*     */ 
/*     */   public void create(Chunk text, String in1, String in2, String in3)
/*     */   {
/* 152 */     String tag = "idx_" + this.indexcounter++;
/* 153 */     text.setGenericTag(tag);
/* 154 */     text.setLocalDestination(tag);
/* 155 */     Entry entry = new Entry(in1, in2, in3, tag);
/* 156 */     this.indexentry.add(entry);
/*     */   }
/*     */ 
/*     */   public void create(Chunk text, String in1)
/*     */   {
/* 166 */     create(text, in1, "", "");
/*     */   }
/*     */ 
/*     */   public void create(Chunk text, String in1, String in2)
/*     */   {
/* 177 */     create(text, in1, in2, "");
/*     */   }
/*     */ 
/*     */   public void setComparator(Comparator<Entry> aComparator)
/*     */   {
/* 211 */     this.comparator = aComparator;
/*     */   }
/*     */ 
/*     */   public List<Entry> getSortedEntries()
/*     */   {
/* 220 */     Map grouped = new HashMap();
/*     */ 
/* 222 */     for (int i = 0; i < this.indexentry.size(); i++) {
/* 223 */       Entry e = (Entry)this.indexentry.get(i);
/* 224 */       String key = e.getKey();
/*     */ 
/* 226 */       Entry master = (Entry)grouped.get(key);
/* 227 */       if (master != null) {
/* 228 */         master.addPageNumberAndTag(e.getPageNumber(), e.getTag());
/*     */       } else {
/* 230 */         e.addPageNumberAndTag(e.getPageNumber(), e.getTag());
/* 231 */         grouped.put(key, e);
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 236 */     List sorted = new ArrayList(grouped.values());
/* 237 */     Collections.sort(sorted, this.comparator);
/* 238 */     return sorted;
/*     */   }
/*     */ 
/*     */   public class Entry
/*     */   {
/*     */     private String in1;
/*     */     private String in2;
/*     */     private String in3;
/*     */     private String tag;
/* 274 */     private List<Integer> pagenumbers = new ArrayList();
/*     */ 
/* 279 */     private List<String> tags = new ArrayList();
/*     */ 
/*     */     public Entry(String aIn1, String aIn2, String aIn3, String aTag)
/*     */     {
/* 290 */       this.in1 = aIn1;
/* 291 */       this.in2 = aIn2;
/* 292 */       this.in3 = aIn3;
/* 293 */       this.tag = aTag;
/*     */     }
/*     */ 
/*     */     public String getIn1()
/*     */     {
/* 301 */       return this.in1;
/*     */     }
/*     */ 
/*     */     public String getIn2()
/*     */     {
/* 309 */       return this.in2;
/*     */     }
/*     */ 
/*     */     public String getIn3()
/*     */     {
/* 317 */       return this.in3;
/*     */     }
/*     */ 
/*     */     public String getTag()
/*     */     {
/* 325 */       return this.tag;
/*     */     }
/*     */ 
/*     */     public int getPageNumber()
/*     */     {
/* 333 */       int rt = -1;
/* 334 */       Integer i = (Integer)IndexEvents.this.indextag.get(this.tag);
/* 335 */       if (i != null) {
/* 336 */         rt = i.intValue();
/*     */       }
/* 338 */       return rt;
/*     */     }
/*     */ 
/*     */     public void addPageNumberAndTag(int number, String tag)
/*     */     {
/* 347 */       this.pagenumbers.add(Integer.valueOf(number));
/* 348 */       this.tags.add(tag);
/*     */     }
/*     */ 
/*     */     public String getKey()
/*     */     {
/* 356 */       return this.in1 + "!" + this.in2 + "!" + this.in3;
/*     */     }
/*     */ 
/*     */     public List<Integer> getPagenumbers()
/*     */     {
/* 364 */       return this.pagenumbers;
/*     */     }
/*     */ 
/*     */     public List<String> getTags()
/*     */     {
/* 372 */       return this.tags;
/*     */     }
/*     */ 
/*     */     public String toString()
/*     */     {
/* 381 */       StringBuffer buf = new StringBuffer();
/* 382 */       buf.append(this.in1).append(' ');
/* 383 */       buf.append(this.in2).append(' ');
/* 384 */       buf.append(this.in3).append(' ');
/* 385 */       for (int i = 0; i < this.pagenumbers.size(); i++) {
/* 386 */         buf.append(this.pagenumbers.get(i)).append(' ');
/*     */       }
/* 388 */       return buf.toString();
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.pdf.events.IndexEvents
 * JD-Core Version:    0.6.2
 */