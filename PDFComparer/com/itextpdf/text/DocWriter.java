/*     */ package com.itextpdf.text;
/*     */ 
/*     */ import com.itextpdf.text.pdf.OutputStreamCounter;
/*     */ import java.io.BufferedOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStream;
/*     */ import java.util.Iterator;
/*     */ import java.util.Properties;
/*     */ import java.util.Set;
/*     */ 
/*     */ public abstract class DocWriter
/*     */   implements DocListener
/*     */ {
/*     */   public static final byte NEWLINE = 10;
/*     */   public static final byte TAB = 9;
/*     */   public static final byte LT = 60;
/*     */   public static final byte SPACE = 32;
/*     */   public static final byte EQUALS = 61;
/*     */   public static final byte QUOTE = 34;
/*     */   public static final byte GT = 62;
/*     */   public static final byte FORWARD = 47;
/*     */   protected Rectangle pageSize;
/*     */   protected Document document;
/*     */   protected OutputStreamCounter os;
/* 109 */   protected boolean open = false;
/*     */ 
/* 112 */   protected boolean pause = false;
/*     */ 
/* 115 */   protected boolean closeStream = true;
/*     */ 
/*     */   protected DocWriter()
/*     */   {
/*     */   }
/*     */ 
/*     */   protected DocWriter(Document document, OutputStream os)
/*     */   {
/* 130 */     this.document = document;
/* 131 */     this.os = new OutputStreamCounter(new BufferedOutputStream(os));
/*     */   }
/*     */ 
/*     */   public boolean add(Element element)
/*     */     throws DocumentException
/*     */   {
/* 148 */     return false;
/*     */   }
/*     */ 
/*     */   public void open()
/*     */   {
/* 156 */     this.open = true;
/*     */   }
/*     */ 
/*     */   public boolean setPageSize(Rectangle pageSize)
/*     */   {
/* 167 */     this.pageSize = pageSize;
/* 168 */     return true;
/*     */   }
/*     */ 
/*     */   public boolean setMargins(float marginLeft, float marginRight, float marginTop, float marginBottom)
/*     */   {
/* 184 */     return false;
/*     */   }
/*     */ 
/*     */   public boolean newPage()
/*     */   {
/* 196 */     if (!this.open) {
/* 197 */       return false;
/*     */     }
/* 199 */     return true;
/*     */   }
/*     */ 
/*     */   public void resetPageCount()
/*     */   {
/*     */   }
/*     */ 
/*     */   public void setPageCount(int pageN)
/*     */   {
/*     */   }
/*     */ 
/*     */   public void close()
/*     */   {
/* 232 */     this.open = false;
/*     */     try {
/* 234 */       this.os.flush();
/* 235 */       if (this.closeStream)
/* 236 */         this.os.close();
/*     */     }
/*     */     catch (IOException ioe) {
/* 239 */       throw new ExceptionConverter(ioe);
/*     */     }
/*     */   }
/*     */ 
/*     */   public static final byte[] getISOBytes(String text)
/*     */   {
/* 253 */     if (text == null)
/* 254 */       return null;
/* 255 */     int len = text.length();
/* 256 */     byte[] b = new byte[len];
/* 257 */     for (int k = 0; k < len; k++)
/* 258 */       b[k] = ((byte)text.charAt(k));
/* 259 */     return b;
/*     */   }
/*     */ 
/*     */   public void pause()
/*     */   {
/* 267 */     this.pause = true;
/*     */   }
/*     */ 
/*     */   public boolean isPaused()
/*     */   {
/* 277 */     return this.pause;
/*     */   }
/*     */ 
/*     */   public void resume()
/*     */   {
/* 285 */     this.pause = false;
/*     */   }
/*     */ 
/*     */   public void flush()
/*     */   {
/*     */     try
/*     */     {
/* 294 */       this.os.flush();
/*     */     }
/*     */     catch (IOException ioe) {
/* 297 */       throw new ExceptionConverter(ioe);
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void write(String string)
/*     */     throws IOException
/*     */   {
/* 309 */     this.os.write(getISOBytes(string));
/*     */   }
/*     */ 
/*     */   protected void addTabs(int indent)
/*     */     throws IOException
/*     */   {
/* 320 */     this.os.write(10);
/* 321 */     for (int i = 0; i < indent; i++)
/* 322 */       this.os.write(9);
/*     */   }
/*     */ 
/*     */   protected void write(String key, String value)
/*     */     throws IOException
/*     */   {
/* 336 */     this.os.write(32);
/* 337 */     write(key);
/* 338 */     this.os.write(61);
/* 339 */     this.os.write(34);
/* 340 */     write(value);
/* 341 */     this.os.write(34);
/*     */   }
/*     */ 
/*     */   protected void writeStart(String tag)
/*     */     throws IOException
/*     */   {
/* 353 */     this.os.write(60);
/* 354 */     write(tag);
/*     */   }
/*     */ 
/*     */   protected void writeEnd(String tag)
/*     */     throws IOException
/*     */   {
/* 366 */     this.os.write(60);
/* 367 */     this.os.write(47);
/* 368 */     write(tag);
/* 369 */     this.os.write(62);
/*     */   }
/*     */ 
/*     */   protected void writeEnd()
/*     */     throws IOException
/*     */   {
/* 379 */     this.os.write(32);
/* 380 */     this.os.write(47);
/* 381 */     this.os.write(62);
/*     */   }
/*     */ 
/*     */   protected boolean writeMarkupAttributes(Properties markup)
/*     */     throws IOException
/*     */   {
/* 393 */     if (markup == null) return false;
/* 394 */     Iterator attributeIterator = markup.keySet().iterator();
/*     */ 
/* 396 */     while (attributeIterator.hasNext()) {
/* 397 */       String name = String.valueOf(attributeIterator.next());
/* 398 */       write(name, markup.getProperty(name));
/*     */     }
/* 400 */     markup.clear();
/* 401 */     return true;
/*     */   }
/*     */ 
/*     */   public boolean isCloseStream()
/*     */   {
/* 409 */     return this.closeStream;
/*     */   }
/*     */ 
/*     */   public void setCloseStream(boolean closeStream)
/*     */   {
/* 417 */     this.closeStream = closeStream;
/*     */   }
/*     */ 
/*     */   public boolean setMarginMirroring(boolean MarginMirroring)
/*     */   {
/* 424 */     return false;
/*     */   }
/*     */ 
/*     */   public boolean setMarginMirroringTopBottom(boolean MarginMirroring)
/*     */   {
/* 432 */     return false;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     com.itextpdf.text.DocWriter
 * JD-Core Version:    0.6.2
 */