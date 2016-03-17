/*     */ package org.apache.pdfbox.io;
/*     */ 
/*     */ import java.io.FilterOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStream;
/*     */ 
/*     */ public class ASCII85OutputStream extends FilterOutputStream
/*     */ {
/*     */   private int lineBreak;
/*     */   private int count;
/*     */   private byte[] indata;
/*     */   private byte[] outdata;
/*     */   private int maxline;
/*     */   private boolean flushed;
/*     */   private char terminator;
/*     */   private static final char OFFSET = '!';
/*     */   private static final char NEWLINE = '\n';
/*     */   private static final char Z = 'z';
/*     */ 
/*     */   public ASCII85OutputStream(OutputStream out)
/*     */   {
/*  56 */     super(out);
/*  57 */     this.lineBreak = 72;
/*  58 */     this.maxline = 72;
/*  59 */     this.count = 0;
/*  60 */     this.indata = new byte[4];
/*  61 */     this.outdata = new byte[5];
/*  62 */     this.flushed = true;
/*  63 */     this.terminator = '~';
/*     */   }
/*     */ 
/*     */   public void setTerminator(char term)
/*     */   {
/*  73 */     if ((term < 'v') || (term > '~') || (term == 'z'))
/*     */     {
/*  75 */       throw new IllegalArgumentException("Terminator must be 118-126 excluding z");
/*     */     }
/*  77 */     this.terminator = term;
/*     */   }
/*     */ 
/*     */   public char getTerminator()
/*     */   {
/*  87 */     return this.terminator;
/*     */   }
/*     */ 
/*     */   public void setLineLength(int l)
/*     */   {
/*  97 */     if (this.lineBreak > l)
/*     */     {
/*  99 */       this.lineBreak = l;
/*     */     }
/* 101 */     this.maxline = l;
/*     */   }
/*     */ 
/*     */   public int getLineLength()
/*     */   {
/* 111 */     return this.maxline;
/*     */   }
/*     */ 
/*     */   private final void transformASCII85()
/*     */   {
/* 119 */     long word = ((this.indata[0] << 8 | this.indata[1] & 0xFF) << 16 | (this.indata[2] & 0xFF) << 8 | this.indata[3] & 0xFF) & 0xFFFFFFFF;
/*     */ 
/* 121 */     if (word == 0L)
/*     */     {
/* 123 */       this.outdata[0] = 122;
/* 124 */       this.outdata[1] = 0;
/* 125 */       return;
/*     */     }
/*     */ 
/* 128 */     long x = word / 52200625L;
/* 129 */     this.outdata[0] = ((byte)(int)(x + 33L));
/* 130 */     word -= x * 85L * 85L * 85L * 85L;
/*     */ 
/* 132 */     x = word / 614125L;
/* 133 */     this.outdata[1] = ((byte)(int)(x + 33L));
/* 134 */     word -= x * 85L * 85L * 85L;
/*     */ 
/* 136 */     x = word / 7225L;
/* 137 */     this.outdata[2] = ((byte)(int)(x + 33L));
/* 138 */     word -= x * 85L * 85L;
/*     */ 
/* 140 */     x = word / 85L;
/* 141 */     this.outdata[3] = ((byte)(int)(x + 33L));
/*     */ 
/* 143 */     this.outdata[4] = ((byte)(int)(word % 85L + 33L));
/*     */   }
/*     */ 
/*     */   public final void write(int b)
/*     */     throws IOException
/*     */   {
/* 155 */     this.flushed = false;
/* 156 */     this.indata[(this.count++)] = ((byte)b);
/* 157 */     if (this.count < 4)
/*     */     {
/* 159 */       return;
/*     */     }
/* 161 */     transformASCII85();
/* 162 */     for (int i = 0; i < 5; i++)
/*     */     {
/* 164 */       if (this.outdata[i] == 0)
/*     */       {
/*     */         break;
/*     */       }
/* 168 */       this.out.write(this.outdata[i]);
/* 169 */       if (--this.lineBreak == 0)
/*     */       {
/* 171 */         this.out.write(10);
/* 172 */         this.lineBreak = this.maxline;
/*     */       }
/*     */     }
/* 175 */     this.count = 0;
/*     */   }
/*     */ 
/*     */   public final void flush()
/*     */     throws IOException
/*     */   {
/* 185 */     if (this.flushed)
/*     */     {
/* 187 */       return;
/*     */     }
/* 189 */     if (this.count > 0)
/*     */     {
/* 191 */       for (int i = this.count; i < 4; i++)
/*     */       {
/* 193 */         this.indata[i] = 0;
/*     */       }
/* 195 */       transformASCII85();
/* 196 */       if (this.outdata[0] == 122)
/*     */       {
/* 198 */         for (int i = 0; i < 5; i++)
/*     */         {
/* 200 */           this.outdata[i] = 33;
/*     */         }
/*     */       }
/* 203 */       for (int i = 0; i < this.count + 1; i++)
/*     */       {
/* 205 */         this.out.write(this.outdata[i]);
/* 206 */         if (--this.lineBreak == 0)
/*     */         {
/* 208 */           this.out.write(10);
/* 209 */           this.lineBreak = this.maxline;
/*     */         }
/*     */       }
/*     */     }
/* 213 */     if (--this.lineBreak == 0)
/*     */     {
/* 215 */       this.out.write(10);
/*     */     }
/* 217 */     this.out.write(this.terminator);
/* 218 */     this.out.write(10);
/* 219 */     this.count = 0;
/* 220 */     this.lineBreak = this.maxline;
/* 221 */     this.flushed = true;
/* 222 */     super.flush();
/*     */   }
/*     */ 
/*     */   public void close()
/*     */     throws IOException
/*     */   {
/*     */     try
/*     */     {
/* 234 */       flush();
/* 235 */       super.close();
/*     */     }
/*     */     finally
/*     */     {
/* 239 */       this.indata = (this.outdata = null);
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.io.ASCII85OutputStream
 * JD-Core Version:    0.6.2
 */