package com.itextpdf.text.pdf;

import com.itextpdf.text.Rectangle;

public abstract interface PdfPCellEvent
{
  public abstract void cellLayout(PdfPCell paramPdfPCell, Rectangle paramRectangle, PdfContentByte[] paramArrayOfPdfContentByte);
}

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.pdf.PdfPCellEvent
 * JD-Core Version:    0.6.2
 */