/*     */ package com.itextpdf.text.pdf;
/*     */ 
/*     */ import com.itextpdf.text.error_messages.MessageLocalization;
/*     */ 
/*     */ public class PdfBoolean extends PdfObject
/*     */ {
/*  62 */   public static final PdfBoolean PDFTRUE = new PdfBoolean(true);
/*  63 */   public static final PdfBoolean PDFFALSE = new PdfBoolean(false);
/*     */   public static final String TRUE = "true";
/*     */   public static final String FALSE = "false";
/*     */   private boolean value;
/*     */ 
/*     */   public PdfBoolean(boolean value)
/*     */   {
/*  84 */     super(1);
/*  85 */     if (value) {
/*  86 */       setContent("true");
/*     */     }
/*     */     else {
/*  89 */       setContent("false");
/*     */     }
/*  91 */     this.value = value;
/*     */   }
/*     */ 
/*     */   public PdfBoolean(String value)
/*     */     throws BadPdfFormatException
/*     */   {
/* 103 */     super(1, value);
/* 104 */     if (value.equals("true")) {
/* 105 */       this.value = true;
/*     */     }
/* 107 */     else if (value.equals("false")) {
/* 108 */       this.value = false;
/*     */     }
/*     */     else
/* 111 */       throw new BadPdfFormatException(MessageLocalization.getComposedMessage("the.value.has.to.be.true.of.false.instead.of.1", new Object[] { value }));
/*     */   }
/*     */ 
/*     */   public boolean booleanValue()
/*     */   {
/* 124 */     return this.value;
/*     */   }
/*     */ 
/*     */   public String toString() {
/* 128 */     return this.value ? "true" : "false";
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.pdf.PdfBoolean
 * JD-Core Version:    0.6.2
 */