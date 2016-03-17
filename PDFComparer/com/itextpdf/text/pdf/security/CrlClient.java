package com.itextpdf.text.pdf.security;

import java.security.cert.X509Certificate;
import java.util.Collection;

public abstract interface CrlClient
{
  public abstract Collection<byte[]> getEncoded(X509Certificate paramX509Certificate, String paramString);
}

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.pdf.security.CrlClient
 * JD-Core Version:    0.6.2
 */