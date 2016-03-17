/*    */ package org.apache.pdfbox.preflight.utils;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import org.apache.pdfbox.cos.COSDictionary;
/*    */ import org.apache.pdfbox.cos.COSDocument;
/*    */ import org.apache.pdfbox.pdfparser.BaseParser;
/*    */ 
/*    */ public class PdfElementParser extends BaseParser
/*    */ {
/*    */   public PdfElementParser(COSDocument cosDocument, byte[] input)
/*    */     throws IOException
/*    */   {
/* 47 */     super(input);
/* 48 */     this.document = cosDocument;
/*    */   }
/*    */ 
/*    */   public COSDictionary parseAsDictionary()
/*    */     throws IOException
/*    */   {
/* 60 */     return parseCOSDictionary();
/*    */   }
/*    */ 
/*    */   public COSDocument getDocument()
/*    */   {
/* 70 */     return this.document;
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.preflight.utils.PdfElementParser
 * JD-Core Version:    0.6.2
 */