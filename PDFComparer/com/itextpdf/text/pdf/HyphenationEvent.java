package com.itextpdf.text.pdf;

public abstract interface HyphenationEvent
{
  public abstract String getHyphenSymbol();

  public abstract String getHyphenatedWordPre(String paramString, BaseFont paramBaseFont, float paramFloat1, float paramFloat2);

  public abstract String getHyphenatedWordPost();
}

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.pdf.HyphenationEvent
 * JD-Core Version:    0.6.2
 */