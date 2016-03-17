/*    */ package antlr;
/*    */ 
/*    */ public abstract class FileLineFormatter
/*    */ {
/* 12 */   private static FileLineFormatter formatter = new DefaultFileLineFormatter();
/*    */ 
/*    */   public static FileLineFormatter getFormatter() {
/* 15 */     return formatter;
/*    */   }
/*    */ 
/*    */   public static void setFormatter(FileLineFormatter paramFileLineFormatter) {
/* 19 */     formatter = paramFileLineFormatter;
/*    */   }
/*    */ 
/*    */   public abstract String getFormatString(String paramString, int paramInt1, int paramInt2);
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     antlr.FileLineFormatter
 * JD-Core Version:    0.6.2
 */