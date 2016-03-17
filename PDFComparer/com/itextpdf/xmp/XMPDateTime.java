package com.itextpdf.xmp;

import java.util.Calendar;
import java.util.TimeZone;

public abstract interface XMPDateTime extends Comparable
{
  public abstract int getYear();

  public abstract void setYear(int paramInt);

  public abstract int getMonth();

  public abstract void setMonth(int paramInt);

  public abstract int getDay();

  public abstract void setDay(int paramInt);

  public abstract int getHour();

  public abstract void setHour(int paramInt);

  public abstract int getMinute();

  public abstract void setMinute(int paramInt);

  public abstract int getSecond();

  public abstract void setSecond(int paramInt);

  public abstract int getNanoSecond();

  public abstract void setNanoSecond(int paramInt);

  public abstract TimeZone getTimeZone();

  public abstract void setTimeZone(TimeZone paramTimeZone);

  public abstract boolean hasDate();

  public abstract boolean hasTime();

  public abstract boolean hasTimeZone();

  public abstract Calendar getCalendar();

  public abstract String getISO8601String();
}

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.xmp.XMPDateTime
 * JD-Core Version:    0.6.2
 */