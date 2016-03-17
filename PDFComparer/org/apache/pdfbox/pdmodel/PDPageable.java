/*     */ package org.apache.pdfbox.pdmodel;
/*     */ 
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Graphics;
/*     */ import java.awt.print.PageFormat;
/*     */ import java.awt.print.Pageable;
/*     */ import java.awt.print.Paper;
/*     */ import java.awt.print.Printable;
/*     */ import java.awt.print.PrinterException;
/*     */ import java.awt.print.PrinterIOException;
/*     */ import java.awt.print.PrinterJob;
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import org.apache.pdfbox.pdfviewer.PageDrawer;
/*     */ import org.apache.pdfbox.pdmodel.common.PDRectangle;
/*     */ import org.apache.pdfbox.pdmodel.encryption.AccessPermission;
/*     */ 
/*     */ public class PDPageable
/*     */   implements Pageable, Printable
/*     */ {
/*  51 */   private final List<PDPage> pages = new ArrayList();
/*     */   private final PrinterJob job;
/*     */ 
/*     */   public PDPageable(PDDocument document, PrinterJob printerJob)
/*     */     throws IllegalArgumentException, PrinterException
/*     */   {
/*  69 */     if ((document == null) || (printerJob == null))
/*     */     {
/*  71 */       throw new IllegalArgumentException("PDPageable(" + document + ", " + printerJob + ")");
/*     */     }
/*     */ 
/*  74 */     if (!document.getCurrentAccessPermission().canPrint())
/*     */     {
/*  76 */       throw new PrinterException("You do not have permission to print this document");
/*     */     }
/*     */ 
/*  81 */     document.getDocumentCatalog().getPages().getAllKids(this.pages);
/*  82 */     this.job = printerJob;
/*     */   }
/*     */ 
/*     */   public PDPageable(PDDocument document)
/*     */     throws IllegalArgumentException, PrinterException
/*     */   {
/*  96 */     this(document, PrinterJob.getPrinterJob());
/*     */   }
/*     */ 
/*     */   public PrinterJob getPrinterJob()
/*     */   {
/* 106 */     return this.job;
/*     */   }
/*     */ 
/*     */   public int getNumberOfPages()
/*     */   {
/* 118 */     return this.pages.size();
/*     */   }
/*     */ 
/*     */   public PageFormat getPageFormat(int i)
/*     */     throws IndexOutOfBoundsException
/*     */   {
/* 130 */     PageFormat format = this.job.defaultPage();
/*     */ 
/* 132 */     PDPage page = (PDPage)this.pages.get(i);
/* 133 */     Dimension media = page.findMediaBox().createDimension();
/* 134 */     Dimension crop = page.findCropBox().createDimension();
/*     */ 
/* 137 */     double diffWidth = 0.0D;
/* 138 */     double diffHeight = 0.0D;
/* 139 */     if (!media.equals(crop))
/*     */     {
/* 141 */       diffWidth = (media.getWidth() - crop.getWidth()) / 2.0D;
/* 142 */       diffHeight = (media.getHeight() - crop.getHeight()) / 2.0D;
/*     */     }
/*     */ 
/* 145 */     Paper paper = format.getPaper();
/* 146 */     if (media.getWidth() < media.getHeight())
/*     */     {
/* 148 */       format.setOrientation(1);
/* 149 */       paper.setImageableArea(diffWidth, diffHeight, crop.getWidth(), crop.getHeight());
/*     */     }
/*     */     else
/*     */     {
/* 153 */       format.setOrientation(0);
/* 154 */       paper.setImageableArea(diffHeight, diffWidth, crop.getHeight(), crop.getWidth());
/*     */     }
/* 156 */     format.setPaper(paper);
/*     */ 
/* 158 */     return format;
/*     */   }
/*     */ 
/*     */   public Printable getPrintable(int i)
/*     */     throws IndexOutOfBoundsException
/*     */   {
/* 173 */     return (Printable)this.pages.get(i);
/*     */   }
/*     */ 
/*     */   public int print(Graphics graphics, PageFormat format, int i)
/*     */     throws PrinterException
/*     */   {
/* 190 */     if ((0 <= i) && (i < this.pages.size()))
/*     */     {
/*     */       try
/*     */       {
/* 194 */         PDPage page = (PDPage)this.pages.get(i);
/* 195 */         PDRectangle cropBox = page.findCropBox();
/* 196 */         PageDrawer drawer = new PageDrawer();
/* 197 */         drawer.drawPage(graphics, page, cropBox.createDimension());
/* 198 */         drawer.dispose();
/* 199 */         return 0;
/*     */       }
/*     */       catch (IOException io)
/*     */       {
/* 203 */         throw new PrinterIOException(io);
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 208 */     return 1;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.pdmodel.PDPageable
 * JD-Core Version:    0.6.2
 */