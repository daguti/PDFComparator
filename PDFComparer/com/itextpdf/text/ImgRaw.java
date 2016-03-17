/*    */ package com.itextpdf.text;
/*    */ 
/*    */ import com.itextpdf.text.error_messages.MessageLocalization;
/*    */ import java.net.URL;
/*    */ 
/*    */ public class ImgRaw extends Image
/*    */ {
/*    */   ImgRaw(Image image)
/*    */   {
/* 62 */     super(image);
/*    */   }
/*    */ 
/*    */   public ImgRaw(int width, int height, int components, int bpc, byte[] data)
/*    */     throws BadElementException
/*    */   {
/* 76 */     super((URL)null);
/* 77 */     this.type = 34;
/* 78 */     this.scaledHeight = height;
/* 79 */     setTop(this.scaledHeight);
/* 80 */     this.scaledWidth = width;
/* 81 */     setRight(this.scaledWidth);
/* 82 */     if ((components != 1) && (components != 3) && (components != 4))
/* 83 */       throw new BadElementException(MessageLocalization.getComposedMessage("components.must.be.1.3.or.4", new Object[0]));
/* 84 */     if ((bpc != 1) && (bpc != 2) && (bpc != 4) && (bpc != 8))
/* 85 */       throw new BadElementException(MessageLocalization.getComposedMessage("bits.per.component.must.be.1.2.4.or.8", new Object[0]));
/* 86 */     this.colorspace = components;
/* 87 */     this.bpc = bpc;
/* 88 */     this.rawData = data;
/* 89 */     this.plainWidth = getWidth();
/* 90 */     this.plainHeight = getHeight();
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.ImgRaw
 * JD-Core Version:    0.6.2
 */