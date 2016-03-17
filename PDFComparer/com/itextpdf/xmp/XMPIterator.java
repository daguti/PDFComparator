package com.itextpdf.xmp;

import java.util.Iterator;

public abstract interface XMPIterator extends Iterator
{
  public abstract void skipSubtree();

  public abstract void skipSiblings();
}

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.xmp.XMPIterator
 * JD-Core Version:    0.6.2
 */