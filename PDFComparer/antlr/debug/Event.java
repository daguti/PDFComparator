/*    */ package antlr.debug;
/*    */ 
/*    */ import java.util.EventObject;
/*    */ 
/*    */ public abstract class Event extends EventObject
/*    */ {
/*    */   private int type;
/*    */ 
/*    */   public Event(Object paramObject)
/*    */   {
/* 10 */     super(paramObject);
/*    */   }
/*    */   public Event(Object paramObject, int paramInt) {
/* 13 */     super(paramObject);
/* 14 */     setType(paramInt);
/*    */   }
/*    */   public int getType() {
/* 17 */     return this.type;
/*    */   }
/*    */   void setType(int paramInt) {
/* 20 */     this.type = paramInt;
/*    */   }
/*    */ 
/*    */   void setValues(int paramInt) {
/* 24 */     setType(paramInt);
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     antlr.debug.Event
 * JD-Core Version:    0.6.2
 */