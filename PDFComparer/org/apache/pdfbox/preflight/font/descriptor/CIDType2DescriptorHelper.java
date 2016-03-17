/*     */ package org.apache.pdfbox.preflight.font.descriptor;
/*     */ 
/*     */ import java.io.ByteArrayInputStream;
/*     */ import org.apache.fontbox.ttf.CIDFontType2Parser;
/*     */ import org.apache.fontbox.ttf.TrueTypeFont;
/*     */ import org.apache.pdfbox.cos.COSBase;
/*     */ import org.apache.pdfbox.cos.COSDictionary;
/*     */ import org.apache.pdfbox.cos.COSDocument;
/*     */ import org.apache.pdfbox.cos.COSName;
/*     */ import org.apache.pdfbox.cos.COSStream;
/*     */ import org.apache.pdfbox.pdmodel.common.PDStream;
/*     */ import org.apache.pdfbox.pdmodel.font.PDFont;
/*     */ import org.apache.pdfbox.pdmodel.font.PDFontDescriptorDictionary;
/*     */ import org.apache.pdfbox.preflight.PreflightContext;
/*     */ import org.apache.pdfbox.preflight.PreflightDocument;
/*     */ import org.apache.pdfbox.preflight.ValidationResult.ValidationError;
/*     */ import org.apache.pdfbox.preflight.font.FontValidator;
/*     */ import org.apache.pdfbox.preflight.font.container.CIDType2Container;
/*     */ import org.apache.pdfbox.preflight.utils.COSUtils;
/*     */ 
/*     */ public class CIDType2DescriptorHelper extends FontDescriptorHelper<CIDType2Container>
/*     */ {
/*     */   public CIDType2DescriptorHelper(PreflightContext context, PDFont font, CIDType2Container fontContainer)
/*     */   {
/*  52 */     super(context, font, fontContainer);
/*     */   }
/*     */ 
/*     */   protected void checkCIDSet(PDFontDescriptorDictionary pfDescriptor)
/*     */   {
/*  63 */     if (FontValidator.isSubSet(pfDescriptor.getFontName()))
/*     */     {
/*  65 */       COSDocument cosDocument = this.context.getDocument().getDocument();
/*  66 */       COSBase cidset = pfDescriptor.getCOSDictionary().getItem(COSName.getPDFName("CIDSet"));
/*  67 */       if ((cidset == null) || (!COSUtils.isStream(cidset, cosDocument)))
/*     */       {
/*  69 */         ((CIDType2Container)this.fContainer).push(new ValidationResult.ValidationError("3.1.11", "The CIDSet entry is missing for the Composite Subset"));
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   public PDStream extractFontFile(PDFontDescriptorDictionary fontDescriptor)
/*     */   {
/*  78 */     PDStream ff2 = fontDescriptor.getFontFile2();
/*  79 */     if (ff2 != null)
/*     */     {
/*  84 */       COSStream stream = ff2.getStream();
/*  85 */       if (stream == null)
/*     */       {
/*  87 */         ((CIDType2Container)this.fContainer).push(new ValidationResult.ValidationError("3.1.3", "The FontFile is missing for " + fontDescriptor.getFontName()));
/*     */ 
/*  89 */         ((CIDType2Container)this.fContainer).notEmbedded();
/*     */       }
/*     */     }
/*  92 */     checkCIDSet(fontDescriptor);
/*  93 */     return ff2;
/*     */   }
/*     */ 
/*     */   protected void processFontFile(PDFontDescriptorDictionary fontDescriptor, PDStream fontFile)
/*     */   {
/* 102 */     TrueTypeFont ttf = null;
/*     */     try
/*     */     {
/* 109 */       ttf = new CIDFontType2Parser(true).parseTTF(new ByteArrayInputStream(fontFile.getByteArray()));
/* 110 */       ((CIDType2Container)this.fContainer).setTtf(ttf);
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 117 */       ((CIDType2Container)this.fContainer).push(new ValidationResult.ValidationError("3.2.3", "The FontFile can't be read"));
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.preflight.font.descriptor.CIDType2DescriptorHelper
 * JD-Core Version:    0.6.2
 */