package com.itextpdf.text.pdf.security;

import java.security.cert.X509Certificate;

public abstract interface OcspClient
{
  public abstract byte[] getEncoded(X509Certificate paramX509Certificate1, X509Certificate paramX509Certificate2, String paramString);
}

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.pdf.security.OcspClient
 * JD-Core Version:    0.6.2
 */