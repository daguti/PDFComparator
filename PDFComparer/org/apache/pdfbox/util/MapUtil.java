/*    */ package org.apache.pdfbox.util;
/*    */ 
/*    */ import java.util.Map;
/*    */ 
/*    */ public class MapUtil
/*    */ {
/*    */   public static final String getNextUniqueKey(Map<String, ?> map, String prefix)
/*    */   {
/* 44 */     int counter = 0;
/* 45 */     while ((map != null) && (map.get(prefix + counter) != null))
/*    */     {
/* 47 */       counter++;
/*    */     }
/* 49 */     return prefix + counter;
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.util.MapUtil
 * JD-Core Version:    0.6.2
 */