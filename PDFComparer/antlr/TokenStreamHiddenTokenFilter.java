/*     */ package antlr;
/*     */ 
/*     */ import antlr.collections.impl.BitSet;
/*     */ 
/*     */ public class TokenStreamHiddenTokenFilter extends TokenStreamBasicFilter
/*     */   implements TokenStream
/*     */ {
/*     */   protected BitSet hideMask;
/*     */   protected CommonHiddenStreamToken nextMonitoredToken;
/*     */   protected CommonHiddenStreamToken lastHiddenToken;
/*  30 */   protected CommonHiddenStreamToken firstHidden = null;
/*     */ 
/*     */   public TokenStreamHiddenTokenFilter(TokenStream paramTokenStream) {
/*  33 */     super(paramTokenStream);
/*  34 */     this.hideMask = new BitSet();
/*     */   }
/*     */ 
/*     */   protected void consume() throws TokenStreamException {
/*  38 */     this.nextMonitoredToken = ((CommonHiddenStreamToken)this.input.nextToken());
/*     */   }
/*     */ 
/*     */   private void consumeFirst() throws TokenStreamException {
/*  42 */     consume();
/*     */ 
/*  46 */     CommonHiddenStreamToken localCommonHiddenStreamToken = null;
/*     */ 
/*  48 */     while ((this.hideMask.member(LA(1).getType())) || (this.discardMask.member(LA(1).getType()))) {
/*  49 */       if (this.hideMask.member(LA(1).getType())) {
/*  50 */         if (localCommonHiddenStreamToken == null) {
/*  51 */           localCommonHiddenStreamToken = LA(1);
/*     */         }
/*     */         else {
/*  54 */           localCommonHiddenStreamToken.setHiddenAfter(LA(1));
/*  55 */           LA(1).setHiddenBefore(localCommonHiddenStreamToken);
/*  56 */           localCommonHiddenStreamToken = LA(1);
/*     */         }
/*  58 */         this.lastHiddenToken = localCommonHiddenStreamToken;
/*  59 */         if (this.firstHidden == null) {
/*  60 */           this.firstHidden = localCommonHiddenStreamToken;
/*     */         }
/*     */       }
/*  63 */       consume();
/*     */     }
/*     */   }
/*     */ 
/*     */   public BitSet getDiscardMask() {
/*  68 */     return this.discardMask;
/*     */   }
/*     */ 
/*     */   public CommonHiddenStreamToken getHiddenAfter(CommonHiddenStreamToken paramCommonHiddenStreamToken)
/*     */   {
/*  75 */     return paramCommonHiddenStreamToken.getHiddenAfter();
/*     */   }
/*     */ 
/*     */   public CommonHiddenStreamToken getHiddenBefore(CommonHiddenStreamToken paramCommonHiddenStreamToken)
/*     */   {
/*  82 */     return paramCommonHiddenStreamToken.getHiddenBefore();
/*     */   }
/*     */ 
/*     */   public BitSet getHideMask() {
/*  86 */     return this.hideMask;
/*     */   }
/*     */ 
/*     */   public CommonHiddenStreamToken getInitialHiddenToken()
/*     */   {
/*  93 */     return this.firstHidden;
/*     */   }
/*     */ 
/*     */   public void hide(int paramInt) {
/*  97 */     this.hideMask.add(paramInt);
/*     */   }
/*     */ 
/*     */   public void hide(BitSet paramBitSet) {
/* 101 */     this.hideMask = paramBitSet;
/*     */   }
/*     */ 
/*     */   protected CommonHiddenStreamToken LA(int paramInt) {
/* 105 */     return this.nextMonitoredToken;
/*     */   }
/*     */ 
/*     */   public Token nextToken()
/*     */     throws TokenStreamException
/*     */   {
/* 121 */     if (LA(1) == null) {
/* 122 */       consumeFirst();
/*     */     }
/*     */ 
/* 127 */     CommonHiddenStreamToken localCommonHiddenStreamToken1 = LA(1);
/*     */ 
/* 129 */     localCommonHiddenStreamToken1.setHiddenBefore(this.lastHiddenToken);
/* 130 */     this.lastHiddenToken = null;
/*     */ 
/* 134 */     consume();
/* 135 */     CommonHiddenStreamToken localCommonHiddenStreamToken2 = localCommonHiddenStreamToken1;
/*     */ 
/* 137 */     while ((this.hideMask.member(LA(1).getType())) || (this.discardMask.member(LA(1).getType()))) {
/* 138 */       if (this.hideMask.member(LA(1).getType()))
/*     */       {
/* 141 */         localCommonHiddenStreamToken2.setHiddenAfter(LA(1));
/*     */ 
/* 143 */         if (localCommonHiddenStreamToken2 != localCommonHiddenStreamToken1) {
/* 144 */           LA(1).setHiddenBefore(localCommonHiddenStreamToken2);
/*     */         }
/* 146 */         localCommonHiddenStreamToken2 = this.lastHiddenToken = LA(1);
/*     */       }
/* 148 */       consume();
/*     */     }
/* 150 */     return localCommonHiddenStreamToken1;
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     antlr.TokenStreamHiddenTokenFilter
 * JD-Core Version:    0.6.2
 */