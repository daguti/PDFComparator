/*     */ package org.apache.pdfbox.pdfparser;
/*     */ 
/*     */ import java.io.BufferedOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.OutputStream;
/*     */ 
/*     */ class EndstreamOutputStream extends BufferedOutputStream
/*     */ {
/*  37 */   private boolean hasCR = false;
/*  38 */   private boolean hasLF = false;
/*  39 */   private int pos = 0;
/*  40 */   private boolean mustFilter = true;
/*     */ 
/*     */   public EndstreamOutputStream(OutputStream out)
/*     */   {
/*  44 */     super(out);
/*     */   }
/*     */ 
/*     */   public void write(byte[] b, int off, int len)
/*     */     throws IOException
/*     */   {
/*  60 */     if ((this.pos == 0) && (len > 10))
/*     */     {
/*  63 */       this.mustFilter = false;
/*  64 */       for (int i = 0; i < 10; i++)
/*     */       {
/*  67 */         if ((b[i] < 9) || ((b[i] > 10) && (b[i] < 32) && (b[i] != 13)))
/*     */         {
/*  70 */           this.mustFilter = true;
/*  71 */           break;
/*     */         }
/*     */       }
/*     */     }
/*  75 */     if (this.mustFilter)
/*     */     {
/*  78 */       if (this.hasCR)
/*     */       {
/*  80 */         if ((!this.hasLF) && (len == 1) && (b[off] == 10))
/*     */         {
/*  85 */           this.hasCR = false;
/*  86 */           return;
/*     */         }
/*  88 */         super.write(13);
/*  89 */         this.hasCR = false;
/*     */       }
/*  91 */       if (this.hasLF)
/*     */       {
/*  93 */         super.write(10);
/*  94 */         this.hasLF = false;
/*     */       }
/*     */ 
/*  97 */       if (len > 0)
/*     */       {
/*  99 */         if (b[(off + len - 1)] == 13)
/*     */         {
/* 101 */           this.hasCR = true;
/* 102 */           len--;
/*     */         }
/* 104 */         else if (b[(off + len - 1)] == 10)
/*     */         {
/* 106 */           this.hasLF = true;
/* 107 */           len--;
/* 108 */           if ((len > 0) && (b[(off + len - 1)] == 13))
/*     */           {
/* 110 */             this.hasCR = true;
/* 111 */             len--;
/*     */           }
/*     */         }
/*     */       }
/*     */     }
/* 116 */     super.write(b, off, len);
/* 117 */     this.pos += len;
/*     */   }
/*     */ 
/*     */   public void flush()
/*     */     throws IOException
/*     */   {
/* 130 */     if ((this.hasCR) && (!this.hasLF))
/*     */     {
/* 132 */       super.write(13);
/* 133 */       this.pos += 1;
/*     */     }
/* 135 */     this.hasCR = false;
/* 136 */     this.hasLF = false;
/* 137 */     super.flush();
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.apache.pdfbox.pdfparser.EndstreamOutputStream
 * JD-Core Version:    0.6.2
 */