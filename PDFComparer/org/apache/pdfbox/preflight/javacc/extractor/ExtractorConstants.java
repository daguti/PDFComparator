/*    */ package org.apache.pdfbox.preflight.javacc.extractor;
/*    */ 
/*    */ public abstract interface ExtractorConstants
/*    */ {
/*    */   public static final int EOF = 0;
/*    */   public static final int PDF_EOF = 1;
/*    */   public static final int EOL = 2;
/*    */   public static final int CR = 3;
/*    */   public static final int LF = 4;
/*    */   public static final int PERCENT = 5;
/*    */   public static final int PDFA_HEADER = 6;
/*    */   public static final int START_TRAILER = 8;
/*    */   public static final int TRAILER_DICT = 9;
/*    */   public static final int END_TRAILER = 10;
/*    */   public static final int DEFAULT = 0;
/*    */   public static final int WithinTrailer = 1;
/* 38 */   public static final String[] tokenImage = { "<EOF>", "\"%%EOF\"", "<EOL>", "\"\\r\"", "\"\\n\"", "\"%\"", "<PDFA_HEADER>", "<token of kind 7>", "<START_TRAILER>", "<TRAILER_DICT>", "<END_TRAILER>" };
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.preflight.javacc.extractor.ExtractorConstants
 * JD-Core Version:    0.6.2
 */