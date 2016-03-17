/*    */ package antlr;
/*    */ 
/*    */ public class CharQueue
/*    */ {
/*    */   protected char[] buffer;
/*    */   private int sizeLessOne;
/*    */   private int offset;
/*    */   protected int nbrEntries;
/*    */ 
/*    */   public CharQueue(int paramInt)
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
/* 35 */     init(i);
/*    */   }
/*    */ 
/*    */   public final void append(char paramChar)
/*    */   {
/* 42 */     if (this.nbrEntries == this.buffer.length) {
/* 43 */       expand();
/*    */     }
/* 45 */     this.buffer[(this.offset + this.nbrEntries & this.sizeLessOne)] = paramChar;
/* 46 */     this.nbrEntries += 1;
/*    */   }
/*    */ 
/*    */   public final char elementAt(int paramInt)
/*    */   {
/* 53 */     return this.buffer[(this.offset + paramInt & this.sizeLessOne)];
/*    */   }
/*    */ 
/*    */   private final void expand()
/*    */   {
/* 58 */     char[] arrayOfChar = new char[this.buffer.length * 2];
/*    */ 
/* 62 */     for (int i = 0; i < this.buffer.length; i++) {
/* 63 */       arrayOfChar[i] = elementAt(i);
/*    */     }
/*    */ 
/* 66 */     this.buffer = arrayOfChar;
/* 67 */     this.sizeLessOne = (this.buffer.length - 1);
/* 68 */     this.offset = 0;
/*    */   }
/*    */ 
/*    */   public void init(int paramInt)
/*    */   {
/* 76 */     this.buffer = new char[paramInt];
/*    */ 
/* 78 */     this.sizeLessOne = (paramInt - 1);
/* 79 */     this.offset = 0;
/* 80 */     this.nbrEntries = 0;
/*    */   }
/*    */ 
/*    */   public final void reset()
/*    */   {
/* 86 */     this.offset = 0;
/* 87 */     this.nbrEntries = 0;
/*    */   }
/*    */ 
/*    */   public final void removeFirst()
/*    */   {
/* 92 */     this.offset = (this.offset + 1 & this.sizeLessOne);
/* 93 */     this.nbrEntries -= 1;
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     antlr.CharQueue
 * JD-Core Version:    0.6.2
 */