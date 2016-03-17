package com.itextpdf.text.log;

public abstract interface Counter
{
  public abstract Counter getCounter(Class<?> paramClass);

  public abstract void read(long paramLong);

  public abstract void written(long paramLong);
}

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.log.Counter
 * JD-Core Version:    0.6.2
 */