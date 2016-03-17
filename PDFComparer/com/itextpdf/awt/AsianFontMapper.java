/*    */ package com.itextpdf.awt;
/*    */ 
/*    */ import com.itextpdf.text.pdf.BaseFont;
/*    */ import java.awt.Font;
/*    */ 
/*    */ public class AsianFontMapper extends DefaultFontMapper
/*    */ {
/*    */   public static final String ChineseSimplifiedFont = "STSong-Light";
/*    */   public static final String ChineseSimplifiedEncoding_H = "UniGB-UCS2-H";
/*    */   public static final String ChineseSimplifiedEncoding_V = "UniGB-UCS2-V";
/*    */   public static final String ChineseTraditionalFont_MHei = "MHei-Medium";
/*    */   public static final String ChineseTraditionalFont_MSung = "MSung-Light";
/*    */   public static final String ChineseTraditionalEncoding_H = "UniCNS-UCS2-H";
/*    */   public static final String ChineseTraditionalEncoding_V = "UniCNS-UCS2-V";
/*    */   public static final String JapaneseFont_Go = "HeiseiKakuGo-W5";
/*    */   public static final String JapaneseFont_Min = "HeiseiMin-W3";
/*    */   public static final String JapaneseEncoding_H = "UniJIS-UCS2-H";
/*    */   public static final String JapaneseEncoding_V = "UniJIS-UCS2-V";
/*    */   public static final String JapaneseEncoding_HW_H = "UniJIS-UCS2-HW-H";
/*    */   public static final String JapaneseEncoding_HW_V = "UniJIS-UCS2-HW-V";
/*    */   public static final String KoreanFont_GoThic = "HYGoThic-Medium";
/*    */   public static final String KoreanFont_SMyeongJo = "HYSMyeongJo-Medium";
/*    */   public static final String KoreanEncoding_H = "UniKS-UCS2-H";
/*    */   public static final String KoreanEncoding_V = "UniKS-UCS2-V";
/*    */   private final String defaultFont;
/*    */   private final String encoding;
/*    */ 
/*    */   public AsianFontMapper(String font, String encoding)
/*    */   {
/* 80 */     this.defaultFont = font;
/* 81 */     this.encoding = encoding;
/*    */   }
/*    */ 
/*    */   public BaseFont awtToPdf(Font font) {
/*    */     try {
/* 86 */       DefaultFontMapper.BaseFontParameters p = getBaseFontParameters(font.getFontName());
/* 87 */       if (p != null) {
/* 88 */         return BaseFont.createFont(p.fontName, p.encoding, p.embedded, p.cached, p.ttfAfm, p.pfb);
/*    */       }
/* 90 */       return BaseFont.createFont(this.defaultFont, this.encoding, true);
/*    */     }
/*    */     catch (Exception e)
/*    */     {
/* 94 */       e.printStackTrace();
/*    */     }
/* 96 */     return null;
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.awt.AsianFontMapper
 * JD-Core Version:    0.6.2
 */