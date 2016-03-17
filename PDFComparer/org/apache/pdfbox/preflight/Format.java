/*    */ package org.apache.pdfbox.preflight;
/*    */ 
/*    */ public enum Format
/*    */ {
/* 26 */   PDF_A1B("PDF/A1-b"), PDF_A1A("PDF/A1-a");
/*    */ 
/*    */   private final String fname;
/*    */ 
/*    */   private Format(String name)
/*    */   {
/* 32 */     this.fname = name;
/*    */   }
/*    */ 
/*    */   public String getFname()
/*    */   {
/* 37 */     return this.fname;
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.preflight.Format
 * JD-Core Version:    0.6.2
 */