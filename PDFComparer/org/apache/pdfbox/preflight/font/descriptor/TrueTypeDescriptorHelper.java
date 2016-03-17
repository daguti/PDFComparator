/*    */ package org.apache.pdfbox.preflight.font.descriptor;
/*    */ 
/*    */ import java.io.ByteArrayInputStream;
/*    */ import java.io.IOException;
/*    */ import org.apache.commons.io.IOUtils;
/*    */ import org.apache.fontbox.ttf.CMAPTable;
/*    */ import org.apache.fontbox.ttf.TTFParser;
/*    */ import org.apache.fontbox.ttf.TrueTypeFont;
/*    */ import org.apache.pdfbox.cos.COSName;
/*    */ import org.apache.pdfbox.cos.COSStream;
/*    */ import org.apache.pdfbox.pdmodel.common.PDStream;
/*    */ import org.apache.pdfbox.pdmodel.font.PDFont;
/*    */ import org.apache.pdfbox.pdmodel.font.PDFontDescriptorDictionary;
/*    */ import org.apache.pdfbox.preflight.PreflightContext;
/*    */ import org.apache.pdfbox.preflight.ValidationResult.ValidationError;
/*    */ import org.apache.pdfbox.preflight.font.container.TrueTypeContainer;
/*    */ 
/*    */ public class TrueTypeDescriptorHelper extends FontDescriptorHelper<TrueTypeContainer>
/*    */ {
/*    */   public TrueTypeDescriptorHelper(PreflightContext context, PDFont font, TrueTypeContainer fontContainer)
/*    */   {
/* 48 */     super(context, font, fontContainer);
/*    */   }
/*    */ 
/*    */   public PDStream extractFontFile(PDFontDescriptorDictionary fontDescriptor)
/*    */   {
/* 53 */     PDStream fontFile = fontDescriptor.getFontFile2();
/* 54 */     COSStream stream = fontFile == null ? null : fontFile.getStream();
/* 55 */     if (stream == null)
/*    */     {
/* 57 */       ((TrueTypeContainer)this.fContainer).push(new ValidationResult.ValidationError("3.1.3", "The FontFile2 is missing for " + fontDescriptor.getFontName()));
/*    */ 
/* 59 */       ((TrueTypeContainer)this.fContainer).notEmbedded();
/* 60 */       return null;
/*    */     }
/*    */ 
/* 63 */     if (stream.getInt(COSName.LENGTH1) <= 0)
/*    */     {
/* 65 */       ((TrueTypeContainer)this.fContainer).push(new ValidationResult.ValidationError("3.1.3", "The FontFile entry /Length1 is invalid for " + fontDescriptor.getFontName()));
/*    */ 
/* 67 */       return null;
/*    */     }
/*    */ 
/* 70 */     return fontFile;
/*    */   }
/*    */ 
/*    */   protected void processFontFile(PDFontDescriptorDictionary fontDescriptor, PDStream fontFile)
/*    */   {
/* 79 */     ByteArrayInputStream bis = null;
/*    */     try
/*    */     {
/* 83 */       bis = new ByteArrayInputStream(fontFile.getByteArray());
/* 84 */       TrueTypeFont ttf = new TTFParser(true).parseTTF(bis);
/*    */ 
/* 86 */       if ((fontDescriptor.isSymbolic()) && (ttf.getCMAP().getCmaps().length != 1))
/*    */       {
/* 88 */         ((TrueTypeContainer)this.fContainer).push(new ValidationResult.ValidationError("3.1.5", "The Encoding should be missing for the Symbolic TTF"));
/*    */       }
/*    */       else
/*    */       {
/* 93 */         ((TrueTypeContainer)this.fContainer).setTrueTypeFont(ttf);
/*    */       }
/*    */ 
/*    */     }
/*    */     catch (IOException e)
/*    */     {
/* 99 */       ((TrueTypeContainer)this.fContainer).push(new ValidationResult.ValidationError("3.2.2", "The FontFile can't be read for " + this.font.getBaseFont()));
/*    */     }
/*    */     finally
/*    */     {
/* 104 */       IOUtils.closeQuietly(bis);
/*    */     }
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.preflight.font.descriptor.TrueTypeDescriptorHelper
 * JD-Core Version:    0.6.2
 */