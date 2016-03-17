/*     */ package org.apache.pdfbox.io;
/*     */ 
/*     */ import java.io.EOFException;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.PushbackInputStream;
/*     */ 
/*     */ public class PushBackInputStream extends PushbackInputStream
/*     */ {
/*  34 */   private long offset = 0L;
/*     */   private final RandomAccessRead raInput;
/*     */ 
/*     */   public PushBackInputStream(InputStream input, int size)
/*     */     throws IOException
/*     */   {
/*  50 */     super(input, size);
/*  51 */     if (input == null)
/*     */     {
/*  53 */       throw new IOException("Error: input was null");
/*     */     }
/*     */ 
/*  56 */     this.raInput = ((input instanceof RandomAccessRead) ? (RandomAccessRead)input : null);
/*     */   }
/*     */ 
/*     */   public int peek()
/*     */     throws IOException
/*     */   {
/*  68 */     int result = read();
/*  69 */     if (result != -1)
/*     */     {
/*  71 */       unread(result);
/*     */     }
/*  73 */     return result;
/*     */   }
/*     */ 
/*     */   public long getOffset()
/*     */   {
/*  82 */     return this.offset;
/*     */   }
/*     */ 
/*     */   public int read()
/*     */     throws IOException
/*     */   {
/*  90 */     int retval = super.read();
/*  91 */     if (retval != -1)
/*     */     {
/*  93 */       this.offset += 1L;
/*     */     }
/*  95 */     return retval;
/*     */   }
/*     */ 
/*     */   public int read(byte[] b)
/*     */     throws IOException
/*     */   {
/* 103 */     return read(b, 0, b.length);
/*     */   }
/*     */ 
/*     */   public int read(byte[] b, int off, int len)
/*     */     throws IOException
/*     */   {
/* 110 */     int retval = super.read(b, off, len);
/* 111 */     if (retval != -1)
/*     */     {
/* 113 */       this.offset += retval;
/*     */     }
/* 115 */     return retval;
/*     */   }
/*     */ 
/*     */   public void unread(int b)
/*     */     throws IOException
/*     */   {
/* 123 */     this.offset -= 1L;
/* 124 */     super.unread(b);
/*     */   }
/*     */ 
/*     */   public void unread(byte[] b)
/*     */     throws IOException
/*     */   {
/* 132 */     unread(b, 0, b.length);
/*     */   }
/*     */ 
/*     */   public void unread(byte[] b, int off, int len)
/*     */     throws IOException
/*     */   {
/* 140 */     if (len > 0)
/*     */     {
/* 142 */       this.offset -= len;
/* 143 */       super.unread(b, off, len);
/*     */     }
/*     */   }
/*     */ 
/*     */   public boolean isEOF()
/*     */     throws IOException
/*     */   {
/* 156 */     int peek = peek();
/* 157 */     return peek == -1;
/*     */   }
/*     */ 
/*     */   public void fillBuffer()
/*     */     throws IOException
/*     */   {
/* 170 */     int bufferLength = this.buf.length;
/* 171 */     byte[] tmpBuffer = new byte[bufferLength];
/* 172 */     int amountRead = 0;
/* 173 */     int totalAmountRead = 0;
/* 174 */     while ((amountRead != -1) && (totalAmountRead < bufferLength))
/*     */     {
/* 176 */       amountRead = read(tmpBuffer, totalAmountRead, bufferLength - totalAmountRead);
/* 177 */       if (amountRead != -1)
/*     */       {
/* 179 */         totalAmountRead += amountRead;
/*     */       }
/*     */     }
/* 182 */     unread(tmpBuffer, 0, totalAmountRead);
/*     */   }
/*     */ 
/*     */   public byte[] readFully(int length)
/*     */     throws IOException
/*     */   {
/* 193 */     byte[] data = new byte[length];
/* 194 */     int pos = 0;
/* 195 */     while (pos < length)
/*     */     {
/* 197 */       int amountRead = read(data, pos, length - pos);
/* 198 */       if (amountRead < 0)
/*     */       {
/* 200 */         throw new EOFException("Premature end of file");
/*     */       }
/* 202 */       pos += amountRead;
/*     */     }
/* 204 */     return data;
/*     */   }
/*     */ 
/*     */   public void seek(long newOffset)
/*     */     throws IOException
/*     */   {
/* 221 */     if (this.raInput == null)
/*     */     {
/* 223 */       throw new IOException("Provided stream of type " + this.in.getClass().getSimpleName() + " is not seekable.");
/*     */     }
/*     */ 
/* 227 */     int unreadLength = this.buf.length - this.pos;
/* 228 */     if (unreadLength > 0)
/*     */     {
/* 230 */       skip(unreadLength);
/*     */     }
/*     */ 
/* 233 */     this.raInput.seek(newOffset);
/* 234 */     this.offset = newOffset;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.io.PushBackInputStream
 * JD-Core Version:    0.6.2
 */