package com.itextpdf.text.pdf.interfaces;

import com.itextpdf.text.pdf.PdfDeveloperExtension;
import com.itextpdf.text.pdf.PdfName;

public abstract interface PdfVersion
{
  public abstract void setPdfVersion(char paramChar);

  public abstract void setAtLeastPdfVersion(char paramChar);

  public abstract void setPdfVersion(PdfName paramPdfName);

  public abstract void addDeveloperExtension(PdfDeveloperExtension paramPdfDeveloperExtension);
}

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.pdf.interfaces.PdfVersion
 * JD-Core Version:    0.6.2
 */