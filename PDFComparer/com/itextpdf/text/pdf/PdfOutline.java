/*     */ package com.itextpdf.text.pdf;
/*     */ 
/*     */ import com.itextpdf.text.BaseColor;
/*     */ import com.itextpdf.text.Chunk;
/*     */ import com.itextpdf.text.Paragraph;
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStream;
/*     */ import java.util.ArrayList;
/*     */ 
/*     */ public class PdfOutline extends PdfDictionary
/*     */ {
/*     */   private PdfIndirectReference reference;
/*  74 */   private int count = 0;
/*     */   private PdfOutline parent;
/*     */   private PdfDestination destination;
/*     */   private PdfAction action;
/*  86 */   protected ArrayList<PdfOutline> kids = new ArrayList();
/*     */   protected PdfWriter writer;
/*     */   private String tag;
/*     */   private boolean open;
/*     */   private BaseColor color;
/* 100 */   private int style = 0;
/*     */ 
/*     */   PdfOutline(PdfWriter writer)
/*     */   {
/* 113 */     super(OUTLINES);
/* 114 */     this.open = true;
/* 115 */     this.parent = null;
/* 116 */     this.writer = writer;
/*     */   }
/*     */ 
/*     */   public PdfOutline(PdfOutline parent, PdfAction action, String title)
/*     */   {
/* 131 */     this(parent, action, title, true);
/*     */   }
/*     */ 
/*     */   public PdfOutline(PdfOutline parent, PdfAction action, String title, boolean open)
/*     */   {
/* 146 */     this.action = action;
/* 147 */     initOutline(parent, title, open);
/*     */   }
/*     */ 
/*     */   public PdfOutline(PdfOutline parent, PdfDestination destination, String title)
/*     */   {
/* 162 */     this(parent, destination, title, true);
/*     */   }
/*     */ 
/*     */   public PdfOutline(PdfOutline parent, PdfDestination destination, String title, boolean open)
/*     */   {
/* 177 */     this.destination = destination;
/* 178 */     initOutline(parent, title, open);
/*     */   }
/*     */ 
/*     */   public PdfOutline(PdfOutline parent, PdfAction action, PdfString title)
/*     */   {
/* 192 */     this(parent, action, title, true);
/*     */   }
/*     */ 
/*     */   public PdfOutline(PdfOutline parent, PdfAction action, PdfString title, boolean open)
/*     */   {
/* 206 */     this(parent, action, title.toString(), open);
/*     */   }
/*     */ 
/*     */   public PdfOutline(PdfOutline parent, PdfDestination destination, PdfString title)
/*     */   {
/* 221 */     this(parent, destination, title, true);
/*     */   }
/*     */ 
/*     */   public PdfOutline(PdfOutline parent, PdfDestination destination, PdfString title, boolean open)
/*     */   {
/* 235 */     this(parent, destination, title.toString(), true);
/*     */   }
/*     */ 
/*     */   public PdfOutline(PdfOutline parent, PdfAction action, Paragraph title)
/*     */   {
/* 250 */     this(parent, action, title, true);
/*     */   }
/*     */ 
/*     */   public PdfOutline(PdfOutline parent, PdfAction action, Paragraph title, boolean open)
/*     */   {
/* 265 */     StringBuffer buf = new StringBuffer();
/* 266 */     for (Chunk chunk : title.getChunks()) {
/* 267 */       buf.append(chunk.getContent());
/*     */     }
/* 269 */     this.action = action;
/* 270 */     initOutline(parent, buf.toString(), open);
/*     */   }
/*     */ 
/*     */   public PdfOutline(PdfOutline parent, PdfDestination destination, Paragraph title)
/*     */   {
/* 285 */     this(parent, destination, title, true);
/*     */   }
/*     */ 
/*     */   public PdfOutline(PdfOutline parent, PdfDestination destination, Paragraph title, boolean open)
/*     */   {
/* 300 */     StringBuffer buf = new StringBuffer();
/* 301 */     for (Object element : title.getChunks()) {
/* 302 */       Chunk chunk = (Chunk)element;
/* 303 */       buf.append(chunk.getContent());
/*     */     }
/* 305 */     this.destination = destination;
/* 306 */     initOutline(parent, buf.toString(), open);
/*     */   }
/*     */ 
/*     */   void initOutline(PdfOutline parent, String title, boolean open)
/*     */   {
/* 318 */     this.open = open;
/* 319 */     this.parent = parent;
/* 320 */     this.writer = parent.writer;
/* 321 */     put(PdfName.TITLE, new PdfString(title, "UnicodeBig"));
/* 322 */     parent.addKid(this);
/* 323 */     if ((this.destination != null) && (!this.destination.hasPage()))
/* 324 */       setDestinationPage(this.writer.getCurrentPage());
/*     */   }
/*     */ 
/*     */   public void setIndirectReference(PdfIndirectReference reference)
/*     */   {
/* 334 */     this.reference = reference;
/*     */   }
/*     */ 
/*     */   public PdfIndirectReference indirectReference()
/*     */   {
/* 344 */     return this.reference;
/*     */   }
/*     */ 
/*     */   public PdfOutline parent()
/*     */   {
/* 354 */     return this.parent;
/*     */   }
/*     */ 
/*     */   public boolean setDestinationPage(PdfIndirectReference pageReference)
/*     */   {
/* 365 */     if (this.destination == null) {
/* 366 */       return false;
/*     */     }
/* 368 */     return this.destination.addPage(pageReference);
/*     */   }
/*     */ 
/*     */   public PdfDestination getPdfDestination()
/*     */   {
/* 376 */     return this.destination;
/*     */   }
/*     */ 
/*     */   int getCount() {
/* 380 */     return this.count;
/*     */   }
/*     */ 
/*     */   void setCount(int count) {
/* 384 */     this.count = count;
/*     */   }
/*     */ 
/*     */   public int level()
/*     */   {
/* 394 */     if (this.parent == null) {
/* 395 */       return 0;
/*     */     }
/* 397 */     return this.parent.level() + 1;
/*     */   }
/*     */ 
/*     */   public void toPdf(PdfWriter writer, OutputStream os)
/*     */     throws IOException
/*     */   {
/* 410 */     if ((this.color != null) && (!this.color.equals(BaseColor.BLACK))) {
/* 411 */       put(PdfName.C, new PdfArray(new float[] { this.color.getRed() / 255.0F, this.color.getGreen() / 255.0F, this.color.getBlue() / 255.0F }));
/*     */     }
/* 413 */     int flag = 0;
/* 414 */     if ((this.style & 0x1) != 0)
/* 415 */       flag |= 2;
/* 416 */     if ((this.style & 0x2) != 0)
/* 417 */       flag |= 1;
/* 418 */     if (flag != 0)
/* 419 */       put(PdfName.F, new PdfNumber(flag));
/* 420 */     if (this.parent != null) {
/* 421 */       put(PdfName.PARENT, this.parent.indirectReference());
/*     */     }
/* 423 */     if ((this.destination != null) && (this.destination.hasPage())) {
/* 424 */       put(PdfName.DEST, this.destination);
/*     */     }
/* 426 */     if (this.action != null)
/* 427 */       put(PdfName.A, this.action);
/* 428 */     if (this.count != 0) {
/* 429 */       put(PdfName.COUNT, new PdfNumber(this.count));
/*     */     }
/* 431 */     super.toPdf(writer, os);
/*     */   }
/*     */ 
/*     */   public void addKid(PdfOutline outline)
/*     */   {
/* 439 */     this.kids.add(outline);
/*     */   }
/*     */ 
/*     */   public ArrayList<PdfOutline> getKids()
/*     */   {
/* 447 */     return this.kids;
/*     */   }
/*     */ 
/*     */   public void setKids(ArrayList<PdfOutline> kids)
/*     */   {
/* 455 */     this.kids = kids;
/*     */   }
/*     */ 
/*     */   public String getTag()
/*     */   {
/* 462 */     return this.tag;
/*     */   }
/*     */ 
/*     */   public void setTag(String tag)
/*     */   {
/* 469 */     this.tag = tag;
/*     */   }
/*     */ 
/*     */   public String getTitle()
/*     */   {
/* 477 */     PdfString title = (PdfString)get(PdfName.TITLE);
/* 478 */     return title.toString();
/*     */   }
/*     */ 
/*     */   public void setTitle(String title)
/*     */   {
/* 486 */     put(PdfName.TITLE, new PdfString(title, "UnicodeBig"));
/*     */   }
/*     */ 
/*     */   public boolean isOpen()
/*     */   {
/* 493 */     return this.open;
/*     */   }
/*     */ 
/*     */   public void setOpen(boolean open)
/*     */   {
/* 500 */     this.open = open;
/*     */   }
/*     */ 
/*     */   public BaseColor getColor()
/*     */   {
/* 508 */     return this.color;
/*     */   }
/*     */ 
/*     */   public void setColor(BaseColor color)
/*     */   {
/* 516 */     this.color = color;
/*     */   }
/*     */ 
/*     */   public int getStyle()
/*     */   {
/* 524 */     return this.style;
/*     */   }
/*     */ 
/*     */   public void setStyle(int style)
/*     */   {
/* 532 */     this.style = style;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.pdf.PdfOutline
 * JD-Core Version:    0.6.2
 */