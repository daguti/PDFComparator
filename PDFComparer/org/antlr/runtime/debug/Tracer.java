/*    */ package org.antlr.runtime.debug;
/*    */ 
/*    */ import java.io.PrintStream;
/*    */ import org.antlr.runtime.IntStream;
/*    */ import org.antlr.runtime.TokenStream;
/*    */ 
/*    */ public class Tracer extends BlankDebugEventListener
/*    */ {
/*    */   public IntStream input;
/* 39 */   protected int level = 0;
/*    */ 
/*    */   public Tracer(IntStream input) {
/* 42 */     this.input = input;
/*    */   }
/*    */ 
/*    */   public void enterRule(String ruleName) {
/* 46 */     for (int i = 1; i <= this.level; i++) System.out.print(" ");
/* 47 */     System.out.println("> " + ruleName + " lookahead(1)=" + getInputSymbol(1));
/* 48 */     this.level += 1;
/*    */   }
/*    */ 
/*    */   public void exitRule(String ruleName) {
/* 52 */     this.level -= 1;
/* 53 */     for (int i = 1; i <= this.level; i++) System.out.print(" ");
/* 54 */     System.out.println("< " + ruleName + " lookahead(1)=" + getInputSymbol(1));
/*    */   }
/*    */ 
/*    */   public Object getInputSymbol(int k) {
/* 58 */     if ((this.input instanceof TokenStream)) {
/* 59 */       return ((TokenStream)this.input).LT(k);
/*    */     }
/* 61 */     return new Character((char)this.input.LA(k));
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     org.antlr.runtime.debug.Tracer
 * JD-Core Version:    0.6.2
 */