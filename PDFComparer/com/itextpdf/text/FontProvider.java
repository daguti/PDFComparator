package com.itextpdf.text;

public abstract interface FontProvider
{
  public abstract boolean isRegistered(String paramString);

  public abstract Font getFont(String paramString1, String paramString2, boolean paramBoolean, float paramFloat, int paramInt, BaseColor paramBaseColor);
}

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.FontProvider
 * JD-Core Version:    0.6.2
 */