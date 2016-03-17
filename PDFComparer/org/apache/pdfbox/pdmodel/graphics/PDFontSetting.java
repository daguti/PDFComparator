/*     */ package org.apache.pdfbox.pdmodel.graphics;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import org.apache.pdfbox.cos.COSArray;
/*     */ import org.apache.pdfbox.cos.COSBase;
/*     */ import org.apache.pdfbox.cos.COSDictionary;
/*     */ import org.apache.pdfbox.cos.COSFloat;
/*     */ import org.apache.pdfbox.cos.COSNumber;
/*     */ import org.apache.pdfbox.pdmodel.common.COSObjectable;
/*     */ import org.apache.pdfbox.pdmodel.font.PDFont;
/*     */ import org.apache.pdfbox.pdmodel.font.PDFontFactory;
/*     */ 
/*     */ public class PDFontSetting
/*     */   implements COSObjectable
/*     */ {
/*  41 */   private COSArray fontSetting = null;
/*     */ 
/*     */   public PDFontSetting()
/*     */   {
/*  48 */     this.fontSetting = new COSArray();
/*  49 */     this.fontSetting.add(null);
/*  50 */     this.fontSetting.add(new COSFloat(1.0F));
/*     */   }
/*     */ 
/*     */   public PDFontSetting(COSArray fs)
/*     */   {
/*  60 */     this.fontSetting = fs;
/*     */   }
/*     */ 
/*     */   public COSBase getCOSObject()
/*     */   {
/*  68 */     return this.fontSetting;
/*     */   }
/*     */ 
/*     */   public PDFont getFont()
/*     */     throws IOException
/*     */   {
/*  80 */     PDFont retval = null;
/*  81 */     COSBase font = this.fontSetting.get(0);
/*  82 */     if ((font instanceof COSDictionary))
/*     */     {
/*  84 */       retval = PDFontFactory.createFont((COSDictionary)font);
/*     */     }
/*  86 */     return retval;
/*     */   }
/*     */ 
/*     */   public void setFont(PDFont font)
/*     */   {
/*  96 */     this.fontSetting.set(0, font);
/*     */   }
/*     */ 
/*     */   public float getFontSize()
/*     */   {
/* 106 */     COSNumber size = (COSNumber)this.fontSetting.get(1);
/* 107 */     return size.floatValue();
/*     */   }
/*     */ 
/*     */   public void setFontSize(float size)
/*     */   {
/* 117 */     this.fontSetting.set(1, new COSFloat(size));
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.pdmodel.graphics.PDFontSetting
 * JD-Core Version:    0.6.2
 */