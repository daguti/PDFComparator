/*     */ package com.itextpdf.text.pdf;
/*     */ 
/*     */ import com.itextpdf.text.DocWriter;
/*     */ import com.itextpdf.text.Document;
/*     */ import com.itextpdf.text.Rectangle;
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.util.zip.Deflater;
/*     */ import java.util.zip.DeflaterOutputStream;
/*     */ 
/*     */ class PdfContents extends PdfStream
/*     */ {
/*  62 */   static final byte[] SAVESTATE = DocWriter.getISOBytes("q\n");
/*  63 */   static final byte[] RESTORESTATE = DocWriter.getISOBytes("Q\n");
/*  64 */   static final byte[] ROTATE90 = DocWriter.getISOBytes("0 1 -1 0 ");
/*  65 */   static final byte[] ROTATE180 = DocWriter.getISOBytes("-1 0 0 -1 ");
/*  66 */   static final byte[] ROTATE270 = DocWriter.getISOBytes("0 -1 1 0 ");
/*  67 */   static final byte[] ROTATEFINAL = DocWriter.getISOBytes(" cm\n");
/*     */ 
/*     */   PdfContents(PdfContentByte under, PdfContentByte content, PdfContentByte text, PdfContentByte secondContent, Rectangle page)
/*     */     throws BadPdfFormatException
/*     */   {
/*     */     try
/*     */     {
/*  83 */       OutputStream out = null;
/*  84 */       Deflater deflater = null;
/*  85 */       this.streamBytes = new ByteArrayOutputStream();
/*  86 */       if (Document.compress)
/*     */       {
/*  88 */         this.compressed = true;
/*  89 */         if (text != null)
/*  90 */           this.compressionLevel = text.getPdfWriter().getCompressionLevel();
/*  91 */         else if (content != null)
/*  92 */           this.compressionLevel = content.getPdfWriter().getCompressionLevel();
/*  93 */         deflater = new Deflater(this.compressionLevel);
/*  94 */         out = new DeflaterOutputStream(this.streamBytes, deflater);
/*     */       }
/*     */       else {
/*  97 */         out = this.streamBytes;
/*  98 */       }int rotation = page.getRotation();
/*  99 */       switch (rotation) {
/*     */       case 90:
/* 101 */         out.write(ROTATE90);
/* 102 */         out.write(DocWriter.getISOBytes(ByteBuffer.formatDouble(page.getTop())));
/* 103 */         out.write(32);
/* 104 */         out.write(48);
/* 105 */         out.write(ROTATEFINAL);
/* 106 */         break;
/*     */       case 180:
/* 108 */         out.write(ROTATE180);
/* 109 */         out.write(DocWriter.getISOBytes(ByteBuffer.formatDouble(page.getRight())));
/* 110 */         out.write(32);
/* 111 */         out.write(DocWriter.getISOBytes(ByteBuffer.formatDouble(page.getTop())));
/* 112 */         out.write(ROTATEFINAL);
/* 113 */         break;
/*     */       case 270:
/* 115 */         out.write(ROTATE270);
/* 116 */         out.write(48);
/* 117 */         out.write(32);
/* 118 */         out.write(DocWriter.getISOBytes(ByteBuffer.formatDouble(page.getRight())));
/* 119 */         out.write(ROTATEFINAL);
/*     */       }
/*     */ 
/* 122 */       if (under.size() > 0) {
/* 123 */         out.write(SAVESTATE);
/* 124 */         under.getInternalBuffer().writeTo(out);
/* 125 */         out.write(RESTORESTATE);
/*     */       }
/* 127 */       if (content.size() > 0) {
/* 128 */         out.write(SAVESTATE);
/* 129 */         content.getInternalBuffer().writeTo(out);
/* 130 */         out.write(RESTORESTATE);
/*     */       }
/* 132 */       if (text != null) {
/* 133 */         out.write(SAVESTATE);
/* 134 */         text.getInternalBuffer().writeTo(out);
/* 135 */         out.write(RESTORESTATE);
/*     */       }
/* 137 */       if (secondContent.size() > 0) {
/* 138 */         secondContent.getInternalBuffer().writeTo(out);
/*     */       }
/* 140 */       out.close();
/* 141 */       if (deflater != null)
/* 142 */         deflater.end();
/*     */     }
/*     */     catch (Exception e)
/*     */     {
/* 146 */       throw new BadPdfFormatException(e.getMessage());
/*     */     }
/* 148 */     put(PdfName.LENGTH, new PdfNumber(this.streamBytes.size()));
/* 149 */     if (this.compressed)
/* 150 */       put(PdfName.FILTER, PdfName.FLATEDECODE);
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.pdf.PdfContents
 * JD-Core Version:    0.6.2
 */