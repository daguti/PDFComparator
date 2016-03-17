package org.apache.pdfbox.io;

import java.io.IOException;

public abstract interface RandomAccessRead extends SequentialRead
{
  public abstract long getPosition()
    throws IOException;

  public abstract void seek(long paramLong)
    throws IOException;

  public abstract long length()
    throws IOException;
}

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.io.RandomAccessRead
 * JD-Core Version:    0.6.2
 */