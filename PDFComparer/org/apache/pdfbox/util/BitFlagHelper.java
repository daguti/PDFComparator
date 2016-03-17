/*     */ package org.apache.pdfbox.util;
/*     */ 
/*     */ import org.apache.pdfbox.cos.COSDictionary;
/*     */ import org.apache.pdfbox.cos.COSName;
/*     */ 
/*     */ public class BitFlagHelper
/*     */ {
/*     */   /** @deprecated */
/*     */   public static final void setFlag(COSDictionary dic, String field, int bitFlag, boolean value)
/*     */   {
/*  47 */     setFlag(dic, COSName.getPDFName(field), bitFlag, value);
/*     */   }
/*     */ 
/*     */   public static final void setFlag(COSDictionary dic, COSName field, int bitFlag, boolean value)
/*     */   {
/*  60 */     int currentFlags = dic.getInt(field, 0);
/*  61 */     if (value)
/*     */     {
/*  63 */       currentFlags |= bitFlag;
/*     */     }
/*     */     else
/*     */     {
/*  67 */       currentFlags = currentFlags &= (bitFlag ^ 0xFFFFFFFF);
/*     */     }
/*  69 */     dic.setInt(field, currentFlags);
/*     */   }
/*     */ 
/*     */   /** @deprecated */
/*     */   public static final boolean getFlag(COSDictionary dic, String field, int bitFlag)
/*     */   {
/*  86 */     return getFlag(dic, COSName.getPDFName(field), bitFlag);
/*     */   }
/*     */ 
/*     */   public static final boolean getFlag(COSDictionary dic, COSName field, int bitFlag)
/*     */   {
/* 101 */     int ff = dic.getInt(field, 0);
/* 102 */     return (ff & bitFlag) == bitFlag;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.util.BitFlagHelper
 * JD-Core Version:    0.6.2
 */