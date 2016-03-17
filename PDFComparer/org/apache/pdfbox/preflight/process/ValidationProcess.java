package org.apache.pdfbox.preflight.process;

import org.apache.pdfbox.preflight.PreflightContext;
import org.apache.pdfbox.preflight.exception.ValidationException;

public abstract interface ValidationProcess
{
  public abstract void validate(PreflightContext paramPreflightContext)
    throws ValidationException;
}

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.preflight.process.ValidationProcess
 * JD-Core Version:    0.6.2
 */