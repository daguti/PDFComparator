/*    */ package org.stringtemplate.v4;
/*    */ 
/*    */ import java.text.DateFormat;
/*    */ import java.text.SimpleDateFormat;
/*    */ import java.util.Calendar;
/*    */ import java.util.Date;
/*    */ import java.util.HashMap;
/*    */ import java.util.Locale;
/*    */ import java.util.Map;
/*    */ 
/*    */ public class DateRenderer
/*    */   implements AttributeRenderer
/*    */ {
/* 40 */   public static final Map<String, Integer> formatToInt = new HashMap() { } ;
/*    */ 
/*    */   public String toString(Object o, String formatString, Locale locale)
/*    */   {
/* 62 */     if (formatString == null) formatString = "short";
/* 63 */     Date d;
/*    */     Date d;
/* 63 */     if ((o instanceof Calendar)) d = ((Calendar)o).getTime(); else
/* 64 */       d = (Date)o;
/* 65 */     Integer styleI = (Integer)formatToInt.get(formatString);
/*    */     DateFormat f;
/*    */     DateFormat f;
/* 67 */     if (styleI == null) { f = new SimpleDateFormat(formatString);
/*    */     } else {
/* 69 */       int style = styleI.intValue();
/*    */       DateFormat f;
/* 70 */       if (formatString.startsWith("date:")) { f = DateFormat.getDateInstance(style); }
/*    */       else
/*    */       {
/* 71 */         DateFormat f;
/* 71 */         if (formatString.startsWith("time:")) f = DateFormat.getTimeInstance(style); else
/* 72 */           f = DateFormat.getDateTimeInstance(style, style); 
/*    */       }
/*    */     }
/* 74 */     return f.format(d);
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.stringtemplate.v4.DateRenderer
 * JD-Core Version:    0.6.2
 */