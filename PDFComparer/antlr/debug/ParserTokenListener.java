package antlr.debug;

public abstract interface ParserTokenListener extends ListenerBase
{
  public abstract void parserConsume(ParserTokenEvent paramParserTokenEvent);

  public abstract void parserLA(ParserTokenEvent paramParserTokenEvent);
}

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     antlr.debug.ParserTokenListener
 * JD-Core Version:    0.6.2
 */