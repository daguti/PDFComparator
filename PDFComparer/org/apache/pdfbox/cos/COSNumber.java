/*     */ package org.apache.pdfbox.cos;
/*     */ 
/*     */ import java.io.IOException;
/*     */ 
/*     */ public abstract class COSNumber extends COSBase
/*     */ {
/*     */ 
/*     */   /** @deprecated */
/*  33 */   public static final COSInteger ZERO = COSInteger.ZERO;
/*     */ 
/*     */   /** @deprecated */
/*  38 */   public static final COSInteger ONE = COSInteger.ONE;
/*     */ 
/*     */   public abstract float floatValue();
/*     */ 
/*     */   public abstract double doubleValue();
/*     */ 
/*     */   public abstract int intValue();
/*     */ 
/*     */   public abstract long longValue();
/*     */ 
/*     */   public static COSNumber get(String number)
/*     */     throws IOException
/*     */   {
/*  79 */     if (number.length() == 1)
/*     */     {
/*  81 */       char digit = number.charAt(0);
/*  82 */       if (('0' <= digit) && (digit <= '9'))
/*     */       {
/*  84 */         return COSInteger.get(digit - '0');
/*     */       }
/*  86 */       if ((digit == '-') || (digit == '.'))
/*     */       {
/*  89 */         return COSInteger.ZERO;
/*     */       }
/*     */ 
/*  93 */       throw new IOException("Not a number: " + number);
/*     */     }
/*     */ 
/*  96 */     if ((number.indexOf('.') == -1) && (number.toLowerCase().indexOf('e') == -1))
/*     */     {
/*     */       try
/*     */       {
/* 100 */         return COSInteger.get(Long.parseLong(number));
/*     */       }
/*     */       catch (NumberFormatException e)
/*     */       {
/* 104 */         throw new IOException("Value is not an integer: " + number);
/*     */       }
/*     */ 
/*     */     }
/*     */ 
/* 109 */     return new COSFloat(number);
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.cos.COSNumber
 * JD-Core Version:    0.6.2
 */