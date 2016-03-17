package antlr.debug;

public abstract interface ParserController extends ParserListener
{
  public abstract void checkBreak();

  public abstract void setParserEventSupport(ParserEventSupport paramParserEventSupport);
}

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     antlr.debug.ParserController
 * JD-Core Version:    0.6.2
 */