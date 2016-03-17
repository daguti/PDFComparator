/*     */ package org.apache.pdfbox.cos;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStream;
/*     */ import java.math.BigDecimal;
/*     */ import org.apache.pdfbox.exceptions.COSVisitorException;
/*     */ 
/*     */ public class COSFloat extends COSNumber
/*     */ {
/*     */   private BigDecimal value;
/*     */   private String valueAsString;
/*     */ 
/*     */   public COSFloat(float aFloat)
/*     */   {
/*  43 */     setValue(aFloat);
/*     */   }
/*     */ 
/*     */   public COSFloat(String aFloat)
/*     */     throws IOException
/*     */   {
/*     */     try
/*     */     {
/*  57 */       this.valueAsString = aFloat;
/*  58 */       this.value = new BigDecimal(this.valueAsString);
/*     */     }
/*     */     catch (NumberFormatException e)
/*     */     {
/*  62 */       throw new IOException("Error expected floating point number actual='" + aFloat + "'");
/*     */     }
/*     */   }
/*     */ 
/*     */   public void setValue(float floatValue)
/*     */   {
/*  75 */     this.value = new BigDecimal(String.valueOf(floatValue));
/*  76 */     this.valueAsString = removeNullDigits(this.value.toPlainString());
/*     */   }
/*     */ 
/*     */   private String removeNullDigits(String value)
/*     */   {
/*  82 */     if ((value.indexOf(".") > -1) && (!value.endsWith(".0")))
/*     */     {
/*  84 */       while ((value.endsWith("0")) && (!value.endsWith(".0")))
/*     */       {
/*  86 */         value = value.substring(0, value.length() - 1);
/*     */       }
/*     */     }
/*  89 */     return value;
/*     */   }
/*     */ 
/*     */   public float floatValue()
/*     */   {
/*  99 */     return this.value.floatValue();
/*     */   }
/*     */ 
/*     */   public double doubleValue()
/*     */   {
/* 109 */     return this.value.doubleValue();
/*     */   }
/*     */ 
/*     */   public long longValue()
/*     */   {
/* 119 */     return this.value.longValue();
/*     */   }
/*     */ 
/*     */   public int intValue()
/*     */   {
/* 129 */     return this.value.intValue();
/*     */   }
/*     */ 
/*     */   public boolean equals(Object o)
/*     */   {
/* 137 */     return ((o instanceof COSFloat)) && (Float.floatToIntBits(((COSFloat)o).value.floatValue()) == Float.floatToIntBits(this.value.floatValue()));
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/* 145 */     return this.value.hashCode();
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 153 */     return "COSFloat{" + this.valueAsString + "}";
/*     */   }
/*     */ 
/*     */   public Object accept(ICOSVisitor visitor)
/*     */     throws COSVisitorException
/*     */   {
/* 165 */     return visitor.visitFromFloat(this);
/*     */   }
/*     */ 
/*     */   public void writePDF(OutputStream output)
/*     */     throws IOException
/*     */   {
/* 176 */     output.write(this.valueAsString.getBytes("ISO-8859-1"));
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.cos.COSFloat
 * JD-Core Version:    0.6.2
 */