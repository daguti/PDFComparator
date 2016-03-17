/*     */ package com.itextpdf.text.pdf;
/*     */ 
/*     */ import com.itextpdf.text.error_messages.MessageLocalization;
/*     */ 
/*     */ public class PdfNumber extends PdfObject
/*     */ {
/*     */   private double value;
/*     */ 
/*     */   public PdfNumber(String content)
/*     */   {
/*  78 */     super(2);
/*     */     try {
/*  80 */       this.value = Double.parseDouble(content.trim());
/*  81 */       setContent(content);
/*     */     }
/*     */     catch (NumberFormatException nfe) {
/*  84 */       throw new RuntimeException(MessageLocalization.getComposedMessage("1.is.not.a.valid.number.2", new Object[] { content, nfe.toString() }));
/*     */     }
/*     */   }
/*     */ 
/*     */   public PdfNumber(int value)
/*     */   {
/*  94 */     super(2);
/*  95 */     this.value = value;
/*  96 */     setContent(String.valueOf(value));
/*     */   }
/*     */ 
/*     */   public PdfNumber(long value)
/*     */   {
/* 105 */     super(2);
/* 106 */     this.value = value;
/* 107 */     setContent(String.valueOf(value));
/*     */   }
/*     */ 
/*     */   public PdfNumber(double value)
/*     */   {
/* 116 */     super(2);
/* 117 */     this.value = value;
/* 118 */     setContent(ByteBuffer.formatDouble(value));
/*     */   }
/*     */ 
/*     */   public PdfNumber(float value)
/*     */   {
/* 127 */     this(value);
/*     */   }
/*     */ 
/*     */   public int intValue()
/*     */   {
/* 138 */     return (int)this.value;
/*     */   }
/*     */ 
/*     */   public long longValue()
/*     */   {
/* 147 */     return ()this.value;
/*     */   }
/*     */ 
/*     */   public double doubleValue()
/*     */   {
/* 156 */     return this.value;
/*     */   }
/*     */ 
/*     */   public float floatValue()
/*     */   {
/* 165 */     return (float)this.value;
/*     */   }
/*     */ 
/*     */   public void increment()
/*     */   {
/* 174 */     this.value += 1.0D;
/* 175 */     setContent(ByteBuffer.formatDouble(this.value));
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.pdf.PdfNumber
 * JD-Core Version:    0.6.2
 */