/*    */ package antlr;
/*    */ 
/*    */ class CSharpCharFormatter
/*    */   implements CharFormatter
/*    */ {
/*    */   public String escapeChar(int paramInt, boolean paramBoolean)
/*    */   {
/* 26 */     switch (paramInt)
/*    */     {
/*    */     case 10:
/* 29 */       return "\\n";
/*    */     case 9:
/* 30 */       return "\\t";
/*    */     case 13:
/* 31 */       return "\\r";
/*    */     case 92:
/* 32 */       return "\\\\";
/*    */     case 39:
/* 33 */       return paramBoolean ? "\\'" : "'";
/*    */     case 34:
/* 34 */       return paramBoolean ? "\"" : "\\\"";
/*    */     }
/* 36 */     if ((paramInt < 32) || (paramInt > 126))
/*    */     {
/* 38 */       if ((0 <= paramInt) && (paramInt <= 15))
/*    */       {
/* 40 */         return "\\u000" + Integer.toString(paramInt, 16);
/*    */       }
/* 42 */       if ((16 <= paramInt) && (paramInt <= 255))
/*    */       {
/* 44 */         return "\\u00" + Integer.toString(paramInt, 16);
/*    */       }
/* 46 */       if ((256 <= paramInt) && (paramInt <= 4095))
/*    */       {
/* 48 */         return "\\u0" + Integer.toString(paramInt, 16);
/*    */       }
/*    */ 
/* 52 */       return "\\u" + Integer.toString(paramInt, 16);
/*    */     }
/*    */ 
/* 57 */     return String.valueOf((char)paramInt);
/*    */   }
/*    */ 
/*    */   public String escapeString(String paramString)
/*    */   {
/* 69 */     String str = new String();
/* 70 */     for (int i = 0; i < paramString.length(); i++)
/*    */     {
/* 72 */       str = str + escapeChar(paramString.charAt(i), false);
/*    */     }
/* 74 */     return str;
/*    */   }
/*    */ 
/*    */   public String literalChar(int paramInt)
/*    */   {
/* 86 */     return "'" + escapeChar(paramInt, true) + "'";
/*    */   }
/*    */ 
/*    */   public String literalString(String paramString)
/*    */   {
/* 98 */     return "@\"\"\"" + escapeString(paramString) + "\"\"\"";
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     antlr.CSharpCharFormatter
 * JD-Core Version:    0.6.2
 */