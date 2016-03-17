package com.itextpdf.text.pdf.parser;

public abstract interface RenderListener
{
  public abstract void beginTextBlock();

  public abstract void renderText(TextRenderInfo paramTextRenderInfo);

  public abstract void endTextBlock();

  public abstract void renderImage(ImageRenderInfo paramImageRenderInfo);
}

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.pdf.parser.RenderListener
 * JD-Core Version:    0.6.2
 */