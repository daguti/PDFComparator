/*    */ package com.itextpdf.text.pdf.parser;
/*    */ 
/*    */ import com.itextpdf.text.pdf.PdfReader;
/*    */ import java.io.IOException;
/*    */ 
/*    */ public final class PdfTextExtractor
/*    */ {
/*    */   public static String getTextFromPage(PdfReader reader, int pageNumber, TextExtractionStrategy strategy)
/*    */     throws IOException
/*    */   {
/* 73 */     PdfReaderContentParser parser = new PdfReaderContentParser(reader);
/* 74 */     return ((TextExtractionStrategy)parser.processContent(pageNumber, strategy)).getResultantText();
/*    */   }
/*    */ 
/*    */   public static String getTextFromPage(PdfReader reader, int pageNumber)
/*    */     throws IOException
/*    */   {
/* 89 */     return getTextFromPage(reader, pageNumber, new LocationTextExtractionStrategy());
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.pdf.parser.PdfTextExtractor
 * JD-Core Version:    0.6.2
 */