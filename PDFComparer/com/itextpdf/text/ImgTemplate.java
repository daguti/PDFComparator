/*    */ package com.itextpdf.text;
/*    */ 
/*    */ import com.itextpdf.text.error_messages.MessageLocalization;
/*    */ import com.itextpdf.text.pdf.PdfTemplate;
/*    */ import java.net.URL;
/*    */ 
/*    */ public class ImgTemplate extends Image
/*    */ {
/*    */   ImgTemplate(Image image)
/*    */   {
/* 64 */     super(image);
/*    */   }
/*    */ 
/*    */   public ImgTemplate(PdfTemplate template)
/*    */     throws BadElementException
/*    */   {
/* 73 */     super((URL)null);
/* 74 */     if (template == null)
/* 75 */       throw new BadElementException(MessageLocalization.getComposedMessage("the.template.can.not.be.null", new Object[0]));
/* 76 */     if (template.getType() == 3)
/* 77 */       throw new BadElementException(MessageLocalization.getComposedMessage("a.pattern.can.not.be.used.as.a.template.to.create.an.image", new Object[0]));
/* 78 */     this.type = 35;
/* 79 */     this.scaledHeight = template.getHeight();
/* 80 */     setTop(this.scaledHeight);
/* 81 */     this.scaledWidth = template.getWidth();
/* 82 */     setRight(this.scaledWidth);
/* 83 */     setTemplateData(template);
/* 84 */     this.plainWidth = getWidth();
/* 85 */     this.plainHeight = getHeight();
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.ImgTemplate
 * JD-Core Version:    0.6.2
 */