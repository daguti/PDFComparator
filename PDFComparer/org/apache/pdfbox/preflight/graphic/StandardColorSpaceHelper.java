/*     */ package org.apache.pdfbox.preflight.graphic;
/*     */ 
/*     */ import java.awt.color.ICC_Profile;
/*     */ import java.io.IOException;
/*     */ import java.util.Collection;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import org.apache.pdfbox.pdmodel.PDResources;
/*     */ import org.apache.pdfbox.pdmodel.common.PDStream;
/*     */ import org.apache.pdfbox.pdmodel.graphics.color.PDColorSpace;
/*     */ import org.apache.pdfbox.pdmodel.graphics.color.PDDeviceN;
/*     */ import org.apache.pdfbox.pdmodel.graphics.color.PDDeviceNAttributes;
/*     */ import org.apache.pdfbox.pdmodel.graphics.color.PDICCBased;
/*     */ import org.apache.pdfbox.pdmodel.graphics.color.PDIndexed;
/*     */ import org.apache.pdfbox.pdmodel.graphics.color.PDSeparation;
/*     */ import org.apache.pdfbox.preflight.PreflightContext;
/*     */ import org.apache.pdfbox.preflight.PreflightDocument;
/*     */ import org.apache.pdfbox.preflight.PreflightPath;
/*     */ import org.apache.pdfbox.preflight.ValidationResult;
/*     */ import org.apache.pdfbox.preflight.ValidationResult.ValidationError;
/*     */ import org.apache.pdfbox.preflight.exception.ValidationException;
/*     */ 
/*     */ public class StandardColorSpaceHelper
/*     */   implements ColorSpaceHelper
/*     */ {
/*  62 */   protected PreflightContext context = null;
/*     */ 
/*  66 */   protected ICCProfileWrapper iccpw = null;
/*     */ 
/*  70 */   protected PDColorSpace pdcs = null;
/*     */ 
/*     */   protected StandardColorSpaceHelper(PreflightContext _context, PDColorSpace _cs)
/*     */   {
/*  74 */     this.context = _context;
/*  75 */     this.pdcs = _cs;
/*     */   }
/*     */ 
/*     */   public final void validate()
/*     */     throws ValidationException
/*     */   {
/*  85 */     if (this.pdcs == null)
/*     */     {
/*  87 */       throw new ValidationException("Unable to create a PDColorSpace with the value null");
/*     */     }
/*     */ 
/*  90 */     this.iccpw = ICCProfileWrapper.getOrSearchICCProfile(this.context);
/*  91 */     processAllColorSpace(this.pdcs);
/*     */   }
/*     */ 
/*     */   protected final void processAllColorSpace(PDColorSpace pdcs)
/*     */   {
/* 105 */     ColorSpaces cs = ColorSpaces.valueOf(pdcs.getName());
/*     */ 
/* 107 */     switch (1.$SwitchMap$org$apache$pdfbox$preflight$graphic$ColorSpaces[cs.ordinal()])
/*     */     {
/*     */     case 1:
/*     */     case 2:
/* 111 */       processRGBColorSpace(pdcs);
/* 112 */       break;
/*     */     case 3:
/*     */     case 4:
/* 115 */       processCYMKColorSpace(pdcs);
/* 116 */       break;
/*     */     case 5:
/*     */     case 6:
/*     */     case 7:
/* 120 */       processCalibratedColorSpace(pdcs);
/* 121 */       break;
/*     */     case 8:
/*     */     case 9:
/* 124 */       processGrayColorSpace(pdcs);
/* 125 */       break;
/*     */     case 10:
/* 127 */       processICCBasedColorSpace(pdcs);
/* 128 */       break;
/*     */     case 11:
/* 130 */       processDeviceNColorSpace(pdcs);
/* 131 */       break;
/*     */     case 12:
/*     */     case 13:
/* 134 */       processIndexedColorSpace(pdcs);
/* 135 */       break;
/*     */     case 14:
/* 137 */       processSeparationColorSpace(pdcs);
/* 138 */       break;
/*     */     case 15:
/* 140 */       processPatternColorSpace(pdcs);
/* 141 */       break;
/*     */     default:
/* 143 */       this.context.addValidationError(new ValidationResult.ValidationError("2.4.4", cs.getLabel() + " is unknown as ColorSpace"));
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void processRGBColorSpace(PDColorSpace pdcs)
/*     */   {
/* 154 */     if (!processDefaultColorSpace(pdcs))
/*     */     {
/* 156 */       if (this.iccpw == null)
/*     */       {
/* 158 */         this.context.addValidationError(new ValidationResult.ValidationError("2.4.3", "DestOutputProfile is missing"));
/*     */       }
/* 161 */       else if (!this.iccpw.isRGBColorSpace())
/*     */       {
/* 163 */         this.context.addValidationError(new ValidationResult.ValidationError("2.4.1", "DestOutputProfile isn't RGB ColorSpace"));
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void processCYMKColorSpace(PDColorSpace pdcs)
/*     */   {
/* 175 */     if (!processDefaultColorSpace(pdcs))
/*     */     {
/* 177 */       if (this.iccpw == null)
/*     */       {
/* 179 */         this.context.addValidationError(new ValidationResult.ValidationError("2.4.3", "DestOutputProfile is missing"));
/*     */       }
/* 182 */       else if (!this.iccpw.isCMYKColorSpace())
/*     */       {
/* 184 */         this.context.addValidationError(new ValidationResult.ValidationError("2.4.2", "DestOutputProfile isn't CMYK ColorSpace"));
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void processPatternColorSpace(PDColorSpace pdcs)
/*     */   {
/* 195 */     if (this.iccpw == null)
/*     */     {
/* 197 */       this.context.addValidationError(new ValidationResult.ValidationError("2.4.3", "DestOutputProfile is missing"));
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void processGrayColorSpace(PDColorSpace pdcs)
/*     */   {
/* 208 */     if ((!processDefaultColorSpace(pdcs)) && (this.iccpw == null))
/*     */     {
/* 210 */       this.context.addValidationError(new ValidationResult.ValidationError("2.4.3", "DestOutputProfile is missing"));
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void processCalibratedColorSpace(PDColorSpace pdcs)
/*     */   {
/*     */   }
/*     */ 
/*     */   protected void processICCBasedColorSpace(PDColorSpace pdcs)
/*     */   {
/* 234 */     PDICCBased iccBased = (PDICCBased)pdcs;
/*     */     try
/*     */     {
/* 237 */       ICC_Profile iccp = ICC_Profile.getInstance(iccBased.getPDStream().getByteArray());
/* 238 */       if (iccp == null)
/*     */       {
/* 240 */         this.context.addValidationError(new ValidationResult.ValidationError("2.4.11", "Unable to read ICCBase color space "));
/*     */ 
/* 242 */         return;
/*     */       }
/* 244 */       List altCs = iccBased.getAlternateColorSpaces();
/* 245 */       for (PDColorSpace altpdcs : altCs)
/*     */       {
/* 247 */         if (altpdcs != null)
/*     */         {
/* 250 */           ColorSpaces altCsId = ColorSpaces.valueOf(altpdcs.getName());
/* 251 */           if (altCsId == ColorSpaces.Pattern)
/*     */           {
/* 253 */             this.context.addValidationError(new ValidationResult.ValidationError("2.4.5", "Pattern is forbidden as AlternateColorSpace of a ICCBased"));
/*     */ 
/* 256 */             return;
/*     */           }
/*     */ 
/*     */         }
/*     */ 
/*     */       }
/*     */ 
/*     */     }
/*     */     catch (IllegalArgumentException e)
/*     */     {
/* 273 */       this.context.addValidationError(new ValidationResult.ValidationError("2.4.11", "ICCBase color space is invalid. Caused By: " + e.getMessage()));
/*     */     }
/*     */     catch (IOException e)
/*     */     {
/* 278 */       this.context.addValidationError(new ValidationResult.ValidationError("2.4", "Unable to read ICCBase color space. Caused by : " + e.getMessage()));
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void processDeviceNColorSpace(PDColorSpace pdcs)
/*     */   {
/* 293 */     PDDeviceN deviceN = (PDDeviceN)pdcs;
/*     */     try
/*     */     {
/* 296 */       if (this.iccpw == null)
/*     */       {
/* 298 */         this.context.addValidationError(new ValidationResult.ValidationError("2.4.3", "DestOutputProfile is missing"));
/*     */ 
/* 300 */         return;
/*     */       }
/*     */ 
/* 303 */       PDColorSpace altColor = deviceN.getAlternateColorSpace();
/* 304 */       if (altColor != null)
/*     */       {
/* 306 */         processAllColorSpace(altColor);
/*     */       }
/*     */ 
/* 309 */       int numberOfColorants = 0;
/* 310 */       PDDeviceNAttributes attr = deviceN.getAttributes();
/*     */       Iterator i$;
/* 311 */       if (attr != null)
/*     */       {
/* 313 */         Map colorants = attr.getColorants();
/* 314 */         if (colorants != null)
/*     */         {
/* 316 */           numberOfColorants = colorants.size();
/* 317 */           for (i$ = colorants.values().iterator(); i$.hasNext(); ) { Object col = i$.next();
/*     */ 
/* 319 */             if (col != null)
/*     */             {
/* 321 */               processAllColorSpace((PDColorSpace)col);
/*     */             }
/*     */           }
/*     */         }
/*     */       }
/* 326 */       int numberOfComponents = deviceN.getNumberOfComponents();
/* 327 */       if ((numberOfColorants > 8) || (numberOfComponents > 8))
/*     */       {
/* 329 */         this.context.addValidationError(new ValidationResult.ValidationError("2.4.10", "DeviceN has too many tint components or colorants"));
/*     */       }
/*     */ 
/*     */     }
/*     */     catch (IOException e)
/*     */     {
/* 336 */       this.context.addValidationError(new ValidationResult.ValidationError("2.4", "Unable to read DeviceN color space : " + e.getMessage()));
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void processIndexedColorSpace(PDColorSpace pdcs)
/*     */   {
/* 351 */     PDIndexed indexed = (PDIndexed)pdcs;
/*     */     try
/*     */     {
/* 354 */       PDColorSpace based = indexed.getBaseColorSpace();
/* 355 */       ColorSpaces cs = ColorSpaces.valueOf(based.getName());
/* 356 */       if ((cs == ColorSpaces.Indexed) || (cs == ColorSpaces.Indexed_SHORT))
/*     */       {
/* 358 */         this.context.addValidationError(new ValidationResult.ValidationError("2.4.8", "Indexed color space can't be used as Base color space"));
/*     */ 
/* 360 */         return;
/*     */       }
/* 362 */       if (cs == ColorSpaces.Pattern)
/*     */       {
/* 364 */         this.context.addValidationError(new ValidationResult.ValidationError("2.4.8", "Pattern color space can't be used as Base color space"));
/*     */ 
/* 366 */         return;
/*     */       }
/* 368 */       processAllColorSpace(based);
/*     */     }
/*     */     catch (IOException e)
/*     */     {
/* 372 */       this.context.addValidationError(new ValidationResult.ValidationError("2.4", "Unable to read Indexed color space : " + e.getMessage()));
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void processSeparationColorSpace(PDColorSpace pdcs)
/*     */   {
/* 387 */     PDSeparation separation = (PDSeparation)pdcs;
/*     */     try
/*     */     {
/* 391 */       PDColorSpace altCol = separation.getAlternateColorSpace();
/* 392 */       if (altCol != null)
/*     */       {
/* 394 */         ColorSpaces acs = ColorSpaces.valueOf(altCol.getName());
/* 395 */         switch (1.$SwitchMap$org$apache$pdfbox$preflight$graphic$ColorSpaces[acs.ordinal()])
/*     */         {
/*     */         case 11:
/*     */         case 12:
/*     */         case 13:
/*     */         case 14:
/*     */         case 15:
/* 402 */           this.context.addValidationError(new ValidationResult.ValidationError("2.4.7", acs.getLabel() + " color space can't be used as alternate color space"));
/*     */ 
/* 404 */           break;
/*     */         default:
/* 406 */           processAllColorSpace(altCol);
/*     */         }
/*     */       }
/*     */     }
/*     */     catch (IOException e)
/*     */     {
/* 412 */       this.context.addValidationError(new ValidationResult.ValidationError("2.4", "Unable to read Separation color space : " + e.getMessage()));
/*     */     }
/*     */   }
/*     */ 
/*     */   protected boolean processDefaultColorSpace(PDColorSpace pdcs)
/*     */   {
/* 426 */     boolean result = false;
/*     */ 
/* 429 */     PreflightPath vPath = this.context.getValidationPath();
/* 430 */     PDResources resources = (PDResources)vPath.getClosestPathElement(PDResources.class);
/* 431 */     if ((resources != null) && (resources.getColorSpaces() != null))
/*     */     {
/* 433 */       PDColorSpace defaultCS = null;
/*     */ 
/* 435 */       Map colorsSpaces = resources.getColorSpaces();
/* 436 */       if (pdcs.getName().equals(ColorSpaces.DeviceCMYK.getLabel()))
/*     */       {
/* 438 */         defaultCS = (PDColorSpace)colorsSpaces.get("DefaultCMYK");
/*     */       }
/* 440 */       else if (pdcs.getName().equals(ColorSpaces.DeviceRGB.getLabel()))
/*     */       {
/* 442 */         defaultCS = (PDColorSpace)colorsSpaces.get("DefaultRGB");
/*     */       }
/* 444 */       else if (pdcs.getName().equals(ColorSpaces.DeviceGray.getLabel()))
/*     */       {
/* 446 */         defaultCS = (PDColorSpace)colorsSpaces.get("DefaultGray");
/*     */       }
/*     */ 
/* 449 */       if (defaultCS != null)
/*     */       {
/* 452 */         int nbOfErrors = this.context.getDocument().getResult().getErrorsList().size();
/* 453 */         processAllColorSpace(defaultCS);
/* 454 */         int newNbOfErrors = this.context.getDocument().getResult().getErrorsList().size();
/* 455 */         result = nbOfErrors == newNbOfErrors;
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 460 */     return result;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.preflight.graphic.StandardColorSpaceHelper
 * JD-Core Version:    0.6.2
 */