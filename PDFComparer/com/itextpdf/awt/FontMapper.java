package com.itextpdf.awt;

import com.itextpdf.text.pdf.BaseFont;
import java.awt.Font;

public abstract interface FontMapper
{
  public abstract BaseFont awtToPdf(Font paramFont);

  public abstract Font pdfToAwt(BaseFont paramBaseFont, int paramInt);
}

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.awt.FontMapper
 * JD-Core Version:    0.6.2
 */