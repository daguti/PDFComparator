package antlr;

public abstract interface CharFormatter
{
  public abstract String escapeChar(int paramInt, boolean paramBoolean);

  public abstract String escapeString(String paramString);

  public abstract String literalChar(int paramInt);

  public abstract String literalString(String paramString);
}

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     antlr.CharFormatter
 * JD-Core Version:    0.6.2
 */