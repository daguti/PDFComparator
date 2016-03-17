/*    */ package org.antlr.stringtemplate.language;
/*    */ 
/*    */ import antlr.CommonToken;
/*    */ import java.util.List;
/*    */ 
/*    */ public class StringTemplateToken extends CommonToken
/*    */ {
/*    */   public List args;
/*    */ 
/*    */   public StringTemplateToken()
/*    */   {
/*    */   }
/*    */ 
/*    */   public StringTemplateToken(int type, String text)
/*    */   {
/* 47 */     super(type, text);
/*    */   }
/*    */ 
/*    */   public StringTemplateToken(String text) {
/* 51 */     super(text);
/*    */   }
/*    */ 
/*    */   public StringTemplateToken(int type, String text, List args) {
/* 55 */     super(type, text);
/* 56 */     this.args = args;
/*    */   }
/*    */ 
/*    */   public String toString() {
/* 60 */     return super.toString() + "; args=" + this.args;
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.antlr.stringtemplate.language.StringTemplateToken
 * JD-Core Version:    0.6.2
 */