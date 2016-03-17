package com.itextpdf.text.html.simpleparser;

import com.itextpdf.text.DocumentException;
import java.io.IOException;
import java.util.Map;

@Deprecated
public abstract interface HTMLTagProcessor
{
  public abstract void startElement(HTMLWorker paramHTMLWorker, String paramString, Map<String, String> paramMap)
    throws DocumentException, IOException;

  public abstract void endElement(HTMLWorker paramHTMLWorker, String paramString)
    throws DocumentException;
}

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.html.simpleparser.HTMLTagProcessor
 * JD-Core Version:    0.6.2
 */