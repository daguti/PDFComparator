package com.itextpdf.text;

public abstract interface DocListener extends ElementListener
{
  public abstract void open();

  public abstract void close();

  public abstract boolean newPage();

  public abstract boolean setPageSize(Rectangle paramRectangle);

  public abstract boolean setMargins(float paramFloat1, float paramFloat2, float paramFloat3, float paramFloat4);

  public abstract boolean setMarginMirroring(boolean paramBoolean);

  public abstract boolean setMarginMirroringTopBottom(boolean paramBoolean);

  public abstract void setPageCount(int paramInt);

  public abstract void resetPageCount();
}

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.DocListener
 * JD-Core Version:    0.6.2
 */