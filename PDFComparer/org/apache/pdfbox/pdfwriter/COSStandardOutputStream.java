/*     */ package org.apache.pdfbox.pdfwriter;
/*     */ 
/*     */ import java.io.FileDescriptor;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.FilterOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.nio.channels.FileChannel;
/*     */ import org.apache.pdfbox.util.StringUtil;
/*     */ 
/*     */ public class COSStandardOutputStream extends FilterOutputStream
/*     */ {
/*  45 */   public static final byte[] CRLF = StringUtil.getBytes("\r\n");
/*     */ 
/*  50 */   public static final byte[] LF = StringUtil.getBytes("\n");
/*     */ 
/*  55 */   public static final byte[] EOL = StringUtil.getBytes("\n");
/*     */ 
/*  58 */   private long pos = 0L;
/*     */ 
/*  60 */   private boolean onNewLine = false;
/*  61 */   private FileChannel fileChannel = null;
/*  62 */   private FileDescriptor fileDescriptor = null;
/*  63 */   private long mark = -1L;
/*     */ 
/*     */   public COSStandardOutputStream(OutputStream out)
/*     */   {
/*  72 */     super(out);
/*  73 */     if ((out instanceof FileOutputStream))
/*     */       try {
/*  75 */         this.fileChannel = ((FileOutputStream)out).getChannel();
/*  76 */         this.fileDescriptor = ((FileOutputStream)out).getFD();
/*  77 */         this.pos = this.fileChannel.position();
/*     */       } catch (IOException e) {
/*  79 */         e.printStackTrace();
/*     */       }
/*     */   }
/*     */ 
/*     */   public long getPos()
/*     */   {
/*  91 */     return this.pos;
/*     */   }
/*     */ 
/*     */   public void setPos(long pos)
/*     */     throws IOException
/*     */   {
/* 102 */     if (this.fileChannel != null) {
/* 103 */       checkPos();
/* 104 */       this.pos = pos;
/* 105 */       this.fileChannel.position(pos);
/*     */     }
/*     */   }
/*     */ 
/*     */   public boolean isOnNewLine()
/*     */   {
/* 116 */     return this.onNewLine;
/*     */   }
/*     */ 
/*     */   public void setOnNewLine(boolean newOnNewLine)
/*     */   {
/* 125 */     this.onNewLine = newOnNewLine;
/*     */   }
/*     */ 
/*     */   public void write(byte[] b, int off, int len)
/*     */     throws IOException
/*     */   {
/* 140 */     checkPos();
/* 141 */     setOnNewLine(false);
/* 142 */     this.out.write(b, off, len);
/* 143 */     this.pos += len;
/*     */   }
/*     */ 
/*     */   public void write(int b)
/*     */     throws IOException
/*     */   {
/* 156 */     checkPos();
/* 157 */     setOnNewLine(false);
/* 158 */     this.out.write(b);
/* 159 */     this.pos += 1L;
/*     */   }
/*     */ 
/*     */   public void writeCRLF()
/*     */     throws IOException
/*     */   {
/* 169 */     write(CRLF);
/*     */   }
/*     */ 
/*     */   public void writeEOL()
/*     */     throws IOException
/*     */   {
/* 179 */     if (!isOnNewLine())
/*     */     {
/* 181 */       write(EOL);
/* 182 */       setOnNewLine(true);
/*     */     }
/*     */   }
/*     */ 
/*     */   public void writeLF()
/*     */     throws IOException
/*     */   {
/* 193 */     write(LF);
/*     */   }
/*     */ 
/*     */   public void mark() throws IOException
/*     */   {
/* 198 */     checkPos();
/* 199 */     this.mark = getPos();
/*     */   }
/*     */ 
/*     */   public void reset() throws IOException
/*     */   {
/* 204 */     if (this.mark < 0L)
/* 205 */       return;
/* 206 */     setPos(this.mark);
/*     */   }
/*     */ 
/*     */   private void checkPos() throws IOException
/*     */   {
/* 211 */     if ((this.fileChannel != null) && (this.fileChannel.position() != getPos()))
/* 212 */       throw new IOException("OutputStream has an invalid position");
/*     */   }
/*     */ 
/*     */   public byte[] getFileInBytes(int[] byteRange) throws IOException
/*     */   {
/* 217 */     return null;
/*     */   }
/*     */ 
/*     */   public InputStream getFilterInputStream(int[] byteRange)
/*     */   {
/* 222 */     return new COSFilterInputStream(new FileInputStream(this.fileDescriptor), byteRange);
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.pdfwriter.COSStandardOutputStream
 * JD-Core Version:    0.6.2
 */