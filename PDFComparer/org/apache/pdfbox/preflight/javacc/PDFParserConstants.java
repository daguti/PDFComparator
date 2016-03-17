/*     */ package org.apache.pdfbox.preflight.javacc;
/*     */ 
/*     */ public abstract interface PDFParserConstants
/*     */ {
/*     */   public static final int EOF = 0;
/*     */   public static final int SPACE = 1;
/*     */   public static final int OTHER_WHITE_SPACE = 2;
/*     */   public static final int EOL = 3;
/*     */   public static final int PERCENT = 4;
/*     */   public static final int PDFA_HEADER = 5;
/*     */   public static final int BINARY_TAG = 6;
/*     */   public static final int HTML_OPEN = 7;
/*     */   public static final int HTML_CLOSE = 8;
/*     */   public static final int END_OBJECT = 9;
/*     */   public static final int STREAM = 10;
/*     */   public static final int OBJ_BOOLEAN = 11;
/*     */   public static final int OBJ_NUMERIC = 12;
/*     */   public static final int OBJ_STRING_HEX = 13;
/*     */   public static final int OBJ_STRING_LIT = 14;
/*     */   public static final int OBJ_ARRAY_START = 15;
/*     */   public static final int OBJ_ARRAY_END = 16;
/*     */   public static final int OBJ_NAME = 17;
/*     */   public static final int OBJ_NULL = 18;
/*     */   public static final int OBJ_REF = 19;
/*     */   public static final int START_OBJECT = 20;
/*     */   public static final int DIGITS = 21;
/*     */   public static final int LOWERLETTER = 22;
/*     */   public static final int UPPERLETTER = 23;
/*     */   public static final int UNICODE = 25;
/*     */   public static final int UNBALANCED_LEFT_PARENTHESES = 26;
/*     */   public static final int UNBALANCED_RIGHT_PARENTHESES = 27;
/*     */   public static final int END_LITERAL = 28;
/*     */   public static final int INNER_START_LIT = 29;
/*     */   public static final int END_STREAM = 31;
/*     */   public static final int XREF_TAG = 32;
/*     */   public static final int FULL_LINE = 33;
/*     */   public static final int SUBSECTION_START = 34;
/*     */   public static final int SUBSECTION_ENTRIES = 35;
/*     */   public static final int FIRST_OBJECT_NUMBER = 36;
/*     */   public static final int TRAILER_TAG = 37;
/*     */   public static final int START_DICTONNARY = 38;
/*     */   public static final int END_DICTONNARY = 39;
/*     */   public static final int STARTXREF_TAG = 40;
/*     */   public static final int OBJ_NUMBER = 41;
/*     */   public static final int EOF_TRAILER_TAG = 42;
/*     */   public static final int DEFAULT = 0;
/*     */   public static final int WithinTrailer = 1;
/*     */   public static final int CrossRefTable = 2;
/*     */   public static final int WithinLIT = 3;
/*     */   public static final int WithinStream = 4;
/* 106 */   public static final String[] tokenImage = { "<EOF>", "\" \"", "<OTHER_WHITE_SPACE>", "<EOL>", "\"%\"", "<PDFA_HEADER>", "<BINARY_TAG>", "<HTML_OPEN>", "<HTML_CLOSE>", "<END_OBJECT>", "<STREAM>", "<OBJ_BOOLEAN>", "<OBJ_NUMERIC>", "<OBJ_STRING_HEX>", "<OBJ_STRING_LIT>", "\"[\"", "\"]\"", "<OBJ_NAME>", "\"null\"", "<OBJ_REF>", "<START_OBJECT>", "<DIGITS>", "<LOWERLETTER>", "<UPPERLETTER>", "<token of kind 24>", "<UNICODE>", "\"\\\\(\"", "\"\\\\)\"", "\")\"", "<INNER_START_LIT>", "<token of kind 30>", "\"endstream\"", "\"xref\"", "<FULL_LINE>", "<SUBSECTION_START>", "<SUBSECTION_ENTRIES>", "<FIRST_OBJECT_NUMBER>", "\"trailer\"", "\"<<\"", "\">>\"", "\"startxref\"", "<OBJ_NUMBER>", "\"%%EOF\"" };
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.preflight.javacc.PDFParserConstants
 * JD-Core Version:    0.6.2
 */