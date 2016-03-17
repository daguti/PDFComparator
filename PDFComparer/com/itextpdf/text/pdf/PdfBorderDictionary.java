/*    */ package com.itextpdf.text.pdf;
/*    */ 
/*    */ import com.itextpdf.text.error_messages.MessageLocalization;
/*    */ 
/*    */ public class PdfBorderDictionary extends PdfDictionary
/*    */ {
/*    */   public static final int STYLE_SOLID = 0;
/*    */   public static final int STYLE_DASHED = 1;
/*    */   public static final int STYLE_BEVELED = 2;
/*    */   public static final int STYLE_INSET = 3;
/*    */   public static final int STYLE_UNDERLINE = 4;
/*    */ 
/*    */   public PdfBorderDictionary(float borderWidth, int borderStyle, PdfDashPattern dashes)
/*    */   {
/* 69 */     put(PdfName.W, new PdfNumber(borderWidth));
/* 70 */     switch (borderStyle) {
/*    */     case 0:
/* 72 */       put(PdfName.S, PdfName.S);
/* 73 */       break;
/*    */     case 1:
/* 75 */       if (dashes != null)
/* 76 */         put(PdfName.D, dashes);
/* 77 */       put(PdfName.S, PdfName.D);
/* 78 */       break;
/*    */     case 2:
/* 80 */       put(PdfName.S, PdfName.B);
/* 81 */       break;
/*    */     case 3:
/* 83 */       put(PdfName.S, PdfName.I);
/* 84 */       break;
/*    */     case 4:
/* 86 */       put(PdfName.S, PdfName.U);
/* 87 */       break;
/*    */     default:
/* 89 */       throw new IllegalArgumentException(MessageLocalization.getComposedMessage("invalid.border.style", new Object[0]));
/*    */     }
/*    */   }
/*    */ 
/*    */   public PdfBorderDictionary(float borderWidth, int borderStyle) {
/* 94 */     this(borderWidth, borderStyle, null);
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.pdf.PdfBorderDictionary
 * JD-Core Version:    0.6.2
 */