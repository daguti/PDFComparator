/*    */ package com.itextpdf.text;
/*    */ 
/*    */ public class Header extends Meta
/*    */ {
/*    */   private StringBuffer name;
/*    */ 
/*    */   public Header(String name, String content)
/*    */   {
/* 77 */     super(0, content);
/* 78 */     this.name = new StringBuffer(name);
/*    */   }
/*    */ 
/*    */   public String getName()
/*    */   {
/* 89 */     return this.name.toString();
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.Header
 * JD-Core Version:    0.6.2
 */