/*    */ package antlr;
/*    */ 
/*    */ public class StringUtils
/*    */ {
/*    */   public static String stripBack(String paramString, char paramChar)
/*    */   {
/* 11 */     while ((paramString.length() > 0) && (paramString.charAt(paramString.length() - 1) == paramChar)) {
/* 12 */       paramString = paramString.substring(0, paramString.length() - 1);
/*    */     }
/* 14 */     return paramString;
/*    */   }
/*    */ 
/*    */   public static String stripBack(String paramString1, String paramString2)
/*    */   {
/*    */     int i;
/*    */     do
/*    */     {
/* 26 */       i = 0;
/* 27 */       for (int j = 0; j < paramString2.length(); j++) {
/* 28 */         int k = paramString2.charAt(j);
/* 29 */         while ((paramString1.length() > 0) && (paramString1.charAt(paramString1.length() - 1) == k)) {
/* 30 */           i = 1;
/* 31 */           paramString1 = paramString1.substring(0, paramString1.length() - 1);
/*    */         }
/*    */       }
/*    */     }
/* 34 */     while (i != 0);
/* 35 */     return paramString1;
/*    */   }
/*    */ 
/*    */   public static String stripFront(String paramString, char paramChar)
/*    */   {
/* 45 */     while ((paramString.length() > 0) && (paramString.charAt(0) == paramChar)) {
/* 46 */       paramString = paramString.substring(1);
/*    */     }
/* 48 */     return paramString;
/*    */   }
/*    */ 
/*    */   public static String stripFront(String paramString1, String paramString2)
/*    */   {
/*    */     int i;
/*    */     do
/*    */     {
/* 60 */       i = 0;
/* 61 */       for (int j = 0; j < paramString2.length(); j++) {
/* 62 */         int k = paramString2.charAt(j);
/* 63 */         while ((paramString1.length() > 0) && (paramString1.charAt(0) == k)) {
/* 64 */           i = 1;
/* 65 */           paramString1 = paramString1.substring(1);
/*    */         }
/*    */       }
/*    */     }
/* 68 */     while (i != 0);
/* 69 */     return paramString1;
/*    */   }
/*    */ 
/*    */   public static String stripFrontBack(String paramString1, String paramString2, String paramString3)
/*    */   {
/* 80 */     int i = paramString1.indexOf(paramString2);
/* 81 */     int j = paramString1.lastIndexOf(paramString3);
/* 82 */     if ((i == -1) || (j == -1)) return paramString1;
/* 83 */     return paramString1.substring(i + 1, j);
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     antlr.StringUtils
 * JD-Core Version:    0.6.2
 */