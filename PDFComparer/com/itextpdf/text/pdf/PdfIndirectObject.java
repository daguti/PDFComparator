/*     */ package com.itextpdf.text.pdf;
/*     */ 
/*     */ import com.itextpdf.text.DocWriter;
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStream;
/*     */ 
/*     */ public class PdfIndirectObject
/*     */ {
/*     */   protected int number;
/*  75 */   protected int generation = 0;
/*     */ 
/*  77 */   static final byte[] STARTOBJ = DocWriter.getISOBytes(" obj\n");
/*  78 */   static final byte[] ENDOBJ = DocWriter.getISOBytes("\nendobj\n");
/*  79 */   static final int SIZEOBJ = STARTOBJ.length + ENDOBJ.length;
/*     */   protected PdfObject object;
/*     */   protected PdfWriter writer;
/*     */ 
/*     */   protected PdfIndirectObject(int number, PdfObject object, PdfWriter writer)
/*     */   {
/*  93 */     this(number, 0, object, writer);
/*     */   }
/*     */ 
/*     */   PdfIndirectObject(PdfIndirectReference ref, PdfObject object, PdfWriter writer) {
/*  97 */     this(ref.getNumber(), ref.getGeneration(), object, writer);
/*     */   }
/*     */ 
/*     */   PdfIndirectObject(int number, int generation, PdfObject object, PdfWriter writer)
/*     */   {
/* 108 */     this.writer = writer;
/* 109 */     this.number = number;
/* 110 */     this.generation = generation;
/* 111 */     this.object = object;
/* 112 */     PdfEncryption crypto = null;
/* 113 */     if (writer != null)
/* 114 */       crypto = writer.getEncryption();
/* 115 */     if (crypto != null)
/* 116 */       crypto.setHashKey(number, generation);
/*     */   }
/*     */ 
/*     */   public PdfIndirectReference getIndirectReference()
/*     */   {
/* 143 */     return new PdfIndirectReference(this.object.type(), this.number, this.generation);
/*     */   }
/*     */ 
/*     */   protected void writeTo(OutputStream os)
/*     */     throws IOException
/*     */   {
/* 154 */     os.write(DocWriter.getISOBytes(String.valueOf(this.number)));
/* 155 */     os.write(32);
/* 156 */     os.write(DocWriter.getISOBytes(String.valueOf(this.generation)));
/* 157 */     os.write(STARTOBJ);
/* 158 */     this.object.toPdf(this.writer, os);
/* 159 */     os.write(ENDOBJ);
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.pdf.PdfIndirectObject
 * JD-Core Version:    0.6.2
 */