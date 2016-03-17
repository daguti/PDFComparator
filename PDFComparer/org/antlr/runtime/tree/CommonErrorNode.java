/*     */ package org.antlr.runtime.tree;
/*     */ 
/*     */ import org.antlr.runtime.IntStream;
/*     */ import org.antlr.runtime.MismatchedTokenException;
/*     */ import org.antlr.runtime.MissingTokenException;
/*     */ import org.antlr.runtime.NoViableAltException;
/*     */ import org.antlr.runtime.RecognitionException;
/*     */ import org.antlr.runtime.Token;
/*     */ import org.antlr.runtime.TokenStream;
/*     */ import org.antlr.runtime.UnwantedTokenException;
/*     */ 
/*     */ public class CommonErrorNode extends CommonTree
/*     */ {
/*     */   public IntStream input;
/*     */   public Token start;
/*     */   public Token stop;
/*     */   public RecognitionException trappedException;
/*     */ 
/*     */   public CommonErrorNode(TokenStream input, Token start, Token stop, RecognitionException e)
/*     */   {
/*  43 */     if ((stop == null) || ((stop.getTokenIndex() < start.getTokenIndex()) && (stop.getType() != -1)))
/*     */     {
/*  51 */       stop = start;
/*     */     }
/*  53 */     this.input = input;
/*  54 */     this.start = start;
/*  55 */     this.stop = stop;
/*  56 */     this.trappedException = e;
/*     */   }
/*     */ 
/*     */   public boolean isNil() {
/*  60 */     return false;
/*     */   }
/*     */ 
/*     */   public int getType() {
/*  64 */     return 0;
/*     */   }
/*     */ 
/*     */   public String getText() {
/*  68 */     String badText = null;
/*  69 */     if ((this.start instanceof Token)) {
/*  70 */       int i = this.start.getTokenIndex();
/*  71 */       int j = this.stop.getTokenIndex();
/*  72 */       if (this.stop.getType() == -1) {
/*  73 */         j = ((TokenStream)this.input).size();
/*     */       }
/*  75 */       badText = ((TokenStream)this.input).toString(i, j);
/*     */     }
/*  77 */     else if ((this.start instanceof Tree)) {
/*  78 */       badText = ((TreeNodeStream)this.input).toString(this.start, this.stop);
/*     */     }
/*     */     else
/*     */     {
/*  83 */       badText = "<unknown>";
/*     */     }
/*  85 */     return badText;
/*     */   }
/*     */ 
/*     */   public String toString() {
/*  89 */     if ((this.trappedException instanceof MissingTokenException)) {
/*  90 */       return "<missing type: " + ((MissingTokenException)this.trappedException).getMissingType() + ">";
/*     */     }
/*     */ 
/*  94 */     if ((this.trappedException instanceof UnwantedTokenException)) {
/*  95 */       return "<extraneous: " + ((UnwantedTokenException)this.trappedException).getUnexpectedToken() + ", resync=" + getText() + ">";
/*     */     }
/*     */ 
/*  99 */     if ((this.trappedException instanceof MismatchedTokenException)) {
/* 100 */       return "<mismatched token: " + this.trappedException.token + ", resync=" + getText() + ">";
/*     */     }
/* 102 */     if ((this.trappedException instanceof NoViableAltException)) {
/* 103 */       return "<unexpected: " + this.trappedException.token + ", resync=" + getText() + ">";
/*     */     }
/*     */ 
/* 106 */     return "<error: " + getText() + ">";
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.antlr.runtime.tree.CommonErrorNode
 * JD-Core Version:    0.6.2
 */