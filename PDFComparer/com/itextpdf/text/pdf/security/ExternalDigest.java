package com.itextpdf.text.pdf.security;

import java.security.GeneralSecurityException;
import java.security.MessageDigest;

public abstract interface ExternalDigest
{
  public abstract MessageDigest getMessageDigest(String paramString)
    throws GeneralSecurityException;
}

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.pdf.security.ExternalDigest
 * JD-Core Version:    0.6.2
 */