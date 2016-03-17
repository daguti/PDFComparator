/*     */ package org.apache.pdfbox.preflight.process;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Set;
/*     */ import org.apache.pdfbox.cos.COSArray;
/*     */ import org.apache.pdfbox.cos.COSBase;
/*     */ import org.apache.pdfbox.cos.COSDictionary;
/*     */ import org.apache.pdfbox.cos.COSDocument;
/*     */ import org.apache.pdfbox.cos.COSName;
/*     */ import org.apache.pdfbox.cos.COSObject;
/*     */ import org.apache.pdfbox.cos.COSString;
/*     */ import org.apache.pdfbox.pdfparser.XrefTrailerResolver;
/*     */ import org.apache.pdfbox.pdmodel.PDDocument;
/*     */ import org.apache.pdfbox.persistence.util.COSObjectKey;
/*     */ import org.apache.pdfbox.preflight.PreflightContext;
/*     */ import org.apache.pdfbox.preflight.PreflightDocument;
/*     */ import org.apache.pdfbox.preflight.ValidationResult.ValidationError;
/*     */ import org.apache.pdfbox.preflight.exception.ValidationException;
/*     */ import org.apache.pdfbox.preflight.utils.COSUtils;
/*     */ 
/*     */ public class TrailerValidationProcess extends AbstractProcess
/*     */ {
/*     */   public void validate(PreflightContext ctx)
/*     */     throws ValidationException
/*     */   {
/*  62 */     PDDocument pdfDoc = ctx.getDocument();
/*     */ 
/*  64 */     COSDictionary linearizedDict = getLinearizedDictionary(pdfDoc);
/*  65 */     if (linearizedDict != null)
/*     */     {
/*  68 */       checkLinearizedDictionnary(ctx, linearizedDict);
/*     */ 
/*  74 */       String pdfVersion = pdfDoc.getDocument().getHeaderString();
/*  75 */       if ((pdfVersion != null) && (pdfVersion.matches("%PDF-1\\.[1-4]")))
/*     */       {
/*  77 */         checkTrailersForLinearizedPDF14(ctx);
/*     */       }
/*     */       else
/*     */       {
/*  81 */         checkTrailersForLinearizedPDF15(ctx);
/*     */       }
/*     */ 
/*     */     }
/*     */     else
/*     */     {
/*  87 */       checkMainTrailer(ctx, pdfDoc.getDocument().getTrailer());
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void checkTrailersForLinearizedPDF14(PreflightContext ctx)
/*     */   {
/*  99 */     COSDictionary first = ctx.getXrefTableResolver().getFirstTrailer();
/* 100 */     if (first == null)
/*     */     {
/* 102 */       addValidationError(ctx, new ValidationResult.ValidationError("1.4", "There are no trailer in the PDF file"));
/*     */     }
/*     */     else
/*     */     {
/* 106 */       COSDictionary last = ctx.getXrefTableResolver().getLastTrailer();
/* 107 */       COSDocument cosDoc = new COSDocument();
/* 108 */       checkMainTrailer(ctx, first);
/* 109 */       if (!compareIds(first, last, cosDoc))
/*     */       {
/* 111 */         addValidationError(ctx, new ValidationResult.ValidationError("1.4.6", "ID is different in the first and the last trailer"));
/*     */       }
/*     */ 
/* 114 */       COSUtils.closeDocumentQuietly(cosDoc);
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void checkTrailersForLinearizedPDF15(PreflightContext ctx)
/*     */   {
/* 126 */     PDDocument pdfDoc = ctx.getDocument();
/*     */     try
/*     */     {
/* 129 */       COSDocument cosDocument = pdfDoc.getDocument();
/* 130 */       List xrefs = cosDocument.getObjectsByType(COSName.XREF);
/*     */ 
/* 132 */       if (xrefs.isEmpty())
/*     */       {
/* 135 */         checkTrailersForLinearizedPDF14(ctx);
/*     */       }
/*     */       else
/*     */       {
/* 139 */         long min = 9223372036854775807L;
/* 140 */         long max = -9223372036854775808L;
/* 141 */         COSDictionary firstTrailer = null;
/* 142 */         COSDictionary lastTrailer = null;
/*     */ 
/* 145 */         for (COSObject co : xrefs)
/*     */         {
/* 147 */           long offset = ((Long)cosDocument.getXrefTable().get(new COSObjectKey(co))).longValue();
/* 148 */           if (offset < min)
/*     */           {
/* 150 */             min = offset;
/* 151 */             firstTrailer = (COSDictionary)co.getObject();
/*     */           }
/*     */ 
/* 154 */           if (offset > max)
/*     */           {
/* 156 */             max = offset;
/* 157 */             lastTrailer = (COSDictionary)co.getObject();
/*     */           }
/*     */ 
/*     */         }
/*     */ 
/* 162 */         checkMainTrailer(ctx, firstTrailer);
/* 163 */         if (!compareIds(firstTrailer, lastTrailer, cosDocument))
/*     */         {
/* 165 */           addValidationError(ctx, new ValidationResult.ValidationError("1.4.6", "ID is different in the first and the last trailer"));
/*     */         }
/*     */       }
/*     */ 
/*     */     }
/*     */     catch (IOException e)
/*     */     {
/* 172 */       addValidationError(ctx, new ValidationResult.ValidationError("1.4", "Unable to check PDF Trailers due to : " + e.getMessage()));
/*     */     }
/*     */   }
/*     */ 
/*     */   protected boolean compareIds(COSDictionary first, COSDictionary last, COSDocument cosDocument)
/*     */   {
/* 187 */     COSBase idFirst = first.getItem(COSName.getPDFName("ID"));
/* 188 */     COSBase idLast = last.getItem(COSName.getPDFName("ID"));
/*     */ 
/* 191 */     if ((idFirst != null) && (idLast != null))
/*     */     {
/* 195 */       COSArray af = COSUtils.getAsArray(idFirst, cosDocument);
/* 196 */       COSArray al = COSUtils.getAsArray(idLast, cosDocument);
/*     */ 
/* 199 */       if ((af == null) || (al == null))
/*     */       {
/* 201 */         return false;
/*     */       }
/*     */ 
/* 205 */       boolean isEqual = true;
/* 206 */       for (Iterator i$ = af.toList().iterator(); i$.hasNext(); ) { Object of = i$.next();
/*     */ 
/* 208 */         boolean oneIsEquals = false;
/* 209 */         for (Iterator i$ = al.toList().iterator(); i$.hasNext(); ) { Object ol = i$.next();
/*     */ 
/* 213 */           if (oneIsEquals)
/*     */             break;
/* 215 */           oneIsEquals = ((COSString)ol).getString().equals(((COSString)of).getString());
/*     */         }
/*     */ 
/* 222 */         isEqual = (isEqual) && (oneIsEquals);
/* 223 */         if (!isEqual)
/*     */         {
/*     */           break;
/*     */         }
/*     */       }
/* 228 */       return isEqual;
/*     */     }
/*     */ 
/* 232 */     return true;
/*     */   }
/*     */ 
/*     */   protected void checkMainTrailer(PreflightContext ctx, COSDictionary trailer)
/*     */   {
/* 244 */     boolean id = false;
/* 245 */     boolean root = false;
/* 246 */     boolean size = false;
/* 247 */     boolean prev = false;
/* 248 */     boolean info = false;
/* 249 */     boolean encrypt = false;
/*     */ 
/* 251 */     for (Object key : trailer.keySet())
/*     */     {
/* 253 */       if (!(key instanceof COSName))
/*     */       {
/* 255 */         addValidationError(ctx, new ValidationResult.ValidationError("1.0.7", "Invalid key in The trailer dictionary"));
/*     */ 
/* 257 */         return;
/*     */       }
/*     */ 
/* 260 */       String cosName = ((COSName)key).getName();
/* 261 */       if (cosName.equals("Encrypt"))
/*     */       {
/* 263 */         encrypt = true;
/*     */       }
/* 265 */       if (cosName.equals("Size"))
/*     */       {
/* 267 */         size = true;
/*     */       }
/* 269 */       if (cosName.equals("Prev"))
/*     */       {
/* 271 */         prev = true;
/*     */       }
/* 273 */       if (cosName.equals("Root"))
/*     */       {
/* 275 */         root = true;
/*     */       }
/* 277 */       if (cosName.equals("Info"))
/*     */       {
/* 279 */         info = true;
/*     */       }
/* 281 */       if (cosName.equals("ID"))
/*     */       {
/* 283 */         id = true;
/*     */       }
/*     */     }
/*     */ 
/* 287 */     COSDocument cosDocument = ctx.getDocument().getDocument();
/*     */ 
/* 289 */     if (!id)
/*     */     {
/* 291 */       addValidationError(ctx, new ValidationResult.ValidationError("1.4.1", "The trailer dictionary doesn't contain ID"));
/*     */     }
/*     */     else
/*     */     {
/* 296 */       COSBase trailerId = trailer.getItem("ID");
/* 297 */       if (!COSUtils.isArray(trailerId, cosDocument))
/*     */       {
/* 299 */         addValidationError(ctx, new ValidationResult.ValidationError("1.4.3", "The trailer dictionary contains an id but it isn't an array"));
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 304 */     if (encrypt)
/*     */     {
/* 306 */       addValidationError(ctx, new ValidationResult.ValidationError("1.4.2", "The trailer dictionary contains Encrypt"));
/*     */     }
/*     */ 
/* 310 */     if (!size)
/*     */     {
/* 312 */       addValidationError(ctx, new ValidationResult.ValidationError("1.4.4", "The trailer dictionary doesn't contain Size"));
/*     */     }
/*     */     else
/*     */     {
/* 317 */       COSBase trailerSize = trailer.getItem("Size");
/* 318 */       if (!COSUtils.isInteger(trailerSize, cosDocument))
/*     */       {
/* 320 */         addValidationError(ctx, new ValidationResult.ValidationError("1.4.3", "The trailer dictionary contains a size but it isn't an integer"));
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 326 */     if (!root)
/*     */     {
/* 328 */       addValidationError(ctx, new ValidationResult.ValidationError("1.4.5", "The trailer dictionary doesn't contain Root"));
/*     */     }
/*     */     else
/*     */     {
/* 333 */       COSBase trailerRoot = trailer.getItem("Root");
/* 334 */       if (!COSUtils.isDictionary(trailerRoot, cosDocument))
/*     */       {
/* 336 */         addValidationError(ctx, new ValidationResult.ValidationError("1.4.3", "The trailer dictionary contains a root but it isn't a dictionary"));
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 341 */     if (prev)
/*     */     {
/* 343 */       COSBase trailerPrev = trailer.getItem("Prev");
/* 344 */       if (!COSUtils.isInteger(trailerPrev, cosDocument))
/*     */       {
/* 346 */         addValidationError(ctx, new ValidationResult.ValidationError("1.4.3", "The trailer dictionary contains a prev but it isn't an integer"));
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 351 */     if (info)
/*     */     {
/* 353 */       COSBase trailerInfo = trailer.getItem("Info");
/* 354 */       if (!COSUtils.isDictionary(trailerInfo, cosDocument))
/*     */       {
/* 356 */         addValidationError(ctx, new ValidationResult.ValidationError("1.4.3", "The trailer dictionary contains an info but it isn't a dictionary"));
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   protected COSDictionary getLinearizedDictionary(PDDocument document)
/*     */   {
/* 372 */     COSDocument cDoc = document.getDocument();
/* 373 */     List lObj = cDoc.getObjects();
/* 374 */     for (Iterator i$ = lObj.iterator(); i$.hasNext(); ) { Object object = i$.next();
/*     */ 
/* 376 */       COSBase curObj = ((COSObject)object).getObject();
/* 377 */       if (((curObj instanceof COSDictionary)) && (((COSDictionary)curObj).keySet().contains(COSName.getPDFName("Linearized"))))
/*     */       {
/* 380 */         return (COSDictionary)curObj;
/*     */       }
/*     */     }
/* 383 */     return null;
/*     */   }
/*     */ 
/*     */   protected void checkLinearizedDictionnary(PreflightContext ctx, COSDictionary linearizedDict)
/*     */   {
/* 396 */     boolean l = false;
/* 397 */     boolean h = false;
/* 398 */     boolean o = false;
/* 399 */     boolean e = false;
/* 400 */     boolean n = false;
/* 401 */     boolean t = false;
/*     */ 
/* 403 */     for (Object key : linearizedDict.keySet())
/*     */     {
/* 405 */       if (!(key instanceof COSName))
/*     */       {
/* 407 */         addValidationError(ctx, new ValidationResult.ValidationError("1.0.7", "Invalid key in The Linearized dictionary"));
/*     */ 
/* 409 */         return;
/*     */       }
/*     */ 
/* 412 */       String cosName = ((COSName)key).getName();
/* 413 */       if (cosName.equals("L"))
/*     */       {
/* 415 */         l = true;
/*     */       }
/* 417 */       if (cosName.equals("H"))
/*     */       {
/* 419 */         h = true;
/*     */       }
/* 421 */       if (cosName.equals("O"))
/*     */       {
/* 423 */         o = true;
/*     */       }
/* 425 */       if (cosName.equals("E"))
/*     */       {
/* 427 */         e = true;
/*     */       }
/* 429 */       if (cosName.equals("N"))
/*     */       {
/* 431 */         n = true;
/*     */       }
/* 433 */       if (cosName.equals("T"))
/*     */       {
/* 435 */         t = true;
/*     */       }
/*     */     }
/*     */ 
/* 439 */     if ((!l) || (!h) || (!o) || (!e) || (!t) || (!n))
/*     */     {
/* 441 */       addValidationError(ctx, new ValidationResult.ValidationError("1.2.3", "Invalid key in The Linearized dictionary"));
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.preflight.process.TrailerValidationProcess
 * JD-Core Version:    0.6.2
 */