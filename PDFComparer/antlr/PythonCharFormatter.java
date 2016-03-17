/*    */ package antlr;
/*    */ 
/*    */ public class PythonCharFormatter
/*    */   implements CharFormatter
/*    */ {
/*    */   public String escapeChar(int paramInt, boolean paramBoolean)
/*    */   {
/* 12 */     String str = _escapeChar(paramInt, paramBoolean);
/*    */ 
/* 14 */     return str;
/*    */   }
/*    */ 
/*    */   public String _escapeChar(int paramInt, boolean paramBoolean)
/*    */   {
/* 19 */     switch (paramInt)
/*    */     {
/*    */     case 10:
/* 22 */       return "\\n";
/*    */     case 9:
/* 24 */       return "\\t";
/*    */     case 13:
/* 26 */       return "\\r";
/*    */     case 92:
/* 28 */       return "\\\\";
/*    */     case 39:
/* 30 */       return paramBoolean ? "\\'" : "'";
/*    */     case 34:
/* 32 */       return paramBoolean ? "\"" : "\\\"";
/*    */     }
/* 34 */     if ((paramInt < 32) || (paramInt > 126)) {
/* 35 */       if ((0 <= paramInt) && (paramInt <= 15)) {
/* 36 */         return "\\u000" + Integer.toString(paramInt, 16);
/*    */       }
/* 38 */       if ((16 <= paramInt) && (paramInt <= 255)) {
/* 39 */         return "\\u00" + Integer.toString(paramInt, 16);
/*    */       }
/* 41 */       if ((256 <= paramInt) && (paramInt <= 4095)) {
/* 42 */         return "\\u0" + Integer.toString(paramInt, 16);
/*    */       }
/*    */ 
/* 45 */       return "\\u" + Integer.toString(paramInt, 16);
/*    */     }
/*    */ 
/* 49 */     return String.valueOf((char)paramInt);
/*    */   }
/*    */ 
/*    */   public String escapeString(String paramString)
/*    */   {
/* 55 */     String str = new String();
/* 56 */     for (int i = 0; i < paramString.length(); i++) {
/* 57 */       str = str + escapeChar(paramString.charAt(i), false);
/*    */     }
/* 59 */     return str;
/*    */   }
/*    */ 
/*    */   public String literalChar(int paramInt) {
/* 63 */     return "" + escapeChar(paramInt, true) + "";
/*    */   }
/*    */ 
/*    */   public String literalString(String paramString) {
/* 67 */     return "\"" + escapeString(paramString) + "\"";
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     antlr.PythonCharFormatter
 * JD-Core Version:    0.6.2
 */