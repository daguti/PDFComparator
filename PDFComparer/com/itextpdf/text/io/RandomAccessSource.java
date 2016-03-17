package com.itextpdf.text.io;

import java.io.IOException;

public abstract interface RandomAccessSource
{
  public abstract int get(long paramLong)
    throws IOException;

  public abstract int get(long paramLong, byte[] paramArrayOfByte, int paramInt1, int paramInt2)
    throws IOException;

  public abstract long length();

  public abstract void close()
    throws IOException;
}

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.io.RandomAccessSource
 * JD-Core Version:    0.6.2
 */