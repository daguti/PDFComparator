/*    */ package antlr;
/*    */ 
/*    */ class TokenQueue
/*    */ {
/*    */   private Token[] buffer;
/*    */   private int sizeLessOne;
/*    */   private int offset;
/*    */   protected int nbrEntries;
/*    */ 
/*    */   public TokenQueue(int paramInt)
/*    */   {
/* 24 */     if (paramInt < 0) {
/* 25 */       init(16);
/* 26 */       return;
/*    */     }
/*    */ 
/* 29 */     if (paramInt >= 1073741823) {
/* 30 */       init(2147483647);
/* 31 */       return;
/*    */     }
/* 33 */     for (int i = 2; i < paramInt; i *= 2);
/* 36 */     init(i);
/*    */   }
/*    */ 
/*    */   public final void append(Token paramToken)
/*    */   {
/* 43 */     if (this.nbrEntries == this.buffer.length) {
/* 44 */       expand();
/*    */     }
/* 46 */     this.buffer[(this.offset + this.nbrEntries & this.sizeLessOne)] = paramToken;
/* 47 */     this.nbrEntries += 1;
/*    */   }
/*    */ 
/*    */   public final Token elementAt(int paramInt)
/*    */   {
/* 54 */     return this.buffer[(this.offset + paramInt & this.sizeLessOne)];
/*    */   }
/*    */ 
/*    */   private final void expand()
/*    */   {
/* 59 */     Token[] arrayOfToken = new Token[this.buffer.length * 2];
/*    */ 
/* 63 */     for (int i = 0; i < this.buffer.length; i++) {
/* 64 */       arrayOfToken[i] = elementAt(i);
/*    */     }
/*    */ 
/* 67 */     this.buffer = arrayOfToken;
/* 68 */     this.sizeLessOne = (this.buffer.length - 1);
/* 69 */     this.offset = 0;
/*    */   }
/*    */ 
/*    */   private final void init(int paramInt)
/*    */   {
/* 77 */     this.buffer = new Token[paramInt];
/*    */ 
/* 79 */     this.sizeLessOne = (paramInt - 1);
/* 80 */     this.offset = 0;
/* 81 */     this.nbrEntries = 0;
/*    */   }
/*    */ 
/*    */   public final void reset()
/*    */   {
/* 87 */     this.offset = 0;
/* 88 */     this.nbrEntries = 0;
/*    */   }
/*    */ 
/*    */   public final void removeFirst()
/*    */   {
/* 93 */     this.offset = (this.offset + 1 & this.sizeLessOne);
/* 94 */     this.nbrEntries -= 1;
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     antlr.TokenQueue
 * JD-Core Version:    0.6.2
 */