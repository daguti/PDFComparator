/*     */ package org.apache.pdfbox.io;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStream;
/*     */ import org.apache.pdfbox.cos.COSBase;
/*     */ import org.apache.pdfbox.cos.COSNumber;
/*     */ import org.apache.pdfbox.cos.COSObject;
/*     */ 
/*     */ public class RandomAccessFileOutputStream extends OutputStream
/*     */ {
/*     */   private RandomAccess file;
/*     */   private long position;
/*  37 */   private long lengthWritten = 0L;
/*  38 */   private COSBase expectedLength = null;
/*     */ 
/*     */   public RandomAccessFileOutputStream(RandomAccess raf)
/*     */     throws IOException
/*     */   {
/*  50 */     this.file = raf;
/*     */ 
/*  52 */     this.position = raf.length();
/*     */   }
/*     */ 
/*     */   public long getPosition()
/*     */   {
/*  63 */     return this.position;
/*     */   }
/*     */ 
/*     */   public long getLengthWritten()
/*     */   {
/*  74 */     return this.lengthWritten;
/*     */   }
/*     */ 
/*     */   public long getLength()
/*     */   {
/*  84 */     long length = -1L;
/*  85 */     if ((this.expectedLength instanceof COSNumber))
/*     */     {
/*  87 */       length = ((COSNumber)this.expectedLength).intValue();
/*     */     }
/*  89 */     else if (((this.expectedLength instanceof COSObject)) && ((((COSObject)this.expectedLength).getObject() instanceof COSNumber)))
/*     */     {
/*  92 */       length = ((COSNumber)((COSObject)this.expectedLength).getObject()).intValue();
/*     */     }
/*  94 */     if (length == -1L)
/*     */     {
/*  96 */       length = this.lengthWritten;
/*     */     }
/*  98 */     return length;
/*     */   }
/*     */ 
/*     */   public void write(byte[] b, int offset, int length)
/*     */     throws IOException
/*     */   {
/* 106 */     this.file.seek(this.position + this.lengthWritten);
/* 107 */     this.lengthWritten += length;
/* 108 */     this.file.write(b, offset, length);
/*     */   }
/*     */ 
/*     */   public void write(int b)
/*     */     throws IOException
/*     */   {
/* 116 */     this.file.seek(this.position + this.lengthWritten);
/* 117 */     this.lengthWritten += 1L;
/* 118 */     this.file.write(b);
/*     */   }
/*     */ 
/*     */   public COSBase getExpectedLength()
/*     */   {
/* 129 */     return this.expectedLength;
/*     */   }
/*     */ 
/*     */   public void setExpectedLength(COSBase value)
/*     */   {
/* 139 */     this.expectedLength = value;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.io.RandomAccessFileOutputStream
 * JD-Core Version:    0.6.2
 */