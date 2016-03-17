/*    */ package antlr.debug;
/*    */ 
/*    */ public abstract class GuessingEvent extends Event
/*    */ {
/*    */   private int guessing;
/*    */ 
/*    */   public GuessingEvent(Object paramObject)
/*    */   {
/*  8 */     super(paramObject);
/*    */   }
/*    */   public GuessingEvent(Object paramObject, int paramInt) {
/* 11 */     super(paramObject, paramInt);
/*    */   }
/*    */   public int getGuessing() {
/* 14 */     return this.guessing;
/*    */   }
/*    */   void setGuessing(int paramInt) {
/* 17 */     this.guessing = paramInt;
/*    */   }
/*    */ 
/*    */   void setValues(int paramInt1, int paramInt2) {
/* 21 */     super.setValues(paramInt1);
/* 22 */     setGuessing(paramInt2);
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     antlr.debug.GuessingEvent
 * JD-Core Version:    0.6.2
 */