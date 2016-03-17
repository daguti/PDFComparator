package antlr.debug;

public abstract interface MessageListener extends ListenerBase
{
  public abstract void reportError(MessageEvent paramMessageEvent);

  public abstract void reportWarning(MessageEvent paramMessageEvent);
}

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     antlr.debug.MessageListener
 * JD-Core Version:    0.6.2
 */