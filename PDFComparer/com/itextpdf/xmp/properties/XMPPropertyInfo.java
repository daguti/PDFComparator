package com.itextpdf.xmp.properties;

import com.itextpdf.xmp.options.PropertyOptions;

public abstract interface XMPPropertyInfo extends XMPProperty
{
  public abstract String getNamespace();

  public abstract String getPath();

  public abstract String getValue();

  public abstract PropertyOptions getOptions();
}

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.xmp.properties.XMPPropertyInfo
 * JD-Core Version:    0.6.2
 */