/*    */ package com.itextpdf.text.pdf.parser;
/*    */ 
/*    */ import com.itextpdf.text.pdf.PdfDictionary;
/*    */ 
/*    */ public class InlineImageInfo
/*    */ {
/*    */   private final byte[] samples;
/*    */   private final PdfDictionary imageDictionary;
/*    */ 
/*    */   public InlineImageInfo(byte[] samples, PdfDictionary imageDictionary)
/*    */   {
/* 58 */     this.samples = samples;
/* 59 */     this.imageDictionary = imageDictionary;
/*    */   }
/*    */ 
/*    */   public PdfDictionary getImageDictionary()
/*    */   {
/* 66 */     return this.imageDictionary;
/*    */   }
/*    */ 
/*    */   public byte[] getSamples()
/*    */   {
/* 73 */     return this.samples;
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.pdf.parser.InlineImageInfo
 * JD-Core Version:    0.6.2
 */