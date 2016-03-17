/*    */ package org.apache.pdfbox.encoding;
/*    */ 
/*    */ public class PDFDocEncodingCharset extends SingleByteCharset
/*    */ {
/*    */   public static final String NAME = "PDFDocEncoding";
/* 32 */   public static final PDFDocEncodingCharset INSTANCE = new PDFDocEncodingCharset();
/*    */ 
/*    */   public PDFDocEncodingCharset()
/*    */   {
/* 39 */     super("PDFDocEncoding", null, createEncoding());
/*    */   }
/*    */ 
/*    */   private static char[] createEncoding()
/*    */   {
/* 44 */     char[] encoding = new char[256];
/*    */ 
/* 47 */     for (int i = 0; i < 256; i++)
/*    */     {
/* 49 */       encoding[i] = ((char)i);
/*    */     }
/*    */ 
/* 53 */     encoding[24] = '˘';
/* 54 */     encoding[25] = 'ˇ';
/* 55 */     encoding[26] = 'ˆ';
/* 56 */     encoding[27] = '˙';
/* 57 */     encoding[28] = '˝';
/* 58 */     encoding[29] = '˛';
/* 59 */     encoding[30] = '˚';
/* 60 */     encoding[31] = '˜';
/*    */ 
/* 62 */     encoding[127] = 65533;
/* 63 */     encoding[''] = '•';
/* 64 */     encoding[''] = '†';
/* 65 */     encoding[''] = '‡';
/* 66 */     encoding[''] = '…';
/* 67 */     encoding[''] = '—';
/* 68 */     encoding[''] = '–';
/* 69 */     encoding[''] = 'ƒ';
/* 70 */     encoding[''] = '⁄';
/* 71 */     encoding[''] = '‹';
/* 72 */     encoding[''] = '›';
/* 73 */     encoding[''] = '−';
/* 74 */     encoding[''] = '‰';
/* 75 */     encoding[''] = '„';
/* 76 */     encoding[''] = '“';
/* 77 */     encoding[''] = '”';
/* 78 */     encoding[''] = '‘';
/* 79 */     encoding[''] = '’';
/* 80 */     encoding[''] = '‚';
/* 81 */     encoding[''] = '™';
/* 82 */     encoding[''] = 64257;
/* 83 */     encoding[''] = 64258;
/* 84 */     encoding[''] = 'Ł';
/* 85 */     encoding[''] = 'Œ';
/* 86 */     encoding[''] = 'Š';
/* 87 */     encoding[''] = 'Ÿ';
/* 88 */     encoding[''] = 'Ž';
/* 89 */     encoding[''] = 'ı';
/* 90 */     encoding[''] = 'ł';
/* 91 */     encoding[''] = 'œ';
/* 92 */     encoding[''] = 'š';
/* 93 */     encoding[''] = 'ž';
/* 94 */     encoding[''] = 65533;
/* 95 */     encoding[' '] = '€';
/*    */ 
/* 97 */     return encoding;
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.encoding.PDFDocEncodingCharset
 * JD-Core Version:    0.6.2
 */