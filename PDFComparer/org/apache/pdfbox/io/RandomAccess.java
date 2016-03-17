package org.apache.pdfbox.io;

import java.io.IOException;

public abstract interface RandomAccess extends RandomAccessRead
{
  public abstract void write(int paramInt)
    throws IOException;

  public abstract void write(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
    throws IOException;
}

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.io.RandomAccess
 * JD-Core Version:    0.6.2
 */