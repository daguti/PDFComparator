package antlr.debug;

public abstract interface TraceListener extends ListenerBase
{
  public abstract void enterRule(TraceEvent paramTraceEvent);

  public abstract void exitRule(TraceEvent paramTraceEvent);
}

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     antlr.debug.TraceListener
 * JD-Core Version:    0.6.2
 */