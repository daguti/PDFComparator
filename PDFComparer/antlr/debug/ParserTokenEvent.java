/*    */ package antlr.debug;
/*    */ 
/*    */ public class ParserTokenEvent extends Event
/*    */ {
/*    */   private int value;
/*    */   private int amount;
/*  6 */   public static int LA = 0;
/*  7 */   public static int CONSUME = 1;
/*    */ 
/*    */   public ParserTokenEvent(Object paramObject)
/*    */   {
/* 11 */     super(paramObject);
/*    */   }
/*    */ 
/*    */   public ParserTokenEvent(Object paramObject, int paramInt1, int paramInt2, int paramInt3) {
/* 15 */     super(paramObject);
/* 16 */     setValues(paramInt1, paramInt2, paramInt3);
/*    */   }
/*    */   public int getAmount() {
/* 19 */     return this.amount;
/*    */   }
/*    */   public int getValue() {
/* 22 */     return this.value;
/*    */   }
/*    */   void setAmount(int paramInt) {
/* 25 */     this.amount = paramInt;
/*    */   }
/*    */   void setValue(int paramInt) {
/* 28 */     this.value = paramInt;
/*    */   }
/*    */ 
/*    */   void setValues(int paramInt1, int paramInt2, int paramInt3) {
/* 32 */     super.setValues(paramInt1);
/* 33 */     setAmount(paramInt2);
/* 34 */     setValue(paramInt3);
/*    */   }
/*    */   public String toString() {
/* 37 */     if (getType() == LA) {
/* 38 */       return "ParserTokenEvent [LA," + getAmount() + "," + getValue() + "]";
/*    */     }
/*    */ 
/* 41 */     return "ParserTokenEvent [consume,1," + getValue() + "]";
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     antlr.debug.ParserTokenEvent
 * JD-Core Version:    0.6.2
 */