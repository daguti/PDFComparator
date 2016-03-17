package org.apache.pdfbox.util;

import java.io.IOException;
import java.io.OutputStream;

public class NullOutputStream extends OutputStream
{
  public void write(int b)
    throws IOException
  {
  }

  public void write(byte[] b, int off, int len)
    throws IOException
  {
  }

  public void write(byte[] b)
    throws IOException
  {
  }
}

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.util.NullOutputStream
 * JD-Core Version:    0.6.2
 */