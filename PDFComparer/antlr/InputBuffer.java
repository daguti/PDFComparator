/*     */ package antlr;
/*     */ 
/*     */ public abstract class InputBuffer
/*     */ {
/*  32 */   protected int nMarkers = 0;
/*     */ 
/*  35 */   protected int markerOffset = 0;
/*     */ 
/*  38 */   protected int numToConsume = 0;
/*     */   protected CharQueue queue;
/*     */ 
/*     */   public InputBuffer()
/*     */   {
/*  45 */     this.queue = new CharQueue(1);
/*     */   }
/*     */ 
/*     */   public void commit()
/*     */   {
/*  55 */     this.nMarkers -= 1;
/*     */   }
/*     */ 
/*     */   public void consume()
/*     */   {
/*  60 */     this.numToConsume += 1;
/*     */   }
/*     */ 
/*     */   public abstract void fill(int paramInt) throws CharStreamException;
/*     */ 
/*     */   public String getLAChars()
/*     */   {
/*  67 */     StringBuffer localStringBuffer = new StringBuffer();
/*  68 */     for (int i = this.markerOffset; i < this.queue.nbrEntries; i++)
/*  69 */       localStringBuffer.append(this.queue.elementAt(i));
/*  70 */     return localStringBuffer.toString();
/*     */   }
/*     */ 
/*     */   public String getMarkedChars() {
/*  74 */     StringBuffer localStringBuffer = new StringBuffer();
/*  75 */     for (int i = 0; i < this.markerOffset; i++)
/*  76 */       localStringBuffer.append(this.queue.elementAt(i));
/*  77 */     return localStringBuffer.toString();
/*     */   }
/*     */ 
/*     */   public boolean isMarked() {
/*  81 */     return this.nMarkers != 0;
/*     */   }
/*     */ 
/*     */   public char LA(int paramInt) throws CharStreamException
/*     */   {
/*  86 */     fill(paramInt);
/*  87 */     return this.queue.elementAt(this.markerOffset + paramInt - 1);
/*     */   }
/*     */ 
/*     */   public int mark()
/*     */   {
/*  94 */     syncConsume();
/*  95 */     this.nMarkers += 1;
/*  96 */     return this.markerOffset;
/*     */   }
/*     */ 
/*     */   public void rewind(int paramInt)
/*     */   {
/* 103 */     syncConsume();
/* 104 */     this.markerOffset = paramInt;
/* 105 */     this.nMarkers -= 1;
/*     */   }
/*     */ 
/*     */   public void reset()
/*     */   {
/* 111 */     this.nMarkers = 0;
/* 112 */     this.markerOffset = 0;
/* 113 */     this.numToConsume = 0;
/* 114 */     this.queue.reset();
/*     */   }
/*     */ 
/*     */   protected void syncConsume()
/*     */   {
/* 119 */     while (this.numToConsume > 0) {
/* 120 */       if (this.nMarkers > 0)
/*     */       {
/* 122 */         this.markerOffset += 1;
/*     */       }
/*     */       else
/*     */       {
/* 126 */         this.queue.removeFirst();
/*     */       }
/* 128 */       this.numToConsume -= 1;
/*     */     }
/*     */   }
/*     */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     antlr.InputBuffer
 * JD-Core Version:    0.6.2
 */