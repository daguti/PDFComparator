/*    */ package antlr.debug;
/*    */ 
/*    */ public class SemanticPredicateEvent extends GuessingEvent
/*    */ {
/*    */   public static final int VALIDATING = 0;
/*    */   public static final int PREDICTING = 1;
/*    */   private int condition;
/*    */   private boolean result;
/*    */ 
/*    */   public SemanticPredicateEvent(Object paramObject)
/*    */   {
/* 11 */     super(paramObject);
/*    */   }
/*    */   public SemanticPredicateEvent(Object paramObject, int paramInt) {
/* 14 */     super(paramObject, paramInt);
/*    */   }
/*    */   public int getCondition() {
/* 17 */     return this.condition;
/*    */   }
/*    */   public boolean getResult() {
/* 20 */     return this.result;
/*    */   }
/*    */   void setCondition(int paramInt) {
/* 23 */     this.condition = paramInt;
/*    */   }
/*    */   void setResult(boolean paramBoolean) {
/* 26 */     this.result = paramBoolean;
/*    */   }
/*    */ 
/*    */   void setValues(int paramInt1, int paramInt2, boolean paramBoolean, int paramInt3) {
/* 30 */     super.setValues(paramInt1, paramInt3);
/* 31 */     setCondition(paramInt2);
/* 32 */     setResult(paramBoolean);
/*    */   }
/*    */   public String toString() {
/* 35 */     return "SemanticPredicateEvent [" + getCondition() + "," + getResult() + "," + getGuessing() + "]";
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     antlr.debug.SemanticPredicateEvent
 * JD-Core Version:    0.6.2
 */