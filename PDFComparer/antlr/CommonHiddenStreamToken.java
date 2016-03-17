/*    */ package antlr;
/*    */ 
/*    */ public class CommonHiddenStreamToken extends CommonToken
/*    */ {
/*    */   protected CommonHiddenStreamToken hiddenBefore;
/*    */   protected CommonHiddenStreamToken hiddenAfter;
/*    */ 
/*    */   public CommonHiddenStreamToken()
/*    */   {
/*    */   }
/*    */ 
/*    */   public CommonHiddenStreamToken(int paramInt, String paramString)
/*    */   {
/* 19 */     super(paramInt, paramString);
/*    */   }
/*    */ 
/*    */   public CommonHiddenStreamToken(String paramString) {
/* 23 */     super(paramString);
/*    */   }
/*    */ 
/*    */   public CommonHiddenStreamToken getHiddenAfter() {
/* 27 */     return this.hiddenAfter;
/*    */   }
/*    */ 
/*    */   public CommonHiddenStreamToken getHiddenBefore() {
/* 31 */     return this.hiddenBefore;
/*    */   }
/*    */ 
/*    */   protected void setHiddenAfter(CommonHiddenStreamToken paramCommonHiddenStreamToken) {
/* 35 */     this.hiddenAfter = paramCommonHiddenStreamToken;
/*    */   }
/*    */ 
/*    */   protected void setHiddenBefore(CommonHiddenStreamToken paramCommonHiddenStreamToken) {
/* 39 */     this.hiddenBefore = paramCommonHiddenStreamToken;
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     antlr.CommonHiddenStreamToken
 * JD-Core Version:    0.6.2
 */