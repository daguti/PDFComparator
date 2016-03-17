package antlr.collections;

import java.util.NoSuchElementException;

public abstract interface Stack
{
  public abstract int height();

  public abstract Object pop()
    throws NoSuchElementException;

  public abstract void push(Object paramObject);

  public abstract Object top()
    throws NoSuchElementException;
}

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     antlr.collections.Stack
 * JD-Core Version:    0.6.2
 */