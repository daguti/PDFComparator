/*    */ package com.itextpdf.text.pdf.parser;
/*    */ 
/*    */ import com.itextpdf.text.pdf.PdfDictionary;
/*    */ import com.itextpdf.text.pdf.PdfName;
/*    */ import com.itextpdf.text.pdf.PdfReader;
/*    */ import java.io.IOException;
/*    */ 
/*    */ public class PdfReaderContentParser
/*    */ {
/*    */   private final PdfReader reader;
/*    */ 
/*    */   public PdfReaderContentParser(PdfReader reader)
/*    */   {
/* 63 */     this.reader = reader;
/*    */   }
/*    */ 
/*    */   public <E extends RenderListener> E processContent(int pageNumber, E renderListener)
/*    */     throws IOException
/*    */   {
/* 76 */     PdfDictionary pageDic = this.reader.getPageN(pageNumber);
/* 77 */     PdfDictionary resourcesDic = pageDic.getAsDict(PdfName.RESOURCES);
/*    */ 
/* 79 */     PdfContentStreamProcessor processor = new PdfContentStreamProcessor(renderListener);
/* 80 */     processor.processContent(ContentByteUtils.getContentBytesForPage(this.reader, pageNumber), resourcesDic);
/* 81 */     return renderListener;
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.pdf.parser.PdfReaderContentParser
 * JD-Core Version:    0.6.2
 */