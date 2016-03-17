package antlr.debug;

public abstract interface InputBufferListener extends ListenerBase
{
  public abstract void inputBufferConsume(InputBufferEvent paramInputBufferEvent);

  public abstract void inputBufferLA(InputBufferEvent paramInputBufferEvent);

  public abstract void inputBufferMark(InputBufferEvent paramInputBufferEvent);

  public abstract void inputBufferRewind(InputBufferEvent paramInputBufferEvent);
}

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     antlr.debug.InputBufferListener
 * JD-Core Version:    0.6.2
 */