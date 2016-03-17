/*     */ package org.apache.pdfbox.pdmodel.common;
/*     */ 
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.util.List;
/*     */ import org.apache.pdfbox.cos.COSBase;
/*     */ import org.apache.pdfbox.cos.COSStream;
/*     */ import org.apache.pdfbox.pdmodel.common.filespecification.PDFileSpecification;
/*     */ 
/*     */ public class PDMemoryStream extends PDStream
/*     */ {
/*     */   private byte[] data;
/*     */ 
/*     */   public PDMemoryStream(byte[] buffer)
/*     */   {
/*  49 */     this.data = buffer;
/*     */   }
/*     */ 
/*     */   public void addCompression()
/*     */   {
/*     */   }
/*     */ 
/*     */   public COSBase getCOSObject()
/*     */   {
/*  72 */     throw new UnsupportedOperationException("not supported for memory stream");
/*     */   }
/*     */ 
/*     */   public OutputStream createOutputStream()
/*     */     throws IOException
/*     */   {
/*  84 */     throw new UnsupportedOperationException("not supported for memory stream");
/*     */   }
/*     */ 
/*     */   public InputStream createInputStream()
/*     */     throws IOException
/*     */   {
/*  96 */     return new ByteArrayInputStream(this.data);
/*     */   }
/*     */ 
/*     */   public InputStream getPartiallyFilteredStream(List stopFilters)
/*     */     throws IOException
/*     */   {
/* 109 */     return createInputStream();
/*     */   }
/*     */ 
/*     */   public COSStream getStream()
/*     */   {
/* 119 */     throw new UnsupportedOperationException("not supported for memory stream");
/*     */   }
/*     */ 
/*     */   public int getLength()
/*     */   {
/* 130 */     return this.data.length;
/*     */   }
/*     */ 
/*     */   public List getFilters()
/*     */   {
/* 140 */     return null;
/*     */   }
/*     */ 
/*     */   public void setFilters(List filters)
/*     */   {
/* 150 */     throw new UnsupportedOperationException("not supported for memory stream");
/*     */   }
/*     */ 
/*     */   public List getDecodeParams()
/*     */     throws IOException
/*     */   {
/* 163 */     return null;
/*     */   }
/*     */ 
/*     */   public void setDecodeParams(List decodeParams)
/*     */   {
/*     */   }
/*     */ 
/*     */   public PDFileSpecification getFile()
/*     */   {
/* 184 */     return null;
/*     */   }
/*     */ 
/*     */   public void setFile(PDFileSpecification f)
/*     */   {
/*     */   }
/*     */ 
/*     */   public List getFileFilters()
/*     */   {
/* 203 */     return null;
/*     */   }
/*     */ 
/*     */   public void setFileFilters(List filters)
/*     */   {
/*     */   }
/*     */ 
/*     */   public List getFileDecodeParams()
/*     */     throws IOException
/*     */   {
/* 226 */     return null;
/*     */   }
/*     */ 
/*     */   public void setFileDecodeParams(List decodeParams)
/*     */   {
/*     */   }
/*     */ 
/*     */   public byte[] getByteArray()
/*     */     throws IOException
/*     */   {
/* 247 */     return this.data;
/*     */   }
/*     */ 
/*     */   public PDMetadata getMetadata()
/*     */   {
/* 258 */     return null;
/*     */   }
/*     */ 
/*     */   public void setMetadata(PDMetadata meta)
/*     */   {
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.pdmodel.common.PDMemoryStream
 * JD-Core Version:    0.6.2
 */