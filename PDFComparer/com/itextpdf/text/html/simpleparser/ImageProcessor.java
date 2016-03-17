package com.itextpdf.text.html.simpleparser;

import com.itextpdf.text.DocListener;
import com.itextpdf.text.Image;
import java.util.Map;

@Deprecated
public abstract interface ImageProcessor
{
  public abstract boolean process(Image paramImage, Map<String, String> paramMap, ChainedProperties paramChainedProperties, DocListener paramDocListener);
}

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.html.simpleparser.ImageProcessor
 * JD-Core Version:    0.6.2
 */