/*     */ package org.apache.xmpbox;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.text.ParseException;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.Calendar;
/*     */ import java.util.Date;
/*     */ import java.util.GregorianCalendar;
/*     */ import java.util.SimpleTimeZone;
/*     */ 
/*     */ public class DateConverter
/*     */ {
/*  48 */   private static final SimpleDateFormat[] POTENTIAL_FORMATS = { new SimpleDateFormat("EEEE, dd MMM yyyy hh:mm:ss a"), new SimpleDateFormat("EEEE, MMM dd, yyyy hh:mm:ss a"), new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'"), new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssz") };
/*     */ 
/*     */   public static Calendar toCalendar(String date)
/*     */     throws IOException
/*     */   {
/*  73 */     Calendar retval = null;
/*  74 */     if ((date != null) && (date.trim().length() > 0))
/*     */     {
/*  77 */       int year = 0;
/*  78 */       int month = 1;
/*  79 */       int day = 1;
/*  80 */       int hour = 0;
/*  81 */       int minute = 0;
/*  82 */       int second = 0;
/*     */       try
/*     */       {
/*  86 */         SimpleTimeZone zone = null;
/*  87 */         if (date.startsWith("D:"))
/*     */         {
/*  89 */           date = date.substring(2, date.length());
/*     */         }
/*     */ 
/*  92 */         date = date.replaceAll("[-:T]", "");
/*     */ 
/*  94 */         if (date.length() < 4)
/*     */         {
/*  96 */           throw new IOException("Error: Invalid date format '" + date + "'");
/*     */         }
/*  98 */         year = Integer.parseInt(date.substring(0, 4));
/*  99 */         if (date.length() >= 6)
/*     */         {
/* 101 */           month = Integer.parseInt(date.substring(4, 6));
/*     */         }
/* 103 */         if (date.length() >= 8)
/*     */         {
/* 105 */           day = Integer.parseInt(date.substring(6, 8));
/*     */         }
/* 107 */         if (date.length() >= 10)
/*     */         {
/* 109 */           hour = Integer.parseInt(date.substring(8, 10));
/*     */         }
/* 111 */         if (date.length() >= 12)
/*     */         {
/* 113 */           minute = Integer.parseInt(date.substring(10, 12));
/*     */         }
/*     */ 
/* 116 */         int timeZonePos = 12;
/* 117 */         if ((date.length() - 12 > 5) || ((date.length() - 12 == 3) && (date.endsWith("Z"))))
/*     */         {
/* 119 */           if (date.length() >= 14)
/*     */           {
/* 121 */             second = Integer.parseInt(date.substring(12, 14));
/*     */           }
/* 123 */           timeZonePos = 14;
/*     */         }
/*     */         else
/*     */         {
/* 127 */           second = 0;
/*     */         }
/*     */ 
/* 130 */         if (date.length() >= timeZonePos + 1)
/*     */         {
/* 132 */           char sign = date.charAt(timeZonePos);
/* 133 */           if (sign == 'Z')
/*     */           {
/* 135 */             zone = new SimpleTimeZone(0, "Unknown");
/*     */           }
/*     */           else
/*     */           {
/* 139 */             int hours = 0;
/* 140 */             int minutes = 0;
/* 141 */             if (date.length() >= timeZonePos + 3)
/*     */             {
/* 143 */               if (sign == '+')
/*     */               {
/* 146 */                 hours = Integer.parseInt(date.substring(timeZonePos + 1, timeZonePos + 3));
/*     */               }
/*     */               else
/*     */               {
/* 150 */                 hours = -Integer.parseInt(date.substring(timeZonePos, timeZonePos + 2));
/*     */               }
/*     */             }
/* 153 */             if (sign == '+')
/*     */             {
/* 155 */               if (date.length() >= timeZonePos + 5)
/*     */               {
/* 157 */                 minutes = Integer.parseInt(date.substring(timeZonePos + 3, timeZonePos + 5));
/*     */               }
/*     */ 
/*     */             }
/* 162 */             else if (date.length() >= timeZonePos + 4)
/*     */             {
/* 164 */               minutes = Integer.parseInt(date.substring(timeZonePos + 2, timeZonePos + 4));
/*     */             }
/*     */ 
/* 167 */             zone = new SimpleTimeZone(hours * 60 * 60 * 1000 + minutes * 60 * 1000, "Unknown");
/*     */           }
/*     */         }
/*     */ 
/* 171 */         if (zone == null)
/*     */         {
/* 173 */           retval = new GregorianCalendar();
/*     */         }
/*     */         else
/*     */         {
/* 177 */           retval = new GregorianCalendar(zone);
/*     */         }
/* 179 */         retval.clear();
/* 180 */         retval.set(year, month - 1, day, hour, minute, second);
/*     */       }
/*     */       catch (NumberFormatException e)
/*     */       {
/* 187 */         if ((date.substring(date.length() - 3, date.length() - 2).equals(":")) && ((date.substring(date.length() - 6, date.length() - 5).equals("+")) || (date.substring(date.length() - 6, date.length() - 5).equals("-"))))
/*     */         {
/* 192 */           date = date.substring(0, date.length() - 3) + date.substring(date.length() - 2);
/*     */         }
/* 194 */         for (int i = 0; (retval == null) && (i < POTENTIAL_FORMATS.length); i++)
/*     */         {
/*     */           try
/*     */           {
/* 198 */             Date utilDate = POTENTIAL_FORMATS[i].parse(date);
/* 199 */             retval = new GregorianCalendar();
/* 200 */             retval.setTime(utilDate);
/*     */           }
/*     */           catch (ParseException pe)
/*     */           {
/*     */           }
/*     */         }
/*     */ 
/* 207 */         if (retval == null)
/*     */         {
/* 210 */           IOException ioe = new IOException("Error converting date:" + date);
/* 211 */           ioe.initCause(e);
/* 212 */           throw ioe;
/*     */         }
/*     */       }
/*     */     }
/* 216 */     return retval;
/*     */   }
/*     */ 
/*     */   private static void zeroAppend(StringBuffer out, int number)
/*     */   {
/* 229 */     if (number < 10)
/*     */     {
/* 231 */       out.append("0");
/*     */     }
/* 233 */     out.append(number);
/*     */   }
/*     */ 
/*     */   public static String toISO8601(Calendar cal)
/*     */   {
/* 245 */     StringBuffer retval = new StringBuffer();
/*     */ 
/* 247 */     retval.append(cal.get(1));
/* 248 */     retval.append("-");
/* 249 */     zeroAppend(retval, cal.get(2) + 1);
/* 250 */     retval.append("-");
/* 251 */     zeroAppend(retval, cal.get(5));
/* 252 */     retval.append("T");
/* 253 */     zeroAppend(retval, cal.get(11));
/* 254 */     retval.append(":");
/* 255 */     zeroAppend(retval, cal.get(12));
/* 256 */     retval.append(":");
/* 257 */     zeroAppend(retval, cal.get(13));
/*     */ 
/* 259 */     int timeZone = cal.get(15) + cal.get(16);
/* 260 */     if (timeZone < 0)
/*     */     {
/* 262 */       retval.append("-");
/*     */     }
/*     */     else
/*     */     {
/* 266 */       retval.append("+");
/*     */     }
/* 268 */     timeZone = Math.abs(timeZone);
/*     */ 
/* 271 */     int hours = timeZone / 1000 / 60 / 60;
/* 272 */     int minutes = (timeZone - hours * 1000 * 60 * 60) / 1000 / 1000;
/* 273 */     if (hours < 10)
/*     */     {
/* 275 */       retval.append("0");
/*     */     }
/* 277 */     retval.append(Integer.toString(hours));
/* 278 */     retval.append(":");
/* 279 */     if (minutes < 10)
/*     */     {
/* 281 */       retval.append("0");
/*     */     }
/* 283 */     retval.append(Integer.toString(minutes));
/* 284 */     return retval.toString();
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.xmpbox.DateConverter
 * JD-Core Version:    0.6.2
 */