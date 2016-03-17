package com.itextpdf.xmp.properties;

import com.itextpdf.xmp.options.PropertyOptions;

public abstract interface XMPProperty
{
  public abstract String getValue();

  public abstract PropertyOptions getOptions();

  public abstract String getLanguage();
}

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.xmp.properties.XMPProperty
 * JD-Core Version:    0.6.2
 */