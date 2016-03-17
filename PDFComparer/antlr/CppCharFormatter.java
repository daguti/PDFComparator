/*    */ package antlr;
/*    */ 
/*    */ class CppCharFormatter
/*    */   implements CharFormatter
/*    */ {
/*    */   public String escapeChar(int paramInt, boolean paramBoolean)
/*    */   {
/* 28 */     switch (paramInt) { case 10:
/* 29 */       return "\\n";
/*    */     case 9:
/* 30 */       return "\\t";
/*    */     case 13:
/* 31 */       return "\\r";
/*    */     case 92:
/* 32 */       return "\\\\";
/*    */     case 39:
/* 33 */       return "\\'";
/*    */     case 34:
/* 34 */       return "\\\"";
/*    */     }
/* 36 */     if ((paramInt < 32) || (paramInt > 126))
/*    */     {
/* 38 */       if (paramInt > 255)
/*    */       {
/* 40 */         String str = Integer.toString(paramInt, 16);
/*    */ 
/* 42 */         while (str.length() < 4)
/* 43 */           str = '0' + str;
/* 44 */         return "\\u" + str;
/*    */       }
/*    */ 
/* 47 */       return "\\" + Integer.toString(paramInt, 8);
/*    */     }
/*    */ 
/* 51 */     return String.valueOf((char)paramInt);
/*    */   }
/*    */ 
/*    */   public String escapeString(String paramString)
/*    */   {
/* 65 */     String str = new String();
/* 66 */     for (int i = 0; i < paramString.length(); i++) {
/* 67 */       str = str + escapeChar(paramString.charAt(i), false);
/*    */     }
/* 69 */     return str;
/*    */   }
/*    */ 
/*    */   public String literalChar(int paramInt)
/*    */   {
/* 78 */     String str = "0x" + Integer.toString(paramInt, 16);
/* 79 */     if ((paramInt >= 0) && (paramInt <= 126))
/* 80 */       str = str + " /* '" + escapeChar(paramInt, true) + "' */ ";
/* 81 */     return str;
/*    */   }
/*    */ 
/*    */   public String literalString(String paramString)
/*    */   {
/* 94 */     return "\"" + escapeString(paramString) + "\"";
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     antlr.CppCharFormatter
 * JD-Core Version:    0.6.2
 */