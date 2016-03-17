/*    */ package com.itextpdf.text.pdf;
/*    */ 
/*    */ import java.io.IOException;
/*    */ 
/*    */ public class PdfRendition extends PdfDictionary
/*    */ {
/*    */   PdfRendition(String file, PdfFileSpecification fs, String mimeType)
/*    */     throws IOException
/*    */   {
/* 54 */     put(PdfName.S, new PdfName("MR"));
/* 55 */     put(PdfName.N, new PdfString("Rendition for " + file));
/* 56 */     put(PdfName.C, new PdfMediaClipData(file, fs, mimeType));
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.pdf.PdfRendition
 * JD-Core Version:    0.6.2
 */