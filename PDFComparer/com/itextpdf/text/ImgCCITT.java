/*    */ package com.itextpdf.text;
/*    */ 
/*    */ import com.itextpdf.text.error_messages.MessageLocalization;
/*    */ import com.itextpdf.text.pdf.codec.TIFFFaxDecoder;
/*    */ import java.net.URL;
/*    */ 
/*    */ public class ImgCCITT extends Image
/*    */ {
/*    */   ImgCCITT(Image image)
/*    */   {
/* 63 */     super(image);
/*    */   }
/*    */ 
/*    */   public ImgCCITT(int width, int height, boolean reverseBits, int typeCCITT, int parameters, byte[] data)
/*    */     throws BadElementException
/*    */   {
/* 82 */     super((URL)null);
/* 83 */     if ((typeCCITT != 256) && (typeCCITT != 257) && (typeCCITT != 258))
/* 84 */       throw new BadElementException(MessageLocalization.getComposedMessage("the.ccitt.compression.type.must.be.ccittg4.ccittg3.1d.or.ccittg3.2d", new Object[0]));
/* 85 */     if (reverseBits)
/* 86 */       TIFFFaxDecoder.reverseBits(data);
/* 87 */     this.type = 34;
/* 88 */     this.scaledHeight = height;
/* 89 */     setTop(this.scaledHeight);
/* 90 */     this.scaledWidth = width;
/* 91 */     setRight(this.scaledWidth);
/* 92 */     this.colorspace = parameters;
/* 93 */     this.bpc = typeCCITT;
/* 94 */     this.rawData = data;
/* 95 */     this.plainWidth = getWidth();
/* 96 */     this.plainHeight = getHeight();
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.ImgCCITT
 * JD-Core Version:    0.6.2
 */