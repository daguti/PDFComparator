/*    */ package com.itextpdf.text.pdf;
/*    */ 
/*    */ import com.itextpdf.text.DocumentException;
/*    */ 
/*    */ public class PdfException extends DocumentException
/*    */ {
/*    */   private static final long serialVersionUID = 6767433960955483999L;
/*    */ 
/*    */   public PdfException(Exception ex)
/*    */   {
/* 62 */     super(ex);
/*    */   }
/*    */ 
/*    */   PdfException()
/*    */   {
/*    */   }
/*    */ 
/*    */   PdfException(String message)
/*    */   {
/* 80 */     super(message);
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.pdf.PdfException
 * JD-Core Version:    0.6.2
 */