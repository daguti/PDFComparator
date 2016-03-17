/*     */ package com.itextpdf.text.pdf.events;
/*     */ 
/*     */ import com.itextpdf.text.Document;
/*     */ import com.itextpdf.text.Paragraph;
/*     */ import com.itextpdf.text.Rectangle;
/*     */ import com.itextpdf.text.pdf.PdfPageEvent;
/*     */ import com.itextpdf.text.pdf.PdfWriter;
/*     */ import java.util.ArrayList;
/*     */ 
/*     */ public class PdfPageEventForwarder
/*     */   implements PdfPageEvent
/*     */ {
/*  65 */   protected ArrayList<PdfPageEvent> events = new ArrayList();
/*     */ 
/*     */   public void addPageEvent(PdfPageEvent event)
/*     */   {
/*  72 */     this.events.add(event);
/*     */   }
/*     */ 
/*     */   public void onOpenDocument(PdfWriter writer, Document document)
/*     */   {
/*  84 */     for (PdfPageEvent event : this.events)
/*  85 */       event.onOpenDocument(writer, document);
/*     */   }
/*     */ 
/*     */   public void onStartPage(PdfWriter writer, Document document)
/*     */   {
/* 101 */     for (PdfPageEvent event : this.events)
/* 102 */       event.onStartPage(writer, document);
/*     */   }
/*     */ 
/*     */   public void onEndPage(PdfWriter writer, Document document)
/*     */   {
/* 116 */     for (PdfPageEvent event : this.events)
/* 117 */       event.onEndPage(writer, document);
/*     */   }
/*     */ 
/*     */   public void onCloseDocument(PdfWriter writer, Document document)
/*     */   {
/* 133 */     for (PdfPageEvent event : this.events)
/* 134 */       event.onCloseDocument(writer, document);
/*     */   }
/*     */ 
/*     */   public void onParagraph(PdfWriter writer, Document document, float paragraphPosition)
/*     */   {
/* 154 */     for (PdfPageEvent event : this.events)
/* 155 */       event.onParagraph(writer, document, paragraphPosition);
/*     */   }
/*     */ 
/*     */   public void onParagraphEnd(PdfWriter writer, Document document, float paragraphPosition)
/*     */   {
/* 174 */     for (PdfPageEvent event : this.events)
/* 175 */       event.onParagraphEnd(writer, document, paragraphPosition);
/*     */   }
/*     */ 
/*     */   public void onChapter(PdfWriter writer, Document document, float paragraphPosition, Paragraph title)
/*     */   {
/* 196 */     for (PdfPageEvent event : this.events)
/* 197 */       event.onChapter(writer, document, paragraphPosition, title);
/*     */   }
/*     */ 
/*     */   public void onChapterEnd(PdfWriter writer, Document document, float position)
/*     */   {
/* 214 */     for (PdfPageEvent event : this.events)
/* 215 */       event.onChapterEnd(writer, document, position);
/*     */   }
/*     */ 
/*     */   public void onSection(PdfWriter writer, Document document, float paragraphPosition, int depth, Paragraph title)
/*     */   {
/* 238 */     for (PdfPageEvent event : this.events)
/* 239 */       event.onSection(writer, document, paragraphPosition, depth, title);
/*     */   }
/*     */ 
/*     */   public void onSectionEnd(PdfWriter writer, Document document, float position)
/*     */   {
/* 256 */     for (PdfPageEvent event : this.events)
/* 257 */       event.onSectionEnd(writer, document, position);
/*     */   }
/*     */ 
/*     */   public void onGenericTag(PdfWriter writer, Document document, Rectangle rect, String text)
/*     */   {
/* 280 */     for (Object element : this.events) {
/* 281 */       PdfPageEvent event = (PdfPageEvent)element;
/* 282 */       event.onGenericTag(writer, document, rect, text);
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.pdf.events.PdfPageEventForwarder
 * JD-Core Version:    0.6.2
 */