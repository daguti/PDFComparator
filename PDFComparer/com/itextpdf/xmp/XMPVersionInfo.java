package com.itextpdf.xmp;

public abstract interface XMPVersionInfo
{
  public abstract int getMajor();

  public abstract int getMinor();

  public abstract int getMicro();

  public abstract int getBuild();

  public abstract boolean isDebug();

  public abstract String getMessage();
}

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.xmp.XMPVersionInfo
 * JD-Core Version:    0.6.2
 */