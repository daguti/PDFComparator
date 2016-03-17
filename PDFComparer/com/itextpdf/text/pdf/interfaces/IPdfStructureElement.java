package com.itextpdf.text.pdf.interfaces;

import com.itextpdf.text.pdf.PdfName;
import com.itextpdf.text.pdf.PdfObject;

public abstract interface IPdfStructureElement
{
  public abstract PdfObject getAttribute(PdfName paramPdfName);

  public abstract void setAttribute(PdfName paramPdfName, PdfObject paramPdfObject);
}

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.pdf.interfaces.IPdfStructureElement
 * JD-Core Version:    0.6.2
 */