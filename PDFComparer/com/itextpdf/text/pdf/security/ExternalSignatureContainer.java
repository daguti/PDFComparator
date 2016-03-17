package com.itextpdf.text.pdf.security;

import com.itextpdf.text.pdf.PdfDictionary;
import java.io.InputStream;
import java.security.GeneralSecurityException;

public abstract interface ExternalSignatureContainer
{
  public abstract byte[] sign(InputStream paramInputStream)
    throws GeneralSecurityException;

  public abstract void modifySigningDictionary(PdfDictionary paramPdfDictionary);
}

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.pdf.security.ExternalSignatureContainer
 * JD-Core Version:    0.6.2
 */