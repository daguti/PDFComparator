/*    */ package antlr;
/*    */ 
/*    */ import antlr.collections.AST;
/*    */ 
/*    */ public class ParseTreeRule extends ParseTree
/*    */ {
/*    */   public static final int INVALID_ALT = -1;
/*    */   protected String ruleName;
/*    */   protected int altNumber;
/*    */ 
/*    */   public ParseTreeRule(String paramString)
/*    */   {
/* 18 */     this(paramString, -1);
/*    */   }
/*    */ 
/*    */   public ParseTreeRule(String paramString, int paramInt) {
/* 22 */     this.ruleName = paramString;
/* 23 */     this.altNumber = paramInt;
/*    */   }
/*    */ 
/*    */   public String getRuleName() {
/* 27 */     return this.ruleName;
/*    */   }
/*    */ 
/*    */   protected int getLeftmostDerivation(StringBuffer paramStringBuffer, int paramInt)
/*    */   {
/* 35 */     int i = 0;
/* 36 */     if (paramInt <= 0) {
/* 37 */       paramStringBuffer.append(' ');
/* 38 */       paramStringBuffer.append(toString());
/* 39 */       return i;
/*    */     }
/* 41 */     AST localAST = getFirstChild();
/* 42 */     i = 1;
/*    */ 
/* 44 */     while (localAST != null) {
/* 45 */       if ((i >= paramInt) || ((localAST instanceof ParseTreeToken))) {
/* 46 */         paramStringBuffer.append(' ');
/* 47 */         paramStringBuffer.append(localAST.toString());
/*    */       }
/*    */       else
/*    */       {
/* 51 */         int j = paramInt - i;
/* 52 */         int k = ((ParseTree)localAST).getLeftmostDerivation(paramStringBuffer, j);
/*    */ 
/* 54 */         i += k;
/*    */       }
/* 56 */       localAST = localAST.getNextSibling();
/*    */     }
/* 58 */     return i;
/*    */   }
/*    */ 
/*    */   public String toString() {
/* 62 */     if (this.altNumber == -1) {
/* 63 */       return '<' + this.ruleName + '>';
/*    */     }
/*    */ 
/* 66 */     return '<' + this.ruleName + "[" + this.altNumber + "]>";
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     antlr.ParseTreeRule
 * JD-Core Version:    0.6.2
 */