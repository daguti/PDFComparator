package com.itextpdf.text.html.simpleparser;

import com.itextpdf.text.DocListener;
import com.itextpdf.text.Image;
import java.util.Map;

@Deprecated
public abstract interface ImageProvider
{
  public abstract Image getImage(String paramString, Map<String, String> paramMap, ChainedProperties paramChainedProperties, DocListener paramDocListener);
}

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.html.simpleparser.ImageProvider
 * JD-Core Version:    0.6.2
 */