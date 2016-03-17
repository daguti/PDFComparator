package com.itextpdf.xmp.properties;

import com.itextpdf.xmp.options.AliasOptions;

public abstract interface XMPAliasInfo
{
  public abstract String getNamespace();

  public abstract String getPrefix();

  public abstract String getPropName();

  public abstract AliasOptions getAliasForm();
}

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.xmp.properties.XMPAliasInfo
 * JD-Core Version:    0.6.2
 */