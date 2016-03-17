/*    */ package org.antlr.stringtemplate.language;
/*    */ 
/*    */ import antlr.CommonAST;
/*    */ import org.antlr.stringtemplate.StringTemplate;
/*    */ 
/*    */ public class StringTemplateAST extends CommonAST
/*    */ {
/* 34 */   protected StringTemplate st = null;
/*    */ 
/*    */   public StringTemplateAST() {
/*    */   }
/*    */   public StringTemplateAST(int type, String text) {
/* 39 */     setType(type);
/* 40 */     setText(text);
/*    */   }
/*    */ 
/*    */   public StringTemplate getStringTemplate() {
/* 44 */     return this.st;
/*    */   }
/*    */ 
/*    */   public void setStringTemplate(StringTemplate st) {
/* 48 */     this.st = st;
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.antlr.stringtemplate.language.StringTemplateAST
 * JD-Core Version:    0.6.2
 */