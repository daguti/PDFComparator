package com.itextpdf.text.pdf.security;

import java.security.GeneralSecurityException;

public abstract interface ExternalSignature
{
  public abstract String getHashAlgorithm();

  public abstract String getEncryptionAlgorithm();

  public abstract byte[] sign(byte[] paramArrayOfByte)
    throws GeneralSecurityException;
}

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.pdf.security.ExternalSignature
 * JD-Core Version:    0.6.2
 */