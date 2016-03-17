package org.stringtemplate.v4;

import org.stringtemplate.v4.misc.STMessage;

public abstract interface STErrorListener
{
  public abstract void compileTimeError(STMessage paramSTMessage);

  public abstract void runTimeError(STMessage paramSTMessage);

  public abstract void IOError(STMessage paramSTMessage);

  public abstract void internalError(STMessage paramSTMessage);
}

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.stringtemplate.v4.STErrorListener
 * JD-Core Version:    0.6.2
 */