/*     */ package org.apache.pdfbox.preflight.process.reflect;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import org.apache.pdfbox.cos.COSBase;
/*     */ import org.apache.pdfbox.cos.COSDictionary;
/*     */ import org.apache.pdfbox.cos.COSDocument;
/*     */ import org.apache.pdfbox.cos.COSName;
/*     */ import org.apache.pdfbox.preflight.PreflightContext;
/*     */ import org.apache.pdfbox.preflight.PreflightDocument;
/*     */ import org.apache.pdfbox.preflight.PreflightPath;
/*     */ import org.apache.pdfbox.preflight.ValidationResult.ValidationError;
/*     */ import org.apache.pdfbox.preflight.exception.ValidationException;
/*     */ import org.apache.pdfbox.preflight.process.AbstractProcess;
/*     */ import org.apache.pdfbox.preflight.utils.COSUtils;
/*     */ 
/*     */ public class ExtGStateValidationProcess extends AbstractProcess
/*     */ {
/*     */   public void validate(PreflightContext context)
/*     */     throws ValidationException
/*     */   {
/*  57 */     PreflightPath vPath = context.getValidationPath();
/*  58 */     if (vPath.isEmpty()) {
/*  59 */       return;
/*     */     }
/*  61 */     if (!vPath.isExpectedType(COSDictionary.class))
/*     */     {
/*  63 */       context.addValidationError(new ValidationResult.ValidationError("2.1.10", "ExtGState validation required at least a Resource dictionary"));
/*     */     }
/*     */     else
/*     */     {
/*  67 */       COSDictionary extGStatesDict = (COSDictionary)vPath.peek();
/*  68 */       List listOfExtGState = extractExtGStateDictionaries(context, extGStatesDict);
/*  69 */       validateTransparencyRules(context, listOfExtGState);
/*     */     }
/*     */   }
/*     */ 
/*     */   public List<COSDictionary> extractExtGStateDictionaries(PreflightContext context, COSDictionary egsEntry)
/*     */     throws ValidationException
/*     */   {
/*  86 */     List listOfExtGState = new ArrayList(0);
/*  87 */     COSDocument cosDocument = context.getDocument().getDocument();
/*  88 */     COSDictionary extGStates = COSUtils.getAsDictionary(egsEntry, cosDocument);
/*     */ 
/*  90 */     if (extGStates != null)
/*     */     {
/*  92 */       for (Object object : extGStates.keySet())
/*     */       {
/*  94 */         COSName key = (COSName)object;
/*  95 */         if (key.getName().matches("(GS|gs)([0-9])+"))
/*     */         {
/*  97 */           COSBase gsBase = extGStates.getItem(key);
/*  98 */           COSDictionary gsDict = COSUtils.getAsDictionary(gsBase, cosDocument);
/*  99 */           if (gsDict == null)
/*     */           {
/* 101 */             throw new ValidationException("The Extended Graphics State dictionary is invalid");
/*     */           }
/* 103 */           listOfExtGState.add(gsDict);
/*     */         }
/*     */       }
/*     */     }
/* 107 */     return listOfExtGState;
/*     */   }
/*     */ 
/*     */   protected void validateTransparencyRules(PreflightContext context, List<COSDictionary> listOfExtGState)
/*     */   {
/* 116 */     for (COSDictionary egs : listOfExtGState)
/*     */     {
/* 118 */       checkSoftMask(context, egs);
/* 119 */       checkCA(context, egs);
/* 120 */       checkBlendMode(context, egs);
/* 121 */       checkTRKey(context, egs);
/* 122 */       checkTR2Key(context, egs);
/*     */     }
/*     */   }
/*     */ 
/*     */   private void checkSoftMask(PreflightContext context, COSDictionary egs)
/*     */   {
/* 135 */     COSBase smVal = egs.getItem(COSName.SMASK);
/* 136 */     if (smVal != null)
/*     */     {
/* 139 */       if ((!(smVal instanceof COSName)) || (!"None".equals(((COSName)smVal).getName())))
/*     */       {
/* 142 */         context.addValidationError(new ValidationResult.ValidationError("4.1.1", "SoftMask must be null or None"));
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   private void checkBlendMode(PreflightContext context, COSDictionary egs)
/*     */   {
/* 157 */     COSBase bmVal = egs.getItem("BM");
/* 158 */     if (bmVal != null)
/*     */     {
/* 161 */       if ((!(bmVal instanceof COSName)) || ((!"Normal".equals(((COSName)bmVal).getName())) && (!"Compatible".equals(((COSName)bmVal).getName()))))
/*     */       {
/* 164 */         context.addValidationError(new ValidationResult.ValidationError("4.1.3", "BlendMode value isn't valid (only Normal and Compatible are authorized)"));
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   private void checkCA(PreflightContext context, COSDictionary egs)
/*     */   {
/* 179 */     COSBase uCA = egs.getItem("CA");
/* 180 */     COSBase lCA = egs.getItem("ca");
/* 181 */     COSDocument cosDocument = context.getDocument().getDocument();
/*     */ 
/* 183 */     if (uCA != null)
/*     */     {
/* 186 */       Float fca = COSUtils.getAsFloat(uCA, cosDocument);
/* 187 */       Integer ica = COSUtils.getAsInteger(uCA, cosDocument);
/* 188 */       if (((fca == null) || (fca.floatValue() != 1.0F)) && ((ica == null) || (ica.intValue() != 1)))
/*     */       {
/* 190 */         context.addValidationError(new ValidationResult.ValidationError("4.1.2", "CA entry in a ExtGState is invalid"));
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 195 */     if (lCA != null)
/*     */     {
/* 198 */       Float fca = COSUtils.getAsFloat(lCA, cosDocument);
/* 199 */       Integer ica = COSUtils.getAsInteger(lCA, cosDocument);
/* 200 */       if (((fca == null) || (fca.floatValue() != 1.0F)) && ((ica == null) || (ica.intValue() != 1)))
/*     */       {
/* 202 */         context.addValidationError(new ValidationResult.ValidationError("4.1.2", "ca entry in a ExtGState  is invalid."));
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void checkTRKey(PreflightContext context, COSDictionary egs)
/*     */   {
/* 216 */     if (egs.getItem("TR") != null)
/*     */     {
/* 218 */       context.addValidationError(new ValidationResult.ValidationError("2.3", "No TR key expected in Extended graphics state"));
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void checkTR2Key(PreflightContext context, COSDictionary egs)
/*     */   {
/* 231 */     if (egs.getItem("TR2") != null)
/*     */     {
/* 233 */       String s = egs.getNameAsString("TR2");
/* 234 */       if (!"Default".equals(s))
/*     */       {
/* 236 */         context.addValidationError(new ValidationResult.ValidationError("2.3.2", "TR2 key only expect 'Default' value, not '" + s + "'"));
/*     */       }
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.preflight.process.reflect.ExtGStateValidationProcess
 * JD-Core Version:    0.6.2
 */