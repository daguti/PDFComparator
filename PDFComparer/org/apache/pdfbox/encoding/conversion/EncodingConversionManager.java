/*    */ package org.apache.pdfbox.encoding.conversion;
/*    */ 
/*    */ import java.util.HashMap;
/*    */ import java.util.Iterator;
/*    */ 
/*    */ public class EncodingConversionManager
/*    */ {
/* 36 */   private static HashMap encodingMap = new HashMap();
/*    */ 
/*    */   public static final EncodingConverter getConverter(String encoding)
/*    */   {
/* 68 */     return (EncodingConverter)encodingMap.get(encoding);
/*    */   }
/*    */ 
/*    */   static
/*    */   {
/* 48 */     Iterator it = CJKEncodings.getEncodingIterator();
/*    */ 
/* 50 */     while (it.hasNext())
/*    */     {
/* 52 */       String encodingName = (String)it.next();
/* 53 */       encodingMap.put(encodingName, new CJKConverter(encodingName));
/*    */     }
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.encoding.conversion.EncodingConversionManager
 * JD-Core Version:    0.6.2
 */