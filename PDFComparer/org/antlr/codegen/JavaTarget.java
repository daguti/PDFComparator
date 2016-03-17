/*    */ package org.antlr.codegen;
/*    */ 
/*    */ import org.antlr.Tool;
/*    */ import org.antlr.stringtemplate.StringTemplate;
/*    */ import org.antlr.tool.Grammar;
/*    */ 
/*    */ public class JavaTarget extends Target
/*    */ {
/*    */   protected StringTemplate chooseWhereCyclicDFAsGo(Tool tool, CodeGenerator generator, Grammar grammar, StringTemplate recognizerST, StringTemplate cyclicDFAST)
/*    */   {
/* 41 */     return recognizerST;
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.antlr.codegen.JavaTarget
 * JD-Core Version:    0.6.2
 */