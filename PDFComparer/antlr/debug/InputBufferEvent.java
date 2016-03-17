/*    */ package antlr.debug;
/*    */ 
/*    */ public class InputBufferEvent extends Event
/*    */ {
/*    */   char c;
/*    */   int lookaheadAmount;
/*    */   public static final int CONSUME = 0;
/*    */   public static final int LA = 1;
/*    */   public static final int MARK = 2;
/*    */   public static final int REWIND = 3;
/*    */ 
/*    */   public InputBufferEvent(Object paramObject)
/*    */   {
/* 17 */     super(paramObject);
/*    */   }
/*    */ 
/*    */   public InputBufferEvent(Object paramObject, int paramInt1, char paramChar, int paramInt2)
/*    */   {
/* 24 */     super(paramObject);
/* 25 */     setValues(paramInt1, paramChar, paramInt2);
/*    */   }
/*    */   public char getChar() {
/* 28 */     return this.c;
/*    */   }
/*    */   public int getLookaheadAmount() {
/* 31 */     return this.lookaheadAmount;
/*    */   }
/*    */   void setChar(char paramChar) {
/* 34 */     this.c = paramChar;
/*    */   }
/*    */   void setLookaheadAmount(int paramInt) {
/* 37 */     this.lookaheadAmount = paramInt;
/*    */   }
/*    */ 
/*    */   void setValues(int paramInt1, char paramChar, int paramInt2) {
/* 41 */     super.setValues(paramInt1);
/* 42 */     setChar(paramChar);
/* 43 */     setLookaheadAmount(paramInt2);
/*    */   }
/*    */   public String toString() {
/* 46 */     return "CharBufferEvent [" + (getType() == 0 ? "CONSUME, " : "LA, ") + getChar() + "," + getLookaheadAmount() + "]";
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     antlr.debug.InputBufferEvent
 * JD-Core Version:    0.6.2
 */