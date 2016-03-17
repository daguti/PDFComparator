package antlr.collections;

import java.util.Enumeration;
import java.util.NoSuchElementException;

public abstract interface List
{
  public abstract void add(Object paramObject);

  public abstract void append(Object paramObject);

  public abstract Object elementAt(int paramInt)
    throws NoSuchElementException;

  public abstract Enumeration elements();

  public abstract boolean includes(Object paramObject);

  public abstract int length();
}

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     antlr.collections.List
 * JD-Core Version:    0.6.2
 */