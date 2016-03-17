package com.itextpdf.text.api;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfWriter;

public abstract interface WriterOperation
{
  public abstract void write(PdfWriter paramPdfWriter, Document paramDocument)
    throws DocumentException;
}

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.api.WriterOperation
 * JD-Core Version:    0.6.2
 */