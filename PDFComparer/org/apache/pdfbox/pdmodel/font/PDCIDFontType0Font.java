/*    */ package org.apache.pdfbox.pdmodel.font;
/*    */ 
/*    */ import java.awt.Font;
/*    */ import java.io.IOException;
/*    */ import org.apache.pdfbox.cos.COSDictionary;
/*    */ import org.apache.pdfbox.cos.COSName;
/*    */ 
/*    */ public class PDCIDFontType0Font extends PDCIDFont
/*    */ {
/*    */   public PDCIDFontType0Font()
/*    */   {
/* 40 */     this.font.setItem(COSName.SUBTYPE, COSName.CID_FONT_TYPE0);
/*    */   }
/*    */ 
/*    */   public PDCIDFontType0Font(COSDictionary fontDictionary)
/*    */   {
/* 50 */     super(fontDictionary);
/*    */   }
/*    */ 
/*    */   public Font getawtFont()
/*    */     throws IOException
/*    */   {
/* 66 */     PDFontDescriptor fd = getFontDescriptor();
/* 67 */     Font awtFont = null;
/* 68 */     if (fd.getFontName() != null)
/*    */     {
/* 70 */       awtFont = FontManager.getAwtFont(fd.getFontName());
/*    */     }
/* 72 */     if ((awtFont == null) && ((fd instanceof PDFontDescriptorDictionary))) {
/* 73 */       PDFontDescriptorDictionary fdd = (PDFontDescriptorDictionary)fd;
/* 74 */       if (fdd.getFontFile3() != null)
/*    */       {
/* 78 */         awtFont = new PDType1CFont(this.font).getawtFont();
/*    */       }
/*    */     }
/*    */ 
/* 82 */     return awtFont;
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.pdmodel.font.PDCIDFontType0Font
 * JD-Core Version:    0.6.2
 */