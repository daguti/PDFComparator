/*    */ package org.apache.pdfbox.encoding;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import org.apache.pdfbox.cos.COSName;
/*    */ 
/*    */ public class EncodingManager
/*    */ {
/* 37 */   public static final EncodingManager INSTANCE = new EncodingManager();
/*    */ 
/*    */   public Encoding getStandardEncoding()
/*    */   {
/* 45 */     return StandardEncoding.INSTANCE;
/*    */   }
/*    */ 
/*    */   public Encoding getEncoding(COSName name)
/*    */     throws IOException
/*    */   {
/* 56 */     if (COSName.STANDARD_ENCODING.equals(name))
/* 57 */       return StandardEncoding.INSTANCE;
/* 58 */     if (COSName.WIN_ANSI_ENCODING.equals(name))
/* 59 */       return WinAnsiEncoding.INSTANCE;
/* 60 */     if (COSName.MAC_ROMAN_ENCODING.equals(name))
/* 61 */       return MacRomanEncoding.INSTANCE;
/* 62 */     if (COSName.PDF_DOC_ENCODING.equals(name)) {
/* 63 */       return PdfDocEncoding.INSTANCE;
/*    */     }
/* 65 */     throw new IOException("Unknown encoding for '" + name.getName() + "'");
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.encoding.EncodingManager
 * JD-Core Version:    0.6.2
 */