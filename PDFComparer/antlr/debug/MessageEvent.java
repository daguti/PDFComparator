/*    */ package antlr.debug;
/*    */ 
/*    */ public class MessageEvent extends Event
/*    */ {
/*    */   private String text;
/*  5 */   public static int WARNING = 0;
/*  6 */   public static int ERROR = 1;
/*    */ 
/*    */   public MessageEvent(Object paramObject)
/*    */   {
/* 10 */     super(paramObject);
/*    */   }
/*    */   public MessageEvent(Object paramObject, int paramInt, String paramString) {
/* 13 */     super(paramObject);
/* 14 */     setValues(paramInt, paramString);
/*    */   }
/*    */   public String getText() {
/* 17 */     return this.text;
/*    */   }
/*    */   void setText(String paramString) {
/* 20 */     this.text = paramString;
/*    */   }
/*    */ 
/*    */   void setValues(int paramInt, String paramString) {
/* 24 */     super.setValues(paramInt);
/* 25 */     setText(paramString);
/*    */   }
/*    */   public String toString() {
/* 28 */     return "ParserMessageEvent [" + (getType() == WARNING ? "warning," : "error,") + getText() + "]";
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     antlr.debug.MessageEvent
 * JD-Core Version:    0.6.2
 */