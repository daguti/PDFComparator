package org.apache.pdfbox.filter;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.apache.pdfbox.cos.COSDictionary;

public abstract interface Filter
{
  public abstract void decode(InputStream paramInputStream, OutputStream paramOutputStream, COSDictionary paramCOSDictionary, int paramInt)
    throws IOException;

  public abstract void encode(InputStream paramInputStream, OutputStream paramOutputStream, COSDictionary paramCOSDictionary, int paramInt)
    throws IOException;
}

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.filter.Filter
 * JD-Core Version:    0.6.2
 */