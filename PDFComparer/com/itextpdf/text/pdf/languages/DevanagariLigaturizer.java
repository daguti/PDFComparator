/*    */ package com.itextpdf.text.pdf.languages;
/*    */ 
/*    */ public class DevanagariLigaturizer extends IndicLigaturizer
/*    */ {
/*    */   public static final char DEVA_MATRA_AA = 'ा';
/*    */   public static final char DEVA_MATRA_I = 'ि';
/*    */   public static final char DEVA_MATRA_E = 'े';
/*    */   public static final char DEVA_MATRA_AI = 'ै';
/*    */   public static final char DEVA_MATRA_HLR = 'ॢ';
/*    */   public static final char DEVA_MATRA_HLRR = 'ॣ';
/*    */   public static final char DEVA_LETTER_A = 'अ';
/*    */   public static final char DEVA_LETTER_AU = 'औ';
/*    */   public static final char DEVA_LETTER_KA = 'क';
/*    */   public static final char DEVA_LETTER_HA = 'ह';
/*    */   public static final char DEVA_HALANTA = '्';
/*    */ 
/*    */   public DevanagariLigaturizer()
/*    */   {
/* 69 */     this.langTable = new char[11];
/* 70 */     this.langTable[0] = 'ा';
/* 71 */     this.langTable[1] = 'ि';
/* 72 */     this.langTable[2] = 'े';
/* 73 */     this.langTable[3] = 'ै';
/* 74 */     this.langTable[4] = 'ॢ';
/* 75 */     this.langTable[5] = 'ॣ';
/* 76 */     this.langTable[6] = 'अ';
/* 77 */     this.langTable[7] = 'औ';
/* 78 */     this.langTable[8] = 'क';
/* 79 */     this.langTable[9] = 'ह';
/* 80 */     this.langTable[10] = '्';
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.pdf.languages.DevanagariLigaturizer
 * JD-Core Version:    0.6.2
 */