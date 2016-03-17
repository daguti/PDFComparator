package com.itextpdf.text.pdf.interfaces;

import com.itextpdf.text.AccessibleElementId;
import com.itextpdf.text.pdf.PdfName;
import com.itextpdf.text.pdf.PdfObject;
import java.util.HashMap;

public abstract interface IAccessibleElement
{
  public abstract PdfObject getAccessibleAttribute(PdfName paramPdfName);

  public abstract void setAccessibleAttribute(PdfName paramPdfName, PdfObject paramPdfObject);

  public abstract HashMap<PdfName, PdfObject> getAccessibleAttributes();

  public abstract PdfName getRole();

  public abstract void setRole(PdfName paramPdfName);

  public abstract AccessibleElementId getId();

  public abstract void setId(AccessibleElementId paramAccessibleElementId);

  public abstract boolean isInline();
}

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.pdf.interfaces.IAccessibleElement
 * JD-Core Version:    0.6.2
 */