package org.stringtemplate.v4;

import org.stringtemplate.v4.misc.STNoSuchPropertyException;

public abstract interface ModelAdaptor
{
  public abstract Object getProperty(Interpreter paramInterpreter, ST paramST, Object paramObject1, Object paramObject2, String paramString)
    throws STNoSuchPropertyException;
}

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.stringtemplate.v4.ModelAdaptor
 * JD-Core Version:    0.6.2
 */