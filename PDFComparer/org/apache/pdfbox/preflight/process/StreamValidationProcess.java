/*     */ package org.apache.pdfbox.preflight.process;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import javax.activation.DataSource;
/*     */ import org.apache.pdfbox.cos.COSArray;
/*     */ import org.apache.pdfbox.cos.COSBase;
/*     */ import org.apache.pdfbox.cos.COSDocument;
/*     */ import org.apache.pdfbox.cos.COSName;
/*     */ import org.apache.pdfbox.cos.COSObject;
/*     */ import org.apache.pdfbox.cos.COSStream;
/*     */ import org.apache.pdfbox.pdmodel.PDDocument;
/*     */ import org.apache.pdfbox.persistence.util.COSObjectKey;
/*     */ import org.apache.pdfbox.preflight.PreflightContext;
/*     */ import org.apache.pdfbox.preflight.PreflightDocument;
/*     */ import org.apache.pdfbox.preflight.ValidationResult.ValidationError;
/*     */ import org.apache.pdfbox.preflight.exception.ValidationException;
/*     */ import org.apache.pdfbox.preflight.utils.COSUtils;
/*     */ import org.apache.pdfbox.preflight.utils.FilterHelper;
/*     */ 
/*     */ public class StreamValidationProcess extends AbstractProcess
/*     */ {
/*     */   public void validate(PreflightContext ctx)
/*     */     throws ValidationException
/*     */   {
/*  54 */     PDDocument pdfDoc = ctx.getDocument();
/*  55 */     COSDocument cDoc = pdfDoc.getDocument();
/*     */ 
/*  57 */     List lCOSObj = cDoc.getObjects();
/*  58 */     for (Iterator i$ = lCOSObj.iterator(); i$.hasNext(); ) { Object o = i$.next();
/*     */ 
/*  60 */       COSObject cObj = (COSObject)o;
/*     */ 
/*  64 */       COSBase cBase = cObj.getObject();
/*  65 */       if ((cBase instanceof COSStream))
/*     */       {
/*  67 */         validateStreamObject(ctx, cObj);
/*     */       } }
/*     */   }
/*     */ 
/*     */   public void validateStreamObject(PreflightContext context, COSObject cObj)
/*     */     throws ValidationException
/*     */   {
/*  74 */     COSStream streamObj = (COSStream)cObj.getObject();
/*     */ 
/*  79 */     checkDictionaryEntries(context, streamObj);
/*     */ 
/*  81 */     checkStreamLength(context, cObj);
/*     */ 
/*  83 */     checkFilters(streamObj, context);
/*     */   }
/*     */ 
/*     */   protected void checkFilters(COSStream stream, PreflightContext context)
/*     */   {
/*  96 */     COSBase bFilter = stream.getDictionaryObject(COSName.FILTER);
/*  97 */     if (bFilter != null)
/*     */     {
/*  99 */       COSDocument cosDocument = context.getDocument().getDocument();
/* 100 */       if (COSUtils.isArray(bFilter, cosDocument))
/*     */       {
/* 102 */         COSArray afName = (COSArray)bFilter;
/* 103 */         for (int i = 0; i < afName.size(); i++)
/*     */         {
/* 105 */           FilterHelper.isAuthorizedFilter(context, afName.getString(i));
/*     */         }
/*     */       }
/* 108 */       else if ((bFilter instanceof COSName))
/*     */       {
/* 110 */         String fName = ((COSName)bFilter).getName();
/* 111 */         FilterHelper.isAuthorizedFilter(context, fName);
/*     */       }
/*     */       else
/*     */       {
/* 116 */         addValidationError(context, new ValidationResult.ValidationError("1.2.7", "Filter should be a Name or an Array"));
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   private boolean readUntilStream(InputStream ra)
/*     */     throws IOException
/*     */   {
/* 125 */     boolean search = true;
/*     */ 
/* 127 */     boolean maybe = false;
/* 128 */     int lastChar = -1;
/*     */     do
/*     */     {
/* 131 */       int c = ra.read();
/* 132 */       switch (c)
/*     */       {
/*     */       case 115:
/* 136 */         maybe = true;
/* 137 */         lastChar = c;
/* 138 */         break;
/*     */       case 116:
/* 141 */         if ((maybe) && (lastChar == 115))
/*     */         {
/* 144 */           lastChar = c;
/*     */         }
/*     */         else
/*     */         {
/* 148 */           maybe = false;
/* 149 */           lastChar = -1;
/*     */         }
/* 151 */         break;
/*     */       case 114:
/* 154 */         if ((maybe) && (lastChar == 116))
/*     */         {
/* 157 */           lastChar = c;
/*     */         }
/*     */         else
/*     */         {
/* 161 */           maybe = false;
/* 162 */           lastChar = -1;
/*     */         }
/* 164 */         break;
/*     */       case 101:
/* 167 */         if ((maybe) && (lastChar == 114))
/*     */         {
/* 169 */           lastChar = c;
/*     */         }
/*     */         else
/*     */         {
/* 174 */           maybe = false;
/*     */         }
/* 176 */         break;
/*     */       case 97:
/* 179 */         if ((maybe) && (lastChar == 101))
/*     */         {
/* 181 */           lastChar = c;
/*     */         }
/*     */         else
/*     */         {
/* 186 */           maybe = false;
/*     */         }
/* 188 */         break;
/*     */       case 109:
/* 191 */         if ((maybe) && (lastChar == 97))
/*     */         {
/* 193 */           return true;
/*     */         }
/*     */ 
/* 197 */         maybe = false;
/*     */ 
/* 199 */         break;
/*     */       case -1:
/* 201 */         search = false;
/* 202 */         break;
/*     */       default:
/* 204 */         maybe = false;
/*     */       }
/*     */     }
/* 207 */     while (search);
/* 208 */     return false;
/*     */   }
/*     */ 
/*     */   protected void checkStreamLength(PreflightContext context, COSObject cObj) throws ValidationException
/*     */   {
/* 213 */     COSStream streamObj = (COSStream)cObj.getObject();
/* 214 */     int length = streamObj.getInt(COSName.LENGTH);
/* 215 */     InputStream ra = null;
/*     */     try
/*     */     {
/* 218 */       ra = context.getSource().getInputStream();
/* 219 */       Long offset = (Long)context.getDocument().getDocument().getXrefTable().get(new COSObjectKey(cObj));
/*     */ 
/* 222 */       long skipped = 0L;
/* 223 */       if (offset != null)
/*     */       {
/* 225 */         while (skipped != offset.longValue())
/*     */         {
/* 227 */           long curSkip = ra.skip(offset.longValue() - skipped);
/* 228 */           if (curSkip < 0L) {
/* 230 */             org.apache.pdfbox.io.IOUtils.closeQuietly(ra);
/* 231 */             addValidationError(context, new ValidationResult.ValidationError("1.2.13", "Unable to skip bytes in the PDFFile to check stream length"));
/*     */             return;
/* 234 */           }skipped += curSkip;
/*     */         }
/*     */ 
/* 238 */         if (readUntilStream(ra))
/*     */         {
/* 240 */           int c = ra.read();
/* 241 */           if (c == 13)
/*     */           {
/* 243 */             ra.read();
/*     */           }
/*     */ 
/* 249 */           byte[] buffer = new byte[1024];
/* 250 */           int nbBytesToRead = length;
/*     */           do
/*     */           {
/* 254 */             int cr = 0;
/* 255 */             if (nbBytesToRead > 1024)
/*     */             {
/* 257 */               cr = ra.read(buffer, 0, 1024);
/*     */             }
/*     */             else
/*     */             {
/* 261 */               cr = ra.read(buffer, 0, nbBytesToRead);
/*     */             }
/* 263 */             if (cr == -1)
/*     */             {
/* 265 */               addValidationError(context, new ValidationResult.ValidationError("1.2.5", "Stream length is invalide"));
/*     */ 
/* 267 */               org.apache.pdfbox.io.IOUtils.closeQuietly(ra);
/*     */               return;
/*     */             }
/* 272 */             nbBytesToRead -= cr;
/*     */           }
/* 274 */           while (nbBytesToRead > 0);
/*     */ 
/* 276 */           int len = "endstream".length() + 2;
/* 277 */           byte[] buffer2 = new byte[len];
/* 278 */           for (int i = 0; i < len; i++)
/*     */           {
/* 280 */             buffer2[i] = ((byte)ra.read());
/*     */           }
/*     */ 
/* 284 */           String endStream = new String(buffer2);
/* 285 */           if ((buffer2[0] == 13) && (buffer2[1] == 10))
/*     */           {
/* 287 */             if (!endStream.contains("endstream"))
/*     */             {
/* 289 */               addValidationError(context, new ValidationResult.ValidationError("1.2.5", "Stream length is invalide"));
/*     */             }
/*     */ 
/*     */           }
/* 293 */           else if ((buffer2[0] == 13) && (buffer2[1] == 101))
/*     */           {
/* 295 */             if (!endStream.contains("endstream"))
/*     */             {
/* 297 */               addValidationError(context, new ValidationResult.ValidationError("1.2.5", "Stream length is invalide"));
/*     */             }
/*     */ 
/*     */           }
/* 301 */           else if ((buffer2[0] == 10) && (buffer2[1] == 101))
/*     */           {
/* 303 */             if (!endStream.contains("endstream"))
/*     */             {
/* 305 */               addValidationError(context, new ValidationResult.ValidationError("1.2.5", "Stream length is invalide"));
/*     */             }
/*     */ 
/*     */           }
/*     */           else
/*     */           {
/* 311 */             addValidationError(context, new ValidationResult.ValidationError("1.2.5", "Stream length is invalide"));
/*     */           }
/*     */ 
/*     */         }
/*     */         else
/*     */         {
/* 318 */           addValidationError(context, new ValidationResult.ValidationError("1.2.5", "Stream length is invalide"));
/*     */         }
/*     */       }
/*     */ 
/*     */     }
/*     */     catch (IOException e)
/*     */     {
/* 325 */       throw new ValidationException("Unable to read a stream to validate it due to : " + e.getMessage(), e);
/*     */     }
/*     */     finally
/*     */     {
/* 329 */       if (ra != null)
/*     */       {
/* 331 */         org.apache.commons.io.IOUtils.closeQuietly(ra);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void checkDictionaryEntries(PreflightContext context, COSStream streamObj)
/*     */   {
/* 345 */     boolean len = streamObj.containsKey(COSName.LENGTH);
/* 346 */     boolean f = streamObj.containsKey(COSName.F);
/* 347 */     boolean ffilter = streamObj.containsKey(COSName.F_FILTER);
/* 348 */     boolean fdecParams = streamObj.containsKey(COSName.F_DECODE_PARMS);
/*     */ 
/* 350 */     if (!len)
/*     */     {
/* 352 */       addValidationError(context, new ValidationResult.ValidationError("1.2.4", "Stream length is missing"));
/*     */     }
/*     */ 
/* 356 */     if ((f) || (ffilter) || (fdecParams))
/*     */     {
/* 358 */       addValidationError(context, new ValidationResult.ValidationError("1.2.6", "F, FFilter or FDecodeParms keys are present in the stream dictionary"));
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.preflight.process.StreamValidationProcess
 * JD-Core Version:    0.6.2
 */