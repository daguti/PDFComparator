package com.itextpdf.text.pdf.interfaces;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.PdfAction;
import com.itextpdf.text.pdf.PdfName;

public abstract interface PdfDocumentActions
{
  public abstract void setOpenAction(String paramString);

  public abstract void setOpenAction(PdfAction paramPdfAction);

  public abstract void setAdditionalAction(PdfName paramPdfName, PdfAction paramPdfAction)
    throws DocumentException;
}

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.pdf.interfaces.PdfDocumentActions
 * JD-Core Version:    0.6.2
 */