/*     */ package org.apache.pdfbox.preflight.font;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import org.apache.fontbox.cmap.CMap;
/*     */ import org.apache.fontbox.cmap.CMapParser;
/*     */ import org.apache.pdfbox.cos.COSArray;
/*     */ import org.apache.pdfbox.cos.COSBase;
/*     */ import org.apache.pdfbox.cos.COSDictionary;
/*     */ import org.apache.pdfbox.cos.COSDocument;
/*     */ import org.apache.pdfbox.cos.COSName;
/*     */ import org.apache.pdfbox.cos.COSStream;
/*     */ import org.apache.pdfbox.pdmodel.font.PDCIDFontType0Font;
/*     */ import org.apache.pdfbox.pdmodel.font.PDCIDFontType2Font;
/*     */ import org.apache.pdfbox.pdmodel.font.PDFont;
/*     */ import org.apache.pdfbox.preflight.PreflightContext;
/*     */ import org.apache.pdfbox.preflight.PreflightDocument;
/*     */ import org.apache.pdfbox.preflight.ValidationResult.ValidationError;
/*     */ import org.apache.pdfbox.preflight.exception.ValidationException;
/*     */ import org.apache.pdfbox.preflight.font.container.FontContainer;
/*     */ import org.apache.pdfbox.preflight.font.container.Type0Container;
/*     */ import org.apache.pdfbox.preflight.utils.COSUtils;
/*     */ 
/*     */ public class Type0FontValidator extends FontValidator<Type0Container>
/*     */ {
/*  62 */   protected COSDocument cosDocument = null;
/*     */ 
/*     */   public Type0FontValidator(PreflightContext context, PDFont font)
/*     */   {
/*  66 */     super(context, font, new Type0Container(font));
/*  67 */     this.cosDocument = this.context.getDocument().getDocument();
/*     */   }
/*     */ 
/*     */   public void validate()
/*     */     throws ValidationException
/*     */   {
/*  73 */     checkMandatoryFields();
/*     */ 
/*  75 */     processDescendantFont();
/*     */ 
/*  77 */     checkEncoding();
/*  78 */     checkToUnicode();
/*     */   }
/*     */ 
/*     */   protected void checkMandatoryFields()
/*     */   {
/*  87 */     COSDictionary fontDictionary = (COSDictionary)this.font.getCOSObject();
/*  88 */     boolean areFieldsPResent = fontDictionary.containsKey(COSName.TYPE);
/*  89 */     areFieldsPResent &= fontDictionary.containsKey(COSName.SUBTYPE);
/*  90 */     areFieldsPResent &= fontDictionary.containsKey(COSName.BASE_FONT);
/*  91 */     areFieldsPResent &= fontDictionary.containsKey(COSName.DESCENDANT_FONTS);
/*  92 */     areFieldsPResent &= fontDictionary.containsKey(COSName.ENCODING);
/*     */ 
/*  94 */     if (!areFieldsPResent)
/*     */     {
/*  96 */       ((Type0Container)this.fontContainer).push(new ValidationResult.ValidationError("3.1.1", "Some keys are missing from composite font dictionary"));
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void processDescendantFont()
/*     */     throws ValidationException
/*     */   {
/* 107 */     COSDictionary fontDictionary = (COSDictionary)this.font.getCOSObject();
/*     */ 
/* 109 */     COSArray array = COSUtils.getAsArray(fontDictionary.getItem(COSName.DESCENDANT_FONTS), this.cosDocument);
/* 110 */     if ((array == null) || (array.size() != 1))
/*     */     {
/* 116 */       ((Type0Container)this.fontContainer).push(new ValidationResult.ValidationError("3.1.7", "CIDFont is missing from the DescendantFonts array or the size of array is greater than 1"));
/*     */ 
/* 118 */       return;
/*     */     }
/*     */ 
/* 121 */     COSDictionary cidFont = COSUtils.getAsDictionary(array.get(0), this.cosDocument);
/* 122 */     if (cidFont == null)
/*     */     {
/* 124 */       ((Type0Container)this.fontContainer).push(new ValidationResult.ValidationError("3.1.7", "The DescendantFonts array should have one element with is a dictionary."));
/*     */ 
/* 126 */       return;
/*     */     }
/*     */ 
/* 129 */     FontValidator cidFontValidator = createDescendantValidator(cidFont);
/* 130 */     if (cidFontValidator != null)
/*     */     {
/* 132 */       ((Type0Container)this.fontContainer).setDelegateFontContainer(cidFontValidator.getFontContainer());
/* 133 */       cidFontValidator.validate();
/*     */     }
/*     */   }
/*     */ 
/*     */   protected FontValidator<? extends FontContainer> createDescendantValidator(COSDictionary cidFont)
/*     */   {
/* 139 */     String subtype = cidFont.getNameAsString(COSName.SUBTYPE);
/* 140 */     FontValidator cidFontValidator = null;
/* 141 */     if ("CIDFontType0".equals(subtype))
/*     */     {
/* 143 */       cidFontValidator = createCIDType0FontValidator(cidFont);
/*     */     }
/* 145 */     else if ("CIDFontType2".equals(subtype))
/*     */     {
/* 147 */       cidFontValidator = createCIDType2FontValidator(cidFont);
/*     */     }
/*     */     else
/*     */     {
/* 151 */       ((Type0Container)this.fontContainer).push(new ValidationResult.ValidationError("3.1.1", "Type and/or Subtype keys are missing"));
/*     */     }
/*     */ 
/* 154 */     return cidFontValidator;
/*     */   }
/*     */ 
/*     */   protected FontValidator<? extends FontContainer> createCIDType0FontValidator(COSDictionary fDict)
/*     */   {
/* 164 */     return new CIDType0FontValidator(this.context, new PDCIDFontType0Font(fDict));
/*     */   }
/*     */ 
/*     */   protected FontValidator<? extends FontContainer> createCIDType2FontValidator(COSDictionary fDict)
/*     */   {
/* 174 */     return new CIDType2FontValidator(this.context, new PDCIDFontType2Font(fDict));
/*     */   }
/*     */ 
/*     */   protected void checkEncoding()
/*     */   {
/* 187 */     COSBase encoding = ((COSDictionary)this.font.getCOSObject()).getItem(COSName.ENCODING);
/* 188 */     checkCMapEncoding(encoding);
/*     */   }
/*     */ 
/*     */   protected void checkCMapEncoding(COSBase encoding)
/*     */   {
/* 193 */     if (COSUtils.isString(encoding, this.cosDocument))
/*     */     {
/* 196 */       String str = COSUtils.getAsString(encoding, this.cosDocument);
/* 197 */       if ((!"Identity-V".equals(str)) && (!"Identity-H".equals(str)))
/*     */       {
/* 200 */         ((Type0Container)this.fontContainer).push(new ValidationResult.ValidationError("3.1.7", "The CMap is a string but it isn't an Identity-H/V"));
/*     */ 
/* 202 */         return;
/*     */       }
/*     */     }
/* 205 */     else if (COSUtils.isStream(encoding, this.cosDocument))
/*     */     {
/* 211 */       processCMapAsStream(COSUtils.getAsStream(encoding, this.cosDocument));
/*     */     }
/*     */     else
/*     */     {
/* 216 */       ((Type0Container)this.fontContainer).push(new ValidationResult.ValidationError("3.1.10", "The CMap type is invalid"));
/*     */     }
/*     */   }
/*     */ 
/*     */   private void processCMapAsStream(COSStream aCMap)
/*     */   {
/* 233 */     COSBase sysinfo = aCMap.getItem(COSName.CIDSYSTEMINFO);
/* 234 */     checkCIDSystemInfo(sysinfo);
/*     */     try
/*     */     {
/* 239 */       CMap fontboxCMap = new CMapParser().parse(null, aCMap.getUnfilteredStream());
/* 240 */       int wmValue = fontboxCMap.getWMode();
/* 241 */       String cmnValue = fontboxCMap.getName();
/*     */ 
/* 247 */       int wmode = aCMap.getInt(COSName.getPDFName("WMode"), 0);
/*     */ 
/* 249 */       String type = aCMap.getNameAsString(COSName.TYPE);
/* 250 */       String cmapName = aCMap.getNameAsString(COSName.getPDFName("CMapName"));
/*     */ 
/* 252 */       if ((cmapName == null) || ("".equals(cmapName)) || (wmode > 1))
/*     */       {
/* 254 */         ((Type0Container)this.fontContainer).push(new ValidationResult.ValidationError("3.1.10", "Some elements in the CMap dictionary are missing or invalid"));
/*     */       }
/* 257 */       else if ((wmValue != wmode) || (!cmapName.equals(cmnValue)))
/*     */       {
/* 259 */         ((Type0Container)this.fontContainer).push(new ValidationResult.ValidationError("3.1.10", "CMapName or WMode is inconsistent"));
/*     */       }
/* 262 */       else if (!"CMap".equals(type))
/*     */       {
/* 264 */         ((Type0Container)this.fontContainer).push(new ValidationResult.ValidationError("3.1.10", "The CMap type is invalid"));
/*     */       }
/*     */ 
/*     */     }
/*     */     catch (IOException e)
/*     */     {
/* 270 */       ((Type0Container)this.fontContainer).push(new ValidationResult.ValidationError("3.2.5", "The CMap type is damaged"));
/*     */     }
/*     */ 
/* 273 */     COSDictionary cmapUsed = (COSDictionary)aCMap.getDictionaryObject(COSName.getPDFName("UseCMap"));
/*     */ 
/* 275 */     if (cmapUsed != null)
/*     */     {
/* 277 */       checkCMapEncoding(cmapUsed);
/*     */     }
/* 279 */     compareCIDSystemInfo(aCMap);
/*     */   }
/*     */ 
/*     */   protected boolean checkCIDSystemInfo(COSBase sysinfo)
/*     */   {
/* 295 */     boolean result = true;
/* 296 */     COSDictionary cidSysInfo = COSUtils.getAsDictionary(sysinfo, this.cosDocument);
/*     */ 
/* 298 */     if (cidSysInfo != null)
/*     */     {
/* 300 */       COSBase reg = cidSysInfo.getItem(COSName.REGISTRY);
/* 301 */       COSBase ord = cidSysInfo.getItem(COSName.ORDERING);
/* 302 */       COSBase sup = cidSysInfo.getItem(COSName.SUPPLEMENT);
/*     */ 
/* 304 */       if ((!COSUtils.isString(reg, this.cosDocument)) || (!COSUtils.isString(ord, this.cosDocument)) || (!COSUtils.isInteger(sup, this.cosDocument)))
/*     */       {
/* 307 */         ((Type0Container)this.fontContainer).push(new ValidationResult.ValidationError("3.1.8"));
/* 308 */         result = false;
/*     */       }
/*     */ 
/*     */     }
/*     */     else
/*     */     {
/* 314 */       ((Type0Container)this.fontContainer).push(new ValidationResult.ValidationError("3.1.8"));
/* 315 */       result = false;
/*     */     }
/* 317 */     return result;
/*     */   }
/*     */ 
/*     */   private void compareCIDSystemInfo(COSDictionary cmap)
/*     */   {
/* 329 */     COSDictionary fontDictionary = (COSDictionary)this.font.getCOSObject();
/* 330 */     COSArray array = COSUtils.getAsArray(fontDictionary.getItem(COSName.DESCENDANT_FONTS), this.cosDocument);
/*     */ 
/* 332 */     if ((array != null) && (array.size() > 0))
/*     */     {
/* 334 */       COSDictionary cidFont = COSUtils.getAsDictionary(array.get(0), this.cosDocument);
/* 335 */       COSDictionary cmsi = COSUtils.getAsDictionary(cmap.getItem(COSName.CIDSYSTEMINFO), this.cosDocument);
/* 336 */       COSDictionary cfsi = COSUtils.getAsDictionary(cidFont.getItem(COSName.CIDSYSTEMINFO), this.cosDocument);
/*     */ 
/* 338 */       String regCM = COSUtils.getAsString(cmsi.getItem(COSName.REGISTRY), this.cosDocument);
/* 339 */       String ordCM = COSUtils.getAsString(cmsi.getItem(COSName.ORDERING), this.cosDocument);
/* 340 */       String regCF = COSUtils.getAsString(cfsi.getItem(COSName.REGISTRY), this.cosDocument);
/* 341 */       String ordCF = COSUtils.getAsString(cfsi.getItem(COSName.ORDERING), this.cosDocument);
/*     */ 
/* 343 */       if ((!regCF.equals(regCM)) || (!ordCF.equals(ordCM)))
/*     */       {
/* 345 */         ((Type0Container)this.fontContainer).push(new ValidationResult.ValidationError("3.1.8", "The CIDSystemInfo is inconsistent"));
/*     */       }
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.preflight.font.Type0FontValidator
 * JD-Core Version:    0.6.2
 */