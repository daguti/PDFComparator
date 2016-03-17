/*     */ package org.apache.pdfbox.preflight.graphic;
/*     */ 
/*     */ import java.awt.color.ICC_ColorSpace;
/*     */ import java.awt.color.ICC_Profile;
/*     */ import java.io.IOException;
/*     */ import org.apache.pdfbox.cos.COSArray;
/*     */ import org.apache.pdfbox.cos.COSBase;
/*     */ import org.apache.pdfbox.cos.COSDictionary;
/*     */ import org.apache.pdfbox.cos.COSName;
/*     */ import org.apache.pdfbox.pdmodel.PDDocumentCatalog;
/*     */ import org.apache.pdfbox.pdmodel.common.PDStream;
/*     */ import org.apache.pdfbox.preflight.PreflightContext;
/*     */ import org.apache.pdfbox.preflight.PreflightDocument;
/*     */ import org.apache.pdfbox.preflight.ValidationResult.ValidationError;
/*     */ import org.apache.pdfbox.preflight.exception.ValidationException;
/*     */ import org.apache.pdfbox.preflight.utils.COSUtils;
/*     */ 
/*     */ public class ICCProfileWrapper
/*     */ {
/*     */   private final ICC_Profile profile;
/*     */   private final ICC_ColorSpace colorSpace;
/*     */ 
/*     */   public ICCProfileWrapper(ICC_Profile _profile)
/*     */   {
/*  61 */     this.profile = _profile;
/*  62 */     this.colorSpace = new ICC_ColorSpace(_profile);
/*     */   }
/*     */ 
/*     */   public int getColorSpaceType()
/*     */   {
/*  72 */     return this.colorSpace.getType();
/*     */   }
/*     */ 
/*     */   public ICC_Profile getProfile()
/*     */   {
/*  80 */     return this.profile;
/*     */   }
/*     */ 
/*     */   public boolean isRGBColorSpace()
/*     */   {
/*  90 */     return 5 == this.colorSpace.getType();
/*     */   }
/*     */ 
/*     */   public boolean isCMYKColorSpace()
/*     */   {
/* 100 */     return 9 == this.colorSpace.getType();
/*     */   }
/*     */ 
/*     */   public boolean isGrayColorSpace()
/*     */   {
/* 110 */     return 6 == this.colorSpace.getType();
/*     */   }
/*     */ 
/*     */   private static ICCProfileWrapper searchFirstICCProfile(PreflightContext context)
/*     */     throws ValidationException
/*     */   {
/* 124 */     PreflightDocument document = context.getDocument();
/* 125 */     PDDocumentCatalog catalog = document.getDocumentCatalog();
/* 126 */     COSBase cBase = catalog.getCOSDictionary().getItem(COSName.getPDFName("OutputIntents"));
/* 127 */     COSArray outputIntents = COSUtils.getAsArray(cBase, document.getDocument());
/*     */ 
/* 129 */     for (int i = 0; (outputIntents != null) && (i < outputIntents.size()); i++)
/*     */     {
/* 131 */       COSDictionary outputIntentDict = COSUtils.getAsDictionary(outputIntents.get(i), document.getDocument());
/* 132 */       COSBase destOutputProfile = outputIntentDict.getItem("DestOutputProfile");
/* 133 */       if (destOutputProfile != null)
/*     */       {
/*     */         try
/*     */         {
/* 137 */           PDStream stream = PDStream.createFromCOS(COSUtils.getAsStream(destOutputProfile, document.getDocument()));
/*     */ 
/* 139 */           if (stream != null)
/*     */           {
/* 141 */             ICC_Profile iccp = ICC_Profile.getInstance(stream.getByteArray());
/* 142 */             return new ICCProfileWrapper(iccp);
/*     */           }
/*     */         }
/*     */         catch (IllegalArgumentException e)
/*     */         {
/* 147 */           context.addValidationError(new ValidationResult.ValidationError("2.1.4", "DestOutputProfile isn't a valid ICCProfile. Caused by : " + e.getMessage()));
/*     */         }
/*     */         catch (IOException e)
/*     */         {
/* 152 */           context.addValidationError(new ValidationResult.ValidationError("2.1.4", "Unable to parse the ICCProfile. Caused by : " + e.getMessage()));
/*     */         }
/*     */       }
/*     */     }
/*     */ 
/* 157 */     return null;
/*     */   }
/*     */ 
/*     */   public static ICCProfileWrapper getOrSearchICCProfile(PreflightContext context) throws ValidationException
/*     */   {
/* 162 */     ICCProfileWrapper profileWrapper = context.getIccProfileWrapper();
/* 163 */     if ((profileWrapper == null) && (!context.isIccProfileAlreadySearched()))
/*     */     {
/* 165 */       profileWrapper = searchFirstICCProfile(context);
/* 166 */       context.setIccProfileAlreadySearched(true);
/*     */     }
/* 168 */     return profileWrapper;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.preflight.graphic.ICCProfileWrapper
 * JD-Core Version:    0.6.2
 */