/*    */ package org.apache.pdfbox.util;
/*    */ 
/*    */ import java.util.Comparator;
/*    */ 
/*    */ public class TextPositionComparator
/*    */   implements Comparator
/*    */ {
/*    */   public int compare(Object o1, Object o2)
/*    */   {
/* 37 */     int retval = 0;
/* 38 */     TextPosition pos1 = (TextPosition)o1;
/* 39 */     TextPosition pos2 = (TextPosition)o2;
/*    */ 
/* 42 */     if (pos1.getDir() < pos2.getDir())
/*    */     {
/* 44 */       return -1;
/*    */     }
/* 46 */     if (pos1.getDir() > pos2.getDir())
/*    */     {
/* 48 */       return 1;
/*    */     }
/*    */ 
/* 52 */     float x1 = pos1.getXDirAdj();
/* 53 */     float x2 = pos2.getXDirAdj();
/*    */ 
/* 55 */     float pos1YBottom = pos1.getYDirAdj();
/* 56 */     float pos2YBottom = pos2.getYDirAdj();
/*    */ 
/* 58 */     float pos1YTop = pos1YBottom - pos1.getHeightDir();
/* 59 */     float pos2YTop = pos2YBottom - pos2.getHeightDir();
/*    */ 
/* 61 */     float yDifference = Math.abs(pos1YBottom - pos2YBottom);
/*    */ 
/* 63 */     if ((yDifference < 0.1D) || ((pos2YBottom >= pos1YTop) && (pos2YBottom <= pos1YBottom)) || ((pos1YBottom >= pos2YTop) && (pos1YBottom <= pos2YBottom)))
/*    */     {
/* 67 */       if (x1 < x2)
/*    */       {
/* 69 */         retval = -1;
/*    */       }
/* 71 */       else if (x1 > x2)
/*    */       {
/* 73 */         retval = 1;
/*    */       }
/*    */       else
/*    */       {
/* 77 */         retval = 0;
/*    */       }
/*    */     }
/* 80 */     else if (pos1YBottom < pos2YBottom)
/*    */     {
/* 82 */       retval = -1;
/*    */     }
/*    */     else
/*    */     {
/* 86 */       return 1;
/*    */     }
/* 88 */     return retval;
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.util.TextPositionComparator
 * JD-Core Version:    0.6.2
 */