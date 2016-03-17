/*    */ package antlr.collections.impl;
/*    */ 
/*    */ import antlr.collections.AST;
/*    */ import antlr.collections.ASTEnumeration;
/*    */ import java.util.NoSuchElementException;
/*    */ 
/*    */ public class ASTEnumerator
/*    */   implements ASTEnumeration
/*    */ {
/*    */   VectorEnumerator nodes;
/* 19 */   int i = 0;
/*    */ 
/*    */   public ASTEnumerator(Vector paramVector)
/*    */   {
/* 23 */     this.nodes = new VectorEnumerator(paramVector);
/*    */   }
/*    */ 
/*    */   public boolean hasMoreNodes() {
/* 27 */     synchronized (this.nodes) {
/* 28 */       return this.i <= this.nodes.vector.lastElement;
/*    */     }
/*    */   }
/*    */ 
/*    */   public AST nextNode() {
/* 33 */     synchronized (this.nodes) {
/* 34 */       if (this.i <= this.nodes.vector.lastElement) {
/* 35 */         return (AST)this.nodes.vector.data[(this.i++)];
/*    */       }
/* 37 */       throw new NoSuchElementException("ASTEnumerator");
/*    */     }
/*    */   }
/*    */ }

/* Location:           C:\Users\ESa10969\Desktop\PDFComparer\
 * Qualified Name:     antlr.collections.impl.ASTEnumerator
 * JD-Core Version:    0.6.2
 */