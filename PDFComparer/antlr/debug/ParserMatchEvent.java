/*    */ package antlr.debug;
/*    */ 
/*    */ public class ParserMatchEvent extends GuessingEvent
/*    */ {
/*  6 */   public static int TOKEN = 0;
/*  7 */   public static int BITSET = 1;
/*  8 */   public static int CHAR = 2;
/*  9 */   public static int CHAR_BITSET = 3;
/* 10 */   public static int STRING = 4;
/* 11 */   public static int CHAR_RANGE = 5;
/*    */   private boolean inverse;
/*    */   private boolean matched;
/*    */   private Object target;
/*    */   private int value;
/*    */   private String text;
/*    */ 
/*    */   public ParserMatchEvent(Object paramObject)
/*    */   {
/* 20 */     super(paramObject);
/*    */   }
/*    */ 
/*    */   public ParserMatchEvent(Object paramObject1, int paramInt1, int paramInt2, Object paramObject2, String paramString, int paramInt3, boolean paramBoolean1, boolean paramBoolean2)
/*    */   {
/* 25 */     super(paramObject1);
/* 26 */     setValues(paramInt1, paramInt2, paramObject2, paramString, paramInt3, paramBoolean1, paramBoolean2);
/*    */   }
/*    */   public Object getTarget() {
/* 29 */     return this.target;
/*    */   }
/*    */   public String getText() {
/* 32 */     return this.text;
/*    */   }
/*    */   public int getValue() {
/* 35 */     return this.value;
/*    */   }
/*    */   public boolean isInverse() {
/* 38 */     return this.inverse;
/*    */   }
/*    */   public boolean isMatched() {
/* 41 */     return this.matched;
/*    */   }
/*    */   void setInverse(boolean paramBoolean) {
/* 44 */     this.inverse = paramBoolean;
/*    */   }
/*    */   void setMatched(boolean paramBoolean) {
/* 47 */     this.matched = paramBoolean;
/*    */   }
/*    */   void setTarget(Object paramObject) {
/* 50 */     this.target = paramObject;
/*    */   }
/*    */   void setText(String paramString) {
/* 53 */     this.text = paramString;
/*    */   }
/*    */   void setValue(int paramInt) {
/* 56 */     this.value = paramInt;
/*    */   }
/*    */ 
/*    */   void setValues(int paramInt1, int paramInt2, Object paramObject, String paramString, int paramInt3, boolean paramBoolean1, boolean paramBoolean2) {
/* 60 */     super.setValues(paramInt1, paramInt3);
/* 61 */     setValue(paramInt2);
/* 62 */     setTarget(paramObject);
/* 63 */     setInverse(paramBoolean1);
/* 64 */     setMatched(paramBoolean2);
/* 65 */     setText(paramString);
/*    */   }
/*    */   public String toString() {
/* 68 */     return "ParserMatchEvent [" + (isMatched() ? "ok," : "bad,") + (isInverse() ? "NOT " : "") + (getType() == TOKEN ? "token," : "bitset,") + getValue() + "," + getTarget() + "," + getGuessing() + "]";
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     antlr.debug.ParserMatchEvent
 * JD-Core Version:    0.6.2
 */