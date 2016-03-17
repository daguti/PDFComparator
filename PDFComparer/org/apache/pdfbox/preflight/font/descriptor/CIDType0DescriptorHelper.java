/*     */ package org.apache.pdfbox.preflight.font.descriptor;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.util.List;
/*     */ import org.apache.fontbox.cff.CFFParser;
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
/*     */ import org.apache.pdfbox.preflight.font.container.CIDType0Container;
/*     */ import org.apache.pdfbox.preflight.utils.COSUtils;
/*     */ 
/*     */ public class CIDType0DescriptorHelper extends FontDescriptorHelper<CIDType0Container>
/*     */ {
/*     */   public CIDType0DescriptorHelper(PreflightContext context, PDFont font, CIDType0Container fontContainer)
/*     */   {
/*  55 */     super(context, font, fontContainer);
/*     */   }
/*     */ 
/*     */   public PDStream extractFontFile(PDFontDescriptorDictionary fontDescriptor)
/*     */   {
/*  61 */     PDStream ff3 = fontDescriptor.getFontFile3();
/*  62 */     if (ff3 != null)
/*     */     {
/*  67 */       COSStream stream = ff3.getStream();
/*  68 */       if (stream == null)
/*     */       {
/*  70 */         ((CIDType0Container)this.fContainer).push(new ValidationResult.ValidationError("3.1.3", "The FontFile is missing for " + fontDescriptor.getFontName()));
/*     */ 
/*  72 */         ((CIDType0Container)this.fContainer).notEmbedded();
/*     */       }
/*     */       else
/*     */       {
/*  80 */         String st = stream.getNameAsString(COSName.SUBTYPE);
/*  81 */         if ((!"CIDFontType0C".equals(st)) && (!"Type1C".equals(st)))
/*     */         {
/*  83 */           ((CIDType0Container)this.fContainer).push(new ValidationResult.ValidationError("3.1.3", "The FontFile3 stream doesn't have the right Subtype for " + fontDescriptor.getFontName()));
/*     */         }
/*     */ 
/*  87 */         checkCIDSet(fontDescriptor);
/*     */       }
/*     */     }
/*  90 */     return ff3;
/*     */   }
/*     */ 
/*     */   protected void checkCIDSet(PDFontDescriptorDictionary pfDescriptor)
/*     */   {
/* 101 */     if (FontValidator.isSubSet(pfDescriptor.getFontName()))
/*     */     {
/* 103 */       COSDocument cosDocument = this.context.getDocument().getDocument();
/* 104 */       COSBase cidset = pfDescriptor.getCOSDictionary().getItem(COSName.getPDFName("CIDSet"));
/* 105 */       if ((cidset == null) || (!COSUtils.isStream(cidset, cosDocument)))
/*     */       {
/* 107 */         ((CIDType0Container)this.fContainer).push(new ValidationResult.ValidationError("3.1.11", "The CIDSet entry is missing for the Composite Subset"));
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void processFontFile(PDFontDescriptorDictionary fontDescriptor, PDStream fontFile)
/*     */   {
/*     */     try
/*     */     {
/* 121 */       CFFParser cffParser = new CFFParser();
/* 122 */       List lCFonts = cffParser.parse(fontFile.getByteArray());
/* 123 */       if ((lCFonts == null) || (lCFonts.isEmpty()))
/*     */       {
/* 125 */         ((CIDType0Container)this.fContainer).push(new ValidationResult.ValidationError("3.2.3", "The FontFile can't be read"));
/*     */       }
/* 127 */       ((CIDType0Container)this.fContainer).setlCFonts(lCFonts);
/*     */     }
/*     */     catch (IOException e)
/*     */     {
/* 131 */       ((CIDType0Container)this.fContainer).push(new ValidationResult.ValidationError("3.2.3", "The FontFile can't be read"));
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.preflight.font.descriptor.CIDType0DescriptorHelper
 * JD-Core Version:    0.6.2
 */