package org.antlr.tool;

public abstract interface ANTLRErrorListener
{
  public abstract void info(String paramString);

  public abstract void error(Message paramMessage);

  public abstract void warning(Message paramMessage);

  public abstract void error(ToolMessage paramToolMessage);
}

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.antlr.tool.ANTLRErrorListener
 * JD-Core Version:    0.6.2
 */