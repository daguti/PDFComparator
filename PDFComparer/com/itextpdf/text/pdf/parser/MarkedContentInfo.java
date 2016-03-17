/*    */ package com.itextpdf.text.pdf.parser;
/*    */ 
/*    */ import com.itextpdf.text.pdf.PdfDictionary;
/*    */ import com.itextpdf.text.pdf.PdfName;
/*    */ import com.itextpdf.text.pdf.PdfNumber;
/*    */ 
/*    */ public class MarkedContentInfo
/*    */ {
/*    */   private final PdfName tag;
/*    */   private final PdfDictionary dictionary;
/*    */ 
/*    */   public MarkedContentInfo(PdfName tag, PdfDictionary dictionary)
/*    */   {
/* 60 */     this.tag = tag;
/* 61 */     this.dictionary = (dictionary != null ? dictionary : new PdfDictionary());
/*    */   }
/*    */ 
/*    */   public PdfName getTag()
/*    */   {
/* 69 */     return this.tag;
/*    */   }
/*    */ 
/*    */   public boolean hasMcid()
/*    */   {
/* 77 */     return this.dictionary.contains(PdfName.MCID);
/*    */   }
/*    */ 
/*    */   public int getMcid()
/*    */   {
/* 87 */     PdfNumber id = this.dictionary.getAsNumber(PdfName.MCID);
/* 88 */     if (id == null) {
/* 89 */       throw new IllegalStateException("MarkedContentInfo does not contain MCID");
/*    */     }
/* 91 */     return id.intValue();
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.pdf.parser.MarkedContentInfo
 * JD-Core Version:    0.6.2
 */