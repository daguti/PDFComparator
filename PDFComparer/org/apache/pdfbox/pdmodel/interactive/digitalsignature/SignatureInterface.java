package org.apache.pdfbox.pdmodel.interactive.digitalsignature;

import java.io.IOException;
import java.io.InputStream;
import org.apache.pdfbox.exceptions.SignatureException;

public abstract interface SignatureInterface
{
  public abstract byte[] sign(InputStream paramInputStream)
    throws SignatureException, IOException;
}

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.pdmodel.interactive.digitalsignature.SignatureInterface
 * JD-Core Version:    0.6.2
 */