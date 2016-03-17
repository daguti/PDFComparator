/*    */ package antlr.debug;
/*    */ 
/*    */ public class TraceEvent extends GuessingEvent
/*    */ {
/*    */   private int ruleNum;
/*    */   private int data;
/*  6 */   public static int ENTER = 0;
/*  7 */   public static int EXIT = 1;
/*  8 */   public static int DONE_PARSING = 2;
/*    */ 
/*    */   public TraceEvent(Object paramObject)
/*    */   {
/* 12 */     super(paramObject);
/*    */   }
/*    */   public TraceEvent(Object paramObject, int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/* 15 */     super(paramObject);
/* 16 */     setValues(paramInt1, paramInt2, paramInt3, paramInt4);
/*    */   }
/*    */   public int getData() {
/* 19 */     return this.data;
/*    */   }
/*    */   public int getRuleNum() {
/* 22 */     return this.ruleNum;
/*    */   }
/*    */   void setData(int paramInt) {
/* 25 */     this.data = paramInt;
/*    */   }
/*    */   void setRuleNum(int paramInt) {
/* 28 */     this.ruleNum = paramInt;
/*    */   }
/*    */ 
/*    */   void setValues(int paramInt1, int paramInt2, int paramInt3, int paramInt4) {
/* 32 */     super.setValues(paramInt1, paramInt3);
/* 33 */     setRuleNum(paramInt2);
/* 34 */     setData(paramInt4);
/*    */   }
/*    */   public String toString() {
/* 37 */     return "ParserTraceEvent [" + (getType() == ENTER ? "enter," : "exit,") + getRuleNum() + "," + getGuessing() + "]";
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     antlr.debug.TraceEvent
 * JD-Core Version:    0.6.2
 */