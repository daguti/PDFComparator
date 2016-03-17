/*     */ package org.antlr.runtime.debug;
/*     */ 
/*     */ import org.antlr.runtime.Token;
/*     */ import org.antlr.runtime.TokenSource;
/*     */ import org.antlr.runtime.TokenStream;
/*     */ 
/*     */ public class DebugTokenStream
/*     */   implements TokenStream
/*     */ {
/*     */   protected DebugEventListener dbg;
/*     */   public TokenStream input;
/*  37 */   protected boolean initialStreamState = true;
/*     */   protected int lastMarker;
/*     */ 
/*     */   public DebugTokenStream(TokenStream input, DebugEventListener dbg)
/*     */   {
/*  43 */     this.input = input;
/*  44 */     setDebugListener(dbg);
/*     */ 
/*  47 */     input.LT(1);
/*     */   }
/*     */ 
/*     */   public void setDebugListener(DebugEventListener dbg) {
/*  51 */     this.dbg = dbg;
/*     */   }
/*     */ 
/*     */   public void consume() {
/*  55 */     if (this.initialStreamState) {
/*  56 */       consumeInitialHiddenTokens();
/*     */     }
/*  58 */     int a = this.input.index();
/*  59 */     Token t = this.input.LT(1);
/*  60 */     this.input.consume();
/*  61 */     int b = this.input.index();
/*  62 */     this.dbg.consumeToken(t);
/*  63 */     if (b > a + 1)
/*     */     {
/*  65 */       for (int i = a + 1; i < b; i++)
/*  66 */         this.dbg.consumeHiddenToken(this.input.get(i));
/*     */     }
/*     */   }
/*     */ 
/*     */   protected void consumeInitialHiddenTokens()
/*     */   {
/*  73 */     int firstOnChannelTokenIndex = this.input.index();
/*  74 */     for (int i = 0; i < firstOnChannelTokenIndex; i++) {
/*  75 */       this.dbg.consumeHiddenToken(this.input.get(i));
/*     */     }
/*  77 */     this.initialStreamState = false;
/*     */   }
/*     */ 
/*     */   public Token LT(int i) {
/*  81 */     if (this.initialStreamState) {
/*  82 */       consumeInitialHiddenTokens();
/*     */     }
/*  84 */     this.dbg.LT(i, this.input.LT(i));
/*  85 */     return this.input.LT(i);
/*     */   }
/*     */ 
/*     */   public int LA(int i) {
/*  89 */     if (this.initialStreamState) {
/*  90 */       consumeInitialHiddenTokens();
/*     */     }
/*  92 */     this.dbg.LT(i, this.input.LT(i));
/*  93 */     return this.input.LA(i);
/*     */   }
/*     */ 
/*     */   public Token get(int i) {
/*  97 */     return this.input.get(i);
/*     */   }
/*     */ 
/*     */   public int mark() {
/* 101 */     this.lastMarker = this.input.mark();
/* 102 */     this.dbg.mark(this.lastMarker);
/* 103 */     return this.lastMarker;
/*     */   }
/*     */ 
/*     */   public int index() {
/* 107 */     return this.input.index();
/*     */   }
/*     */ 
/*     */   public int range() {
/* 111 */     return this.input.range();
/*     */   }
/*     */ 
/*     */   public void rewind(int marker) {
/* 115 */     this.dbg.rewind(marker);
/* 116 */     this.input.rewind(marker);
/*     */   }
/*     */ 
/*     */   public void rewind() {
/* 120 */     this.dbg.rewind();
/* 121 */     this.input.rewind(this.lastMarker);
/*     */   }
/*     */ 
/*     */   public void release(int marker)
/*     */   {
/*     */   }
/*     */ 
/*     */   public void seek(int index)
/*     */   {
/* 130 */     this.input.seek(index);
/*     */   }
/*     */ 
/*     */   public int size() {
/* 134 */     return this.input.size();
/*     */   }
/*     */ 
/*     */   public TokenSource getTokenSource() {
/* 138 */     return this.input.getTokenSource();
/*     */   }
/*     */ 
/*     */   public String getSourceName() {
/* 142 */     return getTokenSource().getSourceName();
/*     */   }
/*     */ 
/*     */   public String toString() {
/* 146 */     return this.input.toString();
/*     */   }
/*     */ 
/*     */   public String toString(int start, int stop) {
/* 150 */     return this.input.toString(start, stop);
/*     */   }
/*     */ 
/*     */   public String toString(Token start, Token stop) {
/* 154 */     return this.input.toString(start, stop);
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.antlr.runtime.debug.DebugTokenStream
 * JD-Core Version:    0.6.2
 */