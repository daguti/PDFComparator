/*    */ package com.itextpdf.text.pdf;
/*    */ 
/*    */ import java.io.IOException;
/*    */ 
/*    */ public class PdfPSXObject extends PdfTemplate
/*    */ {
/*    */   protected PdfPSXObject()
/*    */   {
/*    */   }
/*    */ 
/*    */   public PdfPSXObject(PdfWriter wr)
/*    */   {
/* 64 */     super(wr);
/*    */   }
/*    */ 
/*    */   public PdfStream getFormXObject(int compressionLevel)
/*    */     throws IOException
/*    */   {
/* 77 */     PdfStream s = new PdfStream(this.content.toByteArray());
/* 78 */     s.put(PdfName.TYPE, PdfName.XOBJECT);
/* 79 */     s.put(PdfName.SUBTYPE, PdfName.PS);
/* 80 */     s.flateCompress(compressionLevel);
/* 81 */     return s;
/*    */   }
/*    */ 
/*    */   public PdfContentByte getDuplicate()
/*    */   {
/* 91 */     PdfPSXObject tpl = new PdfPSXObject();
/* 92 */     tpl.writer = this.writer;
/* 93 */     tpl.pdf = this.pdf;
/* 94 */     tpl.thisReference = this.thisReference;
/* 95 */     tpl.pageResources = this.pageResources;
/* 96 */     tpl.separator = this.separator;
/* 97 */     return tpl;
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.pdf.PdfPSXObject
 * JD-Core Version:    0.6.2
 */