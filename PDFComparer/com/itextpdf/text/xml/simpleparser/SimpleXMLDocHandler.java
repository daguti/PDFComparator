package com.itextpdf.text.xml.simpleparser;

import java.util.Map;

public abstract interface SimpleXMLDocHandler
{
  public abstract void startElement(String paramString, Map<String, String> paramMap);

  public abstract void endElement(String paramString);

  public abstract void startDocument();

  public abstract void endDocument();

  public abstract void text(String paramString);
}

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.xml.simpleparser.SimpleXMLDocHandler
 * JD-Core Version:    0.6.2
 */