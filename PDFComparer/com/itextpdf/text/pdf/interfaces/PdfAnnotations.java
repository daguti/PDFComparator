package com.itextpdf.text.pdf.interfaces;

import com.itextpdf.text.pdf.PdfAcroForm;
import com.itextpdf.text.pdf.PdfAnnotation;
import com.itextpdf.text.pdf.PdfFormField;

public abstract interface PdfAnnotations
{
  public abstract PdfAcroForm getAcroForm();

  public abstract void addAnnotation(PdfAnnotation paramPdfAnnotation);

  public abstract void addCalculationOrder(PdfFormField paramPdfFormField);

  public abstract void setSigFlags(int paramInt);
}

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.pdf.interfaces.PdfAnnotations
 * JD-Core Version:    0.6.2
 */