package com.itextpdf.text.log;

public abstract interface Logger
{
  public abstract Logger getLogger(Class<?> paramClass);

  public abstract Logger getLogger(String paramString);

  public abstract boolean isLogging(Level paramLevel);

  public abstract void warn(String paramString);

  public abstract void trace(String paramString);

  public abstract void debug(String paramString);

  public abstract void info(String paramString);

  public abstract void error(String paramString);

  public abstract void error(String paramString, Exception paramException);
}

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.log.Logger
 * JD-Core Version:    0.6.2
 */