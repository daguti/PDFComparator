package antlr.debug;

public abstract interface ParserMatchListener extends ListenerBase
{
  public abstract void parserMatch(ParserMatchEvent paramParserMatchEvent);

  public abstract void parserMatchNot(ParserMatchEvent paramParserMatchEvent);

  public abstract void parserMismatch(ParserMatchEvent paramParserMatchEvent);

  public abstract void parserMismatchNot(ParserMatchEvent paramParserMatchEvent);
}

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     antlr.debug.ParserMatchListener
 * JD-Core Version:    0.6.2
 */