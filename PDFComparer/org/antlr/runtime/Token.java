/*    */ package org.antlr.runtime;
/*    */ 
/*    */ public abstract interface Token
/*    */ {
/*    */   public static final int EOR_TOKEN_TYPE = 1;
/*    */   public static final int DOWN = 2;
/*    */   public static final int UP = 3;
/*    */   public static final int MIN_TOKEN_TYPE = 4;
/*    */   public static final int EOF = -1;
/* 42 */   public static final Token EOF_TOKEN = new CommonToken(-1);
/*    */   public static final int INVALID_TOKEN_TYPE = 0;
/* 45 */   public static final Token INVALID_TOKEN = new CommonToken(0);
/*    */ 
/* 50 */   public static final Token SKIP_TOKEN = new CommonToken(0);
/*    */   public static final int DEFAULT_CHANNEL = 0;
/*    */   public static final int HIDDEN_CHANNEL = 99;
/*    */ 
/*    */   public abstract String getText();
/*    */ 
/*    */   public abstract void setText(String paramString);
/*    */ 
/*    */   public abstract int getType();
/*    */ 
/*    */   public abstract void setType(int paramInt);
/*    */ 
/*    */   public abstract int getLine();
/*    */ 
/*    */   public abstract void setLine(int paramInt);
/*    */ 
/*    */   public abstract int getCharPositionInLine();
/*    */ 
/*    */   public abstract void setCharPositionInLine(int paramInt);
/*    */ 
/*    */   public abstract int getChannel();
/*    */ 
/*    */   public abstract void setChannel(int paramInt);
/*    */ 
/*    */   public abstract int getTokenIndex();
/*    */ 
/*    */   public abstract void setTokenIndex(int paramInt);
/*    */ 
/*    */   public abstract CharStream getInputStream();
/*    */ 
/*    */   public abstract void setInputStream(CharStream paramCharStream);
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.antlr.runtime.Token
 * JD-Core Version:    0.6.2
 */