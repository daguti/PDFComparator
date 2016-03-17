package org.apache.fontbox.ttf;

public abstract interface GlyphDescription
{
  public abstract int getEndPtOfContours(int paramInt);

  public abstract byte getFlags(int paramInt);

  public abstract short getXCoordinate(int paramInt);

  public abstract short getYCoordinate(int paramInt);

  public abstract boolean isComposite();

  public abstract int getPointCount();

  public abstract int getContourCount();

  public abstract void resolve();
}

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.fontbox.ttf.GlyphDescription
 * JD-Core Version:    0.6.2
 */