package com.itextpdf.text.pdf.hyphenation;

import java.util.ArrayList;

public abstract interface PatternConsumer
{
  public abstract void addClass(String paramString);

  public abstract void addException(String paramString, ArrayList<Object> paramArrayList);

  public abstract void addPattern(String paramString1, String paramString2);
}

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.pdf.hyphenation.PatternConsumer
 * JD-Core Version:    0.6.2
 */