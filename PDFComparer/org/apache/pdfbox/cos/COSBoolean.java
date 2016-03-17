/*     */ package org.apache.pdfbox.cos;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStream;
/*     */ import org.apache.pdfbox.exceptions.COSVisitorException;
/*     */ 
/*     */ public class COSBoolean extends COSBase
/*     */ {
/*  35 */   public static final byte[] TRUE_BYTES = { 116, 114, 117, 101 };
/*     */ 
/*  39 */   public static final byte[] FALSE_BYTES = { 102, 97, 108, 115, 101 };
/*     */ 
/*  44 */   public static final COSBoolean TRUE = new COSBoolean(true);
/*     */ 
/*  49 */   public static final COSBoolean FALSE = new COSBoolean(false);
/*     */   private boolean value;
/*     */ 
/*     */   private COSBoolean(boolean aValue)
/*     */   {
/*  60 */     this.value = aValue;
/*     */   }
/*     */ 
/*     */   public boolean getValue()
/*     */   {
/*  70 */     return this.value;
/*     */   }
/*     */ 
/*     */   public Boolean getValueAsObject()
/*     */   {
/*  80 */     return this.value ? Boolean.TRUE : Boolean.FALSE;
/*     */   }
/*     */ 
/*     */   public static COSBoolean getBoolean(boolean value)
/*     */   {
/*  92 */     return value ? TRUE : FALSE;
/*     */   }
/*     */ 
/*     */   public static COSBoolean getBoolean(Boolean value)
/*     */   {
/* 104 */     return getBoolean(value.booleanValue());
/*     */   }
/*     */ 
/*     */   public Object accept(ICOSVisitor visitor)
/*     */     throws COSVisitorException
/*     */   {
/* 116 */     return visitor.visitFromBoolean(this);
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 126 */     return String.valueOf(this.value);
/*     */   }
/*     */ 
/*     */   public void writePDF(OutputStream output)
/*     */     throws IOException
/*     */   {
/* 138 */     if (this.value)
/*     */     {
/* 140 */       output.write(TRUE_BYTES);
/*     */     }
/*     */     else
/*     */     {
/* 144 */       output.write(FALSE_BYTES);
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.cos.COSBoolean
 * JD-Core Version:    0.6.2
 */