/*     */ package antlr;
/*     */ 
/*     */ public class TokenBuffer
/*     */ {
/*     */   protected TokenStream input;
/*  33 */   int nMarkers = 0;
/*     */ 
/*  36 */   int markerOffset = 0;
/*     */ 
/*  39 */   int numToConsume = 0;
/*     */   TokenQueue queue;
/*     */ 
/*     */   public TokenBuffer(TokenStream paramTokenStream)
/*     */   {
/*  46 */     this.input = paramTokenStream;
/*  47 */     this.queue = new TokenQueue(1);
/*     */   }
/*     */ 
/*     */   public final void reset()
/*     */   {
/*  52 */     this.nMarkers = 0;
/*  53 */     this.markerOffset = 0;
/*  54 */     this.numToConsume = 0;
/*  55 */     this.queue.reset();
/*     */   }
/*     */ 
/*     */   public final void consume()
/*     */   {
/*  60 */     this.numToConsume += 1;
/*     */   }
/*     */ 
/*     */   private final void fill(int paramInt) throws TokenStreamException
/*     */   {
/*  65 */     syncConsume();
/*     */ 
/*  67 */     while (this.queue.nbrEntries < paramInt + this.markerOffset)
/*     */     {
/*  69 */       this.queue.append(this.input.nextToken());
/*     */     }
/*     */   }
/*     */ 
/*     */   public TokenStream getInput()
/*     */   {
/*  75 */     return this.input;
/*     */   }
/*     */ 
/*     */   public final int LA(int paramInt) throws TokenStreamException
/*     */   {
/*  80 */     fill(paramInt);
/*  81 */     return this.queue.elementAt(this.markerOffset + paramInt - 1).getType();
/*     */   }
/*     */ 
/*     */   public final Token LT(int paramInt) throws TokenStreamException
/*     */   {
/*  86 */     fill(paramInt);
/*  87 */     return this.queue.elementAt(this.markerOffset + paramInt - 1);
/*     */   }
/*     */ 
/*     */   public final int mark()
/*     */   {
/*  94 */     syncConsume();
/*     */ 
/*  97 */     this.nMarkers += 1;
/*  98 */     return this.markerOffset;
/*     */   }
/*     */ 
/*     */   public final void rewind(int paramInt)
/*     */   {
/* 105 */     syncConsume();
/* 106 */     this.markerOffset = paramInt;
/* 107 */     this.nMarkers -= 1;
/*     */   }
/*     */ 
/*     */   private final void syncConsume()
/*     */   {
/* 114 */     while (this.numToConsume > 0) {
/* 115 */       if (this.nMarkers > 0)
/*     */       {
/* 117 */         this.markerOffset += 1;
/*     */       }
/*     */       else
/*     */       {
/* 121 */         this.queue.removeFirst();
/*     */       }
/* 123 */       this.numToConsume -= 1;
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     antlr.TokenBuffer
 * JD-Core Version:    0.6.2
 */