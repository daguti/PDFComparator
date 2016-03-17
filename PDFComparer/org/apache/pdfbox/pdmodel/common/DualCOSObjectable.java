package org.apache.pdfbox.pdmodel.common;

import org.apache.pdfbox.cos.COSBase;

public abstract interface DualCOSObjectable
{
  public abstract COSBase getFirstCOSObject();

  public abstract COSBase getSecondCOSObject();
}

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.pdmodel.common.DualCOSObjectable
 * JD-Core Version:    0.6.2
 */