/*    */ package com.itextpdf.text.pdf;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.io.OutputStream;
/*    */ 
/*    */ public class PRIndirectReference extends PdfIndirectReference
/*    */ {
/*    */   protected PdfReader reader;
/*    */ 
/*    */   public PRIndirectReference(PdfReader reader, int number, int generation)
/*    */   {
/* 65 */     this.type = 10;
/* 66 */     this.number = number;
/* 67 */     this.generation = generation;
/* 68 */     this.reader = reader;
/*    */   }
/*    */ 
/*    */   public PRIndirectReference(PdfReader reader, int number)
/*    */   {
/* 79 */     this(reader, number, 0);
/*    */   }
/*    */ 
/*    */   public void toPdf(PdfWriter writer, OutputStream os)
/*    */     throws IOException
/*    */   {
/* 85 */     int n = writer.getNewObjectNumber(this.reader, this.number, this.generation);
/* 86 */     os.write(PdfEncodings.convertToBytes(n + " " + (this.reader.isAppendable() ? this.generation : 0) + " R", null));
/*    */   }
/*    */ 
/*    */   public PdfReader getReader() {
/* 90 */     return this.reader;
/*    */   }
/*    */ 
/*    */   public void setNumber(int number, int generation) {
/* 94 */     this.number = number;
/* 95 */     this.generation = generation;
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.pdf.PRIndirectReference
 * JD-Core Version:    0.6.2
 */