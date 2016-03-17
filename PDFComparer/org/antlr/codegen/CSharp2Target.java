/*    */ package org.antlr.codegen;
/*    */ 
/*    */ import org.antlr.Tool;
/*    */ import org.antlr.stringtemplate.StringTemplate;
/*    */ import org.antlr.tool.Grammar;
/*    */ 
/*    */ public class CSharp2Target extends Target
/*    */ {
/*    */   protected StringTemplate chooseWhereCyclicDFAsGo(Tool tool, CodeGenerator generator, Grammar grammar, StringTemplate recognizerST, StringTemplate cyclicDFAST)
/*    */   {
/* 42 */     return recognizerST;
/*    */   }
/*    */ 
/*    */   public String encodeIntAsCharEscape(int v)
/*    */   {
/* 47 */     if (v <= 127)
/*    */     {
/* 49 */       String hex1 = Integer.toHexString(v | 0x10000).substring(3, 5);
/* 50 */       return "\\x" + hex1;
/*    */     }
/* 52 */     String hex = Integer.toHexString(v | 0x10000).substring(1, 5);
/* 53 */     return "\\u" + hex;
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.antlr.codegen.CSharp2Target
 * JD-Core Version:    0.6.2
 */