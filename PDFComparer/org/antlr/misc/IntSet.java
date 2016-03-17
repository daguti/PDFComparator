package org.antlr.misc;

import java.util.List;
import org.antlr.tool.Grammar;

public abstract interface IntSet
{
  public abstract void add(int paramInt);

  public abstract void addAll(IntSet paramIntSet);

  public abstract IntSet and(IntSet paramIntSet);

  public abstract IntSet complement(IntSet paramIntSet);

  public abstract IntSet or(IntSet paramIntSet);

  public abstract IntSet subtract(IntSet paramIntSet);

  public abstract int size();

  public abstract boolean isNil();

  public abstract boolean equals(Object paramObject);

  public abstract int getSingleElement();

  public abstract boolean member(int paramInt);

  public abstract void remove(int paramInt);

  public abstract List toList();

  public abstract String toString();

  public abstract String toString(Grammar paramGrammar);
}

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.antlr.misc.IntSet
 * JD-Core Version:    0.6.2
 */