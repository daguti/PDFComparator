/*    */ package org.antlr.misc;
/*    */ 
/*    */ public class Utils
/*    */ {
/*    */   public static final int INTEGER_POOL_MAX_VALUE = 1000;
/* 32 */   static Integer[] ints = new Integer[1001];
/*    */ 
/*    */   public static Integer integer(int x)
/*    */   {
/* 40 */     if ((x < 0) || (x > 1000)) {
/* 41 */       return new Integer(x);
/*    */     }
/* 43 */     if (ints[x] == null) {
/* 44 */       ints[x] = new Integer(x);
/*    */     }
/* 46 */     return ints[x];
/*    */   }
/*    */ 
/*    */   public static String replace(String src, String replacee, String replacer)
/*    */   {
/* 59 */     StringBuffer result = new StringBuffer(src.length() + 50);
/* 60 */     int startIndex = 0;
/* 61 */     int endIndex = src.indexOf(replacee);
/* 62 */     while (endIndex != -1) {
/* 63 */       result.append(src.substring(startIndex, endIndex));
/* 64 */       if (replacer != null) {
/* 65 */         result.append(replacer);
/*    */       }
/* 67 */       startIndex = endIndex + replacee.length();
/* 68 */       endIndex = src.indexOf(replacee, startIndex);
/*    */     }
/* 70 */     result.append(src.substring(startIndex, src.length()));
/* 71 */     return result.toString();
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.antlr.misc.Utils
 * JD-Core Version:    0.6.2
 */