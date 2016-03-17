package com.itextpdf.text;

import com.itextpdf.text.pdf.PdfChunk;

public abstract interface SplitCharacter
{
  public abstract boolean isSplitCharacter(int paramInt1, int paramInt2, int paramInt3, char[] paramArrayOfChar, PdfChunk[] paramArrayOfPdfChunk);
}

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.SplitCharacter
 * JD-Core Version:    0.6.2
 */