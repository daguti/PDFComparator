package org.antlr.runtime;

public abstract interface TokenStream extends IntStream
{
  public abstract Token LT(int paramInt);

  public abstract int range();

  public abstract Token get(int paramInt);

  public abstract TokenSource getTokenSource();

  public abstract String toString(int paramInt1, int paramInt2);

  public abstract String toString(Token paramToken1, Token paramToken2);
}

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.antlr.runtime.TokenStream
 * JD-Core Version:    0.6.2
 */