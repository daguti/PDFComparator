package com.itextpdf.xmp;

import com.itextpdf.xmp.properties.XMPAliasInfo;
import java.util.Map;

public abstract interface XMPSchemaRegistry
{
  public abstract String registerNamespace(String paramString1, String paramString2)
    throws XMPException;

  public abstract String getNamespacePrefix(String paramString);

  public abstract String getNamespaceURI(String paramString);

  public abstract Map getNamespaces();

  public abstract Map getPrefixes();

  public abstract void deleteNamespace(String paramString);

  public abstract XMPAliasInfo resolveAlias(String paramString1, String paramString2);

  public abstract XMPAliasInfo[] findAliases(String paramString);

  public abstract XMPAliasInfo findAlias(String paramString);

  public abstract Map getAliases();
}

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.xmp.XMPSchemaRegistry
 * JD-Core Version:    0.6.2
 */