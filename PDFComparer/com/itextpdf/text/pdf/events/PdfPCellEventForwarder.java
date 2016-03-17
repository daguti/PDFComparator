/*    */ package com.itextpdf.text.pdf.events;
/*    */ 
/*    */ import com.itextpdf.text.Rectangle;
/*    */ import com.itextpdf.text.pdf.PdfContentByte;
/*    */ import com.itextpdf.text.pdf.PdfPCell;
/*    */ import com.itextpdf.text.pdf.PdfPCellEvent;
/*    */ import java.util.ArrayList;
/*    */ 
/*    */ public class PdfPCellEventForwarder
/*    */   implements PdfPCellEvent
/*    */ {
/* 64 */   protected ArrayList<PdfPCellEvent> events = new ArrayList();
/*    */ 
/*    */   public void addCellEvent(PdfPCellEvent event)
/*    */   {
/* 71 */     this.events.add(event);
/*    */   }
/*    */ 
/*    */   public void cellLayout(PdfPCell cell, Rectangle position, PdfContentByte[] canvases)
/*    */   {
/* 78 */     for (PdfPCellEvent event : this.events)
/* 79 */       event.cellLayout(cell, position, canvases);
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.pdf.events.PdfPCellEventForwarder
 * JD-Core Version:    0.6.2
 */