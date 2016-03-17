package com.itextpdf.text.pdf.security;

import com.itextpdf.text.DocumentException;
import java.io.IOException;
import org.w3c.dom.Document;

public abstract interface XmlLocator
{
  public abstract Document getDocument();

  public abstract void setDocument(Document paramDocument)
    throws IOException, DocumentException;

  public abstract String getEncoding();
}

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.pdf.security.XmlLocator
 * JD-Core Version:    0.6.2
 */