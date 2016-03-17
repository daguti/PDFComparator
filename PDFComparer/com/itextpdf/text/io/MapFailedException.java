/*    */ package com.itextpdf.text.io;
/*    */ 
/*    */ import java.io.IOException;
/*    */ 
/*    */ public class MapFailedException extends IOException
/*    */ {
/*    */   public MapFailedException(IOException e)
/*    */   {
/* 50 */     super(e.getMessage());
/* 51 */     initCause(e);
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.io.MapFailedException
 * JD-Core Version:    0.6.2
 */