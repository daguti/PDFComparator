/*     */ package org.apache.pdfbox.preflight.font.descriptor;
/*     */ 
/*     */ import java.awt.Font;
/*     */ import java.awt.FontFormatException;
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.IOException;
/*     */ import java.util.List;
/*     */ import org.apache.commons.io.IOUtils;
/*     */ import org.apache.fontbox.cff.CFFParser;
/*     */ import org.apache.pdfbox.cos.COSDictionary;
/*     */ import org.apache.pdfbox.cos.COSName;
/*     */ import org.apache.pdfbox.cos.COSStream;
/*     */ import org.apache.pdfbox.pdmodel.common.PDStream;
/*     */ import org.apache.pdfbox.pdmodel.font.PDFont;
/*     */ import org.apache.pdfbox.pdmodel.font.PDFontDescriptorDictionary;
/*     */ import org.apache.pdfbox.preflight.PreflightContext;
/*     */ import org.apache.pdfbox.preflight.ValidationResult.ValidationError;
/*     */ import org.apache.pdfbox.preflight.font.FontValidator;
/*     */ import org.apache.pdfbox.preflight.font.container.Type1Container;
/*     */ import org.apache.pdfbox.preflight.font.util.Type1;
/*     */ import org.apache.pdfbox.preflight.font.util.Type1Parser;
/*     */ 
/*     */ public class Type1DescriptorHelper extends FontDescriptorHelper<Type1Container>
/*     */ {
/*  56 */   private boolean isFontFile1 = true;
/*     */ 
/*     */   public Type1DescriptorHelper(PreflightContext context, PDFont font, Type1Container fontContainer)
/*     */   {
/*  60 */     super(context, font, fontContainer);
/*     */   }
/*     */ 
/*     */   protected boolean checkMandatoryFields(COSDictionary fDescriptor)
/*     */   {
/*  65 */     boolean result = super.checkMandatoryFields(fDescriptor);
/*     */ 
/*  69 */     if (FontValidator.isSubSet(this.fontDescriptor.getFontName()))
/*     */     {
/*  71 */       String charsetStr = this.fontDescriptor.getCharSet();
/*  72 */       if ((charsetStr == null) || ("".equals(charsetStr)))
/*     */       {
/*  74 */         ((Type1Container)this.fContainer).push(new ValidationResult.ValidationError("3.1.4", "The Charset entry is missing for the Type1 Subset"));
/*     */ 
/*  76 */         result = false;
/*     */       }
/*     */     }
/*  79 */     return result;
/*     */   }
/*     */ 
/*     */   public PDStream extractFontFile(PDFontDescriptorDictionary fontDescriptor)
/*     */   {
/*  85 */     PDStream ff1 = fontDescriptor.getFontFile();
/*  86 */     PDStream ff3 = fontDescriptor.getFontFile3();
/*     */ 
/*  88 */     if (ff1 != null)
/*     */     {
/*  90 */       COSStream stream = ff1.getStream();
/*  91 */       if (stream == null)
/*     */       {
/*  93 */         ((Type1Container)this.fContainer).push(new ValidationResult.ValidationError("3.1.3", "The FontFile is missing for " + fontDescriptor.getFontName()));
/*     */ 
/*  95 */         ((Type1Container)this.fContainer).notEmbedded();
/*  96 */         return null;
/*     */       }
/*     */ 
/*  99 */       boolean hasLength1 = stream.getInt(COSName.LENGTH1) > 0;
/* 100 */       boolean hasLength2 = stream.getInt(COSName.getPDFName("Length2")) > 0;
/* 101 */       boolean hasLength3 = stream.getInt(COSName.getPDFName("Length3")) >= 0;
/* 102 */       if ((!hasLength1) || (!hasLength2) || (!hasLength3))
/*     */       {
/* 104 */         ((Type1Container)this.fContainer).push(new ValidationResult.ValidationError("3.1.3", "The FontFile is invalid for " + fontDescriptor.getFontName()));
/*     */ 
/* 106 */         return null;
/*     */       }
/*     */ 
/* 109 */       return ff1;
/*     */     }
/*     */ 
/* 113 */     this.isFontFile1 = false;
/* 114 */     ((Type1Container)this.fContainer).setFontFile1(this.isFontFile1);
/* 115 */     return ff3;
/*     */   }
/*     */ 
/*     */   protected void processFontFile(PDFontDescriptorDictionary fontDescriptor, PDStream fontFile)
/*     */   {
/* 122 */     if (this.isFontFile1)
/*     */     {
/* 124 */       processFontFile1(fontDescriptor, fontFile);
/*     */     }
/*     */     else
/*     */     {
/* 128 */       processFontFile3(fontDescriptor, fontFile);
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void processFontFile1(PDFontDescriptorDictionary fontDescriptor, PDStream fontFile)
/*     */   {
/* 141 */     ByteArrayInputStream bis = null;
/*     */     try
/*     */     {
/* 144 */       bis = new ByteArrayInputStream(fontFile.getByteArray());
/* 145 */       Font.createFont(1, bis);
/* 146 */       IOUtils.closeQuietly(bis);
/*     */ 
/* 149 */       COSStream streamObj = fontFile.getStream();
/* 150 */       int length1 = streamObj.getInt(COSName.LENGTH1);
/* 151 */       int length2 = streamObj.getInt(COSName.LENGTH2);
/* 152 */       bis = new ByteArrayInputStream(fontFile.getByteArray());
/* 153 */       Type1Parser parserForMetrics = Type1Parser.createParserWithEncodingObject(bis, length1, length2, this.font.getFontEncoding());
/*     */ 
/* 155 */       Type1 parsedData = parserForMetrics.parse();
/*     */ 
/* 157 */       ((Type1Container)this.fContainer).setType1Font(parsedData);
/*     */     }
/*     */     catch (IOException e)
/*     */     {
/* 162 */       ((Type1Container)this.fContainer).push(new ValidationResult.ValidationError("3.2.1", "The FontFile can't be read"));
/*     */     }
/*     */     catch (FontFormatException e)
/*     */     {
/* 166 */       ((Type1Container)this.fContainer).push(new ValidationResult.ValidationError("3.2.1", "The FontFile is damaged"));
/*     */     }
/*     */     finally
/*     */     {
/* 170 */       IOUtils.closeQuietly(bis);
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void processFontFile3(PDFontDescriptorDictionary fontDescriptor, PDStream fontFile)
/*     */   {
/*     */     try
/*     */     {
/* 185 */       CFFParser cffParser = new CFFParser();
/* 186 */       List lCFonts = cffParser.parse(fontFile.getByteArray());
/* 187 */       if ((lCFonts == null) || (lCFonts.isEmpty()))
/*     */       {
/* 189 */         ((Type1Container)this.fContainer).push(new ValidationResult.ValidationError("3.2.3", "The FontFile can't be read"));
/*     */       }
/* 191 */       ((Type1Container)this.fContainer).setCFFFontObjects(lCFonts);
/*     */     }
/*     */     catch (IOException e)
/*     */     {
/* 195 */       ((Type1Container)this.fContainer).push(new ValidationResult.ValidationError("3.2.3", "The FontFile can't be read"));
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.preflight.font.descriptor.Type1DescriptorHelper
 * JD-Core Version:    0.6.2
 */