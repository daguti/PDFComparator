/*     */ package org.apache.pdfbox.pdmodel.interactive.documentnavigation.destination;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import org.apache.pdfbox.cos.COSArray;
/*     */ import org.apache.pdfbox.cos.COSBase;
/*     */ import org.apache.pdfbox.cos.COSName;
/*     */ import org.apache.pdfbox.cos.COSString;
/*     */ import org.apache.pdfbox.pdmodel.common.PDDestinationOrAction;
/*     */ 
/*     */ public abstract class PDDestination
/*     */   implements PDDestinationOrAction
/*     */ {
/*     */   public static PDDestination create(COSBase base)
/*     */     throws IOException
/*     */   {
/*  49 */     PDDestination retval = null;
/*  50 */     if (base != null)
/*     */     {
/*  54 */       if (((base instanceof COSArray)) && (((COSArray)base).size() > 0))
/*     */       {
/*  56 */         COSArray array = (COSArray)base;
/*  57 */         COSName type = (COSName)array.getObject(1);
/*  58 */         String typeString = type.getName();
/*  59 */         if ((typeString.equals("Fit")) || (typeString.equals("FitB")))
/*     */         {
/*  62 */           retval = new PDPageFitDestination(array);
/*     */         }
/*  64 */         else if ((typeString.equals("FitV")) || (typeString.equals("FitBV")))
/*     */         {
/*  67 */           retval = new PDPageFitHeightDestination(array);
/*     */         }
/*  69 */         else if (typeString.equals("FitR"))
/*     */         {
/*  71 */           retval = new PDPageFitRectangleDestination(array);
/*     */         }
/*  73 */         else if ((typeString.equals("FitH")) || (typeString.equals("FitBH")))
/*     */         {
/*  76 */           retval = new PDPageFitWidthDestination(array);
/*     */         }
/*  78 */         else if (typeString.equals("XYZ"))
/*     */         {
/*  80 */           retval = new PDPageXYZDestination(array);
/*     */         }
/*     */         else
/*     */         {
/*  84 */           throw new IOException("Unknown destination type:" + type);
/*     */         }
/*     */       }
/*  87 */       else if ((base instanceof COSString))
/*     */       {
/*  89 */         retval = new PDNamedDestination((COSString)base);
/*     */       }
/*  91 */       else if ((base instanceof COSName))
/*     */       {
/*  93 */         retval = new PDNamedDestination((COSName)base);
/*     */       }
/*     */       else
/*     */       {
/*  97 */         throw new IOException("Error: can't convert to Destination " + base);
/*     */       }
/*     */     }
/*  99 */     return retval;
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 109 */     return super.toString();
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.pdmodel.interactive.documentnavigation.destination.PDDestination
 * JD-Core Version:    0.6.2
 */