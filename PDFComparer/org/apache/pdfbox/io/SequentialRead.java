package org.apache.pdfbox.io;

import java.io.IOException;

public abstract interface SequentialRead
{
  public abstract void close()
    throws IOException;

  public abstract int read()
    throws IOException;

  public abstract int read(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
    throws IOException;
}

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.io.SequentialRead
 * JD-Core Version:    0.6.2
 */