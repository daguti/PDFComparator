/*    */ package antlr;
/*    */ 
/*    */ class JavaCharFormatter
/*    */   implements CharFormatter
/*    */ {
/*    */   public String escapeChar(int paramInt, boolean paramBoolean)
/*    */   {
/* 22 */     switch (paramInt)
/*    */     {
/*    */     case 10:
/* 25 */       return "\\n";
/*    */     case 9:
/* 27 */       return "\\t";
/*    */     case 13:
/* 29 */       return "\\r";
/*    */     case 92:
/* 31 */       return "\\\\";
/*    */     case 39:
/* 33 */       return paramBoolean ? "\\'" : "'";
/*    */     case 34:
/* 35 */       return paramBoolean ? "\"" : "\\\"";
/*    */     }
/* 37 */     if ((paramInt < 32) || (paramInt > 126)) {
/* 38 */       if ((0 <= paramInt) && (paramInt <= 15)) {
/* 39 */         return "\\u000" + Integer.toString(paramInt, 16);
/*    */       }
/* 41 */       if ((16 <= paramInt) && (paramInt <= 255)) {
/* 42 */         return "\\u00" + Integer.toString(paramInt, 16);
/*    */       }
/* 44 */       if ((256 <= paramInt) && (paramInt <= 4095)) {
/* 45 */         return "\\u0" + Integer.toString(paramInt, 16);
/*    */       }
/*    */ 
/* 48 */       return "\\u" + Integer.toString(paramInt, 16);
/*    */     }
/*    */ 
/* 52 */     return String.valueOf((char)paramInt);
/*    */   }
/*    */ 
/*    */   public String escapeString(String paramString)
/*    */   {
/* 62 */     String str = new String();
/* 63 */     for (int i = 0; i < paramString.length(); i++) {
/* 64 */       str = str + escapeChar(paramString.charAt(i), false);
/*    */     }
/* 66 */     return str;
/*    */   }
/*    */ 
/*    */   public String literalChar(int paramInt)
/*    */   {
/* 76 */     return "'" + escapeChar(paramInt, true) + "'";
/*    */   }
/*    */ 
/*    */   public String literalString(String paramString)
/*    */   {
/* 85 */     return "\"" + escapeString(paramString) + "\"";
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     antlr.JavaCharFormatter
 * JD-Core Version:    0.6.2
 */