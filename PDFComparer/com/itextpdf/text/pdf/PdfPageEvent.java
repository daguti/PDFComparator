package com.itextpdf.text.pdf;

import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;

public abstract interface PdfPageEvent
{
  public abstract void onOpenDocument(PdfWriter paramPdfWriter, Document paramDocument);

  public abstract void onStartPage(PdfWriter paramPdfWriter, Document paramDocument);

  public abstract void onEndPage(PdfWriter paramPdfWriter, Document paramDocument);

  public abstract void onCloseDocument(PdfWriter paramPdfWriter, Document paramDocument);

  public abstract void onParagraph(PdfWriter paramPdfWriter, Document paramDocument, float paramFloat);

  public abstract void onParagraphEnd(PdfWriter paramPdfWriter, Document paramDocument, float paramFloat);

  public abstract void onChapter(PdfWriter paramPdfWriter, Document paramDocument, float paramFloat, Paragraph paramParagraph);

  public abstract void onChapterEnd(PdfWriter paramPdfWriter, Document paramDocument, float paramFloat);

  public abstract void onSection(PdfWriter paramPdfWriter, Document paramDocument, float paramFloat, int paramInt, Paragraph paramParagraph);

  public abstract void onSectionEnd(PdfWriter paramPdfWriter, Document paramDocument, float paramFloat);

  public abstract void onGenericTag(PdfWriter paramPdfWriter, Document paramDocument, Rectangle paramRectangle, String paramString);
}

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.pdf.PdfPageEvent
 * JD-Core Version:    0.6.2
 */