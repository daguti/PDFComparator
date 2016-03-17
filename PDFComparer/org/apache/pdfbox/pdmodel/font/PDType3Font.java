/*    */ package org.apache.pdfbox.pdmodel.font;
/*    */ 
/*    */ import java.awt.Graphics;
/*    */ import java.awt.geom.AffineTransform;
/*    */ import java.io.IOException;
/*    */ import org.apache.commons.logging.Log;
/*    */ import org.apache.commons.logging.LogFactory;
/*    */ import org.apache.pdfbox.cos.COSDictionary;
/*    */ import org.apache.pdfbox.cos.COSName;
/*    */ import org.apache.pdfbox.pdmodel.common.PDMatrix;
/*    */ 
/*    */ public class PDType3Font extends PDSimpleFont
/*    */ {
/* 40 */   private static final Log LOG = LogFactory.getLog(PDType3Font.class);
/*    */ 
/*    */   public PDType3Font()
/*    */   {
/* 48 */     this.font.setItem(COSName.SUBTYPE, COSName.TYPE3);
/*    */   }
/*    */ 
/*    */   public PDType3Font(COSDictionary fontDictionary)
/*    */   {
/* 58 */     super(fontDictionary);
/*    */   }
/*    */ 
/*    */   public void drawString(String string, int[] codePoints, Graphics g, float fontSize, AffineTransform at, float x, float y)
/*    */     throws IOException
/*    */   {
/* 67 */     LOG.info("Rendering of type3 fonts isn't supported in PDFBox 1.8.x. It will be available in the 2.0 version!");
/*    */   }
/*    */ 
/*    */   public void setFontMatrix(PDMatrix matrix)
/*    */   {
/* 77 */     this.font.setItem(COSName.FONT_MATRIX, matrix);
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.pdmodel.font.PDType3Font
 * JD-Core Version:    0.6.2
 */