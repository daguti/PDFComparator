/*    */ package com.itextpdf.text.pdf;
/*    */ 
/*    */ import com.itextpdf.text.ExceptionConverter;
/*    */ import com.itextpdf.text.error_messages.MessageLocalization;
/*    */ 
/*    */ public class PdfICCBased extends PdfStream
/*    */ {
/*    */   public PdfICCBased(ICC_Profile profile)
/*    */   {
/* 64 */     this(profile, -1);
/*    */   }
/*    */ 
/*    */   public PdfICCBased(ICC_Profile profile, int compressionLevel)
/*    */   {
/*    */     try
/*    */     {
/* 78 */       int numberOfComponents = profile.getNumComponents();
/* 79 */       switch (numberOfComponents) {
/*    */       case 1:
/* 81 */         put(PdfName.ALTERNATE, PdfName.DEVICEGRAY);
/* 82 */         break;
/*    */       case 3:
/* 84 */         put(PdfName.ALTERNATE, PdfName.DEVICERGB);
/* 85 */         break;
/*    */       case 4:
/* 87 */         put(PdfName.ALTERNATE, PdfName.DEVICECMYK);
/* 88 */         break;
/*    */       case 2:
/*    */       default:
/* 90 */         throw new PdfException(MessageLocalization.getComposedMessage("1.component.s.is.not.supported", numberOfComponents));
/*    */       }
/* 92 */       put(PdfName.N, new PdfNumber(numberOfComponents));
/* 93 */       this.bytes = profile.getData();
/* 94 */       put(PdfName.LENGTH, new PdfNumber(this.bytes.length));
/* 95 */       flateCompress(compressionLevel);
/*    */     } catch (Exception e) {
/* 97 */       throw new ExceptionConverter(e);
/*    */     }
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.pdf.PdfICCBased
 * JD-Core Version:    0.6.2
 */