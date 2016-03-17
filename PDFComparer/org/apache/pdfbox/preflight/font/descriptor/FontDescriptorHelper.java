/*     */ package org.apache.pdfbox.preflight.font.descriptor;
/*     */ 
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import org.apache.commons.io.IOUtils;
/*     */ import org.apache.pdfbox.cos.COSDictionary;
/*     */ import org.apache.pdfbox.cos.COSName;
/*     */ import org.apache.pdfbox.pdmodel.common.PDMetadata;
/*     */ import org.apache.pdfbox.pdmodel.common.PDStream;
/*     */ import org.apache.pdfbox.pdmodel.font.PDFont;
/*     */ import org.apache.pdfbox.pdmodel.font.PDFontDescriptor;
/*     */ import org.apache.pdfbox.pdmodel.font.PDFontDescriptorDictionary;
/*     */ import org.apache.pdfbox.preflight.PreflightContext;
/*     */ import org.apache.pdfbox.preflight.ValidationResult.ValidationError;
/*     */ import org.apache.pdfbox.preflight.font.container.FontContainer;
/*     */ import org.apache.pdfbox.preflight.font.util.FontMetaDataValidation;
/*     */ import org.apache.xmpbox.XMPMetadata;
/*     */ import org.apache.xmpbox.xml.DomXmpParser;
/*     */ import org.apache.xmpbox.xml.XmpParsingException;
/*     */ import org.apache.xmpbox.xml.XmpParsingException.ErrorType;
/*     */ 
/*     */ public abstract class FontDescriptorHelper<T extends FontContainer>
/*     */ {
/*     */   protected T fContainer;
/*     */   protected PreflightContext context;
/*     */   protected PDFont font;
/*     */   protected PDFontDescriptorDictionary fontDescriptor;
/*     */ 
/*     */   public FontDescriptorHelper(PreflightContext context, PDFont font, T fontContainer)
/*     */   {
/*  77 */     this.fContainer = fontContainer;
/*  78 */     this.context = context;
/*  79 */     this.font = font;
/*     */   }
/*     */ 
/*     */   public void validate()
/*     */   {
/*  84 */     PDFontDescriptor fd = this.font.getFontDescriptor();
/*     */ 
/*  86 */     if ((fd != null) && ((fd instanceof PDFontDescriptorDictionary)))
/*     */     {
/*  88 */       this.fontDescriptor = ((PDFontDescriptorDictionary)fd);
/*     */ 
/*  90 */       if (checkMandatoryFields(this.fontDescriptor.getCOSDictionary()))
/*     */       {
/*  92 */         if (hasOnlyOneFontFile(this.fontDescriptor))
/*     */         {
/*  94 */           PDStream fontFile = extractFontFile(this.fontDescriptor);
/*  95 */           if (fontFile != null)
/*     */           {
/*  97 */             processFontFile(this.fontDescriptor, fontFile);
/*  98 */             checkFontFileMetaData(this.fontDescriptor, fontFile);
/*     */           }
/*     */ 
/*     */         }
/* 103 */         else if (fontFileNotEmbedded(this.fontDescriptor))
/*     */         {
/* 105 */           this.fContainer.push(new ValidationResult.ValidationError("3.1.3", "FontFile entry is missing from FontDescriptor for " + this.fontDescriptor.getFontName()));
/*     */ 
/* 107 */           this.fContainer.notEmbedded();
/*     */         }
/*     */         else
/*     */         {
/* 111 */           this.fContainer.push(new ValidationResult.ValidationError("3.1.3", "They are more than one FontFile for " + this.fontDescriptor.getFontName()));
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/*     */     }
/*     */     else
/*     */     {
/* 119 */       this.fContainer.push(new ValidationResult.ValidationError("3.1.2", "FontDescriptor is null or is a AFM Descriptor"));
/*     */ 
/* 121 */       this.fContainer.notEmbedded();
/*     */     }
/*     */   }
/*     */ 
/*     */   protected boolean checkMandatoryFields(COSDictionary fDescriptor)
/*     */   {
/* 127 */     boolean areFieldsPresent = fDescriptor.containsKey("FontName");
/* 128 */     areFieldsPresent &= fDescriptor.containsKey("Flags");
/* 129 */     areFieldsPresent &= fDescriptor.containsKey("ItalicAngle");
/* 130 */     areFieldsPresent &= fDescriptor.containsKey("CapHeight");
/* 131 */     areFieldsPresent &= fDescriptor.containsKey("FontBBox");
/* 132 */     areFieldsPresent &= fDescriptor.containsKey("Ascent");
/* 133 */     areFieldsPresent &= fDescriptor.containsKey("Descent");
/* 134 */     areFieldsPresent &= fDescriptor.containsKey("StemV");
/* 135 */     areFieldsPresent &= fDescriptor.containsKey(COSName.FONT_NAME);
/* 136 */     if (!areFieldsPresent)
/*     */     {
/* 138 */       this.fContainer.push(new ValidationResult.ValidationError("3.1.2", "Some mandatory fields are missing from the FontDescriptor"));
/*     */     }
/*     */ 
/* 141 */     return areFieldsPresent;
/*     */   }
/*     */ 
/*     */   public abstract PDStream extractFontFile(PDFontDescriptorDictionary paramPDFontDescriptorDictionary);
/*     */ 
/*     */   protected boolean hasOnlyOneFontFile(PDFontDescriptorDictionary fontDescriptor)
/*     */   {
/* 154 */     PDStream ff1 = fontDescriptor.getFontFile();
/* 155 */     PDStream ff2 = fontDescriptor.getFontFile2();
/* 156 */     PDStream ff3 = fontDescriptor.getFontFile3();
/* 157 */     return (ff1 != null ? 1 : 0) ^ (ff2 != null ? 1 : 0) ^ (ff3 != null ? 1 : 0);
/*     */   }
/*     */ 
/*     */   protected boolean fontFileNotEmbedded(PDFontDescriptorDictionary fontDescriptor)
/*     */   {
/* 162 */     PDStream ff1 = fontDescriptor.getFontFile();
/* 163 */     PDStream ff2 = fontDescriptor.getFontFile2();
/* 164 */     PDStream ff3 = fontDescriptor.getFontFile3();
/* 165 */     return (ff1 == null) && (ff2 == null) && (ff3 == null);
/*     */   }
/*     */ 
/*     */   protected abstract void processFontFile(PDFontDescriptorDictionary paramPDFontDescriptorDictionary, PDStream paramPDStream);
/*     */ 
/*     */   protected void checkFontFileMetaData(PDFontDescriptor fontDescriptor, PDStream fontFile)
/*     */   {
/* 180 */     PDMetadata metadata = null;
/*     */     try
/*     */     {
/* 183 */       metadata = fontFile.getMetadata();
/*     */ 
/* 185 */       if (metadata != null)
/*     */       {
/* 188 */         if ((metadata.getFilters() != null) && (!metadata.getFilters().isEmpty()))
/*     */         {
/* 190 */           this.fContainer.push(new ValidationResult.ValidationError("1.2.7", "Filter specified in font file metadata dictionnary"));
/*     */ 
/* 192 */           return;
/*     */         }
/*     */ 
/* 195 */         byte[] mdAsBytes = getMetaDataStreamAsBytes(metadata);
/*     */         try
/*     */         {
/* 200 */           DomXmpParser xmpBuilder = new DomXmpParser();
/* 201 */           XMPMetadata xmpMeta = xmpBuilder.parse(mdAsBytes);
/*     */ 
/* 203 */           FontMetaDataValidation fontMDval = new FontMetaDataValidation();
/* 204 */           List ve = new ArrayList();
/* 205 */           fontMDval.analyseFontName(xmpMeta, fontDescriptor, ve);
/* 206 */           fontMDval.analyseRights(xmpMeta, fontDescriptor, ve);
/* 207 */           this.fContainer.push(ve);
/*     */         }
/*     */         catch (XmpParsingException e)
/*     */         {
/* 212 */           if (e.getErrorType() == XmpParsingException.ErrorType.NoValueType)
/*     */           {
/* 214 */             this.fContainer.push(new ValidationResult.ValidationError("7.6", e.getMessage()));
/*     */           }
/* 216 */           else if (e.getErrorType() == XmpParsingException.ErrorType.XpacketBadEnd)
/*     */           {
/* 218 */             this.fContainer.push(new ValidationResult.ValidationError("7.1.5", "Unable to parse font metadata due to : " + e.getMessage()));
/*     */           }
/*     */           else
/*     */           {
/* 223 */             this.fContainer.push(new ValidationResult.ValidationError("7.1", e.getMessage()));
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */     catch (IllegalStateException e)
/*     */     {
/* 230 */       this.fContainer.push(new ValidationResult.ValidationError("7.1.3", "The Metadata entry doesn't reference a stream object"));
/*     */     }
/*     */   }
/*     */ 
/*     */   protected final byte[] getMetaDataStreamAsBytes(PDMetadata metadata)
/*     */   {
/* 237 */     byte[] result = null;
/* 238 */     ByteArrayOutputStream bos = null;
/* 239 */     InputStream metaDataContent = null;
/*     */     try
/*     */     {
/* 242 */       bos = new ByteArrayOutputStream();
/* 243 */       metaDataContent = metadata.createInputStream();
/* 244 */       IOUtils.copyLarge(metaDataContent, bos);
/* 245 */       result = bos.toByteArray();
/*     */     }
/*     */     catch (IOException e)
/*     */     {
/* 249 */       this.fContainer.push(new ValidationResult.ValidationError("7.1.4", "Unable to read font metadata due to : " + e.getMessage()));
/*     */     }
/*     */     finally
/*     */     {
/* 254 */       IOUtils.closeQuietly(metaDataContent);
/* 255 */       IOUtils.closeQuietly(bos);
/*     */     }
/* 257 */     return result;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.preflight.font.descriptor.FontDescriptorHelper
 * JD-Core Version:    0.6.2
 */