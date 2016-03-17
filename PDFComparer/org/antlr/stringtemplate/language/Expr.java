/*    */ package org.antlr.stringtemplate.language;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import org.antlr.stringtemplate.StringTemplate;
/*    */ import org.antlr.stringtemplate.StringTemplateWriter;
/*    */ 
/*    */ public abstract class Expr
/*    */ {
/*    */   protected StringTemplate enclosingTemplate;
/* 57 */   protected String indentation = null;
/*    */ 
/*    */   public Expr(StringTemplate enclosingTemplate) {
/* 60 */     this.enclosingTemplate = enclosingTemplate;
/*    */   }
/*    */ 
/*    */   public abstract int write(StringTemplate paramStringTemplate, StringTemplateWriter paramStringTemplateWriter)
/*    */     throws IOException;
/*    */ 
/*    */   public StringTemplate getEnclosingTemplate()
/*    */   {
/* 68 */     return this.enclosingTemplate;
/*    */   }
/*    */ 
/*    */   public String getIndentation() {
/* 72 */     return this.indentation;
/*    */   }
/*    */ 
/*    */   public void setIndentation(String indentation) {
/* 76 */     this.indentation = indentation;
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.antlr.stringtemplate.language.Expr
 * JD-Core Version:    0.6.2
 */