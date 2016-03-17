/*    */ package com.itextpdf.text.pdf;
/*    */ 
/*    */ import java.io.IOException;
/*    */ 
/*    */ public class PdfMediaClipData extends PdfDictionary
/*    */ {
/*    */   PdfMediaClipData(String file, PdfFileSpecification fs, String mimeType)
/*    */     throws IOException
/*    */   {
/* 52 */     put(PdfName.TYPE, new PdfName("MediaClip"));
/* 53 */     put(PdfName.S, new PdfName("MCD"));
/* 54 */     put(PdfName.N, new PdfString("Media clip for " + file));
/* 55 */     put(new PdfName("CT"), new PdfString(mimeType));
/* 56 */     PdfDictionary dic = new PdfDictionary();
/* 57 */     dic.put(new PdfName("TF"), new PdfString("TEMPACCESS"));
/* 58 */     put(new PdfName("P"), dic);
/* 59 */     put(PdfName.D, fs.getReference());
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.pdf.PdfMediaClipData
 * JD-Core Version:    0.6.2
 */