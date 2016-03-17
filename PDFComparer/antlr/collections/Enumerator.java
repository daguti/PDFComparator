package antlr.collections;

public abstract interface Enumerator
{
  public abstract Object cursor();

  public abstract Object next();

  public abstract boolean valid();
}

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     antlr.collections.Enumerator
 * JD-Core Version:    0.6.2
 */