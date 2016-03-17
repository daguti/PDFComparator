package com.itextpdf.text;

import java.util.EventListener;

public abstract interface ElementListener extends EventListener
{
  public abstract boolean add(Element paramElement)
    throws DocumentException;
}

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.ElementListener
 * JD-Core Version:    0.6.2
 */