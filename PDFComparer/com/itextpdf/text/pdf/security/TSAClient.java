package com.itextpdf.text.pdf.security;

import java.security.GeneralSecurityException;
import java.security.MessageDigest;

public abstract interface TSAClient
{
  public abstract int getTokenSizeEstimate();

  public abstract MessageDigest getMessageDigest()
    throws GeneralSecurityException;

  public abstract byte[] getTimeStampToken(byte[] paramArrayOfByte)
    throws Exception;
}

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.pdf.security.TSAClient
 * JD-Core Version:    0.6.2
 */