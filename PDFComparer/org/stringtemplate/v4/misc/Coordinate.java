/*    */ package org.stringtemplate.v4.misc;
/*    */ 
/*    */ public class Coordinate
/*    */ {
/*    */   public int line;
/*    */   public int charPosition;
/*    */ 
/*    */   public Coordinate(int a, int b)
/*    */   {
/* 36 */     this.line = a; this.charPosition = b; } 
/* 37 */   public String toString() { return this.line + ":" + this.charPosition; }
/*    */ 
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.stringtemplate.v4.misc.Coordinate
 * JD-Core Version:    0.6.2
 */