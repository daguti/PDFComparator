/*    */ package antlr;
/*    */ 
/*    */ public class Token
/*    */   implements Cloneable
/*    */ {
/*    */   public static final int MIN_USER_TYPE = 4;
/*    */   public static final int NULL_TREE_LOOKAHEAD = 3;
/*    */   public static final int INVALID_TYPE = 0;
/*    */   public static final int EOF_TYPE = 1;
/*    */   public static final int SKIP = -1;
/* 22 */   protected int type = 0;
/*    */ 
/* 25 */   public static Token badToken = new Token(0, "<no text>");
/*    */ 
/*    */   public Token() {
/*    */   }
/*    */ 
/*    */   public Token(int paramInt) {
/* 31 */     this.type = paramInt;
/*    */   }
/*    */ 
/*    */   public Token(int paramInt, String paramString) {
/* 35 */     this.type = paramInt;
/* 36 */     setText(paramString);
/*    */   }
/*    */ 
/*    */   public int getColumn() {
/* 40 */     return 0;
/*    */   }
/*    */ 
/*    */   public int getLine() {
/* 44 */     return 0;
/*    */   }
/*    */ 
/*    */   public String getFilename() {
/* 48 */     return null;
/*    */   }
/*    */ 
/*    */   public void setFilename(String paramString) {
/*    */   }
/*    */ 
/*    */   public String getText() {
/* 55 */     return "<no text>";
/*    */   }
/*    */ 
/*    */   public void setText(String paramString) {
/*    */   }
/*    */ 
/*    */   public void setColumn(int paramInt) {
/*    */   }
/*    */ 
/*    */   public void setLine(int paramInt) {
/*    */   }
/*    */ 
/*    */   public int getType() {
/* 68 */     return this.type;
/*    */   }
/*    */ 
/*    */   public void setType(int paramInt) {
/* 72 */     this.type = paramInt;
/*    */   }
/*    */ 
/*    */   public String toString() {
/* 76 */     return "[\"" + getText() + "\",<" + getType() + ">]";
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     antlr.Token
 * JD-Core Version:    0.6.2
 */