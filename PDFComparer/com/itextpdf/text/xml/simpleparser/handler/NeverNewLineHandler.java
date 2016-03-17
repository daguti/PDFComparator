/*    */ package com.itextpdf.text.xml.simpleparser.handler;
/*    */ 
/*    */ import com.itextpdf.text.xml.simpleparser.NewLineHandler;
/*    */ 
/*    */ public class NeverNewLineHandler
/*    */   implements NewLineHandler
/*    */ {
/*    */   public boolean isNewLineTag(String tag)
/*    */   {
/* 65 */     return false;
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.xml.simpleparser.handler.NeverNewLineHandler
 * JD-Core Version:    0.6.2
 */