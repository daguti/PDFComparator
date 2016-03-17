package antlr.ASdebug;

import antlr.Token;

public abstract interface IASDebugStream
{
  public abstract String getEntireText();

  public abstract TokenOffsetInfo getOffsetInfo(Token paramToken);
}

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     antlr.ASdebug.IASDebugStream
 * JD-Core Version:    0.6.2
 */