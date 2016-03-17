/*     */ package org.apache.pdfbox.preflight.javacc;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.InputStreamReader;
/*     */ import java.io.Reader;
/*     */ import java.io.UnsupportedEncodingException;
/*     */ 
/*     */ public class SimpleCharStream
/*     */ {
/*     */   public static final boolean staticFlag = false;
/*     */   int bufsize;
/*     */   int available;
/*     */   int tokenBegin;
/*  18 */   public int bufpos = -1;
/*     */   protected int[] bufline;
/*     */   protected int[] bufcolumn;
/*  22 */   protected int column = 0;
/*  23 */   protected int line = 1;
/*     */ 
/*  25 */   protected boolean prevCharIsCR = false;
/*  26 */   protected boolean prevCharIsLF = false;
/*     */   protected Reader inputStream;
/*     */   protected char[] buffer;
/*  31 */   protected int maxNextCharInd = 0;
/*  32 */   protected int inBuf = 0;
/*  33 */   protected int tabSize = 8;
/*     */ 
/*  35 */   protected void setTabSize(int i) { this.tabSize = i; } 
/*  36 */   protected int getTabSize(int i) { return this.tabSize; }
/*     */ 
/*     */ 
/*     */   protected void ExpandBuff(boolean wrapAround)
/*     */   {
/*  41 */     char[] newbuffer = new char[this.bufsize + 2048];
/*  42 */     int[] newbufline = new int[this.bufsize + 2048];
/*  43 */     int[] newbufcolumn = new int[this.bufsize + 2048];
/*     */     try
/*     */     {
/*  47 */       if (wrapAround)
/*     */       {
/*  49 */         System.arraycopy(this.buffer, this.tokenBegin, newbuffer, 0, this.bufsize - this.tokenBegin);
/*  50 */         System.arraycopy(this.buffer, 0, newbuffer, this.bufsize - this.tokenBegin, this.bufpos);
/*  51 */         this.buffer = newbuffer;
/*     */ 
/*  53 */         System.arraycopy(this.bufline, this.tokenBegin, newbufline, 0, this.bufsize - this.tokenBegin);
/*  54 */         System.arraycopy(this.bufline, 0, newbufline, this.bufsize - this.tokenBegin, this.bufpos);
/*  55 */         this.bufline = newbufline;
/*     */ 
/*  57 */         System.arraycopy(this.bufcolumn, this.tokenBegin, newbufcolumn, 0, this.bufsize - this.tokenBegin);
/*  58 */         System.arraycopy(this.bufcolumn, 0, newbufcolumn, this.bufsize - this.tokenBegin, this.bufpos);
/*  59 */         this.bufcolumn = newbufcolumn;
/*     */ 
/*  61 */         this.maxNextCharInd = (this.bufpos += this.bufsize - this.tokenBegin);
/*     */       }
/*     */       else
/*     */       {
/*  65 */         System.arraycopy(this.buffer, this.tokenBegin, newbuffer, 0, this.bufsize - this.tokenBegin);
/*  66 */         this.buffer = newbuffer;
/*     */ 
/*  68 */         System.arraycopy(this.bufline, this.tokenBegin, newbufline, 0, this.bufsize - this.tokenBegin);
/*  69 */         this.bufline = newbufline;
/*     */ 
/*  71 */         System.arraycopy(this.bufcolumn, this.tokenBegin, newbufcolumn, 0, this.bufsize - this.tokenBegin);
/*  72 */         this.bufcolumn = newbufcolumn;
/*     */ 
/*  74 */         this.maxNextCharInd = (this.bufpos -= this.tokenBegin);
/*     */       }
/*     */     }
/*     */     catch (Throwable t)
/*     */     {
/*  79 */       throw new Error(t.getMessage());
/*     */     }
/*     */ 
/*  83 */     this.bufsize += 2048;
/*  84 */     this.available = this.bufsize;
/*  85 */     this.tokenBegin = 0;
/*     */   }
/*     */ 
/*     */   protected void FillBuff() throws IOException
/*     */   {
/*  90 */     if (this.maxNextCharInd == this.available)
/*     */     {
/*  92 */       if (this.available == this.bufsize)
/*     */       {
/*  94 */         if (this.tokenBegin > 2048)
/*     */         {
/*  96 */           this.bufpos = (this.maxNextCharInd = 0);
/*  97 */           this.available = this.tokenBegin;
/*     */         }
/*  99 */         else if (this.tokenBegin < 0) {
/* 100 */           this.bufpos = (this.maxNextCharInd = 0);
/*     */         } else {
/* 102 */           ExpandBuff(false);
/*     */         }
/* 104 */       } else if (this.available > this.tokenBegin)
/* 105 */         this.available = this.bufsize;
/* 106 */       else if (this.tokenBegin - this.available < 2048)
/* 107 */         ExpandBuff(true);
/*     */       else
/* 109 */         this.available = this.tokenBegin;
/*     */     }
/*     */     try
/*     */     {
/*     */       int i;
/* 114 */       if ((i = this.inputStream.read(this.buffer, this.maxNextCharInd, this.available - this.maxNextCharInd)) == -1)
/*     */       {
/* 116 */         this.inputStream.close();
/* 117 */         throw new IOException();
/*     */       }
/*     */ 
/* 120 */       this.maxNextCharInd += i;
/* 121 */       return;
/*     */     }
/*     */     catch (IOException e) {
/* 124 */       this.bufpos -= 1;
/* 125 */       backup(0);
/* 126 */       if (this.tokenBegin == -1)
/* 127 */         this.tokenBegin = this.bufpos;
/* 128 */       throw e;
/*     */     }
/*     */   }
/*     */ 
/*     */   public char BeginToken()
/*     */     throws IOException
/*     */   {
/* 135 */     this.tokenBegin = -1;
/* 136 */     char c = readChar();
/* 137 */     this.tokenBegin = this.bufpos;
/*     */ 
/* 139 */     return c;
/*     */   }
/*     */ 
/*     */   protected void UpdateLineColumn(char c)
/*     */   {
/* 144 */     this.column += 1;
/*     */ 
/* 146 */     if (this.prevCharIsLF)
/*     */     {
/* 148 */       this.prevCharIsLF = false;
/* 149 */       this.line += (this.column = 1);
/*     */     }
/* 151 */     else if (this.prevCharIsCR)
/*     */     {
/* 153 */       this.prevCharIsCR = false;
/* 154 */       if (c == '\n')
/*     */       {
/* 156 */         this.prevCharIsLF = true;
/*     */       }
/*     */       else {
/* 159 */         this.line += (this.column = 1);
/*     */       }
/*     */     }
/* 162 */     switch (c)
/*     */     {
/*     */     case '\r':
/* 165 */       this.prevCharIsCR = true;
/* 166 */       break;
/*     */     case '\n':
/* 168 */       this.prevCharIsLF = true;
/* 169 */       break;
/*     */     case '\t':
/* 171 */       this.column -= 1;
/* 172 */       this.column += this.tabSize - this.column % this.tabSize;
/* 173 */       break;
/*     */     case '\013':
/*     */     case '\f':
/*     */     }
/*     */ 
/* 178 */     this.bufline[this.bufpos] = this.line;
/* 179 */     this.bufcolumn[this.bufpos] = this.column;
/*     */   }
/*     */ 
/*     */   public char readChar()
/*     */     throws IOException
/*     */   {
/* 185 */     if (this.inBuf > 0)
/*     */     {
/* 187 */       this.inBuf -= 1;
/*     */ 
/* 189 */       if (++this.bufpos == this.bufsize) {
/* 190 */         this.bufpos = 0;
/*     */       }
/* 192 */       return this.buffer[this.bufpos];
/*     */     }
/*     */ 
/* 195 */     if (++this.bufpos >= this.maxNextCharInd) {
/* 196 */       FillBuff();
/*     */     }
/* 198 */     char c = this.buffer[this.bufpos];
/*     */ 
/* 200 */     UpdateLineColumn(c);
/* 201 */     return c;
/*     */   }
/*     */ 
/*     */   @Deprecated
/*     */   public int getColumn()
/*     */   {
/* 211 */     return this.bufcolumn[this.bufpos];
/*     */   }
/*     */ 
/*     */   @Deprecated
/*     */   public int getLine()
/*     */   {
/* 221 */     return this.bufline[this.bufpos];
/*     */   }
/*     */ 
/*     */   /** @deprecated */
/*     */   public int getEndColumn() {
/* 226 */     return this.bufcolumn[this.bufpos];
/*     */   }
/*     */ 
/*     */   public int getEndLine()
/*     */   {
/* 231 */     return this.bufline[this.bufpos];
/*     */   }
/*     */ 
/*     */   public int getBeginColumn()
/*     */   {
/* 236 */     return this.bufcolumn[this.tokenBegin];
/*     */   }
/*     */ 
/*     */   public int getBeginLine()
/*     */   {
/* 241 */     return this.bufline[this.tokenBegin];
/*     */   }
/*     */ 
/*     */   public void backup(int amount)
/*     */   {
/* 247 */     this.inBuf += amount;
/* 248 */     if (this.bufpos -= amount < 0)
/* 249 */       this.bufpos += this.bufsize;
/*     */   }
/*     */ 
/*     */   public SimpleCharStream(Reader dstream, int startline, int startcolumn, int buffersize)
/*     */   {
/* 256 */     this.inputStream = dstream;
/* 257 */     this.line = startline;
/* 258 */     this.column = (startcolumn - 1);
/*     */ 
/* 260 */     this.available = (this.bufsize = buffersize);
/* 261 */     this.buffer = new char[buffersize];
/* 262 */     this.bufline = new int[buffersize];
/* 263 */     this.bufcolumn = new int[buffersize];
/*     */   }
/*     */ 
/*     */   public SimpleCharStream(Reader dstream, int startline, int startcolumn)
/*     */   {
/* 270 */     this(dstream, startline, startcolumn, 4096);
/*     */   }
/*     */ 
/*     */   public SimpleCharStream(Reader dstream)
/*     */   {
/* 276 */     this(dstream, 1, 1, 4096);
/*     */   }
/*     */ 
/*     */   public void ReInit(Reader dstream, int startline, int startcolumn, int buffersize)
/*     */   {
/* 283 */     this.inputStream = dstream;
/* 284 */     this.line = startline;
/* 285 */     this.column = (startcolumn - 1);
/*     */ 
/* 287 */     if ((this.buffer == null) || (buffersize != this.buffer.length))
/*     */     {
/* 289 */       this.available = (this.bufsize = buffersize);
/* 290 */       this.buffer = new char[buffersize];
/* 291 */       this.bufline = new int[buffersize];
/* 292 */       this.bufcolumn = new int[buffersize];
/*     */     }
/* 294 */     this.prevCharIsLF = (this.prevCharIsCR = 0);
/* 295 */     this.tokenBegin = (this.inBuf = this.maxNextCharInd = 0);
/* 296 */     this.bufpos = -1;
/*     */   }
/*     */ 
/*     */   public void ReInit(Reader dstream, int startline, int startcolumn)
/*     */   {
/* 303 */     ReInit(dstream, startline, startcolumn, 4096);
/*     */   }
/*     */ 
/*     */   public void ReInit(Reader dstream)
/*     */   {
/* 309 */     ReInit(dstream, 1, 1, 4096);
/*     */   }
/*     */ 
/*     */   public SimpleCharStream(InputStream dstream, String encoding, int startline, int startcolumn, int buffersize)
/*     */     throws UnsupportedEncodingException
/*     */   {
/* 315 */     this(encoding == null ? new InputStreamReader(dstream) : new InputStreamReader(dstream, encoding), startline, startcolumn, buffersize);
/*     */   }
/*     */ 
/*     */   public SimpleCharStream(InputStream dstream, int startline, int startcolumn, int buffersize)
/*     */   {
/* 322 */     this(new InputStreamReader(dstream), startline, startcolumn, buffersize);
/*     */   }
/*     */ 
/*     */   public SimpleCharStream(InputStream dstream, String encoding, int startline, int startcolumn)
/*     */     throws UnsupportedEncodingException
/*     */   {
/* 329 */     this(dstream, encoding, startline, startcolumn, 4096);
/*     */   }
/*     */ 
/*     */   public SimpleCharStream(InputStream dstream, int startline, int startcolumn)
/*     */   {
/* 336 */     this(dstream, startline, startcolumn, 4096);
/*     */   }
/*     */ 
/*     */   public SimpleCharStream(InputStream dstream, String encoding)
/*     */     throws UnsupportedEncodingException
/*     */   {
/* 342 */     this(dstream, encoding, 1, 1, 4096);
/*     */   }
/*     */ 
/*     */   public SimpleCharStream(InputStream dstream)
/*     */   {
/* 348 */     this(dstream, 1, 1, 4096);
/*     */   }
/*     */ 
/*     */   public void ReInit(InputStream dstream, String encoding, int startline, int startcolumn, int buffersize)
/*     */     throws UnsupportedEncodingException
/*     */   {
/* 355 */     ReInit(encoding == null ? new InputStreamReader(dstream) : new InputStreamReader(dstream, encoding), startline, startcolumn, buffersize);
/*     */   }
/*     */ 
/*     */   public void ReInit(InputStream dstream, int startline, int startcolumn, int buffersize)
/*     */   {
/* 362 */     ReInit(new InputStreamReader(dstream), startline, startcolumn, buffersize);
/*     */   }
/*     */ 
/*     */   public void ReInit(InputStream dstream, String encoding)
/*     */     throws UnsupportedEncodingException
/*     */   {
/* 368 */     ReInit(dstream, encoding, 1, 1, 4096);
/*     */   }
/*     */ 
/*     */   public void ReInit(InputStream dstream)
/*     */   {
/* 374 */     ReInit(dstream, 1, 1, 4096);
/*     */   }
/*     */ 
/*     */   public void ReInit(InputStream dstream, String encoding, int startline, int startcolumn)
/*     */     throws UnsupportedEncodingException
/*     */   {
/* 380 */     ReInit(dstream, encoding, startline, startcolumn, 4096);
/*     */   }
/*     */ 
/*     */   public void ReInit(InputStream dstream, int startline, int startcolumn)
/*     */   {
/* 386 */     ReInit(dstream, startline, startcolumn, 4096);
/*     */   }
/*     */ 
/*     */   public String GetImage()
/*     */   {
/* 391 */     if (this.bufpos >= this.tokenBegin) {
/* 392 */       return new String(this.buffer, this.tokenBegin, this.bufpos - this.tokenBegin + 1);
/*     */     }
/* 394 */     return new String(this.buffer, this.tokenBegin, this.bufsize - this.tokenBegin) + new String(this.buffer, 0, this.bufpos + 1);
/*     */   }
/*     */ 
/*     */   public char[] GetSuffix(int len)
/*     */   {
/* 401 */     char[] ret = new char[len];
/*     */ 
/* 403 */     if (this.bufpos + 1 >= len) {
/* 404 */       System.arraycopy(this.buffer, this.bufpos - len + 1, ret, 0, len);
/*     */     }
/*     */     else {
/* 407 */       System.arraycopy(this.buffer, this.bufsize - (len - this.bufpos - 1), ret, 0, len - this.bufpos - 1);
/*     */ 
/* 409 */       System.arraycopy(this.buffer, 0, ret, len - this.bufpos - 1, this.bufpos + 1);
/*     */     }
/*     */ 
/* 412 */     return ret;
/*     */   }
/*     */ 
/*     */   public void Done()
/*     */   {
/* 418 */     this.buffer = null;
/* 419 */     this.bufline = null;
/* 420 */     this.bufcolumn = null;
/*     */   }
/*     */ 
/*     */   public void adjustBeginLineColumn(int newLine, int newCol)
/*     */   {
/* 428 */     int start = this.tokenBegin;
/*     */     int len;
/*     */     int len;
/* 431 */     if (this.bufpos >= this.tokenBegin)
/*     */     {
/* 433 */       len = this.bufpos - this.tokenBegin + this.inBuf + 1;
/*     */     }
/*     */     else
/*     */     {
/* 437 */       len = this.bufsize - this.tokenBegin + this.bufpos + 1 + this.inBuf;
/*     */     }
/*     */ 
/* 440 */     int i = 0; int j = 0; int k = 0;
/* 441 */     int nextColDiff = 0; int columnDiff = 0;
/*     */ 
/* 443 */     while ((i < len) && (this.bufline[(j = start % this.bufsize)] == this.bufline[(k = ++start % this.bufsize)]))
/*     */     {
/* 445 */       this.bufline[j] = newLine;
/* 446 */       nextColDiff = columnDiff + this.bufcolumn[k] - this.bufcolumn[j];
/* 447 */       this.bufcolumn[j] = (newCol + columnDiff);
/* 448 */       columnDiff = nextColDiff;
/* 449 */       i++;
/*     */     }
/*     */ 
/* 452 */     if (i < len)
/*     */     {
/* 454 */       this.bufline[j] = (newLine++);
/* 455 */       this.bufcolumn[j] = (newCol + columnDiff);
/*     */ 
/* 457 */       while (i++ < len)
/*     */       {
/* 459 */         if (this.bufline[(j = start % this.bufsize)] != this.bufline[(++start % this.bufsize)])
/* 460 */           this.bufline[j] = (newLine++);
/*     */         else {
/* 462 */           this.bufline[j] = newLine;
/*     */         }
/*     */       }
/*     */     }
/* 466 */     this.line = this.bufline[j];
/* 467 */     this.column = this.bufcolumn[j];
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.preflight.javacc.SimpleCharStream
 * JD-Core Version:    0.6.2
 */