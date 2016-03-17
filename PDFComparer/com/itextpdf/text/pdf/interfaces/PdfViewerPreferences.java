package com.itextpdf.text.pdf.interfaces;

import com.itextpdf.text.pdf.PdfName;
import com.itextpdf.text.pdf.PdfObject;

public abstract interface PdfViewerPreferences
{
  public abstract void setViewerPreferences(int paramInt);

  public abstract void addViewerPreference(PdfName paramPdfName, PdfObject paramPdfObject);
}

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.pdf.interfaces.PdfViewerPreferences
 * JD-Core Version:    0.6.2
 */