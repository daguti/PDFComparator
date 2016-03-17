/*     */ package org.apache.pdfbox.preflight.utils;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import org.apache.commons.logging.Log;
/*     */ import org.apache.commons.logging.LogFactory;
/*     */ import org.apache.pdfbox.cos.COSArray;
/*     */ import org.apache.pdfbox.cos.COSBase;
/*     */ import org.apache.pdfbox.cos.COSDictionary;
/*     */ import org.apache.pdfbox.cos.COSDocument;
/*     */ import org.apache.pdfbox.cos.COSFloat;
/*     */ import org.apache.pdfbox.cos.COSInteger;
/*     */ import org.apache.pdfbox.cos.COSName;
/*     */ import org.apache.pdfbox.cos.COSObject;
/*     */ import org.apache.pdfbox.cos.COSStream;
/*     */ import org.apache.pdfbox.cos.COSString;
/*     */ import org.apache.pdfbox.pdmodel.PDDocument;
/*     */ import org.apache.pdfbox.persistence.util.COSObjectKey;
/*     */ 
/*     */ public class COSUtils
/*     */ {
/*  44 */   public static final Log LOGGER = LogFactory.getLog(COSUtils.class);
/*     */ 
/*     */   public static boolean isDictionary(COSBase elt, COSDocument doc)
/*     */   {
/*  55 */     if ((elt instanceof COSObject))
/*     */     {
/*     */       try
/*     */       {
/*  59 */         COSObjectKey key = new COSObjectKey((COSObject)elt);
/*  60 */         COSObject obj = doc.getObjectFromPool(key);
/*  61 */         return (obj != null) && ((obj.getObject() instanceof COSDictionary));
/*     */       }
/*     */       catch (IOException e)
/*     */       {
/*  65 */         return false;
/*     */       }
/*     */     }
/*  68 */     return elt instanceof COSDictionary;
/*     */   }
/*     */ 
/*     */   public static boolean isString(COSBase elt, COSDocument doc)
/*     */   {
/*  80 */     if ((elt instanceof COSObject))
/*     */     {
/*     */       try
/*     */       {
/*  84 */         COSObjectKey key = new COSObjectKey((COSObject)elt);
/*  85 */         COSObject obj = doc.getObjectFromPool(key);
/*  86 */         return (obj != null) && (((obj.getObject() instanceof COSString)) || ((obj.getObject() instanceof COSName)));
/*     */       }
/*     */       catch (IOException e)
/*     */       {
/*  90 */         return false;
/*     */       }
/*     */     }
/*     */ 
/*  94 */     return ((elt instanceof COSString)) || ((elt instanceof COSName));
/*     */   }
/*     */ 
/*     */   public static boolean isStream(COSBase elt, COSDocument doc)
/*     */   {
/* 106 */     if ((elt instanceof COSObject))
/*     */     {
/*     */       try
/*     */       {
/* 110 */         COSObjectKey key = new COSObjectKey((COSObject)elt);
/* 111 */         COSObject obj = doc.getObjectFromPool(key);
/* 112 */         return (obj != null) && ((obj.getObject() instanceof COSStream));
/*     */       }
/*     */       catch (IOException e)
/*     */       {
/* 116 */         return false;
/*     */       }
/*     */     }
/*     */ 
/* 120 */     return elt instanceof COSStream;
/*     */   }
/*     */ 
/*     */   public static boolean isInteger(COSBase elt, COSDocument doc)
/*     */   {
/* 132 */     if ((elt instanceof COSObject))
/*     */     {
/*     */       try
/*     */       {
/* 136 */         COSObjectKey key = new COSObjectKey((COSObject)elt);
/* 137 */         COSObject obj = doc.getObjectFromPool(key);
/* 138 */         return (obj != null) && ((obj.getObject() instanceof COSInteger));
/*     */       }
/*     */       catch (IOException e)
/*     */       {
/* 142 */         return false;
/*     */       }
/*     */     }
/*     */ 
/* 146 */     return elt instanceof COSInteger;
/*     */   }
/*     */ 
/*     */   public static boolean isNumeric(COSBase elt, COSDocument doc)
/*     */   {
/* 158 */     return (isInteger(elt, doc)) || (isFloat(elt, doc));
/*     */   }
/*     */ 
/*     */   public static boolean isFloat(COSBase elt, COSDocument doc)
/*     */   {
/* 170 */     if ((elt instanceof COSObject))
/*     */     {
/*     */       try
/*     */       {
/* 174 */         COSObjectKey key = new COSObjectKey((COSObject)elt);
/* 175 */         COSObject obj = doc.getObjectFromPool(key);
/* 176 */         return (obj != null) && ((obj.getObject() instanceof COSFloat));
/*     */       }
/*     */       catch (IOException e)
/*     */       {
/* 180 */         return false;
/*     */       }
/*     */     }
/*     */ 
/* 184 */     return elt instanceof COSFloat;
/*     */   }
/*     */ 
/*     */   public static boolean isArray(COSBase elt, COSDocument doc)
/*     */   {
/* 196 */     if ((elt instanceof COSObject))
/*     */     {
/*     */       try
/*     */       {
/* 200 */         COSObjectKey key = new COSObjectKey((COSObject)elt);
/* 201 */         COSObject obj = doc.getObjectFromPool(key);
/* 202 */         return (obj != null) && ((obj.getObject() instanceof COSArray));
/*     */       }
/*     */       catch (IOException e)
/*     */       {
/* 206 */         return false;
/*     */       }
/*     */     }
/*     */ 
/* 210 */     return elt instanceof COSArray;
/*     */   }
/*     */ 
/*     */   public static COSArray getAsArray(COSBase cbase, COSDocument cDoc)
/*     */   {
/* 223 */     if ((cbase instanceof COSObject))
/*     */     {
/*     */       try
/*     */       {
/* 227 */         COSObjectKey key = new COSObjectKey((COSObject)cbase);
/* 228 */         COSObject obj = cDoc.getObjectFromPool(key);
/* 229 */         if ((obj != null) && ((obj.getObject() instanceof COSArray)))
/*     */         {
/* 231 */           return (COSArray)obj.getObject();
/*     */         }
/*     */ 
/* 235 */         return null;
/*     */       }
/*     */       catch (IOException e)
/*     */       {
/* 240 */         return null;
/*     */       }
/*     */     }
/* 243 */     if ((cbase instanceof COSArray))
/*     */     {
/* 245 */       return (COSArray)cbase;
/*     */     }
/*     */ 
/* 249 */     return null;
/*     */   }
/*     */ 
/*     */   public static String getAsString(COSBase cbase, COSDocument cDoc)
/*     */   {
/* 263 */     if ((cbase instanceof COSObject))
/*     */     {
/*     */       try
/*     */       {
/* 267 */         COSObjectKey key = new COSObjectKey((COSObject)cbase);
/* 268 */         COSObject obj = cDoc.getObjectFromPool(key);
/* 269 */         if ((obj != null) && ((obj.getObject() instanceof COSString)))
/*     */         {
/* 271 */           return ((COSString)obj.getObject()).getString();
/*     */         }
/* 273 */         if ((obj != null) && ((obj.getObject() instanceof COSName)))
/*     */         {
/* 275 */           return ((COSName)obj.getObject()).getName();
/*     */         }
/*     */ 
/* 279 */         return null;
/*     */       }
/*     */       catch (IOException e)
/*     */       {
/* 284 */         return null;
/*     */       }
/*     */     }
/* 287 */     if ((cbase instanceof COSString))
/*     */     {
/* 289 */       return ((COSString)cbase).getString();
/*     */     }
/* 291 */     if ((cbase instanceof COSName))
/*     */     {
/* 293 */       return ((COSName)cbase).getName();
/*     */     }
/*     */ 
/* 297 */     return null;
/*     */   }
/*     */ 
/*     */   public static COSDictionary getAsDictionary(COSBase cbase, COSDocument cDoc)
/*     */   {
/* 311 */     if ((cbase instanceof COSObject))
/*     */     {
/*     */       try
/*     */       {
/* 315 */         COSObjectKey key = new COSObjectKey((COSObject)cbase);
/* 316 */         COSObject obj = cDoc.getObjectFromPool(key);
/* 317 */         if ((obj != null) && ((obj.getObject() instanceof COSDictionary)))
/*     */         {
/* 319 */           return (COSDictionary)obj.getObject();
/*     */         }
/*     */ 
/* 323 */         return null;
/*     */       }
/*     */       catch (IOException e)
/*     */       {
/* 328 */         return null;
/*     */       }
/*     */     }
/* 331 */     if ((cbase instanceof COSDictionary))
/*     */     {
/* 333 */       return (COSDictionary)cbase;
/*     */     }
/*     */ 
/* 337 */     return null;
/*     */   }
/*     */ 
/*     */   public static COSStream getAsStream(COSBase cbase, COSDocument cDoc)
/*     */   {
/* 351 */     if ((cbase instanceof COSObject))
/*     */     {
/*     */       try
/*     */       {
/* 355 */         COSObjectKey key = new COSObjectKey((COSObject)cbase);
/* 356 */         COSObject obj = cDoc.getObjectFromPool(key);
/* 357 */         if ((obj != null) && ((obj.getObject() instanceof COSStream)))
/*     */         {
/* 359 */           return (COSStream)obj.getObject();
/*     */         }
/*     */ 
/* 363 */         return null;
/*     */       }
/*     */       catch (IOException e)
/*     */       {
/* 368 */         return null;
/*     */       }
/*     */     }
/* 371 */     if ((cbase instanceof COSStream))
/*     */     {
/* 373 */       return (COSStream)cbase;
/*     */     }
/*     */ 
/* 377 */     return null;
/*     */   }
/*     */ 
/*     */   public static Float getAsFloat(COSBase cbase, COSDocument cDoc)
/*     */   {
/* 391 */     if ((cbase instanceof COSObject))
/*     */     {
/*     */       try
/*     */       {
/* 395 */         COSObjectKey key = new COSObjectKey((COSObject)cbase);
/* 396 */         COSObject obj = cDoc.getObjectFromPool(key);
/* 397 */         if (obj == null)
/*     */         {
/* 399 */           return null;
/*     */         }
/* 401 */         if ((obj.getObject() instanceof COSFloat))
/*     */         {
/* 403 */           return Float.valueOf(((COSFloat)obj.getObject()).floatValue());
/*     */         }
/* 405 */         if ((obj.getObject() instanceof COSInteger))
/*     */         {
/* 407 */           return Float.valueOf(((COSInteger)obj.getObject()).intValue());
/*     */         }
/*     */ 
/* 411 */         return null;
/*     */       }
/*     */       catch (IOException e)
/*     */       {
/* 416 */         return null;
/*     */       }
/*     */     }
/* 419 */     if ((cbase instanceof COSFloat))
/*     */     {
/* 421 */       return Float.valueOf(((COSFloat)cbase).floatValue());
/*     */     }
/* 423 */     if ((cbase instanceof COSInteger))
/*     */     {
/* 425 */       return Float.valueOf(((COSInteger)cbase).intValue());
/*     */     }
/*     */ 
/* 429 */     return null;
/*     */   }
/*     */ 
/*     */   public static Integer getAsInteger(COSBase cbase, COSDocument cDoc)
/*     */   {
/* 443 */     if ((cbase instanceof COSObject))
/*     */     {
/*     */       try
/*     */       {
/* 447 */         COSObjectKey key = new COSObjectKey((COSObject)cbase);
/* 448 */         COSObject obj = cDoc.getObjectFromPool(key);
/* 449 */         if (obj == null)
/*     */         {
/* 451 */           return null;
/*     */         }
/* 453 */         if ((obj.getObject() instanceof COSInteger))
/*     */         {
/* 455 */           return Integer.valueOf(((COSInteger)obj.getObject()).intValue());
/*     */         }
/* 457 */         if ((obj.getObject() instanceof COSFloat))
/*     */         {
/* 459 */           return Integer.valueOf(((COSFloat)obj.getObject()).intValue());
/*     */         }
/*     */ 
/* 463 */         return null;
/*     */       }
/*     */       catch (IOException e)
/*     */       {
/* 468 */         return null;
/*     */       }
/*     */     }
/* 471 */     if ((cbase instanceof COSInteger))
/*     */     {
/* 473 */       return Integer.valueOf(((COSInteger)cbase).intValue());
/*     */     }
/* 475 */     if ((cbase instanceof COSFloat))
/*     */     {
/* 477 */       return Integer.valueOf(((COSFloat)cbase).intValue());
/*     */     }
/*     */ 
/* 481 */     return null;
/*     */   }
/*     */ 
/*     */   public static void closeDocumentQuietly(COSDocument document)
/*     */   {
/*     */     try
/*     */     {
/* 495 */       if (document != null)
/*     */       {
/* 497 */         document.close();
/*     */       }
/*     */     }
/*     */     catch (IOException e)
/*     */     {
/* 502 */       LOGGER.warn("Error occured during the close of a COSDocument : " + e.getMessage());
/*     */     }
/*     */   }
/*     */ 
/*     */   public static void closeDocumentQuietly(PDDocument document)
/*     */   {
/* 514 */     if (document != null)
/*     */     {
/* 516 */       closeDocumentQuietly(document.getDocument());
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.preflight.utils.COSUtils
 * JD-Core Version:    0.6.2
 */