/*     */ package org.apache.pdfbox.util;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.text.ParsePosition;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.Calendar;
/*     */ import java.util.Date;
/*     */ import java.util.GregorianCalendar;
/*     */ import java.util.Locale;
/*     */ import java.util.SimpleTimeZone;
/*     */ import java.util.TimeZone;
/*     */ import org.apache.pdfbox.cos.COSString;
/*     */ 
/*     */ public class DateConverter
/*     */ {
/*     */   private static final int MINUTES_PER_HOUR = 60;
/*     */   private static final int SECONDS_PER_MINUTE = 60;
/*     */   private static final int MILLIS_PER_MINUTE = 60000;
/*     */   private static final int MILLIS_PER_HOUR = 3600000;
/*     */   private static final int HALF_DAY = 43200000;
/*     */   private static final int DAY = 86400000;
/*     */   public static final int INVALID_YEAR = 999;
/* 111 */   private static final String[] ALPHA_START_FORMATS = { "EEEE, dd MMM yy hh:mm:ss a", "EEEE, MMM dd, yy hh:mm:ss a", "EEEE, MMM dd, yy 'at' hh:mma", "EEEE, MMM dd, yy", "EEEE MMM dd, yy HH:mm:ss", "EEEE MMM dd HH:mm:ss z yy", "EEEE MMM dd HH:mm:ss yy" };
/*     */ 
/* 122 */   private static final String[] DIGIT_START_FORMATS = { "dd MMM yy HH:mm:ss", "dd MMM yy HH:mm", "yyyy MMM d", "yyyymmddhh:mm:ss", "H:m M/d/yy", "M/d/yy HH:mm:ss", "M/d/yy HH:mm", "M/d/yy" };
/*     */ 
/*     */   public static String[] getFormats()
/*     */   {
/* 174 */     String[] val = new String[ALPHA_START_FORMATS.length + DIGIT_START_FORMATS.length];
/* 175 */     System.arraycopy(ALPHA_START_FORMATS, 0, val, 0, ALPHA_START_FORMATS.length);
/* 176 */     System.arraycopy(DIGIT_START_FORMATS, 0, val, ALPHA_START_FORMATS.length, DIGIT_START_FORMATS.length);
/* 177 */     return val;
/*     */   }
/*     */ 
/*     */   public static String toString(Calendar cal)
/*     */   {
/* 192 */     if (cal == null)
/*     */     {
/* 194 */       return null;
/*     */     }
/* 196 */     String offset = formatTZoffset(cal.get(15) + cal.get(16), "'");
/*     */ 
/* 198 */     return String.format("D:%1$4tY%1$2tm%1$2td%1$2tH%1$2tM%1$2tS%2$s'", new Object[] { cal, offset });
/*     */   }
/*     */ 
/*     */   public static String toISO8601(Calendar cal)
/*     */   {
/* 217 */     String offset = formatTZoffset(cal.get(15) + cal.get(16), ":");
/*     */ 
/* 219 */     return String.format("%1$4tY-%1$2tm-%1$2tdT%1$2tH:%1$2tM:%1$2tS%2$s", new Object[] { cal, offset });
/*     */   }
/*     */ 
/*     */   public static int restrainTZoffset(long proposedOffset)
/*     */   {
/* 237 */     proposedOffset = ((proposedOffset + 43200000L) % 86400000L + 86400000L) % 86400000L;
/*     */ 
/* 239 */     proposedOffset = (proposedOffset - 43200000L) % 43200000L;
/*     */ 
/* 241 */     return (int)proposedOffset;
/*     */   }
/*     */ 
/*     */   public static String formatTZoffset(long millis, String sep)
/*     */   {
/* 307 */     SimpleDateFormat sdf = new SimpleDateFormat("Z");
/* 308 */     sdf.setTimeZone(new SimpleTimeZone(restrainTZoffset(millis), "unknown"));
/* 309 */     String tz = sdf.format(new Date());
/* 310 */     return tz.substring(0, 3) + sep + tz.substring(3);
/*     */   }
/*     */ 
/*     */   public static int parseTimeField(String text, ParsePosition where, int maxlen, int remedy)
/*     */   {
/* 338 */     if (text == null)
/*     */     {
/* 340 */       return remedy;
/*     */     }
/*     */ 
/* 344 */     int retval = 0;
/* 345 */     int index = where.getIndex();
/* 346 */     int limit = index + Math.min(maxlen, text.length() - index);
/* 347 */     for (; index < limit; index++)
/*     */     {
/* 349 */       int cval = text.charAt(index) - '0';
/* 350 */       if ((cval < 0) || (cval > 9))
/*     */       {
/*     */         break;
/*     */       }
/* 354 */       retval = retval * 10 + cval;
/*     */     }
/* 356 */     if (index == where.getIndex())
/*     */     {
/* 358 */       return remedy;
/*     */     }
/* 360 */     where.setIndex(index);
/* 361 */     return retval;
/*     */   }
/*     */ 
/*     */   public static char skipOptionals(String text, ParsePosition where, String optionals)
/*     */   {
/* 381 */     char retval = ' ';
/*     */     char currch;
/* 385 */     while ((text != null) && (where.getIndex() < text.length()) && (optionals.indexOf(currch = text.charAt(where.getIndex())) >= 0))
/*     */     {
/* 387 */       retval = currch != ' ' ? currch : retval;
/* 388 */       where.setIndex(where.getIndex() + 1);
/*     */     }
/* 390 */     return retval;
/*     */   }
/*     */ 
/*     */   public static boolean skipString(String text, String victim, ParsePosition where)
/*     */   {
/* 406 */     if (text.startsWith(victim, where.getIndex()))
/*     */     {
/* 408 */       where.setIndex(where.getIndex() + victim.length());
/* 409 */       return true;
/*     */     }
/* 411 */     return false;
/*     */   }
/*     */ 
/*     */   public static GregorianCalendar newGreg()
/*     */   {
/* 424 */     GregorianCalendar retCal = new GregorianCalendar(Locale.ENGLISH);
/* 425 */     retCal.setTimeZone(new SimpleTimeZone(0, "UTC"));
/* 426 */     retCal.setLenient(false);
/* 427 */     retCal.set(14, 0);
/* 428 */     return retCal;
/*     */   }
/*     */ 
/*     */   public static void adjustTimeZoneNicely(GregorianCalendar cal, TimeZone tz)
/*     */   {
/* 441 */     cal.setTimeZone(tz);
/* 442 */     int offset = (cal.get(15) + cal.get(16)) / 60000;
/*     */ 
/* 444 */     cal.add(12, -offset);
/*     */   }
/*     */ 
/*     */   public static boolean parseTZoffset(String text, GregorianCalendar cal, ParsePosition initialWhere)
/*     */   {
/* 472 */     ParsePosition where = new ParsePosition(initialWhere.getIndex());
/* 473 */     TimeZone tz = new SimpleTimeZone(0, "GMT");
/*     */ 
/* 475 */     char sign = skipOptionals(text, where, "Z+- ");
/* 476 */     boolean hadGMT = (sign == 'Z') || (skipString(text, "GMT", where)) || (skipString(text, "UTC", where));
/*     */ 
/* 478 */     sign = !hadGMT ? sign : skipOptionals(text, where, "+- ");
/*     */ 
/* 480 */     int tzHours = parseTimeField(text, where, 2, -999);
/* 481 */     skipOptionals(text, where, "': ");
/* 482 */     int tzMin = parseTimeField(text, where, 2, 0);
/* 483 */     skipOptionals(text, where, "' ");
/*     */ 
/* 485 */     if (tzHours != -999)
/*     */     {
/* 487 */       int hrSign = sign == '-' ? -1 : 1;
/* 488 */       tz.setRawOffset(restrainTZoffset(hrSign * (tzHours * 3600000 + tzMin * 60000)));
/* 489 */       tz.setID("unknown");
/*     */     }
/* 491 */     else if (!hadGMT)
/*     */     {
/* 493 */       String tzText = text.substring(initialWhere.getIndex()).trim();
/* 494 */       tz = TimeZone.getTimeZone(tzText);
/*     */ 
/* 496 */       if ("GMT".equals(tz.getID()))
/*     */       {
/* 499 */         return false;
/*     */       }
/*     */ 
/* 503 */       where.setIndex(text.length());
/*     */     }
/*     */ 
/* 506 */     adjustTimeZoneNicely(cal, tz);
/* 507 */     initialWhere.setIndex(where.getIndex());
/* 508 */     return true;
/*     */   }
/*     */ 
/*     */   public static GregorianCalendar parseBigEndianDate(String text, ParsePosition initialWhere)
/*     */   {
/* 534 */     ParsePosition where = new ParsePosition(initialWhere.getIndex());
/* 535 */     int year = parseTimeField(text, where, 4, 0);
/* 536 */     if (where.getIndex() != 4 + initialWhere.getIndex())
/*     */     {
/* 538 */       return null;
/*     */     }
/* 540 */     skipOptionals(text, where, "/- ");
/* 541 */     int month = parseTimeField(text, where, 2, 1) - 1;
/* 542 */     skipOptionals(text, where, "/- ");
/* 543 */     int day = parseTimeField(text, where, 2, 1);
/* 544 */     skipOptionals(text, where, " T");
/* 545 */     int hour = parseTimeField(text, where, 2, 0);
/* 546 */     skipOptionals(text, where, ": ");
/* 547 */     int minute = parseTimeField(text, where, 2, 0);
/* 548 */     skipOptionals(text, where, ": ");
/* 549 */     int second = parseTimeField(text, where, 2, 0);
/* 550 */     char nextC = skipOptionals(text, where, ".");
/* 551 */     if (nextC == '.')
/*     */     {
/* 554 */       parseTimeField(text, where, 19, 0);
/*     */     }
/*     */ 
/* 557 */     GregorianCalendar dest = newGreg();
/*     */     try
/*     */     {
/* 560 */       dest.set(year, month, day, hour, minute, second);
/* 561 */       dest.getTimeInMillis();
/*     */     }
/*     */     catch (IllegalArgumentException ill)
/*     */     {
/* 565 */       return null;
/*     */     }
/* 567 */     initialWhere.setIndex(where.getIndex());
/* 568 */     skipOptionals(text, initialWhere, " ");
/* 569 */     return dest;
/*     */   }
/*     */ 
/*     */   public static GregorianCalendar parseSimpleDate(String text, String[] fmts, ParsePosition initialWhere)
/*     */   {
/* 595 */     for (String fmt : fmts)
/*     */     {
/* 597 */       ParsePosition where = new ParsePosition(initialWhere.getIndex());
/* 598 */       SimpleDateFormat sdf = new SimpleDateFormat(fmt, Locale.ENGLISH);
/* 599 */       GregorianCalendar retCal = newGreg();
/* 600 */       sdf.setCalendar(retCal);
/* 601 */       if (sdf.parse(text, where) != null)
/*     */       {
/* 603 */         initialWhere.setIndex(where.getIndex());
/* 604 */         skipOptionals(text, initialWhere, " ");
/* 605 */         return retCal;
/*     */       }
/*     */     }
/* 608 */     return null;
/*     */   }
/*     */ 
/*     */   public static Calendar parseDate(String text, String[] moreFmts, ParsePosition initialWhere)
/*     */   {
/* 648 */     int longestLen = -999999;
/*     */ 
/* 650 */     GregorianCalendar longestDate = null;
/*     */ 
/* 653 */     ParsePosition where = new ParsePosition(initialWhere.getIndex());
/*     */ 
/* 655 */     skipOptionals(text, where, " ");
/* 656 */     int startPosition = where.getIndex();
/*     */ 
/* 659 */     GregorianCalendar retCal = parseBigEndianDate(text, where);
/*     */ 
/* 661 */     if ((retCal != null) && ((where.getIndex() == text.length()) || (parseTZoffset(text, retCal, where))))
/*     */     {
/* 667 */       int whereLen = where.getIndex();
/* 668 */       if (whereLen == text.length())
/*     */       {
/* 670 */         initialWhere.setIndex(whereLen);
/* 671 */         return retCal;
/*     */       }
/* 673 */       longestLen = whereLen;
/* 674 */       longestDate = retCal;
/*     */     }
/*     */ 
/* 678 */     where.setIndex(startPosition);
/* 679 */     String[] formats = Character.isDigit(text.charAt(startPosition)) ? DIGIT_START_FORMATS : ALPHA_START_FORMATS;
/*     */ 
/* 683 */     retCal = parseSimpleDate(text, formats, where);
/*     */ 
/* 685 */     if ((retCal != null) && ((where.getIndex() == text.length()) || (parseTZoffset(text, retCal, where))))
/*     */     {
/* 691 */       int whereLen = where.getIndex();
/* 692 */       if (whereLen == text.length())
/*     */       {
/* 694 */         initialWhere.setIndex(whereLen);
/* 695 */         return retCal;
/*     */       }
/* 697 */       if (whereLen > longestLen)
/*     */       {
/* 699 */         longestLen = whereLen;
/* 700 */         longestDate = retCal;
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 705 */     if (moreFmts != null)
/*     */     {
/* 707 */       where.setIndex(startPosition);
/* 708 */       retCal = parseSimpleDate(text, moreFmts, where);
/* 709 */       if ((retCal != null) && ((where.getIndex() == text.length()) || (parseTZoffset(text, retCal, where))))
/*     */       {
/* 713 */         int whereLen = where.getIndex();
/*     */ 
/* 716 */         if ((whereLen == text.length()) || ((longestDate != null) && (whereLen > longestLen)))
/*     */         {
/* 719 */           initialWhere.setIndex(whereLen);
/* 720 */           return retCal;
/*     */         }
/*     */       }
/*     */     }
/* 724 */     if (longestDate != null)
/*     */     {
/* 726 */       initialWhere.setIndex(longestLen);
/* 727 */       return longestDate;
/*     */     }
/* 729 */     return retCal;
/*     */   }
/*     */ 
/*     */   /** @deprecated */
/*     */   public static Calendar toCalendar(COSString text)
/*     */     throws IOException
/*     */   {
/* 749 */     if (text == null)
/*     */     {
/* 751 */       return null;
/*     */     }
/* 753 */     return toCalendar(text.getString());
/*     */   }
/*     */ 
/*     */   /** @deprecated */
/*     */   public static Calendar toCalendar(String text)
/*     */     throws IOException
/*     */   {
/* 776 */     if ((text == null) || ("".equals(text)))
/*     */     {
/* 778 */       return null;
/*     */     }
/* 780 */     Calendar val = toCalendar(text, null);
/* 781 */     if ((val != null) && (val.get(1) == 999))
/*     */     {
/* 783 */       throw new IOException("Error converting date: " + text);
/*     */     }
/* 785 */     return val;
/*     */   }
/*     */ 
/*     */   public static Calendar toCalendar(String text, String[] moreFmts)
/*     */   {
/* 805 */     ParsePosition where = new ParsePosition(0);
/* 806 */     skipOptionals(text, where, " ");
/* 807 */     skipString(text, "D:", where);
/* 808 */     Calendar retCal = parseDate(text, moreFmts, where);
/* 809 */     if ((retCal == null) || (where.getIndex() != text.length()))
/*     */     {
/* 812 */       retCal = newGreg();
/* 813 */       retCal.set(999, 0, 1, 0, 0, 0);
/*     */     }
/* 815 */     return retCal;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.util.DateConverter
 * JD-Core Version:    0.6.2
 */