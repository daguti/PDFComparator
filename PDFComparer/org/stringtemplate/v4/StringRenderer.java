/*    */ package org.stringtemplate.v4;
/*    */ 
/*    */ import java.net.URLEncoder;
/*    */ import java.util.Locale;
/*    */ 
/*    */ public class StringRenderer
/*    */   implements AttributeRenderer
/*    */ {
/*    */   public String toString(Object o, String formatString, Locale locale)
/*    */   {
/* 39 */     String s = (String)o;
/* 40 */     if (formatString == null) return s;
/* 41 */     if (formatString.equals("upper")) return s.toUpperCase(locale);
/* 42 */     if (formatString.equals("lower")) return s.toLowerCase(locale);
/* 43 */     if (formatString.equals("cap")) {
/* 44 */       return s.length() > 0 ? Character.toUpperCase(s.charAt(0)) + s.substring(1) : s;
/*    */     }
/* 46 */     if (formatString.equals("url-encode")) {
/* 47 */       return URLEncoder.encode(s);
/*    */     }
/* 49 */     if (formatString.equals("xml-encode")) {
/* 50 */       return escapeHTML(s);
/*    */     }
/* 52 */     return String.format(formatString, new Object[] { s });
/*    */   }
/*    */ 
/*    */   public static String escapeHTML(String s) {
/* 56 */     if (s == null) {
/* 57 */       return null;
/*    */     }
/* 59 */     StringBuilder buf = new StringBuilder(s.length());
/* 60 */     int len = s.length();
/* 61 */     for (int i = 0; i < len; i++) {
/* 62 */       char c = s.charAt(i);
/* 63 */       switch (c) {
/*    */       case '&':
/* 65 */         buf.append("&amp;");
/* 66 */         break;
/*    */       case '<':
/* 68 */         buf.append("&lt;");
/* 69 */         break;
/*    */       case '>':
/* 71 */         buf.append("&gt;");
/* 72 */         break;
/*    */       case '\t':
/*    */       case '\n':
/*    */       case '\r':
/* 76 */         buf.append(c);
/* 77 */         break;
/*    */       default:
/* 79 */         boolean control = c < ' ';
/* 80 */         boolean aboveASCII = c > '~';
/* 81 */         if ((control) || (aboveASCII)) {
/* 82 */           buf.append("&#");
/* 83 */           buf.append(c);
/* 84 */           buf.append(";");
/*    */         } else {
/* 86 */           buf.append(c); } break;
/*    */       }
/*    */     }
/* 89 */     return buf.toString();
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.stringtemplate.v4.StringRenderer
 * JD-Core Version:    0.6.2
 */