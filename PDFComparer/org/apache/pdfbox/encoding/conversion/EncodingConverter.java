package org.apache.pdfbox.encoding.conversion;

import org.apache.fontbox.cmap.CMap;

public abstract interface EncodingConverter
{
  public abstract String convertString(String paramString);

  public abstract String convertBytes(byte[] paramArrayOfByte, int paramInt1, int paramInt2, CMap paramCMap);
}

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.encoding.conversion.EncodingConverter
 * JD-Core Version:    0.6.2
 */