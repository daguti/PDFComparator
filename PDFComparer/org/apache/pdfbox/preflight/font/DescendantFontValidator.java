/*     */ package org.apache.pdfbox.preflight.font;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import org.apache.pdfbox.cos.COSBase;
/*     */ import org.apache.pdfbox.cos.COSDictionary;
/*     */ import org.apache.pdfbox.cos.COSDocument;
/*     */ import org.apache.pdfbox.cos.COSName;
/*     */ import org.apache.pdfbox.cos.COSStream;
/*     */ import org.apache.pdfbox.pdmodel.font.PDFont;
/*     */ import org.apache.pdfbox.preflight.PreflightContext;
/*     */ import org.apache.pdfbox.preflight.PreflightDocument;
/*     */ import org.apache.pdfbox.preflight.ValidationResult.ValidationError;
/*     */ import org.apache.pdfbox.preflight.font.container.FontContainer;
/*     */ import org.apache.pdfbox.preflight.font.util.CIDToGIDMap;
/*     */ import org.apache.pdfbox.preflight.utils.COSUtils;
/*     */ 
/*     */ public abstract class DescendantFontValidator<T extends FontContainer> extends SimpleFontValidator<T>
/*     */ {
/*  46 */   protected COSDocument cosDocument = null;
/*     */ 
/*     */   public DescendantFontValidator(PreflightContext context, PDFont font, T fContainer)
/*     */   {
/*  50 */     super(context, font, fContainer);
/*  51 */     this.cosDocument = context.getDocument().getDocument();
/*     */   }
/*     */ 
/*     */   protected void checkMandatoryField()
/*     */   {
/*  57 */     COSDictionary fontDictionary = (COSDictionary)this.font.getCOSObject();
/*     */ 
/*  59 */     boolean arePresent = fontDictionary.containsKey(COSName.TYPE);
/*  60 */     arePresent &= fontDictionary.containsKey(COSName.SUBTYPE);
/*  61 */     arePresent &= fontDictionary.containsKey(COSName.BASE_FONT);
/*  62 */     arePresent &= fontDictionary.containsKey(COSName.CIDSYSTEMINFO);
/*  63 */     arePresent &= fontDictionary.containsKey(COSName.FONT_DESC);
/*     */ 
/*  65 */     if (!arePresent)
/*     */     {
/*  67 */       this.fontContainer.push(new ValidationResult.ValidationError("3.1.1", "Required keys are missing"));
/*     */     }
/*     */ 
/*  70 */     checkCIDSystemInfo(fontDictionary.getItem(COSName.CIDSYSTEMINFO));
/*  71 */     checkCIDToGIDMap(fontDictionary.getItem(COSName.CID_TO_GID_MAP));
/*     */   }
/*     */ 
/*     */   protected void checkCIDSystemInfo(COSBase sysinfo)
/*     */   {
/*  87 */     COSDictionary cidSysInfo = COSUtils.getAsDictionary(sysinfo, this.cosDocument);
/*  88 */     if (cidSysInfo != null)
/*     */     {
/*  90 */       COSBase reg = cidSysInfo.getItem(COSName.REGISTRY);
/*  91 */       COSBase ord = cidSysInfo.getItem(COSName.ORDERING);
/*  92 */       COSBase sup = cidSysInfo.getItem(COSName.SUPPLEMENT);
/*     */ 
/*  94 */       if ((!COSUtils.isString(reg, this.cosDocument)) || (!COSUtils.isString(ord, this.cosDocument)) || (!COSUtils.isInteger(sup, this.cosDocument)))
/*     */       {
/*  97 */         this.fontContainer.push(new ValidationResult.ValidationError("3.1.8"));
/*     */       }
/*     */ 
/*     */     }
/*     */     else
/*     */     {
/* 103 */       this.fontContainer.push(new ValidationResult.ValidationError("3.1.8"));
/*     */     }
/*     */   }
/*     */ 
/*     */   protected abstract void checkCIDToGIDMap(COSBase paramCOSBase);
/*     */ 
/*     */   protected CIDToGIDMap checkCIDToGIDMap(COSBase ctog, boolean mandatory)
/*     */   {
/* 131 */     CIDToGIDMap cidToGidMap = null;
/*     */ 
/* 133 */     if (COSUtils.isString(ctog, this.cosDocument))
/*     */     {
/* 136 */       String ctogStr = COSUtils.getAsString(ctog, this.cosDocument);
/* 137 */       if (!"Identity".equals(ctogStr))
/*     */       {
/* 139 */         this.fontContainer.push(new ValidationResult.ValidationError("3.1.9", "The CIDToGID entry is invalid"));
/*     */       }
/*     */ 
/*     */     }
/* 143 */     else if (COSUtils.isStream(ctog, this.cosDocument))
/*     */     {
/*     */       try
/*     */       {
/* 147 */         COSStream ctogMap = COSUtils.getAsStream(ctog, this.cosDocument);
/* 148 */         cidToGidMap = new CIDToGIDMap();
/* 149 */         cidToGidMap.parseStream(ctogMap);
/*     */       }
/*     */       catch (IOException e)
/*     */       {
/* 154 */         this.fontContainer.push(new ValidationResult.ValidationError("3.1.9"));
/*     */       }
/*     */     }
/* 157 */     else if (mandatory)
/*     */     {
/* 159 */       this.fontContainer.push(new ValidationResult.ValidationError("3.1.9"));
/*     */     }
/* 161 */     return cidToGidMap;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.preflight.font.DescendantFontValidator
 * JD-Core Version:    0.6.2
 */