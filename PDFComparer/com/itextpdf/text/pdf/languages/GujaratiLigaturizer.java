/*    */ package com.itextpdf.text.pdf.languages;
/*    */ 
/*    */ public class GujaratiLigaturizer extends IndicLigaturizer
/*    */ {
/*    */   public static final char GUJR_MATRA_AA = 'ા';
/*    */   public static final char GUJR_MATRA_I = 'િ';
/*    */   public static final char GUJR_MATRA_E = 'ે';
/*    */   public static final char GUJR_MATRA_AI = 'ૈ';
/*    */   public static final char GUJR_MATRA_HLR = 'ૢ';
/*    */   public static final char GUJR_MATRA_HLRR = 'ૣ';
/*    */   public static final char GUJR_LETTER_A = 'અ';
/*    */   public static final char GUJR_LETTER_AU = 'ઔ';
/*    */   public static final char GUJR_LETTER_KA = 'ક';
/*    */   public static final char GUJR_LETTER_HA = 'હ';
/*    */   public static final char GUJR_HALANTA = '્';
/*    */ 
/*    */   public GujaratiLigaturizer()
/*    */   {
/* 69 */     this.langTable = new char[11];
/* 70 */     this.langTable[0] = 'ા';
/* 71 */     this.langTable[1] = 'િ';
/* 72 */     this.langTable[2] = 'ે';
/* 73 */     this.langTable[3] = 'ૈ';
/* 74 */     this.langTable[4] = 'ૢ';
/* 75 */     this.langTable[5] = 'ૣ';
/* 76 */     this.langTable[6] = 'અ';
/* 77 */     this.langTable[7] = 'ઔ';
/* 78 */     this.langTable[8] = 'ક';
/* 79 */     this.langTable[9] = 'હ';
/* 80 */     this.langTable[10] = '્';
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.pdf.languages.GujaratiLigaturizer
 * JD-Core Version:    0.6.2
 */