package org.apache.pdfbox.preflight.xobject;

import org.apache.pdfbox.preflight.exception.ValidationException;

public abstract interface XObjectValidator
{
  public abstract void validate()
    throws ValidationException;
}

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.preflight.xobject.XObjectValidator
 * JD-Core Version:    0.6.2
 */