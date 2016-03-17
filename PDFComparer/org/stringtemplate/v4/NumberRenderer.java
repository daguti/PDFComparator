/*    */ package org.stringtemplate.v4;
/*    */ 
/*    */ import java.util.Formatter;
/*    */ import java.util.Locale;
/*    */ 
/*    */ public class NumberRenderer
/*    */   implements AttributeRenderer
/*    */ {
/*    */   public String toString(Object o, String formatString, Locale locale)
/*    */   {
/* 45 */     if (formatString == null) return o.toString();
/* 46 */     Formatter f = new Formatter(locale);
/* 47 */     f.format(formatString, new Object[] { o });
/* 48 */     return f.toString();
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.stringtemplate.v4.NumberRenderer
 * JD-Core Version:    0.6.2
 */