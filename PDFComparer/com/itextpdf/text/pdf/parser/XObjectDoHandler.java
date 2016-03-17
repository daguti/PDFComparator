package com.itextpdf.text.pdf.parser;

import com.itextpdf.text.pdf.PdfIndirectReference;
import com.itextpdf.text.pdf.PdfStream;

public abstract interface XObjectDoHandler
{
  public abstract void handleXObject(PdfContentStreamProcessor paramPdfContentStreamProcessor, PdfStream paramPdfStream, PdfIndirectReference paramPdfIndirectReference);
}

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.pdf.parser.XObjectDoHandler
 * JD-Core Version:    0.6.2
 */