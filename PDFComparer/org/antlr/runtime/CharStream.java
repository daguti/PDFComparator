package org.antlr.runtime;

public abstract interface CharStream extends IntStream
{
  public static final int EOF = -1;

  public abstract String substring(int paramInt1, int paramInt2);

  public abstract int LT(int paramInt);

  public abstract int getLine();

  public abstract void setLine(int paramInt);

  public abstract void setCharPositionInLine(int paramInt);

  public abstract int getCharPositionInLine();
}

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.antlr.runtime.CharStream
 * JD-Core Version:    0.6.2
 */