/*     */ package org.antlr.runtime;
/*     */ 
/*     */ import java.util.List;
/*     */ 
/*     */ public class CommonTokenStream extends BufferedTokenStream
/*     */ {
/*  50 */   protected int channel = 0;
/*     */ 
/*     */   public CommonTokenStream() {
/*     */   }
/*     */   public CommonTokenStream(TokenSource tokenSource) {
/*  55 */     super(tokenSource);
/*     */   }
/*     */ 
/*     */   public CommonTokenStream(TokenSource tokenSource, int channel) {
/*  59 */     this(tokenSource);
/*  60 */     this.channel = channel;
/*     */   }
/*     */ 
/*     */   public void consume()
/*     */   {
/*  65 */     if (this.p == -1) setup();
/*  66 */     this.p += 1;
/*  67 */     sync(this.p);
/*  68 */     while (((Token)this.tokens.get(this.p)).getChannel() != this.channel) {
/*  69 */       this.p += 1;
/*  70 */       sync(this.p);
/*     */     }
/*     */   }
/*     */ 
/*     */   protected Token LB(int k) {
/*  75 */     if ((k == 0) || (this.p - k < 0)) return null;
/*     */ 
/*  77 */     int i = this.p;
/*  78 */     int n = 1;
/*     */ 
/*  80 */     while (n <= k)
/*     */     {
/*  82 */       i = skipOffTokenChannelsReverse(i - 1);
/*  83 */       n++;
/*     */     }
/*  85 */     if (i < 0) return null;
/*  86 */     return (Token)this.tokens.get(i);
/*     */   }
/*     */ 
/*     */   public Token LT(int k)
/*     */   {
/*  91 */     if (this.p == -1) setup();
/*  92 */     if (k == 0) return null;
/*  93 */     if (k < 0) return LB(-k);
/*  94 */     int i = this.p;
/*  95 */     int n = 1;
/*     */ 
/*  97 */     while (n < k)
/*     */     {
/*  99 */       i = skipOffTokenChannels(i + 1);
/* 100 */       n++;
/*     */     }
/* 102 */     if (i > this.range) this.range = i;
/* 103 */     return (Token)this.tokens.get(i);
/*     */   }
/*     */ 
/*     */   protected int skipOffTokenChannels(int i)
/*     */   {
/* 110 */     sync(i);
/* 111 */     while (((Token)this.tokens.get(i)).getChannel() != this.channel) {
/* 112 */       i++;
/* 113 */       sync(i);
/*     */     }
/* 115 */     return i;
/*     */   }
/*     */ 
/*     */   protected int skipOffTokenChannelsReverse(int i) {
/* 119 */     while ((i >= 0) && (((Token)this.tokens.get(i)).getChannel() != this.channel)) {
/* 120 */       i--;
/*     */     }
/* 122 */     return i;
/*     */   }
/*     */ 
/*     */   protected void setup() {
/* 126 */     this.p = 0;
/* 127 */     sync(0);
/* 128 */     int i = 0;
/* 129 */     while (((Token)this.tokens.get(i)).getChannel() != this.channel) {
/* 130 */       i++;
/* 131 */       sync(i);
/*     */     }
/* 133 */     this.p = i;
/*     */   }
/*     */ 
/*     */   public void setTokenSource(TokenSource tokenSource)
/*     */   {
/* 138 */     super.setTokenSource(tokenSource);
/* 139 */     this.channel = 0;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.antlr.runtime.CommonTokenStream
 * JD-Core Version:    0.6.2
 */