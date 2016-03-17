/*    */ package com.itextpdf.awt;
/*    */ 
/*    */ import com.itextpdf.text.pdf.PdfContentByte;
/*    */ import java.awt.print.PrinterGraphics;
/*    */ import java.awt.print.PrinterJob;
/*    */ 
/*    */ public class PdfPrinterGraphics2D extends PdfGraphics2D
/*    */   implements PrinterGraphics
/*    */ {
/*    */   private PrinterJob printerJob;
/*    */ 
/*    */   public PdfPrinterGraphics2D(PdfContentByte cb, float width, float height, PrinterJob printerJob)
/*    */   {
/* 61 */     super(cb, width, height);
/* 62 */     this.printerJob = printerJob;
/*    */   }
/*    */ 
/*    */   public PdfPrinterGraphics2D(PdfContentByte cb, float width, float height, boolean onlyShapes, PrinterJob printerJob) {
/* 66 */     super(cb, width, height, onlyShapes);
/* 67 */     this.printerJob = printerJob;
/*    */   }
/*    */   public PdfPrinterGraphics2D(PdfContentByte cb, float width, float height, FontMapper fontMapper, PrinterJob printerJob) {
/* 70 */     super(cb, width, height, fontMapper, false, false, 0.0F);
/* 71 */     this.printerJob = printerJob;
/*    */   }
/*    */ 
/*    */   public PdfPrinterGraphics2D(PdfContentByte cb, float width, float height, FontMapper fontMapper, boolean onlyShapes, boolean convertImagesToJPEG, float quality, PrinterJob printerJob) {
/* 75 */     super(cb, width, height, fontMapper, onlyShapes, convertImagesToJPEG, quality);
/* 76 */     this.printerJob = printerJob;
/*    */   }
/*    */ 
/*    */   public PrinterJob getPrinterJob() {
/* 80 */     return this.printerJob;
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.awt.PdfPrinterGraphics2D
 * JD-Core Version:    0.6.2
 */