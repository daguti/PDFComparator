/*    */ package org.apache.pdfbox.util;
/*    */ 
/*    */ import java.io.PrintStream;
/*    */ 
/*    */ public class ErrorLogger
/*    */ {
/*    */   public static void log(String errorMessage)
/*    */   {
/* 43 */     System.err.println(errorMessage);
/*    */   }
/*    */ 
/*    */   public static void log(String errorMessage, Throwable t)
/*    */   {
/* 55 */     System.err.println(errorMessage);
/* 56 */     t.printStackTrace();
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.util.ErrorLogger
 * JD-Core Version:    0.6.2
 */