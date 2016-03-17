/*     */ package org.apache.pdfbox.preflight.process;
/*     */ 
/*     */ import java.awt.color.ICC_Profile;
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import org.apache.pdfbox.cos.COSArray;
/*     */ import org.apache.pdfbox.cos.COSBase;
/*     */ import org.apache.pdfbox.cos.COSDictionary;
/*     */ import org.apache.pdfbox.cos.COSDocument;
/*     */ import org.apache.pdfbox.cos.COSName;
/*     */ import org.apache.pdfbox.cos.COSObject;
/*     */ import org.apache.pdfbox.pdmodel.PDDocument;
/*     */ import org.apache.pdfbox.pdmodel.PDDocumentCatalog;
/*     */ import org.apache.pdfbox.pdmodel.PDDocumentNameDictionary;
/*     */ import org.apache.pdfbox.pdmodel.PDEmbeddedFilesNameTreeNode;
/*     */ import org.apache.pdfbox.pdmodel.common.PDStream;
/*     */ import org.apache.pdfbox.persistence.util.COSObjectKey;
/*     */ import org.apache.pdfbox.preflight.PreflightConfiguration;
/*     */ import org.apache.pdfbox.preflight.PreflightContext;
/*     */ import org.apache.pdfbox.preflight.PreflightDocument;
/*     */ import org.apache.pdfbox.preflight.ValidationResult.ValidationError;
/*     */ import org.apache.pdfbox.preflight.exception.ValidationException;
/*     */ import org.apache.pdfbox.preflight.graphic.ICCProfileWrapper;
/*     */ import org.apache.pdfbox.preflight.utils.COSUtils;
/*     */ import org.apache.pdfbox.preflight.utils.ContextHelper;
/*     */ 
/*     */ public class CatalogValidationProcess extends AbstractProcess
/*     */ {
/*     */   protected PDDocumentCatalog catalog;
/* 129 */   protected List<String> listICC = new ArrayList();
/*     */ 
/*     */   public CatalogValidationProcess()
/*     */   {
/* 133 */     this.listICC.add("FOGRA43");
/* 134 */     this.listICC.add("CGATS TR 006");
/* 135 */     this.listICC.add("CGATS TR006");
/* 136 */     this.listICC.add("FOGRA39");
/* 137 */     this.listICC.add("JC200103");
/* 138 */     this.listICC.add("FOGRA27");
/* 139 */     this.listICC.add("EUROSB104");
/* 140 */     this.listICC.add("FOGRA45");
/* 141 */     this.listICC.add("FOGRA46");
/* 142 */     this.listICC.add("FOGRA41");
/* 143 */     this.listICC.add("CGATS TR 001");
/* 144 */     this.listICC.add("CGATS TR 003");
/* 145 */     this.listICC.add("CGATS TR 005");
/* 146 */     this.listICC.add("CGATS TR001");
/* 147 */     this.listICC.add("CGATS TR003");
/* 148 */     this.listICC.add("CGATS TR005");
/* 149 */     this.listICC.add("FOGRA28");
/* 150 */     this.listICC.add("JCW2003");
/* 151 */     this.listICC.add("EUROSB204");
/* 152 */     this.listICC.add("FOGRA47");
/* 153 */     this.listICC.add("FOGRA44");
/* 154 */     this.listICC.add("FOGRA29");
/* 155 */     this.listICC.add("JC200104");
/* 156 */     this.listICC.add("FOGRA40");
/* 157 */     this.listICC.add("FOGRA30");
/* 158 */     this.listICC.add("FOGRA42");
/* 159 */     this.listICC.add("IFRA26");
/* 160 */     this.listICC.add("JCN2002");
/* 161 */     this.listICC.add("CGATS TR 002");
/* 162 */     this.listICC.add("CGATS TR002");
/* 163 */     this.listICC.add("FOGRA33");
/* 164 */     this.listICC.add("FOGRA37");
/* 165 */     this.listICC.add("FOGRA31");
/* 166 */     this.listICC.add("FOGRA35");
/* 167 */     this.listICC.add("FOGRA32");
/* 168 */     this.listICC.add("FOGRA34");
/* 169 */     this.listICC.add("FOGRA36");
/* 170 */     this.listICC.add("FOGRA38");
/* 171 */     this.listICC.add("sRGB");
/* 172 */     this.listICC.add("sRGB IEC61966-2.1");
/* 173 */     this.listICC.add("Adobe RGB (1998)");
/* 174 */     this.listICC.add("bg-sRGB");
/* 175 */     this.listICC.add("sYCC");
/* 176 */     this.listICC.add("scRGB");
/* 177 */     this.listICC.add("scRGB-nl");
/* 178 */     this.listICC.add("scYCC-nl");
/* 179 */     this.listICC.add("ROMM RGB");
/* 180 */     this.listICC.add("RIMM RGB");
/* 181 */     this.listICC.add("ERIMM RGB");
/* 182 */     this.listICC.add("eciRGB");
/* 183 */     this.listICC.add("opRGB");
/*     */   }
/*     */ 
/*     */   protected boolean isStandardICCCharacterization(String name)
/*     */   {
/* 188 */     for (String iccStandard : this.listICC)
/*     */     {
/* 190 */       if (iccStandard.contains(name))
/*     */       {
/* 192 */         return true;
/*     */       }
/*     */     }
/* 195 */     return false;
/*     */   }
/*     */ 
/*     */   public void validate(PreflightContext ctx) throws ValidationException
/*     */   {
/* 200 */     PDDocument pdfbox = ctx.getDocument();
/* 201 */     this.catalog = pdfbox.getDocumentCatalog();
/*     */ 
/* 203 */     if (this.catalog == null)
/*     */     {
/* 205 */       ctx.addValidationError(new ValidationResult.ValidationError("1.2.14", "There are no Catalog entry in the Document."));
/*     */     }
/*     */     else
/*     */     {
/* 209 */       validateActions(ctx);
/* 210 */       validateLang(ctx);
/* 211 */       validateNames(ctx);
/* 212 */       validateOCProperties(ctx);
/* 213 */       validateOutputIntent(ctx);
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void validateActions(PreflightContext ctx)
/*     */     throws ValidationException
/*     */   {
/* 227 */     ContextHelper.validateElement(ctx, this.catalog.getCOSDictionary(), "actions-process");
/*     */ 
/* 229 */     COSBase aa = this.catalog.getCOSDictionary().getItem("AA");
/* 230 */     if (aa != null)
/*     */     {
/* 232 */       addValidationError(ctx, new ValidationResult.ValidationError("6.2.2", "The AA field is forbidden for the Catalog  when the PDF is a PDF/A"));
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void validateLang(PreflightContext ctx)
/*     */     throws ValidationException
/*     */   {
/* 246 */     String lang = this.catalog.getLanguage();
/* 247 */     if ((lang != null) && (!"".equals(lang)) && (!lang.matches("[A-Za-z]{1,8}(-[A-Za-z]{1,8})*")))
/*     */     {
/* 249 */       addValidationError(ctx, new ValidationResult.ValidationError("1.0.8"));
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void validateNames(PreflightContext ctx)
/*     */     throws ValidationException
/*     */   {
/* 261 */     PDDocumentNameDictionary names = this.catalog.getNames();
/* 262 */     if (names != null)
/*     */     {
/* 264 */       PDEmbeddedFilesNameTreeNode efs = names.getEmbeddedFiles();
/* 265 */       if (efs != null)
/*     */       {
/* 267 */         addValidationError(ctx, new ValidationResult.ValidationError("1.4.7", "EmbeddedFile entry is present in the Names dictionary"));
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void validateOCProperties(PreflightContext ctx)
/*     */     throws ValidationException
/*     */   {
/* 281 */     if (this.catalog.getOCProperties() != null)
/*     */     {
/* 283 */       addValidationError(ctx, new ValidationResult.ValidationError("1.4.8", "A Catalog shall not contain the OCPProperties entry."));
/*     */     }
/*     */   }
/*     */ 
/*     */   public void validateOutputIntent(PreflightContext ctx)
/*     */     throws ValidationException
/*     */   {
/* 301 */     COSDocument cosDocument = ctx.getDocument().getDocument();
/* 302 */     COSBase cBase = this.catalog.getCOSDictionary().getItem(COSName.getPDFName("OutputIntents"));
/* 303 */     COSArray outputIntents = COSUtils.getAsArray(cBase, cosDocument);
/*     */ 
/* 305 */     Map tmpDestOutputProfile = new HashMap();
/* 306 */     for (int i = 0; (outputIntents != null) && (i < outputIntents.size()); i++)
/*     */     {
/* 308 */       COSDictionary outputIntentDict = COSUtils.getAsDictionary(outputIntents.get(i), cosDocument);
/*     */ 
/* 310 */       if (outputIntentDict == null)
/*     */       {
/* 312 */         addValidationError(ctx, new ValidationResult.ValidationError("2.1.2", "OutputIntent object is null or isn't a dictionary"));
/*     */       }
/*     */       else
/*     */       {
/* 318 */         String sValue = outputIntentDict.getNameAsString("S");
/* 319 */         if (!"GTS_PDFA1".equals(sValue))
/*     */         {
/* 321 */           addValidationError(ctx, new ValidationResult.ValidationError("2.1.3", "The S entry of the OutputIntent isn't GTS_PDFA1"));
/*     */         }
/*     */         else
/*     */         {
/* 327 */           String outputConditionIdentifier = outputIntentDict.getString("OutputConditionIdentifier");
/*     */ 
/* 329 */           if (outputConditionIdentifier == null)
/*     */           {
/* 331 */             addValidationError(ctx, new ValidationResult.ValidationError("2.1.2", "The OutputIntentCondition is missing"));
/*     */           }
/*     */           else
/*     */           {
/* 343 */             COSBase destOutputProfile = outputIntentDict.getItem("DestOutputProfile");
/* 344 */             validateICCProfile(destOutputProfile, tmpDestOutputProfile, ctx);
/*     */ 
/* 346 */             PreflightConfiguration config = ctx.getConfig();
/* 347 */             if ((config.isLazyValidation()) && (!isStandardICCCharacterization(outputConditionIdentifier)))
/*     */             {
/* 349 */               String info = outputIntentDict.getString(COSName.getPDFName("Info"));
/* 350 */               if ((info == null) || ("".equals(info)))
/*     */               {
/* 352 */                 ValidationResult.ValidationError error = new ValidationResult.ValidationError("2.1.2", "The Info entry of a OutputIntent dictionary is missing");
/*     */ 
/* 354 */                 error.setWarning(true);
/* 355 */                 addValidationError(ctx, error);
/*     */               }
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void validateICCProfile(COSBase destOutputProfile, Map<COSObjectKey, Boolean> mapDestOutputProfile, PreflightContext ctx)
/*     */     throws ValidationException
/*     */   {
/*     */     try
/*     */     {
/* 386 */       if (destOutputProfile == null)
/*     */       {
/* 388 */         return;
/*     */       }
/*     */ 
/* 392 */       if ((destOutputProfile instanceof COSObject))
/*     */       {
/* 394 */         if (mapDestOutputProfile.containsKey(new COSObjectKey((COSObject)destOutputProfile)))
/*     */         {
/* 397 */           return;
/*     */         }
/* 399 */         if (!mapDestOutputProfile.isEmpty())
/*     */         {
/* 402 */           addValidationError(ctx, new ValidationResult.ValidationError("2.1.5", "More than one ICCProfile is defined"));
/*     */ 
/* 404 */           return;
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/* 410 */       mapDestOutputProfile.put(new COSObjectKey((COSObject)destOutputProfile), Boolean.valueOf(true));
/* 411 */       COSDocument cosDocument = ctx.getDocument().getDocument();
/* 412 */       PDStream stream = PDStream.createFromCOS(COSUtils.getAsStream(destOutputProfile, cosDocument));
/* 413 */       if (stream == null)
/*     */       {
/* 415 */         addValidationError(ctx, new ValidationResult.ValidationError("2.1.2", "OutputIntent object uses a NULL Object"));
/*     */ 
/* 417 */         return;
/*     */       }
/*     */ 
/* 420 */       ICC_Profile iccp = ICC_Profile.getInstance(stream.getByteArray());
/* 421 */       PreflightConfiguration config = ctx.getConfig();
/*     */ 
/* 423 */       if (iccp.getMajorVersion() == 2)
/*     */       {
/* 425 */         if (iccp.getMinorVersion() > 64)
/*     */         {
/* 430 */           ValidationResult.ValidationError error = new ValidationResult.ValidationError("2.1.6", "Invalid version of the ICCProfile");
/*     */ 
/* 432 */           error.setWarning(config.isLazyValidation());
/* 433 */           addValidationError(ctx, error);
/*     */         }
/*     */ 
/*     */       }
/* 437 */       else if (iccp.getMajorVersion() > 2)
/*     */       {
/* 442 */         ValidationResult.ValidationError error = new ValidationResult.ValidationError("2.1.6", "Invalid version of the ICCProfile");
/*     */ 
/* 444 */         error.setWarning(config.isLazyValidation());
/* 445 */         addValidationError(ctx, error);
/* 446 */         return;
/*     */       }
/*     */ 
/* 449 */       if (ctx.getIccProfileWrapper() == null)
/*     */       {
/* 451 */         ctx.setIccProfileWrapper(new ICCProfileWrapper(iccp));
/*     */       }
/*     */ 
/*     */     }
/*     */     catch (IllegalArgumentException e)
/*     */     {
/* 458 */       addValidationError(ctx, new ValidationResult.ValidationError("2.1.4", "DestOutputProfile isn't a valid ICCProfile. Caused by : " + e.getMessage()));
/*     */     }
/*     */     catch (IOException e)
/*     */     {
/* 463 */       throw new ValidationException("Unable to parse the ICC Profile.", e);
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.preflight.process.CatalogValidationProcess
 * JD-Core Version:    0.6.2
 */