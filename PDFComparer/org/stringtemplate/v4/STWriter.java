package org.stringtemplate.v4;

import java.io.IOException;

public abstract interface STWriter
{
  public static final int NO_WRAP = -1;

  public abstract void pushIndentation(String paramString);

  public abstract String popIndentation();

  public abstract void pushAnchorPoint();

  public abstract void popAnchorPoint();

  public abstract void setLineWidth(int paramInt);

  public abstract int write(String paramString)
    throws IOException;

  public abstract int write(String paramString1, String paramString2)
    throws IOException;

  public abstract int writeWrap(String paramString)
    throws IOException;

  public abstract int writeSeparator(String paramString)
    throws IOException;

  public abstract int index();
}

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.stringtemplate.v4.STWriter
 * JD-Core Version:    0.6.2
 */