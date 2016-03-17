/*     */ package org.apache.pdfbox.cos;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStream;
/*     */ import org.apache.pdfbox.exceptions.COSVisitorException;
/*     */ 
/*     */ public class COSInteger extends COSNumber
/*     */ {
/*  36 */   private static int LOW = -100;
/*     */ 
/*  41 */   private static int HIGH = 256;
/*     */ 
/*  47 */   private static final COSInteger[] STATIC = new COSInteger[HIGH - LOW + 1];
/*     */ 
/*  53 */   public static final COSInteger ZERO = get(0L);
/*     */ 
/*  59 */   public static final COSInteger ONE = get(1L);
/*     */ 
/*  65 */   public static final COSInteger TWO = get(2L);
/*     */ 
/*  71 */   public static final COSInteger THREE = get(3L);
/*     */   private long value;
/*     */ 
/*     */   public static COSInteger get(long val)
/*     */   {
/*  80 */     if ((LOW <= val) && (val <= HIGH)) {
/*  81 */       int index = (int)val - LOW;
/*     */ 
/*  83 */       if (STATIC[index] == null) {
/*  84 */         STATIC[index] = new COSInteger(val);
/*     */       }
/*  86 */       return STATIC[index];
/*     */     }
/*  88 */     return new COSInteger(val);
/*     */   }
/*     */ 
/*     */   /** @deprecated */
/*     */   public COSInteger(long val)
/*     */   {
/* 102 */     this.value = val;
/*     */   }
/*     */ 
/*     */   /** @deprecated */
/*     */   public COSInteger(int val)
/*     */   {
/* 113 */     this(val);
/*     */   }
/*     */ 
/*     */   /** @deprecated */
/*     */   public COSInteger(String val)
/*     */     throws IOException
/*     */   {
/*     */     try
/*     */     {
/* 127 */       this.value = Long.parseLong(val);
/*     */     }
/*     */     catch (NumberFormatException e)
/*     */     {
/* 131 */       throw new IOException("Error: value is not an integer type actual='" + val + "'");
/*     */     }
/*     */   }
/*     */ 
/*     */   public boolean equals(Object o)
/*     */   {
/* 140 */     return ((o instanceof COSInteger)) && (((COSInteger)o).intValue() == intValue());
/*     */   }
/*     */ 
/*     */   public int hashCode()
/*     */   {
/* 149 */     return (int)(this.value ^ this.value >> 32);
/*     */   }
/*     */ 
/*     */   public String toString()
/*     */   {
/* 157 */     return "COSInt{" + this.value + "}";
/*     */   }
/*     */ 
/*     */   public void setValue(long newValue)
/*     */   {
/* 167 */     this.value = newValue;
/*     */   }
/*     */ 
/*     */   public float floatValue()
/*     */   {
/* 177 */     return (float)this.value;
/*     */   }
/*     */ 
/*     */   public double doubleValue()
/*     */   {
/* 187 */     return this.value;
/*     */   }
/*     */ 
/*     */   public int intValue()
/*     */   {
/* 198 */     return (int)this.value;
/*     */   }
/*     */ 
/*     */   public long longValue()
/*     */   {
/* 209 */     return this.value;
/*     */   }
/*     */ 
/*     */   public Object accept(ICOSVisitor visitor)
/*     */     throws COSVisitorException
/*     */   {
/* 221 */     return visitor.visitFromInt(this);
/*     */   }
/*     */ 
/*     */   public void writePDF(OutputStream output)
/*     */     throws IOException
/*     */   {
/* 232 */     output.write(String.valueOf(this.value).getBytes("ISO-8859-1"));
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.cos.COSInteger
 * JD-Core Version:    0.6.2
 */