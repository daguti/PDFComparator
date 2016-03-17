package com.itextpdf.text.pdf.fonts.cmaps;

import com.itextpdf.text.pdf.PRTokeniser;
import java.io.IOException;

public abstract interface CidLocation
{
  public abstract PRTokeniser getLocation(String paramString)
    throws IOException;
}

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.pdf.fonts.cmaps.CidLocation
 * JD-Core Version:    0.6.2
 */