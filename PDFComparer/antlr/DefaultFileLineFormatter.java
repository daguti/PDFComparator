/*    */ package antlr;
/*    */ 
/*    */ public class DefaultFileLineFormatter extends FileLineFormatter
/*    */ {
/*    */   public String getFormatString(String paramString, int paramInt1, int paramInt2)
/*    */   {
/* 12 */     StringBuffer localStringBuffer = new StringBuffer();
/*    */ 
/* 14 */     if (paramString != null) {
/* 15 */       localStringBuffer.append(paramString + ":");
/*    */     }
/* 17 */     if (paramInt1 != -1) {
/* 18 */       if (paramString == null) {
/* 19 */         localStringBuffer.append("line ");
/*    */       }
/* 21 */       localStringBuffer.append(paramInt1);
/*    */ 
/* 23 */       if (paramInt2 != -1) {
/* 24 */         localStringBuffer.append(":" + paramInt2);
/*    */       }
/* 26 */       localStringBuffer.append(":");
/*    */     }
/*    */ 
/* 29 */     localStringBuffer.append(" ");
/*    */ 
/* 31 */     return localStringBuffer.toString();
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     antlr.DefaultFileLineFormatter
 * JD-Core Version:    0.6.2
 */