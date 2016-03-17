/*     */ package com.itextpdf.text.pdf;
/*     */ 
/*     */ import com.itextpdf.text.ExceptionConverter;
/*     */ import java.io.IOException;
/*     */ 
/*     */ public class PdfFunction
/*     */ {
/*     */   protected PdfWriter writer;
/*     */   protected PdfIndirectReference reference;
/*     */   protected PdfDictionary dictionary;
/*     */ 
/*     */   protected PdfFunction(PdfWriter writer)
/*     */   {
/*  64 */     this.writer = writer;
/*     */   }
/*     */ 
/*     */   PdfIndirectReference getReference() {
/*     */     try {
/*  69 */       if (this.reference == null)
/*  70 */         this.reference = this.writer.addToBody(this.dictionary).getIndirectReference();
/*     */     }
/*     */     catch (IOException ioe)
/*     */     {
/*  74 */       throw new ExceptionConverter(ioe);
/*     */     }
/*  76 */     return this.reference;
/*     */   }
/*     */ 
/*     */   public static PdfFunction type0(PdfWriter writer, float[] domain, float[] range, int[] size, int bitsPerSample, int order, float[] encode, float[] decode, byte[] stream)
/*     */   {
/*  81 */     PdfFunction func = new PdfFunction(writer);
/*  82 */     func.dictionary = new PdfStream(stream);
/*  83 */     ((PdfStream)func.dictionary).flateCompress(writer.getCompressionLevel());
/*  84 */     func.dictionary.put(PdfName.FUNCTIONTYPE, new PdfNumber(0));
/*  85 */     func.dictionary.put(PdfName.DOMAIN, new PdfArray(domain));
/*  86 */     func.dictionary.put(PdfName.RANGE, new PdfArray(range));
/*  87 */     func.dictionary.put(PdfName.SIZE, new PdfArray(size));
/*  88 */     func.dictionary.put(PdfName.BITSPERSAMPLE, new PdfNumber(bitsPerSample));
/*  89 */     if (order != 1)
/*  90 */       func.dictionary.put(PdfName.ORDER, new PdfNumber(order));
/*  91 */     if (encode != null)
/*  92 */       func.dictionary.put(PdfName.ENCODE, new PdfArray(encode));
/*  93 */     if (decode != null)
/*  94 */       func.dictionary.put(PdfName.DECODE, new PdfArray(decode));
/*  95 */     return func;
/*     */   }
/*     */ 
/*     */   public static PdfFunction type2(PdfWriter writer, float[] domain, float[] range, float[] c0, float[] c1, float n) {
/*  99 */     PdfFunction func = new PdfFunction(writer);
/* 100 */     func.dictionary = new PdfDictionary();
/* 101 */     func.dictionary.put(PdfName.FUNCTIONTYPE, new PdfNumber(2));
/* 102 */     func.dictionary.put(PdfName.DOMAIN, new PdfArray(domain));
/* 103 */     if (range != null)
/* 104 */       func.dictionary.put(PdfName.RANGE, new PdfArray(range));
/* 105 */     if (c0 != null)
/* 106 */       func.dictionary.put(PdfName.C0, new PdfArray(c0));
/* 107 */     if (c1 != null)
/* 108 */       func.dictionary.put(PdfName.C1, new PdfArray(c1));
/* 109 */     func.dictionary.put(PdfName.N, new PdfNumber(n));
/* 110 */     return func;
/*     */   }
/*     */ 
/*     */   public static PdfFunction type3(PdfWriter writer, float[] domain, float[] range, PdfFunction[] functions, float[] bounds, float[] encode) {
/* 114 */     PdfFunction func = new PdfFunction(writer);
/* 115 */     func.dictionary = new PdfDictionary();
/* 116 */     func.dictionary.put(PdfName.FUNCTIONTYPE, new PdfNumber(3));
/* 117 */     func.dictionary.put(PdfName.DOMAIN, new PdfArray(domain));
/* 118 */     if (range != null)
/* 119 */       func.dictionary.put(PdfName.RANGE, new PdfArray(range));
/* 120 */     PdfArray array = new PdfArray();
/* 121 */     for (int k = 0; k < functions.length; k++)
/* 122 */       array.add(functions[k].getReference());
/* 123 */     func.dictionary.put(PdfName.FUNCTIONS, array);
/* 124 */     func.dictionary.put(PdfName.BOUNDS, new PdfArray(bounds));
/* 125 */     func.dictionary.put(PdfName.ENCODE, new PdfArray(encode));
/* 126 */     return func;
/*     */   }
/*     */ 
/*     */   public static PdfFunction type4(PdfWriter writer, float[] domain, float[] range, String postscript) {
/* 130 */     byte[] b = new byte[postscript.length()];
/* 131 */     for (int k = 0; k < b.length; k++)
/* 132 */       b[k] = ((byte)postscript.charAt(k));
/* 133 */     PdfFunction func = new PdfFunction(writer);
/* 134 */     func.dictionary = new PdfStream(b);
/* 135 */     ((PdfStream)func.dictionary).flateCompress(writer.getCompressionLevel());
/* 136 */     func.dictionary.put(PdfName.FUNCTIONTYPE, new PdfNumber(4));
/* 137 */     func.dictionary.put(PdfName.DOMAIN, new PdfArray(domain));
/* 138 */     func.dictionary.put(PdfName.RANGE, new PdfArray(range));
/* 139 */     return func;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.pdf.PdfFunction
 * JD-Core Version:    0.6.2
 */