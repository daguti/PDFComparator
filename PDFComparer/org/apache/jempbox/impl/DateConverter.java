/*     */ package org.apache.jempbox.impl;
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
/*  41 */   private static final SimpleDateFormat[] POTENTIAL_FORMATS = { new SimpleDateFormat("EEEE, dd MMM yyyy hh:mm:ss a"), new SimpleDateFormat("EEEE, MMM dd, yyyy hh:mm:ss a"), new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'"), new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssz"), new SimpleDateFormat("MM/dd/yyyy hh:mm:ss"), new SimpleDateFormat("MM/dd/yyyy"), new SimpleDateFormat("EEEE, MMM dd, yyyy"), new SimpleDateFormat("EEEE MMM dd, yyyy HH:mm:ss"), new SimpleDateFormat("EEEE MMM dd HH:mm:ss z yyyy"), new SimpleDateFormat("EEEE, MMM dd, yyyy 'at' hh:mma") };
/*     */ 
/*     */   public static Calendar toCalendar(String date)
/*     */     throws IOException
/*     */   {
/*  70 */     Calendar retval = null;
/*  71 */     if ((date != null) && (date.trim().length() > 0))
/*     */     {
/*  74 */       int year = 0;
/*  75 */       int month = 1;
/*  76 */       int day = 1;
/*  77 */       int hour = 0;
/*  78 */       int minute = 0;
/*  79 */       int second = 0;
/*     */       try
/*     */       {
/*  83 */         SimpleTimeZone zone = null;
/*  84 */         if (date.startsWith("D:"))
/*     */         {
/*  86 */           date = date.substring(2, date.length());
/*     */         }
/*  88 */         date = date.replaceAll("[-:T]", "");
/*     */ 
/*  90 */         if (date.length() < 4)
/*     */         {
/*  92 */           throw new IOException("Error: Invalid date format '" + date + "'");
/*     */         }
/*  94 */         year = Integer.parseInt(date.substring(0, 4));
/*  95 */         if (date.length() >= 6)
/*     */         {
/*  97 */           month = Integer.parseInt(date.substring(4, 6));
/*     */         }
/*  99 */         if (date.length() >= 8)
/*     */         {
/* 101 */           day = Integer.parseInt(date.substring(6, 8));
/*     */         }
/* 103 */         if (date.length() >= 10)
/*     */         {
/* 105 */           hour = Integer.parseInt(date.substring(8, 10));
/*     */         }
/* 107 */         if (date.length() >= 12)
/*     */         {
/* 109 */           minute = Integer.parseInt(date.substring(10, 12));
/*     */         }
/* 111 */         if (date.length() >= 14)
/*     */         {
/* 113 */           second = Integer.parseInt(date.substring(12, 14));
/*     */         }
/* 115 */         if (date.length() >= 15)
/*     */         {
/* 117 */           char sign = date.charAt(14);
/* 118 */           if (sign == 'Z')
/*     */           {
/* 120 */             zone = new SimpleTimeZone(0, "Unknown");
/*     */           }
/*     */           else
/*     */           {
/* 124 */             int hours = 0;
/* 125 */             int minutes = 0;
/* 126 */             if (date.length() >= 17)
/*     */             {
/* 128 */               if (sign == '+')
/*     */               {
/* 131 */                 hours = Integer.parseInt(date.substring(15, 17));
/*     */               }
/*     */               else
/*     */               {
/* 135 */                 hours = -Integer.parseInt(date.substring(14, 16));
/*     */               }
/*     */             }
/* 138 */             if (sign == '+')
/*     */             {
/* 140 */               if (date.length() >= 19)
/*     */               {
/* 142 */                 minutes = Integer.parseInt(date.substring(17, 19));
/*     */               }
/*     */ 
/*     */             }
/* 147 */             else if (date.length() >= 18)
/*     */             {
/* 149 */               minutes = Integer.parseInt(date.substring(16, 18));
/*     */             }
/*     */ 
/* 152 */             zone = new SimpleTimeZone(hours * 60 * 60 * 1000 + minutes * 60 * 1000, "Unknown");
/*     */           }
/*     */         }
/* 155 */         if (zone == null)
/*     */         {
/* 157 */           retval = new GregorianCalendar();
/*     */         }
/*     */         else
/*     */         {
/* 161 */           retval = new GregorianCalendar(zone);
/*     */         }
/* 163 */         retval.clear();
/* 164 */         retval.set(year, month - 1, day, hour, minute, second);
/*     */       }
/*     */       catch (NumberFormatException e)
/*     */       {
/* 171 */         if ((date.substring(date.length() - 3, date.length() - 2).equals(":")) && ((date.substring(date.length() - 6, date.length() - 5).equals("+")) || (date.substring(date.length() - 6, date.length() - 5).equals("-"))))
/*     */         {
/* 176 */           date = date.substring(0, date.length() - 3) + date.substring(date.length() - 2);
/*     */         }
/*     */ 
/* 179 */         for (int i = 0; (retval == null) && (i < POTENTIAL_FORMATS.length); i++)
/*     */         {
/*     */           try
/*     */           {
/* 183 */             Date utilDate = POTENTIAL_FORMATS[i].parse(date);
/* 184 */             retval = new GregorianCalendar();
/* 185 */             retval.setTime(utilDate);
/*     */           }
/*     */           catch (ParseException pe)
/*     */           {
/*     */           }
/*     */         }
/*     */ 
/* 192 */         if (retval == null)
/*     */         {
/* 195 */           throw new IOException("Error converting date:" + date);
/*     */         }
/*     */       }
/*     */     }
/* 199 */     return retval;
/*     */   }
/*     */ 
/*     */   private static final void zeroAppend(StringBuffer out, int number)
/*     */   {
/* 204 */     if (number < 10)
/*     */     {
/* 206 */       out.append("0");
/*     */     }
/* 208 */     out.append(number);
/*     */   }
/*     */ 
/*     */   public static String toISO8601(Calendar cal)
/*     */   {
/* 219 */     StringBuffer retval = new StringBuffer();
/*     */ 
/* 221 */     retval.append(cal.get(1));
/* 222 */     retval.append("-");
/* 223 */     zeroAppend(retval, cal.get(2) + 1);
/* 224 */     retval.append("-");
/* 225 */     zeroAppend(retval, cal.get(5));
/* 226 */     retval.append("T");
/* 227 */     zeroAppend(retval, cal.get(11));
/* 228 */     retval.append(":");
/* 229 */     zeroAppend(retval, cal.get(12));
/* 230 */     retval.append(":");
/* 231 */     zeroAppend(retval, cal.get(13));
/*     */ 
/* 233 */     int timeZone = cal.get(15) + cal.get(16);
/* 234 */     if (timeZone < 0)
/*     */     {
/* 236 */       retval.append("-");
/*     */     }
/*     */     else
/*     */     {
/* 240 */       retval.append("+");
/*     */     }
/* 242 */     timeZone = Math.abs(timeZone);
/*     */ 
/* 244 */     int hours = timeZone / 1000 / 60 / 60;
/* 245 */     int minutes = (timeZone - hours * 1000 * 60 * 60) / 1000 / 1000;
/* 246 */     if (hours < 10)
/*     */     {
/* 248 */       retval.append("0");
/*     */     }
/* 250 */     retval.append(Integer.toString(hours));
/* 251 */     retval.append(":");
/* 252 */     if (minutes < 10)
/*     */     {
/* 254 */       retval.append("0");
/*     */     }
/* 256 */     retval.append(Integer.toString(minutes));
/*     */ 
/* 258 */     return retval.toString();
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.jempbox.impl.DateConverter
 * JD-Core Version:    0.6.2
 */