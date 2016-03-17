package com.itextpdf.text.pdf.interfaces;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfAction;
import com.itextpdf.text.pdf.PdfName;
import com.itextpdf.text.pdf.PdfTransition;

public abstract interface PdfPageActions
{
  public abstract void setPageAction(PdfName paramPdfName, PdfAction paramPdfAction)
    throws DocumentException;

  public abstract void setDuration(int paramInt);

  public abstract void setTransition(PdfTransition paramPdfTransition);
}

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.pdf.interfaces.PdfPageActions
 * JD-Core Version:    0.6.2
 */