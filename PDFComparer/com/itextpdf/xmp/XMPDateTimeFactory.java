/*     */ package com.itextpdf.xmp;
/*     */ 
/*     */ import com.itextpdf.xmp.impl.XMPDateTimeImpl;
/*     */ import java.util.Calendar;
/*     */ import java.util.Date;
/*     */ import java.util.GregorianCalendar;
/*     */ import java.util.TimeZone;
/*     */ 
/*     */ public final class XMPDateTimeFactory
/*     */ {
/*  50 */   private static final TimeZone UTC = TimeZone.getTimeZone("UTC");
/*     */ 
/*     */   public static XMPDateTime createFromCalendar(Calendar calendar)
/*     */   {
/*  69 */     return new XMPDateTimeImpl(calendar);
/*     */   }
/*     */ 
/*     */   public static XMPDateTime create()
/*     */   {
/*  79 */     return new XMPDateTimeImpl();
/*     */   }
/*     */ 
/*     */   public static XMPDateTime create(int year, int month, int day)
/*     */   {
/*  93 */     XMPDateTime dt = new XMPDateTimeImpl();
/*  94 */     dt.setYear(year);
/*  95 */     dt.setMonth(month);
/*  96 */     dt.setDay(day);
/*  97 */     return dt;
/*     */   }
/*     */ 
/*     */   public static XMPDateTime create(int year, int month, int day, int hour, int minute, int second, int nanoSecond)
/*     */   {
/* 116 */     XMPDateTime dt = new XMPDateTimeImpl();
/* 117 */     dt.setYear(year);
/* 118 */     dt.setMonth(month);
/* 119 */     dt.setDay(day);
/* 120 */     dt.setHour(hour);
/* 121 */     dt.setMinute(minute);
/* 122 */     dt.setSecond(second);
/* 123 */     dt.setNanoSecond(nanoSecond);
/* 124 */     return dt;
/*     */   }
/*     */ 
/*     */   public static XMPDateTime createFromISO8601(String strValue)
/*     */     throws XMPException
/*     */   {
/* 137 */     return new XMPDateTimeImpl(strValue);
/*     */   }
/*     */ 
/*     */   public static XMPDateTime getCurrentDateTime()
/*     */   {
/* 149 */     return new XMPDateTimeImpl(new GregorianCalendar());
/*     */   }
/*     */ 
/*     */   public static XMPDateTime setLocalTimeZone(XMPDateTime dateTime)
/*     */   {
/* 162 */     Calendar cal = dateTime.getCalendar();
/* 163 */     cal.setTimeZone(TimeZone.getDefault());
/* 164 */     return new XMPDateTimeImpl(cal);
/*     */   }
/*     */ 
/*     */   public static XMPDateTime convertToUTCTime(XMPDateTime dateTime)
/*     */   {
/* 179 */     long timeInMillis = dateTime.getCalendar().getTimeInMillis();
/* 180 */     GregorianCalendar cal = new GregorianCalendar(UTC);
/* 181 */     cal.setGregorianChange(new Date(-9223372036854775808L));
/* 182 */     cal.setTimeInMillis(timeInMillis);
/* 183 */     return new XMPDateTimeImpl(cal);
/*     */   }
/*     */ 
/*     */   public static XMPDateTime convertToLocalTime(XMPDateTime dateTime)
/*     */   {
/* 196 */     long timeInMillis = dateTime.getCalendar().getTimeInMillis();
/*     */ 
/* 198 */     GregorianCalendar cal = new GregorianCalendar();
/* 199 */     cal.setTimeInMillis(timeInMillis);
/* 200 */     return new XMPDateTimeImpl(cal);
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.xmp.XMPDateTimeFactory
 * JD-Core Version:    0.6.2
 */