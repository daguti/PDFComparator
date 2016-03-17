package antlr.debug;

import java.util.EventListener;

public abstract interface ListenerBase extends EventListener
{
  public abstract void doneParsing(TraceEvent paramTraceEvent);

  public abstract void refresh();
}

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     antlr.debug.ListenerBase
 * JD-Core Version:    0.6.2
 */