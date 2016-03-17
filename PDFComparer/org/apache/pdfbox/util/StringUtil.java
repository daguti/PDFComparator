/*    */ package org.apache.pdfbox.util;
/*    */ 
/*    */ import java.io.UnsupportedEncodingException;
/*    */ 
/*    */ public class StringUtil
/*    */ {
/*    */   public static byte[] getBytes(String s)
/*    */   {
/*    */     try
/*    */     {
/* 32 */       return s.getBytes("ISO-8859-1");
/*    */     }
/*    */     catch (UnsupportedEncodingException e)
/*    */     {
/* 36 */       throw new RuntimeException("Unsupported Encoding", e);
/*    */     }
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.util.StringUtil
 * JD-Core Version:    0.6.2
 */