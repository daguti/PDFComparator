package com.itextpdf.text.pdf.interfaces;

import com.itextpdf.text.DocumentException;
import java.security.cert.Certificate;

public abstract interface PdfEncryptionSettings
{
  public abstract void setEncryption(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2, int paramInt1, int paramInt2)
    throws DocumentException;

  public abstract void setEncryption(Certificate[] paramArrayOfCertificate, int[] paramArrayOfInt, int paramInt)
    throws DocumentException;
}

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.pdf.interfaces.PdfEncryptionSettings
 * JD-Core Version:    0.6.2
 */