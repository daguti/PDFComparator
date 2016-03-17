/*     */ package com.itextpdf.text.pdf.events;
/*     */ 
/*     */ import com.itextpdf.text.pdf.PdfContentByte;
/*     */ import com.itextpdf.text.pdf.PdfPRow;
/*     */ import com.itextpdf.text.pdf.PdfPTable;
/*     */ import com.itextpdf.text.pdf.PdfPTableEvent;
/*     */ import com.itextpdf.text.pdf.PdfPTableEventAfterSplit;
/*     */ import com.itextpdf.text.pdf.PdfPTableEventSplit;
/*     */ import java.util.ArrayList;
/*     */ 
/*     */ public class PdfPTableEventForwarder
/*     */   implements PdfPTableEventAfterSplit
/*     */ {
/*  66 */   protected ArrayList<PdfPTableEvent> events = new ArrayList();
/*     */ 
/*     */   public void addTableEvent(PdfPTableEvent event)
/*     */   {
/*  73 */     this.events.add(event);
/*     */   }
/*     */ 
/*     */   public void tableLayout(PdfPTable table, float[][] widths, float[] heights, int headerRows, int rowStart, PdfContentByte[] canvases)
/*     */   {
/*  80 */     for (PdfPTableEvent event : this.events)
/*  81 */       event.tableLayout(table, widths, heights, headerRows, rowStart, canvases);
/*     */   }
/*     */ 
/*     */   public void splitTable(PdfPTable table)
/*     */   {
/*  90 */     for (PdfPTableEvent event : this.events)
/*  91 */       if ((event instanceof PdfPTableEventSplit))
/*  92 */         ((PdfPTableEventSplit)event).splitTable(table);
/*     */   }
/*     */ 
/*     */   public void afterSplitTable(PdfPTable table, PdfPRow startRow, int startIdx)
/*     */   {
/* 101 */     for (PdfPTableEvent event : this.events)
/* 102 */       if ((event instanceof PdfPTableEventAfterSplit))
/* 103 */         ((PdfPTableEventAfterSplit)event).afterSplitTable(table, startRow, startIdx);
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.pdf.events.PdfPTableEventForwarder
 * JD-Core Version:    0.6.2
 */