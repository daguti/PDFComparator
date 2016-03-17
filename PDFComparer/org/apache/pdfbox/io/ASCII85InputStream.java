/*     */ package org.apache.pdfbox.io;
/*     */ 
/*     */ import java.io.FilterInputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ 
/*     */ public class ASCII85InputStream extends FilterInputStream
/*     */ {
/*     */   private int index;
/*     */   private int n;
/*     */   private boolean eof;
/*     */   private byte[] ascii;
/*     */   private byte[] b;
/*     */   private static final char TERMINATOR = '~';
/*     */   private static final char OFFSET = '!';
/*     */   private static final char NEWLINE = '\n';
/*     */   private static final char RETURN = '\r';
/*     */   private static final char SPACE = ' ';
/*     */   private static final char PADDING_U = 'u';
/*     */   private static final char Z = 'z';
/*     */ 
/*     */   public ASCII85InputStream(InputStream is)
/*     */   {
/*  53 */     super(is);
/*  54 */     this.index = 0;
/*  55 */     this.n = 0;
/*  56 */     this.eof = false;
/*  57 */     this.ascii = new byte[5];
/*  58 */     this.b = new byte[4];
/*     */   }
/*     */ 
/*     */   public final int read()
/*     */     throws IOException
/*     */   {
/*  70 */     if (this.index >= this.n)
/*     */     {
/*  72 */       if (this.eof)
/*     */       {
/*  74 */         return -1;
/*     */       }
/*  76 */       this.index = 0;
/*     */       byte z;
/*     */       do
/*     */       {
/*  81 */         int zz = (byte)this.in.read();
/*  82 */         if (zz == -1)
/*     */         {
/*  84 */           this.eof = true;
/*  85 */           return -1;
/*     */         }
/*  87 */         z = (byte)zz;
/*  88 */       }while ((z == 10) || (z == 13) || (z == 32));
/*     */ 
/*  90 */       if (z == 126)
/*     */       {
/*  92 */         this.eof = true;
/*  93 */         this.ascii = (this.b = null);
/*  94 */         this.n = 0;
/*  95 */         return -1;
/*     */       }
/*  97 */       if (z == 122)
/*     */       {
/*     */         int tmp126_125 = (this.b[2] = this.b[3] = 0); this.b[1] = tmp126_125; this.b[0] = tmp126_125;
/* 100 */         this.n = 4;
/*     */       }
/*     */       else
/*     */       {
/* 104 */         this.ascii[0] = z;
/* 105 */         for (int k = 1; k < 5; k++)
/*     */         {
/*     */           do
/*     */           {
/* 109 */             int zz = (byte)this.in.read();
/* 110 */             if (zz == -1)
/*     */             {
/* 112 */               this.eof = true;
/* 113 */               return -1;
/*     */             }
/* 115 */             z = (byte)zz;
/* 116 */           }while ((z == 10) || (z == 13) || (z == 32));
/* 117 */           this.ascii[k] = z;
/* 118 */           if (z == 126)
/*     */           {
/* 121 */             this.ascii[k] = 117;
/* 122 */             break;
/*     */           }
/*     */         }
/* 125 */         this.n = (k - 1);
/* 126 */         if (this.n == 0)
/*     */         {
/* 128 */           this.eof = true;
/* 129 */           this.ascii = null;
/* 130 */           this.b = null;
/* 131 */           return -1;
/*     */         }
/* 133 */         if (k < 5)
/*     */         {
/* 135 */           for (k++; k < 5; k++)
/*     */           {
/* 138 */             this.ascii[k] = 117;
/*     */           }
/* 140 */           this.eof = true;
/*     */         }
/*     */ 
/* 143 */         long t = 0L;
/* 144 */         for (k = 0; k < 5; k++)
/*     */         {
/* 146 */           z = (byte)(this.ascii[k] - 33);
/* 147 */           if ((z < 0) || (z > 93))
/*     */           {
/* 149 */             this.n = 0;
/* 150 */             this.eof = true;
/* 151 */             this.ascii = null;
/* 152 */             this.b = null;
/* 153 */             throw new IOException("Invalid data in Ascii85 stream");
/*     */           }
/* 155 */           t = t * 85L + z;
/*     */         }
/* 157 */         for (k = 3; k >= 0; k--)
/*     */         {
/* 159 */           this.b[k] = ((byte)(int)(t & 0xFF));
/* 160 */           t >>>= 8;
/*     */         }
/*     */       }
/*     */     }
/* 164 */     return this.b[(this.index++)] & 0xFF;
/*     */   }
/*     */ 
/*     */   public final int read(byte[] data, int offset, int len)
/*     */     throws IOException
/*     */   {
/* 180 */     if ((this.eof) && (this.index >= this.n))
/*     */     {
/* 182 */       return -1;
/*     */     }
/* 184 */     for (int i = 0; i < len; i++)
/*     */     {
/* 186 */       if (this.index < this.n)
/*     */       {
/* 188 */         data[(i + offset)] = this.b[(this.index++)];
/*     */       }
/*     */       else
/*     */       {
/* 192 */         int t = read();
/* 193 */         if (t == -1)
/*     */         {
/* 195 */           return i;
/*     */         }
/* 197 */         data[(i + offset)] = ((byte)t);
/*     */       }
/*     */     }
/* 200 */     return len;
/*     */   }
/*     */ 
/*     */   public void close()
/*     */     throws IOException
/*     */   {
/* 210 */     this.ascii = null;
/* 211 */     this.eof = true;
/* 212 */     this.b = null;
/* 213 */     super.close();
/*     */   }
/*     */ 
/*     */   public boolean markSupported()
/*     */   {
/* 223 */     return false;
/*     */   }
/*     */ 
/*     */   public long skip(long nValue)
/*     */   {
/* 235 */     return 0L;
/*     */   }
/*     */ 
/*     */   public int available()
/*     */   {
/* 245 */     return 0;
/*     */   }
/*     */ 
/*     */   public void mark(int readlimit)
/*     */   {
/*     */   }
/*     */ 
/*     */   public void reset()
/*     */     throws IOException
/*     */   {
/* 264 */     throw new IOException("Reset is not supported");
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.io.ASCII85InputStream
 * JD-Core Version:    0.6.2
 */