/*    */ package antlr;
/*    */ 
/*    */ import antlr.collections.AST;
/*    */ 
/*    */ public class CommonAST extends BaseAST
/*    */ {
/* 14 */   int ttype = 0;
/*    */   String text;
/*    */ 
/*    */   public String getText()
/*    */   {
/* 20 */     return this.text;
/*    */   }
/*    */ 
/*    */   public int getType()
/*    */   {
/* 25 */     return this.ttype;
/*    */   }
/*    */ 
/*    */   public void initialize(int paramInt, String paramString) {
/* 29 */     setType(paramInt);
/* 30 */     setText(paramString);
/*    */   }
/*    */ 
/*    */   public void initialize(AST paramAST) {
/* 34 */     setText(paramAST.getText());
/* 35 */     setType(paramAST.getType());
/*    */   }
/*    */ 
/*    */   public CommonAST() {
/*    */   }
/*    */ 
/*    */   public CommonAST(Token paramToken) {
/* 42 */     initialize(paramToken);
/*    */   }
/*    */ 
/*    */   public void initialize(Token paramToken) {
/* 46 */     setText(paramToken.getText());
/* 47 */     setType(paramToken.getType());
/*    */   }
/*    */ 
/*    */   public void setText(String paramString)
/*    */   {
/* 52 */     this.text = paramString;
/*    */   }
/*    */ 
/*    */   public void setType(int paramInt)
/*    */   {
/* 57 */     this.ttype = paramInt;
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     antlr.CommonAST
 * JD-Core Version:    0.6.2
 */