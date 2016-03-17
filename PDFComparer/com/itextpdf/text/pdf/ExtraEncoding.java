package com.itextpdf.text.pdf;

public abstract interface ExtraEncoding
{
  public abstract byte[] charToByte(String paramString1, String paramString2);

  public abstract byte[] charToByte(char paramChar, String paramString);

  public abstract String byteToChar(byte[] paramArrayOfByte, String paramString);
}

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.pdf.ExtraEncoding
 * JD-Core Version:    0.6.2
 */