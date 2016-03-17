package org.antlr.runtime.tree;

import org.antlr.runtime.IntStream;
import org.antlr.runtime.TokenStream;

public abstract interface TreeNodeStream extends IntStream
{
  public abstract Object get(int paramInt);

  public abstract Object LT(int paramInt);

  public abstract Object getTreeSource();

  public abstract TokenStream getTokenStream();

  public abstract TreeAdaptor getTreeAdaptor();

  public abstract void setUniqueNavigationNodes(boolean paramBoolean);

  public abstract void reset();

  public abstract String toString(Object paramObject1, Object paramObject2);

  public abstract void replaceChildren(Object paramObject1, int paramInt1, int paramInt2, Object paramObject2);
}

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.antlr.runtime.tree.TreeNodeStream
 * JD-Core Version:    0.6.2
 */