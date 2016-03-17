/*    */ package antlr.debug;
/*    */ 
/*    */ public class NewLineEvent extends Event
/*    */ {
/*    */   private int line;
/*    */ 
/*    */   public NewLineEvent(Object paramObject)
/*    */   {
/*  8 */     super(paramObject);
/*    */   }
/*    */   public NewLineEvent(Object paramObject, int paramInt) {
/* 11 */     super(paramObject);
/* 12 */     setValues(paramInt);
/*    */   }
/*    */   public int getLine() {
/* 15 */     return this.line;
/*    */   }
/*    */   void setLine(int paramInt) {
/* 18 */     this.line = paramInt;
/*    */   }
/*    */ 
/*    */   void setValues(int paramInt) {
/* 22 */     setLine(paramInt);
/*    */   }
/*    */   public String toString() {
/* 25 */     return "NewLineEvent [" + this.line + "]";
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     antlr.debug.NewLineEvent
 * JD-Core Version:    0.6.2
 */