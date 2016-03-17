package com.itextpdf.awt.geom;

public abstract interface Shape
{
  public abstract boolean contains(double paramDouble1, double paramDouble2);

  public abstract boolean contains(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4);

  public abstract boolean contains(Point2D paramPoint2D);

  public abstract boolean contains(Rectangle2D paramRectangle2D);

  public abstract Rectangle getBounds();

  public abstract Rectangle2D getBounds2D();

  public abstract PathIterator getPathIterator(AffineTransform paramAffineTransform);

  public abstract PathIterator getPathIterator(AffineTransform paramAffineTransform, double paramDouble);

  public abstract boolean intersects(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4);

  public abstract boolean intersects(Rectangle2D paramRectangle2D);
}

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.awt.geom.Shape
 * JD-Core Version:    0.6.2
 */