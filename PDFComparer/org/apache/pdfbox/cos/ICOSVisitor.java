package org.apache.pdfbox.cos;

import org.apache.pdfbox.exceptions.COSVisitorException;

public abstract interface ICOSVisitor
{
  public abstract Object visitFromArray(COSArray paramCOSArray)
    throws COSVisitorException;

  public abstract Object visitFromBoolean(COSBoolean paramCOSBoolean)
    throws COSVisitorException;

  public abstract Object visitFromDictionary(COSDictionary paramCOSDictionary)
    throws COSVisitorException;

  public abstract Object visitFromDocument(COSDocument paramCOSDocument)
    throws COSVisitorException;

  public abstract Object visitFromFloat(COSFloat paramCOSFloat)
    throws COSVisitorException;

  public abstract Object visitFromInt(COSInteger paramCOSInteger)
    throws COSVisitorException;

  public abstract Object visitFromName(COSName paramCOSName)
    throws COSVisitorException;

  public abstract Object visitFromNull(COSNull paramCOSNull)
    throws COSVisitorException;

  public abstract Object visitFromStream(COSStream paramCOSStream)
    throws COSVisitorException;

  public abstract Object visitFromString(COSString paramCOSString)
    throws COSVisitorException;
}

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.cos.ICOSVisitor
 * JD-Core Version:    0.6.2
 */